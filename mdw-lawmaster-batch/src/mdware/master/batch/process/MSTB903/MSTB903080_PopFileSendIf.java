package mdware.master.batch.process.MSTB903;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.batch.common.util.FileCopyDaoInputBean;
import mdware.master.batch.common.util.FileCopyWithAuthDao;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB903080_PopFileSendIf.java クラス</p>
 * <p>説明　: 商品マスタデータファイル、商品マスタエンドファイル、店別商品マスタデータファイル、店別商品マスタエンドファイルをFTP送信する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.09) T.Ooshiro [CUS00066] ランドローム様対応 POPインターフェイス仕様変更対応対応
 *
 */
public class MSTB903080_PopFileSendIf {

	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();
	private String jobId = userLog.getJobId();

	// 変換文字（yyyyMMddhhmmddの文字）
	private static final String DATE_TIME_DUMMY_CODE = "yyyyMMddhhmmss";
	// 変換後文字（"000000"の文字）
	private static final String TIME_CHANGED_CODE = "000000";

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** CSVファイルパス */
	private String csvFilePath = null;
	/** CSVコピー先ファイルパス */
	private String csvCopyFilePath = null;
	/** 商品マスタデータファイル名 */
	private String popFtpSyohin = null;
	/** 商品マスタエンドファイル名 */
	private String popFtpSyohinEnd = null;
	/** 店別商品マスタデータファイル名 */
	private String popFtpTenbetuSyohin = null;
	/** 店別商品マスタエンドファイル名 */
	private String popFtpTenbetuSyohinEnd = null;
	/** 商品マスタデータファイル名(コピー先) */
	private String popFtpSyohinCopy = null;
	/** 商品マスタエンドファイル名(コピー先) */
	private String popFtpSyohinEndCopy = null;
	/** 店別商品マスタデータファイル名(コピー先) */
	private String popFtpTenbetuSyohinCopy = null;
	/** 店別商品マスタエンドファイル名(コピー先) */
	private String popFtpTenbetuSyohinEndCopy = null;

	// バッチ日付
	private String batchDt = null;
	// 翌日バッチ日付
	private String yokuBatchDt = null;


	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB903080_PopFileSendIf( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MSTB903080_PopFileSendIf() {
	}



	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		try {
			writeLog(Level.INFO_INT, Jobs.getInstance().get(jobId).getJobName() + "を開始します。");

			FileCopyDaoInputBean ib = new FileCopyDaoInputBean();
			FileCopyWithAuthDao fileCopy = new FileCopyWithAuthDao();
			
			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			
			getSystemControl();
			
			//PATH設定
			ib.setFromDirId(csvFilePath);
			ib.setToDirId(csvCopyFilePath);
			
			//その他
			ib.setAddString(AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR));

			// 商品マスタデータファイル
			ib.setFromFileId(popFtpSyohin);
			ib.setToFileId(popFtpSyohinCopy);
			fileCopy.setInputObject(ib);
			fileCopy.copy();

			// 商品マスタエンドファイル
			ib.setFromFileId(popFtpSyohinEnd);
			ib.setToFileId(popFtpSyohinEndCopy);
			fileCopy.setInputObject(ib);
			fileCopy.copy();


			// 店別商品マスタデータファイル
			ib.setFromFileId(popFtpTenbetuSyohin);
			ib.setToFileId(popFtpTenbetuSyohinCopy);
			fileCopy.setInputObject(ib);
			fileCopy.copy();

			// 店別商品マスタエンドファイル
			ib.setFromFileId(popFtpTenbetuSyohinEnd);
			ib.setToFileId(popFtpTenbetuSyohinEndCopy);
			fileCopy.setInputObject(ib);
			fileCopy.copy();

		// 例外処理
		} catch ( Exception e ) {
			writeError(e);
			throw e;
		}finally{
			writeLog(Level.INFO_INT, Jobs.getInstance().get(jobId).getJobName() + "が終了しました。");
		}
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_POP);
		if (csvFilePath == null || csvFilePath.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// CSVコピー先ファイルパス取得
		csvCopyFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_DEST_POP);
		if (csvCopyFilePath == null || csvCopyFilePath.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶコピー先のパスが取得できませんでした");
			throw new Exception();
		}

		// 商品マスタデータファイル名取得
		popFtpSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_SYOHIN);
		if (popFtpSyohin == null || popFtpSyohin.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品マスタエンドファイル名取得
		popFtpSyohinEnd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_SYOHIN_END);
		if (popFtpSyohinEnd == null || popFtpSyohinEnd.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタエンドファイル名が取得できませんでした");
			throw new Exception();
		}

		// 店別商品マスタデータファイル名取得
		popFtpTenbetuSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_TENBETU_SYOHIN);
		if (popFtpTenbetuSyohin == null || popFtpTenbetuSyohin.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、店別商品マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 店別商品マスタエンドファイル名取得
		popFtpTenbetuSyohinEnd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.POP_FTP_TENBETU_SYOHIN_END);
		if (popFtpTenbetuSyohinEnd == null || popFtpTenbetuSyohinEnd.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、店別商品マスタエンドファイル名が取得できませんでした");
			throw new Exception();
		}

		popFtpSyohinCopy = popFtpSyohin.replace(DATE_TIME_DUMMY_CODE, yokuBatchDt + TIME_CHANGED_CODE);
		popFtpSyohinEndCopy = popFtpSyohinEnd.replace(DATE_TIME_DUMMY_CODE, yokuBatchDt + TIME_CHANGED_CODE);
		popFtpTenbetuSyohinCopy = popFtpTenbetuSyohin.replace(DATE_TIME_DUMMY_CODE, yokuBatchDt + TIME_CHANGED_CODE);
		popFtpTenbetuSyohinEndCopy = popFtpTenbetuSyohinEnd.replace(DATE_TIME_DUMMY_CODE, yokuBatchDt + TIME_CHANGED_CODE);
	}


/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
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