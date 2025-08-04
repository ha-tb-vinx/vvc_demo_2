package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: DWHIFDataCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 1.00 (2014.12.04) chou グローバル化対応 店舗コード対応
 */
public class DWHIFDataCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "ＤＷＨIF用データ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB131090";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // エラーコードとエラーDPT名
    private static final HashMap ERROR_DPT = (HashMap) FiResorceUtility.getPropertieMap(UriResorceKeyConstant.ERR_HYOJI_DPT);
    // エラーコード
    private static final String ERROR_DPT_CODE = ERROR_DPT.keySet().toString().replace("[", "").replace("]", "");

    /** 金額項目桁あふれ桁数 */
    private static final String VL_ITEM_OVERFLOW_LENGTH = "8";
    /** 数量項目桁あふれ桁数 */
    private static final String QT_ITEM_OVERFLOW_LENGTH = "7";

    /**
     *
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        int intInsertCount = 0;

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementDel = null;
        PreparedStatementEx preparedStatementIns = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：ＤＷＨIF用データ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用データ:ＤＷＨIF用データ削除処理を開始します。");

        // ＤＷＨIF用累積データ削除処理
        int IDX = 1;
        preparedStatementDel = invoker.getDataBase().prepareStatement(delDWHIFSql());
        // 実行
        preparedStatementDel.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用データ:ＤＷＨIF用データ削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用データ:ＤＷＨIF用データ挿入処理を開始します。");

        // ＤＷＨIF用データ挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        IDX = 1;
        preparedStatementIns = invoker.getDataBase().prepareStatement(insDWHIFSql());
        preparedStatementIns.setString(IDX++, " ");
        preparedStatementIns.setString(IDX++, UriageCommonConstants.KIGYO_CD_0001);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.KIGYO_CD_0001);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.HENPIN_QT_0);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.HENPIN_VL_0);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_00);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DPT_CODE_9999);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DPT_CODE_9999);
        preparedStatementIns.setString(IDX++, ERROR_DPT_CODE);

        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsertCount + "件のＤＷＨIF用データを挿入しました。");
        invoker.infoLog(strUserID + "　：　ＤＷＨIF用データ:ＤＷＨIF用データ挿入処理を終了します。");
        invoker.infoLog(strUserID + "　：　ＤＷＨＳ：ＤＷＨIF用データ作成処理を終了します。");
    }

    private String delDWHIFSql() {
        StringBuffer sb = new StringBuffer("");

        // ＤＷＨIF用データ削除用DeleteSQL構築
        sb.append(" TRUNCATE TABLE ");
        sb.append("     DT_DWH_IF ");

        return sb.toString();
    }

    private String insDWHIFSql() {
        StringBuffer sb = new StringBuffer("");

        // ＤＷＨIF用データ作成用InsertSQL構築
        sb.append(" INSERT /*+ APPEND */ INTO ");
        sb.append("    DT_DWH_IF ");
        sb.append("    ( ");
        sb.append("          COMP_CD ");
        sb.append("        , URIAGE_DT ");
        sb.append("        , URIAGE_TM ");
        sb.append("        , KIGYO1_CD ");
        sb.append("        , KIGYO2_CD ");
        sb.append("        , TENPO_CD ");
        sb.append("        , BUNRUI1_CD ");
        sb.append("        , URIAGE_KYAKU_QT ");
        sb.append("        , URIAGE_QT ");
        sb.append("        , URIAGE_VL ");
        sb.append("        , POS_QT ");
        sb.append("        , POS_VL ");
        sb.append("        , HENPIN_QT ");
        sb.append("        , HENPIN_VL ");
        sb.append("        , ERR_KB ");
        sb.append("        , INSERT_USER_ID ");
        sb.append("        , INSERT_TS ");
        sb.append("        , UPDATE_USER_ID ");
        sb.append("        , UPDATE_TS ");
        sb.append("    ) ");
        sb.append("    ( ");
        sb.append("        SELECT ");
        sb.append("              SUB_DDIR.COMP_CD ");
        sb.append("            , SUB_DDIR.KEIJO_DT ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        //2014/12/04 chou グローバル化対応 店舗コード対応 MOD START
        //sb.append("            , LPAD(RTRIM(SUB_DDIR.TENPO_CD), 4, '0')   TENPO_CD ");
        sb.append("            , LPAD(RTRIM(SUB_DDIR.TENPO_CD), 6, '0')   TENPO_CD ");
        //2014/12/04 chou グローバル化対応 店舗コード対応 MOD END
        sb.append("            , LPAD(RTRIM(SUB_DDIR.BUNRUI1_CD), 6, '0') BUNRUI1_CD ");
        sb.append("            , SUB_DDIR.KYAKU_QT ");
        sb.append("            , SUB_DDIR.URIAGE_QT ");
        sb.append("            , SUB_DDIR.URIAGE_NUKI_VL ");
        sb.append("            , SUB_DDIR.POS_QT ");
        sb.append("            , SUB_DDIR.POS_VL ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , CASE ");
        sb.append("                  WHEN LENGTH(ABS(KYAKU_QT)) >= " + QT_ITEM_OVERFLOW_LENGTH + " ");
        sb.append("                      THEN ? ");
        sb.append("                  WHEN LENGTH(ABS(URIAGE_QT)) >= " + QT_ITEM_OVERFLOW_LENGTH + " ");
        sb.append("                      THEN ? ");
        sb.append("                  WHEN LENGTH(ABS(POS_QT))         >= " + QT_ITEM_OVERFLOW_LENGTH + " ");
        sb.append("                      THEN ? ");
        sb.append("                  WHEN LENGTH(ABS(URIAGE_NUKI_VL)) >= " + VL_ITEM_OVERFLOW_LENGTH + " ");
        sb.append("                      THEN ? ");
        sb.append("                  WHEN LENGTH(ABS(POS_VL))         >= " + VL_ITEM_OVERFLOW_LENGTH + " ");
        sb.append("                      THEN ? ");
        sb.append("                  ELSE ? END ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("        FROM ");
        sb.append("            ( ");
        sb.append("                SELECT ");
        sb.append("                      COMP_CD ");
        sb.append("                    , KEIJO_DT ");
        sb.append("                    , TENPO_CD ");
        sb.append("                    , BUNRUI1_CD ");
        sb.append("                    , SUM(KYAKU_QT)       KYAKU_QT ");
        sb.append("                    , SUM(URIAGE_QT)      URIAGE_QT ");
        sb.append("                    , SUM(URIAGE_NUKI_VL) URIAGE_NUKI_VL ");
        sb.append("                    , SUM(POS_QT)         POS_QT ");
        sb.append("                    , SUM(POS_VL)         POS_VL ");
        sb.append("                FROM ");
        sb.append("                    DT_DWH_IF_RUISEKI ");
        sb.append("                WHERE ");
        sb.append("                    COMP_CD = ? ");
        sb.append("                GROUP BY ");
        sb.append("                      COMP_CD ");
        sb.append("                    , KEIJO_DT ");
        sb.append("                    , TENPO_CD ");
        sb.append("                    , BUNRUI1_CD ");
        sb.append("            ) SUB_DDIR ");
        sb.append("        WHERE ");
        sb.append("            EXISTS ");
        sb.append("            ( ");
        sb.append("                SELECT ");
        sb.append("                      SUB_WHERE.COMP_CD ");
        sb.append("                    , SUB_WHERE.KEIJO_DT ");
        //2014/12/04 chou グローバル化対応 店舗コード対応 MOD START
        //sb.append("                    , LPAD(RTRIM(SUB_WHERE.TENPO_CD ), 4, '0')   TENPO_CD ");
        sb.append("                    , LPAD(RTRIM(SUB_WHERE.TENPO_CD ), 6, '0')   TENPO_CD ");
        //2014/12/04 chou グローバル化対応 店舗コード対応 MOD END
        sb.append("                    , LPAD(RTRIM(SUB_WHERE.BUNRUI1_CD ), 6, '0') BUNRUI1_CD ");
        sb.append("                FROM ");
        sb.append("                    ( ");
        sb.append("                        SELECT ");
        sb.append("                              DDIR.COMP_CD ");
        sb.append("                            , DDIR.KEIJO_DT ");
        sb.append("                            , DDIR.TENPO_CD ");
        sb.append("                            , DDIR.BUNRUI1_CD ");
        sb.append("                        FROM ");
        sb.append("                            DT_DWH_IF_RUISEKI DDIR ");
        sb.append("                        WHERE ");
        sb.append("                            DDIR.COMP_CD        =  ? AND ");
        sb.append("                            DDIR.BUNRUI1_CD     <> ? AND ");
        sb.append("                            DDIR.DATA_RENKEI_FG =  ? ");
        sb.append("                        GROUP BY ");
        sb.append("                              DDIR.COMP_CD ");
        sb.append("                            , DDIR.KEIJO_DT ");
        sb.append("                            , DDIR.TENPO_CD ");
        sb.append("                            , DDIR.BUNRUI1_CD ");
        sb.append("                    ) SUB_WHERE ");
        sb.append("                WHERE ");
        sb.append("                    SUB_DDIR.COMP_CD    = SUB_WHERE.COMP_CD    AND ");
        sb.append("                    SUB_DDIR.KEIJO_DT   = SUB_WHERE.KEIJO_DT   AND ");
        sb.append("                    SUB_DDIR.TENPO_CD   = SUB_WHERE.TENPO_CD   AND ");
        sb.append("                    SUB_DDIR.BUNRUI1_CD = SUB_WHERE.BUNRUI1_CD ");
        sb.append("            ) AND ");
        sb.append("            SUB_DDIR.COMP_CD      = ? AND ");
        sb.append("            SUB_DDIR.BUNRUI1_CD  <> ? AND ");
        sb.append("            SUB_DDIR.BUNRUI1_CD  <> ?  ");
        sb.append("    ) ");

        return sb.toString();
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
}