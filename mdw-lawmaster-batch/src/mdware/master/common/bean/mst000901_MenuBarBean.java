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
public class mst000901_MenuBarBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KENGEN_CD_MAX_LENGTH = 30;		//取得名称の長さ
	public static final int GAMEN_ID_MAX_LENGTH = 1;			//存在フラグの長さ
	public static final int SUBMENU_ID_MAX_LENGTH = 1;		//存在フラグの長さ
	public static final int GYOU_NO_MAX_LENGTH = 1;			//存在フラグの長さ
	public static final int RETU_NO_MAX_LENGTH = 1;			//存在フラグの長さ
	public static final int INSERT_OK_KB_MAX_LENGTH = 1;		//存在フラグの長さ
	public static final int UPDATE_OK_KB_MAX_LENGTH = 1;		//存在フラグの長さ
	public static final int DELETE_OK_KB_MAX_LENGTH = 1;		//存在フラグの長さ
	public static final int REFERENCE_OK_KB_MAX_LENGTH = 1;	//存在フラグの長さ
	public static final int CSV_OK_KB_MAX_LENGTH = 1;		//存在フラグの長さ
	public static final int PRINT_OK_KB_MAX_LENGTH = 1;		//存在フラグの長さ

	private String kengen_cd = null;			//取得名称
	private String gamen_id = null;			//存在フラグ
	private String submenu_id = null;			//存在フラグ
	private String gyou_no = null;			//存在フラグ
	private String retu_no = null;			//存在フラグ
	private String insert_ok_kb = null;		//存在フラグ
	private String update_ok_kb = null;		//存在フラグ
	private String delete_ok_kb = null;		//存在フラグ
	private String reference_ok_kb = null;	//存在フラグ
	private String csv_ok_kb = null;			//存在フラグ
	private String print_ok_kb = null;		//存在フラグ


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
	 * mst000701_MasterInfoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst000701_MasterInfoBean getmst000701_MasterInfoBean(DataHolder dataHolder)
	{
		mst000701_MasterInfoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst000701_MasterInfoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst000701_MasterInfoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// kengen_cdに対するセッターとゲッターの集合
	public boolean setKengenCd(String kengen_cd)
	{
		this.kengen_cd = kengen_cd;
		return true;
	}
	public String getKengenCd()
	{
		return cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH);
	}
	public String getKengenCdString()
	{
		return "'" + cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH) + "'";
	}
	public String getKengenCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH));
	}

	// gamen_idに対するセッターとゲッターの集合
	public boolean setGamenId(String gamen_id)
	{
		this.gamen_id = gamen_id;
		return true;
	}
	public String getGamenId()
	{
		return cutString(this.gamen_id,GAMEN_ID_MAX_LENGTH);
	}
	public String getGamenIdString()
	{
		return "'" + cutString(this.gamen_id,GAMEN_ID_MAX_LENGTH) + "'";
	}
	public String getGamenIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gamen_id,GAMEN_ID_MAX_LENGTH));
	}

	// submenu_idに対するセッターとゲッターの集合
	public boolean setSubmenuId(String submenu_id)
	{
		this.submenu_id = submenu_id;
		return true;
	}
	public String getSubmenuId()
	{
		return cutString(this.submenu_id,SUBMENU_ID_MAX_LENGTH);
	}
	public String getSubmenuIdString()
	{
		return "'" + cutString(this.submenu_id,SUBMENU_ID_MAX_LENGTH) + "'";
	}
	public String getSubmenuIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.submenu_id,SUBMENU_ID_MAX_LENGTH));
	}

	// gyou_noに対するセッターとゲッターの集合
	public boolean setGyouNo(String gyou_no)
	{
		this.gyou_no = gyou_no;
		return true;
	}
	public String getGyouNo()
	{
		return cutString(this.gyou_no,GYOU_NO_MAX_LENGTH);
	}
	public String getGyouNoString()
	{
		return "'" + cutString(this.gyou_no,GYOU_NO_MAX_LENGTH) + "'";
	}
	public String getGyouNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyou_no,GYOU_NO_MAX_LENGTH));
	}

	// retu_noに対するセッターとゲッターの集合
	public boolean setRetuNo(String retu_no)
	{
		this.retu_no = retu_no;
		return true;
	}
	public String getRetuNo()
	{
		return cutString(this.retu_no,RETU_NO_MAX_LENGTH);
	}
	public String getRetuNoString()
	{
		return "'" + cutString(this.retu_no,RETU_NO_MAX_LENGTH) + "'";
	}
	public String getRetuNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.retu_no,RETU_NO_MAX_LENGTH));
	}

	// insert_ok_kbに対するセッターとゲッターの集合
	public boolean setInsertOkKb(String insert_ok_kb)
	{
		this.insert_ok_kb = insert_ok_kb;
		return true;
	}
	public String getInsertOkKb()
	{
		return cutString(this.insert_ok_kb,INSERT_OK_KB_MAX_LENGTH);
	}
	public String getInsertOkKbString()
	{
		return "'" + cutString(this.insert_ok_kb,INSERT_OK_KB_MAX_LENGTH) + "'";
	}
	public String getInsertOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ok_kb,INSERT_OK_KB_MAX_LENGTH));
	}

	// update_ok_kbに対するセッターとゲッターの集合
	public boolean setUpdateOkKb(String update_ok_kb)
	{
		this.update_ok_kb = update_ok_kb;
		return true;
	}
	public String getUpdateOkKb()
	{
		return cutString(this.update_ok_kb,UPDATE_OK_KB_MAX_LENGTH);
	}
	public String getUpdateOkKbString()
	{
		return "'" + cutString(this.update_ok_kb,UPDATE_OK_KB_MAX_LENGTH) + "'";
	}
	public String getUpdateOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ok_kb,UPDATE_OK_KB_MAX_LENGTH));
	}

	// delete_ok_kbに対するセッターとゲッターの集合
	public boolean setDeleteOkKb(String delete_ok_kb)
	{
		this.delete_ok_kb = delete_ok_kb;
		return true;
	}
	public String getDeleteOkKb()
	{
		return cutString(this.delete_ok_kb,DELETE_OK_KB_MAX_LENGTH);
	}
	public String getDeleteOkKbString()
	{
		return "'" + cutString(this.delete_ok_kb,DELETE_OK_KB_MAX_LENGTH) + "'";
	}
	public String getDeleteOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_ok_kb,DELETE_OK_KB_MAX_LENGTH));
	}

	// reference_ok_kbに対するセッターとゲッターの集合
	public boolean setReferenceOkKb(String reference_ok_kb)
	{
		this.reference_ok_kb = reference_ok_kb;
		return true;
	}
	public String getReferenceOkKb()
	{
		return cutString(this.reference_ok_kb,REFERENCE_OK_KB_MAX_LENGTH);
	}
	public String getReferenceOkKbString()
	{
		return "'" + cutString(this.reference_ok_kb,REFERENCE_OK_KB_MAX_LENGTH) + "'";
	}
	public String getReferenceOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.reference_ok_kb,REFERENCE_OK_KB_MAX_LENGTH));
	}

	// csv_ok_kbに対するセッターとゲッターの集合
	public boolean setCsvOkKb(String csv_ok_kb)
	{
		this.csv_ok_kb = csv_ok_kb;
		return true;
	}
	public String getCsvOkKb()
	{
		return cutString(this.csv_ok_kb,CSV_OK_KB_MAX_LENGTH);
	}
	public String getCsvOkKbString()
	{
		return "'" + cutString(this.csv_ok_kb,CSV_OK_KB_MAX_LENGTH) + "'";
	}
	public String getCsvOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.csv_ok_kb,CSV_OK_KB_MAX_LENGTH));
	}

	// print_ok_kbに対するセッターとゲッターの集合
	public boolean setPrintOkKb(String print_ok_kb)
	{
		this.print_ok_kb = print_ok_kb;
		return true;
	}
	public String getPrintOkKb()
	{
		return cutString(this.print_ok_kb,PRINT_OK_KB_MAX_LENGTH);
	}
	public String getPrintOkKbString()
	{
		return "'" + cutString(this.print_ok_kb,PRINT_OK_KB_MAX_LENGTH) + "'";
	}
	public String getPrintOkKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.print_ok_kb,PRINT_OK_KB_MAX_LENGTH));
	}


	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  cd_name = " + getKengenCdString()
		+ " existence_flg  = " + getGamenIdString()
		+ " existence_flg  = " + getSubmenuIdString()
		+ " existence_flg  = " + getGyouNoString()
		+ " existence_flg  = " + getRetuNoString()
		+ " existence_flg  = " + getInsertOkKbString()
		+ " existence_flg  = " + getUpdateOkKbString()
		+ " existence_flg  = " + getDeleteOkKbString()
		+ " existence_flg  = " + getReferenceOkKbString()
		+ " existence_flg  = " + getCsvOkKbString()
		+ " existence_flg  = " + getPrintOkKbString()
		+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst000701_MasterInfoBean コピー後のクラス
	 */
	public mst000801_SysKengenRiyouBean createClone()
	{
		mst000801_SysKengenRiyouBean bean = new mst000801_SysKengenRiyouBean();
		bean.setKengenCd( this.kengen_cd);
		bean.setGamenId( this.gamen_id);
		bean.setSubmenuId( this.submenu_id);
		bean.setGyouNo( this.gyou_no);
		bean.setRetuNo( this.retu_no);
		bean.setInsertOkKb( this.insert_ok_kb);
		bean.setUpdateOkKb( this.update_ok_kb);
		bean.setDeleteOkKb( this.delete_ok_kb);
		bean.setReferenceOkKb( this.reference_ok_kb);
		bean.setCsvOkKb( this.csv_ok_kb);
		bean.setPrintOkKb( this.print_ok_kb);

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
		if( !( o instanceof mst000701_MasterInfoBean ) ) return false;
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
