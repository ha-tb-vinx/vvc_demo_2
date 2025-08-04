package mdware.master.batch.process.mb38;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.command.mst38A103_ReigaiCheck;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006501_BySyoninFgDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;



/**
 * <p>タイトル:店別商品例外マスタ生成</p>
 * <p>説明:店別商品例外マスタ生成を行います。</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @version 1.02 (2015.11.30) TU.TD FIVImart様対応
 * @Version 1.03 (2016.09.07) S.Li #1964 FIVImart対応
 * @Version 1.05 (2016.11.22) S.Takayama #2803 FIVImart対応
 * @Version 1.06 (2016.12.31) T.Han #3784 FIVImart対応
 * @version 1.07 (2017.03.06) S.Takayama #4234 FIVImart対応
 * @version 1.08 (2017.06.27) DAU.TQP #5488 FIVImart対応
 * @Version 2.00  (2021.12.15) HOAI.TTT #6409 対応
 * @version 2.01 (2022.06.16) DINH.TP #6605
 * @version 2.02 (2022.06.29) DINH.TP #6605
 * @version 2.03 (2023.05.12) TUNG.LT #13650
 */

public class MB380C05_CreateReigai {

	private MasterDataBase dataBase = null;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private final String BATCH_ID = "MB38-0C-05";
	private final String BATCH_NA = "店別商品例外マスタ生成";

	//PreparedStatement
	private PreparedStatement ReigaiInsert = null; //店別商品例外マスタInsert用
	private PreparedStatement ReigaiUpInsert = null; //店別商品例外マスタInsert用（更新レコード）
	private PreparedStatement ReigaiUpdate = null; //店別商品例外マスタUpdate用
	private PreparedStatement ReigaiDelIns = null; //店別商品例外扱マスタDelete用（削除レコード）
	private PreparedStatement ReigaiDelUp = null; //店別商品例外マスタDelete用（削除レコード）
	private PreparedStatement ReigaiDelete = null; //店別商品例外マスタDelete用

	private PreparedStatement MessageInsert = null; //処理結果メッセージInsert用

	private final String deleteString = "*"; //削除を表す文字

	private final String TABLE_NA = "TR_TENSYOHIN_REIGAI"; //対象テーブル名

	private final String SYUBETU = mst006401_DataKindDictionary.REIGAI.getCode(); //データ種別

	//マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();
	private String DefaultYukoDt = DateChanger.addDate(MasterDT, 1);

	//登録可能な店舗区分
	private String useTenpoKb = null;

	private MB380000_CommonSql comSql = new MB380000_CommonSql(); //共通SQLクラス

	private MB380008_CommonReigaiSql ReigaiSql = new MB380008_CommonReigaiSql(MasterDT);

	mst38A103_ReigaiCheck ReigaiCheck = null;

	MB380005_CommonReigaiCheck Check = null; //店別商品例外項目チェック用クラス

	private int ICOUNT = 0; //新規登録数
	private int UCOUNT = 0; //更新登録数
	private int DCOUNT = 0; //削除数
	private int CCOUNT = 0; //取消数
	private int ECOUNT = 0; //エラー数

	private static final int DB2_0803 = -803; //DB2用一意制約エラーコード

	private static final String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();

	private final int DEAD_LOCK_ERROR  = -913;
	private final int NOT_EXIST_ERROR = -204;
	private int waitTime = 0;
	private int retryCnt = 0;

	/**
	 * コンストラクタ
	 */
	public MB380C05_CreateReigai() {
		dataBase = new MasterDataBase("rbsite_ora");
	}

	private String batchID;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB380C05_CreateReigai(MasterDataBase db) {
		this.dataBase = db;
		if (db == null) {
			this.dataBase = new MasterDataBase("rbsite_ora");
		}
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
			// #6409 Mod 2021.12.15 HOAI.TTT (S)
			//Map map = MB380001_CommonMessage.getMsg();
			Map map = MB380C01_CommonMessage.getMsg();
			// #6409 Mod 2021.12.15 HOAI.TTT (E)
	    	// 送信対象の店舗区分を取得
			Map tenMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_USE_TENPO_KB);
			useTenpoKb = SqlSupportUtil.createInString(tenMap.keySet().toArray());

			//非承認のデータを処理する
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "否認データ処理開始");
//			sql = comSql.getHiSyoninMessageSQL(TABLE_NA, SYUBETU, map);
//			iCount = dataBase.executeUpdate(sql);
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, iCount + "件の否認データのメッセージを登録しました。");

