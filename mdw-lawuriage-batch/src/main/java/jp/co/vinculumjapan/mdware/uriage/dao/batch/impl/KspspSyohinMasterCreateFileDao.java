package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.DPTBetsuUriageRuisekiDataSyukeiDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.batch.input.KspspSyohinMasterCreateFileDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.impl.CsvExportPreparedDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.CsvExportPreparedInputBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: KspspSyohinMasterCreateFileDao クラス</p>
 *  <P>説明: KSPSP用商品マスタファイル生成処理 </p>
 *  <P>著作権: Copyright (c) 2011</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author S.Umemoto 
 *  @version 1.0 
 */
public class KspspSyohinMasterCreateFileDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "KSPSP用商品マスタファイル生成処理";
    
    // 「/」
    private static final String SOLIDUS = "/";
    
    // 送信元ディレクトリ
    private static final String PATH_SEND = FiResorceUtility.getPropertie(UriResorceKeyConstant.PATH_RECV_POS_NCR);
    
    // 検索会計商品マスタSQL文用定数
    private static final String create_Syohin_Master = "createSyohinMaster";
    
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    
    // 入力ビーン
    private KspspSyohinMasterCreateFileDaoInputBean inputBean = null;
    
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // KSPSP用商品マスタ生成件数
        int intCreateCount = 0;
        
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // CSVファイル名を取得する
        String strCSVFileName = inputBean.getcsvFileName();

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // CSV出力用
        CsvExportPreparedDao csvExportDao = new CsvExportPreparedDao();
        CsvExportPreparedInputBean csvInputBean = new CsvExportPreparedInputBean();

        // ログ出力
        invoker.infoLog(strUserID + "　：　KSPSP用商品マスタファイル生成処理を開始します。");
        invoker.infoLog(strUserID + "　：　KSPSP用商品マスタ:ファイル作成処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(create_Syohin_Master);

        // バッチ日付
        preparedStatementEx.setString(1, BATCH_DT);

        // CSV出力用SQL文をセット
        csvInputBean.setSqlStatement(preparedStatementEx);
        
        // CSV出力パスをセット
        csvInputBean.setExportFilePath(PATH_SEND + SOLIDUS + strCSVFileName);

        // 入力ビーンをセット
        csvExportDao.setInputObject(csvInputBean);

        invoker.InvokeDao(csvExportDao);

        // データ生成件数をセットする
        intCreateCount = Integer.parseInt(csvExportDao.getOutputObject().toString());

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件のKSPSP用商品マスタを作成致しました。");
        invoker.infoLog(strUserID + "　：　KSPSP用商品マスタファイル生成処理を終了します。");
   }

    public Object getOutputObject() throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    public void setInputObject(Object input) throws Exception {
        inputBean = (KspspSyohinMasterCreateFileDaoInputBean) input;
    }

}
