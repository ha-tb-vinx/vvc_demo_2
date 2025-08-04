package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.MDWARE
 * @version 1.0 (Create time: 2006/10/2 14:59:21) K.Tanigawa
 */
public class mst970101_RIryoGroupNoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH       = 4;
	public static final int HINSYU_CD_MAX_LENGTH      = 4;
	public static final int GROUPNO_CD_MAX_LENGTH     = 2;
	public static final int NAME_NA_MAX_LENGTH        = 80;
	public static final int GYOTAI_KB_MAX_LENGTH      = 2;
	public static final int INSERT_TS_MAX_LENGTH      = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH      = 14;
	public static final int UPDATE_TS_FOR_UPDATE_MAX_LENGTH      = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;
	public static final int DELETE_FG_MAX_LENGTH      = 1;
	public static final int INSERT_USER_NA_MAX_LENGTH = 82;
	public static final int UPDATE_USER_NA_MAX_LENGTH = 82;

	private String bumon_cd       = null;
	private String hinsyu_cd      = null;
	private String groupno_cd     = null;
	private String name_na        = null;
	private String gyotai_kb      = null;
	private String insert_ts      = null;
	private String insert_user_id = null;
	private String update_ts      = null;
	private String update_ts_for_update      = null;
	private String update_user_id = null;
	private String delete_fg      = null;
	private String insert_user_na = null;
	private String update_user_na = null;

	private String sentaku        = null;	//画面入力CheckBox
	private String shori          = null;	//画面入力ラジオボタン

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
	 * RIryogroupnoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst970101_RIryoGroupNoBean getRIryogroupnoBean(DataHolder dataHolder)
	{
		mst970101_RIryoGroupNoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst970101_RIryoGroupNoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst970101_RIryoGroupNoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// bumon_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
	}


	// groupno_cdに対するセッターとゲッターの集合
	public boolean setGroupnoCd(String groupno_cd)
	{
		this.groupno_cd = groupno_cd;
		return true;
	}
	public String getGroupnoCd()
	{
		return cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH);
	}
	public String getGroupnoCdString()
	{
		return "'" + cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH) + "'";
	}
	public String getGroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.groupno_cd,GROUPNO_CD_MAX_LENGTH));
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


	// gyotai_kbに対するセッターとゲッターの集合
	public boolean setGyotaiKb(String gyotai_kb)
	{
		this.gyotai_kb = gyotai_kb;
		return true;
	}
	public String getGyotaiKb()
	{
		return cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH);
	}
	public String getGyotaiKbString()
	{
		return "'" + cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getGyotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyotai_kb,GYOTAI_KB_MAX_LENGTH));
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

	// update_ts_for_updateに対するセッターとゲッターの集合
	public boolean setUpdateTsForUpdate(String update_ts_for_update)
	{
		this.update_ts_for_update = update_ts_for_update;
		return true;
	}
	public String getUpdateTsForUpdate()
	{
		return cutString(this.update_ts_for_update,UPDATE_TS_FOR_UPDATE_MAX_LENGTH);
	}
	public String getUpdateTsForUpdateString()
	{
		return "'" + cutString(this.update_ts_for_update,UPDATE_TS_FOR_UPDATE_MAX_LENGTH) + "'";
	}
	public String getUpdateTsForUpdateHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts_for_update,UPDATE_TS_FOR_UPDATE_MAX_LENGTH));
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


	// insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
	}

	
	
	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
	}

	
	
	
	/**
	 * @return
	 */
	public String getSentaku() {
		return sentaku;
	}

	/**
	 * @return
	 */
	public String getShori() {
		return shori;
	}

	/**
	 * @param string
	 */
	public void setSentaku(String string) {
		sentaku = string;
	}

	/**
	 * @param string
	 */
	public void setShori(String string) {
		shori = string;
	}

	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  groupno_cd = " + getGroupnoCdString()
			+ "  name_na = " + getNameNaString()
			+ "  gyotai_kb = " + getGyotaiKbString()
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
	 * @return RIryogroupnoBean コピー後のクラス
	 */
	public mst970101_RIryoGroupNoBean createClone()
	{
		mst970101_RIryoGroupNoBean bean = new mst970101_RIryoGroupNoBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setGroupnoCd(this.groupno_cd);
		bean.setNameNa(this.name_na);
		bean.setGyotaiKb(this.gyotai_kb);
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
		if( !( o instanceof mst970101_RIryoGroupNoBean ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
