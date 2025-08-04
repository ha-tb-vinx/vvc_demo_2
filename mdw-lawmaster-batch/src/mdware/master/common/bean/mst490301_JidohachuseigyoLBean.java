/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/31)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動発注制御マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動発注制御マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/31)初版作成
 */
public class mst490301_JidohachuseigyoLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KANRI_KB_MAX_LENGTH = 1;//管理区分の長さ
	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int HANKU_NA_MAX_LENGTH = 20;//販区名の長さ
	public static final int HINSHU_CD_MAX_LENGTH = 4;//品種コードの長さ
	public static final int HINSHU_NA_MAX_LENGTH = 20;//品種名の長さ
	public static final int KANRI_CD_MAX_LENGTH = 4;//管理コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int TENPO_NA_MAX_LENGTH = 20;//店舗名の長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int JIDO_HACHU_KB_MAX_LENGTH = 1;//自動発注区分の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	public static final int SENTAKU_MAX_LENGTH = 1;//選択の長さ
	public static final int SENTAKU_ORG_MAX_LENGTH = 1;//選択の長さ
	public static final int DISABLED_MAX_LENGTH = 1;//非選択状態の長さ

	private String kanri_kb = null;	//管理区分
	private String hanku_cd = null;	//販区コード
	private String hanku_na = null;	//
	private String hinshu_cd = null;	//
	private String hinshu_na = null;	//
	private String kanri_cd = null;	//管理コード
	private String tenpo_cd = null;	//店舗コード (FK)
	private String tenpo_na = null;	//
	private String yuko_dt = null;	//有効日
	private String jido_hachu_kb = null;	//自動発注区分
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String sentaku = null;	//
	private String sentaku_org = null;	//
	private String disabled = null;	//

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
	 * mst490301_JidohachuseigyoLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst490301_JidohachuseigyoLBean getmst490301_JidohachuseigyoLBean(DataHolder dataHolder)
	{
		mst490301_JidohachuseigyoLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst490301_JidohachuseigyoLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst490301_JidohachuseigyoLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// kanri_kbに対するセッターとゲッターの集合
	public boolean setKanriKb(String kanri_kb)
	{
		this.kanri_kb = kanri_kb;
		return true;
	}
	public String getKanriKb()
	{
		return cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH);
	}
	public String getKanriKbString()
	{
		return "'" + cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH) + "'";
	}
	public String getKanriKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_kb,KANRI_KB_MAX_LENGTH));
	}


	// hanku_cdに対するセッターとゲッターの集合
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


	// hanku_naに対するセッターとゲッターの集合
	public boolean setHankuNa(String hanku_na)
	{
		this.hanku_na = hanku_na;
		return true;
	}
	public String getHankuNa()
	{
		return cutString(this.hanku_na,HANKU_NA_MAX_LENGTH);
	}
	public String getHankuNaString()
	{
		return "'" + cutString(this.hanku_na,HANKU_NA_MAX_LENGTH) + "'";
	}
	public String getHankuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_na,HANKU_NA_MAX_LENGTH));
	}


	// hinshu_cdに対するセッターとゲッターの集合
	public boolean setHinshuCd(String hinshu_cd)
	{
		this.hinshu_cd = hinshu_cd;
		return true;
	}
	public String getHinshuCd()
	{
		return cutString(this.hinshu_cd,HINSHU_CD_MAX_LENGTH);
	}
	public String getHinshuCdString()
	{
		return "'" + cutString(this.hinshu_cd,HINSHU_CD_MAX_LENGTH) + "'";
	}
	public String getHinshuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinshu_cd,HINSHU_CD_MAX_LENGTH));
	}


	// hinshu_naに対するセッターとゲッターの集合
	public boolean setHinshuNa(String hinshu_na)
	{
		this.hinshu_na = hinshu_na;
		return true;
	}
	public String getHinshuNa()
	{
		return cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH);
	}
	public String getHinshuNaString()
	{
		return "'" + cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH) + "'";
	}
	public String getHinshuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinshu_na,HINSHU_NA_MAX_LENGTH));
	}


	// kanri_cdに対するセッターとゲッターの集合
	public boolean setKanriCd(String kanri_cd)
	{
		this.kanri_cd = kanri_cd;
		return true;
	}
	public String getKanriCd()
	{
		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
	}
	public String getKanriCdString()
	{
		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
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


	// tenpo_naに対するセッターとゲッターの集合
	public boolean setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
		return true;
	}
	public String getTenpoNa()
	{
		return cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		return "'" + cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH) + "'";
	}
	public String getTenpoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_na,TENPO_NA_MAX_LENGTH));
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


	// jido_hachu_kbに対するセッターとゲッターの集合
	public boolean setJidoHachuKb(String jido_hachu_kb)
	{
		this.jido_hachu_kb = jido_hachu_kb;
		return true;
	}
	public String getJidoHachuKb()
	{
		return cutString(this.jido_hachu_kb,JIDO_HACHU_KB_MAX_LENGTH);
	}
	public String getJidoHachuKbString()
	{
		return "'" + cutString(this.jido_hachu_kb,JIDO_HACHU_KB_MAX_LENGTH) + "'";
	}
	public String getJidoHachuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jido_hachu_kb,JIDO_HACHU_KB_MAX_LENGTH));
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


	// sentaku_orgに対するセッターとゲッターの集合
	public boolean setSentakuOrg(String sentaku_org)
	{
		this.sentaku_org = sentaku_org;
		return true;
	}
	public String getSentakuOrg()
	{
		return cutString(this.sentaku_org,SENTAKU_ORG_MAX_LENGTH);
	}
	public String getSentakuOrgString()
	{
		return "'" + cutString(this.sentaku_org,SENTAKU_ORG_MAX_LENGTH) + "'";
	}
	public String getSentakuOrgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku_org,SENTAKU_ORG_MAX_LENGTH));
	}


	// disabledに対するセッターとゲッターの集合
	public boolean setDisabled(String disabled)
	{
		this.disabled = disabled;
		return true;
	}
	public String getDisabled()
	{
		return cutString(this.disabled,DISABLED_MAX_LENGTH);
	}
	public String getDisabledString()
	{
		return "'" + cutString(this.disabled,DISABLED_MAX_LENGTH) + "'";
	}
	public String getDisabledHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.disabled,DISABLED_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  kanri_kb = " + getKanriKbString()
			+ "  hanku_cd = " + getHankuCdString()
			+ "  hanku_na = " + getHankuNaString()
			+ "  hinshu_cd = " + getHinshuCdString()
			+ "  hinshu_na = " + getHinshuNaString()
			+ "  kanri_cd = " + getKanriCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  jido_hachu_kb = " + getJidoHachuKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  sentaku = " + getSentakuString()
			+ "  sentaku_org = " + getSentakuOrgString()
			+ "  disabled = " + getDisabledString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst490301_JidohachuseigyoLBean コピー後のクラス
	 */
	public mst490301_JidohachuseigyoLBean createClone()
	{
		mst490301_JidohachuseigyoLBean bean = new mst490301_JidohachuseigyoLBean();
		bean.setKanriKb(this.kanri_kb);
		bean.setHankuCd(this.hanku_cd);
		bean.setHankuNa(this.hanku_na);
		bean.setHinshuCd(this.hinshu_cd);
		bean.setHinshuNa(this.hinshu_na);
		bean.setKanriCd(this.kanri_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNa(this.tenpo_na);
		bean.setYukoDt(this.yuko_dt);
		bean.setJidoHachuKb(this.jido_hachu_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setSentaku(this.sentaku);
		bean.setSentakuOrg(this.sentaku_org);
		bean.setDisabled(this.disabled);
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
		if( !( o instanceof mst490301_JidohachuseigyoLBean ) ) return false;
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
