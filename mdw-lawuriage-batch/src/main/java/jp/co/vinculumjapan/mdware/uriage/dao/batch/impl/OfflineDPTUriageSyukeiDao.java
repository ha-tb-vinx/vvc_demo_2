package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.vinculumjapan.mdware.common.util.CalcUtility;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
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
 * <p>タイトル： OfflineDPTUriageSyukeiDao クラス</p>
 * <p>説明　　： 「オフラインDPT精算売上ワーク」」を条件でデータを抽出し「店別DPT売上データ」に挿入する。
 *                その際、「オフラインDPT精算売上ワーク」を「DPT精算売上(仕入用)ワーク」にINSする。             
 * </p>
 * <p>著作権　： Copyright (c) 2009</p>
 * <p>会社名　： Vinculum Japan Corp.</p>
 * @author   J.Lu
 * @version 1.00 (2009.05.20) 初版作成
 */
public class OfflineDPTUriageSyukeiDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチID
    private static final String BATCH_ID = "URIB091020";
    // バッチ名称
    private static final String BATCH_NAME = "オフラインDPT売上集計処理";
    // エラー表示用DPT取得
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラー表示用DPT
    private static final String ERR_HYOJI_DPT = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");
    // 集計DPT
    private static final String SYUKEI_DPT = "9999";
    // DPT精算状況ﾌﾗｸﾞ(取込済)
    private static final String BUNRUI1_SEISAN_STATE_FG_1 = "1";
    // DPT精算状況ﾌﾗｸﾞ(エラー)
    private static final String BUNRUI1_SEISAN_STATE_FG_9 = "9";
    // DB日付取得
    private String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // エラーDPTコード件数
    private static final String CNT = "CNT";

    /**
     * オフラインDPT売上集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // 店別DPT売上データ更新件数
        int intDtTenDptUriUpdate = 0;
        // 店別DPT売上データ追加件数
        int intDtTenDptUriInsert = 0;
        // 店別精算状況データ更新件数
        int intDtTenSeisanStateUpdate = 0;
        // IFDPT精算売上（仕入用）ワーク追加件数
        int intWkDptSeisanUriShiireInsert = 0;
        // IFDPT精算売上（仕入用）ワーク更新件数
        int intWkDptSeisanUriShiireUpdateRui = 0;
        // IFDPT精算売上（仕入用）ワーク更新件数
        int intWkDptSeisanUriShiireUpdate = 0;
        // 売上金額（税込）
        String strUriageKin = "0";
        // 売上数量
        String strUriageQt = "0";
        // DPT客数
        String strDptKyakuQt = "0";
        // 店舗コード
        String strTenpoCode = "";
        // 計上日
        String strKeijoDt = "";
        // エラーフラグ
        int intErrorFlg = 0;

        // 開始ログを出力する
        invoker.infoLog(strUserId + "　：　オフラインDPT売上集計処理を開始します。");
        invoker.infoLog(strUserId + "　：　IFDPT精算売上（仕入用）ワーク更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　IFDPT精算売上（仕入用）ワーク追加処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別DPT売上データ追加処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別DPT売上データ更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新処理を開始します。");

        // オフラインDPT精算売上ワーク
        ResultSet resultSet = null;
        // ｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェック
        ResultSet ErrorDptRs = null;

        // 会計用店DPTマスタ（会計共通）チェック
        PreparedStatementEx checkPs = invoker.getDataBase().prepareStatement("selectRFiTenpoBunrui1");
        // 店別DPT売上データ新規
        PreparedStatementEx createDtTenDptUriPs = invoker.getDataBase().prepareStatement("createDtTenDptUri");
        // 店別DPT売上データ更新
        PreparedStatementEx updateDtTenDptUriPs = invoker.getDataBase().prepareStatement("updateDtTenDptUri");
        // 店別精算状況データ更新
        PreparedStatementEx updatePs = invoker.getDataBase().prepareStatement("updateDtTenSeisanState");
        // DPT精算売上(仕入用)ワーク作成
        PreparedStatementEx wkCreatePs = invoker.getDataBase().prepareStatement("createWkDptSeisanUriShiire");
        // 店別DPT売上データにｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェック
        PreparedStatementEx checkErrorDpt = invoker.getDataBase().prepareStatement("checkErrorDpt");
        // 「オフラインDPT精算売上ワーク」から対象店舗データ抽出する。（集計DPT用）
        PreparedStatementEx selectWkOfflineDptSeisanUriSum = invoker.getDataBase().prepareStatement("selectWkOfflineDptSeisanUriSum");
        
        // 「オフラインDPT精算売上ワーク」から対象店舗データ抽出する。
        PreparedStatementEx preparedStatementEx = invoker.getDataBase().prepareStatement("selectWkOfflineDptSeisanUri");
        // DPT精算売上(仕入用)ワーク更新
        PreparedStatementEx wkUpdatePs = invoker.getDataBase().prepareStatement("updateWkDptSeisanUriShiire");

        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        // 法人コード
        preparedStatementEx.setString(2, COMP_CD);
        // 集計DPT
        preparedStatementEx.setString(3, SYUKEI_DPT);

        // SQLを実行する
        resultSet = preparedStatementEx.executeQuery();

        // データ取得後、格納用リスト
        List list = new ArrayList();

        OfflineDPTUriageSyukeiDaoOutputBean ouputBean = null;

        // SELECT列リストで指定した別名から、"_" を外してOutputRowBeanの同名プロパティにセットする為のルール
        String[][] rules = { UriageCommonConstants.RULES };

        while (resultSet.next()) {
            ouputBean = new OfflineDPTUriageSyukeiDaoOutputBean();

            // OutputBeanの同名プロパティにデータを全てコピーする
            DaoUtils.copy(ouputBean, DaoUtils.getMapFromRs(resultSet), rules);
            list.add(ouputBean);
        }

        int intSize = list.size();

        for (int i = UriageCommonConstants.ZERO_INT; i < intSize; i++) {

            ResultSet rs = null;

            OfflineDPTUriageSyukeiDaoOutputBean bean = (OfflineDPTUriageSyukeiDaoOutputBean) list.get(i);

            // 店別DPT売上データの作成
            // 該当レコードの「退避（精算）：計上日」、「退避（精算）：店舗コード」と前レコードの「退避（精算）：計上日」、
            // 「退避（精算）：店舗コード」同じ場合、又は１件目レコードの場合
            if ((i == 0) || ((strTenpoCode.equals(bean.getTenpoCd())) && (strKeijoDt.equals(bean.getKeijoDt())))) {

                // 「退避（精算）：DPTコード」が9999以外場合、会計用店DPTマスタ（会計共通）チェック
                if (!SYUKEI_DPT.equals(bean.getBunrui1Cd())) {
                    // 法人コード
                    checkPs.setString(1, COMP_CD);
                    // 店舗コード
                    checkPs.setString(2, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(3, bean.getBunrui1Cd());
                    // 法人コード
                    checkPs.setString(4, COMP_CD);
                    // 有効日
                    checkPs.setString(5, bean.getKeijoDt());
                    // 店舗コード
                    checkPs.setString(6, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(7, bean.getBunrui1Cd());

                    // SQLを実行する
                    rs = checkPs.executeQuery();
                }

                // 「退避（精算）：DPTコード」が9999 又は ①-2-3-1で取得したデータがある場合
                if ((SYUKEI_DPT.equals(bean.getBunrui1Cd())) || (rs.next())) {

                    // 「退避（売上）：店舗コード」＝""の場合、店別DPT売上データ作成
                    if (bean.getDtduTenpoCd().trim().length() == 0) {
                        // 法人コード
                        createDtTenDptUriPs.setString(1, COMP_CD);
                        // 計上日
                        createDtTenDptUriPs.setString(2, bean.getKeijoDt());
                        // 店舗コード
                        createDtTenDptUriPs.setString(3, bean.getTenpoCd());
                        // DPTコード
                        createDtTenDptUriPs.setString(4, bean.getBunrui1Cd());
                        // POS金額
                        createDtTenDptUriPs.setString(5, bean.getUriageKomiVl());
                        // 売上金額（税込）
                        createDtTenDptUriPs.setString(6, bean.getUriageKomiVl());
                        // POS数量
                        createDtTenDptUriPs.setString(7, bean.getUriageQt());
                        // 売上数量
                        createDtTenDptUriPs.setString(8, bean.getUriageQt());
                        // POS客数
                        createDtTenDptUriPs.setString(9, bean.getBunrui1KyakuQt());
                        // 客数
                        createDtTenDptUriPs.setString(10, bean.getBunrui1KyakuQt());
                        // 作成者ID
                        createDtTenDptUriPs.setString(11, BATCH_ID);
                        // 作成年月日
                        createDtTenDptUriPs.setString(12, DBSERVER_DT);
                        // 更新者ID
                        createDtTenDptUriPs.setString(13, BATCH_ID);
                        // 更新年月日
                        createDtTenDptUriPs.setString(14, DBSERVER_DT);

                        intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                    }
                    // 「退避（売上）：店舗コード」≠""の場合、店別DPT売上データ更新
                    else {
                        // POS金額
                        updateDtTenDptUriPs.setString(1, bean.getUriageKomiVl());
                        // 売上金額（税込）
                        updateDtTenDptUriPs.setString(2, bean.getUriageKomiVl());
                        // POS数量    
                        updateDtTenDptUriPs.setString(3, bean.getUriageQt());
                        // 売上数量    
                        updateDtTenDptUriPs.setString(4, bean.getUriageQt());
                        // POS客数    
                        updateDtTenDptUriPs.setString(5, bean.getBunrui1KyakuQt());
                        // 客数      
                        updateDtTenDptUriPs.setString(6, bean.getBunrui1KyakuQt());
                        // 更新者ID
                        updateDtTenDptUriPs.setString(7, BATCH_ID);
                        // 更新年月日
                        updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                        // 法人コード
                        updateDtTenDptUriPs.setString(9, COMP_CD);
                        // 計上日
                        updateDtTenDptUriPs.setString(10, bean.getKeijoDt());
                        // 店舗コード
                        updateDtTenDptUriPs.setString(11, bean.getTenpoCd());
                        // DPTコード
                        updateDtTenDptUriPs.setString(12, bean.getBunrui1Cd());

                        intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                    }

                    // DPT精算売上(仕入用)ワーク更新
                    intWkDptSeisanUriShiireUpdate = 0;
                    // 売上金額（税込）
                    wkUpdatePs.setString(1, bean.getUriageKomiVl());
                    // 法人コード
                    wkUpdatePs.setString(2, COMP_CD);
                    // 計上日
                    wkUpdatePs.setString(3, bean.getKeijoDt());
                    // 店舗コード
                    wkUpdatePs.setString(4, bean.getTenpoCd());
                    // DPTコード
                    wkUpdatePs.setString(5, bean.getBunrui1Cd());

                    intWkDptSeisanUriShiireUpdate = wkUpdatePs.executeUpdate();

                    intWkDptSeisanUriShiireUpdateRui = intWkDptSeisanUriShiireUpdateRui + intWkDptSeisanUriShiireUpdate;

                    if (intWkDptSeisanUriShiireUpdate==0) {
                        
                        // DPT精算売上(仕入用)ワーク作成
                        // 法人コード
                        wkCreatePs.setString(1, COMP_CD);
                        // 計上日
                        wkCreatePs.setString(2, bean.getKeijoDt());
                        // 店舗コード
                        wkCreatePs.setString(3, bean.getTenpoCd());
                        // DPTコード
                        wkCreatePs.setString(4, bean.getBunrui1Cd());
                        // 売上金額（税込）
                        wkCreatePs.setString(5, bean.getUriageKomiVl());

                        intWkDptSeisanUriShiireInsert = intWkDptSeisanUriShiireInsert + wkCreatePs.executeUpdate();
                        
                    }

                } else {

                    invoker.warnLog(strUserId + "　：　計上日：" + bean.getKeijoDt() + " " + "店舗コード：" + bean.getTenpoCd() + " " + "DPTコード：" + bean.getBunrui1Cd() + " " + "が会計用店DPTマスタ（会計共通）に存在しません。");

                    // 売上金額（税込）
                    strUriageKin = CalcUtility.plus(strUriageKin, bean.getUriageKomiVl());
                    // 売上数量
                    strUriageQt = CalcUtility.plus(strUriageQt, bean.getUriageQt());
                    // DPT客数
                    strDptKyakuQt = CalcUtility.plus(strDptKyakuQt, bean.getBunrui1KyakuQt());
                    // エラーフラグ
                    intErrorFlg = 1;
                }
            }

            // 該当レコードの「退避（精算）：計上日」、「退避（精算）：店舗コード」と前レコードの「退避（精算）：計上日」、
            // 「退避（精算）：店舗コード」 いずれ同じではない且つ１件目レコード以外の場合
            if (((i != 0) && ((!strTenpoCode.equals(bean.getTenpoCd())) || (!strKeijoDt.equals(bean.getKeijoDt()))))) {

                // エラーDPTコード件数
                int intErrorDpt = 0;

                // 「退避（店別）：エラーフラグ」＝1 場合、ｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPTでレコードを作成する。
                if (intErrorFlg == 1) {

                    // 店別DPT売上データにｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェックを行う
                    // 法人コード
                    checkErrorDpt.setString(1, COMP_CD);
                    // 計上日
                    checkErrorDpt.setString(2, strKeijoDt);
                    // 店舗コード
                    checkErrorDpt.setString(3, strTenpoCode);
                    // DPTコード
                    checkErrorDpt.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                    ErrorDptRs = checkErrorDpt.executeQuery();

                    if (ErrorDptRs.next()) {
                        // エラーDPTコード件数
                        intErrorDpt = ErrorDptRs.getInt(CNT);
                    }

                    // エラーDPTコード件数 ＝０の場合、新規処理を行う
                    if (intErrorDpt == 0) {
                        // 法人コード
                        createDtTenDptUriPs.setString(1, COMP_CD);
                        // 計上日
                        createDtTenDptUriPs.setString(2, strKeijoDt);
                        // 店舗コード
                        createDtTenDptUriPs.setString(3, strTenpoCode);
                        // DPTコード
                        createDtTenDptUriPs.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));
                        // POS金額
                        createDtTenDptUriPs.setString(5, strUriageKin);
                        // 売上金額（税込）
                        createDtTenDptUriPs.setString(6, strUriageKin);
                        // POS数量
                        createDtTenDptUriPs.setString(7, strUriageQt);
                        // 売上数量
                        createDtTenDptUriPs.setString(8, strUriageQt);
                        // POS客数
                        createDtTenDptUriPs.setString(9, strDptKyakuQt);
                        // 客数
                        createDtTenDptUriPs.setString(10, strDptKyakuQt);
                        // 作成者ID
                        createDtTenDptUriPs.setString(11, BATCH_ID);
                        // 作成年月日
                        createDtTenDptUriPs.setString(12, DBSERVER_DT);
                        // 更新者ID
                        createDtTenDptUriPs.setString(13, BATCH_ID);
                        // 更新年月日
                        createDtTenDptUriPs.setString(14, DBSERVER_DT);

                        intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                    }
                    // 上記以外の場合、更新処理を行う
                    else {
                        // POS金額
                        updateDtTenDptUriPs.setString(1, strUriageKin);
                        // 売上金額（税込）
                        updateDtTenDptUriPs.setString(2, strUriageKin);
                        // POS数量    
                        updateDtTenDptUriPs.setString(3, strUriageQt);
                        // 売上数量    
                        updateDtTenDptUriPs.setString(4, strUriageQt);
                        // POS客数    
                        updateDtTenDptUriPs.setString(5, strDptKyakuQt);
                        // 客数      
                        updateDtTenDptUriPs.setString(6, strDptKyakuQt);
                        // 更新者ID
                        updateDtTenDptUriPs.setString(7, BATCH_ID);
                        // 更新年月日
                        updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                        // 法人コード
                        updateDtTenDptUriPs.setString(9, COMP_CD);
                        // 計上日
                        updateDtTenDptUriPs.setString(10, strKeijoDt);
                        // 店舗コード
                        updateDtTenDptUriPs.setString(11, strTenpoCode);
                        // DPTコード
                        updateDtTenDptUriPs.setString(12, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                        intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                    }
                }

                // 退避領域を初期化する
                // 売上金額（税込）
                strUriageKin = "0";
                // 売上数量
                strUriageQt = "0";
                // DPT客数
                strDptKyakuQt = "0";           

                // 「退避（店別）：エラーフラグ」＝0 場合、店別DPT売上データにｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェックを行う
                if (intErrorFlg == 0) {
                    // 法人コード
                    checkErrorDpt.setString(1, COMP_CD);
                    // 計上日
                    checkErrorDpt.setString(2, strKeijoDt);
                    // 店舗コード
                    checkErrorDpt.setString(3, strTenpoCode);
                    // DPTコード
                    checkErrorDpt.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                    ErrorDptRs = checkErrorDpt.executeQuery();

                    if (ErrorDptRs.next()) {
                        // エラーDPTコード件数
                        intErrorDpt = ErrorDptRs.getInt(CNT);
                    }
                }

                // 「店別精算状況データ」に処理結果を反映する
                // DPT精算状況ﾌﾗｸﾞ
                // 「退避（店別）：エラーフラグ」＝１ 又は「退避：エラー集計DPTコード件数」が０以外の場合【9】(取込済)をセット
                if ((intErrorFlg == 1) || (intErrorDpt != 0)) {
                    updatePs.setString(1, BUNRUI1_SEISAN_STATE_FG_9);
                } else {
                    updatePs.setString(1, BUNRUI1_SEISAN_STATE_FG_1);
                }
                // 更新者ID
                updatePs.setString(2, BATCH_ID);
                // 更新年月日
                updatePs.setString(3, DBSERVER_DT);
                // 法人コード
                updatePs.setString(4, COMP_CD);
                // 計上日
                updatePs.setString(5, strKeijoDt);
                // 店舗コード  
                updatePs.setString(6, strTenpoCode);

                intDtTenSeisanStateUpdate = intDtTenSeisanStateUpdate + updatePs.executeUpdate();

                // エラーフラグ
                intErrorFlg = 0;

                // 集計DPT用のデータを取得
                // 法人コード
                selectWkOfflineDptSeisanUriSum.setString(1, COMP_CD);
                // 集計DPT
                selectWkOfflineDptSeisanUriSum.setString(2, SYUKEI_DPT);
                // 法人コード
                selectWkOfflineDptSeisanUriSum.setString(3, COMP_CD);
                // 計上日
                selectWkOfflineDptSeisanUriSum.setString(4, strKeijoDt);
                // 店舗コード
                selectWkOfflineDptSeisanUriSum.setString(5, strTenpoCode);
                // 集計DPT
                selectWkOfflineDptSeisanUriSum.setString(6, SYUKEI_DPT);

                // SQLを実行する
                resultSet = selectWkOfflineDptSeisanUriSum.executeQuery();                
                resultSet.next();

                OfflineDPTUriageSyukeiDaoOutputBean syukeiBean = new OfflineDPTUriageSyukeiDaoOutputBean();

                // syukeiBeanの同名プロパティにデータを全てコピーする
                DaoUtils.copy(syukeiBean, DaoUtils.getMapFromRs(resultSet), rules);

                // 「退避（売上）：店舗コード」＝""の場合、店別DPT売上データ作成（集計DPT）
                if (syukeiBean.getDtduTenpoCd().trim().length() == 0) {
                    // 法人コード
                    createDtTenDptUriPs.setString(1, COMP_CD);
                    // 計上日
                    createDtTenDptUriPs.setString(2, syukeiBean.getKeijoDt());
                    // 店舗コード
                    createDtTenDptUriPs.setString(3, syukeiBean.getTenpoCd());
                    // DPTコード
                    createDtTenDptUriPs.setString(4, SYUKEI_DPT);
                    // POS金額
                    createDtTenDptUriPs.setString(5, syukeiBean.getUriageKomiVl());
                    // 売上金額（税込）
                    createDtTenDptUriPs.setString(6, syukeiBean.getUriageKomiVl());
                    // POS数量
                    createDtTenDptUriPs.setString(7, syukeiBean.getUriageQt());
                    // 売上数量
                    createDtTenDptUriPs.setString(8, syukeiBean.getUriageQt());
                    // POS客数
                    createDtTenDptUriPs.setString(9, syukeiBean.getBunrui1KyakuQt());
                    // 客数
                    createDtTenDptUriPs.setString(10, syukeiBean.getBunrui1KyakuQt());
                    // 作成者ID
                    createDtTenDptUriPs.setString(11, BATCH_ID);
                    // 作成年月日
                    createDtTenDptUriPs.setString(12, DBSERVER_DT);
                    // 更新者ID
                    createDtTenDptUriPs.setString(13, BATCH_ID);
                    // 更新年月日
                    createDtTenDptUriPs.setString(14, DBSERVER_DT);

                    intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                }
                // 「退避（売上）：店舗コード」≠""の場合、店別DPT売上データ更新（集計DPT）
                else {
                    // POS金額
                    updateDtTenDptUriPs.setString(1, syukeiBean.getUriageKomiVl());
                    // 売上金額（税込）
                    updateDtTenDptUriPs.setString(2, syukeiBean.getUriageKomiVl());
                    // POS数量    
                    updateDtTenDptUriPs.setString(3, syukeiBean.getUriageQt());
                    // 売上数量    
                    updateDtTenDptUriPs.setString(4, syukeiBean.getUriageQt());
                    // POS客数    
                    updateDtTenDptUriPs.setString(5, syukeiBean.getBunrui1KyakuQt());
                    // 客数      
                    updateDtTenDptUriPs.setString(6, syukeiBean.getBunrui1KyakuQt());
                    // 更新者ID
                    updateDtTenDptUriPs.setString(7, BATCH_ID);
                    // 更新年月日
                    updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                    // 法人コード
                    updateDtTenDptUriPs.setString(9, COMP_CD);
                    // 計上日
                    updateDtTenDptUriPs.setString(10, syukeiBean.getKeijoDt());
                    // 店舗コード
                    updateDtTenDptUriPs.setString(11, syukeiBean.getTenpoCd());
                    // DPTコード
                    updateDtTenDptUriPs.setString(12, SYUKEI_DPT);

                    intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                }

                // 「退避（精算）：DPTコード」が9999以外場合、会計用店DPTマスタ（会計共通）チェック
                if (!SYUKEI_DPT.equals(bean.getBunrui1Cd())) {
                    // 法人コード
                    checkPs.setString(1, COMP_CD);
                    // 店舗コード
                    checkPs.setString(2, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(3, bean.getBunrui1Cd());
                    // 法人コード
                    checkPs.setString(4, COMP_CD);
                    // 有効日
                    checkPs.setString(5, bean.getKeijoDt());
                    // 店舗コード
                    checkPs.setString(6, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(7, bean.getBunrui1Cd());

                    // SQLを実行する
                    rs = checkPs.executeQuery();
                }

                // 「退避（精算）：DPTコード」が9999 又は ①-2-3-1で取得したデータがある場合
                if ((SYUKEI_DPT.equals(bean.getBunrui1Cd())) || (rs.next())) {

                    // 「退避（売上）：店舗コード」＝""の場合、店別DPT売上データ作成
                    if (bean.getDtduTenpoCd().trim().length() == 0) {
                        // 法人コード
                        createDtTenDptUriPs.setString(1, COMP_CD);
                        // 計上日
                        createDtTenDptUriPs.setString(2, bean.getKeijoDt());
                        // 店舗コード
                        createDtTenDptUriPs.setString(3, bean.getTenpoCd());
                        // DPTコード
                        createDtTenDptUriPs.setString(4, bean.getBunrui1Cd());
                        // POS金額
                        createDtTenDptUriPs.setString(5, bean.getUriageKomiVl());
                        // 売上金額（税込）
                        createDtTenDptUriPs.setString(6, bean.getUriageKomiVl());
                        // POS数量
                        createDtTenDptUriPs.setString(7, bean.getUriageQt());
                        // 売上数量
                        createDtTenDptUriPs.setString(8, bean.getUriageQt());
                        // POS客数
                        createDtTenDptUriPs.setString(9, bean.getBunrui1KyakuQt());
                        // 客数
                        createDtTenDptUriPs.setString(10, bean.getBunrui1KyakuQt());
                        // 作成者ID
                        createDtTenDptUriPs.setString(11, BATCH_ID);
                        // 作成年月日
                        createDtTenDptUriPs.setString(12, DBSERVER_DT);
                        // 更新者ID
                        createDtTenDptUriPs.setString(13, BATCH_ID);
                        // 更新年月日
                        createDtTenDptUriPs.setString(14, DBSERVER_DT);

                        intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                    }
                    // 「退避（売上）：店舗コード」≠""の場合、店別DPT売上データ更新
                    else {
                        // POS金額
                        updateDtTenDptUriPs.setString(1, bean.getUriageKomiVl());
                        // 売上金額（税込）
                        updateDtTenDptUriPs.setString(2, bean.getUriageKomiVl());
                        // POS数量    
                        updateDtTenDptUriPs.setString(3, bean.getUriageQt());
                        // 売上数量    
                        updateDtTenDptUriPs.setString(4, bean.getUriageQt());
                        // POS客数    
                        updateDtTenDptUriPs.setString(5, bean.getBunrui1KyakuQt());
                        // 客数      
                        updateDtTenDptUriPs.setString(6, bean.getBunrui1KyakuQt());
                        // 更新者ID
                        updateDtTenDptUriPs.setString(7, BATCH_ID);
                        // 更新年月日
                        updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                        // 法人コード
                        updateDtTenDptUriPs.setString(9, COMP_CD);
                        // 計上日
                        updateDtTenDptUriPs.setString(10, bean.getKeijoDt());
                        // 店舗コード
                        updateDtTenDptUriPs.setString(11, bean.getTenpoCd());
                        // DPTコード
                        updateDtTenDptUriPs.setString(12, bean.getBunrui1Cd());

                        intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                    }

                    // DPT精算売上(仕入用)ワーク更新
                    intWkDptSeisanUriShiireUpdate = 0;
                    // 売上金額（税込）
                    wkUpdatePs.setString(1, bean.getUriageKomiVl());
                    // 法人コード
                    wkUpdatePs.setString(2, COMP_CD);
                    // 計上日
                    wkUpdatePs.setString(3, bean.getKeijoDt());
                    // 店舗コード
                    wkUpdatePs.setString(4, bean.getTenpoCd());
                    // DPTコード
                    wkUpdatePs.setString(5, bean.getBunrui1Cd());

                    intWkDptSeisanUriShiireUpdate = wkUpdatePs.executeUpdate();

                    intWkDptSeisanUriShiireUpdateRui = intWkDptSeisanUriShiireUpdateRui + intWkDptSeisanUriShiireUpdate;

                    if (intWkDptSeisanUriShiireUpdate==0) {
                        
                        // DPT精算売上(仕入用)ワーク作成
                        // 法人コード
                        wkCreatePs.setString(1, COMP_CD);
                        // 計上日
                        wkCreatePs.setString(2, bean.getKeijoDt());
                        // 店舗コード
                        wkCreatePs.setString(3, bean.getTenpoCd());
                        // DPTコード
                        wkCreatePs.setString(4, bean.getBunrui1Cd());
                        // 売上金額（税込）
                        wkCreatePs.setString(5, bean.getUriageKomiVl());

                        intWkDptSeisanUriShiireInsert = intWkDptSeisanUriShiireInsert + wkCreatePs.executeUpdate();

                    }
                    
                } else {

                    invoker.warnLog(strUserId + "　：　計上日：" + bean.getKeijoDt() + " " + "店舗コード：" + bean.getTenpoCd() + " " + "DPTコード：" + bean.getBunrui1Cd() + " " + "が会計用店DPTマスタ（会計共通）に存在しません。");

                    // 売上金額（税込）
                    strUriageKin = CalcUtility.plus(strUriageKin, bean.getUriageKomiVl());
                    // 売上数量
                    strUriageQt = CalcUtility.plus(strUriageQt, bean.getUriageQt());
                    // DPT客数
                    strDptKyakuQt = CalcUtility.plus(strDptKyakuQt, bean.getBunrui1KyakuQt());
                    // エラーフラグ
                    intErrorFlg = 1;
                }
            }

            // 最後レコードの場合
            if (i == (intSize - 1)) {
                // 店舗コードが最後レコードの「退避（精算）：店舗コード」を設定
                strTenpoCode = bean.getTenpoCd();
                // 計上日が最後レコードの「退避（精算）：計上日」を設定
                strKeijoDt = bean.getKeijoDt();

                // エラーDPTコード件数
                int intErrorDpt = 0;

                // 「退避（店別）：エラーフラグ」＝1 場合、ｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPTでレコードを作成する。
                if (intErrorFlg == 1) {

                    // 店別DPT売上データにｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェックを行う
                    // 法人コード
                    checkErrorDpt.setString(1, COMP_CD);
                    // 計上日
                    checkErrorDpt.setString(2, strKeijoDt);
                    // 店舗コード
                    checkErrorDpt.setString(3, strTenpoCode);
                    // DPTコード
                    checkErrorDpt.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                    ErrorDptRs = checkErrorDpt.executeQuery();

                    if (ErrorDptRs.next()) {
                        // エラーDPTコード件数
                        intErrorDpt = ErrorDptRs.getInt(CNT);
                    }

                    // エラーDPTコード件数 ＝０の場合、新規処理を行う
                    if (intErrorDpt == 0) {
                        // 法人コード
                        createDtTenDptUriPs.setString(1, COMP_CD);
                        // 計上日
                        createDtTenDptUriPs.setString(2, strKeijoDt);
                        // 店舗コード
                        createDtTenDptUriPs.setString(3, strTenpoCode);
                        // DPTコード
                        createDtTenDptUriPs.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));
                        // POS金額
                        createDtTenDptUriPs.setString(5, strUriageKin);
                        // 売上金額（税込）
                        createDtTenDptUriPs.setString(6, strUriageKin);
                        // POS数量
                        createDtTenDptUriPs.setString(7, strUriageQt);
                        // 売上数量
                        createDtTenDptUriPs.setString(8, strUriageQt);
                        // POS客数
                        createDtTenDptUriPs.setString(9, strDptKyakuQt);
                        // 客数
                        createDtTenDptUriPs.setString(10, strDptKyakuQt);
                        // 作成者ID
                        createDtTenDptUriPs.setString(11, BATCH_ID);
                        // 作成年月日
                        createDtTenDptUriPs.setString(12, DBSERVER_DT);
                        // 更新者ID
                        createDtTenDptUriPs.setString(13, BATCH_ID);
                        // 更新年月日
                        createDtTenDptUriPs.setString(14, DBSERVER_DT);

                        intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                    }
                    // 上記以外の場合、更新処理を行う
                    else {
                        // POS金額
                        updateDtTenDptUriPs.setString(1, strUriageKin);
                        // 売上金額（税込）
                        updateDtTenDptUriPs.setString(2, strUriageKin);
                        // POS数量    
                        updateDtTenDptUriPs.setString(3, strUriageQt);
                        // 売上数量    
                        updateDtTenDptUriPs.setString(4, strUriageQt);
                        // POS客数    
                        updateDtTenDptUriPs.setString(5, strDptKyakuQt);
                        // 客数      
                        updateDtTenDptUriPs.setString(6, strDptKyakuQt);
                        // 更新者ID
                        updateDtTenDptUriPs.setString(7, BATCH_ID);
                        // 更新年月日
                        updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                        // 法人コード
                        updateDtTenDptUriPs.setString(9, COMP_CD);
                        // 計上日
                        updateDtTenDptUriPs.setString(10, strKeijoDt);
                        // 店舗コード
                        updateDtTenDptUriPs.setString(11, strTenpoCode);
                        // DPTコード
                        updateDtTenDptUriPs.setString(12, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                        intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                    }
                }

                // 退避領域を初期化する
                // 売上金額（税込）
                strUriageKin = "0";
                // 売上数量
                strUriageQt = "0";
                // DPT客数
                strDptKyakuQt = "0";

                // 「退避（店別）：エラーフラグ」＝0 場合、店別DPT売上データにｼｽﾃﾑﾏｽﾀｴﾗｰ集計DPT存在するかのチェックを行う
                if (intErrorFlg == 0) {
                    // 法人コード
                    checkErrorDpt.setString(1, COMP_CD);
                    // 計上日
                    checkErrorDpt.setString(2, strKeijoDt);
                    // 店舗コード
                    checkErrorDpt.setString(3, strTenpoCode);
                    // DPTコード
                    checkErrorDpt.setString(4, StringUtility.spaceFormat(ERR_HYOJI_DPT, 4));

                    ErrorDptRs = checkErrorDpt.executeQuery();

                    if (ErrorDptRs.next()) {
                        // エラーDPTコード件数
                        intErrorDpt = ErrorDptRs.getInt(CNT);
                    }
                }

                // 「店別精算状況データ」に処理結果を反映する
                // DPT精算状況ﾌﾗｸﾞ
                // 「退避（店別）：エラーフラグ」＝１ 又は「退避：エラー集計DPTコード件数」が０以外の場合【9】(取込済)をセット
                if ((intErrorFlg == 1) || (intErrorDpt != 0)) {
                    updatePs.setString(1, BUNRUI1_SEISAN_STATE_FG_9);
                } else {
                    updatePs.setString(1, BUNRUI1_SEISAN_STATE_FG_1);
                }
                // 更新者ID
                updatePs.setString(2, BATCH_ID);
                // 更新年月日
                updatePs.setString(3, DBSERVER_DT);
                // 法人コード
                updatePs.setString(4, COMP_CD);
                // 計上日
                updatePs.setString(5, strKeijoDt);
                // 店舗コード  
                updatePs.setString(6, strTenpoCode);

                intDtTenSeisanStateUpdate = intDtTenSeisanStateUpdate + updatePs.executeUpdate();

                // エラーフラグ
                intErrorFlg = 0;

                // 集計DPT用のデータを取得
                // 法人コード
                selectWkOfflineDptSeisanUriSum.setString(1, COMP_CD);
                // 集計DPT
                selectWkOfflineDptSeisanUriSum.setString(2, SYUKEI_DPT);
                // 法人コード
                selectWkOfflineDptSeisanUriSum.setString(3, COMP_CD);
                // 計上日
                selectWkOfflineDptSeisanUriSum.setString(4, strKeijoDt);
                // 店舗コード
                selectWkOfflineDptSeisanUriSum.setString(5, strTenpoCode);
                // 集計DPT
                selectWkOfflineDptSeisanUriSum.setString(6, SYUKEI_DPT);

                // SQLを実行する
                resultSet = selectWkOfflineDptSeisanUriSum.executeQuery();                
                resultSet.next();

                OfflineDPTUriageSyukeiDaoOutputBean syukeiBean = new OfflineDPTUriageSyukeiDaoOutputBean();

                // syukeiBeanの同名プロパティにデータを全てコピーする
                DaoUtils.copy(syukeiBean, DaoUtils.getMapFromRs(resultSet), rules);

                // 「退避（売上）：店舗コード」＝""の場合、店別DPT売上データ作成（集計DPT）
                if (syukeiBean.getDtduTenpoCd().trim().length() == 0) {
                    // 法人コード
                    createDtTenDptUriPs.setString(1, COMP_CD);
                    // 計上日
                    createDtTenDptUriPs.setString(2, syukeiBean.getKeijoDt());
                    // 店舗コード
                    createDtTenDptUriPs.setString(3, syukeiBean.getTenpoCd());
                    // DPTコード
                    createDtTenDptUriPs.setString(4, SYUKEI_DPT);
                    // POS金額
                    createDtTenDptUriPs.setString(5, syukeiBean.getUriageKomiVl());
                    // 売上金額（税込）
                    createDtTenDptUriPs.setString(6, syukeiBean.getUriageKomiVl());
                    // POS数量
                    createDtTenDptUriPs.setString(7, syukeiBean.getUriageQt());
                    // 売上数量
                    createDtTenDptUriPs.setString(8, syukeiBean.getUriageQt());
                    // POS客数
                    createDtTenDptUriPs.setString(9, syukeiBean.getBunrui1KyakuQt());
                    // 客数
                    createDtTenDptUriPs.setString(10, syukeiBean.getBunrui1KyakuQt());
                    // 作成者ID
                    createDtTenDptUriPs.setString(11, BATCH_ID);
                    // 作成年月日
                    createDtTenDptUriPs.setString(12, DBSERVER_DT);
                    // 更新者ID
                    createDtTenDptUriPs.setString(13, BATCH_ID);
                    // 更新年月日
                    createDtTenDptUriPs.setString(14, DBSERVER_DT);

                    intDtTenDptUriInsert = intDtTenDptUriInsert + createDtTenDptUriPs.executeUpdate();
                }
                // 「退避（売上）：店舗コード」≠""の場合、店別DPT売上データ更新（集計DPT）
                else {
                    // POS金額
                    updateDtTenDptUriPs.setString(1, syukeiBean.getUriageKomiVl());
                    // 売上金額（税込）
                    updateDtTenDptUriPs.setString(2, syukeiBean.getUriageKomiVl());
                    // POS数量    
                    updateDtTenDptUriPs.setString(3, syukeiBean.getUriageQt());
                    // 売上数量    
                    updateDtTenDptUriPs.setString(4, syukeiBean.getUriageQt());
                    // POS客数    
                    updateDtTenDptUriPs.setString(5, syukeiBean.getBunrui1KyakuQt());
                    // 客数      
                    updateDtTenDptUriPs.setString(6, syukeiBean.getBunrui1KyakuQt());
                    // 更新者ID
                    updateDtTenDptUriPs.setString(7, BATCH_ID);
                    // 更新年月日
                    updateDtTenDptUriPs.setString(8, DBSERVER_DT);
                    // 法人コード
                    updateDtTenDptUriPs.setString(9, COMP_CD);
                    // 計上日
                    updateDtTenDptUriPs.setString(10, syukeiBean.getKeijoDt());
                    // 店舗コード
                    updateDtTenDptUriPs.setString(11, syukeiBean.getTenpoCd());
                    // DPTコード
                    updateDtTenDptUriPs.setString(12, SYUKEI_DPT);

                    intDtTenDptUriUpdate = intDtTenDptUriUpdate + updateDtTenDptUriPs.executeUpdate();
                }

                // 「退避（精算）：DPTコード」が9999以外場合、会計用店DPTマスタ（会計共通）チェック
                if (!SYUKEI_DPT.equals(bean.getBunrui1Cd())) {
                    // 法人コード
                    checkPs.setString(1, COMP_CD);
                    // 店舗コード
                    checkPs.setString(2, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(3, bean.getBunrui1Cd());
                    // 法人コード
                    checkPs.setString(4, COMP_CD);
                    // 有効日
                    checkPs.setString(5, bean.getKeijoDt());
                    // 店舗コード
                    checkPs.setString(6, bean.getTenpoCd());
                    // DPTコード
                    checkPs.setString(7, bean.getBunrui1Cd());

                    // SQLを実行する
                    rs = checkPs.executeQuery();
                }
            }

            // 店舗コード
            strTenpoCode = bean.getTenpoCd();
            // 計上日
            strKeijoDt = bean.getKeijoDt();
        }

        // 終了ログを出力する。
        invoker.infoLog(strUserId + "　：　" + intWkDptSeisanUriShiireUpdateRui + "件のIFDPT精算売上（仕入用）ワークを更新しました。");
        invoker.infoLog(strUserId + "　：　IFDPT精算売上（仕入用）ワーク更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intWkDptSeisanUriShiireInsert + "件のIFDPT精算売上（仕入用）ワークを追加しました。");
        invoker.infoLog(strUserId + "　：　IFDPT精算売上（仕入用）ワーク追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intDtTenDptUriInsert + "件の店別DPT売上データを追加しました。");
        invoker.infoLog(strUserId + "　：　店別DPT売上データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intDtTenDptUriUpdate + "件の店別DPT売上データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別DPT売上データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intDtTenSeisanStateUpdate + "件の店別精算状況データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　オフラインDPT売上集計処理を終了します。");
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
     *  OutputBeanクラス
     */
    private class OfflineDPTUriageSyukeiDaoOutputBean {

        // 計上日
        private String keijoDt = null;

        // 店舗コード   
        private String tenpoCd = null;

        // DPTコード   
        private String bunrui1Cd = null;

        // 売上金額（税込）  
        private String uriageKomiVl = null;

        // 売上数量      
        private String uriageQt = null;

        // DPT客数
        private String bunrui1KyakuQt = null;

        // 店舗コード
        private String dtduTenpoCd = null;

        /**
         * @return bunrui1Cd
         */
        public String getBunrui1Cd() {
            return bunrui1Cd;
        }

        /**
         * @param bunrui1Cd 設定する bunrui1Cd
         */
        public void setBunrui1Cd(String bunrui1Cd) {
            this.bunrui1Cd = bunrui1Cd;
        }

        /**
         * @return bunrui1KyakuQt
         */
        public String getBunrui1KyakuQt() {
            return bunrui1KyakuQt;
        }

        /**
         * @param bunrui1KyakuQt 設定する bunrui1KyakuQt
         */
        public void setBunrui1KyakuQt(String bunrui1KyakuQt) {
            this.bunrui1KyakuQt = bunrui1KyakuQt;
        }

        /**
         * @return keijoDt
         */
        public String getKeijoDt() {
            return keijoDt;
        }

        /**
         * @param keijoDt 設定する keijoDt
         */
        public void setKeijoDt(String keijoDt) {
            this.keijoDt = keijoDt;
        }

        /**
         * @return tenpoCd
         */
        public String getTenpoCd() {
            return tenpoCd;
        }

        /**
         * @param tenpoCd 設定する tenpoCd
         */
        public void setTenpoCd(String tenpoCd) {
            this.tenpoCd = tenpoCd;
        }

        /**
         * @return uriageKomiVl
         */
        public String getUriageKomiVl() {
            return uriageKomiVl;
        }

        /**
         * @param uriageKomiVl 設定する uriageKomiVl
         */
        public void setUriageKomiVl(String uriageKomiVl) {
            this.uriageKomiVl = uriageKomiVl;
        }

        /**
         * @return uriageQt
         */
        public String getUriageQt() {
            return uriageQt;
        }

        /**
         * @param uriageQt 設定する uriageQt
         */
        public void setUriageQt(String uriageQt) {
            this.uriageQt = uriageQt;
        }

        /**
         * @return dtduTenpoCd
         */
        public String getDtduTenpoCd() {
            return dtduTenpoCd;
        }

        /**
         * @param dtduTenpoCd 設定する dtduTenpoCd
         */
        public void setDtduTenpoCd(String dtduTenpoCd) {
            this.dtduTenpoCd = dtduTenpoCd;
        }
    }

    public static void main(String[] arg) {
        try {
            DaoIf dao = new OfflineDPTUriageSyukeiDao();
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
