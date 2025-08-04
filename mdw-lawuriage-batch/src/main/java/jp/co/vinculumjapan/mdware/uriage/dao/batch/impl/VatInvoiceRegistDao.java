package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;
import jp.co.vinculumjapan.swc.commons.resorces.ResorceUtil;

/**
 * <p>繧ｿ繧､繝医Ν: VatInvoiceRegistDao 繧ｯ繝ｩ繧ｹ</p>
 * <p>隱ｬ譏�:INVOICE諠�蝣ｱ邏ｯ遨�</p>
 * <p>闡嶺ｽ懈ｨｩ: Copyright 2016</p>
 * <p>莨夂､ｾ蜷�: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2016.02.02) TAM.NM FIVIMART蟇ｾ蠢�
 * @Version 1.01 (2016.06.10) k.Hyo FIVI蟇ｾ蠢�
 * @Version 1.02 (2016.07.21) k.Hyo FIVI蟇ｾ蠢�(#1707)
 *               (2016.07.22) Y.Itaki FIVI蟇ｾ蠢�(#1707)
 * @version 1.03 (2016.10.11) Y.Itaki FIVI蟇ｾ蠢�(#1816)
 * @version 1.04 (2016.11.10) J.Endo  FIVI蟇ｾ蠢�(#2313)
 * @version 1.05 (2016.12.02) T.Kamei  FIVI蟇ｾ蠢�(#3036)
 * @version 1.06 (2016.12.07) T.Kamei  FIVI蟇ｾ蠢�(#3208)
 * @version 1.07 (2017.01.05) J.Endo   FIVI蟇ｾ蠢�(#3547)
 * @version 1.08 (2017.01.11) M.Kawakami FIVI蟇ｾ蠢�(#3596)
 * @version 1.09 (2017.01.11) J.Endo     FIVI蟇ｾ蠢�(#3356)
 * @version 1.10 (2017.01.13) M.Kawakami FIVI蟇ｾ蠢�(#3621)
 * @version 1.11 (2017.01.16) J.Endo     FIVI蟇ｾ蠢�(#3502)
 * @version 1.12 (2017.01.23) J.Endo     FIVI蟇ｾ蠢�(#3548)
 * @version 1.13 (2017.01.30) J.Endo     FIVI蟇ｾ蠢�(#3807)
 * @version 1.14 (2017.02.08) J.Endo     FIVI蟇ｾ蠢�(#3932)
 * @Version 1.15 (2017.02.16) J.Endo     FIVI蟇ｾ蠢�(#4013)
 * @Version 1.16 (2017.02.17) J.Endo     FIVI蟇ｾ蠢�(#4097)
 * @Version 1.17 (2017.02.22) X.Liu      FIVI蟇ｾ蠢�(#4022)
 * @Version 1.18 (2017.03.23) X.Liu      FIVI蟇ｾ蠢�(#4387)
 * @Version 1.19 (2017.04.18) X.Liu      FIVI蟇ｾ蠢�(#4768)
 * @Version 1.20 (2017.05.25) X.Liu      FIVI蟇ｾ蠢�(#5149)
 * @Version 1.21 (2017.07.26) X.Liu      FIVI蟇ｾ蠢�(#5668)
 * @see 縺ｪ縺�
 */
public class VatInvoiceRegistDao implements DaoIf {

    // 繝舌ャ繝∝�ｦ逅�蜷�
    private static final String BATCH_NAME = "INVOICE諠�蝣ｱ邏ｯ遨�";
    String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

    // 繧ｷ繧ｹ繝�繝�繧ｳ繝ｳ繝医Ο繝ｼ繝ｫ繧医ｊ繝舌ャ繝∵律莉伜叙蠕�
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /** 蜑企勁SQL譁� */
    private static final String DEL_SQL_H = "TRUNCATE TABLE WK_URIAGE_INVOICE_KANRI_H";
    private static final String DEL_SQL_M = "TRUNCATE TABLE WK_URIAGE_INVOICE_KANRI_M";

