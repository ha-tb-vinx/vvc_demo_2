package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:新店用マスタ作成処理</p>
 * <p>説明:新店用のマスタを作成します。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @author VINX
 * @version 1.00 (2017.09.12) S.Nakazato 新規作成
 */

public class MB380C09_CreateSintenMst {

	private MasterDataBase dataBase = null;
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private final String BATCH_ID = "MB38-0C-09";
	private final String BATCH_NA = "新店用マスタ作成";

	// PreparedStatement
	private PreparedStatement SaibanMerge = null;

	// テーブル名
	private final String R_LIST_DENPYO_NO_SAIBAN_JISEKI = "R_DENPYO_NO_SAIBAN_JISEKI";
	private final String R_LIST_DENPYO_NO_SAIBAN_SHIIRE = "R_DENPYO_NO_SAIBAN_SHIIRE";
	private final String R_LIST_DENPYO_NO_SAIBAN_URIAGE = "R_DENPYO_NO_SAIBAN_URIAGE";
	private final String R_RECIPE_ID_SAIBAN = "R_RECIPE_ID_SAIBAN";
	private final String H_R_TENPO = "H_R_TENPO";
	private final String R_FAST_DENPYO_NO_SAIBAN = "R_FAST_DENPYO_NO_SAIBAN";

	// パラメータ
	private final String LIST_DENPYO_SAIBAN_KB_JISEKI = "LIST_DENPYO_SAIBAN_KB_JISEKI"; 
	private final String LIST_DENPYO_SAIBAN_KB_SHIIRE = "LIST_DENPYO_SAIBAN_KB_SHIIRE"; 
	private final String LIST_DENPYO_SAIBAN_KB_URIAGE = "LIST_DENPYO_SAIBAN_KB_URIAGE"; 
	private final String RECIPE_ID_SAIBAN_KB = "RECIPE_ID_SAIBAN_KB"; 
	private final String FAST_DENPYO_SAIBAN_KB_SHIIRE = "FAST_DENPYO_SAIBAN_KB_SHIIRE";
	private final String LIST_DENPYO_SAIBAN_INIT_START_NB = "LIST_DENPYO_SAIBAN_INIT_START_NB"; 
	private final String LIST_DENPYO_SAIBAN_INIT_END_NB = "LIST_DENPYO_SAIBAN_INIT_END_NB"; 
	private final String LIST_DENPYO_SAIBAN_INIT_NOW_NB = "LIST_DENPYO_SAIBAN_INIT_NOW_NB";
	private final String RECIPE_ID_SAIBAN_INIT_START_NB = "RECIPE_ID_SAIBAN_INIT_START_NB";
	private final String RECIPE_ID_SAIBAN_INIT_END_NB = "RECIPE_ID_SAIBAN_INIT_END_NB";
	private final String RECIPE_ID_SAIBAN_INIT_NOW_NB = "RECIPE_ID_SAIBAN_INIT_NOW_NB";
	private final String FAST_DENPYO_SAIBAN_INIT_START_NB = "FAST_DENPYO_SAIBAN_INIT_START_NB";
	private final String FAST_DENPYO_SAIBAN_INIT_END_NB = "FAST_DENPYO_SAIBAN_INIT_END_NB";
	private final String FAST_DENPYO_SAIBAN_INIT_NOW_NB = "FAST_DENPYO_SAIBAN_INIT_NOW_NB";

	private String TANPINTEN_KB = null; //単品店舗区分
	private String SHINTEN_CD = null; //新店対象

	//マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();

	private int waitTime = 0;
	private int retryCnt = 0;

