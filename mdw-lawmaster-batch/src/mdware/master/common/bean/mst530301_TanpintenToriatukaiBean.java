/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
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
 * @author Sirius Nakajima
 * @version 1.0(2005/03/18)初版作成
 */
public class mst530301_TanpintenToriatukaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;//販売開始日の長さ
	public static final int NON_ACT_KB_MAX_LENGTH = 1;//NON_ACT区分の長さ
	public static final int HASEIMOTO_KB_MAX_LENGTH = 1;//発生元区分の長さ
	public static final int TANAWARI_PATERN_MAX_LENGTH = 1;//棚割パターンの長さ
	public static final int JUKI_NB_MAX_LENGTH = 5;//什器NOの長さ
	public static final int TANA_NB_MAX_LENGTH = 5;//棚NOの長さ
	public static final int FACE_QT_MAX_LENGTH = 2;//フェイス数の長さ
	public static final int MIN_TINRETU_QT_MAX_LENGTH = 3;//最低陳列数の長さ
	public static final int MAX_TINRETU_QT_MAX_LENGTH = 3;//最大陳列数の長さ
	public static final int BASE_ZAIKO_NISU_QT_MAX_LENGTH = 3;//基準在庫日数の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
//	******** ＤＢ Ver3.6修正箇所 *****************************************
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String hanku_cd = null;	//販区コード
	private String syohin_cd = null;	//商品コード
	private String tenpo_cd = null;	//店舗コード (FK)
	private String yuko_dt = null;	//有効日
	private String hanbai_st_dt = null;	//販売開始日
	private String non_act_kb = null;	//NON_ACT区分
	private String haseimoto_kb = null;	//発生元区分
	private String tanawari_patern = null;	//棚割パターン
	private String juki_nb = null;	//什器NO
	private String tana_nb = null;	//棚NO
	private String face_qt = null;	//フェイス数
	private String min_tinretu_qt = null;	//最低陳列数
	private String max_tinretu_qt = null;	//最大陳列数
	private String base_zaiko_nisu_qt = null;	//基準在庫日数
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
//	******** ＤＢ Ver3.6修正箇所 *****************************************
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

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
	 * RTANPINTENTORIATUKAIBeanを１件のみ抽出したい時に使用する
	 */
	public static mst530301_TanpintenToriatukaiBean getRTANPINTENTORIATUKAIBean(DataHolder dataHolder)
	{
		mst530301_TanpintenToriatukaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst530301_TanpintenToriatukaiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst530301_TanpintenToriatukaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// non_act_kbに対するセッターとゲッターの集合
	public boolean setNonActKb(String non_act_kb)
	{
		this.non_act_kb = non_act_kb;
		return true;
	}
	public String getNonActKb()
	{
		return cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH);
	}
	public String getNonActKbString()
	{
		return "'" + cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH) + "'";
	}
	public String getNonActKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.non_act_kb,NON_ACT_KB_MAX_LENGTH));
	}


	// haseimoto_kbに対するセッターとゲッターの集合
	public boolean setHaseimotoKb(String haseimoto_kb)
	{
		this.haseimoto_kb = haseimoto_kb;
		return true;
	}
	public String getHaseimotoKb()
	{
		return cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH);
	}
	public String getHaseimotoKbString()
	{
		return "'" + cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH) + "'";
	}
	public String getHaseimotoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haseimoto_kb,HASEIMOTO_KB_MAX_LENGTH));
	}


	// tanawari_paternに対するセッターとゲッターの集合
	public boolean setTanawariPatern(String tanawari_patern)
	{
		this.tanawari_patern = tanawari_patern;
		return true;
	}
	public String getTanawariPatern()
	{
		return cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH);
	}
	public String getTanawariPaternString()
	{
		return "'" + cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH) + "'";
	}
	public String getTanawariPaternHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanawari_patern,TANAWARI_PATERN_MAX_LENGTH));
	}


	// juki_nbに対するセッターとゲッターの集合
	public boolean setJukiNb(String juki_nb)
	{
		this.juki_nb = juki_nb;
		return true;
	}
	public String getJukiNb()
	{
		return cutString(this.juki_nb,JUKI_NB_MAX_LENGTH);
	}
	public String getJukiNbString()
	{
		return "'" + cutString(this.juki_nb,JUKI_NB_MAX_LENGTH) + "'";
	}
	public String getJukiNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.juki_nb,JUKI_NB_MAX_LENGTH));
	}


	// tana_nbに対するセッターとゲッターの集合
	public boolean setTanaNb(String tana_nb)
	{
		this.tana_nb = tana_nb;
		return true;
	}
	public String getTanaNb()
	{
		return cutString(this.tana_nb,TANA_NB_MAX_LENGTH);
	}
	public String getTanaNbString()
	{
		return "'" + cutString(this.tana_nb,TANA_NB_MAX_LENGTH) + "'";
	}
	public String getTanaNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tana_nb,TANA_NB_MAX_LENGTH));
	}


	// face_qtに対するセッターとゲッターの集合
	public boolean setFaceQt(String face_qt)
	{
		this.face_qt = face_qt;
		return true;
	}
	public String getFaceQt()
	{
		return cutString(this.face_qt,FACE_QT_MAX_LENGTH);
	}
	public String getFaceQtString()
	{
		return "'" + cutString(this.face_qt,FACE_QT_MAX_LENGTH) + "'";
	}
	public String getFaceQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.face_qt,FACE_QT_MAX_LENGTH));
	}


	// min_tinretu_qtに対するセッターとゲッターの集合
	public boolean setMinTinretuQt(String min_tinretu_qt)
	{
		this.min_tinretu_qt = min_tinretu_qt;
		return true;
	}
	public String getMinTinretuQt()
	{
		return cutString(this.min_tinretu_qt,MIN_TINRETU_QT_MAX_LENGTH);
	}
	public String getMinTinretuQtString()
	{
		return "'" + cutString(this.min_tinretu_qt,MIN_TINRETU_QT_MAX_LENGTH) + "'";
	}
	public String getMinTinretuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.min_tinretu_qt,MIN_TINRETU_QT_MAX_LENGTH));
	}


	// max_tinretu_qtに対するセッターとゲッターの集合
	public boolean setMaxTinretuQt(String max_tinretu_qt)
	{
		this.max_tinretu_qt = max_tinretu_qt;
		return true;
	}
	public String getMaxTinretuQt()
	{
		return cutString(this.max_tinretu_qt,MAX_TINRETU_QT_MAX_LENGTH);
	}
	public String getMaxTinretuQtString()
	{
		return "'" + cutString(this.max_tinretu_qt,MAX_TINRETU_QT_MAX_LENGTH) + "'";
	}
	public String getMaxTinretuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.max_tinretu_qt,MAX_TINRETU_QT_MAX_LENGTH));
	}


	// base_zaiko_nisu_qtに対するセッターとゲッターの集合
	public boolean setBaseZaikoNisuQt(String base_zaiko_nisu_qt)
	{
		this.base_zaiko_nisu_qt = base_zaiko_nisu_qt;
		return true;
	}
	public String getBaseZaikoNisuQt()
	{
		return cutString(this.base_zaiko_nisu_qt,BASE_ZAIKO_NISU_QT_MAX_LENGTH);
	}
	public String getBaseZaikoNisuQtString()
	{
		return "'" + cutString(this.base_zaiko_nisu_qt,BASE_ZAIKO_NISU_QT_MAX_LENGTH) + "'";
	}
	public String getBaseZaikoNisuQtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.base_zaiko_nisu_qt,BASE_ZAIKO_NISU_QT_MAX_LENGTH));
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
//	******** ＤＢ Ver3.6修正箇所 *****************************************
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserTs()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserTsString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserTsHTMLString()
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
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		return "  hanku_cd = " + getHankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  hanbai_st_dt = " + getHanbaiStDtString()
			+ "  non_act_kb = " + getNonActKbString()
			+ "  haseimoto_kb = " + getHaseimotoKbString()
			+ "  tanawari_patern = " + getTanawariPaternString()
			+ "  juki_nb = " + getJukiNbString()
			+ "  tana_nb = " + getTanaNbString()
			+ "  face_qt = " + getFaceQtString()
			+ "  min_tinretu_qt = " + getMinTinretuQtString()
			+ "  max_tinretu_qt = " + getMaxTinretuQtString()
			+ "  base_zaiko_nisu_qt = " + getBaseZaikoNisuQtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserTsString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTANPINTENTORIATUKAIBean コピー後のクラス
	 */
	public mst530301_TanpintenToriatukaiBean createClone()
	{
		mst530301_TanpintenToriatukaiBean bean = new mst530301_TanpintenToriatukaiBean();
		bean.setHankuCd(this.hanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setHanbaiStDt(this.hanbai_st_dt);
		bean.setNonActKb(this.non_act_kb);
		bean.setHaseimotoKb(this.haseimoto_kb);
		bean.setTanawariPatern(this.tanawari_patern);
		bean.setJukiNb(this.juki_nb);
		bean.setTanaNb(this.tana_nb);
		bean.setFaceQt(this.face_qt);
		bean.setMinTinretuQt(this.min_tinretu_qt);
		bean.setMaxTinretuQt(this.max_tinretu_qt);
		bean.setBaseZaikoNisuQt(this.base_zaiko_nisu_qt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
//		******** ＤＢ Ver3.6修正箇所 *****************************************
		bean.setUpdateUserTs(this.update_user_id);
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
		if( !( o instanceof mst530301_TanpintenToriatukaiBean ) ) return false;
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
