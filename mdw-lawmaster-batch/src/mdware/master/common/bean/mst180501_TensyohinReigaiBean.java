/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別商品例外マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別商品例外マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/25)初版作成
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
 * @version 1.0(2005/03/25)初版作成
 */
public class mst180501_TensyohinReigaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	//↓↓2006.07.03 chencl カスタマイズ修正↓↓
	public static final int TENPO_NA_MAX_LENGTH = 80;//店舗名長さ
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH = 8;//店舗発注開始日の長さ
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH = 8;//店舗発注終了日の長さ
	public static final int EOS_KB_MAX_LENGTH = 1;//EOS区分の長さ
	//↓↓2006.07.03 chencl カスタマイズ修正↓↓
	public static final int EOS_NA_MAX_LENGTH = 80;//EOS区分名称の長さ
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
//	↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
//	↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
	public static final int SIIRESAKI_NA_MAX_LENGTH = 80;//仕入先名称(漢字)の長さ
	public static final int TENBETU_HAISO_CD_MAX_LENGTH = 5;//店別配送先コードの長さ
	public static final int BIN_1_KB_MAX_LENGTH = 1;//①便区分の長さ
	public static final int HACHU_PATTERN_1_KB_MAX_LENGTH = 1;//①発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;//①センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_1_KB_MAX_LENGTH = 1;//①店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_1_KB_MAX_LENGTH = 1;//①店納品時間帯の長さ
	public static final int BIN_2_KB_MAX_LENGTH = 1;//②便区分の長さ
	public static final int HACHU_PATTERN_2_KB_MAX_LENGTH = 1;//②発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;//②センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_2_KB_MAX_LENGTH = 1;//②店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_2_KB_MAX_LENGTH = 1;//②店納品時間帯の長さ
	public static final int BIN_3_KB_MAX_LENGTH = 1;//③便区分の長さ
	public static final int HACHU_PATTERN_3_KB_MAX_LENGTH = 1;//③発注パターン区分の長さ
	public static final int C_NOHIN_RTIME_3_KB_MAX_LENGTH = 1;//③センタ納品リードタイムの長さ
	public static final int TEN_NOHIN_RTIME_3_KB_MAX_LENGTH = 1;//③店納品リードタイムの長さ
	public static final int TEN_NOHIN_TIME_3_KB_MAX_LENGTH = 1;//③店納品時間帯の長さ
	public static final int C_NOHIN_RTIME_KB_MAX_LENGTH = 1;//センタ納品リードタイムの長さ
	public static final int SYOHIN_KB_MAX_LENGTH = 1;//商品区分の長さ
	public static final int BUTURYU_KB_MAX_LENGTH = 1;//物流区分の長さ
	public static final int TEN_ZAIKO_KB_MAX_LENGTH = 1;//店在庫区分の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
//	↓↓仕様追加による変更（2005.06.29）↓↓
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;//新規登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;//変更日の長さ
//	↑↑仕様追加による変更（2005.06.29）↑↑
	//↓↓2006.07.03 chencl カスタマイズ ADD↓↓
	public static final int YUSEN_BIN_KB_MAX_LENGTH = 1;//優先便区分の長さ
	//↑↑2006.07.03 chencl カスタマイズADD↑↑
//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	private String hanku_cd = null;	//販区コード
	private String bumon_cd = null;	//部門コード
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	private String syohin_cd = null;	//商品コード
	private String tenpo_cd = null;	//店舗コード (FK)
	//↓↓2006.07.03 yuxf カスタマイズ修正↓↓
	private String tenpo_na = null;	//店舗名
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
	private String yuko_dt = null;	//有効日
	private String ten_hachu_st_dt = null;	//店舗発注開始日
	private String ten_hachu_ed_dt = null;	//店舗発注終了日
	private double gentanka_vl = 0;	//原価単価
	private double baitanka_vl = 0;	//売価単価(税込)
	private double max_hachutani_qt = 0;	//最大発注単位
	private String eos_kb = null;	//EOS区分
