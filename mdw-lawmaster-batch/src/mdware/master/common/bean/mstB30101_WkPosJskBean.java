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
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstB30101_WkPosJskBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;
	public static final int TENPO_CD_MAX_LENGTH = 6;
	public static final int URIAGE_DT_MAX_LENGTH = 8;
	public static final int SYUTOKU_FG_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String siiresaki_cd = null;
	private String syohin_cd = null;
	private String tenpo_cd = null;
	private String uriage_dt = null;
	private long uriage_vl = 0;
	private long uriage_qt = 0;
	private String syutoku_fg = null;
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
	 * WkPosJskBeanを１件のみ抽出したい時に使用する
	 */
	public static mstB30101_WkPosJskBean getWkPosJskBean(DataHolder dataHolder)
	{
		mstB30101_WkPosJskBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstB30101_WkPosJskDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstB30101_WkPosJskBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}


	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}


	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH));
	}


	// uriage_dtに対するセッターとゲッターの集合
	public boolean setUriageDt(String uriage_dt)
	{
		this.uriage_dt = uriage_dt;
		return true;
	}
	public String getUriageDt()
	{
		return cutString(this.uriage_dt,URIAGE_DT_MAX_LENGTH);
	}
	public String getUriageDtString()
	{
		return "'" + cutString(this.uriage_dt,URIAGE_DT_MAX_LENGTH) + "'";
	}
	public String getUriageDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriage_dt,URIAGE_DT_MAX_LENGTH));
	}


	// uriage_vlに対するセッターとゲッターの集合
	public boolean setUriageVl(String uriage_vl)
	{
		try
		{
			this.uriage_vl = Long.parseLong(uriage_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUriageVl(long uriage_vl)
	{
		this.uriage_vl = uriage_vl;
		return true;
	}
	public long getUriageVl()
	{
		return this.uriage_vl;
	}
	public String getUriageVlString()
	{
		return Long.toString(this.uriage_vl);
	}


	// uriage_qtに対するセッターとゲッターの集合
	public boolean setUriageQt(String uriage_qt)
	{
		try
		{
			this.uriage_qt = Long.parseLong(uriage_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUriageQt(long uriage_qt)
	{
		this.uriage_qt = uriage_qt;
		return true;
	}
	public long getUriageQt()
	{
		return this.uriage_qt;
	}
	public String getUriageQtString()
	{
		return Long.toString(this.uriage_qt);
	}


	// syutoku_fgに対するセッターとゲッターの集合
	public boolean setSyutokuFg(String syutoku_fg)
	{
		this.syutoku_fg = syutoku_fg;
		return true;
	}
	public String getSyutokuFg()
	{
		return cutString(this.syutoku_fg,SYUTOKU_FG_MAX_LENGTH);
	}
	public String getSyutokuFgString()
	{
		return "'" + cutString(this.syutoku_fg,SYUTOKU_FG_MAX_LENGTH) + "'";
	}
	public String getSyutokuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syutoku_fg,SYUTOKU_FG_MAX_LENGTH));
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
		return "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  uriage_dt = " + getUriageDtString()
			+ "  uriage_vl = " + getUriageVlString()
			+ "  uriage_qt = " + getUriageQtString()
			+ "  syutoku_fg = " + getSyutokuFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return WkPosJskBean コピー後のクラス
	 */
	public mstB30101_WkPosJskBean createClone()
	{
		mstB30101_WkPosJskBean bean = new mstB30101_WkPosJskBean();
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setUriageDt(this.uriage_dt);
		bean.setUriageVl(this.uriage_vl);
		bean.setUriageQt(this.uriage_qt);
		bean.setSyutokuFg(this.syutoku_fg);
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
		if( !( o instanceof mstB30101_WkPosJskBean ) ) return false;
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
