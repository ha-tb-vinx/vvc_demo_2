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
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスタ管理）自動発注本部数量停止マスタ登録画面のデータ入力チェッククラス</p>
 * <p>説明: 	新ＭＤシステムで使用する自動発注本部数量停止マスタ登録画面のデータ入力チェッククラス</p>
 * <p>著作権: 	Copyright (c) 2005</p>
 * <p>会社名: 	Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst680401_CheckBean
{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		DataHolder 	dataHolder
	 * @param		String		kengenCd		: 権限コード
	 * @param		String 		GamenId 		: 画面ID
	 * @param		String 		FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 	CtrlName 		: コントロール名
	 */
	public void Check(
			mst000101_DbmsBean db,
			mst680201_KeepBean Keepb,
			mst680301_KeepMeisaiBean KeepMeisaib,
			DataHolder dataHolder,
			String kengenCd,
			String GamenId,
			String FirstFocusCtl,
			String[] CtrlName,
			Map CtrlColor,
			SessionManager  sessionManager) throws Exception,SQLException {
		// メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");
		// 初期Focus
		Keepb.setFirstFocus(FirstFocusCtl);

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT)){
				//画面内容を保存(ヘッダ部)
				// 販区コード
				Keepb.setHankuCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hankucd")).trim() );
				// 商品コード
				Keepb.setSyohinCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohincd")).trim() );
				// 店配列
				Keepb.setGroupNoCd( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tengroup_no")).trim() );
				// 代表有効日
				Keepb.setDaihyoYukoDt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_daihyo_yukodt")).trim() );
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_syorikb")).trim() );

				//画面内容を保存(明細部)
				List meisai = KeepMeisaib.getMeisai();
				ArrayList meisaiList = new ArrayList();
				for (int i = 0; i < meisai.size(); i++) {
					mst680101_JidoHachuHonbuSuryoTeisiBean dbBean = (mst680101_JidoHachuHonbuSuryoTeisiBean)meisai.get(i);
					if( dbBean.getDisbale() == null || dbBean.getDisbale().equals("") ){
						// 選択フラグ
						dbBean.setSentaku(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sentaku"+Integer.toString(i))));

						// 有効日
						// 未入力の場合は代表有効日をセット
						if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt"+Integer.toString(i))).equals("")){
							if(dbBean.getSentaku().equals("1")){
								dbBean.setYukoDt(mst000401_LogicBean.chkNullString(Keepb.getDaihyoYukoDt()));
							}
						}else{
							dbBean.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt"+Integer.toString(i))));
						}
						// 店舗コード
						dbBean.setTenpoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_tenpocd"+Integer.toString(i))));
						// 店販区コード
						dbBean.setTenHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_tenhankucd"+Integer.toString(i))));
						// 置換区分
						dbBean.setChikanKb( mst000401_LogicBean.chkNullString(dataHolder.getParameter("rb_chikankb"+Integer.toString(i))).trim() );
						// 停止数量
						dbBean.setTeisiQt( mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_teisi"+Integer.toString(i))).trim() );

						// 有効日範囲チェック(マスタ日付の翌日から6ヶ月以内まで入力可能)
						String[] strReturn = new String[3];
						if(!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
							// 選択時のみ
							if( dbBean.getSentaku().equals(mst000101_ConstDictionary.SENTAKU_CHOICE) ){
								// 有効日
								if(!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt"+Integer.toString(i))).equals("")){
									strReturn = getYukoDtRangeCheck(db, mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_yukodt"+Integer.toString(i))));
									if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
										mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_yukodt"+Integer.toString(i) );
										if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
											Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
											Keepb.setFirstFocus("tx_yukodt"+Integer.toString(i));
											mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_yukodt"+Integer.toString(i));
											Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
										}
									}
								}
							}
						}
					}
					meisaiList.add(dbBean);
				}
				KeepMeisaib.setMeisai(meisaiList);
			}

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			// メニューバーアイコン取得
			// SysSosasyaSesson取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
			menuMap.put("kengen_cd",kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
			Keepb.setMenuBar(menu);

			//エラーチェック
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

			//検索条件チェック-----------------------------------------------------------------------------
			// 代表有効日範囲チェック(マスタ日付より半年後まで入力可能)
			if(!Keepb.getDaihyoYukoDt().equals("")){
				String[] strReturn = new String[3];
				strReturn = getYukoDtRangeCheck(db, Keepb.getDaihyoYukoDt());
				if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
					mst000401_LogicBean.setErrorBackColor( CtrlColor, "tx_daihyo_yukodt" );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("tx_daihyo_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_daihyo_yukodt");
						Keepb.setErrorMessage("代表有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}
			}

			String CtlName = "tx_syohincd";

			// 販区
			if(!Keepb.getHankuCd().equals("")){
				HankuCheck( db, Keepb, CtrlColor, sessionManager, map, 0 );
			} else {
				Keepb.setHankuKanjiRn("");
			}

			//----------------------
			// 商品コード
			//----------------------
			CtlName = "tx_syohincd";

			String strGyosyuKb = "";		// 業種区分
			// 選択された業種
			if(SysUserBean.getSelectedGyosyuCd().equals("02")){
				// 「02：住生活」
				strGyosyuKb = mst000601_GyoshuKbDictionary.LIV.getCode();
			}else{
				// 「03：加工食品」
				strGyosyuKb = mst000601_GyoshuKbDictionary.GRO.getCode();
			}

			String MSTDATE = "";
			if(mst000401_LogicBean.chkNullString(Keepb.getDaihyoYukoDt()).equals("")){
				MSTDATE = RMSTDATEUtil.getMstDateDt();
			} else{
				MSTDATE = Keepb.getDaihyoYukoDt();
			}
			if( Keepb.getHankuCd().equals("") ){
				// 商品コードのみの存在チェック
				lst.clear();
				lst.add("    SYOHIN_CD = '" + Keepb.getSyohinCd() + "'");		// 商品コード
				lst.add("AND GYOSYU_KB = '" + strGyosyuKb + "'");				// 業種区分
// 2006.01.25 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN", "HINMEI_KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1);
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_SYOHIN", "HINMEI_KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1, MSTDATE);
// 2006.01.25 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					// 商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40026").toString());
					}
				} else {
					// 商品に対する販区のチェック
					String syohin_hanku = mst000401_LogicBean.getSyohinCd2HankuCd(db,Keepb.getSyohinCd(), strGyosyuKb);
					if(syohin_hanku == null){
						// 複数の販区が存在するエラー
						mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus( CtlName );
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("販区コード" + map.get("0021").toString());
						}
					} else {
						Keepb.setHankuCd(syohin_hanku);
						HankuCheck( db, Keepb, CtrlColor, sessionManager, map, 1 );
					}
				}
			}

			// 商品の存在チェック
			lst.clear();
			lst.add("    HANKU_CD  = '" + Keepb.getHankuCd() + "' ");			// 販区コード
			lst.add("AND SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");			// 商品コード
			lst.add("AND GYOSYU_KB = '" + strGyosyuKb + "'");					// 業種区分
