/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
public class mst310101_TanpinTenToriatukaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//	public static final int HANKU_CD_MAX_LENGTH 			= 4; //販区コードの長さ
	public static final int BUMON_CD_MAX_LENGTH             = 4; //部門コードの長さ
	public static final int BUMON_NM_MAX_LENGTH             = 80;//部門名称の長さ
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
	public static final int SYOHIN_CD_MAX_LENGTH 		= 13;//商品コードの長さ
	public static final int TENPO_CD_MAX_LENGTH 			= 6;//店舗コード (FK)の長さ
//	↓↓仕様変更（2005.07.29）↓↓	
//	public static final int YUKO_DT_MAX_LENGTH 			= 8;//有効日の長さ
//	public static final int HANBAI_ST_DT_MAX_LENGTH 		= 8;//販売開始日の長さ
	public static final int DONYU_ST_DT_MAX_LENGTH		= 8;//導入開始日の長さ
	public static final int DONYU_ED_DT_MAX_LENGTH		= 8;//導入終了日の長さ
	
//	↑↑仕様変更（2005.07.29）↑↑
	public static final int NON_ACT_KB_MAX_LENGTH 		= 1;//NON_ACT区分の長さ
	public static final int NON_ACT_SOSHIN_DT_MAX_LENGTH = 8;//NON_ACT送信日の長さ
	public static final int SAISHIN_HACYU_DT_MAX_LENGTH = 8;//最新発注日の長さ
	public static final int SAISHIN_SHIIRE_DT_MAX_LENGTH = 8;//最新仕入日の長さ
	public static final int SAISHIN_URIAGE_DT_MAX_LENGTH = 8;//最新売上日の長さ
	public static final int HASEIMOTO_KB_MAX_LENGTH 		= 1;//発生元区分の長さ
	public static final int TANAWARI_PATERN_MAX_LENGTH 	= 1;//棚割パターンの長さ
//	2005.09.14 Y.Inaba 初期設定仕様変更による追加 START
	public static final int JUKI_NB_MAX_LENGTH = 5;				//什器Ｎｏの長さ
	public static final int TANA_NB_MAX_LENGTH = 5;				//棚Ｎｏ
	public static final int FACE_QT_MAX_LENGTH = 2;				//フェイス数
	public static final int MIN_TINRETU_QT_MAX_LENGTH = 3;		//最低陳列数
	public static final int MAX_TINRETU_QT_MAX_LENGTH = 3;		//最大陳列数
	public static final int BASE_ZAIKO_NISU_QT_MAX_LENGTH = 3;	//基準在庫日数
//	2005.09.14 Y.Inaba 初期設定仕様変更による追加 END
	public static final int INSERT_TS_MAX_LENGTH 		= 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 	= 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH 		= 20;//更新年月日の長さ
	//*********DB Ver3.6 修正箇所 **********************************
	public static final int UPDATE_USER_ID_MAX_LENGTH 	= 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH 		= 1;//削除フラグの長さ
//	↓↓仕様追加による変更（2005.06.29）↓↓
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;//新規登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;//変更日の長さ
//	↑↑仕様追加による変更（2005.06.29）↑↑
//	↓↓修正（2005.08.16）↓↓
	public static final int DONYU_ST_DT_BEFORE_MAX_LENGTH = 8;//導入開始日の長さ（チェック用）
//	↑↑修正（2005.08.16）↑↑

//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//	private String hanku_cd 			= null;	//販区コード
	private String bumon_cd             = null;	//部門コード
	private String bumon_nm             = null;	//部門名称
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
	private String syohin_cd 			= null;	//商品コード
	private String tenpo_cd 			= null;	//店舗コード (FK)
	// ===BEGIN=== Add by kou 2006/9/22
	private String tenpo_na 			= null;	//店舗コード (FK)
	// ===END=== Add by kou 2006/9/22
//	↓↓仕様変更（2005.07.29）↓↓	
//	private String yuko_dt 			= null;	//有効日
//	private String hanbai_st_dt 		= null;	//販売開始日
	private String donyu_st_dt			= null;//導入開始日
	private String donyu_ed_dt			= null;//導入終了日