    /**
     * 逋ｺ陦梧ｸ�VATINVOICE諠�蝣ｱ邏ｯ遨�
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // 繝ｦ繝ｼ繧ｶID(DB逋ｻ骭ｲ逕ｨ)
        String userId = invoker.getUserId();
        // 繝ｦ繝ｼ繧ｶ繝ｼID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // 繝ｭ繧ｰ蜃ｺ蜉�
        invoker.infoLog(strUserID + "縲��ｼ壹��蜃ｦ逅�繧帝幕蟋九＠縺ｾ縺吶��");

        // 繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ蛻晄悄蛹悶☆繧�
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDel = null;

        //#5668 Del X.Liu 2017.07.26 (S)
//        //#4022 Add X.Liu 2017.02.22 (S)
//        ResultSet rs = null;
//        PreparedStatementEx preparedStatementExSelect = null;
//        // �ｼ｡繝ｬ繧ｳ繝ｼ繝芽ｿ泌刀諠�蝣ｱ繧ｫ繝�繝医ョ繝ｼ繧ｿ蜿門ｾ祐QL
//        preparedStatementExSelect = invoker.getDataBase().prepareStatement(getArecHenpinJohoCutDataSql());
//        rs = preparedStatementExSelect.executeQuery();
//        List<ARecHenJohoCutData> list = new ArrayList<ARecHenJohoCutData>();
//        ARecHenJohoCutData data = null;
//        Map<String,String> map = new HashMap<String,String>();
//        if(rs!=null){
//            while(rs.next()){
//                data = new ARecHenJohoCutData();
//                data.setCommand(rs.getString("COMMAND"));
//                data.setTempkey(rs.getString("TEMPKEY"));
//                data.setAtype(rs.getString("ATYPE"));
//                data.setEigyodt(rs.getString("EIGYODT"));
//                list.add(data);
//            }
//        }
//        //繧ｿ繧､繝怜愛譁ｭ髢句ｧ�
//        if(list.size()>0){
//            String tempKey = "";
//            String command = "";
//            String atype = "";
//            String eigyoDt = "";
//            String command_0043 = "0043";
//            String command_0044 = "0044";
//          //#4387 X.Liu Add 2017.03.23 (S)
//            String command_0045 = "0045";
//          //#4387 X.Liu Add 2017.03.23 (E)
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
//                    //隧ｲ蠖薙�悟ｺ苓�励さ繝ｼ繝会ｼ九Ξ繧ｸ�ｼ九Ξ繧ｷ繝ｼ繝�+蝟ｶ讌ｭ譌･縲阪′蛻晏屓
//                	//#4387 X.Liu Mod 2017.03.23 (S)
////                	if(command_0044.equals(command) && henpinType.equals(atype)){
//                    if((command_0044.equals(command)||command_0045.equals(command) )&& henpinType.equals(atype)){
//                    //#4387 X.Liu Mod 2017.03.23 (E)
//                    	//霑泌刀
//                        map.put(tempKey+eigyoDt, "5");
//                    }else if(command_0043.equals(command) && jikasyouhiType.equals(atype)){
//                        //閾ｪ螳ｶ豸郁ｲｻ
//                        map.put(tempKey+eigyoDt, "6");
//                    //#4387 X.Liu Mod 2017.03.23 (S)
////                    }else if(command_0044.equals(command) && jikasyouhihenType.equals(atype)){
//                    }else if((command_0044.equals(command) || command_0045.equals(command)) && jikasyouhihenType.equals(atype)){
//                    //#4387 X.Liu Mod 2017.03.23 (E)
//                    	//閾ｪ螳ｶ豸郁ｲｻ霑泌刀
//                        map.put(tempKey+eigyoDt, "7");
//                    }else{
//                        //雋ｩ螢ｲ
//                        map.put(tempKey+eigyoDt, "0");
//                    }
//                }else{
//                    //閾ｪ螳ｶ豸郁ｲｻ莉･螟悶′蟇ｾ雎｡
//                    if(!"6".equals(mapVal)){
//                        //隧ｲ蠖薙�悟ｺ苓�励さ繝ｼ繝会ｼ九Ξ繧ｸ�ｼ九Ξ繧ｷ繝ｼ繝�+蝟ｶ讌ｭ譌･縲阪′蛻晏屓縺ｧ縺ｯ縺ｪ縺�
//                    	//#4387 X.Liu Mod 2017.03.23 (S)
////                    	if(command_0044.equals(command) && henpinType.equals(atype) && "5".equals(mapVal)){
//                        if((command_0044.equals(command)||command_0045.equals(command)) && henpinType.equals(atype) && "5".equals(mapVal)){
//                        //#4387 X.Liu Mod 2017.03.23 (E)
//                            //霑泌刀
//                            map.put(tempKey+eigyoDt, "5");
//                        //#4387 X.Liu Mod 2017.03.23 (S)
////                        }else if(command_0044.equals(command) && jikasyouhihenType.equals(atype) && "7".equals(mapVal)){
//                        }else if((command_0044.equals(command) ||command_0045.equals(command)) && jikasyouhihenType.equals(atype) && "7".equals(mapVal)){
//                        //#4387 X.Liu Mod 2017.03.23 (E)
//                        	//閾ｪ螳ｶ豸郁ｲｻ霑泌刀
//                            map.put(tempKey+eigyoDt, "7");
//                        }else if(command_0043.equals(command) && jikasyouhiType.equals(atype)){
//                            //閾ｪ螳ｶ豸郁ｲｻ
//                            map.put(tempKey+eigyoDt, "6");
//                        }else{
//                            //雋ｩ螢ｲ
//                            map.put(tempKey+eigyoDt, "0");
//                        }
//                    }
//                }
//            }
//        }
//        //繧ｿ繧､繝怜愛譁ｭ邨ゆｺ�
//        //#4022 Add X.Liu 2017.02.22 (E)
        //#5668 Del X.Liu 2017.07.26 (E)
        int insertCount = 0;
        try {
            //繧ｵ繝ｼ繝舌�ｼ譎る俣
            String dbServerTime = FiResorceUtility.getDBServerTime();

            // 繝ｯ繝ｼ繧ｯ繝�繝ｼ繝悶Ν縺ｮ繝�繝ｼ繧ｿ繧貞炎髯､縺吶ｋ
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL_H);
            preparedStatementDel.execute();
            preparedStatementDel = invoker.getDataBase().prepareStatement(DEL_SQL_M);
            preparedStatementDel.execute();

            //#5149 Add X.Liu 2017.05.25 (S)
            //螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkUriageInvoiceKanriMInsertSql());

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // 繝ｭ繧ｰ蜃ｺ蜉�
            invoker.infoLog(strUserID + "縲��ｼ壹��" + insertCount + "莉ｶ縺ｮ螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ繧定ｿｽ蜉�縺励∪縺励◆縲�");
            //#5149 Add X.Liu 2017.05.25 (E)
            // 螢ｲ荳蟹NVOICE邂｡逅�繝倥ャ繝�繝ｯ繝ｼ繧ｯ 逋ｻ骭ｲ
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkUriageInvoiceKanriHInsertSql());

            preparedStatementExIns.setString(1, userId);
            preparedStatementExIns.setString(2, dbServerTime);
            preparedStatementExIns.setString(3, userId);
            preparedStatementExIns.setString(4, dbServerTime);
            preparedStatementExIns.setString(5, BATCH_DT);

            insertCount = preparedStatementExIns.executeUpdate();

            // 繝ｭ繧ｰ蜃ｺ蜉�
            invoker.infoLog(strUserID + "縲��ｼ壹��" + insertCount + "莉ｶ縺ｮ螢ｲ荳蟹NVOICE邂｡逅�繝倥ャ繝�繝ｯ繝ｼ繧ｯ繧定ｿｽ蜉�縺励∪縺励◆縲�");

            //#5149 Del X.Liu 2017.05.25 (S)
//            //螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ
//            preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkUriageInvoiceKanriMInsertSql());
//
//            preparedStatementExIns.setString(1, userId);
//            preparedStatementExIns.setString(2, dbServerTime);
//            preparedStatementExIns.setString(3, userId);
//            preparedStatementExIns.setString(4, dbServerTime);
//            preparedStatementExIns.setString(5, BATCH_DT);
//
//            insertCount = preparedStatementExIns.executeUpdate();
//
//            // 繝ｭ繧ｰ蜃ｺ蜉�
//            invoker.infoLog(strUserID + "縲��ｼ壹��" + insertCount + "莉ｶ縺ｮ螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ繧定ｿｽ蜉�縺励∪縺励◆縲�");
            //#5149 Del X.Liu 2017.05.25 (E)
            // APPEND INSERT縺励◆蜀�螳ｹ遒ｺ螳壹☆繧句ｿ�隕√′縺ゅｋ縺ｮ縺ｧcommit繧定｡後≧
            invoker.getDataBase().commit();
            
            //#5668 Del X.Liu 2017.07.26 (S)
//            //#4022 X.Liu Add 2017.02.22 (S)
//            // 螢ｲ荳蟹NVOICE邂｡逅�繝倥ャ繝�繝ｯ繝ｼ繧ｯ 譖ｴ譁ｰ
//            
//
//            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
//
//            while (entries.hasNext()) {
//
//                Map.Entry<String, String> entry = entries.next();
//                preparedStatementExIns = invoker.getDataBase().prepareStatement(getWkUriageInvoiceKanriHUpdateSql());
//                //VAT_PRINT_KB
//                preparedStatementExIns.setString(1, entry.getValue());
//                //蠎苓�励さ繝ｼ繝�
//                preparedStatementExIns.setString(2, entry.getKey().substring(0, 6));
//                //繝ｬ繧ｸ
//                preparedStatementExIns.setString(3, entry.getKey().substring(6, 9));
//                //繝ｬ繧ｷ繝ｼ繝�
//                preparedStatementExIns.setString(4, entry.getKey().substring(9,15));
//                //蝟ｶ讌ｭ譌･
//                preparedStatementExIns.setString(5, entry.getKey().substring(15));
//                insertCount = preparedStatementExIns.executeUpdate();
//                invoker.getDataBase().commit();
//            }
//            //#4022 X.Liu Add 2017.02.22 (E)
            //#5668 Del X.Liu 2017.07.26 (E)

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }
                if (preparedStatementDel != null) {
                    preparedStatementDel.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Close繧ｨ繝ｩ繝ｼ");
            }
        }

        // 繝ｭ繧ｰ蜃ｺ蜉�
        invoker.infoLog(strUserID + "縲��ｼ壹��蜃ｦ逅�繧堤ｵゆｺ�縺励∪縺吶��");
    }



    /**
     * 螢ｲ荳蟹NVOICE邂｡逅�繝倥ャ繝�繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ縺吶ｋSQL繧貞叙蠕励☆繧�
     *
     * @return 螢ｲ荳蟹NVOICE邂｡逅�繝倥ャ繝�繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ
     */
    private String getWkUriageInvoiceKanriHInsertSql() {
        StringBuilder sql = new StringBuilder();

        //2016.10.11 Y.Itaki FIVI蟇ｾ蠢�(#1816) 窶ｻ菫ｮ豁｣驥上′螟壹＞縺溘ａ縲ヾQL繧呈嶌縺咲峩縺励※縺�縺ｾ縺吶��
        sql.append("INSERT /*+ APPEND */ INTO WK_URIAGE_INVOICE_KANRI_H( ");
        sql.append("    COMMAND_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_RB ");
        sql.append("    ,TERMINAL_RB ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,SALES_TS ");
        sql.append("    ,ZEINUKI_VL ");
        sql.append("    ,TAX_VL ");
        sql.append("    ,ZEIKOMI_VL ");
        sql.append("    ,MEMBER_ID ");
        sql.append("    ,INVOICE_NB ");
        sql.append("    ,INVOICE_KB ");
        sql.append("    ,INVOICE_RB ");
        sql.append("    ,OLD_TENPO_CD ");
        sql.append("    ,TENPO_NA ");
        sql.append("    ,CASHIER_CD ");
        sql.append("    ,USER_NA ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ,HAKOU_DT ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DELETE_FG ");
        sql.append("    ,KOJIN_NA ");
        sql.append("    ,KONYU_SYA ");
        sql.append("    ,KAISYA_ZEI_CD ");
        sql.append("    ,JYUSYO ");
        sql.append("    ,SHIHARAI_HOUHOU ");
        sql.append("    ,KOZA_NO ");
        sql.append("    ,ZEINUKI_TOT_16IKOU_VL ");
        sql.append("    ,RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("    ,VAT_5_VL ");
        sql.append("    ,VAT_10_VL ");
        sql.append("    ,HIKAZEI_TOT_VL ");
        sql.append("    ,ZEIKOMI_TOT_0_VL ");
        sql.append("    ,ZEIKOMI_TOT_5_VL ");
        sql.append("    ,ZEIKOMI_TOT_10_VL ");
        sql.append("    ,VAT_PRINT_KB ");
        sql.append("    ,UPDATE_FG ");
        sql.append("    ,CREDIT_NA ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(S)
        sql.append("    ,INVOICE_AUTO_NB ");
        sql.append("    ,INVOICE_AUTO_KB ");
        sql.append("    ,INVOICE_AUTO_RB ");
        sql.append("    ,VAT_AUTO_DT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(E)
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("    ,RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("    ,RECEIPT_SUB_NO ");
        sql.append("    ,MIHAKO_KB ");
        //#4768 Add X.Liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("    ,ZEIMU_MEISAI_QT  ");
        sql.append("    ,ZEIMU_ZEINUKI_VL ");
        sql.append("    ,ZEIMU_ZEIKOMI_VL ");
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("    ,BETSU_SYS_DATA_KB ");
        sql.append("    ,BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    POS_A.COMMAND ");
        sql.append("    ,POS_A.STORE ");
        sql.append("    ,POS_A.POS ");
        sql.append("    ,POS_A.TRANS_NO ");
        sql.append("    ,POS_A.EIGYO_DT ");
        sql.append("    ,POS_B.TORI_TIME ");
        sql.append("    ,POS_A.ZEINUKI_VL ");
        sql.append("    ,POS_A.TAX_VL ");
        sql.append("    ,POS_A.ZEIKOMI_VL ");
        sql.append("    ,POS_B.MEM_CARD ");
        sql.append("    ,null AS INVOICE_NB ");
        sql.append("    ,null AS INVOICE_KB ");
        sql.append("    ,null AS INVOICE_RB ");
        sql.append("    ,H_R_TENPO.OLD_TENPO_CD ");
        sql.append("    ,R_TENPO.KANJI_NA ");
        sql.append("    ,POS_A.CASHIER_ID ");
        sql.append("    ,R_PORTAL_USER.USER_NA ");
        sql.append("    ,POS_C.PYMT_TYPE ");
        sql.append("    ,POS_C.PYMT_TYPE2 ");
        sql.append("    ,null AS HAKOU_DT ");
        sql.append("    ,? AS INSERT_USER_ID ");
        sql.append("    ,? AS INSERT_TS ");
        sql.append("    ,? AS UPDATE_USER_ID ");
        sql.append("    ,? AS UPDATE_TS ");
        sql.append("    ,'0' AS DELETE_FG ");
        sql.append("    ,null AS KOJIN_NA ");
        sql.append("    ,null AS KONYU_SYA ");
        sql.append("    ,null AS KAISYA_ZEI_CD ");
        sql.append("    ,null AS JYUSYO ");
        sql.append("    ,null AS SHIHARAI_HOUHOU ");
        sql.append("    ,null AS KOZA_NO ");
        sql.append("    ,POS_A.ZEINUKI_TOT_16IKOU_VL ");
        sql.append("    ,POS_A.RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("    ,POS_A.VAT_5_VL ");
        sql.append("    ,POS_A.VAT_10_VL ");
        sql.append("    ,'0' AS HIKAZEI_TOT_VL ");
        sql.append("    ,POS_A.ZEIKOMI_TOT_0_VL ");
        sql.append("    ,POS_A.ZEIKOMI_TOT_5_VL ");
        sql.append("    ,POS_A.ZEIKOMI_TOT_10_VL ");
        //#5668 Mod X.Liu 2017.07.26 (S)
//        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) MOD(S)
//        //sql.append("    ,'0' AS VAT_PRINT_KB ");
//        sql.append("    ,CASE POS_A.ATYPE ");
//        sql.append("         WHEN '0301' THEN '5' ");
//        //2017.02.08 J.Endo FIVI蟇ｾ蠢�(#3932) MOD(S)
//        //sql.append("         WHEN '1001' THEN '6' ");
//        //sql.append("         WHEN '1301' THEN '7' ");
//        sql.append("         WHEN '1011' THEN '6' ");
//        sql.append("         WHEN '1311' THEN '7' ");
//        //2017.02.08 J.Endo FIVI蟇ｾ蠢�(#3932) MOD(E)
//        sql.append("         ELSE '0' ");
//        sql.append("     END AS VAT_PRINT_KB ");
//        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) MOD(E)
        sql.append("    ,'0' AS VAT_PRINT_KB ");
        //#5668 Mod X.Liu 2017.07.26 (E)
        sql.append("    ,'0' AS UPDATE_FG ");
        sql.append("    ,R_PAYMENT.SHIHARAI_SYUBETSU_VN_NA AS CREDIT_NA ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(S)
        sql.append("    ,null AS INVOICE_AUTO_NB ");
        sql.append("    ,null AS INVOICE_AUTO_KB ");
        sql.append("    ,null AS INVOICE_AUTO_RB ");
        sql.append("    ,null AS VAT_AUTO_DT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(E)
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("    ,POS_A.RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        
        //#4768 Add X.liu 2017.04.18 (S)
        sql.append("    ,0 ");
        sql.append("    ,'0' ");
        //#4768 Add X.liu 2017.04.18 (E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append("    ,NVL(MC.CNT,0) ");
        sql.append("    ,POS_A.ZEINUKI_VL ");
        sql.append("    ,POS_A.ZEIKOMI_VL ");
        //#5149 Add X.Liu 2017.05.25 (E)
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append("    ,NVL(DPAVI.VAT_PRINT_KB,'3') AS BETSU_SYS_DATA_KB");
        sql.append("    ,'0' AS BETSU_SYS_VAT_PRINT_KB ");
        //#5668 Add X.Liu 2017.07.26 (E)
        sql.append("FROM ");
        //POS_A縺ｮ蜷�鬆�逶ｮ髮�險�
        sql.append("( ");
        sql.append("    SELECT ");
        sql.append("        A1.COMMAND ");
        sql.append("        ,'00' || A1.STORE AS STORE ");
        sql.append("        ,A1.POS ");
        sql.append("        ,A1.TRANS_NO ");
        sql.append("        ,SUBSTR(A1.EIGYO_DT,1,8) AS EIGYO_DT ");
        sql.append("        ,A1.ZEINUKI_VL ");
        sql.append("        ,A1.TAX_VL ");
        sql.append("        ,A1.ZEIKOMI_VL ");
        sql.append("        ,A1.CASHIER_ID ");
        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) ADD(S)
        sql.append("        ,A1.ATYPE ");
        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) ADD(E)
        sql.append("        ,A2.ZEINUKI_TOT_16IKOU_VL ");
        sql.append("        ,A1.RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("        ,A4.VAT_5_VL ");
        sql.append("        ,A5.VAT_10_VL ");
        sql.append("        ,A3.ZEIKOMI_TOT_0_VL ");
        sql.append("        ,A4.ZEIKOMI_TOT_5_VL ");
        sql.append("        ,A5.ZEIKOMI_TOT_10_VL ");
        sql.append("        ,A1.DATA_SAKUSEI_DT ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("        ,A1.STORE || A1.POS || A1.TRANS_NO AS RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        sql.append("    FROM ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,SUM(ACTUAL_SELL_WOT) AS ZEINUKI_VL ");
        sql.append("            ,SUM(ACTUAL_SELL - ACTUAL_SELL_WOT) AS TAX_VL ");
        sql.append("            ,SUM(ACTUAL_SELL) AS ZEIKOMI_VL ");
        sql.append("            ,CASHIER_ID ");
        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) ADD(S)
        sql.append("            ,MIN(ATYPE) AS ATYPE ");
        //2017.01.30 J.Endo FIVI蟇ｾ蠢�(#3807) ADD(E)
        sql.append("            ,SUM(ACTUAL_SELL_WOT2 - ACTUAL_SELL_WOT) AS RECEIPT_DISCOUNT_TOT_VL ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("        FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("            WK_POS_A_ITEM ");
        sql.append("            DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DATA_SAKUSEI_DT = ? ");
        sql.append("            DATA_SAKUSEI_DT = ? AND ");
        sql.append("            ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("    )A1 ");
        sql.append("    LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("            ,SUM(ACTUAL_SELL_WOT) AS ZEINUKI_TOT_16IKOU_VL ");
        sql.append("        FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("            WK_POS_A_ITEM ");
        sql.append("            DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            ODR_LINE_IDX >= 16 ");
        sql.append("            ODR_LINE_IDX >= 16 AND ");
        sql.append("            ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("    )A2 ");
        sql.append("    ON ");
        sql.append("        A1.COMMAND = A2.COMMAND AND ");
        sql.append("        A1.STORE = A2.STORE AND ");
        sql.append("        A1.POS = A2.POS AND ");
        sql.append("        A1.TRANS_NO = A2.TRANS_NO AND ");
        sql.append("        A1.EIGYO_DT = A2.EIGYO_DT AND ");
        sql.append("        A1.CASHIER_ID = A2.CASHIER_ID AND ");
        sql.append("        A1.DATA_SAKUSEI_DT = A2.DATA_SAKUSEI_DT ");
        sql.append("    LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("            ,SUM(ACTUAL_SELL) AS ZEIKOMI_TOT_0_VL ");
        sql.append("        FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("            WK_POS_A_ITEM ");
        sql.append("            DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            GST_TAX = '0.00' ");
        sql.append("            GST_TAX = '0.00' AND ");
        sql.append("            ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("    )A3 ");
        sql.append("    ON ");
        sql.append("        A1.COMMAND = A3.COMMAND AND ");
        sql.append("        A1.STORE = A3.STORE AND ");
        sql.append("        A1.POS = A3.POS AND ");
        sql.append("        A1.TRANS_NO = A3.TRANS_NO AND ");
        sql.append("        A1.EIGYO_DT = A3.EIGYO_DT AND ");
        sql.append("        A1.CASHIER_ID = A3.CASHIER_ID AND ");
        sql.append("        A1.DATA_SAKUSEI_DT = A3.DATA_SAKUSEI_DT ");
        sql.append("    LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("            ,SUM(ACTUAL_SELL - ACTUAL_SELL_WOT) AS VAT_5_VL ");
        sql.append("            ,SUM(ACTUAL_SELL) AS ZEIKOMI_TOT_5_VL ");
        sql.append("        FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("            WK_POS_A_ITEM ");
        sql.append("            DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            GST_TAX = '0.05' ");
        sql.append("            GST_TAX = '0.05' AND ");
        sql.append("            ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("    )A4 ");
        sql.append("    ON ");
        sql.append("        A1.COMMAND = A4.COMMAND AND ");
        sql.append("        A1.STORE = A4.STORE AND ");
        sql.append("        A1.POS = A4.POS AND ");
        sql.append("        A1.TRANS_NO = A4.TRANS_NO AND ");
        sql.append("        A1.EIGYO_DT = A4.EIGYO_DT AND ");
        sql.append("        A1.CASHIER_ID = A4.CASHIER_ID AND ");
        sql.append("        A1.DATA_SAKUSEI_DT = A4.DATA_SAKUSEI_DT ");
        sql.append("    LEFT OUTER JOIN ");
        sql.append("    ( ");
        sql.append("        SELECT ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("            ,SUM(ACTUAL_SELL - ACTUAL_SELL_WOT) AS VAT_10_VL ");
        sql.append("            ,SUM(ACTUAL_SELL) AS ZEIKOMI_TOT_10_VL ");
        sql.append("        FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("            WK_POS_A_ITEM ");
        sql.append("            DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("            GST_TAX = '0.10' ");
        sql.append("            GST_TAX = '0.10' AND ");
        sql.append("            ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("        GROUP BY ");
        sql.append("            COMMAND ");
        sql.append("            ,STORE ");
        sql.append("            ,POS ");
        sql.append("            ,TRANS_NO ");
        sql.append("            ,EIGYO_DT ");
        sql.append("            ,CASHIER_ID ");
        sql.append("            ,DATA_SAKUSEI_DT ");
        sql.append("    )A5 ");
        sql.append("    ON ");
        sql.append("        A1.COMMAND = A5.COMMAND AND ");
        sql.append("        A1.STORE = A5.STORE AND ");
        sql.append("        A1.POS = A5.POS AND ");
        sql.append("        A1.TRANS_NO = A5.TRANS_NO AND ");
        sql.append("        A1.EIGYO_DT = A5.EIGYO_DT AND ");
        sql.append("        A1.CASHIER_ID = A5.CASHIER_ID AND ");
        sql.append("        A1.DATA_SAKUSEI_DT = A5.DATA_SAKUSEI_DT ");
        sql.append("    )POS_A ");

        sql.append("LEFT OUTER JOIN ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("    DT_POS_B_TOTAL POS_B ");
        sql.append("    WK_POS_B_TOTAL POS_B ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("ON ");
        sql.append("    POS_A.COMMAND = POS_B.COMMAND AND ");
        sql.append("    POS_A.STORE = '00' || POS_B.STORE AND ");
        sql.append("    POS_A.POS = POS_B.POS AND ");
        sql.append("    POS_A.TRANS_NO = POS_B.TRANS_NO AND ");
        sql.append("    POS_A.EIGYO_DT = POS_B.EIGYO_DT AND ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("    POS_A.DATA_SAKUSEI_DT=POS_B.DATA_SAKUSEI_DT ");
        sql.append("    POS_A.DATA_SAKUSEI_DT=POS_B.DATA_SAKUSEI_DT AND ");
        sql.append("    POS_B.ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)

        //POS_C縺ｮ驥崎､�繝�繝ｼ繧ｿ邨槭ｊ霎ｼ縺ｿ
        sql.append("LEFT OUTER JOIN ");
        sql.append("    ( ");
        ////2017.01.11 M.Kawakami FIVI蟇ｾ蠢�(#3596) MOD (S) --------------------
        ////sql.append("        SELECT ");
        ////sql.append("            COMMAND ");
        ////sql.append("            ,STORE ");
        ////sql.append("            ,POS ");
        ////sql.append("            ,TRANS_NO ");
        ////sql.append("            ,EIGYO_DT ");
        ////sql.append("            ,DATA_SAKUSEI_DT ");
        ////sql.append("            ,PYMT_TYPE ");
        ////sql.append("            ,PYMT_TYPE2 ");
        ////sql.append("        FROM ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //////sql.append("            DT_POS_C_PAYMENT C1 ");
        //////sql.append("        WHERE EXISTS( ");
        ////sql.append("            WK_POS_C_PAYMENT C1 ");
        ////sql.append("        WHERE ");
        ////sql.append("            ERR_KB = '0' AND ");
        ////sql.append("            EXISTS( ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        ////sql.append("            SELECT ");
        ////sql.append("                COMMAND ");
        ////sql.append("                ,STORE ");
        ////sql.append("                ,POS ");
        ////sql.append("                ,TRANS_NO ");
        ////sql.append("                ,EIGYO_DT ");
        ////sql.append("                ,DATA_SAKUSEI_DT ");
        ////sql.append("                ,PYMT_TYPE ");
        ////sql.append("                ,MIN(PYMT_TYPE2) AS PYMT_TYPE2 ");
        ////sql.append("            FROM ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //////sql.append("                DT_POS_C_PAYMENT C2 ");
        //////sql.append("            WHERE EXISTS( ");
        ////sql.append("                WK_POS_C_PAYMENT C2 ");
        ////sql.append("            WHERE ");
        ////sql.append("                ERR_KB = '0' AND ");
        ////sql.append("                EXISTS( ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        ////sql.append("                SELECT ");
        ////sql.append("                    COMMAND ");
        ////sql.append("                    ,STORE ");
        ////sql.append("                    ,POS ");
        ////sql.append("                    ,TRANS_NO ");
        ////sql.append("                    ,EIGYO_DT ");
        ////sql.append("                    ,DATA_SAKUSEI_DT ");
        ////sql.append("                    ,MIN(PYMT_TYPE) AS PYMT_TYPE ");
        ////sql.append("                FROM ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //////sql.append("                    DT_POS_C_PAYMENT C3 ");
        ////sql.append("                    WK_POS_C_PAYMENT C3 ");
        ////sql.append("                WHERE ");
        ////sql.append("                    ERR_KB = '0' ");
        //////2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        ////sql.append("                GROUP BY ");
        ////sql.append("                    COMMAND ");
        ////sql.append("                    ,STORE ");
        ////sql.append("                    ,POS ");
        ////sql.append("                    ,TRANS_NO ");
        ////sql.append("                    ,EIGYO_DT ");
        ////sql.append("                    ,DATA_SAKUSEI_DT ");
        ////sql.append("                HAVING ");
        ////sql.append("                    C2.COMMAND = C3.COMMAND AND ");
        ////sql.append("                    C2.STORE = C3.STORE AND ");
        ////sql.append("                    C2.POS = C3.POS AND ");
        ////sql.append("                    C2.TRANS_NO = C3.TRANS_NO AND ");
        ////sql.append("                    C2.EIGYO_DT = C3.EIGYO_DT AND ");
        ////sql.append("                    C2.DATA_SAKUSEI_DT = C3.DATA_SAKUSEI_DT AND ");
        ////sql.append("                    C2.PYMT_TYPE = MIN(C3.PYMT_TYPE) ");
        ////sql.append("            ) ");
        ////sql.append("            GROUP BY ");
        ////sql.append("                COMMAND ");
        ////sql.append("                ,STORE ");
        ////sql.append("                ,POS ");
        ////sql.append("                ,TRANS_NO ");
        ////sql.append("                ,EIGYO_DT ");
        ////sql.append("                ,DATA_SAKUSEI_DT ");
        ////sql.append("                ,PYMT_TYPE ");
        ////sql.append("            HAVING ");
        ////sql.append("                C1.COMMAND = C2.COMMAND AND ");
        ////sql.append("                C1.STORE = C2.STORE AND ");
        ////sql.append("                C1.POS = C2.POS AND ");
        ////sql.append("                C1.TRANS_NO = C2.TRANS_NO AND ");
        ////sql.append("                C1.EIGYO_DT = C2.EIGYO_DT AND ");
        ////sql.append("                C1.DATA_SAKUSEI_DT = C2.DATA_SAKUSEI_DT AND ");
        ////sql.append("                C1.PYMT_TYPE = C2.PYMT_TYPE AND ");
        ////sql.append("                C1.PYMT_TYPE2 = MIN(C2.PYMT_TYPE2) ");
        ////sql.append("        ) ");
        ////sql.append("        GROUP BY ");
        ////sql.append("            COMMAND ");
        ////sql.append("            ,STORE ");
        ////sql.append("            ,POS ");
        ////sql.append("            ,TRANS_NO ");
        ////sql.append("            ,EIGYO_DT ");
        ////sql.append("            ,DATA_SAKUSEI_DT ");
        ////sql.append("            ,PYMT_TYPE ");
        ////sql.append("            ,PYMT_TYPE2 ");
        ////
        sql.append("    SELECT ");
        sql.append("       COMMAND ");
        sql.append("      ,STORE ");
        sql.append("      ,POS ");
        sql.append("      ,TRANS_NO ");
        sql.append("      ,EIGYO_DT ");
        sql.append("      ,DATA_SAKUSEI_DT ");
        sql.append("      ,SUBSTR(WK_PYMT_TYPE,1,3) AS PYMT_TYPE ");
        //2017.02.16 J.Endo FIVI蟇ｾ蠢�(#4013) ADD(S)
        //sql.append("      ,SUBSTR(WK_PYMT_TYPE,-5) AS PYMT_TYPE2 ");
        sql.append("      ,SUBSTR(WK_PYMT_TYPE,-7) AS PYMT_TYPE2 ");
        //2017.02.16 J.Endo FIVI蟇ｾ蠢�(#4013) ADD(E)
        sql.append("    FROM ");
        sql.append("      ( ");
        sql.append("      SELECT ");
        sql.append("         COMMAND ");
        sql.append("        ,STORE ");
        sql.append("        ,POS ");
        sql.append("        ,TRANS_NO ");
        sql.append("        ,EIGYO_DT ");
        sql.append("        ,DATA_SAKUSEI_DT ");
        //2017.02.16 J.Endo FIVI蟇ｾ蠢�(#4013) ADD(S)
        //sql.append("        ,MIN( NVL(PYMT_TYPE,'   ') || NVL(PYMT_TYPE2,'     ') ) AS WK_PYMT_TYPE ");
        sql.append("        ,MIN( NVL(PYMT_TYPE,'   ') || NVL(PYMT_TYPE2,'       ') ) AS WK_PYMT_TYPE ");
        //2017.02.16 J.Endo FIVI蟇ｾ蠢�(#4013) ADD(E)
        sql.append("      FROM ");
        sql.append("        WK_POS_C_PAYMENT C1 ");
        sql.append("      WHERE ");
        sql.append("        ERR_KB = '0' ");
        sql.append("      GROUP BY ");
        sql.append("         COMMAND ");
        sql.append("        ,STORE ");
        sql.append("        ,POS ");
        sql.append("        ,TRANS_NO ");
        sql.append("        ,EIGYO_DT ");
        sql.append("        ,DATA_SAKUSEI_DT ");
        sql.append("      ) ");
        ////2017.01.11 M.Kawakami FIVI蟇ｾ蠢�(#3596) MOD (E) --------------------
        sql.append("    )POS_C ");
        sql.append("ON ");
        sql.append("    POS_A.COMMAND = POS_C.COMMAND AND ");
        sql.append("    POS_A.STORE = '00' || POS_C.STORE AND ");
        sql.append("    POS_A.POS = POS_C.POS AND ");
        sql.append("    POS_A.TRANS_NO = POS_C.TRANS_NO AND ");
        sql.append("    POS_A.EIGYO_DT = SUBSTR(POS_C.EIGYO_DT,1,8) AND ");
        sql.append("    POS_A.DATA_SAKUSEI_DT = POS_C.DATA_SAKUSEI_DT ");

        sql.append("LEFT OUTER JOIN ");
        sql.append("    H_R_TENPO ");
        sql.append("ON ");
        sql.append("    POS_A.STORE = H_R_TENPO.TENPO_CD ");

        sql.append("LEFT OUTER JOIN ");
        sql.append("    R_TENPO ");
        sql.append("ON ");
        sql.append("    POS_A.STORE = R_TENPO.TENPO_CD ");

        sql.append("LEFT OUTER JOIN ");
        sql.append("    R_PORTAL_USER ");
        sql.append("ON ");
        sql.append("    TRIM(POS_A.CASHIER_ID) = TRIM(R_PORTAL_USER.USER_ID) ");

        sql.append("LEFT OUTER JOIN ");
        sql.append("    R_PAYMENT ");
        sql.append("ON ");
        //2016.12.02 T.Kamei FIVI蟇ｾ蠢�(#3036) ADD(S)
        //sql.append("    POS_C.PYMT_TYPE = R_PAYMENT.SHIHARAI_SYUBETSU_CD ");
        sql.append("    POS_C.PYMT_TYPE = R_PAYMENT.SHIHARAI_SYUBETSU_CD AND ");
        sql.append("    POS_C.PYMT_TYPE2 = R_PAYMENT.SHIHARAI_SYUBETSU_SUB_CD ");
        //2016.12.02 T.Kamei FIVI蟇ｾ蠢�(#3036) ADD(E)
        //#5149 Add X.Liu 2017.05.25 (S)
        sql.append(" ");
        sql.append(" LEFT JOIN ");
        sql.append(" (");
        sql.append("    SELECT    ");
        sql.append("      COMMAND_CD    ");
        sql.append("      , TENPO_CD    ");
        sql.append("      , REGI_RB    ");
        sql.append("      , TERMINAL_RB    ");
        sql.append("      , EIGYO_DT    ");
        sql.append("      , COUNT(COMMAND_CD) AS CNT     ");
        sql.append("    FROM    ");
        sql.append("      WK_URIAGE_INVOICE_KANRI_M     ");
        sql.append("    GROUP BY    ");
        sql.append("      COMMAND_CD    ");
        sql.append("      , TENPO_CD    ");
        sql.append("      , REGI_RB    ");
        sql.append("      , TERMINAL_RB    ");
        sql.append("      , EIGYO_DT    ");
        sql.append(" ) MC");
        sql.append(" ON POS_A.COMMAND = MC.COMMAND_CD");
        sql.append(" AND POS_A.STORE = MC.TENPO_CD");
        sql.append(" AND POS_A.POS = MC.REGI_RB");
        sql.append(" AND POS_A.TRANS_NO = MC.TERMINAL_RB");
        sql.append(" AND POS_A.EIGYO_DT = MC.EIGYO_DT");
        //#5668 Add X.Liu 2017.07.26 (S)
        sql.append(" LEFT JOIN DT_POS_A_VAT_INF DPAVI ");
        sql.append("    ON DPAVI.COMMAND = POS_A.COMMAND ");
        sql.append("    AND '00' || DPAVI.STORE = POS_A.STORE ");
        sql.append("    AND DPAVI.POS = POS_A.POS ");
        sql.append("    AND DPAVI.TRANS_NO = POS_A.TRANS_NO ");
        sql.append("    AND TRIM(DPAVI.EIGYO_DT) = POS_A.EIGYO_DT");
        //#5668 Add X.Liu 2017.07.26 (E)
        //#5149 Add X.Liu 2017.05.25 (E)
        return sql.toString();
    }



    /**
     * 螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ縺吶ｋSQL繧貞叙蠕励☆繧�
     *
     * @return 螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ
     */
    private String getWkUriageInvoiceKanriMInsertSql( ) {
        StringBuilder sql = new StringBuilder();

        //2016.10.11 Y.Itaki FIVI蟇ｾ蠢�(#1816) 窶ｻ菫ｮ豁｣驥上′螟壹＞縺溘ａ縲ヾQL譖ｸ縺咲峩縺�
        //#5149 Mod X.Liu 2017.05.25 (S)
//        sql.append("INSERT /*+ APPEND */ INTO WK_URIAGE_INVOICE_KANRI_M( ");
        sql.append("INSERT INTO WK_URIAGE_INVOICE_KANRI_M( ");
        //#5149 Mod X.Liu 2017.05.25 (E)
        sql.append("     COMMAND_CD ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,REGI_RB ");
        sql.append("    ,TERMINAL_RB ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,SEQ_RB ");
        sql.append("    ,SALES_TYPE ");
        sql.append("    ,SYOHIN_CD ");
        sql.append("    ,SURYO_QT ");
        sql.append("    ,BAITANKA_VL ");
        sql.append("    ,ZEINUKI_VL ");
        sql.append("    ,TAX_RT ");
        sql.append("    ,ZEIGAKU_VL ");
        sql.append("    ,ZEIKOMI_VL ");
        sql.append("    ,OLD_SYOHIN_CD ");
        sql.append("    ,SYOHIN_NA ");
        sql.append("    ,HANBAI_TANI_TX ");
        sql.append("    ,GENTANKA_VL ");
        sql.append("    ,ZEI_KB ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,DELETE_FG ");
        sql.append("    ,HANBAI_WEIGHT_QT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(S)
        sql.append("    ,HAMPER_ITEM_CD ");
        sql.append("    ,NEBIKI_VATIN_VL ");
        sql.append("    ,REG_SELL ");
        sql.append("    ,REG_SELL_WOT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(E)
        //2017.01.16 J.Endo FIVI蟇ｾ蠢�(#3502) ADD(S)
        sql.append("    ,TEIKAN_KB ");
        //2017.01.16 J.Endo FIVI蟇ｾ蠢�(#3502) ADD(E)
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("    ,RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("    ,RECEIPT_SUB_NO ");
        sql.append("    ,VAT_PRINT_KB ");
        sql.append("    ,SONEBIKIGO_ZEIKOMI_VL ");
        sql.append("    ,SONEBIKIGO_ZEINUKI_VL ");
        //#4768 Add X.Liu 2017.04.18 (E)
        sql.append("    ) ");
        sql.append("SELECT ");
        sql.append("    POS_A.COMMAND ");
        sql.append("    ,POS_A.STORE ");
        sql.append("    ,POS_A.POS ");
        sql.append("    ,POS_A.TRANS_NO ");
        sql.append("    ,POS_A.EIGYO_DT ");
        sql.append("    ,POS_A.ODR_LINE_IDX ");
        sql.append("    ,POS_A.ATYPE ");
        sql.append("    ,POS_A.SKU ");
        sql.append("    ,POS_A.QTY ");
        sql.append("    ,POS_A.REGULAR_UNIT_SELL ");
        sql.append("    ,POS_A.ACTUAL_SELL_WOT2 ");
        sql.append("    ,POS_A.GST_TAX AS TAX_RT ");
        sql.append("    ,POS_A.ZEIGAKU_VL ");
        sql.append("    ,POS_A.ACTUAL_SELL2 ");
        sql.append("    ,RS.OLD_SYOHIN_CD ");
        sql.append("    ,RS.HINMEI_KANJI_NA ");
        sql.append("    ,RN.KANJI_NA AS HANBAI_TANI_TX ");
        sql.append("    ,RS.GENTANKA_VL ");
        sql.append("    ,RS.ZEI_KB ");
        sql.append("    ,? AS INSERT_USER_ID ");
        sql.append("    ,? AS INSERT_TS ");
        sql.append("    ,? AS UPDATE_USER_ID ");
        sql.append("    ,? AS UPDATE_TS ");
        sql.append("    ,'0' AS DELETE_FG ");
        sql.append("    ,POS_A.WEIGHT_SOLD ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(S)
        sql.append("    ,POS_A.HAMPER_ITEM_CD ");
        //2017.01.23 J.Endo FIVI蟇ｾ蠢�(#3548) MOD(S)
        //sql.append("    ,POS_A.REG_SELL - POS_A.ACTUAL_SELL2 NEBIKI_VATIN_VL ");
        //sql.append("    ,POS_A.REG_SELL ");
        sql.append("    ,CASE RS.TEIKAN_KB ");
        sql.append("         WHEN '1' THEN (POS_A.QTY         * POS_A.REG_SELL) - POS_A.ACTUAL_SELL2 ");
        sql.append("         WHEN '2' THEN (POS_A.WEIGHT_SOLD * POS_A.REG_SELL) - POS_A.ACTUAL_SELL2 ");
        sql.append("     END NEBIKI_VATIN_VL ");
        sql.append("    ,CASE RS.TEIKAN_KB ");
        sql.append("         WHEN '1' THEN (POS_A.QTY         * POS_A.REG_SELL) ");
        sql.append("         WHEN '2' THEN (POS_A.WEIGHT_SOLD * POS_A.REG_SELL) ");
        sql.append("     END REG_SELL ");
        //2017.01.23 J.Endo FIVI蟇ｾ蠢�(#3548) MOD(E)
        sql.append("    ,POS_A.REG_SELL_WOT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(E)
        //2017.01.16 J.Endo FIVI蟇ｾ蠢�(#3502) ADD(S)
        sql.append("    ,RS.TEIKAN_KB ");
        //2017.01.16 J.Endo FIVI蟇ｾ蠢�(#3502) ADD(E)
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("    ,POS_A.RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("    ,0 ");
        sql.append("    ,'0' ");
        sql.append("    ,POS_A.ACTUAL_SELL ");
        sql.append("    ,POS_A.ACTUAL_SELL_WOT ");
        //#4768 Add X.Liu 2017.04.18 (E)
        sql.append("FROM ");
        sql.append("( ");
        sql.append("    SELECT ");
        sql.append("        COMMAND ");
        sql.append("        ,'00' || STORE AS STORE ");
        sql.append("        ,POS ");
        sql.append("        ,TRANS_NO ");
        sql.append("        ,SUBSTR(EIGYO_DT,1,8) AS EIGYO_DT ");
        sql.append("        ,ODR_LINE_IDX ");
        sql.append("        ,ATYPE ");
        sql.append("        ,SKU ");
        sql.append("        ,QTY ");
        sql.append("        ,REGULAR_UNIT_SELL ");
        sql.append("        ,ACTUAL_SELL_WOT2 ");
        sql.append("        ,GST_TAX * 100 AS GST_TAX ");
        sql.append("        ,ACTUAL_SELL2 - ACTUAL_SELL_WOT2 AS ZEIGAKU_VL ");
        sql.append("        ,ACTUAL_SELL2 ");
        sql.append("        ,WEIGHT_SOLD ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(S)
        sql.append("        ,HAMPER_ITEM_CD ");
        sql.append("        ,REG_SELL ");
        sql.append("        ,REG_SELL_WOT ");
        //2016.11.10 J.Endo FIVI蟇ｾ蠢�(#2313) ADD(E)

        ////2017.01.13 M.Kawakami FIVI蟇ｾ蠢�(#3621) ADD (S) --------------------
        sql.append("        ,STORE AS J_STORE ");
        sql.append("        ,EIGYO_DT AS J_EIGYO_DT ");
        sql.append("        ,CASHIER_ID ");
        sql.append("        ,FORMAT ");
        ////2017.01.13 M.Kawakami FIVI蟇ｾ蠢�(#3621) ADD (E) --------------------
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(S)
        sql.append("        ,STORE || POS || TRANS_NO AS RECEIPT_NO ");
        //2017.02.17 J.Endo FIVI蟇ｾ蠢�(#4097) ADD(E)
        //#4768 Add X.Liu 2017.04.18 (S)
        sql.append("        ,ACTUAL_SELL ");
        sql.append("        ,ACTUAL_SELL_WOT ");
        //#4768 Add X.Liu 2017.04.18 (E)

        sql.append("    FROM ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("        DT_POS_A_ITEM ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(S)
        //sql.append("        WK_POS_A_ITEM ");
        sql.append("        DT_POS_A_CUT_HENPIN ");
        //2017.01.11 J.Endo FIVI蟇ｾ蠢�(#3356) MOD(E)
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append("    WHERE ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(S)
        //sql.append("        DATA_SAKUSEI_DT = ? ");
        sql.append("        DATA_SAKUSEI_DT = ? AND ");
        sql.append("        ERR_KB = '0' ");
        //2017.01.05 J.Endo FIVI蟇ｾ蠢�(#3547) MOD(E)
        sql.append(")POS_A ");

        sql.append("LEFT OUTER JOIN ");

        ////2017.01.13 M.Kawakami FIVI蟇ｾ蠢�(#3621) MOD (S) --------------------
        ////sql.append("    R_SYOHIN RS ");
        ////sql.append("ON ");
        ////// 2016.12.07 T.Kamei FIVI蟇ｾ蠢�(#3036) MOD(S)
        ////sql.append("    EXISTS ");
        ////sql.append("        ( ");
        ////sql.append("            SELECT 1 FROM R_SYOHIN RS2 ");
        ////sql.append("            WHERE ");
        ////sql.append("                RS.SYOHIN_CD = POS_A.SKU ");
        ////sql.append("            AND RS.YUKO_DT = ");
        ////sql.append("                ( ");
        ////sql.append("                    SELECT MAX(YUKO_DT) FROM R_SYOHIN ");
        ////sql.append("                    WHERE ");
        ////sql.append("                        SYOHIN_CD = POS_A.SKU ");
        ////sql.append("                    AND YUKO_DT <= POS_A.EIGYO_DT ");
        ////sql.append("                    GROUP BY SYOHIN_CD ");
        ////sql.append("                ) ");
        ////sql.append("            AND RS.BUNRUI1_CD = ");
        ////sql.append("                ( ");
        ////sql.append("                    SELECT MIN(BUNRUI1_CD) FROM R_SYOHIN RS3 ");
        ////sql.append("                    WHERE ");
        ////sql.append("                        RS3.SYOHIN_CD = POS_A.SKU ");
        ////sql.append("                    AND RS3.YUKO_DT = ");
        ////sql.append("                        ( ");
        ////sql.append("                            SELECT MAX(YUKO_DT) FROM R_SYOHIN ");
        ////sql.append("                            WHERE ");
        ////sql.append("                                SYOHIN_CD = POS_A.SKU ");
        ////sql.append("                            AND YUKO_DT <= POS_A.EIGYO_DT ");
        ////sql.append("                            GROUP BY SYOHIN_CD ");
        ////sql.append("                        ) ");
        ////sql.append("                    GROUP BY RS3.SYOHIN_CD ");
        ////sql.append("                ) ");
        ////sql.append("        ) ");
        //////sql.append("    POS_A.SKU = RS.SYOHIN_CD AND ");
        //////sql.append("    POS_A.EIGYO_DT >= RS.YUKO_DT ");
        ////// 2016.12.07 T.Kamei FIVI蟇ｾ蠢�(#3036) MOD(S)
        ////
        sql.append("  ( ");
        sql.append("  SELECT ");
        sql.append("     A2S2.COMMAND ");
        sql.append("    ,A2S2.STORE ");
        sql.append("    ,A2S2.POS ");
        sql.append("    ,A2S2.TRANS_NO ");
        sql.append("    ,A2S2.CASHIER_ID ");
        sql.append("    ,A2S2.FORMAT ");
        sql.append("    ,A2S2.ATYPE ");
        sql.append("    ,A2S2.ODR_LINE_IDX ");
        sql.append("    ,A2S2.SKU ");
        sql.append("    ,A2S2.EIGYO_DT ");
        sql.append("    ,A2S2.YUKO_DT ");
        sql.append("    ,A2S2.BUNRUI1_CD ");
        sql.append("    ,RSY2.OLD_SYOHIN_CD ");
        sql.append("    ,RSY2.HINMEI_KANJI_NA ");
        sql.append("    ,RSY2.GENTANKA_VL ");
        sql.append("    ,RSY2.ZEI_KB ");
        sql.append("    ,RSY2.HANBAI_TANI ");
        sql.append("    ,RSY2.TEIKAN_KB ");
        sql.append("  FROM ");
        sql.append("    ( ");
        sql.append("      SELECT ");
        sql.append("         A1S1.COMMAND ");
        sql.append("        ,A1S1.STORE ");
        sql.append("        ,A1S1.POS ");
        sql.append("        ,A1S1.TRANS_NO ");
        sql.append("        ,A1S1.CASHIER_ID ");
        sql.append("        ,A1S1.FORMAT ");
        sql.append("        ,A1S1.ATYPE ");
        sql.append("        ,A1S1.ODR_LINE_IDX ");
        sql.append("        ,A1S1.SKU ");
        sql.append("        ,A1S1.EIGYO_DT ");
        sql.append("        ,A1S1.YUKO_DT ");
        sql.append("        ,MIN( A1S1.BUNRUI1_CD ) BUNRUI1_CD ");
        sql.append("      FROM ");
        sql.append("        ( ");
        sql.append("          SELECT ");
        sql.append("             AWK1.COMMAND ");
        sql.append("            ,AWK1.STORE ");
        sql.append("            ,AWK1.POS ");
        sql.append("            ,AWK1.TRANS_NO ");
        sql.append("            ,AWK1.CASHIER_ID ");
        sql.append("            ,AWK1.FORMAT ");
        sql.append("            ,AWK1.ATYPE ");
        sql.append("            ,AWK1.ODR_LINE_IDX ");
        sql.append("            ,AWK1.SKU ");
        sql.append("            ,AWK1.EIGYO_DT ");
        sql.append("            ,MAX( RSY1.YUKO_DT ) YUKO_DT ");
        sql.append("            ,RSY1.BUNRUI1_CD ");
        sql.append("          FROM ");
        sql.append("            DT_POS_A_CUT_HENPIN  AWK1 ");
        sql.append("          LEFT OUTER JOIN ");
        sql.append("            R_SYOHIN  RSY1 ");
        sql.append("            ON  AWK1.SKU = RSY1.SYOHIN_CD ");
        sql.append("            AND AWK1.EIGYO_DT >= RSY1.YUKO_DT ");
        sql.append("          GROUP BY ");
        sql.append("             AWK1.COMMAND ");
        sql.append("            ,AWK1.STORE ");
        sql.append("            ,AWK1.POS ");
        sql.append("            ,AWK1.TRANS_NO ");
        sql.append("            ,AWK1.CASHIER_ID ");
        sql.append("            ,AWK1.FORMAT ");
        sql.append("            ,AWK1.ATYPE ");
        sql.append("            ,AWK1.ODR_LINE_IDX ");
        sql.append("            ,AWK1.SKU ");
        sql.append("            ,AWK1.EIGYO_DT ");
        sql.append("            ,RSY1.BUNRUI1_CD ");
        sql.append("        ) A1S1 ");
        sql.append("      GROUP BY ");
        sql.append("         A1S1.COMMAND ");
        sql.append("        ,A1S1.STORE ");
        sql.append("        ,A1S1.POS ");
        sql.append("        ,A1S1.TRANS_NO ");
        sql.append("        ,A1S1.CASHIER_ID ");
        sql.append("        ,A1S1.FORMAT ");
        sql.append("        ,A1S1.ATYPE ");
        sql.append("        ,A1S1.ODR_LINE_IDX ");
        sql.append("        ,A1S1.SKU ");
        sql.append("        ,A1S1.EIGYO_DT ");
        sql.append("        ,A1S1.YUKO_DT ");
        sql.append("    ) A2S2 ");
        sql.append("  LEFT OUTER JOIN ");
        sql.append("    R_SYOHIN  RSY2 ");
        sql.append("    ON  A2S2.SKU = RSY2.SYOHIN_CD ");
        sql.append("    AND A2S2.YUKO_DT = RSY2.YUKO_DT ");
        // #6620 MOD 2022.11.18 VU.TD (S)
//        sql.append("    AND A2S2.BUNRUI1_CD = RSY2.BUNRUI1_CD ");
        // #6620 MOD 2022.11.18 VU.TD (E)
        sql.append("  ) RS ");
        sql.append("    ON   POS_A.COMMAND = RS.COMMAND ");
        sql.append("    AND  POS_A.J_STORE = RS.STORE ");
        sql.append("    AND  POS_A.POS = RS.POS ");
        sql.append("    AND  POS_A.TRANS_NO = RS.TRANS_NO ");
        sql.append("    AND  POS_A.CASHIER_ID = RS.CASHIER_ID ");
        sql.append("    AND  POS_A.FORMAT = RS.FORMAT ");
        sql.append("    AND  POS_A.ATYPE = RS.ATYPE ");
        sql.append("    AND  POS_A.ODR_LINE_IDX = RS.ODR_LINE_IDX ");
        sql.append("    AND  POS_A.SKU = RS.SKU ");
        sql.append("    AND  POS_A.J_EIGYO_DT = RS.EIGYO_DT ");
        ////2017.01.13 M.Kawakami FIVI蟇ｾ蠢�(#3621) MOD (E) --------------------

        sql.append("LEFT OUTER JOIN ");
        sql.append("    R_NAMECTF RN ");
        sql.append("ON ");
        sql.append("    TRIM(RS.HANBAI_TANI) = TRIM(RN.CODE_CD) AND ");
        sql.append("    RN.SYUBETU_NO_CD = '" + MessageUtil.getMessage("3040", userLocal) + "' ");

        return sql.toString();
    }

    //#5668 Del X.Liu 2017.07.26 (S)
//    //#4022 X.Liu Add 2017.02.22 (S)
//    /**
//     * A繝ｬ繧ｳ繝ｼ繝芽ｿ泌刀繧ｫ繝�繝医ョ繝ｼ繧ｿ繧貞叙蠕励☆繧�
//     *
//     * @return 螢ｲ荳蟹NVOICE邂｡逅�譏守ｴｰ繝ｯ繝ｼ繧ｯ逋ｻ骭ｲ
//     */
//    private String getArecHenpinJohoCutDataSql( ) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT "); 
//        sql.append(" COMMAND, ");
//        sql.append(" '00'|| STORE||POS||TRANS_NO AS TEMPKEY, ");
//        sql.append(" ATYPE, ");
//        sql.append(" SUBSTR(EIGYO_DT, 1, 8) AS EIGYODT");
//        sql.append(" FROM DT_POS_A_CUT_HENPIN ");
//        sql.append(" WHERE ");
//        sql.append(" ERR_KB = '0'");
//        sql.append(" AND DATA_SAKUSEI_DT = '" +BATCH_DT +"'");
//        sql.append(" ORDER BY TEMPKEY,ATYPE");
//                 
//        return sql.toString();
//    }
//
//    private String getWkUriageInvoiceKanriHUpdateSql(){
//        StringBuilder sql = new StringBuilder();
//        sql.append(" UPDATE WK_URIAGE_INVOICE_KANRI_H "); 
//        sql.append(" SET VAT_PRINT_KB = ? ");
//        sql.append(" WHERE TENPO_CD = ? ");
//        sql.append(" AND REGI_RB = ? ");
//        sql.append(" AND TERMINAL_RB = ? ");
//        sql.append(" AND EIGYO_DT = ? ");
//        return sql.toString();
//    }
//    class ARecHenJohoCutData{
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
//    //#4022 X.Liu Add 2017.02.22 (E)
    //#5668 Del X.Liu 2017.07.26 (E)

    /**
     * 繧｢繧ｦ繝医�励ャ繝�Bean繧貞叙蠕励☆繧�
     * @return Object
     */
    public Object getOutputObject() throws Exception {

        return null;
    }

    /**
     * 繧､繝ｳ繝励ャ繝�Bean繧定ｨｭ螳壹☆繧�
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
