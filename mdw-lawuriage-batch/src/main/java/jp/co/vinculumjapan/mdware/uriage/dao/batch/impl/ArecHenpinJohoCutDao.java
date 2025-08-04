package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.HashMap;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *
 * <p>タイトル: HamperKouseiSyohinUriageJisekiCreateDao.java クラス</p>
 * <p>説明　: Ａレコード返品情報カットデータ作成</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2017/01/10) S_MDware-G_FIVIマート様開発 VINX J.Endo
 * @Version 1.01 (2017/01/18) 購入後返品(コマンドコード"0044")は相殺しないように変更 VINX J.Endo #3626
 * @Version 1.02 (2017/01/20) VINX J.Endo #3710
 * @Version 1.03 (2017/03/23) FIVI対応(#4387) VINX N.Katou
 *
 */
public class ArecHenpinJohoCutDao implements DaoIf {

    /** バッチ処理名 */
    private static final String DAO_NAME = "Ａレコード返品情報カットデータ作成";
    /** 登録先テーブル名称(Ａレコード返品カットデータ) */
    private static final String DT_POS_A_CUT_HENPIN_NAME = "Ａレコード返品カットデータ";
    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE DT_POS_A_CUT_HENPIN";
    /** 返品により明細をカットと判定するための文字列 */
    private static final String CUT_DATA_CHAR = "CUT";

    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;

        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExSelect = null;
        PreparedStatementEx preparedStatementExInsert = null;
        PreparedStatementEx preparedStatementExDelete = null;

        int insertCount = 0;

        ResultSet resultSet = null;

        try {
            // Ａレコード返品情報カットデータの追加
            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DT_POS_A_CUT_HENPIN_NAME + "の追加を開始します。");

            // Ａレコード返品情報カットデータのデータを削除する
            preparedStatementExDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementExDelete.execute();

            // ＡレコードOKデータ取得SQL
            preparedStatementExSelect = invoker.getDataBase().prepareStatement(getArecOKDataSelectSql());
            resultSet = preparedStatementExSelect.executeQuery();

            // Ａレコード返品情報カットデータ作成SQL
            preparedStatementExInsert = invoker.getDataBase().prepareStatement(getArecHenpinJohoCutDataInsertSql());

            // レシート単位でマップに格納して返品を相殺するためのマップ生成
            HashMap<String,String> map = new HashMap<String,String>();
            map.clear(); // レシート単位マップをクリア
            int row = 0;

            // ＡレコードOKデータのデータがなくなるまで繰り返す
            while (resultSet.next()) {
                if (row == 0) {
                    // 初めは無条件でレシート単位マップに追加
                    row++;
                    map.put("COMMAND"           + row, resultSet.getString("COMMAND"));
                    map.put("STORE"             + row, resultSet.getString("STORE"));
                    map.put("POS"               + row, resultSet.getString("POS"));
                    map.put("TRANS_NO"          + row, resultSet.getString("TRANS_NO"));
                    map.put("CASHIER_ID"        + row, resultSet.getString("CASHIER_ID"));
                    map.put("FORMAT"            + row, resultSet.getString("FORMAT"));
                    map.put("ATYPE"             + row, resultSet.getString("ATYPE"));
                    map.put("ODR_LINE_IDX"      + row, resultSet.getString("ODR_LINE_IDX"));
                    map.put("SKU"               + row, resultSet.getString("SKU"));
                    map.put("QTY"               + row, resultSet.getString("QTY"));
                    map.put("WEIGHT_SOLD"       + row, resultSet.getString("WEIGHT_SOLD"));
                    map.put("REG_SELL"          + row, resultSet.getString("REG_SELL"));
                    map.put("ACTUAL_SELL2"      + row, resultSet.getString("ACTUAL_SELL2"));
                    map.put("ACTUAL_SELL"       + row, resultSet.getString("ACTUAL_SELL"));
                    map.put("REG_SELL_WOT"      + row, resultSet.getString("REG_SELL_WOT"));
                    map.put("ACTUAL_SELL_WOT2"  + row, resultSet.getString("ACTUAL_SELL_WOT2"));
                    map.put("ACTUAL_SELL_WOT"   + row, resultSet.getString("ACTUAL_SELL_WOT"));
                    map.put("EMP_PURCH"         + row, resultSet.getString("EMP_PURCH"));
                    map.put("ITEM_WEIGH"        + row, resultSet.getString("ITEM_WEIGH"));
                    map.put("REGULAR_UNIT_SELL" + row, resultSet.getString("REGULAR_UNIT_SELL"));
                    map.put("GST_TAX"           + row, resultSet.getString("GST_TAX"));
                    map.put("DISC4_AMT"         + row, resultSet.getString("DISC4_AMT"));
                    map.put("ITEM_NAME_RECEIPT" + row, resultSet.getString("ITEM_NAME_RECEIPT"));
                    map.put("ITEM_UOM_RECEIPT"  + row, resultSet.getString("ITEM_UOM_RECEIPT"));
                    map.put("PRCCHG_NO"         + row, resultSet.getString("PRCCHG_NO"));
                    map.put("PRCCHG1_NO"        + row, resultSet.getString("PRCCHG1_NO"));
                    map.put("PRCCHG2_NO"        + row, resultSet.getString("PRCCHG2_NO"));
                    map.put("PRCCHG3_NO"        + row, resultSet.getString("PRCCHG3_NO"));
                    map.put("PRIVILEGE_MEM"     + row, resultSet.getString("PRIVILEGE_MEM"));
                    map.put("OVER_WRITE_FLAG"   + row, resultSet.getString("OVER_WRITE_FLAG"));
                    map.put("HAMPER_ITEM_CD"    + row, resultSet.getString("HAMPER_ITEM_CD"));
                    map.put("SUPERVISOR_ID"     + row, resultSet.getString("SUPERVISOR_ID"));
                    map.put("EIGYO_DT"          + row, resultSet.getString("EIGYO_DT"));
                    map.put("DATA_SAKUSEI_DT"   + row, resultSet.getString("DATA_SAKUSEI_DT"));
                    map.put("ERR_KB"            + row, resultSet.getString("ERR_KB"));
                } else {
                    // 前明細と次の明細が同じかを判定
                    if (map.get("EIGYO_DT"+row).equals(resultSet.getString("EIGYO_DT")) &&
                        map.get("STORE"+row).equals(resultSet.getString("STORE")) &&
                        map.get("POS"+row).equals(resultSet.getString("POS")) &&
                        map.get("TRANS_NO"+row).equals(resultSet.getString("TRANS_NO"))) {

                        // 前明細が同じレシートの明細なのでレシート単位マップに追加
                        row++;
                        map.put("COMMAND"           + row, resultSet.getString("COMMAND"));
                        map.put("STORE"             + row, resultSet.getString("STORE"));
                        map.put("POS"               + row, resultSet.getString("POS"));
                        map.put("TRANS_NO"          + row, resultSet.getString("TRANS_NO"));
                        map.put("CASHIER_ID"        + row, resultSet.getString("CASHIER_ID"));
                        map.put("FORMAT"            + row, resultSet.getString("FORMAT"));
                        map.put("ATYPE"             + row, resultSet.getString("ATYPE"));
                        map.put("ODR_LINE_IDX"      + row, resultSet.getString("ODR_LINE_IDX"));
                        map.put("SKU"               + row, resultSet.getString("SKU"));
                        map.put("QTY"               + row, resultSet.getString("QTY"));
                        map.put("WEIGHT_SOLD"       + row, resultSet.getString("WEIGHT_SOLD"));
                        map.put("REG_SELL"          + row, resultSet.getString("REG_SELL"));
                        map.put("ACTUAL_SELL2"      + row, resultSet.getString("ACTUAL_SELL2"));
                        map.put("ACTUAL_SELL"       + row, resultSet.getString("ACTUAL_SELL"));
                        map.put("REG_SELL_WOT"      + row, resultSet.getString("REG_SELL_WOT"));
                        map.put("ACTUAL_SELL_WOT2"  + row, resultSet.getString("ACTUAL_SELL_WOT2"));
                        map.put("ACTUAL_SELL_WOT"   + row, resultSet.getString("ACTUAL_SELL_WOT"));
                        map.put("EMP_PURCH"         + row, resultSet.getString("EMP_PURCH"));
                        map.put("ITEM_WEIGH"        + row, resultSet.getString("ITEM_WEIGH"));
                        map.put("REGULAR_UNIT_SELL" + row, resultSet.getString("REGULAR_UNIT_SELL"));
                        map.put("GST_TAX"           + row, resultSet.getString("GST_TAX"));
                        map.put("DISC4_AMT"         + row, resultSet.getString("DISC4_AMT"));
                        map.put("ITEM_NAME_RECEIPT" + row, resultSet.getString("ITEM_NAME_RECEIPT"));
                        map.put("ITEM_UOM_RECEIPT"  + row, resultSet.getString("ITEM_UOM_RECEIPT"));
                        map.put("PRCCHG_NO"         + row, resultSet.getString("PRCCHG_NO"));
                        map.put("PRCCHG1_NO"        + row, resultSet.getString("PRCCHG1_NO"));
                        map.put("PRCCHG2_NO"        + row, resultSet.getString("PRCCHG2_NO"));
                        map.put("PRCCHG3_NO"        + row, resultSet.getString("PRCCHG3_NO"));
                        map.put("PRIVILEGE_MEM"     + row, resultSet.getString("PRIVILEGE_MEM"));
                        map.put("OVER_WRITE_FLAG"   + row, resultSet.getString("OVER_WRITE_FLAG"));
                        map.put("HAMPER_ITEM_CD"    + row, resultSet.getString("HAMPER_ITEM_CD"));
                        map.put("SUPERVISOR_ID"     + row, resultSet.getString("SUPERVISOR_ID"));
                        map.put("EIGYO_DT"          + row, resultSet.getString("EIGYO_DT"));
                        map.put("DATA_SAKUSEI_DT"   + row, resultSet.getString("DATA_SAKUSEI_DT"));
                        map.put("ERR_KB"            + row, resultSet.getString("ERR_KB"));
                    } else {
                        // 前明細と異なるレシートの明細なので、返品の相殺処理

                        // 返品明細の対象明細を検索し減算して相殺
                        int existNumber = henpinJohoCut(map, row);
                        if (existNumber != 0) {
                            invoker.infoLog(strUserId + "　：　返品に対する商品が存在しません。営業日:"+map.get("EIGYO_DT1")+",店舗コード:"+map.get("STORE1")+",レジNo:"+map.get("POS1")+",レシートNo:"+map.get("TRANS_NO1")+",商品コード:"+map.get("SKU"+existNumber));
                        } else {
                            for (int i = 1; i <= row; i++) {
                                // 返品データおよび相殺されたデータ以外を出力
                                if (!map.get("ODR_LINE_IDX"+i).equals(CUT_DATA_CHAR)) {
                                    // Ａレコード返品情報のカットデータ作成
                                    int index = 1;
                                    preparedStatementExInsert.setString(index++, map.get("COMMAND"+i));
                                    preparedStatementExInsert.setString(index++, map.get("STORE"+i));
                                    preparedStatementExInsert.setString(index++, map.get("POS"+i));
                                    preparedStatementExInsert.setString(index++, map.get("TRANS_NO"+i));
                                    preparedStatementExInsert.setString(index++, map.get("CASHIER_ID"+i));
                                    preparedStatementExInsert.setString(index++, map.get("FORMAT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ATYPE"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ODR_LINE_IDX"+i));
                                    preparedStatementExInsert.setString(index++, map.get("SKU"+i));
                                    preparedStatementExInsert.setString(index++, map.get("QTY"+i));
                                    preparedStatementExInsert.setString(index++, map.get("WEIGHT_SOLD"+i));
                                    preparedStatementExInsert.setString(index++, map.get("REG_SELL"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL2"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL"+i));
                                    preparedStatementExInsert.setString(index++, map.get("REG_SELL_WOT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL_WOT2"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL_WOT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("EMP_PURCH"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ITEM_WEIGH"+i));
                                    preparedStatementExInsert.setString(index++, map.get("REGULAR_UNIT_SELL"+i));
                                    preparedStatementExInsert.setString(index++, map.get("GST_TAX"+i));
                                    preparedStatementExInsert.setString(index++, map.get("DISC4_AMT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ITEM_NAME_RECEIPT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ITEM_UOM_RECEIPT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("PRCCHG_NO"+i));
                                    preparedStatementExInsert.setString(index++, map.get("PRCCHG1_NO"+i));
                                    preparedStatementExInsert.setString(index++, map.get("PRCCHG2_NO"+i));
                                    preparedStatementExInsert.setString(index++, map.get("PRCCHG3_NO"+i));
                                    preparedStatementExInsert.setString(index++, map.get("PRIVILEGE_MEM"+i));
                                    preparedStatementExInsert.setString(index++, map.get("OVER_WRITE_FLAG"+i));
                                    preparedStatementExInsert.setString(index++, map.get("HAMPER_ITEM_CD"+i));
                                    preparedStatementExInsert.setString(index++, map.get("SUPERVISOR_ID"+i));
                                    preparedStatementExInsert.setString(index++, map.get("EIGYO_DT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("DATA_SAKUSEI_DT"+i));
                                    preparedStatementExInsert.setString(index++, map.get("ERR_KB"+i));
                                    preparedStatementExInsert.setString(index++, userId);
                                    preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                                    preparedStatementExInsert.setString(index++, userId);
                                    preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());

                                    insertCount = insertCount + preparedStatementExInsert.executeUpdate();
                                }
                            }
                        }

                        // 返品の相殺処理後なのでレシート単位マップをクリアして追加
                        map.clear(); // レシート単位マップをクリア
                        row = 1;
                        map.put("COMMAND"           + row, resultSet.getString("COMMAND"));
                        map.put("STORE"             + row, resultSet.getString("STORE"));
                        map.put("POS"               + row, resultSet.getString("POS"));
                        map.put("TRANS_NO"          + row, resultSet.getString("TRANS_NO"));
                        map.put("CASHIER_ID"        + row, resultSet.getString("CASHIER_ID"));
                        map.put("FORMAT"            + row, resultSet.getString("FORMAT"));
                        map.put("ATYPE"             + row, resultSet.getString("ATYPE"));
                        map.put("ODR_LINE_IDX"      + row, resultSet.getString("ODR_LINE_IDX"));
                        map.put("SKU"               + row, resultSet.getString("SKU"));
                        map.put("QTY"               + row, resultSet.getString("QTY"));
                        map.put("WEIGHT_SOLD"       + row, resultSet.getString("WEIGHT_SOLD"));
                        map.put("REG_SELL"          + row, resultSet.getString("REG_SELL"));
                        map.put("ACTUAL_SELL2"      + row, resultSet.getString("ACTUAL_SELL2"));
                        map.put("ACTUAL_SELL"       + row, resultSet.getString("ACTUAL_SELL"));
                        map.put("REG_SELL_WOT"      + row, resultSet.getString("REG_SELL_WOT"));
                        map.put("ACTUAL_SELL_WOT2"  + row, resultSet.getString("ACTUAL_SELL_WOT2"));
                        map.put("ACTUAL_SELL_WOT"   + row, resultSet.getString("ACTUAL_SELL_WOT"));
                        map.put("EMP_PURCH"         + row, resultSet.getString("EMP_PURCH"));
                        map.put("ITEM_WEIGH"        + row, resultSet.getString("ITEM_WEIGH"));
                        map.put("REGULAR_UNIT_SELL" + row, resultSet.getString("REGULAR_UNIT_SELL"));
                        map.put("GST_TAX"           + row, resultSet.getString("GST_TAX"));
                        map.put("DISC4_AMT"         + row, resultSet.getString("DISC4_AMT"));
                        map.put("ITEM_NAME_RECEIPT" + row, resultSet.getString("ITEM_NAME_RECEIPT"));
                        map.put("ITEM_UOM_RECEIPT"  + row, resultSet.getString("ITEM_UOM_RECEIPT"));
                        map.put("PRCCHG_NO"         + row, resultSet.getString("PRCCHG_NO"));
                        map.put("PRCCHG1_NO"        + row, resultSet.getString("PRCCHG1_NO"));
                        map.put("PRCCHG2_NO"        + row, resultSet.getString("PRCCHG2_NO"));
                        map.put("PRCCHG3_NO"        + row, resultSet.getString("PRCCHG3_NO"));
                        map.put("PRIVILEGE_MEM"     + row, resultSet.getString("PRIVILEGE_MEM"));
                        map.put("OVER_WRITE_FLAG"   + row, resultSet.getString("OVER_WRITE_FLAG"));
                        map.put("HAMPER_ITEM_CD"    + row, resultSet.getString("HAMPER_ITEM_CD"));
                        map.put("SUPERVISOR_ID"     + row, resultSet.getString("SUPERVISOR_ID"));
                        map.put("EIGYO_DT"          + row, resultSet.getString("EIGYO_DT"));
                        map.put("DATA_SAKUSEI_DT"   + row, resultSet.getString("DATA_SAKUSEI_DT"));
                        map.put("ERR_KB"            + row, resultSet.getString("ERR_KB"));
                    }
                }
            }

            // 返品明細の対象明細を検索し減算して相殺
            int existNumber = henpinJohoCut(map, row);
            if (existNumber != 0) {
                invoker.infoLog(strUserId + "　：　返品に対する商品が存在しません。営業日:"+map.get("EIGYO_DT1")+",店舗コード:"+map.get("STORE1")+",レジNo:"+map.get("POS1")+",レシートNo:"+map.get("TRANS_NO1")+",商品コード:"+map.get("SKU"+existNumber));
            } else {
                for (int i = 1; i <= row; i++) {
                    // 返品データおよび相殺されたデータ以外を出力
                    if (!map.get("ODR_LINE_IDX"+i).equals(CUT_DATA_CHAR)) {
                        // Ａレコード返品情報のカットデータ作成
                        int index = 1;
                        preparedStatementExInsert.setString(index++, map.get("COMMAND"+i));
                        preparedStatementExInsert.setString(index++, map.get("STORE"+i));
                        preparedStatementExInsert.setString(index++, map.get("POS"+i));
                        preparedStatementExInsert.setString(index++, map.get("TRANS_NO"+i));
                        preparedStatementExInsert.setString(index++, map.get("CASHIER_ID"+i));
                        preparedStatementExInsert.setString(index++, map.get("FORMAT"+i));
                        preparedStatementExInsert.setString(index++, map.get("ATYPE"+i));
                        preparedStatementExInsert.setString(index++, map.get("ODR_LINE_IDX"+i));
                        preparedStatementExInsert.setString(index++, map.get("SKU"+i));
                        preparedStatementExInsert.setString(index++, map.get("QTY"+i));
                        preparedStatementExInsert.setString(index++, map.get("WEIGHT_SOLD"+i));
                        preparedStatementExInsert.setString(index++, map.get("REG_SELL"+i));
                        preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL2"+i));
                        preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL"+i));
                        preparedStatementExInsert.setString(index++, map.get("REG_SELL_WOT"+i));
                        preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL_WOT2"+i));
                        preparedStatementExInsert.setString(index++, map.get("ACTUAL_SELL_WOT"+i));
                        preparedStatementExInsert.setString(index++, map.get("EMP_PURCH"+i));
                        preparedStatementExInsert.setString(index++, map.get("ITEM_WEIGH"+i));
                        preparedStatementExInsert.setString(index++, map.get("REGULAR_UNIT_SELL"+i));
                        preparedStatementExInsert.setString(index++, map.get("GST_TAX"+i));
                        preparedStatementExInsert.setString(index++, map.get("DISC4_AMT"+i));
                        preparedStatementExInsert.setString(index++, map.get("ITEM_NAME_RECEIPT"+i));
                        preparedStatementExInsert.setString(index++, map.get("ITEM_UOM_RECEIPT"+i));
                        preparedStatementExInsert.setString(index++, map.get("PRCCHG_NO"+i));
                        preparedStatementExInsert.setString(index++, map.get("PRCCHG1_NO"+i));
                        preparedStatementExInsert.setString(index++, map.get("PRCCHG2_NO"+i));
                        preparedStatementExInsert.setString(index++, map.get("PRCCHG3_NO"+i));
                        preparedStatementExInsert.setString(index++, map.get("PRIVILEGE_MEM"+i));
                        preparedStatementExInsert.setString(index++, map.get("OVER_WRITE_FLAG"+i));
                        preparedStatementExInsert.setString(index++, map.get("HAMPER_ITEM_CD"+i));
                        preparedStatementExInsert.setString(index++, map.get("SUPERVISOR_ID"+i));
                        preparedStatementExInsert.setString(index++, map.get("EIGYO_DT"+i));
                        preparedStatementExInsert.setString(index++, map.get("DATA_SAKUSEI_DT"+i));
                        preparedStatementExInsert.setString(index++, map.get("ERR_KB"+i));
                        preparedStatementExInsert.setString(index++, userId);
                        preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
                        preparedStatementExInsert.setString(index++, userId);
                        preparedStatementExInsert.setString(index++, FiResorceUtility.getDBServerTime());
    
                        insertCount = insertCount + preparedStatementExInsert.executeUpdate();
                    }
                }
            }

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件のＡレコード返品情報カットデータを追加しました。");
            invoker.infoLog(strUserId + "　：　" + DT_POS_A_CUT_HENPIN_NAME + "の追加を終了します。");

        } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExSelect != null) {
                    preparedStatementExSelect.close();
                }
                if (preparedStatementExInsert != null) {
                    preparedStatementExInsert.close();
                }
                if (preparedStatementExDelete != null) {
                    preparedStatementExDelete.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
    }

    /**
     * 返品の明細が存在した場合、返品対象の明細を検索し減算して相殺する。
     * @param map レシートの全明細
     * @param row レシートの明細件数
     * @return ステータス(0:正常終了か相殺不要, エラーの明細行:返品に対する商品が存在しない場合)
     */
    private int henpinJohoCut(HashMap<String,String> map, int row) {

        //2017.01.20 J.Endo FIVI対応(#3710) MOD(S)
        ////2017.01.18 J.Endo FIVI対応(#3626) ADD(S)
        //// 購入後返品(コマンドコード"0044"は前明細同じなため先頭の明細で判定)は未処理
        //if (map.get("COMMAND1").equals("0044")) {
        //    return 0;
        //}
        ////2017.01.18 J.Endo FIVI対応(#3626) ADD(E)
        // #3773 URI15001 2017/3/21 N.katou(S)
//        // レシートの明細があり、購入後返品(コマンドコード"0044"は前明細同じなため先頭の明細で判定)は未処理
//        if (row != 0) {
//            if (map.get("COMMAND1").equals("0044")) {
//                return 0;
//            }
//        }
//        //2017.01.20 J.Endo FIVI対応(#3710) MOD(E)
        // レシートの明細があり、コマンドコード"0044"、"0045"は未処理(全明細同じなため先頭の明細で判定)
        if (row != 0) {
            if (map.get("COMMAND1").equals("0044") || map.get("COMMAND1").equals("0045")) {
                return 0;
            }
        }
        // #3773 URI15001 2017/3/21 N.katou(E)
        
        // 返品があった場合の「行番号」項目の再採番を判断するフラグ(false:返品なし, true:返品あり)
        boolean sequenceFlag = false;

        // レシート全明細を定貫フラグにより「数量」項目または「重量」項目のマイナス値を下から検索する
        for (int i = row; i > 0; i--) {
            if (map.get("ITEM_WEIGH"+i).equals("2")) {
                // 重量の場合の処理
                if (Double.parseDouble(map.get("WEIGHT_SOLD"+i)) < 0) {
                    sequenceFlag = true; // 「行番号」項目の再採番を行う(false:返品なし, true:返品あり)
                    boolean matchFlag = false; // 同じ重量の商品が見つからなかった場合のエラー判定フラグ(false:商品なし, true:商品あり)
                    // 重量がマイナス値の明細が存在した場合、その上までの明細の中から同じ売上商品(SKU)および重量を下から検索する
                    for (int k = i-1; k > 0; k--) {
                        // 同じ売上商品(SKU)および重量を検索し減算（相殺）する
                        if (map.get("SKU"+i).equals(map.get("SKU"+k)) &&
                            map.get("ITEM_WEIGH"+k).equals("2") &&
                            0 == (Double.parseDouble(map.get("WEIGHT_SOLD"+k)) + Double.parseDouble(map.get("WEIGHT_SOLD"+i)))) {
                            map.put("WEIGHT_SOLD"+k,      String.format("%09.3f",  Double.parseDouble(map.get("WEIGHT_SOLD"+k))      + Double.parseDouble(map.get("WEIGHT_SOLD"+i))));
                            map.put("ACTUAL_SELL2"+k,     String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL2"+k))     + Double.parseDouble(map.get("ACTUAL_SELL2"+i))));
                            map.put("ACTUAL_SELL"+k,      String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL"+k))      + Double.parseDouble(map.get("ACTUAL_SELL"+i))));
                            map.put("REG_SELL_WOT"+k,     String.format("%023.2f", Double.parseDouble(map.get("REG_SELL_WOT"+k))     + Double.parseDouble(map.get("REG_SELL_WOT"+i))));
                            map.put("ACTUAL_SELL_WOT2"+k, String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL_WOT2"+k)) + Double.parseDouble(map.get("ACTUAL_SELL_WOT2"+i))));
                            map.put("ACTUAL_SELL_WOT"+k,  String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL_WOT"+k))  + Double.parseDouble(map.get("ACTUAL_SELL_WOT"+i))));
                            matchFlag = true;
                            break;
                        }
                    }
                    // 同じ重量の商品が見つからなかった場合はエラー
                    if (matchFlag == false) {
                        return i;
                    }
                }
            } else {
                // 数量の場合の処理
                Double srcQTY = Double.parseDouble(map.get("QTY"+i));
                if (srcQTY < 0) {
                    sequenceFlag = true; // 「行番号」項目の再採番を行う(false:返品なし, true:返品あり)
                    // 数量がマイナス値の明細が存在した場合、その上までの明細の中から同じ売上商品(SKU)を下から検索する
                    for (int k = i-1; k > 0; k--) {
                        // 同じ売上商品(SKU)を検索し、数量が正の数(1以上)であれば減算（相殺）する
                        if (map.get("SKU"+i).equals(map.get("SKU"+k))) {
                            Double dstQTY = Double.parseDouble(map.get("QTY"+k));
                            if(0 <= (dstQTY + srcQTY)) {
                                // 売上数量から返品数量を差し引いても売上数量が余る場合
                                map.put("QTY"+k,              String.format("%09.2f",  dstQTY + srcQTY));
                                map.put("ACTUAL_SELL2"+k,     String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL2"+k))     + Double.parseDouble(map.get("ACTUAL_SELL2"+i))));
                                map.put("ACTUAL_SELL"+k,      String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL"+k))      + Double.parseDouble(map.get("ACTUAL_SELL"+i))));
                                map.put("REG_SELL_WOT"+k,     String.format("%023.2f", Double.parseDouble(map.get("REG_SELL_WOT"+k))     + Double.parseDouble(map.get("REG_SELL_WOT"+i))));
                                map.put("ACTUAL_SELL_WOT2"+k, String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL_WOT2"+k)) + Double.parseDouble(map.get("ACTUAL_SELL_WOT2"+i))));
                                map.put("ACTUAL_SELL_WOT"+k,  String.format("%023.2f", Double.parseDouble(map.get("ACTUAL_SELL_WOT"+k))  + Double.parseDouble(map.get("ACTUAL_SELL_WOT"+i))));
                                srcQTY = 0.00;
                                break; // 売上数量から返品数量を相殺完了
                            } else if (0 < dstQTY) {
                                // 売上数量が1以上で、返品数量を差し引いても返品数量がゼロにならない場合
                                map.put("QTY"+k, "000000.00");
                                srcQTY = dstQTY + srcQTY;
                            } // 売上数量がマイナスは以降に相殺するためスキップ
                        }
                    }
                    // 返品数量が相殺しきれなかった場合はエラー
                    if (srcQTY < 0) {
                        return i;
                    }
                }
            }
        }
        
        // 返品があった場合「行番号」項目を再採番（返品データと相殺され「数量」または「重量」がゼロになったデータ以外）
        if (sequenceFlag == true) {
            int sequenceNumber = 1;
            for (int i = 1; i <= row; i++) {
                if (( map.get("ITEM_WEIGH"+i).equals("2") && Double.parseDouble(map.get("WEIGHT_SOLD"+i)) > 0) ||
                    (!map.get("ITEM_WEIGH"+i).equals("2") && Double.parseDouble(map.get("QTY"+i)) > 0)) {
                    map.put("ODR_LINE_IDX"+i, String.format("%03d", sequenceNumber++));
                } else {
                    map.put("ODR_LINE_IDX"+i, CUT_DATA_CHAR);
                }
            }
        }

        return 0;
    }

    /**
     * ＡレコードOKデータ取得用SQLを取得する
     *
     * @return ＡレコードOKデータ取得用SQL
     */
    private String getArecOKDataSelectSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ATYPE ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,SKU ");
        sql.append("   ,QTY ");
        sql.append("   ,WEIGHT_SOLD ");
        sql.append("   ,REG_SELL ");
        sql.append("   ,ACTUAL_SELL2 ");
        sql.append("   ,ACTUAL_SELL ");
        sql.append("   ,REG_SELL_WOT ");
        sql.append("   ,ACTUAL_SELL_WOT2 ");
        sql.append("   ,ACTUAL_SELL_WOT ");
        sql.append("   ,EMP_PURCH ");
        sql.append("   ,ITEM_WEIGH ");
        sql.append("   ,REGULAR_UNIT_SELL ");
        sql.append("   ,GST_TAX ");
        sql.append("   ,DISC4_AMT ");
        sql.append("   ,ITEM_NAME_RECEIPT ");
        sql.append("   ,ITEM_UOM_RECEIPT ");
        sql.append("   ,PRCCHG_NO ");
        sql.append("   ,PRCCHG1_NO ");
        sql.append("   ,PRCCHG2_NO ");
        sql.append("   ,PRCCHG3_NO ");
        sql.append("   ,PRIVILEGE_MEM ");
        sql.append("   ,OVER_WRITE_FLAG ");
        sql.append("   ,HAMPER_ITEM_CD ");
        sql.append("   ,SUPERVISOR_ID ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("FROM WK_POS_A_ITEM ");
        sql.append("ORDER BY ");
        sql.append("    EIGYO_DT ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,ODR_LINE_IDX ");

        return sql.toString();
    }

    /**
     * Ａレコード返品情報カットデータ作成用SQLを取得する
     *
     * @return Ａレコード返品情報カットデータ作成用SQL
     */
    private String getArecHenpinJohoCutDataInsertSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO DT_POS_A_CUT_HENPIN ( ");
        sql.append("    COMMAND ");
        sql.append("   ,STORE ");
        sql.append("   ,POS ");
        sql.append("   ,TRANS_NO ");
        sql.append("   ,CASHIER_ID ");
        sql.append("   ,FORMAT ");
        sql.append("   ,ATYPE ");
        sql.append("   ,ODR_LINE_IDX ");
        sql.append("   ,SKU ");
        sql.append("   ,QTY ");
        sql.append("   ,WEIGHT_SOLD ");
        sql.append("   ,REG_SELL ");
        sql.append("   ,ACTUAL_SELL2 ");
        sql.append("   ,ACTUAL_SELL ");
        sql.append("   ,REG_SELL_WOT ");
        sql.append("   ,ACTUAL_SELL_WOT2 ");
        sql.append("   ,ACTUAL_SELL_WOT ");
        sql.append("   ,EMP_PURCH ");
        sql.append("   ,ITEM_WEIGH ");
        sql.append("   ,REGULAR_UNIT_SELL ");
        sql.append("   ,GST_TAX ");
        sql.append("   ,DISC4_AMT ");
        sql.append("   ,ITEM_NAME_RECEIPT ");
        sql.append("   ,ITEM_UOM_RECEIPT ");
        sql.append("   ,PRCCHG_NO ");
        sql.append("   ,PRCCHG1_NO ");
        sql.append("   ,PRCCHG2_NO ");
        sql.append("   ,PRCCHG3_NO ");
        sql.append("   ,PRIVILEGE_MEM ");
        sql.append("   ,OVER_WRITE_FLAG ");
        sql.append("   ,HAMPER_ITEM_CD ");
        sql.append("   ,SUPERVISOR_ID ");
        sql.append("   ,EIGYO_DT ");
        sql.append("   ,DATA_SAKUSEI_DT ");
        sql.append("   ,ERR_KB ");
        sql.append("   ,INSERT_USER_ID ");
        sql.append("   ,INSERT_TS ");
        sql.append("   ,UPDATE_USER_ID ");
        sql.append("   ,UPDATE_TS ");
        sql.append("    ) ");
        sql.append("VALUES ( ");
        sql.append("    ? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("   ,? ");
        sql.append("    ) ");

        return sql.toString();
    }

    /**
     * インプットBeanを設定する
     * @param Object input インプットがないためnull
     */
    public void setInputObject(Object input) throws Exception {
        // 処理なし
    }

    /**
     * アウトプットBeanを取得する
     * @return Object (アウトプットがないためnull)
     */
    public Object getOutputObject() throws Exception {
        return null;
    }
}
