/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/16)初版作成
 */
public class mst450101_TenbetuSiiresakiListBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//    ↓↓2006.07.02 maojm カスタマイズ修正↓↓
//	public static final int KANRI_KB_MAX_LENGTH = 1;			// 管理区分の長さ
//	public static final int KANRI_CD_MAX_LENGTH = 1;			// 管理コードの長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;			// 部門コードの長さ
//	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;		//仕入先コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;		//仕入先コードの長さ
//  ↑↑2006.07.02 maojm カスタマイズ修正↑↑		
	public static final int KANJI_RN_MAX_LENGTH = 20;		//仕入先名の長さ
	public static final int YUBIN_CD_MAX_LENGTH = 7;			//郵便番号の長さ
	public static final int ADDRESS_KANJI1_NA_MAX_LENGTH = 120;//住所(漢字)1の長さ
	//public static final int TENPO_CDL_MAX_LENGTH = 1024;		//店舗コード配列の長さ
	public static final int TENPO_CDL_MAX_LENGTH = 2048;		//店舗コード配列の長さ
	//public static final int TKANJI_RNL_MAX_LENGTH = 1024;		//店舗名配列の長さ
	public static final int TKANJI_RNL_MAX_LENGTH = 2048;	//店舗名配列の長さ
	public static final int FLGL_MAX_LENGTH = 1024;			//存在フラグ配列の長さ
	//public static final int UPDATE_TSL_MAX_LENGTH = 1024;		//更新日時配列の長さ
	public static final int UPDATE_TSL_MAX_LENGTH = 3072;	//更新日時配列の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;			//処理選択
	public static final int ALLTENPOTS_MAX_LENGTH = 20;		//全店舗コード更新日の長さ
	public static final int ALLTENPO_MAX_FLG_LENGTH = 1;		//全店舗フラグ（1：全店舗　0：各店舗）

//  ↓↓2006.07.02 maojm カスタマイズ修正↓↓
//	private String kanri_kb = null;			//管理区分
//	private String kanri_cd = null;			//管理コード
	private String bumon_cd = null;		//部門コード
