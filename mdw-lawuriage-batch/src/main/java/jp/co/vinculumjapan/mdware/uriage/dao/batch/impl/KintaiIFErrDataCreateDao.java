package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;


/**
 *  <P>タイトル: KintaiIFErrDataCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 3.00 (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
 *  @Version 3.01 (2013.10.30) T.Ooshiro  [CUS00057] POSインターフェイス仕様変更対応 結合テスト課題対応 №070
 */
public class KintaiIFErrDataCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "勤怠IF用エラーデータ作成処理";
    /** バッチID */
    private static final String BATCH_ID = "URIB131060";
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
        PreparedStatementEx preparedStatementIns = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用エラーデータ作成処理を開始します。");
        invoker.infoLog(strUserID + "　：　勤怠IF用エラーデータ:勤怠IF用エラーデータ挿入処理を開始します。");

        // 勤怠IF用エラーデータ挿入処理
        // SQLを取得し、パラメータを条件にバインドする
        int IDX = 1;
        preparedStatementIns = invoker.getDataBase().prepareStatement(insKintaiIFErrSql());
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, BATCH_ID);
        preparedStatementIns.setString(IDX++, DBSERVER_DT);
        preparedStatementIns.setString(IDX++, UriageCommonConstants.ERR_KB_00);
        preparedStatementIns.setString(IDX++, COMP_CD);

        // 実行
        intInsertCount = preparedStatementIns.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intInsertCount + "件の勤怠IF用エラーデータを挿入しました。");
        invoker.infoLog(strUserID + "　：　勤怠IF用エラーデータ:勤怠エラーデータ用データ挿入処理を終了します。");
        invoker.infoLog(strUserID + "　：　勤怠Ｓ：勤怠IF用エラーデータ作成処理を終了します。");
    }

    private String insKintaiIFErrSql() {
        StringBuffer sb = new StringBuffer("");
        
        // 勤怠IF用エラーデータ作成用InsertSQL構築
        sb.append(" INSERT INTO ");
        sb.append("    DT_ERR_KINTAI_IF ");
        sb.append("    ( ");
        sb.append("          COMP_CD ");
        sb.append("        , SEQ_RB ");
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
        sb.append("    ( ");
        sb.append("        SELECT ");
        sb.append("              COMP_CD ");
        sb.append("            , SEQ_DT_ERR_KINTAI_IF.NEXTVAL ");
        sb.append("            , URIAGE_DT ");
        sb.append("            , TENPO_CD ");
        sb.append("            , BUMON_CD ");
        sb.append("            , TIME_NO ");
        sb.append("            , JISEKI_URIAGE_VL ");
        sb.append("            , JISEKI_HANBAI_QT ");
        sb.append("            , JISEKI_KYAKU_QT ");
        sb.append("            , ERR_KB ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("            , ? ");
        sb.append("        FROM");
        sb.append("            DT_KINTAI_IF ");
        sb.append("        WHERE ");
        sb.append("            ERR_KB  <> ? AND ");
        sb.append("            COMP_CD =  ? ");
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
