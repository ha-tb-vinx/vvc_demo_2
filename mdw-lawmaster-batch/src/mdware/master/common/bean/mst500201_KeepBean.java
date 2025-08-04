/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス 発注納品基準日マスタ用の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する発注納品基準日マスタ用の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Yamada
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス 発注納品基準日マスタ用の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する発注納品基準日マスタ用の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius M.Yamada
 * @version 1.0
 * @see なし								
 */
public class mst500201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分 (FK)の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コード (FK)の長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コード (FK)の長さ
	public static final int HACHU_HOHO_KB_MAX_LENGTH = 1;//発注方法区分の長さ
	public static final int HACHU_SIME_TIME_KB_MAX_LENGTH = 1;//発注〆時間区分の長さ
	public static final int HACHU_MON_FG_MAX_LENGTH = 1;//発注可否フラグ-月の長さ
	public static final int HACHU_TUE_FG_MAX_LENGTH = 1;//発注可否フラグ-火の長さ
	public static final int HACHU_WED_FG_MAX_LENGTH = 1;//発注可否フラグ-水の長さ
	public static final int HACHU_THU_FG_MAX_LENGTH = 1;//発注可否フラグ-木の長さ
	public static final int HACHU_FRI_FG_MAX_LENGTH = 1;//発注可否フラグ-金の長さ
	public static final int HACHU_SAT_FG_MAX_LENGTH = 1;//発注可否フラグ-土の長さ
	public static final int HACHU_SUN_FG_MAX_LENGTH = 1;//発注可否フラグ-日の長さ
	public static final int RTIME_MON_KB_MAX_LENGTH = 1;//リードタイム-月の長さ
	public static final int RTIME_TUE_KB_MAX_LENGTH = 1;//リードタイム-火の長さ
	public static final int RTIME_WED_KB_MAX_LENGTH = 1;//リードタイム-水の長さ
	public static final int RTIME_THU_KB_MAX_LENGTH = 1;//リードタイム-木の長さ
	public static final int RTIME_FRI_KB_MAX_LENGTH = 1;//リードタイム-金の長さ
	public static final int RTIME_SAT_KB_MAX_LENGTH = 1;//リードタイム-土の長さ
	public static final int RTIME_SUN_KB_MAX_LENGTH = 1;//リードタイム-日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
//	↓↓仕様変更（2005.07.28）↓↓
	public static final int SYOKAI_TOROKU_TS_MAX_LENGTH = 20;//初回登録日の長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 10;//初回登録者IDの長さ
