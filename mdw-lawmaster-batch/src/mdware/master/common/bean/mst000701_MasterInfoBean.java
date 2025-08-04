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
 * @version 1.1 (2006/02/06) D.Matsumoto delete_fgを取得したいため追加
 */
public class mst000701_MasterInfoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int CD_NAME_MAX_LENGTH = 30;			//取得名称の長さ
	public static final int EXISTENCE_FLG_MAX_LENGTH = 1;	//存在フラグの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;	                //削除フラグの長さ   //2006/02/06 追加

	private String cd_name = null;			//取得名称
	private String existence_flg = null;		//存在フラグ
	private String delete_fg = null;		    //削除フラグ   //2006/02/06 追加


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




	// cd_nameに対するセッターとゲッターの集合
	public boolean setCdName(String cd_name)
	{
		this.cd_name = cd_name;
		return true;
	}
	public String getCdName()
	{
//BUGNO-006 2005.04.22 T.kikuchi START
		//return cutString(this.cd_name,CD_NAME_MAX_LENGTH);
		return this.cd_name;
//BUGNO-006 2005.04.22 T.kikuchi END
	}
	public String getCdNameString()
	{
		return "'" + cutString(this.cd_name,CD_NAME_MAX_LENGTH) + "'";
	}
	public String getCdNameHTMLString()
	{
//BUGNO-006 2005.04.22 T.kikuchi START
		//return HTMLStringUtil.convert(cutString(this.cd_name,CD_NAME_MAX_LENGTH));
		return HTMLStringUtil.convert(this.cd_name);
//BUGNO-006 2005.04.22 T.kikuchi END
	}

	// existence_flgに対するセッターとゲッターの集合
	public boolean setExistenceFlg(String existence_flg)
	{
		this.existence_flg = existence_flg;
		return true;
	}
	public String getExistenceFlg()
	{
		return cutString(this.existence_flg,EXISTENCE_FLG_MAX_LENGTH);
	}
	public String getExistenceFlgString()
	{
		return "'" + cutString(this.existence_flg,EXISTENCE_FLG_MAX_LENGTH) + "'";
	}
	public String getExistenceFlgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.existence_flg,EXISTENCE_FLG_MAX_LENGTH));
	}

    // 2006/02/06 追加開始
	// existence_flgに対するセッターとゲッターの集合
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
   // 2006/02/06 追加終了


	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  cd_name = " + getCdNameString()
		+ " existence_flg  = " + getExistenceFlgString()
		+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst000701_MasterInfoBean コピー後のクラス
	 */
	public mst000701_MasterInfoBean createClone()
	{
		mst000701_MasterInfoBean bean = new mst000701_MasterInfoBean();
		bean.setCdName(this.cd_name);
		bean.setExistenceFlg(this.existence_flg);
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
