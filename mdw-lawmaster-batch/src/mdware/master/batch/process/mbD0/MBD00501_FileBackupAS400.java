package mdware.master.batch.process.mbD0;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.file.FileBackupUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.batch.common.util.FileControl;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:AS400用ファイルバックアップ</p>
 * <p>バックアップ対象のファイル名規約（ファイル名に日時がある）が共通処理と異なる為、AS400専用に作成</p>
 * <p>バックアップしたファイルの退役処理も行う</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @version 3.00 (2013/07/25) M.Ayukawa    [MSTC00028] 商品変更リスト作成対応
 */
public class MBD00501_FileBackupAS400{

	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private String pram = null;

	// バッチ日付
	private String batchDate = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00501_FileBackupAS400( MasterDataBase db ) {
	}

	/**
	 * コンストラクタ
	 */
	public MBD00501_FileBackupAS400() {

		this( new MasterDataBase( mst000101_ConstDictionary.CONNECTION_STR ) );
	}


	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {

		try {

			// 処理開始ログ
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);

			// バックアップ対象のファイル名を取得し、バックアップ処理パラメータを設定する。
			// バックアップ元ファイル名を設定し、パラメータを完成させる
			Vector vec = new Vector();
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW);
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD);
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST);
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW_FINISH);
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD_FINISH);
			setBackupFileName(vec, mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST_FINISH);

			// パラメータを元にバックアップを行う。
			for(int tmp = 0; tmp < vec.size(); tmp++){
				FileBackupUtil fbUtil = (FileBackupUtil)vec.get(tmp);

				// ファイル名置き換え
				String tmpFileName = fbUtil.getFromFileName();
				if (pram != null && tmpFileName != null) {
					fbUtil.setFromFileName(tmpFileName.replace("\\1", pram));
				}
				tmpFileName = fbUtil.getToFileName();
				if (pram != null && tmpFileName != null) {
					fbUtil.setToFileName(tmpFileName.replace("\\1", pram));
				}

				this.writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップ処理を開始します。");

				int ret = fbUtil.backUp();

				if(ret == FileBackupUtil.SUCCESS){
					this.writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップを完了しました。");
				}else{
					this.writeLog(Level.INFO_INT, fbUtil.getFromFileName() +"のバックアップが完了できませんでした。");
				}
			}

			// バックアップファイルの退役処理
			this.writeLog(Level.INFO_INT, "バックアップファイル退役処理を開始します。");
			deleteBackupFile();
			this.writeLog(Level.INFO_INT, "バックアップファイル退役処理を完了しました。");

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			this.writeError(se);
			throw se;

		// 例外処理
		} catch ( Exception e ) {

			// ログ出力
			this.writeError(e);

			throw e;
		}finally{
			writeLog(Level.INFO_INT, "処理を終了します。");
		}
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// バッチ日付取得
		batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
		if(batchDate == null || batchDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
			throw new Exception();
		}
	}

	/**
	 * ファイル名リストから１つずつファイル名を取り出しVectorに格納する
	 * @param Vector
	 * @param String
	 */
	protected void setBackupFileName(Vector vec, String param) throws Exception {
		// ファイル名の取得（ワイルドカード「*」が指定されている場合は、該当ファイル名を取得する）
		FileControl fc = new FileControl();
		String dirName = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_SOURCE_DIR_NAME);
		String fileName = ResorceUtil.getInstance().getPropertie(param);

		// ファイル名をセット
		List syohinNewData = fc.listFileNames(dirName, fileName, false);
		for (Iterator iter = syohinNewData.iterator(); iter.hasNext();) {
			FileBackupUtil fbUtil = new FileBackupUtil();
			setFileBackupUtilDefault(fbUtil);
			fbUtil.setFromFileName((String) iter.next());
			vec.add(fbUtil);
		}
	}

	/**
	 * バックアップ処理パラメータをデフォルト設定する
	 * @param FileBackupUtil
	 */
	protected void setFileBackupUtilDefault(FileBackupUtil defFbUtil) throws Exception {
		// バックアップ元ファイルが存在しなかった場合の動作（setActionOfNotExist）
		defFbUtil.setActionOfNotExist(-1);			// EXISTS_NO_REACTION
		// バックアップ先ファイルが既に存在していた場合の動作（setActionOfExist）
		defFbUtil.setActionOfExist(0);				// EXISTS_OVERWRITE
		// 圧縮を行うか行わないか（setCompression）
		defFbUtil.setCompression("ZIP");			// COMPRESSION_ZIP
		// バックアップ先ファイル名命名規則（setFilenameAddition）
		defFbUtil.setFilenameAddition((short)1);	// FILENAME_KEEP_ORIGINAL
		// 世代管理数（setGeneration）
		defFbUtil.setGeneration(10);				// 10世代（通常２世代（csv、chk）となるが、余裕を持たせて１０世代と設定する）
		// バックアップ後処理（setDeleteOnBackUp）
		defFbUtil.setAfterProcessToFromfile(1);		// AFTERPROCESS_TO_FROMFILE_DELETE
		// バックアップ元パス（setFromPath）
		defFbUtil.setFromPath(ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_SOURCE_DIR_NAME));
		// バックアップ元ファイル名（setFromFileName）
		//defFbUtil.setFromFileName("");			// 別途設定
		// バックアップ先パス（setToPath）
		defFbUtil.setToPath(ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_BACKUP_DIR_NAME));
		// バックアップ先ファイル名（setToFileName）
		//defFbUtil.setToFileName("");				// 設定不要
	}

	/**
	 * バックアップファイルの退役処理
	 * @param Vector
	 */
	protected void deleteBackupFile() throws Exception {
		// ファイル名の取得（ワイルドカード「*」が指定されている場合は、該当ファイル名を取得する）
		FileControl fc = new FileControl();
		String dirName = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_BACKUP_DIR_NAME);
		String fileName = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_BACKUP);
		String spanDay = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_IF_BACKUP_SPAN);
		writeLog(Level.INFO_INT, "保有日数は" + spanDay + "日です。");

		// ファイル名をセット
		File[] lfs = fc.listFiles(dirName, fileName, true, batchDate, (Integer.parseInt(spanDay) + 1) * -1);
		for (int i = 0; i < lfs.length; i++) {
			String fName = lfs[i].toString();
			// ファイルの削除
			if (lfs[i].delete()){
				writeLog(Level.INFO_INT, fName + "ファイルを削除しました。");
			}else{
				writeLog(Level.INFO_INT, fName + "ファイルの削除に失敗しました。");
			}
		}
	}

	/********* 以下、ログ出力用メソッド *********/

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
