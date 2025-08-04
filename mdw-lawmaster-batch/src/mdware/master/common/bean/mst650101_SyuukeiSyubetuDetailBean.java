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
public class mst650101_SyuukeiSyubetuDetailBean
{
	private static final StcLog stcLog = StcLog.getInstance();


	public static final int SYUKEIHINSYU_HINSYU_SYUKEI_HINSYU_CD_MAX_LENGTH = 4;
	public static final int SYUKEIHINSYU_HINSYU_HINSYU_CD_MAX_LENGTH = 4;
	public static final int SYUKEIHINSYU_HINSYU_INSERT_TS_MAX_LENGTH = 20;
	public static final int SYUKEIHINSYU_HINSYU_INSERT_USER_ID_MAX_LENGTH = 10;
	public static final int SYUKEIHINSYU_HINSYU_UPDATE_TS_MAX_LENGTH = 20;
	public static final int SYUKEIHINSYU_HINSYU_UPDATE_USER_ID_MAX_LENGTH = 10;
	public static final int SYUKEIHINSYU_HINSYU_DELETE_FG_MAX_LENGTH = 1;
	public static final int SYOHIN_TAIKEI_HINSYU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_HINGUN_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_HANKU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_C_SYUKEI_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_URIKU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_S_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_D_GYOSYU_CD_MAX_LENGTH = 4;
	public static final int SYOHIN_TAIKEI_INSERT_USER_ID_MAX_LENGTH = 10;
	public static final int SYOHIN_TAIKEI_INSERT_TS_MAX_LENGTH = 20;
	public static final int SYOHIN_TAIKEI_UPDATE_USER_ID_MAX_LENGTH = 10;
	public static final int SYOHIN_TAIKEI_UPDATE_TS_MAX_LENGTH = 20;
	public static final int HANKU_SYUBETU_NO_CD_MAX_LENGTH = 4;
	public static final int HANKU_CODE_CD_MAX_LENGTH = 10;
	public static final int HANKU_KANJI_NA_MAX_LENGTH = 80;
	public static final int HANKU_KANA_NA_MAX_LENGTH = 80;
	public static final int HANKU_KANJI_RN_MAX_LENGTH = 20;
	public static final int HANKU_KANA_RN_MAX_LENGTH = 20;
	public static final int HANKU_ZOKUSEI_CD_MAX_LENGTH = 30;
	public static final int HANKU_INSERT_TS_MAX_LENGTH = 20;
	public static final int HANKU_INSERT_USER_ID_MAX_LENGTH = 10;
	public static final int HANKU_UPDATE_TS_MAX_LENGTH = 20;
	public static final int HANKU_UPDATE_USER_ID_MAX_LENGTH = 10;
	public static final int HANKU_DELETE_FG_MAX_LENGTH = 1;
	public static final int HINSYU_SYUBETU_NO_CD_MAX_LENGTH = 0;
	public static final int HINSYU_CODE_CD_MAX_LENGTH = 4;
	public static final int HINSYU_KANJI_NA_MAX_LENGTH = 80;
	public static final int HINSYU_KANA_NA_MAX_LENGTH = 80;
	public static final int HINSYU_KANJI_RN_MAX_LENGTH = 20;
	public static final int HINSYU_KANA_RN_MAX_LENGTH = 20;
	public static final int HINSYU_ZOKUSEI_CD_MAX_LENGTH = 30;
	public static final int HINSYU_INSERT_TS_MAX_LENGTH = 20;
	public static final int HINSYU_INSERT_USER_ID_MAX_LENGTH = 10;
	public static final int HINSYU_UPDATE_TS_MAX_LENGTH = 20;
	public static final int HINSYU_UPDATE_USER_ID_MAX_LENGTH = 10;
	public static final int HINSYU_DELETE_FG_MAX_LENGTH = 1;
	/** ユーザ氏名(insert)データ長 */
	public static final int USER_NA_INSERT_MAX_LENGTH = 20;
	/** ユーザ氏名(update)データ長 */
	public static final int USER_NA_UPDATE_MAX_LENGTH = 20;
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択の長さ
	
	public static final int SYUKEIHINSYU_CD_MAX_LENGTH = 6;
	public static final int HINSYU_CD_MAX_LENGTH = 4;
	public static final int HINSYU_NA_MAX_LENGTH = 80;
	public static final int SYUKEI_KB_CD_MAX_LENGTH = 6;
	public static final int SYUKEI_KB_NA_MAX_LENGTH = 80;
	public static final int SYUKEI_KB_A_CD_MAX_LENGTH = 2;
	public static final int SYUKEI_KB_B_CD_MAX_LENGTH = 2;
	public static final int SYUKEI_KB_C_CD_MAX_LENGTH = 2;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int SYORISYA_USER_ID_MAX_LENGTH = 10;
	public static final int SYORISYA_USER_NA_MAX_LENGTH = 20;
	

	private String syukeihinsyu_hinsyu_syukei_hinsyu_cd = null;
	private String syukeihinsyu_hinsyu_hinsyu_cd = null;
	private String syukeihinsyu_hinsyu_insert_ts = null;
	private String syukeihinsyu_hinsyu_insert_user_id = null;
	private String syukeihinsyu_hinsyu_update_ts = null;
	private String syukeihinsyu_hinsyu_update_user_id = null;
	private String syukeihinsyu_hinsyu_delete_fg = null;
	private String syohin_taikei_hinsyu_cd = null;
	private String syohin_taikei_hingun_cd = null;
	private String syohin_taikei_hanku_cd = null;
	private String syohin_taikei_c_syukei_cd = null;
	private String syohin_taikei_uriku_cd = null;
	private String syohin_taikei_s_gyosyu_cd = null;
	private String syohin_taikei_d_gyosyu_cd = null;
	private String syohin_taikei_insert_user_id = null;
	private String syohin_taikei_insert_ts = null;
	private String syohin_taikei_update_user_id = null;
	private String syohin_taikei_update_ts = null;
	private String hanku_syubetu_no_cd = null;
	private String hanku_code_cd = null;
	private String hanku_kanji_na = null;
	private String hanku_kana_na = null;
	private String hanku_kanji_rn = null;
	private String hanku_kana_rn = null;
	private String hanku_zokusei_cd = null;
	private String hanku_insert_ts = null;
	private String hanku_insert_user_id = null;
	private String hanku_update_ts = null;
	private String hanku_update_user_id = null;
	private String hanku_delete_fg = null;
	private String hinsyu_syubetu_no_cd = null;
	private String hinsyu_code_cd = null;
	private String hinsyu_kanji_na = null;
	private String hinsyu_kana_na = null;
	private String hinsyu_kanji_rn = null;
	private String hinsyu_kana_rn = null;
	private String hinsyu_zokusei_cd = null;
	private String hinsyu_insert_ts = null;
	private String hinsyu_insert_user_id = null;
	private String hinsyu_update_ts = null;
	private String hinsyu_update_user_id = null;
	private String hinsyu_delete_fg = null;
	private String user_na_insert = null;
	private String user_na_update = null;
	private String sentaku = null;					//処理選択
	
