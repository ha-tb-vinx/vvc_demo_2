package mdware.master.common.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.batch.process.mb38.MB380000_CommonSql;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <P>タイトル:  登録票アップロードチェック処理</P>
 * <P>説明:      登録票アップロードチェック処理商品マスタTR以外用抽象クラス。</P>
 * <P>著作権:	 Copyright (c) 2006</P>
 * <P>会社名:	 Vinculum Japan Corp.</P>
 * @author      jiangb
 * @version     1.0
 * @version 1.01 (2015.10.09) DAI.BQ FIVImart対応
 * @version 1.02 (2017.10.04) S.Takayama #5994
 * @version 1.03 2022/01/04 HOAI.TTT #6409 対応
 */
public abstract class OtherCheckAbstract implements InterfaceCheck {
    
	// データ結果
	private ResultSet targetRS = null;
	// TRテーブルのキー
	private String[] key = null;
	// メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;
    // 共通メッセージ
	private Map map = null;
    // 共通SQLクラス
	private MB380000_CommonSql comSql = new MB380000_CommonSql(); 
	// データベース
	private MasterDataBase dataBase = null;
    // 共通項目チェッククラス
	private mst38A101_CheckItem checkItem = null;
    // 共通項目チェッククラス
	private boolean blCheckFlg = true;
	// マスタ日付
	private String masterDT = null;
	// ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	// 種別区分
	private String syubetuKbn = null;

	private final String BATCH_ID = "MB83-A1-01";
	private final String BATCH_NA = "登録票アップロードチェック処理";
	
	// 項目の削除を表す文字
	private final String deleteString = "*";

	// ===BEGIN=== Add by kou 2006/8/21
	// バッチから呼ばれているか否か
	private boolean isFromBatch = false;
	// ===END=== Add by kou 2006/8/21
	
	/**
	 * コントラクト
	 * 
	 * @throws SQLException
	 */
	public OtherCheckAbstract(MasterDataBase dataBase) throws SQLException {
		// データベース設定
		this.setDataBase(dataBase);
				
        // 共通メッセージを取得
		//#6409 Mod 2022.01.04 HOAI.TTT (S)
		//map = mst38A101_CommonMessage.getMsg();
		map = mst38A100_CommonMessage.getMsg();
		//#6409 Mod 2022.01.04 HOAI.TTT (E)
		
		// ステートメント作成
		pstmt = comSql.getPreparedMessageSQL(dataBase);
		
		//  マスタ日付を取得する
		this.setMasterDT(RMSTDATEUtil.getMstDateDt());
	}

	/**
	 * 全体共通項目チェック
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws Exception
	 */
	public void dataCheck() throws Exception {
		
		// 全体共通項目チェック処理クラス
		checkItem = new mst38A101_CheckItem(this.targetRS, this.pstmt,
				this.map, this.getSyubetuKbn());
		
		// 全体共通項目のチェック処理を行う
		if (!checkItem.commonCheck()){
			this.setBlCheckFlg(false);
		}
	}
	
	/**
	 * 本処理
	 * @param  なし
	 * @return なし
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		
		// 処理開始ログ
		batchLog.getLog().info(BATCH_ID, BATCH_NA, "共通項目のチェックを開始します。");
		
		// 共通チェック処理
		this.dataCheck();
		
		// 処理開始ログ
		batchLog.getLog().info(BATCH_ID, BATCH_NA, "共通項目のチェックを終了します。");
	}
	
	/**
	 * 処理結果メッセージテーブルへ追加する
	 * 
	 * @throws Exception
	 */
	public void dataUpdate() throws Exception {
		
		// メッセージテーブルに登録する
		messageUpdate();
	}

	/**
	 * エラーメッセージテーブル登録処理を行う
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws SQLException
	 */
	private void messageUpdate() throws SQLException {
		
		// エラーがある場合
		if (!this.isBlCheckFlg()) {
			// メッセージテーブルに登録する
			pstmt.executeBatch();
		}
	}
	
	/**
	 * 結果メッセージ作成SQL
	 * @throws Exception
	 */
	// #5994 2017.10.04 S.Takayama (S)
	//public void setPreparedMessageSQL(String megNo) throws SQLException {
	public void setPreparedMessageSQL(String megNo, Object... args) throws SQLException {
	// #5994 2017.10.04 S.Takayama (E)

		int idx = 0;
		String strDefaultProductTimestamp = AbstractMDWareDateGetter
		.getDefaultProductTimestamp("rbsite_ora"); // システム日時
		//Add 2015.10.09 DAI.BQ (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//Add 2015.10.09 DAI.BQ (E)
		// 取込日
		idx++;
		pstmt.setString(idx, key[0]);
		// EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);
		// 受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);
		// 受付SEQNo
		idx++;
		pstmt.setString(idx, key[3]);
		// シート種別
		idx++;
		pstmt.setString(idx, key[4]);
		// 結果メッセージコード
		idx++;
		pstmt.setString(idx, megNo);
		// 結果メッセージ
		idx++;
		// #5994 2017.10.04 S.Takayama (S)
		//pstmt.setString(idx, MessageUtil.getMessage((String)map.get(megNo), userLocal));
		// #5994 2017.10.04 S.Takayama (E)
		//#6409 Mod 2022/01/04 HOAI.TTT (S)
		//pstmt.setString(idx, MessageUtil.getMessage((String)map.get(megNo), userLocal, args));
		pstmt.setString(idx, (String)map.get(megNo));
		//#6409 Mod 2022/01/04 HOAI.TTT (E)
		// 作成年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 作成ID
		idx++;
		pstmt.setString(idx, key[5]);
		// 更新年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 更新ID
		idx++;
		pstmt.setString(idx, key[5]);
		
		pstmt.addBatch();
	}
	
