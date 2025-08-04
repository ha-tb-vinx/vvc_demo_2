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
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(実用)の画面表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(実用)の画面表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstB20301_PsRequestA07CheckBean {

	public void Check(mst000101_DbmsBean db, mstB20101_KeepBean Keepb,
			DataHolder dataHolder, String FirstFocusCtl, String[] CtrlName,
			Map CtrlColor, SessionManager sessionManager,boolean chkFlg) throws SQLException,Exception {

		List lst = new ArrayList();
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			// 画面内容を保存
			Keepb.setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).trim());	//部門
			Keepb.setHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinbancd")).trim());//品番
			Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")).trim());//品種
			Keepb.setLineCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_linecd")).trim());//ライン
			Keepb.setUnitCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitcd")).trim());//ユニット
			Keepb.setTenpoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenpocd")).trim());//店舗
			Keepb.setTenpoNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenpo_nm")).trim());//店舗名
			Keepb.setSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_senddt")).trim());//出力希望日
			Keepb.setByNo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_byno")).trim());//依頼BY No.
			Keepb.setTanawariBangouFm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanawaribangoufm")).trim());//棚割番号
			Keepb.setTanawariBangouTo(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tanawaribangouto")).trim());//棚割番号
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim());//処理状況

			// 部門コード存在チェック
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
			lst.add(" and "+ "code_cd =  '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true).trim() + "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
			Keepb.setBumon_nm(mstchk.getCdName());
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_bumoncd");
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定された部門コード" + map.get("40007").toString());

				}
			}

			// 品番存在チェック
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
			lst.add(" and "+ "code_cd = '" + StringUtility.charFormat(Keepb.getHinbanCd(),4,"0",true).trim() + "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
			Keepb.setHinbanNm(mstchk.getCdName());
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_hinbancd");
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus("ct_hinbancd");
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定された品番コード" + map.get("40007").toString());

				}
			}

			// 品種コード存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")).equals("")) {
				lst.clear();
				lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
				lst.add(" and "+ "CODE_CD = '" + StringUtility.charFormat(Keepb.getHinsyuCd(),4,"0",true).trim() + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
				Keepb.setHinsyuNm(mstchk.getCdName());
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_hinsyucd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_hinsyucd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された品種コード" + map.get("40007").toString());
					}
				}
			} else {
				Keepb.setHinsyuNm("");
			}

			// ライン存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_linecd")).equals("")) {
				lst.clear();
				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal) + "' ");
				lst.add(" and "+ "code_cd = '" + StringUtility.charFormat(Keepb.getLineCd(),4,"0",true).trim() + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
				Keepb.setLineNm(mstchk.getCdName());
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_linecd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_linecd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたラインコード" + map.get("40007").toString());
					}
				}
			} else {
				Keepb.setLineNm("");
			}

			//　ユニット存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitcd")).equals("")) {
				lst.clear();
				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
				lst.add(" and "+ "code_cd = '" + Keepb.getSystemKb() + StringUtility.charFormat(Keepb.getUnitCd(),4,"0",true).trim() + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
				Keepb.setUnitNm(mstchk.getCdName());
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_unitcd");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						Keepb.setFirstFocus("ct_unitcd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたユニットコード" + map.get("40007").toString());
					}
				}
			} else {
				Keepb.setUnitNm("");
			}

			//　店舗コード存在チェック
			if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT))){
				if (Keepb.getTenpoCd().equals(StringUtility.cutString(mst000101_ConstDictionary.ALL_TENPO_CD,3,3)) ){
	               	Keepb.setTenpoNm("全店");
				} else {
					lst.clear();
					lst.add( " tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpoCd(),6,"000",true)  + "' " );
					lst.add( " and tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' " );
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENPO","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
					Keepb.setTenpoNm(mstchk.getCdName());
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_tenpocd" );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("ct_tenpocd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された店舗コード"+map.get("40007").toString());
						}
					}
				}
			}

		}

	}

}