//	↑↑仕様変更（2005.07.29）↑↑
	private String non_act_kb 			= null;	//NON_ACT区分
	private String non_act_soshin_dt 	= null;	//NON_ACT区分
	private String saishin_hacyu_dt 	= null;	//最新発注日
	private String saishin_shiire_dt 	= null;	//最新仕入日
	private String saishin_uriage_dt 	= null;	//最新売上日
	private String haseimoto_kb 		= null;	//発生元区分
	private String tanawari_patern 	= null;	//棚割パターン
// 2005.09.14 Y.Inaba 初期設定仕様変更 START
//	private long juki_nb 				= 0;		//什器NO
//	private long tana_nb 				= 0;		//棚NO
//	private long face_qt 				= 0;		//フェイス数
//	private long min_tinretu_qt 	  	= 0;		//最低陳列数
//	private long max_tinretu_qt 	  	= 0;		//最大陳列数
//	private long base_zaiko_nisu_qt	= 0;		//基準在庫日数
	private String juki_nb = null;	//什器NO
	private String tana_nb = null;	//棚NO
	private String face_qt = null;	//フェイス数
	private String min_tinretu_qt = null;	//最低陳列数
	private String max_tinretu_qt = null;	//最大陳列数
	private String base_zaiko_nisu_qt = null;	//基準在庫日数
// 2005.09.14 Y.Inaba 初期設定仕様変更 END
	private String insert_ts 		  	= null;	//作成年月日
	private String insert_user_id 	  	= null;	//作成者社員ID
	private String update_ts 		  	= null;	//更新年月日
	//*********DB Ver3.6 修正箇所 **********************************
	private String update_user_id 	  	= null;	//更新者社員ID
	private String delete_fg 		  	= null;	//削除フラグ
//	↓↓仕様追加による変更（2005.06.29）↓↓
	private String sinki_toroku_dt = null;//新規登録日
	private String henko_dt = null;	//変更日
//	↑↑仕様追加による変更（2005.06.29）↑↑

//	↓↓修正（2005.08.16）↓↓
	private String donyu_st_dt_before 	= null;	//導入開始日（チェック用）
//	↑↑修正（2005.08.16）↑↑
	
	private String tenpo_kanji_rn		= null;	//店舗名
	private String insert_user_nm 	  	= null;	//作成者社員名
	private String update_user_nm 	  	= null;	//更新者社員名
	private String sentaku 			= "";		//画面入力CheckBox
	private Map    ctlColor 			= null;	//画面コントロール色	
	private String disbale 			= null;	//明細入力可否

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
	 * mst310201_TanpinTenToriatukaiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst310101_TanpinTenToriatukaiBean getmst310201_TanpinTenToriatukaiBean(DataHolder dataHolder)
	{
		mst310101_TanpinTenToriatukaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst310101_TanpinTenToriatukaiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst310101_TanpinTenToriatukaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



//	↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
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
	
	// bumon_cdに対するセッターとゲッターの集合
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

	// bumon_nmに対するセッターとゲッターの集合
	public boolean setBumonNm(String bumon_nm)
	{
		this.bumon_nm = bumon_nm;
		return true;
	}
	public String getBumonNm()
	{
		return cutString(this.bumon_nm,BUMON_NM_MAX_LENGTH);
	}
	public String getBumonNmString()
	{
		return "'" + cutString(this.bumon_nm,BUMON_NM_MAX_LENGTH) + "'";
	}
	public String getBumonNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_nm,BUMON_NM_MAX_LENGTH));
	}

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
//	↑↑2006.06.21 jiangyan カスタマイズ修正↑↑

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


//	↓↓仕様変更（2005.07.29）↓↓	
	// yuko_dtに対するセッターとゲッターの集合
//	public boolean setYukoDt(String yuko_dt)
//	{
//		this.yuko_dt = yuko_dt;
//		return true;
//	}
//	public String getYukoDt()
//	{
//		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
//	}
//	public String getYukoDtString()
//	{
//		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
//	}
//	public String getYukoDtHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
//	}


