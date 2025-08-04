package mdware.master.batch.process.MSTB910;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * <p>タイトル: MSTB910040_GroupbaihenExcludeReflect.java クラス</p>
 * <p>説明: グループ売変除外品反映処理</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @version 1.00  (2016.09.05) VINX h.sakamoto #1566対応
 * @version 1.01  (2016.09.12) VINX h.sakamoto #1921対応
 * @version 1.02  (2016.11.28) #2839対応 VINX S.Takayama
 * @version 1.03  (2016.12.12) T.Han #3049 FIVImart対応
 * @version 1.04  (2016.12.16) S.Takayama #3232 FIVImart対応
 * @version 1.05  (2017.01.23) ML.Son #3571 FIVImart対応
 * @version 1.06  (2017.02.09) #3765対応 T.Han FIVImart対応
 * @version 1.07  (2017.02.14) #3686 S.Takayama FIVImart対応
 * @version 1.08  (2017.02.24) #4064対応 T.Yajima FIVImart対応
 * @version 1.09  (2017.03.28) Li.Sheng #4418対応
 * @version 1.10  (2017.03.29) Li.Sheng #4417対応
 * @version 1.11  (2017.04.19) T.Han #4705対応
 * @version 1.12  (2017.04.28) T.Yajima #4845対応
 * @version 1.13  (2020.07.13) KHAI.NN #6167 MKV対応
 * @Version 1.14  (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB910040_GroupbaihenExcludeReflect {
    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK_GROUPBAIHEN_EXCLUDE_ASN = "WK_GROUPBAIHEN_EXCLUDE_ASN"; // WK_グループ売変除外品ASN

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチID    
    String jobId = null;

    // バッチ日付
    private String batchDt = null;
    
	 // #3232 MSTB910 2016.12.16 S.Takayama (S)
    // バッチ日付-1
    private String minusBatchDt = null;
	 // #3232 MSTB910 2016.12.16 S.Takayama (E)

    /** ゼロゼロ (定数) */
    private static final String CONST_DOUBLE_ZERO = "00";

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB910040_GroupbaihenExcludeReflect(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB910040_GroupbaihenExcludeReflect() {
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

    		// jobId
    		jobId = userLog.getJobId();

    		// トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            // #3232 MSTB910 2016.12.16 S.Takayama (S)
            // バッチ日付-1
            minusBatchDt = DateChanger.addDate(batchDt, -1);
            // #3232 MSTB910 2016.12.16 S.Takayama (E)

            // WK_グループ売変除外品ASNのTRUNCATE
            writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK_GROUPBAIHEN_EXCLUDE_ASN);
            writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN削除処理を終了します。");

            // WK_グループ売変除外品ASNの登録
            writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN登録処理を開始します。");
            iRec = db.executeUpdate(getWkGroupBaihenExcludeInsertSql());
            writeLog(Level.INFO_INT, "WK_グループ売変除外品ASNを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "WK_グループ売変除外品ASN登録処理を終了します。");

            db.commit();

            // WK_緊急PLU店別商品の反映
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品反映処理を開始します。");
            iRec = db.executeUpdate(getWkTecEmgPluMergeSql());
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品を" + iRec + "件マージしました。");
            writeLog(Level.INFO_INT, "WK_緊急PLU店別商品反映処理を終了します。");

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
     * WK_グループ売変除外品ASNを作成するSQLを取得する
     *
     * @return WK_GROUPBAIHEN_EXCLUDE_ASN登録SQL
     */
    private String getWkGroupBaihenExcludeInsertSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO WK_GROUPBAIHEN_EXCLUDE_ASN( ");
        sql.append("  SYOHIN_CD");
        sql.append("  , TENPO_CD");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("  SELECT");
        sql.append("    DGEA.SYOHIN_CD");
        sql.append("    , DHTT.TENPO_CD");
        sql.append("    , '" + jobId + "'");
        sql.append("    , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("    , '" + jobId + "'");
        sql.append("    , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("  FROM");
        sql.append("    DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
        sql.append("    INNER JOIN DT_THEME DT ");
        sql.append("      ON DGEA.THEME_CD = DT.THEME_CD ");
        sql.append("    INNER JOIN DT_HANSOKU_TAISYO_TENPO DHTT ");
        sql.append("      ON DGEA.THEME_CD = DHTT.THEME_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        sql.append("	INNER JOIN ");
        sql.append("		WK_TEC_EMG_PLU_SYOHIN WTEPS ");
        sql.append("	ON ");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append("		WTEPS.SYOHIN_CD = DGEA.SYOHIN_CD AND ");
//        sql.append("		WTEPS.BUNRUI1_CD = DT.BUNRUI1_CD ");
        sql.append("		WTEPS.SYOHIN_CD = DGEA.SYOHIN_CD ");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append("	INNER JOIN ");
        sql.append("		WK_TEC_EMG_PLU_REIGAI WTEPR ");
        sql.append("	ON ");
        sql.append("		WTEPR.SYOHIN_CD = DGEA.SYOHIN_CD AND ");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append("		WTEPR.BUNRUI1_CD = DT.BUNRUI1_CD AND ");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append("		WTEPR.TENPO_CD = DHTT.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("  WHERE");
        // #4064 MSTB101 2017.02.24 T.Yajima Add (S)
        sql.append("    DT.DELETE_FG  = '0'  AND ");
        // #4064 MSTB101 2017.02.24 T.Yajima Add (E)
        sql.append("    DT.HANBAI_START_DT <= '" + batchDt + "'");
        sql.append("    AND DT.HANBAI_END_DT >= '" + batchDt + "'");
        sql.append("  GROUP BY ");
        sql.append("    DGEA.SYOHIN_CD");
        sql.append("    , DHTT.TENPO_CD");
        return sql.toString();
    }

    /**
     * WK_TEC_EMG_PLUに例外商品をマージするSQLを取得する
     *
     * @return 例外商品マージSQL
     */
    private String getWkTecEmgPluMergeSql() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("MERGE ");
        sql.append("INTO WK_TEC_EMG_PLU ");
      sql.append("  USING ( ");
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//        sql.append("    SELECT");
//        sql.append("      WTEPS.BUNRUI1_CD AS BUNRUI1_CD");
//        sql.append("      , WGEA.SYOHIN_CD AS SYOHIN_CD");
//        //#3571 MSTB910 2017.01.23 ML.Son (S)
//        sql.append("      , WTEPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
//        //#3571 MSTB910 2017.01.23 ML.Son (E)
//        sql.append("      , WGEA.TENPO_CD AS TENPO_CD");
//        sql.append("      , WTEPS.GENTANKA_VL AS GENTANKA_VL");
//        sql.append("      , WTEPS.BAITANKA_VL AS BAITANKA_VL");
//        sql.append("      , WTEPS.SIIRESAKI_CD AS SIIRESAKI_CD");
//        sql.append("      , WTEPS.PLU_SEND_DT AS PLU_SEND_DT");
//        //#3232 MSTB910 2016.12.16 S.Takayama (S)
//        //sql.append("      , WTEPS.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG");
//        sql.append("      , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0'");
//        sql.append("        ELSE WTEPS.BAIKA_HAISHIN_FG");
//        sql.append("        END AS BAIKA_HAISHIN_FG");
//        //#3232 MSTB910 2016.12.16 S.Takayama (E)
//        sql.append("      , WTEPS.BUNRUI5_CD AS BUNRUI5_CD");
//        sql.append("      , WTEPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
//        sql.append("      , WTEPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
//        sql.append("      , WTEPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
//        sql.append("      , WTEPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
//        sql.append("      , WTEPS.ZEI_KB AS ZEI_KB");
//        sql.append("      , WTEPS.BUNRUI2_CD AS BUNRUI2_CD");
//        sql.append("      , WTEPS.TEIKAN_FG AS TEIKAN_FG");
//        sql.append("      , WTEPS.ZEI_RT AS ZEI_RT");
//        sql.append("      , WTEPS.BUNRUI5_CD AS SEASON_ID");
//        sql.append("      , WTEPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
//        sql.append("      , WTEPS.CARD_FG AS CARD_FG");
//        sql.append("      , WTEPS.HANBAI_TANI AS HANBAI_TANI");
//        sql.append("      , WTEPS.KEIRYOKI_NM AS KEIRYOKI_NM ");
//        //2016/9/12 VINX h.sakamoto #1921対応 (S)
//        sql.append("      , WTEPS.PLU_HANEI_TIME AS PLU_HANEI_TIME");
//        sql.append("      , WTEPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
//        sql.append("      , WTEPS.LABEL_SEIBUN AS LABEL_SEIBUN");
//        sql.append("      , WTEPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
//        sql.append("      , WTEPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA ");
//        //2016/9/12 VINX h.sakamoto #1921対応 (E)
//        // #2839 MSTB910 2016.11.24 S.Takayama (S)
//        sql.append("      , TRET.GYOTAI_KB AS GYOTAI_KB ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (E)
//	    // 2016/12/12 VINX t.han #3049対応（S)
//		sql.append("	  , WTEPS.LABEL_COUNTRY_NA ");
//	    // 2016/12/12 VINX t.han #3049対応（E)
//	    // 2017/02/09 T.Han #3765対応（S)
//		sql.append("	  , WTEPS.HANBAI_TANI_EN ");
//	    // 2017/02/09 T.Han #3765対応（E)
//        sql.append("    FROM");
//        sql.append("      WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
//        sql.append("      INNER JOIN WK_TEC_EMG_PLU_SYOHIN WTEPS ");
//        sql.append("        ON WGEA.SYOHIN_CD = WTEPS.SYOHIN_CD ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (S)
//        sql.append("      INNER JOIN TMP_R_EMG_TENPO TRET ");
//        sql.append("        ON WGEA.TENPO_CD = TRET.TENPO_CD ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (E)
//        // #3232 MSTB910 2016.12.16 S.Takayama (S)
//		sql.append(" LEFT JOIN (SELECT ");
//		sql.append("		KOSEI_SYOHIN_CD");
//		sql.append("		,TENPO_CD");
//		sql.append("		,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
//		sql.append("		 FROM WK_HAMPER_KOSEI");
//		sql.append("		WHERE HANBAI_END_DT != '" + minusBatchDt + "'");
//		sql.append("		GROUP BY KOSEI_SYOHIN_CD,TENPO_CD)SUB");
//		sql.append(" ON WGEA.SYOHIN_CD = SUB.KOSEI_SYOHIN_CD");
//		sql.append(" AND TRET.TENPO_CD = SUB.TENPO_CD");
//		// #3232 MSTB910 2016.12.16 S.Takayama (E)
//        sql.append("    WHERE");
//        sql.append("      NOT EXISTS ( ");
//        sql.append("        SELECT");
//        sql.append("          * ");
//        sql.append("        FROM");
//        sql.append("         WK_TEC_EMG_PLU_REIGAI WTEPR ");
//        sql.append("        WHERE WGEA.SYOHIN_CD = WTEPR.SYOHIN_CD ");
//        sql.append("            AND WGEA.TENPO_CD = WTEPR.TENPO_CD");
//        sql.append("      ) ");
//        sql.append("      AND NOT EXISTS ( ");
//        sql.append("        SELECT");
//        sql.append("          * ");
//        sql.append("        FROM");
//        sql.append("          BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
//        sql.append("        WHERE");
//        sql.append("          WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
//        sql.append("          AND WGEA.TENPO_CD = BGEA.TENPO_CD");
//        sql.append("      ) ");
//        sql.append("    UNION ALL ");
// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("    SELECT");
        sql.append("      WTEPS.BUNRUI1_CD AS BUNRUI1_CD");
        sql.append("      , WGEA.SYOHIN_CD AS SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (S)
        sql.append("      , WTEPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (E)
        sql.append("      , WGEA.TENPO_CD AS TENPO_CD");
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//        sql.append("      , WTEPR.GENTANKA_VL AS GENTANKA_VL");
//        sql.append("      , WTEPR.BAITANKA_VL AS BAITANKA_VL");
//        sql.append("      , WTEPR.SIIRESAKI_CD AS SIIRESAKI_CD");
//        sql.append("      , WTEPR.PLU_SEND_DT AS PLU_SEND_DT");
        sql.append("      , NVL(WTEPR.GENTANKA_VL, WTEPS.GENTANKA_VL) AS GENTANKA_VL ");
        sql.append("      , NVL(WTEPR.BAITANKA_VL, WTEPS.BAITANKA_VL) AS BAITANKA_VL ");
        sql.append("      , NVL(WTEPR.SIIRESAKI_CD, WTEPS.SIIRESAKI_CD) AS SIIRESAKI_CD ");
        sql.append("      , NVL(WTEPR.PLU_SEND_DT, WTEPS.PLU_SEND_DT) AS PLU_SEND_DT ");
// #3686 MSTB910 2017.02.14 S.Takayama (E)
        //#3232 MSTB910 2016.12.16 S.Takayama (S)
        //sql.append("      , WTEPR.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("      , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0'");
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//        sql.append("        ELSE WTEPR.BAIKA_HAISHIN_FG");
        //sql.append("        ELSE NVL(WTEPR.BAIKA_HAISHIN_FG, WTEPS.BAIKA_HAISHIN_FG) ");
// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("      , CASE ");
        sql.append("        WHEN WTEPS.HAMPER_SYOHIN_FG = '1' AND SUB.HAMPER_SYOHIN_CD IS NULL  THEN '1' ");
        sql.append("        WHEN WTEPS.HAMPER_SYOHIN_FG = '1' AND SUB.BAIKA_HAISHIN_FG = '1'  THEN '1' ");
        sql.append("        ELSE WTEPR.BAIKA_HAISHIN_FG ");
        // 2017/04/28 T.Yajima #4845対応（E)
        sql.append("        END AS BAIKA_HAISHIN_FG");
        //#3232 MSTB910 2016.12.16 S.Takayama (E)
        sql.append("      , WTEPS.BUNRUI5_CD AS BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("      , WTEPS.HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("      , WTEPS.MAX_BUY_QT AS MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("      , WTEPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
        sql.append("      , WTEPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
        sql.append("      , WTEPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
        sql.append("      , WTEPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
        sql.append("      , WTEPS.ZEI_KB AS ZEI_KB");
        sql.append("      , WTEPS.BUNRUI2_CD AS BUNRUI2_CD");
        sql.append("      , WTEPS.TEIKAN_FG AS TEIKAN_FG");
        sql.append("      , WTEPS.ZEI_RT AS ZEI_RT");
        sql.append("      , WTEPS.BUNRUI5_CD AS SEASON_ID");
        sql.append("      , WTEPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
        sql.append("      , WTEPS.CARD_FG AS CARD_FG");
        sql.append("      , WTEPS.HANBAI_TANI AS HANBAI_TANI");
        sql.append("      , WTEPS.KEIRYOKI_NM AS KEIRYOKI_NM ");
        //2016/9/12 VINX h.sakamoto #1921対応 (S)
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//        sql.append("      , WTEPR.PLU_HANEI_TIME AS PLU_HANEI_TIME");
        sql.append("      , NVL(WTEPR.PLU_HANEI_TIME, WTEPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME");
// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("      , WTEPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("      , WTEPS.LABEL_SEIBUN AS LABEL_SEIBUN");
        sql.append("      , WTEPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
        sql.append("      , WTEPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA ");
        //2016/9/12 VINX h.sakamoto #1921対応 (E)
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("      , TRET.GYOTAI_KB AS GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("	  , WTEPS.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	  , WTEPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB910 2017.02.14 S.Takayama (S)
//		sql.append("	  , GREATEST（WTEPR.DELETE_FG，WTEPS.DELETE_FG）AS DELETE_FG ");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("	  , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0' ELSE GREATEST（WTEPR.DELETE_FG，WTEPS.DELETE_FG）END AS DELETE_FG ");
        sql.append("      , GREATEST(WTEPS.DELETE_FG，WTEPR.DELETE_FG) AS DELETE_FG ");
        // 2017/04/28 T.Yajima #4845対応（E)
		// #3686 MSTB910 2017.02.14 S.Takayama (E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
       sql.append("    FROM");
        sql.append("      WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
        sql.append("      INNER JOIN WK_TEC_EMG_PLU_SYOHIN WTEPS ");
        sql.append("        ON WGEA.SYOHIN_CD = WTEPS.SYOHIN_CD ");
        sql.append("      INNER JOIN WK_TEC_EMG_PLU_REIGAI WTEPR ");
        sql.append("        ON WGEA.SYOHIN_CD = WTEPR.SYOHIN_CD ");
        sql.append("        AND WGEA.TENPO_CD = WTEPR.TENPO_CD ");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("      INNER JOIN TMP_R_EMG_TENPO TRET ");
        sql.append("        ON WGEA.TENPO_CD = TRET.TENPO_CD ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
		// #3232 MSTB910 2016.12.16 S.Takayama (S)
		sql.append(" LEFT JOIN (SELECT ");
		// 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		KOSEI_SYOHIN_CD");
        sql.append("        HAMPER_SYOHIN_CD");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append("		,TENPO_CD");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
        sql.append("        ,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append("		 FROM WK_HAMPER_KOSEI");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		WHERE HANBAI_END_DT != '" + minusBatchDt + "'");
        // 2017/04/28 T.Yajima #4845対応（E)
		//sql.append("		GROUP BY KOSEI_SYOHIN_CD,TENPO_CD)SUB");
        sql.append("        GROUP BY HAMPER_SYOHIN_CD,TENPO_CD)SUB");
        //sql.append(" ON WGEA.SYOHIN_CD = SUB.KOSEI_SYOHIN_CD");
		sql.append(" ON WGEA.SYOHIN_CD = SUB.HAMPER_SYOHIN_CD");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append(" AND TRET.TENPO_CD = SUB.TENPO_CD");
		// #3232 MSTB910 2016.12.16 S.Takayama (E)
        sql.append("    WHERE");
        sql.append("      NOT EXISTS ( ");
        sql.append("        SELECT");
        sql.append("          * ");
        sql.append("        FROM");
        sql.append("          BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
        sql.append("        WHERE");
        sql.append("          WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
        sql.append("          AND WGEA.TENPO_CD = BGEA.TENPO_CD");
        sql.append("      ) ");
// #4417 2017.03.29 Add Li.Sheng 対応（S)
        sql.append("      OR EXISTS ( ");
        sql.append("        SELECT");
        sql.append("          * ");
        sql.append("        FROM");
        sql.append("          WK_TEC_EMG_PLU WTEP ");
        sql.append("        WHERE");
        sql.append("          WGEA.SYOHIN_CD = WTEP.SYOHIN_CD ");
        sql.append("          AND WGEA.TENPO_CD = WTEP.TENPO_CD");
        sql.append("      ) ");
// #4417 2017.03.29 Add Li.Sheng 対応（E)
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//        sql.append("    UNION ALL ");
//        sql.append("    SELECT");
//        sql.append("      WTEPS.BUNRUI1_CD AS BUNRUI1_CD");
//        sql.append("      , BGEA.SYOHIN_CD AS SYOHIN_CD");
//        //#3571 MSTB910 2017.01.23 ML.Son (S)
//        sql.append("      , WTEPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
//        //#3571 MSTB910 2017.01.23 ML.Son (E)
//        sql.append("      , BGEA.TENPO_CD AS TENPO_CD");
//        sql.append("      , WTEPS.GENTANKA_VL AS GENTANKA_VL");
//        sql.append("      , WTEPS.BAITANKA_VL AS BAITANKA_VL");
//        sql.append("      , WTEPS.SIIRESAKI_CD AS SIIRESAKI_CD");
//        sql.append("      , WTEPS.PLU_SEND_DT AS PLU_SEND_DT");
//        //#3232 MSTB910 2016.12.16 S.Takayama (S)
//        //sql.append("      , WTEPS.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG");
//        sql.append("      , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0'");
//        sql.append("        ELSE WTEPS.BAIKA_HAISHIN_FG");
//        sql.append("        END AS BAIKA_HAISHIN_FG");
//        //#3232 MSTB910 2016.12.16 S.Takayama (E)
//        sql.append("      , WTEPS.BUNRUI5_CD AS BUNRUI5_CD");
//        sql.append("      , WTEPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
//        sql.append("      , WTEPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
//        sql.append("      , WTEPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
//        sql.append("      , WTEPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
//        sql.append("      , WTEPS.ZEI_KB AS ZEI_KB");
//        sql.append("      , WTEPS.BUNRUI2_CD AS BUNRUI2_CD");
//        sql.append("      , WTEPS.TEIKAN_FG AS TEIKAN_FG");
//        sql.append("      , WTEPS.ZEI_RT AS ZEI_RT");
//        sql.append("      , '' AS SEASON_ID");
//        sql.append("      , WTEPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
//        sql.append("      , WTEPS.CARD_FG AS CARD_FG");
//        sql.append("      , WTEPS.HANBAI_TANI AS HANBAI_TANI");
//        sql.append("      , WTEPS.KEIRYOKI_NM AS KEIRYOKI_NM ");
//        //2016/9/12 VINX h.sakamoto #1921対応 (S)
//        sql.append("      , WTEPS.PLU_HANEI_TIME AS PLU_HANEI_TIME");
//        sql.append("      , WTEPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
//        sql.append("      , WTEPS.LABEL_SEIBUN AS LABEL_SEIBUN");
//        sql.append("      , WTEPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
//        sql.append("      , WTEPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA ");
//        //2016/9/12 VINX h.sakamoto #1921対応 (E)
//        // #2839 MSTB910 2016.11.24 S.Takayama (S)
//        sql.append("      , TRET.GYOTAI_KB AS GYOTAI_KB ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (E)
//	    // 2016/12/12 VINX t.han #3049対応（S)
//		sql.append("	  , WTEPS.LABEL_COUNTRY_NA ");
//	    // 2016/12/12 VINX t.han #3049対応（E)
//	    // 2017/02/09 T.Han #3765対応（S)
//		sql.append("	  , WTEPS.HANBAI_TANI_EN ");
//	    // 2017/02/09 T.Han #3765対応（E)
//        sql.append("    FROM");
//        sql.append("      BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
//        sql.append("      INNER JOIN WK_TEC_EMG_PLU_SYOHIN WTEPS ");
//        sql.append("        ON BGEA.SYOHIN_CD = WTEPS.SYOHIN_CD ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (S)
//        sql.append("      INNER JOIN TMP_R_EMG_TENPO TRET ");
//        sql.append("        ON BGEA.TENPO_CD = TRET.TENPO_CD ");
//        // #2839 MSTB910 2016.11.24 S.Takayama (E)
//		// #3232 MSTB910 2016.12.16 S.Takayama (S)
//		sql.append(" LEFT JOIN (SELECT ");
//		sql.append("		KOSEI_SYOHIN_CD");
//		sql.append("		,TENPO_CD");
//		sql.append("		,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
//		sql.append("		 FROM WK_HAMPER_KOSEI");
//		sql.append("		WHERE HANBAI_END_DT != '" + minusBatchDt + "'");
//		sql.append("		GROUP BY KOSEI_SYOHIN_CD,TENPO_CD)SUB");
//		sql.append(" ON BGEA.SYOHIN_CD = SUB.KOSEI_SYOHIN_CD");
//		sql.append(" AND TRET.TENPO_CD = SUB.TENPO_CD");
//		// #3232 MSTB910 2016.12.16 S.Takayama (E)
//        sql.append("    WHERE");
//        sql.append("      NOT EXISTS ( ");
//        sql.append("        SELECT");
//        sql.append("          * ");
//        sql.append("        FROM");
//        sql.append("          WK_TEC_EMG_PLU_REIGAI WTEPR ");
//        sql.append("        WHERE");
//        sql.append("          BGEA.SYOHIN_CD = WTEPR.SYOHIN_CD ");
//        sql.append("          AND BGEA.TENPO_CD = WTEPR.TENPO_CD");
//        sql.append("      ) ");
//        sql.append("      AND NOT EXISTS ( ");
//        sql.append("        SELECT");
//        sql.append("          * ");
//        sql.append("        FROM");
//        sql.append("          WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
//        sql.append("        WHERE");
//        sql.append("          WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
//        sql.append("          AND WGEA.TENPO_CD = BGEA.TENPO_CD");
//        sql.append("      ) ");
// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("    UNION ALL ");
        sql.append("    SELECT");
        sql.append("      WTEPS.BUNRUI1_CD AS BUNRUI1_CD");
        sql.append("      , BGEA.SYOHIN_CD AS SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (S)
        sql.append("      , WTEPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (E)
        sql.append("      , BGEA.TENPO_CD AS TENPO_CD");
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//      sql.append("      , WTEPR.GENTANKA_VL AS GENTANKA_VL");
//      sql.append("      , WTEPR.BAITANKA_VL AS BAITANKA_VL");
//      sql.append("      , WTEPR.SIIRESAKI_CD AS SIIRESAKI_CD");
//      sql.append("      , WTEPR.PLU_SEND_DT AS PLU_SEND_DT");
      sql.append("      , NVL(WTEPR.GENTANKA_VL, WTEPS.GENTANKA_VL) AS GENTANKA_VL ");
      sql.append("      , NVL(WTEPR.BAITANKA_VL, WTEPS.BAITANKA_VL) AS BAITANKA_VL ");
      sql.append("      , NVL(WTEPR.SIIRESAKI_CD, WTEPS.SIIRESAKI_CD) AS SIIRESAKI_CD ");
      sql.append("      , NVL(WTEPR.PLU_SEND_DT, WTEPS.PLU_SEND_DT) AS PLU_SEND_DT ");
//#3686 MSTB910 2017.02.14 S.Takayama (E)
        //#3232 MSTB910 2016.12.16 S.Takayama (S)
        //sql.append("      , WTEPR.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("      , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0'");
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//      sql.append("        ELSE WTEPR.BAIKA_HAISHIN_FG");
      //sql.append("        ELSE NVL(WTEPR.BAIKA_HAISHIN_FG, WTEPS.BAIKA_HAISHIN_FG) ");
//#3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("      , CASE ");
        sql.append("        WHEN WTEPS.HAMPER_SYOHIN_FG = '1' AND SUB.HAMPER_SYOHIN_CD IS NULL  THEN '1' ");
        sql.append("        WHEN WTEPS.HAMPER_SYOHIN_FG = '1' AND SUB.BAIKA_HAISHIN_FG = '1'  THEN '1' ");
        sql.append("        ELSE WTEPR.BAIKA_HAISHIN_FG ");
        // 2017/04/28 T.Yajima #4845対応（E)
        sql.append("        END AS BAIKA_HAISHIN_FG");
        //#3232 MSTB910 2016.12.16 S.Takayama (E)
        sql.append("      , WTEPS.BUNRUI5_CD AS BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("      , WTEPS.HINMEI_KANJI_NA AS HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("      , WTEPS.MAX_BUY_QT AS MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("      , WTEPS.REC_HINMEI_KANJI_NA AS REC_HINMEI_KANJI_NA");
        sql.append("      , WTEPS.REC_HINMEI_KANA_NA AS REC_HINMEI_KANA_NA");
        sql.append("      , WTEPS.KIKAKU_KANJI_NA AS KIKAKU_KANJI_NA");
        sql.append("      , WTEPS.MAKER_KIBO_KAKAKU_VL AS MAKER_KIBO_KAKAKU_VL");
        sql.append("      , WTEPS.ZEI_KB AS ZEI_KB");
        sql.append("      , WTEPS.BUNRUI2_CD AS BUNRUI2_CD");
        sql.append("      , WTEPS.TEIKAN_FG AS TEIKAN_FG");
        sql.append("      , WTEPS.ZEI_RT AS ZEI_RT");
        sql.append("      , '' AS SEASON_ID");
        sql.append("      , WTEPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
        sql.append("      , WTEPS.CARD_FG AS CARD_FG");
        sql.append("      , WTEPS.HANBAI_TANI AS HANBAI_TANI");
        sql.append("      , WTEPS.KEIRYOKI_NM AS KEIRYOKI_NM ");
        //2016/9/12 VINX h.sakamoto #1921対応 (S)
// #3686 MSTB910 2017.02.14 S.Takayama (S)
//      sql.append("      , WTEPR.PLU_HANEI_TIME AS PLU_HANEI_TIME");
      sql.append("      , NVL(WTEPR.PLU_HANEI_TIME, WTEPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME");
//#3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("      , WTEPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("      , WTEPS.LABEL_SEIBUN AS LABEL_SEIBUN");
        sql.append("      , WTEPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
        sql.append("      , WTEPS.LABEL_TUKAIKATA AS LABEL_TUKAIKATA ");
        //2016/9/12 VINX h.sakamoto #1921対応 (E)
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("      , TRET.GYOTAI_KB AS GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("	  , WTEPS.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	  , WTEPS.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（S)
		// #3686 MSTB910 2017.02.14 S.Takayama (S)
//		sql.append("	  , GREATEST（WTEPR.DELETE_FG，WTEPS.DELETE_FG）AS DELETE_FG ");
		// #3686 MSTB910 2017.02.14 S.Takayama (E)
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("	  , CASE WHEN SUB.BAIKA_HAISHIN_FG = '0' THEN '0' ELSE GREATEST（WTEPR.DELETE_FG，WTEPS.DELETE_FG）END AS DELETE_FG ");
        sql.append("      , GREATEST(WTEPS.DELETE_FG，WTEPR.DELETE_FG) AS DELETE_FG ");
        // 2017/04/28 T.Yajima #4845対応（E)
// #4418 2017.03.28 Mod Li.Sheng 対応（E)
        sql.append("    FROM");
        sql.append("      BK_GROUPBAIHEN_EXCLUDE_ASN BGEA ");
        sql.append("      INNER JOIN WK_TEC_EMG_PLU_SYOHIN WTEPS ");
        sql.append("        ON BGEA.SYOHIN_CD = WTEPS.SYOHIN_CD ");
        sql.append("      INNER JOIN WK_TEC_EMG_PLU_REIGAI WTEPR ");
        sql.append("        ON BGEA.SYOHIN_CD = WTEPR.SYOHIN_CD ");
        sql.append("        AND BGEA.TENPO_CD = WTEPR.TENPO_CD ");
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("      INNER JOIN TMP_R_EMG_TENPO TRET ");
        sql.append("        ON BGEA.TENPO_CD = TRET.TENPO_CD ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
		// #3232 MSTB910 2016.12.16 S.Takayama (S)
		sql.append(" LEFT JOIN (SELECT ");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		KOSEI_SYOHIN_CD");
        sql.append("        HAMPER_SYOHIN_CD");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append("		,TENPO_CD");
        // 2017/04/28 T.Yajima #4845対応（S)
        //sql.append("        ,MIN(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
		sql.append("		,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append("		 FROM WK_HAMPER_KOSEI");
        // 2017/04/28 T.Yajima #4845対応（S)
		//sql.append("		WHERE HANBAI_END_DT != '" + minusBatchDt + "'");
        //sql.append("        GROUP BY KOSEI_SYOHIN_CD,TENPO_CD)SUB");
        //sql.append(" ON BGEA.SYOHIN_CD = SUB.KOSEI_SYOHIN_CD");
		sql.append("		GROUP BY HAMPER_SYOHIN_CD,TENPO_CD)SUB");
		sql.append(" ON BGEA.SYOHIN_CD = SUB.HAMPER_SYOHIN_CD");
        // 2017/04/28 T.Yajima #4845対応（E)
		sql.append(" AND TRET.TENPO_CD = SUB.TENPO_CD");
		// #3232 MSTB910 2016.12.16 S.Takayama (E)
        sql.append("    WHERE");
        sql.append("      NOT EXISTS ( ");
        sql.append("        SELECT");
        sql.append("          * ");
        sql.append("        FROM");
        sql.append("          WK_GROUPBAIHEN_EXCLUDE_ASN WGEA ");
        sql.append("        WHERE");
        sql.append("          WGEA.SYOHIN_CD = BGEA.SYOHIN_CD ");
        sql.append("          AND WGEA.TENPO_CD = BGEA.TENPO_CD");
        sql.append("      )");
        sql.append("  ) ABCD ");
        sql.append("    ON ( ");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append("      ABCD.BUNRUI1_CD = WK_TEC_EMG_PLU.BUNRUI1_CD ");
//        sql.append("      AND ABCD.SYOHIN_CD = WK_TEC_EMG_PLU.SYOHIN_CD ");
        sql.append("       ABCD.SYOHIN_CD = WK_TEC_EMG_PLU.SYOHIN_CD ");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append("      AND ABCD.TENPO_CD = WK_TEC_EMG_PLU.TENPO_CD");
        sql.append("    ) WHEN MATCHED THEN UPDATE ");
        sql.append("SET");
        sql.append("  GENTANKA_VL = ABCD.GENTANKA_VL");
        sql.append("  , BAITANKA_VL = ABCD.BAITANKA_VL");
        sql.append("  , SIIRESAKI_CD = ABCD.SIIRESAKI_CD");
        sql.append("  , PLU_SEND_DT = ABCD.PLU_SEND_DT");
        sql.append("  , BAIKA_HAISHIN_FG = ABCD.BAIKA_HAISHIN_FG");
        sql.append("  , BUNRUI5_CD = ABCD.BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("  , HINMEI_KANJI_NA = ABCD.HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("  , MAX_BUY_QT = ABCD.MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("  , REC_HINMEI_KANJI_NA = ABCD.REC_HINMEI_KANJI_NA");
        sql.append("  , REC_HINMEI_KANA_NA = ABCD.REC_HINMEI_KANA_NA");
        sql.append("  , KIKAKU_KANJI_NA = ABCD.KIKAKU_KANJI_NA");
        sql.append("  , MAKER_KIBO_KAKAKU_VL = ABCD.MAKER_KIBO_KAKAKU_VL");
        sql.append("  , ZEI_KB = ABCD.ZEI_KB");
        sql.append("  , ERR_KB = '"+CONST_DOUBLE_ZERO+"'");
        sql.append("  , BUNRUI2_CD = ABCD.BUNRUI2_CD");
        sql.append("  , TEIKAN_FG = ABCD.TEIKAN_FG");
        sql.append("  , ZEI_RT = ABCD.ZEI_RT");
        sql.append("  , SEASON_ID = ABCD.SEASON_ID");
        sql.append("  , SYOHI_KIGEN_DT = ABCD.SYOHI_KIGEN_DT");
        sql.append("  , CARD_FG = ABCD.CARD_FG");
        sql.append("  , INSERT_USER_ID =  '" + jobId + "'");
        sql.append("  , INSERT_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("  , UPDATE_USER_ID =  '" + jobId + "'");
        sql.append("  , UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("  , HANBAI_TANI = ABCD.HANBAI_TANI");
        sql.append("  , KEIRYOKI_NM = ABCD.KEIRYOKI_NM ");
        //2016/9/12 VINX h.sakamoto #1921対応 (S)
        sql.append("  , PLU_HANEI_TIME = ABCD.PLU_HANEI_TIME");
        sql.append("  , SYOHI_KIGEN_HYOJI_PATTER = ABCD.SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("  , LABEL_SEIBUN = ABCD.LABEL_SEIBUN");
        sql.append("  , LABEL_HOKAN_HOHO = ABCD.LABEL_HOKAN_HOHO");
        sql.append("  , LABEL_TUKAIKATA = ABCD.LABEL_TUKAIKATA ");
        //2016/9/12 VINX h.sakamoto #1921対応 (E)
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("  , GYOTAI_KB = ABCD.GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("  , LABEL_COUNTRY_NA = ABCD.LABEL_COUNTRY_NA ");
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , HANBAI_TANI_EN = ABCD.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
        //#3571 MSTB910 2017.01.23 ML.Son (S)
        sql.append("  , OLD_SYOHIN_CD = ABCD.OLD_SYOHIN_CD ");
        //#3571 MSTB910 2017.01.23 ML.Son (E)
	    // 2016/12/12 VINX t.han #3049対応（E)
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        sql.append("  , DELETE_FG = ABCD.DELETE_FG ");
     	// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append("WHEN NOT MATCHED THEN ");
        sql.append("INSERT ( ");
        sql.append("  BUNRUI1_CD");
        sql.append("  , SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (S)
        sql.append("  , OLD_SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (E)
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
        sql.append("  , BUNRUI2_CD");
        sql.append("  , TEIKAN_FG");
        sql.append("  , ZEI_RT");
        sql.append("  , SEASON_ID");
        sql.append("  , SYOHI_KIGEN_DT");
        sql.append("  , CARD_FG");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append("  , HANBAI_TANI");
        sql.append("  , KEIRYOKI_NM");
        //2016/9/12 VINX h.sakamoto #1921対応 (S)
        sql.append("  , PLU_HANEI_TIME");
        sql.append("  , SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("  , LABEL_SEIBUN");
        sql.append("  , LABEL_HOKAN_HOHO");
        sql.append("  , LABEL_TUKAIKATA");
        //2016/9/12 VINX h.sakamoto #1921対応 (E)
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("  , GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("  , LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        sql.append("  , DELETE_FG ");
     	// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append(") ");
        sql.append("VALUES ( ");
        sql.append("  ABCD.BUNRUI1_CD");
        sql.append("  , ABCD.SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (S)
        sql.append("  , ABCD.OLD_SYOHIN_CD");
        //#3571 MSTB910 2017.01.23 ML.Son (E)
        sql.append("  , ABCD.TENPO_CD");
        sql.append("  , ABCD.GENTANKA_VL");
        sql.append("  , ABCD.BAITANKA_VL");
        sql.append("  , ABCD.SIIRESAKI_CD");
        sql.append("  , ABCD.PLU_SEND_DT");
        sql.append("  , ABCD.BAIKA_HAISHIN_FG");
        sql.append("  , ABCD.BUNRUI5_CD");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (S)
        sql.append("  , ABCD.HINMEI_KANJI_NA");
        // #6167 MSTB910 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("  , ABCD.MAX_BUY_QT");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("  , ABCD.REC_HINMEI_KANJI_NA");
        sql.append("  , ABCD.REC_HINMEI_KANA_NA");
        sql.append("  , ABCD.KIKAKU_KANJI_NA");
        sql.append("  , ABCD.MAKER_KIBO_KAKAKU_VL");
        sql.append("  , ABCD.ZEI_KB");
        sql.append("  , '"+CONST_DOUBLE_ZERO+"'");
        sql.append("  , ABCD.BUNRUI2_CD");
        sql.append("  , ABCD.TEIKAN_FG");
        sql.append("  , ABCD.ZEI_RT");
        sql.append("  , ABCD.SEASON_ID");
        sql.append("  , ABCD.SYOHI_KIGEN_DT");
        sql.append("  , ABCD.CARD_FG");
        sql.append("    , '" + jobId + "'");
        sql.append("    , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("    , '" + jobId + "'");
        sql.append("    , '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
        sql.append("  , ABCD.HANBAI_TANI");
        sql.append("  , ABCD.KEIRYOKI_NM");
        //2016/9/12 VINX h.sakamoto #1921対応 (S)
        sql.append("  , ABCD.PLU_HANEI_TIME");
        sql.append("  , ABCD.SYOHI_KIGEN_HYOJI_PATTER");
        sql.append("  , ABCD.LABEL_SEIBUN");
        sql.append("  , ABCD.LABEL_HOKAN_HOHO");
        sql.append("  , ABCD.LABEL_TUKAIKATA");
        //2016/9/12 VINX h.sakamoto #1921対応 (E)
        // #2839 MSTB910 2016.11.24 S.Takayama (S)
        sql.append("  , ABCD.GYOTAI_KB ");
        // #2839 MSTB910 2016.11.24 S.Takayama (E)
	    // 2016/12/12 VINX t.han #3049対応（S)
		sql.append("  , ABCD.LABEL_COUNTRY_NA ");
	    // 2016/12/12 VINX t.han #3049対応（E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("  , ABCD.HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
        // #3686 MSTB910 2017.02.14 S.Takayama (S)
        sql.append("	  , ABCD.DELETE_FG ");
     	// #3686 MSTB910 2017.02.14 S.Takayama (E)
        sql.append(") ");
        return sql.toString();
    }

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
