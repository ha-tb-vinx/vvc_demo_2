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
public class mst640101_SyuukeiSyubetuInfoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SYUKEIHINSYU_CD_MAX_LENGTH = 6;
	public static final int KANJI_NA_MAX_LENGTH =80;
	public static final int KANA_NA_MAX_LENGTH = 80;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int INSERT_USER_ID_MAX_LENGTH = 7;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 7;
	public static final int DELETE_FG_MAX_LENGTH = 1;
	/** ユーザ氏名(insert)データ長 */
	public static final int USER_NA_INSERT_MAX_LENGTH = 20;
	/** ユーザ氏名(update)データ長 */
	public static final int USER_NA_UPDATE_MAX_LENGTH = 20;
	/** 集計種別コードデータ長 */	
	public static final int SYUKEI_SYUBETU_CD_RENKAN_MAX_LENGTH = 6;
	/** 集計種別品種連関更新年月日データ長 */
	public static final int UPDATE_TS_RENKAN_MAX_LENGTH = 20;
	/** 品種コードデータ長 */	
	public static final int HINSYU_CD_HINSYUBETUHINSYU_MAX_LENGTH = 4;
	/** 集計品種別品種更新年月日データ長 */
	public static final int UPDATE_TS_HINSYUBETUHINSYU_MAX_LENGTH = 20;
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択の長さ
	
	public static final int SYUKEIHINSYU_NA_MAX_LENGTH = 36;
	public static final int SYUKEIHINSYU_KA_MAX_LENGTH = 18;
	public static final int SYUKEISYUBETU_CD_MAX_LENGTH = 4;
	public static final int SYORISYA_USER_ID_MAX_LENGTH = 10;
	public static final int SYORISYA_USER_NA_MAX_LENGTH = 20;
	public static final int GYOSYU_CD_MAX_LENGTH = 2;

	private String syukeihinsyu_cd = null;
	private String kanji_na = null;
	private String kana_na = null;
	private String syukei_hinsyu_cd_add = null;
	private String kanji_na_add = null;
	private String kana_na_add = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String delete_fg = null;
	private String user_na_insert = null;
	private String user_na_update = null;
	private String syukei_syubetu_cd_renkan = null;
	private String update_ts_renkan = null;
	private String hinsyu_cd_hinsyubetuhinsyu = null;
	private String update_ts_hinsyubetuhinsyu = null;
	private String sentaku = null;					//処理選択
	private String sentakuAdd = null;					//処理選択
    private String InsertData = null; // 追加処理判定

	private String syukeihinsyu_na = null;
	private String syukeihinsyu_ka = null;
	private String syukeisyubetu_cd = null;
	private String syorisya_user_id = null;
	private String syorisya_user_na = null;
	private String gyosyu_cd = null;
	
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
	 * mst640101_SyuukeiSyubetuInfoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst640101_SyuukeiSyubetuInfoBean getmst640101_SyuukeiSyubetuInfoBean(DataHolder dataHolder)
	{
		mst640101_SyuukeiSyubetuInfoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst640101_SyuukeiSyubetuInfoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst640101_SyuukeiSyubetuInfoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	// syukei_hinsyu_cdに対するセッターとゲッターの集合
	public boolean setSyukeiHinsyuCd(String syukeihinsyu_cd)
	{
		this.syukeihinsyu_cd = syukeihinsyu_cd;
		return true;
	}
	public String getSyukeiHinsyuCd()
	{
		return cutString(this.syukeihinsyu_cd,SYUKEIHINSYU_CD_MAX_LENGTH);
	}
	public String getSyukeiHinsyuCdString()
	{
		return "'" + cutString(this.syukeihinsyu_cd,SYUKEIHINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_cd,SYUKEIHINSYU_CD_MAX_LENGTH));
	}

	
	// syukei_hinsyu_cd_addに対するセッターとゲッターの集合
	public boolean setSyukeiHinsyuCdAdd(String syukei_hinsyu_cd_add)
	{
		this.syukei_hinsyu_cd_add = syukei_hinsyu_cd_add;
		return true;
	}
	public String getSyukeiHinsyuCdAdd()
	{
		return cutString(this.syukei_hinsyu_cd_add,SYUKEIHINSYU_CD_MAX_LENGTH);
	}
	public String getSyukeiHinsyuCdAddString()
	{
		return "'" + cutString(this.syukei_hinsyu_cd_add,SYUKEIHINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiHinsyuCdAddHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_hinsyu_cd_add,SYUKEIHINSYU_CD_MAX_LENGTH));
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


	// kanji_na_addに対するセッターとゲッターの集合
	public boolean setKanjiNaAdd(String kanji_na_add)
	{
		this.kanji_na_add = kanji_na_add;
		return true;
	}
	public String getKanjiNaAdd()
	{
		return cutString(this.kanji_na_add,KANJI_NA_MAX_LENGTH);
	}
	public String getKanjiNaAddString()
	{
		return "'" + cutString(this.kanji_na_add,KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKanjiNaAddHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_add,KANJI_NA_MAX_LENGTH));
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

	
	// kana_na_addに対するセッターとゲッターの集合
	public boolean setKanaNaAdd(String kana_na_add)
	{
		this.kana_na_add = kana_na_add;
		return true;
	}
	public String getKanaNaAdd()
	{
		return cutString(this.kana_na_add,KANA_NA_MAX_LENGTH);
	}
	public String getKanaNaAddString()
	{
		return "'" + cutString(this.kana_na_add,KANA_NA_MAX_LENGTH) + "'";
	}
	public String getKanaNaAddHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kana_na_add,KANA_NA_MAX_LENGTH));
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

	// user_na_insertに対するセッターとゲッターの集合
	public boolean setUserNaInsert(String user_na_insert)
	{
		this.user_na_insert = user_na_insert;
		return true;
	}
	public String getUserNaInsert()
	{
		return cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH);
	}
	public String getUserNaInsertString()
	{
		return "'" + cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH) + "'";
	}
	public String getUserNaInsertHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_na_insert,USER_NA_INSERT_MAX_LENGTH));
	}
	
	// user_na_updateに対するセッターとゲッターの集合
	public boolean setUserNaUpdate(String user_na_update)
	{
		this.user_na_update = user_na_update;
		return true;
	}
	public String getUserNaUpdate()
	{
		return cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH);
	}
	public String getUserNaUpdateString()
	{
		return "'" + cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH) + "'";
	}
	public String getUserNaUpdateHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_na_update,USER_NA_UPDATE_MAX_LENGTH));
	}	
	
	// syukei_syubetu_cd_renkanに対するセッターとゲッターの集合
	public boolean setSyukeiSyubetuCdRenkan(String syukei_syubetu_cd_renkan)
	{
		this.syukei_syubetu_cd_renkan = syukei_syubetu_cd_renkan;
		return true;
	}
	public String getSyukeiSyubetuCdRenkan()
	{
		return cutString(this.syukei_syubetu_cd_renkan,SYUKEI_SYUBETU_CD_RENKAN_MAX_LENGTH);
	}
	public String getSyukeiSyubetuCdRenkantring()
	{
		return "'" + cutString(this.syukei_syubetu_cd_renkan,SYUKEI_SYUBETU_CD_RENKAN_MAX_LENGTH) + "'";
	}
	public String getSyukeiSyubetuCdRenkanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_syubetu_cd_renkan,SYUKEI_SYUBETU_CD_RENKAN_MAX_LENGTH));
	}	

	// update_ts_renkanに対するセッターとゲッターの集合
	public boolean setUpdateTsRenkan(String update_ts_renkan)
	{
		this.update_ts_renkan = update_ts_renkan;
		return true;
	}
	public String getUpdateTsRenkan()
	{
		return cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH);
	}
	public String getUpdateTsRenkanString()
	{
		return "'" + cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH) + "'";
	}
	public String getUpdateTsRenkanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts_renkan,UPDATE_TS_RENKAN_MAX_LENGTH));
	}	

	// hinsyu_cd_hinsyubetuhinsyuに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd_hinsyubetuhinsyu)
	{
		this.hinsyu_cd_hinsyubetuhinsyu = hinsyu_cd_hinsyubetuhinsyu;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd_hinsyubetuhinsyu,HINSYU_CD_HINSYUBETUHINSYU_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd_hinsyubetuhinsyu,HINSYU_CD_HINSYUBETUHINSYU_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd_hinsyubetuhinsyu,HINSYU_CD_HINSYUBETUHINSYU_MAX_LENGTH));
	}
	
	// update_ts_hinsyubetuhinsyuに対するセッターとゲッターの集合
	public boolean setUpdateTsHinsyubetuhinsyu(String update_ts_hinsyubetuhinsyu)
	{
		this.update_ts_hinsyubetuhinsyu = update_ts_hinsyubetuhinsyu;
		return true;
	}
	public String getUpdateTsHinsyubetuhinsyu()
	{
		return cutString(this.update_ts_hinsyubetuhinsyu,UPDATE_TS_HINSYUBETUHINSYU_MAX_LENGTH);
	}
	public String getUpdateTsHinsyubetuhinsyuString()
	{
		return "'" + cutString(this.update_ts_hinsyubetuhinsyu,UPDATE_TS_HINSYUBETUHINSYU_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHinsyubetuhinsyuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts_hinsyubetuhinsyu,UPDATE_TS_HINSYUBETUHINSYU_MAX_LENGTH));
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

	// sentakuAddに対するセッターとゲッターの集合
	public boolean setSentakuAdd(String sentakuAdd)
	{
		this.sentakuAdd = sentakuAdd;
		return true;
	}
	public String getSentakuAdd()
	{
		return cutString(this.sentakuAdd,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuAddString()
	{
		return "'" + cutString(this.sentakuAdd,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuAddHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentakuAdd,SENTAKU_MAX_LENGTH));
	}
	
	
	
	// syukeihinsyu_naに対するセッターとゲッターの集合
	public boolean setSyukeiHinsyuNa(String syukeihinsyu_na)
	{
		this.syukeihinsyu_na = syukeihinsyu_na;
		return true;
	}
	public String getSyukeiHinsyuNa()
	{
		return cutString(this.syukeihinsyu_na,SYUKEIHINSYU_NA_MAX_LENGTH);
	}
	public String getSyukeiHinsyuNaString()
	{
		return "'" + cutString(this.syukeihinsyu_na,SYUKEIHINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getSyukeiHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_na,SYUKEIHINSYU_NA_MAX_LENGTH));
	}
	
	
	// syukeihinsyu_kaに対するセッターとゲッターの集合
	public boolean setSyukeiHinsyuKa(String syukeihinsyu_ka)
	{
		this.syukeihinsyu_ka = syukeihinsyu_ka;
		return true;
	}
	public String getSyukeiHinsyuKa()
	{
		return cutString(this.syukeihinsyu_ka,SYUKEIHINSYU_KA_MAX_LENGTH);
	}
	public String getSyukeiHinsyuKaString()
	{
		return "'" + cutString(this.syukeihinsyu_ka,SYUKEIHINSYU_KA_MAX_LENGTH) + "'";
	}
	public String getSyukeiHinsyuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_ka,SYUKEIHINSYU_KA_MAX_LENGTH));
	}
	
	
	// syukeisyubetu_cdに対するセッターとゲッターの集合
	public boolean setSyukeiSyubetuCd(String syukeisyubetu_cd)
	{
		this.syukeisyubetu_cd = syukeisyubetu_cd;
		return true;
	}
	public String getSyukeiSyubetuCd()
	{
		return cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH);
	}
	public String getSyukeiSyubetuCdString()
	{
		return "'" + cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiSyubetuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeisyubetu_cd,SYUKEISYUBETU_CD_MAX_LENGTH));
	}
	
	
	// syorisya_user_idに対するセッターとゲッターの集合
	public boolean setSyorisyaUserId(String syorisya_user_id)
	{
		this.syorisya_user_id = syorisya_user_id;
		return true;
	}
	public String getSyorisyaUserId()
	{
		return cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH);
	}
	public String getSyorisyaUserIdString()
	{
		return "'" + cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyorisyaUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH));
	}
	
	
	// syorisya_user_naに対するセッターとゲッターの集合
	public boolean setSyorisyaUserNa(String syorisya_user_na)
	{
		this.syorisya_user_na = syorisya_user_na;
		return true;
	}
	public String getSyorisyaUserNa()
	{
		return cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH);
	}
	public String getSyorisyaUserNaString()
	{
		return "'" + cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH) + "'";
	}
	public String getSyorisyaUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH));
	}
	
	
	// gyosyu_cdに対するセッターとゲッターの集合
	public boolean setGyosyuCd(String gyosyu_cd)
	{
		this.gyosyu_cd = gyosyu_cd;
		return true;
	}
	public String getGyosyuCd()
	{
		return cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH);
	}
	public String getGyosyuCdString()
	{
		return "'" + cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH));
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
		return "  syukeihinsyu_cd = " + getSyukeiHinsyuCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  kana_na = " + getKanaNaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  syukeihinsyu_na = " + getSyukeiHinsyuNaString()
			+ "  syukeihinsyu_ka = " + getSyukeiHinsyuKaString()
			+ "  syukeisyubetu_cd = " + getSyukeiSyubetuCdString()
			+ "  syorisya_user_id = " + getSyorisyaUserIdString()
			+ "  syorisya_user_na = " + getSyorisyaUserNaString()
			+ "  gyosyu_cd = " + getGyosyuCdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst640101_SyuukeiSyubetuInfoBean コピー後のクラス
	 */
	public mst640101_SyuukeiSyubetuInfoBean createClone()
	{
		mst640101_SyuukeiSyubetuInfoBean bean = new mst640101_SyuukeiSyubetuInfoBean();
		bean.setSyukeiHinsyuCd(this.syukeihinsyu_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setKanaNa(this.kana_na);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setSyukeiHinsyuNa(this.syukeihinsyu_na);
		bean.setSyukeiHinsyuKa(this.syukeihinsyu_ka);
		bean.setSyukeiSyubetuCd(this.syukeisyubetu_cd);
		bean.setSyorisyaUserId(this.syorisya_user_id);
		bean.setSyorisyaUserNa(this.syorisya_user_na);
		bean.setGyosyuCd(this.gyosyu_cd);
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
		if( !( o instanceof mst640101_SyuukeiSyubetuInfoBean ) ) return false;
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
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
