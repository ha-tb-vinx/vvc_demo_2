/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius koyama
 * @version 1.0(2005/03/22)初版作成
 */
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
public class mst900101_SyohinItiranBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final int HACHU_NO_MAX_LENGTH 	= 10;	//発注№の長さ
	public static final int TENPO_CD_MAX_LENGTH	= 6;	//店舗コードの長さ
	public static final int URIKU_CD_MAX_LENGTH	= 4;	//売区コードの長さ
	public static final int TENHANKU_CD_MAX_LENGTH = 4;	//店販区コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;	//仕入先コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH	= 13;	//商品コードの長さ
	public static final int NOUHINBI_DT_MAX_LENGTH = 8;	//納品日の長さ
	public static final int GENKA_VL_MAX_LENGTH 	= 11;	//原単価の長さ
	public static final int BAIKA_VL_MAX_LENGTH	= 7;	//売単価の長さ
	public static final int HACHU_QT_MAX_LENGTH	= 6;	//発注数の長さ
	
	public static final int JYOTAI_KB_MAX_LENGTH = 1;	//状態区分（非生鮮）の長さ
	public static final int JYOTAI_FRS_KB_MAX_LENGTH = 1;//状態区分（生鮮）の長さ


	private String hachu_no = null;		//発注№
	private String tenpo_cd = null;		//店舗コード
	private String uriku_cd = null;		//売区コード
	private String tenhanku_cd = null;	//店販区コード
	private String siiresaki_cd = null;	//仕入先コード
	private String syohin_cd = null;		//商品コード
	private String nouhinbi_dt = null;	//納品日
	private String genka_vl = null;		//原単価
	private String baika_vl = null;		//売単価
	private String hachu_qt = null;		//発注数
	
	private String jyotai_kb = null;		//状態区分（非生鮮）
	private String jyotai_frs_kb = null;	//状態区分（生鮮）

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
	 * mst900101_SyohinItiranBeanを１件のみ抽出したい時に使用する
	 */
	public static mst900101_SyohinItiranBean getmst900101_SyohinItiranBean(DataHolder dataHolder)
	{
		mst900101_SyohinItiranBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst900101_SyohinItiranDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst900101_SyohinItiranBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}
	
	
	// hachu_noに対するセッターとゲッターの集合
	public boolean setHachuNo(String hachu_no)
	{
		this.hachu_no = hachu_no;
		return true;
	}
	public String getHachuNo()
	{
		return cutString(this.hachu_no,HACHU_NO_MAX_LENGTH);
	}
	public String getHachuNoString()
	{
		return "'" + cutString(this.hachu_no,HACHU_NO_MAX_LENGTH) + "'";
	}
	public String getHachuNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_no,HACHU_NO_MAX_LENGTH));
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


	// nouhinbi_dtに対するセッターとゲッターの集合
	public boolean setNouhinbiDt(String nouhinbi_dt)
	{
		this.nouhinbi_dt = nouhinbi_dt;
		return true;
	}
	public String getNouhinbiDt()
	{
		return cutString(this.nouhinbi_dt,NOUHINBI_DT_MAX_LENGTH);
	}
	public String getNouhinbiDtString()
	{
		return "'" + cutString(this.nouhinbi_dt,NOUHINBI_DT_MAX_LENGTH) + "'";
	}
	public String getNouhinbiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nouhinbi_dt,NOUHINBI_DT_MAX_LENGTH));
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
	
	
	
	// jyotai_frs_kbに対するセッターとゲッターの集合
	public boolean setJyotaiFrsKb(String jyotai_frs_kb)
	{
		this.jyotai_frs_kb = jyotai_frs_kb;
		return true;
	}
	public String getJyotaiFrsKb()
	{
		return cutString(this.jyotai_frs_kb,JYOTAI_FRS_KB_MAX_LENGTH);
	}
	public String getJyotaiFrsKbString()
	{
		return "'" + cutString(this.jyotai_frs_kb,JYOTAI_FRS_KB_MAX_LENGTH) + "'";
	}
	public String getJyotaiFrsKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jyotai_frs_kb,JYOTAI_FRS_KB_MAX_LENGTH));
	}
	

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  hachu_no = " + getHachuNoString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  uriku_cd = " + getUrikuCdString()
		    + "  tenhanku_cd = " + getTenhankuCdString()
			+ "  siiresaki_cd = " + getSiiresakiCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  nouhinbi_dt = " + getNouhinbiDtString()
			+ "  genka_vl = " + getGenkaVlString()
			+ "  baika_vl = " + getBaikaVlString()
			+ "  hachu_qt = " + getHachuQtString()
			+ "  jyotai_kb = " + getJyotaiKbString()
			+ "  jyotai_frs_kb = " + getJyotaiFrsKbString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst900101_SyohinItiranBean コピー後のクラス
	 */
	public mst900101_SyohinItiranBean createClone()
	{
		mst900101_SyohinItiranBean bean = new mst900101_SyohinItiranBean();
		bean.setHachuNo(this.hachu_no);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setUrikuCd(this.uriku_cd);
		bean.setTenhankuCd(this.tenhanku_cd);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setNouhinbiDt(this.nouhinbi_dt);
		bean.setGenkaVl(this.genka_vl);
		bean.setBaikaVl(this.baika_vl);
		bean.setHachuQt(this.hachu_qt);
		bean.setJyotaiKb(this.jyotai_kb);
		bean.setJyotaiFrsKb(this.jyotai_frs_kb);
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
		if( !( o instanceof mst900101_SyohinItiranBean ) ) return false;
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
