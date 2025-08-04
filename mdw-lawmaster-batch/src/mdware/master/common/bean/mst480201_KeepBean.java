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
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.Map;
import java.util.HashMap;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * 
 * @version 1.1(2005/05/13)新ER対応
 * 
 * 　　　　　追加…発行区分(hakou_kb)
 *                 発行場所コード(hakou_basyo_cd)
 * 
 *           修正…商品マスタ登録情報区分(syohin_toroku_kb) ⇒ 商品ﾏｽﾀ登録情報媒体区分(syohin_toroku_batai_kb)
 *                 初回導入提案区分(syohin_syokai_donyu_kb) ⇒ 初回導入提案媒体区分(syohin_syokai_donyu_batai_kb)
 *                 発注区分(hachu_kb)             ⇒ 発注媒体区分(hachu_batai_kb)
 *                 納品(ASN)区分(nohin_asn_kb)    ⇒ 納品(ASN)媒体区分(nohin_asn_batai_kb)
 *                 欠品区分(kepin_kb)             ⇒ 欠品媒体区分(kepin_batai_kb)
 *                 仕入計上区分(siire_keijo_kb)   ⇒ 仕入計上媒体区分(siire_keijo_batai_kb)
 *                 請求区分(seikyu_kb)            ⇒ 請求媒体区分(seikyu_batai_kb)
 *                 支払区分(siharai_kb)           ⇒ 支払媒体区分(siharai_batai_kb)
 *                 販売区分(hanbai_kb)            ⇒ 販売媒体区分(hanbai_batai_kb)
 *                 発注勧告区分(hachu_kankoku_kb) ⇒ 発注勧告媒体区分(hachu_kankoku_batai_kb)
 *                 在庫区分(zaiko_kb)             ⇒ 在庫媒体区分(zaiko_batai_kb)
 *                 タグ区分(tag_kb)               ⇒ タグ媒体区分(tag_batai_kb)								
 */
public class mst480201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//	 ↓↓2006.06.20 maojm カスタマイズ修正↓↓		
//	public static final int KANRI_KB_MAX_LENGTH 				 = 1;//管理区分の長さ
//	public static final int KANRI_CD_MAX_LENGTH 				 = 4;//管理コードの
    public static final int BUMON_CD_MAX_LENGTH         = 4;// 
    public static final int BUMON_KANJI_RN_MAX_LENGTH         = 20;//
//	public static final int KANRI_KANJI_RN_MAX_LENGTH 		 = 20;//管理名の長さ
	public static final int SHIHAI_KB_MAX_LENGTH 			 = 1;//仕配区分の長さ
//	public static final int SHIHAI_CD_MAX_LENGTH 			 = 6;//仕配コードの長さ
	public static final int SHIHAI_CD_MAX_LENGTH 		 = 9;//仕配コードの長さ
	public static final int SHIHAI_KANJI_RN_MAX_LENGTH 		 = 20;//仕配名の長さ
	public static final int TENPO_CD_MAX_LENGTH 				 = 6;//店舗コードの長さ
	public static final int TENPO_KANJI_RN_MAX_LENGTH 		 = 20;//店舗名の長さ	
	public static final int YUKO_DT_MAX_LENGTH 				 = 8;//有効日の長さ	
//	public static final int COPY_KANRI_CD_MAX_LENGTH 		 = 4;//コピー販区コードの
//	public static final int COPY_KANRI_KANJI_RN_MAX_LENGTH 	 = 20;//コピー販区名の長さ
	public static final int COPY_BUMON_CD_MAX_LENGTH 		 = 4;//コピー部門コードの長さ
	public static final int COPY_BUMON_KANJI_RN_MAX_LENGTH 		 = 20;//コピー部門名の長さ
//	↓↓仕様変更（2005.09.29）↓↓
//	public static final int WEB_HAISINSAKI_CD_MAX_LENGTH 	 = 11;//WEB配信先コードの長さ
//	public static final int WEB_HAISINSAKI_CD1_MAX_LENGTH 	 = 9;//WEB配信先コードの長さ
//	public static final int WEB_HAISINSAKI_CD_MAX_LENGTH 	 = 10;//WEB配信先コードの長さ
//.	public static final int WEB_HAISINSAKI_CD1_MAX_LENGTH 	 = 8;//WEB配信先コードの長さ
	public static final int WEB_HAISINSAKI_CD_MAX_LENGTH 	 = 11;//WEB配信先コードの長さ
	public static final int WEB_HAISINSAKI_CD1_MAX_LENGTH 	 = 9;//WEB配信先コードの長さ
//	↑↑仕様変更（2005.09.29）↑↑
	public static final int WEB_HAISINSAKI_CD2_MAX_LENGTH 	 = 2;//WEB配信先コードの長さ
	public static final int JCA_HAISINSAKI_CD_MAX_LENGTH 	 = 8;//JCA配信先コードの長さ
	public static final int JCA_HAISINSAKI_CD1_MAX_LENGTH 	 = 6;//JCA配信先コードの長さ
	public static final int JCA_HAISINSAKI_CD2_MAX_LENGTH 	 = 2;//JCA配信先コードの長さ

	//新ER対応 *** 項目追加 start ***
