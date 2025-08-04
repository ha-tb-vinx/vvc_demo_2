package mdware.master.common.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.util.DateChanger;
//↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
import mdware.common.util.StringUtility;
//↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst005601_HatuseiMotoKbDictionary;
import mdware.master.common.dictionary.mst007601_NonActKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;
import mdware.portal.bean.MdwareUserSessionBean;


/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面新規・削除クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面新規・削除クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius S.Murata
 * @version 1.0 (2005/03/20)初版作成
 * @version 1.1 (2006/10/30, 2006/10/31, 2006/11/01)障害票№0227対応 アプリケーション改善対応(WASダウン対応) K.Tanigawa
 */
public class mst300401_TanpinTenToriatukaiUpdateBean
{
	public mst300301_KeepBean delete(MasterDataBase db, DataHolder dataHolder, SessionManager sessionManager) 	throws Exception,SQLException {
		mst300301_KeepBean Keepb = (mst300301_KeepBean)sessionManager.getAttribute("mst300301_Keep");
//		 ADD by Tanigawa 2006/11/01 障害票№0227対応 START
		PreparedStatement checkPS = null;
		PreparedStatement updatePS = null;
//		 ADD by Tanigawa 2006/11/01 障害票№0227対応  END 

		try {
			MdwareUserSessionBean 	SysUserBean 	= new MdwareUserSessionBean();	// ログインユーザー情報		
			SysUserBean=(MdwareUserSessionBean)sessionManager.getAttribute("userSession");
			// メッセージ取得
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());	
			List meisai = new ArrayList();
			mst300201_TanpinTenToriatukaiDM  dbDMIns = new mst300201_TanpinTenToriatukaiDM();// 単品店取扱マスタ新規用
			mst300201_TanpinTenToriatukaiBean  dbBeanIns = new mst300201_TanpinTenToriatukaiBean();// 単品店取扱マスタ新規用

//			 ADD by Tanigawa 2006/11/01 障害票№0227対応 START
			checkPS  = db.getPrepareStatement(dbDMIns.getCheckSelectSqlForPreparedStatement());
			updatePS = db.getPrepareStatement(dbDMIns.getUpdateSqlForPreparedStatement());
//			 ADD by Tanigawa 2006/11/01 障害票№0227対応  END 

			// 削除件数
			int cnt = 0;
			// 削除処理
			meisai = Keepb.getMeisai();
			for (int i=0; i < meisai.size(); i++) {
				StringBuffer oldflgy = new StringBuffer();
				mst300101_TanpinTenToriatukaiLBean mb = (mst300101_TanpinTenToriatukaiLBean)meisai.get(i);
				if (mst000101_ConstDictionary.RECORD_EXISTENCE.equals(mb.getSentaku())) {
					String[] oldFlgx = mst000401_LogicBean.separate(mb.getOldFlgl(),",");
					String[] flg      = mst000401_LogicBean.separate(mb.getFlgl(),",");
					String tc = mb.getTenpoCdl();
					String[] tenpocd = mst000401_LogicBean.separate(tc,",");
					String usr_ts = mb.getUpdateTsl();
					String[] usr_tsl = mst000401_LogicBean.separate(usr_ts,",");
					for (int k=0; k < flg.length; k++) {
						// レコード削除処理
						if (flg[k].equals(mst000101_ConstDictionary.RECORD_NON_EXISTENCE) &&
							oldFlgx[k].equals(mst000101_ConstDictionary.RECORD_EXISTENCE)) {
							
//							 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
							String currentTime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
//							 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 
							
							dbBeanIns.setSyoriFlg("delete");
							dbBeanIns.setBumonCd(mb.getBumonCd());
							dbBeanIns.setSyohinCd(mb.getSyohinCd());
//							↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
//							dbBeanIns.setTenpoCd(tenpocd[k].trim());
							dbBeanIns.setTenpoCd(StringUtility.charFormat(tenpocd[k], 6, "0", true));
							dbBeanIns.setHenkoDt(RMSTDATEUtil.getMstDateDt());
//							↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑
//							dbBeanIns.setUpdateTs(db.getDBSysdate());
							dbBeanIns.setUpdateTs(currentTime);
							dbBeanIns.setUpdateUserTs(SysUserBean.getUserId());
							dbBeanIns.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
							
							// 検索キー設定（個別に設定）
//							Map param = new HashMap();
//							param.put("bumon_cd",dbBeanIns.getBumonCd());
//							param.put("syohin_cd",dbBeanIns.getSyohinCd());
//							param.put("tenpo_cd",dbBeanIns.getTenpoCd());
							// 削除処理
//							if(db.executeUpdate(	dbDMIns.getUpdateSql(dbBeanIns),
//													dbDMIns.getSelectSql(param),
//													mst000401_LogicBean.chkNullString(usr_tsl[k]).trim())){
//ADD by Tanigawa 2006/11/01 障害票№0227対応 START
							if(this.executeUpdatePreparedStatement(checkPS,
																	updatePS,
																	dbBeanIns,
																	mst000401_LogicBean.chkNullString(usr_tsl[k]).trim())){
//ADD by Tanigawa 2006/11/01 障害票№0227対応  END 
								cnt = cnt + 1;
							} else {
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("40004").toString());
								}
							}
						}
					}
				}
			}
			Keepb.setCnt(cnt);
			return Keepb;

		} catch(SQLException sqle) {
			// SQL例外処理
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			Keepb.setErrorMessage(sqle.toString());
			throw sqle;

		} catch(Exception e) {
			// 例外処理
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			Keepb.setErrorMessage(e.toString());
			throw e;

		} finally {
			//db.commit及びdb.closeは、呼び出し元で実行
			if( checkPS != null ){
				checkPS.close();
				checkPS = null;
			}

			if( updatePS != null ){
				updatePS.close();
				updatePS = null;
			}
		}
	}

