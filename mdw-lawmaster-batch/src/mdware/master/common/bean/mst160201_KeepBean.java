package mdware.master.common.bean;
import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（条件指定）の画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）の画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/16)初版作成
 * @version 1.1(2006.09.28)障害票№0086対応 1日の処理対象上限件数を越えた場合に、処理を翌日に回す K.Tanigawa
 * @version 1.2(2006.11.14)障害票No.0143対応(仕入先コードの曖昧検索機能の追加) K.Tanigawa
 */
public class mst160201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String ProcessingDivision		= mst000101_ConstDictionary.PROCESS_REFERENCE;// 処理区分
	private String ErrorFlg				= "";				// エラーフラグ
	private String ErrorMessage			= "";				// エラーメッセージ
	private String Mode					= "";				// 処理モード
	private String[] MenuBar				= null;			// メニューバーアイテム
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private String FirstFocus				= "";				// フォーカスを最初に取得するオブジェクト名
	private String InsertFlg				= "";				// 新規処理利用可能区分
	private String UpdateFlg				= "";				// 更新処理利用可能区分
	private String DeleteFlg				= "";				// 削除処理利用可能区分
	private String ReferenceFlg			= "";				// 照会処理利用可能区分
	private String CsvFlg					= "";				// CSV処理利用可能区分
	private String PrintFlg				= "";				// 印刷処理利用可能区分
	private String CheckFlg			    = "";				// チェック処理判断
//	↓↓2006.07.14 lixy カスタマイズ修正↓↓
	private String gamen_kb = null;	//画面区分
    public static final int GAMEN_KB_MAX_LENGTH  = 1; //画面区分の長さ
//	↑↑2006.07.14 lixy カスタマイズ修正↑↑

//  ↓↓2006.07.06 lixy カスタマイズ修正↓↓
//	private String hanku_cd 				= "";				//販区コード
//	private String hanku_kanji_rn 			= "";				//販区名
	private String system_kb 				= null;				//システム区分
	private String bumon_cd				="";				//部門コード
	private String bumon_kanji_rn			="";				//部門コード名
	private String hinban_cd				="";				//品番
	private String hinban_kanji_rn			="";				//品番名
	private String ranyin_cd				="";				//ライン
	private String ranyin_kanji_rn			="";				//ライン名
	private String siiresakisyohin_cd  	="";				//仕入先商品コード
	private String siiresakisyohin_kanji_rn ="";				//仕入先商品コード名
	private String sabkuras_cd				="";				//サブクラスコード
	private String sabkuras_kanji_cd		="";				//サブクラス名
	private String up_siiresakikanri_choice		= "";		//店別仕入先管理コード選択(更新)
	private String up_siiresakikanri_cd			="";		//店別仕入先管理コード(更新)
	private String siiresakikanri_cd       ="";				//店別仕入先管理コード
	private String siiresakikanri_kanji_rn ="";				//店別仕入先管理コード名
	private String up_siiresakikanri_kanji_rn ="";				//店別仕入先管理コード名選択(更新)
//  ↓↓2006.07.16 lixy カスタマイズ修正↓↓
//	private String up_area_choice		= "";					//地区コード選択(更新)
//	private String up_area_kanji_rn	="";						//地区コード名選択(更新)
//	private String area_cd        			="";				//地区コード
//	private String area_kanji_rn        	="";				//地区コード名
//  ↑↑2006.07.16 lixy カスタマイズ修正↑↑
	private String up_neirert_choice		= "";				//値入率変更選択(更新)
	private String up_neirert_kb 		= "";					//値入率変更区分(更新)
	private String up_neirert_vl 		= "";					//値入率変更値(更新)
	private String hachu_st_dt        	    ="";				//店舗発注開始日
	private String hachu_ed_dt        	    ="";				//店舗発注終了日
	private String up_hachu_st_dt        	    ="";			//店舗発注開始日(更新)
	private String up_hachu_st_choice		= "";				//店舗発注開始日選択(更新)
	private String up_hachu_ed_choice		= "";				//店舗発注終了日選択(更新)
	private String up_hachu_ed_dt        	    ="";			//店舗発注終了日
	private String up_eoskb_chg_choice 	= "";				//EOS区分変更選択(更新)
	private String up_eoskb_chg_kb 		= "";				//EOS区分変更区分(更新)
	private String up_unit_choice		= "";					//EOS区分変更値(更新)
	private String unit_cd        	    	="";				//ユニットコード
	private String up_unit_kanji_rn		="";				//ユニット名(更新)
	private String tanano_st_nb			="";				//開始棚NO
	private String tanano_ed_nb			="";				//終了棚NO

	private String unit_kanji_rn       	="";				//ユニットコード名
	private String up_bayia_choice		= "";
	private String bayia_cd        	    ="";				//初回登録社員ID
	private String syohinkb_cd				="";				//商品区分コード
	private String syohinkb_kanji_rn		="";				//商品区分名
	private String baitenka_st			="";					//売開始単価
	private String baitanka_ed			="";					//売終了単価