//	↑↑仕様変更（2005.07.28）↑↑


	// @added by M.Yamada 2005.03.09
	public static final int KANRI_NM_MAX_LENGTH = 20;//管理コード(FK)対応名の長さ
	public static final int SIIRESAKI_NM_MAX_LENGTH = 6;//仕入先コード(FK)対応名の長さ


	private String ProcessingDivision	= "0";				// 処理区分
	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private String Mode				= "";				// 処理モード
	private String[] MenuBar			= null;			// メニューバーアイテム
	private Map CtrlColor				= new HashMap();	// コントロール前景色
	private String FirstFocus			= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg			= "";				// 新規処理利用可能区分
	private String UpdateFlg			= "";				// 更新処理利用可能区分
	private String DeleteFlg			= "";				// 削除処理利用可能区分
	private String ReferenceFlg		= "";				// 照会処理利用可能区分
	private String CsvFlg				= "";				// CSV処理利用可能区分
	private String PrintFlg			= "";				// 印刷処理利用可能区分
	private String CheckFlg			= "";				// チェック処理判断
	private String ExistFlg			= "";				// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String DecisionFlg			= "";				// 確定ボタン押下フラグ（エラー判定時用）
	private String UpdateProcessFlg	= ""; 				// 更新処理内容
	private String ErrorBackDisp		= "";				// エラー時戻り画面	
	
	private String Genyuko_dt			= "";				// 現在有効日

	private String yuko_dt 			=  "";				//有効日
	private String kanri_kb 			=  "";				//管理区分 (FK)
	private String kanri_cd 			=  "";				//管理コード (FK)
	private String siiresaki_cd 		=  "";				//仕入先コード (FK)
	private String hachu_hoho_kb 		=  "";				//発注方法区分
	private String hachu_sime_time_kb 	=  "";				//発注〆時間区分
	private String kanri_nm     		=  ""; 				//管理コード(FK)対応名
	private String siiresaki_nm 		=  ""; 				//仕入先コード(FK)対応名

	private String hachu_mon_fg 		=  "";				//発注可否フラグ-月
	private String hachu_tue_fg 		=  "";				//発注可否フラグ-火
	private String hachu_wed_fg 		=  "";				//発注可否フラグ-水
	private String hachu_thu_fg 		=  "";				//発注可否フラグ-木
	private String hachu_fri_fg 		=  "";				//発注可否フラグ-金
	private String hachu_sat_fg 		=  "";				//発注可否フラグ-土
	private String hachu_sun_fg 		=  "";				//発注可否フラグ-日
	private String rtime_mon_kb 		=  "";				//リードタイム-月
	private String rtime_tue_kb 		=  "";				//リードタイム-火
	private String rtime_wed_kb 		=  "";				//リードタイム-水
	private String rtime_thu_kb 		=  "";				//リードタイム-木
	private String rtime_fri_kb 		=  "";				//リードタイム-金
	private String rtime_sat_kb 		=  "";				//リードタイム-土
	private String rtime_sun_kb 		=  "";				//リードタイム-日
	private String first_insert_ts		= "";				//作成年月日
	private String first_insert_user_id	= "";				//作成者社員ID
	private String first_update_ts 		= "";				//更新年月日
	private String first_update_user_id	= "";				//更新者社員ID	
	private String delete_fg 			=  "";				//削除フラグ
//	↓↓仕様変更（2005.07.28）↓↓
	private String first_syokai_toroku_ts 	= "";	//初回登録日
	private String first_syokai_user_id 	= "";	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑

	// 発注方法区分:拡販増量の場合
	private String inc_hachu_mon_fg 	=  "";				//発注可否フラグ-月
	private String inc_hachu_tue_fg 	=  "";				//発注可否フラグ-火
	private String inc_hachu_wed_fg 	=  "";				//発注可否フラグ-水
	private String inc_hachu_thu_fg 	=  "";				//発注可否フラグ-木
	private String inc_hachu_fri_fg 	=  "";				//発注可否フラグ-金
	private String inc_hachu_sat_fg 	=  "";				//発注可否フラグ-土
	private String inc_hachu_sun_fg 	=  "";				//発注可否フラグ-日
	private String inc_rtime_mon_kb 	=  "";				//リードタイム-月
	private String inc_rtime_tue_kb 	=  "";				//リードタイム-火
	private String inc_rtime_wed_kb 	=  "";				//リードタイム-水
	private String inc_rtime_thu_kb 	=  "";				//リードタイム-木
	private String inc_rtime_fri_kb 	=  "";				//リードタイム-金
	private String inc_rtime_sat_kb 	=  "";				//リードタイム-土
	private String inc_rtime_sun_kb 	=  "";				//リードタイム-日
	private String inc_insert_ts 		=  "";				//作成年月日
	private String inc_insert_user_id 	=  "";				//作成者社員ID
	private String inc_update_ts 		=  "";				//更新年月日
	private String inc_update_user_id 	=  "";				//更新者社員ID	
	private String inc_delete_fg 		=  "";				//削除フラグ
//	↓↓仕様変更（2005.07.28）↓↓
	private String inc_syokai_toroku_ts 	= "";	//初回登録日
	private String inc_syokai_user_id 	= "";	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑
	
	private String insert_ts 			=  "";				//作成年月日(表示用）
	private String insert_user_id 		=  "";				//作成者社員ID(表示用）
	private String update_ts 			=  "";				//更新年月日(表示用）
	private String update_user_id 		=  "";				//更新者社員ID(表示用）
