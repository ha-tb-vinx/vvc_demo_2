/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
package mdware.master.common.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
public class mstA60101_HonbuTonyuLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

/**
 * あえてコンパイルが通らないようにしています。
 * 下のサイズをＤＢの項目のサイズに合わせてください。
 * 修正が終了してから、このメッセージを削除してください。
 */
	public static final int BUMON_CD_MAX_LENGTH = 4;
//	↓↓2006.09.05 baoql カスタマイズ修正↓↓	
	public static final int TENPO_CD_MAX_LENGTH = 6;
	public static final int FUJI_SYOHIN_KB_MAX_LENGTH = 1;
//	↑↑2006.09.05 baoql カスタマイズ修正↑↑	
	public static final int UNIT_CD_MAX_LENGTH = 4;
	public static final int UNIT_NM_MAX_LENGTH = 20;
	public static final int SYOHIN_KB_NM_MAX_LENGTH = 20;
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH = 15;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;
	public static final int SIIRESAKI_NM_MAX_LENGTH = 20;	
	public static final int TEN_HACHU_ST_DT_MAX_LENGTH = 8;
	public static final int TEN_HACHU_ED_DT_MAX_LENGTH = 8;
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;
	public static final int HANBAI_ED_DT_MAX_LENGTH = 8;	

//	↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
	public static final int COLOR_NA_MAX_LENGTH 			 = 80;	//略式名称漢字
	public static final int SIZE_NA_MAX_LENGTH 			 = 80;	//略式名称漢字
//	↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

	private String bumon_cd = null;
//	↓↓2006.09.05 baoql カスタマイズ修正↓↓	
	private String tenpo_cd = null;
	private String fuji_syohin_kb = null;
//	↑↑2006.09.05 baoql カスタマイズ修正↑↑
	private String unit_cd = null;
	private String unit_nm = null;
	private String syohin_kb_nm = null;
	private String siire_hinban_cd = null;
	private String syohin_cd = null;
	//===BEGIN=== Add by kou 2006/11/10
	private String syohinNm = null;
	private String nohinDt = null;
	//===END=== Add by kou 2006/11/10
	//private String area_cd = null;
	private String siiresaki_cd = null;
	private String siiresaki_nm = null;
	//private String area_nm = null;
	private double gentanka_vl = 0;
	private long suryo_qt = 0;
	private long baitanka_vl = 0;
	private String ten_hachu_st_dt = null;
	private String ten_hachu_ed_dt = null;
	private String hanbai_st_dt = null;
	private String hanbai_ed_dt = null;
	private double hachutani_irisu_qt = 0;
	private String[] tkanji_rnl = null;	//店舗略称配列
	private String[] suryo = null;	//店舗存在フラグ配列

//	↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
	private String color_na 			= null;
	private String size_na 			= null;
//	↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

//	↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
	private String insertTs = null;
//	↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

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
	 * TestBeanを１件のみ抽出したい時に使用する
	 */
	public static mstA60101_HonbuTonyuLBean getHonbuTonyuLBean(DataHolder dataHolder)
	{
		mstA60101_HonbuTonyuLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstA60101_HonbuTonyuLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstA60101_HonbuTonyuLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}

	/**
	 * @param tmpStr
	 * @param num
	 * @return
	 */
	private String insertBR(String tmpStr, int num)
	{
		tmpStr = tmpStr.trim();
		if(tmpStr.length() <= num) {
			return tmpStr;		
		} else {
			if(tmpStr.length() <= num*2 ) {
				return tmpStr.substring(0,num)+"<br/>"+tmpStr.substring(num);
			} else {
				return tmpStr.substring(0,num)+"<br/>"+tmpStr.substring(num,num*2);
			}
		}
	}
	
	// fuji_syohin_kbに対するセッターとゲッターの集合
	public boolean setFujiSyohinKb(String fuji_syohin_kb)
	{
		this.fuji_syohin_kb = fuji_syohin_kb;
		return true;
	}
	public String getFujiSyohinKb()
	{
		return cutString(this.fuji_syohin_kb,FUJI_SYOHIN_KB_MAX_LENGTH);
	}
	public String getFujiSyohinKbString()
	{
		return "'" + cutString(this.fuji_syohin_kb,FUJI_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getFujiSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.fuji_syohin_kb,FUJI_SYOHIN_KB_MAX_LENGTH));
	}

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

	// unit_nmに対するセッターとゲッターの集合
	public boolean setUnitNm(String unit_nm)
	{
		this.unit_nm = unit_nm;
		return true;
	}
	public String getUnitNm()
	{
		return cutString(this.unit_nm,UNIT_NM_MAX_LENGTH);
	}
	public String getUnitNmString()
	{
		return "'" + cutString(this.unit_nm,UNIT_NM_MAX_LENGTH) + "'";
	}
	public String getUnitNmHTMLString()
	{
		//===BEGIN=== Modify by kou 2006/11/10
		//return HTMLStringUtil.convert(cutString(this.unit_nm,UNIT_NM_MAX_LENGTH));
		String tmpStr = HTMLStringUtil.convert(cutString(this.unit_nm,UNIT_NM_MAX_LENGTH));
		return insertBR(tmpStr,6);
		//===END=== Modify by kou 2006/11/10
	}
	
	// syohin_kb_nmに対するセッターとゲッターの集合
	public boolean setSyohinKbNm(String syohin_kb_nm)
	{
		this.syohin_kb_nm = syohin_kb_nm;
		return true;
	}
	public String getSyohinKbNm()
	{
		return cutString(this.syohin_kb_nm,SYOHIN_KB_NM_MAX_LENGTH);
	}
	public String getSyohinKbNmString()
	{
		return "'" + cutString(this.syohin_kb_nm,SYOHIN_KB_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinKbNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kb_nm,SYOHIN_KB_NM_MAX_LENGTH));
	}


	// siire_hinban_cdに対するセッターとゲッターの集合
	public boolean setSiireHinbanCd(String siire_hinban_cd)
	{
		this.siire_hinban_cd = siire_hinban_cd;
		return true;
	}
	public String getSiireHinbanCd()
	{
		return cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getSiireHinbanCdString()
	{
		return "'" + cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getSiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH));
	}


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
		return HTMLStringUtil.convert(cutString(this.syohin_cd.trim(),SYOHIN_CD_MAX_LENGTH));
	}


	// area_cdに対するセッターとゲッターの集合
