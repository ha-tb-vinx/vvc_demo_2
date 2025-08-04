package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.io.IOException;
import java.sql.ResultSet;

import jp.co.vinculumjapan.mdware.uriage.util.FiResorceUtility;
import jp.co.vinculumjapan.swc.commons.dao.DaoException;
import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoTimeOutException;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

/**
 * <p>タイトル: SeisanhyoUrikakeKomokuMaseterCreateDao クラス</p>
 * <p>説明:精算票項目マスタ、売掛項目マスタ追加処理</p>
 * <p>著作権: Copyright 2017</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author VINX
 * @Version 1.00 (2017.03.06) N.Katou
 * @version 1.01 (2017.03.21) N.Katou #4407
 * @see なし
 */
public class SeisanhyoUrikakeKomokuMaseterCreateDao implements DaoIf {

    // バッチ処理名
    private static final String BATCH_NAME = "精算票、売掛項目マスタ登録";

    // バッチID
    private static final String BATCH_ID = "URIB012960";

    // プロパティファイルより法人コード取得
    private static final String COMP_CD = FiResorceUtility.getCompanyCd();

    // システムコントロールよりバッチ日付取得
    private static final String BATCH_DT = FiResorceUtility.getBatchDt();

    /**
     * 精算票、売掛項目マスタ登録
     * @param DaoInvokerIf invoker
     */
    public void executeDataAccess(DaoInvokerIf invoker) throws Exception {

        // ユーザーID
        String strUserID = invoker.getUserId() + " " + BATCH_NAME;
        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を開始します。");

        // オブジェクトを初期化する
        PreparedStatementEx preparedStatementExSel = null;
        PreparedStatementEx preparedStatementExSel2 = null;
        PreparedStatementEx preparedStatementExSel3 = null;
        PreparedStatementEx preparedStatementExSel4 = null;
        PreparedStatementEx preparedStatementExIns = null;
        PreparedStatementEx preparedStatementExIns2 = null;

        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;

        String urikakekomokuCd = "69";
        String seisanhyokomokuCd = "6999";

        int insertCount = 0;
        int iCnt = 1;
        try {

            String dbServerTime = FiResorceUtility.getDBServerTime();

            //支払種別マスタに存在し、精算票項目マスタに存在しないデータ取得
            preparedStatementExSel = invoker.getDataBase().prepareStatement(this.checkSeisanhyoMaseterDaoSql());
            
            iCnt = 1;
            // #4407 URIB012960 2017/3/21 N.katou(S)
            preparedStatementExSel.setString(iCnt++, BATCH_DT); // バッチ日付
            preparedStatementExSel.setString(iCnt++, COMP_CD); // 法人コード
//            preparedStatementExSel.setString(iCnt++, BATCH_DT); // バッチ日付
			// #4407 URIB012960 2017/3/21 N.katou(E)
            rs = preparedStatementExSel.executeQuery();

            if (rs.next()){

                // 精算票項目マスタデータ取得
                preparedStatementExSel2 = invoker.getDataBase().prepareStatement(getSeisanhyoMaseterDaoSql());
                rs2 = preparedStatementExSel2.executeQuery();
                while (rs2.next()) {
                    seisanhyokomokuCd = rs2.getString("SEISANHYO_KOMOKU_CD");
                }
                                
                // 精算票項目マスタ登録
                preparedStatementExIns = invoker.getDataBase().prepareStatement(createSeisanhyoKomokuMaseterDaoSql(dbServerTime));

                int i = 1;
                preparedStatementExIns.setString(i++, COMP_CD); // 法人コード
                preparedStatementExIns.setString(i++, seisanhyokomokuCd); // 精算票項目コード
                preparedStatementExIns.setString(i++, seisanhyokomokuCd); // 精算票項目コード
                preparedStatementExIns.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns.setString(i++, dbServerTime); // システム日付
                preparedStatementExIns.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns.setString(i++, dbServerTime); // システム日付
				// #4407 URIB012960 2017/3/21 N.katou(S)
                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns.setString(i++, COMP_CD); // 法人コード
//                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
				// #4407 URIB012960 2017/3/21 N.katou(E)
                preparedStatementExIns.setString(i++, COMP_CD); // 法人コード
                preparedStatementExIns.setString(i++, seisanhyokomokuCd); // 精算票項目コード
                preparedStatementExIns.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns.setString(i++, dbServerTime); // システム日付
                preparedStatementExIns.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns.setString(i++, dbServerTime); // システム日付
				// #4407 URIB012960 2017/3/21 N.katou(S)
                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns.setString(i++, COMP_CD); // 法人コード
//                preparedStatementExIns.setString(i++, BATCH_DT); // バッチ日付
				// #4407 URIB012960 2017/3/21 N.katou(E)
                insertCount = preparedStatementExIns.executeUpdate();

                // ログ出力
                invoker.infoLog(strUserID + "　：　" + insertCount + "件の精算票項目マスタデータを追加しました。");

            }

            //支払種別マスタに存在し、売掛項目マスタに存在しないデータ取得
            preparedStatementExSel3 = invoker.getDataBase().prepareStatement(this.checkUrikakeMaseterDaoSql());
            
            iCnt = 1;
            // #4407 URIB012960 2017/3/21 N.katou(S)
            preparedStatementExSel3.setString(iCnt++, BATCH_DT); // バッチ日付
            preparedStatementExSel3.setString(iCnt++, COMP_CD); // 法人コード
//            preparedStatementExSel3.setString(iCnt++, BATCH_DT); // バッチ日付
            // #4407 URIB012960 2017/3/21 N.katou(E)
            rs3 = preparedStatementExSel3.executeQuery();

            if (rs3.next()) {

                // 売掛項目マスタデータ取得
                preparedStatementExSel4 = invoker.getDataBase().prepareStatement(getUrikakeMaseterDaoSql());
                rs4 = preparedStatementExSel4.executeQuery();
                while (rs4.next()) {
                    urikakekomokuCd = rs4.getString("URIKAKE_KOMOKU_CD");
                }

                // 売掛項目マスタ登録
                preparedStatementExIns2 = invoker.getDataBase().prepareStatement(createUrikakeKomokuMaseterDaoSql(dbServerTime));

                int i = 1;
                insertCount = 0;
                preparedStatementExIns2.setString(i++, COMP_CD); // 法人コード
                preparedStatementExIns2.setString(i++, urikakekomokuCd); //売掛項目コード
                preparedStatementExIns2.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns2.setString(i++, dbServerTime); // システム日付
                preparedStatementExIns2.setString(i++, BATCH_ID); // バッチID
                preparedStatementExIns2.setString(i++, dbServerTime); // システム日付
                // #4407 URIB012960 2017/3/21 N.katou(S)
                preparedStatementExIns2.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns2.setString(i++, BATCH_DT); // バッチ日付
                preparedStatementExIns2.setString(i++, COMP_CD); // 法人コード
//                preparedStatementExIns2.setString(i++, BATCH_DT); // バッチ日付
				// #4407 URIB012960 2017/3/21 N.katou(E)
                insertCount = preparedStatementExIns2.executeUpdate();

                // ログ出力
                invoker.infoLog(strUserID + "　：　" + insertCount + "件の売掛項目マスタデータを追加しました。");
            }
       } catch (Exception e) {
            invoker.errorLog(e.toString());
            throw e;
        } finally {
            try {
                if (preparedStatementExSel != null) {
                    preparedStatementExSel.close();
                }
                if (preparedStatementExSel2 != null) {
                    preparedStatementExSel2.close();
                }
                if (preparedStatementExSel3 != null) {
                    preparedStatementExSel3.close();
                }
                if (preparedStatementExSel4 != null) {
                    preparedStatementExSel4.close();
                }
                if (preparedStatementExIns != null) {
                    preparedStatementExIns.close();
                }
                if (preparedStatementExIns2 != null) {
                    preparedStatementExIns2.close();
                }
            } catch (Exception ex) {
                invoker.infoLog("preparedStatement Closeエラー");
            }
        }

        // ログ出力
        invoker.infoLog(strUserID + "　：　処理を終了します。");
    }

