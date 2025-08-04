package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;

import jp.co.vinculumjapan.mdware.uriage.constant.FiLogConstant;
import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;


/**
 * <p>タイトル: RegiTorihikiSeisanTorikomiDao クラス</p>
 * <p>説明　: POS実績取込処理(レジ取引精算)</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @Version 3.00 (2013.09.17) T.Ooshiro [CUS00057] ランドローム様対応 POSインターフェイス仕様変更対応
 * @Version 3.01 (2013.10.21) T.Ooshiro [CUS00057] POSインターフェイス仕様変更対応 バックアップ対応
 * @Version 3.02 (2016/05/12) S_MDware-G_FIVIマート様開発 VINX k.Hyo
 * @Version 3.03 (2016.05.10) VINX k.Hyo FIVI対応 店舗コード4桁→6桁修正.
 * @Version 3.04 (2016.06.02) VINX to #1508対応 
 * @Version 3.05 (2017.02.16) VINX J.Endo FIVI対応 サブ支払種別コード7桁化 #4013対応 
 * @Version 3.06 (2017.08.31) VINX N.Katou  FIVI対応 #5840
 * 
 */
public class RegiTorihikiSeisanTorikomiDao implements DaoIf /*extends TorikomiDaoSuper*/ {

    /** バッチ処理名 */
    //private static final String DAO_NAME = "POS実績取込処理(レジ取引精算)";
    /** 登録先テーブル名称 */
    //private static final String INS_TABLE_NAME = "TMPレジ取引精算データ";
    /** レジ別取引精算取込ファイル明細固定項目数 */
    //private static final int REGI_TORIHIKI_DETAIL_STATIC_LIST_LENGTH = 2;
    /** レジ別取引精算取込ファイル明細ループ項目数 */
    //private static final int REGI_TORIHIKI_DETAIL_LOOP_LIST_LENGTH = 6;

    // 追加SQL文用定数
    //private static final String INS_SQL = "insertTmpRegiTorihikiSeisan";
    // 削除SQL文用定数
    //private static final String DEL_SQL = "deleteTmpRegiTorihikiSeisan";

    /** インプットBean */
    //private RegiTorihikiSeisanTorikomiDaoInputBean inputBean = null;

    /**
     * 明細レコードからデータを登録する
     * 
     * @param preparedStatementExIns インサート用preparedStatement
     * @param csvlist 明細レコード(コンマ分割後配列)
     * @param tenpoCd 店コード
     * @param eigyoDt 営業日
     * @param dbServerTime DBサーバ時刻
     * @return 登録データ件数
     * @throws SQLException
     */
    //protected int insertData(PreparedStatementEx preparedStatementExIns, String[] csvlist, String tenpoCd, String eigyoDt, String dbServerTime) throws SQLException {

    //    int dataCnt = (csvlist.length - REGI_TORIHIKI_DETAIL_STATIC_LIST_LENGTH) / REGI_TORIHIKI_DETAIL_LOOP_LIST_LENGTH;
        
    //    for (int i = 0; i < dataCnt; i++) {
    //        preparedStatementExIns.setString(1, COMP_CD);
    //        preparedStatementExIns.setString(2, eigyoDt);
    //        preparedStatementExIns.setString(3, tenpoCd);
    //        preparedStatementExIns.setString(4, csvlist[0]);
    //        preparedStatementExIns.setString(5, csvlist[1]);
    //        int baseIndex = i * REGI_TORIHIKI_DETAIL_LOOP_LIST_LENGTH + REGI_TORIHIKI_DETAIL_STATIC_LIST_LENGTH;
    //        preparedStatementExIns.setString(6, csvlist[baseIndex + 0]);
    //        preparedStatementExIns.setString(7, csvlist[baseIndex + 1]);
    //        preparedStatementExIns.setString(8, csvlist[baseIndex + 2]);
    //        preparedStatementExIns.setString(9, csvlist[baseIndex + 3]);
    //        preparedStatementExIns.setString(10, csvlist[baseIndex + 4]);
    //        preparedStatementExIns.setString(11, csvlist[baseIndex + 5]);
    //        preparedStatementExIns.setString(12, userId);
    //        preparedStatementExIns.setString(13, dbServerTime);
    //        preparedStatementExIns.setString(14, userId);
    //        preparedStatementExIns.setString(15, dbServerTime);

