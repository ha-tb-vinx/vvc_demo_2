package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル： TenbetsuInshizeiTaisyoDataCreateDao クラス</p>
 * <p>説明　　：「レジ別取引精算データ」より、「店別印紙税対象データ」を作成する。</p>
 * <p>著作権　： Copyright (c) 2013</p>
 * <p>会社名　： VINX</p>
 * @author   Y.Tominaga
 * @Version 3.00 (2013.10.09) Y.Tominaga [CUS00057] ランドローム様対応　店別印紙税対象データ作成
 * @Version 3.01 (2017.06.14) X.Liu #5399 
 */
public class TenbetsuInshizeiTaisyoDataCreateDao implements DaoIf {

    // 法人コード
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // バッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // DB日付取得
    private static final String DBSERVER_DT = FiResorceUtility.getDBServerTime();
    // バッチID
    private static final String BATCH_ID = "URIB041030";
    // バッチ名称
    private static final String BATCH_NAME = "店別印紙税対象データ作成";
    
    // システムコントロールマスタの印紙税発行回数設定項目を取得する。
    private static final String INSHI_HAKKO_KOMOKU =FiResorceUtility.getPropertie(UriResorceKeyConstant.INSHI_HAKKO_KOMOKU);
    // システムコントロールマスタの印紙税発行回数の取引コード
    private static final String INSHI_HAKKO_TORIHIKI_CD =FiResorceUtility.getPropertie(UriResorceKeyConstant.INSHI_HAKKO_TORIHIKI_CD);
    
