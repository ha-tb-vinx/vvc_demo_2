package mdware.master.common.command;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.util.StringUtility;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.bean.mst000601_KoushinInfoDM;
import mdware.master.common.bean.mst000701_MasterInfoBean;
import mdware.master.common.bean.mst001301_UpdateBean;
import mdware.master.common.bean.mst580101_TenGroupNoBean;
import mdware.master.common.bean.mst580101_TenGroupNoDM;
import mdware.master.common.bean.mst580201_TenGroupNoLBean;
import mdware.master.common.bean.mst580201_TenGroupNoLDM;
import mdware.master.common.bean.mst580301_KeepBean;
import mdware.master.common.bean.mst590101_TenGroupBean;
import mdware.master.common.bean.mst590101_TenGroupDM;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.master.common.dictionary.mst004801_NewSyoriRiyoukbDictionary;
import mdware.master.common.dictionary.mst004901_UpdateSyoriRiyoukbDictionary;
import mdware.master.common.dictionary.mst005001_DelRiyoukbDictionary;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <P>タイトル : mst580301_TenGroupNoEachUpdateCommandクラス</P>
 * <P>説明 : 新ＭＤシステム 店グルーピングＮＯ登録画面の内容で更新する</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>	
 * @author Sirius S.Murata
 * @version 1.0 (2005.03.11)初版作成
 * @version 1.1 (2006.10.09)障害票№0109対応 論理削除時に、UPDATE_USER_IDのみを更新する様にする K.Tanigawa
 * @version 1.2 (2006.10.09)障害票№0113対応 データ作成①→データ削除②→データ作成③を行うと、③の作成年月日が、①のまま作成されるため、削除済(削除フラグ = 1)の行データに対して、別データを更新する際は、作成年月日を更新する様修正する。 K.Tanigawa
 * @version 1.3 (2006.10.09,2006.10.10)障害票№0128対応 更新処理でエラーが発生する障害を修正 K.Tanigawa
 * @version 1.4 (2006.10.26)障害票№0152対応 メーカーコード照会画面作成 K.Tanigawa
 * @see なし
 */
public class mst580301_TenGroupNoEachUpdateCommand extends AbstractMasterCommand
{
	private static final String[] SESSION_NAMES = mst000101_ConstDictionary.SESSION_NAMES;
	private	 static final String[] SESSION_NAMES2 = new String[SESSION_NAMES.length + 1];
//  ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//	private String Err = "mstErr";											//エラー時JSPを取得
//  ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
	protected static final String MSG_0009 = infoStrings.getInfo("0009");//新規処理でエラーが発生しました。
	protected static final String MSG_0010 = infoStrings.getInfo("0010");//更新処理でエラーが発生しました。
	protected static final String MSG_0011 = infoStrings.getInfo("0011");//削除処理でエラーが発生しました。

//	ADD by Tanigawa 2006/10/26 障害票№0152対応 START
	public static final String ALLTENPOCD = "00";		//全店コード
	public static final String ALLTENPONA = "全店 ";	//全店名称
//	ADD by Tanigawa 2006/10/26 障害票№0152対応  END 
	
	private String jobName = "店グルーピングＮＯ登録画面";
	private String logHeader = "";//ログ出力用変数	

//	↓↓2006.10.07 H.Yamamoto カスタマイズ修正↓↓
	private mst000101_DbmsBean db = null; 
//	↑↑2006.10.07 H.Yamamoto カスタマイズ修正↑↑

