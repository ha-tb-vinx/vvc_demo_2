package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst008901_GiftSelectCodeDictionary;

/**
 * <P>タイトル	：新ＭＤシステム マスタメンテナンス ギフトマスタ登録画面のデータ入力チェッククラス</P>
 * <P>説明		：新ＭＤシステム ギフトマスタ登録画面のデータ入力チェッククラス</P>
 * <P>著作権	：Copyright (c) 2005</p>
 * <P>会社名	：Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst880301_CheckBean
{


	/**
	 * 商品情報をセットします。<br>
	 * また、ヘッダーのＰＬＵ反映日がおかしい場合は、エラーメッセージをセットします。
	 *
	 *
	 * @param 	db		mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	SessionManager				ユーザ情報
	 * @param	dataHolder					リクエスパラメータ情報変数
	 * @param	Flg							0 : 全ての明細チェック<br>
	 * 										1 : 1ページ分のチェック
	 * @throws SQLException	SQL処理中例外
	 * @throws Exception		処理中例外
	 */
	public void setSyohinInfo(
					mst000101_DbmsBean db,
					mst880201_KeepBean Keepb,
					Map CtrlColor,
					SessionManager  sessionManager,
					DataHolder dataHolder,
					String Flg
					) throws Exception,SQLException {
		ResultSet rset = null;
		try{
			//画面内容を保存(明細部)
			List meisai = Keepb.getMeisai();
			// メニューバーアイコン取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			String strSelectedGyosyuCd = SysUserBean.getSelectedGyosyuCd();

			// 追加・更新処理
			mst880101_GiftDM  dbGiftDMIns = new mst880101_GiftDM();

			String pludate = Keepb.getHaneidate();
			if (!checkDate(Keepb, CtrlColor, pludate, 0, "1", "0")) {
				pludate = Keepb.getMstDateNext();
			}
			// Flgによって変更
			int startCnt = 0;
			int endCnt = 0;
			if ("0".equals(Flg) ) {
				startCnt = 1;
				endCnt = meisai.size();
			}else{
				startCnt = Keepb.getStartRowInPage();
				endCnt = Keepb.getEndRowInPage();
			}
			Map syohinmap = new HashMap();
			Map giftmap = new HashMap();
			int errCnt;
			for (int i = startCnt; i <= endCnt; i++) {

				mst880101_GiftBean mb = (mst880101_GiftBean)meisai.get(i-1);
				String shohin_cd = mst000401_LogicBean.chkNullString(mb.getSyohinCd()).trim();
				String gift_cd = mst000401_LogicBean.chkNullString(mb.getGiftCd()).trim();
				if (shohin_cd.length() == 0) {
					if (!mst000101_ConstDictionary.PROCESS_UPDATE.equals(Keepb.getProcessingDivision())) {
						continue;
					}else{
						//修正時、商品が空に設定されている場合のレコードは、ギフトマスタに登録されている商品コードを再設定表示する。
						rset = db.executeQuery(dbGiftDMIns.getGiftMastaInfoSql(gift_cd,"0","0"));
						if (rset.next()) {
							shohin_cd = mst000401_LogicBean.chkNullString(rset.getString("SYOHIN_CD")).trim();
							//すでに削除されている可能性あり
							if (shohin_cd.length() == 0) continue;
							mb.setSyohinCd(shohin_cd);
							mb.setInsertTs(mst000401_LogicBean.chkNullString(rset.getString("INSERT_TS")).trim());
							mb.setInsertUserId(mst000401_LogicBean.chkNullString(rset.getString("INSERT_USER_ID")).trim());
							mb.setUpdateTs(mst000401_LogicBean.chkNullString(rset.getString("UPDATE_TS")).trim());
							mb.setUpdateUserId(mst000401_LogicBean.chkNullString(rset.getString("UPDATE_USER_ID")).trim());
						}
					}
				}
				//リクエストパラメータ
				if (shohin_cd.equals(dataHolder.getParameter("h_syohin_cd"))) {
					//変更なし
					continue;
				}


				String henko_date = mst000401_LogicBean.chkNullString(mb.getHenkoDt());
				if (henko_date.length() == 0 || !checkDate(Keepb, CtrlColor, henko_date, i-1, "0", "1")) {
					henko_date = pludate;
				}


				rset = db.executeQuery(dbGiftDMIns.getSyohinInfoSql(shohin_cd, henko_date, strSelectedGyosyuCd));

				if (rset.next()) {
					mb.setSyohinkb(mst000401_LogicBean.chkNullString(rset.getString("SYOHIN_KB")).trim());
					mb.setJanCd(mst000401_LogicBean.chkNullString(rset.getString("JAN_CD")).trim());
					mb.setSyohinNa(mst000401_LogicBean.chkNullString(rset.getString("SYOHIN_NA")).trim());
					mb.setBaitankaVl(mst000401_LogicBean.chkNullString(rset.getString("BAITANKA_VL")).trim());
					mb.setHinsyuCd(mst000401_LogicBean.chkNullString(rset.getString("HINSYU_CD")).trim());
					mb.setHinsyuNa(mst000401_LogicBean.chkNullString(rset.getString("HINSYU_NA")).trim());
					mb.setHankuCd(mst000401_LogicBean.chkNullString(rset.getString("HANKU_CD")).trim());
					mb.setHankuNa(mst000401_LogicBean.chkNullString(rset.getString("HANKU_NA")).trim());
					mb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(rset.getString("KIKAKU_KANJI_NA")).trim());

				}else{
					mb.setSyohinkb("");
					mb.setJanCd("");
					mb.setSyohinNa("");
					mb.setBaitankaVl("");
					mb.setHinsyuCd("");
					mb.setHinsyuNa("");
					mb.setHankuCd("");
					mb.setHankuNa("");
					mb.setKikakuKanjiNa("");

				}
				//修正時は、チェックしていないとまずくなる
				// 選択データのみのチェック
				if (mb.getSentaku() != null && mb.getSentaku().equals(mst000101_ConstDictionary.RECORD_EXISTENCE)) {
					if (mst000101_ConstDictionary.PROCESS_INSERT.equals(Keepb.getProcessingDivision())) {
						this.insCheck(db, Keepb,CtrlColor,dbGiftDMIns, mb, i-1);
					}else if (mst000101_ConstDictionary.PROCESS_UPDATE.equals(Keepb.getProcessingDivision())) {
						this.upCheck(db, Keepb,CtrlColor,dbGiftDMIns, mb, i-1);
					}else if (mst000101_ConstDictionary.PROCESS_DELETE.equals(Keepb.getProcessingDivision())) {
						this.delCheck(db, Keepb,CtrlColor,dbGiftDMIns, mb, i-1);
					}
					if (mst000101_ConstDictionary.PROCESS_INSERT.equals(Keepb.getProcessingDivision()) ||
							mst000101_ConstDictionary.PROCESS_UPDATE.equals(Keepb.getProcessingDivision())) {
						//更新前チェック
						checkSyohin(db, Keepb, CtrlColor, dbGiftDMIns, mb, i-1);
					}
					String getgift_cd = (String)giftmap.get(gift_cd);
					if (getgift_cd != null && getgift_cd.length() > 0) {
						errCnt = i -1;
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("入力されているギフトコードは、重複しています。");
						Keepb.setFirstFocus("tx_gift_cd" + errCnt );
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + errCnt);
					}

					String getsyohin_cd = (String)syohinmap.get(shohin_cd);
					if (getsyohin_cd != null && getsyohin_cd.length() > 0) {
						errCnt = i -1;
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("入力されている商品コードは、重複しています。");
						Keepb.setFirstFocus("tx_syohin_cd" + i);
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_syohin_cd" + errCnt);
					}

					syohinmap.put(shohin_cd, String.valueOf(i));
					giftmap.put(gift_cd, String.valueOf(i));
				}

			}

		}catch(SQLException sqe) {
			throw sqe;
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 処理区分＝削除時のギフトコード存在チェック
	 *
	 * @param 	db		mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	dbGiftDMIns					mst880101_GiftDM
	 * @param 	dbBeanIns					mst880101_GiftBean
	 * @param 	i							明細行番号
	 * @throws　SQLException	SQL処理中例外
	 * @throws　Exception	処理中例外
	 */
	private void delCheck(
		mst000101_DbmsBean db,
		mst880201_KeepBean Keepb,
		Map CtrlColor,
		mst880101_GiftDM dbGiftDMIns,
		mst880101_GiftBean dbBeanIns,
		int i) throws Exception{

		try{
			//ギフトコードの存在チェック
			String deleteFlg = getDeleteFLG(db, dbGiftDMIns, dbBeanIns.getGiftCd(), "0", "0");
			if (deleteFlg == null && deleteFlg.length() == 0 ) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("他のユーザによって、更新対象データは削除されています。");
				Keepb.setFirstFocus("tx_gift_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + i);
			}else if (mst000801_DelFlagDictionary.IRU.getCode().equals(deleteFlg)) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("他のユーザによって、更新対象データは削除されています。");
				Keepb.setFirstFocus("tx_gift_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + i);
			}
			String henko_date = dbBeanIns.getHenkoDt();
			if (henko_date == null || henko_date.length() == 0) {
				henko_date = Keepb.getHaneidate();
			}
			//登録のためのPLU反映日
			dbBeanIns.setHenkoDt(henko_date);
			checkDate(Keepb,CtrlColor,henko_date,i,"0", "0");

		}catch(SQLException sqe) {
			throw sqe;
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 処理区分＝更新時のギフトコード存在チェック
	 *
	 * @param 	db		mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	dbGiftDMIns					mst880101_GiftDM
	 * @param 	dbBeanIns					mst880101_GiftBean
	 * @param 	i							明細行番号
	 * @throws Exception	処理中例外
	 */
	private void upCheck(
		mst000101_DbmsBean db,
		mst880201_KeepBean Keepb,
		Map CtrlColor,
		mst880101_GiftDM dbGiftDMIns,
		mst880101_GiftBean dbBeanIns,
		int i) throws Exception{

		try{
			//ギフトコードの存在チェック
			String deleteFlg = getDeleteFLG(db, dbGiftDMIns,dbBeanIns.getGiftCd(), "0", "0");
			//ギフトコードがないので、修正はできない
			if ( deleteFlg == null || deleteFlg.length() == 0 ) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("他のユーザによって、更新対象データは削除されています。");
				Keepb.setFirstFocus("tx_gift_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + i);
			}else if (mst000801_DelFlagDictionary.IRU.getCode().equals(deleteFlg)) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("他のユーザによって、更新対象データは削除されています。");
				Keepb.setFirstFocus("tx_gift_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + i);
			}

		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 処理区分＝新規時のギフトコード存在チェック
	 *
	 * @param 	db		mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	dbGiftDMIns					mst880101_GiftDM
	 * @param 	dbBeanIns					mst880101_GiftBean
	 * @param 	i							明細行番号
	 * @throws Exception	処理中例外
	 */
	private void insCheck(
		mst000101_DbmsBean db,
		mst880201_KeepBean Keepb,
		Map CtrlColor,
		mst880101_GiftDM dbGiftDMIns,
		mst880101_GiftBean dbBeanIns,
		int i) throws Exception{
		try{
			//ギフトコードの存在チェック
			String deleteFlg = getDeleteFLG(db,dbGiftDMIns,dbBeanIns.getGiftCd(), "0", "0");
			if (deleteFlg != null && deleteFlg.length() > 0 ) {
				if (mst000801_DelFlagDictionary.IRU.getCode().equals(deleteFlg)) {
					//デリートフラグのセット
					dbBeanIns.setDeleteFg(deleteFlg);
				}else{
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたギフトコードは、すでに登録されています。");
					Keepb.setFirstFocus("tx_gift_cd" + i);
					//背景色設定
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_gift_cd" + i);
				}
			}

		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 変更日（ＰＬＵ反映日）が正しい日付になっているかをチェックします。
	 *
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	henko_date			チェックする日付
	 * @param	i					明細の番号
	 * @param	meisaiFlg			明細かヘッダーかを示すフラグ
	 * 								0 :	明細
	 * 								1:　ヘッダー
	 * @param	setFlg			エラーセットするかどうかのフラグ
	 * 								0 :	セットする
	 * 								1:　セットしない
	 * @return　true:変更日付チェックＯＫ<br>
	 * 			 false:変更日付チェックエラー
	 *
	 * @throws Exception 処理中例外
	 */
	private boolean checkDate(
		mst880201_KeepBean Keepb,
		Map CtrlColor,
		String henko_date,
		int i,
		String meisaiFlg,
		String setFlg) throws Exception {

		boolean result = false;
		try{
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
			//1.日付となっているか？
			DateChanger datechanger = new DateChanger();
			Calendar cal = datechanger.getCalendar(henko_date);
			String date = cal.toString();
			if (date == null || date.length() == 0 || !dateCheck(henko_date)) {
				if ("0".equals(setFlg)) {
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("0006").toString());
					if ("0".equals(meisaiFlg)) {
						Keepb.setFirstFocus("ct_sentaku" + i);
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_henko_date" + i);
					}else{
						Keepb.setFirstFocus("tx_haneidt");
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_haneidt");
					}
				}
				return false;
			}
			Calendar yukocal = datechanger.getCalendar(Keepb.getMstDateNext());
			if (!Keepb.getMstDateNext().equals(henko_date) && !yukocal.before(cal)){
				if ("0".equals(setFlg)) {
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("反映日は、"+ Keepb.getMstDateNext() +"以降です。"+ Keepb.getMstDateNext() +"以降の日付を指定してください。");
					if ("0".equals(meisaiFlg)) {
						Keepb.setFirstFocus("ct_sentaku" + i);
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_henko_date" + i);
					}else{
						Keepb.setFirstFocus("tx_haneidt");
						//背景色設定
						mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_haneidt");
					}
				}
				return false;
			}
			result = true;
		}catch(Exception e) {
			result = false;
		}
		return result;

	}

	/**
	 * 日付となっているかの妥当性チェック
	 *
	 * @param yyyyMMdd
	 * @return true:OK
	 * 			false:エラー
	 */
	private boolean dateCheck(String yyyyMMdd) {
		boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setLenient(false);      // 日付を厳密に解釈します。
		Calendar cal = sdf.getCalendar();
		try {
			cal.setTime( sdf.parse( yyyyMMdd + "000000" ));
			result = true;
		}catch(Exception e){
			result = false;
		}
		return result;
	}

	/**
	 * ヘッダー情報をセットします。
	 *
	 * @param 	db		mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	SessionManager				ユーザ情報
	 *
	 * @throws　SQLException	SQL処理中例外
	 * @throws　Exception	処理中例外
	 */
	public void setSelectName(
			mst000101_DbmsBean db,
			mst880201_KeepBean Keepb,
			Map CtrlColor,
			SessionManager  sessionManager
			) throws Exception,SQLException {

		try{

			//処理区分を取得。
			String syoribkstr = Keepb.getProcessingDivision();
			String selectkb = mst000401_LogicBean.chkNullString(Keepb.getSelectkb());
			if ( mst000101_ConstDictionary.PROCESS_INSERT.equals(syoribkstr) || mst008901_GiftSelectCodeDictionary.SITEINASHI.getCode().equals(selectkb)) {
				Keepb.setSelectkb(mst008901_GiftSelectCodeDictionary.SITEINASHI.getCode());
				Keepb.setSelectcd("");
				Keepb.setGiftCd("");
				Keepb.setSyohincd("");
				Keepb.setSelectna("");
				return;
			}

			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			List lst = new ArrayList();
			mst000701_MasterInfoBean masterb = new mst000701_MasterInfoBean();
			String name = "";
			if (mst008901_GiftSelectCodeDictionary.HANKU.getCode().equals(selectkb)) {
				String hanku_cd = mst000401_LogicBean.chkNullString(Keepb.getSelectcd());
				if (hanku_cd.length() > 0) {
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' AND ");
					lst.add("CODE_CD = '" + hanku_cd + "' ");
					masterb = mst000401_LogicBean.getMasterCdName2(db,"R_NAMECTF","KANJI_NA",lst,"0", "");
					//販区名
					name = masterb.getCdName();
					if (name == null || name.length()==0) {
						setErrorMessage(Keepb, CtrlColor, selectkb);
					}
				}
			}else if (mst008901_GiftSelectCodeDictionary.HINSYU.getCode().equals(selectkb)) {
				String hinsyu_cd = mst000401_LogicBean.chkNullString(Keepb.getSelectcd());
				if (hinsyu_cd.length() > 0) {
					lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' AND ");
					lst.add("CODE_CD = '" + hinsyu_cd + "' ");
					masterb = mst000401_LogicBean.getMasterCdName2(db,"R_NAMECTF","KANJI_NA",lst,"0", "");
					//品種名
					name = masterb.getCdName();
					if (name == null || name.length()==0) {
						setErrorMessage(Keepb, CtrlColor, selectkb);
					}
				}
			}else if (mst008901_GiftSelectCodeDictionary.SYOHIN.getCode().equals(selectkb)) {
				ResultSet rset = null;
				mst880101_GiftDM  dbGiftDMIns = new mst880101_GiftDM();
				String shohin_cd = mst000401_LogicBean.chkNullString(Keepb.getSyohincd());
				String date = Keepb.getMstDateNext();
				mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();
				SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
				String strSelectedGyosyuCd = SysUserBean.getSelectedGyosyuCd();
				rset = db.executeQuery(dbGiftDMIns.getSyohinInfoSql(shohin_cd, date, strSelectedGyosyuCd));
				if (rset.next()) {
					name = mst000401_LogicBean.chkNullString(rset.getString("SYOHIN_NA")).trim();
				}
				if (name == null || name.length()==0) {
					setErrorMessage(Keepb, CtrlColor, selectkb);
				}
			}
			Keepb.setSelectna(name);
		}catch(SQLException sqe) {
			throw sqe;
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * ヘッダー情報時のエラーメッセージをセットします。
	 * @param	Keepb	mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor					背景色情報
	 * @param 	selectkb					選択コード区分
	 */
	private void setErrorMessage(mst880201_KeepBean Keepb, Map CtrlColor, String selectkb) {
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
		if (mst008901_GiftSelectCodeDictionary.HANKU.getCode().equals(selectkb)) {
			Keepb.setErrorMessage(map.get("40022").toString());
			Keepb.setFirstFocus("ct_select_cd");
			//背景色設定
			mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_select_cd");
		}else if (mst008901_GiftSelectCodeDictionary.HINSYU.getCode().equals(selectkb)) {
			Keepb.setErrorMessage(map.get("40023").toString());
			Keepb.setFirstFocus("ct_select_cd");
			//背景色設定
			mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_select_cd");
		}else if (mst008901_GiftSelectCodeDictionary.SYOHIN.getCode().equals(selectkb)) {
			Keepb.setErrorMessage(map.get("40026").toString());
			Keepb.setFirstFocus("ct_syohin_cd");
			//背景色設定
			mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_syohin_cd");

		}
	}

	/**
	 * 以下の登録、更新前チェックを行います。
	 *
	 * @param 	db			mst000101_DbmsBean	マスタ管理用ＤＢ
	 * @param	Keepb		mst880201_KeepBean	ギフトマスタ画面情報
	 * @param	CtrlColor						背景色情報
	 * @param 	dbGiftDMIns	mst880101_GiftDM
	 * @param 	mb			mst880101_GiftBean  リクエストが格納されている変数
	 * @param  i								Listの配列番号
	 * @return true:チェックOK (登録、更新可能)<br>
	 * 			false:チェックエラー(登録、更新不可)
	 *
	 * @throws　SQLException	SQL処理中例外
	 * @throws　Exception	処理中例外
	 */
	private void checkSyohin(mst000101_DbmsBean db, mst880201_KeepBean Keepb, Map CtrlColor, mst880101_GiftDM dbGiftDMIns, mst880101_GiftBean mb, int i) throws Exception {
		try{
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
			ResultSet rset = null;
			String henko_date = mb.getHenkoDt();
			if (henko_date == null || henko_date.length() == 0) {
				henko_date = Keepb.getHaneidate();
			}
			//登録のためのPLU反映日
			mb.setHenkoDt(henko_date);
			if (!checkDate(Keepb,CtrlColor,henko_date,i, "0", "0")) {
				return;
			}

			String shohin_cd = mb.getSyohinCd();
			//商品コードは画面の入力情報なので、存在しない場合は、販区がないはず
			String hanku_cd = mb.getHankuCd();
			int test = hanku_cd.length();
			if (hanku_cd.length() == 0) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage(map.get("40026").toString());
				Keepb.setFirstFocus("ct_sentaku" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_syohin_cd" + i);
				return;
			}

			//商品チェック
			//１．商品区分＝１又はＪＡＮ＝２１の場合は、ＰＬＵ送信対象外
			String syohin_ku =  mb.getSyohinkb();
			String jan_cd = mb.getJanCd();
			if (!"1".equals(syohin_ku) || "21".equals(jan_cd)) {
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("PLU送信対象外商品です。");
				Keepb.setFirstFocus("tx_syohin_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_syohin_cd" + i);
				return;
			}

			//商品コードのギフトマスタ存在チェック
			String deleteFlg = getDeleteFLG(db,dbGiftDMIns, shohin_cd, "1", "1");
			rset = db.executeQuery(dbGiftDMIns.getGiftMastaInfoSql(shohin_cd,"1","1"));
			if ( rset.next()) {
				deleteFlg = mst000401_LogicBean.chkNullString(rset.getString("DELETE_FG"));
			}

			//商品コードのチェック
			if ( deleteFlg != null && deleteFlg.length() > 0 ) {
				if (mst000101_ConstDictionary.PROCESS_UPDATE.equals(Keepb.getProcessingDivision())) {
					String gift_cd = mst000401_LogicBean.chkNullString(rset.getString("GIFT_CD"));
					if ( gift_cd.equals(mb.getGiftCd()) ) {
							return;
					}
				}
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("この商品は、すでにギフトマスタ登録されています。");
				Keepb.setFirstFocus("tx_syohin_cd" + i);
				//背景色設定
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "tx_syohin_cd" + i);
				return;
			}
		}catch(SQLException sqe) {
			throw sqe;

		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * ギフトコード、商品コードがマスタに存在するかどうかをチェックし、<br>
	 * 存在するときは、DELETE_FLGの結果を返します。
	 *
	 * @param 	dbGiftDMIns	mst880101_GiftDM
	 * @param  checkCD		チェックするコード
	 * @param  FLG			0:ギフトコード
	 * 						1:商品コード
	 * @param  DelFlg		0:デリートチェックしない
	 * 						1:デリートチェックする
	 * @return DELETE_FG 削除フラグの値を返します。<br>
	 * 			取得できなかった場合は、nullを返します。
	 * @throws　SQLException	SQL処理中例外
	 * @throws　Exception	処理中例外
	 */
	private String getDeleteFLG(
		mst000101_DbmsBean db,
		mst880101_GiftDM dbGiftDMIns,
		String checkCD,
		String FLG,
		String DelFlg) throws SQLException,Exception {

		String result = null;
		ResultSet rset = null;
		try{
			rset = db.executeQuery(dbGiftDMIns.getGiftMastaInfoSql(checkCD,FLG,DelFlg));
			if (rset.next()) {
				result = rset.getString("DELETE_FG");
			}else{
				result = null;
			}

			if(rset != null){
				rset.close();
			}

		}catch(SQLException sqe){
			result = null;
			throw sqe;
		}catch(Exception e){
			result = null;
			throw e;
		}
		return result;
	}


}
