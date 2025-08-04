package mdware.master.batch.process.mb86;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.util.db.MDWareSeq;
import mdware.master.util.db.MasterDataBase;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.master.batch.process.mb38.MB380001_CommonMessage;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006901_AlarmMakeFgDictionary;
import mdware.master.common.dictionary.mst007601_NonActKbDictionary;
import mdware.master.common.dictionary.mst010001_SyoriSyubetsuKbDictionary;
import mdware.portal.bean.DtAlarmBean;
import mdware.portal.dictionary.AlarmProcessKbDic;
import mdware.portal.dictionary.PortalAlarmKbDic;
import mdware.portal.process.PortalAlarmInterface;

/**
 * <p>タイトル:不稼動判定処理</p>
 * <p>説明:単品売上情報、単品仕入情報をもとに、不稼動商品の設定を行う。</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2015/07/21 Sou ORACLE11対応
 * @Version 1.01 2015/11/30 TAM.NM FIVI様対応
 */

public class MB860102_FukadoHantei {

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = "";

	// 不稼動期間
	private String fukadoKikan = "";

	// 不稼動除外期間
	private String fukadoJyogaiKikan = "";

	private String uketsukeNo = null;

	private int seqNo = 0;

	private Map msgMap = MB380001_CommonMessage.getMsg();

	private PreparedStatement psSyohin   = null;
	private PreparedStatement psGift     = null;
	private PreparedStatement psKeiryoki = null;
	private PreparedStatement psHachu    = null;
	private PreparedStatement psTrFukado = null;

	private static final int SYORI_DAYS = 1;

	String bunrui1cd = null;   	// 分類１コード
	String syohincd = null;   	// 商品コード
	String yukodt = null;   	// 有効日

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB860102_FukadoHantei(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB860102_FukadoHantei() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		int iRec = 0;
		// 計量器件数用
		int iRec2 = 0;
		// ギフト商品件数用
		int iRec3 = 0;
		// 商品発注納品基準日件数用
		int iRec4 = 0;

		try {

			writeLog(Level.INFO_INT, "処理を開始します。");

			//バッチ日付取得
			batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			//不稼動期間取得
			fukadoKikan = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.NONACT_SPAN);

			//不稼動除外期間取得
			fukadoJyogaiKikan = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.ACT_SPAN);

			//SQL文生成用
			StringBuffer strSql = new StringBuffer();

			writeLog(Level.INFO_INT, "単品売上情報更新処理を開始します。");

			//最新売上日更新
			strSql.delete(0,strSql.length());

			// 2015/07/21 Sou ORACLE11対応 Start
//			strSql.append("UPDATE ( ");
//			strSql.append("    SELECT /*+ BYPASS_UJVC */ ");
//			strSql.append("           T.*, W.KEIJYO_DT ");
//			strSql.append("      FROM (SELECT SYOHIN_CD, ");
//			strSql.append("                   TENPO_CD, ");
//			strSql.append("                   MAX(KEIJYO_DT) KEIJYO_DT ");
//			strSql.append("              FROM WK_TANPIN_URIAGE ");
//			strSql.append("             GROUP BY ");
//			strSql.append("                   SYOHIN_CD, ");
//			strSql.append("                   TENPO_CD ");
//			strSql.append("           ) W ");
//			strSql.append("     INNER JOIN ");
//			strSql.append("           R_TANPINTEN_TORIATUKAI T ");
//			strSql.append("        ON T.SYOHIN_CD = W.SYOHIN_CD ");
//			strSql.append("       AND T.TENPO_CD  = W.TENPO_CD ");
//			strSql.append("       AND T.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
//			strSql.append(")  ");
//			strSql.append("SET NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
//			strSql.append("    NON_ACT_SOSHIN_DT = NULL, ");
//			strSql.append("    SAISHIN_URIAGE_DT = KEIJYO_DT, ");
//			strSql.append("    HENKO_DT          = '" + batchDate + "', ");
//			strSql.append("    BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
//			strSql.append("    BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");

