package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.command.mst38A105_GiftSyohinCheck;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006501_BySyoninFgDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst006801_MstMainteFgDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:ギフト商品マスタEXCEL登録処理</p>
 * <p>説明:ギフト商品マスタEXCEL登録処理を行います。</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @Version 1.02  (2021.12.15) HOAI.TTT #6409 対応
 */

public class MB380C03_CreateGiftSyohin {

	private MasterDataBase dataBase = null;

	//batchID
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	private final String BATCH_ID = "MB38-0C-03";
	private final String BATCH_NA = "ギフト商品マスタ生成";

	//PreparedStatement
	private PreparedStatement GiftSyohinInsert = null; 		//ギフト商品マスタInsert用
	private PreparedStatement GiftSyohinUpdate = null; 		//ギフト商品マスタUpdate用
	private PreparedStatement GiftSyohinDelInsert = null; 		//ギフト商品マスタInsert用（削除レコード）
	private PreparedStatement GiftSyohinDelUp = null; 			//ギフト商品マスタUpdate用（削除レコード）
	private PreparedStatement SyohinUpInsert = null; 			//商品マスタInsert用
	private PreparedStatement KeiryokiUpInsert = null; 		//計量器マスタInsert用
	private PreparedStatement HachuUpInsert = null; 			//商品発注納品基準日マスタInsert用
	private PreparedStatement SyohinHenkoDtUp = null; 			//商品マスタの変更日更新用

	private PreparedStatement MessageInsert = null; 			//処理結果メッセージInsert用

	private final String deleteString = "*"; 					//削除を表す文字

	private final String TABLE_NA = "TR_GIFT_SYOHIN"; 			//対象テーブル名

	private final String SYUBETU = mst006401_DataKindDictionary.GIFT.getCode(); //データ種別

	private MB380000_CommonSql comSql = null;							//共通SQLクラス
	private MB380007_CommonSyohinSql comSyohin = null;
	private MB380009_CommonGiftSyohinCheck Check = null; 				//項目チェック用クラス
	private mst38A105_GiftSyohinCheck GiftSyohinCheck = null; 			//共通チェック用クラス

	private int ICOUNT = 0; //新規登録数
	private int UCOUNT = 0; //更新数
	private int DCOUNT = 0; //削除数
	private int CCOUNT = 0; //予約取消数
	private int ECOUNT = 0; //エラー数

//	private static final int DB2_00001 = -803; //DB2用一意制約エラーコード
	private static final int DB2_00001 = 1; //ORACLE用一意制約エラーコード
	private final int DEAD_LOCK_ERROR  = -913;

	private String MasterDT = null;
	private String DefaultYukoDt = null;



	private static final String DEL_FG_INAI = mst000801_DelFlagDictionary.INAI.getCode();

	private int waitTime = 0;
	private int retryCnt = 0;

