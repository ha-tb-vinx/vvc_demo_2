/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（条件指定）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理） 商品マスタ一括修正（条件指定）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する 商品マスタ一括修正（条件指定）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/16)初版作成
 * @version 1.1(2006/11/07)障害票№0194 高速化、表示項目(消札売価)追加、条件指定画面と更新画面の融合対応 K.Tanigawa
 */
public class mst160101_SyohinIkkatuMenteBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	private String ErrorFlg			= "";				// エラーフラグ
	private String ErrorMessage		= "";				// エラーメッセージ
	private Map CtrlColor					= new HashMap();	// コントロール前景色
	private String FirstFocus				= "";				// フォーカスを最初に取得するオブジェクト名
	private static final String INIT_PAGE   = "mst160101_SyohinIkkatuMenteInit";	// 初期画面JSPを取得
	public static final int YUKO_DT_MAX_LENGTH 			 = 8;	//有効日の長さ
//	↓↓2006.07.11 lixy カスタマイズ修正↓↓
	//public static final int HANKU_CD_MAX_LENGTH 			 = 4;	//販区コードの長さ
	//public static final int HANKU_KANJI_RN_MAX_LENGTH	 = 20;	//販区名の長さ
	public static final int SYOHIN_CD_MAX_LENGTH 		 = 13;	//商品コードの長さ
	//public static final int HINSYU_CD_MAX_LENGTH 		 = 4;	//品種コードの長さ
	//public static final int SYOHIN_1_CD_MAX_LENGTH 	 = 2;	//商品コード1の長さ
	//public static final int SYOHIN_2_CD_MAX_LENGTH 	 = 13;	//商品コード２の長さ
	public static final int BUMON_CD_MAX_LENGTH       = 4;   //部門コードの長さ
	//public static final int BUMON_NM_MAX_LENGTH       = 80;  //部門名称の長さ
	public static final int BUMON_NM_MAX_LENGTH       = 20;  //部門名称の長さ
	public static final int HINSYU_CD_MAX_LENGTH 		 = 3;	//品種コードの長さ
	public static final int JAN_MARK_CD_MAX_LENGTH 	 = 9;	//JANメーカーコードの長さ
//BUGNO-S061 2005.06.14 T.Kikuchi START
	public static final int HINMEI_KANJI_NA_MAX_LENGTH 	 = 18;	//漢字品名の長さ
	public static final int KIKAKU_KANJI_NA_MAX_LENGTH 	 = 6;	//漢字規格の長さ
	//public static final int HINMEI_KANJI_NA_MAX_LENGTH 	 = 80;	//漢字品名の長さ
	//public static final int KIKAKU_KANJI_NA_MAX_LENGTH 	 = 40;	//漢字規格の長さ
//BUGNO-S061 2005.06.14 T.Kikuchi START
//	↑↑2006.07.11 lixy カスタマイズ修正↑↑
	public static final int SIIRESAKI_CD_MAX_LENGTH 		 = 9;	//仕入先コードの長さ
	public static final int SIIRESAKI_KANJI_RN_MAX_LENGTH = 20;	//仕入先名の長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH 		 = 8;	//販売開始日の長さ
	public static final int HANBAI_ED_DT_MAX_LENGTH 		 = 8;	//販売終了日の長さ
	public static final int SEASON_CD_MAX_LENGTH 		 = 3;	//シーズンコードの長さ
	public static final int SEASON_KANJI_RN_MAX_LENGTH	 = 20;	//シーズン名の長さ
	//public static final int INSERT_TS_MAX_LENGTH 		 = 20;	//作成年月日の長さ
	public static final int INSERT_TS_MAX_LENGTH 		 = 8;	//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 	 = 10;	//作成者社員IDの長さ
	public static final int INSERT_USER_NM_MAX_LENGTH 	 = 20;	//作成者社員名の長さ
	//public static final int UPDATE_TS_MAX_LENGTH 		 = 20;	//更新年月日の長さ
	public static final int UPDATE_TS_MAX_LENGTH 		 = 8;	//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 	 = 10;	//更新者社員IDの長さ
	public static final int UPDATE_USER_NM_MAX_LENGTH 	 = 20;	//更新者社員名の長さ
	public static final int DELETE_FG_MAX_LENGTH 		 = 1;	//削除フラグの長さ

