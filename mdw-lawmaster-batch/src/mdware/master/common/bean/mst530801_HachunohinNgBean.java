/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店発注納品不可日マスタ（生鮮）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店発注納品不可日マスタ（生鮮）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店発注納品不可日マスタ（生鮮）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店発注納品不可日マスタ（生鮮）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
public class mst530801_HachunohinNgBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int YMD_DT_MAX_LENGTH = 8;//年月日の長さ
	public static final int HACHU_NG_FG_MAX_LENGTH = 1;//発注不可の長さ
	public static final int NOHIN_NG_FG_MAX_LENGTH = 1;//納品不可の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_TS_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
//	******** ＤＢ Ver3.6修正箇所 *****************************************
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String tenpo_cd = null;	//店舗コード (FK)
	private String ymd_dt = null;	//年月日
	private String hachu_ng_fg = null;	//発注不可
	private String nohin_ng_fg = null;	//納品不可
	private String insert_ts = null;	//作成年月日
	private String insert_user_ts = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
//	******** ＤＢ Ver3.6修正箇所 *****************************************
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
	 * RHACHUNOHINNGBeanを１件のみ抽出したい時に使用する
	 */
	public static mst530801_HachunohinNgBean getRHACHUNOHINNGBean(DataHolder dataHolder)
	{
		mst530801_HachunohinNgBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst530801_HachunohinNgDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst530801_HachunohinNgBean )ite.next();
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


	// ymd_dtに対するセッターとゲッターの集合
	public boolean setYmdDt(String ymd_dt)
	{
		this.ymd_dt = ymd_dt;
		return true;
	}
	public String getYmdDt()
	{
		return cutString(this.ymd_dt,YMD_DT_MAX_LENGTH);
	}
	public String getYmdDtString()
	{
		return "'" + cutString(this.ymd_dt,YMD_DT_MAX_LENGTH) + "'";
	}
	public String getYmdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ymd_dt,YMD_DT_MAX_LENGTH));
	}


	// hachu_ng_fgに対するセッターとゲッターの集合
	public boolean setHachuNgFg(String hachu_ng_fg)
	{
		this.hachu_ng_fg = hachu_ng_fg;
		return true;
	}
	public String getHachuNgFg()
	{
		return cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH);
	}
	public String getHachuNgFgString()
	{
		return "'" + cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH) + "'";
	}
	public String getHachuNgFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH));
	}


	// nohin_ng_fgに対するセッターとゲッターの集合
	public boolean setNohinNgFg(String nohin_ng_fg)
	{
		this.nohin_ng_fg = nohin_ng_fg;
		return true;
	}
	public String getNohinNgFg()
	{
		return cutString(this.nohin_ng_fg,NOHIN_NG_FG_MAX_LENGTH);
	}
	public String getNohinNgFgString()
	{
		return "'" + cutString(this.nohin_ng_fg,NOHIN_NG_FG_MAX_LENGTH) + "'";
	}
	public String getNohinNgFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_ng_fg,NOHIN_NG_FG_MAX_LENGTH));
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


	// insert_user_tsに対するセッターとゲッターの集合
	public boolean setInsertUserTs(String insert_user_ts)
	{
		this.insert_user_ts = insert_user_ts;
		return true;
	}
	public String getInsertUserTs()
	{
		return cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH);
	}
	public String getInsertUserTsString()
	{
		return "'" + cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH) + "'";
	}
	public String getInsertUserTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH));
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
//	******** ＤＢ Ver3.6修正箇所 *****************************************
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserTs()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserTsString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserTsHTMLString()
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
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  ymd_dt = " + getYmdDtString()
			+ "  hachu_ng_fg = " + getHachuNgFgString()
			+ "  nohin_ng_fg = " + getNohinNgFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_ts = " + getInsertUserTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserTsString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RHACHUNOHINNGBean コピー後のクラス
	 */
	public mst530801_HachunohinNgBean createClone()
	{
		mst530801_HachunohinNgBean bean = new mst530801_HachunohinNgBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYmdDt(this.ymd_dt);
		bean.setHachuNgFg(this.hachu_ng_fg);
		bean.setNohinNgFg(this.nohin_ng_fg);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserTs(this.insert_user_ts);
		bean.setUpdateTs(this.update_ts);
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		bean.setUpdateUserTs(this.update_user_id);
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
		if( !( o instanceof mst530801_HachunohinNgBean ) ) return false;
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