//	public static final int HAKOU_KB_MAX_LENGTH               = 2; //発行区分の長さ
//	public static final int HAKOU_BASYO_CD_MAX_LENGTH         = 4; //発行場所コードの長さ
//新ER対応 *** 項目追加 end ***
//	 ↑↑2006.06.20 maojm カスタマイズ修正↑↑		
	//新ER対応 *** 項目名変更 start ***
	public static final int SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH 	   = 1; //商品マスタ登録情報媒体区分の長さ
	public static final int SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH = 1; //初回導入提案媒体区分の長さ
	public static final int HACHU_BATAI_KB_MAX_LENGTH 			   = 1; //発注媒体区分の長さ
	public static final int NOHIN_ASN_BATAI_KB_MAX_LENGTH 		   = 1; //納品(ASN)媒体区分の長さ
	public static final int KEPIN_BATAI_KB_MAX_LENGTH 			   = 1; //欠品媒体区分の長さ
	public static final int SIIRE_KEIJO_BATAI_KB_MAX_LENGTH 		   = 1; //仕入計上媒体区分の長さ
	public static final int SEIKYU_BATAI_KB_MAX_LENGTH 			   = 1; //請求媒体区分の長さ
	public static final int SIHARAI_BATAI_KB_MAX_LENGTH 			   = 1; //支払媒体区分の長さ
	public static final int HANBAI_BATAI_KB_MAX_LENGTH 			   = 1; //販売媒体区分の長さ
	public static final int HACHU_KANKOKU_BATAI_KB_MAX_LENGTH 	   = 1; //発注勧告媒体区分の長さ
	public static final int ZAIKO_BATAI_KB_MAX_LENGTH 			   = 1; //在庫媒体区分の長さ
	public static final int TAG_BATAI_KB_MAX_LENGTH 				   = 1; //タグ媒体区分の長さ
	//新ER対応 *** 項目名変更 end ***
	
/*	
	public static final int SYOHIN_TOROKU_KB_MAX_LENGTH 		 = 1;//商品マスタ登録情報区分の長さ
	public static final int SYOHIN_SYOKAI_DONYU_KB_MAX_LENGTH = 1;//初回導入提案区分の長さ
	public static final int HACHU_KB_MAX_LENGTH 				 = 1;//発注区分の長さ
	public static final int NOHIN_ASN_KB_MAX_LENGTH 			 = 1;//納品(ASN)区分の長さ
	public static final int KEPIN_KB_MAX_LENGTH 				 = 1;//欠品区分の長さ
	public static final int SIIRE_KEIJO_KB_MAX_LENGTH 		 = 1;//仕入計上区分の長さ
	public static final int SEIKYU_KB_MAX_LENGTH 			 = 1;//請求区分の長さ
	public static final int SIHARAI_KB_MAX_LENGTH 			 = 1;//支払区分の長さ
	public static final int HANBAI_KB_MAX_LENGTH 			 = 1;//販売区分の長さ
	public static final int HACHU_KANKOKU_KB_MAX_LENGTH 		 = 1;//発注勧告区分の長さ
	public static final int ZAIKO_KB_MAX_LENGTH 				 = 1;//在庫区分の長さ
	public static final int TAG_KB_MAX_LENGTH 				 = 1;//タグ区分の長さ
*/	
	
	public static final int SYUKANHACHUYOTEI_KB_MAX_LENGTH 	 = 1;//週間発注予定区分の長さ
	public static final int TENMEISAI_KB_MAX_LENGTH 			 = 1;//店明細区分の長さ
	public static final int INSERT_TS_MAX_LENGTH 			 = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 		 = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_NM_MAX_LENGTH 		 = 10;//作成者社員名の長さ
	public static final int UPDATE_TS_MAX_LENGTH 			 = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 		 = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_NM_MAX_LENGTH 		 = 10;//更新者社員名の長さ
	public static final int DELETE_FG_MAX_LENGTH 			 = 1;//削除フラグの長さ
	
//	↓↓仕様変更（2005.07.28）↓↓
	public static final int SYOKAI_TOROKU_TS_MAX_LENGTH = 20;//初回登録日の長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 10;//初回登録者IDの長さ
//	↑↑仕様変更（2005.07.28）↑↑
	
	
	private String ProcessingDivision		= "0";	// 処理状況
	private String ErrorFlg				= "";	// エラーフラグ
	private String ErrorMessage			= "";	// エラーメッセージ
	private String Mode					= "";	// 処理モード
	private String[] MenuBar				= null;			// メニューバーアイテム
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private String FirstFocus				= "";		// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg				= "";		// 新規処理利用可能区分
	private String UpdateFlg				= "";		// 更新処理利用可能区分
	private String DeleteFlg				= "";		// 削除処理利用可能区分
	private String ReferenceFlg			= "";		// 照会処理利用可能区分
	private String CsvFlg					= "";		// CSV処理利用可能区分
	private String PrintFlg				= "";		// 印刷処理利用可能区分
	private String CheckFlg			    = "";		// チェック処理判断
	private String ExistFlg			    = "";		// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String DecisionFlg				= "";		// 確定ボタン押下フラグ（エラー判定時用）
	private String UpdateProcessFlg		= ""; 		// 更新処理内容
	private String ErrorBackDisp			= "";		// エラー時戻り画面
	private String Genyuko_dt				= "";		// 現在有効日
//	↓↓2006.06.20 maojm カスタマイズ修正↓↓	
//	private String kanri_kb 				= "";		//管理区分
//	private String kanri_cd 				= "";		//管理コード
	private String bumon_cd           = "";      // 部門コード
	private String bumon_kanji_rn          = "";    // 部門名
	private String back_kb                     = null;         //戻る区分
//	private String kanri_kanji_rn 			= "";		//管理名
	private String shihai_kb 				= "";		//仕配区分
	private String shihai_cd 				= "";		//仕配コード
	private String shihai_kanji_rn			= "";		//仕配名
	private String tenpo_cd 				= "";		//店舗コード
	private String tenpo_kanji_rn			= "";		//店舗名
	private String yuko_dt 				= "";		//有効日
