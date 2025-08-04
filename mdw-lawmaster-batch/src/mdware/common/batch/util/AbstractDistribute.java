package mdware.common.batch.util;

import mdware.common.batch.log.BatchLog;
import mdware.batch.retailif.RetailInterface;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.batch.util.file.record.RecordProperties;
import mdware.common.batch.util.file.record.Records;
import mdware.common.bean.SystemConfBean;
import mdware.common.util.DateUtility;
import mdware.common.util.FunctionBeanHolder;
import mdware.common.util.RetailBeanHolder;
import mdware.common.util.SystemConfBeanHolder;
import jp.co.vinculumjapan.stc.util.db.DataBase;

/**
 * <p>タイトル：集配信処理抽象クラス</p>
 * <p>説明：集配信処理によく使用される処理を実装しています</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/21 kaneda 新規作成
 * @version 1.10 2004/09/21 sakai 内部改修 バックアップファイル作成時に時間との間にアンダースコアを挿入する
 * @version 1.20 2004/09/24 sakai バックアップ処理時間の取得関数を修正
 */
public abstract class AbstractDistribute {

	/** 改行コード */
	protected String returnStr = "";
	/** DBアクセサ */
	protected DataBase dataBase = null;
	/** DBコネクションのクローズ状態（true：open　false:close） */
	protected boolean closeDb = false;
	/** 現在日時　yyyyMMddHHmmss */
	protected final String longNow = DateUtility.longNow(); //現在日時
	/** 現在日付　yyyyMMdd */
	protected final String today = DateUtility.today(); //現在日付
	/** 現在時刻　HHmm */
	protected final String time = DateUtility.getTotime(); //現在時刻
	/** ログマネージャ */
	protected final BatchLog batchLog = BatchLog.getInstance();
	/** 小売コード */
	protected final String kouriCd = new RetailBeanHolder().getRetailBean().getKouriCd().trim(); //小売コードの取得

	/**
	 * コンストラクタ<BR>
	 * DBアクセサと、RecordPropertiesから取得した改行コードをメンバに設定します
	 * @param dataBase DBアクセサ
	 */
	public AbstractDistribute(DataBase dataBase) {
		this.dataBase = dataBase;
		if (this.dataBase == null) {
			this.dataBase = new DataBase("rbsite_ora");
			this.closeDb = true;
		}
		Records records = new Records();
		RecordProperties recordProperties = records.getRecordProperties(this.getFileKb());
		this.returnStr = recordProperties.getLineField();
	}

	/**
	 * コンストラクタ<BR>
	 * DB名"rbsite_ora"でDBアクセサを生成し、DBアクセサと<BR>
	 * RecordPropertiesから取得した改行コードをメンバに設定します
	 */
	public AbstractDistribute() {
		this(new DataBase("rbsite_ora"));
		this.closeDb = true;
	}

	/**
	 * 集配信処理実行メソッド<BR>
	 * このメソッドを起点に集配信処理を実装してください
	 */
	public abstract void execute() throws Exception;

	/**
	 * バッチログにエラーレコードを書き込みます。
	 * @param line 行番号
	 * @param comment コメント
	 * @param errorRecord エラーが発生したレコード
	 */
	protected void writeErrorRecord(long line, String comment, String errorRecord) {
		String err = "";
		if (line > -1) {
			err += "エラー行：" + line + " ";
		}
		if (comment != null && comment.trim().length() > 0) {
			err += comment;
		}
		batchLog.getLog().error(this.getBatchID(), this.getBatchNa(), err);
		batchLog.getLog().error(this.getBatchID(), this.getBatchNa(), errorRecord);
	}

	/**
	 * ファイル区分を取得します<BR>
	 * @return ファイル区分
	 */
	protected abstract String getFileKb();

	/**
	 * バッチIDを取得します
	 * @return バッチID
	 */
	protected abstract String getBatchID();

	/**
	 * バッチ処理名を取得します
	 * @return バッチ処理名
	 */
	protected abstract String getBatchNa();

	/**
	 * データ種別コードを取得します
	 * @return データ種別コード
	 */
	protected abstract String getDataKind();

	protected DataBase getNewDataBase() {
		return new DataBase("rbsite_ora");
	}

	/**
	 * 集信ファイルのバックアップ処理をします
	 * @param inputFilePath 集信ファイルパス
	 * @return 処理結果（true:成功　false:失敗）
	 */
	protected boolean createBackup(String inputFilePath) {
		SystemConfBean bean = new SystemConfBeanHolder().getSysConfBean();

		//バックアップファイルパス
		String backupFilePath =
			bean.getBackupDirTx()
				+ "/"
				+ new FunctionBeanHolder().getIfFileHeadName(this.getDataKind())
				+ "_"
				+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())
				+ RetailInterface.SUFFIX_BAK;
		if (!DataExchanger.exchange(DataExchanger.ZIP, inputFilePath, backupFilePath)) {
			batchLog.getLog().warn(this.getBatchID(), this.getBatchNa(), "\"" + inputFilePath + "\"のバックアップに失敗しました。");
		}
		return false;
	}
}