//	↓↓仕様変更（2005.07.28）↓↓
	private String syokai_toroku_ts 	= "";	//初回登録日
	private String syokai_user_id 	= "";	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst500101_HachuNohinKijunbiInit";// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst500201_HachuNohinKijunbiEdit";// 修正JSPを取得
	private static final String VIEW_PAGE = "mst500301_HachuNohinKijunbiView";// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

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


	// kanri_kbに対するセッターとゲッターの集合
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb()
	{
		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
	}
	public String getKanriKbString()
	{
		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getKanriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
	}


	// kanri_cdに対するセッターとゲッターの集合
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd()
	{
		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
	}
	public String getKanriCdString()
	{
		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
	}

	// kanri_nmに対するセッターとゲッターの集合	
	public boolean setKanriNm(String kanri_nm)
	{
		this.kanri_nm = kanri_nm;
		return true;
	}
	public String getKanriNm()
	{
//BUGNO-006 2005.04.20 T.kikuchi START		
		//return cutString(this.kanri_nm,KANRI_NM_MAX_LENGTH);
		return this.kanri_nm;
//BUGNO-006 2005.04.20 T.kikuchi END		
	}
	public String getKanriNmString()
	{
		return "'" + cutString(this.kanri_nm,KANRI_NM_MAX_LENGTH) + "'";
	}
	public String getKanriNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_nm,KANRI_NM_MAX_LENGTH));
	}


	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}

	// siiresaki_nmに対するセッターとゲッターの集合
	public boolean setSiiresakiNm(String siiresaki_nm)
	{
		this.siiresaki_nm = siiresaki_nm;
		return true;
	}
	public String getSiiresakiNm()
	{
//BUGNO-006 2005.04.20 T.kikuchi START		
		//return cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH);
		return this.siiresaki_nm;
//BUGNO-006 2005.04.20 T.kikuchi END		
	}
	public String getSiiresakiNmString()
	{
		return "'" + cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH));
	}


	// hachu_hoho_kbに対するセッターとゲッターの集合
	public boolean setHachuHohoKb(String hachu_hoho_kb)
	{
		this.hachu_hoho_kb = hachu_hoho_kb;
		return true;
	}
	public String getHachuHohoKb()
	{
		return cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH);
	}
	public String getHachuHohoKbString()
	{
		return "'" + cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH) + "'";
	}
	public String getHachuHohoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH));
	}


	// hachu_sime_time_kbに対するセッターとゲッターの集合
	public boolean setHachuSimeTimeKb(String hachu_sime_time_kb)
	{
		this.hachu_sime_time_kb = hachu_sime_time_kb;
		return true;
	}
	public String getHachuSimeTimeKb()
	{
		return cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH);
	}
	public String getHachuSimeTimeKbString()
	{
		return "'" + cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH) + "'";
	}
	public String getHachuSimeTimeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH));
	}

	// hachu_mon_fgに対するセッターとゲッターの集合
	public boolean setHachuMonFg(String hachu_mon_fg)
	{
		this.hachu_mon_fg = hachu_mon_fg;
		return true;
	}
	public String getHachuMonFg()
	{
		return cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH);
	}
	public String getHachuMonFgString()
	{
		return "'" + cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH) + "'";
	}
	public String getHachuMonFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH));
	}


	// hachu_tue_fgに対するセッターとゲッターの集合
	public boolean setHachuTueFg(String hachu_tue_fg)
	{
		this.hachu_tue_fg = hachu_tue_fg;
		return true;
	}
	public String getHachuTueFg()
	{
		return cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH);
	}
	public String getHachuTueFgString()
	{
		return "'" + cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH) + "'";
	}
	public String getHachuTueFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH));
	}


	// hachu_wed_fgに対するセッターとゲッターの集合
	public boolean setHachuWedFg(String hachu_wed_fg)
	{
		this.hachu_wed_fg = hachu_wed_fg;
		return true;
	}
	public String getHachuWedFg()
	{
		return cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH);
	}
	public String getHachuWedFgString()
	{
		return "'" + cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH) + "'";
	}
	public String getHachuWedFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH));
	}


	// hachu_thu_fgに対するセッターとゲッターの集合
	public boolean setHachuThuFg(String hachu_thu_fg)
	{
		this.hachu_thu_fg = hachu_thu_fg;
		return true;
	}
	public String getHachuThuFg()
	{
		return cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH);
	}
	public String getHachuThuFgString()
	{
		return "'" + cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH) + "'";
	}
	public String getHachuThuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH));
	}


	// hachu_fri_fgに対するセッターとゲッターの集合
	public boolean setHachuFriFg(String hachu_fri_fg)
	{
		this.hachu_fri_fg = hachu_fri_fg;
		return true;
	}
	public String getHachuFriFg()
	{
		return cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH);
	}
	public String getHachuFriFgString()
	{
		return "'" + cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH) + "'";
	}
	public String getHachuFriFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH));
	}


	// hachu_sat_fgに対するセッターとゲッターの集合
	public boolean setHachuSatFg(String hachu_sat_fg)
	{
		this.hachu_sat_fg = hachu_sat_fg;
		return true;
	}
	public String getHachuSatFg()
	{
		return cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH);
	}
	public String getHachuSatFgString()
	{
		return "'" + cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH) + "'";
	}
	public String getHachuSatFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH));
	}


	// hachu_sun_fgに対するセッターとゲッターの集合
	public boolean setHachuSunFg(String hachu_sun_fg)
	{
		this.hachu_sun_fg = hachu_sun_fg;
		return true;
	}
	public String getHachuSunFg()
	{
		return cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH);
	}
	public String getHachuSunFgString()
	{
		return "'" + cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH) + "'";
	}
	public String getHachuSunFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH));
	}


	// rtime_mon_kbに対するセッターとゲッターの集合
	public boolean setRtimeMonKb(String rtime_mon_kb)
	{
		this.rtime_mon_kb = rtime_mon_kb;
		return true;
	}
	public String getRtimeMonKb()
	{
		return cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH);
	}
	public String getRtimeMonKbString()
	{
		return "'" + cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeMonKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH));
	}


	// rtime_tue_kbに対するセッターとゲッターの集合
	public boolean setRtimeTueKb(String rtime_tue_kb)
	{
		this.rtime_tue_kb = rtime_tue_kb;
		return true;
	}
	public String getRtimeTueKb()
	{
		return cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH);
	}
	public String getRtimeTueKbString()
	{
		return "'" + cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeTueKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH));
	}


	// rtime_wed_kbに対するセッターとゲッターの集合
	public boolean setRtimeWedKb(String rtime_wed_kb)
	{
		this.rtime_wed_kb = rtime_wed_kb;
		return true;
	}
	public String getRtimeWedKb()
	{
		return cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH);
	}
	public String getRtimeWedKbString()
	{
		return "'" + cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeWedKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH));
	}


	// rtime_thu_kbに対するセッターとゲッターの集合
	public boolean setRtimeThuKb(String rtime_thu_kb)
	{
		this.rtime_thu_kb = rtime_thu_kb;
		return true;
	}
	public String getRtimeThuKb()
	{
		return cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH);
	}
	public String getRtimeThuKbString()
	{
		return "'" + cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeThuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH));
	}


	// rtime_fri_kbに対するセッターとゲッターの集合
	public boolean setRtimeFriKb(String rtime_fri_kb)
	{
		this.rtime_fri_kb = rtime_fri_kb;
		return true;
	}
	public String getRtimeFriKb()
	{
		return cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH);
	}
	public String getRtimeFriKbString()
	{
		return "'" + cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeFriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH));
	}


	// rtime_sat_kbに対するセッターとゲッターの集合
	public boolean setRtimeSatKb(String rtime_sat_kb)
	{
		this.rtime_sat_kb = rtime_sat_kb;
		return true;
	}
	public String getRtimeSatKb()
	{
		return cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH);
	}
	public String getRtimeSatKbString()
	{
		return "'" + cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeSatKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH));
	}


	// rtime_sun_kbに対するセッターとゲッターの集合
	public boolean setRtimeSunKb(String rtime_sun_kb)
	{
		this.rtime_sun_kb = rtime_sun_kb;
		return true;
	}
	public String getRtimeSunKb()
	{
		return cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH);
	}
	public String getRtimeSunKbString()
	{
		return "'" + cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeSunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH));
	}

	// inc_hachu_mon_fgに対するセッターとゲッターの集合
	public boolean setIncHachuMonFg(String inc_hachu_mon_fg)
	{
		this.inc_hachu_mon_fg = inc_hachu_mon_fg;
		return true;
	}
	public String getIncHachuMonFg()
	{
		return cutString(this.inc_hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH);
	}
	public String getIncHachuMonFgString()
	{
		return "'" + cutString(this.inc_hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuMonFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH));
	}

	// inc_hachu_tue_fgに対するセッターとゲッターの集合
	public boolean setIncHachuTueFg(String inc_hachu_tue_fg)
	{
		this.inc_hachu_tue_fg = inc_hachu_tue_fg;
		return true;
	}
	public String getIncHachuTueFg()
	{
		return cutString(this.inc_hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH);
	}
	public String getIncHachuTueFgString()
	{
		return "'" + cutString(this.inc_hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuTueFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH));
	}


	// inc_hachu_wed_fgに対するセッターとゲッターの集合
	public boolean setIncHachuWedFg(String inc_hachu_wed_fg)
	{
		this.inc_hachu_wed_fg = inc_hachu_wed_fg;
		return true;
	}
	public String getIncHachuWedFg()
	{
		return cutString(this.inc_hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH);
	}
	public String getIncHachuWedFgString()
	{
		return "'" + cutString(this.inc_hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuWedFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH));
	}


	// inc_hachu_thu_fgに対するセッターとゲッターの集合
	public boolean setIncHachuThuFg(String inc_hachu_thu_fg)
	{
		this.inc_hachu_thu_fg = inc_hachu_thu_fg;
		return true;
	}
	public String getIncHachuThuFg()
	{
		return cutString(this.inc_hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH);
	}
	public String getIncHachuThuFgString()
	{
		return "'" + cutString(this.inc_hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuThuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH));
	}


	// inc_hachu_fri_fgに対するセッターとゲッターの集合
	public boolean setIncHachuFriFg(String inc_hachu_fri_fg)
	{
		this.inc_hachu_fri_fg = inc_hachu_fri_fg;
		return true;
	}
	public String getIncHachuFriFg()
	{
		return cutString(this.inc_hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH);
	}
	public String getIncHachuFriFgString()
	{
		return "'" + cutString(this.inc_hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuFriFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH));
	}


	// inc_hachu_sat_fgに対するセッターとゲッターの集合
	public boolean setIncHachuSatFg(String inc_hachu_sat_fg)
	{
		this.inc_hachu_sat_fg = inc_hachu_sat_fg;
		return true;
	}
	public String getIncHachuSatFg()
	{
		return cutString(this.inc_hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH);
	}
	public String getIncHachuSatFgString()
	{
		return "'" + cutString(this.inc_hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuSatFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH));
	}


	// inc_hachu_sun_fgに対するセッターとゲッターの集合
	public boolean setIncHachuSunFg(String inc_hachu_sun_fg)
	{
		this.inc_hachu_sun_fg = inc_hachu_sun_fg;
		return true;
	}
	public String getIncHachuSunFg()
	{
		return cutString(this.inc_hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH);
	}
	public String getIncHachuSunFgString()
	{
		return "'" + cutString(this.inc_hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH) + "'";
	}
	public String getIncHachuSunFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH));
	}


	// inc_rtime_mon_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeMonKb(String inc_rtime_mon_kb)
	{
		this.inc_rtime_mon_kb = inc_rtime_mon_kb;
		return true;
	}
	public String getIncRtimeMonKb()
	{
		return cutString(this.inc_rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH);
	}
	public String getIncRtimeMonKbString()
	{
		return "'" + cutString(this.inc_rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeMonKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH));
	}


	// inc_rtime_tue_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeTueKb(String inc_rtime_tue_kb)
	{
		this.inc_rtime_tue_kb = inc_rtime_tue_kb;
		return true;
	}
	public String getIncRtimeTueKb()
	{
		return cutString(this.inc_rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH);
	}
	public String getIncRtimeTueKbString()
	{
		return "'" + cutString(this.inc_rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeTueKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH));
	}


	// inc_rtime_wed_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeWedKb(String inc_rtime_wed_kb)
	{
		this.inc_rtime_wed_kb = inc_rtime_wed_kb;
		return true;
	}
	public String getIncRtimeWedKb()
	{
		return cutString(this.inc_rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH);
	}
	public String getIncRtimeWedKbString()
	{
		return "'" + cutString(this.inc_rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeWedKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH));
	}


	// inc_rtime_thu_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeThuKb(String inc_rtime_thu_kb)
	{
		this.inc_rtime_thu_kb = inc_rtime_thu_kb;
		return true;
	}
	public String getIncRtimeThuKb()
	{
		return cutString(this.inc_rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH);
	}
	public String getIncRtimeThuKbString()
	{
		return "'" + cutString(this.inc_rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeThuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH));
	}


	// inc_rtime_fri_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeFriKb(String inc_rtime_fri_kb)
	{
		this.inc_rtime_fri_kb = inc_rtime_fri_kb;
		return true;
	}
	public String getIncRtimeFriKb()
	{
		return cutString(this.inc_rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH);
	}
	public String getIncRtimeFriKbString()
	{
		return "'" + cutString(this.inc_rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeFriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH));
	}


	// inc_rtime_sat_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeSatKb(String inc_rtime_sat_kb)
	{
		this.inc_rtime_sat_kb = inc_rtime_sat_kb;
		return true;
	}
	public String getIncRtimeSatKb()
	{
		return cutString(this.inc_rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH);
	}
	public String getIncRtimeSatKbString()
	{
		return "'" + cutString(this.inc_rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeSatKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH));
	}


	// inc_rtime_sun_kbに対するセッターとゲッターの集合
	public boolean setIncRtimeSunKb(String inc_rtime_sun_kb)
	{
		this.inc_rtime_sun_kb = inc_rtime_sun_kb;
		return true;
	}
	public String getIncRtimeSunKb()
	{
		return cutString(this.inc_rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH);
	}
	public String getIncRtimeSunKbString()
	{
		return "'" + cutString(this.inc_rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH) + "'";
	}
	public String getIncRtimeSunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH));
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
	

	// inc_insert_tsに対するセッターとゲッターの集合
	public boolean setIncInsertTs(String inc_insert_ts)
	{
		this.inc_insert_ts = inc_insert_ts;
		return true;
	}
	public String getIncInsertTs()
	{
		return this.inc_insert_ts;
	}

	// inc_insert_user_idに対するセッターとゲッターの集合
	public boolean setIncInsertUserId(String inc_insert_user_id)
	{
		this.inc_insert_user_id = inc_insert_user_id;
		return true;
	}
	public String getIncInsertUserId()
	{
		return this.inc_insert_user_id;
	}

	// inc_update_tsに対するセッターとゲッターの集合
	public boolean setIncUpdateTs(String inc_update_ts)
	{
		this.inc_update_ts = inc_update_ts;
		return true;
	}
	public String getIncUpdateTs()
	{
		return this.inc_update_ts;
	}

	// inc_update_user_idに対するセッターとゲッターの集合
	public boolean setIncUpdateUserId(String inc_update_user_id)
	{
		this.inc_update_user_id = inc_update_user_id;
		return true;
	}
	public String getIncUpdateUserId()
	{
		return this.inc_update_user_id;
	}

	// inc_delete_fgに対するセッターとゲッターの集合
	public boolean setIncDeleteFg(String inc_delete_fg)
	{
		this.inc_delete_fg = inc_delete_fg;
		return true;
	}
	public String getIncDeleteFg()
	{
		return this.inc_delete_fg;
	}
	