//	private String copy_kanri_cd 			= "";		//コピー販区コード
//	private String copy_kanri_kanji_rn 	= "";		//コピー販区名
	private String copy_bumon_cd       = "" ;    // コピー部門コード
	private String copy_bumon_kanji_rn = "" ;   // コピー部門名			
	private String web_haisinsaki_cd 		= "";		//WEB配信先コード
	private String web_haisinsaki_cd1 		= "";		//WEB配信先コード
	private String web_haisinsaki_cd2 		= "";		//WEB配信先コード
	private String jca_haisinsaki_cd 		= "";		//JCA配信先コード
	private String jca_haisinsaki_cd1 		= "";		//JCA配信先コード
	private String jca_haisinsaki_cd2 		= "";		//JCA配信先コード
	
	//新ER対応 *** 項目追加 start ***
//	private String hakou_kb                = null;    //発行区分
//	private String hakou_basyo_cd          = null;    //発行場所コード
   //新ER対応 *** 項目追加 end ***
//	↑↑2006.06.20 maojm カスタマイズ修正↑↑		
	//新ER対応 *** 項目名変更 start ***
	private String syohin_toroku_batai_kb       = null;//商品ﾏｽﾀ登録情報媒体区分
	private String syohin_syokai_donyu_batai_kb = null;//初回導入提案媒体区分
	private String hachu_batai_kb               = null;//発注媒体区分
	private String nohin_asn_batai_kb           = null;//納品(ASN)媒体区分
	private String kepin_batai_kb               = null;//欠品媒体区分
	private String siire_keijo_batai_kb         = null;//仕入計上媒体区分
	private String seikyu_batai_kb              = null;//請求媒体区分
	private String siharai_batai_kb             = null;//支払媒体区分
	private String hanbai_batai_kb              = null;//販売媒体区分
	private String hachu_kankoku_batai_kb       = null;//発注勧告媒体区分
	private String zaiko_batai_kb               = null;//在庫媒体区分
	private String tag_batai_kb                 = null;//タグ媒体区分
	//新ER対応 *** 項目名変更 end ***
/*
	private String syohin_toroku_kb 		= "";		//商品マスタ登録情報区分
	private String syohin_syokai_donyu_kb  = "";		//初回導入提案区分
	private String hachu_kb 				= "";		//発注区分
	private String nohin_asn_kb 			= "";		//納品(ASN)区分
	private String kepin_kb 				= "";		//欠品区分
	private String siire_keijo_kb 			= "";		//仕入計上区分
	private String seikyu_kb 				= "";		//請求区分
	private String siharai_kb 				= "";		//支払区分
	private String hanbai_kb 				= "";		//販売区分
	private String hachu_kankoku_kb 		= "";		//発注勧告区分
	private String zaiko_kb 				= "";		//在庫区分
	private String tag_kb 					= "";		//タグ区分
*/
	private String syukanhachuyotei_kb 	= "";		//週間発注予定区分
	private String tenmeisai_kb 			= "";		//店明細区分
	private String insert_ts 				= "";		//作成年月日
	private String insert_user_id 			= "";		//作成者社員ID
	private String insert_user_nm 			= "";		//作成者社員名
	private String update_ts 				= "";		//更新年月日
	private String update_user_id 			= "";		//更新者社員ID
	private String update_user_nm 			= "";		//更新者社員名
	private String delete_fg 				= "";		//削除フラグ
	
//	↓↓仕様変更（2005.07.28）↓↓
	private String syokai_toroku_ts 	= "";	//初回登録日
	private String syokai_user_id 	= "";	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑

//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE 		= "mst480101_HakoKanriInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE 		= "mst480201_HakoKanriEdit";	// 修正JSPを取得
	private static final String VIEW_PAGE 		= "mst480301_HakoKanriView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE		= "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END
	

	// Processingdivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String ProcessingDivision)
	{
		this.ProcessingDivision = ProcessingDivision;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.ProcessingDivision;
	}

	// ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	// Modeに対するセッターとゲッターの集合
	public boolean setMode(String Mode)
	{
		this.Mode = Mode;
		return true;
	}
	public String getMode()
	{
		return this.Mode;
	}

	// MenuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] MenuBar)
	{
		this.MenuBar = MenuBar;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.MenuBar;
	}

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.FirstFocus;
	}
	// InsertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String InsertFlg)
	{
		this.InsertFlg = InsertFlg;
		return true;
	}
	public String getInsertFlg()
	{
		return this.InsertFlg;
	}
	// UpdateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String UpdateFlg)
	{
		this.UpdateFlg = UpdateFlg;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.UpdateFlg;
	}
	// DeleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String DeleteFlg)
	{
		this.DeleteFlg = DeleteFlg;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.DeleteFlg;
	}
	// ReferenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String ReferenceFlg)
	{
		this.ReferenceFlg = ReferenceFlg;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.ReferenceFlg;
	}
	// CsvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String CsvFlg)
	{
		this.CsvFlg = CsvFlg;
		return true;
	}
	public String getCsvFlg()
	{
		return this.CsvFlg;
	}
	// PrintFlgに対するセッターとゲッターの集合
	public boolean setPrintFlg(String PrintFlg)
	{
		this.PrintFlg = PrintFlg;
		return true;
	}
	public String getPrintFlg()
	{
		return this.PrintFlg;
	}
	// CheckFlgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String CheckFlg)
	{
		this.CheckFlg = CheckFlg;
		return true;
	}
	public String getCheckFlg()
	{
		return this.CheckFlg;
	}
	//ExistFlgに対するセッターとゲッターの集合
	public boolean setExistFlg(String ExistFlg)
	{
		this.ExistFlg = ExistFlg;
		return true;
	}
	public String getExistFlg()
	{
		return this.ExistFlg;
	}
	//DecisionFlgに対するセッターとゲッターの集合
	public boolean setDecisionFlg(String DecisionFlg)
	{
		this.DecisionFlg = DecisionFlg;
		return true;
	}
	public String getDecisionFlg()
	{
		return this.DecisionFlg;
	}
	
	//UpdateProcessFlgに対するセッターとゲッターの集合
	public boolean setUpdateProcessFlg(String UpdateProcessFlg)
	{
		this.UpdateProcessFlg = UpdateProcessFlg;
		return true;
	}
	public String getUpdateProcessFlg()
	{
		return this.UpdateProcessFlg;
	}
	
	//ErrorBackDispに対するセッターとゲッターの集合
	public boolean setErrorBackDisp(String ErrorBackDisp)
	{
		this.ErrorBackDisp = ErrorBackDisp;
		return true;
	}
	public String getErrorBackDisp()
	{
		return this.ErrorBackDisp;
	}	
	
	// Genyuko_dtに対するセッターとゲッターの集合
	public boolean setGenyukoDt(String Genyuko_dt)
	{
		this.Genyuko_dt = Genyuko_dt;
		return true;
	}
	public String getGenyukoDt()
	{
		return this.Genyuko_dt;
	}	
