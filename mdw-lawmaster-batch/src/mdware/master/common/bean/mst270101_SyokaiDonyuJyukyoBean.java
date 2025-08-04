/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
public class mst270101_SyokaiDonyuJyukyoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コードの長さ

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	public static final int HACHUNO_CD_MAX_LENGTH = 10;//発注コードの長さ
	
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int THEME_CD_MAX_LENGTH = 2;//テーマコードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int TENPO_NM_MAX_LENGTH = 20;//店舗コード名称の長さ
	public static final int COLOR_CD_MAX_LENGTH = 2;//カラーコードの長さ
	public static final int SIZE_CD_MAX_LENGTH = 2;//サイズコードの長さ
	public static final int ASORT_PATTERN_CD_MAX_LENGTH = 2;//アソートパターンの長さ
	public static final int HATYU_DT_MAX_LENGTH = 8;//発注日の長さ
	public static final int NOHIN_DT_MAX_LENGTH = 8;//納品日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	public static final int HANBAI_ST_DT_LENGTH = 8;//販売開始日の長さ
	public static final int HANBAI_ED_DT_LENGTH = 8;//店舗発注開始日の長さ
//	2006/03/01 kim START
	public static final int AUTO_HACHU_KB_MAX_LENGTH 				= 1;	//自動発注一時の長さ
	private String auto_hachu_kb =""; 										//自動発注一時・停止	
//	2006/03/01 kim END
//	↓↓仕様変更（2005.07.07）↓↓
	public static final int SENTAKU_MAX_LENGTH = 1;			//処理選択
//	↑↑仕様変更（2005.07.07）↑↑

//	private String hanku_cd = null;	//販区コード
	
//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	private String siiresaki_cd = null;	//仕入先コード

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	private String hachuno_cd = null;	//発注コード
	
	private String syohin_cd = null;	//商品コード
	private String theme_cd = null;	//テーマコード
	private String tenpo_cd = null;	//店舗コード
	private String tenpo_nm = null;	//店舗コード名称
	private String suryo_qt = null;	//数量
	private String suryo_qt_old = null;	//数量(検索時の値)
	private String gentanka_vl = null;	//原価単価
	private String baitanka_vl = null;	//売価単価(税込)
	private String hachutani_qt = null;	//発注単位
	private String hatyu_dt = null;	//発注日
	private String nohin_dt = null;	//納品日
	private String hanbai_st_dt = null;	//販売開始日
	private String hanbai_ed_dt = null;	//販売終了日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String bumon_cd = null;	//部門コード
	private String checked = null;	
	private String anbun_rt = null;	
	
//	↓↓仕様変更（2005.07.07）↓↓
	private String sentaku = null;			//処理選択
//	↑↑仕様変更（2005.07.07）↑↑


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
	 * mst270101_SyokaiDonyuJyukyoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst270101_SyokaiDonyuJyukyoBean getmst270101_SyokaiDonyuJyukyoBean(DataHolder dataHolder)
	{
		mst270101_SyokaiDonyuJyukyoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst270101_SyokaiDonyuJyukyoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst270101_SyokaiDonyuJyukyoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

//	bumon_cdに対するセッターとゲッターの集合
	 public boolean setBumonCd(String bumon_cd)
	 {
		 this.bumon_cd = bumon_cd;
		 return true;
	 }
	 public String getBumonCd()
	 {
		 return this.bumon_cd;
	 }

//		checkedに対するセッターとゲッターの集合
	 public boolean setChecked(String checked)
	 {
		 this.checked = checked;
		 return true;
	 }
	 public String getChecked()
	 {
		 return this.checked;
	 }
	
//		hanbai_st_dtに対するセッターとゲッターの集合
	 public boolean setHanbaiStDt(String hanbai_st_dt)
	 {
		 this.hanbai_st_dt = hanbai_st_dt;
		 return true;
	 }
	 public String getHanbaiStDt()
	 {
		 return cutString(this.hanbai_st_dt,HANBAI_ST_DT_LENGTH);
	 }
	 public String getHanbaiStDtString()
	 {
		 return "'" + cutString(this.hanbai_st_dt,HANBAI_ST_DT_LENGTH) + "'";
	 }
	 public String getHanbaiStDtHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hanbai_st_dt,HANBAI_ST_DT_LENGTH));
	 }	
	 