//    ↑↑2006.07.06 lixy カスタマイズ修正↑↑

//  ↓↓2006.07.11 lixy カスタマイズ修正↓↓
	private String by_no                   = "";	            //BY NO.
	private String file_nm                 = "";				//文件名
//  ↑↑2006.07.11 lixy カスタマイズ修正↑↑

	private String hinsyu_cd 				= "";				//品種コード
	private String hinsyu_kanji_rn			= "";				//品種名
	private String siiresaki_cd 			= "";				//仕入先コード
	private String siiresaki_kanji_rn 		= "";				//仕入先名
	private String jan_mark_cd 			= "";				//JANメーカコード
	private String jan_mark_kanji_rn 		= "";				//JANメーカ名
	private String hanbai_st_dt 			= "";				//販売開始日
	private String hanbai_ed_dt 			= "";				//販売終了日
	private String season_cd 				= "";				//シーズンコード
	private String season_kanji_rn 		= "";				//シーズン名
	private String ten_groupno_cd 			= "";				//店グルーピング名
	private String ten_groupno_name_na		= "";				//店グルーピング名

	private String up_siiresaki_choice		= "";				//仕入先コード選択(更新)
	private String up_siiresaki_cd 		= "";				//仕入先コード(更新)
	private String up_siiresaki_kanji_rn 	= "";				//仕入先名(更新)
	private String up_genka_chg_choice		= "";				//原価変更選択(更新)
	private String up_genka_chg_kb 		= "";				//原価変更区分(更新)
	private String up_genka_chg_vl 		= "";				//原価変更値(更新)
	private String up_baika_chg_choice 	= "";				//売価変更選択(更新)
	private String up_baika_chg_kb 		= "";				//売価変更区分(更新)
	private String up_baika_chg_vl 		= "";				//売価変更値(更新)
	private String up_hanbai_st_choice		= "";				//販売開始日選択(更新)
	private String up_hanbai_st_dt 		= "";				//販売開始日(更新)
	private String up_hanbai_ed_choice		= "";				//販売終了日選択(更新)
	private String up_hanbai_ed_dt 		= "";				//販売終了日(更新)

//  ↓↓2006.07.18 lixy カスタマイズ修正↓↓
	private String up_tanano_nb            = "";
	private String up_bayia_cd             = "";
	private String up_tanano_choice        = "";
	private String up_unit_cd              = "";
//  ↑↑2006.07.18 lixy カスタマイズ修正↑↑

	// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //
	private String yuko_dt = null;	//有効日（更新）
	// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //

//	 ADD by Tanigawa 2006/11/09 障害票№0194対応 START
	private String default_yuko_dt = null;	//本日表示する有効日(シスコン.マスタ管理日付 + 1)
//	 ADD by Tanigawa 2006/11/09 障害票№0194対応  END

//	 ADD by Tanigawa 2006/11/16 障害票№XXXX対応 START
	private int scrollPos = 0;
//	 ADD by Tanigawa 2006/11/16 障害票№XXXX対応  END

//	 ADD by Tanigawa 2006/9/28 障害票№0086対応 1日の処理対象上限件数を越えた場合に、処理を翌日に回す START
	private int procNum      = 0;	//表示件数保持用
	private int procRegisteredNum = 0;	//すでに本日処理対象となっているデータ数
	private int procMaxNum   = 0;	//処理上限値
	private String torokuTs  = "";	//画面のデータをR_SYOHIN_IKKATU_MENTEテーブルに挿入する際の登録年月日
//	 ADD by Tanigawa 2006/9/28 障害票№0086対応 1日の処理対象上限件数を越えた場合に、処理を翌日に回す  END

//	 ADD by Tanigawa 2006/11/14 障害票№0143対応 START
	private boolean isSiiresakiSyohinCdCBChecked = false;	//仕入先商品コードチェックボックスにチェックが入っているか否かの確認用
//	 ADD by Tanigawa 2006/11/14 障害票№0143対応  END

//  ↓↓2006.07.11 lixy カスタマイズ修正↓↓
	//private String gyosyuid = null;//業種ID
//  ↑↑2006.07.11 lixy カスタマイズ修正↑↑

