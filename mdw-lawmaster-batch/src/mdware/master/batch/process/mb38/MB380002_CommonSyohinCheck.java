package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.util.DateChanger;
import mdware.common.util.DateDiff;
import mdware.common.util.NumberUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000401_KumisuKbDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst001101_TeikanKbDictionary;
import mdware.master.common.dictionary.mst001201_EosKbDictionary;
import mdware.master.common.dictionary.mst001901_MiseZaikoKbDictionary;
import mdware.master.common.dictionary.mst002501_UnitPriceKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:商品マスタ生成バッチ共通チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.00 (2014/08/07) NGHIA-HT 海外LAWSON様通貨対応
 * @version 1.01 (2016/10/24) T.Arimoto #2256対応
 * @version 1.02 (2017/02/03) T.Arimoto #1174対応
 */

public class MB380002_CommonSyohinCheck {

	//項目の削除を表す文字
	private final String deleteString = "*";

	//メッセージを保持するMap
	private Map map = null;

	//DBクラス
	private MasterDataBase dataBase = null;

	//メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;

	//商品マスタチェック用PreparedStatement
	private PreparedStatement syohinBefCheck = null;
	private PreparedStatement syohinAftCheck = null;
	private PreparedStatement syohinBumonCheck = null;	// add by kema 06.11.03
	private PreparedStatement syohinCount = null;
	private PreparedStatement poscodeCheck = null;
	private PreparedStatement syohinCdCheck = null;

	private PreparedStatement syohinAftCheck2 = null;
	
	
	//TRテーブルのキー
	private String[] key = null;

	//マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();

	//Insert or Updateを判断するフラグ
	private boolean InsertFg = true;

	//物理削除の要否を判断するフラグ
	private boolean DeleteFg = false;

	//Update時のキーになる有効日
	private String YukoDt = "";

	private final int DEAD_LOCK_ERROR  = -913;
	private BatchLog batchLog = BatchLog.getInstance();
	
	// メッセージ登録時のID
	String messageId;

	public MB380002_CommonSyohinCheck(PreparedStatement pstmt, Map map, MasterDataBase dataBase) throws SQLException {

		this.pstmt 			= pstmt;
		this.map 			= map;
		this.dataBase 		= dataBase;
		syohinBefCheck 		= getPreparedBefCheckSQL(this.dataBase);
		syohinAftCheck 		= getPreparedAftCheckSQL(this.dataBase);
		syohinBumonCheck 	= getPreparedBumonCheckSQL(this.dataBase);	
		syohinCount 		= getPreparedCountSQL(this.dataBase);
		poscodeCheck 		= getPreparedPosCodeSQL(this.dataBase);
		syohinCdCheck 		= getPreparedSyohinCdSQL(this.dataBase);

		// MM用
		syohinAftCheck2 = getPreparedAftCheckSQL2(this.dataBase);
		
	}

	/**
	 * TRテーブルのキーのセット処理
	 * @return 
	 */
	public void setKey(String[] key) {
		this.key = key;
	}

