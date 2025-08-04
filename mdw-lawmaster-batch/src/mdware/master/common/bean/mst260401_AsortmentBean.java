/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/25)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）アソートメントマスタ(衣料12桁）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するアソートメントマスタ(衣料12桁）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/25)初版作成
 */
public class mst260401_AsortmentBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int COLOR_CD_MAX_LENGTH = 2;//カラーコードの長さ
	public static final int SIZE_CD_MAX_LENGTH = 2;//サイズコードの長さ
	public static final int COLOR_NM_MAX_LENGTH = 20;//の長さ
	public static final int SIZE_NM_MAX_LENGTH = 20;//の長さ

	private String color_cd = null;	//カラーコード
	private String size_cd = null;	//サイズコード
	private String color_nm = null;	//
	private String size_nm = null;	//

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
	 * mst260401_AsortmentBeanを１件のみ抽出したい時に使用する
	 */
	public static mst260401_AsortmentBean getmst260401_AsortmentBean(DataHolder dataHolder)
	{
		mst260401_AsortmentBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst260401_AsortmentDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst260401_AsortmentBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// color_cdに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd)
	{
		this.color_cd = color_cd;
		return true;
	}
	public String getColorCd()
	{
		return cutString(this.color_cd,COLOR_CD_MAX_LENGTH);
	}
	public String getColorCdString()
	{
		return "'" + cutString(this.color_cd,COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_cd,COLOR_CD_MAX_LENGTH));
	}


	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = size_cd;
		return true;
	}
	public String getSizeCd()
	{
		return cutString(this.size_cd,SIZE_CD_MAX_LENGTH);
	}
	public String getSizeCdString()
	{
		return "'" + cutString(this.size_cd,SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getSizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_cd,SIZE_CD_MAX_LENGTH));
	}


	// color_nmに対するセッターとゲッターの集合
	public boolean setColorNm(String color_nm)
	{
		this.color_nm = color_nm;
		return true;
	}
	public String getColorNm()
	{
		return cutString(this.color_nm,COLOR_NM_MAX_LENGTH);
	}
	public String getColorNmString()
	{
		return "'" + cutString(this.color_nm,COLOR_NM_MAX_LENGTH) + "'";
	}
	public String getColorNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_nm,COLOR_NM_MAX_LENGTH));
	}


	// size_nmに対するセッターとゲッターの集合
	public boolean setSizeNm(String size_nm)
	{
		this.size_nm = size_nm;
		return true;
	}
	public String getSizeNm()
	{
		return cutString(this.size_nm,SIZE_NM_MAX_LENGTH);
	}
	public String getSizeNmString()
	{
		return "'" + cutString(this.size_nm,SIZE_NM_MAX_LENGTH) + "'";
	}
	public String getSizeNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_nm,SIZE_NM_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  color_cd = " + getColorCdString()
			+ "  size_cd = " + getSizeCdString()
			+ "  color_nm = " + getColorNmString()
			+ "  size_nm = " + getSizeNmString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst260401_AsortmentBean コピー後のクラス
	 */
	public mst260401_AsortmentBean createClone()
	{
		mst260401_AsortmentBean bean = new mst260401_AsortmentBean();
		bean.setColorCd(this.color_cd);
		bean.setSizeCd(this.size_cd);
		bean.setColorNm(this.color_nm);
		bean.setSizeNm(this.size_nm);
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
		if( !( o instanceof mst260401_AsortmentBean ) ) return false;
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