//BUGNO-S052 2005.05.14 Sirius START
	private static final String INIT_PAGE   = "mst160101_SyohinIkkatuMenteInit";	// 初期画面JSPを取得
	private static final String VIEW_PAGE   = "mst160201_SyohinIkkatuMenteView";	// 一覧画面JSPを取得
	private static final String EDIT_PAGE   = "mst170101_SyohinIkkatuMenteEdit";	// 編集画面JSPを取得
//  ↓↓2006.07.11 lixy カスタマイズ修正↓↓
//	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//  ↑↑2006.07.11 lixy カスタマイズ修正↑↑
//BUGNO-S052 2005.05.14 Sirius END

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

//  ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓

	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return this.hanku_cd;
//	}

	// hanku_kanji_rnに対するセッターとゲッターの集合
//	public boolean setHankuKanjiRn(String hanku_kanji_rn)
//	{
//		this.hanku_kanji_rn = hanku_kanji_rn;
//		return true;
//	}
//	public String getHankuKanjiRn()
//	{
//		return this.hanku_kanji_rn;
//	}

	//system_kbに対するセッターとゲッターの集合
	public String getSystemKb() {
		return system_kb;
	}


	public void setSystemKb(String system_kb) {
		this.system_kb = system_kb;
	}
	//	 tanano_st_nbに対するセッターとゲッターの集合
	public boolean setTananostNb(String tanano_st_nb)
	{
		this.tanano_st_nb = tanano_st_nb;
		return true;
	}
	public String getTananostNb()
	{
		return this.tanano_st_nb;
	}
	//	 tanano_ed_nbに対するセッターとゲッターの集合
	public boolean setTananoedNb(String tanano_ed_nb)
	{
		this.tanano_ed_nb = tanano_ed_nb;
		return true;
	}
	public String getTananoedNb()
	{
		return this.tanano_ed_nb;
	}
	//	 setBumonCdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return this.bumon_cd;
	}
	// bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return this.bumon_kanji_rn;
	}
	//	 hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return this.hinban_cd;
	}

	// hinban_kanji_rnに対するセッターとゲッターの集合
	public boolean setHinbanKanjiRn(String hinban_kanji_rn)
	{
		this.hinban_kanji_rn = hinban_kanji_rn;
		return true;
	}
	public String getHinbanKanjiRn()
	{
		return this.hinban_kanji_rn;
	}
	//	 ranyin_cdに対するセッターとゲッターの集合
	public boolean setRayinCd(String ranyin_cd)
	{
		this.ranyin_cd = ranyin_cd;
		return true;
	}
	public String getRayinbanCd()
	{
		return this.ranyin_cd;
	}

	// rayin_kanji_rnに対するセッターとゲッターの集合
	public boolean setRanyinKanjiRn(String ranyin_kanji_rn)
	{
		this.ranyin_kanji_rn = ranyin_kanji_rn;
		return true;
	}
	public String getRanyinKanjiRn()
	{
		return this.ranyin_kanji_rn;
	}
	//	 siiresakisyohin_cdに対するセッターとゲッターの集合
	public boolean setSiiresakisyohinCd(String siiresakisyohin_cd)
	{
		this.siiresakisyohin_cd = siiresakisyohin_cd;
		return true;
	}
	public String getSiiresakisyohinCd()
	{
		return this.siiresakisyohin_cd;
	}

	// siiresakisyohin_kanji_rnに対するセッターとゲッターの集合
	public boolean setSiiresakisyohinKanjiRn(String siiresakisyohin_kanji_rn)
	{
		this.siiresakisyohin_kanji_rn = siiresakisyohin_kanji_rn;
		return true;
	}
	public String getSiiresakisyohinKanjiRn()
	{
		return this.siiresakisyohin_kanji_rn;
	}

	// siiresakisyohin_kanji_rnに対するセッターとゲッターの集合
	public boolean setFileNm(String file_nm)
	{
		this.file_nm = file_nm;
		return true;
	}
	public String getFileNm()
	{
		return this.file_nm;
	}

//  ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑

//  ↓↓2006.07.16 lixy カスタマイズ修正↓↓
	//up_area_choiceに対するセッターとゲッターの集合
