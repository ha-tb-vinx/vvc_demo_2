package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>タイトル: VatSkipRirekiCreateDao クラス</p>
 * <p>説明:VATスキップ履歴作成（Fレコード）</p>
 * <p>著作権: Copyright 2016</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.11.30) J.Endo FIVI対応(#2945)
 * @Version 1.01 (2017.02.14) J.Endo FIVI対応(#3950)
 * @Version 1.02 (2017.02.20) J.Endo FIVI対応(#4097)
 * @Version 1.03 (2017.03.02) X.Liu  FIVI対応(#4022)
 * @Version 1.04 (2017.04.05) X.Liu  FIVI対応(#4526)
 * @Version 1.05 (2017.04.18) X.Liu  FIVI対応(#4768)
 * @Version 1.06 (2017.05.25) X.Liu  FIVI対応(#5149)
 * @Version 1.07 (2017.07.10) X.Liu  FIVI対応(#5580)
 * @Version 1.08 (2017.07.26) X.Liu  FIVI対応(#5668)
 * @see なし
 */
public class VatSkipRirekiCreateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "VATスキップ履歴作成（Fレコード）";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // バッチID
    private static final String BATCH_ID = "URIB012830";

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    // ＤＢシステム日時
    String DBServerTime = FiResorceUtility.getDBServerTime();

    /**
     * VATスキップ履歴作成（Fレコード）
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExInsert = null;
        //#5580 Del X.Liu 2017.07.10 (S)
//        //#4022 Add X.Liu 2017.03.01(S) 
//        
//        ResultSet rs = null;
//        PreparedStatementEx preparedStatementExSelect = null;
//        // Ａレコード返品情報カットデータ取得SQL
//        preparedStatementExSelect = invoker.getDataBase().prepareStatement(getArecChkWkSql());
//        rs = preparedStatementExSelect.executeQuery();
//        List<ArecChkWk> list = new ArrayList<ArecChkWk>();
//        ArecChkWk data = null;
//        Map<String,String> map = new HashMap<String,String>();
//        if(rs!=null){
//            while(rs.next()){
//                data = new ArecChkWk();
//                data.setCommand(rs.getString("COMMAND"));
//                data.setTempkey(rs.getString("TEMPKEY"));
//                data.setAtype(rs.getString("ATYPE"));
//                data.setEigyodt(rs.getString("EIGYODT"));
//                list.add(data);
//            }
//        }
//        //タイプ判断開始
//        if(list.size()>0){
//            String tempKey = "";
//            String command = "";
//            String atype = "";
//            String eigyoDt = "";
//            String command_0043 = "0043";
//            String command_0044 = "0044";
//            //# 4526 Add X.Liu 2017.04.05 (S)
//            String command_0045 = "0045";
//            //# 4526 Add X.Liu 2017.04.05 (E)
//            String henpinType = "0301";
//            String jikasyouhiType = "1011";
//            String jikasyouhihenType = "1311";
//            String mapVal = "";
//            for(int i = 0;i<list.size();i++){
//                tempKey = list.get(i).getTempkey();
//                command = list.get(i).getCommand();
//                atype = list.get(i).getAtype();
//                eigyoDt = list.get(i).getEigyodt();
//                
//                mapVal =map.get(tempKey+eigyoDt); 
//                if(mapVal==null || "".equals(mapVal)){
//                    //該当「店舗コード＋レジ＋レシート+営業日」が初回
//                	//#4526 Mod X.Liu 2017.04.05 (S)
////                	if(command_0044.equals(command) && henpinType.equals(atype)){
//                    if((command_0044.equals(command) || command_0045.equals(command))&& henpinType.equals(atype)){
//                    //#4526 Mod X.Liu 2017.04.05 (E)
//                        //返品
//                        map.put(tempKey+eigyoDt, "5");
//                    }else if(command_0043.equals(command) && jikasyouhiType.equals(atype)){
//                        //自家消費
//                        map.put(tempKey+eigyoDt, "6");
//                    //#4526 Mod X.Liu 2017.04.05 (S)
////                    }else if(command_0044.equals(command) && jikasyouhihenType.equals(atype)){
//                    }else if((command_0044.equals(command) || command_0045.equals(command))&& jikasyouhihenType.equals(atype)){
//                    //#4526 Mod X.Liu 2017.04.05 (E)
//                        //自家消費返品
//                        map.put(tempKey+eigyoDt, "7");
//                    }else{
//                        //販売
//                        map.put(tempKey+eigyoDt, "3");
//                    }
//                }else{
//                    //自家消費以外が対象
//                    if(!"6".equals(mapVal)){
//                        //該当「店舗コード＋レジ＋レシート+営業日」が初回ではない
//                    	//#4526 Mod X.Liu 2017.04.05 (S)
////                    	if(command_0044.equals(command) && henpinType.equals(atype) && "5".equals(mapVal)){
//                        if((command_0044.equals(command) || command_0045.equals(command))&& henpinType.equals(atype) && "5".equals(mapVal)){
//                        //#4526 Mod X.Liu 2017.04.05 (E)
//                            //返品
//                            map.put(tempKey+eigyoDt, "5");
//                        //#4526 Mod X.Liu 2017.04.05 (S)
////                        }else if(command_0044.equals(command) && jikasyouhihenType.equals(atype) && "7".equals(mapVal)){
//                        }else if((command_0044.equals(command) || command_0045.equals(command) )&& jikasyouhihenType.equals(atype) && "7".equals(mapVal)){
//                        //#4526 Mod X.Liu 2017.04.05 (E)
//                            //自家消費返品
//                            map.put(tempKey+eigyoDt, "7");
//                        }else if(command_0043.equals(command) && jikasyouhiType.equals(atype)){
//                            //自家消費
//                            map.put(tempKey+eigyoDt, "6");
//                        }else{
//                            //販売
//                            map.put(tempKey+eigyoDt, "3");
//                        }
//                    }
//                }
//            }
//        }
//        //タイプ判断終了
//      //#4022 Add X.Liu 2017.03.01(E)
        //#5580 Del X.Liu 2017.07.10 (E)
        int insertCount = 0;
        try {
            // VATスキップ履歴作成（Fレコード）
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getVatSkipRirekiCreateSql());
            insertCount = preparedStatementExInsert.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserID + "　：　" + insertCount + "件のVATスキップ履歴データを追加しました。");
            //#5580 Del X.Liu 2017.07.10 (S)
//            //#4022 Add X.Liu 2017.03.01(S)
//            invoker.getDataBase().commit();
//            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
//
//            while (entries.hasNext()) {
//
//                Map.Entry<String, String> entry = entries.next();
//                preparedStatementExInsert = invoker.getDataBase().prepareStatement(getUpdateVatSkipRirekiSql());
//                //VAT_PRINT_KB
//                preparedStatementExInsert.setString(1, entry.getValue());
//                //店舗コード
//                preparedStatementExInsert.setString(2, entry.getKey().substring(0, 6));
//                //レジ
//                preparedStatementExInsert.setString(3, entry.getKey().substring(6, 9));
//                //レシート
//                preparedStatementExInsert.setString(4, entry.getKey().substring(9,15));
//                //営業日
//                preparedStatementExInsert.setString(5, entry.getKey().substring(15));
//                //ユーザID
//                preparedStatementExInsert.setString(6, BATCH_ID);
//                //タイム
//                preparedStatementExInsert.setString(7, DBServerTime);
//                insertCount = preparedStatementExInsert.executeUpdate();
//                
//                invoker.getDataBase().commit();
//            }
//            //#4022 Add X.Liu 2017.03.01(E)
            //#5580 Del X.Liu 2017.07.10 (E)
        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExInsert != null) {
                    preparedStatementExInsert.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * VATスキップ履歴作成（Fレコード）SQLを取得する
     *
     * @return VATスキップ履歴作成（Fレコード）SQL
     */
    private String getVatSkipRirekiCreateSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_URIAGE_INVOICE_H_RIREKI ( ");
        sql.append("    RIREKI_TS ");
        sql.append("   ,RIREKI_INSERT_USER_ID ");
        sql.append("   ,RIREKI_INSERT_TS ");
        sql.append("   ,RIREKI_KB ");
        sql.append("   ,COMMAND_CD ");
        sql.append("   ,TENPO_CD ");
        sql.append("   ,REGI_RB ");
        sql.append("   ,TERMINAL_RB ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,SALES_TS ");
        sql.append("   ,ZEINUKI_VL ");
        sql.append("   ,TAX_VL ");
        sql.append("   ,ZEIKOMI_VL ");
        sql.append("   ,MEMBER_ID ");
        sql.append("   ,INVOICE_NB ");
        sql.append("   ,INVOICE_KB ");
        sql.append("   ,INVOICE_RB ");
        sql.append("   ,OLD_TENPO_CD ");
        sql.append("   ,TENPO_NA ");
        sql.append("   ,CASHIER_CD ");
        sql.append("   ,USER_NA ");
        sql.append("   ,SHIHARAI_SYUBETSU_CD ");
        sql.append("   ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   ,HAKOU_DT ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("   ,DELETE_FG ");
        sql.append("   ,KOJIN_NA ");
        sql.append("   ,KONYU_SYA ");
        sql.append("   ,KAISYA_ZEI_CD ");
        sql.append("   ,JYUSYO ");
        sql.append("   ,SHIHARAI_HOUHOU ");
        sql.append("   ,KOZA_NO ");
        sql.append("   ,ZEINUKI_TOT_16IKOU_VL ");
        sql.append("   ,RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("   ,VAT_5_VL ");
        sql.append("   ,VAT_10_VL ");
        sql.append("   ,HIKAZEI_TOT_VL ");
        sql.append("   ,ZEIKOMI_TOT_0_VL ");
        sql.append("   ,ZEIKOMI_TOT_5_VL ");
        sql.append("   ,ZEIKOMI_TOT_10_VL ");
        sql.append("   ,VAT_PRINT_KB ");
        sql.append("   ,UPDATE_FG ");
        sql.append("   ,CREDIT_NA ");
        sql.append("   ,INVOICE_AUTO_NB ");
        sql.append("   ,INVOICE_AUTO_KB ");
        sql.append("   ,INVOICE_AUTO_RB ");
        sql.append("   ,VAT_AUTO_DT ");
        sql.append("   ,SIN_INV_SKIP_KIND ");
        sql.append("   ,SIN_INV_SKIP_REASON ");
        sql.append("   ,SEQ_NB ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("   ,RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("   ,RECEIPT_SUB_NO ");
        sql.append("   ,MIHAKO_KB ");
        //#4768 Add X.Liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("   ,ZEIMU_MEISAI_QT  ");
        sql.append("   ,ZEIMU_ZEINUKI_VL ");
        sql.append("   ,ZEIMU_ZEIKOMI_VL ");
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,VAT_PRINT_USER_ID ");
        sql.append("   ,VAT_PRINT_USER_NA ");
        sql.append("   ,VAT_CANCEL_USER_ID ");
        sql.append("   ,VAT_CANCEL_USER_NA ");
        //#5580 Add X.Liu 2017.07.10 (E)
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("   ,BETSU_SYS_DATA_KB ");
        sql.append("   ,BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    TO_CHAR(SYSTIMESTAMP,'yyyymmddhh24missff3') ");
        sql.append("   ,'" + BATCH_ID + "' ");
        sql.append("   ,'" + DBServerTime + "' ");
        sql.append("   ,'30' ");
        sql.append("   ,DPIS.COMMAND ");
        sql.append("   ,LPAD(DPIS.STORE,6,'0') ");
        sql.append("   ,DPIS.POS ");
        sql.append("   ,DPIS.TRANS_NO ");
        sql.append("   ,SUBSTR(DPIS.EIGYO_DT,1,8) ");
        sql.append("   ,NULL ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,NULL ");
        sql.append("   ,DPIS.SNI_INV_FORM ");
        sql.append("   ,DPIS.SIN_INV_SERIAL ");
        sql.append("   ,DPIS.SIN_INV_NO ");
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,DPIS.CASHIER_ID ");
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,SUBSTR(DPIS.EIGYO_DT,1,8) ");
        sql.append("   ,'" + BATCH_ID + "' ");
        sql.append("   ,'" + DBServerTime + "' ");
        sql.append("   ,'" + BATCH_ID + "' ");
        sql.append("   ,'" + DBServerTime + "' ");
        sql.append("   ,'0' ");
        sql.append("   ,SUBSTR(DPIS.SNI_CUST_NAME,1,180) ");
        sql.append("   ,DPIS.SNI_CUST_COMPANY ");
        sql.append("   ,SUBSTR(DPIS.SNI_CUST_TAX_CODE,1,10) ");
        sql.append("   ,SUBSTR(DPIS.SNI_CUST_ADDRS,1,270) ");
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        //#4022 Mod X.Liu 2017.03.01(S)
        //2017.02.14 J.Endo FIVI対応(#3950) MOD(S)
        //sql.append("   ,NULL ");
//        sql.append("   ,CASE ");
//        sql.append("        WHEN DPIS.COMMAND = '0044' AND WPA.ATYPE = '0301' THEN '5' ");
//        sql.append("        WHEN DPIS.COMMAND = '0043' AND WPA.ATYPE = '1011' THEN '6' ");
//        sql.append("        WHEN DPIS.COMMAND = '0044' AND WPA.ATYPE = '1311' THEN '7' ");
//        sql.append("                                                          ELSE '3' ");
//        sql.append("    END ");
        //2017.02.14 J.Endo FIVI対応(#3950) MOD(E)
        //#5580 Mod X.Liu 2017.07.10 (S)
//        sql.append("   ,'3' ");
        //#5668 Mod X.Liu 2017.07.26 (S)
//        sql.append("   ,DPAVI.VAT_PRINT_KB ");
        sql.append("   , CASE WHEN DPIS.SIN_INV_SKIP_KIND = '1' THEN '9' ");
        sql.append("    ELSE 'V' END AS VAT_PRINT_KB ");
        //#5668 Mod X.Liu 2017.07.26 (E)
        //#5580 Mod X.Liu 2017.07.10 (E)
        //#4022 Mod X.Liu 2017.03.01(E)
        sql.append("   ,NULL ");
        sql.append("   ,NULL ");
        sql.append("   ,DPIS.SNI_INV_FORM2 ");
        sql.append("   ,DPIS.SIN_INV_SERIAL2 ");
        sql.append("   ,DPIS.SIN_INV_NO2 ");
        sql.append("   ,SUBSTR(DPIS.EIGYO_DT,1,8) ");
        sql.append("   ,DPIS.SIN_INV_SKIP_KIND ");
        sql.append("   ,DPIS.SIN_INV_SKIP_REASON ");
        sql.append("   ,ROWNUM ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(S)
        sql.append("   ,DPIS.STORE || DPIS.POS || DPIS.TRANS_NO AS RECEIPT_NO ");
        //2017.02.20 J.Endo FIVI対応(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("   ,0 ");
        sql.append("   ,'0' ");
        //#4768 Add X.Liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        sql.append("   ,0 ");
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   ,CASE WHEN DPIS.SIN_INV_SKIP_KIND ='1' THEN DPIS.SNI_INV_ISSUED_CASHERID ELSE NULL END VAT_PRINT_USER_ID ");
        sql.append("   ,CASE WHEN DPIS.SIN_INV_SKIP_KIND ='1' THEN RPU1.USER_NA ELSE NULL END VAT_PRINT_USER_NA ");
        sql.append("   ,CASE WHEN DPIS.SIN_INV_SKIP_KIND ='1' THEN DPIS.CASHIER_ID ELSE NULL END VAT_CANCEL_USER_ID ");
        sql.append("   ,CASE WHEN DPIS.SIN_INV_SKIP_KIND ='1' THEN RPU2.USER_NA ELSE NULL END VAT_CANCEL_USER_NA ");
        //#5580 Add X.Liu 2017.07.10 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("   ,DPAVI.VAT_PRINT_KB AS BETSU_SYS_DATA_KB");
        sql.append("   ,CASE WHEN DPIS.SIN_INV_SKIP_KIND = '1' THEN '1' ");
        sql.append("    ELSE 'V' END AS BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("FROM ");
        sql.append("    DT_POS_INVOICE_SKIP DPIS ");
        //2017.02.14 J.Endo FIVI対応(#3950) ADD(S)
        sql.append("    LEFT OUTER JOIN ");
        //#5580 Del X.Liu 2017.07.10 (S)
//        sql.append("       (SELECT ");
//        sql.append("            COMMAND ");
//        sql.append("           ,STORE ");
//        sql.append("           ,POS ");
//        sql.append("           ,TRANS_NO ");
//        sql.append("           ,EIGYO_DT ");
//        sql.append("           ,CASHIER_ID ");
//        sql.append("           ,MIN(ATYPE) AS ATYPE ");
//        sql.append("           ,DATA_SAKUSEI_DT ");
//        sql.append("        FROM ");
//        sql.append("            WK_POS_A_ITEM ");
//        sql.append("        WHERE ");
//        sql.append("            DATA_SAKUSEI_DT = '" + BATCH_DT + "' AND ");
//        sql.append("            ERR_KB = '0' ");
//        sql.append("        GROUP BY ");
//        sql.append("            COMMAND ");
//        sql.append("           ,STORE ");
//        sql.append("           ,POS ");
//        sql.append("           ,TRANS_NO ");
//        sql.append("           ,EIGYO_DT ");
//        sql.append("           ,CASHIER_ID ");
//        sql.append("           ,DATA_SAKUSEI_DT ");
//        sql.append("        ) WPA ");
//        sql.append("    ON  WPA.COMMAND = DPIS.COMMAND ");
//        sql.append("    AND WPA.STORE = DPIS.STORE ");
//        sql.append("    AND WPA.POS = DPIS.POS ");
//        sql.append("    AND WPA.TRANS_NO = DPIS.TRANS_NO ");
//        sql.append("    AND SUBSTR(WPA.EIGYO_DT,1,8) = SUBSTR(DPIS.EIGYO_DT,1,8) ");
//        sql.append("    AND WPA.CASHIER_ID = DPIS.CASHIER_ID ");
//        sql.append("    AND WPA.DATA_SAKUSEI_DT = DPIS.DATA_SAKUSEI_DT ");
        //#5580 Del X.Liu 2017.07.10 (E)
        //2017.02.14 J.Endo FIVI対応(#3950) ADD(E)
        //#5580 Add X.Liu 2017.07.10 (S)
        sql.append("   DT_POS_A_VAT_INF DPAVI");
        sql.append("      ON  DPAVI.COMMAND = DPIS.COMMAND  ");
        sql.append("      AND DPAVI.STORE = DPIS.STORE ");
        sql.append("      AND DPAVI.POS = DPIS.POS ");
        sql.append("      AND DPAVI.TRANS_NO = DPIS.TRANS_NO ");
        sql.append("      AND SUBSTR(DPAVI.EIGYO_DT,1,8) = SUBSTR(DPIS.EIGYO_DT,1,8) ");
        sql.append("   LEFT OUTER JOIN ");
        sql.append("   R_PORTAL_USER RPU1 ");
        sql.append("      ON  TRIM(RPU1.USER_ID) = TRIM(DPIS.SNI_INV_ISSUED_CASHERID) ");
        sql.append("   LEFT OUTER JOIN ");
        sql.append("   R_PORTAL_USER RPU2 ");
        sql.append("   ON  TRIM(RPU2.USER_ID) = TRIM(DPIS.CASHIER_ID) ");
        //#5580 Add X.Liu 2017.07.10 (E)
        sql.append("WHERE ");
        sql.append("    DPIS.DATA_SAKUSEI_DT = '" + BATCH_DT + "' ");

        return sql.toString();
    }
  //#5580 Del X.Liu 2017.07.10 (S)
//    //#4022 Add X.Liu 2017.03.01(S)
//    /**
//     * Aレコードチェックワークデータを取得する
//     *
//     * @return 取得SQL
//     * 
//     */
//    private String getArecChkWkSql( ) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT "); 
//        sql.append(" COMMAND, ");
//        sql.append(" '00'|| STORE||POS||TRANS_NO AS TEMPKEY, ");
//        sql.append(" ATYPE, ");
//        sql.append(" SUBSTR(EIGYO_DT, 1, 8) AS EIGYODT");
//        sql.append(" FROM WK_POS_A_ITEM ");
//        sql.append(" WHERE ");
//        sql.append(" ERR_KB = '0'");
//        sql.append(" AND DATA_SAKUSEI_DT = '" +BATCH_DT +"'");
//        sql.append(" ORDER BY TEMPKEY,ATYPE,EIGYODT");
//                 
//        return sql.toString();
//    }
//    private String getUpdateVatSkipRirekiSql(){
//        StringBuilder sql = new StringBuilder();
//        sql.append(" UPDATE DT_URIAGE_INVOICE_H_RIREKI "); 
//        sql.append(" SET VAT_PRINT_KB = ? ");
//        sql.append(" WHERE TENPO_CD = ? ");
//        sql.append(" AND REGI_RB = ? ");
//        sql.append(" AND TERMINAL_RB = ? ");
//        sql.append(" AND EIGYO_DT = ? ");
//        sql.append(" AND TRIM(RIREKI_INSERT_USER_ID) = ? ");
//        sql.append(" AND RIREKI_INSERT_TS = ? ");
//        return sql.toString();
//    }
//    class ArecChkWk{
//        private String command;
//        private String tempkey;
//        private String atype;
//        private String eigyodt;
//        public String getCommand() {
//            return command;
//        }
//        public void setCommand(String command) {
//            this.command = command;
//        }
//        public String getTempkey() {
//            return tempkey;
//        }
//        public void setTempkey(String tempkey) {
//            this.tempkey = tempkey;
//        }
//        public String getAtype() {
//            return atype;
//        }
//        public void setAtype(String atype) {
//            this.atype = atype;
//        }
//        public String getEigyodt() {
//            return eigyodt;
//        }
//        public void setEigyodt(String eigyodt) {
//            this.eigyodt = eigyodt;
//        }
//    }
//    //#4022 Add X.Liu 2017.03.01(E)
    //#5580 Del X.Liu 2017.07.10 (E)
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
            DaoIf dao = new VatInvoiceRegistDao();
            new StandAloneDaoInvoker("MM").InvokeDao(dao);
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
