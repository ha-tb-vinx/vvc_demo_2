/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発行管理マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する発行管理マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/04)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発行管理マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する発行管理マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/04)初版作成
 * 
 * @version 1.1(2005/05/12)新ER対応
 * 
 * 　　　　　追加…発行区分(hakou_kb)
 *                 発行場所コード(hakou_basyo_cd)
 * 
 *           修正…商品マスタ登録情報区分(syohin_toroku_kb) ⇒ 商品ﾏｽﾀ登録情報媒体区分(syohin_toroku_batai_kb)
 *                 初回導入提案区分(syohin_syokai_donyu_kb) ⇒ 初回導入提案媒体区分(syohin_syokai_donyu_batai_kb)
 *                 発注区分(hachu_kb)             ⇒ 発注媒体区分(hachu_batai_kb)
 *                 納品(ASN)区分(nohin_asn_kb)    ⇒ 納品(ASN)媒体区分(nohin_asn_batai_kb)
 *                 欠品区分(kepin_kb)             ⇒ 欠品媒体区分(kepin_batai_kb)
 *                 仕入計上区分(siire_keijo_kb)   ⇒ 仕入計上媒体区分(siire_keijo_batai_kb)
 *                 請求区分(seikyu_kb)            ⇒ 請求媒体区分(seikyu_batai_kb)
 *                 支払区分(siharai_kb)           ⇒ 支払媒体区分(siharai_batai_kb)
 *                 販売区分(hanbai_kb)            ⇒ 販売媒体区分(hanbai_batai_kb)
 *                 発注勧告区分(hachu_kankoku_kb) ⇒ 発注勧告媒体区分(hachu_kankoku_batai_kb)
 *                 在庫区分(zaiko_kb)             ⇒ 在庫媒体区分(zaiko_batai_kb)
 *                 タグ区分(tag_kb)               ⇒ タグ媒体区分(tag_batai_kb)
 */
public class mst470101_HakoKanriBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
	//PRIMARY KEY設定
//	public static final int KANRI_KB_MAX_LENGTH 				 = 1; //管理区分の長さ
//	public static final int KANRI_CD_MAX_LENGTH 				 = 4; //管理コードの長さ
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑
	public static final int KANRI_KANJI_RN_MAX_LENGTH 		 = 20;//管理名の長さ
	public static final int SHIHAI_KB_MAX_LENGTH 			 = 1; //仕配区分の長さ
	public static final int SHIHAI_CD_MAX_LENGTH 			 = 9; //仕配コードの長さ
	public static final int SHIHAI_KANJI_RN_MAX_LENGTH 		 = 20;//仕配名の長さ
	public static final int TENPO_CD_MAX_LENGTH 				 = 6; //店舗コードの長さ
	public static final int TENPO_KANJI_RN_MAX_LENGTH 		 = 20;//店舗名の長さ	
	public static final int YUKO_DT_MAX_LENGTH 				 = 8; //有効日の長さ
	
	public static final int WEB_HAISINSAKI_CD_MAX_LENGTH 	 = 11;//WEB配信先コードの長さ
	public static final int JCA_HAISINSAKI_CD_MAX_LENGTH 	 = 8; //JCA配信先コードの長さ
	
	//新ER対応 *** 項目追加 start ***
	public static final int HAKOU_KB_MAX_LENGTH               = 2; //発行区分の長さ
	public static final int HAKOU_BASYO_CD_MAX_LENGTH         = 4; //発行場所コードの長さ
	//新ER対応 *** 項目追加 end ***
	
	//新ER対応 *** 項目名変更 start ***
	public static final int SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH 	   = 1; //商品マスタ登録情報媒体区分の長さ
	public static final int SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH = 1; //初回導入提案媒体区分の長さ
	public static final int HACHU_BATAI_KB_MAX_LENGTH 			   = 1; //発注媒体区分の長さ
	public static final int NOHIN_ASN_BATAI_KB_MAX_LENGTH 		   = 1; //納品(ASN)媒体区分の長さ
	public static final int KEPIN_BATAI_KB_MAX_LENGTH 			   = 1; //欠品媒体区分の長さ
	public static final int SIIRE_KEIJO_BATAI_KB_MAX_LENGTH 		   = 1; //仕入計上媒体区分の長さ
	public static final int SEIKYU_BATAI_KB_MAX_LENGTH 			   = 1; //請求媒体区分の長さ
	public static final int SIHARAI_BATAI_KB_MAX_LENGTH 			   = 1; //支払媒体区分の長さ
	public static final int HANBAI_BATAI_KB_MAX_LENGTH 			   = 1; //販売媒体区分の長さ
	public static final int HACHU_KANKOKU_BATAI_KB_MAX_LENGTH 	   = 1; //発注勧告媒体区分の長さ
	public static final int ZAIKO_BATAI_KB_MAX_LENGTH 			   = 1; //在庫媒体区分の長さ
	public static final int TAG_BATAI_KB_MAX_LENGTH 				   = 1; //タグ媒体区分の長さ
	//新ER対応 *** 項目名変更 end ***

