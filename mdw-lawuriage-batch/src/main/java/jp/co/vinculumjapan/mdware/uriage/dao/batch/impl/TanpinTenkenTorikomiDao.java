package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;


/**
 * <p>タイトル: TanpinTenkenTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(単品点検)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.18) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2015.08.28) NGUYEN.NTM FIVImart様対応
 * @Version 3.03 (2015.11.10) NGUYEN.NTM FIVImart様対応
 * @Version 3.04 (2016.05.20) Hirata FIVImart様対応
 * @Version 3.05 (2016.09.30) Y.Itaki FIVImart様対応(#2196)
 * @Version 3.06 (2023.02.24) TUNG.LT #6733 MKH対応
 *
 */
public class TanpinTenkenTorikomiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理(単品点検)";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMP単品点検データ";

    // 削除SQL文用定数
    private static final String DEL_SQL = "deleteTmpTanpinTenken";
    // 追加SQL文用定数
    private static final String INS_SQL = "insertTmpTanpinTenken";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDelete = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // SQLを取得し、TMPテーブルを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            // SQLを取得し、パラメータを条件にバインドする
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(S)
            //preparedStatementExIns = invoker.getDataBase().prepareStatement(INS_SQL);
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpTanpinTenkenInsertSql());
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(E)

            int index = 1;
            preparedStatementExIns.setString(index++, COMP_CD);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            preparedStatementExIns.setString(index++, userId);
            preparedStatementExIns.setString(index++, dbServerTime);
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) ADD(S)
            preparedStatementExIns.setString(index++, BATCH_DT);
            // 2016/09/30 VINX Y.Itaki FIVI(#2196) ADD(E)
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
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
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
     * TMP単品点検データ登録用SQLを取得する
     *
     * @return TMP単品点検データ登録用SQL
     */
    private String getTmpTanpinTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_TANPIN_TENKEN ( ");
        sql.append("     COMP_CD ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,JIKANTAI_NO ");
        sql.append("    ,URIAGE_QT ");
        sql.append("    ,URIAGE_VL ");
        sql.append("    ,NEBIKI_REGI_QT ");
        sql.append("    ,NEBIKI_REGI_VL ");
        sql.append("    ,ARARI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     ? ");
        sql.append("    ,TO_CHAR(TO_DATE(KEIJYO_DT, 'yyyy/mm/dd'), 'yyyy/mm/dd') ");
        sql.append("    ,LPAD(RTRIM(TENPO_CD), 6, '0') ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,SUBSTR(HHMM, 1, 2) ");
        sql.append("    ,SUM(URIAGE_QT) ");
        sql.append("    ,SUM(URIAGE_VL) ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
        sql.append("    ,0 ");
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
                            //マスタの旧商品コードと一致したら置き換え、それ以外はそのまま移送
        sql.append("        ,COALESCE(R.SYOHIN_CD,TJF.SYOHIN_CD) AS SYOHIN_CD ");
        sql.append("        ,HHMM ");
        sql.append("        ,URIAGE_QT ");
        sql.append("        ,URIAGE_VL ");
        sql.append("    FROM ");
        sql.append("        TMP_JIKANTAI_FORMAT1 TJF ");
                        //旧商品コードが一致するデータをLEFT JOINで結合(COALESCEで使うため)
        sql.append("    LEFT OUTER JOIN ");
                            //マスタの旧商品コード
        sql.append("        ( ");
        sql.append("        SELECT ");
        sql.append("            R_1.SYOHIN_CD ");
        sql.append("            ,R_1.OLD_SYOHIN_CD ");
        sql.append("        FROM ");
        sql.append("            R_SYOHIN R_1 ");
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
        sql.append("            ) R_2 ");
        sql.append("        ON ");
        sql.append("            R_1.SYOHIN_CD = R_2.SYOHIN_CD AND ");
        sql.append("            R_1.YUKO_DT = R_2.YUKO_DT ");
        sql.append("        GROUP BY ");
        sql.append("            R_1.SYOHIN_CD ");
        sql.append("            ,R_1.OLD_SYOHIN_CD ");
        sql.append("        ) R ");
        sql.append("    ON ");
                            //桁数合わせ後、比較
        sql.append("        LPAD(TRIM(TJF.SYOHIN_CD),13,'0') = LPAD(TRIM(R.OLD_SYOHIN_CD),13,'0') ");
        sql.append("    ) ");
        // 2016/09/30 VINX Y.Itaki FIVI(#2196) MOD(E)
        // #6733 ADD 2023.02.24 TUNG.LT (S)
        sql.append("WHERE ");
        sql.append("     TO_CHAR(TO_DATE(KEIJYO_DT, 'yyyy/mm/dd'), 'yyyymmdd') = ?  ");
        // #6733 ADD 2023.02.24 TUNG.LT (E)
        sql.append("GROUP BY ");
        sql.append("     KEIJYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,SUBSTR(HHMM, 1, 2) ");


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
            DaoIf dao = new TanpinTenkenTorikomiDao();
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