//	// hanbai_st_dtに対するセッターとゲッターの集合
//	public boolean setHanbaiStDt(String hanbai_st_dt)
//	{
//		this.hanbai_st_dt = hanbai_st_dt;
//		return true;
//	}
//	public String getHanbaiStDt()
//	{
//		return cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH);
//	}
//	public String getHanbaiStDtString()
//	{
//		return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH) + "'";
//	}
//	public String getHanbaiStDtHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_MAX_LENGTH));
//	}

	// donyu_st_dtに対するセッターとゲッターの集合
	public boolean setDonyuStDt(String donyu_st_dt)
	{
		this.donyu_st_dt = donyu_st_dt;
		return true;
	}

	public String getDonyuStDt()
	{
		return cutString(this.donyu_st_dt,DONYU_ST_DT_MAX_LENGTH);
	}
	public String getDonyuStDtString()
	{
		return "'" + cutString(this.donyu_st_dt,DONYU_ST_DT_MAX_LENGTH) + "'";
	}
	public String getDonyuStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.donyu_st_dt,DONYU_ST_DT_MAX_LENGTH));
	}

	
	// donyu_ed_dtに対するセッターとゲッターの集合
	public boolean setDonyuEdDt(String donyu_ed_dt)
	{
		this.donyu_ed_dt = donyu_ed_dt;
		return true;
	}

	public String getDonyuEdDt()
	{
		return cutString(this.donyu_ed_dt,DONYU_ED_DT_MAX_LENGTH);
	}
	public String getDonyuEdDtString()
	{
		return "'" + cutString(this.donyu_ed_dt,DONYU_ED_DT_MAX_LENGTH) + "'";
	}
	public String getDonyuEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.donyu_ed_dt,DONYU_ED_DT_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.29）↑↑

	// non_act_kbに対するセッターとゲッターの集合
	public boolean setNonActKb(String non_act_kb)
	{
		this.non_act_kb = non_act_kb;
		return true;
	}
	public String getNonActKb()
	{
		return cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH);
	}
	public String getNonActKbString()
	{
		return "'" + cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH) + "'";
	}
	public String getNonActKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH));
	}
	
	// insert_tsに対するセッターとゲッターの集合
	public boolean setNonActSoshinDt(String non_act_soshin_dt)
	{
		this.non_act_soshin_dt = non_act_soshin_dt;
		return true;
	}
	public String getNonActSoshinDt()
	{
		return cutString(this.non_act_soshin_dt,NON_ACT_SOSHIN_DT_MAX_LENGTH);
	}
	public String getNonActSoshinDtString()
	{
		return "'" + cutString(this.non_act_soshin_dt,NON_ACT_SOSHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNonActSoshinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.non_act_soshin_dt,NON_ACT_SOSHIN_DT_MAX_LENGTH));
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setSaiShinHachuDt(String saishin_hacyu_dt)
	{
		this.saishin_hacyu_dt = saishin_hacyu_dt;
		return true;
	}
	public String getSaiShinHachuDt()
	{
		return cutString(this.saishin_hacyu_dt,SAISHIN_HACYU_DT_MAX_LENGTH);
	}
	public String getSaiShinHachuDtString()
	{
		return "'" + cutString(this.saishin_hacyu_dt,SAISHIN_HACYU_DT_MAX_LENGTH) + "'";
	}
	public String getSaiShinHachuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.saishin_hacyu_dt,SAISHIN_HACYU_DT_MAX_LENGTH));
	}
	
	// insert_tsに対するセッターとゲッターの集合
	public boolean setSaishinShiireDt(String saishin_shiire_dt)
	{
		this.saishin_shiire_dt = saishin_shiire_dt;
		return true;
	}
	public String getSaishinShiireDt()
	{
		return cutString(this.saishin_shiire_dt,SAISHIN_SHIIRE_DT_MAX_LENGTH);
	}
	public String getSaishinShiireDtString()
	{
		return "'" + cutString(this.saishin_shiire_dt,SAISHIN_SHIIRE_DT_MAX_LENGTH) + "'";
	}
	public String getSaishinShiireDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.saishin_shiire_dt,SAISHIN_SHIIRE_DT_MAX_LENGTH));
	}
	
	// insert_tsに対するセッターとゲッターの集合
	public boolean setSaishinUriageDt(String saishin_uriage_dt)
	{
		this.saishin_uriage_dt = saishin_uriage_dt;
		return true;
	}
	public String getSaishinUriageDt()
	{
		return cutString(this.saishin_uriage_dt,SAISHIN_URIAGE_DT_MAX_LENGTH);
	}
	public String getSaishinUriageDtString()
	{
		return "'" + cutString(this.saishin_uriage_dt,SAISHIN_URIAGE_DT_MAX_LENGTH) + "'";
	}
	public String getSaishinUriageDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.saishin_uriage_dt,SAISHIN_URIAGE_DT_MAX_LENGTH));
	}
	
	// haseimoto_kbに対するセッターとゲッターの集合
	public boolean setHaseimotoKb(String haseimoto_kb)
	{
		this.haseimoto_kb = haseimoto_kb;
		return true;
	}
	public String getHaseimotoKb()
	{
		return cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH);
	}
	public String getHaseimotoKbString()
	{
		return "'" + cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH) + "'";
	}
	public String getHaseimotoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH));
	}


	// tanawari_paternに対するセッターとゲッターの集合
	public boolean setTanawariPatern(String tanawari_patern)
	{
		this.tanawari_patern = tanawari_patern;
		return true;
	}
	public String getTanawariPatern()
	{
		return cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH);
	}
	public String getTanawariPaternString()
	{
		return "'" + cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH) + "'";
	}
	public String getTanawariPaternHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH));
	}


