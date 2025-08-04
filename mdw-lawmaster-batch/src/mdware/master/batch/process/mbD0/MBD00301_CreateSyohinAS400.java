package mdware.master.batch.process.mbD0;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;

import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst001201_EosKbDictionary;
import mdware.master.common.dictionary.mst005801_BinKbDictionary;
import mdware.master.common.dictionary.mst010101_SyohinKbDictionary;
import mdware.master.common.dictionary.mst011201_CgcHenpinKbDictionary;

/**
 * <p>タイトル:AS400商品マスタ連携ファイル作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author S.Matsushita
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @version 3.00 (2013/07/25) M.Ayukawa    [MSTC00028] 商品変更リスト作成対応
 * @version 3.00 (2013/09/09) M.Ayukawa    [MSTC00030 MMST-0003] アイス、冷凍食品項目追加
 */
public class MBD00301_CreateSyohinAS400 {

	// 変換文字（ワイルドカードの文字）
	private static final String WILD_CARD_CODE = "*";
	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE	= ",";
	// 改行文字
	private static final String RETURN_CODE	= System.getProperty("line.separator");
	// データ区分（新規）
	private static final String DATA_KBN_NEW = mst000101_ConstDictionary.AS400_DATA_KB_NEW;
	// データ区分（修正）
	private static final String DATA_KBN_ADD = mst000101_ConstDictionary.AS400_DATA_KB_ADD;
	// データ区分（変更リスト）
	private static final String DATA_KBN_UPDLIST = mst000101_ConstDictionary.AS400_DATA_KB_UPDLIST;
	
// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（S)
	// 冷食アイス判断用
	private static final String REISYOKU_DPT_CD 	= "24";
	private static final String REISYOKU_NA 		= "冷食注意";
	private static final String ICE_DPT_CD 			= "33";
	private static final String ICE_LINE_CD 		= "015";
	private static final String ICE_NA 				= "アイス注意";
// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（E)

	

