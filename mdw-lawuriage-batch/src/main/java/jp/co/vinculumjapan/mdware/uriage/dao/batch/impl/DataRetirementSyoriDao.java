package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.Iterator;

import jp.co.vinculumjapan.mdware.uriage.dao.impl.DataRetirementDao;
import jp.co.vinculumjapan.mdware.uriage.dao.input.DataRetirementDaoInputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.output.DataRetirementDaoOutputBean;
import jp.co.vinculumjapan.mdware.uriage.dao.output.row.DataRetirementDaoOutputRowBean;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 *  <P>タイトル: DataRetirementSyoriDao クラス</p>
 *  <P>説明: データ退役処理です。</p>
 *  <P>著作権: Copyright (c) 2009</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @author L.Cheng
 *  @version 1.0 (2009.05.27) 初版作成
 */
public class DataRetirementSyoriDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "データ退役処理";

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    /**
     * データ退役処理を行い
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;

        // ログ出力
        invoker.infoLog(strUserID + "　：　データ退役処理を開始します。");

        // データ退役DAO
        DataRetirementDaoInputBean inputBean = new DataRetirementDaoInputBean();
        // 法人コード
        inputBean.setCompCd(COMP_CD);

        // データ退役DAOの実行
        DataRetirementDao dataRetirement = new DataRetirementDao();
        DataRetirementDaoOutputBean outputBean = new DataRetirementDaoOutputBean();

        // 入力ビーンをセット
        dataRetirement.setInputObject(inputBean);

        invoker.InvokeDao(dataRetirement);

        // 出力ビーンをセット
        outputBean = (DataRetirementDaoOutputBean) dataRetirement.getOutputObject();

        // 《テーブル別累積件数RowBean》の取得
        Iterator iterator = outputBean.getDetailList().iterator();

        // 一時領域明細行RowBeanにデータを追加
        while (iterator.hasNext()) {

            DataRetirementDaoOutputRowBean outputRowBean = (DataRetirementDaoOutputRowBean) iterator.next();
            // 件数ログ出力
            invoker.infoLog(strUserID + "　：　" + outputRowBean.getTableLogicalNa() + "から" + outputRowBean.getRetireCnt() + "件のデータを削除しました。");
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　データ退役処理を終了します。");
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
            DaoIf dao = new DataRetirementSyoriDao();
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
