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
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:バッチ処理結果データ削除処理</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author A.Okada
 * @version 1.0 2009/02/27 マミーマート様向け初版作成<BR>
 * @version 1.1 2017/08/02 DAU.TQP #5212 FIVIMART対応<BR>
 */
public class MB160104_BatchSyoriDataDelete {
	private MasterDataBase db = null;

	// 対象テーブル（キーテーブルは最後に登録）
	private static final String[][] table = { 
		{ "TR_SYOHIN_IKKATU_MENTE",    "01", "商品一括修正TR" },
		{ "TR_FUKADO_HANTEIKEKKA",     "02", "不稼動判定結果TR" },
		{ "TR_MASTER_SEIGOUSEI_CHECK", "03", "マスタ整合成チェックTR" },
		{ "TR_BATCH_SYORIKEKKA",       "",   "バッチ処理結果TR" }
	};

	// 退避対象テーブル（キーテーブルは最後に登録）
	private static final String[][] bkTable = { 
		{ "TR_SYOHIN_IKKATU_MENTE",    "01", "退避用商品一括修正TR" },
		{ "TR_FUKADO_HANTEIKEKKA",     "02", "退避用不稼動判定結果TR" },
		{ "TR_MASTER_SEIGOUSEI_CHECK", "03", "退避用マスタ整合成チェックTR" },
		{ "TR_BATCH_SYORIKEKKA",       "",   "退避用バッチ処理結果TR" }
	};

	// キーテーブルの配列位置（0始まり）
	private static final int    KEYTABLE_ID   = 3;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;

	// R_BATCH_PARAMテーブルアクセス用Util
	RBatchParamUtil bPropeties; 

	private static final String BATCH_PROPERTIES_ID  = "MB160104";
	private static final String BACKUP_BK   = "Backup_BK";
	private static final String BACKUP_MAIN = "Backup_MAIN";