	private DataBase db	= null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
	// CSVファイルパス
	private String csvFilePath = null;
	// システム日付
	private String systemDt = null;
	// 商品マスタ（新規）データファイル名
	private String fileSyohinNew = null;
	// 商品マスタ（新規）完了ファイル名
	private String fileSyohinNewFinish = null;
	// 商品マスタ（修正）データファイル名
	private String fileSyohinAdd = null;
	// 商品マスタ（修正）完了ファイル名
	private String fileSyohinAddFinish = null;
	// 商品変更リストデータファイル名
	private String fileSyohinUpdList = null;
	// 商品変更リスト完了ファイル名
	private String fileSyohinUpdListFinish = null;

	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBD00301_CreateSyohinAS400(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase( mst000101_ConstDictionary.CONNECTION_STR );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBD00301_CreateSyohinAS400() {
		this(new DataBase( mst000101_ConstDictionary.CONNECTION_STR ));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try{

			//バッチ処理件数をカウント（ログ出力用）
			long iRecNew    = 0;	// 新規データ出力件数
			long iRecAdd    = 0;	// 修正データ出力件数
			long iRecUpdLst = 0;	// 商品変更リストデータ出力件数

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			// ※ＣＳＶファイル出力処理のみなのでトランザクションは不要
//			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);
			writeLog(Level.INFO_INT, "出力先ディレクトリ：" + csvFilePath);

			// 商品マスタ（新規）データファイル作成
			writeLog(Level.INFO_INT, "商品マスタ（新規）データファイル（" + fileSyohinNew + "）作成処理を開始します。");
			iRecNew = createCSVFile(DATA_KBN_NEW);
			writeLog(Level.INFO_INT, "商品マスタ（新規）データファイルを" + iRecNew + "件作成しました。");
			// 商品マスタ（修正）データファイル作成
			writeLog(Level.INFO_INT, "商品マスタ（修正）データファイル（" + fileSyohinAdd + "）作成処理を開始します。");
			iRecAdd = createCSVFile(DATA_KBN_ADD);
			writeLog(Level.INFO_INT, "商品マスタ（修正）データファイルを" + iRecAdd + "件作成しました。");			
			// 商品変更リストデータファイル作成
			writeLog(Level.INFO_INT, "商品変更リストデータファイル（" + fileSyohinUpdList + "）作成処理を開始します。");
			iRecUpdLst = createCSVFile(DATA_KBN_UPDLIST);
			writeLog(Level.INFO_INT, "商品変更リストデータファイルを" + iRecUpdLst + "件作成しました。");
			
			// 商品マスタ（新規）完了ファイル作成
			writeLog(Level.INFO_INT, "商品マスタ（新規）完了ファイル（" + fileSyohinNewFinish + "）作成処理を開始します。");
			iRecNew = createCHKFile(DATA_KBN_NEW, iRecNew);
			writeLog(Level.INFO_INT, "商品マスタ（新規）完了ファイルを作成しました。");
			// 商品マスタ（修正）完了ファイル作成
			writeLog(Level.INFO_INT, "商品マスタ（修正）完了ファイル（" + fileSyohinAddFinish + "）作成処理を開始します。");
			iRecAdd = createCHKFile(DATA_KBN_ADD, iRecAdd);
			writeLog(Level.INFO_INT, "商品マスタ（修正）完了ファイルを作成しました。");
			// 商品変更リスト完了ファイル作成
			writeLog(Level.INFO_INT, "商品変更リスト完了ファイル（" + fileSyohinUpdListFinish + "）作成処理を開始します。");
			iRecAdd = createCHKFile(DATA_KBN_UPDLIST, iRecUpdLst);
			writeLog(Level.INFO_INT, "商品変更リスト完了ファイルを作成しました。");

//			db.commit();

			writeLog(Level.INFO_INT, "処理を終了します。");

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
//			db.rollback();
//			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
//			db.rollback();
//			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

		//SQL終了処理
		}finally{
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

		// バッチ日付取得
		batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
		if(batchDate == null || batchDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
			throw new Exception();
		}

		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_SOURCE_DIR_NAME);
		if(csvFilePath == null || csvFilePath.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// 商品マスタ（新規）データファイル名
		fileSyohinNew = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW);
		if(fileSyohinNew == null || fileSyohinNew.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタ（新規）データファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品マスタ（新規）完了ファイル名
		fileSyohinNewFinish = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_NEW_FINISH);
		if(fileSyohinNewFinish == null || fileSyohinNewFinish.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタ（新規）完了ファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品マスタ（修正）データファイル名
		fileSyohinAdd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD);
		if(fileSyohinAdd == null || fileSyohinAdd.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタ（修正）データファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品マスタ（修正）完了ファイル名
		fileSyohinAddFinish = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_ADD_FINISH);
		if(fileSyohinAddFinish == null || fileSyohinAddFinish.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタ（修正）完了ファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品変更リストデータファイル名
		fileSyohinUpdList = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST);
		if(fileSyohinUpdList == null || fileSyohinUpdList.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品変更リストデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 商品変更リスト完了ファイル名
		fileSyohinUpdListFinish = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.AS400_FTP_FILE_SYOHIN_UPDLIST_FINISH);
		if(fileSyohinUpdListFinish == null || fileSyohinUpdListFinish.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品変更リスト完了ファイル名が取得できませんでした");
			throw new Exception();
		}

		// システム日付取得（CSVファイル名にセットする値）
		systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		if(systemDt == null || systemDt.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システム日時の取得に失敗しました");
			throw new Exception();
		}

		// ファイル名の変換（ファイル名にある「*」を「YYYYMMDDHH24MISS」に変換する）
		fileSyohinNew 			= fileSyohinNew.replace(WILD_CARD_CODE, systemDt);
		fileSyohinNewFinish 	= fileSyohinNewFinish.replace(WILD_CARD_CODE, systemDt);
		fileSyohinAdd 			= fileSyohinAdd.replace(WILD_CARD_CODE, systemDt);
		fileSyohinAddFinish 	= fileSyohinAddFinish.replace(WILD_CARD_CODE, systemDt);
		fileSyohinUpdList 		= fileSyohinUpdList.replace(WILD_CARD_CODE, systemDt);
		fileSyohinUpdListFinish = fileSyohinUpdListFinish.replace(WILD_CARD_CODE, systemDt);
	}

	/**
	 * CSVファイルを作成します。
	 * @param dataKb 	1:新規、2:修正、3:変更リスト
	 * @return 出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private long createCSVFile(String dataKb) throws IOException, SQLException, Exception {

		ResultSet		rs			= null;
		String			fileName	= null;
		File			file 		= null;
		FileWriter		fw 			= null;
		BufferedWriter	bw 			= null;
		StringBuffer	sb			= new StringBuffer();
		long			lngOPCnt	= 0;

		try{
			// CSVファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);
			if (DATA_KBN_NEW.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinNew;
			}else if (DATA_KBN_ADD.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinAdd;
			}else if (DATA_KBN_UPDLIST.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinUpdList;
			}

			if( file.exists() == false ){
				// ディレクトリが見つからなければ
	    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// ファイルオープン
			fw = new FileWriter( fileName, true );
			bw = new BufferedWriter( fw );

			// ヘッダデータ作成
			sb.append( this.createCsvHeadData(dataKb) );

			// ヘッダデータ出力
			bw.write(sb.toString());
			sb.setLength(0);

			// データ取得
			rs = db.executeQuery(getIfSyohinSQL(dataKb));

			while( rs.next() ){

				// 行データ作成
				sb.append( this.createCsvRowData(rs,dataKb) );

				// 行データ出力
				bw.write(sb.toString());
				sb.setLength(0);

				lngOPCnt++;
			}

			// 最終ファイル
			if( !rs.next() ){
				// ファイルクローズ
				if( bw != null ){
					bw.close();
				}
				if( fw != null ){
					fw.close();
				}
			}

			rs.close();

		} catch( Exception e ) {
			// ファイルクローズ
			if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}
			throw e;
		}

		return lngOPCnt;
	}

	/**
	 * CSVファイルへ出力するヘッダデータを作成する
	 * @param 		dataKb 			1:新規、2:修正、3:変更リスト
	 * @return		StringBuffer	１行分の文字列
	 * @throws		Exception
	 */
	private StringBuffer createCsvHeadData(String dataKb) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		if (dataKb.equals(DATA_KBN_NEW) || dataKb.equals(DATA_KBN_ADD)) {
			
			// 新規、修正			
			sb.append("SQNO");
			sb.append(SPLIT_CODE).append("冷食アイス"); // 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加
			sb.append(SPLIT_CODE).append("商品ｺｰﾄﾞ");
			sb.append(SPLIT_CODE).append("漢字名称");
			sb.append(SPLIT_CODE).append("規格");
			sb.append(SPLIT_CODE).append("EOSｶﾅ名称");
			sb.append(SPLIT_CODE).append("POS名称");
			sb.append(SPLIT_CODE).append("DPT");
			sb.append(SPLIT_CODE).append("ﾗｲﾝ");
			sb.append(SPLIT_CODE).append("ｸﾗｽ");
			sb.append(SPLIT_CODE).append("発注先");
			sb.append(SPLIT_CODE).append("入数");
			sb.append(SPLIT_CODE).append("原価");
			sb.append(SPLIT_CODE).append("売価");
			sb.append(SPLIT_CODE).append("OB区分");
			sb.append(SPLIT_CODE).append("商品区分");
			sb.append(SPLIT_CODE).append("PC区分");
			sb.append(SPLIT_CODE).append("送信区分");
			sb.append(SPLIT_CODE).append("1便");
			sb.append(SPLIT_CODE).append("2便");
			sb.append(SPLIT_CODE).append("適用日");
			sb.append(SPLIT_CODE).append("特性");
			sb.append(SPLIT_CODE).append("取扱");
			sb.append(SPLIT_CODE).append("停止");
			sb.append(SPLIT_CODE).append("ENDﾏｰｸ");

		}else if (dataKb.equals(DATA_KBN_UPDLIST)) {

			// 変更リスト
			sb.append("SQNO");
			sb.append(SPLIT_CODE).append("冷食アイス"); // 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加
			sb.append(SPLIT_CODE).append("商品ｺｰﾄﾞ");
			sb.append(SPLIT_CODE).append("有効日");	
			sb.append(SPLIT_CODE).append("漢字名称");
			sb.append(SPLIT_CODE).append("規格");
			sb.append(SPLIT_CODE).append("EOSｶﾅ名称");
			sb.append(SPLIT_CODE).append("POS名称");
			sb.append(SPLIT_CODE).append("DPT");
			sb.append(SPLIT_CODE).append("ﾗｲﾝ");
			sb.append(SPLIT_CODE).append("ｸﾗｽ");
			sb.append(SPLIT_CODE).append("発注先");
			sb.append(SPLIT_CODE).append("入数");
			sb.append(SPLIT_CODE).append("原価");
			sb.append(SPLIT_CODE).append("売価");
			sb.append(SPLIT_CODE).append("OB区分");
			sb.append(SPLIT_CODE).append("商品区分");
			sb.append(SPLIT_CODE).append("PC区分");
			sb.append(SPLIT_CODE).append("送信区分");
			sb.append(SPLIT_CODE).append("1便");
			sb.append(SPLIT_CODE).append("2便");
			sb.append(SPLIT_CODE).append("適用日");
			sb.append(SPLIT_CODE).append("特性");
			sb.append(SPLIT_CODE).append("取扱");
			sb.append(SPLIT_CODE).append("停止");
			sb.append(SPLIT_CODE).append("ENDﾏｰｸ");
			sb.append(SPLIT_CODE).append("30文字品名");
			sb.append(SPLIT_CODE).append("ﾒｰｶｰｺｰﾄﾞ");
			sb.append(SPLIT_CODE).append("更新ID");
			sb.append(SPLIT_CODE).append("更新時刻");

		}
		
		sb.append(RETURN_CODE);

		return sb;
	}

