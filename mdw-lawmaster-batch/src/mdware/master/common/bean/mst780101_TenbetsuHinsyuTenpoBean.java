/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）店別品種(店舗)マスタのレコード格納用クラス</p>
 * <p>説明		：新ＭＤシステムで使用する店別品種(店舗)マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権	：Copyright (c) 2005</p>
 * <p>会社名	：Vinculum Japan Corp.</p>
 * @author 	VJC A.Tozaki
 * @version 	1.0(2005/11/01)初版作成
 */
package mdware.master.common.bean;

import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;


public class mst780101_TenbetsuHinsyuTenpoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH 			= 6;	// 店舗コードの長さ
	public static final int HINSYU_CD_MAX_LENGTH			= 4;	// 品種コードの長さ
	public static final int HINSYU_NA_MAX_LENGTH			= 20;	// 品種名称の長さ
	public static final int ATUKAI_ST_DT_MAX_LENGTH 		= 8;	// 取扱開始日の長さ
	public static final int ATUKAI_ED_DT_MAX_LENGTH 		= 8;	// 取扱終了日コードの長さ
	public static final int PC_SEND_KB_MAX_LENGTH 		= 1;	// ＰＣ送信区分の長さ
	public static final int SEND_DT_MAX_LENGTH 			= 8;	// 送信日の長さ
	public static final int INSERT_TS_MAX_LENGTH			= 20;	// 作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 	= 10;	// 作成者社員ＩＤの長さ
	public static final int UPDATE_TS_MAX_LENGTH 		= 20;	// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 	= 10;	// 更新者社員ＩＤの長さ
	public static final int DELETE_FG_MAX_LENGTH 		= 1;	// 削除フラグの長さ
	public static final int TANPIN_QT_MAX_LENGTH 		= 7;	// 単品数の長さ
	public static final int HENKO_DT_MAX_LENGTH 			= 8;	// 変更日の長さ


	private String tenpo_cd 			= null;	// 店舗コード 
	private String hinsyu_cd			= null;	// 品種コード
	private String hinsyu_na			= null;	// 品種名称
	private String atukai_st_dt		= null;	// 取扱開始日
	private String atukai_ed_dt		= null;	// 取扱終了日
	private String pc_send_kb 	  		= null;	// ＰＣ送信区分
	private String send_dt 			= null;	// 送信日
	private String insert_ts 		  	= null;	// 作成年月日
	private String insert_user_id 	  	= null;	// 作成者社員ＩＤ
	private String update_ts 		  	= null;	// 更新年月日
	private String update_user_id 	  	= null;	// 更新者社員ＩＤ
	private String delete_fg 	  		= null;	// 削除フラグ
	private String tanpin_qt			= null;	// 単品数
	private String henko_dt			= null;	// 変更日
	
	private String sentaku 			= "";		//画面入力CheckBox
	private Map    ctlColor 			= null;	//画面コントロール色	
	private String disbale 			= null;	//明細入力可否

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
	 * mst780101_TenbetsuHinsyuTenpoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst780101_TenbetsuHinsyuTenpoBean getmst780101_TenbetsuHinsyuTenpoBean(DataHolder dataHolder)
	{
		mst780101_TenbetsuHinsyuTenpoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst780101_TenbetsuHinsyuTenpoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst780101_TenbetsuHinsyuTenpoBean )ite.next();
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


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH));
	}


	// hinsyu_naに対するセッターとゲッターの集合
	public boolean setHinsyuNa(String hinsyu_na)
	{
		this.hinsyu_na = hinsyu_na;
		return true;
	}
	public String getHinsyuNa()
	{
		return cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH);
	}
	public String getHinsyuNaString()
	{
		return "'" + cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH));
	}


	// atukai_st_dtに対するセッターとゲッターの集合
	public boolean setAtukaiStDt(String atukai_st_dt)
	{
		this.atukai_st_dt = atukai_st_dt;
		return true;
	}
	public String getAtukaiStDt()
	{
		return cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH);
	}
	public String getAtukaiStDtString()
	{
		return "'" + cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getAtukaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH));
	}


	// atukai_ed_dtに対するセッターとゲッターの集合
	public boolean setAtukaiEdDt(String atukai_ed_dt)
	{
		this.atukai_ed_dt = atukai_ed_dt;
		return true;
	}
	public String getAtukaiEdDt()
	{
		return cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH);
	}
	public String getAtukaiEdDtString()
	{
		return "'" + cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getAtukaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH));
	}

	// pc_send_kbに対するセッターとゲッターの集合
	public boolean setPcSendKb(String pc_send_kb)
	{
		this.pc_send_kb = pc_send_kb;
		return true;
	}
	public String getPcSendKb()
	{
		return cutString(this.pc_send_kb, PC_SEND_KB_MAX_LENGTH);
	}
	public String getPcSendKbString()
	{
		return "'" + cutString(this.pc_send_kb, PC_SEND_KB_MAX_LENGTH) + "'";
	}
	public String getPcSendKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_send_kb, PC_SEND_KB_MAX_LENGTH));
	}

	// send_dtに対するセッターとゲッターの集合
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	public String getSendDt()
	{
		return cutString(this.send_dt, SEND_DT_MAX_LENGTH);
	}
	public String getSendDtString()
	{
		return "'" + cutString(this.send_dt, SEND_DT_MAX_LENGTH) + "'";
	}
	public String getSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_dt, SEND_DT_MAX_LENGTH));
	}


	// tanpin_qtに対するセッターとゲッターの集合
	public boolean setTanpinQt(String tanpin_qt)
	{
		this.tanpin_qt = tanpin_qt;
		return true;
	}
	public String getTanpinQt()
	{
		return cutString(this.tanpin_qt,TANPIN_QT_MAX_LENGTH);
	}
	public String getTanpinQtString()
	{
		return "'" + cutString(this.tanpin_qt,TANPIN_QT_MAX_LENGTH) + "'";
	}
	public String getTanpinQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanpin_qt,TANPIN_QT_MAX_LENGTH));
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
		return cutString(this.delete_fg, DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg, DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg, DELETE_FG_MAX_LENGTH));
	}

	
	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt, HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt, HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt, HENKO_DT_MAX_LENGTH));
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

	// ctlColorに対するセッターとゲッターの集合
	public boolean setCtlColor(Map ctlColor)
	{
		this.ctlColor = ctlColor;
		return true;
	}
	public Map getCtlColor()
	{
		return this.ctlColor;
	}
	
	// disbaleに対するセッターとゲッターの集合
	public boolean setDisbale(String disbale)
	{
		this.disbale = disbale;
		return true;
	}
	public String getDisbale()
	{
		return this.disbale;
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_na " + getHinsyuNaString()
			+ "  atukai_st_dt = " + getAtukaiStDtString()
			+ "  atukai_ed_dt = " + getAtukaiEdDtString()
			+ "  pc_send_kb = " + getPcSendKbString()
			+ "  send_dt = " + getSendDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  tanpin_qt = " + getTanpinQtString()
			+ "  henko_dt = " + getHenkoDtString()
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst780101_TenbetsuHinsyuTenpoBean コピー後のクラス
	 */
	public mst780101_TenbetsuHinsyuTenpoBean createClone()
	{
		mst780101_TenbetsuHinsyuTenpoBean bean = new mst780101_TenbetsuHinsyuTenpoBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNa(this.hinsyu_na);
		bean.setAtukaiStDt(this.atukai_st_dt);
		bean.setAtukaiEdDt(this.atukai_ed_dt);
		bean.setPcSendKb(this.pc_send_kb);
		bean.setSendDt(this.send_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setTanpinQt(this.tanpin_qt);
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
		if( !( o instanceof mst780101_TenbetsuHinsyuTenpoBean ) ) return false;
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
