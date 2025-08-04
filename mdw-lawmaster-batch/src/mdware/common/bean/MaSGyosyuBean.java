package mdware.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/9/20 14:52:49)
 */
public class MaSGyosyuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int S_GYOSYU_NA_MAX_LENGTH = 36;
	public static final int S_GYOSYU_KA_MAX_LENGTH = 18;
	public static final int L_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int INSERT_TS_MAX_LENGTH = 20;

	private String s_gyosyu_cd = null;
	private String s_gyosyu_na = null;
	private String s_gyosyu_ka = null;
	private String l_gyosyu_cd = null;
	private String insert_ts = null;

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
	 * MaSGyosyuBeanを１件のみ抽出したい時に使用する
	 */
	public static MaSGyosyuBean getMaSGyosyuBean(DataHolder dataHolder)
	{
		MaSGyosyuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaSGyosyuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaSGyosyuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// s_gyosyu_naに対するセッターとゲッターの集合
	public boolean setSGyosyuNa(String s_gyosyu_na)
	{
		this.s_gyosyu_na = s_gyosyu_na;
		return true;
	}
	public String getSGyosyuNa()
	{
		return cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH);
	}
	public String getSGyosyuNaString()
	{
		return "'" + cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH) + "'";
	}
	public String getSGyosyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH));
	}


	// s_gyosyu_kaに対するセッターとゲッターの集合
	public boolean setSGyosyuKa(String s_gyosyu_ka)
	{
		this.s_gyosyu_ka = s_gyosyu_ka;
		return true;
	}
	public String getSGyosyuKa()
	{
		return cutString(this.s_gyosyu_ka,S_GYOSYU_KA_MAX_LENGTH);
	}
	public String getSGyosyuKaString()
	{
		return "'" + cutString(this.s_gyosyu_ka,S_GYOSYU_KA_MAX_LENGTH) + "'";
	}
	public String getSGyosyuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_ka,S_GYOSYU_KA_MAX_LENGTH));
	}


	// l_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setLGyosyuCd(String l_gyosyu_cd)
	{
		this.l_gyosyu_cd = l_gyosyu_cd;
		return true;
	}
	public String getLGyosyuCd()
	{
		return cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH);
	}
	public String getLGyosyuCdString()
	{
		return "'" + cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getLGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_gyosyu_cd,L_GYOSYU_CD_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  s_gyosyu_na = " + getSGyosyuNaString()
			+ "  s_gyosyu_ka = " + getSGyosyuKaString()
			+ "  l_gyosyu_cd = " + getLGyosyuCdString()
			+ "  insert_ts = " + getInsertTsString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaSGyosyuBean コピー後のクラス
	 */
	public MaSGyosyuBean createClone()
	{
		MaSGyosyuBean bean = new MaSGyosyuBean();
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setSGyosyuNa(this.s_gyosyu_na);
		bean.setSGyosyuKa(this.s_gyosyu_ka);
		bean.setLGyosyuCd(this.l_gyosyu_cd);
		bean.setInsertTs(this.insert_ts);
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
		if( !( o instanceof MaSGyosyuBean ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