//	   ↓↓2006.06.20 maojm カスタマイズ修正↓↓	
	// kanri_kbに対するセッターとゲッターの集合	
//	public boolean setKanriKb(String kanri_kb)
//	{
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
//	public String getKanriKb()
//	{
//		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
//	}
//	public String getKanriKbString()
//	{
//		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
//	}
//	public String getKanriKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
//	}
//
//
//	// kanri_cdに対するセッターとゲッターの集合
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
//	}
//	public String getKanriCdString()
//	{
//		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
//	}
//	
//	// kanri_kanji_rnに対するセッターとゲッターの集合
//	public boolean setKanriKanjiRn(String kanri_kanji_rn)
//	{
//		this.kanri_kanji_rn = kanri_kanji_rn;
//		return true;
//	}
//	public String getKanriKanjiRn()
//	{
////BUGNO-006 2005.04.20 T.kikuchi START		
//		//return cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH);
//		return this.kanri_kanji_rn;
////BUGNO-006 2005.04.20 T.kikuchi END		
//	}
//	public String getKanriKanjiRnString()
//	{
//		return "'" + cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getKanriKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH));
//	}

//	 bumoncdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}
//	 bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return this.bumon_kanji_rn;
	}
	public String getBumonKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_kanji_rn,BUMON_KANJI_RN_MAX_LENGTH));
	}
		
//	   ↑↑2006.06.20 maojm カスタマイズ修正↑↑	
	// shihai_kbに対するセッターとゲッターの集合
	public boolean setShihaiKb(String shihai_kb)
	{
		this.shihai_kb = shihai_kb;
		return true;
	}
	public String getShihaiKb()
	{
		return cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH);
	}
	public String getShihaiKbString()
	{
		return "'" + cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH) + "'";
	}
	public String getShihaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH));
	}


	// shihai_cdに対するセッターとゲッターの集合
	public boolean setShihaiCd(String shihai_cd)
	{
		this.shihai_cd = shihai_cd;
		return true;
	}
	public String getShihaiCd()
	{
		return cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH);
	}
	public String getShihaiCdString()
	{
		return "'" + cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH) + "'";
	}
	public String getShihaiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH));
	}

	// shihai_kanji_rnに対するセッターとゲッターの集合
	public boolean setShihaiKanjiRn(String shihai_kanji_rn)
	{
		this.shihai_kanji_rn = shihai_kanji_rn;
		return true;
	}
	public String getShihaiKanjiRn()
	{
//BUGNO-006 2005.04.20 T.kikuchi START
		//return cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH);
		return this.shihai_kanji_rn;
//BUGNO-006 2005.04.20 T.kikuchi END		
	}
	public String getShihaiKanjiRnString()
	{
		return "'" + cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getShihaiKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH));
	}

	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH));
	}
	
	// tenpo_kanji_rnに対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
//BUGNO-006 2005.04.20 T.kikuchi START		
		//return cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH);
		return this.tenpo_kanji_rn;
//BUGNO-006 2005.04.20 T.kikuchi END		
	}
	public String getTenpoKanjiRnString()
	{
		return "'" + cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getTenpoKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH));
	}

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}
		
//	↓↓2006.06.20 maojm カスタマイズ修正↓↓	
	// copy_kanri_cdに対するセッターとゲッターの集合
//	public boolean setCopyKanriCd(String copy_kanri_cd)
//	{
//		this.copy_kanri_cd = copy_kanri_cd;
//		return true;
//	}
//	public String getCopyKanriCd()
//	{
//		return cutString(this.copy_kanri_cd,COPY_KANRI_CD_MAX_LENGTH);
//	}
//	public String getCopyKanriCdString()
//	{
//		return "'" + cutString(this.copy_kanri_cd,COPY_KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getCopyKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.copy_kanri_cd,COPY_KANRI_CD_MAX_LENGTH));
//	}
//	
//	// copy_kanri_kanji_rnに対するセッターとゲッターの集合
//	public boolean setCopyKanriKanjiRn(String copy_kanri_kanji_rn)
//	{
//		this.copy_kanri_kanji_rn = copy_kanri_kanji_rn;
//		return true;
//	}
//	public String getCopyKanriKanjiRn()
//	{
////BUGNO-006 2005.04.20 T.kikuchi START		
//		//return cutString(this.copy_kanri_kanji_rn,COPY_KANRI_KANJI_RN_MAX_LENGTH);
//		return this.copy_kanri_kanji_rn;
////BUGNO-006 2005.04.20 T.kikuchi END		
//	}
//	public String getCopyKanriKanjiRnString()
//	{
//		return "'" + cutString(this.copy_kanri_kanji_rn,COPY_KANRI_KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getCopyKanriKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.copy_kanri_kanji_rn,COPY_KANRI_KANJI_RN_MAX_LENGTH));
//	}