	private String syukeihinsyu_cd = null;
	private String hinsyu_cd = null;
	private String hinsyu_na = null;
	private String syukei_kb_cd = null;
	private String syukei_kb_na = null;
	private String syukei_kb_a_cd = null;
	private String syukei_kb_b_cd = null;
	private String syukei_kb_c_cd = null;
	private String insert_ts = null;
	private String update_ts = null;
	private String syorisya_user_id = null;
	private String syorisya_user_na = null;
	

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
	 * mst650101_SyuukeiSyubetuDetailBeanを１件のみ抽出したい時に使用する
	 */
	public static mst650101_SyuukeiSyubetuDetailBean getmst650101_SyuukeiSyubetuDetailBean(DataHolder dataHolder)
	{
		mst650101_SyuukeiSyubetuDetailBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst650101_SyuukeiSyubetuDetailDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst650101_SyuukeiSyubetuDetailBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// syukeihinsyu_hinsyu_syukei_hinsyu_cdに対するセッターとゲッターの集合
	public boolean setSyukeihinsyuHinsyuSyukeiHinsyuCd(String syukeihinsyu_hinsyu_syukei_hinsyu_cd)
	{
		this.syukeihinsyu_hinsyu_syukei_hinsyu_cd = syukeihinsyu_hinsyu_syukei_hinsyu_cd;
		return true;
	}
	public String getSyukeihinsyuHinsyuSyukeiHinsyuCd()
	{
		return cutString(this.syukeihinsyu_hinsyu_syukei_hinsyu_cd,SYUKEIHINSYU_HINSYU_SYUKEI_HINSYU_CD_MAX_LENGTH);
	}
	public String getSyukeihinsyuHinsyuSyukeiHinsyuCdString()
	{
		return "'" + cutString(this.syukeihinsyu_hinsyu_syukei_hinsyu_cd,SYUKEIHINSYU_HINSYU_SYUKEI_HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeihinsyuHinsyuSyukeiHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_syukei_hinsyu_cd,SYUKEIHINSYU_HINSYU_SYUKEI_HINSYU_CD_MAX_LENGTH));
	}


	// syukeihinsyu_hinsyu_hinsyu_cdに対するセッターとゲッターの集合
	public boolean setSyukeihinsyuHinsyuHinsyuCd(String syukeihinsyu_hinsyu_hinsyu_cd)
	{
		this.syukeihinsyu_hinsyu_hinsyu_cd = syukeihinsyu_hinsyu_hinsyu_cd;
		return true;
	}
	public String getSyukeihinsyuHinsyuHinsyuCd()
	{
		return cutString(this.syukeihinsyu_hinsyu_hinsyu_cd,SYUKEIHINSYU_HINSYU_HINSYU_CD_MAX_LENGTH);
	}
	public String getSyukeihinsyuHinsyuHinsyuCdString()
	{
		return "'" + cutString(this.syukeihinsyu_hinsyu_hinsyu_cd,SYUKEIHINSYU_HINSYU_HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeihinsyuHinsyuHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_hinsyu_cd,SYUKEIHINSYU_HINSYU_HINSYU_CD_MAX_LENGTH));
	}

	// syukeihinsyu_hinsyu_insert_tsに対するセッターとゲッターの集合
//	public boolean setInsertTs(String syukeihinsyu_hinsyu_insert_ts)
//	{
//		this.syukeihinsyu_hinsyu_insert_ts = syukeihinsyu_hinsyu_insert_ts;
//		return true;
//	}
//	public String getInsertTs()
//	{
//		return cutString(this.syukeihinsyu_hinsyu_insert_ts,SYUKEIHINSYU_HINSYU_INSERT_TS_MAX_LENGTH);
//	}
//	public String getInsertTsString()
//	{
//		return "'" + cutString(this.syukeihinsyu_hinsyu_insert_ts,SYUKEIHINSYU_HINSYU_INSERT_TS_MAX_LENGTH) + "'";
//	}
//	public String getInsertTsHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_insert_ts,SYUKEIHINSYU_HINSYU_INSERT_TS_MAX_LENGTH));
//	}


	// syukeihinsyu_hinsyu_insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String syukeihinsyu_hinsyu_insert_user_id)
	{
		this.syukeihinsyu_hinsyu_insert_user_id = syukeihinsyu_hinsyu_insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.syukeihinsyu_hinsyu_insert_user_id,SYUKEIHINSYU_HINSYU_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.syukeihinsyu_hinsyu_insert_user_id,SYUKEIHINSYU_HINSYU_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_insert_user_id,SYUKEIHINSYU_HINSYU_INSERT_USER_ID_MAX_LENGTH));
	}


	// syukeihinsyu_hinsyu_update_tsに対するセッターとゲッターの集合