	/**
	 * コンストラクタ
	 */
	public MB380C09_CreateSintenMst() {
		dataBase = new MasterDataBase("rbsite_ora");
	}

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB380C09_CreateSintenMst(MasterDataBase db) {
		this.dataBase = db;
		if (db == null) {
			this.dataBase = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @param waitTime ウエイト時間
	 * @param retryCnt リトライ回数
	 * @throws Exception 例外
	 */
	public void execute(String batchId,String waitTime,String retryCnt) throws Exception {
		batchID = batchId;
		this.waitTime = Integer.parseInt(waitTime);
		this.retryCnt = Integer.parseInt(retryCnt);
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

			// ▼単品店取扱マスタ作成▼ ======================================
			int cnt = 0;
			//単品店舗区分を取得
			writeLog(Level.INFO_INT, "単品店舗区分の取得処理開始");
			Map tenpoKbMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.TANPINTEN_CREATE_TENPO_KB);
			TANPINTEN_KB = SqlSupportUtil.createInString(tenpoKbMap.keySet().toArray());
			writeLog(Level.INFO_INT, "単品店舗区分は【" + TANPINTEN_KB + "】です。");
			writeLog(Level.INFO_INT, "単品店舗区分の取得処理終了");

			String TenpoTaisyoSQL = null;
			//新店対象の店舗コードを取得
			writeLog(Level.INFO_INT, "新店対象の店舗コード取得処理開始");
			ResultSet rsShitenCd = null;
			TenpoTaisyoSQL = getTenpoTaisyoSQL();
			rsShitenCd = dataBase.executeQuery(TenpoTaisyoSQL);
			while (rsShitenCd.next()) {
				SHINTEN_CD = rsShitenCd.getString("TENPO_CD");
			}
			dataBase.closeResultSet(rsShitenCd);
			writeLog(Level.INFO_INT, "新店対象の店舗は"+ SHINTEN_CD + "です。");
			writeLog(Level.INFO_INT, "新店対象の店舗コード取得処理終了");
			
			String TanpinToriatukaiMrgSQL = null;
			//単品店取扱マスタの追加
			writeLog(Level.INFO_INT, "単品店取扱マスタの追加（新店のみ）処理開始");
			TanpinToriatukaiMrgSQL = getTanpinToriatukaiMrgSQL();
			cnt = dataBase.executeUpdate(TanpinToriatukaiMrgSQL);
			writeLog(Level.INFO_INT, "単品店取扱マスタを"+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "単品店取扱マスタの追加（新店のみ）処理終了");
			// ▲単品店取扱マスタ作成▲ ======================================


			// ▼伝票番号採番マスタ作成▼ ======================================
			Map startNbMap = ResorceUtil.getInstance().getPropertieMap(LIST_DENPYO_SAIBAN_INIT_START_NB);
			Map endNbMap = ResorceUtil.getInstance().getPropertieMap(LIST_DENPYO_SAIBAN_INIT_END_NB);
			Map nowNbMap = ResorceUtil.getInstance().getPropertieMap(LIST_DENPYO_SAIBAN_INIT_NOW_NB);

			// 発注用採番区分・採番番号を取得
			List<SaibanBean> jisekiSaibanList = getSaibanList(startNbMap, endNbMap, nowNbMap, LIST_DENPYO_SAIBAN_KB_JISEKI);
			// 仕入用採番区分・採番番号を取得
			List<SaibanBean> shiireSaibanList = getSaibanList(startNbMap, endNbMap, nowNbMap, LIST_DENPYO_SAIBAN_KB_SHIIRE);
			// 売上用採番区分・採番番号を取得
			List<SaibanBean> uriageSaibanList = getSaibanList(startNbMap, endNbMap, nowNbMap, LIST_DENPYO_SAIBAN_KB_URIAGE);

			// PreparedStatement
			SaibanMerge = getSaibanMstMrgSQL(R_LIST_DENPYO_NO_SAIBAN_JISEKI, dataBase);
			// 発注：伝票採番マスタ作成
			writeLog(Level.INFO_INT, "発注：伝票採番マスタの追加　処理開始");
			cnt = this.execSaibanMerge(SaibanMerge, jisekiSaibanList);
			writeLog(Level.INFO_INT, "発注：伝票採番マスタ "+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "発注：伝票採番マスタの追加　処理終了");
			SaibanMerge.close();
			// 仕入：伝票採番マスタ作成
			SaibanMerge = getSaibanMstMrgSQL(R_LIST_DENPYO_NO_SAIBAN_SHIIRE, dataBase);
			writeLog(Level.INFO_INT, "仕入：伝票採番マスタの追加　処理開始");
			cnt = this.execSaibanMerge(SaibanMerge, shiireSaibanList);
			writeLog(Level.INFO_INT, "仕入：伝票採番マスタ "+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "仕入：伝票採番マスタの追加　処理終了");
			SaibanMerge.close();
			// 売上：伝票採番マスタ作成
			SaibanMerge = getSaibanMstMrgSQL(R_LIST_DENPYO_NO_SAIBAN_URIAGE, dataBase);
			writeLog(Level.INFO_INT, "売上：伝票採番マスタの追加　処理開始");
			cnt = this.execSaibanMerge(SaibanMerge, uriageSaibanList);
			writeLog(Level.INFO_INT, "売上：伝票採番マスタ "+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "売上：伝票採番マスタの追加　処理終了");
			SaibanMerge.close();
			// ▲伝票番号採番マスタ作成▲ ======================================


			// ▼レシピID採番マスタ作成▼ ======================================
			Map startNbMapR = ResorceUtil.getInstance().getPropertieMap(RECIPE_ID_SAIBAN_INIT_START_NB);
			Map endNbMapR = ResorceUtil.getInstance().getPropertieMap(RECIPE_ID_SAIBAN_INIT_END_NB);
			Map nowNbMapR = ResorceUtil.getInstance().getPropertieMap(RECIPE_ID_SAIBAN_INIT_NOW_NB);

			// レシピ用採番区分・採番番号を取得
			List<SaibanBean> recipeSaibanList = getSaibanList(startNbMapR, endNbMapR, nowNbMapR, RECIPE_ID_SAIBAN_KB);
			SaibanMerge = getSaibanMstMrgSQL(R_RECIPE_ID_SAIBAN, dataBase);
			// レシピ：伝票採番マスタ作成
			writeLog(Level.INFO_INT, "レシピID：採番マスタの追加　処理開始");
			cnt = this.execSaibanMerge(SaibanMerge, recipeSaibanList);
			writeLog(Level.INFO_INT, "レシピID：採番マスタ "+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "レシピID：採番マスタの追加　処理終了");
			SaibanMerge.close();
			// ▲レシピID採番マスタ作成▲ ======================================


			// ▼店舗新旧変換表マスタ作成▼ ======================================
			String HTenpoMrgSQL = null;
			//店舗新旧変換表マスタの追加
			writeLog(Level.INFO_INT, "店舗新旧変換表マスタの追加（新店のみ）処理開始");
			HTenpoMrgSQL = getHTenpoMrgSQL();
			cnt = dataBase.executeUpdate(HTenpoMrgSQL);
			writeLog(Level.INFO_INT, "店舗新旧変換表マスタを"+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "店舗新旧変換表マスタの追加（新店のみ）処理終了");
			// ▲店舗新旧変換表マスタ作成▲ ======================================


			// ▼FAST伝票採番マスタ作成▼ ======================================
			Map startNbMapF = ResorceUtil.getInstance().getPropertieMap(FAST_DENPYO_SAIBAN_INIT_START_NB);
			Map endNbMapF = ResorceUtil.getInstance().getPropertieMap(FAST_DENPYO_SAIBAN_INIT_END_NB);
			Map nowNbMapF = ResorceUtil.getInstance().getPropertieMap(FAST_DENPYO_SAIBAN_INIT_NOW_NB);

			// FAST伝票採番区分・採番番号を取得
			List<SaibanBean> fastSaibanList = getSaibanList(startNbMapF, endNbMapF, nowNbMapF, FAST_DENPYO_SAIBAN_KB_SHIIRE);
			SaibanMerge = getSaibanMstMrgSQL(R_FAST_DENPYO_NO_SAIBAN, dataBase);
			// FAST：伝票採番マスタ作成
			writeLog(Level.INFO_INT, "FAST伝票：採番マスタの追加　処理開始");
			cnt = this.execSaibanMerge(SaibanMerge, fastSaibanList);
			writeLog(Level.INFO_INT, "FAST伝票：採番マスタ "+ cnt + "件、追加しました。");
			writeLog(Level.INFO_INT, "FAST伝票：採番マスタの追加　処理終了");
			SaibanMerge.close();
			// ▲FAST伝票採番マスタ作成▲ ======================================


			dataBase.commit();

		} catch (SQLException se) {
			dataBase.rollback();
			writeError(se);
		throw se;
		} catch (Exception e) {
			dataBase.rollback();
			writeError(e);
			throw e;
		} finally {
			dataBase.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * 採番区分のListを返却する<br>
	 *
	 * @param startNbMap
	 * @param endNbMap
	 * @param nowNbMap
	 * @param paraID
	 * @return 採番区分のList
	 */
	private List<SaibanBean> getSaibanList (Map startNbMap, Map endNbMap, Map nowNbMap, String paraID) throws Exception  {

		Map saibanKbMap = ResorceUtil.getInstance().getPropertieMap(paraID);
		List<SaibanBean> sabanList = new ArrayList<SaibanBean>();

		for (Iterator ite = saibanKbMap.entrySet().iterator(); ite.hasNext();) {
			Map.Entry tmpSaibanKbMap = (Map.Entry) ite.next();
			SaibanBean bean = new SaibanBean();
			bean.setSaibanKb(nullToEmpty(tmpSaibanKbMap.getKey()).toString());
			bean.setStartNb(nullToEmpty(startNbMap.get(tmpSaibanKbMap.getKey())).toString());
			bean.setEndNb(nullToEmpty(endNbMap.get(tmpSaibanKbMap.getKey())).toString());
			bean.setNowNb(nullToEmpty(nowNbMap.get(tmpSaibanKbMap.getKey())).toString());

			// ディクショナリコントロール不備の場合、Listに追加しない
			if (bean.getSaibanKb().isEmpty() || bean.getStartNb().isEmpty() || bean.getEndNb().isEmpty() || bean.getNowNb().isEmpty()) {
				continue;
			}
			sabanList.add(bean);
		}
		return sabanList;
	} 

	/**
	 * PreparedStatementを実行する<br>
	 *
	 * @param startNbMap
	 * @param endNbMap
	 * @param nowNbMap
	 * @param paraID
	 * @return 採番区分のList
	 */
	private int execSaibanMerge(PreparedStatement pstmt, List<SaibanBean> saibanList) throws IllegalArgumentException, Exception {
		int cnt = 0;
		try {
			for (SaibanBean listRowBean : saibanList) {
				int idx = 0;
				// 採番区分
				idx++;
				pstmt.setString(idx, listRowBean.getSaibanKb());
				// 開始番号
				idx++;
				pstmt.setString(idx, listRowBean.getStartNb());
				// 終了番号
				idx++;
				pstmt.setString(idx, listRowBean.getEndNb());
				// 現在番号
				idx++;
				pstmt.setString(idx, listRowBean.getNowNb());

				// SQLの実行
				cnt += pstmt.executeUpdate();
			}
			return cnt;
		} catch (SQLException e) {
			//ロールバック
			dataBase.rollback();
			batchLog.getLog().fatal(BATCH_ID, BATCH_NA, "伝票採番号マスタの登録に失敗しました。");
			userLog.fatal("伝票採番号マスタの登録に失敗しました。");
			throw e;
		}
	}

	/**
	 * 処理対象となるデータを取得するSQL生成
	 * @throws Exception
	 */
	private String getTenpoTaisyoSQL() throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   LISTAGG(RT.TENPO_CD , ',') WITHIN GROUP (ORDER BY NULL) TENPO_CD ");
		sql.append(" FROM ");
		sql.append("   R_TENPO RT ");
		sql.append(" WHERE ");
		sql.append("   ( ");
		sql.append("     RT.TENPO_KB IN (" + TANPINTEN_KB + ") ");
		sql.append("     OR RT.TENPO_CD IN ('') ");
		sql.append("   ) ");
		sql.append("   AND COALESCE(HEITEN_DT, '99999999') >= '" + MasterDT + "' ");
		sql.append("   AND RT.DELETE_FG = '0' ");
		sql.append("   AND NOT EXISTS ( ");
		sql.append("     SELECT");
		sql.append("       1 ");
		sql.append("     FROM");
		sql.append("       R_TANPINTEN_TORIATUKAI RTT ");
		sql.append("     WHERE ");
		sql.append("       RTT.TENPO_CD = RT.TENPO_CD ");
		sql.append("       AND RTT.SINKI_TOROKU_DT < '" + MasterDT + "' ");
		sql.append("   ) ");
	
		return sql.toString();
	}

	/**
	 * 処理対象となるデータを取得するSQL生成
	 * @throws Exception
	 */
	private String getTanpinToriatukaiMrgSQL() throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append(" MERGE /*+ ORDERED USE_NL(WK RTT) */ ");
		sql.append(" INTO R_TANPINTEN_TORIATUKAI RTT  ");
		sql.append("   USING (  ");
		sql.append("     SELECT ");
		sql.append("       RS.BUNRUI1_CD AS BUNRUI1_CD ");
		sql.append("       , RS.SYOHIN_CD AS SYOHIN_CD ");
		sql.append("       , RT.TENPO_CD AS TENPO_CD ");
		sql.append("       , '" + MasterDT + "' AS DONYU_ST_DT ");
		sql.append("       , '99999999' AS DONYU_ED_DT ");
		sql.append("       , '1' AS NON_ACT_KB ");
		sql.append("       , NULL AS NON_ACT_SOSHIN_DT ");
		sql.append("       , NULL AS SAISHIN_HACYU_DT ");
		sql.append("       , NULL AS SAISHIN_SHIIRE_DT ");
		sql.append("       , NULL AS SAISHIN_URIAGE_DT ");
		sql.append("       , '1' AS HASEIMOTO_KB ");
		sql.append("       , NULL AS TANAWARI_PATERN ");
		sql.append("       , NULL AS JUKI_NB ");
		sql.append("       , NULL AS TANA_NB ");
		sql.append("       , NULL AS FACE_QT ");
		sql.append("       , NULL AS MIN_TINRETU_QT ");
		sql.append("       , NULL AS MAX_TINRETU_QT ");
		sql.append("       , NULL AS BASE_ZAIKO_NISU_QT ");
		sql.append("       , '" + MasterDT + "' AS HENKO_DT ");
		sql.append("       , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' AS INSERT_TS ");
		sql.append("       , RS.SYOKAI_USER_ID AS INSERT_USER_ID ");
		sql.append("       , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' AS UPDATE_TS ");
		sql.append("       , RS.SYOKAI_USER_ID AS UPDATE_USER_ID ");
		sql.append("       , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' AS BATCH_UPDATE_TS ");
		sql.append("       , '" + BATCH_ID + "' AS BATCH_UPDATE_ID ");
		sql.append("       , '0' AS DELETE_FG ");
		sql.append("       , '" + MasterDT + "' AS SINKI_TOROKU_DT  ");
		sql.append("     FROM ");
		sql.append("       (  ");
		sql.append("         SELECT ");
		sql.append("           RS.BUNRUI1_CD ");
		sql.append("           , RS.SYOHIN_CD ");
		sql.append("           , MAX(RS.SYOKAI_USER_ID) AS SYOKAI_USER_ID  ");
		sql.append("         FROM ");
		sql.append("           R_SYOHIN RS  ");
		sql.append("         WHERE ");
		sql.append("           RS.SINKI_TOROKU_DT < '" + MasterDT + "'  ");
		sql.append("           AND RS.DELETE_FG = '0'  ");
		sql.append("         GROUP BY ");
		sql.append("           RS.BUNRUI1_CD ");
		sql.append("           , RS.SYOHIN_CD ");
		sql.append("       ) RS  ");
		sql.append("       INNER JOIN R_TENPO RT  ");
		sql.append("         ON RT.TENPO_CD IN ("+ SHINTEN_CD +") ");
		sql.append("   ) WK  ");
		sql.append("     ON (  ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("       RTT.BUNRUI1_CD = WK.BUNRUI1_CD  ");
//		sql.append("       AND RTT.SYOHIN_CD = WK.SYOHIN_CD  ");
		sql.append("       RTT.SYOHIN_CD = WK.SYOHIN_CD  ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("       AND RTT.TENPO_CD = WK.TENPO_CD ");
		sql.append("     ) WHEN NOT MATCHED THEN  ");
		sql.append(" INSERT (  ");
		sql.append("   RTT.BUNRUI1_CD ");
		sql.append("   , RTT.SYOHIN_CD ");
		sql.append("   , RTT.TENPO_CD ");
		sql.append("   , RTT.DONYU_ST_DT ");
		sql.append("   , RTT.DONYU_ED_DT ");
		sql.append("   , RTT.NON_ACT_KB ");
		sql.append("   , RTT.NON_ACT_SOSHIN_DT ");
		sql.append("   , RTT.SAISHIN_HACYU_DT ");
		sql.append("   , RTT.SAISHIN_SHIIRE_DT ");
		sql.append("   , RTT.SAISHIN_URIAGE_DT ");
		sql.append("   , RTT.HASEIMOTO_KB ");
		sql.append("   , RTT.TANAWARI_PATERN ");
		sql.append("   , RTT.JUKI_NB ");
		sql.append("   , RTT.TANA_NB ");
		sql.append("   , RTT.FACE_QT ");
		sql.append("   , RTT.MIN_TINRETU_QT ");
		sql.append("   , RTT.MAX_TINRETU_QT ");
		sql.append("   , RTT.BASE_ZAIKO_NISU_QT ");
		sql.append("   , RTT.HENKO_DT ");
		sql.append("   , RTT.INSERT_TS ");
		sql.append("   , RTT.INSERT_USER_ID ");
		sql.append("   , RTT.UPDATE_TS ");
		sql.append("   , RTT.UPDATE_USER_ID ");
		sql.append("   , RTT.BATCH_UPDATE_TS ");
		sql.append("   , RTT.BATCH_UPDATE_ID ");
		sql.append("   , RTT.DELETE_FG ");
		sql.append("   , RTT.SINKI_TOROKU_DT ");
		sql.append(" )  ");
		sql.append(" VALUES (  ");
		sql.append("   WK.BUNRUI1_CD ");
		sql.append("   , WK.SYOHIN_CD ");
		sql.append("   , WK.TENPO_CD ");
		sql.append("   , WK.DONYU_ST_DT ");
		sql.append("   , WK.DONYU_ED_DT ");
		sql.append("   , WK.NON_ACT_KB ");
		sql.append("   , WK.NON_ACT_SOSHIN_DT ");
		sql.append("   , WK.SAISHIN_HACYU_DT ");
		sql.append("   , WK.SAISHIN_SHIIRE_DT ");
		sql.append("   , WK.SAISHIN_URIAGE_DT ");
		sql.append("   , WK.HASEIMOTO_KB ");
		sql.append("   , WK.TANAWARI_PATERN ");
		sql.append("   , WK.JUKI_NB ");
		sql.append("   , WK.TANA_NB ");
		sql.append("   , WK.FACE_QT ");
		sql.append("   , WK.MIN_TINRETU_QT ");
		sql.append("   , WK.MAX_TINRETU_QT ");
		sql.append("   , WK.BASE_ZAIKO_NISU_QT ");
		sql.append("   , WK.HENKO_DT ");
		sql.append("   , WK.INSERT_TS ");
		sql.append("   , WK.INSERT_USER_ID ");
		sql.append("   , WK.UPDATE_TS ");
		sql.append("   , WK.UPDATE_USER_ID ");
		sql.append("   , WK.BATCH_UPDATE_TS ");
		sql.append("   , WK.BATCH_UPDATE_ID ");
		sql.append("   , WK.DELETE_FG ");
		sql.append("   , WK.SINKI_TOROKU_DT ");
		sql.append(" )  ");
	
		return sql.toString();
	}

	/**
	 * 処理対象となるデータを取得するSQL生成
	 * @throws Exception
	 */
	private String getHTenpoMrgSQL() throws SQLException {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" MERGE ");
		sql.append(" INTO " + H_R_TENPO + " HRT ");
		sql.append("   USING ( ");
		sql.append("     SELECT");
		sql.append("       RT.TENPO_CD AS TENPO_CD");
		sql.append("       , '    ' AS OLD_TENPO_CD");
		sql.append("       , '  ' AS OLD_AREA_CD");
		sql.append("       , ' ' AS OLD_TENPO_KB");
		sql.append("     FROM");
		sql.append("       R_TENPO RT ");
		sql.append("     WHERE");
		sql.append("       ( ");
		sql.append("         RT.TENPO_KB IN (" + TANPINTEN_KB + ") ");
		sql.append("         OR RT.TENPO_CD IN ('')");
		sql.append("       ) ");
		sql.append("       AND COALESCE(RT.HEITEN_DT, '99999999') >= '" + MasterDT + "' ");
		sql.append("       AND RT.DELETE_FG = '0' ");
		sql.append("       AND NOT EXISTS ( ");
		sql.append("         SELECT");
		sql.append("           1 ");
		sql.append("         FROM");
		sql.append("           H_R_TENPO HRT ");
		sql.append("         WHERE");
		sql.append("           HRT.TENPO_CD = RT.TENPO_CD");
		sql.append("       )");
		sql.append("   ) WK ");
		sql.append("     ON ( ");
		sql.append("        HRT.TENPO_CD = WK.TENPO_CD");
		sql.append("     ) WHEN NOT MATCHED THEN ");
		sql.append(" INSERT ( ");
		sql.append("   HRT.TENPO_CD");
		sql.append("   , HRT.OLD_TENPO_CD");
		sql.append("   , HRT.OLD_AREA_CD");
		sql.append("   , HRT.OLD_TENPO_KB");
		sql.append(" ) ");
		sql.append(" VALUES ( ");
		sql.append("   WK.TENPO_CD");
		sql.append("   , WK.OLD_TENPO_CD");
		sql.append("   , WK.OLD_AREA_CD");
		sql.append("   , WK.OLD_TENPO_KB");
		sql.append(" ) ");
		
		return sql.toString();
	}
	
	/**
	 * 処理対象となるデータを取得するSQL生成
	 * @throws Exception
	 */
	private PreparedStatement getSaibanMstMrgSQL(String table_name, MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append(" MERGE ");
		sql.append(" INTO " + table_name + " RLDNS ");
		sql.append("   USING ( ");
		sql.append("     SELECT");
		sql.append("       '0000' AS COMP_CD");
		sql.append("       , ? AS SAIBAN_KB");
		sql.append("       , RT.TENPO_CD AS TENPO_CD");
		sql.append("       , ? AS START_NB");
		sql.append("       , ? AS END_NB");
		sql.append("       , ? AS NOW_NB");
		sql.append("       , '" + BATCH_ID + "' AS INSERT_USER_ID");
		sql.append("       , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' AS INSERT_TS");
		sql.append("       , '" + BATCH_ID + "' AS UPDATE_USER_ID");
		sql.append("       , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' AS UPDATE_TS ");
		sql.append("     FROM");
		sql.append("       R_TENPO RT ");
		sql.append("     WHERE");
		sql.append("       ( ");
		sql.append("         RT.TENPO_KB IN (" + TANPINTEN_KB + ") ");
		sql.append("         OR RT.TENPO_CD IN ('')");
		sql.append("       ) ");
		sql.append("       AND COALESCE(RT.HEITEN_DT, '99999999') >= " + MasterDT + " ");
		sql.append("       AND RT.DELETE_FG = '0' ");
		sql.append("       AND NOT EXISTS ( ");
		sql.append("         SELECT");
		sql.append("           1 ");
		sql.append("         FROM");
		sql.append("           " + table_name + " RLDNS ");
		sql.append("         WHERE");
		sql.append("           RLDNS.COMP_CD = '0000' ");
		sql.append("           AND RLDNS.SAIBAN_KB = '0' ");
		sql.append("           AND RLDNS.TENPO_CD = RT.TENPO_CD");
		sql.append("       )");
		sql.append("   ) WK ");
		sql.append("     ON ( ");
		sql.append("       RLDNS.COMP_CD = WK.COMP_CD ");
		sql.append("       AND RLDNS.SAIBAN_KB = WK.SAIBAN_KB ");
		sql.append("       AND RLDNS.TENPO_CD = WK.TENPO_CD");
		sql.append("     ) WHEN NOT MATCHED THEN ");
		sql.append(" INSERT ( ");
		sql.append("   RLDNS.COMP_CD");
		sql.append("   , RLDNS.SAIBAN_KB");
		sql.append("   , RLDNS.TENPO_CD");
		sql.append("   , RLDNS.START_NB");
		sql.append("   , RLDNS.END_NB");
		sql.append("   , RLDNS.NOW_NB");
		sql.append("   , RLDNS.INSERT_USER_ID");
		sql.append("   , RLDNS.INSERT_TS");
		sql.append("   , RLDNS.UPDATE_USER_ID");
		sql.append("   , RLDNS.UPDATE_TS");
		sql.append(" ) ");
		sql.append(" VALUES ( ");
		sql.append("   WK.COMP_CD");
		sql.append("   , WK.SAIBAN_KB");
		sql.append("   , WK.TENPO_CD");
		sql.append("   , WK.START_NB");
		sql.append("   , WK.END_NB");
		sql.append("   , WK.NOW_NB");
		sql.append("   , WK.INSERT_USER_ID");
		sql.append("   , WK.INSERT_TS");
		sql.append("   , WK.UPDATE_USER_ID");
		sql.append("   , WK.UPDATE_TS");
		sql.append(" ) ");
		
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	
	private Object nullToEmpty(Object obj){
		if (obj == null){
			return "";
		}
		return obj;
	}

	
    /**
     * <p>タイトル: MB380C09_CreateSintenMst内の内部クラス</p>
     * <p>説明: 実行結果から編集したBean</p>
     * <p>著作権: Copyright (c) 2017</p>
     * <p>会社名: VINX</p>
     * @author S.Nakazato
     * @version 1.00 (2017.09.12) S.Nakazato 新規作成
     */
    private class SaibanBean {
        
        /** 採番区分 */
        private String saibanKb = null;
        
        /** 開始番号 */
        private String startNb = null;
        
        /** 終了番号 */
        private String endNb = null;
        
        /** 現在番号 */
        private String nowNb = null;

		public String getSaibanKb() {
			return saibanKb;
		}

		public void setSaibanKb(String saibanKb) {
			this.saibanKb = saibanKb;
		}

		public String getStartNb() {
			return startNb;
		}

		public void setStartNb(String startNb) {
			this.startNb = startNb;
		}

		public String getEndNb() {
			return endNb;
		}

		public void setEndNb(String endNb) {
			this.endNb = endNb;
		}

		public String getNowNb() {
			return nowNb;
		}

		public void setNowNb(String nowNb) {
			this.nowNb = nowNb;
		}

        
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
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}

