/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/09)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/04/09)初版作成
 */
public class mst150101_ButuryukeiroBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コードの長さ
	public static final int SIHAI_KB_MAX_LENGTH = 1;//仕配区分の長さ
	public static final int SIHAI_CD_MAX_LENGTH = 6;//仕配コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int NOHIN_CENTER_CD_MAX_LENGTH = 6;//納品センターコードの長さ
	public static final int KEIYU_CENTER_CD_MAX_LENGTH = 6;//経由センターコードの長さ
	public static final int TENHAI_CENTER_CD_MAX_LENGTH = 6;//店配センターコードの長さ
	public static final int CENTER_NOHIN_READ_TIME_MAX_LENGTH = 1;//センター納品リードタイムの長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String sihai_kb = null;	//仕配区分
	private String sihai_cd = null;	//仕配コード
	private String syohin_cd = null;	//商品コード
	private String tenpo_cd = null;	//店舗コード
	private String yuko_dt = null;	//有効日
	private String nohin_center_cd = null;	//納品センターコード
	private String keiyu_center_cd = null;	//経由センターコード
	private String tenhai_center_cd = null;	//店配センターコード
	private String center_nohin_read_time = null;	//センター納品リードタイム
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

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
	 * mst150101_ButuryukeiroBeanを１件のみ抽出したい時に使用する
	 */
	public static mst150101_ButuryukeiroBean getmst150101_ButuryukeiroBean(DataHolder dataHolder)
	{
		mst150101_ButuryukeiroBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst150101_ButuryukeiroDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst150101_ButuryukeiroBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// kanri_kbに対するセッターとゲッターの集合
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb()
	{
		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
	}
	public String getKanriKbString()
	{
		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getKanriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
	}


	// kanri_cdに対するセッターとゲッターの集合
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd()
	{
		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
	}
	public String getKanriCdString()
	{
		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
	}


	// sihai_kbに対するセッターとゲッターの集合
	public boolean setSihaiKb(String sihai_kb)
	{
		this.sihai_kb = sihai_kb;
		return true;
	}
	public String getSihaiKb()
	{
		return cutString(this.sihai_kb,SIHAI_KB_MAX_LENGTH);
	}
	public String getSihaiKbString()
	{
		return "'" + cutString(this.sihai_kb,SIHAI_KB_MAX_LENGTH) + "'";
	}
	public String getSihaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sihai_kb,SIHAI_KB_MAX_LENGTH));
	}


	// sihai_cdに対するセッターとゲッターの集合
	public boolean setSihaiCd(String sihai_cd)
	{
		this.sihai_cd = sihai_cd;
		return true;
	}
	public String getSihaiCd()
	{
		return cutString(this.sihai_cd,SIHAI_CD_MAX_LENGTH);
	}
	public String getSihaiCdString()
	{
		return "'" + cutString(this.sihai_cd,SIHAI_CD_MAX_LENGTH) + "'";
	}
	public String getSihaiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sihai_cd,SIHAI_CD_MAX_LENGTH));
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


	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}


	// nohin_center_cdに対するセッターとゲッターの集合
	public boolean setNohinCenterCd(String nohin_center_cd)
	{
		this.nohin_center_cd = nohin_center_cd;
		return true;
	}
	public String getNohinCenterCd()
	{
		return cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH);
	}
	public String getNohinCenterCdString()
	{
		return "'" + cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getNohinCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_center_cd,NOHIN_CENTER_CD_MAX_LENGTH));
	}


	// keiyu_center_cdに対するセッターとゲッターの集合
	public boolean setKeiyuCenterCd(String keiyu_center_cd)
	{
		this.keiyu_center_cd = keiyu_center_cd;
		return true;
	}
	public String getKeiyuCenterCd()
	{
		return cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH);
	}
	public String getKeiyuCenterCdString()
	{
		return "'" + cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getKeiyuCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH));
	}


	// tenhai_center_cdに対するセッターとゲッターの集合
	public boolean setTenhaiCenterCd(String tenhai_center_cd)
	{
		this.tenhai_center_cd = tenhai_center_cd;
		return true;
	}
	public String getTenhaiCenterCd()
	{
		return cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH);
	}
	public String getTenhaiCenterCdString()
	{
		return "'" + cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getTenhaiCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhai_center_cd,TENHAI_CENTER_CD_MAX_LENGTH));
	}


	// center_nohin_read_timeに対するセッターとゲッターの集合
	public boolean setCenterNohinReadTime(String center_nohin_read_time)
	{
		this.center_nohin_read_time = center_nohin_read_time;
		return true;
	}
	public String getCenterNohinReadTime()
	{
		return cutString(this.center_nohin_read_time,CENTER_NOHIN_READ_TIME_MAX_LENGTH);
	}
	public String getCenterNohinReadTimeString()
	{
		return "'" + cutString(this.center_nohin_read_time,CENTER_NOHIN_READ_TIME_MAX_LENGTH) + "'";
	}
	public String getCenterNohinReadTimeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.center_nohin_read_time,CENTER_NOHIN_READ_TIME_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  kanri_kb = " + getKanriKbString()
			+ "  kanri_cd = " + getKanriCdString()
			+ "  sihai_kb = " + getSihaiKbString()
			+ "  sihai_cd = " + getSihaiCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  nohin_center_cd = " + getNohinCenterCdString()
			+ "  keiyu_center_cd = " + getKeiyuCenterCdString()
			+ "  tenhai_center_cd = " + getTenhaiCenterCdString()
			+ "  center_nohin_read_time = " + getCenterNohinReadTimeString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst150101_ButuryukeiroBean コピー後のクラス
	 */
	public mst150101_ButuryukeiroBean createClone()
	{
		mst150101_ButuryukeiroBean bean = new mst150101_ButuryukeiroBean();
		bean.setKanriKb(this.kanri_kb);
		bean.setKanriCd(this.kanri_cd);
		bean.setSihaiKb(this.sihai_kb);
		bean.setSihaiCd(this.sihai_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setNohinCenterCd(this.nohin_center_cd);
		bean.setKeiyuCenterCd(this.keiyu_center_cd);
		bean.setTenhaiCenterCd(this.tenhai_center_cd);
		bean.setCenterNohinReadTime(this.center_nohin_read_time);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
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
		if( !( o instanceof mst150101_ButuryukeiroBean ) ) return false;
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
