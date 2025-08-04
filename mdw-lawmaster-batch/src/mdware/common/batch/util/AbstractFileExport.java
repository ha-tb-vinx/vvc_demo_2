package mdware.common.batch.util;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import mdware.common.batch.bean.BeanList;
import mdware.common.batch.log.BatchLog;
import mdware.batch.retailif.RetailInterface;
import mdware.common.batch.util.file.FileSelector;
import mdware.common.batch.util.file.record.RecordProperties;
import mdware.common.batch.util.file.record.Records;
import mdware.common.bean.EDownloadFileHeaderBean;
import mdware.common.bean.EDownloadFileHeaderDM;
import mdware.common.bean.EHachuFileHeaderBean;
import mdware.common.bean.EHachuFileHeaderDM;
import mdware.common.bean.EJcaHeaderBean;
import mdware.common.bean.EJcaHeaderDM;
import mdware.common.bean.ENohinFileHeaderBean;
import mdware.common.bean.ENohinFileHeaderDM;
import mdware.common.bean.SystemConfBean;
import mdware.common.dictionary.SyoriJoukyouSt1;
import mdware.common.util.DateUtility;
import mdware.common.util.FunctionBeanHolder;
import mdware.common.util.RetailBeanHolder;
import mdware.common.util.Seq;
import mdware.common.util.StringUtility;
import mdware.common.util.SystemConfBeanHolder;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル：配信処理抽象クラス </p>
 * <p>説明：配信処理のテンプレートクラスです</p>
 * <p>配信処理を実装するクラスはこのクラスを継承してください</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/14 kaneda 新規作成
 */
public abstract class AbstractFileExport extends AbstractDistribute{

	/**
	 * コンストラクタ<BR>
	 * DBアクセサと、RecordPropertiesから取得した改行コードをメンバに設定します
	 * @param dataBase DBアクセサ
	 */
	public AbstractFileExport(DataBase dataBase){
		super(dataBase);
	}
	
	/**
	 * コンストラクタ<BR>
	 * DB名"rbsite_ora"でDBアクセサを生成し、DBアクセサと<BR>
	 * RecordPropertiesから取得した改行コードをメンバに設定します
	 */
	public AbstractFileExport(){
		super();
	}
	
	/**
	 * 配信ファイルを作成する処理を実装してください
	 * @param beanList BeanList
	 * @throws Exception
	 */
	protected abstract int createFiles(BeanList beanList) throws Exception;

	/**
	 * 配信ファイルのファイルパスを取得します
	 * @return 配信ファイルパス
	 */
	protected String getOutputFilePath() throws Exception {
		SystemConfBean bean = new SystemConfBeanHolder().getSysConfBean();

		String outputFileDir = bean.getShareDirDistributeTx(); //配信用ディレクトリ
		File outputDir = new File(outputFileDir);

		if (!outputDir.exists()) {
			if (!outputDir.mkdirs()) {
				String errorMsg = "\"" + outputFileDir + "\"の作成に失敗しました。";
				batchLog.getLog().error(this.getBatchID(), this.getBatchNa(), errorMsg);
				throw new Exception(errorMsg);
			}
		}

		String outFileName = new FunctionBeanHolder().getIfFileHeadName(this.getDataKind())
								+ RetailInterface.SUFFIX_DATA;  //機能ﾏｽﾀより取得
		String outFilePath = outputFileDir + "/" + outFileName;
		batchLog.getLog().info(this.getBatchID(), this.getBatchNa(), "配信ファイルパス：" + outFilePath);

		//前回分が残っている場合、ファイルを削除
		File oldFile = new File(outFilePath);
		if (oldFile.exists()) {
			oldFile.delete();
		}

		return outFilePath;
	}

