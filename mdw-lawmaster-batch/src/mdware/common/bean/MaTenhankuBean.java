package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/8/3 16:41:19)
 */
public class MaTenhankuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 10;
	public static final int TENHANKU_CD_MAX_LENGTH = 4;
	public static final int TENHANKU_NA_MAX_LENGTH = 36;
	public static final int TENHANKU_KA_MAX_LENGTH = 18;
	public static final int URIKU_CD_MAX_LENGTH = 4;
	public static final int URIKU_NA_MAX_LENGTH = 36;
	public static final int URIKU_KA_MAX_LENGTH = 18;
	public static final int INSERT_TS_MAX_LENGTH = 20;

	private String tenpo_cd = null;
	private String tenhanku_cd = null;
	private String tenhanku_na = null;
	private String tenhanku_ka = null;
	private String uriku_cd = null;
	private String uriku_na = null;
	private String uriku_ka = null;
	private String insert_ts = null;

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
	 * MaTenhankuBeanを１件のみ抽出したい時に使用する
	 */
	public static MaTenhankuBean getMaTenhankuBean(DataHolder dataHolder)
	{
		MaTenhankuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaTenhankuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaTenhankuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// tenhanku_cdに対するセッターとゲッターの集合
	public boolean setTenhankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
	public String getTenhankuCd()
	{
		return cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenhankuCdString()
	{
		return "'" + cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH) + "'";
	}
	public String getTenhankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH));
	}


	// tenhanku_naに対するセッターとゲッターの集合
	public boolean setTenhankuNa(String tenhanku_na)
	{
		this.tenhanku_na = tenhanku_na;
		return true;
	}
	public String getTenhankuNa()
	{
		return cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH);
	}
	public String getTenhankuNaString()
	{
		return "'" + cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH) + "'";
	}
	public String getTenhankuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_na,TENHANKU_NA_MAX_LENGTH));
	}


	// tenhanku_kaに対するセッターとゲッターの集合
	public boolean setTenhankuKa(String tenhanku_ka)
	{
		this.tenhanku_ka = tenhanku_ka;
		return true;
	}
	public String getTenhankuKa()
	{
		return cutString(this.tenhanku_ka,TENHANKU_KA_MAX_LENGTH);
	}
	public String getTenhankuKaString()
	{
		return "'" + cutString(this.tenhanku_ka,TENHANKU_KA_MAX_LENGTH) + "'";
	}
	public String getTenhankuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_ka,TENHANKU_KA_MAX_LENGTH));
	}


	// uriku_cdに対するセッターとゲッターの集合
	public boolean setUrikuCd(String uriku_cd)
	{
		this.uriku_cd = uriku_cd;
		return true;
	}
	public String getUrikuCd()
	{
		return cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH);
	}
	public String getUrikuCdString()
	{
		return "'" + cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH) + "'";
	}
	public String getUrikuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH));
	}


	// uriku_naに対するセッターとゲッターの集合
	public boolean setUrikuNa(String uriku_na)
	{
		this.uriku_na = uriku_na;
		return true;
	}
	public String getUrikuNa()
	{
		return cutString(this.uriku_na,URIKU_NA_MAX_LENGTH);
	}
	public String getUrikuNaString()
	{
		return "'" + cutString(this.uriku_na,URIKU_NA_MAX_LENGTH) + "'";
	}
	public String getUrikuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku_na,URIKU_NA_MAX_LENGTH));
	}


	// uriku_kaに対するセッターとゲッターの集合
	public boolean setUrikuKa(String uriku_ka)
	{
		this.uriku_ka = uriku_ka;
		return true;
	}
	public String getUrikuKa()
	{
		return cutString(this.uriku_ka,URIKU_KA_MAX_LENGTH);
	}
	public String getUrikuKaString()
	{
		return "'" + cutString(this.uriku_ka,URIKU_KA_MAX_LENGTH) + "'";
	}
	public String getUrikuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku_ka,URIKU_KA_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  tenhanku_cd = " + getTenhankuCdString()
			+ "  tenhanku_na = " + getTenhankuNaString()
			+ "  tenhanku_ka = " + getTenhankuKaString()
			+ "  uriku_cd = " + getUrikuCdString()
			+ "  uriku_na = " + getUrikuNaString()
			+ "  uriku_ka = " + getUrikuKaString()
			+ "  insert_ts = " + getInsertTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaTenhankuBean コピー後のクラス
	 */
	public MaTenhankuBean createClone()
	{
		MaTenhankuBean bean = new MaTenhankuBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setTenhankuNa(this.tenhanku_na);
		bean.setTenhankuKa(this.tenhanku_ka);
		bean.setUrikuCd(this.uriku_cd);
		bean.setUrikuNa(this.uriku_na);
		bean.setUrikuKa(this.uriku_ka);
		bean.setInsertTs(this.insert_ts);
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
		if( !( o instanceof MaTenhankuBean ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
