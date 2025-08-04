package mdware.master.batch.process.MSTB910;

import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.common.dictionary.mst910020_EmgFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * <p>タイトル: MSTB910020_PluTenbetuSyohinCreate.java クラス</p>
 * <p>説明: 緊急PLU店別商品マスタ作成処理</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2015.10.13) NGUYEN.NTM FIVIMART対応
 * @Version 1.01  (2016.04.25) to  FIVIMART対応
 * @Version 1.02  (2016.05.06) M.Kanno #1306対応
 * @Version 1.03  (2016.05.13) M.Kanno #1336対応
 * @Version 1.04  (2016.05.16) M.Kanno #1332対応 金額桁数エラー判定削除
 * @Version 1.05  (2016.05.30) M.Kanno #1334対応
 * @version 1.06  (2016.09.09) H.Sakamoto #1921対応
 * @version 1.07  (2016.09.16) H.Sakamoto #1954対応
 * @version 1.07  (2016.09.26) H.Sakamoto #1954対応
 * @version 1.08  (2016.09.29) Li.Sheng #1954対応
 * @version 1.09  (2016.09.26) nv.cuong #2526対応
 * @version 1.10  (2016.11.07) #1750対応 VINX t.han
 * @version 1.11  (2016.11.28) #2839対応 VINX S.Takayama
 * @version 1.12  (2016.11.29) #2629対応 VINX t.han
 * @version 1.13  (2016.12.05) M.Akagi #3102対応
 * @version 1.14  (2016.12.12) T.Han #3049 FIVImart対応
 * @version 1.15  (2016.12.16) Li.Sheng #3232対応
 * @version 1.16  (2017.01.24) X.Liu #3571対応
 * @version 1.17  (2017.01.25) X.Liu #3720対応
 * @version 1.18  (2017.02.09) #3765対応 T.Han FIVImart対応
 * @version 1.19  (2017.02.14) #3765対応 M.Son FIVImart対応
 * @version 1.20  (2017.03.02) M.Akagi #4239
 * @version 1.21  (2017.02.14) #3686 S.Takayama FIVImart対応
 * @version 1.22  (2017.03.16) Li.Sheng #4377対応
 * @version 1.23  (2017.03.28) Li.Sheng #4418対応
 * @version 1.24  (2017.04.24) S.Takayama #4775対応
 * @version 1.25  (2017.04.28) T.Yajima #4845対応
 * @version 1.26  (2017.05.17) S.Takayama #5033対応
 * @version 1.27  (2017.05.23) T.Han #5100対応 FIVImart対応
 * @version 1.28  (2020.07.13) KHAI.NN #6167 MKV対応
 * @version 1.29  (2021.10.22) Duy.HK #6367
 * @version 1.30  (2022.01.13) SIEU.D #6486
 * @version 1.31  (2022.01.27) SIEU.D #6367
 * @version 1.32  (2022.07.25) SIEU.D #6629
 * @version 1.33  (2022.10.05) VU.TD #6655
 * @version 1.34  (2023.02.16) SIEU.D #6728
 * @Version 1.35  (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB910020_PluTenbetuSyohinCreate {
    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK_EMG_PLU_SYOHIN = "WK_TEC_EMG_PLU_SYOHIN"; // WK_緊急PLU商品
    private static final String TABLE_WK_EMG_PLU_REIGAI = "WK_TEC_EMG_PLU_REIGAI"; // WK_緊急PLU例外
    private static final String WK_EMG_PLU = "WK_TEC_EMG_PLU"; // WK_緊急PLU店別商品

    // 2016.11.07 T.han #1750対応（S)
	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";
    // 2016.11.07 T.han #1750対応（E)

    // #1306対応 2016.05.06 M.Kanno del (S)
    /** 金額項目桁数エラー判定値 */
    //private static final String KINGAKU_ERROR_CHECK_VL = "9999999";
    // #1306対応 2016.05.06 M.Kanno del (E)

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    // バッチ日付
    private String batchDt = null;
    // 翌日バッチ日付
    private String yokuBatchDt = null;
/* #3232 Add 2016.12.16 Li.Sheng (S) */
    // バッチ日付の前日
    private String zenjituBatchDt = null;
/* #3232 Add 2016.12.16 Li.Sheng (E) */

    /** ゼロ (定数) */
    private static final String CONST_ZERO = "0";

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB910020_PluTenbetuSyohinCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB910020_PluTenbetuSyohinCreate() {
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
/* #3232 Add 2016.12.16 Li.Sheng (S) */
            zenjituBatchDt = DateChanger.addDate(batchDt, -1);
/* #3232 Add 2016.12.16 Li.Sheng (E) */
            // WK_緊急PLU商品のTRUNCATE
            writeLog(Level.INFO_INT, "WK_緊急PLU商品削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK_EMG_PLU_SYOHIN);
            writeLog(Level.INFO_INT, "WK_緊急PLU商品を削除処理を終了します。");

            // WK_緊急PLU例外のTRUNCATE
            writeLog(Level.INFO_INT, "WK_緊急PLU例外削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK_EMG_PLU_REIGAI);
            writeLog(Level.INFO_INT, "WK_緊急PLU例外を削除処理を終了します。");

            // WK_緊急PLU店別商品のTRUNCATE
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品削除処理を開始します。");
            DBUtil.truncateTable(db, WK_EMG_PLU);
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を削除処理を終了します。");

            db.commit();

            // WK_緊急PLU商品の登録
            writeLog(Level.INFO_INT, "WK_緊急PLU商品登録処理を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluSyohinInsertSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU商品を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU商品登録処理を終了します。");

            db.commit();

            // WK_緊急PLU例外の登録
            writeLog(Level.INFO_INT, "WK_緊急PLU例外登録処理を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluReigaiInsertSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU例外を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU例外登録処理を終了します。");

            db.commit();

            // 2017.03.02 M.Akagi #4239 (S)
            String ownName = CollectConnections.getInstance().getDBSetting("rbsite_ora").getUser().toUpperCase();
            // WK_緊急PLU商品Analize
            db.execute(getAnalizeSql(ownName, "WK_TEC_EMG_PLU_SYOHIN"));
            // WK_緊急PLU例外Analize
            db.execute(getAnalizeSql(ownName, "WK_TEC_EMG_PLU_REIGAI"));
            // 2017.03.02 M.Akagi #4239 (E)

            // WK_緊急PLU店別商品の登録
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品登録処理を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluInsertSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品登録処理を終了します。");

            db.commit();

            //2016/9/16 VINX h.sakamoto #1954対応 (S)
            // WK_緊急PLU店別商品例外反映
//            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外反映処理を開始します。");
//            iRec = db.executeUpdate(getWkTecEmgPluMergeSql());
//            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を" + iRec + "件マージしました。");
//            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外反映処理を終了します。");
            //2016/9/16 VINX h.sakamoto #1954対応 (E)

            //2016/9/16 VINX h.sakamoto #1954対応 (S)
            // WK_緊急PLU店別商品例外更新
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外更新処理を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluUpdateReigaiSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を" + iRec + "件更新しました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外更新処理を終了します。");
            //2016/9/16 VINX h.sakamoto #1954対応 (E)

            db.commit();

            //2016/9/16 VINX h.sakamoto #1954対応 (S)
            // WK_緊急PLU店別商品例外登録
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外登録を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluInsertReigaiSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を" + iRec + "件登録しました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品例外登録を終了します。");
            //2016/9/16 VINX h.sakamoto #1954対応 (E)

            db.commit();

            // 配信時間の更新
            writeLog(Level.INFO_INT, "配信時間の更新を開始します。");
            iRec = db.executeUpdate(getPosFileSeqUpdSql());
            writeLog(Level.INFO_INT, "配信時間の更新を更新しました。");
            writeLog(Level.INFO_INT, "配信時間の更新を" + iRec + "件更新しました。");
            writeLog(Level.INFO_INT, "配信時間の更新を終了します。");

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


    /**
     * WK_TEC_EMG_PLU_SYOHINを登録するSQLを取得する
     *
     * @return WK_TEC_EMG_PLU_SYOHIN登録SQL
     */
    private String getWkTecEmgPluSyohinInsertSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        //#3765 MSTB910 2017.02.14 M.Son (S)
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
        //#3765 MSTB910 2017.02.14 M.Son (E)
        
        sql.append(" INSERT /*+ APPEND */ INTO WK_TEC_EMG_PLU_SYOHIN");
        sql.append(" 		(BUNRUI1_CD,");
        sql.append(" 		SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (S)
        sql.append(" 		OLD_SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (E)
        sql.append(" 		YUKO_DT,");
        sql.append(" 		GENTANKA_VL,");
        sql.append(" 		BAITANKA_VL,");
        sql.append(" 		SIIRESAKI_CD,");
        sql.append(" 		PLU_SEND_DT,");
        sql.append(" 		BAIKA_HAISHIN_FG,");
        sql.append(" 		BUNRUI5_CD,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append(" 		HINMEI_KANJI_NA,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append(" 		MAX_BUY_QT,");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append(" 		REC_HINMEI_KANJI_NA,");
        sql.append(" 		REC_HINMEI_KANA_NA,");
        sql.append(" 		KIKAKU_KANJI_NA,");
        sql.append(" 		MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 		ZEI_KB,");
        sql.append(" 		SYOHIN_KB,");

        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        sql.append(" 		DELETE_FG,");
        sql.append(" 		HANBAI_TANI,");
        // to 2016.04.28 v0r6　S62対応　桁数変更 (S)
        // sql.append(" 		KANJI_NA)");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append(" 		KEIRYOKI_NM)");
        // to 2016.04.28 v0r6　S62対応　桁数変更 (S)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        sql.append(" 		KEIRYOKI_NM,");
        sql.append("		BUNRUI2_CD, ");
        sql.append("		TEIKAN_FG, ");
        sql.append("		ZEI_RT, ");
        sql.append("		SEASON_ID, ");
        sql.append("		SYOHI_KIGEN_DT, ");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append("		CARD_FG) ");
        sql.append("		CARD_FG, ");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        // #1306対応 2016.05.06 M.Kanno (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
        sql.append("		PLU_HANEI_TIME ,");
        sql.append("		SYOHI_KIGEN_HYOJI_PATTER ,");
        sql.append("		LABEL_SEIBUN ,");
        sql.append("		LABEL_HOKAN_HOHO ,");
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append("		LABEL_TUKAIKATA )");
        sql.append("		LABEL_TUKAIKATA ,");
	    // 2016/12/12 VINX t.han #3049対応（S)
        //sql.append(" 		EMG_FLAG ) ");
        sql.append(" 		EMG_FLAG , ");
	    // 2016/12/12 VINX t.han #3049対応（S)
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
	    // 2017/02/09 T.Han #3765対応（S)
		//sql.append("		LABEL_COUNTRY_NA ) ");
		sql.append("		LABEL_COUNTRY_NA ");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		,HANBAI_TANI_EN ) ");
        sql.append("        ,HANBAI_TANI_EN  ");
        sql.append("        ,HAMPER_SYOHIN_FG ) ");
        // 2017/04/28 T.Yajima #4845対応（E)
	    // 2017/02/09 T.Han #3765対応（E)
	    // 2016/12/12 VINX t.han #3049対応（E)

        sql.append(" SELECT TRES.BUNRUI1_CD,");
        sql.append(" 		TRES.SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (S)
        sql.append(" 		TRES.OLD_SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (E)
        sql.append(" 		TRES.YUKO_DT,");
        sql.append(" 		TRES.GENTANKA_VL,");
        sql.append(" 		TRES.BAITANKA_VL,");
        sql.append(" 		TRES.SIIRESAKI_CD,");
        sql.append(" 		TRES.PLU_SEND_DT,");
        sql.append(" 		NVL(TRES.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") BAIKA_HAISHIN_FG,");
        sql.append(" 		TRES.BUNRUI5_CD,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append(" 		TRES.REC_HINMEI_KANJI_NA,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append(" 		TRES.FREE_4_KB AS MAX_BUY_QT, ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        //2016/9/16 VINX h.sakamoto #2106対応 (S)
//      sql.append(" 		TRES.REC_HINMEI_KANJI_NA,");
         // #6486 MOD 2022.01.13 SIEU.D (S)
        // sql.append(" 		SUBSTR(TRES.HINMEI_KANJI_NA,1,40),");
        sql.append(" 		SUBSTRB(TRES.HINMEI_KANJI_NA || ' ' || TRES.KIKAKU_KANJI_NA , 0, 85 ), ");
        // #6486 MOD 2022.01.13 SIEU.D (E)
        //2016/9/16 VINX h.sakamoto #2106対応 (E)
        sql.append(" 		TRES.REC_HINMEI_KANA_NA,");
        sql.append(" 		TRES.KIKAKU_KANJI_NA,");
        sql.append(" 		TRES.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 		TRES.ZEI_KB,");
        sql.append(" 		TRES.SYOHIN_KB,");
        sql.append(" 		TRES.DELETE_FG,");

        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        // #1307対応 2016.05.06 M.Kanno (S)
        //sql.append(" 		HANBAI_TANI,");
        //sql.append(" 		NMST.KANJI_NA");
        sql.append(" 		NMST.KANJI_NA,");
        sql.append(" 		TRESA.SYOHIN_EIJI_NA,");
        // #1307対応 2016.05.06 M.Kanno (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        // #1306対応 2016.05.06 M.Kanno (S)
        sql.append("		TRES.BUNRUI2_CD, ");
        // #1336対応 2016.05.13 M.Kanno (S)
        //sql.append("		CASE ");
        //sql.append("			WHEN TRES.TEIKAN_KB = 1 THEN 0 ");
        //sql.append("			WHEN TRES.TEIKAN_KB = 2 THEN 1 ");
        //sql.append("		 END AS TEIKAN_KB, ");
        sql.append("		NVL(TRESA.HANBAI_HOHO_KB, 0) AS TEIKAN_KB, ");
        // #1336対応 2016.05.13 M.Kanno (E)
        sql.append("		TRES4.TAX_RT, ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
//     // #6367 Mod 2021.10.22 Duy.HK (S)
////    // add 2016.09.02 nv.cuong(S)
//////      sql.append("		CASE ");
//////      sql.append("			WHEN DGEA.SYOHIN_CD = TRES.SYOHIN_CD THEN TRES.BUNRUI5_CD ");
//////      sql.append("			ELSE '' ");
//////      sql.append("		 END AS SEASON_ID, ");
////        sql.append("'' AS SEASON_ID, ");
////   //  add 2016.09.02 nv.cuong(E)
//        sql.append("         CASE ");
//        sql.append("            WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//    	sql.append("        	WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//    	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("            ELSE '' ");
//        sql.append("         END AS SEASON_ID, ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        // #6728 ADD 2023.02.16 SIEU.D (E)
        sql.append("         CASE ");
        sql.append("            WHEN TRES.WARIBIKI_KB = '0' THEN '999999' ");
        sql.append("            WHEN TRES.WARIBIKI_KB = '2' THEN '888888' ");
        sql.append("            ELSE '' ");
        sql.append("         END AS SEASON_ID, ");
        // #6728 ADD 2023.02.16 SIEU.D (E)
        sql.append("		TRES.SYOHI_KIGEN_DT, ");
        sql.append("		CASE ");
        sql.append("			WHEN TRESA.MEMBER_DISCOUNT_FG = 0 THEN 2 ");
        sql.append("			WHEN TRESA.MEMBER_DISCOUNT_FG = 1 THEN 0 ");
        sql.append("		 END AS MEMBER_DISCOUNT_FG ");
        // #1306対応 2016.05.06 M.Kanno (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
        sql.append("		, TRES.PLU_HANEI_TIME , ");
        sql.append("		TRES.SYOHI_KIGEN_HYOJI_PATTER , ");
        sql.append("		TRESA.LABEL_SEIBUN , ");
        sql.append("		TRESA.LABEL_HOKAN_HOHO , ");
        sql.append("		TRESA.LABEL_TUKAIKATA ");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
        sql.append("		, TRES.EMG_FLAG ");
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
	    // 2017/02/09 T.Han #3765対応（S)
		//sql.append("		, RN1.KANJI_NA ");
		sql.append("		, RN1.KANJI_RN ");
		sql.append("		, RN2.KANJI_RN ");
        // 2017/04/28 T.Yajima #4845対応（S)
        sql.append("        , TRESA.HAMPER_SYOHIN_FG  ");
        // 2017/04/28 T.Yajima #4845対応（E)
		// 2017/02/09 T.Han #3765対応（E)
	    // 2016/12/12 VINX t.han #3049対応（E)

        // #6367 Mod 2021.10.22 Duy.HK (S)
        //sql.append(" FROM 	TMP_R_EMG_SYOHIN TRES INNER JOIN ");
        sql.append("    FROM    TMP_R_EMG_SYOHIN TRES  ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
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
//        sql.append("     ON    TRES.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND TRES.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        sql.append("    INNER JOIN  ");
        // #6367 Mod 2021.10.22 Duy.HK (E)
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 		(SELECT MAX(YUKO_DT) YUKO_DT");

        // 2016.10.16 #2526 nv.cuong(S)
        //sql.append(" 		(SELECT MAX(PLU_SEND_DT) PLU_SEND_DT");
        sql.append(" 		(SELECT MAX(YUKO_DT) YUKO_DT");
        // 2016.10.16 #2526 nv.cuong(E)

        // add 2016.09.02 nv.cuong(S)
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
        // #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append(" 					,BUNRUI1_CD");
        // #5033 Del 2017.05.17 S.Takayama (E)
        sql.append(" 					,SYOHIN_CD");
        sql.append(" 			FROM TMP_R_EMG_SYOHIN");
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 			WHERE YUKO_DT <= '" + yokuBatchDt + "' AND EMG_FLAG = '" + mst910020_EmgFlagDictionary.ON.getCode() + "'");
        // 2016.10.16 #2526 nv.cuong(S)
        //sql.append(" 			WHERE PLU_SEND_DT <= '" + batchDt + "'");
        sql.append(" 			WHERE YUKO_DT <= '" + batchDt + "'");
        // 2016.10.16 #2526 nv.cuong(E)
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
	    // 2016.11.29 T.han #2629対応（S)
		sql.append("				  AND PLU_SEND_DT	<= '" + batchDt + "'  ");
	    // 2016.11.29 T.han #2629対応（E)
		// #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append(" 			GROUP BY BUNRUI1_CD, SYOHIN_CD");
		sql.append(" 			GROUP BY SYOHIN_CD");
        // #5033 Del 2017.05.17 S.Takayama (E)
        sql.append(" 		) RES");
        // #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append("  		ON TRES.BUNRUI1_CD = RES.BUNRUI1_CD ");
        //sql.append(" 		AND TRES.SYOHIN_CD = RES.SYOHIN_CD ");
        sql.append(" 		ON TRES.SYOHIN_CD = RES.SYOHIN_CD ");
        // #5033 Del 2017.05.17 S.Takayama (E)
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 		AND TRES.YUKO_DT = RES.YUKO_DT");
        // 2016.10.16 #2526 nv.cuong(S)
        //sql.append(" 		AND TRES.PLU_SEND_DT = RES.PLU_SEND_DT");
        sql.append(" 		AND TRES.YUKO_DT = RES.YUKO_DT");
        // 2016.10.16 #2526 nv.cuong(E)
        //2016/9/16 VINX h.sakamoto #1954対応 (E)

        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        sql.append(" 		INNER JOIN R_NAMECTF NMST");
        //#3765 MSTB910 2017.02.14 M.Son (S)
        //sql.append(" 		ON trim(NMST.SYUBETU_NO_CD) = '3040'");
        sql.append("		ON trim(NMST.SYUBETU_NO_CD) = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' ");
        //#3765 MSTB910 2017.02.14 M.Son (E)
        sql.append(" 		AND trim(NMST.CODE_CD) = trim(TRES.HANBAI_TANI) ");
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        // #1306対応 2016.05.06 M.Kanno (S)
        //add 2016.09.02 nv.cuong(S)
//        sql.append("		LEFT JOIN ");
//        sql.append("			DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
//        sql.append("		ON ");
//        sql.append("			DGEA.SYOHIN_CD = TRES.SYOHIN_CD ");
      //add 2016.09.02 nv.cuong(E)
        sql.append("		INNER JOIN ");
        sql.append("			TMP_R_EMG_SYOHIN_ASN TRESA ");
        sql.append("		ON ");
        sql.append("			TRESA.SYOHIN_CD = TRES.SYOHIN_CD ");
        sql.append("			AND TRES.YUKO_DT = TRESA.YUKO_DT ");
        sql.append("	INNER JOIN  ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				 TRES.SYOHIN_CD  ");
        sql.append("				,TRES.YUKO_DT ");
        sql.append("				,MIN(TRES.DELETE_FG) AS DELETE_FG ");
        sql.append("			FROM ");
        sql.append("				TMP_R_EMG_SYOHIN TRES  ");
/* #4377 Del 2017.03.16 Li.Sheng (S) */
//        sql.append("				INNER JOIN ");
//        sql.append("					(  ");
//        sql.append("						SELECT  ");
//        sql.append("							 TRES.SYOHIN_CD  ");
//        sql.append("							,MAX(TRES.YUKO_DT) AS YUKO_DT  ");
//        sql.append("						FROM  ");
//        sql.append("							TMP_R_EMG_SYOHIN TRES  ");
//        sql.append("						WHERE  ");
//        //2016/9/26 VINX h.sakamoto #1954対応 (S)
////      sql.append("							TRES.YUKO_DT	<= '" + yokuBatchDt + "'  ");
//        sql.append("							TRES.YUKO_DT	<= '" + batchDt + "'  ");
//        //2016/9/26 VINX h.sakamoto #1954対応 (E)
//        sql.append("						GROUP BY  ");
//        sql.append("							 TRES.SYOHIN_CD  ");
//        sql.append("					) TRES1  ");
//        sql.append("					ON  ");
//        sql.append("						TRES.SYOHIN_CD	= TRES1.SYOHIN_CD	AND  ");
//        sql.append("						TRES.YUKO_DT		= TRES1.YUKO_DT ");
/* #4377 Del 2017.03.16 Li.Sheng (E) */
        sql.append("			GROUP BY ");
        sql.append("				 TRES.SYOHIN_CD  ");
        sql.append("				,TRES.YUKO_DT ");
        sql.append("		) TRES2 ");
        sql.append("		ON ");
        sql.append("			TRES.SYOHIN_CD	= TRES2.SYOHIN_CD	AND  ");
        sql.append("			TRES.YUKO_DT		= TRES2.YUKO_DT		AND ");
        sql.append("			TRES.DELETE_FG	= TRES2.DELETE_FG ");
        sql.append("	INNER JOIN  ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				 TRETR.TAX_RATE_KB ");
        sql.append("				 , TRETR.TAX_RT  ");
        sql.append("			FROM ");
        sql.append("				TMP_R_EMG_TAX_RATE TRETR  ");
        sql.append("				INNER JOIN ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 TRETR.TAX_RATE_KB  ");
        sql.append("							, MAX(TRETR.YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_EMG_TAX_RATE TRETR  ");
        sql.append("						WHERE  ");
        //2016/9/26 VINX h.sakamoto #1954対応 (S)
//      sql.append("							TRETR.YUKO_DT	<= '" + yokuBatchDt + "'  ");
        sql.append("							TRETR.YUKO_DT	<= '" + batchDt + "'  ");
        //2016/9/26 VINX h.sakamoto #1954対応 (E)
        sql.append("							AND TRETR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("						GROUP BY  ");
        sql.append("							 TRETR.TAX_RATE_KB  ");
        sql.append("					) TRES3  ");
        sql.append("				ON ");
        sql.append("					TRETR.TAX_RATE_KB	= TRES3.TAX_RATE_KB ");
        sql.append("					AND TRETR.YUKO_DT	= TRES3.YUKO_DT ");
        sql.append("		) TRES4 ");
        sql.append("		ON ");
        sql.append("			TRES.TAX_RATE_KB	= TRES4.TAX_RATE_KB ");
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN1 ");
		sql.append("	ON ");
		//#3765 MSTB910 2017.02.14 M.Son (S)
		//sql.append("		RN1.SYUBETU_NO_CD = '4070' AND ");
		sql.append("		RN1.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COUNTRY_DIVISION, userLocal) + "' AND ");
		//#3765 MSTB910 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRESA.COUNTRY_CD) = TRIM(RN1.CODE_CD) ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN2 ");
		sql.append("	ON ");
		//#3765 MSTB102 2017.02.14 M.Son (S)
		//sql.append("		RN2.SYUBETU_NO_CD = '3040' AND ");
		sql.append("		RN2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
		//#3765 MSTB102 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRES.HANBAI_TANI) = TRIM(RN2.CODE_CD) ");
	    // 2017/02/09 T.Han #3765対応（E)

        // #1306対応 2016.05.06 M.Kanno (E)
        return sql.toString();
    }

    /**
     * WK_TEC_EMG_PLU_REIGAIを登録するSQLを取得する
     *
     * @return WK_TEC_EMG_PLU_REIGAI登録SQL
     */
    private String getWkTecEmgPluReigaiInsertSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT /*+ APPEND */ INTO WK_TEC_EMG_PLU_REIGAI");
        sql.append(" 		(BUNRUI1_CD,");
        sql.append(" 		SYOHIN_CD,");
        sql.append(" 		TENPO_CD,");
        sql.append(" 		YUKO_DT,");
        sql.append(" 		GENTANKA_VL,");
        sql.append(" 		BAITANKA_VL,");
        sql.append(" 		SIIRESAKI_CD,");
        sql.append(" 		PLU_SEND_DT,");
        sql.append(" 		BAIKA_HAISHIN_FG,");
      //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 		DELETE_FG)");
        sql.append(" 		DELETE_FG,");
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 		PLU_HANEI_TIME)");
        sql.append(" 		PLU_HANEI_TIME,");
        sql.append(" 		EMG_FLG)");
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        sql.append(" SELECT TESR.BUNRUI1_CD,");
        sql.append(" 		TESR.SYOHIN_CD,");
        sql.append(" 		TESR.TENPO_CD,");
        sql.append(" 		TESR.YUKO_DT,");
        sql.append(" 		TESR.GENTANKA_VL,");
        sql.append(" 		TESR.BAITANKA_VL,");
        sql.append(" 		TESR.SIIRESAKI_CD,");
        sql.append(" 		TESR.PLU_SEND_DT,");
        sql.append(" 		NVL(TESR.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") BAIKA_HAISHIN_FG,");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 		TESR.DELETE_FG");
        sql.append(" 		TESR.DELETE_FG,");
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 		TESR.PLU_HANEI_TIME");
        sql.append(" 		TESR.PLU_HANEI_TIME,");
        sql.append(" 		TESR.EMG_FLAG");
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        //2016/9/15 VINX h.sakamoto #1954対応 (S)
//        sql.append(" FROM 	TMP_R_EMG_SYOHIN_REIGAI TESR INNER JOIN ");
        sql.append(" FROM 	TMP_R_EMG_TENSYOHIN_REIGAI TESR INNER JOIN ");
        //2016/9/15 VINX h.sakamoto #1954対応 (E)
        sql.append(" 		(SELECT MAX(YUKO_DT) YUKO_DT");
        // #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append(" 				,BUNRUI1_CD");
        // #5033 Del 2017.05.17 S.Takayama (E)
        sql.append(" 				,SYOHIN_CD");
        sql.append(" 				,TENPO_CD");
        //2016/9/15 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 			FROM TMP_R_EMG_SYOHIN_REIGAI");
        sql.append(" 			FROM TMP_R_EMG_TENSYOHIN_REIGAI TRETR");
        //2016/9/15 VINX h.sakamoto #1954対応 (E)
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
//        sql.append(" 			WHERE YUKO_DT <= '" + yokuBatchDt + "'");

        // 2016.10.16 #2526 nv.cuong(S)
        /*sql.append(" 			WHERE TRETR.PLU_SEND_DT < '" + batchDt + "' OR ");
        sql.append(" 			(");
        sql.append(" 				TRETR.PLU_SEND_DT = '" + batchDt + "' AND");
        sql.append(" 				(");
        sql.append(" 						TRETR.PLU_HANEI_TIME <= (SELECT SEND_TIME FROM POS_FILE_SEQ) ");
        sql.append(" 						OR TRETR.PLU_HANEI_TIME IS NULL");
        sql.append(" 					)");
        sql.append(" 			)");*/
        sql.append(" 			WHERE YUKO_DT <= '" + batchDt + "'");
        // 2016.10.16 #2526 nv.cuong(E)

        //2016/9/16 VINX h.sakamoto #1954対応 (E)
	    // 2016.11.29 T.han #2629対応（S)
		sql.append("				  AND ");
		sql.append("				  ( ");
		sql.append("					PLU_SEND_DT	= '" + SPECIAL_DATE + "'	OR ");
		sql.append("					PLU_SEND_DT	<= '" + batchDt + "'	   ");
		sql.append("				  ) ");
		// 2016.11.29 T.han #2629対応（E)
		// #5033 Del 2017.05.17 S.Takayama (S)
		//sql.append(" 			GROUP BY BUNRUI1_CD, SYOHIN_CD, TENPO_CD");
        sql.append(" 			GROUP BY SYOHIN_CD, TENPO_CD");
        // #5033 Del 2017.05.17 S.Takayama (E)
        sql.append(" 		) ESR");
        // #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append("  		ON TESR.BUNRUI1_CD = ESR.BUNRUI1_CD ");
        //sql.append(" 		AND TESR.SYOHIN_CD = ESR.SYOHIN_CD ");
        sql.append(" 		ON TESR.SYOHIN_CD = ESR.SYOHIN_CD ");
        // #5033 Del 2017.05.17 S.Takayama (S)
        sql.append(" 		AND TESR.YUKO_DT = ESR.YUKO_DT");
        sql.append(" 		AND TESR.TENPO_CD = ESR.TENPO_CD");
        return sql.toString();
    }

    /**
     * WK_TEC_EMG_PLUを登録するSQLを取得する
     *
     * @return WK_TEC_EMG_PLU登録SQL
     */
    private String getWkTecEmgPluInsertSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("	INSERT INTO WK_TEC_EMG_PLU");
        sql.append("			(BUNRUI1_CD,");
        sql.append("			SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (S)
        sql.append(" 			OLD_SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (E)
        sql.append("			TENPO_CD,");
        sql.append("			GENTANKA_VL,");
        sql.append("			BAITANKA_VL,");
        sql.append("			SIIRESAKI_CD,");
        sql.append("			PLU_SEND_DT,");
        sql.append("			BAIKA_HAISHIN_FG,");
        sql.append("			BUNRUI5_CD,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("			HINMEI_KANJI_NA,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("			MAX_BUY_QT,");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("			REC_HINMEI_KANJI_NA,");
        sql.append("			REC_HINMEI_KANA_NA,");
        sql.append("			KIKAKU_KANJI_NA,");
        sql.append("			MAKER_KIBO_KAKAKU_VL,");
        sql.append("			ZEI_KB,");
        sql.append("			ERR_KB,");

        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        sql.append(" 			HANBAI_TANI,");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append(" 			KEIRYOKI_NM)");
        sql.append(" 			KEIRYOKI_NM,");
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        sql.append("			BUNRUI2_CD, ");
        sql.append("			TEIKAN_FG, ");
        sql.append("			ZEI_RT, ");
        sql.append("			SEASON_ID, ");
        sql.append("			SYOHI_KIGEN_DT, ");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append("			CARD_FG) ");
        sql.append("			CARD_FG, ");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        // #1306対応 2016.05.06 M.Kanno (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
        sql.append("			PLU_HANEI_TIME, ");
        sql.append("			SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			LABEL_SEIBUN, ");
        sql.append("			LABEL_HOKAN_HOHO, ");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        //sql.append("			LABEL_TUKAIKATA )");
        sql.append("			LABEL_TUKAIKATA ,");
	    // 2016/12/12 VINX t.han #3049対応（S)
        //sql.append("			GYOTAI_KB )");
        sql.append("			GYOTAI_KB ,");
	    // 2016/12/12 VINX t.han #3049対応（S)
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			HANBAI_TANI_EN, ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB910 2017.02.14 S.Takayama (S)
		//sql.append("			LABEL_COUNTRY_NA ) ");
		sql.append("			LABEL_COUNTRY_NA ");
		sql.append("			, DELETE_FG ");
		sql.append("			) ");
		// #3686 MSTB910 2017.02.14 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（E)

        sql.append("	SELECT	EPS.BUNRUI1_CD,");
        sql.append("			EPS.SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (S)
        sql.append(" 			EPS.OLD_SYOHIN_CD,");
        // #1334対応 2016.05.30 M.Kanno (E)
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        //sql.append("			RET.TENPO_CD,");
        sql.append("			TRET.TENPO_CD,");
        // #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("			EPS.GENTANKA_VL,");
        sql.append("			EPS.BAITANKA_VL,");
        sql.append("			EPS.SIIRESAKI_CD,");
        sql.append("			EPS.PLU_SEND_DT,");
/* #3232 Mod 2016.12.16 Li.Sheng (S) */
//        sql.append("			EPS.BAIKA_HAISHIN_FG,");
        // #4775 Add 2017.04.27 S.Takayama (S)
        //sql.append("			DECODE(SUB1.BAIKA_HAISHIN_FG,'0',SUB1.BAIKA_HAISHIN_FG,EPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG,");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("			CASE ");
        //sql.append("			WHEN SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
        //sql.append("			ELSE EPS.BAIKA_HAISHIN_FG ");
        //sql.append("			END BAIKA_HAISHIN_FG,");
        sql.append("            CASE ");
        sql.append("            WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.HAMPER_SYOHIN_CD IS NULL THEN '1' ");
        sql.append("            WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
        sql.append("            ELSE EPS.BAIKA_HAISHIN_FG ");
        sql.append("            END BAIKA_HAISHIN_FG,");
        // 2017/04/28 T.Yajima #4845対応（E)
        // #4775 Add 2017.04.27 S.Takayama (E)
/* #3232 Mod 2016.12.16 Li.Sheng (E) */  
        sql.append("			EPS.BUNRUI5_CD,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("			EPS.HINMEI_KANJI_NA,");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("			EPS.MAX_BUY_QT,");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("			EPS.REC_HINMEI_KANJI_NA,");
        sql.append("			EPS.REC_HINMEI_KANA_NA,");
        sql.append("			EPS.KIKAKU_KANJI_NA,");
        sql.append("			EPS.MAKER_KIBO_KAKAKU_VL,");
        sql.append("			EPS.ZEI_KB,");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append("			CASE ");
        //sql.append("				WHEN EPS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "'");
        //sql.append("				WHEN EPS.GENTANKA_VL > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "'");
        //sql.append("				ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'");
        //sql.append("			END AS ERR_KB,");
        sql.append("			'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        // #1306対応 2016.05.06 M.Kanno (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        sql.append(" 			EPS.HANBAI_TANI,");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append(" 			EPS.KEIRYOKI_NM");
        sql.append(" 			EPS.KEIRYOKI_NM,");
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        sql.append("			EPS.BUNRUI2_CD, ");
        sql.append("			EPS.TEIKAN_FG, ");
        sql.append("			EPS.ZEI_RT, ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
//        // #6367 Mod 2021.10.22 Duy.HK (S)
////        //add 2016.09.02 nv.cuong(S)
////        //sql.append("			EPS.SEASON_ID, ");
////        sql.append("'' AS SEASON_ID, ");
////        //add 2016.09.02 nv.cuong(E)
//        sql.append("        CASE ");
//        sql.append("            WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//    	sql.append("        	WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//    	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("            ELSE '' ");
//        sql.append("        END AS SEASON_ID, ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        // #6728 ADD 2023.02.16 SIEU.D (S)
        sql.append("			EPS.SEASON_ID ,");
        // #6728 ADD 2023.02.16 SIEU.D (E)
        sql.append("			EPS.SYOHI_KIGEN_DT, ");
        sql.append("			EPS.CARD_FG ");
        // #1306対応 2016.05.06 M.Kanno (E)
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
        sql.append("			,EPS.PLU_HANEI_TIME, ");
        sql.append("			EPS.SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			EPS.LABEL_SEIBUN, ");
        sql.append("			EPS.LABEL_HOKAN_HOHO, ");
        sql.append("			EPS.LABEL_TUKAIKATA");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        // #2839 2016.11.24 S.Takayama (S)
        sql.append("			,TRET.GYOTAI_KB");
        // #2839 2016.11.24 S.Takayama (S)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("			,EPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("			,EPS.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB910 2017.02.14 S.Takayama (S)
		//sql.append("			,EPS.DELETE_FG ");
		// #3686 MSTB910 2017.02.14 S.Takayama (E)
		// #4775 Add 2017.04.27 S.Takayama (S)
        //sql.append("			,DECODE(SUB1.BAIKA_HAISHIN_FG,'0','0',EPS.DELETE_FG) AS DELETE_FG ");
		sql.append("			,EPS.DELETE_FG ");
        // #4775 Add 2017.04.27 S.Takayama (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
        // #6367 Mod 2021.10.22 Duy.HK (S)
        //sql.append("	FROM 	WK_TEC_EMG_PLU_SYOHIN EPS INNER JOIN ");
        sql.append("    FROM     WK_TEC_EMG_PLU_SYOHIN EPS  ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
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
//        sql.append("     ON    EPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND EPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        sql.append("    INNER JOIN  ");
        //#6367 Mod 2021.10.22 Duy.HK (E)
		// #3686 MSTB910 2017.02.14 S.Takayama (S)
        //sql.append("			(SELECT TENPO_CD, ");
        //sql.append("					BUNRUI1_CD, ");
        //sql.append("					SYOHIN_CD ");
        //sql.append("			FROM TMP_R_EMG_TORIATUKAI) RET");
        //sql.append("			ON 	(RET.BUNRUI1_CD = EPS.BUNRUI1_CD AND");
        //sql.append("				RET.SYOHIN_CD = EPS.SYOHIN_CD)");
        sql.append("			(SELECT TENPO_CD, ");
        sql.append("					BAIKA_HAISHIN_FG, ");
        sql.append("					SYOHIN_CD ");
		// 2017.05.23 #5100対応 T.Han (S)
		sql.append("            		,DELETE_FG ");
		// 2017.05.23 #5100対応 T.Han (E)
        sql.append("			FROM WK_TEC_EMG_PLU_REIGAI) WTEPR");
        sql.append("			ON 	WTEPR.SYOHIN_CD = EPS.SYOHIN_CD");
        // #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("			INNER JOIN ");
        sql.append("			(SELECT TENPO_CD ");
        // #2839 2016.11.24 S.Takayama (S)
        sql.append("			,GYOTAI_KB");
        // #2839 2016.11.24 S.Takayama (S)
        sql.append("			FROM TMP_R_EMG_TENPO");
        sql.append("			WHERE 	DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        sql.append("					ZAIMU_END_DT > '" + batchDt + "') TRET");
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        //sql.append("			ON 	TRET.TENPO_CD = RET.TENPO_CD");
        sql.append("			ON 	TRET.TENPO_CD = WTEPR.TENPO_CD ");
        // #3686 MSTB910 2017.02.14 S.Takayama (E)
/* #3232 Add 2016.12.16 Li.Sheng (S) */
        sql.append("			LEFT JOIN ");
        // #4775 Add 2017.04.27 S.Takayama (S)
        //sql.append("			(SELECT KOSEI_SYOHIN_CD ");
        sql.append("			(SELECT HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.27 S.Takayama (E)
        sql.append("			,TENPO_CD ");
        sql.append("			,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append("			FROM WK_HAMPER_KOSEI ");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("			WHERE HANBAI_END_DT <> '" + zenjituBatchDt + "' ");
        // 2017/04/28 T.Yajima #4845対応（E)
        // #4775 Add 2017.04.27 S.Takayama (S)
        //sql.append("			GROUP BY KOSEI_SYOHIN_CD,TENPO_CD ");
        sql.append("			GROUP BY HAMPER_SYOHIN_CD,TENPO_CD ");
        //sql.append("			) SUB1 ON EPS.SYOHIN_CD = SUB1.KOSEI_SYOHIN_CD ");
        sql.append("			) SUB1 ON EPS.SYOHIN_CD = SUB1.HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.27 S.Takayama (E)
        sql.append("			AND TRET.TENPO_CD = SUB1.TENPO_CD ");
/* #3232 Add 2016.12.16 Li.Sheng (E) */
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        //sql.append("	WHERE	EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        sql.append("	WHERE ");
        // #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("			EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
		// #6367 DEL 2022.01.27 SIEU.D (S)
//	    // 2016.11.07 T.han #1750対応（S)
//		sql.append("			AND GET_JAN_SYUBETSU(EPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'	 ");
//        // 2016.11.07 T.han #1750対応（E)
		// #6367 DEL 2022.01.27 SIEU.D (E)
        //2016/9/16 VINX h.sakamoto #1954対応 (S)
        sql.append("			AND EPS.EMG_FLAG = '"+ mst910020_EmgFlagDictionary.ON.getCode() +"' ");
        sql.append("			AND EPS.PLU_SEND_DT = '" + batchDt + "'");
        sql.append("			AND ( ");
        sql.append("				EPS.PLU_HANEI_TIME <= (SELECT SEND_TIME FROM POS_FILE_SEQ) ");
        sql.append("				OR EPS.PLU_HANEI_TIME IS NULL");
        sql.append("			) ");
        //2016/9/16 VINX h.sakamoto #1954対応 (E)
        /* #3720 Delete 2017.01.25 X.Liu (S) */
	    // 2016.11.07 T.han #1750対応（S)
		//sql.append("			AND ");
		//sql.append("			NOT EXISTS ");
		//sql.append("			( ");
		//sql.append("				SELECT ");
		//sql.append("			 		WTEPR.BUNRUI1_CD ");
		//sql.append("				FROM ");
		//sql.append("					WK_TEC_EMG_PLU_REIGAI WTEPR ");
		//sql.append("				WHERE ");
		//sql.append("					WTEPR.PLU_SEND_DT	<> '" + SPECIAL_DATE + "'	AND ");
		//sql.append("					WTEPR.BUNRUI1_CD	= EPS.BUNRUI1_CD			AND ");
		//sql.append("					WTEPR.SYOHIN_CD		= EPS.SYOHIN_CD			AND ");
		//sql.append("					WTEPR.TENPO_CD		= TRET.TENPO_CD ");
		//sql.append("			) ");
        // 2016.11.07 T.han #1750対応（E)
		/* #3720 Delete 2017.01.25 X.Liu (E) */
	     // #3686 MSTB910 2017.02.14 S.Takayama (S)
        sql.append("			AND ( ");
        sql.append("				WTEPR.BAIKA_HAISHIN_FG = '"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"' ");
		// 2017.05.23 #5100対応 T.Han (S)
		sql.append("            	AND WTEPR.DELETE_FG = '0' ");
		// 2017.05.23 #5100対応 T.Han (E)
        // #4775 Add 2017.04.27 S.Takayama (S)
        //sql.append("				OR SUB1.BAIKA_HAISHIN_FG ='"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"' ");
		// 2017.05.23 #5100対応 T.Han (S)
        //sql.append("				OR NVL(SUB1.BAIKA_HAISHIN_FG,'0') <> '"+ mst011701_BaikaHaishinFlagDictionary.SURU.getCode() +"' ");
        sql.append("				AND NVL(SUB1.BAIKA_HAISHIN_FG,'0') <> '"+ mst011701_BaikaHaishinFlagDictionary.SURU.getCode() +"' ");
		// 2017.05.23 #5100対応 T.Han (E)
        // #4775 Add 2017.04.27 S.Takayama (E)
        sql.append("			) ");
	     // #3686 MSTB910 2017.02.14 S.Takayama (E)
        return sql.toString();
    }

    /**
     * WK_TEC_EMG_PLUに例外商品をマージするSQLを取得する
     *
     * @return 例外商品をマージSQL
     */
    private String getWkTecEmgPluMergeSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE INTO");
        sql.append(" 	WK_TEC_EMG_PLU WTEP");
        sql.append(" 	USING");
        sql.append(" 		(");
        sql.append(" 			SELECT	EPS.BUNRUI1_CD AS BUNRUI1_CD,");
        sql.append(" 					EPS.SYOHIN_CD AS SYOHIN_CD,");
        sql.append(" 					TEPR.TENPO_CD AS TENPO_CD,");
        sql.append(" 					NVL(TEPR.GENTANKA_VL, EPS.GENTANKA_VL) AS GENTANKA_VL,");
        sql.append(" 					NVL(TEPR.BAITANKA_VL, EPS.BAITANKA_VL) AS BAITANKA_VL,");
        sql.append(" 					NVL(TEPR.SIIRESAKI_CD, EPS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
        sql.append(" 					NVL(TEPR.PLU_SEND_DT, EPS.PLU_SEND_DT) AS PLU_SEND_DT,");
        sql.append(" 					NVL(TEPR.BAIKA_HAISHIN_FG, EPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG,");
        sql.append(" 					EPS.BUNRUI5_CD AS BUNRUI5_CD,");
        sql.append(" 					EPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA,");
        sql.append(" 					EPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA,");
        sql.append(" 					EPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA,");
        sql.append(" 					EPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 					EPS.ZEI_KB AS ZEI_KB,");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append(" 					CASE ");
        //sql.append(" 						WHEN NVL(TEPR.GENTANKA_VL, EPS.GENTANKA_VL) > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "'");
        //sql.append(" 						WHEN NVL(TEPR.BAITANKA_VL, EPS.BAITANKA_VL) > " + KINGAKU_ERROR_CHECK_VL + " THEN '" + mst000102_IfConstDictionary.ERROR_KB_01 + "'");
        //sql.append(" 						ELSE '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'");
        //sql.append(" 					END AS ERR_KB,");
        sql.append("					'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        // #1306対応 2016.05.06 M.Kanno (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        sql.append(" 					EPS.HANBAI_TANI,");
        // #1306対応 2016.05.06 M.Kanno (S)
        //sql.append(" 					EPS.KEIRYOKI_NM");
        sql.append(" 					EPS.KEIRYOKI_NM,");
        sql.append("					EPS.BUNRUI2_CD, ");
        sql.append("					EPS.TEIKAN_FG, ");
        sql.append("					EPS.ZEI_RT, ");
        sql.append("					EPS.SEASON_ID, ");
        sql.append("					EPS.SYOHI_KIGEN_DT, ");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append("					EPS.CARD_FG ");
        sql.append("					EPS.CARD_FG, ");
        sql.append("					NVL(TEPR.PLU_HANEI_TIME, EPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME, ");
        sql.append("					EPS.SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("					EPS.LABEL_SEIBUN, ");
        sql.append("					EPS.LABEL_HOKAN_HOHO, ");
        sql.append("					EPS.LABEL_TUKAIKATA");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        // #1306対応 2016.05.06 M.Kanno (E)
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        sql.append(" 			FROM	WK_TEC_EMG_PLU_SYOHIN EPS INNER JOIN");
        sql.append(" 					(");
        sql.append(" 						SELECT BUNRUI1_CD,");
        sql.append(" 								SYOHIN_CD,");
        sql.append(" 								TENPO_CD,");
        sql.append(" 								GENTANKA_VL,");
        sql.append(" 								BAITANKA_VL,");
        sql.append(" 								SIIRESAKI_CD,");
        sql.append(" 								PLU_SEND_DT,");
        sql.append(" 								BAIKA_HAISHIN_FG,");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 								DELETE_FG");
        sql.append(" 								DELETE_FG,");
        sql.append(" 								PLU_HANEI_TIME");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        sql.append(" 						FROM WK_TEC_EMG_PLU_REIGAI");
        sql.append(" 						WHERE DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
        sql.append(" 					) TEPR");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append(" 					ON 	EPS.BUNRUI1_CD = TEPR.BUNRUI1_CD AND");
        sql.append(" 					ON 	");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append(" 						EPS.SYOHIN_CD = TEPR.SYOHIN_CD");
        sql.append(" 					INNER JOIN");
        sql.append(" 					(");
        sql.append(" 						SELECT TENPO_CD");
        sql.append(" 						FROM TMP_R_EMG_TENPO");
        sql.append(" 						WHERE 	DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        sql.append(" 								ZAIMU_END_DT > '" + batchDt + "'");
        sql.append(" 					) RET");
        sql.append(" 					ON RET.TENPO_CD = TEPR.TENPO_CD");
        sql.append(" 			WHERE	EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        // #6367 MOD 2022.01.27 SIEU.D (S)
        // sql.append(" 					EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' AND");
        // sql.append(" 					GET_JAN_SYUBETSU(EPS.SYOHIN_CD) <> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'");
        sql.append(" 					EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' ");
       // #6367 MOD 2022.01.27 SIEU.D (E)
        sql.append(" 		) WTEPS");
        sql.append(" 	ON");
        sql.append(" 	(");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append(" 		WTEP.BUNRUI1_CD = WTEPS.BUNRUI1_CD AND");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append(" 		WTEP.SYOHIN_CD = WTEPS.SYOHIN_CD AND");
        sql.append(" 		WTEP.TENPO_CD = WTEPS.TENPO_CD");
        sql.append(" 	)");
        sql.append(" WHEN MATCHED THEN");
        sql.append(" 	UPDATE");
        sql.append(" 		SET");
        sql.append(" 			WTEP.GENTANKA_VL = WTEPS.GENTANKA_VL,");
        sql.append(" 			WTEP.BAITANKA_VL = WTEPS.BAITANKA_VL,");
        sql.append(" 			WTEP.SIIRESAKI_CD = WTEPS.SIIRESAKI_CD,");
        sql.append(" 			WTEP.PLU_SEND_DT = WTEPS.PLU_SEND_DT,");
        sql.append(" 			WTEP.BAIKA_HAISHIN_FG = WTEPS.BAIKA_HAISHIN_FG,");
        sql.append(" 			WTEP.BUNRUI5_CD = WTEPS.BUNRUI5_CD,");
        sql.append(" 			WTEP.REC_HINMEI_KANJI_NA = WTEPS.REC_HINMEI_KANJI_NA,");
        sql.append(" 			WTEP.REC_HINMEI_KANA_NA = WTEPS.REC_HINMEI_KANA_NA,");
        sql.append(" 			WTEP.KIKAKU_KANJI_NA = WTEPS.KIKAKU_KANJI_NA,");
        sql.append(" 			WTEP.MAKER_KIBO_KAKAKU_VL = WTEPS.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			WTEP.ZEI_KB = WTEPS.ZEI_KB,");
        sql.append(" 			WTEP.ERR_KB = WTEPS.ERR_KB,");
        //add 2016.09.02 nv.cuong(S)
        sql.append(" 			WTEP.HANBAI_TANI = WTEPS.HANBAI_TANI,");
        sql.append(" 			WTEP.KEIRYOKI_NM = WTEPS.KEIRYOKI_NM,");
        sql.append(" 			WTEP.BUNRUI2_CD = WTEPS.BUNRUI2_CD,");
        sql.append(" 			WTEP.TEIKAN_FG = WTEPS.TEIKAN_FG,");
        sql.append(" 			WTEP.ZEI_RT = WTEPS.ZEI_RT,");
        sql.append(" 			WTEP.SEASON_ID = '',");
        sql.append(" 			WTEP.SYOHI_KIGEN_DT = WTEPS.SYOHI_KIGEN_DT,");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 			WTEP.CARD_FG = WTEPS.CARD_FG ");
        sql.append(" 			WTEP.CARD_FG = WTEPS.CARD_FG, ");
        sql.append(" 			WTEP.PLU_HANEI_TIME = WTEPS.PLU_HANEI_TIME, ");
        sql.append(" 			WTEP.SYOHI_KIGEN_HYOJI_PATTER = WTEPS.SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append(" 			WTEP.LABEL_SEIBUN = WTEPS.LABEL_SEIBUN, ");
        sql.append(" 			WTEP.LABEL_HOKAN_HOHO = WTEPS.LABEL_HOKAN_HOHO, ");
        sql.append(" 			WTEP.LABEL_TUKAIKATA = WTEPS.LABEL_TUKAIKATA ");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        //add 2016.09.02 nv.cuong(E)
        sql.append(" WHEN NOT MATCHED THEN");
        sql.append(" 	INSERT");
        sql.append(" 		(");
        sql.append(" 			BUNRUI1_CD,");
        sql.append(" 			SYOHIN_CD,");
        sql.append(" 			TENPO_CD,");
        sql.append(" 			GENTANKA_VL,");
        sql.append(" 			BAITANKA_VL,");
        sql.append(" 			SIIRESAKI_CD,");
        sql.append(" 			PLU_SEND_DT,");
        sql.append(" 			BAIKA_HAISHIN_FG,");
        sql.append(" 			BUNRUI5_CD,");
        sql.append(" 			REC_HINMEI_KANJI_NA,");
        sql.append(" 			REC_HINMEI_KANA_NA,");
        sql.append(" 			KIKAKU_KANJI_NA,");
        sql.append(" 			MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			ZEI_KB,");
        sql.append(" 			ERR_KB,");
        //add 2016.09.02 nv.cuong(S)
        sql.append(" 			HANBAI_TANI,");
        sql.append(" 			KEIRYOKI_NM,");
        sql.append(" 			BUNRUI2_CD,");
        sql.append(" 			TEIKAN_FG,");
        sql.append(" 			ZEI_RT,");
        sql.append(" 			SEASON_ID,");
        sql.append(" 			SYOHI_KIGEN_DT,");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 			CARD_FG");
        sql.append(" 			CARD_FG,");
        sql.append(" 			PLU_HANEI_TIME,");
        sql.append(" 			SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			LABEL_SEIBUN,");
        sql.append(" 			LABEL_HOKAN_HOHO,");
        sql.append(" 			LABEL_TUKAIKATA");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        //add 2016.09.02 nv.cuong(E)
        sql.append(" 		)");
        sql.append(" 	VALUES");
        sql.append(" 		(");
        sql.append(" 			WTEPS.BUNRUI1_CD,");
        sql.append(" 			WTEPS.SYOHIN_CD,");
        sql.append(" 			WTEPS.TENPO_CD,");
        sql.append(" 			WTEPS.GENTANKA_VL,");
        sql.append(" 			WTEPS.BAITANKA_VL,");
        sql.append(" 			WTEPS.SIIRESAKI_CD,");
        sql.append(" 			WTEPS.PLU_SEND_DT,");
        sql.append(" 			WTEPS.BAIKA_HAISHIN_FG,");
        sql.append(" 			WTEPS.BUNRUI5_CD,");
        sql.append(" 			WTEPS.REC_HINMEI_KANJI_NA,");
        sql.append(" 			WTEPS.REC_HINMEI_KANA_NA,");
        sql.append(" 			WTEPS.KIKAKU_KANJI_NA,");
        sql.append(" 			WTEPS.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 			WTEPS.ZEI_KB,");
        sql.append(" 			WTEPS.ERR_KB,");
        //add 2016.09.02 nv.cuong(S)
        sql.append(" 			WTEPS.HANBAI_TANI,");
        sql.append(" 			WTEPS.KEIRYOKI_NM,");
        sql.append(" 			WTEPS.BUNRUI2_CD,");
        sql.append(" 			WTEPS.TEIKAN_FG,");
        sql.append(" 			WTEPS.ZEI_RT,");
        sql.append(" 			'',");
        sql.append(" 			WTEPS.SYOHI_KIGEN_DT,");
        //2016/9/9 VINX h.sakamoto #1921対応 (S)
//        sql.append(" 			WTEPS.CARD_FG");
        sql.append(" 			WTEPS.CARD_FG,");
        sql.append(" 			WTEPS.PLU_HANEI_TIME,");
        sql.append(" 			WTEPS.SYOHI_KIGEN_HYOJI_PATTER,");
        sql.append(" 			WTEPS.LABEL_SEIBUN,");
        sql.append(" 			WTEPS.LABEL_HOKAN_HOHO,");
        sql.append(" 			WTEPS.LABEL_TUKAIKATA");
        //2016/9/9 VINX h.sakamoto #1921対応 (E)
        //add 2016.09.02 nv.cuong(E)
        sql.append(" 		)");
        return sql.toString();
    }

    //2016/9/20 VINX h.sakamoto #1954対応 (S)
    /**
     * WK_TEC_EMG_PLUの例外商品を上書きするSQLを取得する
     *
     * @return 例外商品をupdateするSQL
     */
    private String getWkTecEmgPluUpdateReigaiSql() throws SQLException {
    	StringBuilder sql = new StringBuilder();
    	sql.append("MERGE ");
    	sql.append("INTO WK_TEC_EMG_PLU WTEP ");
    	sql.append("  USING ( ");
    	sql.append("    SELECT");
    	sql.append("      EPS.BUNRUI1_CD AS BUNRUI1_CD");
    	sql.append("      , EPS.SYOHIN_CD AS SYOHIN_CD");
    	/* #3571 Add 2017.01.24 X.Liu (S) */
    	sql.append("      , EPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
    	/* #3571 Add 2017.01.24 X.Liu (E) */
    	sql.append("      , TEPR.TENPO_CD AS TENPO_CD");
    	sql.append("      , NVL(TEPR.GENTANKA_VL, EPS.GENTANKA_VL) AS GENTANKA_VL");
    	sql.append("      , NVL(TEPR.BAITANKA_VL, EPS.BAITANKA_VL) AS BAITANKA_VL");
    	sql.append("      , NVL(TEPR.SIIRESAKI_CD, EPS.SIIRESAKI_CD) AS SIIRESAKI_CD");
    	sql.append("      , NVL(TEPR.PLU_SEND_DT, EPS.PLU_SEND_DT) AS PLU_SEND_DT");
/* #3232 Mod 2016.12.16 Li.Sheng (S) */
//    	sql.append("      , NVL(TEPR.BAIKA_HAISHIN_FG, EPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
    	// #4775 Add 2017.04.24 S.Takayama (S)
    	//sql.append("		,DECODE(SUB1.BAIKA_HAISHIN_FG,'0',SUB1.BAIKA_HAISHIN_FG,TEPR.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
    	sql.append("		, CASE ");
    	// #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append("		WHEN SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
    	sql.append("		WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.HAMPER_SYOHIN_CD IS NULL THEN '1' ");
    	sql.append("		WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
        // #5033 Del 2017.05.17 S.Takayama (E)
        //sql.append("		ELSE EPS.BAIKA_HAISHIN_FG ");
        sql.append("		ELSE TEPR.BAIKA_HAISHIN_FG ");
        sql.append("		END AS BAIKA_HAISHIN_FG ");
        // #4775 Add 2017.04.24 S.Takayama (E)
/* #3232 Mod 2016.12.16 Li.Sheng (E) */
    	sql.append("      , EPS.BUNRUI5_CD AS BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
    	sql.append("      , EPS.HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
    	// #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("      , EPS.MAX_BUY_QT AS MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
    	sql.append("      , EPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
    	sql.append("      , EPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
    	sql.append("      , EPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
    	sql.append("      , EPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
    	sql.append("      , EPS.ZEI_KB AS ZEI_KB");
    	sql.append("      ,'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB");
    	sql.append("      , EPS.HANBAI_TANI AS HANBAI_TANI");
    	sql.append("      , EPS.KEIRYOKI_NM AS KEIRYOKI_NM");
    	sql.append("      , EPS.BUNRUI2_CD AS BUNRUI2_CD");
    	sql.append("      , EPS.TEIKAN_FG AS TEIKAN_FG");
    	sql.append("      , EPS.ZEI_RT AS ZEI_RT");
        // #6728 MOD 2023.02.16 SIEU.D (S)
//    // #6367 Mod 2021.10.22 Duy.HK (S)
//    	//sql.append("      , EPS.SEASON_ID AS SEASON_ID");
//    	 sql.append("     , CASE ");
//         sql.append("           WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)
//     	sql.append("        	WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//     	// #6655 ADD 2022.10.04 VU.TD (E)
//         sql.append("           ELSE EPS.SEASON_ID ");
//         sql.append("       END AS SEASON_ID ");
//    // #6367 Mod 2021.10.22 Duy.HK (E)
    	sql.append("      , EPS.SEASON_ID AS SEASON_ID");
         // #6728 MOD 2023.02.16 SIEU.D (E)
    	sql.append("      , EPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
    	sql.append("      , EPS.CARD_FG AS CARD_FG");
    	sql.append("      , NVL(TEPR.PLU_HANEI_TIME, EPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME");
    	sql.append("      , EPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
    	sql.append("      , EPS.LABEL_SEIBUN AS LABEL_SEIBUN");
    	sql.append("      , EPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
    	sql.append("      , EPS.LABEL_TUKAIKATA  AS LABEL_TUKAIKATA");
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("	  , EPS.LABEL_COUNTRY_NA");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	  , EPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB910 2017.02.17 S.Takayama (S)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
//		sql.append("      , GREATEST（EPS.DELETE_FG，TEPR.DELETE_FG）AS DELETE_FG ");
		// #3686 MSTB910 2017.02.17 S.Takayama (E)
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("			,DECODE(SUB1.BAIKA_HAISHIN_FG,'0','0',GREATEST（EPS.DELETE_FG，TEPR.DELETE_FG)) AS DELETE_FG ");
		//sql.append("		, EPS.DELETE_FG	 ");
		sql.append("			, GREATEST(EPS.DELETE_FG,TEPR.DELETE_FG) DELETE_FG ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		// #4418 2017.03.28 Mod Li.Sheng 対応（E)
    	sql.append("    FROM");
    	sql.append("      WK_TEC_EMG_PLU_SYOHIN EPS ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
//        // #6367 Add 2021.10.22 Duy.HK (S)
//         sql.append("    LEFT JOIN  ");
//         sql.append("        (SELECT   SYOHIN_CD     ");
//         sql.append("                 ,BUNRUI1_CD    ");
//         sql.append("                 ,WARIBIKI_KB   ");
//         sql.append("         FROM   ");
//         sql.append("               R_SYOHIN   RS_1  ");
//         sql.append("         WHERE  ");
//         sql.append("               RS_1.YUKO_DT = ( ");
//         sql.append("                              SELECT  MAX(YUKO_DT)   ");
//         sql.append("                              FROM   ");
//         sql.append("                                   R_SYOHIN    RS_2  ");
//         sql.append("                              WHERE ");
//         // #6620 MOD 2022.11.18 VU.TD (S)
////         sql.append("                                       RS_2.BUNRUI1_CD = RS_1.BUNRUI1_CD");
////         sql.append("                                   AND RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//         sql.append("                                    RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//         // #6620 MOD 2022.11.18 VU.TD (E)
//         sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//         sql.append("                              ) ");
//         sql.append("        )  RS  ");
//         sql.append("     ON    EPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//         // #6620 DEL 2022.11.18 VU.TD (S)
////         sql.append("       AND EPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//         // #6620 DEL 2022.11.18 VU.TD (E)
//        //#6367 Add 2021.10.22 Duy.HK (E)
         // #6728 DEL 2023.02.16 SIEU.D (E)
    	sql.append("      INNER JOIN WK_TEC_EMG_PLU_REIGAI TEPR ");
    	// #6620 MOD 2022.11.18 VU.TD (S)
//    	sql.append("        ON EPS.BUNRUI1_CD = TEPR.BUNRUI1_CD ");
//    	sql.append("        AND EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
    	sql.append("        ON EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
    	// #6620 MOD 2022.11.18 VU.TD (E)
    	sql.append("      INNER JOIN TMP_R_EMG_TENPO RET ");
    	sql.append("        ON RET.TENPO_CD = TEPR.TENPO_CD ");
    	sql.append("        AND RET.DELETE_FG =　'" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
    	sql.append("        AND ZAIMU_END_DT > '" + batchDt + "'");
/* #3232 Add 2016.12.16 Li.Sheng (S) */
        sql.append("			LEFT JOIN ");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			(SELECT KOSEI_SYOHIN_CD ");
        sql.append("			(SELECT HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			,TENPO_CD ");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append("			,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			FROM WK_HAMPER_KOSEI ");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("			WHERE HANBAI_END_DT <> '" + zenjituBatchDt + "' ");
        // 2017/04/28 T.Yajima #4845対応（E)
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			GROUP BY KOSEI_SYOHIN_CD,TENPO_CD ");
        //sql.append("			) SUB1 ON TEPR.SYOHIN_CD = SUB1.KOSEI_SYOHIN_CD ");
        sql.append("			GROUP BY HAMPER_SYOHIN_CD,TENPO_CD ");
        sql.append("			) SUB1 ON TEPR.SYOHIN_CD = SUB1.HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			AND TEPR.TENPO_CD = SUB1.TENPO_CD ");
/* #3232 Add 2016.12.16 Li.Sheng (E) */
    	sql.append("    WHERE");
    	// #3686 MSTB910 2017.02.17 S.Takayama (S)
    	//sql.append("      EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
    	//sql.append("      AND TEPR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
    	//sql.append("      AND EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
    	sql.append("      EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
    	// #3686 MSTB910 2017.02.17 S.Takayama (E)
        // #6367 DEL 2022.01.27 SIEU.D (S)
    	// sql.append("      AND GET_JAN_SYUBETSU(EPS.SYOHIN_CD) <> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'");
        // #6367 DEL 2022.01.27 SIEU.D (E)
	    // 2016.11.07 T.han #1750対応（S)
		sql.append("	  AND ");
		sql.append("	  ( ");
		/* #3720 Mod 2017.01.25 X.Liu (S) */
		//sql.append("		  (EPS.PLU_SEND_DT				= '" + batchDt + "'		AND ");
		//sql.append("		  NVL(TEPR.PLU_SEND_DT,' ')		<> '" + SPECIAL_DATE + "')	OR  ");
		sql.append("		  EPS.PLU_SEND_DT				= '" + batchDt + "'		OR ");
		/* #3720 Mod 2017.01.25 X.Liu (E) */
		sql.append("		  NVL(TEPR.PLU_SEND_DT,' ')		= '" + batchDt + "'			");
		sql.append("	  ) ");
	    // 2016.11.07 T.han #1750対応（E)
    	sql.append("  ) WTEPS ");
    	sql.append("    ON ( ");
    	sql.append("      WTEP.SYOHIN_CD = WTEPS.SYOHIN_CD ");
    	sql.append("      AND WTEP.TENPO_CD = WTEPS.TENPO_CD");
    	sql.append("    ) WHEN MATCHED THEN UPDATE ");
    	sql.append("SET");
    	sql.append("  WTEP.GENTANKA_VL = WTEPS.GENTANKA_VL");
    	sql.append("  , WTEP.BAITANKA_VL = WTEPS.BAITANKA_VL");
    	sql.append("  , WTEP.SIIRESAKI_CD = WTEPS.SIIRESAKI_CD");
    	sql.append("  , WTEP.PLU_SEND_DT = WTEPS.PLU_SEND_DT");
    	sql.append("  , WTEP.BAIKA_HAISHIN_FG = WTEPS.BAIKA_HAISHIN_FG");
    	sql.append("  , WTEP.BUNRUI5_CD = WTEPS.BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
    	sql.append("  , WTEP.HINMEI_KANJI_NA = WTEPS.HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("  , WTEP.MAX_BUY_QT = WTEPS.MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
    	sql.append("  , WTEP.REC_HINMEI_KANJI_NA = WTEPS.REC_HINMEI_KANJI_NA");
    	sql.append("  , WTEP.REC_HINMEI_KANA_NA = WTEPS.REC_HINMEI_KANA_NA");
    	sql.append("  , WTEP.KIKAKU_KANJI_NA = WTEPS.KIKAKU_KANJI_NA");
    	sql.append("  , WTEP.MAKER_KIBO_KAKAKU_VL = WTEPS.MAKER_KIBO_KAKAKU_VL");
    	sql.append("  , WTEP.ZEI_KB = WTEPS.ZEI_KB");
    	sql.append("  , WTEP.ERR_KB = WTEPS.ERR_KB");
    	sql.append("  , WTEP.HANBAI_TANI = WTEPS.HANBAI_TANI");
    	sql.append("  , WTEP.KEIRYOKI_NM = WTEPS.KEIRYOKI_NM");
    	sql.append("  , WTEP.BUNRUI2_CD = WTEPS.BUNRUI2_CD");
    	sql.append("  , WTEP.TEIKAN_FG = WTEPS.TEIKAN_FG");
    	sql.append("  , WTEP.ZEI_RT = WTEPS.ZEI_RT");
    	// #6629 MOD 2022.07.25 SIEU.D (S)
    	// sql.append("  , WTEP.SEASON_ID = ''");
    	sql.append("  , WTEP.SEASON_ID = WTEPS.SEASON_ID ");
    	// #6629 MOD 2022.07.25 SIEU.D (E)
    	sql.append("  , WTEP.SYOHI_KIGEN_DT = WTEPS.SYOHI_KIGEN_DT");
    	sql.append("  , WTEP.CARD_FG = WTEPS.CARD_FG");
    	sql.append("  , WTEP.PLU_HANEI_TIME = WTEPS.PLU_HANEI_TIME");
    	sql.append("  , WTEP.SYOHI_KIGEN_HYOJI_PATTER = WTEPS.SYOHI_KIGEN_HYOJI_PATTER");
    	sql.append("  , WTEP.LABEL_SEIBUN = WTEPS.LABEL_SEIBUN");
    	sql.append("  , WTEP.LABEL_HOKAN_HOHO = WTEPS.LABEL_HOKAN_HOHO");
    	sql.append("  , WTEP.LABEL_TUKAIKATA = WTEPS.LABEL_TUKAIKATA");
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("  , WTEP.LABEL_COUNTRY_NA = WTEPS.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , WTEP.HANBAI_TANI_EN = WTEPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		
		/* #3571 Add 2017.01.24 X.Liu (S) */
		sql.append("  , WTEP.OLD_SYOHIN_CD = WTEPS.OLD_SYOHIN_CD ");
		/* #3571 Add 2017.01.24 X.Liu (E) */
		// #3686 MSTB910 2017.02.17 S.Takayama (S)
		sql.append("  , WTEP.DELETE_FG = WTEPS.DELETE_FG ");
		// #3686 MSTB910 2017.02.17 S.Takayama (E)
    	return sql.toString();
    }
    //2016/9/20 VINX h.sakamoto #1954対応 (E)

    //2016/9/20 VINX h.sakamoto #1954対応 (S)
    /**
     * WK_TEC_EMG_PLUに例外商品を追加するSQLを取得する
     *
     * @return 例外商品をinsertするSQL
     */
    private String getWkTecEmgPluInsertReigaiSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("MERGE ");
        sql.append("INTO WK_TEC_EMG_PLU WTEP ");
        sql.append("  USING ( ");
        sql.append("    SELECT");
        sql.append("      EPS.BUNRUI1_CD AS BUNRUI1_CD");
        sql.append("      , EPS.SYOHIN_CD AS SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (S) */
        sql.append("      , EPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (E) */
        sql.append("      , TEPR.TENPO_CD AS TENPO_CD");
        sql.append("      , NVL(TEPR.GENTANKA_VL, EPS.GENTANKA_VL) AS GENTANKA_VL");
        sql.append("      , NVL(TEPR.BAITANKA_VL, EPS.BAITANKA_VL) AS BAITANKA_VL");
        sql.append("      , NVL(TEPR.SIIRESAKI_CD, EPS.SIIRESAKI_CD) AS SIIRESAKI_CD");
        sql.append("      , NVL(TEPR.PLU_SEND_DT, EPS.PLU_SEND_DT) AS PLU_SEND_DT");
/* #3232 Mod 2016.12.16 Li.Sheng (S) */
//    	sql.append("      , NVL(TEPR.BAIKA_HAISHIN_FG, EPS.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
        // #4775 Add 2017.04.24 S.Takayama (S)
    	//sql.append("		,DECODE(SUB1.BAIKA_HAISHIN_FG,'0',SUB1.BAIKA_HAISHIN_FG,TEPR.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append("		, CASE ");
        // #5033 Del 2017.05.17 S.Takayama (S)
        //sql.append("		WHEN SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
    	sql.append("		WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.HAMPER_SYOHIN_CD IS NULL THEN '1' ");
    	sql.append("		WHEN EPS.HAMPER_SYOHIN_FG = '1' AND SUB1.BAIKA_HAISHIN_FG = '1' THEN '1' ");
        // #5033 Del 2017.05.17 S.Takayama (E)
        sql.append("		ELSE TEPR.BAIKA_HAISHIN_FG ");
        sql.append("		END AS BAIKA_HAISHIN_FG ");
        // #4775 Add 2017.04.24 S.Takayama (E)
/* #3232 Mod 2016.12.16 Li.Sheng (E) */
        sql.append("      , EPS.BUNRUI5_CD AS BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("      , EPS.HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("      , EPS.MAX_BUY_QT AS MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("      , EPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
        sql.append("      , EPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
        sql.append("      , EPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
        sql.append("      , EPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
        sql.append("      , EPS.ZEI_KB AS ZEI_KB");
        sql.append("      , '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB");
        sql.append("      , EPS.HANBAI_TANI");
        sql.append("      , EPS.KEIRYOKI_NM");
        sql.append("      , EPS.BUNRUI2_CD");
        sql.append("      , EPS.TEIKAN_FG");
        sql.append("      , EPS.ZEI_RT");
        // #6728 DEL 2023.02.16 SIEU.D (S)
//        // #6367 Mod 2021.10.22 Duy.HK (S)
//    	//sql.append("      , EPS.SEASON_ID AS SEASON_ID");
//        sql.append("     , CASE ");
//        sql.append("           WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//     	sql.append("        	WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//     	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("           ELSE EPS.SEASON_ID ");
//        sql.append("       END AS SEASON_ID ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        // #6728 ADD 2023.02.16 SIEU.D (S)
        sql.append("      , EPS.SEASON_ID AS SEASON_ID");
        // #6728 ADD 2023.02.16 SIEU.D (E)
        sql.append("      , EPS.SYOHI_KIGEN_DT");
        sql.append("      , EPS.CARD_FG");
        sql.append("      , NVL(TEPR.PLU_HANEI_TIME, EPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME");
        sql.append("      , EPS.SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("      , EPS.LABEL_SEIBUN");
        sql.append("      , EPS.LABEL_HOKAN_HOHO");
        sql.append("      , EPS.LABEL_TUKAIKATA ");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("      ,RET.GYOTAI_KB AS GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("	  , EPS.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  	  , EPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB910 2017.02.17 S.Takayama (S)
//		sql.append("      , GREATEST（EPS.DELETE_FG，TEPR.DELETE_FG）AS DELETE_FG ");
		// #4775 Add 2017.04.24 S.Takayama (S)
		//sql.append("			,DECODE(SUB1.BAIKA_HAISHIN_FG,'0','0',GREATEST（EPS.DELETE_FG，TEPR.DELETE_FG)) AS DELETE_FG ");
		//sql.append("			, EPS.DELETE_FG ");
		sql.append("			, GREATEST(EPS.DELETE_FG,TEPR.DELETE_FG) DELETE_FG ");
		// #4775 Add 2017.04.24 S.Takayama (E)
		// #3686 MSTB910 2017.02.17 S.Takayama (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
        sql.append("    FROM");
        sql.append("      WK_TEC_EMG_PLU_SYOHIN EPS ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
//        // #6367 Add 2021.10.22 Duy.HK  (S)
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
//        sql.append("     ON    EPS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND EPS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        sql.append("      INNER JOIN WK_TEC_EMG_PLU_REIGAI TEPR ");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append("        ON EPS.BUNRUI1_CD = TEPR.BUNRUI1_CD ");
//        sql.append("        AND EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
        sql.append("        ON EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append("      INNER JOIN TMP_R_EMG_TENPO RET ");
        sql.append("        ON RET.TENPO_CD = TEPR.TENPO_CD ");
        sql.append("        AND RET.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
        sql.append("        AND ZAIMU_END_DT > '" + batchDt + "'");
/* #3232 Add 2016.12.16 Li.Sheng (S) */
        sql.append("			LEFT JOIN ");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			(SELECT KOSEI_SYOHIN_CD ");
        sql.append("			(SELECT HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			,TENPO_CD ");
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        sql.append("			,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			FROM WK_HAMPER_KOSEI ");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("			WHERE HANBAI_END_DT <> '" + zenjituBatchDt + "' ");
        // 2017/04/28 T.Yajima #4845対応（E)
        // #4775 Add 2017.04.24 S.Takayama (S)
        //sql.append("			GROUP BY KOSEI_SYOHIN_CD,TENPO_CD ");
        //sql.append("			) SUB1 ON TEPR.SYOHIN_CD = SUB1.KOSEI_SYOHIN_CD ");
        sql.append("			GROUP BY HAMPER_SYOHIN_CD,TENPO_CD ");
        sql.append("			) SUB1 ON TEPR.SYOHIN_CD = SUB1.HAMPER_SYOHIN_CD ");
        // #4775 Add 2017.04.24 S.Takayama (E)
        sql.append("			AND TEPR.TENPO_CD = SUB1.TENPO_CD ");
/* #3232 Add 2016.12.16 Li.Sheng (E) */
        sql.append("    WHERE");
        // #3686 MSTB910 2017.02.17 S.Takayama (S)
        //sql.append("      EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
        //sql.append("      AND TEPR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
        //sql.append("      AND EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
        sql.append("      EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
        // #3686 MSTB910 2017.02.17 S.Takayama (E)
        // #6367 DEL 2022.01.27 SIEU.D (S)
        // sql.append("      AND GET_JAN_SYUBETSU(EPS.SYOHIN_CD) <>  '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'");
        // #6367 DEL 2022.01.27 SIEU.D (E)
        sql.append("      AND TEPR.EMG_FLG = '"+ mst910020_EmgFlagDictionary.ON.getCode() +"' ");
        sql.append("      AND TEPR.PLU_SEND_DT = '" + batchDt + "'");
        sql.append("      AND ( ");
        /* #1954 Mod 2016.09.29 Li.Sheng (S) */
//        sql.append("        TEPR.PLU_HANEI_TIME < (SELECT SEND_TIME FROM POS_FILE_SEQ) ");
        sql.append("        TEPR.PLU_HANEI_TIME <= (SELECT SEND_TIME FROM POS_FILE_SEQ) ");
        /* #1954 Mod 2016.09.29 Li.Sheng (E) */
        sql.append("        OR TEPR.PLU_HANEI_TIME IS NULL");
        sql.append("      ) ");
        /* #1954 Del 2016.09.29 Li.Sheng (S) */
//        sql.append("      AND EXISTS ( ");
//        sql.append("        SELECT");
//        sql.append("          1");
//        sql.append("        FROM");
//        sql.append("          WK_TEC_EMG_PLU WTEP1 ");
//        sql.append("          INNER JOIN WK_TEC_EMG_PLU_REIGAI TEPR1 ");
//        sql.append("            ON TEPR1.SYOHIN_CD = WTEP1.SYOHIN_CD ");
//        sql.append("            AND TEPR1.TENPO_CD = WTEP1.TENPO_CD");
//        sql.append("      )");
        /* #1954 Del 2016.09.29 Li.Sheng (E) */
        sql.append("  ) WTEPS ");
        sql.append("    ON ( ");
        sql.append("      WTEP.SYOHIN_CD = WTEPS.SYOHIN_CD ");
        sql.append("      AND WTEP.TENPO_CD = WTEPS.TENPO_CD");
        sql.append("    ) WHEN NOT MATCHED THEN ");
        sql.append("INSERT ( ");
        sql.append("  BUNRUI1_CD");
        sql.append("  , SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (S) */
        sql.append("  , OLD_SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (E) */
        sql.append("  , TENPO_CD");
        sql.append("  , GENTANKA_VL");
        sql.append("  , BAITANKA_VL");
        sql.append("  , SIIRESAKI_CD");
        sql.append("  , PLU_SEND_DT");
        sql.append("  , BAIKA_HAISHIN_FG");
        sql.append("  , BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("  , HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("  , MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("  , REC_HINMEI_KANJI_NA");
        sql.append("  , REC_HINMEI_KANA_NA");
        sql.append("  , KIKAKU_KANJI_NA");
        sql.append("  , MAKER_KIBO_KAKAKU_VL");
        sql.append("  , ZEI_KB");
        sql.append("  , ERR_KB");
        sql.append("  , HANBAI_TANI");
        sql.append("  , KEIRYOKI_NM");
        sql.append("  , BUNRUI2_CD");
        sql.append("  , TEIKAN_FG");
        sql.append("  , ZEI_RT");
        sql.append("  , SEASON_ID");
        sql.append("  , SYOHI_KIGEN_DT");
        sql.append("  , CARD_FG");
        sql.append("  , PLU_HANEI_TIME");
        sql.append("  , SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("  , LABEL_SEIBUN");
        sql.append("  , LABEL_HOKAN_HOHO");
        sql.append("  , LABEL_TUKAIKATA");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
    	sql.append("  , GYOTAI_KB");
    	// #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("  , LABEL_COUNTRY_NA");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB910 2017.02.17 S.Takayama (S)
		sql.append("      , DELETE_FG ");
		// #3686 MSTB910 2017.02.17 S.Takayama (E)
        sql.append(") ");
        sql.append("VALUES ( ");
        sql.append("  WTEPS.BUNRUI1_CD");
        sql.append("  , WTEPS.SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (S) */
        sql.append("  , WTEPS.OLD_SYOHIN_CD");
        /* #3571 Add 2017.01.24 X.Liu (E) */
        sql.append("  , WTEPS.TENPO_CD");
        sql.append("  , WTEPS.GENTANKA_VL");
        sql.append("  , WTEPS.BAITANKA_VL");
        sql.append("  , WTEPS.SIIRESAKI_CD");
        sql.append("  , WTEPS.PLU_SEND_DT");
        sql.append("  , WTEPS.BAIKA_HAISHIN_FG");
        sql.append("  , WTEPS.BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("  , WTEPS.HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("  , WTEPS.MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("  , WTEPS.REC_HINMEI_KANJI_NA");
        sql.append("  , WTEPS.REC_HINMEI_KANA_NA");
        sql.append("  , WTEPS.KIKAKU_KANJI_NA");
        sql.append("  , WTEPS.MAKER_KIBO_KAKAKU_VL");
        sql.append("  , WTEPS.ZEI_KB");
        sql.append("  , WTEPS.ERR_KB");
        sql.append("  , WTEPS.HANBAI_TANI");
        sql.append("  , WTEPS.KEIRYOKI_NM");
        sql.append("  , WTEPS.BUNRUI2_CD");
        sql.append("  , WTEPS.TEIKAN_FG");
        sql.append("  , WTEPS.ZEI_RT");
    	// #6629 MOD 2022.07.25 SIEU.D (S)
        // sql.append("  , ''");
        sql.append("  , WTEPS.SEASON_ID ");
    	// #6629 MOD 2022.07.25 SIEU.D (E)
        sql.append("  , WTEPS.SYOHI_KIGEN_DT");
        sql.append("  , WTEPS.CARD_FG");
        sql.append("  , WTEPS.PLU_HANEI_TIME");
        sql.append("  , WTEPS.SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("  , WTEPS.LABEL_SEIBUN");
        sql.append("  , WTEPS.LABEL_HOKAN_HOHO");
        sql.append("  , WTEPS.LABEL_TUKAIKATA");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
    	sql.append("  , WTEPS.GYOTAI_KB");
    	// #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/09 VINX t.han #3049対応（S)
		sql.append("  , WTEPS.LABEL_COUNTRY_NA ");
	    // 2016/12/09 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , WTEPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #3686 MSTB910 2017.02.17 S.Takayama (S)
		sql.append("      , WTEPS.DELETE_FG ");
		// #3686 MSTB910 2017.02.17 S.Takayama (E)
        sql.append(") ");
        return sql.toString();
    }
    //2016/9/20 VINX h.sakamoto #1954対応 (E)

    //2016/9/20 VINX h.sakamoto #1954対応 (S)
    /**
     * 配信時間の更新するSQLを取得
     *
     * @return
     * @throws SQLException
     */
    private String getPosFileSeqUpdSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE POS_FILE_SEQ ");
	    sql.append("SET");
	    sql.append("  SEND_TIME = ( ");
	    sql.append("    SELECT");
	    // 2016.12.05 M.Akagi #3102 (S)
	    //sql.append("      TO_CHAR(TO_NUMBER(SEND_TIME) + 200) ");
	    sql.append("      CASE ");
	    sql.append("      WHEN SEND_TIME LIKE '%30' THEN LPAD(TO_CHAR(TO_NUMBER(SEND_TIME) + 70),4,'0') ");
	    sql.append("      ELSE LPAD(TO_CHAR(TO_NUMBER(SEND_TIME) + 30),4,'0') ");
	    sql.append("      END AS SEND_TIME ");
	    // 2016.12.05 M.Akagi #3102 (E)
	    sql.append("    FROM");
	    sql.append("      POS_FILE_SEQ");
	    sql.append("  ) ");
	    return sql.toString();
    }
    //2016/9/20 VINX h.sakamoto #1954対応 (E)

    // 2017.03.02 M.Akagi #4239 (S)
	/**
	 * アナライズするSQLを返します。
	 * @param ownName ユーザ名
	 * @param tableName テーブル名
	 * @return　アナライズするSQL
	 */
	private String getAnalizeSql(String ownName, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("BEGIN");
		sql.append("	DBMS_STATS.GATHER_TABLE_STATS(");
		sql.append("		OWNNAME => '" + ownName + "',");
		sql.append("		TABNAME => '" + tableName + "',");
		sql.append("		METHOD_OPT => 'FOR ALL INDEXED',");
		sql.append("		CASCADE => TRUE");
		sql.append("	);");
		sql.append("END;");
		return sql.toString();
	}
	// 2017.03.02 M.Akagi #4239 (E)

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
