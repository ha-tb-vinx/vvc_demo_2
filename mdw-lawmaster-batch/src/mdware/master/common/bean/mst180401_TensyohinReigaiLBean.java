/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/23)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/23)初版作成
 */
public class mst180401_TensyohinReigaiLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SENTAKU_MAX_LENGTH = 1;//選択の長さ
//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	public static final int BUMON_NA_MAX_LENGTH = 80;//部門名の長さ
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 320;//漢字品名の長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int TENPO_NA_MAX_LENGTH = 80;//店舗名の長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH = 8;//店舗発注開始日の長さ
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH = 8;//店舗発注終了日の長さ
	public static final int GENTANKA_VL_EN_MAX_LENGTH = 7;//原価単価（円）の長さ
	public static final int GENTANKA_VL_SEN_MAX_LENGTH = 2;//原価単価（銭）の長さ
	public static final int EOS_KB_MAX_LENGTH = 1;//EOS区分の長さ
	public static final int EOS_NA_MAX_LENGTH = 80;//EOS区分名の長さ
//	↓↓2006.07.05 xiongjun カスタマイズ修正↓↓
//	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
//	↑↑2006.07.05 xiongjun カスタマイズ修正↑↑
	public static final int SIIRESAKI_NA_MAX_LENGTH = 80;//仕入先名の長さ
	public static final int BUTURYU_KB_MAX_LENGTH = 1;//物流区分の長さ
	public static final int BUTURYU_NA_MAX_LENGTH = 80;//物流区分名の長さ
	public static final int TEN_ZAIKO_KB_MAX_LENGTH = 1;//店在庫区分の長さ
	public static final int TEN_ZAIKO_NA_MAX_LENGTH = 80;//店在庫区分名の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_NA_MAX_LENGTH = 20;//作成者社員名の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_NA_MAX_LENGTH = 20;//更新者社員名の長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	public static final int BAITANKA_VL_STR_MAX_LENGTH = 9;//売価単価(税込)（表示用）の長さ
	public static final int GENTANKA_VL_STR_MAX_LENGTH = 11;//原価単価(税込)（表示用）の長さ

	public static final int MAX_HACHUTANI_QT_STR_MAX_LENGTH = 7;//最大発注単位（表示用）の長さ
	//生鮮用 Start
	public static final int HACHU_PATTERN_1_KB_MAX_LENGTH = 1;//①発注パターン区分の長さ
	public static final int HACHU_PATTERN_1_NA_MAX_LENGTH = 20;//①発注パターン名の長さ
	public static final int HACHU_PATTERN_2_KB_MAX_LENGTH = 1;//②発注パターン区分の長さ
	public static final int HACHU_PATTERN_2_NA_MAX_LENGTH = 20;//②発注パターン名の長さ
	public static final int BIN_1_KB_MAX_LENGTH = 1;//①便区分の長さ
	public static final int BIN_1_NA_MAX_LENGTH = 4;//①便名の長さ
	public static final int BIN_2_KB_MAX_LENGTH = 1;//②便区分の長さ
	public static final int BIN_2_NA_MAX_LENGTH = 4;//②便名の長さ
	//↓↓2006.07.03 chencl カスタマイズ 修正↓↓
	public static final int YUSEN_BIN_KB_MAX_LENGTH = 1;//優先便区分の長さ
	public static final int YUSEN_BIN_NA_MAX_LENGTH = 4;//優先便区分名の長さ
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
	//生鮮用 End
//	↓↓仕様追加による変更（2005.08.04）↓↓
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;//新規登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;//変更日の長さ
//	↑↑仕様追加による変更（2005.08.04）↑↑

	private String sentaku = null;	//選択
