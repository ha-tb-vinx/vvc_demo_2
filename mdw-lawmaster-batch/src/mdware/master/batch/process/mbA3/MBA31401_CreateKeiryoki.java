package mdware.master.batch.process.mbA3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用計量器マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author SIC A.Okada
 * @version 1.00 2015/10/16 Sou OHPA用システムコントロールデータ修正
 */

public class MBA31401_CreateKeiryoki {

	// CSVファイル名（計量器マスタ）
	private static final String FILE_NAME		= "IF_R_KEIRYOKI.csv";
	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	// 計量器送付対象DPT
	private String sendIfBunrui1Cd = null;

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;
	// CSVファイルパス
	private String 		csvFilePath = null;
	// システム日付
	private String 		systemDt 	= null;

	// 処理当日から基準日までの間隔
	private static final int SYORI_SPAN = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA31401_CreateKeiryoki( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA31401_CreateKeiryoki() {

		this( new MasterDataBase( "rbsite_ora" ) );
		closeDb = true;

	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute( String batchId ) throws Exception {

		execute();

	}

	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		long lngOPCnt = 0;

		try {

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();

			// CSVファイル削除
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を開始します。");
			this.deleteCSVFile();
			this.writeLog(Level.INFO_INT, "既存ファイルの削除処理を終了します。");


			// CSVファイル作成
			this.writeLog(Level.INFO_INT, FILE_NAME + "の出力処理を開始します。");
			lngOPCnt = this.createCSVFile( this.getSelDataSQL() );
			if( lngOPCnt == 0 ){
				this.writeLog(Level.INFO_INT, FILE_NAME + "への出力対象データがありませんでした。");
			}else{
				this.writeLog(Level.INFO_INT, FILE_NAME + "へ" + lngOPCnt + "件のデータを出力しました。");
			}

			this.writeLog(Level.INFO_INT, "処理を終了します。");

		}catch( Exception e ){
			db.rollback();

			this.writeError(e);

			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
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
		csvFilePath = null;

    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}

    	// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.CSVPATH_JOHO);

    	if(csvFilePath == null || csvFilePath.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、情報分析用のCSVファイルパスが取得できませんでした");
    		throw new Exception();
		}

    	// 作成対象のDTP
		Map map = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_KEIRYOKI_BUMON_CD);
		sendIfBunrui1Cd = SqlSupportUtil.createInString(map.keySet().toArray());
	}

	/**
	 * CSVファイル削除処理
	 * @return int
	 * @throws IOException
	 */
	private void deleteCSVFile() throws IOException, Exception{

		String	fileName	= null;
		File 	file 		= null;

		// 情報分析用CSVファイル格納パス、ファイル名
		file 	 = new File(csvFilePath);
		fileName = new File(csvFilePath) + "/" + FILE_NAME;

		if( file.exists() == false ){
			//　削除ディレクトリが見つからなければ
    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
			throw new Exception();
		} else {
			// 削除
			new File( fileName ).delete();
    		this.writeLog(Level.INFO_INT, FILE_NAME + " の削除処理を実施しました。");
		}
	}

