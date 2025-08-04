package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.common.bean.CalculateTaxBean;
import jp.co.vinculumjapan.mdware.common.util.CalcUtility;
import jp.co.vinculumjapan.mdware.common.util.CalculateTax;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.mdware.common.util.dictionary.FractionDigitDic;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 *  <P>タイトル: DPTUriageZeiKeisanDao クラス</p>
 *  <P>説明: DPT売上税計算処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 (2014.12.04) chou グローバル化対応 通貨対応
 */
public class DPTUriageZeiKeisanDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "DPT売上税計算処理";
    // バッチID
    private static final String BATCH_ID = "URIB131010";
    // 税率
    private static final String TAX_RT = "TAX_RT";
    // 売上金額（税込）
    private static final String URIAGE_KOMI_VL = "URIAGE_KOMI_VL";
    // 税区分
    private static final String ZEI_KB = "ZEI_KB";
    // 計上日
    private static final String KEIJO_DT = "KEIJO_DT";
    // 店舗コード
    private static final String TENPO_CD = "TENPO_CD";
    // DPTコード
    private static final String BUNRUI1_CD = "BUNRUI1_CD";
    // 消費税額
    private static final String SYOHIZEI_VL = "SYOHIZEI_VL";
    // 精算金額
    private static final String SEISAN_VL = "SEISAN_VL";
    // 検索店別DPT売上SQL文用定数
    private static final String SEL_TEIBETSU_DPT_URI = "selectTeibetsuDPTUriage";
    // 更新店別DPT売上SQL文用定数
    private static final String UPD_TEIBETSU_DPT_URI = "updateTeibetsuDPTUriage";
    // 検索店別DPT売上SQL文用定数（調整）
    private static final String SEL_TEIBETSU_DPT_URI_TYOSEI = "selectTeibetsuDPTUriageTyosei";
    // 更新店別DPT売上SQL文用定数（調整）
    private static final String UPD_TEIBETSU_DPT_URI_TYOSEI = "updateTeibetsuDPTUriageTyosei";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // 消費税精算票項目取得
    private static final String SYOHIZEI_SEISAN_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.SYOHIZEI_SEISAN_KOMOKU);
    // 売上精算票項目取得
    private static final String URIAGE_SEISAN_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.URIAGE_SEISAN_KOMOKU);
    // エラー表示用DPT取得
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラー表示用DPT設定
    private static final String ERR_HYOJI_DPT =  StringUtility.spaceFormat(ERROR_DPT.keySet().toString().replace("[", "").replace("]", ""), 4);

    private ResorceUtil ru = ResorceUtil.getInstance();
    /**
     * DPT売上税計算処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 店別DPT売上データ更新件数
        int intCountTenDPTUri = 0;
        // 消費税誤差調整件数
        int intCountTyosei = 0;
        // 差額
        String strSagaku = null;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx psUpdateTeibetsuDPTUriage = null;
        PreparedStatementEx psUpdateTeibetsuDPTUriageTyosei = null;
        ResultSet resultSet = null;
        ResultSet rs = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　DPT売上税計算処理を開始します。");
        invoker.infoLog(strUserID + "　：　店別DPT売上データ更新（DPT売上税計算）を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_TEIBETSU_DPT_URI);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 法人コード
        preparedStatementEx.setString(2, COMP_CD);
        /* 2009.12.2 DPT税計算 エラー表示用DPTの税込金額を税抜金額にコピーする対応 */
//        // DPTコード
//        preparedStatementEx.setString(3, ERR_HYOJI_DPT);

        // SQLを実行する
        resultSet = preparedStatementEx.executeQuery();

        // 更新店別DPT売上SQLを取得し、パラメータを条件にバインドする
        psUpdateTeibetsuDPTUriage = invoker.getDataBase().prepareStatement(UPD_TEIBETSU_DPT_URI);

        // 店別DPT売上データのデータがなくなるまで繰り返す
        while (resultSet.next()) {

            // 税計算処理用Bean用意
            CalculateTaxBean calcInBean = new CalculateTaxBean();
            CalculateTaxBean calcOutBean = new CalculateTaxBean();

            /* 2009.12.2 DPT税計算 エラー表示用DPTの税込金額を税抜金額にコピーする対応 */
            if(!(ERROR_DPT.containsKey(resultSet.getString(BUNRUI1_CD)))){

                // BigDecimal型からString型への変換
                BigDecimal bdectaxRt = new BigDecimal(FiStringUtility.convert(resultSet.getString(TAX_RT), FiStringUtility.MODE_NUM));
                BigDecimal bdecKingaku = new BigDecimal(FiStringUtility.convert(resultSet.getString(URIAGE_KOMI_VL), FiStringUtility.MODE_NUM));

                // 項目をcalcInBeanにセット
                //2014/12/04 chou グローバル化対応 通貨対応 MOD START
                // 丸め区分
                //calcInBean.setMarumeKb(CalculateTax.STR_MIMANKIRISUTE);
                //端数処理設定
                calcInBean.setFractionDigit(ru.getPropertie(FractionDigitDic.FRACTION_COMMON_CURRENCY_LEN.getCode()));
                //四捨五入
                calcInBean.setMarumeKb(ru.getPropertie(FractionDigitDic.FRACTION_COMMON_CURRENCY_MODE.getCode()));
                //2014/12/04 chou グローバル化対応 通貨対応 MOD END
                // 税区分
                calcInBean.setTaxKb(resultSet.getString(ZEI_KB));
                // 税率
                calcInBean.setTaxRt(bdectaxRt);
                // 金額
                calcInBean.setKingaku(bdecKingaku);

                // 売上金額（税抜）と消費税額を取得する
                calcOutBean = CalculateTax.getTaxBeanByJava(calcInBean);

                // 売上金額（税抜）
                psUpdateTeibetsuDPTUriage.setString(1, calcOutBean.getTaxOut().toString());
                // 消費税額
                psUpdateTeibetsuDPTUriage.setString(2, calcOutBean.getTax().toString());

            }else{

                // 売上金額（税抜）
                psUpdateTeibetsuDPTUriage.setString(1, resultSet.getString(URIAGE_KOMI_VL));
                // 消費税額
                psUpdateTeibetsuDPTUriage.setString(2, "0");

            }

            // 更新者ID
            psUpdateTeibetsuDPTUriage.setString(3, BATCH_ID);
            // 更新年月日
            psUpdateTeibetsuDPTUriage.setString(4, DBSERVER_DT);
            // 法人コード
            psUpdateTeibetsuDPTUriage.setString(5, COMP_CD);
            // 計上日
            psUpdateTeibetsuDPTUriage.setString(6, resultSet.getString(KEIJO_DT));
            // 店舗コード
            psUpdateTeibetsuDPTUriage.setString(7, resultSet.getString(TENPO_CD));
            // DPTコード
            psUpdateTeibetsuDPTUriage.setString(8, resultSet.getString(BUNRUI1_CD));

            // 店別DPT売上データ更新件数を設定する
            intCountTenDPTUri = intCountTenDPTUri + psUpdateTeibetsuDPTUriage.executeUpdate();

        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountTenDPTUri + "件の店別DPT売上データを更新しました。");
        invoker.infoLog(strUserID + "　：　店別DPT売上データ更新（DPT売上税計算）を終了します。");
