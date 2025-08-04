package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
 * <p>タイトル: TanpinTenkenUriagegaiNyukinJogaiDao.java クラス</p>
 * <p>説明　: 売上外入金除外処理（単品点検）</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.26) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.22) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №003, №004
 * @Version 3.02 (2013.10.23) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №008
 *
 */
public class TanpinTenkenUriagegaiNyukinJogaiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /** バッチ処理名 */
    private static final String DAO_NAME = "売上外入金除外処理（単品点検）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "単品点検ワーク";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE WK_TANPIN_TENKEN";
    /** 売上外単品コード */
    private static final Map URIAGEGAI_TANPIN_CD_MAP = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.URIAGEGAI_TANPIN_CD);


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
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;

        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkTanpinTenkenInsertSql());

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, FiResorceUtility.getDBServerTime());
            preparedStatementExIns.setString(5, COMP_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception e2) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");

    }


    /**
     * 単品点検ワーク登録用SQLを取得する
     * 
     * @return 単品点検ワーク登録用SQL
     */
    private String getWkTanpinTenkenInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO WK_TANPIN_TENKEN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,KEIJO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,TANPIN_CD ");
        sql.append("    ,TIME_NO ");
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
        sql.append("     TTT.COMP_CD ");
        sql.append("    ,TTT.EIGYO_DT ");
        sql.append("    ,TTT.TENPO_CD ");
        sql.append("    ,TTT.SYOHIN_CD ");
        sql.append("    ,TTT.JIKANTAI_NO ");
        sql.append("    ,TTT.URIAGE_QT ");
        sql.append("    ,TTT.URIAGE_VL ");
        sql.append("    ,TTT.NEBIKI_REGI_QT ");
        sql.append("    ,TTT.NEBIKI_REGI_VL ");
        sql.append("    ,TTT.ARARI_VL ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    TMP_TANPIN_TENKEN TTT ");
        sql.append("WHERE ");
        sql.append("    TTT.COMP_CD = ? ");

        if (!URIAGEGAI_TANPIN_CD_MAP.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №003, №004（S)
            for (Iterator it = URIAGEGAI_TANPIN_CD_MAP.entrySet().iterator(); it.hasNext();) {
                Map.Entry uriageTanpinCd = (Map.Entry) it.next();
                if (!StringUtils.isEmpty(((String) uriageTanpinCd.getKey()).trim())) {
                    buff.append(",'" + uriageTanpinCd.getValue() + "'");
                }
            }

            if (buff.length() > 0) {
                sql.append("    AND TTT.SYOHIN_CD NOT IN (");
                sql.append(buff.substring(1));
                sql.append(")");
            }
            // 2013.10.22 T.Ooshiro [CUS00057] 結合テスト課題対応 №003, №004（E)
        }

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
            DaoIf dao = new TanpinTenkenUriagegaiNyukinJogaiDao();
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
