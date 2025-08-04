/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのレコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.bean.mst870201_MstDateDM;
/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのレコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst870201_MstDateBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int MST_DATE_DT_MAX_LENGTH = 8;			// マスタ日付
	public static final int BAT_DATE_DT_MAX_LENGTH = 8;			// バッチ日付
	public static final int MST_DATE_DT_NEXT_MAX_LENGTH = 8;		// マスタ日付+1日
	public static final int BAT_DATE_DT_NEXT_MAX_LENGTH = 8;		// バッチ日付+1日
	public static final int MST_DATE_DT_2NEXT_MAX_LENGTH = 8;	// マスタ日付+2日
	public static final int BAT_DATE_DT_2NEXT_MAX_LENGTH = 8;	// バッチ日付+2日

	private String mst_date_dt 		= null;	// マスタ日付
	private String bat_date_dt 		= null;	// バッチ日付
	private String mst_date_dt_next 	= null;	// マスタ日付+1日
	private String bat_date_dt_next 	= null;	// バッチ日付+1日
	private String mst_date_dt_2next 	= null;	// マスタ日付+2日
	private String bat_date_dt_2next 	= null;	// バッチ日付+2日

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
	 * MstDateBeanを１件のみ抽出したい時に使用する
	 */
	public static mst870201_MstDateBean getBean(DataHolder dataHolder)
	{
		mst870201_MstDateBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst870201_MstDateDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst870201_MstDateBean )ite.next();
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


	// mst_date_dt_nextに対するセッターとゲッターの集合
	public boolean setMstDateDtNext(String mst_date_dt_next)
	{
		this.mst_date_dt_next = mst_date_dt_next;
		return true;
	}
	public String getMstDateDtNext()
	{
		return cutString(this.mst_date_dt_next,MST_DATE_DT_NEXT_MAX_LENGTH);
	}
	public String getMstDateDtNextString()
	{
		return "'" + cutString(this.mst_date_dt_next,MST_DATE_DT_NEXT_MAX_LENGTH) + "'";
	}
	public String getMstDateDtNextHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mst_date_dt_next,MST_DATE_DT_NEXT_MAX_LENGTH));
	}


	// bat_date_dt_nextに対するセッターとゲッターの集合
	public boolean setBatDateDtNext(String bat_date_dt_next)
	{
		this.bat_date_dt_next = bat_date_dt_next;
		return true;
	}
	public String getBatDateDtNext()
	{
		return cutString(this.bat_date_dt_next,BAT_DATE_DT_NEXT_MAX_LENGTH);
	}
	public String getBatDateDtNextString()
	{
		return "'" + cutString(this.bat_date_dt_next,BAT_DATE_DT_NEXT_MAX_LENGTH) + "'";
	}
	public String getBatDateDtNextHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bat_date_dt_next,BAT_DATE_DT_NEXT_MAX_LENGTH));
	}


	// mst_date_dt_2nextに対するセッターとゲッターの集合
	public boolean setMstDateDt2Next(String mst_date_dt_2next)
	{
		this.mst_date_dt_2next = mst_date_dt_2next;
		return true;
	}
	public String getMstDateDt2Next()
	{
		return cutString(this.mst_date_dt_2next,MST_DATE_DT_NEXT_MAX_LENGTH);
	}
	public String getMstDateDt2NextString()
	{
		return "'" + cutString(this.mst_date_dt_2next,MST_DATE_DT_NEXT_MAX_LENGTH) + "'";
	}
	public String getMstDateDt2NextHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.mst_date_dt_2next,MST_DATE_DT_NEXT_MAX_LENGTH));
	}


	// bat_date_dt_2nextに対するセッターとゲッターの集合
	public boolean setBatDateDt2Next(String bat_date_dt_2next)
	{
		this.bat_date_dt_2next = bat_date_dt_2next;
		return true;
	}
	public String getBatDateDt2Next()
	{
		return cutString(this.bat_date_dt_next,BAT_DATE_DT_2NEXT_MAX_LENGTH);
	}
	public String getBatDateDt2NextString()
	{
		return "'" + cutString(this.bat_date_dt_next,BAT_DATE_DT_2NEXT_MAX_LENGTH) + "'";
	}
	public String getBatDateDt2NextHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bat_date_dt_2next,BAT_DATE_DT_2NEXT_MAX_LENGTH));
	}




	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  mst_date_dt = " + getMstDateDtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return NameCtfBean コピー後のクラス
	 */
	public mst870201_MstDateBean createClone()
	{
		mst870201_MstDateBean bean = new mst870201_MstDateBean();
		bean.setMstDateDt(this.mst_date_dt);
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
		if( !( o instanceof mst870201_MstDateBean ) ) return false;
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