//			sql = comSql.getHiSyoninSQL(TABLE_NA, BATCH_ID);
//			iCount = dataBase.executeUpdate(sql);
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, iCount + "件の否認データを処理済（エラー）に更新しました。");
//			dataBase.commit();
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "否認データ処理終了");


			//後者優先ルールによる登録対象外データ
			writeLog(Level.INFO_INT, "承認データかつ登録チェック対象外データ処理開始");

			// ワークテーブルを削除
			DBUtil.truncateTable(dataBase, "WK_MB38_TAISYOGAI_REIGAI");

			//対象外のデータを取得
			sql = getSyoriTaisyoSQL();
			iCount = dataBase.executeUpdate(sql);

			//メッセージ作成
			sql = getKosyaYusenMessageSQL(map);
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の承認データかつ登録チェック対象外データのメッセージを登録しました。");

			//マスタメンテフラグを更新
			sql = getUpdKosyaYusenTaisyogai(TABLE_NA, SYUBETU);
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の承認データかつ登録チェック対象外データを処理済（エラー）に更新しました。");
			dataBase.commit();
			writeLog(Level.INFO_INT, "承認データかつ登録チェック対象外データ処理終了");

			// 2016.11.16 M.Akagi #2256 (S)
			//データ補正処理する
			//sql = comSql.getYukoDtFollowSQL(TABLE_NA, SYUBETU, MasterDT);
			//iCount = dataBase.executeUpdate(sql);
			//dataBase.commit();
			// 2016.11.16 M.Akagi #2256 (E)

			ReigaiInsert = getPreparedReigaiInsSQL(dataBase);
			ReigaiUpInsert = ReigaiSql.getPreparedReigaiUpInsSQL(dataBase, BATCH_ID);
			ReigaiUpdate = getPreparedReigaiUpSQL(dataBase);
			ReigaiDelUp = ReigaiSql.getPreparedReigaiDelUpSQL(dataBase, BATCH_ID);
			ReigaiDelete = getPreparedReigaiDelSQL(dataBase);

			MessageInsert = comSql.getPreparedMessageSQL(dataBase);

			Check = new MB380005_CommonReigaiCheck(MessageInsert, map, dataBase);

			ReigaiCheck = new mst38A103_ReigaiCheck(dataBase);

			//予約取消
			writeLog(Level.INFO_INT, "取消データ処理開始");
			crs = executeQueryRetry( getCancelSelSQL(), BATCH_ID, BATCH_NA, waitTime, retryCnt);
			writeLog(Level.INFO_INT, "取消データ取得処理終了");

			dataProcess(crs);

			// 2017.01.31 T.han #3784対応（S)
			//writeLog(Level.INFO_INT, DCOUNT + "件の予約取消データを処理済に更新しました。");
			writeLog(Level.INFO_INT, CCOUNT + "件の予約取消データを処理済に更新しました。");
			// 2017.01.31 T.han #3784対応（E)
			writeLog(Level.INFO_INT, ECOUNT + "件の予約取消データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "取消データ処理終了");
			ECOUNT = 0;

			//新規データ取得
			writeLog(Level.INFO_INT, "新規データ取得処理開始");
			irs = executeQueryRetry(getInsertSelSQL(), BATCH_ID, BATCH_NA, waitTime, retryCnt);
			writeLog(Level.INFO_INT, "新規データ取得処理終了");

			dataProcess(irs);

			writeLog(Level.INFO_INT, ICOUNT + "件の新規登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の新規登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "新規登録データ処理終了");
			ECOUNT = 0;

			//更新
			writeLog(Level.INFO_INT, "更新データ処理開始");
			urs = executeQueryRetry(getUpdateSelSQL(), BATCH_ID, BATCH_NA, waitTime, retryCnt);
			writeLog(Level.INFO_INT, "更新データ取得処理終了");

			dataProcess(urs);

			writeLog(Level.INFO_INT, UCOUNT + "件の更新登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の更新登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "更新データ処理終了");
			ECOUNT = 0;

			//削除
			writeLog(Level.INFO_INT, "削除データ処理開始");
			drs = executeQueryRetry( getDeleteSelSQL(), BATCH_ID, BATCH_NA, waitTime, retryCnt);
			writeLog(Level.INFO_INT, "削除データ取得処理終了");

			dataProcess(drs);

			writeLog(Level.INFO_INT, DCOUNT + "件の削除登録データを処理済に更新しました。");
			writeLog(Level.INFO_INT, ECOUNT + "件の削除登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, "削除データ処理終了");

			dataBase.commit();

		} catch (SQLException se) {
			dataBase.rollback();
			writeError(se);
		throw se;
		} catch (Exception e) {
			dataBase.rollback();
			writeError(e);
			throw e;
		} finally {
			//dataBase.rollback();
			dataBase.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * @param rs
	 */
	private void dataProcess(ResultSet rs) throws SQLException, Exception {
		// ===BEGIN=== Modify by kou 2006/8/23
		String JyotaiFlg = null;
		String torikomiDT = null; 			//取込日
		String excelSyubetsu = null; 		//EXCELファイル種別
		String uketsukeNO = null; 			//受付No.

		String JyotaiFlg_old = null;
		String torikomiDT_old = null; 			//前のレコードの取込日
		String excelSyubetsu_old = null; 		//前のレコードのEXCELファイル種別
		String uketsukeNO_old = null; 			//前のレコードの受付No.

		String updateJyotaiSql = null;

		while (rs.next()) {
			JyotaiFlg = null;
			torikomiDT = rs.getString("torikomi_dt"); //取込日
			excelSyubetsu = rs.getString("excel_file_syubetsu"); //EXCELファイル種別
			uketsukeNO = rs.getString("uketsuke_no"); //受付No.

			if(dataCheck(rs)){
//				JyotaiFlg = "8";		// 正常
			}else{
				JyotaiFlg = "2";		// エラー
			}

			// 登録処理状態更新
			if(uketsukeNO_old != null && !uketsukeNO_old.equals(uketsukeNO) && JyotaiFlg_old != null){
				updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT_old,
								excelSyubetsu_old, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
				dataBase.execute(updateJyotaiSql);
				JyotaiFlg_old = null;
			}

			if(JyotaiFlg != null){
				JyotaiFlg_old = JyotaiFlg;
			}
			uketsukeNO_old = uketsukeNO;
			torikomiDT_old = torikomiDT;
			excelSyubetsu_old = excelSyubetsu;

			batchLog.getLog().info(uketsukeNO + " " + rs.getString("uketsuke_seq"));
		}
		//20060926 hiu@vjc パフォーマンス改善
		//削除、更新、新規ごとにこの場所でexecuteする。（メモリ圧迫を考慮した）
		if(ReigaiInsert!=null){
			ReigaiInsert.executeBatch();
		}
		if(ReigaiUpInsert!=null){
			ReigaiUpInsert.executeBatch();
		}
		if(ReigaiUpdate!=null){
			ReigaiUpdate .executeBatch();
		}

		if(ReigaiDelUp!=null){
			ReigaiDelUp .executeBatch();
		}
		if(ReigaiDelete!=null){
			ReigaiDelete .executeBatch();
		}
		if(MessageInsert!=null){
			MessageInsert .executeBatch();
		}

		if(uketsukeNO_old != null && JyotaiFlg_old != null){
			updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT,
							excelSyubetsu, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
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
		boolean checkFg = true; //エラーフラグ
		String[] key = new String[6]; //キー項目
		boolean insertFg = true; //Insert、Updateを判断するフラグ
		boolean deleteFg = false;
		boolean upinsertFg = false;
		boolean warningFg = false;
		String mstMainteFg = mst006801_MstMainteFgDictionary.ERROR.getCode();
		String delete_dt = "";

		key[0] = rs.getString("torikomi_dt"); //取込日
		key[1] = rs.getString("excel_file_syubetsu"); //EXCELファイル種別
		key[2] = rs.getString("uketsuke_no"); //受付No.
		key[3] = rs.getString("uketsuke_seq"); //受付SEQNo.
		key[4] = this.SYUBETU; //シート種別
		key[5] = rs.getString("by_no"); 				// 登録承認バイヤーNO

		Check.setKey(key);

		//修正区分
		String syusei_kb = rs.getString("syusei_kb");

		//商品コード
		String syouhin =  rs.getString("syohin_cd");

		// チェック処理
		if (ReigaiCheck.process(rs, true) == null) {
			checkFg = false;
		}

		//	削除、予約取消以外
		if (!syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode()) && !syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			//店舗発注開始・終了日
			if (!Check.hachuDtStEdCheck(rs)) {
				checkFg = false;
			}
		}

		//商品コード
		String syohin_cd = "";

		//テーブル名
		String table_nm = "";

		//商品コードは空ではない場合
		if (isNotBlank(syouhin) && syouhin.trim().length() > 3) {
			syohin_cd = syouhin;
		} else {
			//商品マスタTR_xxを設定する
			table_nm = "TR_SYOHIN";

			//商品コードを取得する
			syohin_cd = getSyohinCdSQL(table_nm, rs);

			// 商品コードを取得できない場合、エラー
			if(!Check.syohinNullCheck(syohin_cd)){
				checkFg = false;
			}

		}

		//店別商品例外マスタとの関連チェック
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			//新規登録
//			if (!Check.insertReigaiCheck(rs, "bunrui1_ck", syohin_cd)) {
			if (!Check.insertReigaiCheck(rs, "bunrui1_cd", syohin_cd)) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
				deleteFg = Check.getDeleteFg();
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
			//更新
//			if (!Check.updateReigaiCheck(rs, "bunrui1_ck")) {
			if (!Check.updateReigaiCheck(rs, "bunrui1_cd")) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
				upinsertFg = Check.getUpInsertFg();
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {

			//削除
//			if (!Check.deleteReigaiCheck(rs, "bunrui1_ck")) {
			if (!Check.deleteReigaiCheck(rs, "bunrui1_cd")) {
				checkFg = false;
			} else {

				if (checkFg && Check.syohinDeleteCheck(rs)) {
					//商品が削除されている場合はワーニング扱いにする
					mstMainteFg = mst006801_MstMainteFgDictionary.KEIKOKU.getCode();
					checkFg = false;
				} else {
					insertFg = Check.getInsertFg();
					delete_dt = Check.getYukoDt();
				}
			}
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			//取消
//			if (!Check.deleteReigaiCheck(rs, "bunrui1_ck")) {
			if (!Check.cancelReigaiCheck(rs, "bunrui1_cd")) {
				checkFg = false;
			} else {
				insertFg = Check.getInsertFg();
				delete_dt = Check.getYukoDt();
			}
		}
		if (!checkFg) {
			//エラーの場合
			//20060926 hiu@vjc パフォーマンス改善
			//MessageInsert.executeBatch();
			//20060926 hiu@vjc パフォーマンス改善
			ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mstMainteFg, BATCH_ID));

			if (mstMainteFg.equals(mst006801_MstMainteFgDictionary.KEIKOKU.getCode())) {
				//警告のみの場合は正常で復帰
				return true;
			} else {
				return false;
			}
		} else {
			//エラーでない場合
			try {
				//↓↓2006/08/01 ryo 業種に分けるの場合、この部分の処理から修正する
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {

					if (deleteFg){
						setPrepareReigaiDeleteSQL(ReigaiDelete, rs);
						//20060926 hiu@vjc パフォーマンス改善
						//ReigaiDelete.executeUpdate();
						ReigaiDelete.addBatch();
						//20060926 hiu@vjc パフォーマンス改善
					}

					//新規登録
//					=== BEGIN === Modify by ryo 2006/8/2
//					setPrepareReigaiInsSQL(ReigaiInsert, rs);
					setPrepareReigaiInsSQL(ReigaiInsert, rs, syohin_cd);
//					=== END === Modify by ryo 2006/8/2

					//20060926 hiu@vjc パフォーマンス改善
					//ReigaiInsert.executeUpdate();
					ReigaiInsert.addBatch ();
					//20060926 hiu@vjc パフォーマンス改善
//					=== BEGIN === Add by ryo 2006/8/1
//					↓↓2006.09.13 H.Yamamoto カスタマイズ修正↓↓
//					if (!isNotBlank(syouhin)) {
					if (!isNotBlank(syouhin) || syouhin.trim().length() < 3) {
//					↑↑2006.09.13 H.Yamamoto カスタマイズ修正↑↑
						//TR_SYOHIN_GROの商品コードを更新する
						dataBase.executeUpdate(comSql.updateSyohinCodeSQL(TABLE_NA, key, syohin_cd));
					}
//					=== END === Add by ryo 2006/8/1
				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
					//更新
					if (upinsertFg) {
//						=== BEGIN === Modify by ryo 2006/8/2
//						setPrepareReigaiInsSQL(ReigaiInsert, rs);
						setPrepareReigaiInsSQL(ReigaiInsert, rs, syouhin);
//						=== END === Modify by ryo 2006/8/2
						//20060926 hiu@vjc パフォーマンス改善
						//ReigaiInsert.executeUpdate();
						ReigaiInsert.addBatch();
						//20060926 hiu@vjc パフォーマンス改善
					} else{
						if (insertFg) {
							//No.187 MSTB011 Mod 2015.12.09 TU.TD (S)
							//ReigaiSql.setPrepareReigaiUpInsSQL(ReigaiUpInsert, rs, "bunrui1_cd");
							ReigaiSql.setPrepareReigaiUpInsSQL(ReigaiUpInsert, rs, "bunrui1_cd", dataBase);
							//No.187 MSTB011 Mod 2015.12.09 TU.TD (E)
							//20060926 hiu@vjc パフォーマンス改善
							//ReigaiUpInsert.executeUpdate();
							ReigaiUpInsert.addBatch() ;
							//20060926 hiu@vjc パフォーマンス改善
						} else {
							setPrepareReigaiUpSQL(ReigaiUpdate, rs);
							//20060926 hiu@vjc パフォーマンス改善
							//ReigaiUpdate.executeUpdate();
							ReigaiUpdate.addBatch();
							//20060926 hiu@vjc パフォーマンス改善
						}
					}
				//↑↑2006/08/01 ryo 業種に分けるの場合、この部分の処理まで修正する
				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {
					//削除
					if (insertFg) {
						ReigaiDelIns = ReigaiSql.getPreparedReigaiDelInsSQL(dataBase, rs, BATCH_ID);
						ReigaiSql.setPrepareReigaiDelInsSQL(ReigaiDelIns, rs, delete_dt, "bunrui1_cd");
						//20060926 hiu@vjc パフォーマンス改善
						//ReigaiDelIns.executeUpdate();
//						↓↓2007.03.05 H.Yamamoto カスタマイズ修正↓↓
//						ReigaiDelIns.addBatch() ;
						ReigaiDelIns.executeUpdate();
						ReigaiDelIns.close();
//						↑↑2007.03.05 H.Yamamoto カスタマイズ修正↑↑
						//20060926 hiu@vjc パフォーマンス改善
					} else {
						ReigaiSql.setPrepareReigaiDelUpSQL(ReigaiDelUp, rs, "bunrui1_cd");
						//20060926 hiu@vjc パフォーマンス改善
						//ReigaiDelUp.executeUpdate();
						ReigaiDelUp.addBatch() ;
						//20060926 hiu@vjc パフォーマンス改善
					}

				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {

					String sql = "";

					// 物理削除対象のレコードをバックアップ
					sql = getBackupSQL(rs, MasterDT);
					dataBase.executeUpdate(sql);

					// 物理削除
					sql = getDeleteSQL(rs);
					dataBase.executeUpdate(sql);
				}

			} catch (SQLException e) {
				//ロールバック
				dataBase.rollback();
				if (e.getErrorCode() == DB2_0803) {
					String msg = "";
					if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
						msg = "0009"; //既に登録されている。（チェック中に画面より登録された場合）
					} else {
						msg = "0010"; //他のユーザーにより変更されている。
					}
					Check.setPreparedMessageSQL(msg,rs.getString("by_no"));
					//20060926 hiu@vjc パフォーマンス改善
					//MessageInsert.executeUpdate();
					MessageInsert.addBatch() ;
					//20060926 hiu@vjc パフォーマンス改善
					ECOUNT += dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.ERROR.getCode(), BATCH_ID));
					return false;
				} else {
					batchLog.getLog().fatal(BATCH_ID, BATCH_NA, "店別商品例外マスタのデータ登録に失敗しました。");
					throw e;
				}
			}
		}

		//登録完了（正常）
		Check.setPreparedMessageSQL("0000",rs.getString("by_no"));
		//20060926 hiu@vjc パフォーマンス改善
		MessageInsert.addBatch() ;
		//MessageInsert.executeUpdate();
		//20060926 hiu@vjc パフォーマンス改善
		int tmp_count = dataBase.executeUpdate(comSql.getMstMainteSQL(TABLE_NA, key, mst006801_MstMainteFgDictionary.SYORIZUMI.getCode(), BATCH_ID));

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
	public String getInsertSelSQL() {
		StringBuffer sql = new StringBuffer();


		sql.append("select distinct");
		sql.append("    tr.torikomi_dt as torikomi_dt,"); 						//取込日
		sql.append("    tr.excel_file_syubetsu as excel_file_syubetsu,"); 		//EXCELファイル種別
		sql.append("    tr.uketsuke_no as uketsuke_no,"); 						//受付No
		sql.append("    tr.uketsuke_seq as uketsuke_seq,"); 					//受付SEQNo
		sql.append("    tr.syusei_kb as syusei_kb,"); 							//修正区分
		sql.append("    tr.tr_bunrui1_cd as bunrui1_cd,"); 							//部門コード
		sql.append("    bumon_ck.code_cd as bunrui1_ck,"); //部門コード2（品種、部門チェック）
		sql.append("    tr.tr_syohin_cd as syohin_cd,"); //商品コード
		sql.append("    tr.tr_tenpo_cd as tenpo_cd,"); //店舗コード
		sql.append("    tenpo_ck.tenpo_cd as tenpo_ck,"); //店舗コード存在チェック
//		sql.append("    tr.yuko_dt as yuko_dt,"); //有効日
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("    tr.ck_yuko_dt as ck_yuko_dt,"); //有効日
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("    tr.tmp_yuko_dt as yuko_dt,"); //有効日
		sql.append("    tr.tr_ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
		sql.append("    tr.tr_ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
		sql.append("    replace(tr.tr_gentanka_vl,',') as gentanka_vl,"); //原単価
		sql.append("    replace(tr.tr_baitanka_vl,',') as baitanka_vl,"); //売単価
		sql.append("    tr.tr_max_hachutani_qt as max_hachutani_qt,"); //最大発注数
		sql.append("    tr.tr_eos_kb as eos_kb,"); //EOS区分
		sql.append("    eos_ck.code_cd as eos_ck,"); //EOS区分存在チェック
		sql.append("    tr.tr_buturyu_kb as buturyu_kb,"); //物流区分
		sql.append("    buturyu_ck.code_cd as buturyu_ck,"); //物流区分存在チェック
		sql.append("    tr.tr_siiresaki_cd as siiresaki_cd,"); //仕入先コード
		sql.append("    siiresaki_ck.torihikisaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
		sql.append("    tr.tr_bin_1_kb as bin_1_kb,"); //①便区分
		sql.append("    tr.tr_bin_2_kb as bin_2_kb,"); //②便区分
		sql.append("    tr.tr_yusen_bin_kb as yusen_bin_kb,"); //優先便区分
		sql.append("    tr.tr_plu_send_dt as plu_send_dt,"); //PLU送信日
		sql.append("    tr.by_no as by_no, "); //バイヤーNO
		sql.append("    tr.syohin_gyo_no as syohin_gyo_no,");//商品マスタ登録行№
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		sql.append("    tr.tr_plu_hanei_time as plu_hanei_time,");//PLU反映時間
		sql.append("    tr.tr_hachu_fuka_flg as hachu_fuka_flg,");//発注不可フラグ
		sql.append("    tr.tr_orosi_baitanka_nuki_vl as orosi_baitanka_nuki_vl,");//卸売価単価(税抜)
        /* #1964 Add 2016.09.07 Li.Sheng (E) */
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		sql.append("    tr.tr_baika_haishin_fg as baika_haishin_fg,");//取扱停止
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)
// 2017.01.31 T.han #3784対応（S)
		sql.append("    tr.tr_siire_kahi_kb as siire_kahi_kb,");//仕入可否区分
		sql.append("    tr.tr_henpin_kahi_kb as henpin_kahi_kb,");//返品可否区分
		sql.append("    '' as rtr_siire_kahi_kb,");//仕入可否区分
		sql.append("    '' as rtr_henpin_kahi_kb,");//返品可否区分
// 2017.01.31 T.han #3784対応（E)
		sql.append("    '' as rtr_ten_hachu_st_dt,");
		sql.append("    '' as rtr_ten_hachu_ed_dt,");
		sql.append("    '' as rtr_gentanka_vl,");
		// #36271 ADD 2025.0708 THONG.LT (S)
		sql.append("    '' as rtr_gentanka_nuki_vl,");
		// #36271 ADD 2025.0708 THONG.LT (E)
		sql.append("    '' as rtr_baitanka_vl,");
		sql.append("    '' as rtr_max_hachutani_qt,");
		sql.append("    '' as rtr_eos_kb,");
		sql.append("    '' as rtr_siiresaki_cd,");
		sql.append("    '' as rtr_yusen_bin_kb,");
		sql.append("    '' as rtr_bin_1_kb,");
		sql.append("    '' as rtr_bin_2_kb,");
		sql.append("    '' as rtr_buturyu_kb,");
		sql.append("    '' as rtr_plu_send_dt,");
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		sql.append("    '' as rtr_plu_hanei_time,");
		sql.append("    '' as rtr_baika_haishin_fg,");
		sql.append("    '' as rtr_orosi_baitanka_nuki_vl,"); //卸売価単価(税抜)
		sql.append("    '' as rtr_hachu_fuka_flg,"); //発注不可フラグ
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)
		sql.append("    s.syohin_cd as s_syohin_cd, "); //商品コード（商品マスタ）
		sql.append("    s.ten_hachu_st_dt as s_ten_hachu_st_dt,"); //店舗発注開始日（商品マスタ）
		sql.append("    s.ten_hachu_ed_dt as s_ten_hachu_ed_dt, "); //店舗発注終了日（商品マスタ）
		sql.append("    S.BIN_1_KB AS S_BIN_1_KB,"); 				//①便区分（商品マスタ）
		sql.append("    S.BIN_2_KB AS S_BIN_2_KB,"); 				//②便区分（商品マスタ）
		sql.append("    S.GENTANKA_VL AS S_GENTANKA_VL,"); 			//原単価（商品マスタ）
		sql.append("    S.BAITANKA_VL AS S_BAITANKA_VL,"); 			//売単価（商品マスタ）
		sql.append("    BIN1_CK.CODE_CD AS BIN1_CK,"); 				//①便区分存在チェック
		sql.append("    BIN2_CK.CODE_CD AS BIN2_CK"); 				//②便区分存在チェック

		sql.append("  FROM (SELECT R.*, ");
		sql.append("               T.BY_NO, ");
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("               R.TR_YUKO_DT AS ck_yuko_dt, ");
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("               COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_TENSYOHIN_REIGAI ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND TENPO_CD   = R.TR_TENPO_CD ");
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS REIGAI_YUKO_DT, ");

		sql.append("               (SELECT MAX(TEKIYO_START_DT) ");
		sql.append("                  FROM R_TORIHIKISAKI ");
		sql.append("                 WHERE COMP_CD          = '").append("0000").append("' ");
		sql.append("                   AND CHOAI_KB         = '").append("1").append("' ");
		sql.append("                   AND TORIHIKISAKI_CD  = R.TR_SIIRESAKI_CD ");
		sql.append("                   AND TORIKESHI_FG     = '").append("0").append("' ");
		sql.append("                   AND TEKIYO_START_DT <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS TORIHIKISAKI_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_TENSYOHIN_REIGAI R ");
		sql.append("            ON R.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND R.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND R.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND R.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND R.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON 	 ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		// #13650 ADD 2023.05.12 TUNG.LT (S)
		sql.append("   AND S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
		// #13650 ADD 2023.05.12 TUNG.LT (S)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_TENSYOHIN_REIGAI RT ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RT.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RT.TENPO_CD   = TR.TR_TENPO_CD ");
		sql.append("   AND RT.YUKO_DT    = TR.REIGAI_YUKO_DT ");
		sql.append("   AND RT.DELETE_FG  = '").append(DEL_FG).append("' ");

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		//仕入先
		sql.append("    LEFT JOIN ");
		sql.append("         R_TORIHIKISAKI SIIRESAKI_CK");
		sql.append("      ON SIIRESAKI_CK.COMP_CD            = '").append("0000").append("' ");
		sql.append("     AND SIIRESAKI_CK.CHOAI_KB           = '").append("1").append("' ");
		sql.append("     AND SIIRESAKI_CK.TORIHIKISAKI_CD    = TR.TR_SIIRESAKI_CD ");
		sql.append("     AND SIIRESAKI_CK.TORIHIKI_TEISHI_KB = '").append("0").append("' ");
		sql.append("     AND SIIRESAKI_CK.TORIKESHI_FG       = '").append("0").append("' ");
		sql.append("     AND SIIRESAKI_CK.TEKIYO_START_DT    = TR.TORIHIKISAKI_YUKO_DT ");
		sql.append("     AND SIIRESAKI_CK.DELETE_FG          = '").append(DEL_FG).append("' ");

		// 分類１
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf bumon_ck ");
		sql.append("    on bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   and bumon_ck.code_cd       = trim(tr.tr_bunrui1_cd) ");
		sql.append("   and bumon_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 店舗
		sql.append("  LEFT JOIN ");
		sql.append("       r_tenpo tenpo_ck ");
		sql.append("    on tenpo_ck.tenpo_cd   = tr.tr_tenpo_cd ");
		sql.append("   and tenpo_ck.tenpo_kb   in (").append(useTenpoKb).append(") ");
		sql.append("   and coalesce(tenpo_ck.heiten_dt, '99999999') >= '").append(MasterDT).append("' ");
		sql.append("   and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");

		// EOS区分
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf eos_ck ");
		sql.append("    on eos_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
		sql.append("   and eos_ck.code_cd       = tr.tr_eos_kb ");
		sql.append("   and eos_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 物流区分
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf buturyu_ck ");
		sql.append("    on buturyu_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DISTRIBUTION_DIVISION, userLocal)).append("' ");
		sql.append("   and buturyu_ck.code_cd       = TRIM(tr.tr_buturyu_kb) ");
		sql.append("   and buturyu_ck.delete_fg     = '").append(DEL_FG).append("'");

		//①便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN1_CK ");
		sql.append("    ON BIN1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN1_CK.CODE_CD       = TR.TR_BIN_1_KB ");
		sql.append("   AND BIN1_CK.DELETE_FG     = '").append(DEL_FG).append("'");

		//②便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN2_CK ");
		sql.append("    ON BIN2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN2_CK.CODE_CD       = TR.TR_BIN_2_KB ");
		sql.append("   AND BIN2_CK.DELETE_FG     = '").append(DEL_FG).append("'");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");										//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");								//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");										//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");										//受付SEQ №

		return sql.toString();
	}

	/**
	 * 更新データ取得SQL生成
	 * @throws
	 */
	public String getUpdateSelSQL() {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct ");
		sql.append("    tr.torikomi_dt as torikomi_dt,"); //取込日
		sql.append("    tr.excel_file_syubetsu as excel_file_syubetsu,"); //EXCELファイル種別
		sql.append("    tr.uketsuke_no as uketsuke_no,"); //受付No
		sql.append("    tr.uketsuke_seq as uketsuke_seq,"); //受付SEQNo
		sql.append("    tr.syusei_kb as syusei_kb,"); //修正区分
		sql.append("    tr.tr_bunrui1_cd as bunrui1_cd,"); //部門コード
		sql.append("    bumon_ck.code_cd as bunrui1_ck,"); //部門コード2（品種、部門チェック）
		sql.append("    tr.tr_syohin_cd as syohin_cd,"); //商品コード
		sql.append("    tr.tr_tenpo_cd as tenpo_cd,"); //店舗コード
		sql.append("    tenpo_ck.tenpo_cd as tenpo_ck,"); //店舗コード存在チェック
//		sql.append("    tr.tr_yuko_dt as yuko_dt,"); //有効日
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("    tr.ck_yuko_dt as ck_yuko_dt,"); //有効日
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("    tr.tmp_yuko_dt as yuko_dt,"); //有効日
		sql.append("    tr.tr_ten_hachu_st_dt as ten_hachu_st_dt,"); //店舗発注開始日
		sql.append("    tr.tr_ten_hachu_ed_dt as ten_hachu_ed_dt,"); //店舗発注終了日
		sql.append("    tr.tr_gentanka_vl as gentanka_vl,"); //原単価
		sql.append("    tr.tr_baitanka_vl as baitanka_vl,"); //売単価
		sql.append("    tr.tr_max_hachutani_qt as max_hachutani_qt,"); //最大発注数
		sql.append("    tr.tr_eos_kb as eos_kb,"); //EOS区分
		sql.append("    eos_ck.code_cd as eos_ck,"); //EOS区分存在チェック
		sql.append("    tr.tr_siiresaki_cd as siiresaki_cd,"); //仕入先コード
		sql.append("    siiresaki_ck.torihikisaki_cd as siiresaki_ck,"); //仕入先コード存在チェック
		sql.append("    rt.tenbetu_haiso_cd as tenbetu_haiso_cd,"); //店別配送先コード
		sql.append("    tr.tr_buturyu_kb as buturyu_kb,"); //物流区分
		sql.append("    buturyu_ck.code_cd as buturyu_ck,"); //物流区分存在チェック
		sql.append("    rt.ten_zaiko_kb as ten_zaiko_kb,"); //店在庫区分
		sql.append("    tr.tr_yusen_bin_kb as yusen_bin_kb,"); //優先便区分
		sql.append("    tr.tr_bin_1_kb as bin_1_kb,");//①便区分
		sql.append("    tr.tr_bin_2_kb as bin_2_kb,");//②便区分
		sql.append("    tr.tr_plu_send_dt as plu_send_dt,"); //PLU送信日
		sql.append("    tr.by_no as by_no, "); //バイヤーNO
		sql.append("    tr.syohin_gyo_no as syohin_gyo_no,");//商品マスタ登録行№
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		sql.append("    tr.tr_plu_hanei_time as plu_hanei_time,");//PLU反映時間
		sql.append("    tr.tr_hachu_fuka_flg as hachu_fuka_flg,");//発注不可フラグ
		sql.append("    tr.tr_orosi_baitanka_nuki_vl as orosi_baitanka_nuki_vl,");//卸売価単価(税抜)
        /* #1964 Add 2016.09.07 Li.Sheng (E) */
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		sql.append("    tr.tr_baika_haishin_fg as baika_haishin_fg,");//取扱停止
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)
		sql.append("    rt.delete_fg as delete_fg, "); //削除フラグ
		sql.append("    rt.hachu_pattern_1_kb as hachu_pattern_1_kb,");
		sql.append("    rt.sime_time_1_qt as sime_time_1_qt,");
		sql.append("    rt.c_nohin_rtime_1_kb as c_nohin_rtime_1_kb,");
		sql.append("    rt.ten_nohin_rtime_1_kb as ten_nohin_rtime_1_kb,");
		sql.append("    rt.ten_nohin_time_1_kb as ten_nohin_time_1_kb,");
		sql.append("    rt.hachu_pattern_2_kb as hachu_pattern_2_kb,");
		sql.append("    rt.sime_time_2_qt as sime_time_2_qt,");
		sql.append("    rt.c_nohin_rtime_2_kb as c_nohin_rtime_2_kb,");
		sql.append("    rt.ten_nohin_rtime_2_kb as ten_nohin_rtime_2_kb,");
		sql.append("    rt.ten_nohin_time_2_kb as ten_nohin_time_2_kb,");
		sql.append("    rt.bin_3_kb as bin_3_kb,");
		sql.append("    rt.hachu_pattern_3_kb as hachu_pattern_3_kb,");
		sql.append("    rt.sime_time_3_qt as sime_time_3_qt,");
		sql.append("    rt.c_nohin_rtime_3_kb as c_nohin_rtime_3_kb,");
		sql.append("    rt.ten_nohin_rtime_3_kb as ten_nohin_rtime_3_kb,");
		sql.append("    rt.ten_nohin_time_3_kb as ten_nohin_time_3_kb,");
		sql.append("    rt.c_nohin_rtime_kb as c_nohin_rtime_kb,");
		sql.append("    rt.syohin_kb as syohin_kb,");
		sql.append("    rt.sinki_toroku_dt as sinki_toroku_dt, ");
		sql.append("    rt.ten_hachu_st_dt as rtr_ten_hachu_st_dt,");
		sql.append("    rt.ten_hachu_ed_dt as rtr_ten_hachu_ed_dt,");
		sql.append("    rt.gentanka_vl as rtr_gentanka_vl,");
		// #36271 ADD 2025.0708 THONG.LT (S)
		sql.append("    rt.gentanka_nuki_vl as rtr_gentanka_nuki_vl,");
		// #36271 ADD 2025.0708 THONG.LT (E)
		sql.append("    rt.baitanka_vl as rtr_baitanka_vl,");
		sql.append("    rt.max_hachutani_qt as rtr_max_hachutani_qt,");
		sql.append("    rt.eos_kb as rtr_eos_kb,");
		sql.append("    rt.siiresaki_cd as rtr_siiresaki_cd,");
		sql.append("    rt.yusen_bin_kb as rtr_yusen_bin_kb,");
		sql.append("    rt.bin_1_kb as rtr_bin_1_kb,");
		sql.append("    rt.bin_2_kb as rtr_bin_2_kb,");
		sql.append("    rt.buturyu_kb as rtr_buturyu_kb,");
		sql.append("    rt.plu_send_dt as rtr_plu_send_dt,"); //PLU送信日
		// #2803 MSTB011050 2016.11.24 S.Takayama (S)
		sql.append("    rt.plu_hanei_time as rtr_plu_hanei_time,"); //PLU反映時間
		sql.append("    rt.baika_haishin_fg as rtr_baika_haishin_fg,");//取扱停止
		sql.append("    rt.orosi_baitanka_nuki_vl as rtr_orosi_baitanka_nuki_vl,"); //卸売価単価(税抜)
		sql.append("    rt.hachu_fuka_flg as rtr_hachu_fuka_flg,"); //発注不可フラグ
		// #2803 MSTB011050 2016.11.24 S.Takayama (E)
// 2017.01.31 T.han #3784対応（S)
		sql.append("    tr.tr_siire_kahi_kb as siire_kahi_kb,");//仕入可否区分
		sql.append("    tr.tr_henpin_kahi_kb as henpin_kahi_kb,");//返品可否区分
		sql.append("    rt.siire_kahi_kb as rtr_siire_kahi_kb,");//仕入可否区分
		sql.append("    rt.henpin_kahi_kb as rtr_henpin_kahi_kb,");//返品可否区分
// 2017.01.31 T.han #3784対応（E)
		sql.append("    s.syohin_cd as s_syohin_cd, "); //商品コード（商品マスタ）
		sql.append("    s.ten_hachu_st_dt as s_ten_hachu_st_dt,"); //店舗発注開始日（商品マスタ）
		sql.append("    s.ten_hachu_ed_dt as s_ten_hachu_ed_dt "); //店舗発注終了日（商品マスタ）

		sql.append("  FROM (SELECT R.*, ");
		sql.append("               T.BY_NO, ");
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("               R.TR_YUKO_DT AS ck_yuko_dt, ");
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("               COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE	 ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_TENSYOHIN_REIGAI ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE 	 ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND TENPO_CD   = R.TR_TENPO_CD ");
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS REIGAI_YUKO_DT, ");

		sql.append("               (SELECT MAX(TEKIYO_START_DT) ");
		sql.append("                  FROM R_TORIHIKISAKI ");
		sql.append("                 WHERE COMP_CD          = '").append("0000").append("' ");
		sql.append("                   AND CHOAI_KB         = '").append("1").append("' ");
		sql.append("                   AND TORIHIKISAKI_CD  = R.TR_SIIRESAKI_CD ");
		sql.append("                   AND TORIKESHI_FG     = '").append("0").append("' ");
		sql.append("                   AND TEKIYO_START_DT <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS TORIHIKISAKI_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_TENSYOHIN_REIGAI R ");
		sql.append("            ON R.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND R.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND R.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND R.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND R.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON 	 ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_TENSYOHIN_REIGAI RT ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RT.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RT.TENPO_CD   = TR.TR_TENPO_CD ");
		sql.append("   AND RT.YUKO_DT    = TR.REIGAI_YUKO_DT ");
		sql.append("   AND RT.DELETE_FG  = '").append(DEL_FG).append("' ");

		//仕入先
		sql.append("    LEFT JOIN ");
		sql.append("         R_TORIHIKISAKI SIIRESAKI_CK");
		sql.append("      ON SIIRESAKI_CK.COMP_CD            = '").append("0000").append("' ");
		sql.append("     AND SIIRESAKI_CK.CHOAI_KB           = '").append("1").append("' ");
		sql.append("     AND SIIRESAKI_CK.TORIHIKISAKI_CD    = TR.TR_SIIRESAKI_CD ");
		sql.append("     AND SIIRESAKI_CK.TEKIYO_START_DT    = TR.TORIHIKISAKI_YUKO_DT ");
		sql.append("     AND SIIRESAKI_CK.DELETE_FG          = '").append(DEL_FG).append("' ");
		sql.append("     AND SIIRESAKI_CK.TORIHIKI_TEISHI_KB = '").append("0").append("' ");
		sql.append("     AND SIIRESAKI_CK.TORIKESHI_FG       = '").append("0").append("' ");

		// 分類１
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf bumon_ck ");
		sql.append("    on bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   and bumon_ck.code_cd       = trim(tr.tr_bunrui1_cd) ");
		sql.append("   and bumon_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 店舗
		sql.append("  LEFT JOIN ");
		sql.append("       r_tenpo tenpo_ck ");
		sql.append("    on tenpo_ck.tenpo_cd   = tr.tr_tenpo_cd ");
		sql.append("   and tenpo_ck.tenpo_kb   in (").append(useTenpoKb).append(") ");
		sql.append("   and coalesce(tenpo_ck.heiten_dt, '99999999') >= '").append(MasterDT).append("' ");
		sql.append("   and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");

		// EOS区分
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf eos_ck ");
		sql.append("    on eos_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
		sql.append("   and eos_ck.code_cd       = tr.tr_eos_kb ");
		sql.append("   and eos_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 物流区分
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf buturyu_ck ");
		sql.append("    on buturyu_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DISTRIBUTION_DIVISION, userLocal)).append("' ");
		sql.append("   and buturyu_ck.code_cd       = TRIM(tr.tr_buturyu_kb) ");
		sql.append("   and buturyu_ck.delete_fg     = '").append(DEL_FG).append("'");

		//①便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN1_CK ");
		sql.append("    ON BIN1_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN1_CK.CODE_CD       = TR.TR_BIN_1_KB ");
		sql.append("   AND BIN1_CK.DELETE_FG     = '").append(DEL_FG).append("'");

		//②便区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF BIN2_CK ");
		sql.append("    ON BIN2_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND BIN2_CK.CODE_CD       = TR.TR_BIN_2_KB ");
		sql.append("   AND BIN2_CK.DELETE_FG     = '").append(DEL_FG).append("'");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");										//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");								//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");										//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");										//受付SEQ №

		return sql.toString();
	}

	/**
	 * 削除データ取得SQL生成
	 * @throws
	 */
	public String getDeleteSelSQL() {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append("    TR.TORIKOMI_DT         AS TORIKOMI_DT,"); 					//取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU AS EXCEL_FILE_SYUBETSU,"); 			//EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO         AS UKETSUKE_NO,"); 					//受付NO
		sql.append("    TR.UKETSUKE_SEQ        AS UKETSUKE_SEQ,"); 					//受付SEQNO
		sql.append("    TR.SYUSEI_KB           AS SYUSEI_KB,"); 					//修正区分
		sql.append("    TR.TR_BUNRUI1_CD       AS BUNRUI1_CD,"); 					//部門コード
		sql.append("    BUMON_CK.CODE_CD       AS BUNRUI1_CK,"); 					//部門コード2（品種、部門チェック）
		sql.append("    TR.TR_SYOHIN_CD        AS SYOHIN_CD,"); 					//商品コード
		sql.append("    TR.TR_TENPO_CD         AS TENPO_CD,");						//店舗コード
		sql.append("    TENPO_CK.TENPO_CD      AS TENPO_CK,");						//店舗コード存在チェック
//		sql.append("    TR.TR_YUKO_DT          AS YUKO_DT,"); 						//有効日
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("    TR.ck_yuko_dt          AS ck_yuko_dt,"); 						//有効日
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("    TR.TMP_YUKO_DT         AS YUKO_DT,"); 						//有効日
		sql.append("    TR.BY_NO AS BY_NO, "); 										//バイヤーNO
		sql.append("    TR.SYOHIN_GYO_NO       AS SYOHIN_GYO_NO,");					//商品マスタ登録行№
		sql.append("    S.SYOHIN_CD            AS S_SYOHIN_CD, ");	 				//商品コード（商品マスタ）
		sql.append("    S.YUKO_DT              AS S_YUKO_DT, ");	 				//有効日　　（商品マスタ）
		sql.append("    S.DELETE_FG            AS S_DELETE_FG, ");	 				//削除フラグ（商品マスタ）
		sql.append("    RT.SYOHIN_CD           AS REIGAI_SYOHIN_CD  "); 			//商品コード（店別商品例外マスタ）

		sql.append("  FROM (SELECT R.*, ");
		sql.append("               T.BY_NO, ");
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("               R.TR_YUKO_DT AS ck_yuko_dt, ");
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("               COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_TENSYOHIN_REIGAI ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND TENPO_CD   = R.TR_TENPO_CD ");
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS REIGAI_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_TENSYOHIN_REIGAI R ");
		sql.append("            ON R.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND R.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND R.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND R.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND R.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.DELETE.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
//		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_TENSYOHIN_REIGAI RT ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RT.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON 	 ");
		sql.append("   	 RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RT.TENPO_CD   = TR.TR_TENPO_CD ");
		sql.append("   AND RT.YUKO_DT    = TR.REIGAI_YUKO_DT ");
		sql.append("   AND RT.DELETE_FG  = '").append(DEL_FG).append("' ");

		// 分類１
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf bumon_ck ");
		sql.append("    on bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   and bumon_ck.code_cd       = trim(tr.tr_bunrui1_cd) ");
		sql.append("   and bumon_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 店舗
		sql.append("  LEFT JOIN ");
		sql.append("       r_tenpo tenpo_ck ");
		sql.append("    on tenpo_ck.tenpo_cd   = tr.tr_tenpo_cd ");
		sql.append("   and tenpo_ck.tenpo_kb   in (").append(useTenpoKb).append(") ");
		sql.append("   and coalesce(tenpo_ck.heiten_dt, '99999999') >= '").append(MasterDT).append("' ");
		sql.append("   and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");										//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");								//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");										//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");										//受付SEQ №

		return sql.toString();
	}

	/**
	 * 予約取消データ取得SQL生成
	 * @throws
	 */
	public String getCancelSelSQL() {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append("    TR.TORIKOMI_DT         AS TORIKOMI_DT,"); 					//取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU AS EXCEL_FILE_SYUBETSU,"); 			//EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO         AS UKETSUKE_NO,"); 					//受付NO
		sql.append("    TR.UKETSUKE_SEQ        AS UKETSUKE_SEQ,"); 					//受付SEQNO
		sql.append("    TR.SYUSEI_KB           AS SYUSEI_KB,"); 					//修正区分
		sql.append("    TR.TR_BUNRUI1_CD       AS BUNRUI1_CD,"); 					//部門コード
		sql.append("    BUMON_CK.CODE_CD       AS BUNRUI1_CK,"); 					//部門コード2（品種、部門チェック）
		sql.append("    TR.TR_SYOHIN_CD        AS SYOHIN_CD,"); 					//商品コード
		sql.append("    TR.TR_TENPO_CD         AS TENPO_CD,");						//店舗コード
		sql.append("    TENPO_CK.TENPO_CD      AS TENPO_CK,");						//店舗コード存在チェック
//		sql.append("    TR.TR_YUKO_DT          AS YUKO_DT,"); 						//有効日
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("    TR.ck_yuko_dt          AS ck_yuko_dt,"); 						//有効日
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("    TR.TMP_YUKO_DT         AS YUKO_DT,"); 						//有効日
		sql.append("    TR.BY_NO AS BY_NO, "); 										//バイヤーNO
		sql.append("    TR.SYOHIN_GYO_NO       AS SYOHIN_GYO_NO,");					//商品マスタ登録行№
		sql.append("    S.SYOHIN_CD            AS S_SYOHIN_CD, ");	 				//商品コード（商品マスタ）
		sql.append("    RT.SYOHIN_CD           AS REIGAI_SYOHIN_CD  "); 			//商品コード（店別商品例外マスタ）

		sql.append("  FROM (SELECT R.*, ");
		sql.append("               T.BY_NO, ");
		// 2016.11.16 M.Akagi #2256 (S)
		sql.append("               R.TR_YUKO_DT AS ck_yuko_dt, ");
		// 2016.11.16 M.Akagi #2256 (E)
		sql.append("               COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT, ");

		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_TENSYOHIN_REIGAI ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = R.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = R.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   SYOHIN_CD  = R.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND TENPO_CD   = R.TR_TENPO_CD ");
		sql.append("                   AND YUKO_DT   <= COALESCE(R.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS REIGAI_YUKO_DT ");

		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_TENSYOHIN_REIGAI R ");
		sql.append("            ON R.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND R.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND R.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND R.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND R.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.CANCEL.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_TENSYOHIN_REIGAI RT ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON RT.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 RT.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND RT.TENPO_CD   = TR.TR_TENPO_CD ");
		sql.append("   AND RT.YUKO_DT    = TR.REIGAI_YUKO_DT ");
		sql.append("   AND RT.DELETE_FG  = '").append(DEL_FG).append("' ");

		// 分類１
		sql.append("  LEFT JOIN ");
		sql.append("       r_namectf bumon_ck ");
		sql.append("    on bumon_ck.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sql.append("   and bumon_ck.code_cd       = trim(tr.tr_bunrui1_cd) ");
		sql.append("   and bumon_ck.delete_fg     = '").append(DEL_FG).append("'");

		// 店舗
		sql.append("  LEFT JOIN ");
		sql.append("       r_tenpo tenpo_ck ");
		sql.append("    on tenpo_ck.tenpo_cd   = tr.tr_tenpo_cd ");
		sql.append("   and tenpo_ck.tenpo_kb   in (").append(useTenpoKb).append(") ");
		sql.append("   and coalesce(tenpo_ck.heiten_dt, '99999999') >= '").append(MasterDT).append("' ");
		sql.append("   and tenpo_ck.delete_fg = '").append(DEL_FG).append("'");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");										//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");								//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");										//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");										//受付SEQ №

		return sql.toString();
	}

	/**
	 * 店別商品例外マスタデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiInsSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		//部門コード
		sql1.append("bunrui1_cd,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("syohin_cd,");
		sql2.append(" ?,");

		//店舗コード
		sql1.append("tenpo_cd,");
		sql2.append(" ?,");

		//有効日
		sql1.append("yuko_dt,");
		sql2.append(" ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt,");
		sql2.append(" ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt,");
		sql2.append(" ?,");

		//原価単価
		sql1.append("gentanka_vl,");
		sql2.append(" ?,");

		//売価単価
		sql1.append("baitanka_vl,");
		sql2.append(" ?,");

		//最大発注単位
		sql1.append("max_hachutani_qt,");
		sql2.append(" ?,");

		//EOS区分
		sql1.append("eos_kb,");
		sql2.append(" ?,");

		//仕入先コード
		sql1.append("siiresaki_cd,");
		sql2.append(" ?,");

//		↓↓2006.10.05 H.Yamamoto カスタマイズ修正↓↓
		//店別配送先コード
		sql1.append("tenbetu_haiso_cd,");
		sql2.append(" NULL,");
//		↑↑2006.10.05 H.Yamamoto カスタマイズ修正↑↑

		//①便区分
		sql1.append("bin_1_kb,");
		sql2.append(" ?,");

//		↓↓2006.10.05 H.Yamamoto カスタマイズ修正↓↓
		//①発注パターン区分
		sql1.append("hachu_pattern_1_kb,");
		sql2.append(" NULL,");

		//①締め時間
		sql1.append("sime_time_1_qt,");
		sql2.append(" NULL,");

		//①センター納品リードタイム
		sql1.append("c_nohin_rtime_1_kb,");
		sql2.append(" NULL,");

		//①店納品リードタイム
		sql1.append("ten_nohin_rtime_1_kb,");
		sql2.append(" NULL,");

		//①店納品時間帯
		sql1.append("ten_nohin_time_1_kb,");
		sql2.append(" NULL,");
//		↑↑2006.10.05 H.Yamamoto カスタマイズ修正↑↑

		//②便区分
		sql1.append("bin_2_kb,");
		sql2.append(" ?,");

//		↓↓2006.10.05 H.Yamamoto カスタマイズ修正↓↓
		//②発注パターン区分
		sql1.append("hachu_pattern_2_kb,");
		sql2.append(" NULL,");

		//②締め時間
		sql1.append("sime_time_2_qt,");
		sql2.append(" NULL,");

		//②センター納品リードタイム
		sql1.append("c_nohin_rtime_2_kb,");
		sql2.append(" NULL,");

		//②店納品リードタイム
		sql1.append("ten_nohin_rtime_2_kb,");
		sql2.append(" NULL,");

		//②店納品時間帯
		sql1.append("ten_nohin_time_2_kb,");
		sql2.append(" NULL,");

		//③便区分
		sql1.append("bin_3_kb,");
		sql2.append(" NULL,");

		//③発注パターン区分
		sql1.append("hachu_pattern_3_kb,");
		sql2.append(" NULL,");

		//③締め時間
		sql1.append("sime_time_3_qt,");
		sql2.append(" NULL,");

		//③センター納品リードタイム
		sql1.append("c_nohin_rtime_3_kb,");
		sql2.append(" NULL,");

		//③店納品リードタイム
		sql1.append("ten_nohin_rtime_3_kb,");
		sql2.append(" NULL,");

		//③店納品時間帯
		sql1.append("ten_nohin_time_3_kb,");
		sql2.append(" NULL,");

		//センター納品リードタイム
		sql1.append("c_nohin_rtime_kb,");
		sql2.append(" NULL,");

		//商品区分
		sql1.append("syohin_kb,");
		sql2.append(" NULL,");

		//物流区分
		sql1.append("buturyu_kb,");
//		↓↓2007.07.03 oohashi 物流区分対応
//		sql2.append(" NULL,");
		sql2.append(" ?,");
//		↑↑2007.07.03 oohashi 物流区分対応

		//店在庫区分
		sql1.append("ten_zaiko_kb,");
		sql2.append(" NULL,");
//		↑↑2006.10.05 H.Yamamoto カスタマイズ修正↑↑

		//便優先区分
		sql1.append("yusen_bin_kb,");
		sql2.append(" ?,");

		//PLU送信日
		sql1.append("PLU_SEND_DT,");
		sql2.append(" ?,");

		//新規登録日
		sql1.append("sinki_toroku_dt,");
		sql2.append("'").append(MasterDT).append("',");

		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		sql1.append("gentanka_nuki_vl,");
		sql2.append(" ?,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

		//変更日
		sql1.append("henko_dt,");
		sql2.append("'").append(MasterDT).append("',");

		//作成年月日
		sql1.append("insert_ts,");
		sql2.append("     '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//作成者ID
		sql1.append("insert_user_id,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("update_ts,");
		sql2.append("     '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//更新者ID
		sql1.append("update_user_id,");
		sql2.append(" ?,");

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS,");
		sql2.append("     '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID,");
		sql2.append("'" + BATCH_ID + "',");

        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		sql1.append("emg_flag,");
		sql2.append(" ?,");
		sql1.append("plu_hanei_time,");
		sql2.append(" ?,");
		sql1.append("hachu_fuka_flg,");
		sql2.append(" ?,");
		sql1.append("orosi_baitanka_nuki_vl,");
		sql2.append(" ?,");
        /* #1964 Add 2016.09.07 Li.Sheng (E) */

		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		sql1.append("BAIKA_HAISHIN_FG,");
		sql2.append(" ?,");
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		sql1.append("SIIRE_KAHI_KB,");
		sql2.append(" ?,");
		//返品可否区分
		sql1.append("HENPIN_KAHI_KB,");
		sql2.append(" ?,");
// 2017.01.31 T.han #3784対応（E)

		//削除フラグ
		sql1.append("delete_fg");
		sql2.append(" '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		sql.append("insert into r_tensyohin_reigai ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ新規登録SQL
	 * @throws Exception
	 */
	private void setPrepareReigaiInsSQL(PreparedStatement pstmt, ResultSet rs, String syohin_cd) throws SQLException {

		int idx = 0;

		String str = "";
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		String pluSendDt="";
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)
		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		//商品コード
		idx++;
//		=== BEGIN === Modify by ryo 2006/8/2
		pstmt.setString(idx, syohin_cd);

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

		//有効日
		idx++;
//		===BEGIN=== Modify by ryo 2006/8/3
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		if (rs.getString("yuko_dt") == null || rs.getString("yuko_dt").trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}
//		===END=== Modify by ryo 2006/8/3

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
			str = rs.getString("rtr_ten_hachu_st_dt");
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
			str = rs.getString("rtr_ten_hachu_ed_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//原価単価
		idx++;
		//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
//2016/08/04 VINX s.sakamoto #1900対応 (S)
//		String zeiKb = null;
//		int zei_kb = 0;
//		BigDecimal tax_rt = null ;
//		String str_tax_rt = null ;
		String siireZeiKb = null;
		int siire_Zei_kb = 0;
		BigDecimal siire_tax_rt = null ;
		String str_siire_tax_rt = null ;
//2016/08/04 VINX s.sakamoto #1900対応 (E)
		// #4234 MSTB011050 2017.03.06 S.Takayama (S)
		//ResultSet result = dataBase.executeQuery(ReigaiSql.sqlCalculateTax(syohin_cd));
		ResultSet result = dataBase.executeQuery(ReigaiSql.sqlCalculateTax(syohin_cd,rs.getString("yuko_dt")));
		// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		if (result.next()) {
//2016/08/04 VINX s.sakamoto #1900対応 (S)
//			zeiKb = result.getString("ZEI_KB");
//			if ("1".equals(zeiKb)) {
//				zei_kb = 2;
//			}else {
//				zei_kb = Integer.parseInt(zeiKb);
//			}
//			str_tax_rt = result.getString("TAX_RT");
//			if (isNotBlank(str_tax_rt)) {
//				tax_rt = new BigDecimal(str_tax_rt.trim());
//			}else {
//				tax_rt = null;
//			}
			siireZeiKb = result.getString("SIIRE_ZEI_KB");
			if ("1".equals(siireZeiKb)) {
				siire_Zei_kb = 2;
			}else {
				siire_Zei_kb = Integer.parseInt(siireZeiKb);
			}
			str_siire_tax_rt = result.getString("SIIRE_TAX_RATE");
			if (isNotBlank(str_siire_tax_rt)) {
				siire_tax_rt = new BigDecimal(str_siire_tax_rt.trim());
			}else {
				siire_tax_rt = null;
			}
//2016/08/04 VINX s.sakamoto #1900対応 (E)
		}
		// #5488 Add 2017.06.27 DAU.TQP (S)
		dataBase.closeResultSet(result);
		// #5488 Add 2017.06.27 DAU.TQP (E)

		//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
		// #6605 Mod 2022.06.29 DINH.TP (S)
			//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
//			BigDecimal gentanka_vl = new BigDecimal(str.trim());
//			try {
////2016/08/04 VINX s.sakamoto #1900対応 (S)
////				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
////						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
////						MoneyUtil.getFractionCostUnitMode());
////				if ("1".equals(zeiKb)) {
//				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//						MoneyUtil.getFractionCostUnitLen(), siire_Zei_kb, siire_tax_rt,
//						MoneyUtil.getFractionCostUnitMode());
//				if ("1".equals(siireZeiKb)) {
////2016/08/04 VINX s.sakamoto #1900対応 (E)
//					str = ctu.getTaxIn().toString();
//				}else {
//					str = ctu.getTaxOut().toString();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				try {
					CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
							siire_Zei_kb, siire_tax_rt, MoneyUtil.getFractionCostUnitMode());
					if ("1".equals(siireZeiKb)) {
						str = ctu.getTaxIn().toString();
					} else {
						str = ctu.getTaxOut().toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		// #6605 Mod 2022.06.29 DINH.TP (E)
		} else {
			str = rs.getString("rtr_gentanka_vl");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//売価単価(税込)
		idx++;
		str = rs.getString("baitanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0026] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0026] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0027] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0027] Update to parse double for tanka (E)
			}
		} else {
			str = rs.getString("rtr_baitanka_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0028] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0028] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0029] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0029] Update to parse double for tanka (E)
			}
		}

		//最大発注単位
		idx++;
		str = rs.getString("max_hachutani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rtr_max_hachutani_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
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
			str = rs.getString("rtr_eos_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
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
			str = rs.getString("rtr_siiresaki_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}


		//①便区分
		idx++;
		str = rs.getString("bin_1_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_bin_1_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//②便区分
		idx++;
		str = rs.getString("bin_2_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_bin_2_kb");
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
			str = rs.getString("rtr_buturyu_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//便優先区分
		idx++;
		str = rs.getString("yusen_bin_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_yusen_bin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//PLU送信日
		idx++;
		str = rs.getString("plu_send_dt");
		// 2016/11/29 T.Arimoto #2803対応（S)
		if (!isNotBlank(str)) {
			// TR_TENSYOHIN_REIGAIにて空欄の場合、前の世代を取得
			str = rs.getString("rtr_plu_send_dt");
		}
		// 2016/11/29 T.Arimoto #2803対応（E)
		if (isNotBlank(str)) {
//			if (!str.trim().equals(deleteString) && DateDiff.getDiffDays(rs.getString("yuko_dt"), str) >= 0) {
			if (!str.trim().equals(deleteString) && str.compareTo(rs.getString("yuko_dt")) >= 0) {
				pstmt.setString(idx, str);
				// #2803 MSTB011050 2016.11.24 S.Takayama (S)
				pluSendDt = str;
				// #2803 MSTB011050 2016.11.24 S.Takayama (E)
			} else {
				pstmt.setString(idx, rs.getString("yuko_dt"));
				// #2803 MSTB011050 2016.11.24 S.Takayama (S)
				pluSendDt = rs.getString("yuko_dt");
				// #2803 MSTB011050 2016.11.24 S.Takayama (E)
			}
		} else {
//			str = rs.getString("rtr_plu_send_dt");
//			if (isNotBlank(str)) {
//				pstmt.setString(idx, str);
//			} else {
//				pstmt.setNull(idx, Types.VARCHAR);
//			}
			pstmt.setString(idx, rs.getString("yuko_dt"));
			// #2803 MSTB011050 2016.11.24 S.Takayama (S)
			pluSendDt = rs.getString("yuko_dt");
			// #2803 MSTB011050 2016.11.24 S.Takayama (E)
		}

		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		idx++;
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			// #36271 MOD 2025.07.08 THONG.LT (S)
			//str = rs.getString("rtr_gentanka_vl");
			str = rs.getString("rtr_gentanka_nuki_vl");
			// #36271 MOD 2025.07.08 THONG.LT (E)
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

		//作成者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		//緊急配信フラグ
		idx++;
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		//pstmt.setString(idx, "0");
		// PLU反映時間取得
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (str.trim().equals(deleteString)) {
				str = "";
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (!isNotBlank(str) || !pluSendDt.equals(rs.getString("rtr_plu_send_dt")) ) {
				str = "";
			}
		}
		if(pluSendDt.equals(MasterDT)){
			pstmt.setString(idx, "1");
		}else if(isNotBlank(str)){
			// PLU反映時間が設定されている場合
			pstmt.setString(idx, "1");
		}else{
			pstmt.setString(idx, "0");
		}
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

		// PLU反映時間
		idx++;
		// 2016/11/29 T.Arimoto #2803対応（S)
//		str = rs.getString("plu_hanei_time");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (isNotBlank(str) && pluSendDt.equals(rs.getString("rtr_plu_send_dt")) ) {
				// PLU反映日が前の世代を引き継いでいれば、PLU反映時間も引き継ぐ
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)


		//発注不可フラグ
		idx++;
		str = rs.getString("hachu_fuka_flg");
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_hachu_fuka_flg");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
// 2017.01.31 T.han #3784対応（S)
//				pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
// 2017.01.31 T.han #3784対応（E)
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)

		//卸売価単価(税抜)
		idx++;
		str = rs.getString("orosi_baitanka_nuki_vl");
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rtr_orosi_baitanka_nuki_vl");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)

        /* #1964 Add 2016.09.07 Li.Sheng (E) */
		
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		//取扱停止
		idx++;
		str = rs.getString("baika_haishin_fg");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		} else {
			str = rs.getString("rtr_baika_haishin_fg");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		idx++;
		str = rs.getString("siire_kahi_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_siire_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}

		//返品可否区分
		idx++;
		str = rs.getString("henpin_kahi_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_henpin_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}
// 2017.01.31 T.han #3784対応（E)

	}

	/**
	 * 店別商品例外マスタデータ更新用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt = ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt = ?,");

		//原価単価
		sql1.append("gentanka_vl = ?,");

		//売価単価
		sql1.append("baitanka_vl = ?,");

		//最大発注単位
		sql1.append("max_hachutani_qt = ?,");

		//EOS区分
		sql1.append("eos_kb = ?,");

		//仕入先コード
		sql1.append("siiresaki_cd = ?,");

		//①便区分
		sql1.append("bin_1_kb = ?,");

		//②便区分
		sql1.append("bin_2_kb = ?,");

//		↓↓2007.07.03 oohashi 物流区分対応
		//物流区分
		sql1.append("buturyu_kb = ?,");
//		↑↑2007.07.03 oohashi 物流区分対応

		//便優先区分
		sql1.append("yusen_bin_kb = ?,");

		//PLU送信日
		sql1.append("plu_send_dt = ?,");

		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		sql1.append("gentanka_nuki_vl = ?,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

		//変更日
		sql1.append("henko_dt = '").append(MasterDT).append("',");

		//更新年月日
		sql1.append("update_ts = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//更新者D
		sql1.append("update_user_id = ?, ");

        /* #1964 Add 2016.09.07 Li.Sheng (S) */

		//緊急配信フラグ
		sql1.append("emg_flag = ?, ");

		//PLU反映時間
		sql1.append("plu_hanei_time = ?, ");

		//発注不可フラグ
		sql1.append("hachu_fuka_flg = ?, ");

		//卸売価単価(税抜)
		sql1.append("orosi_baitanka_nuki_vl = ?, ");

        /* #1964 Add 2016.09.07 Li.Sheng (E) */

		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		sql1.append("BAIKA_HAISHIN_FG = ?, ");
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		sql1.append("siire_kahi_kb = ?, ");
		//返品可否区分
		sql1.append("henpin_kahi_kb = ?, ");
// 2017.01.31 T.han #3784対応（E)

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS = '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID = '" + BATCH_ID + "' ");

		sql.append("update r_tensyohin_reigai ");
		sql.append("set ");
		sql.append(sql1.toString());
		sql.append("where");
		sql.append(" bunrui1_cd = ? and");
		sql.append(" syohin_cd = ? and");
		sql.append(" tenpo_cd = ? and");
		sql.append(" yuko_dt = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ更新SQL
	 * @throws Exception
	 */
	private void setPrepareReigaiUpSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		int idx = 0;

		String str = "";

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
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_ten_hachu_st_dt");
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
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_ten_hachu_ed_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//原価単価
		idx++;
		//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
//2016/08/04 VINX s.sakamoto #1900対応 (S)
//		String zeiKb = null;
//		int zei_kb = 0;
//		BigDecimal tax_rt = null ;
//		String str_tax_rt = null ;
		String siireZeiKb = null;
		int siire_Zei_kb = 0;
		BigDecimal siire_tax_rt = null ;
		String str_siire_tax_rt = null ;
//2016/08/04 VINX s.sakamoto #1900対応 (E)
		// #4234 MSTB011050 2017.03.06 S.Takayama (S)
		//ResultSet result = dataBase.executeQuery(ReigaiSql.sqlCalculateTax(rs.getString("syohin_cd")));
		ResultSet result = dataBase.executeQuery(ReigaiSql.sqlCalculateTax(rs.getString("syohin_cd"),rs.getString("yuko_dt")));
		// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		if (result.next()) {
//2016/08/04 VINX s.sakamoto #1900対応 (S)
//			zeiKb = result.getString("ZEI_KB");
//			if ("1".equals(zeiKb)) {
//				zei_kb = 2;
//			}else {
//				zei_kb = Integer.parseInt(zeiKb);
//			}
//			str_tax_rt = result.getString("TAX_RT");
//			if (isNotBlank(str_tax_rt)) {
//				tax_rt = new BigDecimal(str_tax_rt.trim());
//			}else {
//				tax_rt = null;
//			}
			siireZeiKb = result.getString("SIIRE_ZEI_KB");
			if ("1".equals(siireZeiKb)) {
				siire_Zei_kb = 2;
			}else {
				siire_Zei_kb = Integer.parseInt(siireZeiKb);
			}
			str_siire_tax_rt = result.getString("SIIRE_TAX_RATE");
			if (isNotBlank(str_siire_tax_rt)) {
				siire_tax_rt = new BigDecimal(str_siire_tax_rt.trim());
			}else {
				siire_tax_rt = null;
			}
//2016/08/04 VINX s.sakamoto #1900対応 (E)
		}
        // #5488 Add 2017.06.27 DAU.TQP (S)
        dataBase.closeResultSet(result);
        // #5488 Add 2017.06.27 DAU.TQP (E)
		//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
		// #6605 Mod 2022.06.29 DINH.TP (S)
			//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
//			BigDecimal gentanka_vl = new BigDecimal(str.trim());
//			try {
////2016/08/04 VINX s.sakamoto #1900対応 (S)
////				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
////						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
////						MoneyUtil.getFractionCostUnitMode());
////				if ("1".equals(zeiKb)) {
//				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//						MoneyUtil.getFractionCostUnitLen(), siire_Zei_kb, siire_tax_rt,
//						MoneyUtil.getFractionCostUnitMode());
//				if ("1".equals(siireZeiKb)) {
////2016/08/04 VINX s.sakamoto #1900対応 (E)
//					str = ctu.getTaxIn().toString();
//				}else {
//					str = ctu.getTaxOut().toString();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
//			if (!str.trim().equals(deleteString)) {
//				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//			} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				try {
					CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
							siire_Zei_kb, siire_tax_rt, MoneyUtil.getFractionCostUnitMode());
					if ("1".equals(siireZeiKb)) {
						str = ctu.getTaxIn().toString();
					} else {
						str = ctu.getTaxOut().toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		// #6605 Mod 2022.06.29 DINH.TP (E)
		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
			str = rs.getString("rtr_gentanka_vl");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//売価単価(税込)
		idx++;
		str = rs.getString("baitanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				//2014.07.29 [CUS0022] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0022] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0023] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0023] Update to parse double for tanka (E)
			}
		} else {
//			pstmt.setNull(idx, Types.BIGINT);
			str = rs.getString("rtr_baitanka_vl");
			if (isNotBlank(str)) {
				//2014.07.29 [CUS0024] Update to parse double for tanka (S)
				//pstmt.setLong(idx, Long.parseLong(str.trim()));
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
				//2014.07.29 [CUS0024] Update to parse double for tanka (E)
			} else {
				//2014.07.29 [CUS0025] Update to parse double for tanka (S)
				//pstmt.setNull(idx, Types.BIGINT);
				pstmt.setNull(idx, Types.DOUBLE);
				//2014.07.29 [CUS0025] Update to parse double for tanka (E)
			}
		}

		//最大発注単位
		idx++;
		str = rs.getString("max_hachutani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
			str = rs.getString("rtr_max_hachutani_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
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
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_eos_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
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
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_siiresaki_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//①便区分
		idx++;
		str = rs.getString("bin_1_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_bin_1_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//②便区分
		idx++;
		str = rs.getString("bin_2_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_bin_2_kb");
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
			str = rs.getString("rtr_buturyu_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//便優先区分
		idx++;
		str = rs.getString("yusen_bin_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_yusen_bin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//PLU送信日
		// #2803 MSTB011050 2016.11.24 S.Takayama (S)
		String PluSendDt= "";
		// #2803 MSTB011050 2016.11.24 S.Takayama (E)
		idx++;
		str = rs.getString("plu_send_dt");
		if (!isNotBlank(str)) {
			str = rs.getString("rtr_plu_send_dt");
		}
		if (isNotBlank(str)) {
			//					if (!str.trim().equals(deleteString) && DateDiff.getDiffDays(rs.getString("yuko_dt"), str) >= 0) {
			if (!str.trim().equals(deleteString) && str.compareTo(rs.getString("yuko_dt")) >= 0) {
				pstmt.setString(idx, str);
				// #2803 MSTB011050 2016.11.29 S.Takayama (S)
				PluSendDt = str;
				// #2803 MSTB011050 2016.11.29 S.Takayama (E)
			} else {
				pstmt.setString(idx, rs.getString("yuko_dt"));
				// #2803 MSTB011050 2016.11.29 S.Takayama (S)
				PluSendDt = rs.getString("yuko_dt");
				// #2803 MSTB011050 2016.11.29 S.Takayama (E)
			}
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
			// #2803 MSTB011050 2016.11.29 S.Takayama (S)
			PluSendDt = rs.getString("yuko_dt");
			// #2803 MSTB011050 2016.11.29 S.Takayama (E)
		}
				
		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		idx++;
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			// #36271 MOD 2025.07.08 THONG.LT (S)
			//str = rs.getString("rtr_gentanka_vl");
			str = rs.getString("rtr_gentanka_nuki_vl");
			// #36271 MOD 2025.07.08 THONG.LT (E)
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

		//更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		
		//緊急配信フラグ
		idx++;
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		//pstmt.setString(idx, "0");
		// PLU反映時間の取得
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (str.trim().equals(deleteString)) {
				str = "";
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (!isNotBlank(str)) {
				str = "";
			}
		}
		if ( PluSendDt.equals(MasterDT) || isNotBlank(str) ) {
			pstmt.setString(idx, "1");
		} else {
			pstmt.setString(idx, "0");
		}
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

		// PLU反映時間
		idx++;
		// #2803 MSTB011050 2016.11.24 S.Takayama (S)
		/*str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if(isNotBlank(str)){
			pstmt.setString(idx, str);
			}
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}*/
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// #2803 MSTB011050 2016.11.24 S.Takayama (E)

		//発注不可フラグ
		idx++;
		str = rs.getString("hachu_fuka_flg");
		pstmt.setString(idx, str);
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_hachu_fuka_flg");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)

		//卸売価単価(税抜)
		idx++;
		str = rs.getString("orosi_baitanka_nuki_vl");
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rtr_orosi_baitanka_nuki_vl");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)

        /* #1964 Add 2016.09.07 Li.Sheng (E) */
		
		// #2803 MSTB011050 2016.11.22 S.Takayama (S)
		//取扱停止
		idx++;
		str = rs.getString("baika_haishin_fg");
		if(isNotBlank(str)){
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		} else {
			str = rs.getString("rtr_baika_haishin_fg");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}
		// #2803 MSTB011050 2016.11.22 S.Takayama (E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		idx++;
		str = rs.getString("siire_kahi_kb");
		pstmt.setString(idx, str);
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_siire_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//返品可否区分
		idx++;
		str = rs.getString("henpin_kahi_kb");
		pstmt.setString(idx, str);
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_henpin_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
// 2017.01.31 T.han #3784対応（E)

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("bunrui1_cd"));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

		//有効日
		idx++;
//		===BEGIN=== Modify by ryo 2006/8/4
//		pstmt.setString(idx, rs.getString("yuko_dt"));
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}
//		===END=== Modify by ryo 2006/8/4

	}

	/**
	 * 店別商品例外マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiDelSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("    delete from r_tensyohin_reigai ");
		sql.append("            where   ");
		sql.append("                bunrui1_cd = ?");
		sql.append("                and syohin_cd = ?");
		sql.append("                and tenpo_cd = ?");
		sql.append("                and yuko_dt = ?");
		sql.append("                and delete_fg = '"+mst000801_DelFlagDictionary.IRU.getCode()+"'");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ新規登録SQL
	 * @throws Exception
	 */
	private void setPrepareReigaiDeleteSQL(PreparedStatement pstmt, ResultSet rs) throws SQLException {

		int idx = 0;

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString("bumon_cd"));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

		//有効日
		idx++;
		pstmt.setString(idx, rs.getString("yuko_dt"));
	}

	/**
	 * 商品コードを取得用のSQL
	 * @throws
	 */
	public String getSyohinCdSQL(String tableNm, ResultSet rs) throws SQLException {
		StringBuffer sql = new StringBuffer();
//		=== BEGIN === Add by ryo 2006/8/2
		ResultSet rs1 = null;
		String dbsyohin_cd = "";
//		=== END === Add by ryo 2006/8/2
		sql.append(" select");
		sql.append("   str.tr_syohin_cd");
		sql.append(" from");
		sql.append("   " + tableNm + " str");
		sql.append(" where ");
		sql.append("   str.torikomi_dt = '" + rs.getString("torikomi_dt") + "' and");
		sql.append("   str.excel_file_syubetsu = '" + rs.getString("excel_file_syubetsu") + "' and");
		sql.append("   str.uketsuke_no = '" + rs.getString("uketsuke_no") + "' and");
//		=== BEGIN === Add by ryo 2006/8/1
//		↓↓2006.10.05 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("   str.uketsuke_seq = " + rs.getString("uketsuke_seq") + " and");
//		↑↑2006.10.05 H.Yamamoto カスタマイズ修正↑↑
//		=== END === Add by ryo 2006/8/1
		sql.append("   str.sakusei_gyo_no = " + rs.getString("syohin_gyo_no"));
//		=== BEGIN === Add by ryo 2006/8/2
//		return sql.toString();
		rs1 = dataBase.executeQuery(sql.toString());
		if (rs1.next()) {
			dbsyohin_cd = rs1.getString("tr_syohin_cd");
		}
//		↓↓2006.09.19 H.Yamamoto カスタマイズ修正↓↓
		dataBase.closeResultSet(rs1);
//		↑↑2006.09.19 H.Yamamoto カスタマイズ修正↑↑
		return dbsyohin_cd;
//		=== END === Add by ryo 2006/8/2
	}



//========================================================================
//		ＭＭ用
//========================================================================

	/**
	 * 処理対象となるデータを取得するSQL生成
	 * @throws Exception
	 */
	private String getSyoriTaisyoSQL() throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT ");
		sql.append("  INTO WK_MB38_TAISYOGAI_REIGAI ");
		sql.append("SELECT R.TORIKOMI_DT, ");
		sql.append("       R.EXCEL_FILE_SYUBETSU, ");
		sql.append("       R.UKETSUKE_NO, ");
		sql.append("       R.UKETSUKE_SEQ, ");
		sql.append("       R.TR_BUNRUI1_CD, ");
		sql.append("       R.TR_SYOHIN_CD, ");
		sql.append("       R.TR_TENPO_CD, ");
		sql.append("       '" + SYUBETU + "' AS SHEET_SYUBETSU ");
		sql.append("  FROM TR_TENSYOHIN_REIGAI R ");
		sql.append("  LEFT JOIN ");
		sql.append("       (SELECT TR_BUNRUI1_CD, ");
		sql.append("               TR_SYOHIN_CD, ");
		sql.append("               TR_TENPO_CD, ");
		sql.append("               TO_NUMBER(SUBSTR(KEY, 15, 5)) AS UKETSUKE_NO, ");
		sql.append("               TO_NUMBER(SUBSTR(KEY, 24, 6)) AS UKETSUKE_SEQ ");
		sql.append("          FROM (SELECT TR_BUNRUI1_CD, ");
		sql.append("                       TR_SYOHIN_CD, ");
		sql.append("                       TR_TENPO_CD, ");
		sql.append("                       MAX(UPDATE_TS || TO_CHAR(UKETSUKE_NO, 'FM00000') || TO_CHAR(COALESCE(SAKUSEI_GYO_NO, 0), 'FM0000') || TO_CHAR(UKETSUKE_SEQ, 'FM000000')) AS KEY ");
		sql.append("                  FROM TR_TENSYOHIN_REIGAI ");
		sql.append("                 WHERE MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' ");
		sql.append("                 GROUP BY ");
		sql.append("                       TR_BUNRUI1_CD, ");
		sql.append("                       TR_SYOHIN_CD, ");
		sql.append("                       TR_TENPO_CD ");
		sql.append("               ) W ");
		sql.append("       ) T ");
		sql.append("    ON R.TR_BUNRUI1_CD = T.TR_BUNRUI1_CD ");
		sql.append("   AND R.TR_SYOHIN_CD  = T.TR_SYOHIN_CD ");
		sql.append("   AND R.TR_TENPO_CD   = T.TR_TENPO_CD ");
		sql.append("   AND R.UKETSUKE_NO   = T.UKETSUKE_NO ");
		sql.append("   AND R.UKETSUKE_SEQ  = T.UKETSUKE_SEQ ");
		sql.append(" WHERE R.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "' ");
		sql.append("   AND NVL(LENGTH(TRIM(R.TR_SYOHIN_CD)), 0) > 3 ");
		sql.append("   AND T.TR_BUNRUI1_CD IS NULL ");

		return sql.toString();
	}

	private String getUpdKosyaYusenTaisyogai(String strTableName, String strSheetSyubetsu) throws Exception {

		StringBuffer sql = new StringBuffer();;

		//  2013.05.06 [MSTM00004] VIEW UPDATE対応(S)
		sql.append("UPDATE ");
		sql.append("	" + strTableName + " TR ");
		sql.append("SET  MST_MAINTE_FG  = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "' ");
		sql.append("    ,UPDATE_USER_ID = '" + batchID + "' ");
		sql.append("    ,UPDATE_TS      = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		sql.append("WHERE ");
		sql.append("    TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
		sql.append("AND	EXISTS ");
		sql.append("		( ");
		sql.append("		SELECT '' ");
		sql.append("		FROM  ");
		sql.append("			WK_MB38_TAISYOGAI_REIGAI WK 									");
		sql.append("		WHERE WK.SHEET_SYUBETSU       = '" + strSheetSyubetsu + "' 	");
		sql.append("        AND   TR.TORIKOMI_DT          = WK.TORIKOMI_DT 				");
		sql.append("        AND   TR.EXCEL_FILE_SYUBETSU  = WK.EXCEL_FILE_SYUBETSU 		");
		sql.append("        AND   TR.UKETSUKE_NO          = WK.UKETSUKE_NO 				");
		sql.append("        AND   TR.UKETSUKE_SEQ         = WK.UKETSUKE_SEQ 			");
		sql.append("		) ");

//		sql.append("UPDATE ( ");
//		sql.append("    SELECT /*+ BYPASS_UJVC */ ");
//		sql.append("  	       TR.MST_MAINTE_FG, ");
//		sql.append("  	       TR.UPDATE_TS, ");
//		sql.append("  	       TR.UPDATE_USER_ID ");
//		sql.append("      FROM WK_MB38_TAISYOGAI_REIGAI WK ");
//		sql.append("     INNER JOIN ");
//		sql.append("           " + strTableName + " TR ");
//		sql.append("        ON TR.TORIKOMI_DT          = WK.TORIKOMI_DT ");
//		sql.append("       AND TR.EXCEL_FILE_SYUBETSU  = WK.EXCEL_FILE_SYUBETSU ");
//		sql.append("       AND TR.UKETSUKE_NO          = WK.UKETSUKE_NO ");
//		sql.append("       AND TR.UKETSUKE_SEQ         = WK.UKETSUKE_SEQ ");
//		sql.append("       AND TR.MST_MAINTE_FG = '" + mst006801_MstMainteFgDictionary.SYORITYU.getCode() + "'");
//		sql.append("     WHERE WK.SHEET_SYUBETSU = '" + strSheetSyubetsu + "' ");
//		sql.append(")");
//		sql.append("SET MST_MAINTE_FG  = '" + mst006801_MstMainteFgDictionary.KEIKOKU.getCode() + "',");
//		sql.append("    UPDATE_TS      = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
//		sql.append("    UPDATE_USER_ID = '" + batchID + "' ");

		//  2013.05.06 [MSTM00004] VIEW UPDATE対応(E)

		return sql.toString();
	}

	/**
	 * 後者優先による登録対象外のデータのメッセージ作成SQL
	 * @throws
	 */
	public String getKosyaYusenMessageSQL(Map map)
		throws SQLException
	{
		StringBuffer str = new StringBuffer();

		str.append("insert into ");
		str.append(" tr_message ");
		str.append("(select ");
		str.append("   top.torikomi_dt,"); 				//取込日
		str.append("   top.excel_file_syubetsu,"); 		//EXCELファイル種別
		str.append("   top.uketsuke_no,"); 				//受付ファイルNo.
		str.append("   nvl(tts.uketsuke_seq,0),"); 		//受付SEQNo.
		str.append("   sheet_syubetsu, "); 				//シート種別
		str.append("   '0003',"); 						//結果メッセージコード
		str.append("'" + map.get("0003") + "',"); 		//結果メッセージ
		str.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no, ");
		str.append("   '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		str.append("   top.by_no ");
		str.append(" from ");
		str.append("   tr_toroku_syonin top right outer join ");
		str.append(" WK_MB38_TAISYOGAI_REIGAI tts");
		str.append("   on top.torikomi_dt = tts.torikomi_dt ");
		str.append("   and top.excel_file_syubetsu = tts.excel_file_syubetsu ");
		str.append("   and top.uketsuke_no = tts.uketsuke_no ");
		str.append(")");

		return str.toString();
	}

	/**
	 * バックアップ用SQL生成
	 * @throws Exception
	 */
	private String getBackupSQL(ResultSet rs, String syoriDt) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT ");
		sql.append("  INTO DEL_R_TENSYOHIN_REIGAI ");
		sql.append("SELECT '" + syoriDt + "', ");
		sql.append("       '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");
		sql.append("       '" + rs.getString("BY_NO") + "', ");
		sql.append("       T.* ");
		sql.append("  FROM R_TENSYOHIN_REIGAI T ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "' ");
		sql.append("   AND TENPO_CD   = '" + rs.getString("TENPO_CD") + "' ");
		sql.append("   AND YUKO_DT    = '" + rs.getString("YUKO_DT") + "' ");

		return sql.toString();
	}


	/**
	 * 削除用SQL生成
	 * @throws Exception
	 */
	private String getDeleteSQL(ResultSet rs) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE ");
		sql.append("  FROM R_TENSYOHIN_REIGAI ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "' ");
		sql.append("   AND TENPO_CD   = '" + rs.getString("TENPO_CD") + "' ");
		sql.append("   AND YUKO_DT    = '" + rs.getString("YUKO_DT") + "' ");

		return sql.toString();
	}


	private boolean isNotBlank(String val) {
		// #6605 MOD DINH.TP 29.06.2022 (S)
		// #6605 MOD DINH.TP 16.06.2022 (S)
//		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		// #6605 MOD DINH.TP 16.06.2022 (E)
		// #6605 MOD DINH.TP 29.06.2022 (E)
		return false;
	}


	private ResultSet executeQueryRetry(String sql, String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt)  throws SQLException {
		ResultSet res = null;

		for (int i = 0;i < retryCnt;i++) {
			try {
				res = dataBase.executeQuery(sql);
				break;
			} catch (SQLException sqle) {

				if (sqle.getErrorCode() == this.DEAD_LOCK_ERROR) {

					if (i + 1 >= retryCnt) {
						batchLog.getLog().info(BATCH_ID, BATCH_NA, "チェック処理が最大リトライ回数に達したため停止します。");
						throw sqle;
					}

					batchLog.getLog().info(BATCH_ID, BATCH_NA, "チェック処理に失敗したため" + waitTime / 1000 + "秒待機後にリトライします。" + (i + 1) + "回目");
					try{
						Thread.sleep(waitTime);
					} catch (Exception e){}
				} else {
					throw sqle;
				}
			}
		}

		return res;
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

}