//	 copy_bumon_cdに対するセッターとゲッターの集合
public boolean setCopyBumonCd(String copy_bumon_cd)
{
	this.copy_bumon_cd = copy_bumon_cd;
	return true;
}	
public String getCopyBumonCd()	
{
	return cutString(this.copy_bumon_cd,COPY_BUMON_CD_MAX_LENGTH);
}	
public String getCopyBumoniCdString()
{
	return "'" + cutString(this.copy_bumon_cd,COPY_BUMON_CD_MAX_LENGTH) + "'";
}
public String getCopyBumoniCdHTMLString()
{
	return HTMLStringUtil.convert(cutString(this.copy_bumon_cd,COPY_BUMON_CD_MAX_LENGTH));
}
// copy_bumon_kanji_rnに対応するセッターとゲッターの集合
public boolean setCopyBumonKanjiRn(String copy_bumon_kanji_rn)
{
	this.copy_bumon_kanji_rn = copy_bumon_kanji_rn;
	return true;
}
public String getCopyBumonKanjiRn()
{
	return this.copy_bumon_kanji_rn;
}
public String getCopyBumonKanjiRnString()
{
	return "'" + cutString(this.copy_bumon_kanji_rn,COPY_BUMON_KANJI_RN_MAX_LENGTH) + "'";
}
public String getCopyKanriKanjiRnHTMLString()
{
	return HTMLStringUtil.convert(cutString(this.copy_bumon_kanji_rn,COPY_BUMON_KANJI_RN_MAX_LENGTH));
}

//back_kbに対応するセッターとゲッターの集合
	public String  setBackKb(String back_kb)
	{
		return this.back_kb = back_kb;
	}

	public String getBackKb()
	{
	   return this.back_kb;
		
	}

//↑↑2006.06.20 maojm カスタマイズ修正↑↑	
	// web_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setWebHaisinsakiCd(String web_haisinsaki_cd)
	{
		this.web_haisinsaki_cd = web_haisinsaki_cd;
		return true;
	}
	public String getWebHaisinsakiCd()
	{
		return cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getWebHaisinsakiCdString()
	{
		return "'" + cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getWebHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH));
	}

	// web_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setWebHaisinsakiCd1(String web_haisinsaki_cd1)
	{
		this.web_haisinsaki_cd1 = web_haisinsaki_cd1;
		return true;
	}
	public String getWebHaisinsakiCd1()
	{
		return cutString(this.web_haisinsaki_cd1,WEB_HAISINSAKI_CD1_MAX_LENGTH);
	}
	public String getWebHaisinsakiCd1String()
	{
		return "'" + cutString(this.web_haisinsaki_cd1,WEB_HAISINSAKI_CD1_MAX_LENGTH) + "'";
	}
	public String getWebHaisinsakiCd1HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.web_haisinsaki_cd1,WEB_HAISINSAKI_CD1_MAX_LENGTH));
	}
	// web_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setWebHaisinsakiCd2(String web_haisinsaki_cd2)
	{
		this.web_haisinsaki_cd2 = web_haisinsaki_cd2;
		return true;
	}
	public String getWebHaisinsakiCd2()
	{
		return cutString(this.web_haisinsaki_cd2,WEB_HAISINSAKI_CD2_MAX_LENGTH);
	}
	public String getWebHaisinsakiCd2String()
	{
		return "'" + cutString(this.web_haisinsaki_cd2,WEB_HAISINSAKI_CD2_MAX_LENGTH) + "'";
	}
	public String getWebHaisinsakiCd2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.web_haisinsaki_cd2,WEB_HAISINSAKI_CD2_MAX_LENGTH));
	}

	// jca_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setJcaHaisinsakiCd(String jca_haisinsaki_cd)
	{
		this.jca_haisinsaki_cd = jca_haisinsaki_cd;
		return true;
	}
	public String getJcaHaisinsakiCd()
	{
		return cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getJcaHaisinsakiCdString()
	{
		return "'" + cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getJcaHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH));
	}
	// jca_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setJcaHaisinsakiCd1(String jca_haisinsaki_cd1)
	{
		this.jca_haisinsaki_cd1 = jca_haisinsaki_cd1;
		return true;
	}
	public String getJcaHaisinsakiCd1()
	{
		return cutString(this.jca_haisinsaki_cd1,JCA_HAISINSAKI_CD1_MAX_LENGTH);
	}
	public String getJcaHaisinsakiCd1String()
	{
		return "'" + cutString(this.jca_haisinsaki_cd1,JCA_HAISINSAKI_CD1_MAX_LENGTH) + "'";
	}
	public String getJcaHaisinsakiCd1HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jca_haisinsaki_cd1,JCA_HAISINSAKI_CD1_MAX_LENGTH));
	}

	// jca_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setJcaHaisinsakiCd2(String jca_haisinsaki_cd2)
	{
		this.jca_haisinsaki_cd2 = jca_haisinsaki_cd2;
		return true;
	}
	public String getJcaHaisinsakiCd2()
	{
		return cutString(this.jca_haisinsaki_cd2,JCA_HAISINSAKI_CD2_MAX_LENGTH);
	}
	public String getJcaHaisinsakiCd2String()
	{
		return "'" + cutString(this.jca_haisinsaki_cd2,JCA_HAISINSAKI_CD2_MAX_LENGTH) + "'";
	}
	public String getJcaHaisinsakiCd2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jca_haisinsaki_cd2,JCA_HAISINSAKI_CD2_MAX_LENGTH));
	}
	
