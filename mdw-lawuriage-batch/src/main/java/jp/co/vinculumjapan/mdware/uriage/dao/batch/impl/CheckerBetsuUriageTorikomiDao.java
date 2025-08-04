package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.CheckerBetsuUriageTorikomiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: CheckerBetsuUriageTorikomiDao クラス</p>
 *  <P>説明: チェッカー別売上取込処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 *  @Version 3.00 (2013.10.11) S.Arakawa [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応 SQLLoaderを使用した取込処理から、責任者精算データより作成する処理に修正
 *  @Version 3.01 (2013.10.27) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №042
 *  @Version 3.02 (2016.05.11) monden S03対応
 *  @Version 3.03 (2016.06.1) to #1481対応
 */
public class CheckerBetsuUriageTorikomiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "チェッカー別売上取込処理";
    // 更新対象テーブル名 
    private static final String TABLE_NAME = "WK_CHECKER_SEISAN_URI";
    
    // 入力ビーン
    private CheckerBetsuUriageTorikomiDaoInputBean inputBean = null;
    
 // 2013.10.11 S.Arakawa SQLLoaderを使用した取込処理から、責任者精算データより作成する処理へ変更対応（S)
    // バッチID
    private static final String BATCH_ID = "URIB051010";
    // バッチ日付
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

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
        invoker.infoLog(strUserID + "　：　チェッカー別売上取込処理を開始します。");
        
        // 「チェッカー精算売上ワーク」のデータを全件削除
        psDelCheckerUriWork = invoker.getDataBase().prepareStatement(getDeleteCheckerSeisanUriageWorkSQL());
        psDelCheckerUriWork.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー精算売上ワークへのデータ取込処理を開始します。");
        
        // 「チェッカー精算売上ワーク」取込処理SQL文取得
        psInsCheckerUriWork = invoker.getDataBase().prepareStatement(getInsertCheckerSeisanUriageWorkSQL());
        
        // バインドパラメータ設定
        psInsCheckerUriWork.setString(index++, BATCH_ID);
        psInsCheckerUriWork.setString(index++, DBSERVER_DT);
        psInsCheckerUriWork.setString(index++, BATCH_ID);
        psInsCheckerUriWork.setString(index++, DBSERVER_DT);
        psInsCheckerUriWork.setString(index++, COMP_CD);
        psInsCheckerUriWork.setString(index++, BATCH_DT);
        // 2016/06/1 VINX #1481対応（S)
//        psInsCheckerUriWork.setString(index++, FiResorceUtility.getPropertie(UriResorceKeyConstant.DAY_NET_TORIHIKI_CD));
        // 2016/06/1 VINX #1481対応（E)
        // SQL文実行して、処理件数取得
        insCount = psInsCheckerUriWork.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　チェッカー精算売上ワークへのデータ取込処理を終了します。");
        invoker.infoLog("取込件数は " + insCount  + " 件です。");
        invoker.infoLog(strUserID + "　：　チェッカー別売上取込処理を終了します。");
        
    }
    
    /**
     * チェッカー精算売上ワークのトランケートSQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    
    private String getDeleteCheckerSeisanUriageWorkSQL(){
        
        StringBuffer sql = new StringBuffer("");
        
        sql.append("TRUNCATE TABLE");
        sql.append("    WK_CHECKER_SEISAN_URI");
        
        return sql.toString();
        
    }

    /**
     * 責任者精算データ取込SQL
     * @param なし
     * @return String sql
     * @throws なし
     */
    private String getInsertCheckerSeisanUriageWorkSQL(){
        
        StringBuffer sql = new StringBuffer("");
        
        sql.append("INSERT /*+ APPEND */ INTO           ");
        sql.append("    WK_CHECKER_SEISAN_URI           ");
        sql.append("    (                               ");
        sql.append("        COMP_CD                     ");
        sql.append("       ,KEIJO_DT                    ");
        sql.append("       ,TENPO_CD                    ");
        sql.append("       ,CHECKER_CD                  ");
        sql.append("       ,CHECKER_NA                  ");
        sql.append("       ,CHECKER_KANA_NA             ");
        sql.append("       ,DAY_NET_VL                  ");
        sql.append("       ,INSERT_USER_ID              ");
        sql.append("       ,INSERT_TS                   ");
        sql.append("       ,UPDATE_USER_ID              ");
        sql.append("       ,UPDATE_TS                   ");
        // 2016/05/11 VINX #S03対応（S)
        sql.append("       ,SHIHARAI_SYUBETSU_CD        ");
        sql.append("       ,SHIHARAI_SYUBETSU_SUB_CD    ");
        sql.append("       ,DATA_SAKUSEI_DT             ");
        // 2016/05/11 VINX #S03対応（E)
        sql.append("    )                               ");
        sql.append("SELECT                              ");
        sql.append("     DSS.COMP_CD                    ");
        sql.append("    ,DSS.KEIJO_DT                   ");
        sql.append("    ,DSS.TENPO_CD                   ");
        sql.append("    ,DSS.CHECKER_CD                 ");
        sql.append("    ,NULL                           ");
        sql.append("    ,NULL                           ");
        sql.append("    ,DSS." + FiResorceUtility.getPropertie(UriResorceKeyConstant.DAY_NET_KOMOKU));
        sql.append("    ,?                              ");
        sql.append("    ,?                              ");
        sql.append("    ,?                              ");
        sql.append("    ,?                              ");
        // 2016/05/11 VINX #S03対応（S)
        sql.append("    ,SHIHARAI_SYUBETSU_CD           ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD       ");
        sql.append("    ,DATA_SAKUSEI_DT                ");
        // 2016/05/11 VINX #S03対応（E)
        sql.append("FROM                                ");
        sql.append("    DT_SEKININSYA_SEISAN DSS        ");
        sql.append("WHERE                               ");
        sql.append("    DSS.COMP_CD     = ?     AND     ");
        // 2016/05/11 VINX #S03対応（S)
        //sql.append("    DSS.KEIJO_DT    = ?     AND     ");
        // 2016/06/1 VINX #1481対応（S)
//        sql.append("    DSS.DATA_SAKUSEI_DT = ? AND     ");
        sql.append("    DSS.DATA_SAKUSEI_DT = ?      ");
        // 2016/05/11 VINX #S03対応（E)
        // 2016/06/1 VINX #1481対応（S)
//        sql.append("    DSS.TORIHIKI_CD = ?             ");
        // 2016/06/1 VINX #1481対応（E)
        return sql.toString();
    }

// 2013.10.11 S.Arakawa SQLLoaderを使用した取込処理から、責任者精算データより作成する処理へ変更対応（E)
    
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
        inputBean = (CheckerBetsuUriageTorikomiDaoInputBean) input;
        
    }
    
    public static void main(String[] arg) {
        try {
            DaoIf dao = new CheckerBetsuUriageTorikomiDao();
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
