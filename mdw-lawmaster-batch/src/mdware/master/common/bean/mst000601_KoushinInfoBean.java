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
public class mst000601_KoushinInfoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成日MAXレングス
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新日MAXレングス
	public static final int INSERT_USER_NAME_MAX_LENGTH = 80;	//作成者MAXレングス
	public static final int UPDATE_USER_NAME_MAX_LENGTH = 80;	//更新者MAXレングス

	private String insert_ts = null;			//作成日
	private String update_ts = null;			//更新日
	private String insert_user_name = null;	//作成者
	private String update_user_name = null;	//更新者

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
	 * mst000601_KoushinInfoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst000601_KoushinInfoBean getmst000601_KoushinInfoBean(DataHolder dataHolder)
	{
		mst000601_KoushinInfoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst000601_KoushinInfoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst000601_KoushinInfoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}


	// insert_user_nameに対するセッターとゲッターの集合
	public boolean setInsertUserName(String insert_user_name)
	{
		this.insert_user_name = insert_user_name;
		return true;
	}
	public String getInsertUserName()
	{
		return cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH);
	}
	public String getInsertUserNameString()
	{
		return "'" + cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH) + "'";
	}
	public String getInsertUserNameHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH));
	}


	// update_user_nameに対するセッターとゲッターの集合
	public boolean setUpdateUserName(String update_user_name)
	{
		this.update_user_name = update_user_name;
		return true;
	}
	public String getUpdateUserName()
	{
		return cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH);
	}
	public String getUpdateUserNameString()
	{
		return "'" + cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNameHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  insert_user_name = " + getInsertUserNameString()
			+ "  update_user_name = " + getUpdateUserNameString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst000601_KoushinInfoBean コピー後のクラス
	 */
	public mst000601_KoushinInfoBean createClone()
	{
		mst000601_KoushinInfoBean bean = new mst000601_KoushinInfoBean();
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
		bean.setInsertUserName(this.insert_user_name);
		bean.setUpdateUserName(this.update_user_name);
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
		if( !( o instanceof mst000601_KoushinInfoBean ) ) return false;
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