//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	private String hanku_cd = null;	//販区コード
	private String bumon_cd = null;	//部門コード
	private String bumon_na = null;	//部門名
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	private String syohin_cd = null;	//商品コード
	private String hinmei_kanji_na = null;	//漢字品名
	private String tenpo_cd = null;	//店舗コード (FK)
	private String tenpo_na = null;	//店舗名
	private String yuko_dt = null;	//有効日
	private String ten_hachu_st_dt = null;	//店舗発注開始日
	private String ten_hachu_ed_dt = null;	//店舗発注終了日
	private double gentanka_vl = 0;	//原価単価
	private String gentanka_vl_en = null;	//原価単価（円）
	private String gentanka_vl_sen = null;	//原価単価（銭）
	private long baitanka_vl = 0;	//売価単価(税込)
	private double max_hachutani_qt = 0;	//最大発注単位
	private String eos_kb = null;	//EOS区分
	private String eos_na = null;	//EOS区分名
	private String siiresaki_cd = null;	//仕入先コード
	private String siiresaki_na = null;	//仕入先名
//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	private String area_cd = null;	//地区コード
//	private String area_na = null;	//地区名
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	private String buturyu_kb = null;	//物流区分
	private String buturyu_na = null;	//物流区分名
	private String ten_zaiko_kb = null;	//店在庫区分
	private String ten_zaiko_na = null;	//店在庫区分名
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String insert_user_na = null;	//作成者社員名
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String update_user_na = null;	//更新者社員名
	private String delete_fg = null;	//削除フラグ
	private String gentanka_vl_str = null;	//原価単価(税込)（表示用）
	private String baitanka_vl_str = null;	//売価単価(税込)（表示用）


	private String max_hachutani_qt_str = null;	//最大発注単位（表示用）
	//生鮮用 Start
//BUGNO-S075 2005.07.20 Sirius START
//	private long hontai_vl = 0;	//本体売価（税抜）
	private String hontai_vl = null;	//本体売価（税抜）
//BUGNO-S075 2005.07.20 Sirius END
	private String hachu_pattern_1_kb = null;	//①発注パターン区分
	private String hachu_pattern_1_na = null;	//①発注パターン名
	private String hachu_pattern_2_kb = null;	//②発注パターン区分
	private String hachu_pattern_2_na = null;	//②発注パターン名
	private String bin_1_kb = null;	//①便区分
	private String bin_1_na = null;	//①便名
	private String bin_2_kb = null;	//②便区分
	private String bin_2_na = null;	//②便名
	//↓↓2006.07.03 chencl カスタマイズ修正↓↓
	private String yusen_bin_kb = null;	//優先便区分
	private String yusen_bin_na = null;	//優先便区分名
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
	//生鮮用 End
//	↓↓仕様追加による変更（2005.08.04）↓↓
	private String sinki_toroku_dt = null;//新規登録日
	private String henko_dt = null;	//変更日