	/**
	 * 配信ファイルの出力先を取得します
	 * @param haisinsaki_cd 配信先コード
	 * @return 配信ファイル出力パス
	 * @throws Exception
	 */
	protected String getOutputFilePath(String haisinsaki_cd) throws Exception {
		String outputFileDir = new SystemConfBeanHolder().getDataStoredDirPath(this.getDataKind(), 
			this.kouriCd, haisinsaki_cd.trim());
		File outputDir = new File(outputFileDir);
		if (!outputDir.exists()) {
			if (!outputDir.mkdirs()) {
				String errorMsg = "\"" + outputFileDir + "\"の作成に失敗しました。";
				batchLog.getLog().error(this.getBatchID(), this.getBatchNa(), errorMsg);
				throw new Exception(errorMsg);
			}
		}

		String outputFileName = this.longNow + RetailInterface.SUFFIX_DATA;
		String outputFilePath = outputFileDir + "/" + FileSelector.getBranchFileName(outputFileDir, outputFileName);

		return outputFilePath;
	}
	
	/**
	 * EJCA制御電文情報をデフォルト値で新規登録します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param torihikisaki_cd 取引先コード
	 * @param haisinsaki_cd 配信先コード
	 * @param jca_nb JCA制御電文番号
	 * @return 登録されたEJcaHeaderBean
	 * @throws SQLException
	 */
	protected EJcaHeaderBean insertJcaHeader(String torihikisaki_cd, String haisinsaki_cd, String jca_nb) throws SQLException{
		EJcaHeaderBean bean = new EJcaHeaderBean();

		// JCA制御電文番号
		bean.setJcaControlNb(jca_nb);
		// 小売コード
		bean.setKouriCd(this.kouriCd);
		// ID
		bean.setIdCd("");
		// 配信先コード
		bean.setHaisinsakiCd(haisinsaki_cd);
		// 取引先コード
		bean.setTorihikisakiCd(torihikisaki_cd);
		// 要求区分
		bean.setYokyuKb("");
		// 伝送年月日
		bean.setDensoDt("");
		// センターコード
		bean.setCenterCd("");
		// 識別子
		bean.setSikibetusiKb("");
		// データ種類区分
		bean.setDataSyuruiKb(this.getDataKind());
		// データカウント１
		bean.setDataCount1Vl(0);
		// データカウント２
		bean.setDataCount2Vl(0);
		// データ変換日
		bean.setDataConvertDt("");
		// データベース格納日
		//bean.setDatabaseInsertDt(this.today);
		// 処理区分
		bean.setSyoriKb("");
		// データ種別コード
		bean.setDataSyubetuCd(this.getDataKind());
		// ファイル名称
		bean.setFileNa("");
		// 作成年月日
//		bean.setInsertTs(this.today);
		// 更新年月日
//		bean.setUpdateTs(this.today);

		EJcaHeaderDM dm = new EJcaHeaderDM();
		try{
			this.dataBase.executeUpdate(dm.getInsertSql(bean));
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"JCAヘッダの登録中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}finally{
			dm.close();
		}
		return bean;
	}

	/**
	 * ダウンロードファイルヘッダ情報をデフォルト値で新規登録します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param jca_nb JCA制御電文番号
	 * @param filePath ダウンロードファイルパス
	 * @return 登録されたEDownloadFileHeaderBean
	 * @throws SQLException
	 */
	protected EDownloadFileHeaderBean insertDownloadFileHeader(String jca_nb, String filePath) throws SQLException{
		EDownloadFileHeaderBean bean = new EDownloadFileHeaderBean();

		// ファイルヘッダ番号
		bean.setFileHeadNb(StringUtility.zeroFormat(Seq.nextVal("downfile"), 20));
		// JCA制御電文番号
		bean.setJcaControlNb(jca_nb);
		// 処理状況フラグ
		bean.setSyoriJyokyoFg(SyoriJoukyouSt1.MISYORI.getCode());
		// サーバ内保存ファイル名称
		bean.setServerFileNa(new SystemConfBeanHolder().getDownloadDirPath(filePath));
		// ブラウザ保存ファイル名称
		bean.setClientFileNa("");
		// ファイルサイズbyte
		bean.setFileLengthQt(String.valueOf(new File(filePath).length()));
		// 利用ユーザID
		bean.setRiyoUserId(this.getBatchID());
		// 作成年月日
//		bean.setInsertTs(this.today);
		// 更新年月日
//		bean.setUpdateTs(this.today);

		EDownloadFileHeaderDM dm = new EDownloadFileHeaderDM();
		try{
			this.dataBase.executeUpdate(dm.getInsertSql(bean));
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"ダウンロードファイルヘッダの登録中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}finally{
			dm.close();
		}
		return bean;
	}