// 2005.09.14 Y.Inaba 初期設定仕様変更 END
	// juki_nbに対するセッターとゲッターの集合
/*	public boolean setJukiNb(String juki_nb)
	{
		try
		{
			this.juki_nb = Long.parseLong(juki_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setJukiNb(long juki_nb)
	{
		this.juki_nb = juki_nb;
		return true;
	}
	public long getJukiNb()
	{
		return this.juki_nb;
	}
	public String getJukiNbString()
	{
		return Long.toString(this.juki_nb);
	}
*/
	public boolean setJukiNb(String juki_nb){
		this.juki_nb = juki_nb;
		return true;
	}
	public String getJukiNb(){
		return this.juki_nb;
	}

	// tana_nbに対するセッターとゲッターの集合
/*	public boolean setTanaNb(String tana_nb)
	{
		try
		{
			this.tana_nb = Long.parseLong(tana_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTanaNb(long tana_nb)
	{
		this.tana_nb = tana_nb;
		return true;
	}
	public long getTanaNb()
	{
		return this.tana_nb;
	}
	public String getTanaNbString()
	{
		return Long.toString(this.tana_nb);
	}
*/
	public boolean setTanaNb(String tana_nb){
		this.tana_nb = tana_nb;
		return true;
	}
	public String getTanaNb(){
		return this.tana_nb;
	}

	// face_qtに対するセッターとゲッターの集合
/*	public boolean setFaceQt(String face_qt)
	{
		try
		{
			this.face_qt = Long.parseLong(face_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setFaceQt(long face_qt)
	{
		this.face_qt = face_qt;
		return true;
	}
	public long getFaceQt()
	{
		return this.face_qt;
	}
	public String getFaceQtString()
	{
		return Long.toString(this.face_qt);
	}
*/
	public boolean setFaceQt(String face_qt){
		this.face_qt = face_qt;
		return true;
	}
	public String getFaceQt(){
		return this.face_qt;
	}

	// min_tinretu_qtに対するセッターとゲッターの集合
/*	public boolean setMinTinretuQt(String min_tinretu_qt)
	{
		try
		{
			this.min_tinretu_qt = Long.parseLong(min_tinretu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMinTinretuQt(long min_tinretu_qt)
	{
		this.min_tinretu_qt = min_tinretu_qt;
		return true;
	}
	public long getMinTinretuQt()
	{
		return this.min_tinretu_qt;
	}
	public String getMinTinretuQtString()
	{
		return Long.toString(this.min_tinretu_qt);
	}
*/
	public boolean setMinTinretuQt(String min_tinretu_qt){
		this.min_tinretu_qt = min_tinretu_qt;
		return true;
	}
	public String getMinTinretuQt(){
		return this.min_tinretu_qt;
	}

	// max_tinretu_qtに対するセッターとゲッターの集合