	protected String doProcessMaster(String requestJobId, String requestUrlId)
		throws ServletException, IOException
	{	
		logHeader = getUserId().trim() + " mst580301 " + jobName + " : ";
		String processName = "";
		String Msg_Err = "";
		processName = "更新";
		Msg_Err = MSG_0010;
		stcLog.getLog().info(logHeader + "店グルーピングＮＯ登録画面" + processName + "処理を開始します。");		

		String logEnd = "店グルーピングＮＯ登録画面" + processName + "処理を終了します。";

		//userSession取得
//      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓		
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//		SysUserBean=(mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
		MdwareUserSessionBean 		SysUserBean 	= new MdwareUserSessionBean();	// ログインユーザー情報	
		SysUserBean=(MdwareUserSessionBean)sessionManager.getAttribute("userSession");
		mst580201_TenGroupNoLDM 		dbDM2 			= new mst580201_TenGroupNoLDM();
//      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
		//DBインスタンス作成
//		↓↓2006.10.07 H.Yamamoto カスタマイズ修正↓↓
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();			// DBインスタンス作成
		db = mst000101_DbmsBean.getInstance();			// DBインスタンス作成
//		↑↑2006.10.07 H.Yamamoto カスタマイズ修正↑↑

		//画面情報用
		mst580301_KeepBean Keepb = new mst580301_KeepBean();		//mst580301_KeepBean
		Keepb = (mst580301_KeepBean)sessionManager.getAttribute("mst580301_Keep");

		try {
			if ((Keepb.getInsertFlg().equals(mst004801_NewSyoriRiyoukbDictionary.KANOU.getCode())) ||
				(Keepb.getUpdateFlg().equals(mst004901_UpdateSyoriRiyoukbDictionary.KANOU.getCode())) ||
				(Keepb.getDeleteFlg().equals(mst005001_DelRiyoukbDictionary.KANOU.getCode()))) {
				Keepb.setShosaiFlg(true);
			} else {
				Keepb.setShosaiFlg(false);
			}
	
			//店グルーピングＮＯマスタ情報の取得用
			mst580201_TenGroupNoLDM dbLDM = new mst580201_TenGroupNoLDM();	//mst840201_TenkabutuListBean用添加物マスタのDMモジュール
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//			mst580201_TenGroupNoLBean tenkabutuLb = new mst580201_TenGroupNoLBean();	//mst840201_TenkabutuListBean
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
			BeanHolder dbBeanHolderL = new BeanHolder(dbLDM);						//mst840201_TenkabutuListBean用添加物マスタのBeanHolder
			//mst840401_KeepMeisaiBean Meisaib = new mst840401_KeepMeisaiBean();
	
			//店グルーピングＮＯマスタ情報の保存用
			mst580101_TenGroupNoDM dbDM = new mst580101_TenGroupNoDM();		//mst840201_TenkabutuBean用添加物マスタのDMモジュール
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//			mst580101_TenGroupNoBean tenkabutub = new mst580101_TenGroupNoBean();	//mst840201_TenkabutuBean
//			BeanHolder dbBeanHolder = new BeanHolder(dbDM);					//mst840201_TenkabutuBean用添加物マスタのBeanHolder
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑				
			mst580101_TenGroupNoBean[] dbBeanList = new mst580101_TenGroupNoBean[100];	//mst840201_TenkabutuBean
			for (int i=0; i < 100; i++) {
				dbBeanList[i] = new mst580101_TenGroupNoBean();
			}
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//			String GamenId 		  = "mst580101";	//画面ID			
//			String FirstFocusCtl  = "ct_sentaku(1)";//初期Focusｺﾝﾄﾛｰﾙ		
//			String[] CtrlName 	  = new String[Keepb.getMeisai().length];
//			for (int i=0; i < Keepb.getMeisai().length; i++) {
//				CtrlName[i] 	  = "ct_name_na" + i;
//			}
			String FirstFocusCtl  = "ct_sentaku(1)";//初期Focusｺﾝﾄﾛｰﾙ	
			String[] CtrlName 	  = {"tx_bumoncd"};
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
			//コントロール表示色設定			
			Map CtrlColor = mst000401_LogicBean.getBackColorInit(CtrlName);
			Keepb.setCtrlColor(CtrlColor);
	
			Keepb.setFirstFocus(FirstFocusCtl);
			
			String[] groupno = dataHolder.getParameterValues("ct_groupno_cd");
	
			for (int i=0; i < 100; i++) {
				String sentaku = dataHolder.getParameter("ct_sentaku_h" + i);
				String shori = dataHolder.getParameter("ct_shori" + i);	
				String name = dataHolder.getParameter("ct_name_na" + i);
				String insertts = dataHolder.getParameter("ct_insertts" + i);
				String insertuserid = dataHolder.getParameter("ct_insertUserId" + i);
				String updatets = dataHolder.getParameter("ct_updatets" + i);
				String updateuserid = dataHolder.getParameter("ct_updateUserId" + i);
				String updateRn = dataHolder.getParameter("ct_updateRn" + i);
				String insertRn = dataHolder.getParameter("ct_insertUserRn" + i);
//				↓↓2006.08.12 H.Yamamoto カスタマイズ修正↓↓
				String deleteFg = dataHolder.getParameter("ct_deleteFg" + i);
//				↑↑2006.08.12 H.Yamamoto カスタマイズ修正↑↑

// ↓↓仕様変更（2005.07.16）↓↓
//				dbBeanList[i].setLGyosyuCd(mst000401_LogicBean.chkNullString(SysUserBean.getGyosyuCd()));
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓				
//				dbBeanList[i].setLGyosyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_l_gyosyucd")));
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑				
// ↑↑仕様変更（2005.07.16）↑↑
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓				
//				dbBeanList[i].setYotoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yotokb")).trim());
				dbBeanList[i].setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).trim());
				dbBeanList[i].setBumonKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim());
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
				dbBeanList[i].setSentaku(sentaku);
				dbBeanList[i].setShori(shori);
				dbBeanList[i].setGroupnoCd(StringUtility.charFormat(groupno[i],2,"0",true));
				dbBeanList[i].setNameNa(mst000401_LogicBean.chkNullString(name));
				dbBeanList[i].setInsertTs(mst000401_LogicBean.chkNullString(insertts));
				dbBeanList[i].setInsertUserId(mst000401_LogicBean.chkNullString(insertuserid));
				dbBeanList[i].setUpdateTs(mst000401_LogicBean.chkNullString(updatets));
				dbBeanList[i].setUpdateUserId(mst000401_LogicBean.chkNullString(updateuserid));
				
