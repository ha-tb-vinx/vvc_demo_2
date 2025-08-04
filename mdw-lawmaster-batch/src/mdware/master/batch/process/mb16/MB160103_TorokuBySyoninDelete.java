package mdware.master.batch.process.mb16;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.bean.RBatchParamBean;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.properties.RBatchParamUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006501_BySyoninFgDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:登録票BY承認データ削除処理</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author A.Okada
 * @version 1.0 2009/02/12 マミーマート様向け初版作成(MB870101の流用)<BR>
 */
public class MB160103_TorokuBySyoninDelete {
	private MasterDataBase db = null;

	// 対象テーブル（キーテーブルは最後に登録）
	private static final String[][] table = { 
		{ "TR_MESSAGE",          "処理結果メッセージ" },
		{ "TR_SYOHIN",           "商品マスタTR" },
		{ "TR_GIFT_SYOHIN",      "ギフト商品マスタTR" },
		{ "TR_KEIRYOKI",         "計量器マスタTR" },
		{ "TR_TENSYOHIN_REIGAI", "店別商品例外マスタTR" },
		{ "TR_TOROKU_SYONIN",    "登録票承認TR" }
	};

	// 退避対象テーブル（キーテーブルは最後に登録）
	private static final String[][] bkTable = { 
		{ "",                    "" },
		{ "TR_SYOHIN",           "退避用商品マスタTR" },
		{ "TR_GIFT_SYOHIN",      "退避用ギフト商品マスタTR" },
		{ "TR_KEIRYOKI",         "退避用計量器マスタTR" },
		{ "TR_TENSYOHIN_REIGAI", "退避用店別商品例外マスタTR" },
		{ "TR_TOROKU_SYONIN",    "退避用登録票承認TR" }
	};

	// キーテーブルの配列位置
	private static final int    KEYTABLE_ID   = 5;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;

	// R_BATCH_PARAMテーブルアクセス用Util
	RBatchParamUtil bPropeties; 

	private static final String BATCH_PROPERTIES_ID  = "MB160103";
	private static final String MISYONIN_BK   = "Misyonin_BK";
	private static final String MISYONIN_MAIN = "Misyonin_MAIN";
	private static final String SYONIN_BK     = "Syonin_BK";
	private static final String SYONIN_MAIN   = "Syonin_MAIN";
	private static final String ERROR_BK      = "Error_BK";
	private static final String ERROR_MAIN    = "Error_MAIN";