            // 登録実行
    //        preparedStatementExIns.executeUpdate();
    //    }

    //    return dataCnt;
    //}

    /**
     * 明細行判定
     * 
     * @param recordColumnCount レコードカラム桁数
     * @return true：明細行である、false：明細行ではない
     */
    //protected boolean isDetailRecord(int recordColumnCount) {
    //    return ((recordColumnCount - REGI_TORIHIKI_DETAIL_STATIC_LIST_LENGTH) % REGI_TORIHIKI_DETAIL_LOOP_LIST_LENGTH) == 0;
    //}

    /**
     * アウトプットBeanを取得する
     * 
     * @return Object (アウトプットがないためnull)
     */
    //public Object getOutputObject() throws Exception {
    //    return null;
    //}

    /**
     * インプットBeanを設定する
     * 
     * @param Object input RegiTorihikiSeisanTorikomiDaoInputBean型オブジェクト
     */
    //public void setInputObject(Object input) throws Exception {
    //    inputBean = (RegiTorihikiSeisanTorikomiDaoInputBean) input;

    //}

    /**
     * @param args
     */
    //public static void main(String[] args) {
    //    try {
    //        DaoIf dao = new RegiTorihikiSeisanTorikomiDao();
    //        new StandAloneDaoInvoker("mm").InvokeDao(dao);
    //        System.out.println(dao.getOutputObject());
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    } catch (DaoTimeOutException e) {
    //        e.printStackTrace();
    //    } catch (DaoException e) {
    //        e.printStackTrace();
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}

    /**
     * JOBIDを取得します。
     * @return バッチ処理名
     */
    //String getJobId() {
    //    return inputBean.getJobId();
    //}

    /**
     * バッチ処理名を取得します。
     * @return バッチ処理名
     */
    //String getDaoName() {
    //    return DAO_NAME;
    //}

    /**
     * 登録先テーブル名称を取得します。
     * @return 登録先テーブル名称
     */
    //String getInsTableName() {
    //    return INS_TABLE_NAME;
    //}

    /**
     * 取込ファイルを取得します。
     * @return 取込ファイル
     */
    //String getFileName() {
    //    return inputBean.getFileId();
    //}

    /**
     * 追加SQL文を取得します。
     * @return 追加SQL文
     */
    //String getInsSql() {
    //    return INS_SQL;
    //}

    /**
     * 削除SQL文を取得します。
     * @return 削除SQL文
     */
    //String getDelSql() {
    //    return DEL_SQL;
    //}

    /**
     * バックアップフォルダPIDを取得します。
     * @return バックアップフォルダPID
     */
    //String getBackUpDirPID() {
    //    return inputBean.getBackUpDirPID();
    //}

    /**
     * SEITO社POSDataの取り込み処理
     *
     * @param invoker データベースアクセスオブジェクトの為のinvoker
     * @param jobId ジョブID
     * @param dirPath ディレクトリパス
     * @param mainFileId ファイル名
     * @throws Exception 例外
     */
   // protected void seitoPosTorikomi(DaoInvokerIf invoker, String jobId, String dirPath, String mainFileId) throws Exception {
        
        // ユーザID
        //String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + daoName;
        
        // ログ出力
        //invoker.infoLog(strUserId + "　：　" + daoName + "を開始します。");
        
        // ディレクトリを取得する
        //File directory = new File(dirPath);
        //if (!directory.isDirectory()) {
        //    invoker.errorLog("ディレクトリを正しく指定して下さい。");
        //    throw new IllegalStateException("ディレクトリを正しく指定して下さい。");
        //}
        
        // ファイルをリスト化
        //File readFile = new File(directory + "/" + fileName);
        //if (!readFile.exists()) {
        //    invoker.errorLog(dirPath + "にファイルが存在しません");
        //} else {
            
        //    BufferedReader file = null;
            
        //    int insertCount = 0;
            
            // オブジェクトを初期化する
        //    PreparedStatementEx preparedStatementExIns = null;
        //    PreparedStatementEx preparedStatementDelete = null;
            
        //    try {
                
                // SQLを取得し、TMPテーブルを削除する
        //        preparedStatementDelete = invoker.getDataBase().prepareStatement(delSql);
        //        preparedStatementDelete.execute();
                
                // SQLを取得し、パラメータを条件にバインドする
        //        preparedStatementExIns = invoker.getDataBase().prepareStatement(insSql);
                
                // ファイル読み込み
        //        file = new BufferedReader(new FileReader(readFile));
                // CSVの取得用
        //        String record = null;
        //        String tenpoCd = null;
                
        //        String eigyoDt = null;
                
        //        String batchDt = FiResorceUtility.getDBServerTime();
                
                // Type=Aのレコードのみを保持
        //        List<String> recordList = new ArrayList<String>();
                // キャッシャーIDを保持（重複除去）
        //        Set<String> cashierSet = new TreeSet<String>();
                
                // CSVファイルを1行リストに入れる
        //        while ((record = file.readLine()) != null) {
        //            char recordType = record.charAt(24);
        //            if (recordType == 'A') {
                        // Headerから店舗コード取得
        //                if (tenpoCd == null) {
        //                    tenpoCd = record.substring(4, 8);
        //                }
        //                recordList.add(record);
                        // キャッシャーIDをセットにセット
                        //cashierSet.add(record.substring(190, 197));
        //                cashierSet.add("0001");
        //            } else if (recordType == 'B' && eigyoDt == null) {
                        // 営業日を取得
        //                eigyoDt = TorikomiUtility.getEigyoDt(record);
        //            }
        //       }
                
        //        Iterator<String> ite = cashierSet.iterator();
        //        while (ite.hasNext()) {
                    // 責任者コード（CashierId）
        //            String cashierId = ite.next();
                    // SUM(客数)
        //            Set<String> transactionSet = new TreeSet<String>();
                    // SUM(点数/回数)
        //            int tensu = 0;
                    // SUM(金額)
        //            BigDecimal amountVl = new BigDecimal(0);
                    // SUM(値引金額)
        //            BigDecimal amountDiscountVl = new BigDecimal(0);
                    
        //            for (String rec_a : recordList) {
                        //String recCashierId = rec_a.substring(190, 197);
        //                String recTorihikiCd = "0001";
                        
        //                if (cashierId.equals(recTorihikiCd)) {
                            
                            // 売上点数
        //                    BigDecimal uriageQtBig = new BigDecimal(rec_a.substring(43, 52));
                            // 計量器の場合、小数点が入っている場合があるので、四捨五入して単位をそろえる
        //                    uriageQtBig = uriageQtBig.setScale(0, BigDecimal.ROUND_HALF_UP);
                            // 売上点数のインクリメント
        //                    tensu += uriageQtBig.intValue();
                            
                            // 客数を見る為にTransactionNumber（レシートNO？）をセットにセット
        //                    transactionSet.add(rec_a.substring(16, 23));
                            
                            // 値引前税込売価 - 値引後税込売価 = 値引額
        //                    BigDecimal mae = new BigDecimal(rec_a.substring(52, 75));
        //                    BigDecimal go = new BigDecimal(rec_a.substring(75, 98));
                            
                            // 金額を足し込み                            
        //                    amountVl = amountVl.add(go);
                            
                            // 値引き額を足し込み
        //                    BigDecimal discountVl = mae.subtract(go);
        //                    amountDiscountVl = amountDiscountVl.add(discountVl);
                            
        //                }
        //            }
                    
                    // 法人コード
        //            preparedStatementExIns.setString(1, COMP_CD);
                    // 営業日
        //            preparedStatementExIns.setString(2, TorikomiUtility.createEigyobi(eigyoDt));
                    // 店舗コード
        //            preparedStatementExIns.setString(3, "00".concat(tenpoCd));
                    // フロアNo.
        //            preparedStatementExIns.setString(4, null);
                    // REGI_NO
        //            preparedStatementExIns.setString(5, null);
                    // 取引コード
        //            preparedStatementExIns.setString(6, "0001");
                    // タイプ
        //            preparedStatementExIns.setString(7, null);
                    // 客数
        //            preparedStatementExIns.setString(8, TorikomiUtility.addZeroPrefix(String.valueOf(transactionSet.size()), 16));
                    // 点数
        //            preparedStatementExIns.setString(9, TorikomiUtility.addZeroPrefix(String.valueOf(tensu), 16));
                    // 金額
        //            preparedStatementExIns.setString(10, TorikomiUtility.addZeroPrefix(amountVl.toString(), 19));
                    // 値引き額
        //            preparedStatementExIns.setString(11, TorikomiUtility.addZeroPrefix(amountDiscountVl.toString(), 19));
        //            preparedStatementExIns.setString(12, userId);
        //            preparedStatementExIns.setString(13, batchDt);
        //            preparedStatementExIns.setString(14, userId);
        //            preparedStatementExIns.setString(15, batchDt);
                    
                    // 登録実行
        //            preparedStatementExIns.executeUpdate();
                    
        //            insertCount++;
        //        }
                
        //        file.close();
                
                // ログ出力
        //        invoker.infoLog(strUserId + "　：　" + insTableName + "へのデータ取込処理を終了します。取込件数は" + insertCount + "件です。");
                
        //    } catch (Exception e) {
        //        invoker.errorLog(e.toString());
        //        throw e;
        //    } finally {
        //        try {
        //            if (file != null) {
        //                file.close();
        //            }
        //        } catch (Exception e) {
        //            invoker.infoLog("FILE Closeエラー");
        //        }
        //        try {
        //            if (preparedStatementExIns != null) {
        //                preparedStatementExIns.close();
        //            }
                    
        //            if (preparedStatementDelete != null) {
        //                preparedStatementDelete.close();
        //            }
                    
        //        } catch (Exception e) {
        //            invoker.infoLog("preparedStatement Closeエラー");
        //        }
        //    }
            
       // }
        
       // invoker.infoLog(strUserId + "　：　" + daoName + "を終了します。");
   // }
    
    // ここまでは既存ソース
    
    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();
    
    /** バッチ処理名 */
    private static final String DAO_NAME = "POS実績取込処理（レジ別取引精算）";
    /** 登録先テーブル名称 */
    private static final String INS_TABLE_NAME = "TMPレジ別取引精算データ";

    /** 削除SQL文 */
    private static final String DEL_SQL = "TRUNCATE TABLE TMP_REGI_TORIHIKI_SEISAN";
    /**
     * 処理実行
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザID(DB登録用)
        String userId = invoker.getUserId();
        // ユーザID
        String strUserId = invoker.getUserId() + FiLogConstant.FI_LOG_ONE_BYTE_SPACE + DAO_NAME;
        //
        // ログ出力
        invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementDelete = null;

        int insertCount = 0;
        try {

            // ワークテーブルのデータを削除する
            preparedStatementDelete = invoker.getDataBase().prepareStatement(DEL_SQL);
            preparedStatementDelete.execute();

            String dbServerTime = FiResorceUtility.getDBServerTime();
            // TMPレジ別取引精算データから有効レジ別取引精算ワークへ登録する
            preparedStatementExIns = invoker.getDataBase().prepareStatement(getTmpRegiTorihikiSeisanSql());

            preparedStatementExIns.setString(1, COMP_CD);
            preparedStatementExIns.setString(2, userId);
            preparedStatementExIns.setString(3, dbServerTime);
            preparedStatementExIns.setString(4, userId);
            preparedStatementExIns.setString(5, dbServerTime);

            insertCount = preparedStatementExIns.executeUpdate();

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + insertCount + "件の" + INS_TABLE_NAME + "を追加しました。");

            // ログ出力
            invoker.infoLog(strUserId + "　：　" + DAO_NAME + "を終了します。");
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
            try {
                if (preparedStatementDelete != null) {
                    preparedStatementDelete.close();
                }

            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }
    }
    
    /**
     * POSジャーナル（C_Payment）一時データからTMPレジ別取引精算データへ登録するSQLを取得する
     *
     * @return TMPレジ別取引精算データ登録クエリー
     */
    private String getTmpRegiTorihikiSeisanSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT /*+ APPEND */ INTO TMP_REGI_TORIHIKI_SEISAN( ");
        sql.append("     COMP_CD ");
        sql.append("    ,EIGYO_DT ");
        sql.append("    ,TENPO_CD ");
        sql.append("    ,FLOAR_NO ");
        sql.append("    ,REGI_NO ");
        sql.append("    ,TORIHIKI_CD ");
        sql.append("    ,TYPE_KB ");
        sql.append("    ,KYAKU_QT ");
        sql.append("    ,TEN_KAISUU_QT ");
        sql.append("    ,KINGAKU_VL ");
        sql.append("    ,NEBIKI_VL ");
        sql.append("    ,INSERT_USER_ID ");
        sql.append("    ,INSERT_TS ");
        sql.append("    ,UPDATE_USER_ID ");
        sql.append("    ,UPDATE_TS ");
        sql.append("    ,SHIHARAI_SYUBETSU_CD ");
        sql.append("    ,SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("    ) ");
        sql.append("SELECT  ");
        sql.append("     ? ");
        // 2016/06/02 VINX to #1508対応 （S)
//        sql.append("    ,TO_CHAR(TO_DATE(SUBSTR(EIGYO_DT,1,8)),'YYYY/MM/DD') ");
        sql.append("    ,SUBSTR(EIGYO_DT,1,4)||'/'||SUBSTR(EIGYO_DT,5,2)||'/'||SUBSTR(EIGYO_DT,7,2) ");
        // 2016/06/02 VINX to #1508対応 （E)
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（S)
        sql.append("    ,LPAD(STORE,6,'0') ");
        //sql.append("    STORE, ");
        // 2016/05/10 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)
        sql.append("    ,NULL ");
        sql.append("    ,NULL ");
        sql.append("    ,CASE ");
        // 2017/02/16 VINX J.Endo #4013対応 MOD(S)
        //sql.append("        WHEN PYMT_TYPE = 'CSH' AND PYMT_TYPE2 = 'CSH00' THEN '0001' ");
        sql.append("        WHEN PYMT_TYPE = 'CSH' AND PYMT_TYPE2 = 'CSH0000' THEN '0001' ");
        // 2017/02/16 VINX J.Endo #4013対応 MOD(E)
        sql.append("        ELSE '0002' ");
        sql.append("    END AS TORIHIKI_CD ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,'0' ");
        sql.append("    ,SUM(ACTUAL_AMT) ");
        sql.append("    ,'0' ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,? ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");
        sql.append("FROM ");
        // 2017/08/31 VINX N.Katou #5840 (S)
//        sql.append("    TMP_POS_C_PAYMENT ");
        sql.append("    WK_POS_C_PAYMENT ");
        sql.append("WHERE ");
        sql.append("    ERR_KB = '0' ");
        // 2017/08/31 VINX N.Katou #5840 (E)
        sql.append("GROUP BY ");
        sql.append("    EIGYO_DT");
        sql.append("    ,STORE ");
        sql.append("    ,PYMT_TYPE ");
        sql.append("    ,PYMT_TYPE2 ");

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
            DaoIf dao = new RegiTorihikiSeisanMstJohoSyutokuDao();
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
    // 2016/05/12 VINX k.Hyo S_MDware-G_FIVIマート様開発（E)

}
