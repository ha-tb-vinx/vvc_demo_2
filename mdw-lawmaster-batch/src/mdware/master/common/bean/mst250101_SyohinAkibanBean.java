package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius koyama
 * @version 1.0(2005/03/22)初版作成
 */
public class mst250101_SyohinAkibanBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int SELECT_MAX_LENGTH = 1;//選択区分の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;//選択の長さ
	public static final int SELBYNO_MAX_LENGTH = 10;//選択の長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int SYOHIN_NA_MAX_LENGTH = 18;//商品名称の長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
//	public static final int GYOSYU_KB_MAX_LENGTH = 3;//業種区分の長さ
//	public static final int HINSYU_CD_MAX_LENGTH = 4;//品種コードの長さ
	
//BUGNO-S061 2005.06.14 T.Kikuchi START	
//	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 18;//漢字品名の長さ
//	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;//漢字品名の長さ
//BUGNO-S061 2005.06.14 T.Kikuchi END
//	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
//	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 14;//作成年月日の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 14;//更新年月日の長さ
	public static final int UPDATE_DATE_MAX_LENGTH = 14;//更新時間の長さ
	
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
// 2006/01/30 kim START
//    public static final int SYOHIN_2_CD_MAX_LENGTH =13;     	//POSコードの長さ 
//	public static final int KIKAKU_NA_MAX_LENGTH    = 40;	//漢字規格の長さ 
//   	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;		//仕入先コードの長さ 
//	public static final int SIIRESAKI_NA_MAX_LENGTH = 80; 	//仕入先名称の長さ   
//	public static final int SIIRE_HINBAN_CD_MAX_LENGTH =15;	//取引先品番の長さ
// 2006/01/30 kim END	

	// 2005.10.03 状態区分追加
	public static final int JYOTAI_KB_MAX_LENGTH = 6;//状態区分の長さ

	public static final int COLOR_CD_MAX_LENGTH = 2;//カラーコードの長さ
	public static final int COLOR_NA_MAX_LENGTH = 20;//カラー名称の長さ
	public static final int SIZE_CD_MAX_LENGTH = 4;//サイズコードの長さ
	public static final int SIZE_NA_MAX_LENGTH = 20;//サイズ名称の長さ
	public static final int UNIT_CD_MAX_LENGTH = 4;//ユニットコードの長さ
	public static final int UNIT_NA_MAX_LENGTH = 20;//ユニット名称の長さ
	public static final int SIIRESAKISYOHIN_CD_MAX_LENGTH = 9;//仕入先商品コードの長さ
	public static final int BY_NO_MAX_LENGTH = 10;//BY No.の長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	
	
	public static final int HINSHU_NA_MAX_LENGTH = 20;//の長さ
	public static final int HANKU_NA_MAX_LENGTH = 20;//の長さ
	public static final int INSERT_USER_NA_MAX_LENGTH = 20;//の長さ
	public static final int UPDATE_USER_NA_MAX_LENGTH = 20;//の長さ
//	↑↑2006.07.19 xiongjun カスタマイズ修正↑↑

//	↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//	private String hanku_cd = null;	//販区コード
	private String select = null;		//選択区分
	private String sentaku = null;		//選択
	private String sel_by_no = null;	//選択用BY No.
	private String syohin_cd = null;	//商品コード
	private String syohin_na = null;	//商品名称
	private String yuko_dt = null;		//有効日
	private String color_cd = null;	//カラーコード
	private String color_na = null;	//カラー名称
	private String size_cd = null;		//サイズコード
	private String size_na = null;		//サイズ名称
	private String unit_cd = null;		//ユニットコード
	private String unit_na = null;		//ユニット名称
	private String siiresakisyohin_cd = null;		//仕入先商品コード
	private String by_no = null;		//BY No.
	private String bumon_cd = null;	//部門コード
//	private String gyosyu_kb = null;	//業種区分
//	private String hinsyu_cd = null;	//品種コード
//	private String hinmei_kanji_na = null;	//漢字品名
	private String insert_ts = null;	//作成年月日
	private String update_ts = null;	//更新年月日
	private String update_date = null;	//更新時間
	private String delete_fg = null;	//削除フラグ
	// 2005.10.03 状態区分追加	
	private String jyotai_kb = null;	//状態区分
//	private String hinshu_na = null;	//
//	private String hanku_na = null;		//
	private String insert_user_na = null;	//
	private String update_user_na = null;	//

