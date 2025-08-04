/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
public class mst630101_SyuukeiSyubetuBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	/** 集計種別コードデータ長 */
	public static final int SYUKEISYUBETU_CD_MAX_LENGTH = 4;
	/** 正式名称(漢字)データ長 */
	public static final int KANJI_NA_MAX_LENGTH = 80;
	/** 正式名称(カナ)データ長 */
	public static final int KANA_NA_MAX_LENGTH = 80;
	/** 作成年月日データ長 */
	public static final int INSERT_TS_MAX_LENGTH = 20;
	/** 作成者社員IDデータ長 */
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;
	/** 更新年月日データ長 */
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	/** 更新者社員IDデータ長 */
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;
	/** 削除フラグデータ長 */
	public static final int DELETE_FG_MAX_LENGTH = 1;
	/** ユーザIDデータ長 */	
	public static final int USER_ID_MAX_LENGTH = 7;
	/** ユーザ氏名(insert)データ長 */
	public static final int USER_NA_INSERT_MAX_LENGTH = 20;
	/** ユーザ氏名(update)データ長 */
	public static final int USER_NA_UPDATE_MAX_LENGTH = 20;
	/** 集計品種コードデータ長 */	
	public static final int SYUKEI_HINSYU_CD_RENKAN_MAX_LENGTH = 4;
	/** 集計種別品種連関更新年月日データ長 */
	public static final int UPDATE_TS_RENKAN_MAX_LENGTH = 20;
	/** 選択処理数 */	
	public static final int SENTAKU_MAX_LENGTH = 1;
	
	public static final int SYUKEISYUBETU_NA_MAX_LENGTH = 36;
	public static final int SYUKEISYUBETU_KA_MAX_LENGTH = 18;
	public static final int SYORISYA_USER_ID_MAX_LENGTH = 10;
	public static final int SYORISYA_USER_NA_MAX_LENGTH = 20;

	private String syukeisyubetu_cd = null;
	private String kanji_na = null;
	private String kana_na = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String delete_fg = null;
	private String user_id = null;
	private String user_na_insert = null;
	private String user_na_update = null;
	private String syukei_hinsyu_cd_renkan = null;
	private String update_ts_renkan = null;
	private String sentaku = null;					//処理選択
    private String InsertData = null; // 追加処理判定

	private String syukeisyubetu_na = null;
	private String syukeisyubetu_ka = null;
	private String syorisya_user_id = null;
	private String syorisya_user_na = null;

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	/**  ＤＢ更新を行う時に役に立つフラグ 	 
	 * @return createDatabase createDatabase
	 */ 
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

	/**
	 * mst630101_SyuukeiSyubetuBeanを１件のみ抽出したい時に使用する
	 * @param dataHolder dataHolder
	 * @return mst630101_SyuukeiSyubetuBean mst630101_SyuukeiSyubetuBean
	 */
	public static mst630101_SyuukeiSyubetuBean getmst630101_SyuukeiSyubetuBean(DataHolder dataHolder)
	{
		mst630101_SyuukeiSyubetuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst630101_SyuukeiSyubetuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst630101_SyuukeiSyubetuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	/**  syukei_syubetu_cdに対するセッター
	 * @param syukei_syubetu_cd syukei_syubetu_cd
	 * @return boolean
	 */
	public boolean setSyukeiSyubetuCd(String syukeisyubetu_cd)
	{
		this.syukeisyubetu_cd = syukeisyubetu_cd;
		return true;
	}
    /** syukei_syubetu_cdに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiSyubetuCd()
	{
		return cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH);
	}
    /** syukei_syubetu_cdに対するゲッター 	
     * @return boolean
     */
	public String getSyukeiSyubetuCdString()
	{
		return "'" + cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH) + "'";
	}
    /** syukei_syubetu_cdに対するゲッター 
     * 	 * @return boolean
     */
	public String getSyukeiSyubetuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH));
	}


	/**  kanji_naに対するセッター
	 * @param kanji_na kanji_na
	 * @return boolean
	 */
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
    /** kanji_naに対するゲッター 
	 * @return boolean
     */
	public String getKanjiNa()
	{
		return cutString(this.kanji_na,KANJI_NA_MAX_LENGTH);
	}
    /** kanji_naに対するゲッター 
	 * @return boolean
     */
	public String getKanjiNaString()
	{
		return "'" + cutString(this.kanji_na,KANJI_NA_MAX_LENGTH) + "'";
	}
    /** kanji_naに対するゲッター 
	 * @return boolean
     */
	public String getKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na,KANJI_NA_MAX_LENGTH));
	}


	/**  kana_naに対するセッター
	 * @param kana_na kanji_na
	 * @return boolean
	 */
	public boolean setKanaNa(String kana_na)
	{
		this.kana_na = kana_na;
		return true;
	}
    /** kana_naに対するゲッター 
	 * @return boolean
     */
	public String getKanaNa()
	{
		return cutString(this.kana_na,KANA_NA_MAX_LENGTH);
	}
    /** kana_naに対するゲッター 
	 * @return boolean
     */
	public String getKanaNaString()
	{
		return "'" + cutString(this.kana_na,KANA_NA_MAX_LENGTH) + "'";
	}
    /** kana_naに対するゲッター 
	 * @return boolean
     */
	public String getKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_na,KANA_NA_MAX_LENGTH));
	}


	/**  insert_tsに対するセッター
	 * @param insert_ts insert_ts
	 * @return boolean
	 */
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}


	/**  insert_user_idに対するセッター
	 * @param insert_user_id insert_user_id
	 * @return boolean
	 */
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
    /** insert_user_idに対するゲッター 
	 * @return boolean
     */
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
    /** insert_user_idに対するゲッター 
	 * @return boolean
     */
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
    /** insert_user_idに対するゲッター 
	 * @return boolean
     */
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}


	/**  update_tsに対するセッター
	 * @param update_ts update_ts
	 * @return boolean
	 */
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}


	/**  update_user_idに対するセッター
	 * @param update_user_id update_user_id
	 * @return boolean
	 */
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
    /** update_user_idに対するゲッター 
	 * @return boolean
     */
	public String getUpdateUserId()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
    /** update_user_idに対するゲッター 
	 * @return boolean
     */
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
    /** update_user_idに対するゲッター 
	 * @return boolean
     */
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}


	/**  delete_fgに対するセッター
	 * @param delete_fg delete_fg
	 * @return boolean
	 */
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
		return true;
	}
    /** delete_fgに対するゲッター 
	 * @return boolean
     */
	public String getDeleteFg()
	{
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
    /** delete_fgに対するゲッター 
	 * @return boolean
     */
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
    /** delete_fgに対するゲッター 
	 * @return boolean
     */
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}


	/**  user_idに対するセッター
	 * @param user_id user_id
	 * @return boolean
	 */
	public boolean setUserId(String user_id)
	{
		this.user_id = user_id;
		return true;
	}
    /** user_idに対するゲッター 
	 * @return boolean
     */
	public String getUserId()
	{
		return cutString(this.user_id,USER_ID_MAX_LENGTH);
	}
    /** user_idに対するゲッター 
	 * @return boolean
     */
	public String getUserIdString()
	{
		return "'" + cutString(this.user_id,USER_ID_MAX_LENGTH) + "'";
	}
    /** user_idに対するゲッター 
	 * @return boolean
     */
	public String getUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_id,USER_ID_MAX_LENGTH));
	}


	/**  user_na_insertに対するセッター
	 * @param user_na_insert user_na_insert
	 * @return boolean
	 */
	public boolean setUserNaInsert(String user_na_insert)
	{
		this.user_na_insert = user_na_insert;
		return true;
	}
    /** user_na_insertに対するゲッター 
	 * @return boolean
     */
	public String getUserNaInsert()
	{
		return cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH);
	}
    /** user_na_insertに対するゲッター 
	 * @return boolean
     */
	public String getUserNaInsertString()
	{
		return "'" + cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH) + "'";
	}
    /** user_na_insertに対するゲッター 
	 * @return boolean
     */
	public String getUserNaInsertHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH));
	}
	
	/**  user_na_updateに対するセッター
	 * @param user_na_update user_na_update
	 * @return boolean
	 */
	public boolean setUserNaUpdate(String user_na_update)
	{
		this.user_na_update = user_na_update;
		return true;
	}
    /** user_na_updateに対するゲッター 
	 * @return boolean
     */
	public String getUserNaUpdate()
	{
		return cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH);
	}
    /** user_na_updateに対するゲッター 
	 * @return boolean
     */
	public String getUserNaUpdateString()
	{
		return "'" + cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH) + "'";
	}
    /** user_na_updateに対するゲッター 
	 * @return boolean
     */
	public String getUserNaUpdateHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH));
	}
	
	/**  syukei_hinsyu_cd_renkanに対するセッター
	 * @param syukei_hinsyu_cd_renkan syukei_hinsyu_cd_renkan
	 * @return boolean
	 */
	public boolean setSyukeiHinsyuCdRenkan(String syukei_hinsyu_cd_renkan)
	{
		this.syukei_hinsyu_cd_renkan = syukei_hinsyu_cd_renkan;
		return true;
	}
    /** syukei_hinsyu_cd_renkanに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiHinsyuCdRenkan()
	{
		return cutString(this.syukei_hinsyu_cd_renkan,SYUKEI_HINSYU_CD_RENKAN_MAX_LENGTH);
	}
    /** syukei_hinsyu_cd_renkanに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiHinsyuCdRenkantring()
	{
		return "'" + cutString(this.syukei_hinsyu_cd_renkan,SYUKEI_HINSYU_CD_RENKAN_MAX_LENGTH) + "'";
	}
    /** syukei_hinsyu_cd_renkanに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiHinsyuCdRenkanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_hinsyu_cd_renkan,SYUKEI_HINSYU_CD_RENKAN_MAX_LENGTH));
	}

	/**  update_ts_renkanに対するセッター
	 * @param update_ts_renkan update_ts_renkan
	 * @return boolean
	 */
	public boolean setUpdateTsRenkan(String update_ts_renkan)
	{
		this.update_ts_renkan = update_ts_renkan;
		return true;
	}	
    /** update_ts_renkanに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsRenkan()
	{
		return cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH);
	}
    /** update_ts_renkanに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsRenkanString()
	{
		return "'" + cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH) + "'";
	}
    /** update_ts_renkanに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsRenkanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH));
	}

	/**  syukeisyubetu_naに対するセッター
	 * @param syukeisyubetu_na syukeisyubetu_na
	 * @return boolean
	 */
	public boolean setSyukeiSyubetuNa(String syukeisyubetu_na)
	{
		this.syukeisyubetu_na = syukeisyubetu_na;
		return true;
	}
    /** syukeisyubetu_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiSyubetuNa()
	{
		return cutString(this.syukeisyubetu_na,SYUKEISYUBETU_NA_MAX_LENGTH);
	}
    /** syukeisyubetu_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiSyubetuNaString()
	{
		return "'" + cutString(this.syukeisyubetu_na,SYUKEISYUBETU_NA_MAX_LENGTH) + "'";
	}
    /** syukeisyubetu_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiSyubetuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeisyubetu_na,SYUKEISYUBETU_NA_MAX_LENGTH));
	}
	
	
	
	/**  syukeisyubetu_kaに対するセッター
	 * @param syukeisyubetu_ka syukeisyubetu_ka
	 * @return boolean
	 */
	public boolean setSyukeiSyubetuKa(String syukeisyubetu_ka)
	{
		this.syukeisyubetu_ka = syukeisyubetu_ka;
		return true;
	}
	/** syukeisyubetu_kaに対するゲッター 
	 * @return boolean
	 */
	public String getSyukeiSyubetuKa()
	{
		return cutString(this.syukeisyubetu_ka,SYUKEISYUBETU_KA_MAX_LENGTH);
	}
	/** syukeisyubetu_kaに対するゲッター 
	 * @return boolean
	 */
	public String getSyukeiSyubetuKaString()
	{
		return "'" + cutString(this.syukeisyubetu_ka,SYUKEISYUBETU_KA_MAX_LENGTH) + "'";
	}
	/** syukeisyubetu_kaに対するゲッター 
	 * @return boolean
	 */
	public String getSyukeiSyubetuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeisyubetu_ka,SYUKEISYUBETU_KA_MAX_LENGTH));
	}
	
	
	/**  syorisya_user_idに対するセッター
	 * @param syorisya_user_id syorisya_user_id
	 * @return boolean
	 */
	public boolean setSyorisyaUserId(String syorisya_user_id)
	{
		this.syorisya_user_id = syorisya_user_id;
		return true;
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserId()
	{
		return cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH);
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserIdString()
	{
		return "'" + cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH) + "'";
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH));
	}
	
	
	/**  syorisya_user_naに対するセッター
	 * @param syorisya_user_na syorisya_user_na
	 * @return boolean
	 */
	public boolean setSyorisyaUserNa(String syorisya_user_na)
	{
		this.syorisya_user_na = syorisya_user_na;
		return true;
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNa()
	{
		return cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH);
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNaString()
	{
		return "'" + cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH) + "'";
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH));
	}
	
	
	/**  sentakuに対するセッター
	 * @param sentaku sentaku
	 * @return boolean
	 */
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}
	

    /**
     * @return InsertData を戻します。
     */
    public String getInsertData() {
        return InsertData;
    }

    /**
     * @param InsertData
     *            InsertData を設定。
     */
    public void setInsertData(String insertData) {
        this.InsertData = insertData;
    }
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  syukeisyubetu_cd = " + getSyukeiSyubetuCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  user_id = " + getUserIdString()
			+ "  user_na_insert = " + getUserNaInsertString()
			+ "  user_na_update = " + getUserNaUpdateString()
			+ "  syukeisyubetu_na = " + getSyukeiSyubetuNaString()
			+ "  syukeisyuebtu_ka = " + getSyukeiSyubetuKaString()
			+ "  syorisya_user_id = " + getSyorisyaUserIdString()
			+ "  syorisya_user_na = " + getSyorisyaUserNaString()			
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst630101_SyuukeiSyubetuBean コピー後のクラス
	 */
	public mst630101_SyuukeiSyubetuBean createClone()
	{
		mst630101_SyuukeiSyubetuBean bean = new mst630101_SyuukeiSyubetuBean();
		bean.setSyukeiSyubetuCd(this.syukeisyubetu_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setUserId(this.user_id);
		bean.setUserNaInsert(this.user_na_insert);
		bean.setUserNaUpdate(this.user_na_update);
		bean.setSyukeiSyubetuNa(this.syukeisyubetu_na);
		bean.setSyukeiSyubetuKa(this.syukeisyubetu_ka);
		bean.setSyorisyaUserId(this.syorisya_user_id);
		bean.setSyorisyaUserNa(this.syorisya_user_na);
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param o 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst630101_SyuukeiSyubetuBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param base カットされる文字列
	 * @param max 最大バイト数
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
