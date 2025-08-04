/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタ一括修正のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ一括修正のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタ一括修正のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタ一括修正のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/17)初版作成
 */
public class mst170101_SyohinIkkatuMenteBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TOUROKU_TS_MAX_LENGTH = 14;//登録年月日の長さ
	public static final int TOROKU_USER_ID_MAX_LENGTH = 10;//登録社員IDの長さ
//    ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
//    ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
	public static final int GENKA_CHG_KB_MAX_LENGTH = 1;//原価変更区分の長さ
	public static final int BAIKA_CHG_KB_MAX_LENGTH = 1;//売価変更区分の長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;//販売開始日の長さ
	public static final int HANBAI_ED_DT_MAX_LENGTH = 8;//販売終了日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ


	private String touroku_ts = null;	//登録年月日
	private String toroku_user_id = null;	//登録社員ID
//    ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
	public static final int HACHU_ST_DT_MAX_LENGTH = 8;//発注開始日の長さ
	public static final int HACHU_ED_DT_MAX_LENGTH = 8;//発注終了日の長さ
	public static final int SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;//店別仕入先管理コードの長さ
	public static final int BUMON_CD_MAX_LENGTH      =4;//部門の長さ
	public static final int UNIT_CD_MAX_LENGTH                =4;//ユニットコードの長さ
	public static final int UNIT_KANJI_RN_MAX_LENGTH      =20;//ユニット名の長さ
	public static final int BAYIA_CD_MAX_LENGTH                =10;//初回登録社員IDの長さ
	public static final int EDS_DB_MAX_LENGTH                =1;//EOS区分の長さ
	public static final int NEIRE_RT_KB_MAX_LENGTH                =1;//値入率区分の長さ
	public static final int NEIRE_RT_VL_MAX_LENGTH                =3;//値入率の長さ
	public static final int TANA_NO_NB_MAX_LENGTH				=5;//棚NOの長さ
//	private String hanku_cd = null;	//販区コード
	private String siiresakikanri_cd       =null;				//店別仕入先管理コード
	private String bumon_cd				=null;				//部門コード
	private String unit_cd        	    	=null;				//ユニットコード
	private String bayia_cd        	    =null;				//初回登録社員ID
	private String hachu_st_dt        	    =null;				//店舗発注開始日
	private String hachu_ed_dt        	    =null;				//店舗発注終了日
	private String up_neirert_kb 			=null;					//値入率変更区分(更新)
	private String tanano_nb				=null;				//棚NO
	private long up_neirert_vl 			= 0;					//値入率変更値(更新)
	private String up_eoskb_chg_vl 		= null;				//EOS区分変更値(更新)up_eoskb_chg_vl

//    ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
	private String syohin_cd = null;	//商品コード
	private String siiresaki_cd = null;	//仕入先コード
	private String genka_chg_kb = null;	//原価変更区分
	private long genka_chg_vl = 0;	//原価変更値
	private String baika_chg_kb = null;	//売価変更区分
	private long baika_chg_vl = 0;	//売価変更値
	private String hanbai_st_dt = null;	//販売開始日
	private String hanbai_ed_dt = null;	//販売終了日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

	// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	private String yuko_dt = null;	//有効日
	// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //

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
	 * RSYOHINIKKATUMENTEBeanを１件のみ抽出したい時に使用する
	 */
	public static mst170101_SyohinIkkatuMenteBean getmst170101_SyohinIkkatuMenteBean(DataHolder dataHolder)
	{
		mst170101_SyohinIkkatuMenteBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst170101_SyohinIkkatuMenteDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst170101_SyohinIkkatuMenteBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}
//    ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
//	 siiresakikanri_cdに対するセッターとゲッターの集合
	public boolean setSiiresakikanriCd(String siiresakikanri_cd)
	{
		this.siiresakikanri_cd = siiresakikanri_cd;
		return true;
	}
	public String getSiiresakikanriCd()
	{
		return cutString(this.siiresakikanri_cd,SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getSiiresakikanriString()
	{
		return "'" + cutString(this.siiresakikanri_cd,SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakikanriHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresakikanri_cd,SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}
//	 tanano_nbに対するセッターとゲッターの集合
	public boolean setTananoNb(String tanano_nb)
	{
		this.tanano_nb = tanano_nb;
		return true;
	}
	public String getTananoNb()
	{
		return this.tanano_nb;
	}
	public String getTananoString()
	{
		return "'" + cutString(this.tanano_nb,TANA_NO_NB_MAX_LENGTH) + "'";
	}
	public String getTananoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanano_nb,TANA_NO_NB_MAX_LENGTH));
	}
