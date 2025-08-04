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
 * @version X.X (Create time: 2004/12/29 19:15:8)
 */
public class MaCenterBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int CENTER_CD_MAX_LENGTH = 10;
	public static final int CENTER_NA_MAX_LENGTH = 20;
	public static final int CENTER_KA_MAX_LENGTH = 10;
	public static final int CENTER_RN_MAX_LENGTH = 10;
	public static final int CENTER_RK_MAX_LENGTH = 5;
	public static final int HOJIN_CD_MAX_LENGTH = 4;
	public static final int RIYO_USER_ID_MAX_LENGTH = 20;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;

	private String center_cd = null;
	private String center_na = null;
	private String center_ka = null;
	private String center_rn = null;
	private String center_rk = null;
	private String hojin_cd = null;
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
	 * MaCenterBeanを１件のみ抽出したい時に使用する
	 */
	public static MaCenterBean getMaCenterBean(DataHolder dataHolder)
	{
		MaCenterBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaCenterDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaCenterBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// center_cdに対するセッターとゲッターの集合
	public boolean setCenterCd(String center_cd)
	{
		this.center_cd = center_cd;
		return true;
	}
	public String getCenterCd()
	{
		return cutString(this.center_cd,CENTER_CD_MAX_LENGTH);
	}
	public String getCenterCdString()
	{
		return "'" + cutString(this.center_cd,CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_cd,CENTER_CD_MAX_LENGTH));
	}


	// center_naに対するセッターとゲッターの集合
	public boolean setCenterNa(String center_na)
	{
		this.center_na = center_na;
		return true;
	}
	public String getCenterNa()
	{
		return cutString(this.center_na,CENTER_NA_MAX_LENGTH);
	}
	public String getCenterNaString()
	{
		return "'" + cutString(this.center_na,CENTER_NA_MAX_LENGTH) + "'";
	}
	public String getCenterNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_na,CENTER_NA_MAX_LENGTH));
	}


	// center_kaに対するセッターとゲッターの集合
	public boolean setCenterKa(String center_ka)
	{
		this.center_ka = center_ka;
		return true;
	}
	public String getCenterKa()
	{
		return cutString(this.center_ka,CENTER_KA_MAX_LENGTH);
	}
	public String getCenterKaString()
	{
		return "'" + cutString(this.center_ka,CENTER_KA_MAX_LENGTH) + "'";
	}
	public String getCenterKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_ka,CENTER_KA_MAX_LENGTH));
	}


	// center_rnに対するセッターとゲッターの集合
	public boolean setCenterRn(String center_rn)
	{
		this.center_rn = center_rn;
		return true;
	}
	public String getCenterRn()
	{
		return cutString(this.center_rn,CENTER_RN_MAX_LENGTH);
	}
	public String getCenterRnString()
	{
		return "'" + cutString(this.center_rn,CENTER_RN_MAX_LENGTH) + "'";
	}
	public String getCenterRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_rn,CENTER_RN_MAX_LENGTH));
	}


	// center_rkに対するセッターとゲッターの集合
	public boolean setCenterRk(String center_rk)
	{
		this.center_rk = center_rk;
		return true;
	}
	public String getCenterRk()
	{
		return cutString(this.center_rk,CENTER_RK_MAX_LENGTH);
	}
	public String getCenterRkString()
	{
		return "'" + cutString(this.center_rk,CENTER_RK_MAX_LENGTH) + "'";
	}
	public String getCenterRkHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_rk,CENTER_RK_MAX_LENGTH));
	}


	// hojin_cdに対するセッターとゲッターの集合
	public boolean setHojinCd(String hojin_cd)
	{
		this.hojin_cd = hojin_cd;
		return true;
	}
	public String getHojinCd()
	{
		return cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH);
	}
	public String getHojinCdString()
	{
		return "'" + cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH) + "'";
	}
	public String getHojinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH));
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
		return "  center_cd = " + getCenterCdString()
			+ "  center_na = " + getCenterNaString()
			+ "  center_ka = " + getCenterKaString()
			+ "  center_rn = " + getCenterRnString()
			+ "  center_rk = " + getCenterRkString()
			+ "  hojin_cd = " + getHojinCdString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaCenterBean コピー後のクラス
	 */
	public MaCenterBean createClone()
	{
		MaCenterBean bean = new MaCenterBean();
		bean.setCenterCd(this.center_cd);
		bean.setCenterNa(this.center_na);
		bean.setCenterKa(this.center_ka);
		bean.setCenterRn(this.center_rn);
		bean.setCenterRk(this.center_rk);
		bean.setHojinCd(this.hojin_cd);
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
		if( !( o instanceof MaCenterBean ) ) return false;
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
