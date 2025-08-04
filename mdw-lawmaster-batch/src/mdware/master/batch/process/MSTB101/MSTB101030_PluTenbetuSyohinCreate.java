package mdware.master.batch.process.MSTB101;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
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
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB101030_PluTenbetuSyohinCreate.java クラス</p>
 * <p>説明　: TMP商品マスタ、TMP店別商品例外マスタ、TMP単品店取扱マスタの内容を、<br>
 *            WK_PLU商品、WK_PLU例外、WK_PLU店別商品に取込む</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.25) T.Ooshiro [CUS00063] ランドローム様対応 POSインターフェイス仕様変更対応
 * @version 3.01 (2013.12.18) T.Ooshiro [CUS00063] POSインターフェイス仕様変更対応  結合テスト課題対応 №020
 * @version 3.02 (2013.12.24) T.Ooshiro [CUS00063] POSインターフェイス仕様変更対応  結合テスト課題対応 №026
 * @version 3.03 (2014.02.10) S.Arakawa [シス0018] 項目セット内容変更
 * @version 3.04 (2014.02.14) S.Arakawa [シス0041] PLU店舗区分参照対応
 * @version 3.05 (2015.09.08) THO.VT  FIVImart様対応
 * @version 3.06 (2016.04.05) Huy.NT 設計書No.650(#1211) FIVImart様対応
 * @Version 3.07 (2016.05.13) M.Kanno #1336対応
 * @Version 3.08 (2016.05.16) M.Kanno #1332対応 金額桁数エラー判定削除
 * @version 3.09 (2016.09.02) #1566対応 VINX t.han
 * @version 3.09 (2016.09.08) #1921対応 VINX t.han
 * @version 3.10 (2016.09.16) #1954対応 VINX h.sakamoto
 * @version 3.11  (2016.09.29) Li.Sheng #1954対応
 * @version 3.12 (2016.10.26) #2526 nv.cuong 対応 VINX
 * @version 3.13 (2016.11.04) #1750対応 VINX t.han
 * @version 3.14 (2016.11.28) #2839対応 VINX S.Takayama
 * @version 3.15 (2016.11.29) #2629対応 VINX t.han
 * @version 3.16 (2016.12.09) #3049対応 VINX t.han
 * @version 3.17 (2017.01.27) #3720対応 VINX t.yajima
 * @version 3.18 (2017.02.09) #3765対応 T.Han FIVImart対応
 * @version 3.19 (2017.02.14) #3765対応 M.Son FIVImart対応
 * @version 3.20 (2017.02.13) #3686対応 T.Yajima FIVImart対応
 * @version 3.21 (2017.04.28) #4845対応 M.Son FIVImart対応
 * @version 3.22 (2017.05.23) #5100対応 T.Han FIVImart対応
 * @version 3.23 (2020.07.10) KHAI.NN #6167 MKV対応
 * @version 3.24 (2021.10.22) Duy.HK #6367
 * @version 3.25 (2021.12.14) KHOI.ND #6406
 * @version 3.26 (2022.01.13) SIEU.D #6486
 * @version 3.27 (2022.01.27) SIEU.D #6367
 * @version 3.28 (2022.10.05) VU.TD #6655
 * @version 3.29 (2022.11.09) VU.TD #6698
 * @version 3.30 (2023.02.15) DUY.HK #6728
 * @Version 3.31 (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB101030_PluTenbetuSyohinCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_WK_PLU_SYOHIN = "WK_TEC_PLU_SYOHIN"; // WK_PLU商品
	private static final String TABLE_WK_PLU_SYOHIN_REIGAI = "WK_TEC_PLU_SYOHIN_REIGAI"; // WK_PLU例外
	private static final String TABLE_WK_PLU = "WK_TEC_PLU"; // WK_PLU店別商品

	// #1332対応 2015/09/18 M.Kanno del (S)
	/** 金額項目桁数エラー判定値 */
	//private static final String KINGAKU_ERROR_CHECK_VL = "999999";
	// #1332対応 2015/09/18 M.Kanno del (E)
	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;

	/** ゼロ (定数) */
	private static final String CONST_ZERO = "0";
	
	/** 店舗区分 */
	private String SEND_IF_TENPO_KB = null; 
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB101030_PluTenbetuSyohinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB101030_PluTenbetuSyohinCreate() {
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
			Map tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_TENPO_KB);	
			SEND_IF_TENPO_KB = SqlSupportUtil.createInString(tenpoMap.keySet().toArray()); 

			// WK_PLU商品 のTRUNCATE
			writeLog(Level.INFO_INT, "WK_PLU商品 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_PLU_SYOHIN);
			writeLog(Level.INFO_INT, "WK_PLU商品 を削除処理を終了します。");

			// WK_PLU例外 のTRUNCATE
			writeLog(Level.INFO_INT, "WK_PLU例外 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_PLU_SYOHIN_REIGAI);
			writeLog(Level.INFO_INT, "WK_PLU例外 を削除処理を終了します。");

			// WK_PLU店別商品 のTRUNCATE
			writeLog(Level.INFO_INT, "WK_PLU店別商品 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_WK_PLU);
			writeLog(Level.INFO_INT, "WK_PLU店別商品 を削除処理を終了します。");

			// WK_PLU商品 の登録
			writeLog(Level.INFO_INT, "WK_PLU商品 登録処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluSyohinInsertSql());
			writeLog(Level.INFO_INT, "WK_PLU商品 を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_PLU商品 登録処理を終了します。");

			db.commit();

			// WK_PLU例外 の登録
			writeLog(Level.INFO_INT, "WK_PLU例外 登録処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluSyohinReigaiInsertSql());
			writeLog(Level.INFO_INT, "WK_PLU例外 を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_PLU例外 登録処理を終了します。");

			db.commit();

			// WK_PLU店別商品 の登録
			writeLog(Level.INFO_INT, "WK_PLU店別商品 登録処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluInsertSql());
			writeLog(Level.INFO_INT, "WK_PLU店別商品 を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "WK_PLU店別商品 登録処理を終了します。");

			db.commit();

			//2016/9/16 VINX h.sakamoto #1954対応 (S)
			// WK_PLU店別商品 例外反映
//			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外反映処理を開始します。");
//			iRec = db.executeUpdate(getWkTecPluMargeReigaiSql());
//			writeLog(Level.INFO_INT, "WK_PLU店別商品 を" + iRec + "件マージしました。");
//			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外反映処理を終了します。");
//
//			db.commit();
			//2016/9/16 VINX h.sakamoto #1954対応 (E)

			//2016/9/16 VINX h.sakamoto #1954対応 (S)
			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外更新処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluUpdateReigaiSql());
			writeLog(Level.INFO_INT, "WK_PLU店別商品 を" + iRec + "件更新しました。");
			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外更新処理を終了します。");

			db.commit();
			//2016/9/16 VINX h.sakamoto #1954対応 (E)

			//2016/9/16 VINX h.sakamoto #1954対応 (S)
			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外登録処理を開始します。");
			iRec = db.executeUpdate(getWkTecPluInsertReigaiSql());
			writeLog(Level.INFO_INT, "WK_PLU店別商品 を" + iRec + "件登録しました。");
			writeLog(Level.INFO_INT, "WK_PLU店別商品 例外登録処理を終了します。");
			//2016/9/16 VINX h.sakamoto #1954対応 (E)

			db.commit();

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
	 * WK_TEC_PLU_SYOHINを登録するSQLを取得する
	 * 
	 * @return WK_TEC_PLU_SYOHIN登録SQL
	 */
	private String getWkTecPluSyohinInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		//#3765 MSTB101 2017.02.14 M.Son (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//#3765 MSTB101 2017.02.14 M.Son (E)

		sql.append("INSERT /*+ APPEND */ INTO WK_TEC_PLU_SYOHIN ");
		sql.append("	( ");
		sql.append("	 BUNRUI1_CD ");
		sql.append("	,SYOHIN_CD ");
		// ADD 2015/09/18 THO.VT (S)
		sql.append("	,OLD_SYOHIN_CD ");
		// ADD 2015/09/18 THO.VT (E)
		sql.append("	,YUKO_DT ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,PLU_SEND_DT ");
		sql.append("	,BAIKA_HAISHIN_FG ");
		sql.append("	,BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("	,HINMEI_KANJI_NA");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,REC_HINMEI_KANA_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,ZEI_KB ");
		sql.append("	,SYOHIN_KB ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("	,NENREI_SEIGEN_KB ");
		sql.append("	,NENREI ");
		sql.append("	,KAN_KB ");
		sql.append("	,HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END
		// 2015/09/09 THO.VT (S)
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		// 2015/09/09 THO.VT (E)
		sql.append("	,DELETE_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("	,HANBAI_TANI");
		sql.append("	,KEIRYOKI_NM");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,PLU_HANEI_TIME");
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER");
		sql.append("	,LABEL_SEIBUN");
		sql.append("	,LABEL_HOKAN_HOHO");
		sql.append("	,LABEL_TUKAIKATA");
	    // 2016/09/08 VINX t.han #1921対応（E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("	,LABEL_COUNTRY_NA");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,HANBAI_TANI_EN");
	    // 2017/02/09 T.Han #3765対応（E)
		// #4845 Add 2017/04/28 M.Son（S)
		sql.append("	,HAMPER_SYOHIN_FG");
		// #4845 Add 2017/04/28 M.Son（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRS.BUNRUI1_CD ");
		sql.append("	,TRS.SYOHIN_CD ");
		// 2015/09/18 THO.VT (S)
		sql.append("	,TRS.OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		sql.append("	,TRS.YUKO_DT ");
		sql.append("	,TRS.GENTANKA_VL ");
		sql.append("	,TRS.BAITANKA_VL ");
		sql.append("	,TRS.SIIRESAKI_CD ");
		// #6698 MOD 2022.11.09 VU.TD (S)
		// sql.append("	,TRS.PLU_SEND_DT ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRS.EMG_FLAG = '1' AND TRS.PLU_SEND_DT < '" + yokuBatchDt + "' THEN '" + yokuBatchDt + "' ");
		sql.append("		ELSE TRS.PLU_SEND_DT ");
		sql.append("	END PLU_SEND_DT ");
		// #6698 MOD 2022.11.09 VU.TD (E)
		sql.append("	,NVL(TRS.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") ");
		sql.append("	,TRS.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("	,TRS.REC_HINMEI_KANJI_NA");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,TRS.FREE_4_KB AS MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("	,TRS.REC_HINMEI_KANJI_NA ");
        // #6486 MOD 2022.01.13 SIEU.D (S)
		// sql.append("	,SUBSTR(TRS.HINMEI_KANJI_NA,1,40) ");
		sql.append("	,SUBSTR(TRS.HINMEI_KANJI_NA|| ' ' || TRS.KIKAKU_KANJI_NA , 1, 40) ");
        // #6486 MOD 2022.01.13 SIEU.D (E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		// 項目セット先はPOSレシート品名（カナ）であるが、カナ品名をセット
//		sql.append("	,TRS.REC_HINMEI_KANA_NA ");
		sql.append("	,SUBSTR(TRS.HINMEI_KANA_NA, 0, 20) ");
		sql.append("	,TRS.KIKAKU_KANJI_NA ");
		sql.append("	,TRS.MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,TRS.ZEI_KB ");
		sql.append("	,TRS.SYOHIN_KB ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("	,TRS.NENREI_SEIGEN_KB ");
		sql.append("	,TRS.NENREI ");
		sql.append("	,TRS.KAN_KB ");
		sql.append("	,TRS.HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END
		// 2015/09/09 THO.VT (S)
		sql.append("	,TRS.BUNRUI2_CD ");
		// #1336対応 2016.05.13 M.Kanno (S)
		//sql.append("	,CASE ");
		//sql.append("		WHEN TRS.TEIKAN_KB = 1 THEN 0 ");
		//sql.append("		WHEN TRS.TEIKAN_KB = 2 THEN 1 ");
		//sql.append("	 END AS TEIKAN_KB ");
		sql.append("	,NVL(TRSA.HANBAI_HOHO_KB, 0) AS TEIKAN_KB ");
		// #1336対応 2016.05.13 M.Kanno (E)
		sql.append("	,TRS4.TAX_RT ");
        // #6367 Mod 2021.10.22 Duy.HK  (S)
//		// 2016/09/02 VINX t.han #1566対応（S)
//		/*sql.append("	,CASE ");
//		sql.append("		WHEN DGEA.SYOHIN_CD = TRS.SYOHIN_CD THEN TRS.BUNRUI5_CD ");
//		sql.append("		ELSE '' ");
//		sql.append("	 END AS SEASON_ID ");*/
//		//sql.append("	,NULL AS SEASON_ID ");
//		// 2016/09/02 VINX t.han #1566対応（E)
// #6728 MOD 2023.02.15 DUY.HK (S)
//        sql.append("       ,CASE ");
//        sql.append("            WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//     // #6655 ADD 2022.10.04 VU.TD (S)
//        sql.append("            WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//     // #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("            ELSE NULL ");
//        sql.append("        END AS SEASON_ID ");
// #6367 Mod 2021.10.22 Duy.HK  (E)
      sql.append("       ,CASE ");
      sql.append("            WHEN TRS.WARIBIKI_KB = '0' THEN '999999' ");
      sql.append("            WHEN TRS.WARIBIKI_KB = '2' THEN '888888' ");
      sql.append("            ELSE NULL ");
      sql.append("        END AS SEASON_ID ");
// #6728 MOD 2023.02.15 DUY.HK (E)
		sql.append("	,TRS.SYOHI_KIGEN_DT ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRSA.MEMBER_DISCOUNT_FG = 0 THEN 2 ");
		sql.append("		WHEN TRSA.MEMBER_DISCOUNT_FG = 1 THEN 0 ");
		sql.append("	 END AS MEMBER_DISCOUNT_FG ");
		// 2015/09/09 THO.VT (E)
		sql.append("	,TRS.DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("	,RN.KANJI_NA ");
		sql.append("	,TRSA.SYOHIN_EIJI_NA ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,TRS.PLU_HANEI_TIME ");
		sql.append("	,TRS.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,TRSA.LABEL_SEIBUN ");
		sql.append("	,TRSA.LABEL_HOKAN_HOHO ");
		sql.append("	,TRSA.LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
	    // 2016/12/09 VINX t.han #3049対応（S)
	    // 2017/02/09 T.Han #3765対応（S)
		//sql.append("	,RN1.KANJI_NA ");
		sql.append("	,RN1.KANJI_RN ");
		sql.append("	,RN2.KANJI_RN ");
	    // 2017/02/09 T.Han #3765対応（E)
	    // 2016/12/09 VINX t.han #3049対応（E)
		// #4845 Add 2017/04/28 M.Son（S)
		sql.append("	,TRSA.HAMPER_SYOHIN_FG ");
		// #4845 Add 2017/04/28 M.Son（E)
		sql.append("FROM ");
		sql.append("	TMP_R_SYOHIN TRS ");
// #6728 DEL 2023.02.15 DUY.HK (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//        sql.append("    LEFT JOIN  ");
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
//     // #6620 MOD 2022.11.18 VU.TD (S)
////        sql.append("                                       RS_2.BUNRUI1_CD = RS_1.BUNRUI1_CD");
////        sql.append("                                   AND RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        sql.append("                                    RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//     // #6620 MOD 2022.11.18 VU.TD (E)
//        sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//        sql.append("                              ) ");
//        sql.append("        )  RS  ");
//        sql.append("     ON    TRS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//     // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND TRS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//     // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
// #6728 DEL 2023.02.15 DUY.HK (E)
		sql.append("	INNER JOIN  ");
		sql.append("		( ");
		sql.append("			SELECT  ");
		sql.append("				 TRS.SYOHIN_CD  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("				,TRS.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("				,TRS.PLU_SEND_DT ");
		sql.append("				,TRS.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("				,MIN(TRS.DELETE_FG) AS DELETE_FG ");
		sql.append("			FROM ");
		sql.append("				TMP_R_SYOHIN TRS  ");
		sql.append("				INNER JOIN ");
		sql.append("					(  ");
		sql.append("						SELECT  ");
		sql.append("							 TRS.SYOHIN_CD  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("							,MAX(TRS.YUKO_DT) AS YUKO_DT  ");
		// 2016.10.16 #2526 nv.cuong(S)
		sql.append("							,MAX(TRS.YUKO_DT) AS YUKO_DT  ");
		//sql.append("							,MAX(TRS.PLU_SEND_DT) AS PLU_SEND_DT  ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("						FROM  ");
		sql.append("							TMP_R_SYOHIN TRS  ");
		sql.append("						WHERE  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("							TRS.YUKO_DT	<= '" + yokuBatchDt + "'  ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("							TRS.PLU_SEND_DT	<= '" + yokuBatchDt + "'  ");
		sql.append("							TRS.YUKO_DT	<= '" + yokuBatchDt + "'  ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
	    // 2016.11.29 T.han #2629対応（S)
		sql.append("							AND TRS.PLU_SEND_DT	<= '" + yokuBatchDt + "'  ");
	    // 2016.11.29 T.han #2629対応（E)
		sql.append("						GROUP BY  ");
		sql.append("							 TRS.SYOHIN_CD  ");
		sql.append("					) TRS1  ");
		sql.append("					ON  ");
		sql.append("						TRS.SYOHIN_CD	= TRS1.SYOHIN_CD	AND  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("						TRS.YUKO_DT		= TRS1.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(S)
		sql.append("						TRS.YUKO_DT		= TRS1.YUKO_DT ");
		//sql.append("						TRS.PLU_SEND_DT		= TRS1.PLU_SEND_DT ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("			GROUP BY ");
		sql.append("				 TRS.SYOHIN_CD  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("				,TRS.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("				,TRS.PLU_SEND_DT ");
		sql.append("				,TRS.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("		) TRS2 ");
		sql.append("		ON ");
		sql.append("			TRS.SYOHIN_CD	= TRS2.SYOHIN_CD	AND  ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("			TRS.YUKO_DT		= TRS2.YUKO_DT		AND ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("			TRS.PLU_SEND_DT		= TRS2.PLU_SEND_DT		AND ");
		sql.append("			TRS.YUKO_DT		= TRS2.YUKO_DT		AND ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("			TRS.DELETE_FG	= TRS2.DELETE_FG ");
		// ADD 2015/09/08 THO.VT (S)
		sql.append("	INNER JOIN  ");
		sql.append("		( ");
		sql.append("			SELECT  ");
		sql.append("				 TRTR.TAX_RATE_KB ");
		sql.append("				 , TRTR.TAX_RT  ");
		sql.append("			FROM ");
		sql.append("				TMP_R_TAX_RATE TRTR  ");
		sql.append("				INNER JOIN ");
		sql.append("					(  ");
		sql.append("						SELECT  ");
		sql.append("							 TRTR.TAX_RATE_KB  ");
		sql.append("							, MAX(TRTR.YUKO_DT) AS YUKO_DT  ");
		sql.append("						FROM  ");
		sql.append("							TMP_R_TAX_RATE TRTR  ");
		sql.append("						WHERE  ");
		sql.append("							TRTR.YUKO_DT	<= '" + yokuBatchDt + "'  ");
		sql.append("							AND TRTR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("						GROUP BY  ");
		sql.append("							 TRTR.TAX_RATE_KB  ");
		sql.append("					) TRS3  ");
		sql.append("				ON ");
		sql.append("					TRTR.TAX_RATE_KB	= TRS3.TAX_RATE_KB ");
		sql.append("					AND TRTR.YUKO_DT	= TRS3.YUKO_DT ");
		sql.append("		) TRS4 ");
		sql.append("		ON ");
		sql.append("			TRS.TAX_RATE_KB	= TRS4.TAX_RATE_KB ");
	    // 2016/09/02 VINX t.han #1566対応（S)
		/*sql.append("	LEFT JOIN ");
		sql.append("		DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
		sql.append("	ON ");
		sql.append("		DGEA.SYOHIN_CD = TRS.SYOHIN_CD ");*/
	    // 2016/09/02 VINX t.han #1566対応（E)
		sql.append("	INNER JOIN ");
		sql.append("		TMP_R_SYOHIN_ASN TRSA ");
		sql.append("	ON ");
		sql.append("		TRSA.SYOHIN_CD = TRS.SYOHIN_CD ");
		sql.append("		AND TRS.YUKO_DT = TRSA.YUKO_DT ");
		// ADD 2015/09/08 THO.VT (E)
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN ");
		sql.append("	ON ");
		sql.append("		RN.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
		sql.append("		TRIM(TRS.HANBAI_TANI) = TRIM(RN.CODE_CD) ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN1 ");
		sql.append("	ON ");
		//#3765 MSTB101 2017.02.14 M.Son (S)
		//sql.append("		RN1.SYUBETU_NO_CD = '4070' AND ");
		sql.append("		RN1.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COUNTRY_DIVISION, userLocal) + "' AND ");
		//#3765 MSTB101 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRSA.COUNTRY_CD) = TRIM(RN1.CODE_CD) ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN2 ");
		sql.append("	ON ");
		//#3765 MSTB101 2017.02.14 M.Son (S)
		//sql.append("		RN2.SYUBETU_NO_CD = '3040' AND ");
		sql.append("		RN2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
		//#3765 MSTB101 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRS.HANBAI_TANI) = TRIM(RN2.CODE_CD) ");
	    // 2017/02/09 T.Han #3765対応（E)
		return sql.toString();
	}

	/**
	 * WK_TEC_PLU_SYOHIN_REIGAIを登録するSQLを取得する
	 * 
	 * @return WK_TEC_PLU_SYOHIN_REIGAI登録SQL
	 */
	private String getWkTecPluSyohinReigaiInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_TEC_PLU_SYOHIN_REIGAI ");
		sql.append("	( ");
		sql.append("	 BUNRUI1_CD ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,TENPO_CD ");
		sql.append("	,YUKO_DT ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,PLU_SEND_DT ");
		sql.append("	,BAIKA_HAISHIN_FG ");
		sql.append("	,DELETE_FG ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,PLU_HANEI_TIME ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 TRT.BUNRUI1_CD ");
		sql.append("	,TRT.SYOHIN_CD ");
		sql.append("	,TRT.TENPO_CD ");
		sql.append("	,TRT.YUKO_DT ");
		sql.append("	,TRT.GENTANKA_VL ");
		sql.append("	,TRT.BAITANKA_VL ");
		sql.append("	,TRT.SIIRESAKI_CD ");
		// #6698 MOD 2022.11.09 VU.TD (S)
		// sql.append("	,TRT.PLU_SEND_DT ");
		sql.append("	,CASE ");
		sql.append("		WHEN TRT.EMG_FLAG = '1' AND TRT.PLU_SEND_DT < '" + yokuBatchDt + "' THEN '" + yokuBatchDt + "' ");
		sql.append("		ELSE TRT.PLU_SEND_DT ");
		sql.append("	END PLU_SEND_DT ");
		// #6698 MOD 2022.11.09 VU.TD (E)
		sql.append("	,NVL(TRT.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") ");
		sql.append("	,TRT.DELETE_FG ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,TRT.PLU_HANEI_TIME ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("FROM ");
		sql.append("	TMP_R_TENSYOHIN_REIGAI TRT ");
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				 TRT.SYOHIN_CD ");
		sql.append("				,TRT.TENPO_CD ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("				,MAX(TRT.YUKO_DT) AS YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("				,MAX(TRT.PLU_SEND_DT) AS PLU_SEND_DT ");
		sql.append("				,MAX(TRT.YUKO_DT) AS YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
		sql.append("			FROM ");
		sql.append("				TMP_R_TENSYOHIN_REIGAI TRT ");
		sql.append("			WHERE ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("				TRT.YUKO_DT	<= '" + yokuBatchDt + "' ");
		// 2016.10.16 #2526 nv.cuong(S)
		/*sql.append("				TRT.PLU_SEND_DT	< '" + yokuBatchDt + "' ");
		sql.append("				OR ");
		sql.append("				( ");
		sql.append("				 	TRT.PLU_SEND_DT = '" + yokuBatchDt + "'");
		sql.append("				    AND TRT.PLU_HANEI_TIME IS NULL ");
		sql.append("				 )");*/
		sql.append("				TRT.YUKO_DT	<= '" + yokuBatchDt + "' ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)
	    // 2016.11.29 T.han #2629対応（S)
		sql.append("				AND ");
		sql.append("				( ");
		sql.append("					TRT.PLU_SEND_DT	= '" + SPECIAL_DATE + "'	OR ");
		sql.append("					TRT.PLU_SEND_DT	<= '" + yokuBatchDt + "'	   ");
		sql.append("				) ");
		// 2016.11.29 T.han #2629対応（E)
		sql.append("			GROUP BY ");
		sql.append("				 TRT.SYOHIN_CD ");
		sql.append("				,TRT.TENPO_CD ");
		sql.append("		) TRT1 ");
		sql.append("		ON ");
		sql.append("			TRT.SYOHIN_CD	= TRT1.SYOHIN_CD	AND ");
		sql.append("			TRT.TENPO_CD	= TRT1.TENPO_CD		AND ");
		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("			TRT.YUKO_DT		= TRT1.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(S)
		//sql.append("			TRT.PLU_SEND_DT		= TRT1.PLU_SEND_DT ");
		sql.append("			TRT.YUKO_DT		= TRT1.YUKO_DT ");
		// 2016.10.16 #2526 nv.cuong(E)
		//2016/9/16 VINX h.sakamoto #1954対応 (E)

		return sql.toString();
	}

	/**
	 * WK_TEC_PLUを登録するSQLを取得する
	 * 
	 * @return WK_TEC_PLU登録SQL
	 */
	private String getWkTecPluInsertSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO WK_TEC_PLU ");
		sql.append("	( ");
		sql.append("	 BUNRUI1_CD ");
		sql.append("	,SYOHIN_CD ");
		// ADD 2015/09/18 THO.VT (S)
		sql.append("	,OLD_SYOHIN_CD ");
		// ADD 2015/09/18 THO.VT (E)
		sql.append("	,TENPO_CD ");
		sql.append("	,GENTANKA_VL ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,PLU_SEND_DT ");
		sql.append("	,BAIKA_HAISHIN_FG ");
		sql.append("	,BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("	,HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	,REC_HINMEI_KANJI_NA ");
		sql.append("	,REC_HINMEI_KANA_NA ");
		sql.append("	,KIKAKU_KANJI_NA ");
		sql.append("	,MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,ZEI_KB ");
		sql.append("	,ERR_KB ");
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("	,NENREI_SEIGEN_KB ");
		sql.append("	,NENREI ");
		sql.append("	,KAN_KB ");
		sql.append("	,HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/10 THO.VT (S)
		sql.append("	,BUNRUI2_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		// 2015/09/10 THO.VT (E)
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,INSERT_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,UPDATE_TS ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("	,HANBAI_TANI ");
		sql.append("	,KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,PLU_HANEI_TIME ");
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,LABEL_SEIBUN ");
		sql.append("	,LABEL_HOKAN_HOHO ");
		sql.append("	,LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("	,GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("	,LABEL_COUNTRY_NA");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,HANBAI_TANI_EN");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("    ,DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 WTPS.BUNRUI1_CD ");
		sql.append("	,WTPS.SYOHIN_CD ");
		// 2015/09/18 THO.VT (S)
		sql.append("	,WTPS.OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		// #3686対応 2017/02/13 T.Yajima Mod(S)
		//sql.append("    ,TRTT.TENPO_CD ");
		sql.append("	,TRT.TENPO_CD ");
		// #3686対応 2017/02/13 T.Yajima Mod(E)
		sql.append("	,WTPS.GENTANKA_VL ");
		sql.append("	,WTPS.BAITANKA_VL ");
		sql.append("	,WTPS.SIIRESAKI_CD ");
		sql.append("	,WTPS.PLU_SEND_DT ");
		sql.append("	,WTPS.BAIKA_HAISHIN_FG ");
		sql.append("	,WTPS.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("	,WTPS.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,WTPS.MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	,WTPS.REC_HINMEI_KANJI_NA ");
		sql.append("	,WTPS.REC_HINMEI_KANA_NA ");
		sql.append("	,WTPS.KIKAKU_KANJI_NA ");
		sql.append("	,WTPS.MAKER_KIBO_KAKAKU_VL ");
		sql.append("	,WTPS.ZEI_KB ");
		// #1332対応 2015/09/18 M.Kanno (S)
		//sql.append("	,CASE ");
		//sql.append("		WHEN WTPS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		//sql.append("		WHEN WTPS.BAITANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		//sql.append("		ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' ");
		//sql.append("	 END AS ERR_KB ");
		sql.append("	 ,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB ");
		// #1332対応 2015/09/18 M.Kanno (E)
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("	,WTPS.NENREI_SEIGEN_KB ");
		sql.append("	,WTPS.NENREI ");
		sql.append("	,WTPS.KAN_KB ");
		sql.append("	,WTPS.HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/10 THO.VT (S)
		sql.append("	,WTPS.BUNRUI2_CD ");
		sql.append("	,WTPS.TEIKAN_FG ");
		sql.append("	,WTPS.ZEI_RT ");
// #6728 MOD 2023.02.15 DUY.HK (S)
//        // #6367 Mod 2021.10.22 Duy.HK (S)
//		//sql.append("	,WTPS.SEASON_ID ");
//        sql.append("    ,CASE ");
//        sql.append("        WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)
//        sql.append("        WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//        // #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("        ELSE WTPS.SEASON_ID ");
//        sql.append("    END AS SEASON_ID ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
		sql.append("	,WTPS.SEASON_ID ");
// #6728 MOD 2023.02.15 DUY.HK (E)
		sql.append("	,WTPS.SYOHI_KIGEN_DT ");
		sql.append("	,WTPS.CARD_FG ");
		// 2015/09/10 THO.VT (E)
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("	,WTPS.HANBAI_TANI ");
		sql.append("	,WTPS.KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("	,WTPS.PLU_HANEI_TIME ");
		sql.append("	,WTPS.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,WTPS.LABEL_SEIBUN ");
		sql.append("	,WTPS.LABEL_HOKAN_HOHO ");
		sql.append("	,WTPS.LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("	,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("	,WTPS.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,WTPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("    ,WTPS.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("FROM ");
		sql.append("	WK_TEC_PLU_SYOHIN WTPS ");
// #6728 DEL 2023.02.15 DUY.HK (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//        sql.append("    LEFT JOIN  ");
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
//     // #6620 MOD 2022.11.18 VU.TD (S)
////        sql.append("                                       RS_2.BUNRUI1_CD = RS_1.BUNRUI1_CD");
////        sql.append("                                   AND RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        sql.append("                                    RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//     // #6620 MOD 2022.11.18 VU.TD (E)
//        sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//        sql.append("                              ) ");
//        sql.append("        )  RS  ");
//        sql.append("     ON    WTPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//     // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND WTPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//     // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
// #6728 DEL 2023.02.15 DUY.HK (E)
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("		SELECT ");
		// #3686対応 2017/02/13 T.Yajima Mod(S)
		//sql.append("			 TRTT.TENPO_CD ");
		//sql.append("			,TRTT.BUNRUI1_CD ");
		//sql.append("			,TRTT.SYOHIN_CD ");
		//sql.append("		FROM ");
		//sql.append("			TMP_R_TANPINTEN_TORIATUKAI TRTT ");
		//sql.append("		) TRTT ");
		//sql.append("		ON ");
		//sql.append("			WTPS.BUNRUI1_CD	= TRTT.BUNRUI1_CD	AND ");
		//sql.append("			WTPS.SYOHIN_CD	= TRTT.SYOHIN_CD ");
		sql.append("             WTPSR.SYOHIN_CD ");
		sql.append("            ,WTPSR.TENPO_CD ");
		sql.append("            ,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("            ,WTPSR.DELETE_FG ");
		sql.append("        FROM ");
		sql.append("            WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("        ) WTPSR ");
		sql.append("        ON ");
		sql.append("            WTPS.SYOHIN_CD = WTPSR.SYOHIN_CD ");
		sql.append("            AND WTPSR.BAIKA_HAISHIN_FG = '0'  ");
		// #3686対応 2017/02/13 T.Yajima Mod(E)
		// 2017.05.23 #5100対応 T.Han (S)
		sql.append("            AND WTPSR.DELETE_FG = '0' ");
		// 2017.05.23 #5100対応 T.Han (E)
		sql.append("	INNER JOIN ");
		sql.append("		( ");
		sql.append("			SELECT ");
		sql.append("				TRT.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("				,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
		sql.append("			FROM ");
		sql.append("				TMP_R_TENPO TRT ");
		sql.append("			WHERE ");
		sql.append("				TRT.ZAIMU_END_DT	> '" + batchDt + "'	AND ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("				TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("				TRT.TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") ");
		sql.append("				TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("		) TRT ");
		sql.append("		ON ");
		// #3686対応 2017/02/13 T.Yajima Mod(S)
		//sql.append("			TRIM(TRT.TENPO_CD)	= TRIM(TRTT.TENPO_CD) ");
		sql.append("          TRIM(TRT.TENPO_CD) = WTPSR.TENPO_CD ");
		// #3686対応 2017/02/13 T.Yajima Mod(E)
		sql.append("WHERE ");
        // #3686対応 2017/02/13 T.Yajima Del(S)
		//sql.append("	WTPS.DELETE_FG						 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'			AND ");
        // #3686対応 2017/02/13 T.Yajima Del(E)
		sql.append("	WTPS.SYOHIN_KB						<>  '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'		AND ");
		sql.append("	WTPS.PLU_SEND_DT					 = '" + yokuBatchDt + "'										AND ");
		// #6367 MOD 2022.01.27 SIEU.D (S)
//		//2016/9/16 VINX h.sakamoto #1954対応 (S)
//		sql.append("	WTPS.PLU_HANEI_TIME					IS NULL															AND ");
//		//2016/9/16 VINX h.sakamoto #1954対応 (E)
//		//2017.01.24 T.Yajima #3720対応（S)
//		sql.append("	GET_JAN_SYUBETSU(WTPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'	 ");
//		//2017.01.24 T.Yajima #3720対応（E)
		sql.append("	WTPS.PLU_HANEI_TIME					IS NULL ");
		// #6367 MOD 2022.01.27 SIEU.D (E)
		/*//2017.01.24 T.Yajima #3720対応（S)
		sql.append("	NOT EXISTS ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			 WTPSR.BUNRUI1_CD ");
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("		WHERE ");
		// 2016.11.04 T.han #1750対応（S)
		//sql.append("			WTPSR.PLU_SEND_DT	= '" + SPECIAL_DATE + "'	AND ");
		sql.append("			WTPSR.PLU_SEND_DT	<> '" + SPECIAL_DATE + "'	AND ");
		// 2016.11.04 T.han #1750対応（E)
		sql.append("			WTPSR.BUNRUI1_CD	= WTPS.BUNRUI1_CD			AND ");
		sql.append("			WTPSR.SYOHIN_CD		= WTPS.SYOHIN_CD			AND ");
		sql.append("			WTPSR.TENPO_CD		= TRT.TENPO_CD ");
		sql.append("	 ");
		sql.append("	) ");
		//2017.01.24 T.Yajima #3720対応（E)*/
		return sql.toString();
	}

	/**
	 * WK_TEC_PLUに例外商品をマージするSQLを取得する
	 * 
	 * @return 例外商品をマージSQL
	 */
	private String getWkTecPluMargeReigaiSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("MERGE INTO ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	USING ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WTPS.BUNRUI1_CD                                     AS BUNRUI1_CD ");
		sql.append("			,WTPS.SYOHIN_CD                                      AS SYOHIN_CD ");
		// 2015/09/18 THO.VT (S)
		sql.append("			,WTPS.OLD_SYOHIN_CD                                  AS OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		sql.append("			,WTPSR.TENPO_CD                                      AS TENPO_CD ");
		sql.append("			,NVL(WTPSR.GENTANKA_VL		, WTPS.GENTANKA_VL)      AS GENTANKA_VL ");
		sql.append("			,NVL(WTPSR.BAITANKA_VL		, WTPS.BAITANKA_VL)      AS BAITANKA_VL ");
		sql.append("			,NVL(WTPSR.SIIRESAKI_CD		, WTPS.SIIRESAKI_CD)     AS SIIRESAKI_CD ");
		sql.append("			,NVL(WTPSR.PLU_SEND_DT		, WTPS.PLU_SEND_DT)      AS PLU_SEND_DT ");
		sql.append("			,NVL(WTPSR.BAIKA_HAISHIN_FG	, WTPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		sql.append("			,WTPS.BUNRUI5_CD                                     AS BUNRUI5_CD ");
		sql.append("			,WTPS.REC_HINMEI_KANJI_NA                            AS REC_HINMEI_KANJI_NA ");
		sql.append("			,WTPS.REC_HINMEI_KANA_NA                             AS REC_HINMEI_KANA_NA ");
		sql.append("			,WTPS.KIKAKU_KANJI_NA                                AS KIKAKU_KANJI_NA ");
		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL                           AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTPS.ZEI_KB                                         AS ZEI_KB ");
		// #1332対応 2015/09/18 M.Kanno (S)
		//sql.append("			,CASE ");
		//sql.append("				WHEN NVL(WTPSR.GENTANKA_VL, WTPS.GENTANKA_VL) > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		//sql.append("				WHEN NVL(WTPSR.BAITANKA_VL, WTPS.BAITANKA_VL) > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "' ");
		//sql.append("				ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'  ");
		//sql.append("			 END                                                 AS ERR_KB ");
		sql.append("			 ,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB ");
		// #1332対応 2015/09/18 M.Kanno (E)
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("			,WTPS.NENREI_SEIGEN_KB                               AS NENREI_SEIGEN_KB ");
		sql.append("			,WTPS.NENREI                                         AS NENREI ");
		sql.append("			,WTPS.KAN_KB                                         AS KAN_KB ");
		sql.append("			,WTPS.HOSHOUKIN                                      AS HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/14 THO.VT (S)
		sql.append("			,WTPS.BUNRUI2_CD                          			 AS BUNRUI2_CD ");
		sql.append("			,WTPS.TEIKAN_FG                               		 AS TEIKAN_FG ");
		sql.append("			,WTPS.ZEI_RT                             			 AS ZEI_RT ");
		sql.append("			,WTPS.SEASON_ID                               		 AS SEASON_ID ");
		sql.append("			,WTPS.SYOHI_KIGEN_DT                               	 AS SYOHI_KIGEN_DT ");
		sql.append("			,WTPS.CARD_FG                            	   		 AS CARD_FG ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("			,WTPS.HANBAI_TANI                                    AS HANBAI_TANI ");
		sql.append("			,WTPS.KEIRYOKI_NM                                    AS KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("			,WTPSR.PLU_HANEI_TIME                                AS PLU_HANEI_TIME ");
		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER                       AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTPS.LABEL_SEIBUN                                   AS LABEL_SEIBUN ");
		sql.append("			,WTPS.LABEL_HOKAN_HOHO                               AS LABEL_HOKAN_HOHO ");
		sql.append("			,WTPS.LABEL_TUKAIKATA                                AS LABEL_TUKAIKATA ");
		// 2016/09/08 VINX t.han #1921対応（E)
		// 2015/09/14 THO.VT (E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            ,GREATEST（WTPS.DELETE_FG ，WTPSR.DELETE_FG） ");
		// #3686対応 2017/02/13 T.Yajima Add(E) 
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 WTPSR.BUNRUI1_CD ");
		sql.append("					,WTPSR.SYOHIN_CD ");
		sql.append("					,WTPSR.TENPO_CD ");
		sql.append("					,WTPSR.GENTANKA_VL ");
		sql.append("					,WTPSR.BAITANKA_VL ");
		sql.append("					,WTPSR.SIIRESAKI_CD ");
		sql.append("					,WTPSR.PLU_SEND_DT ");
		sql.append("					,WTPSR.BAIKA_HAISHIN_FG ");
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("					,WTPSR.PLU_HANEI_TIME ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("				FROM ");
		sql.append("					WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		sql.append("				WHERE ");
		sql.append("					WTPSR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("				) WTPSR ");
		sql.append("				ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("					WTPS.BUNRUI1_CD	= WTPSR.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("					WTPS.SYOHIN_CD	= WTPSR.SYOHIN_CD ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("					SELECT ");
		sql.append("						TRT.TENPO_CD ");
		sql.append("					FROM ");
		sql.append("						TMP_R_TENPO TRT ");
		sql.append("					WHERE ");
		sql.append("						TRT.ZAIMU_END_DT	> '" + batchDt + "'	AND ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("						TRT.TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") ");
		sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("				) TRT ");
		sql.append("				ON ");
		sql.append("					TRIM(TRT.TENPO_CD)	= TRIM(WTPSR.TENPO_CD) ");
		sql.append("		WHERE ");
		sql.append("			WTPS.DELETE_FG						 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'				AND ");
		sql.append("			WTPS.SYOHIN_KB						<> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'				AND ");
		// #6367 DEL 2022.01.27 SIEU.D (S)
		// sql.append("			GET_JAN_SYUBETSU(WTPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'				AND ");
		// #6367 DEL 2022.01.27 SIEU.D (E)
		sql.append("			( ");
		sql.append("				(WTPS.PLU_SEND_DT				 = '" + yokuBatchDt + "'	AND ");
		sql.append("				 NVL(WTPSR.PLU_SEND_DT, ' ')	<> '" + SPECIAL_DATE + "')	OR ");
		sql.append("				NVL(WTPSR.PLU_SEND_DT, ' ')		 = '" + yokuBatchDt + "'	) ");
		sql.append("		) WTPSR ");
		sql.append("		ON ");
		sql.append("			( ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("			WTP.BUNRUI1_CD	= WTPSR.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("			WTP.SYOHIN_CD	= WTPSR.SYOHIN_CD	AND ");
		sql.append("			WTP.TENPO_CD	= WTPSR.TENPO_CD ");
		sql.append("			) ");
		sql.append("	WHEN MATCHED THEN ");
		sql.append("		UPDATE ");
		sql.append("			SET ");
		// 2015/09/18 THO.VT (S)
		sql.append("				 WTP.OLD_SYOHIN_CD			= WTPSR.OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		sql.append("				,WTP.GENTANKA_VL			= WTPSR.GENTANKA_VL ");
		sql.append("				,WTP.BAITANKA_VL			= WTPSR.BAITANKA_VL ");
		sql.append("				,WTP.SIIRESAKI_CD			= WTPSR.SIIRESAKI_CD ");
		sql.append("				,WTP.PLU_SEND_DT			= WTPSR.PLU_SEND_DT ");
		sql.append("				,WTP.BAIKA_HAISHIN_FG		= WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("				,WTP.BUNRUI5_CD				= WTPSR.BUNRUI5_CD ");
		sql.append("				,WTP.REC_HINMEI_KANJI_NA	= WTPSR.REC_HINMEI_KANJI_NA ");
		sql.append("				,WTP.REC_HINMEI_KANA_NA		= WTPSR.REC_HINMEI_KANA_NA ");
		sql.append("				,WTP.KIKAKU_KANJI_NA		= WTPSR.KIKAKU_KANJI_NA ");
		sql.append("				,WTP.MAKER_KIBO_KAKAKU_VL	= WTPSR.MAKER_KIBO_KAKAKU_VL ");
		sql.append("				,WTP.ZEI_KB					= WTPSR.ZEI_KB ");
		sql.append("				,WTP.ERR_KB					= WTPSR.ERR_KB ");
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("				,WTP.NENREI_SEIGEN_KB		= WTPSR.NENREI_SEIGEN_KB ");
		sql.append("				,WTP.NENREI					= WTPSR.NENREI ");
		sql.append("				,WTP.KAN_KB					= WTPSR.KAN_KB ");
		sql.append("				,WTP.HOSHOUKIN				= WTPSR.HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/14 THO.VT (S)
		sql.append("				,WTP.BUNRUI2_CD				= BUNRUI2_CD ");
		sql.append("				,WTP.TEIKAN_FG				= TEIKAN_FG ");
		sql.append("				,WTP.ZEI_RT					= ZEI_RT ");
		sql.append("				,WTP.SEASON_ID				= SEASON_ID ");
		sql.append("				,WTP.SYOHI_KIGEN_DT			= SYOHI_KIGEN_DT ");
		sql.append("				,WTP.CARD_FG				= CARD_FG ");
		// 2015/09/14 THO.VT (E)
		sql.append("				,WTP.UPDATE_USER_ID			= '" + userLog.getJobId() + "' ");
		sql.append("				,WTP.UPDATE_TS				= '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("				,WTP.HANBAI_TANI			= WTPSR.HANBAI_TANI ");
		sql.append("				,WTP.KEIRYOKI_NM			= WTPSR.KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("				,WTP.PLU_HANEI_TIME             = WTPSR.PLU_HANEI_TIME ");
		sql.append("				,WTP.SYOHI_KIGEN_HYOJI_PATTER   = WTPSR.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("				,WTP.LABEL_SEIBUN               = WTPSR.LABEL_SEIBUN ");
		sql.append("				,WTP.LABEL_HOKAN_HOHO           = WTPSR.LABEL_HOKAN_HOHO ");
		sql.append("				,WTP.LABEL_TUKAIKATA            = WTPSR.LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("	WHEN NOT MATCHED THEN ");
		sql.append("				INSERT ");
		sql.append("					( ");
		sql.append("					 BUNRUI1_CD ");
		sql.append("					,SYOHIN_CD ");
		// 2015/09/18 THO.VT (S)
		sql.append("					,OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		sql.append("					,TENPO_CD ");
		sql.append("					,GENTANKA_VL ");
		sql.append("					,BAITANKA_VL ");
		sql.append("					,SIIRESAKI_CD ");
		sql.append("					,PLU_SEND_DT ");
		sql.append("					,BAIKA_HAISHIN_FG ");
		sql.append("					,BUNRUI5_CD ");
		sql.append("					,REC_HINMEI_KANJI_NA ");
		sql.append("					,REC_HINMEI_KANA_NA ");
		sql.append("					,KIKAKU_KANJI_NA ");
		sql.append("					,MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,ZEI_KB ");
		sql.append("					,ERR_KB ");
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("					,NENREI_SEIGEN_KB ");
		sql.append("					,NENREI ");
		sql.append("					,KAN_KB ");
		sql.append("					,HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/10 THO.VT (S)
		sql.append("					,BUNRUI2_CD ");
		sql.append("					,TEIKAN_FG ");
		sql.append("					,ZEI_RT ");
		sql.append("					,SEASON_ID ");
		sql.append("					,SYOHI_KIGEN_DT ");
		sql.append("					,CARD_FG ");
		// 2015/09/10 THO.VT (E)
		sql.append("					,INSERT_USER_ID ");
		sql.append("					,INSERT_TS ");
		sql.append("					,UPDATE_USER_ID ");
		sql.append("					,UPDATE_TS ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("					,HANBAI_TANI ");
		sql.append("					,KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("					,PLU_HANEI_TIME ");
		sql.append("					,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,LABEL_SEIBUN ");
		sql.append("					,LABEL_HOKAN_HOHO ");
		sql.append("					,LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("					) ");
		sql.append("				VALUES ");
		sql.append("					( ");
		sql.append("					 WTPSR.BUNRUI1_CD ");
		sql.append("					,WTPSR.SYOHIN_CD ");
		// 2015/09/18 THO.VT (S)
		sql.append("					,WTPSR.OLD_SYOHIN_CD ");
		// 2015/09/18 THO.VT (E)
		sql.append("					,WTPSR.TENPO_CD ");
		sql.append("					,WTPSR.GENTANKA_VL ");
		sql.append("					,WTPSR.BAITANKA_VL ");
		sql.append("					,WTPSR.SIIRESAKI_CD ");
		sql.append("					,WTPSR.PLU_SEND_DT ");
		sql.append("					,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("					,WTPSR.BUNRUI5_CD ");
		sql.append("					,WTPSR.REC_HINMEI_KANJI_NA ");
		sql.append("					,WTPSR.REC_HINMEI_KANA_NA ");
		sql.append("					,WTPSR.KIKAKU_KANJI_NA ");
		sql.append("					,WTPSR.MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,WTPSR.ZEI_KB ");
		sql.append("					,WTPSR.ERR_KB ");
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (S)
		/*// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 START
		sql.append("					,WTPSR.NENREI_SEIGEN_KB ");
		sql.append("					,WTPSR.NENREI ");
		sql.append("					,WTPSR.KAN_KB ");
		sql.append("					,WTPSR.HOSHOUKIN ");
		// 2015/05/14 Nhat.nks 年齢制限、瓶追加対応 END*/
		// No.650 MSTB101 Del 2016.04.05 Huy.NT (E)
		// 2015/09/10 THO.VT (S)
		sql.append("					,WTPSR.BUNRUI2_CD ");
		sql.append("					,WTPSR.TEIKAN_FG ");
		sql.append("					,WTPSR.ZEI_RT ");
		sql.append("					,WTPSR.SEASON_ID ");
		sql.append("					,WTPSR.SYOHI_KIGEN_DT ");
		sql.append("					,WTPSR.CARD_FG ");
		// 2015/09/10 THO.VT (E)
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (S)
		sql.append("					,WTPSR.HANBAI_TANI ");
		sql.append("					,WTPSR.KEIRYOKI_NM ");
		// No.650 MSTB101 Add 2016.04.04 Huy.NT (E)
	    // 2016/09/08 VINX t.han #1921対応（S)
		sql.append("					,WTPSR.PLU_HANEI_TIME ");
		sql.append("					,WTPSR.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,WTPSR.LABEL_SEIBUN ");
		sql.append("					,WTPSR.LABEL_HOKAN_HOHO ");
		sql.append("					,WTPSR.LABEL_TUKAIKATA ");
	    // 2016/09/08 VINX t.han #1921対応（E)
		sql.append("					) ");

		return sql.toString();
	}

	/**
	 * WK_PLU例外のデータをWK_PLU店別商品に上書きするSQLを取得する
	 * 
	 * @return 例外商品を上書きSQL
	 */
	private String getWkTecPluUpdateReigaiSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("MERGE INTO ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	USING ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WTPS.BUNRUI1_CD                                     AS BUNRUI1_CD ");
		sql.append("			,WTPS.SYOHIN_CD                                      AS SYOHIN_CD ");
		sql.append("			,WTPS.OLD_SYOHIN_CD                                  AS OLD_SYOHIN_CD ");
		sql.append("			,WTPSR.TENPO_CD                                      AS TENPO_CD ");
		sql.append("			,NVL(WTPSR.GENTANKA_VL		, WTPS.GENTANKA_VL)      AS GENTANKA_VL ");
		sql.append("			,NVL(WTPSR.BAITANKA_VL		, WTPS.BAITANKA_VL)      AS BAITANKA_VL ");
		sql.append("			,NVL(WTPSR.SIIRESAKI_CD		, WTPS.SIIRESAKI_CD)     AS SIIRESAKI_CD ");
		sql.append("			,NVL(WTPSR.PLU_SEND_DT		, WTPS.PLU_SEND_DT)      AS PLU_SEND_DT ");
		sql.append("			,NVL(WTPSR.BAIKA_HAISHIN_FG	, WTPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		sql.append("			,WTPS.BUNRUI5_CD                                     AS BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("			,WTPS.HINMEI_KANJI_NA                            AS HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("			,WTPS.MAX_BUY_QT                                     AS MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("			,WTPS.REC_HINMEI_KANJI_NA                            AS REC_HINMEI_KANJI_NA ");
		sql.append("			,WTPS.REC_HINMEI_KANA_NA                             AS REC_HINMEI_KANA_NA ");
		sql.append("			,WTPS.KIKAKU_KANJI_NA                                AS KIKAKU_KANJI_NA ");
		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL                           AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTPS.ZEI_KB                                         AS ZEI_KB ");
		sql.append("			 ,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB ");
		sql.append("			,WTPS.BUNRUI2_CD                          			 AS BUNRUI2_CD ");
		sql.append("			,WTPS.TEIKAN_FG                               		 AS TEIKAN_FG ");
		sql.append("			,WTPS.ZEI_RT                             			 AS ZEI_RT ");
// #6728 MOD 2023.02.15 DUY.HK (S)
//        // #6367 Mod 2021.11.22 Duy.HK (S)
//		//sql.append("			,    TPS.SEASON_ID                               		 AS SEASON_ID ");
//        sql.append("    ,CASE ");
//        sql.append("        WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//        sql.append("        WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//        // #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("        ELSE WTPS.SEASON_ID ");
//        sql.append("    END AS SEASON_ID ");
//        // #6367 Mod 2021.11.22 Duy.HK (E)
		sql.append("			,WTPS.SEASON_ID                                      AS SEASON_ID ");
// #6728 MOD 2023.02.15 DUY.HK (E)
		sql.append("			,WTPS.SYOHI_KIGEN_DT                               	 AS SYOHI_KIGEN_DT ");
		sql.append("			,WTPS.CARD_FG                            	   		 AS CARD_FG ");
		sql.append("			,WTPS.HANBAI_TANI                                    AS HANBAI_TANI ");
		sql.append("			,WTPS.KEIRYOKI_NM                                    AS KEIRYOKI_NM ");
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.PLU_HANEI_TIME                                AS PLU_HANEI_TIME ");
		sql.append("			,NVL(WTPSR.PLU_HANEI_TIME		, WTPS.PLU_HANEI_TIME)      AS PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER                       AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTPS.LABEL_SEIBUN                                   AS LABEL_SEIBUN ");
		sql.append("			,WTPS.LABEL_HOKAN_HOHO                               AS LABEL_HOKAN_HOHO ");
		sql.append("			,WTPS.LABEL_TUKAIKATA                                AS LABEL_TUKAIKATA ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB                                       AS GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("			,WTPS.LABEL_COUNTRY_NA                               AS LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTPS.HANBAI_TANI_EN                                 AS HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            ,GREATEST（WTPS.DELETE_FG ，WTPSR.DELETE_FG）AS DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E) 
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
// #6728 DEL 2023.02.15 DUY.HK (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//        sql.append("    LEFT JOIN  ");
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
//        sql.append("                                   RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        // #6620 MOD 2022.11.18 VU.TD (E)
//        sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//        sql.append("                              ) ");
//        sql.append("        )  RS  ");
//        sql.append("     ON    WTPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND WTPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
// #6728 DEL 2023.02.15 DUY.HK (E)
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 WTPSR.BUNRUI1_CD ");
		sql.append("					,WTPSR.SYOHIN_CD ");
		sql.append("					,WTPSR.TENPO_CD ");
		sql.append("					,WTPSR.GENTANKA_VL ");
		sql.append("					,WTPSR.BAITANKA_VL ");
		sql.append("					,WTPSR.SIIRESAKI_CD ");
		sql.append("					,WTPSR.PLU_SEND_DT ");
		sql.append("					,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("					,WTPSR.PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima add(S)
		sql.append("					,WTPSR.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima add(E)
		sql.append("				FROM ");
		sql.append("					WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		// #3686対応 2017/02/13 T.Yajima Del(S)
		//sql.append("				WHERE ");
		//sql.append("					WTPSR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #3686対応 2017/02/13 T.Yajima Del(E)
		sql.append("				) WTPSR ");
		sql.append("				ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("					WTPS.BUNRUI1_CD	= WTPSR.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("					WTPS.SYOHIN_CD	= WTPSR.SYOHIN_CD ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("					SELECT ");
		sql.append("						TRT.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("						,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
		sql.append("					FROM ");
		sql.append("						TMP_R_TENPO TRT ");
		sql.append("					WHERE ");
		sql.append("						TRT.ZAIMU_END_DT	> '" + batchDt + "'	AND ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("						TRT.TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") ");
		sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("				) TRT ");
		sql.append("				ON ");
		sql.append("					TRIM(TRT.TENPO_CD)	= TRIM(WTPSR.TENPO_CD) ");
		sql.append("		WHERE ");
		// #3686対応 2017/02/13 T.Yajima Del(S)
		//sql.append("			WTPS.DELETE_FG						 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'				AND ");
		// #3686対応 2017/02/13 T.Yajima Del(E)
		// #6367 MOD 2022.01.27 SIEU.D (S)
		// sql.append("			WTPS.SYOHIN_KB						<> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'				AND ");
		// sql.append("			GET_JAN_SYUBETSU(WTPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'	 ");
		sql.append("			WTPS.SYOHIN_KB						<> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' ");
		// #6367 MOD 2022.01.27 SIEU.D (E)
	    // 2016.11.04 T.han #1750対応（S)
		sql.append("			AND ");
		sql.append("			( ");
		/*// 2017.01.24 T.Yajima #3720対応（S)
		sql.append("				(WTPS.PLU_SEND_DT				= '" + yokuBatchDt + "'		AND ");
		sql.append("				NVL(WTPSR.PLU_SEND_DT,' ')		= '" + SPECIAL_DATE + "')	OR  ");
		// 2017.01.24 T.Yajima #3720対応（E)*/
		// 2017.01.24 T.Yajima #3720対応（S)
		sql.append("               ( WTPS.PLU_SEND_DT               = '" + yokuBatchDt + "')    OR  ");
		// 2017.01.24 T.Yajima #3720対応（E)
		sql.append("				NVL(WTPSR.PLU_SEND_DT,' ')		= '" + yokuBatchDt + "'			");
		sql.append("			) ");
	    // 2016.11.04 T.han #1750対応（E)
		sql.append("		) WTPSR ");
		sql.append("		ON ");
		sql.append("			( ");
		sql.append("			WTP.SYOHIN_CD	= WTPSR.SYOHIN_CD	AND ");
		sql.append("			WTP.TENPO_CD	= WTPSR.TENPO_CD ");
		sql.append("			) ");
		sql.append("	WHEN MATCHED THEN ");
		sql.append("		UPDATE ");
		sql.append("			SET ");
		sql.append("				 WTP.OLD_SYOHIN_CD			= WTPSR.OLD_SYOHIN_CD ");
		sql.append("				,WTP.GENTANKA_VL			= WTPSR.GENTANKA_VL ");
		sql.append("				,WTP.BAITANKA_VL			= WTPSR.BAITANKA_VL ");
		sql.append("				,WTP.SIIRESAKI_CD			= WTPSR.SIIRESAKI_CD ");
		sql.append("				,WTP.PLU_SEND_DT			= WTPSR.PLU_SEND_DT ");
		sql.append("				,WTP.BAIKA_HAISHIN_FG		= WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("				,WTP.BUNRUI5_CD				= WTPSR.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("				,WTP.HINMEI_KANJI_NA	= WTPSR.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("				,WTP.MAX_BUY_QT				= WTPSR.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("				,WTP.REC_HINMEI_KANJI_NA	= WTPSR.REC_HINMEI_KANJI_NA ");
		sql.append("				,WTP.REC_HINMEI_KANA_NA		= WTPSR.REC_HINMEI_KANA_NA ");
		sql.append("				,WTP.KIKAKU_KANJI_NA		= WTPSR.KIKAKU_KANJI_NA ");
		sql.append("				,WTP.MAKER_KIBO_KAKAKU_VL	= WTPSR.MAKER_KIBO_KAKAKU_VL ");
		sql.append("				,WTP.ZEI_KB					= WTPSR.ZEI_KB ");
		sql.append("				,WTP.ERR_KB					= WTPSR.ERR_KB ");
		sql.append("				,WTP.BUNRUI2_CD				= BUNRUI2_CD ");
		sql.append("				,WTP.TEIKAN_FG				= TEIKAN_FG ");
		sql.append("				,WTP.ZEI_RT					= ZEI_RT ");
		sql.append("				,WTP.SEASON_ID				= SEASON_ID ");
		sql.append("				,WTP.SYOHI_KIGEN_DT			= SYOHI_KIGEN_DT ");
		sql.append("				,WTP.CARD_FG				= CARD_FG ");
		sql.append("				,WTP.UPDATE_USER_ID			= '" + userLog.getJobId() + "' ");
		sql.append("				,WTP.UPDATE_TS				= '" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("				,WTP.HANBAI_TANI			= WTPSR.HANBAI_TANI ");
		sql.append("				,WTP.KEIRYOKI_NM			= WTPSR.KEIRYOKI_NM ");
		sql.append("				,WTP.PLU_HANEI_TIME             = WTPSR.PLU_HANEI_TIME ");
		sql.append("				,WTP.SYOHI_KIGEN_HYOJI_PATTER   = WTPSR.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("				,WTP.LABEL_SEIBUN               = WTPSR.LABEL_SEIBUN ");
		sql.append("				,WTP.LABEL_HOKAN_HOHO           = WTPSR.LABEL_HOKAN_HOHO ");
		sql.append("				,WTP.LABEL_TUKAIKATA            = WTPSR.LABEL_TUKAIKATA ");
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("				,WTP.LABEL_COUNTRY_NA           = WTPSR.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("				,WTP.HANBAI_TANI_EN             = WTPSR.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("				,WTP.DELETE_FG             = WTPSR.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E) 
		return sql.toString();
	}

	/**
	 * WK_PLU例外のデータをWK_PLU店別商品に追加するSQLを取得する
	 * 
	 * @return 例外商品を追加するSQL
	 */
	private String getWkTecPluInsertReigaiSql() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("MERGE INTO ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	USING ");
		sql.append("		( ");
		sql.append("		SELECT ");
		sql.append("			 WTPS.BUNRUI1_CD                                     AS BUNRUI1_CD ");
		sql.append("			,WTPS.SYOHIN_CD                                      AS SYOHIN_CD ");
		sql.append("			,WTPS.OLD_SYOHIN_CD                                  AS OLD_SYOHIN_CD ");
		sql.append("			,WTPSR.TENPO_CD                                      AS TENPO_CD ");
		sql.append("			,NVL(WTPSR.GENTANKA_VL		, WTPS.GENTANKA_VL)      AS GENTANKA_VL ");
		sql.append("			,NVL(WTPSR.BAITANKA_VL		, WTPS.BAITANKA_VL)      AS BAITANKA_VL ");
		sql.append("			,NVL(WTPSR.SIIRESAKI_CD		, WTPS.SIIRESAKI_CD)     AS SIIRESAKI_CD ");
		sql.append("			,NVL(WTPSR.PLU_SEND_DT		, WTPS.PLU_SEND_DT)      AS PLU_SEND_DT ");
		sql.append("			,NVL(WTPSR.BAIKA_HAISHIN_FG	, WTPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		sql.append("			,WTPS.BUNRUI5_CD                                     AS BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("			,WTPS.HINMEI_KANJI_NA                            AS HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("			,WTPS.MAX_BUY_QT                                     AS MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("			,WTPS.REC_HINMEI_KANJI_NA                            AS REC_HINMEI_KANJI_NA ");
		sql.append("			,WTPS.REC_HINMEI_KANA_NA                             AS REC_HINMEI_KANA_NA ");
		sql.append("			,WTPS.KIKAKU_KANJI_NA                                AS KIKAKU_KANJI_NA ");
		sql.append("			,WTPS.MAKER_KIBO_KAKAKU_VL                           AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("			,WTPS.ZEI_KB                                         AS ZEI_KB ");
		sql.append("			 ,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB ");
		sql.append("			,WTPS.BUNRUI2_CD                          			 AS BUNRUI2_CD ");
		sql.append("			,WTPS.TEIKAN_FG                               		 AS TEIKAN_FG ");
		sql.append("			,WTPS.ZEI_RT                             			 AS ZEI_RT ");
// #6728 MOD 2023.02.15 DUY.HK (S)
//        // #6367 Mod 2021.10.22 Duy.HK (S)
//		//sql.append("			,WTPS.SEASON_ID                               		 AS SEASON_ID ");
//        sql.append("         ,CASE ");
//        sql.append("        WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//        sql.append("        WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//      	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("              ELSE WTPS.SEASON_ID ");
//        sql.append("         END AS SEASON_ID ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
		sql.append("			,WTPS.SEASON_ID                                      AS SEASON_ID ");
// #6728 MOD 2023.02.15 DUY.HK (E)
		sql.append("			,WTPS.SYOHI_KIGEN_DT                               	 AS SYOHI_KIGEN_DT ");
		sql.append("			,WTPS.CARD_FG                            	   		 AS CARD_FG ");
		sql.append("			,WTPS.HANBAI_TANI                                    AS HANBAI_TANI ");
		sql.append("			,WTPS.KEIRYOKI_NM                                    AS KEIRYOKI_NM ");
		// #3686対応 2017/02/13 T.Yajima Add(S)
		//sql.append("			,WTPSR.PLU_HANEI_TIME                                AS PLU_HANEI_TIME ");
		sql.append("			,NVL(WTPSR.PLU_HANEI_TIME		, WTPS.PLU_HANEI_TIME)      AS PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("			,WTPS.SYOHI_KIGEN_HYOJI_PATTER                       AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("			,WTPS.LABEL_SEIBUN                                   AS LABEL_SEIBUN ");
		sql.append("			,WTPS.LABEL_HOKAN_HOHO                               AS LABEL_HOKAN_HOHO ");
		sql.append("			,WTPS.LABEL_TUKAIKATA                                AS LABEL_TUKAIKATA ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("			,TRT.GYOTAI_KB                                        AS GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("			,WTPS.LABEL_COUNTRY_NA                               AS LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,WTPS.HANBAI_TANI_EN                                 AS HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("            ,GREATEST（WTPS.DELETE_FG ，WTPSR.DELETE_FG）        AS DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E) 
		sql.append("		FROM ");
		sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
// #6728 DEL 2023.02.15 DUY.HK (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//        sql.append("    LEFT JOIN  ");
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
//        sql.append("     ON    WTPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND WTPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
// #6728 DEL 2023.02.15 DUY.HK (E)
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("				SELECT ");
		sql.append("					 WTPSR.BUNRUI1_CD ");
		sql.append("					,WTPSR.SYOHIN_CD ");
		sql.append("					,WTPSR.TENPO_CD ");
		sql.append("					,WTPSR.GENTANKA_VL ");
		sql.append("					,WTPSR.BAITANKA_VL ");
		sql.append("					,WTPSR.SIIRESAKI_CD ");
		sql.append("					,WTPSR.PLU_SEND_DT ");
		sql.append("					,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("					,WTPSR.PLU_HANEI_TIME ");
		// #3686対応 2017/02/13 T.Yajima add(S)
		sql.append("					,WTPSR.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima add(E)
		sql.append("				FROM ");
		sql.append("					WK_TEC_PLU_SYOHIN_REIGAI WTPSR ");
		// #3686対応 2017/02/13 T.Yajima Del(S)
		//sql.append("				WHERE ");
		//sql.append("					WTPSR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #3686対応 2017/02/13 T.Yajima Mod(E)
		sql.append("				) WTPSR ");
		sql.append("				ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("					WTPS.BUNRUI1_CD	= WTPSR.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("					WTPS.SYOHIN_CD	= WTPSR.SYOHIN_CD ");
		sql.append("			INNER JOIN ");
		sql.append("				( ");
		sql.append("					SELECT ");
		sql.append("						TRT.TENPO_CD ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("						,TRT.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
		sql.append("					FROM ");
		sql.append("						TMP_R_TENPO TRT ");
		sql.append("					WHERE ");
		sql.append("						TRT.ZAIMU_END_DT	> '" + batchDt + "'	AND ");
		// #6406 Mod 2021.12.14 KHOI.ND (S)
		// sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
		// sql.append("						TRT.TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") ");
		sql.append("						TRT.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.14 KHOI.ND (E)
		sql.append("				) TRT ");
		sql.append("				ON ");
		sql.append("					TRIM(TRT.TENPO_CD)	= TRIM(WTPSR.TENPO_CD) ");
		sql.append("		WHERE ");
		// #3686対応 2017/02/13 T.Yajima Del(S)
		//sql.append("			WTPS.DELETE_FG						 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'				AND ");
		// #3686対応 2017/02/13 T.Yajima Del(E)
		sql.append("			WTPS.SYOHIN_KB						<> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'				AND ");
		// #6367 DEL 2022.01.27 SIEU.D (S)
		// sql.append("			GET_JAN_SYUBETSU(WTPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'	AND ");
		// #6367 DEL 2022.01.27 SIEU.D (S)
		sql.append("			WTPSR.PLU_SEND_DT = '" + yokuBatchDt + "'							AND ");
/* #1954 Mod 2016.09.29 Li.Sheng (S) */ 
		//sql.append("			WTPSR.PLU_HANEI_TIME IS NULL 										AND ");
		sql.append("			WTPSR.PLU_HANEI_TIME IS NULL 										");
/* #1954 Mod 2016.09.29 Li.Sheng (E) */ 
        /* #1954 Del 2016.09.29 Li.Sheng (S) */     		
//		sql.append("			EXISTS ( ");
//		sql.append("				SELECT");
//		sql.append("					1");
//		sql.append("				FROM");
//		sql.append(" 					WK_TEC_EMG_PLU WTEP1 ");
//		sql.append("					INNER JOIN WK_TEC_EMG_PLU_REIGAI TEPR1 ");
//		sql.append("				ON TEPR1.SYOHIN_CD = WTEP1.SYOHIN_CD ");
//		sql.append("				AND TEPR1.TENPO_CD = WTEP1.TENPO_CD");
//		sql.append("			)");
        /* #1954 Del 2016.09.29 Li.Sheng (E) */     
		sql.append("		) WTPSR ");
		sql.append("		ON ");
		sql.append("			( ");
		sql.append("			WTP.SYOHIN_CD	= WTPSR.SYOHIN_CD	AND ");
		sql.append("			WTP.TENPO_CD	= WTPSR.TENPO_CD ");
		sql.append("			) ");
		sql.append("	WHEN NOT MATCHED THEN ");
		sql.append("				INSERT ");
		sql.append("					( ");
		sql.append("					 BUNRUI1_CD ");
		sql.append("					,SYOHIN_CD ");
		sql.append("					,OLD_SYOHIN_CD ");
		sql.append("					,TENPO_CD ");
		sql.append("					,GENTANKA_VL ");
		sql.append("					,BAITANKA_VL ");
		sql.append("					,SIIRESAKI_CD ");
		sql.append("					,PLU_SEND_DT ");
		sql.append("					,BAIKA_HAISHIN_FG ");
		sql.append("					,BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("					,HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("					,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("					,REC_HINMEI_KANJI_NA ");
		sql.append("					,REC_HINMEI_KANA_NA ");
		sql.append("					,KIKAKU_KANJI_NA ");
		sql.append("					,MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,ZEI_KB ");
		sql.append("					,ERR_KB ");
		sql.append("					,BUNRUI2_CD ");
		sql.append("					,TEIKAN_FG ");
		sql.append("					,ZEI_RT ");
		sql.append("					,SEASON_ID ");
		sql.append("					,SYOHI_KIGEN_DT ");
		sql.append("					,CARD_FG ");
		sql.append("					,INSERT_USER_ID ");
		sql.append("					,INSERT_TS ");
		sql.append("					,UPDATE_USER_ID ");
		sql.append("					,UPDATE_TS ");
		sql.append("					,HANBAI_TANI ");
		sql.append("					,KEIRYOKI_NM ");
		sql.append("					,PLU_HANEI_TIME ");
		sql.append("					,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,LABEL_SEIBUN ");
		sql.append("					,LABEL_HOKAN_HOHO ");
		sql.append("					,LABEL_TUKAIKATA ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("					,GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("					,LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("					,HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("					,DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("					) ");
		sql.append("				VALUES ");
		sql.append("					( ");
		sql.append("					 WTPSR.BUNRUI1_CD ");
		sql.append("					,WTPSR.SYOHIN_CD ");
		sql.append("					,WTPSR.OLD_SYOHIN_CD ");
		sql.append("					,WTPSR.TENPO_CD ");
		sql.append("					,WTPSR.GENTANKA_VL ");
		sql.append("					,WTPSR.BAITANKA_VL ");
		sql.append("					,WTPSR.SIIRESAKI_CD ");
		sql.append("					,WTPSR.PLU_SEND_DT ");
		sql.append("					,WTPSR.BAIKA_HAISHIN_FG ");
		sql.append("					,WTPSR.BUNRUI5_CD ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (S)
		sql.append("					,WTPSR.HINMEI_KANJI_NA ");
		// #6167 MSTB101 Add 2020.07.10 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("					,WTPSR.MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("					,WTPSR.REC_HINMEI_KANJI_NA ");
		sql.append("					,WTPSR.REC_HINMEI_KANA_NA ");
		sql.append("					,WTPSR.KIKAKU_KANJI_NA ");
		sql.append("					,WTPSR.MAKER_KIBO_KAKAKU_VL ");
		sql.append("					,WTPSR.ZEI_KB ");
		sql.append("					,WTPSR.ERR_KB ");
		sql.append("					,WTPSR.BUNRUI2_CD ");
		sql.append("					,WTPSR.TEIKAN_FG ");
		sql.append("					,WTPSR.ZEI_RT ");
		sql.append("					,WTPSR.SEASON_ID ");
		sql.append("					,WTPSR.SYOHI_KIGEN_DT ");
		sql.append("					,WTPSR.CARD_FG ");
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("					,'" + userLog.getJobId() + "' ");
		sql.append("					,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
		sql.append("					,WTPSR.HANBAI_TANI ");
		sql.append("					,WTPSR.KEIRYOKI_NM ");
		sql.append("					,WTPSR.PLU_HANEI_TIME ");
		sql.append("					,WTPSR.SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("					,WTPSR.LABEL_SEIBUN ");
		sql.append("					,WTPSR.LABEL_HOKAN_HOHO ");
		sql.append("					,WTPSR.LABEL_TUKAIKATA ");
		// #2839 MSTB101 2016.11.24 S.Takayama (S)
		sql.append("					,WTPSR.GYOTAI_KB ");
		// #2839 MSTB101 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("					,WTPSR.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("					,WTPSR.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686対応 2017/02/13 T.Yajima Add(S)
		sql.append("					,WTPSR.DELETE_FG ");
		// #3686対応 2017/02/13 T.Yajima Add(E)
		sql.append("					) ");
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
