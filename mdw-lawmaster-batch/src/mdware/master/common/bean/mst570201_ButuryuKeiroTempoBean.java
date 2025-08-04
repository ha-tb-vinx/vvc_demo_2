/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/17)初版作成
 */
public class mst570201_ButuryuKeiroTempoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ

	private String tenpo_cd = null;	//店舗コード
	private String kanji_rn = null;	//略式名称(漢字)

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
	 * RBUTURYUKEIROBeanを１件のみ抽出したい時に使用する
	 */
	public static mst570201_ButuryuKeiroTempoBean getmst570201_ButuryuKeiroTempoBean(DataHolder dataHolder)
	{
		mst570201_ButuryuKeiroTempoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst570201_ButuryuKeiroTempoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst570201_ButuryuKeiroTempoBean )ite.next();
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


	// kanji_rnに対するセッターとゲッターの集合
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
	public String getKanjiRn()
	{
		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
	}
	public String getKanjiRnString()
	{
		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst570201_ButuryuKeiroTempoBean コピー後のクラス
	 */
	public mst570201_ButuryuKeiroTempoBean createClone()
	{
		mst570201_ButuryuKeiroTempoBean bean = new mst570201_ButuryuKeiroTempoBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setKanjiRn(this.kanji_rn);
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
		if( !( o instanceof mst570201_ButuryuKeiroTempoBean ) ) return false;
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
