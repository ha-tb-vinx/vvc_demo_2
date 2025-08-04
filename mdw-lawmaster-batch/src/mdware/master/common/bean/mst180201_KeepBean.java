/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/22)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 S.Takahashi START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 S.Takahashi END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/22)初版作成
 */
public class mst180201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.07.14 xiongjun カスタマイズ修正↓↓
    public static final int SYSTEM_KB_MAX_LENGTH  = 1; //システム区分の長さ
    private String system_kb = null; //システム区分
//	↑↑2006.07.14 xiongjun カスタマイズ修正↑↑

//	↓↓2006.07.14 xuzf カスタマイズ修正↓↓
	private String gamen_kb = null;	//画面区分
    public static final int GAMEN_KB_MAX_LENGTH  = 1; //画面区分の長さ
//	↑↑2006.07.14 xuzf カスタマイズ修正↑↑

	private String bumon_cd = null;	//部門コード
	private String bumon_na = null;	// 部門品名
	private String syohin_cd = null;	//商品コード
	private String hinmei_kanji_na = null;	//漢字品名
	private String yuko_dt = null;	//有効日
	private String ten_hachu_st_dt = null;	//店舗発注開始日
	private String ten_hachu_ed_dt = null;	//店舗発注終了日
	private long gentanka_vl = 0;	//原価単価
	private long baitanka_vl = 0;	//売価単価(税込)
	private long max_hachutani_qt = 0;	//最大発注単位
	private String eos_kb = null;	//EOS区分
	private String eos_na = null;	//EOS区分名
	private String siiresaki_cd = null;	//仕入先コード
	private String kanji_rn = null;	//略式名称(漢字)
	private String buturyu_kb = null;	//物流区分
	private String buturyu_na = null;	//
	private String ten_zaiko_kb = null;	//店在庫区分
	private String ten_zaiko_na = null;	//
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String insert_user_na = null;	//
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String update_user_na = null;	//
	private String processingDivision = null;	//処理状況
	private String errorFlg = null;	//エラーフラグ
	private String errorMessage = null;	//エラーメッセージ
	private String[] menuBar = null;	//メニューバーアイテム
	private String mode = null;	//処理モード
	private String firstFocus = null;	//フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;	//新規処理利用可能区分
	private String updateFlg = null;	//更新処理利用可能区分
	private String deleteFlg = null;	//削除処理利用可能区分
	private String referenceFlg = null;	//照会処理利用可能区分
	private String csvFlg = null;	//CSV処理利用可能区分
	private String printFlg = null;	//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;	//現画面URL
	private String checkFlg = null;	//チェック処理判断
	private String existFlg = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色
	private List yukochklst = new ArrayList();
	private List updateprocesslst = new ArrayList();
//BUGNO-S005 2005.04.21 T.kikuchi START
	private String ChangeFlg = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
//BUGNO-S005 2005.04.21 T.kikuchi END

//BUGNO-S052 2005.05.14 S.Takahashi START
	private static final String INIT_PAGE   = "mst180101_TensyohinReigaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE   = "mst180201_TensyohinReigaiEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE   = "mst180301_TensyohinReigaiView";	// 照会・削除画面JSPを取得
	private static final String INIT_PAGEA5   = "mstA50101_TensyohinReigaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGEA5   = "mstA50201_TensyohinReigaiEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGEA5   = "mstA50301_TensyohinReigaiView";	// 照会・削除画面JSPを取得
	private static final String INIT_PAGE20   = "mst200101_TensyohinReigaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE20   = "mst200201_TensyohinReigaiEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE20   = "mst200301_TensyohinReigaiView";	// 照会・削除画面JSPを取得
	private static final String INIT_PAGE21   = "mst210101_TensyohinReigaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE21   = "mst210201_TensyohinReigaiEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE21   = "mst210301_TensyohinReigaiView";	// 照会・削除画面JSPを取得
	private static final String INIT_PAGE22 = "mst220101_TensyohinReigaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE22 = "mst220201_TensyohinReigaiEdit";	// 編集画面JSPを取得
	private static final String VIEW_PAGE22 = "mst220301_TensyohinReigaiView";	// 照会・削除画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 S.Takahashi END

//	↓↓2006.07.14 xiongjun カスタマイズ修正↓↓
	// system_kbに対するセッターとゲッターの集合
    public boolean setSystemKb(String system_kb)
    {
        this.system_kb = system_kb;
        return true;
    }
    public String getSystemKb()
    {
        return cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH);
    }
    public String getSystemKbString()
    {
        return "'" + cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH) + "'";
    }
    public String getSystemKbHTMLString()
    {
        return HTMLStringUtil.convert(cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH));
    }
//	↑↑2006.07.14 xiongjun カスタマイズ修正↑↑

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
 * 部門コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setBumonCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