//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	public static final int COLOR_NA_MAX_LENGTH 			 = 80;	//略式名称漢字
	public static final int SIZE_NA_MAX_LENGTH 			 = 80;	//略式名称漢字
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

	private String yuko_dt 			= null;	//有効日
//	↓↓2006.07.11 lixy カスタマイズ修正↓↓
	private String system_kb 			= null;				//システム区分
	private String hinban_cd				="";				//品番
	private String hinban_kanji_rn			="";				//品番名
	private String ranyin_cd				="";				//ライン
	private String ranyin_kanji_rn			="";				//ライン名
	private String siiresakisyohin_cd  	="";				//仕入先商品コード
	private String sabkuras_cd				="";				//サブクラスコード
	private String sabkuras_kanji_cd		="";				//サブクラス名
	private String unit_cd        	    	="";				//ユニットコード
	private String unit_kanji_rn       	="";				//ユニットコード名
	private String hinsyu_kanji_rn			= "";				//品種名
	private String by_no                   = "";	            //BY NO.
	private String syouhin_kb              = "";	            //商品区分
	private String baitenka_st			="";					//売開始単価
	private String baitanka_ed			="";					//売終了単価
	private String jan_mark_kanji_rn 		= "";				//JANメーカ名
	private String hachu_st_dt        	    ="";				//店舗発注開始日
	private String hachu_ed_dt        	    ="";				//店舗発注終了日
	private String tanano_st_nb			="";				//開始棚NO
	private String tanano_ed_nb			="";				//終了棚NO

	private String bumon_cd            = null;//部門コード
	private String bumon_kanji_rn 		= null;	//部門名
	//private String hanku_cd 			= null;	//販区コード
	//private String hanku_kanji_rn 		= null;	//販区名
	private String syohin_cd 			= null;	//商品コード
	private String hinsyu_cd 			= null;	//品種コード
	//private String syohin_1_cd 		= null;	//商品コード１
	//private String syohin_2_cd 		= null;	//商品コード２
//	↑↑2006.07.11 lixy カスタマイズ修正↑↑
	private String jan_mark_cd 		= null;	//JANメーカーコード
	private String hinmei_kanji_na 	= null;	//漢字品名
	private String kikaku_kanji_na 	= null;	//漢字規格
	private double gentanka_vl 		= 0;		//原価単価
	private long baitanka_vl 			= 0;		//売価単価（税込）
	private String siiresaki_cd 		= null;	//仕入先コード
	private String siiresaki_kanji_rn 	= null;	//仕入先名
	private String hanbai_st_dt 		= null;	//販売開始日
	private String hanbai_ed_dt 		= null;	//販売終了日
	private String season_cd 			= null;	//シーズンコード
	private String season_kanji_rn 	= null;	//シーズン名
	private String insert_ts 			= null;	//作成年月日
	private String insert_user_id 		= null;	//作成者社員ID
	private String insert_user_nm 		= null;	//作成者社員名
	private String update_ts 			= null;	//更新年月日
	private String update_user_id 		= null;	//更新者社員ID
	private String update_user_nm 		= null;	//更新者社員名
	private String delete_fg 			= null;	//削除フラグ
	private String sentaku_fg 			= null;	//選択フラグ

//	 ADD by Tanigawa 2006/11/01 障害票№0194対応 START
	private long keshi_baika_vl		= 0;//消札売価