//	↑↑仕様追加による変更（2005.08.04）↑↑

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

	/**
	 * mst180000_TensyohinReigaiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst180401_TensyohinReigaiLBean getmst180000_TensyohinReigaiBean(DataHolder dataHolder)
	{
		mst180401_TensyohinReigaiLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst180401_TensyohinReigaiLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst180401_TensyohinReigaiLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}

//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
	// bumon_cdに対するセッターとゲッターの集合
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

	// bumon_naに対するセッターとゲッターの集合
	public boolean setBumonNa(String bumon_na)
	{
		this.bumon_na = bumon_na;
		return true;
	}
	public String getBumonNa()
	{
		return cutString(this.bumon_na,BUMON_NA_MAX_LENGTH);
	}
	public String getBumonNaString()
	{
		return "'" + cutString(this.bumon_na,BUMON_NA_MAX_LENGTH) + "'";
	}
	public String getBumonNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_na,BUMON_NA_MAX_LENGTH));
	}
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}


	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmeiKanjiNa()
	{
		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getHinmeiKanjiNaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
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


	// tenpo_naに対するセッターとゲッターの集合
	public boolean setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
		return true;
	}
	public String getTenpoNa()
	{
		return cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		return "'" + cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
	}
	public String getTenpoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH));
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


	// ten_hachu_st_dtに対するセッターとゲッターの集合
	public boolean setTenHachuStDt(String ten_hachu_st_dt)
	{
		this.ten_hachu_st_dt = ten_hachu_st_dt;
		return true;
	}
	public String getTenHachuStDt()
	{
		return cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH);
	}
	public String getTenHachuStDtString()
	{
		return "'" + cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getTenHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_st_dt,TEN_HACHU_ST_DT_MAX_LENGTH));
	}


	// ten_hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setTenHachuEdDt(String ten_hachu_ed_dt)
	{
		this.ten_hachu_ed_dt = ten_hachu_ed_dt;
		return true;
	}
	public String getTenHachuEdDt()
	{
		return cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH);
	}
	public String getTenHachuEdDtString()
	{
		return "'" + cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getTenHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hachu_ed_dt,TEN_HACHU_ED_DT_MAX_LENGTH));
	}


	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
		try
		{
			this.gentanka_vl = Double.parseDouble(gentanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGentankaVl(double gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
		return true;
	}
	public double getGentankaVl()
	{
		return this.gentanka_vl;
	}
	public String getGentankaVlString()
	{
		return Double.toString(this.gentanka_vl);
	}


	// gentanka_vl_enに対するセッターとゲッターの集合
	public boolean setGentankaVlEn(String gentanka_vl_en)
	{
		this.gentanka_vl_en = gentanka_vl_en;
		return true;
	}
	public String getGentankaVlEn()
	{
		return cutString(this.gentanka_vl_en,GENTANKA_VL_EN_MAX_LENGTH);
	}
	public String getGentankaVlEnString()
	{
		return "'" + cutString(this.gentanka_vl_en,GENTANKA_VL_EN_MAX_LENGTH) + "'";
	}
	public String getGentankaVlEnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_vl_en,GENTANKA_VL_EN_MAX_LENGTH));
	}


	// gentanka_vl_senに対するセッターとゲッターの集合
	public boolean setGentankaVlSen(String gentanka_vl_sen)
	{
		this.gentanka_vl_sen = gentanka_vl_sen;
		return true;
	}
	public String getGentankaVlSen()
	{
		return cutString(this.gentanka_vl_sen,GENTANKA_VL_SEN_MAX_LENGTH);
	}
	public String getGentankaVlSenString()
	{
		return "'" + cutString(this.gentanka_vl_sen,GENTANKA_VL_SEN_MAX_LENGTH) + "'";
	}
	public String getGentankaVlSenHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_vl_sen,GENTANKA_VL_SEN_MAX_LENGTH));
	}


	// baitanka_vlに対するセッターとゲッターの集合
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
	public boolean setBaitankaVl(long baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public long getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlString()
	{
		return Long.toString(this.baitanka_vl);
	}


	// max_hachutani_qtに対するセッターとゲッターの集合
	public boolean setMaxHachutaniQt(String max_hachutani_qt)
	{
		try
		{
			this.max_hachutani_qt = Double.parseDouble(max_hachutani_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxHachutaniQt(double max_hachutani_qt)
	{
		this.max_hachutani_qt = max_hachutani_qt;
		return true;
	}
	public double getMaxHachutaniQt()
	{
		return this.max_hachutani_qt;
	}
	public String getMaxHachutaniQtString()
	{
		return Double.toString(this.max_hachutani_qt);
	}


	// eos_kbに対するセッターとゲッターの集合
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = eos_kb;
		return true;
	}
	public String getEosKb()
	{
		return cutString(this.eos_kb,EOS_KB_MAX_LENGTH);
	}
	public String getEosKbString()
	{
		return "'" + cutString(this.eos_kb,EOS_KB_MAX_LENGTH) + "'";
	}
	public String getEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eos_kb,EOS_KB_MAX_LENGTH));
	}


	// eos_naに対するセッターとゲッターの集合
	public boolean setEosNa(String eos_na)
	{
		this.eos_na = eos_na;
		return true;
	}
	public String getEosNa()
	{
		return cutString(this.eos_na,EOS_NA_MAX_LENGTH);
	}
	public String getEosNaString()
	{
		return "'" + cutString(this.eos_na,EOS_NA_MAX_LENGTH) + "'";
	}
	public String getEosNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eos_na,EOS_NA_MAX_LENGTH));
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


	// siiresaki_naに対するセッターとゲッターの集合
	public boolean setSiiresakiNa(String siiresaki_na)
	{
		this.siiresaki_na = siiresaki_na;
		return true;
	}
	public String getSiiresakiNa()
	{
		return cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH);
	}
	public String getSiiresakiNaString()
	{
		return "'" + cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH));
	}

	// buturyu_kbに対するセッターとゲッターの集合
	public boolean setButuryuKb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
		return true;
	}
	public String getButuryuKb()
	{
		return cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH);
	}
	public String getButuryuKbString()
	{
		return "'" + cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH) + "'";
	}
	public String getButuryuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.buturyu_kb,BUTURYU_KB_MAX_LENGTH));
	}


	// buturyu_naに対するセッターとゲッターの集合
	public boolean setButuryuNa(String buturyu_na)
	{
		this.buturyu_na = buturyu_na;
		return true;
	}
	public String getButuryuNa()
	{
		return cutString(this.buturyu_na,BUTURYU_NA_MAX_LENGTH);
	}
	public String getButuryuNaString()
	{
		return "'" + cutString(this.buturyu_na,BUTURYU_NA_MAX_LENGTH) + "'";
	}
	public String getButuryuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.buturyu_na,BUTURYU_NA_MAX_LENGTH));
	}


	// ten_zaiko_kbに対するセッターとゲッターの集合
	public boolean setTenZaikoKb(String ten_zaiko_kb)
	{
		this.ten_zaiko_kb = ten_zaiko_kb;
		return true;
	}
	public String getTenZaikoKb()
	{
		return cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH);
	}
	public String getTenZaikoKbString()
	{
		return "'" + cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH) + "'";
	}
	public String getTenZaikoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_zaiko_kb,TEN_ZAIKO_KB_MAX_LENGTH));
	}


	// ten_zaiko_naに対するセッターとゲッターの集合
	public boolean setTenZaikoNa(String ten_zaiko_na)
	{
		this.ten_zaiko_na = ten_zaiko_na;
		return true;
	}
	public String getTenZaikoNa()
	{
		return cutString(this.ten_zaiko_na,TEN_ZAIKO_NA_MAX_LENGTH);
	}
	public String getTenZaikoNaString()
	{
		return "'" + cutString(this.ten_zaiko_na,TEN_ZAIKO_NA_MAX_LENGTH) + "'";
	}
	public String getTenZaikoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_zaiko_na,TEN_ZAIKO_NA_MAX_LENGTH));
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


	// insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
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


	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
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

	// baitanka_vl_strに対するセッターとゲッターの集合
	public boolean setBaitankaVlStr(String baitanka_vl_str)
	{
		this.baitanka_vl_str = baitanka_vl_str;
		return true;
	}
	public String getBaitankaVlStr()
	{
		return cutString(this.baitanka_vl_str,BAITANKA_VL_STR_MAX_LENGTH);
	}
	public String getBaitankaVlStrString()
	{
		return "'" + cutString(this.baitanka_vl_str,BAITANKA_VL_STR_MAX_LENGTH) + "'";
	}
	public String getBaitankaVlStrHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baitanka_vl_str,BAITANKA_VL_STR_MAX_LENGTH));
	}


	// gentankaVlStrに対するセッターとゲッターの集合
	public boolean setGentankaVlStr(String gentanka_vl_str)
	{
		this.gentanka_vl_str = gentanka_vl_str;
		return true;
	}
	public String getGentankaVlStr()
	{
		return cutString(this.gentanka_vl_str,GENTANKA_VL_STR_MAX_LENGTH);
	}
	public String getGentankaVlStrString()
	{
		return "'" + cutString(this.gentanka_vl_str,GENTANKA_VL_STR_MAX_LENGTH) + "'";
	}
	public String getGentankaVlStrHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gentanka_vl_str,GENTANKA_VL_STR_MAX_LENGTH));
	}


	// max_hachutani_qt_strに対するセッターとゲッターの集合
	public boolean setMaxHachutaniQtStr(String max_hachutani_qt_str)
	{
		this.max_hachutani_qt_str = max_hachutani_qt_str;
		return true;
	}
	public String getMaxHachutaniQtStr()
	{
		return cutString(this.max_hachutani_qt_str,MAX_HACHUTANI_QT_STR_MAX_LENGTH);
	}
	public String getMaxHachutaniQtStrString()
	{
		return "'" + cutString(this.max_hachutani_qt_str,MAX_HACHUTANI_QT_STR_MAX_LENGTH) + "'";
	}
	public String getMaxHachutaniQtStrHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.max_hachutani_qt_str,MAX_HACHUTANI_QT_STR_MAX_LENGTH));
	}

	// hontai_vlに対するセッターとゲッターの集合
	public boolean setHontaiVl(String hontai_vl)
	{
//BUGNO-S075 2005.07.20 Sirius START
//		try
//		{
//			this.hontai_vl = Long.parseLong(hontai_vl);
		this.hontai_vl = hontai_vl;
//		}
//		catch(Exception e)
//		{
//			return false;
//		}
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
//BUGNO-S075 2005.07.20 Sirius START
/*
	public boolean setHontaiVl(long hontai_vl)
	{
		this.hontai_vl = hontai_vl;
		return true;
	}
	public long getHontaiVl()
	{
		return this.hontai_vl;
	}
	*/