//	↓↓2006.07.03 chencl カスタマイズ修正↓↓
	private String eos_na = null;	//EOS区分名
//	↑↑2006.07.03 chencl カスタマイズ修正↑↑
	private String siiresaki_cd = null;	//仕入先コード
	private String siiresaki_na = null;	//仕入先名
//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//	private String area_cd = null;	//地区コード
//	private String area_na = null;	//地区名
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑
	private String tenbetu_haiso_cd = null;	//店別配送先コード
	private String bin_1_kb = null;	//①便区分
	private String hachu_pattern_1_kb = null;	//①発注パターン区分
	private double sime_time_1_qt = 0;	//①締め時間
	private String c_nohin_rtime_1_kb = null;	//①センタ納品リードタイム
	private String ten_nohin_rtime_1_kb = null;	//①店納品リードタイム
	private String ten_nohin_time_1_kb = null;	//①店納品時間帯
	private String bin_2_kb = null;	//②便区分
	private String hachu_pattern_2_kb = null;	//②発注パターン区分
	private double sime_time_2_qt = 0;	//②締め時間
	private String c_nohin_rtime_2_kb = null;	//②センタ納品リードタイム
	private String ten_nohin_rtime_2_kb = null;	//②店納品リードタイム
	private String ten_nohin_time_2_kb = null;	//②店納品時間帯
	private String bin_3_kb = null;	//③便区分
	private String hachu_pattern_3_kb = null;	//③発注パターン区分
	private double sime_time_3_qt = 0;	//③締め時間
	private String c_nohin_rtime_3_kb = null;	//③センタ納品リードタイム
	private String ten_nohin_rtime_3_kb = null;	//③店納品リードタイム
	private String ten_nohin_time_3_kb = null;	//③店納品時間帯
	private String c_nohin_rtime_kb = null;	//センタ納品リードタイム
	private String syohin_kb = null;	//商品区分
	private String buturyu_kb = null;	//物流区分
	private String ten_zaiko_kb = null;	//店在庫区分
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	//↓↓2006.07.03 chencl カスタマイズ修正↓↓
	private String yusen_bin_kb = null;	//優先便区分
	//↑↑2006.07.03 chencl カスタマイズ修正↑↑
//	↓↓仕様追加による変更（2005.06.29）↓↓
	private String sinki_toroku_dt = null;//新規登録日
	private String henko_dt = null;	//変更日
//	↑↑仕様追加による変更（2005.06.29）↑↑

//BUGNO-S075 2005.07.20 Sirius START
	private HashMap gamenFlg = new HashMap();	//画面使用フラグ
