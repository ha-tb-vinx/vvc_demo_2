/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/15)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/15)初版作成
 */
public class mst730101_BundleMixPlanBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KIKAKU_TOKUBAINO_CD_MAX_LENGTH = 9;		//企画特売No.の長さ
	public static final int NAME_NA_MAX_LENGTH = 30;					//名称の長さ
	public static final int KIKAKU_ST_DT_MAX_LENGTH = 8;				//開始日の長さ
	public static final int KIKAKU_ED_DT_MAX_LENGTH = 8;				//終了日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;				//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;			//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;				//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;			//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;				//削除フラグの長さ
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;			//登録日フラグの長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;					//変更日フラグの長さ

	private String kikaku_tokubaino_cd = null;						//企画特売No.
	private String name_na = null;									//名称
	private String kikaku_st_dt = null;								//開始日
	private String kikaku_ed_dt = null;								//終了日
	private String insert_ts = null;									//作成年月日
	private String insert_user_id = null;								//作成者社員ID
	private String update_ts = null;									//更新年月日
	private String update_user_id = null;								//更新者社員ID
	private String delete_fg = null;									//削除フラグ
	private String sinki_toroku_dt = null;							//登録日
	private String henko_dt = null;									//変更日

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
	 * mst730101_BundleMixPlanBeanを１件のみ抽出したい時に使用する
	 */
	public static mst730101_BundleMixPlanBean getmst730101_BundleMixPlanBean(DataHolder dataHolder)
	{
		mst730101_BundleMixPlanBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst730101_BundleMixPlanDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst730101_BundleMixPlanBean )ite.next();
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
	public boolean setKikakuTokubainoCd(String kikaku_tokubaino_cd)
	{
		this.kikaku_tokubaino_cd = kikaku_tokubaino_cd;
		return true;
	}
	public String getKikakuTokubainoCd()
	{
		return cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_MAX_LENGTH);
	}
	public String getKikakuTokubainoCdString()
	{
		return "'" + cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_MAX_LENGTH) + "'";
	}
	public String getKikakuTokubainoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_tokubaino_cd,KIKAKU_TOKUBAINO_CD_MAX_LENGTH));
	}


	// name_naに対するセッターとゲッターの集合
	public boolean setNameNa(String name_na)
	{
		this.name_na = name_na;
		return true;
	}
	public String getNameNa()
	{
		return cutString(this.name_na,NAME_NA_MAX_LENGTH);
	}
	public String getNameNaString()
	{
		return "'" + cutString(this.name_na,NAME_NA_MAX_LENGTH) + "'";
	}
	public String getNameNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.name_na,NAME_NA_MAX_LENGTH));
	}


	// kikaku_st_dtに対するセッターとゲッターの集合
	public boolean setKikakuStDt(String kikaku_st_dt)
	{
		this.kikaku_st_dt = kikaku_st_dt;
		return true;
	}
	public String getKikakuStDt()
	{
		return cutString(this.kikaku_st_dt,KIKAKU_ST_DT_MAX_LENGTH);
	}
	public String getKikakuStDtString()
	{
		return "'" + cutString(this.kikaku_st_dt,KIKAKU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getKikakuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_st_dt,KIKAKU_ST_DT_MAX_LENGTH));
	}


	// kikaku_ed_dtに対するセッターとゲッターの集合
	public boolean setKikakuEdDt(String kikaku_ed_dt)
	{
		this.kikaku_ed_dt = kikaku_ed_dt;
		return true;
	}
	public String getKikakuEdDt()
	{
		return cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_MAX_LENGTH);
	}
	public String getKikakuEdDtString()
	{
		return "'" + cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getKikakuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_ed_dt,KIKAKU_ED_DT_MAX_LENGTH));
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

//A.Tozaki

	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
		return true;
	}
	public String getSinkiTorokuDt()
	{
		return cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH);
	}
	public String getSinkiTorokuDtString()
	{
		return "'" + cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH) + "'";
	}
	public String getSinkiTorokuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH));
	}

	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt,HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt,HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt,HENKO_DT_MAX_LENGTH));
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  kikaku_tokubaino_cd = " + getKikakuTokubainoCdString()
			+ "  name_na = " + getNameNaString()
			+ "  kikaku_st_dt = " + getKikakuStDtString()
			+ "  kikaku_ed_dt = " + getKikakuEdDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  sinki_toroku_dt = " + getSinkiTorokuDtString()
			+ "  henko_dt = " + getHenkoDtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RBANDLEMIXKIKAKUTOKUBAIBean コピー後のクラス
	 */
	public mst730101_BundleMixPlanBean createClone()
	{
		mst730101_BundleMixPlanBean bean = new mst730101_BundleMixPlanBean();
		bean.setKikakuTokubainoCd(this.kikaku_tokubaino_cd);
		bean.setNameNa(this.name_na);
		bean.setKikakuStDt(this.kikaku_st_dt);
		bean.setKikakuEdDt(this.kikaku_ed_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
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
		if( !( o instanceof mst730101_BundleMixPlanBean ) ) return false;
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