//	public boolean setUpAreaChoice(String up_area_choice)
//	{
//		this.up_area_choice = up_area_choice;
//		return true;
//	}
//	public String getUpAreaChoice()
//	{
//		return this.up_area_choice;
//	}
//	// area_cdに対するセッターとゲッターの集合
//	public boolean setAreaCd(String area_cd)
//	{
//		this.area_cd = area_cd;
//		return true;
//	}
//	public String getAreaCd()
//	{
//		return this.area_cd;
//	}
//	//	 up_area_kanji_rnに対するセッターとゲッターの集合
//	public boolean setUpAreakanjiRn(String up_area_kanji_rn)
//	{
//		this.up_area_kanji_rn = up_area_kanji_rn;
//		return true;
//	}
//	public String getUpAreaKanjiRn()
//	{
//		return this.up_area_kanji_rn;
//	}
//	// area_kanji_rnに対するセッターとゲッターの集合
//	public boolean setAreakanjiRn(String area_kanji_rn)
//	{
//		this.area_kanji_rn = area_kanji_rn;
//		return true;
//	}
//	public String getAreaKanjiRn()
//	{
//		return this.area_kanji_rn;
//	}
//  ↑↑2006.07.16 lixy カスタマイズ修正↑↑
	//	 syohinkb_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohinkb_cd)
	{
		this.syohinkb_cd = syohinkb_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return this.syohinkb_cd;
	}

	// syohinkb_cdに対するセッターとゲッターの集合
	public boolean setSyohinkanjiRn(String syohinkb_kanji_rn)
	{
		this.syohinkb_kanji_rn = syohinkb_kanji_rn;
		return true;
	}
	public String getSyohinKanjiRn()
	{
		return this.syohinkb_kanji_rn;
	}

	//	 unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return this.unit_cd;
	}

	// unit_kanji_rnに対するセッターとゲッターの集合
	public boolean setUnitKanjiRn(String unit_kanji_rn)
	{
		this.unit_kanji_rn = unit_kanji_rn;
		return true;
	}
	public String getUnitKanjiRn()
	{
		return this.unit_kanji_rn;
	}
	//	 up_unit_kanji_rnに対するセッターとゲッターの集合
	public boolean setUpunitKanjiRn(String up_unit_kanji_rn)
	{
		this.up_unit_kanji_rn = up_unit_kanji_rn;
		return true;
	}
	public String getUpunitKanjiRn()
	{
		return this.up_unit_kanji_rn;
	}

	public boolean setSiiresakikanriCd(String siiresakikanri_cd)
	{
		this.siiresakikanri_cd = siiresakikanri_cd;
		return true;
	}
	public String getSiiresakikanriCd()
	{
		return this.siiresakikanri_cd;
	}
	public boolean setUpSiiresakikanriCd(String up_siiresakikanri_cd)
	{
		this.up_siiresakikanri_cd = up_siiresakikanri_cd;
		return true;
	}
	public String getUpSiiresakikanriCd()
	{
		return this.up_siiresakikanri_cd;
	}
	// up_siiresakikanri_choiceに対するセッターとゲッターの集合
	public boolean setUpSiiresakikanriChoice(String up_siiresakikanri_choice)
	{
		this.up_siiresakikanri_choice = up_siiresakikanri_choice;
		return true;
	}
	public String getUpSiiresakikanriChoice()
	{
		return this.up_siiresakikanri_choice;
	}

	// siiresakikanri_kanji_rnに対するセッターとゲッターの集合
	public boolean setSiiresakikanrikanjiRn(String siiresakikanri_kanji_rn)
	{
		this.siiresakikanri_kanji_rn = siiresakikanri_kanji_rn;
		return true;
	}
	public String getSiiresakikanriKanjiRn()
	{
		return this.siiresakikanri_kanji_rn;
	}
	// up_siiresakikanri_kanji_rnに対するセッターとゲッターの集合
	public boolean setUpSiiresakikanriKanjiRn(String up_siiresakikanri_kanji_rn)
	{
		this.up_siiresakikanri_kanji_rn = up_siiresakikanri_kanji_rn;
		return true;
	}
	public String getUpSiiresakikanriKanjiRn()
	{
		return this.up_siiresakikanri_kanji_rn;
	}

	public boolean setUpEoskbChgChoice(String up_eoskb_chg_choice)
	{
		this.up_eoskb_chg_choice = up_eoskb_chg_choice;
		return true;
	}
	public String getUpEoskbChgChoice()
	{
		return this.up_eoskb_chg_choice;
	}
	// up_baika_chg_kbに対するセッターとゲッターの集合
	public boolean setEosKb(String up_eoskb_chg_kb)
	{
		this.up_eoskb_chg_kb = up_eoskb_chg_kb;
		return true;
	}
	public String getEosKb()
	{
		return this.up_eoskb_chg_kb;
	}
	//up_bayia_choice
	public boolean setUpBayiaChoice(String up_bayia_choice)
	{
		this.up_bayia_choice = up_bayia_choice;
		return true;
	}
	public String getUpBayiaChoice()
	{
		return this.up_bayia_choice;
	}
	//	bayia_cd
	public boolean setBayiaCd(String bayia_cd)
	{
		this.bayia_cd = bayia_cd;
		return true;
	}
	public String getBayiaCd()
	{
		return this.bayia_cd;
	}
	// sabkuras_cdに対するセッターとゲッターの集合
	public boolean setSabkurasCd(String sabkuras_cd)
	{
		this.sabkuras_cd = sabkuras_cd;
		return true;
	}
	public String getSabkurasCd()
	{
		return this.sabkuras_cd;
	}
	// siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setSabkurasKanjiRn(String sabkuras_kanji_cd)
	{
		this.sabkuras_kanji_cd = sabkuras_kanji_cd;
		return true;
	}
	public String getSabkurasKanjiRn()
	{
		return this.sabkuras_kanji_cd;
	}
	//	 baitenka_stに対するセッターとゲッターの集合
	public boolean setBaisttanka(String baitenka_st)
	{
		this.baitenka_st = baitenka_st;
		return true;
	}
	public String getBaisttanka()
	{
		return this.baitenka_st;
	}
	//	 baitanka_edに対するセッターとゲッターの集合
	public boolean setBaiedtanka(String baitanka_ed)
	{
		this.baitanka_ed = baitanka_ed;
		return true;
	}
	public String getBaiedstanka()
	{
		return this.baitanka_ed;
	}
	// up_baika_chg_vlに対するセッターとゲッターの集合
	public boolean setUpUnitChoice(String up_unit_choice)
	{
		this.up_unit_choice = up_unit_choice;
		return true;
	}
	public String getUpUnitChoice()
	{
		return this.up_unit_choice;
	}
	//	 up_neirert_choiceに対するセッターとゲッターの集合
	public boolean setUpNeireChoice(String up_neirert_choice)
	{
		this.up_neirert_choice = up_neirert_choice;
		return true;
	}
	public String getUpNeireChoice()
	{
		return this.up_neirert_choice;
	}
	// up_baika_chg_kbに対するセッターとゲッターの集合
	public boolean setUpNeireKb(String up_neirert_kb)
	{
		this.up_neirert_kb = up_neirert_kb;
		return true;
	}
	public String getUpNeirekb()
	{
		return this.up_neirert_kb;
	}
	// up_baika_chg_vlに対するセッターとゲッターの集合
	public boolean setUpNeireVl(String up_neirert_vl)
	{
		this.up_neirert_vl = up_neirert_vl;
		return true;
	}
	public String getUpNeireVl()
	{
		return this.up_neirert_vl;
	}
	//	 hachu_st_dtに対するセッターとゲッターの集合
	public boolean setHachuStDt(String hachu_st_dt)
	{
		this.hachu_st_dt = hachu_st_dt;
		return true;
	}
	public String getHachuStDt()
	{
		return this.hachu_st_dt;
	}

	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHachuEdDt(String hachu_ed_dt)
	{
		this.hachu_ed_dt = hachu_ed_dt;
		return true;
	}
	public String getHachuEdDt()
	{
		return this.hachu_ed_dt;
	}
	//	 up_hachu_st_choiceに対するセッターとゲッターの集合
	public boolean setUpHachuStChoice(String up_hachu_st_choice)
	{
		this.up_hachu_st_choice = up_hachu_st_choice;
		return true;
	}
	public String getUpHachuStChoice()
	{
		return this.up_hachu_st_choice;
	}
	//	 up_hachu_ed_choiceに対するセッターとゲッターの集合
	public boolean setUpHachuEdChoice(String up_hachu_ed_choice)
	{
		this.up_hachu_ed_choice = up_hachu_ed_choice;
		return true;
	}
	public String getUpHachuEdChoice()
	{
		return this.up_hachu_ed_choice;
	}
	//	 up_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setUpHachuStDt(String up_hachu_st_dt)
	{
		this.up_hachu_st_dt = up_hachu_st_dt;
		return true;
	}
	public String getUpHachuStDt()
	{
		return this.up_hachu_st_dt;
	}
	//	 up_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setUpHachuEdDt(String up_hachu_ed_dt)
	{
		this.up_hachu_ed_dt = up_hachu_ed_dt;
		return true;
	}
	public String getUpHachuEdDt()
	{
		return this.up_hachu_ed_dt;
	}
