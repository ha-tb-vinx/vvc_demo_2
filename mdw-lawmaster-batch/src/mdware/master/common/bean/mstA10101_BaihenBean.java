package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.util.DateChanger;

/**
 * <p>タイトル: 新ＭＤシステム（POSー管理）R_POS_BAIHENのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するPOS売価変更指示（基準売価）のレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 
 * @version 1.0(2006/03/27)初版作成
 */
public class mstA10101_BaihenBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SAKUSEI_DT_MAX_LENGTH = 8;			//作成日の長さ
	public static final int TENPO_CD_MAX_LENGTH	=	6;			//店舗コードの長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH	= 4; 		//小業種の長さ
	public static final int URIKU_CD_MAX_LENGTH	= 4;			//売区コードの長さ
	public static final int TENHANKU_CD_MAX_LENGTH = 4;			//店販区コードの長さ
	public static final int HANKU_CD_MAX_LENGTH = 4;				//販区コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;			//商品コードの長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;				//有効日の長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 36;		//漢字品名の長さ
	public static final int KIKAKU_NA_MAX_LENGTH    = 14;		//漢字規格の長さ 
	public static final int POSFUL_HINBAN_CD_MAX_LENGTH	=	13; //POSFUL品番の長さ
	public static final int POS_CD_MAX_LENGTH		=13;		//ＰＯＳコード
	public static final int BAIKA_VL_MAX_LENGTH	= 7;			//売単価の長さ
	public static final int OLD_BAIKA_VL_MAX_LENGTH	= 7;		//旧売単価の長さ
	public static final int HANEI_DT_MAX_LENGTH = 8;				//反映日の長さ
	public static final int HACHU_HANEI_DT_MAX_LENGTH = 8;		//発注反映日の長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;			//仕入先コードの長さ 
	public static final int SIIRESAKI_NA_MAX_LENGTH = 20; 		//仕入先名称の長さ   
	public static final int INSERT_TS_MAX_LENGTH = 20;			//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;		//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;			//削除フラグの長さ

	private String sakusei_dt	= null; 		//作成日
	private String tenpo_cd	=	null; 		//店舗コード
	private String s_gyosyu_cd	=	null; 		//小業種
	private String uriku_cd = null;			//売区コード
	private String tenhanku_cd = null;		//店販区コード
	private String hanku_cd = null;			//販区コード
	private String syohin_cd = null;			//商品コード
	private String yuko_dt = null;			//有効日
	private String hinmei_kanji_na = null;	//漢字品名
	private String kikaku_kanji_na =null;		//漢字規格
	private String	posful_hinban_cd=null;		//POSFUL品番
	private String pos_cd	=null;				//ＰＯＳコード
	private long baitanka_vl = 0;				//売単価
	private long old_baitanka_vl = 0;			//旧売単価
	private String hanei_dt	=null;			//反映日
	private String hachu_hanei_dt = null;		//発注反映日
	private String siiresaki_cd = 	null;		//仕入先コード 
	private String siiresaki_na=   null;		//仕入先名称 
	private String insert_ts = null;			//作成年月日
	private String insert_user_id = null;		//作成者社員ID
	private String update_ts = null;			//更新年月日
	private String update_user_id = null;		//更新者社員ID
	private String delete_fg = null;			//削除フラグ
	
	
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
	 *mstA10101_BaihenBeanを１件のみ抽出したい時に使用する
	 */
	public static mstA10101_BaihenBean getmstA10101_BaihenBean(DataHolder dataHolder)
	{
		mstA10101_BaihenBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstA10101_BaihenDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstA10101_BaihenBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


//	sakusei_dtに対するセッターとゲッターの集合
	 public boolean setSakuseiDt(String sakusei_dt)
	 {
		 this.sakusei_dt = sakusei_dt;
		 return true;
	 }
	 public String getSakuseiDt()
	 {
		 return cutString(this.sakusei_dt,SAKUSEI_DT_MAX_LENGTH);
	 }
	 public String getSakuseiDtString()
	 {
		 return "'" + cutString(this.sakusei_dt,SAKUSEI_DT_MAX_LENGTH) + "'";
	 }
	 public String getSakuseiDtHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.sakusei_dt,SAKUSEI_DT_MAX_LENGTH));
	 }
	 public String getSakuseiDtMMDD(){
		String flatDate = "";
		String retDate = "";
	   	flatDate = DateChanger.toFlatDate( this.sakusei_dt ).trim();
		retDate = flatDate.substring(4,6) + "/" + flatDate.substring(6);
		return retDate;
	 }

