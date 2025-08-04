/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
public class mst460101_HaisouLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コードの長さ
	public static final int HAISOSAKI_CD_MAX_LENGTH = 5;//配送先コードの長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;//正式名称(漢字)の長さ
	public static final int KANA_NA_MAX_LENGTH = 80;//正式名称(カナ)の長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ
	public static final int KANA_RN_MAX_LENGTH = 20;//略式名称(カナ)の長さ
	public static final int TOSAN_KB_MAX_LENGTH = 1;//倒産区分の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_NM_MAX_LENGTH = 20;//作成者名の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_NM_MAX_LENGTH = 20;//更新者名の長さ
	public static final int TENPO_CDL_MAX_LENGTH = 1024;//店舗コード配列の長さ
	public static final int TKANJI_RNL_MAX_LENGTH = 1024;//店舗略称配列の長さ
	public static final int FLGL_MAX_LENGTH = 1024;//店舗存在フラグ配列の長さ
	public static final int INSERT_TSL_MAX_LENGTH = 1024;//登録日時配列の長さ
	public static final int UPDATE_TSL_MAX_LENGTH = 1024;//更新日時配列の長さ
	public static final int INSERT_USER_IDL_MAX_LENGTH = 1024;//登録者ID配列の長さ
	public static final int UPDATE_USER_IDL_MAX_LENGTH = 1024;//更新者ID配列の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;//処理選択
	
	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String haisosaki_cd = null;	//配送先コード
	private String kanji_na = null;	//正式名称(漢字)
	private String kana_na = null;	//正式名称(カナ)
	private String kanji_rn = null;	//略式名称(漢字)
	private String kana_rn = null;	//略式名称(カナ)
	private String tosan_kb = null;	//倒産区分
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String insert_user_nm = null;	//作成者名
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String update_user_nm = null;	//更新者名
	private String tenpo_cdl = null;	//店舗コード配列
	private String tkanji_rnl = null;	//店舗略称配列
	private String flgl = null;	//店舗存在フラグ配列
	private String insert_tsl = null;	//登録日時配列
	private String update_tsl = null;	//更新日時配列
	private String insert_user_idl = null;//登録者ID配列
	private String update_user_idl = null;//更新者ID配列
	private String sentaku = null;	//処理選択

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
	 * mst460101_HaisouLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst460101_HaisouLBean getmst460101_HaisouLBean(DataHolder dataHolder)
	{
		mst460101_HaisouLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst460101_HaisouLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst460101_HaisouLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
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


	// haisosaki_cdに対するセッターとゲッターの集合
	public boolean setHaisosakiCd(String haisosaki_cd)
	{
		this.haisosaki_cd = haisosaki_cd;
		return true;
	}
	public String getHaisosakiCd()
	{
		return cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH);
	}
	public String getHaisosakiCdString()
	{
		return "'" + cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getHaisosakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haisosaki_cd,HAISOSAKI_CD_MAX_LENGTH));
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


	// tosan_kbに対するセッターとゲッターの集合
	public boolean setTosanKb(String tosan_kb)
	{
		this.tosan_kb = tosan_kb;
		return true;
	}
	public String getTosanKb()
	{
		return cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH);
	}
	public String getTosanKbString()
	{
		return "'" + cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH) + "'";
	}
	public String getTosanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tosan_kb,TOSAN_KB_MAX_LENGTH));
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


	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH);
	}
	public String getInsertUserNmString()
	{
		return "'" + cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH) + "'";
	}
	public String getInsertUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH));
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


	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH);
	}
	public String getUpdateUserNmString()
	{
		return "'" + cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH));
	}


	// tenpo_cdlに対するセッターとゲッターの集合
	public boolean setTenpoCdl(String tenpo_cdl)
	{
		this.tenpo_cdl = tenpo_cdl;
		return true;
	}
	public String getTenpoCdl()
	{
		return cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH);
	}
	public String getTenpoCdlString()
	{
		return "'" + cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH) + "'";
	}
	public String getTenpoCdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH));
	}


	// tkanji_rnlに対するセッターとゲッターの集合
	public boolean setTkanjiRnl(String tkanji_rnl)
	{
		this.tkanji_rnl = tkanji_rnl;
		return true;
	}
	public String getTkanjiRnl()
	{
		return cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH);
	}
	public String getTkanjiRnlString()
	{
		return "'" + cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH) + "'";
	}
	public String getTkanjiRnlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH));
	}


	// flglに対するセッターとゲッターの集合
	public boolean setFlgl(String flgl)
	{
		this.flgl = flgl;
		return true;
	}
	public String getFlgl()
	{
		return cutString(this.flgl,FLGL_MAX_LENGTH);
	}
	public String getFlglString()
	{
		return "'" + cutString(this.flgl,FLGL_MAX_LENGTH) + "'";
	}
	public String getFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.flgl,FLGL_MAX_LENGTH));
	}

	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTsl(String insert_tsl)
	{
		this.insert_tsl = insert_tsl;
		return true;
	}
	public String getInsertTsl()
	{
		return cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH);
	}
	public String getInsertTslString()
	{
		return "'" + cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH) + "'";
	}
	public String getInsertTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH));
	}

	// insert_user_idlに対するセッターとゲッターの集合
	public boolean setInsertUserIdl(String insert_user_idl)
	{
		this.insert_user_idl = insert_user_idl;
		return true;
	}
	public String getInsertUserIdl()
	{
		return cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH);
	}
	public String getInsertUserIdlString()
	{
		return "'" + cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH));
	}

	// update_tslに対するセッターとゲッターの集合
	public boolean setUpdateTsl(String update_tsl)
	{
		this.update_tsl = update_tsl;
		return true;
	}
	public String getUpdateTsl()
	{
		return cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getUpdateTslString()
	{
		return "'" + cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getUpdateTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH));
	}

	// update_user_idlに対するセッターとゲッターの集合
	public boolean setUpdateUserIdl(String update_user_idl)
	{
		this.update_user_idl = update_user_idl;
		return true;
	}
	public String getUpdateUserIdl()
	{
		return cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH);
	}
	public String getUpdateUserIdlString()
	{
		return "'" + cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH));
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
			+ "  haisosaki_cd = " + getHaisosakiCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  kana_rn = " + getKanaRnString()
			+ "  tosan_kb = " + getTosanKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  insert_user_nm = " + getInsertUserNmString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  update_user_nm = " + getUpdateUserNmString()
			+ "  tenpo_cdl = " + getTenpoCdlString()
			+ "  tkanji_rnl = " + getTkanjiRnlString()
			+ "  flgl = " + getFlglString()
			+ "  insert_tsl = " + getInsertTslString()
			+ "  insert_user_idl = " + getInsertUserIdlString()
			+ "  update_tsl = " + getUpdateTslString()
			+ "  update_user_idl = " + getUpdateUserIdlString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst460101_HaisouLBean コピー後のクラス
	 */
	public mst460101_HaisouLBean createClone()
	{
		mst460101_HaisouLBean bean = new mst460101_HaisouLBean();
		bean.setKanriKb(this.kanri_kb);
		bean.setKanriCd(this.kanri_cd);
		bean.setHaisosakiCd(this.haisosaki_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setKanjiRn(this.kanji_rn);
		bean.setKanaRn(this.kana_rn);
		bean.setTosanKb(this.tosan_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNm(this.insert_user_nm);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateUserNm(this.update_user_nm);
		bean.setTenpoCdl(this.tenpo_cdl);
		bean.setTkanjiRnl(this.tkanji_rnl);
		bean.setFlgl(this.flgl);
		bean.setInsertTsl(this.insert_tsl);
		bean.setInsertUserIdl(this.insert_user_idl);
		bean.setUpdateTsl(this.update_tsl);
		bean.setUpdateUserIdl(this.update_user_idl);
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
		if( !( o instanceof mst460101_HaisouLBean ) ) return false;
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