	/**
	 * 新規登録時の商品マスタチェック
	 * @return boolean
	 */
	public boolean insertSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {

		String syohin_cd 	= rs.getString("syohin_cd");
		String bunrui1_cd 	= rs.getString("bunrui1_cd");
		String yuko_dt 		= rs.getString("yuko_dt");

		if(yuko_dt == null) {
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}

		this.InsertFg = true;
		this.DeleteFg = false;

		boolean existsFg = false;

		setPreparedSyohinBefCheckSQL(syohinBefCheck, bunrui1_cd, syohin_cd, yuko_dt);
		
		ResultSet res = null;
		
		// リトライ処理
		for (int i = 0;i < retryCnt;i++) {
			try {
				res = syohinBefCheck.executeQuery();
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
		
		//登録する有効日以前のデータチェック
		if (res.next()) {
			String tmp_yuko = res.getString("yuko_dt");
			String tmp_del = res.getString("delete_fg");

			if (tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
				if (DateDiff.getDiffDays(tmp_yuko, yuko_dt) > 0 || tmp_yuko.trim().equals(yuko_dt)) {
					//データが存在する場合、削除データで有効日がExcel入力日付より過去もしくは当日のときは有効
					existsFg = false;
					if (tmp_yuko.trim().equals(yuko_dt)) {
						//削除データで有効日がExcel入力日と同一の場合、は物理削除の要否を「必要」に設定
						this.DeleteFg = true;
					}
				}else{
					//削除データだが、既存データがExcel入力日付より未来のものは無効
					existsFg = true;
				}
			}
			// 普通有効レコードがある場合、エラー
			else{
				existsFg = true;
			}
		} else {
			existsFg = false;
		}
		dataBase.closeResultSet(res);

		//登録する有効日より未来のデータチェック
		if (!existsFg) {
			setPreparedSyohinAftCheckSQL(syohinAftCheck, bunrui1_cd, syohin_cd, yuko_dt);
			for (int i = 0;i < retryCnt;i++) {
				try {
					res = syohinAftCheck.executeQuery();
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
	
			//未来のデータが存在する場合エラー
			if (res.next()) {
				existsFg = true;
			}
		}

		dataBase.closeResultSet(res);

		if (existsFg) {
			setPreparedMessageSQL("0009");
			pstmt.addBatch();
			return false;
		}

		if (this.DeleteFg) {
			setPreparedMessageSQL("0261");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * 更新時の商品マスタチェック
	 * @return boolean
	 */
	public boolean updateSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {
		
		String syohin_cd 	= rs.getString("syohin_cd");
		String bunrui1_cd 	= rs.getString("bunrui1_cd");	
		String yuko_dt 		= rs.getString("yuko_dt");
		
		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}
		
		String gyosyu_kb = rs.getString("excel_file_syubetsu");

		boolean existsFg = false; 	// データが存在するか
		boolean updateFg = true; 	// データを修正してよいか

		setPreparedSyohinBefCheckSQL(syohinBefCheck, bunrui1_cd, syohin_cd, yuko_dt);
		
		ResultSet res = null;
		// リトライ処理
		for (int i = 0;i < retryCnt;i++) {
			try {
				res = syohinBefCheck.executeQuery();
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

		//登録する有効日以前のデータチェック
		if (res.next()) {
			String tmp_yuko 	= res.getString("yuko_dt");
			String tmp_del 		= res.getString("delete_fg");
			String tmp_sinki 	= res.getString("sinki_toroku_dt");

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
				if (tmp_yuko.equals(yuko_dt)) {
					//有効日が同じ場合はUPDATE
					this.InsertFg = false;
				} else {
					//有効日が異なる場合はINSERT
					this.InsertFg = true;
				}

				//有効日が当日のものは当日に登録されたもののみ修正できる
				// 2016/10/25 T.Arimoto #2256対応（S)
//				if (yuko_dt.equals(MasterDT)) {
//					if (!tmp_sinki.equals(MasterDT)) {
//						updateFg = false;
//					}
//				}
				// 2016/10/25 T.Arimoto #2256対応（E)
			} else {
				existsFg = false;
			}
		}

		// 2016/10/24 T.Arimoto #2256対応（S)
		//登録する有効日より未来のデータチェック
//		if (existsFg && updateFg) {
//			setPreparedSyohinAftCheckSQL(syohinAftCheck, bunrui1_cd, syohin_cd, yuko_dt);			
//			for (int i = 0;i < retryCnt;i++) {
//				try {
//					res = syohinAftCheck.executeQuery();
//					break;
//				} catch (SQLException sqle) {
//					
//					if (sqle.getErrorCode() == this.DEAD_LOCK_ERROR) {
//						
//						if (i + 1 >= retryCnt) {
//							batchLog.getLog().info(BATCH_ID, BATCH_NA, "チェック処理が最大リトライ回数に達したため停止します。");
//							throw sqle;
//						}
//						
//						batchLog.getLog().info(BATCH_ID, BATCH_NA, "チェック処理に失敗したため" + waitTime / 1000 + "秒待機後にリトライします。" + (i + 1) + "回目");
//						try{
//							Thread.sleep(waitTime);
//						} catch (Exception e){}
//					} else {
//						throw sqle;
//					}
//				}
//			}
//			
//			// 
//			if (res.next()) {
//				//未来が存在する場合はエラー
//				updateFg = false;
//			} else {
//				updateFg = true;
//			}
//		}
		// 2016/10/24 T.Arimoto #2256対応（E)

		dataBase.closeResultSet(res);

		if (!existsFg) {
			//修正対象の商品が存在しない
			setPreparedMessageSQL("0011");
			pstmt.addBatch();
			return false;
		}

		if (!updateFg) {
			//修正不可
			setPreparedMessageSQL("0012");
			pstmt.addBatch();
			return false;
		}

		//未来データは2件まで有効
		// 2016/10/24 T.Arimoto #2256対応（S) オンライン日付にてInsert可能とする対応
//		if (InsertFg) {
		if (InsertFg && !yuko_dt.equals(MasterDT) ) {
			// Insert処理でオンライン日付以外の場合
			// 2016/10/24 T.Arimoto #2256対応（E)
			setPreparedSyohinCountSQL(syohinCount, bunrui1_cd, syohin_cd);
			ResultSet rset = syohinCount.executeQuery();

			if (rset.next()) {
				int icount = rset.getInt("count");
				dataBase.closeResultSet(rset);

				if (gyosyu_kb.equals("FRE")) {
					if (icount > 9) {
						setPreparedMessageSQL("0111");
						pstmt.addBatch();
						return false;
					}
				} else {
					if (icount > 1) {
						setPreparedMessageSQL("0015");
						pstmt.addBatch();
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 削除時の商品マスタチェック
	 * @return boolean
	 */
	public boolean deleteSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {
		
		String syohin_cd 	= rs.getString("syohin_cd");
		String hanku_cd 	= rs.getString("bunrui1_cd");
		String yuko_dt 		= rs.getString("yuko_dt");

		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}

		boolean existsFg = false; 	// データが存在するか
		boolean deleteFg = true; 	// データを削除してよいか

		setPreparedSyohinBefCheckSQL(syohinBefCheck, hanku_cd, syohin_cd, yuko_dt);

		// リトライ
		ResultSet res = null;
		for (int i = 0;i < retryCnt;i++) {
			try {
				res = syohinBefCheck.executeQuery();
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
		
		//登録する有効日以前のデータチェック
		if (res.next()) {
			String tmp_yuko 	= res.getString("yuko_dt");
			String tmp_del 		= res.getString("delete_fg");
			String tmp_sinki 	= res.getString("sinki_toroku_dt");

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
				this.YukoDt = tmp_yuko;

				if (tmp_yuko.equals(yuko_dt)) {
					//有効日が同じ場合はUPDATE
					this.InsertFg = false;
				} else {
					//有効日が異なる場合はINSERT
					this.InsertFg = true;
				}

				//有効日が当日のものは当日に登録されたもののみ修正できる
				if (yuko_dt.equals(MasterDT)) {
					if (!tmp_sinki.equals(MasterDT)) {
						deleteFg = false;
					}
				}
			} else {
				existsFg = false;
			}
		} else {
			existsFg = false;
		}
		// 2017/02/03 T.Arimoto #1174対応（S)
		dataBase.closeResultSet(res);
		// 2017/02/03 T.Arimoto #1174対応（E)

		//登録する有効日より未来のデータチェック
		if (existsFg && deleteFg) {
			setPreparedSyohinAftCheckSQL(syohinAftCheck, hanku_cd, syohin_cd, yuko_dt);
			
			for (int i = 0;i < retryCnt;i++) {
				try {
					res = syohinAftCheck.executeQuery();
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
			
			if (res.next()) {
				//未来が存在する場合エラー
				deleteFg = false;
			} else {
				deleteFg = true;
			}
		}

		dataBase.closeResultSet(res);

		if (!existsFg) {
			//削除対象の商品が存在しない
			setPreparedMessageSQL("0013");
			pstmt.addBatch();
			return false;
		}

		if (!deleteFg) {
			//削除不可
			setPreparedMessageSQL("0014");
			pstmt.addBatch();
			return false;
		}

		//未来データは2件まで有効
		if (InsertFg) {
			setPreparedSyohinCountSQL(syohinCount, hanku_cd, syohin_cd);
			ResultSet rset = syohinCount.executeQuery();

			if (rset.next()) {
				int icount = rset.getInt("count");
				dataBase.closeResultSet(rset);

				// 生鮮の場合は10件まで有効--
				if (rs.getString("excel_file_syubetsu").equals("FRE")) {
					if (icount > 9) {
						setPreparedMessageSQL("0111");
						pstmt.addBatch();
						return false;
					}
				} else {
					if (icount > 1) {
						setPreparedMessageSQL("0015");
						pstmt.addBatch();
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 品種コードがCTFマスタに存在するかのチェック
	 * １：CTFに存在するか
	 * ２：商品体系マスタに登録されているか
	 * ３：権限チェック
	 * @return boolean
	 */
	public boolean hinsyuCheck(ResultSet rs, String gyosyu_cd) throws SQLException {
		String hinsyu_ck = rs.getString("hinsyu_ck"); //CTFマスタの品種コード
		String hanku_cd = rs.getString("hanku_cd"); //入力販区コード
		String hanku_cd2 = rs.getString("hanku_cd2"); //品種より求めた販区コード
		String d_gyosyu_cd = rs.getString("d_gyosyu_cd"); //大業種（商品体系マスタ）
		String s_gyosyu_cd = rs.getString("s_gyosyu_cd"); //小業種（商品体系マスタ）

		if (hinsyu_ck == null) {
			//CTFに存在しない
			setPreparedMessageSQL("0004");
			pstmt.addBatch();
			return false;
		}

		if (hanku_cd2 == null) {
			//商品体系マスタに存在しない
			setPreparedMessageSQL("0004");
			pstmt.addBatch();
			return false;
		} else {
			if ((hanku_cd != null && !hanku_cd2.equals(hanku_cd))) {
				//入力販区と品種から求めた販区が異なる
				setPreparedMessageSQL("0082");
				pstmt.addBatch();
				return false;
			}
		}

		//衣料・住居の場合
		if (gyosyu_cd.equals("01") || gyosyu_cd.equals("02")) {
			if (!gyosyu_cd.equals(d_gyosyu_cd)) {
				//権限エラー
				setPreparedMessageSQL("0005");
				pstmt.addBatch();
				return false;
			}
		} else if (gyosyu_cd.equals("03")) {
			//加工食品の場合
//			↓↓仕様変更（2005.09.27）↓↓
//			if (!gyosyu_cd.equals(d_gyosyu_cd) || !s_gyosyu_cd.equals("0087")) {
			if (!gyosyu_cd.equals(d_gyosyu_cd) || !(s_gyosyu_cd.equals("0087") || s_gyosyu_cd.equals("0088"))) {
//			↑↑仕様変更（2005.09.27）↑↑
				//権限エラー
				setPreparedMessageSQL("0005");
				pstmt.addBatch();
				return false;
			}
		} else if (gyosyu_cd.equals("04")) {
			//生鮮の場合（食品は大業種を"03"に変換している）
//			↓↓仕様変更（2005.09.27）↓↓
//			if (!"03".equals(d_gyosyu_cd) || s_gyosyu_cd.equals("0087")) {
			if (!"03".equals(d_gyosyu_cd) || (s_gyosyu_cd.equals("0087") || s_gyosyu_cd.equals("0088"))) {
//			↑↑仕様変更（2005.09.27）↑↑
				//権限エラー
				setPreparedMessageSQL("0005");
				pstmt.addBatch();
				return false;
			}
		}

		return true;
	}

	/**
	 * 販区コードチェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hankuCheck(ResultSet rs) throws SQLException {
		String hanku_ck = rs.getString("hanku_ck"); //CTFマスタのコード

		if (hanku_ck == null) {
			//CTFに存在しない
			setPreparedMessageSQL("0006");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * 有効日のチェック
	 * １：システム日付+1<=有効日<=システム日付+1年
	 * →変更
	 * １：システム日付<=有効日<=システム日付+1年
	 * @return boolean
	 */
	public boolean yukoDtCheck(ResultSet rs) throws SQLException {
		String yuko_dt = rs.getString("yuko_dt"); //有効日
		//		String startDt = DateChanger.addDate(MasterDT, 1);
		String startDt = MasterDT;
		String endDt = DateChanger.addYear(MasterDT, 1);

		if (DateDiff.getDiffDays(startDt, yuko_dt) >= 0 && DateDiff.getDiffDays(yuko_dt, endDt) >= 0) {

		} else {
			setPreparedMessageSQL("0008");
			pstmt.addBatch();
			return false;
		}
		return true;
	}

	/**
	 * 有効日のチェック
	 * １：システム日付+1<=有効日 - リードタイム<=システム日付+1年
	 * @return boolean
	 */
	public boolean yukoDtCheck2(ResultSet rs) throws SQLException {
		String rtime1 = rs.getString("ten_nohin_rtime_1_kb");
		String rtime2 = rs.getString("ten_nohin_rtime_2_kb");
		String rtime3 = rs.getString("ten_nohin_rtime_3_kb");
		int iRtime1 = 0;
		int iRtime2 = 0;
		int iRtime3 = 0;
		int iRtime = 0;

		//発注パターンからリードタイムが求められない場合は、リードタイム２とする
		if ((rtime1 == null || rtime1.equals("")) && (rtime2 == null || rtime2.equals("")) && (rtime3 == null || rtime3.equals(""))) {
			iRtime = 2;
		} else {
			if (rtime1 != null && rtime1.trim().length() > 0) {
				iRtime1 = Integer.parseInt(rtime1.trim());
			}

			if (rtime2 != null && rtime2.trim().length() > 0) {
				iRtime2 = Integer.parseInt(rtime2.trim());
			}

			if (rtime3 != null && rtime3.trim().length() > 0) {
				iRtime3 = Integer.parseInt(rtime3.trim());
			}

			if (iRtime1 >= iRtime2) {
				iRtime = iRtime1;
			} else {
				iRtime = iRtime2;
			}

			if (iRtime >= iRtime3) {

			} else {
				iRtime = iRtime3;
			}
		}

		String wMasterDT = DateChanger.addDate(MasterDT, iRtime);

		String yuko_dt = rs.getString("yuko_dt"); //有効日
		String startDt = DateChanger.addDate(wMasterDT, 1);
		String endDt = DateChanger.addYear(wMasterDT, 1);

		if (DateDiff.getDiffDays(startDt, yuko_dt) >= 0 && DateDiff.getDiffDays(yuko_dt, endDt) >= 0) {

		} else {
			setPreparedMessageSQL("0008");
			pstmt.addBatch();
			return false;
		}
		return true;
	}

	/**
	 * 品目コードチェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hinmokuCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hinmoku_cd", "hinmoku_ck", "0016");

		return checkFg;
	}

	/**
	 * POSコードチェック
	 * １：同一販区でPOSコードが重複していないか
	 * @return boolean
	 */
	public boolean syohin2CdCheck(ResultSet rs) throws SQLException {
		String hanku_cd = rs.getString("hanku_cd2");
		String syohin_cd = rs.getString("syohin_cd");
		String yuko_dt = rs.getString("yuko_dt");
		String syohin_2_cd = rs.getString("syohin_2_cd");

		//"２１"＋空白はチェック対象外
		if (syohin_2_cd != null && !(syohin_2_cd.startsWith(mst000101_ConstDictionary.INSTORE_21) && syohin_2_cd.trim().length() == 2)) {
			setPreparedPosCodeSQL(poscodeCheck, hanku_cd, syohin_cd, yuko_dt, syohin_2_cd);
			ResultSet res = poscodeCheck.executeQuery();

			if (res.next()) {
				int iCount = res.getInt("count");
				dataBase.closeResultSet(res);

				if (iCount > 0) {
					setPreparedMessageSQL("0106");
					pstmt.addBatch();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 商品コードチェック
	 * １：食品（大業種）で商品コードが重複していないか
	 * @return boolean
	 */
	public boolean syohinCdCheck(ResultSet rs) throws SQLException {
		String hanku_cd = rs.getString("hanku_cd2");
		String syohin_cd = rs.getString("syohin_cd");
		String yuko_dt = rs.getString("yuko_dt");

		setPreparedSyohinCdSQL(syohinCdCheck, hanku_cd, syohin_cd, yuko_dt);
		ResultSet res = syohinCdCheck.executeQuery();

		if (res.next()) {
			int iCount = res.getInt("count");
			dataBase.closeResultSet(res);

			if (iCount > 0) {
				setPreparedMessageSQL("0107");
				pstmt.addBatch();
				return false;
			}
		}

		return true;
	}

	/**
	 * 品種コードと商品コードのチェック(04コード)
	 * @return
	 */
	public boolean syohinInstoreCheck(ResultSet rs) throws SQLException {
		String syohin_cd = rs.getString("syohin_cd");
		String hinsyu_cd = rs.getString("hinsyu_cd");
		if(syohin_cd.substring(0,4).equals(mst000101_ConstDictionary.INSTORE_04)){
			if(!syohin_cd.substring(4,8).equals(hinsyu_cd)){
				setPreparedMessageSQL("0112");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * ブランドコードチェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean blandCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "bland_cd", "bland_ck", "0018");

		return checkFg;
	}

	/**
	 * 輸入品区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean yunyuhinCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "yunyuhin_kb", "yunyuhin_ck", "0019");

		return checkFg;
	}

	/**
	 * 産地コードチェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean santiCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "santi_cd", "santi_ck", "0020");

		return checkFg;
	}

	/**
	 * 納品温度帯チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean nohinOndoCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "nohin_ondo_kb", "nohin_ondo_ck", "0021");

		return checkFg;
	}

	/**
	 * 店舗発注開始・終了日のチェック
	 * @return boolean
	 */
	public boolean hachuDtCheck(ResultSet rs) throws SQLException {
		String ten_hachu_st_dt = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
		String ten_hachu_ed_dt = rs.getString("ten_hachu_ed_dt"); //店舗発注終了日

		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(ten_hachu_ed_dt) && !ten_hachu_ed_dt.equals("99999999")) {
			if (DateDiff.getDiffDays(ten_hachu_st_dt, ten_hachu_ed_dt) < 0) {
				//日付の大小関係エラー
				setPreparedMessageSQL("0022");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 原価単価のチェック（副資材チェック有り）
	 * @return boolean
	 */
	public boolean gentankaCheck(ResultSet rs) throws SQLException {
		String hukusizai_ck = rs.getString("hukusizai_ck"); //副資材コード
		String tmp = rs.getString("gentanka_vl"); //原単価

		if (isNotBlank(tmp)) {
			double gentanka_vl = Double.parseDouble(tmp.trim());

			if (hukusizai_ck == null && gentanka_vl <= 0) {
				//副資材品種以外で０の場合エラー
				setPreparedMessageSQL("0023");
				pstmt.addBatch();
				return false;
			}
		}

		return true;
	}

	/**
	 * 売価単価のチェック（副資材チェック有り）
	 * @return boolean
	 */
	public boolean baitankaCheck(ResultSet rs) throws SQLException {
		String hukusizai_ck = rs.getString("hukusizai_ck"); //副資材コード
		String tmp = rs.getString("baitanka_vl"); //売単価

		//		if (tmp != null && tmp.trim().length() > 0) {
		if (isNotBlank(tmp)) {
			double baitanka_vl = Double.parseDouble(tmp.trim());
		
			if (hukusizai_ck == null && baitanka_vl <= 0) {
				//副資材品種以外で０の場合エラー
				setPreparedMessageSQL("0024");
				pstmt.addBatch();
				return false;
			}
		}

		return true;
	}

	/**
	 * 原価・売価のチェック
	 * @return boolean
	 */
	public boolean tankaCheck(ResultSet rs, boolean hukusizaiFg) throws SQLException {
		String tmp = rs.getString("gentanka_vl"); //原価単価
		double gentanka_vl = 0;

		if (tmp != null && tmp.trim().length() > 0) {
			gentanka_vl = Double.parseDouble(tmp.trim());
		}

		tmp = rs.getString("baitanka_vl"); //売価単価
		double baitanka_vl = 0;

		if (tmp != null && tmp.trim().length() > 0) {
			baitanka_vl = Double.parseDouble(tmp.trim());
		}

		if (hukusizaiFg) {
			//副資材がある業種のみチェック
			if (gentanka_vl == 0 && baitanka_vl != 0) {
				//原単価が０の場合、売単価 が０以外の場合エラー
				setPreparedMessageSQL("0096");
				pstmt.addBatch();
				return false;
			}
		}

		if (gentanka_vl > 0 && baitanka_vl > 0) {
			double wariai = NumberUtility.round(gentanka_vl / baitanka_vl, 4);

			if (wariai <= 0.09) {
				//原単価/売単価 <= 0.09の場合エラー
				setPreparedMessageSQL("0025");
				pstmt.addBatch();
				return false;
			}

			if (wariai >= 11) {
				//原単価/売単価 >= 11の場合エラー
				setPreparedMessageSQL("0026");
				pstmt.addBatch();
				return false;
			}
		}

		return true;
	}

	/**
	 * 定貫区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean teikanCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "teikan_kb", "teikan_ck", "0027");

		return checkFg;
	}

	/**
	 * EOS区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean eosCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "eos_kb", "eos_ck", "0028");

		return checkFg;
	}

	/**
	 * EOS区分と店舗発注開始日
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean eosTenCheck(ResultSet rs) throws SQLException {
		String eos_kb = rs.getString("eos_kb");

		if (isNotBlank(eos_kb)) {
			String ten_hachu_st_dt = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
			String ten_hachu_ed_dt = rs.getString("ten_hachu_ed_dt"); //店舗発注終了日

			//EOS区分が"1"または"3"の場合
			if (eos_kb.equals(mst001201_EosKbDictionary.EOS_TAISYO.getCode()) || eos_kb.equals(mst001201_EosKbDictionary.JIDO_TAISYOGAI.getCode())) {

				if (!isNotBlank(ten_hachu_st_dt) || !isNotBlank(ten_hachu_ed_dt)) {
					setPreparedMessageSQL("0101");
					pstmt.addBatch();
					return false;
				}
			/* 2006/01/17 EOS区分が２の時、開始日終了日チェックをしない*/
			/*
			} else if (eos_kb.equals(mst001201_EosKbDictionary.EOS_TAISYOGAI.getCode())) {
				//EOS区分が"2"の場合
				if (isNotBlank(ten_hachu_st_dt) || isNotBlank(ten_hachu_ed_dt)) {
					setPreparedMessageSQL("0102");
					pstmt.addBatch();
					return false;
				}
			*/
			/* 2006/01/17 削除ここまで*/
			}
		}

		return true;
	}

	/**
	 * 納品期限区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean nohinKigenKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "nohin_kigen_kb", "nohin_kigen_ck", "0029");

		return checkFg;
	}

	/**
	 * 納品期限チェック
	 * １：納品期限は両方入力か未入力
	 * @return boolean
	 */
	public boolean nohinKigenCheck(ResultSet rs) throws SQLException {
		String nohin_kigen_qt = rs.getString("nohin_kigen_qt");
		String nohin_kigen_kb = rs.getString("nohin_kigen_kb");

		if (!isNotBlank(nohin_kigen_qt) && isNotBlank(nohin_kigen_kb)) {
			setPreparedMessageSQL("0097");
			pstmt.addBatch();
			return false;
		}

		if (isNotBlank(nohin_kigen_qt) && !isNotBlank(nohin_kigen_kb)) {
			setPreparedMessageSQL("0097");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * 発注停止区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hachuTeisiCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hachu_teisi_kb", "hachu_teisi_ck", "0030");

		return checkFg;
	}

	/**
	 * 仕入先コードチェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean siiresakiCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "siiresaki_cd", "siiresaki_ck", "0031");

		return checkFg;
	}

	/**
	 * 仕入先コードチェック（グロサリ用）
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean siiresakiGroCheck(ResultSet rs) throws SQLException {
		String ten_siiresaki_kanri_cd = rs.getString("ten_siiresaki_kanri_cd");
		
		if(isNotBlank(ten_siiresaki_kanri_cd)){
			//店別仕入先が入力されている場合は仕入先コードのチェックを行わない
			return true;
		}
		
		boolean checkFg = rNamectfCheck(rs, "siiresaki_cd", "siiresaki_ck", "0031");

		return checkFg;
	}
	
	/**
	 * 代表配送先コードチェック
	 * １：1店舗でも登録されているか
	 * @return boolean
	 */
	public boolean daihyoHaisosakiCheck(ResultSet rs) throws SQLException {
		String daihyo_haiso_cd = rs.getString("daihyo_haiso_cd");
		int count = rs.getInt("daihyo_haiso_ck");

		if (isNotBlank(daihyo_haiso_cd)) {
			if (count == 0) {
				setPreparedMessageSQL("0032");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 店別仕入先管理コードチェック
	 * １：店別仕入先マスタに登録されているか
	 * @return boolean
	 */
	public boolean tenSiiresakiKanriCheck(ResultSet rs) throws SQLException {
		String ten_siiresaki_kanri_cd = rs.getString("ten_siiresaki_kanri_cd");
		String ten_siiresaki_kanri_ck = rs.getString("ten_siiresaki_kanri_ck");

		if (isNotBlank(ten_siiresaki_kanri_cd)) {
			if (ten_siiresaki_kanri_ck == null) {
				setPreparedMessageSQL("0033");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 店別仕入先管理コードと仕入先コードの関係チェック
	 * １：店別仕入先（代表）の仕入先と商品マスタの仕入先が同じかチェック
	 * @return boolean
	 */
	public boolean daihyoSiiresakiCheck(ResultSet rs) throws SQLException{
		String siiresaki_cd = rs.getString("siiresaki_cd"); //仕入先コード
		String ten_siiresaki_kanri_ck = rs.getString("ten_siiresaki_kanri_ck"); //店別仕入先マスタの仕入先コード
		
		if(isNotBlank(siiresaki_cd) && isNotBlank(ten_siiresaki_kanri_ck)){
			
			if(!siiresaki_cd.equals(ten_siiresaki_kanri_ck)){
				setPreparedMessageSQL("0110");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 相場商品区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sobaSyohinCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "soba_syohin_kb", "soba_syohin_ck", "0034");

		return checkFg;
	}

	/**
	 * ①便チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean bin1KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "bin_1_kb", "bin_1_ck", "0035");

		return checkFg;
	}

	/**
	 * ①発注パターン区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hachuPattern1KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_1_kb", "hachu_pattern_1_ck", "0036");

		return checkFg;
	}

	/**
	 * ②便チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean bin2KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "bin_2_kb", "bin_2_ck", "0037");

		return checkFg;
	}

	/**
	 * ②発注パターン区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hachuPattern2KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_2_kb", "hachu_pattern_2_ck", "0038");

		return checkFg;
	}

	/**
	 * ③便チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean bin3KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "bin_3_kb", "bin_3_ck", "0039");

		return checkFg;
	}

	/**
	 * ③発注パターン区分チェック
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hachuPattern3KbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_3_kb", "hachu_pattern_3_ck", "0040");

		return checkFg;
	}

	/**
	 * ①②③便チェック
	 * １：同じ便が存在するか
	 * @return boolean
	 */
	public boolean binKbCheck(ResultSet rs) throws SQLException {
		String bin_1_kb = rs.getString("bin_1_kb"); //①便区分
		String bin_2_kb = rs.getString("bin_2_kb"); //②便区分
		String bin_3_kb = rs.getString("bin_3_kb"); //③便区分

		boolean checkFg = false;

		if (isNotBlank(bin_1_kb) && isNotBlank(bin_2_kb)) {
			if (bin_1_kb.equals(bin_2_kb)) {
				checkFg = true;
			}
		}

		if (!checkFg && isNotBlank(bin_1_kb) && isNotBlank(bin_3_kb)) {
			if (bin_1_kb.equals(bin_3_kb)) {
				checkFg = true;
			}
		}

		if (!checkFg && isNotBlank(bin_2_kb) && isNotBlank(bin_3_kb)) {
			if (bin_2_kb.equals(bin_3_kb)) {
				checkFg = true;
			}
		}

		if (checkFg) { //同じ便が指定されている
			setPreparedMessageSQL("0041");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * ①②③発注パターン区分チェック
	 * １：同じ発注パターンが存在するか
	 * @return boolean
	 */
	public boolean hachuPatternKbCheck(ResultSet rs) throws SQLException {
		String hachu_pattern_1_kb = rs.getString("hachu_pattern_1_kb"); //①発注パターン区分
		String hachu_pattern_2_kb = rs.getString("hachu_pattern_2_kb"); //②発注パターン区分
		String hachu_pattern_3_kb = rs.getString("hachu_pattern_3_kb"); //②発注パターン区分

		boolean checkFg = false;

		if (isNotBlank(hachu_pattern_1_kb) && isNotBlank(hachu_pattern_2_kb)) {
			if (hachu_pattern_1_kb.equals(hachu_pattern_2_kb)) {
				checkFg = true;
			}
		}

		if (!checkFg && isNotBlank(hachu_pattern_1_kb) && isNotBlank(hachu_pattern_3_kb)) {
			if (hachu_pattern_1_kb.equals(hachu_pattern_3_kb)) {
				checkFg = true;
			}
		}

		if (!checkFg && isNotBlank(hachu_pattern_2_kb) && isNotBlank(hachu_pattern_3_kb)) {
			if (hachu_pattern_2_kb.equals(hachu_pattern_3_kb)) {
				checkFg = true;
			}
		}

		if (checkFg) { //同じ発注パターンが指定されている
			setPreparedMessageSQL("0042");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * センタ納品リードタイム
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean cNohinRtimeKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "c_nohin_rtime_kb", "c_nohin_rtime_ck", "0043");

		return checkFg;
	}

	/**
	 * 商品区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean syohinKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "syohin_kb", "syohin_ck", "0044");

		return checkFg;
	}

	/**
	 * 物流区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean buturyuKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "buturyu_kb", "buturyu_ck", "0045");

		return checkFg;
	}

	/**
	 * 横もち区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean yokomotiKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "yokomoti_kb", "yokomoti_ck", "0046");

		return checkFg;
	}

	/**
	 * 店在庫区分
	 * １：ディクショナリに存在するか
	 * @return boolean
	 */
	public boolean tenZaikoKbCheck(ResultSet rs) throws SQLException {
		String ten_zaiko_kb = rs.getString("ten_zaiko_kb");

		if (isNotBlank(ten_zaiko_kb)) {
			mst001901_MiseZaikoKbDictionary dictionary = mst001901_MiseZaikoKbDictionary.getStatus(ten_zaiko_kb);

			if (dictionary.equals(mst001901_MiseZaikoKbDictionary.UNKNOWN)) {
				//ディクショナリに存在しない
				setPreparedMessageSQL("0047");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 販売政策区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hanbaiSeisakuKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hanbai_seisaku_kb", "hanbai_seisaku_ck", "0048");

		return checkFg;
	}

	/**
	 * リベート対象フラグ
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean rebateFgCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "rebate_fg", "rebate_ck", "0049");

		return checkFg;
	}

	/**
	 * 販売開始・終了日のチェックと店舗発注開始・終了日の関連チェック
	 * @return boolean
	 */
	public boolean hanbaiDtCheck(ResultSet rs) throws SQLException {
		String hanbai_st_dt = rs.getString("hanbai_st_dt"); //販売開始日
		String hanbai_ed_dt = rs.getString("hanbai_ed_dt"); //販売終了日
		boolean dateFg = true;

		if (isNotBlank(hanbai_st_dt) && isNotBlank(hanbai_ed_dt) && !hanbai_ed_dt.equals("99999999")) {
			if (DateDiff.getDiffDays(hanbai_st_dt, hanbai_ed_dt) < 0) {
				//日付の大小関係エラー
				setPreparedMessageSQL("0050");
				pstmt.addBatch();
				return false;
			}
		}

		String ten_hachu_st_dt = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
		String ten_hachu_ed_dt = rs.getString("ten_hachu_ed_dt"); //店舗発注終了日

		//店舗発注開始日 >= 販売開始日
		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(hanbai_st_dt)) {
			if (DateDiff.getDiffDays(ten_hachu_st_dt, hanbai_st_dt) > 0) {
				//日付の大小関係エラー
				setPreparedMessageSQL("0099");
				pstmt.addBatch();
				dateFg = false;
			}
		}

		//店舗発注終了日 < 販売終了日
		if (isNotBlank(ten_hachu_ed_dt) && isNotBlank(hanbai_ed_dt) && !hanbai_ed_dt.equals("99999999")) {
			if (ten_hachu_ed_dt.equals("99999999")) {
				setPreparedMessageSQL("0100");
				pstmt.addBatch();
				dateFg = false;
			} else if (DateDiff.getDiffDays(ten_hachu_ed_dt, hanbai_ed_dt) <= 0) {
				//日付の大小関係エラー
				setPreparedMessageSQL("0100");
				pstmt.addBatch();
				dateFg = false;
			}
		}

		return dateFg;
	}

	/**
	 * 販売期限区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hanbaiLimitKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hanbai_limit_kb", "hanbai_limit_ck", "0051");

		return checkFg;
	}

	/**
	 * 販売期限チェック
	 * １：販売期限は両方入力か未入力
	 * @return boolean
	 */
	public boolean hanbaiLimitCheck(ResultSet rs) throws SQLException {
		String hanbai_limit_qt = rs.getString("hanbai_limit_qt");
		String hanbai_limit_kb = rs.getString("hanbai_limit_kb");

		if (!isNotBlank(hanbai_limit_qt) && isNotBlank(hanbai_limit_kb)) {
			setPreparedMessageSQL("0098");
			pstmt.addBatch();
			return false;
		}

		if (isNotBlank(hanbai_limit_qt) && !isNotBlank(hanbai_limit_kb)) {
			setPreparedMessageSQL("0098");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	/**
	 * 自動削除対象区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean autoDelKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "auto_del_kb", "auto_del_ck", "0052");

		return checkFg;
	}

	/**
	 * GOT無条件表示対象
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean gotMujyokenFgCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "got_mujyoken_fg", "got_mujyoken_ck", "0053");

		return checkFg;
	}

	/**
	 * GOT表示開始・終了月チェック
	 * １：開始終了が両方指定または未指定かのチェック
	 * @return boolean
	 */
	public boolean gotMMCheck(ResultSet rs) throws SQLException {
		String got_start_mm = rs.getString("got_start_mm"); //GOT表示開始月
		String got_end_mm = rs.getString("got_end_mm"); //GOT表示終了月

		if ((!isNotBlank(got_start_mm) && isNotBlank(got_end_mm)) || (isNotBlank(got_start_mm) && !isNotBlank(got_end_mm))) {
			setPreparedMessageSQL("0054");
			pstmt.addBatch();
			return false;
		}
		return true;
	}

	/**
	 * キーPLU対象
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean keypluFgCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "keyplu_fg", "keyplu_ck", "0055");

		return checkFg;
	}

	/**
	 * PC発行区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean pcKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "pc_kb", "pc_ck", "0056");

		return checkFg;
	}

	/**
	 * ユニットプライス単位区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean unitPriceTaniKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "unit_price_tani_kb", "unit_price_tani_ck", "0057");

		return checkFg;
	}

	/**
	 * ユニットプライスチェック
	 * １：すべて入力か未入力かのチェック
	 * @return boolean
	 */
	public boolean unitPriceCheck(ResultSet rs) throws SQLException {
		String unit_price_tani_kb = rs.getString("unit_price_tani_kb"); //ユニットプライス単位
		String unit_price_naiyoryo_qt = rs.getString("unit_price_naiyoryo_qt"); //ユニットプライス内容量
		String unit_price_kijun_tani_qt = rs.getString("unit_price_kijun_tani_qt"); //ユニットプライス基準単量

		if ((isNotBlank(unit_price_tani_kb) && isNotBlank(unit_price_naiyoryo_qt) && isNotBlank(unit_price_kijun_tani_qt) || (!isNotBlank(unit_price_tani_kb) && !isNotBlank(unit_price_naiyoryo_qt) && !isNotBlank(unit_price_kijun_tani_qt)))) {
			//全て入力されているか、未入力の場合はOK
		} else {
			setPreparedMessageSQL("0058");
			pstmt.addBatch();
			return false;
		}
		return true;
	}

	/**
	 * ユニットプライス値チェック
	 * １：すべて入力か未入力かのチェック
	 * ２：定貫区分=２（不定貫）の場合
	 * 　　『単位区分＝1(g)且つ基準単位量＝100』または
	 * 　　『単位区分＝2(kg)且つ内容量＝1且つ基準単位量＝1』であること。それ以外はエラー。
	 * @return boolean
	 */
	public boolean unitPriceCheck2(ResultSet rs) throws SQLException {
		String teikan_kb = rs.getString("teikan_kb"); //定貫区分
		String unit_price_tani_kb = rs.getString("unit_price_tani_kb"); //ユニットプライス単位
		String unit_price_naiyoryo_qt = rs.getString("unit_price_naiyoryo_qt"); //ユニットプライス内容量
		String unit_price_kijun_tani_qt = rs.getString("unit_price_kijun_tani_qt"); //ユニットプライス基準単量

		if (isNotBlank(unit_price_tani_kb) && isNotBlank(unit_price_naiyoryo_qt) && isNotBlank(unit_price_kijun_tani_qt)) {
			if (isNotBlank(teikan_kb) && teikan_kb.equals(mst001101_TeikanKbDictionary.FUTEIKAN.getCode())) {
				//不定貫の場合
				if (!(unit_price_tani_kb.equals(mst002501_UnitPriceKbDictionary.GRAM.getCode()) && unit_price_kijun_tani_qt.trim().equals("100")) && !(unit_price_tani_kb.equals(mst002501_UnitPriceKbDictionary.KILOGRAM.getCode()) && unit_price_naiyoryo_qt.trim().equals("1") && unit_price_kijun_tani_qt.trim().equals("1"))) {
					setPreparedMessageSQL("0059");
					pstmt.addBatch();
					return false;
				}
			}
		} else if (!isNotBlank(unit_price_tani_kb) && !isNotBlank(unit_price_naiyoryo_qt) && !isNotBlank(unit_price_kijun_tani_qt)) {

		} else {
			//全て入力されているか、未入力の場合以外はエラー
			setPreparedMessageSQL("0058");
			pstmt.addBatch();
			return false;
		}
		return true;
	}

	/**
	 * 品揃区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sinazoroeKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "shinazoroe_kb", "shinazoroe_ck", "0060");

		return checkFg;
	}

	/**
	 * 組数区分
	 * １：ディクショナリに存在するか
	 * @return boolean
	 */
	public boolean kumisuKbCheck(ResultSet rs) throws SQLException {
		String kumisu_kb = rs.getString("kumisu_kb");

		if (isNotBlank(kumisu_kb)) {
			mst000401_KumisuKbDictionary dictionary = mst000401_KumisuKbDictionary.getStatus(kumisu_kb);

			if (dictionary.equals(mst000401_KumisuKbDictionary.UNKNOWN)) {
				//ディクショナリに存在しない
				setPreparedMessageSQL("0061");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 値札区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean nefudaKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "nefuda_kb", "nefuda_ck", "0062");

		return checkFg;
	}

	/**
	 * シーズンコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean seasonCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "season_cd", "season_ck", "0063");

		return checkFg;
	}

	/**
	 * 服種コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hukusyuCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "hukusyu_cd", "hukusyu_ck", "0064");

		return checkFg;
	}

	/**
	 * スタイルコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean styleCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "style_cd", "style_ck", "0065");

		return checkFg;
	}

	/**
	 * シーンコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sceneCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "scene_cd", "scene_ck", "0066");

		return checkFg;
	}

	/**
	 * 性別コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sexCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "sex_cd", "sex_ck", "0067");

		return checkFg;
	}

	/**
	 * 年代コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean ageCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "age_cd", "age_ck", "0068");

		return checkFg;
	}

	/**
	 * 世代コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean generationCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "generation_cd", "generation_ck", "0069");

		return checkFg;
	}

	/**
	 * 素材コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sozaiCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "sozai_cd", "sozai_ck", "0070");

		return checkFg;
	}

	/**
	 * パターンコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean patternCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "pattern_cd", "pattern_ck", "0071");

		return checkFg;
	}

	/**
	 * 織り編みコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean oriamiCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "oriami_cd", "oriami_ck", "0072");

		return checkFg;
	}

	/**
	 * 付加機能コード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean hukaKinoCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "huka_kino_cd", "huka_kino_ck", "0073");

		return checkFg;
	}

	/**
	 * サイズコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sizeCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "size_cd", "size_ck", "0074");

		return checkFg;
	}

	/**
	 * カラーコード
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean colorCdCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "color_cd", "color_ck", "0075");

		return checkFg;
	}

	/**
	 * 酒税報告分類
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean syuzeiHokokuKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "syuzei_hokoku_kb", "syuzei_hokoku_ck", "0076");

		return checkFg;
	}

	/**
	 * 小属性１
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sZokusei1Check(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "s_zokusei_1_na", "s_zokusei_1_ck", "0077");

		return checkFg;
	}

	/**
	 * 小属性２
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sZokusei2Check(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "s_zokusei_2_na", "s_zokusei_2_ck", "0078");

		return checkFg;
	}

	/**
	 * 小属性３
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sZokusei3Check(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "s_zokusei_3_na", "s_zokusei_3_ck", "0079");

		return checkFg;
	}

	/**
	 * 小属性４
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sZokusei4Check(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "s_zokusei_4_na", "s_zokusei_4_ck", "0080");

		return checkFg;
	}

	/**
	 * 小属性５
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean sZokusei5Check(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "s_zokusei_5_na", "s_zokusei_5_ck", "0081");

		return checkFg;
	}

	/**
	 * CTFマスタに存在するかのチェック（共通部）
	 * @param ResultSet rs
	 * @param String col1 入力値が入っているカラム名
	 * @param String col2 CTFマスタのチェック結果のカラム名
	 * @param String msgNo エラー時のメッセージNo
	 * @return boolean
	 */
	public boolean rNamectfCheck(ResultSet rs, String col1, String col2, String msgNo) throws SQLException {
		String str1 = rs.getString(col1);
		String str2 = rs.getString(col2);

		if (isNotBlank(str1)) {
			if (str2 == null) {
				//CTFに存在しない
				setPreparedMessageSQL(msgNo);
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 結果メッセージ作成SQL
	 * @throws Exception
	 */
	public void setPreparedMessageSQL(String megNo) throws SQLException {

		messageId = megNo;

		int idx = 0;
		//取込日
		idx++;
		pstmt.setString(idx, key[0]);
		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);
		//受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);
		//受付SEQNo
		idx++;
		pstmt.setString(idx, key[3]);
		//シート種別
		idx++;
		pstmt.setString(idx, key[4]);
		//結果メッセージコード
		idx++;
		pstmt.setString(idx, megNo);
		//結果メッセージ
		idx++;
		pstmt.setString(idx, (String)map.get(megNo));
		
//		 ↓↓2006.06.28 jianglm カスタマイズ修正↓↓
		//作成年月日
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"));
		
		//作成者ID
		idx++;
		pstmt.setString(idx, key[5]);
		
		//更新年月日
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora"));
		
		//更新者ID
		idx++;
		pstmt.setString(idx, key[5]);
//		 ↑↑2006.06.28 jianglm カスタマイズ修正↑↑
		System.out.println("MSGSET " + key[0] + "," + key[1] + "," + key[2] + "," + key[3] + "," + key[4] + "," + key[5] + "," + megNo);
	}

	public String getKeys(){
		return key[0] + "," + key[1] + "," + key[2] + "," + key[3] + "," + key[4] + "," + key[5] + "," + messageId;
	}
	
	/**
	 * 商品マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedBefCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rs.yuko_dt as yuko_dt,");
		sql.append("  rs.sinki_toroku_dt as sinki_toroku_dt,");
		sql.append("  rs.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_syohin rs ");
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  yuko_dt =");
		sql.append("            (select");
		sql.append("               max(yuko_dt)");
		sql.append("             from");
		sql.append("               r_syohin sub");
		sql.append("             where");
		// #6620 DEL 2022.06.30 SIEU.D(S)
		// sql.append("               sub.bunrui1_cd = rs.bunrui1_cd AND");
		// #6620 DEL 2022.06.30 SIEU.D(E)
		sql.append("               sub.syohin_cd = rs.syohin_cd AND");
		sql.append("               sub.yuko_dt <= ?) ");
		sql.append("for update");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 商品マスタチェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinBefCheckSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String yuko_dt) throws SQLException {
		int idx = 0;

		//販区コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
	}

	/** ↓↓ add by kema 06.11.03 別部門で同じ商品コードがある場合エラーにする
	 * 商品マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedBumonCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append(" syohin_cd ");
		sql.append("from");
		sql.append("  r_syohin ");
		sql.append("where ");
		sql.append("  bunrui1_cd != ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 商品マスタチェックSQL
	 * @throws Exception
	 */
	public void setPreparedBumonCheckSQL(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd) throws SQLException {
		int idx = 0;

		//部門コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
	}
	//↑↑ add by kema 06.1.03


	/**
	 * 商品マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedAftCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rs.yuko_dt as yuko_dt,");
		sql.append("  rs.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_syohin rs ");
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  yuko_dt =");
		sql.append("            (select ");
		sql.append("               min(yuko_dt)");
		sql.append("             from");
		sql.append("               r_syohin sub");
		sql.append("             where");
		sql.append("               sub.bunrui1_cd = rs.bunrui1_cd AND");
		sql.append("               sub.syohin_cd = rs.syohin_cd AND");
		sql.append("               sub.yuko_dt > ?) ");
		sql.append("for update");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * 商品マスタチェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinAftCheckSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String yuko_dt) throws SQLException {
		int idx = 0;
		//販区コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
	}

	/**
	 * 商品マスタ未来データ数チェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedCountSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" count(*) as count ");
		sql.append("from");
		sql.append(" r_syohin ");
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append(" syohin_cd = ? and");
		sql.append(" yuko_dt > '").append(MasterDT).append("'");
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 商品マスタ未来データ数チェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinCountSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd) throws SQLException {
		int idx = 0;
		//販区コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
	}

	/**
	 * POSコードチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedPosCodeSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select");
		sql.append("    rs1.count1 + rs2.count2 count ");
		sql.append("from");
		sql.append("    (select");
		sql.append("        count(main.syohin_2_cd) count1");
		sql.append("    from");
		sql.append("        r_syohin main");
		sql.append("    where");
		sql.append("        main.bunrui1_cd = ? and");
		sql.append("        main.syohin_cd <> ? and");
		sql.append("        main.syohin_2_cd = ? and");
		sql.append("        main.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' and");
		sql.append("        main.yuko_dt = (select");
		sql.append("                            max(yuko_dt)");
		sql.append("                        from");
		sql.append("                            r_syohin sub");
		sql.append("                        where");
		sql.append("                            sub.bunrui1_cd = main.bunrui1_cd and");
		sql.append("                            sub.syohin_cd = main.syohin_cd and");
		sql.append("                            sub.yuko_dt <= ?");
		sql.append("                        )) rs1,");
		sql.append("    (select");
		sql.append("        count(main.syohin_2_cd) count2");
		sql.append("    from ");
		sql.append("        r_syohin main");
		sql.append("    where ");
		sql.append("        main.bunrui1_cd = ? and");
		sql.append("        main.syohin_cd <> ? and");
		sql.append("        main.syohin_2_cd = ? and");
		sql.append("        main.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' and");
		sql.append("        main.yuko_dt > ?");
		sql.append("    ) rs2");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * POSコードチェックSQL
	 * @throws Exception
	 */
	public void setPreparedPosCodeSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String yuko_dt, String pos_cd) throws SQLException {
		int idx = 0;
		//販区コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//POSコード
		idx++;
		pstmt.setString(idx, pos_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
		//販区コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//POSコード
		idx++;
		pstmt.setString(idx, pos_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
	}

	/**
	 * 商品コードチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedSyohinCdSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select");
		sql.append("    rs1.count1 + rs2.count2 count ");
		sql.append("from");
		sql.append("    (select");
		sql.append("        count(main.syohin_cd) count1");
		sql.append("    from");
		sql.append("        r_syohin main");
		sql.append("    where");
		sql.append("        main.syohin_cd = ? and");
		sql.append("        main.gyosyu_kb in ('").append(mst000601_GyoshuKbDictionary.FRE.getCode()).append("','").append(mst000601_GyoshuKbDictionary.GRO.getCode()).append("') and");
		sql.append("        main.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' and");
		sql.append("        main.yuko_dt = (select");
		sql.append("                            max(yuko_dt)");
		sql.append("                        from");
		sql.append("                            r_syohin sub");
		sql.append("                        where");
		// #6620 DEL 2022.06.30 SIEU.D(S)
		// sql.append("                            sub.bunrui1_cd = main.bunrui1_cd and");
		// #6620 DEL 2022.06.30 SIEU.D(E)
		sql.append("                            sub.syohin_cd = main.syohin_cd and");
		sql.append("                            sub.yuko_dt <= ?");
		sql.append("                        )) rs1,");
		sql.append("    (select");
		sql.append("        count(main.syohin_cd) count2");
		sql.append("    from");
		sql.append("        r_syohin main");
		sql.append("    where");
		sql.append("        main.syohin_cd = ? and");
		sql.append("        main.gyosyu_kb in ('").append(mst000601_GyoshuKbDictionary.FRE.getCode()).append("','").append(mst000601_GyoshuKbDictionary.GRO.getCode()).append("') and");
		sql.append("        main.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' and");
		sql.append("        main.yuko_dt > ?");
		sql.append("    ) rs2");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 商品コードチェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinCdSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String yuko_dt) throws SQLException {
		int idx = 0;

		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
	}

	private boolean isNotBlank(String val) {
		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
			return true;
		}
		return false;
	}


//	↓↓(2005.12.21) 値札、組数区分チェック追加
	/**
	 * 組数区分
	 * １：ディクショナリに存在するか
	 * @return boolean
	 */
	public boolean assortKumisuKbCheck(ResultSet rs) throws SQLException {
		String kumisu_kb = rs.getString("assort_kumisu_kb");

		if (isNotBlank(kumisu_kb)) {
			mst000401_KumisuKbDictionary dictionary = mst000401_KumisuKbDictionary.getStatus(kumisu_kb);

			if (dictionary.equals(mst000401_KumisuKbDictionary.UNKNOWN)) {
				//ディクショナリに存在しない
				setPreparedMessageSQL("0061");
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 値札区分
	 * １：CTFマスタに存在するか
	 * @return boolean
	 */
	public boolean assortNefudaKbCheck(ResultSet rs) throws SQLException {

		boolean checkFg = rNamectfCheck(rs, "assort_nefuda_kb", "assort_nefuda_ck", "0062");

		return checkFg;
	}
//	↑↑(2005.12.21) 値札、組数区分チェック追加

	
	
	/**
	 * 予約取消時の商品マスタチェック
	 * @return boolean
	 */
	public boolean torikesiSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {
		
		String 		syohin_cd 		= rs.getString("syohin_cd");
		String 		hanku_cd 		= rs.getString("bunrui1_cd");
		String 		yuko_dt 		= rs.getString("yuko_dt");
		String 		rs_syohin_cd 	= rs.getString("s_syohin_cd");
		ResultSet 	res 			= null;

		boolean deleteFg = true; //データを削除してよいか

		// 取消対象のレコードが存在するか
		if (rs_syohin_cd == null || rs_syohin_cd.trim().length() <= 0) {

			//対象の商品が存在しない
			setPreparedMessageSQL("0013");
			pstmt.addBatch();
			return false;
		}
		
		// 2016/10/26 T.Arimoto #2256対応（S)
		//登録する有効日より未来のデータチェック
//		setPreparedSyohinAftCheckSQL(syohinAftCheck, hanku_cd, syohin_cd, yuko_dt);
//		res = executeQueryRetry(syohinAftCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);
//			
//		if (res.next()) {
//			//未来が存在する場合エラー
//			deleteFg = false;
//		} else {
//			deleteFg = true;
//		}
//
//		dataBase.closeResultSet(res);

		if( yuko_dt.equals(MasterDT) ){
			// オンライン日付の場合は、予約取消不可
			deleteFg = false;
		}
		// 2016/10/26 T.Arimoto #2256対応（E)

		if (!deleteFg) {
			//取消不可
			setPreparedMessageSQL("0301");
			pstmt.addBatch();
			return false;
		}

		//登録する有効日より未来のデータチェック
		setPreparedSyohinAftCheckSQL2(syohinAftCheck2, hanku_cd, syohin_cd, yuko_dt);
		res = executeQueryRetry(syohinAftCheck2, BATCH_ID, BATCH_NA, waitTime, retryCnt);
			
		if (res.next()) {
			if (res.getString("YUKO_DT").equals(yuko_dt) && !res.getString("DELETE_FG").equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				deleteFg = true;
			} else {
				deleteFg = false;
			}
		} else {
			deleteFg = true;
		}

		dataBase.closeResultSet(res);

		if (!deleteFg) {
			//取消不可
			setPreparedMessageSQL("0302");
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	public boolean getInsertFg() {
		return this.InsertFg;
	}

	public boolean getDeleteFg() {
		return this.DeleteFg;
	}

	public String getYukoDt() {
		return this.YukoDt;
	}

	private ResultSet executeQueryRetry(PreparedStatement pstmt, String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt)  throws SQLException {
		ResultSet res = null;
		
		for (int i = 0;i < retryCnt;i++) {
			try {
				res = pstmt.executeQuery();
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

	
//	/**
//	 * 商品マスタチェック用PreparedStatement
//	 * @throws
//	 */
//	public PreparedStatement getPreparedBefCheckSQL2(MasterDataBase dataBase) throws SQLException {
//		StringBuffer sql = new StringBuffer();
//
//		sql.append("SELECT S.YUKO_DT         AS SYOHIN_YUKO_DT, ");
//		sql.append("       S.DELETE_FG       AS SYOHIN_DELETE_FG, ");
//		sql.append("       S.SINKI_TOROKU_DT AS SYOHIN_SINKI_TOROKU_DT, ");
//		sql.append("       G.YUKO_DT         AS GIFT_YUKO_DT, ");
//		sql.append("       G.DELETE_FG       AS GIFT_DELETE_FG ");
//		sql.append("  FROM R_SYOHIN S ");
//		sql.append("  LEFT JOIN ");
//		sql.append("       R_GIFT_SYOHIN G");
//		sql.append("    ON G.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND G.SYOHIN_CD  = S.SYOHIN_CD ");
//		sql.append("   AND G.YUKO_DT    = S.YUKO_DT ");
//		sql.append(" WHERE S.BUNRUI1_CD = ? ");
//		sql.append("   AND S.SYOHIN_CD  = ? ");
//		sql.append("   AND S.YUKO_DT    = ");
//		sql.append("       (SELECT MAX(YUKO_DT) ");
//		sql.append("          FROM R_SYOHIN ");
//		sql.append("         WHERE BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("           AND SYOHIN_CD  = S.SYOHIN_CD ");
//		sql.append("           AND YUKO_DT   <= ? ");
//		sql.append("       ) ");
//		
//		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
//		return pstmt;
//	}
//
//	
//	/**
//	 * 商品マスタチェックSQL
//	 * @throws Exception
//	 */
//	public void setPreparedSyohinBefCheckSQL2(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String yuko_dt) throws SQLException {
//		int idx = 0;
//
//		//分類１コード
//		idx++;
//		pstmt.setString(idx, bunrui1_cd);
//		//商品コード
//		idx++;
//		pstmt.setString(idx, syohin_cd);
//		//有効日
//		idx++;
//		pstmt.setString(idx, yuko_dt);
//	}

	/**
	 * 商品マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedAftCheckSQL2(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rs.yuko_dt as yuko_dt,");
		sql.append("  rs.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_syohin rs ");
		sql.append("where ");
		sql.append("  bunrui1_cd <> ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  yuko_dt =");
		sql.append("            (select ");
		sql.append("               min(yuko_dt)");
		sql.append("             from");
		sql.append("               r_syohin sub");
		sql.append("             where");
		// #6620 DEL 2022.11.18 VU.TD (S)
		//sql.append("               sub.bunrui1_cd = rs.bunrui1_cd AND");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("               sub.syohin_cd = rs.syohin_cd AND");
		sql.append("               sub.yuko_dt >= ?) ");
		sql.append("for update");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * 商品マスタチェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinAftCheckSQL2(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String yuko_dt) throws SQLException {
		int idx = 0;

		//分類１コード
		idx++;
		pstmt.setString(idx, hanku_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
	}

}