				dbBeanList[i].setUpdateRn(mst000401_LogicBean.chkNullString(updateRn));
				dbBeanList[i].setInsertRn(mst000401_LogicBean.chkNullString(insertRn));
//				↓↓2006.08.12 H.Yamamoto カスタマイズ修正↓↓
				dbBeanList[i].setDeleteFg(mst000401_LogicBean.chkNullString(deleteFg));
//				↑↑2006.08.12 H.Yamamoto カスタマイズ修正↑↑
			}
						
			Keepb.setMeisai(dbBeanList);
			
			//メッセージ取得
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());	
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//			Keepb.setLGyosyuCd(dataHolder.getParameter("ct_l_gyosyucd"));
//			Keepb.setYotoKb(dataHolder.getParameter("ct_yotokb"));
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
			Keepb.setBumonCd(dataHolder.getParameter("tx_bumoncd"));
			Keepb.setBumonKanjiRn(dataHolder.getParameter("tx_bumon_nm"));
		    Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("changeflg")));
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
			//メーニューバーアイコン取得
//			Map menuMap = new HashMap();
//			menuMap.put("gamen_id","mst580101");
//			menuMap.put("kengen_cd",SysUserBean.getKengenCd());
//			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
//			String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
//			Keepb.setMenuBar(menu);
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑		
			//エラーチェック用
			Map param = new HashMap();		//抽出条件格納用
//			ResultSet rset = null;			//抽出結果(ResultSet)
	
			//メッセージ初期値設定
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
			Keepb.setErrorMessage(map.get("0031").toString());
//		      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓				
//			int insertCnt = 0;//新規・更新件数
//			int deleteCnt = 0;//削除件数
//		      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑			
			for ( int i = 0; i < 100; i++) {
				if (dbBeanList[i].getSentaku().equals(mst000101_ConstDictionary.CHECK_UPDATE)) {
					if (dbBeanList[i].getShori().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
						if (dbBeanList[i].getInsertTs() == null || dbBeanList[i].getInsertTs().equals("")) {
//						      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
							dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
							dbBeanList[i].setInsertUserId(SysUserBean.getUserId());
							dbBeanList[i].setUpdateRn(SysUserBean.getUserNa());
							dbBeanList[i].setInsertRn(SysUserBean.getUserNa());
							List lst=new ArrayList();
//							lst.add("    l_gyosyu_cd     = '" + dbBeanList[i].getLGyosyuCd() + "' ");
//							lst.add("and yoto_kb        = '" + dbBeanList[i].getYotoKb() + "' ");
//							lst.add("and groupno_cd = '" + new Long(dbBeanList[i].getGroupnoCd()).toString() + "' ");								
							lst.add("    bumon_cd     = '" +StringUtility.charFormat(dbBeanList[i].getBumonCd(),4,"0",true) +"' ");
							lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' "); //add by kema 06.09.20
							lst.add("and groupno_cd = '" + StringUtility.charFormat(new Long(dbBeanList[i].getGroupnoCd()).toString() ,2,"0",true)+ "' ");
							//更新処理
							if(executeInsertno(dbDM,dbBeanList[i],Keepb,SysUserBean.getUserId(),"R_TENGROUPNO",lst)){
								dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
							} else {
//								 ADD by Tanigawa 2006/10/09 障害票№0128対応  START
								dbBeanList[i].setUpdateUserId("");
								dbBeanList[i].setInsertUserId("");
								dbBeanList[i].setUpdateRn("");
								dbBeanList[i].setInsertRn("");
								dbBeanList[i].setInsertTs("");
								dbBeanList[i].setUpdateTs("");
//								 ADD by Tanigawa 2006/10/09 障害票№0128対応   END
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("0009").toString());
									Keepb.setFirstFocus("ct_name_na" + i);
								}
								mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_name_na" + i);	
								return Keepb.getEditUrl(logHeader, logEnd);
							}
//						} else {
//							検索キー設定（個別に設定）
//							param.put("l_gyosyu_cd",dbBeanList[i].getLGyosyuCd());
//							param.put("yoto_kb",dbBeanList[i].getYotoKb());
//							param.put("groupno_cd",new Long(dbBeanList[i].getGroupnoCd()).toString());
//							dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
//							更新処理
//							if(db.executeUpdate(dbDM.getUpdateSql(dbBeanList[i]),dbDM.getSelectSql(param),dbBeanList[i].getUpdateTs().trim())){
								//更新OK
//								dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
//							} else {
//								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								   Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								   Keepb.setErrorMessage(map.get("40004").toString());
//								   Keepb.setFirstFocus("ct_name_na" + i);
//							   }
//							   mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_name_na" + i);	
//							   return Keepb.getEditUrl(logHeader, logEnd);
//
//							}
//						}
//					}
//						      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑		
//						      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
							//更新処理
						} else {	
//						↓↓2006.08.12 H.Yamamoto カスタマイズ修正↓↓								
//						//検索キー設定（個別に設定）	
//
//					List lst=new ArrayList();
//						lst.add("    bumon_cd     = '" +StringUtility.charFormat(dbBeanList[i].getBumonCd(),4,"0",true) +"' ");
//						lst.add(" and groupno_cd = '" + StringUtility.charFormat(new Long(dbBeanList[i].getGroupnoCd()).toString() ,2,"0",true) + "' ");
//						dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
//						dbBeanList[i].setInsertUserId(SysUserBean.getUserId());
//						if(executeInsertno(dbDM,dbBeanList[i],Keepb,SysUserBean.getUserId(),"R_TENGROUPNO",lst)){
//							dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
//						} else {
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("0009").toString());
//								Keepb.setFirstFocus("ct_name_na" + i);
//							}
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_name_na" + i);	
//							return Keepb.getEditUrl(logHeader, logEnd);
//						}
								//検索キー設定（個別に設定）	
							param.put("bumon_cd",StringUtility.charFormat(dbBeanList[i].getBumonCd(),4,"0",true));
							param.put("groupno_cd",StringUtility.charFormat(new Long(dbBeanList[i].getGroupnoCd()).toString() ,2,"0",true));
	//							dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
							String tmpSysDate = db.getDBSysdate();
							if (dbBeanList[i].getDeleteFg().equals(mst000801_DelFlagDictionary.IRU.getCode())){
								dbBeanList[i].setInsertUserId(SysUserBean.getUserId());
								dbBeanList[i].setInsertRn(SysUserBean.getUserNa());
	//							 ADD by Tanigawa 2006/10/08 障害票№0113対応  START
								dbBeanList[i].setInsertTs(tmpSysDate);	//障害票№113対応
	//							 ADD by Tanigawa 2006/10/08 障害票№0113対応   END 
								dbBeanList[i].setDeleteFg(mst000801_DelFlagDictionary.INAI.getCode());
							}
							dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
							dbBeanList[i].setUpdateRn(SysUserBean.getUserNa());
							dbBeanList[i].setUpdateTsForUpdate(tmpSysDate);
							
							//更新処理
							if(db.executeUpdate(dbDM.getUpdateSql(dbBeanList[i]),dbDM.getSelectSql(param),dbBeanList[i].getUpdateTs().trim())){
								//更新OK
								dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
								dbBeanList[i].setUpdateTs(dbBeanList[i].getUpdateTsForUpdate());
							} else {
									if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
										Keepb.setErrorMessage(map.get("40004").toString());
										Keepb.setFirstFocus("ct_name_na" + i);
									}
									mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_name_na" + i);	
									return Keepb.getEditUrl(logHeader, logEnd);
							}
	//						↑↑2006.08.12 H.Yamamoto カスタマイズ修正↑↑
						}
					}

