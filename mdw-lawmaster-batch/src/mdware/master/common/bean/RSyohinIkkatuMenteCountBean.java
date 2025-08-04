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
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/9/28 20:37:53)
 * @version 1.0 K.Tanigawa (障害票№0086対応　R_SYOHIN_IKKATU_MENTEマスタの本日～のデータ件数を日付単位で集計します。特定の日付のデータ件数が処理対象上限件数を超えているか、いないかを確認するためにDBから値を取得します)
 */
public class RSyohinIkkatuMenteCountBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TOUROKU_TS_MAX_LENGTH = 8;

	private String touroku_ts = null;
	private long registered_num = 0;

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
	 * RSyohinIkkatuMenteCountBeanを１件のみ抽出したい時に使用する
	 */
	public static RSyohinIkkatuMenteCountBean getRSyohinIkkatuMenteCountBean(DataHolder dataHolder)
	{
		RSyohinIkkatuMenteCountBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new RSyohinIkkatuMenteCountDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (RSyohinIkkatuMenteCountBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// touroku_tsに対するセッターとゲッターの集合
	public boolean setTourokuTs(String touroku_ts)
	{
		this.touroku_ts = touroku_ts;
		return true;
	}
	public String getTourokuTs()
	{
		return cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH);
	}
	public String getTourokuTsString()
	{
		return "'" + cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH) + "'";
	}
	public String getTourokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH));
	}


	// registered_numに対するセッターとゲッターの集合
	public boolean setRegisteredNum(String registered_num)
	{
		try
		{
			this.registered_num = Long.parseLong(registered_num);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setRegisteredNum(long registered_num)
	{
		this.registered_num = registered_num;
		return true;
	}
	public long getRegisteredNum()
	{
		return this.registered_num;
	}
	public String getRegisteredNumString()
	{
		return Long.toString(this.registered_num);
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  touroku_ts = " + getTourokuTsString()
			+ "  registered_num = " + getRegisteredNumString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RSyohinIkkatuMenteCountBean コピー後のクラス
	 */
	public RSyohinIkkatuMenteCountBean createClone()
	{
		RSyohinIkkatuMenteCountBean bean = new RSyohinIkkatuMenteCountBean();
		bean.setTourokuTs(this.touroku_ts);
		bean.setRegisteredNum(this.registered_num);
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
		if( !( o instanceof RSyohinIkkatuMenteCountBean ) ) return false;
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
