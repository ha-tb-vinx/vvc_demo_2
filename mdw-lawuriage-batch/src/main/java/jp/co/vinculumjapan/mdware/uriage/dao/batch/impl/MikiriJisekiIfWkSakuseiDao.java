package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * 
 * <p>タイトル: MikiriJisekiIfWkSakuseiDao.java クラス</p>
 * <p>説明　: 見切実績データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.10.09) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 *
 */
public class MikiriJisekiIfWkSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    /** 見切実績用値上下詳細区分マップ */
    private static final Map MIKIRI_NEJOGE_SYOSAI_KB_MAP = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.MIKIRI_NEJOGE_SYOSAI_KB);

    /** バッチ処理名 */
    private static final String DAO_NAME = "見切実績データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "IF見切実績ワーク";
    /** 削除SQL */
    private static final String DEL_SQL = "DELETE FROM IF_WK_MIKIRI_JISEKI";

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {

            // IF見切実績ワークを削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 単品精算データからPOS値上下ワークを登録する(MM値引)
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getIfWkMikiriJisekiInsertSql());
            preparedStatementExIns.setString(1, dbServerTime);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);
            preparedStatementExIns.setString(6, COMP_CD);
            preparedStatementExIns.setString(7, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementDel != null) {
                    preparedStatementDel.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * POS値上下ワークからIF見切実績ワークを登録するSQLを取得する
     * 
     * @return IF見切実績ワーク登録SQL
     */
    private String getIfWkMikiriJisekiInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */INTO IF_WK_MIKIRI_JISEKI( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,MIKIRI_SURYOU_QT ");
        sql.append("    ,MIKIRI_KINGAKU_VL ");
        sql.append("    ,IF_INSERT_TS ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     WNP.COMP_CD ");
        sql.append("    ,WNP.KANRI_DT ");
        sql.append("    ,WNP.TENPO_CD ");
        sql.append("    ,WNP.SYOHIN_CD ");
        sql.append("    ,SUM(WNP.SURYO_QT) ");
        sql.append("    ,SUM(WNP.BAIKA_ZEIKOMI_VL) ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    WK_NEJOGE_POS WNP ");
        sql.append("WHERE ");
        sql.append("    WNP.COMP_CD          = ?    AND ");
        sql.append("    WNP.KANRI_DT         = ?    ");

        if (!MIKIRI_NEJOGE_SYOSAI_KB_MAP.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = MIKIRI_NEJOGE_SYOSAI_KB_MAP.entrySet().iterator(); it.hasNext();){
                Map.Entry mikiriNejogeSyosaiKb = (Map.Entry)it.next();
                buff.append(",'" + mikiriNejogeSyosaiKb.getKey() + "'");
            }

            sql.append(" AND ");
            sql.append("    WNP.RIYU_SYOSAI_KB  IN (");
            sql.append(buff.substring(1));
            sql.append(") ");
        }

        sql.append("GROUP BY ");
        sql.append("     WNP.COMP_CD ");
        sql.append("    ,WNP.KANRI_DT ");
        sql.append("    ,WNP.TENPO_CD ");
        sql.append("    ,WNP.SYOHIN_CD ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * 
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
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
            DaoIf dao = new MikiriJisekiIfWkSakuseiDao();
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