//				    		↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
					if (dbBeanList[i].getShori().equals(mst000101_ConstDictionary.PROCESS_DELETE)) {
						//検索キー設定（個別に設定）
//					      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓						
//						param.put("l_gyosyu_cd",dbBeanList[i].getLGyosyuCd());
//						param.put("yoto_kb",dbBeanList[i].getYotoKb());
						param.put("bumon_cd",StringUtility.charFormat(dbBeanList[i].getBumonCd(),4,"0",true));
//					      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑						
						param.put("groupno_cd",StringUtility.charFormat(new Long(dbBeanList[i].getGroupnoCd()).toString() ,2,"0",true));

//						 DEL by Tanigawa 2006/9/28 障害票№0086対応 START
//						dbBeanList[i].setInsertUserId(SysUserBean.getUserId());
//						 DEL by Tanigawa 2006/9/28 障害票№0086対応  END 
						
//						 ADD by Tanigawa 2006/10/09 障害票№00128対応 START
						//DELETE用にセットした値が、DELETE失敗時に画面に表示されてしまうため、
						//DELETE失敗時は、値をセットする前のコピーの値をセットする
						mst580101_TenGroupNoBean cloneBean = dbBeanList[i].createClone();
//						 ADD by Tanigawa 2006/10/09 障害票№00128対応  END 
						dbBeanList[i].setUpdateUserId(SysUserBean.getUserId());
						dbBeanList[i].setUpdateTsForUpdate(db.getDBSysdate());
						dbBeanList[i].setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
						
//						dbBeanList[i].setUpdateTs(db.getDBSysdate());
						//更新処理
						if(db.executeUpdate(dbDM.getUpdateSql(dbBeanList[i]),dbDM.getSelectSql(param),dbBeanList[i].getUpdateTs().trim())){
							//更新OK
							dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
//						      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓								
							mst590101_TenGroupDM tenGpDM= new mst590101_TenGroupDM();
							mst590101_TenGroupBean tenGpBean= new mst590101_TenGroupBean();
//							tenGpBean.setLGyosyuCd(dbBeanList[i].getLGyosyuCd());
//							tenGpBean.setYotoKb(dbBeanList[i].getYotoKb());
							tenGpBean.setBumonCd(StringUtility.charFormat(dbBeanList[i].getBumonCd(),4,"0",true));
//						      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑								
							tenGpBean.setGroupnoCd(StringUtility.charFormat(dbBeanList[i].getGroupnoCd(),2,"0",true));
							tenGpBean.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
							tenGpBean.setUpdateTs(db.getDBSysdate());
//							tenGpBean.setInsertUserId(SysUserBean.getUserId());
							tenGpBean.setUpdateUserId(SysUserBean.getUserId());
							//更新処理
//							if(db.executeUpdate(tenGpDM.getUpdateSql(tenGpBean),tenGpDM.getSelectSql(param),dbBeanList[i].getUpdateTs())){
							db.executeUpdate(tenGpDM.getUpdateSql(tenGpBean));
						} else {

//							 ADD by Tanigawa 2006/10/09 障害票№00128対応 START
							//失敗時は、DELETE用に値をセットする前のBeanに戻す
							dbBeanList[i].setDeleteFg(cloneBean.getDeleteFg());
							dbBeanList[i].setUpdateTs(cloneBean.getUpdateTs());//FIXME：成功した時は、updatetsforupdateをsetupdatetsする必要があるのでは？
//							 ADD by Tanigawa 2006/10/09 障害票№00128対応  END 
							
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							   Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							   Keepb.setErrorMessage(map.get("40004").toString());
							   Keepb.setFirstFocus("ct_name_na" + i);
						   }
						   return Keepb.getEditUrl(logHeader, logEnd);
						}
					}
				}
			}
			
			if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
				List lst=new ArrayList();
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓					
//				lst.add("    l_gyosyu_cd     = '" + Keepb.getLGyosyuCd() + "' ");
//				lst.add("and yoto_kb        = '" + Keepb.getYotoKb() + "' ");
				lst.add("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true) +"'");
				lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' "); //add by kema 06.09.20
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
				lst.add(" and groupno_cd > 0");
// 2006.01.25 Y.Inaba Mod ↓
//				mst000701_MasterInfoBean chk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "COUNT(*)", lst, "0");
				mst000701_MasterInfoBean chk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "COUNT(*)", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
				//店グルーピングＮＯマスタ情報の取得用
				mst580101_TenGroupNoDM dbDM00 = new mst580101_TenGroupNoDM();	//mst840201_TenkabutuListBean用添加物マスタのDMモジュール
				mst580101_TenGroupNoBean dbBean00 = new mst580101_TenGroupNoBean();	//mst840201_TenkabutuListBean
				if (chk.getCdName().equals("0")) {
//				      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓						
//					param.put("l_gyosyu_cd",Keepb.getLGyosyuCd());
//					param.put("yoto_kb",Keepb.getYotoKb());
					param.put("bumon_cd",StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));
					param.put("groupno_cd","00");
//					dbBean00.setLGyosyuCd(Keepb.getLGyosyuCd());
//					dbBean00.setYotoKb(Keepb.getYotoKb());
					dbBean00.setBumonCd(Keepb.getBumonCd());
//				      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
					dbBean00.setGroupnoCd("00");
					dbBean00.setUpdateUserId(SysUserBean.getUserId());
					dbBean00.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
//					dbBean00.setUpdateTs(db.getDBSysdate());
					//更新処理
					if(db.executeUpdate(dbDM00.getUpdateSql(dbBean00),dbDM00.getSelectSql(param),dbBeanList[0].getUpdateTs().trim())){
					} else {
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						   Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						   Keepb.setErrorMessage(map.get("40004").toString());
					   }
					}
