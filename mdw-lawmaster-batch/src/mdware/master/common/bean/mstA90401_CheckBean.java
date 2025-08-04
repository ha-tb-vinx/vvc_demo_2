/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(単品指定)の画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(単品指定)登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005-2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2008.08.18)初版作成
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
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(単品指定)の画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(単品指定)登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005-2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author xubq
 * @version 1.0(2006.08.18)初版作成
 */
public class mstA90401_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
			mst000101_DbmsBean db,
			mstA90201_KeepBean Keepb,
			mstA90301_KeepMeisaiBean KeepMeisaib,
			DataHolder dataHolder,
			String FirstFocusCtl,
			String[] CtrlName,
			Map CtrlColor,
			SessionManager  sessionManager) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//初期Focus
		Keepb.setFirstFocus(FirstFocusCtl);

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				//画面内容を保存
				// 部門コード
				Keepb.setBumonCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_cd")).trim() );
				// 商品コード
				Keepb.setSyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohin_cd")).trim() );
				// 出力希望日
				Keepb.setSendDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_send_dt")).trim() );
				// 店舗コード
				Keepb.setTenpoCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_cd")).trim() );
				// プライスシール送付先
				Keepb.setPsSofusakiKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ps_sofusaki_kb")).trim() );
				// コメント設定
				Keepb.setCommentTx( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_comment_dt")).trim() );
				// 処理状況
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syori_kb")).trim() );
				// 商品情報ファイル名
				Keepb.setFileNm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_file")).trim() );
				// ボタンモード
				Keepb.setButtonMode( mst000401_LogicBean.chkNullString(dataHolder.getParameter("buttonMode")).trim() );

				//エラーチェック
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

				String CtlName = "tx_bumon_cd";

				//部門コード存在チェック
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)  + "' ");
				lst.add(" and ");
				lst.add("code_cd = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true)) + "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
				Keepb.setBumonNm( mstchk.getCdName() );
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
	    	        //部門コード存在エラー
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					Keepb.setBumonNm("");
					Keepb.setFirstFocus("tx_bumon_cd");
					Keepb.setErrorMessage("指定された部門コード"+map.get("40007").toString());
				} else {
					Keepb.setBumonNm( mstchk.getCdName() );
				}
				if(!"csv".equals( mst000401_LogicBean.chkNullString(dataHolder.getParameter("csv")).trim() )){
					//商品コード
					CtlName = "tx_syohin_cd";
					//商品の存在チェック
					lst.clear();
					lst.add("bumon_cd = '"  + mst000401_LogicBean.chkNullString(StringUtility.charFormat(Keepb.getBumonCd(), 4, "0", true)) + "' ");
					lst.add(" and ");
					lst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","hinmei_kanji_na", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1, "" );
					Keepb.setSyohinNm( mstchk.getCdName() );
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						//商品コード存在エラー
						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus( CtlName );
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40026").toString());
						}
					}

					//	 店舗コード
					CtlName = "tx_tenpo_cd";
					if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT))){
						 if (Keepb.getTenpoCd().equals(StringUtility.cutString(mst000101_ConstDictionary.ALL_TENPO_CD,3,3)) ){
			                	Keepb.setTenpoNm("全店");
						} else {
								lst.clear();
								//lst.add( " tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpoCd(),6,"000",true)  + "' " );
								lst.add( " tenpo_cd = '" + StringUtility.charFormat(  Keepb.getTenpoCd(),6,"0",true)  + "' " );
								mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENPO","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
								Keepb.setTenpoNm(mstchk.getCdName());
								if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
									//店舗コード存在エラー
									mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
									if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
										Keepb.setFirstFocus(CtlName);
										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
										Keepb.setErrorMessage("指定された店舗コード"+map.get("40007").toString());
									}
								}
							}
					} else {
						// 店舗名称
						Keepb.setTenpoNm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpo_nm")).trim() );
					}
				}
			}
		}
	}
	/**
	 * 明細行重複をチェックする by kema 06.09.08
	 *
	 * @param  mstA90301_KeepMeisaiBean 	KeepMeisaib	   ：キープビーン(明細用)
	 * @param  bumoncd                     String	   	   :部門コード
	 * @param  syohincd					String         :商品コード
	 * @param  tenpocd			 			String		   :店舗コード
	 * @return	true or false
	 *
	 */
	public boolean chkDuplicateList(mstA90301_KeepMeisaiBean KeepMeisaib, String bumoncd, String syohincd,
										String tenpocd) throws Exception {

		for (int i=0; i <  KeepMeisaib.getMeisai().size(); i++) {
			mstA90101_PsTanpinHakkouRequestBean bean = (mstA90101_PsTanpinHakkouRequestBean) KeepMeisaib.getMeisai().get(i);
			//照会モードから追加モードに変更した場合明細行が残るため add by kema 06.11.01
			if(bean.getBumonCd() == null){
				return true;
			}
			if(bean.getBumonCd().trim().equals(bumoncd.trim()) &&
				bean.getSyohinCd().trim().equals(syohincd.trim()) &&
				bean.getTenpoCd().trim().equals(tenpocd.trim())) {
				return false;
			}
		}
		return true;
	}

}