/* 新ER対応前の項目
	public static final int SYOHIN_TOROKU_KB_MAX_LENGTH 		 = 1; //商品マスタ登録情報区分の長さ
	public static final int SYOHIN_SYOKAI_DONYU_KB_MAX_LENGTH = 1; //初回導入提案区分の長さ
	public static final int HACHU_KB_MAX_LENGTH 				 = 1; //発注区分の長さ
	public static final int NOHIN_ASN_KB_MAX_LENGTH 			 = 1; //納品(ASN)区分の長さ
	public static final int KEPIN_KB_MAX_LENGTH 				 = 1; //欠品区分の長さ
	public static final int SIIRE_KEIJO_KB_MAX_LENGTH 		 = 1; //仕入計上区分の長さ
	public static final int SEIKYU_KB_MAX_LENGTH 			 = 1; //請求区分の長さ
	public static final int SIHARAI_KB_MAX_LENGTH 			 = 1; //支払区分の長さ
	public static final int HANBAI_KB_MAX_LENGTH 			 = 1; //販売区分の長さ
	public static final int HACHU_KANKOKU_KB_MAX_LENGTH 		 = 1; //発注勧告区分の長さ
	public static final int ZAIKO_KB_MAX_LENGTH 				 = 1; //在庫区分の長さ
	public static final int TAG_KB_MAX_LENGTH 				 = 1; //タグ区分の長さ
*/
	
	public static final int SYUKANHACHUYOTEI_KB_MAX_LENGTH 	 = 1; //週間発注予定区分の長さ
	public static final int TENMEISAI_KB_MAX_LENGTH 			 = 1; //店明細区分の長さ
	public static final int INSERT_TS_MAX_LENGTH 			 = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 		 = 10;//作成者社員IDの長さ
//BUGNO-006 2005.04.20 T.kikuchi START	
	//public static final int INSERT_USER_NM_MAX_LENGTH 		 = 10;//作成者社員名の長さ
	public static final int INSERT_USER_NM_MAX_LENGTH 		 = 20;//作成者社員名の長さ
//BUGNO-006 2005.04.20 T.kikuchi END
	public static final int UPDATE_TS_MAX_LENGTH 			 = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 		 = 10;//更新者社員IDの長さ
//BUGNO-006 2005.04.20 T.kikuchi START	
	//public static final int UPDATE_USER_NM_MAX_LENGTH 		 = 10;//更新者社員名の長さ
	public static final int UPDATE_USER_NM_MAX_LENGTH 		 = 20;//更新者社員名の長さ
