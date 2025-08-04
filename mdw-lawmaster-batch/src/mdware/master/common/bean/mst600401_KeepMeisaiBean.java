/**
 * <P>タイトル : 新ＭＤシステム　（マスター管理）
 *               自動廃番制御マスタ登録 mst600101_Jidohaiban 用
 *               自動廃番制御マスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する mst600101_Jidohaiban用計量器マスタのレコード格納用クラス(DmCreatorにより自動生成)</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author C.Sawabe
 * @version 1.0	(2005/04/13)	新規作成
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <P>タイトル : 新ＭＤシステム（マスター管理） mst600101_Jidohaiban用自動廃番制御マスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst600101_Jidohaiban用自動廃番制御マスタのレコード格納用クラス(DmCreatorにより自動生成)</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author C.Sawabe
 * @version 1.0	(2005/04/13)	新規作成
 * @see なし								
 */
public class mst600401_KeepMeisaiBean {
	
	private static final StcLog stcLog = StcLog.getInstance();
		
	//定数設定
	public static final int KANRI_KB_MAX_LENGTH	    = 1;	//管理区分
//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
	public static final int KANRI_CD_MAX_LENGTH	    = 4;	//管理コード
	public static final int KANRI_CD_FROM_MAX_LENGTH	= 4;	//管理コード(from)
	public static final int KANRI_CD_TO_MAX_LENGTH	= 4;	//管理コード(to)
	public static final int SENTAKU_MAX_LENGTH       = 1;    //処理選択	
	//明細行
//	public static final int HANKU_CD_MAX_LENGTH         = 4; //販区コード
//	public static final int HANKU_NA_MAX_LENGTH         = 20;//販区名称
//	public static final int SYO_GYOUSYU_CD_MAX_LENGTH   = 4; //小業種コード
//	public static final int SYO_GYOUSYU_NA_MAX_LENGTH   = 20;//小業種名称
	public static final int HINBAN_CD_MAX_LENGTH         = 4; //品番コード
	public static final int HINBAN_NA_MAX_LENGTH         = 20;//品番名称
	public static final int BUMON_CD_MAX_LENGTH   = 4; //部門コード
	public static final int BUMON_NA_MAX_LENGTH   = 20;//部門名称
//	public static final int TENPO_CD_MAX_LENGTH         = 6; //店舗コード (FK)
	public static final int TENPO_NA_MAX_LENGTH         = 20;//店舗名称
	public static final int CHECK_SYCLE_MAX_LENGTH      = 1; //チェックサイクル
	public static final int OUT_MONTHDAY_QT_MAX_LENGTH  = 2; //出力日(日)
	public static final int OUT_WEEKDAY_QT_MAX_LENGTH   = 1; //出力日(週曜日)
	public static final int OUT_DAY_QT_MAX_LENGTH       = 3; //廃番予定出力(N日前)
	public static final int DELDAYS_REMAIN_MAX_LENGTH   = 3; //削除余裕日
	//public static final int HACHU_TEISIYOTEI_MAX_LENGTH = 3;	//発注停止予定日
//	 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
	public static final int INSERT_TS_MAX_LENGTH        = 20;//作成年月日
	public static final int INSERT_USER_ID_MAX_LENGTH   = 10;//作成者社員ID
	public static final int INSERT_USER_NA_MAX_LENGTH   = 20;//作成者名
	public static final int UPDATE_TS_MAX_LENGTH        = 20;//更新年月日
	public static final int UPDATE_USER_ID_MAX_LENGTH   = 10;//更新者社員ID
	public static final int UPDATE_USER_NA_MAX_LENGTH   = 20;//更新者名
	public static final int DELETE_FG_MAX_LENGTH        = 1; //削除フラグ
	

	//定数設定
	private String kanri_kb      = null;		//管理区分
	private String kanri_cd      = null;		//管理コード
	private String kanri_cd_from = null;		//管理コード(from)
	private String kanri_cd_to   = null;		//管理コード(to)
	private String sentaku_flg   = null;    	//処理選択
	