// 2006/01/30 kim START 検索の結果
//	private String syohin_2_cd =	null;     	//POSコード
//	private String kikaku_kanji_na =null;		//漢字規格
//  private long gentankaVl =0;					//原単価 
//  private long baitankaVl =0;					//売単価 
//  private String siiresaki_cd = 	null;		//仕入先コード 
//  private String siiresaki_na=   null;		//仕入先名称 
//  private String siire_hinban_cd =null;       //取引先品番 
// 2006/01/30 kim END
//	↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
    
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
	 * mst250101_SyohinAkibanBeanを１件のみ抽出したい時に使用する
	 */
	public static mst250101_SyohinAkibanBean getmst250101_SyohinAkibanBean(DataHolder dataHolder)
	{
		mst250101_SyohinAkibanBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst250101_SyohinAkibanDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst250101_SyohinAkibanBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

//	↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//// 2006/01/30 kim START
//// syohin_2_cdに対するセッターとゲッターの集合
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
//
//
//// kikaku_kanji_naに対するセッターとゲッターの集合
//
//	public boolean setKikakuKanjiNa(String kikaku_kanji_na)
//	{
//		this.kikaku_kanji_na = kikaku_kanji_na;
//		return true;
//	}
//	public String getKikakuKanjiNa()
//	{
//		return cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH);
//	}
//	public String getKikakuKanjiNaString()
//	{
//		return "'" + cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH) + "'";
//	}
//	public String getKikakuKanjiNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na,KIKAKU_NA_MAX_LENGTH));
//	}
//
//	
//// siire_hinban_cdに対するセッターとゲッターの集合
//	public boolean setSiireHinbanCd(String siire_hinban_cd)
//	{
//		this.siire_hinban_cd = siire_hinban_cd;
//		return true;
//	}
//	public String getSiireHinbanCd()
//	{
//		return cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH);
//	}
//	public String getSiireHinbanCdString()
//	{
//		return "'" + cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
//	}
//	public String getSiireHinbanCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.siire_hinban_cd,SIIRE_HINBAN_CD_MAX_LENGTH));
//	}
//	
//   // siiresaki_cdに対するセッターとゲッターの集合
//   public boolean setSiiresakiCd(String siiresaki_cd)
//   {
//	   this.siiresaki_cd = siiresaki_cd;
//	   return true;
//   }
//   public String getSiiresakiCd()
//   {
//	   return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
//   }
//   public String getSiiresakiCdString()
//   {
//	   return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
//   }
//   public String getSiiresakiCdHTMLString()
//   {
//	   return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
//   }
//	
//	
//// siiresaki_naに対するセッターとゲッターの集合
//	public boolean setSiiresakiNa(String siiresaki_na)
//	{
//		this.siiresaki_na = siiresaki_na;
//		return true;
//	}
//	public String getSiiresakiNa()
//	{
//		return cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH);
//	}
//	public String getSiiresakiNaString()
//	{
//		return "'" + cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH) + "'";
//	}
//	public String getSiiresakiNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH));
//	}
//	 
//	
//   // gentankaVlに対するセッターとゲッターの集合
//   public boolean setGentankaVl(String gentankaVl)
//	   {
//		   try
//		   {
//			   this.gentankaVl = Long.parseLong(gentankaVl);
//		   }
//		   catch(Exception e)
//		   {
//			   return false;
//		   }
//		   return true;
//	   }
//	   public boolean setGentankaVl(long gentankaVl)
//	   {
//		   this.gentankaVl = gentankaVl;
//		   return true;
//	   }
//	   public long getGentankaVl()
//	   {
//		   return this.gentankaVl;
//	   }
//	   public String getGentankaVlString()
//	   {
//		   return Long.toString(this.gentankaVl);
//	   }
//	 
//	 
//	 
//// baitankaVlに対するセッターとゲッターの集合
//	public boolean setBaitankaVl(String baitankaVl)
//		{
//			try
//			{
//				this.gentankaVl = Long.parseLong(baitankaVl);
//			}
//			catch(Exception e)
//			{
//				return false;
//			}
//			return true;
//		}
//		public boolean setBaitankaVl(long baitankaVl)
//		{
//			this.baitankaVl = baitankaVl;
//			return true;
//		}
//		public long getBaitankaVl()
//		{
//			return this.baitankaVl;
//		}
//		public String getBaitankaVlString()
//		{
//			return Long.toString(this.baitankaVl);
//		}
//// 2006/01/30 kim END
//
//
//	// hanku_cdに対するセッターとゲッターの集合
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

	// selectに対するセッターとゲッターの集合
	public boolean setSelect(String select)
	{
		this.select = select;
		return true;
	}
	public String getSelect()
	{
		return cutString(this.select,SELECT_MAX_LENGTH);
	}
	public String getSelectString()
	{
		return "'" + cutString(this.select,SELECT_MAX_LENGTH) + "'";
	}
	public String getSelectHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.select,SELECT_MAX_LENGTH));
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
	
	// sel_by_noに対するセッターとゲッターの集合
	public boolean setSelByNo(String sel_by_no)
	{
		this.sel_by_no = sel_by_no;
		return true;
	}
	public String getSelByNo()
	{
		return cutString(this.sel_by_no,SELBYNO_MAX_LENGTH);
	}
	public String getSelbynoString()
	{
		return "'" + cutString(this.sel_by_no,SELBYNO_MAX_LENGTH) + "'";
	}
	public String getSelbynoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sel_by_no,SELBYNO_MAX_LENGTH));
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

	// syohin_naに対するセッターとゲッターの集合
	public boolean setSyohinNa(String syohin_na)
	{
		this.syohin_na = syohin_na;
		return true;
	}
	public String getSyohinnNa()
	{
		return cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH);
	}
	public String getSyohinNaString()
	{
		return "'" + cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH) + "'";
	}
	public String getSyohinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH));
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

	// color_cdに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd)
	{
		this.color_cd = color_cd;
		return true;
	}
	public String getColorCd()
	{
		return cutString(this.color_cd,COLOR_CD_MAX_LENGTH);
	}
	public String getColorCdString()
	{
		return "'" + cutString(this.color_cd,COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_cd,COLOR_CD_MAX_LENGTH));
	}
	
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
	
	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = size_cd;
		return true;
	}
	public String getSizeCd()
	{
		return cutString(this.size_cd,SIZE_CD_MAX_LENGTH);
	}
	public String getSizeCdString()
	{
		return "'" + cutString(this.size_cd,SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getSizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_cd,SIZE_CD_MAX_LENGTH));
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
	
	// unit_cdに対するセッターとゲッターの集合
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
	
	// unit_naに対するセッターとゲッターの集合
	public boolean setUnitNa(String unit_na)
	{
		this.unit_na = unit_na;
		return true;
	}
	public String getUnitNa()
	{
		return cutString(this.unit_na,UNIT_NA_MAX_LENGTH);
	}
	public String getUnitNaString()
	{
		return "'" + cutString(this.unit_na,UNIT_NA_MAX_LENGTH) + "'";
	}
	public String getUnitNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_na,UNIT_NA_MAX_LENGTH));
	}
	
	// siiresakisyohin_cdに対するセッターとゲッターの集合
	public boolean setSiiresakisyohinCd(String siiresakisyohin_cd)
	{
		this.siiresakisyohin_cd = siiresakisyohin_cd;
		return true;
	}
	public String getSiiresakisyohinCd()
	{
		return cutString(this.siiresakisyohin_cd,SIIRESAKISYOHIN_CD_MAX_LENGTH);
	}
	public String getSiiresakisyohinCdString()
	{
		return "'" + cutString(this.siiresakisyohin_cd,SIIRESAKISYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakisyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresakisyohin_cd,SIIRESAKISYOHIN_CD_MAX_LENGTH));
	}
	
	// by_noに対するセッターとゲッターの集合
	public boolean setByNo(String by_no)
	{
		this.by_no = by_no;
		return true;
	}
	public String getByNo()
	{
		return cutString(this.by_no,BY_NO_MAX_LENGTH);
	}
	public String getByNoString()
	{
		return "'" + cutString(this.by_no,BY_NO_MAX_LENGTH) + "'";
	}
	public String getByNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.by_no,BY_NO_MAX_LENGTH));
	}
	
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
	
