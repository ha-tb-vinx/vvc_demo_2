package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: KintaiIFDataCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 3.00 (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
 *  @Version 3.01 (2013.10.27) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №044
 *  @Version 3.02 2014.02.28  Y.Tominaga  [シス0081] 速度改善対応
 */
public class KintaiIFDataCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "勤怠IF用データ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB131050";
    /** DB日付取得 */
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /** 法人コード */
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

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
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用データ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ削除処理を開始します。");

        // 勤怠IF用累積データ削除処理
        int IDX = 1;
        preparedStatementDel = invoker.getDataBase().prepareStatement(delKintaiIFSql());
        // 実行
        preparedStatementDel.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ挿入処理を開始します。");

        // 勤怠IF用累積データ(当日差分)挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        IDX = 1;
        preparedStatementIns = invoker.getDataBase().prepareStatement(insKintaiIFSql());
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DPT_CODE_9999);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DATA_RENKEI_FG_MI);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_01);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_02);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_00);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, COMP_CD);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.DPT_CODE_9999);

        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsertCount + "件の勤怠IF用データを挿入しました。");
        invoker.infoLog(strUserID + "　：　勤怠IF用データ:勤怠IF用データ挿入処理を終了します。");
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用データ作成処理を終了します。");
    }

    private String delKintaiIFSql() {
        StringBuffer sb = new StringBuffer("");
        
        // 勤怠IF用データ削除用DeleteSQL構築
        sb.append(" TRUNCATE TABLE ");
        sb.append("     DT_KINTAI_IF ");
        
        return sb.toString();
    }

    private String insKintaiIFSql() {
        StringBuffer sb = new StringBuffer("");
        
        // 勤怠IF用データ作成用InsertSQL構築
        sb.append(" INSERT /*+ APPEND */ INTO ");
        sb.append("    DT_KINTAI_IF ");
        sb.append("    ( ");
        sb.append("          COMP_CD ");
        sb.append("        , URIAGE_DT ");
        sb.append("        , TENPO_CD ");
        sb.append("        , BUMON_CD ");
        sb.append("        , TIME_NO ");
        sb.append("        , JISEKI_URIAGE_VL ");
        sb.append("        , JISEKI_HANBAI_QT ");
        sb.append("        , JISEKI_KYAKU_QT ");
        sb.append("        , ERR_KB ");
        sb.append("        , INSERT_USER_ID ");
        sb.append("        , INSERT_TS ");
        sb.append("        , UPDATE_USER_ID ");
        sb.append("        , UPDATE_TS ");
        sb.append("    ) ");
        sb.append("    WITH SUB_MAIN AS ");
        sb.append("        ( ");
        sb.append("            SELECT ");
        sb.append("                SUB_WHERE.COMP_CD , ");
        sb.append("                SUB_WHERE.KEIJO_DT , ");
        sb.append("                SUB_WHERE.TENPO_CD , ");
        sb.append("                SUB_WHERE.BUMON_CD ");
        sb.append("            FROM ( ");
        sb.append("                    SELECT ");
        sb.append("                        DKIR.COMP_CD , ");
        sb.append("                        DKIR.KEIJO_DT , ");
        sb.append("                        DKIR.TENPO_CD , ");
        sb.append("                        COALESCE(RKBH.BUMON_CD , '   ') AS BUMON_CD ");
        sb.append("                    FROM ");
        sb.append("                        DT_KINTAI_IF_RUISEKI DKIR ");
        sb.append("                    LEFT JOIN ");
        sb.append("                        R_KINTAI_BUMON_HENKAN    RKBH ");
        sb.append("                        ON  DKIR.COMP_CD         = RKBH.COMP_CD ");
        sb.append("                        AND DKIR.BUNRUI1_CD      = RKBH.BUNRUI1_CD ");
        sb.append("                    WHERE ");
        sb.append("                        DKIR.COMP_CD             = ? ");
        sb.append("                        AND DKIR.BUNRUI1_CD     <> ? ");
        sb.append("                        AND DKIR.DATA_RENKEI_FG  = ? ");
        sb.append("                    GROUP BY ");
        sb.append("                        DKIR.COMP_CD , ");
        sb.append("                        DKIR.KEIJO_DT , ");
        sb.append("                        DKIR.TENPO_CD , ");
        sb.append("                        RKBH.BUMON_CD ");
        sb.append("                 ) SUB_WHERE ");
        sb.append("        ) ");
        sb.append("    ( ");
        sb.append("        SELECT ");
        sb.append("            SUB.COMP_CD , ");
        sb.append("            SUB.KEIJO_DT , ");
        sb.append("            SUBSTR(RTRIM(SUB.TENPO_CD), - 3), ");
        sb.append("            SUB.BUMON_CD , ");
        sb.append("            SUB.TIME_NO , ");
        sb.append("            SUB.URIAGE_NUKI_VL , ");
        sb.append("            SUB.URIAGE_QT , ");
        sb.append("            SUB.KYAKU_QT , ");
        sb.append("            CASE ");
        sb.append("               WHEN TRIM(SUB.BUMON_CD) IS NULL    THEN ?  ");
        sb.append("                WHEN LENGTH(URIAGE_NUKI_VL)  >= 11 THEN ? ");
        sb.append("                ELSE ? ");
        sb.append("                END ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("        FROM ");
        sb.append("            SUB_MAIN ");
        sb.append("        INNER JOIN  ");
        sb.append("            ( ");
        sb.append("            SELECT ");
        sb.append("                DKIR.COMP_CD , ");
        sb.append("                DKIR.KEIJO_DT , ");
        sb.append("                DKIR.TENPO_CD , ");
        sb.append("                COALESCE(RKBH.BUMON_CD , '   ') AS BUMON_CD , ");
        sb.append("                DKIR.TIME_NO , ");
        sb.append("                SUM(DKIR.URIAGE_NUKI_VL) URIAGE_NUKI_VL , ");
        sb.append("                SUM(DKIR.URIAGE_QT) URIAGE_QT , ");
        sb.append("                SUM(DKIR.KYAKU_QT) KYAKU_QT ");
        sb.append("            FROM ");
        sb.append("                DT_KINTAI_IF_RUISEKI DKIR ");
        sb.append("            LEFT JOIN ");
        sb.append("                R_KINTAI_BUMON_HENKAN RKBH ");
        sb.append("                ON  DKIR.COMP_CD     = RKBH.COMP_CD ");
        sb.append("                AND DKIR.BUNRUI1_CD  = RKBH.BUNRUI1_CD ");
        sb.append("            WHERE ");
        sb.append("                DKIR.COMP_CD         = ? ");
        sb.append("                AND DKIR.BUNRUI1_CD <> ? ");
        sb.append("            GROUP BY ");
        sb.append("                DKIR.COMP_CD , ");
        sb.append("                DKIR.KEIJO_DT , ");
        sb.append("                DKIR.TENPO_CD , ");
        sb.append("                RKBH.BUMON_CD, ");
        sb.append("                DKIR.TIME_NO ");
        sb.append("            ) SUB ");
        sb.append("            ON  SUB.COMP_CD  = SUB_MAIN.COMP_CD  ");
        sb.append("            AND SUB.KEIJO_DT = SUB_MAIN.KEIJO_DT ");
        sb.append("            AND SUB.TENPO_CD = SUB_MAIN.TENPO_CD ");
        sb.append("            AND SUB.BUMON_CD = SUB_MAIN.BUMON_CD ");
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