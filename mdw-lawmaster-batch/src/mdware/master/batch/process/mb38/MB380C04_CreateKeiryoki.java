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
import mdware.master.common.command.mst38A106_KeiryokiCheck;
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
 * <p>タイトル:商品マスタEXCEL登録処理（グロサリー・バラエティ）</p>
 * <p>説明:商品マスタEXCEL登録処理（グロサリー・バラエティ）を行います。</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @Version 1.02  (2021.12.15) HOAI.TTT #6409 対応
 */

public class MB380C04_CreateKeiryoki {

	private MasterDataBase dataBase = null;

	//batchID
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	private final String BATCH_ID = "MB38-0C-04";
	private final String BATCH_NA = "計量器マスタ生成";

	//PreparedStatement
	private PreparedStatement KeiryokiInsert = null; 			//計量器マスタInsert用
	private PreparedStatement KeiryokiUpdate = null; 			//計量器マスタUpdate用
	private PreparedStatement KeiryokiDelInsert = null; 		//計量器マスタInsert用（削除レコード）
	private PreparedStatement KeiryokiDelUp = null; 			//計量器マスタUpdate用（削除レコード）
	private PreparedStatement SyohinUpInsert = null; 			//商品マスタInsert用
	private PreparedStatement GiftSyohinUpInsert = null; 		//ギフト商品マスタInsert用
	private PreparedStatement HachuUpInsert = null; 			//商品発注納品基準日マスタInsert用
	private PreparedStatement SyohinHenkoDtUp = null; 			//商品マスタの変更日更新用

	private PreparedStatement MessageInsert = null; 			//処理結果メッセージInsert用

	private final String deleteString = "*"; 					//削除を表す文字

	private final String TABLE_NA = "TR_KEIRYOKI"; 			//対象テーブル名

	private final String SYUBETU = mst006401_DataKindDictionary.KEIRYOKI.getCode(); //データ種別

