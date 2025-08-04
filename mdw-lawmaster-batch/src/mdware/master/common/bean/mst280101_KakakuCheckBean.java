/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
public class mst280101_KakakuCheckBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int KAKAKU_KB_MAX_LENGTH = 1;//価格区分の長さ
	public static final int CHECK_ST_DT_MAX_LENGTH = 8;//チェック開始日の長さ
	public static final int CHECK_ED_DT_MAX_LENGTH = 8;//チェック終了日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String hanku_cd = null;	//販区コード
	private String syohin_cd = null;	//商品コード
	private String kakaku_kb = null;	//価格区分
	private long min_kakaku_vl = 0;	//最低価格
	private String check_st_dt = null;	//チェック開始日
	private String check_ed_dt = null;	//チェック終了日
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
	 * mst280101_KakakuCheckBeanを１件のみ抽出したい時に使用する
	 */
	public static mst280101_KakakuCheckBean getmst280101_KakakuCheckBean(DataHolder dataHolder)
	{
		mst280101_KakakuCheckBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst280101_KakakuCheckDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst280101_KakakuCheckBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// kakaku_kbに対するセッターとゲッターの集合
	public boolean setKakakuKb(String kakaku_kb)
	{
		this.kakaku_kb = kakaku_kb;
		return true;
	}
	public String getKakakuKb()
	{
		return cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH);
	}
	public String getKakakuKbString()
	{
		return "'" + cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH) + "'";
	}
	public String getKakakuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH));
	}


	// min_kakaku_vlに対するセッターとゲッターの集合
	public boolean setMinKakakuVl(String min_kakaku_vl)
	{
		try
		{
			this.min_kakaku_vl = Long.parseLong(min_kakaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMinKakakuVl(long min_kakaku_vl)
	{
		this.min_kakaku_vl = min_kakaku_vl;
		return true;
	}
	public long getMinKakakuVl()
	{
		return this.min_kakaku_vl;
	}
	public String getMinKakakuVlString()
	{
		return Long.toString(this.min_kakaku_vl);
	}


	// check_st_dtに対するセッターとゲッターの集合
	public boolean setCheckStDt(String check_st_dt)
	{
		this.check_st_dt = check_st_dt;
		return true;
	}
	public String getCheckStDt()
	{
		return cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH);
	}
	public String getCheckStDtString()
	{
		return "'" + cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH) + "'";
	}
	public String getCheckStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH));
	}


	// check_ed_dtに対するセッターとゲッターの集合
	public boolean setCheckEdDt(String check_ed_dt)
	{
		this.check_ed_dt = check_ed_dt;
		return true;
	}
	public String getCheckEdDt()
	{
		return cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH);
	}
	public String getCheckEdDtString()
	{
		return "'" + cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH) + "'";
	}
	public String getCheckEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH));
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
		return "  hanku_cd = " + getHankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  kakaku_kb = " + getKakakuKbString()
			+ "  min_kakaku_vl = " + getMinKakakuVlString()
			+ "  check_st_dt = " + getCheckStDtString()
			+ "  check_ed_dt = " + getCheckEdDtString()
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
	 * @return mst280101_KakakuCheckBean コピー後のクラス
	 */
	public mst280101_KakakuCheckBean createClone()
	{
		mst280101_KakakuCheckBean bean = new mst280101_KakakuCheckBean();
		bean.setHankuCd(this.hanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setKakakuKb(this.kakaku_kb);
		bean.setMinKakakuVl(this.min_kakaku_vl);
		bean.setCheckStDt(this.check_st_dt);
		bean.setCheckEdDt(this.check_ed_dt);
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
		if( !( o instanceof mst280101_KakakuCheckBean ) ) return false;
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