//        invoker.infoLog(strUserID + "　：　店別DPT売上データ更新（消費税誤差の調整）を開始します。");
//
//        // SQLを取得し、パラメータを条件にバインドする
//        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_TEIBETSU_DPT_URI_TYOSEI);
//        // 法人コード
//        preparedStatementEx.setString(1, COMP_CD);
//        // DPTコード
//        preparedStatementEx.setString(2, ERR_HYOJI_DPT);
//        // 法人コード
//        preparedStatementEx.setString(3, COMP_CD);
//        // DPTコード
//        preparedStatementEx.setString(4, ERR_HYOJI_DPT);
//        // 法人コード
//        preparedStatementEx.setString(5, COMP_CD);
//        // 法人コード
//        preparedStatementEx.setString(6, COMP_CD);
//        // 法人コード
//        preparedStatementEx.setString(7, COMP_CD);
//        // DPTコード
//        preparedStatementEx.setString(8, ERR_HYOJI_DPT);
//        // 法人コード
//        preparedStatementEx.setString(9, COMP_CD);
//        // 消費税精算票項目
//        preparedStatementEx.setString(10, SYOHIZEI_SEISAN_KOMOKU);
//        // 法人コード
//        preparedStatementEx.setString(11, COMP_CD);
//        // 売上精算票項目
//        preparedStatementEx.setString(12, URIAGE_SEISAN_KOMOKU);
//
//        // SQLを実行する
//        rs = preparedStatementEx.executeQuery();
//
//        // 更新店別DPT売上（調整）SQLを取得し、パラメータを条件にバインドする
//        psUpdateTeibetsuDPTUriageTyosei = invoker.getDataBase().prepareStatement(UPD_TEIBETSU_DPT_URI_TYOSEI);
//
//        // DPT精算売上ワークのデータがなくなるまで繰り返す
//        while (rs.next()) {
//
//            // 差額 =（精算）：精算金額 - （店別）：消費税額
//            strSagaku = CalcUtility.minus(rs.getString(SEISAN_VL), rs.getString(SYOHIZEI_VL));
//
//            // 差額
//            psUpdateTeibetsuDPTUriageTyosei.setString(1, strSagaku);
//            // 差額
//            psUpdateTeibetsuDPTUriageTyosei.setString(2, strSagaku);
//            // 更新者ID
//            psUpdateTeibetsuDPTUriageTyosei.setString(3, BATCH_ID);
//            // 更新年月日
//            psUpdateTeibetsuDPTUriageTyosei.setString(4, DBSERVER_DT);
//            // 法人コード
//            psUpdateTeibetsuDPTUriageTyosei.setString(5, COMP_CD);
//            // 計上日
//            psUpdateTeibetsuDPTUriageTyosei.setString(6, rs.getString(KEIJO_DT));
//            // 店舗コード
//            psUpdateTeibetsuDPTUriageTyosei.setString(7, rs.getString(TENPO_CD));
//            // DPTコード
//            psUpdateTeibetsuDPTUriageTyosei.setString(8, rs.getString(BUNRUI1_CD));
//
//            // 消費税誤差調整件数を設定する
//            intCountTyosei = intCountTyosei + psUpdateTeibetsuDPTUriageTyosei.executeUpdate();
//        }
//
//        // ログ出力
//        invoker.infoLog(strUserID + "　：　" + intCountTyosei + "件の店別DPT売上データを更新しました。");
//        invoker.infoLog(strUserID + "　：　店別DPT売上データ更新（消費税誤差の調整）を終了します。");
        invoker.infoLog(strUserID + "　：　DPT売上税計算処理を終了します。");

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
            DaoIf dao = new DPTUriageZeiKeisanDao();
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
