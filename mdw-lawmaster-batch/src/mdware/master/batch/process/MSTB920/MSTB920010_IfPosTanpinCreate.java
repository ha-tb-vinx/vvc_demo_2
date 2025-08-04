package mdware.master.batch.process.MSTB920;

import java.nio.CharBuffer;
import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB920010_IfPosTanpinCreate クラス</p>
* <p>説明　: WK_緊急PLU店別商品の内容を、IF_緊急PLU単品メンテに取込む</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 (2015.10.13) THO.VT FIVImart様対応
* @version 1.01 (2015.11.23) Duc.DCM FIVI対応
* @Version 1.02  (2016.04.26) to  FIVIMART対応
* @Version 1.03  (2016.05.26) to  FIVIMART対応
* @Version 1.04  (2016.09.06) S.Li #1871 FIVImart対応
* @Version 1.05  (2016.11.09) S.Takayama #1750 FIVImart対応
* @Version 1.06  (2016.11.25) S.Takayama #2839 FIVImart対応
* @Version 1.07  (2016.12.09) Li.Sheng #3049 FIVImart様対応
* @version 1.08  (2016.12.13) T.Han #3234 FIVImart対応
* @version 1.09  (2017.01.11) M.Kanno #3587 FIVIMART対応
* @version 1.10  (2017.02.09) M.Son #3765 FIVIMART対応
* @version 1.11  (2017.02.17) X.Liu #3686 FIVIMART対応
* @version 1.12  (2017.05.19) S.Takayama #5039対応
* @version 1.13  (2020.07.13) KHAI.NN #6167 MKV対応
* @version 1.14  (2020.09.22) KHAI.NN #6227 MKV対応
* @version 1.15  (2020.09.30) KHAI.NN #6238 MKV対応
* @Version 1.16  (2024.01.16) DUY.HM #15277 MKH対応
*/

public class MSTB920010_IfPosTanpinCreate {
    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();
    // バッチ日付
    private String batchDt = null;
    private String yokuBatchDt = null;
    /** システム時刻 */
    private String timeStamp = "";
    /** 作成日 */
    private String sakuseiDt = "";

    // テーブルTOROKU_ID_A
    private static final String TABLE_IF_PLU_EMG_TANPIN = " IF_PLU_EMG_TANPIN "; // IF_緊急PLU単品メンテ
    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
    /** 送信回数 */
    private static final String SEND_QT = "01";
    /** 新規・訂正 登録ID */
    private static final String TOROKU_ID_A = "A";

    private static final String TOROKU_ID_D = "D";
    /** 商品バーコード*/
    private static final String SYOHIN_BAR_CD = "000000000000000000";
    /** 会員売価単価*/
    // Mod 2015/10/26 THO.VT (S)
//	private static final String KAIIN_BAITANKA_VL = "00000000000000000";
    private static final String KAIIN_BAITANKA_VL = "0";
    // Mod 2015/10/26 THO.VT (E)
    /** PLUフラグ*/
    private static final String PLU_FG = "2";
    /** 税区分（VAT）*/
    private static final String ZEI_KB = "0";
    /** 販売税率*/
    // Mod 2015/10/26 THO.VT (S)
//	private static final String HANBAI_ZEI_RT = "000";
    private static final String HANBAI_ZEI_RT = "0";
    // Mod 2015/10/26 THO.VT (E)
    /** 学生割引カード（KADS1M）フラグ*/
    private static final String STUDENT_WARIBIKI_CARD_FG = "0";
    /** パディング文字 */
    private static final String PADDING_STR = "0";
    /** 店舗コード 桁数 */
    private static final String TENPO_CD_LENGTH = "6";
    /** 正式名称（漢字）開始カラム */
    private static final String KANJI_NA_START_COLUMN = "1";
    /** 正式名称（漢字）桁数 ライン */
    private static final String KANJI_NA_LENGTH = "20";
    private static final int LENGTH_SYOHIN_RN  = 15;
    private static final int LENGTH_SYOHIN_NA = 30;
    private static final int LENGTH_SYOHIN_NA_CHN = 40;
    private static final int LENGTH_HANBAI_TN = 5;
    private static final int LENGTH_DIVISION_CD = 3;
    private static final int LENGTH_DEPARTMENT_CD = 3;
    private static final int LENGTH_CREATE_TS = 8;
    private static final int LENGTH_INJI_HANBAI_TN = 8;
    private static final int LENGTH_INJI_SEIZOU_DT = 3;
    private static final int LENGTH_ZEI_CD = 5;
    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB920010_IfPosTanpinCreate(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB920010_IfPosTanpinCreate() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }
    /**
     * 本処理
     * @throws Exception
     */
    public void execute() throws Exception{
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
            writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);
            yokuBatchDt = DateChanger.addDate(batchDt, 1);

