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
 * <p>タイトル: JikantaiDataKyakusuRuisekiDao クラス</p>
 * <p>説明:時間帯データ客数累積</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.06.24) k.Hyo FIVI対応
 * @Version 1.10 (2022.11.30) TUNG.LT #6706 MKH対応
 * @see なし
 */
public class JikantaiDataKyakusuRuisekiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "時間帯データ客数累積";  
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    
    // バッチID
    private static final String BATCH_ID = "URIB013340";
    
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    /**
     * 時間帯データ客数累積
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
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {
            
            String dbServerTime = FiResorceUtility.getDBServerTime();
            //売上INVOICE管理明細データ登録
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getJikantaiDataKyakusuRuisekiSql());
            
            preparedStatementExIns.setString(1, BATCH_ID);
            preparedStatementExIns.setString(2, BATCH_DT);
            preparedStatementExIns.setString(3, BATCH_ID);
            preparedStatementExIns.setString(4, BATCH_DT);

            // #6706 DEL 2022.11.22 TUNG.LT (S)
            // preparedStatementExIns.setString(5, BATCH_ID);
            // preparedStatementExIns.setString(6, BATCH_DT);
            // preparedStatementExIns.setString(7, BATCH_ID);
            // preparedStatementExIns.setString(8, BATCH_DT);
            // #6706 DEL 2022.11.22 TUNG.LT (E)
            
            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件の時間帯データ客数累積を追加しました。");
            
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
     * 時間帯データ客数累積登録するSQLを取得する
     *
     * @return 時間帯データ客数累積登録
     */
    private String getJikantaiDataKyakusuRuisekiSql( ) {
        StringBuilder sql = new StringBuilder();
   
        sql.append("INSERT /*+ APPEND */ INTO DT_JIKANTAI_KYAKUSU_RUISEKI( ");
        sql.append("    TENPO_CD ");
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
        sql.append("    TJF.TENPO_CD ");
        sql.append("    ,TJF.KYAKU_SYUKEI_TANI ");
        sql.append("    ,TJF.KYAKU_SYUKEI_CD ");
        sql.append("    ,TJF.HHMM ");
        sql.append("    ,TJF.KYAKU_QT ");
        sql.append("    ,TJF.KEIJYO_DT ");
        sql.append("    ,? ");
        sql.append("    ,? ");  
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM                ");
        sql.append("    TMP_JIKANTAI_FORMAT2 TJF ");
        // #6706 MOD 2022.11.22 TUNG.LT (S)
        // sql.append("WHERE NOT EXISTS ");
        // sql.append("    (  ");                
        // sql.append("        SELECT ");
        // sql.append("            TJF.TENPO_CD ");
        // sql.append("            ,TJF.KYAKU_SYUKEI_TANI ");
        // sql.append("            ,TJF.KYAKU_SYUKEI_CD ");
        // sql.append("            ,TJF.HHMM ");
        // sql.append("            ,TJF.KYAKU_QT ");
        // sql.append("            ,TJF.KEIJYO_DT ");
        // sql.append("            ,? ");
        // sql.append("            ,? ");  
        // sql.append("            ,? ");
        // sql.append("            ,? ");
        // sql.append("        FROM ");  
        // sql.append("            DT_JIKANTAI_KYAKUSU_RUISEKI_ZN SUB ");
        // sql.append("        WHERE ");           
        // sql.append("            TJF.TENPO_CD = SUB.TENPO_CD ");
        // sql.append("            AND TJF.KYAKU_SYUKEI_TANI = SUB.KYAKU_SYUKEI_TANI ");
        // sql.append("            AND TJF.KYAKU_SYUKEI_CD = SUB.KYAKU_SYUKEI_CD ");
        // sql.append("            AND TJF.HHMM = SUB.HHMM ");
        // sql.append("            AND TJF.KEIJYO_DT = SUB.KEIJYO_DT) ");
        sql.append("LEFT JOIN DT_JIKANTAI_KYAKUSU_RUISEKI_ZN SUB   ");
        sql.append("ON TJF.TENPO_CD        = SUB.TENPO_CD   ");
        sql.append("   AND TJF.KYAKU_SYUKEI_TANI = SUB.KYAKU_SYUKEI_TANI   ");
        sql.append("   AND TJF.KYAKU_SYUKEI_CD   = SUB.KYAKU_SYUKEI_CD   ");
        sql.append("   AND TJF.HHMM              = SUB.HHMM   ");
        sql.append("   AND TJF.KEIJYO_DT         = SUB.KEIJYO_DT   ");
        sql.append("WHERE SUB.KEIJYO_DT IS NULL   ");
        // #6706 MOD 2022.11.22 TUNG.LT (E)
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