/****************************************************************************************************
 * 							(2005/05/12)新ER対応    追加項目	@author sawabe
 ****************************************************************************************************/	
//	　　↓↓2006.06.20 maojm カスタマイズ修正↓↓
	/**
	 * 発行区分
	 * hakou_kb に対するセッターとゲッターの集合
	 * @return
	 */
//	public boolean setHakouKb(String hakou_kb) {
//		this.hakou_kb = hakou_kb;
//		return true;
//	}
//	public String getHakouKb() {
//		return cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH);
//	}
//	public String getHakouKbString() {
//		return "'" + cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH) + "'";
//	}
//	public String getHakouKbHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH));
//	}

	/**
	 * 発行場所コード
	 * hakou_basyo_cd に対するセッターとゲッターの集合
	 * @return
	 */
//	public boolean setHakouBasyoCd(String hakou_basyo_cd) {
//		this.hakou_basyo_cd = hakou_basyo_cd;
//		return true;
//	}
//	public String getHakouBasyoCd() {
//		return cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH);
//	}
//	public String getHakouBasyoCdString() {
//		return "'" + cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH) + "'";
//	}
//	public String getHakouBasyoCdHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH));
//	}
//	　　↑↑2006.06.20 maojm カスタマイズ修正↑↑
/****************************************************************************************************
 * 							(2005/05/12)新ER対応    追加名修正	@author sawabe
 ****************************************************************************************************/	
	/**
	 * 商品マスタ登録情報媒体区分
	 * syohin_toroku_batai_kbに対するセッターとゲッターの集合
	 */
	public boolean setSyohinTorokuBataiKb(String syohin_toroku_kb) {
		this.syohin_toroku_batai_kb = syohin_toroku_kb;
		return true;
	}
	public String getSyohinTorokuBataiKb()
	{
		return cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH);
	}
	public String getSyohinTorokuBataiKbString()
	{
		return "'" + cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinTorokuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH));
	}


	/**
	 * 初回導入提案媒体区分
	 * syohin_syokai_donyu_batai_kbに対するセッターとゲッターの集合
	 */
	public boolean setSyohinSyokaiDonyuBataiKb(String syohin_syokai_donyu_batai_kb) {
		this.syohin_syokai_donyu_batai_kb = syohin_syokai_donyu_batai_kb;
		return true;
	}
	public String getSyohinSyokaiDonyuBataiKb()
	{
		return cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH);
	}
	public String getSyohinSyokaiDonyuBataiKbString()
	{
		return "'" + cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinSyokaiDonyuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 発注媒体区分
	 * hachu_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHachuBataiKb(String hachu_batai_kb) {
		this.hachu_batai_kb = hachu_batai_kb;
		return true;
	}
	public String getHachuBataiKb()
	{
		return cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH);
	}
	public String getHachuBataiKbString()
	{
		return "'" + cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 納品(ASN)媒体区分
	 * nohin_asn_kb に対するセッターとゲッターの集合
	 */
	public boolean setNohinAsnBataiKb(String nohin_asn_batai_kb) {
		this.nohin_asn_batai_kb = nohin_asn_batai_kb;
		return true;
	}
	public String getNohinAsnBataiKb() {
		return cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH);
	}
	public String getNohinAsnBataiKbString() {
		return "'" + cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getNohinAsnBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 欠品媒体区分
	 * kepin_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setKepinBataiKb(String kepin_batai_kb) {
		this.kepin_batai_kb = kepin_batai_kb;
		return true;
	}
	public String getKepinBataiKb() {
		return cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH);
	}
	public String getKepinBataiKbString() {
		return "'" + cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getKepinBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 仕入計上媒体区分
	 * siire_keijo_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSiireKeijoBataiKb(String siire_keijo_batai_kb) {
		this.siire_keijo_batai_kb = siire_keijo_batai_kb;
		return true;
	}
	public String getSiireKeijoBataiKb() {
		return cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH);
	}
	public String getSiireKeijoBataiKbString() {
		return "'" + cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSiireKeijoBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 請求媒体区分
	 * seikyu_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSeikyuBataiKb(String seikyu_batai_kb) {
		this.seikyu_batai_kb = seikyu_batai_kb;
		return true;
	}
	public String getSeikyuBataiKb() {
		return cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH);
	}
	public String getSeikyuBataiKbString()
	{
		return "'" + cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSeikyuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 支払媒体区分
	 * siharai_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSiharaiBataiKb(String siharai_batai_kb) {
		this.siharai_batai_kb = siharai_batai_kb;
		return true;
	}
	public String getSiharaiBataiKb() {
		return cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH);
	}
	public String getSiharaiBataiKbString() {
		return "'" + cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSiharaiBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 販売媒体区分
	 * hanbai_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHanbaiBataiKb(String hanbai_batai_kb) {
		this.hanbai_batai_kb = hanbai_batai_kb;
		return true;
	}
	public String getHanbaiBataiKb() {
		return cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH);
	}
	public String getHanbaiBataiKbString() {
		return "'" + cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 発注勧告媒体区分
	 * hachu_kankoku_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHachuKankokuBataiKb(String hachu_kankoku_batai_kb)
	{
		this.hachu_kankoku_batai_kb = hachu_kankoku_batai_kb;
		return true;
	}
	public String getHachuKankokuBataiKb()
	{
		return cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH);
	}
	public String getHachuKankokuBataiKbString()
	{
		return "'" + cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKankokuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 在庫媒体区分
	 * zaiko_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setZaikoBataiKb(String zaiko_batai_kb) {
		this.zaiko_batai_kb = zaiko_batai_kb;
		return true;
	}
	public String getZaikoBataiKb() {
		return cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH);
	}
	public String getZaikoBataiKbString()
	{
		return "'" + cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getZaikoBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * タグ媒体区分
	 * tag_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setTagBataiKb(String tag_kb)
	{
		this.tag_batai_kb = tag_kb;
		return true;
	}
	public String getTagBataiKb()
	{
		return cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH);
	}
	public String getTagBataiKbString()
	{
		return "'" + cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getTagBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH));
	}
