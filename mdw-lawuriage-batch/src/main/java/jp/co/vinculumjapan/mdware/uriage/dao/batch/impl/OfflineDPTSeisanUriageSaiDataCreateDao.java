package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.common.util.CalcUtility;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル： OfflineDPTSeisanUriageSaiDataCreateDao クラス</p>
 * <p>説明　　： 「オフラインDPT精算売上ワーク」からデータを抽出し「オフラインDPT精算売上差異ワーク」を作成する。
 *                その際、「オフラインDPT精算売上累積ワーク」がなければINS、あればUPDする。             
 * </p>
 * <p>著作権　： Copyright (c) 2009</p>
 * <p>会社名　： Vinculum Japan Corp.</p>
 * @author   M.TADA
 * @version 1.00 (2009.12.25) 初版作成
 */
public class OfflineDPTSeisanUriageSaiDataCreateDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ名称
    private static final String BATCH_NAME = "ｵﾌﾗｲﾝDPT精算売上差異ﾃﾞｰﾀ作成処理";

    /**
     * メイン処理
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;

        // 売上金額（税込）
        String strUriKomiVl = "0";
        // 売上数量
        String strUriQt = "0";
        // 値引金額
        String strNebikiVl = "0";
        // 値引数量
        String strNebikiQt = "0";
        // 返品金額
        String strHenpinVl = "0";
        // 返品数量
        String strHenpinQt = "0";
        // DPT客数
        String strBunruiKyakuQt = "0";

        PreparedStatementEx ps = null;
        PreparedStatementEx psRuiSel = null;
        PreparedStatementEx psRuiIns = null;
        PreparedStatementEx psRuiUpd = null;
        PreparedStatementEx psSaiIns = null;
        ResultSet rs = null;
        ResultSet rsRui = null;
        int i = 0;
        int iRS = 0;
        int iR = 0;
        int iS = 0;
        int cntRuiUpd = 0;
        int cntRuiUpdSum = 0;
        int cntRuiIns = 0;
        int cntRuiInsSum = 0;
        int cntSaiIns = 0;
        int cntSaiInsSum = 0;
        
        // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ抽出処理
        psRuiSel = invoker.getDataBase().prepareStatement("selectWkOffDptSeiUriRui");
        // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ追加処理
        psRuiIns = invoker.getDataBase().prepareStatement("insertWkOffDptSeiUriRui");
        // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ更新処理
        psRuiUpd = invoker.getDataBase().prepareStatement("updateWkOffDptSeiUriRui");
        // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ追加処理
        psSaiIns = invoker.getDataBase().prepareStatement("insertWkOffDptSeiUriSai");
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上差異データ作成処理を開始します。");
        
        // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ削除処理
        deleteDptSeiUriSai(invoker, strUserId);
        
        // ｵﾌﾗｲﾝDPT売上精算ﾜｰｸ抽出処理
        ps = invoker.getDataBase().prepareStatement("selectWkOffDptSeiUri");
        
        ps.setString(++i, COMP_CD);

        // ｵﾌﾗｲﾝDPT売上精算ﾜｰｸ抽出処理 実行
        rs = ps.executeQuery();
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上累積更新処理を開始します。");
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上差異追加処理を開始します。");

        while (rs.next()) {
            
            // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ抽出処理
            iRS = 0;
            psRuiSel.setString(++iRS, rs.getString("COMP_CD"));
            psRuiSel.setString(++iRS, rs.getString("KEIJO_DT"));
            psRuiSel.setString(++iRS, rs.getString("TENPO_CD"));
//            psRuiSel.setString(++iRS, rs.getString("ENTRY_DT"));
            psRuiSel.setString(++iRS, rs.getString("POS_NB"));
//            psRuiSel.setString(++iRS, rs.getString("JIGYOBU_CD"));
//            psRuiSel.setString(++iRS, rs.getString("GYOSYU_CD"));
            psRuiSel.setString(++iRS, rs.getString("BUNRUI1_CD"));
            
            // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ抽出処理 実行
            rsRui = psRuiSel.executeQuery();
            
            if (rsRui.next()) {

                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸがある場合
                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ更新処理
                iR = 0;
                psRuiUpd.setString(++iR, rs.getString("ENTRY_DT"));
                psRuiUpd.setString(++iR, rs.getString("JIGYOBU_CD"));
                psRuiUpd.setString(++iR, rs.getString("GYOSYU_CD"));
                psRuiUpd.setString(++iR, rs.getString("URIAGE_KOMI_VL"));
                psRuiUpd.setString(++iR, rs.getString("URIAGE_QT"));
                psRuiUpd.setString(++iR, rs.getString("NEBIKI_VL"));
                psRuiUpd.setString(++iR, rs.getString("NEBIKI_QT"));
                psRuiUpd.setString(++iR, rs.getString("HENPIN_VL"));
                psRuiUpd.setString(++iR, rs.getString("HENPIN_QT"));
                psRuiUpd.setString(++iR, rs.getString("BUNRUI1_KYAKU_QT"));
                psRuiUpd.setString(++iR, rs.getString("COMP_CD"));
                psRuiUpd.setString(++iR, rs.getString("KEIJO_DT"));
                psRuiUpd.setString(++iR, rs.getString("TENPO_CD"));
                psRuiUpd.setString(++iR, rs.getString("POS_NB"));
                psRuiUpd.setString(++iR, rs.getString("BUNRUI1_CD"));
                
                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ更新処理 実行
                cntRuiUpd = psRuiUpd.executeUpdate();
                cntRuiUpdSum = cntRuiUpdSum + cntRuiUpd; 
                
                // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ追加のために変数セット
                // ｵﾌﾗｲﾝDPT売上精算ﾜｰｸ.売上金額-ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ.売上金額
                strUriKomiVl = CalcUtility.minus(rs.getString("URIAGE_KOMI_VL"), rsRui.getString("URIAGE_KOMI_VL"));
                strUriQt = CalcUtility.minus(rs.getString("URIAGE_QT"), rsRui.getString("URIAGE_QT"));
                strNebikiVl = CalcUtility.minus(rs.getString("NEBIKI_VL"), rsRui.getString("NEBIKI_VL"));
                strNebikiQt = CalcUtility.minus(rs.getString("NEBIKI_QT"), rsRui.getString("NEBIKI_QT"));
                strHenpinVl = CalcUtility.minus(rs.getString("HENPIN_VL"), rsRui.getString("HENPIN_VL"));
                strHenpinQt = CalcUtility.minus(rs.getString("HENPIN_QT"), rsRui.getString("HENPIN_QT"));
                strBunruiKyakuQt = CalcUtility.minus(rs.getString("BUNRUI1_KYAKU_QT"), rsRui.getString("BUNRUI1_KYAKU_QT"));
                
            } else {

                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸがない場合
                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ追加処理
                iR = 0;
                psRuiIns.setString(++iR, rs.getString("COMP_CD"));
                psRuiIns.setString(++iR, rs.getString("KEIJO_DT"));
                psRuiIns.setString(++iR, rs.getString("TENPO_CD"));
                psRuiIns.setString(++iR, rs.getString("ENTRY_DT"));
                psRuiIns.setString(++iR, rs.getString("POS_NB"));
                psRuiIns.setString(++iR, rs.getString("JIGYOBU_CD"));
                psRuiIns.setString(++iR, rs.getString("GYOSYU_CD"));
                psRuiIns.setString(++iR, rs.getString("BUNRUI1_CD"));
                psRuiIns.setString(++iR, rs.getString("URIAGE_KOMI_VL"));
                psRuiIns.setString(++iR, rs.getString("URIAGE_QT"));
                psRuiIns.setString(++iR, rs.getString("NEBIKI_VL"));
                psRuiIns.setString(++iR, rs.getString("NEBIKI_QT"));
                psRuiIns.setString(++iR, rs.getString("HENPIN_VL"));
                psRuiIns.setString(++iR, rs.getString("HENPIN_QT"));
                psRuiIns.setString(++iR, rs.getString("BUNRUI1_KYAKU_QT"));
                
                // ｵﾌﾗｲﾝDPT売上精算累積ﾜｰｸ追加処理 実行
                cntRuiIns = psRuiIns.executeUpdate();
                cntRuiInsSum = cntRuiInsSum + cntRuiIns; 
                
                // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ追加のために変数セット
                // ｵﾌﾗｲﾝDPT売上精算ﾜｰｸ.売上金額
                strUriKomiVl = rs.getString("URIAGE_KOMI_VL");
                strUriQt = rs.getString("URIAGE_QT");
                strNebikiVl = rs.getString("NEBIKI_VL");
                strNebikiQt = rs.getString("NEBIKI_QT");
                strHenpinVl = rs.getString("HENPIN_VL");
                strHenpinQt = rs.getString("HENPIN_QT");
                strBunruiKyakuQt = rs.getString("BUNRUI1_KYAKU_QT");
                
            }
            
            // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ追加処理
            iS = 0;
            psSaiIns.setString(++iS, rs.getString("COMP_CD"));
            psSaiIns.setString(++iS, rs.getString("KEIJO_DT"));
            psSaiIns.setString(++iS, rs.getString("TENPO_CD"));
            psSaiIns.setString(++iS, rs.getString("ENTRY_DT"));
            psSaiIns.setString(++iS, rs.getString("POS_NB"));
            psSaiIns.setString(++iS, rs.getString("JIGYOBU_CD"));
            psSaiIns.setString(++iS, rs.getString("GYOSYU_CD"));
            psSaiIns.setString(++iS, rs.getString("BUNRUI1_CD"));
            psSaiIns.setString(++iS, strUriKomiVl);
            psSaiIns.setString(++iS, strUriQt);
            psSaiIns.setString(++iS, strNebikiVl);
            psSaiIns.setString(++iS, strNebikiQt);
            psSaiIns.setString(++iS, strHenpinVl);
            psSaiIns.setString(++iS, strHenpinQt);
            psSaiIns.setString(++iS, strBunruiKyakuQt);
            
            // ｵﾌﾗｲﾝDPT売上精算差異ﾜｰｸ追加処理 実行
            cntSaiIns = psSaiIns.executeUpdate();
            cntSaiInsSum = cntSaiInsSum + cntSaiIns;
            
        }
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + cntRuiInsSum + "件のオフラインDPT精算売上累積データを追加しました。");
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上累積データ追加処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + cntRuiUpdSum + "件のオフラインDPT精算売上累積データを更新しました。");
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上累積データ更新処理を終了します。");
        invoker.infoLog(strUserId + "　：　" + cntSaiInsSum + "件のオフラインDPT売上精算売上差異データを追加しました。");
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上差異データ追加処理を終了します。");
        // 終了ログを出力する。
        invoker.infoLog(strUserId + "　：　オフラインDPT精算売上差異データ作成処理を終了します。");
        
    }

    /**
     * ｵﾌﾗｲﾝDPT精算売上差異ﾜｰｸ削除処理
     */
    private void deleteDptSeiUriSai(DaoInvokerIf invoker , String strUserId) throws Exception {

        int count = 0;
        int i = 0;
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　ｵﾌﾗｲﾝDPT精算売上差異ﾜｰｸ削除処理を開始します。");

        // ｵﾌﾗｲﾝDPT売上精算差異ﾃﾞｰﾀ削除
        PreparedStatementEx psDelSai = invoker.getDataBase().prepareStatement("deleteWkOffDptSeiUriSai");

        // パラメタ設定
        psDelSai.setString(++i, COMP_CD);
        
        // 実行
        count = psDelSai.executeUpdate();
        
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + count + "件のｵﾌﾗｲﾝDPT精算売上差異ﾜｰｸを削除しました。");
        invoker.infoLog(strUserId + "　：　ｵﾌﾗｲﾝDPT精算売上差異ﾜｰｸ削除処理を終了します。");

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
            DaoIf dao = new OfflineDPTSeisanUriageSaiDataCreateDao();
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
