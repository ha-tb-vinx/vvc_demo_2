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
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import org.apache.log4j.Level;

/**
 * <p>タイトル:情報分析用商品マスタ生成処理</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author  VINX
 * @version 1.00 2015/10/16 Sou OHPA用システムコントロールデータ修正
 * @version 1.01 2017/06/22 S.Takayama #5330対応
 */

public class MBA31001_CreateSyohin {

	// CSVファイル名（商品マスタ）
	private static final String FILE_NAME		= "IF_R_SYOHIN.csv";
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
	public MBA31001_CreateSyohin( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA31001_CreateSyohin() {

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
	 * 商品マスタ取得SQLを取得します。
	 * @return SQL
	 */
	private String getSelDataSQL() {

		StringBuffer sb  = new StringBuffer("");

		sb.append("SELECT ");
		sb.append("    TRIM(BUNRUI1_CD)             AS BUNRUI1_CD, ");
		sb.append("    TRIM(SYOHIN_CD)              AS SYOHIN_CD, ");
		sb.append("    TRIM(YUKO_DT)                AS YUKO_DT, ");
		sb.append("    TRIM(SYSTEM_KB)              AS SYSTEM_KB, ");
		sb.append("    TRIM(GYOSYU_KB)              AS GYOSYU_KB, ");
		sb.append("    TRIM(SYOHIN_KB)              AS SYOHIN_KB, ");
		sb.append("    TRIM(KETASU_KB)              AS KETASU_KB, ");
		sb.append("    TRIM(BUNRUI2_CD)             AS BUNRUI2_CD, ");
		sb.append("    TRIM(BUNRUI5_CD)             AS BUNRUI5_CD, ");
		sb.append("    TRIM(HINMOKU_CD)             AS HINMOKU_CD, ");
		sb.append("    TRIM(SYOHIN_2_CD)            AS SYOHIN_2_CD, ");
		sb.append("    TRIM(ZAIKO_SYOHIN_CD)        AS ZAIKO_SYOHIN_CD, ");
		sb.append("    TRIM(JYOHO_SYOHIN_CD)        AS JYOHO_SYOHIN_CD, ");
		sb.append("    TRIM(MAKER_CD)               AS MAKER_CD, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(HINMEI_KANJI_NA)        AS HINMEI_KANJI_NA, ");
		//sb.append("    TRIM(KIKAKU_KANJI_NA)        AS KIKAKU_KANJI_NA, ");
		//sb.append("    TRIM(KIKAKU_KANJI_2_NA)      AS KIKAKU_KANJI_2_NA, ");
		//sb.append("    TRIM(REC_HINMEI_KANJI_NA)    AS REC_HINMEI_KANJI_NA, ");
		//sb.append("    TRIM(HINMEI_KANA_NA)         AS HINMEI_KANA_NA, ");
		//sb.append("    TRIM(KIKAKU_KANA_NA)         AS KIKAKU_KANA_NA, ");
		//sb.append("    TRIM(KIKAKU_KANA_2_NA)       AS KIKAKU_KANA_2_NA, ");
		//sb.append("    TRIM(REC_HINMEI_KANA_NA)     AS REC_HINMEI_KANA_NA, ");
		sb.append( " '\"' || TRIM(HINMEI_KANJI_NA) || '\"' AS HINMEI_KANJI_NA, " );
		sb.append( " '\"' || TRIM(KIKAKU_KANJI_NA) || '\"' AS KIKAKU_KANJI_NA, " );
		sb.append( " '\"' || TRIM(KIKAKU_KANJI_2_NA) || '\"' AS KIKAKU_KANJI_2_NA, " );
		sb.append( " '\"' || TRIM(REC_HINMEI_KANJI_NA) || '\"' AS REC_HINMEI_KANJI_NA, " );
		sb.append( " '\"' || TRIM(HINMEI_KANA_NA) || '\"' AS HINMEI_KANA_NA, " );
		sb.append( " '\"' || TRIM(KIKAKU_KANA_NA) || '\"' AS KIKAKU_KANA_NA, " );
		sb.append( " '\"' || TRIM(KIKAKU_KANA_2_NA) || '\"' AS KIKAKU_KANA_2_NA, " );
		sb.append( " '\"' || TRIM(REC_HINMEI_KANA_NA) || '\"' AS REC_HINMEI_KANA_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    SYOHIN_WIDTH_QT, ");
		sb.append("    SYOHIN_HEIGHT_QT, ");
		sb.append("    SYOHIN_DEPTH_QT, ");
		sb.append("    TRIM(E_SHOP_KB)              AS E_SHOP_KB, ");
		sb.append("    TRIM(PB_KB)                  AS PB_KB, ");
		sb.append("    TRIM(SUBCLASS_CD)            AS SUBCLASS_CD, ");
		sb.append("    TRIM(HAIFU_CD)               AS HAIFU_CD, ");
		sb.append("    TRIM(ZEI_KB)                 AS ZEI_KB, ");
		sb.append("    TRIM(TAX_RATE_KB)            AS TAX_RATE_KB, ");
		sb.append("    GENTANKA_VL, ");
		sb.append("    BAITANKA_VL, ");
		// #5330 2017/06/22 S.Takayama (S)
		sb.append("    GENTANKA_NUKI_VL, ");
		sb.append("    BAITANKA_NUKI_VL, ");
		// #5330 2017/06/22 S.Takayama (E)
		sb.append("    TOSYO_BAIKA_VL, ");
		sb.append("    PRE_GENTANKA_VL, ");
		sb.append("    PRE_BAITANKA_VL, ");
		sb.append("    TOKUBETU_GENKA_VL, ");
		sb.append("    MAKER_KIBO_KAKAKU_VL, ");
		sb.append("    TRIM(SIIRESAKI_CD)           AS SIIRESAKI_CD, ");
		sb.append("    TRIM(DAIHYO_HAISO_CD)        AS DAIHYO_HAISO_CD, ");
		sb.append("    TRIM(TEN_SIIRESAKI_KANRI_CD) AS TEN_SIIRESAKI_KANRI_CD, ");
		sb.append("    TRIM(SIIRE_HINBAN_CD)        AS SIIRE_HINBAN_CD, ");
		sb.append("    TRIM(HACYU_SYOHIN_KB)        AS HACYU_SYOHIN_KB, ");
		sb.append("    TRIM(HACYU_SYOHIN_CD)        AS HACYU_SYOHIN_CD, ");
		sb.append("    TRIM(EOS_KB)                 AS EOS_KB, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(HACHU_TANI_NA)          AS HACHU_TANI_NA, ");
		sb.append( " '\"' || TRIM(HACHU_TANI_NA) || '\"' AS HACHU_TANI_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(HANBAI_TANI)            AS HANBAI_TANI, ");
		sb.append("    HACHUTANI_IRISU_QT, ");
		sb.append("    MAX_HACHUTANI_QT, ");
		sb.append("    TRIM(CASE_HACHU_KB)          AS CASE_HACHU_KB, ");
		sb.append("    BARA_IRISU_QT, ");
		sb.append("    TRIM(TEN_HACHU_ST_DT)        AS TEN_HACHU_ST_DT, ");
		sb.append("    TRIM(TEN_HACHU_ED_DT)        AS TEN_HACHU_ED_DT, ");
		sb.append("    TRIM(HANBAI_ST_DT)           AS HANBAI_ST_DT, ");
		sb.append("    TRIM(HANBAI_ED_DT)           AS HANBAI_ED_DT, ");
		sb.append("    TRIM(HANBAI_KIKAN_KB)        AS HANBAI_KIKAN_KB, ");
		sb.append("    TRIM(TEIKAN_KB)              AS TEIKAN_KB, ");
		sb.append("    TRIM(SOBA_SYOHIN_KB)         AS SOBA_SYOHIN_KB, ");
		sb.append("    TRIM(NOHIN_KIGEN_KB)         AS NOHIN_KIGEN_KB, ");
		sb.append("    NOHIN_KIGEN_QT, ");
		sb.append("    TRIM(BIN_1_KB)               AS BIN_1_KB, ");
		sb.append("    TRIM(HACHU_PATTERN_1_KB)     AS HACHU_PATTERN_1_KB, ");
		sb.append("    SIME_TIME_1_QT, ");
		sb.append("    TRIM(C_NOHIN_RTIME_1_KB)     AS C_NOHIN_RTIME_1_KB, ");
		sb.append("    TRIM(TEN_NOHIN_RTIME_1_KB)   AS TEN_NOHIN_RTIME_1_KB, ");
		sb.append("    TRIM(TEN_NOHIN_TIME_1_KB)    AS TEN_NOHIN_TIME_1_KB, ");
		sb.append("    TRIM(BIN_2_KB)               AS BIN_2_KB, ");
		sb.append("    TRIM(HACHU_PATTERN_2_KB)     AS HACHU_PATTERN_2_KB, ");
		sb.append("    SIME_TIME_2_QT, ");
		sb.append("    TRIM(C_NOHIN_RTIME_2_KB)     AS C_NOHIN_RTIME_2_KB, ");
		sb.append("    TRIM(TEN_NOHIN_RTIME_2_KB)   AS TEN_NOHIN_RTIME_2_KB, ");
		sb.append("    TRIM(TEN_NOHIN_TIME_2_KB)    AS TEN_NOHIN_TIME_2_KB, ");
		sb.append("    TRIM(BIN_3_KB)               AS BIN_3_KB, ");
		sb.append("    TRIM(HACHU_PATTERN_3_KB)     AS HACHU_PATTERN_3_KB, ");
		sb.append("    SIME_TIME_3_QT, ");
		sb.append("    TRIM(C_NOHIN_RTIME_3_KB)     AS C_NOHIN_RTIME_3_KB, ");
		sb.append("    TRIM(TEN_NOHIN_RTIME_3_KB)   AS TEN_NOHIN_RTIME_3_KB, ");
		sb.append("    TRIM(TEN_NOHIN_TIME_3_KB)    AS TEN_NOHIN_TIME_3_KB, ");
		sb.append("    TRIM(C_NOHIN_RTIME_KB)       AS C_NOHIN_RTIME_KB, ");
		sb.append("    TRIM(YUSEN_BIN_KB)           AS YUSEN_BIN_KB, ");
		sb.append("    TRIM(F_BIN_KB)               AS F_BIN_KB, ");
		sb.append("    TRIM(BUTURYU_KB)             AS BUTURYU_KB, ");
		sb.append("    TRIM(GOT_MUJYOKEN_FG)        AS GOT_MUJYOKEN_FG, ");
		sb.append("    TRIM(GOT_START_MM)           AS GOT_START_MM, ");
		sb.append("    TRIM(GOT_END_MM)             AS GOT_END_MM, ");
		sb.append("    TRIM(HACHU_TEISI_KB)         AS HACHU_TEISI_KB, ");
		sb.append("    TRIM(CENTER_ZAIKO_KB)        AS CENTER_ZAIKO_KB, ");
		sb.append("    TRIM(NOHIN_SYOHIN_CD)        AS NOHIN_SYOHIN_CD, ");
		sb.append("    TRIM(NYUKA_SYOHIN_CD)        AS NYUKA_SYOHIN_CD, ");
		sb.append("    TRIM(NYUKA_SYOHIN_2_CD)      AS NYUKA_SYOHIN_2_CD, ");
		sb.append("    TRIM(ITF_CD)                 AS ITF_CD, ");
		sb.append("    TRIM(GTIN_CD)                AS GTIN_CD, ");
		sb.append("    TRIM(VENDER_MAKER_CD)        AS VENDER_MAKER_CD, ");
		sb.append("    TRIM(ZAIKO_CENTER_CD)        AS ZAIKO_CENTER_CD, ");
		sb.append("    TRIM(ZAIKO_HACHU_SAKI)       AS ZAIKO_HACHU_SAKI, ");
		sb.append("    CENTER_WEIGHT_QT, ");
		sb.append("    PACK_WIDTH_QT, ");
		sb.append("    PACK_HEIGTH_QT, ");
		sb.append("    PACK_DEPTH_QT, ");
		sb.append("    PACK_WEIGHT_QT, ");
		sb.append("    TRIM(CENTER_HACHUTANI_KB)    AS CENTER_HACHUTANI_KB, ");
		sb.append("    CENTER_HACHUTANI_QT, ");
		sb.append("    CENTER_BARA_IRISU_QT, ");
		sb.append("    CENTER_IRISU_QT, ");
		sb.append("    CASE_IRISU_QT, ");
		sb.append("    CENTER_IRISU_2_QT, ");
		sb.append("    MIN_ZAIKOSU_QT, ");
		sb.append("    MAX_ZAIKOSU_QT, ");
		sb.append("    KIJUN_ZAIKOSU_QT, ");
		sb.append("    MIN_ZAIKONISSU_QT, ");
		sb.append("    MAX_ZAIKONISSU_QT, ");
		sb.append("    TRIM(CENTER_KYOYO_KB)        AS CENTER_KYOYO_KB, ");
		sb.append("    CENTER_KYOYO_DT, ");
//		sb.append("    TRIM(CENTER_SYOMI_KIKAN_KB)  AS CENTER_SYOMI_KIKAN_KB, ");
//		sb.append("    CENTER_SYOMI_KIKAN_DT, ");
		sb.append("    NULL AS CENTER_SYOMI_KIKAN_KB, ");
		sb.append("    NULL AS CENTER_SYOMI_KIKAN_DT, ");
		sb.append("    TRIM(TEN_GROUPNO_CD)         AS TEN_GROUPNO_CD, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(TC_JYOUHO_NA)           AS TC_JYOUHO_NA, ");
		sb.append( " '\"' || TRIM(TC_JYOUHO_NA) || '\"' AS TC_JYOUHO_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(NOHIN_ONDO_KB)          AS NOHIN_ONDO_KB, ");
		sb.append("    TRIM(YOKOMOTI_KB)            AS YOKOMOTI_KB, ");
		sb.append("    TRIM(SHINAZOROE_KB)          AS SHINAZOROE_KB, ");
		sb.append("    TRIM(HONBU_ZAI_KB)           AS HONBU_ZAI_KB, ");
		sb.append("    TRIM(TEN_ZAIKO_KB)           AS TEN_ZAIKO_KB, ");
		sb.append("    TRIM(HANBAI_SEISAKU_KB)      AS HANBAI_SEISAKU_KB, ");
		sb.append("    TRIM(HENPIN_NB)              AS HENPIN_NB, ");
		sb.append("    HENPIN_GENKA_VL, ");
		sb.append("    TRIM(CGC_HENPIN_KB)          AS CGC_HENPIN_KB, ");
		sb.append("    TRIM(HANBAI_LIMIT_KB)        AS HANBAI_LIMIT_KB, ");
		sb.append("    HANBAI_LIMIT_QT, ");
		sb.append("    TRIM(PLU_SEND_DT)            AS PLU_SEND_DT, ");
		sb.append("    TRIM(KEYPLU_FG)              AS KEYPLU_FG, ");
		sb.append("    TRIM(PLU_KB)                 AS PLU_KB, ");
		sb.append("    TRIM(SYUZEI_HOKOKU_KB)       AS SYUZEI_HOKOKU_KB, ");
		sb.append("    SAKE_NAIYORYO_QT, ");
		sb.append("    TAG_HYOJI_BAIKA_VL, ");
		sb.append("    KESHI_BAIKA_VL, ");
		sb.append("    TRIM(YORIDORI_KB)            AS YORIDORI_KB, ");
		sb.append("    YORIDORI_VL, ");
		sb.append("    YORIDORI_QT, ");
		sb.append("    TRIM(BLAND_CD)               AS BLAND_CD, ");
		sb.append("    TRIM(SEASON_CD)              AS SEASON_CD, ");
		sb.append("    TRIM(HUKUSYU_CD)             AS HUKUSYU_CD, ");
		sb.append("    TRIM(STYLE_CD)               AS STYLE_CD, ");
		sb.append("    TRIM(SCENE_CD)               AS SCENE_CD, ");
		sb.append("    TRIM(SEX_CD)                 AS SEX_CD, ");
		sb.append("    TRIM(AGE_CD)                 AS AGE_CD, ");
		sb.append("    TRIM(GENERATION_CD)          AS GENERATION_CD, ");
		sb.append("    TRIM(SOZAI_CD)               AS SOZAI_CD, ");
		sb.append("    TRIM(PATTERN_CD)             AS PATTERN_CD, ");
		sb.append("    TRIM(ORIAMI_CD)              AS ORIAMI_CD, ");
		sb.append("    TRIM(HUKA_KINO_CD)           AS HUKA_KINO_CD, ");
		sb.append("    TRIM(SODE_CD)                AS SODE_CD, ");
		sb.append("    TRIM(SIZE_CD)                AS SIZE_CD, ");
		sb.append("    TRIM(COLOR_CD)               AS COLOR_CD, ");
		sb.append("    KEIYAKU_SU_QT, ");
		sb.append("    TRIM(KEIYAKU_PATTERN_KB)     AS KEIYAKU_PATTERN_KB, ");
		sb.append("    TRIM(KEIYAKU_ST_DT)          AS KEIYAKU_ST_DT, ");
		sb.append("    TRIM(KEIYAKU_ED_DT)          AS KEIYAKU_ED_DT, ");
		sb.append("    TRIM(KUMISU_KB)              AS KUMISU_KB, ");
		sb.append("    TRIM(NEFUDA_KB)              AS NEFUDA_KB, ");
		sb.append("    TRIM(NEFUDA_UKEWATASI_DT)    AS NEFUDA_UKEWATASI_DT, ");
		sb.append("    TRIM(NEFUDA_UKEWATASI_KB)    AS NEFUDA_UKEWATASI_KB, ");
		sb.append("    TRIM(PC_KB)                  AS PC_KB, ");
		sb.append("    TRIM(DAISI_NO_NB)            AS DAISI_NO_NB, ");
		sb.append("    TRIM(UNIT_PRICE_TANI_KB)     AS UNIT_PRICE_TANI_KB, ");
		sb.append("    UNIT_PRICE_NAIYORYO_QT, ");
		sb.append("    UNIT_PRICE_KIJUN_TANI_QT, ");
		sb.append("    TRIM(SYOHI_KIGEN_KB)         AS SYOHI_KIGEN_KB, ");
		sb.append("    SYOHI_KIGEN_DT, ");
		sb.append("    TRIM(DAICHO_TENPO_KB)        AS DAICHO_TENPO_KB, ");
		sb.append("    TRIM(DAICHO_HONBU_KB)        AS DAICHO_HONBU_KB, ");
		sb.append("    TRIM(DAICHO_SIIRESAKI_KB)    AS DAICHO_SIIRESAKI_KB, ");
		sb.append("    TANA_NO_NB, ");
		sb.append("    DAN_NO_NB, ");
		sb.append("    TRIM(REBATE_FG)              AS REBATE_FG, ");
		sb.append("    TRIM(MARK_GROUP_CD)          AS MARK_GROUP_CD, ");
		sb.append("    TRIM(MARK_CD)                AS MARK_CD, ");
		sb.append("    TRIM(YUNYUHIN_KB)            AS YUNYUHIN_KB, ");
		sb.append("    TRIM(SANTI_CD)               AS SANTI_CD, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(D_ZOKUSEI_1_NA)         AS D_ZOKUSEI_1_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_1_NA)         AS S_ZOKUSEI_1_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_2_NA)         AS D_ZOKUSEI_2_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_2_NA)         AS S_ZOKUSEI_2_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_3_NA)         AS D_ZOKUSEI_3_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_3_NA)         AS S_ZOKUSEI_3_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_4_NA)         AS D_ZOKUSEI_4_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_4_NA)         AS S_ZOKUSEI_4_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_5_NA)         AS D_ZOKUSEI_5_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_5_NA)         AS S_ZOKUSEI_5_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_6_NA)         AS D_ZOKUSEI_6_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_6_NA)         AS S_ZOKUSEI_6_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_7_NA)         AS D_ZOKUSEI_7_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_7_NA)         AS S_ZOKUSEI_7_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_8_NA)         AS D_ZOKUSEI_8_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_8_NA)         AS S_ZOKUSEI_8_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_9_NA)         AS D_ZOKUSEI_9_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_9_NA)         AS S_ZOKUSEI_9_NA, ");
		//sb.append("    TRIM(D_ZOKUSEI_10_NA)        AS D_ZOKUSEI_10_NA, ");
		//sb.append("    TRIM(S_ZOKUSEI_10_NA)        AS S_ZOKUSEI_10_NA, ");
		sb.append( " '\"' || TRIM(D_ZOKUSEI_1_NA) || '\"' AS D_ZOKUSEI_1_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_1_NA) || '\"' AS S_ZOKUSEI_1_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_2_NA) || '\"' AS D_ZOKUSEI_2_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_2_NA) || '\"' AS S_ZOKUSEI_2_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_3_NA) || '\"' AS D_ZOKUSEI_3_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_3_NA) || '\"' AS S_ZOKUSEI_3_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_4_NA) || '\"' AS D_ZOKUSEI_4_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_4_NA) || '\"' AS S_ZOKUSEI_4_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_5_NA) || '\"' AS D_ZOKUSEI_5_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_5_NA) || '\"' AS S_ZOKUSEI_5_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_6_NA) || '\"' AS D_ZOKUSEI_6_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_6_NA) || '\"' AS S_ZOKUSEI_6_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_7_NA) || '\"' AS D_ZOKUSEI_7_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_7_NA) || '\"' AS S_ZOKUSEI_7_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_8_NA) || '\"' AS D_ZOKUSEI_8_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_8_NA) || '\"' AS S_ZOKUSEI_8_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_9_NA) || '\"' AS D_ZOKUSEI_9_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_9_NA) || '\"' AS S_ZOKUSEI_9_NA, " );
		sb.append( " '\"' || TRIM(D_ZOKUSEI_10_NA) || '\"' AS D_ZOKUSEI_10_NA, " );
		sb.append( " '\"' || TRIM(S_ZOKUSEI_10_NA) || '\"' AS S_ZOKUSEI_10_NA, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(FUJI_SYOHIN_KB)         AS FUJI_SYOHIN_KB, ");
		// 2015/11/05 togashi システムコントロールデータ修正 Start
		//sb.append("    TRIM(COMMENT_TX)             AS COMMENT_TX, ");
		sb.append( " '\"' || TRIM(COMMENT_TX) || '\"' AS COMMENT_TX, " );
		// 2015/11/05 togashi システムコントロールデータ修正 End
		sb.append("    TRIM(AUTO_DEL_KB)            AS AUTO_DEL_KB, ");
		sb.append("    TRIM(MST_SIYOFUKA_KB)        AS MST_SIYOFUKA_KB, ");
		sb.append("    TRIM(HAIBAN_FG)              AS HAIBAN_FG, ");
		sb.append("    TRIM(SINKI_TOROKU_DT)        AS SINKI_TOROKU_DT, ");
		sb.append("    TRIM(HENKO_DT)               AS HENKO_DT, ");
		sb.append("    TRIM(SYOKAI_TOROKU_TS)       AS SYOKAI_TOROKU_TS, ");
		sb.append("    TRIM(SYOKAI_USER_ID)         AS SYOKAI_USER_ID, ");
		sb.append("    TRIM(INSERT_TS)              AS INSERT_TS, ");
		sb.append("    TRIM(INSERT_USER_ID)         AS INSERT_USER_ID, ");
		sb.append("    TRIM(UPDATE_TS)              AS UPDATE_TS, ");
		sb.append("    TRIM(UPDATE_USER_ID)         AS UPDATE_USER_ID, ");
		sb.append("    TRIM(BATCH_UPDATE_TS)        AS BATCH_UPDATE_TS, ");
		sb.append("    TRIM(BATCH_UPDATE_ID)        AS BATCH_UPDATE_ID, ");
		sb.append("    TRIM(DELETE_FG)              AS DELETE_FG ");
		sb.append("FROM ");
		sb.append("    TMP_R_SYOHIN TB1 ");
		sb.append("WHERE ");
		sb.append("    ( ");
		sb.append("        TB1.YUKO_DT = ( ");
		sb.append("                          SELECT ");
		sb.append("                              MAX( YUKO_DT ) ");
		sb.append("                          FROM ");
		sb.append("                              TMP_R_SYOHIN ");
		sb.append("                          WHERE ");
		sb.append("                              YUKO_DT <= '").append( DateChanger.addDate(batchDt, SYORI_SPAN) ).append("' ");
		sb.append("                          AND SYOHIN_CD = TB1.SYOHIN_CD ");
		// #6620 DEL 2022.06.29 SIEU.D (S)
		// sb.append("                          AND BUNRUI1_CD = TB1.BUNRUI1_CD ");
		// #6620 DEL 2022.06.29 SIEU.D (E)
		sb.append("                      ) ");
		sb.append("     ) ");
		sb.append("     OR");
		sb.append("     ( TB1.YUKO_DT > '").append( DateChanger.addDate(batchDt, SYORI_SPAN) ).append("' ) ");
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


		// システム区分
		if( rs.getString("SYSTEM_KB") != null ){
			sb.append( rs.getString("SYSTEM_KB").trim() );
		}
		sb.append( SPLIT_CODE );


		// 業種区分
		if( rs.getString("GYOSYU_KB") != null ){
			sb.append( rs.getString("GYOSYU_KB").trim() );
		}
		sb.append( SPLIT_CODE );


		// 商品区分
		if( rs.getString("SYOHIN_KB") != null ){
			sb.append( rs.getString("SYOHIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );


		// 桁数区分
		if( rs.getString("KETASU_KB") != null ){
			sb.append( rs.getString("KETASU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 分類２コード
		if( rs.getString("BUNRUI2_CD") != null ){
			sb.append( rs.getString("BUNRUI2_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 分類５コード
		if( rs.getString("BUNRUI5_CD") != null ){
			sb.append( rs.getString("BUNRUI5_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 品目
		if( rs.getString("HINMOKU_CD") != null ){
			sb.append( rs.getString("HINMOKU_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品コード２
		if( rs.getString("SYOHIN_2_CD") != null ){
			sb.append( rs.getString("SYOHIN_2_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■在庫用商品集計コード
		if( rs.getString("ZAIKO_SYOHIN_CD") != null ){
			sb.append( rs.getString("ZAIKO_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■情報系用商品集計コード
		if( rs.getString("JYOHO_SYOHIN_CD") != null ){
			sb.append( rs.getString("JYOHO_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ＪＡＮメーカーコード
		if( rs.getString("MAKER_CD") != null ){
			sb.append( rs.getString("MAKER_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 漢字品名
		if( rs.getString("HINMEI_KANJI_NA") != null ){
			sb.append( rs.getString("HINMEI_KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 漢字規格
		if( rs.getString("KIKAKU_KANJI_NA") != null ){
			sb.append( rs.getString("KIKAKU_KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 漢字規格２
		if( rs.getString("KIKAKU_KANJI_2_NA") != null ){
			sb.append( rs.getString("KIKAKU_KANJI_2_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// POSレシート品名(漢字)
		if( rs.getString("REC_HINMEI_KANJI_NA") != null ){
			sb.append( rs.getString("REC_HINMEI_KANJI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// カナ品名
		if( rs.getString("HINMEI_KANA_NA") != null ){
			sb.append( rs.getString("HINMEI_KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// カナ規格
		if( rs.getString("KIKAKU_KANA_NA") != null ){
			sb.append( rs.getString("KIKAKU_KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// カナ規格２
		if( rs.getString("KIKAKU_KANA_2_NA") != null ){
			sb.append( rs.getString("KIKAKU_KANA_2_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// POSレシート品名(カナ)
		if( rs.getString("REC_HINMEI_KANA_NA") != null ){
			sb.append( rs.getString("REC_HINMEI_KANA_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品サイズ(幅)
		if( rs.getString("SYOHIN_WIDTH_QT") != null ){
			sb.append( rs.getString("SYOHIN_WIDTH_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品サイズ(高さ)
		if( rs.getString("SYOHIN_HEIGHT_QT") != null ){
			sb.append( rs.getString("SYOHIN_HEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品サイズ(奥行き)
		if( rs.getString("SYOHIN_DEPTH_QT") != null ){
			sb.append( rs.getString("SYOHIN_DEPTH_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// Eショップ区分
		if( rs.getString("E_SHOP_KB") != null ){
			sb.append( rs.getString("E_SHOP_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// PB区分
		if( rs.getString("PB_KB") != null ){
			sb.append( rs.getString("PB_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// サブクラスコード
		if( rs.getString("SUBCLASS_CD") != null ){
			sb.append( rs.getString("SUBCLASS_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 配布コード
		if( rs.getString("HAIFU_CD") != null ){
			sb.append( rs.getString("HAIFU_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 税区分
		if( rs.getString("ZEI_KB") != null ){
			sb.append( rs.getString("ZEI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■税率区分
		if( rs.getString("TAX_RATE_KB") != null ){
			sb.append( rs.getString("TAX_RATE_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 原価単価
		if( rs.getString("GENTANKA_VL") != null ){
			sb.append(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(rs.getString("GENTANKA_VL").trim())));
		}
		sb.append( SPLIT_CODE );

		// 売価単価
		if( rs.getString("BAITANKA_VL") != null ){
			sb.append( MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(rs.getString("BAITANKA_VL").trim())));
		}
		sb.append( SPLIT_CODE );
		// #5330 2017/06/22 S.Takayama (S)
		//○原価単価（税抜）
		if( rs.getString("GENTANKA_NUKI_VL") != null ){
			sb.append(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(rs.getString("GENTANKA_NUKI_VL").trim())));
		}
		sb.append( SPLIT_CODE );
		
		//○売価単価（税抜）
		if( rs.getString("BAITANKA_NUKI_VL") != null ){
			sb.append(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(rs.getString("BAITANKA_NUKI_VL").trim())));
		}
		sb.append( SPLIT_CODE );
		
		// #5330 2017/06/22 S.Takayama (E)
		// 当初売価
		if( rs.getString("TOSYO_BAIKA_VL") != null ){
			sb.append( rs.getString("TOSYO_BAIKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 前回原価単価
		if( rs.getString("PRE_GENTANKA_VL") != null ){
			sb.append( rs.getString("PRE_GENTANKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 前回売価単価
		if( rs.getString("PRE_BAITANKA_VL") != null ){
			sb.append( rs.getString("PRE_BAITANKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 特別原価
		if( rs.getString("TOKUBETU_GENKA_VL") != null ){
			sb.append( rs.getString("TOKUBETU_GENKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// メーカー希望小売り価格
		if( rs.getString("MAKER_KIBO_KAKAKU_VL") != null ){
			sb.append( MoneyUtil.removeFormatMoney(MoneyUtil. formatSellUnitString(rs.getString("MAKER_KIBO_KAKAKU_VL").trim())));
		}
		sb.append( SPLIT_CODE );

		// 仕入先コード
		if( rs.getString("SIIRESAKI_CD") != null ){
			sb.append( rs.getString("SIIRESAKI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 代表配送先コード
		if( rs.getString("DAIHYO_HAISO_CD") != null ){
			sb.append( rs.getString("DAIHYO_HAISO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店別仕入先管理コード
		if( rs.getString("TEN_SIIRESAKI_KANRI_CD") != null ){
			sb.append( rs.getString("TEN_SIIRESAKI_KANRI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 仕入先品番
		if( rs.getString("SIIRE_HINBAN_CD") != null ){
			sb.append( rs.getString("SIIRE_HINBAN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注商品コード区分
		if( rs.getString("HACYU_SYOHIN_KB") != null ){
			sb.append( rs.getString("HACYU_SYOHIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注商品コード
		if( rs.getString("HACYU_SYOHIN_CD") != null ){
			sb.append( rs.getString("HACYU_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// EOS区分
		if( rs.getString("EOS_KB") != null ){
			sb.append( rs.getString("EOS_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注単位呼称
		if( rs.getString("HACHU_TANI_NA") != null ){
			sb.append( rs.getString("HACHU_TANI_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■販売単位呼称
		if( rs.getString("HANBAI_TANI") != null ){
			sb.append( rs.getString("HANBAI_TANI").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注単位(入数)
		if( rs.getString("HACHUTANI_IRISU_QT") != null ){
			sb.append( rs.getString("HACHUTANI_IRISU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ●最大発注単位数
		if( rs.getString("MAX_HACHUTANI_QT") != null ){
			sb.append( rs.getString("MAX_HACHUTANI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■ケース発注区分
		if( rs.getString("CASE_HACHU_KB") != null ){
			sb.append( rs.getString("CASE_HACHU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■バラ入数
		if( rs.getString("BARA_IRISU_QT") != null ){
			sb.append( rs.getString("BARA_IRISU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗発注開始日
		if( rs.getString("TEN_HACHU_ST_DT") != null ){
			sb.append( rs.getString("TEN_HACHU_ST_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店舗発注終了日
		if( rs.getString("TEN_HACHU_ED_DT") != null ){
			sb.append( rs.getString("TEN_HACHU_ED_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売開始日
		if( rs.getString("HANBAI_ST_DT") != null ){
			sb.append( rs.getString("HANBAI_ST_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売終了日
		if( rs.getString("HANBAI_ED_DT") != null ){
			sb.append( rs.getString("HANBAI_ED_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売期間区分
		if( rs.getString("HANBAI_KIKAN_KB") != null ){
			sb.append( rs.getString("HANBAI_KIKAN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 定貫区分
		if( rs.getString("TEIKAN_KB") != null ){
			sb.append( rs.getString("TEIKAN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 相場商品区分
		if( rs.getString("SOBA_SYOHIN_KB") != null ){
			sb.append( rs.getString("SOBA_SYOHIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 納品期限区分
		if( rs.getString("NOHIN_KIGEN_KB") != null ){
			sb.append( rs.getString("NOHIN_KIGEN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 納品期限
		if( rs.getString("NOHIN_KIGEN_QT") != null ){
			sb.append( rs.getString("NOHIN_KIGEN_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①便区分
		if( rs.getString("BIN_1_KB") != null ){
			sb.append( rs.getString("BIN_1_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①発注パターン区分
		if( rs.getString("HACHU_PATTERN_1_KB") != null ){
			sb.append( rs.getString("HACHU_PATTERN_1_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①締め時間
		if( rs.getString("SIME_TIME_1_QT") != null ){
			sb.append( rs.getString("SIME_TIME_1_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①センタ納品リードタイム
		if( rs.getString("C_NOHIN_RTIME_1_KB") != null ){
			sb.append( rs.getString("C_NOHIN_RTIME_1_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①店納品リードタイム
		if( rs.getString("TEN_NOHIN_RTIME_1_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_RTIME_1_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ①店納品時間帯
		if( rs.getString("TEN_NOHIN_TIME_1_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_TIME_1_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②便区分
		if( rs.getString("BIN_2_KB") != null ){
			sb.append( rs.getString("BIN_2_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②発注パターン区分
		if( rs.getString("HACHU_PATTERN_2_KB") != null ){
			sb.append( rs.getString("HACHU_PATTERN_2_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②締め時間
		if( rs.getString("SIME_TIME_2_QT") != null ){
			sb.append( rs.getString("SIME_TIME_2_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②センタ納品リードタイム
		if( rs.getString("C_NOHIN_RTIME_2_KB") != null ){
			sb.append( rs.getString("C_NOHIN_RTIME_2_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②店納品リードタイム
		if( rs.getString("TEN_NOHIN_RTIME_2_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_RTIME_2_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ②店納品時間帯
		if( rs.getString("TEN_NOHIN_TIME_2_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_TIME_2_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③便区分
		if( rs.getString("BIN_3_KB") != null ){
			sb.append( rs.getString("BIN_3_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③発注パターン区分
		if( rs.getString("HACHU_PATTERN_3_KB") != null ){
			sb.append( rs.getString("HACHU_PATTERN_3_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③締め時間
		if( rs.getString("SIME_TIME_3_QT") != null ){
			sb.append( rs.getString("SIME_TIME_3_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③センタ納品リードタイム
		if( rs.getString("C_NOHIN_RTIME_3_KB") != null ){
			sb.append( rs.getString("C_NOHIN_RTIME_3_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③店納品リードタイム
		if( rs.getString("TEN_NOHIN_RTIME_3_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_RTIME_3_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ③店納品時間帯
		if( rs.getString("TEN_NOHIN_TIME_3_KB") != null ){
			sb.append( rs.getString("TEN_NOHIN_TIME_3_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// センタ納品リードタイム
		if( rs.getString("C_NOHIN_RTIME_KB") != null ){
			sb.append( rs.getString("C_NOHIN_RTIME_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 優先便区分
		if( rs.getString("YUSEN_BIN_KB") != null ){
			sb.append( rs.getString("YUSEN_BIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■F便区分
		if( rs.getString("F_BIN_KB") != null ){
			sb.append( rs.getString("F_BIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 物流区分
		if( rs.getString("BUTURYU_KB") != null ){
			sb.append( rs.getString("BUTURYU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// GOT無条件表示対象
		if( rs.getString("GOT_MUJYOKEN_FG") != null ){
			sb.append( rs.getString("GOT_MUJYOKEN_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■季節開始月日
		if( rs.getString("GOT_START_MM") != null ){
			sb.append( rs.getString("GOT_START_MM").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■季節終了月日
		if( rs.getString("GOT_END_MM") != null ){
			sb.append( rs.getString("GOT_END_MM").trim() );
		}
		sb.append( SPLIT_CODE );

		// 発注停止区分
		if( rs.getString("HACHU_TEISI_KB") != null ){
			sb.append( rs.getString("HACHU_TEISI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// センター在庫区分
		if( rs.getString("CENTER_ZAIKO_KB") != null ){
			sb.append( rs.getString("CENTER_ZAIKO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 納品商品コード
		if( rs.getString("NOHIN_SYOHIN_CD") != null ){
			sb.append( rs.getString("NOHIN_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 入荷時商品コード
		if( rs.getString("NYUKA_SYOHIN_CD") != null ){
			sb.append( rs.getString("NYUKA_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■入荷時商品コード２
		if( rs.getString("NYUKA_SYOHIN_2_CD") != null ){
			sb.append( rs.getString("NYUKA_SYOHIN_2_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ITFコード
		if( rs.getString("ITF_CD") != null ){
			sb.append( rs.getString("ITF_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// GTINコード
		if( rs.getString("GTIN_CD") != null ){
			sb.append( rs.getString("GTIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■ベンダーメーカーコード
		if( rs.getString("VENDER_MAKER_CD") != null ){
			sb.append( rs.getString("VENDER_MAKER_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 在庫センターコード
		if( rs.getString("ZAIKO_CENTER_CD") != null ){
			sb.append( rs.getString("ZAIKO_CENTER_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 在庫補充発注先
		if( rs.getString("ZAIKO_HACHU_SAKI") != null ){
			sb.append( rs.getString("NOHIN_SYOHIN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// センター重量
		if( rs.getString("CENTER_WEIGHT_QT") != null ){
			sb.append( rs.getString("CENTER_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 外箱サイズ幅
		if( rs.getString("PACK_WIDTH_QT") != null ){
			sb.append( rs.getString("PACK_WIDTH_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 外箱サイズ高さ
		if( rs.getString("PACK_HEIGTH_QT") != null ){
			sb.append( rs.getString("PACK_HEIGTH_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 外箱サイズ奥行き
		if( rs.getString("PACK_DEPTH_QT") != null ){
			sb.append( rs.getString("PACK_DEPTH_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 外箱重量
		if( rs.getString("PACK_WEIGHT_QT") != null ){
			sb.append( rs.getString("PACK_WEIGHT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// センター発注単位区分
		if( rs.getString("CENTER_HACHUTANI_KB") != null ){
			sb.append( rs.getString("CENTER_HACHUTANI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// センター発注単位数
		if( rs.getString("CENTER_HACHUTANI_QT") != null ){
			sb.append( rs.getString("CENTER_HACHUTANI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■センターバラ入数
		if( rs.getString("CENTER_BARA_IRISU_QT") != null ){
			sb.append( rs.getString("CENTER_BARA_IRISU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ●最低入数
		if( rs.getString("CENTER_IRISU_QT") != null ){
			sb.append( rs.getString("CENTER_IRISU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ケース入り数
		if( rs.getString("CASE_IRISU_QT") != null ){
			sb.append( rs.getString("CASE_IRISU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■梱り合せ数
		if( rs.getString("CENTER_IRISU_2_QT") != null ){
			sb.append( rs.getString("CENTER_IRISU_2_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ●最小在庫数
		if( rs.getString("MIN_ZAIKOSU_QT") != null ){
			sb.append( rs.getString("MIN_ZAIKOSU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 最大在庫数
		if( rs.getString("MAX_ZAIKOSU_QT") != null ){
			sb.append( rs.getString("MAX_ZAIKOSU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 基準在庫日数
		if( rs.getString("KIJUN_ZAIKOSU_QT") != null ){
			sb.append( rs.getString("KIJUN_ZAIKOSU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■最小在庫日数
		if( rs.getString("MIN_ZAIKONISSU_QT") != null ){
			sb.append( rs.getString("MIN_ZAIKONISSU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■最大在庫日数
		if( rs.getString("MAX_ZAIKONISSU_QT") != null ){
			sb.append( rs.getString("MAX_ZAIKONISSU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■センター許容区分
		if( rs.getString("CENTER_KYOYO_KB") != null ){
			sb.append( rs.getString("CENTER_KYOYO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// センター許容日
		if( rs.getString("CENTER_KYOYO_DT") != null ){
			sb.append( rs.getString("CENTER_KYOYO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■センター賞味期間区分
		if( rs.getString("CENTER_SYOMI_KIKAN_KB") != null ){
			sb.append( rs.getString("CENTER_SYOMI_KIKAN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■センター賞味期間
		if( rs.getString("CENTER_SYOMI_KIKAN_DT") != null ){
			sb.append( rs.getString("CENTER_SYOMI_KIKAN_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店グルーピングNO(物流）
		if( rs.getString("TEN_GROUPNO_CD") != null ){
			sb.append( rs.getString("TEN_GROUPNO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// TC情報
		if( rs.getString("TC_JYOUHO_NA") != null ){
			sb.append( rs.getString("TC_JYOUHO_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 納品温度帯
		if( rs.getString("NOHIN_ONDO_KB") != null ){
			sb.append( rs.getString("NOHIN_ONDO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 横もち区分
		if( rs.getString("YOKOMOTI_KB") != null ){
			sb.append( rs.getString("YOKOMOTI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 品揃区分
		if( rs.getString("SHINAZOROE_KB") != null ){
			sb.append( rs.getString("SHINAZOROE_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 本部在庫区分
		if( rs.getString("HONBU_ZAI_KB") != null ){
			sb.append( rs.getString("HONBU_ZAI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 店在庫区分
		if( rs.getString("TEN_ZAIKO_KB") != null ){
			sb.append( rs.getString("TEN_ZAIKO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売政策区分
		if( rs.getString("HANBAI_SEISAKU_KB") != null ){
			sb.append( rs.getString("HANBAI_SEISAKU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 返品契約書NO
		if( rs.getString("HENPIN_NB") != null ){
			sb.append( rs.getString("HENPIN_NB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 返品原価
		if( rs.getString("HENPIN_GENKA_VL") != null ){
			sb.append( rs.getString("HENPIN_GENKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■CGC返品区分
		if( rs.getString("CGC_HENPIN_KB") != null ){
			sb.append( rs.getString("CGC_HENPIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売期限区分
		if( rs.getString("HANBAI_LIMIT_KB") != null ){
			sb.append( rs.getString("HANBAI_LIMIT_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 販売期限
		if( rs.getString("HANBAI_LIMIT_QT") != null ){
			sb.append( rs.getString("HANBAI_LIMIT_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// PLU送信日
		if( rs.getString("PLU_SEND_DT") != null ){
			sb.append( rs.getString("PLU_SEND_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// キーPLU対象
		if( rs.getString("KEYPLU_FG") != null ){
			sb.append( rs.getString("KEYPLU_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// PLU区分
		if( rs.getString("PLU_KB") != null ){
			sb.append( rs.getString("PLU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 酒税報告分類
		if( rs.getString("SYUZEI_HOKOKU_KB") != null ){
			sb.append( rs.getString("SYUZEI_HOKOKU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■酒内容量
		if( rs.getString("SAKE_NAIYORYO_QT") != null ){
			sb.append( rs.getString("SAKE_NAIYORYO_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// タグ表示売価
		if( rs.getString("TAG_HYOJI_BAIKA_VL") != null ){
			sb.append( rs.getString("TAG_HYOJI_BAIKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// 消札売価
		if( rs.getString("KESHI_BAIKA_VL") != null ){
			sb.append( rs.getString("KESHI_BAIKA_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// よりどり種類
		if( rs.getString("YORIDORI_KB") != null ){
			sb.append( rs.getString("YORIDORI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// よりどり価格
		if( rs.getString("YORIDORI_VL") != null ){
			sb.append( rs.getString("YORIDORI_VL").trim() );
		}
		sb.append( SPLIT_CODE );

		// よりどり数量
		if( rs.getString("YORIDORI_QT") != null ){
			sb.append( rs.getString("YORIDORI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ブランドコード
		if( rs.getString("BLAND_CD") != null ){
			sb.append( rs.getString("BLAND_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// シーズンコード
		if( rs.getString("SEASON_CD") != null ){
			sb.append( rs.getString("SEASON_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 服種コード
		if( rs.getString("HUKUSYU_CD") != null ){
			sb.append( rs.getString("HUKUSYU_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// スタイルコード
		if( rs.getString("STYLE_CD") != null ){
			sb.append( rs.getString("STYLE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// シーンコード
		if( rs.getString("SCENE_CD") != null ){
			sb.append( rs.getString("SCENE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 性別コード
		if( rs.getString("SEX_CD") != null ){
			sb.append( rs.getString("SEX_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 年代コード
		if( rs.getString("AGE_CD") != null ){
			sb.append( rs.getString("AGE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 世代コード
		if( rs.getString("GENERATION_CD") != null ){
			sb.append( rs.getString("GENERATION_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 素材コード
		if( rs.getString("SOZAI_CD") != null ){
			sb.append( rs.getString("SOZAI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// パターンコード
		if( rs.getString("PATTERN_CD") != null ){
			sb.append( rs.getString("PATTERN_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 織り編みコード
		if( rs.getString("ORIAMI_CD") != null ){
			sb.append( rs.getString("ORIAMI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 付加機能コード
		if( rs.getString("HUKA_KINO_CD") != null ){
			sb.append( rs.getString("HUKA_KINO_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 袖丈コード
		if( rs.getString("SODE_CD") != null ){
			sb.append( rs.getString("SODE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// サイズコード
		if( rs.getString("SIZE_CD") != null ){
			sb.append( rs.getString("SIZE_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// カラーコード
		if( rs.getString("COLOR_CD") != null ){
			sb.append( rs.getString("COLOR_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 契約数
		if( rs.getString("KEIYAKU_SU_QT") != null ){
			sb.append( rs.getString("KEIYAKU_SU_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 契約パターン
		if( rs.getString("KEIYAKU_PATTERN_KB") != null ){
			sb.append( rs.getString("KEIYAKU_PATTERN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 契約開始期間
		if( rs.getString("KEIYAKU_ST_DT") != null ){
			sb.append( rs.getString("KEIYAKU_ST_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 契約終了期間
		if( rs.getString("KEIYAKU_ED_DT") != null ){
			sb.append( rs.getString("KEIYAKU_ED_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 組数区分
		if( rs.getString("KUMISU_KB") != null ){
			sb.append( rs.getString("KUMISU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 値札区分
		if( rs.getString("NEFUDA_KB") != null ){
			sb.append( rs.getString("NEFUDA_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 値札受渡日
		if( rs.getString("NEFUDA_UKEWATASI_DT") != null ){
			sb.append( rs.getString("NEFUDA_UKEWATASI_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 値札受渡方法
		if( rs.getString("NEFUDA_UKEWATASI_KB") != null ){
			sb.append( rs.getString("NEFUDA_UKEWATASI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// PC発行区分
		if( rs.getString("PC_KB") != null ){
			sb.append( rs.getString("PC_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 台紙NO
		if( rs.getString("DAISI_NO_NB") != null ){
			sb.append( rs.getString("DAISI_NO_NB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ユニットプライス-単位区分
		if( rs.getString("UNIT_PRICE_TANI_KB") != null ){
			sb.append( rs.getString("UNIT_PRICE_TANI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// ユニットプライス-内容量
		if( rs.getString("UNIT_PRICE_NAIYORYO_QT") != null ){
			sb.append( rs.getString("UNIT_PRICE_NAIYORYO_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ユニットプライス-基準単位量
		if( rs.getString("UNIT_PRICE_KIJUN_TANI_QT") != null ){
			sb.append( rs.getString("UNIT_PRICE_KIJUN_TANI_QT").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■消費期限区分
		if( rs.getString("SYOHI_KIGEN_KB") != null ){
			sb.append( rs.getString("SYOHI_KIGEN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 消費期限
		if( rs.getString("SYOHI_KIGEN_DT") != null ){
			sb.append( rs.getString("SYOHI_KIGEN_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品台帳(店舗)
		if( rs.getString("DAICHO_TENPO_KB") != null ){
			sb.append( rs.getString("DAICHO_TENPO_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品台帳(本部)
		if( rs.getString("DAICHO_HONBU_KB") != null ){
			sb.append( rs.getString("DAICHO_HONBU_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 商品台帳(仕入先)
		if( rs.getString("DAICHO_SIIRESAKI_KB") != null ){
			sb.append( rs.getString("DAICHO_SIIRESAKI_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 棚NO
		if( rs.getString("TANA_NO_NB") != null ){
			sb.append( rs.getString("TANA_NO_NB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 段NO
		if( rs.getString("DAN_NO_NB") != null ){
			sb.append( rs.getString("DAN_NO_NB").trim() );
		}
		sb.append( SPLIT_CODE );

		// リベート対象フラグ
		if( rs.getString("REBATE_FG") != null ){
			sb.append( rs.getString("REBATE_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// マークグループ
		if( rs.getString("MARK_GROUP_CD") != null ){
			sb.append( rs.getString("MARK_GROUP_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// マークコード
		if( rs.getString("MARK_CD") != null ){
			sb.append( rs.getString("MARK_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 輸入品区分
		if( rs.getString("YUNYUHIN_KB") != null ){
			sb.append( rs.getString("YUNYUHIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 原産国/産地コード
		if( rs.getString("SANTI_CD") != null ){
			sb.append( rs.getString("SANTI_CD").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性１
		if( rs.getString("D_ZOKUSEI_1_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_1_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性１
		if( rs.getString("S_ZOKUSEI_1_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_1_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性２
		if( rs.getString("D_ZOKUSEI_2_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_2_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性２
		if( rs.getString("S_ZOKUSEI_2_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_2_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性３
		if( rs.getString("D_ZOKUSEI_3_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_3_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性３
		if( rs.getString("S_ZOKUSEI_3_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_3_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性４
		if( rs.getString("D_ZOKUSEI_4_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_4_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性４
		if( rs.getString("S_ZOKUSEI_4_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_4_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性５
		if( rs.getString("D_ZOKUSEI_5_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_5_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性５
		if( rs.getString("S_ZOKUSEI_5_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_5_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性６
		if( rs.getString("D_ZOKUSEI_6_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_6_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性６
		if( rs.getString("S_ZOKUSEI_6_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_6_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性７
		if( rs.getString("D_ZOKUSEI_7_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_7_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性７
		if( rs.getString("S_ZOKUSEI_7_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_7_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性８
		if( rs.getString("D_ZOKUSEI_8_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_8_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性８
		if( rs.getString("S_ZOKUSEI_8_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_8_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性９
		if( rs.getString("D_ZOKUSEI_9_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_9_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性９
		if( rs.getString("S_ZOKUSEI_9_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_9_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 大属性１０
		if( rs.getString("D_ZOKUSEI_10_NA") != null ){
			sb.append( rs.getString("D_ZOKUSEI_10_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// 小属性１０
		if( rs.getString("S_ZOKUSEI_10_NA") != null ){
			sb.append( rs.getString("S_ZOKUSEI_10_NA").trim() );
		}
		sb.append( SPLIT_CODE );

		// F商品区分
		if( rs.getString("FUJI_SYOHIN_KB") != null ){
			sb.append( rs.getString("FUJI_SYOHIN_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// コメント
		if( rs.getString("COMMENT_TX") != null ){
			sb.append( rs.getString("COMMENT_TX").trim() );
		}
		sb.append( SPLIT_CODE );

		// 自動削除対象区分
		if( rs.getString("AUTO_DEL_KB") != null ){
			sb.append( rs.getString("AUTO_DEL_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// マスタ使用不可区分
		if( rs.getString("MST_SIYOFUKA_KB") != null ){
			sb.append( rs.getString("MST_SIYOFUKA_KB").trim() );
		}
		sb.append( SPLIT_CODE );

		// 廃番実施フラグ
		if( rs.getString("HAIBAN_FG") != null ){
			sb.append( rs.getString("HAIBAN_FG").trim() );
		}
		sb.append( SPLIT_CODE );

		// 新規登録日
		if( rs.getString("SINKI_TOROKU_DT") != null ){
			sb.append( rs.getString("SINKI_TOROKU_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 変更日
		if( rs.getString("HENKO_DT") != null ){
			sb.append( rs.getString("HENKO_DT").trim() );
		}
		sb.append( SPLIT_CODE );

		// 初回登録日
		if( rs.getString("SYOKAI_TOROKU_TS") != null ){
			sb.append( rs.getString("SYOKAI_TOROKU_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 初回登録社員ID
		if( rs.getString("SYOKAI_USER_ID") != null ){
			sb.append( rs.getString("SYOKAI_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成年月日
		if( rs.getString("INSERT_TS") != null ){
			sb.append( rs.getString("INSERT_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 作成者ID
		if( rs.getString("INSERT_USER_ID") != null ){
			sb.append( rs.getString("INSERT_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新年月日
		if( rs.getString("UPDATE_TS") != null ){
			sb.append( rs.getString("UPDATE_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// 更新者ID
		if( rs.getString("UPDATE_USER_ID") != null ){
			sb.append( rs.getString("UPDATE_USER_ID").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■バッチ更新年月日
		if( rs.getString("BATCH_UPDATE_TS") != null ){
			sb.append( rs.getString("BATCH_UPDATE_TS").trim() );
		}
		sb.append( SPLIT_CODE );

		// ■バッチ更新者ID
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
