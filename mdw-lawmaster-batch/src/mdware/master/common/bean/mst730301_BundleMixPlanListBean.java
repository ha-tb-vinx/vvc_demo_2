/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売登録画面の検索結果レコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売登録の検索結果レコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/14)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売登録画面の検索結果レコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売登録画面の検索結果レコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/14)初版作成
 */
public class mst730301_BundleMixPlanListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KIKAKU_TOKUBAINO_CD_LENGTH = 9;		//企画特売No.の長さ
	public static final int EXIST_SYOHIN_LENGTH = 1;
	public static final int NAME_NA_LENGTH = 30;					//名称の長さ
	public static final int KIKAKU_ST_DT_LENGTH = 8;				//開始日の長さ
	public static final int KIKAKU_ED_DT_LENGTH = 8;				//終了日の長さ

	public static final int TENPO_CDL_MAX_LENGTH = 1024;			//店舗コード配列の長さ
	public static final int TKANJI_RNL_MAX_LENGTH = 1024;			//店舗略称配列の長さ
	public static final int FLGL_MAX_LENGTH = 1024;				//店舗存在フラグ配列の長さ
	public static final int INSERT_TSL_MAX_LENGTH = 1024;			//作成年月日配列の長さ
	public static final int INSERT_USER_IDL_MAX_LENGTH = 1024;	//作成者社員ID配列の長さ
	public static final int UPDATE_TSL_MAX_LENGTH = 1024;			//更新年月日配列の長さ
	public static final int UPDATE_USER_IDL_MAX_LENGTH = 1024;	//更新者社員ID配列の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;		//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;				//削除フラグの長さ
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択の長さ
	public static final int OLDFLGL_MAX_LENGTH = 1024;			//店舗存在フラグ配列の長さ

	
	private String kikaku_tokubaino_cd = null;		//企画特売No.
	private String exist_syohin = null;			//取扱商品有無（1：有、2：無）
	private String name_na = null;					//名称
	private String kikaku_st_dt = null;			//開始日
	private String kikaku_ed_dt = null;			//終了日

	private String tenpo_cdl = null;				//店舗コード配列
	private String tkanji_rnl = null;				//店舗略称配列
	private String flgl = null;					//店舗存在フラグ配列
	private String insert_tsl = null;				//作成年月日配列
	private String insert_user_idl = null;			//作成者社員ID配列
	private String update_tsl = null;				//更新年月日配列
	private String update_user_idl = null;			//更新者社員ID配列
	private String insert_ts = null;				//作成年月日
	private String insert_user_id = null;			//作成者社員ID
	private String update_ts = null;				//更新年月日
	private String update_user_id = null;			//更新者社員ID
	private String delete_fg = null;				//削除フラグ
	private String sentaku = null;					//処理選択
	private String oldFlgl = null;					//更新前店舗存在フラグ配列

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
	 * mst730301_BundleMixPlanListBeanを１件のみ抽出したい時に使用する
	 */
	public static mst730301_BundleMixPlanListBean getmst730301_BundleMixPlanListBean(DataHolder dataHolder)
	{
		mst730301_BundleMixPlanListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst730301_BundleMixPlanListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst730301_BundleMixPlanListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	// kikaku_tokubaino_cdに対するセッターとゲッターの集合
	public boolean setKikakuTokubaiNoCd(String kikaku_tokubaino_cd)
	{
		this.kikaku_tokubaino_cd = kikaku_tokubaino_cd;
		return true;
	}
	public String getKikakuTokubaiNoCd()
	{
		return cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_LENGTH);
	}
	public String getKikakuTokubaiNoCdString()
	{
		return "'" + cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_LENGTH) + "'";
	}
	public String getKikakuTokubaiNoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_LENGTH));
	}


	// kikaku_tokubaino_cdに対するセッターとゲッターの集合
	public boolean setExistSyohin(String exist_syohin)
	{
		this.exist_syohin = exist_syohin;
		return true;
	}
	public String getExistSyohin()
	{
		return cutString(this.exist_syohin,EXIST_SYOHIN_LENGTH);
	}
	public String getExistSyohinString()
	{
		return "'" + cutString(this.exist_syohin,EXIST_SYOHIN_LENGTH) + "'";
	}
	public String getExistSyohinHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.exist_syohin,EXIST_SYOHIN_LENGTH));
	}
	
	
	// name_naに対するセッターとゲッターの集合
	public boolean setNameNa(String name_na)
	{
		this.name_na = name_na;
		return true;
	}
	public String getNameNa()
	{
		return cutString(this.name_na,NAME_NA_LENGTH);
	}
	public String getNameNaString()
	{
		return "'" + cutString(this.name_na,NAME_NA_LENGTH) + "'";
	}
	public String getNameNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.name_na,NAME_NA_LENGTH));
	}

	// kikaku_st_dtに対するセッターとゲッターの集合
	public boolean setKikakuStDt(String kikaku_st_dt)
	{
		this.kikaku_st_dt = kikaku_st_dt;
		return true;
	}
	public String getKikakuStDt()
	{
		return cutString(this.kikaku_st_dt,KIKAKU_ST_DT_LENGTH);
	}
	public String getKikakuStDtString()
	{
		return "'" + cutString(this.kikaku_st_dt,KIKAKU_ST_DT_LENGTH) + "'";
	}
	public String getKikakuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_st_dt,KIKAKU_ST_DT_LENGTH));
	}

	// kikaku_ed_dtに対するセッターとゲッターの集合
	public boolean setKikakuEdDt(String kikaku_ed_dt)
	{
		this.kikaku_ed_dt = kikaku_ed_dt;
		return true;
	}
	public String getKikakuEdDt()
	{
		return cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_LENGTH);
	}
	public String getKikakuEdDtString()
	{
		return "'" + cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_LENGTH) + "'";
	}
	public String getKikakuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_LENGTH));
	}
	
	

	// tenpo_cdlに対するセッターとゲッターの集合
	public boolean setTenpoCdl(String tenpo_cdl)
	{
		this.tenpo_cdl = tenpo_cdl;
		return true;
	}
	public String getTenpoCdl()
	{
		return cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH);
	}
	public String getTenpoCdlString()
	{
		return "'" + cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH) + "'";
	}
	public String getTenpoCdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH));
	}


	// tkanji_rnlに対するセッターとゲッターの集合
	public boolean setTkanjiRnl(String tkanji_rnl)
	{
		this.tkanji_rnl = tkanji_rnl;
		return true;
	}
	public String getTkanjiRnl()
	{
		return cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH);
	}
	public String getTkanjiRnlString()
	{
		return "'" + cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH) + "'";
	}
	public String getTkanjiRnlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH));
	}


	// flglに対するセッターとゲッターの集合
	public boolean setFlgl(String flgl)
	{
		this.flgl = flgl;
		return true;
	}
	public String getFlgl()
	{
		return cutString(this.flgl,FLGL_MAX_LENGTH);
	}
	public String getFlglString()
	{
		return "'" + cutString(this.flgl,FLGL_MAX_LENGTH) + "'";
	}
	public String getFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.flgl,FLGL_MAX_LENGTH));
	}
	
	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTsl(String insert_tsl)
	{
		this.insert_tsl = insert_tsl;
		return true;
	}
	public String getInsertTsl()
	{
		return cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH);
	}
	public String getInsertTslString()
	{
		return "'" + cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH) + "'";
	}
	public String getInsertTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH));
	}


	// insert_user_idlに対するセッターとゲッターの集合
	public boolean setInsertUserIdl(String insert_user_idl)
	{
		this.insert_user_idl = insert_user_idl;
		return true;
	}
	public String getInsertUserIdl()
	{
		return cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH);
	}
	public String getInsertUserIdlString()
	{
		return "'" + cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH));
	}


	// update_tslに対するセッターとゲッターの集合
	public boolean setUpdateTsl(String update_tsl)
	{
		this.update_tsl = update_tsl;
		return true;
	}
	public String getUpdateTsl()
	{
		return cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getUpdateTslString()
	{
		return "'" + cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getUpdateTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH));
	}


	// update_user_idlに対するセッターとゲッターの集合
	public boolean setUpdateUserIdl(String update_user_idl)
	{
		this.update_user_idl = update_user_idl;
		return true;
	}
	public String getUpdateUserIdl()
	{
		return cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH);
	}
	public String getUpdateUserIdlString()
	{
		return "'" + cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH));
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

	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}

	
	
	// oldFlglに対するセッターとゲッターの集合
	public boolean setOldFlgl(String oldFlgl)
	{
		this.oldFlgl = oldFlgl;
		return true;
	}
	public String getOldFlgl()
	{
		return cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH);
	}
	public String getOldFlglString()
	{
		return "'" + cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH) + "'";
	}
	public String getOldFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH));
	}


	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return
			  "  kikaku_tokubaino_cd = " + getKikakuTokubaiNoCdString()
			+ "  exist_syohin = " + getExistSyohinString()
			+ "  name_na = " + getNameNaString()
			+ "  kikaku_st_dt = " + getKikakuStDtString()
			+ "  kikaku_ed_dt = " + getKikakuEdDtString()
			+ "  tenpo_cdl = " + getTenpoCdlString()
			+ "  tkanji_rnl = " + getTkanjiRnlString()
			+ "  flgl = " + getFlglString()
			+ "  insert_tsl = " + getInsertTslString()
			+ "  insert_user_idl = " + getInsertUserIdlString()
			+ "  update_tsl = " + getUpdateTslString()
			+ "  update_user_idl = " + getUpdateUserIdlString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  sentaku = " + getSentaku()
			+ "  oldFlgl = " + getOldFlglString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst730301_BundleMixPlanListBean コピー後のクラス
	 */
	public mst730301_BundleMixPlanListBean createClone()
	{
		mst730301_BundleMixPlanListBean bean = new mst730301_BundleMixPlanListBean();
		bean.setKikakuTokubaiNoCd(this.getKikakuTokubaiNoCd());
		bean.setExistSyohin(this.getExistSyohin());
		bean.setNameNa(this.getNameNa());
		bean.setKikakuStDt(this.getKikakuStDt());
		bean.setKikakuEdDt(this.getKikakuEdDt());
		bean.setTenpoCdl(this.tenpo_cdl);
		bean.setTkanjiRnl(this.tkanji_rnl);
		bean.setFlgl(this.flgl);
		bean.setInsertTsl(this.insert_tsl);
		bean.setInsertUserIdl(this.insert_user_idl);
		bean.setUpdateTsl(this.update_tsl);
		bean.setUpdateUserIdl(this.update_user_idl);
		bean.setInsertTs(this.getInsertTs());
		bean.setInsertUserId(this.getInsertUserId());
		bean.setUpdateTs(this.getUpdateTs());
		bean.setUpdateUserId(this.getUpdateUserId());
		bean.setDeleteFg(this.getDeleteFg());
		bean.setSentaku(this.getSentaku());
		bean.setOldFlgl(this.getOldFlgl());
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
		if( !( o instanceof mst730301_BundleMixPlanListBean ) ) return false;
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
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
