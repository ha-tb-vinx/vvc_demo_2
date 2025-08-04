/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信トランのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信トランのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T. Kimura
 * @version 1.0(2005/04/22)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信トランのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信トランのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T. Kimura
 * @version 1.0(2005/04/22)初版作成
 */
public class mst770101_TanpinSendBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KB_MAX_LENGTH = 1;			//管理区分の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;			//管理コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;			//店舗コードの長さ
	public static final int SEND_DT_MAX_LENGTH = 8;			//送信日の長さ
	public static final int SELECT_KB_MAX_LENGTH = 1;			//条件区分の長さ
	public static final int ENTRY_KB_MAX_LENGTH = 1;			//登録区分の長さ
	public static final int HINSYU_CD_MAX_LENGTH = 4;			//単品コードの長さ
	public static final int SEND_END_KB_MAX_LENGTH = 1;		//送信済区分の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 8;	//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 8;	//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;			//削除フラグの長さ
	public static final int REQUEST_KB_MAX_LENGTH = 1;			//リクエスト区分の長さ
//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;			//販区コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;			//部門コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;			//商品コードの長さ
	public static final int PC_SEND_END_KB_MAX_LENGTH=1;     //PC送信済区分の長さ

	private String kanri_kb = null;							//管理区分
	private String kanri_cd = null;							//管理コード
	private String tenpo_cd = null;							//店舗コード
	private String send_dt = null;								//送信日
	private String select_kb = null;							//条件区分
	private String entry_kb = null;							//登録区分
	private String hinsyu_cd = null;							//単品コード
	private String send_end_kb = null;							//送信済区分
	private String insert_ts = null;							//作成年月日
	private String insert_user_id = null;						//作成者社員ID
	private String update_ts = null;							//更新年月日
	private String update_user_id = null;						//更新者社員ID
	private String delete_fg = null;							//削除フラグ
	private String request_kb = null;							//リクエスト区分
//	private String hanku_cd = null;								//販区コード
	private String bumon_cd = null;								//部門コード
	private String syohin_cd = null;							//商品コード
	private String pc_send_end_kb = null;                   //PC送信済区分
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑

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
	 * mst770101_TanpinSendBeanを１件のみ抽出したい時に使用する
	 */
	public static mst770101_TanpinSendBean getmst770101_TanpinSendBean(DataHolder dataHolder)
	{
		mst770101_TanpinSendBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst770101_TanpinSendDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst770101_TanpinSendBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}

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
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
	
	// request_kbに対するセッターとゲッターの集合
	public boolean setRequestKb(String request_kb)
	{
		this.request_kb = request_kb;
		return true;
	}
	public String getRequestKb()
	{
		return cutString(this.request_kb,REQUEST_KB_MAX_LENGTH);
	}
	public String getRequestKbString()
	{
	return "'" + cutString(this.request_kb,REQUEST_KB_MAX_LENGTH) + "'";
	}
	public String getRequestKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.request_kb,REQUEST_KB_MAX_LENGTH));
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


	// send_dtに対するセッターとゲッターの集合
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	public String getSendDt()
	{
		return cutString(this.send_dt,SEND_DT_MAX_LENGTH);
	}
	public String getSendDtString()
	{
		return "'" + cutString(this.send_dt,SEND_DT_MAX_LENGTH) + "'";
	}
	public String getSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_dt,SEND_DT_MAX_LENGTH));
	}


	// select_kbに対するセッターとゲッターの集合
	public boolean setSelectKb(String select_kb)
	{
		this.select_kb = select_kb;
		return true;
	}
	public String getSelectKb()
	{
		return cutString(this.select_kb,SELECT_KB_MAX_LENGTH);
	}
	public String getSelectKbString()
	{
		return "'" + cutString(this.select_kb,SELECT_KB_MAX_LENGTH) + "'";
	}
	public String getSelectKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.select_kb,SELECT_KB_MAX_LENGTH));
	}


	// entry_kbに対するセッターとゲッターの集合
	public boolean setEntryKb(String entry_kb)
	{
		this.entry_kb = entry_kb;
		return true;
	}
	public String getEntryKb()
	{
		return cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH);
	}
	public String getEntryKbString()
	{
		return "'" + cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH) + "'";
	}
	public String getEntryKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH));
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


	// send_end_kbに対するセッターとゲッターの集合
	public boolean setSendEndKb(String send_end_kb)
	{
		this.send_end_kb = send_end_kb;
		return true;
	}
	public String getSendEndKb()
	{
		return cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH);
	}
	public String getSendEndKbString()
	{
		return "'" + cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH) + "'";
	}
	public String getSendEndKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH));
	}


//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	 pc_send_end_kbに対するセッターとゲッターの集合
	public boolean setPcSendEndKb(String 	pc_send_end_kb)
	{
		this.pc_send_end_kb = pc_send_end_kb;
		return true;
	}
	public String getPcSendEndKb()
	{
		return cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH);
	}
	public String getPcSendEndKbString()
	{
		return "'" + cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH) + "'";
	}
	public String getPcSendEndKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH));
	}
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑

	
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
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  send_dt = " + getSendDtString()
			+ "  select_kb = " + getSelectKbString()
			+ "  entry_kb = " + getEntryKbString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  send_end_kb = " + getSendEndKbString()
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
	 * @return RTanpinSendBean コピー後のクラス
	 */
	public mst770101_TanpinSendBean createClone()
	{
		mst770101_TanpinSendBean bean = new mst770101_TanpinSendBean();
		bean.setKanriKb(this.kanri_kb);
		bean.setKanriCd(this.kanri_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setSendDt(this.send_dt);
		bean.setSelectKb(this.select_kb);
		bean.setEntryKb(this.entry_kb);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setSendEndKb(this.send_end_kb);
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
		if( !( o instanceof mst770101_TanpinSendBean ) ) return false;
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