//				      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
					lst.clear();
					lst.add("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true) +"'");
					lst.add(" and groupno_cd = '00' ");
					lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");
					
					mst000701_MasterInfoBean tengroupchk= mst000401_LogicBean.getMasterCdName(db, "R_TENGROUP", "COUNT(*)", lst, "0", "");
					
					if(!tengroupchk.getCdName().equals("0")){
						
						mst590101_TenGroupDM dbDM59 = new mst590101_TenGroupDM();	//mst840201_TenkabutuListBean用添加物マスタのDMモジュール
						mst590101_TenGroupBean dbBean59 = new mst590101_TenGroupBean();	//mst840201_TenkabutuListBean
						dbBean59.setBumonCd(StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));
						dbBean59.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());

//						↓↓2006.08.24 H.Yamamoto カスタマイズ修正↓↓						
//						StringBuffer sp = new StringBuffer();
//						sp.append(" select ");
//						sp.append(" tenpo_cd as tenpo_cd ");
//						sp.append(" from ");
//						sp.append(" R_TENPO ");
//						sp.append(" where ");
//						sp.append("TENPO_KB = '"+ mst000901_KanriKbDictionary.BUMON.getCode()+"'");	
//						sp.append(" order by tenpo_cd");
//						dbBean59.setUpdateUserId(SysUserBean.getUserId());
//						dbBean59.setUpdateTs(db.getDBSysdate());
//						int i = 0;
//						List dataList = new ArrayList();
//						ResultSet rst = db.executeQuery(sp.toString());	
//						while(rst.next()) {
//
//							dataList.add(i, rst.getString("tenpo_cd"));
//							i++;
//						}
//						for(int n = 0; n < i; n++){
//							
//							db.execute(dbDM59.getUpdateSql(dbBean59));
//						}
						dbBean59.setGroupnoCd("00");
						dbBean59.setUpdateUserId(SysUserBean.getUserId());
						dbBean59.setUpdateTs(db.getDBSysdate());
						db.execute(dbDM59.getUpdateSql(dbBean59));
