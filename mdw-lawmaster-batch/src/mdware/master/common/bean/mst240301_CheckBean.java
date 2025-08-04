/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）の画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
//BUGNO-S066 2005.06.14 Sirius START
//import mdware.master.common.dictionary.mst004801_NewSyoriRiyoukbDictionary;
//import mdware.master.common.dictionary.mst004901_UpdateSyoriRiyoukbDictionary;
//import mdware.master.common.dictionary.mst005001_DelRiyoukbDictionary;
//import mdware.master.common.dictionary.mst005101_ViewRiyoukbDictionary;
//BUGNO-S066 2005.06.14 Sirius END
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）の画面データ入力チェツククラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）登録画面データの入力チェツククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/22)初版作成
 */
public class mst240301_CheckBean
{
	/**
	 * 
	 * 権限チェック・KeepBeenへのセット・検索条件のエラーチェックを行う。
	 * @param      mst000101_DbmsBean db         : マスターメンテナンス用データベース接続クラス
	 * @param      mst240201_KeepBean Keepb      : キープビーン
	 * @param 		DataHolder 	dataHolder        : データホルダー
	 * @param		String		kengenCd		  : 権限コード
	 * @param		String 		GamenId 		  : 画面ID
	 * @param		String 		FirstFocusCtl  	  : 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 	CtrlName 		  : コントロール名
	 * @param      Map CtrlColor                 : コントロールカラー 
	 * @param      SessionManager sessionManager : セッションマネージャ        
	 */	
/*
	public void CheckHeader(mst000101_DbmsBean db
							,mst240201_KeepBean Keepb
							,DataHolder dataHolder 
							,String kengenCd 
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
//BUGNO-S066 2005.06.14 Sirius START
//		Map Kengen = mst000401_LogicBean.getKengen(db, kengenCd, GamenId);
//BUGNO-S066 2005.06.14 Sirius END
//BUGNO-S066 2005.06.14 Sirius START
/*		
		//画面に対する権限の有無を判定
		if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")).trim().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			if(!Kengen.get("insert_ok_kb").equals(mst004801_NewSyoriRiyoukbDictionary.KANOU.getCode())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_AUTHORITY_ERROR);
			}
		}

		if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")).trim().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
			if(!Kengen.get("update_ok_kb").equals(mst004901_UpdateSyoriRiyoukbDictionary.KANOU.getCode())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_AUTHORITY_ERROR);
			}
		}

		if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")).trim().equals(mst000101_ConstDictionary.PROCESS_DELETE)){
			if(!Kengen.get("delete_ok_kb").equals(mst005001_DelRiyoukbDictionary.KANOU.getCode())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_AUTHORITY_ERROR);
			}
		}

		if(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")).trim().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)){
			if(!Kengen.get("reference_ok_kb").equals(mst005101_ViewRiyoukbDictionary.KANOU.getCode())){
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_AUTHORITY_ERROR);
			}
		}
		
		*/

//BUGNO-S066 2005.06.14 Sirius END

//		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			//操作権限設定
//BUGNO-S066 2005.06.14 Sirius START
//			Keepb.setInsertFlg((String)Kengen.get("insert_ok_kb"));
//			Keepb.setUpdateFlg((String)Kengen.get("update_ok_kb"));
//			Keepb.setDeleteFlg((String)Kengen.get("delete_ok_kb"));
//			Keepb.setReferenceFlg((String)Kengen.get("reference_ok_kb"));
//			Keepb.setCsvFlg((String)Kengen.get("csv_ok_kb"));
//			Keepb.setPrintFlg((String)Kengen.get("print_ok_kb"));
//BUGNO-S066 2005.06.14 Sirius END
			//画面入力値取得
/*
			Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));	//商品コード
			Keepb.setSyohinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));	//商品名
			Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));	//仕入先品番
			Keepb.setProcessingDivision(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_processingdivision")));	//処理状況
			
			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//メーニューバーアイコン取得
			// SysSosasyaSesson取得
			mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
			SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
			Map menuMap = new HashMap();
			menuMap.put("gamen_id",GamenId);
			menuMap.put("kengen_cd",kengenCd);
			menuMap.put("sentaku_gyosyu_cd", SysUserBean.getSelectedGyosyuCd());
			String[] menu = mst000401_LogicBean.getCommonMenubar(db, menuMap);
			Keepb.setMenuBar(menu);

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

				//商品マスタ存在チェック
				
				//検索条件
//2005.12.07 A.Jo START 商品マスタ検索時、有効日を顧慮するように変更。
//				lst.clear();
//				lst.add( " GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.A12.getCode() + "' ");
//				lst.add( " AND SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
//				//取得カラム
//				col.clear();
//				col.add("HINMEI_KANJI_NA");
//				col.add("SIIRE_HINBAN_CD");
//				col.add("HANKU_CD");
//				Map mstchk = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHIN",col, lst, "0");
//				if(mstchk == null || mstchk.size() == 0) {
//					//商品コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setFirstFocus("ct_syohincd");
//						Keepb.setErrorMessage("指定された自社品番" + map.get("40007").toString());
//					}
//					Keepb.setSyohinmeiKanjiNa("");
//					Keepb.setSiireHinbanCd("");
//				} else {
//					Keepb.setSyohinmeiKanjiNa(mstchk.get("HINMEI_KANJI_NA").toString());
//					Keepb.setSiireHinbanCd(mstchk.get("SIIRE_HINBAN_CD").toString());
//					//販区コード妥協性チェック
//					if(!mst000401_LogicBean.getHankuCdCheck(db, mstchk.get("HANKU_CD").toString(),sessionManager)){
//						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");	
//						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
//							Keepb.setFirstFocus("ct_syohincd");
//							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//							Keepb.setErrorMessage(map.get("40026").toString());
//						}											
//					}
//				}
				ResultSet rst = null;
				String sql = makeRSyohinSql(Keepb);
				rst = db.executeQuery(sql);
				rst.next();
				if(rst == null || rst.getRow() == 0) {
					//商品コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_syohincd");
						Keepb.setErrorMessage("指定された自社品番" + map.get("40007").toString());
					}
					Keepb.setSyohinmeiKanjiNa("");
					Keepb.setSiireHinbanCd("");
				} else {
					Keepb.setSyohinmeiKanjiNa(rst.getString("HINMEI_KANJI_NA"));
					Keepb.setSiireHinbanCd(rst.getString("SIIRE_HINBAN_CD"));
					//販区コード妥協性チェック
					if(!mst000401_LogicBean.getHankuCdCheck(db, rst.getString("HANKU_CD"),sessionManager)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");	
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
							Keepb.setFirstFocus("ct_syohincd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40026").toString());
						}											
					}
				}
//2005.12.07 A.Jo END

//BUGNO-S070 2005.07.11 Sirius START
//				//アソートメントマスタ存在チェック
				if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
					rst = null;
					StringBuffer sb = new StringBuffer();
					//未登録サイズの有無をチェック
					sb.append("select ");
					sb.append(" count(distinct R_namectf.code_cd) cnt");
					sb.append("  from ");
					sb.append(" R_namectf left outer join"); 
					sb.append("(select size_cd ");
					sb.append("   from R_Assortment"); 
					sb.append("  where R_Assortment.syohin_cd = '").append(dataHolder.getParameter("ct_syohincd")).append("'"); 
					sb.append("    and R_Assortment.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("')  R_Assortment");
					sb.append(" on substr(R_namectf.code_cd,5,2) = R_Assortment.size_cd");
					sb.append(" where R_namectf.syubetu_no_cd = '").append(mst000101_ConstDictionary.SIZE).append("'");
					sb.append("   and R_namectf.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
					sb.append("   and R_namectf.code_cd like '").append(dataHolder.getParameter("ct_syohincd").substring(0,4)).append("%'");
					sb.append("   and R_Assortment.size_cd is null");				
					rst = db.executeQuery(sb.toString());
					rst.next();
					if (rst.getInt("cnt")== 0) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");	
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
							Keepb.setFirstFocus("ct_syohincd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("40001").toString());
						}											
					}
				} else {	
					lst.clear();
					lst.add( " SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
//					↓↓ＤＢバージョンアップによる変更（2005.05.25）				
//					boolean ret = mst000401_LogicBean.getMasterChk(db, "R_Asortment", lst );
					boolean ret = mst000401_LogicBean.getMasterChk(db, "R_Assortment", lst );
//					↑↑ＤＢバージョンアップによる変更（2005.05.25）
//					//処理状況：新規
//					if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
//						//データ有り
//						if (ret) {
//							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");	
//							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
//								Keepb.setFirstFocus("ct_syohincd");
//								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//								Keepb.setErrorMessage(map.get("40001").toString());
//							}											
//						}
//					} else {
					//データ無し
					if (!ret) {
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_syohincd");	
						if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){							
							Keepb.setFirstFocus("ct_syohincd");
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							Keepb.setErrorMessage(map.get("0005").toString());
						}											
					}
				}
//BUGNO-S070 2005.07.11 Sirius END

//BUGNO-006 2005.04.20 T.Makuta START
//			}
			} else {
				//検索条件
//2005.12.07 A.Jo START 商品マスタ検索時、有効日を顧慮するように変更。
//				lst.clear();
//				lst.add( " GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.A12.getCode() + "' ");
//				lst.add( " AND SYOHIN_CD = '" + Keepb.getSyohinCd() + "' ");
//				//取得カラム
//				col.clear();
//				col.add("HINMEI_KANJI_NA");
//				col.add("SIIRE_HINBAN_CD");
//				col.add("HANKU_CD");
//				Map mstchk = mst000401_LogicBean.getMasterItemValues(db, "R_SYOHIN",col, lst, "0");
//				Keepb.setSyohinmeiKanjiNa(mstchk.get("HINMEI_KANJI_NA").toString());
//				Keepb.setSiireHinbanCd(mstchk.get("SIIRE_HINBAN_CD").toString());
				ResultSet rst = null;
				String sql = makeRSyohinSql(Keepb);
				rst = db.executeQuery(sql);
				rst.next();
				if(rst == null || rst.getRow() == 0) {
					Keepb.setSyohinmeiKanjiNa(rst.getString("HINMEI_KANJI_NA"));
					Keepb.setSiireHinbanCd(rst.getString("SIIRE_HINBAN_CD"));
				}
//2005.12.07 A.Jo END
			}
//BUGNO-006 2005.04.20 T.Makuta END
		}

		}
*/
	/**
	 * 2005.12.07 A.Jo ADD
	 * 商品マスタ存在チェックと表示名称の取得SQLを作成する。
	 * @param      mst240201_KeepBean Keepb      : キープビーン
	 */	
/*
	public String makeRSyohinSql(mst240201_KeepBean Keepb){
		
		String GYOSYU_CD = mst000601_GyoshuKbDictionary.A12.getCode();
		String DEL_FG = mst000801_DelFlagDictionary.INAI.getCode();

		ResultSet rst = null;
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("	rs.hanku_cd as hanku_cd");
		sb.append("	, ");
		sb.append("	rs.syohin_cd as syohin_cd");
		sb.append("	, ");
		sb.append("	rs.yuko_dt as yuko_dt");
		sb.append("	, ");
		sb.append("	rs.hinmei_kanji_na as hinmei_kanji_na");
		sb.append("	, ");
		sb.append("	rs.siire_hinban_cd as siire_hinban_cd ");
		sb.append("from ");
		sb.append("	r_syohin rs ");
		sb.append("where ");
		sb.append(" rs.syohin_cd = '" + Keepb.getSyohinCd() + "'"); //ファイル種別
		sb.append(" and ");
		sb.append(" rs.gyosyu_kb = '").append(GYOSYU_CD).append("' ");
		sb.append(" and ");
		sb.append(" rs.delete_fg = '").append(DEL_FG).append("' ");
		sb.append(" and ");
		sb.append(" rs.yuko_dt = (select max(yuko_dt) from r_syohin sub where sub.hanku_cd = rs.hanku_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt <= '").append(RMSTDATEUtil.getMstDateDt()).append("')"); //有効日

		StringBuffer sql = new StringBuffer();

		sql.append(sb);
		sql.append("union all ");
		sql.append("select ");
		sql.append(" rs.hanku_cd as hanku_cd");
		sql.append(" , ");
		sql.append(" rs.syohin_cd as syohin_cd");
		sql.append(" , ");
		sql.append(" rs.yuko_dt as yuko_dt");
		sql.append(" , ");
		sql.append(" rs.hinmei_kanji_na as hinmei_kanji_na");
		sql.append(" , ");
		sql.append("	rs.siire_hinban_cd as siire_hinban_cd ");
		sql.append("from ");
		sql.append(" r_syohin rs ");
		sql.append("where ");
		sql.append(" rs.syohin_cd = '" + Keepb.getSyohinCd() + "'"); //ファイル種別
		sql.append(" and ");
		sql.append(" rs.gyosyu_kb = '").append(GYOSYU_CD).append("' ");
		sql.append(" and ");
		sql.append(" rs.yuko_dt = (select min(yuko_dt) from r_syohin sub where sub.hanku_cd = rs.hanku_cd and sub.syohin_cd = rs.syohin_cd and sub.yuko_dt > '").append(RMSTDATEUtil.getMstDateDt()).append("' and sub.delete_fg = '").append(DEL_FG).append("')"); //有効日
		sql.append(" and");
		sql.append(" not exists");
		sql.append(" (");
		sql.append("  select '1'");
		sql.append("  from");
		sql.append("   (").append(sb).append(") sub2");
		sql.append("  where");
		sql.append("    sub2.hanku_cd = rs.hanku_cd and");
		sql.append("    sub2.syohin_cd = rs.syohin_cd");
		sql.append(" ) ");
		sql.append("order by ");
		sql.append(" hanku_cd,");
		sql.append(" syohin_cd,");
		sql.append(" yuko_dt");

		return sql.toString();
	}
*/	
	/**
	 * 
	 * 明細入力に対するエラーチェックを行う。
	 * @param      mst000101_DbmsBean db         : マスターメンテナンス用データベース接続クラス
	 * @param      mst240201_KeepBean Keepb      : キープビーン
	 * @param 		DataHolder 	dataHolder
	 * @param		String[] 	CtrlName 		: コントロール名 
	 */	
/*
	public void CheckMeisai(mst000101_DbmsBean db
							,mst240201_KeepBean Keepb
							,DataHolder dataHolder
							,String[] CtrlName) throws SQLException {
							
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		List lst = new ArrayList();	//マスタ存在チェック抽出条件

//BUGNO-S070 2005.07.11 Sirius START
		HashMap sizeColorMap = new HashMap();	//サイズカラー重複チェック用
		boolean isEntry = false;
//BUGNO-S070 2005.07.11 Sirius END

		//確定処理チェック-----------------------------------------------------------------------------
		if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){

			ArrayList sizeList = new ArrayList();
			ArrayList colorList = new ArrayList();

			//横軸（サイズ）
			for (int x = 0;x < Keepb.getSizeCnt();x++) {

				//画面入力のサイズを再設定
				String sizeCd = dataHolder.getParameter("ct_m_size_" + x);
				mst000301_NameCtfBean sizebean = (mst000301_NameCtfBean)Keepb.getSizeList().get(x);
				sizebean.setCodeCd(sizeCd);
				sizeList.add(sizebean);
								
				//横軸（カラー）
				for (int y = 0;y < Keepb.getColorCnt();y++) {

					mst000301_NameCtfBean colorbean = (mst000301_NameCtfBean)Keepb.getColorList().get(y);
					if (x == 0) {
						//画面入力のサイズを再設定
						String colorCd = dataHolder.getParameter("ct_m_color_" + y);
						colorbean.setCodeCd(colorCd);
						colorList.add(colorbean);
					}

					mst240101_AsortmentBean dispBean = Keepb.getMeisaiArgs(x,y);
//BUGNO-S070 2005.07.11 Sirius START
					Map CtrlMeisaiColor = mst000401_LogicBean.getBackColorInit(dispBean.getCtlname());
					mst240101_AsortmentBean beforeBean = Keepb.getBeforeMeisaiArgs(x,y);
//BUGNO-S070 2005.07.11 Sirius END

					//画面入力値をBeanに再設定
					if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
						Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
						String pattern1Qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[0]));
						String pattern2Qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[1]));
						String pattern3Qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[2]));
						String pattern4Qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[3]));
						String pattern5Qt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[4]));
//						↓↓ＤＢバージョンアップによる変更（2005.05.25）
//						String nehudaQt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[5]));
//						↑↑ＤＢバージョンアップによる変更（2005.05.25）
//						復活start（2005.07.05）
						String tagHakoQt = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[5]));
//						復活end（2005.07.05）
//						↓↓ＤＢバージョンアップによる変更
						String hachuTeisiKb = mst000401_LogicBean.chkNullString(dataHolder.getParameter(dispBean.getCtlname()[6]));
//						↑↑ＤＢバージョンアップによる変更
						dispBean.setPattern1Qt(pattern1Qt);
						dispBean.setPattern2Qt(pattern2Qt);
						dispBean.setPattern3Qt(pattern3Qt);
						dispBean.setPattern4Qt(pattern4Qt);
						dispBean.setPattern5Qt(pattern5Qt);
//						↓↓ＤＢバージョンアップによる変更（2005.05.25）
//						dispBean.setNehudaQt(nehudaQt);
//						↑↑ＤＢバージョンアップによる変更（2005.05.25）
//						復活start（2005.07.05）
						dispBean.setTagHakoQt(tagHakoQt);
//						復活end（2005.07.05）
//						↓↓ＤＢバージョンアップによる変更
						dispBean.setHachuTeisiKb(hachuTeisiKb);
//						↑↑ＤＢバージョンアップによる変更
						Keepb.setMeisaiArgs(dispBean,x,y);
//BUGNO-S070 2005.07.11 Sirius START

//						↓↓仕様追加（2005.07.29）↓↓
						boolean pt_flg = true;
						if (pattern1Qt.equals("")) {
							pt_flg = false;
						}
						if (pattern2Qt.equals("")) {
							pt_flg = false;
						} else {
							if (!pt_flg) {
								mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[1]);
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("アソートパターンは入力欄１から順に入力して下さい");
								}
							}
						}
						if (pattern3Qt.equals("")) {
							pt_flg = false;
						} else {
							if (!pt_flg) {
								mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[2]);
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("アソートパターンは入力欄１から順に入力して下さい");
								}
							}
						}
						if (pattern4Qt.equals("")) {
							pt_flg = false;
						} else {
							if (!pt_flg) {
								mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[3]);
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("アソートパターンは入力欄１から順に入力して下さい");
								}
							}
						}
						if (pattern5Qt.equals("")) {
							pt_flg = false;
						} else {
							if (!pt_flg) {
								mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[4]);
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage("アソートパターンは入力欄１から順に入力して下さい");
								}
							}
						}
//						↑↑仕様追加（2005.07.29）↑↑

						//入力チェック
  						if (!mst000401_LogicBean.isSingleNumber(pattern1Qt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("パターン数量1" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[0]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
  						}
						if (!mst000401_LogicBean.isSingleNumber(pattern2Qt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("パターン数量2" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[1]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
						}
						if (!mst000401_LogicBean.isSingleNumber(pattern3Qt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("パターン数量3" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[2]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
						}
						if (!mst000401_LogicBean.isSingleNumber(pattern4Qt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("パターン数量4" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[3]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
						}
						if (!mst000401_LogicBean.isSingleNumber(pattern5Qt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("パターン数量5" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[4]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
						}
						if (!mst000401_LogicBean.isSingleNumber(tagHakoQt)) {
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorMessage("タグ発行枚数" + map.get("0022").toString());
							}
							mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[5]);
							Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
							dispBean.setCtlColor(CtrlMeisaiColor);
							Keepb.setMeisaiArgs(dispBean,x,y);
						}
						//画面内重複チェック
						//何か入力されている又は修正の場合にチェック
					 	if (dispBean.getPattern1Qt().trim().length() != 0 ||
							dispBean.getPattern2Qt().trim().length() != 0 ||
							dispBean.getPattern3Qt().trim().length() != 0 ||
							dispBean.getPattern4Qt().trim().length() != 0 ||
							dispBean.getPattern5Qt().trim().length() != 0 ||
							dispBean.getTagHakoQt().trim().length() != 0 ||
							dispBean.getHachuTeisiKb().trim().length() != 0 ||
							(beforeBean.getInsertTs() != null && beforeBean.getInsertTs().length() > 0)) {
							String sizeColor = dataHolder.getParameter("ct_m_size_" + x) + dataHolder.getParameter("ct_m_color_" + y);
							if (sizeColorMap.get(sizeColor) != null) {
								if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
									Keepb.setErrorMessage(map.get("40302").toString());
								}
								//背景色設定
								for (int i = 0;i < dispBean.getCtlname().length;i++) {
									mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[i]);	
								}
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
								String[] idxs = sizeColorMap.get(sizeColor).toString().split(",");
								mst240101_AsortmentBean dispBean2 = Keepb.getMeisaiArgs(Integer.parseInt(idxs[0]),Integer.parseInt(idxs[1]));
								for (int i = 0;i < dispBean2.getCtlname().length;i++) {
									mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean2.getCtlname()[i]);	
								}
								dispBean2.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean2,Integer.parseInt(idxs[0]),Integer.parseInt(idxs[1]));
								
							} else {
								sizeColorMap.put(sizeColor,x + "," + y);
							}
							isEntry = true;
						}
//BUGNO-S070 2005.07.11 Sirius END
					}
					
//BUGNO-S070 2005.07.11 Sirius START
					//mst240101_AsortmentBean beforeBean = Keepb.getBeforeMeisaiArgs(x,y);
					//Map CtrlMeisaiColor = mst000401_LogicBean.getBackColorInit(dispBean.getCtlname());
//BUGNO-S070 2005.07.11 Sirius END
					
					if (beforeBean.getInsertTs() != null && beforeBean.getInsertTs().length() > 0) {
						//修正かつ値クリア又は処理状況：削除
						if ((Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE) &&
							dispBean.getPattern1Qt().length() == 0 &&
							dispBean.getPattern2Qt().length() == 0 &&
							dispBean.getPattern3Qt().length() == 0 &&
							dispBean.getPattern4Qt().length() == 0 &&
							dispBean.getPattern5Qt().length() == 0 &&
//						↓↓ＤＢバージョンアップによる変更（2005.05.25）
//							dispBean.getNehudaQt().length() == 0 &&
//						↑↑ＤＢバージョンアップによる変更（2005.05.25）
//						復活start（2005.07.05）
							dispBean.getTagHakoQt().length() == 0 &&
//						復活end（2005.07.05）
//						↓↓ＤＢバージョンアップによる変更
							dispBean.getHachuTeisiKb().length() == 0) ||
//						↑↑ＤＢバージョンアップによる変更
							Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE)) {
							
							//初回導入マスタ存在チェック
							lst.clear();
							lst.add( " COLOR_CD = '" + beforeBean.getColorCd() + "' ");
							lst.add( " AND SIZE_CD = '" + beforeBean.getSizeCd() + "' ");
							boolean ret = mst000401_LogicBean.getMasterChk(db, "R_SYOKAIDONYU", lst );
							
							//データ有り
							if (ret) {
								//背景色設定
								for (int i = 0;i < dispBean.getCtlname().length;i++) {
									mst000401_LogicBean.setErrorBackColor(CtrlMeisaiColor,dispBean.getCtlname()[i]);	
								}
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
										Keepb.setFirstFocus(dispBean.getCtlname()[0]);
									}
									String errName = sizebean.getKanjiRn() + "・" + colorbean.getKanjiRn();
									if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
										Keepb.setErrorMessage(errName + map.get("40301").toString());
									}
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									dispBean.setPattern1Qt(beforeBean.getPattern1Qt());
									dispBean.setPattern2Qt(beforeBean.getPattern2Qt());
									dispBean.setPattern3Qt(beforeBean.getPattern3Qt());
									dispBean.setPattern4Qt(beforeBean.getPattern4Qt());
									dispBean.setPattern5Qt(beforeBean.getPattern5Qt());
//									↓↓ＤＢバージョンアップによる変更（2005.05.25）
//									dispBean.setNehudaQt(beforeBean.getNehudaQt());
//									↑↑ＤＢバージョンアップによる変更（2005.05.25）
//									復活start（2005.07.05）
									dispBean.setTagHakoQt(beforeBean.getTagHakoQt());
//									復活end（2005.07.05）
//									↓↓ＤＢバージョンアップによる変更
									dispBean.setHachuTeisiKb(beforeBean.getHachuTeisiKb());
//									↑↑ＤＢバージョンアップによる変更
								}
								dispBean.setCtlColor(CtrlMeisaiColor);
								Keepb.setMeisaiArgs(dispBean,x,y);
							}
						}
					}
									
				}//End For カラー

			}//End For サイズ
			
//BUGNO-S070 2005.07.11 Sirius START
			//新規・修正で更新対象がない場合
			if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT) ||
				Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
				if (!isEntry && Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					Keepb.setErrorMessage(map.get("0027").toString());
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				}
			}
//BUGNO-S070 2005.07.11 Sirius END
			
			Keepb.setSizeList(sizeList);
			Keepb.setColorList(colorList);
		}
	}
	
*/
		/**
	 * 
	 * 明細入力に対するデータ更新時エラーチェックを行う。
	 * @param      mst000101_DbmsBean db               : マスターメンテナンス用データベース接続クラス
	 * @param      mst240201_KeepBean Keepb            : キープビーン
	 * @param      Map where                           : 検索条件
	 * @param      mst240101_AsortmentDM asortmentDM   : アソートメントマスタDMクラス
	 * @param      mst240101_AsortmentBean asortmentDM : アソートメントマスタレコードクラス
	 */	
/*
	public void CheckUpdateMeisai(mst000101_DbmsBean db
								  ,mst240201_KeepBean Keepb
								  ,Map where
								  ,mst240101_AsortmentDM asortmentDM
								  ,mst240101_AsortmentBean bean) throws SQLException {
		
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		ResultSet rset = null;			//抽出結果(ResultSet)

		rset = db.executeQuery(asortmentDM.getSelectSql(where));
		if(rset.next()){
			Keepb.setDataExists(true);
			String deleteFg = mst000401_LogicBean.chkNullString(rset.getString("delete_fg"));
			String updateTs = mst000401_LogicBean.chkNullString(rset.getString("update_ts"));
			//登録モードの場合、削除フラグ＝0が、存在していたらエラー
			if (Keepb.getUpdateProcessFlg().equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT) &&
				deleteFg.trim().equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//背景色設定
				for (int i = 0;i < bean.getCtlname().length;i++) {
					mst000401_LogicBean.setErrorBackColor(bean.getCtlColor(),bean.getCtlname()[i]);	
				}
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
						Keepb.setFirstFocus(bean.getCtlname()[0]);
					}
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40001").toString());
				}
			}
			//更新モードの場合、タイムスタンプがかわっていたらエラー
			if (Keepb.getUpdateProcessFlg().equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)) {
				if (!bean.getUpdateTs().trim().equals(updateTs.trim())) {
					//背景色設定
					for (int i = 0;i < bean.getCtlname().length;i++) {
						mst000401_LogicBean.setErrorBackColor(bean.getCtlColor(),bean.getCtlname()[i]);	
					}
					if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
						if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
							Keepb.setFirstFocus(bean.getCtlname()[0]);
						}
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40004").toString());
					}
				}
			}			
		} else {
			Keepb.setDataExists(false);
			//有効レコードが存在しない場合はエラー
			if (Keepb.getUpdateProcessFlg().equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)) {
				//背景色設定
				for (int i = 0;i < bean.getCtlname().length;i++) {
					mst000401_LogicBean.setErrorBackColor(bean.getCtlColor(),bean.getCtlname()[i]);	
				}
				if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					if (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
						Keepb.setFirstFocus(bean.getCtlname()[0]);
					}
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("40004").toString());
				}
			}			
		}
		rset.close();

	}
*/

}