	/**
	 * 発注ファイルヘッダ情報をデフォルト値で新規登録します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param jca_nb JCA制御電文番号
	 * @param filePath 発注ファイルパス
	 * @return 登録されたEHachuFileHeaderBean
	 * @throws SQLException
	 */
	protected EHachuFileHeaderBean insertHachuFileHeader(String jca_nb) throws SQLException{
		EHachuFileHeaderBean bean = new EHachuFileHeaderBean();

		// ファイルヘッダ番号
		bean.setFileHeadNb(String.valueOf(Seq.nextVal("orderfile")));
		// JCA制御電文番号
		bean.setJcaControlNb(jca_nb);
		// 処理状況フラグ
		bean.setSyoriJyokyoFg("0");
		// 伝発状況フラグ
		bean.setDenpatuJyokyoFg("0");
		// サーバ内保存ファイル名称
		bean.setServerFileNa("");		
		// ファイルサイズ
		bean.setFileLengthQt("0");		
		// 発注データ区分
		bean.setHachuDataKb("");
		// 利用ユーザID
		bean.setRiyoUserId(this.getBatchID());
		// 作成年月日
		//bean.setInsertTs(this.longNow);
		// 更新年月日
		//bean.setUpdateTs(this.longNow);

		EHachuFileHeaderDM dm = new EHachuFileHeaderDM();
		try{
			this.dataBase.executeUpdate(dm.getInsertSql(bean));
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"発注ファイルへッダの登録中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}finally{
			dm.close();
		}
		return bean;
	}
	
	/**
	 * 発注ファイルヘッダ情報を更新します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param bean 発注ファイルヘッダBean
	 * @return 処理件数
	 * @throws SQLException
	 */
	protected int updateHachuFileHeader(EHachuFileHeaderBean bean) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("update e_hachu_file_header set ");
		// 処理状況フラグ
		String syoriJyoKyoFg = bean.getSyoriJyokyoFgString();
		if(syoriJyoKyoFg != null && !syoriJyoKyoFg.trim().equals("")){
			sql.append("syori_jyokyo_fg=" + syoriJyoKyoFg + ", ");		
		}
		// サーバ内保存ファイル名称
		String serverFileNa = bean.getServerFileNaString();
		if(serverFileNa != null && !serverFileNa.trim().equals("")){
			sql.append("server_file_na=" + serverFileNa + ", ");		
		}
		// ファイルサイズ
		String fileLength = bean.getFileLengthQt();
		if(fileLength != null && !fileLength.trim().equals("")){
			sql.append("file_length_qt=" + StringUtility.adjustStringWithZero(fileLength, 14) + ", ");		
		}
		// 発注データ区分
		String hachuDataKb = bean.getHachuDataKb();
		if(hachuDataKb != null && !hachuDataKb.trim().equals("")){
			sql.append("hachu_data_kb=" + hachuDataKb + ", ");		
		}
		sql.append("riyo_user_id='" + this.getBatchID() + "', ");
		sql.append("update_ts= TO_CHAR(sysdate, 'YYYYMMDDHH24MISS') ");
		sql.append("where file_head_nb='" + bean.getFileHeadNb() + "'");
		
		int result = -1;
		try{
			result = this.dataBase.executeUpdate(sql.toString());
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"発注ファイルへッダの更新中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}
		return result;
	}
		