//						↑↑2006.08.24 H.Yamamoto カスタマイズ修正↑↑
					}	
//				      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
				} else {
					//検索キー設定（個別に設定）
					lst=new ArrayList();
//				      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓						
//					lst.add("    l_gyosyu_cd     = '" + Keepb.getLGyosyuCd() + "' ");
//					lst.add("and yoto_kb        = '" + Keepb.getYotoKb() + "' ");
					lst.add("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true)+"'");
					lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' "); //add by kema 06.09.20
					lst.add(" and  groupno_cd = '00'");
//				      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑			
// 2006.01.25 Y.Inaba Mod ↓
//					chk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "COUNT(*)", lst, "0");
					chk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "COUNT(*)", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
					if (chk.getCdName().equals("0")) {
						lst=new ArrayList();
//					      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓							
//						lst.add("    l_gyosyu_cd     = '" + Keepb.getLGyosyuCd() + "' ");
//						lst.add("and yoto_kb        = '" + Keepb.getYotoKb() + "' ");
						lst.add("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true)+"'");
						lst.add(" and yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' "); //add by kema 06.09.20
						lst.add(" and  groupno_cd = '00' ");
//						dbBean00.setLGyosyuCd(Keepb.getLGyosyuCd());
//						dbBean00.setYotoKb(Keepb.getYotoKb());
						dbBean00.setBumonCd(StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));
//					      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑						
//						dbBean00.setGroupnoCd("00");
//						MOD by Tanigawa 2006/10/26 障害票№0152対応 START
						dbBean00.setGroupnoCd(ALLTENPOCD);
//						MOD by Tanigawa 2006/10/26 障害票№0152対応  END 
						dbBean00.setUpdateUserId(SysUserBean.getUserId());
						dbBean00.setUpdateTs(db.getDBSysdate());
						dbBean00.setInsertUserId(SysUserBean.getUserId());
						dbBean00.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
						dbBean00.setInsertTs(db.getDBSysdate());
//					      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//						dbBean00.setNameNa("全店 ");
//MOD by Tanigawa 2006/10/26 障害票№0152対応 START
						dbBean00.setNameNa(ALLTENPONA);
//MOD by Tanigawa 2006/10/26 障害票№0152対応  END 
						
						
						//						dbBean00.setNameNa("店コード順全店 ");
//					      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
						//新規処理
						if(db.executeInsert(dbDM00,dbBean00,Keepb,SysUserBean.getUserId(),"R_TENGROUPNO",lst)){
						} else {
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("0009").toString());
							}
							return Keepb.getEditUrl(logHeader, logEnd);
						}
//					      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
						//検索キー設定（個別に設定）
						lst.clear();
						lst.add("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true) +"'");
						lst.add(" and  groupno_cd = '00' ");
						lst.add(" and yoto_kb = '"+mst003901_YotoKbDictionary.HACHU.getCode()+"'");
						
						mst000701_MasterInfoBean tengroupchk= mst000401_LogicBean.getMasterCdName(db, "R_TENGROUP", "COUNT(*)", lst, "0", "");
						
						if(tengroupchk.getCdName().equals("0")){
							
							mst590101_TenGroupDM dbDM59 = new mst590101_TenGroupDM();	//mst840201_TenkabutuListBean用添加物マスタのDMモジュール
							mst590101_TenGroupBean dbBean59 = new mst590101_TenGroupBean();	//mst840201_TenkabutuListBean
							
							dbBean59.setBumonCd(StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true));
							dbBean59.setYotoKb(mst003901_YotoKbDictionary.HACHU.getCode());
							dbBean59.setGroupnoCd("00");
							dbBean59.setInsertUserId(SysUserBean.getUserId());
							dbBean59.setDeleteFg(mst000801_DelFlagDictionary.INAI.getCode());
							dbBean59.setInsertTs(db.getDBSysdate());
							dbBean59.setUpdateUserId(SysUserBean.getUserId());
							dbBean59.setUpdateTs(db.getDBSysdate());						
							//個数を取得
							StringBuffer sp = new StringBuffer();
							sp.append(" select ");
							sp.append(" tenpo_cd as tenpo_cd ");
							sp.append(" from ");
							sp.append(" R_TENPO ");
							sp.append(" where ");
							sp.append("TENPO_KB = '"+ mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode()+"'");	
							sp.append(" order by tenpo_cd");

							int i = 0;
							List dataList = new ArrayList();
							ResultSet rst = db.executeQuery(sp.toString());	
							
							while(rst.next()) {

								dataList.add(i, rst.getString("tenpo_cd"));
								i++;
							}
							//delete_fgの値は１の時記録の個数を設定
							StringBuffer sb = new StringBuffer();
							sb.append("SELECT ");
							sb.append(" GROUPNO_CD AS CD_NAME ");
							sb.append("FROM ");
							sb.append(" R_TENGROUP  " );
							sb.append("WHERE ");
							sb.append("bumon_cd = '"+StringUtility.charFormat(Keepb.getBumonCd(),4,"0",true) +"'");	
							sb.append(" and  groupno_cd = '00' ");
							sb.append(" and yoto_kb = '"+mst003901_YotoKbDictionary.HACHU.getCode()+"' ");
							ResultSet rst2 = db.executeQuery(sb.toString());	
							
							if(rst2.next()){
								// 更新処理
								for(int n = 0; n < i; n++){																	
										dbBean59.setTenpoCd(dataList.get(n).toString());
//									↓↓2006.08.24 H.Yamamoto カスタマイズ修正↓↓
//									dbBean59.setRankNb(n);
									dbBean59.setRankNb(n + 1);
//									↑↑2006.08.24 H.Yamamoto カスタマイズ修正↑↑
										db.executeUpdate(dbDM59.getUpdateSql(dbBean59));
								}
							}else{
								//新規処理
								for(int n = 0; n < i; n++){									
									dbBean59.setTenpoCd(dataList.get(n).toString());
//									↓↓2006.08.24 H.Yamamoto カスタマイズ修正↓↓
//									dbBean59.setRankNb(n);
									dbBean59.setRankNb(n + 1);
//									↑↑2006.08.24 H.Yamamoto カスタマイズ修正↑↑
									db.execute(dbDM59.getInsertSql(dbBean59));
								}
							}
						
						}
						
//					      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
					}
				}
			}

			if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
				db.commit();
