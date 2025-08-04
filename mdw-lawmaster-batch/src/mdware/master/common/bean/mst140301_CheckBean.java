/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/012)初版作成
 * @version 1.1(2005/05/20)新ER対応 Y.Inaba
 * 　　　　　削除…店配送先管理コード(ten_haiso_kanri_cd)
 * @version 1.2(2006/02/06)新規の場合のみ削除フラグがあるデータを無視する D.Matsumoto
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
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;
import mdware.master.common.dictionary.mst006001_HacyuSyohinKbDictionary;
import mdware.master.common.dictionary.mst007501_ZeiKbDictionary;
import mdware.master.util.RMSTDATEUtil;
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面データ入力チェッククラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ登録画面データの入力チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst140301_CheckBean
{
	/**
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 */
	public void Check(
		mst000101_DbmsBean db,
		mst140201_KeepBean Keepb,
		DataHolder dataHolder,
		String kengenCd,
		String GamenId,
		String FirstFocusCtl,
		String[] CtrlName,
		Map CtrlColor,
		String tableNa,
		List whereList,
		SessionManager  sessionManager ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");

		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
			if( !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_INIT) ){
				//画面内容を保存
				Keepb.setSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohincd")));							//商品コード
				Keepb.setHinmeiKanji(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinna")));						//商品名
				Keepb.setYukoDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yukodt")));								//有効日
				Keepb.setPluSendDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_plusenddt")));							//PLU送信日
				Keepb.setSyohinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syohinkb")));							//商品区分
				Keepb.setProcessingDivision( mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syorikb")).trim() );
				Keepb.setChangeFlg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("h_changeflg")).trim());

				//画面内容を保存(BODY)
				Keepb.setHankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankucd")));								//販区コード
				Keepb.setHankuKanjiRn(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hankuna"))));//販区名
				Keepb.setHinsyuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyucd")));							//品種コード
				Keepb.setHinsyuNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinsyuna"))));	//品種名
				Keepb.setHinmokuCd(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokucd"))));	//品目
				Keepb.setHinmokuNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmokuna")));							//品目
				Keepb.setMakerCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makercd")));							//JANメーカーコード
				Keepb.setMakerNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_makerna")));							//JANメーカー名称
				Keepb.setHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanana")));					//カナ品名
				Keepb.setKikakuKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanana")));					//カナ規格
				Keepb.setHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hinmeikanjina")));					//漢字品名
				Keepb.setKikakuKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_kikakukanjina")));					//漢字規格
				Keepb.setYunyuhinKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_yunyuhinkb")));						//仕入れ
				Keepb.setSantiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_santicd")));								//国
				Keepb.setBlandCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandcd")));								//ブランドコード
				Keepb.setBlandNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_blandna"))));		//ブランド
				Keepb.setSyuzeiHokokuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_syuzeihokokukb")));				//酒税報告分類
				Keepb.setGotMujyokenFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gotmujyokenfg")));					//GOT無条件表示対象
				Keepb.setUnitPriceTaniKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricetanikb")));				//ユニットプライス-単位区分
				Keepb.setUnitPriceNaiyoryoQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricenaiyoryoqt")));		//ユニットプライス-内容量
				Keepb.setUnitPriceKijunTaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_unitpricekijuntaniqt")));	//ユニットプライス-基準単量
				Keepb.setGentankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankavl")));						//原価単価
				Keepb.setGentankaSenVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_gentankasenvl")));					//原価単価(銭)
				Keepb.setBaitankaVl(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_baitankavl")));						//売価単価(税込)
				Keepb.setHachutaniIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachutaniirisuqt")));			//発注単位(入数)
				Keepb.setMaxHachutaniQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_maxhachutaniqt")));				//最大発注単位
				Keepb.setSiiresakiCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakicd")));						//仕入先コード
				Keepb.setSiiresakiNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siiresakirn"))));//仕入先名
				Keepb.setTenSiiresakiKanriCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanricd")));		//店別仕入先管理コード
				Keepb.setTenSiiresakiKanriNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tensiiresakikanrina"))));//店別仕入先管理名
				Keepb.setDaihyoHaisoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisocd")));					//代表配送先コード
				Keepb.setDaihyoHaisoNa(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_daihyohaisona"))));	//代表配送先名
				Keepb.setSiireHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_siirehinbancd")));					//メーカー品番
				Keepb.setEosKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_eoskb")));									//EOS区分
				Keepb.setTenZaikoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenzaikokb")));						//店在庫区分
				Keepb.setButuryuKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_buturyukb")));							//物流区分
				Keepb.setHachuTeisiKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hachuteisikb")));					//発注停止区分
				Keepb.setKeypluFg(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_keyplufg")));							//キーPLU対象
				Keepb.setRecHinmeiKanaNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanana")));				//POSレシート品名(カナ)
				Keepb.setRecHinmeiKanjiNa(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_rechinmeikanjina")));			//POSレシート品名(漢字)
				Keepb.setPcKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_pckb")));									//PC発行区分
				Keepb.setHanbaiLimitKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitkb")));					//販売期限区分
				Keepb.setHanbaiLimitQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_hanbailimitqt")));					//販売期限
				Keepb.setNohinKigenKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinkigenkb")));					//納品期限区分
				Keepb.setNohinKigenQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinkigenqt")));					//納品期限
				Keepb.setNohinOndoKb(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinondokb")));						//納品温度帯
				Keepb.setCaseIrisuQt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_caseirisuqt")));						//ケース入り数
				Keepb.setNohinSyohinCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_nohinsyohincd")));					//納品商品コード
			}

			Keepb.setMode(mst000401_LogicBean.chkNullString(dataHolder.getParameter("mode"))	);
			Keepb.setExistFlg("");	//データ存在(検索[ｷｬﾝｾﾙ]時)
			Keepb.setErrorBackDisp("");

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
			String[] menu = mst000401_LogicBean.getCommonMenubar(db,menuMap);
			Keepb.setMenuBar(menu);

			//エラーチェック
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				List lst = new ArrayList();	//マスタ存在チェック抽出条件
				String name = "";				//戻り値格納
				mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
				String CtlName = "";
				String chkDeg = "";
				//検索条件処理チェック　/////////////////////////////////////////////////////////////////////
				// 有効日範囲チェック(マスタ日付より1年後まで入力可能)
				// 照会時はチェックを行わない
				String[] strReturn = new String[3];
				if((!Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) && (Keepb.getYukoDt() != null) && (!Keepb.getYukoDt().trim().equals(""))){
// ↓↓仕様変更（2005.08.19）↓↓
//					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db, Keepb.getYukoDt());
					strReturn = mst000401_LogicBean.getYukoDtRangeCheckSyohin(db, Keepb.getYukoDt());
// ↑↑仕様変更（2005.08.19）↑↑
					if(strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setFirstFocus("ct_yukodt");
						mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
						Keepb.setErrorMessage("有効日は" + strReturn[1] + "～" + strReturn[2] + "の間で入力してください。");
					}
				}
// ↓↓仕様変更（2005.08.19）↓↓
				//即時伝発対応
				if ((Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
					||	Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
					&&	Keepb.getYukoDt().equals(RMSTDATEUtil.getMstDateDt())) {

					List wlst = new ArrayList();
					StringBuffer sb = new StringBuffer();
					ResultSet rst = null;
					String genYukoDt = "";

					wlst.add("syohin_cd = '" + Keepb.getSyohinCd() + "' ");
					if (Keepb.getHankuCd() != null && Keepb.getHankuCd().length() > 0){
						wlst.add(" AND ");
						wlst.add("hanku_cd = '" + Keepb.getHankuCd() + "' ");
					}
//					genYukoDt = mst001001_EffectiveDayBean.getGenYoyaku("r_syohin","yuko_dt",wlst,"0",db);
					genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db,"r_syohin","yuko_dt",wlst,"0",true);

					sb.append(" SELECT ");
					sb.append("		sinki_toroku_dt ");
					sb.append(" FROM ");
					sb.append("		r_syohin ");
					sb.append(" WHERE ");
					for (int i=0; i < wlst.size(); i++) {
						sb.append(wlst.get(i));
					}
					sb.append(" AND ");
					sb.append(" yuko_dt = '" + genYukoDt + "' ");

					rst = db.executeQuery(sb.toString());
					if (rst.next()) {
						if (!genYukoDt.equals(mst000401_LogicBean.chkNullString(rst.getString("sinki_toroku_dt")))) {
							mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_yukodt");
							if (Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage("有効日が今日なので、即時伝発対象商品以外の修正・削除はできません。");
								Keepb.setFirstFocus("ct_yukodt");
							}
						}
					}
					if (rst != null) {
						rst.close();
					}
				}
// ↑↑仕様変更（2005.08.19）↑↑

				//商品コード
				CtlName = "ct_syohincd";
				Keepb.setHinmeiKanji("");
				//処理状況：【新規】
				if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
					if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_SEARCH)){
						if( Keepb.getSyohinCd().length() >= 12 && Keepb.getSyohinCd().substring(0,4).equals("0400")){
							//品種の存在チェック
							chkHinsyu( db, Keepb, CtrlColor, CtlName, sessionManager, map, Keepb.getSyohinCd().substring(4,8) );
						}
					}
				}
				//商品の存在チェック
				String chkSyohinCd = "";
				//チェックデジット
				chkSyohinCd = getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length() );
				//チェックデジット
				if(chkSyohinCd.equals("")){
					//チェックデジットエラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40201").toString());
					}
				} else {
					//商品の存在チェック
//					mstchk = getSyohin( db, chkSyohinCd ,"" ,Keepb.getGyosyuKb() ,"gyosyu_kb" ,Keepb.getYukoDt() );
					mstchk = getSyohin( db, chkSyohinCd ,"" ,Keepb.getGyosyuKb() ,"gyosyu_kb" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());
					if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_INSERT)){
						if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							// 2006/02/06 ↓IF文追加 削除フラグが０の場合のみ商品コード存在エラー
							if(mstchk.getDeleteFg().equals(mst000101_ConstDictionary.DELETE_FG_NOR)){
								//商品コード存在エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									if(Keepb.getGyosyuKb().equals( mstchk.getCdName().trim())){
										Keepb.setErrorMessage(map.get("40001").toString());
									} else {
										Keepb.setErrorMessage(map.get("40026").toString());
									}
								}
							}
							// 2006/02/06 ↑IF文追加
						}
					} else {
						if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
							//商品コード存在エラー
							mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
							if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
								Keepb.setFirstFocus( CtlName );
								Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
								Keepb.setErrorMessage(map.get("0005").toString());
							}
						} else {
							if(!Keepb.getGyosyuKb().equals( mstchk.getCdName().trim())){
								//商品コード存在エラー
								mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
								if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
									Keepb.setFirstFocus( CtlName );
									Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
									Keepb.setErrorMessage(map.get("40026").toString());
								}
							}
						}
					}
