package mdware.master.batch.process.mbA3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用店舗マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2009/01/28<BR>
 * @author SIC A.Okada
 */

public class MBA30601_CreateTenpo {

	// CSVファイル名（店舗マスタ）
	private static final String FILE_NAME		= "IF_R_TENPO.csv";
	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");

	// DB
	private MasterDataBase db			= null;
	private boolean		closeDb 	= false;

	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// CSVファイルパス
	private String 		csvFilePath = null;
	// システム日付
	private String 		systemDt 	= null;


	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA30601_CreateTenpo( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA30601_CreateTenpo() {

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
		csvFilePath = null;

		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.CSVPATH_JOHO);

    	if(csvFilePath == null || csvFilePath.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、情報分析用のCSVファイルパスが取得できませんでした");
    		throw new Exception();
		}
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
	 * 店舗マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(TENPO_CD)             AS TENPO_CD, ");
		sb.append("    TRIM(HOJIN_CD)             AS HOJIN_CD, ");
		sb.append("    TRIM(TENPOKAISO_TIIKI_CD)  AS TENPOKAISO_TIIKI_CD, ");
		sb.append("    TRIM(TENPOKAISO_AREA_CD)   AS TENPOKAISO_AREA_CD, ");
		sb.append("    TRIM(TENPOKAISO_BLOCK_CD)  AS TENPOKAISO_BLOCK_CD, ");
		// 2015/10/26 Zhou OHPA用店舗マスタデータ修正 Start
		//sb.append("    TRIM(KANJI_NA)             AS KANJI_NA, ");
		//sb.append("    TRIM(KANA_NA)              AS KANA_NA, ");
		//sb.append("    TRIM(KANJI_RN)             AS KANJI_RN, ");
		//sb.append("    TRIM(KANA_RN)              AS KANA_RN, ");
		sb.append(" '\"' ||   TRIM(KANJI_NA)  ||  '\"'  AS KANJI_NA, ");
		sb.append(" '\"' ||   TRIM(KANA_NA)   ||  '\"'  AS KANA_NA, ");
		sb.append(" '\"' ||   TRIM(KANJI_RN)  ||  '\"'  AS KANJI_RN, ");
		sb.append(" '\"' ||   TRIM(KANA_RN)   ||  '\"'  AS KANA_RN, ");
		// 2015/10/26 Zhou OHPA用店舗マスタデータ修正 End
		sb.append("    TRIM(TENPO_TYPE_KB)        AS TENPO_TYPE_KB, ");
		sb.append("    TRIM(TENPO_KB)             AS TENPO_KB, ");
		sb.append("    TRIM(KAITEN_DT)            AS KAITEN_DT, ");
		sb.append("    TRIM(HEITEN_DT)            AS HEITEN_DT, ");
		sb.append("    TRIM(ZAIMU_END_DT)         AS ZAIMU_END_DT, ");
		sb.append("    TRIM(OPEN_TM)              AS OPEN_TM, ");
		sb.append("    TRIM(CLOSE_TM)             AS CLOSE_TM, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 Start
		//sb.append("    TRIM(ADDRESS_KANJI_NA)     AS ADDRESS_KANJI_NA, ");
		sb.append(" '\"' ||  TRIM(ADDRESS_KANJI_NA)  ||  '\"'  AS ADDRESS_KANJI_NA, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 End
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 Start
		//sb.append("    TRIM(ADDRESS_KANA_NA)      AS ADDRESS_KANA_NA, ");
		sb.append("'\"'  ||    TRIM(ADDRESS_KANA_NA)  ||  '\"'  AS ADDRESS_KANA_NA, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 End
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 Start
		//sb.append("    TRIM(ADDRESS_3_NA)         AS ADDRESS_3_NA, ");
		sb.append(" '\"' ||   TRIM(ADDRESS_3_NA)     || '\"'   AS ADDRESS_3_NA, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 End
		sb.append("    TRIM(YUBIN_CD)             AS YUBIN_CD, ");
		sb.append("    TRIM(TEL_CD)               AS TEL_CD, ");
		sb.append("    TRIM(TRANS_TEL_CD)         AS TRANS_TEL_CD, ");
		sb.append("    TRIM(FAX_CD)               AS FAX_CD, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 Start
		//sb.append("    TRIM(EMAIL_ADDRESS)        AS EMAIL_ADDRESS, ");
		sb.append(" '\"' ||    TRIM(EMAIL_ADDRESS)   ||  '\"'  AS EMAIL_ADDRESS, ");
		// 2015/10/21 kou OHPA用店舗マスタデータ修正 End
		sb.append("    TRIM(HACHU_ST_DT)          AS HACHU_ST_DT, ");
		sb.append("    TRIM(HACHU_ED_DT)          AS HACHU_ED_DT, ");
		sb.append("    FLOOR_AREA_QT, ");
		sb.append("    URIBA_AREA_QT, ");
		sb.append("    BK_AREA_QT, ");
		sb.append("    TENANT_AREA_QT, ");
		sb.append("    REGISTER_COUNT_QT, ");
		sb.append("    PARKING_AREA_QT, ");
		sb.append("    PARKING_COUNT_QT, ");
		sb.append("    TRIM(TENPO_HYOJIJUN_NO)    AS TENPO_HYOJIJUN_NO, ");
		sb.append("    TRIM(BUTYURYUHI_CENTER)    AS BUTYURYUHI_CENTER, ");
		sb.append("    TRIM(SNDST_NEHUDA_DT)      AS SNDST_NEHUDA_DT, ");
		sb.append("    TRIM(SNDST_PRICE_DT)       AS SNDST_PRICE_DT, ");
		sb.append("    TRIM(SNDST_TAG_DT)         AS SNDST_TAG_DT, ");
		sb.append("    TRIM(SNDST_PLU_DT)         AS SNDST_PLU_DT, ");
		sb.append("    TRIM(SNDST_POP_DT)         AS SNDST_POP_DT, ");
		sb.append("    TRIM(SNDST_KEIRYOKI_DT)    AS SNDST_KEIRYOKI_DT, ");
		sb.append("    TRIM(SAI_SEND_PLU_DT)      AS SAI_SEND_PLU_DT, ");
		sb.append("    TRIM(HANKU1_KEIRYOKI_TYPE) AS HANKU1_KEIRYOKI_TYPE, ");
		sb.append("    TRIM(HANKU2_KEIRYOKI_TYPE) AS HANKU2_KEIRYOKI_TYPE, ");
		sb.append("    TRIM(HANKU3_KEIRYOKI_TYPE) AS HANKU3_KEIRYOKI_TYPE, ");
		sb.append("    TRIM(HANKU4_KEIRYOKI_TYPE) AS HANKU4_KEIRYOKI_TYPE, ");
		sb.append("    TRIM(POS_KB)               AS POS_KB, ");
		sb.append("    TRIM(BOTEN_CD)             AS BOTEN_CD, ");
		sb.append("    TRIM(GYOTAI_KB)            AS GYOTAI_KB, ");
		sb.append("    TRIM(ZENTEN_KB)            AS ZENTEN_KB ");
		sb.append("FROM ");
		sb.append("    TMP_R_TENPO ");
		sb.append("WHERE ");
		sb.append("    DELETE_FG = '" ).append( mst000801_DelFlagDictionary.INAI.getCode() ).append( "' " );
		sb.append("ORDER BY " );
		sb.append("    TENPO_CD " );

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

		// 店舗コード
		if( rs.getString("TENPO_CD") != null ){
			sb.append( rs.getString("TENPO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 法人コード
		if( rs.getString("HOJIN_CD") != null  ){
			sb.append( rs.getString("HOJIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗階層（地域）
		if( rs.getString("TENPOKAISO_TIIKI_CD") != null ){
			sb.append( rs.getString("TENPOKAISO_TIIKI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗階層（エリア）
		if( rs.getString("TENPOKAISO_AREA_CD") != null ){
			sb.append( rs.getString("TENPOKAISO_AREA_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗階層（ブロック）
		if( rs.getString("TENPOKAISO_BLOCK_CD") != null ){
			sb.append( rs.getString("TENPOKAISO_BLOCK_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 正式名称（漢字）
		if( rs.getString("KANJI_NA") != null ){
			sb.append( rs.getString("KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 正式名称（カナ）
		if( rs.getString("KANA_NA") != null ){
			sb.append( rs.getString("KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 略式名称（漢字）
		if( rs.getString("KANJI_RN") != null ){
			sb.append( rs.getString("KANJI_RN").trim() );
		}
		sb.append( SPLIT_CODE );

		// 略式名称（カナ）
		if( rs.getString("KANA_RN") != null ){
			sb.append( rs.getString("KANA_RN").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗タイプ
		if( rs.getString("TENPO_TYPE_KB") != null ){
			sb.append( rs.getString("TENPO_TYPE_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗区分
		if( rs.getString("TENPO_KB") != null ){
			sb.append( rs.getString("TENPO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 開店日
		if( rs.getString("KAITEN_DT") != null ){
			sb.append( rs.getString("KAITEN_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 閉店日
		if( rs.getString("HEITEN_DT") != null ){
			sb.append( rs.getString("HEITEN_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 財務終了日
		if( rs.getString("ZAIMU_END_DT") != null ){
			sb.append( rs.getString("ZAIMU_END_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 開店時間
		if( rs.getString("OPEN_TM") != null ){
			sb.append( rs.getString("OPEN_TM").trim() );
		}
		sb.append( SPLIT_CODE );

		// 閉店時間
		if( rs.getString("CLOSE_TM") != null ){
			sb.append( rs.getString("CLOSE_TM").trim() );
		}
		sb.append( SPLIT_CODE );

		// 住所（漢字）
		if( rs.getString("ADDRESS_KANJI_NA") != null ){
			sb.append( rs.getString("ADDRESS_KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 住所（カナ）
		if( rs.getString("ADDRESS_KANA_NA") != null ){
			sb.append( rs.getString("ADDRESS_KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 住所３
		if( rs.getString("ADDRESS_3_NA") != null ){
			sb.append( rs.getString("ADDRESS_3_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 郵便番号
		if( rs.getString("YUBIN_CD") != null ){
			sb.append( rs.getString("YUBIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 電話番号
		if( rs.getString("TEL_CD") != null ){
			sb.append( rs.getString("TEL_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 伝送用電話番号
		if( rs.getString("TRANS_TEL_CD") != null ){
			sb.append( rs.getString("TRANS_TEL_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// FAX番号
		if( rs.getString("FAX_CD") != null ){
			sb.append( rs.getString("FAX_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// Eメールアドレス
		if( rs.getString("EMAIL_ADDRESS") != null ){
			sb.append( rs.getString("EMAIL_ADDRESS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注可能開始日
		if( rs.getString("HACHU_ST_DT") != null ){
			sb.append( rs.getString("HACHU_ST_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注可能終了日
		if( rs.getString("HACHU_ED_DT") != null ){
			sb.append( rs.getString("HACHU_ED_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 建物延床面積
		if( rs.getString("FLOOR_AREA_QT") != null ){
			sb.append( rs.getString("FLOOR_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 売場面積
		if( rs.getString("URIBA_AREA_QT") != null ){
			sb.append( rs.getString("URIBA_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// BK面積
		if( rs.getString("BK_AREA_QT") != null ){
			sb.append( rs.getString("BK_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// テナント売場面積
		if( rs.getString("TENANT_AREA_QT") != null ){
			sb.append( rs.getString("TENANT_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// レジ台数
		if( rs.getString("REGISTER_COUNT_QT") != null ){
			sb.append( rs.getString("REGISTER_COUNT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 駐車場面積
		if( rs.getString("PARKING_AREA_QT") != null ){
			sb.append( rs.getString("PARKING_AREA_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 駐車台数
		if( rs.getString("PARKING_COUNT_QT") != null ){
			sb.append( rs.getString("PARKING_COUNT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗表示順
		if( rs.getString("TENPO_HYOJIJUN_NO") != null ){
			sb.append( rs.getString("TENPO_HYOJIJUN_NO").trim() );
		}
		sb.append( SPLIT_CODE );

		// 物流費計算センター
		if( rs.getString("BUTYURYUHI_CENTER") != null ){
			sb.append( rs.getString("BUTYURYUHI_CENTER").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：値札シール
		if( rs.getString("SNDST_NEHUDA_DT") != null ){
			sb.append( rs.getString("SNDST_NEHUDA_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：プライスカード
		if( rs.getString("SNDST_PRICE_DT") != null ){
			sb.append( rs.getString("SNDST_PRICE_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：タグ
		if( rs.getString("SNDST_TAG_DT") != null ){
			sb.append( rs.getString("SNDST_TAG_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：PLU
		if( rs.getString("SNDST_PLU_DT") != null ){
			sb.append( rs.getString("SNDST_PLU_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：POP
		if( rs.getString("SNDST_POP_DT") != null ){
			sb.append( rs.getString("SNDST_POP_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 送信開始日：計量器
		if( rs.getString("SNDST_KEIRYOKI_DT") != null ){
			sb.append( rs.getString("SNDST_KEIRYOKI_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 再送信日：PLU
		if( rs.getString("SAI_SEND_PLU_DT") != null ){
			sb.append( rs.getString("SAI_SEND_PLU_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器タイプ１
		if( rs.getString("HANKU1_KEIRYOKI_TYPE") != null ){
			sb.append( rs.getString("HANKU1_KEIRYOKI_TYPE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器タイプ２
		if( rs.getString("HANKU2_KEIRYOKI_TYPE") != null ){
			sb.append( rs.getString("HANKU1_KEIRYOKI_TYPE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器タイプ３
		if( rs.getString("HANKU3_KEIRYOKI_TYPE") != null ){
			sb.append( rs.getString("HANKU1_KEIRYOKI_TYPE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 計量器タイプ４
		if( rs.getString("HANKU4_KEIRYOKI_TYPE") != null ){
			sb.append( rs.getString("HANKU1_KEIRYOKI_TYPE").trim() );
		}
		sb.append( SPLIT_CODE );

		// 新旧POS区分
		if( rs.getString("POS_KB") != null ){
			sb.append( rs.getString("POS_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 母店コード
		if( rs.getString("BOTEN_CD") != null ){
			sb.append( rs.getString("BOTEN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 業態区分
		if( rs.getString("GYOTAI_KB") != null ){
			sb.append( rs.getString("GYOTAI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 全店区分
		if( rs.getString("ZENTEN_KB") != null ){
			sb.append( rs.getString("ZENTEN_KB").trim() );
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