//	// gyosyu_kbに対するセッターとゲッターの集合
//	public boolean setGyosyuKb(String gyosyu_kb)
//	{
//		this.gyosyu_kb = gyosyu_kb;
//		return true;
//	}
//	public String getGyosyuKb()
//	{
//		return cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH);
//	}
//	public String getGyosyuKbString()
//	{
//		return "'" + cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH) + "'";
//	}
//	public String getGyosyuKbHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH));
//	}
//
//	// hinsyu_cdに対するセッターとゲッターの集合
//	public boolean setHinsyuCd(String hinsyu_cd)
//	{
//		this.hinsyu_cd = hinsyu_cd;
//		return true;
//	}
//	public String getHinsyuCd()
//	{
//		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
//	}
//	public String getHinsyuCdString()
//	{
//		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHinsyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
//	}
//
//
//	// hinmei_kanji_naに対するセッターとゲッターの集合
//	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
//	{
//		this.hinmei_kanji_na = hinmei_kanji_na;
//		return true;
//	}
//	public String getHinmeiKanjiNa()
//	{
//		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
//	}
//	public String getHinmeiKanjiNaString()
//	{
//		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
//	}
//	public String getHinmeiKanjiNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
//	}


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

	// update_dateに対するセッターとゲッターの集合
	public boolean setUpdateDate(String update_date)
	{
		this.update_date = update_date;
		return true;
	}
	public String getUpdateDate()
	{
		return cutString(this.update_date,UPDATE_DATE_MAX_LENGTH);
	}
	public String getUpdateDateString()
	{
		return "'" + cutString(this.update_date,UPDATE_DATE_MAX_LENGTH) + "'";
	}
	public String getUpdateDateHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_date,UPDATE_DATE_MAX_LENGTH));
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


	// 2005.10.03 状態区分追加
	// jyotai_kbに対するセッターとゲッターの集合
	public boolean setJyotaiKb(String jyotai_kb)
	{
		this.jyotai_kb = jyotai_kb;
		return true;
	}
	public String getJyotaiKb()
	{
		return cutString(this.jyotai_kb,JYOTAI_KB_MAX_LENGTH);
	}
	public String getJyotaiKbString()
	{
		return "'" + cutString(this.jyotai_kb,JYOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getJyotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jyotai_kb,JYOTAI_KB_MAX_LENGTH));
	}
