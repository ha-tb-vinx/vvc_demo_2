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
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/28 10:20:15)
 */
public class SystemControlBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int ONLINE_DT_MAX_LENGTH = 8;
	public static final int BATCH_DT_MAX_LENGTH = 8;
	public static final int MST_DATE_DT_MAX_LENGTH = 8;
	public static final int BAT_DATE_DT_MAX_LENGTH = 8;
	public static final int MST_USE_FG_MAX_LENGTH = 1;
	public static final int PRE_ORDER_BEGIN_DT_MAX_LENGTH = 8;
	public static final int PRE_ORDER_END_DT_MAX_LENGTH = 8;
	public static final int PRE_ORDER_SLIDE_YOBI_KB_MAX_LENGTH = 1;
	public static final int PRE_ORDER_SIME_KB_YOBI_KB_MAX_LENGTH = 1;
	public static final int EOB_USE_FG_MAX_LENGTH = 1;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;

	private String online_dt = null;
	private String batch_dt = null;
	private String mst_date_dt = null;
	private String bat_date_dt = null;
	private String mst_use_fg = null;
	private String pre_order_begin_dt = null;
	private String pre_order_end_dt = null;
	private String pre_order_slide_yobi_kb = null;
	private String pre_order_sime_kb_yobi_kb = null;
	private double max_hachu_dy = 0;
	private String eob_use_fg = null;
	private String riyo_user_id = null;
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
	 * SystemControlBeanを１件のみ抽出したい時に使用する
	 */
	public static SystemControlBean getSystemControlBean(DataHolder dataHolder)
	{
		SystemControlBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new SystemControlDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (SystemControlBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
//FUJI_POSF OTSUKA MARGED START デバッグのため
	e.printStackTrace();
//FUJI_POSF OTSUKA MARGED END
		}
		return bean;
	}

	// online_dtに対するセッターとゲッターの集合
	public boolean setOnlineDt(String online_dt)
	{
		this.online_dt = online_dt;
		return true;
	}
	public String getOnlineDt()
	{
		return cutString(this.online_dt,ONLINE_DT_MAX_LENGTH);
	}
	public String getOnlineDtString()
	{
		return "'" + cutString(this.online_dt,ONLINE_DT_MAX_LENGTH) + "'";
	}
	public String getOnlineDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.online_dt,ONLINE_DT_MAX_LENGTH));
	}


	// batch_dtに対するセッターとゲッターの集合
	public boolean setBatchDt(String batch_dt)
	{
		this.batch_dt = batch_dt;
		return true;
	}
	public String getBatchDt()
	{
		return cutString(this.batch_dt,BATCH_DT_MAX_LENGTH);
	}
	public String getBatchDtString()
	{
		return "'" + cutString(this.batch_dt,BATCH_DT_MAX_LENGTH) + "'";
	}
	public String getBatchDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.batch_dt,BATCH_DT_MAX_LENGTH));
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


	// pre_order_begin_dtに対するセッターとゲッターの集合
	public boolean setPreOrderBeginDt(String pre_order_begin_dt)
	{
		this.pre_order_begin_dt = pre_order_begin_dt;
		return true;
	}
	public String getPreOrderBeginDt()
	{
		return cutString(this.pre_order_begin_dt,PRE_ORDER_BEGIN_DT_MAX_LENGTH);
	}
	public String getPreOrderBeginDtString()
	{
		return "'" + cutString(this.pre_order_begin_dt,PRE_ORDER_BEGIN_DT_MAX_LENGTH) + "'";
	}
	public String getPreOrderBeginDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pre_order_begin_dt,PRE_ORDER_BEGIN_DT_MAX_LENGTH));
	}


	// pre_order_end_dtに対するセッターとゲッターの集合
	public boolean setPreOrderEndDt(String pre_order_end_dt)
	{
		this.pre_order_end_dt = pre_order_end_dt;
		return true;
	}
	public String getPreOrderEndDt()
	{
		return cutString(this.pre_order_end_dt,PRE_ORDER_END_DT_MAX_LENGTH);
	}
	public String getPreOrderEndDtString()
	{
		return "'" + cutString(this.pre_order_end_dt,PRE_ORDER_END_DT_MAX_LENGTH) + "'";
	}
	public String getPreOrderEndDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pre_order_end_dt,PRE_ORDER_END_DT_MAX_LENGTH));
	}


	// pre_order_slide_yobi_kbに対するセッターとゲッターの集合
	public boolean setPreOrderSlideYobiKb(String pre_order_slide_yobi_kb)
	{
		this.pre_order_slide_yobi_kb = pre_order_slide_yobi_kb;
		return true;
	}
	public String getPreOrderSlideYobiKb()
	{
		return cutString(this.pre_order_slide_yobi_kb,PRE_ORDER_SLIDE_YOBI_KB_MAX_LENGTH);
	}
	public String getPreOrderSlideYobiKbString()
	{
		return "'" + cutString(this.pre_order_slide_yobi_kb,PRE_ORDER_SLIDE_YOBI_KB_MAX_LENGTH) + "'";
	}
	public String getPreOrderSlideYobiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pre_order_slide_yobi_kb,PRE_ORDER_SLIDE_YOBI_KB_MAX_LENGTH));
	}


	// pre_order_sime_kb_yobi_kbに対するセッターとゲッターの集合
	public boolean setPreOrderSimeKbYobiKb(String pre_order_sime_kb_yobi_kb)
	{
		this.pre_order_sime_kb_yobi_kb = pre_order_sime_kb_yobi_kb;
		return true;
	}
	public String getPreOrderSimeKbYobiKb()
	{
		return cutString(this.pre_order_sime_kb_yobi_kb,PRE_ORDER_SIME_KB_YOBI_KB_MAX_LENGTH);
	}
	public String getPreOrderSimeKbYobiKbString()
	{
		return "'" + cutString(this.pre_order_sime_kb_yobi_kb,PRE_ORDER_SIME_KB_YOBI_KB_MAX_LENGTH) + "'";
	}
	public String getPreOrderSimeKbYobiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pre_order_sime_kb_yobi_kb,PRE_ORDER_SIME_KB_YOBI_KB_MAX_LENGTH));
	}


	// max_hachu_dyに対するセッターとゲッターの集合
	public boolean setMaxHachuDy(String max_hachu_dy)
	{
		try
		{
			this.max_hachu_dy = Double.parseDouble(max_hachu_dy);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxHachuDy(double max_hachu_dy)
	{
		this.max_hachu_dy = max_hachu_dy;
		return true;
	}
	public double getMaxHachuDy()
	{
		return this.max_hachu_dy;
	}
	public String getMaxHachuDyString()
	{
		return Double.toString(this.max_hachu_dy);
	}


	// eob_use_fgに対するセッターとゲッターの集合
	public boolean setEobUseFg(String eob_use_fg)
	{
		this.eob_use_fg = eob_use_fg;
		return true;
	}
	public String getEobUseFg()
	{
		return cutString(this.eob_use_fg,EOB_USE_FG_MAX_LENGTH);
	}
	public String getEobUseFgString()
	{
		return "'" + cutString(this.eob_use_fg,EOB_USE_FG_MAX_LENGTH) + "'";
	}
	public String getEobUseFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eob_use_fg,EOB_USE_FG_MAX_LENGTH));
	}


	// riyo_user_idに対するセッターとゲッターの集合
	public boolean setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
		return true;
	}
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}
	public String getRiyoUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH));
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
		return "  online_dt = " + getOnlineDtString()
			+ "  batch_dt = " + getBatchDtString()
			+ "  mst_date_dt = " + getMstDateDtString()
			+ "  bat_date_dt = " + getBatDateDtString()
			+ "  mst_use_fg = " + getMstUseFgString()
			+ "  pre_order_begin_dt = " + getPreOrderBeginDtString()
			+ "  pre_order_end_dt = " + getPreOrderEndDtString()
			+ "  pre_order_slide_yobi_kb = " + getPreOrderSlideYobiKbString()
			+ "  pre_order_sime_kb_yobi_kb = " + getPreOrderSimeKbYobiKbString()
			+ "  max_hachu_dy = " + getMaxHachuDyString()
			+ "  eob_use_fg = " + getEobUseFgString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return SystemControlBean コピー後のクラス
	 */
	public SystemControlBean createClone()
	{
		SystemControlBean bean = new SystemControlBean();
		bean.setOnlineDt(this.online_dt);
		bean.setBatchDt(this.batch_dt);
		bean.setMstDateDt(this.mst_date_dt);
		bean.setBatDateDt(this.bat_date_dt);
		bean.setMstUseFg(this.mst_use_fg);
		bean.setPreOrderBeginDt(this.pre_order_begin_dt);
		bean.setPreOrderEndDt(this.pre_order_end_dt);
		bean.setPreOrderSlideYobiKb(this.pre_order_slide_yobi_kb);
		bean.setPreOrderSimeKbYobiKb(this.pre_order_sime_kb_yobi_kb);
		bean.setMaxHachuDy(this.max_hachu_dy);
		bean.setEobUseFg(this.eob_use_fg);
		bean.setRiyoUserId(this.riyo_user_id);
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
		if( !( o instanceof SystemControlBean ) ) return false;
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