// 2006.01.25 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SYOHIN","HINMEI_KANJI_NA", lst, mst000101_ConstDictionary.FUNCTION_PARAM_1, MSTDATE );
// 2006.01.25 Y.Inaba Mod ↑
			Keepb.setHinmeiKanjiNa( mstchk.getCdName() );
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				// 商品コード存在エラー
				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus( CtlName );
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40026").toString());
				}
			}

			//----------------------
			// 店配列
			//----------------------
			Keepb.setTenGroupNoNm("");
			if(!Keepb.getGroupNoCd().equals("")){
				lst.clear();

				lst.add(" 	  L_GYOSYU_CD = '" + dataHolder.getParameter("h_l_gyosyucd") + "'");		// 大業種
				lst.add(" AND YOTO_KB     = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "'");		// 用途区分「1：発注」
				lst.add(" AND GROUPNO_CD  = " + Keepb.getGroupNoCd());									// グルーピングNO
// 2006.01.25 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "NAME_NA", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENGROUPNO", "NAME_NA", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
				Keepb.setTenGroupNoNm(mstchk.getCdName());

				// データが存在しない場合
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tengroup_no");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus("tx_tengroup_no");
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された店配列" + map.get("40007").toString());
					}
				// データが存在する場合、店グルーピングマスタもチェック
				} else {
// 2006.01.25 Y.Inaba Mod ↓
//					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUP","TENPO_CD", lst, "0");
					mstchk = mst000401_LogicBean.getMasterCdName(db,"R_TENGROUP","TENPO_CD", lst, "0", "");
// 2006.01.25 Y.Inaba Mod ↑
					//データが存在しない場合
					if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"tx_tengroup_no");
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
							Keepb.setFirstFocus("tx_tengroup_no");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage("指定された店配列" + map.get("40007").toString());
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * 販区のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */
	public void  HankuCheck( mst000101_DbmsBean db, mst680201_KeepBean Keepb, Map CtrlColor,
								 SessionManager  sessionManager, TreeMap map, int flg ) throws Exception,SQLException {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		String CtlName = "tx_hankucd";

		List lst = new ArrayList();	// マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

		lst.add("	 SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");				// 種別コード
		lst.add("AND CODE_CD       = '" + Keepb.getHankuCd() + "'");							// 販区コード
// 2006.01.25 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, "");
// 2006.01.25 T.Inaba Mod ↑
		Keepb.setHankuKanjiRn(mstchk.getCdName());

		if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){

			// 販区コード存在エラー
			mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
				Keepb.setFirstFocus(CtlName);
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				if(flg == 0){
					Keepb.setErrorMessage("指定された販区コード" + map.get("40007").toString());
				} else {
					Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
				}
			}
		} else {
			// 選択業種との関連チェック
//			↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//			if(!mst000401_LogicBean.getHankuCdCheck(db,Keepb.getHankuCd(),sessionManager)){
//				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus(CtlName);
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					if( flg == 0){
//						Keepb.setErrorMessage(map.get("40022").toString());
//					} else {
//						Keepb.setErrorMessage("指定された商品コード" + map.get("40007").toString());
//					}
//				}
//			}
//			↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
		}
	}

	/**
	 *
	 * 有効日チェックを行う。
	 * @param      mst310201_KeepBean Keepb      : キープビーン
	 * @param		String[] 	CtrlName 		  : コントロール名
	 * @param      Map         map               : コントロールカラー
	 * @param      SessionManager sessionManager : セッションマネージャ
	 * @param		String		tableNa			  : 対象テーブル名称
	 * @param		String		columnNa		  : 有効日カラム名称
	 * @param		List        whereList		  : 有効日を除くKEY部
	 * @param		String		yuko_dt		  	  : 有効日
	 * @param		String		addValue		  : 暦日加算値
	 */
	public void YukoDtCheck( mst310201_KeepBean Keepb ,String[] CtrlName ,Map CtrlColor
						,SessionManager sessionManager ,String tableNa ,String columnNa
						,List whereList, String yuko_dt, String addValue, mst000101_DbmsBean db )
						throws Exception, SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

