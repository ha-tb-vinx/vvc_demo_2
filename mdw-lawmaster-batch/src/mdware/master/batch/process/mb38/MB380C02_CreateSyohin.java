package mdware.master.batch.process.mb38;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.dictionary.HachuFukaYobiKbDictionary;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.bean.mst001501_SaibanBean;
import mdware.master.common.command.mst38A101_SyohinCheckGRO;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst001101_TeikanKbDictionary;
import mdware.master.common.dictionary.mst001201_EosKbDictionary;
import mdware.master.common.dictionary.mst001701_ButuryuKbDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006501_BySyoninFgDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.common.dictionary.mst007001_MstSiyofukaKbDictionary;
import mdware.master.common.dictionary.mst007501_ZeiKbDictionary;
import mdware.master.common.dictionary.mst009301_TaikeiKirikaeJyotaiDictionary;
import mdware.master.common.dictionary.mst010701_PcKbDictionary;
import mdware.master.common.dictionary.mst011201_CgcHenpinKbDictionary;
import mdware.master.common.dictionary.mst011301_SobaSyohinKbDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.common.dictionary.mst011701_KazeiKbDictionary;
import mdware.master.common.dictionary.mst011901_FbinKbDictionary;
import mdware.master.common.dictionary.mst012001_CaseHachuKbDictionary;
import mdware.master.common.dictionary.mst012101_HachuTaniKbDictionary;
import mdware.master.common.dictionary.mst012201_HanbaiTaniKbDictionary;
import mdware.master.common.dictionary.mst012301_TaxRateKbDictionary;
import mdware.master.common.dictionary.mst012401_DaishiNoDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;


/**
 * <p>タイトル:商品マスタEXCEL登録処理（グロサリー・バラエティ）</p>
 * <p>説明:商品マスタEXCEL登録処理（グロサリー・バラエティ）を行います。</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.0 2014/09/15 Ha.ntt 海外LAWSON様通貨対応
 * @version 1.01 (2015.07.10) DAI.BQ FIVImart様対応
 * @version 1.02 (2015.08.17) THO.VT FIVImart様対応
 * @version 1.03 (2015.10.07) THAO.NTL #768対応
 * @version 1.04 (2015.11.26) TU.TD FIVImart様対応
 * @version 1.05 (2016.01.11) Huy.NT FIVI様対応
 * @Version 1.06 (2016.03.14) TU.TD 設計書No.618(#1155) FIVImart対応
 * @Version 1.07 (2016.03.16) Vuong.LT 設計書No.623(#1177) FIVImart対応
 * @Version 1.08 (2016.06.03) M.Kanno #1417 FIVImart対応
 * @Version 1.09 (2016.08.04) M.Akagi #1900 FIVImart対応
 * @Version 1.10 (2016.08.22) M.Akagi #1964 FIVImart対応
 * @Version 1.11 (2016.10.20) T.Arimoto #2254 FIVImart対応
 * @Version 1.12 (2016.10.25) Li.Sheng #2258 FIVI様対応
 * @Version 1.13 (2016.11.02) T.Arimoto #2258 FIVI様対応
 * @Version 1.14 (2016.11.11) T.Arimoto #2256 FIVI様対応
 * @Version 1.15 (2016.11.22) T.Arimoto #2803 FIVI様対応
 * @Version 1.16 (2016.12.22) T.Arimoto #2841 FIVI様対応
 * @Version 1.17 (2017.01.12) M.Akagi #3531 FIVI様対応
 * @Version 1.18 (2017.01.20) S.Takayama #3701 FIVI様対応
 * @Version 1.19 (2017.02.03) T.Arimoto #1174 FIVI様対応
 * @Version 1.20 (2017.02.22) M.Son #4158 FIVI様対応
 * @Version 1.21 (2017.02.27) Li.Sheng #4205 FIVI様対応
 * @Version 1.22 (2017.03.07) M.Akagi #4302 FIVI様対応
 * @Version 1.23 (2017.03.15) T.Arimoto #4358 FIVI様対応
 * @Version 1.24 (2017.04.03) M.Akagi #4509
 * @Version 1.25 (2020.07.22) KHAI.NN #6167 MKV様対応
 * @Version 1.26 (2021.09.09) SIEU.D #6338 MKV対応 
 * @Version 1.27 (2021.09.27) SIEU.D #6355 MKV対応 
 * @Version 1.28  (2021.12.14) HOAI.TTT #6409 対応
 * @Version 1.29  (2022.06.29) DINH.TP #6505
 * @Version 1.30  (2025.04.09) HUY.LTH #33102
 */

public class MB380C02_CreateSyohin {

	private MasterDataBase dataBase = null;

	//batchID
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	private final String BATCH_ID = "MB38-0C-02";
	private final String BATCH_NA = "商品マスタ生成（グロサリー・バラエティ）";

	//PreparedStatement
	private PreparedStatement SyohinInsert 			= null; 	// 商品マスタ			Insert用
	private PreparedStatement SyohinUpInsert 		= null; 	// 商品マスタ			Insert用（更新レコード）
	private PreparedStatement SyohinUpdate 			= null; 	// 商品マスタ			Update用
	private PreparedStatement SyohinDelIns 			= null; 	// 商品マスタ			Insert用（削除レコード）
	private PreparedStatement SyohinDelUp 			= null; 	// 商品マスタ			Update用（削除レコード）
	//Add 2015.07.10 DAI.BQ (S)
	private PreparedStatement ASNSyohinInsert 		= null; 	// 商品マスタASN		Insert用
	private PreparedStatement ASNSyohinUpInsert 	= null; 	// 商品マスタASN		Insert用（更新レコード）
	private PreparedStatement ASNSyohinUpdate 		= null; 	// 商品マスタASN		Update用
	private PreparedStatement ASNSyohinDelIns 		= null; 	// 商品マスタASN		Insert用（削除レコード）
	private PreparedStatement ASNSyohinDelUp 		= null; 	// 商品マスタASN		Update用（削除レコード）
	//Add 2015.07.10 DAI.BQ (E)
	private PreparedStatement GiftSyohinUpInsert 	= null; 	// ギフト商品マスタ		Insert用（更新・削除レコード）
	private PreparedStatement KeiryokiUpInsert 		= null; 	// 計量器マスタ			Insert用（更新・削除レコード）
	private PreparedStatement GiftSyohinDelUp 		= null; 	// ギフト商品マスタ		Update用（削除レコード）
	private PreparedStatement KeiryokiDelUp 		= null; 	// 計量器マスタ			Update用（削除レコード）
	private PreparedStatement TrGiftSyohinCdUp 		= null;	 	// ギフト商品TRの商品コード更新用
	private PreparedStatement HachuUpInsert 		= null; 	// 商品発注基準日マスタ	Insert用（更新・削除レコード）
	private PreparedStatement HachuDelUp 			= null; 	// 商品発注基準日マスタ	Update用（削除レコード）
	private PreparedStatement TrKeiryokiSyohinCdUp 	= null;		// 計量器TRの商品コード更新用


	private PreparedStatement MessageInsert = null; // 処理結果メッセージInsert用

	private final String deleteString = "*"; 		// 削除を表す文字

	private final String TABLE_NA = "TR_SYOHIN"; 	// 対象テーブル名

	private final String SYUBETU = mst006401_DataKindDictionary.SYOHIN.getCode(); // データ種別

	private MB380000_CommonSql 			comSql 			= null;	// 共通SQLクラス
	private MB380007_CommonSyohinSql 	comSyohin 		= null; // 商品マスタ生成共通SQLクラス
	private MB380002_CommonSyohinCheck 	Check 			= null; // 項目チェック用クラス
	private mst38A101_SyohinCheckGRO 	SyohinCheckGRO 	= null; // 共通チェック用クラス

	private int ICOUNT = 0; //新規登録数
	private int UCOUNT = 0; //更新数
	private int DCOUNT = 0; //削除数
	private int CCOUNT = 0; //予約取消数
	private int ECOUNT = 0; //エラー数

//	private static final int DB2_00001 = -803; //DB2用一意制約エラーコード
	private static final int DB2_00001 = 1; //ORACLE用一意制約エラーコード

	//オンライン日付
	private String MasterDT = null;
	private String DefaultYukoDt = null;

	private static final String DEL_FG_INAI = mst000801_DelFlagDictionary.INAI.getCode();

	private int waitTime = 0;
	private int retryCnt = 0;

	// 2016/10/25 T.Arimoto #2256対応（S)
	// 世代原価処理にて使用するテーブルインデックス
	private static final int NOW_GEN_TABLE_INDEX = 0; // 現在の原価のテーブルインデックス
	private static final int CUR_GEN_TABLE_INDEX = 1; // 現世代の原価のテーブルインデックス
	private static final int ONE_GEN_TABLE_INDEX = 2; // 1世代前の原価のテーブルインデックス
	private static final int TWO_GEN_TABLE_INDEX = 3; // 2世代前の原価のテーブルインデックス
	private static final String EMG_ON = "1";	// 緊急配信フラグON
	private static final String EMG_OFF = "0";	// 緊急配信フラグOFF
	// 2016/10/25 T.Arimoto #2256対応（E)

	// #33102 Mod 2025.04.09 HUY.LTH (S)
	private static final String MIN_HACHU_QT_DEFAULT = "0";
	private static final String MAX_HACHU_QT_DEFAULT = "9999";
	// #33102 Mod 2025.04.09 HUY.LTH (E)

	/**
	 * コンストラクタ
	 */
	public MB380C02_CreateSyohin() {
		dataBase = new MasterDataBase("rbsite_ora");
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @param waitTime ウエイト時間
	 * @param retryCnt リトライ回数
	 * @throws Exception 例外
	 */
	public void execute(String batchId,String waitTime,String retryCnt) throws Exception {
		batchID = batchId;
		this.waitTime = Integer.parseInt(waitTime);
		this.retryCnt = Integer.parseInt(retryCnt);
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		ResultSet irs = null;
		ResultSet urs = null;
		ResultSet drs = null;
		ResultSet crs = null;

		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

			int iCount = 0;
			String sql = "";

			// システムコントロール情報取得
			getSystemControl();
			// #6409 Mod 2021.12.14 HOAI.TTT (S)
			//Map map = MB380001_CommonMessage.getMsg();
			Map map = MB380C01_CommonMessage.getMsg();
			// #6409 Mod 2021.12.14 HOAI.TTT (E)
			comSql = new MB380000_CommonSql();
			comSyohin = new MB380007_CommonSyohinSql(MasterDT, BATCH_ID, mst000601_GyoshuKbDictionary.GRO.getCode());

			// 2016/10/27 T.Arimoto #2256対応（S)
			//データ補正処理する
//			writeLog(Level.INFO_INT, "有効日補正処理開始");
//			sql = comSql.getYukoDtFollowSQL(TABLE_NA, SYUBETU, MasterDT);
//			iCount = dataBase.executeUpdate(sql);
//			writeLog(Level.INFO_INT, iCount + "件の有効日を補正しました。");
			// 2016/10/27 T.Arimoto #2256対応（S)

			//予約取消データ取得
			writeLog(Level.INFO_INT, "予約取消データ取得処理開始");
			sql = getYoyakuTorikesiSelSQL();
			crs = dataBase.executeQuery(sql);
			writeLog(Level.INFO_INT, "予約取消データ取得処理終了");

			//新規、更新、削除データの取得
			//新規データ取得
			writeLog(Level.INFO_INT, "新規データ取得処理開始");
			sql = getInsertSelSQL();
			irs = dataBase.executeQuery(sql);
			writeLog(Level.INFO_INT, "新規データ取得処理終了");

			//更新データ取得
			writeLog(Level.INFO_INT, "更新データ取得処理開始");
			sql = getUpdateSelSQL();
			urs = dataBase.executeQuery(sql);
			writeLog(Level.INFO_INT, "更新データ取得処理終了");

			//削除データ取得
			writeLog(Level.INFO_INT, "削除データ取得処理開始");
			sql = getDeleteSelSQL();
			drs = dataBase.executeQuery(sql);
			writeLog(Level.INFO_INT, "削除データ取得処理終了");

			//ステートメント作成
			SyohinInsert 		= getPreparedSyohinSQL(dataBase);
			SyohinUpInsert 		= getPreparedSyohinUpInsSQL(dataBase);
			SyohinUpdate 		= getPreparedSyohinUpSQL(dataBase);
			SyohinDelIns 		= comSyohin.getPreparedSyohinInsSQL(dataBase, BATCH_ID);
			SyohinDelUp 		= getPreparedSyohinDelUpSQL(dataBase);
			//Add 2015.07.10 DAI.BQ (S)
			ASNSyohinInsert 	= getPreparedASNSyohinSQL(dataBase);
			ASNSyohinUpInsert 	= getPreparedASNSyohinUpInsSQL(dataBase);
			ASNSyohinUpdate 	= getPreparedASNSyohinUpSQL(dataBase);
			ASNSyohinDelIns 	= comSyohin.getPreparedASNSyohinInsSQL(dataBase);
			ASNSyohinDelUp		= getPreparedASNSyohinDelUpSQL(dataBase);
			//Add 2015.07.10 DAI.BQ (E)
			GiftSyohinUpInsert 	= comSyohin.getPreparedGiftSyohinInsSQL(dataBase, BATCH_ID);
			KeiryokiUpInsert  	= comSyohin.getPreparedKeiryokiInsSQL(dataBase, BATCH_ID);
			GiftSyohinDelUp 	= comSyohin.getPreparedSyohinSubDelUpSQL(dataBase, "R_GIFT_SYOHIN");
			KeiryokiDelUp   	= comSyohin.getPreparedSyohinSubDelUpSQL(dataBase, "R_KEIRYOKI");
			HachuUpInsert  		= comSyohin.getPreparedSyohinHachuNohinkijunbiInsSQL(dataBase, BATCH_ID);
			HachuDelUp   		= comSyohin.getPreparedSyohinSubDelUpSQL(dataBase, "R_SYOHIN_HACHUNOHINKIJUNBI");
			TrGiftSyohinCdUp    = comSyohin.getPreparedTrSyohinCdUpSQL(dataBase, "TR_GIFT_SYOHIN");
			TrKeiryokiSyohinCdUp = comSyohin.getPreparedTrSyohinCdUpSQL(dataBase, "TR_KEIRYOKI");

			MessageInsert = comSql.getPreparedMessageSQL(dataBase);

			// 共通チェック
			Check = new MB380002_CommonSyohinCheck(MessageInsert, map, dataBase);

			//共通チェックを行う
			SyohinCheckGRO = new mst38A101_SyohinCheckGRO(dataBase);

			//予約取消
			writeLog(Level.INFO_INT, "予約取消データ処理開始");
			dataProcess(crs);

			writeLog(Level.INFO_INT, CCOUNT + "件の予約取消データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の予約取消データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "予約取消データ処理終了");
			ECOUNT = 0;

			//新規登録
			writeLog(Level.INFO_INT, "新規登録データ処理開始");
			dataProcess(irs);

			writeLog(Level.INFO_INT, ICOUNT + "件の新規登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の新規登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "新規登録データ処理終了");
			ECOUNT = 0;

			//更新
			writeLog(Level.INFO_INT, "更新データ処理開始");
			dataProcess(urs);

			writeLog(Level.INFO_INT, UCOUNT + "件の更新登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の更新登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "更新データ処理終了");
			ECOUNT = 0;

			//削除
			writeLog(Level.INFO_INT, "削除データ処理開始");
			dataProcess(drs);

			writeLog(Level.INFO_INT, DCOUNT + "件の削除登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の削除登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "削除データ処理終了");

		} catch (SQLException se) {
			dataBase.rollback();
			writeError(se);
			outputException("E02", se);

			throw se;
		} catch (Exception e) {
			dataBase.rollback();
			writeError(e);
			outputException("E02", e);

			throw e;
		} finally {
			dataBase.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * @param rs
	 */
	private void dataProcess(ResultSet rs) throws SQLException, Exception {

		String JyotaiFlg 			= null;
		String torikomiDT 			= null; 	// 取込日
		String excelSyubetsu 		= null; 	// EXCELファイル種別
		String uketsukeNO 			= null; 	// 受付No.

		String JyotaiFlg_old 		= null;
		String torikomiDT_old 		= null; 	// 前のレコードの取込日
		String excelSyubetsu_old 	= null; 	// 前のレコードのEXCELファイル種別
		String uketsukeNO_old 		= null; 	// 前のレコードの受付No.

		String updateJyotaiSql = null;

		int rsCnt = 0;	// ループカウンタ

		while (rs.next()) {
			rsCnt++;

			batchLog.getLog().info("---------------------データ処理(" + rsCnt + ")番目-----");
			outputRs("処理開始時", rs);

			JyotaiFlg = null;

			torikomiDT 		= rs.getString("torikomi_dt"); 			// 取込日
			excelSyubetsu 	= rs.getString("excel_file_syubetsu"); 	// EXCELファイル種別
			uketsukeNO 		= rs.getString("uketsuke_no"); 			// 受付No.


			//*-----------------------------
			//  データチェック及び登録処理
			//*----------------------------
			if(dataCheck(rs)){
//				JyotaiFlg = "8";		// 正常
			}else{
				JyotaiFlg = "2";		// エラー
			}

			// 登録処理状態更新
			if(uketsukeNO_old != null && !uketsukeNO_old.equals(uketsukeNO) && JyotaiFlg_old != null){
				updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT_old,
								excelSyubetsu_old, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
				writeLog(Level.INFO_INT, updateJyotaiSql);
				dataBase.execute(updateJyotaiSql);
				JyotaiFlg_old = null;
			}

			if(JyotaiFlg != null){
				JyotaiFlg_old = JyotaiFlg;
			}
			uketsukeNO_old = uketsukeNO;
			torikomiDT_old = torikomiDT;
			excelSyubetsu_old = excelSyubetsu;

			outputRs("処理完了後", rs);
		}

		if(uketsukeNO_old != null && JyotaiFlg_old != null){
			updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT,
							excelSyubetsu, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
			writeLog(Level.INFO_INT, updateJyotaiSql);
			dataBase.execute(updateJyotaiSql);
		}

		dataBase.commit();
		dataBase.closeResultSet(rs);
	}

	/**
	 * データチェック及び登録処理
	 * @throws
	 */
	private boolean dataCheck(ResultSet rs) throws SQLException,Exception {

		boolean checkFg 	= true; 			// エラーフラグ
		String[] key 		= new String[6]; 	// キー項目
		boolean insertFg 	= true; 			// Insert、Updateを判断するフラグ
		String delete_dt 	= "";

		outputRs("全体共通項目チェック", rs);


		String syusei_kb  = rs.getString("syusei_kb");		//修正区分
		String trSyohinCd = rs.getString("syohin_cd");		//商品コード
		String by_no      = rs.getString("by_no");			//バイヤーNO

		key[0] = rs.getString("torikomi_dt"); 				//取込日
		key[1] = rs.getString("excel_file_syubetsu"); 		//EXCELファイル種別
		key[2] = rs.getString("uketsuke_no"); 				//受付No.
		key[3] = rs.getString("uketsuke_seq"); 				//受付SEQNo.
		key[4] = this.SYUBETU; 								//シート種別
		key[5] = by_no	;									//バイヤーNO

		Check.setKey(key);

		SyohinCheckGRO.setChkSyohin(false);

		// 共通チェックを行う
		if (SyohinCheckGRO.process(rs) == null){

			checkFg =  false;
		}

		outputRs("商品マスタ関連チェック", rs);

		//共通チェックでエラーの場合
		if (!checkFg) {
			MessageInsert.executeBatch();
			ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), by_no));
			return false;
		}

		//商品マスタとの関連チェック
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			//新規登録
			if (!Check.insertSyohinCheck(rs,BATCH_ID,BATCH_NA,this.waitTime,this.retryCnt)) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
			//更新
			if (!Check.updateSyohinCheck(rs,BATCH_ID,BATCH_NA,this.waitTime,this.retryCnt)) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {
			//削除
			if (!Check.deleteSyohinCheck(rs,BATCH_ID,BATCH_NA,this.waitTime,this.retryCnt)) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
				delete_dt = Check.getYukoDt();
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			//予約取消
			if (!Check.torikesiSyohinCheck(rs,BATCH_ID,BATCH_NA,this.waitTime,this.retryCnt)) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
				delete_dt = Check.getYukoDt();
			}
		}

		if (!checkFg) {
			//エラーの場合
			MessageInsert.executeBatch();
			ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), by_no));
			return false;
		} else {
			//エラーでない場合
			try {
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {

					//商品コード
					String syohin_cd = "";

					if (trSyohinCd == null) {
						trSyohinCd = "";
					}

					//*--------------------------
					//  自動採番
					//*--------------------------
					if (trSyohinCd.trim().length() <= 3){

						// Mod 2015.08.20 DAI.BQ (S)
// 2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(S)
//						syohin_cd = mst001501_SaibanBean.getSaibanKetalaw("m_syohin_gro", rs.getString("bunrui1_cd"), rs.getString("siiresaki_cd"), trSyohinCd.trim(), rs.getString("yuko_dt"), DateChanger.addDate(MasterDT, 1), by_no, dataBase);
//						syohin_cd = mst001501_SaibanBean.getSaibanKeta2("m_syohin_gro", rs.getString("bunrui1_cd"), trSyohinCd.trim(), rs.getString("yuko_dt"), DateChanger.addDate(MasterDT, 1), by_no, dataBase);
// 2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(E)
						syohin_cd = mst001501_SaibanBean.getSaibanKetalaw("fi_syohin", rs.getString("bunrui1_cd"), rs.getString("siiresaki_cd"), trSyohinCd.trim(), rs.getString("yuko_dt"), DateChanger.addDate(MasterDT, 1), by_no, dataBase);
						// Mod 2015.08.20 DAI.BQ (E)

						if(syohin_cd == null || syohin_cd.length() != 13){
							Check.setPreparedMessageSQL("0260");
							outputMessage("メッセージ登録", Check.getKeys());
							MessageInsert.executeUpdate();
							outputMessage("TR商品コード更新", trSyohinCd);
							ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), by_no));
							return false;
						}

						if(trSyohinCd.trim().length() == 3 && !trSyohinCd.trim().equals(syohin_cd.substring(0,3))){
							Check.setPreparedMessageSQL("0222");
							outputMessage("メッセージ登録", Check.getKeys());
							MessageInsert.executeUpdate();
							outputMessage("TR商品コード更新", trSyohinCd);
							ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), by_no));
							return false;
						}

						outputMessage("TR商品コード更新", trSyohinCd);

						//TR_SYOHINの商品コードを更新する
						dataBase.executeUpdate(comSql.updateSyohinCodeSQL(TABLE_NA, key, syohin_cd));

						//ギフト商品TRの商品コードを更新
						comSyohin.setPreparedTrSyohinCdUpSQL(TrGiftSyohinCdUp, rs, syohin_cd);
						TrGiftSyohinCdUp.executeUpdate();

						//計量器TRの商品コードを更新
						comSyohin.setPreparedTrSyohinCdUpSQL(TrKeiryokiSyohinCdUp, rs, syohin_cd);
						TrKeiryokiSyohinCdUp.executeUpdate();

					}else {

						syohin_cd = trSyohinCd;
					}

					//*----------------------
					//  新規登録
					//*----------------------
					outputRs("新規登録", rs);
					// 項目セット（INSERT）
					setPrepareSyohinSQL(SyohinInsert, rs, insertFg, syohin_cd);
					//Add 2015.07.10 DAI.BQ (S)
					setPrepareASNSyohinSQL(ASNSyohinInsert, rs, insertFg, syohin_cd);
					//Add 2015.07.10 DAI.BQ (E)

					String msg = trSyohinCd + "," + key[0] + "," + key[1] + "," + key[2] + "," + key[3];
					outputMessage("TRテーブル商品コード更新", msg);

					msg = insertFg + "," + rs.getString("bunrui1_cd") + "," + trSyohinCd + "," + rs.getString("yuko_dt");
					outputMessage("商品マスタ登録(1)", msg);
					SyohinInsert.executeUpdate();
					//Add 2015.07.10 DAI.BQ (S)
					ASNSyohinInsert.executeUpdate();
					//Add 2015.07.10 DAI.BQ (E)
				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {

					//更新
					outputRs("更新処理", rs);

					if (insertFg) {

						String msg = rs.getString("bunrui1_cd") + "," + rs.getString("syohin_cd") + "," + rs.getString("yuko_dt");
						outputMessage("商品マスタ登録(2)", msg);

						// 商品マスタ
						// 項目セット（UPDATE INSERT）
						setPrepareSyohinUpInsSQL(SyohinUpInsert, rs, "bunrui1_cd", rs.getString("syohin_cd"));
						SyohinUpInsert.executeUpdate();

						// 2016/10/25 T.Arimoto #2256対応（S)
						// 未来世代の世代原価設定処理
						setGenerationGentankaMethod( rs, getGenerationGentanka( rs.getString("bunrui1_cd").trim(),
								rs.getString("syohin_cd").trim(), rs.getString("yuko_dt").trim() ) ) ;
						// 2016/10/25 T.Arimoto #2256対応（E)

						//Add 2015.07.13 DAI.BQ (S)
						// 商品マスタASN
						// 項目セット（UPDATE INSERT）
						setPrepareASNSyohinUpInsSQL(ASNSyohinUpInsert, rs, "bunrui1_cd", rs.getString("syohin_cd"));
						ASNSyohinUpInsert.executeUpdate();
						//Add 2015.07.13 DAI.BQ (E)

						// ギフト商品マスタ
						comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						GiftSyohinUpInsert.executeUpdate();

						// 計量器商品マスタ
						comSyohin.setPreparedKeiryokiInsSQL(KeiryokiUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						KeiryokiUpInsert.executeUpdate();

						// 商品発注基準日マスタ
						comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						HachuUpInsert.executeUpdate();

					} else {
						setPrepareSyohinSQL(SyohinUpdate, rs, insertFg,trSyohinCd);
						SyohinUpdate.executeUpdate();

						// 2016/10/25 T.Arimoto #2256対応（S)
						// 未来世代の世代原価設定処理
						setGenerationGentankaMethod( rs, getGenerationGentanka( rs.getString("bunrui1_cd").trim(),
								rs.getString("syohin_cd").trim(), rs.getString("yuko_dt").trim() ) ) ;
						// 2016/10/25 T.Arimoto #2256対応（E)

						//Add 2015.07.13 DAI.BQ (S)
						setPrepareASNSyohinSQL(ASNSyohinUpdate, rs, insertFg,trSyohinCd);
						ASNSyohinUpdate.executeUpdate();
						//Add 2015.07.13 DAI.BQ (E)
					}

				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {

					//削除
					outputRs("商品マスタ削除", rs);
					String msg = insertFg + "," + rs.getString("bunrui1_cd") + "," + rs.getString("syohin_cd") + "," + rs.getString("yuko_dt");
					outputMessage("商品マスタ登録", msg);

					if (insertFg) {

						String mSetYukoDt = rs.getString("YUKO_DT");			//有効日
						String mBunrui1Cd = rs.getString("BUNRUI1_CD");			//分類１コード
						String mSyohinCd  = rs.getString("SYOHIN_CD");			//商品コード
						String mUserId    = rs.getString("BY_NO");				//更新ユーザ
						String mDelFg     = mst000801_DelFlagDictionary.IRU.getCode();

						// 商品マスタ
						// 項目セット（INSERT）
						comSyohin.setPreparedSyohinInsSQL(SyohinDelIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, delete_dt);
						SyohinDelIns.executeUpdate();

						//Add 2015.07.13 DAi.BQ (S)
						// 商品マスタASN
						// 項目セット（INSERT）
						comSyohin.setPreparedASNSyohinInsSQL(ASNSyohinDelIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, delete_dt);
						ASNSyohinDelIns.executeUpdate();
						//Add 2015.07.13 DAi.BQ (E)

						// ギフト商品マスタ
						comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinUpInsert, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, delete_dt);
						GiftSyohinUpInsert.executeUpdate();

						// 計量器マスタ
						comSyohin.setPreparedKeiryokiInsSQL(KeiryokiUpInsert, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, delete_dt);
						KeiryokiUpInsert.executeUpdate();

						// 商品発注納品基準日マスタ
						comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, delete_dt);
						HachuUpInsert.executeUpdate();

					} else {

						// 商品マスタ
						setPreparedSyohinDelUpSQL(SyohinDelUp, rs);
						SyohinDelUp.executeUpdate();

						//Add 2015.07.13 DAi.BQ (S)
						// 商品マスタASN
						setPreparedASNSyohinDelUpSQL(ASNSyohinDelUp, rs);
						ASNSyohinDelUp.executeUpdate();
						//Add 2015.07.13 DAi.BQ (E)

						// ギフト商品マスタ
						comSyohin.setPreparedSyohinSubDelUpSQL(GiftSyohinDelUp, rs);
						GiftSyohinDelUp.executeUpdate();

						// 計量器マスタ
						comSyohin.setPreparedSyohinSubDelUpSQL(KeiryokiDelUp, rs);
						KeiryokiDelUp.executeUpdate();

						// 商品発注納品基準日マスタ
						comSyohin.setPreparedSyohinSubDelUpSQL(HachuDelUp, rs);
						HachuDelUp.executeUpdate();
					}

				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {

					String sql = "";

					//取消
					outputRs("商品マスタ取消", rs);
					String msg = insertFg + "," + rs.getString("bunrui1_cd") + "," + rs.getString("syohin_cd") + "," + rs.getString("yuko_dt");
					outputMessage("商品マスタ取消", msg);

					// 物理削除対象のレコードをバックアップ（商品マスタ）
					sql = getBackupSQL(rs, MasterDT, "R_SYOHIN");
					dataBase.executeUpdate(sql);

					//Add 2015.07.09 DAI.BQ (S)
					// 物理削除対象のレコードをバックアップ（商品マスタASN）
					sql = getBackupSQL(rs, MasterDT, "R_SYOHIN_ASN");
					dataBase.executeUpdate(sql);
					//Add 2015.07.09 DAI.BQ (E)

					// 物理削除対象のレコードをバックアップ（ギフト商品マスタ）
					sql = getBackupSQL(rs, MasterDT, "R_GIFT_SYOHIN");
					dataBase.executeUpdate(sql);

					// 物理削除対象のレコードをバックアップ（計量器マスタ）
					sql = getBackupSQL(rs, MasterDT, "R_KEIRYOKI");
					dataBase.executeUpdate(sql);

					// 物理削除対象のレコードをバックアップ（商品発注納品基準日マスタ）
					sql = getBackupSQL(rs, MasterDT, "R_SYOHIN_HACHUNOHINKIJUNBI");
					dataBase.executeUpdate(sql);

					// 物理削除（商品マスタ）
					sql = getDeleteSQL(rs, "R_SYOHIN");
					dataBase.executeUpdate(sql);

					//Add 2015.07.09 DAI.BQ (S)
					// 物理削除（商品マスタASN）
					sql = getDeleteSQL(rs, "R_SYOHIN_ASN");
					dataBase.executeUpdate(sql);
					//Add 2015.07.09 DAI.BQ (E)

					// 物理削除（ギフト商品マスタ）
					sql = getDeleteSQL(rs, "R_GIFT_SYOHIN");
					dataBase.executeUpdate(sql);

					// 物理削除（計量器マスタ）
					sql = getDeleteSQL(rs, "R_KEIRYOKI");
					dataBase.executeUpdate(sql);

					// 物理削除（商品発注納品基準日マスタ）
					sql = getDeleteSQL(rs, "R_SYOHIN_HACHUNOHINKIJUNBI");
					dataBase.executeUpdate(sql);

					// 2016/10/25 T.Arimoto #2256対応（S)
					// 未来世代の世代原価設定処理
					setGenerationGentankaMethod( rs, getPastGentanka(rs.getString("bunrui1_cd").trim(),
							rs.getString("syohin_cd").trim(), rs.getString("yuko_dt").trim() ) );
					// 2016/10/25 T.Arimoto #2256対応（E)
				}

			} catch (SQLException e) {
				//ロールバック
				dataBase.rollback();
				if (e.getErrorCode() == DB2_00001) {
					String msg = "";
					if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
						msg = "0009"; //既に登録されている。（チェック中に画面より登録された場合）
					} else {
						msg = "0010"; //他のユーザーにより変更されている。
					}
					Check.setPreparedMessageSQL(msg);
					MessageInsert.executeUpdate();
					ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), by_no));
					return false;
				} else {
					batchLog.getLog().fatal(BATCH_ID, BATCH_NA, "商品マスタのデータ登録に失敗しました。");
					userLog.fatal("商品マスタのデータ登録に失敗しました。");
					throw e;
				}
			}
		}

		//登録完了（正常）
		Check.setPreparedMessageSQL("0000");

		outputMessage("メッセージ登録", Check.getKeys());

		MessageInsert.executeUpdate();
		int tmp_count = dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.SYORIZUMI.getCode(), by_no));

		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			//新規登録
			ICOUNT += tmp_count;
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
			//更新
			UCOUNT += tmp_count;
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {
			//削除
			DCOUNT += tmp_count;
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			//取消
			CCOUNT += tmp_count;
		}
		return true;
	}

	/**
	  * 新規登録データ取得SQL生成
	  * @throws
	  */
	private String getInsertSelSQL() {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append("    TR.BY_NO AS BY_NO, ");												//バイヤーno
		sql.append("    TR.TORIKOMI_DT,");													//取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU,");											//EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO,");													//受付ファイル№
		sql.append("    TR.UKETSUKE_SEQ,");													//受付SEQ №
		sql.append("    TR.TR_BUNRUI1_CD               AS BUNRUI1_CD,");					//TR_分類１コード
		sql.append("    TR.TR_BUNRUI5_CD               AS BUNRUI5_CD,");					//TR_分類５コード
		sql.append("    TR.TR_SYOHIN_CD                AS SYOHIN_CD,");						//TR_商品コード
		sql.append("    TR.TR_YUKO_DT                  AS YUKO_DT,");						//TR_有効日
		sql.append("    TR.TR_SYOHIN_KB                AS SYOHIN_KB,");						//TR_商品区分
		sql.append("    TR.TR_SOBA_SYOHIN_KB           AS SOBA_SYOHIN_KB,");				//TR_相場商品区分
		sql.append("    TR.TR_TEIKAN_KB                AS TEIKAN_KB,");						//TR_定貫区分
		sql.append("    TR.TR_ZAIKO_SYOHIN_CD          AS ZAIKO_SYOHIN_CD,");				//TR_在庫用商品集計コード
		sql.append("    TR.TR_JYOHO_SYOHIN_CD          AS JYOHO_SYOHIN_CD,");				//TR_情報系用商品集計コード
		sql.append("    TR.TR_SYOHIN_WIDTH_QT          AS SYOHIN_WIDTH_QT,");				//TR_商品サイズ(幅)
		sql.append("    TR.TR_SYOHIN_HEIGHT_QT         AS SYOHIN_HEIGHT_QT,");				//TR_商品サイズ(高さ)
		sql.append("    TR.TR_SYOHIN_DEPTH_QT          AS SYOHIN_DEPTH_QT,");				//TR_商品サイズ(奥行き)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
//		sql.append("    TR.TR_KIKAKU_KANJI_2_NA        AS MAKER_CD,");						//TR_メーカコード（漢字規格２を利用）
//		sql.append("    ''                             AS KIKAKU_KANJI_2_NA,");				//TR_漢字規格２（TR漢字規格２はメーカコードと利用する為、ここでは空文字セット）
		sql.append("    TR.TR_KIKAKU_KANJI_2_NA        AS KIKAKU_KANJI_2_NA,");				//TR_漢字規格２
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("    TR.TR_HINMEI_KANJI_NA          AS HINMEI_KANJI_NA,");				//TR_漢字品名
		sql.append("    TR.TR_KIKAKU_KANJI_NA          AS KIKAKU_KANJI_NA,");				//TR_漢字規格
		sql.append("    TR.TR_REC_HINMEI_KANJI_NA      AS REC_HINMEI_KANJI_NA,");			//TR_POSレシート品名(漢字)
		sql.append("    TR.TR_KIKAKU_KANA_2_NA         AS KIKAKU_KANA_2_NA,");				//TR_カナ規格２
		sql.append("    TR.TR_HINMEI_KANA_NA           AS HINMEI_KANA_NA,");				//TR_カナ品名
		sql.append("    TR.TR_KIKAKU_KANA_NA           AS KIKAKU_KANA_NA,");				//TR_カナ規格
		sql.append("    TR.TR_REC_HINMEI_KANA_NA       AS REC_HINMEI_KANA_NA,");			//TR_POSレシート品名(カナ)
		sql.append("    TR.TR_SIIRESAKI_CD             AS SIIRESAKI_CD,");					//TR_仕入先コード
		sql.append("    TR.TR_BUTURYU_KB               AS BUTURYU_KB,");					//TR_物流区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("    TR.TR_BAIKA_HAISHIN_FG         AS BAIKA_HAISHIN_FG,");				//TR_売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("    TR.TR_GENTANKA_VL              AS GENTANKA_VL,");					//TR_原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    TR.TR_GENTANKA_NUKI_VL         AS GENTANKA_NUKI_VL,");				//TR_原価単価(税抜)
		sql.append("    TR.TR_BAITANKA_VL              AS BAITANKA_VL,");					//TR_売価単価
		sql.append("    TR.TR_BAITANKA_NUKI_VL         AS BAITANKA_NUKI_VL,");				//TR_売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TR.TR_MAKER_KIBO_KAKAKU_VL     AS MAKER_KIBO_KAKAKU_VL,");			//TR_メーカー希望小売り価格
		sql.append("    TR.TR_ZEI_KB                   AS ZEI_KB,");						//TR_税区分
		sql.append("    TR.TR_TAX_RATE_KB              AS TAX_RATE_KB,");					//TR_税率区分
		//TR_SYOHIN参照より取得
		sql.append("    TAX_RATE_CK.TAX_RATE_KB        AS TAX_RATE_CK, ");					//税率分
		sql.append("    TAX_RATE_CK.TAX_RT             AS TAX_RT, ");						//税率
		sql.append("    TR.TR_EOS_KB                   AS EOS_KB,");						//TR_EOS区分
		sql.append("    TR.TR_HACHU_TANI_NA            AS HACHU_TANI_NA,");					//TR_発注単位呼称
		sql.append("    TR.TR_HANBAI_TANI              AS HANBAI_TANI,");					//TR_販売単位呼称
		sql.append("    TR.TR_HACHUTANI_IRISU_QT       AS HACHUTANI_IRISU_QT,");			//TR_発注単位(入数)
		sql.append("    TR.TR_MAX_HACHUTANI_QT         AS MAX_HACHUTANI_QT,");				//TR_最大発注単位数
		sql.append("    TR.TR_SIME_TIME_1_QT           AS SIME_TIME_1_QT,");				//TR_①締め時間
		sql.append("    TR.TR_BIN_1_KB                 AS BIN_1_KB,");						//TR_①便区分
		sql.append("    TR.TR_SIME_TIME_2_QT           AS SIME_TIME_2_QT,");				//TR_②締め時間
		sql.append("    TR.TR_BIN_2_KB                 AS BIN_2_KB,");						//TR_②便区分
		sql.append("    TR.TR_F_BIN_KB                 AS F_BIN_KB,");						//TR_F便区分
		sql.append("    TR.TR_BARA_IRISU_QT            AS BARA_IRISU_QT,");					//TR_バラ入数
		sql.append("    TR.TR_CASE_HACHU_KB            AS CASE_HACHU_KB,");					//TR_ケース発注区分
		sql.append("    TR.TR_HANBAI_ST_DT             AS HANBAI_ST_DT,");					//TR_販売開始日
		sql.append("    TR.TR_HANBAI_ED_DT             AS HANBAI_ED_DT,");					//TR_販売終了日
		sql.append("    TR.TR_TEN_HACHU_ST_DT          AS TEN_HACHU_ST_DT,");				//TR_店舗発注開始日
		sql.append("    TR.TR_TEN_HACHU_ED_DT          AS TEN_HACHU_ED_DT,");				//TR_店舗発注終了日
		sql.append("    TR.TR_GOT_START_MM             AS GOT_START_MM,");					//TR_季節開始月日
		sql.append("    TR.TR_GOT_END_MM               AS GOT_END_MM,");					//TR_季節終了月日
		sql.append("    TR.TR_PLU_SEND_DT              AS PLU_SEND_DT,");					//TR_PLU送信日
		sql.append("    TR.TR_PC_KB                    AS PC_KB,");							//TR_PC発行区分
		sql.append("    TR.TR_DAISI_NO_NB              AS DAISI_NO_NB,");					//TR_台紙NO
		sql.append("    TR.TR_HANBAI_LIMIT_QT          AS HANBAI_LIMIT_QT,");				//TR_販売期限
		sql.append("    TR.TR_HANBAI_LIMIT_KB          AS HANBAI_LIMIT_KB,");				//TR_販売期限区分
		sql.append("    TR.TR_CENTER_KYOYO_DT          AS CENTER_KYOYO_DT,");				//TR_センター許容日
		sql.append("    TR.TR_CENTER_KYOYO_KB          AS CENTER_KYOYO_KB,");				//TR_センター許容区分
		sql.append("    TR.TR_UNIT_PRICE_TANI_KB       AS UNIT_PRICE_TANI_KB,");			//TR_ユニットプライス-単位区分
		sql.append("    TR.TR_UNIT_PRICE_NAIYORYO_QT   AS UNIT_PRICE_NAIYORYO_QT,");		//TR_ユニットプライス-内容量
		sql.append("    TR.TR_UNIT_PRICE_KIJUN_TANI_QT AS UNIT_PRICE_KIJUN_TANI_QT,");		//TR_ユニットプライス-基準単位量
		sql.append("    TR.TR_SYUZEI_HOKOKU_KB         AS SYUZEI_HOKOKU_KB,");				//TR_酒税報告分類
		sql.append("    TR.TR_SAKE_NAIYORYO_QT         AS SAKE_NAIYORYO_QT,");				//TR_酒内容量
		sql.append("    TR.TR_CGC_HENPIN_KB            AS CGC_HENPIN_KB,");					//TR_CGC返品区分
		sql.append("    TR.TR_MIN_ZAIKOSU_QT           AS MIN_ZAIKOSU_QT,");				//TR_最小在庫数
		sql.append("    TR.TR_MIN_ZAIKONISSU_QT        AS MIN_ZAIKONISSU_QT,");				//TR_最小在庫日数
		sql.append("    TR.TR_MAX_ZAIKOSU_QT           AS MAX_ZAIKOSU_QT,");				//TR_最大在庫数
		sql.append("    TR.TR_MAX_ZAIKONISSU_QT        AS MAX_ZAIKONISSU_QT,");				//TR_最大在庫日数
		sql.append("    TR.TR_ITF_CD                   AS ITF_CD,");						//TR_ITFコード
		sql.append("    TR.TR_NYUKA_SYOHIN_CD          AS NYUKA_SYOHIN_CD,");				//TR_入荷時商品コード
		sql.append("    TR.TR_NYUKA_SYOHIN_2_CD        AS NYUKA_SYOHIN_2_CD,");				//TR_入荷時商品コード２
		sql.append("    TR.TR_VENDER_MAKER_CD          AS VENDER_MAKER_CD,");				//TR_ベンダーメーカーコード
		sql.append("    TR.TR_CENTER_IRISU_QT          AS CENTER_IRISU_QT,");				//TR_最低入数
		sql.append("    TR.TR_CASE_IRISU_QT            AS CASE_IRISU_QT,");					//TR_ケース入り数
		sql.append("    TR.TR_CENTER_IRISU_2_QT        AS CENTER_IRISU_2_QT,");				//TR_梱り合せ数
		sql.append("    TR.TR_CENTER_ZAIKO_KB          AS CENTER_ZAIKO_KB,");				//TR_センター在庫区分
		sql.append("    TR.TR_CENTER_SYOMI_KIKAN_KB    AS CENTER_SYOMI_KIKAN_KB,");			//TR_センター賞味期間区分
		sql.append("    TR.TR_CENTER_SYOMI_KIKAN_DT    AS CENTER_SYOMI_KIKAN_DT,");			//TR_センター賞味期間
		sql.append("    TR.TR_CENTER_BARA_IRISU_QT     AS CENTER_BARA_IRISU_QT,");			//TR_センターバラ入数
		sql.append("    TR.TR_PACK_WEIGHT_QT           AS PACK_WEIGHT_QT,");				//TR_外箱重量
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    TR.TR_FREE_1_KB                AS FREE_1_KB,");						//TR_任意区分１
		sql.append("    TR.TR_FREE_2_KB                AS FREE_2_KB,");						//TR_任意区分２
		sql.append("    TR.TR_FREE_3_KB                AS FREE_3_KB,");						//TR_任意区分３
		sql.append("    TR.TR_FREE_4_KB                AS FREE_4_KB,");						//TR_任意区分４
		sql.append("    TR.TR_FREE_5_KB                AS FREE_5_KB,");						//TR_任意区分５
		sql.append("    TR.TR_COMMENT_1_TX             AS COMMENT_1_TX,");					//TR_コメント１
		sql.append("    TR.TR_COMMENT_2_TX             AS COMMENT_2_TX,");					//TR_コメント２
		sql.append("    TR.TR_FREE_CD                  AS FREE_CD,");						//TR_任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TR.SYUSEI_KB,");													//修正区分
		sql.append("    TR.SAKUSEI_GYO_NO,");												//作成元行
		sql.append("    TR.MST_MAINTE_FG,");												//マスタメンテフラグ
		sql.append("    TR.ALARM_MAKE_FG,");												//アラーム作成フラグ
		//Add 2015.07.13 DAI.BQ (S)
		sql.append("    TR.TR_SYOHIN_EIJI_NA           AS TR_SYOHIN_EIJI_NA, ");
		sql.append("    TR.TR_COUNTRY_CD               AS TR_COUNTRY_CD, ");
		//ADD 2015/08/17 THO.VT (S)
		sql.append("    TR.TR_MAKER_CD                 AS TR_MAKER_CD, ");
		//ADD 2015/08/17 THO.VT (E)
		//Add 2016.01.11 Huy.NT (S)
		sql.append("	TR.TR_HANBAI_HOHO_KB           AS TR_HANBAI_HOHO_KB, ");			//販売方法
		//Add 2016.01.11 Huy.NT (E)
		sql.append("    TR.TR_MIN_ZAIKO_QT             AS TR_MIN_ZAIKO_QT, ");
		sql.append("    TR.TR_SECURITY_TAG_FG          AS TR_SECURITY_TAG_FG, ");
		sql.append("    TR.TR_MEMBER_DISCOUNT_FG       AS TR_MEMBER_DISCOUNT_FG, ");
		sql.append("    TR.TR_HAMPER_SYOHIN_FG         AS TR_HAMPER_SYOHIN_FG, ");
		//Add 2015.07.13 DAI.BQ (E)
		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		sql.append("    TR.NENREI_SEIGEN_KB            AS NENREI_SEIGEN_KB, ");
		sql.append("    TR.NENREI					   AS NENREI, ");
		sql.append("    TR.KAN_KB					   AS KAN_KB, ");
		sql.append("    TR.HOSHOUKIN				   AS HOSHOUKIN, ");
		//#1417対応 2016.06.03 M.Kanno (S)
		sql.append("    TR.TR_SYOHI_KIGEN_DT		   AS SYOHI_KIGEN_DT, ");
		//#1417対応 2016.06.03 M.Kanno (E)
		// #1900対応 2016.08.04 M.Akagi (S)
		sql.append("    TR.TR_SIIRE_ZEI_KB             AS SIIRE_ZEI_KB, ");					//TR_仕入税区分
		sql.append("    TR.TR_SIIRE_TAX_RATE_KB        AS SIIRE_TAX_RATE_KB, ");			//TR_仕入税率区分

		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		sql.append("      TR.TR_WARIBIKI_KB           AS WARIBIKI_KB , ");
		sql.append("      TR.TR_PB_SYOHIN_KB           AS PB_SYOHIN_KB , ");
		sql.append("      TR.TR_IYAKUHIN_KB           AS IYAKUHIN_KB , ");
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		// #6355 Add 2021/09/27 SIEU.D (S)
		sql.append("      TR.TR_SYOMI_KIGEN_NISU           AS SYOMI_KIGEN_NISU , "); 			// 賞味期限
		sql.append("      TR.TR_SHUKKA_KIGEN_NISU           AS SHUKKA_KIGEN_NISU , ");		// 出荷可能限度期日
		sql.append("      TR.TR_NYUKA_KIGEN_NISU           AS NYUKA_KIGEN_NISU , ");			// 入荷可能限度期日
		// #6355 Add 2021/09/27 SIEU.D (E)
		
		//税率区分（仕入）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS SIIRETAX_RATE_CK, ");
		//税率（仕入）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS SIIRETAX_RT, ");

		sql.append("    TR.TR_OROSI_ZEI_KB             AS OROSI_ZEI_KB, ");					//TR_卸税区分
		sql.append("    TR.TR_OROSI_TAX_RATE_KB        AS OROSI_TAX_RATE_KB, ");			//TR_卸税率区分

		//税率区分（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS OROSHITAX_RATE_CK, ");
		//税率（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS OROSHITAX_RT, ");

		sql.append("    TR.TR_OROSI_BAITANKA_VL        AS OROSI_BAITANKA_VL, ");			//TR_卸売価単価
		sql.append("    TR.TR_OROSI_BAITANKA_NUKI_VL   AS OROSI_BAITANKA_NUKI_VL, ");		//TR_卸売価単価（税抜）
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		sql.append("    TR.TR_MIN_HACHU_QT             AS MIN_HACHU_QT, ");					//TR_最低発注数
		sql.append("    TR.TR_HACHU_FUKA_FLG           AS HACHU_FUKA_FLG, ");				//TR_発注不可フラグ
		sql.append("    TR.TR_PER_TXT                  AS PER_TXT, ");						//TR_規格内容
		sql.append("    TR.TR_SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER, ");		//TR_消費期限表示パターン
		sql.append("    TR.TR_PLU_HANEI_TIME           AS PLU_HANEI_TIME, ");				//TR_PLU反映時間
		sql.append("    TR.TR_LABEL_SEIBUN             AS LABEL_SEIBUN, ");					//TR_ラベル成分
		sql.append("    TR.TR_LABEL_HOKAN_HOHO         AS LABEL_HOKAN_HOHO, ");				//TR_ラベル保管方法
		sql.append("    TR.TR_LABEL_TUKAIKATA          AS LABEL_TUKAIKATA, ");				//TR_ラベル使い方
		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql.append("    TR.TR_OROSI_BAITANKA_VL_KOURI          AS TR_OROSI_BAITANKA_VL_KOURI, ");		// 卸売価単価(小売税)
		// 2016/10/25 Li.Sheng #2258 対応（E)
		// #1964対応 2016.08.22 M.Akagi (E)

		// 2017.01.12 M.Akagi #3531 (S)
		sql.append("       TR.TR_OLD_SYOHIN_CD             AS OLD_SYOHIN_CD, ");				//TR_旧商品コード
		// 2017.01.12 M.Akagi #3531 (E)
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)
		sql.append("    '' AS RS_BUNRUI1_CD,");												//分類１コード
		sql.append("    '' AS RS_SYOHIN_CD,");												//商品コード
		sql.append("    '' AS RS_YUKO_DT,");												//有効日
		sql.append("    '' AS RS_SYOHIN_KB,");												//商品区分
		sql.append("    '' AS RS_BUNRUI2_CD,");												//分類２コード
		sql.append("    '' AS RS_BUNRUI5_CD,");												//分類５コード
		sql.append("    '' AS RS_ZAIKO_SYOHIN_CD,");										//在庫用商品集計コード
		sql.append("    '' AS RS_JYOHO_SYOHIN_CD,");										//情報系用商品集計コード
		sql.append("    '' AS RS_HINMEI_KANJI_NA,");										//漢字品名
		sql.append("    '' AS RS_KIKAKU_KANJI_NA,");										//漢字規格
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
		// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//sql.append("    '' AS RS_MAKER_CD,");												//メーカコード
		// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
		sql.append("    '' AS RS_KIKAKU_KANJI_2_NA,");										//漢字規格２
		sql.append("    '' AS RS_REC_HINMEI_KANJI_NA,");									//POSレシート品名(漢字)
		sql.append("    '' AS RS_HINMEI_KANA_NA,");											//カナ品名
		sql.append("    '' AS RS_KIKAKU_KANA_NA,");											//カナ規格
		sql.append("    '' AS RS_KIKAKU_KANA_2_NA,");										//カナ規格２
		sql.append("    '' AS RS_REC_HINMEI_KANA_NA,");										//POSレシート品名(カナ)
		sql.append("    '' AS RS_SYOHIN_WIDTH_QT,");										//商品サイズ(幅)
		sql.append("    '' AS RS_SYOHIN_HEIGHT_QT,");										//商品サイズ(高さ)
		sql.append("    '' AS RS_SYOHIN_DEPTH_QT,");										//商品サイズ(奥行き)
		sql.append("    '' AS RS_ZEI_KB,");													//税区分
		sql.append("    '' AS RS_TAX_RATE_KB,");											//税率区分
		////R_SYOHIN参照より
		//税率区分
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_TAX_RATE_CK, ");
		//税率
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_TAX_RT, ");

		// #1900対応 2016.08.04 M.Akagi (S)
		//仕入税率区分
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_SIIRETAX_RATE_CK, ");
		//仕入税率
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_SIIRETAX_RT, ");

		//税率区分（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_OROSHITAX_RATE_CK, ");
		//税率（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_OROSHITAX_RT, ");
		// #1900対応 2016.08.04 M.Akagi (E)

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("    '' AS RS_BAIKA_HAISHIN_FG,");										//売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("    '' AS RS_GENTANKA_VL,");											//原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    '' AS RS_GENTANKA_NUKI_VL,");										//原価単価(税抜)
		sql.append("    '' AS RS_BAITANKA_VL,");											//売価単価
		sql.append("    '' AS RS_BAITANKA_NUKI_VL,");										//売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    '' AS RS_MAKER_KIBO_KAKAKU_VL,");									//メーカー希望小売り価格
		sql.append("    '' AS RS_SIIRESAKI_CD,");											//仕入先コード
		sql.append("    '' AS RS_EOS_KB,");													//EOS区分
		sql.append("    '' AS RS_HACHU_TANI_NA,");											//発注単位呼称
		sql.append("    '' AS RS_HANBAI_TANI,");											//販売単位呼称
		sql.append("    '' AS RS_HACHUTANI_IRISU_QT,");										//発注単位(入数)
		sql.append("    '' AS RS_MAX_HACHUTANI_QT,");										//最大発注単位数
		sql.append("    '' AS RS_CASE_HACHU_KB,");											//ケース発注区分
		sql.append("    '' AS RS_BARA_IRISU_QT,");											//バラ入数
		sql.append("    '' AS RS_TEN_HACHU_ST_DT,");										//店舗発注開始日
		sql.append("    '' AS RS_TEN_HACHU_ED_DT,");										//店舗発注終了日
		sql.append("    '' AS RS_HANBAI_ST_DT,");											//販売開始日
		sql.append("    '' AS RS_HANBAI_ED_DT,");											//販売終了日
		sql.append("    '' AS RS_TEIKAN_KB,");												//定貫区分
		sql.append("    '' AS RS_SOBA_SYOHIN_KB,");											//相場商品区分
		sql.append("    '' AS RS_BIN_1_KB,");												//①便区分
		sql.append("    '' AS RS_SIME_TIME_1_QT,");											//①締め時間
		sql.append("    '' AS RS_BIN_2_KB,");												//②便区分
		sql.append("    '' AS RS_SIME_TIME_2_QT,");											//②締め時間
		sql.append("    '' AS RS_F_BIN_KB,");												//F便区分
		sql.append("    '' AS RS_BUTURYU_KB,");												//物流区分
		sql.append("    '' AS RS_GOT_START_MM,");											//季節開始月日
		sql.append("    '' AS RS_GOT_END_MM,");												//季節終了月日
		sql.append("    '' AS RS_CENTER_ZAIKO_KB,");										//センター在庫区分
		sql.append("    '' AS RS_NYUKA_SYOHIN_CD,");										//入荷時商品コード
		sql.append("    '' AS RS_NYUKA_SYOHIN_2_CD,");										//入荷時商品コード２
		sql.append("    '' AS RS_ITF_CD,");													//ITFコード
		sql.append("    '' AS RS_VENDER_MAKER_CD,");										//ベンダーメーカーコード
		sql.append("    '' AS RS_CENTER_BARA_IRISU_QT,");									//センターバラ入数
		sql.append("    '' AS RS_CENTER_IRISU_QT,");										//最低入数
		sql.append("    '' AS RS_CASE_IRISU_QT,");											//ケース入り数
		sql.append("    '' AS RS_CENTER_IRISU_2_QT,");										//梱り合せ数
		sql.append("    '' AS RS_MIN_ZAIKOSU_QT,");											//最小在庫数
		sql.append("    '' AS RS_MAX_ZAIKOSU_QT,");											//最大在庫数
		sql.append("    '' AS RS_MIN_ZAIKONISSU_QT,");										//最小在庫日数
		sql.append("    '' AS RS_MAX_ZAIKONISSU_QT,");										//最大在庫日数
		sql.append("    '' AS RS_CENTER_KYOYO_KB,");										//センター許容区分
		sql.append("    '' AS RS_CENTER_KYOYO_DT,");										//センター許容日
		sql.append("    '' AS RS_CENTER_SYOMI_KIKAN_KB,");									//センター賞味期間区分
		sql.append("    '' AS RS_CENTER_SYOMI_KIKAN_DT,");									//センター賞味期間
		sql.append("    '' AS RS_CGC_HENPIN_KB,");											//CGC返品区分
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    '' AS RS_FREE_1_KB,");												//任意区分１
		sql.append("    '' AS RS_FREE_2_KB,");												//任意区分２
		sql.append("    '' AS RS_FREE_3_KB,");												//任意区分３
		sql.append("    '' AS RS_FREE_4_KB,");												//任意区分４
		sql.append("    '' AS RS_FREE_5_KB,");												//任意区分５
		sql.append("    '' AS RS_COMMENT_1_TX,");											//コメント１
		sql.append("    '' AS RS_COMMENT_2_TX,");											//コメント２
		sql.append("    '' AS RS_FREE_CD,");												//任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    '' AS RS_HANBAI_LIMIT_KB,");										//販売期限区分
		sql.append("    '' AS RS_HANBAI_LIMIT_QT,");										//販売期限
		sql.append("    '' AS RS_PLU_SEND_DT,");											//PLU送信日
		sql.append("    '' AS RS_SYUZEI_HOKOKU_KB,");										//酒税報告分類
		sql.append("    '' AS RS_SAKE_NAIYORYO_QT,");										//酒内容量
		sql.append("    '' AS RS_PC_KB,");													//PC発行区分
		sql.append("    '' AS RS_DAISI_NO_NB,");											//台紙NO
		sql.append("    '' AS RS_UNIT_PRICE_TANI_KB,");										//ユニットプライス-単位区分
		sql.append("    '' AS RS_UNIT_PRICE_NAIYORYO_QT,");									//ユニットプライス-内容量
		sql.append("    '' AS RS_UNIT_PRICE_KIJUN_TANI_QT,");								//ユニットプライス-基準単位量
		sql.append("    '' AS RS_PACK_WEIGHT_QT,");											//外箱重量
		//Add 2015.07.13 DAI.BQ (S)
		sql.append("    '' AS RS_SYOHIN_EIJI_NA, ");
		sql.append("    '' AS RS_COUNTRY_CD, ");
		//ADD 2015/08/17 THO.VT (S)
		sql.append("    '' AS RS_MAKER_CD, ");
		//ADD 2015/08/17 THO.VT (E)
		sql.append("    '' AS RS_MIN_ZAIKO_QT, ");
		sql.append("    '' AS RS_SECURITY_TAG_FG, ");
		sql.append("    '' AS RS_MEMBER_DISCOUNT_FG, ");
		sql.append("    '' AS RS_HAMPER_SYOHIN_FG, ");
		// #1900対応 2016.08.04 M.Akagi (S)
		sql.append("    '' AS RS_SIIRE_ZEI_KB, ");										//仕入税区分
		sql.append("    '' AS RS_SIIRE_TAX_RATE_KB, ");									//仕入税率区分
		sql.append("    '' AS RS_OROSI_ZEI_KB, ");										//卸税区分
		sql.append("    '' AS RS_OROSI_TAX_RATE_KB, ");									//卸税率区分
		sql.append("    '' AS RS_OROSI_BAITANKA_VL, ");									//卸売価単価
		sql.append("    '' AS RS_OROSI_BAITANKA_NUKI_VL, ");							//卸売価単価（税抜）
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		sql.append("    '' AS RS_MIN_HACHU_QT, ");										//最低発注数
		sql.append("    '' AS RS_HACHU_FUKA_FLG, ");									//発注不可フラグ
		sql.append("    '' AS RS_PER_TXT, ");											//規格内容
		sql.append("    '' AS RS_SYOHI_KIGEN_HYOJI_PATTER, ");							//消費期限表示パターン
		sql.append("    '' AS RS_PLU_HANEI_TIME, ");									//PLU反映時間
		// 2017.01.12 M.Akagi #3531 (S)
		sql.append("    '' AS RS_OLD_SYOHIN_CD, ");									//旧商品コード
		// 2017.01.12 M.Akagi #3531 (E)
		// 2017.03.07 M.Akagi #4302 (S)
		sql.append("    '' AS RS_NENREI_SEIGEN_KB, ");		// 年齢制限区分
		sql.append("    '' AS RS_NENREI, ");		// 年齢
		sql.append("    '' AS RS_KAN_KB, ");		// 瓶区分
		sql.append("    '' AS RS_HOSHOUKIN, ");		// 保証金
		// 2017.03.07 M.Akagi #4302 (E)
		// 2016/11/22 T.Arimoto #2803対応（S)
		//sql.append("    '' AS RS_LABEL_SEIBUN, ");										//ラベル成分
		//sql.append("    '' AS RS_LABEL_HOKAN_HOHO, ");									//ラベル保管方法
		//sql.append("    '' AS RS_LABEL_TUKAIKATA, ");									//ラベル使い方
		sql.append("    '' AS RSA_LABEL_SEIBUN, ");										//ラベル成分
		sql.append("    '' AS RSA_LABEL_HOKAN_HOHO, ");									//ラベル保管方法
		sql.append("    '' AS RSA_LABEL_TUKAIKATA, ");									//ラベル使い方
		// 2016/11/22 T.Arimoto #2803対応（E)
		// #1964対応 2016.08.22 M.Akagi (E)
		// 2016/11/22 T.Arimoto #2803対応（S)
		sql.append("    '' AS RSA_SYOHIN_EIJI_NA, ");									//商品名（英字）
		sql.append("    '' AS RSA_COUNTRY_CD, ");										//国コード
		sql.append("    '' AS RSA_MAKER_CD, ");											//メーカーコード
		sql.append("    '' AS RSA_HANBAI_HOHO_KB, ");									//販売方法
		sql.append("    '' AS RSA_MIN_ZAIKO_QT, ");										//最低在庫数量
		sql.append("    '' AS RSA_SECURITY_TAG_FG, ");									//セキュリティタグフラグ
		sql.append("    '' AS RSA_MEMBER_DISCOUNT_FG, ");								//メンバーディ割引対象外商品フラグ
		sql.append("    '' AS RSA_HAMPER_SYOHIN_FG, ");									//ハンパー商品フラグ
		// 2016/11/22 T.Arimoto #2803対応（E)
		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql.append("    '' AS RS_OROSI_BAITANKA_VL_KOURI, ");		// 卸売価単価(小売税)
		// 2016/10/25 Li.Sheng #2258 対応（E)

		sql.append("    RST.BUNRUI5_CD                AS RST_UNIT_CK, "); 					//分類５コード（体系）
		sql.append("    RST.BUNRUI2_CD                AS BUNRUI2_CD, ");					//分類２コード（体系）
		sql.append("    RSTK.BUNRUI5_CD               AS RSTK_UNIT_CK, "); 					//分類５コード（切替）
		sql.append("    RSTK.BUNRUI2_CD               AS RSTK_BUNRUI2_CD, ");				//分類２コード（切替）
		sql.append("    BUNRUI1_CD2.BUNRUI1_CD        AS BUNRUI1_CD2, "); 					//分類１コード（体系）
		sql.append("    UNIT_CK.CODE_CD               AS UNIT_CK, "); 						//分類５コード
		sql.append("    BUMON_CK.CODE_CD              AS BUMON_CK, ");						//分類１コード
		sql.append("    SYOHIN_KB_CK.CODE_CD          AS SYOHIN_KB_CK, ");					//商品区分
		sql.append("    ZEI_CK.CODE_CD                AS ZEI_CK, ");						//税区分
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    FREE_1_CK.CODE_CD             AS FREE_1_CK, ");						//任意区分１
		sql.append("    FREE_2_CK.CODE_CD             AS FREE_2_CK, ");						//任意区分２
		sql.append("    FREE_3_CK.CODE_CD             AS FREE_3_CK, ");						//任意区分３
		sql.append("    FREE_4_CK.CODE_CD             AS FREE_4_CK, ");						//任意区分４
		sql.append("    FREE_5_CK.CODE_CD             AS FREE_5_CK, ");						//任意区分５
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    EOS_CK.CODE_CD                AS EOS_CK,"); 						//EOS区分 存在チェック
		sql.append("    HACHU_TANI_CK.CODE_CD         AS HACHU_TANI_CK,"); 					//発注単位呼称
		sql.append("    HANBAI_TANI_CK.CODE_CD        AS HANBAI_TANI_CK,"); 				//販売単位呼称
		sql.append("    CASE_HACHU_CK.CODE_CD         AS CASE_HACHU_CK,"); 					//ケース発注区分
		sql.append("    TEIKAN_CK.CODE_CD             AS TEIKAN_CK,"); 						//定貫区分
		sql.append("    SOBA_SYOHIN_CK.CODE_CD        AS SOBA_SYOHIN_CK,"); 				//相場商品区分
		sql.append("    BIN1_CK.CODE_CD               AS BIN1_CK,"); 						//①便区分
		sql.append("    SIME_TIME1_CK.CODE_CD         AS SIME_TIME1_CK,"); 					//①締め時間
		sql.append("    BIN2_CK.CODE_CD               AS BIN2_CK,"); 						//②便区分
		sql.append("    SIME_TIME2_CK.CODE_CD         AS SIME_TIME2_CK,"); 					//②締め時間
		sql.append("    F_BIN_CK.CODE_CD              AS F_BIN_CK,"); 						//F便区分
		sql.append("    BUTURYU_CK.CODE_CD            AS BUTURYU_CK,"); 					//物流区分 存在チェック
		sql.append("    CENTER_ZAIKO_CK.CODE_CD       AS CENTER_ZAIKO_CK,"); 				//センター在庫区分
		sql.append("    CENTER_KYOYO_CK.CODE_CD       AS CENTER_KYOYO_CK,"); 				//センター許容区分
		sql.append("    CENTER_SYOMI_KIKAN_CK.CODE_CD AS CENTER_SYOMI_KIKAN_CK,"); 			//センター賞味期間区分
		sql.append("    CGC_HENPIN_CK.CODE_CD         AS CGC_HENPIN_CK,"); 					//CGC返品区分
		sql.append("    HANBAI_LIMIT_CK.CODE_CD       AS HANBAI_LIMIT_CK,"); 				//販売期限区分
		sql.append("    SYUZEI_HOKOKU_CK.CODE_CD      AS SYUZEI_HOKOKU_CK, "); 				//酒税報告分類
		sql.append("    PC_CK.CODE_CD                 AS PC_CK, ");							//PC発行区分
		sql.append("    DAISI_NO_NB_CK.CODE_CD        AS DAISI_NO_NB_CK, "); 				//台紙№
		sql.append("    UNIT_PRICE_TANI_CK.CODE_CD    AS UNIT_PRICE_TANI_CK, "); 	 		//ユニットプライス単位区分
		sql.append("    SIIRESAKI_CK.TORIHIKISAKI_CD  AS SIIRESAKI_CK,"); 					//仕入先コード存在チェック
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//sql.append("    MAKER_CK.CODE_CD              AS MAKER_CK,"); 						//メーカーコード存在チェック
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
		//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("    TAX_RATE_CK.TAX_RATE_KB       AS TAX_RATE_CK,"); 					//税率区分存在チェック
		sql.append("    RS.SYOHIN_CD                  AS S_SYOHIN_CD, ");
		sql.append("    TR.TMP_YUKO_DT              AS TMP_YUKO_DT ");

		sql.append("  FROM (SELECT S.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = S.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = S.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   		SYOHIN_CD  = S.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(TEKIYO_START_DT) ");
		sql.append("                  FROM R_TORIHIKISAKI ");
		sql.append("                 WHERE COMP_CD          = '").append(mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD).append("' ");
		sql.append("                   AND CHOAI_KB         = '").append(mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI).append("' ");
		sql.append("                   AND TORIHIKISAKI_CD  = TRIM(S.TR_SIIRESAKI_CD) ");
		sql.append("                   AND TORIKESHI_FG     = '").append(mst000101_ConstDictionary.DELETE_FG_NOR).append("' ");
		sql.append("                   AND TEKIYO_START_DT <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS TORIHIKISAKI_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT) ");
		sql.append("                  FROM R_TAX_RATE ");
		sql.append("                 WHERE TAX_RATE_KB = S.TR_TAX_RATE_KB 						AND		 ");
		sql.append("                   	   YUKO_DT    <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS TAX_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_SYOHIN S ");
		sql.append("            ON S.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND S.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND S.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND S.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND S.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("' ");
		sql.append("       ) TR ");

		// 商品マスタ
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN RS ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RS.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RS.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND RS.DELETE_FG  = '"+ DEL_FG_INAI + "' ");

		//商品分類体系
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI RST ");
		sql.append("    ON RST.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RST.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RST.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//商品分類体系切替
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI_KIRIKAE RSTK ");
		sql.append("    ON RSTK.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RSTK.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RSTK.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//分類１コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BUMON_CK ");
		sql.append("    ON BUMON_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   AND BUMON_CK.CODE_CD       = TR.TR_BUNRUI1_CD ");
		sql.append("   AND BUMON_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//分類１コード（体系）
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI BUNRUI1_CD2 ");
		sql.append("    ON BUNRUI1_CD2.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND BUNRUI1_CD2.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");

		//商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOHIN_KB_CK ");
		sql.append("    ON SYOHIN_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.COMMODITY_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOHIN_KB_CK.CODE_CD       = TR.TR_SYOHIN_KB ");
		sql.append("   AND SYOHIN_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//分類５コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF UNIT_CK ");
		sql.append("    ON UNIT_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		sql.append("   AND UNIT_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || TR.TR_BUNRUI5_CD ");
		sql.append("   AND UNIT_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//税区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF ZEI_CK ");
		sql.append("    ON ZEI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TAX_DIVISION, userLocal)).append("' ");
		sql.append("   AND ZEI_CK.CODE_CD       = TR.TR_ZEI_KB ");
		sql.append("   AND ZEI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_1_CK ");
		sql.append("    ON FREE_1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_1_KB, userLocal)).append("' ");
		sql.append("   AND FREE_1_CK.CODE_CD       = TR.TR_FREE_1_KB ");
		sql.append("   AND FREE_1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分２
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_2_CK ");
		sql.append("    ON FREE_2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_2_KB, userLocal)).append("' ");
		sql.append("   AND FREE_2_CK.CODE_CD       = TR.TR_FREE_2_KB ");
		sql.append("   AND FREE_2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分３
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_3_CK ");
		sql.append("    ON FREE_3_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_3_KB, userLocal)).append("' ");
		sql.append("   AND FREE_3_CK.CODE_CD       = TR.TR_FREE_3_KB ");
		sql.append("   AND FREE_3_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分４
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_4_CK ");
		sql.append("    ON FREE_4_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_4_KB, userLocal)).append("' ");
		sql.append("   AND FREE_4_CK.CODE_CD       = TR.TR_FREE_4_KB ");
		sql.append("   AND FREE_4_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分５
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_5_CK ");
		sql.append("    ON FREE_5_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_5_KB, userLocal)).append("' ");
		sql.append("   AND FREE_5_CK.CODE_CD       = TR.TR_FREE_5_KB ");
		sql.append("   AND FREE_5_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//税率区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_TAX_RATE TAX_RATE_CK ");
		sql.append("    ON TAX_RATE_CK.TAX_RATE_KB = TR.TR_TAX_RATE_KB ");
		sql.append("   AND TAX_RATE_CK.YUKO_DT     = TR.TAX_YUKO_DT ");
		sql.append("   AND TAX_RATE_CK.DELETE_FG   = '").append(DEL_FG_INAI).append("'");

		//EOS区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EOS_CK ");
		sql.append("    ON EOS_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
		sql.append("   AND EOS_CK.CODE_CD       = TR.TR_EOS_KB ");
		sql.append("   AND EOS_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//発注単位呼称
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HACHU_TANI_CK ");
		sql.append("    ON HACHU_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HACHU_TANI_DIVISION, userLocal)).append("' ");
		sql.append("   AND HACHU_TANI_CK.CODE_CD       = TR.TR_HACHU_TANI_NA ");
		sql.append("   AND HACHU_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//販売単位呼称
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HANBAI_TANI_CK ");
		sql.append("    ON HANBAI_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal)).append("' ");
		
		//#4158 Mod 2017.02.22 M.Son (S)
		//sql.append("   AND HANBAI_TANI_CK.CODE_CD       = TR.TR_HANBAI_TANI ");
		sql.append("   AND HANBAI_TANI_CK.CODE_CD       = TRIM(TR.TR_HANBAI_TANI) ");
		//#4158 Mod 2017.02.22 M.Son (E)
		sql.append("   AND HANBAI_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ケース発注区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CASE_HACHU_CK ");
		sql.append("    ON CASE_HACHU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CASE_HACHU_DIVISION, userLocal)).append("' ");
		sql.append("   AND CASE_HACHU_CK.CODE_CD       = TR.TR_CASE_HACHU_KB ");
		sql.append("   AND CASE_HACHU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定貫区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIKAN_CK ");
		sql.append("    ON TEIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.WEIGHT_OF_STANDARD, userLocal)).append("' ");
		sql.append("   AND TEIKAN_CK.CODE_CD       = TR.TR_TEIKAN_KB ");
		sql.append("   AND TEIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//相場商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SOBA_SYOHIN_CK ");
		sql.append("    ON SOBA_SYOHIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.MARKET_PRICE_COMMODITY, userLocal)).append("' ");
		sql.append("   AND SOBA_SYOHIN_CK.CODE_CD       = TR.TR_SOBA_SYOHIN_KB ");
		sql.append("   AND SOBA_SYOHIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//①便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN1_CK ");
		sql.append("    ON BIN1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN1_CK.CODE_CD       = TR.TR_BIN_1_KB ");
		sql.append("   AND BIN1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//①締め時間
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SIME_TIME1_CK ");
		sql.append("    ON SIME_TIME1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIME_TIME, userLocal)).append("' ");
		sql.append("   AND SIME_TIME1_CK.CODE_CD       = TRIM(TR.TR_SIME_TIME_1_QT) ");
		sql.append("   AND SIME_TIME1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//②便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN2_CK ");
		sql.append("    ON BIN2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN2_CK.CODE_CD       = TR.TR_BIN_2_KB ");
		sql.append("   AND BIN2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//②締め時間
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SIME_TIME2_CK ");
		sql.append("    ON SIME_TIME2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIME_TIME, userLocal)).append("' ");
		sql.append("   AND SIME_TIME2_CK.CODE_CD       = TRIM(TR.TR_SIME_TIME_2_QT) ");
		sql.append("   AND SIME_TIME2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//F便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF F_BIN_CK ");
		sql.append("    ON F_BIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FBIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND F_BIN_CK.CODE_CD       = TR.TR_F_BIN_KB ");
		sql.append("   AND F_BIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//物流区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BUTURYU_CK ");
		sql.append("    ON BUTURYU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DISTRIBUTION_DIVISION, userLocal)).append("' ");
		sql.append("   AND BUTURYU_CK.CODE_CD       = TR.TR_BUTURYU_KB ");
		sql.append("   AND BUTURYU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター在庫区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_ZAIKO_CK ");
		sql.append("    ON CENTER_ZAIKO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CENTER_STOCK_DIVISION, userLocal)).append("' ");
		sql.append("   AND CENTER_ZAIKO_CK.CODE_CD       = TR.TR_CENTER_ZAIKO_KB ");
		sql.append("   AND CENTER_ZAIKO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター許容区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_KYOYO_CK ");
		sql.append("    ON CENTER_KYOYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.NYUKA_KIJUN_TANI, userLocal)).append("' ");
		sql.append("   AND CENTER_KYOYO_CK.CODE_CD       = TR.TR_CENTER_KYOYO_KB ");
		sql.append("   AND CENTER_KYOYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター賞味期間区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_SYOMI_KIKAN_CK ");
		sql.append("    ON CENTER_SYOMI_KIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CENTER_SYOMI_KIKAN_DIVISION, userLocal)).append("' ");
		sql.append("   AND CENTER_SYOMI_KIKAN_CK.CODE_CD       = TR.TR_CENTER_SYOMI_KIKAN_KB ");
		sql.append("   AND CENTER_SYOMI_KIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//CGC返品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CGC_HENPIN_CK ");
		sql.append("    ON CGC_HENPIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CGC_HENPIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND CGC_HENPIN_CK.CODE_CD       = TR.TR_CGC_HENPIN_KB ");
		sql.append("   AND CGC_HENPIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//販売期限区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HANBAI_LIMIT_CK ");
		sql.append("    ON HANBAI_LIMIT_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_KIJUN_TANI, userLocal)).append("' ");
		sql.append("   AND HANBAI_LIMIT_CK.CODE_CD       = TR.TR_HANBAI_LIMIT_KB ");
		sql.append("   AND HANBAI_LIMIT_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//酒税報告分類
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYUZEI_HOKOKU_CK ");
		sql.append("    ON SYUZEI_HOKOKU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.LIQUOR_TAX_REPORT_DIVIDE, userLocal)).append("' ");
		sql.append("   AND SYUZEI_HOKOKU_CK.CODE_CD       = TRIM(TR.TR_SYUZEI_HOKOKU_KB) ");
		sql.append("   AND SYUZEI_HOKOKU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//PC発行区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF PC_CK ");
		sql.append("    ON PC_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PC_ISSUE_DIVISION, userLocal)).append("' ");
		sql.append("   AND PC_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || TR.TR_PC_KB ");
		sql.append("   AND PC_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//台紙№
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF DAISI_NO_NB_CK ");
		sql.append("    ON DAISI_NO_NB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_SEAL_KIND, userLocal)).append("' ");
		sql.append("   AND DAISI_NO_NB_CK.CODE_CD       =  TRIM(TR.TR_DAISI_NO_NB) ");
		sql.append("   AND DAISI_NO_NB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ユニットプライス単位区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF UNIT_PRICE_TANI_CK ");
		sql.append("    ON UNIT_PRICE_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT, userLocal)).append("' ");
		sql.append("   AND UNIT_PRICE_TANI_CK.CODE_CD       = TRIM(TR.TR_UNIT_PRICE_TANI_KB) ");
		sql.append("   AND UNIT_PRICE_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//仕入先
		sql.append("  LEFT JOIN ");
		sql.append("       R_TORIHIKISAKI SIIRESAKI_CK");
		sql.append("    ON SIIRESAKI_CK.COMP_CD            = '").append(mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD).append("' ");
		sql.append("   AND SIIRESAKI_CK.CHOAI_KB           = '").append(mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIHIKISAKI_CD    = TRIM(TR.TR_SIIRESAKI_CD) ");
		sql.append("   AND SIIRESAKI_CK.TEKIYO_START_DT    = TR.TORIHIKISAKI_YUKO_DT ");
		sql.append("   AND SIIRESAKI_CK.DELETE_FG          = '").append(DEL_FG_INAI).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIHIKI_TEISHI_KB = '").append(mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIKESHI_FG       = '").append(mst000101_ConstDictionary.DELETE_FG_NOR).append("' ");
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
//		//メーカーコード
//		sql.append("  LEFT JOIN ");
//		sql.append("       R_NAMECTF MAKER_CK ");
//		sql.append("    ON MAKER_CK.SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.MAKER_NAME).append("' ");
//		sql.append("   AND MAKER_CK.CODE_CD       = TRIM(TR.TR_KIKAKU_KANJI_2_NA) ");
//		sql.append("   AND MAKER_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");												//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");										//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");												//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");												//受付SEQ №

		return sql.toString();
	 }


	/**
	 * 更新データ取得SQL生成
	 * @throws
	 */
	private String getUpdateSelSQL() {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT TR.BY_NO, ");														//バイヤーno
		sql.append("       TR.TMP_YUKO_DT, ");													//TMP有効日
		sql.append("       TR.TORIKOMI_DT,");													//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");											//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");													//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ,");													//受付SEQ №
		sql.append("       TR.TR_BUNRUI1_CD               AS BUNRUI1_CD,");						//TR_分類１コード
		sql.append("       TR.TR_BUNRUI5_CD               AS BUNRUI5_CD,");						//TR_分類５コード
		sql.append("       TR.TR_SYOHIN_CD                AS SYOHIN_CD,");						//TR_商品コード
		sql.append("       TR.TR_YUKO_DT                  AS YUKO_DT,");						//TR_有効日
		sql.append("       TR.TR_SYOHIN_KB                AS SYOHIN_KB,");						//TR_商品区分
		sql.append("       TR.TR_SOBA_SYOHIN_KB           AS SOBA_SYOHIN_KB,");					//TR_相場商品区分
		sql.append("       TR.TR_TEIKAN_KB                AS TEIKAN_KB,");						//TR_定貫区分
		sql.append("       TR.TR_ZAIKO_SYOHIN_CD          AS ZAIKO_SYOHIN_CD,");				//TR_在庫用商品集計コード
		sql.append("       TR.TR_JYOHO_SYOHIN_CD          AS JYOHO_SYOHIN_CD,");				//TR_情報系用商品集計コード
		sql.append("       TR.TR_SYOHIN_WIDTH_QT          AS SYOHIN_WIDTH_QT,");				//TR_商品サイズ(幅)
		sql.append("       TR.TR_SYOHIN_HEIGHT_QT         AS SYOHIN_HEIGHT_QT,");				//TR_商品サイズ(高さ)
		sql.append("       TR.TR_SYOHIN_DEPTH_QT          AS SYOHIN_DEPTH_QT,");				//TR_商品サイズ(奥行き)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
//		sql.append("    	TR.TR_KIKAKU_KANJI_2_NA       AS MAKER_CD,");						//TR_メーカコード（漢字規格２を利用）
//		sql.append("    	''                            AS KIKAKU_KANJI_2_NA,");				//TR_漢字規格２（TR漢字規格２はメーカコードと利用する為、ここでは空文字セット）
		sql.append("       TR.TR_KIKAKU_KANJI_2_NA        AS KIKAKU_KANJI_2_NA,");				//TR_漢字規格２
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("       TR.TR_HINMEI_KANJI_NA          AS HINMEI_KANJI_NA,");				//TR_漢字品名
		sql.append("       TR.TR_KIKAKU_KANJI_NA          AS KIKAKU_KANJI_NA,");				//TR_漢字規格
		sql.append("       TR.TR_REC_HINMEI_KANJI_NA      AS REC_HINMEI_KANJI_NA,");			//TR_POSレシート品名(漢字)
		sql.append("       TR.TR_KIKAKU_KANA_2_NA         AS KIKAKU_KANA_2_NA,");				//TR_カナ規格２
		sql.append("       TR.TR_HINMEI_KANA_NA           AS HINMEI_KANA_NA,");					//TR_カナ品名
		sql.append("       TR.TR_KIKAKU_KANA_NA           AS KIKAKU_KANA_NA,");					//TR_カナ規格
		sql.append("       TR.TR_REC_HINMEI_KANA_NA       AS REC_HINMEI_KANA_NA,");				//TR_POSレシート品名(カナ)
		sql.append("       TR.TR_SIIRESAKI_CD             AS SIIRESAKI_CD,");					//TR_仕入先コード
		sql.append("       TR.TR_BUTURYU_KB               AS BUTURYU_KB,");						//TR_物流区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("       TR.TR_BAIKA_HAISHIN_FG         AS BAIKA_HAISHIN_FG,");				//TR_売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("       TR.TR_GENTANKA_VL              AS GENTANKA_VL,");					//TR_原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("       TR.TR_GENTANKA_NUKI_VL         AS GENTANKA_NUKI_VL,");				//TR_原価単価(税抜)
		sql.append("       TR.TR_BAITANKA_VL              AS BAITANKA_VL,");					//TR_売価単価
		sql.append("       TR.TR_BAITANKA_NUKI_VL         AS BAITANKA_NUKI_VL,");				//TR_売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("       TR.TR_MAKER_KIBO_KAKAKU_VL     AS MAKER_KIBO_KAKAKU_VL,");			//TR_メーカー希望小売り価格
		sql.append("       TR.TR_ZEI_KB                   AS ZEI_KB,");							//TR_税区分
		sql.append("       TR.TR_TAX_RATE_KB              AS TAX_RATE_KB,");					//TR_税率区分
		//TR_SYOHIN参照より取得
		sql.append("       TAX_RATE_CK.TAX_RATE_KB        AS TAX_RATE_CK, ");					//税率区分
		sql.append("       TAX_RATE_CK.TAX_RT             AS TAX_RT, ");					    //税率
		sql.append("       TR.TR_EOS_KB                   AS EOS_KB,");							//TR_EOS区分
		sql.append("       TR.TR_HACHU_TANI_NA            AS HACHU_TANI_NA,");					//TR_発注単位呼称
		sql.append("       TR.TR_HANBAI_TANI              AS HANBAI_TANI,");					//TR_販売単位呼称
		sql.append("       TR.TR_HACHUTANI_IRISU_QT       AS HACHUTANI_IRISU_QT,");				//TR_発注単位(入数)
		sql.append("       TR.TR_MAX_HACHUTANI_QT         AS MAX_HACHUTANI_QT,");				//TR_最大発注単位数
		sql.append("       TR.TR_SIME_TIME_1_QT           AS SIME_TIME_1_QT,");					//TR_①締め時間
		sql.append("       TR.TR_BIN_1_KB                 AS BIN_1_KB,");						//TR_①便区分
		sql.append("       TR.TR_SIME_TIME_2_QT           AS SIME_TIME_2_QT,");					//TR_②締め時間
		sql.append("       TR.TR_BIN_2_KB                 AS BIN_2_KB,");						//TR_②便区分
		sql.append("       TR.TR_F_BIN_KB                 AS F_BIN_KB,");						//TR_F便区分
		sql.append("       TR.TR_BARA_IRISU_QT            AS BARA_IRISU_QT,");					//TR_バラ入数
		sql.append("       TR.TR_CASE_HACHU_KB            AS CASE_HACHU_KB,");					//TR_ケース発注区分
		sql.append("       TR.TR_HANBAI_ST_DT             AS HANBAI_ST_DT,");					//TR_販売開始日
		sql.append("       TR.TR_HANBAI_ED_DT             AS HANBAI_ED_DT,");					//TR_販売終了日
		sql.append("       TR.TR_TEN_HACHU_ST_DT          AS TEN_HACHU_ST_DT,");				//TR_店舗発注開始日
		sql.append("       TR.TR_TEN_HACHU_ED_DT          AS TEN_HACHU_ED_DT,");				//TR_店舗発注終了日
		sql.append("       TR.TR_GOT_START_MM             AS GOT_START_MM,");					//TR_季節開始月日
		sql.append("       TR.TR_GOT_END_MM               AS GOT_END_MM,");						//TR_季節終了月日
		sql.append("       TR.TR_PLU_SEND_DT              AS PLU_SEND_DT,");					//TR_PLU送信日
		sql.append("       TR.TR_PC_KB                    AS PC_KB,");							//TR_PC発行区分
		sql.append("       TR.TR_DAISI_NO_NB              AS DAISI_NO_NB,");					//TR_台紙NO
		sql.append("       TR.TR_HANBAI_LIMIT_QT          AS HANBAI_LIMIT_QT,");				//TR_販売期限
		sql.append("       TR.TR_HANBAI_LIMIT_KB          AS HANBAI_LIMIT_KB,");				//TR_販売期限区分
		sql.append("       TR.TR_CENTER_KYOYO_DT          AS CENTER_KYOYO_DT,");				//TR_センター許容日
		sql.append("       TR.TR_CENTER_KYOYO_KB          AS CENTER_KYOYO_KB,");				//TR_センター許容区分
		sql.append("       TR.TR_UNIT_PRICE_TANI_KB       AS UNIT_PRICE_TANI_KB,");				//TR_ユニットプライス-単位区分
		sql.append("       TR.TR_UNIT_PRICE_NAIYORYO_QT   AS UNIT_PRICE_NAIYORYO_QT,");			//TR_ユニットプライス-内容量
		sql.append("       TR.TR_UNIT_PRICE_KIJUN_TANI_QT AS UNIT_PRICE_KIJUN_TANI_QT,");		//TR_ユニットプライス-基準単位量
		sql.append("       TR.TR_SYUZEI_HOKOKU_KB         AS SYUZEI_HOKOKU_KB,");				//TR_酒税報告分類
		sql.append("       TR.TR_SAKE_NAIYORYO_QT         AS SAKE_NAIYORYO_QT,");				//TR_酒内容量
		sql.append("       TR.TR_CGC_HENPIN_KB            AS CGC_HENPIN_KB,");					//TR_CGC返品区分
		sql.append("       TR.TR_MIN_ZAIKOSU_QT           AS MIN_ZAIKOSU_QT,");					//TR_最小在庫数
		sql.append("       TR.TR_MIN_ZAIKONISSU_QT        AS MIN_ZAIKONISSU_QT,");				//TR_最小在庫日数
		sql.append("       TR.TR_MAX_ZAIKOSU_QT           AS MAX_ZAIKOSU_QT,");					//TR_最大在庫数
		sql.append("       TR.TR_MAX_ZAIKONISSU_QT        AS MAX_ZAIKONISSU_QT,");				//TR_最大在庫日数
		sql.append("       TR.TR_ITF_CD                   AS ITF_CD,");							//TR_ITFコード
		sql.append("       TR.TR_NYUKA_SYOHIN_CD          AS NYUKA_SYOHIN_CD,");				//TR_入荷時商品コード
		sql.append("       TR.TR_NYUKA_SYOHIN_2_CD        AS NYUKA_SYOHIN_2_CD,");				//TR_入荷時商品コード２
		sql.append("       TR.TR_VENDER_MAKER_CD          AS VENDER_MAKER_CD,");				//TR_ベンダーメーカーコード
		sql.append("       TR.TR_CENTER_IRISU_QT          AS CENTER_IRISU_QT,");				//TR_最低入数
		sql.append("       TR.TR_CASE_IRISU_QT            AS CASE_IRISU_QT,");					//TR_ケース入り数
		sql.append("       TR.TR_CENTER_IRISU_2_QT        AS CENTER_IRISU_2_QT,");				//TR_梱り合せ数
		sql.append("       TR.TR_CENTER_ZAIKO_KB          AS CENTER_ZAIKO_KB,");				//TR_センター在庫区分
		sql.append("       TR.TR_CENTER_SYOMI_KIKAN_KB    AS CENTER_SYOMI_KIKAN_KB,");			//TR_センター賞味期間区分
		sql.append("       TR.TR_CENTER_SYOMI_KIKAN_DT    AS CENTER_SYOMI_KIKAN_DT,");			//TR_センター賞味期間
		sql.append("       TR.TR_CENTER_BARA_IRISU_QT     AS CENTER_BARA_IRISU_QT,");			//TR_センターバラ入数
		sql.append("       TR.TR_PACK_WEIGHT_QT           AS PACK_WEIGHT_QT,");					//TR_外箱重量
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("       TR.TR_FREE_1_KB                 AS FREE_1_KB,");						//TR_任意区分１
		sql.append("       TR.TR_FREE_2_KB                 AS FREE_2_KB,");						//TR_任意区分２
		sql.append("       TR.TR_FREE_3_KB                 AS FREE_3_KB,");						//TR_任意区分３
		sql.append("       TR.TR_FREE_4_KB                 AS FREE_4_KB,");						//TR_任意区分４
		sql.append("       TR.TR_FREE_5_KB                 AS FREE_5_KB,");						//TR_任意区分５
		sql.append("       TR.TR_COMMENT_1_TX              AS COMMENT_1_TX,");					//TR_コメント１
		sql.append("       TR.TR_COMMENT_2_TX              AS COMMENT_2_TX,");					//TR_コメント２
		sql.append("       TR.TR_FREE_CD                   AS FREE_CD,");						//TR_任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("       TR.SYUSEI_KB,");														//修正区分
		sql.append("       TR.SAKUSEI_GYO_NO,");												//作成元行
		sql.append("       TR.MST_MAINTE_FG,");													//マスタメンテフラグ
		sql.append("       TR.ALARM_MAKE_FG,");													//アラーム作成フラグ
		//Add 2015.07.13 DAI.BQ (S)
		sql.append("       TR.TR_SYOHIN_EIJI_NA           AS TR_SYOHIN_EIJI_NA, ");				//商品名（英字）
		sql.append("       TR.TR_COUNTRY_CD               AS TR_COUNTRY_CD, ");					//国コード
		//ADD 2015/08/17 THO.VT (S)
		sql.append("       TR.TR_MAKER_CD                 AS TR_MAKER_CD, ");
		//ADD 2015/08/17 THO.VT (E)
		//Add 2016.01.11 Huy.NT (S)
		sql.append("       TR.TR_HANBAI_HOHO_KB           AS TR_HANBAI_HOHO_KB, ");				//販売方法
		//Add 2016.01.11 Huy.NT (E)
		sql.append("       TR.TR_MIN_ZAIKO_QT             AS TR_MIN_ZAIKO_QT, ");				//最低在庫数量
		sql.append("       TR.TR_SECURITY_TAG_FG          AS TR_SECURITY_TAG_FG, ");			//セキュリティタグフラグ
		sql.append("       TR.TR_MEMBER_DISCOUNT_FG       AS TR_MEMBER_DISCOUNT_FG, ");			//メンバーディ割引対象外商品フラグ
		sql.append("       TR.TR_HAMPER_SYOHIN_FG         AS TR_HAMPER_SYOHIN_FG, ");			//ハンパー商品フラグ
		//Add 2015.07.13 DAI.BQ (E)
		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		sql.append("       TR.NENREI_SEIGEN_KB            AS NENREI_SEIGEN_KB, ");
		sql.append("       TR.NENREI					  AS NENREI, ");
		sql.append("       TR.KAN_KB					  AS KAN_KB, ");
		sql.append("       TR.HOSHOUKIN				      AS HOSHOUKIN, ");
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)
		//#1417対応 2016.06.03 M.Kanno (S)
		sql.append("       TR.TR_SYOHI_KIGEN_DT			  AS SYOHI_KIGEN_DT, ");
		//#1417対応 2016.06.03 M.Kanno (E)
		// #1900対応 2016.08.04 M.Akagi (S)
		sql.append("       TR.TR_SIIRE_ZEI_KB             AS SIIRE_ZEI_KB, ");					//TR_仕入税区分
		sql.append("       TR.TR_SIIRE_TAX_RATE_KB        AS SIIRE_TAX_RATE_KB, ");				//TR_仕入税率区分

		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		sql.append("      TR.TR_WARIBIKI_KB           AS WARIBIKI_KB , ");
		sql.append("      TR.TR_PB_SYOHIN_KB           AS PB_SYOHIN_KB , ");
		sql.append("      TR.TR_IYAKUHIN_KB           AS IYAKUHIN_KB , ");
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		
		// #6355 Add 2021/09/27 SIEU.D (S)
		sql.append("      TR.TR_SYOMI_KIGEN_NISU           AS SYOMI_KIGEN_NISU , "); 			// 賞味期限
		sql.append("      TR.TR_SHUKKA_KIGEN_NISU           AS SHUKKA_KIGEN_NISU , ");		// 出荷可能限度期日
		sql.append("      TR.TR_NYUKA_KIGEN_NISU           AS NYUKA_KIGEN_NISU , ");			// 入荷可能限度期日
		// #6355 Add 2021/09/27 SIEU.D (E)

		//税率区分（仕入）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS SIIRETAX_RATE_CK, ");
		//税率（仕入）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS SIIRETAX_RT, ");

		sql.append("       TR.TR_OROSI_ZEI_KB             AS OROSI_ZEI_KB, ");					//TR_卸税区分
		sql.append("       TR.TR_OROSI_TAX_RATE_KB        AS OROSI_TAX_RATE_KB, ");				//TR_卸税率区分

		//税率区分（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS OROSHITAX_RATE_CK, ");
		//税率（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = TR.TR_OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(TR.TR_YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS OROSHITAX_RT, ");

		sql.append("       TR.TR_OROSI_BAITANKA_VL        AS OROSI_BAITANKA_VL, ");				//TR_卸売価単価
		sql.append("       TR.TR_OROSI_BAITANKA_NUKI_VL   AS OROSI_BAITANKA_NUKI_VL, ");		//TR_卸売価単価（税抜）
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		sql.append("       TR.TR_MIN_HACHU_QT             AS MIN_HACHU_QT, ");					//TR_最低発注数
		sql.append("       TR.TR_HACHU_FUKA_FLG           AS HACHU_FUKA_FLG, ");				//TR_発注不可フラグ
		sql.append("       TR.TR_PER_TXT                  AS PER_TXT, ");						//TR_規格内容
		sql.append("       TR.TR_SYOHI_KIGEN_HYOJI_PATTER AS SYOHI_KIGEN_HYOJI_PATTER, ");		//TR_消費期限表示パターン
		sql.append("       TR.TR_PLU_HANEI_TIME           AS PLU_HANEI_TIME, ");				//TR_PLU反映時間
		sql.append("       TR.TR_LABEL_SEIBUN                AS LABEL_SEIBUN, ");					//TR_ラベル成分
		sql.append("       TR.TR_LABEL_HOKAN_HOHO            AS LABEL_HOKAN_HOHO, ");				//TR_ラベル保管方法
		sql.append("       TR.TR_LABEL_TUKAIKATA             AS LABEL_TUKAIKATA, ");				//TR_ラベル使い方
		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql.append("       TR.TR_OROSI_BAITANKA_VL_KOURI          AS TR_OROSI_BAITANKA_VL_KOURI, ");		// 卸売価単価(小売税)(新規登録)
		// 2016/10/25 Li.Sheng #2258 対応（E)
		// #1964対応 2016.08.22 M.Akagi (E)

		// 2017.01.12 M.Akagi #3531 (S)
		sql.append("       TR.TR_OLD_SYOHIN_CD             AS OLD_SYOHIN_CD, ");				//TR_旧商品コード
		// 2017.01.12 M.Akagi #3531 (E)
		sql.append("       RS.SYOHIN_CD                   AS S_SYOHIN_CD,");					//商品コード
		sql.append("       RS.YUKO_DT                     AS RS_YUKO_DT,");						//有効日
		sql.append("       RS.SYOHIN_KB                   AS RS_SYOHIN_KB,");					//商品区分
		sql.append("       RS.BUNRUI2_CD                  AS RS_BUNRUI2_CD,");					//分類５コード
		sql.append("       RS.BUNRUI5_CD                  AS RS_BUNRUI5_CD,");					//分類５コード
		sql.append("       RS.ZAIKO_SYOHIN_CD             AS RS_ZAIKO_SYOHIN_CD,");				//■在庫用商品集計コード
		sql.append("       RS.JYOHO_SYOHIN_CD             AS RS_JYOHO_SYOHIN_CD,");				//■情報系用商品集計コード
		sql.append("       RS.HINMEI_KANJI_NA             AS RS_HINMEI_KANJI_NA,");				//漢字品名
		sql.append("       RS.KIKAKU_KANJI_NA             AS RS_KIKAKU_KANJI_NA,");				//漢字規格
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
//		sql.append("       RS.MAKER_CD                     AS RS_MAKER_CD,");					//メーカコード
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("       RS.KIKAKU_KANJI_2_NA           AS RS_KIKAKU_KANJI_2_NA,");			//漢字規格２
		sql.append("       RS.REC_HINMEI_KANJI_NA         AS RS_REC_HINMEI_KANJI_NA,");			//POSレシート品名(漢字)
		sql.append("       RS.HINMEI_KANA_NA              AS RS_HINMEI_KANA_NA,");				//カナ品名
		sql.append("       RS.KIKAKU_KANA_NA              AS RS_KIKAKU_KANA_NA,");				//カナ規格
		sql.append("       RS.KIKAKU_KANA_2_NA            AS RS_KIKAKU_KANA_2_NA,"); 			//カナ規格２
		sql.append("       RS.REC_HINMEI_KANA_NA          AS RS_REC_HINMEI_KANA_NA,");			//POSレシート品名(カナ)
		sql.append("       RS.SYOHIN_WIDTH_QT             AS RS_SYOHIN_WIDTH_QT,");				//商品サイズ(幅)
		sql.append("       RS.SYOHIN_HEIGHT_QT            AS RS_SYOHIN_HEIGHT_QT,"); 			//商品サイズ(高さ)
		sql.append("       RS.SYOHIN_DEPTH_QT             AS RS_SYOHIN_DEPTH_QT,");				//商品サイズ(奥行き)
		sql.append("       RS.ZEI_KB                      AS RS_ZEI_KB,");						//税区分
		sql.append("       RS.TAX_RATE_KB                 AS RS_TAX_RATE_KB,");					//■税率区分
		////R_SYOHINより取得
		//税率区分
		sql.append("      ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					 AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("      )AS RS_TAX_RATE_CK, ");
		//税率
		sql.append("      ( ");
		sql.append("   			SELECT ");
		sql.append("   	       	  RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        	FROM ");
		sql.append("   	       	  R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        	WHERE ");
		sql.append("   	       	  RS_TAX_RATE_CK.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
		sql.append("    	   	  RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	   	  						   		SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            	FROM R_TAX_RATE ");
		sql.append("   						                	WHERE TAX_RATE_KB = RS.TAX_RATE_KB 	  					AND		");
		sql.append("   	                                       	      YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					 AND ");
		sql.append("              RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("       )AS RS_TAX_RT, ");

		// #1900対応 2016.08.04 M.Akagi (S)
		//仕入税率区分
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_SIIRETAX_RATE_CK, ");
		//仕入税率
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_SIIRETAX_RT, ");

		//税率区分（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  '" + DefaultYukoDt + "') ");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_OROSHITAX_RATE_CK, ");
		//税率（卸）
		sql.append("    ( ");
		sql.append("   		SELECT ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RT ");
		sql.append("        FROM ");
		sql.append("   	         R_TAX_RATE RS_TAX_RATE_CK ");
		sql.append("        WHERE ");
		sql.append("   	         RS_TAX_RATE_CK.TAX_RATE_KB = RS.OROSI_TAX_RATE_KB	AND ");
		sql.append("    	     RS_TAX_RATE_CK.YUKO_DT = ( ");
		sql.append("    	     						   	SELECT MAX(YUKO_DT) ");
		sql.append("   			  				            FROM R_TAX_RATE ");
		sql.append("   						                WHERE TAX_RATE_KB = RS.OROSI_TAX_RATE_KB 						AND		");
		sql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT, '" + DefaultYukoDt + "') 	");
		sql.append("   	                                    )					AND ");
		sql.append("             RS_TAX_RATE_CK.DELETE_FG = '").append(DEL_FG_INAI).append("'");
		sql.append("    )AS RS_OROSHITAX_RT, ");
		// #1900対応 2016.08.04 M.Akagi (E)

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		sql.append("       RS.BAIKA_HAISHIN_FG            AS RS_BAIKA_HAISHIN_FG,");			//売価配信区分
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		sql.append("       RS.GENTANKA_VL                 AS RS_GENTANKA_VL,");					//原価単価
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("       RS.GENTANKA_NUKI_VL            AS RS_GENTANKA_NUKI_VL,");			//原価単価(税抜)
		sql.append("       RS.BAITANKA_VL                 AS RS_BAITANKA_VL,");					//売価単価
		sql.append("       RS.BAITANKA_NUKI_VL            AS RS_BAITANKA_NUKI_VL,");			//売価単価(税抜)
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("       RS.MAKER_KIBO_KAKAKU_VL        AS RS_MAKER_KIBO_KAKAKU_VL,"); 		//メーカー希望小売り価格
		sql.append("       RS.SIIRESAKI_CD                AS RS_SIIRESAKI_CD,"); 				//仕入先コード
		sql.append("       RS.EOS_KB                      AS RS_EOS_KB,");						//EOS区分
		sql.append("       RS.HACHU_TANI_NA               AS RS_HACHU_TANI_NA,");				//発注単位呼称
		sql.append("       RS.HANBAI_TANI                 AS RS_HANBAI_TANI,");					//■販売単位呼称
		sql.append("       RS.HACHUTANI_IRISU_QT          AS RS_HACHUTANI_IRISU_QT,");			//発注単位(入数)
		sql.append("       RS.MAX_HACHUTANI_QT            AS RS_MAX_HACHUTANI_QT,"); 			//●最大発注単位数
		sql.append("       RS.CASE_HACHU_KB               AS RS_CASE_HACHU_KB,");				//■ケース発注区分
		sql.append("       RS.BARA_IRISU_QT               AS RS_BARA_IRISU_QT,");				//■バラ入数
		sql.append("       RS.TEN_HACHU_ST_DT             AS RS_TEN_HACHU_ST_DT,");				//店舗発注開始日
		sql.append("       RS.TEN_HACHU_ED_DT             AS RS_TEN_HACHU_ED_DT,");				//店舗発注終了日
		sql.append("       RS.HANBAI_ST_DT                AS RS_HANBAI_ST_DT,"); 				//販売開始日
		sql.append("       RS.HANBAI_ED_DT                AS RS_HANBAI_ED_DT,"); 				//販売終了日
		sql.append("       RS.TEIKAN_KB                   AS RS_TEIKAN_KB,");					//定貫区分
		sql.append("       RS.SOBA_SYOHIN_KB              AS RS_SOBA_SYOHIN_KB,");				//相場商品区分
		sql.append("       RS.BIN_1_KB                    AS RS_BIN_1_KB,"); 					//①便区分
		sql.append("       RS.SIME_TIME_1_QT              AS RS_SIME_TIME_1_QT,");				//①締め時間
		sql.append("       RS.BIN_2_KB                    AS RS_BIN_2_KB,"); 					//②便区分
		sql.append("       RS.SIME_TIME_2_QT              AS RS_SIME_TIME_2_QT,");				//②締め時間
		sql.append("       RS.F_BIN_KB                    AS RS_F_BIN_KB,"); 					//■F便区分
		sql.append("       RS.BUTURYU_KB                  AS RS_BUTURYU_KB,");					//物流区分
		sql.append("       RS.GOT_START_MM                AS RS_GOT_START_MM,"); 				//■季節開始月日
		sql.append("       RS.GOT_END_MM                  AS RS_GOT_END_MM,");					//■季節終了月日
		sql.append("       RS.CENTER_ZAIKO_KB             AS RS_CENTER_ZAIKO_KB,");				//センター在庫区分
		sql.append("       RS.NYUKA_SYOHIN_CD             AS RS_NYUKA_SYOHIN_CD,");				//入荷時商品コード
		sql.append("       RS.NYUKA_SYOHIN_2_CD           AS RS_NYUKA_SYOHIN_2_CD,");			//■入荷時商品コード２
		sql.append("       RS.ITF_CD                      AS RS_ITF_CD,");						//ITFコード
		sql.append("       RS.VENDER_MAKER_CD             AS RS_VENDER_MAKER_CD,");				//■ベンダーメーカーコード
		sql.append("       RS.CENTER_BARA_IRISU_QT        AS RS_CENTER_BARA_IRISU_QT,"); 		//■センターバラ入数
		sql.append("       RS.CENTER_IRISU_QT             AS RS_CENTER_IRISU_QT,");				//●最低入数
		sql.append("       RS.CASE_IRISU_QT               AS RS_CASE_IRISU_QT,");				//ケース入り数
		sql.append("       RS.CENTER_IRISU_2_QT           AS RS_CENTER_IRISU_2_QT,");			//■梱り合せ数
		sql.append("       RS.MIN_ZAIKOSU_QT              AS RS_MIN_ZAIKOSU_QT,");				//●最小在庫数
		sql.append("       RS.MAX_ZAIKOSU_QT              AS RS_MAX_ZAIKOSU_QT,");				//最大在庫数
		sql.append("       RS.MIN_ZAIKONISSU_QT           AS RS_MIN_ZAIKONISSU_QT,");			//■最小在庫日数
		sql.append("       RS.MAX_ZAIKONISSU_QT           AS RS_MAX_ZAIKONISSU_QT,");			//■最大在庫日数
		sql.append("       RS.CENTER_KYOYO_KB             AS RS_CENTER_KYOYO_KB,");				//■センター許容区分
		sql.append("       RS.CENTER_KYOYO_DT             AS RS_CENTER_KYOYO_DT,");				//センター許容日
		sql.append("       RS.CENTER_SYOMI_KIKAN_KB       AS RS_CENTER_SYOMI_KIKAN_KB,");		//■センター賞味期間区分
		sql.append("       RS.CENTER_SYOMI_KIKAN_DT       AS RS_CENTER_SYOMI_KIKAN_DT,");		//■センター賞味期間
		sql.append("       RS.CGC_HENPIN_KB               AS RS_CGC_HENPIN_KB,");				//■CGC返品区分
		sql.append("       RS.HANBAI_LIMIT_KB             AS RS_HANBAI_LIMIT_KB,");				//販売期限区分
		sql.append("       RS.HANBAI_LIMIT_QT             AS RS_HANBAI_LIMIT_QT,");				//販売期限
		sql.append("       RS.PLU_SEND_DT                 AS RS_PLU_SEND_DT,");					//PLU送信日
		sql.append("       RS.SYUZEI_HOKOKU_KB            AS RS_SYUZEI_HOKOKU_KB,"); 			//酒税報告分類
		sql.append("       RS.SAKE_NAIYORYO_QT            AS RS_SAKE_NAIYORYO_QT,"); 			//■酒内容量
		sql.append("       RS.PC_KB                       AS RS_PC_KB,");						//PC発行区分
		sql.append("       RS.DAISI_NO_NB                 AS RS_DAISI_NO_NB,");					//台紙NO
		sql.append("       RS.UNIT_PRICE_TANI_KB          AS RS_UNIT_PRICE_TANI_KB,");			//ユニットプライス-単位区分
		sql.append("       RS.UNIT_PRICE_NAIYORYO_QT      AS RS_UNIT_PRICE_NAIYORYO_QT,");		//ユニットプライス-内容量
		sql.append("       RS.UNIT_PRICE_KIJUN_TANI_QT    AS RS_UNIT_PRICE_KIJUN_TANI_QT,"); 	//ユニットプライス-基準単位量
		sql.append("       RS.PACK_WEIGHT_QT              AS RS_PACK_WEIGHT_QT,");				//外箱重量
		sql.append("       RS.SYSTEM_KB                   AS SYSTEM_KB,");						//システム区分
		sql.append("       RS.GYOSYU_KB                   AS GYOSYU_KB,");						//業種区分
		sql.append("       RS.KETASU_KB                   AS KETASU_KB,");						//桁数区分
//		sql.append("       RS.BUNRUI2_CD                  AS BUNRUI2_CD,");						//分類２コード
		sql.append("       RS.HINMOKU_CD                  AS HINMOKU_CD,");						//品目
		sql.append("       RS.SYOHIN_2_CD                 AS SYOHIN_2_CD,");					//商品コード２
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		sql.append("       RS.MAKER_CD                    AS MAKER_CD,"); 						//ＪＡＮメーカーコード
//  2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("       RS.E_SHOP_KB                   AS E_SHOP_KB,");						//Eショップ区分
		sql.append("       RS.PB_KB                       AS PB_KB,");							//PB区分
		sql.append("       RS.SUBCLASS_CD                 AS SUBCLASS_CD,");					//サブクラスコード
		sql.append("       RS.HAIFU_CD                    AS HAIFU_CD,"); 						//配布コード
		sql.append("       RS.TOSYO_BAIKA_VL              AS TOSYO_BAIKA_VL,");					//当初売価
		sql.append("       RS.PRE_GENTANKA_VL             AS PRE_GENTANKA_VL,");				//前回原価単価
		sql.append("       RS.PRE_BAITANKA_VL             AS PRE_BAITANKA_VL,");				//前回売価単価
		sql.append("       RS.TOKUBETU_GENKA_VL           AS TOKUBETU_GENKA_VL,");				//特別原価
		sql.append("       RS.DAIHYO_HAISO_CD             AS DAIHYO_HAISO_CD,");				//代表配送先コード
		sql.append("       RS.TEN_SIIRESAKI_KANRI_CD      AS TEN_SIIRESAKI_KANRI_CD,");			//店別仕入先管理コード
		sql.append("       RS.SIIRE_HINBAN_CD             AS SIIRE_HINBAN_CD,");				//仕入先品番
		sql.append("       RS.HACYU_SYOHIN_KB             AS HACYU_SYOHIN_KB,");				//発注商品コード区分
		sql.append("       RS.HACYU_SYOHIN_CD             AS HACYU_SYOHIN_CD,");				//発注商品コード
		sql.append("       RS.HANBAI_KIKAN_KB             AS HANBAI_KIKAN_KB,");				//販売期間区分
		sql.append("       RS.NOHIN_KIGEN_KB              AS NOHIN_KIGEN_KB,");					//納品期限区分
		sql.append("       RS.NOHIN_KIGEN_QT              AS NOHIN_KIGEN_QT,");					//納品期限
		sql.append("       RS.HACHU_PATTERN_1_KB          AS HACHU_PATTERN_1_KB,");				//①発注パターン区分
		sql.append("       RS.C_NOHIN_RTIME_1_KB          AS C_NOHIN_RTIME_1_KB,");				//①センタ納品リードタイム
		sql.append("       RS.TEN_NOHIN_RTIME_1_KB        AS TEN_NOHIN_RTIME_1_KB,"); 			//①店納品リードタイム
		sql.append("       RS.TEN_NOHIN_TIME_1_KB         AS TEN_NOHIN_TIME_1_KB,");			//①店納品時間帯
		sql.append("       RS.HACHU_PATTERN_2_KB          AS HACHU_PATTERN_2_KB,");				//②発注パターン区分
		sql.append("       RS.C_NOHIN_RTIME_2_KB          AS C_NOHIN_RTIME_2_KB,");				//②センタ納品リードタイム
		sql.append("       RS.TEN_NOHIN_RTIME_2_KB        AS TEN_NOHIN_RTIME_2_KB,"); 			//②店納品リードタイム
		sql.append("       RS.TEN_NOHIN_TIME_2_KB         AS TEN_NOHIN_TIME_2_KB,");			//②店納品時間帯
		sql.append("       RS.BIN_3_KB                    AS BIN_3_KB,"); 						//③便区分
		sql.append("       RS.HACHU_PATTERN_3_KB          AS HACHU_PATTERN_3_KB,");				//③発注パターン区分
		sql.append("       RS.SIME_TIME_3_QT              AS SIME_TIME_3_QT,");					//③締め時間
		sql.append("       RS.C_NOHIN_RTIME_3_KB          AS C_NOHIN_RTIME_3_KB,");				//③センタ納品リードタイム
		sql.append("       RS.TEN_NOHIN_RTIME_3_KB        AS TEN_NOHIN_RTIME_3_KB,"); 			//③店納品リードタイム
		sql.append("       RS.TEN_NOHIN_TIME_3_KB         AS TEN_NOHIN_TIME_3_KB,");			//③店納品時間帯
		sql.append("       RS.C_NOHIN_RTIME_KB            AS C_NOHIN_RTIME_KB,"); 				//センタ納品リードタイム
		sql.append("       RS.YUSEN_BIN_KB                AS YUSEN_BIN_KB,"); 					//優先便区分
		sql.append("       RS.GOT_MUJYOKEN_FG             AS GOT_MUJYOKEN_FG,");				//GOT無条件表示対象
		sql.append("       RS.HACHU_TEISI_KB              AS HACHU_TEISI_KB,");					//発注停止区分
		sql.append("       RS.NOHIN_SYOHIN_CD             AS NOHIN_SYOHIN_CD,");				//納品商品コード
		sql.append("       RS.GTIN_CD                     AS GTIN_CD,");						//GTINコード
		sql.append("       RS.ZAIKO_CENTER_CD             AS ZAIKO_CENTER_CD,");				//在庫センターコード
		sql.append("       RS.ZAIKO_HACHU_SAKI            AS ZAIKO_HACHU_SAKI,"); 				//在庫補充発注先
		sql.append("       RS.CENTER_WEIGHT_QT            AS CENTER_WEIGHT_QT,"); 				//センター重量
		sql.append("       RS.PACK_WIDTH_QT               AS PACK_WIDTH_QT,");					//外箱サイズ幅
		sql.append("       RS.PACK_HEIGTH_QT              AS PACK_HEIGTH_QT,");					//外箱サイズ高さ
		sql.append("       RS.PACK_DEPTH_QT               AS PACK_DEPTH_QT,");					//外箱サイズ奥行き
		sql.append("       RS.CENTER_HACHUTANI_KB         AS CENTER_HACHUTANI_KB,");			//センター発注単位区分
		sql.append("       RS.CENTER_HACHUTANI_QT         AS CENTER_HACHUTANI_QT,");			//センター発注単位数
		sql.append("       RS.KIJUN_ZAIKOSU_QT            AS KIJUN_ZAIKOSU_QT,"); 				//基準在庫日数
		sql.append("       RS.TEN_GROUPNO_CD              AS TEN_GROUPNO_CD,");					//店グルーピングNO(物流）
		sql.append("       RS.TC_JYOUHO_NA                AS TC_JYOUHO_NA,"); 					//TC情報
		sql.append("       RS.NOHIN_ONDO_KB               AS NOHIN_ONDO_KB,");					//納品温度帯
		sql.append("       RS.YOKOMOTI_KB                 AS YOKOMOTI_KB,");					//横もち区分
		sql.append("       RS.SHINAZOROE_KB               AS SHINAZOROE_KB,");					//品揃区分
		sql.append("       RS.HONBU_ZAI_KB                AS HONBU_ZAI_KB,"); 					//本部在庫区分
		sql.append("       RS.TEN_ZAIKO_KB                AS TEN_ZAIKO_KB,"); 					//店在庫区分
		sql.append("       RS.HANBAI_SEISAKU_KB           AS HANBAI_SEISAKU_KB,");				//販売政策区分
		sql.append("       RS.HENPIN_NB                   AS HENPIN_NB,");						//返品契約書NO
		sql.append("       RS.HENPIN_GENKA_VL             AS HENPIN_GENKA_VL,");				//返品原価
		sql.append("       RS.KEYPLU_FG                   AS KEYPLU_FG,");						//キーPLU対象
		sql.append("       RS.PLU_KB                      AS PLU_KB,");							//PLU区分
		sql.append("       RS.TAG_HYOJI_BAIKA_VL          AS TAG_HYOJI_BAIKA_VL,");				//タグ表示売価
		sql.append("       RS.KESHI_BAIKA_VL              AS KESHI_BAIKA_VL,");					//消札売価
		sql.append("       RS.YORIDORI_KB                 AS YORIDORI_KB,");					//よりどり種類
		sql.append("       RS.YORIDORI_VL                 AS YORIDORI_VL,");					//よりどり価格
		sql.append("       RS.YORIDORI_QT                 AS YORIDORI_QT,");					//よりどり数量
		sql.append("       RS.BLAND_CD                    AS BLAND_CD,"); 						//ブランドコード
		sql.append("       RS.SEASON_CD                   AS SEASON_CD,");						//シーズンコード
		sql.append("       RS.HUKUSYU_CD                  AS HUKUSYU_CD,");						//服種コード
		sql.append("       RS.STYLE_CD                    AS STYLE_CD,"); 						//スタイルコード
		sql.append("       RS.SCENE_CD                    AS SCENE_CD,"); 						//シーンコード
		sql.append("       RS.SEX_CD                      AS SEX_CD,");							//性別コード
		sql.append("       RS.AGE_CD                      AS AGE_CD,");							//年代コード
		sql.append("       RS.GENERATION_CD               AS GENERATION_CD,");					//世代コード
		sql.append("       RS.SOZAI_CD                    AS SOZAI_CD,"); 						//素材コード
		sql.append("       RS.PATTERN_CD                  AS PATTERN_CD,");						//パターンコード
		sql.append("       RS.ORIAMI_CD                   AS ORIAMI_CD,");						//織り編みコード
		sql.append("       RS.HUKA_KINO_CD                AS HUKA_KINO_CD,"); 					//付加機能コード
		sql.append("       RS.SODE_CD                     AS SODE_CD,");						//袖丈コード
		sql.append("       RS.SIZE_CD                     AS SIZE_CD,");						//サイズコード
		sql.append("       RS.COLOR_CD                    AS COLOR_CD,"); 						//カラーコード
		sql.append("       RS.KEIYAKU_SU_QT               AS KEIYAKU_SU_QT,");					//契約数
		sql.append("       RS.KEIYAKU_PATTERN_KB          AS KEIYAKU_PATTERN_KB,");				//契約パターン
		sql.append("       RS.KEIYAKU_ST_DT               AS KEIYAKU_ST_DT,");					//契約開始期間
		sql.append("       RS.KEIYAKU_ED_DT               AS KEIYAKU_ED_DT,");					//契約終了期間
		sql.append("       RS.KUMISU_KB                   AS KUMISU_KB,");						//組数区分
		sql.append("       RS.NEFUDA_KB                   AS NEFUDA_KB,");						//値札区分
		sql.append("       RS.NEFUDA_UKEWATASI_DT         AS NEFUDA_UKEWATASI_DT,");			//値札受渡日
		sql.append("       RS.NEFUDA_UKEWATASI_KB         AS NEFUDA_UKEWATASI_KB,");			//値札受渡方法
		sql.append("       RS.SYOHI_KIGEN_KB              AS SYOHI_KIGEN_KB,");					//■消費期限区分
		//#1417対応 2016.06.03 M.Kanno (S)
		//sql.append("       RS.SYOHI_KIGEN_DT              AS SYOHI_KIGEN_DT,");					//消費期限
		//#1417対応 2016.06.03 M.Kanno (E)
		sql.append("       RS.DAICHO_TENPO_KB             AS DAICHO_TENPO_KB,");				//商品台帳(店舗)
		sql.append("       RS.DAICHO_HONBU_KB             AS DAICHO_HONBU_KB,");				//商品台帳(本部)
		sql.append("       RS.DAICHO_SIIRESAKI_KB         AS DAICHO_SIIRESAKI_KB,");			//商品台帳(仕入先)
		sql.append("       RS.TANA_NO_NB                  AS TANA_NO_NB,");						//棚NO
		sql.append("       RS.DAN_NO_NB                   AS DAN_NO_NB,");						//段NO
		sql.append("       RS.REBATE_FG                   AS REBATE_FG,");						//リベート対象フラグ
		sql.append("       RS.MARK_GROUP_CD               AS MARK_GROUP_CD,");					//マークグループ
		sql.append("       RS.MARK_CD                     AS MARK_CD,");						//マークコード
		sql.append("       RS.YUNYUHIN_KB                 AS YUNYUHIN_KB,");					//輸入品区分
		sql.append("       RS.SANTI_CD                    AS SANTI_CD,"); 						//原産国/産地コード
		sql.append("       RS.D_ZOKUSEI_1_NA              AS D_ZOKUSEI_1_NA,");					//大属性１
		sql.append("       RS.S_ZOKUSEI_1_NA              AS S_ZOKUSEI_1_NA,");					//小属性１
		sql.append("       RS.D_ZOKUSEI_2_NA              AS D_ZOKUSEI_2_NA,");					//大属性２
		sql.append("       RS.S_ZOKUSEI_2_NA              AS S_ZOKUSEI_2_NA,");					//小属性２
		sql.append("       RS.D_ZOKUSEI_3_NA              AS D_ZOKUSEI_3_NA,");					//大属性３
		sql.append("       RS.S_ZOKUSEI_3_NA              AS S_ZOKUSEI_3_NA,");					//小属性３
		sql.append("       RS.D_ZOKUSEI_4_NA              AS D_ZOKUSEI_4_NA,");					//大属性４
		sql.append("       RS.S_ZOKUSEI_4_NA              AS S_ZOKUSEI_4_NA,");					//小属性４
		sql.append("       RS.D_ZOKUSEI_5_NA              AS D_ZOKUSEI_5_NA,");					//大属性５
		sql.append("       RS.S_ZOKUSEI_5_NA              AS S_ZOKUSEI_5_NA,");					//小属性５
		sql.append("       RS.D_ZOKUSEI_6_NA              AS D_ZOKUSEI_6_NA,");					//大属性６
		sql.append("       RS.S_ZOKUSEI_6_NA              AS S_ZOKUSEI_6_NA,");					//小属性６
		sql.append("       RS.D_ZOKUSEI_7_NA              AS D_ZOKUSEI_7_NA,");					//大属性７
		sql.append("       RS.S_ZOKUSEI_7_NA              AS S_ZOKUSEI_7_NA,");					//小属性７
		sql.append("       RS.D_ZOKUSEI_8_NA              AS D_ZOKUSEI_8_NA,");					//大属性８
		sql.append("       RS.S_ZOKUSEI_8_NA              AS S_ZOKUSEI_8_NA,");					//小属性８
		sql.append("       RS.D_ZOKUSEI_9_NA              AS D_ZOKUSEI_9_NA,");					//大属性９
		sql.append("       RS.S_ZOKUSEI_9_NA              AS S_ZOKUSEI_9_NA,");					//小属性９
		sql.append("       RS.D_ZOKUSEI_10_NA             AS D_ZOKUSEI_10_NA,");				//大属性１０
		sql.append("       RS.S_ZOKUSEI_10_NA             AS S_ZOKUSEI_10_NA,");				//小属性１０
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("       RS.FREE_1_KB                   AS RS_FREE_1_KB,");					//任意区分１
		sql.append("       RS.FREE_2_KB                   AS RS_FREE_2_KB,");					//任意区分２
		sql.append("       RS.FREE_3_KB                   AS RS_FREE_3_KB,");					//任意区分３
		sql.append("       RS.FREE_4_KB                   AS RS_FREE_4_KB,");					//任意区分４
		sql.append("       RS.FREE_5_KB                   AS RS_FREE_5_KB,");					//任意区分５
		sql.append("       RS.COMMENT_1_TX                AS RS_COMMENT_1_TX,");				//コメント１
		sql.append("       RS.COMMENT_2_TX                AS RS_COMMENT_2_TX,");				//コメント２
		sql.append("       RS.FREE_CD                     AS RS_FREE_CD,");						//任意コード
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("       RS.FUJI_SYOHIN_KB              AS FUJI_SYOHIN_KB,");					//F商品区分
		sql.append("       RS.COMMENT_TX                  AS COMMENT_TX,");						//コメント
		sql.append("       RS.AUTO_DEL_KB                 AS AUTO_DEL_KB,");					//自動削除対象区分
		sql.append("       RS.MST_SIYOFUKA_KB             AS MST_SIYOFUKA_KB,");				//マスタ使用不可区分
		sql.append("       RS.HAIBAN_FG                   AS HAIBAN_FG,");						//廃番実施フラグ
		sql.append("       RS.SINKI_TOROKU_DT             AS SINKI_TOROKU_DT,");				//新規登録日
		sql.append("       RS.HENKO_DT                    AS HENKO_DT,"); 						//変更日
		sql.append("       RS.SYOKAI_TOROKU_TS            AS SYOKAI_TOROKU_TS,"); 				//初回登録日
		sql.append("       RS.SYOKAI_USER_ID              AS SYOKAI_USER_ID,");					//初回登録社員ID
		sql.append("       RS.INSERT_TS                   AS INSERT_TS,");						//作成年月日
		sql.append("       RS.INSERT_USER_ID              AS INSERT_USER_ID,");					//作成者ID
		sql.append("       RS.UPDATE_TS                   AS UPDATE_TS,");						//更新年月日
		sql.append("       RS.UPDATE_USER_ID              AS UPDATE_USER_ID,");					//更新者ID
		sql.append("       RS.BATCH_UPDATE_TS             AS BATCH_UPDATE_TS,");				//■バッチ更新年月日
		sql.append("       RS.BATCH_UPDATE_ID             AS BATCH_UPDATE_ID,");				//■バッチ更新者ID
		sql.append("       RS.DELETE_FG                   AS DELETE_FG, ");						//削除フラグ

		// 2016/10/25 T.Arimoto #2256対応（S)
		sql.append("       RS.CUR_GENERATION_YUKO_DT      AS CUR_GENERATION_YUKO_DT, ");		//現世代 有効日
		sql.append("       RS.CUR_GENERATION_GENTANKA_VL  AS CUR_GENERATION_GENTANKA_VL, ");	//現世代 原単価
		sql.append("       RS.ONE_GENERATION_YUKO_DT      AS ONE_GENERATION_YUKO_DT, ");		//１世代前 有効日
		sql.append("       RS.ONE_GENERATION_GENTANKA_VL  AS ONE_GENERATION_GENTANKA_VL, ");	//１世代前 原単価
		sql.append("       RS.TWO_GENERATION_YUKO_DT      AS TWO_GENERATION_YUKO_DT, ");		//２世代前 有効日
		sql.append("       RS.TWO_GENERATION_GENTANKA_VL  AS TWO_GENERATION_GENTANKA_VL, ");	//２世代前 原単価
		sql.append("       PRE_RS.TWO_GENERATION_YUKO_DT      AS PRE_TWO_GENERATION_YUKO_DT, ");		//１世代前レコードの２世代前 有効日
		sql.append("       PRE_RS.TWO_GENERATION_GENTANKA_VL  AS PRE_TWO_GENERATION_GENTANKA_VL, ");	//１世代前レコードの２世代前 原単価
		// 2016/10/25 T.Arimoto #2256対応（E)

		// #1900対応 2016.08.04 M.Akagi (S)
		sql.append("       RS.SIIRE_ZEI_KB                AS RS_SIIRE_ZEI_KB, ");				//仕入税区分
		sql.append("       RS.SIIRE_TAX_RATE_KB           AS RS_SIIRE_TAX_RATE_KB, ");			//仕入税率区分
		sql.append("       RS.OROSI_ZEI_KB                AS RS_OROSI_ZEI_KB, ");				//卸税区分
		sql.append("       RS.OROSI_TAX_RATE_KB           AS RS_OROSI_TAX_RATE_KB, ");			//卸税率区分
		sql.append("       RS.OROSI_BAITANKA_VL           AS RS_OROSI_BAITANKA_VL, ");			//卸売価単価
		sql.append("       RS.OROSI_BAITANKA_NUKI_VL      AS RS_OROSI_BAITANKA_NUKI_VL, ");		//卸売価単価（税抜）
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		sql.append("       RS.MIN_HACHU_QT                AS RS_MIN_HACHU_QT, ");				//最低発注数
		sql.append("       RS.HACHU_FUKA_FLG              AS RS_HACHU_FUKA_FLG, ");				//発注不可フラグ
		sql.append("       RS.PER_TXT                     AS RS_PER_TXT, ");					//規格内容
		sql.append("       RS.SYOHI_KIGEN_HYOJI_PATTER    AS RS_SYOHI_KIGEN_HYOJI_PATTER, ");	//消費期限表示パターン
		sql.append("       RS.PLU_HANEI_TIME              AS RS_PLU_HANEI_TIME, ");				//PLU反映時間
		// #1964対応 2016.08.22 M.Akagi (E)
		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql.append("       RS.OROSI_BAITANKA_VL_KOURI          AS RS_OROSI_BAITANKA_VL_KOURI, ");		// 卸売価単価(小売税)(新規登録)
		// 2016/10/25 Li.Sheng #2258 対応（E)
// 2017/02/27 Add Li.Sheng #4205 対応（S)
		sql.append("       RS.NENREI_SEIGEN_KB          AS RS_NENREI_SEIGEN_KB, ");		// 年齢制限区分
		sql.append("       RS.NENREI          AS RS_NENREI, ");		// 年齢
		sql.append("       RS.KAN_KB          AS RS_KAN_KB, ");		// 瓶区分
		sql.append("       RS.HOSHOUKIN          AS RS_HOSHOUKIN, ");		// 保証金
// 2017/02/27 Add Li.Sheng #4205 対応（E)
		sql.append("       RST.BUNRUI5_CD                 AS RST_UNIT_CK, "); 					//分類５コード（体系）
		sql.append("       RST.BUNRUI2_CD                 AS BUNRUI2_CD, ");					//分類２コード（体系）
		sql.append("       RSTK.BUNRUI5_CD                AS RSTK_UNIT_CK, "); 					//分類５コード（切替）
		sql.append("       RSTK.BUNRUI2_CD                AS RSTK_BUNRUI2_CD, ");				//分類２コード（切替）
		sql.append("       BUNRUI1_CD2.BUNRUI1_CD         AS BUNRUI1_CD2, "); 					//分類１コード（体系）
		sql.append("       UNIT_CK.CODE_CD                AS UNIT_CK, "); 						//分類５コード
		sql.append("       BUMON_CK.CODE_CD               AS BUMON_CK, ");						//分類１コード
		sql.append("       SYOHIN_KB_CK.CODE_CD           AS SYOHIN_KB_CK, ");					//商品区分
		sql.append("       ZEI_CK.CODE_CD                 AS ZEI_CK, ");						//税区分
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		sql.append("       FREE_1_CK.CODE_CD              AS FREE_1_CK, ");						//任意区分１
		sql.append("       FREE_2_CK.CODE_CD              AS FREE_2_CK, ");						//任意区分２
		sql.append("       FREE_3_CK.CODE_CD              AS FREE_3_CK, ");						//任意区分３
		sql.append("       FREE_4_CK.CODE_CD              AS FREE_4_CK, ");						//任意区分４
		sql.append("       FREE_5_CK.CODE_CD              AS FREE_5_CK, ");						//任意区分５
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		sql.append("       EOS_CK.CODE_CD                 AS EOS_CK,"); 						//EOS区分 存在チェック
		sql.append("       HACHU_TANI_CK.CODE_CD          AS HACHU_TANI_CK,");	 				//販売単位呼称
		sql.append("       HANBAI_TANI_CK.CODE_CD         AS HANBAI_TANI_CK,"); 				//販売単位呼称
		sql.append("       CASE_HACHU_CK.CODE_CD          AS CASE_HACHU_CK,"); 					//ケース発注区分
		sql.append("       TEIKAN_CK.CODE_CD              AS TEIKAN_CK,"); 						//定貫区分
		sql.append("       SOBA_SYOHIN_CK.CODE_CD         AS SOBA_SYOHIN_CK,"); 				//相場商品区分
		sql.append("       BIN1_CK.CODE_CD                AS BIN1_CK,"); 						//①便区分
		sql.append("       SIME_TIME1_CK.CODE_CD          AS SIME_TIME1_CK,"); 					//①締め時間
		sql.append("       BIN2_CK.CODE_CD                AS BIN2_CK,"); 						//②便区分
		sql.append("       SIME_TIME2_CK.CODE_CD          AS SIME_TIME2_CK,"); 					//②締め時間
		sql.append("       F_BIN_CK.CODE_CD               AS F_BIN_CK,"); 						//F便区分
		sql.append("       BUTURYU_CK.CODE_CD             AS BUTURYU_CK,"); 					//物流区分 存在チェック
		sql.append("       CENTER_ZAIKO_CK.CODE_CD        AS CENTER_ZAIKO_CK,"); 				//センター在庫区分
		sql.append("       CENTER_KYOYO_CK.CODE_CD        AS CENTER_KYOYO_CK,"); 				//センター許容区分
		sql.append("       CENTER_SYOMI_KIKAN_CK.CODE_CD  AS CENTER_SYOMI_KIKAN_CK,"); 			//センター賞味期間区分
		sql.append("       CGC_HENPIN_CK.CODE_CD          AS CGC_HENPIN_CK,"); 					//CGC返品区分
		sql.append("       HANBAI_LIMIT_CK.CODE_CD        AS HANBAI_LIMIT_CK,"); 				//販売期限区分
		sql.append("       SYUZEI_HOKOKU_CK.CODE_CD       AS SYUZEI_HOKOKU_CK, "); 				//酒税報告分類
		sql.append("       PC_CK.CODE_CD                  AS PC_CK, ");							//PC発行区分
		sql.append("       DAISI_NO_NB_CK.CODE_CD         AS DAISI_NO_NB_CK, "); 				//台紙№
		sql.append("       UNIT_PRICE_TANI_CK.CODE_CD     AS UNIT_PRICE_TANI_CK, "); 	 		//ユニットプライス単位区分
		sql.append("       SIIRESAKI_CK.TORIHIKISAKI_CD   AS SIIRESAKI_CK,"); 					//仕入先コード存在チェック
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
		// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//sql.append("       MAKER_CK.CODE_CD               AS MAKER_CK,"); 						//メーカーコード存在チェック
		// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		sql.append("       RS.SYOHIN_CD                   AS S_SYOHIN_CD, ");
		sql.append("       RS.YUKO_DT                     AS S_YUKO_DT, ");
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		sql.append("       RS.OLD_SYOHIN_CD               AS RS_OLD_SYOHIN_CD, ");
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)
		sql.append("       TR.TMP_YUKO_DT ");
		// 2016/11/22 T.Arimoto #2803対応（S)
		sql.append("       , RSA.SYOHIN_EIJI_NA           AS RSA_SYOHIN_EIJI_NA ");				//商品名（英字）
		sql.append("       , RSA.COUNTRY_CD               AS RSA_COUNTRY_CD ");					//国コード
		sql.append("       , RSA.MAKER_CD                 AS RSA_MAKER_CD ");					//メーカーコード
		sql.append("       , RSA.HANBAI_HOHO_KB           AS RSA_HANBAI_HOHO_KB ");				//販売方法
		sql.append("       , RSA.MIN_ZAIKO_QT             AS RSA_MIN_ZAIKO_QT ");				//最低在庫数量
		sql.append("       , RSA.SECURITY_TAG_FG          AS RSA_SECURITY_TAG_FG ");			//セキュリティタグフラグ
		sql.append("       , RSA.MEMBER_DISCOUNT_FG       AS RSA_MEMBER_DISCOUNT_FG ");			//メンバーディ割引対象外商品フラグ
		sql.append("       , RSA.HAMPER_SYOHIN_FG         AS RSA_HAMPER_SYOHIN_FG ");			//ハンパー商品フラグ
		sql.append("       , RSA.LABEL_SEIBUN             AS RSA_LABEL_SEIBUN ");				// ラベル成分
		sql.append("       , RSA.LABEL_HOKAN_HOHO         AS RSA_LABEL_HOKAN_HOHO ");			// ラベル保管方法
		sql.append("       , RSA.LABEL_TUKAIKATA          AS RSA_LABEL_TUKAIKATA ");			// ラベル使い方
		// 2016/11/22 T.Arimoto #2803対応（E)
		// #33102 Mod 2025.04.09 HUY.LTH (S)
		sql.append("       , RS.WARIBIKI_KB          AS RS_WARIBIKI_KB ");
		sql.append("       , RS.IYAKUHIN_KB          AS RS_IYAKUHIN_KB ");
		sql.append("       , RS.PB_SYOHIN_KB          AS RS_PB_SYOHIN_KB ");
		// #33102 Mod 2025.04.09 HUY.LTH (E)

		sql.append("  FROM (SELECT S.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = S.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = S.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                  		SYOHIN_CD  = S.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(TEKIYO_START_DT) ");
		sql.append("                  FROM R_TORIHIKISAKI ");
		sql.append("                 WHERE COMP_CD          = '").append(mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD).append("' ");
		sql.append("                   AND CHOAI_KB         = '").append(mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI).append("' ");
		sql.append("                   AND TORIHIKISAKI_CD  = S.TR_SIIRESAKI_CD ");
		sql.append("                   AND TORIKESHI_FG     = '").append(mst000101_ConstDictionary.DELETE_FG_NOR).append("' ");
		sql.append("                   AND TEKIYO_START_DT <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) TORIHIKISAKI_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT) ");
		sql.append("                  FROM R_TAX_RATE ");
		sql.append("                 WHERE TAX_RATE_KB = S.TR_TAX_RATE_KB 						AND		 ");
		sql.append("                       YUKO_DT    <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS TAX_YUKO_DT ");

		// 2016/10/25 T.Arimoto #2256対応（S)
		sql.append("               ,( SELECT MAX(YUKO_DT) ");
		sql.append("                  FROM R_SYOHIN PRE_RSS");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE PRE_RSS.BUNRUI1_CD = S.TR_BUNRUI1_CD   AND ");
		sql.append("                 WHERE ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                       PRE_RSS.SYOHIN_CD  =  S.TR_SYOHIN_CD   AND ");
		sql.append("                       PRE_RSS.YUKO_DT    <  S.TR_YUKO_DT     AND ");
		sql.append("                       PRE_RSS.DELETE_FG  = '" + DEL_FG_INAI + "' ");
		sql.append("               ) AS PRE_RS_YUKO_DT ");
		// 2016/10/25 T.Arimoto #2256対応（E)

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_SYOHIN S ");
		sql.append("            ON S.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND S.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND S.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND S.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND S.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("' ");
		sql.append("       ) TR ");

		// 商品マスタ
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN RS ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RS.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RS.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND RS.DELETE_FG  = '"+ DEL_FG_INAI + "' ");

		// 2016/11/22 T.Arimoto #2803対応（S)
		// 商品マスタ ASN
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_ASN RSA ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RSA.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RSA.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 RSA.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RSA.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND RSA.DELETE_FG  = '"+ DEL_FG_INAI + "' ");
		// 2016/11/22 T.Arimoto #2803対応（E)

		//商品分類体系
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI RST ");
		sql.append("    ON RST.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RST.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RST.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//商品分類体系切替
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI_KIRIKAE RSTK ");
		sql.append("    ON RSTK.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RSTK.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RSTK.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//分類１コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BUMON_CK ");
		sql.append("    ON BUMON_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   AND BUMON_CK.CODE_CD       = TR.TR_BUNRUI1_CD ");
		sql.append("   AND BUMON_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//分類１コード（体系）
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI BUNRUI1_CD2 ");
		sql.append("    ON BUNRUI1_CD2.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND BUNRUI1_CD2.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");

		//商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOHIN_KB_CK ");
		sql.append("    ON SYOHIN_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.COMMODITY_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOHIN_KB_CK.CODE_CD       = TR.TR_SYOHIN_KB ");
		sql.append("   AND SYOHIN_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//分類５コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF UNIT_CK ");
		sql.append("    ON UNIT_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		sql.append("   AND UNIT_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || TR.TR_BUNRUI5_CD ");
		sql.append("   AND UNIT_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//税区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF ZEI_CK ");
		sql.append("    ON ZEI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TAX_DIVISION, userLocal)).append("' ");
		sql.append("   AND ZEI_CK.CODE_CD       = TR.TR_ZEI_KB ");
		sql.append("   AND ZEI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_1_CK ");
		sql.append("    ON FREE_1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_1_KB, userLocal)).append("' ");
		sql.append("   AND FREE_1_CK.CODE_CD       = TR.TR_FREE_1_KB ");
		sql.append("   AND FREE_1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分２
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_2_CK ");
		sql.append("    ON FREE_2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_2_KB, userLocal)).append("' ");
		sql.append("   AND FREE_2_CK.CODE_CD       = TR.TR_FREE_2_KB ");
		sql.append("   AND FREE_2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分３
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_3_CK ");
		sql.append("    ON FREE_3_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_3_KB, userLocal)).append("' ");
		sql.append("   AND FREE_3_CK.CODE_CD       = TR.TR_FREE_3_KB ");
		sql.append("   AND FREE_3_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分４
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_4_CK ");
		sql.append("    ON FREE_4_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_4_KB, userLocal)).append("' ");
		sql.append("   AND FREE_4_CK.CODE_CD       = TR.TR_FREE_4_KB ");
		sql.append("   AND FREE_4_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//任意区分５
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF FREE_5_CK ");
		sql.append("    ON FREE_5_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FREE_5_KB, userLocal)).append("' ");
		sql.append("   AND FREE_5_CK.CODE_CD       = TR.TR_FREE_5_KB ");
		sql.append("   AND FREE_5_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//税率区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_TAX_RATE TAX_RATE_CK ");
		sql.append("    ON TAX_RATE_CK.TAX_RATE_KB = TR.TR_TAX_RATE_KB ");
		sql.append("   AND TAX_RATE_CK.YUKO_DT     = TR.TAX_YUKO_DT ");
		sql.append("   AND TAX_RATE_CK.DELETE_FG   = '").append(DEL_FG_INAI).append("'");

		//EOS区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EOS_CK ");
		sql.append("    ON EOS_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
		sql.append("   AND EOS_CK.CODE_CD       = TR.TR_EOS_KB ");
		sql.append("   AND EOS_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//発注単位呼称
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HACHU_TANI_CK ");
		sql.append("    ON HACHU_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HACHU_TANI_DIVISION, userLocal)).append("' ");
		sql.append("   AND HACHU_TANI_CK.CODE_CD       = TR.TR_HACHU_TANI_NA ");
		sql.append("   AND HACHU_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//販売単位呼称
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HANBAI_TANI_CK ");
		sql.append("    ON HANBAI_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_TANI_DIVISION, userLocal)).append("' ");
		//#4158 Mod 2017.02.22 M.Son (S)
		//sql.append("   AND HANBAI_TANI_CK.CODE_CD       = TR.TR_HANBAI_TANI ");
		sql.append("   AND HANBAI_TANI_CK.CODE_CD       = TRIM(TR.TR_HANBAI_TANI) ");
		//#4158 Mod 2017.02.22 M.Son (E)
		sql.append("   AND HANBAI_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ケース発注区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CASE_HACHU_CK ");
		sql.append("    ON CASE_HACHU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CASE_HACHU_DIVISION, userLocal)).append("' ");
		sql.append("   AND CASE_HACHU_CK.CODE_CD       = TR.TR_CASE_HACHU_KB ");
		sql.append("   AND CASE_HACHU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定貫区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIKAN_CK ");
		sql.append("    ON TEIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.WEIGHT_OF_STANDARD, userLocal)).append("' ");
		sql.append("   AND TEIKAN_CK.CODE_CD       = TR.TR_TEIKAN_KB ");
		sql.append("   AND TEIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//相場商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SOBA_SYOHIN_CK ");
		sql.append("    ON SOBA_SYOHIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.MARKET_PRICE_COMMODITY, userLocal)).append("' ");
		sql.append("   AND SOBA_SYOHIN_CK.CODE_CD       = TR.TR_SOBA_SYOHIN_KB ");
		sql.append("   AND SOBA_SYOHIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//①便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN1_CK ");
		sql.append("    ON BIN1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN1_CK.CODE_CD       = TR.TR_BIN_1_KB ");
		sql.append("   AND BIN1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//①締め時間
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SIME_TIME1_CK ");
		sql.append("    ON SIME_TIME1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIME_TIME, userLocal)).append("' ");
		sql.append("   AND SIME_TIME1_CK.CODE_CD       = TRIM(TR.TR_SIME_TIME_1_QT) ");
		sql.append("   AND SIME_TIME1_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//②便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN2_CK ");
		sql.append("    ON BIN2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN2_CK.CODE_CD       = TR.TR_BIN_2_KB ");
		sql.append("   AND BIN2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//②締め時間
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SIME_TIME2_CK ");
		sql.append("    ON SIME_TIME2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIME_TIME, userLocal)).append("' ");
		sql.append("   AND SIME_TIME2_CK.CODE_CD       = TRIM(TR.TR_SIME_TIME_2_QT) ");
		sql.append("   AND SIME_TIME2_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//F便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF F_BIN_CK ");
		sql.append("    ON F_BIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.FBIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND F_BIN_CK.CODE_CD       = TR.TR_F_BIN_KB ");
		sql.append("   AND F_BIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//物流区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BUTURYU_CK ");
		sql.append("    ON BUTURYU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DISTRIBUTION_DIVISION, userLocal)).append("' ");
		sql.append("   AND BUTURYU_CK.CODE_CD       = TR.TR_BUTURYU_KB ");
		sql.append("   AND BUTURYU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター在庫区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_ZAIKO_CK ");
		sql.append("    ON CENTER_ZAIKO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CENTER_STOCK_DIVISION, userLocal)).append("' ");
		sql.append("   AND CENTER_ZAIKO_CK.CODE_CD       = TR.TR_CENTER_ZAIKO_KB ");
		sql.append("   AND CENTER_ZAIKO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター許容区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_KYOYO_CK ");
		sql.append("    ON CENTER_KYOYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.NYUKA_KIJUN_TANI, userLocal)).append("' ");
		sql.append("   AND CENTER_KYOYO_CK.CODE_CD       = TR.TR_CENTER_KYOYO_KB ");
		sql.append("   AND CENTER_KYOYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//センター賞味期間区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CENTER_SYOMI_KIKAN_CK ");
		sql.append("    ON CENTER_SYOMI_KIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CENTER_SYOMI_KIKAN_DIVISION, userLocal)).append("' ");
		sql.append("   AND CENTER_SYOMI_KIKAN_CK.CODE_CD       = TR.TR_CENTER_SYOMI_KIKAN_KB ");
		sql.append("   AND CENTER_SYOMI_KIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//CGC返品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF CGC_HENPIN_CK ");
		sql.append("    ON CGC_HENPIN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CGC_HENPIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND CGC_HENPIN_CK.CODE_CD       = TR.TR_CGC_HENPIN_KB ");
		sql.append("   AND CGC_HENPIN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//販売期限区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HANBAI_LIMIT_CK ");
		sql.append("    ON HANBAI_LIMIT_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HANBAI_KIJUN_TANI, userLocal)).append("' ");
		sql.append("   AND HANBAI_LIMIT_CK.CODE_CD       = TR.TR_HANBAI_LIMIT_KB ");
		sql.append("   AND HANBAI_LIMIT_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//酒税報告分類
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYUZEI_HOKOKU_CK ");
		sql.append("    ON SYUZEI_HOKOKU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.LIQUOR_TAX_REPORT_DIVIDE, userLocal)).append("' ");
		sql.append("   AND SYUZEI_HOKOKU_CK.CODE_CD       = TRIM(TR.TR_SYUZEI_HOKOKU_KB) ");
		sql.append("   AND SYUZEI_HOKOKU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//PC発行区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF PC_CK ");
		sql.append("    ON PC_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PC_ISSUE_DIVISION, userLocal)).append("' ");
		sql.append("   AND PC_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || TR.TR_PC_KB ");
		sql.append("   AND PC_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//台紙№
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF DAISI_NO_NB_CK ");
		sql.append("    ON DAISI_NO_NB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_SEAL_KIND, userLocal)).append("' ");
		sql.append("   AND DAISI_NO_NB_CK.CODE_CD       =  TRIM(TR.TR_DAISI_NO_NB) ");
		sql.append("   AND DAISI_NO_NB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ユニットプライス単位区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF UNIT_PRICE_TANI_CK ");
		sql.append("    ON UNIT_PRICE_TANI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.UNIT_PRICE_UNIT_AMOUNT, userLocal)).append("' ");
		sql.append("   AND UNIT_PRICE_TANI_CK.CODE_CD       = TRIM(TR.TR_UNIT_PRICE_TANI_KB) ");
		sql.append("   AND UNIT_PRICE_TANI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//仕入先
		sql.append("  LEFT JOIN ");
		sql.append("       R_TORIHIKISAKI SIIRESAKI_CK");
		sql.append("    ON SIIRESAKI_CK.COMP_CD            = '").append(mst000101_ConstDictionary.TORIHIKISAKI_COMP_CD).append("' ");
		sql.append("   AND SIIRESAKI_CK.CHOAI_KB           = '").append(mst000101_ConstDictionary.CHOAI_DIVISION_SIIRESAKI).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIHIKISAKI_CD    = TR.TR_SIIRESAKI_CD ");
		sql.append("   AND SIIRESAKI_CK.TEKIYO_START_DT    = TR.TORIHIKISAKI_YUKO_DT ");
		sql.append("   AND SIIRESAKI_CK.DELETE_FG          = '").append(DEL_FG_INAI).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIHIKI_TEISHI_KB = '").append(mst000101_ConstDictionary.TORIHIKI_TEISHI_DIVISION_YUKO).append("' ");
		sql.append("   AND SIIRESAKI_CK.TORIKESHI_FG       = '").append(mst000101_ConstDictionary.DELETE_FG_NOR).append("' ");
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//メーカーコード
		//sql.append("  LEFT JOIN ");
		//sql.append("       R_NAMECTF MAKER_CK ");
		//sql.append("    ON MAKER_CK.SYUBETU_NO_CD = '").append(mst000101_ConstDictionary.MAKER_NAME).append("' ");
		//sql.append("   AND MAKER_CK.CODE_CD       = TRIM(TR.TR_KIKAKU_KANJI_2_NA) ");
		//sql.append("   AND MAKER_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)

		// 2016/10/25 T.Arimoto #2256対応（S)
		// 1世代前の商品マスタ
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN PRE_RS ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON PRE_RS.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND PRE_RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 PRE_RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND PRE_RS.YUKO_DT    = TR.PRE_RS_YUKO_DT ");
		// 2016/10/25 T.Arimoto #2256対応（E)

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");												//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");										//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");												//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");												//受付SEQ №

		return sql.toString();
	}

	/**
	 * 削除データ取得SQL生成
	 * @throws
	 */
	private String getDeleteSelSQL() {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT TR.TORIKOMI_DT         AS TORIKOMI_DT,"); 				//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU AS EXCEL_FILE_SYUBETSU,");		//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO         AS UKETSUKE_NO,"); 				//受付NO
		sql.append("       TR.UKETSUKE_SEQ        AS UKETSUKE_SEQ,");				//受付SEQNO
		sql.append("       TR.TR_BUNRUI1_CD       AS BUNRUI1_CD,"); 				//部門コード
		sql.append("       TR.TR_BUNRUI5_CD       AS BUNRUI5_CD,"); 				//ユニットコード
		sql.append("       TR.TR_SYOHIN_CD        AS SYOHIN_CD,"); 					//商品コード
		sql.append("       TR.TR_YUKO_DT          AS YUKO_DT,"); 					//有効日
		sql.append("       TR.SYUSEI_KB           AS SYUSEI_KB,"); 					//修正区分
		sql.append("       TR.SAKUSEI_GYO_NO      AS SAKUSEI_GYO_NO,"); 			//作成元行
		sql.append("       TR.MST_MAINTE_FG       AS MST_MAINTE_FG, "); 			//マスタメンテフラグ
		sql.append("       TR.ALARM_MAKE_FG       AS ALARM_MAKE_FG, "); 			//アラーム作成フラグ
		sql.append("       TR.INSERT_TS           AS MST_MAINTE_FG, "); 			//作成年月日
		sql.append("       TR.INSERT_USER_ID      AS MST_MAINTE_FG, "); 			//作成者ID
		sql.append("       TR.UPDATE_TS           AS MST_MAINTE_FG, "); 			//更新年月日
		sql.append("       TR.UPDATE_USER_ID      AS MST_MAINTE_FG, "); 			//更新者ID
		sql.append("       RST.BUNRUI2_CD         AS BUNRUI2_CD, ");
		sql.append("       TR.BY_NO               AS BY_NO, ");						//バイヤーNO
		sql.append("       RS.YUKO_DT             AS RS_YUKO_DT, ");

		sql.append("       BUNRUI1_CD2.BUNRUI1_CD AS BUNRUI1_CD2, ");
		sql.append("       UNIT_CK.CODE_CD        AS UNIT_CK, ");
		sql.append("       RST.BUNRUI5_CD         AS RST_UNIT_CK, ");
		sql.append("       RSTK.BUNRUI2_CD        AS RSTK_BUNRUI2_CD, ");
		sql.append("       RSTK.BUNRUI5_CD        AS RSTK_UNIT_CK, ");
		sql.append("       RS.SYOHIN_CD           AS S_SYOHIN_CD, ");
		sql.append("       BUMON_CK.CODE_CD       AS BUMON_CK ");

		sql.append("  FROM (SELECT S.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = S.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = S.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = S.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(S.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_SYOHIN S ");
		sql.append("            ON S.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND S.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND S.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND S.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND S.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.DELETE.getCode()).append("' ");
		sql.append("       ) TR ");

		// 商品マスタ
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN RS ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RS.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RS.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND RS.DELETE_FG  = '"+ DEL_FG_INAI + "' ");

		//商品分類体系
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI RST ");
		sql.append("    ON RST.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RST.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RST.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//商品分類体系切替
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI_KIRIKAE RSTK ");
		sql.append("    ON RSTK.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND RSTK.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");
		sql.append("   AND RSTK.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");

		//分類１コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BUMON_CK ");
		sql.append("    ON BUMON_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   AND BUMON_CK.CODE_CD       = TR.TR_BUNRUI1_CD ");
		sql.append("   AND BUMON_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//分類１コード（体系）
		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN_TAIKEI BUNRUI1_CD2 ");
		sql.append("    ON BUNRUI1_CD2.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		sql.append("   AND BUNRUI1_CD2.BUNRUI5_CD = TR.TR_BUNRUI5_CD ");

		//分類５コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF UNIT_CK ");
		sql.append("    ON UNIT_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		sql.append("   AND UNIT_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || TR.TR_BUNRUI5_CD ");
		sql.append("   AND UNIT_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");							//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");					//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");							//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");							//受付SEQ №

		return sql.toString();
	}

	/**
	 * 予約取消データ取得SQL生成
	 * @throws
	 */
	private String getYoyakuTorikesiSelSQL() {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT TTS.BY_NO, ");							//バイヤーno
		sql.append("       TR.TORIKOMI_DT,");						//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");				//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");						//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ,");						//受付SEQ №
		sql.append("       TR.TR_BUNRUI1_CD AS BUNRUI1_CD, ");		//TR_分類１コード
		sql.append("       TR.TR_SYOHIN_CD  AS SYOHIN_CD, ");		//TR_商品コード
		sql.append("       TR.TR_YUKO_DT    AS YUKO_DT,");			//TR_有効日
		sql.append("       TR.SYUSEI_KB,");							//修正区分
		sql.append("       TR.SAKUSEI_GYO_NO,");					//作成元行
		sql.append("       TR.MST_MAINTE_FG,");						//マスタメンテフラグ
		sql.append("       TR.ALARM_MAKE_FG,");						//アラーム作成フラグ
		sql.append("       TR.ALARM_MAKE_FG,");						//アラーム作成フラグ
		sql.append("       RS.SYOHIN_CD AS S_SYOHIN_CD ");			//商品コード

		sql.append("  FROM TR_TOROKU_SYONIN TTS ");

		sql.append(" INNER JOIN ");
		sql.append("       TR_SYOHIN TR ");
		sql.append("    ON TR.TORIKOMI_DT         = TTS.TORIKOMI_DT ");
		sql.append("   AND TR.EXCEL_FILE_SYUBETSU = TTS.EXCEL_FILE_SYUBETSU ");
		sql.append("   AND TR.UKETSUKE_NO         = TTS.UKETSUKE_NO ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN RS ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RS.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 RS.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RS.YUKO_DT    = TR.TR_YUKO_DT ");

		sql.append(" WHERE TTS.TOROKU_SYONIN_FG = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("   AND TR.MST_MAINTE_FG     = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("   AND TR.SYUSEI_KB         = '").append(mst006701_SyuseiKbDictionary.CANCEL.getCode()).append("' ");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");												//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");										//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");												//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");												//受付SEQ №

		return sql.toString();
	}

	/**
	 * 商品マスタデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedSyohinSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		//分類１コード
		sql1.append("BUNRUI1_CD,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("SYOHIN_CD,");
		sql2.append(" ?,");

		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		//旧商品コード
		sql1.append("OLD_SYOHIN_CD,");
		sql2.append(" ?,");
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

		//有効日
		sql1.append("yuko_dt,");
		sql2.append(" ?,");

		//システム区分
		sql1.append("system_kb,");
		sql2.append(" '").append(mst000611_SystemKbDictionary.G.getCode()).append("',");

		//業種区分
		sql1.append("gyosyu_kb,");
		sql2.append(" '").append(mst000601_GyoshuKbDictionary.GRO.getCode()).append("',");

		//商品区分
		sql1.append("SYOHIN_KB,");
//		sql2.append("'1',");
		sql2.append("?,");

		//桁数区分
		sql1.append("KETASU_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//分類２コード
		sql1.append("BUNRUI2_CD,");
		sql2.append(" ?,");

		//分類５コード
		sql1.append("BUNRUI5_CD,");
		sql2.append(" ?,");

		//品目
		sql1.append("HINMOKU_CD,");
		sql2.append("CAST(NULL AS CHAR),");

		//商品コード２
		sql1.append("SYOHIN_2_CD,");
		sql2.append("?,");

		//在庫用商品集計コード
		sql1.append("ZAIKO_SYOHIN_CD,");
		sql2.append(" ?,");

		//情報系用商品集計コード
		sql1.append("JYOHO_SYOHIN_CD,");
		sql2.append(" ?,");

		//JANメーカーコード
		sql1.append("MAKER_CD,");
		sql2.append(" CAST(NULL AS CHAR),");
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//sql2.append(" ?,");
//		sql2.append(" CAST(NULL AS CHAR),");
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		//漢字品名
		sql1.append("hinmei_kanji_na,");
		sql2.append(" ?,");

		//漢字規格
		sql1.append("kikaku_kanji_na,");
		sql2.append(" ?,");

		//漢字規格2
		sql1.append("KIKAKU_KANJI_2_NA,");
		sql2.append(" ?,");

		//POSレシート品名(漢字)
		sql1.append("REC_HINMEI_KANJI_NA,");
		sql2.append("?,");

		//カナ品名
		sql1.append("hinmei_kana_na,");
		sql2.append(" ?,");

		//カナ規格
		sql1.append("kikaku_kana_na,");
		sql2.append(" ?,");

		//カナ規格2
		sql1.append("KIKAKU_KANA_2_NA,");
		sql2.append(" ?,");

		//POSレシート品名(カナ)
		sql1.append("REC_HINMEI_KANA_NA,");
		sql2.append("?,");

		//商品サイズ(幅)
		sql1.append("SYOHIN_WIDTH_QT,");
//		sql2.append(" 0,");
		sql2.append("?,");

		//商品サイズ(高さ)
		sql1.append("SYOHIN_HEIGHT_QT,");
//		sql2.append(" 0,");
		sql2.append("?,");

		//商品サイズ(奥行き)
		sql1.append("SYOHIN_DEPTH_QT,");
//		sql2.append(" 0,");
		sql2.append("?,");

		//Eショップ区分
		sql1.append("e_shop_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//PB区分
		sql1.append("PB_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//サブクラスコード
		sql1.append("SUBCLASS_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//配布コード
		sql1.append("HAIFU_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//税区分
		sql1.append("zei_kb,");
//		sql2.append(" '1',");
		sql2.append("?,");

		//税率区分
		sql1.append("TAX_RATE_KB,");
		sql2.append(" ?,");

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		//売価配信区分
		sql1.append("baika_haishin_fg,");
		sql2.append(" ?,");
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)

		//原価単価
		sql1.append("gentanka_vl,");
		sql2.append(" ?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//原価単価(税抜)
		sql1.append("gentanka_nuki_vl,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//売価単価
		sql1.append("baitanka_vl,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//売価単価(税抜)
		sql1.append("baitanka_nuki_vl,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//	当初売価
		sql1.append("tosyo_baika_vl,");
		sql2.append(" ?,");

		//前回原価単価
		sql1.append("PRE_GENTANKA_VL,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//前回売価単価
		sql1.append("PRE_BAITANKA_VL,");
		sql2.append("CAST(NULL AS NUMERIC),");

		//特別原価
		sql1.append("TOKUBETU_GENKA_VL,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//メーカー希望小売り価格
		sql1.append("maker_kibo_kakaku_vl,");
		sql2.append(" ?,");

		//仕入先コード
		sql1.append("siiresaki_cd,");
		sql2.append(" ?,");

		//代表配送先コード
		sql1.append("daihyo_haiso_cd,");
		sql2.append("  CAST(NULL AS CHAR),");

		//店別仕入先管理コード
		sql1.append("ten_siiresaki_kanri_cd,");
//		sql2.append("  ?,");
		sql2.append(" CAST(NULL AS CHAR),");

		//仕入先品番
		sql1.append("siire_hinban_cd,");
//		sql2.append("  ?,");
		sql2.append(" CAST(NULL AS CHAR),");

		//発注商品コード区分
		sql1.append("hacyu_syohin_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//発注商品コード
		sql1.append("HACYU_SYOHIN_CD,");
		sql2.append(" ?,");

		//EOS区分
		sql1.append("EOS_KB,");
		sql2.append("  ?,");

		//発注単位呼称
		sql1.append("HACHU_TANI_NA,");
		sql2.append(" ?,");

		//販売単位呼称
		sql1.append("HANBAI_TANI,");
		sql2.append(" ?,");

		//発注単位(入数)
		sql1.append("hachutani_irisu_qt,");
		sql2.append(" ?,");

		//最大発注単位数
		sql1.append("max_hachutani_qt,");
		sql2.append(" ?,");

		//ケース発注区分
		sql1.append("CASE_HACHU_KB,");
		sql2.append(" ?,");

		//バラ入数
		sql1.append("BARA_IRISU_QT,");
		sql2.append(" ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt,");
		sql2.append(" ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt,");
		sql2.append(" ?,");

		//販売開始日
		sql1.append("hanbai_st_dt,");
		sql2.append(" ?,");

		//販売終了日
		sql1.append("hanbai_ed_dt,");
		sql2.append(" ?,");

		//販売期間区分
		sql1.append("HANBAI_KIKAN_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//定貫区分
		sql1.append("TEIKAN_KB,");
//		sql2.append("  CAST(NULL AS CHAR),");
		sql2.append("?,");

		//相場商品区分
		sql1.append("soba_syohin_kb,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//納品期限区分
		sql1.append("NOHIN_KIGEN_KB,");
		sql2.append("   CAST(NULL AS CHAR),");

		//納品期限
		sql1.append("NOHIN_KIGEN_QT,");
		sql2.append("CAST(NULL AS NUMERIC) ,");

		//①便区分
		sql1.append("BIN_1_KB,");
//		sql2.append("CAST(NULL AS CHAR),");
		sql2.append("?,");

		//①発注パターン区分
		sql1.append("HACHU_PATTERN_1_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//①締め時間
		sql1.append("SIME_TIME_1_QT,");
//		sql2.append("CAST(NULL AS CHAR),");
		sql2.append("?,");

		//①センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_1_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//①店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_1_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//①店納品時間帯
		sql1.append("TEN_NOHIN_TIME_1_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//②便区分
		sql1.append("BIN_2_KB,");
//		sql2.append("CAST(NULL AS CHAR),");
		sql2.append("?,");

		//②発注パターン区分
		sql1.append("HACHU_PATTERN_2_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//②締め時間
		sql1.append("SIME_TIME_2_QT,");
//		sql2.append("CAST(NULL AS CHAR),");
		sql2.append("?,");

		//②センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_2_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//②店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_2_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//②店納品時間帯
		sql1.append("TEN_NOHIN_TIME_2_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//③便区分
		sql1.append("BIN_3_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//③発注パターン区分
		sql1.append("HACHU_PATTERN_3_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//③締め時間
		sql1.append("SIME_TIME_3_QT,");
		sql2.append("CAST(NULL AS CHAR),");

		//③センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_3_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//③店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_3_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//③店納品時間帯
		sql1.append("TEN_NOHIN_TIME_3_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//優先便区分
		sql1.append("YUSEN_BIN_KB, ");
		sql2.append(" CAST(NULL AS CHAR),");

		//F便区分
		sql1.append("F_BIN_KB,");
		sql2.append(" ?,");

		//物流区分
		sql1.append("buturyu_kb,");
		sql2.append(" ?,");

		//GOT無条件表示対象
		sql1.append("GOT_MUJYOKEN_FG,");
		sql2.append(" CAST(NULL AS CHAR),");

		//GOT表示開始月
		sql1.append("got_start_mm,");
//		sql2.append("CAST(NULL AS CHAR),");
		sql2.append("?,");

		//GOT表示終了月
		sql1.append("got_end_mm,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//発注停止区分
		sql1.append("hachu_teisi_kb,");
//		sql2.append(" '").append(mst007001_MstSiyofukaKbDictionary.KA.getCode()).append("',");
		sql2.append("CAST(NULL AS CHAR),");

		//センター在庫区分
		sql1.append("CENTER_ZAIKO_KB,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//納品商品コード
		sql1.append("NOHIN_SYOHIN_CD,");
		sql2.append(" ?,");

		//入荷時商品コード
		sql1.append("NYUKA_SYOHIN_CD,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//入荷時商品コード２
		sql1.append("NYUKA_SYOHIN_2_CD,");
		sql2.append(" ?,");

		//ITFコード
		sql1.append("ITF_CD,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//GTINコード
		sql1.append("GTIN_CD,");
		sql2.append("?,");

		//ベンダーメーカーコード
		sql1.append("VENDER_MAKER_CD,");
		sql2.append(" ?,");

		//在庫センターコード
		sql1.append("ZAIKO_CENTER_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//在庫補充発注先
		sql1.append("ZAIKO_HACHU_SAKI,");
		sql2.append(" CAST(NULL AS CHAR),");

		//センター重量
		sql1.append("CENTER_WEIGHT_QT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//外箱サイズ幅
		sql1.append("PACK_WIDTH_QT,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//外箱サイズ高さ
		sql1.append("PACK_HEIGTH_QT,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//外箱サイズ奥行き
		sql1.append("PACK_DEPTH_QT,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//外箱重量
		sql1.append("PACK_WEIGHT_QT,");
//2011.03.04 T.Urano Mod 平均重量追加対応 Start
//		sql2.append(" CAST(NULL AS NUMERIC),");
		sql2.append(" ?,");
//2011.03.04 T.Urano Add 平均重量追加対応 End

		//センター発注単位区分
		sql1.append("CENTER_HACHUTANI_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//センター発注単位数
		sql1.append("CENTER_HACHUTANI_QT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//センターバラ入数
		sql1.append("CENTER_BARA_IRISU_QT,");
		sql2.append(" ?,");

		//センター入り数
		sql1.append("CENTER_IRISU_QT,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//ケース入り数
		sql1.append("CASE_IRISU_QT,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//梱り合せ数
		sql1.append("CENTER_IRISU_2_QT,");
		sql2.append(" ?,");

		//最低在庫数(発注点)
		sql1.append("MIN_ZAIKOSU_QT,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//最大在庫数
		sql1.append("MAX_ZAIKOSU_QT,");
//		sql2.append(" CAST(NULL AS CHAR),");
		sql2.append("?,");

		//基準在庫日数
		sql1.append("KIJUN_ZAIKOSU_QT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//最小在庫日数
		sql1.append("MIN_ZAIKONISSU_QT,");
		sql2.append(" ?,");

		//最大在庫日数
		sql1.append("MAX_ZAIKONISSU_QT,");
		sql2.append(" ?,");

		//センター許容区分
		sql1.append("CENTER_KYOYO_KB,");
		sql2.append(" ?,");

		//センター許容日
		sql1.append("CENTER_KYOYO_DT,");
		sql2.append(" ?,");

		//センター賞味期間区分
		sql1.append("CENTER_SYOMI_KIKAN_KB,");
		sql2.append(" ?,");

		//センター賞味期間
		sql1.append("CENTER_SYOMI_KIKAN_DT,");
		sql2.append(" ?,");

		//店グルーピングNO(物流）
		sql1.append("TEN_GROUPNO_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//TC情報
		sql1.append("TC_JYOUHO_NA,");
		sql2.append(" CAST(NULL AS CHAR),");

		//納品温度帯
		sql1.append("NOHIN_ONDO_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//横もち区分
		sql1.append("yokomoti_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//品揃区分
		sql1.append("shinazoroe_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//本部在庫区分
		sql1.append("HONBU_ZAI_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//店在庫区分
		sql1.append("ten_zaiko_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//販売政策区分
		sql1.append("hanbai_seisaku_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//返品契約書NO
		sql1.append("henpin_nb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//返品原価
		sql1.append("henpin_genka_vl,");
		sql2.append(" CAST(NULL AS NUMERIC),");

		//CGC返品区分
		sql1.append("CGC_HENPIN_KB,");
		sql2.append(" ?,");

		//販売期限区分
		sql1.append("HANBAI_LIMIT_KB,");
		sql2.append("?,");

		//販売期限
		sql1.append("HANBAI_LIMIT_QT,");
		sql2.append(" ?,");

		//PLU送信日
		sql1.append("PLU_SEND_DT,");
		sql2.append("?,");

		//キーPLU対象
		sql1.append("KEYPLU_FG,");
		sql2.append(" CAST(NULL AS CHAR),");

		//PLU区分
		sql1.append("PLU_KB, ");
		sql2.append(" ?,");

		//酒税報告分類
		sql1.append("SYUZEI_HOKOKU_KB,");
		sql2.append(" ?,");

		//酒内容量
		sql1.append("SAKE_NAIYORYO_QT,");
		sql2.append(" ?,");

		//タグ表示売価
		sql1.append("tag_hyoji_baika_vl,");
		sql2.append(" CAST(NULL AS CHAR),");

		//消札売価
		sql1.append("KESHI_BAIKA_VL,");
		sql2.append(" CAST(NULL AS CHAR),");

		//よりどり種類
		sql1.append("YORIDORI_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//よりどり価格
		sql1.append("YORIDORI_VL,");
		sql2.append(" CAST(NULL AS CHAR),");

		//よりどり数量
		sql1.append("YORIDORI_QT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//ブランドコード
		sql1.append("bland_cd,");
		sql2.append(" CAST(NULL AS CHAR),");

		//シーズンコード
		sql1.append("season_cd,");
		sql2.append(" CAST(NULL AS CHAR),");

		//服種コード
		sql1.append("HUKUSYU_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//スタイルコード
		sql1.append("STYLE_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//シーンコード
		sql1.append("scene_cd,");
		sql2.append(" CAST(NULL AS CHAR),");

		//性別コード
		sql1.append("sex_cd,");
		sql2.append(" CAST(NULL AS CHAR),");

		//年代コード
		sql1.append("age_cd,");
		sql2.append(" CAST(NULL AS CHAR),");

		//世代コード
		sql1.append("GENERATION_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//素材コード
		sql1.append("SOZAI_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//パターンコード
		sql1.append("PATTERN_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//織り編みコード
		sql1.append("ORIAMI_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//付加機能コード
		sql1.append("HUKA_KINO_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//袖丈コード
		sql1.append("SODE_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//サイズコード
		sql1.append("SIZE_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//カラーコード
		sql1.append("COLOR_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//契約数
		sql1.append("KEIYAKU_SU_QT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//契約パターン
		sql1.append("KEIYAKU_PATTERN_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//契約開始期間
		sql1.append("KEIYAKU_ST_DT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//契約終了期間
		sql1.append("KEIYAKU_ED_DT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//組数区分
		sql1.append("kumisu_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//値札区分
		sql1.append("nefuda_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//値札受渡日
		sql1.append("NEFUDA_UKEWATASI_DT,");
		sql2.append(" CAST(NULL AS CHAR),");

		//値札受渡方法
		sql1.append("NEFUDA_UKEWATASI_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//PC発行区分
		sql1.append("pc_kb,");
		sql2.append(" ?,");

		//台紙NO
		sql1.append("DAISI_NO_NB,");
		sql2.append(" ?,");

		//ユニットプライス-単位区分
		sql1.append("UNIT_PRICE_TANI_KB,");
		sql2.append(" ?,");

		//ユニットプライス-内容量
		sql1.append("UNIT_PRICE_NAIYORYO_QT,");
		sql2.append(" ?,");

		//ユニットプライス-基準単位量
		sql1.append("UNIT_PRICE_KIJUN_TANI_QT,");
		sql2.append(" ?,");


		//消費期限区分
		sql1.append("SYOHI_KIGEN_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//消費期限
		sql1.append("SYOHI_KIGEN_DT,");
		//#1417対応 2016.06.03 M.Kanno (S)
		//sql2.append(" CAST(NULL AS NUMERIC),");
		sql2.append(" ?,");
		//#1417対応 2016.06.03 M.Kanno (E)


		//商品台帳（店舗）
		sql1.append("DAICHO_TENPO_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//商品台帳（本部）
		sql1.append("DAICHO_HONBU_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//商品台帳（仕入先）
		sql1.append("DAICHO_SIIRESAKI_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//棚NO
		sql1.append("TANA_NO_NB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//段NO1
		sql1.append("DAN_NO_NB,");
		sql2.append("CAST(NULL AS CHAR),");

		//リベート対象フラグ
		sql1.append("rebate_fg,");
		sql2.append("CAST(NULL AS CHAR),");

		//マークグループ
		sql1.append("MARK_GROUP_CD,");
		sql2.append("CAST(NULL AS CHAR),");

		//マークコード
		sql1.append("MARK_CD,");
		sql2.append("CAST(NULL AS CHAR),");

		//輸入品区分
		sql1.append("YUNYUHIN_KB,");
		sql2.append(" CAST(NULL AS CHAR),");

		//原産国/配布コード
		sql1.append("SANTI_CD,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性１
		sql1.append("d_zokusei_1_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性１
		sql1.append("s_zokusei_1_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性２
		sql1.append("d_zokusei_2_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性2
		sql1.append("s_zokusei_2_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性３
		sql1.append("d_zokusei_3_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性３
		sql1.append("s_zokusei_3_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性４
		sql1.append("d_zokusei_4_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性4
		sql1.append("s_zokusei_4_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性５
		sql1.append("d_zokusei_5_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性5
		sql1.append("s_zokusei_5_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性６
		sql1.append("d_zokusei_6_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性6
		sql1.append("s_zokusei_6_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性７
		sql1.append("d_zokusei_7_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性7
		sql1.append("s_zokusei_7_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性８
		sql1.append("d_zokusei_8_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性8
		sql1.append("s_zokusei_8_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性９
		sql1.append("d_zokusei_9_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性9
		sql1.append("s_zokusei_9_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//大属性１０
		sql1.append("d_zokusei_10_na,");
		sql2.append(" CAST(NULL AS CHAR),");

		//小属性10
		sql1.append("s_zokusei_10_na,");
		sql2.append(" CAST(NULL AS CHAR),");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		sql1.append("free_1_kb,");
		sql2.append(" ?,");

		//任意区分２
		sql1.append("free_2_kb,");
		sql2.append(" ?,");

		//任意区分３
		sql1.append("free_3_kb,");
		sql2.append(" ?,");

		//任意区分４
		sql1.append("free_4_kb,");
		sql2.append(" ?,");

		//任意区分５
		sql1.append("free_5_kb,");
		sql2.append(" ?,");

		//コメント１
		sql1.append("comment_1_tx,");
		sql2.append(" ?,");

		//コメント２
		sql1.append("comment_2_tx,");
		sql2.append(" ?,");

		//任意コード
		sql1.append("free_cd,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//FUJI商品区分
		sql1.append("FUJI_SYOHIN_KB,");
		sql2.append("CAST(NULL AS CHAR),");

		//コメント
		sql1.append("COMMENT_TX,");
		sql2.append(" CAST(NULL AS CHAR),");

		//自動削除対象区分
		sql1.append("auto_del_kb,");
		sql2.append(" CAST(NULL AS CHAR),");

		//マスタ使用不可区分
		sql1.append("mst_siyofuka_kb,");
		sql2.append(" '").append(mst007001_MstSiyofukaKbDictionary.KA.getCode()).append("',");

		//廃番実施フラグ
		sql1.append("HAIBAN_FG,");
		sql2.append(" CAST(NULL AS CHAR),");

		//新規登録日
		sql1.append("SINKI_TOROKU_DT,");
		sql2.append("'").append(MasterDT).append("',");

		//変更日
		sql1.append("henko_dt,");
		sql2.append("'").append(MasterDT).append("',");

		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		//年齢制限区分
		sql1.append("NENREI_SEIGEN_KB,");
		sql2.append(" ?,");

		//年齢
		sql1.append("NENREI,");
		sql2.append(" ?,");

		//瓶区分
		sql1.append("KAN_KB,");
		sql2.append(" ?,");

		//保証金
		sql1.append("HOSHOUKIN,");
		sql2.append(" ?,");
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)

		// 2016/10/26 T.Arimoto #2256対応（S)
		// 緊急配信フラグ
		sql1.append("EMG_FLAG,");
		sql2.append(" ?,");
		// 2016/10/26 T.Arimoto #2256対応（E)

		//初回登録日
		sql1.append("SYOKAI_TOROKU_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//初回登録社員ID
		sql1.append("SYOKAI_USER_ID,");
		sql2.append(" ?,");

		//作成年月日
		sql1.append("INSERT_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//作成者ID
		sql1.append("INSERT_USER_ID,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID,");
		sql2.append(" ?,");

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID,");
		sql2.append(" '" + BATCH_ID + "', ");

		//削除フラグ
		sql1.append("DELETE_FG ");
		sql2.append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
		// 有効日
		sql1.append(",CUR_GENERATION_YUKO_DT ");
		sql2.append(" , ? ");

		// 原価単価
		sql1.append(",CUR_GENERATION_GENTANKA_VL ");
		sql2.append(" , ? ");
		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)

		// #1900対応 2016.08.04 M.Akagi (S)
		// 仕入税区分
		sql1.append(",SIIRE_ZEI_KB ");
		sql2.append(" , ? ");

		// 仕入税率区分
		sql1.append(",SIIRE_TAX_RATE_KB ");
		sql2.append(" , ? ");

		// 卸税区分
		sql1.append(",OROSI_ZEI_KB ");
		sql2.append(" , ? ");

		// 卸税率区分
		sql1.append(",OROSI_TAX_RATE_KB ");
		sql2.append(" , ? ");

		// 卸売価単価
		sql1.append(",OROSI_BAITANKA_VL ");
		sql2.append(" , ? ");

		// 卸売価単価（税抜）
		sql1.append(",OROSI_BAITANKA_NUKI_VL ");
		sql2.append(" , ? ");
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		// 最低発注数
		sql1.append(",MIN_HACHU_QT ");
		sql2.append(" , ? ");

		// 発注不可フラグ
		sql1.append(",HACHU_FUKA_FLG ");
		sql2.append(" , ? ");

		// 規格内容
		sql1.append(",PER_TXT ");
		sql2.append(" , ? ");

		// 消費期限表示パターン
		sql1.append(",SYOHI_KIGEN_HYOJI_PATTER ");
		sql2.append(" , ? ");

		// PLU反映時間
		sql1.append(",PLU_HANEI_TIME ");
		sql2.append(" , ? ");
		// #1964対応 2016.08.22 M.Akagi (E)

		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql1.append(",OROSI_BAITANKA_VL_KOURI "); // 卸売価単価(小売税)
		sql2.append(" , ? ");
		// 2016/10/25 Li.Sheng #2258 対応（E)

		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		// 割引区分
		sql1.append(",WARIBIKI_KB ");
		sql2.append(" , ? ");
		
		// PB区分
		sql1.append(",PB_SYOHIN_KB ");
		sql2.append(" , ? ");
		
		// 医薬品分類
		sql1.append(",IYAKUHIN_KB ");
		sql2.append(" , ? ");

		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		
		// #6355 Add 2021/09/27 SIEU.D (S)
		// 賞味期限
		sql1.append(",SYOMI_KIGEN_NISU ");
		sql2.append(" , ? ");

		// 出荷可能限度期日
		sql1.append(",SHUKKA_KIGEN_NISU ");
		sql2.append(" , ? ");

		// 入荷可能限度期日
		sql1.append(",NYUKA_KIGEN_NISU ");
		sql2.append(" , ? ");
		// #6355 Add 2021/09/27 SIEU.D (E)

		sql.append("insert into r_syohin ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	//Add 2015.07.10 DAI.BQ (S)
	/**
	 * 商品マスタASNデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedASNSyohinSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		//分類１コード
		sql1.append("BUNRUI1_CD,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("SYOHIN_CD,");
		sql2.append(" ?,");

		//有効日
		sql1.append("YUKO_DT,");
		sql2.append(" ?,");

		//商品名（英字）
		sql1.append("SYOHIN_EIJI_NA,");
		sql2.append(" ?,");

		//国コード
		sql1.append("COUNTRY_CD,");
		sql2.append(" ?,");

		//ADD 2015/08/17 THO.VT (S)
		//メーカーコード
		sql1.append("MAKER_CD,");
		sql2.append(" ?,");
		//ADD 2015/08/17 THO.VT (E)

		//Add 2016.01.11 Huy.NT (S)
		//販売方法
		sql1.append("HANBAI_HOHO_KB,");
		sql2.append(" ?,");
		//Add 2016.01.11 Huy.NT (E)

		//最低在庫数量
		sql1.append("MIN_ZAIKO_QT,");
		sql2.append("?,");

		//セキュリティタグフラグ
		sql1.append("SECURITY_TAG_FG,");
		sql2.append(" ?,");

		//メンバーディ割引対象外商品フラグ
		sql1.append("MEMBER_DISCOUNT_FG,");
		sql2.append(" ?,");

		//ハンパー商品フラグ
		sql1.append("HAMPER_SYOHIN_FG,");
		sql2.append(" ?,");

		//作成年月日
		sql1.append("INSERT_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//作成者ID
		sql1.append("INSERT_USER_ID,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID,");
		sql2.append(" ?,");

		//削除フラグ
		sql1.append("DELETE_FG");
		sql2.append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

		// #1964対応 2016.08.22 M.Akagi (S)
		// ラベル成分
		sql1.append(",LABEL_SEIBUN");
		sql2.append(" ,?");

		// ラベル保管方法
		sql1.append(",LABEL_HOKAN_HOHO");
		sql2.append(" ,?");

		// ラベル使い方
		sql1.append(",LABEL_TUKAIKATA");
		sql2.append(" ,?");
		// #1964対応 2016.08.22 M.Akagi (E)

		sql.append(" INSERT INTO R_SYOHIN_ASN ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") VALUES ( ");
		sql.append(sql2.toString());
		sql.append(") ");
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	//Add 2015.07.10 DAI.BQ (E)

	/**
	 * 商品マスタデータ更新用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedSyohinUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();

		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		//旧商品コード
		sql1.append("OLD_SYOHIN_CD = ?,");
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

		//システム区分

		//業種区分

		//商品区分
		sql1.append("SYOHIN_KB = ?,");

		//桁数区分

		//分類２コード
		sql1.append("BUNRUI2_CD = ?,");

		//分類５コード
		sql1.append("BUNRUI5_CD = ?,");

		//品目

		//商品コード２
//		sql1.append("SYOHIN_2_CD =?,");

		//在庫用商品集計コード
		sql1.append("ZAIKO_SYOHIN_CD = ?,");

		//情報系用商品集計コード
		sql1.append("JYOHO_SYOHIN_CD = ?,");
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//JANメーカーコード
//		sql1.append("MAKER_CD =?,");
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		//漢字品名
		sql1.append("hinmei_kanji_na = ?,");

		//漢字規格
		sql1.append("kikaku_kanji_na = ?,");

		//漢字規格2
		sql1.append("KIKAKU_KANJI_2_NA = ?,");

		//POSレシート品名(漢字)
		sql1.append("REC_HINMEI_KANJI_NA =?,");

		//カナ品名
		sql1.append("hinmei_kana_na = ?,");

		//カナ規格
		sql1.append("kikaku_kana_na = ?,");

		//カナ規格2
		sql1.append("KIKAKU_KANA_2_NA = ?,");

		//POSレシート品名(カナ)
		sql1.append("REC_HINMEI_KANA_NA =?,");

		//商品サイズ(幅)
		sql1.append("SYOHIN_WIDTH_QT =?,");

		//商品サイズ(高さ)
		sql1.append("SYOHIN_HEIGHT_QT =?,");

		//商品サイズ(奥行き)
		sql1.append("SYOHIN_DEPTH_QT =?,");

		//Eショップ区分

		//PB区分
//		sql1.append("PB_KB = ?,");

		//サブクラスコード
//		sql1.append("SUBCLASS_CD = ?,");

		//配布コード
//		sql1.append("HAIFU_CD = ?,");

		//税区分
		sql1.append("zei_kb =?,");

		//税率区分
		sql1.append("TAX_RATE_KB = ?,");

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		//売価配信区分
		sql1.append("BAIKA_HAISHIN_FG = ?,");
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)

		//原価単価
		sql1.append("gentanka_vl = ?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//原価単価(税抜)
		sql1.append("gentanka_nuki_vl = ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//売価単価
		sql1.append("baitanka_vl = ?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//売価単価(税抜)
		sql1.append("baitanka_nuki_vl = ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//  当初売価
//		sql1.append("tosyo_baika_vl = ?,");

		//前回原価単価
//		sql1.append("pre_gentanka_vl = ?,");

		//前回売価単価
//		sql1.append("pre_baitanka_vl = ?,");

		//特別原価

		//メーカー希望小売り価格
		sql1.append("maker_kibo_kakaku_vl = ?,");

		//仕入先コード
		sql1.append("siiresaki_cd = ?,");

		//代表配送先コード

		//店別仕入先管理コード
//		sql1.append("ten_siiresaki_kanri_cd =  ?,");

		//仕入先品番
//		sql1.append("siire_hinban_cd = ?,");

		//発注商品コード区分

		//発注商品コード
//		sql1.append("HACYU_SYOHIN_CD = ?,");

		//EOS区分
		sql1.append("EOS_KB =  ?,");

		//発注単位呼称
		sql1.append("HACHU_TANI_NA = ?,");

		//販売単位呼称
		sql1.append("HANBAI_TANI = ?,");

		//発注単位(入数)
		sql1.append("hachutani_irisu_qt = ?,");

		//最大発注単位数
		sql1.append("max_hachutani_qt = ?,");

		//ケース発注区分
		sql1.append("CASE_HACHU_KB = ?,");

		//バラ入数
		sql1.append("BARA_IRISU_QT = ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt = ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt = ?,");

		//販売開始日
		sql1.append("hanbai_st_dt = ?,");

		//販売終了日
		sql1.append("hanbai_ed_dt = ?,");

		//販売期間区分

		//定貫区分
		sql1.append("TEIKAN_KB =?,");

		//相場商品区分
		sql1.append("soba_syohin_kb =?,");

		//納品期限区分

		//納品期限

		//①便区分
		sql1.append("BIN_1_KB =?,");

		//①発注パターン区分

		//①締め時間
		sql1.append("SIME_TIME_1_QT =?,");

		//①センタ納品リードタイム

		//①店納品リードタイム

		//①店納品時間帯

		//②便区分
		sql1.append("BIN_2_KB =?,");

		//②発注パターン区分

		//②締め時間
		sql1.append("SIME_TIME_2_QT =?,");

		//②センタ納品リードタイム

		//②店納品リードタイム

		//②店納品時間帯

		//③便区分

		//③発注パターン区分

		//③締め時間

		//③センタ納品リードタイム

		//③店納品リードタイム

		//③店納品時間帯

		//センタ納品リードタイム

		//優先便区分

		//F便区分
		sql1.append("F_BIN_KB = ?,");

		//物流区分
		sql1.append("buturyu_kb = ?,");

		//GOT無条件表示対象

		//GOT表示開始月
		sql1.append("got_start_mm =?,");

		//GOT表示終了月
		sql1.append("got_end_mm =?,");

		//発注停止区分

		//センター在庫区分
		sql1.append("CENTER_ZAIKO_KB =?,");

		//納品商品コード
//		sql1.append("NOHIN_SYOHIN_CD = ?,");

		//入荷時商品コード
		sql1.append("NYUKA_SYOHIN_CD =?,");

		//入荷時商品コード２
		sql1.append("NYUKA_SYOHIN_2_CD = ?,");

		//ITFコード
		sql1.append("ITF_CD =?,");

		//GTINコード
//		sql1.append("GTIN_CD =?,");

		//ベンダーメーカーコード
		sql1.append("VENDER_MAKER_CD = ?,");

		//在庫センターコード

		//在庫補充発注先

		//センター重量

		//外箱サイズ幅
//		sql1.append("PACK_WIDTH_QT = 0,");

		//外箱サイズ高さ
//		sql1.append("PACK_HEIGTH_QT = 0,");

		//外箱サイズ奥行き
//		sql1.append("PACK_DEPTH_QT = 0,");

		//外箱重量
//		sql1.append("PACK_WEIGHT_QT = 0,");
//2011.03.04 T.Urano Add 平均重量追加対応 Start
		sql1.append("PACK_WEIGHT_QT = ?,");
//2011.03.04 T.Urano Add 平均重量追加対応 End

		//センター発注単位区分

		//センター発注単位数

		//センターバラ入数
		sql1.append("CENTER_BARA_IRISU_QT = ?,");

		//センター入り数
		sql1.append("CENTER_IRISU_QT =?,");

		//ケース入り数
		sql1.append("CASE_IRISU_QT =?,");

		//梱り合せ数
		sql1.append("CENTER_IRISU_2_QT = ?,");

		//最低在庫数(発注点)
		sql1.append("MIN_ZAIKOSU_QT =?,");

		//最大在庫数
		sql1.append("MAX_ZAIKOSU_QT =?,");

		//基準在庫日数

		//最小在庫日数
		sql1.append("MIN_ZAIKONISSU_QT = ?,");

		//最大在庫日数
		sql1.append("MAX_ZAIKONISSU_QT = ?,");

		//センター許容区分
		sql1.append("CENTER_KYOYO_KB = ?,");

		//センター許容日
		sql1.append("CENTER_KYOYO_DT = ?,");

		//センター賞味期間区分
		sql1.append("CENTER_SYOMI_KIKAN_KB = ?,");

		//センター賞味期間
		sql1.append("CENTER_SYOMI_KIKAN_DT = ?,");

		//店グルーピングNO(物流）

		//TC情報

		//納品温度帯

		//横もち区分

		//品揃区分

		//本部在庫区分
//		sql1.append("HONBU_ZAI_KB = ?,");

		//店在庫区分

		//販売政策区分

		//返品契約書NO

		//返品原価

		//CGC返品区分
		sql1.append("CGC_HENPIN_KB = ?,");

		//販売期限区分
		sql1.append("HANBAI_LIMIT_KB =?,");

		//販売期限
		sql1.append("HANBAI_LIMIT_QT = ?,");

		//PLU送信日
		sql1.append("PLU_SEND_DT =?,");

		//キーPLU対象
//		sql1.append("KEYPLU_FG ='9',");

		//PLU区分
//		sql1.append("PLU_KB, = ?,");

		//酒税報告分類
		sql1.append("SYUZEI_HOKOKU_KB = ?,");

		//酒内容量
		sql1.append("SAKE_NAIYORYO_QT = ?,");

		//タグ表示売価

		//消札売価

		//よりどり種類

		//よりどり価格

		//よりどり数量

		//ブランドコード
//		sql1.append("bland_cd = ?,");

		//シーズンコード

		//服種コード

		//スタイルコード

		//シーンコード

		//性別コード

		//年代コード

		//世代コード

		//素材コード

		//パターンコード

		//織り編みコード

		//付加機能コード
//		sql1.append("HUKA_KINO_CD = ?,");

		//袖丈コード

		//サイズコード

		//カラーコード

		//契約数

		//契約パターン

		//契約開始期間

		//契約終了期間

		//組数区分

		//値札区分

		//値札受渡日

		//値札受渡方法

		//PC発行区分
		sql1.append("pc_kb = ?,");

		//台紙NO
		sql1.append("DAISI_NO_NB = ?,");

		//ユニットプライス-単位区分
		sql1.append("UNIT_PRICE_TANI_KB = ?,");

		//ユニットプライス-内容量
		sql1.append("UNIT_PRICE_NAIYORYO_QT = ?,");

		//ユニットプライス-基準単位量
		sql1.append("UNIT_PRICE_KIJUN_TANI_QT = ?,");


		//消費期限区分
//		sql1.append("SYOHI_KIGEN_KB = ?,");

		//消費期限
		//#1417対応 2016.06.03 M.Kanno (S)
		sql1.append("SYOHI_KIGEN_DT = ?,");
		//#1417対応 2016.06.03 M.Kanno (E)

		//商品台帳（店舗）
//		sql1.append("DAICHO_TENPO_KB = ?,");

		//商品台帳（本部）
//		sql1.append("DAICHO_HONBU_KB = ?,");

		//商品台帳（仕入先）
//		sql1.append("DAICHO_SIIRESAKI_KB = ?,");

		//棚NO

		//段NO1

		//リベート対象フラグ

		//マークグループ

		//マークコード

		//輸入品区分

		//原産国/配布コード

		//大属性１

		//小属性１

		//大属性２

		//小属性2

		//大属性３

		//小属性３

		//大属性４

		//小属性4

		//大属性５

		//小属性5

		//大属性６

		//小属性6

		//大属性７

		//小属性7

		//大属性８

		//小属性8

		//大属性９

		//小属性9

		//大属性１０

		//小属性10

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		sql1.append("FREE_1_KB = ?,");

		//任意区分２
		sql1.append("FREE_2_KB = ?,");

		//任意区分３
		sql1.append("FREE_3_KB = ?,");

		//任意区分４
		sql1.append("FREE_4_KB = ?,");

		//任意区分５
		sql1.append("FREE_5_KB = ?,");

		//コメント１
		sql1.append("COMMENT_1_TX = ?,");

		//コメント２
		sql1.append("COMMENT_2_TX = ?,");

		//任意コード
		sql1.append("FREE_CD = ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//FUJI商品区分
//		sql1.append("FUJI_SYOHIN_KB = ?,");

		//コメント

		//自動削除対象区分

		//マスタ使用不可区分

		//廃番実施フラグ

		//新規登録日

		//変更日
		sql1.append("henko_dt ='").append(MasterDT).append("',");

		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		//年齢制限区分
		sql1.append("NENREI_SEIGEN_KB = ?,");

		//年齢
		sql1.append("NENREI = ?,");

		//瓶区分
		sql1.append("KAN_KB = ?,");

		//保証金
		sql1.append("HOSHOUKIN = ?,");
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)

		// 2016/10/26 T.Arimoto #2803対応（S)
		// 緊急配信フラグ
		sql1.append("EMG_FLAG = ?,");
		// 2016/10/26 T.Arimoto #2803対応（E)


		//初回登録日

		//初回登録社員ID

		//作成年月日
		sql1.append("INSERT_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//作成者ID
		sql1.append("INSERT_USER_ID = ?,");

		//更新年月日
		sql1.append("UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID = ?,");

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID = '" + BATCH_ID + "' ");

		//削除フラグ
//		sql1.append("DELETE_FG ='").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		// 2016/10/25 T.Arimoto #2256対応（S)
		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
		// 有効日(現世代)
		// sql1.append(", CUR_GENERATION_YUKO_DT     =  YUKO_DT 		");
		sql1.append(", CUR_GENERATION_YUKO_DT     =  ? 		");
		// 2016/10/25 T.Arimoto #2256対応（E)

		// 原価単価(現世代)
		sql1.append(", CUR_GENERATION_GENTANKA_VL =  ? 		");
		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)

		// 2016/10/25 T.Arimoto #2256対応（S)
		// 有効日(１世代前)
		sql1.append(", ONE_GENERATION_YUKO_DT     =  ? 		");

		// 原価単価(１世代前)
		sql1.append(", ONE_GENERATION_GENTANKA_VL =  ? 		");

		// 有効日(２世代前)
		sql1.append(", TWO_GENERATION_YUKO_DT     =  ? 		");

		// 原価単価(２世代前)
		sql1.append(", TWO_GENERATION_GENTANKA_VL =  ? 		");
		// 2016/10/25 T.Arimoto #2256対応（E)

		// #1900対応 2016.08.04 M.Akagi (S)
		// 仕入税区分
		sql1.append(", SIIRE_ZEI_KB = ? ");

		// 仕入税率区分
		sql1.append(", SIIRE_TAX_RATE_KB = ? ");

		// 卸税区分
		sql1.append(", OROSI_ZEI_KB = ? ");

		// 卸税率区分
		sql1.append(", OROSI_TAX_RATE_KB = ? ");

		// 卸売単価
		sql1.append(", OROSI_BAITANKA_VL = ? ");

		// 卸売価単価（税抜）
		sql1.append(", OROSI_BAITANKA_NUKI_VL = ? ");
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		// 最低発注数
		sql1.append(", MIN_HACHU_QT = ? ");

		// 発注不可フラグ
		sql1.append(", HACHU_FUKA_FLG = ? ");

		// 規格内容
		sql1.append(", PER_TXT = ? ");

		// 消費期限表示パターン
		sql1.append(", SYOHI_KIGEN_HYOJI_PATTER = ? ");

		// PLU反映時間
		sql1.append(", PLU_HANEI_TIME = ? ");
		// #1964対応 2016.08.22 M.Akagi (E)
		

		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql1.append(", OROSI_BAITANKA_VL_KOURI = ? "); // 卸売価単価(小売税)
		// 2016/10/25 Li.Sheng #2258 対応（E)
		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		// 割引区分
		sql1.append(",WARIBIKI_KB = ? ");
		
		// PB区分
		sql1.append(",PB_SYOHIN_KB = ? ");
		
		// 医薬品分類
		sql1.append(",IYAKUHIN_KB = ? ");
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		// #6355 Add 2021/09/27 SIEU.D (S)
		// 賞味期限
		sql1.append(",SYOMI_KIGEN_NISU = ?  ");

		// 出荷可能限度期日
		sql1.append(",SHUKKA_KIGEN_NISU = ?  ");

		// 入荷可能限度期日
		sql1.append(",NYUKA_KIGEN_NISU = ?  ");
		// #6355 Add 2021/09/27 SIEU.D (E)

		sql.append("update r_syohin ");
		sql.append("set ");
		sql.append(sql1.toString());
		sql.append("where");
		sql.append(" bunrui1_cd = ? and");
		sql.append(" syohin_cd = ? and");
		sql.append(" yuko_dt = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	//Add 2015.07.10 DAI.BQ (S)
	/**
	 * 商品マスタデータ更新用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedASNSyohinUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		//商品名（英字）
		sql1.append("SYOHIN_EIJI_NA = ?,");

		//国コード
		sql1.append("COUNTRY_CD = ?,");

		//ADD 2015/08/17 THO.VT (S)
		//メーカーコード
		sql1.append("MAKER_CD = ?,");
		//ADD 2015/08/17 THO.VT (E)

		//Add 2016.01.11 Huy.NT (S)
		//販売方法
		sql1.append("HANBAI_HOHO_KB = ?,");
		//Add 2016.01.11 Huy.NT (E)

		//最低在庫数量
		sql1.append("MIN_ZAIKO_QT = ?,");

		//セキュリティタグフラグ
		sql1.append("SECURITY_TAG_FG = ?,");

		//メンバーディ割引対象外商品フラグ
		sql1.append("MEMBER_DISCOUNT_FG = ?,");

		//ハンパー商品フラグ
		sql1.append("HAMPER_SYOHIN_FG = ?,");

		//更新年月日
		sql1.append("UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID = ?,");

		//削除フラグ
		sql1.append("DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");

		// #1964対応 2016.08.22 M.Akagi (S)
		// ラベル成分
		sql1.append(",LABEL_SEIBUN = ?");

		// ラベル保管方法
		sql1.append(",LABEL_HOKAN_HOHO = ?");

		// ラベル使い方
		sql1.append(",LABEL_TUKAIKATA = ? ");
		// #1964対応 2016.08.22 M.Akagi (E)

		sql.append(" update r_syohin_asn ");
		sql.append("set ");
		sql.append(sql1.toString());
		sql.append(" where");
		sql.append(" bunrui1_cd = ? and ");
		sql.append(" syohin_cd = ? and ");
		sql.append(" yuko_dt = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	//Add 2015.07.10 DAI.BQ (E)


	private boolean isNotBlank(String val) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		return false;
	}


	/**
	 * 商品マスタ修正時の新規登録用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedSyohinUpInsSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		//分類１コード
		sql1.append("BUNRUI1_CD,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("SYOHIN_CD,");
		sql2.append(" ?,");

		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		//旧商品コード
		sql1.append("OLD_SYOHIN_CD,");
		sql2.append(" ?,");
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

		//有効日
		sql1.append("yuko_dt,");
		sql2.append(" ?,");

		//システム区分
		sql1.append("system_kb,");
		sql2.append(" ?,");

		//業種区分
		sql1.append("gyosyu_kb,");
		sql2.append(" ?,");

		//商品区分
		sql1.append("SYOHIN_KB,");
		sql2.append("?,");

		//桁数区分
		sql1.append("KETASU_KB,");
		sql2.append("?,");

		//分類２コード
		sql1.append("BUNRUI2_CD,");
		sql2.append(" ?,");

		//分類５コード
		sql1.append("BUNRUI5_CD,");
		sql2.append(" ?,");

		//品目
		sql1.append("HINMOKU_CD,");
		sql2.append("?,");

		//商品コード２
		sql1.append("SYOHIN_2_CD,");
		sql2.append("?,");

		//在庫用商品集計コード
		sql1.append("ZAIKO_SYOHIN_CD,");
		sql2.append(" ?,");

		//情報系用商品集計コード
		sql1.append("JYOHO_SYOHIN_CD,");
		sql2.append(" ?,");

		//JANメーカーコード
		sql1.append("MAKER_CD,");
		sql2.append("?,");

		//漢字品名
		sql1.append("hinmei_kanji_na,");
		sql2.append(" ?,");

		//漢字規格
		sql1.append("kikaku_kanji_na,");
		sql2.append(" ?,");

		//漢字規格2
		sql1.append("KIKAKU_KANJI_2_NA,");
		sql2.append(" ?,");

		//POSレシート品名(漢字)
		sql1.append("REC_HINMEI_KANJI_NA,");
		sql2.append("?,");

		//カナ品名
		sql1.append("hinmei_kana_na,");
		sql2.append(" ?,");

		//カナ規格
		sql1.append("kikaku_kana_na,");
		sql2.append(" ?,");

		//カナ規格2
		sql1.append("KIKAKU_KANA_2_NA,");
		sql2.append(" ?,");

		//POSレシート品名(カナ)
		sql1.append("REC_HINMEI_KANA_NA,");
		sql2.append("?,");

		//商品サイズ(幅)
		sql1.append("SYOHIN_WIDTH_QT,");
		sql2.append("?,");

		//商品サイズ(高さ)
		sql1.append("SYOHIN_HEIGHT_QT,");
		sql2.append("?,");

		//商品サイズ(奥行き)
		sql1.append("SYOHIN_DEPTH_QT,");
		sql2.append("?,");

		//Eショップ区分
		sql1.append("e_shop_kb,");
		sql2.append("?,");

		//PB区分
		sql1.append("PB_KB,");
		sql2.append(" ?,");

		//サブクラスコード
		sql1.append("SUBCLASS_CD,");
		sql2.append(" ?,");

		//配布コード
		sql1.append("HAIFU_CD,");
		sql2.append(" ?,");

		//税区分
		sql1.append("zei_kb,");
		sql2.append("?,");

		//税率区分
		sql1.append("TAX_RATE_KB,");
		sql2.append(" ?,");

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		//売価配信フラグ
		sql1.append("BAIKA_HAISHIN_FG,");
		sql2.append(" ?,");
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)

		//原価単価
		sql1.append("gentanka_vl,");
		sql2.append(" ?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//原価単価(税抜)
		sql1.append("gentanka_nuki_vl,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//売価単価
		sql1.append("baitanka_vl,");
		sql2.append(" ?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//売価単価(税抜)
		sql1.append("baitanka_nuki_vl,");
		sql2.append(" ?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//当初売価
		sql1.append("tosyo_baika_vl,");
		sql2.append(" ?,");

		//前回原価単価
		sql1.append("PRE_GENTANKA_VL,");
		sql2.append("?,");

		//前回売価単価
		sql1.append("PRE_BAITANKA_VL,");
		sql2.append("?,");

		//特別原価
		sql1.append("TOKUBETU_GENKA_VL,");
		sql2.append("?,");

		//メーカー希望小売り価格
		sql1.append("maker_kibo_kakaku_vl,");
		sql2.append(" ?,");

		//仕入先コード
		sql1.append("siiresaki_cd,");
		sql2.append(" ?,");

		//代表配送先コード
		sql1.append("daihyo_haiso_cd,");
		sql2.append("?,");

		//店別仕入先管理コード
		sql1.append("ten_siiresaki_kanri_cd,");
		sql2.append("  ?,");

		//仕入先品番
		sql1.append("siire_hinban_cd,");
		sql2.append(" ?,");

		//発注商品コード区分
		sql1.append("hacyu_syohin_kb,");
		sql2.append("?,");

		//発注商品コード
		sql1.append("HACYU_SYOHIN_CD,");
		sql2.append(" ?,");

		//EOS区分
		sql1.append("EOS_KB,");
		sql2.append("  ?,");

		//発注単位呼称
		sql1.append("HACHU_TANI_NA,");
		sql2.append(" ?,");

		//販売単位呼称
		sql1.append("HANBAI_TANI,");
		sql2.append(" ?,");

		//発注単位(入数)
		sql1.append("hachutani_irisu_qt,");
		sql2.append(" ?,");

		//最大発注単位数
		sql1.append("max_hachutani_qt,");
		sql2.append(" ?,");

		//ケース発注区分
		sql1.append("CASE_HACHU_KB,");
		sql2.append(" ?,");

		//バラ入数
		sql1.append("BARA_IRISU_QT,");
		sql2.append(" ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt,");
		sql2.append(" ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt,");
		sql2.append(" ?,");

		//販売開始日
		sql1.append("hanbai_st_dt,");
		sql2.append(" ?,");

		//販売終了日
		sql1.append("hanbai_ed_dt,");
		sql2.append(" ?,");

		//販売期間区分
		sql1.append("HANBAI_KIKAN_KB,");
		sql2.append("?,");

		//定貫区分
		sql1.append("TEIKAN_KB,");
		sql2.append("?,");

		//相場商品区分
		sql1.append("soba_syohin_kb,");
		sql2.append("?,");

		//納品期限区分
		sql1.append("NOHIN_KIGEN_KB,");
		sql2.append("?,");

		//納品期限
		sql1.append("NOHIN_KIGEN_QT,");
		sql2.append("?,");

		//①便区分
		sql1.append("BIN_1_KB,");
		sql2.append("?,");

		//①発注パターン区分
		sql1.append("HACHU_PATTERN_1_KB,");
		sql2.append("?,");

		//①締め時間
		sql1.append("SIME_TIME_1_QT,");
		sql2.append("?,");

		//①センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_1_KB,");
		sql2.append("?,");

		//①店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_1_KB,");
		sql2.append("?,");

		//①店納品時間帯
		sql1.append("TEN_NOHIN_TIME_1_KB,");
		sql2.append("?,");

		//②便区分
		sql1.append("BIN_2_KB,");
		sql2.append("?,");

		//②発注パターン区分
		sql1.append("HACHU_PATTERN_2_KB,");
		sql2.append("?,");

		//②締め時間
		sql1.append("SIME_TIME_2_QT,");
		sql2.append("?,");

		//②センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_2_KB,");
		sql2.append("?,");

		//②店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_2_KB,");
		sql2.append("?,");

		//②店納品時間帯
		sql1.append("TEN_NOHIN_TIME_2_KB,");
		sql2.append("?,");

		//③便区分
		sql1.append("BIN_3_KB,");
		sql2.append("?,");

		//③発注パターン区分
		sql1.append("HACHU_PATTERN_3_KB,");
		sql2.append("?,");

		//③締め時間
		sql1.append("SIME_TIME_3_QT,");
		sql2.append("?,");

		//③センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_3_KB,");
		sql2.append("?,");

		//③店納品リードタイム
		sql1.append("TEN_NOHIN_RTIME_3_KB,");
		sql2.append("?,");

		//③店納品時間帯
		sql1.append("TEN_NOHIN_TIME_3_KB,");
		sql2.append("?,");

		//センタ納品リードタイム
		sql1.append("C_NOHIN_RTIME_KB,");
		sql2.append("?,");

		//優先便区分
		sql1.append("YUSEN_BIN_KB, ");
		sql2.append("?,");

		//F便区分
		sql1.append("F_BIN_KB,");
		sql2.append(" ?,");

		//物流区分
		sql1.append("buturyu_kb,");
		sql2.append(" ?,");

		//GOT無条件表示対象
		sql1.append("GOT_MUJYOKEN_FG,");
		sql2.append("?,");

		//GOT表示開始月
		sql1.append("got_start_mm,");
		sql2.append("?,");

		//GOT表示終了月
		sql1.append("got_end_mm,");
		sql2.append("?,");

		//発注停止区分
		sql1.append("hachu_teisi_kb,");
		sql2.append("?,");

		//センター在庫区分
		sql1.append("CENTER_ZAIKO_KB,");
		sql2.append("?,");

		//納品商品コード
		sql1.append("NOHIN_SYOHIN_CD,");
		sql2.append(" ?,");

		//入荷時商品コード
		sql1.append("NYUKA_SYOHIN_CD,");
		sql2.append("?,");

		//入荷時商品コード２
		sql1.append("NYUKA_SYOHIN_2_CD,");
		sql2.append(" ?,");

		//ITFコード
		sql1.append("ITF_CD,");
		sql2.append("?,");

		//GTINコード
		sql1.append("GTIN_CD,");
		sql2.append("?,");

		//ベンダーメーカーコード
		sql1.append("VENDER_MAKER_CD,");
		sql2.append(" ?,");

		//在庫センターコード
		sql1.append("ZAIKO_CENTER_CD,");
		sql2.append("?,");

		//在庫補充発注先
		sql1.append("ZAIKO_HACHU_SAKI,");
		sql2.append("?,");

		//センター重量
		sql1.append("CENTER_WEIGHT_QT,");
		sql2.append("?,");

		//外箱サイズ幅
		sql1.append("PACK_WIDTH_QT,");
		sql2.append("?,");

		//外箱サイズ高さ
		sql1.append("PACK_HEIGTH_QT,");
		sql2.append("?,");

		//外箱サイズ奥行き
		sql1.append("PACK_DEPTH_QT,");
		sql2.append("?,");

		//外箱重量
		sql1.append("PACK_WEIGHT_QT,");
		sql2.append("?,");

		//センター発注単位区分
		sql1.append("CENTER_HACHUTANI_KB,");
		sql2.append("?,");

		//センター発注単位数
		sql1.append("CENTER_HACHUTANI_QT,");
		sql2.append("?,");

		//センターバラ入数
		sql1.append("CENTER_BARA_IRISU_QT,");
		sql2.append(" ?,");

		//センター入り数
		sql1.append("CENTER_IRISU_QT,");
		sql2.append("?,");

		//ケース入り数
		sql1.append("CASE_IRISU_QT,");
		sql2.append("?,");

		//梱り合せ数
		sql1.append("CENTER_IRISU_2_QT,");
		sql2.append(" ?,");

		//最低在庫数(発注点)
		sql1.append("MIN_ZAIKOSU_QT,");
		sql2.append("?,");

		//最大在庫数
		sql1.append("MAX_ZAIKOSU_QT,");
		sql2.append("?,");

		//基準在庫日数
		sql1.append("KIJUN_ZAIKOSU_QT,");
		sql2.append("?,");

		//最小在庫日数
		sql1.append("MIN_ZAIKONISSU_QT,");
		sql2.append(" ?,");

		//最大在庫日数
		sql1.append("MAX_ZAIKONISSU_QT,");
		sql2.append(" ?,");

		//センター許容区分
		sql1.append("CENTER_KYOYO_KB,");
		sql2.append(" ?,");

		//センター許容日
		sql1.append("CENTER_KYOYO_DT,");
		sql2.append(" ?,");

		//センター賞味期間区分
		sql1.append("CENTER_SYOMI_KIKAN_KB,");
		sql2.append(" ?,");

		//センター賞味期間
		sql1.append("CENTER_SYOMI_KIKAN_DT,");
		sql2.append(" ?,");

		//店グルーピングNO(物流）
		sql1.append("TEN_GROUPNO_CD,");
		sql2.append("?,");

		//TC情報
		sql1.append("TC_JYOUHO_NA,");
		sql2.append("?,");

		//納品温度帯
		sql1.append("NOHIN_ONDO_KB,");
		sql2.append("?,");

		//横もち区分
		sql1.append("yokomoti_kb,");
		sql2.append("?,");

		//品揃区分
		sql1.append("shinazoroe_kb,");
		sql2.append("?,");

		//本部在庫区分
		sql1.append("HONBU_ZAI_KB,");
		sql2.append(" ?,");

		//店在庫区分
		sql1.append("ten_zaiko_kb,");
		sql2.append("?,");

		//販売政策区分
		sql1.append("hanbai_seisaku_kb,");
		sql2.append("?,");

		//返品契約書NO
		sql1.append("henpin_nb,");
		sql2.append("?,");

		//返品原価
		sql1.append("henpin_genka_vl,");
		sql2.append("?,");

		//CGC返品区分
		sql1.append("CGC_HENPIN_KB,");
		sql2.append(" ?,");

		//販売期限区分
		sql1.append("HANBAI_LIMIT_KB,");
		sql2.append("?,");

		//販売期限
		sql1.append("HANBAI_LIMIT_QT,");
		sql2.append(" ?,");

		//PLU送信日
		sql1.append("PLU_SEND_DT,");
		sql2.append("?,");

		//キーPLU対象
		sql1.append("KEYPLU_FG,");
		sql2.append("?,");

		//PLU区分
		sql1.append("PLU_KB, ");
		sql2.append(" ?,");

		//酒税報告分類
		sql1.append("SYUZEI_HOKOKU_KB,");
		sql2.append(" ?,");

		//酒内容量
		sql1.append("SAKE_NAIYORYO_QT,");
		sql2.append(" ?,");

		//タグ表示売価
		sql1.append("tag_hyoji_baika_vl,");
		sql2.append("?,");

		//消札売価
		sql1.append("KESHI_BAIKA_VL,");
		sql2.append("?,");

		//よりどり種類
		sql1.append("YORIDORI_KB,");
		sql2.append("?,");

		//よりどり価格
		sql1.append("YORIDORI_VL,");
		sql2.append("?,");

		//よりどり数量
		sql1.append("YORIDORI_QT,");
		sql2.append("?,");

		//ブランドコード
		sql1.append("bland_cd,");
		sql2.append(" ?,");

		//シーズンコード
		sql1.append("season_cd,");
		sql2.append("?,");

		//服種コード
		sql1.append("HUKUSYU_CD,");
		sql2.append("?,");

		//スタイルコード
		sql1.append("STYLE_CD,");
		sql2.append("?,");

		//シーンコード
		sql1.append("scene_cd,");
		sql2.append("?,");

		//性別コード
		sql1.append("sex_cd,");
		sql2.append("?,");

		//年代コード
		sql1.append("age_cd,");
		sql2.append("?,");

		//世代コード
		sql1.append("GENERATION_CD,");
		sql2.append("?,");

		//素材コード
		sql1.append("SOZAI_CD,");
		sql2.append("?,");

		//パターンコード
		sql1.append("PATTERN_CD,");
		sql2.append("?,");

		//織り編みコード
		sql1.append("ORIAMI_CD,");
		sql2.append("?,");

		//付加機能コード
		sql1.append("HUKA_KINO_CD,");
		sql2.append("?,");

		//袖丈コード
		sql1.append("SODE_CD,");
		sql2.append("?,");

		//サイズコード
		sql1.append("SIZE_CD,");
		sql2.append("?,");

		//カラーコード
		sql1.append("COLOR_CD,");
		sql2.append("?,");

		//契約数
		sql1.append("KEIYAKU_SU_QT,");
		sql2.append("?,");

		//契約パターン
		sql1.append("KEIYAKU_PATTERN_KB,");
		sql2.append("?,");

		//契約開始期間
		sql1.append("KEIYAKU_ST_DT,");
		sql2.append("?,");

		//契約終了期間
		sql1.append("KEIYAKU_ED_DT,");
		sql2.append("?,");

		//組数区分
		sql1.append("kumisu_kb,");
		sql2.append("?,");

		//値札区分
		sql1.append("nefuda_kb,");
		sql2.append("?,");

		//値札受渡日
		sql1.append("NEFUDA_UKEWATASI_DT,");
		sql2.append("?,");

		//値札受渡方法
		sql1.append("NEFUDA_UKEWATASI_KB,");
		sql2.append("?,");

		//PC発行区分
		sql1.append("pc_kb,");
		sql2.append(" ?,");

		//台紙NO
		sql1.append("DAISI_NO_NB,");
		sql2.append(" ?,");

		//ユニットプライス-単位区分
		sql1.append("UNIT_PRICE_TANI_KB,");
		sql2.append(" ?,");

		//ユニットプライス-内容量
		sql1.append("UNIT_PRICE_NAIYORYO_QT,");
		sql2.append(" ?,");

		//ユニットプライス-基準単位量
		sql1.append("UNIT_PRICE_KIJUN_TANI_QT,");
		sql2.append(" ?,");

		//消費期限区分
		sql1.append("SYOHI_KIGEN_KB,");
		sql2.append(" ?,");

		//消費期限
		sql1.append("SYOHI_KIGEN_DT,");
		sql2.append(" ?,");

		//商品台帳（店舗）
		sql1.append("DAICHO_TENPO_KB,");
		sql2.append(" ?,");

		//商品台帳（本部）
		sql1.append("DAICHO_HONBU_KB,");
		sql2.append(" ?,");

		//商品台帳（仕入先）
		sql1.append("DAICHO_SIIRESAKI_KB,");
		sql2.append(" ?,");

		//棚NO
		sql1.append("TANA_NO_NB,");
		sql2.append("?,");

		//段NO1
		sql1.append("DAN_NO_NB,");
		sql2.append("?,");

		//リベート対象フラグ
		sql1.append("rebate_fg,");
		sql2.append("?,");

		//マークグループ
		sql1.append("MARK_GROUP_CD,");
		sql2.append("?,");

		//マークコード
		sql1.append("MARK_CD,");
		sql2.append("?,");

		//輸入品区分
		sql1.append("YUNYUHIN_KB,");
		sql2.append("?,");

		//原産国/配布コード
		sql1.append("SANTI_CD,");
		sql2.append("?,");

		//大属性１
		sql1.append("d_zokusei_1_na,");
		sql2.append("?,");

		//小属性１
		sql1.append("s_zokusei_1_na,");
		sql2.append("?,");

		//大属性２
		sql1.append("d_zokusei_2_na,");
		sql2.append("?,");

		//小属性2
		sql1.append("s_zokusei_2_na,");
		sql2.append("?,");

		//大属性３
		sql1.append("d_zokusei_3_na,");
		sql2.append("?,");

		//小属性３
		sql1.append("s_zokusei_3_na,");
		sql2.append("?,");

		//大属性４
		sql1.append("d_zokusei_4_na,");
		sql2.append("?,");

		//小属性4
		sql1.append("s_zokusei_4_na,");
		sql2.append("?,");

		//大属性５
		sql1.append("d_zokusei_5_na,");
		sql2.append("?,");

		//小属性5
		sql1.append("s_zokusei_5_na,");
		sql2.append("?,");

		//大属性６
		sql1.append("d_zokusei_6_na,");
		sql2.append("?,");

		//小属性6
		sql1.append("s_zokusei_6_na,");
		sql2.append("?,");

		//大属性７
		sql1.append("d_zokusei_7_na,");
		sql2.append("?,");

		//小属性7
		sql1.append("s_zokusei_7_na,");
		sql2.append("?,");

		//大属性８
		sql1.append("d_zokusei_8_na,");
		sql2.append("?,");

		//小属性8
		sql1.append("s_zokusei_8_na,");
		sql2.append("?,");

		//大属性９
		sql1.append("d_zokusei_9_na,");
		sql2.append("?,");

		//小属性9
		sql1.append("s_zokusei_9_na,");
		sql2.append("?,");

		//大属性１０
		sql1.append("d_zokusei_10_na,");
		sql2.append("?,");

		//小属性10
		sql1.append("s_zokusei_10_na,");
		sql2.append("?,");

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		sql1.append("free_1_kb,");
		sql2.append("?,");

		//任意区分２
		sql1.append("free_2_kb,");
		sql2.append("?,");

		//任意区分３
		sql1.append("free_3_kb,");
		sql2.append("?,");

		//任意区分４
		sql1.append("free_4_kb,");
		sql2.append("?,");

		//任意区分５
		sql1.append("free_5_kb,");
		sql2.append("?,");

		//コメント１
		sql1.append("comment_1_tx,");
		sql2.append("?,");

		//コメント２
		sql1.append("comment_2_tx,");
		sql2.append("?,");

		//任意コード
		sql1.append("free_cd,");
		sql2.append("?,");
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//FUJI商品区分
		sql1.append("FUJI_SYOHIN_KB,");
		sql2.append(" ?,");

		//コメント
		sql1.append("COMMENT_TX,");
		sql2.append("?,");

		//自動削除対象区分
		sql1.append("auto_del_kb,");
		sql2.append("?,");

		//マスタ使用不可区分
		sql1.append("mst_siyofuka_kb,");
		sql2.append("?,");

		//廃番実施フラグ
		sql1.append("HAIBAN_FG,");
		sql2.append("?,");

		//新規登録日
		sql1.append("SINKI_TOROKU_DT,");
		sql2.append("?,");

		//変更日
		sql1.append("henko_dt,");
		sql2.append("'").append(MasterDT).append("',");

		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		//年齢制限区分
		sql1.append("NENREI_SEIGEN_KB,");
		sql2.append(" ?,");

		//年齢
		sql1.append("NENREI,");
		sql2.append(" ?,");

		//瓶区分
		sql1.append("KAN_KB,");
		sql2.append(" ?,");

		//保証金
		sql1.append("HOSHOUKIN,");
		sql2.append(" ?,");
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)

		// 2016/10/25 T.Arimoto #2256対応（S)
		// 緊急配信フラグ
		sql1.append("EMG_FLAG,");
		sql2.append(" ?,");
		// 2016/10/25 T.Arimoto #2256対応（E)

		//初回登録日
		sql1.append("SYOKAI_TOROKU_TS,");
		sql2.append("?,");

		//初回登録社員ID
		sql1.append("SYOKAI_USER_ID,");
		sql2.append(" ?,");

		//作成年月日
		sql1.append("INSERT_TS,");
//		sql2.append("?,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//作成者ID
		sql1.append("INSERT_USER_ID,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID,");
		sql2.append(" ?,");

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID,");
		sql2.append(" '" + BATCH_ID + "', ");

		//削除フラグ
		sql1.append("DELETE_FG ");
		sql2.append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
		// 有効日
		sql1.append(",CUR_GENERATION_YUKO_DT ");
		sql2.append(" , ? ");

		// 原価単価
		sql1.append(",CUR_GENERATION_GENTANKA_VL ");
		sql2.append(" , ? ");

		// 有効日
		sql1.append(",ONE_GENERATION_YUKO_DT ");
		sql2.append(" , ? ");

		// 原価単価
		sql1.append(",ONE_GENERATION_GENTANKA_VL ");
		sql2.append(" , ? ");

		// 有効日
		sql1.append(",TWO_GENERATION_YUKO_DT ");
		sql2.append(" , ? ");

		// 原価単価
		sql1.append(",TWO_GENERATION_GENTANKA_VL ");
		sql2.append(" , ? ");
		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)

		// #1900対応 2016.08.04 M.Akagi (S)
		// 仕入税区分
		sql1.append(",SIIRE_ZEI_KB ");
		sql2.append(" , ? ");

		// 仕入税率区分
		sql1.append(",SIIRE_TAX_RATE_KB ");
		sql2.append(" , ? ");

		// 卸税区分
		sql1.append(",OROSI_ZEI_KB ");
		sql2.append(" , ? ");

		// 卸税率区分
		sql1.append(",OROSI_TAX_RATE_KB ");
		sql2.append(" , ? ");

		// 卸売価単価
		sql1.append(",OROSI_BAITANKA_VL ");
		sql2.append(" , ? ");

		// 卸売価単価（税抜）
		sql1.append(",OROSI_BAITANKA_NUKI_VL ");
		sql2.append(" , ? ");
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		// 最低発注数
		sql1.append(",MIN_HACHU_QT ");
		sql2.append(" , ? ");

		// 発注不可フラグ
		sql1.append(",HACHU_FUKA_FLG ");
		sql2.append(" , ? ");

		// 規格内容
		sql1.append(",PER_TXT ");
		sql2.append(" , ? ");

		// 消費期限表示パターン
		sql1.append(",SYOHI_KIGEN_HYOJI_PATTER ");
		sql2.append(" , ? ");

		// PLU反映時間
		sql1.append(",PLU_HANEI_TIME ");
		sql2.append(" , ? ");
		// #1964対応 2016.08.22 M.Akagi (E)
		

		// 2016/10/25 Li.Sheng #2258 対応（S)
		sql1.append(",OROSI_BAITANKA_VL_KOURI "); // 卸売価単価(小売税)
		sql2.append(" , ? ");
		// 2016/10/25 Li.Sheng #2258 対応（E)

		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		// 割引区分
		sql1.append(",WARIBIKI_KB ");
		sql2.append(" , ? ");
		
		// PB区分
		sql1.append(",PB_SYOHIN_KB ");
		sql2.append(" , ? ");
		
		// 医薬品分類
		sql1.append(",IYAKUHIN_KB ");
		sql2.append(" , ? ");
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		
		// #6355 Add 2021/09/27 SIEU.D (S)
		// 賞味期限
		sql1.append(",SYOMI_KIGEN_NISU ");
		sql2.append(" , ? ");

		// 出荷可能限度期日
		sql1.append(",SHUKKA_KIGEN_NISU ");
		sql2.append(" , ? ");

		// 入荷可能限度期日
		sql1.append(",NYUKA_KIGEN_NISU ");
		sql2.append(" , ? ");
		// #6355 Add 2021/09/27 SIEU.D (E)
		
		sql.append("insert into r_syohin ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	//Add 2015.07.10 DAI.BQ (S)
	/**
	 * 商品マスタASN修正時の新規登録用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedASNSyohinUpInsSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		//分類１コード
		sql1.append(" BUNRUI1_CD,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("SYOHIN_CD,");
		sql2.append(" ?,");

		//有効日
		sql1.append("YUKO_DT,");
		sql2.append(" ?,");

		//商品名（英字）
		sql1.append("SYOHIN_EIJI_NA,");
		sql2.append(" ?,");

		//国コード
		sql1.append("COUNTRY_CD,");
		sql2.append(" ?,");

		//ADD 2015/08/17 THO.VT (S)
		//メーカーコード
		sql1.append("MAKER_CD,");
		sql2.append(" ?,");
		//ADD 2015/08/17 THO.VT (E)

		//Add 2016.01.11 Huy.NT (S)
		sql1.append("HANBAI_HOHO_KB,");
		sql2.append(" ?,");
		//Add 2016.01.11 Huy.NT (E)

		//最低在庫数量
		sql1.append("MIN_ZAIKO_QT,");
		sql2.append("?,");

		//セキュリティタグフラグ
		sql1.append("SECURITY_TAG_FG,");
		sql2.append(" ?,");

		//メンバーディ割引対象外商品フラグ
		sql1.append("MEMBER_DISCOUNT_FG,");
		sql2.append(" ?,");

		//ハンパー商品フラグ
		sql1.append("HAMPER_SYOHIN_FG,");
		sql2.append(" ?,");

		//作成年月日
		sql1.append("INSERT_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//作成者ID
		sql1.append("INSERT_USER_ID,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("UPDATE_TS,");
		sql2.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ,");

		//更新者ID
		sql1.append("UPDATE_USER_ID,");
		sql2.append(" ?,");


		//削除フラグ
		sql1.append("DELETE_FG ");
		sql2.append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		// #1964対応 2016.08.22 M.Akagi (S)
		// ラベル成分
		sql1.append(",LABEL_SEIBUN");
		sql2.append(" ,?");

		// ラベル保管方法
		sql1.append(",LABEL_HOKAN_HOHO");
		sql2.append(" ,?");

		// ラベル使い方
		sql1.append(",LABEL_TUKAIKATA ");
		sql2.append(" ,? ");
		// #1964対応 2016.08.22 M.Akagi (E)

		sql.append(" INSERT INTO R_SYOHIN_ASN ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") VALUES ( ");
		sql.append(sql2.toString());
		sql.append(") ");
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	//Add 2015.07.10 DAI.BQ (E)


	/**
	 * 商品マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedSyohinDelUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE R_SYOHIN ");
		sql.append("   SET HENKO_DT        = '").append(MasterDT).append("',");
		sql.append("       UPDATE_TS       = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       UPDATE_USER_ID  = ?,");
		sql.append("       BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       BATCH_UPDATE_ID = '").append(BATCH_ID).append("', ");
		sql.append("       DELETE_FG       = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		// 2017.04.03 M.Akagi #4509 (S)
		sql.append("       ,PLU_HANEI_TIME  = NULL ");
		sql.append("       ,EMG_FLAG  = '" + EMG_OFF +  "' ");
		// 2017.04.03 M.Akagi #4509 (E)
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	//Add 2015.07.10 DAI.BQ (S)
	/**
	 * 商品マスタASNデータ削除用PreparedStatement
	 * @throws Exception
	 */
	private PreparedStatement getPreparedASNSyohinDelUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE R_SYOHIN_ASN ");
		sql.append("   SET UPDATE_TS       = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       UPDATE_USER_ID  = ?,");
		sql.append("       DELETE_FG       = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	//Add 2015.07.10 DAI.BQ (E)

	private void setPreparedSyohinDelUpSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		String str = null;
		int idx = 0;

		//更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("BY_NO"));

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("SYOHIN_CD"));

		//有効日
		idx++;
		str = rs.getString("YUKO_DT");
		if(str == null || str.trim().length() <= 0){
			pstmt.setString(idx, DateChanger.addDate(MasterDT, 1));
		}else{
			pstmt.setString(idx, str);
		}
	}

	//Add 2015.07.10 DAI.BQ (S)
	private void setPreparedASNSyohinDelUpSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		String str = null;
		int idx = 0;

		//更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("BY_NO"));

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("SYOHIN_CD"));

		//有効日
		idx++;
		str = rs.getString("YUKO_DT");
		if(str == null || str.trim().length() <= 0){
			pstmt.setString(idx, DateChanger.addDate(MasterDT, 1));
		}else{
			pstmt.setString(idx, str);
		}
	}
	//Add 2015.07.10 DAI.BQ (E)

	/**
	 * 商品マスタデータ更新SQL（INSERT時）
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	private void setPrepareSyohinSQL(PreparedStatement pstmt, ResultSet rs, boolean insertFg,String syohin_cd) throws IllegalArgumentException, Exception {

		int idx = 0;

		String str = "";

		//修正区分
		String syusei_kb = rs.getString("syusei_kb");

		if (insertFg) {
			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("bunrui1_cd"));

			//商品コード
			idx++;
			pstmt.setString(idx, syohin_cd);

			// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
			//旧商品コード
			idx++;
			// 2017.01.12 M.Akagi #3531 (S)
			//pstmt.setNull(idx, Types.VARCHAR);
			pstmt.setString(idx,  rs.getString("old_syohin_cd"));
			// 2017.01.12 M.Akagi #3531 (E)
			// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

			//有効日
			idx++;
//			pstmt.setString(idx, rs.getString("yuko_dt"));
			// 有効開始日が未入力の場合、管理日付の翌日でセットする
			if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx, startDt);
			} else {
				pstmt.setString(idx, rs.getString("yuko_dt"));
			}
		}
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		else {
			//旧商品コード
			idx++;
			pstmt.setString(idx, rs.getString("old_syohin_cd"));
		}
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

		//システム区分
		//業種区分


		//商品区分
		idx++;
		str = rs.getString("syohin_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_syohin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//桁数区分

		//分類２コード
		idx++;
//		str = rs.getString("bunrui2_cd");
		if(!SyohinCheckGRO.getTaikeiKirikaeJyotai().equals(mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEATO.getCode())) {
			str = rs.getString("bunrui2_cd");
		} else {
			str = rs.getString("rstk_bunrui2_cd");
		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_bunrui2_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//分類５コード
		idx++;
		str = rs.getString("bunrui5_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_bunrui5_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//品目コード

		//商品コード２
		if (insertFg) {

			str = syohin_cd;
//  2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(S)
//			if (str.substring(0, 7).equals("0000000") && !str.substring(0, 9).equals("000000000")) {
//				//UPC-Eの場合はUPC-Aに変換
//				str = ConvertUpcEtoUpcA.convert(str);
//			} else if ("02".equals(str.substring(0, 2))) {
//				// 02商品の場合はチェックデジットを付与
//				str = str.substring(0, 12).concat(mst001401_CheckDegitBean.getModulus10(str, 12));
//			}
//  2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(E)
			idx++;
			pstmt.setString(idx, str.trim());
		}

		//在庫用商品集計コード
		idx++;
		str = rs.getString("ZAIKO_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = syohin_cd;
			} else {
				str = rs.getString("rs_ZAIKO_SYOHIN_CD");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//情報系用商品集計コード
		idx++;
		str = rs.getString("JYOHO_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = syohin_cd;
			} else {
				str = rs.getString("rs_JYOHO_SYOHIN_CD");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		//JANメーカーコード
//		idx++;
//		str = rs.getString("maker_cd");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else {
//			str = rs.getString("rs_maker_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
// 2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)
		//漢字品名
		idx++;
		str = rs.getString("hinmei_kanji_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hinmei_kanji_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//漢字規格
		idx++;
		str = rs.getString("kikaku_kanji_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kanji_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//漢字規格2
		idx++;
		str = rs.getString("kikaku_kanji_2_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kanji_2_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//POSレシート品名(漢字)
		idx++;
		// 2016/10/20 T.Arimoto #2254対応（S) POSレシート品名(漢字)<- 漢字品名
		// str = rs.getString("rec_hinmei_kanji_na");
		// #6167 MB38 Mod 2020.07.22 KHAI.NN (S)
//		str = rs.getString("hinmei_kanji_na");
		str = rs.getString("rec_hinmei_kanji_na");
		// #6167 MB38 Mod 2020.07.22 KHAI.NN (E)
		// 2016/10/20 T.Arimoto #2254対応（E)
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2016/10/20 T.Arimoto #2254対応（S) POSレシート品名(漢字)<- 漢字品名
			// str = rs.getString("rs_rec_hinmei_kanji_na");
			// #6167 MB38 Mod 2020.07.22 KHAI.NN (S)
//			str = rs.getString("rs_hinmei_kanji_na");
			str = rs.getString("rs_rec_hinmei_kanji_na");
			// #6167 MB38 Mod 2020.07.22 KHAI.NN (E)
			// 2016/10/20 T.Arimoto #2254対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ品名
		idx++;
		str = rs.getString("hinmei_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hinmei_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ規格
		idx++;
		str = rs.getString("kikaku_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ規格2
		idx++;
		str = rs.getString("kikaku_kana_2_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kana_2_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//POSレシート品名(カナ)
		idx++;
		str = rs.getString("rec_hinmei_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_rec_hinmei_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//商品サイズ（幅）
		idx++;
		str = rs.getString("SYOHIN_WIDTH_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_WIDTH_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//商品サイズ（高さ）
		idx++;
		str = rs.getString("SYOHIN_HEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_HEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//商品サイズ（奥行き）
		idx++;
		str = rs.getString("SYOHIN_DEPTH_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_DEPTH_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//Ｅショップ区分

		//PB区分
//		idx++;
//		str = rs.getString("pb_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_pb_kb");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//サブクラスコード
//		idx++;
//		str = rs.getString("subclass_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_subclass_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//配布コード
//		idx++;
//		str = rs.getString("haifu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_haifu_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//税区分
		idx++;
		str = rs.getString("ZEI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2017/03/15 T.Arimoto #4358対応（S)
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				str = mst007501_ZeiKbDictionary.UTIZEI.getCode();
//			} else {
//				str = rs.getString("RS_ZEI_KB");
//			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			str = rs.getString("RS_ZEI_KB");
			// 2017/03/15 T.Arimoto #4358対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		// #1900対応 2016.08.04 M.Akagi (S)
		str = rs.getString("SIIRE_ZEI_KB");
		if (!isNotBlank(str)) {
			str = rs.getString("RS_SIIRE_ZEI_KB");
		}
		// #1900対応 2016.08.04 M.Akagi (E)
		String zeiKb = null ;
		zeiKb = str.trim();

		//税率区分
		idx++;
		str = rs.getString("TAX_RATE_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2017/03/15 T.Arimoto #4358対応（S)
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				str = mst012301_TaxRateKbDictionary.PERCENT_5.getCode();
//			} else {
//				str = rs.getString("rs_TAX_RATE_KB");
//			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			str = rs.getString("RS_TAX_RATE_KB");
			// 2017/03/15 T.Arimoto #4358対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		//売価配信区分
		idx++;
		str = rs.getString("BAIKA_HAISHIN_FG");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())){
				//  未記入ときの初期値セット （新規：「１」）
			//	str = mst011701_BaikaHaishinFlagDictionary.SURU.getCode();
			//}else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())){
			//	str = mst011701_BaikaHaishinFlagDictionary.SINAI.getCode();
			//}
			//pstmt.setString(idx, str.trim());
			//
			str = rs.getString("RS_BAIKA_HAISHIN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, mst011701_BaikaHaishinFlagDictionary.SINAI.getCode());
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)

		//原価単価
		idx++;
		str = rs.getString("gentanka_vl");
		// 2016/11/02 T.Arimoto #2258対応（S)
		if (!isNotBlank(str)) {
			// TR_SYOHINにてnullだった場合、R_SYOHINから取得

			str = rs.getString("rs_gentanka_nuki_vl");
		}
		// 2016/11/02 T.Arimoto #2258対応（E)
		//No.158 MSTB011 Add 2015.12.04 TU.TD (S)

		int zei_kb = 0;
		if (zeiKb.equals("1")) {
			zei_kb = 2;
		} else {
			zei_kb = Integer.parseInt(zeiKb);
		}

		//税率取得
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		// #1900対応 2016.08.04 M.Akagi (S)
		//str_tax_rt = rs.getString("tax_rt");
		str_tax_rt = rs.getString("SIIRETAX_RT");
		// #1900対応 2016.08.04 M.Akagi (E)

		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			// #1900対応 2016.08.04 M.Akagi (S)
			//str_tax_rt = rs.getString("rs_tax_rt");
			str_tax_rt = rs.getString("RS_SIIRETAX_RT");
			// #1900対応 2016.08.04 M.Akagi (E)
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}
		//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
		if (isNotBlank(str)) {
		//#6505 Mod 2022.06.29 DINH.TP (S)
			//No.158 MSTB011 Add 2015.12.04 TU.TD (S)
//			BigDecimal gentanka_vl =new BigDecimal(str.trim());
//			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionCostUnitMode());
//			if (zeiKb.equals("1")) {
//				str = ctu.getTaxIn().toString();
//			} else {
//				str = ctu.getTaxOut().toString();
//			}
//			//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
						zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
				if (zeiKb.equals("1")) {
					str = ctu.getTaxIn().toString();
				} else {
					str = ctu.getTaxOut().toString();
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			//#6505 Mod 2022.06.29 DINH.TP (E)
		} else {
			// 2016/11/02 T.Arimoto #2258対応（S)
//			str = rs.getString("rs_gentanka_vl");
//			if (isNotBlank(str)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			pstmt.setNull(idx, Types.DOUBLE);
			// 2016/11/02 T.Arimoto #2258対応（E)
		}

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//原価単価(税抜)
		idx++;
		//No.158 MSTB011 Del 2015.12.04 TU.TD (S)
		/*BigDecimal num =new BigDecimal("100");
		int zei_kb = Integer.parseInt(zeiKb);

		//税率取得
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		str_tax_rt = rs.getString("tax_rt");
		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			str_tax_rt = rs.getString("rs_tax_rt");
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}*/
		//No.158 MSTB011 Del 2015.12.04 TU.TD (E)
		//No.158 MSTB011 Add 2015.12.04 TU.TD (S)
		str = rs.getString("gentanka_vl");
		//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
		if (isNotBlank(str)) {
			//No.158 MSTB011 Mod 2015.12.04 TU.TD (S)
			/*BigDecimal gentanka_vl =new BigDecimal(str.trim());
			//円未満四捨五入の対応
			gentanka_vl = gentanka_vl.multiply(num);

			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
					&& ! gentanka_vl.equals("")){
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
						MoneyUtil.getFractionCostUnitMode());
				str = (ctu.getTaxOut().divide(num)).toString();
			}else{
				str =  "";
			}

			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				} else {
					pstmt.setNull(idx, Types.DOUBLE);
				}
			}else{
				pstmt.setNull(idx, Types.DOUBLE);
			}*/
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			//No.158 MSTB011 Mod 2015.12.04 TU.TD (E)
		}else{
			// 2016/11/02 T.Arimoto #2258対応（S)
			//pstmt.setNull(idx, Types.DOUBLE);
			str = rs.getString("RS_GENTANKA_NUKI_VL");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// 2016/11/02 T.Arimoto #2258対応（E)
		}
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//売価単価
		idx++;
		str = rs.getString("baitanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0001] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0001] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0002] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0002] Update to parse double for tanka (E)
			}
		} else {
			str = rs.getString("rs_baitanka_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0003] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0003] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0004] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0004] Update to parse double for tanka (E)
			}
		}

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//売価単価(税抜)
		idx++;
		// #1900対応 2016.08.04 M.Akagi (S)
		// 2016/11/02 T.Arimoto #2258対応（S)
		//str = rs.getString("ZEI_KB");
		//if (!isNotBlank(str)) {
		//	str = rs.getString("RS_ZEI_KB");
		//}
		//zeiKb = str.trim();
		String zeiKb_str = rs.getString("ZEI_KB");
		if (!isNotBlank(zeiKb_str)) {
			zeiKb_str = rs.getString("RS_ZEI_KB");
		}
		zeiKb = zeiKb_str.trim();
		// 2016/11/02 T.Arimoto #2258対応（S)


		zei_kb = 0;
		// 2016/11/02 T.Arimoto #2258対応（S)
//		if (zeiKb.equals("1")) {
//			zei_kb = 2;
//		} else {
//			zei_kb = Integer.parseInt(zeiKb);
//		}
		if( isNotBlank(zeiKb) ){
			zei_kb = Integer.parseInt(zeiKb);
		}
		// 2016/11/02 T.Arimoto #2258対応（E)

		//税率取得
		tax_rt = null ;
		str_tax_rt = null ;
		str_tax_rt = rs.getString("tax_rt");

		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			str_tax_rt = rs.getString("rs_tax_rt");
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}
		// #1900対応 2016.08.04 M.Akagi (E)

		if (isNotBlank(str)) {
			// #6505 Mod DINH.TP 2022.06.29 (S)
//			BigDecimal baitanka_vl = new BigDecimal(str.trim());
//
//			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
//					&& ! baitanka_vl.equals("")){
//				CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl,
//						MoneyUtil.getFractionSellUnitLen(), zei_kb, tax_rt,
//						MoneyUtil.getFractionSellUnitMode());
//				// 2016/11/02 T.Arimoto #2258対応（S)
//				//str = ctu.getTaxOut().toString();
//				str = MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()));
//				// 2016/11/02 T.Arimoto #2258対応（E)
//			}else{
//				// 2017/03/15 T.Arimoto #4358対応（S) // 内税以外の場合、売価（税込）をそのまま格納
//				//str =  "";
//				// 2017/03/15 T.Arimoto #4358対応（E)
//			}
//
//			if (isNotBlank(str)) {
//				if (!str.trim().equals(deleteString)) {
//					//2014.07.29 [CUS0005] Update to parse double for tanka (S)
//					//pstmt.setLong(idx, Long.parseLong(str.trim()));
//					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//					//2014.07.29 [CUS0005] Update to parse double for tanka (E)
//				} else {
//					//2014.07.29 [CUS0006] Update to parse double for tanka (S)
//					//pstmt.setNull(idx, Types.BIGINT);
//					pstmt.setNull(idx, Types.DOUBLE);
//					//2014.07.29 [CUS0006] Update to parse double for tanka (E)
//				}
//			} else {
//				//2014.07.29 [CUS0007] Update to parse double for tanka (S)
//				//pstmt.setNull(idx, Types.BIGINT);
//				pstmt.setNull(idx, Types.DOUBLE);
//				//2014.07.29 [CUS0007] Update to parse double for tanka (E)
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal baitanka_vl = new BigDecimal(str.trim());

				if (zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
						&& !baitanka_vl.equals("")) {
					CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl, MoneyUtil.getFractionSellUnitLen(),
							zei_kb, tax_rt, MoneyUtil.getFractionSellUnitMode());
					str = MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()));
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// #6505 Mod DINH.TP 2022.06.29 (E)
		}else{
			//2014.07.29 [CUS0008] Update to parse double for tanka (S)
			//pstmt.setNull(idx, Types.BIGINT);
			pstmt.setNull(idx, Types.DOUBLE);
			//2014.07.29 [CUS0008] Update to parse double for tanka (E)
		}
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//当初売価
		if (insertFg) {
			idx++;
			pstmt.setNull(idx, Types.BIGINT);
		}

//		if (insertFg) {
//
//			//当初売価（新規登録時は売価単価(税込)を設定する）
//			idx++;
//			str = rs.getString("baitanka_vl");
//			if (isNotBlank(str)) {
//				if (!str.trim().equals(deleteString)) {
//					pstmt.setInt(idx, Integer.parseInt(str.trim()));
//				} else {
//					pstmt.setNull(idx, Types.BIGINT);
//				}
//			} else {
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		}
//
//		if (!insertFg) {
//
//			//前回原価単価
//			idx++;
//			str = rs.getString("rs_gentanka_vl");
//			str = rs.getString("pre_gentanka_vl");
//			if (isNotBlank(str)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
//
//			//前回売価単価
//			idx++;
//			str = rs.getString("rs_baitanka_vl");
//			str = rs.getString("pre_baitanka_vl");
//			if (isNotBlank(str)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.BIGINT);
//			}
//		}

		//特別原価

		//メーカー希望小売り価格
		idx++;
		str = rs.getString("maker_kibo_kakaku_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0009] Update to parse double for kakaku (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0009] Update to parse double for kakaku (E)
			} else {
				//2014.07.29 [CUS0010] Update to parse double for kakaku (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0010] Update to parse double for kakaku (E)
			}
		} else {
			str = rs.getString("rs_maker_kibo_kakaku_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0011] Update to parse double for kakaku (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0011] Update to parse double for kakaku (E)
			} else {
				//2014.07.29 [CUS0012] Update to parse double for kakaku (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0012] Update to parse double for kakaku (E)
			}
		}

		//仕入先コード
		idx++;
		str = rs.getString("siiresaki_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_siiresaki_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//代表配送先コード

		//店別仕入先管理コード
//		idx++;
//		str = rs.getString("ten_siiresaki_kanri_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_ten_siiresaki_kanri_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//仕入先品番
//		idx++;
//		str = rs.getString("siire_hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_siire_hinban_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}


		//発注商品コード区分

		//発注商品コード
		if (insertFg) {
			idx++;
//			str = syohin_cd;
//			if (isNotBlank(str)) {
//				if (!"0".equals(str.substring(0,1))){
//
//					pstmt.setString(idx, str);
//
//				}else if ("0400".equals(str.substring(0,4))){
//
//					pstmt.setString(idx, str);
//
//				}else if ("0000045".equals(str.substring(0,7))
//						|| "0000049".equals(str.substring(0,7))){
//
//					pstmt.setString(idx, str);
//
//				}else if ("00000".equals(str.substring(0,5))
//						&& Integer.parseInt(str.substring(5,6)) >= 3){
//
//					pstmt.setString(idx, str);
//
//				}else if ( Integer.parseInt(str.substring(1,2)) == 2
//						|| Integer.parseInt(str.substring(3,4)) == 4
//						||	Integer.parseInt(str.substring(4,5)) == 5){
//
//					pstmt.setString(idx, str);
//
//				}else {
//
//					str = mst001401_CheckDegitBean.getModulus10Code(str.substring(1),12);
//					pstmt.setString(idx, str);
//				}

// 2013.05.21 [MSTC00004] 商品コードチェック仕様変更対応
			str = syohin_cd;
//			str = MstCommonUtil.convertHachuSyohinCdFromSyohinCd(syohin_cd);
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//EOS区分
		idx++;
		str = rs.getString("eos_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst001201_EosKbDictionary.EOS_TAISYO.getCode();
			} else {
				str = rs.getString("rs_eos_kb");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注単位呼称
		idx++;
		str = rs.getString("hachu_tani_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst012101_HachuTaniKbDictionary.KO.getCode();
			} else {
				str = rs.getString("rs_hachu_tani_na");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売単位呼称
		idx++;
		str = rs.getString("HANBAI_TANI");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst012201_HanbaiTaniKbDictionary.KO.getCode();
			} else {
				str = rs.getString("rs_HANBAI_TANI");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注単位(入数)
		idx++;
		str = rs.getString("hachutani_irisu_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rs_hachutani_irisu_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		// 最大発注単位
		idx++;
		str = rs.getString("max_hachutani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				// #33102 Mod 2025.04.09 HUY.LTH (S)
				// str = "999";
				str = MAX_HACHU_QT_DEFAULT;
				// #33102 Mod 2025.04.09 HUY.LTH (E)
			} else {
				str = rs.getString("rs_max_hachutani_qt");
			}
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//ケース発注区分
		idx++;
		str = rs.getString("CASE_HACHU_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst012001_CaseHachuKbDictionary.TAISYOGAI.getCode();
			} else {
				str = rs.getString("rs_CASE_HACHU_KB");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//バラ入数
		idx++;
		str = rs.getString("BARA_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
//				pstmt.setNull(idx, Types.INTEGER);
				pstmt.setInt(idx, 1);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = "1";
			} else {
				str = rs.getString("rs_BARA_IRISU_QT");
			}
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
//				pstmt.setNull(idx, Types.INTEGER);
				pstmt.setInt(idx, 1);
			}
		}

		//店舗発注開始日
		idx++;
		str = rs.getString("ten_hachu_st_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//			pstmt.setNull(idx, Types.VARCHAR);
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				// ===BEGIN=== Modify by kou 2006/10/25
				// 未入力の場合の店舗発注開始日と終了日の設定 また変更
////				↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
//				str = rs.getString("hanbai_st_dt");
//				if (isNotBlank(str)) {
//					str = DateChanger.addDate(rs.getString("hanbai_st_dt"), 1);
//				} else {
////				↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑
//					if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
//						str = DateChanger.addDate(MasterDT, 2);
//					}else{
//						str = DateChanger.addDate(rs.getString("yuko_dt"), 1);
//					}
////				↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
//				}
////				↑↑2006.09.28 H.Yamamoto カスタマイズ修正↑↑

				//有効日と同じ日を設定
				if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
					str = DateChanger.addDate(MasterDT, 1);
				}else{
					str = rs.getString("yuko_dt");
				}
				// ===END=== Modify by kou 2006/10/25
				pstmt.setString(idx, str);
			} else {
				str = rs.getString("rs_ten_hachu_st_dt");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
//			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		}

		//店舗発注終了日
		idx++;
		str = rs.getString("ten_hachu_ed_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//			pstmt.setNull(idx, Types.VARCHAR);
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				// ===BEGIN=== Modify by kou 2006/10/25
				//販売終了日の前日を設定。但し販売終了日が"99999999"の場合は、"99999999"を設定
				//pstmt.setString(idx, "99999999");
				if(rs.getString("hanbai_ed_dt") == null || "99999999".equals(rs.getString("hanbai_ed_dt")) || "".equals(rs.getString("hanbai_ed_dt"))) {
					pstmt.setString(idx, "99999999");
				} else {
					pstmt.setString(idx, DateChanger.addDate(rs.getString("hanbai_ed_dt"), -1));
				}
				// ===END=== Modify by kou 2006/10/25
			} else {
				str = rs.getString("rs_ten_hachu_ed_dt");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
//			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		}

		//販売開始日
		idx++;
		str = rs.getString("hanbai_st_dt");
		if (isNotBlank(str)) {
//			↓↓2006.12.21 H.Yamamoto カスタマイズ修正↓↓
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				String strw = rs.getString("yuko_dt");
				if(strw == null || strw.trim().equals("")){
					strw = DateChanger.addDate(MasterDT, 1);
				}
				if (isNotBlank(strw)) {
					if (Long.parseLong(str) < Long.parseLong(strw)) {
						str = strw;
					}
				}
			}
//			↑↑2006.12.21 H.Yamamoto カスタマイズ修正↑↑
			pstmt.setString(idx, str);
		} else {
//			↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//			pstmt.setNull(idx, Types.VARCHAR);
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
					str = DateChanger.addDate(MasterDT, 1);
				}else{
					str = rs.getString("yuko_dt");
				}
				pstmt.setString(idx, str);
			} else {
				str = rs.getString("rs_hanbai_st_dt");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
//			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		}

		//販売終了日
		idx++;
		str = rs.getString("hanbai_ed_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
//			↓↓2006.08.30 H.Yamamoto カスタマイズ修正↓↓
//			pstmt.setNull(idx, Types.VARCHAR);
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				pstmt.setString(idx, "99999999");
			} else {
				str = rs.getString("rs_hanbai_ed_dt");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
//			↑↑2006.08.30 H.Yamamoto カスタマイズ修正↑↑
		}

		//販売期間区分


		//定貫区分
		idx++;
		str = rs.getString("TEIKAN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst001101_TeikanKbDictionary.TEIKAN.getCode();
			} else {
				str = rs.getString("RS_TEIKAN_KB");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//相場商品区分
		idx++;
		str = rs.getString("SOBA_SYOHIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst011301_SobaSyohinKbDictionary.HISOBA_SYOHIN.getCode();
			} else {
				str = rs.getString("RS_SOBA_SYOHIN_KB");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//納品期限区分
		//納品期限

		//①便区分
		idx++;
		str = rs.getString("BIN_1_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_BIN_1_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//①発注パターン区分


		//①締め時間
		idx++;
		str = rs.getString("SIME_TIME_1_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SIME_TIME_1_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//①センタ納品リードタイム
		//①店納品リードタイム
		//①店納品時間帯


		//②便区分
		idx++;
		str = rs.getString("BIN_2_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_BIN_2_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//②発注パターン区分


		//②締め時間
		idx++;
		str = rs.getString("SIME_TIME_2_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SIME_TIME_2_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//②センタ納品リードタイム
		//②店納品リードタイム
		//②店納品時間帯
		//③便区分
		//③発注パターン区分
		//③締め時間
		//③センタ納品リードタイム
		//③店納品リードタイム
		//③店納品時間帯
		//センタ納品リードタイム
		//優先便区分

		//F便区分
		idx++;
		str = rs.getString("F_BIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst011901_FbinKbDictionary.TAISYOGAI.getCode();
			} else {
				str = rs.getString("rs_F_BIN_KB");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//物流区分
		idx++;
		str = rs.getString("buturyu_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst001701_ButuryuKbDictionary.CHOKUNO.getCode();
			} else {
				str = rs.getString("rs_buturyu_kb");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GOT無条件表示対象

		//GOT表示開始月
		idx++;
		str = rs.getString("GOT_START_MM");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GOT_START_MM");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GOT表示終了月
		idx++;
		str = rs.getString("GOT_END_MM");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GOT_END_MM");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注停止区分


		//センター在庫区分
		idx++;
		str = rs.getString("CENTER_ZAIKO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_CENTER_ZAIKO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//納品商品コード
		if (insertFg) {
			idx++;
			str = syohin_cd;
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//入荷時商品コード
		idx++;
		str = rs.getString("NYUKA_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NYUKA_SYOHIN_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//入荷時商品コード２
		idx++;
		str = rs.getString("NYUKA_SYOHIN_2_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_NYUKA_SYOHIN_2_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ITFコード
		idx++;
		str = rs.getString("ITF_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_ITF_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GTINコード
		if (insertFg) {
			idx++;
			str = syohin_cd;
			if (isNotBlank(str)) {
				pstmt.setString(idx, StringUtility.charFormat(str,str.length()+1,"0",true));
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ベンダーメーカーコード
		idx++;
		str = rs.getString("VENDER_MAKER_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_VENDER_MAKER_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//在庫センターコード
		//在庫補充発注先
		//センター重量
		//外箱サイズ幅
		//外箱サイズ高さ
		//外箱サイズ奥行き
		//外箱重量
//2011.03.04 T.Urano Add 平均重量追加対応 Start
		idx++;
		str = rs.getString("PACK_WEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_PACK_WEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}
//2011.03.04 T.Urano Add 平均重量追加対応 End

		//センター発注単位区分
		//センター発注単位数

		//センターバラ入数
		idx++;
		str = rs.getString("CENTER_BARA_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_BARA_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター入り数
		idx++;
		str = rs.getString("CENTER_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CENTER_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//ケース入り数
		idx++;
		str = rs.getString("CASE_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CASE_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//梱り合せ数
		idx++;
		str = rs.getString("CENTER_IRISU_2_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_IRISU_2_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最低在庫数(発注点)
		idx++;
		str = rs.getString("MIN_ZAIKOSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_MIN_ZAIKOSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最大在庫数
		idx++;
		str = rs.getString("MAX_ZAIKOSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_MAX_ZAIKOSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//基準在庫日数

		//最小在庫日数
		idx++;
		str = rs.getString("MIN_ZAIKONISSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_MIN_ZAIKONISSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最大在庫日数
		idx++;
		str = rs.getString("MAX_ZAIKONISSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_MAX_ZAIKONISSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター許容区分
		idx++;
		str = rs.getString("CENTER_KYOYO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CENTER_KYOYO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//センター許容日
		idx++;
//		long val = 0;
//		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//
//			if (changeNulltoZero(rs.getString("syohi_kigen_dt")) == 1
//					|| changeNulltoZero(rs.getString("syohi_kigen_dt")) == 2){
//
//				val = 1;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 3
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 8 ){
//
//				val = changeNulltoZero(rs.getString("syohi_kigen_dt"))	- 1;
//
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) == 9 ){
//
//				val = 7;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) == 10
//					|| changeNulltoZero(rs.getString("syohi_kigen_dt")) == 11){
//
//				val = 8;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) == 12
//					|| changeNulltoZero(rs.getString("syohi_kigen_dt")) == 13){
//
//				val = 9;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 14 ){
//
//				val = Math.round(Double.parseDouble(rs.getString("syohi_kigen_dt").trim())*0.7);
//			}
//
//			pstmt.setLong(idx, val);
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//		pstmt.setNull(idx, Types.INTEGER);
//		↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
//		str = rs.getString("center_kyoyo_dt");
//		if (isNotBlank(str)) {
//			pstmt.setInt(idx, Integer.parseInt(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
		str = rs.getString("CENTER_KYOYO_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CENTER_KYOYO_DT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター賞味期間区分
		idx++;
		str = rs.getString("CENTER_SYOMI_KIKAN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CENTER_SYOMI_KIKAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//センター賞味期間
		idx++;
		str = rs.getString("CENTER_SYOMI_KIKAN_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_SYOMI_KIKAN_DT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//店グルーピングNO(物流）
		//TC情報
		//納品温度帯
		//横もち区分
		//品揃区分

		//本部在庫区分
//		idx++;
//		str = rs.getString("honbu_zai_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_honbu_zai_kb");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.CHAR);
//			}
//		}
//		pstmt.setNull(idx, Types.CHAR);

		//店在庫区分
		//販売政策区分
		//返品契約書NO
		//返品原価

		//CGC返品区分
		idx++;
		str = rs.getString("CGC_HENPIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst011201_CgcHenpinKbDictionary.NASI.getCode();
			} else {
				str = rs.getString("rs_CGC_HENPIN_KB");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売期限区分
		idx++;
		str = rs.getString("HANBAI_LIMIT_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HANBAI_LIMIT_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//販売期限
		idx++;
//		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//			int value = 0;
//			if (changeNulltoZero(rs.getString("syohi_kigen_dt")) == 1){
//
//				value = 0;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 2
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 8 ){
//
//				value = -1;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 9
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 15 ){
//
//				value = -2;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 16
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 30 ){
//
//				value = -3;
//			}if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 31
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 90 ){
//
//				value = -5;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 91
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 180 ){
//
//				value = -7;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 181
//					&& changeNulltoZero(rs.getString("syohi_kigen_dt")) <= 360 ){
//
//				value = -20;
//			}else if (changeNulltoZero(rs.getString("syohi_kigen_dt")) >= 361 ){
//
//				value = -30;
//			}
//			pstmt.setInt(idx, value);
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
//		pstmt.setNull(idx, Types.INTEGER);
//		↓↓2006.09.28 H.Yamamoto カスタマイズ修正↓↓
//		str = rs.getString("hanbai_limit_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.INTEGER);
//		}
		str = rs.getString("HANBAI_LIMIT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_HANBAI_LIMIT_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//PLU送信日
		idx++;
		str = rs.getString("plu_send_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				str = rs.getString("tmp_yuko_dt");
				pstmt.setString(idx, str);
			}
		} else {
			//未入力時
			str = rs.getString("tmp_yuko_dt");
			pstmt.setString(idx, str);
		}
		// 2016/11/22 T.Arimoto #2803対応（S)
		String pluSendDt = str;
		// 2016/11/22 T.Arimoto #2803対応（E)

		//キーPLU対象

		//PLU区分
		if (insertFg) {
			idx++;
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//酒税報告分類
		idx++;
		str = rs.getString("syuzei_hokoku_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = "00";
			} else {
				str = rs.getString("rs_syuzei_hokoku_kb");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//酒内容量
		idx++;
		str = rs.getString("SAKE_NAIYORYO_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_SAKE_NAIYORYO_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//タグ表示売価
		//消札売価
		//よりどり種類
		//よりどり価格
		//よりどり数量

		//ブランドコード
//		idx++;
//		str = rs.getString("bland_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_bland_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}
//		pstmt.setNull(idx, Types.VARCHAR);

		//シーズンコード
		//服種コード
		//スタイルコード
		//シーンコード
		//性別コード
		//年代コード
		//世代コード
		//素材コード
		//パターンコード
		//織り編みコード


		//付加機能コード
//		idx++;
//		str = rs.getString("syurui_dosuu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, StringUtility.charFormat(str.trim(),3,"0",true));
//		} else {
//			str = rs.getString("rs_huka_kino_cd");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//袖丈コード
		//サイズコード
		//カラーコード
		//契約数
		//契約パターン
		//契約開始期間
		//契約終了期間
		//組数区分
		//値札区分
		//値札受渡日
		//値札受渡方法

		//PC発行区分
		idx++;
		str = rs.getString("pc_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = mst010701_PcKbDictionary.HAKKO_NASI.getCode();
			} else {
				str = rs.getString("rs_pc_kb");
			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//台紙NO
		idx++;
		str = rs.getString("daisi_no_nb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2017/03/15 T.Arimoto #4358対応（S) 初期設定処理を削除します
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(S)
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				str = mst012401_DaishiNoDictionary.NASI.getCode();
//			} else {
//				str = rs.getString("rs_daisi_no_nb");
//			}
			//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応(E)
			str = rs.getString("rs_daisi_no_nb");
			// 2017/03/15 T.Arimoto #4358対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ユニットプライス-単位区分
		idx++;
		str = rs.getString("unit_price_tani_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = "0";
			} else {
				str = rs.getString("rs_unit_price_tani_kb");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ユニットプライス-内容量
		idx++;
		str = rs.getString("unit_price_naiyoryo_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_unit_price_naiyoryo_qt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//ユニットプライス-基準単位量
		idx++;
		str = rs.getString("unit_price_kijun_tani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_unit_price_kijun_tani_qt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//消費期限区分
//		idx++;
//		str = rs.getString("SYOHI_KIGEN_KB");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_SYOHI_KIGEN_KB");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		}

		//#1417対応 2016.06.03 M.Kanno (S)
		//消費期限
		idx++;
		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//			pstmt.setInt(idx, Integer.parseInt(str.trim()));
//		} else {
//			str = rs.getString("rs_syohi_kigen_dt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		//#1417対応 2016.06.03 M.Kanno (E)
//		}

		//商品台帳（店舗）
//		idx++;
//		str = rs.getString("daicho_tenpo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_daicho_tenpo_kb");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.CHAR);
//			}
//		}


		//商品台帳（本部）
//		idx++;
//		pstmt.setNull(idx, Types.CHAR);

		//商品台帳（仕入先）
//		idx++;
//		pstmt.setNull(idx, Types.CHAR);

		//棚ＮＯ
		//段ＮＯ
		//リベート対象フラグ
		//マークグループ
		//マークコード
		//輸入品区分
		//原産国/配布コード
		//大属性１
		//小属性１
		//大属性２
		//小属性２
		//大属性３
		//小属性３
		//大属性４
		//小属性４
		//大属性５
		//小属性５
		//大属性６
		//小属性６
		//大属性７
		//小属性７
		//大属性８
		//小属性８
		//大属性９
		//小属性９
		//大属性１０
		//小属性１０


//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		idx++;
		str = rs.getString("free_1_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_1_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意区分２
		idx++;
		str = rs.getString("free_2_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_2_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		//任意区分３
		idx++;
		str = rs.getString("free_3_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_3_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意区分４
		idx++;
		str = rs.getString("free_4_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// 	str = rs.getString("rs_free_4_kb");
			// if (isNotBlank(str)) {
			// 	pstmt.setString(idx, str);
			// } else {
			// 	pstmt.setNull(idx, Types.VARCHAR);
			// }
			pstmt.setNull(idx, Types.VARCHAR);
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		//任意区分５
		idx++;
		str = rs.getString("free_5_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_5_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//コメント１
		idx++;
		str = rs.getString("comment_1_tx");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_comment_1_tx");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//コメント２
		idx++;
		str = rs.getString("comment_2_tx");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_comment_2_tx");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意コード
		idx++;
		str = rs.getString("free_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_free_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		//FUJI商品区分
//		idx++;
//		str = rs.getString("fuji_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				pstmt.setString(idx, "0");
//			} else {
//				str = rs.getString("rs_fuji_syohin_kb");
//				if (isNotBlank(str)) {
//					pstmt.setString(idx, str);
//				} else {
//					pstmt.setNull(idx, Types.VARCHAR);
//				}
//			}
//		}

		//コメント
		//自動削除対象区分
		//マスタ使用不可区分
		//廃番実施フラグ
		//新規登録日
		//変更日
// 2017/02/27 Mod Li.Sheng #4205 対応（S)
		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
		//年齢制限区分
//		idx++;
//		str = rs.getString("nenrei_seigen_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//年齢
//		idx++;
//		str = rs.getString("nenrei");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//瓶区分
//		idx++;
//		str = rs.getString("kan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//保証金
//		idx++;
//		str = rs.getString("hoshoukin");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)
		
		//年齢制限区分
		idx++;
		str = rs.getString("nenrei_seigen_kb");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NENREI_SEIGEN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}

		//年齢
		idx++;
		str = rs.getString("nenrei");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NENREI");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		//瓶区分
		idx++;
		str = rs.getString("kan_kb");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}

		//保証金
		idx++;
		str = rs.getString("hoshoukin");

		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HOSHOUKIN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}
// 2017/02/27 Mod Li.Sheng #4205 対応（E)	
		// 2016/10/26 T.Arimoto #2256対応（S)
		// 2016/11/22 T.Arimoto #2803対応（S)
		// 緊急配信フラグ
//		if (insertFg) {
//			idx++;
//			str = rs.getString("yuko_dt");
//			if (str.equals(MasterDT)) {
//				// オンライン日付の新規登録・修正登録は緊急配信フラグON
//				pstmt.setString(idx, EMG_ON);
//			} else {
//				pstmt.setString(idx, EMG_OFF);
//			}
//		}
		// PLU反映時間の取得
		str = rs.getString("PLU_HANEI_TIME");
		if (isNotBlank(str)) {
			if (str.trim().equals(deleteString)) {
				str= "";
			}
		} else {
			str = rs.getString("RS_PLU_HANEI_TIME");
		}
		idx++;
		if( isNotBlank( pluSendDt ) ){
			if( pluSendDt.equals(MasterDT) ){
				// PLU送信日がオンライン日付だった場合
				pstmt.setString(idx, EMG_ON);
			} else if( isNotBlank(str) ) {
				// PLU送信日がオンライン日付以外でPLU送信時間が設定されている場合
				pstmt.setString(idx, EMG_ON);
			} else {
				// 上記以外
				pstmt.setString(idx, EMG_OFF);
			}
		} else {
			// PLU送信日が空の場合
			pstmt.setString(idx, EMG_OFF);
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		// 2016/10/26 T.Arimoto #2256対応（E)

		//初回登録日


		//初回登録社員ID
		if (insertFg) {
			idx++;
			str = rs.getString("by_no");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//作成年月日

		//作成者ID
//		if (insertFg) {
			idx++;
			str = rs.getString("by_no");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//更新年月日

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//バッチ更新年月日
		//バッチ更新者ID


		if (!insertFg) {

			// 2016/10/25 T.Arimoto #2256対応（S)
			// 原価 世代判定格納処理
			String tr_yuko_dt = mst000401_LogicBean.chkNullString(rs.getString("YUKO_DT")).trim();// 有効日(TR_YUKO_DT)
			String tr_gentanka_vl = mst000401_LogicBean.chkNullString(rs.getString("GENTANKA_VL")).trim();// 原単価 税抜き(TR_GENTANKA_VL)

			String cur_generation_yuko_dt = mst000401_LogicBean.chkNullString(rs.getString("CUR_GENERATION_YUKO_DT")).trim();// 現世代 有効日
			String one_generation_yuko_dt = mst000401_LogicBean.chkNullString(rs.getString("ONE_GENERATION_YUKO_DT")).trim();// １世代前 有効日
			String two_generation_yuko_dt = mst000401_LogicBean.chkNullString(rs.getString("TWO_GENERATION_YUKO_DT")).trim();// ２世代前 有効日
			String cur_generation_gentanka_vl = mst000401_LogicBean.chkNullString(rs.getString("CUR_GENERATION_GENTANKA_VL")).trim();// 現世代 原単価
			String one_generation_gentanka_vl = mst000401_LogicBean.chkNullString(rs.getString("ONE_GENERATION_GENTANKA_VL")).trim();// １世代前 原単価
			String two_generation_gentanka_vl = mst000401_LogicBean.chkNullString(rs.getString("TWO_GENERATION_GENTANKA_VL")).trim();// ２世代前 原単価

			String pre_two_generation_yuko_dt = mst000401_LogicBean.chkNullString(rs.getString("PRE_TWO_GENERATION_YUKO_DT")).trim();// １世代前レコードの２世代前 有効日
			String pre_two_generation_gentanka_vl = mst000401_LogicBean.chkNullString(rs.getString("PRE_TWO_GENERATION_GENTANKA_VL")).trim();// １世代前レコードの２世代前 原単価

// 2017/02/27 Add Li.Sheng #4205 対応（S)	
			if ("".equals(tr_gentanka_vl)) {
				tr_gentanka_vl = cur_generation_gentanka_vl;
			}
// 2017/02/27 Add Li.Sheng #4205 対応（E)
									
			if(jp.co.vinculumjapan.mdware.common.util.StringUtility.decimalCompare(tr_gentanka_vl, cur_generation_gentanka_vl) != 0){
				// 現世代原価と現在の原価が異なっていた場合
				if( tr_yuko_dt.equals(cur_generation_yuko_dt) ){
					// 現在の有効日と現世代の有効日が等しかった場合

					if(jp.co.vinculumjapan.mdware.common.util.StringUtility.decimalCompare(tr_gentanka_vl, one_generation_gentanka_vl) == 0 ){
						// 1世代前原価と現在の原価が等しかった場合

						// 1世代前の原価と有効日を現世代の原価と有効日に設定
						cur_generation_yuko_dt = one_generation_yuko_dt;
						cur_generation_gentanka_vl = one_generation_gentanka_vl;
						// 2世代前の原価と有効日を1世代前の原価と有効日に設定
						one_generation_yuko_dt = two_generation_yuko_dt;
						one_generation_gentanka_vl = two_generation_gentanka_vl;

						// 過去の直近のデータから2世代前の原価と有効日を取得し、2世代前の原価と有効日に設定
						two_generation_yuko_dt = pre_two_generation_yuko_dt;
						two_generation_gentanka_vl =pre_two_generation_gentanka_vl;
					} else {
						// 現世代の原価と有効日に現在の原価と有効日を設定
						cur_generation_yuko_dt = tr_yuko_dt;
						cur_generation_gentanka_vl = tr_gentanka_vl;
					}

				} else {
					// 現在の有効日と現世代の有効日が異なっていた場合

					// 1世代前の原価と有効日を2世代前の原価と有効日に設定
					two_generation_yuko_dt = one_generation_yuko_dt;
					two_generation_gentanka_vl = one_generation_gentanka_vl;
					// 現世代の原価と有効日を1世代前の原価と有効日に設定
					one_generation_yuko_dt = cur_generation_yuko_dt;
					one_generation_gentanka_vl = cur_generation_gentanka_vl;
					// 現在の原価と有効日を現世代の原価と有効日に設定
					cur_generation_yuko_dt = tr_yuko_dt;
					cur_generation_gentanka_vl = tr_gentanka_vl;
				}
			} else {
				// 現世代原価と現在の原価が等しかった場合、世代原価はそのまま
			}

//			// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
//			// 原価単価
//			idx++;
//			str = rs.getString("gentanka_vl");
//			if (isNotBlank(str)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.NUMERIC);
//			}
//			// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)


			idx++;
			pstmt.setString(idx, cur_generation_yuko_dt); // 有効日（現世代）

			// 原単価（現世代）
			idx++;
			if (isNotBlank(cur_generation_gentanka_vl)) {
				pstmt.setDouble(idx, Double.parseDouble(cur_generation_gentanka_vl.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}

			idx++;
			pstmt.setString(idx, one_generation_yuko_dt); // 有効日（１世代前）

			// 原価単価（１世代前）
			idx++;
			if (isNotBlank(one_generation_gentanka_vl)) {
				pstmt.setDouble(idx, Double.parseDouble(one_generation_gentanka_vl.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}

			idx++;
			pstmt.setString(idx, two_generation_yuko_dt); // 有効日（２世代前）

			// 原価単価（２世代前）
			idx++;
			if (isNotBlank(two_generation_gentanka_vl)) {
				pstmt.setDouble(idx, Double.parseDouble(two_generation_gentanka_vl.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// 2016/10/25 T.Arimoto #2256対応（E)

			// #1900対応 2016.08.04 M.Akagi (S)
			// 仕入税区分
			idx++;
			str = rs.getString("SIIRE_ZEI_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SIIRE_ZEI_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 仕入税率区分
			idx++;
			str = rs.getString("SIIRE_TAX_RATE_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SIIRE_TAX_RATE_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 卸税区分
			idx++;
			str = rs.getString("OROSI_ZEI_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_ZEI_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			String oZeiKb = null ;
			oZeiKb = StringUtils.trim(str);

			// 卸税率区分
			idx++;
			str = rs.getString("OROSI_TAX_RATE_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_TAX_RATE_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			String oZeiRtKb = null ;
			oZeiRtKb = StringUtils.trim(str);

			// 卸売価単価
			idx++;
			// 2016/11/02 T.Arimoto #2258対応（S)
			// str = rs.getString("OROSI_BAITANKA_VL");
			str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			if(!isNotBlank(str)){
				// TR_SYOHINにて空欄だった場合
				str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
			}
			// 2016/11/02 T.Arimoto #2258対応（E)
			if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)){
				zeiKb = oZeiKb;
			}

			zei_kb = 0;
			if (zeiKb.equals("1")) {
				zei_kb = 2;
			} else {
				zei_kb = Integer.parseInt(zeiKb);
			}

			//税率取得
			tax_rt = null ;
			str_tax_rt = null ;
			if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)) {
				str_tax_rt = rs.getString("OROSHITAX_RT");
				if (isNotBlank(str_tax_rt)) {
					if (!str_tax_rt.trim().equals(deleteString)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				} else {
					str_tax_rt = rs.getString("RS_OROSHITAX_RT");
					if (isNotBlank(str_tax_rt)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				}
			} else {
				str_tax_rt = rs.getString("tax_rt");
				if (isNotBlank(str_tax_rt)) {
					if (!str_tax_rt.trim().equals(deleteString)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				} else {
					str_tax_rt = rs.getString("rs_tax_rt");
					if (isNotBlank(str_tax_rt)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				}
			}

			if (isNotBlank(str)) {
			// #6505 Mod 2022.06.29 DINH.TP（S)
//				BigDecimal gentanka_vl =new BigDecimal(str.trim());
//				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//						MoneyUtil.getFractionCostUnitMode());
//				if (zeiKb.equals("1")) {
//					str = ctu.getTaxIn().toString();
//				} else {
//					str = ctu.getTaxOut().toString();
//				}
//				if (!str.trim().equals(deleteString)) {
//					// 2016/11/02 T.Arimoto #2258対応（S)
//					//pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//					pstmt.setDouble(idx, Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
//					// 2016/11/02 T.Arimoto #2258対応（E)
//				} else {
//					pstmt.setNull(idx, Types.DOUBLE);
//				}
				if (!str.trim().equals(deleteString)) {
					BigDecimal gentanka_vl = new BigDecimal(str.trim());
					CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
							zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
					if (zeiKb.equals("1")) {
						str = ctu.getTaxIn().toString();
					} else {
						str = ctu.getTaxOut().toString();
					}
					pstmt.setDouble(idx, Double
							.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
				} else {
					pstmt.setNull(idx, Types.DOUBLE);
				}
			// #6505 Mod 2022.06.29 DINH.TP（E)
			} else {
				// 2016/11/02 T.Arimoto #2258対応（S)
//				str = rs.getString("RS_OROSI_BAITANKA_VL");
//				if (isNotBlank(str)) {
//					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//				} else {
//					pstmt.setNull(idx, Types.DOUBLE);
//				}
				pstmt.setNull(idx, Types.DOUBLE);
				// 2016/11/02 T.Arimoto #2258対応（E)
			}

			// 卸売価単価（税抜）
			idx++;
			str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// #1900対応 2016.08.04 M.Akagi (E)

			// #1964対応 2016.08.22 M.Akagi (S)
			// 最低発注数
			idx++;
			str = rs.getString("MIN_HACHU_QT");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				// #33102 Mod 2025.04.09 HUY.LTH (S)
				//str = rs.getString("RS_MIN_HACHU_QT");
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
					str = MIN_HACHU_QT_DEFAULT;
				} else {
					str = rs.getString("RS_MIN_HACHU_QT");
				}
				// #33102 Mod 2025.04.09 HUY.LTH (E)
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 発注不可フラグ
			idx++;
			str = rs.getString("HACHU_FUKA_FLG");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_HACHU_FUKA_FLG");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					// 2017/03/15 T.Arimoto #4358対応（S)
					//pstmt.setNull(idx, Types.VARCHAR);
					pstmt.setString(idx, HachuFukaYobiKbDictionary.KA.getCode());
					// 2017/03/15 T.Arimoto #4358対応（E)
				}
			}

			// 規格内容
			idx++;
			str = rs.getString("PER_TXT");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_PER_TXT");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 消費期限パターン
			idx++;
			str = rs.getString("SYOHI_KIGEN_HYOJI_PATTER");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SYOHI_KIGEN_HYOJI_PATTER");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// PLU反映時間
			idx++;
			str = rs.getString("PLU_HANEI_TIME");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_PLU_HANEI_TIME");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// #1964対応 2016.08.22 M.Akagi (E)

			// 2016/10/25 Li.Sheng #2258 対応（S)
			// 卸売価単価(小売税) (Update)
			idx++;
			str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			// 2016/11/04 T.Arimoto #2258 対応（S)
			if(!isNotBlank(str)){
				// TR_SYOHINが空欄だった場合
				str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
			}
			// 2016/11/04 T.Arimoto #2258 対応（E)

			if (isNotBlank(str)) {

				String temp_orosi_st_zei_kb = rs.getString("ZEI_KB"); // 税区分
				if (!isNotBlank(temp_orosi_st_zei_kb)) {
					temp_orosi_st_zei_kb = rs.getString("RS_ZEI_KB");
				}

				String temp_tax_rt = rs.getString("TAX_RT");
				if (!isNotBlank(temp_tax_rt)) {
					temp_tax_rt = rs.getString("RS_TAX_RT");
				}
				BigDecimal temp_orosi_tax_rt = new BigDecimal(temp_tax_rt); // 税率

				int temp_orosi_zei_kb = 0; // 計算用税区分

				// 内税か判定
				if ("1".equals(temp_orosi_st_zei_kb)) {
					temp_orosi_zei_kb = 2;
				} else {
					temp_orosi_zei_kb = Integer.parseInt(temp_orosi_st_zei_kb);
				}

				// 卸売価単価(小売税)
				BigDecimal orosi_baitanka_nuki_vl = new BigDecimal(str.trim());

				// 税計算処理
				CalculateTaxUtility ctu;
				ctu = new CalculateTaxUtility(orosi_baitanka_nuki_vl,
						MoneyUtil.getFractionCostUnitLen(), temp_orosi_zei_kb,
						temp_orosi_tax_rt, MoneyUtil.getFractionCostUnitMode());

				if ("1".equals(temp_orosi_st_zei_kb)) {
					pstmt.setDouble(idx,
							Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxIn().toString()))));
				} else {
					pstmt.setDouble(idx,
							Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()))));
				}

			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// 2016/10/25 Li.Sheng #2258 対応（E)

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("bunrui1_cd"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("syohin_cd"));

			//有効日
			idx++;
			// 有効開始日が未入力の場合、管理日付の翌日でセットする
			// 商品マスタに当日（管理日付の翌日）のレコードがある場合の配慮
			//pstmt.setString(idx, rs.getString("yuko_dt"));
			if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx,startDt);
			}else{
				pstmt.setString(idx, rs.getString("yuko_dt"));
			}
		}

		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
		if (insertFg) {
			// 有効日(現世代)
			idx++;
			str = rs.getString("yuko_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, rs.getString("yuko_dt"));
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}

			// 原価単価(現世代)
			idx++;
			str = rs.getString("gentanka_vl");
			if (isNotBlank(str)) {
				pstmt.setString(idx, rs.getString("gentanka_vl"));
			} else {
				pstmt.setNull(idx, Types.NUMERIC);
			}

			// #1900対応 2016.08.04 M.Akagi (S)
			// 仕入税区分
			idx++;
			str = rs.getString("SIIRE_ZEI_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SIIRE_ZEI_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 仕入税率区分
			idx++;
			str = rs.getString("SIIRE_TAX_RATE_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SIIRE_TAX_RATE_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 卸税区分
			idx++;
			str = rs.getString("OROSI_ZEI_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_ZEI_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			String oZeiKb = null ;
			oZeiKb = StringUtils.trim(str);

			// 卸税率区分
			idx++;
			str = rs.getString("OROSI_TAX_RATE_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_TAX_RATE_KB");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			String oZeiRtKb = null ;
			oZeiRtKb = StringUtils.trim(str);

			// 卸売価単価
			idx++;
			// 2016/11/02 T.Arimoto #2258対応（S)
			//str = rs.getString("OROSI_BAITANKA_VL");
			str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			if(!isNotBlank(str)){
				// TR_SYOHINにて空欄だった場合
				str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
			}
			// 2016/11/02 T.Arimoto #2258対応（E)
			if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)){
				zeiKb = oZeiKb;
			}

			zei_kb = 0;
			if (zeiKb.equals("1")) {
				zei_kb = 2;
			} else {
				zei_kb = Integer.parseInt(zeiKb);
			}

			//税率取得
			tax_rt = null ;
			str_tax_rt = null ;
			if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)) {
				str_tax_rt = rs.getString("OROSHITAX_RT");
				if (isNotBlank(str_tax_rt)) {
					if (!str_tax_rt.trim().equals(deleteString)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				} else {
					str_tax_rt = rs.getString("RS_OROSHITAX_RT");
					if (isNotBlank(str_tax_rt)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				}
			} else {
				str_tax_rt = rs.getString("tax_rt");
				if (isNotBlank(str_tax_rt)) {
					if (!str_tax_rt.trim().equals(deleteString)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				} else {
					str_tax_rt = rs.getString("rs_tax_rt");
					if (isNotBlank(str_tax_rt)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				}
			}

			if (isNotBlank(str)) {
				// #6505 Mod DINH.TP 2022.06.29 （S)
//				BigDecimal gentanka_vl =new BigDecimal(str.trim());
//				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//						MoneyUtil.getFractionCostUnitMode());
//				if (zeiKb.equals("1")) {
//					str = ctu.getTaxIn().toString();
//				} else {
//					str = ctu.getTaxOut().toString();
//				}
//				if (!str.trim().equals(deleteString)) {
//					// 2016/11/02 T.Arimoto #2258対応（S)
//					//pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//					pstmt.setDouble(idx, Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
//					// 2016/11/02 T.Arimoto #2258対応（E)
//				} else {
//					pstmt.setNull(idx, Types.DOUBLE);
//				}
				if (!str.trim().equals(deleteString)) {
					BigDecimal gentanka_vl = new BigDecimal(str.trim());
					CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
							zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
					if (zeiKb.equals("1")) {
						str = ctu.getTaxIn().toString();
					} else {
						str = ctu.getTaxOut().toString();
					}
					pstmt.setDouble(idx, Double
							.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
				} else {
					pstmt.setNull(idx, Types.DOUBLE);
				}
				// #6505 Mod DINH.TP 2022.06.29 （E)
			} else {
				// 2016/11/02 T.Arimoto #2258対応（S)
//				str = rs.getString("RS_OROSI_BAITANKA_VL");
//				if (isNotBlank(str)) {
//					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//				} else {
//					pstmt.setNull(idx, Types.DOUBLE);
//				}
				pstmt.setNull(idx, Types.DOUBLE);
				// 2016/11/02 T.Arimoto #2258対応（E)
			}

			// 卸売価単価（税抜）
			idx++;
			str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// #1900対応 2016.08.04 M.Akagi (E)

			// #1964対応 2016.08.22 M.Akagi (S)
			// 最低発注数
			idx++;
			str = rs.getString("MIN_HACHU_QT");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				// #33102 Mod 2025.04.09 HUY.LTH (S)
				//str = rs.getString("RS_MIN_HACHU_QT");
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
					str = MIN_HACHU_QT_DEFAULT;
				} else {
					str = rs.getString("RS_MIN_HACHU_QT");
				}
				// #33102 Mod 2025.04.09 HUY.LTH (E)
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// 発注不可フラグ
			idx++;
			str = rs.getString("HACHU_FUKA_FLG");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_HACHU_FUKA_FLG");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					// 2017/03/15 T.Arimoto #4358対応（S)
					// pstmt.setNull(idx, Types.VARCHAR);
					pstmt.setString(idx, HachuFukaYobiKbDictionary.KA.getCode());
					// 2017/03/15 T.Arimoto #4358対応（E)
				}
			}

			// 規格内容
			idx++;
			str = rs.getString("PER_TXT");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_PER_TXT");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// 消費期限パターン
			idx++;
			str = rs.getString("SYOHI_KIGEN_HYOJI_PATTER");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_SYOHI_KIGEN_HYOJI_PATTER");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}

			// PLU反映時間
			idx++;
			str = rs.getString("PLU_HANEI_TIME");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			} else {
				str = rs.getString("RS_PLU_HANEI_TIME");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// #1964対応 2016.08.22 M.Akagi (E)

			// 2016/10/25 Li.Sheng #2258 対応（S)
			// 卸売価単価(小売税)
			idx++;
			String orosi_baitanka_nuki_vl_str = rs.getString("OROSI_BAITANKA_NUKI_VL");
			if(!isNotBlank(orosi_baitanka_nuki_vl_str)){
				// TR_SYOHINが空欄だった場合
				orosi_baitanka_nuki_vl_str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
			}
			if (isNotBlank(orosi_baitanka_nuki_vl_str)) {

				String temp_orosi_st_zei_kb = rs.getString("ZEI_KB"); // 税区分
				if (!isNotBlank(temp_orosi_st_zei_kb)) {
					temp_orosi_st_zei_kb = rs.getString("RS_ZEI_KB");
				}

				String temp_tax_rt = rs.getString("TAX_RT"); // 税率取得
				if (!isNotBlank(temp_tax_rt)) {
					temp_tax_rt = rs.getString("RS_TAX_RT");
				}
				BigDecimal temp_orosi_tax_rt = new BigDecimal(temp_tax_rt); // 税率

				int temp_orosi_zei_kb = 0; // 計算用税区分

				// 内税か判定
				if ("1".equals(temp_orosi_st_zei_kb)) {
					temp_orosi_zei_kb = 2;
				} else {
					temp_orosi_zei_kb = Integer.parseInt(temp_orosi_st_zei_kb);
				}

				// 卸売価単価(小売税)
				BigDecimal orosi_baitanka_nuki_vl = new BigDecimal(
						orosi_baitanka_nuki_vl_str.trim());

				// 税計算処理
				CalculateTaxUtility ctu;
				ctu = new CalculateTaxUtility(orosi_baitanka_nuki_vl,
						MoneyUtil.getFractionCostUnitLen(), temp_orosi_zei_kb,
						temp_orosi_tax_rt, MoneyUtil.getFractionCostUnitMode());

				if ("1".equals(temp_orosi_st_zei_kb)) {
					pstmt.setDouble(idx,
							Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxIn().toString()))));
				} else {
					pstmt.setDouble(idx,
							Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()))));
				}

			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// 2016/10/25 Li.Sheng #2258 対応（E)
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// 割引区分
			idx++;
			str = rs.getString("WARIBIKI_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.CHAR);
				}
			} else {
				str = rs.getString("RS_WARIBIKI_KB");
				pstmt.setString(idx, str.trim());
			}
			
			// PB区分
			idx++;
			str = rs.getString("PB_SYOHIN_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.CHAR);
				}
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
			
			// 医薬品分類
			idx++;
			str = rs.getString("IYAKUHIN_KB");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setNull(idx, Types.CHAR);
				}
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
			
			// 賞味期限
			idx++;
			str = rs.getString("SYOMI_KIGEN_NISU");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setInt(idx, 0);
				}
			} else {
				pstmt.setInt(idx, 0);
			}
			
			// 出荷可能限度期日
			idx++;
			str = rs.getString("SHUKKA_KIGEN_NISU");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setInt(idx, 0);
				}
			} else {
				pstmt.setInt(idx, 0);
			}
			
			// 入荷可能限度期日
			idx++;
			str = rs.getString("NYUKA_KIGEN_NISU");
			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setString(idx, str.trim());
				} else {
					pstmt.setInt(idx, 0);
				}
			} else {
				pstmt.setInt(idx, 0);
			}
			// #33102 Mod 2025.04.09 HUY.LTH (E)

		}
		// #33102 Del 2025.03. HUY.LTH (S)
//		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
//		// 割引区分
//		idx++;
//		str = rs.getString("WARIBIKI_KB");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setNull(idx, Types.CHAR);
//			}
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
//		
//		// PB区分
//		idx++;
//		str = rs.getString("PB_SYOHIN_KB");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setNull(idx, Types.CHAR);
//			}
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
//		
//		// 医薬品分類
//		idx++;
//		str = rs.getString("IYAKUHIN_KB");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setNull(idx, Types.CHAR);
//			}
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
//		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
//		// #6355 Add 2021/09/27 SIEU.D (S)
//		// 賞味期限
//		idx++;
//		str = rs.getString("SYOMI_KIGEN_NISU");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setInt(idx, 0);
//			}
//		} else {
//			pstmt.setInt(idx, 0);
//		}
//		
//		// 出荷可能限度期日
//		idx++;
//		str = rs.getString("SHUKKA_KIGEN_NISU");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setInt(idx, 0);
//			}
//		} else {
//			pstmt.setInt(idx, 0);
//		}
//		
//		// 入荷可能限度期日
//		idx++;
//		str = rs.getString("NYUKA_KIGEN_NISU");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str.trim());
//			} else {
//				pstmt.setInt(idx, 0);
//			}
//		} else {
//			pstmt.setInt(idx, 0);
//		}
		// #6355 Add 2021/09/27 SIEU.D (E)
		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)
		// #33102 Del 2025.03. HUY.LTH (E)
	}

	//Add 2015.07.13 DAI.BQ (S)
	/**
	 * 商品マスタASNデータ更新SQL（INSERT時）
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	private void setPrepareASNSyohinSQL(PreparedStatement pstmt, ResultSet rs, boolean insertFg,String syohin_cd) throws IllegalArgumentException, Exception {
		int idx = 0;

		String str = "";

		if (insertFg) {
			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("bunrui1_cd"));

			//商品コード
			idx++;
			pstmt.setString(idx, syohin_cd);

			//有効日
			idx++;
			if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx, startDt);
			} else {
				pstmt.setString(idx, rs.getString("yuko_dt"));
			}
		}

		idx++;
		str = rs.getString("TR_SYOHIN_EIJI_NA");
		// #768対応 2015.10.02 THAO.NTL Mod (S)
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RSA_SYOHIN_EIJI_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)


		idx++;
		str = rs.getString("TR_COUNTRY_CD");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_COUNTRY_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		// #768対応 2015.10.02 THAO.NTL Mod (E)

		//ADD 2015/08/17 THO.VT (S)
		idx++;
		str = rs.getString("TR_MAKER_CD");
		// #768対応 2015.10.02 THAO.NTL Mod (S)
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_MAKER_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		// #768対応 2015.10.02 THAO.NTL Mod (E)
		//ADD 2015/08/17 THO.VT (E)

		//Add 2016.01.11 Huy.NT (S)
		idx++;
		str = rs.getString("TR_HANBAI_HOHO_KB");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if(isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_HANBAI_HOHO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		//Add 2016.01.11 Huy.NT (E)

		idx++;
		str = rs.getString("TR_MIN_ZAIKO_QT");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//				pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RSA_MIN_ZAIKO_QT");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// #33102 Mod 2025.04.09 HUY.LTH (S)
				// str = "0";
				// pstmt.setString(idx, str);
				pstmt.setNull(idx, Types.BIGINT);
				// #33102 Mod 2025.04.09 HUY.LTH (E)
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_SECURITY_TAG_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			str = "1";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_SECURITY_TAG_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// #3701 MSTB011020 2017.01.20 S.Takayama (S)
				//str = "1";
				str = "0";
				// #3701 MSTB011020 2017.01.20 S.Takayama (E)
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_MEMBER_DISCOUNT_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_MEMBER_DISCOUNT_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "0";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_HAMPER_SYOHIN_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_HAMPER_SYOHIN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "0";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		if(insertFg){
			idx++;
			str = rs.getString("by_no");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//更新年月日

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		// #1964対応 2016.08.22 M.Akagi (S)
		// ラベル成分
		idx++;
		str = rs.getString("LABEL_SEIBUN");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_SEIBUN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}

		// ラベル保管方法
		idx++;
		str = rs.getString("LABEL_HOKAN_HOHO");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_HOKAN_HOHO");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}

		// ラベル使い方
		idx++;
		str = rs.getString("LABEL_TUKAIKATA");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_TUKAIKATA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
		// #1964対応 2016.08.22 M.Akagi (E)

		if (!insertFg) {

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("bunrui1_cd"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("syohin_cd"));

			//有効日
			idx++;
			if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx,startDt);
			}else{
				pstmt.setString(idx, rs.getString("yuko_dt"));
			}
		}
	}
	//Add 2015.07.13 DAI.BQ (E)

	/**
	 * 商品マスタデータ更新SQL
	 * @throws Exception
	 */
	private void setPrepareSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bunrui1_cd,String syohin_cd) throws Exception {

		int idx = 0;

		String str = "";

		idx++;
		pstmt.setString(idx, rs.getString(bunrui1_cd));

		idx++;
		pstmt.setString(idx, syohin_cd);

		// No.618 MSTB011020 Add 2016.03.14 TU.TD (S)
		//旧商品コード
		idx++;
		// 2017.01.12 M.Akagi #3531 (S)
		//pstmt.setString(idx, rs.getString("old_syohin_cd"));
		str = rs.getString("old_syohin_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_OLD_SYOHIN_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2017.01.12 M.Akagi #3531 (E)
		// No.618 MSTB011020 Add 2016.03.14 TU.TD (E)

		idx++;
		// ===BEGIN=== Modify by kou 2006/8/3
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		//pstmt.setString(idx, rs.getString("yuko_dt"));
		if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx,startDt);
		}else{
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}
		// ===END=== Modify by kou 2006/8/3

		//システム区分
		idx++;
		str = rs.getString("system_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//業種区分
		idx++;
		str = rs.getString("gyosyu_kb");

		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//商品区分
		idx++;
		str = rs.getString("syohin_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_syohin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//桁数区分
		idx++;
		str = rs.getString("ketasu_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//分類２コード
		idx++;
//		str = rs.getString("bunrui2_cd");
		if(!SyohinCheckGRO.getTaikeiKirikaeJyotai().equals(mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEATO.getCode())) {
			str = rs.getString("bunrui2_cd");
		} else {
			str = rs.getString("rstk_bunrui2_cd");
		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_bunrui2_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//分類５コード
		idx++;
		str = rs.getString("bunrui5_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_bunrui5_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//品目コード
		idx++;
		str = rs.getString("hinmoku_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//商品コード２
		idx++;
		str = rs.getString("syohin_2_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//在庫用商品集計コード
		idx++;
		str = rs.getString("ZAIKO_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_ZAIKO_SYOHIN_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//情報系用商品集計コード
		idx++;
		str = rs.getString("JYOHO_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_JYOHO_SYOHIN_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
//  2013.05.05 [MSTC00001] メーカコード コード管理対応 (S)
		//JANメーカーコード
		idx++;
		str = rs.getString("maker_cd");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else {
//			str = rs.getString("rs_maker_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}
//  2013.05.05 [MSTC00001] メーカコード コード管理対応 (E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)

		//漢字品名
		idx++;
		str = rs.getString("hinmei_kanji_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hinmei_kanji_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//漢字規格
		idx++;
		str = rs.getString("kikaku_kanji_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kanji_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//漢字規格2
		idx++;
		str = rs.getString("kikaku_kanji_2_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kanji_2_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//POSレシート品名(漢字)
		idx++;
		// 2016/10/20 T.Arimoto #2254対応（S) POSレシート品名(漢字)<- 漢字品名
		// str = rs.getString("rec_hinmei_kanji_na");
		// #6167 MB38 Mod 2020.07.22 KHAI.NN (S)
//		str = rs.getString("hinmei_kanji_na");
		str = rs.getString("rec_hinmei_kanji_na");
		// #6167 MB38 Mod 2020.07.22 KHAI.NN (E)
		// 2016/10/20 T.Arimoto #2254対応（E)
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2016/10/20 T.Arimoto #2254対応（S) POSレシート品名(漢字)<- 漢字品名
			//str = rs.getString("rs_rec_hinmei_kanji_na");
			// #6167 MB38 Mod 2020.07.22 KHAI.NN (S)
//			str = rs.getString("rs_hinmei_kanji_na");
			str = rs.getString("rs_rec_hinmei_kanji_na");
			// #6167 MB38 Mod 2020.07.22 KHAI.NN (E)
			// 2016/10/20 T.Arimoto #2254対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ品名
		idx++;
		str = rs.getString("hinmei_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hinmei_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ規格
		idx++;
		str = rs.getString("kikaku_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//カナ規格2
		idx++;
		str = rs.getString("kikaku_kana_2_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_kikaku_kana_2_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//POSレシート品名(カナ)
		idx++;
		str = rs.getString("rec_hinmei_kana_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_rec_hinmei_kana_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//商品サイズ（幅）
		idx++;
		str = rs.getString("SYOHIN_WIDTH_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_WIDTH_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//商品サイズ（高さ）
		idx++;
		str = rs.getString("SYOHIN_HEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_HEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//商品サイズ（奥行き）
		idx++;
		str = rs.getString("SYOHIN_DEPTH_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOHIN_DEPTH_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//Ｅショップ区分
		idx++;
		str = rs.getString("e_shop_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//PB区分
		idx++;
		str = rs.getString("pb_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_pb_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//サブクラスコード
		idx++;
		str = rs.getString("subclass_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_subclass_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//配布コード
		idx++;
		str = rs.getString("haifu_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_haifu_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//税区分
		idx++;
		str = rs.getString("ZEI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_ZEI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//税区分取得
		// #1900対応 2016.08.04 M.Akagi (S)
		str = rs.getString("SIIRE_ZEI_KB");
		// 2016/11/25 T.Arimoto #2803対応（S)
		//if (isNotBlank(str)) {
		if (!isNotBlank(str)) {
			// 2016/11/25 T.Arimoto #2803対応（E)
			str = rs.getString("RS_SIIRE_ZEI_KB");
		}
		// #1900対応 2016.08.04 M.Akagi (E)
		String zeiKb = null ;
		zeiKb =str.trim();

		//税率区分
		idx++;
		str = rs.getString("TAX_RATE_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_TAX_RATE_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		//売価配信区分
		idx++;
		str = rs.getString("baika_haishin_fg");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//  未記入ときの初期値セット （修正：「０」）
			//str = mst011701_BaikaHaishinFlagDictionary.SINAI.getCode();
			//pstmt.setString(idx, str.trim());
			str = rs.getString("RS_BAIKA_HAISHIN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, mst011701_BaikaHaishinFlagDictionary.SINAI.getCode());
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		//原価単価
		idx++;
		str = rs.getString("gentanka_vl");
		// 2016/11/02 T.Arimoto #2258対応（S)
		if (!isNotBlank(str)) {
			// TR_SYOHINにて空欄だった場合
			str = rs.getString("rs_gentanka_nuki_vl");
		}
		// 2016/11/02 T.Arimoto #2258対応（E)

		//No.158 MSTB011 Add 2015.12.04 TU.TD (S)
		int zei_kb = 0;
		if (zeiKb.equals("1")) {
			zei_kb = 2;
		} else {
			zei_kb = Integer.parseInt(zeiKb);
		}

		//税率取得
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		// #1900対応 2016.08.04 M.Akagi (S)
		//str_tax_rt = rs.getString("tax_rt");
		str_tax_rt = rs.getString("SIIRETAX_RT");
		// #1900対応 2016.08.04 M.Akagi (E)
		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			// #1900対応 2016.08.04 M.Akagi (S)
			//str_tax_rt = rs.getString("rs_tax_rt");
			str_tax_rt = rs.getString("RS_SIIRETAX_RT");
			// #1900対応 2016.08.04 M.Akagi (E)
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}
		//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
		if (isNotBlank(str)) {
		//#6505 Mod DINH.TP 2022.06.29 (S)
			//No.158 MSTB011 Add 2015.12.04 TU.TD (S)
//			BigDecimal gentanka_vl =new BigDecimal(str.trim());
//			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionCostUnitMode());
//			if (zeiKb.equals("1")) {
//				str = ctu.getTaxIn().toString();
//			} else {
//				str = ctu.getTaxOut().toString();
//			}
//			//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
						zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
				if (zeiKb.equals("1")) {
					str = ctu.getTaxIn().toString();
				} else {
					str = ctu.getTaxOut().toString();
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		// 2016/11/02 T.Arimoto #2258対応（S)
		//#6505 Mod DINH.TP 2022.06.29 (E)
		} else {
			//str = rs.getString("rs_gentanka_vl");
			//if (isNotBlank(str)) {
			//	pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			//} else {
			//	pstmt.setNull(idx, Types.DOUBLE);
			//}
			pstmt.setNull(idx, Types.DOUBLE);
		// 2016/11/02 T.Arimoto #2258対応（E)
		}

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//原価単価(税抜)
		idx++;
		//No.158 MSTB011 Del 2015.12.04 TU.TD (S)
		/*BigDecimal num =new BigDecimal("100");
		int zei_kb = Integer.parseInt(zeiKb);

		//税率取得
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		str_tax_rt = rs.getString("tax_rt");
		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			str_tax_rt = rs.getString("rs_tax_rt");
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}*/
		/*if (isNotBlank(str)) {
			BigDecimal gentanka_vl =new BigDecimal(str.trim());
			//円未満四捨五入の対応
			gentanka_vl = gentanka_vl.multiply(num);

			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
					&& ! gentanka_vl.equals("")){
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
						MoneyUtil.getFractionCostUnitMode());
				str = (ctu.getTaxOut().divide(num)).toString();
			}else{
				str =  "";
			}

			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				} else {
					pstmt.setNull(idx, Types.DOUBLE);
				}
			}else{
				pstmt.setNull(idx, Types.DOUBLE);
			}*/
		//No.158 MSTB011 Del 2015.12.04 TU.TD (E)
		//No.158 MSTB011 Add 2015.12.04 TU.TD (S)
		str = rs.getString("gentanka_vl");
		//No.158 MSTB011 Add 2015.12.04 TU.TD (E)
		if (isNotBlank(str)) {
			//No.158 MSTB011 Mod 2015.12.04 TU.TD (S)
			/*BigDecimal gentanka_vl =new BigDecimal(str.trim());
			//円未満四捨五入の対応
			gentanka_vl = gentanka_vl.multiply(num);

			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
					&& ! gentanka_vl.equals("")){
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
						MoneyUtil.getFractionCostUnitMode());
				str = (ctu.getTaxOut().divide(num)).toString();
			}else{
				str =  "";
			}

			if (isNotBlank(str)) {
				if (!str.trim().equals(deleteString)) {
					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				} else {
					pstmt.setNull(idx, Types.DOUBLE);
				}
			}else{
				pstmt.setNull(idx, Types.DOUBLE);
			}*/
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			//No.158 MSTB011 Mod 2015.12.04 TU.TD (E)
		}else{
			// 2016/11/02 T.Arimoto #2258対応（S)
			//pstmt.setNull(idx, Types.DOUBLE);
			str = rs.getString("rs_gentanka_nuki_vl");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// 2016/11/02 T.Arimoto #2258対応（E)
		}
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//売価単価
		idx++;
		str = rs.getString("baitanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0013] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0013] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0014] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0014] Update to parse double for tanka (E)
			}
		} else {
			str = rs.getString("rs_baitanka_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0015] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0015] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0016] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0016] Update to parse double for tanka (E)
			}
		}

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//売価単価(税抜)
		idx++;

		// #1900対応 2016.08.04 M.Akagi (S)
		// 2016/11/02 T.Arimoto #2258対応（S)
//		str = rs.getString("ZEI_KB");
//		if (isNotBlank(str)) {
//			str = rs.getString("RS_ZEI_KB");
//		}
//		zeiKb = str.trim();
		String zei_str = rs.getString("ZEI_KB");
		if (!isNotBlank(zei_str)) {
			// TR_SYOHINにて空欄だった場合
			zei_str = rs.getString("RS_ZEI_KB");
		}
		zeiKb = zei_str.trim();
		// 2016/11/02 T.Arimoto #2258対応（E)

		zei_kb = 0;
		// 2016/11/02 T.Arimoto #2258対応（S)
//		if (zeiKb.equals("1")) {
//			zei_kb = 2;
//		} else {
//			zei_kb = Integer.parseInt(zeiKb);
//		}
		if (isNotBlank(zeiKb)) {
			zei_kb = Integer.parseInt(zeiKb);
		}
		// 2016/11/02 T.Arimoto #2258対応（E)
		//税率取得
		tax_rt = null ;
		str_tax_rt = null ;
		str_tax_rt = rs.getString("tax_rt");

		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(deleteString)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		} else {
			str_tax_rt = rs.getString("rs_tax_rt");
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}
		// #1900対応 2016.08.04 M.Akagi (E)

		if (isNotBlank(str)) {
			// #6505 Mod 2022.06.29 DINH.TP (S)
//			BigDecimal baitanka_vl = new BigDecimal( str.trim());
//
//			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
//					&& ! baitanka_vl.equals("")){
//				CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl,
//						MoneyUtil.getFractionSellUnitLen(), zei_kb, tax_rt,
//						MoneyUtil.getFractionSellUnitMode());
//				// 2016/11/02 T.Arimoto #2258対応（S)
//				//str = ctu.getTaxOut().toString();
//				str = MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()));
//				// 2016/11/02 T.Arimoto #2258対応（E)
//			}else{
//				// 2017/03/15 T.Arimoto #4358対応（S) 内税以外の場合、売単価（税込）をそのまま売単価（税抜）に格納します
//				//str =  "";
//				// 2017/03/15 T.Arimoto #4358対応（E)
//			}
//
//			if (isNotBlank(str)) {
//				if (!str.trim().equals(deleteString)) {
//					//2014.07.29 [CUS0017] Update to parse double for tanka (S)
//					//pstmt.setLong(idx, Long.parseLong(str.trim()));
//					pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//					//2014.07.29 [CUS0017] Update to parse double for tanka (E)
//				} else {
//					//2014.07.29 [CUS0018] Update to parse double for tanka (S)
//					//pstmt.setNull(idx, Types.BIGINT);
//					pstmt.setNull(idx, Types.DOUBLE);
//					//2014.07.29 [CUS0018] Update to parse double for tanka (E)
//				}
//			} else {
//				//2014.07.29 [CUS0018] Update to parse double for tanka (S)
//				//pstmt.setNull(idx, Types.BIGINT);
//				pstmt.setNull(idx, Types.DOUBLE);
//				//2014.07.29 [CUS0018] Update to parse double for tanka (E)
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal baitanka_vl = new BigDecimal(str.trim());
				if (zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
						&& !baitanka_vl.equals("")) {
					CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl, MoneyUtil.getFractionSellUnitLen(),
							zei_kb, tax_rt, MoneyUtil.getFractionSellUnitMode());
					str = MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()));
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			// #6505 Mod 2022.06.29 DINH.TP (E)
		}else{
			//2014.07.29 [CUS0018] Update to parse double for tanka (S)
			//pstmt.setNull(idx, Types.BIGINT);
			pstmt.setNull(idx, Types.DOUBLE);
			//2014.07.29 [CUS0018] Update to parse double for tanka (E)
		}
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

		//当初売価（新規登録時は売価単価(税込)を設定する）
		idx++;
		str = rs.getString("TOSYO_BAIKA_VL");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//前回原価単価
		idx++;
		str = rs.getString("pre_gentanka_vl");
		if (isNotBlank(str)) {
			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
		} else {
			pstmt.setNull(idx, Types.DOUBLE);
		}

		//前回売価単価
		idx++;
		str = rs.getString("pre_baitanka_vl");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//特別原価
		idx++;
		str = rs.getString("tokubetu_genka_vl");
		if (isNotBlank(str)) {
			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
		} else {
			pstmt.setNull(idx, Types.DOUBLE);
		}

		//メーカー希望小売り価格
		idx++;
		str = rs.getString("maker_kibo_kakaku_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0018] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0018] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0019] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0019] Update to parse double for tanka (E)
			}
		} else {
			str = rs.getString("rs_maker_kibo_kakaku_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0020] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0020] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0021] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0021] Update to parse double for tanka (E)
			}
		}

		//仕入先コード
		idx++;
		str = rs.getString("siiresaki_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_siiresaki_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//代表配送先コード
		idx++;
		str = rs.getString("daihyo_haiso_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//店別仕入先管理コード
		idx++;
		str = rs.getString("ten_siiresaki_kanri_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_ten_siiresaki_kanri_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//仕入先品番
		idx++;
		str = rs.getString("siire_hinban_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_siire_hinban_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}


		//発注商品コード区分
		idx++;
		str = rs.getString("hacyu_syohin_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}


		//発注商品コード
		idx++;
		str = rs.getString("hacyu_syohin_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//EOS区分
		idx++;
		str = rs.getString("eos_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_eos_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注単位呼称
		idx++;
		str = rs.getString("hachu_tani_na");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hachu_tani_na");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売単位呼称
		idx++;
		str = rs.getString("HANBAI_TANI");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_HANBAI_TANI");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注単位(入数)
		idx++;
		str = rs.getString("hachutani_irisu_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rs_hachutani_irisu_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		// 最大発注単位
		idx++;
		str = rs.getString("max_hachutani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rs_max_hachutani_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//ケース発注区分
		idx++;
		str = rs.getString("CASE_HACHU_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CASE_HACHU_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//バラ入数
		idx++;
		str = rs.getString("BARA_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
//				pstmt.setNull(idx, Types.INTEGER);
				pstmt.setInt(idx, 1);
			}
		} else {
			str = rs.getString("rs_BARA_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
//				pstmt.setNull(idx, Types.INTEGER);
				pstmt.setInt(idx, 1);
			}
		}

		//店舗発注開始日
		idx++;
		str = rs.getString("ten_hachu_st_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_ten_hachu_st_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//店舗発注終了日
		idx++;
		str = rs.getString("ten_hachu_ed_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_ten_hachu_ed_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//販売開始日
		idx++;
		str = rs.getString("hanbai_st_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hanbai_st_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売終了日
		idx++;
		str = rs.getString("hanbai_ed_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_hanbai_ed_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売期間区分
		idx++;
		str = rs.getString("hanbai_kikan_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//定貫区分
		idx++;
		str = rs.getString("TEIKAN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TEIKAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//相場商品区分
		idx++;
		str = rs.getString("SOBA_SYOHIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SOBA_SYOHIN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//納品期限区分
		idx++;
		str = rs.getString("nohin_kigen_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//納品期限
		idx++;
		str = rs.getString("nohin_kigen_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//①便区分
		idx++;
		str = rs.getString("BIN_1_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_BIN_1_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//①発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①締め時間
		idx++;
		str = rs.getString("SIME_TIME_1_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SIME_TIME_1_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//①センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②便区分
		idx++;
		str = rs.getString("BIN_2_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_BIN_2_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//②発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②締め時間
		idx++;
		str = rs.getString("SIME_TIME_2_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SIME_TIME_2_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//②センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③便区分
		idx++;
		str = rs.getString("bin_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③締め時間
		idx++;
		str = rs.getString("sime_time_3_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//③センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//優先便区分
		idx++;
		str = rs.getString("yusen_bin_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}


		//F便区分
		idx++;
		str = rs.getString("F_BIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_F_BIN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//物流区分
		idx++;
		str = rs.getString("buturyu_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_buturyu_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GOT無条件表示対象
		idx++;
		str = rs.getString("got_mujyoken_fg");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//GOT表示開始月
		idx++;
		str = rs.getString("GOT_START_MM");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GOT_START_MM");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GOT表示終了月
		idx++;
		str = rs.getString("GOT_END_MM");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GOT_END_MM");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//発注停止区分
		idx++;
		str = rs.getString("hachu_teisi_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//センター在庫区分
		idx++;
		str = rs.getString("CENTER_ZAIKO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_CENTER_ZAIKO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//納品商品コード
		idx++;
//		str = rs.getString("SYOHIN_CD");
		str = rs.getString("NOHIN_SYOHIN_CD");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//入荷時商品コード
		idx++;
		str = rs.getString("NYUKA_SYOHIN_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NYUKA_SYOHIN_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//入荷時商品コード２
		idx++;
		str = rs.getString("NYUKA_SYOHIN_2_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_NYUKA_SYOHIN_2_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ITFコード
		idx++;
		str = rs.getString("ITF_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_ITF_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//GTINコード
		idx++;
//		str = rs.getString("syohin_cd");
//		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setString(idx, StringUtility.charFormat(str,str.length()+1,"0",true));
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
//		} else {
			str = rs.getString("gtin_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//ベンダーメーカーコード
		idx++;
		str = rs.getString("VENDER_MAKER_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_VENDER_MAKER_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//在庫センターコード
		idx++;
		str = rs.getString("zaiko_center_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//在庫補充発注先
		idx++;
		str = rs.getString("zaiko_hachu_saki");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//センター重量
		idx++;
		str = rs.getString("center_weight_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//外箱サイズ幅
		idx++;
		str = rs.getString("pack_width_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//外箱サイズ高さ
		idx++;
		str = rs.getString("pack_heigth_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//外箱サイズ奥行き
		idx++;
		str = rs.getString("pack_depth_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//外箱重量
		idx++;
//2011.03.04 T.Urano Mod 平均重量追加対応 Start
//		str = rs.getString("pack_weight_qt");
//		if (isNotBlank(str)) {
//			pstmt.setLong(idx, Long.parseLong(str.trim()));
//		} else {
//			pstmt.setNull(idx, Types.BIGINT);
//		}

		str = rs.getString("pack_weight_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("rs_pack_weight_qt");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}
//2011.03.04 T.Urano Mod 平均重量追加対応 End

		//センター発注単位区分
		idx++;
		str = rs.getString("center_hachutani_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//センター発注単位数
		idx++;
		str = rs.getString("center_hachutani_qt");
		if (isNotBlank(str)) {
			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
		} else {
			pstmt.setNull(idx, Types.DOUBLE);
		}

		//センターバラ入数
		idx++;
		str = rs.getString("CENTER_BARA_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_BARA_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター入り数
		idx++;
		str = rs.getString("CENTER_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CENTER_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//ケース入り数
		idx++;
		str = rs.getString("CASE_IRISU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CASE_IRISU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//梱り合せ数
		idx++;
		str = rs.getString("CENTER_IRISU_2_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_IRISU_2_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最低在庫数(発注点)
		idx++;
		str = rs.getString("MIN_ZAIKOSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_MIN_ZAIKOSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最大在庫数
		idx++;
		str = rs.getString("MAX_ZAIKOSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_MAX_ZAIKOSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//基準在庫日数
		idx++;
		str = rs.getString("kijun_zaikosu_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//最小在庫日数
		idx++;
		str = rs.getString("MIN_ZAIKONISSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_MIN_ZAIKONISSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//最大在庫日数
		idx++;
		str = rs.getString("MAX_ZAIKONISSU_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_MAX_ZAIKONISSU_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター許容区分
		idx++;
		str = rs.getString("CENTER_KYOYO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CENTER_KYOYO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//センター許容日
		idx++;
		str = rs.getString("center_kyoyo_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("RS_CENTER_KYOYO_DT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//センター賞味期間区分
		idx++;
		str = rs.getString("CENTER_SYOMI_KIKAN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CENTER_SYOMI_KIKAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//センター賞味期間
		idx++;
		str = rs.getString("CENTER_SYOMI_KIKAN_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_CENTER_SYOMI_KIKAN_DT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//店グルーピングNO(物流）
		idx++;
		str = rs.getString("ten_groupno_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//TC情報
		idx++;
		str = rs.getString("tc_jyouho_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//納品温度帯
		idx++;
		str = rs.getString("nohin_ondo_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//横もち区分
		idx++;
		str = rs.getString("yokomoti_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//品揃区分
		idx++;
		str = rs.getString("shinazoroe_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//本部在庫区分
		idx++;
		str = rs.getString("honbu_zai_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_honbu_zai_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}


		//店在庫区分
		idx++;
		str = rs.getString("ten_zaiko_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//販売政策区分
		idx++;
		str = rs.getString("hanbai_seisaku_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//返品契約書NO
		idx++;
		str = rs.getString("henpin_nb");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//返品原価
		idx++;
		str = rs.getString("henpin_genka_vl");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//CGC返品区分
		idx++;
		str = rs.getString("CGC_HENPIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_CGC_HENPIN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//販売期限区分
		idx++;
		str = rs.getString("HANBAI_LIMIT_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HANBAI_LIMIT_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//販売期限
		idx++;
		str = rs.getString("hanbai_limit_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("rs_hanbai_limit_qt");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//PLU送信日
		idx++;
		str = rs.getString("plu_send_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				if(rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")){
//					str = DateChanger.addDate(MasterDT, 1);
//				}else{
//					str = rs.getString("yuko_dt");
//				}
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
			// 2016/11/22 T.Arimoto #2803対応（S)
//			str = rs.getString("yuko_dt");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
			str = rs.getString("RS_PLU_SEND_DT");
			if( isNotBlank(str) ){
				if( str.compareTo( rs.getString("YUKO_DT") ) >= 0 ){
					// 前の世代のPLU送信日が有効日より未来だった場合 PLU送信日を引き継ぐ
					pstmt.setString(idx, str);
				} else {
					str = rs.getString("yuko_dt");
					if (isNotBlank(str)) {
						pstmt.setString(idx, str);
					} else {
						pstmt.setNull(idx, Types.VARCHAR);
					}
				}
			} else {
				str = rs.getString("yuko_dt");
				if (isNotBlank(str)) {
					pstmt.setString(idx, str);
				} else {
					pstmt.setNull(idx, Types.VARCHAR);
				}
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
		// 2016/11/22 T.Arimoto #2803対応（S)
		String pluSendDt = str;
		// 2016/11/22 T.Arimoto #2803対応（E)

		//キーPLU対象
		idx++;
		str = rs.getString("keyplu_fg");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//PLU区分
		idx++;
		str = rs.getString("plu_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//酒税報告分類
		idx++;
		str = rs.getString("syuzei_hokoku_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_syuzei_hokoku_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//酒内容量
		idx++;
		str = rs.getString("SAKE_NAIYORYO_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_SAKE_NAIYORYO_QT");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//タグ表示売価
		idx++;
		str = rs.getString("tag_hyoji_baika_vl");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//消札売価
		idx++;
		str = rs.getString("keshi_baika_vl");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
//			pstmt.setNull(idx, Types.LONGVARBINARY);
			pstmt.setNull(idx, Types.BIGINT);
		}

		//よりどり種類
		idx++;
		str = rs.getString("yoridori_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//よりどり価格
		idx++;
		str = rs.getString("yoridori_vl");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.INTEGER);
		}

		//よりどり数量
		idx++;
		str = rs.getString("yoridori_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//ブランドコード
		idx++;
		str = rs.getString("bland_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_bland_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}


		//シーズンコード
		idx++;
		str = rs.getString("season_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//服種コード
		idx++;
		str = rs.getString("hukusyu_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//スタイルコード
		idx++;
		str = rs.getString("style_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//シーンコード
		idx++;
		str = rs.getString("scene_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//性別コード
		idx++;
		str = rs.getString("sex_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//年代コード
		idx++;
		str = rs.getString("age_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//世代コード
		idx++;
		str = rs.getString("generation_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//素材コード
		idx++;
		str = rs.getString("sozai_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//パターンコード
		idx++;
		str = rs.getString("pattern_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//織り編みコード
		idx++;
		str = rs.getString("oriami_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//付加機能コード
		idx++;
//		↓↓2006.10.18 H.Yamamoto カスタマイズ修正↓↓
		str = rs.getString("huka_kino_cd");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//		str = rs.getString("syurui_dosuu_qt");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, StringUtility.charFormat(str.trim(),3,"0",true));
//		} else {
//			str = rs.getString("rs_huka_kino_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//袖丈コード
		idx++;
		str = rs.getString("sode_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//サイズコード
		idx++;
		str = rs.getString("size_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//カラーコード
		idx++;
		str = rs.getString("color_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//契約数
		idx++;
		str = rs.getString("keiyaku_su_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//契約パターン
		idx++;
		str = rs.getString("keiyaku_pattern_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//契約開始期間
		idx++;
		str = rs.getString("keiyaku_st_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//契約終了期間
		idx++;
		str = rs.getString("keiyaku_ed_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//組数区分
		idx++;
		str = rs.getString("kumisu_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//値札区分
		idx++;
		str = rs.getString("nefuda_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//値札受渡日
		idx++;
		str = rs.getString("nefuda_ukewatasi_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//値札受渡方法
		idx++;
		str = rs.getString("nefuda_ukewatasi_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//PC発行区分
		idx++;
		str = rs.getString("pc_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_pc_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//台紙NO
		idx++;
		str = rs.getString("daisi_no_nb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_daisi_no_nb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ユニットプライス-単位区分
		idx++;
		str = rs.getString("unit_price_tani_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_unit_price_tani_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ユニットプライス-内容量
		idx++;
		str = rs.getString("unit_price_naiyoryo_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_unit_price_naiyoryo_qt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//ユニットプライス-基準単位量
		idx++;
		str = rs.getString("unit_price_kijun_tani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		} else {
			str = rs.getString("rs_unit_price_kijun_tani_qt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
		}

		//消費期限区分
		idx++;
		str = rs.getString("SYOHI_KIGEN_KB");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_SYOHI_KIGEN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//消費期限
		idx++;
		str = rs.getString("syohi_kigen_dt");
//		if (isNotBlank(str)) {
//			pstmt.setInt(idx, Integer.parseInt(str.trim()));
//		} else {
//			str = rs.getString("rs_syohi_kigen_dt");
			if (isNotBlank(str)) {
				pstmt.setInt(idx, Integer.parseInt(str.trim()));
			} else {
				pstmt.setNull(idx, Types.INTEGER);
			}
//		}

		//商品台帳（店舗）
		idx++;
		str = rs.getString("daicho_tenpo_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_daicho_tenpo_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
//		}

		//商品台帳（本部）
		idx++;
		str = rs.getString("daicho_honbu_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_daicho_honbu_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//商品台帳（仕入先）
		idx++;
		str = rs.getString("daicho_siiresaki_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_daicho_siiresaki_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//棚№
		idx++;
		str = rs.getString("tana_no_nb");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//段ＮＯ１
		idx++;
		str = rs.getString("dan_no_nb");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//リベート対象フラグ
		idx++;
		str = rs.getString("rebate_fg");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//マークグループ
		idx++;
		str = rs.getString("mark_group_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//マークコード
		idx++;
		str = rs.getString("mark_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//輸入品区分
		idx++;
		str = rs.getString("yunyuhin_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//原産国/配布コード
		idx++;
		str = rs.getString("santi_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性１
		idx++;
		str = rs.getString("d_zokusei_1_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性１
		idx++;
		str = rs.getString("s_zokusei_1_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性２
		idx++;
		str = rs.getString("d_zokusei_2_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性２
		idx++;
		str = rs.getString("s_zokusei_2_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性３
		idx++;
		str = rs.getString("d_zokusei_3_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性３
		idx++;
		str = rs.getString("s_zokusei_3_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性４
		idx++;
		str = rs.getString("d_zokusei_4_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性４
		idx++;
		str = rs.getString("s_zokusei_4_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性５
		idx++;
		str = rs.getString("d_zokusei_5_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性５
		idx++;
		str = rs.getString("s_zokusei_5_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性６
		idx++;
		str = rs.getString("d_zokusei_6_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性６
		idx++;
		str = rs.getString("s_zokusei_6_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性７
		idx++;
		str = rs.getString("d_zokusei_7_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性７
		idx++;
		str = rs.getString("s_zokusei_7_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性８
		idx++;
		str = rs.getString("d_zokusei_8_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性８
		idx++;
		str = rs.getString("s_zokusei_8_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性９
		idx++;
		str = rs.getString("d_zokusei_9_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性９
		idx++;
		str = rs.getString("s_zokusei_9_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//大属性１０
		idx++;
		str = rs.getString("d_zokusei_10_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//小属性１０
		idx++;
		str = rs.getString("s_zokusei_10_na");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}
		//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
		//任意区分１
		idx++;
		str = rs.getString("free_1_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_1_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意区分２
		idx++;
		str = rs.getString("free_2_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_2_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		//任意区分３
		idx++;
		str = rs.getString("free_3_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_3_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意区分４
		idx++;
		str = rs.getString("free_4_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// str = rs.getString("rs_free_4_kb");
			// if (isNotBlank(str)) {
			// 	pstmt.setString(idx, str);
			// } else {
			// 	pstmt.setNull(idx, Types.VARCHAR);
			// }
			pstmt.setNull(idx, Types.VARCHAR);
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		//任意区分５
		idx++;
		str = rs.getString("free_5_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
				str = rs.getString("rs_free_5_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//コメント１
		idx++;
		str = rs.getString("comment_1_tx");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_comment_1_tx");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//コメント２
		idx++;
		str = rs.getString("comment_2_tx");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_comment_2_tx");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//任意コード
		idx++;
		str = rs.getString("free_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rs_free_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

//2013.11.22 [CUS00048]  マスタ未使用項目 (E)
		//FUJI商品区分
		idx++;
		str = rs.getString("fuji_syohin_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_fuji_syohin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}


		//コメント
		idx++;
		str = rs.getString("comment_tx");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//自動削除対象区分
		idx++;
		str = rs.getString("auto_del_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//マスタ使用不可区分
		idx++;
		str = rs.getString("mst_siyofuka_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}


		//廃番実施フラグ
		idx++;
		str = rs.getString("HAIBAN_FG");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			str = rs.getString("rs_HAIBAN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
//		}

		//新規登録日
		idx++;
		str = rs.getString("sinki_toroku_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//変更日
// 2017/02/27 Mod Li.Sheng #4205 対応（S)
//		// No.158 MSTB011 Add 2015.11.26 TU.TD (S)
//		//年齢制限区分
//		idx++;
//		str = rs.getString("nenrei_seigen_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//年齢
//		idx++;
//		str = rs.getString("nenrei");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//瓶区分
//		idx++;
//		str = rs.getString("kan_kb");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//
//		//保証金
//		idx++;
//		str = rs.getString("hoshoukin");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//		// No.158 MSTB011 Add 2015.11.26 TU.TD (E)
		
		//年齢制限区分
		idx++;
		str = rs.getString("nenrei_seigen_kb");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NENREI_SEIGEN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				// pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}

		//年齢
		idx++;
		str = rs.getString("nenrei");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NENREI");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		//瓶区分
		idx++;
		str = rs.getString("kan_kb");
		
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				// pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}

		//保証金
		idx++;
		str = rs.getString("hoshoukin");

		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HOSHOUKIN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S) 初期値：0を設定します
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}
// 2017/02/27 Mod Li.Sheng #4205 対応（E)	

		// 2016/10/25 T.Arimoto #2256対応（S)
		// 緊急配信フラグ
		idx++;
		// 2016/11/22 T.Arimoto #2803対応（S)
		//str = rs.getString("yuko_dt");
		//if (str.equals(MasterDT)) {
			// オンライン日付の新規登録・修正登録は緊急配信フラグON
		//	pstmt.setString(idx, EMG_ON);
		//} else {
		//	pstmt.setString(idx, EMG_OFF);
		//}
		str = rs.getString("PLU_HANEI_TIME");
		if ( isNotBlank(str) ) {
			if (str.trim().equals(deleteString)) {
				str = "";
			}
		}else {
			// TR_SYOHINのPLU_HANEI_TIMEが空
			if ( isNotBlank(rs.getString("RS_PLU_HANEI_TIME")) && isNotBlank(pluSendDt) && pluSendDt.equals(rs.getString("RS_PLU_SEND_DT"))  ) {
				// 前の世代のPLU反映時間がNULL以外で、PLU送信日が前の世代を引き継いでいた場合
				str = rs.getString("RS_PLU_HANEI_TIME"); // 前の世代のPLU反映時間を取得
			}
		}
		if( isNotBlank( pluSendDt ) ){
			if( pluSendDt.equals(MasterDT) ){
				// PLU送信日がオンライン日付だった場合
				pstmt.setString(idx, EMG_ON);
			} else if( isNotBlank(str) ) {
				// PLU送信日がオンライン日付以外でPLU送信時間が設定されている場合
				pstmt.setString(idx, EMG_ON);
			} else {
				// 上記以外
				pstmt.setString(idx, EMG_OFF);
			}
		} else {
			// PLU送信日が空の場合
			pstmt.setString(idx, EMG_OFF);
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		// 2016/10/25 T.Arimoto #2256対応（E)

		//初回登録日
		idx++;
		str = rs.getString("syokai_toroku_ts");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//初回登録社員ID
		idx++;
		str = rs.getString("SYOKAI_USER_ID");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//作成年月日
//		idx++;
//		str = rs.getString("insert_ts");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}

		//作成者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//更新年月日

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//バッチ更新年月日

		//バッチ更新者ID

		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (S)
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("  RS.GENTANKA_NUKI_VL ");
		sql.append("  ,RS.CUR_GENERATION_YUKO_DT ");
		sql.append("  ,RS.CUR_GENERATION_GENTANKA_VL ");
		sql.append("  ,RS.ONE_GENERATION_YUKO_DT ");
		sql.append("  ,RS.ONE_GENERATION_GENTANKA_VL ");
		sql.append("  ,RS.TWO_GENERATION_YUKO_DT ");
		sql.append("  ,RS.TWO_GENERATION_GENTANKA_VL ");
		sql.append("from");
		sql.append("  r_syohin RS ");
		sql.append("where ");
		sql.append("  TRIM(bunrui1_cd) = TRIM(?) and");
		sql.append("  TRIM(syohin_cd) = TRIM(?) and");
		sql.append("  yuko_dt =");
		sql.append("            (select");
		sql.append("               max(yuko_dt)");
		sql.append("             from");
		sql.append("               r_syohin sub");
		sql.append("             where");
		sql.append("               TRIM(sub.bunrui1_cd) = TRIM(rs.bunrui1_cd) AND");
		sql.append("               TRIM(sub.syohin_cd) = TRIM(rs.syohin_cd) AND");
		sql.append("               sub.yuko_dt <= ?) ");

		PreparedStatement pstmt1 = dataBase.getPrepareStatement(sql.toString());

		int intI = 1;

		// 分類コード
		pstmt1.setString(intI++, rs.getString(bunrui1_cd));

		// 商品コード
		pstmt1.setString(intI++, syohin_cd);

		// 有効日
		pstmt1.setString(intI++, rs.getString("yuko_dt"));

		ResultSet beforeRs = pstmt1.executeQuery();

		if(beforeRs.next()) {

			String gentenkaVl = mst000401_LogicBean.chkNullString(rs.getString("gentanka_vl")).trim();
			String gentenkaNukiVl = mst000401_LogicBean.chkNullString(beforeRs.getString("GENTANKA_NUKI_VL")).trim();

// 2017/02/27 Add Li.Sheng #4205 対応（S)	
			if ("".equals(gentenkaVl)) {
				gentenkaVl = gentenkaNukiVl;
			}
// 2017/02/27 Add Li.Sheng #4205 対応（E)

			String yukoDt = mst000401_LogicBean.chkNullString(rs.getString("yuko_dt")).trim();

			String curGenerationYukoDt = mst000401_LogicBean.chkNullString(beforeRs.getString("CUR_GENERATION_YUKO_DT")).trim();
			String curGenerationGenVl  = mst000401_LogicBean.chkNullString(beforeRs.getString("CUR_GENERATION_GENTANKA_VL")).trim();
			String oneGenerationYukoDt = mst000401_LogicBean.chkNullString(beforeRs.getString("ONE_GENERATION_YUKO_DT")).trim();
			String oneGenerationGenVl =  mst000401_LogicBean.chkNullString(beforeRs.getString("ONE_GENERATION_GENTANKA_VL")).trim();
			String twoGenerationYukoDt = mst000401_LogicBean.chkNullString(beforeRs.getString("TWO_GENERATION_YUKO_DT")).trim();
			String twoGenerationGenVl =  mst000401_LogicBean.chkNullString(beforeRs.getString("TWO_GENERATION_GENTANKA_VL")).trim();

			// 2016/10/24 T.Arimoto #2256対応（S)
			// 有効日(現世代)
			//if(!gentenkaVl.equals(gentenkaNukiVl)) {
			if(jp.co.vinculumjapan.mdware.common.util.StringUtility.decimalCompare(gentenkaVl, gentenkaNukiVl) != 0){
			// 2016/10/24 T.Arimoto #2256対応（E)

				idx++;
				pstmt.setString(idx, yukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, gentenkaVl);

				// 有効日(現世代)
				idx++;
				pstmt.setString(idx, curGenerationYukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, curGenerationGenVl);

				// 有効日(現世代)
				idx++;
				pstmt.setString(idx, oneGenerationYukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, oneGenerationGenVl);
			} else {
				idx++;
				pstmt.setString(idx, curGenerationYukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, curGenerationGenVl);

				// 有効日(現世代)
				idx++;
				pstmt.setString(idx, oneGenerationYukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, oneGenerationGenVl);

				// 有効日(現世代)
				idx++;
				pstmt.setString(idx, twoGenerationYukoDt);

				// 原価単価(現世代)
				idx++;
				pstmt.setString(idx, twoGenerationGenVl);
			}
		}
		
		// 2017/02/03 T.Arimoto #1174対応（S)
		dataBase.closeResultSet(beforeRs);
		if(null != pstmt1){
			pstmt1.close();
		}
		// 2017/02/03 T.Arimoto #1174対応（E)

		// No.623 MSTB011020 Add 2016.03.16 Vuong.LT (E)

		// #1900対応 2016.08.04 M.Akagi (S)
		// 仕入税区分
		idx++;
		str = rs.getString("SIIRE_ZEI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SIIRE_ZEI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		// 仕入税率区分
		idx++;
		str = rs.getString("SIIRE_TAX_RATE_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SIIRE_TAX_RATE_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		// 卸税区分
		idx++;
		str = rs.getString("OROSI_ZEI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_OROSI_ZEI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		String oZeiKb = null ;
		oZeiKb = StringUtils.trim(str);

		// 卸税率区分
		idx++;
		str = rs.getString("OROSI_TAX_RATE_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_OROSI_TAX_RATE_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		String oZeiRtKb = null ;
		oZeiRtKb = StringUtils.trim(str);

		// 卸売価単価
		idx++;
		// 2016/11/02 T.Arimoto #2258対応（S)
		//str = rs.getString("OROSI_BAITANKA_VL");
		str = rs.getString("OROSI_BAITANKA_NUKI_VL");
		if( !isNotBlank(str) ){
			// TR_SYOHINにて空欄だった場合
			str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
		}
		// 2016/11/02 T.Arimoto #2258対応（E)
		if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)){
			zeiKb = oZeiKb;
		}

		zei_kb = 0;
		if (zeiKb.equals("1")) {
			zei_kb = 2;
		} else {
			zei_kb = Integer.parseInt(zeiKb);
		}

		//税率取得
		tax_rt = null ;
		str_tax_rt = null ;
		if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)) {
			str_tax_rt = rs.getString("OROSHITAX_RT");
			if (isNotBlank(str_tax_rt)) {
				if (!str_tax_rt.trim().equals(deleteString)) {
					tax_rt = new BigDecimal(str_tax_rt.trim());
				} else {
					tax_rt = null ;
				}
			} else {
				str_tax_rt = rs.getString("RS_OROSHITAX_RT");
				if (isNotBlank(str_tax_rt)) {
					tax_rt = new BigDecimal(str_tax_rt.trim());
				} else {
					tax_rt = null ;
				}
			}
		} else {
			str_tax_rt = rs.getString("tax_rt");
			if (isNotBlank(str_tax_rt)) {
				if (!str_tax_rt.trim().equals(deleteString)) {
					tax_rt = new BigDecimal(str_tax_rt.trim());
				} else {
					tax_rt = null ;
				}
			} else {
				str_tax_rt = rs.getString("rs_tax_rt");
				if (isNotBlank(str_tax_rt)) {
					tax_rt = new BigDecimal(str_tax_rt.trim());
				} else {
					tax_rt = null ;
				}
			}
		}
		if (isNotBlank(str)) {
			//#6505 Mod DINH.TP 2022.06.29 （S)
//			BigDecimal gentanka_vl =new BigDecimal(str.trim());
//			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionCostUnitMode());
//			if (zeiKb.equals("1")) {
//				str = ctu.getTaxIn().toString();
//			} else {
//				str = ctu.getTaxOut().toString();
//			}
//			if (!str.trim().equals(deleteString)) {
//				// 2016/11/02 T.Arimoto #2258対応（S)
//				//pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//				pstmt.setDouble(idx, Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
//				// 2016/11/02 T.Arimoto #2258対応（E)
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
						zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
				if (zeiKb.equals("1")) {
					str = ctu.getTaxIn().toString();
				} else {
					str = ctu.getTaxOut().toString();
				}
				pstmt.setDouble(idx,
						Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(str.trim()))));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
			//#6505 Mod DINH.TP 2022.06.29 （E)
		} else {
			// 2016/11/02 T.Arimoto #2258対応（S)
//			str = rs.getString("RS_OROSI_BAITANKA_VL");
//			if (isNotBlank(str)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			pstmt.setNull(idx, Types.DOUBLE);
			// 2016/11/02 T.Arimoto #2258対応（E)
		}

		// 卸売価単価（税抜）
		idx++;
		str = rs.getString("OROSI_BAITANKA_NUKI_VL");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// #1900対応 2016.08.04 M.Akagi (E)

		// #1964対応 2016.08.22 M.Akagi (S)
		// 最低発注数
		idx++;
		str = rs.getString("MIN_HACHU_QT");
		// 2016/12/22 T.Arimoto #2841対応（S)
		//if (isNotBlank(str)) {
		//	pstmt.setString(idx, str);
		//} else {
		//	pstmt.setNull(idx, Types.VARCHAR);
		//}
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			str = rs.getString("RS_MIN_HACHU_QT");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/12/22 T.Arimoto #2841対応（E)

		// 発注不可フラグ
		idx++;
		str = rs.getString("HACHU_FUKA_FLG");
		// 2016/12/22 T.Arimoto #2841対応（S)
		//if (isNotBlank(str)) {
		//	pstmt.setString(idx, str);
		//} else {
		//	pstmt.setNull(idx, Types.VARCHAR);
		//}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HACHU_FUKA_FLG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017/03/15 T.Arimoto #4358対応（S)
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, HachuFukaYobiKbDictionary.KA.getCode());
				// 2017/03/15 T.Arimoto #4358対応（E)
			}
		}
		// 2016/12/22 T.Arimoto #2841対応（E)

		// 規格内容
		idx++;
		str = rs.getString("PER_TXT");
		// 2016/12/22 T.Arimoto #2841対応（S)
		//if (isNotBlank(str)) {
		//	pstmt.setString(idx, str);
		//} else {
		//	pstmt.setNull(idx, Types.VARCHAR);
		//}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_PER_TXT");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/12/22 T.Arimoto #2841対応（E)

		// 消費期限表示パターン
		idx++;
		str = rs.getString("SYOHI_KIGEN_HYOJI_PATTER");
		// 2016/12/22 T.Arimoto #2841対応（S)
		//if (isNotBlank(str)) {
		//	pstmt.setString(idx, str);
		//} else {
		//	pstmt.setNull(idx, Types.VARCHAR);
		//}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SYOHI_KIGEN_HYOJI_PATTER");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/12/22 T.Arimoto #2841対応（E)

		// PLU反映時間
		idx++;
		str = rs.getString("PLU_HANEI_TIME");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			// PLU反映時間の設定値が空の場合
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RS_PLU_HANEI_TIME"); // 前の世代のPLU反映時間を取得
			if ( isNotBlank(str) && isNotBlank(pluSendDt) && pluSendDt.equals(rs.getString("RS_PLU_SEND_DT"))  ) {
				// 前の世代のPLU反映時間がNULL以外で、PLU送信日が前の世代を引き継いでいた場合、前の世代を引き継ぐ
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
		// #1964対応 2016.08.22 M.Akagi (E)

		// 2016/10/25 Li.Sheng #2258 対応（S)
		// 卸売価単価(小売税)
		idx++;
		String orosi_baitanka_nuki_vl_str = rs.getString("OROSI_BAITANKA_NUKI_VL");
		if( !isNotBlank(orosi_baitanka_nuki_vl_str) ){
			orosi_baitanka_nuki_vl_str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
		}

		if (isNotBlank(orosi_baitanka_nuki_vl_str)) {

			String temp_orosi_st_zei_kb = rs.getString("ZEI_KB"); // 税区分
			if( !isNotBlank(temp_orosi_st_zei_kb) ){
				temp_orosi_st_zei_kb = rs.getString("RS_ZEI_KB");
			}

			String temp_tax_rt = rs.getString("TAX_RT"); // 税率
			if( !isNotBlank(temp_tax_rt) ){
				temp_tax_rt = rs.getString("RS_TAX_RT");
			}

			// 2016/11/22 T.Arimoto #2803対応（S)
//			BigDecimal temp_orosi_tax_rt = new BigDecimal(
//					rs.getString("TAX_RT")); // 税率
			BigDecimal temp_orosi_tax_rt = new BigDecimal(temp_tax_rt); // 税率
			// 2016/11/22 T.Arimoto #2803対応（E)

			int temp_orosi_zei_kb = 0; // 計算用税区分

			// 内税か判定
			if ("1".equals(temp_orosi_st_zei_kb)) {
				temp_orosi_zei_kb = 2;
			} else {
				temp_orosi_zei_kb = Integer.parseInt(temp_orosi_st_zei_kb);
			}

			// 卸売価単価(小売税)
			BigDecimal orosi_baitanka_nuki_vl = new BigDecimal(
					orosi_baitanka_nuki_vl_str.trim());

			// 税計算処理
			CalculateTaxUtility ctu;
			ctu = new CalculateTaxUtility(orosi_baitanka_nuki_vl,
					MoneyUtil.getFractionCostUnitLen(), temp_orosi_zei_kb,
					temp_orosi_tax_rt, MoneyUtil.getFractionCostUnitMode());

			if ("1".equals(temp_orosi_st_zei_kb)) {
				pstmt.setDouble(idx,
						Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxIn().toString()))));
			} else {
				pstmt.setDouble(idx,
						Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString()))));
			}

		} else {
			pstmt.setNull(idx, Types.DOUBLE);
		}
		// 2016/10/25 Li.Sheng #2258 対応（E)
		// #6338 MST01003 Add 2021/09/10 SIEU.D (S)
		// 割引区分
		idx++;
		str = rs.getString("WARIBIKI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			str = rs.getString("RS_WARIBIKI_KB");
			pstmt.setString(idx, str.trim());
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		// PB区分
		idx++;
		str = rs.getString("PB_SYOHIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			str = rs.getString("RS_PB_SYOHIN_KB");
			pstmt.setString(idx, str.trim());
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		// 医薬品分類
		idx++;
		str = rs.getString("IYAKUHIN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			pstmt.setNull(idx, Types.CHAR);
		}
		// #6338 MST01003 Add 2021/09/10 SIEU.D (E)
		// #6355 Add 2021/09/27 SIEU.D (S)
		// 賞味期限
		idx++;
		str = rs.getString("SYOMI_KIGEN_NISU");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setInt(idx, 0);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// pstmt.setInt(idx, 0);
			str = rs.getString("RS_SYOMI_KIGEN_NISU");
			pstmt.setString(idx, str.trim());
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		// 出荷可能限度期日
		idx++;
		str = rs.getString("SHUKKA_KIGEN_NISU");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setInt(idx, 0);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// pstmt.setInt(idx, 0);
			str = rs.getString("RS_SHUKKA_KIGEN_NISU");
			pstmt.setString(idx, str.trim());
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}

		// 入荷可能限度期日
		idx++;
		str = rs.getString("NYUKA_KIGEN_NISU");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setInt(idx, 0);
			}
		} else {
			// #33102 Mod 2025.04.09 HUY.LTH (S)
			// pstmt.setInt(idx, 0);
			str = rs.getString("RS_NYUKA_KIGEN_NISU");
			pstmt.setString(idx, str.trim());
			// #33102 Mod 2025.04.09 HUY.LTH (E)
		}
		// #6355 Add 2021/09/27 SIEU.D (E)

	}

	//Add 2015.07.13 DAI.BQ (S)
	/**
	 * 商品マスタデータ更新SQL
	 * @throws Exception
	 */
	private void setPrepareASNSyohinUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bunrui1_cd,String syohin_cd) throws Exception {
		int idx = 0;

		String str = "";

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString(bunrui1_cd));

		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);

		//有効日
		idx++;
		if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}

		// No.623 MSTB011020 Mod 2016.03.16 Vuong.LT (S)
		idx++;
		str = rs.getString("TR_SYOHIN_EIJI_NA");
		//pstmt.setString(idx, str.trim());
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RSA_SYOHIN_EIJI_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_COUNTRY_CD");
		// pstmt.setString(idx, str.trim());
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_COUNTRY_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)


		//ADD 2015/08/17 THO.VT (S)
		idx++;
		str = rs.getString("TR_MAKER_CD");
		// pstmt.setString(idx, str.trim());
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_MAKER_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		// No.623 MSTB011020 Mod 2016.03.16 Vuong.LT (E)
		//ADD 2015/08/17 THO.VT (E)

		//Add 2016.01.11 Huy.NT (S)
		idx++;
		str = rs.getString("TR_HANBAI_HOHO_KB");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.CHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_HANBAI_HOHO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)
		//Add 2016.01.11 Huy.NT (E)

		idx++;
		str = rs.getString("TR_MIN_ZAIKO_QT");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RSA_MIN_ZAIKO_QT");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "0";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_SECURITY_TAG_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//				pstmt.setString(idx, str.trim());
//		} else {
//			str = "1";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_SECURITY_TAG_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "1";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_MEMBER_DISCOUNT_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//				pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			// 2016/12/22 T.Arimoto #2841対応（S)
			//str = rs.getString("RSA_SECURITY_TAG_FG");
			str = rs.getString("RSA_MEMBER_DISCOUNT_FG");
			// 2016/12/22 T.Arimoto #2841対応（E)
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "0";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("TR_HAMPER_SYOHIN_FG");
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			str = "0";
//			pstmt.setString(idx, str);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.CHAR);
			}
		} else {
			str = rs.getString("RSA_HAMPER_SYOHIN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				str = "0";
				pstmt.setString(idx, str);
			}
		}
		// 2016/11/22 T.Arimoto #2803対応（E)

		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//更新年月日

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		// #1964対応 2016.08.22 M.Akagi (S)
		// ラベル成分
		idx++;
		str = rs.getString("LABEL_SEIBUN");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_SEIBUN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}

		// ラベル保管方法
		idx++;
		str = rs.getString("LABEL_HOKAN_HOHO");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_HOKAN_HOHO");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}

		// ラベル使い方
		idx++;
		str = rs.getString("LABEL_TUKAIKATA");
		if (isNotBlank(str)) {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setString(idx, str);
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		} else {
			// 2016/11/22 T.Arimoto #2803対応（S)
			//pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("RSA_LABEL_TUKAIKATA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
			// 2016/11/22 T.Arimoto #2803対応（E)
		}
		// #1964対応 2016.08.22 M.Akagi (E)


	}
	//Add 2015.07.13 DAI.BQ (E)

	/**
	 * バックアップ用SQL生成
	 * @throws Exception
	 */
	private String getBackupSQL(ResultSet rs, String syoriDt, String tableName) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT ");
		sql.append("  INTO DEL_" + tableName + " ");
		sql.append("SELECT '" + syoriDt + "', ");
		sql.append("       '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sql.append("       '" + rs.getString("BY_NO") + "', ");
		sql.append("       T.* ");
		sql.append("  FROM " + tableName + " T ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "' ");
		sql.append("   AND YUKO_DT    = '" + rs.getString("YUKO_DT") + "' ");

		return sql.toString();
	}


	/**
	 * 削除用SQL生成
	 * @throws Exception
	 */
	private String getDeleteSQL(ResultSet rs, String tableName) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE ");
		sql.append("  FROM " + tableName);
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "' ");
		sql.append("   AND YUKO_DT    = '" + rs.getString("YUKO_DT") + "' ");

		return sql.toString();
	}

	// 2016/10/25 T.Arimoto #2256対応（S)

	/**
	 * 現世代、1世代前、2世代前の原価の設定を行います。
	 * @param db
	 * @param bean
	 * @param Keepb
	 * @param userLocal
	 * @return true:OK false:NG
	 * @throws Exception
	 * @throws SQLException
	 */
	private boolean setGenerationGentankaMethod( ResultSet rs , String sourceSql )
			throws Exception, SQLException {

		String bunrui1Cd = rs.getString("bunrui1_cd").trim();
		String syohinCd = rs.getString("syohin_cd").trim();
		String yukoDt = rs.getString("yuko_dt").trim();

		// 商品マスタの未来世代の件数を取得
		ResultSet rsCnt = dataBase.executeQuery( getFutureDateCnt( bunrui1Cd, syohinCd, yukoDt) );

		if (rsCnt.next()) {
			// 商品マスタの未来の世代があった場合
			int recCnt = rsCnt.getInt("count");
			// 2017/02/03 T.Arimoto #1174対応（S)
			//rsCnt.close();
			dataBase.closeResultSet(rsCnt);
			// 2017/02/03 T.Arimoto #1174対応（E)

			// 反映元のデータ取得
			ResultSet sourceInfo = dataBase.executeQuery( sourceSql );

			if( null == sourceInfo || !sourceInfo.next() ){
				// 取得データが無い場合
				writeLog(Level.INFO_INT, "反映元の取得に失敗しました");
				return false;
			}

			// 反映元のデータ格納
			String[] source_yukoDt = { mst000401_LogicBean.chkNullString(sourceInfo.getString("YUKO_DT"))
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("CUR_GENERATION_YUKO_DT"))
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("ONE_GENERATION_YUKO_DT"))
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("TWO_GENERATION_YUKO_DT")) };

			String[] source_gentanka = { mst000401_LogicBean.chkNullString(sourceInfo.getString("GENTANKA_NUKI_VL")).trim()
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("CUR_GENERATION_GENTANKA_VL")).trim()
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("ONE_GENERATION_GENTANKA_VL")).trim()
					, mst000401_LogicBean.chkNullString(sourceInfo.getString("TWO_GENERATION_GENTANKA_VL")).trim() };

			// 2017/02/03 T.Arimoto #1174対応（S)
			dataBase.closeResultSet(sourceInfo);
			// 2017/02/03 T.Arimoto #1174対応（E)
			
			for( int futureCount = 0; futureCount < recCnt; ++futureCount ){

				ResultSet futureGenkaData = dataBase.executeQuery(getFutureGentanka(bunrui1Cd, syohinCd, source_yukoDt[0]));

				if ( null == futureGenkaData || !futureGenkaData.next() ){
					// 取得データが無い場合
					writeLog(Level.INFO_INT, "未来世代の取得に失敗しました");
					return false;
				}
				// 取得した世代情報（有効日、原価）格納
				String[] future_yukoDt = { mst000401_LogicBean.chkNullString(futureGenkaData.getString("YUKO_DT"))
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("CUR_GENERATION_YUKO_DT"))
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("ONE_GENERATION_YUKO_DT"))
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("TWO_GENERATION_YUKO_DT")) };
				String[] future_gentanka = { mst000401_LogicBean.chkNullString(futureGenkaData.getString("GENTANKA_NUKI_VL")).trim()
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("CUR_GENERATION_GENTANKA_VL")).trim()
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("ONE_GENERATION_GENTANKA_VL")).trim()
						, mst000401_LogicBean.chkNullString(futureGenkaData.getString("TWO_GENERATION_GENTANKA_VL")).trim() };

				// 2017/02/03 T.Arimoto #1174対応（S)
				//futureGenkaData.close();
				dataBase.closeResultSet(futureGenkaData);
				// 2017/02/03 T.Arimoto #1174対応（E)

				// 原価の世代判定と未来世代の更新処理
				if(!setGenerationFromSourceToFuture( dataBase, bunrui1Cd, syohinCd,
						source_yukoDt, future_yukoDt, source_gentanka, future_gentanka ) ){
					// 未来世代への更新を失敗した場合
					writeLog(Level.INFO_INT, "未来世代の更新に失敗しました");
					return false;
				}

				// 未来世代のデータを送信元の格納
				source_yukoDt = future_yukoDt;
				source_gentanka = future_gentanka;
			}
		} else {
			// 未来世代がない場合は、処理を行いません
			if(null != rsCnt){
				// 2017/02/03 T.Arimoto #1174対応（S)
				//rsCnt.close();
				dataBase.closeResultSet(rsCnt);
				// 2017/02/03 T.Arimoto #1174対応（E)
			}
		}
		return true;
	}

	/**
	 * 直近未来データの原単価・有効日、世代の原単価・有効日を取得します
	 * 商品マスタ用
	 * @throws
	 */
	private static String getFutureGentanka(String bunrui1Cd, String syohinCd, String yukoDt) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   YUKO_DT, ");
		sql.append("   GENTANKA_NUKI_VL, ");
		sql.append("   CUR_GENERATION_YUKO_DT, ");
		sql.append("   CUR_GENERATION_GENTANKA_VL, ");
		sql.append("   ONE_GENERATION_YUKO_DT, ");
		sql.append("   ONE_GENERATION_GENTANKA_VL, ");
		sql.append("   TWO_GENERATION_YUKO_DT, ");
		sql.append("   TWO_GENERATION_GENTANKA_VL ");
		sql.append(" FROM ");
		sql.append("   R_SYOHIN ");
		sql.append(" WHERE ");
		sql.append("   BUNRUI1_CD = '"+ bunrui1Cd +"' AND ");
		sql.append("   SYOHIN_CD = '"+ syohinCd +"' AND ");
		// 2016/11/11 T.Arimoto #2256対応（S)
		// sql.append("   DELETE_FG = '0' AND ");
		// 2016/11/11 T.Arimoto #2256対応（E)
		sql.append("   YUKO_DT = ( ");
		sql.append("     SELECT ");
		sql.append("       MIN(YUKO_DT) ");
		sql.append("     FROM R_SYOHIN ");
		sql.append("     WHERE ");
		sql.append("       BUNRUI1_CD = '" + bunrui1Cd + "' AND ");
		sql.append("       SYOHIN_CD = '" + syohinCd + "' AND ");
		// 2016/11/11 T.Arimoto #2256対応（S)
		// sql.append("       YUKO_DT > '" + yukoDt + "' AND ");
		sql.append("       YUKO_DT > '" + yukoDt + "' ) ");
		// sql.append("       DELETE_FG = '0' ) ");
		// 2016/11/11 T.Arimoto #2256対応（E)

		return sql.toString();
	}

	/**
	 * 反映元の世代原価データから未来世代の世代原価データを設定します
	 * 未来世代のデータベースの更新も行います
	 * @param db
	 * @param bunrui1Cd
	 * @param syohinCd
	 * @param source_yukoDt
	 * @param future_yukoDt
	 * @param source_gentanka 反映元のデータ
	 * @param future_gentanka 反映先の未来世代のデータ
	 * @return true:反映先のデータ更新成功 false:反映先のデータ更新失敗
	 * @throws Exception
	 * @throws SQLException
	 */
	private boolean setGenerationFromSourceToFuture( MasterDataBase db, String bunrui1Cd, String syohinCd,
			String[] source_yukoDt, String[] future_yukoDt, String[] source_gentanka, String[] future_gentanka )
					throws Exception, SQLException {

		try{
			// 原価の世代判定処理開始
			if(jp.co.vinculumjapan.mdware.common.util.StringUtility.decimalCompare(MoneyUtil.removeFormatMoney(source_gentanka[NOW_GEN_TABLE_INDEX]), future_gentanka[NOW_GEN_TABLE_INDEX]) != 0 ){
				// 未来世代の現在の原価と反映元の現在の原価が異なっていた場合

				// 未来世代の現世代に未来世代の現在の有効日と原価を設定
				future_yukoDt[CUR_GEN_TABLE_INDEX] = future_yukoDt[NOW_GEN_TABLE_INDEX];
				future_gentanka[CUR_GEN_TABLE_INDEX] = future_gentanka[NOW_GEN_TABLE_INDEX];
				// 未来世代の1世代前に反映元の現世代の原価と有効日を設定
				future_yukoDt[ONE_GEN_TABLE_INDEX] = source_yukoDt[CUR_GEN_TABLE_INDEX];
				future_gentanka[ONE_GEN_TABLE_INDEX] = source_gentanka[CUR_GEN_TABLE_INDEX];
				// 未来世代の2世代前に反映元の1世代前の原価と有効日を設定
				future_yukoDt[TWO_GEN_TABLE_INDEX] = source_yukoDt[ONE_GEN_TABLE_INDEX];
				future_gentanka[TWO_GEN_TABLE_INDEX] = source_gentanka[ONE_GEN_TABLE_INDEX];
			} else {
				// 未来世代の現在の原価と反映元の現在の原価が等しかった場合

				// 未来世代の現世代に自分の現世代の有効日と原価を設定
				future_yukoDt[CUR_GEN_TABLE_INDEX] = source_yukoDt[CUR_GEN_TABLE_INDEX];
				future_gentanka[CUR_GEN_TABLE_INDEX] = source_gentanka[CUR_GEN_TABLE_INDEX];
				// 未来世代の1世代前に自分の1世代前の有効日と原価を設定
				future_yukoDt[ONE_GEN_TABLE_INDEX] = source_yukoDt[ONE_GEN_TABLE_INDEX];
				future_gentanka[ONE_GEN_TABLE_INDEX] = source_gentanka[ONE_GEN_TABLE_INDEX];
				// 未来世代の2世代前に自分の2世代前の有効日と原価を設定
				future_yukoDt[TWO_GEN_TABLE_INDEX] = source_yukoDt[TWO_GEN_TABLE_INDEX];
				future_gentanka[TWO_GEN_TABLE_INDEX] = source_gentanka[TWO_GEN_TABLE_INDEX];
			}

			// 未来世代へのデータの設定処理
			int sqlResult = db.executeUpdate(getFutureGentankaUpdateSql(bunrui1Cd, syohinCd, future_yukoDt, future_gentanka ));

			if( 0 == sqlResult ){
				// DB更新失敗
				return false;
			} else {
				// DB更新成功
				return true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
	}


	/**
	 * 未来データ件数取得
	 * 商品マスタ用
	 * @throws
	 */
	private static String getFutureDateCnt(String bunrui1Cd, String syohinCd, String yukoDt) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   COUNT(*) as COUNT ");
		sql.append(" FROM ");
		sql.append("   R_SYOHIN ");
		sql.append(" WHERE ");
		if (!bunrui1Cd.equals("")) {
			sql.append("   BUNRUI1_CD = '"+ bunrui1Cd +"' and ");
		}
		sql.append(" syohin_cd = '"+ syohinCd +"' and ");
		sql.append(" yuko_dt > '"+ yukoDt +"'");

		return sql.toString();
	}


	/**
	 * 世代の原単価・有効日を設定します
	 * 商品マスタ用
	 * @param bunrui1Cd
	 * @param syohinCd
	 * @param yukoDt [0]:現在の有効日 [1]:現世代の有効日 [2]:１世代前の有効日 [3]:２世代前の有効日
	 * @param gentanka
	 * @return String SQL文字列
	 */
	private static String getFutureGentankaUpdateSql(String bunrui1Cd, String syohinCd, String[] yukoDt, String[] gentanka) {
		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE R_SYOHIN RS ");
		sql.append(" SET ");
		sql.append("   RS.CUR_GENERATION_YUKO_DT = '"+ yukoDt[CUR_GEN_TABLE_INDEX] +"' ");
		sql.append("   , RS.CUR_GENERATION_GENTANKA_VL = '"+ gentanka[CUR_GEN_TABLE_INDEX] +"' ");
		sql.append("   , RS.ONE_GENERATION_YUKO_DT = '"+ yukoDt[ONE_GEN_TABLE_INDEX] +"' ");
		sql.append("   , RS.ONE_GENERATION_GENTANKA_VL = '"+ gentanka[ONE_GEN_TABLE_INDEX] +"' ");
		sql.append("   , RS.TWO_GENERATION_YUKO_DT = '"+ yukoDt[TWO_GEN_TABLE_INDEX] +"' ");
		sql.append("   , RS.TWO_GENERATION_GENTANKA_VL = '"+ gentanka[TWO_GEN_TABLE_INDEX] +"' ");
		sql.append(" WHERE ");
		sql.append("   RS.BUNRUI1_CD = '"+ bunrui1Cd +"' AND ");
		sql.append("   RS.SYOHIN_CD = '"+ syohinCd +"' AND ");
		sql.append("   YUKO_DT = '"+ yukoDt[NOW_GEN_TABLE_INDEX] +"' ");

		return sql.toString();
	}


	/**
	 * 原単価・有効日、世代の原単価・有効日を取得するSQLを設定します
	 * 商品マスタ用
	 * @throws
	 */
	private static String getGenerationGentanka(String bunrui1Cd, String syohinCd, String yukoDt) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   YUKO_DT, ");
		sql.append("   GENTANKA_NUKI_VL, ");
		sql.append("   CUR_GENERATION_YUKO_DT, ");
		sql.append("   CUR_GENERATION_GENTANKA_VL, ");
		sql.append("   ONE_GENERATION_YUKO_DT, ");
		sql.append("   ONE_GENERATION_GENTANKA_VL, ");
		sql.append("   TWO_GENERATION_YUKO_DT, ");
		sql.append("   TWO_GENERATION_GENTANKA_VL ");
		sql.append(" FROM ");
		sql.append("   R_SYOHIN ");
		sql.append(" WHERE ");
		sql.append("   BUNRUI1_CD = '"+ bunrui1Cd +"' AND ");
		sql.append("   SYOHIN_CD = '"+ syohinCd +"' AND ");
		sql.append("   YUKO_DT = '"+ yukoDt +"' ");

		return sql.toString();
	}

	/**
	 * 直近過去データの原単価・有効日、世代の原単価・有効日を取得します
	 * 商品マスタ用
	 * @throws
	 */
	private static String getPastGentanka(String bunrui1Cd, String syohinCd, String yukoDt) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   YUKO_DT, ");
		sql.append("   GENTANKA_NUKI_VL, ");
		sql.append("   CUR_GENERATION_YUKO_DT, ");
		sql.append("   CUR_GENERATION_GENTANKA_VL, ");
		sql.append("   ONE_GENERATION_YUKO_DT, ");
		sql.append("   ONE_GENERATION_GENTANKA_VL, ");
		sql.append("   TWO_GENERATION_YUKO_DT, ");
		sql.append("   TWO_GENERATION_GENTANKA_VL ");
		sql.append(" FROM ");
		sql.append("   R_SYOHIN ");
		sql.append(" WHERE ");
		sql.append("   BUNRUI1_CD = '"+ bunrui1Cd +"' AND ");
		sql.append("   SYOHIN_CD = '"+ syohinCd +"' AND ");
		// 2016/11/11 T.Arimoto #2256対応（S)
		// sql.append("   DELETE_FG = '0' AND ");
		// 2016/11/11 T.Arimoto #2256対応（E)
		sql.append("   YUKO_DT = ( ");
		sql.append("     SELECT ");
		sql.append("       MAX(YUKO_DT) ");
		sql.append("     FROM R_SYOHIN ");
		sql.append("     WHERE ");
		sql.append("       BUNRUI1_CD = '" + bunrui1Cd + "' AND ");
		sql.append("       SYOHIN_CD = '" + syohinCd + "' AND ");
		// 2016/11/11 T.Arimoto #2256対応（S)
		// sql.append("       YUKO_DT < '" + yukoDt + "' AND ");
		// sql.append("       DELETE_FG = '0' ) ");
		sql.append("       YUKO_DT < '" + yukoDt + "' ) ");
		// 2016/11/11 T.Arimoto #2256対応（E)

		return sql.toString();
	}

	// 2016/10/25 T.Arimoto #2256対応（E)



	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// バッチ日付取得
		MasterDT = RMSTDATEUtil.getMstDateDt();
		DefaultYukoDt = DateChanger.addDate(MasterDT, 1);
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
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

	// チェック用ログ出力メソッド
	private void outputRs(String key, ResultSet rds)
	throws Exception{
		batchLog.getLog().info("getInfo (" + key + ")" + rds.getString("syohin_cd")
				+ rds.getString("bunrui1_cd") + "," + rds.getString("YUKO_DT") + ","
				+ rds.getString("torikomi_dt") + "," + rds.getString("excel_file_syubetsu") + "," + rds.getString("uketsuke_no") + "," + rds.getString("uketsuke_seq"));
	}

	private void outputException(String key, Exception se){
		StackTraceElement[] ste = se.getStackTrace();
		for(int s = 0; s < ste.length; s++){
			batchLog.getLog().fatal("getStackTrace(" + key + ")" + ste[s].getClassName() + "." + ste[s].getMethodName() + ":" + "(" + ste[s].getLineNumber() + ")");
		}
	}

	private void outputMessage(String key, String message)
	throws Exception{
			batchLog.getLog().info("message (" + key + ")" + message);
	}
}