package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mdware.common.batch.log.BatchLog;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:ギフト商品マスタ生成バッチ共通チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/25<BR>
 * @version 1.01 2006/01/17 D.Matsumoto EOS区分が２の時、店舗発注開始日終了日のチェックはしない
 * @version 1.02 2006/03/07 D.Matsumoto 販区切り替え対応
 * @author shimoyama
 */

public class MB380009_CommonGiftSyohinCheck {

	//メッセージを保持するMap
	private Map map = null;

	//DBクラス
	private MasterDataBase dataBase = null;

	//メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;

	//商品マスタチェック用PreparedStatement
	private PreparedStatement syohinBefCheck = null;
	private PreparedStatement syohinAftCheck = null;
	private PreparedStatement syohinCount = null;

	
	//TRテーブルのキー
	private String[] key = null;

	//マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();

	//Insert or Updateを判断するフラグ
	private boolean InsertFg = true;

	//処理を行う必要があるかを判断するフラグ
	private boolean SyoriFg = true;

	//物理削除の要否を判断するフラグ
	private boolean DeleteFg = false;

	//Update時のキーになる有効日
	private String YukoDt = "";

	//商品マスタの世代を追加する必要があるかどうかのフラグ
	private boolean SyohinInsertFg = true;	
	
	private final int DEAD_LOCK_ERROR  = -913;
	private BatchLog batchLog = BatchLog.getInstance();
	
	// メッセージ登録時のID
	String messageId;

	
	public MB380009_CommonGiftSyohinCheck(PreparedStatement pstmt, Map map, MasterDataBase dataBase) throws SQLException {

		this.pstmt = pstmt;
		this.map = map;
		this.dataBase = dataBase;
		
		syohinCount 	= getPreparedCountSQL(this.dataBase);
		syohinBefCheck 	= getPreparedBefCheckSQL(this.dataBase);
		syohinAftCheck 	= getPreparedAftCheckSQL(this.dataBase);
		
	}

	/**
	 * TRテーブルのキーのセット処理
	 * @return 
	 */
	public void setKey(String[] key) {
		this.key = key;
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

		System.out.println("MSGSET " + key[0] + "," + key[1] + "," + key[2] + "," + key[3] + "," + key[4] + "," + key[5] + "," + megNo);
	}