//	 ADD by Tanigawa 2006/11/01 障害票№0194対応  END
//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	private String color_na 			= null;
	private String size_na 			= null;
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

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
	 * mst160101_SyohinIkkatuMenteBeanを１件のみ抽出したい時に使用する
	 */
	public static mst160101_SyohinIkkatuMenteBean getRSYOHINBean(DataHolder dataHolder)
	{
		mst160101_SyohinIkkatuMenteBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst160101_SyohinIkkatuMenteDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst160101_SyohinIkkatuMenteBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
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
//	↓↓2006.07.11 lixy カスタマイズ修正↓↓
//	 ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg)
	{
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg()
	{
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus)
	{
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus()
	{
		return this.FirstFocus;
	}

	  /**
	   * 初期画面URL取得(ログ出力有り)
	   * <br>
	   * Ex)<br>
	   * getInitUrl("logHeader","logMsg") -&gt; String<br>
	   * <br>
	   * @param String logHeader
	   * @param String logMsg
	   * @return		String
	   */
	  public String getInitUrl(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.ErrorFlg.equals("")){
			  //通常系
			  if(!this.ErrorMessage.equals("")){
				  stcLog.getLog().info(logHeader + this.ErrorMessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.ErrorMessage);
		  }

		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }

		  return	INIT_PAGE;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrl()
	  {
		  return	INIT_PAGE;
	  }

	  //CtrlColorに対するセッターとゲッターの集合
		public boolean setCtrlColor(Map CtrlColor)
		{
			this.CtrlColor = CtrlColor;
			return true;
		}
		public Map getCtrlColor()
		{
			return this.CtrlColor;
		}
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

	// hanku_kanji_rnに対するセッターとゲッターの集合
//	public boolean setHankuKanjiRn(String hanku_kanji_rn)
//	{
//		this.hanku_kanji_rn = hanku_kanji_rn;
//		return true;
//	}
//	public String getHankuKanjiRn()
//	{
//		return cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH);
//	}
//	public String getHankuKanjiRnString()
//	{
//		return "'" + cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getHankuKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_kanji_rn,HANKU_KANJI_RN_MAX_LENGTH));
//	}
//	↑↑2006.07.11 lixy カスタマイズ修正↑↑

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
//	↓↓2006.07.11 lixy カスタマイズ修正↓↓
	// syohin_1_cdに対するセッターとゲッターの集合
//	public boolean setSyohin1Cd(String syohin_1_cd)
//	{
//		this.syohin_1_cd = syohin_1_cd;
//		return true;
//	}
//	public String getSyohin1Cd()
//	{
//		return cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH);
//	}
//	public String getSyohin1CdString()
//	{
//		return "'" + cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
//	}
//	public String getSyohin1CdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH));
//	}

	// syohin_2_cdに対するセッターとゲッターの集合
//	public boolean setSyohin2Cd(String syohin_2_cd)
//	{
//		this.syohin_2_cd = syohin_2_cd;
//		return true;
//	}
//	public String getSyohin2Cd()
//	{
//		return cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH);
//	}
//	public String getSyohin2CdString()
//	{
//		return "'" + cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH) + "'";
//	}
//	public String getSyohin2CdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.syohin_2_cd,SYOHIN_2_CD_MAX_LENGTH));
//	}

//	↓↓2006.07.11 lixy カスタマイズ修正↓↓
//	 bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return cutString(this.bumon_kanji_rn,BUMON_NM_MAX_LENGTH);
	}
	public String getBumonKanjiRnString()
	{
		return "'" + cutString(this.bumon_kanji_rn,BUMON_NM_MAX_LENGTH) + "'";
	}
	public String getBumonKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_kanji_rn,BUMON_NM_MAX_LENGTH));
	}

//	 bumon_cdに対するセッターとゲッターの集合
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
//	↑↑2006.07.11 lixy カスタマイズ修正↑↑

