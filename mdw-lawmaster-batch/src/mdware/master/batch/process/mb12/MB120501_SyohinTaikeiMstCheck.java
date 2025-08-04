package mdware.master.batch.process.mb12;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.util.db.MDWareSeq;
import mdware.master.batch.process.mb38.MB380001_CommonMessage;
import mdware.master.batch.process.mb38.MB380007_CommonSyohinSql;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst007001_MstSiyofukaKbDictionary;
import mdware.master.common.dictionary.mst010001_SyoriSyubetsuKbDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.portal.bean.DtAlarmBean;
import mdware.portal.dictionary.AlarmProcessKbDic;
import mdware.portal.dictionary.PortalAlarmKbDic;
import mdware.portal.process.PortalAlarmInterface;

import org.apache.log4j.Level;

/**
 * <p>タイトル:マスタ整合性チェック処理</p>
 * <p>説明:商品階層マスタに登録されている内容に基づき、商品分類体系マスタ作成を行う。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/03/09 マミーマート様向け初版作成 T.Mori
 * @version 1.01 2009/05/12 Urano 処理見直し<BR>
 * @Version 3.00 (2013.12.24) K.TO [CUS00048] ランドローム様対応 マスタ未使用項目
 * @Version 3.01 2015/11/30 TAM.NM FIVI様対応
 */

public class MB120501_SyohinTaikeiMstCheck {

	// DB
	private MasterDataBase db = null;
	private boolean closeDb = false;

	// ログ
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = "";

	// バッチ有効日付
	private String batchYukoDate = "";

	// 切替日付
	private String kirikaeDate = "";

	private String uketsukeNo = null;


	private int seqNo = 0;
	private Map msgMap = MB380001_CommonMessage.getMsg();

	private MB380007_CommonSyohinSql comSyohin = null;

	public static final int SYORI_DAYS = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB120501_SyohinTaikeiMstCheck(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB120501_SyohinTaikeiMstCheck() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			writeLog(Level.INFO_INT, "処理を開始します。");

			//バッチ日付取得
			batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			//バッチ日付+1日
			batchYukoDate = DateChanger.addDate(batchDate, SYORI_DAYS);

			//商品分類体系作成日取得
			kirikaeDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_KIRIKAE_DT);

			int iRecCnt = 0;
			int iRecCntIns = 0;

			boolean syoriFg = false;
			comSyohin = new MB380007_CommonSyohinSql(batchDate, userLog.getJobId(), mst000601_GyoshuKbDictionary.GRO.getCode());
			String delFg = mst000801_DelFlagDictionary.INAI.getCode();

			ResultSet rs = null;
			PreparedStatement psInsGiftSyohinBunrui2 = comSyohin.getPreparedGiftSyohinInsSQL(db, userLog.getJobId());
			PreparedStatement psInsKeiryokiBunrui2 = comSyohin.getPreparedKeiryokiInsSQL(db, userLog.getJobId());
			PreparedStatement psInsHachuBunrui2 = comSyohin.getPreparedSyohinHachuNohinkijunbiInsSQL(db, userLog.getJobId());
			PreparedStatement psInsSyohinBunrui2 = db.getPrepareStatement(getInsSyohinBunrui2SQL());
			PreparedStatement psUpdSyohinBunrui2     = db.getPrepareStatement(getUpdSyohinBunrui2SQL());
			PreparedStatement psUpdSyohinSiyoFukaOK = db.getPrepareStatement(getUpdSyohinMstSiyoFukaSQL(mst007001_MstSiyofukaKbDictionary.KA.getCode()));
			PreparedStatement psUpdSyohinSiyoFukaNG = db.getPrepareStatement(getUpdSyohinMstSiyoFukaSQL(mst007001_MstSiyofukaKbDictionary.FUKA.getCode()));
			PreparedStatement psInsMasterSeigouseiTr = db.getPrepareStatement(getInsMasterSeigouseiCheck());

			// アラーム作成用。

			PortalAlarmInterface alarm = new PortalAlarmInterface();

			/********************************************************
			 * 分類２コードが不一致の場合更新する。以外は更新しない。
			 ********************************************************/
			writeLog(Level.INFO_INT, "商品マスタ更新対象データ取得処理＆更新処理を開始します。");

			rs = db.executeQuery(getSelSyohinBunrui2SQL());

			while (rs.next()) {

				String yuko_dt = rs.getString("YUKO_DT");
				String bunrui1_cd = rs.getString("BUNRUI1_CD");
				String bunrui2_cd = rs.getString("BUNRUI2_CD");
				String syohin_cd = rs.getString("SYOHIN_CD");

				// 有効日が未来日の場合はレコードを更新。
				if(yuko_dt.compareTo(batchDate) > 0){
					psUpdSyohinBunrui2.clearParameters();

					psUpdSyohinBunrui2.setString(1, bunrui2_cd);
					psUpdSyohinBunrui2.setString(2, bunrui1_cd);
					psUpdSyohinBunrui2.setString(3, syohin_cd);
					psUpdSyohinBunrui2.setString(4, yuko_dt);

					iRecCnt += psUpdSyohinBunrui2.executeUpdate();
				}else{
					psUpdSyohinBunrui2.clearParameters();

					psUpdSyohinBunrui2.setString(1, bunrui2_cd);
					psUpdSyohinBunrui2.setString(2, bunrui1_cd);
					psUpdSyohinBunrui2.setString(3, syohin_cd);
					psUpdSyohinBunrui2.setString(4, batchYukoDate);

					int updCnt = psUpdSyohinBunrui2.executeUpdate();

					if(updCnt > 0){
						iRecCnt += updCnt;
					}else{
						//分類２コードを更新(新世代作成)
						psInsSyohinBunrui2.setString(1, batchYukoDate);
						psInsSyohinBunrui2.setString(2, bunrui2_cd);
						psInsSyohinBunrui2.setString(3, bunrui1_cd);
						psInsSyohinBunrui2.setString(4, syohin_cd);
						psInsSyohinBunrui2.setString(5, yuko_dt);
						iRecCntIns += psInsSyohinBunrui2.executeUpdate();

						//ギフト商品マスタ
						comSyohin.setPreparedGiftSyohinInsSQL(psInsGiftSyohinBunrui2, batchYukoDate, userLog.getJobId(), delFg, bunrui1_cd, syohin_cd, yuko_dt);
						psInsGiftSyohinBunrui2.executeUpdate();

						// 計量器商品マスタ
						comSyohin.setPreparedKeiryokiInsSQL(psInsKeiryokiBunrui2, batchYukoDate, userLog.getJobId(), delFg, bunrui1_cd, syohin_cd, yuko_dt);
						psInsKeiryokiBunrui2.executeUpdate();

						// 商品発注納品基準日マスタ
						comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(psInsHachuBunrui2, batchYukoDate, userLog.getJobId(), delFg, bunrui1_cd, syohin_cd, yuko_dt);
						psInsHachuBunrui2.executeUpdate();
					}
				}
			}
			db.closeResultSet(rs);
			psInsSyohinBunrui2.close();
			psUpdSyohinBunrui2.close();

			writeLog(Level.INFO_INT, iRecCntIns + "件の商品マスタレコードを作成しました。");
			writeLog(Level.INFO_INT, iRecCnt + "件の商品マスタレコードを更新しました。");
			writeLog(Level.INFO_INT, "商品マスタ更新対象データ取得処理＆更新処理を終了します。");

			/**********************************************************************
			 * 商品マスタ　使用不可更新（取引先マスタ状況により、可から不可へ更新）
			 **********************************************************************/
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（取引先マスタ状況により、可から不可へ更新）処理を開始します。");
			iRecCnt = 0;
			syoriFg = false;
			seqNo = 0;

			rs = db.executeQuery(getSelSyohinTorihikisakiNgSQL());

			while (rs.next()) {

				//マスタ使用不可区分を「不可」に更新
				psUpdSyohinSiyoFukaNG.setString(1, rs.getString("BUNRUI1_CD"));
				psUpdSyohinSiyoFukaNG.setString(2, rs.getString("SYOHIN_CD"));
				psUpdSyohinSiyoFukaNG.setString(3, rs.getString("YUKO_DT"));

				iRecCnt = iRecCnt + psUpdSyohinSiyoFukaNG.executeUpdate();

				//バッチ処理結果TRへ登録
				if (!syoriFg) {
					syoriFg = true;
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を開始します。");
					// 受付№取得
					uketsukeNo = MDWareSeq.nextValString("bat_uketsuke_no", userLog.getJobId());
					writeLog(Level.INFO_INT, "受付№を取得しました [" + uketsukeNo + "]");
					db.executeUpdate(getInsBatchSyoriKekka(uketsukeNo, msgMap.get("0529").toString()));
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を終了します。");
				}

				//マスタ整合性チェックTRへ登録
				setInsMasterSeigouseiCheck(psInsMasterSeigouseiTr, rs, Integer.valueOf(uketsukeNo).intValue(), ++seqNo);
				psInsMasterSeigouseiTr.executeUpdate();
			}
			db.closeResultSet(rs);

			writeLog(Level.INFO_INT, iRecCnt + "件の商品マスタレコードを更新しました。");
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（取引先マスタ状況により、可から不可へ更新）処理を終了します。");