/*	public boolean setMaxTinretuQt(String max_tinretu_qt)
	{
		try
		{
			this.max_tinretu_qt = Long.parseLong(max_tinretu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxTinretuQt(long max_tinretu_qt)
	{
		this.max_tinretu_qt = max_tinretu_qt;
		return true;
	}
	public long getMaxTinretuQt()
	{
		return this.max_tinretu_qt;
	}
	public String getMaxTinretuQtString()
	{
		return Long.toString(this.max_tinretu_qt);
	}
*/
	public boolean setMaxTinretuQt(String max_tinretu_qt){
		this.max_tinretu_qt = max_tinretu_qt;
		return true;
	}
	public String getMaxTinretuQt(){
		return this.max_tinretu_qt;
	}

	// base_zaiko_nisu_qtに対するセッターとゲッターの集合
/*	public boolean setBaseZaikoNisuQt(String base_zaiko_nisu_qt)
	{
		try
		{
			this.base_zaiko_nisu_qt = Long.parseLong(base_zaiko_nisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaseZaikoNisuQt(long base_zaiko_nisu_qt)
	{
		this.base_zaiko_nisu_qt = base_zaiko_nisu_qt;
		return true;
	}
	public long getBaseZaikoNisuQt()
	{
		return this.base_zaiko_nisu_qt;
	}
	public String getBaseZaikoNisuQtString()
	{
		return Long.toString(this.base_zaiko_nisu_qt);
	}
*/
	public boolean setBaseZaikoNisuQt(String base_zaiko_nisu_qt){
		this.base_zaiko_nisu_qt = base_zaiko_nisu_qt;
		return true;
	}
	public String getBaseZaikoNisuQt(){
		return this.base_zaiko_nisu_qt;
	}
// 2005.09.14 Y.Inaba 初期設定仕様変更 END

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

	//*********DB Ver3.6 修正箇所 **********************************
	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserTs()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserTsString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserTsHTMLString()
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
	
//	↓↓仕様追加による変更（2005.06.29）↓↓
	// sinki_toroku_dtに対するセッターとゲッターの集合
	public boolean setSinkiTorokuDt(String sinki_toroku_dt)
	{
		this.sinki_toroku_dt = sinki_toroku_dt;
		return true;
	}
	public String getSinkiTorokuDt()
	{
		return cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH);
	}
	public String getSinkiTorokuDtString()
	{
		return "'" + cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH) + "'";
	}
	public String getSinkiTorokuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sinki_toroku_dt,SINKI_TOROKU_DT_MAX_LENGTH));
	}
	
	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt,HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt,HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt,HENKO_DT_MAX_LENGTH));
	}
//	↑↑仕様追加による変更（2005.06.29）↑↑

//	↓↓修正（2005.08.16）↓↓
	// donyu_st_dt_beforeに対するセッターとゲッターの集合
	public boolean setDonyuStDtBefore(String donyu_st_dt_before)
	{
		this.donyu_st_dt_before = donyu_st_dt_before;
		return true;
	}
	public String getDonyuStDtBefore()
	{
		return cutString(this.donyu_st_dt_before,DONYU_ST_DT_BEFORE_MAX_LENGTH);
	}
	public String getDonyuStDtBeforeString()
	{
		return "'" + cutString(this.donyu_st_dt_before,DONYU_ST_DT_BEFORE_MAX_LENGTH) + "'";
	}
	public String getDonyuStDtBeforeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.donyu_st_dt_before,DONYU_ST_DT_BEFORE_MAX_LENGTH));
	}