/******************************* (2005/05/12) 新ER対応 end *************************************/

/*

	// syohin_toroku_kbに対するセッターとゲッターの集合
	public boolean setSyohinTorokuKb(String syohin_toroku_kb)
	{
		this.syohin_toroku_kb = syohin_toroku_kb;
		return true;
	}
	public String getSyohinTorokuKb()
	{
		return cutString(this.syohin_toroku_kb,SYOHIN_TOROKU_KB_MAX_LENGTH);
	}
	public String getSyohinTorokuKbString()
	{
		return "'" + cutString(this.syohin_toroku_kb,SYOHIN_TOROKU_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinTorokuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_toroku_kb,SYOHIN_TOROKU_KB_MAX_LENGTH));
	}


	// syohin_syokai_donyu_kbに対するセッターとゲッターの集合
	public boolean setSyohinSyokaiDonyuKb(String syohin_syokai_donyu_kb)
	{
		this.syohin_syokai_donyu_kb = syohin_syokai_donyu_kb;
		return true;
	}
	public String getSyohinSyokaiDonyuKb()
	{
		return cutString(this.syohin_syokai_donyu_kb,SYOHIN_SYOKAI_DONYU_KB_MAX_LENGTH);
	}
	public String getSyohinSyokaiDonyuKbString()
	{
		return "'" + cutString(this.syohin_syokai_donyu_kb,SYOHIN_SYOKAI_DONYU_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinSyokaiDonyuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_syokai_donyu_kb,SYOHIN_SYOKAI_DONYU_KB_MAX_LENGTH));
	}


	// hachu_kbに対するセッターとゲッターの集合
	public boolean setHachuKb(String hachu_kb)
	{
		this.hachu_kb = hachu_kb;
		return true;
	}
	public String getHachuKb()
	{
		return cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH);
	}
	public String getHachuKbString()
	{
		return "'" + cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH));
	}


	// nohin_asn_kbに対するセッターとゲッターの集合
	public boolean setNohinAsnKb(String nohin_asn_kb)
	{
		this.nohin_asn_kb = nohin_asn_kb;
		return true;
	}
	public String getNohinAsnKb()
	{
		return cutString(this.nohin_asn_kb,NOHIN_ASN_KB_MAX_LENGTH);
	}
	public String getNohinAsnKbString()
	{
		return "'" + cutString(this.nohin_asn_kb,NOHIN_ASN_KB_MAX_LENGTH) + "'";
	}
	public String getNohinAsnKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_asn_kb,NOHIN_ASN_KB_MAX_LENGTH));
	}


	// kepin_kbに対するセッターとゲッターの集合
	public boolean setKepinKb(String kepin_kb)
	{
		this.kepin_kb = kepin_kb;
		return true;
	}
	public String getKepinKb()
	{
		return cutString(this.kepin_kb,KEPIN_KB_MAX_LENGTH);
	}
	public String getKepinKbString()
	{
		return "'" + cutString(this.kepin_kb,KEPIN_KB_MAX_LENGTH) + "'";
	}
	public String getKepinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kepin_kb,KEPIN_KB_MAX_LENGTH));
	}


	// siire_keijo_kbに対するセッターとゲッターの集合
	public boolean setSiireKeijoKb(String siire_keijo_kb)
	{
		this.siire_keijo_kb = siire_keijo_kb;
		return true;
	}
	public String getSiireKeijoKb()
	{
		return cutString(this.siire_keijo_kb,SIIRE_KEIJO_KB_MAX_LENGTH);
	}
	public String getSiireKeijoKbString()
	{
		return "'" + cutString(this.siire_keijo_kb,SIIRE_KEIJO_KB_MAX_LENGTH) + "'";
	}
	public String getSiireKeijoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siire_keijo_kb,SIIRE_KEIJO_KB_MAX_LENGTH));
	}


	// seikyu_kbに対するセッターとゲッターの集合
	public boolean setSeikyuKb(String seikyu_kb)
	{
		this.seikyu_kb = seikyu_kb;
		return true;
	}
	public String getSeikyuKb()
	{
		return cutString(this.seikyu_kb,SEIKYU_KB_MAX_LENGTH);
	}
	public String getSeikyuKbString()
	{
		return "'" + cutString(this.seikyu_kb,SEIKYU_KB_MAX_LENGTH) + "'";
	}
	public String getSeikyuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.seikyu_kb,SEIKYU_KB_MAX_LENGTH));
	}


	// siharai_kbに対するセッターとゲッターの集合
	public boolean setSiharaiKb(String siharai_kb)
	{
		this.siharai_kb = siharai_kb;
		return true;
	}
	public String getSiharaiKb()
	{
		return cutString(this.siharai_kb,SIHARAI_KB_MAX_LENGTH);
	}
	public String getSiharaiKbString()
	{
		return "'" + cutString(this.siharai_kb,SIHARAI_KB_MAX_LENGTH) + "'";
	}
	public String getSiharaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siharai_kb,SIHARAI_KB_MAX_LENGTH));
	}


	// hanbai_kbに対するセッターとゲッターの集合
	public boolean setHanbaiKb(String hanbai_kb)
	{
		this.hanbai_kb = hanbai_kb;
		return true;
	}
	public String getHanbaiKb()
	{
		return cutString(this.hanbai_kb,HANBAI_KB_MAX_LENGTH);
	}
	public String getHanbaiKbString()
	{
		return "'" + cutString(this.hanbai_kb,HANBAI_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_kb,HANBAI_KB_MAX_LENGTH));
	}


	// hachu_kankoku_kbに対するセッターとゲッターの集合
	public boolean setHachuKankokuKb(String hachu_kankoku_kb)
	{
		this.hachu_kankoku_kb = hachu_kankoku_kb;
		return true;
	}
	public String getHachuKankokuKb()
	{
		return cutString(this.hachu_kankoku_kb,HACHU_KANKOKU_KB_MAX_LENGTH);
	}
	public String getHachuKankokuKbString()
	{
		return "'" + cutString(this.hachu_kankoku_kb,HACHU_KANKOKU_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKankokuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kankoku_kb,HACHU_KANKOKU_KB_MAX_LENGTH));
	}


	// zaiko_kbに対するセッターとゲッターの集合
	public boolean setZaikoKb(String zaiko_kb)
	{
		this.zaiko_kb = zaiko_kb;
		return true;
	}
	public String getZaikoKb()
	{
		return cutString(this.zaiko_kb,ZAIKO_KB_MAX_LENGTH);
	}
	public String getZaikoKbString()
	{
		return "'" + cutString(this.zaiko_kb,ZAIKO_KB_MAX_LENGTH) + "'";
	}
	public String getZaikoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_kb,ZAIKO_KB_MAX_LENGTH));
	}


	// tag_kbに対するセッターとゲッターの集合
	public boolean setTagKb(String tag_kb)
	{
		this.tag_kb = tag_kb;
		return true;
	}
	public String getTagKb()
	{
		return cutString(this.tag_kb,TAG_KB_MAX_LENGTH);
	}
	public String getTagKbString()
	{
		return "'" + cutString(this.tag_kb,TAG_KB_MAX_LENGTH) + "'";
	}
	public String getTagKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_kb,TAG_KB_MAX_LENGTH));
	}
*/

	// syukanhachuyotei_kbに対するセッターとゲッターの集合
	public boolean setSyukanhachuyoteiKb(String syukanhachuyotei_kb)
	{
		this.syukanhachuyotei_kb = syukanhachuyotei_kb;
		return true;
	}
	public String getSyukanhachuyoteiKb()
	{
		return cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH);
	}
	public String getSyukanhachuyoteiKbString()
	{
		return "'" + cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH) + "'";
	}
	public String getSyukanhachuyoteiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH));
	}


	// tenmeisai_kbに対するセッターとゲッターの集合
	public boolean setTenmeisaiKb(String tenmeisai_kb)
	{
		this.tenmeisai_kb = tenmeisai_kb;
		return true;
	}
	public String getTenmeisaiKb()
	{
		return cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH);
	}
	public String getTenmeisaiKbString()
	{
		return "'" + cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH) + "'";
	}
	public String getTenmeisaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH));
	}


	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}


	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}

	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH);
	}
	public String getInsertUserNmString()
	{
		return "'" + cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH) + "'";
	}
	public String getInsertUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH));
	}


	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}


	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}

	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;	
	}
	public String getUpdateUserNm()
	{
		return cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH);
	}
	public String getUpdateUserNmString()
	{
		return "'" + cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH));
	}

	// delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
		return true;
	}
	public String getDeleteFg()
	{
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}
	