//		String chks = mst001001_EffectiveDayBean.getYukoDtCheck( tableNa, columnNa, whereList, addValue,yuko_dt, Keepb, db );
		String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,yuko_dt,Keepb,db,addValue,true);
		//エラー有り
		if( chks == null){
			Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH ) ){
				Keepb.setErrorMessage( map.get("0007").toString() );
			}
			if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE ) ){
				if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ){
					Keepb.setErrorMessage( map.get("0009").toString() );
				} else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ){
					Keepb.setErrorMessage( map.get("0010").toString() );
				} else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
					Keepb.setErrorMessage( map.get("0011").toString() );
				}
			}
		}
	}

	/**
	 * 有効日範囲チェック
	 * 入力された有効日がマスタ日付の範囲内かどうかをチェックします。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getYukoDtRangeCheck(db, yukoDt) -&gt;<br>
	 * <br>
	 * @param 		mst000101_DbmsBean	db			データベース
	 * @param 		String				yukoDt		入力した有効日
	 * @return		String[]	１番目：正常(N) エラー(E)
	 * 							２番目：有効開始日
	 * 							３番目：有効終了日
	 * 							※有効開始日と有効終了日はエラー時、エラーメッセージ表示のために使用
	 */
	public static String[] getYukoDtRangeCheck(mst000101_DbmsBean db, String yukoDt) throws SQLException {
		String[] strReturn = new String[3];
		String strRet = "E";

		String strYukoDtSt = "";							// 開始有効日
		String strYukoDtEd = "";							// 終了有効日

		try {
			StringBuffer buffSql = new StringBuffer();
			ResultSet rset = null;

			// 有効日(マスタ日付)、有効日(マスタ日付の6ヶ月後)
			buffSql.append(" SELECT ");
			buffSql.append("		TO_CHAR(TO_DATE(MST_DATE_DT, 'YYYYMMDD'), 'YYYYMMDD') AS YUKO_DT_STR, ");
			buffSql.append("		TO_CHAR(ADD_MONTHS(TO_DATE(MST_DATE_DT, 'YYYYMMDD'), 6), 'YYYYMMDD') AS YUKO_DT_END ");
			buffSql.append(" FROM SYSTEM_CONTROL ");

			rset = db.executeQuery(buffSql.toString());

			if(rset.next()){
				strYukoDtSt = rset.getString("YUKO_DT_STR");
				strYukoDtEd = rset.getString("YUKO_DT_END");

				// 範囲チェック
				if((Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt)
					 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
					strRet = "N";
				}
			}else{
				strRet = "E";
			}

			if(rset != null){
				rset.close();
			}

			strReturn[0] = strRet;
			strReturn[1] = strYukoDtSt;
			strReturn[2] = strYukoDtEd;
		} catch(SQLException sqle) {
			throw sqle;
		} finally {
//			if( null != db ) {
//				db.close();
//				db = null;
//			}
		}
		return strReturn;
	}
}