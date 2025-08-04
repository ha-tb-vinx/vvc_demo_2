package mdware.master.batch.process.MSTB102;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.command.MstbGetPluMaster;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * <p>タイトル: 指定日PLU店別商品マスタ作成処理(MSTB102030)</p>
 * <p>説明: POSデータ抽出指定登録画面で入力された指定日基準の店別商品マスタを作成する。</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @version 1.00  (2017.01.17) #1749対応 VINX S.Takayama
 * @version 1.01  (2017.02.09) #3765対応 VINX S.Takayama
 * @version 1.02  (2017.02.14) #3765対応 VINX M.Son
 * @version 1.03  (2017.02.14) #3686対応 VINX S.Takayama
 * @version 1.04  (2017.03.07) #4064対応 VINX S.Takayama
 * @version 1.05  (2017.03.14) #4337対応 VINX S.Takayama
 * @version 1.06  (2017.03.15) #4336対応 T.Han FIVIMART対応
 * @version 1.07  (2017.03.30) #4433対応 VINX J.KOJIMA
 * @version 1.08  (2017.04.19) #4705対応 T.Han FIVIMART対応
 * @version 1.09  (2020.07.13) #6167 KHAI.NN MKV対応
 * @version 1.10  (2021.10.22) Duy.HK #6367
 * @version 1.11  (2021.12.20) KHOI.ND #6406
 * @version 1.12  (2022.01.27) SIEU.D #6367
 * @version 1.13  (2022.10.05) VU.TD #6655
 * @version 1.14  (2023.02.16) SIEU.D #6728
 * @Version 1.15  (2024.01.16) DUY.HM #15277 MKH対応
 */
public class MSTB102030_PluTenbetuSyohinCreate {
    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;


    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    // テーブル
    private static final String TABLE_WK_TEC_STI_PLU = "WK_TEC_STI_PLU"; // WK_指定日PLU店別商品
    private static final String TABLE_WK_TEC_STI_PLU_SYOHIN = "WK_TEC_STI_PLU_SYOHIN"; // WK_指定日PLU商品
    private static final String TABLE_WK_TEC_STI_PLU_SYOHIN_REIGAI = "WK_TEC_STI_PLU_SYOHIN_REIGAI"; // WK_指定日PLU例外

	/** 特殊日付("99999999") */
	private static final String SPECIAL_DATE = "99999999";

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    // バッチ日付
    private String batchDt = null;

    /** ゼロ (定数) */
    private static final String CONST_ZERO = "0";

    /** jobID*/
    private String jobId = null;

	/** 店舗区分 */
	private String SEND_IF_TENPO_KB = null;

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB102030_PluTenbetuSyohinCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB102030_PluTenbetuSyohinCreate() {
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

	    	// 店舗区分セット
			Map tenpoMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_TENPO_KB);
			SEND_IF_TENPO_KB = SqlSupportUtil.createInString(tenpoMap.keySet().toArray());

            jobId = userLog.getJobId();
            // トランザクションログ有無（AutoCommitモード）
            // （trueを指定すると、トランザクションログ出力をしない分の速度向上
            // 　が見込めますが、コミット・ロールバックが無効となります。）
            db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

            // 処理開始ログ
            writeLog(Level.INFO_INT, "処理を開始します。");

            // バッチ日付
            batchDt = RMSTDATEUtil.getBatDateDt();


