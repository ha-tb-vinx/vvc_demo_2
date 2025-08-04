package mdware.master.batch.process.MSTB201;

import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB201030_InisyaruMisebetsuSyohinCreate.java クラス</p>
 * <p>説明　: PLU再送信日が設定されている店舗向けに、イニシャルPLU店別商品マスタを作成する。</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2016.09.28) Li.Sheng 新規作成
 * @version 1.01 (2016.11.01) M.Akagi #2623
 * @version 1.02 (2016.11.09) nv.cuong #1750
 * @version 1.03 (2016.11.25) nv.cuong #2839
 * @version 1.04 (2016.12.01) M.Akagi #2972
 * @version 1.05 (2016.12.12) S.Takayama #3049
 * @version 1.06 (2016.12.16) S.Takayama #3232
 * @version 1.07 (2017.01.06) M.Akagi #3570
 * @version 1.08 (2017.01.24) ML.Son #3571
 * @version 1.09 (2017.02.09) S.Takayama #3765
 * @version 1.10 (2017.02.15) T.Yajima #3686
 * @version 1.11 (2017.03.07) S.Takayama #4064
 * @version 1.12 (2017.03.15) #4336対応 T.Han FIVIMART対応
 * @version 1.13 (2017.03.28) Li.Sheng #4418対応
 * @version 1.14 (2017.03.28) Li.Sheng #4433対応
 * @version 1.15 (2017.04.25) M.Son #4775対応
 * @version 1.16 (2017.04.28) T.Han #4845対応
 * @version 1.17 (2020.07.10) KHAI.NN #6167 MKV対応
 * @version 1.18 (2021.10.22) Duy.HK #6367
 * @version 1.19 (2021.12.17) KHOI.ND #6406
 * @version 1.20 (2022.01.27) SIEU.D #6367
 * @version 1.21 (2022.10.05) VU.TD #6655
 * @version 1.22 (2023.02.15) DUY.HK #6728
 * @Version 1.23 (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB201030_IniPluTenbetuSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	/** ログ出力用変数 */
	private BatchLog batchLog = BatchLog.getInstance();

	private BatchUserLog userLog = BatchUserLog.getInstance();

	/** テーブル */
	private static final String TABLE_WK = "WK_TEC_INI_PLU"; // WK_INI_PLU店別商品

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** バッチ日付 */
	private String batchDt = null;

	/** 翌日バッチ日付 */
	private String yokuBatchDt = null;

	/** 店舗区分 */
	private String SEND_IF_TENPO_KB = null;

	/** 削除フラグ */
	private String DELETE_FLG = "0";

	/** 商品区分(仕入れ) */
	private String SYOHIN_KBN_SHIIRE = "3";

	/** 生鮮ＥＤＩコード("03") */
	private String EDI_CD = "03";

	/** 特殊日付 */
	private String TOKUBETU_DT = "99999999";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB201030_IniPluTenbetuSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB201030_IniPluTenbetuSyohinCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			yokuBatchDt = DateChanger.addDate(batchDt, 1);

	    	// 店舗区分セット
			Map<?, ?> tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_TENPO_KB);
			SEND_IF_TENPO_KB = SqlSupportUtil.createInString(tenpoMap.keySet().toArray());
			writeLog(Level.INFO_INT, "店舗区分は:" + SEND_IF_TENPO_KB);

			// 2016.11.01 M.Akagi #2623 (S)
			// WK_INI_PLU店別商品の削除
			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK);
			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品削除処理を終了します。");
			// 2016.11.01 M.Akagi #2623 (E)

			// WK_PLU商品からWK_INI_PLU店別商品の登録
			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品登録処理一（WK_PLU→WK_INI_PLU）を開始します。");
			iRec = db.executeUpdate(getWkIniPluInsertSql1());
			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品登録処理一（WK_PLU→WK_INI_PLU）を終了します。");

			db.commit();

			// WK_PLU例外より、WK_INI_PLU店別商品を作成する
// 2017.03.15 T.Han #4336対応（S)
//			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品登録処理二（WK_PLU例外→WK_INI_PLU）を開始します。");
//			iRec = db.executeUpdate(getWkIniPluInsertSql2());
//			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品を" + iRec + "件作成しました。");
//			writeLog(Level.INFO_INT, "WK_INI_PLU店別商品登録処理二（WK_PLU例外→WK_INI_PLU）を終了します。");