//					mstchk = getSyohin( db, chkSyohinCd ,"" ,Keepb.getGyosyuKb() ,"hinmei_kanji_na" ,Keepb.getYukoDt() );
					mstchk = getSyohin( db, chkSyohinCd ,"" ,Keepb.getGyosyuKb() ,"hinmei_kanji_na" ,Keepb.getYukoDt(),Keepb.getProcessingDivision());
					Keepb.setHinmeiKanji( mstchk.getCdName() );
				}
				//処理状況：【修正・削除】
				if( (Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
					|| Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE))
						&& Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					whereList.clear();
//					whereList.add(" syohin_cd = '" + chkSyohinCd + "' ");
//					whereList.add(" AND gyosyu_kb = '" + Keepb.getGyosyuKb() + "' ");

				// ↓↓　2006/03/30 kim START
				//	加工食品の場合、販区を''にして有効日のチェックをする。
				//	keepbeanの中で既存販区の値が書き込んでいるので有効日のチェックする時、エラー出る。
					Keepb.setHankuCd("");
				// ↑↑　2006/03/30 kim END
					//有効日チェック
// 2006.02.09 Y.Inaba Mod ↓
//					String chks = mst001001_EffectiveDayBean.getYukoDtCheck(tableNa,columnNa,whereList,addValue,Keepb.getYukoDt(),Keepb,db);
					String chks = mst000201_EffectiveDayCheckBean.getYukoDtCheck(tableNa,Keepb.getYukoDt(),Keepb,db,"0",false);