//	↓↓仕様変更（2005.07.28）↓↓
	// inc_syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setIncSyokaiTorokuTs(String inc_syokai_toroku_ts)
	{
		this.inc_syokai_toroku_ts = inc_syokai_toroku_ts;
		return true;
	}
	public String getIncSyokaiTorokuTs()
	{
		return cutString(this.inc_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH);
	}
	public String getIncSyokaiTorokuTsString()
	{
		return "'" + cutString(this.inc_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH) + "'";
	}
	public String getIncSyokaiTorokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH));
	}

	// inc_syokai_user_idに対するセッターとゲッターの集合
	public boolean setIncSyokaiUserId(String inc_syokai_user_id)
	{
		this.inc_syokai_user_id = inc_syokai_user_id;
		return true;
	}
	public String getIncSyokaiUserId()
	{
		return cutString(this.inc_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getIncSyokaiUserIdString()
	{
		return "'" + cutString(this.inc_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getIncSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.inc_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.28）↑↑

	// first_insert_tsに対するセッターとゲッターの集合
	public boolean setFirstInsertTs(String first_insert_ts)
	{
		this.first_insert_ts = first_insert_ts;
		return true;
	}
	public String getFirstInsertTs()
	{
		return this.first_insert_ts;
	}

	// first_insert_user_idに対するセッターとゲッターの集合
	public boolean setFirstInsertUserId(String first_insert_user_id)
	{
		this.first_insert_user_id = first_insert_user_id;
		return true;
	}
	public String getFirstInsertUserId()
	{
		return this.first_insert_user_id;
	}

	// first_update_tsに対するセッターとゲッターの集合
	public boolean setFirstUpdateTs(String first_update_ts)
	{
		this.first_update_ts = first_update_ts;
		return true;
	}
	public String getFirstUpdateTs()
	{
		return this.first_update_ts;
	}

	// first_update_user_idに対するセッターとゲッターの集合
	public boolean setFirstUpdateUserId(String first_update_user_id)
	{
		this.first_update_user_id = first_update_user_id;
		return true;
	}
	public String getFirstUpdateUserId()
	{
		return this.first_update_user_id;
	}
	
//	↓↓仕様変更（2005.07.28）↓↓
	// first_syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setFirstSyokaiTorokuTs(String first_syokai_toroku_ts)
	{
		this.first_syokai_toroku_ts = first_syokai_toroku_ts;
		return true;
	}
	public String getFirstSyokaiTorokuTs()
	{
		return cutString(this.first_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH);
	}
	public String getFirstSyokaiTorokuTsString()
	{
		return "'" + cutString(this.first_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH) + "'";
	}
	public String getFirstSyokaiTorokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.first_syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH));
	}

	// first_syokai_user_idに対するセッターとゲッターの集合
	public boolean setFirstSyokaiUserId(String first_syokai_user_id)
	{
		this.first_syokai_user_id = first_syokai_user_id;
		return true;
	}
	public String getFirstSyokaiUserId()
	{
		return cutString(this.first_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getFirstSyokaiUserIdString()
	{
		return "'" + cutString(this.first_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getFirstSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.first_syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.28）↑↑

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
//BUGNO-S052 2005.05.14 SIRIUS END
}
