package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
 *  <P>タイトル: OfflineKaikeiSeisanUriageDataCreateDao クラス</p>
 *  <P>説明: オフライン会計精算売上データ作成処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 (2009.05.27) 初版作成
 */
public class OfflineKaikeiSeisanUriageDataCreateDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチID
    private static final String BATCH_ID = "URIB101020";
    // バッチ名称
    private static final String BATCH_NAME = "オフライン会計精算売上データ作成処理";
    // 売上精算票項目取得
    private static final String URIAGE_SEISAN_KOMOKU = FiResorceUtility.getPropertie(UriResorceKeyConstant.URIAGE_SEISAN_KOMOKU);
    // DB日付取得
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // オフライン会計精算売上ワーク検索SQL
    private static final String SEL_WK_OffLINE = "selectWKOffLine";
    // 精算票項目コード取得SQL
    private static final String SEL_SEISAN_KOMOKU_CD = "selectSeisanKomokuCd";
    // 売上金額整合性チェックSQL
    private static final String URIAGE_KIN_CHECK = "uriageKinCheck";
    // 店別精算状況データに処理結果を反映SQL
    private static final String UPDATE_TEN_SEISAN_STATE = "updateTenSeisanState";
    // 店舗コード
    private static final String TENPO_CD = "TENPO_CD";
    // 店別DPT売上データ.POS金額
    private static final String DPT_POS_VL = "DPT_POS_VL";
    // 店別精算データ.POS金額
    private static final String POS_VL = "POS_VL";
    // 計上日
    private static final String KEIJO_DT = "KEIJO_DT";

    /**
     * オフライン会計精算売上データ作成処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;
        // 店別精算データ更新件数
        int intUpdSeisan = 0;
        // 店別精算売掛データ更新件数
        int intUpdUrikake = 0;
        // 店別精算データ追加件数
        int intInsSeisan = 0;
        // 店別精算売掛データ追加件数
        int intInsUrikake = 0;
        // 店別精算データ更新件数
        int intUpdSeisanState = 0;

        // 計上日
        String strKeijoDT = "";
        // 精算票項目コード
        String strSeisanhyoKomokuCd = "";
        // POSデータカラム名
        String strPosName = "";

        // 売上金額整合性チェックフラグ
        boolean blnFlag = false;

        // プロジェクト初期化
        PreparedStatementEx preparedStatementEx = null;
        PreparedStatementEx psSelKomokuCD = null;

        PreparedStatementEx psUriageKinCheck = null;
        PreparedStatementEx psUpdTenSeisanState = null;
        ResultSet resultSet = null;
        ResultSet rsUriageKinCheck = null;
        ResultSet rs = null;

        // ログ出力
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上データ作成処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算データ更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算データ追加処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ追加処理を開始します。");
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新処理を開始します。");

        // オフライン会計精算売上ワークから対象店舗データ抽出する
        preparedStatementEx = invoker.getDataBase().prepareStatement(SEL_WK_OffLINE);
        // 法人コード
        preparedStatementEx.setString(1, COMP_CD);
        preparedStatementEx.setString(2, COMP_CD);

        // SQL文より、データを検索する
        resultSet = preparedStatementEx.executeQuery();

        // 精算票項目コードを取得する              
        psSelKomokuCD = invoker.getDataBase().prepareStatement(SEL_SEISAN_KOMOKU_CD);
        // 売上金額整合性チェックSQL文を取得する
        psUriageKinCheck = invoker.getDataBase().prepareStatement(URIAGE_KIN_CHECK);
        // 店別精算状況データに処理結果の反映SQL文を取得する
        psUpdTenSeisanState = invoker.getDataBase().prepareStatement(UPDATE_TEN_SEISAN_STATE);

        while (resultSet.next()) {

            // 計上日を設定する
            strKeijoDT = resultSet.getString(KEIJO_DT);

            // 精算票項目マスタ.法人コード
            psSelKomokuCD.setString(1, COMP_CD);
            // 精算票項目マスタ.適用開始日
            psSelKomokuCD.setString(2, strKeijoDT);
            // 精算票項目マスタ.法人コード
            psSelKomokuCD.setString(3, COMP_CD);
            // 売掛項目マスタ.法人コード
            psSelKomokuCD.setString(4, COMP_CD);
            // 売掛項目マスタ.適用開始日
            psSelKomokuCD.setString(5, strKeijoDT);
            // 売掛項目マスタ.法人コード
            psSelKomokuCD.setString(6, COMP_CD);
            // SQLを実行する
            rs = psSelKomokuCD.executeQuery();

            // データ取得後、格納用リスト
            List list = new ArrayList();

            OfflineKaikeiSeisanUriageDataCreateDaoOutputBean ouputBean = null;

            // SELECT列リストで指定した別名から、"_" を外してOutputRowBeanの同名プロパティにセットする為のルール
            String[][] rules = { UriageCommonConstants.RULES };

            while (rs.next()) {
                ouputBean = new OfflineKaikeiSeisanUriageDataCreateDaoOutputBean();

                // OutputBeanの同名プロパティにデータを全てコピーする
                DaoUtils.copy(ouputBean, DaoUtils.getMapFromRs(rs), rules);
                list.add(ouputBean);
            }

            int intSize = list.size();

            if (intSize > UriageCommonConstants.ZERO_INT) {

                for (int i = UriageCommonConstants.ZERO_INT; i < intSize; i++) {

                    // ouputBean初期化
                    ouputBean = new OfflineKaikeiSeisanUriageDataCreateDaoOutputBean();

                    // リストのi件目を取得
                    ouputBean = (OfflineKaikeiSeisanUriageDataCreateDaoOutputBean) list.get(i);

                    // 店別精算データを作成する
                    // 当レコードが１件目の場合
                    if (i == UriageCommonConstants.ZERO_INT) {

                        // 該当レコードの（精算）：精算票項目コード
                        strSeisanhyoKomokuCd = ouputBean.getSeisanhyoKomokuCd();

                        // 該当レコードの（売掛）：POSデータカラム名がNULLの場合
                        if (UriageCommonConstants.EMPTY.equals(ouputBean.getUrikakePosDataColumnNa())) {
                            // 該当レコードの（精算）：POSデータカラム名
                            strPosName = ouputBean.getPosDataColumnNa().trim();
                        } else {
                            // 該当レコードの（売掛）：POSデータカラム名
                            strPosName = ouputBean.getUrikakePosDataColumnNa().trim();
                        }
                    }

                    // 該当レコードが１件目ではない、
                    // 且つ該当レコードの（精算）精算票項目コードと精算票項目コード同じ場合
                    if ((i != UriageCommonConstants.ZERO_INT) && (strSeisanhyoKomokuCd.equals(ouputBean.getSeisanhyoKomokuCd()))) {
                        // POSデータカラム名 + "+" + 該当レコードの（売掛）：POSデータカラム名
                        if ((!UriageCommonConstants.EMPTY.equals(strPosName)) && (!UriageCommonConstants.EMPTY.equals(ouputBean.getUrikakePosDataColumnNa().trim()))) {
                            strPosName = strPosName + "+" + ouputBean.getUrikakePosDataColumnNa().trim();
                        } else if (UriageCommonConstants.EMPTY.equals(strPosName)) {
                            strPosName = ouputBean.getUrikakePosDataColumnNa().trim();
                        }
                    }

                    // 該当レコードの（精算）：精算票項目コードと精算票項目コード同じではない場合
                    if (!strSeisanhyoKomokuCd.equals(ouputBean.getSeisanhyoKomokuCd())) {

                        // 店別精算データ更新件数をセットする
                        intUpdSeisan = intUpdSeisan + updateTenSeisan(invoker, strPosName, strKeijoDT, strSeisanhyoKomokuCd);
                        // 店別精算データ登録件数をセットする
                        intInsSeisan = intInsSeisan + insertTenSeisan(invoker, strPosName, strKeijoDT, strSeisanhyoKomokuCd);

                        // 該当レコードの（売掛）：POSデータカラム名がNULLの場合
                        if (UriageCommonConstants.EMPTY.equals(ouputBean.getUrikakePosDataColumnNa())) {
                            // 該当レコードの（精算）：POSデータカラム名
                            strPosName = ouputBean.getPosDataColumnNa().trim();
                        } else {
                            // 該当レコードの（売掛）：POSデータカラム名
                            strPosName = ouputBean.getUrikakePosDataColumnNa().trim();
                        }

                        // 該当レコードの（精算）：精算票項目コード
                        strSeisanhyoKomokuCd = ouputBean.getSeisanhyoKomokuCd();

                    }

                    // 該当レコードは最後レコードの場合
                    if (i == intSize - 1) {

                        // 店別精算データ更新件数をセットする
                        intUpdSeisan = intUpdSeisan + updateTenSeisan(invoker, strPosName, strKeijoDT, strSeisanhyoKomokuCd);
                        // 店別精算データ登録件数をセットする
                        intInsSeisan = intInsSeisan + insertTenSeisan(invoker, strPosName, strKeijoDT, strSeisanhyoKomokuCd);
                    }

                    // 店別精算売掛データ作成と更新
                    // 該当レコードの（売掛）：精算票項目コードがNULL以外の場合
                    if (!UriageCommonConstants.EMPTY.equals(ouputBean.getUrikakeSeisanhyoKomokuCD())) {

                        // 店別精算売掛データ更新
                        PreparedStatementEx psUpdTenSeisanUrikake = invoker.getDataBase().prepareStatement(getUpdTenSeisanUrikakeSQL(ouputBean.getUrikakePosDataColumnNa().trim()));
                        // 法人コード
                        psUpdTenSeisanUrikake.setString(1, COMP_CD);
                        psUpdTenSeisanUrikake.setString(2, COMP_CD);
                        // 計上日
                        psUpdTenSeisanUrikake.setString(3, strKeijoDT);
                        /** 090904 yasuda update start(精算金額とPOS金額を同額に) */
                        // 法人コード
                        psUpdTenSeisanUrikake.setString(4, COMP_CD);
                        psUpdTenSeisanUrikake.setString(5, COMP_CD);
                        // 計上日
                        psUpdTenSeisanUrikake.setString(6, strKeijoDT);
                        /** 090904 yasuda update end */
                        // 更新者ID
                        psUpdTenSeisanUrikake.setString(7, BATCH_ID);
                        // 更新年月日
                        psUpdTenSeisanUrikake.setString(8, DBSERVER_DT);
                        // 法人コード
                        psUpdTenSeisanUrikake.setString(9, COMP_CD);
                        // 計上日
                        psUpdTenSeisanUrikake.setString(10, strKeijoDT);
                        // 法人コード
                        psUpdTenSeisanUrikake.setString(11, COMP_CD);
                        psUpdTenSeisanUrikake.setString(12, COMP_CD);
                        // 計上日
                        psUpdTenSeisanUrikake.setString(13, strKeijoDT);
                        // 精算票項目コード
                        psUpdTenSeisanUrikake.setString(14, strSeisanhyoKomokuCd);
                        // 売掛項目コード
                        psUpdTenSeisanUrikake.setString(15, ouputBean.getUrikakeKomokuCd());

                        // 店別精算売掛データ更新件数セット
                        intUpdUrikake = intUpdUrikake + psUpdTenSeisanUrikake.executeUpdate();

                        psUpdTenSeisanUrikake.close();

                        // 店別精算売掛データ作成
                        // 店別精算売掛データ登録SQL文を取得する
                        PreparedStatementEx psInsTenSeisanUrikake = invoker.getDataBase().prepareStatement(getInsTenSeisanUrikakeSQL(ouputBean.getUrikakePosDataColumnNa().trim()));
                        // 法人コード
                        psInsTenSeisanUrikake.setString(1, COMP_CD);
                        // 計上日
                        psInsTenSeisanUrikake.setString(2, strKeijoDT);
                        // 精算票項目コード
                        psInsTenSeisanUrikake.setString(3, strSeisanhyoKomokuCd);
                        // 売掛項目コード
                        psInsTenSeisanUrikake.setString(4, ouputBean.getUrikakeKomokuCd());
                        // 作成者ID   
                        psInsTenSeisanUrikake.setString(5, BATCH_ID);
                        // 作成年月日
                        psInsTenSeisanUrikake.setString(6, DBSERVER_DT);
                        // 更新者ID
                        psInsTenSeisanUrikake.setString(7, BATCH_ID);
                        // 更新年月日
                        psInsTenSeisanUrikake.setString(8, DBSERVER_DT);
                        // 法人コード
                        psInsTenSeisanUrikake.setString(9, COMP_CD);
                        psInsTenSeisanUrikake.setString(10, COMP_CD);
                        // 精算票項目コード
                        psInsTenSeisanUrikake.setString(11, strSeisanhyoKomokuCd);
                        // 売掛項目コード
                        psInsTenSeisanUrikake.setString(12, ouputBean.getUrikakeKomokuCd());
                        // 法人コード
                        psInsTenSeisanUrikake.setString(13, COMP_CD);
                        // 計上日
                        psInsTenSeisanUrikake.setString(14, strKeijoDT);

                        // 店別精算売掛データ作件数セット
                        intInsUrikake = intInsUrikake + psInsTenSeisanUrikake.executeUpdate();

                        psInsTenSeisanUrikake.close();
                    }
                }
            }

            // 法人コード
            psUriageKinCheck.setString(1, COMP_CD);
            // 計上日
            psUriageKinCheck.setString(2, strKeijoDT);
            // 法人コード
            psUriageKinCheck.setString(3, COMP_CD);
            // 計上日
            psUriageKinCheck.setString(4, strKeijoDT);
            // 精算票項目
            psUriageKinCheck.setString(5, URIAGE_SEISAN_KOMOKU);

            // SQLを実行する
            rsUriageKinCheck = psUriageKinCheck.executeQuery();

            while (rsUriageKinCheck.next()) {

                // 店舗コードを取得する
                String strTenpoCode = rsUriageKinCheck.getString(TENPO_CD);

                // 店別DPT売上データ.POS金額≠店別精算データ.POS金額の場合
                if (!rsUriageKinCheck.getString(POS_VL).equals(rsUriageKinCheck.getString(DPT_POS_VL))) {
                    // エラーログを出力する
                    invoker.warnLog(strUserId + "　：　DPT売上と精算売上が一致しません。計上日：" + rsUriageKinCheck.getString(KEIJO_DT) + " " + "店舗コード：" + " " + strTenpoCode + " " + "DPT売上金額："
                            + rsUriageKinCheck.getString(DPT_POS_VL) + " " + "精算売上金額：" + rsUriageKinCheck.getString(POS_VL));
                    // DPT精算状況ﾌﾗｸﾞを設定する
                    blnFlag = false;
                } else {
                    blnFlag = true;
                }

                // DPT精算状況ﾌﾗｸﾞは売上金額整合性チェック正常の場合、1(取込済)をセット
                if (blnFlag) {
                    psUpdTenSeisanState.setString(1, UriageCommonConstants.TORIKOMI_TSUMI_FLAG);
                }
                // 売上金額整合性チェックエラーの場合、9(エラー)をセット
                else {
                    psUpdTenSeisanState.setString(1, UriageCommonConstants.ERROR_FLAG);
                }
                // 更新者ID     
                psUpdTenSeisanState.setString(2, BATCH_ID);
                // 更新年月日    
                psUpdTenSeisanState.setString(3, DBSERVER_DT);
                // 法人コード
                psUpdTenSeisanState.setString(4, COMP_CD);
                // バッチ日付
                psUpdTenSeisanState.setString(5, strKeijoDT);
                // 店舗コード
                psUpdTenSeisanState.setString(6, strTenpoCode);

                // SQLを実行して成功場合、店別精算状況データ更新件数＋１
                intUpdSeisanState = intUpdSeisanState + psUpdTenSeisanState.executeUpdate();
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + intUpdSeisan + "件の店別精算データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intInsSeisan + "件の店別精算データを追加しました。");
        invoker.infoLog(strUserId + "　：　店別精算データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intUpdUrikake + "件の店別精算売掛データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intInsUrikake + "件の店別精算売掛データを追加しました。");
        invoker.infoLog(strUserId + "　：　店別精算売掛データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + intUpdSeisanState + "件の店別精算状況データを更新しました。");
        invoker.infoLog(strUserId + "　：　店別精算状況データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　オフライン会計精算売上データ作成処理を終了します。");
    }

    /**
     * 店別精算データの更新
     * @param invoker
     * @param strPosName
     * @param strKeijoDT
     * @param strSeisanhyoKomokuCd
     * @return 更新件数
     * @throws Exception
     */
    private int updateTenSeisan(DaoInvokerIf invoker, String strPosName, String strKeijoDT, String strSeisanhyoKomokuCd) throws Exception {

        StringBuffer sb = new StringBuffer();
        int intCount = 0;

        sb.append(" UPDATE ");
        sb.append("     DT_TEN_SEISAN ");
        sb.append(" SET ");
        sb.append("     POS_VL = POS_VL +  ");
        sb.append("             (SELECT ");
        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("    SUM(" + strPosName + ") SUM_POS_VL ");
        } else {
            sb.append("    SUM(0) SUM_POS_VL ");
        }
        sb.append("             FROM ");
        sb.append("                 WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("             INNER JOIN ");
        sb.append("                 (SELECT ");
        sb.append("                     KEIJO_DT, ");
        sb.append("                     TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     DT_TEN_SEISAN_STATE ");
        sb.append("                 WHERE ");
        sb.append("                     COMP_CD = ? ");
        sb.append("                 AND (SEISAN_STATE_FG = '0' ");
        sb.append("                     OR SEISAN_STATE_FG = '1' ");
        sb.append("                     OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("             ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("             AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("             WHERE ");
        sb.append("                 WOKS.COMP_CD = ? ");
        sb.append("             AND WOKS.KEIJO_DT = ? ");
        sb.append("             AND WOKS.TENPO_CD = DT_TEN_SEISAN.TENPO_CD ");
        sb.append("             GROUP BY ");
        sb.append("                 WOKS.TENPO_CD), ");
/** 090904 yasuda update start(精算金額にPOS金額と同額をセット) */
        //sb.append("     SEISAN_VL = 0, ");
        sb.append("     SEISAN_VL = POS_VL +  ");
        sb.append("             (SELECT ");
        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("    SUM(" + strPosName + ") SUM_POS_VL ");
        } else {
            sb.append("    SUM(0) SUM_POS_VL ");
        }
        sb.append("             FROM ");
        sb.append("                 WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("             INNER JOIN ");
        sb.append("                 (SELECT ");
        sb.append("                     KEIJO_DT, ");
        sb.append("                     TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     DT_TEN_SEISAN_STATE ");
        sb.append("                 WHERE ");
        sb.append("                     COMP_CD = ? ");
        sb.append("                 AND (SEISAN_STATE_FG = '0' ");
        sb.append("                     OR SEISAN_STATE_FG = '1' ");
        sb.append("                     OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("             ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("             AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("             WHERE ");
        sb.append("                 WOKS.COMP_CD = ? ");
        sb.append("             AND WOKS.KEIJO_DT = ? ");
        sb.append("             AND WOKS.TENPO_CD = DT_TEN_SEISAN.TENPO_CD ");
        sb.append("             GROUP BY ");
        sb.append("                 WOKS.TENPO_CD), ");
/** 090904 yasuda update end */
        sb.append("     POS_QT = 0, ");
        sb.append("     SEISAN_QT = 0, ");
        sb.append("     UPDATE_USER_ID = ?, ");
        sb.append("     UPDATE_TS = ? ");
        sb.append(" WHERE ");
        sb.append("     COMP_CD = ? ");
        sb.append(" AND KEIJO_DT = ? ");
        sb.append(" AND TENPO_CD IN (SELECT ");
        sb.append("                     WOKS.TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("                 INNER JOIN ");
        sb.append("                     (SELECT ");
        sb.append("                         KEIJO_DT, ");
        sb.append("                         TENPO_CD ");
        sb.append("                     FROM ");
        sb.append("                         DT_TEN_SEISAN_STATE ");
        sb.append("                     WHERE ");
        sb.append("                         COMP_CD = ? ");
        sb.append("                     AND (SEISAN_STATE_FG = '0' ");
        sb.append("                         OR SEISAN_STATE_FG = '1' ");
        sb.append("                         OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("                 ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("                 AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("                 WHERE ");
        sb.append("                     WOKS.COMP_CD = ? ");
        sb.append("                 AND WOKS.KEIJO_DT = ? ");
        sb.append("                 GROUP BY ");
        sb.append("                     WOKS.TENPO_CD) ");
        sb.append(" AND SEISANHYO_KOMOKU_CD = ? ");

        PreparedStatementEx ps = invoker.getDataBase().prepareStatement(sb.toString());

        // 法人コード
        ps.setString(1, COMP_CD);
        ps.setString(2, COMP_CD);
        // 計上日
        ps.setString(3, strKeijoDT);
/** 090904 yasuda update start(精算金額とPOS金額を同額に) */
        // 法人コード
        ps.setString(4, COMP_CD);
        ps.setString(5, COMP_CD);
        // 計上日
        ps.setString(6, strKeijoDT);
/** 090904 yasuda update end */
        // 更新者ID
        ps.setString(7, BATCH_ID);
        // 更新年月日
        ps.setString(8, DBSERVER_DT);
        // 法人コード
        ps.setString(9, COMP_CD);
        // 計上日
        ps.setString(10, strKeijoDT);
        // 法人コード
        ps.setString(11, COMP_CD);
        ps.setString(12, COMP_CD);
        // 計上日
        ps.setString(13, strKeijoDT);
        // 精算票項目コード
        ps.setString(14, strSeisanhyoKomokuCd);

        intCount = ps.executeUpdate();

        ps.close();

        return intCount;
    }

    /**
     * 店別精算データの登録
     * @param invoker
     * @param strPosName
     * @param strKeijoDT
     * @param strSeisanhyoKomokuCd
     * @return 登録件数
     * @throws Exception
     */
    private int insertTenSeisan(DaoInvokerIf invoker, String strPosName, String strKeijoDT, String strSeisanhyoKomokuCd) throws Exception {

        int intCount = 0;
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO ");
        sb.append("     DT_TEN_SEISAN ");
        sb.append("     ( ");
        sb.append("     COMP_CD, ");
        sb.append("     KEIJO_DT, ");
        sb.append("     TENPO_CD, ");
        sb.append("     SEISANHYO_KOMOKU_CD, ");
        sb.append("     POS_VL, ");
        sb.append("     SEISAN_VL, ");
        sb.append("     POS_QT, ");
        sb.append("     SEISAN_QT, ");
        sb.append("     INSERT_USER_ID, ");
        sb.append("     INSERT_TS, ");
        sb.append("     UPDATE_USER_ID, ");
        sb.append("     UPDATE_TS) ");
        sb.append(" (SELECT ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     WOKS.TENPO_CD, ");
        sb.append("     ?, ");

        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("     SUM(" + strPosName + "), ");
            sb.append("     SUM(" + strPosName + "), ");
        } else {
            sb.append("     SUM(0), ");
            sb.append("     SUM(0), ");
        }
/** 090904 yasuda update start(精算金額にPOS金額と同額をセット) */
        //sb.append("     0, ");
        sb.append("     0, ");
        sb.append("     0, ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     ? ");
        sb.append(" FROM ");
        sb.append("     WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append(" INNER JOIN ");
        sb.append("     (SELECT ");
        sb.append("         KEIJO_DT, ");
        sb.append("         TENPO_CD ");
        sb.append("     FROM ");
        sb.append("         DT_TEN_SEISAN_STATE ");
        sb.append("     WHERE ");
        sb.append("         COMP_CD = ? ");
        sb.append("     AND (SEISAN_STATE_FG = '0' ");
        sb.append("         OR SEISAN_STATE_FG = '1' ");
        sb.append("         OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append(" ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append(" AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append(" LEFT JOIN ");
        sb.append("     DT_TEN_SEISAN DTS ");
        sb.append(" ON DTS.COMP_CD = ? ");
        sb.append(" AND DTS.KEIJO_DT= WOKS.KEIJO_DT ");
        sb.append(" AND DTS.TENPO_CD = WOKS.TENPO_CD ");
        sb.append(" AND DTS.SEISANHYO_KOMOKU_CD = ? ");
        sb.append(" WHERE ");
        sb.append("     WOKS.COMP_CD = ? ");
        sb.append(" AND WOKS.KEIJO_DT = ? ");
        sb.append(" AND DTS.COMP_CD IS NULL ");
        sb.append(" GROUP BY ");
        sb.append("     WOKS.TENPO_CD) ");

        PreparedStatementEx ps = invoker.getDataBase().prepareStatement(sb.toString());

        // 法人コード
        ps.setString(1, COMP_CD);
        // 計上日
        ps.setString(2, strKeijoDT);
        // 精算票項目コード
        ps.setString(3, strSeisanhyoKomokuCd);
        // 作成者ID   
        ps.setString(4, BATCH_ID);
        // 作成年月日
        ps.setString(5, DBSERVER_DT);
        // 更新者ID
        ps.setString(6, BATCH_ID);
        // 更新年月日
        ps.setString(7, DBSERVER_DT);
        // 法人コード
        ps.setString(8, COMP_CD);
        ps.setString(9, COMP_CD);
        // 精算票項目コード
        ps.setString(10, strSeisanhyoKomokuCd);
        // 法人コード
        ps.setString(11, COMP_CD);
        // 計上日
        ps.setString(12, strKeijoDT);

        intCount = ps.executeUpdate();

        ps.close();

        return intCount;
    }

    /**
     * 店別精算売掛データの更新SQL文を取得
     * @param strPosName
     * @return SQL文
     * @throws Exception
     */
    private String getUpdTenSeisanUrikakeSQL(String strPosName) throws Exception {

        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE ");
        sb.append("     DT_TEN_SEISAN_URIKAKE ");
        sb.append(" SET ");
        sb.append("     POS_VL = POS_VL +  ");
        sb.append("             (SELECT ");

        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("    SUM(" + strPosName + ") SUM_POS_VL ");
        } else {
            sb.append("    SUM(0) SUM_POS_VL ");
        }

        sb.append("             FROM ");
        sb.append("                 WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("             INNER JOIN ");
        sb.append("                 (SELECT ");
        sb.append("                     KEIJO_DT, ");
        sb.append("                     TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     DT_TEN_SEISAN_STATE ");
        sb.append("                 WHERE ");
        sb.append("                    COMP_CD = ? ");
        sb.append("                 AND (SEISAN_STATE_FG = '0' ");
        sb.append("                     OR SEISAN_STATE_FG = '1' ");
        sb.append("                     OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("             ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("             AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("             WHERE ");
        sb.append("                 WOKS.COMP_CD = ? ");
        sb.append("             AND WOKS.KEIJO_DT = ? ");
        sb.append("             AND WOKS.TENPO_CD = DT_TEN_SEISAN_URIKAKE.TENPO_CD ");
        sb.append("             GROUP BY ");
        sb.append("                 WOKS.TENPO_CD), ");
/** 090904 yasuda update start(精算金額にPOS金額と同額をセット) */
        //sb.append("     SEISAN_VL = 0, ");
        sb.append("     SEISAN_VL = POS_VL +  ");
        sb.append("             (SELECT ");

        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("    SUM(" + strPosName + ") SUM_POS_VL ");
        } else {
            sb.append("    SUM(0) SUM_POS_VL ");
        }

        sb.append("             FROM ");
        sb.append("                 WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("             INNER JOIN ");
        sb.append("                 (SELECT ");
        sb.append("                     KEIJO_DT, ");
        sb.append("                     TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     DT_TEN_SEISAN_STATE ");
        sb.append("                 WHERE ");
        sb.append("                    COMP_CD = ? ");
        sb.append("                 AND (SEISAN_STATE_FG = '0' ");
        sb.append("                     OR SEISAN_STATE_FG = '1' ");
        sb.append("                     OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("             ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("             AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("             WHERE ");
        sb.append("                 WOKS.COMP_CD = ? ");
        sb.append("             AND WOKS.KEIJO_DT = ? ");
        sb.append("             AND WOKS.TENPO_CD = DT_TEN_SEISAN_URIKAKE.TENPO_CD ");
        sb.append("             GROUP BY ");
        sb.append("                 WOKS.TENPO_CD), ");
/** 090904 yasuda update end */
        sb.append("     UPDATE_USER_ID = ?, ");
        sb.append("     UPDATE_TS = ? ");
        sb.append(" WHERE ");
        sb.append("     COMP_CD = ? ");
        sb.append(" AND KEIJO_DT = ? ");
        sb.append(" AND TENPO_CD IN (SELECT ");
        sb.append("                     WOKS.TENPO_CD ");
        sb.append("                 FROM ");
        sb.append("                     WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("                 INNER JOIN ");
        sb.append("                     (SELECT ");
        sb.append("                         KEIJO_DT, ");
        sb.append("                         TENPO_CD ");
        sb.append("                     FROM ");
        sb.append("                         DT_TEN_SEISAN_STATE ");
        sb.append("                     WHERE ");
        sb.append("                         COMP_CD = ? ");
        sb.append("                     AND (SEISAN_STATE_FG = '0' ");
        sb.append("                         OR SEISAN_STATE_FG = '1' ");
        sb.append("                         OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append("                 ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append("                 AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append("                 WHERE ");
        sb.append("                     WOKS.COMP_CD = ? ");
        sb.append("                 AND WOKS.KEIJO_DT = ? ");
        sb.append("                 GROUP BY ");
        sb.append("                     WOKS.TENPO_CD) ");
        sb.append(" AND SEISANHYO_KOMOKU_CD = ? ");
        sb.append(" AND URIKAKE_KOMOKU_CD = ? ");

        return sb.toString();
    }

    /**
     * 店別精算売掛データの登録SQL文を取得
     * @param strPosName
     * @return SQL文
     * @throws Exception
     */
    private String getInsTenSeisanUrikakeSQL(String strPosName) throws Exception {

        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO ");
        sb.append("     DT_TEN_SEISAN_URIKAKE ");
        sb.append("     ( ");
        sb.append("     COMP_CD, ");
        sb.append("     KEIJO_DT, ");
        sb.append("     TENPO_CD, ");
        sb.append("     SEISANHYO_KOMOKU_CD, ");
        sb.append("     URIKAKE_KOMOKU_CD, ");
        sb.append("     POS_VL, ");
        sb.append("     SEISAN_VL, ");
        sb.append("     INSERT_USER_ID, ");
        sb.append("     INSERT_TS, ");
        sb.append("     UPDATE_USER_ID, ");
        sb.append("     UPDATE_TS) ");
        sb.append(" (SELECT ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     WOKS.TENPO_CD, ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        if (!UriageCommonConstants.EMPTY.equals(strPosName)) {
            sb.append("     SUM(" + strPosName + "), ");
            sb.append("     SUM(" + strPosName + "), ");
        } else {
            sb.append("     SUM(0), ");
            sb.append("     SUM(0), ");
        }
/** 090904 yasuda update start(精算金額にPOS金額と同額をセット) */
        //sb.append("     0, ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     ?, ");
        sb.append("     ? ");
        sb.append("     FROM ");
        sb.append("     WK_OFF_KAIKEI_SEISAN_SAI WOKS ");
        sb.append("     INNER JOIN ");
        sb.append("     (SELECT ");
        sb.append("         KEIJO_DT, ");
        sb.append("         TENPO_CD ");
        sb.append("     FROM ");
        sb.append("         DT_TEN_SEISAN_STATE ");
        sb.append("     WHERE ");
        sb.append("         COMP_CD = ? ");
        sb.append("     AND (SEISAN_STATE_FG = '0' ");
        sb.append("         OR SEISAN_STATE_FG = '1' ");
        sb.append("         OR SEISAN_STATE_FG = '9')) SQ1 ");
        sb.append(" ON SQ1.KEIJO_DT = WOKS.KEIJO_DT ");
        sb.append(" AND SQ1.TENPO_CD = WOKS.TENPO_CD ");
        sb.append(" LEFT JOIN ");
        sb.append("     DT_TEN_SEISAN_URIKAKE DTSU ");
        sb.append(" ON DTSU.COMP_CD = ? ");
        sb.append(" AND DTSU.KEIJO_DT= WOKS.KEIJO_DT ");
        sb.append(" AND DTSU.TENPO_CD = WOKS.TENPO_CD ");
        sb.append(" AND DTSU.SEISANHYO_KOMOKU_CD = ? ");
        sb.append(" AND DTSU.URIKAKE_KOMOKU_CD = ? ");
        sb.append(" WHERE ");
        sb.append("     WOKS.COMP_CD = ? ");
        sb.append(" AND WOKS.KEIJO_DT = ? ");
        sb.append(" AND DTSU.COMP_CD IS NULL ");
        sb.append(" GROUP BY ");
        sb.append("     WOKS.TENPO_CD) ");

        return sb.toString();
    }

    /**
     *  OutputBeanクラス
     */
    private class OfflineKaikeiSeisanUriageDataCreateDaoOutputBean {

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
            DaoIf dao = new OfflineKaikeiSeisanUriageDataCreateDao();
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
