package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.CalcUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiStringUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: TenantSeisanDataSyukeiDao クラス</p>
 *  <P>説明: テナント精算データ集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */
public class TenantSeisanDataSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "テナント精算データ集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB141010";
    // 「０」
    private static final String ZERO = "0";
    // 「１」
    private static final String ONE = "1";
    // 法人コード
    private static final String COMP_CD_CONST = "COMP_CD";
    // 連番
    private static final String SEQ_RB = "SEQ_RB";
    // 計上日
    private static final String KEIJO_DT = "KEIJO_DT";
    // テナントコード
    private static final String TENANT_CD = "TENANT_CD";
    // 店舗コード
    private static final String TENPO_CD = "TENPO_CD";
    // 前回入金売上金額（税込）
    private static final String ZEN_NYUKIN_URIAGE_KOMI_VL = "ZEN_NYUKIN_URIAGE_KOMI_VL";
    // 前回報告売上金額（税込）
    private static final String ZEN_HOKOKU_URIAGE_KOMI_VL = "ZEN_HOKOKU_URIAGE_KOMI_VL";
    // 前回報告売上金額（税額）
    private static final String ZEN_HOKOKU_URIAGE_ZEIGAKU_VL = "ZEN_HOKOKU_URIAGE_ZEIGAKU_VL";
    // 入金売上金額（税込）
    private static final String NYUKIN_URIAGE_KOMI_VL = "NYUKIN_URIAGE_KOMI_VL";
    // 報告売上金額（税込）
    private static final String HOKOKU_URIAGE_KOMI_VL = "HOKOKU_URIAGE_KOMI_VL";
    // 報告売上金額（税額）
    private static final String HOKOKU_URIAGE_ZEIGAKU_VL = "HOKOKU_URIAGE_ZEIGAKU_VL";
    // 検索ワークテナント精算SQL文用定数
    private static final String SEL_WK_TENANT_SEISAN = "selectWKTenantSeisan";
    // 登録ワークテナント精算SQL文用定数
    private static final String INS_WK_TENANT_SEISAN = "createWKTenantSeisan";
    // 更新テナント精算SQL文用定数
    private static final String UPD_TENANT_SEISAN = "updateTenantSeisan";
    // 検索テナント精算SQL文用定数
    private static final String SEL_TENANT_SEISAN = "selectTenantSeisan";

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * テナント精算データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // IFテナント売上精算ワーク追加件数
        int intCreateCount = 0;
        // 店別テナント精算データ更新件数
        int intUpdateCount = 0;
        // データ件数
        int intCount = 0;
        // 連番
        String strNumber = null;
        // 入金売上金額（税込）用変数
        String strNyukinKomi = null;
        // 報告売上金額（税込）用変数
        String strHokokuKomi = null;
        // 報告売上金額（税額）用変数
        String strHokokuZeigaku = null;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx psSelectWKTenantSeisan = null;
        PreparedStatementEx psCreateWKTenantSeisan = null;
        PreparedStatementEx psUpdateTenantSeisan = null;
        ResultSet resultSet = null;
        ResultSet result = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　テナント精算データ集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　IFテナント売上精算ワーク追加処理を開始します。");
        invoker.infoLog(strUserID + "　：　店別テナント精算データ更新処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_TENANT_SEISAN);

        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 法人コード
        preparedStatementEx.setString(2, COMP_CD);

        // SQLを実行して、「店別テナント精算データ」データ抽出
        resultSet = preparedStatementEx.executeQuery();

        // 検索ワークテナント精算SQLを取得し、パラメータを条件にバインドする
        psSelectWKTenantSeisan = invoker.getDataBase().prepareStatement(SEL_WK_TENANT_SEISAN);
        // 登録ワークテナント精算SQLを取得し、パラメータを条件にバインドする
        psCreateWKTenantSeisan = invoker.getDataBase().prepareStatement(INS_WK_TENANT_SEISAN);
        // 更新テナント精算SQLを取得し、パラメータを条件にバインドする
        psUpdateTenantSeisan = invoker.getDataBase().prepareStatement(UPD_TENANT_SEISAN);

        while (resultSet.next()) {

            // 入金売上金額（税込）- 前回入金売上金額（税込）
            strNyukinKomi = CalcUtility.minus(
                    FiStringUtility.convert(resultSet.getString(NYUKIN_URIAGE_KOMI_VL), FiStringUtility.MODE_NUM),
                    FiStringUtility.convert(resultSet.getString(ZEN_NYUKIN_URIAGE_KOMI_VL), FiStringUtility.MODE_NUM));
            // 報告売上金額（税込）- 前回報告売上金額（税込）
            strHokokuKomi = CalcUtility.minus(
                    FiStringUtility.convert(resultSet.getString(HOKOKU_URIAGE_KOMI_VL), FiStringUtility.MODE_NUM), 
                    FiStringUtility.convert(resultSet.getString(ZEN_HOKOKU_URIAGE_KOMI_VL), FiStringUtility.MODE_NUM));
            // 報告売上金額（税額）- 前回報告売上金額（税額）
            strHokokuZeigaku = CalcUtility.minus(
                    FiStringUtility.convert(resultSet.getString(HOKOKU_URIAGE_ZEIGAKU_VL), FiStringUtility.MODE_NUM), 
                    FiStringUtility.convert(resultSet.getString(ZEN_HOKOKU_URIAGE_ZEIGAKU_VL), FiStringUtility.MODE_NUM));

            // 上記マイナスした値が０以外の場合
            if ((!ZERO.equals(strNyukinKomi)) || (!ZERO.equals(strHokokuKomi)) || (!ZERO.equals(strHokokuZeigaku))) {
                // 連番値を初期化
                strNumber = ZERO;

                // 法人コード
                psSelectWKTenantSeisan.setString(1, resultSet.getString(COMP_CD_CONST));
                // バッチ日付
                psSelectWKTenantSeisan.setString(2, BATCH_DT);

                // SQLを実行して、「IFテナント売上精算ワーク」データ抽出
                result = psSelectWKTenantSeisan.executeQuery();

                if (result.next()) {
                    if (result.getString(SEQ_RB) == null) {
                        // データがない場合
                        strNumber = ONE;
                    } else {
                        // 抽出した「連番」+ 1
                        strNumber = CalcUtility.plus(result.getString(SEQ_RB), ONE);
                    }
                }

                // 法人コード
                psCreateWKTenantSeisan.setString(1, resultSet.getString(COMP_CD_CONST));
                // バッチ日付
                psCreateWKTenantSeisan.setString(2, BATCH_DT);
                // 連番
                psCreateWKTenantSeisan.setString(3, strNumber);
                // 計上日
                psCreateWKTenantSeisan.setString(4, resultSet.getString(KEIJO_DT));
                // 店舗コード
                psCreateWKTenantSeisan.setString(5, resultSet.getString(TENPO_CD));
                // テナントコード
                psCreateWKTenantSeisan.setString(6, resultSet.getString(TENANT_CD));
                // 入金売上金額（税込）
                psCreateWKTenantSeisan.setString(7, strNyukinKomi);
                // 報告売上金額（税込）
                psCreateWKTenantSeisan.setString(8, strHokokuKomi);
                // 報告売上金額（税額）
                psCreateWKTenantSeisan.setString(9, strHokokuZeigaku);
                // IF作成日時
                psCreateWKTenantSeisan.setString(10, DBSERVER_DT);
                // 作成者ID
                psCreateWKTenantSeisan.setString(11, BATCH_ID);
                // 作成年月日
                psCreateWKTenantSeisan.setString(12, DBSERVER_DT);
                // 更新者ID
                psCreateWKTenantSeisan.setString(13, BATCH_ID);
                // 更新年月日
                psCreateWKTenantSeisan.setString(14, DBSERVER_DT);

                // IFテナント売上精算ワーク追加件数を設定する
                intCreateCount = intCreateCount + psCreateWKTenantSeisan.executeUpdate();

                // 前回入金売上金額（税込）
                psUpdateTenantSeisan.setString(1, resultSet.getString(NYUKIN_URIAGE_KOMI_VL));
                // 前回報告売上金額（税込）
                psUpdateTenantSeisan.setString(2, resultSet.getString(HOKOKU_URIAGE_KOMI_VL));
                // 前回報告売上金額（税額）
                psUpdateTenantSeisan.setString(3, resultSet.getString(HOKOKU_URIAGE_ZEIGAKU_VL));
                // 更新者ID
                psUpdateTenantSeisan.setString(4, BATCH_ID);
                // 更新年月日
                psUpdateTenantSeisan.setString(5, DBSERVER_DT);
                // 法人コード
                psUpdateTenantSeisan.setString(6, resultSet.getString(COMP_CD_CONST));
                // 計上日
                psUpdateTenantSeisan.setString(7, resultSet.getString(KEIJO_DT));
                // 店舗コード
                psUpdateTenantSeisan.setString(8, resultSet.getString(TENPO_CD));
                // テナントコード
                psUpdateTenantSeisan.setString(9, resultSet.getString(TENANT_CD));

                // 店別テナント精算データ更新件数を設定する
                intUpdateCount = intUpdateCount + psUpdateTenantSeisan.executeUpdate();

                // データ判断件数用
                intCount++;
            }
        }
        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCreateCount + "件のIFテナント売上精算ワークデータを追加しました。");
        invoker.infoLog(strUserID + "　：　IFテナント売上精算ワーク追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　" + intUpdateCount + "件の店別テナント精算データを更新しました。");
        invoker.infoLog(strUserID + "　：　店別テナント精算データ更新処理を終了します。");
        invoker.infoLog(strUserID + "　：　テナント精算データ集計処理を終了します。");
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
            DaoIf dao = new TenantSeisanDataSyukeiDao();
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