//	↓↓2006.07.17 lixy カスタマイズ修正↓↓
	//system_kbに対するセッターとゲッターの集合
	public String getSystemKb() {
		return system_kb;
	}
	public void setSystemKb(String system_kb) {
		this.system_kb = system_kb;
	}

	//	 hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return this.hinban_cd;
	}

	// hinban_kanji_rnに対するセッターとゲッターの集合
	public boolean setHinbanKanjiRn(String hinban_kanji_rn)
	{
		this.hinban_kanji_rn = hinban_kanji_rn;
		return true;
	}
	public String getHinbanKanjiRn()
	{
		return this.hinban_kanji_rn;
	}
	//	 ranyin_cdに対するセッターとゲッターの集合
	public boolean setRayinCd(String ranyin_cd)
	{
		this.ranyin_cd = ranyin_cd;
		return true;
	}
	public String getRayinbanCd()
	{
		return this.ranyin_cd;
	}

	// rayin_kanji_rnに対するセッターとゲッターの集合
	public boolean setRanyinKanjiRn(String ranyin_kanji_rn)
	{
		this.ranyin_kanji_rn = ranyin_kanji_rn;
		return true;
	}
	public String getRanyinKanjiRn()
	{
		return this.ranyin_kanji_rn;
	}
	//	 siiresakisyohin_cdに対するセッターとゲッターの集合
	public boolean setSiiresakisyohinCd(String siiresakisyohin_cd)
	{
		this.siiresakisyohin_cd = siiresakisyohin_cd;
		return true;
	}
	public String getSiiresakisyohinCd()
	{
		return this.siiresakisyohin_cd;
	}

	//	 unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return this.unit_cd;
	}

	// unit_kanji_rnに対するセッターとゲッターの集合
	public boolean setUnitKanjiRn(String unit_kanji_rn)
	{
		this.unit_kanji_rn = unit_kanji_rn;
		return true;
	}
	public String getUnitKanjiRn()
	{
		return this.unit_kanji_rn;
	}

	// sabkuras_cdに対するセッターとゲッターの集合
	public boolean setSabkurasCd(String sabkuras_cd)
	{
		this.sabkuras_cd = sabkuras_cd;
		return true;
	}
	public String getSabkurasCd()
	{
		return this.sabkuras_cd;
	}
	// siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setSabkurasKanjiRn(String sabkuras_kanji_cd)
	{
		this.sabkuras_kanji_cd = sabkuras_kanji_cd;
		return true;
	}
	public String getSabkurasKanjiRn()
	{
		return this.sabkuras_kanji_cd;
	}
	//	 baitenka_stに対するセッターとゲッターの集合
	public boolean setBaisttanka(String baitenka_st)
	{
		this.baitenka_st = baitenka_st;
		return true;
	}
	public String getBaisttanka()
	{
		return this.baitenka_st;
	}
	//	 baitanka_edに対するセッターとゲッターの集合
	public boolean setBaiedtanka(String baitanka_ed)
	{
		this.baitanka_ed = baitanka_ed;
		return true;
	}
	public String getBaiedstanka()
	{
		return this.baitanka_ed;
	}

	//by_noに対するセッターとゲッターの集合
	public boolean setByNo(String by_no)
	{
		this.by_no = by_no;
		return true;
	}
	public String getByNo()
	{
		return this.by_no;
	}
	// syouhin_kbに対するセッターとゲッターの集合
	public boolean setSyouhinKb(String syouhin_kb)
	{
		this.syouhin_kb = syouhin_kb;
		return true;
	}
	public String getSyouhinKb()
	{
		return this.syouhin_kb;
	}

	// hinsyu_kanji_rnに対するセッターとゲッターの集合
	public boolean setHinsyuKanjiRn(String hinsyu_kanji_rn)
	{
		this.hinsyu_kanji_rn = hinsyu_kanji_rn;
		return true;
	}
	public String getHinsyuKanjiRn()
	{
		return this.hinsyu_kanji_rn;
	}

	// jan_mark_kanji_rnに対するセッターとゲッターの集合
	public boolean setJanMarkKanjiRn(String jan_mark_kanji_rn)
	{
		this.jan_mark_kanji_rn = jan_mark_kanji_rn;
		return true;
	}
	public String getJanMarkKanjiRn()
	{
		return this.jan_mark_kanji_rn;
	}

	//	 hachu_st_dtに対するセッターとゲッターの集合
	public boolean setHachuStDt(String hachu_st_dt)
	{
		this.hachu_st_dt = hachu_st_dt;
		return true;
	}
	public String getHachuStDt()
	{
		return this.hachu_st_dt;
	}

	// hanbai_ed_dtに対するセッターとゲッターの集合
	public boolean setHachuEdDt(String hachu_ed_dt)
	{
		this.hachu_ed_dt = hachu_ed_dt;
		return true;
	}
	public String getHachuEdDt()
	{
		return this.hachu_ed_dt;
	}

	//	 tanano_st_nbに対するセッターとゲッターの集合
	public boolean setTananostNb(String tanano_st_nb)
	{
		this.tanano_st_nb = tanano_st_nb;
		return true;
	}
	public String getTananostNb()
	{
		return this.tanano_st_nb;
	}
	//	 tanano_ed_nbに対するセッターとゲッターの集合
	public boolean setTananoedNb(String tanano_ed_nb)
	{
		this.tanano_ed_nb = tanano_ed_nb;
		return true;
	}
	public String getTananoedNb()
	{
		return this.tanano_ed_nb;
	}