//BUGNO-006 2005.04.20 T.kikuchi END		
	public static final int DELETE_FG_MAX_LENGTH 			 = 1;//削除フラグの長さ
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
	public static final int BUMON_CD_MAX_LENGTH			=4;//部門コードの長さ
	public static final int BUMON_KANJI_RN_MAX_LENGTH 	=20;//部門名の長さ
	private String bumon_cd          			=null; //部門コード
	private String bumon_kanji_rn  			 	=null;	//部門名	
//	private String kanri_kb 				= null;	//管理区分
//	private String kanri_cd 				= null;	//管理コード
//	private String kanri_kanji_rn 			= null;	//管理名
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	
	private String shihai_kb 				= null;	//仕配区分
	private String shihai_cd 				= null;	//仕配コード
	private String shihai_kanji_rn			= null;	//仕配名
	private String tenpo_cd 				= null;	//店舗コード
	private String tenpo_kanji_rn			= null;	//店舗名
	private String yuko_dt 				= null;	//有効日
	
	private String web_haisinsaki_cd 		= null;	//WEB配信先コード
	private String jca_haisinsaki_cd 		= null;	//JCA配信先コード
	
	//新ER対応 *** 項目追加 start ***
	private String hakou_kb = null;                   //発行区分
	private String hakou_basyo_cd = null;             //発行場所コード
	//新ER対応 *** 項目追加 end ***
	
	//新ER対応 *** 項目名変更 start ***
	private String syohin_toroku_batai_kb       = null;//商品ﾏｽﾀ登録情報媒体区分
	private String syohin_syokai_donyu_batai_kb = null;//初回導入提案媒体区分
	private String hachu_batai_kb               = null;//発注媒体区分
	private String nohin_asn_batai_kb           = null;//納品(ASN)媒体区分
	private String kepin_batai_kb               = null;//欠品媒体区分
	private String siire_keijo_batai_kb         = null;//仕入計上媒体区分
	private String seikyu_batai_kb              = null;//請求媒体区分
	private String siharai_batai_kb             = null;//支払媒体区分
	private String hanbai_batai_kb              = null;//販売媒体区分
	private String hachu_kankoku_batai_kb       = null;//発注勧告媒体区分
	private String zaiko_batai_kb               = null;//在庫媒体区分
	private String tag_batai_kb                 = null;//タグ媒体区分
	//新ER対応 *** 項目名変更 end ***

/* 新ER対応前の項目
	private String syohin_toroku_kb 		= null;	//商品マスタ登録情報区分
	private String syohin_syokai_donyu_kb  = null;	//初回導入提案区分
	private String hachu_kb 				= null;	//発注区分
	private String nohin_asn_kb 			= null;	//納品(ASN)区分
	private String kepin_kb 				= null;	//欠品区分
	private String siire_keijo_kb 			= null;	//仕入計上区分
	private String seikyu_kb 				= null;	//請求区分
	private String siharai_kb 				= null;	//支払区分
	private String hanbai_kb 				= null;	//販売区分
	private String hachu_kankoku_kb 		= null;	//発注勧告区分
	private String zaiko_kb 				= null;	//在庫区分
	private String tag_kb 					= null;	//タグ区分
*/

	private String syukanhachuyotei_kb 	= null;	//週間発注予定区分
	private String tenmeisai_kb 			= null;	//店明細区分
	private String insert_ts 				= null;	//作成年月日
	private String insert_user_id 			= null;	//作成者社員ID
	private String insert_user_nm 			= null;	//作成者社員名
	private String update_ts 				= null;	//更新年月日
	private String update_user_id 			= null;	//更新者社員ID
	private String update_user_nm 			= null;	//更新者社員名
	private String delete_fg 				= null;	//削除フラグ

	private String chk_fg 					= null;	//仕入先/配送先　店別マスタの存在チェック
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
	 * mst470101_HakoKanriBeanを１件のみ抽出したい時に使用する
	 */
	public static mst470101_HakoKanriBean getRHAKOKANRIBean(DataHolder dataHolder)
	{
		mst470101_HakoKanriBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst470101_HakoKanriDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst470101_HakoKanriBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓	
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
	
	// bumon_kanji_rnに対するセッターとゲッターの集合
	public boolean setBumonKanjiRn(String bumon_kanji_rn)
	{
		this.bumon_kanji_rn = bumon_kanji_rn;
		return true;
	}
	public String getBumonKanjiRn()
	{
		return cutString(this.bumon_kanji_rn,BUMON_KANJI_RN_MAX_LENGTH);
	}
	public String getBumonKanjiRnString()
	{
		return "'" + cutString(this.bumon_kanji_rn,BUMON_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getBumonKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_kanji_rn,BUMON_KANJI_RN_MAX_LENGTH));
	}
	
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	
	// kanri_kbに対するセッターとゲッターの集合