//  ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑

//  ↓↓2006.07.11 lixy カスタマイズ修正↓↓
	//by_noに対するセッターとゲッターの集合
	public boolean setByNo(String by_no)
	{
		this.by_no = by_no;
		return true;
	}
	public String getByNo()
	{
		return this.by_no;
	}
//  ↑↑2006.07.11 lixy カスタマイズ修正↑↑

	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return this.hinsyu_cd;
	}

	// hinsyu_kanji_rnに対するセッターとゲッターの集合
	public boolean setHinsyuKanjiRn(String hinsyu_kanji_rn)
	{
		this.hinsyu_kanji_rn = hinsyu_kanji_rn;
		return true;
	}
	public String getHinsyuKanjiRn()
	{
		return this.hinsyu_kanji_rn;
	}

	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return this.siiresaki_cd;
	}

	// siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setSiiresakiKanjiRn(String siiresaki_kanji_rn)
	{
		this.siiresaki_kanji_rn = siiresaki_kanji_rn;
		return true;
	}
	public String getSiiresakiKanjiRn()
	{
		return this.siiresaki_kanji_rn;
	}

	// jan_mark_cdに対するセッターとゲッターの集合
	public boolean setJanMarkCd(String jan_mark_cd)
	{
		this.jan_mark_cd = jan_mark_cd;
		return true;
	}
	public String getJanMarkCd()
	{
		return this.jan_mark_cd;
	}

	// jan_mark_kanji_rnに対するセッターとゲッターの集合
	public boolean setJanMarkKanjiRn(String jan_mark_kanji_rn)
	{
		this.jan_mark_kanji_rn = jan_mark_kanji_rn;
		return true;
	}
	public String getJanMarkKanjiRn()
	{
		return this.jan_mark_kanji_rn;
	}

	// hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setHanbaiStDt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
		return true;
	}
	public String getHanbaiStDt()
	{
		return this.hanbai_st_dt;
	}

	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHanbaiEdDt(String hanbai_ed_dt)
	{
		this.hanbai_ed_dt = hanbai_ed_dt;
		return true;
	}
	public String getHanbaiEdDt()
	{
		return this.hanbai_ed_dt;
	}

	// season_cdに対するセッターとゲッターの集合
	public boolean setSeasonCd(String season_cd)
	{
		this.season_cd = season_cd;
		return true;
	}
	public String getSeasonCd()
	{
		return this.season_cd;
	}

	// season_kanji_rnに対するセッターとゲッターの集合
	public boolean setSeasonKanjiRn(String season_kanji_rn)
	{
		this.season_kanji_rn = season_kanji_rn;
		return true;
	}
	public String getSeasonKanjiRn()
	{
		return this.season_kanji_rn;
	}

	// ten_groupno_cdに対するセッターとゲッターの集合
	public boolean setTenGroupnoCd(String ten_groupno_cd)
	{
		this.ten_groupno_cd = ten_groupno_cd;
		return true;
	}
	public String getTenGroupnoCd()
	{
		return this.ten_groupno_cd;
	}

	// ten_groupno_name_naに対するセッターとゲッターの集合
	public boolean setTenGroupnoNameNa(String ten_groupno_name_na)
	{
		this.ten_groupno_name_na = ten_groupno_name_na;
		return true;
	}
	public String getTenGroupnoNameNa()
	{
		return this.ten_groupno_name_na;
	}

	// up_siiresaki_choiceに対するセッターとゲッターの集合
	public boolean setUpSiiresakiChoice(String up_siiresaki_choice)
	{
		this.up_siiresaki_choice = up_siiresaki_choice;
		return true;
	}
	public String getUpSiiresakiChoice()
	{
		return this.up_siiresaki_choice;
	}

	// up_siiresaki_cdに対するセッターとゲッターの集合
	public boolean setUpSiiresakiCd(String up_siiresaki_cd)
	{
		this.up_siiresaki_cd = up_siiresaki_cd;
		return true;
	}
	public String getUpSiiresakiCd()
	{
		return this.up_siiresaki_cd;
	}

	// up_siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setUpSiiresakiKanjiRn(String up_siiresaki_kanji_rn)
	{
		this.up_siiresaki_kanji_rn = up_siiresaki_kanji_rn;
		return true;
	}
	public String getUpSiiresakiKanjiRn()
	{
		return this.up_siiresaki_kanji_rn;
	}

	// up_genka_chg_choiceに対するセッターとゲッターの集合
	public boolean setUpGenkaChgChoice(String up_genka_chg_choice)
	{
		this.up_genka_chg_choice = up_genka_chg_choice;
		return true;
	}
	public String getUpGenkaChgChoice()
	{
		return this.up_genka_chg_choice;
	}

	// up_genka_chg_kbに対するセッターとゲッターの集合
	public boolean setUpGenkaChgKb(String up_genka_chg_kb)
	{
		this.up_genka_chg_kb = up_genka_chg_kb;
		return true;
	}
	public String getUpGenkaChgkb()
	{
		return this.up_genka_chg_kb;
	}
	// up_genka_chg_vlに対するセッターとゲッターの集合
	public boolean setUpGenkaChgVl(String up_genka_chg_vl)
	{
		this.up_genka_chg_vl = up_genka_chg_vl;
		return true;
	}
	public String getUpGenkaChgVl()
	{
		return this.up_genka_chg_vl;
	}

	// up_baika_chg_choiceに対するセッターとゲッターの集合
	public boolean setUpBaikaChgChoice(String up_baika_chg_choice)
	{
		this.up_baika_chg_choice = up_baika_chg_choice;
		return true;
	}
	public String getUpBaikaChgChoice()
	{
		return this.up_baika_chg_choice;
	}

	// up_baika_chg_kbに対するセッターとゲッターの集合
	public boolean setUpBaikaChgKb(String up_baika_chg_kb)
	{
		this.up_baika_chg_kb = up_baika_chg_kb;
		return true;
	}
	public String getUpBaikaChgkb()
	{
		return this.up_baika_chg_kb;
	}
	// up_baika_chg_vlに対するセッターとゲッターの集合
	public boolean setUpBaikaChgVl(String up_baika_chg_vl)
	{
		this.up_baika_chg_vl = up_baika_chg_vl;
		return true;
	}
	public String getUpBaikaChgVl()
	{
		return this.up_baika_chg_vl;
	}

	// up_hanbai_st_choicetに対するセッターとゲッターの集合
	public boolean setUpHanbaiStChoice(String up_hanbai_st_choice)
	{
		this.up_hanbai_st_choice = up_hanbai_st_choice;
		return true;
	}
	public String getUpHanbaiStChoice()
	{
		return this.up_hanbai_st_choice;
	}

	// up_hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setUpHanbaiStDt(String up_hanbai_st_dt)
	{
		this.up_hanbai_st_dt = up_hanbai_st_dt;
		return true;
	}
	public String getUpHanbaiStDt()
	{
		return this.up_hanbai_st_dt;
	}

	// up_hanbai_ed_choiceに対するセッターとゲッターの集合
	public boolean setUpHanbaiEdChoice(String up_hanbai_ed_choice)
	{
		this.up_hanbai_ed_choice = up_hanbai_ed_choice;
		return true;
	}
	public String getUpHanbaiEdChoice()
	{
		return this.up_hanbai_ed_choice;
	}

	// up_hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setUpHanbaiEdDt(String up_hanbai_ed_dt)
	{
		this.up_hanbai_ed_dt = up_hanbai_ed_dt;
		return true;
	}
	public String getUpHanbaiEdDt()
	{
		return this.up_hanbai_ed_dt;
	}

	// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return this.yuko_dt;
	}

