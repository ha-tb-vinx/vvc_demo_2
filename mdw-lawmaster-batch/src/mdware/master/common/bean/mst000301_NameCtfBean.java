/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのレコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.bean.mst000301_NameCtfDM;
/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst000301_SelectBean用名称CTFのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst000301_SelectBean用名称CTFのレコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst000301_NameCtfBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	public static final int SYUBETU_NO_CD_MAX_LENGTH = 4;//種別NOの長さ
	public static final int SYUBETU_NO_CD_MAX_LENGTH = 10;//種別NOの長さ
	public static final int CODE_CD_MAX_LENGTH = 10;//コードの長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;//正式名称(漢字)の長さ
	public static final int KANA_NA_MAX_LENGTH = 80;//正式名称(カナ)の長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ
	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
	public static final int ZOKUSEI_CD_MAX_LENGTH = 30;//属性コードの長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String syubetu_no_cd = null;	//種別NO
	private String code_cd = null;	//コード
	private String kanji_na = null;	//正式名称(漢字)
	private String kana_na = null;	//正式名称(カナ)
	private String kanji_rn = null;	//略式名称(漢字)
	private String kana_rn = null;	//略式名称(カナ)
	private String zokusei_cd = null;	//属性コード
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
	 * NameCtfBeanを１件のみ抽出したい時に使用する
	 */
	public static mst000301_NameCtfBean getNameCtfBean(DataHolder dataHolder)
	{
		mst000301_NameCtfBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst000301_NameCtfDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst000301_NameCtfBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// syubetu_no_cdに対するセッターとゲッターの集合
	public boolean setSyubetuNoCd(String syubetu_no_cd)
	{
		this.syubetu_no_cd = syubetu_no_cd;
		return true;
	}
	public String getSyubetuNoCd()
	{
		return cutString(this.syubetu_no_cd,SYUBETU_NO_CD_MAX_LENGTH);
	}
	public String getSyubetuNoCdString()
	{
		return "'" + cutString(this.syubetu_no_cd,SYUBETU_NO_CD_MAX_LENGTH) + "'";
	}
	public String getSyubetuNoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syubetu_no_cd,SYUBETU_NO_CD_MAX_LENGTH));
	}


	// code_cdに対するセッターとゲッターの集合
	public boolean setCodeCd(String code_cd)
	{
		this.code_cd = code_cd;
		return true;
	}
	public String getCodeCd()
	{
		return cutString(this.code_cd,CODE_CD_MAX_LENGTH);
	}
	public String getCodeCdString()
	{
		return "'" + cutString(this.code_cd,CODE_CD_MAX_LENGTH) + "'";
	}
	public String getCodeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.code_cd,CODE_CD_MAX_LENGTH));
	}


	// kanji_naに対するセッターとゲッターの集合
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
	public String getKanjiNa()
	{
		return cutString(this.kanji_na,KANJI_NA_MAX_LENGTH);
	}
	public String getKanjiNaString()
	{
		return "'" + cutString(this.kanji_na,KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na,KANJI_NA_MAX_LENGTH));
	}


	// kana_naに対するセッターとゲッターの集合
	public boolean setKanaNa(String kana_na)
	{
		this.kana_na = kana_na;
		return true;
	}
	public String getKanaNa()
	{
		return cutString(this.kana_na,KANA_NA_MAX_LENGTH);
	}
	public String getKanaNaString()
	{
		return "'" + cutString(this.kana_na,KANA_NA_MAX_LENGTH) + "'";
	}
	public String getKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_na,KANA_NA_MAX_LENGTH));
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


	// kana_rnに対するセッターとゲッターの集合
	public boolean setKanaRn(String kana_rn)
	{
		this.kana_rn = kana_rn;
		return true;
	}
	public String getKanaRn()
	{
		return cutString(this.kana_rn,KANA_RN_MAX_LENGTH);
	}
	public String getKanaRnString()
	{
		return "'" + cutString(this.kana_rn,KANA_RN_MAX_LENGTH) + "'";
	}
	public String getKanaRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_rn,KANA_RN_MAX_LENGTH));
	}


	// zokusei_cdに対するセッターとゲッターの集合
	public boolean setZokuseiCd(String zokusei_cd)
	{
		this.zokusei_cd = zokusei_cd;
		return true;
	}
	public String getZokuseiCd()
	{
		return cutString(this.zokusei_cd,ZOKUSEI_CD_MAX_LENGTH);
	}
	public String getZokuseiCdString()
	{
		return "'" + cutString(this.zokusei_cd,ZOKUSEI_CD_MAX_LENGTH) + "'";
	}
	public String getZokuseiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zokusei_cd,ZOKUSEI_CD_MAX_LENGTH));
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
		return "  syubetu_no_cd = " + getSyubetuNoCdString()
			+ "  code_cd = " + getCodeCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  kana_rn = " + getKanaRnString()
			+ "  zokusei_cd = " + getZokuseiCdString()
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
	 * @return NameCtfBean コピー後のクラス
	 */
	public mst000301_NameCtfBean createClone()
	{
		mst000301_NameCtfBean bean = new mst000301_NameCtfBean();
		bean.setSyubetuNoCd(this.syubetu_no_cd);
		bean.setCodeCd(this.code_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setKanjiRn(this.kanji_rn);
		bean.setKanaRn(this.kana_rn);
		bean.setZokuseiCd(this.zokusei_cd);
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
		if( !( o instanceof mst000301_NameCtfBean ) ) return false;
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