//    ↑↑2006.07.02 maojm カスタマイズ修正↑↑	
	private String siiresaki_cd = null;		//仕入先コード
	private String kanji_rn = null;			//略式名称(漢字)
	private String yubin_cd = null;			//郵便番号
	private String address_kanji1_na = null;	//住所(漢字)1
	private String tenpo_cdl = null;			//店舗コード配列
	private String tkanji_rnl = null;			//店舗略称配列
	private String flgl = null;				//店舗存在フラグ配列
	private String defaultflgl = null;				//店舗存在フラグ配列
	private String insert_tsl = null;			//登録日時配列
	private String update_tsl = null;			//更新日時配列
	private String sentaku = null;			//処理選択
	private String alltenpots = null;			//全店舗コード更新日
	private String alltenpo_flg = null;		//全店舗
	private String alltenpo = null;		//全店舗
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
	 * Mst450101TenbetuSiireBeanを１件のみ抽出したい時に使用する
	 */
	public static mst450101_TenbetuSiiresakiListBean getmst450101_TenbetuSiiresakiListBean(DataHolder dataHolder)
	{
		mst450101_TenbetuSiiresakiListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst450101_TenbetuSiiresakiListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst450101_TenbetuSiiresakiListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

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
	


	// Siiresaki_cdに対するセッターとゲッターの集合
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


	// kanji_rnに対するセッターとゲッターの集合
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
	public String getKanjiRn()
	{
		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
	}
	public String getKanjiRnString()
	{
		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
	}


	// yubin_cdに対するセッターとゲッターの集合
	public boolean setYubinCd(String yubin_cd)
	{
		this.yubin_cd = yubin_cd;
		return true;
	}
	public String getYubinCd()
	{
		return cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH);
	}
	public String getYubinCdString()
	{
		return "'" + cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH) + "'";
	}
	public String getYubinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH));
	}


	// address_kanji1_naに対するセッターとゲッターの集合
	public boolean setAddressKanji1Na(String address_kanji1_na)
	{
		this.address_kanji1_na = address_kanji1_na;
		return true;
	}
	public String getAddressKanji1Na()
	{
		return cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH);
	}
	public String getAddressKanji1NaString()
	{
		return "'" + cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanji1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kanji1_na,ADDRESS_KANJI1_NA_MAX_LENGTH));
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
	public boolean setTKanjiRnl(String tkanji_rnl)
	{
		this.tkanji_rnl = tkanji_rnl;
		return true;
	}
	public String getTKanjiRnl()
	{
		return cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH);
	}
	public String getTKanjiRnlString()
	{
		return "'" + cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH) + "'";
	}
	public String getTKanjiRnlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tkanji_rnl,TKANJI_RNL_MAX_LENGTH));
	}

	// flglに対するセッターとゲッターの集合
	public boolean setDefaultFlgl(String defaultflgl)
	{
		this.defaultflgl = defaultflgl;
		return true;
	}
	public String getDefaultFlgl()
	{
		return cutString(this.defaultflgl,FLGL_MAX_LENGTH);
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


	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTsl(String insert_tsl)
	{
		this.insert_tsl = insert_tsl;
		return true;
	}
	public String getInsertTsl()
	{
		return cutString(this.insert_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getInsertTslString()
	{
		return "'" + cutString(this.insert_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getInsertTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_tsl,UPDATE_TSL_MAX_LENGTH));
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


	// alltenpotsに対するセッターとゲッターの集合
	public boolean setAllTenpoTs(String alltenpots)
	{
		this.alltenpots = alltenpots;
		return true;
	}
	public String getAllTenpoTs()
	{
		return cutString(this.alltenpots,ALLTENPOTS_MAX_LENGTH);
	}
	public String getAllTenpoTsString()
	{
		return "'" + cutString(this.alltenpots,ALLTENPOTS_MAX_LENGTH) + "'";
	}
	public String getAllTenpoTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.alltenpots,ALLTENPOTS_MAX_LENGTH));
	}


	// alltenpo_flgに対するセッターとゲッターの集合
	public boolean setAllTenpoFlg(String alltenpo_flg)
	{
		this.alltenpo_flg = alltenpo_flg;
		return true;
	}
	public String getAllTenpoFlg()
	{
		return cutString(this.alltenpo_flg,ALLTENPO_MAX_FLG_LENGTH);
	}
	public String getAllTenpoFlgString()
	{
		return "'" + cutString(this.alltenpo_flg,ALLTENPO_MAX_FLG_LENGTH) + "'";
	}
	public String getAllTenpoFlgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.alltenpo_flg,ALLTENPO_MAX_FLG_LENGTH));
	}
	public boolean setAllTenpo(String alltenpo)
	{
		this.alltenpo = alltenpo;
		return true;
	}
	public String getAllTenpo()
	{
		return cutString(this.alltenpo,ALLTENPO_MAX_FLG_LENGTH);
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//	    ↓↓2006.07.02 maojm カスタマイズ修正↓↓		
//		return "  kanri_kb = " + getKanriKbString()
//			+ "  kanri_cd = " + getKanriCdString()
		return  "  kanri_cd = " + getBumonCdString()
		    + "  Siiresaki_cd = " + getSiiresakiCdString()
			+ "  sentaku = " + getSentaku()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  yubin_cd = " + getYubinCdString()
			+ "  address_kanji1_na = " + getAddressKanji1NaString()
			+ "  tenpo_cd = " + getTenpoCdlString()
			+ "  flg = " + getFlglString()
			+ "  alltenpots = " + getAllTenpoTs()
			+ " createDatabase  = " + createDatabase;
//	    ↑↑2006.07.02 maojm カスタマイズ修正↑↑		
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst450101_TenbetuSiiresakiListBean コピー後のクラス
	 */
	public mst450101_TenbetuSiiresakiListBean createClone()
	{
		mst450101_TenbetuSiiresakiListBean bean = new mst450101_TenbetuSiiresakiListBean();
//	    ↓↓2006.07.02 maojm カスタマイズ修正↓↓		
//		bean.setKanriKb(this.kanri_kb);
//		bean.setKanriCd(this.kanri_cd);
		bean.setBumonCd(this.bumon_cd);		
//	    ↑↑2006.07.02 maojm カスタマイズ修正↑↑	
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setKanjiRn(this.kanji_rn);
		bean.setYubinCd(this.yubin_cd);
		bean.setAddressKanji1Na(this.address_kanji1_na);
		bean.setTenpoCdl(this.tenpo_cdl);
		bean.setTKanjiRnl(this.tkanji_rnl);
		bean.setFlgl(this.flgl);
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
		if( !( o instanceof mst450101_TenbetuSiiresakiListBean ) ) return false;
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