//	↓↓仕様変更（2005.07.28）↓↓
	// syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setSyokaiTorokuTs(String syokai_toroku_ts)
	{
		this.syokai_toroku_ts = syokai_toroku_ts;
		return true;
	}
	public String getSyokaiTorokuTs()
	{
		return cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH);
	}
	public String getSyokaiTorokuTsString()
	{
		return "'" + cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH) + "'";
	}
	public String getSyokaiTorokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH));
	}


	// syokai_user_idに対するセッターとゲッターの集合
	public boolean setSyokaiUserId(String syokai_user_id)
	{
		this.syokai_user_id = syokai_user_id;
		return true;
	}
	public String getSyokaiUserId()
	{
		return cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getSyokaiUserIdString()
	{
		return "'" + cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.28）↑↑
	
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}

//BUGNO-S052 2005.05.14 SIRIUS START
  /**
   * 初期画面URL取得(ログ出力有り)
   * <br>
   * Ex)<br>
   * getInitUrl("logHeader","logMsg") -&gt; String<br>
   * <br>
   * @param String logHeader
   * @param String logMsg
   * @return		String
   */
	public String getInitUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.ErrorFlg.equals("")){
			//通常系
			if(!this.ErrorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.ErrorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.ErrorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	INIT_PAGE;
	}
	/**
	 * 初期画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getInitUrl()
	{
		return	INIT_PAGE;
	}

	/**
	 * 登録画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getEditUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.ErrorFlg.equals("")){
			//通常系
			if(!this.ErrorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.ErrorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.ErrorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	EDIT_PAGE;
	}
	/**
	 * 登録画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getEditUrl()
	{
		return	EDIT_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getViewUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.ErrorFlg.equals("")){
			//通常系
			if(!this.ErrorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.ErrorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.ErrorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	VIEW_PAGE;
	}
	/**
	 * 照会画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getViewUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getViewUrl()
	{
		return	VIEW_PAGE;
	}

	/**
	 * 権限エラー画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getKengenErr("logHeader") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @return		String
	 */
	public String getKengenErr(String logHeader)
	{
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}
//BUGNO-S052 2005.05.14 SIRIUS START
}