//	 ADD by Tanigawa 2006/11/09 障害票№0194対応 START
	// yuko_dtに対するセッターとゲッターの集合
	public boolean setDefaultYukoDt(String default_yuko_dt)
	{
		this.default_yuko_dt = default_yuko_dt;
		return true;
	}
	public String getDefaultYukoDt()
	{
		return this.default_yuko_dt;
	}
//	 ADD by Tanigawa 2006/11/09 障害票№0194対応  END

//  ↓↓2006.07.18 lixy カスタマイズ修正↓↓
	//	up_tanano_nb
	 public boolean setUpTananoNb(String up_tanano_nb)
	 {
	  this.up_tanano_nb = up_tanano_nb;
	  return true;
	 }
	 public String getUpTananoNb()
	 {
	  return this.up_tanano_nb;
	 }

	 //	up_bayia_cd
	 public boolean setUpBayiaCd(String up_bayia_cd)
	 {
	  this.up_bayia_cd = up_bayia_cd;
	  return true;
	 }
	 public String getUpBayiaCd()
	 {
	  return this.up_bayia_cd;
	 }
	 //  up_unit_cdに対するセッターとゲッターの集合
	 public boolean setUpUnitCd(String up_unit_cd)
	 {
	  this.up_unit_cd = up_unit_cd;
	  return true;
	 }
	 public String getUpUnitCd()
	 {
	  return this.up_unit_cd;
	 }
	 //  up_tanano_choiceに対するセッターとゲッターの集合
	 public boolean setUpTananoChoice(String up_tanano_choice)
	 {
	  this.up_tanano_choice = up_tanano_choice;
	  return true;
	 }
	 public String getUpTananoChoice()
	 {
	  return this.up_tanano_choice;
	 }
