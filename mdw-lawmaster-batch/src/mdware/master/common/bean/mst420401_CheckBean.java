/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタの画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタ登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
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
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003401_KaisoPatternDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタの画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタ登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst420401_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・検索条件のエラーチェックを行う。
	 */
	public void CheckHeader(mst000101_DbmsBean db
							,mst420201_KeepBean Keepb
							,DataHolder dataHolder
							,String GamenId
							,String FirstFocusCtl
							,String[] CtrlName
							,Map CtrlColor
							,SessionManager sessionManager ) throws SQLException,Exception {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//エラー情報初期化
		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		//個別に権限取得
		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//画面入力値取得
			Keepb.setKaisouPatternKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kaisoupatternkb")));	//階層パターン
//			  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
			Keepb.setSystemKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_systemkb")));	//システム区分
//			  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
			Keepb.setCode1Cd(mst000401_LogicBean.chkNullString(mst000401_LogicBean.formatZero(dataHolder.getParameter("ct_code1cd"),4)));					//コード１
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")));	//処理状況
			Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// userSession取得
//			  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
			MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
			SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
			//Map menuMap = new HashMap();
			//menuMap.put("gamen_id",GamenId);
			//menuMap.put("kengen_cd",kengenCd);
			//menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			//String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
			//Keepb.setMenuBar(menu);
//			  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			List col = new ArrayList();	//マスタ存在チェック取得カラム
			String name = "";				//戻り値格納
			//Keepb.getCheckFlg()-- チェック処理判断
			//               チェック処理　初期--mst000101_ConstDictionary.CHECK_INIT = "0";
			//               チェック処理　検索--mst000101_ConstDictionary.CHECK_SEARCH = "1";
			//               チェック処理　更新（登録/修正/削除)--mst000101_ConstDictionary.CHECK_UPDATE = "2";
			//               チェック処理　照会--mst000101_ConstDictionary.CHECK_VIEW = "3";
			//               チェック処理　その他--mst000101_ConstDictionary.CHECK_OTHERS = "4";

			//検索処理チェック-----------------------------------------------------------------------------
			if (Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)) {
				String syubetu_cd = null;
				//コード１チェック（新規・修正・削除及び照会（上位）は上位コード）
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
// 2006.03.16 K.Satobo ADD ↓ (仕様変更：修正の検索入力コードを上位コードに変更する)
					Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) ||
// 2006.03.16 K.Satobo ADD ↑
					Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ||
					Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE_EXC)) {

//					  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
					//ユニット→ライン
					if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI4);
					//ライン→品種
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.LINE_HINSYU.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI3);
					//品種→品番
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINSYU_HINBAN.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI2);
					//品番→部門
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI1);
					//中間集計→小業種
					}
//					else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.TYUKANSYUKEI_SYOGYOSYU.getCode())) {
//						syubetu_cd = new String(mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS);
//					//小業種→大業種
//					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.SYOGYOSYU_DAIGYOSYU.getCode())) {
//						syubetu_cd = new String(mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS);
//					}
//					  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
				} else {
					//ユニット→ライン
					if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI5);
					//ライン→品種
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.LINE_HINSYU.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI4);
					//品種→品番
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINSYU_HINBAN.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI3);
					//品番→部門
					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
						syubetu_cd = new String(mst000101_ConstDictionary.BUNRUI2);
					//中間集計→小業種
					}
//					else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.TYUKANSYUKEI_SYOGYOSYU.getCode())) {
//						syubetu_cd = new String(mst000101_ConstDictionary.MIDDLE_TOTAL);
//					//小業種→大業種
//					} else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.SYOGYOSYU_DAIGYOSYU.getCode())) {
//						syubetu_cd = new String(mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS);
//					}
				}

				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();

				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(syubetu_cd, userLocal) + "' ");
				lst.add(" and ");
				//lst.add("CODE_CD = '" + Keepb.getCode1Cd() + "' ");
//				  ↓↓2006.08.01  shiyue カスタマイズ修正↓↓
				if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode()) && Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) {
				lst.add("CODE_CD =  '"+ Keepb.getSystemKb() +Keepb.getCode1Cd() + "' ");
				}else{
					lst.add("CODE_CD =  '" +mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "' ");
				}
