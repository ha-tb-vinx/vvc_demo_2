/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタの表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタの表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタの表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタの表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst840201_TenkabutuListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENKABUTU_CD_MAX_LENGTH 		= 4;	//添加物コードの長さ
	public static final int TENKABUTU_NA_MAX_LENGTH 		= 30;	//添加物名称の長さ
	public static final int UPDATE_TS_MAX_LENGTH    		= 20;	//更新日(YYYYMMDDHH24MMSS)の長さ
	public static final int UPDATE_TS_SHORT_MAX_LENGTH	= 8;	//更新日(YYYYMMDD)の長さ
	public static final int DELETE_FG_MAX_LENGTH			= 1;	//削除フラグの長さ

	private String tenkabutu_cd	= null;	//添加物コード
	private String tenkabutu_na	= null;	//添加物名称
	private String update_ts		= null;	//更新日(YYYYMMDDHH24MMSS)
	private String update_ts_short	= null;	//更新日(YYYYMMDD)
	private String delete_fg		= null;	//削除フラグ
	private String ChangeFlg		= null;	//項目変更フラグ
	private String checkflg		= "0";
	private String optflg			= "0";

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
	 * RTenkabutuLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst840201_TenkabutuListBean getRTenkabutuLBean(DataHolder dataHolder)
	{
		mst840201_TenkabutuListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst840201_TenkabutuListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst840201_TenkabutuListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// tenkabutu_cdに対するセッターとゲッターの集合
	public boolean setTenkabutuCd(String tenkabutu_cd)
	{
		this.tenkabutu_cd = tenkabutu_cd;
		return true;
	}
	public String getTenkabutuCd()
	{
		return cutString(this.tenkabutu_cd,TENKABUTU_CD_MAX_LENGTH);
	}
	public String getTenkabutuCdString()
	{
		return "'" + cutString(this.tenkabutu_cd,TENKABUTU_CD_MAX_LENGTH) + "'";
	}
	public String getTenkabutuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenkabutu_cd,TENKABUTU_CD_MAX_LENGTH));
	}


	// tenkabutu_naに対するセッターとゲッターの集合
	public boolean setTenkabutuNa(String tenkabutu_na)
	{
		this.tenkabutu_na = tenkabutu_na;
		return true;
	}
	public String getTenkabutuNa()
	{
		return cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH);
	}
	public String getTenkabutuNaString()
	{
		return "'" + cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH) + "'";
	}
	public String getTenkabutuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH));
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
	
	// update_ts_shortに対するセッターとゲッターの集合
	public boolean setUpdateTsShort(String update_ts_short)
	{
		this.update_ts_short = update_ts_short;
		return true;
	}
	public String getUpdateTsShort()
	{
		return cutString(this.update_ts_short,UPDATE_TS_SHORT_MAX_LENGTH);
	}
	public String getUpdateTsShortString()
	{
		return "'" + cutString(this.update_ts_short,UPDATE_TS_SHORT_MAX_LENGTH) + "'";
	}
	public String getUpdateTsShortHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts_short,UPDATE_TS_SHORT_MAX_LENGTH));
	}


	// delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
		return true;
	}
	public String getDeleteFg()
	{
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}

	// ChangeFlgに対するセッターとゲッターの集合
	public boolean setChangeFlg(String ChangeFlg)
	{
		this.ChangeFlg = ChangeFlg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.ChangeFlg;
	}

	// checkflgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String checkflg)
	{
		this.checkflg = checkflg;
		return true;
	}
	public String getCheckFlg()
	{
		return checkflg;
	}

	// optflgに対するセッターとゲッターの集合
	public boolean setOptFlg(String optflg)
	{
		this.optflg = optflg;
		return true;
	}
	public String getOptFlg()
	{
		return optflg;
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenkabutu_cd = " + getTenkabutuCdString()
			+ "  tenkabutu_na = " 	+ getTenkabutuNaString()
			+ "  update_ts = " 		+ getUpdateTsString()
			+ "  delete_fg = " 		+ getDeleteFgString()
			+ "  check_flg = " 		+ getCheckFlg()
			+ "  opt_flg = " 		+ getOptFlg()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTenkabutuLBean コピー後のクラス
	 */
	public mst840201_TenkabutuListBean createClone()
	{
		mst840201_TenkabutuListBean bean = new mst840201_TenkabutuListBean();
		bean.setTenkabutuCd(this.tenkabutu_cd);
		bean.setTenkabutuNa(this.tenkabutu_na);
		bean.setUpdateTs(this.update_ts);
		bean.setDeleteFg(this.delete_fg);
		bean.setCheckFlg(this.checkflg);
		bean.setOptFlg(this.optflg);
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
		if( !( o instanceof mst840201_TenkabutuListBean ) ) return false;
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