// 2006.02.09 Y.Inaba Mod ↑
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
							}
							else if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
								Keepb.setErrorMessage( map.get("0011").toString() );
							}
						}
					} else if ( chks.equals(mst000101_ConstDictionary.ERR_CHK_ERROR) ){
						for ( int i = 0;i<CtrlName.length;i++ ){
							mst000401_LogicBean.setErrorBackColor(CtrlColor,CtrlName[i]);
						}
					}
				}
			}
			//検索条件処理チェック   END ////////////////////////////////////////////////////////////////
			if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
				Keepb.setErrorBackDisp(mst000101_ConstDictionary.ERR_CHK_ERROR);
			}

			if( Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE) ){
				//更新処理処理チェック   //////////////////////
				UpdateCheck( db, Keepb, CtrlColor, sessionManager, map );
			}
		}
	}
	/**
	 * 指定商品マスタの取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String hankuCd				: 販区コード
	 * @param		String gyosyuKbr 			: 業種区分
	 * @param		String columnNa				: 取得カラム名
	 * @param		String yukoDt				: 有効日
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
	public mst000701_MasterInfoBean getSyohin( mst000101_DbmsBean db, String syohinCd, String hankuCd, String gyosyuKb,
					String columnNa, String yukoDt, String syoriKb) throws Exception,SQLException {

		mst110101_SyohinDM 	 	 dm  = new mst110101_SyohinDM();

		mst000701_MasterInfoBean ret = new mst000701_MasterInfoBean();
		ret.setCdName("");
		ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);
		//2006/02/06 追加 削除フラグを初期化する
		ret.setDeleteFg("");

		//マスタ日付
		String MSTDATE = RMSTDATEUtil.getMstDateDt();
		String yuko_Dt = "";

		Map selectMap  = new HashMap();
		selectMap.put("syohin_cd",syohinCd);
		if(!hankuCd.equals("")){
		  selectMap.put("hanku_cd",hankuCd);
		}
		if (!gyosyuKb.equals("")) {
			selectMap.put("gyosyu_kb",gyosyuKb);
		}
		selectMap.put("yuko_dt",yukoDt);

		ResultSet rset = null;    //抽出結果(ResultSet)
		rset = db.executeQuery(dm.getSelectSql(selectMap));
		if(rset.next()){
			ret.setCdName(mst000401_LogicBean.chkNullString(rset.getString(columnNa)).trim());
			ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
			//2006/02/06 追加 削除フラグをセットする
			ret.setDeleteFg(mst000401_LogicBean.chkNullString(rset.getString("delete_fg")).trim());
		} else {
			//商品(現在有効)の存在チェック
			List whereList = new ArrayList();
			whereList.clear();
			whereList.add("  syohin_cd = '" + syohinCd + "' ");
			if(!hankuCd.equals("")){
				whereList.add(" AND hanku_cd = '" + hankuCd + "' ");
			}
			if (!gyosyuKb.equals("")) {
				whereList.add(" AND gyosyu_kb = '" + gyosyuKb + "' ");
			}
// 2005.11.30 Y.Inaba 処理区分により処理を分岐 START
			//現在有効日を取得
			if(syoriKb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)
				&& (yukoDt != null) && (!yukoDt.trim().equals(""))){
				yuko_Dt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, false);
			} else{
				yuko_Dt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, "r_syohin","yuko_dt",whereList, MSTDATE, true);
			}
// 2005.11.30 Y.Inaba 処理区分により処理を分岐 END
			if(!yuko_Dt.trim().equals("")){
				selectMap.put("yuko_dt",yuko_Dt);
				ResultSet rset1 = null;    //抽出結果(ResultSet)
				rset1 = db.executeQuery(dm.getSelectSql(selectMap));
				if(rset1.next()){
					ret.setCdName(mst000401_LogicBean.chkNullString(rset1.getString(columnNa)).trim());
					ret.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
					//2006/02/06 追加 削除フラグをセットする
					ret.setDeleteFg(mst000401_LogicBean.chkNullString(rset1.getString("delete_fg")).trim());
				}
			}
		}
		return ret;
	}
	/**
	 * 品種のチェックと販売区分の取得を行う。
	 * @param		mst000101_DbmsBean db		: DBインスタンス
	 * @param		String syohinCd				: 商品コード
	 * @param		String hankuCd				: 販区コード
	 * @param		String gyosyuKbr 			: 業種区分
	 * @param		String columnNa				: 取得カラム名
	 * @return		mst000701_MasterInfoBean 	: 取得結果
	 */
	public void chkHinsyu( mst000101_DbmsBean db, mst140201_KeepBean Keepb, Map CtrlColor, String CtlName,
									 SessionManager  sessionManager, TreeMap map, String hinsyuCd) throws Exception,SQLException {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		List lst = new ArrayList();
		lst.clear();
		lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		lst.add(" and ");
		lst.add("code_cd = '" + hinsyuCd + "' ");
// 2006.02.09 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
		mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
		Keepb.setHinsyuNa( mstchk.getCdName() );
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//品種コード存在エラー
				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus( CtlName );
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage(map.get("0315").toString());
				}
			} else {
				//ログインユーザの売区とのチェック
				if(!mst000401_LogicBean.getHinshuCdCheck(db,hinsyuCd,sessionManager)){
						mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40023").toString());
					}
				}
			}
		}
		//販区の取得（品種入力時に商品体系マスタより取得）
		lst.clear();
		lst.add("hinsyu_cd = '" + hinsyuCd + "' ");
		mstchk = mst000401_LogicBean.getSyohinTaikei(db,"hanku_cd", lst );
		Keepb.setHankuCd( mstchk.getCdName() );
		//販区名称取得
		lst.clear();
		lst.add("SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		lst.add(" and ");
		lst.add("CODE_CD = '" + Keepb.getHankuCd() + "' ");
// 2006.02.09 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0);
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt());
// 2006.02.09 Y.Inaba Mod ↑
		Keepb.setHankuKanjiRn(mstchk.getCdName());
		//販区チェック
		if(Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_UPDATE)){
			//処理状況：修正
			if(Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){

				mstchk = getSyohin( db, getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length()) ,"" ,Keepb.getGyosyuKb() ,"hanku_cd" ,Keepb.getYukoDt(), Keepb.getProcessingDivision());

				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)
						|| !Keepb.getHankuCd().equals(mstchk.getCdName().trim())){
					//商品コード存在無しエラーor販区が変更されたエラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage(map.get("40023").toString());
					}
				}
			}
		}
	}

	/**
	 * 指定商品のチェックデジットのチェック＆取得を行う。
	 * @param		String syohinCd		: 商品コード
	 * @param		int len   			: 商品コード入力桁数
	 * @return		String[] 			: [0]エラー有無,[1]商品コード
	 */
	public String getCheckDegit( String syohinCd , int len) {

		String ret = syohinCd;
		String chkDeg = "";
		//7桁は前0削除
		if(len == 7 ){
			syohinCd = syohinCd.substring(1).trim();
		}
		//04xx入力時の処理
		if(len == 2 || len == 4){
			if(syohinCd.substring(0,2).equals("04")){
				if(syohinCd.length() > 4 && syohinCd.length() < 12){
					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
					syohinCd = syohinCd.trim() + chkDeg.trim();
				}
			}
		}
		switch (syohinCd.length()) {
			case 8:
				chkDeg = mst001401_CheckDegitBean.getModulus10( "0000"+syohinCd.substring(0,7) ,12 );
				if(!chkDeg.equals(syohinCd.substring(7))){
					ret = "";
				}
				break;
			case 10:
				syohinCd = "00" + syohinCd.trim();
				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.trim() ,12 );
				syohinCd = syohinCd.trim() + chkDeg.trim();
				break;
			case 11:
				syohinCd = "0" + syohinCd.trim();
				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.trim() ,12 );
				syohinCd = syohinCd.trim() + chkDeg.trim();
				break;
			case 12:
				if( syohinCd.substring(0,4).equals("0400")){
					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
					if(!chkDeg.equals(syohinCd.substring(11,12))){
						ret = "";
					}
					chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
					syohinCd = syohinCd.trim() + chkDeg.trim();
				} else {
					if(len > 2){
						syohinCd = "0" + syohinCd.trim();
						chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
						if(!chkDeg.equals(syohinCd.substring(12,13))){
							ret = "";
						}
					} else {
						chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
						syohinCd = syohinCd.trim() + chkDeg.trim();
					}
				}
				break;
			case 13:
				if( syohinCd.substring(0,4).equals("0400")){
					chkDeg = mst001401_CheckDegitBean.getModulus11( syohinCd.substring(4,11) ,7 );
					if(!chkDeg.equals(syohinCd.substring(11,12))){
						ret = "";
					}
				}
				chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
				if(!chkDeg.equals(syohinCd.substring(12))){
					ret = "";
				}
				break;
			default:break;
		}

		if(!ret.equals("")){
			ret = syohinCd;
		}
		return ret;
	}

	/**
	 *
	 * 新規/修正時の入力項目のチェックを行う。
	 * @param		mst000101_DbmsBean db					: DBインスタンス
	 * @param		mst310201_KeepBean mst310201_KeepBean 	: 画面情報
	 * @param		Map 			   CtrlColor			: コントロール表示色
	 * @param		SessionManager     sessionManager 		: SessionManager
	 * @param		TreeMap            map					: メッセージ
	 */
	public void  UpdateCheck( mst000101_DbmsBean db, mst140201_KeepBean Keepb, Map CtrlColor,
							 SessionManager  sessionManager, TreeMap map ) throws Exception,SQLException {
		List lst = new ArrayList();	//マスタ存在チェック抽出条件
		mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
		String CtlName = "";
		String chkDeg = "";
		//〔品種コード〕
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		CtlName = "ct_hinsyucd";
		chkHinsyu( db, Keepb, CtrlColor, CtlName, sessionManager, map, Keepb.getHinsyuCd());
		//〔品目〕
		CtlName = "ct_hinmokucd";
		if(!Keepb.getHinmokuCd().equals("")){
			//品目の存在チェック
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.DAIBUNRUI1, userLocal) + "' ");
			lst.add(" and ");
			lst.add("code_cd = '" + Keepb.getHinsyuCd() + Keepb.getHinmokuCd() + "' ");
