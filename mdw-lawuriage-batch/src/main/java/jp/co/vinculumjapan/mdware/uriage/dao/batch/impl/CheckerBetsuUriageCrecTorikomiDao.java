package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: CheckerBetsuUriageCrecTorikomiDao クラス</p>
 * <p>説明: チェッカー別売上Crec取込処理</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.11.14) VINX J.Endo FIVI対応 新規作成
 * @Version 1.01 (2017.08.29) VINX N.Katou #5840 
 */
public class CheckerBetsuUriageCrecTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "チェッカー別売上Crec取込処理";
    // バッチID
    private static final String BATCH_ID = "URIB051030";
    // バッチ日付
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * チェッカー別売上取込処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // オブジェクトを初期化する
        PreparedStatementEx psDelCheckerUriWork = null;
        PreparedStatementEx psInsCheckerUriWork = null;

        // バインドパラメータインデックス
        int index = 1;
        // 取込件数カウント
        int insCount = 0;

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー別売上Crec取込処理を開始します。");

        // 「チェッカー精算売上ワーク」のデータを全件削除
        psDelCheckerUriWork = invoker.getDataBase().prepareStatement(getDeleteCheckerSeisanUriageWorkSQL());
        psDelCheckerUriWork.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー精算売上Crecワークへのデータ取込処理を開始します。");

        // 「チェッカー精算売上ワーク」取込処理SQL文取得
        psInsCheckerUriWork = invoker.getDataBase().prepareStatement(getInsertCheckerSeisanUriageWorkSQL());

        // バインドパラメータ設定
        psInsCheckerUriWork.setString(index++, BATCH_ID);
        psInsCheckerUriWork.setString(index++, FiResorceUtility.getDBServerTime());
        psInsCheckerUriWork.setString(index++, BATCH_ID);
        psInsCheckerUriWork.setString(index++, FiResorceUtility.getDBServerTime());
        psInsCheckerUriWork.setString(index++, COMP_CD);
        psInsCheckerUriWork.setString(index++, BATCH_DT);
        // SQL文実行して、処理件数取得
        insCount = psInsCheckerUriWork.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー精算売上Crecワークへのデータ取込処理を終了します。");
        invoker.infoLog("取込件数は " + insCount  + " 件です。");
        invoker.infoLog(strUserID + "　：　チェッカー別売上Crec取込処理を終了します。");
    }

    /**
     * チェッカー精算売上CrecワークのトランケートSQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getDeleteCheckerSeisanUriageWorkSQL(){
        StringBuffer sql = new StringBuffer("");

        sql.append("TRUNCATE TABLE ");
        sql.append("    WK_CHECKER_SEISAN_URI_C ");

        return sql.toString();
    }

    /**
     * 責任者精算Crecデータ取込SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getInsertCheckerSeisanUriageWorkSQL(){
        StringBuffer sql = new StringBuffer("");

        sql.append("INSERT INTO WK_CHECKER_SEISAN_URI_C ( ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,CHECKER_NA ");
        sql.append("   ,CHECKER_KANA_NA ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    COMP_CD ");
        sql.append("   ,KEIJO_DT ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,CHECKER_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,SHIHARAI_VL ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (S)
        sql.append("   ,RECEIPT_QT ");
        sql.append("   ,SYUUKIN_VL ");
        sql.append("   ,CYOUKA_VL ");
        // 2017.08.29 VINX N.Katou FIVI対応 #5840 (E)
        sql.append("FROM ");
        sql.append("    DT_SEKININSYA_SEISAN_C ");
        sql.append("WHERE ");
        sql.append("    COMP_CD = ? AND ");
        sql.append("    DATA_SAKUSEI_DT = ? ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new CheckerBetsuUriageCrecTorikomiDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