			strSql.append(" MERGE INTO ");
			strSql.append("     R_TANPINTEN_TORIATUKAI T ");
			strSql.append(" USING ");
			strSql.append("    (SELECT SYOHIN_CD, ");
			strSql.append("             TENPO_CD, ");
			strSql.append("             MAX(KEIJYO_DT) KEIJYO_DT ");
			strSql.append("       FROM WK_TANPIN_URIAGE ");
			strSql.append("      GROUP BY ");
			strSql.append("             SYOHIN_CD, ");
			strSql.append("             TENPO_CD ) W ");
			strSql.append(" ON (T.SYOHIN_CD = W.SYOHIN_CD ");
			strSql.append("     AND T.TENPO_CD  = W.TENPO_CD ");
			strSql.append("     AND T.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("     ) ");
			strSql.append(" WHEN MATCHED THEN ");
			strSql.append("  UPDATE SET ");
			strSql.append("     T.NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
			strSql.append("     T.NON_ACT_SOSHIN_DT = NULL, ");
			strSql.append("     T.SAISHIN_URIAGE_DT = W.KEIJYO_DT, ");
			strSql.append("     T.HENKO_DT          = '" + batchDate + "', ");
			strSql.append("     T.BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("     T.BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");
			// 2015/07/21 Sou ORACLE11対応 End
			iRec = db.executeUpdate(strSql.toString());

			writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタを更新しました。");
			writeLog(Level.INFO_INT, "単品売上情報更新処理を終了します。");


			//最新仕入日更新
			writeLog(Level.INFO_INT, "単品仕入情報更新処理を開始します。");
			strSql.delete(0,strSql.length());

			// 2015/07/21 Sou ORACLE11対応 Start
//			strSql.append("UPDATE ( ");
//			strSql.append("    SELECT /*+ BYPASS_UJVC */ ");
//			strSql.append("           T.*, W.DENPYO_DT ");
//			strSql.append("      FROM (SELECT BUNRUI1_CD, ");
//			strSql.append("                   SYOHIN_CD, ");
//			strSql.append("                   TENPO_CD, ");
//			strSql.append("                   MAX(DENPYO_DT) DENPYO_DT ");
//			strSql.append("              FROM WK_TANPIN_SHIIRE ");
//			strSql.append("             GROUP BY ");
//			strSql.append("                   BUNRUI1_CD, ");
//			strSql.append("                   SYOHIN_CD, ");
//			strSql.append("                   TENPO_CD ");
//			strSql.append("           ) W ");
//			strSql.append("     INNER JOIN ");
//			strSql.append("           R_TANPINTEN_TORIATUKAI T ");
//			strSql.append("        ON T.BUNRUI1_CD = W.BUNRUI1_CD ");
//			strSql.append("       AND T.SYOHIN_CD  = W.SYOHIN_CD ");
//			strSql.append("       AND T.TENPO_CD   = W.TENPO_CD ");
//			strSql.append("       AND T.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
//			strSql.append(")  ");
//			strSql.append("SET NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
//			strSql.append("    NON_ACT_SOSHIN_DT = NULL, ");
//			strSql.append("    SAISHIN_SHIIRE_DT = DENPYO_DT, ");
//			strSql.append("    HENKO_DT          = '" + batchDate + "', ");
//			strSql.append("    BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
//			strSql.append("    BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");

			strSql.append(" MERGE INTO ");
			strSql.append("     R_TANPINTEN_TORIATUKAI T ");
			strSql.append(" USING ");
			strSql.append("     (SELECT BUNRUI1_CD, ");
			strSql.append("             SYOHIN_CD, ");
			strSql.append("             TENPO_CD, ");
			strSql.append("             MAX(DENPYO_DT) DENPYO_DT ");
			strSql.append("        FROM WK_TANPIN_SHIIRE ");
			strSql.append("       GROUP BY ");
			strSql.append("             BUNRUI1_CD, ");
			strSql.append("             SYOHIN_CD, ");
			strSql.append("             TENPO_CD ) W");
			strSql.append("  ON( T.BUNRUI1_CD = W.BUNRUI1_CD ");
			strSql.append("     AND T.SYOHIN_CD  = W.SYOHIN_CD ");
			strSql.append("     AND T.TENPO_CD   = W.TENPO_CD ");
			strSql.append("     AND T.DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ) ");
			strSql.append(" WHEN MATCHED THEN ");
			strSql.append("  UPDATE SET ");
			strSql.append("    T.NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
			strSql.append("    T.NON_ACT_SOSHIN_DT = NULL, ");
			strSql.append("    T.SAISHIN_SHIIRE_DT = W.DENPYO_DT, ");
			strSql.append("    T.HENKO_DT          = '" + batchDate + "', ");
			strSql.append("    T.BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("    T.BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");
			// 2015/07/21 Sou ORACLE11対応 End
			iRec = db.executeUpdate(strSql.toString());

			writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタを更新しました。");
			writeLog(Level.INFO_INT, "単品仕入情報更新処理を終了します。");


			writeLog(Level.INFO_INT, "単品不稼動更新処理を開始します。");

			//NON-ACT⇒ACT
			strSql.delete(0,strSql.length());
			strSql.append("UPDATE R_TANPINTEN_TORIATUKAI T ");
			strSql.append("   SET NON_ACT_KB        = '" + mst007601_NonActKbDictionary.ACT.getCode() + "', ");
			strSql.append("       NON_ACT_SOSHIN_DT = '" + batchDate + "', ");
			strSql.append("       HENKO_DT          = '" + batchDate + "', ");
			strSql.append("       BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("       BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");
			strSql.append(" WHERE NON_ACT_KB = '" + mst007601_NonActKbDictionary.NON_ACT.getCode() + "' ");
			strSql.append("   AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("   AND EXISTS ");
			strSql.append("       (SELECT /*+ HASH_SJ */ ");
			strSql.append("               * ");
			strSql.append("          FROM R_SYOHIN ");
			strSql.append("         WHERE BUNRUI1_CD = T.BUNRUI1_CD ");
			strSql.append("           AND SYOHIN_CD  = T.SYOHIN_CD ");
			strSql.append("           AND YUKO_DT   >= '" + DateChanger.addMonth(batchDate,0 - (Integer.parseInt(fukadoJyogaiKikan))) + "' ");
			strSql.append("           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("           AND TEN_HACHU_ED_DT >= '" + batchDate + "' ");
			strSql.append("       ) ");
			iRec = db.executeUpdate(strSql.toString());

			writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタレコードを稼動に更新しました。");

			//ACT⇒NON-ACT
			strSql.delete(0,strSql.length());
			strSql.append("UPDATE R_TANPINTEN_TORIATUKAI T ");
			strSql.append("   SET NON_ACT_KB        = '" + mst007601_NonActKbDictionary.NON_ACT.getCode() + "', ");
			strSql.append("       NON_ACT_SOSHIN_DT = '" + batchDate + "', ");
			strSql.append("       HENKO_DT          = '" + batchDate + "', ");
			strSql.append("       BATCH_UPDATE_TS   = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
			strSql.append("       BATCH_UPDATE_ID   = '" + userLog.getJobId() + "' ");
			strSql.append(" WHERE NON_ACT_KB = '" + mst007601_NonActKbDictionary.ACT.getCode() + "' ");
			strSql.append("   AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("   AND COALESCE(SAISHIN_SHIIRE_DT, '00000000') <= '" + DateChanger.addMonth(batchDate, 0 - (Integer.parseInt(fukadoKikan))) + "'  ");
			strSql.append("   AND COALESCE(SAISHIN_URIAGE_DT, '00000000') <= '" + DateChanger.addMonth(batchDate, 0 - (Integer.parseInt(fukadoKikan))) + "'  ");
			strSql.append("   AND NOT EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_SYOHIN ");
			strSql.append("         WHERE BUNRUI1_CD = T.BUNRUI1_CD ");
			strSql.append("           AND SYOHIN_CD  = T.SYOHIN_CD ");
			strSql.append("           AND YUKO_DT   >= '" + DateChanger.addMonth(batchDate,0 - (Integer.parseInt(fukadoJyogaiKikan))) + "' ");
			strSql.append("           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("           AND TEN_HACHU_ED_DT >= '" + batchDate + "' ");
			strSql.append("       ) ");
			//2010.08.16 Y.Imai 新店舗不稼動判定考慮 Add (S)
			strSql.append("   AND EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_TENPO ");
			strSql.append("         WHERE TENPO_CD = T.TENPO_CD ");
			strSql.append("           AND KAITEN_DT   <= '" + DateChanger.addMonth(batchDate,0 - (Integer.parseInt(fukadoJyogaiKikan))) + "' ");
			strSql.append("           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("       ) ");
			//2010.08.16 Y.Imai 新店舗不稼動判定考慮 Add (E)

			iRec = db.executeUpdate(strSql.toString());

			writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタレコードを不稼動に更新しました。");
			writeLog(Level.INFO_INT, "単品不稼動更新処理を終了します。");

			iRec  = 0;
			iRec2 = 0;
			iRec3 = 0;
			iRec4 = 0;

			boolean syoriInsFg = false;

			writeLog(Level.INFO_INT, "商品情報更新処理を開始します。");

			strSql.delete(0,strSql.length());
			strSql.append("SELECT BUNRUI1_CD, ");
			strSql.append("       SYOHIN_CD, ");
			strSql.append("       YUKO_DT, ");
			strSql.append("       TEN_HACHU_ED_DT ");
			strSql.append("  FROM R_SYOHIN RS ");
			strSql.append(" WHERE YUKO_DT  <= '" + DateChanger.addMonth(batchDate,Integer.parseInt("-" + fukadoJyogaiKikan)) + "' ");
			strSql.append("   AND TEN_HACHU_ED_DT >= '" + batchDate + "' ");
			strSql.append("   AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("   AND YUKO_DT   = ");
			strSql.append("       (SELECT MAX(YUKO_DT) ");
			strSql.append("          FROM R_SYOHIN ");
			// #6620 DEL 2022.06.30 SIEU.D(S)
			// strSql.append("         WHERE BUNRUI1_CD = RS.BUNRUI1_CD ");
			// strSql.append("           AND SYOHIN_CD  = RS.SYOHIN_CD ");
			strSql.append("         WHERE SYOHIN_CD  = RS.SYOHIN_CD ");
			// #6620 DEL 2022.06.30 SIEU.D(E)
			strSql.append("       ) ");
			strSql.append("   AND NOT EXISTS ");
			strSql.append("       (SELECT * ");
			strSql.append("          FROM R_TANPINTEN_TORIATUKAI ");
			strSql.append("         WHERE BUNRUI1_CD = RS.BUNRUI1_CD ");
			strSql.append("           AND SYOHIN_CD  = RS.SYOHIN_CD ");
			strSql.append("           AND NON_ACT_KB = '" + mst007601_NonActKbDictionary.ACT.getCode() + "' ");
			strSql.append("           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() +"' ");
			strSql.append("       ) ");

			ResultSet rs = db.executeQuery(strSql.toString());

			while (rs.next()){

				if(!syoriInsFg){
					// 受付№取得
					uketsukeNo = MDWareSeq.nextValString("bat_uketsuke_no", userLog.getJobId());
					writeLog(Level.INFO_INT, "受付№を取得しました [" + uketsukeNo + "]");
					db.executeUpdate(getInsBatchSyoriKekka(uketsukeNo, msgMap.get("0532").toString()));
					syoriInsFg = true;

					psSyohin   = db.getPrepareStatement(getInsertSql());
					psGift     = db.getPrepareStatement(getInsertGiftsyohinSql());
					psKeiryoki = db.getPrepareStatement(getInsertKeiryokiSql());
					psHachu    = db.getPrepareStatement(getInsertHachuSql());
					psTrFukado = db.getPrepareStatement(getInsFukado_Hanteikekka(uketsukeNo));
				}

				bunrui1cd = rs.getString("BUNRUI1_CD");
				syohincd  = rs.getString("SYOHIN_CD");
				yukodt    = rs.getString("YUKO_DT");

				//商品マスタ
				psSyohin.setString(1, bunrui1cd);
				psSyohin.setString(2, syohincd);
				psSyohin.setString(3, yukodt);
				iRec += psSyohin.executeUpdate();

				//ギフト商品マスタ
				psGift.setString(1, bunrui1cd);
				psGift.setString(2, syohincd);
				psGift.setString(3, yukodt);
				iRec2 += psGift.executeUpdate();

				//計量器マスタ
				psKeiryoki.setString(1, bunrui1cd);
				psKeiryoki.setString(2, syohincd);
				psKeiryoki.setString(3, yukodt);
				iRec3 += psKeiryoki.executeUpdate();

				//商品発注納品基準日マスタ
				psHachu.setString(1, bunrui1cd);
				psHachu.setString(2, syohincd);
				psHachu.setString(3, yukodt);
				iRec4 += psHachu.executeUpdate();

				//TR不稼動判定結果
				seqNo++;
				psTrFukado.setInt(1, seqNo);
				psTrFukado.setString(2, bunrui1cd);
				psTrFukado.setString(3, syohincd);
				psTrFukado.setString(4, DateChanger.addDate(batchDate,  + SYORI_DAYS));
				psTrFukado.executeUpdate();
			}
			db.closeResultSet(rs);
			db.commit();

			writeLog(Level.INFO_INT, seqNo + "件のTR不稼動判定結果レコードを登録しました。");
			writeLog(Level.INFO_INT, iRec  + "件の商品マスタレコードを登録しました。");
			writeLog(Level.INFO_INT, iRec2 + "件の計量器マスタレコードを登録しました。");
			writeLog(Level.INFO_INT, iRec3 + "件のギフト商品マスタレコードを登録しました。");
			writeLog(Level.INFO_INT, iRec4 + "件の商品発注納品基準日マスタレコードを登録しました。");
			writeLog(Level.INFO_INT, "商品情報更新処理を終了します。");


			// ポータルアラームへの登録
			writeLog(Level.INFO_INT, "アラームへの登録処理を開始します。");
			PortalAlarmInterface alarm = new PortalAlarmInterface();
			String jobId = userLog.getJobId();
			iRec = 0;

			ResultSet rs4 = db.executeQuery(getAlarmTaisyo());
			while (rs4.next()) {
				String bunrui1Cd = rs4.getString("BUNRUI1_CD");
				String delKeyStr3 = "mst0031" + "/" + bunrui1Cd + "/" + batchDate;
				setAlarmMessage(alarm, "mst0031", bunrui1Cd, delKeyStr3, jobId);
				iRec = iRec + 1;
			}
			db.closeResultSet(rs4);
			writeLog(Level.INFO_INT, iRec + "件のアラーム情報を登録しました。");

			//アラーム作成済に変更
			db.executeUpdate(setUpdateTR(jobId));
			db.commit();
			writeLog(Level.INFO_INT, iRec + "件をアラーム登録済に変更しました。");

			writeLog(Level.INFO_INT, "アラームへの登録処理を終了します。");

			writeLog(Level.INFO_INT, "処理を終了します。");

		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();
			this.writeError(se);
			throw se;

		}catch( Exception e ){
			db.rollback();
			this.writeError(e);
			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/**
	 * テーブル商品マスタにをデータをインサート用SQL
	 * @return 用のSQL
	 */
	private String getInsertSql() throws SQLException{
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN (");
		sql.append("  BUNRUI1_CD,");
		sql.append("  SYOHIN_CD,");
		sql.append("  YUKO_DT,");
		sql.append("  SYSTEM_KB,");
		sql.append("  GYOSYU_KB,");
		sql.append("  SYOHIN_KB,");
		sql.append("  KETASU_KB,");
		sql.append("  BUNRUI2_CD,");
		sql.append("  BUNRUI5_CD,");
		sql.append("  HINMOKU_CD,");
		sql.append("  SYOHIN_2_CD,");
		sql.append("  ZAIKO_SYOHIN_CD,");
		sql.append("  JYOHO_SYOHIN_CD,");
		sql.append("  MAKER_CD,");
		sql.append("  HINMEI_KANJI_NA,");
		sql.append("  KIKAKU_KANJI_NA,");
		sql.append("  KIKAKU_KANJI_2_NA,");
		sql.append("  REC_HINMEI_KANJI_NA,");
		sql.append("  HINMEI_KANA_NA,");
		sql.append("  KIKAKU_KANA_NA,");
		sql.append("  KIKAKU_KANA_2_NA,");
		sql.append("  REC_HINMEI_KANA_NA,");
		sql.append("  SYOHIN_WIDTH_QT,");
		sql.append("  SYOHIN_HEIGHT_QT,");
		sql.append("  SYOHIN_DEPTH_QT,");
		sql.append("  E_SHOP_KB,");
		sql.append("  PB_KB,");
		sql.append("  SUBCLASS_CD,");
		sql.append("  HAIFU_CD,");
		sql.append("  ZEI_KB,");
		sql.append("  TAX_RATE_KB,");
		sql.append("  GENTANKA_VL,");
		// No.184 MSTB081 Add 2015.12.04 TAM.NM (S)
		sql.append("  GENTANKA_NUKI_VL,");
		// No.184 MSTB081 Add 2015.12.04 TAM.NM (E)
		sql.append("  BAITANKA_VL,");
		sql.append("  TOSYO_BAIKA_VL,");
		sql.append("  PRE_GENTANKA_VL,");
		sql.append("  PRE_BAITANKA_VL,");
		sql.append("  TOKUBETU_GENKA_VL,");
		sql.append("  MAKER_KIBO_KAKAKU_VL,");
		sql.append("  SIIRESAKI_CD,");
		sql.append("  DAIHYO_HAISO_CD,");
		sql.append("  TEN_SIIRESAKI_KANRI_CD,");
		sql.append("  SIIRE_HINBAN_CD,");
		sql.append("  HACYU_SYOHIN_KB,");
		sql.append("  HACYU_SYOHIN_CD,");
		sql.append("  EOS_KB,");
		sql.append("  HACHU_TANI_NA,");
		sql.append("  HANBAI_TANI,");
		sql.append("  HACHUTANI_IRISU_QT,");
		sql.append("  MAX_HACHUTANI_QT,");
		sql.append("  CASE_HACHU_KB,");
		sql.append("  BARA_IRISU_QT,");
		sql.append("  TEN_HACHU_ST_DT,");
		sql.append("  TEN_HACHU_ED_DT,");
		sql.append("  HANBAI_ST_DT,");
		sql.append("  HANBAI_ED_DT,");
		sql.append("  HANBAI_KIKAN_KB,");
		sql.append("  TEIKAN_KB,");
		sql.append("  SOBA_SYOHIN_KB,");
		sql.append("  NOHIN_KIGEN_KB,");
		sql.append("  NOHIN_KIGEN_QT,");
		sql.append("  BIN_1_KB,");
		sql.append("  HACHU_PATTERN_1_KB,");
		sql.append("  SIME_TIME_1_QT,");
		sql.append("  C_NOHIN_RTIME_1_KB,");
		sql.append("  TEN_NOHIN_RTIME_1_KB,");
		sql.append("  TEN_NOHIN_TIME_1_KB,");
		sql.append("  BIN_2_KB,");
		sql.append("  HACHU_PATTERN_2_KB,");
		sql.append("  SIME_TIME_2_QT,");
		sql.append("  C_NOHIN_RTIME_2_KB,");
		sql.append("  TEN_NOHIN_RTIME_2_KB,");
		sql.append("  TEN_NOHIN_TIME_2_KB,");
		sql.append("  BIN_3_KB,");
		sql.append("  HACHU_PATTERN_3_KB,");
		sql.append("  SIME_TIME_3_QT,");
		sql.append("  C_NOHIN_RTIME_3_KB,");
		sql.append("  TEN_NOHIN_RTIME_3_KB,");
		sql.append("  TEN_NOHIN_TIME_3_KB,");
		sql.append("  C_NOHIN_RTIME_KB,");
		sql.append("  YUSEN_BIN_KB,");
		sql.append("  F_BIN_KB,");
		sql.append("  BUTURYU_KB,");
		sql.append("  GOT_MUJYOKEN_FG,");
		sql.append("  GOT_START_MM,");
		sql.append("  GOT_END_MM,");
		sql.append("  HACHU_TEISI_KB,");
		sql.append("  CENTER_ZAIKO_KB,");
		sql.append("  NOHIN_SYOHIN_CD,");
		sql.append("  NYUKA_SYOHIN_CD,");
		sql.append("  NYUKA_SYOHIN_2_CD,");
		sql.append("  ITF_CD,");
		sql.append("  GTIN_CD,");
		sql.append("  VENDER_MAKER_CD,");
		sql.append("  ZAIKO_CENTER_CD,");
		sql.append("  ZAIKO_HACHU_SAKI,");
		sql.append("  CENTER_WEIGHT_QT,");
		sql.append("  PACK_WIDTH_QT,");
		sql.append("  PACK_HEIGTH_QT,");
		sql.append("  PACK_DEPTH_QT,");
		sql.append("  PACK_WEIGHT_QT,");
		sql.append("  CENTER_HACHUTANI_KB,");
		sql.append("  CENTER_HACHUTANI_QT,");
		sql.append("  CENTER_BARA_IRISU_QT,");
		sql.append("  CENTER_IRISU_QT,");
		sql.append("  CASE_IRISU_QT,");
		sql.append("  CENTER_IRISU_2_QT,");
		sql.append("  MIN_ZAIKOSU_QT,");
		sql.append("  MAX_ZAIKOSU_QT,");
		sql.append("  KIJUN_ZAIKOSU_QT,");
		sql.append("  MIN_ZAIKONISSU_QT,");
		sql.append("  MAX_ZAIKONISSU_QT,");
		sql.append("  CENTER_KYOYO_KB,");
		sql.append("  CENTER_KYOYO_DT,");
		sql.append("  CENTER_SYOMI_KIKAN_KB,");
		sql.append("  CENTER_SYOMI_KIKAN_DT,");
		sql.append("  TEN_GROUPNO_CD,");
		sql.append("  TC_JYOUHO_NA,");
		sql.append("  NOHIN_ONDO_KB,");
		sql.append("  YOKOMOTI_KB,");
		sql.append("  SHINAZOROE_KB,");
		sql.append("  HONBU_ZAI_KB,");
		sql.append("  TEN_ZAIKO_KB,");
		sql.append("  HANBAI_SEISAKU_KB,");
		sql.append("  HENPIN_NB,");
		sql.append("  HENPIN_GENKA_VL,");
		sql.append("  CGC_HENPIN_KB,");
		sql.append("  HANBAI_LIMIT_KB,");
		sql.append("  HANBAI_LIMIT_QT,");
		sql.append("  PLU_SEND_DT,");
		sql.append("  KEYPLU_FG,");
		sql.append("  PLU_KB,");
		sql.append("  SYUZEI_HOKOKU_KB,");
		sql.append("  SAKE_NAIYORYO_QT,");
		sql.append("  TAG_HYOJI_BAIKA_VL,");
		sql.append("  KESHI_BAIKA_VL,");
		sql.append("  YORIDORI_KB,");
		sql.append("  YORIDORI_VL,");
		sql.append("  YORIDORI_QT,");
		sql.append("  BLAND_CD,");
		sql.append("  SEASON_CD,");
		sql.append("  HUKUSYU_CD,");
		sql.append("  STYLE_CD,");
		sql.append("  SCENE_CD,");
		sql.append("  SEX_CD,");
		sql.append("  AGE_CD,");
		sql.append("  GENERATION_CD,");
		sql.append("  SOZAI_CD,");
		sql.append("  PATTERN_CD,");
		sql.append("  ORIAMI_CD,");
		sql.append("  HUKA_KINO_CD,");
		sql.append("  SODE_CD,");
		sql.append("  SIZE_CD,");
		sql.append("  COLOR_CD,");
		sql.append("  KEIYAKU_SU_QT,");
		sql.append("  KEIYAKU_PATTERN_KB,");
		sql.append("  KEIYAKU_ST_DT,");
		sql.append("  KEIYAKU_ED_DT,");
		sql.append("  KUMISU_KB,");
		sql.append("  NEFUDA_KB,");
		sql.append("  NEFUDA_UKEWATASI_DT,");
		sql.append("  NEFUDA_UKEWATASI_KB,");
		sql.append("  PC_KB,");
		sql.append("  DAISI_NO_NB,");
		sql.append("  UNIT_PRICE_TANI_KB,");
		sql.append("  UNIT_PRICE_NAIYORYO_QT,");
		sql.append("  UNIT_PRICE_KIJUN_TANI_QT,");
		sql.append("  SYOHI_KIGEN_KB,");
		sql.append("  SYOHI_KIGEN_DT,");
		sql.append("  DAICHO_TENPO_KB,");
		sql.append("  DAICHO_HONBU_KB,");
		sql.append("  DAICHO_SIIRESAKI_KB,");
		sql.append("  TANA_NO_NB,");
		sql.append("  DAN_NO_NB,");
		sql.append("  REBATE_FG,");
		sql.append("  MARK_GROUP_CD,");
		sql.append("  MARK_CD,");
		sql.append("  YUNYUHIN_KB,");
		sql.append("  SANTI_CD,");
		sql.append("  D_ZOKUSEI_1_NA,");
		sql.append("  S_ZOKUSEI_1_NA,");
		sql.append("  D_ZOKUSEI_2_NA,");
		sql.append("  S_ZOKUSEI_2_NA,");
		sql.append("  D_ZOKUSEI_3_NA,");
		sql.append("  S_ZOKUSEI_3_NA,");
		sql.append("  D_ZOKUSEI_4_NA,");
		sql.append("  S_ZOKUSEI_4_NA,");
		sql.append("  D_ZOKUSEI_5_NA,");
		sql.append("  S_ZOKUSEI_5_NA,");
		sql.append("  D_ZOKUSEI_6_NA,");
		sql.append("  S_ZOKUSEI_6_NA,");
		sql.append("  D_ZOKUSEI_7_NA,");
		sql.append("  S_ZOKUSEI_7_NA,");
		sql.append("  D_ZOKUSEI_8_NA,");
		sql.append("  S_ZOKUSEI_8_NA,");
		sql.append("  D_ZOKUSEI_9_NA,");
		sql.append("  S_ZOKUSEI_9_NA,");
		sql.append("  D_ZOKUSEI_10_NA,");
		sql.append("  S_ZOKUSEI_10_NA,");
		sql.append("  FUJI_SYOHIN_KB,");
		sql.append("  COMMENT_TX,");
		sql.append("  AUTO_DEL_KB,");
		sql.append("  MST_SIYOFUKA_KB,");
		sql.append("  HAIBAN_FG,");
		sql.append("  SINKI_TOROKU_DT,");
		sql.append("  HENKO_DT,");
		sql.append("  SYOKAI_TOROKU_TS,");
		sql.append("  SYOKAI_USER_ID,");
		sql.append("  INSERT_TS,");
		sql.append("  INSERT_USER_ID,");
		sql.append("  UPDATE_TS,");
		sql.append("  UPDATE_USER_ID,");
		sql.append("  BATCH_UPDATE_TS,");
		sql.append("  BATCH_UPDATE_ID,");
		sql.append("  DELETE_FG) ");
		sql.append("SELECT ");
		sql.append("  BUNRUI1_CD,");
		sql.append("  SYOHIN_CD,");
		sql.append("  '" + DateChanger.addDate(batchDate,  + SYORI_DAYS) + "', ");
		sql.append("  SYSTEM_KB,");
		sql.append("  GYOSYU_KB,");
		sql.append("  SYOHIN_KB,");
		sql.append("  KETASU_KB,");
		sql.append("  BUNRUI2_CD,");
		sql.append("  BUNRUI5_CD,");
		sql.append("  HINMOKU_CD,");
		sql.append("  SYOHIN_2_CD,");
		sql.append("  ZAIKO_SYOHIN_CD,");
		sql.append("  JYOHO_SYOHIN_CD,");
		sql.append("  MAKER_CD,");
		sql.append("  HINMEI_KANJI_NA,");
		sql.append("  KIKAKU_KANJI_NA,");
		sql.append("  KIKAKU_KANJI_2_NA,");
		sql.append("  REC_HINMEI_KANJI_NA,");
		sql.append("  HINMEI_KANA_NA,");
		sql.append("  KIKAKU_KANA_NA,");
		sql.append("  KIKAKU_KANA_2_NA,");
		sql.append("  REC_HINMEI_KANA_NA,");
		sql.append("  SYOHIN_WIDTH_QT,");
		sql.append("  SYOHIN_HEIGHT_QT,");
		sql.append("  SYOHIN_DEPTH_QT,");
		sql.append("  E_SHOP_KB,");
		sql.append("  PB_KB,");
		sql.append("  SUBCLASS_CD,");
		sql.append("  HAIFU_CD,");
		sql.append("  ZEI_KB,");
		sql.append("  TAX_RATE_KB,");
		sql.append("  GENTANKA_VL,");
		// No.184 MSTB081 Add 2015.12.04 TAM.NM (S)
		sql.append("  GENTANKA_NUKI_VL,");
		// No.184 MSTB081 Add 2015.12.04 TAM.NM (E)
		sql.append("  BAITANKA_VL,");
		sql.append("  TOSYO_BAIKA_VL,");
		sql.append("  PRE_GENTANKA_VL,");
		sql.append("  PRE_BAITANKA_VL,");
		sql.append("  TOKUBETU_GENKA_VL,");
		sql.append("  MAKER_KIBO_KAKAKU_VL,");
		sql.append("  SIIRESAKI_CD,");
		sql.append("  DAIHYO_HAISO_CD,");
		sql.append("  TEN_SIIRESAKI_KANRI_CD,");
		sql.append("  SIIRE_HINBAN_CD,");
		sql.append("  HACYU_SYOHIN_KB,");
		sql.append("  HACYU_SYOHIN_CD,");
		sql.append("  EOS_KB,");
		sql.append("  HACHU_TANI_NA,");
		sql.append("  HANBAI_TANI,");
		sql.append("  HACHUTANI_IRISU_QT,");
		sql.append("  MAX_HACHUTANI_QT,");
		sql.append("  CASE_HACHU_KB,");
		sql.append("  BARA_IRISU_QT,");
		sql.append("  TEN_HACHU_ST_DT,");
		sql.append("  '" + batchDate + "', ");
		sql.append("  HANBAI_ST_DT,");
		sql.append("  HANBAI_ED_DT,");
		sql.append("  HANBAI_KIKAN_KB,");
		sql.append("  TEIKAN_KB,");
		sql.append("  SOBA_SYOHIN_KB,");
		sql.append("  NOHIN_KIGEN_KB,");
		sql.append("  NOHIN_KIGEN_QT,");
		sql.append("  BIN_1_KB,");
		sql.append("  HACHU_PATTERN_1_KB,");
		sql.append("  SIME_TIME_1_QT,");
		sql.append("  C_NOHIN_RTIME_1_KB,");
		sql.append("  TEN_NOHIN_RTIME_1_KB,");
		sql.append("  TEN_NOHIN_TIME_1_KB,");
		sql.append("  BIN_2_KB,");
		sql.append("  HACHU_PATTERN_2_KB,");
		sql.append("  SIME_TIME_2_QT,");
		sql.append("  C_NOHIN_RTIME_2_KB,");
		sql.append("  TEN_NOHIN_RTIME_2_KB,");
		sql.append("  TEN_NOHIN_TIME_2_KB,");
		sql.append("  BIN_3_KB,");
		sql.append("  HACHU_PATTERN_3_KB,");
		sql.append("  SIME_TIME_3_QT,");
		sql.append("  C_NOHIN_RTIME_3_KB,");
		sql.append("  TEN_NOHIN_RTIME_3_KB,");
		sql.append("  TEN_NOHIN_TIME_3_KB,");
		sql.append("  C_NOHIN_RTIME_KB,");
		sql.append("  YUSEN_BIN_KB,");
		sql.append("  F_BIN_KB,");
		sql.append("  BUTURYU_KB,");
		sql.append("  GOT_MUJYOKEN_FG,");
		sql.append("  GOT_START_MM,");
		sql.append("  GOT_END_MM,");
		sql.append("  HACHU_TEISI_KB,");
		sql.append("  CENTER_ZAIKO_KB,");
		sql.append("  NOHIN_SYOHIN_CD,");
		sql.append("  NYUKA_SYOHIN_CD,");
		sql.append("  NYUKA_SYOHIN_2_CD,");
		sql.append("  ITF_CD,");
		sql.append("  GTIN_CD,");
		sql.append("  VENDER_MAKER_CD,");
		sql.append("  ZAIKO_CENTER_CD,");
		sql.append("  ZAIKO_HACHU_SAKI,");
		sql.append("  CENTER_WEIGHT_QT,");
		sql.append("  PACK_WIDTH_QT,");
		sql.append("  PACK_HEIGTH_QT,");
		sql.append("  PACK_DEPTH_QT,");
		sql.append("  PACK_WEIGHT_QT,");
		sql.append("  CENTER_HACHUTANI_KB,");
		sql.append("  CENTER_HACHUTANI_QT,");
		sql.append("  CENTER_BARA_IRISU_QT,");
		sql.append("  CENTER_IRISU_QT,");
		sql.append("  CASE_IRISU_QT,");
		sql.append("  CENTER_IRISU_2_QT,");
		sql.append("  MIN_ZAIKOSU_QT,");
		sql.append("  MAX_ZAIKOSU_QT,");
		sql.append("  KIJUN_ZAIKOSU_QT,");
		sql.append("  MIN_ZAIKONISSU_QT,");
		sql.append("  MAX_ZAIKONISSU_QT,");
		sql.append("  CENTER_KYOYO_KB,");
		sql.append("  CENTER_KYOYO_DT,");
		sql.append("  CENTER_SYOMI_KIKAN_KB,");
		sql.append("  CENTER_SYOMI_KIKAN_DT,");
		sql.append("  TEN_GROUPNO_CD,");
		sql.append("  TC_JYOUHO_NA,");
		sql.append("  NOHIN_ONDO_KB,");
		sql.append("  YOKOMOTI_KB,");
		sql.append("  SHINAZOROE_KB,");
		sql.append("  HONBU_ZAI_KB,");
		sql.append("  TEN_ZAIKO_KB,");
		sql.append("  HANBAI_SEISAKU_KB,");
		sql.append("  HENPIN_NB,");
		sql.append("  HENPIN_GENKA_VL,");
		sql.append("  CGC_HENPIN_KB,");
		sql.append("  HANBAI_LIMIT_KB,");
		sql.append("  HANBAI_LIMIT_QT,");
		sql.append("  PLU_SEND_DT,");
		sql.append("  KEYPLU_FG,");
		sql.append("  PLU_KB,");
		sql.append("  SYUZEI_HOKOKU_KB,");
		sql.append("  SAKE_NAIYORYO_QT,");
		sql.append("  TAG_HYOJI_BAIKA_VL,");
		sql.append("  KESHI_BAIKA_VL,");
		sql.append("  YORIDORI_KB,");
		sql.append("  YORIDORI_VL,");
		sql.append("  YORIDORI_QT,");
		sql.append("  BLAND_CD,");
		sql.append("  SEASON_CD,");
		sql.append("  HUKUSYU_CD,");
		sql.append("  STYLE_CD,");
		sql.append("  SCENE_CD,");
		sql.append("  SEX_CD,");
		sql.append("  AGE_CD,");
		sql.append("  GENERATION_CD,");
		sql.append("  SOZAI_CD,");
		sql.append("  PATTERN_CD,");
		sql.append("  ORIAMI_CD,");
		sql.append("  HUKA_KINO_CD,");
		sql.append("  SODE_CD,");
		sql.append("  SIZE_CD,");
		sql.append("  COLOR_CD,");
		sql.append("  KEIYAKU_SU_QT,");
		sql.append("  KEIYAKU_PATTERN_KB,");
		sql.append("  KEIYAKU_ST_DT,");
		sql.append("  KEIYAKU_ED_DT,");
		sql.append("  KUMISU_KB,");
		sql.append("  NEFUDA_KB,");
		sql.append("  NEFUDA_UKEWATASI_DT,");
		sql.append("  NEFUDA_UKEWATASI_KB,");
		sql.append("  PC_KB,");
		sql.append("  DAISI_NO_NB,");
		sql.append("  UNIT_PRICE_TANI_KB,");
		sql.append("  UNIT_PRICE_NAIYORYO_QT,");
		sql.append("  UNIT_PRICE_KIJUN_TANI_QT,");
		sql.append("  SYOHI_KIGEN_KB,");
		sql.append("  SYOHI_KIGEN_DT,");
		sql.append("  DAICHO_TENPO_KB,");
		sql.append("  DAICHO_HONBU_KB,");
		sql.append("  DAICHO_SIIRESAKI_KB,");
		sql.append("  TANA_NO_NB,");
		sql.append("  DAN_NO_NB,");
		sql.append("  REBATE_FG,");
		sql.append("  MARK_GROUP_CD,");
		sql.append("  MARK_CD,");
		sql.append("  YUNYUHIN_KB,");
		sql.append("  SANTI_CD,");
		sql.append("  D_ZOKUSEI_1_NA,");
		sql.append("  S_ZOKUSEI_1_NA,");
		sql.append("  D_ZOKUSEI_2_NA,");
		sql.append("  S_ZOKUSEI_2_NA,");
		sql.append("  D_ZOKUSEI_3_NA,");
		sql.append("  S_ZOKUSEI_3_NA,");
		sql.append("  D_ZOKUSEI_4_NA,");
		sql.append("  S_ZOKUSEI_4_NA,");
		sql.append("  D_ZOKUSEI_5_NA,");
		sql.append("  S_ZOKUSEI_5_NA,");
		sql.append("  D_ZOKUSEI_6_NA,");
		sql.append("  S_ZOKUSEI_6_NA,");
		sql.append("  D_ZOKUSEI_7_NA,");
		sql.append("  S_ZOKUSEI_7_NA,");
		sql.append("  D_ZOKUSEI_8_NA,");
		sql.append("  S_ZOKUSEI_8_NA,");
		sql.append("  D_ZOKUSEI_9_NA,");
		sql.append("  S_ZOKUSEI_9_NA,");
		sql.append("  D_ZOKUSEI_10_NA,");
		sql.append("  S_ZOKUSEI_10_NA,");
		sql.append("  FUJI_SYOHIN_KB,");
		sql.append("  COMMENT_TX,");
		sql.append("  AUTO_DEL_KB,");
		sql.append("  MST_SIYOFUKA_KB,");
		sql.append("  HAIBAN_FG,");
		sql.append("  SINKI_TOROKU_DT,");
		sql.append("  '" + batchDate + "', ");
		sql.append("  SYOKAI_TOROKU_TS,");
		sql.append("  SYOKAI_USER_ID,");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  DELETE_FG ");
		sql.append("FROM ");
		sql.append("  R_SYOHIN ");
		sql.append("WHERE ");
		sql.append("     BUNRUI1_CD = ? ");
		sql.append("AND  SYOHIN_CD  = ? ");
		sql.append("AND  YUKO_DT    = ? ");
		sql.append("AND  DELETE_FG 	= '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ


		return sql.toString();
	}


	/**
	 * テーブル商品マスタにをデータをインサート用SQL
	 * @return 用のSQL
	 */
	private String getInsertKeiryokiSql() throws SQLException{
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_KEIRYOKI (");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    SYOHIN_YOBIDASI, ");
		sql.append("    S_GYOSYU_CD, ");
		sql.append("    THEME_CD, ");
		sql.append("    KEIRYO_HANKU_CD, ");
		sql.append("    HANEI_DT, ");
		sql.append("    SYOHIN_KBN, ");
		sql.append("    KEIRYOKI_NA, ");
		sql.append("    KEIRYOKI2_NA, ");
		sql.append("    KEIRYOKI3_NA, ");
		sql.append("    RECEIPT_HINMEI_NA, ");
		sql.append("    TEIGAKU_UP_KB, ");
		sql.append("    TANKA_VL, ");
		sql.append("    TEIGAKU_VL, ");
		sql.append("    TEIGAKUJI_TANI_KB, ");
		sql.append("    SYOMIKIKAN_KB, ");
		sql.append("    SYOMIKIKAN_VL, ");
		sql.append("    SANTI_KB, ");
		sql.append("    KAKOJIKOKU_PRINT_KB, ");
		sql.append("    CHORIYO_KOKOKUBUN_KB, ");
		sql.append("    HOZON_ONDOTAI_KB, ");
		sql.append("    START_KB, ");
		sql.append("    BACK_LABEL_KB, ");
		sql.append("    EIYO_SEIBUN_NA, ");
		sql.append("    COMMENT_KB, ");
		sql.append("    BIKO_TX, ");
		sql.append("    GENZAIRYO_NA, ");
		sql.append("    TENKABUTU_NA, ");
		sql.append("    MIN_WEIGHT_QT, ");
		sql.append("    MAX_WEIGHT_QT, ");
		sql.append("    TEIKAN_WEIGHT_QT, ");
		sql.append("    FUTAI_WEIGHT_QT, ");
		sql.append("    EYE_CATCH_NO, ");
		sql.append("    EYE_CATCH_MODE, ");
		sql.append("    TEIGAKU_KB, ");
		sql.append("    TEIGAKU_BAIKA_VL, ");
		sql.append("    HOZON_ONDO_QT, ");
		sql.append("    CALORIE, ");
		sql.append("    TRAY_NA, ");
		sql.append("    NETSUKEKI_NA, ");
		sql.append("    NETSUKEKI_NA_2, ");
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG) ");
		sql.append("SELECT ");
		sql.append("    RK.BUNRUI1_CD, ");
		sql.append("    RK.SYOHIN_CD, ");
		sql.append("  '" + DateChanger.addDate(batchDate,  + SYORI_DAYS) + "', ");
		sql.append("    RK.SYOHIN_YOBIDASI, ");
		sql.append("    RK.S_GYOSYU_CD, ");
		sql.append("    RK.THEME_CD, ");
		sql.append("    RK.KEIRYO_HANKU_CD, ");
		sql.append("    RK.HANEI_DT, ");
		sql.append("    RK.SYOHIN_KBN, ");
		sql.append("    RK.KEIRYOKI_NA, ");
		sql.append("    RK.KEIRYOKI2_NA, ");
		sql.append("    RK.KEIRYOKI3_NA, ");
		sql.append("    RK.RECEIPT_HINMEI_NA, ");
		sql.append("    RK.TEIGAKU_UP_KB, ");
		sql.append("    RK.TANKA_VL, ");
		sql.append("    RK.TEIGAKU_VL, ");
		sql.append("    RK.TEIGAKUJI_TANI_KB, ");
		sql.append("    RK.SYOMIKIKAN_KB, ");
		sql.append("    RK.SYOMIKIKAN_VL, ");
		sql.append("    RK.SANTI_KB, ");
		sql.append("    RK.KAKOJIKOKU_PRINT_KB, ");
		sql.append("    RK.CHORIYO_KOKOKUBUN_KB, ");
		sql.append("    RK.HOZON_ONDOTAI_KB, ");
		sql.append("    RK.START_KB, ");
		sql.append("    RK.BACK_LABEL_KB, ");
		sql.append("    RK.EIYO_SEIBUN_NA, ");
		sql.append("    RK.COMMENT_KB, ");
		sql.append("    RK.BIKO_TX, ");
		sql.append("    RK.GENZAIRYO_NA, ");
		sql.append("    RK.TENKABUTU_NA, ");
		sql.append("    RK.MIN_WEIGHT_QT, ");
		sql.append("    RK.MAX_WEIGHT_QT, ");
		sql.append("    RK.TEIKAN_WEIGHT_QT, ");
		sql.append("    RK.FUTAI_WEIGHT_QT, ");
		sql.append("    RK.EYE_CATCH_NO, ");
		sql.append("    RK.EYE_CATCH_MODE, ");
		sql.append("    RK.TEIGAKU_KB, ");
		sql.append("    RK.TEIGAKU_BAIKA_VL, ");
		sql.append("    RK.HOZON_ONDO_QT, ");
		sql.append("    RK.CALORIE, ");
		sql.append("    RK.TRAY_NA, ");
		sql.append("    RK.NETSUKEKI_NA, ");
		sql.append("    RK.NETSUKEKI_NA_2, ");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("    RK.DELETE_FG ");
		sql.append("FROM ");
		sql.append("  R_KEIRYOKI RK ");
		sql.append("WHERE ");
		sql.append("     BUNRUI1_CD = ? ");
		sql.append("AND  SYOHIN_CD  = ? ");
		sql.append("AND  YUKO_DT    = ? ");
		sql.append("AND  DELETE_FG 	= '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ

		return sql.toString();
	}

	/**
	 * テーブル商品発注納品基準日マスタにをデータをインサート用SQL
	 * @return 用のSQL
	 */
	private String getInsertHachuSql() throws SQLException{
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN_HACHUNOHINKIJUNBI (");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    TENPO_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    HACHU_HOHO_KB, ");
		sql.append("    HACHU_MON_FG, ");
		sql.append("    HACHU_TUE_FG, ");
		sql.append("    HACHU_WED_FG, ");
		sql.append("    HACHU_THU_FG, ");
		sql.append("    HACHU_FRI_FG, ");
		sql.append("    HACHU_SAT_FG, ");
		sql.append("    HACHU_SUN_FG, ");
		sql.append("    RTIME_MON_KB, ");
		sql.append("    RTIME_TUE_KB, ");
		sql.append("    RTIME_WED_KB, ");
		sql.append("    RTIME_THU_KB, ");
		sql.append("    RTIME_FRI_KB, ");
		sql.append("    RTIME_SAT_KB, ");
		sql.append("    RTIME_SUN_KB, ");
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    TENPO_CD, ");
		sql.append("  '" + DateChanger.addDate(batchDate,  + SYORI_DAYS) + "', ");
		sql.append("    HACHU_HOHO_KB, ");
		sql.append("    HACHU_MON_FG, ");
		sql.append("    HACHU_TUE_FG, ");
		sql.append("    HACHU_WED_FG, ");
		sql.append("    HACHU_THU_FG, ");
		sql.append("    HACHU_FRI_FG, ");
		sql.append("    HACHU_SAT_FG, ");
		sql.append("    HACHU_SUN_FG, ");
		sql.append("    RTIME_MON_KB, ");
		sql.append("    RTIME_TUE_KB, ");
		sql.append("    RTIME_WED_KB, ");
		sql.append("    RTIME_THU_KB, ");
		sql.append("    RTIME_FRI_KB, ");
		sql.append("    RTIME_SAT_KB, ");
		sql.append("    RTIME_SUN_KB, ");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("    DELETE_FG ");
		sql.append("FROM ");
		sql.append("  R_SYOHIN_HACHUNOHINKIJUNBI ");
		sql.append("WHERE ");
		sql.append("     BUNRUI1_CD = ? ");
		sql.append("AND  SYOHIN_CD  = ? ");
		sql.append("AND  YUKO_DT    = ? ");
		sql.append("AND  DELETE_FG 	= '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ

		return sql.toString();
	}

	/**
	 * テーブル商品マスタにをデータをインサート用SQL
	 * @return 用のSQL
	 */
	private String getInsertGiftsyohinSql() throws SQLException{
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_GIFT_SYOHIN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");
		sql.append("    SYOHIN_CD, ");
		sql.append("    YUKO_DT, ");
		sql.append("    TSUBAN, ");
		sql.append("    GIFT_CD, ");
		sql.append("    WARIBIKI_QT, ");
		sql.append("    POINT_QT, ");
		sql.append("    SYOHIN_COMMENT_TX, ");
		sql.append("    MEISAI_TX, ");
		sql.append("    KAZEI_KB, ");
		sql.append("    KEIYU_KB, ");
		sql.append("    DENPYO_KB, ");
		sql.append("    SORYO_KB, ");
		sql.append("    GIFT_HASSOMOTO_CD, ");
		sql.append("    HAISO_KB, ");
		sql.append("    JUSHIN_START_DT, ");
		sql.append("    JUSHIN_END_DT, ");
		sql.append("    HAISO_START_DT, ");
		sql.append("    HAISO_END_DT, ");
		sql.append("    INSERT_TS, ");
		sql.append("    INSERT_USER_ID, ");
		sql.append("    UPDATE_TS, ");
		sql.append("    UPDATE_USER_ID, ");
		sql.append("    BATCH_UPDATE_TS, ");
		sql.append("    BATCH_UPDATE_ID, ");
		sql.append("    DELETE_FG ");
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    RG.BUNRUI1_CD, ");
		sql.append("    RG.SYOHIN_CD, ");
		sql.append("  '" + DateChanger.addDate(batchDate,  + SYORI_DAYS) + "', ");
		sql.append("    RG.TSUBAN, ");
		sql.append("    RG.GIFT_CD, ");
		sql.append("    RG.WARIBIKI_QT, ");
		sql.append("    RG.POINT_QT, ");
		sql.append("    RG.SYOHIN_COMMENT_TX, ");
		sql.append("    RG.MEISAI_TX, ");
		sql.append("    RG.KAZEI_KB, ");
		sql.append("    RG.KEIYU_KB, ");
		sql.append("    RG.DENPYO_KB, ");
		sql.append("    RG.SORYO_KB, ");
		sql.append("    RG.GIFT_HASSOMOTO_CD, ");
		sql.append("    RG.HAISO_KB, ");
		sql.append("    RG.JUSHIN_START_DT, ");
		sql.append("    RG.JUSHIN_END_DT, ");
		sql.append("    RG.HAISO_START_DT, ");
		sql.append("    RG.HAISO_END_DT, ");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  '" + userLog.getJobId() + "',");
		sql.append("    RG.DELETE_FG ");
		sql.append("FROM ");
		sql.append("  R_GIFT_SYOHIN RG ");
		sql.append("WHERE ");
		sql.append("     BUNRUI1_CD = ? ");
		sql.append("AND  SYOHIN_CD  = ? ");
		sql.append("AND  YUKO_DT    = ? ");
		sql.append("AND  DELETE_FG 	= '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ


		return sql.toString();
	}

	/**
	 * バッチ処理結果TRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private String getInsBatchSyoriKekka(String uketsukeNo, String msg) throws SQLException
	{

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO TR_BATCH_SYORIKEKKA (");
		sql.append("    SYORI_DT, ");																		// 処理日
		sql.append("    SYORI_SYUBETSU_KB, ");																// 処理種別区分
		sql.append("    UKETSUKE_NO, ");																	// 受付№
		sql.append("    MESSAGE_TX, ");																		// メッセージ内容
		sql.append("    INSERT_TS, ");																		// 作成年月日
		sql.append("    INSERT_USER_ID, ");																	// 作成者ID
		sql.append("    UPDATE_TS, ");																		// 更新年月日
		sql.append("    UPDATE_USER_ID, ");																	// 更新者ID
		sql.append("    DELETE_FG ");																		// 削除フラグ
		sql.append(") VALUES (");
		sql.append("    '" + batchDate + "', ");															// 処理日
		sql.append("    '" + mst010001_SyoriSyubetsuKbDictionary.MST_FUKADO_CHK.getCode() + "', ");			// 処理種別区分
		sql.append("    " + uketsukeNo + ", ");																// 受付№
		sql.append("    '" + msg + "', ");																	// メッセージ内容
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + userLog.getJobId() + "',");													// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + userLog.getJobId() + "',");													// 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ
		sql.append(") ");

		return sql.toString();
	}

	/**
	 * 不稼動判定結果TRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private String getInsFukado_Hanteikekka(String uketsukeNo) throws SQLException
	{

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO TR_FUKADO_HANTEIKEKKA(");
		sql.append("  SYORI_DT,");
		sql.append("  UKETSUKE_NO,");
		sql.append("  UKETSUKE_SEQ,");
		sql.append("  BUNRUI1_CD,");
		sql.append("  SYOHIN_CD,");
		sql.append("  YUKO_DT,");
		sql.append("  BUNRUI2_CD,");
		sql.append("  BUNRUI5_CD,");
		sql.append("  GENTANKA_VL,");
		// No.184 MSTB081 Add 2015.11.26 TAM.NM (S)
		sql.append("  GENTANKA_NUKI_VL,");
		// No.184 MSTB081 Add 2015.11.26 TAM.NM (E)
		sql.append("  BAITANKA_VL,");
		sql.append("  SIIRESAKI_CD,");
		sql.append("  HANBAI_ST_DT,");
		sql.append("  HANBAI_ED_DT,");
		sql.append("  TEN_HACHU_ST_DT,");
		sql.append("  TEN_HACHU_ED_DT,");
		sql.append("  ALARM_MAKE_FG,");
		sql.append("  INSERT_TS,");
		sql.append("  INSERT_USER_ID,");
		sql.append("  UPDATE_TS,");
		sql.append("  UPDATE_USER_ID,");
		sql.append("  DELETE_FG) ");
		sql.append("SELECT ");
		sql.append("  '" + batchDate + "',");
		sql.append("  " + uketsukeNo + ",");
		sql.append("  ?,");
		sql.append("  BUNRUI1_CD,");
		sql.append("  SYOHIN_CD,");
		sql.append("  YUKO_DT,");
		sql.append("  BUNRUI2_CD,");
		sql.append("  BUNRUI5_CD,");
		sql.append("  GENTANKA_VL,");
		// No.184 MSTB081 Add 2015.11.26 TAM.NM (S)
        sql.append("  GENTANKA_NUKI_VL,");
        // No.184 MSTB081 Add 2015.11.26 TAM.NM (E)
		sql.append("  BAITANKA_VL,");
		sql.append("  SIIRESAKI_CD,");
		sql.append("  HANBAI_ST_DT,");
		sql.append("  HANBAI_ED_DT,");
		sql.append("  TEN_HACHU_ST_DT,");
		sql.append("  TEN_HACHU_ED_DT,");
		sql.append(" '0', ");
		sql.append(" '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
		sql.append("	   '" + userLog.getJobId() + "', ");
		sql.append(" '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") +"', ");
		sql.append("	   '" + userLog.getJobId() + "', ");
		sql.append(" '"+ mst000801_DelFlagDictionary.INAI.getCode()  +"' ");
		sql.append("FROM ");
		sql.append("  R_SYOHIN ");
		sql.append("WHERE ");
		sql.append("     BUNRUI1_CD = ? ");
		sql.append("AND  SYOHIN_CD  = ? ");
		sql.append("AND  YUKO_DT    = ? ");

		return sql.toString();
	}


	/**
	 * アラームへの登録対象を取得する。
	 * @param  なし
	 * @return なし
	 */
	private String getAlarmTaisyo() {

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT TRIM(RS.BUNRUI1_CD) AS BUNRUI1_CD ");
		sb.append("  FROM TR_FUKADO_HANTEIKEKKA RS ");
		sb.append(" WHERE SYORI_DT      = '" + batchDate + "'");
		sb.append("   AND ALARM_MAKE_FG = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' ");
		sb.append(" GROUP BY ");
		sb.append("       RS.BUNRUI1_CD ");
		sb.append(" ORDER BY ");
		sb.append("       BUNRUI1_CD");

		return sb.toString();
	}

	/**
	 * アラーム作成フラグ「１」更新用SQL
	 * @param	String mstNa			テーブル名
	 * @throws SQLException
	 */
	private String setUpdateTR(String jobId) throws SQLException {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE TR_FUKADO_HANTEIKEKKA ");
		sb.append("   SET ALARM_MAKE_FG  = '" + mst006901_AlarmMakeFgDictionary.ZUMI.getCode() + "', ");
		sb.append("       UPDATE_TS      = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sb.append("       UPDATE_USER_ID = '"+ jobId +"'");
		sb.append(" WHERE ALARM_MAKE_FG  = '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "' ");

		return sb.toString();
	}


	/**
	 * アラーム情報を登録する。
	 * @param  alarm
	 * @param  strAlarmId アラームID
	 * @param  strDelKeyStr String
	 * @return なし
	 */
	private void setAlarmMessage(PortalAlarmInterface alarm, String strAlarmId, String strBumonCd, String DelKeyStr, String strJobId) {

		// アラーム情報登録クラス
		DtAlarmBean alarmBean = new DtAlarmBean();
		String strProcess = null ;

		// アラーム区分
		alarmBean.setAlarmKb(PortalAlarmKbDic.BUYERMUKE.getCode());
		// 送信先区分
		alarmBean.setDestinationBumonCd(strBumonCd);
		// 送信先ユザID
		alarmBean.setDestinationUserId(null);
		// url_tx
//		alarmBean.setUrlTx("app?JobID=mst160101_SyohinIkkatuMeneteInit");
		alarmBean.setUrlTx(null);
		// del_key_tx
		alarmBean.setDelKeyTx(DelKeyStr);
		// アラームID
		alarmBean.setAlarmId(strAlarmId);
		// 作成者ID
		alarmBean.setInsertUserId(strJobId);
		// 処理区分
		strProcess = AlarmProcessKbDic.BATCH.getCode();

		alarm.setAlarmMessage(alarmBean, strProcess);
	}


	/**
	 * ユーザーログとバッチログにログを出力します。
	 *
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
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
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
}