//  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓
	// kanri_cdに対するセッターとゲッターの集合
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
//	}
//	public String getKanriCdString()
//	{
//		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
//	}
	
	// kanri_kanji_rnに対するセッターとゲッターの集合
//	public boolean setKanriKanjiRn(String kanri_kanji_rn)
//	{
//		this.kanri_kanji_rn = kanri_kanji_rn;
//		return true;
//}
//	public String getKanriKanjiRn()
//	{
//		return cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH);
//	}
//	public String getKanriKanjiRnString()
//	{
//	return "'" + cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getKanriKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH));
//	}
//  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑	

	// shihai_kbに対するセッターとゲッターの集合
	public boolean setShihaiKb(String shihai_kb)
	{
		this.shihai_kb = shihai_kb;
		return true;
	}
	public String getShihaiKb()
	{
		return cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH);
	}
	public String getShihaiKbString()
	{
		return "'" + cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH) + "'";
	}
	public String getShihaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_kb,SHIHAI_KB_MAX_LENGTH));
	}


	// shihai_cdに対するセッターとゲッターの集合
	public boolean setShihaiCd(String shihai_cd)
	{
		this.shihai_cd = shihai_cd;
		return true;
	}
	public String getShihaiCd()
	{
		return cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH);
	}
	public String getShihaiCdString()
	{
		return "'" + cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH) + "'";
	}
	public String getShihaiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_cd,SHIHAI_CD_MAX_LENGTH));
	}

	// shihai_kanji_rnに対するセッターとゲッターの集合
	public boolean setShihaiKanjiRn(String shihai_kanji_rn)
	{
		this.shihai_kanji_rn = shihai_kanji_rn;
		return true;
	}
	public String getShihaiKanjiRn()
	{
		return cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH);
	}
	public String getShihaiKanjiRnString()
	{
		return "'" + cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getShihaiKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.shihai_kanji_rn,SHIHAI_KANJI_RN_MAX_LENGTH));
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
	
	// tenpo_kanji_rnに対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
		return cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH);
	}
	public String getTenpoKanjiRnString()
	{
		return "'" + cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getTenpoKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH));
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


	// web_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setWebHaisinsakiCd(String web_haisinsaki_cd)
	{
		this.web_haisinsaki_cd = web_haisinsaki_cd;
		return true;
	}
	public String getWebHaisinsakiCd()
	{
		return cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getWebHaisinsakiCdString()
	{
		return "'" + cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getWebHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.web_haisinsaki_cd,WEB_HAISINSAKI_CD_MAX_LENGTH));
	}


	// jca_haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setJcaHaisinsakiCd(String jca_haisinsaki_cd)
	{
		this.jca_haisinsaki_cd = jca_haisinsaki_cd;
		return true;
	}
	public String getJcaHaisinsakiCd()
	{
		return cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getJcaHaisinsakiCdString()
	{
		return "'" + cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getJcaHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jca_haisinsaki_cd,JCA_HAISINSAKI_CD_MAX_LENGTH));
	}