// ↓↓仕様変更（2005.07.16）↓↓
//				dataHolder.updateParameterValue("l_gyosyu_cd",SysUserBean.getGyosyuCd());
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓				
//				dataHolder.updateParameterValue("l_gyosyu_cd",dataHolder.getParameter("ct_l_gyosyucd"));				
// ↑↑仕様変更（2005.07.16）↑↑
//				dataHolder.updateParameterValue("yoto_kb", 
//					mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yotokb")).trim());
				dataHolder.updateParameterValue("bumon_cd",dataHolder.getParameter("tx_bumoncd"));
				dataHolder.updateParameterValue("bumon_kanji_rn",dataHolder.getParameter("tx_bumon_nm"));
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑					
				dbBeanHolderL.setRowsInPage(0);	
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓	
//				dbBeanHolderL.setDataHolder( dataHolder );
//				List lst = dbBeanHolderL.getFirstPageBeanList();

//				remove by kema 06.09.12
//				db.execute(dbDM2.getWorkTbl());
//				for(int i=0 ;i < 100; i++){
//					db.execute(dbDM2.getInsertTmp(i));
//				}
				List lst=getBean( dataHolder,db );
				dbBeanList = new mst580101_TenGroupNoBean[100];	//mst840201_TenkabutuBean
				for (int i=0; i < 100; i++) {
					dbBeanList[i] = new mst580101_TenGroupNoBean();
				}			
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑				
				for (int i=0; i < 100; i++) {
					mst580201_TenGroupNoLBean be = (mst580201_TenGroupNoLBean)lst.get(i);
//				  ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//					dbBeanList[i].setLGyosyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_l_gyosyucd")));
//					dbBeanList[i].setYotoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yotokb")).trim());
					dbBeanList[i].setBumonCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumoncd")).trim());
					dbBeanList[i].setBumonKanjiRn(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_bumon_nm")).trim());
					dbBeanList[i].setUpdateRn(be.getUpdateRn());
					dbBeanList[i].setInsertRn(be.getInsertRn());
//				  ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑					
					dbBeanList[i].setSentaku(mst000101_ConstDictionary.CHECK_INIT);
					dbBeanList[i].setShori(mst000101_ConstDictionary.PROCESS_UPDATE);
					dbBeanList[i].setGroupnoCd(be.getGroupnoCd());
					dbBeanList[i].setNameNa(be.getNameNa());
					dbBeanList[i].setInsertTs(be.getInsertTs());
					dbBeanList[i].setInsertUserId(be.getInsertUserId());
					dbBeanList[i].setUpdateTs(be.getUpdateTs());
					dbBeanList[i].setUpdateUserId(be.getUpdateUserId());
					dbBeanList[i].setDeleteFg(be.getDeleteFg());
				}

				Keepb.setMeisai(dbBeanList);
				Keepb.setChangeFlg("");

			} else {
				db.rollback();
			}

			//取得データをSESSIONにセット
			sessionManager.setAttribute("mst580301_Keep", Keepb);

			if(Keepb.getInsertFlg().equals(mst004801_NewSyoriRiyoukbDictionary.KANOU.getCode())
			|| Keepb.getUpdateFlg().equals(mst004901_UpdateSyoriRiyoukbDictionary.KANOU.getCode())) {
				return Keepb.getEditUrl(logHeader, logEnd);

			} else {
				return Keepb.getViewUrl(logHeader, logEnd);

			}

		} catch(SQLException sqle) {
			//SQL例外処理
			db.rollback();
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			Keepb.setErrorMessage(Msg_Err);
			stcLog.getLog().fatal(logHeader + Msg_Err);
			stcLog.getLog().fatal(logHeader + sqle.toString());		
			return Keepb.getEditUrl();
				
		} catch(Exception e) {
			//例外処理
			db.rollback();
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			Keepb.setErrorMessage(Msg_Err);
			stcLog.getLog().error(logHeader + Msg_Err);
			stcLog.getLog().error(logHeader + e.toString());	
			return Keepb.getEditUrl();
	
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
	}