    /**
     * 精算票項目マスタデータ存在チェックSQL
     * @return
     */
    private String checkSeisanhyoMaseterDaoSql(){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("   RP.SHIHARAI_SYUBETSU_CD ");
        sql.append("   , RP.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   , RP.SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("   , RP.YUKO_START_DT  ");
        sql.append(" FROM ");
        sql.append("   R_PAYMENT RP  ");
        sql.append(" WHERE ");
        sql.append("   NOT EXISTS (  ");
        sql.append("     SELECT ");
        sql.append("       RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("       , RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("       , RSK.TEKIYO_START_DT ");
        sql.append("       , RSK.DELETE_FG  ");
        sql.append("     FROM ");
        sql.append("       R_SEISANHYO_KOMOKU RSK  ");
        sql.append("       INNER JOIN (  ");
        sql.append("         SELECT ");
        sql.append("           SHIHARAI_SYUBETSU_CD ");
        sql.append("           , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("         FROM ");
        sql.append("           R_SEISANHYO_KOMOKU  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("         WHERE ");
        sql.append("           TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         GROUP BY ");
        sql.append("           SHIHARAI_SYUBETSU_CD ");
        sql.append("           , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("       ) RSK_MAX  ");
        sql.append("         ON RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("         AND RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT  ");
        sql.append("     WHERE ");
        sql.append("       RP.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("       AND RP.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("       AND RSK.COMP_CD = ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
//		sql.append("       AND RSK.TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("       AND RSK.DELETE_FG = '0' ");
        sql.append("   )  ");

        return sql.toString();

    }

    /**
     * 売掛項目マスタデータ存在チェックSQL
     * @return
     */
    private String checkUrikakeMaseterDaoSql(){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("   RP.SHIHARAI_SYUBETSU_CD ");
        sql.append("   , RP.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("   , RP.SHIHARAI_SYUBETSU_VN_NA ");
        sql.append("   , RP.YUKO_START_DT  ");
        sql.append(" FROM ");
        sql.append("   R_PAYMENT RP  ");
        sql.append(" WHERE ");
        sql.append("   NOT EXISTS (  ");
        sql.append("     SELECT ");
        sql.append("       RUK.SHIHARAI_SYUBETSU_CD ");
        sql.append("       , RUK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("       , RUK.TEKIYO_START_DT ");
        sql.append("       , RUK.DELETE_FG  ");
        sql.append("     FROM ");
        sql.append("       R_URIKAKE_KOMOKU RUK  ");
        sql.append("       INNER JOIN (  ");
        sql.append("         SELECT ");
        sql.append("           SHIHARAI_SYUBETSU_CD ");
        sql.append("           , SHIHARAI_SYUBETSU_SUB_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("           , SEISANHYO_KOMOKU_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("           , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("         FROM ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("           R_URIKAKE_KOMOKU  ");
        sql.append("         WHERE ");
        sql.append("           TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         GROUP BY ");
        sql.append("           SHIHARAI_SYUBETSU_CD ");
        sql.append("           , SHIHARAI_SYUBETSU_SUB_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("           , SEISANHYO_KOMOKU_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("       ) RUK_MAX  ");
        sql.append("         ON RUK.SHIHARAI_SYUBETSU_CD = RUK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND RUK.SHIHARAI_SYUBETSU_SUB_CD = RUK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("         AND RUK.TEKIYO_START_DT = RUK_MAX.TEKIYO_START_DT  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("         AND RUK.SEISANHYO_KOMOKU_CD = RUK_MAX.SEISANHYO_KOMOKU_CD  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("     WHERE ");
        sql.append("       RP.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("       AND RP.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("       AND RUK.COMP_CD = ?  ");
        sql.append("       AND RUK.SEISANHYO_KOMOKU_CD = '0002'  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("       AND RUK.TEKIYO_START_DT <= ?  ");		
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("       AND RUK.DELETE_FG = '0' ");
        sql.append("   )  ");

        return sql.toString();

    }

    /**
     * 精算票項目マスタデータ取得SQL
     * @return
     */
    private String getSeisanhyoMaseterDaoSql(){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("   SEISANHYO_KOMOKU_CD  ");
        sql.append(" FROM ");
        sql.append("   (  ");
        sql.append("     SELECT ");
        sql.append("       ROWNUM ");
        sql.append("       , SEISANHYO_KOMOKU_CD ");
        sql.append("     FROM ");
        sql.append("       R_SEISANHYO_KOMOKU  ");
        sql.append("     WHERE ");
        sql.append("       SEISANHYO_KOMOKU_CD BETWEEN '7000' AND '7999'  ");
        sql.append("     ORDER BY ");
        sql.append("       SEISANHYO_KOMOKU_CD DESC ");
        sql.append("   )  ");
        sql.append(" WHERE ");
        sql.append("   ROWNUM = 1 ");

        return sql.toString();

    }

    /**
     * 売掛項目マスタデータ取得SQL
     * @return
     */
    private String getUrikakeMaseterDaoSql(){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("   URIKAKE_KOMOKU_CD  ");
        sql.append(" FROM ");
        sql.append("   (  ");
        sql.append("     SELECT ");
        sql.append("       ROWNUM ");
        sql.append("       , URIKAKE_KOMOKU_CD ");
        sql.append("     FROM ");
        sql.append("       R_URIKAKE_KOMOKU  ");
        sql.append("     WHERE ");
        sql.append("       URIKAKE_KOMOKU_CD BETWEEN '70' AND '89'  ");
        sql.append("     ORDER BY ");
        sql.append("       URIKAKE_KOMOKU_CD DESC ");
        sql.append("   )  ");
        sql.append(" WHERE ");
        sql.append("   ROWNUM = 1 ");

        return sql.toString();

    }

    /**
     * 精算票項目マスタ登録SQL
     *
     * @return 精算票項目マスタ登録
     */
    private String createSeisanhyoKomokuMaseterDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT  ");
        sql.append(" INTO R_SEISANHYO_KOMOKU(  ");
        sql.append("   COMP_CD ");
        sql.append("   , SEISANHYO_KOMOKU_CD ");
        sql.append("   , TEKIYO_START_DT ");
        sql.append("   , DELETE_FG ");
        sql.append("   , SEISANHYO_KOMOKU_NA ");
        sql.append("   , SYUSEI_FUKA_FG ");
        sql.append("   , TAISYAKU_KB ");
        sql.append("   , SHIWAKE_TENPO_CD ");
        sql.append("   , KAMOKU_CD ");
        sql.append("   , HOJO_CD ");
        sql.append("   , POS_DATA_COLUMN_NA ");
        sql.append("   , SYOUHIZEI_KB ");
        sql.append("   , TAX_RATE_KB ");
        sql.append("   , ZEI_KB ");
        sql.append("   , SYUKEI_TAISYO_KB ");
        sql.append("   , INSERT_USER_ID ");
        sql.append("   , INSERT_TS ");
        sql.append("   , UPDATE_USER_ID ");
        sql.append("   , UPDATE_TS ");
        sql.append("   , SHIHARAI_SYUBETSU_CD ");
        sql.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append(" )  ");
        sql.append(" SELECT ");
        sql.append("   COMP_CD ");
        sql.append("   , SEISANHYO_KOMOKU_CD ");
        sql.append("   , TEKIYO_START_DT ");
        sql.append("   , DELETE_FG ");
        sql.append("   , SEISANHYO_KOMOKU_NA ");
        sql.append("   , SYUSEI_FUKA_FG ");
        sql.append("   , TAISYAKU_KB ");
        sql.append("   , SHIWAKE_TENPO_CD ");
        sql.append("   , KAMOKU_CD ");
        sql.append("   , HOJO_CD ");
        sql.append("   , POS_DATA_COLUMN_NA ");
        sql.append("   , SYOUHIZEI_KB ");
        sql.append("   , TAX_RATE_KB ");
        sql.append("   , ZEI_KB ");
        sql.append("   , SYUKEI_TAISYO_KB ");
        sql.append("   , INSERT_USER_ID ");
        sql.append("   , INSERT_TS ");
        sql.append("   , UPDATE_USER_ID ");
        sql.append("   , UPDATE_TS ");
        sql.append("   , SHIHARAI_SYUBETSU_CD ");
        sql.append("   , SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append(" FROM ");
        sql.append("   (  ");
        sql.append("     SELECT ");
        sql.append("       ROWNUM ");
        sql.append("       , ? AS COMP_CD ");
        sql.append("       , CASE  ");
        sql.append("         WHEN ROWNUM = 1  ");
        sql.append("         THEN TO_CHAR(TO_NUMBER(? + 1))  ");
        sql.append("         ELSE TO_CHAR(TO_NUMBER(? + ROWNUM * 2 - 1))  ");
        sql.append("         END AS SEISANHYO_KOMOKU_CD ");
        sql.append("       , CASE  ");
  		// #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("         WHEN RP.YUKO_START_DT IS NULL  ");
        sql.append("         WHEN RP.YUKO_START_DT IS NULL  AND RSK.DELETE_FG ='1' ");
        sql.append("         THEN RSK.TEKIYO_START_DT  ");
        sql.append("         WHEN RP.YUKO_START_DT IS NULL   ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         THEN '20160101'  ");
        sql.append("         ELSE RP.YUKO_START_DT  ");
        sql.append("         END AS TEKIYO_START_DT ");
        sql.append("       , '0' AS DELETE_FG ");
        sql.append("       , CASE  ");
        sql.append("         WHEN LENGTHB(RTRIM(RP.SHIHARAI_SYUBETSU_VN_NA) || '(Thiếu)') > 60  ");
        sql.append("         THEN RP.SHIHARAI_SYUBETSU_VN_NA  ");
        sql.append("         ELSE RTRIM(RP.SHIHARAI_SYUBETSU_VN_NA) || '(Thiếu)'  ");
        sql.append("         END AS SEISANHYO_KOMOKU_NA ");
        sql.append("       , '1' AS SYUSEI_FUKA_FG ");
        sql.append("       , '0' AS TAISYAKU_KB ");
        sql.append("       , NULL AS SHIWAKE_TENPO_CD ");
        sql.append("       , NULL AS KAMOKU_CD ");
        sql.append("       , NULL AS HOJO_CD ");
        sql.append("       , NULL AS POS_DATA_COLUMN_NA ");
        sql.append("       , NULL AS SYOUHIZEI_KB ");
        sql.append("       , NULL AS TAX_RATE_KB ");
        sql.append("       , NULL AS ZEI_KB ");
        sql.append("       , '1' AS SYUKEI_TAISYO_KB ");
        sql.append("       , ? AS INSERT_USER_ID ");
        sql.append("       , ? AS INSERT_TS ");
        sql.append("       , ? AS UPDATE_USER_ID ");
        sql.append("       , ? AS UPDATE_TS ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_CD ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("     FROM ");
        sql.append("       R_PAYMENT RP  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("       LEFT JOIN (  ");
        sql.append("         SELECT ");
        sql.append("           RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RSK.TEKIYO_START_DT ");
        sql.append("           , RSK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_SEISANHYO_KOMOKU RSK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        sql.append("               R_SEISANHYO_KOMOKU  ");
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ) RSK_MAX  ");
        sql.append("             ON RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT ");
        sql.append("       ) RSK  ");
        sql.append("         ON RP.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND RP.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD  ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("     WHERE ");
        sql.append("       NOT EXISTS (  ");
        sql.append("         SELECT ");
        sql.append("           RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RSK.TEKIYO_START_DT ");
        sql.append("           , RSK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_SEISANHYO_KOMOKU RSK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        sql.append("               R_SEISANHYO_KOMOKU  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ) RSK_MAX  ");
        sql.append("             ON RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT  ");
        sql.append("         WHERE ");
        sql.append("           RP.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("           AND RP.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("           AND RSK.COMP_CD = ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
//		  sql.append("           AND RSK.TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("           AND RSK.DELETE_FG = '0' ");
        sql.append("       )  ");
        sql.append("     UNION ALL  ");
        sql.append("     SELECT ");
        sql.append("       ROWNUM ");
        sql.append("       , ? AS COMP_CD ");
        sql.append("       , TO_CHAR(TO_NUMBER(? + ROWNUM * 2)) AS SEISANHYO_KOMOKU_CD ");
        sql.append("       , CASE  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("         WHEN RP.YUKO_START_DT IS NULL  ");        
        sql.append("         WHEN RP.YUKO_START_DT IS NULL  AND RSK.DELETE_FG ='1' ");
        sql.append("         THEN RSK.TEKIYO_START_DT  ");
        sql.append("         WHEN RP.YUKO_START_DT IS NULL   ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         THEN '20160101'  ");
        sql.append("         ELSE RP.YUKO_START_DT  ");
        sql.append("         END AS TEKIYO_START_DT ");
        sql.append("       , '0' AS DELETE_FG ");
        sql.append("       , CASE  ");
        sql.append("         WHEN LENGTHB(RTRIM(RP.SHIHARAI_SYUBETSU_VN_NA) || '(Dư)') > 60  ");
        sql.append("         THEN RP.SHIHARAI_SYUBETSU_VN_NA  ");
        sql.append("         ELSE RTRIM(RP.SHIHARAI_SYUBETSU_VN_NA) || '(Dư)'  ");
        sql.append("         END AS SEISANHYO_KOMOKU_NA ");
        sql.append("       , '1' AS SYUSEI_FUKA_FG ");
        sql.append("       , '1' AS TAISYAKU_KB ");
        sql.append("       , NULL AS SHIWAKE_TENPO_CD ");
        sql.append("       , NULL AS KAMOKU_CD ");
        sql.append("       , NULL AS HOJO_CD ");
        sql.append("       , NULL AS POS_DATA_COLUMN_NA ");
        sql.append("       , NULL AS SYOUHIZEI_KB ");
        sql.append("       , NULL AS TAX_RATE_KB ");
        sql.append("       , NULL AS ZEI_KB ");
        sql.append("       , '1' AS SYUKEI_TAISYO_KB ");
        sql.append("       , ? AS INSERT_USER_ID ");
        sql.append("       , ? AS INSERT_TS ");
        sql.append("       , ? AS UPDATE_USER_ID ");
        sql.append("       , ? AS UPDATE_TS ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_CD ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("     FROM ");
        sql.append("       R_PAYMENT RP  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("     LEFT JOIN (  ");
        sql.append("         SELECT ");
        sql.append("           RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RSK.TEKIYO_START_DT ");
        sql.append("           , RSK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_SEISANHYO_KOMOKU RSK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        sql.append("               R_SEISANHYO_KOMOKU  ");
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ) RSK_MAX  ");
        sql.append("             ON RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT ");
        sql.append("       ) RSK  ");
        sql.append("         ON RP.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND RP.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD  ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("     WHERE ");
        sql.append("       NOT EXISTS (  ");
        sql.append("         SELECT ");
        sql.append("           RSK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RSK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RSK.TEKIYO_START_DT ");
        sql.append("           , RSK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_SEISANHYO_KOMOKU RSK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        sql.append("               R_SEISANHYO_KOMOKU  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           ) RSK_MAX  ");
        sql.append("             ON RSK.SHIHARAI_SYUBETSU_CD = RSK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RSK.SHIHARAI_SYUBETSU_SUB_CD = RSK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RSK.TEKIYO_START_DT = RSK_MAX.TEKIYO_START_DT  ");
        sql.append("         WHERE ");
        sql.append("           RP.SHIHARAI_SYUBETSU_CD = RSK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("           AND RP.SHIHARAI_SYUBETSU_SUB_CD = RSK.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("           AND RSK.COMP_CD = ?  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("           AND RSK.TEKIYO_START_DT <= ?  ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("           AND RSK.DELETE_FG = '0' ");
        sql.append("       ) ");
        sql.append("   )  ");

        return sql.toString();
    }

    /**
     * 売掛項目マスタ登録SQL
     *
     * @return 売掛項目マスタ登録
     */
    private String createUrikakeKomokuMaseterDaoSql(String dbServerTime) {
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT  ");
        sql.append(" INTO R_URIKAKE_KOMOKU(  ");
        sql.append("   COMP_CD ");
        sql.append("   , SEISANHYO_KOMOKU_CD ");
        sql.append("   , URIKAKE_KOMOKU_CD ");
        sql.append("   , TEKIYO_START_DT ");
        sql.append("   , DELETE_FG ");
        sql.append("   , URIKAKE_KOMOKU_NA ");
        sql.append("   , SYUSEI_FUKA_FG ");
        sql.append("   , TAISYAKU_KB ");
        sql.append("   , SHIWAKE_TENPO_CD ");
        sql.append("   , KAMOKU_CD ");
        sql.append("   , HOJO_CD ");
        sql.append("   , POS_DATA_COLUMN_NA ");
        sql.append("   , SYOUHIZEI_KB ");
        sql.append("   , TAX_RATE_KB ");
        sql.append("   , ZEI_KB ");
        sql.append("   , INSERT_USER_ID ");
        sql.append("   , INSERT_TS ");
        sql.append("   , UPDATE_USER_ID ");
        sql.append("   , UPDATE_TS ");
        sql.append("   , SHIHARAI_SYUBETSU_CD ");
        sql.append("   , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append(" )  ");
        sql.append(" SELECT ");
        sql.append("   COMP_CD ");
        sql.append("   , SEISANHYO_KOMOKU_CD ");
        sql.append("   , URIKAKE_KOMOKU_CD ");
        sql.append("   , TEKIYO_START_DT ");
        sql.append("   , DELETE_FG ");
        sql.append("   , URIKAKE_KOMOKU_NA ");
        sql.append("   , SYUSEI_FUKA_FG ");
        sql.append("   , TAISYAKU_KB ");
        sql.append("   , SHIWAKE_TENPO_CD ");
        sql.append("   , KAMOKU_CD ");
        sql.append("   , HOJO_CD ");
        sql.append("   , POS_DATA_COLUMN_NA ");
        sql.append("   , SYOUHIZEI_KB ");
        sql.append("   , TAX_RATE_KB ");
        sql.append("   , ZEI_KB ");
        sql.append("   , INSERT_USER_ID ");
        sql.append("   , INSERT_TS ");
        sql.append("   , UPDATE_USER_ID ");
        sql.append("   , UPDATE_TS ");
        sql.append("   , SHIHARAI_SYUBETSU_CD ");
        sql.append("   , SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append(" FROM ");
        sql.append("   (  ");
        sql.append("     SELECT ");
        sql.append("       ROWNUM ");
        sql.append("       , ? AS COMP_CD ");
        sql.append("       , '0002' AS SEISANHYO_KOMOKU_CD ");
        sql.append("       , TO_CHAR(TO_NUMBER(? + ROWNUM)) AS URIKAKE_KOMOKU_CD ");
        sql.append("       , CASE  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("         WHEN RP.YUKO_START_DT IS NULL  ");        
        sql.append("         WHEN RP.YUKO_START_DT IS NULL  AND RUK.DELETE_FG ='1' ");
        sql.append("         THEN RUK.TEKIYO_START_DT  ");
        sql.append("         WHEN RP.YUKO_START_DT IS NULL   ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         THEN '20160101'  ");
        sql.append("         ELSE RP.YUKO_START_DT  ");
        sql.append("         END AS TEKIYO_START_DT ");
        sql.append("       , '0' AS DELETE_FG ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_SUB_NA  AS URIKAKE_KOMOKU_NA");
        sql.append("       , '1' AS SYUSEI_FUKA_FG ");
        sql.append("       , '0' AS TAISYAKU_KB ");
        sql.append("       , NULL AS SHIWAKE_TENPO_CD ");
        sql.append("       , NULL AS KAMOKU_CD ");
        sql.append("       , NULL AS HOJO_CD ");
        sql.append("       , NULL AS POS_DATA_COLUMN_NA ");
        sql.append("       , '00' AS SYOUHIZEI_KB ");
        sql.append("       , '0' AS TAX_RATE_KB ");
        sql.append("       , '3' AS ZEI_KB ");
        sql.append("       , ? AS INSERT_USER_ID ");
        sql.append("       , ? AS INSERT_TS ");
        sql.append("       , ? AS UPDATE_USER_ID ");
        sql.append("       , ? AS UPDATE_TS ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_CD ");
        sql.append("       , RP.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("     FROM ");
        sql.append("       R_PAYMENT RP  ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("     LEFT JOIN (  ");
        sql.append("         SELECT ");
        sql.append("           RUK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RUK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RUK.TEKIYO_START_DT ");
        sql.append("           , RUK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_URIKAKE_KOMOKU RUK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , SEISANHYO_KOMOKU_CD ");
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        sql.append("               R_URIKAKE_KOMOKU  ");
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
        sql.append("               AND SEISANHYO_KOMOKU_CD = '0002'  ");
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("               , SEISANHYO_KOMOKU_CD ");
        sql.append("           ) RUK_MAX  ");
        sql.append("             ON RUK.SHIHARAI_SYUBETSU_CD = RUK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RUK.SHIHARAI_SYUBETSU_SUB_CD = RUK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RUK.TEKIYO_START_DT = RUK_MAX.TEKIYO_START_DT ");
        sql.append("             AND RUK.SEISANHYO_KOMOKU_CD = RUK_MAX.SEISANHYO_KOMOKU_CD  ");
        sql.append("       ) RUK  ");
        sql.append("         ON RP.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("         AND RP.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD  ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("     WHERE ");
        sql.append("       NOT EXISTS (  ");
        sql.append("         SELECT ");
        sql.append("           RUK.SHIHARAI_SYUBETSU_CD ");
        sql.append("           , RUK.SHIHARAI_SYUBETSU_SUB_CD ");
        sql.append("           , RUK.TEKIYO_START_DT ");
        sql.append("           , RUK.DELETE_FG  ");
        sql.append("         FROM ");
        sql.append("           R_URIKAKE_KOMOKU RUK  ");
        sql.append("           INNER JOIN (  ");
        sql.append("             SELECT ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("               , SEISANHYO_KOMOKU_CD ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("               , MAX(TEKIYO_START_DT) AS TEKIYO_START_DT  ");
        sql.append("             FROM ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("               R_URIKAKE_KOMOKU  ");
        sql.append("             WHERE ");
        sql.append("               TEKIYO_START_DT <= ?  ");
        sql.append("               AND SEISANHYO_KOMOKU_CD = '0002'  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("             GROUP BY ");
        sql.append("               SHIHARAI_SYUBETSU_CD ");
        sql.append("               , SHIHARAI_SYUBETSU_SUB_CD ");
        // #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("               , SEISANHYO_KOMOKU_CD ");
        // #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("           ) RUK_MAX  ");
        sql.append("             ON RUK.SHIHARAI_SYUBETSU_CD = RUK_MAX.SHIHARAI_SYUBETSU_CD  ");
        sql.append("             AND RUK.SHIHARAI_SYUBETSU_SUB_CD = RUK_MAX.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("             AND RUK.TEKIYO_START_DT = RUK_MAX.TEKIYO_START_DT  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
        sql.append("             AND RUK.SEISANHYO_KOMOKU_CD = RUK_MAX.SEISANHYO_KOMOKU_CD  ");
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("         WHERE ");
        sql.append("           RP.SHIHARAI_SYUBETSU_CD = RUK.SHIHARAI_SYUBETSU_CD  ");
        sql.append("           AND RP.SHIHARAI_SYUBETSU_SUB_CD = RUK.SHIHARAI_SYUBETSU_SUB_CD  ");
        sql.append("           AND RUK.COMP_CD = ?  ");
        sql.append("           AND RUK.SEISANHYO_KOMOKU_CD = '0002'  ");
		// #4407 URIB012960 2017/3/21 N.katou(S)
//        sql.append("           AND RUK.TEKIYO_START_DT <= ?  ");		
		// #4407 URIB012960 2017/3/21 N.katou(E)
        sql.append("           AND RUK.DELETE_FG = '0' ");
        sql.append("       ) ");
        sql.append("   )  ");

        return sql.toString();
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