/****************************************************************************************************
 * 							(2005/05/12)新ER対応    追加項目	@author sawabe
 ****************************************************************************************************/	

	/**
	 * 発行区分
	 * hakou_kb に対するセッターとゲッターの集合
	 * @return
	 */
	public boolean setHakouKb(String hakou_kb) {
		this.hakou_kb = hakou_kb;
		return true;
	}
	public String getHakouKb() {
		return cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH);
	}
	public String getHakouKbString() {
		return "'" + cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH) + "'";
	}
	public String getHakouKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hakou_kb, this.HAKOU_KB_MAX_LENGTH));
	}

	/**
	 * 発行場所コード
	 * hakou_basyo_cd に対するセッターとゲッターの集合
	 * @return
	 */
	public boolean setHakouBasyoCd(String hakou_basyo_cd) {
		this.hakou_basyo_cd = hakou_basyo_cd;
		return true;
	}
	public String getHakouBasyoCd() {
		return cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH);
	}
	public String getHakouBasyoCdString() {
		return "'" + cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH) + "'";
	}
	public String getHakouBasyoCdHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hakou_basyo_cd, this.HAKOU_BASYO_CD_MAX_LENGTH));
	}

/****************************************************************************************************
 * 							(2005/05/12)新ER対応    追加名修正	@author sawabe
 ****************************************************************************************************/	
	/**
	 * 商品マスタ登録情報媒体区分
	 * syohin_toroku_batai_kbに対するセッターとゲッターの集合
	 */
	public boolean setSyohinTorokuBataiKb(String syohin_toroku_kb) {
		this.syohin_toroku_batai_kb = syohin_toroku_kb;
		return true;
	}
	public String getSyohinTorokuBataiKb()
	{
		return cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH);
	}
	public String getSyohinTorokuBataiKbString()
	{
		return "'" + cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinTorokuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_toroku_batai_kb, SYOHIN_TOROKU_BATAI_KB_MAX_LENGTH));
	}


	/**
	 * 初回導入提案媒体区分
	 * syohin_syokai_donyu_batai_kbに対するセッターとゲッターの集合
	 */
	public boolean setSyohinSyokaiDonyuBataiKb(String syohin_syokai_donyu_batai_kb) {
		this.syohin_syokai_donyu_batai_kb = syohin_syokai_donyu_batai_kb;
		return true;
	}
	public String getSyohinSyokaiDonyuBataiKb()
	{
		return cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH);
	}
	public String getSyohinSyokaiDonyuBataiKbString()
	{
		return "'" + cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSyohinSyokaiDonyuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_syokai_donyu_batai_kb, SYOHIN_SYOKAI_DONYU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 発注媒体区分
	 * hachu_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHachuBataiKb(String hachu_batai_kb) {
		this.hachu_batai_kb = hachu_batai_kb;
		return true;
	}
	public String getHachuBataiKb()
	{
		return cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH);
	}
	public String getHachuBataiKbString()
	{
		return "'" + cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_batai_kb, HACHU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 納品(ASN)媒体区分
	 * nohin_asn_kb に対するセッターとゲッターの集合
	 */
	public boolean setNohinAsnBataiKb(String nohin_asn_batai_kb) {
		this.nohin_asn_batai_kb = nohin_asn_batai_kb;
		return true;
	}
	public String getNohinAsnBataiKb() {
		return cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH);
	}
	public String getNohinAsnBataiKbString() {
		return "'" + cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getNohinAsnBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.nohin_asn_batai_kb, NOHIN_ASN_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 欠品媒体区分
	 * kepin_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setKepinBataiKb(String kepin_batai_kb) {
		this.kepin_batai_kb = kepin_batai_kb;
		return true;
	}
	public String getKepinBataiKb() {
		return cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH);
	}
	public String getKepinBataiKbString() {
		return "'" + cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getKepinBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.kepin_batai_kb, KEPIN_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 仕入計上媒体区分
	 * siire_keijo_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSiireKeijoBataiKb(String siire_keijo_batai_kb) {
		this.siire_keijo_batai_kb = siire_keijo_batai_kb;
		return true;
	}
	public String getSiireKeijoBataiKb() {
		return cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH);
	}
	public String getSiireKeijoBataiKbString() {
		return "'" + cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSiireKeijoBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siire_keijo_batai_kb, SIIRE_KEIJO_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 請求媒体区分
	 * seikyu_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSeikyuBataiKb(String seikyu_batai_kb) {
		this.seikyu_batai_kb = seikyu_batai_kb;
		return true;
	}
	public String getSeikyuBataiKb() {
		return cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH);
	}
	public String getSeikyuBataiKbString()
	{
		return "'" + cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSeikyuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.seikyu_batai_kb, SEIKYU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 支払媒体区分
	 * siharai_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setSiharaiBataiKb(String siharai_batai_kb) {
		this.siharai_batai_kb = siharai_batai_kb;
		return true;
	}
	public String getSiharaiBataiKb() {
		return cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH);
	}
	public String getSiharaiBataiKbString() {
		return "'" + cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getSiharaiBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.siharai_batai_kb, SIHARAI_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 販売媒体区分
	 * hanbai_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHanbaiBataiKb(String hanbai_batai_kb) {
		this.hanbai_batai_kb = hanbai_batai_kb;
		return true;
	}
	public String getHanbaiBataiKb() {
		return cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH);
	}
	public String getHanbaiBataiKbString() {
		return "'" + cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiBataiKbHTMLString() {
		return HTMLStringUtil.convert(cutString(this.hanbai_batai_kb, HANBAI_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 発注勧告媒体区分
	 * hachu_kankoku_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setHachuKankokuBataiKb(String hachu_kankoku_batai_kb)
	{
		this.hachu_kankoku_batai_kb = hachu_kankoku_batai_kb;
		return true;
	}
	public String getHachuKankokuBataiKb()
	{
		return cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH);
	}
	public String getHachuKankokuBataiKbString()
	{
		return "'" + cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKankokuBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kankoku_batai_kb, HACHU_KANKOKU_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * 在庫媒体区分
	 * zaiko_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setZaikoBataiKb(String zaiko_batai_kb) {
		this.zaiko_batai_kb = zaiko_batai_kb;
		return true;
	}
	public String getZaikoBataiKb() {
		return cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH);
	}
	public String getZaikoBataiKbString()
	{
		return "'" + cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getZaikoBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.zaiko_batai_kb, ZAIKO_BATAI_KB_MAX_LENGTH));
	}

	/**
	 * タグ媒体区分
	 * tag_batai_kb に対するセッターとゲッターの集合
	 */
	public boolean setTagBataiKb(String tag_kb)
	{
		this.tag_batai_kb = tag_kb;
		return true;
	}
	public String getTagBataiKb()
	{
		return cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH);
	}
	public String getTagBataiKbString()
	{
		return "'" + cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH) + "'";
	}
	public String getTagBataiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tag_batai_kb, TAG_BATAI_KB_MAX_LENGTH));
	}