	/**
	 * 計量器マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(BUNRUI1_CD)           AS BUNRUI1_CD, ");
		sb.append("    TRIM(SYOHIN_CD)            AS SYOHIN_CD, ");
		sb.append("    TRIM(YUKO_DT)              AS YUKO_DT, ");
		sb.append("    TRIM(SYOHIN_YOBIDASI)      AS SYOHIN_YOBIDASI, ");
		sb.append("    TRIM(S_GYOSYU_CD)          AS S_GYOSYU_CD, ");
		sb.append("    TRIM(THEME_CD)             AS THEME_CD, ");
		sb.append("    TRIM(KEIRYO_HANKU_CD)      AS KEIRYO_HANKU_CD, ");
		sb.append("    TRIM(HANEI_DT)             AS HANEI_DT, ");
		sb.append("    TRIM(SYOHIN_KBN)           AS SYOHIN_KBN, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(KEIRYOKI_NA)          AS KEIRYOKI_NA, ");
		//sb.append("    TRIM(KEIRYOKI2_NA)         AS KEIRYOKI2_NA, ");
		//sb.append("    TRIM(KEIRYOKI3_NA)         AS KEIRYOKI3_NA, ");
		//sb.append("    TRIM(RECEIPT_HINMEI_NA)    AS RECEIPT_HINMEI_NA, ");
		sb.append( " '\"' || TRIM(KEIRYOKI_NA) || '\"' AS KEIRYOKI_NA, " );
		sb.append( " '\"' || TRIM(KEIRYOKI2_NA) || '\"' AS KEIRYOKI2_NA, " );
		sb.append( " '\"' || TRIM(KEIRYOKI3_NA) || '\"' AS KEIRYOKI3_NA, " );
		sb.append( " '\"' || TRIM(RECEIPT_HINMEI_NA) || '\"' AS RECEIPT_HINMEI_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(TEIGAKU_UP_KB)        AS TEIGAKU_UP_KB, ");
		sb.append("    TANKA_VL, ");
		sb.append("    TEIGAKU_VL, ");
		sb.append("    TRIM(TEIGAKUJI_TANI_KB)    AS TEIGAKUJI_TANI_KB, ");
		sb.append("    TRIM(SYOMIKIKAN_KB)        AS SYOMIKIKAN_KB, ");
		sb.append("    SYOMIKIKAN_VL, ");
		sb.append("    TRIM(SANTI_KB)             AS SANTI_KB, ");
		sb.append("    TRIM(KAKOJIKOKU_PRINT_KB)  AS KAKOJIKOKU_PRINT_KB, ");
		sb.append("    TRIM(CHORIYO_KOKOKUBUN_KB) AS CHORIYO_KOKOKUBUN_KB, ");
		sb.append("    TRIM(HOZON_ONDOTAI_KB)     AS HOZON_ONDOTAI_KB, ");
		sb.append("    TRIM(START_KB)             AS START_KB, ");
		sb.append("    TRIM(BACK_LABEL_KB)        AS BACK_LABEL_KB, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(EIYO_SEIBUN_NA)       AS EIYO_SEIBUN_NA, ");
		sb.append( " '\"' || TRIM(EIYO_SEIBUN_NA) || '\"' AS EIYO_SEIBUN_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(COMMENT_KB)           AS COMMENT_KB, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(BIKO_TX)              AS BIKO_TX, ");
		//sb.append("    TRIM(GENZAIRYO_NA)         AS GENZAIRYO_NA, ");
		//sb.append("    TRIM(TENKABUTU_NA)         AS TENKABUTU_NA, ");
		sb.append( " '\"' || TRIM(BIKO_TX) || '\"' AS BIKO_TX, " );
		sb.append( " '\"' || TRIM(GENZAIRYO_NA) || '\"' AS GENZAIRYO_NA, " );
		sb.append( " '\"' || TRIM(TENKABUTU_NA) || '\"' AS TENKABUTU_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(MIN_WEIGHT_QT)        AS MIN_WEIGHT_QT, ");
		sb.append("    TRIM(MAX_WEIGHT_QT)        AS MAX_WEIGHT_QT, ");
		sb.append("    TRIM(TEIKAN_WEIGHT_QT)     AS TEIKAN_WEIGHT_QT, ");
		sb.append("    TRIM(FUTAI_WEIGHT_QT)      AS FUTAI_WEIGHT_QT, ");
		sb.append("    TRIM(EYE_CATCH_NO)         AS EYE_CATCH_NO, ");
		sb.append("    TRIM(EYE_CATCH_MODE)       AS EYE_CATCH_MODE, ");
		sb.append("    TRIM(TEIGAKU_KB)           AS TEIGAKU_KB, ");
		sb.append("    TEIGAKU_BAIKA_VL, ");
		sb.append("    HOZON_ONDO_QT, ");
		sb.append("    TRIM(CALORIE)              AS CALORIE, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(TRAY_NA)              AS TRAY_NA, ");
		//sb.append("    TRIM(NETSUKEKI_NA)         AS NETSUKEKI_NA, ");
		//sb.append("    TRIM(NETSUKEKI_NA_2)       AS NETSUKEKI_NA_2, ");
		sb.append( " '\"' || TRIM(TRAY_NA) || '\"' AS TRAY_NA, " );
		sb.append( " '\"' || TRIM(NETSUKEKI_NA) || '\"' AS NETSUKEKI_NA, " );
		sb.append( " '\"' || TRIM(NETSUKEKI_NA_2) || '\"' AS NETSUKEKI_NA_2, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(INSERT_TS)            AS INSERT_TS, ");
		sb.append("    TRIM(INSERT_USER_ID)       AS INSERT_USER_ID, ");
		sb.append("    TRIM(UPDATE_TS)            AS UPDATE_TS, ");
		sb.append("    TRIM(UPDATE_USER_ID)       AS UPDATE_USER_ID, ");
		sb.append("    TRIM(BATCH_UPDATE_TS)      AS BATCH_UPDATE_TS, ");
		sb.append("    TRIM(BATCH_UPDATE_ID)      AS BATCH_UPDATE_ID, ");
		sb.append("    TRIM(DELETE_FG)            AS DELETE_FG ");
		sb.append("FROM ");
		sb.append("    TMP_R_KEIRYOKI TB1 ");
		sb.append("WHERE ");
		sb.append("	   TB1.BUNRUI1_CD IN (" + sendIfBunrui1Cd + ") ");
		sb.append("AND TB1.SYOHIN_CD LIKE '02%' ");
		sb.append("AND TB1.YUKO_DT = ");
		sb.append("                  ( ");
		sb.append("                      SELECT ");
		sb.append("                          MAX( YUKO_DT ) ");
		sb.append("                      FROM ");
		sb.append("                          TMP_R_KEIRYOKI ");
		sb.append("                      WHERE ");
		sb.append("                          YUKO_DT <= '").append( DateChanger.addDate(batchDt, SYORI_SPAN) ).append("' ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sb.append("                      AND BUNRUI1_CD = TB1.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sb.append("                      AND SYOHIN_CD = TB1.SYOHIN_CD ");
		sb.append("                  ) ");
		sb.append("AND TB1.DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY ");
		sb.append("    BUNRUI1_CD, ");
		sb.append("    SYOHIN_CD, ");
		sb.append("    YUKO_DT ");

		return( sb.toString() );

	}

	/**
	 * CSVファイルを作成します。
	 *
	 * @param	String			SQL文字列
	 * @throws	IOException
	 * @throws	SQLException
	 * @throws	Exception
	 */
	private long createCSVFile(String selSql) throws IOException, SQLException, Exception{

		ResultSet		rs			= null;
		String			fileName	= null;
		File			file 		= null;
		// 2015/10/16 Sou エンコーディング指定 Start
		//FileWriter		fw 			= null;
		//BufferedWriter	bw 			= null;
		OutputStreamWriter osw = null;
		// 2015/10/16 Sou エンコーディング指定 End
		StringBuffer	sb			= new StringBuffer();
		long			lngOPCnt	= 0;

		try{
			// 情報分析用CSVファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);
			fileName = new File(csvFilePath) + "/" + FILE_NAME;

			if( file.exists() == false ){
				// ディレクトリが見つからなければ
	    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// システム日付取得
			systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );

			// データ取得
			rs = db.executeQuery(selSql);

			while( rs.next() ){

				// 2015/10/16 Sou エンコーディング指定 Start
				/*if( (fw == null) && (bw == null) ){
					// ファイルオープン
					fw = new FileWriter( fileName, true );
					bw = new BufferedWriter( fw );
				}*/

				if(osw == null){
					 osw = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
				}
				// 2015/10/16 Sou エンコーディング指定 End

				// 行データ作成
				sb.append( this.createRowData(rs) );

				// 行データ出力
				// 2015/10/16 Sou エンコーディング指定 Start
				//bw.write(sb.toString());
				osw.write(sb.toString());
				// 2015/10/16 Sou エンコーディング指定 End
				sb.setLength(0);

				lngOPCnt++;
			}

			// 最終ファイル
			if( !rs.next() ){
				// 2015/10/16 Sou エンコーディング指定 Start
				// ファイルクローズ
				/*if( bw != null ){
					bw.close();
				}
				if( fw != null ){
					fw.close();
				} */
				if( osw != null ){
					osw.close();
				}
				// 2015/10/16 Sou エンコーディング指定 End
			}

			if( closeDb ){
				if( rs != null ){
					db.closeResultSet(rs);
				}
				if( db != null ){
					this.db.close();
				}
			}

		} catch( Exception e ) {
			// ファイルクローズ
			// 2015/10/16 Sou エンコーディング指定 Start
			/*if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}*/
			if( osw != null ){
				osw.close();
			}
			// 2015/10/16 Sou エンコーディング指定 End
			throw e;
		}

		return lngOPCnt;
	}