//	  ↑↑2006.07.18 lixy カスタマイズ修正↑↑

	// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //
//	BUGNO-S052 2005.05.14 Sirius START
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

//		↓↓2006.07.14 lixy カスタマイズ修正↓↓
	   public boolean setGamenKb(String gamen_kb)
	    {
	        this.gamen_kb = gamen_kb;
	        return true;
	    }
	    public String getGamenKb()
	    {
	        return cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH);
	    }
	    public String getGamenKbString()
	    {
	        return "'" + cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH) + "'";
	    }
	    public String getGamenKbHTMLString()
	    {
	        return HTMLStringUtil.convert(cutString(this.gamen_kb,GAMEN_KB_MAX_LENGTH));
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
					byte bt[] = base.substring(pos,pos+1).getBytes("UTF-8");
					count += bt.length;
					if( count > max )
						break;
					wk += base.substring(pos,pos+1);
				}
				catch(Exception e)
				{
					stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				}
			}
			return wk;
		}
//		↑↑2006.07.14 lixy カスタマイズ修正↑↑


//		 ADD by Tanigawa 2006/9/28 障害票№0086対応 1日の処理対象上限件数を越えた場合に、処理を翌日に回す START
		/**
		 * procNum を戻します。
		 * @return procNum
		 */
		public int getProcNum() {
			return this.procNum;
		}

		/**
		 * procNum を設定
		 * @param procNum
		 */
		public void setProcNum(int procNum) {
			this.procNum = procNum;
		}
		/**
		 * procMaxNum を戻します。
		 * @return procMaxNum
		 */
		public int getProcMaxNum() {
			return this.procMaxNum;
		}
		/**
		 * procMaxNum を設定
		 * @param procMaxNum
		 */
		public void setProcMaxNum(int procMaxNum) {
			this.procMaxNum = procMaxNum;
		}
		/**
		 * procRegisteredNum を戻します。
		 * @return procRegisteredNum
		 */
		public int getProcRegisteredNum() {
			return this.procRegisteredNum;
		}
		/**
		 * procRegisteredNum を設定
		 * @param procRegisteredNum
		 */
		public void setProcRegisteredNum(int procRegisteredNum) {
			this.procRegisteredNum = procRegisteredNum;
		}
		/**
		 * torokuTs を戻します。
		 * @return torokuTs
		 */
		public String getTorokuTs() {
			return this.torokuTs;
		}
		/**
		 * torokuTs を設定
		 * @param torokuTs
		 */
		public void setTorokuTs(String torokuTs) {
			this.torokuTs = torokuTs;
		}