// 2006.02.09 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
			Keepb.setHinmokuNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//品目コード存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された品目"+map.get("40007").toString());
					}
				}
			}
		} else {
			Keepb.setHinmokuNa( "" );
		}
		// 〔JANメーカーコード〕
		CtlName = "ct_makercd";
		if(!Keepb.getMakerCd().equals("")){
			//JANメーカーコードの存在チェック
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.JAN_MAKER_NAME, userLocal) + "'");
			lst.add(" and ");
			lst.add("code_cd = '" + Keepb.getMakerCd() + "' ");
// 2006.02.09 Y.Inaba Mod ↑
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
			Keepb.setMakerNa( mstchk.getCdName() );
// ↓↓仕様変更（2005.07.25）↓↓
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//					//JANメーカーコード存在エラー
//					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus( CtlName );
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定されたJANメーカーコード" + map.get("40007").toString());
//					}
//				}
//			}
// ↑↑仕様変更（2005.07.25）↑↑
		} else {
			Keepb.setMakerNa( "" );
		}
		//〔ブランドコード〕
		CtlName = "ct_blandcd";
		if(!Keepb.getBlandCd().equals("")){
			//ブランド区分の存在チェック
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BRAND, userLocal) + "' ");
			lst.add(" and ");
// ↓↓ブランドコード仕様変更対応(2005.09.26)
// ↓↓仕様変更（2005.07.22）↓↓
//			lst.add("code_cd = '" + Keepb.getBlandCd() + "' ");
//			lst.add("code_cd = '" + Keepb.getHankuCd() + Keepb.getBlandCd() + "' ");
			lst.add("trim(code_cd) = '" + mst000101_ConstDictionary.DUMMY_CD + Keepb.getBlandCd() + "' ");