	/**
	 * CTFマスタに存在するかのチェック（共通部）
	 * 
	 * @param ResultSet rs
	 * @param String col1 CTFマスタのチェック結果のカラム名
	 * @param String msgNo エラー時のメッセージNo
	 * 
	 * @return boolean
	 */
	public boolean rNamectfCheck(ResultSet rs, String col1, String msgNo) throws SQLException {
        String strValue = rs.getString(col1);
		
		if (!isNotBlank(strValue)) {
			// CTFに存在しない
			setPreparedMessageSQL(msgNo);
			return false;
		}
		return true;
	}

	/**
	 * 入力された値がブランクかのチェック
	 * 
	 * @param val String
	 * @return ある：true;ない:false
	 * 
	 */
	public boolean isNotBlank(String val) {
		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * TRテーブルのキーのセット処理
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws SQLException
	 */
	public void setTRKey() throws SQLException {
		String[] key = new String[6]; // キー項目

		key[0] = this.getTargetRS().getString("torikomi_dt"); // 取込日
		key[1] = this.getTargetRS().getString("excel_file_syubetsu"); // EXCELファイル種別
		key[2] = this.getTargetRS().getString("uketsuke_no"); // 受付No.
		key[3] = this.getTargetRS().getString("uketsuke_seq"); // 受付SEQNo.
		key[4] = this.getSyubetuKbn(); // シート種別
		key[5] = this.getTargetRS().getString("by_no"); // 登録承認BY_NO
		// メッセージ登録用
		this.setKey(key);
	}	
	
	/**
	 * TRテーブルのキーのセット処理
	 * @return なし
	 */
	public void setKey(String[] key) {
		this.key = key;
	}

	/**
	 * データセットのゲット処理
	 * 
	 * @param  なし
	 * @return targetRS
	 */
	public ResultSet getTargetRS() {
		return targetRS;
	}
	
	/**
	 * データセットのセット処理
	 * 
	 * @param  targetRS
	 * @return なし
	 */
	public void setTargetRS(ResultSet targetRS) {
		this.targetRS = targetRS;
	}
	
	/**
	 * データベースのゲット処理
	 * 
	 * @param  なし
	 * @return dataBase
	 */
	public MasterDataBase getDataBase() {
		return dataBase;
	}
	
	/**
	 * データベースのセット処理
	 * 
	 * @param  なし
	 * @return dataBase
	 */
	public void setDataBase(MasterDataBase dataBase) {
		this.dataBase = dataBase;
	}

	/**
	 * チェックフラグ
	 * 
	 * @param  blCheckFlg
	 * @return dataBase
	 */
	public boolean isBlCheckFlg() {
		return blCheckFlg;
	}
	
	/**
	 * チェックフラグのセット処理
	 * 
	 * @param  blCheckFlg
	 * @return なし
	 */
	public void setBlCheckFlg(boolean blCheckFlg) {
		this.blCheckFlg = blCheckFlg;
	}
	
	/**
	 * マスタ日付のゲット処理
	 * 
	 * @param  なし
	 * @return masterDT
	 */
	public String getMasterDT() {
		return masterDT;
	}
	
	/**
	 * マスタ日付のセット処理
	 * 
	 * @param  masterDT
	 * @return なし
	 */
	public void setMasterDT(String masterDT) {
		this.masterDT = masterDT;
	}

	/**
	 * 種別区分のゲット処理
	 * 
	 * @param  なし
	 * @return syubetuKbn
	 */
	public String getSyubetuKbn() {
		return syubetuKbn;
	}

	/**
	 * 種別区分のセット処理
	 * 
	 * @param  syubetuKbn
	 * @return なし
	 */
	public void setSyubetuKbn(String syubetuKbn) {
		this.syubetuKbn = syubetuKbn;
	}
	
	// ===BEGIN=== Add by kou 2006/8/21
	/**
	 * バッチから呼ばれているか否かの判別
	 * @return
	 */
	public boolean isFromBatch() {
		return isFromBatch;
	}

	/**
	 * バッチから呼ばれているか否かのセット
	 * @param b
	 */
	public void setFromBatch(boolean b) {
		isFromBatch = b;
	}
	// ===END=== Add by kou 2006/8/21

}
