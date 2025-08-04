/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングNOマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングNOマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/11)初版作成
 */
public class mst580201_TenGroupNoLBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//	public static final int L_GYOSYU_CD_MAX_LENGTH = 4;//大業種の長さ
//	public static final int YOTO_KB_MAX_LENGTH = 1;//用途区分の長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	public static final int BUMON_KANJI_RN_MAX_LENGTH 	=20;//部門名の長さ
	public static final int GROUP_CD_MAX_LENGTH = 2;//グルーピングNOの長さ
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
	public static final int NAME_NA_MAX_LENGTH = 20;//名称の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
//    ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
	public static final int UPDATE_USER_RN_MAX_LENGTH = 20;//更新者社員IDの長さ
	public static final int INSERT_USER_RN_MAX_LENGTH = 20;//作成者社員IDの長さ
//    ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//	private String l_gyosyu_cd = null;	//大業種
//	private String yoto_kb = null;	//用途区分
	private String bumon_cd = null;	//部門コード
	private String bumon_kanji_rn =null;	//部門名	
	private String insert_user_rn =null;//作成者社員ID名前
	private String update_user_rn = null;//更新者社員ID名前
	
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
//	private  groupno_cd = 0;	//グルーピングNO
	private String groupno_cd=null;
	private String name_na = null;	//名称
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
	 * RTengroupnoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst580201_TenGroupNoLBean getRTengroupnoBean(DataHolder dataHolder)
	{
		mst580201_TenGroupNoLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst580201_TenGroupNoLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst580201_TenGroupNoLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



//			      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
	// insert_user_rnに対するセッターとゲッターの集合
	public boolean setInsertRn(String insert_user_rn)
	{
		this.insert_user_rn = insert_user_rn;
		return true;
	}
	public String getInsertRn()
	{
		return cutString(this.insert_user_rn,INSERT_USER_RN_MAX_LENGTH);
	}
	public String getInsertRnString()
	{
		return "'" + cutString(this.insert_user_rn,INSERT_USER_RN_MAX_LENGTH) + "'";
	}
	public String getInsertRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_rn,INSERT_USER_RN_MAX_LENGTH));
	}
	// update_user_rnに対するセッターとゲッターの集合
	public boolean setUpdateRn(String update_user_rn)
	{
		this.update_user_rn = update_user_rn;
		return true;
	}
	public String getUpdateRn()
	{
		return cutString(this.update_user_rn,UPDATE_USER_RN_MAX_LENGTH);
	}
	public String getUpdateRnString()
	{
		return "'" + cutString(this.update_user_rn,UPDATE_USER_RN_MAX_LENGTH) + "'";
	}
	public String getUpdateRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_rn,UPDATE_USER_RN_MAX_LENGTH));
	}
	// l_gyosyu_cdに対するセッターとゲッターの集合
//	public boolean setLGyosyuCd(String l_gyosyu_cd)
//	{
//		this.l_gyosyu_cd = l_gyosyu_cd;
//		return true;
//	}
//	public String getLGyosyuCd()
//	{
//		return cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH);
//	}
//	public String getLGyosyuCdString()
//	{
//		return "'" + cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getLGyosyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH));
//	}


	// yoto_kbに対するセッターとゲッターの集合
//	public boolean setYotoKb(String yoto_kb)
//	{
//		this.yoto_kb = yoto_kb;
//		return true;
//	}
//	public String getYotoKb()
//	{
//		return cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH);
//	}
//	public String getYotoKbString()
//	{
//		return "'" + cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH) + "'";
//	}
//	public String getYotoKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH));
//	}
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
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return cutString(this.bumon_kanji_rn,BUMON_KANJI_RN_MAX_LENGTH);
	}
//			      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
	// groupno_cdに対するセッターとゲッターの集合
//	public boolean setGroupnoCd(String groupno_cd)
//	{
//		try
//		{
//			this.groupno_cd = Long.parseLong(groupno_cd);
//		}
//		catch(Exception e)
//		{
//			return false;
//		}
//		return true;
//	}
//	public boolean setGroupnoCd(long groupno_cd)
//	{
//		this.groupno_cd = groupno_cd;
//		return true;
//	}
//	public long getGroupnoCd()
//	{
//		return this.groupno_cd;
//	}
//	public String getGroupnoCdString()
//	{
//		return Long.toString(this.groupno_cd);
//	}
	public boolean setGroupnoCd(String groupno_cd)
	{
		this.groupno_cd = groupno_cd;
		return true;
	}
	public String getGroupnoCd()
	{
		return cutString(this.groupno_cd,GROUP_CD_MAX_LENGTH);
	}
	public String getGroupnoCdString()
	{
		return "'" + cutString(this.groupno_cd,GROUP_CD_MAX_LENGTH) + "'";
	}
	public String getGroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.groupno_cd,GROUP_CD_MAX_LENGTH));
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
//				  ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		return "  l_gyosyu_cd = " + getLGyosyuCdString()
//			+ "  yoto_kb = " + getYotoKbString()
		return "bumon_cd="+ getBumonCdString()                   
			+ "bumon_kanji_rn="+getBumonKanjiRn()
//				  ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
			+ "  groupno_cd = " + getGroupnoCdString()
			+ "  name_na = " + getNameNaString()
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
	 * @return RTengroupnoBean コピー後のクラス
	 */
	public mst580201_TenGroupNoLBean createClone()
	{
		mst580201_TenGroupNoLBean bean = new mst580201_TenGroupNoLBean();
//					      ↓↓2006.06.23 zhangxia カスタマイズ修正↓↓
//		bean.setLGyosyuCd(this.l_gyosyu_cd);
//		bean.setYotoKb(this.yoto_kb);
		bean.setBumonCd(this.bumon_cd);
		bean.setBumonKanjiRn(this.bumon_kanji_rn);
//					      ↑↑2006.06.23 zhangxia カスタマイズ修正↑↑	
		bean.setGroupnoCd(this.groupno_cd);
		bean.setNameNa(this.name_na);
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
		if( !( o instanceof mst580201_TenGroupNoLBean ) ) return false;
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