/**
 * 部門コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBumonCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBumonCd()
	{
		return this.bumon_cd;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setBumonNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBumonNa(String bumon_na)
	{
		this.bumon_na = bumon_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBumonNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBumonNa()
	{
		return this.bumon_na;
	}


/**
 * 商品コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setSyohinCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
/**
 * 商品コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSyohinCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}


/**
 * 漢字品名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHinmeiKanjiNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
/**
 * 漢字品名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHinmeiKanjiNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHinmeiKanjiNa()
	{
		return this.hinmei_kanji_na;
	}


/**
 * 有効日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setYukoDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
/**
 * 有効日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getYukoDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getYukoDt()
	{
		return this.yuko_dt;
	}


/**
 * 店舗発注開始日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenHachuStDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenHachuStDt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = ten_hachu_st_dt;
		return true;
	}
/**
 * 店舗発注開始日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenHachuStDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenHachuStDt()
	{
		return this.ten_hachu_st_dt;
	}


/**
 * 店舗発注終了日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenHachuEdDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenHachuEdDt(String ten_hachu_ed_dt)
	{
		this.ten_hachu_ed_dt = ten_hachu_ed_dt;
		return true;
	}
/**
 * 店舗発注終了日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenHachuEdDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenHachuEdDt()
	{
		return this.ten_hachu_ed_dt;
	}


/**
 * 原価単価に対するセッター<br>
 * <br>
 * Ex)<br>
 * setGentankaVl("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setGentankaVl(String gentanka_vl)
	{
		try
		{
			this.gentanka_vl = Long.parseLong(gentanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 原価単価に対するセッター<br>
 * <br>
 * Ex)<br>
 * setGentankaVl("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setGentankaVl(long gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
		return true;
	}
	public long getGentankaVl()
	{
		return this.gentanka_vl;
	}
/**
 * 原価単価に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getGentankaVl();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getGentankaVlString()
	{
		return Long.toString(this.gentanka_vl);
	}


/**
 * 売価単価(税込)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setBaitankaVl("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBaitankaVl(String baitanka_vl)
	{
		try
		{
			this.baitanka_vl = Long.parseLong(baitanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 売価単価(税込)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setBaitankaVl("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setBaitankaVl(long baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public long getBaitankaVl()
	{
		return this.baitanka_vl;
	}
/**
 * 売価単価(税込)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBaitankaVl();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBaitankaVlString()
	{
		return Long.toString(this.baitanka_vl);
	}


/**
 * 最大発注単位に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMaxHachutaniQt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMaxHachutaniQt(String max_hachutani_qt)
	{
		try
		{
			this.max_hachutani_qt = Long.parseLong(max_hachutani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 最大発注単位に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMaxHachutaniQt("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setMaxHachutaniQt(long max_hachutani_qt)
	{
		this.max_hachutani_qt = max_hachutani_qt;
		return true;
	}
	public long getMaxHachutaniQt()
	{
		return this.max_hachutani_qt;
	}
/**
 * 最大発注単位に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMaxHachutaniQt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMaxHachutaniQtString()
	{
		return Long.toString(this.max_hachutani_qt);
	}


/**
 * EOS区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setEosKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = eos_kb;
		return true;
	}
/**
 * EOS区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getEosKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getEosKb()
	{
		return this.eos_kb;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setEosNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setEosNa(String eos_na)
	{
		this.eos_na = eos_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getEosNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getEosNa()
	{
		return this.eos_na;
	}
/**
 * 仕入先コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setSiiresakiCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
/**
 * 仕入先コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSiiresakiCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSiiresakiCd()
	{
		return this.siiresaki_cd;
	}


/**
 * 略式名称(漢字)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanjiRn("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
/**
 * 略式名称(漢字)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanjiRn();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanjiRn()
	{
		return this.kanji_rn;
	}


/**
 * 物流区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setButuryuKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setButuryuKb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
		return true;
	}
/**
 * 物流区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getButuryuKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getButuryuKb()
	{
		return this.buturyu_kb;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setButuryuNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setButuryuNa(String buturyu_na)
	{
		this.buturyu_na = buturyu_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getButuryuNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getButuryuNa()
	{
		return this.buturyu_na;
	}


/**
 * 店在庫区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenZaikoKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenZaikoKb(String ten_zaiko_kb)
	{
		this.ten_zaiko_kb = ten_zaiko_kb;
		return true;
	}
/**
 * 店在庫区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenZaikoKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenZaikoKb()
	{
		return this.ten_zaiko_kb;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenZaikoNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenZaikoNa(String ten_zaiko_na)
	{
		this.ten_zaiko_na = ten_zaiko_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenZaikoNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenZaikoNa()
	{
		return this.ten_zaiko_na;
	}


/**
 * 作成年月日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertTs("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
/**
 * 作成年月日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertTs();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertTs()
	{
		return this.insert_ts;
	}


/**
 * 作成者社員IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertUserId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
/**
 * 作成者社員IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertUserId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertUserId()
	{
		return this.insert_user_id;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertUserNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertUserNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertUserNa()
	{
		return this.insert_user_na;
	}


/**
 * 更新年月日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateTs("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
/**
 * 更新年月日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateTs();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateTs()
	{
		return this.update_ts;
	}


/**
 * 更新者社員IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateUserId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
/**
 * 更新者社員IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateUserId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateUserId()
	{
		return this.update_user_id;
	}


/**
 * に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateUserNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
/**
 * に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateUserNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateUserNa()
	{
		return this.update_user_na;
	}


/**
 * 処理状況に対するセッター<br>
 * <br>
 * Ex)<br>
 * setProcessingDivision("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setProcessingDivision(String processingDivision)
	{
		this.processingDivision = processingDivision;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getProcessingDivision();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getProcessingDivision()
	{
		return this.processingDivision;
	}


/**
 * エラーフラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorFlg(String errorFlg)
	{
		this.errorFlg = errorFlg;
		return true;
	}
/**
 * エラーフラグに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorFlg()
	{
		return this.errorFlg;
	}


/**
 * エラーメッセージに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorMessage("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return true;
	}
/**
 * エラーメッセージに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorMessage();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorMessage()
	{
		return this.errorMessage;
	}


/**
 * メニューバーアイテムに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMenuBar("String[]");<br>
 * <br>
 * @param String[] 設定する文字配列
 */
	public boolean setMenuBar(String[] menuBar)
	{
		try
		{
			this.menuBar = menuBar;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * メニューバーアイテムに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMenuBar();　戻り値　文字配列<br>
 * <br>
 * @return String[] 文字配列
 */
	public String[] getMenuBar()
	{
		return this.menuBar;
	}


/**
 * 処理モードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMode(String mode)
	{
		this.mode = mode;
		return true;
	}
/**
 * 処理モードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMode();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMode()
	{
		return this.mode;
	}


/**
 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFirstFocus("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFirstFocus(String firstFocus)
	{
		this.firstFocus = firstFocus;
		return true;
	}
/**
 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFirstFocus();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFirstFocus()
	{
		return this.firstFocus;
	}


/**
 * 新規処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertFlg(String insertFlg)
	{
		this.insertFlg = insertFlg;
		return true;
	}
/**
 * 新規処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertFlg()
	{
		return this.insertFlg;
	}


/**
 * 更新処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateFlg(String updateFlg)
	{
		this.updateFlg = updateFlg;
		return true;
	}
/**
 * 更新処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateFlg()
	{
		return this.updateFlg;
	}


/**
 * 削除処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFlg(String deleteFlg)
	{
		this.deleteFlg = deleteFlg;
		return true;
	}
/**
 * 削除処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDeleteFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDeleteFlg()
	{
		return this.deleteFlg;
	}


/**
 * 照会処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setReferenceFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setReferenceFlg(String referenceFlg)
	{
		this.referenceFlg = referenceFlg;
		return true;
	}
/**
 * 照会処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getReferenceFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getReferenceFlg()
	{
		return this.referenceFlg;
	}


/**
 * CSV処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCsvFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCsvFlg(String csvFlg)
	{
		this.csvFlg = csvFlg;
		return true;
	}
/**
 * CSV処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCsvFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCsvFlg()
	{
		return this.csvFlg;
	}


/**
 * 印刷処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setPrintFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setPrintFlg(String printFlg)
	{
		this.printFlg = printFlg;
		return true;
	}
/**
 * 印刷処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getPrintFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getPrintFlg()
	{
		return this.printFlg;
	}


/**
 * 前画面IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setBeforeDispId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBeforeDispId(String before_disp_id)
	{
		this.before_disp_id = before_disp_id;
		return true;
	}
/**
 * 前画面IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBeforeDispId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBeforeDispId()
	{
		return this.before_disp_id;
	}


/**
 * 現画面URLに対するセッター<br>
 * <br>
 * Ex)<br>
 * setDispUrl("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDispUrl(String disp_url)
	{
		this.disp_url = disp_url;
		return true;
	}
/**
 * 現画面URLに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDispUrl();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDispUrl()
	{
		return this.disp_url;
	}


/**
 * チェック処理判断に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCheckFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCheckFlg(String checkFlg)
	{
		this.checkFlg = checkFlg;
		return true;
	}
/**
 * チェック処理判断に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCheckFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCheckFlg()
	{
		return this.checkFlg;
	}


/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setExistFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setExistFlg(String existFlg)
	{
		this.existFlg = existFlg;
		return true;
	}
/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getExistFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getExistFlg()
	{
		return this.existFlg;
	}


/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSearchErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSearchErrorFlg(String searchErrorFlg)
	{
		this.searchErrorFlg = searchErrorFlg;
		return true;
	}
/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSearchErrorFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSearchErrorFlg()
	{
		return this.searchErrorFlg;
	}


/**
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateProcessFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateProcessFlg(String updateProcessFlg)
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
/**
 * 更新処理内容に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateProcessFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
	}


/**
 * コントロール背景色に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCtrlColor("Map");<br>
 * <br>
 * @param Map 設定するMap配列
 */
	public boolean setCtrlColor(Map ctrlColor)
	{
		try
		{
			this.ctrlColor = ctrlColor;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * コントロール背景色に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCtrlColor();　戻り値　Map配列<br>
 * <br>
 * @return String[] Map配列
 */
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}

	/**
	 * 有効日情報配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYukoChkLst();　戻り値　有効日情報配列<br>
	 * <br>
	 * @return List 有効日情報配列
	 */
	public List getYukoChkLst() {
		return yukochklst;
	}

	/**
	 * 有効日情報配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoChkLst(List);<br>
	 * <br>
	 * @param List 設定する有効日情報配列
	 */
	public void setYukoChkLst(List list) {
		yukochklst = list;
	}

	/**
	 * 更新処理内容配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　更新処理内容配列<br>
	 * <br>
	 * @return List 更新処理内容配列
	 */
	public List getUpdateProcessLst() {
		return updateprocesslst;
	}

	/**
	 * 更新処理内容配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setYukoChkLst(List);<br>
	 * <br>
	 * @param List 設定する更新処理内容配列
	 */
	public void setUpdateProcessLst(List list) {
		updateprocesslst = list;
	}

//BUGNO-S005 2005.04.21 T.kikuchi START
    /**
     * 内容変更flgに対するゲッター<br>
     * <br>
     * Ex)<br>
     * getChangeFlg();　戻り値　文字列<br>
     * <br>
     * @return String 文字列
     */
	public String getChangeFlg() {
		return ChangeFlg;
	}
	/**
 	 * 内容変更flgに対するセッター<br>
 	 * <br>
 	 * Ex)<br>
 	 * setChangeFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */

	public void setChangeFlg(String string) {
		ChangeFlg = string;
	}
//BUGNO-S005 2005.04.21 T.kikuchi START


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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
	 * 初期画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	  public String getInitUrlA5(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	INIT_PAGEA5;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrlA5()
	  {
		  return	INIT_PAGEA5;
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
	  public String getEditUrlA5(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	EDIT_PAGEA5;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrlA5()
	  {
		  return	EDIT_PAGEA5;
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
	  public String getViewUrlA5(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	VIEW_PAGEA5;
	  }
	  /**
	   * 照会画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getViewUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getViewUrlA5()
	  {
		  return	VIEW_PAGEA5;
	  }
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
	  public String getInitUrl20(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	INIT_PAGE20;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrl20()
	  {
		  return	INIT_PAGE20;
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
	  public String getEditUrl20(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	EDIT_PAGE20;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrl20()
	  {
		  return	EDIT_PAGE20;
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
	  public String getViewUrl20(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	VIEW_PAGE20;
	  }
	  /**
	   * 照会画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getViewUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getViewUrl20()
	  {
		  return	VIEW_PAGE20;
	  }
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
	  public String getInitUrl21(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	INIT_PAGE21;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrl21()
	  {
		  return	INIT_PAGE21;
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
	  public String getEditUrl21(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	EDIT_PAGE21;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrl21()
	  {
		  return	EDIT_PAGE21;
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
	  public String getViewUrl21(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	VIEW_PAGE21;
	  }
	  /**
	   * 照会画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getViewUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getViewUrl21()
	  {
		  return	VIEW_PAGE21;
	  }

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
	  public String getInitUrl22(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	INIT_PAGE22;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrl22()
	  {
		  return	INIT_PAGE22;
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
	  public String getEditUrl22(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	EDIT_PAGE22;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrl22()
	  {
		  return	EDIT_PAGE22;
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
	  public String getViewUrl22(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorFlg.equals("")){
			  //通常系
			  if(!this.errorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	VIEW_PAGE22;
	  }
	  /**
	   * 照会画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getViewUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getViewUrl22()
	  {
		  return	VIEW_PAGE22;
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
//	↓↓2006.07.14 xiongjun カスタマイズ修正↓↓
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
//	↑↑2006.07.14 xiongjun カスタマイズ修正↑↑
}