            // 作成日取得
            timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
            sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
            writeLog(Level.INFO_INT, "作成日： " + sakuseiDt);

            // IF_緊急PLU単品メンテTRUNCATE
            writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ削除処理を開始します。");
            DBUtil.truncateTable(db, TABLE_IF_PLU_EMG_TANPIN);
            writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテを削除処理を終了します。");

            // IF_緊急PLU単品メンテ（新規・訂正）登録処理の登録
            writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ（新規・訂正）登録処理（WK→IF）を開始します。");
            iRec = db.executeUpdate(getIfPluEmgTanpinInsertSql());
            writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ（新規・訂正）を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ（新規・訂正）登録処理（WK→IF）を終了します。");

            db.commit();
            // #1750 MSTB920 2016.11.09 S.Takayama (S)
            // IF_緊急PLU単品メンテ（削除）の登録
            //writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を開始します。");
            //iRec = db.executeUpdate(getIfPluEmgTanpinDelInsertSql());
            //writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ（削除）を" + iRec + "件作成しました。");
            //writeLog(Level.INFO_INT, "IF_緊急PLU単品メンテ（削除）登録処理（SEND→IF）を終了します。");
            
            //IF_PLU単品メンテ（削除）の登録
            writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を開始します。");
            iRec = db.executeUpdate(getIfPluTanpinDelInsertSql());
            writeLog(Level.INFO_INT, "IF_PLU単品メンテ（削除）を" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_PLU単品メンテ（削除）登録処理（SEND→IF）を終了します。");
            // #1750 MSTB920 2016.11.09 S.Takayama (E)

            db.commit();

            writeLog(Level.INFO_INT, "処理を終了します。");

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
     * IF_緊急PLU単品メンテ(新規・訂正)を登録するSQLを取得する
     *
     * @return IF_PLU_EMG_TANPIN(新規・訂正)登録SQL
     */
    public String getIfPluEmgTanpinInsertSql() throws Exception{
        StringBuilder sql= new StringBuilder();
        String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

        sql.append("INSERT /*+ APPEND */ INTO ");
        sql.append(TABLE_IF_PLU_EMG_TANPIN);
        sql.append("	( ");
        sql.append("	EIGYO_DT");
        sql.append("	,TENPO_CD ");
        sql.append("	,SEND_QT ");
        sql.append("	,TOROKU_ID ");
        sql.append("	,SYOHIN_CD ");
        sql.append("	,SYOHIN_RN ");
        sql.append("	,SYOHIN_NA ");
        sql.append("	,SYOHIN_RN_CHN ");
        sql.append("	,SYOHIN_NA_CHN ");
        sql.append("	,SYOHIN_BAR_CD ");
        sql.append("	,BAITANKA_VL ");
        sql.append("	,KAIIN_BAITANKA_VL ");
        sql.append("	,HANBAI_TN ");
        sql.append("	,DIVISION_CD ");
        sql.append("	,DEPARTMENT_CD ");
        sql.append("	,CLASS_CD ");
        sql.append("	,SUBCLASS_CD ");
        sql.append("	,TEIKAN_FG ");
        sql.append("	,PLU_FG ");
        sql.append("	,CREATE_TS ");
        sql.append("	,ZEI_KB ");
        sql.append("	,ZEI_RT ");
        sql.append("	,SEASON_ID ");
        sql.append("	,HANBAI_ZEI_RT ");
        sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,SYOHI_KIGEN_DT ");
        sql.append("	,CARD_FG ");
        sql.append("	,INJI_HANBAI_TN ");
        sql.append("	,INJI_SEIZOU_DT ");
        sql.append("	,ZEI_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        sql.append("	,SIIRESAKI_CD ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        // to 2016.05.26 #1334対応　桁数変更 (S)
        sql.append("	,OLD_SYOHIN_CD ");
        // to 2016.05.26 #1334対応　桁数変更 (E)
        
        /* #1921 Add 2016.09.06 Li.Sheng (S) */
        sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
        sql.append("	,LABEL_SEIBUN ");
        sql.append("	,LABEL_HOKAN_HOHO ");
        sql.append("	,LABEL_TUKAIKATA ");
        /* #1921 Add 2016.09.06 Li.Sheng (E) */
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
        sql.append("	,GYOTAI_KB ");
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
// #3049 Add 2016.12.09 Li.Sheng (S)
        sql.append("	,LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
        // #3765 Add 2017.02.09 M.Son (S)
        sql.append("	,INJI_HANBAI_TN_EN ");
        // #3765 Add 2017.02.09 M.Son (E)
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (S)
        sql.append("	,ITEM_OFFICIAL_NA ");
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("	) ");
        sql.append(" SELECT ");
        sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
        sql.append("	,LPAD(TRIM(WTEP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                            AS TENPO_CD ");
        sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
        sql.append("	,'" + TOROKU_ID_A + "'                                                                                               AS TOROKU_ID ");
        sql.append("	,WTEP.SYOHIN_CD                                                                                                       AS SYOHIN_CD ");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (S)
//        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (S)
        //sql.append("	,WTEP.HINMEI_KANJI_NA                                                                                   AS SYOHIN_NA");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (E)
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (E)
        
        // to 2016.04.25 v0r6　S62対応　桁数変更 (S)
        // sql.append("	,SUBSTRB(WTEP.REC_HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ")                            AS SYOHIN_RN_CHN ");
        sql.append("	,WTEP.KEIRYOKI_NM                                                                                                    AS KEIRYOKI_NM");
        // sql.append("	,'" + spaces(LENGTH_SYOHIN_NA_CHN)+ "'                                                                               AS SYOHIN_NA_CHN");
        sql.append("	,WTEP.REC_HINMEI_KANJI_NA                                                                                            AS REC_HINMEI_KANJI_NA");
        // to 2016.04.25 v0r6　S62対応　桁数変更 (E)
        
        sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
        sql.append("	,WTEP.BAITANKA_VL                                                                                                     AS BAITANKA_VL");
        sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
        sql.append("	,'" + spaces(LENGTH_HANBAI_TN) + "'                                                                                   AS HANBAI_TN ");
        sql.append("	,'" + spaces(LENGTH_DIVISION_CD) + "'                                                                                 AS DIVISION_CD ");
        sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD) + "'                                                                               AS DEPARTMENT_CD ");
        sql.append("	,LTRIM(WTEP.BUNRUI2_CD, '0')                                                                                          AS CLASS_CD ");
        sql.append("	,LTRIM(WTEP.BUNRUI5_CD, '0')                                                                                           AS SUBCLASS_CD ");
        sql.append("	,WTEP.TEIKAN_FG                                                                                                       AS TEIKAN_FG ");
        sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
        sql.append("	,'" + spaces(LENGTH_CREATE_TS) + "'                                                                                   AS CREATE_TS ");
	    // 2016/12/13 VINX t.han #3234対応（S)
        //sql.append("	,'" + ZEI_KB + "'                                                                                                    AS ZEI_KB ");
        sql.append("	,WTEP.ZEI_KB                                                                                                         AS ZEI_KB ");
	    // 2016/12/13 VINX t.han #3234対応（E)
        sql.append("	,WTEP.ZEI_RT                                                                                                          AS ZEI_RT ");
        sql.append("	,WTEP.SEASON_ID                                                                                                      AS SEASON_ID ");
        sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
        sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,WTEP.SYOHI_KIGEN_DT                                                                                                  AS SYOHI_KIGEN_DT ");
        sql.append("	,WTEP.CARD_FG                                                                                                         AS CARD_FG ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        // sql.append("	,'" + spaces(LENGTH_INJI_HANBAI_TN) + "'                                                                              AS INJI_HANBAI_TN ");
        sql.append("	,WTEP.HANBAI_TANI                                                                                                       AS HANBAI_TANI ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
        sql.append("	,'" + spaces(LENGTH_ZEI_CD) + "'                                                                                      AS ZEI_CD ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        sql.append("	,WTEP.SIIRESAKI_CD                                                                                                   AS SIIRESAKI_CD ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        // to 2016.05.26 #1334対応　桁数変更 (S)
        sql.append("	,WTEP.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
        // to 2016.05.26 #1334対応　桁数変更 (E)
        /* #1921 Add 2016.09.06 Li.Sheng (S) */
        sql.append("	,WTEP.SYOHI_KIGEN_HYOJI_PATTER                                                                                                   AS SYOHI_KIGEN_HYOJI_PATTER ");
        sql.append("	,WTEP.LABEL_SEIBUN                                                                                                   AS LABEL_SEIBUN ");
        sql.append("	,WTEP.LABEL_HOKAN_HOHO                                                                                                   AS LABEL_HOKAN_HOHO ");
        sql.append("	,WTEP.LABEL_TUKAIKATA                                                                                                   AS LABEL_TUKAIKATA ");
        /* #1921 Add 2016.09.06 Li.Sheng (E) */
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
        sql.append("	,WTEP.GYOTAI_KB ");
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
// #3049 Add 2016.12.09 Li.Sheng (S)
        sql.append("	,WTEP.LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
        // #3765 Add 2017.02.09 M.Son (S)
        sql.append("	,WTEP.HANBAI_TANI_EN ");
        // #3765 Add 2017.02.09 M.Son (E)
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (S)
        sql.append("	,WTEP.HINMEI_KANJI_NA AS ITEM_OFFICIAL_NA");
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append(" FROM ");
        sql.append("	WK_TEC_EMG_PLU WTEP ");
        //No.201 MST920 Del 2015.11.23 Duc.DCM (S)
        /*sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("		SELECT ");
        sql.append("			 DTPS.BUNRUI1_CD ");
        sql.append("			,DTPS.SYOHIN_CD ");
        sql.append("			,DTPS.TENPO_CD ");
        sql.append("		FROM ");
        sql.append("			DT_TEC_PLU_SEND DTPS ");
        sql.append("		) DTPS ");
        sql.append("		ON ");
        sql.append("			DTPS.BUNRUI1_CD	= WTEP.BUNRUI1_CD	AND ");
        sql.append("			DTPS.SYOHIN_CD	= WTEP.SYOHIN_CD		AND ");
        sql.append("			DTPS.TENPO_CD	= WTEP.TENPO_CD ");*/
        //No.201 MST920 Del 2015.11.23 Duc.DCM (E)
        // #1750 MSTB920 2016.11.09 S.Takayama (S)
// #5039 Del 2017.05.19 S.Takayama (S)
//        sql.append("	LEFT JOIN DT_TEC_PLU_SEND DTPS 	");
//        sql.append("	ON DTPS.BUNRUI1_CD = WTEP.BUNRUI1_CD	");
//        sql.append("	AND DTPS.SYOHIN_CD = WTEP.SYOHIN_CD	");
//        sql.append("	AND DTPS.TENPO_CD = WTEP.TENPO_CD	");
// #5039 Del 2017.05.19 S.Takayama (E)
        // #1750 MSTB920 2016.11.09 S.Takayama (E)
        sql.append(" WHERE ");
        //No.201 MST920 Mod 2015.11.23 Duc.DCM (S)
        /*sql.append("	WTEP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
        sql.append("	( ");
        sql.append("		(DTPS.SYOHIN_CD		IS NULL		AND ");
        sql.append("		 WTEP.PLU_SEND_DT	= '" + yokuBatchDt + "')	OR ");
        sql.append("		DTPS.SYOHIN_CD		IS NOT NULL ");
        sql.append("	) ");*/
        sql.append("	WTEP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	");
        //No.201 MST920 Mod 2015.11.23 Duc.DCM (E)
        // #1750 MSTB920 2016.11.09 S.Takayama (S)
        sql.append("	AND WTEP.BAIKA_HAISHIN_FG = '0'	");
        // #3587 MSTB920 2017.01.11 M.Kanno (S)
        //sql.append("	AND 	");
        //sql.append("		(	");
        //sql.append("			(	");
        //sql.append("				DTPS.SYOHIN_CD IS NULL	");
        //sql.append("				AND(	");
        //sql.append("					WTEP.PLU_SEND_DT = '" + batchDt + "'	");
        //sql.append("					OR WTEP.PLU_SEND_DT = '99999999'	");
        //sql.append("					)	");
        //sql.append("				AND(	");
        //sql.append("					WTEP.PLU_HANEI_TIME IS NULL	");
        //sql.append("					OR WTEP.PLU_HANEI_TIME < (SELECT SEND_TIME FROM POS_FILE_SEQ)	");
        //sql.append("					)	");
        //sql.append("	 			)	");
        //sql.append("			OR DTPS.SYOHIN_CD IS NOT NULL	");
        //sql.append("		)	");
        // #3587 MSTB920 2017.01.11 M.Kanno (E)
        // #1750 MSTB920 2016.11.09 S.Takayama (E)
        //#3686 Add X.Liu 2017.02.17 (S)
        sql.append("	AND WTEP.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        //#3686 Add X.Liu 2017.02.17 (E)

        return sql.toString();
    }

    /**
     * IF_緊急PLU単品メンテ（削除）を登録するSQLを取得する
     *
     * @return IF_PLU_EMG_TANPIN(削除)登録SQL
     */
    public String getIfPluEmgTanpinDelInsertSql() throws Exception {
        StringBuilder sql= new StringBuilder();
        String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

        sql.append("INSERT /*+ APPEND */ INTO ");
        sql.append(TABLE_IF_PLU_EMG_TANPIN);
        sql.append("	( ");
        sql.append("	EIGYO_DT");
        sql.append("	,TENPO_CD ");
        sql.append("	,SEND_QT ");
        sql.append("	,TOROKU_ID ");
        sql.append("	,SYOHIN_CD ");
        sql.append("	,SYOHIN_RN ");
        sql.append("	,SYOHIN_NA ");
        sql.append("	,SYOHIN_RN_CHN ");
        sql.append("	,SYOHIN_NA_CHN ");
        sql.append("	,SYOHIN_BAR_CD ");
        sql.append("	,BAITANKA_VL ");
        sql.append("	,KAIIN_BAITANKA_VL ");
        sql.append("	,HANBAI_TN ");
        sql.append("	,DIVISION_CD ");
        sql.append("	,DEPARTMENT_CD ");
        sql.append("	,CLASS_CD ");
        sql.append("	,SUBCLASS_CD ");
        sql.append("	,TEIKAN_FG ");
        sql.append("	,PLU_FG ");
        sql.append("	,CREATE_TS ");
        sql.append("	,ZEI_KB ");
        sql.append("	,ZEI_RT ");
        sql.append("	,SEASON_ID ");
        sql.append("	,HANBAI_ZEI_RT ");
        sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,SYOHI_KIGEN_DT ");
        sql.append("	,CARD_FG ");
        sql.append("	,INJI_HANBAI_TN ");
        sql.append("	,INJI_SEIZOU_DT ");
        sql.append("	,ZEI_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        sql.append("	,SIIRESAKI_CD ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        // to 2016.05.26 #1334対応　桁数変更 (S)
        sql.append("	,OLD_SYOHIN_CD ");
        // to 2016.05.26 #1334対応　桁数変更 (E)
        
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
        sql.append("	,LPAD(TRIM(DTPS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                           AS TENPO_CD ");
        sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
        sql.append("	,'" + TOROKU_ID_D + "'                                                                                               AS TOROKU_ID ");
        sql.append("	,DTPS.SYOHIN_CD                                                                                                      AS SYOHIN_CD ");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
        
        // sql.append("	,SUBSTRB(DTPS.REC_HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ")                           AS SYOHIN_RN_CHN ");
        // sql.append("	,'" + spaces(LENGTH_SYOHIN_NA_CHN) + "'                                                                              AS SYOHIN_NA_CHN");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        // sql.append("	,SUBSTRB(WTEP.REC_HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ")                            AS SYOHIN_RN_CHN ");
        sql.append("	,DTPS.KEIRYOKI_NM                                                                                                    AS KEIRYOKI_NM");
        // sql.append("	,'" + spaces(LENGTH_SYOHIN_NA_CHN)+ "'                                                                               AS SYOHIN_NA_CHN");
        sql.append("	,DTPS.REC_HINMEI_KANJI_NA                                                                                            AS REC_HINMEI_KANJI_NA");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
        sql.append("	,DTPS.BAITANKA_VL                                                                                                    AS BAITANKA_VL");
        sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
        sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                                                                   AS HANBAI_TN ");
        sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                                                                 AS DIVISION_CD ");
        sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                                                               AS DEPARTMENT_CD ");
        sql.append("	,LTRIM(DTPS.BUNRUI2_CD, '0')                                                                                         AS CLASS_CD ");
        sql.append("	,LTRIM(DTPS.BUNRUI5_CD, '0')                                                                                          AS SUBCLASS_CD ");
        sql.append("	,DTPS.TEIKAN_FG                                                                                                      AS TEIKAN_FG ");
        sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
        sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                                                                   AS CREATE_TS ");
        sql.append("	,'" + ZEI_KB + "'                                                                                                    AS ZEI_KB ");
        sql.append("	,DTPS.ZEI_RT                                                                                                         AS ZEI_RT ");
        sql.append("	,DTPS.SEASON_ID                                                                                                     AS SEASON_ID ");
        sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
        sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,DTPS.SYOHI_KIGEN_DT                                                                                                 AS SYOHI_KIGEN_DT ");
        sql.append("	,DTPS.CARD_FG                                                                                                        AS CARD_FG ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        // sql.append("	,'" + spaces(LENGTH_INJI_HANBAI_TN) + "'                                                                              AS INJI_HANBAI_TN ");
        sql.append("	,DTPS.HANBAI_TANI                                                                                                       AS HANBAI_TANI ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
        sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                                                                      AS ZEI_CD ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
        
        // to 2016.04.26 v0r6　S62対応　桁数変更 (S)
        sql.append("	,DTPS.SIIRESAKI_CD                                                                                                   AS SIIRESAKI_CD ");
        // to 2016.04.26 v0r6　S62対応　桁数変更 (E)
        
        // to 2016.05.26 #1334対応　桁数変更 (S)
        sql.append("	,DTPS.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
        // to 2016.05.26 #1334対応　桁数変更 (E)
        
        sql.append(" FROM ");
        sql.append("	DT_TEC_PLU_SEND DTPS ");
        sql.append("	INNER JOIN ");
        sql.append("		( ");
        sql.append("		SELECT ");
        sql.append("			 WTPS.BUNRUI1_CD ");
        sql.append("			,WTPS.SYOHIN_CD ");
        sql.append("		FROM ");
        sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
        sql.append("		WHERE ");
        sql.append("			WTPS.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
        sql.append("		) WTPS ");
        sql.append("		ON ");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append("			DTPS.BUNRUI1_CD	= WTPS.BUNRUI1_CD	AND ");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append("			DTPS.SYOHIN_CD	= WTPS.SYOHIN_CD ");

        return sql.toString();
    }
    
    // #1750 MSTB920 2016.11.09 S.Takayama (S)
    /**
     * IF_PLU単品メンテ（削除）を登録するSQLを取得する
     *
     * @return IF_PLU_TANPIN(削除)登録SQL
     */
    public String getIfPluTanpinDelInsertSql() throws Exception {
        StringBuilder sql= new StringBuilder();
        String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);
        
        sql.append("INSERT /*+ APPEND */ INTO ");
        sql.append(TABLE_IF_PLU_EMG_TANPIN);
        sql.append("	( ");
        sql.append("	EIGYO_DT");
        sql.append("	,TENPO_CD ");
        sql.append("	,SEND_QT ");
        sql.append("	,TOROKU_ID ");
        sql.append("	,SYOHIN_CD ");
        sql.append("	,SYOHIN_RN ");
        sql.append("	,SYOHIN_NA ");
        sql.append("	,SYOHIN_RN_CHN ");
        sql.append("	,SYOHIN_NA_CHN ");
        sql.append("	,SYOHIN_BAR_CD ");
        sql.append("	,BAITANKA_VL ");
        sql.append("	,KAIIN_BAITANKA_VL ");
        sql.append("	,HANBAI_TN ");
        sql.append("	,DIVISION_CD ");
        sql.append("	,DEPARTMENT_CD ");
        sql.append("	,CLASS_CD ");
        sql.append("	,SUBCLASS_CD ");
        sql.append("	,TEIKAN_FG ");
        sql.append("	,PLU_FG ");
        sql.append("	,CREATE_TS ");
        sql.append("	,ZEI_KB ");
        sql.append("	,ZEI_RT ");
        sql.append("	,SEASON_ID ");
        sql.append("	,HANBAI_ZEI_RT ");
        sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,SYOHI_KIGEN_DT ");
        sql.append("	,CARD_FG ");
        sql.append("	,INJI_HANBAI_TN ");
        sql.append("	,INJI_SEIZOU_DT ");
        sql.append("	,ZEI_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	,SIIRESAKI_CD ");
        sql.append("	,OLD_SYOHIN_CD ");
        sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
        sql.append("	,LABEL_SEIBUN ");
        sql.append("	,LABEL_HOKAN_HOHO ");
        sql.append("	,LABEL_TUKAIKATA ");
        // #2839 MSTB920 2016.11.24 S.Takayama (S)
     	sql.append("	,GYOTAI_KB ");
     	// #2839 MSTB920 2016.11.24 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
        sql.append("	,LABEL_COUNTRY_NA ");
// #3049 Add 2016.12.09 Li.Sheng (E)
        // #3765 Add 2017.02.09 M.Son (S)
        sql.append("	,INJI_HANBAI_TN_EN ");
        // #3765 Add 2017.02.09 M.Son (E)
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (S)
        sql.append("	,ITEM_OFFICIAL_NA ");
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append("	) ");
// #5039 Del 2017.05.19 S.Takayama (S)
//        sql.append(" SELECT ");
//        sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
//        sql.append("	,LPAD(TRIM(DTPS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                           AS TENPO_CD ");
//        sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
//        sql.append("	,'" + TOROKU_ID_D + "'                                                                                               AS TOROKU_ID ");
//        sql.append("	,DTPS.SYOHIN_CD                                                                                                      AS SYOHIN_CD ");
//        sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
//        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
//        sql.append("	,DTPS.KEIRYOKI_NM                                                                                                    AS KEIRYOKI_NM");
//        sql.append("	,DTPS.REC_HINMEI_KANJI_NA                                                                                            AS REC_HINMEI_KANJI_NA");
//        sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
//        sql.append("	,DTPS.BAITANKA_VL                                                                                                    AS BAITANKA_VL");
//        sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
//        sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                                                                   AS HANBAI_TN ");
//        sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                                                                 AS DIVISION_CD ");
//        sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                                                               AS DEPARTMENT_CD ");
//        sql.append("	,LTRIM(DTPS.BUNRUI2_CD, '0')                                                                                         AS CLASS_CD ");
//        sql.append("	,LTRIM(DTPS.BUNRUI5_CD, '0')                                                                                          AS SUBCLASS_CD ");
//        sql.append("	,DTPS.TEIKAN_FG                                                                                                      AS TEIKAN_FG ");
//        sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
//        sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                                                                   AS CREATE_TS ");
//	    // 2016/12/13 VINX t.han #3234対応（S)
//        //sql.append("	,'" + ZEI_KB + "'                                                                                                    AS ZEI_KB ");
//        sql.append("	,DTPS.ZEI_KB                                                                                                         AS ZEI_KB ");
//	    // 2016/12/13 VINX t.han #3234対応（E)
//        sql.append("	,DTPS.ZEI_RT                                                                                                         AS ZEI_RT ");
//        sql.append("	,DTPS.SEASON_ID                                                                                                     AS SEASON_ID ");
//        sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
//        sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
//        sql.append("	,DTPS.SYOHI_KIGEN_DT                                                                                                 AS SYOHI_KIGEN_DT ");
//        sql.append("	,DTPS.CARD_FG                                                                                                        AS CARD_FG ");
//        sql.append("	,DTPS.HANBAI_TANI                                                                                                       AS HANBAI_TANI ");
//        sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
//        sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                                                                      AS ZEI_CD ");
//        sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
//        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
//        sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
//        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
//        sql.append("	,DTPS.SIIRESAKI_CD                                                                                                   AS SIIRESAKI_CD ");
//// #3049 Mod 2016.12.09 Li.Sheng (S)
////        sql.append("	,WTEP.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
////        sql.append("	,WTEP.SYOHI_KIGEN_HYOJI_PATTER                                                                                                   AS SYOHI_KIGEN_HYOJI_PATTER ");
////        sql.append("	,WTEP.LABEL_SEIBUN                                                                                                   AS LABEL_SEIBUN ");
////        sql.append("	,WTEP.LABEL_HOKAN_HOHO                                                                                                   AS LABEL_HOKAN_HOHO ");
////        sql.append("	,WTEP.LABEL_TUKAIKATA                                                                                                   AS LABEL_TUKAIKATA ");
////        // #2839 MSTB920 2016.11.24 S.Takayama (S)
////        sql.append("	,WTEP.GYOTAI_KB                                                                                                   AS GYOTAI_KB ");
//     	// #2839 MSTB920 2016.11.24 S.Takayama (E)
//        sql.append("	,DTPS.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
//        sql.append("	,DTPS.SYOHI_KIGEN_HYOJI_PATTER                                                                                                   AS SYOHI_KIGEN_HYOJI_PATTER ");
//        sql.append("	,DTPS.LABEL_SEIBUN                                                                                                   AS LABEL_SEIBUN ");
//        sql.append("	,DTPS.LABEL_HOKAN_HOHO                                                                                                   AS LABEL_HOKAN_HOHO ");
//        sql.append("	,DTPS.LABEL_TUKAIKATA                                                                                                   AS LABEL_TUKAIKATA ");
//        sql.append("	,DTPS.GYOTAI_KB                                                                                                   AS GYOTAI_KB ");
//// #3049 Mod 2016.12.09 Li.Sheng (E)
//// #3049 Add 2016.12.09 Li.Sheng (S)
//        sql.append("	,DTPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
//// #3049 Add 2016.12.09 Li.Sheng (E)
//        // #3765 Add 2017.02.09 M.Son (S)
//        sql.append("	,DTPS.HANBAI_TANI_EN AS HANBAI_TANI_EN");
//        // #3765 Add 2017.02.09 M.Son (E)
        sql.append(" SELECT ");
        sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
        sql.append("	,LPAD(TRIM(WTEP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                           AS TENPO_CD ");
        sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
        sql.append("	,'" + TOROKU_ID_D + "'                                                                                               AS TOROKU_ID ");
        sql.append("	,WTEP.SYOHIN_CD                                                                                                      AS SYOHIN_CD ");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (S)
//        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (S)
        //sql.append("	,WTEP.HINMEI_KANJI_NA                                                                                   AS SYOHIN_NA");
        sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
        // #6227 MSTB920 Mod 2020.09.22 KHAI.NN (E)
        // #6167 MSTB920 Mod 2020.07.13 KHAI.NN (E)
        sql.append("	,WTEP.KEIRYOKI_NM                                                                                                    AS KEIRYOKI_NM");
        sql.append("	,WTEP.REC_HINMEI_KANJI_NA                                                                                            AS REC_HINMEI_KANJI_NA");
        sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
        sql.append("	,WTEP.BAITANKA_VL                                                                                                    AS BAITANKA_VL");
        sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
        sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                                                                   AS HANBAI_TN ");
        sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                                                                 AS DIVISION_CD ");
        sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                                                               AS DEPARTMENT_CD ");
        sql.append("	,LTRIM(WTEP.BUNRUI2_CD, '0')                                                                                         AS CLASS_CD ");
        sql.append("	,LTRIM(WTEP.BUNRUI5_CD, '0')                                                                                          AS SUBCLASS_CD ");
        sql.append("	,WTEP.TEIKAN_FG                                                                                                      AS TEIKAN_FG ");
        sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
        sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                                                                   AS CREATE_TS ");
        sql.append("	,WTEP.ZEI_KB                                                                                                         AS ZEI_KB ");
        sql.append("	,WTEP.ZEI_RT                                                                                                         AS ZEI_RT ");
        sql.append("	,WTEP.SEASON_ID                                                                                                     AS SEASON_ID ");
        sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
        sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
        sql.append("	,WTEP.SYOHI_KIGEN_DT                                                                                                 AS SYOHI_KIGEN_DT ");
        sql.append("	,WTEP.CARD_FG                                                                                                        AS CARD_FG ");
        sql.append("	,WTEP.HANBAI_TANI                                                                                                       AS HANBAI_TANI ");
        sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
        sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                                                                      AS ZEI_CD ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
        sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
        sql.append("	,WTEP.SIIRESAKI_CD                                                                                                   AS SIIRESAKI_CD ");
        sql.append("	,WTEP.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
        sql.append("	,WTEP.SYOHI_KIGEN_HYOJI_PATTER                                                                                                   AS SYOHI_KIGEN_HYOJI_PATTER ");
        sql.append("	,WTEP.LABEL_SEIBUN                                                                                                   AS LABEL_SEIBUN ");
        sql.append("	,WTEP.LABEL_HOKAN_HOHO                                                                                                   AS LABEL_HOKAN_HOHO ");
        sql.append("	,WTEP.LABEL_TUKAIKATA                                                                                                   AS LABEL_TUKAIKATA ");
        sql.append("	,WTEP.GYOTAI_KB                                                                                                   AS GYOTAI_KB ");
        sql.append("	,WTEP.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
        sql.append("	,WTEP.HANBAI_TANI_EN AS HANBAI_TANI_EN");
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (S)
        sql.append("	,WTEP.HINMEI_KANJI_NA AS ITEM_OFFICIAL_NA ");
        // #6238 MSTB920 Add 2020.09.30 KHAI.NN (E)
        // #15277 ADD 2024.01.16 DUY.HM (S)
        sql.append("	,MAX_BUY_QT ");
        // #15277 ADD 2024.01.16 DUY.HM (E)
        sql.append(" FROM ");
        sql.append("	WK_TEC_EMG_PLU WTEP ");
// #5039 Del 2017.05.19 S.Takayama (S)
//        sql.append("	LEFT JOIN DT_TEC_PLU_SEND DTPS 	");
//        sql.append("	ON DTPS.BUNRUI1_CD = WTEP.BUNRUI1_CD	");
//        sql.append("	AND DTPS.SYOHIN_CD = WTEP.SYOHIN_CD	");
//        sql.append("	AND DTPS.TENPO_CD = WTEP.TENPO_CD	");
// #5039 Del 2017.05.19 S.Takayama (E)
        sql.append(" WHERE ");
        sql.append("	WTEP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	");
        sql.append("	AND WTEP.BAIKA_HAISHIN_FG = '1'	");
// #5039 Del 2017.05.19 S.Takayama (S)
//        sql.append("	AND DTPS.SYOHIN_CD IS NOT NULL	");
// #5039 Del 2017.05.19 S.Takayama (S)
        // #3587 MSTB920 2017.01.11 M.Kanno (S)
        //sql.append("	AND(	");
        //sql.append("		WTEP.PLU_SEND_DT = '" + batchDt + "'	");
        //sql.append("		OR WTEP.PLU_SEND_DT = '99999999'	");
        //sql.append("		)	");
        //sql.append("	AND(	");
        //sql.append("		WTEP.PLU_HANEI_TIME IS NULL	");
        //sql.append("		OR WTEP.PLU_HANEI_TIME < (SELECT SEND_TIME FROM POS_FILE_SEQ)	");
        //sql.append("		)	");
        // #3587 MSTB920 2017.01.11 M.Kanno (E)
        //#3686 X.Liu Add 2017.02.17 (S)
        sql.append("	AND WTEP.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
        //#3686 X.Liu Add 2017.02.17 (E)

        return sql.toString();
    }
    // #1750 MSTB920 2016.11.09 S.Takayama (E)

    /**
     * @param spaces
     * @return String
     */
    private String spaces(int spaces){
        return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
    }


    /******* 共通処理 **********/

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
