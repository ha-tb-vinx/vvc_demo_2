package mdware.master.util.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2005/3/31 13:52:46)
 */
public class RMSTDATEBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int MST_DATE_DT_MAX_LENGTH = 8;
	public static final int BAT_DATE_DT_MAX_LENGTH = 8;
	public static final int MST_USE_FG_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;

	private String mst_date_dt = null;
	private String bat_date_dt = null;
	private String mst_use_fg = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;

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
	 * RMSTDATEBeanを１件のみ抽出したい時に使用する
	 */
	public static RMSTDATEBean getRMSTDATEBean(DataHolder dataHolder)
	{
		RMSTDATEBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new RMSTDATEDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (RMSTDATEBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// mst_date_dtに対するセッターとゲッターの集合
	public boolean setMstDateDt(String mst_date_dt)
	{
		this.mst_date_dt = mst_date_dt;
		return true;
	}
	public String getMstDateDt()
	{
		return cutString(this.mst_date_dt,MST_DATE_DT_MAX_LENGTH);
	}
	public String getMstDateDtString()
	{
		return "'" + cutString(this.mst_date_dt,MST_DATE_DT_MAX_LENGTH) + "'";
	}
	public String getMstDateDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mst_date_dt,MST_DATE_DT_MAX_LENGTH));
	}


	// bat_date_dtに対するセッターとゲッターの集合
	public boolean setBatDateDt(String bat_date_dt)
	{
		this.bat_date_dt = bat_date_dt;
		return true;
	}
	public String getBatDateDt()
	{
		return cutString(this.bat_date_dt,BAT_DATE_DT_MAX_LENGTH);
	}
	public String getBatDateDtString()
	{
		return "'" + cutString(this.bat_date_dt,BAT_DATE_DT_MAX_LENGTH) + "'";
	}
	public String getBatDateDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bat_date_dt,BAT_DATE_DT_MAX_LENGTH));
	}

	// mst_use_fgに対するセッターとゲッターの集合
	public boolean setMstUseFg(String mst_use_fg)
	{
		this.mst_use_fg = mst_use_fg;
		return true;
	}
	public String getMstUseFg()
	{
		return cutString(this.mst_use_fg,MST_USE_FG_MAX_LENGTH);
	}
	public String getMstUseFgString()
	{
		return "'" + cutString(this.mst_use_fg,MST_USE_FG_MAX_LENGTH) + "'";
	}
	public String getMstUseFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mst_use_fg,MST_USE_FG_MAX_LENGTH));
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


	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
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


	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  mst_date_dt = " + getMstDateDtString()
			+ "  bat_date_dt = " + getBatDateDtString()
			+ "  mst_use_fg = " + getMstUseFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RMSTDATEBean コピー後のクラス
	 */
	public RMSTDATEBean createClone()
	{
		RMSTDATEBean bean = new RMSTDATEBean();
		bean.setMstDateDt(this.mst_date_dt);
		bean.setMstUseFg(this.mst_use_fg);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
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
		if( !( o instanceof RMSTDATEBean ) ) return false;
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
