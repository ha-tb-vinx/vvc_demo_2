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
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/8/17 10:29:52)
 */
public class ENohinFileHeaderBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int FILE_HEAD_NB_MAX_LENGTH = 20;
	public static final int SYORI_JYOKYO_FG_MAX_LENGTH = 1;
	public static final int SERVER_FILE_NA_MAX_LENGTH = 1000;
	public static final int CLIENT_FILE_NA_MAX_LENGTH = 1000;
	public static final int FILE_LENGTH_QT_MAX_LENGTH = 14;
	public static final int OUTPUT_FG_MAX_LENGTH = 1;
	public static final int FILE_RECV_FG_MAX_LENGTH = 1;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String file_head_nb = null;
	private long jca_control_nb = 0;
	private String syori_jyokyo_fg = null;
	private String server_file_na = null;
	private String client_file_na = null;
	private String file_length_qt = null;
	private String output_fg = null;
	private String file_recv_fg = null;
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
	 * ENohinFileHeaderBeanを１件のみ抽出したい時に使用する
	 */
	public static ENohinFileHeaderBean getENohinFileHeaderBean(DataHolder dataHolder)
	{
		ENohinFileHeaderBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new ENohinFileHeaderDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (ENohinFileHeaderBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	//ゼロつきfile_head_nbセットメソッド
	public void setFileHeadNbWithZero(String file_head_nb)
	{
		this.file_head_nb = mdware.common.util.StringUtility.adjustStringWithZero(file_head_nb, FILE_HEAD_NB_MAX_LENGTH);
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


	// client_file_naに対するセッターとゲッターの集合
	public boolean setClientFileNa(String client_file_na)
	{
		this.client_file_na = client_file_na;
		return true;
	}
	public String getClientFileNa()
	{
		return cutString(this.client_file_na,CLIENT_FILE_NA_MAX_LENGTH);
	}
	public String getClientFileNaString()
	{
		return "'" + cutString(this.client_file_na,CLIENT_FILE_NA_MAX_LENGTH) + "'";
	}
	public String getClientFileNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.client_file_na,CLIENT_FILE_NA_MAX_LENGTH));
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


	// output_fgに対するセッターとゲッターの集合
	public boolean setOutputFg(String output_fg)
	{
		this.output_fg = output_fg;
		return true;
	}
	public String getOutputFg()
	{
		return cutString(this.output_fg,OUTPUT_FG_MAX_LENGTH);
	}
	public String getOutputFgString()
	{
		return "'" + cutString(this.output_fg,OUTPUT_FG_MAX_LENGTH) + "'";
	}
	public String getOutputFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.output_fg,OUTPUT_FG_MAX_LENGTH));
	}


	// file_recv_fgに対するセッターとゲッターの集合
	public boolean setFileRecvFg(String file_recv_fg)
	{
		this.file_recv_fg = file_recv_fg;
		return true;
	}
	public String getFileRecvFg()
	{
		return cutString(this.file_recv_fg,FILE_RECV_FG_MAX_LENGTH);
	}
	public String getFileRecvFgString()
	{
		return "'" + cutString(this.file_recv_fg,FILE_RECV_FG_MAX_LENGTH) + "'";
	}
	public String getFileRecvFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.file_recv_fg,FILE_RECV_FG_MAX_LENGTH));
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
			+ "  server_file_na = " + getServerFileNaString()
			+ "  client_file_na = " + getClientFileNaString()
			+ "  file_length_qt = " + getFileLengthQtString()
			+ "  output_fg = " + getOutputFgString()
			+ "  file_recv_fg = " + getFileRecvFgString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return ENohinFileHeaderBean コピー後のクラス
	 */
	public ENohinFileHeaderBean createClone()
	{
		ENohinFileHeaderBean bean = new ENohinFileHeaderBean();
		bean.setFileHeadNb(this.file_head_nb);
		bean.setJcaControlNb(this.jca_control_nb);
		bean.setSyoriJyokyoFg(this.syori_jyokyo_fg);
		bean.setServerFileNa(this.server_file_na);
		bean.setClientFileNa(this.client_file_na);
		bean.setFileLengthQt(this.file_length_qt);
		bean.setOutputFg(this.output_fg);
		bean.setFileRecvFg(this.file_recv_fg);
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
		if( !( o instanceof ENohinFileHeaderBean ) ) return false;
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