			/***************************************************************************
			 * 商品マスタ　使用不可更新（商品分類体系マスタ状況により、可から不可へ更新）
			 ***************************************************************************/
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（商品分類体系マスタ状況により、可から不可へ更新）処理を開始します。");
			iRecCnt = 0;
			syoriFg = false;
			seqNo = 0;

			rs = db.executeQuery(getSelSyohinBunruiNgSQL());

			while (rs.next()) {

				//マスタ使用不可区分を「不可」に更新
				psUpdSyohinSiyoFukaNG.setString(1, rs.getString("BUNRUI1_CD"));
				psUpdSyohinSiyoFukaNG.setString(2, rs.getString("SYOHIN_CD"));
				psUpdSyohinSiyoFukaNG.setString(3, rs.getString("YUKO_DT"));

				iRecCnt = iRecCnt + psUpdSyohinSiyoFukaNG.executeUpdate();

				//バッチ処理結果TRへ登録
				if (!syoriFg) {
					syoriFg = true;
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を開始します。");
					// 受付№取得
					uketsukeNo = MDWareSeq.nextValString("bat_uketsuke_no", userLog.getJobId());
					writeLog(Level.INFO_INT, "受付№を取得しました [" + uketsukeNo + "]");
					db.executeUpdate(getInsBatchSyoriKekka(uketsukeNo, msgMap.get("0530").toString()));
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を終了します。");
				}

				//マスタ整合性チェックTRへ登録
				setInsMasterSeigouseiCheck(psInsMasterSeigouseiTr, rs, Integer.valueOf(uketsukeNo).intValue(), ++seqNo);
				psInsMasterSeigouseiTr.executeUpdate();
			}
			db.closeResultSet(rs);

			writeLog(Level.INFO_INT, iRecCnt + "件の商品マスタレコードを更新しました。");
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（商品分類体系マスタ状況により、可から不可へ更新）処理を終了します。");

			/******************************************
			 * 商品マスタ使用可更新（不可から可へ更新）
			 ******************************************/
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（不可から可へ更新）処理を開始します。");
			iRecCnt = 0;
			syoriFg = false;
			seqNo = 0;

			rs = db.executeQuery(getSelSyohinSeigoseiOkSQL());

			while (rs.next()) {

				//マスタ使用不可区分を「可」に更新
				psUpdSyohinSiyoFukaOK.setString(1, rs.getString("BUNRUI1_CD"));
				psUpdSyohinSiyoFukaOK.setString(2, rs.getString("SYOHIN_CD"));
				psUpdSyohinSiyoFukaOK.setString(3, rs.getString("YUKO_DT"));

				iRecCnt = iRecCnt + psUpdSyohinSiyoFukaOK.executeUpdate();

				//バッチ処理結果TRへ登録
				if (!syoriFg) {
					syoriFg = true;
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を開始します。");
					// 受付№取得
					uketsukeNo = MDWareSeq.nextValString("bat_uketsuke_no", userLog.getJobId());
					writeLog(Level.INFO_INT, "受付№を取得しました [" + uketsukeNo + "]");
					db.executeUpdate(getInsBatchSyoriKekka(uketsukeNo, msgMap.get("0528").toString()));
					writeLog(Level.INFO_INT, "バッチ処理結果TR登録処理を終了します。");
				}

				//マスタ整合性チェックTRへ登録
				setInsMasterSeigouseiCheck(psInsMasterSeigouseiTr, rs, Integer.valueOf(uketsukeNo).intValue(), ++seqNo);
				psInsMasterSeigouseiTr.executeUpdate();
			}
			db.closeResultSet(rs);

			writeLog(Level.INFO_INT, iRecCnt + "件の商品マスタレコードを更新しました。");
			writeLog(Level.INFO_INT, "商品マスタ使用可更新（不可から可へ更新）処理を終了します。");

			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

			// -----------------------------------------------------------------
			// アラーム発行処理
			writeLog(Level.INFO_INT, "アラーム発行処理を開始します。");

			String jobId = userLog.getJobId();

			ResultSet aRs = db.executeQuery(getAlarmList());
			while (aRs.next()) {
				String dptCd = aRs.getString("BUNRUI1_CD");
				String siyofukaKb = aRs.getString("MST_SIYOFUKA_KB");

				// "SYORI_DT" がnullでなく、商品マスタの使用不可区分が"不可"の場合は"可"→"不可"に処理されたものと判断
				if (mst007001_MstSiyofukaKbDictionary.FUKA.getCode().equals(siyofukaKb)) {
					String delKeyStr2 = "mst0021" + "kaToFuka/" + dptCd + "/" + batchDate;
					setAlarmMessage(alarm, jobId, dptCd, "mst0021", delKeyStr2);
				}

				// "SYORI_DT" がnullでなく、商品マスタの使用不可区分が"可"の場合は"不可"→"可"に処理されたものと判断
				else if (mst007001_MstSiyofukaKbDictionary.KA.getCode().equals(siyofukaKb)) {
					String delKeyStr3 = "mst0022" + "FukaToKa/" + dptCd + "/" + batchDate;
					setAlarmMessage(alarm, jobId, dptCd, "mst0022", delKeyStr3);
				}
			}
			db.closeResultSet(rs);
			db.commit();

			writeLog(Level.INFO_INT, "アラーム発行処理を終了します。");

