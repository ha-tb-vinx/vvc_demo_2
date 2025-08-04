package mdware.master.common.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:計量器マスタTRの特徴項目チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2006/06/28<BR>
 * @version 1.01 2011/02/25 Y.Imai 計量器保存温度帯区分追加対応 MM00111
 */

public class mst38A106_KeiryokiCheck extends SyohinSubCheckAbstract{

	// データベース
	private MasterDataBase dataBase = null;
	
	/**
	 * コンストラクタ
	 */
	public mst38A106_KeiryokiCheck(MasterDataBase dataBase) throws SQLException {
		super(dataBase);
	}

	/**
	 * 本処理
	 * 
	 * @param  targetRS ResultSet
	 * @return 処理したデータセット
	 * 
	 * @throws Exception
	 */
	public ResultSet process(ResultSet targetRS, String sheetSyubetu) throws Exception {
			
		try {

		    // データ結果
			super.setTargetRS(targetRS);
			
			// チェックフラグ初期化
			super.setBlCheckFlg(true);
			
			// TRテーブルのキーのセット処理
//			super.setTRKey();
			super.setTRKey(sheetSyubetu);

			// データベースを取得する
			dataBase = super.getDataBase();

			// 共通チェックを行う
			super.process();
			
			// ギフト商品マスタのチェック
			if (!dataCheck(targetRS)) {
				super.setBlCheckFlg(false);
			}
			
			// メッセージテーブルに登録する
			super.dataUpdate();
			
			// エラーがある場合、nullを戻す
			if (!super.isBlCheckFlg()) {
				return null;
			}
			return targetRS;
			
		} catch (Exception e) {
//			dataBase.rollback();
//			batchLog.getLog().error(BATCH_ID, BATCH_NA, e.toString());
			throw e;
		}
	}

	/**
	 * データチェック及び登録処理
	 * 
	 * @param   rs ResultSet
	 * @return  エラーある：false、エラーなし：true
	 * 
	 * @throws　SQLException
	 * 
	 */
	private boolean dataCheck(ResultSet rs) throws SQLException {
		
		boolean checkFg = true; // エラーフラグ

		// 修正区分
		String syusei_kb = rs.getString("syusei_kb");
		
		// 削除の場合は、主キー以外の項目をチェックする必要がない
		if(syusei_kb == null){
			return false;
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())){
			return true;
		}
		
		// アイキャッチ№
		if (isNotBlank(rs.getString("EYE_CATCH_NO"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "EYE_CATCH_NO_CK", "0434")) {
				checkFg = false;
			}
		}
		
		// アイキャッチモード
		if (isNotBlank(rs.getString("EYE_CATCH_MODE"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "EYE_CATCH_MODE_CK", "0436")) {
				checkFg = false;
			}
		}
		
		// 定額区分
		if (isNotBlank(rs.getString("TEIGAKU_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "TEIGAKU_CK", "0438")) {
				checkFg = false;
			}
		}
		
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		// 保存温度帯区分
		if (isNotBlank(rs.getString("HOZON_ONDOTAI_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "HOZON_ONDOTAI_KB_CK", "0627")) {
				checkFg = false;
			}
		}
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End
		// 商品区分
		if (isNotBlank(rs.getString("SYOHIN_KBN"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "SYOHIN_KB_CK", "0440")) {
				checkFg = false;
			}
		}
		
		// 賞味期間計算区分 
		if (isNotBlank(rs.getString("SYOMIKIKAN_KB"))) {
			
			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "SYOMIKIKAN_CK", "0604")) {
				checkFg = false;
			}
		}
		
		return checkFg;
	}
}