//	tenpo_cdに対するセッターとゲッターの集合 
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

//	s_gyosyu_cdに対するセッターとゲッターの集合
	 public boolean setSgyosyuCd(String s_gyosyu_cd)
	 {
		 this.s_gyosyu_cd = s_gyosyu_cd;
		 return true;
	 }
	 public String getSgyosyuCd()
	 {
		 return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	 }
	 public String getSgyosyuCdString()
	 {
		 return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	 }
	 public String getSgyosyuCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	 }
	
	// uriku_cdに対するセッターとゲッターの集合
	public boolean setUrikuCd(String uriku_cd)
	{
		this.uriku_cd = uriku_cd;
		return true;
	}
	public String getUrikuCd()
	{
		return cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH);
	}
	public String getUrikuCdString()
	{
		return "'" + cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH) + "'";
	}
	public String getUrikuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku_cd,URIKU_CD_MAX_LENGTH));
	}


	// tenhanku_cdに対するセッターとゲッターの集合
	public boolean setTenhankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
	public String getTenhankuCd()
	{
		return cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenhankuCdString()
	{
		return "'" + cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH) + "'";
	}
	public String getTenhankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_cd,TENHANKU_CD_MAX_LENGTH));
	}
	
	
//	hanku_cdに対するセッターとゲッターの集合
	 public boolean setHankuCd(String hanku_cd)
	 {
		 this.hanku_cd = hanku_cd;
		 return true;
	 }
	 public String getHankuCd()
	 {
		 return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
	 }
	 public String getHankuCdString()
	 {
		 return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
	 }
	 public String getHankuCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
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
	 
//	hinmei_kanji_naに対するセッターとゲッターの集合
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
	 
 
//	kikaku_kanji_naに対するセッターとゲッターの集合

	 public boolean setKikakuKanjiNa(String kikaku_kanji_na)
	 {
		 this.kikaku_kanji_na = kikaku_kanji_na;
		 return true;
	 }
	 public String getKikakuKanjiNa()
	 {
		 return cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH);
	 }
	 public String getKikakuKanjiNaString()
	 {
		 return "'" + cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH) + "'";
	 }
	 public String getKikakuKanjiNaHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH));
	 }



//	posful_hinban_cdに対するセッターとゲッターの集合

	 public boolean setPosfulHinbanCd(String posful_hinban_cd)
	 {
		 this.posful_hinban_cd = posful_hinban_cd;
		 return true;
	 }
	 public String getPosfulHinbanCd()
	 {
		 return cutString(this.posful_hinban_cd,POSFUL_HINBAN_CD_MAX_LENGTH);
	 }
	 public String getPosfulHinbanCdString()
	 {
		 return "'" + cutString(this.posful_hinban_cd,POSFUL_HINBAN_CD_MAX_LENGTH) + "'";
	 }
	 public String getPosfulHinbanCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.posful_hinban_cd,POSFUL_HINBAN_CD_MAX_LENGTH));
	 }
	 
//	pos_cdに対するセッターとゲッターの集合

	 public boolean setPosCd(String pos_cd)
	 {
		 this.pos_cd = pos_cd;
		 return true;
	 }
	 public String getPosCd()
	 {
		 return cutString(this.pos_cd,POS_CD_MAX_LENGTH);
	 }
	 public String getPosCdString()
	 {
		 return "'" + cutString(this.pos_cd,POS_CD_MAX_LENGTH) + "'";
	 }
	 public String getPosCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.pos_cd,POS_CD_MAX_LENGTH));
	 }
	 

	// baitankaVlに対するセッターとゲッターの集合
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
	 
	 
//	old_baitanka_vlに対するセッターとゲッターの集合
	public boolean setOldBaitankaVl(String old_baitanka_vl)
	   {
		   try
		   {
			   this.old_baitanka_vl = Long.parseLong(old_baitanka_vl);
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
		   return true;
	   }
	   public boolean setOldBaitankaVl(long old_baitanka_vl)
	   {
		   this.old_baitanka_vl = old_baitanka_vl;
		   return true;
	   }
	   public long getOldBaitankaVl()
	   {
		   return this.old_baitanka_vl;
	   }
	   public String getOldBaitankaVlString()
	   {
		   return Long.toString(this.old_baitanka_vl);
	   }
	 
	 
	// hanei_dtに対するセッターとゲッターの集合
		public boolean setHaneiDt(String hanei_dt)
		{
			this.hanei_dt = hanei_dt;
			return true;
		}
		public String getHaneiDt()
		{
			return cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH);
		}
		public String getHaneiDtString()
		{
			return "'" + cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH) + "'";
		}
		public String getHaneiDtHTMLString()
		{
			return HTMLStringUtil.convert(cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH));
		}	

	
	