			// SQL例外処理
		} catch (SQLException se) {
			db.rollback();

			this.writeError(se);

			throw se;

		} catch (Exception e) {
			db.rollback();

			this.writeError(e);

			throw e;

		} finally {
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/**
	 * 商品マスタの分類２コードが分類体系と異なるデータを取得するSQL
	 * @throws Exception
	 */
	public String getSelSyohinBunrui2SQL() throws SQLException {

		StringBuffer strSql = new StringBuffer();

		//切替日≦バッチ日付+1日の場合
		if (Integer.parseInt(kirikaeDate) <= Integer.parseInt(batchYukoDate)) {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT RST.BUNRUI1_CD AS BUNRUI1_CD_RST, ");
			strSql.append("       SHN.BUNRUI1_CD AS BUNRUI1_CD, ");
			strSql.append("       SHN.SYOHIN_CD, ");
			strSql.append("       SHN.YUKO_DT, ");
			strSql.append("       RST.BUNRUI2_CD ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI RST ");
			strSql.append("    ON SHN.SYSTEM_KB  = RST.SYSTEM_KB ");
			strSql.append("   AND SHN.BUNRUI5_CD = RST.BUNRUI5_CD");
			strSql.append(" WHERE SHN.BUNRUI2_CD <> RST.BUNRUI2_CD");
			strSql.append("   AND SHN.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND ( ");
			strSql.append("          SHN.YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("          OR ");
			strSql.append("          SHN.YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                           FROM R_SYOHIN ");
			// #6620 DEL 2022.06.29 SIEU.D (S)
			// strSql.append("                          WHERE BUNRUI1_CD = SHN.BUNRUI1_CD ");
			// strSql.append("                            AND SYOHIN_CD  = SHN.SYOHIN_CD ");
			strSql.append("                          WHERE SYOHIN_CD  = SHN.SYOHIN_CD ");
			// #6620 DEL 2022.06.29 SIEU.D (E)
			strSql.append("	                           AND YUKO_DT   <= '" + batchYukoDate + "' ");
			strSql.append("	                       ) ");
			strSql.append("	      ) ");

			//切替日＞バッチ日付+1日の場合
		} else {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT RST.BUNRUI1_CD AS BUNRUI1_CD_RST, ");
			strSql.append("       SHN.BUNRUI1_CD AS BUNRUI1_CD, ");
			strSql.append("       SHN.SYOHIN_CD, ");
			strSql.append("       SHN.YUKO_DT, ");
			strSql.append("       RST.BUNRUI2_CD ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI RST ");
			strSql.append("    ON SHN.SYSTEM_KB  = RST.SYSTEM_KB ");
			strSql.append("   AND SHN.BUNRUI5_CD = RST.BUNRUI5_CD");
			strSql.append(" WHERE SHN.BUNRUI2_CD <> RST.BUNRUI2_CD");
			strSql.append("   AND SHN.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND SHN.YUKO_DT   < '" + kirikaeDate + "' ");
			strSql.append("   AND ( ");
			strSql.append("          SHN.YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("          OR ");
			strSql.append("          SHN.YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                           FROM R_SYOHIN ");
			// #6620 DEL 2022.06.29 SIEU.D (S)
			// strSql.append("                          WHERE BUNRUI1_CD = SHN.BUNRUI1_CD ");
			// strSql.append("                            AND SYOHIN_CD  = SHN.SYOHIN_CD ");
			strSql.append("                          WHERE SYOHIN_CD  = SHN.SYOHIN_CD ");
			// #6620 DEL 2022.06.29 SIEU.D (E)
			strSql.append("	                           AND YUKO_DT   <= '" + batchYukoDate + "' ");
			strSql.append("	                       ) ");
			strSql.append("	      ) ");
			strSql.append("UNION ALL ");
			strSql.append("SELECT RST.BUNRUI1_CD AS BUNRUI1_CD_RST, ");
			strSql.append("       SHN.BUNRUI1_CD AS BUNRUI1_CD, ");
			strSql.append("       SHN.SYOHIN_CD, ");
			strSql.append("       SHN.YUKO_DT, ");
			strSql.append("       RST.BUNRUI2_CD ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI_KIRIKAE RST ");
			strSql.append("    ON SHN.BUNRUI5_CD = RST.BUNRUI5_CD");
			strSql.append("   AND SHN.SYSTEM_KB  = RST.SYSTEM_KB ");
			strSql.append(" WHERE SHN.BUNRUI2_CD <> RST.BUNRUI2_CD");
			strSql.append("   AND SHN.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND SHN.YUKO_DT  >= '" + kirikaeDate + "' ");
		}

		return strSql.toString();
	}

	//アラーム登録用のレコード一覧取得
	private String getAlarmList() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT ");
		sb.append("	TRIM(RS.BUNRUI1_CD) AS BUNRUI1_CD, ");
		sb.append("	TRIM(RS.MST_SIYOFUKA_KB) AS MST_SIYOFUKA_KB ");

		sb.append("FROM");
		sb.append("	R_SYOHIN RS ");
		sb.append("	JOIN TR_MASTER_SEIGOUSEI_CHECK TR ");
		sb.append("		ON TR.YUKO_DT = RS.YUKO_DT AND");
		sb.append("		TR.SYOHIN_CD = RS.SYOHIN_CD AND");
		sb.append("		TR.BUNRUI1_CD = RS.BUNRUI1_CD AND ");
		sb.append("		RS.HENKO_DT = TR.SYORI_DT ");

		sb.append("	JOIN TR_BATCH_SYORIKEKKA KEKKA ");
		sb.append("		ON TR.SYORI_DT = TR.SYORI_DT AND");
		sb.append("		TR.UKETSUKE_NO = KEKKA.UKETSUKE_NO ");

		sb.append("WHERE");
		sb.append("	RS.HENKO_DT = '").append(batchDate).append("' AND");
		sb.append("	RS.BATCH_UPDATE_ID = '").append(userLog.getJobId()).append("'");
		sb.append(" GROUP BY RS.BUNRUI1_CD, RS.MST_SIYOFUKA_KB");
		sb.append(" ORDER BY BUNRUI1_CD, MST_SIYOFUKA_KB ");
		return sb.toString();
	}

	/**
	 * 商品マスタから取引コードが不正なデータを取得するSQL
	 * @throws Exception
	 */
	public String getSelSyohinTorihikisakiNgSQL() throws SQLException {

		StringBuffer strSql = new StringBuffer();

		strSql.delete(0, strSql.length());
		strSql.append("SELECT SHN.* ");
		strSql.append("  FROM (SELECT BUNRUI1_CD, ");
		strSql.append("               SYOHIN_CD, ");
		strSql.append("               YUKO_DT, ");
		strSql.append("               SYSTEM_KB, ");
		strSql.append("               BUNRUI2_CD, ");
		strSql.append("               BUNRUI5_CD, ");
		strSql.append("               GENTANKA_VL, ");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
		strSql.append("               GENTANKA_NUKI_VL, ");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
		strSql.append("               BAITANKA_VL, ");
		strSql.append("               SIIRESAKI_CD, ");
		strSql.append("               HANBAI_ST_DT, ");
		strSql.append("               HANBAI_ED_DT, ");
		strSql.append("               TEN_HACHU_ST_DT, ");
		strSql.append("               TEN_HACHU_ED_DT, ");
		strSql.append("               (SELECT MAX(TEKIYO_START_DT) ");
		strSql.append("                  FROM R_TORIHIKISAKI ");
		strSql.append("                 WHERE COMP_CD  ='" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
		strSql.append("                   AND CHOAI_KB ='" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
		strSql.append("                   AND TORIHIKISAKI_CD = S.SIIRESAKI_CD ");
		strSql.append("                   AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
		strSql.append("                   AND TEKIYO_START_DT <= CASE WHEN S.YUKO_DT <= '" + batchYukoDate + "' ");
		strSql.append("                                               THEN '" + batchYukoDate + "' ");
		strSql.append("                                               ELSE S.YUKO_DT ");
		strSql.append("                                          END ");
		strSql.append("               ) TORIHIKI_TEKIYO_DT ");
		strSql.append("          FROM R_SYOHIN S ");
		strSql.append("         WHERE MST_SIYOFUKA_KB = '" + mst007001_MstSiyofukaKbDictionary.KA.getCode() + "' ");
		strSql.append("           AND DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("           AND ( ");
		strSql.append("                   YUKO_DT > '" + batchYukoDate + "' ");
		strSql.append("                   OR ");
		strSql.append("                   YUKO_DT = (SELECT MAX(YUKO_DT) ");
		strSql.append("                                FROM R_SYOHIN ");
		// #6620 DEL 2022.06.29 SIEU.D (S)
		// strSql.append("                               WHERE BUNRUI1_CD = S.BUNRUI1_CD ");
		// strSql.append("                                 AND SYOHIN_CD  = S.SYOHIN_CD ");
		strSql.append("                               WHERE SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 DEL 2022.06.29 SIEU.D (E)
		strSql.append("                                 AND YUKO_DT   <= '" + batchYukoDate + "' ");
		strSql.append("                             ) ");
		strSql.append("               ) ");
		strSql.append("       ) SHN ");
		strSql.append("  LEFT JOIN ");
		strSql.append("       R_TORIHIKISAKI RTR ");
		strSql.append("    ON RTR.COMP_CD            = '" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
		strSql.append("   AND RTR.CHOAI_KB           = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
		strSql.append("   AND RTR.TORIHIKISAKI_CD    = SHN.SIIRESAKI_CD ");
		strSql.append("   AND RTR.TEKIYO_START_DT    = SHN.TORIHIKI_TEKIYO_DT ");
		strSql.append("   AND RTR.DELETE_FG          = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		strSql.append("   AND RTR.TORIHIKI_TEISHI_KB = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
		strSql.append("   AND RTR.TORIKESHI_FG       = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
		strSql.append(" WHERE RTR.TORIHIKISAKI_CD IS NULL ");

		return strSql.toString();
	}

	/**
	 * 商品マスタから分類コードが不正なデータを取得するSQL
	 * @throws Exception
	 */
	public String getSelSyohinBunruiNgSQL() throws SQLException {

		StringBuffer strSql = new StringBuffer();

		//切替日≦バッチ日付+1日の場合
		if (Integer.parseInt(kirikaeDate) <= Integer.parseInt(batchYukoDate)) {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT BUNRUI1_CD, ");
			strSql.append("       SYOHIN_CD, ");
			strSql.append("       YUKO_DT, ");
			strSql.append("       BUNRUI2_CD, ");
			strSql.append("       BUNRUI5_CD, ");
			strSql.append("       GENTANKA_VL, ");
			// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
	        strSql.append("       GENTANKA_NUKI_VL, ");
	        // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
			strSql.append("       BAITANKA_VL, ");
			strSql.append("       SIIRESAKI_CD, ");
			strSql.append("       HANBAI_ST_DT, ");
			strSql.append("       HANBAI_ED_DT, ");
			strSql.append("       TEN_HACHU_ST_DT, ");
			strSql.append("       TEN_HACHU_ED_DT ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" WHERE MST_SIYOFUKA_KB = '" + mst007001_MstSiyofukaKbDictionary.KA.getCode() + "' ");
			strSql.append("   AND DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND ( ");
			strSql.append("           YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("           OR ");
			strSql.append("           YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                        FROM R_SYOHIN ");
			// #6620 DEL 2022.06.29 SIEU.D (S)
			// strSql.append("                       WHERE BUNRUI1_CD = SHN.BUNRUI1_CD ");
			// strSql.append("                         AND SYOHIN_CD  = SHN.SYOHIN_CD ");
			strSql.append("                       WHERE SYOHIN_CD  = SHN.SYOHIN_CD ");
			// #6620 DEL 2022.06.29 SIEU.D (E)
			strSql.append("                         AND YUKO_DT   <= '" + batchYukoDate + "' ");
			strSql.append("                     ) ");
			strSql.append("       ) ");
			strSql.append("   AND NOT EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_SYOHIN_TAIKEI ");
			strSql.append("         WHERE SYSTEM_KB  = SHN.SYSTEM_KB ");
			strSql.append("           AND BUNRUI5_CD = SHN.BUNRUI5_CD ");
			strSql.append("           AND BUNRUI1_CD = SHN.BUNRUI1_CD ");
			strSql.append("       ) ");

			//切替日＞バッチ日付+1日の場合
		} else {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT BUNRUI1_CD, ");
			strSql.append("       SYOHIN_CD, ");
			strSql.append("       YUKO_DT, ");
			strSql.append("       BUNRUI2_CD, ");
			strSql.append("       BUNRUI5_CD, ");
			strSql.append("       GENTANKA_VL, ");
			// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
            strSql.append("       GENTANKA_NUKI_VL, ");
            // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
			strSql.append("       BAITANKA_VL, ");
			strSql.append("       SIIRESAKI_CD, ");
			strSql.append("       HANBAI_ST_DT, ");
			strSql.append("       HANBAI_ED_DT, ");
			strSql.append("       TEN_HACHU_ST_DT, ");
			strSql.append("       TEN_HACHU_ED_DT ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" WHERE MST_SIYOFUKA_KB = '" + mst007001_MstSiyofukaKbDictionary.KA.getCode() + "' ");
			strSql.append("   AND DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND YUKO_DT         < '" + kirikaeDate + "' ");
			strSql.append("   AND ( ");
			strSql.append("           YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("           OR ");
			strSql.append("           YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                        FROM R_SYOHIN ");
			// #6620 DEL 2022.06.30 SIEU.D(S)
			// strSql.append("                       WHERE BUNRUI1_CD = SHN.BUNRUI1_CD ");
			// strSql.append("                         AND SYOHIN_CD  = SHN.SYOHIN_CD ");
 			strSql.append("                       WHERE SYOHIN_CD  = SHN.SYOHIN_CD ");
			// #6620 DEL 2022.06.30 SIEU.D(S)
			strSql.append("                         AND YUKO_DT   <= '" + batchYukoDate + "' ");
			strSql.append("                     ) ");
			strSql.append("       ) ");
			strSql.append("   AND NOT EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_SYOHIN_TAIKEI ");
			strSql.append("         WHERE SYSTEM_KB  = SHN.SYSTEM_KB ");
			strSql.append("           AND BUNRUI5_CD = SHN.BUNRUI5_CD ");
			strSql.append("           AND BUNRUI1_CD = SHN.BUNRUI1_CD ");
			strSql.append("       ) ");
			strSql.append("UNION ALL ");
			strSql.append("SELECT BUNRUI1_CD, ");
			strSql.append("       SYOHIN_CD, ");
			strSql.append("       YUKO_DT, ");
			strSql.append("       BUNRUI2_CD, ");
			strSql.append("       BUNRUI5_CD, ");
			strSql.append("       GENTANKA_VL, ");
			// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
            strSql.append("       GENTANKA_NUKI_VL, ");
            // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
			strSql.append("       BAITANKA_VL, ");
			strSql.append("       SIIRESAKI_CD, ");
			strSql.append("       HANBAI_ST_DT, ");
			strSql.append("       HANBAI_ED_DT, ");
			strSql.append("       TEN_HACHU_ST_DT, ");
			strSql.append("       TEN_HACHU_ED_DT ");
			strSql.append("  FROM R_SYOHIN SHN ");
			strSql.append(" WHERE SHN.MST_SIYOFUKA_KB = '" + mst007001_MstSiyofukaKbDictionary.KA.getCode() + "' ");
			strSql.append("   AND SHN.DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("   AND SHN.YUKO_DT        >= '" + kirikaeDate + "' ");
			strSql.append("   AND NOT EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_SYOHIN_TAIKEI_KIRIKAE ");
			strSql.append("         WHERE SYSTEM_KB  = SHN.SYSTEM_KB ");
			strSql.append("           AND BUNRUI5_CD = SHN.BUNRUI5_CD ");
			strSql.append("           AND BUNRUI1_CD = SHN.BUNRUI1_CD ");
			strSql.append("       ) ");
		}

		return strSql.toString();
	}

	/**
	 * 商品マスタからマスタ使用不可区分が「可」に変更となるデータを取得するSQL
	 * @throws Exception
	 */
	public String getSelSyohinSeigoseiOkSQL() throws SQLException {

		StringBuffer strSql = new StringBuffer();

		//切替日≦バッチ日付+1日の場合
		if (Integer.parseInt(kirikaeDate) <= Integer.parseInt(batchYukoDate)) {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT SHN.* ");
			strSql.append("  FROM (SELECT BUNRUI1_CD, ");
			strSql.append("               SYOHIN_CD, ");
			strSql.append("               YUKO_DT, ");
			strSql.append("               SYSTEM_KB, ");
			strSql.append("               BUNRUI2_CD, ");
			strSql.append("               BUNRUI5_CD, ");
			strSql.append("               GENTANKA_VL, ");
			// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
            strSql.append("               GENTANKA_NUKI_VL, ");
            // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
			strSql.append("               BAITANKA_VL, ");
			strSql.append("               SIIRESAKI_CD, ");
			strSql.append("               HANBAI_ST_DT, ");
			strSql.append("               HANBAI_ED_DT, ");
			strSql.append("               TEN_HACHU_ST_DT, ");
			strSql.append("               TEN_HACHU_ED_DT, ");
			strSql.append("               (SELECT MAX(TEKIYO_START_DT) ");
			strSql.append("                  FROM R_TORIHIKISAKI ");
			strSql.append("                 WHERE COMP_CD  ='" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
			strSql.append("                   AND CHOAI_KB ='" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
			strSql.append("                   AND TORIHIKISAKI_CD = S.SIIRESAKI_CD ");
			strSql.append("                   AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
			strSql.append("                   AND TEKIYO_START_DT <= CASE WHEN S.YUKO_DT <= '" + batchYukoDate + "' ");
			strSql.append("                                               THEN '" + batchYukoDate + "' ");
			strSql.append("                                               ELSE S.YUKO_DT ");
			strSql.append("                                          END ");
			strSql.append("               ) TORIHIKI_TEKIYO_DT ");
			strSql.append("          FROM R_SYOHIN S ");
			strSql.append("         WHERE MST_SIYOFUKA_KB ='" + mst007001_MstSiyofukaKbDictionary.FUKA.getCode() + "' ");
			strSql.append("           AND DELETE_FG       ='" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("           AND ( ");
			strSql.append("                   YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("                   OR ");
			strSql.append("                   YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                                FROM R_SYOHIN ");
			strSql.append("                               WHERE SYOHIN_CD  = S.SYOHIN_CD ");
			// #6620 DEL 2022.06.30 SIEU.D(S)
			// strSql.append("                                 AND BUNRUI1_CD = S.BUNRUI1_CD ");
			// #6620 DEL 2022.06.30 SIEU.D(E)
			strSql.append("                                 AND YUKO_DT <= '" + batchYukoDate + "' ");
			strSql.append("                             ) ");
			strSql.append("               ) ");
			strSql.append("       ) SHN ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_TORIHIKISAKI RTR ");
			strSql.append("    ON RTR.COMP_CD            = '" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
			strSql.append("   AND RTR.CHOAI_KB           = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
			strSql.append("   AND RTR.TORIHIKISAKI_CD    = SHN.SIIRESAKI_CD ");
			strSql.append("   AND RTR.TEKIYO_START_DT    = SHN.TORIHIKI_TEKIYO_DT ");
			strSql.append("   AND RTR.DELETE_FG          = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
			strSql.append("   AND RTR.TORIHIKI_TEISHI_KB = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
			strSql.append("   AND RTR.TORIKESHI_FG       = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI RST ");
			strSql.append("    ON SHN.SYSTEM_KB  = RST.SYSTEM_KB ");
			strSql.append("   AND SHN.BUNRUI5_CD = RST.BUNRUI5_CD ");
			strSql.append("   AND SHN.BUNRUI1_CD = RST.BUNRUI1_CD ");

			//切替日＞バッチ日付+1日の場合
		} else {

			strSql.delete(0, strSql.length());
			strSql.append("SELECT SHN.* ");
			strSql.append("  FROM (SELECT BUNRUI1_CD, ");
			strSql.append("               SYOHIN_CD, ");
			strSql.append("               YUKO_DT, ");
			strSql.append("               SYSTEM_KB, ");
			strSql.append("               BUNRUI2_CD, ");
			strSql.append("               BUNRUI5_CD, ");
			strSql.append("               GENTANKA_VL, ");
			// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
            strSql.append("       GENTANKA_NUKI_VL, ");
            // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
			strSql.append("               BAITANKA_VL, ");
			strSql.append("               SIIRESAKI_CD, ");
			strSql.append("               HANBAI_ST_DT, ");
			strSql.append("               HANBAI_ED_DT, ");
			strSql.append("               TEN_HACHU_ST_DT, ");
			strSql.append("               TEN_HACHU_ED_DT, ");
			strSql.append("               (SELECT MAX(TEKIYO_START_DT) ");
			strSql.append("                  FROM R_TORIHIKISAKI ");
			strSql.append("                 WHERE COMP_CD  ='" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
			strSql.append("                   AND CHOAI_KB ='" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
			strSql.append("                   AND TORIHIKISAKI_CD = S.SIIRESAKI_CD ");
			strSql.append("                   AND TEKIYO_START_DT <= CASE WHEN S.YUKO_DT <= '" + batchYukoDate + "' ");
			strSql.append("                                               THEN '" + batchYukoDate + "' ");
			strSql.append("                                               ELSE S.YUKO_DT ");
			strSql.append("                                          END ");
			strSql.append("                   AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
			strSql.append("               ) TORIHIKI_TEKIYO_DT ");
			strSql.append("          FROM R_SYOHIN S ");
			strSql.append("         WHERE MST_SIYOFUKA_KB ='" + mst007001_MstSiyofukaKbDictionary.FUKA.getCode() + "' ");
			strSql.append("           AND DELETE_FG ='" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			strSql.append("           AND ( ");
			strSql.append("                   YUKO_DT > '" + batchYukoDate + "' ");
			strSql.append("                   OR ");
			strSql.append("                   YUKO_DT = (SELECT MAX(YUKO_DT) ");
			strSql.append("                                FROM R_SYOHIN ");
			strSql.append("                               WHERE SYOHIN_CD  = S.SYOHIN_CD ");
			// #6620 MOD 2022.06.30 SIEU.D(S)
			// strSql.append("                                 AND BUNRUI1_CD = S.BUNRUI1_CD ");
			// #6620 MOD 2022.06.30 SIEU.D(E)
			strSql.append("                                 AND YUKO_DT <= '" + batchYukoDate + "' ");
			strSql.append("                             ) ");
			strSql.append("               ) ");
			strSql.append("       ) SHN ");
			strSql.append(" INNER JOIN ");
			strSql.append("       R_TORIHIKISAKI RTR ");
			strSql.append("    ON RTR.COMP_CD            = '" + mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD + "' ");
			strSql.append("   AND RTR.CHOAI_KB           = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' ");
			strSql.append("   AND RTR.TORIHIKISAKI_CD    = SHN.SIIRESAKI_CD ");
			strSql.append("   AND RTR.TEKIYO_START_DT    = SHN.TORIHIKI_TEKIYO_DT ");
			strSql.append("   AND RTR.DELETE_FG          = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
			strSql.append("   AND RTR.TORIHIKI_TEISHI_KB = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
			strSql.append("   AND RTR.TORIKESHI_FG       = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
			strSql.append("  LEFT JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI RST ");
			strSql.append("    ON RST.SYSTEM_KB  = SHN.SYSTEM_KB ");
			strSql.append("   AND RST.BUNRUI5_CD = SHN.BUNRUI5_CD ");
			strSql.append("   AND RST.BUNRUI1_CD = SHN.BUNRUI1_CD ");
			strSql.append("  LEFT JOIN ");
			strSql.append("       R_SYOHIN_TAIKEI_KIRIKAE RSK ");
			strSql.append("    ON RSK.SYSTEM_KB  = SHN.SYSTEM_KB ");
			strSql.append("   AND RSK.BUNRUI5_CD = SHN.BUNRUI5_CD ");
			strSql.append("   AND RSK.BUNRUI1_CD = SHN.BUNRUI1_CD ");
			strSql.append(" WHERE (SHN.YUKO_DT <  '" + kirikaeDate + "' AND RST.BUNRUI5_CD IS NOT NULL) ");
			strSql.append("    OR (SHN.YUKO_DT >= '" + kirikaeDate + "' AND RSK.BUNRUI5_CD IS NOT NULL) ");

		}

		return strSql.toString();
	}

	/**
	 * 商品マスタの分類２コードを更新するSQL
	 * @throws Exception
	 */
	public String getUpdSyohinBunrui2SQL() throws SQLException {

		StringBuffer strSql = new StringBuffer();

		strSql.delete(0, strSql.length());
		strSql.append("UPDATE R_SYOHIN ");
		strSql.append("   SET BUNRUI2_CD      = ?, ");
		strSql.append("       HENKO_DT        = '" + batchDate + "', ");
		strSql.append("       BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		strSql.append("       BATCH_UPDATE_ID = '" + userLog.getJobId() + "' ");
		strSql.append(" WHERE BUNRUI1_CD = ? "); //分類１コード
		strSql.append("   AND SYOHIN_CD  = ? "); //商品コード
		strSql.append("   AND YUKO_DT    = ? "); //有効日

		return strSql.toString();
	}

	/**
	 * 商品マスタのマスタ使用不可区分を更新するSQL
	 * @throws Exception
	 */
	public String getUpdSyohinMstSiyoFukaSQL(String mstSiyoFukaKb) throws SQLException {

		StringBuffer strSql = new StringBuffer();

		strSql.delete(0, strSql.length());
		strSql.append("UPDATE R_SYOHIN ");
		strSql.append("   SET MST_SIYOFUKA_KB = '" + mstSiyoFukaKb + "', ");
		strSql.append("       HENKO_DT        = '" + batchDate + "', ");
		strSql.append("       BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		strSql.append("       BATCH_UPDATE_ID = '" + userLog.getJobId() + "' ");
		strSql.append(" WHERE BUNRUI1_CD = ? "); //分類１コード
		strSql.append("   AND SYOHIN_CD  = ? "); //商品コード
		strSql.append("   AND YUKO_DT    = ? "); //有効日

		return strSql.toString();
	}

	/**
	 * バッチ処理結果TRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private String getInsBatchSyoriKekka(String uketsukeNo, String msg) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO TR_BATCH_SYORIKEKKA (");
		sql.append("    SYORI_DT, "); // 処理日
		sql.append("    SYORI_SYUBETSU_KB, "); // 処理種別区分
		sql.append("    UKETSUKE_NO, "); // 受付№
		sql.append("    MESSAGE_TX, "); // メッセージ内容
		sql.append("    INSERT_TS, "); // 作成年月日
		sql.append("    INSERT_USER_ID, "); // 作成者ID
		sql.append("    UPDATE_TS, "); // 更新年月日
		sql.append("    UPDATE_USER_ID, "); // 更新者ID
		sql.append("    DELETE_FG "); // 削除フラグ
		sql.append(") VALUES (");
		sql.append("    '" + batchDate + "', "); // 処理日
		sql.append("    '" + mst010001_SyoriSyubetsuKbDictionary.MST_SEIGOUSEI_CHK.getCode() + "', "); // 処理種別区分
		sql.append("    " + uketsukeNo + ", "); // 受付№
		sql.append("    '" + msg + "', "); // メッセージ内容
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',"); // 作成年月日
		sql.append("    '" + userLog.getJobId() + "',"); // 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',"); // 更新年月日
		sql.append("    '" + userLog.getJobId() + "',"); // 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' "); // 削除フラグ
		sql.append(") ");

		return sql.toString();
	}

	/**
	 * マスタ整合性チェックTRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private String getInsMasterSeigouseiCheck() throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO TR_MASTER_SEIGOUSEI_CHECK ");
		sql.append("( ");
		sql.append("    SYORI_DT,");
		sql.append("    UKETSUKE_NO,");
		sql.append("    UKETSUKE_SEQ,");
		sql.append("    BUNRUI1_CD,");
		sql.append("    SYOHIN_CD,");
		sql.append("    YUKO_DT,");
		sql.append("    BUNRUI2_CD,");
		sql.append("    BUNRUI5_CD,");
		sql.append("    GENTANKA_VL,");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
		sql.append("    GENTANKA_NUKI_VL,");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (E)
		sql.append("    BAITANKA_VL,");
		sql.append("    SIIRESAKI_CD,");
		sql.append("    HANBAI_ST_DT,");
		sql.append("    HANBAI_ED_DT,");
		sql.append("    TEN_HACHU_ST_DT,");
		sql.append("    TEN_HACHU_ED_DT,");
		sql.append("    ALARM_MAKE_FG,");
		sql.append("    INSERT_TS,");
		sql.append("    INSERT_USER_ID,");
		sql.append("    UPDATE_TS,");
		sql.append("    UPDATE_USER_ID,");
		sql.append("    DELETE_FG ");
		sql.append(") VALUES ( ");
		sql.append("    '" + batchDate + "',");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
		sql.append("    ?,");
		// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    ?,");
		sql.append("    '1', ");
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sql.append("  	'" + userLog.getJobId() + "', ");
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sql.append("  	'" + userLog.getJobId() + "', ");
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sql.append(" ) ");

		return sql.toString();
	}

	/**
	 * マスタ整合性チェックTRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private void setInsMasterSeigouseiCheck(PreparedStatement ps, ResultSet rs, int uketukeNo, int seqNo) throws SQLException {

		int idx = 0;

		//受付№
		idx++;
		ps.setInt(idx, uketukeNo);

		//受付SEQ №
		idx++;
		ps.setInt(idx, seqNo);

		//分類１コード
		idx++;
		ps.setString(idx, rs.getString("BUNRUI1_CD"));

		//商品コード
		idx++;
		ps.setString(idx, rs.getString("SYOHIN_CD"));

		//有効日
		idx++;
		ps.setString(idx, rs.getString("YUKO_DT"));

		//分類２コード
		idx++;
		ps.setString(idx, rs.getString("BUNRUI2_CD"));

		//分類５コード
		idx++;
		ps.setString(idx, rs.getString("BUNRUI5_CD"));

		//原価単価
		idx++;
		ps.setString(idx, rs.getString("GENTANKA_VL"));

		// No.183 MSTB071 Add 2015.11.26 TAM.NM (S)
		//原価単価（税抜）
        idx++;
        ps.setString(idx, rs.getString("GENTANKA_NUKI_VL"));
        // No.183 MSTB071 Add 2015.11.26 TAM.NM (E)

		//売価単価
		idx++;
		ps.setString(idx, rs.getString("BAITANKA_VL"));

		//仕入先コード
		idx++;
		ps.setString(idx, rs.getString("SIIRESAKI_CD"));

		//販売開始日
		idx++;
		ps.setString(idx, rs.getString("HANBAI_ST_DT"));

		//販売終了日
		idx++;
		ps.setString(idx, rs.getString("HANBAI_ED_DT"));

		//店舗発注開始日
		idx++;
		ps.setString(idx, rs.getString("TEN_HACHU_ST_DT"));

		//店舗発注終了日
		idx++;
		ps.setString(idx, rs.getString("TEN_HACHU_ED_DT"));

	}

	/**
	 * ユーザーログとバッチログにログを出力します。
	 *
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}

	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 *
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

	/**
	 * アラーム情報を登録する。
	 * @param  alarm
	 * @param  strAlarmId アラームID
	 * @param  strDelKeyStr String
	 * @return なし
	 */
	private void setAlarmMessage(PortalAlarmInterface alarm, String jobId, String bumonCd, String strAlarmId, String DelKeyStr) {
		alarm.removeAlarmMessage(DelKeyStr, jobId, AlarmProcessKbDic.BATCH.getCode());

		// アラーム情報登録クラス
		DtAlarmBean alarmBean = new DtAlarmBean();
		String strProcess = null;

		// アラーム区分
		alarmBean.setAlarmKb(PortalAlarmKbDic.BUYERMUKE.getCode());
		// 送信先区分
		alarmBean.setDestinationBumonCd(bumonCd);
		// 送信先ユーザーID
		alarmBean.setDestinationUserId(jobId);
		// url_tx
		//		alarmBean.setUrlTx("app?JobID=mstC01201_BatchResultInit");
		// del_key_tx
		alarmBean.setDelKeyTx(DelKeyStr);
		// アラームID
		alarmBean.setAlarmId(strAlarmId);
		// 作成者ID
		alarmBean.setInsertUserId(jobId);

		// 処理区分
		strProcess = AlarmProcessKbDic.BATCH.getCode();

		alarm.setAlarmMessage(alarmBean, strProcess);
	}

	private String getInsSyohinBunrui2SQL() throws SQLException {
		String timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT ");
		sb.append("INTO R_SYOHIN( ");
		sb.append("	BUNRUI1_CD, ");
		sb.append("	SYOHIN_CD, ");
		sb.append("	YUKO_DT, ");
		sb.append("	SYSTEM_KB, ");
		sb.append("	GYOSYU_KB, ");
		sb.append("	SYOHIN_KB, ");
		sb.append("	KETASU_KB, ");
		sb.append("	BUNRUI2_CD, ");
		sb.append("	BUNRUI5_CD, ");
		sb.append("	HINMOKU_CD, ");
		sb.append("	SYOHIN_2_CD, ");
		sb.append("	ZAIKO_SYOHIN_CD, ");
		sb.append("	JYOHO_SYOHIN_CD, ");
		sb.append("	MAKER_CD, ");
		sb.append("	HINMEI_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_2_NA, ");
		sb.append("	REC_HINMEI_KANJI_NA, ");
		sb.append("	HINMEI_KANA_NA, ");
		sb.append("	KIKAKU_KANA_NA, ");
		sb.append("	KIKAKU_KANA_2_NA, ");
		sb.append("	REC_HINMEI_KANA_NA, ");
		sb.append("	SYOHIN_WIDTH_QT, ");
		sb.append("	SYOHIN_HEIGHT_QT, ");
		sb.append("	SYOHIN_DEPTH_QT, ");
		sb.append("	E_SHOP_KB, ");
		sb.append("	PB_KB, ");
		sb.append("	SUBCLASS_CD, ");
		sb.append("	HAIFU_CD, ");
		sb.append("	ZEI_KB, ");
		sb.append("	TAX_RATE_KB, ");
		sb.append("	GENTANKA_VL, ");
		sb.append("	BAITANKA_VL, ");
//2013.12.24 [CUS00048]  マスタ未使用項目 (S)
		sb.append("	GENTANKA_NUKI_VL, ");
		sb.append("	BAITANKA_NUKI_VL, ");
		sb.append("	BAIKA_HAISHIN_FG, ");
		sb.append("	FREE_1_KB, ");
		sb.append("	FREE_2_KB, ");
		sb.append("	FREE_3_KB, ");
		sb.append("	FREE_4_KB, ");
		sb.append("	FREE_5_KB, ");
		sb.append("	COMMENT_1_TX, ");
		sb.append("	COMMENT_2_TX, ");
		sb.append("	FREE_CD, ");
//2013.12.24 [CUS00048]  マスタ未使用項目 (E)
		sb.append("	TOSYO_BAIKA_VL, ");
		sb.append("	PRE_GENTANKA_VL, ");
		sb.append("	PRE_BAITANKA_VL, ");
		sb.append("	TOKUBETU_GENKA_VL, ");
		sb.append("	MAKER_KIBO_KAKAKU_VL, ");
		sb.append("	SIIRESAKI_CD, ");
		sb.append("	DAIHYO_HAISO_CD, ");
		sb.append("	TEN_SIIRESAKI_KANRI_CD, ");
		sb.append("	SIIRE_HINBAN_CD, ");
		sb.append("	HACYU_SYOHIN_KB, ");
		sb.append("	HACYU_SYOHIN_CD, ");
		sb.append("	EOS_KB, ");
		sb.append("	HACHU_TANI_NA, ");
		sb.append("	HANBAI_TANI, ");
		sb.append("	HACHUTANI_IRISU_QT, ");
		sb.append("	MAX_HACHUTANI_QT, ");
		sb.append("	CASE_HACHU_KB, ");
		sb.append("	BARA_IRISU_QT, ");
		sb.append("	TEN_HACHU_ST_DT, ");
		sb.append("	TEN_HACHU_ED_DT, ");
		sb.append("	HANBAI_ST_DT, ");
		sb.append("	HANBAI_ED_DT, ");
		sb.append("	HANBAI_KIKAN_KB, ");
		sb.append("	TEIKAN_KB, ");
		sb.append("	SOBA_SYOHIN_KB, ");
		sb.append("	NOHIN_KIGEN_KB, ");
		sb.append("	NOHIN_KIGEN_QT, ");
		sb.append("	BIN_1_KB, ");
		sb.append("	HACHU_PATTERN_1_KB, ");
		sb.append("	SIME_TIME_1_QT, ");
		sb.append("	C_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_TIME_1_KB, ");
		sb.append("	BIN_2_KB, ");
		sb.append("	HACHU_PATTERN_2_KB, ");
		sb.append("	SIME_TIME_2_QT, ");
		sb.append("	C_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_TIME_2_KB, ");
		sb.append("	BIN_3_KB, ");
		sb.append("	HACHU_PATTERN_3_KB, ");
		sb.append("	SIME_TIME_3_QT, ");
		sb.append("	C_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_TIME_3_KB, ");
		sb.append("	C_NOHIN_RTIME_KB, ");
		sb.append("	YUSEN_BIN_KB, ");
		sb.append("	F_BIN_KB, ");
		sb.append("	BUTURYU_KB, ");
		sb.append("	GOT_MUJYOKEN_FG, ");
		sb.append("	GOT_START_MM, ");
		sb.append("	GOT_END_MM, ");
		sb.append("	HACHU_TEISI_KB, ");
		sb.append("	CENTER_ZAIKO_KB, ");
		sb.append("	NOHIN_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_2_CD, ");
		sb.append("	ITF_CD, ");
		sb.append("	GTIN_CD, ");
		sb.append("	VENDER_MAKER_CD, ");
		sb.append("	ZAIKO_CENTER_CD, ");
		sb.append("	ZAIKO_HACHU_SAKI, ");
		sb.append("	CENTER_WEIGHT_QT, ");
		sb.append("	PACK_WIDTH_QT, ");
		sb.append("	PACK_HEIGTH_QT, ");
		sb.append("	PACK_DEPTH_QT, ");
		sb.append("	PACK_WEIGHT_QT, ");
		sb.append("	CENTER_HACHUTANI_KB, ");
		sb.append("	CENTER_HACHUTANI_QT, ");
		sb.append("	CENTER_BARA_IRISU_QT, ");
		sb.append("	CENTER_IRISU_QT, ");
		sb.append("	CASE_IRISU_QT, ");
		sb.append("	CENTER_IRISU_2_QT, ");
		sb.append("	MIN_ZAIKOSU_QT, ");
		sb.append("	MAX_ZAIKOSU_QT, ");
		sb.append("	KIJUN_ZAIKOSU_QT, ");
		sb.append("	MIN_ZAIKONISSU_QT, ");
		sb.append("	MAX_ZAIKONISSU_QT, ");
		sb.append("	CENTER_KYOYO_KB, ");
		sb.append("	CENTER_KYOYO_DT, ");
		sb.append("	CENTER_SYOMI_KIKAN_KB, ");
		sb.append("	CENTER_SYOMI_KIKAN_DT, ");
		sb.append("	TEN_GROUPNO_CD, ");
		sb.append("	TC_JYOUHO_NA, ");
		sb.append("	NOHIN_ONDO_KB, ");
		sb.append("	YOKOMOTI_KB, ");
		sb.append("	SHINAZOROE_KB, ");
		sb.append("	HONBU_ZAI_KB, ");
		sb.append("	TEN_ZAIKO_KB, ");
		sb.append("	HANBAI_SEISAKU_KB, ");
		sb.append("	HENPIN_NB, ");
		sb.append("	HENPIN_GENKA_VL, ");
		sb.append("	CGC_HENPIN_KB, ");
		sb.append("	HANBAI_LIMIT_KB, ");
		sb.append("	HANBAI_LIMIT_QT, ");
		sb.append("	PLU_SEND_DT, ");
		sb.append("	KEYPLU_FG, ");
		sb.append("	PLU_KB, ");
		sb.append("	SYUZEI_HOKOKU_KB, ");
		sb.append("	SAKE_NAIYORYO_QT, ");
		sb.append("	TAG_HYOJI_BAIKA_VL, ");
		sb.append("	KESHI_BAIKA_VL, ");
		sb.append("	YORIDORI_KB, ");
		sb.append("	YORIDORI_VL, ");
		sb.append("	YORIDORI_QT, ");
		sb.append("	BLAND_CD, ");
		sb.append("	SEASON_CD, ");
		sb.append("	HUKUSYU_CD, ");
		sb.append("	STYLE_CD, ");
		sb.append("	SCENE_CD, ");
		sb.append("	SEX_CD, ");
		sb.append("	AGE_CD, ");
		sb.append("	GENERATION_CD, ");
		sb.append("	SOZAI_CD, ");
		sb.append("	PATTERN_CD, ");
		sb.append("	ORIAMI_CD, ");
		sb.append("	HUKA_KINO_CD, ");
		sb.append("	SODE_CD, ");
		sb.append("	SIZE_CD, ");
		sb.append("	COLOR_CD, ");
		sb.append("	KEIYAKU_SU_QT, ");
		sb.append("	KEIYAKU_PATTERN_KB, ");
		sb.append("	KEIYAKU_ST_DT, ");
		sb.append("	KEIYAKU_ED_DT, ");
		sb.append("	KUMISU_KB, ");
		sb.append("	NEFUDA_KB, ");
		sb.append("	NEFUDA_UKEWATASI_DT, ");
		sb.append("	NEFUDA_UKEWATASI_KB, ");
		sb.append("	PC_KB, ");
		sb.append("	DAISI_NO_NB, ");
		sb.append("	UNIT_PRICE_TANI_KB, ");
		sb.append("	UNIT_PRICE_NAIYORYO_QT, ");
		sb.append("	UNIT_PRICE_KIJUN_TANI_QT, ");
		sb.append("	SYOHI_KIGEN_KB, ");
		sb.append("	SYOHI_KIGEN_DT, ");
		sb.append("	DAICHO_TENPO_KB, ");
		sb.append("	DAICHO_HONBU_KB, ");
		sb.append("	DAICHO_SIIRESAKI_KB, ");
		sb.append("	TANA_NO_NB, ");
		sb.append("	DAN_NO_NB, ");
		sb.append("	REBATE_FG, ");
		sb.append("	MARK_GROUP_CD, ");
		sb.append("	MARK_CD, ");
		sb.append("	YUNYUHIN_KB, ");
		sb.append("	SANTI_CD, ");
		sb.append("	D_ZOKUSEI_1_NA, ");
		sb.append("	S_ZOKUSEI_1_NA, ");
		sb.append("	D_ZOKUSEI_2_NA, ");
		sb.append("	S_ZOKUSEI_2_NA, ");
		sb.append("	D_ZOKUSEI_3_NA, ");
		sb.append("	S_ZOKUSEI_3_NA, ");
		sb.append("	D_ZOKUSEI_4_NA, ");
		sb.append("	S_ZOKUSEI_4_NA, ");
		sb.append("	D_ZOKUSEI_5_NA, ");
		sb.append("	S_ZOKUSEI_5_NA, ");
		sb.append("	D_ZOKUSEI_6_NA, ");
		sb.append("	S_ZOKUSEI_6_NA, ");
		sb.append("	D_ZOKUSEI_7_NA, ");
		sb.append("	S_ZOKUSEI_7_NA, ");
		sb.append("	D_ZOKUSEI_8_NA, ");
		sb.append("	S_ZOKUSEI_8_NA, ");
		sb.append("	D_ZOKUSEI_9_NA, ");
		sb.append("	S_ZOKUSEI_9_NA, ");
		sb.append("	D_ZOKUSEI_10_NA, ");
		sb.append("	S_ZOKUSEI_10_NA, ");
		sb.append("	FUJI_SYOHIN_KB, ");
		sb.append("	COMMENT_TX, ");
		sb.append("	AUTO_DEL_KB, ");
		sb.append("	MST_SIYOFUKA_KB, ");
		sb.append("	HAIBAN_FG, ");
		sb.append("	SINKI_TOROKU_DT, ");
		sb.append("	HENKO_DT, ");
		sb.append("	SYOKAI_TOROKU_TS, ");
		sb.append("	SYOKAI_USER_ID, ");
		sb.append("	INSERT_TS, ");
		sb.append("	INSERT_USER_ID, ");
		sb.append("	UPDATE_TS, ");
		sb.append("	UPDATE_USER_ID, ");
		sb.append("	BATCH_UPDATE_TS, ");
		sb.append("	BATCH_UPDATE_ID, ");
		sb.append("	DELETE_FG");
		sb.append(") ");

		sb.append("SELECT");
		sb.append("	BUNRUI1_CD, ");
		sb.append("	SYOHIN_CD, ");
		sb.append("	?, "); // YUKO_DT
		sb.append("	SYSTEM_KB, ");
		sb.append("	GYOSYU_KB, ");
		sb.append("	SYOHIN_KB, ");
		sb.append("	KETASU_KB, ");
		sb.append("	?, ");
		sb.append("	BUNRUI5_CD, ");
		sb.append("	HINMOKU_CD, ");
		sb.append("	SYOHIN_2_CD, ");
		sb.append("	ZAIKO_SYOHIN_CD, ");
		sb.append("	JYOHO_SYOHIN_CD, ");
		sb.append("	MAKER_CD, ");
		sb.append("	HINMEI_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_2_NA, ");
		sb.append("	REC_HINMEI_KANJI_NA, ");
		sb.append("	HINMEI_KANA_NA, ");
		sb.append("	KIKAKU_KANA_NA, ");
		sb.append("	KIKAKU_KANA_2_NA, ");
		sb.append("	REC_HINMEI_KANA_NA, ");
		sb.append("	SYOHIN_WIDTH_QT, ");
		sb.append("	SYOHIN_HEIGHT_QT, ");
		sb.append("	SYOHIN_DEPTH_QT, ");
		sb.append("	E_SHOP_KB, ");
		sb.append("	PB_KB, ");
		sb.append("	SUBCLASS_CD, ");
		sb.append("	HAIFU_CD, ");
		sb.append("	ZEI_KB, ");
		sb.append("	TAX_RATE_KB, ");
		sb.append("	GENTANKA_VL, ");
		sb.append("	BAITANKA_VL, ");
//2013.12.24 [CUS00048]  マスタ未使用項目 (S)
		sb.append("	GENTANKA_NUKI_VL, ");
		sb.append("	BAITANKA_NUKI_VL, ");
		sb.append( mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +",");//売価配信フラグに「0」をセット
		sb.append("	FREE_1_KB, ");
		sb.append("	FREE_2_KB, ");
		sb.append("	FREE_3_KB, ");
		sb.append("	FREE_4_KB, ");
		sb.append("	FREE_5_KB, ");
		sb.append("	COMMENT_1_TX, ");
		sb.append("	COMMENT_2_TX, ");
		sb.append("	FREE_CD, ");
//2013.12.24 [CUS00048]  マスタ未使用項目 (E)
		sb.append("	TOSYO_BAIKA_VL, ");
		sb.append("	PRE_GENTANKA_VL, ");
		sb.append("	PRE_BAITANKA_VL, ");
		sb.append("	TOKUBETU_GENKA_VL, ");
		sb.append("	MAKER_KIBO_KAKAKU_VL, ");
		sb.append("	SIIRESAKI_CD, ");
		sb.append("	DAIHYO_HAISO_CD, ");
		sb.append("	TEN_SIIRESAKI_KANRI_CD, ");
		sb.append("	SIIRE_HINBAN_CD, ");
		sb.append("	HACYU_SYOHIN_KB, ");
		sb.append("	HACYU_SYOHIN_CD, ");
		sb.append("	EOS_KB, ");
		sb.append("	HACHU_TANI_NA, ");
		sb.append("	HANBAI_TANI, ");
		sb.append("	HACHUTANI_IRISU_QT, ");
		sb.append("	MAX_HACHUTANI_QT, ");
		sb.append("	CASE_HACHU_KB, ");
		sb.append("	BARA_IRISU_QT, ");
		sb.append("	TEN_HACHU_ST_DT, ");
		sb.append("	TEN_HACHU_ED_DT, ");
		sb.append("	HANBAI_ST_DT, ");
		sb.append("	HANBAI_ED_DT, ");
		sb.append("	HANBAI_KIKAN_KB, ");
		sb.append("	TEIKAN_KB, ");
		sb.append("	SOBA_SYOHIN_KB, ");
		sb.append("	NOHIN_KIGEN_KB, ");
		sb.append("	NOHIN_KIGEN_QT, ");
		sb.append("	BIN_1_KB, ");
		sb.append("	HACHU_PATTERN_1_KB, ");
		sb.append("	SIME_TIME_1_QT, ");
		sb.append("	C_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_TIME_1_KB, ");
		sb.append("	BIN_2_KB, ");
		sb.append("	HACHU_PATTERN_2_KB, ");
		sb.append("	SIME_TIME_2_QT, ");
		sb.append("	C_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_TIME_2_KB, ");
		sb.append("	BIN_3_KB, ");
		sb.append("	HACHU_PATTERN_3_KB, ");
		sb.append("	SIME_TIME_3_QT, ");
		sb.append("	C_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_TIME_3_KB, ");
		sb.append("	C_NOHIN_RTIME_KB, ");
		sb.append("	YUSEN_BIN_KB, ");
		sb.append("	F_BIN_KB, ");
		sb.append("	BUTURYU_KB, ");
		sb.append("	GOT_MUJYOKEN_FG, ");
		sb.append("	GOT_START_MM, ");
		sb.append("	GOT_END_MM, ");
		sb.append("	HACHU_TEISI_KB, ");
		sb.append("	CENTER_ZAIKO_KB, ");
		sb.append("	NOHIN_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_2_CD, ");
		sb.append("	ITF_CD, ");
		sb.append("	GTIN_CD, ");
		sb.append("	VENDER_MAKER_CD, ");
		sb.append("	ZAIKO_CENTER_CD, ");
		sb.append("	ZAIKO_HACHU_SAKI, ");
		sb.append("	CENTER_WEIGHT_QT, ");
		sb.append("	PACK_WIDTH_QT, ");
		sb.append("	PACK_HEIGTH_QT, ");
		sb.append("	PACK_DEPTH_QT, ");
		sb.append("	PACK_WEIGHT_QT, ");
		sb.append("	CENTER_HACHUTANI_KB, ");
		sb.append("	CENTER_HACHUTANI_QT, ");
		sb.append("	CENTER_BARA_IRISU_QT, ");
		sb.append("	CENTER_IRISU_QT, ");
		sb.append("	CASE_IRISU_QT, ");
		sb.append("	CENTER_IRISU_2_QT, ");
		sb.append("	MIN_ZAIKOSU_QT, ");
		sb.append("	MAX_ZAIKOSU_QT, ");
		sb.append("	KIJUN_ZAIKOSU_QT, ");
		sb.append("	MIN_ZAIKONISSU_QT, ");
		sb.append("	MAX_ZAIKONISSU_QT, ");
		sb.append("	CENTER_KYOYO_KB, ");
		sb.append("	CENTER_KYOYO_DT, ");
		sb.append("	CENTER_SYOMI_KIKAN_KB, ");
		sb.append("	CENTER_SYOMI_KIKAN_DT, ");
		sb.append("	TEN_GROUPNO_CD, ");
		sb.append("	TC_JYOUHO_NA, ");
		sb.append("	NOHIN_ONDO_KB, ");
		sb.append("	YOKOMOTI_KB, ");
		sb.append("	SHINAZOROE_KB, ");
		sb.append("	HONBU_ZAI_KB, ");
		sb.append("	TEN_ZAIKO_KB, ");
		sb.append("	HANBAI_SEISAKU_KB, ");
		sb.append("	HENPIN_NB, ");
		sb.append("	HENPIN_GENKA_VL, ");
		sb.append("	CGC_HENPIN_KB, ");
		sb.append("	HANBAI_LIMIT_KB, ");
		sb.append("	HANBAI_LIMIT_QT, ");
		sb.append("	CASE");
		sb.append(" 	WHEN PLU_SEND_DT > '").append(batchYukoDate).append("' THEN PLU_SEND_DT ");
		sb.append("		ELSE '").append(batchYukoDate).append("'");
		sb.append("	END AS PLU_SEND_DT, ");
		sb.append("	KEYPLU_FG, ");
		sb.append("	PLU_KB, ");
		sb.append("	SYUZEI_HOKOKU_KB, ");
		sb.append("	SAKE_NAIYORYO_QT, ");
		sb.append("	TAG_HYOJI_BAIKA_VL, ");
		sb.append("	KESHI_BAIKA_VL, ");
		sb.append("	YORIDORI_KB, ");
		sb.append("	YORIDORI_VL, ");
		sb.append("	YORIDORI_QT, ");
		sb.append("	BLAND_CD, ");
		sb.append("	SEASON_CD, ");
		sb.append("	HUKUSYU_CD, ");
		sb.append("	STYLE_CD, ");
		sb.append("	SCENE_CD, ");
		sb.append("	SEX_CD, ");
		sb.append("	AGE_CD, ");
		sb.append("	GENERATION_CD, ");
		sb.append("	SOZAI_CD, ");
		sb.append("	PATTERN_CD, ");
		sb.append("	ORIAMI_CD, ");
		sb.append("	HUKA_KINO_CD, ");
		sb.append("	SODE_CD, ");
		sb.append("	SIZE_CD, ");
		sb.append("	COLOR_CD, ");
		sb.append("	KEIYAKU_SU_QT, ");
		sb.append("	KEIYAKU_PATTERN_KB, ");
		sb.append("	KEIYAKU_ST_DT, ");
		sb.append("	KEIYAKU_ED_DT, ");
		sb.append("	KUMISU_KB, ");
		sb.append("	NEFUDA_KB, ");
		sb.append("	NEFUDA_UKEWATASI_DT, ");
		sb.append("	NEFUDA_UKEWATASI_KB, ");
		sb.append("	PC_KB, ");
		sb.append("	DAISI_NO_NB, ");
		sb.append("	UNIT_PRICE_TANI_KB, ");
		sb.append("	UNIT_PRICE_NAIYORYO_QT, ");
		sb.append("	UNIT_PRICE_KIJUN_TANI_QT, ");
		sb.append("	SYOHI_KIGEN_KB, ");
		sb.append("	SYOHI_KIGEN_DT, ");
		sb.append("	DAICHO_TENPO_KB, ");
		sb.append("	DAICHO_HONBU_KB, ");
		sb.append("	DAICHO_SIIRESAKI_KB, ");
		sb.append("	TANA_NO_NB, ");
		sb.append("	DAN_NO_NB, ");
		sb.append("	REBATE_FG, ");
		sb.append("	MARK_GROUP_CD, ");
		sb.append("	MARK_CD, ");
		sb.append("	YUNYUHIN_KB, ");
		sb.append("	SANTI_CD, ");
		sb.append("	D_ZOKUSEI_1_NA, ");
		sb.append("	S_ZOKUSEI_1_NA, ");
		sb.append("	D_ZOKUSEI_2_NA, ");
		sb.append("	S_ZOKUSEI_2_NA, ");
		sb.append("	D_ZOKUSEI_3_NA, ");
		sb.append("	S_ZOKUSEI_3_NA, ");
		sb.append("	D_ZOKUSEI_4_NA, ");
		sb.append("	S_ZOKUSEI_4_NA, ");
		sb.append("	D_ZOKUSEI_5_NA, ");
		sb.append("	S_ZOKUSEI_5_NA, ");
		sb.append("	D_ZOKUSEI_6_NA, ");
		sb.append("	S_ZOKUSEI_6_NA, ");
		sb.append("	D_ZOKUSEI_7_NA, ");
		sb.append("	S_ZOKUSEI_7_NA, ");
		sb.append("	D_ZOKUSEI_8_NA, ");
		sb.append("	S_ZOKUSEI_8_NA, ");
		sb.append("	D_ZOKUSEI_9_NA, ");
		sb.append("	S_ZOKUSEI_9_NA, ");
		sb.append("	D_ZOKUSEI_10_NA, ");
		sb.append("	S_ZOKUSEI_10_NA, ");
		sb.append("	FUJI_SYOHIN_KB, ");
		sb.append("	COMMENT_TX, ");
		sb.append("	AUTO_DEL_KB, ");
		sb.append("	MST_SIYOFUKA_KB, ");
		sb.append("	HAIBAN_FG, ");
		sb.append("	SINKI_TOROKU_DT, ");
		sb.append("	'").append(batchDate).append("', ");					// HENKO_DT
		sb.append("	SYOKAI_TOROKU_TS, ");
		sb.append("	SYOKAI_USER_ID, ");

		sb.append("	'" + timeStamp + "', ");	// INSERT_TS
		sb.append("	INSERT_USER_ID, ");
		sb.append("	'" + timeStamp + "', ");	// UPDATE_TS
		sb.append("	UPDATE_USER_ID, ");
		sb.append("	'" + timeStamp + "', ");	// BATCH_UPDATE_TS
		sb.append("	'" + userLog.getJobId() + "', ");	// BATCH_UPDATE_ID

		sb.append("	DELETE_FG ");
		sb.append("FROM");
		sb.append("	R_SYOHIN ");
		sb.append("WHERE");
		sb.append("	BUNRUI1_CD = ? AND");
		sb.append("	SYOHIN_CD = ? AND");
		sb.append("	YUKO_DT = ?");

		return sb.toString();
	}
}