//		 ADD by Tanigawa 2006/9/28 障害票№0086対応 1日の処理対象上限件数を越えた場合に、処理を翌日に回す  END

//		 ADD by Tanigawa 2006/11/14 障害票№0143対応 START
		/**
		 * isSiiresakiSyohinCdCBChecked を戻します。
		 * @return isSiiresakiSyohinCdCBChecked
		 */
		public boolean isSiiresakiSyohinCdCBChecked() {
			return isSiiresakiSyohinCdCBChecked;
		}
		/**
		 * isSiiresakiSyohinCdCBChecked を設定
		 * @param isSiiresakiSyohinCdCBChecked
		 */
		public void setSiiresakiSyohinCdCBChecked(boolean isSiiresakiSyohinCdCBChecked) {
			this.isSiiresakiSyohinCdCBChecked = isSiiresakiSyohinCdCBChecked;
		}
//		 ADD by Tanigawa 2006/11/14 障害票№0143対応  END
//		 ADD by Tanigawa 2006/11/16 障害票№XXXX対応 START
		/**
		 * scrollPos を戻します。
		 * @return scrollPos
		 */
		public int getScrollPos() {
			return scrollPos;
		}
		/**
		 * scrollPos を設定
		 * @param scrollPos
		 */
		public void setScrollPos(int scrollPos) {
			this.scrollPos = scrollPos;
		}
		/**
		 * scrollPos を設定
		 * @param scrollPos
		 */
		public void setScrollPos(String scrollPos) {

			int i = 0;
			try {
				i = Integer.parseInt(scrollPos);
			} catch (NumberFormatException e) {
				i = 0;
			}

			this.scrollPos = i;
		}
//		 ADD by Tanigawa 2006/11/16 障害票№XXXX対応  END

//	  ↓↓2006.07.11 lixy カスタマイズ修正↓↓
//	  /**
//	   * 権限エラー画面URL取得(ログ出力有り)
//	   * <br>
//	   * Ex)<br>
//	   * getKengenErr("logHeader") -&gt; String<br>
//	   * <br>
//	   * @param String logHeader
//	   * @return		String
//	   */
//	  public String getKengenErr(String logHeader)
//	  {
//		  stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
//		  return KENGEN_PAGE;
//	  }
////	BUGNO-S052 2005.05.14 Sirius END
//
//	/**
//	 * 業種取得用のゲッターとセッター
//	 */
//	public void setGyosyuId(String str) {
//		gyosyuid = str;
//
//	}
//
//	public String getGyosyuId() {
//		return this.gyosyuid;
//	}
//  ↑↑2006.07.16 lixy カスタマイズ修正↑↑
}