//			↑↑仕様変更（2005.07.22）↑↑
// ↑↑ブランドコード仕様変更対応(2005.09.26)
// 2006.02.09 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
			Keepb.setBlandNa( mstchk.getCdName() );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//ブランド区分存在エラー
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定されたブランドコード"+map.get("40007").toString());
					}
				}
			}
		} else {
			Keepb.setBlandNa( "" );
		}

// ↓↓仕様変更（2005.08.24）↓↓
//		//処理状況：【修正】のみ
//		if( Keepb.getProcessingDivision().equals(mst000101_ConstDictionary.PROCESS_UPDATE)
//			&& !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS) ){
//			String syohin = getCheckDegit( Keepb.getSyohinCd(), Keepb.getSyohinCd().length() );
//			//〔原価単価(税抜)〕
//			CtlName = "ct_gentankavl";
//			String gentanka = mst000401_LogicBean.removeComma(Keepb.getGentankaVl()).trim();
//			if(!Keepb.getGentankaSenVl().trim().equals("")){
//				if(Keepb.getGentankaSenVl().length() < 2){
//					gentanka = gentanka + ".0" + Keepb.getGentankaSenVl().trim();
//				} else {
//					gentanka = gentanka + "." + Keepb.getGentankaSenVl().trim();
//				}
//			}
//			lst.clear();
//			lst.add( " HANKU_CD = '" + Keepb.getHankuCd() + "' ");
//			lst.add( " AND ");
//			lst.add( " SYOHIN_CD = '" + syohin + "' ");
//			lst.add( " AND ");
//			lst.add( " KAKAKU_KB = '" + mst004501_KakakuKbDictionary.GEN_TANKA.getCode() + "' ");
//			lst.add( " AND ");
//			lst.add( " MIN_KAKAKU_VL > '" + gentanka + "' ");
//			lst.add( " AND ");
//			lst.add( " CHECK_ST_DT <= '" + Keepb.getYukoDt() + "' ");
//			lst.add( " AND ");
//			lst.add( " CHECK_ED_DT >= '" + Keepb.getYukoDt() + "' ");
//			lst.add( " AND ");
//			lst.add( " DELETE_FG = '0' ");
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_KAKAKUCHECK","MIN_KAKAKU_VL", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//存在(有)エラー
//				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//				mst000401_LogicBean.setErrorBackColor( CtrlColor, "ct_gentankasenvl" );
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus( CtlName );
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("原価単価"+map.get("40202").toString());
//				}
//			}
//			//〔売価単価（税込）〕
//			CtlName = "ct_baitankavl";
//			lst.clear();
//			lst.add( " HANKU_CD = '" + Keepb.getHankuCd() + "' ");
//			lst.add( " AND ");
//			lst.add( " SYOHIN_CD = '" + syohin + "' ");
//			lst.add( " AND ");
//			lst.add( " KAKAKU_KB = '" + mst004501_KakakuKbDictionary.URI_TANKA.getCode() + "' ");
//			lst.add( " AND ");
//			lst.add( " MIN_KAKAKU_VL > '" + mst000401_LogicBean.removeComma(Keepb.getBaitankaVl()) + "' ");
//			lst.add( " AND ");
//			lst.add( " CHECK_ST_DT <= '" + Keepb.getYukoDt() + "' ");
//			lst.add( " AND ");
//			lst.add( " CHECK_ED_DT >= '" + Keepb.getYukoDt() + "' ");
//			lst.add( " AND ");
//			lst.add( " DELETE_FG = '0' ");
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_KAKAKUCHECK","MIN_KAKAKU_VL", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			if(mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				//存在(有)エラー
//				mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
//				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//					Keepb.setFirstFocus( CtlName );
//					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					Keepb.setErrorMessage("売価単価（税込）"+map.get("40202").toString());
//				}
//			}
//		}
// ↑↑仕様変更（2005.08.24）↑↑

