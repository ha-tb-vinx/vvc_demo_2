package mdware.common.batch.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.batch.master.ExecBcp;
import mdware.batch.master.bean.BcpBean;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.batch.util.file.FileSelector;
import mdware.common.util.DateUtility;
import mdware.common.util.FunctionBeanHolder;
import mdware.common.util.SystemConfBeanHolder;
import mdware.common.util.SystemControlUtil;

/**
 * <p>タイトル: AbstractMasterLoading.java</p>
 * <p>説明: マスタローディング処理を行います。</p>
 *
 * プロパティファイル配置場所
 * \WEB-INF\properties\rbsite\master\import.properties
 * bspコマンド プロパティファイル
 * \WEB-INF\properties\rbsite\master\format\****.fmt
 *
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * <p>日付: 2003.10.10</p>
 * @author S.Sakai
 * @version 1.00
 * @version 1.10 2004/07/20 kaneda Oracle,SQL Serverへの対応
 * @version 1.20 2004/07/22 sakai ログ出力等の見直し
 * @version 1.30 2004/09/02 sakai ローディングディレクトリ変更対応 
 * @version 1.40 2004/09/14 okuno トランケートせずにローディングする処理を追加
 * @version 1.50 2004/09/15 sakai 時間取得関数を修正
 * @version 1.60 2004/09/21 sakai デリート処理通知がDebugモードでログ記録されていたのを修正
 * @version 1.70 2004/09/23 okuno batchJobID,batchNameへセットする処理を追加
 * @version 1.80 2004/09/24 sakai バックアップ処理時間の取得関数を修正
 * @version 1.81 2005/09/22 tokuda database ゲッターメソッド追加
 * */
public abstract class AbstractMasterLoading {
	private static final String SUFFIX_DATA = ".dat";
	private static final String SUFFIX_BAK = ".zip";
	private static final String DB_KIND_ORA = "ora";
	private static final String DB_KIND_MSSQL = "mssql";
	private FunctionBeanHolder funcBh = null;
	private SystemConfBeanHolder sysBh = null;
	private DataBase dataBase = null;
	protected String longNow = DateUtility.longNowBatch(); //現在日時
	protected String today = SystemControlUtil.getBatchDate(); //現在日付
	protected BatchLog batchLog = BatchLog.getInstance();
	private ExecBcp execBcp = null;
	protected String batchJobID = null;
	protected String batchName = null;
	protected static boolean fileLoadingFlag = true;
	private String shareDir = "";
	private boolean doTruncate = true;	//Added	2004/09/14	H.Okuno

	/**
	 * コンストラクタ<BR>
	 * マスタローディング処理前に、IFテーブルの状態をログ出力します
	 * ログ出力削除
	 */
	protected AbstractMasterLoading() {
		funcBh = new FunctionBeanHolder();
		sysBh = new SystemConfBeanHolder();
		dataBase = new DataBase("rbsite_ora");
		execBcp = new ExecBcp();
		//2004.09.02 sakai ローディングディレクトリ変更対応
		shareDir = sysBh.getSysConfBean().getShareDirMasterTx();
	}

	/**
	 * マスタローディング処理の開始メソッドです(引数としてデータ種別を与える場合)<BR>
	 * 実装クラスは、このメソッドから処理を開始してください
	 * @param dataSyubetuCd データ種別コード
	 * @throws Exception
	 */
	public abstract void execute(String dataSyubetuCd) throws Exception;

	/**
	 * マスタローディング処理の開始メソッドです(引数なしの場合)<<BR>
	 * 実装クラスは、このメソッドから処理を開始してください
	 * @throws Exception
	 */
	public abstract void execute() throws Exception;

	/**
	 * ローディング前にトランケートする様に設定する<<BR>
	 * （デフォルトは「トランケートする」です）
	 */
	protected void setTruncateOn() {
		this.doTruncate = true;
	}
	
	/**
	 * ローディング前にトランケートしない様に設定する<<BR>
	 * （デフォルトは「トランケートする」です）
	 */
	protected void setTruncateOff() {
		this.doTruncate = false;
	}
	