//				  ↑↑2006.08.01  shiyue カスタマイズ修正↑↑
				//lst.add("CODE_CD =  '"+ Keepb.getSystemKb() +Keepb.getCode1Cd() + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード１存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_code1cd");
					Keepb.setFirstFocus("ct_code1cd");
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたコード"+map.get("40007").toString());
					Keepb.setCode1Rn("");
				} else {
					Keepb.setCode1Rn(mstchk.getCdName());
				}

				//処理状況：新規
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
					//最上位階層以外の場合、一つ上の階層に存在するかチェック
					if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
						lst.clear();
						lst.add("KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
//						  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						lst.add(" and ");
						lst.add("SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");	//システム区分
//						  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						lst.add(" and ");
						//lst.add("CODE1_CD = '" + Keepb.getCode1Cd() + "' ");
						lst.add("CODE1_CD =  '"+mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "' ");
						if (!mst000401_LogicBean.getMasterChk(db,"R_SYOHINKAISO", lst)) {
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_code1cd");
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setFirstFocus("ct_code1cd");
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("40204").toString());
							}
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * 明細入力に対するエラーチェックを行う。
	 * @param      mst000101_DbmsBean db                 : マスターメンテナンス用データベース接続クラス
	 * @param      mst420201_KeepBean Keepb              : キープビーン
	 * @param 		DataHolder 	dataHolder                : データホルダー
	 * @param		int         idx                       : チェック対象Index
	 * @param		mst420101_SyohinKaisoBean beforeBean  : 変更前レコード保存ビーン
	 */
	public void CheckMeisai(mst000101_DbmsBean db	,mst420201_KeepBean Keepb
							,Map CtrlColor
							,DataHolder dataHolder , int idx, mst420101_SyohinKaisoBean beforeBean) throws Exception , SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		List whereList = new ArrayList();
		List col = new ArrayList();
		List lst = new ArrayList();	//マスタ存在チェック抽出条件

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール

		String tableNa = new String("r_syohinkaiso");
		String columnNa = new String("yuko_dt");
		String	addValue 	  = "1";			 	//暦日加算値

		//確定処理チェック-----------------------------------------------------------------------------
		//選択された場合 JSで 1行選択時 "1" をすべて選択時 "2" を
		//掘り込んでるため処理されない by kema 06.09.21
		if (Keepb.getCheckFlg().equals(mst000101_ConstDictionary.RECORD_EXISTENCE) ||
			Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
			String yukoDt = mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_m_yukodt"+idx)).trim();
			String entryCode = mst000401_LogicBean.chkNullString(mst000401_LogicBean.formatZero(dataHolder.getParameter("ct_m_code"+idx),4)).trim();
			String[] syubetuArgs = getSyubetuCd(Keepb.getKaisouPatternKb());

// ↓↓仕様変更（2005.10.03）↓↓
			//切替日の範囲チェック
			String[] strReturn = new String[3];
			strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, yukoDt);
			if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("切替日は" + strReturn[1] + " から " + strReturn[2] + " の間で入力してください。");
			}
// ↑↑仕様変更（2005.10.03）↑↑

			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			//入力用コードの名称を取得
			if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
				whereList.clear();
				whereList.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(syubetuArgs[0], userLocal) + "' ");
				whereList.add(" and ");
//				  ↓↓2006.08.01  shiyue カスタマイズ修正↓↓
				if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode())){
					whereList.add("CODE_CD = '"+ Keepb.getSystemKb() + entryCode + "' ");
				}else{
					whereList.add("CODE_CD = '" + entryCode + "' ");
				}