//	public static mst300301_KeepBean insert(DataHolder dataHolder, SessionManager sessionManager) 	throws Exception,SQLException {
	public mst300301_KeepBean insert(MasterDataBase db, DataHolder dataHolder, SessionManager sessionManager) 	throws Exception,SQLException {
		 
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();

//		 DEL by Tanigawa 2006/10/31 障害票№0227対応 START 理解に苦しむコードは不要
//		String mukou_rec = "@@@@@@@@";// レコードダミー判断
//		 DEL by Tanigawa 2006/10/31 障害票№0227対応  END
		
		mst300301_KeepBean Keepb = (mst300301_KeepBean)sessionManager.getAttribute("mst300301_Keep");
//		 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
		PreparedStatement insertPS = null;
		PreparedStatement deletePS = null;
		PreparedStatement statusPS = null;
		PreparedStatement statusPSForUpdate = null;
//		 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 
		try {
			
//		      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓		
//			mst000501_SysSosasyaBean 	SysUserBean 	= new mst000501_SysSosasyaBean();	// ログインユーザー情報		
			MdwareUserSessionBean 	SysUserBean 	= new MdwareUserSessionBean();	// ログインユーザー情報		
			// sysUserSesson取得
//			SysUserBean=(mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
			SysUserBean=(MdwareUserSessionBean)sessionManager.getAttribute("userSession");
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
			// メッセージ取得
			TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());	

			List meisai = new ArrayList();
			mst300201_TanpinTenToriatukaiDM  dbDMIns = new mst300201_TanpinTenToriatukaiDM();// 単品店取扱マスタ新規用
			mst300201_TanpinTenToriatukaiBean  dbBeanIns = new mst300201_TanpinTenToriatukaiBean();// 単品店取扱マスタ新規用

//			 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
			insertPS = db.getPrepareStatement( dbDMIns.getInsertSqlForPreparedStatement() );
//			PreparedStatement updatePS = db.getPrepareStatement( dbDMIns.getUpdateSqlForPreparedStatement() );
			deletePS = db.getPrepareStatement( dbDMIns.getDeleteSqlForPreparedStatement() );
			statusPS = db.getPrepareStatement( this.getStatusSql() );
			statusPSForUpdate = db.getPrepareStatement(this.getStatusSqlForUpdate());
//			 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 
			
			// 新規件数
			int cnt = 0;
			// 新規処理
			meisai = Keepb.getMeisai();
			for (int i=0; i < meisai.size(); i++) {
				StringBuffer oldflgy = new StringBuffer();
				mst300101_TanpinTenToriatukaiLBean mb = (mst300101_TanpinTenToriatukaiLBean)meisai.get(i);
				if (mst000101_ConstDictionary.RECORD_EXISTENCE.equals(mb.getSentaku())) {
					String[] oldFlgx = mst000401_LogicBean.separate(mb.getOldFlgl(),",");
					String[] flg      = mst000401_LogicBean.separate(mb.getFlgl(),",");
					String tc = mb.getTenpoCdl();
					String[] tenpocd = mst000401_LogicBean.separate(tc,",");
					dbBeanIns.setSyoriFlg("insert");
					for (int k=0; k < flg.length; k++) {
						// レコード追加処理
						if (flg[k].equals(mst000101_ConstDictionary.RECORD_EXISTENCE) &&
							oldFlgx[k].equals(mst000101_ConstDictionary.RECORD_NON_EXISTENCE)) {

//							 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
							String currentTime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
//							 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 
							
							dbBeanIns.setBumonCd(mb.getBumonCd());
							dbBeanIns.setSyohinCd(mb.getSyohinCd());
//							↓↓2006.11.24 H.Yamamoto カスタマイズ修正↓↓
//							dbBeanIns.setTenpoCd(tenpocd[k].trim());
							dbBeanIns.setTenpoCd(StringUtility.charFormat(tenpocd[k], 6, "0", true));
//							↑↑2006.11.24 H.Yamamoto カスタマイズ修正↑↑
							dbBeanIns.setDonyuStDt(DateChanger.addDate(RMSTDATEUtil.getMstDateDt(), 1));
							dbBeanIns.setDonyuEdDt("99999999");
							dbBeanIns.setNonActKb(mst007601_NonActKbDictionary.ACT.getCode());
							dbBeanIns.setHaseimotoKb(mst005601_HatuseiMotoKbDictionary.TANPIN_MISE_TOUROKU.getCode());
							dbBeanIns.setHenkoDt(RMSTDATEUtil.getMstDateDt());
//							dbBeanIns.setInsertTs(db.getDBSysdate());
							dbBeanIns.setInsertTs(currentTime);
							dbBeanIns.setInsertUserId(SysUserBean.getUserId());
//							dbBeanIns.setUpdateTs(db.getDBSysdate());
							dbBeanIns.setUpdateTs(currentTime);
							dbBeanIns.setUpdateUserId(SysUserBean.getUserId());
							dbBeanIns.setDeleteFg(mst000801_DelFlagDictionary.INAI.getCode());
							dbBeanIns.setSinkiTorokuDt(RMSTDATEUtil.getMstDateDt());
//							// 検索キー設定（個別に設定）
//							List wlst=new ArrayList();
//							wlst.add("    BUMON_CD     = '" + dbBeanIns.getBumonCd() + "' ");
//							wlst.add("and SYOHIN_CD    = '" + dbBeanIns.getSyohinCd() + "' ");
//							wlst.add("and TENPO_CD     = '" + dbBeanIns.getTenpoCd() + "' ");
//							// 更新処理
//							if(db.executeInsert(dbDMIns,dbBeanIns,Keepb,SysUserBean.getUserId(),sb.toString(),wlst)){
//MOD by Tanigawa 2006/10/31 障害票№0227対応 START
							if(executeInsertPreparedStatement(	insertPS,
																deletePS,
																statusPS,
																statusPSForUpdate,
															  	dbBeanIns,
															  	Keepb,
															  	SysUserBean.getUserId(),
															  	currentTime)){
//MOD by Tanigawa 2006/10/31 障害票№0227対応  END 
								cnt = cnt + 1;
							} else {
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("0009").toString());
								}
							}
						}
					}
				}
			}
			Keepb.setCnt(cnt);
			return Keepb;

		} catch(SQLException sqle) {
			// SQL例外処理
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			Keepb.setErrorMessage(sqle.toString());
			throw sqle;
	
		} catch(Exception e) {
			// 例外処理
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage(e.toString());
				throw e;
	
		} finally {
			
			//db.commit及びdb.closeは、呼び出し元で実行

			if( insertPS != null ){
				insertPS.close();
				insertPS = null;
			}
			if( deletePS != null ){
				deletePS.close();
				deletePS = null;
			}
			if( statusPS != null ){
				statusPS.close();
				statusPS = null;
			}
			if( statusPSForUpdate != null ){
				statusPSForUpdate.close();
				statusPSForUpdate = null;
			}
		}
	}
	
	//ADD by Tanigawa 2006/10/31 障害票№0227対応 START
	//mst000101_DbmsBeanクラスのexecuteInsertメソッドのPreparedStatement Versionです。
	/**
	 *	追加(Insert用)
	 *  引数で渡された条件で更新する。成功/失敗を返す
	 *	@param 	String	updateSql	//Insert用Sql
	 *	@param 	String	chkSql		//更新年月日取得用Sql
	 *	@param 	String	chkDT		//保存していた更新年月日
	 *  @return 	boolean	true  :成功（更新条件に満たない場合も成功で返す。エラーフラグで判断） 
	 *                   	false :失敗（Bean,Keepへの値の取得設定に失敗した場合、失敗を返す。）
	 *	@exception java.sql.SQLException
	 */
	private boolean executeInsertPreparedStatement(	final PreparedStatement insertPS,
														final PreparedStatement deletePS,
														final PreparedStatement statusPS,
														final PreparedStatement statusPSForUpdate,
														final mst300201_TanpinTenToriatukaiBean Bean, 
														final Object Keep, 
														final String UserId, 
//														String TableNa, 
//														final List whereList,
														final String currentTime )
		throws SQLException, Exception
	{
//		mst000601_KoushinInfoDM Info = new mst000601_KoushinInfoDM();	//レコード更新状態の照会
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
//		whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//		rset = db.executeQuery(Info.getStatusSql(TableNa,whereList));
//		whereList.remove(whereList.size()-1);
		statusPS.setString(1, Bean.getBumonCd());
		statusPS.setString(2, Bean.getSyohinCd());
		statusPS.setString(3, Bean.getTenpoCd());
		statusPS.setString(4, mst000801_DelFlagDictionary.INAI.getCode());
		rset = statusPS.executeQuery();
		if(rset.next() == true){
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

//			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");
//			rset = db.executeQuery(Info.getStatusSql(TableNa,whereList) + " for update with rs");
//			whereList.remove(whereList.size()-1);
			statusPSForUpdate.setString(1, Bean.getBumonCd());
			statusPSForUpdate.setString(2, Bean.getSyohinCd());
			statusPSForUpdate.setString(3, Bean.getTenpoCd());
			statusPSForUpdate.setString(4, mst000801_DelFlagDictionary.IRU.getCode());
			rset.close();
			rset = statusPSForUpdate.executeQuery();
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
//				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,db.getDBSysdate() ) == null){
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,currentTime ) == null){
					
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				//db.executeUpdate(DM.getDeleteSql(Bean));
				//db.execute(DM.getInsertSql(Bean));
				deletePS.setString(1, Bean.getBumonCd());
				deletePS.setString(2, Bean.getSyohinCd());
				deletePS.setString(3, Bean.getTenpoCd());
				deletePS.executeUpdate();
			    insertPS.setString(1, Bean.getBumonCd() );
			    insertPS.setString(2, Bean.getSyohinCd() );
			    insertPS.setString(3, Bean.getTenpoCd() );
			    insertPS.setString(4, Bean.getDonyuStDt() );
			    insertPS.setString(5, Bean.getDonyuEdDt() );
			    insertPS.setString(6, Bean.getNonActKb() );
			    insertPS.setString(7, Bean.getHaseimotoKb() );
			    insertPS.setString(8, Bean.getInsertTs() );
			    insertPS.setString(9, Bean.getInsertUserId() );
			    insertPS.setString(10,Bean.getUpdateTs() );
			    insertPS.setString(11,Bean.getUpdateUserId() );
			    insertPS.setString(12,Bean.getDeleteFg() );
			    insertPS.setString(13,Bean.getSinkiTorokuDt() );
			    insertPS.setString(14,Bean.getHenkoDt() );
			    insertPS.executeUpdate();
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
//				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,db.getDBSysdate() ) == null){
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,currentTime ) == null){
					
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}

