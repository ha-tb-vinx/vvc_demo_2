package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperKouseiSyohinUriageJisekiCreateDao.java クラス</p>
 * <p>説明　: 日別客数集計Arecデータ作成</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016/12/21) S_MDware-G_FIVIマート様開発 VINX J.Endo
 * @Version 1.01 (2017/01/10) 性能改善 VINX J.Endo #3586
 * @Version 1.02 (2017/03/31) VINX X.Liu #4501
 *
 */
public class HibetuKyakusuSyukeiArecDataCreateDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String DAO_NAME = "日別客数集計Arecデータ作成";
    /** 登録先テーブル名称(日別客数集計Arecデータ作成) */
    private static final String DT_HIBETU_KYAKUSU_A_NAME = "日別客数集計Arecデータ";
    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE DT_HIBETU_KYAKUSU_A";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserID = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsSTR = null;
        PreparedStatementEx preparedStatementExInsTYP = null;
        PreparedStatementEx preparedStatementExDel = null;

        int insertCount = 0;

        try {
            // 日別客数集計Arecデータ作成の追加
            // ログ出力
            invoker.infoLog(strUserID + "　：　" + DT_HIBETU_KYAKUSU_A_NAME + "の追加を開始します。");

            // 日別客数集計Arecデータのデータを削除する
            preparedStatementExDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementExDel.execute();

            // 日別客数集計Arecデータ(STR)登録
            preparedStatementExInsSTR = invoker.getDataBase().prepareStatement(getHibetuKyakusuSyukeiArecDataSTRInsertSql());

            preparedStatementExInsSTR.setString(1, userId);
            preparedStatementExInsSTR.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsSTR.setString(3, userId);
            preparedStatementExInsSTR.setString(4, FiResorceUtility.getDBServerTime());
            preparedStatementExInsSTR.setString(5, FiResorceUtility.getBatchDt());

            insertCount = preparedStatementExInsSTR.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件の日別客数集計Arecデータ(STR)を追加しました。");


            // 日別客数集計Arecデータ(TYP)登録
            preparedStatementExInsTYP = invoker.getDataBase().prepareStatement(getHibetuKyakusuSyukeiArecDataTYPInsertSql());

            preparedStatementExInsTYP.setString(1, userId);
            preparedStatementExInsTYP.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExInsTYP.setString(3, userId);
            preparedStatementExInsTYP.setString(4, FiResorceUtility.getDBServerTime());
            preparedStatementExInsTYP.setString(5, FiResorceUtility.getPropertie("DUMMY_BUNRUI1_CD"));
            preparedStatementExInsTYP.setString(6, FiResorceUtility.getBatchDt());

            insertCount = preparedStatementExInsTYP.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件の日別客数集計Arecデータ(TYP)を追加しました。");
            invoker.infoLog(strUserID + "　：　" + DT_HIBETU_KYAKUSU_A_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExInsSTR != null) {
                    preparedStatementExInsSTR.close();
                }
                if (preparedStatementExInsTYP != null) {
                    preparedStatementExInsTYP.close();
                }
                if (preparedStatementExDel != null) {
                    preparedStatementExDel.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserID + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 日別客数集計Arecデータ(STR)作成用SQLを取得する
     *
     * @return 日別客数集計Arecデータ(STR)作成用SQL
     */
    private String getHibetuKyakusuSyukeiArecDataSTRInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO DT_HIBETU_KYAKUSU_A ( ");
        sql.append("    TENPO_CD ");
        sql.append("   ,KYAKU_SYUKEI_TANI ");
        sql.append("   ,KYAKU_SYUKEI_CD ");
        sql.append("   ,KEIJYO_DT ");
        sql.append("   ,KYAKU_QT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    LPAD(STORE,6,'0') ");
        sql.append("   ,'STR' ");
        sql.append("   ,LPAD(STORE,10,'0') ");
        sql.append("   ,SUBSTR(EIGYO_DT,1,8) ");
        sql.append("   ,COUNT(STORE) AS KYAKU_QT ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("    DISTINCT ");
        sql.append("        STORE ");
        sql.append("       ,POS ");
        sql.append("       ,TRANS_NO ");
        sql.append("       ,EIGYO_DT ");
        sql.append("    FROM DT_POS_A_ITEM ");
        sql.append("    WHERE ");
        sql.append("        DATA_SAKUSEI_DT = ? AND ");
        // #4501 X.Liu Mod 2017.03.31 (S)
//        sql.append("        ( ");
//        sql.append("        ATYPE = '0001' OR ");
//        sql.append("        ATYPE = '1011' ");
//        sql.append("        ) ");
        sql.append("        COMMAND = '0043' ");
        // #4501 X.Liu Mod 2017.03.31 (E)
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    STORE ");
        sql.append("   ,EIGYO_DT ");

        return sql.toString();
    }

    /**
     * 日別客数集計Arecデータ(TYP)作成用SQLを取得する
     *
     * @return 日別客数集計Arecデータ(TYP)作成用SQL
     */
    private String getHibetuKyakusuSyukeiArecDataTYPInsertSql() {
        StringBuilder sql = new StringBuilder();

        //2017/01/10 J.Endo 性能改善(#3586) MOD(S)
        //sql.append("INSERT INTO DT_HIBETU_KYAKUSU_A ( ");
        //sql.append("    TENPO_CD ");
        //sql.append("   ,KYAKU_SYUKEI_TANI ");
        //sql.append("   ,KYAKU_SYUKEI_CD ");
        //sql.append("   ,KEIJYO_DT ");
        //sql.append("   ,KYAKU_QT ");
        //sql.append("   ,INSERT_USER_ID ");
        //sql.append("   ,INSERT_TS ");
        //sql.append("   ,UPDATE_USER_ID ");
        //sql.append("   ,UPDATE_TS ");
        //sql.append("    ) ");
        //sql.append("SELECT ");
        //sql.append("    LPAD(STORE,6,'0') ");
        //sql.append("   ,'TYP' ");
        //sql.append("   ,LPAD(TRIM(BUNRUI1_CD),10,'0') ");
        //sql.append("   ,SUBSTR(EIGYO_DT,1,8) ");
        //sql.append("   ,COUNT(STORE) ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("   ,? ");
        //sql.append("FROM ( ");
        //sql.append("    SELECT ");
        //sql.append("    DISTINCT ");
        //sql.append("        STORE ");
        //sql.append("       ,POS ");
        //sql.append("       ,TRANS_NO ");
        //sql.append("       ,EIGYO_DT ");
        //sql.append("   ,CASE WHEN RS.BUNRUI1_CD IS NULL OR RS.DELETE_FG = '1' THEN ? ELSE RS.BUNRUI1_CD END AS BUNRUI1_CD ");
        //sql.append("    FROM DT_POS_A_ITEM DPAI ");
        //sql.append("    LEFT OUTER JOIN R_SYOHIN RS ");
        //sql.append("    ON  EXISTS "); // この存在チェックは「商品マスタ.有効日範囲内の最大」を抽出（開始）
        //sql.append("    (SELECT 1 FROM R_SYOHIN RS2 ");
        //sql.append("    WHERE ");
        //sql.append("        RS.SYOHIN_CD = DPAI.SKU ");
        //sql.append("    AND RS.YUKO_DT = ");
        //sql.append("        (SELECT MAX(YUKO_DT) FROM R_SYOHIN ");
        //sql.append("        WHERE ");
        //sql.append("            SYOHIN_CD = DPAI.SKU ");
        //sql.append("        AND YUKO_DT <= DPAI.EIGYO_DT ");
        //sql.append("        GROUP BY SYOHIN_CD ");
        //sql.append("        ) ");
        //sql.append("    ) ");          // この存在チェックは「商品マスタ.有効日範囲内の最大」を抽出（終了）
        //sql.append("    WHERE ");
        //sql.append("        DATA_SAKUSEI_DT = ? AND ");
        //sql.append("        ( ATYPE = '0001' OR ATYPE = '1011' ) ");
        //sql.append("    ) ");
        //sql.append("GROUP BY ");
        //sql.append("    STORE ");
        //sql.append("   ,BUNRUI1_CD ");
        //sql.append("   ,EIGYO_DT ");
        sql.append("INSERT INTO DT_HIBETU_KYAKUSU_A ( ");
        sql.append("    TENPO_CD ");
        sql.append("   ,KYAKU_SYUKEI_TANI ");
        sql.append("   ,KYAKU_SYUKEI_CD ");
        sql.append("   ,KEIJYO_DT ");
        sql.append("   ,KYAKU_QT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    LPAD(STORE,6,'0') ");
        sql.append("   ,'TYP' ");
        sql.append("   ,LPAD(TRIM(BUNRUI1_CD),10,'0') ");
        sql.append("   ,SUBSTR(EIGYO_DT,1,8) ");
        sql.append("   ,COUNT(STORE) ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("FROM ( ");
        sql.append("    SELECT ");
        sql.append("    DISTINCT ");
        sql.append("        STORE ");
        sql.append("       ,POS ");
        sql.append("       ,TRANS_NO ");
        sql.append("       ,EIGYO_DT ");
        sql.append("       ,CASE WHEN BUNRUI1_CD IS NULL OR DELETE_FG = '1' THEN ? ELSE BUNRUI1_CD END AS BUNRUI1_CD ");
        sql.append("    FROM ( ");
        sql.append("        SELECT ");
        sql.append("            DPAI.STORE      AS STORE ");
        sql.append("           ,DPAI.POS        AS POS ");
        sql.append("           ,DPAI.TRANS_NO   AS TRANS_NO ");
        sql.append("           ,DPAI.SKU        AS SKU ");
        sql.append("           ,DPAI.EIGYO_DT   AS EIGYO_DT ");
        sql.append("           ,MAX(RS.YUKO_DT) AS YUKO_DT ");
        sql.append("           ,RS.BUNRUI1_CD   AS BUNRUI1_CD ");
        sql.append("           ,RS.DELETE_FG    AS DELETE_FG ");
        sql.append("        FROM DT_POS_A_ITEM DPAI ");
        sql.append("        LEFT OUTER JOIN R_SYOHIN RS ");
        sql.append("        ON  DPAI.SKU = RS.SYOHIN_CD AND ");
        sql.append("            DPAI.EIGYO_DT >= RS.YUKO_DT ");
        sql.append("        WHERE DPAI.DATA_SAKUSEI_DT = ? AND ");
        //#4501 Mod X.Liu 2017.03.31 (S)
//        sql.append("            ( DPAI.ATYPE = '0001' OR DPAI.ATYPE = '1011' ) ");
        sql.append("              DPAI.COMMAND = '0043' ");
        //#4501 Mod X.Liu 2017.03.31 (E)
        sql.append("        GROUP BY ");
        sql.append("            DPAI.STORE ");
        sql.append("           ,DPAI.POS ");
        sql.append("           ,DPAI.TRANS_NO ");
        sql.append("           ,DPAI.SKU ");
        sql.append("           ,DPAI.EIGYO_DT ");
        sql.append("           ,RS.BUNRUI1_CD ");
        sql.append("           ,RS.DELETE_FG ");
        sql.append("        ) ");
        sql.append("    ) ");
        sql.append("GROUP BY ");
        sql.append("    STORE ");
        sql.append("   ,BUNRUI1_CD ");
        sql.append("   ,EIGYO_DT ");
        //2017/01/10 J.Endo 性能改善(#3586) MOD(E)

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }
}
