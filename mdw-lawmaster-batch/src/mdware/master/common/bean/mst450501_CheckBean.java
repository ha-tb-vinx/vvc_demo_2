/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst450501_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */

	// ↓↓2006.06.30 maojm カスタマイズ修正↓↓
	//	public void Check(mst000101_DbmsBean db, mst450301_KeepBean Keepb ,mst450401_KeepMeisaiBean Meisaib ,DataHolder dataHolder ,String kengenCd
	//						,String GamenId ,String FirstFocusCtl ,String[] CtrlName ,Map CtrlColor ,SessionManager sessionManager) throws SQLException,Exception {
	public void Check(
		mst000101_DbmsBean db,
		mst450301_KeepBean Keepb,
		mst450401_KeepMeisaiBean Meisaib,
		DataHolder dataHolder,
		String GamenId,
		String FirstFocusCtl,
		String[] CtrlName,
		Map CtrlColor,
		SessionManager sessionManager)
		throws SQLException, Exception
	{
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		boolean sentakuFlg = false; // 選択
		int tenpoNum = 0; // 店舗数
		mst450101_TenbetuSiiresakiListBean dataGamen = new mst450101_TenbetuSiiresakiListBean();

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		//エラーチェック
		//個別に権限取得
		//		Map Kengen = mst000401_LogicBean.getKengen(db,kengenCd, GamenId);
		//	    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
		if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
		{
			//画面内容を保存
			//		    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
			//			Keepb.setKanriKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim() );
			//			Keepb.setKanriCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")).trim() );
			//			Keepb.setKanriNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanri_nm")).trim() );
			//			Keepb.setCopyKanriCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanricd")).trim() );
			//			Keepb.setCopyKanriNm( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copykanri_nm")).trim() );
			Keepb.setBumonCd(
				mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")).trim());
			Keepb.setBumonNm(
				mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumonnm")).trim());
			Keepb.setCopyBumonCd(
				mst000401_LogicBean
					.chkNullString(dataHolder.getParameter("ct_copybumoncd"))
					.trim());
			Keepb.setCopyBumonNm(
				mst000401_LogicBean
					.chkNullString(dataHolder.getParameter("ct_copybumonnm"))
					.trim());
			//		    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
			Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakicd")).trim());
			Keepb.setTenSiiresakiKanriNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakinm")).trim());
			//		    ↓↓ add by kema 06.11.10
			Keepb.setGroupNoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_groupnocd")));
			Keepb.setGroupNoNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_groupnonm")));
			Keepb.setCopyTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copytensiiresakicd")));
			Keepb.setCopyTenSiiresakiKanriNm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copytensiiresakinm")));
			//		    ↑↑ add by kema 06.11.10
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim());
			//画面変更フラグ
			Keepb.setChgFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mst45_chgflg")).trim());

			//画面明細内容を保存
			List mlst = Meisaib.getMeisai();
			mst450101_TenbetuSiiresakiListBean mb = new mst450101_TenbetuSiiresakiListBean();
			//			 ↓↓2006.07.05 maojm カスタマイズ修正↓↓
			int startrow = 1;
			int endrow = 0;
			if (!"".equals(Meisaib.getStartRowInPage()) && Meisaib.getStartRowInPage() != null)
			{
				startrow = Integer.parseInt(Meisaib.getStartRowInPage());
			}
			if (!"".equals(Meisaib.getEndRowInPage()) && Meisaib.getEndRowInPage() != null)
			{
				endrow = Integer.parseInt(Meisaib.getEndRowInPage());
			}
			//			 ↑↑2006.07.05 maojm カスタマイズ修正↑↑
			//			String[] sentaku = dataHolder.getParameterValues("ct_h_sentaku");
			//			for (int i=0; i < sentaku.length; i++) {
			if (dataHolder.getParameter("buttonMode") != null
				&& "action".equals(dataHolder.getParameter("buttonMode")))
			{
				for (int i = (startrow - 1); i < endrow; i++)
				{
					boolean allCheckFlag = false;
					if (mlst.size() != 0)
					{
						mb = (mst450101_TenbetuSiiresakiListBean) mlst.get(i);
					}
					//				mb.setSentaku(sentaku[i]);
					mb.setSentaku(
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_sentaku" + i)));
					String ct_siiresakicd =
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_siiresakicd" + i));
					String ct_siiresakinm =
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_siiresakinm" + i));
					String ct_h_yubincd =
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_h_yubincd" + i));
					String ct_h_addresskanji1na =
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_h_addresskanji1na" + i));
					String[] flg = dataHolder.getParameterValues("ct_h_tenpocd" + i);
					tenpoNum = flg.length;
					mb.setSiiresakiCd(ct_siiresakicd);
					mb.setKanjiRn(ct_siiresakinm);
					mb.setYubinCd(ct_h_yubincd);
					mb.setAddressKanji1Na(ct_h_addresskanji1na);
					mb.setFlgl(
						mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_h_tenpocd" + i)));
					//				String flgx = "";
					//				for (int j=0; j < flg.length; j++) {
					//					if (flgx.equals("")) {
					//						flgx = flgx + flg[j];
					//						allCheckFlag = true;
					//					} else {
					//						flgx = flgx + "," + flg[j];
					//					}
					//				}
					//				mb.setFlgl(flgx);
					//				if(!allCheckFlag){
					//					mb.setAllTenpoFlg("1");
					//				} else {
					//					mb.setAllTenpoFlg("0");
					//				}
					mlst.set(i, mb);
				}
			}
			Meisaib.setMeisai(mlst);
//			↓↓2007.09.05 oohashi 本番に合わせて修正
//			Meisaib.setNewTcdL(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_new_tcd_l")));
//			Meisaib.setNewGnoL(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_new_gno_l")));
//			Meisaib.setNewFlgL(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_new_flg_l")));
//			Meisaib.setUpdTsL(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_h_upd_ts_l")));
//			↑↑2007.09.05 oohashi 本番に合わせて修正

			// ===BEGIN=== Comment out by kou 2006/10/13
			////			↓↓2006.07.26 xubq カスタマイズ修正↓↓
			//if (dataHolder.getParameter("buttonMode") != null
			//	&& "action".equals(dataHolder.getParameter("buttonMode")))
			//{
			//	if (dataHolder
			//		.getParameter("ct_syorikb")
			//		.trim()
			//		.equals(mst000101_ConstDictionary.PROCESS_INSERT)
			//		|| dataHolder.getParameter("ct_syorikb").trim().equals(
			//			mst000101_ConstDictionary.PROCESS_UPDATE))
			//	{
			//		// 代表仕入先と同じ仕入先で店別には登録不可にする
			//		int chkcnt = 0;
			//		String bk_siiresakicd = "";
			//		int errcnt = 0; //全店データ数のカウンタ
			//
			//		// 全店データが2件以上存在した場合、エラーとする
			//		for (int k = 0; k < mlst.size(); k++)
			//		{
			//			boolean allCheckFlag = false;
			//			if (mlst.size() != 0)
			//			{
			//				mb = (mst450101_TenbetuSiiresakiListBean) mlst.get(k);
			//			}
			//			String flg = mb.getFlgl();
			//			if(flg == null) continue;
			//			String[] flgl = flg.split(",");
			//			for (int j = 0; j < flgl.length; j++)
			//			{
			//				if (!flgl[j].equals(mst000101_ConstDictionary.RECORD_EXISTENCE))
			//				{
			//					allCheckFlag = true;
			//					break;
			//				}
			//			}
			//			if (!allCheckFlag
			//				&& mb.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE))
			//			{
			//				mb.setAllTenpo("1");
			//				errcnt++;
			//			}
			//			else if ("1".equals(mb.getAllTenpoFlg()))
			//			{
			//				bk_siiresakicd = mst000401_LogicBean.chkNullString(mb.getSiiresakiCd());
			//				mb.setAllTenpo("1");
			//				errcnt++;
			//			}
			//			else
			//			{
			//				mb.setAllTenpo("0");
			//			}
			//		}
			//		//	 全店データが2件以上存在した場合、エラーとする
			//		if (errcnt > 1)
			//		{
			//			if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
			//			{
			//				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			//				Keepb.setErrorMessage(map.get("40812").toString());
			//			}
			//		}
			//		// ===BEGIN=== Comment out by kou 2006/10/02
			//		//if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
			//		//{
			//		//	//複数店舗選択チェック
			//		//	for (int i = 0; i < tenpoNum; i++)
			//		//	{
			//		//		chkcnt = 0;
			//		//		for (int k = 0; k < mlst.size(); k++)
			//		//		{
			//		//			if (mlst.size() != 0)
			//		//			{
			//		//				mb = (mst450101_TenbetuSiiresakiListBean) mlst.get(k);
			//		//			}
			//		//			String flg = mb.getFlgl();
			//		//			String[] flgl = flg.split(",");
			//		//
			//		//			String flgDefault = mb.getDefaultFlgl();
			//		//			String[] flgDefaultl = flgDefault.split(",");
			//		//			if ("0"
			//		//				.equals(mst000401_LogicBean.chkNullString(mb.getAllTenpo())))
			//		//			{
			//		//				if (Keepb
			//		//					.getErrorFlg()
			//		//					.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
			//		//					&& dataHolder.getParameter("ct_syorikb").trim().equals(
			//		//						mst000101_ConstDictionary.PROCESS_INSERT))
			//		//				{
			//		//					if (flgl[i]
			//		//						.equals(mst000101_ConstDictionary.RECORD_EXISTENCE)
			//		//						&& mb.getSentaku().equals(
			//		//							mst000101_ConstDictionary.SENTAKU_CHOICE))
			//		//					{
			//		//						chkcnt++;
			//		//					}
			//		//				}
			//		//				else if (
			//		//					Keepb.getErrorFlg().equals(
			//		//						mst000101_ConstDictionary.ERR_CHK_NORMAL)
			//		//						&& dataHolder.getParameter("ct_syorikb").trim().equals(
			//		//							mst000101_ConstDictionary.PROCESS_UPDATE))
			//		//				{
			//		//					if (flgl[i]
			//		//						.equals(mst000101_ConstDictionary.RECORD_EXISTENCE)
			//		//						&& mb.getSentaku().equals(
			//		//							mst000101_ConstDictionary.SENTAKU_CHOICE))
			//		//					{
			//		//						chkcnt++;
			//		//					}
			//		//					if (!mb
			//		//						.getSentaku()
			//		//						.equals(mst000101_ConstDictionary.SENTAKU_CHOICE)
			//		//						&& flgDefaultl[i].equals(
			//		//							mst000101_ConstDictionary.RECORD_EXISTENCE))
			//		//					{
			//		//						chkcnt++;
			//		//					}
			//		//				}
			//		//			}
			//		//			if ("0".equals(mb.getAllTenpo())
			//		//				&& bk_siiresakicd.equals(mb.getSiiresakiCd())
			//		//				&& !"".equals(bk_siiresakicd))
			//		//			{
			//		//				if (Keepb
			//		//					.getErrorFlg()
			//		//					.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
			//		//				{
			//		//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			//		//					Keepb.setErrorMessage(map.get("40811").toString());
			//		//				}
			//		//			}
			//		//			if (chkcnt > 1)
			//		//			{
			//		//				if (Keepb
			//		//					.getErrorFlg()
			//		//					.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
			//		//				{
			//		//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			//		//					Keepb.setErrorMessage(map.get("40012").toString());
			//		//				}
			//		//			}
			//		//		}
			//		//	}
			//		//}
			//		// ===END=== Comment out by kou 2006/10/02
			//	}
			//}
			////			↑↑2006.07.26 xubq カスタマイズ修正↑↑
			// ===END=== Comment out by kou 2006/10/13

			Map param = new HashMap(); //抽出条件格納用
			ResultSet rset = null; //抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);
			//		    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
			//			//メーニューバーアイコン取得
			//			// userSession取得
			//			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			//			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean(); // ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean) sessionManager.getAttribute("userSession");
			//			Map menuMap = new HashMap();
			//			menuMap.put("gamen_id",GamenId);
			//			menuMap.put("kengen_cd",kengenCd);
			//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			//			String[] menu = mst000401_LogicBean.getCommonMenubar(menuMap);
			//			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
			//			Keepb.setMenuBar(menu);
			//		    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList(); //マスタ存在チェック抽出条件
			String name = ""; //戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			//検索処理チェック-----------------------------------------------------------------------------
			//		    ↓↓2006.07.02 maojm カスタマイズ修正↓↓
			//			//種別の取得
			//			String Syubetu = new String();
			//			if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode()) ){
			//				Syubetu = mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS;
			//			} else if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
			//				Syubetu = mst000101_ConstDictionary.H_SALE;
			//			}

			//			//管理コード存在チェック
			//			lst = new ArrayList();
			//			mstchk = new mst000701_MasterInfoBean();

			//			lst.add( "SYUBETU_NO_CD = " + Syubetu );
			//			lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) + "' ");
			// 2006.01.24 Y.Inaba Mod ↓
			//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
			//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
			// 2006.01.24 Y.Inaba Mod ↑
			//			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			//				//管理コード存在エラー
			//				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
			//				Keepb.setFirstFocus("ct_kanricd");
			//				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			//					Keepb.setErrorMessage("指定された管理コード"+map.get("40007").toString());
			//				}
			//			} else {
			//				Keepb.setKanriNm(mstchk.getCdName());
			//				if( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")).trim().equals(mst000901_KanriKbDictionary.HANKU.getCode()) ){
			//					if(!mst000401_LogicBean.getHankuCdCheck(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")),sessionManager)){
			//					if(!mst000401_LogicBean.getHankuCdCheck(db,mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")),sessionManager)){
			//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
			//						Keepb.setFirstFocus("ct_kanricd");
			//						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
			//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			//							Keepb.setErrorMessage(map.get("40022").toString());
			//						}
			//					}
			//				}
			//			}

			//			//部門コード存在チェック
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();
			lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
			lst.add(
				" and "
					+ " CODE_CD = '"
					+ StringUtility.charFormat(
						mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),
						4,
						"0",
						true)
					+ "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
			{
				//部門コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_bumoncd");
				Keepb.setFirstFocus("ct_bumoncd");
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
				{
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setBumonNm("");
					Keepb.setErrorMessage("指定された部門コード" + map.get("40007").toString());
				}
			}
			else
			{
				Keepb.setBumonNm(mstchk.getCdName());
			}
			//		    ↑↑2006.07.02 maojm カスタマイズ修正↑↑
			//店仕入先管理コード存在チェック
			lst = new ArrayList();
			mstchk = new mst000701_MasterInfoBean();

			lst.add(
				"SYUBETU_NO_CD = '"
//				↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//					+ mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP
					+ MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal)
//				↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
					+ "'");
			//↓↓販区＋店別仕入先管理コード対応（2005.06.23）↓↓
			//			lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakicd")) + "' ");

			// 選択された業種が「04：生鮮」の場合は'0033'で検索
			//		    ↓↓2006.06.30 maojm カスタマイズ修正↓↓
			//			if(SysUserBean.getSelectedGyosyuCd().equals("04")){
			//				lst.add( " and "+ " CODE_CD = '0000" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakicd")) + "' ");
			//			}else{
			//				lst.add( " and "+ " CODE_CD = '" + dataHolder.getParameter("ct_kanricd") + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakicd")) + "' ");
			//			}
			lst.add(
				" and "
					+ " CODE_CD = '"
//				↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//					+ StringUtility.charFormat(
//						mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_bumoncd")),
//						4,
//						"0",
//						true)
//				↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
					+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakicd"))
					+ "' ");
			//		    ↑↑2006.06.30 maojm カスタマイズ修正↑↑
			//↑↑販区＋店別仕入先管理コード対応（2005.06.23）↑↑
			// 2006.01.24 Y.Inaba Mod ↓
			//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
			mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
			// 2006.01.24 Y.Inaba Mod ↑
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
			{
				//管理コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_tensiiresakicd");
				Keepb.setTenSiiresakiKanriNm("");
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
				{
					Keepb.setFirstFocus("ct_tensiiresakicd");
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//					Keepb.setErrorMessage("指定された店メーカーコード" + map.get("40007").toString());
					Keepb.setErrorMessage("指定されたメーカーコード" + map.get("40007").toString());
//					↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
				}
			}
			else
			{
				Keepb.setTenSiiresakiKanriNm(mstchk.getCdName());
			}
			if (!(mst000401_LogicBean
				.chkNullString(dataHolder.getParameter("ct_copybumoncd"))
				.trim()
				.equals("")))
			{
				//コピー部門コード存在チェック
				lst.clear();
				lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' ");
				lst.add(" and ");
				lst.add(
					" CODE_CD = '"
						+ StringUtility.charFormat(
							mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("ct_copybumoncd")),
							4,
							"0",
							true)
						+ "' ");
				mstchk =
					mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
				{
					//コピー部門コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_copybumoncd");
					Keepb.setCopyBumonNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
					{
						Keepb.setFirstFocus("ct_copybumoncd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたコピー部門" + map.get("40007").toString());
					}
				}
				else
				{
					Keepb.setCopyBumonNm(mstchk.getCdName());
				}
			}
			else
			{
				Keepb.setCopyBumonNm("");
			}
//			↓↓2006.11.13 H.Yamamoto カスタマイズ修正↓↓
			if (!(mst000401_LogicBean
				.chkNullString(dataHolder.getParameter("ct_copytensiiresakicd"))
				.trim()
				.equals(""))) {
				//コピー店仕入先管理コード存在チェック
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();

				lst.add(
					"SYUBETU_NO_CD = '"
						+ MessageUtil.getMessage(mst000101_ConstDictionary.MAKER_NAME, userLocal)
						+ "'");
				lst.add(
					" and "
						+ " CODE_CD = '"
						+ mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_copytensiiresakicd"))
						+ "' ");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", lst, "0", "");
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
					//管理コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_copytensiiresakicd");
					Keepb.setCopyTenSiiresakiKanriNm("");
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
					{
						Keepb.setFirstFocus("ct_copytensiiresakicd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたコピーメーカーコード" + map.get("40007").toString());
					}
				} else {
					Keepb.setCopyTenSiiresakiKanriNm(mstchk.getCdName());
				}
			} else {
				Keepb.setCopyTenSiiresakiKanriNm("");
			}
//			↑↑2006.11.13 H.Yamamoto カスタマイズ修正↑↑
			if (!(mst000401_LogicBean
				.chkNullString(dataHolder.getParameter("ct_copybumoncd"))
				.trim()
				.equals(""))
//			↓↓2006.11.13 H.Yamamoto カスタマイズ修正↓↓
				&& !(mst000401_LogicBean
				.chkNullString(dataHolder.getParameter("ct_copytensiiresakicd"))
				.trim()
				.equals(""))
//			↑↑2006.11.13 H.Yamamoto カスタマイズ修正↑↑
				&& dataHolder.getParameter("ct_syorikb").trim().equals(
					mst000101_ConstDictionary.PROCESS_INSERT))
			{
//				↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
				// コピー先のデータ存在確認
				lst.clear();
				lst.add(
					"KANRI_CD = '"
						+ StringUtility.charFormat(
							mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("ct_bumoncd")),
							4,
							"0",
							true)
						+ "' ");
				lst.add(" and ");
				lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
				lst.add(" and ");
				lst.add(
					"TEN_SIIRESAKI_KANRI_CD = '"
						+ mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_tensiiresakicd"))
						+ "' ");
				lst.add(" fetch first 1 rows only ");
				mstchk =
					mst000401_LogicBean.getMasterCdName(
						db,
						"R_TENBETU_SIIRESAKI",
						"KANRI_CD",
						lst,
						"0",
						"");
				if (mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
				{
					//	部門存在エラー
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
					{
						Keepb.setFirstFocus("ct_bumoncd");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40001").toString());
					}
				}
//				↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑

				lst.clear();
				lst.add(
					"KANRI_CD = '"
						+ StringUtility.charFormat(
							mst000401_LogicBean.chkNullString(
								dataHolder.getParameter("ct_copybumoncd")),
							4,
							"0",
							true)
						+ "' ");
				lst.add(" and ");
				//					lst.add( "KANRI_KB = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' " );
				lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
				lst.add(" and ");
//				↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
//				lst.add(
//					"TEN_SIIRESAKI_KANRI_CD = '"
//						+ mst000401_LogicBean.chkNullString(
//							dataHolder.getParameter("ct_tensiiresakicd"))
//						+ "' ");
				lst.add(
					"TEN_SIIRESAKI_KANRI_CD = '"
						+ mst000401_LogicBean.chkNullString(
							dataHolder.getParameter("ct_copytensiiresakicd"))
						+ "' ");
//				↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
//				↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
				lst.add(" fetch first 1 rows only ");
//				↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
				// 2006.01.24 Y.Inaba Mod ↓
				//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENBETU_SIIRESAKI","KANRI_CD", lst, "0");
				mstchk =
					mst000401_LogicBean.getMasterCdName(
						db,
						"R_TENBETU_SIIRESAKI",
						"KANRI_CD",
						lst,
						"0",
						"");
				// 2006.01.24 Y.Inaba Mod ↑
				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
				{
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
					{
//						↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
//						Keepb.setFirstFocus("ct_tensiiresakicd");
						Keepb.setFirstFocus("ct_copybumoncd");
//						↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
//						↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
						Keepb.setErrorMessage(map.get("0005").toString());
					}
				}
			}

//			↓↓2006.11.30 H.Yamamoto カスタマイズ修正↓↓
//			if (!dataHolder
//				.getParameter("ct_syorikb")
//				.trim()
//				.equals(mst000101_ConstDictionary.PROCESS_INSERT))
//			{
//				lst.clear();
//				//				lst.add( "KANRI_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanricd")) +"' " );
//				lst.add(
//					"KANRI_CD = '"
//						+ StringUtility.charFormat(
//							mst000401_LogicBean.chkNullString(
//								dataHolder.getParameter("ct_bumoncd")),
//							4,
//							"0",
//							true)
//						+ "' ");
//				lst.add(" and ");
//				//				lst.add( "KANRI_KB = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kanrikb")) + "' " );
//				lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' ");
//				lst.add(" and ");
//				lst.add(
//					"TEN_SIIRESAKI_KANRI_CD = '"
//						+ mst000401_LogicBean.chkNullString(
//							dataHolder.getParameter("ct_tensiiresakicd"))
//						+ "' ");
////				↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//				lst.add(" fetch first 1 rows only ");
////				↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
//				// 2006.01.24 Y.Inaba Mod ↓
//				//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENBETU_SIIRESAKI","KANRI_CD", lst, "0");
//				mstchk =
//					mst000401_LogicBean.getMasterCdName(
//						db,
//						"R_TENBETU_SIIRESAKI",
//						"KANRI_CD",
//						lst,
//						"0",
//						"");
//				// 2006.01.24 Y.Inaba Mod ↑
//				if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE))
//				{
//					//					//販区存在エラー
//					//	部門存在エラー
//					//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_kanricd");
//					//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_bumoncd");
//					//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_tensiiresakicd");
//					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL))
//					{
//						//						Keepb.setFirstFocus("ct_kanricd");
//						Keepb.setFirstFocus("ct_bumoncd");
////						↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
////						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
////						↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
//						Keepb.setErrorMessage(map.get("0005").toString());
//					}
//				}
//			}
//			//		    ↑↑2006.07.02 maojm カスタマイズ修正↑↑
//			↑↑2006.11.30 H.Yamamoto カスタマイズ修正↑↑
			//店配列存在チェック ↓↓ add by kema 06.11.10
			lst.clear();
			lst.add(" bumon_cd =  '" + StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true).trim() + "' ");
			lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
			lst.add(" and groupno_cd =  '" + StringUtility.charFormat(Keepb.getGroupNoCd(),2,"0",true).trim() + "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUPNO","NAME_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "" );
			Keepb.setGroupNoNm(mstchk.getCdName());
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				mst000401_LogicBean.setErrorBackColor(CtrlColor, "ct_groupnocd");
				if(FirstFocusCtl.equals("")){
					FirstFocusCtl="ct_groupnocd";
				}
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定された店配列" + map.get("40007").toString());
				}
			}
			//↑↑ add by kema 06.11.10
		}

		Keepb.setCtrlColor(CtrlColor);
	}

	//    ↓↓2006.08.16 maojm カスタマイズ修正↓↓
	/**
	 *
	 * コントロール名取得処理
	 * @param 		int meisaiCnt       : 明細数
	 * @return		String[]
	 */
	public String[] getCtrlName(int meisaiCnt)
	{

//		↓↓2006.11.14 H.Yamamoto カスタマイズ修正↓↓
//		String[] CtrlName = new String[meisaiCnt + 3];
		String[] CtrlName = new String[meisaiCnt + 5];
//		↑↑2006.11.14 H.Yamamoto カスタマイズ修正↑↑
		int i = 0;
		for (; i < meisaiCnt; i++)
		{
			CtrlName[i] = "ct_siiresakicd" + i;
		}
		CtrlName[i] = "ct_bumoncd";
		CtrlName[i + 1] = "ct_tensiiresakicd";
		CtrlName[i + 2] = "ct_copybumoncd";
//		↓↓2007.09.05 oohashi 本番に合わせて修正
//		CtrlName[i + 3] = "ct_copytensiiresakicd";
//		CtrlName[i + 4] = "ct_groupnocd";
//		↑↑2007.09.05 oohashi 本番に合わせて修正
		return CtrlName;
	}
	//  ↑↑2006.08.16 maojm カスタマイズ修正↑↑
}