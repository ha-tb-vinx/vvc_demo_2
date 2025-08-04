package mdware.master.batch.process.MSTB908;

import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 *
 * <p>タイトル: MSTB908010_CreateBlkSyohinBunruiKaisouData.java クラス</p>
 * <p>説明　: TMP商品分類体系マスタからBlynkに連携するグループマスタ、部門マスタ、デプトマスタ、ラインマスタ、クラスマスタデータを作成する</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2015.08.06) THAO.NTL FIVImart様対応
 * @Version 1.01 (2015.10.15) THAO.NTL #747対応
 * @Version 1.02 (2016.05.18) to #1325,1328対応
 * @version 1.03 (2016.10.18) nv.cuong #2238 FIVIMART対応
 *
 */
public class MSTB908010_CreateBlkSyohinBunruiKaisouData {

    /** DBインスタンス */
    private DataBase db = null;
    /** DB接続フラグ */
    private boolean closeDb = false;

    //ログ出力用変数
    private BatchLog batchLog = BatchLog.getInstance();
    private BatchUserLog userLog = BatchUserLog.getInstance();

    /** DB接続文字列 */
    private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

    // バッチ日付
    private String batchDt = null;

    /**
     * コンストラクタ
     * @param dataBase
     */
    public MSTB908010_CreateBlkSyohinBunruiKaisouData(DataBase db) {
        this.db = db;
        if (db == null) {
            this.db = new DataBase(CONNECTION_STR);
            closeDb = true;
        }
    }

    /**
     * コンストラクタ
     */
    public MSTB908010_CreateBlkSyohinBunruiKaisouData() {
        this(new DataBase(CONNECTION_STR));
        closeDb = true;
    }

