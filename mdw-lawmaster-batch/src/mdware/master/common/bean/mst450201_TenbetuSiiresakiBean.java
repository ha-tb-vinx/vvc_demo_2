/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別仕入先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別仕入先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450201_TenbetuSiiresakiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//    ↓↓2006.07.02 maojm カスタマイズ修正↓↓	
//	public static final int KANRI_KB_MAX_LENGTH 				= 1;	//管理区分 (FK)の長さ
//	public static final int KANRI_CD_MAX_LENGTH 				= 4;	//管理コード (FK)の長さ
	public static final int BUMON_CD_MAX_LENGTH 		= 4;	//部門コード の長さ
//    ↑↑2006.07.02 maojm カスタマイズ修正↑↑	
	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;	//店別仕入先管理コードの長さ
	public static final int TENPO_CD_MAX_LENGTH 				= 6;	//店舗コード (FK)の長さ
//  ↓↓2006.07.02 maojm カスタマイズ修正↓↓	
//	public static final int SIIRESAKI_CD_MAX_LENGTH 			= 6;	//仕入先コード (FK)の長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH 			= 9;	//仕入先コード (FK)の長さ
//  ↑↑2006.07.02 maojm カスタマイズ修正↑↑	
	public static final int INSERT_TS_MAX_LENGTH 			= 20;	//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 		= 10;	//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH 			= 20;	//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 		= 10;	//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH 			= 1;	//削除フラグの長さ
//	↓↓仕様追加による変更（2005.06.29）↓↓
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;//新規登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;//変更日の長さ
//	↑↑仕様追加による変更（2005.06.29）↑↑

// ↓↓2006.07.02 maojm カスタマイズ修正↓↓	
//	private String kanri_kb 			= null;	//管理区分 (FK)
//	private String kanri_cd 			= null;	//管理コード (FK)
	private String bumon_cd 			= null;	//部門コード 
//    ↑↑2006.07.02 maojm カスタマイズ修正↑↑	
	private String ten_siiresaki_kanri_cd = null;	//店別仕入先管理コード
	private String tenpo_cd 			= null;	//店舗コード (FK)
	private String siiresaki_cd 		= null;	//仕入先コード (FK)
	private String insert_ts 			= null;	//作成年月日
	private String insert_user_id 		= null;	//作成者社員ID
	private String update_ts 			= null;	//更新年月日
	private String update_user_id 		= null;	//更新者社員ID
	private String delete_fg 			= null;	//削除フラグ
//	↓↓仕様追加による変更（2005.06.29）↓↓
	private String sinki_toroku_dt = null;//新規登録日
	private String henko_dt = null;	//変更日
//	↑↑仕様追加による変更（2005.06.29）↑↑

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
	 * RTenbetuSiiresakiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst450201_TenbetuSiiresakiBean getmst450201_TenbetuSiiresakiBean(DataHolder dataHolder)
	{
		mst450201_TenbetuSiiresakiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst450201_TenbetuSiiresakiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst450201_TenbetuSiiresakiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




//    ↓↓2006.07.02 maojm カスタマイズ修正↓↓	
//	// kanri_kbに対するセッターとゲッターの集合
//	public boolean setKanriKb(String kanri_kb)
//	{
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
//	public String getKanriKb()
//	{
//		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
//	}
//	public String getKanriKbString()
//	{
//		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
//	}
//	public String getKanriKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
//	}


//	// kanri_cdに対するセッターとゲッターの集合
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
//	}
//	public String getKanriCdString()
//	{
//		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
//	}
//
//	 bumon_cdに対するセッターとゲッターの集合
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

	
//    ↑↑2006.07.02 maojm カスタマイズ修正↑↑	

	// ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
		return true;
	}
	public String getTenSiiresakiKanriCd()
	{
		return cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
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


	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
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
	
//	↓↓仕様追加による変更（2005.06.29）↓↓
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
//	↑↑仕様追加による変更（2005.06.29）↑↑
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//	    ↓↓2006.07.06 maojm カスタマイズ修正↓↓		
//		return "  kanri_kb = " + getKanriKbString()
//			+ "  kanri_cd = " + getKanriCdString()
		 return "  kanri_cd = " + getBumonCdString()
//		 ↑↑2006.07.06 maojm カスタマイズ修正↑↑				
			+ "  ten_siiresaki_kanri_cd = " + getTenSiiresakiKanriCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
//			↓↓仕様追加による変更（2005.06.29）↓↓
			+ "  sinki_toroku_dt = " + getSinkiTorokuDt()
			+ "  henko_dt = " + getHenkoDt()
//			↑↑仕様追加による変更（2005.06.29）↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst450201_TenbetuSiiresakiBean コピー後のクラス
	 */
	public mst450201_TenbetuSiiresakiBean createClone()
	{
		mst450201_TenbetuSiiresakiBean bean = new mst450201_TenbetuSiiresakiBean();
//	    ↓↓2006.07.02 maojm カスタマイズ修正↓↓		
//		bean.setKanriKb(this.kanri_kb);
//		bean.setKanriCd(this.kanri_cd);
		bean.setBumonCd(this.bumon_cd);		
//	    ↑↑2006.07.02 maojm カスタマイズ修正↑↑
		bean.setTenSiiresakiKanriCd(this.ten_siiresaki_kanri_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様追加による変更（2005.06.29）↓↓
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
//		↑↑仕様追加による変更（2005.06.29）↑↑
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
		if( !( o instanceof mst450201_TenbetuSiiresakiBean ) ) return false;
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
