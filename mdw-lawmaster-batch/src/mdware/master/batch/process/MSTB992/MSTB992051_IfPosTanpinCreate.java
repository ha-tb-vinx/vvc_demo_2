package mdware.master.batch.process.MSTB992;

import java.nio.CharBuffer;
import java.sql.SQLException;
import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000102_IfConstDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
*
* <p>タイトル: MSTB992051_IfPosTanpinCreate.java クラス</p>
* <p>説明　: WK_PLU店別商品の内容を、IF_PLU単品メンテに取込む</p>
* <p>著作権: Copyright (c) 2015</p>
* <p>会社名: VINX</p>
* @version 1.00 2015/08/05 TAM.NM FIVImart様対応
* @version 1.01 2015/08/07 DAI.BQ FIVImart様対応
* @version 1.02 2016.04.04 Huy.NT 設計書No.651(#1211) FIVIMART対応
* @version 1.03 2016.10.18 nv.cuong #2238 FIVIMART対応
* @version 1.04 2016.11.04 H.Sakamoto #1750対応
* @version 1.05 (2016.11.25) S.Talayama #2839
* @Version 1.06 2016.12.08 Li.Sheng #3049 FIVImart対応
* @Version 1.07 2016.12.16 S.Takayama #3234 FIVImart対応
* @Version 1.08 2017.01.11 M.Kanno #3587 FIVIMART対応
* @version 1.09 2017.02.09 #3765対応 T.Han FIVImart対応
* @version 1.10 2017.02.17 #3686対応 X.Liu FIVImart対応
* @version 1.11 2017.05.19 #5039対応 S.Takayama 
* @version 1.12 2020.07.10 KHAI.NN #6167 MKV対応
* @version 1.13 2020.09.21 KHAI.NN #6227 MKV対応
* @version 1.14 2020.09.30 KHAI.NN #6238 MKV対応
* @Version 1.15 2024.01.16 DUY.HM #15277 MKH対応
*/

public class MSTB992051_IfPosTanpinCreate {
	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	// バッチ日付
	private String batchDt = null;
	private String yokuBatchDt = null;
	/** システム時刻 */
	private String timeStamp = "";
	/** 作成日 */
	private String sakuseiDt = "";

	// テーブルTOROKU_ID_A
	private static final String TABLE_IF_PLU_TANPIN = "IF_PLU_TANPIN"; // IF_PLU単品メンテ
	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;
	/** 送信回数 */
	private static final String SEND_QT = "01";
	/** 新規・訂正 登録ID */
	private static final String TOROKU_ID_A = "A";

	private static final String TOROKU_ID_D = "D";
	/** 商品バーコード*/
	private static final String SYOHIN_BAR_CD = "000000000000000000";
	/** 会員売価単価*/
	private static final String KAIIN_BAITANKA_VL = "00000000000000000";
	/** PLUフラグ*/
	private static final String PLU_FG = "2";
	/** 税区分（VAT）*/
	private static final String ZEI_KB = "0";
	/** 販売税率*/
	private static final String HANBAI_ZEI_RT = "000";
	/** 学生割引カード（KADS1M）フラグ*/
	private static final String STUDENT_WARIBIKI_CARD_FG = "0";
	/** パディング文字 */
	private static final String PADDING_STR = "0";
	/** 店舗コード 桁数 */
	private static final String TENPO_CD_LENGTH = "6";
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (S)
	/** 正式名称（漢字）開始カラム */
	/*private static final String KANJI_NA_START_COLUMN = "1";*/
	/** 正式名称（漢字）桁数 ライン */
	/*private static final String KANJI_NA_LENGTH = "20";*/
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (E)
	private static final int LENGTH_SYOHIN_RN  = 15;
	private static final int LENGTH_SYOHIN_NA = 30;
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (S)
	/*private static final int LENGTH_SYOHIN_NA_CHN = 40;*/
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (E)
	private static final int LENGTH_HANBAI_TN = 5;
	private static final int LENGTH_DIVISION_CD = 3;
	private static final int LENGTH_DEPARTMENT_CD = 3;
	private static final int LENGTH_CREATE_TS = 8;
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (S)
	/*private static final int LENGTH_INJI_HANBAI_TN = 8;*/
	// No.651 MSTB992 Del 2016.04.04 Huy.NT (E)
	private static final int LENGTH_INJI_SEIZOU_DT = 3;
	private static final int LENGTH_ZEI_CD = 5;
	// No.651 MSTB992 Add 2016.04.04 Huy.NT (S)
	/** 仕入先コードがNULLの場合に設定する値 */
	private static final String SIIRESAKI_CD_NULL = "000000000";
	// No.651 MSTB992 Add 2016.04.04 Huy.NT (E)
	//2016/11/04 VINX h.sakamoto #1750対応 (S)
	/** 特殊日付 */
	private static final String TOKUSYU_DT = "99999999";
	//2016/11/04 VINX h.sakamoto #1750対応 (E)
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB992051_IfPosTanpinCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB992051_IfPosTanpinCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception{
		try {
			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// バッチ日付
			batchDt = RMSTDATEUtil.getBatDateDt();
			writeLog(Level.INFO_INT, "バッチ日付： " + batchDt);
			yokuBatchDt = DateChanger.addDate(batchDt, 1);
			// #2238 add 2016.10.18 nv.cuong(S)
			//2016/11/04 VINX h.sakamoto #1750対応 (S)
//			batchDt = DateChanger.addDate(batchDt, 1);
			//2016/11/04 VINX h.sakamoto #1750対応 (E)
			// #2238 add 2016.10.18 nv.cuong(E)
			
			// 作成日取得
			timeStamp = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
			sakuseiDt = timeStamp.substring(0, 4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
			writeLog(Level.INFO_INT, "作成日： " + sakuseiDt);

			// IF_PLU単品メンテTRUNCATE
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_PLU_TANPIN);
			writeLog(Level.INFO_INT, "IF_PLU単品メンテを削除処理を終了します。");

			// IF_PLU単品メンテ(新規・訂正)の登録
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を開始します。");
			iRec = db.executeUpdate(getIfPluTanpinInsertSql());
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(新規・訂正)登録処理（WK→IF）を終了します。");

			db.commit();
			// IF_PLU単品メンテ(削除)の登録
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を開始します。");
			iRec = db.executeUpdate(getIfPluTanpinDelInsertSql());
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)を" + iRec + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)登録処理（SEND→IF）を終了します。");

			db.commit();

// #5039 Del 2017.05.19 S.Takayam (S)
//			//2016/11/04 VINX h.sakamoto #1750対応 (S)
//			// IF_PLU単品メンテ（削除）登録（店取扱停止の場合）
//			writeLog(Level.INFO_INT, "IF_PLU単品メンテ（削除）登録処理（店取扱停止の場合）を開始します。");
//			iRec = db.executeUpdate(getIfPluTanpinDelTeishiInsertSql());
//			writeLog(Level.INFO_INT, "IF_PLU単品メンテ(削除)（店取扱停止の場合）を" + iRec + "件作成しました。");
//			writeLog(Level.INFO_INT, "IF_PLU単品メンテ（削除）登録処理（店取扱停止の場合）を終了します。");
//
//			db.commit();
//			//2016/11/04 VINX h.sakamoto #1750対応 (E)
// #5039 Del 2017.05.19 S.Takayam (E)

			writeLog(Level.INFO_INT, "処理を終了します。");

		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/********** ＳＱＬ生成処理 **********/

	/**
	 * If_Plu_Tanpin(新規・訂正)を登録するSQLを取得する
	 *
	 * @return If_Plu_Tanpin(新規・訂正)登録SQL
	 */
	public String getIfPluTanpinInsertSql() throws Exception{
		StringBuilder sql= new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_PLU_TANPIN");
		sql.append("	( ");
		sql.append("	EIGYO_DT");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("	,OLD_SYOHIN_CD");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("	,SYOHIN_RN ");
		sql.append("	,SYOHIN_NA ");
		sql.append("	,SYOHIN_RN_CHN ");
		sql.append("	,SYOHIN_NA_CHN ");
		sql.append("	,SYOHIN_BAR_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,KAIIN_BAITANKA_VL ");
		sql.append("	,HANBAI_TN ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,PLU_FG ");
		sql.append("	,CREATE_TS ");
		sql.append("	,ZEI_KB ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,HANBAI_ZEI_RT ");
		sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		sql.append("	,INJI_HANBAI_TN ");
		sql.append("	,INJI_SEIZOU_DT ");
		sql.append("	,ZEI_CD ");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (S)
		sql.append("	,SIIRESAKI_CD ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (E)
		// #1921 MSTB992 2016.09.08 S.Takayama (S)
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,LABEL_SEIBUN ");
		sql.append("	,LABEL_HOKAN_HOHO ");
		sql.append("	,LABEL_TUKAIKATA ");
		// #1921 MSTB992 2016.09.08 S.Takayama (E)
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("	,LABEL_COUNTRY_NA");
// #3049 Add 2016.12.09 Li.Sheng (E)	
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,INJI_HANBAI_TN_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (S)
		sql.append("	,ITEM_OFFICIAL_NA ");
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	) ");
		sql.append("SELECT ");
		//2016/11/04 VINX h.sakamoto #1750対応 (S)
//		sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
		sql.append("	 '" + yokuBatchDt + "'  AS EIGYO_DT ");
		//2016/11/04 VINX h.sakamoto #1750対応 (E)
		sql.append("	,LPAD(TRIM(WTP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                            AS TENPO_CD ");
		sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_A + "'                                                                                               AS TOROKU_ID ");
		sql.append("	,WTP.SYOHIN_CD                                                                                                       AS SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("	,WTP.OLD_SYOHIN_CD                                                                                                   AS OLD_SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
		// #6167 MSTB992 Mod 2020.07.10 KHAI.NN (S)
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
		// #6227 MSTB992 Mod 2020.09.21 KHAI.NN (S)
		//sql.append("	,WTP.HINMEI_KANJI_NA                                                                                                 AS SYOHIN_NA");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
		// #6227 MSTB992 Mod 2020.09.21 KHAI.NN (E)
		// #6167 MSTB992 Mod 2020.07.10 KHAI.NN (E)
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
		/*sql.append("	,SUBSTRB(WTP.REC_HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ")                            AS SYOHIN_RN_CHN ");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA_CHN)+ "'                                                                               AS SYOHIN_NA_CHN");*/
		sql.append("	,WTP.KEIRYOKI_NM                                                                                                     AS SYOHIN_RN_CHN ");
		sql.append("	,WTP.REC_HINMEI_KANJI_NA                                                                                             AS SYOHIN_NA_CHN");
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
		sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
		sql.append("	,WTP.BAITANKA_VL                                                                                                     AS BAITANKA_VL");
		sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
		sql.append("	,'" + spaces(LENGTH_HANBAI_TN) + "'                                                                                   AS HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_DIVISION_CD) + "'                                                                                 AS DIVISION_CD ");
		sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD) + "'                                                                               AS DEPARTMENT_CD ");
		sql.append("	,LTRIM(WTP.BUNRUI2_CD, '0')                                                                                          AS CLASS_CD ");
		sql.append("	,LTRIM(WTP.BUNRUI5_CD, '0')                                                                                           AS SUBCLASS_CD ");
		sql.append("	,WTP.TEIKAN_FG                                                                                                       AS TEIKAN_FG ");
		sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
		sql.append("	,'" + spaces(LENGTH_CREATE_TS) + "'                                                                                   AS CREATE_TS ");
		// #3234 MSTB992 2016.12.12 S.Takayama (S)
		//sql.append("	,'" + ZEI_KB + "'                                                                                                    AS ZEI_KB ");
		sql.append("	,WTP.ZEI_KB                                                                                        	            AS ZEI_KB ");
		// #3234 MSTB992 2016.12.12 S.Takayama (E)
		sql.append("	,WTP.ZEI_RT                                                                                                          AS ZEI_RT ");
		sql.append("	,WTP.SEASON_ID                                                                                                      AS SEASON_ID ");
		sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
		sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,WTP.SYOHI_KIGEN_DT                                                                                                  AS SYOHI_KIGEN_DT ");
		sql.append("	,WTP.CARD_FG                                                                                                         AS CARD_FG ");
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
		/*sql.append("	,'" + spaces(LENGTH_INJI_HANBAI_TN) + "'                                                                              AS INJI_HANBAI_TN ");*/
		sql.append("	,WTP.HANBAI_TANI                                                                                                     AS INJI_HANBAI_TN ");
		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
		sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
		sql.append("	,'" + spaces(LENGTH_ZEI_CD) + "'                                                                                      AS ZEI_CD ");
		//#862 Mod 2015.10.21 DAI.BQ (S)
		sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (S)
		sql.append("	,COALESCE(WTP.SIIRESAKI_CD, '" + SIIRESAKI_CD_NULL + "')                                                             AS SIIRESAKI_CD ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (E)
		// #1921 MSTB992 2016.09.08 S.Takayama (S)
		sql.append("	,WTP.SYOHI_KIGEN_HYOJI_PATTER                                                            AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,WTP.LABEL_SEIBUN                                                            AS LABEL_SEIBUN ");
		sql.append("	,WTP.LABEL_HOKAN_HOHO                                                            AS LABEL_HOKAN_HOHO ");
		sql.append("	,WTP.LABEL_TUKAIKATA                                                            AS LABEL_TUKAIKATA ");
		// #1921 MSTB992 2016.09.08 S.Takayama (E)
		//#862 Mod 2015.10.21 DAI.BQ (E)
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,WTP.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("	,WTP.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");		
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,WTP.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (S)
		sql.append("	,WTP.HINMEI_KANJI_NA AS ITEM_OFFICIAL_NA");
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,WTP.MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append(" FROM ");
		sql.append("	WK_TEC_PLU WTP ");
// #5039 Del 2017.05.19 S.Takayama (S)
//		sql.append("	LEFT JOIN ");
//		sql.append("		( ");
//		sql.append("		SELECT ");
//		sql.append("			 DTPS.BUNRUI1_CD ");
//		sql.append("			,DTPS.SYOHIN_CD ");
//		sql.append("			,DTPS.TENPO_CD ");
//		sql.append("		FROM ");
//		sql.append("			DT_TEC_PLU_SEND DTPS ");
//		sql.append("		) DTPS ");
//		sql.append("		ON ");
//		sql.append("			DTPS.BUNRUI1_CD	= WTP.BUNRUI1_CD	AND ");
//		sql.append("			DTPS.SYOHIN_CD	= WTP.SYOHIN_CD		AND ");
//		sql.append("			DTPS.TENPO_CD	= WTP.TENPO_CD ");
// #5039 Del 2017.05.19 S.Takayama (E)
		sql.append("WHERE ");
		sql.append("	WTP.ERR_KB	= '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'	AND ");
		//2016/11/04 VINX h.sakamoto #1750対応 (S)
		//2017/01/11 VINX M.Kanno #3587対応 (S)
		//sql.append("	WTP.BAIKA_HAISHIN_FG = '" + mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() + "'	AND ");
		sql.append("	WTP.BAIKA_HAISHIN_FG = '" + mst011701_BaikaHaishinFlagDictionary.SINAI.getCode() + "' ");
		//2016/11/04 VINX h.sakamoto #1750対応 (E)
		//#3686 Add X.Liu 2017.02.17 (S)
		sql.append("	AND WTP.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		//#3686 Add X.Liu 2017.02.17 (E)
		//sql.append("	( ");
		//sql.append("		(DTPS.SYOHIN_CD		IS NULL		AND ");
		//2016/11/04 VINX h.sakamoto #1750対応 (S)
		//sql.append("		( ");
//		sql.append("		 WTP.PLU_SEND_DT	= '" + yokuBatchDt + "')	OR ");
		//sql.append("		 	WTP.PLU_SEND_DT	= '" + yokuBatchDt + "' OR ");
		//sql.append("		 	WTP.PLU_SEND_DT	= '" + TOKUSYU_DT + "' ");
		//sql.append("		) ");
		//sql.append("	) OR ");
		//2016/11/04 VINX h.sakamoto #1750対応 (E)
		//sql.append("		DTPS.SYOHIN_CD		IS NOT NULL ");
		//sql.append("	) ");
		//2017/01/11 VINX M.Kanno #3587対応 (E)

		return sql.toString();
	}

	/**
	 * If_Plu_Tanpin(新規・訂正)を登録するSQLを取得する
	 *
	 * @return If_Plu_Tanpin(新規・訂正)登録SQL
	 */
	public String getIfPluTanpinDelInsertSql() throws Exception {
		StringBuilder sql= new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_PLU_TANPIN");
		sql.append("	( ");
		sql.append("	EIGYO_DT");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,SYOHIN_CD ");
		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
		sql.append("	,OLD_SYOHIN_CD");
		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
		sql.append("	,SYOHIN_RN ");
		sql.append("	,SYOHIN_NA ");
		sql.append("	,SYOHIN_RN_CHN ");
		sql.append("	,SYOHIN_NA_CHN ");
		sql.append("	,SYOHIN_BAR_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,KAIIN_BAITANKA_VL ");
		sql.append("	,HANBAI_TN ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,PLU_FG ");
		sql.append("	,CREATE_TS ");
		sql.append("	,ZEI_KB ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,HANBAI_ZEI_RT ");
		sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		sql.append("	,INJI_HANBAI_TN ");
		sql.append("	,INJI_SEIZOU_DT ");
		sql.append("	,ZEI_CD ");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (S)
		sql.append("	,SIIRESAKI_CD ");
		// No.651 MSTB992 Add 2016.04.04 Huy.NT (E)
		// #1921 MSTB992 2016.09.08 S.Takayama (S)
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,LABEL_SEIBUN ");
		sql.append("	,LABEL_HOKAN_HOHO ");
		sql.append("	,LABEL_TUKAIKATA ");
		// #1921 MSTB992 2016.09.08 S.Takayama (S)
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
		sql.append("	,LABEL_COUNTRY_NA");
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,INJI_HANBAI_TN_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (S)
		sql.append("	,ITEM_OFFICIAL_NA ");
		// #6238 MSTB992 Add 2020.09.30 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,MAX_BUY_QT ");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append("	) ");
// #5039 Del 2017.05.19 S.Takayama (S)
//		sql.append("SELECT ");
//		//2016/11/04 VINX h.sakamoto #1750対応 (S)
////		sql.append("	 '" + batchDt + "'  AS EIGYO_DT ");
//		sql.append("	 '" + yokuBatchDt + "'  AS EIGYO_DT ");
//		//2016/11/04 VINX h.sakamoto #1750対応 (E)
//		sql.append("	,LPAD(TRIM(DTPS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                           AS TENPO_CD ");
//		sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
//		sql.append("	,'" + TOROKU_ID_D + "'                                                                                               AS TOROKU_ID ");
//		sql.append("	,DTPS.SYOHIN_CD                                                                                                      AS SYOHIN_CD ");
//		//Add 2015.08.21 DAI.BQ オペレーションコード (S)
//		sql.append("	,DTPS.OLD_SYOHIN_CD                                                                                                  AS OLD_SYOHIN_CD ");
//		//Add 2015.08.21 DAI.BQ オペレーションコード (E)
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
//		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
//		/*sql.append("	,SUBSTRB(DTPS.REC_HINMEI_KANJI_NA, " + KANJI_NA_START_COLUMN + ", " + KANJI_NA_LENGTH + ")                           AS SYOHIN_RN_CHN ");
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA_CHN) + "'                                                                              AS SYOHIN_NA_CHN");*/
//		sql.append("	,DTPS.KEIRYOKI_NM                                                                                                    AS SYOHIN_RN_CHN ");
//		sql.append("	,DTPS.REC_HINMEI_KANJI_NA                                                                                            AS SYOHIN_NA_CHN");
//		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
//		sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
//		sql.append("	,DTPS.BAITANKA_VL                                                                                                    AS BAITANKA_VL");
//		sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
//		sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                                                                   AS HANBAI_TN ");
//		sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                                                                 AS DIVISION_CD ");
//		sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                                                               AS DEPARTMENT_CD ");
//		sql.append("	,LTRIM(DTPS.BUNRUI2_CD, '0')                                                                                         AS CLASS_CD ");
//		sql.append("	,LTRIM(DTPS.BUNRUI5_CD, '0')                                                                                          AS SUBCLASS_CD ");
//		sql.append("	,DTPS.TEIKAN_FG                                                                                                      AS TEIKAN_FG ");
//		sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
//		sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                                                                   AS CREATE_TS ");
//		// #3234 MSTB992 2016.12.12 S.Takayama (S)
//		//sql.append("	,'" + ZEI_KB + "'                                                                                                    AS ZEI_KB ");
//		sql.append("	,DTPS.ZEI_KB	                                                                                                     AS ZEI_KB ");
//		// #3234 MSTB992 2016.12.12 S.Takayama (E)
//		sql.append("	,DTPS.ZEI_RT                                                                                                         AS ZEI_RT ");
//		sql.append("	,DTPS.SEASON_ID                                                                                                     AS SEASON_ID ");
//		sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
//		sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
//		sql.append("	,DTPS.SYOHI_KIGEN_DT                                                                                                 AS SYOHI_KIGEN_DT ");
//		sql.append("	,DTPS.CARD_FG                                                                                                        AS CARD_FG ");
//		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (S)
//		/*sql.append("	,'" + spaces(LENGTH_INJI_HANBAI_TN) + "'                                                                              AS INJI_HANBAI_TN ");*/
//		sql.append("	,DTPS.HANBAI_TANI                                                                                                     AS INJI_HANBAI_TN ");
//		// No.651 MSTB992 Mod 2016.04.04 Huy.NT (E)
//		sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
//		sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                                                                      AS ZEI_CD ");
//		//#862 Mod 2015.10.21 DAI.BQ (S)
//		sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
//		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
//		sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
//		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
//		//#862 Mod 2015.10.21 DAI.BQ (E)
//		// No.651 MSTB992 Add 2016.04.04 Huy.NT (S)
//		sql.append("	,COALESCE(DTPS.SIIRESAKI_CD, '" + SIIRESAKI_CD_NULL + "')                                                            AS SIIRESAKI_CD ");
//		// No.651 MSTB992 Add 2016.04.04 Huy.NT (E)
//		// #1921 MSTB992 2016.09.08 S.Takayama (S)
//		sql.append("	,DTPS.SYOHI_KIGEN_HYOJI_PATTER                                                            AS SYOHI_KIGEN_HYOJI_PATTER ");
//		sql.append("	,DTPS.LABEL_SEIBUN                                                            AS LABEL_SEIBUN ");
//		sql.append("	,DTPS.LABEL_HOKAN_HOHO                                                            AS LABEL_HOKAN_HOHO ");
//		sql.append("	,DTPS.LABEL_TUKAIKATA                                                            AS LABEL_TUKAIKATA ");
//		// #1921 MSTB992 2016.09.08 S.Takayama (E)
//		// #2839 MSTB992 2016.11.25 S.Takayama (S)
//		sql.append("	,DTPS.GYOTAI_KB AS GYOTAI_KB");
//		// #2839 MSTB992 2016.11.25 S.Takayama (E)
//// #3049 Add 2016.12.09 Li.Sheng (S)
//		sql.append("	,DTPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
//// #3049 Add 2016.12.09 Li.Sheng (E)
//	    // 2017/02/09 T.Han #3765対応（S)
//		sql.append("	,DTPS.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
//	    // 2017/02/09 T.Han #3765対応（E)
//		sql.append(" FROM ");
//		sql.append("	DT_TEC_PLU_SEND DTPS ");
//		sql.append("	INNER JOIN ");
//		
//		//#3686 X.Liu Mod 2017.02.17 (S)
////		sql.append("		( ");
////		sql.append("		SELECT ");
////		sql.append("			 WTPS.BUNRUI1_CD ");
////		sql.append("			,WTPS.SYOHIN_CD ");
////		sql.append("		FROM ");
////		sql.append("			WK_TEC_PLU_SYOHIN WTPS ");
////		sql.append("		WHERE ");
////		sql.append("			WTPS.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
////		sql.append("		) WTPS ");
////		sql.append("		ON ");
////		sql.append("			DTPS.BUNRUI1_CD	= WTPS.BUNRUI1_CD	AND ");
////		sql.append("			DTPS.SYOHIN_CD	= WTPS.SYOHIN_CD ");
//		sql.append("		( ");
//		sql.append("		SELECT ");
//		sql.append("			 WTP.BUNRUI1_CD ");
//		sql.append("			,WTP.SYOHIN_CD ");
//		sql.append("			,WTP.TENPO_CD ");
//		sql.append("		FROM ");
//		sql.append("			WK_TEC_PLU WTP ");
//		sql.append("		WHERE ");
//		sql.append("			WTP.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_DEL + "' ");
//		sql.append("		) WTP ");
//		sql.append("		ON ");
//		sql.append("			DTPS.BUNRUI1_CD	= WTP.BUNRUI1_CD	AND ");
//		sql.append("			DTPS.SYOHIN_CD	= WTP.SYOHIN_CD     AND ");
//		sql.append("			DTPS.TENPO_CD	= WTP.TENPO_CD  ");
//		//#3686 X.Liu Mod 2017.02.17 (E)
		sql.append("SELECT ");
		sql.append("	 '" + yokuBatchDt + "'  AS EIGYO_DT ");
		sql.append("	,LPAD(TRIM(WTP.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "')                                           AS TENPO_CD ");
		sql.append("	,'" + SEND_QT +"'                                                                                                    AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "'                                                                                               AS TOROKU_ID ");
		sql.append("	,WTP.SYOHIN_CD                                                                                                      AS SYOHIN_CD ");
		sql.append("	,WTP.OLD_SYOHIN_CD                                                                                                  AS OLD_SYOHIN_CD ");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                                                                   AS SYOHIN_RN");
		// #6167 MSTB992 Mod 2020.07.10 KHAI.NN (S)
//		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
		// #6227 MSTB992 Mod 2020.09.21 KHAI.NN (S)
		//sql.append("	,WTP.HINMEI_KANJI_NA                                                                                               AS SYOHIN_NA");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                                                                   AS SYOHIN_NA");
		// #6227 MSTB992 Mod 2020.09.21 KHAI.NN (E)
		// #6167 MSTB992 Mod 2020.07.10 KHAI.NN (E)
		sql.append("	,WTP.KEIRYOKI_NM                                                                                                    AS SYOHIN_RN_CHN ");
		sql.append("	,WTP.REC_HINMEI_KANJI_NA                                                                                            AS SYOHIN_NA_CHN");
		sql.append("	,'" + SYOHIN_BAR_CD + "'                                                                                             AS SYOHIN_BAR_CD ");
		sql.append("	,WTP.BAITANKA_VL                                                                                                    AS BAITANKA_VL");
		sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                                                                         AS KAIIN_BAITANKA_VL ");
		sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                                                                   AS HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                                                                 AS DIVISION_CD ");
		sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                                                               AS DEPARTMENT_CD ");
		sql.append("	,LTRIM(WTP.BUNRUI2_CD, '0')                                                                                         AS CLASS_CD ");
		sql.append("	,LTRIM(WTP.BUNRUI5_CD, '0')                                                                                          AS SUBCLASS_CD ");
		sql.append("	,WTP.TEIKAN_FG                                                                                                      AS TEIKAN_FG ");
		sql.append("	,'" + PLU_FG + "'                                                                                                    AS PLU_FG ");
		sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                                                                   AS CREATE_TS ");
		sql.append("	,WTP.ZEI_KB	                                                                                                     AS ZEI_KB ");
		sql.append("	,WTP.ZEI_RT                                                                                                         AS ZEI_RT ");
		sql.append("	,WTP.SEASON_ID                                                                                                     AS SEASON_ID ");
		sql.append("	,'" + HANBAI_ZEI_RT + "'                                                                                             AS HANBAI_ZEI_RT ");
		sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                                                                  AS STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,WTP.SYOHI_KIGEN_DT                                                                                                 AS SYOHI_KIGEN_DT ");
		sql.append("	,WTP.CARD_FG                                                                                                        AS CARD_FG ");
		sql.append("	,WTP.HANBAI_TANI                                                                                                     AS INJI_HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                                                              AS INJI_SEIZOU_DT ");
		sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                                                                      AS ZEI_CD ");
		sql.append("	,'" + batchTs + "'                                                                                                   AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                                                                   AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                                                                        AS UPDATE_USER_ID ");
		sql.append("	,COALESCE(WTP.SIIRESAKI_CD, '" + SIIRESAKI_CD_NULL + "')                                                            AS SIIRESAKI_CD ");
		sql.append("	,WTP.SYOHI_KIGEN_HYOJI_PATTER                                                            AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,WTP.LABEL_SEIBUN                                                            AS LABEL_SEIBUN ");
		sql.append("	,WTP.LABEL_HOKAN_HOHO                                                            AS LABEL_HOKAN_HOHO ");
		sql.append("	,WTP.LABEL_TUKAIKATA                                                            AS LABEL_TUKAIKATA ");
		sql.append("	,WTP.GYOTAI_KB AS GYOTAI_KB");
		sql.append("	,WTP.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
		sql.append("	,WTP.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
		// #6238 MSTB992 Add 2020.09.21 KHAI.NN (S)
		sql.append("	,WTP.HINMEI_KANJI_NA AS ITEM_OFFICIAL_NA");
		// #6238 MSTB992 Add 2020.09.21 KHAI.NN (E)
		// #15277 ADD 2024.01.16 DUY.HM (S)
		sql.append("	,WTP.MAX_BUY_QT");
		// #15277 ADD 2024.01.16 DUY.HM (E)
		sql.append(" FROM ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append(" WHERE ");
		sql.append("	WTP.ERR_KB = '00' ");
		sql.append("	AND (  ");
		sql.append("	WTP.DELETE_FG = '1' ");
		sql.append("	OR WTP.BAIKA_HAISHIN_FG = '1' ");
		sql.append("	) ");
		return sql.toString();
	}

	//2016/11/04 VINX h.sakamoto #1750対応 (S)
	/**
	 * IF_PLU単品メンテ(削除)（店取扱停止の場合）を登録するSQLを取得する
	 *
	 * @return If_Plu_Tanpin(削除）（店取扱停止の場合）登録SQL
	 */
	public String getIfPluTanpinDelTeishiInsertSql() throws Exception {
		StringBuilder sql= new StringBuilder();
		String batchTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(CONNECTION_STR);

		sql.append("INSERT /*+ APPEND */ INTO IF_PLU_TANPIN");
		sql.append("	( ");
		sql.append("	EIGYO_DT");
		sql.append("	,TENPO_CD ");
		sql.append("	,SEND_QT ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,SYOHIN_CD ");
		sql.append("	,OLD_SYOHIN_CD");
		sql.append("	,SYOHIN_RN ");
		sql.append("	,SYOHIN_NA ");
		sql.append("	,SYOHIN_RN_CHN ");
		sql.append("	,SYOHIN_NA_CHN ");
		sql.append("	,SYOHIN_BAR_CD ");
		sql.append("	,BAITANKA_VL ");
		sql.append("	,KAIIN_BAITANKA_VL ");
		sql.append("	,HANBAI_TN ");
		sql.append("	,DIVISION_CD ");
		sql.append("	,DEPARTMENT_CD ");
		sql.append("	,CLASS_CD ");
		sql.append("	,SUBCLASS_CD ");
		sql.append("	,TEIKAN_FG ");
		sql.append("	,PLU_FG ");
		sql.append("	,CREATE_TS ");
		sql.append("	,ZEI_KB ");
		sql.append("	,ZEI_RT ");
		sql.append("	,SEASON_ID ");
		sql.append("	,HANBAI_ZEI_RT ");
		sql.append("	,STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,SYOHI_KIGEN_DT ");
		sql.append("	,CARD_FG ");
		sql.append("	,INJI_HANBAI_TN ");
		sql.append("	,INJI_SEIZOU_DT ");
		sql.append("	,ZEI_CD ");
		sql.append("	,INSERT_TS ");
		sql.append("	,INSERT_USER_ID ");
		sql.append("	,UPDATE_TS ");
		sql.append("	,UPDATE_USER_ID ");
		sql.append("	,SIIRESAKI_CD ");
		sql.append("	,SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,LABEL_SEIBUN ");
		sql.append("	,LABEL_HOKAN_HOHO ");
		sql.append("	,LABEL_TUKAIKATA ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
				sql.append("	,LABEL_COUNTRY_NA");
// #3049 Add 2016.12.09 Li.Sheng (E)		
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,INJI_HANBAI_TN_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + yokuBatchDt + "'  AS EIGYO_DT ");
		sql.append("	,LPAD(TRIM(DTPS.TENPO_CD), " + TENPO_CD_LENGTH + ", '" + PADDING_STR + "') AS TENPO_CD ");
		sql.append("	,'" + SEND_QT +"'                                                          AS SEND_QT ");
		sql.append("	,'" + TOROKU_ID_D + "'                                                     AS TOROKU_ID ");
		sql.append("	,DTPS.SYOHIN_CD                                                            AS SYOHIN_CD ");
		sql.append("	,DTPS.OLD_SYOHIN_CD                                                        AS OLD_SYOHIN_CD ");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_RN)+ "'                                         AS SYOHIN_RN");
		sql.append("	,'" + spaces(LENGTH_SYOHIN_NA)+ "'                                         AS SYOHIN_NA");
		sql.append("	,DTPS.KEIRYOKI_NM                                                          AS SYOHIN_RN_CHN ");
		sql.append("	,DTPS.REC_HINMEI_KANJI_NA                                                  AS SYOHIN_NA_CHN");
		sql.append("	,'" + SYOHIN_BAR_CD + "'                                                   AS SYOHIN_BAR_CD ");
		sql.append("	,DTPS.BAITANKA_VL                                                          AS BAITANKA_VL");
		sql.append("	,'" + KAIIN_BAITANKA_VL + "'                                               AS KAIIN_BAITANKA_VL ");
		sql.append("	,'" + spaces(LENGTH_HANBAI_TN)+ "'                                         AS HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_DIVISION_CD)+ "'                                       AS DIVISION_CD ");
		sql.append("	,'" + spaces(LENGTH_DEPARTMENT_CD)+ "'                                     AS DEPARTMENT_CD ");
		sql.append("	,LTRIM(DTPS.BUNRUI2_CD, '0')                                               AS CLASS_CD ");
		sql.append("	,LTRIM(DTPS.BUNRUI5_CD, '0')                                               AS SUBCLASS_CD ");
		sql.append("	,DTPS.TEIKAN_FG                                                            AS TEIKAN_FG ");
		sql.append("	,'" + PLU_FG + "'                                                          AS PLU_FG ");
		sql.append("	,'" + spaces(LENGTH_CREATE_TS)+ "'                                         AS CREATE_TS ");
		// #3234 MSTB992 2016.12.12 S.Takayama (S)
		//sql.append("	,'" + ZEI_KB + "'                                                          AS ZEI_KB ");
		sql.append("	,DTPS.ZEI_KB                                                               AS ZEI_KB ");
		// #3234 MSTB992 2016.12.12 S.Takayama (E)
		sql.append("	,DTPS.ZEI_RT                                                               AS ZEI_RT ");
		sql.append("	,DTPS.SEASON_ID                                                            AS SEASON_ID ");
		sql.append("	,'" + HANBAI_ZEI_RT + "'                                                   AS HANBAI_ZEI_RT ");
		sql.append("	,'" + STUDENT_WARIBIKI_CARD_FG + "'                                        AS STUDENT_WARIBIKI_CARD_FG ");
		sql.append("	,DTPS.SYOHI_KIGEN_DT                                                       AS SYOHI_KIGEN_DT ");
		sql.append("	,DTPS.CARD_FG                                                              AS CARD_FG ");
		sql.append("	,DTPS.HANBAI_TANI                                                          AS INJI_HANBAI_TN ");
		sql.append("	,'" + spaces(LENGTH_INJI_SEIZOU_DT) + "'                                   AS INJI_SEIZOU_DT ");
		sql.append("	,'" + spaces(LENGTH_ZEI_CD)+ "'                                            AS ZEI_CD ");
		sql.append("	,'" + batchTs + "'                                                         AS INSERT_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS INSERT_USER_ID ");
		sql.append("	,'" + batchTs + "'                                                         AS UPDATE_TS ");
		sql.append("	,'" + userLog.getJobId() + "'                                              AS UPDATE_USER_ID ");
		sql.append("	,COALESCE(DTPS.SIIRESAKI_CD, '" + SIIRESAKI_CD_NULL + "')                  AS SIIRESAKI_CD ");
		sql.append("	,DTPS.SYOHI_KIGEN_HYOJI_PATTER                                             AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("	,DTPS.LABEL_SEIBUN                                                         AS LABEL_SEIBUN ");
		sql.append("	,DTPS.LABEL_HOKAN_HOHO                                                     AS LABEL_HOKAN_HOHO ");
		sql.append("	,DTPS.LABEL_TUKAIKATA                                                      AS LABEL_TUKAIKATA ");
		// #2839 MSTB992 2016.11.25 S.Takayama (S)
		sql.append("	,DTPS.GYOTAI_KB AS GYOTAI_KB");
		// #2839 MSTB992 2016.11.25 S.Takayama (E)
// #3049 Add 2016.12.09 Li.Sheng (S)
				sql.append("	,DTPS.LABEL_COUNTRY_NA AS LABEL_COUNTRY_NA");
// #3049 Add 2016.12.09 Li.Sheng (E)
	    // 2017/02/09 T.Han #3765対応（S)
		sql.append("	,DTPS.HANBAI_TANI_EN AS HANBAI_TANI_EN ");
	    // 2017/02/09 T.Han #3765対応（E)
		sql.append(" FROM ");
		sql.append("	DT_TEC_PLU_SEND DTPS ");
		sql.append("	LEFT JOIN ");
		sql.append("	WK_TEC_PLU WTP ");
		sql.append("	ON ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("	DTPS.BUNRUI1_CD	= WTP.BUNRUI1_CD	AND ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("	DTPS.SYOHIN_CD	= WTP.SYOHIN_CD	AND ");
		sql.append("	DTPS.TENPO_CD	= WTP.TENPO_CD ");
		sql.append(" WHERE ");
		sql.append("	WTP.ERR_KB = '" + mst000102_IfConstDictionary.ERROR_KB_NORMAL + "'			AND ");
		sql.append("	WTP.BAIKA_HAISHIN_FG = '" + mst011701_BaikaHaishinFlagDictionary.SURU.getCode() + "'	AND ");
		//2017/01/11 VINX M.Kanno #3587対応 (S)
		//sql.append("	DTPS.SYOHIN_CD IS NOT NULL													AND ");
		sql.append("	DTPS.SYOHIN_CD IS NOT NULL													");
		//sql.append("	( ");
		//sql.append("	 	WTP.PLU_SEND_DT = '" + yokuBatchDt + "'		OR ");
		//sql.append("	 	WTP.PLU_SEND_DT = '" + TOKUSYU_DT + "' ");
		//sql.append("	) ");
		//2017/01/11 VINX M.Kanno #3587対応 (S)
		//#3686 X.Liu Add 2017.02.17 (S)
		sql.append("	AND WTP.DELETE_FG	= '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		//#3686 X.Liu Add 2017.02.17 (E)

		return sql.toString();
	}
	//2016/11/04 VINX h.sakamoto #1750対応 (E)

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
	}


	/******* 共通処理 **********/

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
