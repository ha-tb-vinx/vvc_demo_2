package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: JikantaiKyakusuRuisekiDataCopyDao クラス</p>
 * <p>説明:時間帯客数累積データコピー</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.06.27) k.Hyo FIVI対応
 * @see なし
 */
public class JikantaiKyakusuRuisekiDataCopyDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "時間帯客数累積データコピー";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB013360";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /** 削除SQL */
    //private static final String DEL_SQL = "TRUNCATE TABLE DT_JIKANTAI_KYAKUSU_RUISEKI_ZN";
    private static final String DEL_SQL = "TRUNCATE TABLE DT_JIKANTAI_KYAKUSU_RUISEKI_ZN";
    
    /**
     * 発行済VATINVOICE情報累積
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {
        
        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {
            
            // 時間帯客数累積データ（前回）を削除する
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDel.execute();

            
            String dbServerTime = FiResorceUtility.getDBServerTime();
            //売上INVOICE管理明細データ登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getJikantaiKyakusuRuisekiDataCopyDaoSql());

            preparedStatementExIns.setString(1, BATCH_ID);
            preparedStatementExIns.setString(2, BATCH_DT);
            preparedStatementExIns.setString(3, BATCH_ID);
            preparedStatementExIns.setString(4, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件の時間帯客数累積データ（前回）を追加しました。");

            // APPEND INSERTした内容確定する必要があるのでcommitを行う
            invoker.getDataBase().commit();
            
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
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * 時間帯客数累積データ（前回）登録するSQLを取得する
     *
     * @return 時間帯客数累積データコピー
     */
    private String getJikantaiKyakusuRuisekiDataCopyDaoSql( ) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT /*+ APPEND */ INTO DT_JIKANTAI_KYAKUSU_RUISEKI_ZN( ");
        sql.append("     TENPO_CD ");
        sql.append("    ,KYAKU_SYUKEI_TANI ");
        sql.append("    ,KYAKU_SYUKEI_CD ");
        sql.append("    ,HHMM ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,KEIJYO_DT ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("SELECT");
        sql.append("     DJKR.TENPO_CD ");
        sql.append("    ,DJKR.KYAKU_SYUKEI_TANI ");
        sql.append("    ,DJKR.KYAKU_SYUKEI_CD ");
        sql.append("    ,DJKR.HHMM ");   
        sql.append("    ,DJKR.KYAKU_QT ");
        sql.append("    ,DJKR.KEIJYO_DT ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM                ");
        sql.append("    DT_JIKANTAI_KYAKUSU_RUISEKI DJKR "); 
        return sql.toString();
    }

    /**
     * アウトプットBeanを取得する
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * インプットBeanを設定する
     * @param Object input
     */
    public void setInputObject(Object input) throws Exception {
    
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new VatInvoiceRegistDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
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
