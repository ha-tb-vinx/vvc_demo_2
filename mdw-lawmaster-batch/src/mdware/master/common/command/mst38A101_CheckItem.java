package mdware.master.common.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
/**
 * <P>タイトル:  mst38A101_CheckItem</P>
 * <P>説明:      mst38A101_CheckItem</P>
 * <P>著作権:	 Copyright (c) 2006</P>
 * <P>会社名:	 Vinculum Japan Corp.</P>
 * @author
 * @version     1.0
 * @version 1.01 (2015.10.09) DAI.BQ FIVImart対応
 * @version 1.02 2022/01/04 HOAI.TTT #6409 対応
 */

public class mst38A101_CheckItem {
	
	// メッセージを保持するMap
	private Map map = null;

	// メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;	
	
	// データ結果
	private ResultSet targetRS = null;
	
	// エクセルファイル種別
	private String syubetu = null;
	
	 // バッチでは、商品コードの有効レコードのチェックをされている、
	 // CheckItemでのチェックは有効無効関係なく、あったらエラーになる。それは間違い結果になる
	 // バッチからの新規チェックする時、CheckItemでの商品コードの
	 // 商品コードをチェックするか否か、デフォルトはチェックする
	 private boolean chkSyohin = true;

	// パンチデータ用チェック対応用  [true:パンチデータ false:通常データ（デフォルト）]
	protected boolean punchKb = false;
	
	public mst38A101_CheckItem(ResultSet rs, PreparedStatement pstmt,Map map,String shubetsu) {
		
		this.map = map;
		this.targetRS = rs;
		this.pstmt = pstmt;
		this.syubetu = shubetsu;
	}
	
	public boolean commonCheck() throws Exception {
		
		boolean checkFg = true; // エラーフラグ

		// 修正区分
		String syusei_kb = targetRS.getString("syusei_kb");
		
		// 部門コードの存在チェック
		if (!isNotBlank(targetRS.getString("bunrui1_cd"))) {
			insertMessageTable("0113");
			checkFg = false;
		} else {
			// 名称マスタに存在しない場合エラー
			//if (!rNamectfCheck(targetRS, "bumon_ck", "0114")) {
			//	checkFg = false;
			//}
		}
		
		// 商品コード
		// 新規の場合
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			if(chkSyohin){
				// 空ではない場合
				if (isNotBlank(targetRS.getString("syohin_cd")) && targetRS.getString("syohin_cd").trim().length() > 2){
					// 商品マスタに現在有効または予約レコードが存在する場合、エラー
					if (isNotBlank(targetRS.getString("s_syohin_cd"))) {
						insertMessageTable("0115");
						checkFg = false;
					} 
				}
			}
		// 修正の場合
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
			// 商品コードが空なら、エラー
			if (!isNotBlank(targetRS.getString("syohin_cd")) || targetRS.getString("syohin_cd").trim().length() < 3){
					insertMessageTable("0116");
					checkFg = false;
			} else {
				// 商品マスタになしまたは削除状態であれば、エラー
				if (!isNotBlank(targetRS.getString("s_syohin_cd"))) {
					insertMessageTable("0117");
					checkFg = false;
				}
			}
		// 削除の場合	
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {
			// 商品コードが空なら、エラー
			if (!isNotBlank(targetRS.getString("syohin_cd"))){
					insertMessageTable("0116");
					checkFg = false;
			} else {
				// 商品マスタになしまたは削除状態であれば、エラー
				if (!isNotBlank(targetRS.getString("s_syohin_cd"))) {
					insertMessageTable("0118");
					checkFg = false;
				}
			}
		// 取消の場合	
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			// 商品コードが空なら、エラー
			if (!isNotBlank(targetRS.getString("syohin_cd"))){
					insertMessageTable("0116");
					checkFg = false;
			} else {
				// 商品マスタに存在しない場合はエラー
				if (!isNotBlank(targetRS.getString("s_syohin_cd"))) {
					insertMessageTable("0432");
					checkFg = false;
				}
			}
		}
		return checkFg;
	}
	/**
	 * 結果メッセージ作成SQL
	 * @throws Exception
	 */
	private void setPreparedMessageSQL(String megNo) throws SQLException {

		int idx = 0;
		String strDefaultProductTimestamp = AbstractMDWareDateGetter
			.getDefaultProductTimestamp("rbsite_ora"); // システム日時
		//Add 2015.10.09 DAI.BQ (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//Add 2015.10.09 DAI.BQ (E)
		// 取込日
		idx++;
		pstmt.setString(idx, targetRS.getString("torikomi_dt"));
		// EXCELファイル種別
		idx++;
		pstmt.setString(idx, targetRS.getString("excel_file_syubetsu"));
		// 受付ファイルNo
		idx++;
		pstmt.setString(idx, targetRS.getString("uketsuke_no"));
		// 受付SEQNo
		idx++;
		pstmt.setString(idx, targetRS.getString("uketsuke_seq"));
		// シート種別
		idx++;
		pstmt.setString(idx, syubetu);
		// 結果メッセージコード
		idx++;
		pstmt.setString(idx, megNo);
		// 結果メッセージ
		idx++;
		//#6409 Mod 2022/01/04 HOAI.TTT (S)
		//pstmt.setString(idx, MessageUtil.getMessage((String)map.get(megNo), userLocal));
		pstmt.setString(idx, (String)map.get(megNo));
		//#6409 Mod 2022/01/04 HOAI.TTT (E)
		// 作成年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 作成ID
		idx++;
		pstmt.setString(idx, targetRS.getString("by_no"));
		// 更新年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 更新ID
		idx++;
		pstmt.setString(idx, targetRS.getString("by_no"));
	}
	
	/**
	 * 結果メッセージテーブルにインサート
	 * @param megNo String メッセージID
	 * @throws Exception
	 */
	public void insertMessageTable(String megNo) throws SQLException {
		// 結果メッセージ作成
		setPreparedMessageSQL(megNo);
		pstmt.addBatch();
	}

	/**
	 * CTFマスタに存在するかのチェック（共通部）
	 * @param ResultSet rs
	 * @param String col1 CTFマスタのチェック結果のカラム名
	 * @param String msgNo エラー時のメッセージNo
	 * @return boolean
	 */
	public boolean rNamectfCheck(ResultSet rs, String col1, String msgNo) throws SQLException {
		String str1 = rs.getString(col1);
		
		if (str1 == null||str1.trim().equals("")) {
			// CTFに存在しない
			insertMessageTable(msgNo);
			return false;
		}
		return true;
	}
	
	/**
	 * 入力された値がブランクかのチェック
	 * @param val String
	 * @return boolean
	 */
	public boolean isNotBlank(String val) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param b
	 */
	public void setChkSyohin(boolean b) {
		chkSyohin = b;
	}
	
	/**
	 * @param b
	 */
	public void setPunchKb(boolean b) {
		punchKb = b;
	}

}