//			db.commit();
// 2017.03.15 T.Han #4336対応（E)

			writeLog(Level.INFO_INT, "処理を終了します。");

			//SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
			if (closeDb || db != null) {
				db.close();
			}
		}

	}

	/********** ＳＱＬ生成処理 **********/
	/**
	 * WK_INI_PLU店別商品を登録するSQLを取得する
	 *
	 * @return WK_INI_PLU店別商品登録SQL
	 */
	private String getWkIniPluInsertSql2() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("MERGE INTO ");
		sql.append("	" + TABLE_WK + " WTIP ");
		sql.append("	USING ");
		sql.append("		( ");

		sql.append(" SELECT ");
		sql.append("	 WTPSK.BUNRUI1_CD ");
		sql.append("	 ,WTPSK.SYOHIN_CD ");
		//add 2017.01.24 #3571 ML.Son(S)
		sql.append("	 ,WTPSK.OLD_SYOHIN_CD ");
		//add 2017.01.24 #3571 ML.Son(E)
		sql.append("	 ,SUB1.TENPO_CD ");
		//add 2016.11.25 #2839 nv.cuong(S)
		sql.append(" 	 ,SUB2.GYOTAI_KB ");
		//add 2016.11.25 #2839 nv.cuong(E)
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append("	 ,SUB1.GENTANKA_VL ");
		//sql.append("	 ,SUB1.BAITANKA_VL ");
		//sql.append("	 ,SUB1.SIIRESAKI_CD ");
		//sql.append("	 ,SUB1.PLU_SEND_DT ");
		sql.append("     ,NVL(SUB1.GENTANKA_VL   ,WTPSK.GENTANKA_VL) AS GENTANKA_VL");
		sql.append("     ,NVL(SUB1.BAITANKA_VL   ,WTPSK.BAITANKA_VL) AS BAITANKA_VL ");
		sql.append("     ,NVL(SUB1.SIIRESAKI_CD  ,WTPSK.SIIRESAKI_CD) AS SIIRESAKI_CD ");
		sql.append("     ,NVL(SUB1.PLU_SEND_DT  ,WTPSK.PLU_SEND_DT) AS PLU_SEND_DT ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		sql.append("	 ,SUB1.BAIKA_HAISHIN_FG ");
		sql.append("	 ,WTPSK.BUNRUI5_CD ");
		sql.append("	 ,WTPSK.REC_HINMEI_KANJI_NA ");
		sql.append("	 ,WTPSK.REC_HINMEI_KANA_NA ");
		sql.append("	 ,WTPSK.KIKAKU_KANJI_NA ");
		sql.append("	 ,WTPSK.MAKER_KIBO_KAKAKU_VL ");
		sql.append("	 ,WTPSK.ZEI_KB ");
		sql.append("	 ,'00' AS ERR_KB ");
		sql.append("	 ,WTPSK.BUNRUI2_CD AS  BUNRUI2_CD");
		sql.append("	 ,WTPSK.TEIKAN_FG ");
		sql.append("	 ,WTPSK.ZEI_RT ");
		// 206.12.01 M.Akgai #2972 (S)
		//sql.append("	 ,SUB3.BUNRUI5_CD AS BUNRUI5_CD2");
		// 206.12.01 M.Akgai #2972 (E)
		sql.append("	 ,WTPSK.SYOHI_KIGEN_DT ");
		sql.append("	 ,WTPSK.CARD_FG ");
		sql.append("	 ,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	 ,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		sql.append("	 ,WTPSK.HANBAI_TANI ");
		sql.append("	 ,WTPSK.KEIRYOKI_NM ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append("	 ,SUB1.PLU_HANEI_TIME ");
		sql.append("     ,NVL(SUB1.PLU_HANEI_TIME  ,WTPSK.PLU_HANEI_TIME) AS PLU_HANEI_TIME ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		sql.append("	 ,WTPSK.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	 ,WTPSK.LABEL_SEIBUN ");
		sql.append("	 ,WTPSK.LABEL_HOKAN_HOHO ");
		sql.append("	 ,WTPSK.LABEL_TUKAIKATA ");
		// #3049 MSTB201 2016.12.12 S.Takayama (S)
		sql.append("	 ,WTPSK.LABEL_COUNTRY_NA ");
		// #3049 MSTB201 2016.12.12 S.Takayama (E)
		// #3765 MSTB201 2017.02.09 S.Takayama (S)
		sql.append("	 ,WTPSK.HANBAI_TANI_EN ");
		// #3765 MSTB201 2017.02.09 S.Takayama (E)
		// #3686 MSTB201 2017.02.13 T.Yajima (S)
		sql.append("     ,GREATEST(WTPSK.DELETE_FG ，SUB1.DELETE_FG) AS DELETE_FG ");
		// #3686 MSTB201 2017.02.13 T.Yajima (E)

		sql.append(" FROM WK_TEC_PLU_SYOHIN WTPSK ");
		sql.append(" INNER JOIN ( ");
		sql.append(" SELECT ");
		sql.append(" WTPSR.BUNRUI1_CD, ");
		sql.append(" WTPSR.SYOHIN_CD, ");
		sql.append(" WTPSR.TENPO_CD, ");
		sql.append(" WTPSR.GENTANKA_VL, ");
		sql.append(" WTPSR.BAITANKA_VL, ");
		sql.append(" WTPSR.SIIRESAKI_CD, ");
		sql.append(" WTPSR.PLU_SEND_DT, ");
		sql.append(" WTPSR.BAIKA_HAISHIN_FG, ");
		sql.append(" WTPSR.PLU_HANEI_TIME ");
		// #3686 MSTB201 2017.02.13 T.Yajima (S)
		sql.append(" , WTPSR.DELETE_FG ");
		// #3686 MSTB201 2017.02.13 T.Yajima (E)
		sql.append(" FROM ");
		sql.append(" WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");  // 内部：サブクエリ１（WK_PLU商品）
		sql.append(" WHERE ");
		// add 2016.11.11 #1750 nv.cuong(S)
		// #3232 MST201 2016.12.16 S.Takayama (S)
		//sql.append(" WTPSR.DELETE_FG = '" + DELETE_FLG + "' AND ");
		sql.append(" WTPSR.DELETE_FG = '" + DELETE_FLG + "' ");
		// #3232 MST201 2016.12.16 S.Takayama (E)
		//sql.append(" WTPSR.PLU_SEND_DT <> '" + TOKUBETU_DT + "' ");
		// add 2016.11.11 #1750 nv.cuong(S)
		// add 2016.11.18 #1750 nv.cuong(S)
		// #3232 MST201 2016.12.16 S.Takayama (S)
		//sql.append(" WTPSR.BAIKA_HAISHIN_FG = '0'  ");
		// #3232 MST201 2016.12.16 S.Takayama (E)
		// add 2016.11.18 #1750 nv.cuong(E)
		sql.append(" ) SUB1 ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append(" ON WTPSK.BUNRUI1_CD = SUB1.BUNRUI1_CD AND ");
		sql.append(" ON  ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append(" WTPSK.SYOHIN_CD = SUB1.SYOHIN_CD ");

		sql.append(" INNER JOIN "); // 内部：サブクエリ2(TMP店舗マスタ)
		sql.append(" (SELECT ");
		sql.append(" TENPO_CD ");
		//add 2016.11.25 #2839 nv.cuong(S)
		sql.append(" ,GYOTAI_KB ");
		//add 2016.11.25 #2839 nv.cuong(E)
		sql.append(" FROM ");
		sql.append(" TMP_R_TENPO ");
		sql.append(" WHERE ");
		sql.append(" ZAIMU_END_DT > '" + batchDt + "' AND ");
		sql.append(" DELETE_FG = '" + DELETE_FLG + "' AND ");
		// #6406 Del 2021.12.17 KHOI.ND (S)
		// sql.append(" TENPO_KB IN (" + SEND_IF_TENPO_KB + ") AND ");
		// #6406 Del 2021.12.17 KHOI.ND (E)
		sql.append(" SAI_SEND_PLU_DT = '" + yokuBatchDt + "' ");
		sql.append(" ) SUB2 ");
		sql.append(" ON TRIM(SUB2.TENPO_CD) = TRIM(SUB1.TENPO_CD) ");

		// 206.12.01 M.Akgai #2972 (S)
//		sql.append(" LEFT JOIN "); // 外部：サブクエリ3
//		sql.append(" (SELECT ");
//		sql.append(" DGEA.SYOHIN_CD, ");
//		sql.append(" DHTT.TENPO_CD, ");
//		sql.append(" DGA.BUNRUI5_CD ");
//		sql.append(" FROM DT_GROUPBAIHEN_EXCLUDE_ASN DGEA "); // グループ売変除外品ASN
//		sql.append(" INNER JOIN DT_HANSOKU_TAISYO_TENPO DHTT ON "); // 内部：販促対象店舗
//		sql.append(" DGEA.THEME_CD = DHTT.THEME_CD ");
//		sql.append(" INNER JOIN DT_GROUPBAIHEN_ASN DGA ON "); // 内部：グループ売変ASN
//		sql.append(" DGEA.THEME_CD = DGA.THEME_CD ");
//		sql.append(" WHERE ");
//		sql.append(" DGA.HANBAI_START_DT <= '" + yokuBatchDt + "' AND ");
//		sql.append(" DGA.HANBAI_END_DT >= '" + yokuBatchDt + "' ");
//		sql.append(" ) SUB3 ");
//		sql.append(" ON SUB1.SYOHIN_CD = SUB3.SYOHIN_CD AND ");
//		sql.append(" SUB1.TENPO_CD = SUB3.TENPO_CD ");
		// 206.12.01 M.Akgai #2972 (E)

		sql.append(" WHERE "); // 抽出条件
		sql.append(" WTPSK.DELETE_FG = '" + DELETE_FLG + "' AND ");
		// #6367 MOD 2022.01.27 SIEU.D (S)
		// sql.append(" WTPSK.SYOHIN_KB <> '" + SYOHIN_KBN_SHIIRE + "' AND ");
		// sql.append(" GET_JAN_SYUBETSU(WTPSK.SYOHIN_CD) <> '" + EDI_CD + "'  ");
		 sql.append(" WTPSK.SYOHIN_KB <> '" + SYOHIN_KBN_SHIIRE + "' ");
		// #6367 MOD 2022.01.27 SIEU.D (S)
		sql.append("		) WTPSR1 ");

		sql.append("		ON ");
		sql.append("			( ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("			WTIP.BUNRUI1_CD	= WTPSR1.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("			WTIP.SYOHIN_CD	= WTPSR1.SYOHIN_CD AND ");
		sql.append("			WTIP.TENPO_CD	= WTPSR1.TENPO_CD ");
		sql.append("			) ");

		// add 2016.11.19 #1750 nv.cuong(S)
		sql.append("    WHEN MATCHED THEN UPDATE ");
        sql.append(" SET ");
//        sql.append("  BUNRUI1_CD = WTPSR1.BUNRUI1_CD ");  // 分類１コード
//		sql.append(" ,SYOHIN_CD = WTPSR1.SYOHIN_CD "); //  商品コード
//		sql.append(" ,TENPO_CD = WTPSR1.TENPO_CD "); //  店舗コード
        //add 2016.11.25 #2839 nv.cuong(S)
      	sql.append(" GYOTAI_KB = WTPSR1.GYOTAI_KB ");
      	//add 2016.11.25 #2839 nv.cuong(E)
		sql.append(" ,GENTANKA_VL = WTPSR1.GENTANKA_VL "); //  原価単価
		sql.append(" ,BAITANKA_VL = WTPSR1.BAITANKA_VL "); //  売価単価
		sql.append(" ,SIIRESAKI_CD = WTPSR1.SIIRESAKI_CD "); //  仕入先コード
		sql.append(" ,PLU_SEND_DT = WTPSR1.PLU_SEND_DT "); //  PLU送信日
		sql.append(" ,BAIKA_HAISHIN_FG = WTPSR1.BAIKA_HAISHIN_FG "); //  売価配信フラグ
		sql.append(" ,BUNRUI5_CD = WTPSR1.BUNRUI5_CD "); //  分類５コード
		sql.append(" ,REC_HINMEI_KANJI_NA = WTPSR1.REC_HINMEI_KANJI_NA "); //  POSレシート品名（漢字）
		sql.append(" ,REC_HINMEI_KANA_NA = WTPSR1.REC_HINMEI_KANA_NA "); //  POSレシート品名（カナ）
		sql.append(" ,KIKAKU_KANJI_NA = WTPSR1.KIKAKU_KANJI_NA "); //  漢字規格
		sql.append(" ,MAKER_KIBO_KAKAKU_VL = WTPSR1.MAKER_KIBO_KAKAKU_VL "); //  メーカー希望小売り価格
		sql.append(" ,ZEI_KB = WTPSR1.ZEI_KB "); //  税区分
		sql.append(" ,ERR_KB = '00' "); //  エラー区分
		sql.append(" ,BUNRUI2_CD = WTPSR1.BUNRUI2_CD "); //  分類２コード
		sql.append(" ,TEIKAN_FG = WTPSR1.TEIKAN_FG "); //  定貫フラグ
		sql.append(" ,ZEI_RT = WTPSR1.ZEI_RT "); //  税率（VAT）
		// 2016.12.01 M.Akagi #2972 (S)
		//sql.append(" ,SEASON_ID = SUBSTR(WTPSR1.BUNRUI5_CD2,1,6) "); //  season id
		// 2016.12.01 M.Akagi #2972 (E)
		sql.append(" ,SYOHI_KIGEN_DT = WTPSR1.SYOHI_KIGEN_DT "); //  消費期限日数
		sql.append(" ,CARD_FG = WTPSR1.CARD_FG "); //  カードフラグ
		sql.append(" ,INSERT_USER_ID = '" + userLog.getJobId() + "' "); //  作成者ID
		sql.append(" ,INSERT_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' "); //  作成年月日
		sql.append(" ,UPDATE_USER_ID = '" + userLog.getJobId() + "' "); //  更新者ID
		sql.append(" ,UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' "); //  更新年月日
		sql.append(" ,HANBAI_TANI = WTPSR1.HANBAI_TANI "); //  販売単位
		sql.append(" ,KEIRYOKI_NM = WTPSR1.KEIRYOKI_NM "); //  計量器名
		sql.append(" ,PLU_HANEI_TIME = WTPSR1.PLU_HANEI_TIME "); //  PLU反映時間
		sql.append(" ,SYOHI_KIGEN_HYOJI_PATTER = WTPSR1.SYOHI_KIGEN_HYOJI_PATTER "); //  消費期限表示パターン
		sql.append(" ,LABEL_SEIBUN = WTPSR1.LABEL_SEIBUN "); //  ラベル成分
		sql.append(" ,LABEL_HOKAN_HOHO = WTPSR1.LABEL_HOKAN_HOHO "); //  ラベル保管方法
		sql.append(" ,LABEL_TUKAIKATA = WTPSR1.LABEL_TUKAIKATA "); //  ラベル使い方
		// add 2016.11.19 #1750 nv.cuong(E)
		// #3049 MSTB201 2016.12.12 S.Takayama (S)
		sql.append(" , LABEL_COUNTRY_NA = WTPSR1.LABEL_COUNTRY_NA ");
		// #3049 MSTB201 2016.12.12 S.Takayama (E)
		//add 2017.01.24 #3571 ML.Son(S)
		sql.append(" ,OLD_SYOHIN_CD = WTPSR1.OLD_SYOHIN_CD "); // 旧商品コード
		//add 2017.01.24 #3571 ML.Son(E)
		// #3765 MSTB201 2017.02.09 S.Takayama (S)
		sql.append(" , HANBAI_TANI_EN = WTPSR1.HANBAI_TANI_EN "); // 販売単位（英字）
		// #3765 MSTB201 2017.02.09 S.Takayama (E)

		// 2016.12.01 M.Akagi #2972 (S)
//		sql.append("	WHEN NOT MATCHED THEN ");
//		sql.append("				INSERT ");
//		sql.append("					( ");
//		sql.append("  BUNRUI1_CD ");  // 分類１コード
//		sql.append(" ,SYOHIN_CD "); //  商品コード
//		//sql.append(" ,OLD_SYOHIN_CD "); //  OLD商品コード
//		sql.append(" ,TENPO_CD "); //  店舗コード
//		//add 2016.11.25 #2839 nv.cuong(S)
//		sql.append(" ,GYOTAI_KB ");
//		//add 2016.11.25 #2839 nv.cuong(E)
//		sql.append(" ,GENTANKA_VL "); //  原価単価
//		sql.append(" ,BAITANKA_VL "); //  売価単価
//		sql.append(" ,SIIRESAKI_CD "); //  仕入先コード
//		sql.append(" ,PLU_SEND_DT "); //  PLU送信日
//		sql.append(" ,BAIKA_HAISHIN_FG "); //  売価配信フラグ
//		sql.append(" ,BUNRUI5_CD "); //  分類５コード
//		sql.append(" ,REC_HINMEI_KANJI_NA "); //  POSレシート品名（漢字）
//		sql.append(" ,REC_HINMEI_KANA_NA "); //  POSレシート品名（カナ）
//		sql.append(" ,KIKAKU_KANJI_NA "); //  漢字規格
//		sql.append(" ,MAKER_KIBO_KAKAKU_VL "); //  メーカー希望小売り価格
//		sql.append(" ,ZEI_KB "); //  税区分
//		sql.append(" ,ERR_KB "); //  エラー区分
//		sql.append(" ,BUNRUI2_CD "); //  分類２コード
//		sql.append(" ,TEIKAN_FG "); //  定貫フラグ
//		sql.append(" ,ZEI_RT "); //  税率（VAT）
//		// 206.12.01 M.Akgai #2972 (S)
//		//sql.append(" ,SEASON_ID "); //  season id
//		// 206.12.01 M.Akgai #2972 (E)
//		sql.append(" ,SYOHI_KIGEN_DT "); //  消費期限日数
//		sql.append(" ,CARD_FG "); //  カードフラグ
//		sql.append(" ,INSERT_USER_ID "); //  作成者ID
//		sql.append(" ,INSERT_TS "); //  作成年月日
//		sql.append(" ,UPDATE_USER_ID "); //  更新者ID
//		sql.append(" ,UPDATE_TS "); //  更新年月日
//		sql.append(" ,HANBAI_TANI "); //  販売単位
//		sql.append(" ,KEIRYOKI_NM "); //  計量器名
//		sql.append(" ,PLU_HANEI_TIME "); //  PLU反映時間
//		sql.append(" ,SYOHI_KIGEN_HYOJI_PATTER "); //  消費期限表示パターン
//		sql.append(" ,LABEL_SEIBUN "); //  ラベル成分
//		sql.append(" ,LABEL_HOKAN_HOHO "); //  ラベル保管方法
//		sql.append(" ,LABEL_TUKAIKATA "); //  ラベル使い方
//		sql.append("					) ");
//		sql.append("				VALUES ");
//		sql.append("					( ");
//
//		sql.append("	 WTPSR1.BUNRUI1_CD ");
//		sql.append("	 ,WTPSR1.SYOHIN_CD ");
//		sql.append("	 ,WTPSR1.TENPO_CD ");
//		//add 2016.11.25 #2839 nv.cuong(S)
//		sql.append("     ,WTPSR1.GYOTAI_KB ");
//		//add 2016.11.25 #2839 nv.cuong(E)
//		sql.append("	 ,WTPSR1.GENTANKA_VL ");
//		sql.append("	 ,WTPSR1.BAITANKA_VL ");
//		sql.append("	 ,WTPSR1.SIIRESAKI_CD ");
//		sql.append("	 ,WTPSR1.PLU_SEND_DT ");
//		sql.append("	 ,WTPSR1.BAIKA_HAISHIN_FG ");
//		sql.append("	 ,WTPSR1.BUNRUI5_CD ");
//		sql.append("	 ,WTPSR1.REC_HINMEI_KANJI_NA ");
//		sql.append("	 ,WTPSR1.REC_HINMEI_KANA_NA ");
//		sql.append("	 ,WTPSR1.KIKAKU_KANJI_NA ");
//		sql.append("	 ,WTPSR1.MAKER_KIBO_KAKAKU_VL ");
//		sql.append("	 ,WTPSR1.ZEI_KB ");
//		sql.append("	 ,'00' ");
//		sql.append("	 ,WTPSR1.BUNRUI2_CD ");
//		sql.append("	 ,WTPSR1.TEIKAN_FG ");
//		sql.append("	 ,WTPSR1.ZEI_RT ");
//		// 206.12.01 M.Akgai #2972 (S)
//		//sql.append("	 ,SUBSTR(WTPSR1.BUNRUI5_CD2,1,6) ");
//		// 206.12.01 M.Akgai #2972 (E)
//		sql.append("	 ,WTPSR1.SYOHI_KIGEN_DT ");
//		sql.append("	 ,WTPSR1.CARD_FG ");
//		sql.append("	 ,'" + userLog.getJobId() + "' ");
//		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
//		sql.append("	 ,'" + userLog.getJobId() + "' ");
//		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
//		sql.append("	 ,WTPSR1.HANBAI_TANI ");
//		sql.append("	 ,WTPSR1.KEIRYOKI_NM ");
//		sql.append("	 ,WTPSR1.PLU_HANEI_TIME ");
//		sql.append("	 ,WTPSR1.SYOHI_KIGEN_HYOJI_PATTER ");
//		sql.append("	 ,WTPSR1.LABEL_SEIBUN ");
//		sql.append("	 ,WTPSR1.LABEL_HOKAN_HOHO ");
//		sql.append("	 ,WTPSR1.LABEL_TUKAIKATA ");
//		sql.append("					) ");
		// 2016.12.01 M.Akagi #2972 (E)

		return sql.toString();
	}

	/**
	 * WK_INI_PLU店別商品を登録するSQLを取得する
	 *
	 * @return WK_INI_PLU店別商品登録SQL
	 */
	private String getWkIniPluInsertSql1() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO " + TABLE_WK + " ");
		sql.append("	( ");
		sql.append("  BUNRUI1_CD ");  // 分類１コード
		sql.append(" ,SYOHIN_CD "); //  商品コード
		//sql.append(" ,OLD_SYOHIN_CD "); //  OLD商品コード
		//add 2017.01.24 #3571 ML.Son(S)
		sql.append(" ,OLD_SYOHIN_CD "); //  OLD商品コード
		//add 2017.01.24 #3571 ML.Son(E)
		sql.append(" ,TENPO_CD "); //  店舗コード
		//add 2016.11.25 #2839 nv.cuong(S)
		sql.append(" ,GYOTAI_KB ");
		//add 2016.11.25 #2839 nv.cuong(E)
		sql.append(" ,GENTANKA_VL "); //  原価単価
		sql.append(" ,BAITANKA_VL "); //  売価単価
		sql.append(" ,SIIRESAKI_CD "); //  仕入先コード
		sql.append(" ,PLU_SEND_DT "); //  PLU送信日
		sql.append(" ,BAIKA_HAISHIN_FG "); //  売価配信フラグ
		sql.append(" ,BUNRUI5_CD "); //  分類５コード
		// #6167 MSTB201 Add 2020.07.10 KHAI.NN (S)
		sql.append(" ,HINMEI_KANJI_NA ");
		// #6167 MSTB201 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append(" ,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append(" ,REC_HINMEI_KANJI_NA "); //  POSレシート品名（漢字）
		sql.append(" ,REC_HINMEI_KANA_NA "); //  POSレシート品名（カナ）
		sql.append(" ,KIKAKU_KANJI_NA "); //  漢字規格
		sql.append(" ,MAKER_KIBO_KAKAKU_VL "); //  メーカー希望小売り価格
		sql.append(" ,ZEI_KB "); //  税区分
		sql.append(" ,ERR_KB "); //  エラー区分
		sql.append(" ,BUNRUI2_CD "); //  分類２コード
		sql.append(" ,TEIKAN_FG "); //  定貫フラグ
		sql.append(" ,ZEI_RT "); //  税率（VAT）
		sql.append(" ,SEASON_ID "); //  season id
		sql.append(" ,SYOHI_KIGEN_DT "); //  消費期限日数
		sql.append(" ,CARD_FG "); //  カードフラグ
		sql.append(" ,INSERT_USER_ID "); //  作成者ID
		sql.append(" ,INSERT_TS "); //  作成年月日
		sql.append(" ,UPDATE_USER_ID "); //  更新者ID
		sql.append(" ,UPDATE_TS "); //  更新年月日
		sql.append(" ,HANBAI_TANI "); //  販売単位
		sql.append(" ,KEIRYOKI_NM "); //  計量器名
		sql.append(" ,PLU_HANEI_TIME "); //  PLU反映時間
		sql.append(" ,SYOHI_KIGEN_HYOJI_PATTER "); //  消費期限表示パターン
		sql.append(" ,LABEL_SEIBUN "); //  ラベル成分
		sql.append(" ,LABEL_HOKAN_HOHO "); //  ラベル保管方法
		sql.append(" ,LABEL_TUKAIKATA "); //  ラベル使い方
		// #3049 MSTB201 2016.12.12 S.Takayama (S)
		sql.append(" ,LABEL_COUNTRY_NA "); //  ラベル国名
		// #3049 MSTB201 2016.12.12 S.Takayama (E)
		// #3765 MSTB201 2017.02.09 S.Takayama (S)
		sql.append(" ,HANBAI_TANI_EN "); //  販売単位（英字）
		// #3765 MSTB201 2017.02.09 S.Takayama (E)
		// #3686 MSTB201 2017.02.13 T.Yajima (S)
		sql.append(" ,DELETE_FG "); //  削除フラグ
		// #3686 MSTB201 2017.02.13 T.Yajima (E)
		sql.append("	) ");

		sql.append(" SELECT ");
		sql.append("	 WTPSK.BUNRUI1_CD ");
		sql.append("	 ,WTPSK.SYOHIN_CD ");
		//add 2017.01.24 #3571 ML.Son(S)
		sql.append("	 ,WTPSK.OLD_SYOHIN_CD ");
		//add 2017.01.24 #3571 ML.Son(E)
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append("	 ,TRTT.TENPO_CD ");
		sql.append("     ,SUB1.TENPO_CD ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		//add 2016.11.25 #2839 nv.cuong(S)
		sql.append(" 	 ,SUB1.GYOTAI_KB ");
		//add 2016.11.25 #2839 nv.cuong(E)
		// 2017.03.15 T.Han #4336対応（S)
		//sql.append("	 ,WTPSK.GENTANKA_VL ");
		//sql.append("	 ,WTPSK.BAITANKA_VL ");
		//sql.append("	 ,WTPSK.SIIRESAKI_CD ");
		//sql.append("	 ,WTPSK.PLU_SEND_DT ");
		//sql.append("	 ,WTPSK.BAIKA_HAISHIN_FG ");
		sql.append("	 ,NVL(WTPSR.GENTANKA_VL, WTPSK.GENTANKA_VL) ");
		sql.append("	 ,NVL(WTPSR.BAITANKA_VL, WTPSK.BAITANKA_VL) ");
		sql.append("	 ,NVL(WTPSR.SIIRESAKI_CD, WTPSK.SIIRESAKI_CD) ");
		sql.append("	 ,NVL(WTPSR.PLU_SEND_DT, WTPSK.PLU_SEND_DT) ");
		sql.append("	 ,WTPSR.BAIKA_HAISHIN_FG ");
		// 2017.03.15 T.Han #4336対応（E)
		sql.append("	 ,WTPSK.BUNRUI5_CD ");
		// #6167 MSTB201 Add 2020.07.10 KHAI.NN (S)
		sql.append("	 ,WTPSK.HINMEI_KANJI_NA ");
		// #6167 MSTB201 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	 ,WTPSK.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	 ,WTPSK.REC_HINMEI_KANJI_NA ");
		sql.append("	 ,WTPSK.REC_HINMEI_KANA_NA ");
		sql.append("	 ,WTPSK.KIKAKU_KANJI_NA ");
		sql.append("	 ,WTPSK.MAKER_KIBO_KAKAKU_VL ");
		sql.append("	 ,WTPSK.ZEI_KB ");
		sql.append("	 ,'00' ");
		sql.append("	 ,WTPSK.BUNRUI2_CD ");
		sql.append("	 ,WTPSK.TEIKAN_FG ");
		sql.append("	 ,WTPSK.ZEI_RT ");
		// 2016.12.01 M.Akagi #2972 (S)
		//sql.append("	 ,SUBSTR(SUB2.BUNRUI5_CD,1,6) ");
// #6728 MOD 2023.02.15 DUY.HK (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//        //sql.append("	 ,DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(WTPSK.BUNRUI5_CD, 1, 6)) ");
//        sql.append("    ,CASE ");
//        sql.append("        WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)       
//    	sql.append("        WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//    	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("            ELSE DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(WTPSK.BUNRUI5_CD, 1, 6)) ");
//        sql.append("        END AS SEASON_ID ");
//        // #6367 Add 2021.10.22 Duy.HK (E)
		sql.append("    ,CASE ");
		sql.append("        WHEN WTPSK.SEASON_ID IS NOT NULL THEN WTPSK.SEASON_ID ");
		sql.append("            ELSE DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(WTPSK.BUNRUI5_CD, 1, 6)) ");
		sql.append("        END AS SEASON_ID ");
// #6728 MOD 2023.02.15 DUY.HK (E)
		// 2016.12.01 M.Akagi #2972 (E)
		sql.append("	 ,WTPSK.SYOHI_KIGEN_DT ");
		sql.append("	 ,WTPSK.CARD_FG ");
		sql.append("	 ,'" + userLog.getJobId() + "' ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,'" + userLog.getJobId() + "' ");
		sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("	 ,WTPSK.HANBAI_TANI ");
		sql.append("	 ,WTPSK.KEIRYOKI_NM ");
		// 2017.03.15 T.Han #4336対応（S)
		//sql.append("	 ,WTPSK.PLU_HANEI_TIME ");
		sql.append("	 ,NVL(WTPSR.PLU_HANEI_TIME, WTPSK.PLU_HANEI_TIME) ");
		// 2017.03.15 T.Han #4336対応（E)
		sql.append("	 ,WTPSK.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	 ,WTPSK.LABEL_SEIBUN ");
		sql.append("	 ,WTPSK.LABEL_HOKAN_HOHO ");
		sql.append("	 ,WTPSK.LABEL_TUKAIKATA ");
		// #3049 MSTB201 2016.12.12 S.Takayama (S)
		sql.append("	 ,WTPSK.LABEL_COUNTRY_NA ");
		// #3049 MSTB201 2016.12.12 S.Takayama (E)
		// #3765 MSTB201 2017.02.09 S.Takayama (S)
		sql.append("	 ,WTPSK.HANBAI_TANI_EN ");
		// #3765 MSTB201 2017.02.09 S.Takayama (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB201 2017.02.13 T.Yajima (S)
		// 2017.03.15 T.Han #4336対応（S)
        //sql.append("     ,WTPSK.DELETE_FG ");
//        sql.append("	 ,GREATEST(WTPSK.DELETE_FG,WTPSR.DELETE_FG) ");
		// 2017.03.15 T.Han #4336対応（E)
		// #3686 MSTB201 2017.02.13 T.Yajima (E)
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append("	 ,DECODE(SUB3.BAIKA_HAISHIN_FG,'0','0',GREATEST(WTPSK.DELETE_FG,WTPSR.DELETE_FG)) AS DELETE_FG ");
		sql.append("	 ,GREATEST(WTPSK.DELETE_FG,WTPSR.DELETE_FG) AS DELETE_FG ");
		// #4775 Mod 2017.04.25 M.Son (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)

		sql.append(" FROM WK_TEC_PLU_SYOHIN WTPSK ");
// #6728 DEL 2023.02.15 DUY.HK (E)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//		sql.append("    LEFT JOIN  ");
//        sql.append("        (SELECT   SYOHIN_CD     ");
//        sql.append("                 ,BUNRUI1_CD    ");
//        sql.append("                 ,WARIBIKI_KB   ");
//        sql.append("         FROM   ");
//        sql.append("               R_SYOHIN   RS_1  ");
//        sql.append("         WHERE  ");
//        sql.append("               RS_1.YUKO_DT = ( ");
//        sql.append("                              SELECT  MAX(YUKO_DT)   ");
//        sql.append("                              FROM   ");
//        sql.append("                                   R_SYOHIN    RS_2  ");
//        sql.append("                              WHERE ");
//        // #6620 MOD 2022.11.18 VU.TD (S)
////        sql.append("                                       RS_2.BUNRUI1_CD = RS_1.BUNRUI1_CD");
////        sql.append("                                   AND RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        sql.append("                                    RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        // #6620 MOD 2022.11.18 VU.TD (E)
//        sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//        sql.append("                              ) ");
//        sql.append("        )  RS  ");
//        sql.append("     ON    WTPSK.SYOHIN_CD   = RS.SYOHIN_CD  ");
//     // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND WTPSK.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//     // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
// #6728 DEL 2023.02.15 DUY.HK (E)
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append(" INNER JOIN TMP_R_TANPINTEN_TORIATUKAI TRTT ON "); // 内部：TMP単品店取扱マスタ
		//sql.append(" WTPSK.BUNRUI1_CD = TRTT.BUNRUI1_CD  AND ");
		//sql.append(" WTPSK.SYOHIN_CD = TRTT.SYOHIN_CD ");
		sql.append(" INNER JOIN WK_TEC_PLU_SYOHIN_REIGAI WTPSR ON "); // 内部：WK_PLU商品例外
        sql.append(" WTPSK.SYOHIN_CD = WTPSR.SYOHIN_CD   ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		sql.append(" INNER JOIN "); // 内部：サブクエリ1(TMP店舗マスタ)
		sql.append(" (SELECT ");
		sql.append(" TENPO_CD ");
		//add 2016.11.25 #2839 nv.cuong(S)
		sql.append(" ,GYOTAI_KB ");
		//add 2016.11.25 #2839 nv.cuong(E)
		sql.append(" FROM ");
		sql.append(" TMP_R_TENPO ");
		sql.append(" WHERE ");
		sql.append(" ZAIMU_END_DT > '" + batchDt + "' AND ");
		sql.append(" DELETE_FG = '" + DELETE_FLG + "' AND ");
		// #6406 Del 2021.12.17 KHOI.ND (S)
		// sql.append(" TENPO_KB IN (" + SEND_IF_TENPO_KB + ") AND ");
		// #6406 Del 2021.12.17 KHOI.ND (E)
		sql.append(" SAI_SEND_PLU_DT = '" + yokuBatchDt + "' ");
		sql.append(" ) SUB1 ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append(" ON TRIM(SUB1.TENPO_CD) = TRIM(TRTT.TENPO_CD) ");
		sql.append(" ON TRIM(SUB1.TENPO_CD) = WTPSR.TENPO_CD ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
// #4433 2017.03.29 Mod Li.Sheng 対応（S)
//		sql.append(" LEFT JOIN "); // 外部：サブクエリ2
//		sql.append(" (SELECT ");
//		sql.append(" DGEA.SYOHIN_CD, ");
//		// 2016.12.01 M.Akagi #2972 (S)
//		//sql.append(" DHTT.TENPO_CD, ");
//		//sql.append(" DGA.BUNRUI5_CD ");
//		sql.append(" DHTT.TENPO_CD ");
//		// 2016.12.01 M.Akagi #2972 (E)
//		sql.append(" FROM DT_GROUPBAIHEN_EXCLUDE_ASN DGEA "); // グループ売変除外品ASN
//		sql.append(" INNER JOIN DT_HANSOKU_TAISYO_TENPO DHTT ON "); // 内部：販促対象店舗
//		sql.append(" DGEA.THEME_CD = DHTT.THEME_CD ");
//		sql.append(" INNER JOIN DT_GROUPBAIHEN_ASN DGA ON "); // 内部：グループ売変ASN
//		sql.append(" DGEA.THEME_CD = DGA.THEME_CD ");
//		// #4064 MSTB201030 2017.02.24 S.Takayama (S)
//		sql.append(" INNER JOIN  DT_THEME DT ");
//		sql.append(" ON  DT.THEME_CD = DGA.THEME_CD ");
//		sql.append(" AND  DT.DELETE_FG = '"+ mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
//		// #4064 MSTB201030 2017.02.24 S.Takayama (E)
//		sql.append(" WHERE ");
//		sql.append(" DGA.HANBAI_START_DT <= '" + yokuBatchDt + "' AND ");
//		sql.append(" DGA.HANBAI_END_DT >= '" + yokuBatchDt + "' ");
//		sql.append(" GROUP BY ");
//		sql.append(" DGEA.SYOHIN_CD, ");
//		// 2016.12.01 M.Akagi #2972 (S)
//		//sql.append(" DHTT.TENPO_CD, ");
//		//sql.append(" DGA.BUNRUI5_CD ")
//		sql.append(" DHTT.TENPO_CD ");
//		// 2016.12.01 M.Akagi #2972 (E)
//		sql.append(" ) SUB2 ");
		// 外部：　WKグループ売変除外品ASN
		sql.append(" LEFT JOIN "); 
		sql.append(" WK_GROUPBAIHEN_EXCLUDE_ASN SUB2 "); // WKグループ売変除外品ASN
// #4433 2017.03.29 Mod Li.Sheng 対応（E)
		sql.append(" ON WTPSK.SYOHIN_CD = SUB2.SYOHIN_CD AND ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append(" TRTT.TENPO_CD = SUB2.TENPO_CD ");
		sql.append(" SUB1.TENPO_CD = SUB2.TENPO_CD ");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		// #3232 MSTB201 2016.12.16 S.Takayama (S)
		sql.append(" LEFT JOIN ( SELECT ");
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append(" KOSEI_SYOHIN_CD");
		sql.append(" HAMPER_SYOHIN_CD");
		// #4775 Mod 2017.04.25 M.Son (E)
		sql.append(" ,TENPO_CD");
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append(" ,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
		sql.append(" ,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
		// #4775 Mod 2017.04.25 M.Son (E)
		sql.append(" FROM WK_HAMPER_KOSEI");
		// 2017.01.06 M.Akagi #3570 (S)
		//sql.append(" WHERE HANBAI_END_DT != '" + yokuBatchDt + "' ");
	    // 2017.04.28 T.Han #4845対応（S)
		//sql.append(" WHERE HANBAI_END_DT != '" + batchDt + "' ");
	    // 2017.04.28 T.Han #4845対応（E)
		// 2017.01.06 M.Akagi #3570 (E)
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append(" GROUP BY KOSEI_SYOHIN_CD,TENPO_CD");
		sql.append(" GROUP BY HAMPER_SYOHIN_CD,TENPO_CD");
		// #4775 Mod 2017.04.25 M.Son (E)
		sql.append(" )SUB3");
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append(" ON WTPSK.SYOHIN_CD = SUB3.KOSEI_SYOHIN_CD");
		sql.append(" ON WTPSK.SYOHIN_CD = SUB3.HAMPER_SYOHIN_CD");
		// #4775 Mod 2017.04.25 M.Son (E)
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(S)
		//sql.append(" AND TRTT.TENPO_CD = SUB3.TENPO_CD");
		sql.append(" AND SUB1.TENPO_CD = SUB3.TENPO_CD");
		// #3686 MSTB201 2017.02.15 T.Yajima Mod(E)
		// #3232 MSTB201 2016.12.16 S.Takayama (E)

		sql.append(" WHERE "); // 抽出条件
// #4418 2017.03.28 Del Li.Sheng 対応（S)
//		sql.append(" WTPSK.DELETE_FG = '" + DELETE_FLG + "' AND ");
		// #3686 MSTB201 2017.02.15 T.Yajima Add(S)
//		sql.append(" WTPSR.DELETE_FG = '" + DELETE_FLG + "' AND ");
		// #3686 MSTB201 2017.02.15 T.Yajima Add(E)
// #4418 2017.03.28 Del Li.Sheng 対応（E)
		sql.append(" WTPSK.SYOHIN_KB <> '" + SYOHIN_KBN_SHIIRE + "' AND ");
		// #6367 DEL 2022.01.27 SIEU.D (S)
		// sql.append(" GET_JAN_SYUBETSU(WTPSK.SYOHIN_CD) <> '" + EDI_CD + "' AND ");
		// #6367 DEL 2022.01.27 SIEU.D (E)
		// #3232 MSTB201 2016.12.16 S.Takayama (S)
		sql.append(" (");
		// #3686 MSTB201 2017.02.15 T.Yajima Del(S)
//		sql.append(" (");
//		sql.append(" SUB3.BAIKA_HAISHIN_FG='0'");
//		sql.append(" )");
//		sql.append(" OR");
//		sql.append(" (");
//		// #3232 MSTB201 2016.12.16 S.Takayama (E)
//		//add 2016.11.17 #1750 nv.cuong(S)
//		sql.append(" WTPSK.BAIKA_HAISHIN_FG = '0' AND ");
//		//add 2016.11.17 #1750 nv.cuong(E)
//		sql.append(" NOT EXISTS ( ");
//		sql.append(" SELECT ");
//		sql.append(" WTPSR.BUNRUI1_CD ");
//		sql.append(" FROM WK_TEC_PLU_SYOHIN_REIGAI WTPSR "); // WK_PLU例外
//		sql.append(" WHERE ");
//		// add 2016.11.10 #1750 nv.cuong(S)
//		//sql.append(" WTPSR.PLU_SEND_DT = '" + TOKUBETU_DT + "' AND ");
//		sql.append(" WTPSR.BAIKA_HAISHIN_FG = '1' AND " );
//		// add 2016.11.10 #1750 nv.cuong(E)
//		sql.append(" WTPSR.BUNRUI1_CD = WTPSK.BUNRUI1_CD AND ");
//		sql.append(" WTPSR.SYOHIN_CD = WTPSK.SYOHIN_CD AND ");
//		sql.append(" WTPSR.TENPO_CD = SUB1.TENPO_CD) ");
//		// #3232 MSTB201 2016.12.16 S.Takayama (S)
//		sql.append(" )");
		// #3686 MSTB201 2017.02.15 T.Yajima Del(E)
		// #3686 MSTB201 2017.02.15 T.Yajima Add(S)
		// #4775 Mod 2017.04.25 M.Son (S)
		//sql.append(" SUB3.BAIKA_HAISHIN_FG='0'");
		//sql.append(" OR");
	    // 2017.04.28 T.Han #4845対応（S)
		//sql.append(" NVL(SUB3.BAIKA_HAISHIN_FG,'0') <> '1' AND");
		sql.append(" 	( ");
		sql.append(" 	WTPSK.HAMPER_SYOHIN_FG = '1' AND ");
		sql.append(" 	SUB3.BAIKA_HAISHIN_FG = '0' ");
		sql.append(" 	) ");
		sql.append(" 	OR ");
		// 2017.04.28 T.Han #4845対応（E)
		// #4775 Mod 2017.04.25 M.Son (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
//		sql.append(" WTPSR.BAIKA_HAISHIN_FG = '0' ");
	    // 2017.04.28 T.Han #4845対応（S)
		//sql.append(" (WTPSR.BAIKA_HAISHIN_FG = '0' AND WTPSK.DELETE_FG = '" + DELETE_FLG + "' AND WTPSR.DELETE_FG = '" + DELETE_FLG + "') ");
		sql.append(" (WTPSK.HAMPER_SYOHIN_FG = '0' AND WTPSR.BAIKA_HAISHIN_FG = '0' AND WTPSK.DELETE_FG = '" + DELETE_FLG + "' AND WTPSR.DELETE_FG = '" + DELETE_FLG + "') ");
	    // 2017.04.28 T.Han #4845対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
		// #3686 MSTB201 2017.02.15 T.Yajima Add(E)
		sql.append(" )");
		// #3232 MSTB201 2016.12.16 S.Takayama (E)

		return sql.toString();
	}

	/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
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