	//明細行
//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//	private String hanku_cd      = null;    	//販区コード
//	private String hanku_na      = null;   	//販区名称
//	private String s_gyousyu_cd  = null;	    //小業種コード
//	private String s_gyousyu_na  = null;		//小業種名称
	private String hinban_cd      = null;    	//品番コード
	private String hinban_na      = null;   	//品番名称
	private String bumon_cd  = null;	    //部門コード
	private String bumon_na  = null;		//部門名称
//	private String tenpo_cd      = null;    	//店舗コード (FK)
//	private String tenpo_na      = null;   		//店舗名称
	private String check_sycle   = null;		//チェックサイクル
	private String o_weekday_qt  = null; 		//出力日(週曜日)
	private int o_monthday_qt = 0; 			//出力日(日)
	private int o_day_qt      = 0; 			//廃番予定出力(N日前)
	private String deldays_remain= null;		//削除余裕日
	//private String hachu_teisiyotei = null;	//発注停止予定日
//	 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
	private String insert_ts     = null;		//作成年月日(画面表示用)
	private String db_insert_ts  = null;		//作成年月日(DBチェック用)
	private String insert_user_id= null;		//作成者社員ID
	private String insert_user_na= null;		//作成者名
	private String update_ts     = null;		//更新年月日(画面表示用)
	private String db_update_ts  = null;		//更新年月日(DBチェック用)
	private String update_user_id= null;		//更新者社員ID
	private String update_user_na= null;		//更新者名
	private String delete_fg     = null;		//削除フラグ	

	/**
	 * コンストラクタ
	 */
	public mst600401_KeepMeisaiBean () {
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		this.hanku_cd       = "";
//		this.hanku_na       = "";
//		this.s_gyousyu_cd   = "";
//		this.s_gyousyu_na   = "";
//		this.check_sycle    = "";
//		this.o_weekday_qt   = "";
//		this.o_monthday_qt  = 0;
//		this.o_day_qt       = 0;
//		this.deldays_remain = "";
//		this.hachu_teisiyotei = "";
		this.hinban_cd       = "";
		this.hinban_na       = "";
		this.bumon_cd   = "";
		this.bumon_na   = "";
		this.check_sycle    = "";
		this.o_weekday_qt   = "";
		this.o_monthday_qt  = 0;
		this.o_day_qt       = 0;
		this.deldays_remain = "";
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		this.insert_ts      = "";//作成年月日
		this.db_insert_ts   = "";
		this.insert_user_id = "";//作成者社員ID
		this.insert_user_na = "";
		this.update_ts      = "";//更新年月日
		this.db_update_ts   = "";
		this.update_user_id = "";//更新者社員ID
		this.update_user_na = "";
	}

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase() {
		createDatabase = true;
	}
	public boolean isCreateDatabase() {
		return createDatabase;
	}

	/**
	 * JidohaibanBeanを１件のみ抽出したい時に使用する
	 */
	public static mst600401_KeepMeisaiBean getJidohaibanBean(DataHolder dataHolder) {
		mst600401_KeepMeisaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst600101_JidohaibanLDM() );
		String logHeader = " mst600101_JidohaibanLDM * getJidohaibanBean : ";
		