//				db.execute(DM.getInsertSql(Bean));
			    insertPS.setString(1, Bean.getBumonCd() );
			    insertPS.setString(2, Bean.getSyohinCd() );
			    insertPS.setString(3, Bean.getTenpoCd() );
			    insertPS.setString(4, Bean.getDonyuStDt() );
			    insertPS.setString(5, Bean.getDonyuEdDt() );
			    insertPS.setString(6, Bean.getNonActKb() );
			    insertPS.setString(7, Bean.getHaseimotoKb() );
			    insertPS.setString(8, Bean.getInsertTs() );
			    insertPS.setString(9, Bean.getInsertUserId() );
			    insertPS.setString(10,Bean.getUpdateTs() );
			    insertPS.setString(11,Bean.getUpdateUserId() );
			    insertPS.setString(12,Bean.getDeleteFg() );
			    insertPS.setString(13,Bean.getSinkiTorokuDt() );
			    insertPS.setString(14,Bean.getHenkoDt() );
			    insertPS.executeUpdate();
			}
			//更新情報再取得
//			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//			rset = db.executeQuery(Info.getStatusSql(TableNa,whereList));
//			whereList.remove(whereList.size()-1);

			statusPS.setString(1, Bean.getBumonCd());
			statusPS.setString(2, Bean.getSyohinCd());
			statusPS.setString(3, Bean.getTenpoCd());
			statusPS.setString(4, mst000801_DelFlagDictionary.INAI.getCode());
			rset.close();
			rset = statusPS.executeQuery();
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

		rset.close();	//closeをしないと rset.next() を勝手に素通りするので changed by kema殿@2006.09.13
		return true;
		
	}

	
	//mst000101_DbmsBeanクラスのexecuteUpdateメソッドのPreparedStatement Versionです。
	/**
	 *	更新(Update Delete用)
	 *  引数で渡された更新年月日とテーブルの更新年月日を比較しbooleanで結果を戻す
	 *	@param String updateSql	//Update Delete用Sql
	 *	@param String chkSql		//更新年月日取得用Sql
	 *	@param String chkDT		//保存していた更新年月日
	 *  @return boolean true:更新日に差異がない（更新もおこなう） 
	 *                   false:更新日に差異がある（更新はおこなわない）
	 *	@exception java.sql.SQLException
	 */
	private boolean executeUpdatePreparedStatement(	final PreparedStatement checkPS,
														final PreparedStatement updatePS,
														final mst300201_TanpinTenToriatukaiBean bean, 
														final String chkDT )
		throws SQLException
	{
//		param.put("bumon_cd",dbBeanIns.getBumonCd());
//		param.put("syohin_cd",dbBeanIns.getSyohinCd());
//		param.put("tenpo_cd",dbBeanIns.getTenpoCd());
		checkPS.setString(1, bean.getBumonCd());
		checkPS.setString(2, bean.getSyohinCd());
		checkPS.setString(3, bean.getTenpoCd());
		
//		ResultSet rset = db.executeQuery(chkSql);
		ResultSet rset = checkPS.executeQuery();
		if (rset.next() == false) {		//if(rset.next()) は勝手に素通りするので changed by kema殿@2006.09.13
			return false;
		}
		
		if ((String)rset.getString("update_ts") == null) {
			return false;
		}
		
		String wkChkDt = (String)rset.getString("update_ts");	//更新年月日取得用
		
		if (!wkChkDt.trim().equals(chkDT)) {
			return false;
		}
		rset.close();						//closeをしないと rset.next() を勝手に素通りするので changed by kema殿@2006.09.13

//		db.executeUpdate(updateSql);
		updatePS.setString(1, RMSTDATEUtil.getMstDateDt());	// add by kema 06.11.10 変更日を先頭に追加したので、後へずらせました
		updatePS.setString(2, bean.getUpdateTs());
		updatePS.setString(3, bean.getUpdateUserTs());
		updatePS.setString(4, bean.getDeleteFg());
		updatePS.setString(5, bean.getBumonCd());
		updatePS.setString(6, bean.getSyohinCd());
		updatePS.setString(7, bean.getTenpoCd());
		updatePS.executeUpdate();
		
		return true;
	}

	
	/**
	 * 対象レコードの状態取得用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	private String getStatusSql()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("		 INSERT_TS      ");
		sb.append("		,UPDATE_TS      ");
		sb.append("		,INSERT_USER_ID ");
		sb.append("		,UPDATE_USER_ID ");
		sb.append("		,DELETE_FG      ");
		sb.append(" FROM                ");
		sb.append("		 R_TANPINTEN_TORIATUKAI ");
		sb.append(" WHERE               ");
		sb.append("    BUMON_CD     = ? ");
		sb.append("and SYOHIN_CD    = ? ");
		sb.append("and TENPO_CD     = ? ");
		sb.append("and DELETE_FG    = ? ");
		
		return sb.toString();
	}

	/**
	 * 対象レコードの状態取得用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	private String getStatusSqlForUpdate()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("		 INSERT_TS        ");
		sb.append("		,UPDATE_TS        ");
		sb.append("		,INSERT_USER_ID   ");
		sb.append("		,UPDATE_USER_ID   ");
		sb.append("		,DELETE_FG        ");
		sb.append(" FROM                  ");
		sb.append("		 R_TANPINTEN_TORIATUKAI ");
		sb.append(" WHERE                 ");
		sb.append("      BUMON_CD     = ? ");
		sb.append("  and SYOHIN_CD    = ? ");
		sb.append("  and TENPO_CD     = ? ");
		sb.append("  and DELETE_FG    = ? ");
		sb.append(" FOR UPDATE WITH RS ");
		
		return sb.toString();
	}
	//ADD by Tanigawa 2006/10/31 障害票№0227対応  END 

	
}
