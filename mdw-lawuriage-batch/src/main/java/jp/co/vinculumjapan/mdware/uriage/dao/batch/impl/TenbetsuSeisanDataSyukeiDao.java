package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.mdware.common.alarm.bean.DtAlarmBean;
import jp.co.vinculumjapan.mdware.common.alarm.dictionary.AlarmProcessKbDic;
import jp.co.vinculumjapan.mdware.common.alarm.process.PortalAlarm;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.DaoUtils;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル： KaikeiSeisanUriageDataTorikomiDao クラス</p>
 * <p>説明　　： 「会計精算売上ワーク」をデータを抽出し、「店別精算データ」「店別精算売掛データ」に挿入する、
 *               売上金額整合性チェック結果を「店別精算状況データ」に反映する</p>
 * <p>著作権　： Copyright (c) 2009</p>
 * <p>会社名　： Vinculum Japan Corp.</p>
 * @author   J.Lu
 * @Version 1.01 (2016/05/12) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 1.02 (2017/01/20) S_MDware-G_FIVIマート様開発 VINX T.Kamei #3637
 */
public class TenbetsuSeisanDataSyukeiDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチID
    private static final String BATCH_ID = "URIB041020";
    // バッチ名称
    private static final String BATCH_NAME = "店別精算データ集計処理";
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // 売上精算票項目取得
    // 2016.12.06 T.Kamei #3157対応(S)
    //private static final String URIAGE_SEISAN_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.URIAGE_SEISAN_KOMOKU);
    private static final String URIAGE_SEISAN_KOMOKU = "0051";
    // 2016.12.06 T.Kamei #3157対応(E)
    // DB日付取得
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // 店舗マスタチェックSQLid
    private static final String TENPO_MASTER_CHECK = "tenpoMasterCheck";
    // 精算票項目コード取得SQLid
    private static final String SELECT_SEISAN_KOMOKU_CD = "selectSeisanKomokuCd";
    // 売上金額整合性チェックSQLid
    private static final String URIAGE_KIN_CHECK = "uriageKinCheck";
    // 店別精算状況データに処理結果を反映
    private static final String UPDATE_TEN_SEISAN_STATE = "updateTenSeisanState";
    // 店舗コード
    private static final String TENPO_CD = "TENPO_CD";
    // マスタチェック用の店舗コード
    private static final String TENPO_CODE = "TENPO_CODE";
    // 店別DPT売上データ.POS金額
    private static final String DPT_POS_VL = "DPT_POS_VL";
    // 店別精算データ.POS金額
    private static final String POS_VL = "POS_VL";
    // 計上日
    private static final String KEIJO_DT = "KEIJO_DT";
    // 店舗区分
    private static final String TENPO_KUBUN = "1";
    // 削除フラグ
    private static final String DELETE_FLG = "0";
    // 修正不可フラグ(可能)
    private static final String SYUSEI_FUKA_FG_KANO = "0";
    // 修正不可フラグ(不可)
    private static final String SYUSEI_FUKA_FG_FUKA = "1";
    // アラームID
    private static final String URI_00001 = "URI_00001";

    /**
     * 店別精算データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // 開始ログを出力する
        invoker.infoLog(strUserId + "　：　店別精算データ集計処理を開始します。");
        // データ追加処理ログを出力する
        // #3637 2017.01.20 T.Kamei MOD (S)
        //invoker.infoLog(strUserId + "　：　店別精算データ追加処理を開始します。");
        //invoker.infoLog(strUserId + "　：　店別精算売掛データ追加処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算データ追加・更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ追加・更新処理を開始します。");
        // #3637 2017.01.20 T.Kamei MOD (E)

        PreparedStatementEx preparedStatementEx = null;
        ResultSet resultSet = null;
        // データ追加処理用
        ResultSet rs = null;
        // 売上金額整合性チェック用
        ResultSet checkRs = null;
        // 店別精算データ追加件数
        int intSeisan = 0;
        // 店別精算売掛データ追加件数
        int intUrikake = 0;
        // 店別精算状況データ更新件数
        int intUpdate = 0;
        // 売上金額整合性チェックフラグ
        boolean blnFlag = false;

        // 店舗マスタチェックを行う
        preparedStatementEx = invoker.getDataBase().prepareStatement(TENPO_MASTER_CHECK);

        // 店舗マスタ.法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 店舗マスタ.開店日
        preparedStatementEx.setString(2, BATCH_DT);
        // 店舗マスタ.財務終了日
        preparedStatementEx.setString(3, BATCH_DT);
        // 会計精算売上ワーク.法人コード
        preparedStatementEx.setString(4, COMP_CD);
        // 会計精算売上ワーク.計上日
        preparedStatementEx.setString(5, BATCH_DT);
        // SQLを実行する
        resultSet = preparedStatementEx.executeQuery();

        while (resultSet.next()) {
            // 店舗コードを取得する
            String strTenpoCd = resultSet.getString(TENPO_CD);

            // 店舗マスタチェックを行う
            if (!strTenpoCd.equals(resultSet.getString(TENPO_CODE))) {
                // ログを出力する
                invoker.warnLog(strUserId + "　：　店舗コード" + "：" + strTenpoCd + "が店舗マスタに存在しません。");
                continue;
            }
        }

        // 精算票項目コードを取得する
        //preparedStatementEx = invoker.getDataBase().prepareStatement(SELECT_SEISAN_KOMOKU_CD);
        // 精算票項目マスタ.法人コード
        //preparedStatementEx.setString(1, COMP_CD);
        // 精算票項目マスタ.適用開始日
        //preparedStatementEx.setString(2, BATCH_DT);
        // 売掛項目マスタ.法人コード
        //preparedStatementEx.setString(3, COMP_CD);
        // 売掛項目マスタ.適用開始日
        //preparedStatementEx.setString(4, BATCH_DT);
        // SQLを実行する
        //rs = preparedStatementEx.executeQuery();
        // データ取得後、格納用リスト
        //List list = new ArrayList();

        TenbetuSeisanDataShukeiDaoOutputBean ouputBean = null;

        // SELECT列リストで指定した別名から、"_" を外してOutputRowBeanの同名プロパティにセットする為のルール
        //String[][] rules = { UriageCommonConstants.RULES };

        //while (rs.next()) {
            ouputBean = new TenbetuSeisanDataShukeiDaoOutputBean();

            // OutputBeanの同名プロパティにデータを全てコピーする
        //    DaoUtils.copy(ouputBean, DaoUtils.getMapFromRs(rs), rules);
        //    list.add(ouputBean);
        //}

        //int intSize = list.size();

        //if (intSize > UriageCommonConstants.ZERO_INT) {
            // 店別精算データ.POSデータカラム名を定義する
            //String strSeisanPosNA = UriageCommonConstants.EMPTY;
            // 売掛項目マスタ.POSデータカラム名を定義する
            //String strUrikakePosNA = UriageCommonConstants.EMPTY;
            // 精算票項目コードを定義する
            //String strKomokuCd = null;

            //for (int i = UriageCommonConstants.ZERO_INT; i < intSize; i++) {
                // ouputBean初期化
                //ouputBean = new TenbetuSeisanDataShukeiDaoOutputBean();

                // リストのi件目を取得
                //ouputBean = (TenbetuSeisanDataShukeiDaoOutputBean) list.get(i);

                // 最先精算票項目コードを取得する
                //if (i == UriageCommonConstants.ZERO_INT) {
                //    strKomokuCd = ouputBean.getSeisanhyoKomokuCd();
                //}

                // 該当レコードの精算票項目コードと後レコードの精算票項目コード同じ場合
                //if (strKomokuCd.equals(ouputBean.getSeisanhyoKomokuCd())) {

                    // 精算票項目コードは最新の精算票項目コードを設定する
                    //strKomokuCd = ouputBean.getSeisanhyoKomokuCd();

                    // 売掛項目マスタ.精算票項目コードが非NULL場合、店別精算売掛データ作成する
                    //if ((ouputBean.getUrikakeSeisanhyoKomokuCD() != null) && (!UriageCommonConstants.EMPTY.equals(ouputBean.getUrikakeSeisanhyoKomokuCD()))) {
                        // 売掛項目マスタ.POSデータカラム名を取得する
                    //    strUrikakePosNA = ouputBean.getUrikakePosDataColumnNa().trim();

                        // 店別精算売掛データ作成用SQL文作成する
                        PreparedStatementEx tenSeisanUrikakePs = invoker.getDataBase().prepareStatement(getInsertTenSeisanUrikakeSql(/*strUrikakePosNA, ouputBean.getSyuseiFukaFg()*/));

                        // #3637 2017.01.20 T.Kamei MOD (S)
                        // 店別精算売掛データ.法人コード
                        //tenSeisanUrikakePs.setString(1, COMP_CD);
                        // 店別精算売掛データ.計上日
                        //tenSeisanUrikakePs.setString(2, BATCH_DT);
                        // 店別精算売掛データ.精算票項目コード
                        //tenSeisanUrikakePs.setString(3, strKomokuCd);
                        // 店別精算売掛データ.売掛項目コード
                        //tenSeisanUrikakePs.setString(4, ouputBean.getUrikakeKomokuCd());
                        // 店別精算売掛データ.作成者ID
                        //tenSeisanUrikakePs.setString(2, BATCH_ID);
                        // 店別精算売掛データ.作成年月日
                        //tenSeisanUrikakePs.setString(3, DBSERVER_DT);
                        // 店別精算売掛データ.更新者ID
                        //tenSeisanUrikakePs.setString(4, BATCH_ID);
                        // 店別精算売掛データ.更新年月日
                        //tenSeisanUrikakePs.setString(5, DBSERVER_DT);
                        // 店舗マスタ.法人コード
                        //tenSeisanUrikakePs.setString(6, COMP_CD);
                        tenSeisanUrikakePs.setString(1, COMP_CD);
                        // 店舗マスタ.開店日
                        //tenSeisanUrikakePs.setString(7, BATCH_DT);
                        tenSeisanUrikakePs.setString(2, BATCH_DT);
                        // 店舗マスタ.財務終了日
                        //tenSeisanUrikakePs.setString(8, BATCH_DT);
                        tenSeisanUrikakePs.setString(3, BATCH_DT);

                        // 精算票項目マスタ.適用開始日
                        //tenSeisanUrikakePs.setString(9, BATCH_DT);
                        tenSeisanUrikakePs.setString(4, BATCH_DT);

                        // 会計精算売上ワーク.法人コード
                        //tenSeisanUrikakePs.setString(10, COMP_CD);
                        tenSeisanUrikakePs.setString(5, COMP_CD);
                        // 会計精算売上ワーク.計上日
                        // 会計精算売上ワーク.データ作成日
                        //tenSeisanUrikakePs.setString(11, BATCH_DT);
                        tenSeisanUrikakePs.setString(6, BATCH_DT);

                        // 店別精算売掛データ.更新者ID
                        tenSeisanUrikakePs.setString(7, BATCH_ID);
                        // 店別精算売掛データ.更新年月日
                        tenSeisanUrikakePs.setString(8, DBSERVER_DT);

                        // 店別精算売掛データ.作成者ID
                        tenSeisanUrikakePs.setString(9, BATCH_ID);
                        // 店別精算売掛データ.作成年月日
                        tenSeisanUrikakePs.setString(10, DBSERVER_DT);
                        // 店別精算売掛データ.更新者ID
                        tenSeisanUrikakePs.setString(11, BATCH_ID);
                        // 店別精算売掛データ.更新年月日
                        tenSeisanUrikakePs.setString(12, DBSERVER_DT);
                        // #3637 2017.01.20 T.Kamei MOD (E)

                        // SQLを実行して成功場合、店別精算売掛データ追加件数＋１
                        intUrikake = intUrikake + tenSeisanUrikakePs.executeUpdate();

                        // 使用済みオブジェクトを閉じる
                        tenSeisanUrikakePs.close();

                        // 店別精算データ.POSデータカラム名を取得する
                        //if (UriageCommonConstants.EMPTY.equals(strSeisanPosNA)) {
                        //    strSeisanPosNA = strUrikakePosNA;
                        //} else {
                            // POSデータカラム名が非空場合
                        //    if (!"".equals(strUrikakePosNA)) {
                        //        strSeisanPosNA = strSeisanPosNA + "+" + strUrikakePosNA;
                        //    }
                        //}
                        // 店別精算データ.POSデータカラム名を取得する
                    //} else {
                    //    strSeisanPosNA = ouputBean.getPosDataColumnNa().trim();
                    //}
                //}
                // 該当レコードの精算票項目コードと後レコードの精算票項目コード同じじゃない場合、店別精算データ作成する
                //if (!strKomokuCd.equals(ouputBean.getSeisanhyoKomokuCd())) {
                    // 店別精算データ作成用SQL文作成する
                    PreparedStatementEx tenSeisanPs = invoker.getDataBase().prepareStatement(getInsertTenSeisan(/*strSeisanPosNA*/));

                    // #3637 2017.01.20 T.Kamei MOD (S)
                    // 店別精算データ.法人コード
                    //tenSeisanPs.setString(1, COMP_CD);
                    // 店別精算データ.計上日
                    //tenSeisanPs.setString(2, BATCH_DT);
                    // 店別精算データ.精算票項目コード
                    //tenSeisanPs.setString(3, strKomokuCd);
                    // 店別精算データ.作成者ID
                    //tenSeisanPs.setString(2, BATCH_ID);
                    // 店別精算データ.作成年月日
                    //tenSeisanPs.setString(3, DBSERVER_DT);
                    // 店別精算データ.更新者ID
                    //tenSeisanPs.setString(4, BATCH_ID);
                    // 店別精算データ.更新年月日
                    //tenSeisanPs.setString(5, DBSERVER_DT);
                    // 店舗マスタ.法人コード
                    //tenSeisanPs.setString(6, COMP_CD);
                    tenSeisanPs.setString(1, COMP_CD);
                    // 店舗マスタ.開店日
                    //tenSeisanPs.setString(7, BATCH_DT);
                    tenSeisanPs.setString(2, BATCH_DT);
                    // 店舗マスタ.財務終了日
                    //tenSeisanPs.setString(8, BATCH_DT);
                    tenSeisanPs.setString(3, BATCH_DT);
                    // 精算票項目マスタ.適用開始日
                    //tenSeisanPs.setString(9, BATCH_DT);
                    tenSeisanPs.setString(4, BATCH_DT);
                    // 会計精算売上ワーク.法人コード
                    //tenSeisanPs.setString(10, COMP_CD);
                    tenSeisanPs.setString(5, COMP_CD);
                    // 会計精算売上ワーク.計上日
                    // 会計精算売上ワーク.データ作成日
                    //tenSeisanPs.setString(11, BATCH_DT);
                    tenSeisanPs.setString(6, BATCH_DT);
                    
                    // 店別精算データ.更新者ID
                    tenSeisanPs.setString(7, BATCH_ID);
                    // 店別精算データ.更新年月日
                    tenSeisanPs.setString(8, DBSERVER_DT);

                    // 店別精算データ.作成者ID
                    tenSeisanPs.setString(9, BATCH_ID);
                    // 店別精算データ.作成年月日
                    tenSeisanPs.setString(10, DBSERVER_DT);
                    // 店別精算データ.更新者ID
                    tenSeisanPs.setString(11, BATCH_ID);
                    // 店別精算データ.更新年月日
                    tenSeisanPs.setString(12, DBSERVER_DT);

                    // SQLを実行して成功場合、店別精算データ追加件数＋１
                    intSeisan = intSeisan + tenSeisanPs.executeUpdate();

                    // 使用済みオブジェクトを閉じる
                    tenSeisanPs.close();

                    // 精算票項目コードは最新の精算票項目コードを設定する
                    //strKomokuCd = ouputBean.getSeisanhyoKomokuCd();

                    // 店別精算データ.POSデータカラム名をクリア
                    //strSeisanPosNA = UriageCommonConstants.EMPTY;

                    // ループ-1
                    //i = i - 1;
                //}

                // 該当レコードは最後レコードの場合、店別精算データ作成する
                //if (i == (intSize - 1)) {
                    // 店別精算データ作成用SQL文作成する
                    //PreparedStatementEx tenSeisanPs = invoker.getDataBase().prepareStatement(getInsertTenSeisan(/*strSeisanPosNA*/));

                    // 法人コード
                    //tenSeisanPs.setString(1, COMP_CD);
                    // 計上日
                    //tenSeisanPs.setString(2, BATCH_DT);
                    // 精算票項目コード
                    //tenSeisanPs.setString(3, strKomokuCd);
                    // 作成者ID
                    //tenSeisanPs.setString(2, BATCH_ID);
                    // 作成年月日
                    //tenSeisanPs.setString(3, DBSERVER_DT);
                    // 更新者ID
                    //tenSeisanPs.setString(4, BATCH_ID);
                    // 更新年月日
                    //tenSeisanPs.setString(5, DBSERVER_DT);
                    // 店舗マスタ.法人コード
                    //tenSeisanPs.setString(6, COMP_CD);
                    // 店舗マスタ.開店日
                    //tenSeisanPs.setString(7, BATCH_DT);
                    // 店舗マスタ.財務終了日
                    //tenSeisanPs.setString(8, BATCH_DT);
                    // 精算票項目マスタ.適用開始日
                    //tenSeisanPs.setString(9, BATCH_DT);
                    // 会計精算売上ワーク.法人コード
                    //tenSeisanPs.setString(10, COMP_CD);
                    // 会計精算売上ワーク.計上日
                    //tenSeisanPs.setString(11, BATCH_DT);

                    // SQLを実行して成功場合、店別精算データ追加件数＋１
                    //intSeisan = intSeisan + tenSeisanPs.executeUpdate();

                    // 使用済みオブジェクトを閉じる
                    //tenSeisanPs.close();

                    // 精算票項目コードは最新の精算票項目コードを設定する
                //    strKomokuCd = ouputBean.getSeisanhyoKomokuCd();
                //}
            //}
        //}
        // 店別精算データ追加処理終了ログを出力する
        // #3637 2017.01.20 T.Kamei MOD (S)
        //invoker.infoLog(strUserId + "　：　" + intSeisan + "件の店別精算データを追加しました。");
        //invoker.infoLog(strUserId + "　：　店別精算データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intSeisan + "件の店別精算データを追加・更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算データ追加・更新処理を終了します。");
        // 店別精算売掛データ追加処理終了ログを出力する
        //invoker.infoLog(strUserId + "　：　" + intUrikake + "件の店別精算売掛データを追加しました。");
        //invoker.infoLog(strUserId + "　：　店別精算売掛データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intUrikake + "件の店別精算売掛データを追加・更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ追加・更新処理を終了します。");
        // #3637 2017.01.20 T.Kamei MOD (E)

        // 売上金額整合性チェック開始ログを出力する
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新を開始します。");

        // 売上金額整合性チェックを行う
        preparedStatementEx = invoker.getDataBase().prepareStatement(URIAGE_KIN_CHECK);
        // 店別精算データ.法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 2016.12.06 T.Kamei #3157対応(S)
        // 店別精算データ.計上日
        //preparedStatementEx.setString(2, BATCH_DT);
        // 店別DPT売上データ.法人コード
        //preparedStatementEx.setString(3, COMP_CD);
        preparedStatementEx.setString(2, COMP_CD);
        // 店別DPT売上データ.計上日
        //preparedStatementEx.setString(4, BATCH_DT);
        preparedStatementEx.setString(3, BATCH_DT);
        // 店別精算データ.精算票項目
        //preparedStatementEx.setString(5, URIAGE_SEISAN_KOMOKU);
        preparedStatementEx.setString(4, URIAGE_SEISAN_KOMOKU);
        // 2016.12.06 T.Kamei #3157対応(E)
        // SQLを実行する
        checkRs = preparedStatementEx.executeQuery();

        // 店別精算状況データに処理結果を反映する
        PreparedStatementEx updatePs = invoker.getDataBase().prepareStatement(UPDATE_TEN_SEISAN_STATE);

        while (checkRs.next()) {
            // 店舗コードを取得する
            String strTenpoCode = checkRs.getString(TENPO_CD);

            // 店別DPT売上データ.POS金額≠店別精算データ.POS金額の場合
            if (!checkRs.getString(POS_VL).equals(checkRs.getString(DPT_POS_VL))) {
                // エラーログを出力する
                invoker.warnLog(strUserId + "　：　DPT売上と精算売上が一致しません。計上日：" + checkRs.getString(KEIJO_DT) + " " + "店舗コード：" + " " + strTenpoCode + " " + "DPT売上金額：" + checkRs.getString(DPT_POS_VL) + " "
                        + "精算売上金額：" + checkRs.getString(POS_VL));
                // 精算状況ﾌﾗｸﾞを設定する(090901 yasuda update コメントをDPT精算状況ﾌﾗｸﾞから精算状況フラグに変更)
                blnFlag = false;

                // アラーム出力
                pushAlarmMsg(URI_00001, invoker.getUserId(), FiResorceUtility.getPropertie(UriResorceKeyConstant.ALARM_DESTINATION_CD_SYSTEM));

            } else {
                blnFlag = true;
            }

            // 精算状況ﾌﾗｸﾞは売上金額整合性チェック正常の場合、1(取込済)をセット(090901 yasuda update コメントをDPT精算状況ﾌﾗｸﾞから精算状況フラグに変更)
            if (blnFlag) {
                updatePs.setString(1, UriageCommonConstants.TORIKOMI_TSUMI_FLAG);
            }
            // 売上金額整合性チェックエラーの場合、9(エラー)をセット
            else {
                // 2016.12.06 T.Kamei #3157対応(S)
                //updatePs.setString(1, UriageCommonConstants.ERROR_FLAG);
                updatePs.setString(1, UriageCommonConstants.TORIKOMI_TSUMI_FLAG);
                // 2016.12.06 T.Kamei #3157対応(E)
            }
            // 更新者ID
            updatePs.setString(2, BATCH_ID);
            // 更新年月日
            updatePs.setString(3, DBSERVER_DT);
            // 法人コード
            updatePs.setString(4, COMP_CD);
            // 2016.12.06 T.Kamei #3157対応(S)
            // バッチ日付
            //updatePs.setString(5, BATCH_DT);
            // 計上日
            updatePs.setString(5, checkRs.getString(KEIJO_DT));
            // 2016.12.06 T.Kamei #3157対応(E)
            // 店舗コード
            updatePs.setString(6, strTenpoCode);

            // SQLを実行して成功場合、店別精算状況データ更新件数＋１
            intUpdate = intUpdate + updatePs.executeUpdate();
        }

        // 売上金額整合性チェックログ終了ログを出力する
        invoker.infoLog(strUserId + "　：　" + intUpdate + "件の店別精算状況データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新処理を終了します。");

        // 終了ログを出力する
        invoker.infoLog(strUserId + "　：　店別精算データ集計処理を終了します。");
    }

    /**
     * アラーム出力処理
     */
    private void pushAlarmMsg(String alarmId, String userId, String DestinationBusyoCd) throws Exception {

        DtAlarmBean inBean = new DtAlarmBean();
        PortalAlarm alrm = new PortalAlarm();

        // アラームID
        inBean.setAlarmId(alarmId);
        // 作成ユーザーID
        inBean.setInsertUserId(userId);
        // 宛先部署コード
        inBean.setDestinationBusyoCd(DestinationBusyoCd);

        // プロセス区分
        String strProcessKb = AlarmProcessKbDic.BATCH.getCode();

        // アラーム登録
        alrm.setAlarmMessage(inBean, strProcessKb);
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

    /**
     * 店別精算売掛データ用SQLを作成し、返します
     * @param String PosName
     * @param String SyuseiFukaFg
     * @return String SQL文
     */
    private String getInsertTenSeisanUrikakeSql(/*String PosName, String SyuseiFukaFg*/) {

        StringBuffer sqlBuffer = new StringBuffer();

        // #3637 2017.01.20 T.Kamei MOD (S)
        sqlBuffer.append("MERGE INTO ");
        sqlBuffer.append("    DT_TEN_SEISAN_URIKAKE DTSU ");
        sqlBuffer.append("USING ");
        sqlBuffer.append("    ( ");
        sqlBuffer.append("        SELECT ");
        sqlBuffer.append("            WKS.COMP_CD, ");
        sqlBuffer.append("            WKS.KEIJO_DT, ");
        sqlBuffer.append("            WKS.TENPO_CD, ");
        sqlBuffer.append("            WKS.SEISANHYO_KOMOKU_CD, ");
        sqlBuffer.append("            WKS.URIKAKE_KOMOKU_CD, ");
        sqlBuffer.append("            WKS.KINGAKU_VL ");
        sqlBuffer.append("        FROM ");
        sqlBuffer.append("            WK_KAIKEI_SEISAN WKS ");
        sqlBuffer.append("        INNER JOIN");
        sqlBuffer.append("           DT_TEN_SEISAN_STATE DTSS ");
        sqlBuffer.append("        ON DTSS.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND DTSS.KEIJO_DT = WKS.KEIJO_DT ");
        sqlBuffer.append("        AND DTSS.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        AND DTSS.SEISAN_STATE_FG <> '" + UriageCommonConstants.UPDATE_FLAG + "' ");
        sqlBuffer.append("        LEFT JOIN");
        sqlBuffer.append("            R_TENPO RT ");
        sqlBuffer.append("        ON HOJIN_CD = ? ");
        sqlBuffer.append("        AND TENPO_KB = " + TENPO_KUBUN);
        sqlBuffer.append("        AND KAITEN_DT <= ? ");
        sqlBuffer.append("        AND ZAIMU_END_DT >= ? ");
        sqlBuffer.append("        AND DELETE_FG = " + DELETE_FLG);
        sqlBuffer.append("        AND RT.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        LEFT JOIN ");
        sqlBuffer.append("            R_URIKAKE_KOMOKU RUK ");
        sqlBuffer.append("        ON RUK.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND RUK.URIKAKE_KOMOKU_CD = WKS.URIKAKE_KOMOKU_CD ");
        sqlBuffer.append("        AND RUK.TEKIYO_START_DT = ? " );
        sqlBuffer.append("        AND RUK.DELETE_FG = '0' ");
        sqlBuffer.append("        WHERE ");
        sqlBuffer.append("            WKS.COMP_CD = ? ");
        sqlBuffer.append("        AND WKS.DATA_SAKUSEI_DT = ? ");
        sqlBuffer.append("        AND WKS.URIKAKE_KOMOKU_CD != ' ' ");
        sqlBuffer.append("        AND RT.TENPO_CD IS NOT NULL ");
        sqlBuffer.append("    ) WKS ");
        sqlBuffer.append("ON ");
        sqlBuffer.append("    ( ");
        sqlBuffer.append("            DTSU.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND DTSU.KEIJO_DT = WKS.KEIJO_DT ");
        sqlBuffer.append("        AND DTSU.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        AND DTSU.SEISANHYO_KOMOKU_CD = WKS.SEISANHYO_KOMOKU_CD ");
        sqlBuffer.append("        AND DTSU.URIKAKE_KOMOKU_CD = WKS.URIKAKE_KOMOKU_CD ");
        sqlBuffer.append("    ) ");
        sqlBuffer.append("WHEN MATCHED THEN ");
        sqlBuffer.append("    UPDATE SET ");
        sqlBuffer.append("        DTSU.POS_VL = DTSU.POS_VL + WKS.KINGAKU_VL, ");
        sqlBuffer.append("        DTSU.SEISAN_VL = DTSU.SEISAN_VL + WKS.KINGAKU_VL, ");
        sqlBuffer.append("        DTSU.UPDATE_USER_ID = ?, ");
        sqlBuffer.append("        DTSU.UPDATE_TS = ? ");
        sqlBuffer.append("WHEN NOT MATCHED THEN ");
        sqlBuffer.append("    INSERT ");
        sqlBuffer.append("        ( ");
        sqlBuffer.append("            COMP_CD, ");
        sqlBuffer.append("            KEIJO_DT, ");
        sqlBuffer.append("            TENPO_CD, ");
        sqlBuffer.append("            SEISANHYO_KOMOKU_CD, ");
        sqlBuffer.append("            URIKAKE_KOMOKU_CD, ");
        sqlBuffer.append("            POS_VL, ");
        sqlBuffer.append("            SEISAN_VL, ");
        sqlBuffer.append("            INSERT_USER_ID, ");
        sqlBuffer.append("            INSERT_TS, ");
        sqlBuffer.append("            UPDATE_USER_ID, ");
        sqlBuffer.append("            UPDATE_TS ");
        sqlBuffer.append("        ) ");
        sqlBuffer.append("    VALUES ");
        sqlBuffer.append("        ( ");
        sqlBuffer.append("            WKS.COMP_CD, ");
        sqlBuffer.append("            WKS.KEIJO_DT, ");
        sqlBuffer.append("            WKS.TENPO_CD, ");
        sqlBuffer.append("            WKS.SEISANHYO_KOMOKU_CD, ");
        sqlBuffer.append("            WKS.URIKAKE_KOMOKU_CD, ");
        sqlBuffer.append("            WKS.KINGAKU_VL, ");
        sqlBuffer.append("            WKS.KINGAKU_VL, ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ? ");
        sqlBuffer.append("        ) ");

        //sqlBuffer.append("INSERT INTO ");
        //sqlBuffer.append("    DT_TEN_SEISAN_URIKAKE ");
        //sqlBuffer.append("    ( ");
        //sqlBuffer.append("    COMP_CD, ");
        //sqlBuffer.append("    KEIJO_DT, ");
        //sqlBuffer.append("    TENPO_CD, ");
        //sqlBuffer.append("    SEISANHYO_KOMOKU_CD, ");
        //sqlBuffer.append("    URIKAKE_KOMOKU_CD, ");
        //sqlBuffer.append("    POS_VL, ");
        //sqlBuffer.append("    SEISAN_VL, ");
        //sqlBuffer.append("    INSERT_USER_ID, ");
        //sqlBuffer.append("    INSERT_TS, ");
        //sqlBuffer.append("    UPDATE_USER_ID, ");
        //sqlBuffer.append("    UPDATE_TS) ");
        //sqlBuffer.append("    ( ");
        //sqlBuffer.append("    SELECT ");

        // 2016/05/31 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("        ?, ?, WKS.TENPO_CD, ?, ");
        //sqlBuffer.append("        ?, ");
        //sqlBuffer.append("        WKS.KEIJO_DT, ");
        //sqlBuffer.append("        WKS.TENPO_CD, ");
        //sqlBuffer.append("        WKS.SEISANHYO_KOMOKU_CD, ");
        //sqlBuffer.append("        WKS.URIKAKE_KOMOKU_CD, ");
        //sqlBuffer.append("        WKS.KINGAKU_VL, ");
        //sqlBuffer.append("        WKS.KINGAKU_VL, ");
        // カラム名が非空場合
        //if (!"".equals(PosName)) {
        //    sqlBuffer.append("        SUM(" + PosName + "), ");
        //} else {
        //    sqlBuffer.append("        '0',");
        //}
        // カラム名が非空場合
        //if (!"".equals(PosName)) {
        //    sqlBuffer.append("        SUM(" + PosName + "), ");
        //} else {
        //    sqlBuffer.append("        '0',");
        //}
        // 2016/05/31 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

//        // 修正不可フラグ＝【0】(可能)の場合
//        if (SYUSEI_FUKA_FG_KANO.equals(SyuseiFukaFg)) {
//            sqlBuffer.append("        '0',");
//        // 修正不可フラグ＝【1】(不可)の場合
//        } else if (SYUSEI_FUKA_FG_FUKA.equals(SyuseiFukaFg)) {
//            sqlBuffer.append("        SUM(" + PosName + "), ");
//        } else {
//            sqlBuffer.append("        '0',");
//        }
        //sqlBuffer.append("        ?, ?, ?, ? ");
        //sqlBuffer.append("    FROM ");
        //sqlBuffer.append("        WK_KAIKEI_SEISAN WKS ");
        //sqlBuffer.append("    INNER JOIN");
        //sqlBuffer.append("        DT_TEN_SEISAN_STATE DTSS ");
        //sqlBuffer.append("    ON DTSS.COMP_CD = WKS.COMP_CD ");
        //sqlBuffer.append("    AND DTSS.KEIJO_DT = WKS.KEIJO_DT ");
        //sqlBuffer.append("    AND DTSS.TENPO_CD = WKS.TENPO_CD ");
        //sqlBuffer.append("    AND DTSS.SEISAN_STATE_FG <> '" + UriageCommonConstants.UPDATE_FLAG + "' ");
        //sqlBuffer.append("    LEFT JOIN");
        //sqlBuffer.append("        R_TENPO RT ");
        //sqlBuffer.append("    ON HOJIN_CD = ? ");
        //sqlBuffer.append("    AND TENPO_KB = " + TENPO_KUBUN);
        //sqlBuffer.append("    AND KAITEN_DT <= ? ");
        //sqlBuffer.append("    AND ZAIMU_END_DT >= ? ");
        //sqlBuffer.append("    AND DELETE_FG = " + DELETE_FLG);
        //sqlBuffer.append("    AND RT.TENPO_CD = WKS.TENPO_CD ");

        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("    LEFT JOIN ");
        //sqlBuffer.append("        R_URIKAKE_KOMOKU RUK ");
        //sqlBuffer.append("    ON RUK.COMP_CD = WKS.COMP_CD ");
        //sqlBuffer.append("    AND RUK.URIKAKE_KOMOKU_CD = WKS.URIKAKE_KOMOKU_CD ");
        //sqlBuffer.append("    AND RUK.TEKIYO_START_DT = ? " );
        //sqlBuffer.append("    AND RUK.DELETE_FG = '0' ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

        //sqlBuffer.append("    WHERE ");
        //sqlBuffer.append("        WKS.COMP_CD = ? ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("    AND WKS.KEIJO_DT = ? ");
        //sqlBuffer.append("    AND WKS.DATA_SAKUSEI_DT = ? ");
        //sqlBuffer.append("    AND WKS.URIKAKE_KOMOKU_CD != ' ' ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        //sqlBuffer.append("    AND RT.TENPO_CD IS NOT NULL) ");
        //sqlBuffer.append("    GROUP BY ");
        //sqlBuffer.append("        WKS.TENPO_CD) ");
        // #3637 2017.01.20 T.Kamei MOD (E)

        return sqlBuffer.toString();
    }

    /**
     * 店別精算データ用SQLを作成し、返します
     * @param String PosName
     * @return String SQL文
     */
    private String getInsertTenSeisan(/*String PosName*/) {

        StringBuffer sqlBuffer = new StringBuffer();

        // #3637 2017.01.20 T.Kamei MOD (S)
        sqlBuffer.append("MERGE INTO ");
        sqlBuffer.append("    DT_TEN_SEISAN DTS ");
        sqlBuffer.append("USING ");
        sqlBuffer.append("    ( ");
        sqlBuffer.append("        SELECT ");
        sqlBuffer.append("           WKS.COMP_CD,");
        sqlBuffer.append("           WKS.KEIJO_DT,");
        sqlBuffer.append("           WKS.TENPO_CD,");
        sqlBuffer.append("           WKS.SEISANHYO_KOMOKU_CD,");
        sqlBuffer.append("           WKS.KINGAKU_VL ");
        sqlBuffer.append("        FROM ");
        sqlBuffer.append("            WK_KAIKEI_SEISAN WKS ");
        sqlBuffer.append("        INNER JOIN");
        sqlBuffer.append("            DT_TEN_SEISAN_STATE DTSS ");
        sqlBuffer.append("        ON DTSS.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND DTSS.KEIJO_DT = WKS.KEIJO_DT ");
        sqlBuffer.append("        AND DTSS.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        AND DTSS.SEISAN_STATE_FG <> '" + UriageCommonConstants.UPDATE_FLAG + "' ");
        sqlBuffer.append("        LEFT JOIN ");
        sqlBuffer.append("            R_TENPO RT ");
        sqlBuffer.append("        ON HOJIN_CD = ? ");
        sqlBuffer.append("        AND TENPO_KB = " + TENPO_KUBUN);
        sqlBuffer.append("        AND KAITEN_DT <= ? ");
        sqlBuffer.append("        AND ZAIMU_END_DT >= ? ");
        sqlBuffer.append("        AND DELETE_FG = " + DELETE_FLG);
        sqlBuffer.append("        AND RT.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        LEFT JOIN ");
        sqlBuffer.append("            R_SEISANHYO_KOMOKU RSK ");
        sqlBuffer.append("        ON RSK.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND RSK.SEISANHYO_KOMOKU_CD = WKS.SEISANHYO_KOMOKU_CD ");
        sqlBuffer.append("        AND RSK.TEKIYO_START_DT = ? ");
        sqlBuffer.append("        AND RSK.DELETE_FG = '0' ");
        sqlBuffer.append("        WHERE ");
        sqlBuffer.append("            WKS.COMP_CD = ? ");
        sqlBuffer.append("        AND WKS.DATA_SAKUSEI_DT = ? ");
        sqlBuffer.append("        AND WKS.URIKAKE_KOMOKU_CD = ' ' ");
        sqlBuffer.append("        AND RT.TENPO_CD IS NOT NULL ");
        sqlBuffer.append("    ) WKS ");
        sqlBuffer.append("ON ");
        sqlBuffer.append("    ( ");
        sqlBuffer.append("            DTS.COMP_CD = WKS.COMP_CD ");
        sqlBuffer.append("        AND DTS.KEIJO_DT = WKS.KEIJO_DT ");
        sqlBuffer.append("        AND DTS.TENPO_CD = WKS.TENPO_CD ");
        sqlBuffer.append("        AND DTS.SEISANHYO_KOMOKU_CD = WKS.SEISANHYO_KOMOKU_CD ");
        sqlBuffer.append("    ) ");
        sqlBuffer.append("WHEN MATCHED THEN ");
        sqlBuffer.append("    UPDATE SET ");
        sqlBuffer.append("        DTS.POS_VL = DTS.POS_VL + WKS.KINGAKU_VL, ");
        sqlBuffer.append("        DTS.SEISAN_VL = DTS.SEISAN_VL + WKS.KINGAKU_VL, ");
        sqlBuffer.append("        DTS.UPDATE_USER_ID = ?, ");
        sqlBuffer.append("        DTS.UPDATE_TS = ? ");
        sqlBuffer.append("WHEN NOT MATCHED THEN ");
        sqlBuffer.append("    INSERT ");
        sqlBuffer.append("        ( ");
        sqlBuffer.append("            COMP_CD, ");
        sqlBuffer.append("            KEIJO_DT, ");
        sqlBuffer.append("            TENPO_CD, ");
        sqlBuffer.append("            SEISANHYO_KOMOKU_CD, ");
        sqlBuffer.append("            POS_VL, ");
        sqlBuffer.append("            SEISAN_VL, ");
        sqlBuffer.append("            POS_QT, ");
        sqlBuffer.append("            SEISAN_QT, ");
        sqlBuffer.append("            INSERT_USER_ID, ");
        sqlBuffer.append("            INSERT_TS, ");
        sqlBuffer.append("            UPDATE_USER_ID, ");
        sqlBuffer.append("            UPDATE_TS ");
        sqlBuffer.append("        ) ");
        sqlBuffer.append("    VALUES ");
        sqlBuffer.append("        ( ");
        sqlBuffer.append("            WKS.COMP_CD, ");
        sqlBuffer.append("            WKS.KEIJO_DT, ");
        sqlBuffer.append("            WKS.TENPO_CD, ");
        sqlBuffer.append("            WKS.SEISANHYO_KOMOKU_CD, ");
        sqlBuffer.append("            WKS.KINGAKU_VL, ");
        sqlBuffer.append("            WKS.KINGAKU_VL, ");
        sqlBuffer.append("            '0', ");
        sqlBuffer.append("            '0', ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ?, ");
        sqlBuffer.append("            ? ");
        sqlBuffer.append("        ) ");

        //sqlBuffer.append("INSERT ");
        //sqlBuffer.append("    INTO DT_TEN_SEISAN ");
        //sqlBuffer.append("    (");
        //sqlBuffer.append("    COMP_CD, ");
        //sqlBuffer.append("    KEIJO_DT, ");
        //sqlBuffer.append("    TENPO_CD, ");
        //sqlBuffer.append("    SEISANHYO_KOMOKU_CD, ");
        //sqlBuffer.append("    POS_VL, ");
        //sqlBuffer.append("    SEISAN_VL, ");
        //sqlBuffer.append("    POS_QT, ");
        //sqlBuffer.append("    SEISAN_QT, ");
        //sqlBuffer.append("    INSERT_USER_ID, ");
        //sqlBuffer.append("    INSERT_TS, ");
        //sqlBuffer.append("    UPDATE_USER_ID, ");
        //sqlBuffer.append("    UPDATE_TS) ");
        //sqlBuffer.append("    (");
        //sqlBuffer.append("    SELECT ");
        // 2016/05/31 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("        ?, ?, WKS.TENPO_CD, ?, ");
        //sqlBuffer.append("        ?,");
        //sqlBuffer.append("       WKS.KEIJO_DT,");
        //sqlBuffer.append("       WKS.TENPO_CD,");
        //sqlBuffer.append("       WKS.SEISANHYO_KOMOKU_CD,");
        //sqlBuffer.append("       WKS.KINGAKU_VL,");
        //sqlBuffer.append("       WKS.KINGAKU_VL,");
        // 2016/05/31 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

        // カラム名が非空場合
        //if (!"".equals(PosName)) {
        //    sqlBuffer.append("        SUM(" + PosName + "), ");
        //} else {
        //    sqlBuffer.append("        '0',");
        //}
        // カラム名が非空場合
        //if (!"".equals(PosName)) {
        //    sqlBuffer.append("        SUM(" + PosName + "), ");
        //} else {
        //    sqlBuffer.append("        '0',");
        //}
        //sqlBuffer.append("        '0', '0', ?, ?, ?, ? ");
        //sqlBuffer.append("    FROM ");
        //sqlBuffer.append("        WK_KAIKEI_SEISAN WKS ");
        //sqlBuffer.append("    INNER JOIN");
        //sqlBuffer.append("        DT_TEN_SEISAN_STATE DTSS ");
        //sqlBuffer.append("    ON DTSS.COMP_CD = WKS.COMP_CD ");
        //sqlBuffer.append("    AND DTSS.KEIJO_DT = WKS.KEIJO_DT ");
        //sqlBuffer.append("    AND DTSS.TENPO_CD = WKS.TENPO_CD ");
        //sqlBuffer.append("    AND DTSS.SEISAN_STATE_FG <> '" + UriageCommonConstants.UPDATE_FLAG + "' ");
        //sqlBuffer.append("    LEFT JOIN ");
        //sqlBuffer.append("        R_TENPO RT ");
        //sqlBuffer.append("    ON HOJIN_CD = ? ");
        //sqlBuffer.append("    AND TENPO_KB = " + TENPO_KUBUN);
        //sqlBuffer.append("    AND KAITEN_DT <= ? ");
        //sqlBuffer.append("    AND ZAIMU_END_DT >= ? ");
        //sqlBuffer.append("    AND DELETE_FG = " + DELETE_FLG);
        //sqlBuffer.append("    AND RT.TENPO_CD = WKS.TENPO_CD ");

        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("    LEFT JOIN ");
        //sqlBuffer.append("        R_SEISANHYO_KOMOKU RSK ");
        //sqlBuffer.append("    ON RSK.COMP_CD = WKS.COMP_CD ");
        //sqlBuffer.append("    AND RSK.SEISANHYO_KOMOKU_CD = WKS.SEISANHYO_KOMOKU_CD ");
        //sqlBuffer.append("    AND RSK.TEKIYO_START_DT = ? ");
        //sqlBuffer.append("    AND RSK.DELETE_FG = '0' ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

        //sqlBuffer.append("    WHERE ");
        //sqlBuffer.append("        WKS.COMP_CD = ? ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        //sqlBuffer.append("    AND WKS.KEIJO_DT = ? ");
        //sqlBuffer.append("    AND WKS.DATA_SAKUSEI_DT = ? ");
        //sqlBuffer.append("    AND WKS.URIKAKE_KOMOKU_CD = ' ' ");
        // 2016/05/24 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        //sqlBuffer.append("    AND RT.TENPO_CD IS NOT NULL) ");
        //sqlBuffer.append("    GROUP BY ");
        //sqlBuffer.append("        WKS.TENPO_CD) ");
        // #3637 2017.01.20 T.Kamei MOD (E)

        return sqlBuffer.toString();
    }

    /**
     *  OutputBeanクラス
     */
    private class TenbetuSeisanDataShukeiDaoOutputBean {

        // 精算票項目マスタ.精算票項目コード
        private String seisanhyoKomokuCd = null;

        // 精算票項目マスタ.POSデータカラム名
        private String posDataColumnNa = null;

        // 売掛項目マスタ.精算票項目コード
        private String urikakeSeisanhyoKomokuCD = null;

        // 売掛項目マスタ.POSデータカラム名
        private String urikakePosDataColumnNa = null;

        // 売掛項目マスタ.売掛項目コード
        private String urikakeKomokuCd = null;

        // 売掛項目マスタ.修正不可フラグ
        private String syuseiFukaFg = null;

        /**
         * @return posDataColumnNa
         */
        public String getPosDataColumnNa() {
            return posDataColumnNa;
        }

        /**
         * @param posDataColumnNa 設定する posDataColumnNa
         */
        public void setPosDataColumnNa(String posDataColumnNa) {
            this.posDataColumnNa = posDataColumnNa;
        }

        /**
         * @return seisanhyoKomokuCd
         */
        public String getSeisanhyoKomokuCd() {
            return seisanhyoKomokuCd;
        }

        /**
         * @param seisanhyoKomokuCd 設定する seisanhyoKomokuCd
         */
        public void setSeisanhyoKomokuCd(String seisanhyoKomokuCd) {
            this.seisanhyoKomokuCd = seisanhyoKomokuCd;
        }

        /**
         * @return urikakeKomokuCd
         */
        public String getUrikakeKomokuCd() {
            return urikakeKomokuCd;
        }

        /**
         * @param urikakeKomokuCd 設定する urikakeKomokuCd
         */
        public void setUrikakeKomokuCd(String urikakeKomokuCd) {
            this.urikakeKomokuCd = urikakeKomokuCd;
        }

        /**
         * @return urikakePosDataColumnNa
         */
        public String getUrikakePosDataColumnNa() {
            return urikakePosDataColumnNa;
        }

        /**
         * @param urikakePosDataColumnNa 設定する urikakePosDataColumnNa
         */
        public void setUrikakePosDataColumnNa(String urikakePosDataColumnNa) {
            this.urikakePosDataColumnNa = urikakePosDataColumnNa;
        }

        /**
         * @return urikakeSeisanhyoKomokuCD
         */
        public String getUrikakeSeisanhyoKomokuCD() {
            return urikakeSeisanhyoKomokuCD;
        }

        /**
         * @param urikakeSeisanhyoKomokuCD 設定する urikakeSeisanhyoKomokuCD
         */
        public void setUrikakeSeisanhyoKomokuCD(String urikakeSeisanhyoKomokuCD) {
            this.urikakeSeisanhyoKomokuCD = urikakeSeisanhyoKomokuCD;
        }

        /**
         * @return syuseiFukaFg
         */
        public String getSyuseiFukaFg() {
            return syuseiFukaFg;
        }

        /**
         * @param syuseiFukaFg 設定する syuseiFukaFg
         */
        public void setSyuseiFukaFg(String syuseiFukaFg) {
            this.syuseiFukaFg = syuseiFukaFg;
        }
}

    public static void main(String[] arg) {
        try {
            DaoIf dao = new KaikeiSeisanUriageDataTorikomiDao();
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