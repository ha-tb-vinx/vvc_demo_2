/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/03)初版作成
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
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/03)初版作成
 */
public class mst540101_HachuNohinNgBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH 		 = 6;//店舗コードの長さ
	public static final int TENPO_KANJI_RN_MAX_LENGTH = 20;//店舗略式名称(漢字)の長さ
	public static final int YMD_DT_MAX_LENGTH		 = 8;//年月日の長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int HACHU_NG_FG_MAX_LENGTH	 = 1;//発注不可の長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int NOHIN_NG_FG_MAX_LENGTH	 = 1;//納品不可の長さ
	public static final int INSERT_TS_MAX_LENGTH		 = 20;//作成年月日の長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int INSERT_USER_TS_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int UPDATE_TS_MAX_LENGTH		 = 20;//更新者社員ID更新年月日の長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int UPDATE_USER_TS_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int DELETE_FG_MAX_LENGTH 	= 1;//削除フラグの長さ

	private String tenpo_cd		 = null;	//店舗コード
	private String tenpo_kanji_rn	 = null;	//店舗略式名称(漢字)
	private String ymd_dt			 = null;	//年月日
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String hachu_ng_fg		 = null;	//発注不可
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String nohin_ng_fg		 = null;	//納品不可
	private String insert_ts		 = null;	//作成年月日
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String insert_user_ts	 = null;	//作成者社員ID
	private String insert_user_id	 = null;	//作成者社員ID
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String update_ts		 = null;	//更新年月日
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String update_user_ts	 = null;	//更新者社員ID
	private String update_user_id	 = null;	//更新者社員ID
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String delete_fg		 = null;	//削除フラグ

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
	 *  mst540101_HachuNohinNgBeanを１件のみ抽出したい時に使用する
	 */
	public static mst540101_HachuNohinNgBean getMst540101_HachuNohinNgBean(DataHolder dataHolder)
	{
		mst540101_HachuNohinNgBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst540101_HachuNohinNgDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst540101_HachuNohinNgBean )ite.next();
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
	
	// tenpo_kanji_rn対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
		return cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH);
	}
	public String getTenpoKanjiRnString()
	{
		return "'" + cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getTenpoKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH));
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


//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	// hachu_ng_fgに対するセッターとゲッターの集合
//	public boolean setHachuNgFg(String hachu_ng_fg)
//	{
//		this.hachu_ng_fg = hachu_ng_fg;
//		return true;
//	}
//	public String getHachuNgFg()
//	{
//		return cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH);
//	}
//	public String getHachuNgFgString()
//	{
//	return "'" + cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH) + "'";
//	}
//	public String getHachuNgFgHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hachu_ng_fg,HACHU_NG_FG_MAX_LENGTH));
//	}
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）


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


//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	// insert_user_tsに対するセッターとゲッターの集合
//	public boolean setInsertUserTs(String insert_user_ts)
//	{
//		this.insert_user_ts = insert_user_ts;
//		return true;
//	}
//	public String getInsertUserTs()
//	{
//		return cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH);
//	}
//	public String getInsertUserTsString()
//	{
//		return "'" + cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH) + "'";
//	}
//	public String getInsertUserTsHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.insert_user_ts,INSERT_USER_TS_MAX_LENGTH));
//	}
	
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
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）


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


//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	// update_user_tsに対するセッターとゲッターの集合
//	public boolean setUpdateUserTs(String update_user_ts)
//	{
//		this.update_user_ts = update_user_ts;
//		return true;
//	}
//	public String getUpdateUserTs()
//	{
//		return cutString(this.update_user_ts,UPDATE_USER_TS_MAX_LENGTH);
//	}
//	public String getUpdateUserTsString()
//	{
//		return "'" + cutString(this.update_user_ts,UPDATE_USER_TS_MAX_LENGTH) + "'";
//	}
//	public String getUpdateUserTsHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.update_user_ts,UPDATE_USER_TS_MAX_LENGTH));
//	}

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
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）


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
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  ymd_dt = " + getYmdDtString()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  hachu_ng_fg = " + getHachuNgFgString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  nohin_ng_fg = " + getNohinNgFgString()
			+ "  insert_ts = " + getInsertTsString()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  insert_user_ts = " + getInsertUserTsString()
			+ "  insert_user_ts = " + getInsertUserIdString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  update_ts = " + getUpdateTsString()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  update_user_ts = " + getUpdateUserTsString()
			+ "  update_user_ts = " + getUpdateUserIdString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return  RHACHUNOHINNGBean コピー後のクラス
	 */
	public  mst540101_HachuNohinNgBean createClone()
	{
		 mst540101_HachuNohinNgBean bean = new  mst540101_HachuNohinNgBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYmdDt(this.ymd_dt);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setHachuNgFg(this.hachu_ng_fg);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setNohinNgFg(this.nohin_ng_fg);
		bean.setInsertTs(this.insert_ts);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setInsertUserTs(this.insert_user_ts);
		bean.setInsertUserId(this.insert_user_id);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setUpdateTs(this.update_ts);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setUpdateUserTs(this.update_user_ts);
		bean.setUpdateUserId(this.update_user_id);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
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
		if( !( o instanceof  mst540101_HachuNohinNgBean ) ) return false;
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
