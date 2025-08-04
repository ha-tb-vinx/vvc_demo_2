package mdware.portal.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/7 11:10:9)
 */
public class MaAlarmBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int ALARM_ID_MAX_LENGTH = 20;
	public static final int ALARM_TYPE_NA_MAX_LENGTH = 32;
	public static final int CONTENT_TX_MAX_LENGTH = 256;
	public static final int URL_TX_MAX_LENGTH = 256;
	public static final int INSERT_TS_MAX_LENGTH = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;
	public static final int COLOR_TX_MAX_LENGTH = 16;

	private String alarm_id = null;
	private String alarm_type_na = null;
	private String content_tx = null;
	private String url_tx = null;
	private long period = 0;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String color_tx = null;

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
	 * MaAlarmBeanを１件のみ抽出したい時に使用する
	 */
	public static MaAlarmBean getMaAlarmBean(DataHolder dataHolder)
	{
		MaAlarmBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaAlarmDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaAlarmBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// alarm_idに対するセッターとゲッターの集合
	public boolean setAlarmId(String alarm_id)
	{
		this.alarm_id = alarm_id;
		return true;
	}
	public String getAlarmId()
	{
		return cutString(this.alarm_id,ALARM_ID_MAX_LENGTH);
	}
	public String getAlarmIdString()
	{
		return "'" + cutString(this.alarm_id,ALARM_ID_MAX_LENGTH) + "'";
	}
	public String getAlarmIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.alarm_id,ALARM_ID_MAX_LENGTH));
	}


	// alarm_type_naに対するセッターとゲッターの集合
	public boolean setAlarmTypeNa(String alarm_type_na)
	{
		this.alarm_type_na = alarm_type_na;
		return true;
	}
	public String getAlarmTypeNa()
	{
		return cutString(this.alarm_type_na,ALARM_TYPE_NA_MAX_LENGTH);
	}
	public String getAlarmTypeNaString()
	{
		return "'" + cutString(this.alarm_type_na,ALARM_TYPE_NA_MAX_LENGTH) + "'";
	}
	public String getAlarmTypeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.alarm_type_na,ALARM_TYPE_NA_MAX_LENGTH));
	}


	// content_txに対するセッターとゲッターの集合
	public boolean setContentTx(String content_tx)
	{
		this.content_tx = content_tx;
		return true;
	}
	public String getContentTx()
	{
		return cutString(this.content_tx,CONTENT_TX_MAX_LENGTH);
	}
	public String getContentTxString()
	{
		return "'" + cutString(this.content_tx,CONTENT_TX_MAX_LENGTH) + "'";
	}
	public String getContentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.content_tx,CONTENT_TX_MAX_LENGTH));
	}


	// url_txに対するセッターとゲッターの集合
	public boolean setUrlTx(String url_tx)
	{
		this.url_tx = url_tx;
		return true;
	}
	public String getUrlTx()
	{
		return cutString(this.url_tx,URL_TX_MAX_LENGTH);
	}
	public String getUrlTxString()
	{
		return "'" + cutString(this.url_tx,URL_TX_MAX_LENGTH) + "'";
	}
	public String getUrlTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.url_tx,URL_TX_MAX_LENGTH));
	}


	// periodに対するセッターとゲッターの集合
	public boolean setPeriod(String period)
	{
		try
		{
			this.period = Long.parseLong(period);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPeriod(long period)
	{
		this.period = period;
		return true;
	}
	public long getPeriod()
	{
		return this.period;
	}
	public String getPeriodString()
	{
		return Long.toString(this.period);
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
	
	// color_txに対するセッターとゲッターの集合
	public boolean setColorTX(String color_tx)
	{
		this.color_tx = color_tx;
		return true;
	}
	public String getColorTX()
	{
		return cutString(this.color_tx,COLOR_TX_MAX_LENGTH);
	}
	public String getColorTXString()
	{
		return "'" + cutString(this.color_tx,COLOR_TX_MAX_LENGTH) + "'";
	}
	public String getColorTXHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_tx,COLOR_TX_MAX_LENGTH));
	}
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  alarm_id = " + getAlarmIdString()
			+ "  alarm_type_na = " + getAlarmTypeNaString()
			+ "  content_tx = " + getContentTxString()
			+ "  url_tx = " + getUrlTxString()
			+ "  period = " + getPeriodString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  color_tx = " + getColorTXString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaAlarmBean コピー後のクラス
	 */
	public MaAlarmBean createClone()
	{
		MaAlarmBean bean = new MaAlarmBean();
		bean.setAlarmId(this.alarm_id);
		bean.setAlarmTypeNa(this.alarm_type_na);
		bean.setContentTx(this.content_tx);
		bean.setUrlTx(this.url_tx);
		bean.setPeriod(this.period);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setColorTX(this.color_tx);
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
		if( !( o instanceof MaAlarmBean ) ) return false;
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