//	↑↑修正（2005.08.16）↑↑
	
	// tenpo_kanji_rnに対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
		return this.tenpo_kanji_rn;
	}
	
	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return this.insert_user_nm;
	}
	
	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return this.update_user_nm;
	}
	
	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return this.sentaku;
	}

	// ctlColorに対するセッターとゲッターの集合
	public boolean setCtlColor(Map ctlColor)
	{
		this.ctlColor = ctlColor;
		return true;
	}
	public Map getCtlColor()
	{
		return this.ctlColor;
	}
	
	// disbaleに対するセッターとゲッターの集合
	public boolean setDisbale(String disbale)
	{
		this.disbale = disbale;
		return true;
	}
	public String getDisbale()
	{
		return this.disbale;
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	//*********DB Ver3.6 修正箇所 **********************************
	public String toString()
	{
//		↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//		return "  hanku_cd = " + getHankuCdString()
		return "  bumon_cd = " + getBumonCdString()
//		↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
//			↓↓仕様変更（2005.07.29）↓↓
//			+ "  yuko_dt = " + getYukoDtString()
//			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  donyu_st_dt = " + getDonyuStDtString()
//			+ "  donyu_ed_dt = " + getDonyuEdDtString()
//			↑↑仕様変更（2005.07.29）↑↑
			+ "  non_act_kb = " + getNonActKbString()
			+ "  haseimoto_kb = " + getHaseimotoKbString()
			+ "  tanawari_patern = " + getTanawariPaternString()
// 2005.09.14 Y.Inaba 初期設定仕様変更 START
//			+ "  juki_nb = " + getJukiNbString()
//			+ "  tana_nb = " + getTanaNbString()
//			+ "  face_qt = " + getFaceQtString()
//			+ "  min_tinretu_qt = " + getMinTinretuQtString()
//			+ "  max_tinretu_qt = " + getMaxTinretuQtString()
//			+ "  base_zaiko_nisu_qt = " + getBaseZaikoNisuQtString()
			+ "  juki_nb = " + getJukiNb()
			+ "  tana_nb = " + getTanaNb()
			+ "  face_qt = " + getFaceQt()
			+ "  min_tinretu_qt = " + getMinTinretuQt()
			+ "  max_tinretu_qt = " + getMaxTinretuQt()
			+ "  base_zaiko_nisu_qt = " + getBaseZaikoNisuQt()
			+ "  sentaku = " + getSentaku()
// 2005.09.14 Y.Inaba 初期設定仕様変更 END
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  insert_user_nm = " + getInsertUserNm()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserTsString()
			+ "  update_user_nm = " + getUpdateUserNm()			
			+ "  delete_fg = " + getDeleteFgString()
//			↓↓仕様追加による変更（2005.06.29）↓↓
			+ "  sinki_toroku_dt = " + getSinkiTorokuDt()
			+ "  henko_dt = " + getHenkoDt()
//			↑↑仕様追加による変更（2005.06.29）↑↑
			+ " createDatabase  = " + createDatabase;
			
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst310201_TanpinTenToriatukaiBean コピー後のクラス
	 */
	public mst310101_TanpinTenToriatukaiBean createClone()
	{
		mst310101_TanpinTenToriatukaiBean bean = new mst310101_TanpinTenToriatukaiBean();
//		↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//		bean.setHankuCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
//		↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
//		↓↓仕様変更（2005.07.29）↓↓
//		bean.setYukoDt(this.yuko_dt);
//		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setDonyuStDt(this.donyu_st_dt);
//		bean.setDonyuEdDt(this.donyu_ed_dt);
//		↑↑仕様変更（2005.07.29）↑↑
		bean.setNonActKb(this.non_act_kb);
		bean.setHaseimotoKb(this.haseimoto_kb);
		bean.setTanawariPatern(this.tanawari_patern);
		bean.setJukiNb(this.juki_nb);
		bean.setTanaNb(this.tana_nb);
		bean.setFaceQt(this.face_qt);
		bean.setMinTinretuQt(this.min_tinretu_qt);
		bean.setMaxTinretuQt(this.max_tinretu_qt);
		bean.setBaseZaikoNisuQt(this.base_zaiko_nisu_qt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNm(this.insert_user_nm);
		bean.setUpdateTs(this.update_ts);
		//*********DB Ver3.6 修正箇所 **********************************
		bean.setUpdateUserTs(this.update_user_id);
		bean.setUpdateUserNm(this.update_user_nm);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様追加による変更（2005.06.29）↓↓
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);
		bean.setHenkoDt(this.henko_dt);
//		↑↑仕様追加による変更（2005.06.29）↑↑
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
		if( !( o instanceof mst310101_TanpinTenToriatukaiBean ) ) return false;
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
	/**
	 * @return
	 */
	public String getTenpo_na() {
		return tenpo_na;
	}

	/**
	 * @param string
	 */
	public void setTenpo_na(String string) {
		tenpo_na = string;
	}

}
