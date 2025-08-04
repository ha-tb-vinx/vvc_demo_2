/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店舗仮登録照会表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店舗仮登録照会表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author shijun
 * @version 1.0(2006/07/20)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>
 * タイトル: 新ＭＤシステム（マスター管理）店舗仮登録照会の画面表示データチェック用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する店舗仮登録照会の画面表示データチェック用クラス
 * </p>
 * <p>
 * 著作権: Copyright (c) 2006
 * </p>
 * <p>
 * 会社名: Vinculum Japan Corp.
 * </p>
 *
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstB10401_CheckBean {

	public void Check(mst000101_DbmsBean db,mstB10101_TenpoKaTorokuSyokaiDM dbDM, mstB10201_KeepBean Keepb,
			mstB10301_KeepMeisaiBean Meisaib, DataHolder dataHolder,
			String GamenId, String FirstFocusCtl, String[] CtrlName,
			Map CtrlColor, SessionManager sessionManager) throws SQLException,
			Exception {

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if (Keepb.getErrorFlg()
				.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			// 画面内容を保存
//			↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
			Keepb.setSystemKb(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("gyosyu_kb")).trim());
//					↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_bumoncd")).trim());
			Keepb.setBumonNm(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_bumonnm")).trim());
			Keepb.setHinbanCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hinbancd")).trim());
			Keepb.setHinbanNm(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hinbannm")).trim());
			Keepb.setUnitCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_unitcd")).trim());
			Keepb.setUnitNm(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_unitnm")).trim());
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_syohincd")).trim());
			Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_syohinnm")).trim());
			Keepb.setTenpoCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_tenpocd")).trim());
			Keepb.setTenpoNm(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_tenponm")).trim());
			if ("true".equals(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("h_inputflg")).trim())) {
				Keepb.setInputflg(true);
			} else {
				Keepb.setInputflg(false);
			}

			List lst = new ArrayList(); // マスタ存在チェック抽出条件
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			// 部門コード存在チェック
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			if (!mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_bumoncd")).equals("")) {
				lst = new ArrayList();
				lst.add(" CODE_CD = '"
						+ mst000401_LogicBean.chkNullString(StringUtility
								.charFormat(dataHolder
										.getParameter("tx_bumoncd"), 4, "0",
										true)) + "' ");
				lst.add("  AND SYUBETU_NO_CD  = '"
						+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");

				lst.add("  AND DELETE_FG  = '"
						+ mst000801_DelFlagDictionary.INAI.getCode()+ "' ");

				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
						"KANJI_RN", lst, "0", "");

				if (!mstchk.getExistenceFlg().equals(
						mst000101_ConstDictionary.FUNCTION_TRUE)) {

					// 部門コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,
							"tx_bumoncd");
					FirstFocusCtl = "tx_bumoncd";
					Keepb.setBumonNm("");
					if (Keepb.getErrorFlg().equals(
							mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb
								.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された部門コード"
								+ map.get("40007").toString());
					}
				} else {
					Keepb.setBumonNm(mstchk.getCdName());
				}
			} else {
				Keepb.setBumonNm("");
			}

			// 品番コード存在チェック
			lst = new ArrayList();
			if (!mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hinbancd")).equals("")) {
				lst.add("  CODE_CD  = '"
						+ mst000401_LogicBean.chkNullString(StringUtility
								.charFormat(dataHolder
										.getParameter("tx_hinbancd"), 4, "0",
										true)) + "' ");
				lst.add("  AND SYUBETU_NO_CD  =  '"
						+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");

				lst.add("  AND DELETE_FG  = '"
						+ mst000801_DelFlagDictionary.INAI.getCode()+ "' ");

				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
						"KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(
						mst000101_ConstDictionary.FUNCTION_TRUE)) {

					mst000401_LogicBean.setErrorBackColor(CtrlColor,
							"tx_hinbancd");
					Keepb.setHinbanNm("");
					if (Keepb.getErrorFlg().equals(
							mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb
								.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_hinbancd";
						Keepb.setErrorMessage("指定された品番コード"
								+ map.get("40007").toString());
					}
				} else {
					Keepb.setHinbanNm(mstchk.getCdName());
				}
			} else {
				Keepb.setHinbanNm("");
			}

			// ユニットコード存在チェック
			lst = new ArrayList();

//			↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//			// システム区分
//			String systemKb = Keepb.getSystemKb();
//			if (systemKb == null) {
//				ResultSet rest = db.executeQuery(dbDM.getSystemKbSql(Keepb.getBumonCd(), Keepb.getUnitCd()));
//				while (rest.next()) {
//					systemKb = rest.getString("system_kb");
//					break;
//				}
//			}
//
//			if (systemKb == null) {
//				systemKb = "";
//			}
//
//			Keepb.setSystemKb(systemKb);
//			↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑

			String unitCd = Keepb.getSystemKb() + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unitcd"));
			if (!mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_unitcd")).equals("")) {
				lst.add("  CODE_CD  = '"
						+ unitCd + "' ");
				lst.add("  AND SYUBETU_NO_CD  =  '"
						+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");

				lst.add("  AND DELETE_FG  = '"
						+ mst000801_DelFlagDictionary.INAI.getCode()+ "' ");

				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
						"KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(
						mst000101_ConstDictionary.FUNCTION_TRUE)) {

					mst000401_LogicBean.setErrorBackColor(CtrlColor,
							"tx_unitcd");
					Keepb.setUnitNm("");
					if (Keepb.getErrorFlg().equals(
							mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb
								.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_unitcd";
						Keepb.setErrorMessage("指定されたユニットコード"
								+ map.get("40007").toString());
					}
				} else {
					Keepb.setUnitNm(mstchk.getCdName());
				}
			} else {
				Keepb.setUnitNm("");
			}

			// 商品名称を取得
			String hinmeiKanjiNa = "";
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohincd")).equals("")) {
				ResultSet rest = db.executeQuery(dbDM.getSyohinNmSql(Keepb.getBumonCd(),Keepb.getSyohinCd()));
				while (rest.next()) {
					hinmeiKanjiNa = rest.getString("hinmei_kanji_na");
					break;
				}
			}
			Keepb.setHinmeiKanjiNa(hinmeiKanjiNa);

			// 店舗コード存在チェック
			lst = new ArrayList();
			if (!mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_tenpocd")).equals("")) {
			lst.add( "     TENPO_CD = '" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(dataHolder.getParameter("tx_tenpocd"), 6, "0",true)) + "' ");
			lst.add("  AND DELETE_FG  = '" + mst000801_DelFlagDictionary.INAI.getCode()+ "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENPO","KANJI_RN", lst, "0", "");
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//店舗コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tenpocd");
				Keepb.setTenpoNm("");
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					FirstFocusCtl = "tx_tenpocd";
					Keepb.setErrorMessage("指定された店舗コード" + map.get("40007").toString());
				}
			} else {
				Keepb.setTenpoNm(mstchk.getCdName());
			}
			} else {
				Keepb.setTenpoNm("");
			}


		}

		Keepb.setFirstFocus(FirstFocusCtl);
		Keepb.setCtrlColor(CtrlColor);
	}
}