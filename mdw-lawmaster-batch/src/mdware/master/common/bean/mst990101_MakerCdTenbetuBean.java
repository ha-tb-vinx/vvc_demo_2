package mdware.master.common.bean;

import java.util.Iterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.MDWARE
 * @version 1.0 (Create time: 2006/10/16 10:39:22) K.Tanigawa
 */
public class mst990101_MakerCdTenbetuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;
	public static final int TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH = 80;
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;
	public static final int SIIRESAKI_NA_MAX_LENGTH = 80;
	public static final int TENPO_CD_MAX_LENGTH = 6;

	private String ten_siiresaki_kanri_cd = null;
	private String ten_siiresaki_kanri_na = null;
	private String siiresaki_cd = null;
	private String siiresaki_na = null;
	private long tenpo_count_num = 0;
	private String tenpo_cd = null;
	
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
	 * Mst990101MakerCdViewBeanを１件のみ抽出したい時に使用する
	 */
	public static mst990101_MakerCdTenbetuBean getMst990101MakerCdViewBean(DataHolder dataHolder)
	{
		mst990101_MakerCdTenbetuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst990101_MakerCdTenbetuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst990101_MakerCdTenbetuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
		return true;
	}
	public String getTenSiiresakiKanriCd()
	{
		return cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}


	// ten_siiresaki_kanri_naに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriNa(String ten_siiresaki_kanri_na)
	{
		this.ten_siiresaki_kanri_na = ten_siiresaki_kanri_na;
		return true;
	}
	public String getTenSiiresakiKanriNa()
	{
		return cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriNaString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH));
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


	// tenpo_count_numに対するセッターとゲッターの集合
	public boolean setTenpoCountNum(String tenpo_count_num)
	{
		try
		{
			this.tenpo_count_num = Long.parseLong(tenpo_count_num);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTenpoCountNum(long tenpo_count_num)
	{
		this.tenpo_count_num = tenpo_count_num;
		return true;
	}
	public long getTenpoCountNum()
	{
		return this.tenpo_count_num;
	}
	public String getTenpoCountNumString()
	{
		return Long.toString(this.tenpo_count_num);
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
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  ten_siiresaki_kanri_cd = " + getTenSiiresakiKanriCdString()
			+ "  ten_siiresaki_kanri_na = " + getTenSiiresakiKanriNaString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  siiresaki_na = " + getSiiresakiNaString()
			+ "  tenpo_count_num = " + getTenpoCountNumString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return Mst990101MakerCdViewBean コピー後のクラス
	 */
	public mst990101_MakerCdTenbetuBean createClone()
	{
		mst990101_MakerCdTenbetuBean bean = new mst990101_MakerCdTenbetuBean();
		bean.setTenSiiresakiKanriCd(this.ten_siiresaki_kanri_cd);
		bean.setTenSiiresakiKanriNa(this.ten_siiresaki_kanri_na);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiNa(this.siiresaki_na);
		bean.setTenpoCountNum(this.tenpo_count_num);
		bean.setTenpoCd(this.tenpo_cd);
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
		if( !( o instanceof mst990101_MakerCdTenbetuBean ) ) return false;
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
