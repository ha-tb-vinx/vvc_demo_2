package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: EHachuFileHeaderBean</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/7/30 14:39:49)
 */
public class EHachuFileHeaderBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int FILE_HEAD_NB_MAX_LENGTH 		= 20;
	public static final int SYORI_JYOKYO_FG_MAX_LENGTH 		= 1;
	public static final int DENPATU_JYOKYO_FG_MAX_LENGTH 	= 1;
	public static final int SERVER_FILE_NA_MAX_LENGTH 		= 1000;
	public static final int FILE_LENGTH_QT_MAX_LENGTH 		= 14;
	public static final int HACHU_DATA_KB_MAX_LENGTH 		= 1;
	public static final int RIYO_USER_ID_MAX_LENGTH 		= 20;
	public static final int INSERT_TS_MAX_LENGTH 			= 20;
	public static final int UPDATE_TS_MAX_LENGTH 			= 20;

	private String file_head_nb = null;
	private long jca_control_nb = 0;
	private String syori_jyokyo_fg = null;
	private String denpatu_jyokyo_fg = null;
	private String server_file_na = null;
	private String file_length_qt = null;
	private String hachu_data_kb = null;
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
	 * EHachuFileHeaderBeanを１件のみ抽出したい時に使用する
	 */
	public static EHachuFileHeaderBean getEHachuFileHeaderBean(DataHolder dataHolder)
	{
		EHachuFileHeaderBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new EHachuFileHeaderDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (EHachuFileHeaderBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// file_head_nbに対するセッターとゲッターの集合
	public boolean setFileHeadNb(String file_head_nb)
	{
		this.file_head_nb = file_head_nb;
		return true;
	}
	public String getFileHeadNb()
	{
		return cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH);
	}
	public String getFileHeadNbString()
	{
		return "'" + cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH) + "'";
	}
	public String getFileHeadNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.file_head_nb,FILE_HEAD_NB_MAX_LENGTH));
	}


	// jca_control_nbに対するセッターとゲッターの集合
	public boolean setJcaControlNb(String jca_control_nb)
	{
		try
		{
			this.jca_control_nb = Long.parseLong(jca_control_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setJcaControlNb(long jca_control_nb)
	{
		this.jca_control_nb = jca_control_nb;
		return true;
	}
	public long getJcaControlNb()
	{
		return this.jca_control_nb;
	}
	public String getJcaControlNbString()
	{
		return Long.toString(this.jca_control_nb);
	}


	// syori_jyokyo_fgに対するセッターとゲッターの集合
	public boolean setSyoriJyokyoFg(String syori_jyokyo_fg)
	{
		this.syori_jyokyo_fg = syori_jyokyo_fg;
		return true;
	}
	public String getSyoriJyokyoFg()
	{
		return cutString(this.syori_jyokyo_fg,SYORI_JYOKYO_FG_MAX_LENGTH);
	}
	public String getSyoriJyokyoFgString()
	{
		return "'" + cutString(this.syori_jyokyo_fg,SYORI_JYOKYO_FG_MAX_LENGTH) + "'";
	}
	public String getSyoriJyokyoFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syori_jyokyo_fg,SYORI_JYOKYO_FG_MAX_LENGTH));
	}


	// denpatu_jyokyo_fgに対するセッターとゲッターの集合
	public boolean setDenpatuJyokyoFg(String denpatu_jyokyo_fg)
	{
		this.denpatu_jyokyo_fg = denpatu_jyokyo_fg;
		return true;
	}
	public String getDenpatuJyokyoFg()
	{
		return cutString(this.denpatu_jyokyo_fg,DENPATU_JYOKYO_FG_MAX_LENGTH);
	}
	public String getDenpatuJyokyoFgString()
	{
		return "'" + cutString(this.denpatu_jyokyo_fg,DENPATU_JYOKYO_FG_MAX_LENGTH) + "'";
	}
	public String getDenpatuJyokyoFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.denpatu_jyokyo_fg,DENPATU_JYOKYO_FG_MAX_LENGTH));
	}


	// server_file_naに対するセッターとゲッターの集合
	public boolean setServerFileNa(String server_file_na)
	{
		this.server_file_na = server_file_na;
		return true;
	}
	public String getServerFileNa()
	{
		return cutString(this.server_file_na,SERVER_FILE_NA_MAX_LENGTH);
	}
	public String getServerFileNaString()
	{
		return "'" + cutString(this.server_file_na,SERVER_FILE_NA_MAX_LENGTH) + "'";
	}
	public String getServerFileNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.server_file_na,SERVER_FILE_NA_MAX_LENGTH));
	}


	// file_length_qtに対するセッターとゲッターの集合
	public boolean setFileLengthQt(String file_length_qt)
	{
		this.file_length_qt = file_length_qt;
		return true;
	}
	public String getFileLengthQt()
	{
		return cutString(this.file_length_qt,FILE_LENGTH_QT_MAX_LENGTH);
	}
	public String getFileLengthQtString()
	{
		return "'" + cutString(this.file_length_qt,FILE_LENGTH_QT_MAX_LENGTH) + "'";
	}
	public String getFileLengthQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.file_length_qt,FILE_LENGTH_QT_MAX_LENGTH));
	}


	// hachu_data_kbに対するセッターとゲッターの集合
	public boolean setHachuDataKb(String hachu_data_kb)
	{
		this.hachu_data_kb = hachu_data_kb;
		return true;
	}
	public String getHachuDataKb()
	{
		return cutString(this.hachu_data_kb,HACHU_DATA_KB_MAX_LENGTH);
	}
	public String getHachuDataKbString()
	{
		return "'" + cutString(this.hachu_data_kb,HACHU_DATA_KB_MAX_LENGTH) + "'";
	}
	public String getHachuDataKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_data_kb,HACHU_DATA_KB_MAX_LENGTH));
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
		return "  file_head_nb = " + getFileHeadNbString()
			+ "  jca_control_nb = " + getJcaControlNbString()
			+ "  syori_jyokyo_fg = " + getSyoriJyokyoFgString()
			+ "  denpatu_jyokyo_fg = " + getDenpatuJyokyoFgString()
			+ "  server_file_na = " + getServerFileNaString()
			+ "  file_length_qt = " + getFileLengthQtString()
			+ "  hachu_data_kb = " + getHachuDataKbString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return EHachuFileHeaderBean コピー後のクラス
	 */
	public EHachuFileHeaderBean createClone()
	{
		EHachuFileHeaderBean bean = new EHachuFileHeaderBean();
		bean.setFileHeadNb(this.file_head_nb);
		bean.setJcaControlNb(this.jca_control_nb);
		bean.setSyoriJyokyoFg(this.syori_jyokyo_fg);
		bean.setDenpatuJyokyoFg(this.denpatu_jyokyo_fg);
		bean.setServerFileNa(this.server_file_na);
		bean.setFileLengthQt(this.file_length_qt);
		bean.setHachuDataKb(this.hachu_data_kb);
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
		if( !( o instanceof EHachuFileHeaderBean ) ) return false;
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