//				  ↑↑2006.08.01  shiyue カスタマイズ修正↑↑
// 2006.01.24 Y.Inaba Mod ↓
//				mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", whereList, "0");
				mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", whereList, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたコード"+map.get("40007").toString());
					Keepb.setCodeErr(true);
				}
			} else if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
				whereList.clear();
				whereList.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(syubetuArgs[1], userLocal) + "' ");
				whereList.add(" and ");
				whereList.add("CODE_CD = '" + entryCode + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//				mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", whereList, "0");
				mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", whereList, "0", "");
// 2006.01.24 Y.Inaba Mod ↑
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定されたコード"+map.get("40007").toString());
					Keepb.setCodeErr(true);
				}
			}

			//処理状況：新規
			if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					//現在有効レコード又は予約レコードが存在する場合、エラー
					whereList.clear();
					whereList.add( "     KAISOU_PATTERN_KB  = '" + Keepb.getKaisouPatternKb() + "' " );
//					 ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
					whereList.add( "  AND   SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//					 ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
					whereList.add( " AND CODE1_CD  = '" + entryCode + "' " );
// 2006.03.14 K.Satobo MOD ↓ (新規時の存在チェック追加)
					whereList.add( " AND ((DELETE_FG='1' " );
					whereList.add( "     AND   YUKO_DT > '" + RMSTDATEUtil.getMstDateDt() + "') ");
					whereList.add( "   OR (DELETE_FG='0' " );
					whereList.add( "     AND   YUKO_DT = " );
					whereList.add( "           (SELECT MAX(YUKO_DT) MAX_YUKO_DT " );
					whereList.add( "                FROM " );
					whereList.add( "                    R_SYOHINKAISO " );
					whereList.add( "                WHERE " );
					whereList.add( "                    KAISOU_PATTERN_KB = '" + Keepb.getKaisouPatternKb() + "' " );
//					↓↓2006.06.23  shiyue カスタマイズ修正↓↓
					whereList.add( "                AND    SYSTEM_KB = '" + Keepb.getSystemKb() + "' " );
//					↑↑2006.06.23  shiyue カスタマイズ修正↑↑
					whereList.add( "                AND CODE1_CD = '" + entryCode + "'))) " );
					if(mst000201_EffectiveDayCheckBean.chkYukoDtInsert(db,tableNa,columnNa,whereList,"")){
//// 2006.01.20 Y.Inaba Mod ↓
////					if(!mst001001_EffectiveDayBean.getYukoDtCheckIns(db,tableNa,columnNa,whereList,addValue,yukoDt)) {
//					if(mst000201_EffectiveDayCheckBean.chkYukoDtInsert(db,tableNa,columnNa,whereList,yukoDt)){
//// 2006.01.20 Y.Inaba Mod ↑
// 2006.03.14 K.Satobo MOD ↑
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40001").toString());
					}
				}
//				  ↓↓2006.08.03  shiyue カスタマイズ修正↓↓
				if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
					lst.clear();
					lst.add("KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
					lst.add(" and ");
					lst.add("SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");	//システム区分
					lst.add(" and ");
					//lst.add("CODE1_CD = '" + Keepb.getCode1Cd() + "' ");
					lst.add("CODE1_CD =  '"+mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "' ");
					if (!mst000401_LogicBean.getMasterChk(db,"R_SYOHINKAISO", lst)) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_code1cd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("ct_code1cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40204").toString());
						}
					}
				}
//				  ↑↑2006.08.03  shiyue カスタマイズ修正↑↑
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					↓↓2006.06.23  shiyue カスタマイズ修正↓↓
					if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
