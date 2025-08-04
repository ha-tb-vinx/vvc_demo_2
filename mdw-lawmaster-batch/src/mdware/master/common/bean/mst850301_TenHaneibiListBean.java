/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/14)初版作成
 */
public class mst850301_TenHaneibiListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;//小業種コードの長さ
	public static final int THEME_CD_MAX_LENGTH = 2;//テーマコード (FK)の長さ
	public static final int KEIRYO_HANKU_CD_MAX_LENGTH = 1;//計量販区コードの長さ
	public static final int SYOHIN_YOBIDASI_MAX_LENGTH = 4;//呼出NOの長さ
	public static final int HANEI_DT_MAX_LENGTH = 8;//反映日の長さ
	public static final int TEN_HANEI_DT_MAX_LENGTH = 8;//店別反映日の長さ
	public static final int UPDATE_TS_MAX_LENGTH    		= 20;	//更新日(YYYYMMDDHH24MMSS)の長さ
	public static final int DELETE_FG_MAX_LENGTH			= 1;	//削除フラグの長さ

	private String tenpo_cd = null;	//店舗コード
	private String kanji_rn = null;	//略式名称(漢字)
	private String s_gyosyu_cd = null;	//小業種コード
	private String theme_cd = null;	//テーマコード (FK)
	private String keiryo_hanku_cd = null;	//計量販区コード
	private String syohin_yobidasi = null;	//呼出NO
	private String hanei_dt = null;	//反映日
	private String ten_hanei_dt = null;	//店別反映日
	private String update_ts		= null;	//更新日(YYYYMMDDHH24MMSS)
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
	 * Beanを１件のみ抽出したい時に使用する
	 */
	public static mst850301_TenHaneibiListBean getBean(DataHolder dataHolder)
	{
		mst850301_TenHaneibiListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst850301_TenHaneibiListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst850301_TenHaneibiListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// kanji_rnに対するセッターとゲッターの集合
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
	public String getKanjiRn()
	{
		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
	}
	public String getKanjiRnString()
	{
		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
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


	// keiryo_hanku_cdに対するセッターとゲッターの集合
	public boolean setKeiryoHankuCd(String keiryo_hanku_cd)
	{
		this.keiryo_hanku_cd = keiryo_hanku_cd;
		return true;
	}
	public String getKeiryoHankuCd()
	{
		return cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH);
	}
	public String getKeiryoHankuCdString()
	{
		return "'" + cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH));
	}


	// syohin_yobidasiに対するセッターとゲッターの集合
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
	public String getSyohinYobidasi()
	{
		return cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH);
	}
	public String getSyohinYobidasiString()
	{
		return "'" + cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH) + "'";
	}
	public String getSyohinYobidasiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH));
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


	// ten_hanei_dtに対するセッターとゲッターの集合
	public boolean setTenHaneiDt(String ten_hanei_dt)
	{
		this.ten_hanei_dt = ten_hanei_dt;
		return true;
	}
	public String getTenHaneiDt()
	{
		return cutString(this.ten_hanei_dt,TEN_HANEI_DT_MAX_LENGTH);
	}
	public String getTenHaneiDtString()
	{
		return "'" + cutString(this.ten_hanei_dt,TEN_HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getTenHaneiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_hanei_dt,TEN_HANEI_DT_MAX_LENGTH));
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
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  kanji_rn        = " + getKanjiRnString()
			+ "  s_gyosyu_cd     = " + getSGyosyuCdString()
			+ "  theme_cd        = " + getThemeCdString()
			+ "  keiryo_hanku_cd = " + getKeiryoHankuCdString()
			+ "  syohin_yobidasi = " + getSyohinYobidasiString()
			+ "  hanei_dt        = " + getHaneiDtString()
			+ "  ten_hanei_dt    = " + getTenHaneiDtString()
			+ "  checkflg        = " + getCheckFlg() 
			+ "  optflg          = " +  getOptFlg()
			+ "  createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return Bean コピー後のクラス
	 */
	public mst850301_TenHaneibiListBean createClone()
	{
		mst850301_TenHaneibiListBean bean = new mst850301_TenHaneibiListBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setKanjiRn(this.kanji_rn);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setThemeCd(this.theme_cd);
		bean.setKeiryoHankuCd(this.keiryo_hanku_cd);
		bean.setSyohinYobidasi(this.syohin_yobidasi);
		bean.setHaneiDt(this.hanei_dt);
		bean.setTenHaneiDt(this.ten_hanei_dt);
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
		if( !( o instanceof mst850301_TenHaneibiListBean ) ) return false;
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
