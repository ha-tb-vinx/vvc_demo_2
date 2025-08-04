/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BMコードマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBMコードマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/19)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BMコードマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するBMコードマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/19)初版作成
 */
public class mst740101_BMCodeBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUNDLEMIX_CD_MAX_LENGTH = 4;			//BMコードの長さ
	public static final int NAME_NA_MAX_LENGTH = 20;				//漢字名称の長さ
	public static final int NAME_KA_MAX_LENGTH = 20;				//半角カナ名称の長さ
	public static final int BUNDLEMIX_ST_DT_MAX_LENGTH = 8;		//開始日の長さ
	public static final int BUNDLEMIX_ED_DT_MAX_LENGTH = 8;		//終了日の長さ
	public static final int BUNDLEMIX_STTIME_QT_MAX_LENGTH = 4;	//開始時間の長さ
	public static final int BUNDLEMIX_EDTIME_QT_MAX_LENGTH = 4;	//終了時間の長さ
	public static final int JISI_MON_FG_MAX_LENGTH = 1;			//月曜日実施フラグの長さ
	public static final int JISI_TUE_FG_MAX_LENGTH = 1;			//火曜日実施フラグの長さ
	public static final int JISI_WED_FG_MAX_LENGTH = 1;			//水曜日実施フラグの長さ
	public static final int JISI_THU_FG_MAX_LENGTH = 1;			//木曜日実施フラグの長さ
	public static final int JISI_FRI_FG_MAX_LENGTH = 1;			//金曜日実施フラグの長さ
	public static final int JISI_SAT_FG_MAX_LENGTH = 1;			//土曜日実施フラグの長さ
	public static final int JISI_SUN_FG_MAX_LENGTH = 1;			//日曜日実施フラグの長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;		//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;				//削除フラグの長さ
	public static final int INSERT_USER_NM_LENGTH = 20;			//作成者名称の長さ
	public static final int UPDATE_USER_NM_LENGTH = 20;			//更新者名称の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択の長さ
	//A.Tozaki
	public static final int SINKI_TOROKU_DT_MAX_LENGTH = 8;		//登録日の長さ
	public static final int HENKO_DT_MAX_LENGTH = 8;				//変更日の長さ
	
		
	private String bundlemix_cd = null;							//BMコード
	private String name_na = null;									//漢字名称
	private String name_ka = null;									//半角カナ名称
	private String bundlemix_st_dt = null;							//開始日
	private String bundlemix_ed_dt = null;							//終了日
	private String bundlemix_sttime_qt = null;						//開始時間
	private String bundlemix_edtime_qt = null;						//終了時間
	private long seiritu_kosu_qt = 0;								//成立個数
	private long seiritu_kingaku_vl = 0;							//成立金額
	private long tanka_vl = 0;										//一点単価
	private String jisi_mon_fg = null;								//月曜日実施フラグ
	private String jisi_tue_fg = null;								//火曜日実施フラグ
	private String jisi_wed_fg = null;								//水曜日実施フラグ
	private String jisi_thu_fg = null;								//木曜日実施フラグ
	private String jisi_fri_fg = null;								//金曜日実施フラグ
	private String jisi_sat_fg = null;								//土曜日実施フラグ
	private String jisi_sun_fg = null;								//日曜日実施フラグ
	private String insert_ts = null;								//作成年月日
	private String insert_user_id = null;							//作成者社員ID
	private String update_ts = null;								//更新年月日
	private String update_user_id = null;							//更新者社員ID
	private String delete_fg = null;								//削除フラグ
	private String insert_user_nm = null;							//作成者名称
	private String update_user_nm = null;							//更新者名称

	//A.Tozaki
	private String sinki_toroku_dt = null;						//登録日
	private String henko_dt = null;								//変更日
	
	
	private String sentaku = null;									//処理選択
	
	
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
	 * mst740101_BMCodeBeanを１件のみ抽出したい時に使用する
	 */
	public static mst740101_BMCodeBean getmst740101_BMCodeBean(DataHolder dataHolder)
	{
		mst740101_BMCodeBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst740101_BMCodeDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst740101_BMCodeBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	// bundlemix_cdに対するセッターとゲッターの集合
	public boolean setBundlemixCd(String bundlemix_cd)
	{
		this.bundlemix_cd = bundlemix_cd;
		return true;
	}
	public String getBundlemixCd()
	{
		return cutString(this.bundlemix_cd,BUNDLEMIX_CD_MAX_LENGTH);
	}
	public String getBundlemixCdString()
	{
		return "'" + cutString(this.bundlemix_cd,BUNDLEMIX_CD_MAX_LENGTH) + "'";
	}
	public String getBundlemixCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bundlemix_cd,BUNDLEMIX_CD_MAX_LENGTH));
	}


	// name_naに対するセッターとゲッターの集合
	public boolean setNameNa(String name_na)
	{
		this.name_na = name_na;
		return true;
	}
	public String getNameNa()
	{
		return cutString(this.name_na,NAME_NA_MAX_LENGTH);
	}
	public String getNameNaString()
	{
		return "'" + cutString(this.name_na,NAME_NA_MAX_LENGTH) + "'";
	}
	public String getNameNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.name_na,NAME_NA_MAX_LENGTH));
	}


	// name_kaに対するセッターとゲッターの集合
	public boolean setNameKa(String name_ka)
	{
		this.name_ka = name_ka;
		return true;
	}
	public String getNameKa()
	{
		return cutString(this.name_ka,NAME_KA_MAX_LENGTH);
	}
	public String getNameKaString()
	{
		return "'" + cutString(this.name_ka,NAME_KA_MAX_LENGTH) + "'";
	}
	public String getNameKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.name_ka,NAME_KA_MAX_LENGTH));
	}


	// bundlemix_st_dtに対するセッターとゲッターの集合
	public boolean setBundlemixStDt(String bundlemix_st_dt)
	{
		this.bundlemix_st_dt = bundlemix_st_dt;
		return true;
	}
	public String getBundlemixStDt()
	{
		return cutString(this.bundlemix_st_dt,BUNDLEMIX_ST_DT_MAX_LENGTH);
	}
	public String getBundlemixStDtString()
	{
		return "'" + cutString(this.bundlemix_st_dt,BUNDLEMIX_ST_DT_MAX_LENGTH) + "'";
	}
	public String getBundlemixStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bundlemix_st_dt,BUNDLEMIX_ST_DT_MAX_LENGTH));
	}


	// bundlemix_ed_dtに対するセッターとゲッターの集合
	public boolean setBundlemixEdDt(String bundlemix_ed_dt)
	{
		this.bundlemix_ed_dt = bundlemix_ed_dt;
		return true;
	}
	public String getBundlemixEdDt()
	{
		return cutString(this.bundlemix_ed_dt,BUNDLEMIX_ED_DT_MAX_LENGTH);
	}
	public String getBundlemixEdDtString()
	{
		return "'" + cutString(this.bundlemix_ed_dt,BUNDLEMIX_ED_DT_MAX_LENGTH) + "'";
	}
	public String getBundlemixEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bundlemix_ed_dt,BUNDLEMIX_ED_DT_MAX_LENGTH));
	}


	// bundlemix_sttime_qtに対するセッターとゲッターの集合
	public boolean setBundlemixSttimeQt(String bundlemix_sttime_qt)
	{
		this.bundlemix_sttime_qt = bundlemix_sttime_qt;
		return true;
	}
	public String getBundlemixSttimeQt()
	{
		return cutString(this.bundlemix_sttime_qt,BUNDLEMIX_STTIME_QT_MAX_LENGTH);
	}
	public String getBundlemixSttimeQtString()
	{
		return "'" + cutString(this.bundlemix_sttime_qt,BUNDLEMIX_STTIME_QT_MAX_LENGTH) + "'";
	}
	public String getBundlemixSttimeQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bundlemix_sttime_qt,BUNDLEMIX_STTIME_QT_MAX_LENGTH));
	}


	// bundlemix_edtime_qtに対するセッターとゲッターの集合
	public boolean setBundlemixEdtimeQt(String bundlemix_edtime_qt)
	{
		this.bundlemix_edtime_qt = bundlemix_edtime_qt;
		return true;
	}
	public String getBundlemixEdtimeQt()
	{
		return cutString(this.bundlemix_edtime_qt,BUNDLEMIX_EDTIME_QT_MAX_LENGTH);
	}
	public String getBundlemixEdtimeQtString()
	{
		return "'" + cutString(this.bundlemix_edtime_qt,BUNDLEMIX_EDTIME_QT_MAX_LENGTH) + "'";
	}
	public String getBundlemixEdtimeQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bundlemix_edtime_qt,BUNDLEMIX_EDTIME_QT_MAX_LENGTH));
	}


	// seiritu_kosu_qtに対するセッターとゲッターの集合
	public boolean setSeirituKosuQt(String seiritu_kosu_qt)
	{
		try
		{
			this.seiritu_kosu_qt = Long.parseLong(seiritu_kosu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSeirituKosuQt(long seiritu_kosu_qt)
	{
		this.seiritu_kosu_qt = seiritu_kosu_qt;
		return true;
	}
	public long getSeirituKosuQt()
	{
		return this.seiritu_kosu_qt;
	}
	public String getSeirituKosuQtString()
	{
		return Long.toString(this.seiritu_kosu_qt);
	}


	// seiritu_kingaku_vlに対するセッターとゲッターの集合
	public boolean setSeirituKingakuVl(String seiritu_kingaku_vl)
	{
		try
		{   
		    this.seiritu_kingaku_vl = Long.parseLong(seiritu_kingaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSeirituKingakuVl(long seiritu_kingaku_vl)
	{
		this.seiritu_kingaku_vl = seiritu_kingaku_vl;
		return true;
	}
	public long getSeirituKingakuVl()
	{
		return this.seiritu_kingaku_vl;
	}
	public String getSeirituKingakuVlString()
	{
		return Long.toString(this.seiritu_kingaku_vl);
	}


	// tanka_vlに対するセッターとゲッターの集合
	public boolean setTankaVl(String tanka_vl)
	{
		try
		{
			this.tanka_vl = Long.parseLong(tanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTankaVl(long tanka_vl)
	{
		this.tanka_vl = tanka_vl;
		return true;
	}
	public long getTankaVl()
	{
		return this.tanka_vl;
	}
	public String getTankaVlString()
	{
		return Long.toString(this.tanka_vl);
	}


	// jisi_mon_fgに対するセッターとゲッターの集合
	public boolean setJisiMonFg(String jisi_mon_fg)
	{
		this.jisi_mon_fg = jisi_mon_fg;
		return true;
	}
	public String getJisiMonFg()
	{
		return cutString(this.jisi_mon_fg,JISI_MON_FG_MAX_LENGTH);
	}
	public String getJisiMonFgString()
	{
		return "'" + cutString(this.jisi_mon_fg,JISI_MON_FG_MAX_LENGTH) + "'";
	}
	public String getJisiMonFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_mon_fg,JISI_MON_FG_MAX_LENGTH));
	}


	// jisi_tue_fgに対するセッターとゲッターの集合
	public boolean setJisiTueFg(String jisi_tue_fg)
	{
		this.jisi_tue_fg = jisi_tue_fg;
		return true;
	}
	public String getJisiTueFg()
	{
		return cutString(this.jisi_tue_fg,JISI_TUE_FG_MAX_LENGTH);
	}
	public String getJisiTueFgString()
	{
		return "'" + cutString(this.jisi_tue_fg,JISI_TUE_FG_MAX_LENGTH) + "'";
	}
	public String getJisiTueFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_tue_fg,JISI_TUE_FG_MAX_LENGTH));
	}


	// jisi_wed_fgに対するセッターとゲッターの集合
	public boolean setJisiWedFg(String jisi_wed_fg)
	{
		this.jisi_wed_fg = jisi_wed_fg;
		return true;
	}
	public String getJisiWedFg()
	{
		return cutString(this.jisi_wed_fg,JISI_WED_FG_MAX_LENGTH);
	}
	public String getJisiWedFgString()
	{
		return "'" + cutString(this.jisi_wed_fg,JISI_WED_FG_MAX_LENGTH) + "'";
	}
	public String getJisiWedFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_wed_fg,JISI_WED_FG_MAX_LENGTH));
	}


	// jisi_thu_fgに対するセッターとゲッターの集合
	public boolean setJisiThuFg(String jisi_thu_fg)
	{
		this.jisi_thu_fg = jisi_thu_fg;
		return true;
	}
	public String getJisiThuFg()
	{
		return cutString(this.jisi_thu_fg,JISI_THU_FG_MAX_LENGTH);
	}
	public String getJisiThuFgString()
	{
		return "'" + cutString(this.jisi_thu_fg,JISI_THU_FG_MAX_LENGTH) + "'";
	}
	public String getJisiThuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_thu_fg,JISI_THU_FG_MAX_LENGTH));
	}


	// jisi_fri_fgに対するセッターとゲッターの集合
	public boolean setJisiFriFg(String jisi_fri_fg)
	{
		this.jisi_fri_fg = jisi_fri_fg;
		return true;
	}
	public String getJisiFriFg()
	{
		return cutString(this.jisi_fri_fg,JISI_FRI_FG_MAX_LENGTH);
	}
	public String getJisiFriFgString()
	{
		return "'" + cutString(this.jisi_fri_fg,JISI_FRI_FG_MAX_LENGTH) + "'";
	}
	public String getJisiFriFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_fri_fg,JISI_FRI_FG_MAX_LENGTH));
	}


	// jisi_sat_fgに対するセッターとゲッターの集合
	public boolean setJisiSatFg(String jisi_sat_fg)
	{
		this.jisi_sat_fg = jisi_sat_fg;
		return true;
	}
	public String getJisiSatFg()
	{
		return cutString(this.jisi_sat_fg,JISI_SAT_FG_MAX_LENGTH);
	}
	public String getJisiSatFgString()
	{
		return "'" + cutString(this.jisi_sat_fg,JISI_SAT_FG_MAX_LENGTH) + "'";
	}
	public String getJisiSatFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_sat_fg,JISI_SAT_FG_MAX_LENGTH));
	}


	// jisi_sun_fgに対するセッターとゲッターの集合
	public boolean setJisiSunFg(String jisi_sun_fg)
	{
		this.jisi_sun_fg = jisi_sun_fg;
		return true;
	}
	public String getJisiSunFg()
	{
		return cutString(this.jisi_sun_fg,JISI_SUN_FG_MAX_LENGTH);
	}
	public String getJisiSunFgString()
	{
		return "'" + cutString(this.jisi_sun_fg,JISI_SUN_FG_MAX_LENGTH) + "'";
	}
	public String getJisiSunFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisi_sun_fg,JISI_SUN_FG_MAX_LENGTH));
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
	
	
	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return cutString(this.insert_user_nm, INSERT_USER_NM_LENGTH);
	}
	public String getInsertUserNmString()
	{
		return "'" + cutString(this.insert_user_nm ,INSERT_USER_NM_LENGTH) + "'";
	}
	public String getInsertUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_nm, INSERT_USER_NM_LENGTH));
	}

	
	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return cutString(this.update_user_nm, UPDATE_USER_NM_LENGTH);
	}
	public String getUpdateUserNmString()
	{
		return "'" + cutString(this.update_user_nm ,UPDATE_USER_NM_LENGTH) + "'";
	}
	public String getUpdateUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_nm, UPDATE_USER_NM_LENGTH));
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


	//A.Tozaki
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
	
	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bundlemix_cd = " + getBundlemixCdString()
			+ "  name_na = " + getNameNaString()
			+ "  name_ka = " + getNameKaString()
			+ "  bundlemix_st_dt = " + getBundlemixStDtString()
			+ "  bundlemix_ed_dt = " + getBundlemixEdDtString()
			+ "  bundlemix_sttime_qt = " + getBundlemixSttimeQtString()
			+ "  bundlemix_edtime_qt = " + getBundlemixEdtimeQtString()
			+ "  seiritu_kosu_qt = " + getSeirituKosuQtString()
			+ "  seiritu_kingaku_vl = " + getSeirituKingakuVlString()
			+ "  tanka_vl = " + getTankaVlString()
			+ "  jisi_mon_fg = " + getJisiMonFgString()
			+ "  jisi_tue_fg = " + getJisiTueFgString()
			+ "  jisi_wed_fg = " + getJisiWedFgString()
			+ "  jisi_thu_fg = " + getJisiThuFgString()
			+ "  jisi_fri_fg = " + getJisiFriFgString()
			+ "  jisi_sat_fg = " + getJisiSatFgString()
			+ "  jisi_sun_fg = " + getJisiSunFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  insert_user_nm = " + getInsertUserNmString()
			+ "  update_user_nm = " + getUpdateUserNmString()

			//A.Tozaki
			+ "  sinki_toroku_dt = " + getSinkiTorokuDtString()
			+ "  henko_dt = " + getHenkoDtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RBUNDLEMIXCDBean コピー後のクラス
	 */
	public mst740101_BMCodeBean createClone()
	{
		mst740101_BMCodeBean bean = new mst740101_BMCodeBean();
		bean.setBundlemixCd(this.bundlemix_cd);
		bean.setNameNa(this.name_na);
		bean.setNameKa(this.name_ka);
		bean.setBundlemixStDt(this.bundlemix_st_dt);
		bean.setBundlemixEdDt(this.bundlemix_ed_dt);
		bean.setBundlemixSttimeQt(this.bundlemix_sttime_qt);
		bean.setBundlemixEdtimeQt(this.bundlemix_edtime_qt);
		bean.setSeirituKosuQt(this.seiritu_kosu_qt);
		bean.setSeirituKingakuVl(this.seiritu_kingaku_vl);
		bean.setTankaVl(this.tanka_vl);
		bean.setJisiMonFg(this.jisi_mon_fg);
		bean.setJisiTueFg(this.jisi_tue_fg);
		bean.setJisiWedFg(this.jisi_wed_fg);
		bean.setJisiThuFg(this.jisi_thu_fg);
		bean.setJisiFriFg(this.jisi_fri_fg);
		bean.setJisiSatFg(this.jisi_sat_fg);
		bean.setJisiSunFg(this.jisi_sun_fg);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setInsertUserNm(this.getInsertUserNm());
		bean.setUpdateUserNm(this.getUpdateUserNm());		
		bean.setDeleteFg(this.delete_fg);
		//A.Tozaki
		bean.setSinkiTorokuDt(this.sinki_toroku_dt);		
		bean.setHenkoDt(this.henko_dt);
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
		if( !( o instanceof mst740101_BMCodeBean ) ) return false;
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