//		hanbai_ed_dtに対するセッターとゲッターの集合
	 public boolean setHanbaiEdDt(String hanbai_ed_dt)
	 {
		 this.hanbai_ed_dt = hanbai_ed_dt;
		 return true;
	 }
	 public String getHanbaiEdDt()
	 {
		 return cutString(this.hanbai_ed_dt,HANBAI_ED_DT_LENGTH);
	 }
	 public String getHanbaiEdDtString()
	 {
		 return "'" + cutString(this.hanbai_ed_dt,HANBAI_ED_DT_LENGTH) + "'";
	 }
	 public String getHanbaiEdDtHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hanbai_ed_dt,HANBAI_ED_DT_LENGTH));
	 }	
	
//	2006/03/01 kim START
//	auto_hachu_kbに対するセッターとゲッターの集合
	 public boolean setAutoHachuKb(String auto_hachu_kb)
	 {
		 this.auto_hachu_kb = auto_hachu_kb;
		 return true;
	 }
	 public String getAutoHachuKb()
	 {
		 return cutString(this.auto_hachu_kb,AUTO_HACHU_KB_MAX_LENGTH);
	 }
	 public String getAutoHachuKbString()
	 {
		 return "'" + cutString(this.auto_hachu_kb,AUTO_HACHU_KB_MAX_LENGTH) + "'";
	 }
	 public String getAutoDelKbHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.auto_hachu_kb,AUTO_HACHU_KB_MAX_LENGTH));
	 }	

//	2006/03/01 kim END	


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

//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	// siiresaki_cdに対するセッターとゲッターの集合
//	public boolean setSiiresakiCd(String siiresaki_cd)
//	{
//		this.siiresaki_cd = siiresaki_cd;
//		return true;
//	}
//	public String getSiiresakiCd()
//	{
//		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
//	}
//	public String getSiiresakiCdString()
//	{
//		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
//	}
//	public String getSiiresakiCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
//	}

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	// hachuno_cdに対するセッターとゲッターの集合
	public boolean setHachunoCd(String hachuno_cd)
	{
		this.hachuno_cd = hachuno_cd;
		return true;
	}
	public String getHachunoCd()
	{
		return cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH);
	}
	public String getHachunoCdString()
	{
		return "'" + cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH) + "'";
	}
	public String getHachunoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH));
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


	// theme_cdに対するセッターとゲッターの集合
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
	public String getThemeCd()
	{
		return cutString(this.theme_cd,THEME_CD_MAX_LENGTH);
	}
	public String getThemeCdString()
	{
		return "'" + cutString(this.theme_cd,THEME_CD_MAX_LENGTH) + "'";
	}
	public String getThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_cd,THEME_CD_MAX_LENGTH));
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

	// tenpo_nmに対するセッターとゲッターの集合
	public boolean setTenpoNm(String tenpo_nm)
	{
		this.tenpo_nm = tenpo_nm;
		return true;
	}
	public String getTenpoNm()
	{
		return cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH);
	}
	public String getTenpoNmString()
	{
		return "'" + cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH) + "'";
	}
	public String getTenpoNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH));
	}

	// suryo_qtに対するセッターとゲッターの集合
	public boolean setSuryoQt(String suryo_qt)
	{
		this.suryo_qt = suryo_qt;
		return true;
	}
	public String getSuryoQt()
	{
		return this.suryo_qt;
	}
	public String getSuryoQtHTMLString()
	{
		String suryo = this.suryo_qt;
		if(suryo == null || suryo.equals("")){
			return "";
		}
	
		suryo = mst000401_LogicBean.addComma(suryo);

		return HTMLStringUtil.convert(suryo);
	}

	// suryo_qt_oldに対するセッターとゲッターの集合
	public boolean setSuryoQtOld(String suryo_qt_old)
	{
		this.suryo_qt_old = suryo_qt_old;
		return true;
	}
	public String getSuryoQtOld()
	{
		return this.suryo_qt_old;
	}
	public String getSuryoQtOldHTMLString()
	{
		String suryo_old = this.suryo_qt_old;
		if(suryo_old == null || suryo_old.equals("")){
			return "";
		}
	
		suryo_old = mst000401_LogicBean.addComma(suryo_old);

		return HTMLStringUtil.convert(suryo_old);
	}

	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
		//Stringでデータを受け取っているので
		//少数点が先頭の場合、0を付加する
		String val = gentanka_vl;
		
		if(val != null && !val.equals("")){
			if(val.substring(0,1).equals(".")){
				val = "0" + val;
			}
			
			if(val.indexOf(".") == -1){
				val = val + ".00";
			}
			
		}

		this.gentanka_vl = val;
		
		return true;
	}
	public String getGentankaVl()
	{
		return this.gentanka_vl;
	}
	public String getGentankaVlHTMLString()
	{
		String genka = this.gentanka_vl;
		if(genka == null || genka.equals("")){
			return "";
		}
	
		genka = mst000401_LogicBean.addCommaDecimal(genka,mst000101_ConstDictionary.FUNCTION_PARAM_0,"2");

		return HTMLStringUtil.convert(genka);
	}


	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlHTMLString()
	{
		String baitank = this.baitanka_vl;
		if(baitank == null || baitank.equals("")){
			return "";
		}
	
		baitank = mst000401_LogicBean.addComma(baitank);

		return HTMLStringUtil.convert(baitank);
	}




	// hachutani_qtに対するセッターとゲッターの集合
	public boolean setHachutaniQt(String hachutani_qt)
	{
		//Stringでデータを受け取っているので
		//少数点が先頭の場合、0を付加する
		String val = hachutani_qt;
		
		if(val != null && !val.equals("")){
			if(val.substring(0,1).equals(".")){
				val = "0" + val;
			}
			
			if(val.indexOf(".") == -1){
				val = val + ".0";
			}
			
		}

		this.hachutani_qt = val;
		
		return true;
	}
	public String getHachutaniQt()
	{
		return this.hachutani_qt;
	}
	public String getHachutaniQtHTMLString()
	{
		String hachutani = this.hachutani_qt;
		if(hachutani == null || hachutani.equals("")){
			return "";
		}
	
		hachutani = mst000401_LogicBean.addCommaDecimal(hachutani,mst000101_ConstDictionary.FUNCTION_PARAM_0,"1");

		return HTMLStringUtil.convert(hachutani);
	}



	// hatyu_dtに対するセッターとゲッターの集合
	public boolean setHatyuDt(String hatyu_dt)
	{
		this.hatyu_dt = hatyu_dt;
		return true;
	}
	public String getHatyuDt()
	{
		return cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH);
	}
	public String getHatyuDtString()
	{
		return "'" + cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH) + "'";
	}
	public String getHatyuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH));
	}


	// nohin_dtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohin_dt)
	{
		this.nohin_dt = nohin_dt;
		return true;
	}
	public String getNohinDt()
	{
		return cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		return "'" + cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH));
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
	