//					↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						//大業種までひもづかない場合、エラー
						whereList.clear();
						whereList.add("KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
//						  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add(" and ");
						whereList.add( " SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//						  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						whereList.add(" and ");
						//whereList.add("CODE1_CD = '" + Keepb.getCode1Cd() + "' ");
						whereList.add("CODE1_CD = '" +mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "' ");
						whereList.add(" AND ");
						whereList.add(" YUKO_DT = ");
						whereList.add(" ( ");
						whereList.add(" SELECT ");
						whereList.add(" 	MAX(YUKO_DT) MAX_YUKO_DT ");
						whereList.add(" FROM ");
						whereList.add("	R_SYOHINKAISO ");
						whereList.add(" WHERE ");
						whereList.add(" 	delete_fg = '0' AND ");
						whereList.add(" 	YUKO_DT <= '" + yukoDt + "' AND ");
						whereList.add("     KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
						whereList.add("        and ");
//						↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add("     SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");
						whereList.add("        and ");
//						↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						//whereList.add("     CODE1_CD = '" + Keepb.getCode1Cd() + "') ");
						whereList.add("     CODE1_CD = '" +mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "') ");
						//取得カラム
						col.clear();
						col.add("CODE2_CD");
						Map mstchk = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHINKAISO",col, whereList, "0");
						if(mstchk == null || mstchk.size() == 0) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40204").toString());
						} else {
//							  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
							//バッティングチェック、販区コードを求める
							String bumoncd = null;
							//	String hankuCd = null;
//							↓↓2006.08.03  shiyue カスタマイズ修正↓↓
							//品種→品番
							if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINSYU_HINBAN.getCode())) {
							//hankuCd = (String)mstchk.get("CODE2_CD");
							bumoncd = (String)mstchk.get("CODE2_CD");
							}
//							else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.LINE_HINSYU.getCode())) {
//							bumoncd = 	mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4);
//							//bumoncd = 	Keepb.getCode1Cd();
//							//hankuCd = 	Keepb.getCode1Cd();
//							}
//							↑↑2006.08.03  shiyue カスタマイズ修正↑↑
							//if (hankuCd != null && hankuCd.length() > 0) {
							if (bumoncd != null && bumoncd.length() > 0) {
								//WHERE句作成
								whereList.clear();
								whereList.add(" 1 = 1");
								//DB検索
// 2006.01.20 Y.Inaba Mod ↓
//								ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(hankuCd),whereList) );    //抽出結果(ResultSet)
							//ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(hankuCd,yukoDt),whereList) );    //抽出結果(ResultSet)
								ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(bumoncd,yukoDt),whereList) );    //抽出結果(ResultSet)
// 2006.01.20 Y.Inaba Mod ↑
//								↑↑2006.06.22  shiyue カスタマイズ修正↑↑
								if (rset.next()) {
									if (rset.getInt("CNT") > 0) {
										Keepb.setFlgBatting(true);
										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
										Keepb.setErrorMessage(map.get("40205").toString());
									}
								}
							}
						}
					}
				}

			//処理状況：修正
			} else if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					//予約レコードが存在し、予約レコードの有効日以外入力の場合エラー
					whereList.clear();
					whereList.add( "     KAISOU_PATTERN_KB  = '" + Keepb.getKaisouPatternKb() + "' " );
//					↓↓2006.06.23  shiyue カスタマイズ修正↓↓
					whereList.add( "  AND   SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//					↑↑2006.06.23  shiyue カスタマイズ修正↑↑
// 2006.03.16 K.Satobo MOD ↓ (仕様変更：修正の検索入力コードを上位コードに変更する)
					whereList.add( " AND CODE1_CD  = '" +mst000401_LogicBean.formatZero(beforeBean.getCode1Cd(),4) + "' " );
//					whereList.add( " AND CODE1_CD  = '" + beforeBean.getCode1Cd() + "' " );
//					whereList.add( " AND CODE1_CD  = '" + Keepb.getCode1Cd() + "' " );
// 2006.03.16 K.Satobo MOD ↑
					if(!getChkYoyaku( db,  whereList, yukoDt )){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40203").toString());
					}
				}
//				  ↓↓2006.08.09  shiyue カスタマイズ修正↓↓
				if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
					lst.clear();
					lst.add("KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
					lst.add(" and ");
					lst.add("SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");	//システム区分
					lst.add(" and ");
					//lst.add("CODE1_CD = '" + Keepb.getCode1Cd() + "' ");
					lst.add("CODE1_CD =  '"+mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4) + "' ");
					if (!mst000401_LogicBean.getMasterChk(db,"R_SYOHINKAISO", lst)) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_code1cd");
						if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
							Keepb.setFirstFocus("ct_code1cd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40204").toString());
						}
					}
				}
//				  ↑↑2006.08.09  shiyue カスタマイズ修正↑↑
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
					if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