//	public boolean setAreaCd(String area_cd)
//	{
//		this.area_cd = area_cd;
//		return true;
//	}
//	public String getAreaCd()
//	{
//		return cutString(this.area_cd,AREA_CD_MAX_LENGTH);
//	}
//	public String getAreaCdString()
//	{
//		return "'" + cutString(this.area_cd,AREA_CD_MAX_LENGTH) + "'";
//	}
//	public String getAreaCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.area_cd,AREA_CD_MAX_LENGTH));
//	}


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
		return cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH);
	}
	public String getSiiresakiNmString()
	{
		return "'" + cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNmHTMLString()
	{
		//===BEGIN=== Modify by kou 2006/11/10
		//return HTMLStringUtil.convert(cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH));
		String tmpStr = HTMLStringUtil.convert(cutString(this.siiresaki_nm.trim(),SIIRESAKI_NM_MAX_LENGTH));
		return insertBR(tmpStr,8);
		//===END=== Modify by kou 2006/11/10
	}


	// area_nmに対するセッターとゲッターの集合
//	public boolean setAreaNm(String area_nm)
//	{
//		this.area_nm = area_nm;
//		return true;
//	}
//	public String getAreaNm()
//	{
//		return cutString(this.area_nm,AREA_NM_MAX_LENGTH);
//	}
//	public String getAreaNmString()
//	{
//		return "'" + cutString(this.area_nm,AREA_NM_MAX_LENGTH) + "'";
//	}
//	public String getAreaNmHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.area_nm,AREA_NM_MAX_LENGTH));
//	}


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


	// suryo_qtに対するセッターとゲッターの集合
	public boolean setSuryoQt(String suryo_qt)
	{
		try
		{
			this.suryo_qt = Long.parseLong(suryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSuryoQt(long suryo_qt)
	{
		this.suryo_qt = suryo_qt;
		return true;
	}
	public long getSuryoQt()
	{
		return this.suryo_qt;
	}
	public String getSuryoQtString()
	{
		return Long.toString(this.suryo_qt);
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

	//tkanji_rnlに対するセッターとゲッターの集合
	public String[] getTkanji_rnl() {
		return tkanji_rnl;
	}
	public void setTkanji_rnl(String[] tkanji_rnl) {
		this.tkanji_rnl = tkanji_rnl;
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
	
	public void setSuryo(String[] suryo) {
		this.suryo = suryo;
	}
	
	public String[] getSuryo() {
		return suryo;
	}


	// hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setHanbaiStDt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
		return true;
	}
	public String getHanbaiStDt()
	{
		return cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getHanbaiStDtString()
	{
		return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH));
	}


	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHanbaiEdDt(String hanbai_ed_dt)
	{
		this.hanbai_ed_dt = hanbai_ed_dt;
		return true;
	}
	public String getHanbaiEdDt()
	{
		return cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getHanbaiEdDtString()
	{
		return "'" + cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH));
	}


	// hachutani_irisu_qtに対するセッターとゲッターの集合
	public boolean setHachutaniIrisuQt(String hachutani_irisu_qt)
	{
		try
		{
			this.hachutani_irisu_qt = Double.parseDouble(hachutani_irisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHachutaniIrisuQt(double hachutani_irisu_qt)
	{
		this.hachutani_irisu_qt = hachutani_irisu_qt;
		return true;
	}
	public double getHachutaniIrisuQt()
	{
		return this.hachutani_irisu_qt;
	}
	public String getHachutaniIrisuQtString()
	{
		return Double.toString(this.hachutani_irisu_qt);
	}
	//===BEGIN=== Add by kou 2006/11/10
	public String getHachutaniIrisuQtInt()
	{
		return Integer.toString((int)this.hachutani_irisu_qt);
	}
	//===END=== Add by kou 2006/11/10

//	↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
	// color_naに対するセッターとゲッターの集合
	public boolean setColorNa(String color_na)
	{
		this.color_na = color_na;
		return true;
	}
	public String getColorNa()
	{
		return cutString(this.color_na,COLOR_NA_MAX_LENGTH);
	}
	public String getColorNaString()
	{
		return "'" + cutString(this.color_na,COLOR_NA_MAX_LENGTH) + "'";
	}
	public String getColorNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_na,COLOR_NA_MAX_LENGTH));
	}


	// size_naに対するセッターとゲッターの集合
	public boolean setSizeNa(String size_na)
	{
		this.size_na = size_na;
		return true;
	}
	public String getSizeNa()
	{
		return cutString(this.size_na,SIZE_NA_MAX_LENGTH);
	}
	public String getSizeNaString()
	{
		return "'" + cutString(this.size_na,SIZE_NA_MAX_LENGTH) + "'";
	}
	public String getSizeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_na,SIZE_NA_MAX_LENGTH));
	}