	/**
	 * コンストラクタ
	 * 
	 * @param dataBase
	 */
	public MB160104_BatchSyoriDataDelete(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB160104_BatchSyoriDataDelete() {
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
			int[] intCnt    = { 0, 0, 0, 0 };
			int[] intBkCnt1 = { 0, 0, 0, 0 };
			int[] intBkCnt2 = { 0, 0, 0, 0 };

			// 退避削除基準日
			String refugeDate = null;
			
			// 退避登録/削除基準日
			String deleteDate = null; 

			// システムコントロール情報取得
			this.getSystemControl();

           //#5212 Add 2017.08.02 DAU.TQP (S)
           /** 退避済みデータ削除処理 **/
           int[] delCnt    = {0, 0};
           //商品マスタ一括修正の場合
           deleteDate = getPropertyDate(BACKUP_MAIN, 4, 1);
           delCnt[0] = db.executeUpdate(deleteSyohinMasterIkkatuMenteSql(deleteDate));
           //商品マスタ一括修正管理の場合
           deleteDate = getPropertyDate(BACKUP_MAIN, 5, 1);
           delCnt[1] = db.executeUpdate(deleteSyohinMasterIkkatuMenteKanriSql(deleteDate));
           //#5212 Add 2017.08.02 DAU.TQP (E)

			/*******************************************************************
			* 各TRテーブルから保持期間経過データを退避・削除する
			*   ①バックアップTBLの保持期間経過データを削除
			*   ②TR系TBLから保持期間経過データをバックアップTBLへ退避
			*   ③TR系TBLから保持期間経過データを削除
			*******************************************************************/

			for (int i = 0; i < table.length - 1; i++) {
				refugeDate = getPropertyDate(BACKUP_BK, i, 0);
				deleteDate = getPropertyDate(BACKUP_MAIN, i, 1);

				if (!bkTable[i][0].equals("")) {
					intBkCnt1[i] = db.executeUpdate(SetBkDeleteSql(bkTable[i][0], bkTable[i][1], refugeDate));
					intBkCnt2[i] = db.executeUpdate(SetBkInsertSql(bkTable[i][0], bkTable[i][1], deleteDate));
				}
				intCnt[i] = db.executeUpdate(SetMainDeleteSql(table[i][0], table[i][1], deleteDate));
			}

			// 処理結果TRから未承認データを削除する
			refugeDate = getPropertyDate(BACKUP_BK, KEYTABLE_ID, 0);
			deleteDate = getPropertyDate(BACKUP_MAIN, KEYTABLE_ID, 1);

			intBkCnt1[KEYTABLE_ID] = db.executeUpdate(SetBkDeleteKekkaSql(refugeDate));
			intBkCnt2[KEYTABLE_ID] = db.executeUpdate(SetBkInsertKekkaSql(deleteDate));
			intCnt[KEYTABLE_ID]    = db.executeUpdate(SetMainDeleteKekkaSql(deleteDate));

			db.commit();

			// Log内容を記入する
            //#5212 Add 2017.08.02 DAU.TQP (S)
           this.writeLog(Level.INFO_INT, delCnt[0] + "件、商品マスタ一括修正のデータを物理削除しました。");
           this.writeLog(Level.INFO_INT, delCnt[1] + "件、商品マスタ一括修正管理のデータを物理削除しました。");
           //#5212 Add 2017.08.02 DAU.TQP (E)
			for (int i = 0; i < table.length; i++) {
				if (!bkTable[i][0].equals("")) {
					this.writeLog(Level.INFO_INT, intBkCnt1[i] + "件、" + bkTable[i][2] + "のデータを物理削除しました。");
					this.writeLog(Level.INFO_INT, intBkCnt2[i] + "件、" + bkTable[i][2] + "へデータを新規登録しました。");
				}
				this.writeLog(Level.INFO_INT, intCnt[i] + "件、" + table[i][2] + "のデータを物理削除しました。");
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
	 * バックアップテーブルの保持期間経過データを削除
	 * 
	 * @param Tablename ：対象テーブル名
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetBkDeleteSql(String Tablename, String SyoriKB, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(" BK_").append(Tablename);
		strSql.append(" WHERE ");
		strSql.append("   SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) ");
		strSql.append(" 	FROM BK_TR_BATCH_SYORIKEKKA ");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  SYORI_SYUBETSU_KB = '").append(SyoriKB).append("' AND ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_DT <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}

	/**
	 * 各TRテーブルの保持期間経過データを
	 * 対象のバックアップテーブルへコピーする
	 * 
	 * @param Tablename ：対象テーブル名
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetBkInsertSql(String Tablename, String SyoriKB, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append(" BK_").append( Tablename );
		strSql.append(" SELECT * FROM ");
		strSql.append( Tablename );
		strSql.append(" WHERE ");
		strSql.append("   SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) ");
		strSql.append(" 	FROM TR_BATCH_SYORIKEKKA ");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  SYORI_SYUBETSU_KB = '").append(SyoriKB).append("' AND ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_DT <= '").append(date).append("' ");
		strSql.append(" )");

		return strSql.toString();

	}
	
	/**
	 * 各TRテーブルの保持期間経過データを削除
	 * 
	 * @param Tablename ：対象テーブル名
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetMainDeleteSql(String Tablename, String SyoriKB, String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append( Tablename );
		strSql.append(" WHERE ");
		strSql.append("   SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) IN ");
		strSql.append(" ( 	SELECT ");
		strSql.append(" 	  SYORI_DT||CAST(UKETSUKE_NO AS CHAR(5)) ");
		strSql.append(" 	FROM TR_BATCH_SYORIKEKKA ");
		strSql.append(" 	WHERE ");
		strSql.append(" 	  SYORI_SYUBETSU_KB = '").append(SyoriKB).append("' AND ");
		strSql.append(" 	  DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append(" 	  SYORI_DT <= '").append(date).append("' ");

		strSql.append(" )");

		return strSql.toString();

	}
	
	/**
	 * BKバッチ処理結果TRの保持期間経過データを削除
	 * 
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetBkDeleteKekkaSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE FROM ");
		strSql.append("   BK_TR_BATCH_SYORIKEKKA ");
		strSql.append(" WHERE ");
		strSql.append("   DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append("   SYORI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}

	/**
	 * バッチ処理結果TRの保持期間経過データを
	 * バックアップテーブルへコピーする
	 * 
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetBkInsertKekkaSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" INSERT INTO ");
		strSql.append("   BK_TR_BATCH_SYORIKEKKA ");
		strSql.append(" SELECT * ");
		strSql.append("   FROM TR_BATCH_SYORIKEKKA");
		strSql.append(" WHERE");
		strSql.append("   DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append("   SYORI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}
	
	/**
	 * バッチ処理結果TRの保持期間経過データを削除
	 * 
	 * @param date      ：基準日
	 * @return SQL文
	 */
	private String SetMainDeleteKekkaSql(String date) {

		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.delete(0, strSql.length());
		strSql.append(" DELETE FROM ");
		strSql.append("   TR_BATCH_SYORIKEKKA");
		strSql.append(" WHERE");
		strSql.append("   DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' AND ");
		strSql.append("   SYORI_DT <= '").append(date).append("' ");

		return strSql.toString();

	}

	//#5212 Add 2017.08.02 DAU.TQP (S)
	 /**
     * 商品マスタ一括修正の退避済みデータ削除処理
     * 
     * @param date      ：基準日
     * @return SQL文
     */
	private String deleteSyohinMasterIkkatuMenteSql(String date) {
	    // SQL文生成用
        StringBuffer strSql = new StringBuffer();
        strSql.delete(0, strSql.length());
        strSql.append(" DELETE FROM R_SYOHIN_IKKATU_MENTE   ");
        strSql.append(" WHERE ");
        strSql.append("   CAST(UKETSUKE_NO AS CHAR (5)) || BUNRUI1_CD || SYOHIN_CD IN (  ");
        strSql.append("     SELECT ");
        strSql.append("       CAST(UKETSUKE_NO AS CHAR (5)) || BUNRUI1_CD || SYOHIN_CD  ");
        strSql.append("     FROM ");
        strSql.append("       TR_SYOHIN_IKKATU_MENTE  ");
        strSql.append("     WHERE ");
        strSql.append("       SYORI_DT <= '").append(date).append("' ");
        strSql.append("   ) ");

        return strSql.toString();
	}
	/**
     * 商品マスタ一括修正管理の退避済みデータ削除処理
     * 
     * @param date      ：基準日
     * @return SQL文
     */
    private String deleteSyohinMasterIkkatuMenteKanriSql(String date) {
        // SQL文生成用
        StringBuffer strSql = new StringBuffer();
        strSql.delete(0, strSql.length());
        strSql.append(" DELETE FROM R_SYOHIN_IKKATU_MENTE_KANRI ");
        strSql.append(" WHERE ");
        strSql.append("   CAST(UKETSUKE_NO AS CHAR (5)) IN (  ");
        strSql.append("     SELECT ");
        strSql.append("       CAST(UKETSUKE_NO AS CHAR (5)) ");
        strSql.append("     FROM ");
        strSql.append("       TR_SYOHIN_IKKATU_MENTE  ");
        strSql.append("     WHERE ");
        strSql.append("       SYORI_DT <= '").append(date).append("' ");
        strSql.append("   ) ");

        return strSql.toString();
    }
	//#5212 Add 2017.08.02 DAU.TQP (E)
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

			if (propBean == null || propBean.getParame2_tx() == null) {
				throw new Exception(table[num][1] + "の保持期間設定が取得できませんでした：ID[" 
										+ BATCH_PROPERTIES_ID + "] SubId[" + subId + "] valueTx[" + valueTx + "]");
			}

			propValue = propBean.getParame2_tx();
			// 設定された値が 0 でなければ減産して返す
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