	/**
	 * CSVファイルへ出力する明細データを作成する
	 * @param		ResultSet       取得データ
	 * @param 		dataKb 			1:新規、2:修正、3:変更リスト
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createCsvRowData(ResultSet rs,String dataKb) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		if (dataKb.equals(DATA_KBN_NEW) || dataKb.equals(DATA_KBN_ADD)) {
		
			// 新規、修正
			sb.append(createCsvString(rs.getString("SEQNO")));
			sb.append(createCsvString(rs.getString("REISYOKU_NA"))); // 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加
			sb.append(createCsvString(rs.getString("SYOHIN_CD")));
			sb.append(createCsvString(rs.getString("SYOHIN_NA")));
			sb.append(createCsvString(rs.getString("KIKAKU_NA")));
			sb.append(createCsvString(rs.getString("KANA_NA")));
			sb.append(createCsvString(rs.getString("POS_NA")));
			sb.append(createCsvString(rs.getString("DPT_CD")));
			sb.append(createCsvString(rs.getString("LINE_CD")));
			sb.append(createCsvString(rs.getString("CLASS_CD")));
			sb.append(createCsvString(rs.getString("HACHUSAKI_CD")));
			sb.append(createCsvString(rs.getString("IRISU_QT")));
			sb.append(createCsvString(rs.getString("GENTANKA_VL")));
			// #6395 Mod 2021.11.29 KHOI.ND (S)
			// sb.append(createCsvString(rs.getString("BAITANKA_VL")));
			sb.append(createCsvString(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellString(rs.getString("BAITANKA_VL")))));
			// #6395 Mod 2021.11.29 KHOI.ND (E)
			sb.append(createCsvString(rs.getString("OB_KB")));
			sb.append(createCsvString(rs.getString("SYOHIN_KB")));
			sb.append(createCsvString(rs.getString("PC_KB")));
			sb.append(createCsvString(rs.getString("SOSHIN_KB")));
			sb.append(createCsvString(rs.getString("BIN_1_KB")));
			sb.append(createCsvString(rs.getString("BIN_2_KB")));
			sb.append(createCsvString(rs.getString("TEKIYO_DT")));
			sb.append(createCsvString(rs.getString("TOKUSEI_KB")));
			sb.append(createCsvString(rs.getString("TORIATUKAI_KB")));
			sb.append(createCsvString(rs.getString("TEISHI_KB")));
			sb.append(createCsvEndString(rs.getString("END_MARK_TX")));
		
		}else if (dataKb.equals(DATA_KBN_UPDLIST)) {
			
			// 変更リスト
			sb.append(createCsvString(rs.getString("SEQNO")));
			sb.append(createCsvString(rs.getString("REISYOKU_NA"))); // 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加
			sb.append(createCsvString(rs.getString("SYOHIN_CD")));
			sb.append(createCsvString(rs.getString("YUKO_DT"))); 		// 有効日
			sb.append(createCsvString(rs.getString("SYOHIN_NA")));
			sb.append(createCsvString(rs.getString("KIKAKU_NA")));
			sb.append(createCsvString(rs.getString("KANA_NA")));
			sb.append(createCsvString(rs.getString("POS_NA")));
			sb.append(createCsvString(rs.getString("DPT_CD")));
			sb.append(createCsvString(rs.getString("LINE_CD")));
			sb.append(createCsvString(rs.getString("CLASS_CD")));
			sb.append(createCsvString(rs.getString("HACHUSAKI_CD")));
			sb.append(createCsvString(rs.getString("IRISU_QT")));
			sb.append(createCsvString(rs.getString("GENTANKA_VL")));
			// #6395 Mod 2021.11.29 KHOI.ND (S)
			// sb.append(createCsvString(rs.getString("BAITANKA_VL")));
			sb.append(createCsvString(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellString(rs.getString("BAITANKA_VL")))));
			// #6395 Mod 2021.11.29 KHOI.ND (E)
			sb.append(createCsvString(rs.getString("OB_KB")));
			sb.append(createCsvString(rs.getString("SYOHIN_KB")));
			sb.append(createCsvString(rs.getString("PC_KB")));
			sb.append(createCsvString(rs.getString("SOSHIN_KB")));
			sb.append(createCsvString(rs.getString("BIN_1_KB")));
			sb.append(createCsvString(rs.getString("BIN_2_KB")));
			sb.append(createCsvString(rs.getString("TEKIYO_DT")));
			sb.append(createCsvString(rs.getString("TOKUSEI_KB")));
			sb.append(createCsvString(rs.getString("TORIATUKAI_KB")));
			sb.append(createCsvString(rs.getString("TEISHI_KB")));
			sb.append(createCsvString(rs.getString("END_MARK_TX")));
			sb.append(createCsvString(rs.getString("HINMEI_KANJI_NA"))); // 30文字品名
			sb.append(createCsvString(rs.getString("MAKER_CD"))); 		// ﾒｰｶｰｺｰﾄﾞ
			sb.append(createCsvString(rs.getString("UPDATE_USER_ID"))); // 更新ID
			sb.append(createCsvEndString(rs.getString("UPDATE_TS"))); 	// 更新時刻
			
		}

		return sb;
	}

	/**
	 * 完了（CHK）ファイルを作成します。
	 * @param dataKb
	 * @param csvRowCount
	 * @return 出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private long createCHKFile(String dataKb, long csvRowCount) throws IOException, Exception {

		String			fileName	= null;
		File			file 		= null;
		FileWriter		fw 			= null;
		BufferedWriter	bw 			= null;
		StringBuffer	sb			= new StringBuffer();
		long			lngOPCnt	= 0;

		try{
			// CHKファイル格納パス、ファイル名
			file 	 = new File(csvFilePath);
			
			if (DATA_KBN_NEW.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinNewFinish;
			}else if (DATA_KBN_ADD.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinAddFinish;
			}else if (DATA_KBN_UPDLIST.equals(dataKb)) {
				fileName = new File(csvFilePath) + "/" + fileSyohinUpdListFinish;
			}

			if( file.exists() == false ){
				// ディレクトリが見つからなければ
	    		this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// ファイルオープン
			fw = new FileWriter( fileName, true );
			bw = new BufferedWriter( fw );

			// 行データ作成
			sb.append( batchDate );
			sb.append( SPLIT_CODE );
			sb.append( csvRowCount );

			// 行データ出力
			bw.write(sb.toString());
			sb.setLength(0);
			lngOPCnt++;

			// ファイルクローズ
			if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}

		} catch( Exception e ) {
			// ファイルクローズ
			if( bw != null ){
				bw.close();
			}
			if( fw != null ){
				fw.close();
			}
			throw e;
		}

		return lngOPCnt;
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_AS400商品マスタ取得SQLを取得します。
	 * IF_AS400商品マスタにデータを格納時に編集している為、ここでは空白を取り除く等の単純処理のみとする。
	 * @param dataKb 1:新規、2:修正、3:変更リスト
	 * @return SQL
	 */
	private String getIfSyohinSQL(String dataKb) {

		StringBuffer sb  = new StringBuffer("");
		
		if (dataKb.equals(DATA_KBN_NEW) || dataKb.equals(DATA_KBN_ADD)) {

			// 新規、修正
			sb.append("SELECT ");
			sb.append("	  IAS.SEQNO ");
			// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（S)
			sb.append("	  ,CASE  ");
			sb.append("		WHEN IAS.DPT_CD  = '" + REISYOKU_DPT_CD + "' ");
			sb.append("	  		THEN '" + REISYOKU_NA + "' ");
			sb.append("		WHEN IAS.DPT_CD  = '" + ICE_DPT_CD + "'   AND ");
			sb.append("			 IAS.LINE_CD = '" + ICE_LINE_CD + "'  ");
			sb.append("			THEN '" + ICE_NA + "' ");
			sb.append("	  END                       AS REISYOKU_NA ");
			// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（E)
			sb.append("	, TRIM(IAS.SYOHIN_CD)		AS SYOHIN_CD ");
			sb.append("	, TRIM(IAS.SYOHIN_NA)		AS SYOHIN_NA ");
			sb.append("	, TRIM(IAS.KIKAKU_NA)		AS KIKAKU_NA ");
			sb.append("	, TRIM(IAS.KANA_NA)			AS KANA_NA ");
			sb.append("	, TRIM(IAS.POS_NA)			AS POS_NA ");
			sb.append("	, TRIM(IAS.DPT_CD)			AS DPT_CD ");
			sb.append("	, TRIM(IAS.LINE_CD)			AS LINE_CD ");
			sb.append("	, TRIM(IAS.CLASS_CD)		AS CLASS_CD ");
			sb.append("	, TRIM(IAS.HACHUSAKI_CD)	AS HACHUSAKI_CD ");
			sb.append("	, IAS.IRISU_QT ");
			sb.append("	, RTRIM(TO_CHAR(IAS.GENTANKA_VL,'FM9999990.99'),'.') AS GENTANKA_VL ");
			sb.append("	, IAS.BAITANKA_VL ");
			sb.append("	, TRIM(IAS.OB_KB) 			AS OB_KB ");
			sb.append("	, TRIM(IAS.SYOHIN_KB) 		AS SYOHIN_KB ");
			sb.append("	, TRIM(IAS.PC_KB) 			AS PC_KB ");
			sb.append("	, TRIM(IAS.SOSHIN_KB) 		AS SOSHIN_KB ");
			sb.append("	, TRIM(IAS.BIN_1_KB) 		AS BIN_1_KB ");
			sb.append("	, TRIM(IAS.BIN_2_KB) 		AS BIN_2_KB ");
			sb.append("	, TRIM(IAS.TEKIYO_DT) 		AS TEKIYO_DT ");
			sb.append("	, TRIM(IAS.TOKUSEI_KB) 		AS TOKUSEI_KB ");
			sb.append("	, TRIM(IAS.TORIATUKAI_KB) 	AS TORIATUKAI_KB ");
			sb.append("	, TRIM(IAS.TEISHI_KB) 		AS TEISHI_KB ");
			sb.append("	, TRIM(IAS.END_MARK_TX) 	AS END_MARK_TX ");
			sb.append("FROM ");
			sb.append("	IF_AS400_SYOHIN IAS ");
			sb.append("WHERE ");
			sb.append("	DATA_KB = '" + dataKb + "' ");
			sb.append("ORDER BY ");
			sb.append("	SEQNO ");
		
		}else if(dataKb.equals(DATA_KBN_UPDLIST)) {
			
			// 変更リスト
			sb.append("SELECT																					");
			sb.append("	 IAS.SEQNO                                                                              ");
			// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（S)			
			sb.append("	  ,CASE  ");
			sb.append("		WHEN TRIM(WMS.BUNRUI1_CD) = '" + REISYOKU_DPT_CD + "' ");
			sb.append("	  		THEN '" + REISYOKU_NA + "' ");
			sb.append("		WHEN TRIM(WMS.BUNRUI1_CD) = '" + ICE_DPT_CD + "'   AND ");
			sb.append("			 TRIM(WMS.BUNRUI2_CD) = '" + ICE_LINE_CD + "'  ");
			sb.append("			THEN '" + ICE_NA + "' ");
			sb.append("	  END                      AS REISYOKU_NA ");
			// 2013.09.09 [MSTC00030 MMST-0003] アイス、冷凍食品項目追加（E)			
			sb.append("	,WMS.YUKO_DT                                                                            ");
			sb.append("	,WMS.SYOHIN_CD                                                                          ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.REC_HINMEI_KANJI_NA = ZMS.REC_HINMEI_KANJI_NA THEN NULL                   ");
			sb.append("		ELSE  WMS.REC_HINMEI_KANJI_NA                                                       ");
			sb.append("	 END                            								SYOHIN_NA               ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.KIKAKU_KANJI_NA = ZMS.KIKAKU_KANJI_NA THEN NULL                           ");
			sb.append("		ELSE  WMS.KIKAKU_KANJI_NA                                                           ");
			sb.append("	 END                            								KIKAKU_NA               ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.HINMEI_KANA_NA = ZMS.HINMEI_KANA_NA THEN NULL                             ");
			sb.append("		ELSE  WMS.HINMEI_KANA_NA                                                            ");
			sb.append("	 END                            								KANA_NA                 ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.REC_HINMEI_KANA_NA = ZMS.REC_HINMEI_KANA_NA THEN NULL                     ");
			sb.append("		ELSE  WMS.REC_HINMEI_KANA_NA                                                        ");
			sb.append("	 END                            								POS_NA                  ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BUNRUI1_CD = ZMS.BUNRUI1_CD THEN NULL                                     ");
			sb.append("		ELSE  WMS.BUNRUI1_CD                                                                ");
			sb.append("	 END                            								DPT_CD                  ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BUNRUI2_CD = ZMS.BUNRUI2_CD THEN NULL                                     ");
			sb.append("		ELSE  WMS.BUNRUI2_CD                                                                ");
			sb.append("	 END                            								LINE_CD                 ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BUNRUI5_CD = ZMS.BUNRUI5_CD THEN NULL                                     ");
			sb.append("		ELSE  WMS.BUNRUI5_CD                                                                ");
			sb.append("	 END                            								CLASS_CD                ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.SIIRESAKI_CD = ZMS.SIIRESAKI_CD THEN NULL                                 ");
			sb.append("		ELSE  WMS.SIIRESAKI_CD                                                              ");
			sb.append("	 END                            								HACHUSAKI_CD            ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.HACHUTANI_IRISU_QT = ZMS.HACHUTANI_IRISU_QT THEN NULL                     ");
			sb.append("		ELSE  WMS.HACHUTANI_IRISU_QT                                                        ");
			sb.append("	 END                            								IRISU_QT                ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.GENTANKA_VL = ZMS.GENTANKA_VL THEN NULL                                   ");
			sb.append("		ELSE  RTRIM(TO_CHAR(WMS.GENTANKA_VL,'FM9999990.99'),'.')                            ");
			sb.append("	 END                            								GENTANKA_VL             ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BAITANKA_VL = ZMS.BAITANKA_VL THEN NULL                                   ");
			sb.append("		ELSE  WMS.BAITANKA_VL                                                               ");
			sb.append("	 END                            								BAITANKA_VL             ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.CGC_HENPIN_KB = ZMS.CGC_HENPIN_KB THEN NULL                               ");
			sb.append("		ELSE  WMS.CGC_HENPIN_KB                                                             ");
			sb.append("	 END                            								OB_KB                   ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.SYOHIN_KB = ZMS.SYOHIN_KB THEN NULL                                       ");
			sb.append("		ELSE  WMS.SYOHIN_KB                                                                 ");
			sb.append("	 END                            								SYOHIN_KB               ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.PC_KB = ZMS.PC_KB THEN NULL                                               ");
			sb.append("		ELSE  WMS.PC_KB                                                                     ");
			sb.append("	 END                            								PC_KB                   ");
			sb.append("	,''                             								SOSHIN_KB               ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BIN_1_KB = ZMS.BIN_1_KB THEN NULL                                         ");
			sb.append("		ELSE  WMS.BIN_1_KB                                                                  ");
			sb.append("	 END                            								BIN_1_KB                ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.BIN_2_KB = ZMS.BIN_2_KB THEN NULL                                         ");
			sb.append("		ELSE  WMS.BIN_2_KB                                                                  ");
			sb.append("	 END                            								BIN_2_KB                ");
			sb.append("	,NULL                           								TEKIYO_DT               ");
			sb.append("	,NULL                           								TOKUSEI_KB              ");
			sb.append("	,NULL                           								TORIATUKAI_KB           ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  ZMS.EOS_KB =  '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "' AND ");
			sb.append("		      WMS.EOS_KB <> '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "'  	");
			sb.append("		  THEN '" + mst000101_ConstDictionary.AS400_TEISI_KAIJO_KB + "'                     ");
			sb.append("		WHEN  ZMS.EOS_KB <> '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "' AND ");
			sb.append("		      WMS.EOS_KB =  '" + mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode() + "'     ");
			sb.append("		  THEN '" + mst000101_ConstDictionary.AS400_TEISI_KB + "'                           ");
			sb.append("	 END                            								TEISHI_KB               ");
			sb.append("	,'*'                            								END_MARK_TX             ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.HINMEI_KANJI_NA = ZMS.HINMEI_KANJI_NA THEN NULL                           ");
			sb.append("		ELSE  WMS.HINMEI_KANJI_NA                                                           ");
			sb.append("	 END                            								HINMEI_KANJI_NA         ");
			sb.append("	,CASE                                                                                   ");
			sb.append("		WHEN  WMS.MAKER_CD = ZMS.MAKER_CD THEN NULL                                         ");
			sb.append("		ELSE  WMS.MAKER_CD                                                                  ");
			sb.append("	 END                            								MAKER_CD                ");
			sb.append("	,SUBSTR(WMS.UPDATE_USER_ID,0,8) 								UPDATE_USER_ID          ");
			sb.append("	,WMS.UPDATE_TS                  								UPDATE_TS               ");
			sb.append("FROM                                                                                     ");
			sb.append("	IF_AS400_SYOHIN IAS                                                                     ");
			sb.append("	INNER JOIN                                                                              ");
			sb.append("		(                                                                                   ");
			sb.append("		SELECT                                                                              ");
			sb.append("			 SUBSTR(SYOHIN_CD,1,13)                					SYOHIN_CD               ");
			sb.append("			,YUKO_DT                                                                        ");
			sb.append("			,TRIM(RTRIM(SUBSTR(REC_HINMEI_KANJI_NA,1,15),'　'))     REC_HINMEI_KANJI_NA     ");
			sb.append("			,NVL(TRIM(RTRIM(SUBSTR(KIKAKU_KANJI_NA,1,8),'　')),'" + mst000101_ConstDictionary.AS400_SPACE + "')   KIKAKU_KANJI_NA    ");
			sb.append("			,TRIM(SUBSTR(HINMEI_KANA_NA,1,25))           			HINMEI_KANA_NA          ");
			sb.append("			,NVL(TRIM(SUBSTR(REC_HINMEI_KANA_NA,1,14)),'" + mst000101_ConstDictionary.AS400_SPACE + "')           REC_HINMEI_KANA_NA ");
			sb.append("			,SUBSTR(BUNRUI1_CD,1,3)                					BUNRUI1_CD              ");
			sb.append("			,SUBSTR(BUNRUI2_CD,3,3)                					BUNRUI2_CD              ");
			sb.append("			,SUBSTR(BUNRUI5_CD,3,3)                					BUNRUI5_CD              ");
			sb.append("			,SUBSTR(SIIRESAKI_CD,1,6)              					SIIRESAKI_CD            ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN HACHUTANI_IRISU_QT > " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + " THEN " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + "  ");
			sb.append("             ELSE HACHUTANI_IRISU_QT                                                     ");
			sb.append("          END HACHUTANI_IRISU_QT                                                         ");
			sb.append("			,GENTANKA_VL                                                                    ");
			sb.append("			,BAITANKA_VL                                                                    ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN CGC_HENPIN_KB = '" + mst011201_CgcHenpinKbDictionary.REITO_SYOKUHIN_IGAI.getCode() + "' ");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_OB_KB + "' 					    ");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("          END CGC_HENPIN_KB                                                              ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN SYOHIN_KB IN ('" + mst010101_SyohinKbDictionary.SIIREHANBAI.getCode() + "','" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "') ");
			sb.append("					THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_SYOHIN + "' 		");
			sb.append("				WHEN SYOHIN_KB = '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'     ");
			sb.append("					THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_GENRYO + "' 		");
			sb.append("          END SYOHIN_KB                                                                  ");
			sb.append("			,PC_KB                                                                          ");
			sb.append("			,CASE 																		    ");
			sb.append("				WHEN BIN_1_KB = '" + mst005801_BinKbDictionary.BIN1.getCode() + "' 			");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' 						");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("			 END BIN_1_KB 																    ");
			sb.append("			,CASE 																		    ");
			sb.append("				WHEN BIN_2_KB = '" + mst005801_BinKbDictionary.BIN2.getCode() + "' 			");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' 						");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("			 END BIN_2_KB 																    ");
			sb.append("			,EOS_KB                                                                         ");
			sb.append("			,TRIM(RTRIM(HINMEI_KANJI_NA,'　'))     					HINMEI_KANJI_NA         ");
			sb.append("			,MAKER_CD                                                                       ");
			sb.append("			,UPDATE_USER_ID                                                                 ");
			sb.append("			,UPDATE_TS                                                                      ");
			sb.append("		FROM                                                                                ");
			sb.append("			WK_MBD0_SYOHIN                                                                  ");
			sb.append("		) WMS                                                                               ");
			sb.append("	ON                                                                                      ");
			sb.append("		IAS.SYOHIN_CD = WMS.SYOHIN_CD                                                       ");
			sb.append("	INNER JOIN                                                                              ");
			sb.append("		(                                                                                   ");
			sb.append("		SELECT                                                                              ");
			sb.append("			 SUBSTR(SYOHIN_CD,1,13)                					SYOHIN_CD               ");
			sb.append("			,YUKO_DT                                                                        ");
			sb.append("			,TRIM(RTRIM(SUBSTR(REC_HINMEI_KANJI_NA,1,15),'　'))     REC_HINMEI_KANJI_NA     ");
			sb.append("			,NVL(TRIM(RTRIM(SUBSTR(KIKAKU_KANJI_NA,1,8),'　')),'" + mst000101_ConstDictionary.AS400_SPACE + "')   KIKAKU_KANJI_NA    ");
			sb.append("			,TRIM(SUBSTR(HINMEI_KANA_NA,1,25))           			HINMEI_KANA_NA          ");
			sb.append("			,NVL(TRIM(SUBSTR(REC_HINMEI_KANA_NA,1,14)),'" + mst000101_ConstDictionary.AS400_SPACE + "')           REC_HINMEI_KANA_NA ");
			sb.append("			,SUBSTR(BUNRUI1_CD,1,3)                					BUNRUI1_CD              ");
			sb.append("			,SUBSTR(BUNRUI2_CD,3,3)                					BUNRUI2_CD              ");
			sb.append("			,SUBSTR(BUNRUI5_CD,3,3)                					BUNRUI5_CD              ");
			sb.append("			,SUBSTR(SIIRESAKI_CD,1,6)              					SIIRESAKI_CD            ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN HACHUTANI_IRISU_QT > " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + " THEN " + mst000101_ConstDictionary.AS400_MAX_IRISU_QT + "  ");
			sb.append("             ELSE HACHUTANI_IRISU_QT                                                     ");
			sb.append("          END HACHUTANI_IRISU_QT                                                         ");
			sb.append("			,GENTANKA_VL                                                                    ");
			sb.append("			,BAITANKA_VL                                                                    ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN CGC_HENPIN_KB = '" + mst011201_CgcHenpinKbDictionary.REITO_SYOKUHIN_IGAI.getCode() + "' ");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_OB_KB + "' 					    ");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("          END CGC_HENPIN_KB                                                              ");
			sb.append("			,CASE                                                                           ");
			sb.append("				WHEN SYOHIN_KB IN ('" + mst010101_SyohinKbDictionary.SIIREHANBAI.getCode() + "','" + mst010101_SyohinKbDictionary.HANBAI.getCode() + "') ");
			sb.append("					THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_SYOHIN + "' 		");
			sb.append("				WHEN SYOHIN_KB = '" + mst010101_SyohinKbDictionary.SIIRE.getCode() + "'     ");
			sb.append("					THEN '" + mst000101_ConstDictionary.AS400_SYOHIN_KB_GENRYO + "' 		");
			sb.append("          END SYOHIN_KB                                                                  ");
			sb.append("			,PC_KB                                                                          ");
			sb.append("			,CASE 																		    ");
			sb.append("				WHEN BIN_1_KB = '" + mst005801_BinKbDictionary.BIN1.getCode() + "' 			");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' 						");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("			 END BIN_1_KB 																    ");
			sb.append("			,CASE 																		    ");
			sb.append("				WHEN BIN_2_KB = '" + mst005801_BinKbDictionary.BIN2.getCode() + "' 			");
			sb.append("				THEN '" + mst000101_ConstDictionary.AS400_BIN_KB + "' 						");
			sb.append("				ELSE '" + mst000101_ConstDictionary.AS400_SPACE + "' 					    ");
			sb.append("			 END BIN_2_KB 																    ");
			sb.append("			,EOS_KB                                                                         ");
			sb.append("			,TRIM(RTRIM(HINMEI_KANJI_NA,'　'))     					HINMEI_KANJI_NA         ");
			sb.append("			,MAKER_CD                                                                       ");
			sb.append("			,UPDATE_USER_ID                                                                 ");
			sb.append("			,UPDATE_TS                                                                      ");
			sb.append("		FROM                                                                                ");
			sb.append("			ZEN_MBD0_SYOHIN                                                                 ");
			sb.append("		) ZMS                                                                               ");
			sb.append("	ON                                                                                      ");
			sb.append("		IAS.SYOHIN_CD = ZMS.SYOHIN_CD                                                       ");
			sb.append("WHERE                                                                                    ");
			sb.append("	IAS.DATA_KB = '" + DATA_KBN_ADD + "'                                                    "); // 変更リストは修正分を対象にする
			sb.append("ORDER BY ");
			sb.append("	SEQNO ");
			
		}


		return sb.toString();
	}

/********** 共通処理 **********/

	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @param endFg true:最終項目、false:最終項目以外
	 * @return CSV出力データ
	 */
	private String createCsvString(String str) {
		return createCsvStringCommon(str, false);
	}
	private String createCsvEndString(String str) {
		return createCsvStringCommon(str, true);
	}
	private String createCsvStringCommon(String str, boolean endFg) {
		String val = "";
		if( str != null ){
			val = str.trim();
		}

		// セパレータの判定。最終項目の場合は改行する。
		if (endFg) {
			val += RETURN_CODE;
		} else {
			val += SPLIT_CODE;
		}

		return val;
	}

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