//BUGNO-S075 2005.07.20 Sirius END
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
	 * mst180501_TensyohinReigaiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst180501_TensyohinReigaiBean getmst180501_TensyohinReigaiBean(DataHolder dataHolder)
	{
		mst180501_TensyohinReigaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst180501_TensyohinReigaiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst180501_TensyohinReigaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


//	↓↓2006.07.03 yuxf カスタマイズ修正↓↓
	// bumon_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bumon_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//	↑↑2006.07.03 yuxf カスタマイズ修正↑↑


	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tenpo_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	//  tenpo_naに対するセッターとゲッターの集合
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("yuko_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_hachu_st_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_hachu_ed_dt","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gentanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setGentankaVl(double gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("gentanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		try
		{
			this.baitanka_vl = Double.parseDouble(baitanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("baitanka_vl","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setBaitankaVl(double baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("baitanka_vl","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlString()
	{
		return Double.toString(this.baitanka_vl);
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
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_hachutani_qt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setMaxHachutaniQt(double max_hachutani_qt)
	{
		this.max_hachutani_qt = max_hachutani_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("max_hachutani_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("eos_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("siiresaki_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
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

	// tenbetu_haiso_cdに対するセッターとゲッターの集合
	public boolean setTenbetuHaisoCd(String tenbetu_haiso_cd)
	{
		this.tenbetu_haiso_cd = tenbetu_haiso_cd;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("tenbetu_haiso_cd","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenbetuHaisoCd()
	{
		return cutString(this.tenbetu_haiso_cd,TENBETU_HAISO_CD_MAX_LENGTH);
	}
	public String getTenbetuHaisoCdString()
	{
		return "'" + cutString(this.tenbetu_haiso_cd,TENBETU_HAISO_CD_MAX_LENGTH) + "'";
	}
	public String getTenbetuHaisoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenbetu_haiso_cd,TENBETU_HAISO_CD_MAX_LENGTH));
	}


	// bin_1_kbに対するセッターとゲッターの集合
	public boolean setBin1Kb(String bin_1_kb)
	{
		this.bin_1_kb = bin_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// hachu_pattern_1_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern1Kb(String hachu_pattern_1_kb)
	{
		this.hachu_pattern_1_kb = hachu_pattern_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// sime_time_1_qtに対するセッターとゲッターの集合
	public boolean setSimeTime1Qt(String sime_time_1_qt)
	{
		try
		{
			this.sime_time_1_qt = Double.parseDouble(sime_time_1_qt);
		}
		catch(Exception e)
		{
			return false;
		}
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_1_qt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setSimeTime1Qt(double sime_time_1_qt)
	{
		this.sime_time_1_qt = sime_time_1_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_1_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getSimeTime1Qt()
	{
		return this.sime_time_1_qt;
	}
	public String getSimeTime1QtString()
	{
		return Double.toString(this.sime_time_1_qt);
	}


	// c_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime1Kb(String c_nohin_rtime_1_kb)
	{
		this.c_nohin_rtime_1_kb = c_nohin_rtime_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtime1Kb()
	{
		return cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getCNohinRtime1KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_1_kb,C_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}


	// ten_nohin_rtime_1_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime1Kb(String ten_nohin_rtime_1_kb)
	{
		this.ten_nohin_rtime_1_kb = ten_nohin_rtime_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime1Kb()
	{
		return cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime1KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_1_kb,TEN_NOHIN_RTIME_1_KB_MAX_LENGTH));
	}


	// ten_nohin_time_1_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime1Kb(String ten_nohin_time_1_kb)
	{
		this.ten_nohin_time_1_kb = ten_nohin_time_1_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_1_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime1Kb()
	{
		return cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH);
	}
	public String getTenNohinTime1KbString()
	{
		return "'" + cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime1KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_1_kb,TEN_NOHIN_TIME_1_KB_MAX_LENGTH));
	}


	// bin_2_kbに対するセッターとゲッターの集合
	public boolean setBin2Kb(String bin_2_kb)
	{
		this.bin_2_kb = bin_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//	 ↓↓2006.07.03 chencl カスタマイズ修正↓↓
	// yusen_bin_kbに対するセッターとゲッターの集合
	public boolean setYusenBinKb(String yusen_bin_kb)
	{
		this.yusen_bin_kb = yusen_bin_kb;
		//↓↓2006.07.03 chencl カスタマイズ修正↓↓
		gamenFlg.put("yusen_bin_kb","1");
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

	// hachu_pattern_2_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern2Kb(String hachu_pattern_2_kb)
	{
		this.hachu_pattern_2_kb = hachu_pattern_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// sime_time_2_qtに対するセッターとゲッターの集合
	public boolean setSimeTime2Qt(String sime_time_2_qt)
	{
		try
		{
			this.sime_time_2_qt = Double.parseDouble(sime_time_2_qt);
		}
		catch(Exception e)
		{
			return false;
		}
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_2_qt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setSimeTime2Qt(double sime_time_2_qt)
	{
		this.sime_time_2_qt = sime_time_2_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_2_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getSimeTime2Qt()
	{
		return this.sime_time_2_qt;
	}
	public String getSimeTime2QtString()
	{
		return Double.toString(this.sime_time_2_qt);
	}


	// c_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime2Kb(String c_nohin_rtime_2_kb)
	{
		this.c_nohin_rtime_2_kb = c_nohin_rtime_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtime2Kb()
	{
		return cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getCNohinRtime2KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_2_kb,C_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}


	// ten_nohin_rtime_2_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime2Kb(String ten_nohin_rtime_2_kb)
	{
		this.ten_nohin_rtime_2_kb = ten_nohin_rtime_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime2Kb()
	{
		return cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime2KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_2_kb,TEN_NOHIN_RTIME_2_KB_MAX_LENGTH));
	}


	// ten_nohin_time_2_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime2Kb(String ten_nohin_time_2_kb)
	{
		this.ten_nohin_time_2_kb = ten_nohin_time_2_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_2_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime2Kb()
	{
		return cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH);
	}
	public String getTenNohinTime2KbString()
	{
		return "'" + cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime2KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_2_kb,TEN_NOHIN_TIME_2_KB_MAX_LENGTH));
	}


	// bin_3_kbに対するセッターとゲッターの集合
	public boolean setBin3Kb(String bin_3_kb)
	{
		this.bin_3_kb = bin_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("bin_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getBin3Kb()
	{
		return cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH);
	}
	public String getBin3KbString()
	{
		return "'" + cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH) + "'";
	}
	public String getBin3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_3_kb,BIN_3_KB_MAX_LENGTH));
	}


	// hachu_pattern_3_kbに対するセッターとゲッターの集合
	public boolean setHachuPattern3Kb(String hachu_pattern_3_kb)
	{
		this.hachu_pattern_3_kb = hachu_pattern_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("hachu_pattern_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getHachuPattern3Kb()
	{
		return cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH);
	}
	public String getHachuPattern3KbString()
	{
		return "'" + cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH) + "'";
	}
	public String getHachuPattern3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_pattern_3_kb,HACHU_PATTERN_3_KB_MAX_LENGTH));
	}


	// sime_time_3_qtに対するセッターとゲッターの集合
	public boolean setSimeTime3Qt(String sime_time_3_qt)
	{
		try
		{
			this.sime_time_3_qt = Double.parseDouble(sime_time_3_qt);
		}
		catch(Exception e)
		{
			return false;
		}
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_3_qt","1");
//		BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public boolean setSimeTime3Qt(double sime_time_3_qt)
	{
		this.sime_time_3_qt = sime_time_3_qt;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sime_time_3_qt","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public double getSimeTime3Qt()
	{
		return this.sime_time_3_qt;
	}
	public String getSimeTime3QtString()
	{
		return Double.toString(this.sime_time_3_qt);
	}


	// c_nohin_rtime_3_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtime3Kb(String c_nohin_rtime_3_kb)
	{
		this.c_nohin_rtime_3_kb = c_nohin_rtime_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtime3Kb()
	{
		return cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH);
	}
	public String getCNohinRtime3KbString()
	{
		return "'" + cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_3_kb,C_NOHIN_RTIME_3_KB_MAX_LENGTH));
	}


	// ten_nohin_rtime_3_kbに対するセッターとゲッターの集合
	public boolean setTenNohinRtime3Kb(String ten_nohin_rtime_3_kb)
	{
		this.ten_nohin_rtime_3_kb = ten_nohin_rtime_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_rtime_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinRtime3Kb()
	{
		return cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH);
	}
	public String getTenNohinRtime3KbString()
	{
		return "'" + cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinRtime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_rtime_3_kb,TEN_NOHIN_RTIME_3_KB_MAX_LENGTH));
	}


	// ten_nohin_time_3_kbに対するセッターとゲッターの集合
	public boolean setTenNohinTime3Kb(String ten_nohin_time_3_kb)
	{
		this.ten_nohin_time_3_kb = ten_nohin_time_3_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_nohin_time_3_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getTenNohinTime3Kb()
	{
		return cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH);
	}
	public String getTenNohinTime3KbString()
	{
		return "'" + cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH) + "'";
	}
	public String getTenNohinTime3KbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_nohin_time_3_kb,TEN_NOHIN_TIME_3_KB_MAX_LENGTH));
	}


	// c_nohin_rtime_kbに対するセッターとゲッターの集合
	public boolean setCNohinRtimeKb(String c_nohin_rtime_kb)
	{
		this.c_nohin_rtime_kb = c_nohin_rtime_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("c_nohin_rtime_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getCNohinRtimeKb()
	{
		return cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH);
	}
	public String getCNohinRtimeKbString()
	{
		return "'" + cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH) + "'";
	}
	public String getCNohinRtimeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.c_nohin_rtime_kb,C_NOHIN_RTIME_KB_MAX_LENGTH));
	}


	// syohin_kbに対するセッターとゲッターの集合
	public boolean setSyohinKb(String syohin_kb)
	{
		this.syohin_kb = syohin_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("syohin_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
		return true;
	}
	public String getSyohinKb()
	{
		return cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH);
	}
	public String getSyohinKbString()
	{
		return "'" + cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kb,SYOHIN_KB_MAX_LENGTH));
	}


	// buturyu_kbに対するセッターとゲッターの集合
	public boolean setButuryuKb(String buturyu_kb)
	{
		this.buturyu_kb = buturyu_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("buturyu_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// ten_zaiko_kbに対するセッターとゲッターの集合
	public boolean setTenZaikoKb(String ten_zaiko_kb)
	{
		this.ten_zaiko_kb = ten_zaiko_kb;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("ten_zaiko_kb","1");
//BUGNO-S075 2005.07.20 Sirius END
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


	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("insert_ts","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("insert_user_id","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("update_ts","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("update_user_id","1");
//BUGNO-S075 2005.07.20 Sirius END
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
//BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("delete_fg","1");
//BUGNO-S075 2005.07.20 Sirius END
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


//	↓↓仕様追加による変更（2005.06.29）↓↓
	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("sinki_toroku_dt","1");
//		BUGNO-S075 2005.07.20 Sirius END
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
//		BUGNO-S075 2005.07.20 Sirius START
		gamenFlg.put("henko_dt","1");
//		BUGNO-S075 2005.07.20 Sirius END
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
//	↑↑仕様追加による変更（2005.06.29）↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//		↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//		return "  hanku_cd = " + getHankuCdString()
		return "  hanku_cd = " + getBumonCdString()
//		↑↑2006.07.03 yuxf カスタマイズ修正↑↑
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  ten_hachu_st_dt = " + getTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getTenHachuEdDtString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  max_hachutani_qt = " + getMaxHachutaniQtString()
			+ "  eos_kb = " + getEosKbString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  tenbetu_haiso_cd = " + getTenbetuHaisoCdString()
			+ "  bin_1_kb = " + getBin1KbString()
			+ "  hachu_pattern_1_kb = " + getHachuPattern1KbString()
			+ "  sime_time_1_qt = " + getSimeTime1QtString()
			+ "  c_nohin_rtime_1_kb = " + getCNohinRtime1KbString()
			+ "  ten_nohin_rtime_1_kb = " + getTenNohinRtime1KbString()
			+ "  ten_nohin_time_1_kb = " + getTenNohinTime1KbString()
			+ "  bin_2_kb = " + getBin2KbString()
			+ "  hachu_pattern_2_kb = " + getHachuPattern2KbString()
			+ "  sime_time_2_qt = " + getSimeTime2QtString()
			+ "  c_nohin_rtime_2_kb = " + getCNohinRtime2KbString()
			+ "  ten_nohin_rtime_2_kb = " + getTenNohinRtime2KbString()
			+ "  ten_nohin_time_2_kb = " + getTenNohinTime2KbString()
			+ "  bin_3_kb = " + getBin3KbString()
			+ "  hachu_pattern_3_kb = " + getHachuPattern3KbString()
			+ "  sime_time_3_qt = " + getSimeTime3QtString()
			+ "  c_nohin_rtime_3_kb = " + getCNohinRtime3KbString()
			+ "  ten_nohin_rtime_3_kb = " + getTenNohinRtime3KbString()
			+ "  ten_nohin_time_3_kb = " + getTenNohinTime3KbString()
			+ "  c_nohin_rtime_kb = " + getCNohinRtimeKbString()
			+ "  syohin_kb = " + getSyohinKbString()
			+ "  buturyu_kb = " + getButuryuKbString()
			+ "  ten_zaiko_kb = " + getTenZaikoKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
//			↓↓仕様追加による変更（2005.06.29）↓↓
			+ "  sinki_toroku_dt = " + getSinkiTorokuDtString()
			+ "  henko_dt = " + getHenkoDtString()
//			↑↑仕様追加による変更（2005.06.29）↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst180501_TensyohinReigaiBean コピー後のクラス
	 */
	public mst180501_TensyohinReigaiBean createClone()
	{
		mst180501_TensyohinReigaiBean bean = new mst180501_TensyohinReigaiBean();
//		↓↓2006.07.03 yuxf カスタマイズ修正↓↓
//		bean.setHankuCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
//		↑↑2006.07.03 yuxf カスタマイズ修正↑↑
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setTenHachuStDt(this.ten_hachu_st_dt);
		bean.setTenHachuEdDt(this.ten_hachu_ed_dt);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setMaxHachutaniQt(this.max_hachutani_qt);
		bean.setEosKb(this.eos_kb);
//		↓↓2006.07.03 yuxf カスタマイズ修正↓↓
		bean.setSiiresakiCd(this.siiresaki_cd);
//		↑↑2006.07.03 yuxf カスタマイズ修正↑↑
		bean.setTenbetuHaisoCd(this.tenbetu_haiso_cd);
		bean.setBin1Kb(this.bin_1_kb);
		bean.setHachuPattern1Kb(this.hachu_pattern_1_kb);
		bean.setSimeTime1Qt(this.sime_time_1_qt);
		bean.setCNohinRtime1Kb(this.c_nohin_rtime_1_kb);
		bean.setTenNohinRtime1Kb(this.ten_nohin_rtime_1_kb);
		bean.setTenNohinTime1Kb(this.ten_nohin_time_1_kb);
		bean.setBin2Kb(this.bin_2_kb);
		bean.setHachuPattern2Kb(this.hachu_pattern_2_kb);
		bean.setSimeTime2Qt(this.sime_time_2_qt);
		bean.setCNohinRtime2Kb(this.c_nohin_rtime_2_kb);
		bean.setTenNohinRtime2Kb(this.ten_nohin_rtime_2_kb);
		bean.setTenNohinTime2Kb(this.ten_nohin_time_2_kb);
		bean.setBin3Kb(this.bin_3_kb);
		bean.setHachuPattern3Kb(this.hachu_pattern_3_kb);
		bean.setSimeTime3Qt(this.sime_time_3_qt);
		bean.setCNohinRtime3Kb(this.c_nohin_rtime_3_kb);
		bean.setTenNohinRtime3Kb(this.ten_nohin_rtime_3_kb);
		bean.setTenNohinTime3Kb(this.ten_nohin_time_3_kb);
		bean.setCNohinRtimeKb(this.c_nohin_rtime_kb);
		bean.setSyohinKb(this.syohin_kb);
		bean.setButuryuKb(this.buturyu_kb);
		bean.setTenZaikoKb(this.ten_zaiko_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様追加による変更（2005.06.29）↓↓
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
//		↑↑仕様追加による変更（2005.06.29）↑↑
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
		if( !( o instanceof mst180501_TensyohinReigaiBean ) ) return false;
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

//BUGNO-S075 2005.07.20 Sirius START
	/**
	 * @return
	 */
	public HashMap getGamenFlg() {
		return gamenFlg;
	}

	/**
	 * @param map
	 */
	public void setGamenFlg(HashMap map) {
		gamenFlg = map;
	}
//BUGNO-S075 2005.07.20 Sirius END

}
