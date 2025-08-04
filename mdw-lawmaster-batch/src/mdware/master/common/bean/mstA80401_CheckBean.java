/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール・棚割表発行リクエスト表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール・棚割表発行リクエスト表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/08/12)初版作成
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
import mdware.common.util.DateChanger;
import mdware.common.util.DateUtility;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;
import mdware.portal.dictionary.RRiyoUserRiyoUserSyubetuKbDic;
//↑↑2006.12.20 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール・棚割表発行リクエスト表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール・棚割表発行リクエスト表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstA80401_CheckBean {

	public void Check(mst000101_DbmsBean db, mstA80201_KeepBean Keepb,
			mstA80301_KeepMeisaiBean Meisaib, DataHolder dataHolder,
			String GamenId, String FirstFocusCtl, String[] CtrlName,
			Map CtrlColor, SessionManager sessionManager,boolean chkFlg) throws SQLException,
			Exception {

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			// 画面内容を保存
//			↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
			Keepb.setEntryKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_entry_kb")).trim());	//依頼種別
//			↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).trim());	//部門
			Keepb.setHinbanCdFrom(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinban_cd_from")).trim());//品番
			Keepb.setHinbanCdTo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinban_cd_to")).trim());//品番
			Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_cd")).trim());//品種
			Keepb.setLineCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_cd")).trim());//ライン
			Keepb.setUnitCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_cd")).trim());//ユニット
			Keepb.setTenpocd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_cd")).trim());//店舗
			Keepb.setSendDT(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_send_dt")).trim());//出力希望日
			Keepb.setByNo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_by_no")).trim());//依頼BY No.
			Keepb.setTanadaiNb1From(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb1From")).trim());//棚割番号
			Keepb.setTanadaiNb1To(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb1To")).trim());//棚割番号
			Keepb.setTanadaiNb2From(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb2From")).trim());//棚割番号
			Keepb.setTanadaiNb2To(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb2To")).trim());//棚割番号
			Keepb.setTanadaiNb3From(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb3From")).trim());//棚割番号
			Keepb.setTanadaiNb3To(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb3To")).trim());//棚割番号
			Keepb.setTrPsTanaRequest(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_tanaRequest")).trim());//棚割外
			Keepb.setSofusakiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_sofusakiKb")).trim());//送付先
			Keepb.setKaiPageKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_kaiPageKb")).trim());//改ページ
			Keepb.setRequest_kb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("chk_request_kb1")).trim());//リクエス
			Keepb.setRequest_kb_tan(mst000401_LogicBean.chkNullString(dataHolder.getParameter("chk_request_kb2")).trim());//リクエス
			Keepb.setTanaJyoken(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanaJyoken")).trim());//棚割表
			Keepb.setCommentTx(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_commentTx")).trim());//コメント設定
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syori_kb")).trim());//処理状況
//			↓↓2006.12.19 H.Yamamoto カスタマイズ修正↓↓
			Keepb.setIraiNo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_irai_no")).trim());//依頼No
			Keepb.setTanadaiNb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb")).trim());//棚割番号
//			↑↑2006.12.19 H.Yamamoto カスタマイズ修正↑↑

			Keepb.setChangflg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

			Meisaib.setPageMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("movePage")).trim() );

			if (chkFlg){
//				↓↓2006.12.20 H.Yamamoto カスタマイズ修正↓↓
				String masterDt = RMSTDATEUtil.getMstDateDt();
				MdwareUserSessionBean SysUserBean = (MdwareUserSessionBean) sessionManager.getAttribute("userSession"); // ログインユーザー情報
				if(SysUserBean.getUserSyubetuKb().equals(RRiyoUserRiyoUserSyubetuKbDic.BUYER.getCode())){
					int masterYobi = DateUtility.getDayOfWeek(masterDt);
					String sendDtMin = "";
					// 木曜締（日～木曜日）の翌サイクル金曜日設定
					if (masterYobi >= 1 && masterYobi <= 5) { // 今週金曜日
						sendDtMin = DateChanger.addDate(masterDt, 6 - masterYobi);
					} else { // 翌週金曜日
						sendDtMin = DateChanger.addDate(masterDt, 13 - masterYobi);
					}
					if (Long.parseLong(Keepb.getSendDT().toString()) < Long.parseLong(sendDtMin)) { // 範囲外
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_send_dt");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							FirstFocusCtl = "tx_send_dt";
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("出力希望日は " + DateChanger.toSeparatorDate(sendDtMin) + " 以降の金曜日を指定してください。");
						}
					} else {
						int sendYobi = DateUtility.getDayOfWeek(Keepb.getSendDT().toString());
						if (sendYobi != 6) { // 金曜日以外
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_send_dt");
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								FirstFocusCtl = "tx_send_dt";
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("出力希望日は " + DateChanger.toSeparatorDate(sendDtMin) + " 以降の金曜日を指定してください。");
							}
						}
					}
				}
//				↑↑2006.12.20 H.Yamamoto カスタマイズ修正↑↑

				List lst = new ArrayList(); // マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

				// 部門コード存在チェック
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).equals("")) {
					lst = new ArrayList();
					mstchk = new mst000701_MasterInfoBean();
					lst.add(" CODE_CD = '"+ mst000401_LogicBean.chkNullString(
							StringUtility.charFormat(dataHolder.getParameter("tx_bumon_cd"), 4, "0",true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");

					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");

					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {

						// 部門コード存在エラー
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_bumon_cd");
						FirstFocusCtl = "tx_bumon_cd";
						Keepb.setBumonNm("");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された部門コード"+ map.get("40007").toString());
						}
					} else {
						Keepb.setBumonNm(mstchk.getCdName());
					}
				} else {
					Keepb.setBumonNm("");
				}

				// 品番コード（開始）存在チェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(Keepb.getHinbanCdFrom()).equals("")) {
					lst.add("  CODE_CD  = '"+ mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getHinbanCdFrom(), 4, "0",true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"	+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_hinban_cd_from");
						Keepb.setHinbanNmFrom("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_hinban_cd_from";
							Keepb.setErrorMessage("指定された品番コード（開始）" + map.get("40007").toString());
						}
					} else {
						Keepb.setHinbanNmFrom(mstchk.getCdName());
					}
				} else {
					Keepb.setHinbanNmFrom("");
				}

				// 品番コード（終了）存在チェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(Keepb.getHinbanCdTo()).equals("")) {
					lst.add("  CODE_CD  = '"+ mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getHinbanCdTo(), 4, "0",true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"	+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,
								"tx_hinban_cd_to");
						Keepb.setHinbanNmTo("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_hinban_cd_to";
							Keepb.setErrorMessage("指定された品番コード（終了）" + map.get("40007").toString());
						}
					} else {
						Keepb.setHinbanNmTo(mstchk.getCdName());
					}
				} else {
					Keepb.setHinbanNmTo("");
				}

				// 品種コード存在チェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hinsyu_cd")).equals("")) {
					lst.add("  CODE_CD  = '"+ mst000401_LogicBean.chkNullString(
							StringUtility.charFormat(dataHolder.getParameter("tx_hinsyu_cd"), 4, "0",true)) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"	+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_hinsyu_cd");
						Keepb.setHinsyuNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_hinsyu_cd";
							Keepb.setErrorMessage("指定された品種コード"+ map.get("40007").toString());
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
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_cd")).equals("")) {
					lst.add("  CODE_CD  = '"+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_line_cd")) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_line_cd");
						Keepb.setLineNm("");
						if (Keepb.getErrorFlg().equals(
								mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							FirstFocusCtl = "tx_line_cd";
							Keepb.setErrorMessage("指定されたラインコード"+ map.get("40007").toString());
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
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_cd")).equals("")) {
					lst.add("  CODE_CD  = '" +Keepb.getSysKb() +"' || '"+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_unit_cd")) + "' ");
					lst.add("  AND SYUBETU_NO_CD  =  '"	+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF","KANJI_RN", lst, "0", "");
					if (!mstchk.getExistenceFlg().equals(
							mst000101_ConstDictionary.FUNCTION_TRUE)) {

						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_unit_cd");
						Keepb.setUnitNm("");
						if (Keepb.getErrorFlg().equals(	mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
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

				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT))){
				 if (Keepb.getTenpocd().equals(StringUtility.cutString(mst000101_ConstDictionary.ALL_TENPO_CD,3,3)) ){
	                	Keepb.setTenpoNm("全店");
				} else {
						lst.clear();
						//lst.add( " tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpocd(),6,"000",true)  + "' " );
						lst.add( " tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpocd(),6,"0",true)  + "' " );
						//lst.add( " and tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' " );
						mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENPO","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
						Keepb.setTenpoNm(mstchk.getCdName());
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//店舗コード存在エラー
							mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_tenpo_cd" );
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								FirstFocusCtl = "tx_tenpo_cd";
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("指定された店舗コード"+map.get("40007").toString());
							}
						}
					}
				}else{
					Keepb.setTenpoNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_nm")).trim());
				}

				// ゴンドラ番号（個別）チェック
				if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tanadaiNb")).equals("")) {
					boolean OkFlg = true;
					StringBuffer gn = new StringBuffer();
					String gondoraNbL = Keepb.getTanadaiNb().trim();
					String[] gondoraNb = null;
					gondoraNb = gondoraNbL.split(",");
					for (int i = 0; i < gondoraNb.length; i++){
						if (gondoraNb[i] != null && gondoraNb[i].trim().length() > 0) {
							int gondora = StringUtility.string2int(gondoraNb[i]);
							if (gondora < 1 || gondora > 999) {
								OkFlg = false;
							} else {
								if(gn.length() > 0){
									gn.append(",");
								}
								gn.append(String.valueOf(gondora));
							}
						}
					}
					if (OkFlg) {
						Keepb.setTanadaiNb(gn.toString());
					} else {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tanadaiNb");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							FirstFocusCtl = "tx_tanadaiNb";
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("ゴンドラ番号は1～999の範囲内で指定してください。");
						}
					}
				}
			}

		}

		Keepb.setFirstFocus(FirstFocusCtl);
		Keepb.setCtrlColor(CtrlColor);
	}
}

