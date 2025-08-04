/**
 * <p>タイトル	: 新ＭＤシステム（マスタ管理）発注（センター）マスタのレコード格納用クラス</p>
 * <p>説明		: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権	: Copyright (c) 2005</p>
 * <p>会社名	: Vinculum Japan Corp.</p>
 * @author		VJC A.Tozaki
 * @version	1.0(2005/10/15)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;


public class mst910101_DenpyoHakkoBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final int DENPYO_NB_MAX_LENGTH			= 10;	// 伝票№の長さ
	public static final int DENPYOGYO_NB_MAX_LENGTH		= 2;	// 伝票行№の長さ
	public static final int NOHIN_DT_MAX_LENGTH			= 8;	// 納品日の長さ
	public static final int HACHU_DT_MAX_LENGTH			= 8;	// 発注日の長さ
	public static final int L_HANKU_CD_MAX_LENGTH		= 4;	// Ｌ販区（販区）の長さ
	public static final int TORIHIKISAKI_CD_MAX_LENGTH	= 6;	// 取引先（仕入先）コードの長さ
	public static final int TORIHIKISAKI_NA_MAX_LENGTH	= 30;	// 取引先（仕入先）名称の長さ
	public static final int TENPO_CD_MAX_LENGTH			= 6;	// 店舗コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH			= 13;	// 商品コードの長さ
	public static final int SYOHIN_NA_MAX_LENGTH			= 50;	// 商品名称カナ（商品名称）の長さ
	public static final int GENKA_VL_MAX_LENGTH			= 11;	// 原単価の長さ
	public static final int BAIKA_VL_MAX_LENGTH			= 7;	// 売単価の長さ
	public static final int HACHU_TANI_QT_MAX_LENGTH		= 5;	// 発注単位の長さ
	public static final int HACHU_QT_MAX_LENGTH			= 5;	// 発注数の長さ
	public static final int HACHU_KB_MAX_LENGTH			= 8;	// 発注区分の長さ
	public static final int HAISINSAKI_CD_MAX_LENGTH		= 11;	// 配信先の長さ
	public static final int SA_KB_MAX_LENGTH				= 2;	// SAの長さ
	public static final int KEIYU_CENTER_CD_MAX_LENGTH	= 4;	// 経由センターコードの長さ
	


	private String denpyo_nb		= null;				// 伝票№
	private String denpyogyo_nb	= null;				// 伝票行№
	private String nohin_dt		= null;				// 納品日
	private String hachu_dt		= null;				// 発注日
	private String l_hanku_cd		= null;				// Ｌ販区（販区）
	private String torihikisaki_cd	= null;				// 取引先（仕入先）コード
	private String torihikisaki_na	= null;				// 取引先（仕入先）名称
	private String tenpo_cd		= null;				// 店舗コード
	private String syohin_cd		= null;				// 商品コード
	private String syohin_na		= null;				// 商品名称カナ（商品名称）
	private String genka_vl		= null;				// 原単価
	private String baika_vl		= null;				// 売単価
	private String hachu_tani_qt	= null;				// 発注単位
	private String hachu_qt		= null;				// 発注数
	private String hachu_kb		= null;				// 発注区分
	private String haisinsaki_cd	= null;				// 配信先
	private String sa_kb			= null;				// SA
	private String keiyu_center_cd	= null;				// 経由センターコード


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
	 * mst910101_DenpyoHakkoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst910101_DenpyoHakkoBean getmst910101_DenpyoHakkoBean(DataHolder dataHolder)
	{
		mst910101_DenpyoHakkoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst910101_DenpyoHakkoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst910101_DenpyoHakkoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}
	
	
	// denpyo_nbに対するセッターとゲッターの集合
	public boolean setDenpyoNb(String denpyo_nb)
	{
		this.denpyo_nb = denpyo_nb;
		return true;
	}
	public String getDenpyoNb()
	{
		return cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH);
	}
	public String getDenpyoNbString()
	{
		return "'" + cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH) + "'";
	}
	public String getDenpyoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.denpyo_nb,DENPYO_NB_MAX_LENGTH));
	}


	// denpyogyo_nbに対するセッターとゲッターの集合
	public boolean setDenpyoGyoNb(String denpyogyo_nb)
	{
		this.denpyogyo_nb = denpyogyo_nb;
		return true;
	}
	public String getDenpyoGyoNb()
	{
		return cutString(this.denpyogyo_nb,DENPYO_NB_MAX_LENGTH);
	}
	public String getDenpyoGyoNbString()
	{
		return "'" + cutString(this.denpyogyo_nb,DENPYO_NB_MAX_LENGTH) + "'";
	}
	public String getDenpyoGyoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.denpyogyo_nb,DENPYOGYO_NB_MAX_LENGTH));
	}


	// nohin_dtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohin_dt)
	{
		this.nohin_dt = nohin_dt;
		return true;
	}
	public String getNohinDt()
	{
		return cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		return "'" + cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH));
	}


	// hachu_dtに対するセッターとゲッターの集合
	public boolean setHachuDt(String hachu_dt)
	{
		this.hachu_dt = hachu_dt;
		return true;
	}
	public String getHachuDt()
	{
		return cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH);
	}
	public String getHachuDtString()
	{
		return "'" + cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH) + "'";
	}
	public String getHachuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_dt,HACHU_DT_MAX_LENGTH));
	}

	// l_hanku_cdに対するセッターとゲッターの集合
	public boolean setLHankuCd(String l_hanku_cd)
	{
		this.l_hanku_cd = l_hanku_cd;
		return true;
	}
	public String getLHankuCd()
	{
		return cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH);
	}
	public String getLHankuCdString()
	{
		return "'" + cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getLHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH));
	}


	// torihikisaki_cdに対するセッターとゲッターの集合
	public boolean setTorihikisakiCd(String torihikisaki_cd)
	{
		this.torihikisaki_cd = torihikisaki_cd;
		return true;
	}
	public String getTorihikisakiCd()
	{
		return cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString()
	{
		return "'" + cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH));
	}


	// torihikisaki_naに対するセッターとゲッターの集合
	public boolean setTorihikisakiNa(String torihikisaki_na)
	{
		this.torihikisaki_na = torihikisaki_na;
		return true;
	}
	public String getTorihikisakiNa()
	{
		return cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH);
	}
	public String getTorihikisakiNaString()
	{
		return "'" + cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH));
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
	public String getSyohinNa()
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


	// genka_vlに対するセッターとゲッターの集合
	public boolean setGenkaVl(String genka_vl)
	{
		this.genka_vl = genka_vl;
		return true;
	}
	public String getGenkaVl()
	{
		return cutString(this.genka_vl,GENKA_VL_MAX_LENGTH);
	}
	public String getGenkaVlString()
	{
		return "'" + cutString(this.genka_vl,GENKA_VL_MAX_LENGTH) + "'";
	}
	public String getGenkaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.genka_vl,GENKA_VL_MAX_LENGTH));
	}


	// baika_vlに対するセッターとゲッターの集合
	public boolean setBaikaVl(String baika_vl)
	{
		this.baika_vl = baika_vl;
		return true;
	}
	public String getBaikaVl()
	{
		return cutString(this.baika_vl,BAIKA_VL_MAX_LENGTH);
	}
	public String getBaikaVlString()
	{
		return "'" + cutString(this.baika_vl,BAIKA_VL_MAX_LENGTH) + "'";
	}
	public String getBaikaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baika_vl,BAIKA_VL_MAX_LENGTH));
	}


	// hachu_tani_qtに対するセッターとゲッターの集合
	public boolean setHachuTaniQt(String hachu_tani_qt)
	{
		this.hachu_tani_qt = hachu_tani_qt;
		return true;
	}
	public String getHachuTaniQt()
	{
		return cutString(this.hachu_tani_qt,HACHU_TANI_QT_MAX_LENGTH);
	}
	public String getHachuTaniQtString()
	{
		return "'" + cutString(this.hachu_tani_qt,HACHU_TANI_QT_MAX_LENGTH) + "'";
	}
	public String getHachuTaniQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_tani_qt,HACHU_TANI_QT_MAX_LENGTH));
	}

	
	// hachu_qtに対するセッターとゲッターの集合
	public boolean setHachuQt(String hachu_qt)
	{
		this.hachu_qt = hachu_qt;
		return true;
	}
	public String getHachuQt()
	{
		return cutString(this.hachu_qt,HACHU_QT_MAX_LENGTH);
	}
	public String getHachuQtString()
	{
		return "'" + cutString(this.hachu_qt,HACHU_QT_MAX_LENGTH) + "'";
	}
	public String getHachuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_qt,HACHU_QT_MAX_LENGTH));
	}
	
	// hachu_kbに対するセッターとゲッターの集合
	public boolean setHachuKb(String hachu_kb)
	{
		this.hachu_kb = hachu_kb;
		return true;
	}
	public String getHachuKb()
	{
		return cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH);
	}
	public String getHachuKbString()
	{
		return "'" + cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getHachuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_kb,HACHU_KB_MAX_LENGTH));
	}
	

	
	// haisinsaki_cdに対するセッターとゲッターの集合
	public boolean setHaisinsakiCd(String haisinsaki_cd)
	{
		this.haisinsaki_cd = haisinsaki_cd;
		return true;
	}
	public String getHaisinsakiCd()
	{
		return cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH);
	}
	public String getHaisinsakiCdString()
	{
		return "'" + cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH) + "'";
	}
	public String getHaisinsakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haisinsaki_cd,HAISINSAKI_CD_MAX_LENGTH));
	}
		
	
	// sa_kbに対するセッターとゲッターの集合
	public boolean setSaKb(String sa_kb)
	{
		this.sa_kb = sa_kb;
		return true;
	}
	public String getSaKb()
	{
		return cutString(this.sa_kb,SA_KB_MAX_LENGTH);
	}
	public String getSaKbString()
	{
		return "'" + cutString(this.sa_kb,SA_KB_MAX_LENGTH) + "'";
	}
	public String getSaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sa_kb,SA_KB_MAX_LENGTH));
	}

	
	// keiyu_center_cdに対するセッターとゲッターの集合
	public boolean setKeiyuCenterCd(String keiyu_center_cd)
	{
		this.keiyu_center_cd = keiyu_center_cd;
		return true;
	}
	public String getKeiyuCenterCd()
	{
		return cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH);
	}
	public String getKeiyuCenterCdString()
	{
		return "'" + cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getKeiyuCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiyu_center_cd,KEIYU_CENTER_CD_MAX_LENGTH));
	}
	

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "		denpyo_nb		= " + getDenpyoNbString()
				+ "  	nohin_dt		= " + getNohinDtString()
				+ "  	l_hanku_cd		= " + getLHankuCdString()
		    	+ "		torihikisaki_cd	= " + getTorihikisakiCdString()
				+ "		torihikisaki_na = " + getTorihikisakiNaString()
				+ "		tenpo_cd		= " + getTenpoCdString()
				+ "		syohin_cd		= " + getSyohinCdString()
				+ "		syohin_na		= " + getSyohinNaString()
				+ "		genka_vl		= " + getGenkaVlString()
				+ "		baika_vl		= " + getBaikaVlString()
				+ "		hachu_tani_qt	= " + getHachuTaniQtString()
				+ "		hachu_qt		= " + getHachuQtString()
				+ "		sa_kb			= " + getSaKbString()
				+ "		keiyu_center_cd = " + getKeiyuCenterCdString()
				+ "		createDatabase  = " + createDatabase;
	}

	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst900101_SyohinItiranBean コピー後のクラス
	 */
	public mst910101_DenpyoHakkoBean createClone()
	{
		mst910101_DenpyoHakkoBean bean = new mst910101_DenpyoHakkoBean();
		bean.setDenpyoNb(this.denpyo_nb);
		bean.setNohinDt(this.nohin_dt);
		bean.setLHankuCd(this.l_hanku_cd);
		bean.setTorihikisakiCd(this.torihikisaki_cd);
		bean.setTorihikisakiNa(this.torihikisaki_na);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setGenkaVl(this.genka_vl);
		bean.setBaikaVl(this.baika_vl);
		bean.setHachuTaniQt(this.hachu_tani_qt);
		bean.setHachuQt(this.hachu_qt);
		bean.setHachuKb(this.hachu_kb);
		bean.setSaKb(this.sa_kb);
		bean.setKeiyuCenterCd(this.keiyu_center_cd);
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
		if( !( o instanceof mst910101_DenpyoHakkoBean ) ) return false;
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