    /**
     * 店別精算データ集計処理を行う
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserId = invoker.getUserId() + " " + BATCH_NAME;
        
        // 開始ログを出力する
        invoker.infoLog(strUserId + "　：　店別印紙税対象データ作成処理を開始します。");
        
        // 実行ログを出力する
        invoker.infoLog(strUserId + "　：　レジ別取引精算データより、店別印紙税対象データの作成処理を開始します。");
        
        // 店別印紙税対象データを作成する
        PreparedStatementEx psDtTenbetsuInshizeiInsert = null;
        int index = 1;
        int kensu = 0;
        
        // SQL取得
        psDtTenbetsuInshizeiInsert = invoker.getDataBase().prepareStatement(dtTenbetsuInshizeiInsert());
        
        psDtTenbetsuInshizeiInsert.setString(index++, BATCH_ID);
        psDtTenbetsuInshizeiInsert.setString(index++, DBSERVER_DT);
        psDtTenbetsuInshizeiInsert.setString(index++, BATCH_ID);
        psDtTenbetsuInshizeiInsert.setString(index++, DBSERVER_DT);
        psDtTenbetsuInshizeiInsert.setString(index++, COMP_CD);
        psDtTenbetsuInshizeiInsert.setString(index++, BATCH_DT);
        psDtTenbetsuInshizeiInsert.setString(index++, INSHI_HAKKO_TORIHIKI_CD);
        
        kensu = psDtTenbetsuInshizeiInsert.executeUpdate();
        
        // 売上金額整合性チェックログ終了ログを出力する
        invoker.infoLog(strUserId + "　：　" + kensu + "件の店別印紙税対象データを作成しました。");
        invoker.infoLog(strUserId + "　：　レジ別取引精算データより、店別印紙税対象データの作成処理を終了します。");

        // 終了ログを出力する
        invoker.infoLog(strUserId + "　：　店別印紙税対象データ作成処理を終了します。");
    }


    /**
     * 店別印紙税データ作成処理のSQL
     * @return
     */
    private String dtTenbetsuInshizeiInsert() {
        
        StringBuffer sb = new StringBuffer();
        
        //#5399 Mod X.Liu 2017.06.16 (S)
        sb.append("MERGE ");
        sb.append("INTO DT_TEN_INSHIZEI DTI ");
        sb.append("  USING ( ");
        sb.append("    SELECT");
        sb.append("      COMP_CD");
        sb.append("      , KEIJO_DT");
        sb.append("      , TENPO_CD");
        sb.append("      ," + getInshizei() + " AS POS_QT");
        sb.append("      ," + getInshizei() + " AS SEISAN_QT");
        sb.append("      , ? AS INSERT_USER_ID");
        sb.append("      , ? AS INSERT_TS");
        sb.append("      , ? AS UPDATE_USER_ID");
        sb.append("      , ? AS UPDATE_TS ");
        sb.append("    FROM");
        sb.append("      DT_REGI_TORIHIKI_SEISAN ");
        sb.append("    WHERE");
        sb.append("      COMP_CD = ? ");
        sb.append("      AND DATA_SAKUSEI_DT = ? ");
        sb.append("      AND TORIHIKI_CD = ?");
        sb.append("  ) DRTS ");
        sb.append("    ON ( ");
        sb.append("      DTI.COMP_CD = DRTS.COMP_CD ");
        sb.append("      AND DTI.KEIJO_DT = DRTS.KEIJO_DT ");
        sb.append("      AND DTI.TENPO_CD = DRTS.TENPO_CD");
        sb.append("    ) WHEN MATCHED THEN UPDATE ");
        sb.append("SET");
        sb.append("  POS_QT = DRTS.POS_QT");
        sb.append("  , SEISAN_QT = DRTS.SEISAN_QT");
        sb.append("  , UPDATE_USER_ID = DRTS.UPDATE_USER_ID");
        sb.append("  , UPDATE_TS = DRTS.UPDATE_TS WHEN NOT MATCHED THEN ");
//        sb.append(" INSERT INTO");
//        sb.append("     DT_TEN_INSHIZEI");
        sb.append("     INSERT ");
        //#5399 Mod X.Liu 2017.06.16 (E)
        sb.append("     (");
        sb.append("     COMP_CD");
        sb.append("     ,KEIJO_DT");
        sb.append("     ,TENPO_CD");
        sb.append("     ,POS_QT");
        sb.append("     ,SEISAN_QT");
        sb.append("     ,INSERT_USER_ID");
        sb.append("     ,INSERT_TS");
        sb.append("     ,UPDATE_USER_ID");
        sb.append("     ,UPDATE_TS");
        sb.append("     )");
        //#5399 Mod X.Liu 2017.06.14 (S)
//        sb.append("     SELECT");
//        sb.append("         DRTS.COMP_CD");
//        sb.append("         ,DRTS.KEIJO_DT");
//        sb.append("         ,DRTS.TENPO_CD");
//        sb.append("         ,DRTS." + getInshizei());
//        sb.append("         ,DRTS." + getInshizei());
//        sb.append("         ,?");
//        sb.append("         ,?");
//        sb.append("         ,?");
//        sb.append("         ,?");
//        sb.append("     FROM");
//        sb.append("         DT_REGI_TORIHIKI_SEISAN DRTS");
//        sb.append("     WHERE");
//        sb.append("         DRTS.COMP_CD     = ? AND");
//        sb.append("         DRTS.KEIJO_DT    = ? AND");
//        sb.append("         DRTS.TORIHIKI_CD = ?");
        sb.append(" VALUES (    ");
        sb.append("   DRTS.COMP_CD  ");
        sb.append("   , DRTS.KEIJO_DT   ");
        sb.append("   , DRTS.TENPO_CD   ");
        sb.append("   , DRTS.POS_QT ");
        sb.append("   , DRTS.SEISAN_QT  ");
        sb.append("   , DRTS.INSERT_USER_ID ");
        sb.append("   , DRTS.INSERT_TS  ");
        sb.append("   , DRTS.UPDATE_USER_ID ");
        sb.append("   , DRTS.UPDATE_TS  ");
        sb.append(" )   ");
        //#5399 Mod X.Liu 2017.06.14 (E)
        
        return sb.toString();
    }

    
    /**
     * システムコントロールマスタの印紙税発行回数の設定項目を取得する。
     * @return
     */
    private String getInshizei() {
        
        if (INSHI_HAKKO_KOMOKU == null || INSHI_HAKKO_KOMOKU.trim().length() == 0 ) {
            return null;
        } else {

            return INSHI_HAKKO_KOMOKU;
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