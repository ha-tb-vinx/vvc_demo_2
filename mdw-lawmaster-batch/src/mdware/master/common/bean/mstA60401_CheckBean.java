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

/**
 * <p>
 * タイトル: 新ＭＤシステム（マスター管理）本部投入数量確認の画面表示データチェック用クラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用する本部投入数量確認の画面表示データチェック用クラス
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
public class mstA60401_CheckBean {

	public void Check(mst000101_DbmsBean db, mstA60201_KeepBean Keepb,
			mstA60301_KeepMeisaiBean Meisaib, DataHolder dataHolder,
			String GamenId, String FirstFocusCtl, String[] CtrlName,
			Map CtrlColor, SessionManager sessionManager,boolean chkFlg) throws SQLException,
			Exception {

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if (Keepb.getErrorFlg()
				.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			// 画面内容を保存
//			↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
			Keepb.setSysKb(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("gyosyu_kb")).trim());
//			↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_bumon_cd")).trim());
			Keepb.setHinbanCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hinban_cd")).trim());
			Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hinsyu_cd")).trim());
			Keepb.setLineCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_line_cd")).trim());
			Keepb.setUnitCd(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_unit_cd")).trim());
			Keepb.setTanadaiNbFrom(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_tanadaiNb_from")).trim());
			Keepb.setTanadaiNbTo(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_tanadaiNb_to")).trim());
			Keepb.setHachuStDt(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hachuDt_from")).trim());
			Keepb.setHachuEnDt(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_hachuDt_to")).trim());
			Keepb.setNohinStDt(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_nohinDt_from")).trim());
			Keepb.setNohinEnDt(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("tx_nohinDt_to")).trim());
			Keepb.setGyotaiKb(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("ct_gyotaiKb")).trim());
			Keepb.setHyouji(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("rb_hyouji")).trim());
			if ("true".equals(mst000401_LogicBean.chkNullString(
					dataHolder.getParameter("h_inputflg")).trim())) {
				Keepb.setInputflg(true);
			} else {
				Keepb.setInputflg(false);
			}
			//===BEGIN=== Add by kou 2006/11/13
			Keepb.setSort(mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("rd_sort")).trim());
			Keepb.setTenpoCd(mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("tx_tenpo_cd")).trim());
			Keepb.setTenpoNm(mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("tx_tenpo_nm")).trim());
			Keepb.setTenGroupNo(mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("ct_tenGroupNo_cd")).trim());
			Keepb.setTenGroupNm(mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("ct_tenGroupNo_nm")).trim());
			//===END=== Add by kou 2006/11/13

			Meisaib.setPageMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("movePage")).trim() );

			if (chkFlg){
				List lst = new ArrayList(); // マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

				// 部門コード存在チェック
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_bumon_cd")).equals("")) {
					String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add(" CODE_CD = '"
							+ mst000401_LogicBean.chkNullString(StringUtility
									.charFormat(dataHolder
											.getParameter("tx_bumon_cd"), 4, "0",
											true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  = '"
							+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
							"KANJI_RN", lst, "0", "");

					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						// 部門コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_bumon_cd");
						FirstFocusCtl = "tx_bumon_cd";
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
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_hinban_cd")).equals("")) {
					lst.add("  CODE_CD  = '"
							+ mst000401_LogicBean.chkNullString(StringUtility
									.charFormat(dataHolder
											.getParameter("tx_hinban_cd"), 4, "0",
											true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"
							+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
							"KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_hinban_cd");
						Keepb.setHinbanNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb
									.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_hinban_cd";
							Keepb.setErrorMessage("指定された品番コード"
									+ map.get("40007").toString());
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
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_hinsyu_cd")).equals("")) {
					lst.add("  CODE_CD  = '"
							+ mst000401_LogicBean.chkNullString(StringUtility
									.charFormat(dataHolder
											.getParameter("tx_hinsyu_cd"), 4, "0",
											true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"
							+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
							"KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_hinsyu_cd");
						Keepb.setHinsyuNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb
									.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_hinsyu_cd";
							Keepb.setErrorMessage("指定された品種コード"
									+ map.get("40007").toString());
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
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_line_cd")).equals("")) {
					lst.add("  CODE_CD  = '"
							+ mst000401_LogicBean.chkNullString(dataHolder
									.getParameter("tx_line_cd")) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"
							+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
							"KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_line_cd");
						Keepb.setLineNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb
									.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_line_cd";
							Keepb.setErrorMessage("指定されたラインコード"
									+ map.get("40007").toString());
						}
					} else {
						Keepb.setLineNm(mstchk.getCdName());
					}
				} else {
					Keepb.setLineNm("");
				}

//				↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
//				//システム区分の取得
//				if ("".equals(mst000401_LogicBean.chkNullString(Keepb.getSysKb()))) {
//					Keepb.setSysKb(getSysKb(db, Keepb));
//				}
//				↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑

				// ユニットコード存在チェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_unit_cd")).equals("")) {
					lst.add("  CODE_CD  = '" +Keepb.getSysKb() +"' || '"
							+ mst000401_LogicBean.chkNullString(dataHolder
									.getParameter("tx_unit_cd")) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"
							+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF",
							"KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_unit_cd");
						Keepb.setUnitNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb
									.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_unit_cd";
							Keepb.setErrorMessage("指定されたユニットコード"
									+ map.get("40007").toString());
						}
					} else {
						Keepb.setUnitNm(mstchk.getCdName());
					}
				} else {
					Keepb.setUnitNm("");
				}

				//===BEGIN=== Add by kou 2006/11/15
				//店舗コードのチェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("tx_tenpo_cd")).equals("")) {

					lst.add("  TENPO_CD  = '000' || '" + mst000401_LogicBean.chkNullString(
												dataHolder.getParameter("tx_tenpo_cd")) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENPO",
							"KANJI_NA", lst, "0", "");

					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_tenpo_cd");
						Keepb.setTenpoNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_tenpo_cd";
							Keepb.setErrorMessage("指定された店舗コード"
									+ map.get("40007").toString());
						}
					} else {
						Keepb.setTenpoNm(mstchk.getCdName());
					}
				} else {
					Keepb.setTenpoNm("");
				}
				//店配列のチェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(
						dataHolder.getParameter("ct_tenGroupNo_cd")).equals("")) {

					lst.add("  		BUMON_CD  = '0' || '" + mst000401_LogicBean.chkNullString(
												dataHolder.getParameter("tx_bumon_cd")) + "' ");
					lst.add("  AND	GROUPNO_CD  = '" + mst000401_LogicBean.chkNullString(
												dataHolder.getParameter("ct_tenGroupNo_cd")) + "' ");
					lst.add("  AND	YOTO_KB  = '1' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO",
							"NAME_NA", lst, "0", "");

					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"ct_tenGroupNo_cd");
						Keepb.setTenGroupNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "ct_tenGroupNo_cd";
							Keepb.setErrorMessage("指定された店配列コード"
									+ map.get("40007").toString());
						}
					} else {
						Keepb.setTenGroupNm(mstchk.getCdName());
					}
				} else {
					Keepb.setTenGroupNm("");
				}
				//===END=== Add by kou 2006/11/15

			}

		}

		Keepb.setFirstFocus(FirstFocusCtl);
		Keepb.setCtrlColor(CtrlColor);
	}

//	↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * システム区分の取得 <br>
//	 * Ex)<br>
//	 *
//	 * @param mst000101_DbmsBean
//	 *            db : dbインスタンス
//	 * @param mstA60201_KeepBean
//	 *            Keepb
//	 */
//	public String getSysKb(mst000101_DbmsBean db, mstA60201_KeepBean Keepb)
//			throws Exception, SQLException {
//		ResultSet rest = null; // 抽出結果(ResultSet)
//		String ret = "";
//		String sql = "select min(SYSTEM_KB) as SYSTEM_KB from R_SYOHIN_TAIKEI "
//				+ "where " + " BUMON_CD = '"
//				+ StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true)
//				+ "'" + " AND " + " HINBAN_CD = '"
//				+ StringUtility.charFormat(Keepb.getHinbanCd(), 4, "0", true)
//				+ "' " + "group by  BUMON_CD ,HINBAN_CD ";
//		try {
//			rest = db.executeQuery(sql); // 抽出結果(ResultSet)
//			while (rest.next()) {
//				ret = rest.getString("SYSTEM_KB");
//			}
//
//		} catch (SQLException sqle) {
//			throw sqle;
//		} catch (Exception e) {
//			throw e;
//		}finally {
//			if (null != rest) {
//				rest.close();
//				rest = null;
//			}
//		}
//
//		return ret;
//
//	}
//	↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑
}

