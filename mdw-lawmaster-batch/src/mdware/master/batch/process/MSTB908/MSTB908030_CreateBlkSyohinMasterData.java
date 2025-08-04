package mdware.master.batch.process.MSTB908;

import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import org.apache.log4j.Level;
/**
*
* <p>タイトル: MSTB908030_CreateBlkSyohinMasterData.java クラス</p>
* <p>説明　: TMP商品マスタからBlynkに連携する商品マスタデータを作成する</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2015.08.07) TAM.NM FIVImart様対応
* @version 1.01 (2015.10.14) DAI.BQ FIVImart様対応
* @version 1.02 (2016.01.22) TAM.NM FIVImart様対応
* @version 1.03 (2016.01.25) TU.TD FIVImart様対応
* @Version 1.04 (2016.02.17) TU.TD 設計書No.577(#1155) FIVImart対応
* @Version 1.05 (2016.03.14) Huy.NT 設計書No.619(#1155) FIVImart対応
* @Version 1.06 (2016.05.18) to #1325,1328対応
* @version 1.07 (2016.10.18) Li.Sheng #2238
*/
public class MSTB908030_CreateBlkSyohinMasterData {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();
    private static final String SYORI_KBCR = "A";
    private static final String SYORI_KBDEL = "D";
    // バッチ日付
    private String batchDt = null;
    private String yokuBatchDt = null;
    // テーブル
    private static final String TABLE_IF = "IF_BLK_SYOHIN"; // IF_BLK商品マスタ
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    private String jobId =userLog.getJobId();

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB908030_CreateBlkSyohinMasterData(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB908030_CreateBlkSyohinMasterData() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }
    public void execute() throws Exception{
        try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
            //バッチ処理件数をカウント（ログ出力用）
            int iRec = 0;
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し
            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");
            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();
            writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);

            yokuBatchDt = DateChanger.addDate(batchDt, 1);

            // IF_BLK商品マスタTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF);
            writeLog(Level.INFO_INT, "IF_BLK商品マスタを削除処理を終了します。");

            // IF_BLK商品マスタ登録(新規・更新)
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ登録(新規・更新)処理を開始します。");
            iRec = db.executeUpdate(getIfBlkSyohinInsUpdInsertSql());
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ(新規・更新)を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ登録(新規・更新)処理を終了します。");
            db.commit();

            // IF_BLK商品マスタ登録(削除)
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ登録(削除)処理を開始します。");
            iRec = db.executeUpdate(getIfBlkSyohinDelInsertSql());
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ(削除)を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLK商品マスタ登録(削除)処理を終了します。");

            db.commit();
            writeLog(Level.INFO_INT, "処理を終了します。");