            // WK_指定日PLU店別商品のTRUNCATE
            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_WK_TEC_STI_PLU);
            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品を削除処理を終了します。");

            ResultSet rs = new MstbGetPluMaster().process(db);
            List<String> uketsuNo = new ArrayList<String>();
            List<String> tenpoCd = new ArrayList<String>();
            List<String> taisyoDt = new ArrayList<String>();


			while(rs.next()){

				String Uketsu = rs.getString("UKETSUKE_NO");
				String Tenpo = rs.getString("TENPO_CD");
				String Taisyo = rs.getString("TAISYO_DT");

				uketsuNo.add(Uketsu);
				tenpoCd.add(Tenpo);
				taisyoDt.add(Taisyo);

			}
			for (int i = 0 ;i<uketsuNo.size();i++){

				String Taisyo_Dt = taisyoDt.get(i);
				String Tenpo_Cd =tenpoCd.get(i);
				String Uketsu_No = uketsuNo.get(i);

// 2017.03.15 T.Han #4336対応（S)
				// WK_指定日PLU商品のTRUNCATE
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品削除処理を開始します。");
//	            DBUtil.truncateTable(db, TABLE_WK_TEC_STI_PLU_SYOHIN);
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品削除処理を終了します。");

	            // WK_指定日PLU例外のTRUNCATE
//	            writeLog(Level.INFO_INT, "WK_指定日PLU例外削除処理を開始します。");
//	            DBUtil.truncateTable(db, TABLE_WK_TEC_STI_PLU_SYOHIN_REIGAI);
//	            writeLog(Level.INFO_INT, "WK_指定日PLU例外削除処理を終了します。");

	            // WK_指定日PLU商品の登録
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品登録処理を開始します。");
//	            iRec = db.executeUpdate(getWkTecStiPluSyohinInsertSql(Taisyo_Dt));
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品を" + iRec + "件作成しました。");
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品登録処理を終了します。");

//	            db.commit();
	            // WK_指定日PLU例外の登録
//	            writeLog(Level.INFO_INT, "WK_指定日PLU例外登録処理を開始します。");
//	            iRec = db.executeUpdate(getWkTecStiPluReigaiInsertSql(Tenpo_Cd,Taisyo_Dt));
//	            writeLog(Level.INFO_INT, "WK_指定日PLU例外を" + iRec + "件作成しました。");
//	            writeLog(Level.INFO_INT, "WK_指定日PLU例外登録処理を終了します。");

//	            db.commit();
	            // WK_指定日PLU店別商品の登録
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品から店別展開を行い、WK_指定日PLU店別商品登録処理を開始します。");
//	            iRec = db.executeUpdate(getWkTecStiPluInsertSql(Uketsu_No,Taisyo_Dt,Tenpo_Cd));
//	            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品を" + iRec + "件作成しました。");
//	            writeLog(Level.INFO_INT, "WK_指定日PLU商品から店別展開を行い、WK_指定日PLU店別商品登録処理を終了します。");

//	            db.commit();
	            // WK_指定日PLU店別商品更新
//	            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品更新処理を開始します。");
//	            iRec = db.executeUpdate(getWkTecStiPluUpdateReigaiSql(Uketsu_No,Taisyo_Dt));
//	            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品を" + iRec + "件更新しました。");
//	            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品更新処理を終了します。");
//	            db.commit();
	            // WK_指定日PLU店別商品の登録
	            writeLog(Level.INFO_INT, "TMP指定日商品マスタから店別展開を行い、WK_指定日PLU店別商品作成処理を開始します。");
	            iRec = db.executeUpdate(getInsertWkTecStiPluSql(Uketsu_No,Taisyo_Dt,Tenpo_Cd));
	            writeLog(Level.INFO_INT, "WK_指定日PLU店別商品を" + iRec + "件作成しました。");
	            writeLog(Level.INFO_INT, "TMP指定日商品マスタから店別展開を行い、WK_指定日PLU店別商品作成処理を終了します。");

	            db.commit();
// 2017.03.15 T.Han #4336対応（E)

			}


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
     * WK_指定日PLU商品を登録するSQLを取得する
     * @param taisyo_Dt
     * @return WK_指定日PLU商品登録SQL
     */
    private String getWkTecStiPluSyohinInsertSql(String taisyo_Dt) throws SQLException {
        StringBuilder sql = new StringBuilder();
        //#3765 MSTB101 2017.02.14 M.Son (S)
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
        //#3765 MSTB101 2017.02.14 M.Son (E)

        sql.append(" INSERT /*+ APPEND */ INTO WK_TEC_STI_PLU_SYOHIN");
        sql.append(" 		(BUNRUI1_CD,");
        sql.append(" 		SYOHIN_CD,");
        sql.append(" 		OLD_SYOHIN_CD,");
        sql.append(" 		YUKO_DT,");
        sql.append(" 		GENTANKA_VL,");
        sql.append(" 		BAITANKA_VL,");
        sql.append(" 		SIIRESAKI_CD,");
        sql.append(" 		PLU_SEND_DT,");
        sql.append(" 		BAIKA_HAISHIN_FG,");
        sql.append(" 		BUNRUI5_CD,");
        sql.append(" 		REC_HINMEI_KANJI_NA,");
        sql.append(" 		REC_HINMEI_KANA_NA,");
        sql.append(" 		KIKAKU_KANJI_NA,");
        sql.append(" 		MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 		ZEI_KB,");
        sql.append(" 		SYOHIN_KB,");
        sql.append(" 		DELETE_FG,");
        sql.append(" 		HANBAI_TANI,");
        sql.append(" 		KEIRYOKI_NM,");
        sql.append("		BUNRUI2_CD, ");
        sql.append("		TEIKAN_FG, ");
        sql.append("		ZEI_RT, ");
        sql.append("		SEASON_ID, ");
        sql.append("		SYOHI_KIGEN_DT, ");
        sql.append("		CARD_FG, ");
        sql.append("		PLU_HANEI_TIME ,");
        sql.append("		SYOHI_KIGEN_HYOJI_PATTER ,");
        sql.append("		LABEL_SEIBUN ,");
        sql.append("		LABEL_HOKAN_HOHO ,");
        sql.append("		LABEL_TUKAIKATA ,");
		sql.append("		LABEL_COUNTRY_NA , ");
		// #3765 MSTB102 2017.02.09 S.Takayama (S)
		sql.append("		HANBAI_TANI_EN , ");
		// #3765 MSTB102 2017.02.09 S.Takayama (E)
        sql.append("		NENREI_SEIGEN_KB,");
        sql.append("		NENREI,");
        sql.append("		KAN_KB,");
        sql.append("		HOSHOUKIN,");
        sql.append("		INSERT_USER_ID,");
        sql.append("		INSERT_TS,");
        sql.append("		UPDATE_USER_ID,");
        sql.append("		UPDATE_TS)");
        sql.append(" SELECT ");
        sql.append(" 		TRES.BUNRUI1_CD,");
        sql.append(" 		TRES.SYOHIN_CD,");
        sql.append(" 		TRES.OLD_SYOHIN_CD,");
        sql.append(" 		TRES.YUKO_DT,");
        sql.append(" 		TRES.GENTANKA_VL,");
        sql.append(" 		TRES.BAITANKA_VL,");
        sql.append(" 		TRES.SIIRESAKI_CD,");
        sql.append(" 		TRES.PLU_SEND_DT,");
        sql.append(" 		NVL(TRES.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") BAIKA_HAISHIN_FG,");
        sql.append(" 		TRES.BUNRUI5_CD,");
        sql.append(" 		SUBSTR(TRES.HINMEI_KANJI_NA,1,40),");
        sql.append(" 		TRES.REC_HINMEI_KANA_NA,");
        sql.append(" 		TRES.KIKAKU_KANJI_NA,");
        sql.append(" 		TRES.MAKER_KIBO_KAKAKU_VL,");
        sql.append(" 		TRES.ZEI_KB,");
        sql.append(" 		TRES.SYOHIN_KB,");
        sql.append(" 		TRES.DELETE_FG,");
        sql.append(" 		NMST.KANJI_NA,");
        sql.append(" 		TRESA.SYOHIN_EIJI_NA,");
        sql.append("		TRES.BUNRUI2_CD, ");
        sql.append("		NVL(TRESA.HANBAI_HOHO_KB, 0) AS TEIKAN_KB, ");
        sql.append("		TRES4.TAX_RT, ");
        sql.append("'' AS SEASON_ID, ");
        sql.append("		TRES.SYOHI_KIGEN_DT, ");
        sql.append("		CASE ");
        sql.append("			WHEN TRESA.MEMBER_DISCOUNT_FG = 0 THEN 2 ");
        sql.append("			WHEN TRESA.MEMBER_DISCOUNT_FG = 1 THEN 0 ");
        sql.append("		 END AS MEMBER_DISCOUNT_FG ");
        sql.append("		, TRES.PLU_HANEI_TIME , ");
        sql.append("		TRES.SYOHI_KIGEN_HYOJI_PATTER , ");
        sql.append("		TRESA.LABEL_SEIBUN , ");
        sql.append("		TRESA.LABEL_HOKAN_HOHO , ");
        sql.append("		TRESA.LABEL_TUKAIKATA ");
        // #3765 MSTB102 2017.02.09 S.Takayama (S)
        //sql.append("		, RN1.KANJI_NA ");
        sql.append("		, RN1.KANJI_RN ");
        sql.append("		, RN2.KANJI_RN ");
        // #3765 MSTB102 2017.02.09 S.Takayama (E)
        sql.append("		, TRES.NENREI_SEIGEN_KB ");
        sql.append("		,TRES.NENREI");
        sql.append("		,TRES.KAN_KB");
        sql.append("		,TRES.HOSHOUKIN ");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");

        sql.append(" FROM 	TMP_R_STI_SYOHIN TRES INNER JOIN ");
        sql.append(" 		(SELECT MAX(YUKO_DT) YUKO_DT");
        sql.append(" 					,BUNRUI1_CD");
        sql.append(" 					,SYOHIN_CD");
        sql.append(" 			FROM TMP_R_STI_SYOHIN");
        sql.append(" 			WHERE YUKO_DT <= '" + taisyo_Dt + "'");
		sql.append("				  AND PLU_SEND_DT	<= '" + taisyo_Dt + "'  ");
        sql.append(" 			GROUP BY BUNRUI1_CD, SYOHIN_CD");
        sql.append(" 		) RES");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append("  		ON TRES.BUNRUI1_CD = RES.BUNRUI1_CD ");
//        sql.append(" 		AND TRES.SYOHIN_CD = RES.SYOHIN_CD ");
        sql.append("  		ON  ");
        sql.append(" 		 TRES.SYOHIN_CD = RES.SYOHIN_CD ");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append(" 		AND TRES.YUKO_DT = RES.YUKO_DT");

        sql.append(" 		INNER JOIN R_NAMECTF NMST");
//#3765 MSTB102 2017.02.14 M.Son (S)
//      sql.append(" 		ON trim(NMST.SYUBETU_NO_CD) = '3040'");
        sql.append("		ON trim(NMST.SYUBETU_NO_CD) = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' ");
//#3765 MSTB102 2017.02.14 M.Son (E)
        sql.append(" 		AND trim(NMST.CODE_CD) = trim(TRES.HANBAI_TANI) ");
        sql.append("		INNER JOIN ");
        sql.append("			TMP_R_STI_SYOHIN_ASN TRESA ");
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
        sql.append("				TMP_R_STI_SYOHIN TRES  ");
        sql.append("				INNER JOIN ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 TRES.SYOHIN_CD  ");
        sql.append("							,MAX(TRES.YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_SYOHIN TRES  ");
        sql.append("						WHERE  ");
        sql.append("							TRES.YUKO_DT	<= '" + taisyo_Dt + "'  ");
        sql.append("							AND TRES.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("						GROUP BY  ");
        sql.append("							 TRES.SYOHIN_CD  ");
        sql.append("					) TRES1  ");
        sql.append("					ON  ");
        sql.append("						TRES.SYOHIN_CD	= TRES1.SYOHIN_CD	AND  ");
        sql.append("						TRES.YUKO_DT		= TRES1.YUKO_DT ");
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
        sql.append("				TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("				INNER JOIN ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 TRETR.TAX_RATE_KB  ");
        sql.append("							, MAX(TRETR.YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("						WHERE  ");
        sql.append("							TRETR.YUKO_DT	<= '" + taisyo_Dt + "'  ");
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
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN1 ");
		sql.append("	ON ");
//#3765 MSTB102 2017.02.14 M.Son (S)
//		sql.append("		RN1.SYUBETU_NO_CD = '4070' AND ");
		sql.append("		RN1.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COUNTRY_DIVISION, userLocal) + "' AND ");
//#3765 MSTB102 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRESA.COUNTRY_CD) = TRIM(RN1.CODE_CD) ");
		// #3765 MSTB102 2017.02.09 S.Takayama (S)
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN2 ");
		sql.append("	ON ");
//#3765 MSTB102 2017.02.14 M.Son (S)
//		sql.append("		RN2.SYUBETU_NO_CD = '3040' AND ");
		sql.append("		RN2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
//#3765 MSTB102 2017.02.14 M.Son (E)
		sql.append("		TRIM(TRES.HANBAI_TANI) = TRIM(RN2.CODE_CD) ");
		// #3765 MSTB102 2017.02.09 S.Takayama (E)
        return sql.toString();
    }

    /**
     * WK_指定日PLU例外を登録するSQLを取得する
     * @param taisyo_Dt
     * @param tenpo_Cd
     * @return WK_指定日PLU例外登録SQL
     */
    private String getWkTecStiPluReigaiInsertSql(String tenpo_Cd, String taisyo_Dt) throws SQLException {

    	StringBuilder sql = new StringBuilder();
        sql.append(" INSERT /*+ APPEND */ INTO WK_TEC_STI_PLU_SYOHIN_REIGAI");
        sql.append(" 		(BUNRUI1_CD,");
        sql.append(" 		SYOHIN_CD,");
        sql.append(" 		TENPO_CD,");
        sql.append(" 		YUKO_DT,");
        sql.append(" 		GENTANKA_VL,");
        sql.append(" 		BAITANKA_VL,");
        sql.append(" 		SIIRESAKI_CD,");
        sql.append(" 		PLU_SEND_DT,");
        sql.append(" 		BAIKA_HAISHIN_FG,");
        sql.append(" 		DELETE_FG,");
        sql.append(" 		PLU_HANEI_TIME,");
        sql.append("		INSERT_USER_ID,");
        sql.append("		INSERT_TS,");
        sql.append("		UPDATE_USER_ID,");
        sql.append("		UPDATE_TS)");
        sql.append(" SELECT TESR.BUNRUI1_CD,");
        sql.append(" 		TESR.SYOHIN_CD,");
        sql.append(" 		TESR.TENPO_CD,");
        sql.append(" 		TESR.YUKO_DT,");
        sql.append(" 		TESR.GENTANKA_VL,");
        sql.append(" 		TESR.BAITANKA_VL,");
        sql.append(" 		TESR.SIIRESAKI_CD,");
        sql.append(" 		TESR.PLU_SEND_DT,");
        sql.append(" 		NVL(TESR.BAIKA_HAISHIN_FG, " + CONST_ZERO + ") BAIKA_HAISHIN_FG,");
        sql.append(" 		TESR.DELETE_FG,");
        sql.append(" 		TESR.PLU_HANEI_TIME");
		sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append(" FROM 	TMP_R_STI_TENSYOHIN_REIGAI TESR INNER JOIN ");
        sql.append(" 		(SELECT MAX(YUKO_DT) YUKO_DT");
        sql.append(" 				,BUNRUI1_CD");
        sql.append(" 				,SYOHIN_CD");
        sql.append(" 				,TENPO_CD");
        sql.append(" 			FROM TMP_R_STI_TENSYOHIN_REIGAI TRETR");
        sql.append(" 			WHERE YUKO_DT <= '" + taisyo_Dt + "'");
        sql.append(" 				AND TENPO_CD = '"+tenpo_Cd +"' ");
		sql.append("				  AND ");
		sql.append("				  ( ");
		sql.append("					PLU_SEND_DT	= '" + SPECIAL_DATE + "'	OR ");
		sql.append("					PLU_SEND_DT	<= '" + taisyo_Dt + "'	   ");
		sql.append("				  ) ");
        sql.append(" 			GROUP BY BUNRUI1_CD, SYOHIN_CD, TENPO_CD");
        sql.append(" 		) ESR");
        sql.append(" 		ON   TESR.SYOHIN_CD = ESR.SYOHIN_CD ");
        sql.append(" 		AND TESR.YUKO_DT = ESR.YUKO_DT");
        sql.append(" 		AND TESR.TENPO_CD = ESR.TENPO_CD");
        return sql.toString();
    }

    /**
     * WK_指定日PLU店別商品を登録するSQLを取得する
     *
     * @return WK_指定日PLU店別商品登録SQL
     */
    private String getWkTecStiPluInsertSql(String uketsuNo,String taiSyoDt,String Tenpo_Cd) throws SQLException {
    	StringBuilder sql = new StringBuilder();
        sql.append("	INSERT INTO WK_TEC_STI_PLU");
        sql.append("			(UKETSUKE_NO,");
        sql.append("			TAISYO_DT,");
        sql.append("			BUNRUI1_CD,");
        sql.append("			SYOHIN_CD,");
        sql.append(" 			OLD_SYOHIN_CD,");
        sql.append("			TENPO_CD,");
        sql.append("			GENTANKA_VL,");
        sql.append("			BAITANKA_VL,");
        sql.append("			SIIRESAKI_CD,");
        sql.append("			PLU_SEND_DT,");
        sql.append("			BAIKA_HAISHIN_FG,");
        sql.append("			BUNRUI5_CD,");
        sql.append("			REC_HINMEI_KANJI_NA,");
        sql.append("			REC_HINMEI_KANA_NA,");
        sql.append("			KIKAKU_KANJI_NA,");
        sql.append("			MAKER_KIBO_KAKAKU_VL,");
        sql.append("			ZEI_KB,");
        sql.append("			ERR_KB,");
        sql.append(" 			HANBAI_TANI,");
        sql.append(" 			KEIRYOKI_NM,");
        sql.append("			BUNRUI2_CD, ");
        sql.append("			TEIKAN_FG, ");
        sql.append("			ZEI_RT, ");
        sql.append("			SEASON_ID, ");
        sql.append("			SYOHI_KIGEN_DT, ");
        sql.append("			CARD_FG, ");
        sql.append("			PLU_HANEI_TIME, ");
        sql.append("			SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			LABEL_SEIBUN, ");
        sql.append("			LABEL_HOKAN_HOHO, ");
        sql.append("			LABEL_TUKAIKATA ,");
        sql.append("			GYOTAI_KB ,");
		sql.append("			LABEL_COUNTRY_NA , ");
		// #3765 MSTB102 2017.02.09 S.Takayama (S)
		sql.append("			HANBAI_TANI_EN , ");
		// #3765 MSTB102 2017.02.09 S.Takayama (E)
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        sql.append("			DELETE_FG , ");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			INSERT_USER_ID,");
        sql.append("			INSERT_TS,");
        sql.append("			UPDATE_USER_ID,");
        sql.append("			UPDATE_TS)");
        sql.append("			SELECT	" +uketsuNo+",'"+taiSyoDt+"',");
        sql.append("			EPS.BUNRUI1_CD,");
        sql.append("			EPS.SYOHIN_CD,");
        sql.append(" 			EPS.OLD_SYOHIN_CD,");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        //sql.append("			RET.TENPO_CD,");
        sql.append("			TRET.TENPO_CD,");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			EPS.GENTANKA_VL,");
        sql.append("			EPS.BAITANKA_VL,");
        sql.append("			EPS.SIIRESAKI_CD,");
        sql.append("			EPS.PLU_SEND_DT,");
        sql.append("			EPS.BAIKA_HAISHIN_FG,");
        sql.append("			EPS.BUNRUI5_CD,");
        sql.append("			EPS.REC_HINMEI_KANJI_NA,");
        sql.append("			EPS.REC_HINMEI_KANA_NA,");
        sql.append("			EPS.KIKAKU_KANJI_NA,");
        sql.append("			EPS.MAKER_KIBO_KAKAKU_VL,");
        sql.append("			EPS.ZEI_KB,");
        sql.append("			'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        sql.append(" 			EPS.HANBAI_TANI,");
        sql.append(" 			EPS.KEIRYOKI_NM,");
        sql.append("			EPS.BUNRUI2_CD, ");
        sql.append("			EPS.TEIKAN_FG, ");
        sql.append("			EPS.ZEI_RT, ");
        sql.append("	 		DECODE(SUB1.SYOHIN_CD,NULL,NULL,SUBSTR(EPS.BUNRUI5_CD, 1, 6)) AS SEASON_ID, ");
        sql.append("			EPS.SYOHI_KIGEN_DT, ");
        sql.append("			EPS.CARD_FG, ");
        sql.append("			EPS.PLU_HANEI_TIME, ");
        sql.append("			EPS.SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			EPS.LABEL_SEIBUN, ");
        sql.append("			EPS.LABEL_HOKAN_HOHO, ");
        sql.append("			EPS.LABEL_TUKAIKATA, ");
        sql.append("			TRET.GYOTAI_KB");
		sql.append("			,EPS.LABEL_COUNTRY_NA ");
		// #3765 MSTB102 2017.02.09 S.Takayama (S)
		sql.append("			,EPS.HANBAI_TANI_EN ");
		// #3765 MSTB102 2017.02.09 S.Takayama (E)
		// #3686 MSTB102 2017.02.17 S.Takayama (S)
        sql.append("			, EPS.DELETE_FG ");
		// #3686 MSTB102 2017.02.17 S.Takayama (E)
		sql.append("			,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("			,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("			,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	FROM 	WK_TEC_STI_PLU_SYOHIN EPS INNER JOIN ");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        //sql.append("			(SELECT TENPO_CD, ");
        //sql.append("					BUNRUI1_CD, ");
        //sql.append("					SYOHIN_CD ");
        //sql.append("			FROM TMP_R_STI_TORIATUKAI) RET");
        //sql.append("			ON 	(RET.BUNRUI1_CD = EPS.BUNRUI1_CD AND");
        //sql.append("				RET.SYOHIN_CD = EPS.SYOHIN_CD)");
        sql.append("			(SELECT TENPO_CD, ");
        sql.append("					SYOHIN_CD, ");
        sql.append("					BAIKA_HAISHIN_FG ");
	     // #3686 MSTB102 2017.02.14 S.Takayama (S)
        sql.append("					, DELETE_FG ");
	     // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			FROM WK_TEC_STI_PLU_SYOHIN_REIGAI) WTSPTR");
        sql.append("			ON 	EPS.SYOHIN_CD = WTSPTR.SYOHIN_CD");
        sql.append("			AND	WTSPTR.BAIKA_HAISHIN_FG = '"+ mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() +"'");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			INNER JOIN ");
        sql.append("			(SELECT TENPO_CD ");
        sql.append("			,GYOTAI_KB");
        sql.append("			FROM TMP_R_STI_TENPO");
        sql.append("			WHERE 	DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        // #6406 Del 2021.12.20 KHOI.ND (S)
		// sql.append("					TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") AND ");
        // #6406 Del 2021.12.20 KHOI.ND (E)
		sql.append("					TENPO_CD	= '" + Tenpo_Cd + "' AND ");
        sql.append("					ZAIMU_END_DT >= '" + taiSyoDt + "') TRET ");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        //sql.append("			ON 	TRIM(TRET.TENPO_CD) = TRIM(RET.TENPO_CD) ");
        sql.append("			ON TRIM(TRET.TENPO_CD) = TRIM(WTSPTR.TENPO_CD) ");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			LEFT JOIN ");
        sql.append("			(SELECT ");
        sql.append("			DGEA.SYOHIN_CD  ");
        sql.append("			,DHTT.TENPO_CD ");
        sql.append("			FROM DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
        sql.append("				INNER JOIN  DT_HANSOKU_TAISYO_TENPO DHTT ");
        sql.append("				ON  DGEA.THEME_CD = DHTT.THEME_CD ");
        sql.append("				INNER JOIN  DT_GROUPBAIHEN_ASN DGA ");
        sql.append("				ON  DGEA.THEME_CD = DGA.THEME_CD ");
        // #4064 MSTB102030 2017.03.07 S.Takayama (S)
        sql.append("				INNER JOIN  DT_THEME DT ");
        sql.append("				ON  DT.THEME_CD = DGA.THEME_CD ");
        sql.append("				AND  DT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        // #4064 MSTB102030 2017.03.07 S.Takayama (E)
        sql.append("			WHERE DGA.HANBAI_START_DT <= '" + taiSyoDt + "' ");
        sql.append("			AND DGA.HANBAI_END_DT >= '" + taiSyoDt + "' ");
        sql.append("			GROUP BY DGEA.SYOHIN_CD,DHTT.TENPO_CD ");
        sql.append("			) SUB1 ON EPS.SYOHIN_CD = SUB1.SYOHIN_CD ");
        sql.append("			AND TRET.TENPO_CD = SUB1.TENPO_CD ");
        sql.append("	WHERE	EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        // #3686 MSTB102 2017.02.14 S.Takayama (S)
        sql.append("			WTSPTR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
        // #3686 MSTB102 2017.02.14 S.Takayama (E)
        sql.append("			EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
		// #6367 DEL 2022.01.27 SIEU.D (S)
		// sql.append("			AND GET_JAN_SYUBETSU(EPS.SYOHIN_CD)	<> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'	 ");
		// #6367 DEL 2022.01.27 SIEU.D (E)
		// #3686 MSTB102 2017.02.14 S.Takayama (S)
		//sql.append("			AND ( ");
        //sql.append("				EPS.BAIKA_HAISHIN_FG = '0' ");
        //sql.append("			AND ");
		//sql.append("			NOT EXISTS ");
		//sql.append("			( ");
		//sql.append("				SELECT ");
		//sql.append("			 		WTEPR.BUNRUI1_CD ");
		//sql.append("				FROM ");
		//sql.append("					WK_TEC_STI_PLU_SYOHIN_REIGAI WTEPR ");
		//sql.append("				WHERE ");
		//sql.append("					WTEPR.BAIKA_HAISHIN_FG	= '1'	AND ");
		//sql.append("					WTEPR.BUNRUI1_CD	= EPS.BUNRUI1_CD			AND ");
		//sql.append("					WTEPR.SYOHIN_CD		= EPS.SYOHIN_CD			AND ");
		//sql.append("					WTEPR.TENPO_CD		= RET.TENPO_CD ");
		//sql.append("			) ");
		//sql.append("			) ");
		// #3686 MSTB102 2017.02.14 S.Takayama (E)
        return sql.toString();
    }

    // 2017.03.15 T.Han #4336対応（S)
    /**
     * WK_指定日PLU店別商品を登録するSQLを取得する
     *
     * @return WK_指定日PLU店別商品登録SQL
     */
    private String getInsertWkTecStiPluSql(String uketsuNo,String taiSyoDt,String Tenpo_Cd) throws SQLException {
    	StringBuilder sql = new StringBuilder();
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        sql.append("	INSERT INTO WK_TEC_STI_PLU");
        sql.append("			(UKETSUKE_NO,");
        sql.append("			TAISYO_DT,");
        sql.append("			BUNRUI1_CD,");
        sql.append("			SYOHIN_CD,");
        sql.append(" 			OLD_SYOHIN_CD,");
        sql.append("			TENPO_CD,");
        sql.append("			GENTANKA_VL,");
        sql.append("			BAITANKA_VL,");
        sql.append("			SIIRESAKI_CD,");
        sql.append("			PLU_SEND_DT,");
        sql.append("			BAIKA_HAISHIN_FG,");
        sql.append("			BUNRUI5_CD,");
        // #6167 MSTB102 Add 2020.07.13 KHAI.NN (S)
        sql.append("			HINMEI_KANJI_NA,");
        // #6167 MSTB102 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("			MAX_BUY_QT,");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("			REC_HINMEI_KANJI_NA,");
        sql.append("			REC_HINMEI_KANA_NA,");
        sql.append("			KIKAKU_KANJI_NA,");
        sql.append("			MAKER_KIBO_KAKAKU_VL,");
        sql.append("			ZEI_KB,");
        sql.append("			ERR_KB,");
        sql.append("			BUNRUI2_CD,");
        sql.append("			TEIKAN_FG, ");
        sql.append("			ZEI_RT, ");
        sql.append("			SEASON_ID, ");
        sql.append("			SYOHI_KIGEN_DT, ");
        sql.append("			CARD_FG, ");
        sql.append("			INSERT_USER_ID,");
        sql.append("			INSERT_TS,");
        sql.append("			UPDATE_USER_ID,");
        sql.append("			UPDATE_TS,");
        sql.append("			HANBAI_TANI, ");
        sql.append("			KEIRYOKI_NM, ");
        sql.append("			PLU_HANEI_TIME, ");
        sql.append("			SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			LABEL_SEIBUN, ");
        sql.append("			LABEL_HOKAN_HOHO, ");
        sql.append("			LABEL_TUKAIKATA ,");
        sql.append("			GYOTAI_KB ,");
        sql.append("			LABEL_COUNTRY_NA , ");
        sql.append("			HANBAI_TANI_EN , ");
        sql.append("			DELETE_FG ) ");
        sql.append("	SELECT ");
        sql.append("			'" + uketsuNo + "', ");
        sql.append("			'" + taiSyoDt + "', ");
        sql.append("			TRSS.BUNRUI1_CD,");
        sql.append("			TRSS.SYOHIN_CD,");
        sql.append("			TRSS.OLD_SYOHIN_CD,");
        sql.append("			TRST.TENPO_CD,");
        sql.append("			NVL(TRSTR.GENTANKA_VL,TRSS.GENTANKA_VL) AS GENTANKA_VL,");
        sql.append("			NVL(TRSTR.BAITANKA_VL,TRSS.BAITANKA_VL) AS BAITANKA_VL,");
        sql.append("			NVL(TRSTR.SIIRESAKI_CD,TRSS.SIIRESAKI_CD) AS SIIRESAKI_CD,");
        sql.append("			NVL(TRSTR.PLU_SEND_DT,TRSS.PLU_SEND_DT) AS PLU_SEND_DT,");
        sql.append("			TRSTR.BAIKA_HAISHIN_FG,");
        sql.append("			TRSS.BUNRUI5_CD,");
        // #6167 MSTB102 Add 2020.07.13 KHAI.NN (S)
        sql.append("			TRSS.REC_HINMEI_KANJI_NA,");
        // #6167 MSTB102 Add 2020.07.13 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("			TRSS.FREE_4_KB AS MAX_BUY_QT, ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("			SUBSTR(TRSS.HINMEI_KANJI_NA, 1, 40),");
        sql.append("			SUBSTR(TRSS.HINMEI_KANA_NA, 1, 20),");
        sql.append("			TRSS.KIKAKU_KANJI_NA,");
        sql.append("			TRSS.MAKER_KIBO_KAKAKU_VL,");
        sql.append("			TRSS.ZEI_KB,");
        sql.append("			'" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "' AS ERR_KB, ");
        sql.append("			TRSS.BUNRUI2_CD, ");
        sql.append("			NVL(TRESA.HANBAI_HOHO_KB, '0'), ");
        sql.append("			TRSTR.TAX_RT, ");
        // #6728 MOD 2023.02.16 SIEU.D (S)
//        // #6367 Mod 2021.10.22 Duy.HK (S)
//        //sql.append("	 		DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(TRSS.BUNRUI5_CD, 1, 6)) AS SEASON_ID, ");
//        sql.append("            CASE ");
//        sql.append("                WHEN RS.WARIBIKI_KB = '0' THEN '999999' ");
//        // #6655 ADD 2022.10.04 VU.TD (S)        
//      	sql.append("        		WHEN RS.WARIBIKI_KB = '2' THEN '888888' ");
//    	// #6655 ADD 2022.10.04 VU.TD (E)
//        sql.append("                ELSE DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(TRSS.BUNRUI5_CD, 1, 6)) ");
//        sql.append("            END AS SEASON_ID, ");
//        // #6367 Mod 2021.10.22 Duy.HK (E)
        sql.append("            CASE WHEN TRSS.WARIBIKI_KB = '0' THEN '999999' ");
        sql.append("                WHEN TRSS.WARIBIKI_KB = '2' THEN '888888' ");
        sql.append("                ELSE DECODE(SUB2.SYOHIN_CD,NULL,NULL,SUBSTR(TRSS.BUNRUI5_CD, 1, 6)) ");
        sql.append("            END AS SEASON_ID, ");
        // #6728 MOD 2023.02.16 SIEU.D (E)
        sql.append("			TRSS.SYOHI_KIGEN_DT, ");
        sql.append("			CASE ");
        sql.append("				WHEN TRESA.MEMBER_DISCOUNT_FG = 0 THEN 2 ");
        sql.append("				WHEN TRESA.MEMBER_DISCOUNT_FG = 1 THEN 0 ");
        sql.append("		 	END AS MEMBER_DISCOUNT_FG, ");
		sql.append("			'" + userLog.getJobId() + "' AS INSERT_USER_ID, ");
		sql.append("			'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS, ");
		sql.append("			'" + userLog.getJobId() + "' AS UPDATE_USER_ID, ");
		sql.append("			'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS, ");
        sql.append("			RN2.KANJI_NA, ");
        sql.append("			TRESA.SYOHIN_EIJI_NA, ");
        sql.append("			NVL(TRSTR.PLU_HANEI_TIME, TRSS.PLU_HANEI_TIME), ");
        sql.append("			TRSS.SYOHI_KIGEN_HYOJI_PATTER, ");
        sql.append("			TRESA.LABEL_SEIBUN, ");
        sql.append("			TRESA.LABEL_HOKAN_HOHO, ");
        sql.append("			TRESA.LABEL_TUKAIKATA, ");
        sql.append("			TRST.GYOTAI_KB, ");
        sql.append("			RN1.KANJI_RN, ");
        sql.append("			RN2.KANJI_RN, ");
        sql.append("	  		GREATEST(TRSS.DELETE_FG,TRSTR.DELETE_FG) AS DELETE_FG ");
        sql.append("	FROM 	TMP_R_STI_SYOHIN TRSS ");
        // #6728 DEL 2023.02.16 SIEU.D (S)
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
//    	// #6620 MOD 2022.11.18 VU.TD (S)
////        sql.append("                                       RS_2.BUNRUI1_CD = RS_1.BUNRUI1_CD");
////        sql.append("                                   AND RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        sql.append("                                    RS_2.SYOHIN_CD  = RS_1.SYOHIN_CD");
//        // #6620 MOD 2022.11.18 VU.TD (E)
//        sql.append("                                   AND RS_2.YUKO_DT <= '" + batchDt + "'");
//        sql.append("                              ) ");
//        sql.append("        )  RS  ");
//        sql.append("     ON    TRSS.SYOHIN_CD   = RS.SYOHIN_CD  ");
//        // #6620 DEL 2022.11.18 VU.TD (S)
////        sql.append("       AND TRSS.BUNRUI1_CD  = RS.BUNRUI1_CD ");
//        // #6620 DEL 2022.11.18 VU.TD (E)
//        // #6367 Add 2021.10.22 Duy.HK (E)
        // #6728 DEL 2023.02.16 SIEU.D (E)
        sql.append("	INNER JOIN  ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				 TRES.SYOHIN_CD  ");
        sql.append("				,MAX(TRES.YUKO_DT) AS YUKO_DT ");
        sql.append("				,TRES.DELETE_FG ");
        sql.append("			FROM ");
        sql.append("				TMP_R_STI_SYOHIN TRES  ");
        sql.append("			WHERE  ");
        sql.append("				TRES.YUKO_DT <= '" + taiSyoDt + "' ");
        sql.append("				AND TRES.PLU_SEND_DT <= '" + taiSyoDt + "' ");
        sql.append("			GROUP BY  ");
        sql.append("				 TRES.SYOHIN_CD ");
        sql.append("				,TRES.DELETE_FG ");
        sql.append("		) SUB1 ");
        sql.append("		ON ");
        sql.append("			TRSS.SYOHIN_CD = SUB1.SYOHIN_CD AND  ");
        sql.append("			TRSS.YUKO_DT = SUB1.YUKO_DT AND ");
        sql.append("			TRSS.DELETE_FG = SUB1.DELETE_FG ");
        sql.append("	INNER JOIN  ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				 TRETR.TAX_RATE_KB ");
        sql.append("				 , TRETR.TAX_RT  ");
        sql.append("			FROM ");
        sql.append("				TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("				INNER JOIN ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 TRETR.TAX_RATE_KB  ");
        sql.append("							, MAX(TRETR.YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_TAX_RATE TRETR  ");
        sql.append("						WHERE  ");
        sql.append("							TRETR.YUKO_DT <= '" + taiSyoDt + "'  ");
        sql.append("							AND TRETR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("						GROUP BY  ");
        sql.append("							 TRETR.TAX_RATE_KB  ");
        sql.append("					) SUB6  ");
        sql.append("				ON ");
        sql.append("					TRETR.TAX_RATE_KB = SUB6.TAX_RATE_KB ");
        sql.append("					AND TRETR.YUKO_DT = SUB6.YUKO_DT ");
        sql.append("		) TRSTR ");
        sql.append("		ON ");
        sql.append("			TRSS.TAX_RATE_KB	= TRSTR.TAX_RATE_KB ");
        sql.append("	INNER JOIN ");
        sql.append("		TMP_R_STI_SYOHIN_ASN TRESA ");
        sql.append("	ON ");
        sql.append("		TRESA.SYOHIN_CD = TRSS.SYOHIN_CD ");
        sql.append("		AND TRESA.YUKO_DT = TRSS.YUKO_DT ");
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN1 ");
		sql.append("	ON ");
		sql.append("		RN1.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COUNTRY_DIVISION, userLocal) + "' AND ");
		sql.append("		TRIM(TRESA.COUNTRY_CD) = TRIM(RN1.CODE_CD) ");
		sql.append("	LEFT JOIN ");
		sql.append("		R_NAMECTF RN2 ");
		sql.append("	ON ");
		sql.append("		RN2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal) + "' AND ");
		sql.append("		TRIM(TRSS.HANBAI_TANI) = TRIM(RN2.CODE_CD) ");
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				 TRSTR.*  ");
        sql.append("			FROM ");
        sql.append("				TMP_R_STI_TENSYOHIN_REIGAI TRSTR  ");
        sql.append("				INNER JOIN  ");
        sql.append("					(  ");
        sql.append("						SELECT  ");
        sql.append("							 SYOHIN_CD  ");
        sql.append("							,TENPO_CD  ");
        sql.append("							,MAX(YUKO_DT) AS YUKO_DT  ");
        sql.append("						FROM  ");
        sql.append("							TMP_R_STI_TENSYOHIN_REIGAI ");
        sql.append("						WHERE  ");
        sql.append("							YUKO_DT <= '" + taiSyoDt + "' AND ");
        sql.append("							( PLU_SEND_DT <= '" + taiSyoDt + "' OR PLU_SEND_DT = '" + SPECIAL_DATE + "' )  ");
        sql.append("						GROUP BY  ");
        sql.append("							 SYOHIN_CD, TENPO_CD  ");
        sql.append("					) SUB3  ");
        sql.append("					ON ");
        sql.append("						TRSTR.YUKO_DT = SUB3.YUKO_DT AND ");
        sql.append("						TRSTR.SYOHIN_CD = SUB3.SYOHIN_CD AND  ");
        sql.append("						TRSTR.TENPO_CD = SUB3.TENPO_CD ");
        sql.append("		) TRSTR ");
        sql.append("		ON ");
        sql.append("			TRSS.SYOHIN_CD = TRSTR.SYOHIN_CD AND ");
        sql.append("			TRSTR.BAIKA_HAISHIN_FG = '0' ");
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				TENPO_CD ");
        sql.append("			   ,GYOTAI_KB");
        sql.append("			FROM TMP_R_STI_TENPO");
        sql.append("			WHERE 	");
        sql.append("					ZAIMU_END_DT >= '" + taiSyoDt + "' AND ");
		sql.append("					TENPO_CD	= '" + Tenpo_Cd + "' AND ");
		// #6406 Mod 2021.12.20 KHOI.ND (S)
        // sql.append("					DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND");
		// sql.append("					TENPO_KB		IN (" + SEND_IF_TENPO_KB + ")  ");
		sql.append("					DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		// #6406 Mod 2021.12.20 KHOI.ND (S)
        sql.append("		) TRST ");
        sql.append("		ON ");
        sql.append("			TRIM(TRST.TENPO_CD) = TRIM(TRSTR.TENPO_CD) ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT  ");
        sql.append("				DGEA.SYOHIN_CD  ");
        sql.append("			   ,DHTT.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        sql.append("			   ,DT.BUNRUI1_CD ");
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("			FROM  ");
        sql.append("				DT_GROUPBAIHEN_EXCLUDE_ASN DGEA ");
        sql.append("				INNER JOIN  DT_HANSOKU_TAISYO_TENPO DHTT ");
        sql.append("				ON  DGEA.THEME_CD = DHTT.THEME_CD ");
        sql.append("				INNER JOIN  DT_GROUPBAIHEN_ASN DGA ");
        sql.append("				ON  DGEA.THEME_CD = DGA.THEME_CD ");
        sql.append("				INNER JOIN  DT_THEME DT ");
        sql.append("				ON  DT.THEME_CD = DGA.THEME_CD ");
        sql.append("				AND  DT.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        sql.append("			WHERE ");

        // #4433 Add 2017.03.30 J.KOJIMA (S)
        sql.append("                ( ");
        sql.append("                   DT.DATE_SITEI_KB = '0' ");
        sql.append("                   AND ");
        // #4433 Add 2017.03.30 J.KOJIMA (E)

        sql.append("				DGA.HANBAI_START_DT <= '" + taiSyoDt + "' AND ");
        sql.append("				DGA.HANBAI_END_DT >= '" + taiSyoDt + "' ");

        // #4433 Add 2017.03.30 J.KOJIMA (S)
        sql.append("                ) ");
        sql.append("                OR ( ");
        sql.append("                    DT.DATE_SITEI_KB = '1' AND ");
 
        String day = taiSyoDt.substring(6, 8);
        if (day.equals("01")) {
            sql.append("            DT.FLG_1ST = '1' ");
        } else if (day.equals("02")) {
            sql.append("            DT.FLG_2ND = '1' ");
        } else if (day.equals("03")) {
            sql.append("            DT.FLG_3RD = '1' ");
        } else if (day.equals("04")) {
            sql.append("            DT.FLG_4TH = '1' ");
        } else if (day.equals("05")) {
            sql.append("            DT.FLG_5TH = '1' ");
        } else if (day.equals("06")) {
            sql.append("            DT.FLG_6TH = '1' ");
        } else if (day.equals("07")) {
            sql.append("            DT.FLG_7TH = '1' ");
        } else if (day.equals("08")) {
            sql.append("            DT.FLG_8TH = '1' ");
        } else if (day.equals("09")) {
            sql.append("            DT.FLG_9TH = '1' ");
        } else if (day.equals("10")) {
            sql.append("            DT.FLG_10TH = '1' ");
        } else if (day.equals("11")) {
            sql.append("            DT.FLG_11TH = '1' ");
        } else if (day.equals("12")) {
            sql.append("            DT.FLG_12TH = '1' ");
        } else if (day.equals("13")) {
            sql.append("            DT.FLG_13TH = '1' ");
        } else if (day.equals("14")) {
            sql.append("            DT.FLG_14TH = '1' ");
        } else if (day.equals("15")) {
            sql.append("            DT.FLG_15TH = '1' ");
        } else if (day.equals("16")) {
            sql.append("            DT.FLG_16TH = '1' ");
        } else if (day.equals("17")) {
            sql.append("            DT.FLG_17TH = '1' ");
        } else if (day.equals("18")) {
            sql.append("            DT.FLG_18TH = '1' ");
        } else if (day.equals("19")) {
            sql.append("            DT.FLG_19TH = '1' ");
        } else if (day.equals("20")) {
            sql.append("            DT.FLG_20TH = '1' ");
        } else if (day.equals("21")) {
            sql.append("            DT.FLG_21ST = '1' ");
        } else if (day.equals("22")) {
            sql.append("            DT.FLG_22ND = '1' ");
        } else if (day.equals("23")) {
            sql.append("            DT.FLG_23RD = '1' ");
        } else if (day.equals("24")) {
            sql.append("            DT.FLG_24TH = '1' ");
        } else if (day.equals("25")) {
            sql.append("            DT.FLG_25TH = '1' ");
        } else if (day.equals("26")) {
            sql.append("            DT.FLG_26TH = '1' ");
        } else if (day.equals("27")) {
            sql.append("            DT.FLG_27TH = '1' ");
        } else if (day.equals("28")) {
            sql.append("            DT.FLG_28TH = '1' ");
        } else if (day.equals("29")) {
            sql.append("            DT.FLG_29TH = '1' ");
        } else if (day.equals("30")) {
            sql.append("            DT.FLG_30TH = '1' ");
        } else if (day.equals("31")) {
            sql.append("            DT.FLG_31ST = '1' ");
        }
        sql.append("                    AND DGA.HANBAI_START_DT <= '" + taiSyoDt + "' ");
        sql.append("                    AND DGA.HANBAI_END_DT >= '" + taiSyoDt + "' ");
        sql.append("                ) ");
        // #4433 Add 2017.03.30 J.KOJIMA (E)

        sql.append("			GROUP BY ");
        sql.append("				DGEA.SYOHIN_CD,DHTT.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        sql.append("			   ,DT.BUNRUI1_CD ");
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("		) SUB2 ");
        sql.append("		ON ");
        sql.append("			TRSS.SYOHIN_CD = SUB2.SYOHIN_CD AND ");
        sql.append("			TRST.TENPO_CD = SUB2.TENPO_CD ");
// 2017.04.19 T.Han #4705対応 (S)
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append("			AND TRSS.BUNRUI1_CD = SUB2.BUNRUI1_CD ");
//        sql.append("			AND TRSTR.BUNRUI1_CD = SUB2.BUNRUI1_CD ");
        // #6620 DEL 2022.11.18 VU.TD (E)
// 2017.04.19 T.Han #4705対応 (E)
        sql.append("	WHERE ");
        sql.append("		TRSS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
        sql.append("		TRSTR.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
        // #6367 MOD 2022.01.27 SIEU.D (S)
        // sql.append("		TRSS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' AND ");
        // sql.append("		GET_JAN_SYUBETSU(TRSS.SYOHIN_CD) <> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "' ");
        sql.append("		TRSS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "' ");
        // #6367 MOD 2022.01.27 SIEU.D (E)

        return sql.toString();
    }
    // 2017.03.15 T.Han #4336対応（E)

    /**
     * WK_TEC_EMG_PLUの例外商品を上書きするSQLを取得する
     *
     * @return 例外商品をupdateするSQL
     */
    private String getWkTecStiPluUpdateReigaiSql(String uketsuNo,String taisyoDt) throws SQLException {
    	StringBuilder sql = new StringBuilder();
    	sql.append("MERGE ");
    	sql.append("INTO WK_TEC_STI_PLU WTEP ");
    	sql.append("  USING ( ");
    	sql.append("    SELECT "+uketsuNo+" AS UKETSUKE_NO,'"+taisyoDt+"' AS TAISYO_DT ");
    	sql.append("      , EPS.BUNRUI1_CD AS BUNRUI1_CD");
    	sql.append("      , EPS.SYOHIN_CD AS SYOHIN_CD");
    	sql.append("      , EPS.OLD_SYOHIN_CD AS OLD_SYOHIN_CD");
    	sql.append("      , TEPR.TENPO_CD AS TENPO_CD");
    	sql.append("      , NVL(TEPR.GENTANKA_VL, EPS.GENTANKA_VL) AS GENTANKA_VL");
    	sql.append("      , NVL(TEPR.BAITANKA_VL, EPS.BAITANKA_VL) AS BAITANKA_VL");
    	sql.append("      , NVL(TEPR.SIIRESAKI_CD, EPS.SIIRESAKI_CD) AS SIIRESAKI_CD");
    	sql.append("      , NVL(TEPR.PLU_SEND_DT, EPS.PLU_SEND_DT) AS PLU_SEND_DT");
        sql.append("		,TEPR.BAIKA_HAISHIN_FG AS BAIKA_HAISHIN_FG ");
    	sql.append("      , EPS.BUNRUI5_CD AS BUNRUI5_CD");
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
    	// #4337 MSTB102 2017.03.14 S.Takayama (S)
    	//sql.append("      , EPS.SEASON_ID AS SEASON_ID");
    	// #4337 MSTB102 2017.03.14 S.Takayama (E)
    	sql.append("      , EPS.SYOHI_KIGEN_DT AS SYOHI_KIGEN_DT");
    	sql.append("      , EPS.CARD_FG AS CARD_FG");
    	sql.append("      , NVL(TEPR.PLU_HANEI_TIME, EPS.PLU_HANEI_TIME) AS PLU_HANEI_TIME");
    	sql.append("      , EPS.SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER");
    	sql.append("      , EPS.LABEL_SEIBUN AS LABEL_SEIBUN");
    	sql.append("      , EPS.LABEL_HOKAN_HOHO AS LABEL_HOKAN_HOHO");
    	sql.append("      , EPS.LABEL_TUKAIKATA  AS LABEL_TUKAIKATA");
		sql.append("	  , EPS.LABEL_COUNTRY_NA");
		// #3765 MSTB102 2017.02.09 S.Takayama (S)
		sql.append("	  , EPS.HANBAI_TANI_EN ");
		// #3765 MSTB102 2017.02.09 S.Takayama (E)
		// #3686 MSTB102 2017.02.17 S.Takayama (S)
		sql.append("	  , GREATEST(EPS.DELETE_FG,TEPR.DELETE_FG) AS DELETE_FG ");
		// #3686 MSTB102 2017.02.17 S.Takayama (E)
		sql.append("      ,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
		sql.append("      ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
		sql.append("      ,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
		sql.append("      ,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
    	sql.append("    FROM");
    	sql.append("      WK_TEC_STI_PLU_SYOHIN EPS ");
    	sql.append("      INNER JOIN ");
    	sql.append("      	( ");
    	sql.append("      	SELECT ");
    	sql.append("      		TEPR.BUNRUI1_CD ");
    	sql.append("      		,TEPR.SYOHIN_CD ");
    	sql.append("      		,TEPR.TENPO_CD ");
    	sql.append("      		,TEPR.GENTANKA_VL ");
    	sql.append("      		,TEPR.BAITANKA_VL ");
    	sql.append("      		,TEPR.SIIRESAKI_CD ");
    	sql.append("      		,TEPR.PLU_SEND_DT ");
    	sql.append("      		,TEPR.BAIKA_HAISHIN_FG ");
    	sql.append("      		,TEPR.PLU_HANEI_TIME ");
    	// #3686 MSTB102 2017.02.17 S.Takayama (S)
    	sql.append("      		,TEPR.DELETE_FG ");
    	// #3686 MSTB102 2017.02.17 S.Takayama (E)
    	sql.append("      	FROM ");
    	sql.append("      	WK_TEC_STI_PLU_SYOHIN_REIGAI TEPR ");
    	sql.append("      	WHERE ");
    	sql.append("      	TEPR.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
    	sql.append("      	)  TEPR ");
    	// #6620 MOD 2022.11.18 VU.TD (S)
//    	sql.append("        ON EPS.BUNRUI1_CD = TEPR.BUNRUI1_CD ");
//    	sql.append("        AND EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
    	sql.append("        ON  ");
    	sql.append("         EPS.SYOHIN_CD = TEPR.SYOHIN_CD ");
    	// #6620 MOD 2022.11.18 VU.TD (E)
    	sql.append("      INNER JOIN ");
    	sql.append("      	( ");
    	sql.append("      	SELECT ");
    	sql.append("      		RET.TENPO_CD ");
    	sql.append("      		,RET.GYOTAI_KB ");
    	sql.append("      	FROM ");
    	sql.append("      	TMP_R_STI_TENPO RET ");
    	sql.append("      	WHERE ");
    	sql.append("      	RET.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
    	// #6406 Mod 2021.12.20 KHOI.ND (S)
    	// sql.append("      	RET.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' AND ");
    	// sql.append("      	RET.TENPO_KB		IN (" + SEND_IF_TENPO_KB + ") ");
    	sql.append("      	RET.DELETE_FG		= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
    	// #6406 Mod 2021.12.20 KHOI.ND (E)
    	sql.append("      	)  RET ");
    	sql.append("        ON TRIM(RET.TENPO_CD) = TRIM(TEPR.TENPO_CD) ");
    	sql.append("    WHERE");
    	sql.append("      EPS.DELETE_FG = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "'");
    	sql.append("      AND EPS.SYOHIN_KB <> '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'");
        // #6367 DEL 2022.01.27 SIEU.D (S)
        // sql.append("      AND GET_JAN_SYUBETSU(EPS.SYOHIN_CD) <> '" + mst000102_IfConstDictionary.SYOHIN_CD_SYUBETU_EDI + "'");
        // #6367 DEL 2022.01.27 SIEU.D (E)
    	sql.append("  ) WTEPS ");
    	sql.append("    ON ( ");
    	// #6620 MOD 2022.11.18 VU.TD (S)
//    	sql.append("      WTEP.BUNRUI1_CD = WTEPS.BUNRUI1_CD ");
//    	sql.append("      AND WTEP.SYOHIN_CD = WTEPS.SYOHIN_CD ");
    	sql.append("      WTEP.SYOHIN_CD = WTEPS.SYOHIN_CD ");
    	// #6620 MOD 2022.11.18 VU.TD (E)
    	sql.append("      AND WTEP.TENPO_CD = WTEPS.TENPO_CD");
    	sql.append("    ) WHEN MATCHED THEN UPDATE ");
    	sql.append("SET");
    	sql.append("  WTEP.GENTANKA_VL = WTEPS.GENTANKA_VL");
    	sql.append("  , WTEP.BAITANKA_VL = WTEPS.BAITANKA_VL");
    	sql.append("  , WTEP.SIIRESAKI_CD = WTEPS.SIIRESAKI_CD");
    	sql.append("  , WTEP.PLU_SEND_DT = WTEPS.PLU_SEND_DT");
    	sql.append("  , WTEP.BAIKA_HAISHIN_FG = WTEPS.BAIKA_HAISHIN_FG");
    	sql.append("  , WTEP.BUNRUI5_CD = WTEPS.BUNRUI5_CD");
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
    	// #4337 MSTB102 2017.03.14 S.Takayama (S)
    	//sql.append("  , WTEP.SEASON_ID = ''");
    	// #4337 MSTB102 2017.03.14 S.Takayama (E)
    	sql.append("  , WTEP.SYOHI_KIGEN_DT = WTEPS.SYOHI_KIGEN_DT");
    	sql.append("  , WTEP.CARD_FG = WTEPS.CARD_FG");
    	sql.append("  , WTEP.PLU_HANEI_TIME = WTEPS.PLU_HANEI_TIME");
    	sql.append("  , WTEP.SYOHI_KIGEN_HYOJI_PATTER = WTEPS.SYOHI_KIGEN_HYOJI_PATTER");
    	sql.append("  , WTEP.LABEL_SEIBUN = WTEPS.LABEL_SEIBUN");
    	sql.append("  , WTEP.LABEL_HOKAN_HOHO = WTEPS.LABEL_HOKAN_HOHO");
    	sql.append("  , WTEP.LABEL_TUKAIKATA = WTEPS.LABEL_TUKAIKATA");
    	sql.append("  , WTEP.LABEL_COUNTRY_NA = WTEPS.LABEL_COUNTRY_NA ");
    	// #3765 MSTB102 2017.02.09 S.Takayama (S)
    	sql.append("  , WTEP.HANBAI_TANI_EN = WTEPS.HANBAI_TANI_EN ");
    	// #3765 MSTB102 2017.02.09 S.Takayama (E)
		// #3686 MSTB102 2017.02.17 S.Takayama (S)
		sql.append("  , WTEP.DELETE_FG = WTEPS.DELETE_FG ");
		// #3686 MSTB102 2017.02.17 S.Takayama (E)
    	sql.append("  , WTEP.INSERT_USER_ID = WTEPS.INSERT_USER_ID");
    	sql.append("  , WTEP.INSERT_TS = WTEPS.INSERT_TS");
    	sql.append("  , WTEP.UPDATE_USER_ID = WTEPS.UPDATE_USER_ID");
    	sql.append("  , WTEP.UPDATE_TS = WTEPS.UPDATE_TS");

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
