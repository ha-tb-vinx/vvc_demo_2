package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: </p>
 * @author VINX
 * @Version 1.00 2014/06/17 Ha.ntt 海外LAWSON様通貨対応
 */
public class mst370201_DownloadListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	// public static final int HANKU_CD_MAX_LENGTH = 4;
	public static final int BUMON_CD_MAX_LENGTH = 4;
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH = 15;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;
	public static final int YUKO_DT_MAX_LENGTH = 8;
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;
	public static final int KIKAKU_KANJI_NA_MAX_LENGTH = 40;
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;
	public static final int HANBAI_ED_DT_MAX_LENGTH = 8;
	//ADD by Tanigawa 2006/9/21 障害票№0013対応 START
	public static final int COLOR_NA_MAX_LENGTH = 80;	//略式名称漢字
	public static final int SIZE_NA_MAX_LENGTH = 80;	//略式名称漢字
	//ADD by Tanigawa 2006/9/21 障害票№0013対応  END

	// private String hanku_cd = null;
	private String bumon_cd = null;
	private String siire_hinban_cd = null;
	private String syohin_cd = null;
	private String yuko_dt = null;
	private String hinmei_kanji_na = null;
	private String kikaku_kanji_na = null;
	private double gentanka_vl = 0;
	private double baitanka_vl = 0;
	private String hanbai_st_dt = null;
	private String hanbai_ed_dt = null;

	//ADD by Tanigawa 2006/9/21 障害票№0013対応 START
	private String color_na = null;
	private String size_na = null;
	//ADD by Tanigawa 2006/9/21 障害票№0013対応  END


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
	 * mst370201DownloadListBeanを１件のみ抽出したい時に使用する
	 */
	public static mst370201_DownloadListBean getmst370201DownloadListBean(DataHolder dataHolder)
	{
		mst370201_DownloadListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst370201_DownloadListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst370201_DownloadListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



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
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
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


	// kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setKikakuKanjiNa(String kikaku_kanji_na)
	{
		this.kikaku_kanji_na = kikaku_kanji_na;
		return true;
	}
	public String getKikakuKanjiNa()
	{
		return cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getKikakuKanjiNaString()
	{
		return "'" + cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH));
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
		return true;
	}
	public boolean setBaitankaVl(double baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
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


	//ADD by Tanigawa 2006/9/21 障害票№0013対応 START
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
	//ADD by Tanigawa 2006/9/21 障害票№0013対応  END

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		// return "  hanku_cd = " + getHankuCdString()
		return "  bumon_cd = " + getBumonCdString()
			+ "  siire_hinban_cd = " + getSiireHinbanCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+ "  kikaku_kanji_na = " + getKikakuKanjiNaString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getHanbaiEdDtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst370201DownloadListBean コピー後のクラス
	 */
	public mst370201_DownloadListBean createClone()
	{
		mst370201_DownloadListBean bean = new mst370201_DownloadListBean();
		// bean.setHankuCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSiireHinbanCd(this.siire_hinban_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setKikakuKanjiNa(this.kikaku_kanji_na);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
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
		if( !( o instanceof mst370201_DownloadListBean ) ) return false;
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
}