/******************************* (2005/05/12) 新ER対応 end *************************************/

	// syukanhachuyotei_kbに対するセッターとゲッターの集合
	public boolean setSyukanhachuyoteiKb(String syukanhachuyotei_kb)
	{
		this.syukanhachuyotei_kb = syukanhachuyotei_kb;
		return true;
	}
	public String getSyukanhachuyoteiKb()
	{
		return cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH);
	}
	public String getSyukanhachuyoteiKbString()
	{
		return "'" + cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH) + "'";
	}
	public String getSyukanhachuyoteiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukanhachuyotei_kb,SYUKANHACHUYOTEI_KB_MAX_LENGTH));
	}


	// tenmeisai_kbに対するセッターとゲッターの集合
	public boolean setTenmeisaiKb(String tenmeisai_kb)
	{
		this.tenmeisai_kb = tenmeisai_kb;
		return true;
	}
	public String getTenmeisaiKb()
	{
		return cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH);
	}
	public String getTenmeisaiKbString()
	{
		return "'" + cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH) + "'";
	}
	public String getTenmeisaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenmeisai_kb,TENMEISAI_KB_MAX_LENGTH));
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

	// chk_fgに対するセッターとゲッターの集合
	public boolean setChkFg(String chk_fg)
	{
		this.chk_fg = chk_fg;
		return true;
	}
	public String getChkFg()
	{
		return this.chk_fg;
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return
//	  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓		
//		return "  kanri_kb = " + this.getKanriKbString()
//			+ "  kanri_cd = " + this.getKanriCdString()
//			+ "  shihai_kb = " + this.getShihaiKbString()
			"bumon_cd="+this.getBumonCd()
//	  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑		
			+"  shihai_kb = " + this.getShihaiKbString()
			+ "  shihai_cd = " + this.getShihaiCdString()
			+ "  tenpo_cd = " + this.getTenpoCdString()
			+ "  yuko_dt = " + this.getYukoDtString()
			+ "  web_haisinsaki_cd = " + this.getWebHaisinsakiCdString()
			+ "  jca_haisinsaki_cd = " + this.getJcaHaisinsakiCdString()
			
			//新ER対応 *** 項目追加 start ***
			+ "  hakou_kb = " + this.getHakouKbString()			//発行区分
			+ "  hakou_basyo_cd = " + this.getHakouBasyoCdString()	//発行場所コード             
			//新ER対応 *** 項目追加 end ***
	
			//新ER対応 *** 項目名変更 start ***
			+ "  syohin_toroku_batai_kb = " + this.getSyohinTorokuBataiKbString() //商品ﾏｽﾀ登録情報媒体区分
			+ "  syohin_syokai_donyu_batai_kb = " + this.getSyohinSyokaiDonyuBataiKbString()//初回導入提案媒体区分
			+ "  hachu_batai_kb = " + this.getHachuBataiKbString()                //発注媒体区分
			+ "  nohin_asn_batai_kb = " + this.getNohinAsnBataiKbString()         //納品(ASN)媒体区分
			+ "  kepin_batai_kb = " + this.getKepinBataiKbString()                //欠品媒体区分
			+ "  siire_keijo_batai_kb = " + this.getSiireKeijoBataiKbString()     //仕入計上媒体区分
			+ "  seikyu_batai_kb = " + this.getSeikyuBataiKbString()              //請求媒体区分
			+ "  siharai_batai_kb = " + this.getSiharaiBataiKbString()            //支払媒体区分
			+ "  hanbai_batai_kb = " + this.getHanbaiBataiKbString()              //販売媒体区分
			+ "  hachu_kankoku_batai_kb = " + this.getHachuKankokuBataiKbString() //発注勧告媒体区分
			+ "  zaiko_batai_kb = " + this.getZaikoBataiKbString()                //在庫媒体区分
			+ "  tag_batai_kb = " + this.getTagBataiKbString()                    //タグ媒体区分
			//新ER対応 *** 項目名変更 end ***
			
/*
			+ "  syohin_toroku_kb = " + getSyohinTorokuKbString()
			+ "  syohin_syokai_donyu_kb = " + getSyohinSyokaiDonyuKbString()
			+ "  hachu_kb = " + getHachuKbString()
			+ "  nohin_asn_kb = " + getNohinAsnKbString()
			+ "  kepin_kb = " + getKepinKbString()
			+ "  siire_keijo_kb = " + getSiireKeijoKbString()
			+ "  seikyu_kb = " + getSeikyuKbString()
			+ "  siharai_kb = " + getSiharaiKbString()
			+ "  hanbai_kb = " + getHanbaiKbString()
			+ "  hachu_kankoku_kb = " + getHachuKankokuKbString()
			+ "  zaiko_kb = " + getZaikoKbString()
			+ "  tag_kb = " + getTagKbString()
*/
			+ "  syukanhachuyotei_kb = " + this.getSyukanhachuyoteiKbString()
			+ "  tenmeisai_kb = " + this.getTenmeisaiKbString()
			+ "  insert_ts = " + this.getInsertTsString()
			+ "  insert_user_id = " + this.getInsertUserIdString()
			+ "  update_ts = " + this.getUpdateTsString()
			+ "  update_user_id = " + this.getUpdateUserIdString()
			+ "  delete_fg = " + this.getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RHAKOKANRIBean コピー後のクラス
	 */
	public mst470101_HakoKanriBean createClone()
	{
		mst470101_HakoKanriBean bean = new mst470101_HakoKanriBean();
//		  ↓↓2006.06.16 zhangxia カスタマイズ修正↓↓		
//		bean.setKanriKb(this.kanri_kb);
//		bean.setKanriCd(this.kanri_cd);
		bean.setBumonCd(this.bumon_cd);
//	  ↑↑2006.06.16 zhangxia カスタマイズ修正↑↑		
		bean.setShihaiKb(this.shihai_kb);
		bean.setShihaiCd(this.shihai_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setWebHaisinsakiCd(this.web_haisinsaki_cd);
		bean.setJcaHaisinsakiCd(this.jca_haisinsaki_cd);
		
		//新ER対応 *** 項目追加 start ***
		bean.setHakouKb(this.hakou_kb);			//発行区分
		bean.setHakouBasyoCd(this.hakou_basyo_cd);	//発行場所コード             
		//新ER対応 *** 項目追加 end ***
	
		//新ER対応 *** 項目名変更 start ***
		bean.setSyohinTorokuBataiKb(this.syohin_toroku_batai_kb);				//商品ﾏｽﾀ登録情報媒体区分
		bean.setSyohinSyokaiDonyuBataiKb(this.syohin_syokai_donyu_batai_kb);	//初回導入提案媒体区分
		bean.setHachuBataiKb(this.hachu_batai_kb);								//発注媒体区分
		bean.setNohinAsnBataiKb(this.nohin_asn_batai_kb);						//納品(ASN)媒体区分
		bean.setKepinBataiKb(this.kepin_batai_kb);              				//欠品媒体区分
		bean.setSiireKeijoBataiKb(this.siire_keijo_batai_kb);					//仕入計上媒体区分
		bean.setSeikyuBataiKb(this.seikyu_batai_kb);							//請求媒体区分
		bean.setSiharaiBataiKb(this.siharai_batai_kb);							//支払媒体区分
		bean.setHanbaiBataiKb(this.hanbai_batai_kb);							//販売媒体区分
		bean.setHachuKankokuBataiKb(this.hachu_kankoku_batai_kb);				//発注勧告媒体区分
		bean.setZaikoBataiKb(this.zaiko_batai_kb);								//在庫媒体区分
		bean.setTagBataiKb(this.tag_batai_kb);									//タグ媒体区分
		//新ER対応 *** 項目名変更 end ***
		
/*
		bean.setSyohinTorokuKb(this.syohin_toroku_kb);
		bean.setSyohinSyokaiDonyuKb(this.syohin_syokai_donyu_kb);
		bean.setHachuKb(this.hachu_kb);
		bean.setNohinAsnKb(this.nohin_asn_kb);
		bean.setKepinKb(this.kepin_kb);
		bean.setSiireKeijoKb(this.siire_keijo_kb);
		bean.setSeikyuKb(this.seikyu_kb);
		bean.setSiharaiKb(this.siharai_kb);
		bean.setHanbaiKb(this.hanbai_kb);
		bean.setHachuKankokuKb(this.hachu_kankoku_kb);
		bean.setZaikoKb(this.zaiko_kb);
		bean.setTagKb(this.tag_kb);
*/
		bean.setSyukanhachuyoteiKb(this.syukanhachuyotei_kb);
		bean.setTenmeisaiKb(this.tenmeisai_kb);
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
		if( !( o instanceof mst470101_HakoKanriBean ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
