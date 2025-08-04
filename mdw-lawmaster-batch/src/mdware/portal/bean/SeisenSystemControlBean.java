package mdware.portal.bean;

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
 * @version X.X (Create time: 2006/8/19 17:11:25)
 */
public class SeisenSystemControlBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int ONLINE_DT_MAX_LENGTH = 8;
	public static final int BATCH_DT_MAX_LENGTH = 8;
	public static final int HCYU_ST_DT_SOBA_MAX_LENGTH = 8;
	public static final int HCYU_ED_DT_SOBA_MAX_LENGTH = 8;
	public static final int HCYU_ST_DT_HI_MAX_LENGTH = 8;
	public static final int HCYU_ED_DT_HI_MAX_LENGTH = 8;
	public static final int HCYU_ST_DT_HAN_MAX_LENGTH = 8;
	public static final int HCYU_ED_DT_HAN_MAX_LENGTH = 8;
	public static final int SYUSEI_MAKE_END_DT_MAX_LENGTH = 8;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 26;
	public static final int UPDATE_TS_MAX_LENGTH = 26;

	private String online_dt = null;
	private String batch_dt = null;
	private String hcyu_st_dt_soba = null;
	private String hcyu_ed_dt_soba = null;
	private String hcyu_st_dt_hi = null;
	private String hcyu_ed_dt_hi = null;
	private String hcyu_st_dt_han = null;
	private String hcyu_ed_dt_han = null;
	private long soba_st_dt = 0;
	private String syusei_make_end_dt = null;
	private long data_input_lines_qt = 0;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;

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
	 * SeisenSystemControlBeanを１件のみ抽出したい時に使用する
	 */
	public static SeisenSystemControlBean getSeisenSystemControlBean(DataHolder dataHolder)
	{
		SeisenSystemControlBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new SeisenSystemControlDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (SeisenSystemControlBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
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


	// hcyu_st_dt_sobaに対するセッターとゲッターの集合
	public boolean setHcyuStDtSoba(String hcyu_st_dt_soba)
	{
		this.hcyu_st_dt_soba = hcyu_st_dt_soba;
		return true;
	}
	public String getHcyuStDtSoba()
	{
		return cutString(this.hcyu_st_dt_soba,HCYU_ST_DT_SOBA_MAX_LENGTH);
	}
	public String getHcyuStDtSobaString()
	{
		return "'" + cutString(this.hcyu_st_dt_soba,HCYU_ST_DT_SOBA_MAX_LENGTH) + "'";
	}
	public String getHcyuStDtSobaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_st_dt_soba,HCYU_ST_DT_SOBA_MAX_LENGTH));
	}


	// hcyu_ed_dt_sobaに対するセッターとゲッターの集合
	public boolean setHcyuEdDtSoba(String hcyu_ed_dt_soba)
	{
		this.hcyu_ed_dt_soba = hcyu_ed_dt_soba;
		return true;
	}
	public String getHcyuEdDtSoba()
	{
		return cutString(this.hcyu_ed_dt_soba,HCYU_ED_DT_SOBA_MAX_LENGTH);
	}
	public String getHcyuEdDtSobaString()
	{
		return "'" + cutString(this.hcyu_ed_dt_soba,HCYU_ED_DT_SOBA_MAX_LENGTH) + "'";
	}
	public String getHcyuEdDtSobaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_ed_dt_soba,HCYU_ED_DT_SOBA_MAX_LENGTH));
	}


	// hcyu_st_dt_hiに対するセッターとゲッターの集合
	public boolean setHcyuStDtHi(String hcyu_st_dt_hi)
	{
		this.hcyu_st_dt_hi = hcyu_st_dt_hi;
		return true;
	}
	public String getHcyuStDtHi()
	{
		return cutString(this.hcyu_st_dt_hi,HCYU_ST_DT_HI_MAX_LENGTH);
	}
	public String getHcyuStDtHiString()
	{
		return "'" + cutString(this.hcyu_st_dt_hi,HCYU_ST_DT_HI_MAX_LENGTH) + "'";
	}
	public String getHcyuStDtHiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_st_dt_hi,HCYU_ST_DT_HI_MAX_LENGTH));
	}


	// hcyu_ed_dt_hiに対するセッターとゲッターの集合
	public boolean setHcyuEdDtHi(String hcyu_ed_dt_hi)
	{
		this.hcyu_ed_dt_hi = hcyu_ed_dt_hi;
		return true;
	}
	public String getHcyuEdDtHi()
	{
		return cutString(this.hcyu_ed_dt_hi,HCYU_ED_DT_HI_MAX_LENGTH);
	}
	public String getHcyuEdDtHiString()
	{
		return "'" + cutString(this.hcyu_ed_dt_hi,HCYU_ED_DT_HI_MAX_LENGTH) + "'";
	}
	public String getHcyuEdDtHiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_ed_dt_hi,HCYU_ED_DT_HI_MAX_LENGTH));
	}


	// hcyu_st_dt_hanに対するセッターとゲッターの集合
	public boolean setHcyuStDtHan(String hcyu_st_dt_han)
	{
		this.hcyu_st_dt_han = hcyu_st_dt_han;
		return true;
	}
	public String getHcyuStDtHan()
	{
		return cutString(this.hcyu_st_dt_han,HCYU_ST_DT_HAN_MAX_LENGTH);
	}
	public String getHcyuStDtHanString()
	{
		return "'" + cutString(this.hcyu_st_dt_han,HCYU_ST_DT_HAN_MAX_LENGTH) + "'";
	}
	public String getHcyuStDtHanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_st_dt_han,HCYU_ST_DT_HAN_MAX_LENGTH));
	}


	// hcyu_ed_dt_hanに対するセッターとゲッターの集合
	public boolean setHcyuEdDtHan(String hcyu_ed_dt_han)
	{
		this.hcyu_ed_dt_han = hcyu_ed_dt_han;
		return true;
	}
	public String getHcyuEdDtHan()
	{
		return cutString(this.hcyu_ed_dt_han,HCYU_ED_DT_HAN_MAX_LENGTH);
	}
	public String getHcyuEdDtHanString()
	{
		return "'" + cutString(this.hcyu_ed_dt_han,HCYU_ED_DT_HAN_MAX_LENGTH) + "'";
	}
	public String getHcyuEdDtHanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hcyu_ed_dt_han,HCYU_ED_DT_HAN_MAX_LENGTH));
	}


	// soba_st_dtに対するセッターとゲッターの集合
	public boolean setSobaStDt(String soba_st_dt)
	{
		try
		{
			this.soba_st_dt = Long.parseLong(soba_st_dt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSobaStDt(long soba_st_dt)
	{
		this.soba_st_dt = soba_st_dt;
		return true;
	}
	public long getSobaStDt()
	{
		return this.soba_st_dt;
	}
	public String getSobaStDtString()
	{
		return Long.toString(this.soba_st_dt);
	}


	// syusei_make_end_dtに対するセッターとゲッターの集合
	public boolean setSyuseiMakeEndDt(String syusei_make_end_dt)
	{
		this.syusei_make_end_dt = syusei_make_end_dt;
		return true;
	}
	public String getSyuseiMakeEndDt()
	{
		return cutString(this.syusei_make_end_dt,SYUSEI_MAKE_END_DT_MAX_LENGTH);
	}
	public String getSyuseiMakeEndDtString()
	{
		return "'" + cutString(this.syusei_make_end_dt,SYUSEI_MAKE_END_DT_MAX_LENGTH) + "'";
	}
	public String getSyuseiMakeEndDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syusei_make_end_dt,SYUSEI_MAKE_END_DT_MAX_LENGTH));
	}


	// data_input_lines_qtに対するセッターとゲッターの集合
	public boolean setDataInputLinesQt(String data_input_lines_qt)
	{
		try
		{
			this.data_input_lines_qt = Long.parseLong(data_input_lines_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setDataInputLinesQt(long data_input_lines_qt)
	{
		this.data_input_lines_qt = data_input_lines_qt;
		return true;
	}
	public long getDataInputLinesQt()
	{
		return this.data_input_lines_qt;
	}
	public String getDataInputLinesQtString()
	{
		return Long.toString(this.data_input_lines_qt);
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  online_dt = " + getOnlineDtString()
			+ "  batch_dt = " + getBatchDtString()
			+ "  hcyu_st_dt_soba = " + getHcyuStDtSobaString()
			+ "  hcyu_ed_dt_soba = " + getHcyuEdDtSobaString()
			+ "  hcyu_st_dt_hi = " + getHcyuStDtHiString()
			+ "  hcyu_ed_dt_hi = " + getHcyuEdDtHiString()
			+ "  hcyu_st_dt_han = " + getHcyuStDtHanString()
			+ "  hcyu_ed_dt_han = " + getHcyuEdDtHanString()
			+ "  soba_st_dt = " + getSobaStDtString()
			+ "  syusei_make_end_dt = " + getSyuseiMakeEndDtString()
			+ "  data_input_lines_qt = " + getDataInputLinesQtString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return SeisenSystemControlBean コピー後のクラス
	 */
	public SeisenSystemControlBean createClone()
	{
		SeisenSystemControlBean bean = new SeisenSystemControlBean();
		bean.setOnlineDt(this.online_dt);
		bean.setBatchDt(this.batch_dt);
		bean.setHcyuStDtSoba(this.hcyu_st_dt_soba);
		bean.setHcyuEdDtSoba(this.hcyu_ed_dt_soba);
		bean.setHcyuStDtHi(this.hcyu_st_dt_hi);
		bean.setHcyuEdDtHi(this.hcyu_ed_dt_hi);
		bean.setHcyuStDtHan(this.hcyu_st_dt_han);
		bean.setHcyuEdDtHan(this.hcyu_ed_dt_han);
		bean.setSobaStDt(this.soba_st_dt);
		bean.setSyuseiMakeEndDt(this.syusei_make_end_dt);
		bean.setDataInputLinesQt(this.data_input_lines_qt);
		bean.setRiyoUserId(this.riyo_user_id);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
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
		if( !( o instanceof SeisenSystemControlBean ) ) return false;
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