//	unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}
//	bumon_cdに対するセッターとゲッターの集合
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}
//	bayia_cdに対するセッターとゲッターの集合
	public boolean setBayiaCd(String bayia_cd)
	{
		this.bayia_cd = bayia_cd;
		return true;
	}
	public String getBayiaCd()
	{
		return cutString(this.bayia_cd,BAYIA_CD_MAX_LENGTH);
	}
	public String getBayiaCdString()
	{
		return "'" + cutString(this.bayia_cd,BAYIA_CD_MAX_LENGTH) + "'";
	}
	public String getBayiaCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bayia_cd,BAYIA_CD_MAX_LENGTH));
	}
	// hachu_st_dtに対するセッターとゲッターの集合
	public boolean setUpHachuStDt(String hachu_st_dt)
	{
		this.hachu_st_dt = hachu_st_dt;
		return true;
	}
	public String getUpHachuStDt()
	{
		return cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH);
	}
	public String getUpHachuStDtString()
	{
		return "'" + cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getUpHachuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_st_dt,HACHU_ST_DT_MAX_LENGTH));
	}


	// hachu_ed_dtに対するセッターとゲッターの集合
	public boolean setUpHachuEdDt(String hachu_ed_dt)
	{
		this.hachu_ed_dt = hachu_ed_dt;
		return true;
	}
	public String getUpHachuEdDt()
	{
		return cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH);
	}
	public String getUpHachuEdDtString()
	{
		return "'" + cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getUpHachuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_ed_dt,HACHU_ED_DT_MAX_LENGTH));
	}

//	 up_neirert_kbに対するセッターとゲッターの集合
	public boolean setUpNeirertChgKb(String up_neirert_kb)
	{
		this.up_neirert_kb = up_neirert_kb;
		return true;
	}
	public String getUpNeirertChgKb()
	{
		return cutString(this.up_neirert_kb,NEIRE_RT_KB_MAX_LENGTH);
	}
	public String getUpNeirertChgKbString()
	{
		return "'" + cutString(this.up_neirert_kb,NEIRE_RT_KB_MAX_LENGTH) + "'";
	}
	public String getUpNeirertChgKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.up_neirert_kb,NEIRE_RT_KB_MAX_LENGTH));
	}

//	 up_neirert_vlに対するセッターとゲッターの集合
	public boolean setUpNeirertChgVl(String up_neirert_vl)
	{
		try
		{
			this.up_neirert_vl = Long.parseLong(up_neirert_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setUpNeirertChgVl(long up_neirert_vl)
	{
		this.up_neirert_vl = up_neirert_vl;
		return true;
	}
	public long getUpNeirertChgVl()
	{
		return this.up_neirert_vl;
	}
	public String getUpNeirertChgVlString()
	{
		return Long.toString(this.up_neirert_vl);
	}
//	 up_eoskb_chg_vlに対するセッターとゲッターの集合
	public boolean setUpEoskbaChgVl(String up_eoskb_chg_vl)
	{
		this.up_eoskb_chg_vl = up_eoskb_chg_vl;
		return true;
	}
	public String getUpEoskbaChgVl()
	{
		return cutString(this.up_eoskb_chg_vl,EDS_DB_MAX_LENGTH);

	}
	public String getUpEoskbaChgVlString()
	{
		return "'" + cutString(this.up_eoskb_chg_vl,EDS_DB_MAX_LENGTH) + "'";
	}
	public String getUpEoskbaChgVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.up_eoskb_chg_vl,EDS_DB_MAX_LENGTH));
	}
//  ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
	// touroku_tsに対するセッターとゲッターの集合
	public boolean setTourokuTs(String touroku_ts)
	{
		this.touroku_ts = touroku_ts;
		return true;
	}
	public String getTourokuTs()
	{
		return cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH);
	}
	public String getTourokuTsString()
	{
		return "'" + cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH) + "'";
	}
	public String getTourokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.touroku_ts,TOUROKU_TS_MAX_LENGTH));
	}

	// toroku_user_idに対するセッターとゲッターの集合
	public boolean setTorokuUserId(String toroku_user_id)
	{
		this.toroku_user_id = toroku_user_id;
		return true;
	}
	public String getTorokuUserId()
	{
		return cutString(this.toroku_user_id,TOROKU_USER_ID_MAX_LENGTH);
	}
	public String getTorokuUserIdString()
	{
		return "'" + cutString(this.toroku_user_id,TOROKU_USER_ID_MAX_LENGTH) + "'";
	}
	public String getTorokuUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.toroku_user_id,TOROKU_USER_ID_MAX_LENGTH));
	}