	/**
	 * CSVファイルへ出力する行データを作成する
	 *
	 * @param		ResultSet       取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createRowData(ResultSet rs) throws SQLException, Exception{

		StringBuffer sb = new StringBuffer();

		// 分類１コード
		if( rs.getString("BUNRUI1_CD") != null ){
			sb.append( rs.getString("BUNRUI1_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品コード
		if( rs.getString("SYOHIN_CD") != null ){
			sb.append( rs.getString("SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 有効日
		if( rs.getString("YUKO_DT") != null ){
			sb.append( rs.getString("YUKO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 呼出NO
		if( rs.getString("SYOHIN_YOBIDASI") != null ){
			sb.append( rs.getString("SYOHIN_YOBIDASI").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小業種コード
		if( rs.getString("S_GYOSYU_CD") != null ){
			sb.append( rs.getString("S_GYOSYU_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// テーマコード
		if( rs.getString("THEME_CD") != null ){
			sb.append( rs.getString("THEME_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量販区コード
		if( rs.getString("KEIRYO_HANKU_CD") != null ){
			sb.append( rs.getString("KEIRYO_HANKU_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 反映日
		if( rs.getString("HANEI_DT") != null ){
			sb.append( rs.getString("HANEI_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品区分
		if( rs.getString("SYOHIN_KBN") != null ){
			sb.append( rs.getString("SYOHIN_KBN").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器名称
		if( rs.getString("KEIRYOKI_NA") != null ){
			sb.append( rs.getString("KEIRYOKI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器名称２
		if( rs.getString("KEIRYOKI2_NA") != null ){
			sb.append( rs.getString("KEIRYOKI2_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器名称３
		if( rs.getString("KEIRYOKI3_NA") != null ){
			sb.append( rs.getString("KEIRYOKI3_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// レシート品名
		if( rs.getString("RECEIPT_HINMEI_NA") != null ){
			sb.append( rs.getString("RECEIPT_HINMEI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定額・UP区分
		if( rs.getString("TEIGAKU_UP_KB") != null ){
			sb.append( rs.getString("TEIGAKU_UP_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 単価
		if( rs.getString("TANKA_VL") != null ){
			sb.append( rs.getString("TANKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定額時内容量
		if( rs.getString("TEIGAKU_VL") != null ){
			sb.append( rs.getString("TEIGAKU_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定額時単位区分
		if( rs.getString("TEIGAKUJI_TANI_KB") != null ){
			sb.append( rs.getString("TEIGAKUJI_TANI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 賞味期間計算区分
		if( rs.getString("SYOMIKIKAN_KB") != null ){
			sb.append( rs.getString("SYOMIKIKAN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 賞味期間
		if( rs.getString("SYOMIKIKAN_VL") != null ){
			sb.append( rs.getString("SYOMIKIKAN_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 産地番号
		if( rs.getString("SANTI_KB") != null ){
			sb.append( rs.getString("SANTI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 加工時刻印字区分
		if( rs.getString("KAKOJIKOKU_PRINT_KB") != null ){
			sb.append( rs.getString("KAKOJIKOKU_PRINT_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 調理用広告文
		if( rs.getString("CHORIYO_KOKOKUBUN_KB") != null ){
			sb.append( rs.getString("CHORIYO_KOKOKUBUN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 保存温度帯区分
		if( rs.getString("HOZON_ONDOTAI_KB") != null ){
			sb.append( rs.getString("HOZON_ONDOTAI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 開始日付区分
		if( rs.getString("START_KB") != null ){
			sb.append( rs.getString("START_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 裏面ラベル項目分区分
		if( rs.getString("BACK_LABEL_KB") != null ){
			sb.append( rs.getString("BACK_LABEL_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 栄養成分表示
		if( rs.getString("EIYO_SEIBUN_NA") != null ){
			sb.append( rs.getString("EIYO_SEIBUN_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// コメント区分
		if( rs.getString("COMMENT_KB") != null ){
			sb.append( rs.getString("COMMENT_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 備考
		if( rs.getString("BIKO_TX") != null ){
			sb.append( rs.getString("BIKO_TX").trim() );
		}
		sb.append( SPLIT_CODE );

		// 原材料名称
		if( rs.getString("GENZAIRYO_NA") != null ){
			sb.append( rs.getString("GENZAIRYO_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 添加物名称
		if( rs.getString("TENKABUTU_NA") != null ){
			sb.append( rs.getString("TENKABUTU_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 下限重量
		if( rs.getString("MIN_WEIGHT_QT") != null ){
			sb.append( rs.getString("MIN_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 上限重量
		if( rs.getString("MAX_WEIGHT_QT") != null ){
			sb.append( rs.getString("MAX_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定貫重量
		if( rs.getString("TEIKAN_WEIGHT_QT") != null ){
			sb.append( rs.getString("TEIKAN_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 風袋重量
		if( rs.getString("FUTAI_WEIGHT_QT") != null ){
			sb.append( rs.getString("FUTAI_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// アイキャッチ№
		if( rs.getString("EYE_CATCH_NO") != null ){
			sb.append( rs.getString("EYE_CATCH_NO").trim() );
		}
		sb.append( SPLIT_CODE );

		// アイキャッチモード
		if( rs.getString("EYE_CATCH_MODE") != null ){
			sb.append( rs.getString("EYE_CATCH_MODE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定額区分
		if( rs.getString("TEIGAKU_KB") != null ){
			sb.append( rs.getString("TEIGAKU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定額売価
		if( rs.getString("TEIGAKU_BAIKA_VL") != null ){
			sb.append( rs.getString("TEIGAKU_BAIKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 保存温度
		if( rs.getString("HOZON_ONDO_QT") != null ){
			sb.append( rs.getString("HOZON_ONDO_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// カロリー
		if( rs.getString("CALORIE") != null ){
			sb.append( rs.getString("CALORIE").trim() );
		}
		sb.append( SPLIT_CODE );

		// トレー名
		if( rs.getString("TRAY_NA") != null ){
			sb.append( rs.getString("TRAY_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 値付器名１
		if( rs.getString("NETSUKEKI_NA") != null ){
			sb.append( rs.getString("NETSUKEKI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 値付器名２
		if( rs.getString("NETSUKEKI_NA_2") != null ){
			sb.append( rs.getString("NETSUKEKI_NA_2").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成日時
		if( rs.getString("INSERT_TS") != null ){
			sb.append( rs.getString("INSERT_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成者
		if( rs.getString("INSERT_USER_ID") != null ){
			sb.append( rs.getString("INSERT_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新日時
		if( rs.getString("UPDATE_TS") != null ){
			sb.append( rs.getString("UPDATE_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新者
		if( rs.getString("UPDATE_USER_ID") != null ){
			sb.append( rs.getString("UPDATE_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// バッチ更新日時
		if( rs.getString("BATCH_UPDATE_TS") != null ){
			sb.append( rs.getString("BATCH_UPDATE_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// バッチ更新ID
		if( rs.getString("BATCH_UPDATE_ID") != null ){
			sb.append( rs.getString("BATCH_UPDATE_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 削除フラグ
		if( rs.getString("DELETE_FG") != null ){
			sb.append( rs.getString("DELETE_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// IF作成日時
		sb.append( systemDt );
		sb.append( RETURN_CODE );

		return sb;
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