//BUGNO-S075 2005.07.20 Sirius END
	public String getHontaiVlString()
	{
//BUGNO-S075 2005.07.20 Sirius START
//		return Long.toString(this.hontai_vl);
//BUGNO-S075 2005.07.20 Sirius END
		return this.hontai_vl;
	}


	// hachu_pattern_1_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern1Kb(String hachu_pattern_1_kb)
	{
		this.hachu_pattern_1_kb = hachu_pattern_1_kb;
		return true;
	}
	public String getHachuPattern1Kb()
	{
		return cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH);
	}
	public String getHachuPattern1KbString()
	{
		return "'" + cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_1_kb,HACHU_PATTERN_1_KB_MAX_LENGTH));
	}


	// hachu_pattern_1_naに対するセッターとゲッターの集合
	public boolean setHachuPattern1Na(String hachu_pattern_1_na)
	{
		this.hachu_pattern_1_na = hachu_pattern_1_na;
		return true;
	}
	public String getHachuPattern1Na()
	{
		return cutString(this.hachu_pattern_1_na,HACHU_PATTERN_1_NA_MAX_LENGTH);
	}
	public String getHachuPattern1NaString()
	{
		return "'" + cutString(this.hachu_pattern_1_na,HACHU_PATTERN_1_NA_MAX_LENGTH) + "'";
	}
	public String getHachuPattern1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_1_na,HACHU_PATTERN_1_NA_MAX_LENGTH));
	}


	// hachu_pattern_2_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern2Kb(String hachu_pattern_2_kb)
	{
		this.hachu_pattern_2_kb = hachu_pattern_2_kb;
		return true;
	}
	public String getHachuPattern2Kb()
	{
		return cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH);
	}
	public String getHachuPattern2KbString()
	{
		return "'" + cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_2_kb,HACHU_PATTERN_2_KB_MAX_LENGTH));
	}


	// hachu_pattern_2_naに対するセッターとゲッターの集合
	public boolean setHachuPattern2Na(String hachu_pattern_2_na)
	{
		this.hachu_pattern_2_na = hachu_pattern_2_na;
		return true;
	}
	public String getHachuPattern2Na()
	{
		return cutString(this.hachu_pattern_2_na,HACHU_PATTERN_2_NA_MAX_LENGTH);
	}
	public String getHachuPattern2NaString()
	{
		return "'" + cutString(this.hachu_pattern_2_na,HACHU_PATTERN_2_NA_MAX_LENGTH) + "'";
	}
	public String getHachuPattern2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_2_na,HACHU_PATTERN_2_NA_MAX_LENGTH));
	}


	// bin_1_kbに対するセッターとゲッターの集合
	public boolean setBin1Kb(String bin_1_kb)
	{
		this.bin_1_kb = bin_1_kb;
		return true;
	}
	public String getBin1Kb()
	{
		return cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH);
	}
	public String getBin1KbString()
	{
		return "'" + cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH) + "'";
	}
	public String getBin1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_1_kb,BIN_1_KB_MAX_LENGTH));
	}


	// bin_1_naに対するセッターとゲッターの集合
	public boolean setBin1Na(String bin_1_na)
	{
		this.bin_1_na = bin_1_na;
		return true;
	}
	public String getBin1Na()
	{
		return cutString(this.bin_1_na,BIN_1_NA_MAX_LENGTH);
	}
	public String getBin1NaString()
	{
		return "'" + cutString(this.bin_1_na,BIN_1_NA_MAX_LENGTH) + "'";
	}
	public String getBin1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_1_na,BIN_1_NA_MAX_LENGTH));
	}


	// bin_2_kbに対するセッターとゲッターの集合
	public boolean setBin2Kb(String bin_2_kb)
	{
		this.bin_2_kb = bin_2_kb;
		return true;
	}
	public String getBin2Kb()
	{
		return cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH);
	}
	public String getBin2KbString()
	{
		return "'" + cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH) + "'";
	}
	public String getBin2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_2_kb,BIN_2_KB_MAX_LENGTH));
	}


	// bin_2_naに対するセッターとゲッターの集合
	public boolean setBin2Na(String bin_2_na)
	{
		this.bin_2_na = bin_2_na;
		return true;
	}
	public String getBin2Na()
	{
		return cutString(this.bin_2_na,BIN_2_NA_MAX_LENGTH);
	}
	public String getBin2NaString()
	{
		return "'" + cutString(this.bin_2_na,BIN_2_NA_MAX_LENGTH) + "'";
	}
	public String getBin2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_2_na,BIN_2_NA_MAX_LENGTH));
	}
