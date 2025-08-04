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
 * @version X.X (Create time: 2004/8/3 16:41:54)
 */
public class MaHinsyuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HINSYU_CD_MAX_LENGTH = 4;
	public static final int HINSYU_NA_MAX_LENGTH = 36;
	public static final int HINSYU_KA_MAX_LENGTH = 18;
	public static final int INSERT_TS_MAX_LENGTH = 20;

	private String hinsyu_cd = null;
	private String hinsyu_na = null;
	private String hinsyu_ka = null;
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
	 * MaHinsyuBeanを１件のみ抽出したい時に使用する
	 */
	public static MaHinsyuBean getMaHinsyuBean(DataHolder dataHolder)
	{
		MaHinsyuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaHinsyuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaHinsyuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
	}


	// hinsyu_naに対するセッターとゲッターの集合
	public boolean setHinsyuNa(String hinsyu_na)
	{
		this.hinsyu_na = hinsyu_na;
		return true;
	}
	public String getHinsyuNa()
	{
		return cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH);
	}
	public String getHinsyuNaString()
	{
		return "'" + cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH));
	}


	// hinsyu_kaに対するセッターとゲッターの集合
	public boolean setHinsyuKa(String hinsyu_ka)
	{
		this.hinsyu_ka = hinsyu_ka;
		return true;
	}
	public String getHinsyuKa()
	{
		return cutString(this.hinsyu_ka,HINSYU_KA_MAX_LENGTH);
	}
	public String getHinsyuKaString()
	{
		return "'" + cutString(this.hinsyu_ka,HINSYU_KA_MAX_LENGTH) + "'";
	}
	public String getHinsyuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_ka,HINSYU_KA_MAX_LENGTH));
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
		return "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_na = " + getHinsyuNaString()
			+ "  hinsyu_ka = " + getHinsyuKaString()
			+ "  insert_ts = " + getInsertTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaHinsyuBean コピー後のクラス
	 */
	public MaHinsyuBean createClone()
	{
		MaHinsyuBean bean = new MaHinsyuBean();
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNa(this.hinsyu_na);
		bean.setHinsyuKa(this.hinsyu_ka);
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
		if( !( o instanceof MaHinsyuBean ) ) return false;
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