	/**
	 * マスタファイルを読込み、bcpExecImportメソッドへ処理を委譲します
	 * @param dataSyubetuCd データ種別コード(マスタファイルのパス取得する為のキー)
	 * @return 処理件数
	 * @throws Exception
	 */
	protected long bcpImport(String dataSyubetuCd) throws Exception {
		long resultCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		try {
			//*** 配信処理開始 ***//
			String mname = funcBh.getDataClassificationName(dataSyubetuCd);
			//R_KINO ファイル名取得
			String prefix = funcBh.getIfFileHeadName(dataSyubetuCd);
			//SYSTEM_CONF ディレクトリ取得 2004.09.02 sakai ローディングディレクトリ変更対応
			String shareDir = this.shareDir;
			String backupDir = sysBh.getSysConfBean().getBackupDirTx();
			//ディレクトリからファイルを読込む
			File[] files = FileSelector.getFileListWithPrifixSuffix(shareDir, prefix, this.SUFFIX_DATA);
			//データファイル存在しなければ、読込処理を中止してresultCnt==0を返す
			if (files.length < 1) {
				batchLog.getLog().warn(
					batchJobID + " " + batchName + " : " + mname + "インポート対象ファイル(" + prefix + ".dat)は存在しませんでした。");
				return resultCnt;
			}
			String fileName = files[0].getName();
			//データファイルパス
			String dataFilePath = shareDir + "/" + files[0].getName();
			//バックアップファイルパス
			String bakFilePath =
				backupDir + "/" + FileSelector.getFileNameWithoutSuffix(fileName) + "_" + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + this.SUFFIX_BAK;
			//最新のファイルについてマスタ取込み処理を行う。
			batchLog.getLog().info(batchJobID + " " + batchName + " : " + mname + "ファイル（" + fileName + "）を取込みます。");
			//①先ずバックアップ処理行う
			if (!DataExchanger.exchange(DataExchanger.ZIP, dataFilePath, bakFilePath)) {
				batchLog.getLog().warn(
					batchJobID + " " + batchName + " : " + mname + "ファイル（" + fileName + "）のバックアップ処理に失敗しました。");
				//バックアップ処理に失敗した場合はwarnを記録して処理続行
			}
			//②○○.dat → TMP_○○ へのInsert＋Update//
			try {
				resultCnt = this.bcpExecImport(this.getBcpCode(), dataFilePath);
			} catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		}
		return resultCnt;
	}

