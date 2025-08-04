package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mst001101_EffectiveDayBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int YUKO_DT_STR_MAX_LENGTH = 20;			//有効日範囲Startレングス
	public static final int YUKO_DT_END_MAX_LENGTH = 20;			//有効日範囲Endレングス

	private String yuko_dt_str = null;			//有効日範囲Start
	private String yuko_dt_end = null;			//有効日範囲End

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
	 * mst001101_EffectiveDayBeanを１件のみ抽出したい時に使用する
	 */
	public static mst001101_EffectiveDayBean getmst001101_EffectiveDayBean(DataHolder dataHolder)
	{
		mst001101_EffectiveDayBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst001101_EffectiveDayDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst001101_EffectiveDayBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// yuko_dt_strに対するセッターとゲッターの集合
	public boolean setYukoDtStr(String yuko_dt_str)
	{
		this.yuko_dt_str = yuko_dt_str;
		return true;
	}
	public String getYukoDtStr()
	{
		return cutString(this.yuko_dt_str,YUKO_DT_STR_MAX_LENGTH);
	}
	public String getYukoDtStrString()
	{
		return "'" + cutString(this.yuko_dt_str,YUKO_DT_STR_MAX_LENGTH) + "'";
	}
	public String getYukoDtStrHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt_str,YUKO_DT_STR_MAX_LENGTH));
	}


	// yuko_dt_endに対するセッターとゲッターの集合
	public boolean setYukoDtEnd(String yuko_dt_end)
	{
		this.yuko_dt_end = yuko_dt_end;
		return true;
	}
	public String getYukoDtEnd()
	{
		return cutString(this.yuko_dt_end,YUKO_DT_END_MAX_LENGTH);
	}
	public String getYukoDtEndString()
	{
		return "'" + cutString(this.yuko_dt_end,YUKO_DT_END_MAX_LENGTH) + "'";
	}
	public String getYukoDtEndHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt_end,YUKO_DT_END_MAX_LENGTH));
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  insert_ts = " + getYukoDtStrString()
			+ "  update_ts = " + getYukoDtEndString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst000601_KoushinInfoBean コピー後のクラス
	 */
	public mst001101_EffectiveDayBean createClone()
	{
		mst001101_EffectiveDayBean bean = new mst001101_EffectiveDayBean();
		bean.setYukoDtStr(this.yuko_dt_str);
		bean.setYukoDtEnd(this.yuko_dt_end);
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
		if( !( o instanceof mst001101_EffectiveDayBean ) ) return false;
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

}
