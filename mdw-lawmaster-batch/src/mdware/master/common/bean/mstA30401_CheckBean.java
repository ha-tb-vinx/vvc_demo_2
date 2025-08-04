/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別按分率登録検索条件データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別按分率登録検索条件データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別按分率登録検索条件データデータチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別按分率登録検索条件データデータチェック用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstA30401_CheckBean{

	public void Check(mst000101_DbmsBean db, mstA30301_KeepBean Keepb
			, Map CtrlColor, boolean flg) throws Exception,SQLException{

		//----------------------------
		//初期処理
		//----------------------------

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		// エラーコード・メッセージの初期化
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			// エラーチェック
			List lst = new ArrayList();		// マスタ存在チェック抽出条件

			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			// 検索処理チェック----------------------------------------------

			if (flg){
				// 部門コード存在チェック
				if (!Keepb.getBumonCd().equals("")){
					String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add( " CODE_CD = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) +"' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");

					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//部門コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setFirstFocus("ct_bumoncd");
							Keepb.setBumonNm("");
							Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
						}
					}else{
						Keepb.setBumonNm(mstchk.getCdName());
					}
				}

				//グループコード存在チェック
				if (!Keepb.getGroupCd().equals("")){
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add("    bumon_cd     = '" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd() ,4,"0",true)) + "' ");
					lst.add("and yoto_kb        = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
					lst.add("and groupno_cd = '" + mst000401_LogicBean.chkNullString(Keepb.getGroupCd()) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO","NAME_NA", lst, "0", "");

					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//グループコード存在エラー
						Keepb.setGroupNm("");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_groupcd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("ct_groupcd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたグループコード"+map.get("40007").toString());
						}
					} else {
						Keepb.setGroupNm(mstchk.getCdName());
					}
				}
			} else {
				//コピーグループコード存在チェック
				if (!Keepb.getCopyGroupCd().equals("")){
					lst.add("    bumon_cd     = '" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd() ,4,"0",true)) + "' ");
					lst.add("and yoto_kb        = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
					lst.add("and groupno_cd = '" + mst000401_LogicBean.chkNullString(Keepb.getCopyGroupCd()) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO","NAME_NA", lst, "0", "");

					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//コピーグループコード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"setgroup_cd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("setgroup_cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定されたグループコード"+map.get("40007").toString());
						}
					}
				}
			}
		}
		Keepb.setCtrlColor(CtrlColor);
	}
}