// 2005.05.30 Inaba 発注商品コード、システム区分追加 START
		 //〔発注商品コード区分〕
		 Keepb.setHacyuSyohinKb(mst006001_HacyuSyohinKbDictionary.JAN_CD.getCode());
		 //〔システム区分〕
		   lst.clear();
		   lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		   lst.add(" and ");
		   lst.add("code_cd = '" + Keepb.getHankuCd() + "' ");
// 2006.02.09 Y.Inaba Mod ↓
//		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
		   mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","zokusei_cd", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
		   Keepb.setSystemKb( mstchk.getCdName() );
// 2005.05.30 Inaba 発注商品コード、システム区分追加 END
		//〔仕入先コード〕
		CtlName = "ct_siiresakicd";
		lst.clear();
		lst.add("KANRI_KB = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
		lst.add(" and ");
		lst.add("KANRI_CD = '" + Keepb.getHankuCd() + "' ");
		lst.add(" and ");
		lst.add( "SIIRESAKI_CD = '" + Keepb.getSiiresakiCd() + "' " );
// ↓↓仕様追加による変更（2005.06.01）
		lst.add(" and ");
		lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
// ↑↑仕様追加による変更（2005.06.01）
// 2006.02.09 Y.Inaba Mod ↓
//		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
		mstchk = mst000401_LogicBean.getMasterCdName(db,"R_SIIRESAKI","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
		Keepb.setSiiresakiNa( mstchk.getCdName() );
		if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
				//仕入先コード存在エラー
				mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
					Keepb.setFirstFocus(CtlName);
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定された仕入先コード"+map.get("40007").toString());
				}
			}
		}
		//〔店別仕入先管理コード〕
		CtlName = "ct_tensiiresakikanricd";
		if(!Keepb.getTenSiiresakiKanriCd().equals("")){
			lst.clear();
			lst.add( " kanri_kb = '" +  mst000901_KanriKbDictionary.HANKU.getCode() + "' "  );
			lst.add( " and " );
			lst.add( " kanri_cd = '" + Keepb.getHankuCd() + "' "  );
			lst.add( " and " );
			lst.add( " tenpo_cd = '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' " );
			lst.add( " and " );
// ↓↓仕様変更（2005.07.22）↓↓
//			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
// ↓↓仕様変更（2005.07.25）↓↓
//			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' " );
			lst.add( " ten_siiresaki_kanri_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' " );
// ↑↑仕様変更（2005.07.25）↑↑
// ↑↑仕様変更（2005.07.22）↑↑
// ↓↓仕様変更（2005.08.24）↓↓
			lst.add( " and " );
			lst.add( " tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' " );
// ↑↑仕様変更（2005.08.24）↑↑
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
// ↓↓仕様変更（2005.08.24）↓↓
//				if(!mst000401_LogicBean.getMasterChk(db,"r_tenbetu_siiresaki", lst  )){
				if(!mst000401_LogicBean.getMasterChk(db,"vw_r_tensiiresaki", lst  )){
// ↑↑仕様変更（2005.08.24）↑↑
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された店別仕入先管理コード"+map.get("40007").toString());
					}
				}
			}
			//名称取得
			lst.clear();
			lst.add("syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP, userLocal) + "' ");
			lst.add(" and ");
// ↓↓仕様変更（2005.07.13）↓↓
//			lst.add("code_cd = '" + Keepb.getTenSiiresakiKanriCd() + "' ");
			lst.add("code_cd = '" + Keepb.getHankuCd() + Keepb.getTenSiiresakiKanriCd() + "' ");
// ↑↑仕様変更（2005.07.13）↑↑
// 2006.02.09 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0, Keepb.getYukoDt() );
// 2006.02.09 Y.Inaba Mod ↑
			Keepb.setTenSiiresakiKanriNa( mstchk.getCdName() );
// ↓↓仕様変更（2005.07.26）↓↓
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//仕入先コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された店別仕入先コード"+map.get("40007").toString());
					}
				}
			}
