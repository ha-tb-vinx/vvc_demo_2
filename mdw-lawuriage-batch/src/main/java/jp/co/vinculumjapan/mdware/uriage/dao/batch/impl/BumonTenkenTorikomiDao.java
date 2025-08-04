/**
 *
 */
package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.DateChanger;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;


/**
 * <p>タイトル: BumonTenkenTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(部門点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.18) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2016.05.20) Hirata FIVImart様対応
 * @Version 3.03 (2016.09.30) Y.Itaki FIVImart様対応(#2196)
 * @Version 3.04 (2017.03.23) J.Endo  FIVImart様対応(#3331)
 * @Version 3.05 (2021.09.14) SIEU.D #6339
 * @Version 3.06 (2022.08.26) SIEU.D #6644 MKH対応
 * @Version 3.07 (2022.10.04) TUNG.LT #6663 MKH対応
 * @Version 3.08 (2023.01.11) SIEU.D #6720 MKH対応
 * @Version 3.09 (2023.02.24) TUNG.LT #6733 MKH対応
 *
 */
public class BumonTenkenTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(部門点検)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "部門点検集計ワーク";

    // 追加SQL文用定数
    private static final String INS_SQL = "insertWkBumonTenkenSyukei";
    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteWkBumonTenkenSyukei";
    // 商品マスタチェックSQL文用定数
    private static final String SYOHIN_MASTER_CHECK_SQL = "checkSyohinMaster";
    
    // #6663 ADD 2022.10.11 SIEU.D (S)
    private static final String PREVIOUS_DT = DateChanger.addDate(BATCH_DT, -1);
    // #6663 ADD 2022.10.11 SIEU.D (E)

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementCheck = null;
        PreparedStatementEx preparedStatementExIns = null;
        ResultSet resultSetCheck = null;

        int insertCount = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // SQLを取得し、TMPテーブルを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // 商品マスタのチェック
            //preparedStatementCheck = invoker.getDataBase().prepareStatement(SYOHIN_MASTER_CHECK_SQL);
            //int index = 1;
            //preparedStatementCheck.setString(index++, COMP_CD);
            //resultSetCheck = preparedStatementCheck.executeQuery();

            // 商品マスタのチェック結果が存在する場合はエラー
            //if (resultSetCheck.next()) {

                // エラー処理
            //    String errorMessage = "商品マスタに該当するレコードがありません。商品コード=[" + resultSetCheck.getString("SYOHIN_CD") + "]";
            //    invoker.errorLog(errorMessage);
            //    throw new IllegalStateException(errorMessage);
            //}

            // SQLを取得し、パラメータを条件にバインドする
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(S)
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(INS_SQL);
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkBumonTenkenSyukeiInsertSql());
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(E)

            int index = 1;
            preparedStatementExIns.setString(index++, COMP_CD);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) ADD(S)
            preparedStatementExIns.setString(index++, BATCH_DT);
            preparedStatementExIns.setString(index++, BATCH_DT);
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) ADD(E)
            preparedStatementExIns.setString(index++, COMP_CD);
            // #6339 ADD 2021/09/14 SIEU.D(S) 
            preparedStatementExIns.setString(index++, BATCH_DT);
            // #6339 ADD 2021/09/14 SIEU.D(E) 
            // #6733 ADD 2023.02.24 TUNG.LT (S)
            preparedStatementExIns.setString(index++, BATCH_DT);
            // #6733 ADD 2023.02.24 TUNG.LT (E)

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (resultSetCheck != null) {
                    resultSetCheck.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("resultSet Closeエラー");
            }
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementCheck != null) {
                    preparedStatementCheck.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }

        }
    }



    /**
     * 部門点検集計ワーク登録用SQLを取得する
     *
     * @return 部門点検集計ワーク登録用SQL
     */
    private String getWkBumonTenkenSyukeiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_BUMON_TENKEN_SYUKEI ( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUMON_CD ");
        sql.append("    ,JIKANTAI_NO ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("    ,ARARI_VL ");
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("    ? ");
        sql.append("    ,TO_CHAR(TO_DATE(FORMAT1.KEIJYO_DT, 'yyyy/mm/dd'), 'yyyy/mm/dd') ");
        sql.append("    ,LPAD(RTRIM(FORMAT1.TENPO_CD), 6, '0') ");
        sql.append("    ,CASE WHEN SYOHIN.SYOHIN_CD IS NOT NULL THEN SYOHIN.BUNRUI1_CD ");
        sql.append("          ELSE ( ");
        sql.append("                    SELECT ");
        sql.append("                        SUBSTRB(TO_CHAR(PARAMETER_TX),1,4) ");
        sql.append("                    FROM ");
        sql.append("                        SYSTEM_CONTROL ");
        sql.append("                    WHERE ");
        sql.append("                        SUBSYSTEM_ID = 'URIAGE' AND ");
        sql.append("                        PARAMETER_ID = 'DUMMY_BUNRUI1_CD' ");
        sql.append("                ) ");
        sql.append("     END AS BUMON_CD ");
        // 2017/03/23 VINX J.Endo FIVI(#3331) MOD(S)
        //sql.append("    ,SUBSTR(FORMAT1.HHMM, 1, 2) ");
        sql.append("    ,FORMAT1.HHMM ");
        // 2017/03/23 VINX J.Endo FIVI(#3331) MOD(E)
        sql.append("    ,0 ");
        sql.append("    ,RTRIM(FORMAT1.URIAGE_QT) ");
        sql.append("    ,RTRIM(FORMAT1.URIAGE_VL) ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        // #6644 MOD 2022.08.26 SIEU.D (S)
        // sql.append("    ,FORMAT1.URIAGE_VL - (COALESCE(TENSYO.GENTANKA_VL, SYOHIN.GENTANKA_VL, 0) * FORMAT1.URIAGE_QT) ");
        sql.append("    ,FORMAT1.URIAGE_VL - (COALESCE(DSD.SOHEIKIN_GENTANKA_VL, TENSYO.GENTANKA_VL, SYOHIN.GENTANKA_VL, 0) * FORMAT1.URIAGE_QT) ");
        // #6644 MOD 2022.08.26 SIEU.D (E)
        // #6339 ADD 2021/09/14 SIEU.D(E) 
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(S)
                        //商品コード置き換えのため、ここからテーブル化(サマリー無し)
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        KEIJYO_DT ");
        sql.append("        ,TENPO_CD ");
                            //マスタの商品コードと一致、マスタの旧商品コードと一致、どちらとも不一致の順で移送
        sql.append("        ,COALESCE(R1.SYOHIN_CD,R2.SYOHIN_CD,TJF.SYOHIN_CD) AS SYOHIN_CD ");
        sql.append("        ,HHMM ");
        sql.append("        ,URIAGE_QT ");
        sql.append("        ,URIAGE_VL ");
        sql.append("    FROM ");
        sql.append("        TMP_JIKANTAI_FORMAT1 TJF ");
                        //商品コードと旧商品コードをそれぞれLEFT JOINで結合(COALESCEで使うため)
        sql.append("    LEFT OUTER JOIN ");
                            //マスタの商品コード
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            R1_1.SYOHIN_CD ");
        sql.append("        FROM ");
        sql.append("            R_SYOHIN R1_1 ");
        sql.append("        INNER JOIN ");
                                //有効日を絞り込み
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                SYOHIN_CD ");
        sql.append("                ,MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("            FROM ");
        sql.append("                R_SYOHIN ");
        sql.append("            WHERE ");
        sql.append("                YUKO_DT <= ? ");
        sql.append("            GROUP BY ");
        sql.append("                SYOHIN_CD ");
        sql.append("            ) R1_2 ");
        sql.append("        ON ");
        sql.append("            R1_1.SYOHIN_CD = R1_2.SYOHIN_CD AND ");
        sql.append("            R1_1.YUKO_DT = R1_2.YUKO_DT ");
        sql.append("        GROUP BY ");
        sql.append("            R1_1.SYOHIN_CD ");
        sql.append("        ) R1 ");
        sql.append("    ON ");
                            //桁数合わせ後、比較
        sql.append("        LPAD(TRIM(TJF.SYOHIN_CD),13,'0') = LPAD(TRIM(R1.SYOHIN_CD),13,'0') ");
        sql.append("    LEFT OUTER JOIN ");
                            //マスタの旧商品コード
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            R2_1.SYOHIN_CD ");
        sql.append("            ,R2_1.OLD_SYOHIN_CD ");
        sql.append("        FROM ");
        sql.append("            R_SYOHIN R2_1 ");
        sql.append("        INNER JOIN ");
                                //有効日を絞り込み
        sql.append("            ( ");
        sql.append("            SELECT ");
        sql.append("                SYOHIN_CD ");
        sql.append("                ,MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("            FROM ");
        sql.append("                R_SYOHIN ");
        sql.append("            WHERE ");
        sql.append("                YUKO_DT <= ? ");
        sql.append("            GROUP BY ");
        sql.append("                SYOHIN_CD ");
        sql.append("            ) R2_2 ");
        sql.append("        ON ");
        sql.append("            R2_1.SYOHIN_CD = R2_2.SYOHIN_CD AND ");
        sql.append("            R2_1.YUKO_DT = R2_2.YUKO_DT ");
        sql.append("        GROUP BY ");
        sql.append("            R2_1.SYOHIN_CD ");
        sql.append("            ,R2_1.OLD_SYOHIN_CD ");
        sql.append("        ) R2 ");
        sql.append("    ON ");
                            //桁数合わせ後、比較
        sql.append("        LPAD(TRIM(TJF.SYOHIN_CD),13,'0') = LPAD(TRIM(R2.OLD_SYOHIN_CD),13,'0') ");
        sql.append("    )FORMAT1 ");
        // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(E)
        sql.append("LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        SYOHIN.* ");
        sql.append("    FROM ");
        sql.append("        R_FI_SYOHIN SYOHIN ");
        sql.append("    INNER JOIN ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            COMP_CD, SYOHIN_CD ");
        sql.append("            ,MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("        FROM ");
        sql.append("            R_FI_SYOHIN SYOHIN ");
        sql.append("        GROUP BY ");
        sql.append("            COMP_CD ");
        sql.append("            ,SYOHIN_CD ");
        sql.append("        ) MAX_YUKO_DT ");
        sql.append("    ON ");
        sql.append("        MAX_YUKO_DT.COMP_CD = SYOHIN.COMP_CD AND ");
        sql.append("        MAX_YUKO_DT.SYOHIN_CD = SYOHIN.SYOHIN_CD AND ");
        sql.append("        MAX_YUKO_DT.YUKO_DT = SYOHIN.YUKO_DT ");
        sql.append("    )SYOHIN ");
        sql.append("ON ");
        sql.append("    SYOHIN.COMP_CD = ? AND ");
        sql.append("    SYOHIN.SYOHIN_CD = FORMAT1.SYOHIN_CD ");
        // #6339 ADD 2021/09/14 SIEU.D(S) 
        sql.append("LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("        TENSYO.* ");
        sql.append("    FROM ");
        sql.append("        R_TENSYOHIN_REIGAI TENSYO ");
        sql.append("    INNER JOIN ");
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            BUNRUI1_CD ");
        sql.append("            , SYOHIN_CD ");
        sql.append("            , TENPO_CD ");
        sql.append("            , MAX(YUKO_DT) AS YUKO_DT ");
        sql.append("        FROM ");
        sql.append("            R_TENSYOHIN_REIGAI TENSYO ");
        sql.append("        WHERE  ");
        sql.append("            YUKO_DT < ? ");
        sql.append("        GROUP BY ");
        sql.append("            BUNRUI1_CD ");
        sql.append("            ,SYOHIN_CD ");
        sql.append("            ,TENPO_CD ");
        sql.append("        ) MAX_YUKO_DT ");
        sql.append("    ON ");
        // #6620 DEL 2022.11.18 VU.TD (S)
//        sql.append("        MAX_YUKO_DT.BUNRUI1_CD = TENSYO.BUNRUI1_CD AND ");
        // #6620 DEL 2022.11.18 VU.TD (E)
        sql.append("        MAX_YUKO_DT.SYOHIN_CD = TENSYO.SYOHIN_CD AND ");
        sql.append("        MAX_YUKO_DT.TENPO_CD = TENSYO.TENPO_CD AND ");
        sql.append("        MAX_YUKO_DT.YUKO_DT = TENSYO.YUKO_DT ");
        sql.append("    ) TENSYO ");
        sql.append("ON ");
        sql.append("    TENSYO.SYOHIN_CD = FORMAT1.SYOHIN_CD ");
        sql.append("    AND TENSYO.TENPO_CD = LPAD(RTRIM(FORMAT1.TENPO_CD), 6, '0') ");
        //#6720 ADD 2023.01.11 SIEU.D (S)
        sql.append("    AND TENSYO.BUNRUI1_CD = SYOHIN.BUNRUI1_CD ");
        //#6720 ADD 2023.01.11 SIEU.D (E)
        // #6339 ADD 2021/09/14 SIEU.D(E)
        // #6644 ADD 2022.08.26 SIEU.D (S)
      sql.append("LEFT OUTER JOIN ");
      sql.append("    ( ");
      sql.append("    SELECT ");
      sql.append("        MAIN.TENPO_CD ");
      sql.append("        , MAIN.SYOHIN_CD ");
      sql.append("        , MAIN.SOHEIKIN_GENTANKA_VL ");
      sql.append("    FROM ");
      // #6663 ADD 2022.08.26 TUNG.LT (S)
      // sql.append("        DT_SM_DT_").append(BATCH_DT.substring(0, 6)).append("@MDINFO_SYOHINJISEKI MAIN ");
      sql.append("        DT_SM_DT_").append(PREVIOUS_DT.substring(0, 6)).append("@MDINFO_SYOHINJISEKI MAIN ");
      // #6663 ADD 2022.08.26 TUNG.LT (E)
      sql.append("    INNER JOIN ");
      sql.append("        ( ");
      sql.append("        SELECT ");
      sql.append("            TENPO_CD ");
      sql.append("            , SYOHIN_CD ");
      sql.append("            , MAX(KEIJO_DT) AS KEIJO_DT ");
      sql.append("        FROM ");
      // #6663 ADD 2022.08.26 TUNG.LT (S)
      // sql.append("            DT_SM_DT_").append(BATCH_DT.substring(0, 6)).append("@MDINFO_SYOHINJISEKI ");
      sql.append("            DT_SM_DT_").append(PREVIOUS_DT.substring(0, 6)).append("@MDINFO_SYOHINJISEKI ");
      // #6663 ADD 2022.08.26 TUNG.LT (E)
      sql.append("        WHERE TENPO_SM_KB = '9' ");
      sql.append("        GROUP BY ");
      sql.append("            TENPO_CD ");
      sql.append("            , SYOHIN_CD ");
      sql.append("        ) SUB ");
      sql.append("    ON ");
      sql.append("        MAIN.SYOHIN_CD = SUB.SYOHIN_CD AND ");
      sql.append("        MAIN.TENPO_CD = SUB.TENPO_CD AND ");
      sql.append("        MAIN.KEIJO_DT = SUB.KEIJO_DT ");
      sql.append("    ) DSD ");
      sql.append("ON ");
      sql.append("    DSD.SYOHIN_CD = FORMAT1.SYOHIN_CD ");
      sql.append("    AND DSD.TENPO_CD = LPAD(RTRIM(FORMAT1.TENPO_CD), 6, '0') ");
        // #6644 ADD 2022.08.26 SIEU.D (E)
      // #6733 ADD 2023.02.24 TUNG.LT (S)
      sql.append("WHERE ");
      sql.append("    TO_CHAR(TO_DATE(FORMAT1.KEIJYO_DT, 'yyyy/mm/dd'), 'yyyymmdd') = ? ");
      // #6733 ADD 2023.02.24 TUNG.LT (E)

        return sql.toString();
    }



    /**
     * インプットBeanを設定する
     *
     * @param Object
     */
    public void setInputObject(Object input) throws Exception {
    }

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new BumonTenkenTorikomiDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