		try {
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst600401_KeepMeisaiBean)ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		} catch(Exception e) {
			stcLog.getLog().info(logHeader + "+++ Exception +++" + e.toString());
		}
		return bean;
	}
	
	
	/**
	 * 管理区分:kanri_kb に対するセッターとゲッターの集合
	 */
	public boolean setKanriKb(String kanri_kb) {
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb() {
		if (this.kanri_kb == null) {
			this.kanri_kb = "";
		}
		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
	}
	public String getKanriKbString() {
		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getKanriKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
	}
	
	/**
	 * 管理コード:kanri_cd に対するセッターとゲッターの集合
	 */
	public boolean setKanriCd(String kanri_cd) {
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd(){
		if (this.kanri_cd == null) {
			this.kanri_cd = "";
		}
		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
	}
	public String getKanriCdString() {
		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getKanriCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
	}

	/** 
	 * 管理コード(from):kanri_cd_from に対するセッターとゲッターの集合
	 */
	public boolean setKanriCdFrom(String kanri_cd_from) {
		this.kanri_cd_from = kanri_cd_from;
		return true;
	}
	public String getKanriCdFrom() {
		if (this.kanri_cd_from == null) {
			this.kanri_cd_from = "";
		}
		return cutString(this.kanri_cd_from,KANRI_CD_FROM_MAX_LENGTH);
	}
	public String getKanriCdFromString() {
		return "'" + cutString(this.kanri_cd_from,KANRI_CD_FROM_MAX_LENGTH) + "'";
	}
	public String getKanriCdFromHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kanri_cd_from,KANRI_CD_FROM_MAX_LENGTH));
	}

	/**
	 * 管理コード(to):kanri_cd_to に対するセッターとゲッターの集合
	 */
	public boolean setKanriCdTo(String kanri_cd_to) {
		this.kanri_cd_to = kanri_cd_to;
		return true;
	}
	public String getKanriCdTo() {
		if (this.kanri_cd_to == null) {
			this.kanri_cd_to = "";
		}
		return cutString(this.kanri_cd_to,KANRI_CD_TO_MAX_LENGTH);
	}
	public String getKanriCdToString() {
		return "'" + cutString(this.kanri_cd_to,KANRI_CD_TO_MAX_LENGTH) + "'";
	}
	public String getKanriCdToHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kanri_cd_to,KANRI_CD_TO_MAX_LENGTH));
	}

	/**
	 * 処理選択:sentaku_flg に対するセッターとゲッターの集合
	 */
	public boolean setSentakuFlg(String sentaku_flg) {
		this.sentaku_flg = sentaku_flg;
		return true;
	}
	public String getSentakuFlg() {
		if (this.sentaku_flg == null) {
			this.sentaku_flg = "";
		}
		return cutString(this.sentaku_flg,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuFlgString() {
		return "'" + cutString(this.sentaku_flg,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuFlgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.sentaku_flg,SENTAKU_MAX_LENGTH));
	}

//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
	/**
	 * 販区コード:hanku_cd に対するセッターとゲッターの集合
	 */
//	public boolean setHankuCd(String hanku_cd) {
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd() {
//		if (this.hanku_cd == null) {
//			this.hanku_cd = "";
//		}
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString() {
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//	
//	/**
//	 * 販区名称:hanku_na に対するセッターとゲッターの集合
//	 */
//	public boolean setHankuNa(String hanku_na) {
//		this.hanku_na = hanku_na;
//		return true;
//	}
//	public String getHankuNa(){
//		if (this.hanku_na == null) {
//			this.hanku_na = "";
//		}
//		return cutString(this.hanku_na,HANKU_NA_MAX_LENGTH);
//	}
//	public String getHankuNaString() {
//		return "'" + cutString(this.hanku_na,HANKU_NA_MAX_LENGTH) + "'";
//	}
//	public String getHankuNaHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.hanku_na,HANKU_NA_MAX_LENGTH));
//	}	
	/**
	 * 品番コード:hinban_cd に対するセッターとゲッターの集合
	 */
	public boolean setHinbanCd(String hinban_cd) {
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd() {
		if (this.hinban_cd == null) {
			this.hinban_cd = "";
		}
		return cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH);
	}
	public String getHinbanCdString() {
		return "'" + cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getHinbanCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH));
	}
	
	/**
	 * 品番名称:hinban_na に対するセッターとゲッターの集合
	 */
	public boolean setHinbanNa(String hinban_na) {
		this.hinban_na = hinban_na;
		return true;
	}
	public String getHinbanNa(){
		if (this.hinban_na == null) {
			this.hinban_na = "";
		}
		return cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH);
	}
	public String getHinbanNaString() {
		return "'" + cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH) + "'";
	}
	public String getHinbanNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hinban_na,HINBAN_NA_MAX_LENGTH));
	}
