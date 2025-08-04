package mdware.master.common.bean;

import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */			  
public class mstB10101_TenpoKaTorokuSyokaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HINBAN_NA_MAX_LENGTH = 20;
	public static final int UNIT_NA_MAX_LENGTH = 20;
	public static final int TANPIN_NA_MAX_LENGTH = 20;
	public static final int KIKAKU_NA_MAX_LENGTH = 10;
	public static final int TENPO_NA_MAX_LENGTH = 20;
	public static final int SYOHIN_2_CD_MAX_LENGTH = 13;
	public static final int BUMON_CD_MAX_LENGTH = 4;
	public static final int BUMON_NA_MAX_LENGTH = 20;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;

	private String message = "";
	private long hinban_cd = 0;
	private String hinban_na = null;
	private long unit_cd = 0;
	private String unit_na = null;
	private long tenpo_cd = 0;
	private long jan_cd = 0;
	private String tanpin_na = null;
	private String kikaku_na = null;
	private long genzai_tanka_vl = 0;
	private long hyojun_tanka_vl = 0;
	private String jisi_dt = null;
	private String tenpo_na = null;
	private String baitanka_vl = null;
	private String syohin_2_cd = null;
	private String bumon_cd = null;
	private String bumon_na = null;
	private String syohin_cd = null;

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
	public static mstB10101_TenpoKaTorokuSyokaiBean getTestBean(DataHolder dataHolder)
	{
		mstB10101_TenpoKaTorokuSyokaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstB10101_TenpoKaTorokuSyokaiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstB10101_TenpoKaTorokuSyokaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		try
		{
			this.hinban_cd = Long.parseLong(hinban_cd);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHinbanCd(long hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public long getHinbanCd()
	{
		return this.hinban_cd;
	}
	public String getHinbanCdString()
	{
		return Long.toString(this.hinban_cd);
	}


	// hinban_naに対するセッターとゲッターの集合
	public boolean setHinbanNa(String hinban_na)
	{
		this.hinban_na = hinban_na;
		return true;
	}
	public String getHinbanNa()
	{
		return cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH);
	}
	public String getHinbanNaString()
	{
		return "'" + cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH) + "'";
	}
	public String getHinbanNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH));
	}


	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		try
		{
			this.unit_cd = Long.parseLong(unit_cd);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUnitCd(long unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public long getUnitCd()
	{
		return this.unit_cd;
	}
	public String getUnitCdString()
	{
		return Long.toString(this.unit_cd);
	}


	// unit_naに対するセッターとゲッターの集合
	public boolean setUnitNa(String unit_na)
	{
		this.unit_na = unit_na;
		return true;
	}
	public String getUnitNa()
	{
		return cutString(this.unit_na,UNIT_NA_MAX_LENGTH);
	}
	public String getUnitNaString()
	{
		return "'" + cutString(this.unit_na,UNIT_NA_MAX_LENGTH) + "'";
	}
	public String getUnitNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_na,UNIT_NA_MAX_LENGTH));
	}


	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		try
		{
			this.tenpo_cd = Long.parseLong(tenpo_cd);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTenpoCd(long tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public long getTenpoCd()
	{
		return this.tenpo_cd;
	}
	public String getTenpoCdString()
	{
		return Long.toString(this.tenpo_cd);
	}


	// jan_cdに対するセッターとゲッターの集合
	public boolean setJanCd(String jan_cd)
	{
		try
		{
			this.jan_cd = Long.parseLong(jan_cd);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setJanCd(long jan_cd)
	{
		this.jan_cd = jan_cd;
		return true;
	}
	public long getJanCd()
	{
		return this.jan_cd;
	}
	public String getJanCdString()
	{
		return Long.toString(this.jan_cd);
	}


	// tanpin_naに対するセッターとゲッターの集合
	public boolean setTanpinNa(String tanpin_na)
	{
		this.tanpin_na = tanpin_na;
		return true;
	}
	public String getTanpinNa()
	{
		return cutString(this.tanpin_na,TANPIN_NA_MAX_LENGTH);
	}
	public String getTanpinNaString()
	{
		return "'" + cutString(this.tanpin_na,TANPIN_NA_MAX_LENGTH) + "'";
	}
	public String getTanpinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_na,TANPIN_NA_MAX_LENGTH));
	}


	// kikaku_naに対するセッターとゲッターの集合
	public boolean setKikakuNa(String kikaku_na)
	{
		this.kikaku_na = kikaku_na;
		return true;
	}
	public String getKikakuNa()
	{
		return cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH);
	}
	public String getKikakuNaString()
	{
		return "'" + cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH));
	}


	// genzai_tanka_vlに対するセッターとゲッターの集合
	public boolean setGenzaiTankaVl(String genzai_tanka_vl)
	{
		try
		{
			this.genzai_tanka_vl = Long.parseLong(genzai_tanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGenzaiTankaVl(long genzai_tanka_vl)
	{
		this.genzai_tanka_vl = genzai_tanka_vl;
		return true;
	}
	public long getGenzaiTankaVl()
	{
		return this.genzai_tanka_vl;
	}
	public String getGenzaiTankaVlString()
	{
		return Long.toString(this.genzai_tanka_vl);
	}


	// hyojun_tanka_vlに対するセッターとゲッターの集合
	public boolean setHyojunTankaVl(String hyojun_tanka_vl)
	{
		try
		{
			this.hyojun_tanka_vl = Long.parseLong(hyojun_tanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setHyojunTankaVl(long hyojun_tanka_vl)
	{
		this.hyojun_tanka_vl = hyojun_tanka_vl;
		return true;
	}
	public long getHyojunTankaVl()
	{
		return this.hyojun_tanka_vl;
	}
	public String getHyojunTankaVlString()
	{
		return Long.toString(this.hyojun_tanka_vl);
	}


	// jisi_dtに対するセッターとゲッターの集合
	public boolean setJisiDt(String jisi_dt)
	{
		this.jisi_dt = jisi_dt;
		return true;
	}
	public String getJisiDt()
	{
		return this.jisi_dt;
	}
	public String getJisiDtString()
	{
		return "'" + this.jisi_dt + "'";
	}
	public String getJisiDtHTMLString()
	{
		return HTMLStringUtil.convert(this.jisi_dt);
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


	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitankaVl()
	{
		return this.baitanka_vl;
	}

	// syohin_2_cdに対するセッターとゲッターの集合
	public boolean setSyohin2Cd(String syohin_2_cd)
	{
		this.syohin_2_cd = syohin_2_cd;
		return true;
	}
	public String getSyohin2Cd()
	{
		return this.syohin_2_cd;
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  hinban_cd = " + getHinbanCdString()
			+ "  hinban_na = " + getHinbanNaString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_na = " + getUnitNaString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  jan_cd = " + getJanCdString()
			+ "  tanpin_na = " + getTanpinNaString()
			+ "  kikaku_na = " + getKikakuNaString()
			+ "  genzai_tanka_vl = " + getGenzaiTankaVlString()
			+ "  hyojun_tanka_vl = " + getHyojunTankaVlString()
			+ "  jisi_dt = " + getJisiDtString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  baitanka_vl = " + getBaitankaVl()
			+ "  syohin_2_cd = " + getSyohin2Cd()
			+ "  bumon_cd = " + getBumonCdString()
			+ "  bumon_na = " + getBumonNaString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return TestBean コピー後のクラス
	 */
	public mstB10101_TenpoKaTorokuSyokaiBean createClone()
	{
		mstB10101_TenpoKaTorokuSyokaiBean bean = new mstB10101_TenpoKaTorokuSyokaiBean();
		bean.setHinbanCd(this.hinban_cd);
		bean.setHinbanNa(this.hinban_na);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNa(this.unit_na);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setJanCd(this.jan_cd);
		bean.setTanpinNa(this.tanpin_na);
		bean.setKikakuNa(this.kikaku_na);
		bean.setGenzaiTankaVl(this.genzai_tanka_vl);
		bean.setHyojunTankaVl(this.hyojun_tanka_vl);
		bean.setJisiDt(this.jisi_dt);
		bean.setTenpoNa(this.tenpo_na);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setSyohin2Cd(this.syohin_2_cd);
		bean.setBumonCd(this.bumon_cd);
		bean.setBumonNa(this.bumon_na);
		bean.setSyohinCd(this.syohin_cd);
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
		if( !( o instanceof mstB10101_TenpoKaTorokuSyokaiBean ) ) return false;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