//	↑↑2006.07.17 lixy カスタマイズ修正↑↑

	// jan_mark_cdに対するセッターとゲッターの集合
	public boolean setJanMarkCd(String jan_mark_cd)
	{
		this.jan_mark_cd = jan_mark_cd;
		return true;
	}
	public String getJanMarkCd()
	{
		return cutString(this.jan_mark_cd,JAN_MARK_CD_MAX_LENGTH);
	}
	public String getJanMarkCdString()
	{
		return "'" + cutString(this.jan_mark_cd,JAN_MARK_CD_MAX_LENGTH) + "'";
	}
	public String getJanMarkCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jan_mark_cd,JAN_MARK_CD_MAX_LENGTH));
	}

	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmeiKanjiNa()
	{
		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getHinmeiKanjiNaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
	}

	// kikaku_kanji_naに対するセッターとゲッターの集合
	public boolean setKikakuKanjiNa(String kikaku_kanji_na)
	{
		this.kikaku_kanji_na = kikaku_kanji_na;
		return true;
	}
	public String getKikakuKanjiNa()
	{
		return cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH);
	}
	public String getKikakuKanjiNaString()
	{
		return "'" + cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_KANJI_NA_MAX_LENGTH));
	}

	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
		try
		{
			this.gentanka_vl = Double.parseDouble(gentanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setGentankaVl(double gentanka_vl)
	{
		this.gentanka_vl = gentanka_vl;
		return true;
	}
	public double getGentankaVl()
	{
		return this.gentanka_vl;
	}
	public String getGentankaVlString()
	{
		return Double.toString(this.gentanka_vl);
	}

	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		try
		{
			this.baitanka_vl = Long.parseLong(baitanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaitankaVl(long baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public long getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlString()
	{
		return Long.toString(this.baitanka_vl);
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

	// siiresaki_kanji_rnに対するセッターとゲッターの集合
	public boolean setSiiresakiKanjiRn(String siiresaki_kanji_rn)
	{
		this.siiresaki_kanji_rn = siiresaki_kanji_rn;
		return true;
	}
	public String getSiiresakiKanjiRn()
	{
		return cutString(this.siiresaki_kanji_rn,SIIRESAKI_KANJI_RN_MAX_LENGTH);
	}
	public String getSiiresakiKanjiRnString()
	{
		return "'" + cutString(this.siiresaki_kanji_rn,SIIRESAKI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getSiiresakiKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_kanji_rn,SIIRESAKI_KANJI_RN_MAX_LENGTH));
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
	// season_cdに対するセッターとゲッターの集合
	public boolean setSeasonCd(String season_cd)
	{
		this.season_cd = season_cd;
		return true;
	}
	public String getSeasonCd()
	{
		return cutString(this.season_cd,SEASON_CD_MAX_LENGTH);
	}
	public String getSeasonCdString()
	{
		return "'" + cutString(this.season_cd,SEASON_CD_MAX_LENGTH) + "'";
	}
	public String getSeasonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.season_cd,SEASON_CD_MAX_LENGTH));
	}

	// season_kanji_rnに対するセッターとゲッターの集合
	public boolean setSeasonKanjiRn(String season_kanji_rn)
	{
		this.season_kanji_rn = season_kanji_rn;
		return true;
	}
	public String getSeasonKanjiRn()
	{
		return cutString(this.season_kanji_rn,SEASON_KANJI_RN_MAX_LENGTH);
	}
	public String getSeasonKanjiRnString()
	{
		return "'" + cutString(this.season_kanji_rn,SEASON_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getSeasonKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.season_kanji_rn,SEASON_KANJI_RN_MAX_LENGTH));
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

	// sentaku_fgに対するセッターとゲッターの集合
	public boolean setSentakuFg(String sentaku_fg)
	{
		this.sentaku_fg = sentaku_fg;
		return true;
	}
	public String getSentakuFg()
	{
		return this.sentaku_fg;
	}


//	 ADD by Tanigawa 2006/11/07 障害票№0194対応 START
	// keshi_baika_vlに対するセッターとゲッターの集合
	public boolean setKeshiBaikaVl(String keshi_baika_vl)
	{
		try
		{
			this.keshi_baika_vl = Long.parseLong(keshi_baika_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setKeshiBaikaVl(long keshi_baika_vl)
	{
		this.keshi_baika_vl = keshi_baika_vl;
		return true;
	}
	public long getKeshiBaikaVl()
	{
		return this.keshi_baika_vl;
	}
	public String getKeshiBaikaVlString()
	{
		return Long.toString(this.keshi_baika_vl);
	}
//	 ADD by Tanigawa 2006/11/07 障害票№0194対応  END

//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	// color_naに対するセッターとゲッターの集合
	public boolean setColorNa(String color_na)
	{
		this.color_na = color_na;
		return true;
	}
	public String getColorNa()
	{
		return cutString(this.color_na,COLOR_NA_MAX_LENGTH);
	}
	public String getColorNaString()
	{
		return "'" + cutString(this.color_na,COLOR_NA_MAX_LENGTH) + "'";
	}
	public String getColorNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_na,COLOR_NA_MAX_LENGTH));
	}


	// size_naに対するセッターとゲッターの集合
	public boolean setSizeNa(String size_na)
	{
		this.size_na = size_na;
		return true;
	}
	public String getSizeNa()
	{
		return cutString(this.size_na,SIZE_NA_MAX_LENGTH);
	}
	public String getSizeNaString()
	{
		return "'" + cutString(this.size_na,SIZE_NA_MAX_LENGTH) + "'";
	}
	public String getSizeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_na,SIZE_NA_MAX_LENGTH));
	}
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑




	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  yuko_dt 		 	= " + getYukoDtString()
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
			//+ "  hanku_cd 		 	= " + getHankuCdString()
			//+ "  hanku_kanji_rn  	= " + getHankuKanjiRnString()
			+ "  syohin_cd 		 	= " + getSyohinCdString()
			+ "  hinsyu_cd 		 	= " + getHinsyuCdString()
			//+ "  syohin_1_cd 	 	= " + getSyohin1CdString()
			//+ "  syohin_2_cd 	 	= " + getSyohin2CdString()
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
			+ "  jan_mark_cd 	 	= " + getJanMarkCdString()
			+ "  hinmei_kanji_na 	= " + getHinmeiKanjiNaString()
			+ "  kikaku_kanji_na 	= " + getKikakuKanjiNaString()
			+ "  gentanka_vl  		= " + getGentankaVlString()
			+ "  baitanka_vl 		= " + getBaitankaVlString()
			+ "  siiresaki_cd 	 	= " + getSiiresakiCdString()
			+ "  siiresaki_kanji_rn = " + getSiiresakiKanjiRnString()
			+ "  hanbai_st_dt 		= " + getHanbaiStDtString()
			+ "  hanbai_ed_dt 		= " + getHanbaiEdDtString()
			+ "  season_cd 			= " + getSeasonCdString()
			+ "  season_kanji_rn 	= " + getSeasonKanjiRnString()
			+ "  insert_ts 			= " + getInsertTsString()
			+ "  insert_user_id 	= " + getInsertUserIdString()
			+ "  insert_user_nm 	= " + getInsertUserNmString()
			+ "  update_ts 			= " + getUpdateTsString()
			+ "  update_user_id 	= " + getUpdateUserIdString()
			+ "  update_user_nm 	= " + getUpdateUserNmString()
			+ "  delete_fg 			= " + getDeleteFgString()
			+ "  sentaku_fg 		= " + getSentakuFg()
			+ " createDatabase  	= " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RSYOHINBean コピー後のクラス
	 */
	public mst160101_SyohinIkkatuMenteBean createClone()
	{
		mst160101_SyohinIkkatuMenteBean bean = new mst160101_SyohinIkkatuMenteBean();
		bean.setYukoDt(this.yuko_dt);
//		↓↓2006.07.11 lixy カスタマイズ修正↓↓
		//bean.setHankuCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
		//bean.setHankuKanjiRn(this.hanku_kanji_rn);
		bean.setSyohinCd(this.syohin_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		//bean.setSyohin1Cd(this.syohin_1_cd);
		//bean.setSyohin2Cd(this.syohin_2_cd);
//		↑↑2006.07.11 lixy カスタマイズ修正↑↑
		bean.setJanMarkCd(this.jan_mark_cd);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setKikakuKanjiNa(this.kikaku_kanji_na);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiKanjiRn(this.siiresaki_kanji_rn);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setHanbaiEdDt(this.hanbai_ed_dt);
		bean.setSeasonCd(this.season_cd);
		bean.setSeasonKanjiRn(this.season_kanji_rn);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNm(this.insert_user_nm);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateUserNm(this.update_user_nm);
		bean.setDeleteFg(this.delete_fg);
		bean.setSentakuFg(this.sentaku_fg);
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
		if( !( o instanceof mst160101_SyohinIkkatuMenteBean ) ) return false;
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
