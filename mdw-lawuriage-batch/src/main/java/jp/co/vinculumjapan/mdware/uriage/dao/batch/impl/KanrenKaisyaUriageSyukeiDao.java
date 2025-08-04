package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.Map;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: KanrenKaisyaUriageSyukeiDao クラス</p>
 *  <P>説明: 関連会社売上集計処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 
 */
public class KanrenKaisyaUriageSyukeiDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "関連会社売上集計処理";
    // バッチID
    private static final String BATCH_ID = "URIB111010";
    // 削除関連会社売上累積ワークSQL用定数
    private static final String DEL_WK_KANCOMP = "deleteWKKancomp";
    // 伝票種別「03：手書仕入」
    private static final String DENPYO_KB = "'03'";
    // 本締処理区分「0：未処理」
    private static final String HONZIMESYORI_KB = "'0'";
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // プロパティファイルより関連会社法人コード取得
    private static final String KANREN_COMP_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.KANREN_COMP_CD);
    // プロパティファイルよりテナント消化仕入DPT取得
    private static final Map TENANT_SYOKA_SHIIRE_DPT = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.TENANT_SYOKA_SHIIRE_DPT);
    // システム日付
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();

    /**
     * 関連会社売上集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 関連会社売上累積ワーク追加件数
        int intCountTanpin = 0;
        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementEx = null;

        // ログ出力
        invoker.infoLog(strUserID + "　：　関連会社売上集計処理を開始します。");
        invoker.infoLog(strUserID + "　：　関連会社売上累積ワーク削除処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(DEL_WK_KANCOMP);

        preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　関連会社売上累積ワークを全件削除しました。");
        invoker.infoLog(strUserID + "　：　関連会社売上累積ワーク削除処理を終了します。");
        invoker.infoLog(strUserID + "　：　関連会社売上累積ワーク追加処理を開始します。");

        // SQLを取得し、パラメータを条件にバインドする
        preparedStatementEx = invoker.getDataBase().prepareStatement(getInsertWKKancomp(TENANT_SYOKA_SHIIRE_DPT.keySet().toString().replace('[', '(').replace(']', ')')));
        preparedStatementEx.setString(1, KANREN_COMP_CD);
        preparedStatementEx.setString(2, BATCH_ID);
        preparedStatementEx.setString(3, DBSERVER_DT);
        preparedStatementEx.setString(4, BATCH_ID);
        preparedStatementEx.setString(5, DBSERVER_DT);
        preparedStatementEx.setString(6, COMP_CD);
        preparedStatementEx.setString(7, COMP_CD);

        // IF単品別売上データ追加件数を設定する
        intCountTanpin = preparedStatementEx.executeUpdate();

        // ログ出力
        invoker.infoLog(strUserID + "　：　" + intCountTanpin + "件の関連会社売上累積ワークを追加しました。");
        invoker.infoLog(strUserID + "　：　関連会社売上累積ワーク追加処理を終了します。");
        invoker.infoLog(strUserID + "　：　関連会社売上集計処理を終了します。");
    }

    /**
     * 関連会社売上累積ワーク用SQLを作成し、返します
     * @return String SQL文
     */
    private String getInsertWKKancomp(String strTenant) {

        StringBuffer sqlBuffer = new StringBuffer();

        sqlBuffer.append("	INSERT INTO ");
        sqlBuffer.append("		WK_KANCOMP_DPT_URI_RUISEKI ");
        sqlBuffer.append("		(");
        sqlBuffer.append("		COMP_CD, ");
        sqlBuffer.append("		KEIJO_DT, ");
        sqlBuffer.append("		TENPO_CD, ");
        sqlBuffer.append("		BUNRUI1_CD, ");
        sqlBuffer.append("		URIAGE_VL, ");
        sqlBuffer.append("      URIAGE_QT, ");
        sqlBuffer.append("      BUNRUI1_KYAKU_QT, ");
        sqlBuffer.append("		INSERT_USER_ID, ");
        sqlBuffer.append("		INSERT_TS, ");
        sqlBuffer.append("		UPDATE_USER_ID, ");
        sqlBuffer.append("		UPDATE_TS) ");
        sqlBuffer.append("	(SELECT ");
        sqlBuffer.append("		?, ");
        sqlBuffer.append("		KANRI_DT, ");
        sqlBuffer.append("		TENPO_CD, ");
        sqlBuffer.append("		BUNRUI1_CD, ");
        sqlBuffer.append("		SUM(SUMGENKA_ZEINUKI_VL) SUMGENKA_ZEINUKI_VL, ");
        sqlBuffer.append("      '1', ");
        sqlBuffer.append("      '1', ");
        sqlBuffer.append("		?, ");
        sqlBuffer.append("		?, ");
        sqlBuffer.append("		?, ");
        sqlBuffer.append("		? ");
        sqlBuffer.append("	FROM ");
        sqlBuffer.append("		DT_SIR_RUISEKI ");
        sqlBuffer.append("	WHERE ");
        sqlBuffer.append("		COMP_CD = ? ");
        sqlBuffer.append("	AND BUNRUI1_CD IN " + strTenant);
        sqlBuffer.append("	AND DENPYO_KB = " + DENPYO_KB);
        sqlBuffer.append("	AND KANRI_DT >= ");
        sqlBuffer.append("		(SELECT ");
        sqlBuffer.append("			MIN(START_DT) START_DT ");
        sqlBuffer.append("		FROM ");
        sqlBuffer.append("			R_CALENDAR ");
        sqlBuffer.append("		WHERE ");
        sqlBuffer.append("			COMP_CD = ? ");
        sqlBuffer.append("		AND HONZIMESYORI_KB = " + HONZIMESYORI_KB + ") ");
        sqlBuffer.append("		GROUP BY ");
        sqlBuffer.append("			KANRI_DT,TENPO_CD,BUNRUI1_CD ) ");

        return sqlBuffer.toString();
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
            DaoIf dao = new KanrenKaisyaUriageSyukeiDao();
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
