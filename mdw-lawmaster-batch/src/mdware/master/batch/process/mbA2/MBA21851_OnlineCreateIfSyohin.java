package mdware.master.batch.process.mbA2;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:MBA21851_ONLINE IF商品マスタ作成</p>
 * <p>著作権: Copyright (c) 2014</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 3.0 (2014/03/12) M.Ayukawa ランドローム様対応 [シス0103] オンライン特売チェック
 */
 
public class MBA21851_OnlineCreateIfSyohin{

	private MasterDataBase db = null;

	// ログ用フィールド
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private BatchLog batchLog = BatchLog.getInstance();

	// 処理当日から基準日までの間隔
	private static final int SYORI_SPAN = 1;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBA21851_OnlineCreateIfSyohin( MasterDataBase db ) {

		this.db = db;
		if ( db == null ) {
			this.db = new MasterDataBase( "rbsite_ora" );
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBA21851_OnlineCreateIfSyohin() {

		this( new MasterDataBase( "rbsite_ora" ) );
	}


	/**
	 * 本処理
	 * @throws Exception 例外
	 */
	public void execute() throws Exception {
		String jobId = userLog.getJobId();
		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
			
			// タイムスタンプ取得
			String timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( "rbsite_ora" );
			
			// バッチ日付
			String batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

			// IFスキーマ名
			String ifSchema = null;
			try{
				ifSchema = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.IF_SCHEMA).trim();
			}catch(Exception ex){
				writeLog(Level.ERROR_INT, "内部IFスキーマ名が設定されていないため、R商品マスタはIFされません。");
				return;
			}

			// テーブルクリア
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを開始します。");
			DBUtil.truncateTable(db, ifSchema, "IF_R_SYOHIN_ONLINE");
			writeLog(Level.INFO_INT, "IFテーブルのデータクリアを終了します。");

			// IFテーブルへの登録処理
			writeLog(Level.INFO_INT, "IFテーブルのデータ登録を開始します。");
			int insNum = db.executeUpdate(getInsertSql(timeStamp, batchDt));
			writeLog(Level.INFO_INT, "IFテーブルに " + insNum + "件登録しました。");

			// コミット
			db.commit();

		// 例外処理
		} catch ( Exception e ) {

			// ロールバック
			db.rollback();

			// ログ出力
			writeError(e);

			throw e;
		}finally{
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/*********************************************/
	
	/**
	 * IFテーブルInsert用SQL
	 * @param timeStamp 登録年月日
	 * @param batchDate バッチ日付
	 */
	public String getInsertSql(String timeStamp,  String batchDate) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		// SQL作成
		sb.append("INSERT /*+ APPEND */ INTO IF_R_SYOHIN_ONLINE NOLOGGING (");
		sb.append("	BUNRUI1_CD, ");
		sb.append("	SYOHIN_CD, ");
		sb.append("	YUKO_DT, ");
		sb.append("	SYSTEM_KB, ");
		sb.append("	GYOSYU_KB, ");
		sb.append("	SYOHIN_KB, ");
		sb.append("	KETASU_KB, ");
		sb.append("	BUNRUI2_CD, ");
		sb.append("	BUNRUI5_CD, ");
		sb.append("	HINMOKU_CD, ");
		sb.append("	SYOHIN_2_CD, ");
		sb.append("	ZAIKO_SYOHIN_CD, ");
		sb.append("	JYOHO_SYOHIN_CD, ");
		sb.append("	MAKER_CD, ");
		sb.append("	HINMEI_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_NA, ");
		sb.append("	KIKAKU_KANJI_2_NA, ");
		sb.append("	REC_HINMEI_KANJI_NA, ");
		sb.append("	HINMEI_KANA_NA, ");
		sb.append("	KIKAKU_KANA_NA, ");
		sb.append("	KIKAKU_KANA_2_NA, ");
		sb.append("	REC_HINMEI_KANA_NA, ");
		sb.append("	SYOHIN_WIDTH_QT, ");
		sb.append("	SYOHIN_HEIGHT_QT, ");
		sb.append("	SYOHIN_DEPTH_QT, ");
		sb.append("	E_SHOP_KB, ");
		sb.append("	PB_KB, ");
		sb.append("	SUBCLASS_CD, ");
		sb.append("	HAIFU_CD, ");
		sb.append("	ZEI_KB, ");
		sb.append("	TAX_RATE_KB, ");
		sb.append("	GENTANKA_VL, ");
		sb.append("	BAITANKA_VL, ");
		sb.append("	TOSYO_BAIKA_VL, ");
		sb.append("	PRE_GENTANKA_VL, ");
		sb.append("	PRE_BAITANKA_VL, ");
		sb.append("	TOKUBETU_GENKA_VL, ");
		sb.append("	MAKER_KIBO_KAKAKU_VL, ");
		sb.append("	SIIRESAKI_CD, ");
		sb.append("	DAIHYO_HAISO_CD, ");
		sb.append("	TEN_SIIRESAKI_KANRI_CD, ");
		sb.append("	SIIRE_HINBAN_CD, ");
		sb.append("	HACYU_SYOHIN_KB, ");
		sb.append("	HACYU_SYOHIN_CD, ");
		sb.append("	EOS_KB, ");
		sb.append("	HACHU_TANI_NA, ");
		sb.append("	HANBAI_TANI, ");
		sb.append("	HACHUTANI_IRISU_QT, ");
		sb.append("	MAX_HACHUTANI_QT, ");
		sb.append("	CASE_HACHU_KB, ");
		sb.append("	BARA_IRISU_QT, ");
		sb.append("	TEN_HACHU_ST_DT, ");
		sb.append("	TEN_HACHU_ED_DT, ");
		sb.append("	HANBAI_ST_DT, ");
		sb.append("	HANBAI_ED_DT, ");
		sb.append("	HANBAI_KIKAN_KB, ");
		sb.append("	TEIKAN_KB, ");
		sb.append("	SOBA_SYOHIN_KB, ");
		sb.append("	NOHIN_KIGEN_KB, ");
		sb.append("	NOHIN_KIGEN_QT, ");
		sb.append("	BIN_1_KB, ");
		sb.append("	HACHU_PATTERN_1_KB, ");
		sb.append("	SIME_TIME_1_QT, ");
		sb.append("	C_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_RTIME_1_KB, ");
		sb.append("	TEN_NOHIN_TIME_1_KB, ");
		sb.append("	BIN_2_KB, ");
		sb.append("	HACHU_PATTERN_2_KB, ");
		sb.append("	SIME_TIME_2_QT, ");
		sb.append("	C_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_RTIME_2_KB, ");
		sb.append("	TEN_NOHIN_TIME_2_KB, ");
		sb.append("	BIN_3_KB, ");
		sb.append("	HACHU_PATTERN_3_KB, ");
		sb.append("	SIME_TIME_3_QT, ");
		sb.append("	C_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_RTIME_3_KB, ");
		sb.append("	TEN_NOHIN_TIME_3_KB, ");
		sb.append("	C_NOHIN_RTIME_KB, ");
		sb.append("	YUSEN_BIN_KB, ");
		sb.append("	F_BIN_KB, ");
		sb.append("	BUTURYU_KB, ");
		sb.append("	GOT_MUJYOKEN_FG, ");
		sb.append("	GOT_START_MM, ");
		sb.append("	GOT_END_MM, ");
		sb.append("	HACHU_TEISI_KB, ");
		sb.append("	CENTER_ZAIKO_KB, ");
		sb.append("	NOHIN_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_CD, ");
		sb.append("	NYUKA_SYOHIN_2_CD, ");
		sb.append("	ITF_CD, ");
		sb.append("	GTIN_CD, ");
		sb.append("	VENDER_MAKER_CD, ");
		sb.append("	ZAIKO_CENTER_CD, ");
		sb.append("	ZAIKO_HACHU_SAKI, ");
		sb.append("	CENTER_WEIGHT_QT, ");
		sb.append("	PACK_WIDTH_QT, ");
		sb.append("	PACK_HEIGTH_QT, ");
		sb.append("	PACK_DEPTH_QT, ");
		sb.append("	PACK_WEIGHT_QT, ");
		sb.append("	CENTER_HACHUTANI_KB, ");
		sb.append("	CENTER_HACHUTANI_QT, ");
		sb.append("	CENTER_BARA_IRISU_QT, ");
		sb.append("	CENTER_IRISU_QT, ");
		sb.append("	CASE_IRISU_QT, ");
		sb.append("	CENTER_IRISU_2_QT, ");
		sb.append("	MIN_ZAIKOSU_QT, ");
		sb.append("	MAX_ZAIKOSU_QT, ");
		sb.append("	KIJUN_ZAIKOSU_QT, ");
		sb.append("	MIN_ZAIKONISSU_QT, ");
		sb.append("	MAX_ZAIKONISSU_QT, ");
		sb.append("	CENTER_KYOYO_KB, ");
		sb.append("	CENTER_KYOYO_DT, ");
		sb.append("	CENTER_SYOMI_KIKAN_KB, ");
		sb.append("	CENTER_SYOMI_KIKAN_DT, ");
		sb.append("	TEN_GROUPNO_CD, ");
		sb.append("	TC_JYOUHO_NA, ");
		sb.append("	NOHIN_ONDO_KB, ");
		sb.append("	YOKOMOTI_KB, ");
		sb.append("	SHINAZOROE_KB, ");
		sb.append("	HONBU_ZAI_KB, ");
		sb.append("	TEN_ZAIKO_KB, ");
		sb.append("	HANBAI_SEISAKU_KB, ");
		sb.append("	HENPIN_NB, ");
		sb.append("	HENPIN_GENKA_VL, ");
		sb.append("	CGC_HENPIN_KB, ");
		sb.append("	HANBAI_LIMIT_KB, ");
		sb.append("	HANBAI_LIMIT_QT, ");
		sb.append("	PLU_SEND_DT, ");
		sb.append("	KEYPLU_FG, ");
		sb.append("	PLU_KB, ");
		sb.append("	SYUZEI_HOKOKU_KB, ");
		sb.append("	SAKE_NAIYORYO_QT, ");
		sb.append("	TAG_HYOJI_BAIKA_VL, ");
		sb.append("	KESHI_BAIKA_VL, ");
		sb.append("	YORIDORI_KB, ");
		sb.append("	YORIDORI_VL, ");
		sb.append("	YORIDORI_QT, ");
		sb.append("	BLAND_CD, ");
		sb.append("	SEASON_CD, ");
		sb.append("	HUKUSYU_CD, ");
		sb.append("	STYLE_CD, ");
		sb.append("	SCENE_CD, ");
		sb.append("	SEX_CD, ");
		sb.append("	AGE_CD, ");
		sb.append("	GENERATION_CD, ");
		sb.append("	SOZAI_CD, ");
		sb.append("	PATTERN_CD, ");
		sb.append("	ORIAMI_CD, ");
		sb.append("	HUKA_KINO_CD, ");
		sb.append("	SODE_CD, ");
		sb.append("	SIZE_CD, ");
		sb.append("	COLOR_CD, ");
		sb.append("	KEIYAKU_SU_QT, ");
		sb.append("	KEIYAKU_PATTERN_KB, ");
		sb.append("	KEIYAKU_ST_DT, ");
		sb.append("	KEIYAKU_ED_DT, ");
		sb.append("	KUMISU_KB, ");
		sb.append("	NEFUDA_KB, ");
		sb.append("	NEFUDA_UKEWATASI_DT, ");
		sb.append("	NEFUDA_UKEWATASI_KB, ");
		sb.append("	PC_KB, ");
		sb.append("	DAISI_NO_NB, ");
		sb.append("	UNIT_PRICE_TANI_KB, ");
		sb.append("	UNIT_PRICE_NAIYORYO_QT, ");
		sb.append("	UNIT_PRICE_KIJUN_TANI_QT, ");
		sb.append("	SYOHI_KIGEN_KB, ");
		sb.append("	SYOHI_KIGEN_DT, ");
		sb.append("	DAICHO_TENPO_KB, ");
		sb.append("	DAICHO_HONBU_KB, ");
		sb.append("	DAICHO_SIIRESAKI_KB, ");
		sb.append("	TANA_NO_NB, ");
		sb.append("	DAN_NO_NB, ");
		sb.append("	REBATE_FG, ");
		sb.append("	MARK_GROUP_CD, ");
		sb.append("	MARK_CD, ");
		sb.append("	YUNYUHIN_KB, ");
		sb.append("	SANTI_CD, ");
		sb.append("	D_ZOKUSEI_1_NA, ");
		sb.append("	S_ZOKUSEI_1_NA, ");
		sb.append("	D_ZOKUSEI_2_NA, ");
		sb.append("	S_ZOKUSEI_2_NA, ");
		sb.append("	D_ZOKUSEI_3_NA, ");
		sb.append("	S_ZOKUSEI_3_NA, ");
		sb.append("	D_ZOKUSEI_4_NA, ");
		sb.append("	S_ZOKUSEI_4_NA, ");
		sb.append("	D_ZOKUSEI_5_NA, ");
		sb.append("	S_ZOKUSEI_5_NA, ");
		sb.append("	D_ZOKUSEI_6_NA, ");
		sb.append("	S_ZOKUSEI_6_NA, ");
		sb.append("	D_ZOKUSEI_7_NA, ");
		sb.append("	S_ZOKUSEI_7_NA, ");
		sb.append("	D_ZOKUSEI_8_NA, ");
		sb.append("	S_ZOKUSEI_8_NA, ");
		sb.append("	D_ZOKUSEI_9_NA, ");
		sb.append("	S_ZOKUSEI_9_NA, ");
		sb.append("	D_ZOKUSEI_10_NA, ");
		sb.append("	S_ZOKUSEI_10_NA, ");
		sb.append("	FUJI_SYOHIN_KB, ");
		sb.append("	COMMENT_TX, ");
		sb.append("	AUTO_DEL_KB, ");
		sb.append("	MST_SIYOFUKA_KB, ");
		sb.append("	HAIBAN_FG, ");
		sb.append("	SINKI_TOROKU_DT, ");
		sb.append("	HENKO_DT, ");
		sb.append("	SYOKAI_TOROKU_TS, ");
		sb.append("	SYOKAI_USER_ID, ");
		sb.append("	INSERT_TS, ");
		sb.append("	INSERT_USER_ID, ");
		sb.append("	UPDATE_TS, ");
		sb.append("	UPDATE_USER_ID, ");
		sb.append("	BATCH_UPDATE_TS, ");
		sb.append("	BATCH_UPDATE_ID, ");
		sb.append("	DELETE_FG, ");
		sb.append("	IF_INSERT_TS ");
		sb.append(")");

		sb.append("	SELECT ");
		sb.append("	TRIM(BUNRUI1_CD), ");
		sb.append("	TRIM(SYOHIN_CD), ");
		sb.append("	TRIM(YUKO_DT), ");
		sb.append("	TRIM(SYSTEM_KB), ");
		sb.append("	TRIM(GYOSYU_KB), ");
		sb.append("	TRIM(SYOHIN_KB), ");
		sb.append("	TRIM(KETASU_KB), ");
		sb.append("	TRIM(BUNRUI2_CD), ");
		sb.append("	TRIM(BUNRUI5_CD), ");
		sb.append("	TRIM(HINMOKU_CD), ");
		sb.append("	TRIM(SYOHIN_2_CD), ");
		sb.append("	TRIM(ZAIKO_SYOHIN_CD), ");
		sb.append("	TRIM(JYOHO_SYOHIN_CD), ");
		sb.append("	TRIM(MAKER_CD), ");
		sb.append("	TRIM(HINMEI_KANJI_NA), ");
		sb.append("	TRIM(KIKAKU_KANJI_NA), ");
		sb.append("	TRIM(KIKAKU_KANJI_2_NA), ");
		sb.append("	TRIM(REC_HINMEI_KANJI_NA), ");
		sb.append("	TRIM(HINMEI_KANA_NA), ");
		sb.append("	TRIM(KIKAKU_KANA_NA), ");
		sb.append("	TRIM(KIKAKU_KANA_2_NA), ");
		sb.append("	TRIM(REC_HINMEI_KANA_NA), ");
		sb.append("	SYOHIN_WIDTH_QT, ");
		sb.append("	SYOHIN_HEIGHT_QT, ");
		sb.append("	SYOHIN_DEPTH_QT, ");
		sb.append("	TRIM(E_SHOP_KB), ");
		sb.append("	TRIM(PB_KB), ");
		sb.append("	TRIM(SUBCLASS_CD), ");
		sb.append("	TRIM(HAIFU_CD), ");
		sb.append("	TRIM(ZEI_KB), ");
		sb.append("	TRIM(TAX_RATE_KB), ");
		sb.append("	GENTANKA_VL, ");
		sb.append("	BAITANKA_VL, ");
		sb.append("	TOSYO_BAIKA_VL, ");
		sb.append("	PRE_GENTANKA_VL, ");
		sb.append("	PRE_BAITANKA_VL, ");
		sb.append("	TOKUBETU_GENKA_VL, ");
		sb.append("	MAKER_KIBO_KAKAKU_VL, ");
		sb.append("	TRIM(SIIRESAKI_CD), ");
		sb.append("	TRIM(DAIHYO_HAISO_CD), ");
		sb.append("	TRIM(TEN_SIIRESAKI_KANRI_CD), ");
		sb.append("	TRIM(SIIRE_HINBAN_CD), ");
		sb.append("	TRIM(HACYU_SYOHIN_KB), ");
		sb.append("	TRIM(HACYU_SYOHIN_CD), ");
		sb.append("	TRIM(EOS_KB), ");
		sb.append("	TRIM(HACHU_TANI_NA), ");
		sb.append("	TRIM(HANBAI_TANI), ");
		sb.append("	HACHUTANI_IRISU_QT, ");
		sb.append("	MAX_HACHUTANI_QT, ");
		sb.append("	TRIM(CASE_HACHU_KB), ");
		sb.append("	BARA_IRISU_QT, ");
		sb.append("	TRIM(TEN_HACHU_ST_DT), ");
		sb.append("	TRIM(TEN_HACHU_ED_DT), ");
		sb.append("	TRIM(HANBAI_ST_DT), ");
		sb.append("	TRIM(HANBAI_ED_DT), ");
		sb.append("	TRIM(HANBAI_KIKAN_KB), ");
		sb.append("	TRIM(TEIKAN_KB), ");
		sb.append("	TRIM(SOBA_SYOHIN_KB), ");
		sb.append("	TRIM(NOHIN_KIGEN_KB), ");
		sb.append("	NOHIN_KIGEN_QT, ");
		sb.append("	TRIM(BIN_1_KB), ");
		sb.append("	TRIM(HACHU_PATTERN_1_KB), ");
		sb.append("	SIME_TIME_1_QT, ");
		sb.append("	TRIM(C_NOHIN_RTIME_1_KB), ");
		sb.append("	TRIM(TEN_NOHIN_RTIME_1_KB), ");
		sb.append("	TRIM(TEN_NOHIN_TIME_1_KB), ");
		sb.append("	TRIM(BIN_2_KB), ");
		sb.append("	TRIM(HACHU_PATTERN_2_KB), ");
		sb.append("	SIME_TIME_2_QT, ");
		sb.append("	TRIM(C_NOHIN_RTIME_2_KB), ");
		sb.append("	TRIM(TEN_NOHIN_RTIME_2_KB), ");
		sb.append("	TRIM(TEN_NOHIN_TIME_2_KB), ");
		sb.append("	TRIM(BIN_3_KB), ");
		sb.append("	TRIM(HACHU_PATTERN_3_KB), ");
		sb.append("	SIME_TIME_3_QT, ");
		sb.append("	TRIM(C_NOHIN_RTIME_3_KB), ");
		sb.append("	TRIM(TEN_NOHIN_RTIME_3_KB), ");
		sb.append("	TRIM(TEN_NOHIN_TIME_3_KB), ");
		sb.append("	TRIM(C_NOHIN_RTIME_KB), ");
		sb.append("	TRIM(YUSEN_BIN_KB), ");
		sb.append("	TRIM(F_BIN_KB), ");
		sb.append("	TRIM(BUTURYU_KB), ");
		sb.append("	TRIM(GOT_MUJYOKEN_FG), ");
		sb.append("	TRIM(GOT_START_MM), ");
		sb.append("	TRIM(GOT_END_MM), ");
		sb.append("	TRIM(HACHU_TEISI_KB), ");
		sb.append("	TRIM(CENTER_ZAIKO_KB), ");
		sb.append("	TRIM(NOHIN_SYOHIN_CD), ");
		sb.append("	TRIM(NYUKA_SYOHIN_CD), ");
		sb.append("	TRIM(NYUKA_SYOHIN_2_CD), ");
		sb.append("	TRIM(ITF_CD), ");
		sb.append("	TRIM(GTIN_CD), ");
		sb.append("	TRIM(VENDER_MAKER_CD), ");
		sb.append("	TRIM(ZAIKO_CENTER_CD), ");
		sb.append("	TRIM(ZAIKO_HACHU_SAKI), ");
		sb.append("	CENTER_WEIGHT_QT, ");
		sb.append("	PACK_WIDTH_QT, ");
		sb.append("	PACK_HEIGTH_QT, ");
		sb.append("	PACK_DEPTH_QT, ");
		sb.append("	PACK_WEIGHT_QT, ");
		sb.append("	TRIM(CENTER_HACHUTANI_KB), ");
		sb.append("	CENTER_HACHUTANI_QT, ");
		sb.append("	CENTER_BARA_IRISU_QT, ");
		sb.append("	CENTER_IRISU_QT, ");
		sb.append("	CASE_IRISU_QT, ");
		sb.append("	CENTER_IRISU_2_QT, ");
		sb.append("	MIN_ZAIKOSU_QT, ");
		sb.append("	MAX_ZAIKOSU_QT, ");
		sb.append("	KIJUN_ZAIKOSU_QT, ");
		sb.append("	MIN_ZAIKONISSU_QT, ");
		sb.append("	MAX_ZAIKONISSU_QT, ");
		sb.append("	TRIM(CENTER_KYOYO_KB), ");
		sb.append("	CENTER_KYOYO_DT, ");
//		sb.append("	TRIM(CENTER_SYOMI_KIKAN_KB), ");
//		sb.append("	CENTER_SYOMI_KIKAN_DT, ");
		sb.append("	NULL AS CENTER_SYOMI_KIKAN_KB, ");
		sb.append("	NULL AS CENTER_SYOMI_KIKAN_DT, ");
		sb.append("	TRIM(TEN_GROUPNO_CD), ");
		sb.append("	TRIM(TC_JYOUHO_NA), ");
		sb.append("	TRIM(NOHIN_ONDO_KB), ");
		sb.append("	TRIM(YOKOMOTI_KB), ");
		sb.append("	TRIM(SHINAZOROE_KB), ");
		sb.append("	TRIM(HONBU_ZAI_KB), ");
		sb.append("	TRIM(TEN_ZAIKO_KB), ");
		sb.append("	TRIM(HANBAI_SEISAKU_KB), ");
		sb.append("	TRIM(HENPIN_NB), ");
		sb.append("	HENPIN_GENKA_VL, ");
		sb.append("	TRIM(CGC_HENPIN_KB), ");
		sb.append("	TRIM(HANBAI_LIMIT_KB), ");
		sb.append("	HANBAI_LIMIT_QT, ");
		sb.append("	TRIM(PLU_SEND_DT), ");
		sb.append("	TRIM(KEYPLU_FG), ");
		sb.append("	TRIM(PLU_KB), ");
		sb.append("	TRIM(SYUZEI_HOKOKU_KB), ");
		sb.append("	SAKE_NAIYORYO_QT, ");
		sb.append("	TAG_HYOJI_BAIKA_VL, ");
		sb.append("	KESHI_BAIKA_VL, ");
		sb.append("	TRIM(YORIDORI_KB), ");
		sb.append("	YORIDORI_VL, ");
		sb.append("	YORIDORI_QT, ");
		sb.append("	TRIM(BLAND_CD), ");
		sb.append("	TRIM(SEASON_CD), ");
		sb.append("	TRIM(HUKUSYU_CD), ");
		sb.append("	TRIM(STYLE_CD), ");
		sb.append("	TRIM(SCENE_CD), ");
		sb.append("	TRIM(SEX_CD), ");
		sb.append("	TRIM(AGE_CD), ");
		sb.append("	TRIM(GENERATION_CD), ");
		sb.append("	TRIM(SOZAI_CD), ");
		sb.append("	TRIM(PATTERN_CD), ");
		sb.append("	TRIM(ORIAMI_CD), ");
		sb.append("	TRIM(HUKA_KINO_CD), ");
		sb.append("	TRIM(SODE_CD), ");
		sb.append("	TRIM(SIZE_CD), ");
		sb.append("	TRIM(COLOR_CD), ");
		sb.append("	KEIYAKU_SU_QT, ");
		sb.append("	TRIM(KEIYAKU_PATTERN_KB), ");
		sb.append("	TRIM(KEIYAKU_ST_DT), ");
		sb.append("	TRIM(KEIYAKU_ED_DT), ");
		sb.append("	TRIM(KUMISU_KB), ");
		sb.append("	TRIM(NEFUDA_KB), ");
		sb.append("	TRIM(NEFUDA_UKEWATASI_DT), ");
		sb.append("	TRIM(NEFUDA_UKEWATASI_KB), ");
		sb.append("	TRIM(PC_KB), ");
		sb.append("	TRIM(DAISI_NO_NB), ");
		sb.append("	TRIM(UNIT_PRICE_TANI_KB), ");
		sb.append("	UNIT_PRICE_NAIYORYO_QT, ");
		sb.append("	UNIT_PRICE_KIJUN_TANI_QT, ");
		sb.append("	TRIM(SYOHI_KIGEN_KB), ");
		sb.append("	SYOHI_KIGEN_DT, ");
		sb.append("	TRIM(DAICHO_TENPO_KB), ");
		sb.append("	TRIM(DAICHO_HONBU_KB), ");
		sb.append("	TRIM(DAICHO_SIIRESAKI_KB), ");
		sb.append("	TANA_NO_NB, ");
		sb.append("	DAN_NO_NB, ");
		sb.append("	TRIM(REBATE_FG), ");
		sb.append("	TRIM(MARK_GROUP_CD), ");
		sb.append("	TRIM(MARK_CD), ");
		sb.append("	TRIM(YUNYUHIN_KB), ");
		sb.append("	TRIM(SANTI_CD), ");
		sb.append("	TRIM(D_ZOKUSEI_1_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_1_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_2_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_2_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_3_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_3_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_4_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_4_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_5_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_5_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_6_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_6_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_7_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_7_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_8_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_8_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_9_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_9_NA), ");
		sb.append("	TRIM(D_ZOKUSEI_10_NA), ");
		sb.append("	TRIM(S_ZOKUSEI_10_NA), ");
		sb.append("	TRIM(FUJI_SYOHIN_KB), ");
		sb.append("	TRIM(COMMENT_TX), ");
		sb.append("	TRIM(AUTO_DEL_KB), ");
		sb.append("	TRIM(MST_SIYOFUKA_KB), ");
		sb.append("	TRIM(HAIBAN_FG), ");
		sb.append("	TRIM(SINKI_TOROKU_DT), ");
		sb.append("	TRIM(HENKO_DT), ");
		sb.append("	TRIM(SYOKAI_TOROKU_TS), ");
		sb.append("	TRIM(SYOKAI_USER_ID), ");
		sb.append("	TRIM(INSERT_TS), ");
		sb.append("	TRIM(INSERT_USER_ID), ");
		sb.append("	TRIM(UPDATE_TS), ");
		sb.append("	TRIM(UPDATE_USER_ID), ");
		sb.append("	TRIM(BATCH_UPDATE_TS), ");
		sb.append("	TRIM(BATCH_UPDATE_ID), ");
		sb.append("	TRIM(DELETE_FG), ");
		sb.append("	'").append(timeStamp).append("' ");
		sb.append("FROM R_SYOHIN TB1 ");
		sb.append("	WHERE (");
		sb.append(" YUKO_DT = ");
		sb.append("	( SELECT ");
		sb.append("		MAX( YUKO_DT ) ");
		sb.append("	FROM ");
		sb.append("		R_SYOHIN ");
		sb.append("	WHERE ");
		sb.append("		YUKO_DT <= '").append( DateChanger.addDate(batchDate, SYORI_SPAN) ).append("' ");
		sb.append("		AND SYOHIN_CD = TB1.SYOHIN_CD ");
		// #6620 DEL 2022.06.29 SIEU.D (S)
		// sb.append("		AND BUNRUI1_CD = TB1.BUNRUI1_CD ");
		// #6620 DEL 2022.06.29 SIEU.D (E)
		sb.append("	) OR ( ");
		sb.append("		TB1.YUKO_DT > '").append( DateChanger.addDate(batchDate, SYORI_SPAN) ).append("' ");
		sb.append("	) ");
		sb.append(") ");
                   
		return sb.toString();
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
