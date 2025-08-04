/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst290401用セット商品マスタ登録画面項目(詳細一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst290401用セット商品マスタ登録画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author k.hara
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst290401用セット商品マスタ登録画面項目(詳細一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst290401用セット商品マスタ登録画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author k.hara
 * @version 1.0
 * @see なし								
 */
public class mst290401_KeepMeisaiBean
{

	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KANJI_RN_MAX_LENGTH = 20;		//販区名称
	
	public static final int HINMEI_KANJI_RN_MAX_LENGTH = 80;		//商品名称	

	public static final int SYOHIN_1_CD_MAX_LENGTH = 13;			// 商品コード

	public static final int HONSU_1_QT_MAX_LENGTH = 2;			// 本数
	
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int INSERT_USER_ID_MAX_LENGTH = 8;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 8;
//	public static final int DELETE_FG_MAX_LENGTH = 1;



//	画面明細表示項目

  	private String hanku_1_cd;			//販区コード1
	private String kanji_na_1;			//販区名1
	private String syohin_1_cd;		//商品コード1
	private String hinmei_kanji_rn_1;	//商品名称1
	private String honsu_1_qt;			//本数1
	private boolean h_eflg_1;			//販区コードエラーフラグ1
	private boolean s_eflg_1;			//商品コードエラーフラグ1
	private boolean meisyoflg_1;		//名称取得フラグ1
 	
	private String hanku_2_cd;			//販区コード2
	private String kanji_na_2;			//販区名2
	private String syohin_2_cd;		//商品コード2
	private String hinmei_kanji_rn_2;	//商品名称2
	private String honsu_2_qt;			//本数2
	private boolean h_eflg_2;			//販区コードエラーフラグ2
	private boolean s_eflg_2;			//商品コードエラーフラグ2
	private boolean meisyoflg_2;		//名称取得フラグ2
	
	private String hanku_3_cd;			//販区コード3
	private String kanji_na_3;			//販区名3
	private String syohin_3_cd;		//商品コード3
	private String hinmei_kanji_rn_3;	//商品名称3
	private String honsu_3_qt;			//本数3
	private boolean h_eflg_3;			//販区コードエラーフラグ3
	private boolean s_eflg_3;			//商品コードエラーフラグ3
	private boolean meisyoflg_3;		//名称取得フラグ3
	
	private String hanku_4_cd;			//販区コード4
	private String kanji_na_4;			//販区名1
	private String syohin_4_cd;		//商品コード1
	private String hinmei_kanji_rn_4;	//商品名称1
	private String honsu_4_qt;			//本数1
	private boolean h_eflg_4;			//販区コードエラーフラグ4
	private boolean s_eflg_4;			//商品コードエラーフラグ4
	private boolean meisyoflg_4;		//名称取得フラグ4
	
	private String hanku_5_cd;			//販区コード5
	private String kanji_na_5;			//販区名5
	private String syohin_5_cd;		//商品コード5
	private String hinmei_kanji_rn_5;	//商品名称5
	private String honsu_5_qt;			//本数5
	private boolean h_eflg_5;			//販区コードエラーフラグ5
	private boolean s_eflg_5;			//商品コードエラーフラグ5
	private boolean meisyoflg_5;		//名称取得フラグ5

	private String hanku_6_cd;			//販区コード6
	private String kanji_na_6;			//販区名6
	private String syohin_6_cd;		//商品コード6
	private String hinmei_kanji_rn_6;	//商品名称6
	private String honsu_6_qt;			//本数6
	private boolean h_eflg_6;			//販区コードエラーフラグ6
	private boolean s_eflg_6;			//商品コードエラーフラグ6
	private boolean meisyoflg_6;		//名称取得フラグ6

	private String hanku_7_cd;			//販区コード7
	private String kanji_na_7;			//販区名7
	private String syohin_7_cd;		//商品コード7
	private String hinmei_kanji_rn_7;	//商品名称7
	private String honsu_7_qt;			//本数7
	private boolean h_eflg_7;			//販区コードエラーフラグ7
	private boolean s_eflg_7;			//商品コードエラーフラグ7
	private boolean meisyoflg_7;		//名称取得フラグ7

	private String hanku_8_cd;			//販区コード8
	private String kanji_na_8;			//販区名8
	private String syohin_8_cd;		//商品コード8
	private String hinmei_kanji_rn_8;	//商品名称8
	private String honsu_8_qt;			//本数8
	private boolean h_eflg_8;			//販区コードエラーフラグ8
	private boolean s_eflg_8;			//商品コードエラーフラグ8
	private boolean meisyoflg_8;		//名称取得フラグ8

	private String hanku_9_cd;			//販区コード9
	private String kanji_na_9;			//販区名9
	private String syohin_9_cd;		//商品コード9
	private String hinmei_kanji_rn_9;	//商品名称9
	private String honsu_9_qt;			//本数9
	private boolean h_eflg_9;			//販区コードエラーフラグ9
	private boolean s_eflg_9;			//商品コードエラーフラグ9
	private boolean meisyoflg_9;		//名称取得フラグ9
	
	private String hanku_10_cd;		//販区コード10
	private String kanji_na_10;		//販区名10
	private String syohin_10_cd;		//商品コード10
	private String hinmei_kanji_rn_10;	//商品名称10
	private String honsu_10_qt;		//本数10
	private boolean h_eflg_10;		//販区コードエラーフラグ10
	private boolean s_eflg_10;		//商品コードエラーフラグ10
	private boolean meisyoflg_10;		//名称取得フラグ10
	
	private String hanku_11_cd;		//販区コード11
	private String kanji_na_11;		//販区名11
	private String syohin_11_cd;		//商品コード11
	private String hinmei_kanji_rn_11;	//商品名称11
	private String honsu_11_qt;		//本数11
	private boolean h_eflg_11;		//販区コードエラーフラグ11
	private boolean s_eflg_11;		//商品コードエラーフラグ11
	private boolean meisyoflg_11;		//名称取得フラグ11

	private String hanku_12_cd;		//販区コード12
	private String kanji_na_12;		//販区名12	
	private String syohin_12_cd;		//商品コード12
	private String hinmei_kanji_rn_12;	//商品名称12
	private String honsu_12_qt;		//本数12
	private boolean h_eflg_12;		//販区コードエラーフラグ12
	private boolean s_eflg_12;		//商品コードエラーフラグ12
	private boolean meisyoflg_12;		//名称取得フラグ12
	
  	private String insert_ts;			//作成年月日
  	private String insert_user_id;		//作成者社員ID
  	private String insert_user_na;		//作成者名
  	private String update_ts;			//更新年月日
  	private String update_user_id;		//更新者社員ID
	private String henko_dt;			//更新年月日
	private String sinki_toroku_dt;	//更新者社員ID
//  	private String delete_fg;			//削除フラグ

	
	/**
	 * コンストラクタ
	 */
	public mst290401_KeepMeisaiBean () {

//各明細一覧項目に空文字をセットする
		
		this.hanku_1_cd      	= "";	//販区コード
		this.hanku_2_cd      	= "";	//販区コード
		this.hanku_3_cd      	= "";	//販区コード
		this.hanku_4_cd      	= "";	//販区コード
		this.hanku_5_cd      	= "";	//販区コード
		this.hanku_6_cd      	= "";	//販区コード
		this.hanku_7_cd      	= "";	//販区コード
		this.hanku_8_cd      	= "";	//販区コード
		this.hanku_9_cd      	= "";	//販区コード
		this.hanku_10_cd      	= "";	//販区コード
		this.hanku_11_cd      	= "";	//販区コード
		this.hanku_12_cd      	= "";	//販区コード
		
				
		this. kanji_na_1= "";			//販区名1
		this. kanji_na_2= "";			//販区名1
		this. kanji_na_3= "";			//販区名1
		this. kanji_na_4= "";			//販区名1
		this. kanji_na_5= "";			//販区名1
		this. kanji_na_6= "";			//販区名1
		this. kanji_na_7= "";			//販区名1
		this. kanji_na_8= "";			//販区名1
		this. kanji_na_9= "";			//販区名1
		this. kanji_na_10= "";			//販区名1
		this. kanji_na_11= "";			//販区名1
		this. kanji_na_12= "";			//販区名1
		
		this.syohin_1_cd= "";		//商品コード1
		this.syohin_2_cd= "";		//商品コード1
		this.syohin_3_cd= "";		//商品コード1
		this.syohin_4_cd= "";		//商品コード1
		this.syohin_5_cd= "";		//商品コード1
		this.syohin_6_cd= "";		//商品コード1
		this.syohin_7_cd= "";		//商品コード1
		this.syohin_8_cd= "";		//商品コード1
		this.syohin_9_cd= "";		//商品コード1
		this.syohin_10_cd= "";		//商品コード1
		this.syohin_11_cd= "";		//商品コード1
		this.syohin_12_cd= "";		//商品コード1
		
		
		this.hinmei_kanji_rn_1    = "";	//商品名称
		this.hinmei_kanji_rn_2    = "";	//商品名称
		this.hinmei_kanji_rn_3    = "";	//商品名称
		this.hinmei_kanji_rn_4    = "";	//商品名称
		this.hinmei_kanji_rn_5    = "";	//商品名称
		this.hinmei_kanji_rn_6    = "";	//商品名称
		this.hinmei_kanji_rn_7    = "";	//商品名称
		this.hinmei_kanji_rn_8    = "";	//商品名称
		this.hinmei_kanji_rn_9    = "";	//商品名称
		this.hinmei_kanji_rn_10    = "";	//商品名称
		this.hinmei_kanji_rn_11    = "";	//商品名称
		this.hinmei_kanji_rn_12    = "";	//商品名称
		
		this.honsu_1_qt		="";	//本数1
		this.honsu_2_qt		="";	//本数2
		this.honsu_3_qt		="";	//本数3
		this.honsu_4_qt		="";	//本数4
		this.honsu_5_qt		="";	//本数5
		this.honsu_6_qt		="";	//本数6
		this.honsu_7_qt		="";	//本数7
		this.honsu_8_qt		="";	//本数8
		this.honsu_9_qt		="";	//本数9
		this.honsu_10_qt		="";	//本数10
		this.honsu_11_qt		="";	//本数11
		this.honsu_12_qt		="";	//本数12
		
		this.h_eflg_1			=true;	//販区エラーフラグ1
		this.h_eflg_2			=true;	//販区エラーフラグ2
		this.h_eflg_3			=true;	//販区エラーフラグ3
		this.h_eflg_4			=true;	//販区エラーフラグ4
		this.h_eflg_5			=true;	//販区エラーフラグ5
		this.h_eflg_6			=true;	//販区エラーフラグ6
		this.h_eflg_7			=true;	//販区エラーフラグ7
		this.h_eflg_8			=true;	//販区エラーフラグ8
		this.h_eflg_9			=true;	//販区エラーフラグ9
		this.h_eflg_10			=true;	//販区エラーフラグ10
		this.h_eflg_11			=true;	//販区エラーフラグ11
		this.h_eflg_12			=true;	//販区エラーフラグ12		

		this.s_eflg_1			=true;	//商品エラーフラグ1
		this.s_eflg_2			=true;	//商品エラーフラグ2
		this.s_eflg_3			=true;	//商品エラーフラグ3
		this.s_eflg_4			=true;	//商品エラーフラグ4
		this.s_eflg_5			=true;	//商品エラーフラグ5
		this.s_eflg_6			=true;	//商品エラーフラグ6
		this.s_eflg_7			=true;	//商品エラーフラグ7
		this.s_eflg_8			=true;	//商品エラーフラグ8
		this.s_eflg_9			=true;	//商品エラーフラグ9
		this.s_eflg_10			=true;	//商品エラーフラグ10
		this.s_eflg_11			=true;	//商品エラーフラグ11
		this.s_eflg_12			=true;	//商品エラーフラグ12
		
		this.meisyoflg_1		=true;	//名称取得表示用判別フラグ1
		this.meisyoflg_2		=true;	//名称取得表示用判別フラグ2
		this.meisyoflg_3		=true;	//名称取得表示用判別フラグ3
		this.meisyoflg_4		=true;	//名称取得表示用判別フラグ4
		this.meisyoflg_5		=true;	//名称取得表示用判別フラグ5
		this.meisyoflg_6		=true;	//名称取得表示用判別フラグ6
		this.meisyoflg_7		=true;	//名称取得表示用判別フラグ7
		this.meisyoflg_8		=true;	//名称取得表示用判別フラグ8
		this.meisyoflg_9		=true;	//名称取得表示用判別フラグ9
		this.meisyoflg_10		=true;	//名称取得表示用判別フラグ10
		this.meisyoflg_11		=true;	//名称取得表示用判別フラグ11
		this.meisyoflg_12		=true;	//名称取得表示用判別フラグ12				
		
		this.insert_ts   		= "";	//作成年月日
		this.insert_user_id  	= "";	//作成者社員ID
		this.update_ts       	= "";	//作成者名
		this.update_user_id 	= "";	//更新年月日
		this.henko_dt 			= "";	//変更日
		this.sinki_toroku_dt 	= "";	//新規登録日
//		this.delete_fg 		= "";	//削除フラグ
		
	}
	
	 
//******** 商品コード表示用 ***************************************************
//商品コード画面詳細表示用(各行別表示）
							// i : 何行目　string : 項目の番号
	public void setSyohinCd(int i ,String string){
		switch (i) {
			case 1 :
			 	this.syohin_1_cd = string;
				break;
			case 2 :
				this.syohin_2_cd = string;
				break;
			case 3 :
				this.syohin_3_cd = string;
				break;
			case 4 :
				this.syohin_4_cd = string;
				break;
			case 5 :
				this.syohin_5_cd = string;
				break;
			case 6 :
				this.syohin_6_cd = string;
				break;
			case 7 :
				this.syohin_7_cd = string;
				break;
			case 8 :
				this.syohin_8_cd = string;
				break;
			case 9 :
				this.syohin_9_cd = string;
				break;
			case 10 :
				this.syohin_10_cd = string;
				break;
			case 11 :
				this.syohin_11_cd = string;
				break;
			case 12 :
				this.syohin_12_cd = string;
				break;
			default :
				break;
		}
	}

	public String getSyohinCd(int i ){
		
		String string = " ";
		
		switch (i) {
			case 1 :
				string = this.syohin_1_cd != null?  this.syohin_1_cd : "" ;
				break;
			case 2 :
				string = this.syohin_2_cd != null?  this.syohin_2_cd : "" ;
				break;
			case 3 :
				string = this.syohin_3_cd != null?  this.syohin_3_cd : "" ;
				break;
			case 4 :
				string = this.syohin_4_cd != null?  this.syohin_4_cd : "" ;
				break;
			case 5 :
				string = this.syohin_5_cd != null?  this.syohin_5_cd : "" ;
				break;
			case 6 :
				string = this.syohin_6_cd != null?  this.syohin_6_cd : "" ;
				break;
			case 7 :
				string = this.syohin_7_cd != null?  this.syohin_7_cd : "" ;
				break;
			case 8 :
				string = this.syohin_8_cd != null?  this.syohin_8_cd : "" ;
				break;
			case 9 :
				string = this.syohin_9_cd != null?  this.syohin_9_cd : "" ;
				break;
			case 10 :
				string = this.syohin_10_cd != null?  this.syohin_10_cd : "" ;
				break;
			case 11 :
				string = this.syohin_11_cd != null?  this.syohin_11_cd : "" ;
				break;
			case 12 :
				string = this.syohin_12_cd != null?  this.syohin_12_cd : "" ;
				break;
			default :
				string = "" ;
				break;
		}
		return string;
	}


	 //	syohin_1_cdに対するセッターとゲッターの集合
	 //	セット商品コード1
	 public boolean setSyohin1Cd(String setSyohin1Cd)
	 {
		 this.syohin_1_cd = setSyohin1Cd;
		 return true;
	 }
	 public String getSyohin1Cd()
	 {
		 return cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH);
	 }
	 public String getSyohin1CdString()
	 {
		 return "'" + cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	 }
	 public String getSyohin1CdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH));
	 }
	 
	//	syohin_2_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin2Cd(String setSyohin2Cd)
	{
		this.syohin_2_cd = setSyohin2Cd;
		return true;
	}
	public String getSyohin2Cd()
	{
		return cutString(this.syohin_2_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin2CdString()
	{
		return "'" + cutString(this.syohin_2_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin2CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_2_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_1_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin3Cd(String setSyohin3Cd)
	{
		this.syohin_3_cd = setSyohin3Cd;
		return true;
	}
	public String getSyohin3Cd()
	{
		return cutString(this.syohin_3_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin3CdString()
	{
		return "'" + cutString(this.syohin_3_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin3CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_3_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_4_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin4Cd(String setSyohin4Cd)
	{
		this.syohin_4_cd = setSyohin4Cd;
		return true;
	}
	public String getSyohin4Cd()
	{
		return cutString(this.syohin_4_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin4CdString()
	{
		return "'" + cutString(this.syohin_4_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin4CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_4_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_5_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin5Cd(String setSyohin5Cd)
	{
		this.syohin_5_cd = setSyohin5Cd;
		return true;
	}
	public String getSyohin5Cd()
	{
		return cutString(this.syohin_5_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin5CdString()
	{
		return "'" + cutString(this.syohin_5_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin5CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_5_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_6_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin6Cd(String setSyohin6Cd)
	{
		this.syohin_6_cd = setSyohin6Cd;
		return true;
	}
	public String getSyohin6Cd()
	{
		return cutString(this.syohin_6_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin6CdString()
	{
		return "'" + cutString(this.syohin_6_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin6CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_6_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_7_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin7Cd(String setSyohin7Cd)
	{
		this.syohin_7_cd = setSyohin7Cd;
		return true;
	}
	public String getSyohin7Cd()
	{
		return cutString(this.syohin_7_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin7CdString()
	{
		return "'" + cutString(this.syohin_7_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin7CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_7_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_8_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin8Cd(String setSyohin8Cd)
	{
		this.syohin_8_cd = setSyohin8Cd;
		return true;
	}
	public String getSyohin8Cd()
	{
		return cutString(this.syohin_8_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin8CdString()
	{
		return "'" + cutString(this.syohin_8_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin8CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_8_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_9_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin9Cd(String setSyohin9Cd)
	{
		this.syohin_9_cd = setSyohin9Cd;
		return true;
	}
	public String getSyohin9Cd()
	{
		return cutString(this.syohin_9_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin9CdString()
	{
		return "'" + cutString(this.syohin_9_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin9CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_9_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_10_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin10Cd(String setSyohin10Cd)
	{
		this.syohin_10_cd = setSyohin10Cd;
		return true;
	}
	public String getSyohin10Cd()
	{
		return cutString(this.syohin_10_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin10CdString()
	{
		return "'" + cutString(this.syohin_10_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin10CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_10_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_11_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin11Cd(String setSyohin11Cd)
	{
		this.syohin_11_cd = setSyohin11Cd;
		return true;
	}
	public String getSyohin11Cd()
	{
		return cutString(this.syohin_11_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin11CdString()
	{
		return "'" + cutString(this.syohin_11_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin11CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_11_cd,SYOHIN_1_CD_MAX_LENGTH));
	}
	
	//	syohin_12_cdに対するセッターとゲッターの集合
	//	セット商品コード1
	public boolean setSyohin12Cd(String setSyohin12Cd)
	{
		this.syohin_12_cd = setSyohin12Cd;
		return true;
	}
	public String getSyohin12Cd()
	{
		return cutString(this.syohin_12_cd,SYOHIN_1_CD_MAX_LENGTH);
	}
	public String getSyohin12CdString()
	{
		return "'" + cutString(this.syohin_12_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	}
	public String getSyohin12CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_12_cd,SYOHIN_1_CD_MAX_LENGTH));
	}



//	******* 販区コード *************************************************************************************

//	******** 販区コード表示用 ***************************************************
					 // i : 何行目　string : 項目の番号
	  public void setHankuCd(int i ,String string){
		  switch (i) {
			  case 1 :
				  this.hanku_1_cd = string;
				  break;
			  case 2 :
				  this.hanku_2_cd = string;
				  break;
			  case 3 :
				  this.hanku_3_cd = string;
				  break;
			  case 4 :
				  this.hanku_4_cd = string;
				  break;
			  case 5 :
				  this.hanku_5_cd = string;
				  break;
			  case 6 :
				  this.hanku_6_cd = string;
				  break;
			  case 7 :
				  this.hanku_7_cd = string;
				  break;
			  case 8 :
				  this.hanku_8_cd = string;
				  break;
			  case 9 :
				  this.hanku_9_cd = string;
				  break;
			  case 10 :
				  this.hanku_10_cd = string;
				  break;
			  case 11 :
				  this.hanku_11_cd = string;
				  break;
			  case 12 :
				  this.hanku_12_cd = string;
				  break;
			  default :
				  break;
		  }
	  }

	  public String getHankuCd(int i ){
		  String string = "";
		  switch (i) {
			  case 1 :
				  string = this.hanku_1_cd != null?  this.hanku_1_cd : "" ;
				  break;
			  case 2 :
				  string = this.hanku_2_cd != null?  this.hanku_2_cd : "" ;
				  break;
			  case 3 :
				  string = this.hanku_3_cd != null?  this.hanku_3_cd : "" ;
				  break;
			  case 4 :
				  string = this.hanku_4_cd != null?  this.hanku_4_cd : "" ;
				  break;
			  case 5 :
				  string = this.hanku_5_cd != null?  this.hanku_5_cd : "" ;
				  break;
			  case 6 :
				  string = this.hanku_6_cd != null?  this.hanku_6_cd : "" ;
				  break;
			  case 7 :
				  string = this.hanku_7_cd != null?  this.hanku_7_cd : "" ;
				  break;
			  case 8 :
				  string = this.hanku_8_cd != null?  this.hanku_8_cd : "" ;
				  break;
			  case 9 :
				  string = this.hanku_9_cd != null?  this.hanku_9_cd : "" ;
				  break;
			  case 10 :
				  string = this.hanku_10_cd != null?  this.hanku_10_cd : "" ;
				  break;
			  case 11 :
				  string = this.hanku_11_cd != null?  this.hanku_11_cd : "" ;
				  break;
			  case 12 :
				  string = this.hanku_12_cd != null?  this.hanku_12_cd : "" ;
				  break;
			  default :
				  string = "" ;
				  break;
		  }
		  return string;
	  }

	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd1(String hanku_1_cd)
		{
			this.hanku_1_cd = hanku_1_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd1()
		{
			return this.hanku_1_cd;
		}

	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd2(String hanku_2_cd)
		{
			this.hanku_2_cd = hanku_2_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd2()
		{
			return this.hanku_2_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd3(String hanku_3_cd)
		{
			this.hanku_3_cd = hanku_3_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd3()
		{
			return this.hanku_3_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd4(String hanku_4_cd)
		{
			this.hanku_4_cd = hanku_4_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd4()
		{
			return this.hanku_4_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd5(String hanku_5_cd)
		{
			this.hanku_5_cd = hanku_5_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd5()
		{
			return this.hanku_5_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd6(String hanku_6_cd)
		{
			this.hanku_6_cd = hanku_6_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd6()
		{
			return this.hanku_6_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd7(String hanku_7_cd)
		{
			this.hanku_7_cd = hanku_7_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd7()
		{
			return this.hanku_7_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd8(String hanku_8_cd)
		{
			this.hanku_8_cd = hanku_8_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd8()
		{
			return this.hanku_8_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd9(String hanku_9_cd)
		{
			this.hanku_9_cd = hanku_9_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd9()
		{
			return this.hanku_9_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd10(String hanku_10_cd)
		{
			this.hanku_10_cd = hanku_10_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd10()
		{
			return this.hanku_10_cd;
		}
		
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd11(String hanku_11_cd)
		{
			this.hanku_11_cd = hanku_11_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd11()
		{
			return this.hanku_11_cd;
		}
	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuCd12(String hanku_12_cd)
		{
			this.hanku_12_cd = hanku_12_cd;
			return true;
		}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuCd12()
		{
			return this.hanku_12_cd;
		}

//******* 販区名 *************************************************************************************

//******** 販名表示用 ***************************************************
				   // i : 何行目　string : 項目の番号
	public void setHankuKanjiRn(int i ,String string){
		switch (i) {
			case 1 :
				this.kanji_na_1 = string;
				break;
			case 2 :
				this.kanji_na_2 = string;
				break;
			case 3 :
				this.kanji_na_3 = string;
				break;
			case 4 :
				this.kanji_na_4 = string;
				break;
			case 5 :
				this.kanji_na_5 = string;
				break;
			case 6 :
				this.kanji_na_6 = string;
				break;
			case 7 :
				this.kanji_na_7 = string;
				break;
			case 8 :
				this.kanji_na_8 = string;
				break;
			case 9 :
				this.kanji_na_9 = string;
				break;
			case 10 :
				this.kanji_na_10 = string;
				break;
			case 11 :
				this.kanji_na_11 = string;
				break;
			case 12 :
				this.kanji_na_12 = string;
				break;
			default :
				break;
		}
	}

	public String getHankuKanjiRn(int i ){
		String string = "";
		switch (i) {
			case 1 :
				string = this.kanji_na_1 != null?  this.kanji_na_1 : "" ;
				break;
			case 2 :
				string = this.kanji_na_2 != null?  this.kanji_na_2 : "" ;
				break;
			case 3 :
				string = this.kanji_na_3 != null?  this.kanji_na_3 : "" ;
				break;
			case 4 :
				string = this.kanji_na_4 != null?  this.kanji_na_4 : "" ;
				break;
			case 5 :
				string = this.kanji_na_5 != null?  this.kanji_na_5 : "" ;
				break;
			case 6 :
				string = this.kanji_na_6 != null?  this.kanji_na_6 : "" ;
				break;
			case 7 :
				string = this.kanji_na_7 != null?  this.kanji_na_7 : "" ;
				break;
			case 8 :
				string = this.kanji_na_8 != null?  this.kanji_na_8 : "" ;
				break;
			case 9 :
				string = this.kanji_na_9 != null?  this.kanji_na_9 : "" ;
				break;
			case 10 :
				string = this.kanji_na_10 != null?  this.kanji_na_10 : "" ;
				break;
			case 11 :
				string = this.kanji_na_11 != null?  this.kanji_na_11 : "" ;
				break;
			case 12 :
				string = this.kanji_na_12 != null?  this.kanji_na_12 : "" ;
				break;
			default :
				string = "" ;
				break;
		}
		return string;
	}
	
	/**
	 * kanji_na_1に対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn1(String kanji_na_1)
	{
		this.kanji_na_1 = kanji_na_1;
		return true;
	}
	public String getHankuKanjiRn1()
	{
		return cutString(this.kanji_na_1,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn1String()
	{
		return "'" + cutString(this.kanji_na_1,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn1HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_1,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn2(String kanji_na_2)
	{
		this.kanji_na_2 = kanji_na_2;
		return true;
	}
	public String getHankuKanjiRn2()
	{
		return cutString(this.kanji_na_2,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn2String()
	{
		return "'" + cutString(this.kanji_na_2,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_2,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn3(String kanji_na_3)
	{
		this.kanji_na_3 = kanji_na_3;
		return true;
	}
	public String getHankuKanjiRn3()
	{
		return cutString(this.kanji_na_3,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn3String()
	{
		return "'" + cutString(this.kanji_na_3,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn3HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_3,KANRI_KANJI_RN_MAX_LENGTH));
	}
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn4(String kanji_na_4)
	{
		this.kanji_na_4 = kanji_na_4;
		return true;
	}
	public String getHankuKanjiRn4()
	{
		return cutString(this.kanji_na_4,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn4String()
	{
		return "'" + cutString(this.kanji_na_4,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn4HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_4,KANRI_KANJI_RN_MAX_LENGTH));
	}
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn5(String kanji_na_5)
	{
		this.kanji_na_5 = kanji_na_5;
		return true;
	}
	public String getHankuKanjiRn5()
	{
		return cutString(this.kanji_na_5,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn5String()
	{
		return "'" + cutString(this.kanji_na_5,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn5HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_5,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn6(String kanji_na_6)
	{
		this.kanji_na_6 = kanji_na_6;
		return true;
	}
	public String getHankuKanjiRn6()
	{
		return cutString(this.kanji_na_6,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn6String()
	{
		return "'" + cutString(this.kanji_na_6,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn6HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_6,KANRI_KANJI_RN_MAX_LENGTH));
	}

	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn7(String kanji_na_7)
	{
		this.kanji_na_7 = kanji_na_7;
		return true;
	}
	public String getHankuKanjiRn7()
	{
		return cutString(this.kanji_na_7,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn7String()
	{
		return "'" + cutString(this.kanji_na_7,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn7HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_7,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn8(String kanji_na_8)
	{
		this.kanji_na_8 = kanji_na_8;
		return true;
	}
	public String getHankuKanjiRn8()
	{
		return cutString(this.kanji_na_8,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn8String()
	{
		return "'" + cutString(this.kanji_na_8,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn8HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_8,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn9(String kanji_na_9)
	{
		this.kanji_na_9 = kanji_na_9;
		return true;
	}
	public String getHankuKanjiRn9()
	{
		return cutString(this.kanji_na_9,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn9String()
	{
		return "'" + cutString(this.kanji_na_9,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn9HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_9,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn10(String kanji_na_10)
	{
		this.kanji_na_10 = kanji_na_10;
		return true;
	}
	public String getHankuKanjiRn10()
	{
		return cutString(this.kanji_na_10,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn10String()
	{
		return "'" + cutString(this.kanji_na_10,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn10HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_10,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn11(String kanji_na_11)
	{
		this.kanji_na_11 = kanji_na_11;
		return true;
	}
	public String getHankuKanjiRn11()
	{
		return cutString(this.kanji_na_11,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn11String()
	{
		return "'" + cutString(this.kanji_na_11,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn11HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_11,KANRI_KANJI_RN_MAX_LENGTH));
	}
	
	/**
	 * kanri_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn12(String kanji_na_12)
	{
		this.kanji_na_12 = kanji_na_12;
		return true;
	}
	public String getHankuKanjiRn12()
	{
		return cutString(this.kanji_na_12,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRn12String()
	{
		return "'" + cutString(this.kanji_na_12,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRn12HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na_12,KANRI_KANJI_RN_MAX_LENGTH));
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


//	******* 商品名 *************************************************************************************

//	******** 商品名表示用 ***************************************************
					 // i : 何行目　string : 項目の番号
	  public void setHinmeiKanjiRn(int i ,String string){
		  switch (i) {
			  case 1 :
				  this.hinmei_kanji_rn_1 = string;
				  break;
			  case 2 :
				  this.hinmei_kanji_rn_2 = string;
				  break;
			  case 3 :
				  this.hinmei_kanji_rn_3 = string;
				  break;
			  case 4 :
				  this.hinmei_kanji_rn_4 = string;
				  break;
			  case 5 :
				  this.hinmei_kanji_rn_5 = string;
				  break;
			  case 6 :
				  this.hinmei_kanji_rn_6 = string;
				  break;
			  case 7 :
				  this.hinmei_kanji_rn_7 = string;
				  break;
			  case 8 :
				  this.hinmei_kanji_rn_8 = string;
				  break;
			  case 9 :
				  this.hinmei_kanji_rn_9 = string;
				  break;
			  case 10 :
				  this.hinmei_kanji_rn_10 = string;
				  break;
			  case 11 :
				  this.hinmei_kanji_rn_11 = string;
				  break;
			  case 12 :
				  this.hinmei_kanji_rn_12 = string;
				  break;
			  default :
				  break;
		  }
	  }

	  public String getHinmeiKanjiRn(int i ){
		  String string = "";
		  switch (i) {
			  case 1 :
				  string = this.hinmei_kanji_rn_1 != null?  this.hinmei_kanji_rn_1 : "" ;
				  break;
			  case 2 :
				  string = this.hinmei_kanji_rn_2 != null?  this.hinmei_kanji_rn_2 : "" ;
				  break;
			  case 3 :
				  string = this.hinmei_kanji_rn_3 != null?  this.hinmei_kanji_rn_3 : "" ;
				  break;
			  case 4 :
				  string = this.hinmei_kanji_rn_4 != null?  this.hinmei_kanji_rn_4 : "" ;
				  break;
			  case 5 :
				  string = this.hinmei_kanji_rn_5 != null?  this.hinmei_kanji_rn_5 : "" ;
				  break;
			  case 6 :
				  string = this.hinmei_kanji_rn_6 != null?  this.hinmei_kanji_rn_6 : "" ;
				  break;
			  case 7 :
				  string = this.hinmei_kanji_rn_7 != null?  this.hinmei_kanji_rn_7 : "" ;
				  break;
			  case 8 :
				  string = this.hinmei_kanji_rn_8 != null?  this.hinmei_kanji_rn_8 : "" ;
				  break;
			  case 9 :
				  string = this.hinmei_kanji_rn_9 != null?  this.hinmei_kanji_rn_9 : "" ;
				  break;
			  case 10 :
				  string = this.hinmei_kanji_rn_10 != null?  this.hinmei_kanji_rn_10 : "" ;
				  break;
			  case 11 :
				  string = this.hinmei_kanji_rn_11 != null?  this.hinmei_kanji_rn_11 : "" ;
				  break;
			  case 12 :
				  string = this.hinmei_kanji_rn_12 != null?  this.hinmei_kanji_rn_12 : "" ;
				  break;
			  default :
				  string = "" ;
				  break;
		  }
		  return string;
	  }
	  	
		/**
		 * hinmei_kanji_rn_1に対するセッターとゲッターの集合
		 */ 
		public boolean setHinmeiKanjiRn1(String hinmei_kanji_rn_1)
		{
			this.hinmei_kanji_rn_1 = hinmei_kanji_rn_1;
			return true;
		}
		public String getHinmeiKanjiRn1()
		{
			return cutString(this.hinmei_kanji_rn_1,HINMEI_KANJI_RN_MAX_LENGTH);
		}
		public String getHinmeiKanjiRn1String()
		{
			return "'" + cutString(this.hinmei_kanji_rn_1,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
		}
		public String getHinmeiKanjiRn1HTMLString()
		{
			return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_1,HINMEI_KANJI_RN_MAX_LENGTH));
		
		}
		
		
	
	/**
	 * hinmei_kanji_rn_2に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn2(String hinmei_kanji_rn_2)
	{
		this.hinmei_kanji_rn_2 = hinmei_kanji_rn_2;
		return true;
	}
	public String getHinmeiKanjiRn2()
	{
		return cutString(this.hinmei_kanji_rn_2,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn2String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_2,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_2,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_3に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn3(String hinmei_kanji_rn_3)
	{
		this.hinmei_kanji_rn_3 = hinmei_kanji_rn_3;
		return true;
	}
	public String getHinmeiKanjiRn3()
	{
		return cutString(this.hinmei_kanji_rn_3,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn3String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_3,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn3HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_3,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_4に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn4(String hinmei_kanji_rn_4)
	{
		this.hinmei_kanji_rn_4 = hinmei_kanji_rn_4;
		return true;
	}
	public String getHinmeiKanjiRn4()
	{
		return cutString(this.hinmei_kanji_rn_4,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn4String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_4,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn4HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_4,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_5に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn5(String hinmei_kanji_rn_5)
	{
		this.hinmei_kanji_rn_5 = hinmei_kanji_rn_5;
		return true;
	}
	public String getHinmeiKanjiRn5()
	{
		return cutString(this.hinmei_kanji_rn_5,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn5String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_5,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn5HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_5,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_6に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn6(String hinmei_kanji_rn_6)
	{
		this.hinmei_kanji_rn_6 = hinmei_kanji_rn_6;
		return true;
	}
	public String getHinmeiKanjiRn6()
	{
		return cutString(this.hinmei_kanji_rn_6,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn6String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_6,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn6HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_6,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_7に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn7(String hinmei_kanji_rn_7)
	{
		this.hinmei_kanji_rn_7 = hinmei_kanji_rn_7;
		return true;
	}
	public String getHinmeiKanjiRn7()
	{
		return cutString(this.hinmei_kanji_rn_7,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn7String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_7,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn7HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_7,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_8に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn8(String hinmei_kanji_rn_8)
	{
		this.hinmei_kanji_rn_8 = hinmei_kanji_rn_8;
		return true;
	}
	public String getHinmeiKanjiRn8()
	{
		return cutString(this.hinmei_kanji_rn_8,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn8String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_8,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn8HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_8,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_9に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn9(String hinmei_kanji_rn_9)
	{
		this.hinmei_kanji_rn_9 = hinmei_kanji_rn_9;
		return true;
	}
	public String getHinmeiKanjiRn9()
	{
		return cutString(this.hinmei_kanji_rn_9,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn9String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_9,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn9HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_9,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_10に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn10(String hinmei_kanji_rn_10)
	{
		this.hinmei_kanji_rn_10 = hinmei_kanji_rn_10;
		return true;
	}
	public String getHinmeiKanjiRn10()
	{
		return cutString(this.hinmei_kanji_rn_10,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn10String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_10,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn10HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_10,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_11に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn11(String hinmei_kanji_rn_11)
	{
		this.hinmei_kanji_rn_11 = hinmei_kanji_rn_11;
		return true;
	}
	public String getHinmeiKanjiRn11()
	{
		return cutString(this.hinmei_kanji_rn_11,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn11String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_11,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn11HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_11,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}
	
	
	/**
	 * hinmei_kanji_rn_12に対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn12(String hinmei_kanji_rn_12)
	{
		this.hinmei_kanji_rn_12 = hinmei_kanji_rn_12;
		return true;
	}
	public String getHinmeiKanjiRn12()
	{
		return cutString(this.hinmei_kanji_rn_12,HINMEI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRn12String()
	{
		return "'" + cutString(this.hinmei_kanji_rn_12,HINMEI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRn12HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn_12,HINMEI_KANJI_RN_MAX_LENGTH));
		
	}

//	******* 本数 *************************************************************************************

//	******** 本数表示用 ***************************************************
					 // i : 何行目　string : 項目の番号
	  public void setHonsuQt(int i ,String string){
		  switch (i) {
			  case 1 :
				  this.honsu_1_qt = string;
				  break;
			  case 2 :
				  this.honsu_2_qt = string;
				  break;
			  case 3 :
				  this.honsu_3_qt = string;
				  break;
			  case 4 :
				  this.honsu_4_qt = string;
				  break;
			  case 5 :
				  this.honsu_5_qt = string;
				  break;
			  case 6 :
				  this.honsu_6_qt = string;
				  break;
			  case 7 :
				  this.honsu_7_qt = string;
				  break;
			  case 8 :
				  this.honsu_8_qt = string;
				  break;
			  case 9 :
				  this.honsu_9_qt = string;
				  break;
			  case 10 :
				  this.honsu_10_qt = string;
				  break;
			  case 11 :
				  this.honsu_11_qt = string;
				  break;
			  case 12 :
				  this.honsu_12_qt = string;
				  break;
			  default :
				  break;
		  }
	  }

	  public String getHonsuQt(int i ){
		  String string = "";
		  switch (i) {
			  case 1 :
				  string = this.honsu_1_qt != null?  this.honsu_1_qt : "" ;
				  break;
			  case 2 :
				  string = this.honsu_2_qt != null?  this.honsu_2_qt : "" ;
				  break;
			  case 3 :
				  string = this.honsu_3_qt != null?  this.honsu_3_qt : "" ;
				  break;
			  case 4 :
				  string = this.honsu_4_qt != null?  this.honsu_4_qt : "" ;
				  break;
			  case 5 :
				  string = this.honsu_5_qt != null?  this.honsu_5_qt : "" ;
				  break;
			  case 6 :
				  string = this.honsu_6_qt != null?  this.honsu_6_qt : "" ;
				  break;
			  case 7 :
				  string = this.honsu_7_qt != null?  this.honsu_7_qt : "" ;
				  break;
			  case 8 :
				  string = this.honsu_8_qt != null?  this.honsu_8_qt : "" ;
				  break;
			  case 9 :
				  string = this.honsu_9_qt != null?  this.honsu_9_qt : "" ;
				  break;
			  case 10 :
				  string = this.honsu_10_qt != null?  this.honsu_10_qt : "" ;
				  break;
			  case 11 :
				  string = this.honsu_11_qt != null?  this.honsu_11_qt : "" ;
				  break;
			  case 12 :
				  string = this.honsu_12_qt != null?  this.honsu_12_qt : "" ;
				  break;
			  default :
				  string = "" ;
				  break;
		  }
		  return string;
	  }	
	  
	  
//	honsu_1_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu1Qt(String honsu_1_qt)
	{
		this.honsu_1_qt = honsu_1_qt;
		return true;
	}
	public String getHonsu1Qt()
	{
		return cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu1QtString()
	{
		return "'" + cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu1QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_2_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu2Qt(String honsu_2_qt)
	{
		this.honsu_2_qt = honsu_2_qt;
		return true;
	}
	public String getHonsu2Qt()
	{
		return cutString(this.honsu_2_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu2QtString()
	{
		return "'" + cutString(this.honsu_2_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu2QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_2_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_3_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu3Qt(String honsu_3_qt)
	{
		this.honsu_3_qt = honsu_3_qt;
		return true;
	}
	public String getHonsu3Qt()
	{
		return cutString(this.honsu_3_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu3QtString()
	{
		return "'" + cutString(this.honsu_3_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu3QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_3_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_4_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu4Qt(String honsu_4_qt)
	{
		this.honsu_4_qt = honsu_4_qt;
		return true;
	}
	public String getHonsu4Qt()
	{
		return cutString(this.honsu_4_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu4QtString()
	{
		return "'" + cutString(this.honsu_4_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu4QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_4_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_5_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu5Qt(String honsu_5_qt)
	{
		this.honsu_5_qt = honsu_5_qt;
		return true;
	}
	public String getHonsu5Qt()
	{
		return cutString(this.honsu_5_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu5QtString()
	{
		return "'" + cutString(this.honsu_5_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu5QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_5_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_6_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu6Qt(String honsu_6_qt)
	{
		this.honsu_6_qt = honsu_6_qt;
		return true;
	}
	public String getHonsu6Qt()
	{
		return cutString(this.honsu_6_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu6QtString()
	{
		return "'" + cutString(this.honsu_6_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu6QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_6_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_7_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu7Qt(String honsu_7_qt)
	{
		this.honsu_7_qt = honsu_7_qt;
		return true;
	}
	public String getHonsu7Qt()
	{
		return cutString(this.honsu_7_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu7QtString()
	{
		return "'" + cutString(this.honsu_7_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu7QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_7_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_8_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu8Qt(String honsu_8_qt)
	{
		this.honsu_8_qt = honsu_8_qt;
		return true;
	}
	public String getHonsu8Qt()
	{
		return cutString(this.honsu_8_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu8QtString()
	{
		return "'" + cutString(this.honsu_8_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu8QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_8_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_9_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu9Qt(String honsu_9_qt)
	{
		this.honsu_9_qt = honsu_9_qt;
		return true;
	}
	public String getHonsu9Qt()
	{
		return cutString(this.honsu_9_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu9QtString()
	{
		return "'" + cutString(this.honsu_9_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu9QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_9_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_10_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu10Qt(String honsu_10_qt)
	{
		this.honsu_10_qt = honsu_10_qt;
		return true;
	}
	public String getHonsu10Qt()
	{
		return cutString(this.honsu_10_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu10QtString()
	{
		return "'" + cutString(this.honsu_10_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu10QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_10_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_11_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu11Qt(String honsu_11_qt)
	{
		this.honsu_11_qt = honsu_11_qt;
		return true;
	}
	public String getHonsu11Qt()
	{
		return cutString(this.honsu_11_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu11QtString()
	{
		return "'" + cutString(this.honsu_11_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu11QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_11_qt,HONSU_1_QT_MAX_LENGTH));
	}

//	honsu_12_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu12Qt(String honsu_12_qt)
	{
		this.honsu_12_qt = honsu_12_qt;
		return true;
	}
	public String getHonsu12Qt()
	{
		return cutString(this.honsu_12_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu12QtString()
	{
		return "'" + cutString(this.honsu_12_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu12QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_12_qt,HONSU_1_QT_MAX_LENGTH));
	}


//	******* 販区エラーフラグ *************************************************************************************

//	******** 名称取得エラー判別用 ***************************************************
					 // i : 何行目　string : 項目の番号
	  public boolean getH_eflg(int i ){
	  	
		boolean hflg = true;
			
		  switch (i) {
			  case 1 :
			 	  hflg = this.h_eflg_1;
				  break;
			  case 2 :
				  hflg = this.h_eflg_2;
				  break;
			  case 3 :
				  hflg = this.h_eflg_3;
				  break;
			  case 4 :
				  hflg = this.h_eflg_4;
				  break;
			  case 5 :
				  hflg = this.h_eflg_5;
				  break;
			  case 6 :
				  hflg = this.h_eflg_6;
				  break;
			  case 7 :
				  hflg = this.h_eflg_7;
				  break;
			  case 8 :
				  hflg = this.h_eflg_8;
				  break;
			  case 9 :
				  hflg = this.h_eflg_9;
				  break;
			  case 10 :
				  hflg = this.h_eflg_10;
				  break;
			  case 11 :
				  hflg = this.h_eflg_11;
				  break;
			  case 12 :
				  hflg = this.h_eflg_12;
				  break;
			  default :
				  break;
		  }
		return hflg; 
	  }

	  public void setH_eflg(int i , boolean b){
		  switch (i) {
			  case 1 :
				  this.h_eflg_1 = b ;
				  break;
			  case 2 :
				  this.h_eflg_2 = b ;
				  break;
			  case 3 :
				  this.h_eflg_3 = b ;
				  break;
			  case 4 :
				  this.h_eflg_4 = b ;
				  break;
			  case 5 :
				  this.h_eflg_5 = b ;
				  break;
			  case 6 :
				  this.h_eflg_6 = b ;
				  break;
			  case 7 :
				  this.h_eflg_7 = b ;
				  break;
			  case 8 :
				  this.h_eflg_8 = b ;
				  break;
			  case 9 :
				  this.h_eflg_9 = b ;
				  break;
			  case 10 :
				  this.h_eflg_10 = b ;
				  break;
			  case 11 :
				  this.h_eflg_11 = b ;
				  break;
			  case 12 :
				  this.h_eflg_12 = b ;
				  break;
			  default :
				  break;
		  }
	  }	


	/**
	 * エラーフラグに対するゲッター1<br>
	 * @return
	 */
	public boolean getH_eflg_1() {
		

		
		return h_eflg_1;
	}

	/**
	 * エラーフラグに対するセッター1<br>
	 * @param b
	 */
	public void setH_eflg_1(boolean b) {
		h_eflg_1 = b;
	}

	
	/**
	 * エラーフラグに対するゲッター2<br>
	 * @return
	 */
	public boolean getH_eflg_2() {
		return h_eflg_2;
	}

	/**
	 * エラーフラグに対するセッター2<br>
	 * @param b
	 */
	public void setH_eflg_2(boolean b) {
		h_eflg_2 = b;
	}

	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getH_eflg_3() {
		return h_eflg_3;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setH_eflg_3(boolean b) {
		h_eflg_3 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター4<br>
	 * @return
	 */
	public boolean getH_eflg_4() {
		return h_eflg_4;
	}

	/**
	 * エラーフラグに対するセッター4<br>
	 * @param b
	 */
	public void setH_eflg_4(boolean b) {
		h_eflg_4 = b;
	}

	/**
	 * エラーフラグに対するゲッター5<br>
	 * @return
	 */
	public boolean getH_eflg_5() {
		return h_eflg_5;
	}

	/**
	 * エラーフラグに対するセッター5<br>
	 * @param b
	 */
	public void setH_eflg_5(boolean b) {
		h_eflg_5 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター6<br>
	 * @return
	 */
	public boolean getH_eflg_6() {
		return h_eflg_6;
	}

	/**
	 * エラーフラグに対するセッター6<br>
	 * @param b
	 */
	public void setH_eflg_6(boolean b) {
		h_eflg_6 = b;
	}

	/**
	 * エラーフラグに対するゲッター7<br>
	 * @return
	 */
	public boolean getH_eflg_7() {
		return h_eflg_7;
	}

	/**
	 * エラーフラグに対するセッター7<br>
	 * @param b
	 */
	public void setH_eflg_7(boolean b) {
		h_eflg_7 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター8<br>
	 * @return
	 */
	public boolean getH_eflg_8() {
		return h_eflg_8;
	}

	/**
	 * エラーフラグに対するセッター8<br>
	 * @param b
	 */
	public void setH_eflg_8(boolean b) {
		h_eflg_8 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター9<br>
	 * @return
	 */
	public boolean getH_eflg_9() {
		return h_eflg_9;
	}

	/**
	 * エラーフラグに対するセッター9<br>
	 * @param b
	 */
	public void setH_eflg_9(boolean b) {
		h_eflg_9 = b;
	}
	
	
	/**
	 * エラーフラグに対するゲッター10<br>
	 * @return
	 */
	public boolean getH_eflg_10() {
		return h_eflg_10;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setH_eflg_10(boolean b) {
		h_eflg_10 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター11<br>
	 * @return
	 */
	public boolean getH_eflg_11() {
		return h_eflg_11;
	}

	/**
	 * エラーフラグに対するセッター11<br>
	 * @param b
	 */
	public void setH_eflg_11(boolean b) {
		h_eflg_11 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getH_eflg_12() {
		return this.h_eflg_12;
	}

	/**
	 * エラーフラグに対するセッター12<br>
	 * @param b
	 */
	public void setH_eflg_12(boolean b) {
		h_eflg_12 = b;
	}


//	******* 商品コードエラーフラグ *************************************************************************************

//	******** 名称取得エラー判別用 ***************************************************
					 // i : 何行目　string : 項目の番号
	  public boolean getS_eflg(int i ){
	  	
		boolean sflg = true;
	  	
		  switch (i) {
			  case 1 :
				sflg = this.s_eflg_1;
				  break;
			  case 2 :
				sflg = this.s_eflg_2;
				  break;
			  case 3 :
				sflg = this.s_eflg_3;
				  break;
			  case 4 :
				sflg = this.s_eflg_4;
				  break;
			  case 5 :
				sflg = this.s_eflg_5;
				  break;
			  case 6 :
				sflg = this.s_eflg_6;
				  break;
			  case 7 :
				sflg = this.s_eflg_7;
				  break;
			  case 8 :
				sflg = this.s_eflg_8;
				  break;
			  case 9 :
				sflg = this.s_eflg_9;
				  break;
			  case 10 :
				sflg = this.s_eflg_10;
				  break;
			  case 11 :
				sflg = this.s_eflg_11;
				  break;
			  case 12 :
				sflg = this.s_eflg_12;
				  break;
			  default :
				  break;
		  }
		  
		return sflg;
	  }

	  public void setS_eflg(int i , boolean b ){
		  switch (i) {
			  case 1 :
				   this.s_eflg_1 = b;
				  break;
			  case 2 :
				  this.s_eflg_2 = b;
				  break;
			  case 3 :
				  this.s_eflg_3 = b;
				  break;
			  case 4 :
				  this.s_eflg_4 = b;
				  break;
			  case 5 :
				  this.s_eflg_5 = b;
				  break;
			  case 6 :
				  this.s_eflg_6 = b;
				  break;
			  case 7 :
				  this.s_eflg_7 = b;
				  break;
			  case 8 :
				  this.s_eflg_8 = b;
				  break;
			  case 9 :
				  this.s_eflg_9 = b;
				  break;
			  case 10 :
				  this.s_eflg_10 = b;
				  break;
			  case 11 :
				  this.s_eflg_11 = b;
				  break;
			  case 12 :
				  this.s_eflg_12 = b;
				  break;
			  default :
				  break;
		  }
	  }	


	/**
	 * エラーフラグに対するゲッター1<br>
	 * @return
	 */
	public boolean getS_eflg_1() {
		return s_eflg_1;
	}

	/**
	 * エラーフラグに対するセッター1<br>
	 * @param b
	 */
	public void setS_eflg_1(boolean b) {
		s_eflg_1 =b;
	}

	
	/**
	 * エラーフラグに対するゲッター2<br>
	 * @return
	 */
	public boolean getS_eflg_2() {
		return s_eflg_2;
	}

	/**
	 * エラーフラグに対するセッター2<br>
	 * @param b
	 */
	public void setS_eflg_2(boolean b) {
		s_eflg_2 =b;
	}

	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getS_eflg_3() {
		return s_eflg_3;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setS_eflg_3(boolean b) {
		s_eflg_3 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター4<br>
	 * @return
	 */
	public boolean getS_eflg_4() {
		return s_eflg_4;
	}

	/**
	 * エラーフラグに対するセッター4<br>
	 * @param b
	 */
	public void setS_eflg_4(boolean b) {
		s_eflg_4 =b;
	}

	/**
	 * エラーフラグに対するゲッター5<br>
	 * @return
	 */
	public boolean getS_eflg_5() {
		return s_eflg_5;
	}

	/**
	 * エラーフラグに対するセッター5<br>
	 * @param b
	 */
	public void setS_eflg_5(boolean b) {
		s_eflg_5 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター6<br>
	 * @return
	 */
	public boolean getS_eflg_6() {
		return s_eflg_6;
	}

	/**
	 * エラーフラグに対するセッター6<br>
	 * @param b
	 */
	public void setS_eflg_6(boolean b) {
		s_eflg_6 =b;
	}

	/**
	 * エラーフラグに対するゲッター7<br>
	 * @return
	 */
	public boolean getS_eflg_7() {
		return s_eflg_7;
	}

	/**
	 * エラーフラグに対するセッター7<br>
	 * @param b
	 */
	public void setS_eflg_7(boolean b) {
		s_eflg_7 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター8<br>
	 * @return
	 */
	public boolean getS_eflg_8() {
		return s_eflg_8;
	}

	/**
	 * エラーフラグに対するセッター8<br>
	 * @param b
	 */
	public void setS_eflg_8(boolean b) {
		s_eflg_8 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター9<br>
	 * @return
	 */
	public boolean getS_eflg_9() {
		return s_eflg_9;
	}

	/**
	 * エラーフラグに対するセッター9<br>
	 * @param b
	 */
	public void setS_eflg_9(boolean b) {
		s_eflg_9 =b;
	}
	
	
	/**
	 * エラーフラグに対するゲッター10<br>
	 * @return
	 */
	public boolean getS_eflg_10() {
		return s_eflg_10;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setS_eflg_10(boolean b) {
		s_eflg_10 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター11<br>
	 * @return
	 */
	public boolean getS_eflg_11() {
		return s_eflg_11;
	}

	/**
	 * エラーフラグに対するセッター11<br>
	 * @param b
	 */
	public void setS_eflg_11(boolean b) {
		s_eflg_11 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getS_eflg_12() {
		return s_eflg_12;
	}

	/**
	 * エラーフラグに対するセッター12<br>
	 * @param b
	 */
	public void setS_eflg_12(boolean b) {
		s_eflg_12 =b;
	}

//	******* 名称取得表示用判別フラグ **********************************************************

//	******** 名称取得表示用判別フラグ ***************************************************
					 // i : 何行目　string : 項目の番号
	  public boolean getMeisyoflg(int i ){
	  	
		boolean msyflg = true;
	  	
		  switch (i) {
			  case 1 :
				msyflg = this.meisyoflg_1;
				  break;
			  case 2 :
				msyflg = this.meisyoflg_2;
				  break;
			  case 3 :
				msyflg = this.meisyoflg_3;
				  break;
			  case 4 :
				msyflg = this.meisyoflg_4;
				  break;
			  case 5 :
				msyflg = this.meisyoflg_5;
				  break;
			  case 6 :
				msyflg = this.meisyoflg_6;
				  break;
			  case 7 :
				msyflg = this.meisyoflg_7;
				  break;
			  case 8 :
				msyflg = this.meisyoflg_8;
				  break;
			  case 9 :
				msyflg = this.meisyoflg_9;
				  break;
			  case 10 :
				msyflg = this.meisyoflg_10;
				  break;
			  case 11 :
				msyflg = this.meisyoflg_11;
				  break;
			  case 12 :
				msyflg = this.meisyoflg_12;
				  break;
			  default :
				  break;
		  }
		  
		return msyflg;
	  }

	  public void setMeisyoflg(int i , boolean b ){
		  switch (i) {
			  case 1 :
				   this.meisyoflg_1 = b;
				  break;
			  case 2 :
				  this.meisyoflg_2 = b;
				  break;
			  case 3 :
				  this.meisyoflg_3 = b;
				  break;
			  case 4 :
				  this.meisyoflg_4 = b;
				  break;
			  case 5 :
				  this.meisyoflg_5 = b;
				  break;
			  case 6 :
				  this.meisyoflg_6 = b;
				  break;
			  case 7 :
				  this.meisyoflg_7 = b;
				  break;
			  case 8 :
				  this.meisyoflg_8 = b;
				  break;
			  case 9 :
				  this.meisyoflg_9 = b;
				  break;
			  case 10 :
				  this.meisyoflg_10 = b;
				  break;
			  case 11 :
				  this.meisyoflg_11 = b;
				  break;
			  case 12 :
				  this.meisyoflg_12 = b;
				  break;
			  default :
				  break;
		  }
	  }	


	/**
	 * エラーフラグに対するゲッター1<br>
	 * @return
	 */
	public boolean getMeisyoflg_1() {
		return meisyoflg_1;
	}

	/**
	 * エラーフラグに対するセッター1<br>
	 * @param b
	 */
	public void setMeisyoflg_1(boolean b) {
		meisyoflg_1 =b;
	}

	
	/**
	 * エラーフラグに対するゲッター2<br>
	 * @return
	 */
	public boolean getMeisyoflg_2() {
		return meisyoflg_2;
	}

	/**
	 * エラーフラグに対するセッター2<br>
	 * @param b
	 */
	public void setMeisyoflg_2(boolean b) {
		meisyoflg_2 =b;
	}

	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getMeisyoflg_3() {
		return meisyoflg_3;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setMeisyoflg_3(boolean b) {
		meisyoflg_3 = b;
	}
	
	/**
	 * エラーフラグに対するゲッター4<br>
	 * @return
	 */
	public boolean getMeisyoflg_4() {
		return meisyoflg_4;
	}

	/**
	 * エラーフラグに対するセッター4<br>
	 * @param b
	 */
	public void setMeisyoflg_4(boolean b) {
		meisyoflg_4 =b;
	}

	/**
	 * エラーフラグに対するゲッター5<br>
	 * @return
	 */
	public boolean getMeisyoflg_5() {
		return meisyoflg_5;
	}

	/**
	 * エラーフラグに対するセッター5<br>
	 * @param b
	 */
	public void setMeisyoflg_5(boolean b) {
		meisyoflg_5 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター6<br>
	 * @return
	 */
	public boolean getMeisyoflg_6() {
		return meisyoflg_6;
	}

	/**
	 * エラーフラグに対するセッター6<br>
	 * @param b
	 */
	public void setMeisyoflg_6(boolean b) {
		meisyoflg_6 =b;
	}

	/**
	 * エラーフラグに対するゲッター7<br>
	 * @return
	 */
	public boolean getMeisyoflg_7() {
		return meisyoflg_7;
	}

	/**
	 * エラーフラグに対するセッター7<br>
	 * @param b
	 */
	public void setMeisyoflg_7(boolean b) {
		meisyoflg_7 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター8<br>
	 * @return
	 */
	public boolean getMeisyoflg_8() {
		return meisyoflg_8;
	}

	/**
	 * エラーフラグに対するセッター8<br>
	 * @param b
	 */
	public void setMeisyoflg_8(boolean b) {
		meisyoflg_8 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター9<br>
	 * @return
	 */
	public boolean getMeisyoflg_9() {
		return meisyoflg_9;
	}

	/**
	 * エラーフラグに対するセッター9<br>
	 * @param b
	 */
	public void setMeisyoflg_9(boolean b) {
		meisyoflg_9 =b;
	}
	
	
	/**
	 * エラーフラグに対するゲッター10<br>
	 * @return
	 */
	public boolean getMeisyoflg_10() {
		return meisyoflg_10;
	}

	/**
	 * エラーフラグに対するセッター3<br>
	 * @param b
	 */
	public void setMeisyoflg_10(boolean b) {
		meisyoflg_10 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター11<br>
	 * @return
	 */
	public boolean getMeisyoflg_11() {
		return meisyoflg_11;
	}

	/**
	 * エラーフラグに対するセッター11<br>
	 * @param b
	 */
	public void setMeisyoflg_11(boolean b) {
		meisyoflg_11 =b;
	}
	
	/**
	 * エラーフラグに対するゲッター3<br>
	 * @return
	 */
	public boolean getMeisyoflg_12() {
		return meisyoflg_12;
	}

	/**
	 * エラーフラグに対するセッター12<br>
	 * @param b
	 */
	public void setMeisyoflg_12(boolean b) {
		meisyoflg_12 =b;
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
											
	/**
	 * @return
	 */
	public String getHenko_dt() {
		return henko_dt;
	}

	/**
	 * @return
	 */
	public String getSinki_toroku_dt() {
		return sinki_toroku_dt;
	}

	/**
	 * @param string
	 */
	public void setHenko_dt(String string) {
		henko_dt = string;
	}

	/**
	 * @param string
	 */
	public void setSinki_toroku_dt(String string) {
		sinki_toroku_dt = string;
	}

}