	/**
	 * 納品ファイルヘッダ情報をデフォルト値で新規登録します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param jca_nb JCA制御電文番号
	 * @return 登録されたENohinFileHeaderBean
	 * @throws SQLException
	 */
	protected ENohinFileHeaderBean insertNohinFileHeader(String jca_nb) throws SQLException{
		ENohinFileHeaderBean bean = new ENohinFileHeaderBean();
		// ファイルヘッダ番号
		bean.setFileHeadNb(String.valueOf(Seq.nextVal("nohinfile")));
		// JCA制御電文番号
		bean.setJcaControlNb(jca_nb);
		// 処理状況フラグ
		bean.setSyoriJyokyoFg("0");
		// サーバ内保存ファイル名称
		bean.setServerFileNa("");
		// ブラウザ保存ファイル名称
		bean.setClientFileNa("");
		// ファイルサイズ
		bean.setFileLengthQt("0");
		// 出力フラグ
		bean.setOutputFg( "0" );
		// ファイル受信フラグ
		//bean.set
		// 利用ユーザID
		bean.setRiyoUserId(this.getBatchID());
		// 作成年月日
//		bean.setInsertTs( this.today );
		// 更新年月日
//		bean.setUpdateTs( this.today );
		ENohinFileHeaderDM dm = new ENohinFileHeaderDM();
		try{
			this.dataBase.executeUpdate(dm.getInsertSql(bean));
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"納品ファイルへッダの登録中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}finally{
			dm.close();
		}
		return bean;
	}

	/**
	 * 納品ファイルヘッダ情報を更新します<BR>
	 * DBへのcommit、rollback、close処理は呼出し元で行ってください
	 * @param bean 納品ファイルヘッダBean
	 * @return 処理件数
	 * @throws SQLException
	 */
	protected int updateNohinFileHeader(ENohinFileHeaderBean bean) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("update e_nohin_file_header set ");
		// 処理状況フラグ
		String syoriJyoKyoFg = bean.getSyoriJyokyoFgString();
		if(syoriJyoKyoFg != null && !syoriJyoKyoFg.trim().equals("")){
			sql.append("syori_jyoko_fg=" + syoriJyoKyoFg + ", ");		
		}
		// サーバ内保存ファイル名称
		String serverFileNa = bean.getServerFileNaString();
		if(serverFileNa != null && !serverFileNa.trim().equals("")){
			sql.append("server_file_na=" + serverFileNa + ", ");		
		}
		// ブラウザ保存ファイル名称
		String clientFileNa = bean.getClientFileNaString();
		if(clientFileNa != null && !clientFileNa.trim().equals("")){
			sql.append("client_file_na=" + clientFileNa + ", ");		
		}
		// ファイルサイズ
		String fileLength = bean.getFileLengthQtString();
		if(fileLength != null && !fileLength.trim().equals("")){
			sql.append("file_length_qt=" + StringUtility.adjustStringWithZero(fileLength, 14) + ", ");		
		}
		// 出力フラグ
		String outputFg = bean.getOutputFgString();
		if(outputFg != null && !outputFg.trim().equals("")){
			sql.append("output_fg=" + StringUtility.adjustStringWithZero(fileLength, 14) + ", ");		
		}
		sql.append("riyo_user_id=" + this.getBatchID() + ", ");
		sql.append("update_ts=TO_CHAR(sysdate, 'YYYYMMDDHH24MISS') ");
		sql.append("where file_head_nb='" + bean.getFileHeadNb() + "'");
		
		int result = -1;
		try{
			result = this.dataBase.executeUpdate(sql.toString());
		}catch(SQLException e){
			batchLog.getLog().error(this.getBatchID(), this.getBatchNa(),
				"納品ファイルへッダの更新中にエラーが発生しました。 SQLエラーコード[" + e.getErrorCode() + "]");
			throw e;
		}
		return result;
	}
}