    /**
     * 本処理
     * @throws Exception
     */
    public void execute() throws Exception {
        String jobId = userLog.getJobId();
        try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

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
            // #2238 add 2016.10.18 nv.cuong(S)
            batchDt = DateChanger.addDate(batchDt, 1);
            // #2238 add 2016.10.18 nv.cuong(E)

            // IF_BLKグループマスタのTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLKグループマスタを削除処理を開始します。");
            DBUtil.truncateTable(db, "IF_BLK_GROUP");
            writeLog(Level.INFO_INT, "IF_BLKグループマスタを削除処理を終了します。");

            // IF_BLK部門マスタのTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLK部門マスタを削除処理を開始します。");
            DBUtil.truncateTable(db, "IF_BLK_BUMON");
            writeLog(Level.INFO_INT, "IF_BLK部門マスタを削除処理を終了します。");

            // IF_BLKデプトマスタのTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLKデプトマスタを削除処理を開始します。");
            DBUtil.truncateTable(db, "IF_BLK_DPT");
            writeLog(Level.INFO_INT, "IF_BLKデプトマスタを削除処理を終了します。");

            // IF_BLKラインマスタのTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLKラインマスタを削除処理を開始します。");
            DBUtil.truncateTable(db, "IF_BLK_LINE");
            writeLog(Level.INFO_INT, "IF_BLKラインマスタを削除処理を終了します。");

            // IF_BLKクラスマスタのTRUNCATE
            writeLog(Level.INFO_INT, "IF_BLKクラスマスタを削除処理を開始します。");
            DBUtil.truncateTable(db, "IF_BLK_CLASS");
            writeLog(Level.INFO_INT, "IF_BLKクラスマスタを削除処理を終了します。");

            // IF_BLKグループマスタの登録
            writeLog(Level.INFO_INT, "IF_BLKグループマスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkGroupInsertSql());
            writeLog(Level.INFO_INT, "IF_BLKグループマスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLKグループマスタ登録処理を終了します。");

            db.commit();

            // IF_BLK部門マスタの登録
            writeLog(Level.INFO_INT, "IF_BLK部門マスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkBumonInsertSql());
            writeLog(Level.INFO_INT, "IF_BLK部門マスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLK部門マスタ登録処理を終了します。");

            db.commit();

            // IF_BLKデプトマスタの登録
            writeLog(Level.INFO_INT, "IF_BLKデプトマスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkDPTInsertSql());
            writeLog(Level.INFO_INT, "IF_BLKデプトマスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLKデプトマスタ登録処理を終了します。");

            db.commit();

            // IF_BLKラインマスタの登録
            writeLog(Level.INFO_INT, "IF_BLKラインマスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkLineInsertSql());
            writeLog(Level.INFO_INT, "IF_BLKラインマスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLKラインマスタ登録処理を終了します。");

            db.commit();

            // IF_BLKクラスマスタの登録
            writeLog(Level.INFO_INT, "IF_BLKクラスマスタ登録処理を開始します。");
            iRec = db.executeUpdate(getIfBlkClassInsertSql());
            writeLog(Level.INFO_INT, "IF_BLKクラスマスタを" + iRec + "件作成しました。");
            writeLog(Level.INFO_INT, "IF_BLKクラスマスタ登録処理を終了します。");

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
     * IF_BLK_GROUPを登録するSQLを取得する
     *
     * @return IF_BLK_GROUP登録SQL
     */
    private String getIfBlkGroupInsertSql() throws SQLException {
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_GROUP ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT ");
        sql.append("	,GROUP_CD ");
        sql.append("	,GROUP_NA ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "' ");
        // #747対応 2015.10.15 THAO.NTL Mod (S)
        sql.append("	,TRIM(TRS.DAIBUNRUI1_CD) ");
        sql.append("	,TRN.KANJI_NA ");
        // #747対応 2015.10.15 THAO.NTL Mod (E)
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
        sql.append("FROM ");
        sql.append("	TMP_R_SYOHIN_TAIKEI TRS ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				 TRN.CODE_CD ");
        sql.append("				,TRN.KANJI_NA ");
        sql.append("			FROM ");
        sql.append("				TMP_R_NAMECTF TRN ");
        sql.append("			WHERE ");
        sql.append("				TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.DAIBUNRUI1, userLocal) + "' ");
        sql.append("		) TRN ");
        sql.append("		ON ");
        sql.append("			TRIM(TRS.DAIBUNRUI1_CD) = TRN.CODE_CD ");
        sql.append("GROUP BY ");
        sql.append("	TRS.DAIBUNRUI1_CD, ");
        sql.append("	TRN.KANJI_NA ");

        return sql.toString();
    }

    /**
     * IF_BLK_BUMONを登録するSQLを取得する
     *
     * @return IF_BLK_BUMON登録SQL
     */
    private String getIfBlkBumonInsertSql() throws SQLException {
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_BUMON ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT ");
        sql.append("	,BUMON_CD ");
        sql.append("	,BUMON_NA ");
        sql.append("	,GROUP_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "' ");
        // #747対応 2015.10.15 THAO.NTL Mod (S)
        sql.append("	,TRIM(TRS.DAIBUNRUI2_CD) ");
        sql.append("	,TRN.KANJI_NA ");
        sql.append("	,TRIM(TRS.DAIBUNRUI1_CD) ");
        // #747対応 2015.10.15 THAO.NTL Mod (E)
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
        sql.append("FROM ");
        sql.append("	TMP_R_SYOHIN_TAIKEI TRS ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				 TRN.CODE_CD ");
        sql.append("				,TRN.KANJI_NA ");
        sql.append("			FROM ");
        sql.append("				TMP_R_NAMECTF TRN ");
        sql.append("			WHERE ");
        sql.append("				TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.DAIBUNRUI2, userLocal) + "' ");
        sql.append("		) TRN ");
        sql.append("		ON ");
        sql.append("			TRIM(TRS.DAIBUNRUI2_CD) = TRN.CODE_CD ");
        sql.append("GROUP BY ");
        sql.append("	TRS.DAIBUNRUI2_CD, ");
        sql.append("	TRS.DAIBUNRUI1_CD, ");
        sql.append("	TRN.KANJI_NA ");

        return sql.toString();
    }

    /**
     * IF_BLK_DPTを登録するSQLを取得する
     *
     * @return IF_BLK_DPT登録SQL
     */
    private String getIfBlkDPTInsertSql() throws SQLException {
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_DPT ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT ");
        sql.append("	,DPT_CD ");
        sql.append("	,DPT_NA ");
        sql.append("	,BUMON_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "' ");
        // #747対応 2015.10.15 THAO.NTL Mod (S)
        sql.append("	,TRIM(TRS.BUNRUI1_CD) ");
        sql.append("	,TRN.KANJI_NA ");
        sql.append("	,TRIM(TRS.DAIBUNRUI2_CD) ");
        // #747対応 2015.10.15 THAO.NTL Mod (E)
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
        sql.append("FROM ");
        sql.append("	TMP_R_SYOHIN_TAIKEI TRS ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				 TRN.CODE_CD ");
        sql.append("				,TRN.KANJI_NA ");
        sql.append("			FROM ");
        sql.append("				TMP_R_NAMECTF TRN ");
        sql.append("			WHERE ");
        sql.append("				TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
        sql.append("		) TRN ");
        sql.append("		ON ");
        sql.append("			TRIM(TRS.BUNRUI1_CD) = TRN.CODE_CD ");
        sql.append("GROUP BY ");
        sql.append("	TRS.BUNRUI1_CD, ");
        sql.append("	TRS.DAIBUNRUI2_CD, ");
        sql.append("	TRN.KANJI_NA ");

        return sql.toString();
    }

    /**
     * IF_BLK_LINEを登録するSQLを取得する
     *
     * @return IF_BLK_LINE登録SQL
     */
    private String getIfBlkLineInsertSql() throws SQLException {
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_LINE ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT ");
        sql.append("	,LINE_CD ");
        sql.append("	,LINE_NA ");
        sql.append("	,DPT_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "' ");
        sql.append("	,TRIM(TRS.BUNRUI2_CD) ");
        // #747対応 2015.10.15 THAO.NTL Mod (S)
        sql.append("	,TRN.KANJI_NA ");
        sql.append("	,TRIM(TRS.BUNRUI1_CD) ");
        // #747対応 2015.10.15 THAO.NTL Mod (E)
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
        sql.append("FROM ");
        sql.append("	TMP_R_SYOHIN_TAIKEI TRS ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				 TRN.CODE_CD ");
        sql.append("				,TRN.KANJI_NA ");
        sql.append("			FROM ");
        sql.append("				TMP_R_NAMECTF TRN ");
        sql.append("			WHERE ");
        sql.append("				TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
        sql.append("		) TRN ");
        sql.append("		ON ");
        sql.append("			TRIM(TRS.BUNRUI2_CD) = TRN.CODE_CD ");
        sql.append("GROUP BY ");
        sql.append("	TRS.BUNRUI2_CD, ");
        sql.append("	TRS.BUNRUI1_CD, ");
        sql.append("	TRN.KANJI_NA ");

        return sql.toString();
    }

    /**
     * IF_BLK_CLASSを登録するSQLを取得する
     *
     * @return IF_BLK_CLASS登録SQL
     */
    private String getIfBlkClassInsertSql() throws SQLException {
        String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT /*+ APPEND */ INTO IF_BLK_CLASS ");
        sql.append("	( ");
        sql.append("	 DATA_MAKE_DT ");
        sql.append("	,CLASS_CD ");
        sql.append("	,CLASS_NA ");
        sql.append("	,DPT_CD ");
        sql.append("	,LINE_CD ");
        sql.append("	,INSERT_TS ");
        sql.append("	,INSERT_USER_ID ");
        sql.append("	,UPDATE_TS ");
        sql.append("	,UPDATE_USER_ID ");
        sql.append("	) ");
        sql.append("SELECT ");
        sql.append("	 '" + batchDt + "' ");
        sql.append("	,TRIM(TRS.BUNRUI5_CD) AS BUNRUI5_CD ");
        // #747対応 2015.10.15 THAO.NTL Mod (S)
        sql.append("	,TRN.KANJI_NA ");
        sql.append("	,TRIM(TRS.BUNRUI1_CD) ");
        // #747対応 2015.10.15 THAO.NTL Mod (E)
        sql.append("	,TRIM(TRS.BUNRUI2_CD) AS BUNRUI2_CD ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS INSERT_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS INSERT_USER_ID ");
        sql.append("	,'" + AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR) + "' AS UPDATE_TS ");
        sql.append("	,'" + userLog.getJobId() + "' AS UPDATE_USER_ID ");
        sql.append("FROM ");
        sql.append("	TMP_R_SYOHIN_TAIKEI TRS ");
        sql.append("	LEFT JOIN ");
        sql.append("		( ");
        sql.append("			SELECT ");
        sql.append("				 TRN.CODE_CD ");
        sql.append("				,TRN.KANJI_NA ");
        sql.append("			FROM ");
        sql.append("				TMP_R_NAMECTF TRN ");
        sql.append("			WHERE ");
        sql.append("				TRN.SYUBETU_NO_CD	= '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
        sql.append("		) TRN ");
        sql.append("		ON ");
        // #1325,1328対応 2016.05.18 to Mod (S)
//		sql.append("			TRIM(TRS.BUNRUI5_CD) = TRN.CODE_CD ");
        sql.append("			TRIM(TRS.SYSTEM_KB || TRS.BUNRUI5_CD) = TRN.CODE_CD ");
        // #1325,1328対応 2016.05.18 to Mod (E)
        sql.append("GROUP BY ");
        sql.append("	TRS.BUNRUI5_CD, ");
        sql.append("	TRS.BUNRUI2_CD, ");
        sql.append("	TRS.BUNRUI1_CD, ");
        sql.append("	TRN.KANJI_NA ");

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
