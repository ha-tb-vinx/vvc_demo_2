/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
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
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
 */
public class mst300101_TanpinTenToriatukaiLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//    ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//	public static final int HANKU_CD_MAX_LENGTH = 4;// 販区コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;// 部門コードの長さ
//    ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
	public static final int SYOHIN_CD_MAX_LENGTH = 13;// 商品コードの長さ
// BUGNO-S061 2005.06.14 T.Kikuchi START	
// 	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 18;// 漢字品名の長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;// 漢字品名の長さ
// BUGNO-S061 2005.06.14 T.Kikuchi END	
	//===BEGIN=== Modify by kou 2006/11/20
	//public static final int TENPO_CDL_MAX_LENGTH = 1024;// 店舗コード配列の長さ
	public static final int TENPO_CDL_MAX_LENGTH = 2048;// 店舗コード配列の長さ
	//===END=== Modify by kou 2006/11/20
	public static final int TKANJI_RNL_MAX_LENGTH = 1024;// 店舗略称配列の長さ
	public static final int FLGL_MAX_LENGTH = 1024;// 店舗存在フラグ配列の長さ
	public static final int INSERT_TSL_MAX_LENGTH = 1024;// の長さ
	public static final int INSERT_USER_IDL_MAX_LENGTH = 1024;// の長さ
	public static final int UPDATE_TSL_MAX_LENGTH = 4096;// 更新日時配列の長さ
	public static final int UPDATE_USER_IDL_MAX_LENGTH = 1024;// の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;// 処理選択
	public static final int OLDFLGL_MAX_LENGTH = 1024;// 店舗存在フラグ配列の長さ

//  ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//	private String hanku_cd = null;	// 販区コード
	private String bumon_cd = null;	// 部門コード
//  ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
	private String syohin_cd = null;	// 商品コード
	private String hinmei_kanji_na = null;	// 漢字品名
	private String tenpo_cdl = null;	// 店舗コード配列
	private String tkanji_rnl = null;	// 店舗略称配列
	private String flgl = null;	// 店舗存在フラグ配列
	private String insert_tsl = null;	// 
	private String insert_user_idl = null;	// 
	private String update_tsl = null;	// 更新日時配列
	private String update_user_idl = null;	// 
	private String sentaku = null;	// 処理選択
	private String oldFlgl = null;	// 更新前店舗存在フラグ配列

//	↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
	public static final int YUKO_DT_MAX_LENGTH = 8;// 有効日の長さ
	public static final int SYSTEM_KB_MAX_LENGTH = 1;// システム区分の長さ
	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;// 店別仕入先管理コードの長さ

	private String yuko_dt = null;	// 有効日
	private String system_kb = null;	// システム区分
	private String ten_siiresaki_kanri_cd = null;	// 店別仕入先管理コード