// ↑↑仕様変更（2005.07.26）↑↑
		} else {
			Keepb.setTenSiiresakiKanriNa( "" );
		}
		//〔代表配送先コード〕
// ↓↓バグ修正(2005.10.18)
//		int cnt = 0; //不要ＳＱＬ文の削除用カウンター
		StringBuffer sb = new StringBuffer();
		ResultSet rest = null;
		if(!Keepb.getDaihyoHaisoCd().equals("")){
			lst.clear();
//			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
//			lst.add(" and ");
//			lst.add(" kanri_cd = '" + Keepb.getHankuCd() + "' ");
//			lst.add(" and ");
//			lst.add( "haisosaki_cd = '" + Keepb.getDaihyoHaisoCd()+"0" + "' " );
// ↓↓仕様追加による変更（2005.06.01）
//			cnt = lst.size(); //これ以降のＳＱＬ文は削除対象になる。
//			lst.add(" and ");
//			lst.add(" tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
// ↑↑仕様追加による変更（2005.06.01）
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_haisou","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
//			Keepb.setDaihyoHaisoNa( mstchk.getCdName() );
//			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
//			↓↓仕様追加による変更（2005.06.01）
//				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				for (int i = lst.size() - 1; i >= cnt; i--) {
					//余分なＳＱＬ文を削除
//					lst.remove(i);
//				}
//				if(!mst000401_LogicBean.getMasterChk(db,"r_tenbetu_haisosaki", lst  )){
// ↑↑仕様追加による変更（2005.06.01）
					//コード存在エラー
//					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
//					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
//						Keepb.setFirstFocus(CtlName);
//						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						Keepb.setErrorMessage("指定された代表配送先コード"+map.get("40007").toString());
//					}
//				}
//			}
			sb.append(" select ");
			sb.append("   rh.kanji_na ");
			sb.append(" from ");
			sb.append("   r_tenbetu_haisosaki rth inner join r_haisou rh ");
			sb.append("     on rth.kanri_kb = rh.kanri_kb and ");
			sb.append("     rth.kanri_cd = rh.kanri_cd and ");
			sb.append("     rth.haisosaki_cd = rh.haisosaki_cd ");
			sb.append(" where ");
			sb.append("   rth.kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
			sb.append("     and ");
			sb.append("   rth.kanri_cd = '" + Keepb.getHankuCd() + "' ");
			sb.append("     and ");
			sb.append("   rth.daihyo_haiso_cd = '" + Keepb.getDaihyoHaisoCd() + "' ");
			sb.append("     and ");
			sb.append("   rth.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			sb.append("     and ");
			sb.append("   rh.tosan_kb = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' ");
			sb.append("     and ");
			sb.append("   rh.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			sb.append("     and ");
			sb.append("   rownum=1 ");
			sb.append(" order by ");
			sb.append("   rth.haisosaki_cd ");
			rest = db.executeQuery(sb.toString());
			if (!rest.next()) {
				mst000401_LogicBean.setErrorBackColor(CtrlColor, CtlName);
				if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					Keepb.setFirstFocus( CtlName );
					Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					Keepb.setErrorMessage("指定された代表配送先"+map.get("40007").toString());
				}
			} else {
				Keepb.setDaihyoHaisoNa(mst000401_LogicBean.chkNullString(rest.getString("kanji_na")));
			}
// ↑↑バグ修正(2005.10.18)
		} else{
			Keepb.setDaihyoHaisoNa("");
		}
/*
		//〔店別配送先管理コード〕
		CtlName = "ct_tenhaisokanricd";
		if(!Keepb.getTenHaisoKanriCd().equals("")){
			lst.clear();
			lst.add(" kanri_kb = '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' ");
			lst.add(" and ");
			lst.add(" kanri_cd = '" + Keepb.getHankuCd() + "' ");
			lst.add(" and ");
			lst.add( "haisosaki_cd = '" + Keepb.getTenHaisoKanriCd()+"0" + "' " );
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_haisou","KANJI_RN", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					//コード存在エラー
					mst000401_LogicBean.setErrorBackColor(CtrlColor,CtlName);
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus(CtlName);
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("指定された店別配送先管理コード"+map.get("40007").toString());
					}
				}
			}
			//名称取得
			lst.clear();
			lst.add("syubetu_no_cd = '" + mst000101_ConstDictionary.SUPPLIER_MANAGEMENT_CODE_SHOP + "' ");
			lst.add(" and ");
			lst.add("code_cd = '" + Keepb.getTenHaisoKanriCd() + "' ");
			mstchk = mst000401_LogicBean.getMasterCdName(db,"r_namectf","kanji_rn", lst, mst000101_ConstDictionary.FUNCTION_PARAM_0 );
			Keepb.setTenHaisoKanriNa( mstchk.getCdName() );
		} else {
			Keepb.setTenHaisoKanriNa( "" );
		}
*/
		//〔納品POSコード〕
		CtlName = "ct_nohinsyohincd";
		if(!Keepb.getNohinSyohinCd().equals("") && !Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
			if(Keepb.getNohinSyohinCd().length() == 8 || Keepb.getNohinSyohinCd().length() == 13){
				if( getCheckDegit(Keepb.getNohinSyohinCd(), Keepb.getNohinSyohinCd().length()).equals("") ){
					mst000401_LogicBean.setErrorBackColor( CtrlColor, CtlName );
					if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						Keepb.setFirstFocus( CtlName );
						Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
						Keepb.setErrorMessage("納品ＰＯＳコード" + map.get("40219").toString());
					}
				}
			}
		}
// ↓↓バグ修正（2005.08.10）↓↓
		// 〔税区分〕
		CtlName = "ct_hinsyucd";
		if (!Keepb.getHinsyuCd().equals("")) {
			lst.clear();
			lst.add(" syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.TAX_FREE,userLocal) + "' ");
			lst.add(" and ");
			lst.add(" code_cd = '" + Keepb.getHinsyuCd() + "' ");
			if(!Keepb.getCheckFlg().equals(mst000101_ConstDictionary.CHECK_OTHERS)){
				if(!mst000401_LogicBean.getMasterChk(db,"r_namectf", lst  )){
					Keepb.setZeiKb(mst007501_ZeiKbDictionary.PRETAX.getCode());
				} else {
					Keepb.setZeiKb(mst007501_ZeiKbDictionary.TAXFREE.getCode());
				}
			}
		}
// ↑↑バグ修正（2005.08.10）↑↑
	}
}