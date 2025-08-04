package mdware.master.batch.process.mbB1;

import java.sql.SQLException;

import org.apache.log4j.Level;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;

/**
 * <p>タイトル:廃番実施処理</p>
 * <p>説明:商品廃番にる関連マスタ削除処理を行う</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/05/12 Urano MM様対応（MB14-01-01をカスタマイズ）<BR>
 * @version 1.01 2017/04/17 S.Takayama VINX #4551対応
 * @author hara
 */

public class MBB10101_HaibanJissi {

	private boolean closeDb = false;

	//DataBaseクラス
	private MasterDataBase db = null;

	//バッチ日付
	private String batchDt = RMSTDATEUtil.getBatDateDt();

	//batchID
	private String batchID;
	
	//システム日付
	private String system_date = null;
	
	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	
	private static final String BATCH_ID = "MBB1-01-01";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBB10101_HaibanJissi(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBB10101_HaibanJissi() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		//各トランバッチ登録件数をカウント
		int iRec = 0;

		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

    		// システム日付取得
			system_date = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");

			// ワークテーブル削除
			DBUtil.truncateTable(db, "WK_MBB1_SYOHIN");
			
            //===========================
			//	処理対象の商品を取得
			//===========================

			//商品マスタ
			iRec = db.executeUpdate(insertWkSyohin());
			db.commit();

			if (iRec == 0) {
				writeLog(Level.INFO_INT, "処理対象の商品が存在しませんでした。");

			} else {
				writeLog(Level.INFO_INT, iRec + "件の商品を取得しました。");

	            //========================
				//	単品店取扱マスタ
				//========================
				
				//単品店取扱マスタ
				iRec = db.executeUpdate(updateTanpinten());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "単品店取扱マスタに更新対象データが存在しませんでした。");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の単品店取扱マスタを廃番しました。");
				}
	
				
				//========================
				//	店別商品例外マスタ
				//========================
				
