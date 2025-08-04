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
 * @version X.X (Create time: 2005/1/6 14:22:7)
 */
public class MaLHankuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int L_HANKU_CD_MAX_LENGTH = 4;
	public static final int L_HANKU_NA_MAX_LENGTH = 36;
	public static final int L_HANKU_KA_MAX_LENGTH = 18;
	public static final int URIKU_CD_MAX_LENGTH = 4;
	public static final int INSERT_TS_MAX_LENGTH = 20;

	private String l_hanku_cd = null;
	private String l_hanku_na = null;
	private String l_hanku_ka = null;
	private String uriku_cd = null;
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
	 * MaLHankuBeanを１件のみ抽出したい時に使用する
	 */
	public static MaLHankuBean getMaLHankuBean(DataHolder dataHolder)
	{
		MaLHankuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaLHankuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaLHankuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// l_hanku_cdに対するセッターとゲッターの集合
	public boolean setLHankuCd(String l_hanku_cd)
	{
		this.l_hanku_cd = l_hanku_cd;
		return true;
	}
	public String getLHankuCd()
	{
		return cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH);
	}
	public String getLHankuCdString()
	{
		return "'" + cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getLHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH));
	}


	// l_hanku_naに対するセッターとゲッターの集合
	public boolean setLHankuNa(String l_hanku_na)
	{
		this.l_hanku_na = l_hanku_na;
		return true;
	}
	public String getLHankuNa()
	{
		return cutString(this.l_hanku_na,L_HANKU_NA_MAX_LENGTH);
	}
	public String getLHankuNaString()
	{
		return "'" + cutString(this.l_hanku_na,L_HANKU_NA_MAX_LENGTH) + "'";
	}
	public String getLHankuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_na,L_HANKU_NA_MAX_LENGTH));
	}


	// l_hanku_kaに対するセッターとゲッターの集合
	public boolean setLHankuKa(String l_hanku_ka)
	{
		this.l_hanku_ka = l_hanku_ka;
		return true;
	}
	public String getLHankuKa()
	{
		return cutString(this.l_hanku_ka,L_HANKU_KA_MAX_LENGTH);
	}
	public String getLHankuKaString()
	{
		return "'" + cutString(this.l_hanku_ka,L_HANKU_KA_MAX_LENGTH) + "'";
	}
	public String getLHankuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_ka,L_HANKU_KA_MAX_LENGTH));
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
		return "  l_hanku_cd = " + getLHankuCdString()
			+ "  l_hanku_na = " + getLHankuNaString()
			+ "  l_hanku_ka = " + getLHankuKaString()
			+ "  uriku_cd = " + getUrikuCdString()
			+ "  insert_ts = " + getInsertTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaLHankuBean コピー後のクラス
	 */
	public MaLHankuBean createClone()
	{
		MaLHankuBean bean = new MaLHankuBean();
		bean.setLHankuCd(this.l_hanku_cd);
		bean.setLHankuNa(this.l_hanku_na);
		bean.setLHankuKa(this.l_hanku_ka);
		bean.setUrikuCd(this.uriku_cd);
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
		if( !( o instanceof MaLHankuBean ) ) return false;
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