//	  ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
	public List getBean(DataHolder dataHolder, mst000101_DbmsBean db)
	throws SQLException
	{
		List list = new ArrayList();
		int maxRows = 0;
		mst580201_TenGroupNoLDM 		dbDM 			= new mst580201_TenGroupNoLDM();
		Map dth = dataHolder.getParameterMap();
		ResultSet rest = db.executeQuery(dbDM.getSelectSql(dth));
		while( rest.next() )
		{
			maxRows++;
			mst580201_TenGroupNoLBean bean = new mst580201_TenGroupNoLBean();
			bean.setBumonCd(rest.getString("bumon_cd"));
			bean.setUpdateRn(rest.getString("update_user_rn"));
			bean.setInsertRn(rest.getString("insert_user_rn") );
			bean.setGroupnoCd( rest.getString("groupno_cd") );
			bean.setNameNa( rest.getString("name_na") );
			bean.setInsertTs( rest.getString("insert_ts") );
			bean.setInsertUserId( rest.getString("insert_user_id") );
			bean.setUpdateTs( rest.getString("update_ts") );
			bean.setUpdateUserId( rest.getString("update_user_id") );
			bean.setDeleteFg( rest.getString("delete_fg") );
			list.add(bean);

//			list.add( instanceBean( new StcLibResultSet(rest, this.encoding) ) );
		}
		return list;
	}
//  　↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
	/**
	 * セッション名称を追加する
	 * 
	 * @param
	 * @return	String SESSION_NAME2
	 * 
	 */
	protected String[] getSessionAttributeNames()
	{
		for (int i = 0;i < SESSION_NAMES.length;i++) {
			SESSION_NAMES2[i] = SESSION_NAMES[i];
		}
		SESSION_NAMES2[SESSION_NAMES.length] = "mst580301_Keep";
		return SESSION_NAMES2;
	}
//    ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
	public boolean  executeInsertno( DataModule DM, Object Bean,Object Keep, String UserId, String TableNa, List whereList ) throws SQLException{
		mst000601_KoushinInfoDM Info = new mst000601_KoushinInfoDM();	//レコード更新状態の照会
//		↓↓2006.10.07 H.Yamamoto カスタマイズ修正↓↓
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); 		//STCLIBのDBインスタンス格納用
//		↑↑2006.10.07 H.Yamamoto カスタマイズ修正↑↑
		ResultSet rset = null;											//レコード更新状態戻り値
		
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());		// メッセージ取得
		//削除フラグ=0のデータが存在する場合、エラー
		if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
				return false;
			}
			return false;
		}
		whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		rset = db.executeQuery(Info.getStatusSql(TableNa,whereList));
		whereList.remove(whereList.size()-1);
		if(rset.next()){
			//削除フラグ=0 レコード存在
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("40001").toString()) == null){
				return false;
			}
			return false;
		} else {
			//削除フラグ=0 レコード非存在
			//削除フラグ=1のデータが存在する場合、Update
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.IRU.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
			rset = db.executeQuery(Info.getStatusSql(TableNa,whereList) + " for update with rs");
			whereList.remove(whereList.size()-1);
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			if(rset.next()){
				//削除フラグ=1 レコード存在
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean, db.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
			    db.executeUpdate(DM.getDeleteSql(Bean));
			    db.execute(DM.getInsertSql(Bean));
			} else {
				//レコード非存在
				//レコードが存在しない場合、Insert
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,db.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				db.execute(DM.getInsertSql(Bean));
			}
			//更新情報再取得
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			rset = db.executeQuery(Info.getStatusSql(TableNa,whereList));
			whereList.remove(whereList.size()-1);
			if(rset.next()){				
				if(mst001301_UpdateBean.setValue("setInsertTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setInsertUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_user_id")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_user_id")).trim()) == null){
					return false;
				}
			}
		}
		if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_NORMAL) == null){
			return false;
		}
		if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0031").toString()) == null){
			return false;
		}
		rset.close();	// add by kema 06.09.22
		return true;
		
	}
//    ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
}