	/**
	 * 読込んだマスタファイルを削除します
	 * @param dataSyubetuCd データ種別コード
	 * @throws Exception
	 */
	protected void bcpImportRmfile(String dataSyubetuCd) throws Exception {
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		try {
			String mname = funcBh.getDataClassificationName(dataSyubetuCd);
			String prefix = funcBh.getIfFileHeadName(dataSyubetuCd);
			//2004.09.02 sakai ローディングディレクトリ変更対応
			String shareDir = this.shareDir;
			File[] files = FileSelector.getFileListWithPrifixSuffix(shareDir, prefix, this.SUFFIX_DATA);
			if (files.length < 1) {
				batchLog.getLog().warn(batchJobID + " " + batchName + " : " + mname + "ファイルが存在しませんでした。");
				return;
			}
			String fileName = files[0].getName();
			String dataFilePath = shareDir + "/" + files[0].getName();
			//読み込んだファイルを削除する。
			if (!DataExchanger.deleteFile(dataFilePath)) {
				//データファイル
				batchLog.getLog().warn(batchJobID + " " + batchName + " : " + mname + "ファイルの削除に失敗しました。");
			}
			/*			
			batchLog.getLog().info(
				batchJobID + " " + batchName + " : " + mname + "インポートが終了しました。");
			*/
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * BULK INSERT、又はSQL*Loaderを使用してマスタファイルをTMPテーブルにインポートします
	 * @param bcpCode BCPコード
	 * @param masterFileParh マスタファイルパス
	 * @return 処理件数
	 * @throws Exception
	 */
	private long bcpExecImport(String bcpCode, String masterFileParh) throws Exception {
		long resultCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		//Import.proparties の情報を bcpBean に格納する
		BcpBean bcpBean = (BcpBean) this.execBcp.getBcpPropertiesMap().get(bcpCode);
		//*** 以下は共通で存在するものとする ***//
		String Table = bcpBean.getMasterName();
		String dbKind = bcpBean.getDBKind();
		try {
			long dataCnt = 0;
			FileReader fileReader = new FileReader(masterFileParh);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String tempString = "";
			while ((tempString = bufferedReader.readLine()) != null) {
				// 空行なら読み飛ばす。
				if (tempString.trim().length() != 0) {
					dataCnt++;
				}
			}
			fileReader.close();
			if (dataCnt == 0) {
				batchLog.getLog().warn(batchJobID + " " + batchName + " : " + "ファイルにデータがありません。");
				//ファイルは存在するが、データが存在しないとき、ログを記録して処理中断
				return dataCnt;
			}
		} catch (IOException e) {
			throw e;
		}
		try {
			//トランケートする/しないを判断する処理を追加	2004/09/14	H.Okuno
			if(doTruncate){
				//TMPテーブルを全削除
				String delIfSql = "truncate table " + Table;
				dataBase.executeUpdate(delIfSql);
				dataBase.commit();
			}
			//IFにインポートする
			if (dbKind.equals(this.DB_KIND_ORA)) {
				execBcp.importMasterOracle(bcpCode, masterFileParh);
			} else if (dbKind.equals(this.DB_KIND_MSSQL)) {
				execBcp.importMasterMSSQL(bcpCode, masterFileParh);
			} else {
				// エラーコード
				throw new Exception();
			}
			//インポートの確認
			String selIfSql = "select count(*) from " + Table;
			ResultSet rs = dataBase.executeQuery(selIfSql);
			long impCnt = 0;
			if (rs.next()) {
				impCnt = rs.getLong(1);
				batchLog.getLog().info(
					batchJobID + " " + batchName + " : " + impCnt + "件のデータを登録しました(table:" + Table + ")");
			}
			if (impCnt == 0) { //all or nothing...
				String dbKindMsg = bcpBean.getDBKind().equals(DB_KIND_MSSQL) ? "BULK INSER" : "SQL*Loader";
				throw new SQLException(dbKindMsg + "でエラーが発生しました(table:" + Table + ")");
			}
			resultCnt = impCnt;
		} catch (SQLException sqle) {
			dataBase.rollback();
			dataBase.close(); // 20030120 @ADD yamanaka
			throw sqle;
		}
		return resultCnt;
	}

	/**
	 * インポート先のテーブルをトランケートして、データをインポートします
	 * @param tableName インポート先テーブル名
	 * @param loadingSql インポートSQL文
	 * @return 処理件数
	 * @throws Exception
	 */
	protected long sqlExecImport(String tableName, String loadingSql) throws Exception {
		long resultCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		try {
			//③テーブルからテーブルへのInsert＋Updateを行うexecuteQuery
			String delSql = "delete from " + tableName;
			dataBase.executeUpdate(delSql);
			dataBase.commit();
			batchLog.getLog().info(batchJobID + " " + batchName + " : " + tableName + "をデリートしました。");
			try {
				batchLog.getLog().debug(tableName + "ローディングSQL：" + loadingSql);
				resultCnt = dataBase.executeUpdate(loadingSql);
				batchLog.getLog().info(
					batchJobID + " " + batchName + " : " + resultCnt + "件のデータを登録しました(table:" + tableName + ")");
				dataBase.commit();
				dataBase.close();
			} catch (SQLException sqle) {
				dataBase.rollback();
				dataBase.close();
				throw sqle;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return resultCnt;
	}

	/**
	 * トランケートしないで、データをインポートします
	 * @param tableName インポート先テーブル名
	 * @param loadingSql インポートSQL文
	 * @param sqlType "更新"・"追加"・"削除"等、実行した
	 * @return 処理件数
	 * @throws Exception
	 */
	protected long sqlExecImportWithoutTrun(String tableName, String loadingSql, String sqlType) throws Exception {
		long resultCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		try {
			//③テーブルからテーブルへのInsert＋Updateを行う
			try {
				batchLog.getLog().debug(tableName + sqlType + "SQL：" + loadingSql);
				resultCnt = dataBase.executeUpdate(loadingSql);
				batchLog.getLog().info(
					batchJobID
						+ " "
						+ batchName
						+ " : "
						+ resultCnt
						+ "件のデータを"
						+ sqlType
						+ "しました(table:"
						+ tableName
						+ ")");
				dataBase.commit();
				dataBase.close();
			} catch (SQLException sqle) {
				dataBase.rollback();
				dataBase.close();
				throw sqle;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return resultCnt;
	}

	/**
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected long sqlExecCountWithDeleteKb(String tableName) throws Exception {
		long impCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		String selIntSql = "select count(*) from " + tableName + " where delete_kb is null";
		ResultSet rs;
		try {
			rs = dataBase.executeQuery(selIntSql);
			if (rs.next()) {
				impCnt = rs.getLong(1);
				batchLog.getLog().info(
					batchJobID
						+ " "
						+ batchName
						+ " : "
						+ impCnt
						+ "件のデータが存在します(table:"
						+ tableName
						+ ",削除区分 != 'D')。");
			}
			selIntSql = "select count(*) from " + tableName + " where delete_kb = 'D'";
			rs = dataBase.executeQuery(selIntSql);
			impCnt = 0;
			if (rs.next()) {
				impCnt = rs.getLong(1);
				batchLog.getLog().info(
					batchJobID
						+ " "
						+ batchName
						+ " : "
						+ impCnt
						+ "件のデータが存在します(table:"
						+ tableName
						+ ",削除区分 == 'D')。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return impCnt;
	}
	/**
	 * テーブル名
	 * @param tableName
	 * @return データ件数
	 * @throws Exception
	 */
	protected long sqlExecCount(String tableName) throws Exception {
		long impCnt = 0;
		setBatchJobID(getBatchJobID());	//Add	2004/09/23	H.Okuno
		setBatchName(getBatchName());	//Add	2004/09/23	H.Okuno
		String selIntSql = "select count(*) from " + tableName + " ";
		ResultSet rs;
		try {
			rs = dataBase.executeQuery(selIntSql);
			if (rs.next()) {
				impCnt = rs.getLong(1);
				batchLog.getLog().info(
					batchJobID + " " + batchName + " : " + impCnt + "件のデータが存在します(table:" + tableName + ")。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return impCnt;
	}

	/**
	 * (必須) BCPコードを取得します<BR>
	 * BCPコードは、inport.propertiesからINTテーブルのINSERTに<BR>
	 * 必要な情報をを取得する時のキーになります
	 * @return BCPコード
	 */
	protected abstract String getBcpCode();

	/**
	 * (必須) バッチIDを取得します
	 * @return
	 */
	protected abstract String getBatchJobID();

	/**
	 * (必須) バッチ名称を取得します
	 * @return
	 */
	protected abstract String getBatchName();

	/**
	 * @param string
	 */
	protected void setBatchJobID(String string) {
		batchJobID = string;
	}

	/**
	 * @param string
	 */
	public void setBatchName(String string) {
		batchName = string;
	}
	//2004.09.02 sakai ローディングディレクトリ変更対応 Start
	/**
	 * @param 
	 */
	public void setShareDirMasterTx() {
		shareDir = sysBh.getSysConfBean().getShareDirMasterTx();
	}
	/**
	 * @param 
	 */
	public void setShareDirCollectTx() {
		shareDir = sysBh.getSysConfBean().getShareDirCollectTx();
	}
	//2004.09.02 sakai ローディングディレクトリ変更対応 End


//  ↓2005/09/22 tokuda ゲッターメソッド追加
	/**
	 * データベースを取得します。
	 * @return DataBase
	 */
	protected DataBase getDataBase() {
		return dataBase;
	}
//	↑2005/09/22 tokuda ゲッターメソッド追加

}
