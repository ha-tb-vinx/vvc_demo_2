/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）本部投入数量確認表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する本部投入数量確認表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/7)初版作成
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;

/**
 * <p>
 * タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会画面表示データチェック用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する値札タグ発行情報照会画面表示データチェック用クラス
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
public class mstB40301_CheckBean {

	public void Check(
			mst000101_DbmsBean db,
			mstB40201_KeepBean Keepb,
			DataHolder dataHolder,
			String GamenId,
			String FirstFocusCtl,
			String[] CtrlName,
			Map CtrlColor,
			SessionManager sessionManager,
			boolean chkFlg)
			throws SQLException, Exception {

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		// 画面内容を保存
		Keepb.setSystemKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("gyosyu_kb")).trim());
		Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).trim());
		Keepb.setHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinban_cd")).trim());
		Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_cd")).trim());
		Keepb.setLineCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_cd")).trim());
		Keepb.setUnitCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_cd")).trim());
		Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_cd")).trim());
		Keepb.setSiiresakiSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_siiresaki_syohin_cd")).trim());
		Keepb.setSscdFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sscd_flg")).trim());
		Keepb.setSinkiTorokuDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sinki_toroku_dt")).trim());
		Keepb.setHachuStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hachu_dt_from")).trim());
		Keepb.setHachuEnDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hachu_dt_to")).trim());
		Keepb.setNohinStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_nohin_dt_from")).trim());
		Keepb.setNohinEnDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_nohin_dt_to")).trim());
		Keepb.setHanbaiStDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbai_dt_from")).trim());
		Keepb.setHanbaiEnDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanbai_dt_to")).trim());
		Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohin_cd")).trim());
		Keepb.setByNo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_by_no")).trim());

		if (chkFlg){
			List lst = new ArrayList(); // マスタ存在チェック抽出条件
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			// 部門コード存在チェック
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			if (!Keepb.getBumonCd().equals("")) {
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				lst.add(" CODE_CD = '" + StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true) + "' ");
				lst.add(" AND SYUBETU_NO_CD  = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					// 部門コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_bumon_cd");
					FirstFocusCtl = "tx_bumon_cd";
					Keepb.setBumonNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された部門コード" + map.get("40007").toString());
					}
				} else {
					Keepb.setBumonNm(mstchk.getCdName());
				}
			} else {
				Keepb.setBumonNm("");
			}

			// 品番コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getHinbanCd().equals("")) {
				lst.add(" CODE_CD  = '" + StringUtility.charFormat(Keepb.getHinbanCd(), 4, "0", true) + "' ");
				lst.add(" AND SYUBETU_NO_CD  =  '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_hinban_cd");
					Keepb.setHinbanNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_hinban_cd";
						Keepb.setErrorMessage("指定された品番コード" + map.get("40007").toString());
					}
				} else {
					Keepb.setHinbanNm(mstchk.getCdName());
				}
			} else {
				Keepb.setHinbanNm("");
			}

			// 品種コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getHinsyuCd().equals("")) {
				lst.add(" CODE_CD  = '" + StringUtility.charFormat(Keepb.getHinsyuCd(), 4, "0", true) + "' ");
				lst.add(" AND SYUBETU_NO_CD  =  '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_hinsyu_cd");
					Keepb.setHinsyuNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_hinsyu_cd";
						Keepb.setErrorMessage("指定された品種コード" + map.get("40007").toString());
					}
				} else {
					Keepb.setHinsyuNm(mstchk.getCdName());
				}
			} else {
				Keepb.setHinsyuNm("");
			}

			// ラインコード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getLineCd().equals("")) {
				lst.add(" CODE_CD  = '" + Keepb.getLineCd() + "' ");
				lst.add(" AND SYUBETU_NO_CD  =  '"	+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_line_cd");
					Keepb.setLineNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_line_cd";
						Keepb.setErrorMessage("指定されたラインコード" + map.get("40007").toString());
					}
				} else {
					Keepb.setLineNm(mstchk.getCdName());
				}
			} else {
				Keepb.setLineNm("");
			}

			// ユニットコード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getUnitCd().equals("")) {
				lst.add(" CODE_CD  = '" + Keepb.getSystemKb() + Keepb.getUnitCd() + "' ");
				lst.add(" AND SYUBETU_NO_CD  =  '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_unit_cd");
					Keepb.setUnitNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_unit_cd";
						Keepb.setErrorMessage("指定されたユニットコード" + map.get("40007").toString());
					}
				} else {
					Keepb.setUnitNm(mstchk.getCdName());
				}
			} else {
				Keepb.setUnitNm("");
			}

			// 仕入先コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getSiiresakiCd().equals("")) {
				lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
				lst.add(" and ");
				lst.add(" kanri_cd = '0000' ");
				lst.add(" and ");
				lst.add(" tosan_kb = '"+ mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' ");
				lst.add(" and ");
				lst.add(" SUBSTRING(siiresaki_cd,1,6) = '" + Keepb.getSiiresakiCd() + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SIIRESAKI", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_siiresaki_cd");
					Keepb.setSiiresakiNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_siiresaki_cd";
						Keepb.setErrorMessage("指定された仕入先コード" + map.get("40007").toString());
					}
				} else {
					Keepb.setSiiresakiNm(mstchk.getCdName());
				}
			} else {
				Keepb.setSiiresakiNm("");
			}

			// 商品コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			if (!Keepb.getSyohinCd().equals("")) {
//				↓↓2007.09.05 oohashi 本番に合わせて修正
				if(!Keepb.getBumonCd().equals(""))
				{
//				↑↑2007.09.05 oohashi 本番に合わせて修正
				lst.add(" bumon_cd = '" + StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true) + "' ");
				lst.add(" and ");
//				↓↓2007.09.05 oohashi 本番に合わせて修正
				}
//				↑↑2007.09.05 oohashi 本番に合わせて修正
				lst.add(" syohin_cd = '" + Keepb.getSyohinCd() + "' ");
//				↓↓2007.09.05 oohashi 本番に合わせて修正
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN", "HINMEI_KANJI_NA", lst, "0", "");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN", "HINMEI_KANJI_NA", lst, "1", "");
//				↑↑2007.09.05 oohashi 本番に合わせて修正
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_syohin_cd");
					Keepb.setSyohinNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						FirstFocusCtl = "tx_syohin_cd";
						Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
					}
				} else {
					Keepb.setSyohinNm(mstchk.getCdName());
				}
			} else {
				Keepb.setSyohinNm("");
			}

		}

		Keepb.setFirstFocus(FirstFocusCtl);
		Keepb.setCtrlColor(CtrlColor);
	}
}

