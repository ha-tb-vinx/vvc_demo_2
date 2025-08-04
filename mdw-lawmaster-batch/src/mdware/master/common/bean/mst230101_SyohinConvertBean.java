/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/29)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/29)初版作成
 */
public class mst230101_SyohinConvertBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	//最大表示桁数設定
	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コード
	public static final int HANKU_NM_MAX_LENGTH = 20;//販区コード名称
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コード
	public static final int SYOHIN_NM_MAX_LENGTH = 80;//商品コード名称
	public static final int HANKU_AFTER_CD_MAX_LENGTH = 4;//販区コード(変換後)
	public static final int HANKU_AFTER_NM_MAX_LENGTH = 20;//販区名称(変換後)
	public static final int SYOHIN_AFTER_CD_MAX_LENGTH = 13;//商品コード(変換後)
	public static final int SYOHIN_AFTER_NM_MAX_LENGTH = 80;//商品コード名称(変換後)
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員ID
	public static final int INSERT_USER_NM_MAX_LENGTH = 20;//作成者名
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員ID
	public static final int UPDATE_USER_NM_MAX_LENGTH = 20;//更新者名
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグ

	private String sentaku = null;			//選択
	private String hanku_cd = null;			//販区コード
	private String hanku_nm = null;			//販区コード名称
	private String syohin_cd = null;			//商品コード
	private String syohin_nm = null;			//商品コード名称
	private String hanku_after_cd = null;		//販区(変換後)
	private String hanku_after_nm = null;		//販区コード名称(変換後)
	private String syohin_after_cd = null;	//商品コード(変換後)
	private String syohin_after_nm = null;	//商品コード名称(変換後)
	private String insert_ts = null;			//作成年月日
	private String insert_user_id = null;		//作成者社員ID
	private String insert_user_nm = null;		//作成者名
	private String update_ts = null;			//更新年月日
	private String update_user_id = null;		//更新者社員ID
	private String update_user_nm = null;		//更新者名
	private String delete_fg = null;			//削除フラグ

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
	 * mst230101_SyohinConvertBeanを１件のみ抽出したい時に使用する
	 */
	public static mst230101_SyohinConvertBean getmst230101_SyohinConvertBean(DataHolder dataHolder)
	{
		mst230101_SyohinConvertBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst230101_SyohinConvertDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst230101_SyohinConvertBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return this.sentaku;
	}

	// hanku_cdに対するセッターとゲッターの集合
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	public String getHankuCd()
	{
		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
	}
	public String getHankuCdString()
	{
		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
	}


	// hanku_nmに対するセッターとゲッターの集合
	public boolean setHankuNm(String hanku_nm)
	{
		this.hanku_nm = hanku_nm;
		return true;
	}
	public String getHankuNm()
	{
		return cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH);
	}
	public String getHankuNmString()
	{
		return "'" + cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH) + "'";
	}
	public String getHankuNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH));
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


	// syohin_nmに対するセッターとゲッターの集合
	public boolean setSyohinNm(String syohin_nm)
	{
		this.syohin_nm = syohin_nm;
		return true;
	}
	public String getSyohinNm()
	{
		return cutString(this.syohin_nm,SYOHIN_NM_MAX_LENGTH);
	}
	public String getSyohinNmString()
	{
		return "'" + cutString(this.syohin_nm,SYOHIN_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_nm,SYOHIN_NM_MAX_LENGTH));
	}


	// hanku_after_cdに対するセッターとゲッターの集合
	public boolean setHankuAfterCd(String hanku_after_cd)
	{
		this.hanku_after_cd = hanku_after_cd;
		return true;
	}
	public String getHankuAfterCd()
	{
		return cutString(this.hanku_after_cd,HANKU_AFTER_CD_MAX_LENGTH);
	}
	public String getHankuAfterCdString()
	{
		return "'" + cutString(this.hanku_after_cd,HANKU_AFTER_CD_MAX_LENGTH) + "'";
	}
	public String getHankuAfterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_after_cd,HANKU_AFTER_CD_MAX_LENGTH));
	}


	// hanku_after_nmに対するセッターとゲッターの集合
	public boolean setHankuAfterNm(String hanku_after_nm)
	{
		this.hanku_after_nm = hanku_after_nm;
		return true;
	}
	public String getHankuAfterNm()
	{
		return cutString(this.hanku_after_nm,HANKU_AFTER_NM_MAX_LENGTH);
	}
	public String getHankuAfterNmString()
	{
		return "'" + cutString(this.hanku_after_nm,HANKU_AFTER_NM_MAX_LENGTH) + "'";
	}
	public String getHankuAfterNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_after_nm,HANKU_AFTER_NM_MAX_LENGTH));
	}


	// syohin_after_cdに対するセッターとゲッターの集合
	public boolean setSyohinAfterCd(String syohin_after_cd)
	{
		this.syohin_after_cd = syohin_after_cd;
		return true;
	}
	public String getSyohinAfterCd()
	{
		return cutString(this.syohin_after_cd,SYOHIN_AFTER_CD_MAX_LENGTH);
	}
	public String getSyohinAfterCdString()
	{
		return "'" + cutString(this.syohin_after_cd,SYOHIN_AFTER_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinAfterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_after_cd,SYOHIN_AFTER_CD_MAX_LENGTH));
	}


	// syohin_after_nmに対するセッターとゲッターの集合
	public boolean setSyohinAfterNm(String syohin_after_nm)
	{
		this.syohin_after_nm = syohin_after_nm;
		return true;
	}
	public String getSyohinAfterNm()
	{
		return cutString(this.syohin_after_nm,SYOHIN_AFTER_NM_MAX_LENGTH);
	}
	public String getSyohinAfterNmString()
	{
		return "'" + cutString(this.syohin_after_nm,SYOHIN_AFTER_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinAfterNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_after_nm,SYOHIN_AFTER_NM_MAX_LENGTH));
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
		return HTMLStringUtil.convert(cutString(this.insert_ts,8));//Html出力時は8桁
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


	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH);
	}
	public String getInsertUserNmString()
	{
		return "'" + cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH) + "'";
	}
	public String getInsertUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH));
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
		return HTMLStringUtil.convert(cutString(this.update_ts,8)); //Html出力時は8桁
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


	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH);
	}
	public String getUpdateUserNmString()
	{
		return "'" + cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH));
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
		return "  hanku_cd = " + getHankuCdString()
			+ "  hanku_nm = " + getHankuNmString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  syohin_nm = " + getSyohinNmString()
			+ "  hanku_after_cd = " + getHankuAfterCdString()
			+ "  hanku_after_nm = " + getHankuAfterNmString()
			+ "  syohin_after_cd = " + getSyohinAfterCdString()
			+ "  syohin_after_nm = " + getSyohinAfterNmString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  insert_user_nm = " + getInsertUserNmString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  update_user_nm = " + getUpdateUserNmString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst230101_SyohinConvertBean コピー後のクラス
	 */
	public mst230101_SyohinConvertBean createClone()
	{
		mst230101_SyohinConvertBean bean = new mst230101_SyohinConvertBean();
		bean.setHankuCd(this.hanku_cd);
		bean.setHankuNm(this.hanku_nm);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSyohinNm(this.syohin_nm);
		bean.setHankuAfterCd(this.hanku_after_cd);
		bean.setHankuAfterNm(this.hanku_after_nm);
		bean.setSyohinAfterCd(this.syohin_after_cd);
		bean.setSyohinAfterNm(this.syohin_after_nm);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNm(this.insert_user_nm);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateUserNm(this.update_user_nm);
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
		if( !( o instanceof mst230101_SyohinConvertBean ) ) return false;
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
