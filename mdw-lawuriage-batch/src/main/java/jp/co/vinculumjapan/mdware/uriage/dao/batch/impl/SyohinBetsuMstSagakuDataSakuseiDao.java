package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.co.vinculumjapan.mdware.common.util.dictionary.FractionDigitDic;
import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriResorceKeyConstant;
import jp.co.vinculumjapan.mdware.uriage.constant.UriageCommonConstants;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 *
 * <p>タイトル: SyohinBetsuMstSagakuDataSakuseiDao.java クラス</p>
 * <p>説明　: 商品別マスタ差額データ作成処理</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2014.12.04) chou グローバル化対応 通貨対応
 *
 */
public class SyohinBetsuMstSagakuDataSakuseiDao implements DaoIf {

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();
    // システムコントロールよりダミーDPTコード取得
    private static final String DUMMY_BUNRUI1_CD = FiResorceUtility.getPropertie(UriResorceKeyConstant.DUMMY_BUNRUI1_CD);
    /** 値上下対象外ＤＰＴマップ */
    private static final Map NEJOGE_TAISYOUGAI_DPT = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.NEJOGE_TAISYOGAI_DPT);
    /** 値上下対象外クラスマップ */
    private static final Map NEJOGE_TAISYOUGAI_CLASS = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.NEJOGE_TAISYOGAI_CLASS);

    /** DPT代表ラインコード */
    private static final Map DPT_DAIHYO_LINE_CD = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.DPT_DAIHYO_LINE_CD);
    /** DPT代表クラスコード */
    private static final Map DPT_DAIHYO_CLASS_CD = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.DPT_DAIHYO_CLASS_CD);
    /** DPTその他ラインコード */
    private static final Map DPT_SONOTA_LINE_CD = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.DPT_SONOTA_LINE_CD);
    /** DPTその他クラスコード */
    private static final Map DPT_SONOTA_CLASS_CD = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.DPT_SONOTA_CLASS_CD);

    /** バッチ処理名 */
    private static final String DAO_NAME = "商品別マスタ差額データ作成処理";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "POS値上下ワーク";

    private ResorceUtil ru = ResorceUtil.getInstance();

    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;

        int insertCount = 0;
        try {
//            Map  NEJOGE_TAISYOUGAI_CLASS = FiResorceUtility.getPropertieMap(UriResorceKeyConstant.NEJOGE_TAISYOGAI_CLASS);
            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 単品精算データからPOS値上下ワークを登録する(マスタ差額)
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(マスタ差額)の追加を開始します。");

            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkNejogePosInsertSql());
            preparedStatementExIns.setString(1, UriageCommonConstants.DENPYO_SYUBETSU_BAIHEN);
            preparedStatementExIns.setString(2, UriageCommonConstants.RIYU_KB_NEAGE);
            preparedStatementExIns.setString(3, UriageCommonConstants.RIYU_KB_NESAGE);
            preparedStatementExIns.setString(4, UriageCommonConstants.RIYU_SYOSAI_KB_POS_SV_BAIHEN_MINUS);
            preparedStatementExIns.setString(5, UriageCommonConstants.RIYU_SYOSAI_KB_POS_SV_BAIHEN_PLUS);
            preparedStatementExIns.setString(6, userId);
            preparedStatementExIns.setString(7, dbServerTime);
            preparedStatementExIns.setString(8, userId);
            preparedStatementExIns.setString(9, dbServerTime);
            preparedStatementExIns.setString(10, COMP_CD);
            preparedStatementExIns.setString(11, BATCH_DT);
            preparedStatementExIns.setString(12, UriageCommonConstants.TANAOROSHI_GENKA_KB_BAIKA);
            preparedStatementExIns.setString(13, UriageCommonConstants.SHIIRE_HANBAI_KB_SHIIREHANBAI);
            preparedStatementExIns.setString(14, UriageCommonConstants.TEIKAN_KB_FUTEIKAN);
            preparedStatementExIns.setString(15, DUMMY_BUNRUI1_CD);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "(マスタ差額)を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + INS_TABLE_NAME + "(マスタ差額)の追加を終了します。");


        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 単品精算データからPOS値上下ワークを登録するSQLを取得する
     *
     * @return POS値上下ワーク登録SQL
     */
    private String getWkNejogePosInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND*/ INTO WK_NEJOGE_POS( ");
        sql.append("     COMP_CD ");
        sql.append("    ,DENPYO_KB ");
        sql.append("    ,KANRI_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,BUNRUI1_CD ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,BUNRUI2_CD ");
        sql.append("    ,BUNRUI5_CD ");
        sql.append("    ,SYOHIN_HINMEI_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_NA ");
        sql.append("    ,KIKAKU_KANJI_2_NA ");
        sql.append("    ,HINMEI_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_NA ");
        sql.append("    ,KIKAKU_KANA_2_NA ");
        sql.append("    ,RIYU_KB ");
        sql.append("    ,RIYU_SYOSAI_KB ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,SURYO_QT ");
        sql.append("    ,SHIIRE_SURYO_QT ");
        sql.append("    ,BAITANKA_OLD_VL ");
        sql.append("    ,BAITANKA_NEW_VL ");
        sql.append("    ,M_BAITANKA_VL ");
        sql.append("    ,BAIKA_ZEINUKI_VL ");
        sql.append("    ,BAIKA_ZEIKOMI_VL ");
        sql.append("    ,BAIKA_ZEIGAKU_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS) ");
        sql.append("SELECT ");
        sql.append("     DTS.COMP_CD ");
        sql.append("    ,? ");
        sql.append("    ,DTS.KEIJO_DT ");
        sql.append("    ,DTS.TENPO_CD ");
        sql.append("    ,DTS.BUNRUI1_CD ");
        sql.append("    ,DTS.SYOHIN_CD ");
        sql.append("    ,DTS.BUNRUI2_CD ");
        sql.append("    ,DTS.BUNRUI5_CD ");
        sql.append("    ,DTS.HINMEI_KANJI_NA ");
        sql.append("    ,DTS.KIKAKU_KANJI_NA ");
        sql.append("    ,DTS.KIKAKU_KANJI_2_NA ");
        sql.append("    ,DTS.HINMEI_KANA_NA ");
        sql.append("    ,DTS.KIKAKU_KANA_NA ");
        sql.append("    ,DTS.KIKAKU_KANA_2_NA ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DTS.BAIHEN_VL < 0 THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS RIYU_KB ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DTS.BAIHEN_VL < 0 THEN ? ");
        sql.append("        ELSE ? ");
        sql.append("     END AS RIYU_SYOSAI_KB ");
        sql.append("    ,DTS.ZEI_KB ");
        sql.append("    ,DTS.TAX_RT ");
        sql.append("    ,DTS.SURYO_QT ");
        sql.append("    ,DTS.SURYO_QT AS SHIIRE_SURYO_QT ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DTS.BAIHEN_VL > 0 THEN ABS(TRUNC(DTS.BAIHEN_VL / DTS.SURYO_QT, 0)) ");
        sql.append("        ELSE 0 ");
        sql.append("     END AS BAITANKA_OLD_VL ");
        sql.append("    ,CASE ");
        sql.append("        WHEN DTS.BAIHEN_VL > 0 THEN 0 ");
        sql.append("        ELSE ABS(TRUNC(DTS.BAIHEN_VL / DTS.SURYO_QT, 0)) ");
        sql.append("     END AS BAITANKA_NEW_VL ");
        sql.append("    ,DTS.MST_BAITANKA_VL ");
        //2014/12/04 chou グローバル化対応 通貨対応 MOD START
        //sql.append("    ,ABS(GET_ZEINUKI( ");
        //sql.append("        DTS.BAIHEN_VL, DTS.ZEI_KB, DTS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEINUKI_VL ");
        //sql.append("    ,ABS(GET_ZEIKOMI( ");
        //sql.append("        DTS.BAIHEN_VL, DTS.ZEI_KB, DTS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEIKOMI_VL ");
        //sql.append("    ,ABS(GET_ZEIGAKU( ");
        //sql.append("        DTS.BAIHEN_VL, DTS.ZEI_KB, DTS.TAX_RT ");
        //sql.append("     )) AS BAIKA_ZEIGAKU_VL ");

        sql.append("    ,ABS(GET_ZEINUKI_MARUME( ");
        sql.append("        DTS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DTS.ZEI_KB ");
        sql.append("        ,DTS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEINUKI_VL ");

        sql.append("    ,ABS(GET_ZEIKOMI_MARUME( ");
        sql.append("        DTS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DTS.ZEI_KB ");
        sql.append("        ,DTS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEIKOMI_VL ");

        sql.append("    ,ABS(GET_ZEIGAKU_MARUME( ");
        sql.append("        DTS.BAIHEN_VL ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_LEN.getCode()));
        sql.append("        ,DTS.ZEI_KB ");
        sql.append("        ,DTS.TAX_RT ");
        sql.append("        ," + ru.getPropertie(FractionDigitDic.FRACTION_SELL_UNIT_MODE.getCode()));
        sql.append("     )) AS BAIKA_ZEIGAKU_VL ");
        //2014/12/04 chou グローバル化対応 通貨対応 MOD END
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("FROM ");
        sql.append("    ( ");
        sql.append("    SELECT ");
        sql.append("         DTS.COMP_CD ");
        sql.append("        ,DTS.KEIJO_DT ");
        sql.append("        ,DTS.TENPO_CD ");
        sql.append("        ,DTS.BUNRUI1_CD ");
        sql.append("        ,DTS.TANPIN_CD AS SYOHIN_CD ");
        sql.append("        ,DTS.BUNRUI2_CD ");
        sql.append("        ,DTS.BUNRUI5_CD ");
        sql.append("        ,DTS.HINMEI_KANJI_NA ");
        sql.append("        ,DTS.KIKAKU_KANJI_NA ");
        sql.append("        ,DTS.KIKAKU_KANJI_2_NA ");
        sql.append("        ,DTS.HINMEI_KANA_NA ");
        sql.append("        ,DTS.KIKAKU_KANA_NA ");
        sql.append("        ,DTS.KIKAKU_KANA_2_NA ");
        sql.append("        ,DTS.ZEI_KB ");
        sql.append("        ,DTS.TAX_RT ");
        sql.append("        ,DTS.MST_BAITANKA_VL ");
        sql.append("        ,DTS.MST_BAITANKA_VL * DTS.URIAGE_SOURI_QT ");
        sql.append("             - (DTS.URIAGE_SOURI_VL + DTS.NEBIKI_REGI_VL + DTS.NEBIKI_SC_VL) AS BAIHEN_VL ");
        sql.append("        ,ABS(DTS.URIAGE_SOURI_QT) AS SURYO_QT ");
        sql.append("    FROM ");
        sql.append("        DT_TANPIN_SEISAN DTS ");
        sql.append("    WHERE ");
        sql.append("        DTS.COMP_CD                                                              = ?    AND ");
        sql.append("        DTS.KEIJO_DT                                                             = ?    AND ");
        sql.append("        DTS.MST_BAITANKA_VL * DTS.URIAGE_SOURI_QT ");
        sql.append("             - (DTS.URIAGE_SOURI_VL + DTS.NEBIKI_REGI_VL + DTS.NEBIKI_SC_VL)    <> 0    AND ");
        if (!NEJOGE_TAISYOUGAI_DPT.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = NEJOGE_TAISYOUGAI_DPT.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }

            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI1_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        if (!DPT_DAIHYO_LINE_CD.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = DPT_DAIHYO_LINE_CD.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }

            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI2_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        if (!DPT_SONOTA_LINE_CD.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = DPT_SONOTA_LINE_CD.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }
            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI2_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        if (!NEJOGE_TAISYOUGAI_CLASS.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = NEJOGE_TAISYOUGAI_CLASS.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }

            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI5_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        if (!DPT_DAIHYO_CLASS_CD.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = DPT_DAIHYO_CLASS_CD.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }
            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI5_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        if (!DPT_SONOTA_CLASS_CD.isEmpty()) {
            StringBuilder buff = new StringBuilder();

            for(Iterator it = DPT_SONOTA_CLASS_CD.entrySet().iterator(); it.hasNext();){
                Map.Entry nejogeTaisyougai = (Map.Entry)it.next();
                if (!StringUtils.isEmpty(((String) nejogeTaisyougai.getKey()).trim())) {
                    buff.append(",'" + nejogeTaisyougai.getValue() + "'");
                }
            }
            if (buff.length() > 0) {
                sql.append("        TRIM(DTS.BUNRUI5_CD) NOT IN ( ");
                sql.append(buff.substring(1));
                sql.append("        ) AND ");
            }
        }
        sql.append("        DTS.TANAOROSHI_GENKA_KB                                                  = ?    AND ");
        sql.append("        DTS.SHIIRE_HANBAI_KB                                                     = ?    AND ");
        sql.append("        DTS.TEIKAN_KB                                                           <> ?    AND ");
        sql.append("        TRIM(DTS.BUNRUI1_CD)                                                    <> ? ");
        sql.append("    ) DTS ");
        sql.append("WHERE ");
        sql.append("    DTS.SURYO_QT <> 0 ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     *
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     *
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            DaoIf dao = new SyohinBetsuMstSagakuDataSakuseiDao();
            new StandAloneDaoInvoker("mm").InvokeDao(dao);
            System.out.println(dao.getOutputObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoTimeOutException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