//	 ↓↓2006.07.03 chencl カスタマイズ修正↓↓
	// yusen_bin_kbに対するセッターとゲッターの集合
	public boolean setYusenBinKb(String yusen_bin_kb)
	{
		this.yusen_bin_kb = yusen_bin_kb;
		return true;
	}
	public String getYusenBinKb()
	{
		return cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH);
	}
	public String getYusenBinKbString()
	{
		return "'" + cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH) + "'";
	}
	public String getYusenBinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yusen_bin_kb,YUSEN_BIN_KB_MAX_LENGTH));
	}

	// yusen_bin_naに対するセッターとゲッターの集合
	public boolean setYusenBinNa(String yusen_bin_na)
	{
		this.yusen_bin_na = yusen_bin_na;
		return true;
	}
	public String getYusenBinNa()
	{
		return cutString(this.yusen_bin_na,YUSEN_BIN_NA_MAX_LENGTH);
	}
	public String getYusenBinNaString()
	{
		return "'" + cutString(this.yusen_bin_na,YUSEN_BIN_NA_MAX_LENGTH) + "'";
	}
	public String getYusenBinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yusen_bin_na,YUSEN_BIN_NA_MAX_LENGTH));
	}
//↑↑2006.07.03 chencl カスタマイズ修正↑↑
//	↓↓仕様追加による変更（2005.08.04）↓↓
	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
		return true;
	}
	public String getSinkiTorokuDt()
	{
		return cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH);
	}
	public String getSinkiTorokuDtString()
	{
		return "'" + cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH) + "'";
	}
	public String getSinkiTorokuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH));
	}

	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt,HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt,HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt,HENKO_DT_MAX_LENGTH));
	}