//	public boolean setUpdateTs(String syukeihinsyu_hinsyu_update_ts)
//	{
//		this.syukeihinsyu_hinsyu_update_ts = syukeihinsyu_hinsyu_update_ts;
//		return true;
//	}
//	public String getUpdateTs()
//	{
//		return cutString(this.syukeihinsyu_hinsyu_update_ts,SYUKEIHINSYU_HINSYU_UPDATE_TS_MAX_LENGTH);
//	}
//	public String getUpdateTsString()
//	{
//		return "'" + cutString(this.syukeihinsyu_hinsyu_update_ts,SYUKEIHINSYU_HINSYU_UPDATE_TS_MAX_LENGTH) + "'";
//	}
//	public String getUpdateTsHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_update_ts,SYUKEIHINSYU_HINSYU_UPDATE_TS_MAX_LENGTH));
//	}


	// syukeihinsyu_hinsyu_update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String syukeihinsyu_hinsyu_update_user_id)
	{
		this.syukeihinsyu_hinsyu_update_user_id = syukeihinsyu_hinsyu_update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return cutString(this.syukeihinsyu_hinsyu_update_user_id,SYUKEIHINSYU_HINSYU_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.syukeihinsyu_hinsyu_update_user_id,SYUKEIHINSYU_HINSYU_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_update_user_id,SYUKEIHINSYU_HINSYU_UPDATE_USER_ID_MAX_LENGTH));
	}


	// syukeihinsyu_hinsyu_delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String syukeihinsyu_hinsyu_delete_fg)
	{
		this.syukeihinsyu_hinsyu_delete_fg = syukeihinsyu_hinsyu_delete_fg;
		return true;
	}
	public String getDeleteFg()
	{
		return cutString(this.syukeihinsyu_hinsyu_delete_fg,SYUKEIHINSYU_HINSYU_DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.syukeihinsyu_hinsyu_delete_fg,SYUKEIHINSYU_HINSYU_DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukeihinsyu_hinsyu_delete_fg,SYUKEIHINSYU_HINSYU_DELETE_FG_MAX_LENGTH));
	}


	// syohin_taikei_hinsyu_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiHinsyuCd(String syohin_taikei_hinsyu_cd)
	{
		this.syohin_taikei_hinsyu_cd = syohin_taikei_hinsyu_cd;
		return true;
	}
	public String getSyohinTaikeiHinsyuCd()
	{
		return cutString(this.syohin_taikei_hinsyu_cd,SYOHIN_TAIKEI_HINSYU_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiHinsyuCdString()
	{
		return "'" + cutString(this.syohin_taikei_hinsyu_cd,SYOHIN_TAIKEI_HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_hinsyu_cd,SYOHIN_TAIKEI_HINSYU_CD_MAX_LENGTH));
	}


	// syohin_taikei_hingun_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiHingunCd(String syohin_taikei_hingun_cd)
	{
		this.syohin_taikei_hingun_cd = syohin_taikei_hingun_cd;
		return true;
	}
	public String getSyohinTaikeiHingunCd()
	{
		return cutString(this.syohin_taikei_hingun_cd,SYOHIN_TAIKEI_HINGUN_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiHingunCdString()
	{
		return "'" + cutString(this.syohin_taikei_hingun_cd,SYOHIN_TAIKEI_HINGUN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiHingunCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_hingun_cd,SYOHIN_TAIKEI_HINGUN_CD_MAX_LENGTH));
	}


	// syohin_taikei_hanku_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiHankuCd(String syohin_taikei_hanku_cd)
	{
		this.syohin_taikei_hanku_cd = syohin_taikei_hanku_cd;
		return true;
	}
	public String getSyohinTaikeiHankuCd()
	{
		return cutString(this.syohin_taikei_hanku_cd,SYOHIN_TAIKEI_HANKU_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiHankuCdString()
	{
		return "'" + cutString(this.syohin_taikei_hanku_cd,SYOHIN_TAIKEI_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_hanku_cd,SYOHIN_TAIKEI_HANKU_CD_MAX_LENGTH));
	}


	// syohin_taikei_c_syukei_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiCSyukeiCd(String syohin_taikei_c_syukei_cd)
	{
		this.syohin_taikei_c_syukei_cd = syohin_taikei_c_syukei_cd;
		return true;
	}
	public String getSyohinTaikeiCSyukeiCd()
	{
		return cutString(this.syohin_taikei_c_syukei_cd,SYOHIN_TAIKEI_C_SYUKEI_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiCSyukeiCdString()
	{
		return "'" + cutString(this.syohin_taikei_c_syukei_cd,SYOHIN_TAIKEI_C_SYUKEI_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiCSyukeiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_c_syukei_cd,SYOHIN_TAIKEI_C_SYUKEI_CD_MAX_LENGTH));
	}


	// syohin_taikei_uriku_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiUrikuCd(String syohin_taikei_uriku_cd)
	{
		this.syohin_taikei_uriku_cd = syohin_taikei_uriku_cd;
		return true;
	}
	public String getSyohinTaikeiUrikuCd()
	{
		return cutString(this.syohin_taikei_uriku_cd,SYOHIN_TAIKEI_URIKU_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiUrikuCdString()
	{
		return "'" + cutString(this.syohin_taikei_uriku_cd,SYOHIN_TAIKEI_URIKU_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiUrikuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_uriku_cd,SYOHIN_TAIKEI_URIKU_CD_MAX_LENGTH));
	}


	// syohin_taikei_s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiSGyosyuCd(String syohin_taikei_s_gyosyu_cd)
	{
		this.syohin_taikei_s_gyosyu_cd = syohin_taikei_s_gyosyu_cd;
		return true;
	}
	public String getSyohinTaikeiSGyosyuCd()
	{
		return cutString(this.syohin_taikei_s_gyosyu_cd,SYOHIN_TAIKEI_S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiSGyosyuCdString()
	{
		return "'" + cutString(this.syohin_taikei_s_gyosyu_cd,SYOHIN_TAIKEI_S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_s_gyosyu_cd,SYOHIN_TAIKEI_S_GYOSYU_CD_MAX_LENGTH));
	}


	// syohin_taikei_d_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiDGyosyuCd(String syohin_taikei_d_gyosyu_cd)
	{
		this.syohin_taikei_d_gyosyu_cd = syohin_taikei_d_gyosyu_cd;
		return true;
	}
	public String getSyohinTaikeiDGyosyuCd()
	{
		return cutString(this.syohin_taikei_d_gyosyu_cd,SYOHIN_TAIKEI_D_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSyohinTaikeiDGyosyuCdString()
	{
		return "'" + cutString(this.syohin_taikei_d_gyosyu_cd,SYOHIN_TAIKEI_D_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiDGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_d_gyosyu_cd,SYOHIN_TAIKEI_D_GYOSYU_CD_MAX_LENGTH));
	}


	// syohin_taikei_insert_user_idに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiInsertUserId(String syohin_taikei_insert_user_id)
	{
		this.syohin_taikei_insert_user_id = syohin_taikei_insert_user_id;
		return true;
	}
	public String getSyohinTaikeiInsertUserId()
	{
		return cutString(this.syohin_taikei_insert_user_id,SYOHIN_TAIKEI_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getSyohinTaikeiInsertUserIdString()
	{
		return "'" + cutString(this.syohin_taikei_insert_user_id,SYOHIN_TAIKEI_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_insert_user_id,SYOHIN_TAIKEI_INSERT_USER_ID_MAX_LENGTH));
	}


	// syohin_taikei_insert_tsに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiInsertTs(String syohin_taikei_insert_ts)
	{
		this.syohin_taikei_insert_ts = syohin_taikei_insert_ts;
		return true;
	}
	public String getSyohinTaikeiInsertTs()
	{
		return cutString(this.syohin_taikei_insert_ts,SYOHIN_TAIKEI_INSERT_TS_MAX_LENGTH);
	}
	public String getSyohinTaikeiInsertTsString()
	{
		return "'" + cutString(this.syohin_taikei_insert_ts,SYOHIN_TAIKEI_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_insert_ts,SYOHIN_TAIKEI_INSERT_TS_MAX_LENGTH));
	}


	// syohin_taikei_update_user_idに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiUpdateUserId(String syohin_taikei_update_user_id)
	{
		this.syohin_taikei_update_user_id = syohin_taikei_update_user_id;
		return true;
	}
	public String getSyohinTaikeiUpdateUserId()
	{
		return cutString(this.syohin_taikei_update_user_id,SYOHIN_TAIKEI_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getSyohinTaikeiUpdateUserIdString()
	{
		return "'" + cutString(this.syohin_taikei_update_user_id,SYOHIN_TAIKEI_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_update_user_id,SYOHIN_TAIKEI_UPDATE_USER_ID_MAX_LENGTH));
	}


	// syohin_taikei_update_tsに対するセッターとゲッターの集合
	public boolean setSyohinTaikeiUpdateTs(String syohin_taikei_update_ts)
	{
		this.syohin_taikei_update_ts = syohin_taikei_update_ts;
		return true;
	}
	public String getSyohinTaikeiUpdateTs()
	{
		return cutString(this.syohin_taikei_update_ts,SYOHIN_TAIKEI_UPDATE_TS_MAX_LENGTH);
	}
	public String getSyohinTaikeiUpdateTsString()
	{
		return "'" + cutString(this.syohin_taikei_update_ts,SYOHIN_TAIKEI_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getSyohinTaikeiUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_taikei_update_ts,SYOHIN_TAIKEI_UPDATE_TS_MAX_LENGTH));
	}


	// hanku_syubetu_no_cdに対するセッターとゲッターの集合
	public boolean setHankuSyubetuNoCd(String hanku_syubetu_no_cd)
	{
		this.hanku_syubetu_no_cd = hanku_syubetu_no_cd;
		return true;
	}
	public String getHankuSyubetuNoCd()
	{
		return cutString(this.hanku_syubetu_no_cd,HANKU_SYUBETU_NO_CD_MAX_LENGTH);
	}
	public String getHankuSyubetuNoCdString()
	{
		return "'" + cutString(this.hanku_syubetu_no_cd,HANKU_SYUBETU_NO_CD_MAX_LENGTH) + "'";
	}
	public String getHankuSyubetuNoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_syubetu_no_cd,HANKU_SYUBETU_NO_CD_MAX_LENGTH));
	}


	// hanku_code_cdに対するセッターとゲッターの集合
	public boolean setHankuCodeCd(String hanku_code_cd)
	{
		this.hanku_code_cd = hanku_code_cd;
		return true;
	}
	public String getHankuCodeCd()
	{
		return cutString(this.hanku_code_cd,HANKU_CODE_CD_MAX_LENGTH);
	}
	public String getHankuCodeCdString()
	{
		return "'" + cutString(this.hanku_code_cd,HANKU_CODE_CD_MAX_LENGTH) + "'";
	}
	public String getHankuCodeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_code_cd,HANKU_CODE_CD_MAX_LENGTH));
	}


	// hanku_kanji_naに対するセッターとゲッターの集合
	public boolean setHankuKanjiNa(String hanku_kanji_na)
	{
		this.hanku_kanji_na = hanku_kanji_na;
		return true;
	}
	public String getHankuKanjiNa()
	{
		return cutString(this.hanku_kanji_na,HANKU_KANJI_NA_MAX_LENGTH);
	}
	public String getHankuKanjiNaString()
	{
		return "'" + cutString(this.hanku_kanji_na,HANKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_kanji_na,HANKU_KANJI_NA_MAX_LENGTH));
	}


	// hanku_kana_naに対するセッターとゲッターの集合
	public boolean setHankuKanaNa(String hanku_kana_na)
	{
		this.hanku_kana_na = hanku_kana_na;
		return true;
	}
	public String getHankuKanaNa()
	{
		return cutString(this.hanku_kana_na,HANKU_KANA_NA_MAX_LENGTH);
	}
	public String getHankuKanaNaString()
	{
		return "'" + cutString(this.hanku_kana_na,HANKU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getHankuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_kana_na,HANKU_KANA_NA_MAX_LENGTH));
	}


	// hanku_kanji_rnに対するセッターとゲッターの集合
	public boolean setHankuKanjiRn(String hanku_kanji_rn)
	{
		this.hanku_kanji_rn = hanku_kanji_rn;
		return true;
	}
	public String getHankuKanjiRn()
	{
		return cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRnString()
	{
		return "'" + cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH));
	}


	// hanku_kana_rnに対するセッターとゲッターの集合
	public boolean setHankuKanaRn(String hanku_kana_rn)
	{
		this.hanku_kana_rn = hanku_kana_rn;
		return true;
	}
	public String getHankuKanaRn()
	{
		return cutString(this.hanku_kana_rn,HANKU_KANA_RN_MAX_LENGTH);
	}
	public String getHankuKanaRnString()
	{
		return "'" + cutString(this.hanku_kana_rn,HANKU_KANA_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanaRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_kana_rn,HANKU_KANA_RN_MAX_LENGTH));
	}


	// hanku_zokusei_cdに対するセッターとゲッターの集合
	public boolean setHankuZokuseiCd(String hanku_zokusei_cd)
	{
		this.hanku_zokusei_cd = hanku_zokusei_cd;
		return true;
	}
	public String getHankuZokuseiCd()
	{
		return cutString(this.hanku_zokusei_cd,HANKU_ZOKUSEI_CD_MAX_LENGTH);
	}
	public String getHankuZokuseiCdString()
	{
		return "'" + cutString(this.hanku_zokusei_cd,HANKU_ZOKUSEI_CD_MAX_LENGTH) + "'";
	}
	public String getHankuZokuseiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_zokusei_cd,HANKU_ZOKUSEI_CD_MAX_LENGTH));
	}


	// hanku_insert_tsに対するセッターとゲッターの集合
	public boolean setHankuInsertTs(String hanku_insert_ts)
	{
		this.hanku_insert_ts = hanku_insert_ts;
		return true;
	}
	public String getHankuInsertTs()
	{
		return cutString(this.hanku_insert_ts,HANKU_INSERT_TS_MAX_LENGTH);
	}
	public String getHankuInsertTsString()
	{
		return "'" + cutString(this.hanku_insert_ts,HANKU_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getHankuInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_insert_ts,HANKU_INSERT_TS_MAX_LENGTH));
	}


	// hanku_insert_user_idに対するセッターとゲッターの集合
	public boolean setHankuInsertUserId(String hanku_insert_user_id)
	{
		this.hanku_insert_user_id = hanku_insert_user_id;
		return true;
	}
	public String getHankuInsertUserId()
	{
		return cutString(this.hanku_insert_user_id,HANKU_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getHankuInsertUserIdString()
	{
		return "'" + cutString(this.hanku_insert_user_id,HANKU_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getHankuInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_insert_user_id,HANKU_INSERT_USER_ID_MAX_LENGTH));
	}


	// hanku_update_tsに対するセッターとゲッターの集合
	public boolean setHankuUpdateTs(String hanku_update_ts)
	{
		this.hanku_update_ts = hanku_update_ts;
		return true;
	}
	public String getHankuUpdateTs()
	{
		return cutString(this.hanku_update_ts,HANKU_UPDATE_TS_MAX_LENGTH);
	}
	public String getHankuUpdateTsString()
	{
		return "'" + cutString(this.hanku_update_ts,HANKU_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getHankuUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_update_ts,HANKU_UPDATE_TS_MAX_LENGTH));
	}


	// hanku_update_user_idに対するセッターとゲッターの集合
	public boolean setHankuUpdateUserId(String hanku_update_user_id)
	{
		this.hanku_update_user_id = hanku_update_user_id;
		return true;
	}
	public String getHankuUpdateUserId()
	{
		return cutString(this.hanku_update_user_id,HANKU_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getHankuUpdateUserIdString()
	{
		return "'" + cutString(this.hanku_update_user_id,HANKU_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getHankuUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_update_user_id,HANKU_UPDATE_USER_ID_MAX_LENGTH));
	}


	// hanku_delete_fgに対するセッターとゲッターの集合
	public boolean setHankuDeleteFg(String hanku_delete_fg)
	{
		this.hanku_delete_fg = hanku_delete_fg;
		return true;
	}
	public String getHankuDeleteFg()
	{
		return cutString(this.hanku_delete_fg,HANKU_DELETE_FG_MAX_LENGTH);
	}
	public String getHankuDeleteFgString()
	{
		return "'" + cutString(this.hanku_delete_fg,HANKU_DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getHankuDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_delete_fg,HANKU_DELETE_FG_MAX_LENGTH));
	}


	// hinsyu_syubetu_no_cdに対するセッターとゲッターの集合
	public boolean setHinsyuSyubetuNoCd(String hinsyu_syubetu_no_cd)
	{
		this.hinsyu_syubetu_no_cd = hinsyu_syubetu_no_cd;
		return true;
	}
	public String getHinsyuSyubetuNoCd()
	{
		return cutString(this.hinsyu_syubetu_no_cd,HINSYU_SYUBETU_NO_CD_MAX_LENGTH);
	}
	public String getHinsyuSyubetuNoCdString()
	{
		return "'" + cutString(this.hinsyu_syubetu_no_cd,HINSYU_SYUBETU_NO_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuSyubetuNoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_syubetu_no_cd,HINSYU_SYUBETU_NO_CD_MAX_LENGTH));
	}


	// hinsyu_code_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCodeCd(String hinsyu_code_cd)
	{
		this.hinsyu_code_cd = hinsyu_code_cd;
		return true;
	}
	public String getHinsyuCodeCd()
	{
		return cutString(this.hinsyu_code_cd,HINSYU_CODE_CD_MAX_LENGTH);
	}
	public String getHinsyuCodeCdString()
	{
		return "'" + cutString(this.hinsyu_code_cd,HINSYU_CODE_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCodeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_code_cd,HINSYU_CODE_CD_MAX_LENGTH));
	}


	// hinsyu_kanji_naに対するセッターとゲッターの集合
	public boolean setHinsyuKanjiNa(String hinsyu_kanji_na)
	{
		this.hinsyu_kanji_na = hinsyu_kanji_na;
		return true;
	}
	public String getHinsyuKanjiNa()
	{
		return cutString(this.hinsyu_kanji_na,HINSYU_KANJI_NA_MAX_LENGTH);
	}
	public String getHinsyuKanjiNaString()
	{
		return "'" + cutString(this.hinsyu_kanji_na,HINSYU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_kanji_na,HINSYU_KANJI_NA_MAX_LENGTH));
	}


	// hinsyu_kana_naに対するセッターとゲッターの集合
	public boolean setHinsyuKanaNa(String hinsyu_kana_na)
	{
		this.hinsyu_kana_na = hinsyu_kana_na;
		return true;
	}
	public String getHinsyuKanaNa()
	{
		return cutString(this.hinsyu_kana_na,HINSYU_KANA_NA_MAX_LENGTH);
	}
	public String getHinsyuKanaNaString()
	{
		return "'" + cutString(this.hinsyu_kana_na,HINSYU_KANA_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuKanaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_kana_na,HINSYU_KANA_NA_MAX_LENGTH));
	}


	// hinsyu_kanji_rnに対するセッターとゲッターの集合
	public boolean setHinsyuKanjiRn(String hinsyu_kanji_rn)
	{
		this.hinsyu_kanji_rn = hinsyu_kanji_rn;
		return true;
	}
	public String getHinsyuKanjiRn()
	{
		return cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH);
	}
	public String getHinsyuKanjiRnString()
	{
		return "'" + cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinsyuKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH));
	}


	// hinsyu_kana_rnに対するセッターとゲッターの集合
	public boolean setHinsyuKanaRn(String hinsyu_kana_rn)
	{
		this.hinsyu_kana_rn = hinsyu_kana_rn;
		return true;
	}
	public String getHinsyuKanaRn()
	{
		return cutString(this.hinsyu_kana_rn,HINSYU_KANA_RN_MAX_LENGTH);
	}
	public String getHinsyuKanaRnString()
	{
		return "'" + cutString(this.hinsyu_kana_rn,HINSYU_KANA_RN_MAX_LENGTH) + "'";
	}
	public String getHinsyuKanaRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_kana_rn,HINSYU_KANA_RN_MAX_LENGTH));
	}


	// hinsyu_zokusei_cdに対するセッターとゲッターの集合
	public boolean setHinsyuZokuseiCd(String hinsyu_zokusei_cd)
	{
		this.hinsyu_zokusei_cd = hinsyu_zokusei_cd;
		return true;
	}
	public String getHinsyuZokuseiCd()
	{
		return cutString(this.hinsyu_zokusei_cd,HINSYU_ZOKUSEI_CD_MAX_LENGTH);
	}
	public String getHinsyuZokuseiCdString()
	{
		return "'" + cutString(this.hinsyu_zokusei_cd,HINSYU_ZOKUSEI_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuZokuseiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_zokusei_cd,HINSYU_ZOKUSEI_CD_MAX_LENGTH));
	}


	// hinsyu_insert_tsに対するセッターとゲッターの集合
	public boolean setHinsyuInsertTs(String hinsyu_insert_ts)
	{
		this.hinsyu_insert_ts = hinsyu_insert_ts;
		return true;
	}
	public String getHinsyuInsertTs()
	{
		return cutString(this.hinsyu_insert_ts,HINSYU_INSERT_TS_MAX_LENGTH);
	}
	public String getHinsyuInsertTsString()
	{
		return "'" + cutString(this.hinsyu_insert_ts,HINSYU_INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getHinsyuInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_insert_ts,HINSYU_INSERT_TS_MAX_LENGTH));
	}


	// hinsyu_insert_user_idに対するセッターとゲッターの集合
	public boolean setHinsyuInsertUserId(String hinsyu_insert_user_id)
	{
		this.hinsyu_insert_user_id = hinsyu_insert_user_id;
		return true;
	}
	public String getHinsyuInsertUserId()
	{
		return cutString(this.hinsyu_insert_user_id,HINSYU_INSERT_USER_ID_MAX_LENGTH);
	}
	public String getHinsyuInsertUserIdString()
	{
		return "'" + cutString(this.hinsyu_insert_user_id,HINSYU_INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getHinsyuInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_insert_user_id,HINSYU_INSERT_USER_ID_MAX_LENGTH));
	}


	// hinsyu_update_tsに対するセッターとゲッターの集合
	public boolean setHinsyuUpdateTs(String hinsyu_update_ts)
	{
		this.hinsyu_update_ts = hinsyu_update_ts;
		return true;
	}
	public String getHinsyuUpdateTs()
	{
		return cutString(this.hinsyu_update_ts,HINSYU_UPDATE_TS_MAX_LENGTH);
	}
	public String getHinsyuUpdateTsString()
	{
		return "'" + cutString(this.hinsyu_update_ts,HINSYU_UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getHinsyuUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_update_ts,HINSYU_UPDATE_TS_MAX_LENGTH));
	}


	// hinsyu_update_user_idに対するセッターとゲッターの集合
	public boolean setHinsyuUpdateUserId(String hinsyu_update_user_id)
	{
		this.hinsyu_update_user_id = hinsyu_update_user_id;
		return true;
	}
	public String getHinsyuUpdateUserId()
	{
		return cutString(this.hinsyu_update_user_id,HINSYU_UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getHinsyuUpdateUserIdString()
	{
		return "'" + cutString(this.hinsyu_update_user_id,HINSYU_UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getHinsyuUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_update_user_id,HINSYU_UPDATE_USER_ID_MAX_LENGTH));
	}


	// hinsyu_delete_fgに対するセッターとゲッターの集合
	public boolean setHinsyuDeleteFg(String hinsyu_delete_fg)
	{
		this.hinsyu_delete_fg = hinsyu_delete_fg;
		return true;
	}
	public String getHinsyuDeleteFg()
	{
		return cutString(this.hinsyu_delete_fg,HINSYU_DELETE_FG_MAX_LENGTH);
	}
	public String getHinsyuDeleteFgString()
	{
		return "'" + cutString(this.hinsyu_delete_fg,HINSYU_DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getHinsyuDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_delete_fg,HINSYU_DELETE_FG_MAX_LENGTH));
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
	
	
	// syukeihinsyu_cdに対するセッターとゲッターの集合
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
	
	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
	}
	
	// hinsyu_naに対するセッターとゲッターの集合
	public boolean setHinsyuNa(String hinsyu_na)
	{
		this.hinsyu_na = hinsyu_na;
		return true;
	}
	public String getHinsyuNa()
	{
		return cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH);
	}
	public String getHinsyuNaString()
	{
		return "'" + cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH));
	}
	
	// syukei_kb_cdに対するセッターとゲッターの集合
	public boolean setSyukeiKbCd(String syukei_kb_cd)
	{
		this.syukei_kb_cd = syukei_kb_cd;
		return true;
	}
	public String getSyukeiKbCd()
	{
		return cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH);
	}
	public String getSyukeiKbCdString()
	{
		return "'" + cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiKbCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH));
	}
	
	// syukei_kb_naに対するセッターとゲッターの集合
	public boolean setSyukeiKbNa(String syukei_kb_na)
	{
		this.syukei_kb_na = syukei_kb_na;
		return true;
	}
	public String getSyukeiKbNa()
	{
		return cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH);
	}
	public String getSyukeiKbNaString()
	{
		return "'" + cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH) + "'";
	}
	public String getSyukeiKbNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH));
	}
	
	// syukei_kb_a_cdに対するセッターとゲッターの集合
	public boolean setSyukeiKbACd(String syukei_kb_a_cd)
	{
		this.syukei_kb_a_cd = syukei_kb_a_cd;
		return true;
	}
	public String getSyukeiKbACd()
	{
		return cutString(this.syukei_kb_a_cd,SYUKEI_KB_A_CD_MAX_LENGTH);
	}
	public String getSyukeiKbACdString()
	{
		return "'" + cutString(this.syukei_kb_a_cd,SYUKEI_KB_A_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiKbACdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_a_cd,SYUKEI_KB_A_CD_MAX_LENGTH));
	}
	
	// syukei_kb_b_cdに対するセッターとゲッターの集合
	public boolean setSyukeiKbBCd(String syukei_kb_b_cd)
	{
		this.syukei_kb_b_cd = syukei_kb_b_cd;
		return true;
	}
	public String getSyukeiKbBCd()
	{
		return cutString(this.syukei_kb_b_cd,SYUKEI_KB_B_CD_MAX_LENGTH);
	}
	public String getSyukeiKbBCdString()
	{
		return "'" + cutString(this.syukei_kb_b_cd,SYUKEI_KB_B_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiKbBCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_b_cd,SYUKEI_KB_B_CD_MAX_LENGTH));
	}
	
	// syukei_kb_c_cdに対するセッターとゲッターの集合
	public boolean setSyukeiKbCCd(String syukei_kb_c_cd)
	{
		this.syukei_kb_c_cd = syukei_kb_c_cd;
		return true;
	}
	public String getSyukeiKbCCd()
	{
		return cutString(this.syukei_kb_c_cd,SYUKEI_KB_C_CD_MAX_LENGTH);
	}
	public String getSyukeiKbCCdString()
	{
		return "'" + cutString(this.syukei_kb_c_cd,SYUKEI_KB_C_CD_MAX_LENGTH) + "'";
	}
	public String getSyukeiKbCCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_c_cd,SYUKEI_KB_C_CD_MAX_LENGTH));
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
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//		return "  syukeihinsyu_hinsyu_syukei_hinsyu_cd = " + getSyukeihinsyuHinsyuSyukeiHinsyuCdString()
//			+ "  syukeihinsyu_hinsyu_hinsyu_cd = " + getSyukeihinsyuHinsyuHinsyuCdString()
//			+ "  syukeihinsyu_hinsyu_insert_ts = " + getInsertTsString()
//			+ "  syukeihinsyu_hinsyu_insert_user_id = " + getInsertUserIdString()
//			+ "  syukeihinsyu_hinsyu_update_ts = " + getUpdateTsString()
//			+ "  syukeihinsyu_hinsyu_update_user_id = " + getUpdateUserIdString()
//			+ "  syukeihinsyu_hinsyu_delete_fg = " + getDeleteFgString()
//			+ "  syohin_taikei_hinsyu_cd = " + getSyohinTaikeiHinsyuCdString()
//			+ "  syohin_taikei_hingun_cd = " + getSyohinTaikeiHingunCdString()
//			+ "  syohin_taikei_hanku_cd = " + getSyohinTaikeiHankuCdString()
//			+ "  syohin_taikei_c_syukei_cd = " + getSyohinTaikeiCSyukeiCdString()
//			+ "  syohin_taikei_uriku_cd = " + getSyohinTaikeiUrikuCdString()
//			+ "  syohin_taikei_s_gyosyu_cd = " + getSyohinTaikeiSGyosyuCdString()
//			+ "  syohin_taikei_d_gyosyu_cd = " + getSyohinTaikeiDGyosyuCdString()
//			+ "  syohin_taikei_insert_user_id = " + getSyohinTaikeiInsertUserIdString()
//			+ "  syohin_taikei_insert_ts = " + getSyohinTaikeiInsertTsString()
//			+ "  syohin_taikei_update_user_id = " + getSyohinTaikeiUpdateUserIdString()
//			+ "  syohin_taikei_update_ts = " + getSyohinTaikeiUpdateTsString()
//			+ "  hanku_syubetu_no_cd = " + getHankuSyubetuNoCdString()
//			+ "  hanku_code_cd = " + getHankuCodeCdString()
//			+ "  hanku_kanji_na = " + getHankuKanjiNaString()
//			+ "  hanku_kana_na = " + getHankuKanaNaString()
//			+ "  hanku_kanji_rn = " + getHankuKanjiRnString()
//			+ "  hanku_kana_rn = " + getHankuKanaRnString()
//			+ "  hanku_zokusei_cd = " + getHankuZokuseiCdString()
//			+ "  hanku_insert_ts = " + getHankuInsertTsString()
//			+ "  hanku_insert_user_id = " + getHankuInsertUserIdString()
//			+ "  hanku_update_ts = " + getHankuUpdateTsString()
//			+ "  hanku_update_user_id = " + getHankuUpdateUserIdString()
//			+ "  hanku_delete_fg = " + getHankuDeleteFgString()
//			+ "  hinsyu_syubetu_no_cd = " + getHinsyuSyubetuNoCdString()
//			+ "  hinsyu_code_cd = " + getHinsyuCodeCdString()
//			+ "  hinsyu_kanji_na = " + getHinsyuKanjiNaString()
//			+ "  hinsyu_kana_na = " + getHinsyuKanaNaString()
//			+ "  hinsyu_kanji_rn = " + getHinsyuKanjiRnString()
//			+ "  hinsyu_kana_rn = " + getHinsyuKanaRnString()
//			+ "  hinsyu_zokusei_cd = " + getHinsyuZokuseiCdString()
//			+ "  hinsyu_insert_ts = " + getHinsyuInsertTsString()
//			+ "  hinsyu_insert_user_id = " + getHinsyuInsertUserIdString()
//			+ "  hinsyu_update_ts = " + getHinsyuUpdateTsString()
//			+ "  hinsyu_update_user_id = " + getHinsyuUpdateUserIdString()
//			+ "  hinsyu_delete_fg = " + getHinsyuDeleteFgString()
		return "  syukeihinsyu_cd = " + getSyukeiHinsyuCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_na = " + getHinsyuNaString()
			+ "  syukei_kb_cd = " + getSyukeiKbCd()
			+ "  syukei_kb_na = " + getSyukeiKbNa()
			+ "  syukei_kb_a_cd = " + getSyukeiKbACd()
			+ "  syukei_kb_b_cd = " + getSyukeiKbBCd()
			+ "  syukei_kb_c_cd = " + getSyukeiKbCCd()
			+ "  insert_ts = " + getInsertTs()
			+ "  update_ts = " + getUpdateTs()
			+ "  syorisya_user_id = " + getSyorisyaUserId()
			+ "  syorisya_user_na = " + getSyorisyaUserNa()
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst650101_SyuukeiSyubetuDetailBean コピー後のクラス
	 */
	public mst650101_SyuukeiSyubetuDetailBean createClone()
	{
		mst650101_SyuukeiSyubetuDetailBean bean = new mst650101_SyuukeiSyubetuDetailBean();
//		bean.setSyukeihinsyuHinsyuSyukeiHinsyuCd(this.syukeihinsyu_hinsyu_syukei_hinsyu_cd);
//		bean.setSyukeihinsyuHinsyuHinsyuCd(this.syukeihinsyu_hinsyu_hinsyu_cd);
//		bean.setInsertTs(this.syukeihinsyu_hinsyu_insert_ts);
//		bean.setInsertUserId(this.syukeihinsyu_hinsyu_insert_user_id);
//		bean.setUpdateTs(this.syukeihinsyu_hinsyu_update_ts);
//		bean.setUpdateUserId(this.syukeihinsyu_hinsyu_update_user_id);
//		bean.setDeleteFg(this.syukeihinsyu_hinsyu_delete_fg);
//		bean.setSyohinTaikeiHinsyuCd(this.syohin_taikei_hinsyu_cd);
//		bean.setSyohinTaikeiHingunCd(this.syohin_taikei_hingun_cd);
//		bean.setSyohinTaikeiHankuCd(this.syohin_taikei_hanku_cd);
//		bean.setSyohinTaikeiCSyukeiCd(this.syohin_taikei_c_syukei_cd);
//		bean.setSyohinTaikeiUrikuCd(this.syohin_taikei_uriku_cd);
//		bean.setSyohinTaikeiSGyosyuCd(this.syohin_taikei_s_gyosyu_cd);
//		bean.setSyohinTaikeiDGyosyuCd(this.syohin_taikei_d_gyosyu_cd);
//		bean.setSyohinTaikeiInsertUserId(this.syohin_taikei_insert_user_id);
//		bean.setSyohinTaikeiInsertTs(this.syohin_taikei_insert_ts);
//		bean.setSyohinTaikeiUpdateUserId(this.syohin_taikei_update_user_id);
//		bean.setSyohinTaikeiUpdateTs(this.syohin_taikei_update_ts);
//		bean.setHankuSyubetuNoCd(this.hanku_syubetu_no_cd);
//		bean.setHankuCodeCd(this.hanku_code_cd);
//		bean.setHankuKanjiNa(this.hanku_kanji_na);
//		bean.setHankuKanaNa(this.hanku_kana_na);
//		bean.setHankuKanjiRn(this.hanku_kanji_rn);
//		bean.setHankuKanaRn(this.hanku_kana_rn);
//		bean.setHankuZokuseiCd(this.hanku_zokusei_cd);
//		bean.setHankuInsertTs(this.hanku_insert_ts);
//		bean.setHankuInsertUserId(this.hanku_insert_user_id);
//		bean.setHankuUpdateTs(this.hanku_update_ts);
//		bean.setHankuUpdateUserId(this.hanku_update_user_id);
//		bean.setHankuDeleteFg(this.hanku_delete_fg);
//		bean.setHinsyuSyubetuNoCd(this.hinsyu_syubetu_no_cd);
//		bean.setHinsyuCodeCd(this.hinsyu_code_cd);
//		bean.setHinsyuKanjiNa(this.hinsyu_kanji_na);
//		bean.setHinsyuKanaNa(this.hinsyu_kana_na);
//		bean.setHinsyuKanjiRn(this.hinsyu_kanji_rn);
//		bean.setHinsyuKanaRn(this.hinsyu_kana_rn);
//		bean.setHinsyuZokuseiCd(this.hinsyu_zokusei_cd);
//		bean.setHinsyuInsertTs(this.hinsyu_insert_ts);
//		bean.setHinsyuInsertUserId(this.hinsyu_insert_user_id);
//		bean.setHinsyuUpdateTs(this.hinsyu_update_ts);
//		bean.setHinsyuUpdateUserId(this.hinsyu_update_user_id);
//		bean.setHinsyuDeleteFg(this.hinsyu_delete_fg);
		bean.setSyukeiHinsyuCd(this.syukeihinsyu_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNa(this.hinsyu_na);
		bean.setSyukeiKbCd(this.syukei_kb_cd);
		bean.setSyukeiKbACd(this.syukei_kb_a_cd);
		bean.setSyukeiKbBCd(this.syukei_kb_b_cd);
		bean.setSyukeiKbCCd(this.syukei_kb_c_cd);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
		bean.setSyorisyaUserId(this.syorisya_user_id);
		bean.setSyorisyaUserNa(this.syorisya_user_na);
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
		if( !( o instanceof mst650101_SyuukeiSyubetuDetailBean ) ) return false;
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