	private MB380000_CommonSql comSql = null; 					//共通SQLクラス
	private MB380007_CommonSyohinSql comSyohin = null;			//商品マスタ生成共通SQLクラス
	private MB380010_CommonKeiryokiCheck Check = null; 					//項目チェック用クラス
	private mst38A106_KeiryokiCheck KeiryokiCheck = null; 				//共通チェック用クラス

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
	public MB380C04_CreateKeiryoki() {
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
			KeiryokiInsert 		= getPreparedKeiryokiSQL(dataBase);
			KeiryokiUpdate 		= getPreparedKeiryokiUpSQL(dataBase);
			KeiryokiDelUp 		= comSyohin.getPreparedSyohinSubDelUpSQL(dataBase, "R_KEIRYOKI");
			KeiryokiDelInsert	= comSyohin.getPreparedKeiryokiInsSQL(dataBase, BATCH_ID);
			SyohinUpInsert 		= comSyohin.getPreparedSyohinInsSQL(dataBase, BATCH_ID);
			GiftSyohinUpInsert 	= comSyohin.getPreparedGiftSyohinInsSQL(dataBase, BATCH_ID);
			HachuUpInsert 		= comSyohin.getPreparedSyohinHachuNohinkijunbiInsSQL(dataBase, BATCH_ID);
			MessageInsert 		= comSql.getPreparedMessageSQL(dataBase);
			SyohinHenkoDtUp     = comSyohin.getPreparedSyohinHenkoDtUpSQL(dataBase);

			// 共通チェック
			Check = new MB380010_CommonKeiryokiCheck(MessageInsert, map, dataBase);
			KeiryokiCheck = new mst38A106_KeiryokiCheck(dataBase);

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

			writeLog(Level.INFO_INT, ECOUNT + "件の削除登録データを処理済（エラー）に更新しました。");
			writeLog(Level.INFO_INT, DCOUNT + "件の削除登録データを処理済に更新しました。");
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
		key[4] = SYUBETU; 									//シート種別
		key[3] = rs.getString("uketsuke_seq"); 				//受付SEQNo.
		key[5] = by_no	;									//バイヤーNO

		Check.setKey(key);

		KeiryokiCheck.setChkSyohin(false);

		// 共通チェックを行う
		if (KeiryokiCheck.process(rs, SYUBETU) == null){

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

					// 計量器マスタ登録
					setPrepareKeiryokiSQL(KeiryokiInsert, rs, insertFg);
					cnt = KeiryokiInsert.executeUpdate();

					// 関連マスタ
					if (Check.getSyohinInsertFg() == true) {

						// 商品マスタ
						comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, rs.getString("S_PLU_SEND_DT"), mst000801_DelFlagDictionary.INAI.getCode());
						cnt = SyohinUpInsert.executeUpdate();

						// ギフト商品マスタ
						comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
						cnt = GiftSyohinUpInsert.executeUpdate();

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

						// 計量器マスタ物理削除
						if (Check.getDeleteFg()) {
							cnt = dataBase.executeUpdate(getDeleteSQL(rs, "R_KEIRYOKI"));
						}

						// 計量器マスタ登録
						setPrepareKeiryokiSQL(KeiryokiInsert, rs, insertFg);
						cnt = KeiryokiInsert.executeUpdate();

						// 関連マスタ
						if (Check.getSyohinInsertFg() == true) {

							// 商品マスタ
							comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, rs.getString("S_PLU_SEND_DT"), mst000801_DelFlagDictionary.INAI.getCode());
							cnt = SyohinUpInsert.executeUpdate();

							// ギフト商品マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							cnt = GiftSyohinUpInsert.executeUpdate();

							// 商品発注納品基準日マスタ
							comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							cnt = HachuUpInsert.executeUpdate();

						} else {

							// 商品マスタの変更日を更新する
							comSyohin.setPreparedSyohinHenkoDtUpSQL(SyohinHenkoDtUp, rs);
							cnt = SyohinHenkoDtUp.executeUpdate();
						}

					} else {

						// 計量器マスタ
						setPrepareKeiryokiSQL(KeiryokiUpdate, rs, insertFg);
						KeiryokiUpdate.executeUpdate();

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

							// 計量器マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(KeiryokiDelInsert, rs, mst000801_DelFlagDictionary.IRU.getCode());
							KeiryokiDelInsert.executeUpdate();

							// 商品マスタ
							comSyohin.setPreparedSyohinInsSQL(SyohinUpInsert, rs, null, mst000801_DelFlagDictionary.INAI.getCode());
							SyohinUpInsert.executeUpdate();

							// ギフト商品マスタ
							comSyohin.setPreparedGiftSyohinInsSQL(GiftSyohinUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							GiftSyohinUpInsert.executeUpdate();

							// 商品発注納品基準日マスタ
							comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(HachuUpInsert, rs, mst000801_DelFlagDictionary.INAI.getCode());
							HachuUpInsert.executeUpdate();

						} else {

							// 計量器マスタ
							comSyohin.setPreparedSyohinSubDelUpSQL(KeiryokiDelUp, rs);
							KeiryokiDelUp.executeUpdate();

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
					batchLog.getLog().fatal(BATCH_ID, BATCH_NA, "マスタ登録に失敗しました。");
					userLog.fatal("マスタ登録に失敗しました。");
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

		sql.append("    TR.TR_SYOHIN_YOBIDASI   AS SYOHIN_YOBIDASI, ");				// TR_呼出NO
		sql.append("    TR.TR_MIN_WEIGHT_QT     AS MIN_WEIGHT_QT, ");				// TR_下限重量
		sql.append("    TR.TR_MAX_WEIGHT_QT     AS MAX_WEIGHT_QT, ");				// TR_上限重量
		sql.append("    TR.TR_TEIKAN_WEIGHT_QT  AS TEIKAN_WEIGHT_QT, ");			// TR_定貫重量
		sql.append("    TR.TR_FUTAI_WEIGHT_QT   AS FUTAI_WEIGHT_QT, ");				// TR_風袋重量
		sql.append("    TR.TR_EYE_CATCH_NO      AS EYE_CATCH_NO, ");				// TR_アイキャッチ№
		sql.append("    TR.TR_EYE_CATCH_MODE    AS EYE_CATCH_MODE, ");				// TR_アイキャッチモード
		sql.append("    TR.TR_TEIGAKU_KB        AS TEIGAKU_KB, ");					// TR_定額区分
		sql.append("    TR.TR_TEIGAKU_BAIKA_VL  AS TEIGAKU_BAIKA_VL, ");			// TR_定額売価
		sql.append("    TR.TR_HOZON_ONDO_QT     AS HOZON_ONDO_QT, ");				// TR_保存温度
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    TR.TR_HOZON_ONDOTAI_KB  AS HOZON_ONDOTAI_KB, ");			// TR_保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    TR.TR_CALORIE           AS CALORIE, ");						// TR_カロリー
		sql.append("    TR.TR_TRAY_NA           AS TRAY_NA, ");						// TR_トレー名
		sql.append("    TR.TR_SYOMIKIKAN_VL     AS SYOMIKIKAN_VL, ");				// TR_賞味期間
		sql.append("    TR.TR_SYOMIKIKAN_KB     AS SYOMIKIKAN_KB, ");				// TR_賞味期間計算区分
		sql.append("    TR.TR_GENZAIRYO_NA      AS GENZAIRYO_NA, ");				// TR_原材料名称
		sql.append("    TR.TR_TENKABUTU_NA      AS TENKABUTU_NA, ");				// TR_添加物名称
		sql.append("    TR.TR_NETSUKEKI_NA      AS NETSUKEKI_NA, ");				// TR_値付器名１
		sql.append("    TR.TR_NETSUKEKI_NA_2    AS NETSUKEKI_NA_2, ");				// TR_値付器名２
		sql.append("    TR.TR_SYOHIN_KBN        AS SYOHIN_KBN, ");					// TR_商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    TR.TR_KAKOBI_PRINT_KB   AS KAKOBI_PRINT_KB, ");				// TR_加工日印字区分
		sql.append("    TR.TR_KAKOJIKOKU_PRINT_KB  AS KAKOJIKOKU_PRINT_KB, ");		// TR_加工時刻印字区分
		sql.append("    TR.TR_TRACEABILITY_FG   AS TRACEABILITY_FG, ");				// TR_トレサビリティフラグ
		sql.append("    TR.TR_TEIKAN_TANI_KB    AS TEIKAN_TANI_KB, ");				// TR_定貫単位区分
		sql.append("    TR.TR_SENTAKU_COMMENT_CD   AS SENTAKU_COMMENT_CD, ");		// TR_選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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

		sql.append("    R.S_GYOSYU_CD, ");											//小業種コード
		sql.append("    R.THEME_CD, ");												//テーマコード
		sql.append("    R.KEIRYO_HANKU_CD, ");										//計量販区コード
		sql.append("    R.HANEI_DT, ");												//反映日
		sql.append("    R.KEIRYOKI_NA, ");											//計量器名称
		sql.append("    R.KEIRYOKI2_NA, ");											//計量器名称２
		sql.append("    R.KEIRYOKI3_NA, ");											//計量器名称３
		sql.append("    R.RECEIPT_HINMEI_NA, ");									//レシート品名
		sql.append("    R.TEIGAKU_UP_KB, ");										//定額・UP区分
		sql.append("    R.TANKA_VL, ");												//単価
		sql.append("    R.TEIGAKU_VL, ");											//定額時内容量
		sql.append("    R.TEIGAKUJI_TANI_KB, ");									//定額時単位区分
		sql.append("    R.SYOMIKIKAN_KB, ");										//賞味期間計算区分
		sql.append("    R.SYOMIKIKAN_VL, ");										//賞味期間
		sql.append("    R.SANTI_KB, ");												//産地番号
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
//		sql.append("    R.KAKOJIKOKU_PRINT_KB, ");									//加工時刻印字区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
		sql.append("    R.CHORIYO_KOKOKUBUN_KB, ");									//調理用広告文
//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 Start
//		sql.append("    R.HOZON_ONDOTAI_KB, ");										//保存温度帯区分
		sql.append("    R.HOZON_ONDOTAI_KB		AS RS_HOZON_ONDOTAI_KB, ");			//保存温度帯区分
//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    R.START_KB, ");												//開始日付区分
		sql.append("    R.BACK_LABEL_KB, ");										//裏面ラベル項目分区分
		sql.append("    R.EIYO_SEIBUN_NA, ");										//栄養成分表示
		sql.append("    R.COMMENT_KB, ");											//コメント区分
		sql.append("    R.BIKO_TX, ");												//備考

		sql.append("    R.SYOHIN_YOBIDASI       AS RS_SYOHIN_YOBIDASI, ");			// 呼出NO
		sql.append("    R.MIN_WEIGHT_QT         AS RS_MIN_WEIGHT_QT, ");			// 下限重量
		sql.append("    R.MAX_WEIGHT_QT         AS RS_MAX_WEIGHT_QT, ");			// 上限重量
		sql.append("    R.TEIKAN_WEIGHT_QT      AS RS_TEIKAN_WEIGHT_QT, ");			// 定貫重量
		sql.append("    R.FUTAI_WEIGHT_QT       AS RS_FUTAI_WEIGHT_QT, ");			// 風袋重量
		sql.append("    R.EYE_CATCH_NO          AS RS_EYE_CATCH_NO, ");				// アイキャッチ№
		sql.append("    R.EYE_CATCH_MODE        AS RS_EYE_CATCH_MODE, ");			// アイキャッチモード
		sql.append("    R.TEIGAKU_KB            AS RS_TEIGAKU_KB, ");				// 定額区分
		sql.append("    R.TEIGAKU_BAIKA_VL      AS RS_TEIGAKU_BAIKA_VL, ");			// 定額売価
		sql.append("    R.HOZON_ONDO_QT         AS RS_HOZON_ONDO_QT, ");			// 保存温度
		sql.append("    R.CALORIE               AS RS_CALORIE, ");					// カロリー
		sql.append("    R.TRAY_NA               AS RS_TRAY_NA, ");					// トレー名
		sql.append("    R.SYOMIKIKAN_VL         AS RS_SYOMIKIKAN_VL, ");			// 賞味期間
		sql.append("    R.SYOMIKIKAN_KB         AS RS_SYOMIKIKAN_KB, ");			// 賞味期間計算区分
		sql.append("    R.GENZAIRYO_NA          AS RS_GENZAIRYO_NA, ");				// 原材料名称
		sql.append("    R.TENKABUTU_NA          AS RS_TENKABUTU_NA, ");				// 添加物名称
		sql.append("    R.NETSUKEKI_NA          AS RS_NETSUKEKI_NA, ");				// 値付器名１
		sql.append("    R.NETSUKEKI_NA_2        AS RS_NETSUKEKI_NA_2, ");			// 値付器名２
		sql.append("    R.SYOHIN_KBN            AS RS_SYOHIN_KBN, ");				// 商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    R.KAKOBI_PRINT_KB       AS RS_KAKOBI_PRINT_KB, ");			// 加工日印字区分
		sql.append("    R.KAKOJIKOKU_PRINT_KB   AS RS_KAKOJIKOKU_PRINT_KB, ");		// 加工時刻印字区分
		sql.append("    R.TRACEABILITY_FG       AS RS_TRACEABILITY_FG, ");			// トレサビリティフラグ
		sql.append("    R.TEIKAN_TANI_KB        AS RS_TEIKAN_TANI_KB, ");			// 定貫単位区分
		sql.append("    R.SENTAKU_COMMENT_CD    AS RS_SENTAKU_COMMENT_CD, ");		// 選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		sql.append("    S.SYOHIN_CD             AS S_SYOHIN_CD, ");					// 商品マスタ存在チェックに使用
		sql.append("    S.YUKO_DT               AS S_YUKO_DT, ");					//
		sql.append("    S.PLU_SEND_DT           AS S_PLU_SEND_DT, ");				//

		sql.append("    EYE_CAT_NO_CK.CODE_CD   AS EYE_CATCH_NO_CK, ");				// アイキャッチ№
		sql.append("    EYE_CAT_MODE_CK.CODE_CD AS EYE_CATCH_MODE_CK, "); 			// アイキャッチモード
		sql.append("    TEIGAKU_CK.CODE_CD      AS TEIGAKU_CK, "); 					// 定額区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    KAKOBI_PRINT_KB_CK.CODE_CD   		AS KAKOBI_PRINT_KB_CK, ");				// 加工日印字区分
		sql.append("    KAKOJIKOKU_PRINT_KB_CK.CODE_CD 		AS KAKOJIKOKU_PRINT_KB_CK, "); 			// 加工時刻印字区分
		sql.append("    TRACEABILITY_FG_CK.CODE_CD   		AS TRACEABILITY_FG_CK, "); 				// トレサビリティフラグ
		sql.append("    TEIKAN_TANI_KB_CK.CODE_CD		    AS TEIKAN_TANI_KB_CK, ");				// 定貫単位区分
		sql.append("    SENTAKU_COMMENT_CD_CK.CODE_CD       AS SENTAKU_COMMENT_CD_CK, "); 			// 選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    HOZON_ONDOTAI_KB_CK.CODE_CD AS HOZON_ONDOTAI_KB_CK, ");		// 保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    SYOHIN_KB_CK.CODE_CD    AS SYOHIN_KB_CK, "); 				// 商品区分
		sql.append("    SYOMIKIKAN_CK.CODE_CD   AS SYOMIKIKAN_CK "); 				// 賞味期間計算区分

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
		sql.append("               TR_KEIRYOKI W ");
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
		sql.append("    ON	 ");
		sql.append("   		 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_KEIRYOKI R ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 R.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND R.YUKO_DT    = S.YUKO_DT ");
		sql.append("   AND R.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		//商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOHIN_KB_CK ");
		sql.append("    ON SYOHIN_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.MSYOHIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOHIN_KB_CK.CODE_CD       = TRIM(TR.TR_SYOHIN_KBN) ");
		sql.append("   AND SYOHIN_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//アイキャッチ№
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EYE_CAT_NO_CK ");
		sql.append("    ON EYE_CAT_NO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EYE_CATCH_NO, userLocal)).append("' ");
		sql.append("   AND EYE_CAT_NO_CK.CODE_CD       = TRIM(TR.TR_EYE_CATCH_NO) ");
		sql.append("   AND EYE_CAT_NO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//アイキャッチモード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EYE_CAT_MODE_CK ");
		sql.append("    ON EYE_CAT_MODE_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EYE_CATCH_MODE, userLocal)).append("' ");
		sql.append("   AND EYE_CAT_MODE_CK.CODE_CD       = TRIM(TR.TR_EYE_CATCH_MODE) ");
		sql.append("   AND EYE_CAT_MODE_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定額区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIGAKU_CK ");
		sql.append("    ON TEIGAKU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TEIGAKU_DIVISION, userLocal)).append("' ");
		sql.append("   AND TEIGAKU_CK.CODE_CD       = TRIM(TR.TR_TEIGAKU_KB) ");
		sql.append("   AND TEIGAKU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		//保存温度帯区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HOZON_ONDOTAI_KB_CK ");
		sql.append("    ON HOZON_ONDOTAI_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HOZON_ONDOTAI_DIVISION, userLocal)).append("' ");
		sql.append("   AND HOZON_ONDOTAI_KB_CK.CODE_CD       = TRIM(TR.TR_HOZON_ONDOTAI_KB) ");
		sql.append("   AND HOZON_ONDOTAI_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		//加工日付印字区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAKOBI_PRINT_KB_CK ");
		sql.append("    ON KAKOBI_PRINT_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAKOJIKOKU_PRINT_KB, userLocal)).append("' ");
		sql.append("   AND KAKOBI_PRINT_KB_CK.CODE_CD       = TRIM(TR.TR_KAKOBI_PRINT_KB) ");
		sql.append("   AND KAKOBI_PRINT_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//加工時刻印字区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAKOJIKOKU_PRINT_KB_CK ");
		sql.append("    ON KAKOJIKOKU_PRINT_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAKOBI_PRINT_KB, userLocal)).append("' ");
		sql.append("   AND KAKOJIKOKU_PRINT_KB_CK.CODE_CD       = TRIM(TR.TR_KAKOJIKOKU_PRINT_KB) ");
		sql.append("   AND KAKOJIKOKU_PRINT_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//トレサビリティフラグ
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TRACEABILITY_FG_CK ");
		sql.append("    ON TRACEABILITY_FG_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TRACEABILITY_FG, userLocal)).append("' ");
		sql.append("   AND TRACEABILITY_FG_CK.CODE_CD       = TRIM(TR.TR_TRACEABILITY_FG) ");
		sql.append("   AND TRACEABILITY_FG_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定貫単位区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIKAN_TANI_KB_CK ");
		sql.append("    ON TEIKAN_TANI_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TEIKAN_TANI_KB, userLocal)).append("' ");
		sql.append("   AND TEIKAN_TANI_KB_CK.CODE_CD       = TRIM(TR.TR_TEIKAN_TANI_KB) ");
		sql.append("   AND TEIKAN_TANI_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//選択コメントコード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SENTAKU_COMMENT_CD_CK ");
		sql.append("    ON SENTAKU_COMMENT_CD_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SENTAKU_COMMENT_CD, userLocal)).append("' ");
		sql.append("   AND SENTAKU_COMMENT_CD_CK.CODE_CD       = TRIM(TR.TR_SENTAKU_COMMENT_CD) ");
		sql.append("   AND SENTAKU_COMMENT_CD_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		//賞味期間計算区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOMIKIKAN_CK ");
		sql.append("    ON SYOMIKIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SYOMI_KIKAN_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOMIKIKAN_CK.CODE_CD       = TRIM(TR.TR_SYOMIKIKAN_KB) ");
		sql.append("   AND SYOMIKIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

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

		sql.append("    TR.TR_SYOHIN_YOBIDASI   AS SYOHIN_YOBIDASI, ");				// TR_呼出NO
		sql.append("    TR.TR_MIN_WEIGHT_QT     AS MIN_WEIGHT_QT, ");				// TR_下限重量
		sql.append("    TR.TR_MAX_WEIGHT_QT     AS MAX_WEIGHT_QT, ");				// TR_上限重量
		sql.append("    TR.TR_TEIKAN_WEIGHT_QT  AS TEIKAN_WEIGHT_QT, ");			// TR_定貫重量
		sql.append("    TR.TR_FUTAI_WEIGHT_QT   AS FUTAI_WEIGHT_QT, ");				// TR_風袋重量
		sql.append("    TR.TR_EYE_CATCH_NO      AS EYE_CATCH_NO, ");				// TR_アイキャッチ№
		sql.append("    TR.TR_EYE_CATCH_MODE    AS EYE_CATCH_MODE, ");				// TR_アイキャッチモード
		sql.append("    TR.TR_TEIGAKU_KB        AS TEIGAKU_KB, ");					// TR_定額区分
		sql.append("    TR.TR_TEIGAKU_BAIKA_VL  AS TEIGAKU_BAIKA_VL, ");			// TR_定額売価
		sql.append("    TR.TR_HOZON_ONDO_QT     AS HOZON_ONDO_QT, ");				// TR_保存温度
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    TR.TR_HOZON_ONDOTAI_KB  AS HOZON_ONDOTAI_KB, ");			// TR_保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    TR.TR_CALORIE           AS CALORIE, ");						// TR_カロリー
		sql.append("    TR.TR_TRAY_NA           AS TRAY_NA, ");						// TR_トレー名
		sql.append("    TR.TR_SYOMIKIKAN_VL     AS SYOMIKIKAN_VL, ");				// TR_賞味期間
		sql.append("    TR.TR_SYOMIKIKAN_KB     AS SYOMIKIKAN_KB, ");				// TR_賞味期間計算区分
		sql.append("    TR.TR_GENZAIRYO_NA      AS GENZAIRYO_NA, ");				// TR_原材料名称
		sql.append("    TR.TR_TENKABUTU_NA      AS TENKABUTU_NA, ");				// TR_添加物名称
		sql.append("    TR.TR_NETSUKEKI_NA      AS NETSUKEKI_NA, ");				// TR_値付器名１
		sql.append("    TR.TR_NETSUKEKI_NA_2    AS NETSUKEKI_NA_2, ");				// TR_値付器名２
		sql.append("    TR.TR_SYOHIN_KBN        AS SYOHIN_KBN, ");					// TR_商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    TR.TR_KAKOBI_PRINT_KB   AS KAKOBI_PRINT_KB, ");				// TR_加工日印字区分
		sql.append("    TR.TR_KAKOJIKOKU_PRINT_KB  AS KAKOJIKOKU_PRINT_KB, ");		// TR_加工時刻印字区分
		sql.append("    TR.TR_TRACEABILITY_FG   AS TRACEABILITY_FG, ");				// TR_トレサビリティフラグ
		sql.append("    TR.TR_TEIKAN_TANI_KB    AS TEIKAN_TANI_KB, ");				// TR_定貫単位区分
		sql.append("    TR.TR_SENTAKU_COMMENT_CD   AS SENTAKU_COMMENT_CD, ");		// TR_選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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

		sql.append("    R.S_GYOSYU_CD, ");											//小業種コード
		sql.append("    R.THEME_CD, ");												//テーマコード
		sql.append("    R.KEIRYO_HANKU_CD, ");										//計量販区コード
		sql.append("    R.HANEI_DT, ");												//反映日
		sql.append("    R.KEIRYOKI_NA, ");											//計量器名称
		sql.append("    R.KEIRYOKI2_NA, ");											//計量器名称２
		sql.append("    R.KEIRYOKI3_NA, ");											//計量器名称３
		sql.append("    R.RECEIPT_HINMEI_NA, ");									//レシート品名
		sql.append("    R.TEIGAKU_UP_KB, ");										//定額・UP区分
		sql.append("    R.TANKA_VL, ");												//単価
		sql.append("    R.TEIGAKU_VL, ");											//定額時内容量
		sql.append("    R.TEIGAKUJI_TANI_KB, ");									//定額時単位区分
		sql.append("    R.SYOMIKIKAN_KB, ");										//賞味期間計算区分
		sql.append("    R.SYOMIKIKAN_VL, ");										//賞味期間
		sql.append("    R.SANTI_KB, ");												//産地番号
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
//		sql.append("    R.KAKOJIKOKU_PRINT_KB, ");									//加工時刻印字区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
		sql.append("    R.CHORIYO_KOKOKUBUN_KB, ");									//調理用広告文
//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 Start
//		sql.append("    R.HOZON_ONDOTAI_KB, ");										//保存温度帯区分
		sql.append("    R.HOZON_ONDOTAI_KB		AS RS_HOZON_ONDOTAI_KB, ");			//保存温度帯区分
//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    R.START_KB, ");												//開始日付区分
		sql.append("    R.BACK_LABEL_KB, ");										//裏面ラベル項目分区分
		sql.append("    R.EIYO_SEIBUN_NA, ");										//栄養成分表示
		sql.append("    R.COMMENT_KB, ");											//コメント区分
		sql.append("    R.BIKO_TX, ");												//備考

		sql.append("    R.SYOHIN_YOBIDASI       AS RS_SYOHIN_YOBIDASI, ");			// 呼出NO
		sql.append("    R.MIN_WEIGHT_QT         AS RS_MIN_WEIGHT_QT, ");			// 下限重量
		sql.append("    R.MAX_WEIGHT_QT         AS RS_MAX_WEIGHT_QT, ");			// 上限重量
		sql.append("    R.TEIKAN_WEIGHT_QT      AS RS_TEIKAN_WEIGHT_QT, ");			// 定貫重量
		sql.append("    R.FUTAI_WEIGHT_QT       AS RS_FUTAI_WEIGHT_QT, ");			// 風袋重量
		sql.append("    R.EYE_CATCH_NO          AS RS_EYE_CATCH_NO, ");				// アイキャッチ№
		sql.append("    R.EYE_CATCH_MODE        AS RS_EYE_CATCH_MODE, ");			// アイキャッチモード
		sql.append("    R.TEIGAKU_KB            AS RS_TEIGAKU_KB, ");				// 定額区分
		sql.append("    R.TEIGAKU_BAIKA_VL      AS RS_TEIGAKU_BAIKA_VL, ");			// 定額売価
		sql.append("    R.HOZON_ONDO_QT         AS RS_HOZON_ONDO_QT, ");			// 保存温度
		sql.append("    R.CALORIE               AS RS_CALORIE, ");					// カロリー
		sql.append("    R.TRAY_NA               AS RS_TRAY_NA, ");					// トレー名
		sql.append("    R.SYOMIKIKAN_VL         AS RS_SYOMIKIKAN_VL, ");			// 賞味期間
		sql.append("    R.SYOMIKIKAN_KB         AS RS_SYOMIKIKAN_KB, ");			// 賞味期間計算区分
		sql.append("    R.GENZAIRYO_NA          AS RS_GENZAIRYO_NA, ");				// 原材料名称
		sql.append("    R.TENKABUTU_NA          AS RS_TENKABUTU_NA, ");				// 添加物名称
		sql.append("    R.NETSUKEKI_NA          AS RS_NETSUKEKI_NA, ");				// 値付器名１
		sql.append("    R.NETSUKEKI_NA_2        AS RS_NETSUKEKI_NA_2, ");			// 値付器名２
		sql.append("    R.SYOHIN_KBN            AS RS_SYOHIN_KBN, ");				// 商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    R.KAKOBI_PRINT_KB       AS RS_KAKOBI_PRINT_KB, ");			// 加工日印字区分
		sql.append("    R.KAKOJIKOKU_PRINT_KB   AS RS_KAKOJIKOKU_PRINT_KB, ");		// 加工時刻印字区分
		sql.append("    R.TRACEABILITY_FG       AS RS_TRACEABILITY_FG, ");			// トレサビリティフラグ
		sql.append("    R.TEIKAN_TANI_KB        AS RS_TEIKAN_TANI_KB, ");			// 定貫単位区分
		sql.append("    R.SENTAKU_COMMENT_CD    AS RS_SENTAKU_COMMENT_CD, ");		// 選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		sql.append("    S.SYOHIN_CD             AS S_SYOHIN_CD, ");					// 商品マスタ存在チェックに使用
		sql.append("    S.YUKO_DT               AS S_YUKO_DT, ");					//
		sql.append("    S.PLU_SEND_DT           AS S_PLU_SEND_DT, ");				//

		sql.append("    EYE_CAT_NO_CK.CODE_CD   AS EYE_CATCH_NO_CK, ");				// アイキャッチ№
		sql.append("    EYE_CAT_MODE_CK.CODE_CD AS EYE_CATCH_MODE_CK, "); 			// アイキャッチモード
		sql.append("    TEIGAKU_CK.CODE_CD      AS TEIGAKU_CK, "); 					// 定額区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    KAKOBI_PRINT_KB_CK.CODE_CD   		AS KAKOBI_PRINT_KB_CK, ");				// 加工日印字区分
		sql.append("    KAKOJIKOKU_PRINT_KB_CK.CODE_CD 		AS KAKOJIKOKU_PRINT_KB_CK, "); 			// 加工時刻印字区分
		sql.append("    TRACEABILITY_FG_CK.CODE_CD   		AS TRACEABILITY_FG_CK, "); 				// トレサビリティフラグ
		sql.append("    TEIKAN_TANI_KB_CK.CODE_CD		    AS TEIKAN_TANI_KB_CK, ");				// 定貫単位区分
		sql.append("    SENTAKU_COMMENT_CD_CK.CODE_CD       AS SENTAKU_COMMENT_CD_CK, "); 			// 選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    HOZON_ONDOTAI_KB_CK.CODE_CD AS HOZON_ONDOTAI_KB_CK, ");		// 保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    SYOHIN_KB_CK.CODE_CD    AS SYOHIN_KB_CK, "); 				// 定額区分
		sql.append("    SYOMIKIKAN_CK.CODE_CD   AS SYOMIKIKAN_CK "); 				// 賞味期間計算区分

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
		sql.append("               TR_KEIRYOKI W ");
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
		sql.append("    ON 	 ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_KEIRYOKI R ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   	 R.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND R.YUKO_DT    = S.YUKO_DT ");
		sql.append("   AND R.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		//商品区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOHIN_KB_CK ");
		sql.append("    ON SYOHIN_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.MSYOHIN_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOHIN_KB_CK.CODE_CD       = TRIM(TR.TR_SYOHIN_KBN) ");
		sql.append("   AND SYOHIN_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//アイキャッチ№
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EYE_CAT_NO_CK ");
		sql.append("    ON EYE_CAT_NO_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EYE_CATCH_NO, userLocal)).append("' ");
		sql.append("   AND EYE_CAT_NO_CK.CODE_CD       = TRIM(TR.TR_EYE_CATCH_NO) ");
		sql.append("   AND EYE_CAT_NO_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//アイキャッチモード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF EYE_CAT_MODE_CK ");
		sql.append("    ON EYE_CAT_MODE_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EYE_CATCH_MODE, userLocal)).append("' ");
		sql.append("   AND EYE_CAT_MODE_CK.CODE_CD       = TRIM(TR.TR_EYE_CATCH_MODE) ");
		sql.append("   AND EYE_CAT_MODE_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定額区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIGAKU_CK ");
		sql.append("    ON TEIGAKU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TEIGAKU_DIVISION, userLocal)).append("' ");
		sql.append("   AND TEIGAKU_CK.CODE_CD       = TRIM(TR.TR_TEIGAKU_KB) ");
		sql.append("   AND TEIGAKU_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		//保存温度帯区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF HOZON_ONDOTAI_KB_CK ");
		sql.append("    ON HOZON_ONDOTAI_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.HOZON_ONDOTAI_DIVISION, userLocal)).append("' ");
		sql.append("   AND HOZON_ONDOTAI_KB_CK.CODE_CD       = TRIM(TR.TR_HOZON_ONDOTAI_KB) ");
		sql.append("   AND HOZON_ONDOTAI_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		//加工日付印字区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAKOBI_PRINT_KB_CK ");
		sql.append("    ON KAKOBI_PRINT_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAKOJIKOKU_PRINT_KB, userLocal)).append("' ");
		sql.append("   AND KAKOBI_PRINT_KB_CK.CODE_CD       = TRIM(TR.TR_KAKOBI_PRINT_KB) ");
		sql.append("   AND KAKOBI_PRINT_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//加工時刻印字区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF KAKOJIKOKU_PRINT_KB_CK ");
		sql.append("    ON KAKOJIKOKU_PRINT_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.KAKOBI_PRINT_KB, userLocal)).append("' ");
		sql.append("   AND KAKOJIKOKU_PRINT_KB_CK.CODE_CD       = TRIM(TR.TR_KAKOJIKOKU_PRINT_KB) ");
		sql.append("   AND KAKOJIKOKU_PRINT_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//トレサビリティフラグ
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TRACEABILITY_FG_CK ");
		sql.append("    ON TRACEABILITY_FG_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TRACEABILITY_FG, userLocal)).append("' ");
		sql.append("   AND TRACEABILITY_FG_CK.CODE_CD       = TRIM(TR.TR_TRACEABILITY_FG) ");
		sql.append("   AND TRACEABILITY_FG_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//定貫単位区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF TEIKAN_TANI_KB_CK ");
		sql.append("    ON TEIKAN_TANI_KB_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.TEIKAN_TANI_KB, userLocal)).append("' ");
		sql.append("   AND TEIKAN_TANI_KB_CK.CODE_CD       = TRIM(TR.TR_TEIKAN_TANI_KB) ");
		sql.append("   AND TEIKAN_TANI_KB_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

		//選択コメントコード
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SENTAKU_COMMENT_CD_CK ");
		sql.append("    ON SENTAKU_COMMENT_CD_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SENTAKU_COMMENT_CD, userLocal)).append("' ");
		sql.append("   AND SENTAKU_COMMENT_CD_CK.CODE_CD       = TRIM(TR.TR_SENTAKU_COMMENT_CD) ");
		sql.append("   AND SENTAKU_COMMENT_CD_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
		//賞味期間計算区分
		sql.append("  LEFT JOIN ");
		sql.append("       R_NAMECTF SYOMIKIKAN_CK ");
		sql.append("    ON SYOMIKIKAN_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SYOMI_KIKAN_DIVISION, userLocal)).append("' ");
		sql.append("   AND SYOMIKIKAN_CK.CODE_CD       = TRIM(TR.TR_SYOMIKIKAN_KB) ");
		sql.append("   AND SYOMIKIKAN_CK.DELETE_FG     = '").append(DEL_FG_INAI).append("'");

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

		sql.append("    TR.TR_SYOHIN_YOBIDASI   AS SYOHIN_YOBIDASI, ");				// TR_呼出NO
		sql.append("    TR.TR_MIN_WEIGHT_QT     AS MIN_WEIGHT_QT, ");				// TR_下限重量
		sql.append("    TR.TR_MAX_WEIGHT_QT     AS MAX_WEIGHT_QT, ");				// TR_上限重量
		sql.append("    TR.TR_TEIKAN_WEIGHT_QT  AS TEIKAN_WEIGHT_QT, ");			// TR_定貫重量
		sql.append("    TR.TR_FUTAI_WEIGHT_QT   AS FUTAI_WEIGHT_QT, ");				// TR_風袋重量
		sql.append("    TR.TR_EYE_CATCH_NO      AS EYE_CATCH_NO, ");				// TR_アイキャッチ№
		sql.append("    TR.TR_EYE_CATCH_MODE    AS EYE_CATCH_MODE, ");				// TR_アイキャッチモード
		sql.append("    TR.TR_TEIGAKU_KB        AS TEIGAKU_KB, ");					// TR_定額区分
		sql.append("    TR.TR_TEIGAKU_BAIKA_VL  AS TEIGAKU_BAIKA_VL, ");			// TR_定額売価
		sql.append("    TR.TR_HOZON_ONDO_QT     AS HOZON_ONDO_QT, ");				// TR_保存温度
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    TR.TR_HOZON_ONDOTAI_KB  AS HOZON_ONDOTAI_KB, ");			// TR_保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		sql.append("    TR.TR_CALORIE           AS CALORIE, ");						// TR_カロリー
		sql.append("    TR.TR_TRAY_NA           AS TRAY_NA, ");						// TR_トレー名
		sql.append("    TR.TR_SYOMIKIKAN_VL     AS SYOMIKIKAN_VL, ");				// TR_賞味期間
		sql.append("    TR.TR_SYOMIKIKAN_KB     AS SYOMIKIKAN_KB, ");				// TR_賞味期間計算区分
		sql.append("    TR.TR_GENZAIRYO_NA      AS GENZAIRYO_NA, ");				// TR_原材料名称
		sql.append("    TR.TR_TENKABUTU_NA      AS TENKABUTU_NA, ");				// TR_添加物名称
		sql.append("    TR.TR_NETSUKEKI_NA      AS NETSUKEKI_NA, ");				// TR_値付器名１
		sql.append("    TR.TR_NETSUKEKI_NA_2    AS NETSUKEKI_NA_2, ");				// TR_値付器名２
		sql.append("    TR.TR_SYOHIN_KBN        AS SYOHIN_KBN, ");					// TR_商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    TR.TR_KAKOBI_PRINT_KB   AS KAKOBI_PRINT_KB, ");				// TR_加工日印字区分
		sql.append("    TR.TR_KAKOJIKOKU_PRINT_KB  AS KAKOJIKOKU_PRINT_KB, ");		// TR_加工時刻印字区分
		sql.append("    TR.TR_TRACEABILITY_FG   AS TRACEABILITY_FG, ");				// TR_トレサビリティフラグ
		sql.append("    TR.TR_TEIKAN_TANI_KB    AS TEIKAN_TANI_KB, ");				// TR_定貫単位区分
		sql.append("    TR.TR_SENTAKU_COMMENT_CD   AS SENTAKU_COMMENT_CD, ");		// TR_選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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

//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		sql.append("    R.HOZON_ONDOTAI_KB		AS RS_HOZON_ONDOTAI_KB, ");			//保存温度帯区分
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End

		sql.append("    R.SYOHIN_YOBIDASI       AS RS_SYOHIN_YOBIDASI, ");			// 呼出NO
		sql.append("    R.MIN_WEIGHT_QT         AS RS_MIN_WEIGHT_QT, ");			// 下限重量
		sql.append("    R.MAX_WEIGHT_QT         AS RS_MAX_WEIGHT_QT, ");			// 上限重量
		sql.append("    R.TEIKAN_WEIGHT_QT      AS RS_TEIKAN_WEIGHT_QT, ");			// 定貫重量
		sql.append("    R.FUTAI_WEIGHT_QT       AS RS_FUTAI_WEIGHT_QT, ");			// 風袋重量
		sql.append("    R.EYE_CATCH_NO          AS RS_EYE_CATCH_NO, ");				// アイキャッチ№
		sql.append("    R.EYE_CATCH_MODE        AS RS_EYE_CATCH_MODE, ");			// アイキャッチモード
		sql.append("    R.TEIGAKU_KB            AS RS_TEIGAKU_KB, ");				// 定額区分
		sql.append("    R.TEIGAKU_BAIKA_VL      AS RS_TEIGAKU_BAIKA_VL, ");			// 定額売価
		sql.append("    R.HOZON_ONDO_QT         AS RS_HOZON_ONDO_QT, ");			// 保存温度
		sql.append("    R.CALORIE               AS RS_CALORIE, ");					// カロリー
		sql.append("    R.TRAY_NA               AS RS_TRAY_NA, ");					// トレー名
		sql.append("    R.SYOMIKIKAN_VL         AS RS_SYOMIKIKAN_VL, ");			// 賞味期間
		sql.append("    R.SYOMIKIKAN_KB         AS RS_SYOMIKIKAN_KB, ");			// 賞味期間計算区分
		sql.append("    R.GENZAIRYO_NA          AS RS_GENZAIRYO_NA, ");				// 原材料名称
		sql.append("    R.TENKABUTU_NA          AS RS_TENKABUTU_NA, ");				// 添加物名称
		sql.append("    R.NETSUKEKI_NA          AS RS_NETSUKEKI_NA, ");				// 値付器名１
		sql.append("    R.NETSUKEKI_NA_2        AS RS_NETSUKEKI_NA_2, ");			// 値付器名２
		sql.append("    R.SYOHIN_KBN            AS RS_SYOHIN_KBN, ");				// 商品区分
//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		sql.append("    R.KAKOBI_PRINT_KB       AS RS_KAKOBI_PRINT_KB, ");			// 加工日印字区分
		sql.append("    R.KAKOJIKOKU_PRINT_KB   AS RS_KAKOJIKOKU_PRINT_KB, ");		// 加工時刻印字区分
		sql.append("    R.TRACEABILITY_FG       AS RS_TRACEABILITY_FG, ");			// トレサビリティフラグ
		sql.append("    R.TEIKAN_TANI_KB        AS RS_TEIKAN_TANI_KB, ");			// 定貫単位区分
		sql.append("    R.SENTAKU_COMMENT_CD    AS RS_SENTAKU_COMMENT_CD, ");		// 選択コメントコード
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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
		sql.append("                 WHERE 	 ");
		sql.append("                   	 SYOHIN_CD  = W.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("                   AND YUKO_DT   <= COALESCE(W.TR_YUKO_DT, '" + DefaultYukoDt + "') ");
		sql.append("               ) AS SYOHIN_YUKO_DT ");
		sql.append("          FROM TR_TOROKU_SYONIN T ");
		sql.append("         INNER JOIN ");
		sql.append("               TR_KEIRYOKI W ");
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
		sql.append("    ON	 ");
		sql.append("   	 S.SYOHIN_CD  = TR.TR_SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND S.YUKO_DT    = TR.SYOHIN_YUKO_DT ");
		sql.append("   AND S.DELETE_FG  = '").append(DEL_FG_INAI).append("' ");

		sql.append("  LEFT JOIN ");
		sql.append("       R_KEIRYOKI R ");
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
	 * 計量器マスタデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedKeiryokiSQL(MasterDataBase dataBase) throws SQLException {
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

		//呼出NO
		sql1.append("SYOHIN_YOBIDASI,");
		sql2.append(" ?,");

		//小業種コード
		sql1.append("S_GYOSYU_CD,");
		sql2.append(" ?,");

		//テーマコード
		sql1.append("THEME_CD,");
		sql2.append(" ?,");

		//計量販区コード
		sql1.append("KEIRYO_HANKU_CD,");
		sql2.append(" ?,");

		//反映日
		sql1.append("HANEI_DT,");
		sql2.append(" ?,");

		//商品区分
		sql1.append("SYOHIN_KBN,");
		sql2.append(" ?,");

		//計量器名称
		sql1.append("KEIRYOKI_NA,");
		sql2.append(" ?,");

		//計量器名称２
		sql1.append("KEIRYOKI2_NA,");
		sql2.append(" ?,");

		//計量器名称３
		sql1.append("KEIRYOKI3_NA,");
		sql2.append(" ?,");

		//レシート品名
		sql1.append("RECEIPT_HINMEI_NA,");
		sql2.append(" ?,");

		//定額・UP区分
		sql1.append("TEIGAKU_UP_KB,");
		sql2.append(" ?,");

		//単価
		sql1.append("TANKA_VL,");
		sql2.append(" ?,");

		//定額時内容量
		sql1.append("TEIGAKU_VL,");
		sql2.append(" ?,");

		//定額時単位区分
		sql1.append("TEIGAKUJI_TANI_KB,");
		sql2.append(" ?,");

		//賞味期間計算区分
		sql1.append("SYOMIKIKAN_KB,");
		sql2.append(" ?,");

		//賞味期間
		sql1.append("SYOMIKIKAN_VL,");
		sql2.append(" ?,");

		//産地番号
		sql1.append("SANTI_KB,");
		sql2.append(" ?,");

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
//		//加工時刻印字区分
//		sql1.append("KAKOJIKOKU_PRINT_KB,");
//		sql2.append(" ?,");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		//調理用広告文
		sql1.append("CHORIYO_KOKOKUBUN_KB,");
		sql2.append(" ?,");

		//保存温度帯区分
		sql1.append("HOZON_ONDOTAI_KB,");
		sql2.append(" ?,");

		//開始日付区分
		sql1.append("START_KB,");
		sql2.append(" ?,");

		//裏面ラベル項目分区分
		sql1.append("BACK_LABEL_KB,");
		sql2.append(" ?,");

		//栄養成分表示
		sql1.append("EIYO_SEIBUN_NA,");
		sql2.append(" ?,");

		//コメント区分
		sql1.append("COMMENT_KB,");
		sql2.append(" ?,");

		//備考
		sql1.append("BIKO_TX,");
		sql2.append(" ?,");

		//原材料名称
		sql1.append("GENZAIRYO_NA,");
		sql2.append(" ?,");

		//添加物名称
		sql1.append("TENKABUTU_NA,");
		sql2.append(" ?,");

		//下限重量
		sql1.append("MIN_WEIGHT_QT,");
		sql2.append(" ?,");

		//上限重量
		sql1.append("MAX_WEIGHT_QT,");
		sql2.append(" ?,");

		//定貫重量
		sql1.append("TEIKAN_WEIGHT_QT,");
		sql2.append(" ?,");

		//風袋重量
		sql1.append("FUTAI_WEIGHT_QT,");
		sql2.append(" ?,");

		//アイキャッチ№
		sql1.append("EYE_CATCH_NO,");
		sql2.append(" ?,");

		//アイキャッチモード
		sql1.append("EYE_CATCH_MODE,");
		sql2.append(" ?,");

		//定額区分
		sql1.append("TEIGAKU_KB,");
		sql2.append(" ?,");

		//定額売価
		sql1.append("TEIGAKU_BAIKA_VL,");
		sql2.append(" ?,");

		//保存温度
		sql1.append("HOZON_ONDO_QT,");
		sql2.append(" ?,");

		//カロリー
		sql1.append("CALORIE,");
		sql2.append(" ?,");

		//トレー名
		sql1.append("TRAY_NA,");
		sql2.append(" ?,");

		//値付器名１
		sql1.append("NETSUKEKI_NA,");
		sql2.append(" ?,");

		//値付器名２
		sql1.append("NETSUKEKI_NA_2,");
		sql2.append(" ?,");

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		//加工日印字区分
		sql1.append("KAKOBI_PRINT_KB,");
		sql2.append(" ?,");

		//加工時刻印字区分
		sql1.append("KAKOJIKOKU_PRINT_KB,");
		sql2.append(" ?,");

		//トレサビリティフラグ
		sql1.append("TRACEABILITY_FG,");
		sql2.append(" ?,");

		//定貫単位区分
		sql1.append("TEIKAN_TANI_KB,");
		sql2.append(" ?,");

		//選択コメントコード
		sql1.append("SENTAKU_COMMENT_CD,");
		sql2.append(" ?,");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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

		sql.append("INSERT INTO R_KEIRYOKI ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") VALUES ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 計量器マスタデータ更新用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedKeiryokiUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();

		//呼出NO
		sql1.append("SYOHIN_YOBIDASI = ?,");

		//小業種コード
		sql1.append("S_GYOSYU_CD = ?,");

		//テーマコード
		sql1.append("THEME_CD = ?,");

		//計量販区コード
		sql1.append("KEIRYO_HANKU_CD = ?,");

		//反映日
		sql1.append("HANEI_DT = ?,");

		//商品区分
		sql1.append("SYOHIN_KBN = ?,");

		//計量器名称
		sql1.append("KEIRYOKI_NA = ?,");

		//計量器名称２
		sql1.append("KEIRYOKI2_NA = ?,");

		//計量器名称３
		sql1.append("KEIRYOKI3_NA = ?,");

		//レシート品名
		sql1.append("RECEIPT_HINMEI_NA = ?,");

		//定額・UP区分
		sql1.append("TEIGAKU_UP_KB = ?,");

		//単価
		sql1.append("TANKA_VL = ?,");

		//定額時内容量
		sql1.append("TEIGAKU_VL = ?,");

		//定額時単位区分
		sql1.append("TEIGAKUJI_TANI_KB = ?,");

		//賞味期間計算区分
		sql1.append("SYOMIKIKAN_KB = ?,");

		//賞味期間
		sql1.append("SYOMIKIKAN_VL = ?,");

		//産地番号
		sql1.append("SANTI_KB = ?,");

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
//		//加工時刻印字区分
//		sql1.append("KAKOJIKOKU_PRINT_KB = ?,");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		//調理用広告文
		sql1.append("CHORIYO_KOKOKUBUN_KB = ?,");

		//保存温度帯区分
		sql1.append("HOZON_ONDOTAI_KB = ?,");

		//開始日付区分
		sql1.append("START_KB = ?,");

		//裏面ラベル項目分区分
		sql1.append("BACK_LABEL_KB = ?,");

		//栄養成分表示
		sql1.append("EIYO_SEIBUN_NA = ?,");

		//コメント区分
		sql1.append("COMMENT_KB = ?,");

		//備考
		sql1.append("BIKO_TX = ?,");

		//原材料名称
		sql1.append("GENZAIRYO_NA = ?,");

		//添加物名称
		sql1.append("TENKABUTU_NA = ?,");

		//下限重量
		sql1.append("MIN_WEIGHT_QT = ?,");

		//上限重量
		sql1.append("MAX_WEIGHT_QT = ?,");

		//定貫重量
		sql1.append("TEIKAN_WEIGHT_QT = ?,");

		//風袋重量
		sql1.append("FUTAI_WEIGHT_QT = ?,");

		//アイキャッチ№
		sql1.append("EYE_CATCH_NO = ?,");

		//アイキャッチモード
		sql1.append("EYE_CATCH_MODE = ?,");

		//定額区分
		sql1.append("TEIGAKU_KB = ?,");

		//定額売価
		sql1.append("TEIGAKU_BAIKA_VL = ?,");

		//保存温度
		sql1.append("HOZON_ONDO_QT = ?,");

		//カロリー
		sql1.append("CALORIE = ?,");

		//トレー名
		sql1.append("TRAY_NA = ?,");

		//値付器名１
		sql1.append("NETSUKEKI_NA = ?,");

		//値付器名２
		sql1.append("NETSUKEKI_NA_2 = ?,");

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		//加工日印字区分
		sql1.append("KAKOBI_PRINT_KB = ?,");

		//加工時刻印字区分
		sql1.append("KAKOJIKOKU_PRINT_KB = ?,");

		//トレサビリティフラグ
		sql1.append("TRACEABILITY_FG = ?,");

		//定貫単位区分
		sql1.append("TEIKAN_TANI_KB = ?,");

		//選択コメントコード
		sql1.append("SENTAKU_COMMENT_CD = ?,");
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

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

		sql.append("UPDATE R_KEIRYOKI ");
		sql.append("   SET ");
		sql.append(sql1.toString());
		sql.append(" WHERE BUNRUI1_CD = ? ");
		sql.append("   AND SYOHIN_CD  = ? ");
		sql.append("   AND YUKO_DT    = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * 計量器マスタデータ更新SQL
	 * @throws Exception
	 */
	private void setPrepareKeiryokiSQL(PreparedStatement pstmt, ResultSet rs, boolean insertFg) throws SQLException {

		int idx = 0;

		String str = "";

		if (insertFg) {

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("SYOHIN_CD"));

			//有効日
			idx++;
			if(isNotBlank("YUKO_DT")){
				pstmt.setString(idx, rs.getString("YUKO_DT"));
			}else{
				pstmt.setString(idx, DefaultYukoDt);
			}
		}

		//呼出NO ※nullの場合は商品コードの4～7桁をセット
		// substringの引数を修正（0が一桁目のため3,7に修正）
		idx++;
		str = rs.getString("SYOHIN_YOBIDASI");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setString(idx, rs.getString("SYOHIN_CD").substring(3, 7).trim());
			}
		} else {
			str = rs.getString("RS_SYOHIN_YOBIDASI");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setString(idx, rs.getString("SYOHIN_CD").substring(3, 7).trim());
			}
		}

		//小業種コード
		idx++;
		str = rs.getString("S_GYOSYU_CD");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//テーマコード
		idx++;
		str = rs.getString("THEME_CD");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}


		//計量販区コード
		idx++;
		str = rs.getString("KEIRYO_HANKU_CD");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//反映日
		idx++;
		str = rs.getString("HANEI_DT");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//商品区分
		idx++;
		str = rs.getString("SYOHIN_KBN");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SYOHIN_KBN");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//計量器名称
		idx++;
		str = rs.getString("KEIRYOKI_NA");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//計量器名称２
		idx++;
		str = rs.getString("KEIRYOKI2_NA");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//計量器名称３
		idx++;
		str = rs.getString("KEIRYOKI3_NA");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//レシート品名
		idx++;
		str = rs.getString("RECEIPT_HINMEI_NA");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//定額・UP区分
		idx++;
		str = rs.getString("TEIGAKU_UP_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//単価
		idx++;
		str = rs.getString("TANKA_VL");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//定額時内容量
		idx++;
		str = rs.getString("TEIGAKU_VL");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//定額時単位区分
		idx++;
		str = rs.getString("TEIGAKUJI_TANI_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//賞味期間計算区分
		idx++;
		str = rs.getString("SYOMIKIKAN_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SYOMIKIKAN_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//賞味期間
		idx++;
		str = rs.getString("SYOMIKIKAN_VL");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_SYOMIKIKAN_VL");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//産地番号
		idx++;
		str = rs.getString("SANTI_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
//		//加工時刻印字区分
//		idx++;
//		str = rs.getString("KAKOJIKOKU_PRINT_KB");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
//2014.01.15 [CUS00104]  計量器項目変更対応 (E)

		//調理用広告文
		idx++;
		str = rs.getString("CHORIYO_KOKOKUBUN_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 Start
		//保存温度帯区分
//		idx++;
//		str = rs.getString("HOZON_ONDOTAI_KB");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str.trim());
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}

		//保存温度帯区分
		idx++;
		str = rs.getString("HOZON_ONDOTAI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_HOZON_ONDOTAI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
//2011.02.25 Y.Imai Mod 計量器保存温度帯区分追加対応 MM00111 End

		//開始日付区分
		idx++;
		str = rs.getString("START_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//裏面ラベル項目分区分
		idx++;
		str = rs.getString("BACK_LABEL_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//栄養成分表示
		idx++;
		str = rs.getString("EIYO_SEIBUN_NA");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//コメント区分
		idx++;
		str = rs.getString("COMMENT_KB");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//備考
		idx++;
		str = rs.getString("BIKO_TX");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str.trim());
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//原材料名称
		idx++;
		str = rs.getString("GENZAIRYO_NA");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_GENZAIRYO_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//添加物名称
		idx++;
		str = rs.getString("TENKABUTU_NA");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TENKABUTU_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//下限重量
		idx++;
		str = rs.getString("MIN_WEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_MIN_WEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//上限重量
		idx++;
		str = rs.getString("MAX_WEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_MAX_WEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//定貫重量
		idx++;
		str = rs.getString("TEIKAN_WEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_TEIKAN_WEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//風袋重量
		idx++;
		str = rs.getString("FUTAI_WEIGHT_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_FUTAI_WEIGHT_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//アイキャッチ№
		idx++;
		str = rs.getString("EYE_CATCH_NO");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_EYE_CATCH_NO");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//アイキャッチモード
		idx++;
		str = rs.getString("EYE_CATCH_MODE");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_EYE_CATCH_MODE");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//定額区分
		idx++;
		str = rs.getString("TEIGAKU_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TEIGAKU_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//定額売価
		idx++;
		str = rs.getString("TEIGAKU_BAIKA_VL");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_TEIGAKU_BAIKA_VL");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//保存温度
		idx++;
		str = rs.getString("HOZON_ONDO_QT");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
			str = rs.getString("RS_HOZON_ONDO_QT");
			if (isNotBlank(str)) {
				pstmt.setLong(idx, Long.parseLong(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//カロリー
		idx++;
		str = rs.getString("CALORIE");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_CALORIE");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//トレー名
		idx++;
		str = rs.getString("TRAY_NA");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TRAY_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//値付器名１
		idx++;
		str = rs.getString("NETSUKEKI_NA");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NETSUKEKI_NA");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//値付器名２
		idx++;
		str = rs.getString("NETSUKEKI_NA_2");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_NETSUKEKI_NA_2");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

//2014.01.15 [CUS00104]  計量器項目変更対応 (S)
		//加工日印字区分
		idx++;
		str = rs.getString("KAKOBI_PRINT_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KAKOBI_PRINT_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//加工時刻印字区分
		idx++;
		str = rs.getString("KAKOJIKOKU_PRINT_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_KAKOJIKOKU_PRINT_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//トレサビリティフラグ
		idx++;
		str = rs.getString("TRACEABILITY_FG");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TRACEABILITY_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//定貫単位区分
		idx++;
		str = rs.getString("TEIKAN_TANI_KB");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_TEIKAN_TANI_KB");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//選択コメントコード
		idx++;
		str = rs.getString("SENTAKU_COMMENT_CD");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("RS_SENTAKU_COMMENT_CD");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

//2014.01.15 [CUS00104]  計量器項目変更対応 (E)
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
		//削除フラグ

		if (!insertFg) {

			//部門コード
			idx++;
			pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

			//商品コード
			idx++;
			pstmt.setString(idx, rs.getString("SYOHIN_CD"));

			//有効日
			idx++;
			if(isNotBlank("YUKO_DT")){
				pstmt.setString(idx, rs.getString("YUKO_DT"));
			}else{
				pstmt.setString(idx, DefaultYukoDt);
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