//					  ↑↑2006.06.22  shiyue カスタマイズ修正↑↑
						whereList.clear();
						whereList.add("KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
						whereList.add(" and ");
//						↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add("     SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");
						whereList.add("        and ");
//						↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						whereList.add("CODE1_CD = '" + entryCode + "' ");
						whereList.add(" AND ");
						whereList.add(" YUKO_DT = ");
						whereList.add(" ( ");
						whereList.add(" SELECT ");
						whereList.add(" 	MAX(YUKO_DT) MAX_YUKO_DT ");
						whereList.add(" FROM ");
						whereList.add("	R_SYOHINKAISO ");
						whereList.add(" WHERE ");
						whereList.add(" 	delete_fg = '0' AND ");
						whereList.add(" 	YUKO_DT <= '" + yukoDt + "' AND ");
						whereList.add("     KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) + 1)  + "' ");
//						↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add("        and ");
						whereList.add("     SYSTEM_KB = '" + Keepb.getSystemKb() + "' ");
//						↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						whereList.add("        and ");
						whereList.add("     CODE1_CD = '" + entryCode + "') ");
						//取得カラム
						col.clear();
						col.add("CODE2_CD");
						Map mstchk = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHINKAISO",col, whereList, "0");
						if(mstchk == null || mstchk.size() == 0) {
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40204").toString());
						} else {
							//バッティングチェック、販区コードを求める
//							  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
							//String hankuCd = null;
							String bumoncd = null;
//							↓↓2006.08.03  shiyue カスタマイズ修正↓↓
							//品種→品番
							if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.HINSYU_HINBAN.getCode())) {
							//hankuCd = (String)mstchk.get("CODE2_CD");
							bumoncd = (String)mstchk.get("CODE2_CD");
							}
//							else if (Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.LINE_HINSYU.getCode())) {
//							bumoncd = 	mst000401_LogicBean.formatZero(Keepb.getCode1Cd(),4);
//							//bumoncd = 	Keepb.getCode1Cd();
//							//hankuCd = 	Keepb.getCode1Cd();
//							}
//							↑↑2006.08.03  shiyue カスタマイズ修正↑↑
							//if (hankuCd != null && hankuCd.length() > 0) {
							if (bumoncd != null && bumoncd.length() > 0) {
								//WHERE句作成
								whereList.clear();
								whereList.add(" 1 = 1");
								//DB検索
// 2006.01.20 Y.Inaba Mod ↓
//								ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(hankuCd),whereList) );    //抽出結果(ResultSet)
								ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(bumoncd,yukoDt),whereList) );    //抽出結果(ResultSet)
								//ResultSet rset = db.executeQuery( mstDM.getCountSql(getBattingSql(hankuCd,yukoDt),whereList) );    //抽出結果(ResultSet)