//
//	
//	
//	
//	// hinshu_naに対するセッターとゲッターの集合
//	public boolean setHinshuNa(String hinshu_na)
//	{
//		this.hinshu_na = hinshu_na;
//		return true;
//	}
//	public String getHinshuNa()
//	{
//		return cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH);
//	}
//	public String getHinshuNaString()
//	{
//		return "'" + cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH) + "'";
//	}
//	public String getHinshuNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH));
//	}
//
//
//	// hanku_naに対するセッターとゲッターの集合
//	public boolean setHankuNa(String hanku_na)
//	{
//		this.hanku_na = hanku_na;
//		return true;
//	}
//	public String getHankuNa()
//	{
//		return cutString(this.hanku_na,HANKU_NA_MAX_LENGTH);
//	}
//	public String getHankuNaString()
//	{
//		return "'" + cutString(this.hanku_na,HANKU_NA_MAX_LENGTH) + "'";
//	}
//	public String getHankuNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_na,HANKU_NA_MAX_LENGTH));
//	}
//	↑↑2006.07.19 xiongjun カスタマイズ修正↑↑	

	// insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
	}


	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓	
//		return "  hanku_cd = " + getHankuCdString()
//			+ "  syohin_cd = " + getSyohinCdString()
//			+ "  yuko_dt = " + getYukoDtString()
//		    + "  gyosyu_kb = " + getGyosyuKbString()
//			+ "  hinsyu_cd = " + getHinsyuCdString()
//			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
//			+ "  insert_ts = " + getInsertTsString()
//			+ "  update_ts = " + getUpdateTsString()
//			+ "  delete_fg = " + getDeleteFgString()
//			+ "  jyotai_kb = " + getJyotaiKbString()
//			+ "  hinshu_na = " + getHinshuNaString()
//			+ "  hanku_na = " + getHankuNaString()
		return "  syohin_cd = " + getSyohinCdString()
			+ "  syohin_na = " + getSyohinNaString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  color_cd = " + getColorCdString()
			+ "  color_na = " + getColorNaString()
			+ "  size_cd = " + getSizeCdString()
			+ "  size_na = " + getSizeNaString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_na = " + getUnitNaString()
			+ "  siiresakisyohin_cd = " + getSiiresakisyohinCdString()
			+ "  by_no = " + getByNoString()
			+ "  jyotai_kb = " + getJyotaiKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  delete_fg = " + getDeleteFgString()
//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
			+ "  insert_user_na = " + getInsertUserNaString()
			+ "  update_user_na = " + getUpdateUserNaString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst250101_SyohinAkibanBean コピー後のクラス
	 */
	public mst250101_SyohinAkibanBean createClone()
	{
		mst250101_SyohinAkibanBean bean = new mst250101_SyohinAkibanBean();
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//		bean.setHankuCd(this.hanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setYukoDt(this.yuko_dt);
		bean.setColorCd(this.color_cd);
		bean.setColorNa(this.color_na);
		bean.setSizeCd(this.size_cd);
		bean.setSizeNa(this.size_na);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNa(this.unit_na);
		bean.setSiiresakisyohinCd(this.siiresakisyohin_cd);
		bean.setByNo(this.by_no);
		bean.setBumonCd(this.bumon_cd);
		bean.setJyotaiKb(this.jyotai_kb);
//		bean.setGyosyuKb(this.gyosyu_kb);
//		bean.setHinsyuCd(this.hinsyu_cd);
//		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
		bean.setDeleteFg(this.delete_fg);
//		bean.setHinshuNa(this.hinshu_na);
//		bean.setHankuNa(this.hanku_na);
//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
		bean.setInsertUserNa(this.insert_user_na);
		bean.setUpdateUserNa(this.update_user_na);
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