//    ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//    ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}


	// siiresaki_cdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}


	// genka_chg_kbに対するセッターとゲッターの集合
	public boolean setGenkaChgKb(String genka_chg_kb)
	{
		this.genka_chg_kb = genka_chg_kb;
		return true;
	}
	public String getGenkaChgKb()
	{
		return cutString(this.genka_chg_kb,GENKA_CHG_KB_MAX_LENGTH);
	}
	public String getGenkaChgKbString()
	{
		return "'" + cutString(this.genka_chg_kb,GENKA_CHG_KB_MAX_LENGTH) + "'";
	}
	public String getGenkaChgKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.genka_chg_kb,GENKA_CHG_KB_MAX_LENGTH));
	}


	// genka_chg_vlに対するセッターとゲッターの集合
	public boolean setGenkaChgVl(String genka_chg_vl)
	{
		try
		{
			this.genka_chg_vl = Long.parseLong(genka_chg_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGenkaChgVl(long genka_chg_vl)
	{
		this.genka_chg_vl = genka_chg_vl;
		return true;
	}
	public long getGenkaChgVl()
	{
		return this.genka_chg_vl;
	}
	public String getGenkaChgVlString()
	{
		return Long.toString(this.genka_chg_vl);
	}


	// baika_chg_kbに対するセッターとゲッターの集合
	public boolean setBaikaChgKb(String baika_chg_kb)
	{
		this.baika_chg_kb = baika_chg_kb;
		return true;
	}
	public String getBaikaChgKb()
	{
		return cutString(this.baika_chg_kb,BAIKA_CHG_KB_MAX_LENGTH);
	}
	public String getBaikaChgKbString()
	{
		return "'" + cutString(this.baika_chg_kb,BAIKA_CHG_KB_MAX_LENGTH) + "'";
	}
	public String getBaikaChgKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baika_chg_kb,BAIKA_CHG_KB_MAX_LENGTH));
	}


	// baika_chg_vlに対するセッターとゲッターの集合
	public boolean setBaikaChgVl(String baika_chg_vl)
	{
		try
		{
			this.baika_chg_vl = Long.parseLong(baika_chg_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaikaChgVl(long baika_chg_vl)
	{
		this.baika_chg_vl = baika_chg_vl;
		return true;
	}
	public long getBaikaChgVl()
	{
		return this.baika_chg_vl;
	}
	public String getBaikaChgVlString()
	{
		return Long.toString(this.baika_chg_vl);
	}


	// hanbai_st_dtに対するセッターとゲッターの集合
	public boolean setHanbaiStDt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
		return true;
	}
	public String getHanbaiStDt()
	{
		return cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getHanbaiStDtString()
	{
		return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH));
	}


	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHanbaiEdDt(String hanbai_ed_dt)
	{
		this.hanbai_ed_dt = hanbai_ed_dt;
		return true;
	}
	public String getHanbaiEdDt()
	{
		return cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH);
	}
	public String getHanbaiEdDtString()
	{
		return "'" + cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_ed_dt,HANBAI_ED_DT_MAX_LENGTH));
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

	// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //

	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}

	// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  touroku_ts = " + getTourokuTsString()
			+ "  toroku_user_id = " + getTorokuUserIdString()
//           ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
			+ "  bumon_cd = "+ getBumonCdString()
			+ "  bayia_cd = " + getBayiaCdString()
			+ "  up_neirert_vl = " + getUpNeirertChgVlString()
			+ "  up_neirert_kb = " + getUpNeirertChgKbString()
			+ "  up_eoskb_chg_vl = " + getUpEoskbaChgVlString()
			+ "  unit_cd = " + getUnitCdString()
			+ " hachu_st_dt = " + getUpHachuStDt()
			+ " hachu_ed_dt = " + getUpHachuEdDt()
			+ " siiresakikanri_cd = " + getSiiresakikanriString()
			+" tana_no_nb = " +  getTananoString()
//			+ "  hanku_cd = " + getHankuCdString()
//		      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  genka_chg_kb = " + getGenkaChgKbString()
			+ "  genka_chg_vl = " + getGenkaChgVlString()
			+ "  baika_chg_kb = " + getBaikaChgKbString()
			+ "  baika_chg_vl = " + getBaikaChgVlString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getHanbaiEdDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
		// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //
			+ "  yuko_dt = " + getYukoDtString()
		// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst170101_SyohinIkkatuMenteBean コピー後のクラス
	 */
	public mst170101_SyohinIkkatuMenteBean createClone()
	{
		mst170101_SyohinIkkatuMenteBean bean = new mst170101_SyohinIkkatuMenteBean();
		bean.setTourokuTs(this.touroku_ts);
		bean.setTorokuUserId(this.toroku_user_id);
//	      ↓↓2006.07.06 zhangxia カスタマイズ修正↓↓
		bean.setBumonCd(this.bumon_cd);
		bean.setBayiaCd(this.bayia_cd);
		bean.setUpNeirertChgVl(this.up_neirert_vl);
		bean.setUpNeirertChgKb(this.up_neirert_kb);
		bean.setUpEoskbaChgVl(this.up_eoskb_chg_vl);
		bean.setUnitCd(this.unit_cd);
		bean.setHanbaiStDt(this.hachu_st_dt);
		bean.setHanbaiEdDt(this.hachu_ed_dt);
		bean.setSiiresakikanriCd(this.siiresakikanri_cd);
		bean.setTananoNb(this.tanano_nb);
//		bean.setHankuCd(this.hanku_cd);
//	      ↑↑2006.07.06 zhangxia カスタマイズ修正↑↑
		bean.setSyohinCd(this.syohin_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setGenkaChgKb(this.genka_chg_kb);
		bean.setGenkaChgVl(this.genka_chg_vl);
		bean.setBaikaChgKb(this.baika_chg_kb);
		bean.setBaikaChgVl(this.baika_chg_vl);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		// ↓↓　仕様追加による変更（2005.06.16）　↓↓ //
		bean.setYukoDt(this.yuko_dt);
		// ↑↑　仕様追加による変更（2005.06.16）　↑↑ //
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
		if( !( o instanceof mst170101_SyohinIkkatuMenteBean ) ) return false;
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