//	hachu_hanei_dtに対するセッターとゲッターの集合
		 public boolean setHachuHaneiDt(String hachu_hanei_dt)
		 {
			 this.hachu_hanei_dt = hachu_hanei_dt;
			 return true;
		 }
		 public String getHachuHaneiDt()
		 {
			 return cutString(this.hachu_hanei_dt,HACHU_HANEI_DT_MAX_LENGTH);
		 }
		 public String getHachuHaneiDtString()
		 {
			 return "'" + cutString(this.hachu_hanei_dt,HACHU_HANEI_DT_MAX_LENGTH) + "'";
		 }
		 public String getHachuHaneiDtHTMLString()
		 {
			 return HTMLStringUtil.convert(cutString(this.hachu_hanei_dt,HACHU_HANEI_DT_MAX_LENGTH));
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
	
	
//	siiresaki_naに対するセッターとゲッターの集合
	 public boolean setSiiresakiNa(String siiresaki_na)
	 {
		 this.siiresaki_na = siiresaki_na;
		 return true;
	 }
	 public String getSiiresakiNa()
	 {
		 return cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH);
	 }
	 public String getSiiresakiNaString()
	 {
		 return "'" + cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH) + "'";
	 }
	 public String getSiiresakiNaHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH));
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
//BUGNO-S075 2005.07.20 Sirius START
	//	gamenFlg.put("insert_user_id","1");
//BUGNO-S075 2005.07.20 Sirius END
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



//	update_user_idに対するセッターとゲッターの集合
	 public boolean setUpdateUserId(String update_user_id)
	 {
		 this.update_user_id = update_user_id;
// BUGNO-S075 2005.07.20 Sirius START
//		 gamenFlg.put("update_user_id","1");
// BUGNO-S075 2005.07.20 Sirius END
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
	

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	
	public String toString()
	{
		return "sakusei_dt	=	"+getSakuseiDtString()
			+	"tenpo_cd	=	"+getTenpoCdString()
			+	"s_gyosyu_cd	=	"+getSgyosyuCdString()
			+	"uriku_cd	=	"+getUrikuCdString()
			+	"tenhanku_cd	=	"+getTenhankuCdString()
			+	"hanku_cd = " + getHankuCdString()
			+ 	"syohin_cd = " + getSyohinCdString()
			+ 	"yuko_dt = " + getYukoDtString()
			+ 	"hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+	"kikaku_kanji_na	=	"+getKikakuKanjiNaString()
			+ 	"posful_hinban_cd = " + getPosfulHinbanCdString()
			+ 	"pos_cd = " + getPosCdString()
			+	"baitanka_vl = " + getBaitankaVlString()
			+ 	"old_baitanka_vl = " + getOldBaitankaVlString()
			+ 	"hanei_dt = " + getHaneiDtString()
			+ 	"hachu_hanei_dt = " + getHachuHaneiDtString()
			+ 	"siiresaki_cd = " + getSiiresakiCdString()
			+ 	"siiresaki_na = " + getSiiresakiNaString()
			+ 	"insert_ts = " + getInsertTsString()
			+ 	"insert_user_id = " + getInsertUserIdString()
			+ 	"update_ts = " + getUpdateTsString()
			+ 	"update_user_id = " + getUpdateUserIdString()
			+ 	"delete_fg = " + getDeleteFgString();
			
			
			
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst250101_SyohinAkibanBean コピー後のクラス
	 */
	public mstA10101_BaihenBean createClone()
	{
		mstA10101_BaihenBean bean = new mstA10101_BaihenBean();
		bean.setSakuseiDt(this.sakusei_dt);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setSgyosyuCd(this.s_gyosyu_cd);
		bean.setUrikuCd(this.uriku_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setHankuCd(this.hanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setKikakuKanjiNa(this.kikaku_kanji_na);
		bean.setPosfulHinbanCd(this.posful_hinban_cd);
		bean.setPosCd(this.pos_cd);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setOldBaitankaVl(this.old_baitanka_vl);
		bean.setHaneiDt(this.hanei_dt);
		bean.setHachuHaneiDt(this.hachu_hanei_dt);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSiiresakiNa(this.siiresaki_na);
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
		if( !( o instanceof mst250101_SyohinAkibanBean ) ) return false;
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
