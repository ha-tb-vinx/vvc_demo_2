/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別品種連関マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別品種連関マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別品種連関マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別品種連関マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
public class mst640101_SyukeiSyubetuHinsyuRenkanBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	/** 集計種別コードデータ長 */
	public static final int SYUKEI_SYUBETU_CD_MAX_LENGTH = 6;
	/** 集計品種コードデータ長 */	
	public static final int SYUKEI_HINSYU_CD_MAX_LENGTH = 4;
	/** 作成年月日データ長 */
	public static final int INSERT_TS_MAX_LENGTH = 20;
	/** 作成者社員IDデータ長 */
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;
	/** 更新年月日データ長 */
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	/** 更新者社員IDデータ長 */
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;
	/** 削除フラグデータ長 */
	public static final int DELETE_FG_MAX_LENGTH = 1;

	private String syukei_syubetu_cd = null;
	private String syukei_hinsyu_cd = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String delete_fg = null;

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	/**  ＤＢ更新を行う時に役に立つフラグ 	 
	 * @return createDatabase createDatabase
	 */ 	
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

	/**
	 * mst640101_SyukeiSyubetuHinsyuRenkanBeanBeanを１件のみ抽出したい時に使用する
	 * @param dataHolder dataHolder
	 * @return mst640101_SyukeiSyubetuHinsyuRenkanBean mst640101_SyukeiSyubetuHinsyuRenkanBean
	 */
	public static mst640101_SyukeiSyubetuHinsyuRenkanBean getmst640101_SyukeiSyubetuHinsyuRenkanBeanBean(DataHolder dataHolder)
	{
		mst640101_SyukeiSyubetuHinsyuRenkanBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst640101_SyukeiSyubetuHinsyuRenkanDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst640101_SyukeiSyubetuHinsyuRenkanBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// syukei_syubetu_cdに対するセッターとゲッターの集合
	public boolean setSyukeiSyubetuCd(String syukei_syubetu_cd)
	{
		this.syukei_syubetu_cd = syukei_syubetu_cd;
		return true;
	}
	public String getSyukeiSyubetuCd()
	{
		return cutString(this.syukei_syubetu_cd,SYUKEI_SYUBETU_CD_MAX_LENGTH);
	}
	public String getSyukeiSyubetuCdString()
	{
		return "'" + cutString(this.syukei_syubetu_cd,SYUKEI_SYUBETU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiSyubetuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_syubetu_cd,SYUKEI_SYUBETU_CD_MAX_LENGTH));
	}


	// syukei_hinsyu_cdに対するセッターとゲッターの集合
	public boolean setSyukeiHinsyuCd(String syukei_hinsyu_cd)
	{
		this.syukei_hinsyu_cd = syukei_hinsyu_cd;
		return true;
	}
	public String getSyukeiHinsyuCd()
	{
		return cutString(this.syukei_hinsyu_cd,SYUKEI_HINSYU_CD_MAX_LENGTH);
	}
	public String getSyukeiHinsyuCdString()
	{
		return "'" + cutString(this.syukei_hinsyu_cd,SYUKEI_HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_hinsyu_cd,SYUKEI_HINSYU_CD_MAX_LENGTH));
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
		return "  syukei_syubetu_cd = " + getSyukeiSyubetuCdString()
			+ "  syukei_hinsyu_cd = " + getSyukeiHinsyuCdString()
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
	 * @return mst640101_SyukeiSyubetuHinsyuRenkanBeanBean コピー後のクラス
	 */
	public mst640101_SyukeiSyubetuHinsyuRenkanBean createClone()
	{
		mst640101_SyukeiSyubetuHinsyuRenkanBean bean = new mst640101_SyukeiSyubetuHinsyuRenkanBean();
		bean.setSyukeiSyubetuCd(this.syukei_syubetu_cd);
		bean.setSyukeiHinsyuCd(this.syukei_hinsyu_cd);
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
		if( !( o instanceof mst640101_SyukeiSyubetuHinsyuRenkanBean ) ) return false;
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
