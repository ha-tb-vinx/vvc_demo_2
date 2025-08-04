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
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author VINX
 * @version 1.00 (2014/08/07) NGHIA-HT 海外LAWSON様UTF-8対応
 */
public class RSyohinIkkatuMenteNextDayBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int NEXTDAY_MAX_LENGTH = 8;

	private String nextday = null;

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
	 * RSyohinIkkatuMenteNextDayBeanを１件のみ抽出したい時に使用する
	 */
	public static RSyohinIkkatuMenteNextDayBean getRSyohinIkkatuMenteNextDayBean(DataHolder dataHolder)
	{
		RSyohinIkkatuMenteNextDayBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new RSyohinIkkatuMenteNextDayDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (RSyohinIkkatuMenteNextDayBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// nextdayに対するセッターとゲッターの集合
	public boolean setNextday(String nextday)
	{
		this.nextday = nextday;
		return true;
	}
	public String getNextday()
	{
		return cutString(this.nextday,NEXTDAY_MAX_LENGTH);
	}
	public String getNextdayString()
	{
		return "'" + cutString(this.nextday,NEXTDAY_MAX_LENGTH) + "'";
	}
	public String getNextdayHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nextday,NEXTDAY_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  nextday = " + getNextdayString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RSyohinIkkatuMenteNextDayBean コピー後のクラス
	 */
	public RSyohinIkkatuMenteNextDayBean createClone()
	{
		RSyohinIkkatuMenteNextDayBean bean = new RSyohinIkkatuMenteNextDayBean();
		bean.setNextday(this.nextday);
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
		if( !( o instanceof RSyohinIkkatuMenteNextDayBean ) ) return false;
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