	/**
	 * コンストラクタ
	 */
	public MB380C03_CreateGiftSyohin() {
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

		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

			int iCount = 0;
			String sql = "";

			// システムコントロール情報取得
			getSystemControl();

			comSql = new MB380000_CommonSql();
			comSyohin = new MB380007_CommonSyohinSql(MasterDT, BATCH_ID, mst000601_GyoshuKbDictionary.GRO.getCode()); //商品マスタ生成共通SQLクラス
			// #6409 Mod 2021.12.15 HOAI.TTT (S)
			//Map map = MB380001_CommonMessage.getMsg();
			Map map = MB380C01_CommonMessage.getMsg();
			// #6409 Mod 2021.12.15 HOAI.TTT (E)
			//データ補正処理する
			writeLog(Level.INFO_INT, "有効日補正処理開始");
			sql = comSql.getYukoDtFollowSQL(TABLE_NA, SYUBETU, MasterDT);
			iCount = dataBase.executeUpdate(sql);
			writeLog(Level.INFO_INT, iCount + "件の有効日を補正しました。");

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
			GiftSyohinInsert 	= getPreparedGiftSyohinSQL(dataBase);
			GiftSyohinUpdate 	= getPreparedGiftSyohinUpSQL(dataBase);
			GiftSyohinDelUp 	= comSyohin.getPreparedSyohinSubDelUpSQL(dataBase, "R_GIFT_SYOHIN");
			GiftSyohinDelInsert	= comSyohin.getPreparedGiftSyohinInsSQL(dataBase, BATCH_ID);
			SyohinUpInsert 		= comSyohin.getPreparedSyohinInsSQL(dataBase, BATCH_ID);
			KeiryokiUpInsert  	= comSyohin.getPreparedKeiryokiInsSQL(dataBase, BATCH_ID);
			HachuUpInsert  		= comSyohin.getPreparedSyohinHachuNohinkijunbiInsSQL(dataBase, BATCH_ID);
			MessageInsert 		= comSql.getPreparedMessageSQL(dataBase);
			SyohinHenkoDtUp     = comSyohin.getPreparedSyohinHenkoDtUpSQL(dataBase);

			// 共通チェック
			Check = new MB380009_CommonGiftSyohinCheck(MessageInsert, map, dataBase);
			GiftSyohinCheck = new mst38A105_GiftSyohinCheck(dataBase);

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

			// 2008/12 ログ強化対応
			outputException("E02", se);

			throw se;
		} catch (Exception e) {
			dataBase.rollback();
			writeError(e);

			// 2008/12 ログ強化対応
			outputException("E02", e);

			throw e;
		} finally {
			dataBase.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}


	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		//オンライン日付取得
		MasterDT = RMSTDATEUtil.getMstDateDt();
		DefaultYukoDt = DateChanger.addDate(MasterDT, 1);
	}


	/**
	 * @param rs
	 */
	private void dataProcess(ResultSet rs) throws SQLException, Exception {
		String JyotaiFlg = null;
		String torikomiDT = null; 				//取込日
		String excelSyubetsu = null; 			//EXCELファイル種別
		String uketsukeNO = null; 				//受付No.

		String JyotaiFlg_old = null;
		String torikomiDT_old = null; 			//前のレコードの取込日
		String excelSyubetsu_old = null; 		//前のレコードのEXCELファイル種別
		String uketsukeNO_old = null; 			//前のレコードの受付No.

		String updateJyotaiSql = null;

		int rsCnt = 0;	// ループカウンタ
		while (rs.next()) {
			rsCnt++;

			// 2008/12 ログ強化対応
			batchLog.getLog().info("---------------------データ処理(" + rsCnt + ")番目-----");
			outputRs("処理開始時", rs);

			JyotaiFlg = null;

			torikomiDT    = rs.getString("torikomi_dt"); 				//取込日
			excelSyubetsu = rs.getString("excel_file_syubetsu"); 		//EXCELファイル種別
			uketsukeNO    = rs.getString("uketsuke_no"); 				//受付No.

			if(dataCheck(rs)){
//				JyotaiFlg = "8";		// 正常
			}else{
				JyotaiFlg = "2";		// エラー
			}

			// 登録処理状態更新
			if(uketsukeNO_old != null && !uketsukeNO_old.equals(uketsukeNO) && JyotaiFlg_old != null){
				updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT_old, excelSyubetsu_old, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
				dataBase.execute(updateJyotaiSql);
				JyotaiFlg_old = null;
			}

			if(JyotaiFlg != null){
				JyotaiFlg_old = JyotaiFlg;
			}
			uketsukeNO_old = uketsukeNO;
			torikomiDT_old = torikomiDT;
			excelSyubetsu_old = excelSyubetsu;

			// 2008/12 ログ強化対応
			outputRs("処理完了後", rs);
		}

		if(uketsukeNO_old != null && JyotaiFlg_old != null){
			updateJyotaiSql = comSql.getUpdateJyotaiFlgSql(torikomiDT, excelSyubetsu, uketsukeNO_old, JyotaiFlg_old, BATCH_ID);
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
		int cnt = 0;

		// 2008/12 ログ強化対応
		outputRs("全体共通項目チェック", rs);

		//修正区分
		String syusei_kb = rs.getString("syusei_kb");

		//バイヤーNO
		String by_no =  rs.getString("by_no");

		//チェックデジットの結果
		String  checkDegit = null;

		key[0] = rs.getString("torikomi_dt"); 				//取込日
		key[1] = rs.getString("excel_file_syubetsu"); 		//EXCELファイル種別
		key[2] = rs.getString("uketsuke_no"); 				//受付No.
		key[3] = rs.getString("uketsuke_seq"); 				//受付SEQNo.
		key[4] = SYUBETU; 									//シート種別
		key[5] = by_no	;									//バイヤーNO

		Check.setKey(key);

		GiftSyohinCheck.setChkSyohin(false);

		// 共通チェックを行う
		if (GiftSyohinCheck.process(rs, SYUBETU) == null){

			checkFg =  false;
		}

		// 2008/12 ログ強化対応
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

					//新規登録
					// 2008/12 ログ強化対応
					outputRs("新規登録", rs);

					// ギフト商品マスタ物理削除
					if (Check.getDeleteFg()) {
						cnt = dataBase.executeUpdate(getDeleteSQL(rs, "R_GIFT_SYOHIN"));
					}

					// ギフト商品マスタ登録
					setPrepareGiftSyohinSQL(GiftSyohinInsert, rs, insertFg);
					cnt = GiftSyohinInsert.executeUpdate();

					// 関連マスタ
					if (Check.getSyohinInsertFg() == true) {

						// 商品マスタ
						comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, rs.getString("S_PLU_SEND_DT"), mst000801_DelFlagDictionary.INAI.getCode());
						cnt = SyohinUpInsert.executeUpdate();

						// 計量器マスタ
						comSyohin.setPreparedGiftSyohinInsSQL(KeiryokiUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						cnt = KeiryokiUpInsert.executeUpdate();

						// 商品発注納品基準日マスタ
						comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						cnt = HachuUpInsert.executeUpdate();

					} else {

						// 商品マスタの変更日を更新する
						comSyohin.setPreparedSyohinHenkoDtUpSQL(SyohinHenkoDtUp, rs);
						cnt = SyohinHenkoDtUp.executeUpdate();
					}

				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {

					//更新
					// 2008/12 ログ強化対応
					outputRs("更新処理", rs);

					if (insertFg) {

						// 2008/12 ログ強化対応
						String msg = rs.getString("bunrui1_cd") + "," + checkDegit + "," + rs.getString("yuko_dt");
						outputMessage("商品マスタ登録(2)", msg);

						// ギフト商品マスタ物理削除
						if (Check.getDeleteFg()) {
							cnt = dataBase.executeUpdate(getDeleteSQL(rs, "R_GIFT_SYOHIN"));
						}

						// ギフト商品マスタ登録
						setPrepareGiftSyohinSQL(GiftSyohinInsert, rs, insertFg);
						cnt = GiftSyohinInsert.executeUpdate();

						// 関連マスタ
						if (Check.getSyohinInsertFg() == true) {

							// 商品マスタ
							comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, rs.getString("S_PLU_SEND_DT"), mst000801_DelFlagDictionary.INAI.getCode());
							cnt = SyohinUpInsert.executeUpdate();

							// 計量器マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(KeiryokiUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							cnt = KeiryokiUpInsert.executeUpdate();

							// 商品発注納品基準日マスタ
							comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							cnt = HachuUpInsert.executeUpdate();

						} else {

							// 商品マスタの変更日を更新する
							comSyohin.setPreparedSyohinHenkoDtUpSQL(SyohinHenkoDtUp, rs);
							cnt = SyohinHenkoDtUp.executeUpdate();
						}

					} else {

						// ギフト商品マスタ
						setPrepareGiftSyohinSQL(GiftSyohinUpdate, rs, insertFg);
						GiftSyohinUpdate.executeUpdate();

						// 商品マスタの変更日を更新する
						comSyohin.setPreparedSyohinHenkoDtUpSQL(SyohinHenkoDtUp, rs);
						cnt = SyohinHenkoDtUp.executeUpdate();
					}


				} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {

					//削除
					// 2008/12 ログ強化対応
					outputRs("商品マスタ削除", rs);
					String msg = insertFg + "," + rs.getString("bunrui1_cd") + "," + rs.getString("syohin_cd") + "," + rs.getString("yuko_dt");
					outputMessage("商品マスタ登録", msg);

					if (Check.getSyoriFg()) {

						if (insertFg) {

							// ギフト商品マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinDelInsert, rs, mst000801_DelFlagDictionary.IRU.getCode());
							GiftSyohinDelInsert.executeUpdate();

							// 商品マスタ
							comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, null, mst000801_DelFlagDictionary.INAI.getCode());
							SyohinUpInsert.executeUpdate();

							// 計量器マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(KeiryokiUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							KeiryokiUpInsert.executeUpdate();

							// 商品発注納品基準日マスタ
							comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							HachuUpInsert.executeUpdate();

						} else {

							// ギフト商品マスタ
							comSyohin.setPreparedSyohinSubDelUpSQL(GiftSyohinDelUp, rs);
							GiftSyohinDelUp.executeUpdate();

							// 商品マスタの変更日を更新する
							comSyohin.setPreparedSyohinHenkoDtUpSQL(SyohinHenkoDtUp, rs);
							cnt = SyohinHenkoDtUp.executeUpdate();
						}
					}
				}

			} catch (SQLException e) {
				//ロールバック
				dataBase.rollback();
				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, "！！ロールバック！！");
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


		// 2008/12 ログ強化対応
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
	public String getInsertSelSQL() {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT  ");
		sql.append("    TR.BY_NO, ");												// バイヤー№
		sql.append("    TR.TORIKOMI_DT, ");											// 取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU, ");									// EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO, ");											// 受付ファイル№
		sql.append("    TR.UKETSUKE_SEQ, ");										// 受付SEQ №
		sql.append("    TR.TR_BUNRUI1_CD        AS BUNRUI1_CD, ");					// TR_分類１コード
		sql.append("    TR.TR_SYOHIN_CD         AS SYOHIN_CD, ");					// TR_商品コード
//		sql.append("    TR.TR_YUKO_DT           AS YUKO_DT, ");						// TR_有効日
		sql.append("    TR.TMP_YUKO_DT          AS YUKO_DT, ");
		sql.append("    TR.TR_TSUBAN            AS TSUBAN, ");						// TR_通番
		sql.append("    TR.TR_GIFT_CD           AS GIFT_CD, ");						// TR_ギフトコード
		sql.append("    TR.TR_WARIBIKI_QT       AS WARIBIKI_QT, ");					// TR_割引率
		sql.append("    TR.TR_POINT_QT          AS POINT_QT, ");					// TR_ポイント
		sql.append("    TR.TR_SYOHIN_COMMENT_TX AS SYOHIN_COMMENT_TX, ");			// TR_商品コメント
		sql.append("    TR.TR_MEISAI_TX         AS MEISAI_TX, ");					// TR_容量内容明細
		sql.append("    TR.TR_KAZEI_KB          AS KAZEI_KB, ");					// TR_課税区分
		sql.append("    TR.TR_KEIYU_KB          AS KEIYU_KB, ");					// TR_経由区分
		sql.append("    TR.TR_DENPYO_KB         AS DENPYO_KB, ");					// TR_伝票区分
		sql.append("    TR.TR_SORYO_KB          AS SORYO_KB, ");					// TR_送料区分
		sql.append("    TR.TR_GIFT_HASSOMOTO_CD AS GIFT_HASSOMOTO_CD, ");			// TR_ギフト発送元コード
		sql.append("    TR.TR_HAISO_KB          AS HAISO_KB, ");					// TR_配送区分
		sql.append("    TR.TR_JUSHIN_START_DT   AS JUSHIN_START_DT, ");				// TR_受信可能開始日
		sql.append("    TR.TR_JUSHIN_END_DT     AS JUSHIN_END_DT, ");				// TR_受信可能終了日
		sql.append("    TR.TR_HAISO_START_DT    AS HAISO_START_DT, ");				// TR_配送可能開始日
		sql.append("    TR.TR_HAISO_END_DT      AS HAISO_END_DT, ");				// TR_配送可能終了日
		sql.append("    TR.SYUSEI_KB, ");											// 修正区分
		sql.append("    TR.SYOHIN_GYO_NO, ");										// 商品マスタ登録行№
//		sql.append("    TR.SAKUSEI_GYO_NO, ");										// 作成元行
		sql.append("    TR.MST_MAINTE_FG, ");										// マスタメンテフラグ
		sql.append("    TR.ALARM_MAKE_FG, ");										// アラーム作成フラグ
//		sql.append("    TR.INSERT_TS, ");											// 作成年月日
//		sql.append("    TR.INSERT_USER_ID, ");										// 作成者ID
//		sql.append("    TR.UPDATE_TS, ");											// 更新年月日
//		sql.append("    TR.UPDATE_USER_ID, ");										// 更新者ID
//		sql.append("    TR.DELETE_FG, ");											// 削除フラグ

		sql.append("    R.BUNRUI1_CD            AS RS_BUNRUI1_CD, ");				// 分類１コード
		sql.append("    R.SYOHIN_CD             AS RS_SYOHIN_CD, ");				// 商品コード
		sql.append("    R.YUKO_DT               AS RS_YUKO_DT, ");					// 有効日
		sql.append("    R.TSUBAN                AS RS_TSUBAN, ");					// 通番
		sql.append("    R.GIFT_CD               AS RS_GIFT_CD, ");					// ギフトコード
		sql.append("    R.WARIBIKI_QT           AS RS_WARIBIKI_QT, ");				// 割引率
		sql.append("    R.POINT_QT              AS RS_POINT_QT, ");					// ポイント
		sql.append("    R.SYOHIN_COMMENT_TX     AS RS_SYOHIN_COMMENT_TX, ");		// 商品コメント
		sql.append("    R.MEISAI_TX             AS RS_MEISAI_TX, ");				// 容量内容明細
		sql.append("    R.KAZEI_KB              AS RS_KAZEI_KB, ");					// 課税区分
		sql.append("    R.KEIYU_KB              AS RS_KEIYU_KB, ");					// 経由区分
		sql.append("    R.DENPYO_KB             AS RS_DENPYO_KB, ");				// 伝票区分
		sql.append("    R.SORYO_KB              AS RS_SORYO_KB, ");					// 送料区分
		sql.append("    R.GIFT_HASSOMOTO_CD     AS RS_GIFT_HASSOMOTO_CD, ");		// ギフト発送元コード
		sql.append("    R.HAISO_KB              AS RS_HAISO_KB, ");					// 配送区分
		sql.append("    R.JUSHIN_START_DT       AS RS_JUSHIN_START_DT, ");			// 受信可能開始日
		sql.append("    R.JUSHIN_END_DT         AS RS_JUSHIN_END_DT, ");			// 受信可能終了日
		sql.append("    R.HAISO_START_DT        AS RS_HAISO_START_DT, ");			// 配送可能開始日
		sql.append("    R.HAISO_END_DT          AS RS_HAISO_END_DT, ");				// 配送可能終了日

		sql.append("    S.SYOHIN_CD             AS S_SYOHIN_CD, ");					// 商品マスタ存在チェックに使用
		sql.append("    S.YUKO_DT               AS S_YUKO_DT, ");					//
		sql.append("    S.PLU_SEND_DT           AS S_PLU_SEND_DT, ");				//

		sql.append("    KAZEI_CK.CODE_CD        AS KAZEI_CK, "); 					//課税区分
		sql.append("    KEIYU_CK.CODE_CD        AS KEIYU_CK, "); 					//経由区分
		sql.append("    DENPYO_CK.CODE_CD       AS DENPYO_CK, "); 					//伝票区分
		sql.append("    SORYO_CK.CODE_CD        AS SORYO_CK, "); 					//送料区分
		sql.append("    HAISO_CK.CODE_CD        AS HAISO_CK, "); 					//配送区分
		sql.append("    HASSOMOTO_CK.GIFT_HASSOMOTO_CD AS GIFT_HASSOMOTO_CK "); 	//ギフト発送元コード

		sql.append("  FROM (SELECT W.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");
		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = W.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = W.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   SYOHIN_CD  = W.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT ");
		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_GIFT_SYOHIN W ");
		sql.append("            ON W.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND W.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND W.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND W.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND W.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.INSERT.getCode()).append("' ");
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
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_SYOHIN R ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("    	R.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND R.YUKO_DT    = S.YUKO_DT ");
		sql.append("   AND R.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		//課税区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAZEI_CK ");
		sql.append("    ON KAZEI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAZEI_DIVISION, userLocal)).append("' ");
		sql.append("   AND KAZEI_CK.CODE_CD       = TR.TR_KAZEI_KB ");
		sql.append("   AND KAZEI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//経由区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KEIYU_CK ");
		sql.append("    ON KEIYU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KEIYU_DIVISION, userLocal)).append("' ");
		sql.append("   AND KEIYU_CK.CODE_CD       = TR.TR_KEIYU_KB ");
		sql.append("   AND KEIYU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//伝票区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF DENPYO_CK ");
		sql.append("    ON DENPYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DENPYO_DIVISION, userLocal)).append("' ");
		sql.append("   AND DENPYO_CK.CODE_CD       = TR.TR_DENPYO_KB ");
		sql.append("   AND DENPYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//送料区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SORYO_CK ");
		sql.append("    ON SORYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SORYO_DIVISION, userLocal)).append("' ");
		sql.append("   AND SORYO_CK.CODE_CD       = TR.TR_SORYO_KB ");
		sql.append("   AND SORYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//配送区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HAISO_CK ");
		sql.append("    ON HAISO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HAISO_DIVISION, userLocal)).append("' ");
		sql.append("   AND HAISO_CK.CODE_CD       = TR.TR_HAISO_KB ");
		sql.append("   AND HAISO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ギフト配送元コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_HASSOMOTO HASSOMOTO_CK ");
		sql.append("    ON HASSOMOTO_CK.GIFT_HASSOMOTO_CD = TR.TR_GIFT_HASSOMOTO_CD ");
		sql.append("   AND HASSOMOTO_CK.DELETE_FG         = '").append(DEL_FG_INAI).append("'");

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

		sql.append("SELECT  ");
		sql.append("    TR.BY_NO, ");												// バイヤー№
		sql.append("    TR.TORIKOMI_DT, ");											// 取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU, ");									// EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO, ");											// 受付ファイル№
		sql.append("    TR.UKETSUKE_SEQ, ");										// 受付SEQ №
		sql.append("    TR.TR_BUNRUI1_CD        AS BUNRUI1_CD, ");					// TR_分類１コード
		sql.append("    TR.TR_SYOHIN_CD         AS SYOHIN_CD, ");					// TR_商品コード
//		sql.append("    TR.TR_YUKO_DT           AS YUKO_DT, ");						// TR_有効日
		sql.append("    TR.TMP_YUKO_DT          AS YUKO_DT, ");
		sql.append("    TR.TR_TSUBAN            AS TSUBAN, ");						// TR_通番
		sql.append("    TR.TR_GIFT_CD           AS GIFT_CD, ");						// TR_ギフトコード
		sql.append("    TR.TR_WARIBIKI_QT       AS WARIBIKI_QT, ");					// TR_割引率
		sql.append("    TR.TR_POINT_QT          AS POINT_QT, ");					// TR_ポイント
		sql.append("    TR.TR_SYOHIN_COMMENT_TX AS SYOHIN_COMMENT_TX, ");			// TR_商品コメント
		sql.append("    TR.TR_MEISAI_TX         AS MEISAI_TX, ");					// TR_容量内容明細
		sql.append("    TR.TR_KAZEI_KB          AS KAZEI_KB, ");					// TR_課税区分
		sql.append("    TR.TR_KEIYU_KB          AS KEIYU_KB, ");					// TR_経由区分
		sql.append("    TR.TR_DENPYO_KB         AS DENPYO_KB, ");					// TR_伝票区分
		sql.append("    TR.TR_SORYO_KB          AS SORYO_KB, ");					// TR_送料区分
		sql.append("    TR.TR_GIFT_HASSOMOTO_CD AS GIFT_HASSOMOTO_CD, ");			// TR_ギフト発送元コード
		sql.append("    TR.TR_HAISO_KB          AS HAISO_KB, ");					// TR_配送区分
		sql.append("    TR.TR_JUSHIN_START_DT   AS JUSHIN_START_DT, ");				// TR_受信可能開始日
		sql.append("    TR.TR_JUSHIN_END_DT     AS JUSHIN_END_DT, ");				// TR_受信可能終了日
		sql.append("    TR.TR_HAISO_START_DT    AS HAISO_START_DT, ");				// TR_配送可能開始日
		sql.append("    TR.TR_HAISO_END_DT      AS HAISO_END_DT, ");				// TR_配送可能終了日
		sql.append("    TR.SYUSEI_KB, ");											// 修正区分
		sql.append("    TR.SYOHIN_GYO_NO, ");										// 商品マスタ登録行№
//		sql.append("    TR.SAKUSEI_GYO_NO, ");										// 作成元行
		sql.append("    TR.MST_MAINTE_FG, ");										// マスタメンテフラグ
		sql.append("    TR.ALARM_MAKE_FG, ");										// アラーム作成フラグ
//		sql.append("    TR.INSERT_TS, ");											// 作成年月日
//		sql.append("    TR.INSERT_USER_ID, ");										// 作成者ID
//		sql.append("    TR.UPDATE_TS, ");											// 更新年月日
//		sql.append("    TR.UPDATE_USER_ID, ");										// 更新者ID
//		sql.append("    TR.DELETE_FG, ");											// 削除フラグ

		sql.append("    R.BUNRUI1_CD            AS RS_BUNRUI1_CD, ");				// 分類１コード
		sql.append("    R.SYOHIN_CD             AS RS_SYOHIN_CD, ");				// 商品コード
		sql.append("    R.YUKO_DT               AS RS_YUKO_DT, ");					// 有効日
		sql.append("    R.TSUBAN                AS RS_TSUBAN, ");					// 通番
		sql.append("    R.GIFT_CD               AS RS_GIFT_CD, ");					// ギフトコード
		sql.append("    R.WARIBIKI_QT           AS RS_WARIBIKI_QT, ");				// 割引率
		sql.append("    R.POINT_QT              AS RS_POINT_QT, ");					// ポイント
		sql.append("    R.SYOHIN_COMMENT_TX     AS RS_SYOHIN_COMMENT_TX, ");		// 商品コメント
		sql.append("    R.MEISAI_TX             AS RS_MEISAI_TX, ");				// 容量内容明細
		sql.append("    R.KAZEI_KB              AS RS_KAZEI_KB, ");					// 課税区分
		sql.append("    R.KEIYU_KB              AS RS_KEIYU_KB, ");					// 経由区分
		sql.append("    R.DENPYO_KB             AS RS_DENPYO_KB, ");				// 伝票区分
		sql.append("    R.SORYO_KB              AS RS_SORYO_KB, ");					// 送料区分
		sql.append("    R.GIFT_HASSOMOTO_CD     AS RS_GIFT_HASSOMOTO_CD, ");		// ギフト発送元コード
		sql.append("    R.HAISO_KB              AS RS_HAISO_KB, ");					// 配送区分
		sql.append("    R.JUSHIN_START_DT       AS RS_JUSHIN_START_DT, ");			// 受信可能開始日
		sql.append("    R.JUSHIN_END_DT         AS RS_JUSHIN_END_DT, ");			// 受信可能終了日
		sql.append("    R.HAISO_START_DT        AS RS_HAISO_START_DT, ");			// 配送可能開始日
		sql.append("    R.HAISO_END_DT          AS RS_HAISO_END_DT, ");				// 配送可能終了日

		sql.append("    S.SYOHIN_CD             AS S_SYOHIN_CD, ");					// 商品マスタ存在チェックに使用
		sql.append("    S.YUKO_DT               AS S_YUKO_DT, ");					//
		sql.append("    S.PLU_SEND_DT           AS S_PLU_SEND_DT, ");				//

		sql.append("    KAZEI_CK.CODE_CD        AS KAZEI_CK, "); 					//課税区分
		sql.append("    KEIYU_CK.CODE_CD        AS KEIYU_CK, "); 					//経由区分
		sql.append("    DENPYO_CK.CODE_CD       AS DENPYO_CK, "); 					//伝票区分
		sql.append("    SORYO_CK.CODE_CD        AS SORYO_CK, "); 					//送料区分
		sql.append("    HAISO_CK.CODE_CD        AS HAISO_CK, "); 					//配送区分
		sql.append("    HASSOMOTO_CK.GIFT_HASSOMOTO_CD AS GIFT_HASSOMOTO_CK "); 	//ギフト発送元コード

		sql.append("  FROM (SELECT W.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");
		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = W.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = W.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = W.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT ");
		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_GIFT_SYOHIN W ");
		sql.append("            ON W.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND W.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND W.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND W.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND W.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.UPDATE.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_SYOHIN R ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON	 ");
		sql.append("   		 R.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND R.YUKO_DT    = S.YUKO_DT ");
		sql.append("   AND R.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		//課税区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAZEI_CK ");
		sql.append("    ON KAZEI_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAZEI_DIVISION, userLocal)).append("' ");
		sql.append("   AND KAZEI_CK.CODE_CD       = TR.TR_KAZEI_KB ");
		sql.append("   AND KAZEI_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//経由区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KEIYU_CK ");
		sql.append("    ON KEIYU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KEIYU_DIVISION, userLocal)).append("' ");
		sql.append("   AND KEIYU_CK.CODE_CD       = TR.TR_KEIYU_KB ");
		sql.append("   AND KEIYU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//伝票区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF DENPYO_CK ");
		sql.append("    ON DENPYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DENPYO_DIVISION, userLocal)).append("' ");
		sql.append("   AND DENPYO_CK.CODE_CD       = TR.TR_DENPYO_KB ");
		sql.append("   AND DENPYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//送料区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SORYO_CK ");
		sql.append("    ON SORYO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SORYO_DIVISION, userLocal)).append("' ");
		sql.append("   AND SORYO_CK.CODE_CD       = TR.TR_SORYO_KB ");
		sql.append("   AND SORYO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//配送区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HAISO_CK ");
		sql.append("    ON HAISO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HAISO_DIVISION, userLocal)).append("' ");
		sql.append("   AND HAISO_CK.CODE_CD       = TR.TR_HAISO_KB ");
		sql.append("   AND HAISO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//ギフト配送元コード
		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_HASSOMOTO HASSOMOTO_CK ");
		sql.append("    ON HASSOMOTO_CK.GIFT_HASSOMOTO_CD = TR.TR_GIFT_HASSOMOTO_CD ");
		sql.append("   AND HASSOMOTO_CK.DELETE_FG         = '").append(DEL_FG_INAI).append("'");

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
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT  ");
		sql.append("    TR.BY_NO, ");												// バイヤー№
		sql.append("    TR.TORIKOMI_DT, ");											// 取込日
		sql.append("    TR.EXCEL_FILE_SYUBETSU, ");									// EXCELファイル種別
		sql.append("    TR.UKETSUKE_NO, ");											// 受付ファイル№
		sql.append("    TR.UKETSUKE_SEQ, ");										// 受付SEQ №
		sql.append("    TR.TR_BUNRUI1_CD        AS BUNRUI1_CD, ");					// TR_分類１コード
		sql.append("    TR.TR_SYOHIN_CD         AS SYOHIN_CD, ");					// TR_商品コード
//		sql.append("    TR.TR_YUKO_DT           AS YUKO_DT, ");						// TR_有効日
		sql.append("    TR.TMP_YUKO_DT          AS YUKO_DT, ");
		sql.append("    TR.TR_TSUBAN            AS TSUBAN, ");						// TR_通番
		sql.append("    TR.TR_GIFT_CD           AS GIFT_CD, ");						// TR_ギフトコード
		sql.append("    TR.TR_WARIBIKI_QT       AS WARIBIKI_QT, ");					// TR_割引率
		sql.append("    TR.TR_POINT_QT          AS POINT_QT, ");					// TR_ポイント
		sql.append("    TR.TR_SYOHIN_COMMENT_TX AS SYOHIN_COMMENT_TX, ");			// TR_商品コメント
		sql.append("    TR.TR_MEISAI_TX         AS MEISAI_TX, ");					// TR_容量内容明細
		sql.append("    TR.TR_KAZEI_KB          AS KAZEI_KB, ");					// TR_課税区分
		sql.append("    TR.TR_KEIYU_KB          AS KEIYU_KB, ");					// TR_経由区分
		sql.append("    TR.TR_DENPYO_KB         AS DENPYO_KB, ");					// TR_伝票区分
		sql.append("    TR.TR_SORYO_KB          AS SORYO_KB, ");					// TR_送料区分
		sql.append("    TR.TR_GIFT_HASSOMOTO_CD AS GIFT_HASSOMOTO_CD, ");			// TR_ギフト発送元コード
		sql.append("    TR.TR_HAISO_KB          AS HAISO_KB, ");					// TR_配送区分
		sql.append("    TR.TR_JUSHIN_START_DT   AS JUSHIN_START_DT, ");				// TR_受信可能開始日
		sql.append("    TR.TR_JUSHIN_END_DT     AS JUSHIN_END_DT, ");				// TR_受信可能終了日
		sql.append("    TR.TR_HAISO_START_DT    AS HAISO_START_DT, ");				// TR_配送可能開始日
		sql.append("    TR.TR_HAISO_END_DT      AS HAISO_END_DT, ");				// TR_配送可能終了日
		sql.append("    TR.SYUSEI_KB, ");											// 修正区分
		sql.append("    TR.SYOHIN_GYO_NO, ");										// 商品マスタ登録行№
//		sql.append("    TR.SAKUSEI_GYO_NO, ");										// 作成元行
		sql.append("    TR.MST_MAINTE_FG, ");										// マスタメンテフラグ
		sql.append("    TR.ALARM_MAKE_FG, ");										// アラーム作成フラグ
//		sql.append("    TR.INSERT_TS, ");											// 作成年月日
//		sql.append("    TR.INSERT_USER_ID, ");										// 作成者ID
//		sql.append("    TR.UPDATE_TS, ");											// 更新年月日
//		sql.append("    TR.UPDATE_USER_ID, ");										// 更新者ID
//		sql.append("    TR.DELETE_FG, ");											// 削除フラグ

		sql.append("    R.BUNRUI1_CD            AS RS_BUNRUI1_CD, ");				// 分類１コード
		sql.append("    R.SYOHIN_CD             AS RS_SYOHIN_CD, ");				// 商品コード
		sql.append("    R.YUKO_DT               AS RS_YUKO_DT, ");					// 有効日
		sql.append("    R.TSUBAN                AS RS_TSUBAN, ");					// 通番
		sql.append("    R.GIFT_CD               AS RS_GIFT_CD, ");					// ギフトコード
		sql.append("    R.WARIBIKI_QT           AS RS_WARIBIKI_QT, ");				// 割引率
		sql.append("    R.POINT_QT              AS RS_POINT_QT, ");					// ポイント
		sql.append("    R.SYOHIN_COMMENT_TX     AS RS_SYOHIN_COMMENT_TX, ");		// 商品コメント
		sql.append("    R.MEISAI_TX             AS RS_MEISAI_TX, ");				// 容量内容明細
		sql.append("    R.KAZEI_KB              AS RS_KAZEI_KB, ");					// 課税区分
		sql.append("    R.KEIYU_KB              AS RS_KEIYU_KB, ");					// 経由区分
		sql.append("    R.DENPYO_KB             AS RS_DENPYO_KB, ");				// 伝票区分
		sql.append("    R.SORYO_KB              AS RS_SORYO_KB, ");					// 送料区分
		sql.append("    R.GIFT_HASSOMOTO_CD     AS RS_GIFT_HASSOMOTO_CD, ");		// ギフト発送元コード
		sql.append("    R.HAISO_KB              AS RS_HAISO_KB, ");					// 配送区分
		sql.append("    R.JUSHIN_START_DT       AS RS_JUSHIN_START_DT, ");			// 受信可能開始日
		sql.append("    R.JUSHIN_END_DT         AS RS_JUSHIN_END_DT, ");			// 受信可能終了日
		sql.append("    R.HAISO_START_DT        AS RS_HAISO_START_DT, ");			// 配送可能開始日
		sql.append("    R.HAISO_END_DT          AS RS_HAISO_END_DT, ");				// 配送可能終了日

		sql.append("    S.SYOHIN_CD             AS S_SYOHIN_CD, ");					// 商品マスタ存在チェックに使用
		sql.append("    S.YUKO_DT               AS S_YUKO_DT ");					//

		sql.append("  FROM (SELECT W.*, ");
		sql.append("               T.BY_NO, ");
		sql.append("               COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') AS TMP_YUKO_DT, ");
		sql.append("               (SELECT MAX(YUKO_DT)");
		sql.append("                  FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("                 WHERE BUNRUI1_CD = W.TR_BUNRUI1_CD ");
//		sql.append("                   AND SYOHIN_CD  = W.TR_SYOHIN_CD ");
		sql.append("                 WHERE  ");
		sql.append("                   	 SYOHIN_CD  = W.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT ");
		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_GIFT_SYOHIN W ");
		sql.append("            ON W.TORIKOMI_DT         = T.TORIKOMI_DT ");
		sql.append("           AND W.EXCEL_FILE_SYUBETSU = T.EXCEL_FILE_SYUBETSU ");
		sql.append("           AND W.UKETSUKE_NO         = T.UKETSUKE_NO ");
		sql.append("         WHERE T.TOROKU_SYONIN_FG    = '").append(mst006501_BySyoninFgDictionary.SYONIN.getCode()).append("' ");
		sql.append("           AND W.MST_MAINTE_FG       = '").append(mst006801_MstMainteFgDictionary.SYORITYU.getCode()).append("' ");
		sql.append("           AND W.SYUSEI_KB           = '").append(mst006701_SyuseiKbDictionary.DELETE.getCode()).append("' ");
		sql.append("       ) TR ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_SYOHIN S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON S.BUNRUI1_CD = TR.TR_BUNRUI1_CD ");
//		sql.append("   AND S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_SYOHIN R ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		 R.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND R.YUKO_DT    = S.YUKO_DT ");
		sql.append("   AND R.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append(" ORDER BY ");
		sql.append("       TR.TORIKOMI_DT,");										//取込日
		sql.append("       TR.EXCEL_FILE_SYUBETSU,");								//EXCELファイル種別
		sql.append("       TR.UKETSUKE_NO,");										//受付ファイル№
		sql.append("       TR.UKETSUKE_SEQ");										//受付SEQ №

		return sql.toString();
	}

	/**
	 * ギフト商品マスタデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedGiftSyohinSQL(MasterDataBase dataBase) throws SQLException {
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

		//通番
		sql1.append("TSUBAN,");
		sql2.append(" ?,");

		//ギフトコード
		sql1.append("GIFT_CD,");
		sql2.append(" ?,");

		//割引率
		sql1.append("WARIBIKI_QT,");
		sql2.append(" ?,");

		//ポイント
		sql1.append("POINT_QT,");
		sql2.append(" ?,");

		//商品コメント
		sql1.append("SYOHIN_COMMENT_TX,");
		sql2.append(" ?,");

		//容量内容明細
		sql1.append("MEISAI_TX,");
		sql2.append(" ?,");

		//課税区分
		sql1.append("KAZEI_KB,");
		sql2.append(" ?,");

		//経由区分
		sql1.append("KEIYU_KB,");
		sql2.append(" ?,");

		//伝票区分
		sql1.append("DENPYO_KB,");
		sql2.append(" ?,");

		//送料区分
		sql1.append("SORYO_KB,");
		sql2.append(" ?,");

		//ギフト発送元コード
		sql1.append("GIFT_HASSOMOTO_CD,");
		sql2.append(" ?,");

		//配送区分
		sql1.append("HAISO_KB,");
		sql2.append(" ?,");

		//受信可能開始日
		sql1.append("JUSHIN_START_DT,");
		sql2.append(" ?,");

		//受信可能終了日
		sql1.append("JUSHIN_END_DT,");
		sql2.append(" ?,");

		//配送可能開始日
		sql1.append("HAISO_START_DT,");
		sql2.append(" ?,");

		//配送可能終了日
		sql1.append("HAISO_END_DT,");
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

		sql.append("INSERT INTO R_GIFT_SYOHIN ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") VALUES ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * ギフト商品マスタデータ更新用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedGiftSyohinUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();

		//通番
		sql1.append("TSUBAN = ?,");

		//ギフトコード
		sql1.append("GIFT_CD = ?,");

		//割引率
		sql1.append("WARIBIKI_QT = ?,");

		//ポイント
		sql1.append("POINT_QT = ?,");

		//商品コメント
		sql1.append("SYOHIN_COMMENT_TX = ?,");

		//容量内容明細
		sql1.append("MEISAI_TX = ?,");

		//課税区分
		sql1.append("KAZEI_KB = ?,");

		//経由区分
		sql1.append("KEIYU_KB = ?,");

		//伝票区分
		sql1.append("DENPYO_KB = ?,");

		//送料区分
		sql1.append("SORYO_KB = ?,");

		//ギフト発送元コード
		sql1.append("GIFT_HASSOMOTO_CD = ?,");

		//配送区分
		sql1.append("HAISO_KB = ?,");

		//受信可能開始日
		sql1.append("JUSHIN_START_DT = ?,");

		//受信可能終了日
		sql1.append("JUSHIN_END_DT = ?,");

		//配送可能開始日
		sql1.append("HAISO_START_DT = ?,");

		//配送可能終了日
		sql1.append("HAISO_END_DT = ?,");

		//作成年月日
//		sql1.append("INSERT_TS = ?,");

		//作成者社員ID
//		sql1.append("INSERT_USER_ID = ?,");

		//更新年月日
		sql1.append("UPDATE_TS =  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//更新者ID
		sql1.append("UPDATE_USER_ID = ?,");

		//バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "', ");

		//バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID =  '" + BATCH_ID + "', ");

		//削除フラグ
		sql1.append("DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		sql.append("UPDATE R_GIFT_SYOHIN ");
		sql.append("   SET ");
		sql.append(sql1.toString());
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * ギフト商品マスタデータ更新SQL
	 * @throws Exception
	 */
	private void setPrepareGiftSyohinSQL(PreparedStatement pstmt, ResultSet rs, boolean insertFg) throws SQLException {

		int idx = 0;

		String str = "";
		String syuseiKb = rs.getString("syusei_kb");

		if (insertFg) {

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("SYOHIN_CD"));

			//有効日
			idx++;
			// 有効開始日が未入力の場合、管理日付の翌日でセットする
			// 商品マスタに当日（管理日付の翌日）のレコードがある場合の配慮
			if(rs.getString("YUKO_DT")==null || rs.getString("YUKO_DT").trim().equals("")){
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx,startDt);
			}else{
				pstmt.setString(idx, rs.getString("YUKO_DT"));
			}
		}

		//通番
		idx++;
		str = rs.getString("TSUBAN");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TSUBAN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ギフトコード
		idx++;
		str = rs.getString("GIFT_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GIFT_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//割引率
		idx++;
		str = rs.getString("WARIBIKI_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("RS_WARIBIKI_QT");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//ポイント
		idx++;
		str = rs.getString("POINT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_POINT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//商品コメント
		idx++;
		str = rs.getString("SYOHIN_COMMENT_TX");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SYOHIN_COMMENT_TX");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//容量内容明細
		idx++;
		str = rs.getString("MEISAI_TX");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_MEISAI_TX");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//課税区分
		idx++;
		str = rs.getString("KAZEI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KAZEI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//経由区分
		idx++;
		str = rs.getString("KEIYU_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KEIYU_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//伝票区分
		idx++;
		str = rs.getString("DENPYO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_DENPYO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//送料区分
		idx++;
		str = rs.getString("SORYO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SORYO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//ギフト発送元コード
		idx++;
		str = rs.getString("GIFT_HASSOMOTO_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GIFT_HASSOMOTO_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//配送区分
		idx++;
		str = rs.getString("HAISO_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HAISO_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//受信可能開始日
		idx++;
		str = rs.getString("JUSHIN_START_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {

			if (syuseiKb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = rs.getString("YUKO_DT");
			} else {
				str = rs.getString("RS_JUSHIN_START_DT");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//受信可能終了日
		idx++;
		str = rs.getString("JUSHIN_END_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syuseiKb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = "99999999";
			} else {
				str = rs.getString("RS_JUSHIN_END_DT");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//配送可能開始日
		idx++;
		str = rs.getString("HAISO_START_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syuseiKb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = rs.getString("YUKO_DT");
			} else {
				str = rs.getString("RS_HAISO_START_DT");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//配送可能終了日
		idx++;
		str = rs.getString("HAISO_END_DT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			if (syuseiKb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				str = "99999999";
			} else {
				str = rs.getString("RS_HAISO_END_DT");
			}
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//作成年月日

		//作成者ID
		if (insertFg) {
			idx++;
			str = rs.getString("BY_NO").trim();
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//更新年月日

		//更新者ID
		idx++;
		str = rs.getString("BY_NO").trim();
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//バッチ更新年月日
		//バッチ更新者ID


		if (!insertFg) {

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("SYOHIN_CD"));

			//有効日
			idx++;
			// 有効開始日が未入力の場合、管理日付の翌日でセットする
			// 商品マスタに当日（管理日付の翌日）のレコードがある場合の配慮
			if(rs.getString("YUKO_DT")==null || rs.getString("YUKO_DT").trim().equals("")){
				String startDt = DateChanger.addDate(MasterDT, 1);
				pstmt.setString(idx,startDt);
			}else{
				pstmt.setString(idx, rs.getString("YUKO_DT"));
			}
		}

	}


	private boolean isNotBlank(String val) {
//		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		return false;
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

	// 2008/12 ログ強化対応
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