//	 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//	/**
//     * 小業種コード:s_gyousyu_cd に対するセッターとゲッターの集合
//     */
//	public boolean setSGyousyuCd(String s_gyousyu_cd) {
//		this.s_gyousyu_cd = s_gyousyu_cd;
//		return true;
//	}
//	public String getSGyousyuCd() {
//		if (this.s_gyousyu_cd == null) {
//			this.s_gyousyu_cd = "";
//		}
//		return cutString(this.s_gyousyu_cd,SYO_GYOUSYU_CD_MAX_LENGTH);
//	}
//	public String getSGyousyuCdString() {
//		return "'" + cutString(this.s_gyousyu_cd,SYO_GYOUSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getSGyousyuCdHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.s_gyousyu_cd,SYO_GYOUSYU_CD_MAX_LENGTH));
//	}	
//	
//	/**
//	 * 小業種名称:s_gyousyu_na に対するセッターとゲッターの集合
//	 */
//	public boolean setSGyousyuNa(String s_gyousyu_na) {
//		this.s_gyousyu_na = s_gyousyu_na;
//		return true;
//	}
//	public String getSGyousyuNa() {
//		if (this.s_gyousyu_na == null) {
//			this.s_gyousyu_na = "";
//		}
//		return cutString(this.s_gyousyu_na,SYO_GYOUSYU_NA_MAX_LENGTH);
//	}
//	public String getSGyousyuNaString() {
//		return "'" + cutString(this.s_gyousyu_na,SYO_GYOUSYU_NA_MAX_LENGTH) + "'";
//	}
//	public String getSGyousyuNaHTMLString() {
//		return HTMLStringUtil.convert(cutString(this.s_gyousyu_na,SYO_GYOUSYU_NA_MAX_LENGTH));
//	}
	/**
     * 部門コード:bumon_cd に対するセッターとゲッターの集合
     */
	public boolean setBumonCd(String bumon_cd) {
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd() {
		if (this.bumon_cd == null) {
			this.bumon_cd = "";
		}
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString() {
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}	
	
	/**
	 * 部門名称:s_gyousyu_na に対するセッターとゲッターの集合
	 */
	public boolean setBumonNa(String bumon_na) {
		this.bumon_na = bumon_na;
		return true;
	}
	public String getBumonNa() {
		if (this.bumon_na == null) {
			this.bumon_na = "";
		}
		return cutString(this.bumon_na,BUMON_NA_MAX_LENGTH);
	}
	public String getBumonNaString() {
		return "'" + cutString(this.bumon_na,BUMON_NA_MAX_LENGTH) + "'";
	}
	public String getBumonNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.bumon_na,BUMON_NA_MAX_LENGTH));
	}
//	 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
	/**
	 * チェックサイクル:check_sycle に対するセッターとゲッターの集合
	 */
	public boolean setCheckSycle(String check_sycle) {
		this.check_sycle = check_sycle;
		return true;
	}
	public String getCheckSycle() {
		if (this.check_sycle == null) {
			this.check_sycle = "";
		}
		return cutString(this.check_sycle,CHECK_SYCLE_MAX_LENGTH);
	}
	public String getCheckSycleString() {
		return "'" + cutString(this.check_sycle,CHECK_SYCLE_MAX_LENGTH) + "'";
	}
	public String getCheckSycleHTMLString() {
		return HTMLStringUtil.convert(cutString(this.check_sycle,CHECK_SYCLE_MAX_LENGTH));
	}

	/**
	 * 出力日(日):out_monthday_qt に対するセッターとゲッターの集合
	 */
	public void setOutMonthdayQt(int i) {
		this.o_monthday_qt = i;
	}
	public void setOutMonthdayQt(String str) {
		this.o_monthday_qt = Integer.parseInt(str);
	}
	public int getOutMonthdayQt() {
		return this.o_monthday_qt;
	}
	public String getOutMonthdayQtString() {
		return String.valueOf(this.o_monthday_qt);
	}

	/**
	 * 出力日(週曜日):o_weekday_qt に対するセッターとゲッターの集合
	 */
	public boolean setOutWeekdayQt(String o_weekday_qt) {
		this.o_weekday_qt = o_weekday_qt;
		return true;
	}
	public String getOutWeekdayQt() {
		if (this.o_weekday_qt == null) {
			this.o_weekday_qt = "";
		}
		return cutString(this.o_weekday_qt,OUT_WEEKDAY_QT_MAX_LENGTH);
	}
	public String getOutWeekdayQtString() {
		return "'" + cutString(this.o_weekday_qt,OUT_WEEKDAY_QT_MAX_LENGTH) + "'";
	}
	public String getOutWeekdayQtHTMLString() {
		return HTMLStringUtil.convert(cutString(this.o_weekday_qt,OUT_WEEKDAY_QT_MAX_LENGTH));
	}

	/**
	 * 廃番予定出力(N日前):o_day_qt に対するセッターとゲッターの集合
	 */
	public void setOutDayQt(int i) {
		this.o_day_qt = i;
	}
	public void setOutDayQt(String str) {
		this.o_day_qt = Integer.parseInt(str);
	}
	public int getOutDayQt() {
		return this.o_day_qt;
	}
	public String getOutDayQtString() {
		return String.valueOf(this.o_day_qt);
	}

	/**
	 * 削除余裕日:deldays_remain に対するセッターとゲッターの集合
	 */
	public boolean setDeldaysRemain(String deldays_remain) {
		this.deldays_remain = deldays_remain;
		return true;
	}
	public String getDeldaysRemain() {
		if (this.deldays_remain == null) {
			this.deldays_remain = "";
		}
		return cutString(this.deldays_remain,DELDAYS_REMAIN_MAX_LENGTH);
	}
	public String getDeldaysRemainString() {
		return "'" + cutString(this.deldays_remain,DELDAYS_REMAIN_MAX_LENGTH) + "'";
	}
	public String getDeldaysRemainHTMLString() {
		return HTMLStringUtil.convert(cutString(this.deldays_remain,DELDAYS_REMAIN_MAX_LENGTH));
	}

