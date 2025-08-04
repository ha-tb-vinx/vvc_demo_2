/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラス登録検索条件データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラス登録検索条件データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）サブクラス登録検索条件データデータチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するサブクラス登録検索条件データデータチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */

public class mstA20301_CheckBean {

	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mst000101_DbmsBean	db               : DB
	 * @param 		mst230201_KeepBean Keepb             : KeepBeen
	 * @param 		mst230301_KeepMeisaiBean KeepbMeisai : KeepBeen
	 * @param 		DataHolder 	dataHolder               : DataHolder
	 * @param		String		kengenCd		         : 権限コード
	 * @param		String 		GamenId 		         : 画面ID
	 * @param		String 		FirstFocusCtl            : 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		Map CtrlColor           　           : コントロール背景色
	 * @param		SessionManager sessionManager        : SessionManager
	 */
	public void Check(mst000101_DbmsBean db, mstA20101_KeepBean Keepb, DataHolder dataHolder
			, String GamenId, String FirstFocusCtl, String[] CtrlName, Map CtrlColor, SessionManager sessionManager,boolean flg) throws Exception,SQLException{

		//----------------------------
		//初期処理
		//----------------------------

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		// エラーコード・メッセージの初期化
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//----------------------------
		//KeepBean値設定
		//----------------------------

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			// 画面内容を保存
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).trim());	//部門コード
			//Keepb.setBumonNm(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim()));	//部門名称
			Keepb.setSubclass_cd_from(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_subclass_cd_from")).trim());	//開始選択コード
			Keepb.setSubclass_cd_to(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_subclass_cd_to")).trim());	//終了選択コード
			Keepb.setChangFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

			// 初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			if (flg){
				// エラーチェック
				List lst = new ArrayList();		// マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

				// 検索処理チェック----------------------------------------------

				//部門コード存在チェック
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_bumon_cd")).equals("")) {
					String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add(" CODE_CD = '"
							+ mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("tx_bumon_cd"), 4, "0",true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");

					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						// 部門コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_bumon_cd");
						FirstFocusCtl = "tx_bumon_cd";
						Keepb.setBumonNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された部門コード"
									+ map.get("40007").toString());
						}
					} else {
						Keepb.setBumonNm(mstchk.getCdName());
					}
				} else {
					Keepb.setBumonNm("");
				}
			}
		}
		Keepb.setCtrlColor(CtrlColor);
	}
}