				//店別商品例外マスタ (過去)
				iRec = db.executeUpdate(insertTenReigai());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "店別商品例外マスタに登録対象データが存在しませんでした。(過去分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の店別商品例外マスタデータを廃番しました。(過去分)");
				}
	
				//店別商品例外マスタ (翌日)
				iRec = db.executeUpdate(updateTenReigai());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "店別商品例外マスタに登録対象データが存在しませんでした。(翌日分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の店別商品例外マスタデータを廃番しました。(翌日分)");
				}
				
				//店別商品例外マスタ (未来)
				iRec = db.executeUpdate(deleteTenReigai());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "店別商品例外マスタに登録対象データが存在しませんでした。(未来分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の店別商品例外マスタデータを廃番しました。(未来分)");
				}
	
				
				//========================
				//	物流経路マスタ
				//========================
	
				//物流経路マスタ (過去)
				iRec = db.executeUpdate(insertButuryuKeiro());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "物流経路マスタに登録対象データが存在しませんでした。(過去分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の物流経路マスタデータを廃番しました。(過去分)");
				}
	
				//物流経路マスタ (翌日)
				iRec = db.executeUpdate(updateButuryuKeiro());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "物流経路マスタに登録対象データが存在しませんでした。(翌日分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の物流経路マスタデータを廃番しました。(翌日分)");
				}
				
				//物流経路マスタ (未来)
				iRec = db.executeUpdate(deleteButuryuKeiro());
				if (iRec == 0) {
					writeLog(Level.INFO_INT, "物流経路マスタに登録対象データが存在しませんでした。(未来分)");
	
				} else {
					writeLog(Level.INFO_INT, iRec + "件の物流経路マスタデータを廃番しました。(未来分)");
				}
			}

			db.commit();
				
		//SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			writeError(se);
			db.rollback();

		//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			writeError(e);
			db.rollback();

			//SQL終了処理
		} finally {
			db.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}
	
	
	/**
	 * 対象商品の取得用 SQL
	 * @return String strSql
	 */
	private String insertWkSyohin() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" INSERT INTO WK_MBB1_SYOHIN");
		strSql.append(" ( ");			
		strSql.append("        BUNRUI1_CD, ");
		strSql.append("        SYOHIN_CD ");
		strSql.append(" )");
		strSql.append(" SELECT BUNRUI1_CD, ");
		strSql.append("        SYOHIN_CD ");
		strSql.append("   FROM R_SYOHIN");
		strSql.append("  WHERE YUKO_DT  = '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

		return strSql.toString();
	}
	
	
	/**
	 * 単品店マスタ削除用 SQL
	 * @return String strSql
	 */
	private String updateTanpinten() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" UPDATE R_TANPINTEN_TORIATUKAI ");
		strSql.append("    SET BATCH_UPDATE_TS = '" + system_date + "',");			
		strSql.append("        BATCH_UPDATE_ID = '" + BATCH_ID + "',");
		strSql.append("        DELETE_FG       = '" + mst000801_DelFlagDictionary.IRU.getCode() + "', ");
		strSql.append("        HENKO_DT        = '" + batchDt + "' ");
		strSql.append("  WHERE (BUNRUI1_CD, SYOHIN_CD)");
		strSql.append("     IN (SELECT BUNRUI1_CD, SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN");
		strSql.append("		   )");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		return strSql.toString();
	}

	
	/**
	 * 店別例外マスタ削除用 SQL （過去分）
	 * @return String strSql
	 */
	private String insertTenReigai() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" INSERT INTO R_TENSYOHIN_REIGAI ");
		strSql.append(" ( ");
		strSql.append("        BUNRUI1_CD, ");
		strSql.append("        SYOHIN_CD, ");
		strSql.append("        TENPO_CD, ");
		strSql.append("        YUKO_DT, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        OLD_SYOHIN_CD, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("        TEN_HACHU_ST_DT, ");
		strSql.append("        TEN_HACHU_ED_DT, ");
		strSql.append("        GENTANKA_VL, ");
		strSql.append("        BAITANKA_VL, ");
		strSql.append("        MAX_HACHUTANI_QT, ");
		strSql.append("        EOS_KB, ");
		strSql.append("        SIIRESAKI_CD, ");
		strSql.append("        TENBETU_HAISO_CD, ");
		strSql.append("        BIN_1_KB, ");
		strSql.append("        HACHU_PATTERN_1_KB, ");
		strSql.append("        SIME_TIME_1_QT, ");
		strSql.append("        C_NOHIN_RTIME_1_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_1_KB, ");
		strSql.append("        TEN_NOHIN_TIME_1_KB, ");
		strSql.append("        BIN_2_KB, ");
		strSql.append("        HACHU_PATTERN_2_KB, ");
		strSql.append("        SIME_TIME_2_QT, ");
		strSql.append("        C_NOHIN_RTIME_2_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_2_KB, ");
		strSql.append("        TEN_NOHIN_TIME_2_KB, ");
		strSql.append("        BIN_3_KB, ");
		strSql.append("        HACHU_PATTERN_3_KB, ");
		strSql.append("        SIME_TIME_3_QT, ");
		strSql.append("        C_NOHIN_RTIME_3_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_3_KB, ");
		strSql.append("        TEN_NOHIN_TIME_3_KB, ");
		strSql.append("        C_NOHIN_RTIME_KB, ");
		strSql.append("        SYOHIN_KB, ");
		strSql.append("        BUTURYU_KB, ");
		strSql.append("        TEN_ZAIKO_KB, ");
		strSql.append("        YUSEN_BIN_KB, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        BAIKA_HAISHIN_FG, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("        PLU_SEND_DT, ");
		strSql.append("        HENKO_DT, ");
		strSql.append("        INSERT_TS, ");
		strSql.append("        INSERT_USER_ID, ");
		strSql.append("        UPDATE_TS, ");
		strSql.append("        UPDATE_USER_ID, ");
		strSql.append("        BATCH_UPDATE_TS, ");
		strSql.append("        BATCH_UPDATE_ID, ");
		strSql.append("        DELETE_FG, ");
		strSql.append("        SINKI_TOROKU_DT ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        , GENTANKA_NUKI_VL ");
		strSql.append("        , EMG_FLAG ");
		strSql.append("        , PLU_HANEI_TIME ");
		strSql.append("        , HACHU_FUKA_FLG ");
		strSql.append("        , OROSI_BAITANKA_NUKI_VL ");
		strSql.append("        , SIIRE_KAHI_KB ");
		strSql.append("        , HENPIN_KAHI_KB ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append(" ) ");
		strSql.append(" SELECT BUNRUI1_CD, ");
		strSql.append("        SYOHIN_CD, ");
		strSql.append("        TENPO_CD, ");
		strSql.append("        '" + DateChanger.addDate(batchDt, +1) + "', "); //バッチ日付の翌日の有効日
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        OLD_SYOHIN_CD, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("        TEN_HACHU_ST_DT, ");
		strSql.append("        TEN_HACHU_ED_DT, ");
		strSql.append("        GENTANKA_VL, ");
		strSql.append("        BAITANKA_VL, ");
		strSql.append("        MAX_HACHUTANI_QT, ");
		strSql.append("        EOS_KB, ");
		strSql.append("        SIIRESAKI_CD, ");
		strSql.append("        TENBETU_HAISO_CD, ");
		strSql.append("        BIN_1_KB, ");
		strSql.append("        HACHU_PATTERN_1_KB, ");
		strSql.append("        SIME_TIME_1_QT, ");
		strSql.append("        C_NOHIN_RTIME_1_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_1_KB, ");
		strSql.append("        TEN_NOHIN_TIME_1_KB, ");
		strSql.append("        BIN_2_KB, ");
		strSql.append("        HACHU_PATTERN_2_KB, ");
		strSql.append("        SIME_TIME_2_QT, ");
		strSql.append("        C_NOHIN_RTIME_2_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_2_KB, ");
		strSql.append("        TEN_NOHIN_TIME_2_KB, ");
		strSql.append("        BIN_3_KB, ");
		strSql.append("        HACHU_PATTERN_3_KB, ");
		strSql.append("        SIME_TIME_3_QT, ");
		strSql.append("        C_NOHIN_RTIME_3_KB, ");
		strSql.append("        TEN_NOHIN_RTIME_3_KB, ");
		strSql.append("        TEN_NOHIN_TIME_3_KB, ");
		strSql.append("        C_NOHIN_RTIME_KB, ");
		strSql.append("        SYOHIN_KB, ");
		strSql.append("        BUTURYU_KB, ");
		strSql.append("        TEN_ZAIKO_KB, ");
		strSql.append("        YUSEN_BIN_KB, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        BAIKA_HAISHIN_FG, ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		// PLU反映日には、有効日と同じくバッチ日付＋１をセットする。
		// strSql.append("        PLU_SEND_DT, ");
		strSql.append("        '" + DateChanger.addDate(batchDt, +1) + "', "); // バッチ日付の翌日の有効日
		strSql.append("        '" + batchDt + "', ");
		strSql.append("        '" + system_date + "', ");
		strSql.append("        '" + BATCH_ID + "',");
		strSql.append("        UPDATE_TS, ");
		strSql.append("        UPDATE_USER_ID, ");
		strSql.append("        '" + system_date + "', ");
		strSql.append("        '" + BATCH_ID + "',");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		//strSql.append("        DELETE_FG, ");
		strSql.append("        '" + mst000801_DelFlagDictionary.IRU.getCode() + "', ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("        SINKI_TOROKU_DT ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        , GENTANKA_NUKI_VL ");
		strSql.append("        , '0' ");
		strSql.append("        , NULL ");
		strSql.append("        , HACHU_FUKA_FLG ");
		strSql.append("        , OROSI_BAITANKA_NUKI_VL ");
		strSql.append("        , SIIRE_KAHI_KB ");
		strSql.append("        , HENPIN_KAHI_KB ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("   FROM R_TENSYOHIN_REIGAI T ");
		strSql.append("  WHERE (BUNRUI1_CD, SYOHIN_CD)");
		strSql.append("     IN (SELECT BUNRUI1_CD, SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN");
		strSql.append("		   )");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("    AND YUKO_DT < '" + DateChanger.addDate(batchDt, +1) + "' ");			//有効日が翌日のものは除く
		strSql.append("    AND YUKO_DT = (SELECT MAX(YUKO_DT) ");
		strSql.append("                     FROM R_TENSYOHIN_REIGAI ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		strSql.append("                    WHERE BUNRUI1_CD = T.BUNRUI1_CD ");
//		strSql.append("                      AND SYOHIN_CD  = T.SYOHIN_CD ");
		strSql.append("                    WHERE  ");
		strSql.append("                      	 SYOHIN_CD  = T.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		strSql.append("                      AND TENPO_CD   = T.TENPO_CD ");
		strSql.append("                      AND YUKO_DT   <= '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("                  ) ");

		return strSql.toString();
	}
	
	
	/**
	 * 店別例外マスタ削除用 SQL （未来分）
	 * @return String strSql
	 */
	private String updateTenReigai() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" UPDATE R_TENSYOHIN_REIGAI ");
		strSql.append("    SET BATCH_UPDATE_TS = '" + system_date + "',");			
		strSql.append("        BATCH_UPDATE_ID = '" + BATCH_ID + "',");
		strSql.append("        DELETE_FG       = '" + mst000801_DelFlagDictionary.IRU.getCode() + "', ");
		strSql.append("        HENKO_DT        = '" + batchDt + "' ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (S)
		strSql.append("        , PLU_SEND_DT        = '" + DateChanger.addDate(batchDt, +1) + "'  ");
		strSql.append("        , EMG_FLAG        = '0'  ");
		strSql.append("        , PLU_HANEI_TIME        = NULL ");
		// #4551 MSTB131010 2017.04.17 S.Takayama (E)
		strSql.append("  WHERE (BUNRUI1_CD, SYOHIN_CD) ");
		strSql.append("     IN (SELECT BUNRUI1_CD, SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN");
		strSql.append("		   )");
		strSql.append("    AND YUKO_DT   = '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
	
		return strSql.toString();
	}
	

	/**
	 * 店別例外マスタ物理削除用 SQL （未来分）
	 * @return String strSql
	 */
	private String deleteTenReigai() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" DELETE R_TENSYOHIN_REIGAI trg");
		strSql.append("  WHERE (BUNRUI1_CD, SYOHIN_CD)");
		strSql.append("     IN (SELECT BUNRUI1_CD, SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN");
		strSql.append("		   )");
		strSql.append("    AND YUKO_DT > '" + DateChanger.addDate(batchDt, +1) + "' ");
	
		return strSql.toString();
	}
	

	/**
	 * 物流経路マスタ削除用 SQL （過去分）
	 * @return String strSql
	 */
	private String insertButuryuKeiro() {

		StringBuffer strSql = new StringBuffer();

		strSql.append(" INSERT INTO R_BUTURYUKEIRO ");
		strSql.append(" ( ");
		strSql.append("        KANRI_KB, ");
		strSql.append("        KANRI_CD, ");
		strSql.append("        SIHAI_KB, ");
		strSql.append("        SIHAI_CD, ");
		strSql.append("        SYOHIN_CD, ");
		strSql.append("        TENPO_CD, ");
		strSql.append("        YUKO_DT, ");
		strSql.append("        BUTURYU_KB, ");
		strSql.append("        NOHIN_CENTER_CD, ");
		strSql.append("        KEIYU_CENTER_CD, ");
		strSql.append("        TENHAI_CENTER_CD, ");
		strSql.append("        C_NOHIN_RTIME_KB, ");
		strSql.append("        INSERT_TS, ");
		strSql.append("        INSERT_USER_ID, ");
		strSql.append("        UPDATE_TS, ");
		strSql.append("        UPDATE_USER_ID, ");
		strSql.append("        DELETE_FG ");
		strSql.append(" ) ");
		strSql.append(" SELECT KANRI_KB, ");
		strSql.append("        KANRI_CD, ");
		strSql.append("        SIHAI_KB, ");
		strSql.append("        SIHAI_CD, ");
		strSql.append("        SYOHIN_CD, ");
		strSql.append("        TENPO_CD, ");
		strSql.append("        '" + DateChanger.addDate(batchDt, +1) + "', "); 			//バッチ日付の翌日の有効日
		strSql.append("        BUTURYU_KB, ");
		strSql.append("        NOHIN_CENTER_CD, ");
		strSql.append("        KEIYU_CENTER_CD, ");
		strSql.append("        TENHAI_CENTER_CD, ");
		strSql.append("        C_NOHIN_RTIME_KB, ");
		strSql.append("        '" + system_date + "', ");
		strSql.append("        '" + BATCH_ID + "',");
		strSql.append("        '" + system_date + "', ");
		strSql.append("        '" + BATCH_ID + "',");
		strSql.append("        '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
		strSql.append("   FROM R_BUTURYUKEIRO T ");
		strSql.append("  WHERE KANRI_KB  = '" + mst000101_ConstDictionary.KANRI_DIVISION_SYOHIN + "' "); 
		strSql.append("    AND SYOHIN_CD ");
		strSql.append("     IN (SELECT SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN S ");
		strSql.append("   		 WHERE NOT EXISTS ");
		strSql.append("   		       (SELECT * ");
		strSql.append("   		          FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		strSql.append("   		         WHERE BUNRUI1_CD <> S.BUNRUI1_CD ");
//		strSql.append("   		           AND SYOHIN_CD  = S.SYOHIN_CD ");
		strSql.append("   		         WHERE  ");
		strSql.append("   		            SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		strSql.append("   		           AND YUKO_DT   >= '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("   		           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("		           )");
		strSql.append("		   )");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("    AND YUKO_DT   < '" + DateChanger.addDate(batchDt, +1) + "' ");	//有効日が翌日のものは除く
		strSql.append("    AND YUKO_DT = (SELECT MAX(YUKO_DT) ");
		strSql.append("                     FROM R_BUTURYUKEIRO ");
		strSql.append("                    WHERE KANRI_KB   = T.KANRI_KB ");
		strSql.append("                      AND KANRI_CD   = T.KANRI_CD ");
		strSql.append("                      AND SIHAI_KB   = T.SIHAI_KB ");
		strSql.append("                      AND SIHAI_CD   = T.SIHAI_CD ");
		strSql.append("                      AND SYOHIN_CD  = T.SYOHIN_CD ");
		strSql.append("                      AND TENPO_CD   = T.TENPO_CD ");
		strSql.append("                      AND BUTURYU_KB = T.BUTURYU_KB ");
		strSql.append("                      AND YUKO_DT   <= '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("                  ) ");

		return strSql.toString();
	}

	/**
	 * 物流経路マスタ削除用 SQL （未来分）
	 * @return String strSql
	 */
	private String updateButuryuKeiro() {

		StringBuffer strSql = new StringBuffer();
		
		strSql.append(" UPDATE R_BUTURYUKEIRO ");
		strSql.append("    SET UPDATE_TS      = '" + system_date + "',");			
		strSql.append("        UPDATE_USER_ID = '" + BATCH_ID + "',");
		strSql.append("        DELETE_FG      = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
		strSql.append("  WHERE KANRI_KB  = '" + mst000101_ConstDictionary.KANRI_DIVISION_SYOHIN + "' "); 
		strSql.append("    AND SYOHIN_CD ");
		strSql.append("     IN (SELECT SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN S ");
		strSql.append("   		 WHERE NOT EXISTS ");
		strSql.append("   		       (SELECT * ");
		strSql.append("   		          FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		strSql.append("   		         WHERE BUNRUI1_CD <> S.BUNRUI1_CD ");
//		strSql.append("   		           AND SYOHIN_CD  = S.SYOHIN_CD ");
		strSql.append("   		         WHERE  ");
		strSql.append("   		           	 SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		strSql.append("   		           AND YUKO_DT   >= '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("   		           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("		           )");
		strSql.append("		   )");
		strSql.append("    AND YUKO_DT   = '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("    AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
	
		return strSql.toString();
	}

	
	/**
	 * 物流経路マスタ物理削除用 SQL （未来分）
	 * @return String strSql
	 */
	private String deleteButuryuKeiro() {

		StringBuffer strSql = new StringBuffer();
		
		strSql.append(" DELETE R_BUTURYUKEIRO ");
		strSql.append("  WHERE KANRI_KB  = '" + mst000101_ConstDictionary.KANRI_DIVISION_SYOHIN + "' ");
		strSql.append("    AND SYOHIN_CD ");
		strSql.append("     IN (SELECT SYOHIN_CD ");
		strSql.append("   		  FROM WK_MBB1_SYOHIN S ");
		strSql.append("   		 WHERE NOT EXISTS ");
		strSql.append("   		       (SELECT * ");
		strSql.append("   		          FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		strSql.append("   		         WHERE BUNRUI1_CD <> S.BUNRUI1_CD ");
//		strSql.append("   		           AND SYOHIN_CD  = S.SYOHIN_CD ");
		strSql.append("   		         WHERE  ");
		strSql.append("   		           	 SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		strSql.append("   		           AND YUKO_DT   >= '" + DateChanger.addDate(batchDt, +1) + "' ");
		strSql.append("   		           AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		strSql.append("		           )");
		strSql.append("		   )");
		strSql.append("    AND YUKO_DT > '" + DateChanger.addDate(batchDt, +1) + "' ");
	
		return strSql.toString();
	}

	
	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
			
		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}
	
	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * 
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

	
	/**
	 * test実行用メイン関数
	 * @param args
	 */
	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";
		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);
		MBB10101_HaibanJissi batch = new MBB10101_HaibanJissi();
		try {
			batch.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}