//	↓↓仕様変更（2005.07.12）↓↓
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
//	↑↑仕様変更（2005.07.12）↑↑
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		+ "  siiresaki_cd = " + getSiiresakiCdString()
//		****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
		return  "  syohin_cd = " + getSyohinCdString()
			+ "  hachuno_cd = " + getHachunoCdString()
			+ "  theme_cd = " + getThemeCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_nm = " + getTenpoNmString()
			+ "  suryo_qt = " + getSuryoQt()
			+ "  suryo_qt_old = " + getSuryoQtOld()
			+ "  gentanka_vl = " + getGentankaVl()
			+ "  baitanka_vl = " + getBaitankaVl()
			+ "  hachutani_qt = " + getHachutaniQt()
			+ "  hatyu_dt = " + getHatyuDtString()
			+ "  nohin_dt = " + getNohinDtString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  hanbai_ed_dt = " + getHanbaiEdDtString()
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
	 * @return mst270101_SyokaiDonyuJyukyoBean コピー後のクラス
	 */
	public mst270101_SyokaiDonyuJyukyoBean createClone()
	{
		mst270101_SyokaiDonyuJyukyoBean bean = new mst270101_SyokaiDonyuJyukyoBean();
//		bean.setHankuCd(this.hanku_cd);
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************		
//		bean.setSiiresakiCd(this.siiresaki_cd);
//		****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
		bean.setHachunoCd(this.hachuno_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setThemeCd(this.theme_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNm(this.tenpo_nm);
		bean.setSuryoQt(this.suryo_qt);
		bean.setSuryoQtOld(this.suryo_qt_old);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setHachutaniQt(this.hachutani_qt);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setHatyuDt(this.hatyu_dt);
		bean.setNohinDt(this.nohin_dt);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
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
		if( !( o instanceof mst270101_SyokaiDonyuJyukyoBean ) ) return false;
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
	public String getAnbun_rt() {
		return anbun_rt;
	}
	public void setAnbun_rt(String anbun_rt) {
		this.anbun_rt = anbun_rt;
	}
}