// 2006.01.20 Y.Inaba Mod ↑
//								  ↑↑2006.06.22  shiyue カスタマイズ修正↑↑
								if (rset.next()) {
									if (rset.getInt("CNT") > 0) {
										Keepb.setFlgBatting(true);
										Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
										Keepb.setErrorMessage(map.get("40205").toString());
									}
								}
							}
						}
					}
				}

			//処理状況：削除
			} else if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)) {
				//予約レコードが存在し、予約レコードの有効日以外入力の場合エラー
				whereList.clear();
				whereList.add( "     KAISOU_PATTERN_KB  = '" + Keepb.getKaisouPatternKb() + "' " );
//				↓↓2006.06.23  shiyue カスタマイズ修正↓↓
				whereList.add( " AND    SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//				↑↑2006.06.23  shiyue カスタマイズ修正↑↑
				whereList.add( " AND CODE1_CD  = '"+mst000401_LogicBean.formatZero(beforeBean.getCode1Cd(),4) + "' " );
//				whereList.add( " AND CODE1_CD  = '" + beforeBean.getCode1Cd() + "' " );
				if(!getChkYoyaku( db,  whereList, yukoDt )){
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40203").toString());
				}
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
					if (!Keepb.getKaisouPatternKb().equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode())) {
//					  ↑↑2006.06.22  shiyue カスタマイズ修正↑↑
						//下位のデータが存在する場合、エラー
						whereList.clear();
						whereList.add(" KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) - 1) + "' ");
//						↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add( " AND    SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//						↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						whereList.add(" AND ");
						//whereList.add(" CODE2_CD = '" + beforeBean.getCode1Cd() + "' ");
						whereList.add(" CODE2_CD = '" +mst000401_LogicBean.formatZero(beforeBean.getCode1Cd(),4) + "' ");
						whereList.add(" AND ");
// [NO.18] 2006.03.23 K.Satobo MOD ↓ 削除時の下位コード存在チェック対応
						whereList.add(" ((DELETE_FG='1' ");
						whereList.add(" AND ");
						whereList.add(" YUKO_DT > '" + yukoDt + "') ");
						whereList.add(" OR ");
						whereList.add(" (DELETE_FG='0' ");
						whereList.add(" AND ");
						whereList.add(" YUKO_DT = ");
						whereList.add(" (SELECT MAX(YUKO_DT) MAX_YUKO_DT ");
						whereList.add(" FROM R_SYOHINKAISO ");
						whereList.add(" WHERE ");
						whereList.add(" KAISOU_PATTERN_KB = '" + (Integer.parseInt(Keepb.getKaisouPatternKb()) - 1) + "' ");
//						↓↓2006.06.23  shiyue カスタマイズ修正↓↓
						whereList.add( " AND    SYSTEM_KB  = '" + Keepb.getSystemKb() + "' " );
//						↑↑2006.06.23  shiyue カスタマイズ修正↑↑
						whereList.add(" AND ");
						//whereList.add(" CODE2_CD = '" + beforeBean.getCode1Cd() + "'))) ");
						whereList.add(" CODE2_CD = '" +mst000401_LogicBean.formatZero(beforeBean.getCode1Cd(),4) + "'))) ");
						if (mst000401_LogicBean.getMasterContDelChk(db,"R_SYOHINKAISO", whereList)) {
//						whereList.add("YUKO_DT >= '" + yukoDt + "' ");
//						if (mst000401_LogicBean.getMasterChk(db,"R_SYOHINKAISO", whereList)) {
// [NO.18] 2006.03.23 K.Satobo MOD ↑
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40206").toString());
						}
					}
				}
			}
		}
	}//CheckMeisai

	/**
	 * 予約レコード存在チェック
	 * <br>
	 * Ex)<br>
	 * mst001001_getChkYoyaku(tableNa, columnNa, whereList, addValue, yukoDt) -&gt; boolean<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean				: false/エラー
	 */
	public boolean getChkYoyaku(mst000101_DbmsBean db, List whereList, String yukoDt)  throws Exception,SQLException {
		boolean ret = true;
		StringBuffer sql = new StringBuffer();
// 2006.03.27 K.Satobo DEL ↓ 予約レコードの登録件数制御
//		//各種テーブルの更新情報の取得
//// 2006.01.20 Y.Inaba Mod ↓
////		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
//		mst000201_EffectiveDayCheckDM effectivedaydm = new mst000201_EffectiveDayCheckDM();    //対象テーブルの有効日情報のDMモジュール
//// 2006.01.20 Y.Inaba Mod ↑
// 2006.03.27 K.Satobo DEL ↑
		try {
			sql.append("select ");
			sql.append(" count(*) cnt");
			sql.append(",count(case when yuko_dt = '").append(yukoDt).append("' then 1 else null end) cnt_yuko");
			sql.append("  from  r_syohinkaiso");
			sql.append(" where ");
			//キー値セット
			for (int i=0; i < whereList.size(); i++) {
				sql.append(whereList.get(i));
			}
// 2006.03.27 K.Satobo MOD ↓ 予約レコードの登録件数制御
//// 2006.01.20 Y.Inaba Mod ↓
//// ↓↓仕様変更（2005.10.03）↓↓
////			sql.append(" AND yuko_dt > TO_CHAR(SYSDATE ,'YYYYMMDD') ");
////			sql.append(" AND yuko_dt > '" + RMSTDATEUtil.getMstDateDt() + "' ");
//			sql.append(" AND yuko_dt > '" + yukoDt + "' ");
			sql.append(" AND yuko_dt > '" + RMSTDATEUtil.getMstDateDt() + "' ");
//// ↑↑仕様変更（2005.10.03）↑↑
//// 2006.01.20 Y.Inaba Mod ↑
// 2006.03.27 K.Satobo MOD ↑
			ResultSet rset = db.executeQuery(sql.toString());//抽出結果(ResultSet)
			if (rset.next()) {
				if(Integer.parseInt((String)rset.getString("cnt")) > 0 &&
					Integer.parseInt((String)rset.getString("cnt_yuko")) == 0) {
					ret = false;
				}
			}
		} catch(SQLException sqle) {
			throw sqle;
		} catch(Exception e) {
			throw e;
		} finally {
		}
		return ret;
	}

	/**
	 * 階層パターンに対する種別コードを返却する。
	 *
	 * @param   mst420201_KeepBean	Keepb
	 * @return	String[] 種別コード[0]:下位コード 種別コード[1]:上位コード
	 *
	 */
	public String[] getSyubetuCd(String kaisouPatternKb) {
		String[] syubetu_cd = new String[2];

		//品種→品群
//		if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.HINSYU_HINGUN.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.KIND);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.GOODS_GROUP);
//		//品群→販区
//		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.HINGUN_HANKU.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.GOODS_GROUP);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.H_SALE);
//		//販区→売区
//		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.HANKU_URIKU.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.H_SALE);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.U_SALE);
//		//売区→中間集計
//		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.URIKU_TYUKANSYUKEI.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.U_SALE);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.MIDDLE_TOTAL);
//		//中間集計→小業種
//		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.TYUKANSYUKEI_SYOGYOSYU.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.MIDDLE_TOTAL);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS);
//		//小業種→大業種
//		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.SYOGYOSYU_DAIGYOSYU.getCode())) {
//			syubetu_cd[0] = new String(mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS);
//			syubetu_cd[1] = new String(mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS);
//		}
//		  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
		//ユニット→ライン
		if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.UNIT_LINE.getCode())) {
			syubetu_cd[0] = new String(mst000101_ConstDictionary.BUNRUI5);
			syubetu_cd[1] = new String(mst000101_ConstDictionary.BUNRUI4);
		//ライン→品種
		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.LINE_HINSYU.getCode())) {
			syubetu_cd[0] = new String(mst000101_ConstDictionary.BUNRUI4);
			syubetu_cd[1] = new String(mst000101_ConstDictionary.BUNRUI3);
		//品種→品番
		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.HINSYU_HINBAN.getCode())) {
			syubetu_cd[0] = new String(mst000101_ConstDictionary.BUNRUI3);
			syubetu_cd[1] = new String(mst000101_ConstDictionary.BUNRUI2);
		//品番→部門
		} else if (kaisouPatternKb.equals(mst003401_KaisoPatternDictionary.HINBAN_BUMON.getCode())) {
			syubetu_cd[0] = new String(mst000101_ConstDictionary.BUNRUI2);
			syubetu_cd[1] = new String(mst000101_ConstDictionary.BUNRUI1);
		}
