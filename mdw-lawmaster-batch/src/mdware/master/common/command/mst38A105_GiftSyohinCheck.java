package mdware.master.common.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:ギフト商品マスタTRの特徴項目チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2006/06/28<BR>
 */

public class mst38A105_GiftSyohinCheck extends SyohinSubCheckAbstract{

	// データベース
	private MasterDataBase dataBase = null;
	
	/**
	 * コンストラクタ
	 */
	public mst38A105_GiftSyohinCheck(MasterDataBase dataBase) throws SQLException {
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

		
		// 課税区分
		if (isNotBlank(rs.getString("KAZEI_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "KAZEI_CK", "0416")) {
				checkFg = false;
			}
		}
		
		// 経由区分
		if (isNotBlank(rs.getString("KEIYU_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "KEIYU_CK", "0418")) {
				checkFg = false;
			}
		}

		// 伝票区分
		if (isNotBlank(rs.getString("DENPYO_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "DENPYO_CK", "0420")) {
				checkFg = false;
			}
		}

		// 送料区分
		if (isNotBlank(rs.getString("SORYO_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "SORYO_CK", "0422")) {
				checkFg = false;
			}
		}

		// 配送区分
		if (isNotBlank(rs.getString("HAISO_KB"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "HAISO_CK", "0424")) {
				checkFg = false;
			}
		}
		
		// ギフト発送元コード
		if (isNotBlank(rs.getString("GIFT_HASSOMOTO_CD"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "GIFT_HASSOMOTO_CK", "0426")) {
				checkFg = false;
			}

		} else {

			// 新規の場合、必須
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				super.setPreparedMessageSQL("0425");
				checkFg = false;
			}
		}

		return checkFg;
	}
}

