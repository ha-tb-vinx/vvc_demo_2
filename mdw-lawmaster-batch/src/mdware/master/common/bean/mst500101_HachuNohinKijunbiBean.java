/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発注納品基準日マスタ（生鮮以外）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する発注納品基準日マスタ（生鮮以外）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/07)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
//import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発注納品基準日マスタ（生鮮以外）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する発注納品基準日マスタ（生鮮以外）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/07)初版作成
 */
public class mst500101_HachuNohinKijunbiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分 (FK)の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コード (FK)の長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コード (FK)の長さ
	public static final int HACHU_HOHO_KB_MAX_LENGTH = 1;//発注方法区分の長さ
	public static final int HACHU_SIME_TIME_KB_MAX_LENGTH = 1;//発注〆時間区分の長さ
	public static final int HACHU_MON_FG_MAX_LENGTH = 1;//発注可否フラグ-月の長さ
	public static final int HACHU_TUE_FG_MAX_LENGTH = 1;//発注可否フラグ-火の長さ
	public static final int HACHU_WED_FG_MAX_LENGTH = 1;//発注可否フラグ-水の長さ
	public static final int HACHU_THU_FG_MAX_LENGTH = 1;//発注可否フラグ-木の長さ
	public static final int HACHU_FRI_FG_MAX_LENGTH = 1;//発注可否フラグ-金の長さ
	public static final int HACHU_SAT_FG_MAX_LENGTH = 1;//発注可否フラグ-土の長さ
	public static final int HACHU_SUN_FG_MAX_LENGTH = 1;//発注可否フラグ-日の長さ
	public static final int RTIME_MON_KB_MAX_LENGTH = 1;//リードタイム-月の長さ
	public static final int RTIME_TUE_KB_MAX_LENGTH = 1;//リードタイム-火の長さ
	public static final int RTIME_WED_KB_MAX_LENGTH = 1;//リードタイム-水の長さ
	public static final int RTIME_THU_KB_MAX_LENGTH = 1;//リードタイム-木の長さ
	public static final int RTIME_FRI_KB_MAX_LENGTH = 1;//リードタイム-金の長さ
	public static final int RTIME_SAT_KB_MAX_LENGTH = 1;//リードタイム-土の長さ
	public static final int RTIME_SUN_KB_MAX_LENGTH = 1;//リードタイム-日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
//	↓↓仕様変更（2005.07.28）↓↓
	public static final int SYOKAI_TOROKU_TS_MAX_LENGTH = 20;//初回登録日の長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 10;//初回登録者IDの長さ
//	↑↑仕様変更（2005.07.28）↑↑


	private String yuko_dt = null;	//有効日
	private String kanri_kb = null;	//管理区分 (FK)
	private String kanri_cd = null;	//管理コード (FK)
	private String siiresaki_cd = null;	//仕入先コード (FK)
	private String hachu_hoho_kb = null;	//発注方法区分
	private String hachu_sime_time_kb = null;	//発注〆時間区分
	private String hachu_mon_fg = null;	//発注可否フラグ-月
	private String hachu_tue_fg = null;	//発注可否フラグ-火
	private String hachu_wed_fg = null;	//発注可否フラグ-水
	private String hachu_thu_fg = null;	//発注可否フラグ-木
	private String hachu_fri_fg = null;	//発注可否フラグ-金
	private String hachu_sat_fg = null;	//発注可否フラグ-土
	private String hachu_sun_fg = null;	//発注可否フラグ-日
	private String rtime_mon_kb = null;	//リードタイム-月
	private String rtime_tue_kb = null;	//リードタイム-火
	private String rtime_wed_kb = null;	//リードタイム-水
	private String rtime_thu_kb = null;	//リードタイム-木
	private String rtime_fri_kb = null;	//リードタイム-金
	private String rtime_sat_kb = null;	//リードタイム-土
	private String rtime_sun_kb = null;	//リードタイム-日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
//	↓↓仕様変更（2005.07.28）↓↓
	private String syokai_toroku_ts = null;	//初回登録日
	private String syokai_user_id = null;	//初回登録者ID