	/**
	 * コンストラクタ
	 * 
	 * @param dataBase
	 */
	public MB160103_TorokuBySyoninDelete(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB160103_TorokuBySyoninDelete() {
		this(new MasterDataBase("rbsite_ora"));
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		execute();
	}

	/**
	 * 本処理
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {

		bPropeties = new RBatchParamUtil(db);

		try {
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// 各処理件数保持用配列（対象テーブル数分作成）
			int[] intCnt    = { 0, 0, 0, 0, 0, 0 };
			int[] intBkCnt1 = { 0, 0, 0, 0, 0, 0 };
			int[] intBkCnt2 = { 0, 0, 0, 0, 0, 0 };

			// 退避削除基準日
			String refugeDate = null;
			
			// 退避登録/削除基準日
			String deleteDate = null; 

			// システムコントロール情報取得
			this.getSystemControl();

			/*******************************************************************
			 * 登録承認フラグが"0"（未承認）状態で、 取込日がマスタバッチ日付から 
			 * 一定期間経過した対象データを物理削除する。
			 * ！マミーマート様向け仕様では、未承認データは発生しないが
			 * ！ロジックは残しておく
			 ******************************************************************/
			// 各TRテーブルから未承認データを退避・削除する
			//   ①バックアップTBLの保持期間経過データを削除
			//   ②TR系TBLから保持期間経過データをバックアップTBLへ退避
			//   ③TR系TBLから保持期間経過データを削除

			for (int i = 0; i < table.length - 1; i++) {
				refugeDate = getPropertyDate(MISYONIN_BK, i, 0);
				deleteDate = getPropertyDate(MISYONIN_MAIN, i, 1);

				if (!bkTable[i][0].equals("")) {
					intBkCnt1[i] = db.executeUpdate(SetBkDeletemisyoninSql(bkTable[i][0], refugeDate));
					intBkCnt2[i] = db.executeUpdate(SetBkInsertmisyoninSql(bkTable[i][0], deleteDate));
				}
				intCnt[i] = db.executeUpdate(SetDeletemisyoninSql(table[i][0], deleteDate));
			}

			// 登録票承認TRから未承認データを削除する
			refugeDate = getPropertyDate(MISYONIN_BK, KEYTABLE_ID, 0);
			deleteDate = getPropertyDate(MISYONIN_MAIN, KEYTABLE_ID, 1);

			intBkCnt1[KEYTABLE_ID] = db.executeUpdate(SetBkDeletemisyoninTorokuhyoSql(refugeDate));
			intBkCnt2[KEYTABLE_ID] = db.executeUpdate(SetBkInsertmisyoninTorokuhyoSql(deleteDate));
			intCnt[KEYTABLE_ID]    = db.executeUpdate(SetDeletemisyoninTorokuhyoSql(deleteDate));

			db.commit();

			// Log内容を記入する
			for (int i = 0; i < table.length; i++) {
				if (!bkTable[i][0].equals("")) {
					this.writeLog(Level.INFO_INT, intBkCnt1[i] + "件、" + bkTable[i][1] + "の未承認データを物理削除しました。");
					this.writeLog(Level.INFO_INT, intBkCnt2[i] + "件、" + bkTable[i][1] + "へ未承認データを新規登録しました。");
				}
				this.writeLog(Level.INFO_INT, intCnt[i] + "件、" + table[i][1] + "の未承認データを物理削除しました。");
			}


			/*******************************************************************
			 * 登録承認フラグが"1"（承認）もしくは、"2"（否認）状態で、 登録承認日がマスタバッチ日付から
			 * 一定期間経過した対象データを物理削除する。
			 ******************************************************************/
			// 各TRテーブルから承認データ、否認データを退避・削除する
			for (int i = 0; i < table.length - 1; i++) {
				refugeDate = getPropertyDate(SYONIN_BK, i, 2);
				deleteDate = getPropertyDate(SYONIN_MAIN, i, 3);

				if (!bkTable[i][0].equals("")) {
					intBkCnt1[i] = db.executeUpdate(SetBkDeletesyoninSql(bkTable[i][0], refugeDate));
					intBkCnt2[i] = db.executeUpdate(SetBkInsertsyoninSql(bkTable[i][0], deleteDate));
				}
				intCnt[i] = db.executeUpdate(SetDeletesyoninSql(table[i][0], deleteDate));
			}

			// 登録票承認TRから承認データ、否認データを削除する
			refugeDate = getPropertyDate(SYONIN_BK, KEYTABLE_ID, 2);
			deleteDate = getPropertyDate(SYONIN_MAIN, KEYTABLE_ID, 3);

			intBkCnt1[KEYTABLE_ID] = db.executeUpdate(SetBkDeletesyoninTorokuhyoSql(refugeDate));
			intBkCnt2[KEYTABLE_ID] = db.executeUpdate(SetBkInsertsyoninTorokuhyoSql(deleteDate));
			intCnt[KEYTABLE_ID]    = db.executeUpdate(SetDeletesyoninTorokuhyoSql(deleteDate));

			db.commit();

			// Log内容を記入する
			for (int i = 0; i < table.length; i++) {
				if (!bkTable[i][0].equals("")) {
					this.writeLog(Level.INFO_INT, intBkCnt1[i] + "件、" + bkTable[i][1] + "の承認・否認データを物理削除しました。");
					this.writeLog(Level.INFO_INT, intBkCnt2[i] + "件、" + bkTable[i][1] + "へ承認・否認データを新規登録しました。");
				}
				this.writeLog(Level.INFO_INT, intCnt[i] + "件、" + table[i][1] + "の承認・否認データを物理削除しました。");
			}

			/*******************************************************************
			 * 処理状態フラグが"0"以外（エラー）状態で、 更新日がマスタバッチ日付から 
			 * 一定期間経過した対象データを物理削除する。
			 ******************************************************************/
			// 各TRテーブルからエラーデータを退避・削除する
			for (int i = 0; i < table.length - 1; i++) {
				refugeDate = getPropertyDate(ERROR_BK, i, 4);
				deleteDate = getPropertyDate(ERROR_MAIN, i, 5);

				if (!bkTable[i][0].equals("")) {
					intBkCnt1[i] = db.executeUpdate(SetBkDeleteerrorSql(bkTable[i][0], refugeDate));
					intBkCnt2[i] = db.executeUpdate(SetBkInserterrorSql(bkTable[i][0], deleteDate));
				}
				intCnt[i] = db.executeUpdate(SetDeleteerrorSql(table[i][0], deleteDate));
			}

			// 登録票承認TRからエラーデータを削除する
			refugeDate = getPropertyDate(ERROR_BK, KEYTABLE_ID, 4);
			deleteDate = getPropertyDate(ERROR_MAIN, KEYTABLE_ID, 5);

			intBkCnt1[KEYTABLE_ID] = db.executeUpdate(SetBkDeleteerrorTorokuhyoSql(refugeDate));
			intBkCnt2[KEYTABLE_ID] = db.executeUpdate(SetBkInserterrorTorokuhyoSql(deleteDate));
			intCnt[KEYTABLE_ID]    = db.executeUpdate(SetDeleteerrorTorokuhyoSql(deleteDate));

			db.commit();

			// Log内容を記入する
			for (int i = 0; i < table.length; i++) {
				if (!bkTable[i][0].equals("")) {
					this.writeLog(Level.INFO_INT, intBkCnt1[i] + "件、" + bkTable[i][1] + "のエラーデータを物理削除しました。");
					this.writeLog(Level.INFO_INT, intBkCnt2[i] + "件、" + bkTable[i][1] + "へエラーデータを新規登録しました。");
				}
				this.writeLog(Level.INFO_INT, intCnt[i] + "件、" + table[i][1] + "のエラーデータを物理削除しました。");
			}

			/*******************************************************************
			 * 削除フラグが"1"（削除済）のデータを物理削除する。
			 * ！マミーマート様向け仕様では、論理削除データは発生しないが
			 * ！ロジックは残しておく
			 ******************************************************************/
			// 各TRテーブルから削除済みデータを退避・削除する
			for (int i = 0; i < table.length - 1; i++) {
				if (!bkTable[i][0].equals("")) {
					intBkCnt1[i] = db.executeUpdate(SetBkDeleteSql(bkTable[i][0]));
					intBkCnt2[i] = db.executeUpdate(SetBkInsertSql(bkTable[i][0]));
				}
				intCnt[i] = db.executeUpdate(SetDeleteSql(table[i][0]));
			}

			intBkCnt1[KEYTABLE_ID] = db.executeUpdate(SetBkDeleteTorokuhyoSql());
			intBkCnt2[KEYTABLE_ID] = db.executeUpdate(SetBkInsertTorokuhyoSql());
			intCnt[KEYTABLE_ID]    = db.executeUpdate(SetDeleteTorokuhyoSql());

			db.commit();

			// Log内容を記入する
			for (int i = 0; i < table.length; i++) {
				if (!bkTable[i][0].equals("")) {
					this.writeLog(Level.INFO_INT, intBkCnt1[i] + "件、" + bkTable[i][1] + "の論理削除データを物理削除しました。");
					this.writeLog(Level.INFO_INT, intBkCnt2[i] + "件、" + bkTable[i][1] + "へ論理削除データを新規登録しました。");
				}
				this.writeLog(Level.INFO_INT, intCnt[i] + "件、" + table[i][1] + "の論理削除データを物理削除しました。");
			}

			this.writeLog(Level.INFO_INT, "処理を終了します。");

		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();
			this.writeError(se);
			throw se;
		// その他のエラーが発生した場合の処理
		}catch( Exception e ){
			db.rollback();
			this.writeError(e);
			throw e;
		// SQL終了処理
		} finally {
			db.close();
		}
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// 初期化
		batchDt 	= null;
		
    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}
	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetDeletemisyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");

		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  TORIKOMI_DT <= '").append(date).append("' ");

		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetDeletemisyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");

		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" TORIKOMI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetDeletesyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");

		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetDeletesyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");

		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetDeleteerrorSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" 	  SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' )");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetDeleteerrorTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetDeleteSql(String Tablename) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetDeleteTorokuhyoSql() {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

		return strSql.toString();
	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkInsertmisyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" SELECT * FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  TORIKOMI_DT <= '" + date + "' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkInsertmisyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" INSERT ");
		strSql.append(" INTO BK_TR_TOROKU_SYONIN");
		strSql.append(" SELECT * ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");
		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" TORIKOMI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkInsertsyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" SELECT * FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkInsertsyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" INSERT ");
		strSql.append(" INTO BK_TR_TOROKU_SYONIN");
		strSql.append(" SELECT * ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");
		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkInserterrorSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" SELECT * FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" 	  SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkInserterrorTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" INSERT ");
		strSql.append(" INTO BK_TR_TOROKU_SYONIN");
		strSql.append(" SELECT * ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkInsertSql(String Tablename) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" SELECT * FROM ");
		strSql.append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkInsertTorokuhyoSql() {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" INSERT ");
		strSql.append(" INTO BK_TR_TOROKU_SYONIN");
		strSql.append(" SELECT * ");
		strSql.append(" FROM TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

		return strSql.toString();
	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkDeletemisyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  TORIKOMI_DT <= '" + date + "' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"0"(未承認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkDeletemisyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.MISYONIN.getCode() + "' AND ");
		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" TORIKOMI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkDeletesyoninSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" 	  TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" 	  SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 登録承認フラグが"1"(承認)もしくは、 "2"(否認)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkDeletesyoninTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" ( TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.SYONIN.getCode() + "' OR ");
		strSql.append(" TOROKU_SYONIN_FG = '" + mst006501_BySyoninFgDictionary.HISYONIN.getCode() + "' ) AND ");
		strSql.append(" SYORI_JYOTAI_FG = '0' AND ");
		strSql.append(" SUBSTR(TOROKU_SYONIN_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkDeleteerrorSql(String Tablename, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" 	  SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 処理状態フラグが"0"以外(エラー)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkDeleteerrorTorokuhyoSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" SYORI_JYOTAI_FG <> '0' AND ");
		strSql.append(" SUBSTR(UPDATE_TS,1,8) <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る。
	 * 
	 * @param Tablename
	 *            テーブル
	 * @return SQL文
	 */
	private String SetBkDeleteSql(String Tablename) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append("BK_").append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  TORIKOMI_DT||EXCEL_FILE_SYUBETSU||CAST(UKETSUKE_NO AS CHAR(5))");
		strSql.append(" 	FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' AND ");
		strSql.append(" 	  SUBSTR(UPDATE_TS,1,8) <= '" + DateChanger.addMonth(batchDt, -2) + "' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 削除フラグが"1"(削除済)状態のSQL文を作る(登録票承認TR用)。
	 * 
	 * @return SQL文
	 */
	private String SetBkDeleteTorokuhyoSql() {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE  ");
		strSql.append(" FROM BK_TR_TOROKU_SYONIN");
		strSql.append(" WHERE");
		strSql.append(" DELETE_FG = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' AND ");
		strSql.append(" SUBSTR(UPDATE_TS,1,8) <= '" + DateChanger.addMonth(batchDt, -2) + "' ");

		return strSql.toString();
	}

	/**
	 * 指定したプロパティの日付を取得する。 
	 * 設定ミスがあった場合はエラーに出力。
	 * 
	 * @param valueTx R_BATCH_PARAMテーブルのparam1_tx
	 * @param num subId作成用キー１。テーブル設定番号(tablename[] の配列番号)
	 * @param renban subId作成用キー２。連番。
	 */
	private String getPropertyDate(String valueTx, int num, int renban) throws Exception {
		String propValue = null; // プロパティで取得できる値
		try {
			String subId = new StringBuffer().append(num).append(renban).toString();
			RBatchParamBean propBean = bPropeties.getSingleProperty(BATCH_PROPERTIES_ID, subId, valueTx);

			if (propBean == null || propBean.getParame2_tx().trim().length() == 0) {
				throw new Exception(table[num][1] + "の保持期間設定が取得できませんでした：ID[" 
										+ BATCH_PROPERTIES_ID + "] SubId[" + subId + "] valueTx[" + valueTx + "]");
			}

			propValue = propBean.getParame2_tx();
			// 設定された値が 0 でなければ減算して返す
			if (!propValue.equals("0")) {
				int propValueInt = Integer.parseInt(propBean.getParame2_tx());
				return DateChanger.addDate(batchDt, -propValueInt);
			}

			return batchDt;

		} catch (Exception ex) {
			ex.printStackTrace();
			batchLog.getLog().error(table[num][1] + "の保持期間設定でエラー発生：[" + BATCH_PROPERTIES_ID + "] [" 
												+ num + "] [" + renban + "] [" + valueTx + "] > [" + propValue + "]");
			throw ex;
		}
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
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
