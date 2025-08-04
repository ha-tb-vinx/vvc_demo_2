/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst830201_KeiryokiTheme用計量器テーマの表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst830201_KeiryokiTheme用計量器テーマの表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
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
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst830201_KeiryokiTheme用計量器テーマの表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst830201_KeiryokiTheme用計量器テーマの表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst830201_KeiryokiThemeListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int S_GYOSYU_CD_MAX_LENGTH 		= 4;	//小業種コードの長さ
	public static final int THEME_CD_MAX_LENGTH 			= 2;	//テーマコードの長さ
	public static final int THEME_NA_MAX_LENGTH 			= 30;	//テーマ名称の長さ
	public static final int HANEI_DT_MAX_LENGTH 			= 8;	//反映日の長さ
	public static final int UPDATE_TS_MAX_LENGTH    		= 20;	//更新日(YYYYMMDDHH24MMSS)の長さ
	public static final int UPDATE_TS_SHORT_MAX_LENGTH	= 8;	//更新日(YYYYMMDD)の長さ
	public static final int DELETE_FG_MAX_LENGTH			= 1;	//削除フラグの長さ

	private String s_gyosyu_cd		= null;	//小業種コード
	private String theme_cd		= null;	//テーマコード
	private String theme_na		= null;	//テーマ名称
	private String hanei_dt		= null;	//反映日
	private String update_ts		= null;	//更新日(YYYYMMDDHH24MMSS)
	private String update_ts_short	= null;	//更新日(YYYYMMDD)
	private String delete_fg		= null;	//削除フラグ
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
	public static mst830201_KeiryokiThemeListBean getRTenkabutuLBean(DataHolder dataHolder)
	{
		mst830201_KeiryokiThemeListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst830201_KeiryokiThemeListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst830201_KeiryokiThemeListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// theme_cdに対するセッターとゲッターの集合
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
	public String getThemeCd()
	{
		return cutString(this.theme_cd,THEME_CD_MAX_LENGTH);
	}
	public String getThemeCdString()
	{
		return "'" + cutString(this.theme_cd,THEME_CD_MAX_LENGTH) + "'";
	}
	public String getThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_cd,THEME_CD_MAX_LENGTH));
	}


	// theme_naに対するセッターとゲッターの集合
	public boolean setThemeNa(String theme_na)
	{
		this.theme_na = theme_na;
		return true;
	}
	public String getThemeNa()
	{
		return cutString(this.theme_na,THEME_NA_MAX_LENGTH);
	}
	public String getThemeNaString()
	{
		return "'" + cutString(this.theme_na,THEME_NA_MAX_LENGTH) + "'";
	}
	public String getThemeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_na,THEME_NA_MAX_LENGTH));
	}
	
	
	// hanei_dtに対するセッターとゲッターの集合
	public boolean setHaneiDt(String hanei_dt)
	{
		this.hanei_dt = hanei_dt;
		return true;
	}
	public String getHaneiDt()
	{
		return cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH);
	}
	public String getHaneiDtString()
	{
		return "'" + cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getHaneiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH));
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
		return 
		  "  s_gyosyu_cd = "    + getSGyosyuCdString()
		+ "  theme_cd = "       + getThemeCdString()
		+ "  theme_na = "       + getThemeNaString()
		+ "  hanei_dt = "       + getHaneiDtString()
		+ "  update_ts = "      + getUpdateTsString()
		+ "  delete_fg = "      + getDeleteFgString()
//
		+ "  update_ts_short = "+ getUpdateTsShortString()
		+ "  checkflg = "       + getCheckFlg()
		+ "  optflg   = "       + getOptFlg()
//
		+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTenkabutuLBean コピー後のクラス
	 */
	public mst830201_KeiryokiThemeListBean createClone()
	{
		mst830201_KeiryokiThemeListBean bean = new mst830201_KeiryokiThemeListBean();
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setThemeCd(this.theme_cd);
		bean.setThemeNa(this.theme_na);
		bean.setHaneiDt(this.hanei_dt);
		bean.setUpdateTs(this.update_ts);
		bean.setDeleteFg(this.delete_fg);
//
		bean.setUpdateTsShort(this.update_ts_short);
		bean.setCheckFlg(this.checkflg);
		bean.setOptFlg(this.optflg);
//
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