//	↑↑仕様追加による変更（2005.08.04）↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  sentaku = " + getSentakuString()
//			↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//			+ "  hanku_cd = " + getHankuCdString()
			+ "  bumon_cd = " + getBumonCdString()
//			↑↑2006.07.03 yuxf カスタマイズ修正↑↑
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  ten_hachu_st_dt = " + getTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getTenHachuEdDtString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  gentanka_vl_en = " + getGentankaVlEnString()
			+ "  gentanka_vl_sen = " + getGentankaVlSenString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  max_hachutani_qt = " + getMaxHachutaniQtString()
			+ "  eos_kb = " + getEosKbString()
			+ "  eos_na = " + getEosNaString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  siiresaki_na = " + getSiiresakiNaString()
			+ "  buturyu_kb = " + getButuryuKbString()
			+ "  buturyu_na = " + getButuryuNaString()
			+ "  ten_zaiko_kb = " + getTenZaikoKbString()
			+ "  ten_zaiko_na = " + getTenZaikoNaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  insert_user_na = " + getInsertUserNaString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  update_user_na = " + getUpdateUserNaString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  baitanka_vl_str = " + getBaitankaVlStrString()
			+ "  max_hachutani_qt_str = " + getMaxHachutaniQtStrString()
			+ "  hontai_vl = " + getHontaiVlString()
			+ "  hachu_pattern_1_kb = " + getHachuPattern1KbString()
			+ "  hachu_pattern_1_na = " + getHachuPattern1NaString()
			+ "  hachu_pattern_2_kb = " + getHachuPattern2KbString()
			+ "  hachu_pattern_2_na = " + getHachuPattern2NaString()
			+ "  bin_1_kb = " + getBin1KbString()
			+ "  bin_1_na = " + getBin1NaString()
			+ "  bin_2_kb = " + getBin2KbString()
			+ "  bin_2_na = " + getBin2NaString()
			//↓↓2006.07.03 chencl カスタマイズ修正↓↓
			+ "  yusen_bin_kb = " + getYusenBinKbString()
			+ "  yusen_bin_na = " + getYusenBinNaString()
			//↑↑2006.07.03 chencl カスタマイズ修正↑↑