//	↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  unit_cd = " + getUnitCdString()
			+ "  unit_nm = " + getUnitNmString()
			+ "  syohin_kb_nm = " + getSyohinKbNmString()
			+ "  siire_hinban_cd = " + getSiireHinbanCdString()
			+ "  syohin_cd = " + getSyohinCdString()
//			+ "  area_cd = " + getAreaCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  siiresaki_nm = " + getSiiresakiNmString()
//			+ "  area_nm = " + getAreaNmString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  suryo_qt = " + getSuryoQtString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  ten_hachu_st_dt = " + getTenHachuStDtString()
			+ "  ten_hachu_ed_dt = " + getTenHachuEdDtString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getHanbaiEdDtString()
			+ "  hachutani_irisu_qt = " + getHachutaniIrisuQtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return TestBean コピー後のクラス
	 */
	public mstA60101_HonbuTonyuLBean createClone()
	{
		mstA60101_HonbuTonyuLBean bean = new mstA60101_HonbuTonyuLBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNm(this.unit_nm);
		bean.setFujiSyohinKb(this.fuji_syohin_kb);
		bean.setSyohinKbNm(this.syohin_kb_nm);
		bean.setSiireHinbanCd(this.siire_hinban_cd);
		bean.setSyohinCd(this.syohin_cd);	
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiNm(this.siiresaki_nm);	
		bean.setGentankaVl(this.gentanka_vl);
		bean.setSuryoQt(this.suryo_qt);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setTenHachuStDt(this.ten_hachu_st_dt);
		bean.setTenHachuEdDt(this.ten_hachu_ed_dt);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
		bean.setHachutaniIrisuQt(this.hachutani_irisu_qt);
		bean.setTkanji_rnl(this.tkanji_rnl);
		bean.setSuryo(this.suryo);
		//===BEGIN=== Add by kou 2006/11/13
		bean.setSyohinNm(this.getSyohinNm());
		bean.setNohinDt(this.getNohinDt());
		//===END=== Add by kou 2006/11/13
//		↓↓2006.12.18 H.Yamamoto カスタマイズ修正↓↓
		bean.setColorNa(this.getColorNa());
		bean.setSizeNa(this.getSizeNa());
//		↑↑2006.12.18 H.Yamamoto カスタマイズ修正↑↑
		bean.setInsertTs(this.getInsertTs());
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
		if( !( o instanceof mstA60101_HonbuTonyuLBean ) ) return false;
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
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
	
	/**
	 * @return
	 */
	public String getSyohinNm()
	{
		return syohinNm;
	}

	public String getSyohinNmHTMLString()
	{
		String tmpStr = HTMLStringUtil.convert(this.syohinNm);
		return insertBR(tmpStr,8);
	}
	
	/**
	 * @param string
	 */
	public void setSyohinNm(String string)
	{
		this.syohinNm = string;
	}

	/**
	 * @return
	 */
	public String getNohinDt()
	{
		return nohinDt;
	}

	/**
	 * @param string
	 */
	public void setNohinDt(String string)
	{
		this.nohinDt = string;
	}

//	↓↓2007.01.05 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * @return
	 */
	public String getInsertTs()
	{
		return insertTs;
	}

	/**
	 * @param string
	 */
	public void setInsertTs(String string)
	{
		this.insertTs = string;
	}
//	↑↑2007.01.05 H.Yamamoto カスタマイズ修正↑↑

}