//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
	/**
	 * 発注停止予定日:hachu_teisiyotei に対するセッターとゲッターの集合
	 */
//	public boolean setHachu_teisiyotei(String hachu_teisiyotei) {
//		this.hachu_teisiyotei = hachu_teisiyotei;
//		return true;
//	}
//	public String getHachu_teisiyotei() {
//		if (this.hachu_teisiyotei == null) {
//			this.hachu_teisiyotei = "";
//		}
//		return this.hachu_teisiyotei;
//	}
//	 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
	/**
	 * 作成年月日:insert_tsに対するセッターとゲッターの集合
	 */
	public boolean setInsertTs(String insert_ts) {
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs() {
		if (this.insert_ts == null) {
			this.insert_ts = "";
		}
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString() {
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}
	
	/**
	 * 作成年月日(DBチェック用)
	 * insert_tsに対するセッターとゲッターの集合
	 */
	public boolean setInsertTsDB(String db_insert_ts) {
		this.db_insert_ts = db_insert_ts;
		return true;
	}
	public String getInsertTsDB() {
		if (this.db_insert_ts == null) {
			this.db_insert_ts = "";
		}
		return cutString(this.db_insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsDBString() {
		return "'" + cutString(this.db_insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsDBHTMLString() {
		return HTMLStringUtil.convert(cutString(this.db_insert_ts,INSERT_TS_MAX_LENGTH));
	}

	/**
	 * 作成者社員ID:insert_user_idに対するセッターとゲッターの集合
	 */
	public boolean setInsertUserId(String insert_user_id) {
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId() {
		if (this.insert_user_id == null) {
			this.insert_user_id = "";
		}
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString() {
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}
	
	/**
	 * 作成者名:insert_user_naに対するセッターとゲッターの集合
	 */
	public boolean setInsertUserNa(String insert_user_na) {
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa() {
		if (this.insert_user_na == null) {
			this.insert_user_na = "";
		}
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString() {
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
	}
	
	/**
	 * 更新年月日:update_tsに対するセッターとゲッターの集合
	 */
	public boolean setUpdateTs(String update_ts) {
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs() {
		if (this.update_ts == null) {
			this.update_ts = "";
		}
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString() {
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString() {
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}
	
	/**
	 * 更新年月日(DBチェック用)
	 * update_tsに対するセッターとゲッターの集合
	 */
	public boolean setUpdateTsDB(String db_update_ts) {
		this.db_update_ts = db_update_ts;
		return true;
	}
	public String getUpdateTsDB() {
		if (this.update_ts == null) {
			this.update_ts = "";
		}
		return cutString(this.db_update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsDBString() {
		return "'" + cutString(this.db_update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsDBHTMLString() {
		return HTMLStringUtil.convert(cutString(this.db_update_ts,UPDATE_TS_MAX_LENGTH));
	}

	/**
	 * 更新者社員ID:update_user_idに対するセッターとゲッターの集合
	 */
	public boolean setUpdateUserId(String update_user_id) {
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId() {
		if (this.update_user_id == null) {
			this.update_user_id = "";
		}
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString() {
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}
	
	
	/**
	 * 更新者名:update_user_naに対するセッターとゲッターの集合
	 */
	public boolean setUpdateUserNa(String update_user_na) {
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa() {
		if (this.update_user_na == null) {
			this.update_user_na = "";
		}
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString() {
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString() {
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
	}


	/**
	 * 削除フラグ:delete_fgに対するセッターとゲッターの集合
	 */
	public boolean setDeleteFg(String delete_fg) {
		this.delete_fg = delete_fg;
		return true;
	}
	public String getDeleteFg() {
		if (this.delete_fg == null) {
			this.delete_fg = "";
		}
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString() {
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString() {
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}
	

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString(){
		return 
              " kanri_kb      = " + getKanriKbString()		//管理区分
		    + " kanri_cd        = " + getKanriCdString()		//管理コード
			+ " kanri_cd_from   = " + getKanriCdFromString()	//管理コード(from)
			+ " kanri_cd_to     = " + getKanriCdToString()		//管理コード(to)
			+ " sentaku_flg     = " + getSentakuFlgString()		//処理選択
			//	 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//			+ " hanku_cd        = " + getHankuCdString()		//販区コード
//			+ " hanku_na        = " + getHankuNaString()		//販区名称
//			+ " s_gyousyu_cd    = " + getSGyousyuCdString()		//小業種コード
//			+ " s_gyousyu_na    = " + getSGyousyuNaString()		//小業種名称
			+ " hinban_cd        = " + getHinbanCdString()		   //品番コード
			+ " hinban_na        = " + getHinbanNaString()		   //品番名称
			+ " bumon_cd    = " + getBumonCdString()		       //部門コード
			+ " bumon_na    = " + getBumonNaString()		       //部門名称
			//			 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
			+ " check_sycle     = " + getCheckSycleString()		//チェックサイクル
			+ " o_monthday_qt   = " + getOutMonthdayQtString()	//出力日(日)
			+ " o_weekday_qt    = " + getOutWeekdayQtString()	//出力日(週曜日)
			+ " o_day_qt        = " + getOutDayQtString()		//廃番予定出力(N日前)
			+ " deldays_remain  = " + getDeldaysRemainString()	//削除余裕日
			+ " insert_ts       = " + getInsertTsString()		//作成年月日
			+ " insert_user_id  = " + getInsertUserIdString()	//作成者社員ID
			+ " insert_user_na  = " + getInsertUserNaString()	//作成者名
			+ " update_ts       = " + getUpdateTsString()		//更新年月日
			+ " update_user_id  = " + getUpdateUserIdString()	//更新者社員ID
			+ " update_user_na  = " + getUpdateUserNaString()	//更新者名
			+ " delete_fg       = " + getDeleteFgString()		//削除フラグ
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RKeiryokiBean コピー後のクラス
	 */
	public mst600401_KeepMeisaiBean createClone() {
		
		mst600401_KeepMeisaiBean bean = new mst600401_KeepMeisaiBean();
		
		bean.setKanriKb(this.kanri_kb);			//管理区分
		bean.setKanriCd(this.kanri_cd);			//管理コード
		bean.setKanriCdFrom(this.kanri_cd_from);	//管理コード(from)
		bean.setKanriCdTo(this.kanri_cd_to);		//管理コード(to)
		bean.setSentakuFlg(this.sentaku_flg);		//処理選択
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		bean.setHankuCd(this.hanku_cd);			//販区コード
//		bean.setHankuNa(this.hanku_na);			//販区名称
//		bean.setSGyousyuCd(this.s_gyousyu_cd);		//小業種コード
//		bean.setSGyousyuNa(this.s_gyousyu_na);		//小業種名称
		bean.setHinbanCd(this.hinban_cd);			//品番コード
		bean.setHinbanNa(this.hinban_na);			//品番名称
		bean.setBumonCd(this.bumon_cd);		//部門コード
		bean.setBumonNa(this.bumon_na);		//部門名称
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		bean.setCheckSycle(this.check_sycle);		//チェックサイクル
		bean.setOutMonthdayQt(this.o_monthday_qt);	//出力日(日)
		bean.setOutWeekdayQt(this.o_weekday_qt);	//出力日(週曜日)
		bean.setOutDayQt(this.o_day_qt);			//廃番予定出力(N日前)
		bean.setDeldaysRemain(this.deldays_remain);//削除余裕日
		bean.setInsertTs(this.insert_ts);			//作成年月日
		bean.setInsertUserId(this.insert_user_id);	//作成者社員ID
		bean.setInsertUserNa(this.insert_user_na);	//作成者名
		bean.setUpdateTs(this.update_ts);			//更新年月日
		bean.setUpdateUserId(this.update_user_id);	//更新者社員ID
		bean.setUpdateUserNa(this.update_user_na);	//更新者名
		bean.setDeleteFg(this.delete_fg);			//削除フラグ
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o ) {
		if( o == null ) return false;
		if( !( o instanceof mst600401_KeepMeisaiBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max ) {
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ ) {
			try {
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			} catch(Exception e) {
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}

}