//	↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑

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
	 * mst300101_TanpinTenToriatukaiLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst300101_TanpinTenToriatukaiLBean getmst300101_TanpinTenToriatukaiLBean(DataHolder dataHolder)
	{
		mst300101_TanpinTenToriatukaiLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst300101_TanpinTenToriatukaiLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			// １件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst300101_TanpinTenToriatukaiLBean )ite.next();
			// ２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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

//     ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//	hanku_cdに対するセッターとゲッターの集合
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
//     ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

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


	// tenpo_cdlに対するセッターとゲッターの集合
	public boolean setTenpoCdl(String tenpo_cdl)
	{
		this.tenpo_cdl = tenpo_cdl;
		return true;
	}
	public String getTenpoCdl()
	{
		return cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH);
	}
	public String getTenpoCdlString()
	{
		return "'" + cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH) + "'";
	}
	public String getTenpoCdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cdl,TENPO_CDL_MAX_LENGTH));
	}


	// tkanji_rnlに対するセッターとゲッターの集合
	public boolean setTkanjiRnl(String tkanji_rnl)
	{
		this.tkanji_rnl = tkanji_rnl;
		return true;
	}
	public String getTkanjiRnl()
	{
		return cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH);
	}
	public String getTkanjiRnlString()
	{
		return "'" + cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH) + "'";
	}
	public String getTkanjiRnlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH));
	}


	// flglに対するセッターとゲッターの集合
	public boolean setFlgl(String flgl)
	{
		this.flgl = flgl;
		return true;
	}
	public String getFlgl()
	{
		return cutString(this.flgl,FLGL_MAX_LENGTH);
	}
	public String getFlglString()
	{
		return "'" + cutString(this.flgl,FLGL_MAX_LENGTH) + "'";
	}
	public String getFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.flgl,FLGL_MAX_LENGTH));
	}
	// oldFlglに対するセッターとゲッターの集合
	public boolean setOldFlgl(String oldFlgl)
	{
		this.oldFlgl = oldFlgl;
		return true;
	}
	public String getOldFlgl()
	{
		return cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH);
	}
	public String getOldFlglString()
	{
		return "'" + cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH) + "'";
	}
	public String getOldFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH));
	}


	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTsl(String insert_tsl)
	{
		this.insert_tsl = insert_tsl;
		return true;
	}
	public String getInsertTsl()
	{
		return cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH);
	}
	public String getInsertTslString()
	{
		return "'" + cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH) + "'";
	}
	public String getInsertTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_tsl,INSERT_TSL_MAX_LENGTH));
	}


	// insert_user_idlに対するセッターとゲッターの集合
	public boolean setInsertUserIdl(String insert_user_idl)
	{
		this.insert_user_idl = insert_user_idl;
		return true;
	}
	public String getInsertUserIdl()
	{
		return cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH);
	}
	public String getInsertUserIdlString()
	{
		return "'" + cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_idl,INSERT_USER_IDL_MAX_LENGTH));
	}


	// update_tslに対するセッターとゲッターの集合
	public boolean setUpdateTsl(String update_tsl)
	{
		this.update_tsl = update_tsl;
		return true;
	}
	public String getUpdateTsl()
	{
		return cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getUpdateTslString()
	{
		return "'" + cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getUpdateTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH));
	}


	// update_user_idlに対するセッターとゲッターの集合
	public boolean setUpdateUserIdl(String update_user_idl)
	{
		this.update_user_idl = update_user_idl;
		return true;
	}
	public String getUpdateUserIdl()
	{
		return cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH);
	}
	public String getUpdateUserIdlString()
	{
		return "'" + cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH));
	}


//	↓↓2006.10.27 H.Yamamoto カスタマイズ修正↓↓
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

	// system_kbに対するセッターとゲッターの集合
	public boolean setSystemKb(String system_kb)
	{
		this.system_kb = system_kb;
		return true;
	}
	public String getSystemKb()
	{
		return cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH);
	}
	public String getSystemKbString()
	{
		return "'" + cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getSystemKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH));
	}

	// ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
		return true;
	}
	public String getTenSiiresakiKanriCd()
	{
		return cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}
//	↑↑2006.10.27 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//	      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		return "  hanku_cd = " + gethankuCdString()
		return "  bumon_cd = " + getBumonCdString()
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+ "  tenpo_cdl = " + getTenpoCdlString()
			+ "  tkanji_rnl = " + getTkanjiRnlString()
			+ "  flgl = " + getFlglString()
			+ "  insert_tsl = " + getInsertTslString()
			+ "  insert_user_idl = " + getInsertUserIdlString()
			+ "  update_tsl = " + getUpdateTslString()
			+ "  update_user_idl = " + getUpdateUserIdlString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst300101_TanpinTenToriatukaiLBean コピー後のクラス
	 */
	public mst300101_TanpinTenToriatukaiLBean createClone()
	{
		mst300101_TanpinTenToriatukaiLBean bean = new mst300101_TanpinTenToriatukaiLBean();
//	      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		bean.setBumonCd(this.hanku_cd);
		bean.setBumonCd(this.bumon_cd);
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		bean.setSyohinCd(this.syohin_cd);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setTenpoCdl(this.tenpo_cdl);
		bean.setTkanjiRnl(this.tkanji_rnl);
		bean.setFlgl(this.flgl);
		bean.setInsertTsl(this.insert_tsl);
		bean.setInsertUserIdl(this.insert_user_idl);
		bean.setUpdateTsl(this.update_tsl);
		bean.setUpdateUserIdl(this.update_user_idl);
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
		if( !( o instanceof mst300101_TanpinTenToriatukaiLBean ) ) return false;
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
// BUGNO-S051 2005.05.15 Sirius START
// 				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
// BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