//			↓↓仕様追加による変更（2005.08.04）↓↓
			+ "  sinki_toroku_dt = " + getSinkiTorokuDtString()
			+ "  henko_dt = " + getHenkoDtString()
//			↑↑仕様追加による変更（2005.08.04）↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst180000_TensyohinReigaiBean コピー後のクラス
	 */
	public mst180401_TensyohinReigaiLBean createClone()
	{
		mst180401_TensyohinReigaiLBean bean = new mst180401_TensyohinReigaiLBean();
		bean.setSentaku(this.sentaku);
//		↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//		bean.setHankuCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
//		↑↑2006.07.03 yuxf カスタマイズ修正↑↑
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNa(this.tenpo_na);
		bean.setYukoDt(this.yuko_dt);
		bean.setTenHachuStDt(this.ten_hachu_st_dt);
		bean.setTenHachuEdDt(this.ten_hachu_ed_dt);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setGentankaVlEn(this.gentanka_vl_en);
		bean.setGentankaVlSen(this.gentanka_vl_sen);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setMaxHachutaniQt(this.max_hachutani_qt);
		bean.setEosKb(this.eos_kb);
		bean.setEosNa(this.eos_na);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiNa(this.siiresaki_na);
		bean.setButuryuKb(this.buturyu_kb);
		bean.setButuryuNa(this.buturyu_na);
		bean.setTenZaikoKb(this.ten_zaiko_kb);
		bean.setTenZaikoNa(this.ten_zaiko_na);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNa(this.insert_user_na);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateUserNa(this.update_user_na);
		bean.setDeleteFg(this.delete_fg);
		bean.setBaitankaVlStr(this.baitanka_vl_str);
		bean.setMaxHachutaniQtStr(this.max_hachutani_qt_str);
		bean.setHontaiVl(this.hontai_vl);
		bean.setHachuPattern1Kb(this.hachu_pattern_1_kb);
		bean.setHachuPattern1Na(this.hachu_pattern_1_na);
		bean.setHachuPattern2Kb(this.hachu_pattern_2_kb);
		bean.setHachuPattern2Na(this.hachu_pattern_2_na);
		bean.setBin1Kb(this.bin_1_kb);
		bean.setBin1Na(this.bin_1_na);
		bean.setBin2Kb(this.bin_2_kb);
		bean.setBin2Na(this.bin_2_na);
		//↓↓2006.07.03 chencl カスタマイズ修正↓↓
		bean.setYusenBinKb(this.yusen_bin_kb);
		bean.setYusenBinNa(this.yusen_bin_na);
		//↑↑2006.07.03 chencl カスタマイズ修正↑↑
//		↓↓仕様追加による変更（2005.08.04）↓↓
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
//		↑↑仕様追加による変更（2005.08.04）↑↑
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst180401_TensyohinReigaiLBean ) ) return false;
		return this.toString().equals( o.toString() );
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
