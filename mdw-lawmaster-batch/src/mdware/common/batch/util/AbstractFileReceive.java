package mdware.common.batch.util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mdware.common.batch.bean.BeanList;
import mdware.common.batch.log.BatchLog;
import mdware.batch.retailif.RetailInterface;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.batch.util.file.FileSelector;
import mdware.common.batch.util.file.record.RecordProperties;
import mdware.common.batch.util.file.record.Records;
import mdware.common.bean.SystemConfBean;
import mdware.common.util.DateUtility;
import mdware.common.util.FunctionBeanHolder;
import mdware.common.util.RetailBeanHolder;
import mdware.common.util.SystemConfBeanHolder;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル：集信処理抽象クラス</p>
 * <p>説明：集信処理のテンプレートクラスです</p>
 * <p>集信処理を実装するクラスはこのクラスを継承してください</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/14 kaneda 新規作成
 */
public abstract class AbstractFileReceive extends AbstractDistribute{

	/**
	 * コンストラクタ<BR>
	 * DBアクセサと、RecordPropertiesから取得した改行コードをメンバに設定します
	 * @param dataBase DBアクセサ
	 */
	public AbstractFileReceive(DataBase dataBase){
		super(dataBase);
	}
	
	/**
	 * コンストラクタ<BR>
	 * DB名"rbsite_ora"でDBアクセサを生成し、DBアクセサと<BR>
	 * RecordPropertiesから取得した改行コードをメンバに設定します
	 */
	public AbstractFileReceive(){
		super();
	}
	
	/**
	 * BeanListを作成する処理を実装してください
	 * @param filePath 集信ファイルパス
	 * @throws Exception
	 */
	protected abstract BeanList createBeanList(String filePath) throws Exception;
	
	/**
	 * テーブルをトランケートします
	 * @param tableName トランケートするテーブル名
	 * @throws Exception
	 */
	protected void trancateTable(String tableName) throws SQLException {
		String sql = "truncate table " + tableName;
		this.dataBase.executeUpdate(sql);
	}

	/**
	 * 集信ファイルのファイルパスを取得します
	 * @param dataKind データ種別コード
	 * @return 集信ファイルパス
	 */
	protected String getInputFilePath(String dataKind) {
		SystemConfBean bean = new SystemConfBeanHolder().getSysConfBean();

		String inputFileDir = bean.getShareDirCollectTx(); //集信用ディレクトリ
		File inputDir = new File(inputFileDir);

		String inputFileName = new FunctionBeanHolder().getIfFileHeadName(dataKind)
								+ RetailInterface.SUFFIX_DATA;  //機能ﾏｽﾀより取得
		String inputFilePath = inputFileDir + "/" + inputFileName;
		batchLog.getLog().info(this.getBatchID(), this.getBatchNa(), "集信ファイルパス：" + inputFilePath);

		if (!new File(inputFilePath).exists()) {
			return null;
		}

		return inputFilePath;
	}
}
			