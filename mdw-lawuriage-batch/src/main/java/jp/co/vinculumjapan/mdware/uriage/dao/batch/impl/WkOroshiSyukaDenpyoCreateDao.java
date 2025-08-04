package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;


/**
 *  <P>タイトル: WkOroshiSyukaDenpyoCreateDao クラス</p>
 *  <P>説明: </p>
 *  <P>著作権: Copyright (c) 2017</p>
 *  <P>会社名: VINX</P>
 *  @author VINX
 *  @Version 1.00 2017.04.04  X.Liu  FIVI対応 新規作成
 *  @Version 1.01 2017.06.19  X.Liu  FIVI対応 #5270
 *  @Version 1.02 2017.08.08  X.Liu  FIVI対応 #5752
 *  @Version 1.03 2017.08.03  X.Liu  FIVI対応 #5754
 */
public class WkOroshiSyukaDenpyoCreateDao implements DaoIf {

    /** バッチ処理名 */
    private static final String BATCH_NAME = "卸出荷伝票抽出処理";
    /** バッチ処理ID */
    private static final String BATCH_ID = "URIB501040";
    /** 削除SQL文 */
    private static final String DEL_SQL_1 = "TRUNCATE TABLE WK_OROSHI_SYUKA_DENPYO";
    private static final String DEL_SQL_2 = "TRUNCATE TABLE WK_OROSHI_SYUKA_MEI";
    //#5270 Add X.Liu 2017.06.19 (S)
    private static final String DEL_SQL_3 = "TRUNCATE TABLE WK_OROSHI_SYUKA_DENPYO_CANCEL";
    //#5270 Add X.Liu 2017.06.19 (E)
    //#5752 Add X.Liu 2017.08.08 (S)
    private static final String DEL_SQL_4 = "TRUNCATE TABLE WK_OROSHI_SYUKA_HAM_KO_SYO";
    private static final String DEL_SQL_5 = "TRUNCATE TABLE WK_OROSHI_SYUKA_HAM_SYO";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    //#5752 Add X.Liu 2017.08.08 (E)
    /**
     * 
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        int intCnt = 0;
        int intI = 1;
        String dbServerTime = FiResorceUtility.getDBServerTime();
        // オブジェクトを初期化する
        PreparedStatementEx ps = null;
        invoker.infoLog(strUserID + "　：　卸出荷伝票抽出処理を開始します。");
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票ワークのクリア処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(DEL_SQL_1);
        ps.execute();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ワークのクリア処理を終了します。");
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票明細ワークのクリア処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(DEL_SQL_2);
        ps.execute();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票明細ワークのクリア処理を終了します。");

        //#5270 Add X.Liu 2017.06.19 (S)
        invoker.infoLog(strUserID + "　：　卸出荷伝票VAT取消ワークのクリア処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(DEL_SQL_3);
        ps.execute();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票VAT取消ワークのクリア処理を終了します。");
        //#5270 Add X.Liu 2017.06.19 (E)

        //#5752 Add X.Liu 2017.08.08 (S)
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー構成商品ワークのクリア処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(DEL_SQL_4);
        ps.execute();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー構成商品ワークのクリア処理を終了します。");
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー商品ワークのクリア処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(DEL_SQL_5);
        ps.execute();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー商品ワークのクリア処理を終了します。");
        //#5752 Add X.Liu 2017.08.08 (E)

        invoker.infoLog(strUserID + "　：　卸出荷伝票ワーク作成処理を開始します。");
        ps = invoker.getDataBase().prepareStatement("insertWkOroshiSyukaDenpyo");
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        intCnt = ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ワーク作成処理を終了します。登録件数は"+ intCnt +"件です。");
        ps.close();
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票明細ワーク作成処理を開始します。");
        ps = invoker.getDataBase().prepareStatement("insertWkOroshiSyukaMei");
        intI=1;
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        //#5270 Add X.Liu 2017.06.20 (S)
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        //#5270 Add X.Liu 2017.06.20 (E)
        intCnt = ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　卸出荷伝票明細ワーク作成処理を終了します。登録件数は"+ intCnt +"件です。");
        ps.close();
        
        //#5270 Add X.Liu 2017.06.19 (S)
        invoker.infoLog(strUserID + "　：　卸出荷伝票VAT取消ワーク作成処理を開始します。");
        ps = invoker.getDataBase().prepareStatement("insertWkOroshiSyukaDenpyoCancel");
        intCnt = ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　卸出荷伝票VAT取消ワーク作成処理を終了します。登録件数は"+ intCnt +"件です。");
        ps.close();
        //#5270 Add X.Liu 2017.06.19 (E)

        //#5752 Add X.Liu 2017.08.08 (S)
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー構成商品ワーク作成処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(getInsertSql1());
        intI = 1;
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        intCnt = ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー構成商品ワーク作成処理を終了します。登録件数は"+ intCnt +"件です。");
        ps.close();
        
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー商品ワーク作成処理を開始します。");
        ps = invoker.getDataBase().prepareStatement(getInsertSql2());
        intI = 1;
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        intCnt = ps.executeUpdate();
        invoker.infoLog(strUserID + "　：　卸出荷伝票ハンパー商品ワーク作成処理を終了します。登録件数は"+ intCnt +"件です。");
        ps.close();
        //#5752 Add X.Liu 2017.08.08 (E)
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票更新処理を開始します。");
        ps = invoker.getDataBase().prepareStatement("updateDTOroshiSyukaDenpyo");
        intI=1;
        ps.setString(intI++, BATCH_ID);
        ps.setString(intI++, dbServerTime);
        intCnt = ps.executeUpdate();
        ps.close();
        invoker.infoLog(strUserID + "　：　卸出荷伝票更新処理を終了します。更新件数は"+intCnt+"件です。");
        
        
        invoker.infoLog(strUserID + "　：　卸出荷伝票抽出処理を終了します。");
    }

    //#5752 Add X.Liu 2017.08.08 (S)
    /**
     * 
     * @return InsertのSQLを獲得（卸出荷伝票ハンパー構成商品ワーク） 
     */
    private String getInsertSql1(){
        
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO WK_OROSHI_SYUKA_HAM_KO_SYO( ");
        sql.append("  COMP_CD");
        sql.append("  , SEQ_NB");
        sql.append("  , SEQ_EDA_RB");
        sql.append("  , KOSEI_RT");
        sql.append("  , HAMPER_KO_SYOHIN_CD");
        sql.append("  , HAMPER_KO_SYOHIN_NA");
        sql.append("  , BUNRUI1_CD");
        sql.append("  , HANBAI_TANI");
        sql.append("  , HANBAI_TANI_NA");
        sql.append("  , TEIKAN_KB");
        sql.append("  , OROSHI_BAITANKA_ZEINUKI_VL");
        sql.append("  , OROSHI_BAITANKA_ZEIKOMI_VL");
        sql.append("  , OROSHI_BAITANKA_ZEIGAKU_VL");
        sql.append("  , NOUHIN_SURYO_QT");
        sql.append("  , NOUHIN_JYURYO_QT");
        sql.append("  , BAIKA_ZEINUKI_VL");
        sql.append("  , BAIKA_ZEIKOMI_VL");
        sql.append("  , BAIKA_ZEIGAKU_VL");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("SELECT");
        sql.append("  DOSH.COMP_CD");
        sql.append("  , DOSH.SEQ_NB");
        sql.append("  , DOSH.SEQ_EDA_RB");
        sql.append("  , DOSH.KOSEI_RT");
        sql.append("  , DOSH.SYOHIN_CD");
        sql.append("  , DOSH.HINMEI_KANJI_NA");
        sql.append("  , DOSH.BUNRUI1_CD");
        sql.append("  , DOSH.HANBAI_TANI");
        sql.append("  , RN.KANJI_NA");
        sql.append("  , DOSH.TEIKAN_KB");
        sql.append("  , DOSH.OROSHI_BAITANKA_ZEINUKI_VL");
        sql.append("  , DOSH.OROSHI_BAITANKA_ZEIKOMI_VL");
        sql.append("  , DOSH.OROSHI_BAITANKA_ZEIGAKU_VL");
        sql.append("  , DOSH.NOUHIN_SURYO_QT");
        sql.append("  , DOSH.NOUHIN_JYURYO_QT");
        sql.append("  , DOSH.BAIKA_ZEINUKI_VL");
        sql.append("  , DOSH.BAIKA_ZEIKOMI_VL");
        sql.append("  , DOSH.BAIKA_ZEIGAKU_VL");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("FROM");
        sql.append("  DT_OROSHI_SYUKA_HAM DOSH ");
        sql.append("  LEFT JOIN R_NAMECTF RN ");
        sql.append("    ON RN.SYUBETU_NO_CD = '"  + MessageUtil.getMessage("3040", userLocal) + "' "); 
        sql.append("    AND TRIM(RN.CODE_CD) = TRIM(DOSH.HANBAI_TANI) ");
        sql.append("  INNER JOIN DT_OROSHI_SYUKA_DENPYO DOSD ");
        sql.append("    ON DOSH.COMP_CD = DOSD.COMP_CD ");
        sql.append("    AND DOSH.SEQ_NB = DOSD.SEQ_NB ");
        sql.append("  INNER JOIN DT_OROSHI_SYUKA_MEI DOSM ");
        sql.append("    ON DOSH.COMP_CD = DOSM.COMP_CD ");
        sql.append("    AND DOSH.SEQ_NB = DOSM.SEQ_NB ");
        sql.append("    AND DOSH.SEQ_EDA_RB = DOSM.SEQ_EDA_RB ");
        //#5754 Add X.Liu 2017.08.03 (S)
        sql.append("  LEFT JOIN DT_OROSHI_SYUKA_DENPYO DOSDM ");
        sql.append("    ON DOSD.COMP_CD = DOSDM.COMP_CD ");
        sql.append("    AND DOSD.MOTO_DENPYO_NO = DOSDM.DENPYO_NO ");
        //#5754 Add X.Liu 2017.08.03 (E)
        sql.append("WHERE");
        sql.append("  DOSD.SYORI_STATUS_KB = '1' ");
        sql.append("  AND ( ");
        //#5754 Mod X.Liu 2017.08.03 (S)
//        sql.append("    ( ");
//        sql.append("      NVL(DOSD.OROSHI_CANCEL_KB, '0') <> '1' ");
//        sql.append("      AND DOSD.DENPYO_PRINT_QT > 0");
//        sql.append("    ) ");
//        sql.append("    OR (NVL(DOSD.OROSHI_CANCEL_KB, '0') = '1')");
        sql.append("  DOSD.DENPYO_PRINT_QT > 0 ");
        sql.append("  OR (DOSD.DENPYO_PRINT_QT = 0 AND DOSD.LIST_FIRST_PRINT_DT IS NOT NULL) ");
        sql.append("  OR (DOSD.DENPYO_PRINT_QT = 0 AND DOSD.OROSHI_CANCEL_KB = '1' AND DOSDM.LIST_FIRST_PRINT_DT IS NOT NULL) ");
        //#5754 Mod X.Liu 2017.08.03 (E)
        sql.append("  ) ");
        sql.append("  AND DOSD.DELETE_FG = '0' ");
        sql.append("  AND DOSM.DELETE_FG = '0'");
        return sql.toString();
    }
    
    /**
     * 
     * @return InsertのSQLを獲得（卸出荷伝票ハンパー商品ワーク）
     */
    private String getInsertSql2(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT ");
        sql.append("INTO WK_OROSHI_SYUKA_HAM_SYO( ");
        sql.append("   COMP_CD");
        sql.append("  , SEQ_NB");
        sql.append("  , SEQ_EDA_RB");
        sql.append("  , HAMPER_SYOHIN_CD");
        sql.append("  , HAMPER_SYOHIN_NA");
        sql.append("  , TEIKAN_KB");
        sql.append("  , OROSHI_BAITANKA_ZEINUKI_VL");
        sql.append("  , OROSHI_BAITANKA_ZEIKOMI_VL");
        sql.append("  , OROSHI_BAITANKA_ZEIGAKU_VL");
        sql.append("  , NOUHIN_SURYO_QT");
        sql.append("  , NOUHIN_JYURYO_QT");
        sql.append("  , BAIKA_ZEINUKI_VL");
        sql.append("  , BAIKA_ZEIKOMI_VL");
        sql.append("  , BAIKA_ZEIGAKU_VL");
        sql.append("  , TENPO_CD");
        sql.append("  , TENPO_NA");
        sql.append("  , KEIJO_DT");
        sql.append("  , INSERT_USER_ID");
        sql.append("  , INSERT_TS");
        sql.append("  , UPDATE_USER_ID");
        sql.append("  , UPDATE_TS");
        sql.append(") ");
        sql.append("SELECT");
        sql.append("  DOSM.COMP_CD");
        sql.append("  , DOSM.SEQ_NB");
        sql.append("  , DOSM.SEQ_EDA_RB");
        sql.append("  , DOSM.SYOHIN_CD");
        sql.append("  , DOSM.HINMEI_KANJI_NA");
        sql.append("  , DOSM.TEIKAN_KB");
        sql.append("  , DOSM.OROSHI_BAITANKA_ZEINUKI_VL");
        sql.append("  , DOSM.OROSHI_BAITANKA_ZEIKOMI_VL");
        sql.append("  , DOSM.OROSHI_BAITANKA_ZEIGAKU_VL");
        sql.append("  , DOSM.NOUHIN_SURYO_QT");
        sql.append("  , DOSM.NOUHIN_JYURYO_QT");
        sql.append("  , DOSM.BAIKA_ZEINUKI_VL");
        sql.append("  , DOSM.BAIKA_ZEIKOMI_VL");
        sql.append("  , DOSM.BAIKA_ZEIGAKU_VL");
        sql.append("  , DOSD.SYUKA_TENPO_CD");
        sql.append("  , RT.KANJI_NA");
        sql.append("  , DOSD.SYUKA_DT");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("  , ? ");
        sql.append("FROM");
        sql.append("  DT_OROSHI_SYUKA_MEI DOSM ");
        sql.append("  INNER JOIN DT_OROSHI_SYUKA_DENPYO DOSD ");
        sql.append("    ON DOSM.COMP_CD = DOSD.COMP_CD ");
        sql.append("    AND DOSM.SEQ_NB = DOSD.SEQ_NB ");
        sql.append("  LEFT JOIN R_TENPO RT ");
        sql.append("    ON RT.TENPO_CD = DOSD.SYUKA_TENPO_CD ");
        //#5754 Add X.Liu 2017.08.03 (S)
        sql.append("  LEFT JOIN DT_OROSHI_SYUKA_DENPYO DOSDM ");
        sql.append("    ON DOSD.COMP_CD = DOSDM.COMP_CD ");
        sql.append("    AND DOSD.MOTO_DENPYO_NO = DOSDM.DENPYO_NO ");
        //#5754 Add X.Liu 2017.08.03 (E)
        sql.append("WHERE");
        sql.append("  DOSD.SYORI_STATUS_KB = '1' ");
        sql.append("  AND ( ");
        //#5754 Mod X.Liu 2017.08.03 (S)
//        sql.append("    ( ");
//        sql.append("      NVL(DOSD.OROSHI_CANCEL_KB, '0') <> '1' ");
//        sql.append("      AND DOSD.DENPYO_PRINT_QT > 0");
//        sql.append("    ) ");
//        sql.append("    OR (NVL(DOSD.OROSHI_CANCEL_KB, '0') = '1')");
        sql.append("  DOSD.DENPYO_PRINT_QT > 0 ");
        sql.append("  OR (DOSD.DENPYO_PRINT_QT = 0 AND DOSD.LIST_FIRST_PRINT_DT IS NOT NULL) ");
        sql.append("  OR (DOSD.DENPYO_PRINT_QT = 0 AND DOSD.OROSHI_CANCEL_KB = '1' AND DOSDM.LIST_FIRST_PRINT_DT IS NOT NULL) ");
        //#5754 Mod X.Liu 2017.08.03 (E)
        sql.append("  ) ");
        sql.append("  AND DOSD.DELETE_FG = '0' ");
        sql.append("  AND DOSM.DELETE_FG = '0' ");
        sql.append("  AND DOSM.HAMPER_SYOHIN_FG = '1'");

        return sql.toString();
    }
    //#5752 Add X.Liu 2017.08.08 (E)
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