	public String getKeys(){
		return key[0] + "," + key[1] + "," + key[2] + "," + key[3] + "," + key[4] + "," + key[5] + "," + messageId;
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
		// #6620 DEL 2022.11.18 VU.TD (S)
		//sql.append("               sub.bunrui1_cd = rs.bunrui1_cd AND");
		// #6620 DEL 2022.11.18 VU.TD (E)
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

	private boolean isBlank(String val) {
		if (val != null && val.trim().length() > 0) {
			return false;
		}
		return true;
	}


	/**
	 * 新規登録時の商品マスタチェック
	 * @return boolean
	 */
	public boolean insertSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {
		String syohin_cd  = rs.getString("SYOHIN_CD");
		String bunrui1_cd = rs.getString("BUNRUI1_CD");
		String yuko_dt    = rs.getString("YUKO_DT");
	    String SyohinDeleteFg = null;
	    String syohinYukoDt   = null;
		String errMessageCd = null;
		
		this.InsertFg = true;
		this.DeleteFg = false;
		this.SyohinInsertFg = false;
		
		setPreparedSyohinBefCheckSQL2(syohinBefCheck, bunrui1_cd, syohin_cd, yuko_dt);

		ResultSet res = executeQueryRetry(syohinBefCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);
		
		if (!res.next()) {
			
			// 商品マスタなし
			errMessageCd = "0083";

		} else {

			SyohinDeleteFg = res.getString("SYOHIN_DELETE_FG");
			syohinYukoDt   = res.getString("SYOHIN_YUKO_DT");

			if (isBlank(syohinYukoDt) || SyohinDeleteFg.equals(mst000801_DelFlagDictionary.IRU.getCode())) {

				// 商品マスタなし
				errMessageCd = "0083";
				
			} else {

				// 有効日が異なる場合は、商品の登録が必要
				if (!syohinYukoDt.equals(yuko_dt)) {
					this.SyohinInsertFg = true;
				}
				
				String tmp_yuko = res.getString("SUB_YUKO_DT");
				String tmp_DeleteFg = res.getString("SUB_DELETE_FG");
				
				if (!isBlank(tmp_DeleteFg) && tmp_DeleteFg.equals(mst000801_DelFlagDictionary.INAI.getCode())) {

					// 既にギフト商品マスタが登録されている
					errMessageCd = "0009";

				} else {

					//有効日が同じ場合
					if (!isBlank(tmp_yuko) && tmp_yuko.equals(yuko_dt)) {
						errMessageCd = "0009";
					}
					
					//登録する有効日より未来のデータチェック
					setPreparedSyohinAftCheckSQL(syohinAftCheck, bunrui1_cd, syohin_cd, yuko_dt);
					ResultSet rsAft = executeQueryRetry(syohinAftCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);
					
					//未来のデータが存在する場合エラー
					if (rsAft.next()) {
						errMessageCd = "0008";
					}
					dataBase.closeResultSet(rsAft);
				}
			}
		}

		dataBase.closeResultSet(res);

		if (errMessageCd != null) {
			setPreparedMessageSQL(errMessageCd);
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
		
		String SyohinYukoDt   = null;
		String SyohinDeleteFg = null;
		String errMessageCd = null;

		String syohin_cd = rs.getString("syohin_cd");
		String bunrui1_cd = rs.getString("bunrui1_cd");
		String yuko_dt = rs.getString("yuko_dt");

		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}

		this.SyohinInsertFg = false;

		this.DeleteFg = false;
		this.InsertFg = false;
		
		
		// 過去直近データ取得
		setPreparedSyohinBefCheckSQL(syohinBefCheck, bunrui1_cd, syohin_cd, yuko_dt);
		ResultSet res = executeQueryRetry(syohinBefCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);

		if (res.next()) {

			String tmp_yuko  = res.getString("SUB_YUKO_DT");
			String tmp_del   = res.getString("SUB_DELETE_FG");

			SyohinYukoDt   = res.getString("SYOHIN_YUKO_DT");
			SyohinDeleteFg = res.getString("SYOHIN_DELETE_FG");

			if (isBlank(SyohinDeleteFg) || SyohinDeleteFg.equals(mst000801_DelFlagDictionary.IRU.getCode())) {

				// 商品なし
				errMessageCd = "0083";

			} else {
				
				// 有効日が異なる場合は商品マスタの挿入が必要
				if (!isBlank(SyohinYukoDt) && !yuko_dt.equals(SyohinYukoDt)) {
					this.SyohinInsertFg = true;
				}
				
				// ギフト商品マスタ
				if (isBlank(tmp_del) || tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {

					// 登録なし
					this.InsertFg = true;
					
					//有効日が同じ場合は物理削除が必要
					if (!isBlank(tmp_yuko) && tmp_yuko.equals(yuko_dt)) {
						this.DeleteFg = true;
					}
					
				} else {
					// 登録あり

					if (!isBlank(tmp_yuko) && tmp_yuko.equals(yuko_dt)) {
						//有効日が同じ場合はUPDATE
						this.InsertFg = false;
					} else {
						//有効日が異なる場合はINSERT
						this.InsertFg = true;
					}
				}
			}
		}

		dataBase.closeResultSet(res);

		
		//登録する有効日より未来のデータチェック
		setPreparedSyohinAftCheckSQL(syohinAftCheck, bunrui1_cd, syohin_cd, yuko_dt);
		res = executeQueryRetry(syohinAftCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);
		if (res.next()) {
			//先付けの登録がある場合は登録不可
			errMessageCd = "0012";
		}
		dataBase.closeResultSet(res);

		
		//未来データは2件まで有効
		if (errMessageCd == null && this.SyohinInsertFg) {
			setPreparedSyohinCountSQL(syohinCount, bunrui1_cd, syohin_cd);
			ResultSet rset = syohinCount.executeQuery();

			if (rset.next()) {
				if (rset.getInt("count") > 1) {
					errMessageCd = "0015";
				}
			}
			dataBase.closeResultSet(rset);
		}

		if (errMessageCd != null) {
			setPreparedMessageSQL(errMessageCd);
			pstmt.addBatch();
			return false;
		}
		
		return true;
	}


	/**
	 * 削除時の商品マスタチェック
	 * @return boolean
	 */
	public boolean deleteSyohinCheck(ResultSet rs,String BATCH_ID,String BATCH_NA,int waitTime,int retryCnt) throws SQLException {
		
		String syohin_cd = rs.getString("syohin_cd");
		String bunrui1_cd = rs.getString("bunrui1_cd");
		String yuko_dt = rs.getString("yuko_dt");
		String errMessageCd = null;

		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}

		this.SyoriFg     = true;		//処理を行う必要があるか
		
		// 過去直近データ取得
		setPreparedSyohinBefCheckSQL(syohinBefCheck, bunrui1_cd, syohin_cd, yuko_dt);
		ResultSet res = executeQueryRetry(syohinBefCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);

		if (res.next()) {

			String SyohinYukoDt   = res.getString("SYOHIN_YUKO_DT");
			String SyohinDeleteFg = res.getString("SYOHIN_DELETE_FG");
			String SyohinHenkoDt  = res.getString("SYOHIN_HENKO_DT");
	
			String tmp_yuko  = res.getString("SUB_YUKO_DT");
			String tmp_del   = res.getString("SUB_DELETE_FG");

			if (!isBlank(SyohinYukoDt) && SyohinYukoDt.equals(yuko_dt) && SyohinHenkoDt.equals(MasterDT) && SyohinDeleteFg.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
				
				//同日で商品マスタが削除されている場合は、処理する必要なし
				this.SyoriFg = false;
			
			} else if (isBlank(SyohinDeleteFg) || SyohinDeleteFg.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
	
				// 削除対象の商品マスタなし
				errMessageCd = "0013";
				
			} else 	if (isBlank(tmp_del) || tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
	
				// 削除対象のギフト商品マスタなし
				errMessageCd = "0013";

			} else {

				//有効データのときのみ有効
				this.YukoDt = tmp_yuko;

				if (!isBlank(tmp_yuko) && tmp_yuko.equals(yuko_dt)) {
					//有効日が同じ場合はUPDATE
					this.InsertFg = false;
				} else {
					//有効日が異なる場合はINSERT
					this.InsertFg = true;
				}

				//登録する有効日より未来のデータチェック
				setPreparedSyohinAftCheckSQL(syohinAftCheck, bunrui1_cd, syohin_cd, yuko_dt);
				ResultSet rsAft = executeQueryRetry(syohinAftCheck, BATCH_ID, BATCH_NA, waitTime, retryCnt);
				
				if (rsAft.next()) {
					//未来が存在する場合エラー
					errMessageCd = "0014";
				}
				dataBase.closeResultSet(rsAft);
			}
		}
		
		dataBase.closeResultSet(res);

		if (!SyoriFg) {
			//処理する必要がない場合は正常で復帰
			return true;
		}

		//未来データは2件まで有効
		if (errMessageCd == null && InsertFg) {
			setPreparedSyohinCountSQL(syohinCount, bunrui1_cd, syohin_cd);
			ResultSet rset = syohinCount.executeQuery();

			if (rset.next()) {
				if (rset.getInt("count") > 1) {
					errMessageCd = "0015";
				}
			}
			dataBase.closeResultSet(rset);
		}

		if (errMessageCd != null) {
			setPreparedMessageSQL(errMessageCd);
			pstmt.addBatch();
			return false;
		}

		return true;
	}


	public boolean getSyoriFg() {
		return this.SyoriFg;
	}
	
	public boolean getInsertFg() {
		return this.InsertFg;
	}

	public boolean getDeleteFg() {
		return this.DeleteFg;
	}

	public boolean getSyohinInsertFg() {
		return this.SyohinInsertFg;
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

	
	/**
	 * 商品マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedBefCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT S.YUKO_DT         AS SYOHIN_YUKO_DT, ");
		sql.append("       S.DELETE_FG       AS SYOHIN_DELETE_FG, ");
		sql.append("       S.HENKO_DT        AS SYOHIN_HENKO_DT, ");
		sql.append("       S.SINKI_TOROKU_DT AS SYOHIN_SINKI_TOROKU_DT, ");
		sql.append("       G.YUKO_DT         AS SUB_YUKO_DT, ");
		sql.append("       G.DELETE_FG       AS SUB_DELETE_FG ");
		sql.append("  FROM R_SYOHIN S ");
		sql.append("  LEFT JOIN ");
		sql.append("       R_GIFT_SYOHIN G");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("    ON G.BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("   AND G.SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("    ON  ");
		sql.append("   		G.SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("   AND G.YUKO_DT    = S.YUKO_DT ");
		sql.append(" WHERE S.BUNRUI1_CD = ? ");
		sql.append("   AND S.SYOHIN_CD  = ? ");
		sql.append("   AND S.YUKO_DT    = ");
		sql.append("       (SELECT MAX(YUKO_DT) ");
		sql.append("          FROM R_SYOHIN ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sql.append("         WHERE BUNRUI1_CD = S.BUNRUI1_CD ");
//		sql.append("           AND SYOHIN_CD  = S.SYOHIN_CD ");
		sql.append("         WHERE  ");
		sql.append("           		SYOHIN_CD  = S.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sql.append("           AND YUKO_DT   <= ? ");
		sql.append("       ) ");
		
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	
	/**
	 * 商品マスタチェックSQL
	 * @throws Exception
	 */
	public void setPreparedSyohinBefCheckSQL2(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String yuko_dt) throws SQLException {
		int idx = 0;

		//分類１コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//有効日
		idx++;
		pstmt.setString(idx, yuko_dt);
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