//	↑↑仕様変更（2005.07.28）↑↑

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
	 * RHachunohinkijunbiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst500101_HachuNohinKijunbiBean getRHachunohinkijunbiBean(DataHolder dataHolder) {
	//public static RHachunohinkijunbiBean getRHachunohinkijunbiBean(DataHolder dataHolder)
		//RHachunohinkijunbiBean bean = null;
		//BeanHolder beanHolder = new BeanHolder( new RHachunohinkijunbiDM() );
		mst500101_HachuNohinKijunbiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst500101_HachuNohinKijunbiDM() );
		try {
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() ) {
				//bean = (RHachunohinkijunbiBean )ite.next();
				bean = (mst500101_HachuNohinKijunbiBean )ite.next();
			}

			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() ) {
				bean = null;
			}
		} catch(Exception e) {
			;
		}
		return bean;
	}




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


	// hachu_hoho_kbに対するセッターとゲッターの集合
	public boolean setHachuHohoKb(String hachu_hoho_kb)
	{
		this.hachu_hoho_kb = hachu_hoho_kb;
		return true;
	}
	public String getHachuHohoKb()
	{
		return cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH);
	}
	public String getHachuHohoKbString()
	{
		return "'" + cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH) + "'";
	}
	public String getHachuHohoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_hoho_kb,HACHU_HOHO_KB_MAX_LENGTH));
	}


	// hachu_sime_time_kbに対するセッターとゲッターの集合
	public boolean setHachuSimeTimeKb(String hachu_sime_time_kb)
	{
		this.hachu_sime_time_kb = hachu_sime_time_kb;
		return true;
	}
	public String getHachuSimeTimeKb()
	{
		return cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH);
	}
	public String getHachuSimeTimeKbString()
	{
		return "'" + cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH) + "'";
	}
	public String getHachuSimeTimeKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sime_time_kb,HACHU_SIME_TIME_KB_MAX_LENGTH));
	}


	// hachu_mon_fgに対するセッターとゲッターの集合
	public boolean setHachuMonFg(String hachu_mon_fg)
	{
		this.hachu_mon_fg = hachu_mon_fg;
		return true;
	}
	public String getHachuMonFg()
	{
		return cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH);
	}
	public String getHachuMonFgString()
	{
		return "'" + cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH) + "'";
	}
	public String getHachuMonFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_mon_fg,HACHU_MON_FG_MAX_LENGTH));
	}


	// hachu_tue_fgに対するセッターとゲッターの集合
	public boolean setHachuTueFg(String hachu_tue_fg)
	{
		this.hachu_tue_fg = hachu_tue_fg;
		return true;
	}
	public String getHachuTueFg()
	{
		return cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH);
	}
	public String getHachuTueFgString()
	{
		return "'" + cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH) + "'";
	}
	public String getHachuTueFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tue_fg,HACHU_TUE_FG_MAX_LENGTH));
	}


	// hachu_wed_fgに対するセッターとゲッターの集合
	public boolean setHachuWedFg(String hachu_wed_fg)
	{
		this.hachu_wed_fg = hachu_wed_fg;
		return true;
	}
	public String getHachuWedFg()
	{
		return cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH);
	}
	public String getHachuWedFgString()
	{
		return "'" + cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH) + "'";
	}
	public String getHachuWedFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_wed_fg,HACHU_WED_FG_MAX_LENGTH));
	}


	// hachu_thu_fgに対するセッターとゲッターの集合
	public boolean setHachuThuFg(String hachu_thu_fg)
	{
		this.hachu_thu_fg = hachu_thu_fg;
		return true;
	}
	public String getHachuThuFg()
	{
		return cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH);
	}
	public String getHachuThuFgString()
	{
		return "'" + cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH) + "'";
	}
	public String getHachuThuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_thu_fg,HACHU_THU_FG_MAX_LENGTH));
	}


	// hachu_fri_fgに対するセッターとゲッターの集合
	public boolean setHachuFriFg(String hachu_fri_fg)
	{
		this.hachu_fri_fg = hachu_fri_fg;
		return true;
	}
	public String getHachuFriFg()
	{
		return cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH);
	}
	public String getHachuFriFgString()
	{
		return "'" + cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH) + "'";
	}
	public String getHachuFriFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_fri_fg,HACHU_FRI_FG_MAX_LENGTH));
	}


	// hachu_sat_fgに対するセッターとゲッターの集合
	public boolean setHachuSatFg(String hachu_sat_fg)
	{
		this.hachu_sat_fg = hachu_sat_fg;
		return true;
	}
	public String getHachuSatFg()
	{
		return cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH);
	}
	public String getHachuSatFgString()
	{
		return "'" + cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH) + "'";
	}
	public String getHachuSatFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sat_fg,HACHU_SAT_FG_MAX_LENGTH));
	}


	// hachu_sun_fgに対するセッターとゲッターの集合
	public boolean setHachuSunFg(String hachu_sun_fg)
	{
		this.hachu_sun_fg = hachu_sun_fg;
		return true;
	}
	public String getHachuSunFg()
	{
		return cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH);
	}
	public String getHachuSunFgString()
	{
		return "'" + cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH) + "'";
	}
	public String getHachuSunFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_sun_fg,HACHU_SUN_FG_MAX_LENGTH));
	}


	// rtime_mon_kbに対するセッターとゲッターの集合
	public boolean setRtimeMonKb(String rtime_mon_kb)
	{
		this.rtime_mon_kb = rtime_mon_kb;
		return true;
	}
	public String getRtimeMonKb()
	{
		return cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH);
	}
	public String getRtimeMonKbString()
	{
		return "'" + cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeMonKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_mon_kb,RTIME_MON_KB_MAX_LENGTH));
	}


	// rtime_tue_kbに対するセッターとゲッターの集合
	public boolean setRtimeTueKb(String rtime_tue_kb)
	{
		this.rtime_tue_kb = rtime_tue_kb;
		return true;
	}
	public String getRtimeTueKb()
	{
		return cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH);
	}
	public String getRtimeTueKbString()
	{
		return "'" + cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeTueKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_tue_kb,RTIME_TUE_KB_MAX_LENGTH));
	}


	// rtime_wed_kbに対するセッターとゲッターの集合
	public boolean setRtimeWedKb(String rtime_wed_kb)
	{
		this.rtime_wed_kb = rtime_wed_kb;
		return true;
	}
	public String getRtimeWedKb()
	{
		return cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH);
	}
	public String getRtimeWedKbString()
	{
		return "'" + cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeWedKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_wed_kb,RTIME_WED_KB_MAX_LENGTH));
	}


	// rtime_thu_kbに対するセッターとゲッターの集合
	public boolean setRtimeThuKb(String rtime_thu_kb)
	{
		this.rtime_thu_kb = rtime_thu_kb;
		return true;
	}
	public String getRtimeThuKb()
	{
		return cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH);
	}
	public String getRtimeThuKbString()
	{
		return "'" + cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeThuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_thu_kb,RTIME_THU_KB_MAX_LENGTH));
	}


	// rtime_fri_kbに対するセッターとゲッターの集合
	public boolean setRtimeFriKb(String rtime_fri_kb)
	{
		this.rtime_fri_kb = rtime_fri_kb;
		return true;
	}
	public String getRtimeFriKb()
	{
		return cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH);
	}
	public String getRtimeFriKbString()
	{
		return "'" + cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeFriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_fri_kb,RTIME_FRI_KB_MAX_LENGTH));
	}


	// rtime_sat_kbに対するセッターとゲッターの集合
	public boolean setRtimeSatKb(String rtime_sat_kb)
	{
		this.rtime_sat_kb = rtime_sat_kb;
		return true;
	}
	public String getRtimeSatKb()
	{
		return cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH);
	}
	public String getRtimeSatKbString()
	{
		return "'" + cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeSatKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_sat_kb,RTIME_SAT_KB_MAX_LENGTH));
	}


	// rtime_sun_kbに対するセッターとゲッターの集合
	public boolean setRtimeSunKb(String rtime_sun_kb)
	{
		this.rtime_sun_kb = rtime_sun_kb;
		return true;
	}
	public String getRtimeSunKb()
	{
		return cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH);
	}
	public String getRtimeSunKbString()
	{
		return "'" + cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH) + "'";
	}
	public String getRtimeSunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rtime_sun_kb,RTIME_SUN_KB_MAX_LENGTH));
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
	
	
//	↓↓仕様変更（2005.07.28）↓↓
	// syokai_toroku_tsに対するセッターとゲッターの集合
	public boolean setSyokaiTorokuTs(String syokai_toroku_ts)
	{
		this.syokai_toroku_ts = syokai_toroku_ts;
		return true;
	}
	public String getSyokaiTorokuTs()
	{
		return cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH);
	}
	public String getSyokaiTorokuTsString()
	{
		return "'" + cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH) + "'";
	}
	public String getSyokaiTorokuTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_toroku_ts,SYOKAI_TOROKU_TS_MAX_LENGTH));
	}


	// syokai_user_idに対するセッターとゲッターの集合
	public boolean setSyokaiUserId(String syokai_user_id)
	{
		this.syokai_user_id = syokai_user_id;
		return true;
	}
	public String getSyokaiUserId()
	{
		return cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getSyokaiUserIdString()
	{
		return "'" + cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}
//	↑↑仕様変更（2005.07.28）↑↑
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  yuko_dt = " + getYukoDtString()
			+ "  kanri_kb = " + getKanriKbString()
			+ "  kanri_cd = " + getKanriCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  hachu_hoho_kb = " + getHachuHohoKbString()
			+ "  hachu_sime_time_kb = " + getHachuSimeTimeKbString()
			+ "  hachu_mon_fg = " + getHachuMonFgString()
			+ "  hachu_tue_fg = " + getHachuTueFgString()
			+ "  hachu_wed_fg = " + getHachuWedFgString()
			+ "  hachu_thu_fg = " + getHachuThuFgString()
			+ "  hachu_fri_fg = " + getHachuFriFgString()
			+ "  hachu_sat_fg = " + getHachuSatFgString()
			+ "  hachu_sun_fg = " + getHachuSunFgString()
			+ "  rtime_mon_kb = " + getRtimeMonKbString()
			+ "  rtime_tue_kb = " + getRtimeTueKbString()
			+ "  rtime_wed_kb = " + getRtimeWedKbString()
			+ "  rtime_thu_kb = " + getRtimeThuKbString()
			+ "  rtime_fri_kb = " + getRtimeFriKbString()
			+ "  rtime_sat_kb = " + getRtimeSatKbString()
			+ "  rtime_sun_kb = " + getRtimeSunKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
//			↓↓仕様変更（2005.07.28）↓↓
			+ "  syokai_toroku_ts = " + getSyokaiTorokuTs()
			+ "  syokai_user_id = " + getSyokaiUserId()
//			↑↑仕様変更（2005.07.28）↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RHachunohinkijunbiBean コピー後のクラス
	 */
	public mst500101_HachuNohinKijunbiBean createClone()
	{

		//RHachunohinkijunbiBean bean = new RHachunohinkijunbiBean();
		mst500101_HachuNohinKijunbiBean bean = new mst500101_HachuNohinKijunbiBean();

		bean.setYukoDt(this.yuko_dt);
		bean.setKanriKb(this.kanri_kb);
		bean.setKanriCd(this.kanri_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setHachuHohoKb(this.hachu_hoho_kb);
		bean.setHachuSimeTimeKb(this.hachu_sime_time_kb);
		bean.setHachuMonFg(this.hachu_mon_fg);
		bean.setHachuTueFg(this.hachu_tue_fg);
		bean.setHachuWedFg(this.hachu_wed_fg);
		bean.setHachuThuFg(this.hachu_thu_fg);
		bean.setHachuFriFg(this.hachu_fri_fg);
		bean.setHachuSatFg(this.hachu_sat_fg);
		bean.setHachuSunFg(this.hachu_sun_fg);
		bean.setRtimeMonKb(this.rtime_mon_kb);
		bean.setRtimeTueKb(this.rtime_tue_kb);
		bean.setRtimeWedKb(this.rtime_wed_kb);
		bean.setRtimeThuKb(this.rtime_thu_kb);
		bean.setRtimeFriKb(this.rtime_fri_kb);
		bean.setRtimeSatKb(this.rtime_sat_kb);
		bean.setRtimeSunKb(this.rtime_sun_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様変更（2005.07.28）↓↓
		bean.setSyokaiTorokuTs(this.syokai_toroku_ts);
		bean.setSyokaiUserId(this.syokai_user_id);
//		↑↑仕様変更（2005.07.28）↑↑
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
		//if( !( o instanceof RHachunohinkijunbiBean ) ) return false;
		if( !( o instanceof mst500101_HachuNohinKijunbiBean)) return false;
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