        //SQLエラーが発生した場合の処理
        }catch (SQLException se) {
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
     * IF_BLK_SYOHINを登録するSQLを取得する
     *
     * @return IF_BLK_SYOHIN登録SQL
     */
    private String getIfBlkSyohinInsUpdInsertSql() throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_SYOHIN ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT");
        sql.append("	 ,SYORI_KB");
        sql.append("	 ,SYOHIN_CD");
        sql.append("	 ,SYOHIN_NA");
        sql.append("	 ,DPT_CD");
        sql.append("	 ,LINE_CD");
        sql.append("	 ,CLASS_CD");
        sql.append("	 ,SUPPLIER_CD");
        sql.append("	 ,SUPPLIER_NA");
        sql.append("	 ,INSERT_TS");
        sql.append("	 ,INSERT_USER_ID");
        sql.append("	 ,UPDATE_TS");
        sql.append("	 ,UPDATE_USER_ID");
        sql.append("	 ,TEIKAN_FG");
        sql.append("	) ");
        sql.append(" SELECT ");
        // #2238 Mod 2016.10.18 Li.Sheng (S)
        //sql.append("	 '" + batchDt + "' ");
        sql.append("	 '" + yokuBatchDt + "' ");
        // #2238 Mod 2016.10.18 Li.Sheng (E)
        sql.append("	 ,'" + SYORI_KBCR + "'  ");
        sql.append("	 ,TRS.SYOHIN_CD ");
        sql.append("	 ,TRS.HINMEI_KANJI_NA ");
        sql.append("	 ,TRIM(TRS.BUNRUI1_CD) ");
        sql.append("	 ,TRIM(TRS.BUNRUI2_CD) ");
        sql.append("	 ,TRIM(TRS.BUNRUI5_CD) ");
        sql.append("	 ,TRIM(TRS.SIIRESAKI_CD) ");
        sql.append("	 ,TRT2.TORIHIKISAKI_KANJI_NA ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	 ,NVL(ASN.HANBAI_HOHO_KB,0) ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" FROM ");
        sql.append("	TMP_R_SYOHIN TRS ");
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("		SELECT ");
        //Del 2015.10.14 Dai.BQ (S)
        //sql.append("	 		TRSI.BUNRUI1_CD, ");
        //Del 2015.10.14 Dai.BQ (E)
        sql.append("	 		TRSI.SYOHIN_CD");
        sql.append("	 		,TRSI.YUKO_DT");
        sql.append(" 		FROM ");
        sql.append("			TMP_R_SYOHIN TRSI ");
        sql.append("		INNER JOIN ");
        sql.append("			( ");
        sql.append("			SELECT ");
        sql.append("	 			SYOHIN_CD");
        sql.append("	 			,MAX(YUKO_DT) AS YUKO_DT");
        sql.append(" 			FROM ");
        sql.append("				TMP_R_SYOHIN ");
        sql.append("			WHERE ");
        sql.append("				YUKO_DT <= '" + yokuBatchDt + "' ");
        sql.append("			GROUP BY ");
        sql.append("				SYOHIN_CD");
        sql.append("			) TRSI2 ");
        sql.append("		ON ");
        sql.append("			TRSI.SYOHIN_CD  = TRSI2.SYOHIN_CD AND ");
        sql.append("	 		TRSI.YUKO_DT  = TRSI2.YUKO_DT ");
        sql.append("		) TRSI1 ");
        sql.append("	ON ");
        //Del 2015.10.14 Dai.BQ (S)
        //sql.append("	 	TRS.BUNRUI1_CD  = TRSI1.BUNRUI1_CD AND ");
        //Del 2015.10.14 Dai.BQ (E)
        sql.append("	 	TRS.SYOHIN_CD  = TRSI1.SYOHIN_CD AND ");
        sql.append("	 	TRS.YUKO_DT  = TRSI1.YUKO_DT ");
        sql.append("    LEFT JOIN ");
        sql.append("	 	DT_BLK_SYOHIN DTBS");
        sql.append("	ON ");
        sql.append("	 	TRS.SYOHIN_CD  = DTBS.SYOHIN_CD ");
        sql.append("    LEFT JOIN ");
        sql.append("		( ");
        sql.append(" 		SELECT ");
        sql.append(" 			TRT.TORIHIKISAKI_CD ");
        sql.append(" 			,TRT.TORIHIKISAKI_KANJI_NA ");
        sql.append(" 		FROM ");
        sql.append("			TMP_R_TORIHIKISAKI TRT ");
        sql.append("		INNER JOIN ");
        sql.append("			( ");
        sql.append(" 			SELECT ");
        sql.append(" 				TORIHIKISAKI_CD ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append(" 				,CHOAI_KB ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append(" 				,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append(" 			FROM ");
        sql.append("    			TMP_R_TORIHIKISAKI ");
        sql.append("			WHERE ");
        sql.append("				CHOAI_KB = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' AND ");
        sql.append("				TEKIYO_START_DT <=	'" + yokuBatchDt + "' 	 ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append(" 				AND DELETE_FG = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
        sql.append(" 				AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("			GROUP BY ");
        sql.append("				TORIHIKISAKI_CD ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append("				,CHOAI_KB ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("			) TRT1 ");
        sql.append("		ON ");
        sql.append("	 		TRT.TORIHIKISAKI_CD  = TRT1.TORIHIKISAKI_CD AND ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append("	 		TRT.CHOAI_KB  = TRT1.CHOAI_KB AND ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("	 		TRT.TEKIYO_START_DT  = TRT1.TEKIYO_START_DT ");
        //Add 2015.10.20 DAI.BQ (S)
        sql.append("	 		AND TRT.DELETE_FG  = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
        sql.append("	 		AND TRT.TORIKESHI_FG  = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
        //Add 2015.10.20 DAI.BQ (E)
        sql.append("		) TRT2 ");
        sql.append("	ON ");
        sql.append("	 	TRS.SIIRESAKI_CD  = TRT2.TORIHIKISAKI_CD ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("		LEFT JOIN ( ");
        sql.append("		SELECT ");
        sql.append("			ASN1.SYOHIN_CD ");
        sql.append("			,ASN1.YUKO_DT ");
        sql.append("			,ASN1.HANBAI_HOHO_KB ");
        sql.append("		FROM ");
        sql.append("		TMP_R_SYOHIN_ASN ASN1 ) ASN ");
        sql.append("			ON ");
        sql.append("			TRS.SYOHIN_CD = ASN.SYOHIN_CD ");
        sql.append("			AND ");
        sql.append("			TRS.YUKO_DT = ASN.YUKO_DT ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" WHERE ");
        sql.append("	( ");
        // No.577 MSTB908 Mod 2016.02.17 TU.TD (S)
        //sql.append("	 NVL(TRS.HINMEI_KANJI_NA, 0)  <> NVL(DTBS.SYOHIN_NA, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI1_CD, 0)  <> NVL(DTBS.DPT_CD, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI2_CD, 0)  <> NVL(DTBS.LINE_CD, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI5_CD, 0)  <> NVL(DTBS.CLASS_CD, 0) OR");
        //sql.append("	 NVL(TRS.SIIRESAKI_CD, 0)  <> NVL(DTBS.SUPPLIER_CD, 0) OR");
        //sql.append("	 NVL(TRT2.TORIHIKISAKI_KANJI_NA, 0)  <> NVL(DTBS.SUPPLIER_NA, 0)");
        sql.append("	 TRIM(NVL(TRS.HINMEI_KANJI_NA, 0))  <> TRIM(NVL(DTBS.SYOHIN_NA, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI1_CD, 0))  <> TRIM(NVL(DTBS.DPT_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI2_CD, 0))  <> TRIM(NVL(DTBS.LINE_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI5_CD, 0))  <> TRIM(NVL(DTBS.CLASS_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.SIIRESAKI_CD, 0))  <> TRIM(NVL(DTBS.SUPPLIER_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRT2.TORIHIKISAKI_KANJI_NA, 0))  <> TRIM(NVL(DTBS.SUPPLIER_NA, 0))");
        // No.577 MSTB908 Mod 2016.02.17 TU.TD (E)
        sql.append("	)  ");
        sql.append("	AND  ");
        sql.append("	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()  + "' ");
        //No.544 MSTB908 Add 2016.01.22 TAM.NM (S)
        sql.append("UNION ");
        sql.append(" SELECT ");
        // #2238 Mod 2016.10.18 Li.Sheng (S)
        //sql.append("	 '" + batchDt + "' ");
        sql.append("	 '" + yokuBatchDt + "' ");
        // #2238 Mod 2016.10.18 Li.Sheng (E)
        sql.append("	,'" + SYORI_KBCR + "'  ");
        sql.append(" 	,LPAD(TRS.OLD_SYOHIN_CD,'13','0') ");
        sql.append(" 	,TRS.HINMEI_KANJI_NA ");
        sql.append(" 	,TRIM(TRS.BUNRUI1_CD) ");
        sql.append(" 	,TRIM(TRS.BUNRUI2_CD) ");
        sql.append(" 	,TRIM(TRS.BUNRUI5_CD) ");
        sql.append(" 	,TRIM(TRS.SIIRESAKI_CD) ");
        sql.append("	,TRT2.TORIHIKISAKI_KANJI_NA ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	 ,NVL(ASN.HANBAI_HOHO_KB,0) ");
//        sql.append(" FROM ");
//        sql.append("	TMP_R_SYOHIN TRS ");
//        sql.append("	INNER JOIN ");
//        sql.append("		( ");
//        sql.append("		SELECT ");
//        sql.append("	 		TRSI.SYOHIN_CD");
//        sql.append("	 		,TRSI.YUKO_DT");
//        sql.append(" 		FROM ");
//        sql.append("			TMP_R_SYOHIN TRSI ");
//        sql.append("		INNER JOIN ");
//        sql.append("			( ");
//        sql.append("			SELECT ");
//        sql.append("	 			SYOHIN_CD");
//        sql.append("	 			,MAX(YUKO_DT) AS YUKO_DT");
//        sql.append(" 			FROM ");
//        sql.append("				TMP_R_SYOHIN ");
//        sql.append("			WHERE ");
//        sql.append("				YUKO_DT <= '" + yokuBatchDt + "' ");
//        sql.append("			GROUP BY ");
//        sql.append("				SYOHIN_CD");
//        sql.append("			) TRSI2 ");
//        sql.append("		ON ");
//        sql.append("			TRSI.SYOHIN_CD  = TRSI2.SYOHIN_CD AND ");
//        sql.append("	 		TRSI.YUKO_DT  = TRSI2.YUKO_DT ");
//        sql.append("		) TRSI1 ");
//        sql.append("	ON ");
//        sql.append("	 	TRS.SYOHIN_CD  = TRSI1.SYOHIN_CD AND ");
//        sql.append("	 	TRS.YUKO_DT  = TRSI1.YUKO_DT ");
//        sql.append("    LEFT JOIN ");
//        sql.append("	 	DT_BLK_SYOHIN DTBS");
//        sql.append("	ON ");
//        sql.append("	 	TRS.SYOHIN_CD  = DTBS.SYOHIN_CD ");
//        sql.append("    LEFT JOIN ");
//        sql.append("		( ");
//        sql.append(" 		SELECT ");
//        sql.append(" 			TRT.TORIHIKISAKI_CD ");
//        sql.append(" 			,TRT.TORIHIKISAKI_KANJI_NA ");
//        sql.append(" 		FROM ");
//        sql.append("			TMP_R_TORIHIKISAKI TRT ");
//        sql.append("		INNER JOIN ");
//        sql.append("			( ");
//        sql.append(" 			SELECT ");
//        sql.append(" 				TORIHIKISAKI_CD ");
//        sql.append(" 				,CHOAI_KB ");
//        sql.append(" 				,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
//        sql.append(" 			FROM ");
//        sql.append("    			TMP_R_TORIHIKISAKI ");
//        sql.append("			WHERE ");
//        sql.append("				CHOAI_KB = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' AND ");
//        sql.append("				TEKIYO_START_DT <=	'" + yokuBatchDt + "' 	 ");
//        sql.append(" 				AND DELETE_FG = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
//        sql.append(" 				AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
//        sql.append("			GROUP BY ");
//        sql.append("				TORIHIKISAKI_CD ");
//        sql.append("				,CHOAI_KB ");
//        sql.append("			) TRT1 ");
//        sql.append("		ON ");
//        sql.append("	 		TRT.TORIHIKISAKI_CD  = TRT1.TORIHIKISAKI_CD AND ");
//        sql.append("	 		TRT.CHOAI_KB  = TRT1.CHOAI_KB AND ");
//        sql.append("	 		TRT.TEKIYO_START_DT  = TRT1.TEKIYO_START_DT ");
//        sql.append("	 		AND TRT.DELETE_FG  = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
//        sql.append("	 		AND TRT.TORIKESHI_FG  = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
//        sql.append("		) TRT2 ");
//        sql.append("	ON ");
//        sql.append("	 	TRS.SIIRESAKI_CD  = TRT2.TORIHIKISAKI_CD ");
//        sql.append(" WHERE ");
//        sql.append("	( ");
//        sql.append("	 NVL(TRS.HINMEI_KANJI_NA, 0)  <> NVL(DTBS.SYOHIN_NA, 0) OR");
//        sql.append("	 NVL(TRS.BUNRUI1_CD, 0)  <> NVL(DTBS.DPT_CD, 0) OR");
//        sql.append("	 NVL(TRS.BUNRUI2_CD, 0)  <> NVL(DTBS.LINE_CD, 0) OR");
//        sql.append("	 NVL(TRS.BUNRUI5_CD, 0)  <> NVL(DTBS.CLASS_CD, 0) OR");
//        sql.append("	 NVL(TRS.SIIRESAKI_CD, 0)  <> NVL(DTBS.SUPPLIER_CD, 0) OR");
//        sql.append("	 NVL(TRT2.TORIHIKISAKI_KANJI_NA, 0)  <> NVL(DTBS.SUPPLIER_NA, 0)");
//        sql.append("	)  ");
//        sql.append("	AND  ");
//        sql.append("	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()  + "' ");
        sql.append(" FROM ");
        sql.append("	TMP_R_SYOHIN TRS ");
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("		SELECT ");
        //Del 2015.10.14 Dai.BQ (S)
        //sql.append("	 		TRSI.BUNRUI1_CD, ");
        //Del 2015.10.14 Dai.BQ (E)
        sql.append("	 		TRSI.SYOHIN_CD");
        sql.append("	 		,TRSI.YUKO_DT");
        sql.append(" 		FROM ");
        sql.append("			TMP_R_SYOHIN TRSI ");
        sql.append("		INNER JOIN ");
        sql.append("			( ");
        sql.append("			SELECT ");
        sql.append("	 			SYOHIN_CD");
        sql.append("	 			,MAX(YUKO_DT) AS YUKO_DT");
        sql.append(" 			FROM ");
        sql.append("				TMP_R_SYOHIN ");
        sql.append("			WHERE ");
        sql.append("				YUKO_DT <= '" + yokuBatchDt + "' ");
        sql.append("			GROUP BY ");
        sql.append("				SYOHIN_CD");
        sql.append("			) TRSI2 ");
        sql.append("		ON ");
        sql.append("			TRSI.SYOHIN_CD  = TRSI2.SYOHIN_CD AND ");
        sql.append("	 		TRSI.YUKO_DT  = TRSI2.YUKO_DT ");
        sql.append("		) TRSI1 ");
        sql.append("	ON ");
        //Del 2015.10.14 Dai.BQ (S)
        //sql.append("	 	TRS.BUNRUI1_CD  = TRSI1.BUNRUI1_CD AND ");
        //Del 2015.10.14 Dai.BQ (E)
        sql.append("	 	TRS.SYOHIN_CD  = TRSI1.SYOHIN_CD AND ");
        sql.append("	 	TRS.YUKO_DT  = TRSI1.YUKO_DT ");
        sql.append("    LEFT JOIN ");
        sql.append("	 	DT_BLK_SYOHIN DTBS");
        sql.append("	ON ");
        sql.append("	 	TRS.SYOHIN_CD  = DTBS.SYOHIN_CD ");
        sql.append("    LEFT JOIN ");
        sql.append("		( ");
        sql.append(" 		SELECT ");
        sql.append(" 			TRT.TORIHIKISAKI_CD ");
        sql.append(" 			,TRT.TORIHIKISAKI_KANJI_NA ");
        sql.append(" 		FROM ");
        sql.append("			TMP_R_TORIHIKISAKI TRT ");
        sql.append("		INNER JOIN ");
        sql.append("			( ");
        sql.append(" 			SELECT ");
        sql.append(" 				TORIHIKISAKI_CD ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append(" 				,CHOAI_KB ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append(" 				,MAX(TEKIYO_START_DT) AS TEKIYO_START_DT ");
        sql.append(" 			FROM ");
        sql.append("    			TMP_R_TORIHIKISAKI ");
        sql.append("			WHERE ");
        sql.append("				CHOAI_KB = '" + mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI + "' AND ");
        sql.append("				TEKIYO_START_DT <=	'" + yokuBatchDt + "' 	 ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append(" 				AND DELETE_FG = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
        sql.append(" 				AND TORIKESHI_FG = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("			GROUP BY ");
        sql.append("				TORIHIKISAKI_CD ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append("				,CHOAI_KB ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("			) TRT1 ");
        sql.append("		ON ");
        sql.append("	 		TRT.TORIHIKISAKI_CD  = TRT1.TORIHIKISAKI_CD AND ");
        //Add 2015.10.14 DAI.BQ (S)
        sql.append("	 		TRT.CHOAI_KB  = TRT1.CHOAI_KB AND ");
        //Add 2015.10.14 DAI.BQ (E)
        sql.append("	 		TRT.TEKIYO_START_DT  = TRT1.TEKIYO_START_DT ");
        //Add 2015.10.20 DAI.BQ (S)
        sql.append("	 		AND TRT.DELETE_FG  = '" + mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO + "' ");
        sql.append("	 		AND TRT.TORIKESHI_FG  = '" + mst000101_ConstDictionary.TORIKESHI_FG_NOR + "' ");
        //Add 2015.10.20 DAI.BQ (E)
        sql.append("		) TRT2 ");
        sql.append("	ON ");
        sql.append("	 	TRS.SIIRESAKI_CD  = TRT2.TORIHIKISAKI_CD ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("		LEFT JOIN ( ");
        sql.append("		SELECT ");
        sql.append("			ASN1.SYOHIN_CD ");
        sql.append("			,ASN1.YUKO_DT ");
        sql.append("			,ASN1.HANBAI_HOHO_KB ");
        sql.append("		FROM ");
        sql.append("		TMP_R_SYOHIN_ASN ASN1) ASN ");
        sql.append("			ON ");
        sql.append("			TRS.SYOHIN_CD = ASN.SYOHIN_CD ");
        sql.append("			AND ");
        sql.append("			TRS.YUKO_DT = ASN.YUKO_DT ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" WHERE ");
        sql.append("	( ");
        // No.577 MSTB908 Mod 2016.02.17 TU.TD (S)
        //sql.append("	 NVL(TRS.HINMEI_KANJI_NA, 0)  <> NVL(DTBS.SYOHIN_NA, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI1_CD, 0)  <> NVL(DTBS.DPT_CD, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI2_CD, 0)  <> NVL(DTBS.LINE_CD, 0) OR");
        //sql.append("	 NVL(TRS.BUNRUI5_CD, 0)  <> NVL(DTBS.CLASS_CD, 0) OR");
        //sql.append("	 NVL(TRS.SIIRESAKI_CD, 0)  <> NVL(DTBS.SUPPLIER_CD, 0) OR");
        //sql.append("	 NVL(TRT2.TORIHIKISAKI_KANJI_NA, 0)  <> NVL(DTBS.SUPPLIER_NA, 0)");
        sql.append("	 TRIM(NVL(TRS.HINMEI_KANJI_NA, 0))  <> TRIM(NVL(DTBS.SYOHIN_NA, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI1_CD, 0))  <> TRIM(NVL(DTBS.DPT_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI2_CD, 0))  <> TRIM(NVL(DTBS.LINE_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.BUNRUI5_CD, 0))  <> TRIM(NVL(DTBS.CLASS_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRS.SIIRESAKI_CD, 0))  <> TRIM(NVL(DTBS.SUPPLIER_CD, 0)) OR");
        sql.append("	 TRIM(NVL(TRT2.TORIHIKISAKI_KANJI_NA, 0))  <> TRIM(NVL(DTBS.SUPPLIER_NA, 0))");
        // No.577 MSTB908 Mod 2016.02.17 TU.TD (E)
        sql.append("	)  ");
        sql.append("	AND  ");
        sql.append("	DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()  + "' ");
     // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append("	AND  ");
        sql.append("	TRS.OLD_SYOHIN_CD IS NOT NULL");
        //No.544 MSTB908 Add 2016.01.22 TAM.NM (E)
        
        return sql.toString();
    }
    /**
     * IF_BLK_SYOHINを登録するSQLを取得する
     *
     * @return IF_BLK_SYOHIN登録SQL
     */
    private String getIfBlkSyohinDelInsertSql() throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_SYOHIN ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT");
        sql.append("	 ,SYORI_KB");
        sql.append("	 ,SYOHIN_CD");
        sql.append("	 ,SYOHIN_NA");
        sql.append("	 ,DPT_CD");
        sql.append("	 ,LINE_CD");
        sql.append("	 ,CLASS_CD");
        sql.append("	 ,SUPPLIER_CD");
        sql.append("	 ,SUPPLIER_NA");
        sql.append("	 ,INSERT_TS");
        sql.append("	 ,INSERT_USER_ID");
        sql.append("	 ,UPDATE_TS");
        sql.append("	 ,UPDATE_USER_ID");
     // #1325,1328対応 2016.05.19 to Mod (S)
        sql.append("	 ,TEIKAN_FG");
     // #1325,1328対応 2016.05.19 to Mod (S)
        sql.append("	) ");
        sql.append(" SELECT ");
        // #2238 Mod 2016.10.18 Li.Sheng (S)
        //sql.append("	 '" + batchDt + "' ");
        sql.append("	 '" + yokuBatchDt + "' ");
        // #2238 Mod 2016.10.18 Li.Sheng (E)
        sql.append("	 ,'" + SYORI_KBDEL + "'  ");
        sql.append("	 ,LPAD(trim(DBS.SYOHIN_CD), 13,'0') ");
        sql.append("	 ,DBS.SYOHIN_NA");
        sql.append("	 ,DBS.DPT_CD");
        sql.append("	 ,DBS.LINE_CD");
        sql.append("	 ,DBS.CLASS_CD");
        sql.append("	 ,DBS.SUPPLIER_CD");
        sql.append("	 ,DBS.SUPPLIER_NA");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        sql.append("	 ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' ");
        sql.append("	 ,'" + jobId + "' ");
        // #1325,1328対応 2016.05.18 to Mod (S)
        sql.append("	 ,case when DBS.TEIKAN_FG is null then '0' else DBS.TEIKAN_FG end ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append(" FROM ");
        sql.append("	DT_BLK_SYOHIN DBS");
        sql.append(" WHERE ");
        sql.append("	 NOT EXISTS");
        sql.append("	( ");
        // No.619 MSTB098 Del 2016.03.14 Huy.NT (S)
        /*
        sql.append(" 		SELECT ");
        sql.append("	 		TRS.SYOHIN_CD");
        sql.append(" 		FROM ");
        sql.append("			TMP_R_SYOHIN TRS ");
        // No.577 MSTB908 Add 2016.02.17 TU.TD (S)
        sql.append("			INNER JOIN");
        sql.append("			(");
        */
        // No.619 MSTB098 Del 2016.03.14 Huy.NT (E)
        sql.append("				SELECT");
        // No.619 MSTB098 Mod 2016.03.14 Huy.NT (S)
        /*
        sql.append("					TR.BUNRUI1_CD");
        sql.append("					,TR.SYOHIN_CD");
        */
        sql.append("					TR.SYOHIN_CD");
        // No.619 MSTB098 Mod 2016.03.14 Huy.NT (E)
        sql.append("				FROM TMP_R_SYOHIN TR");
        sql.append("				INNER JOIN");
        sql.append("				(");
        sql.append("					SELECT");
        sql.append("						SYOHIN_CD");
        sql.append("						,MAX(YUKO_DT) AS YUKO_DT");
        sql.append("					FROM TMP_R_SYOHIN");
        sql.append("					WHERE YUKO_DT <= '" + yokuBatchDt + "'");
        sql.append("					GROUP BY SYOHIN_CD");
        sql.append("				) TR2");
        sql.append("				ON");
        sql.append("					TR.SYOHIN_CD = TR2.SYOHIN_CD");
        sql.append("					AND TR.YUKO_DT = TR2.YUKO_DT");
        // No.619 MSTB098 Del 2016.03.14 Huy.NT (S)
        /*
        sql.append("			) TR1");
        sql.append("			ON"); 
        sql.append("				TRIM(TRS.BUNRUI1_CD) = TRIM(TR1.BUNRUI1_CD)");
        sql.append("				AND TRS.SYOHIN_CD = TR1.SYOHIN_CD");
        */
        // No.619 MSTB098 Del 2016.03.14 Huy.NT (E)
        // No.577 MSTB908 Add 2016.02.17 TU.TD (E)
        sql.append(" 		WHERE ");
        // No.619 MSTB098 Mod 2016.03.14 Huy.NT (S)
        /*
        // No.577 MSTB908 Add 2016.02.17 TU.TD (S)
        sql.append("		("); 
        // No.577 MSTB908 Add 2016.02.17 TU.TD (E)
        sql.append("			TRS.SYOHIN_CD = DBS.SYOHIN_CD ");
        // No.544 MSTB908 Add 2016.01.25 TU.TD (S)
        sql.append("			OR LPAD(TRS.OLD_SYOHIN_CD,'13','0') = DBS.SYOHIN_CD ");
        // No.544 MSTB908 Add 2016.01.25 TU.TD (E)
        // No.577 MSTB908 Add 2016.02.17 TU.TD (S)
        sql.append("		)");
        sql.append("		AND TRS.DELETE_FG = '0'");
        // No.577 MSTB908 Add 2016.02.17 TU.TD (E)
        */
        sql.append("		("); 
        sql.append("			TR.SYOHIN_CD = DBS.SYOHIN_CD ");
        sql.append("			OR LPAD(TR.OLD_SYOHIN_CD,'13','0') = DBS.SYOHIN_CD ");
        sql.append("		)");
        sql.append("		AND TR.DELETE_FG = '0'");
        // No.619 MSTB098 Mod 2016.03.14 Huy.NT (E)
        sql.append("	) ");

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