//	  ↑↑2006.06.22  shiyue カスタマイズ修正↑↑
		return syubetu_cd;
	}

	/**
	 * 販区に対する商品コードのバッティングチェック用SQLを生成する。
	 *
	 * @param   String sql
	 * @return	String
	 * @throws SQLException
	 *
	 */
//	  ↓↓2006.06.22  shiyue カスタマイズ修正↓↓
	//public String getBattingSql(String hankuCd, String yukoDt) throws SQLException {
	public String getBattingSql(String bumoncd, String yukoDt) throws SQLException {
		StringBuffer sql = new StringBuffer();
//		↓↓移植（2006.05.29）↓↓
		sql.append("(select SYOHIN_CD");
		sql.append("   from r_syohin");
		sql.append("  where syohin_cd in (SELECT");
		sql.append("                      SYOHIN_CD");
		sql.append("                        FROM R_SYOHIN sh");
		sql.append("                       WHERE BUMON_CD = '").append(bumoncd).append("'");
		sql.append("                         AND YUKO_DT = msp710101_getsyohinyukodt(BUMON_CD ,SYOHIN_CD, cast(null as char)) )  ");
		//sql.append("                       WHERE BUMON_CD = '").append(hankuCd).append("'");
//		sql.append("                         AND YUKO_DT = (select max(YUKO_DT) ");
//		sql.append("                                          from R_SYOHIN");
//		sql.append("                                         where BUMON_CD = '").append(bumoncd).append("'");
//		//sql.append("                                         where BUMON_CD = '").append(hankuCd).append("'");
//		sql.append("                                           and SYOHIN_CD = sh.SYOHIN_CD");
//		sql.append("                                           and DELETE_FG = '0'");
//		sql.append("                                           and YUKO_DT <= '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
//		sql.append("                         AND DELETE_FG = '0' ))");
//	// 2006.01.20 Y.Inaba Mod ↑
//		sql.append(" or ");
//		sql.append("        syohin_cd in (SELECT");
//		sql.append("                      SYOHIN_CD");
//		sql.append("                        FROM R_SYOHIN sh");
//		sql.append("                       WHERE BUMON_CD = '").append(bumoncd).append("'");
//		//sql.append("                       WHERE BUMON_CD = '").append(hankuCd).append("'");
//		sql.append("                         AND YUKO_DT = (select min(YUKO_DT) ");
//		sql.append("                                          from R_SYOHIN");
//		sql.append("                                         where BUMON_CD = '").append(bumoncd).append("'");
//		//sql.append("                                         where HANKU_CD = '").append(hankuCd).append("'");
//		sql.append("                                           and SYOHIN_CD = sh.SYOHIN_CD");
//// 2006.01.20 Y.Inaba Mod ↓
////		sql.append("                                           and YUKO_DT > to_char(sysdate,'yyyymmdd')))");
//		sql.append("                                           and YUKO_DT > '" + yukoDt + "')) ");
//// 2006.01.20 Y.Inaba Mod ↑
//		sql.append("    and BUMON_CD != '").append(bumoncd).append("'");
		//sql.append("    and HANKU_CD != '").append(hankuCd).append("'");
//		↑↑移植（2006.05.29）↑↑
// ↓↓テストのため、一時カット（2005.10.18）↓↓
//		sql.append(" union");
//		sql.append(" select SYOHIN_CD");
//		sql.append("   from r_syohin");
//		sql.append("  where syohin_2_cd in (SELECT");
//		sql.append("                        SYOHIN_2_CD");
//		sql.append("                          FROM R_SYOHIN sh");
//		sql.append("                         WHERE HANKU_CD = '").append(hankuCd).append("'");
//		sql.append("                           AND YUKO_DT = (select max(YUKO_DT) ");
//		sql.append("                                            from R_SYOHIN");
//		sql.append("                                           where HANKU_CD = '").append(hankuCd).append("'");
//		sql.append("                                             and SYOHIN_CD = sh.SYOHIN_CD");
//		sql.append("                                             and DELETE_FG = '0'");
//		sql.append("                                             and YUKO_DT <= to_char(sysdate,'yyyymmdd'))");
//		sql.append("                           AND DELETE_FG = '0' ");
//		sql.append("                         union");
//		sql.append("                        SELECT");
//		sql.append("                       	SYOHIN_2_CD");
//		sql.append("                          FROM R_SYOHIN sh");
//		sql.append("                         WHERE HANKU_CD = '").append(hankuCd).append("'");
//		sql.append("                           AND YUKO_DT = (select min(YUKO_DT) ");
//		sql.append("                                            from R_SYOHIN");
//		sql.append("                                           where HANKU_CD = '").append(hankuCd).append("'");
//		sql.append("                                             and SYOHIN_CD = sh.SYOHIN_CD");
//		sql.append("                                             and YUKO_DT > to_char(sysdate,'yyyymmdd')))");
//		sql.append("   and BUMON_CD != '").append(hankuCd).append("' ");
//		sql.append("   and LENGTH(RTRIM(SYOHIN_2_CD)) > 5 ) wk");
		sql.append("   ) wk");
// ↑↑テストのため、一時カット（2005.10.18）↑↑
//	↑↑2006.06.22  shiyue カスタマイズ修正↑↑
		return sql.toString();
	}
}
