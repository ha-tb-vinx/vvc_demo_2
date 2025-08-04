package mdware.master.util.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: マスタ制御情報</p>
 * <p>説明: マスタ制御情報Ｂｅａｎ</p>
 * <p>著作権: Copyright (c) 2007</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0
 */
public class RMasterControlBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TAIKEI_KIRIKAE_KIJUN_DT_T_MAX_LENGTH = 8;
	public static final int TAIKEI_KIRIKAE_KIJUN_DT_J_MAX_LENGTH = 8;
	public static final int TAIKEI_KIRIKAE_KIJUN_DT_G_MAX_LENGTH = 8;
	public static final int TAIKEI_KIRIKAE_KIJUN_DT_F_MAX_LENGTH = 8;
	public static final int SONOTA_KIJUN_DT_1_MAX_LENGTH = 8;
	public static final int SONOTA_KIJUN_DT_2_MAX_LENGTH = 8;
	public static final int SONOTA_KIJUN_DT_3_MAX_LENGTH = 8;
	public static final int INSERT_TS_MAX_LENGTH = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;

	private String taikei_kirikae_kijun_dt_t = null;
	private String taikei_kirikae_kijun_dt_j = null;
	private String taikei_kirikae_kijun_dt_g = null;
	private String taikei_kirikae_kijun_dt_f = null;
	private String sonota_kijun_dt_1 = null;
	private String sonota_kijun_dt_2 = null;
	private String sonota_kijun_dt_3 = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;

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
	 * RMasterControlBeanを１件のみ抽出したい時に使用する
	 */
	public static RMasterControlBean getRMasterControlBean(DataHolder dataHolder)
	{
		RMasterControlBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new RMasterControlDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (RMasterControlBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// taikei_kirikae_kijun_dt_tに対するセッターとゲッターの集合
	public boolean setTaikeiKirikaeKijunDtT(String taikei_kirikae_kijun_dt_t)
	{
		this.taikei_kirikae_kijun_dt_t = taikei_kirikae_kijun_dt_t;
		return true;
	}
	public String getTaikeiKirikaeKijunDtT()
	{
		return cutString(this.taikei_kirikae_kijun_dt_t,TAIKEI_KIRIKAE_KIJUN_DT_T_MAX_LENGTH);
	}
	public String getTaikeiKirikaeKijunDtTString()
	{
		return "'" + cutString(this.taikei_kirikae_kijun_dt_t,TAIKEI_KIRIKAE_KIJUN_DT_T_MAX_LENGTH) + "'";
	}
	public String getTaikeiKirikaeKijunDtTHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.taikei_kirikae_kijun_dt_t,TAIKEI_KIRIKAE_KIJUN_DT_T_MAX_LENGTH));
	}


	// taikei_kirikae_kijun_dt_jに対するセッターとゲッターの集合
	public boolean setTaikeiKirikaeKijunDtJ(String taikei_kirikae_kijun_dt_j)
	{
		this.taikei_kirikae_kijun_dt_j = taikei_kirikae_kijun_dt_j;
		return true;
	}
	public String getTaikeiKirikaeKijunDtJ()
	{
		return cutString(this.taikei_kirikae_kijun_dt_j,TAIKEI_KIRIKAE_KIJUN_DT_J_MAX_LENGTH);
	}
	public String getTaikeiKirikaeKijunDtJString()
	{
		return "'" + cutString(this.taikei_kirikae_kijun_dt_j,TAIKEI_KIRIKAE_KIJUN_DT_J_MAX_LENGTH) + "'";
	}
	public String getTaikeiKirikaeKijunDtJHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.taikei_kirikae_kijun_dt_j,TAIKEI_KIRIKAE_KIJUN_DT_J_MAX_LENGTH));
	}


	// taikei_kirikae_kijun_dt_gに対するセッターとゲッターの集合
	public boolean setTaikeiKirikaeKijunDtG(String taikei_kirikae_kijun_dt_g)
	{
		this.taikei_kirikae_kijun_dt_g = taikei_kirikae_kijun_dt_g;
		return true;
	}
	public String getTaikeiKirikaeKijunDtG()
	{
		return cutString(this.taikei_kirikae_kijun_dt_g,TAIKEI_KIRIKAE_KIJUN_DT_G_MAX_LENGTH);
	}
	public String getTaikeiKirikaeKijunDtGString()
	{
		return "'" + cutString(this.taikei_kirikae_kijun_dt_g,TAIKEI_KIRIKAE_KIJUN_DT_G_MAX_LENGTH) + "'";
	}
	public String getTaikeiKirikaeKijunDtGHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.taikei_kirikae_kijun_dt_g,TAIKEI_KIRIKAE_KIJUN_DT_G_MAX_LENGTH));
	}


	// taikei_kirikae_kijun_dt_fに対するセッターとゲッターの集合
	public boolean setTaikeiKirikaeKijunDtF(String taikei_kirikae_kijun_dt_f)
	{
		this.taikei_kirikae_kijun_dt_f = taikei_kirikae_kijun_dt_f;
		return true;
	}
	public String getTaikeiKirikaeKijunDtF()
	{
		return cutString(this.taikei_kirikae_kijun_dt_f,TAIKEI_KIRIKAE_KIJUN_DT_F_MAX_LENGTH);
	}
	public String getTaikeiKirikaeKijunDtFString()
	{
		return "'" + cutString(this.taikei_kirikae_kijun_dt_f,TAIKEI_KIRIKAE_KIJUN_DT_F_MAX_LENGTH) + "'";
	}
	public String getTaikeiKirikaeKijunDtFHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.taikei_kirikae_kijun_dt_f,TAIKEI_KIRIKAE_KIJUN_DT_F_MAX_LENGTH));
	}


	// sonota_kijun_dt_1に対するセッターとゲッターの集合
	public boolean setSonotaKijunDt1(String sonota_kijun_dt_1)
	{
		this.sonota_kijun_dt_1 = sonota_kijun_dt_1;
		return true;
	}
	public String getSonotaKijunDt1()
	{
		return cutString(this.sonota_kijun_dt_1,SONOTA_KIJUN_DT_1_MAX_LENGTH);
	}
	public String getSonotaKijunDt1String()
	{
		return "'" + cutString(this.sonota_kijun_dt_1,SONOTA_KIJUN_DT_1_MAX_LENGTH) + "'";
	}
	public String getSonotaKijunDt1HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sonota_kijun_dt_1,SONOTA_KIJUN_DT_1_MAX_LENGTH));
	}


	// sonota_kijun_dt_2に対するセッターとゲッターの集合
	public boolean setSonotaKijunDt2(String sonota_kijun_dt_2)
	{
		this.sonota_kijun_dt_2 = sonota_kijun_dt_2;
		return true;
	}
	public String getSonotaKijunDt2()
	{
		return cutString(this.sonota_kijun_dt_2,SONOTA_KIJUN_DT_2_MAX_LENGTH);
	}
	public String getSonotaKijunDt2String()
	{
		return "'" + cutString(this.sonota_kijun_dt_2,SONOTA_KIJUN_DT_2_MAX_LENGTH) + "'";
	}
	public String getSonotaKijunDt2HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sonota_kijun_dt_2,SONOTA_KIJUN_DT_2_MAX_LENGTH));
	}


	// sonota_kijun_dt_3に対するセッターとゲッターの集合
	public boolean setSonotaKijunDt3(String sonota_kijun_dt_3)
	{
		this.sonota_kijun_dt_3 = sonota_kijun_dt_3;
		return true;
	}
	public String getSonotaKijunDt3()
	{
		return cutString(this.sonota_kijun_dt_3,SONOTA_KIJUN_DT_3_MAX_LENGTH);
	}
	public String getSonotaKijunDt3String()
	{
		return "'" + cutString(this.sonota_kijun_dt_3,SONOTA_KIJUN_DT_3_MAX_LENGTH) + "'";
	}
	public String getSonotaKijunDt3HTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sonota_kijun_dt_3,SONOTA_KIJUN_DT_3_MAX_LENGTH));
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
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  taikei_kirikae_kijun_dt_t = " + getTaikeiKirikaeKijunDtTString()
			+ "  taikei_kirikae_kijun_dt_j = " + getTaikeiKirikaeKijunDtJString()
			+ "  taikei_kirikae_kijun_dt_g = " + getTaikeiKirikaeKijunDtGString()
			+ "  taikei_kirikae_kijun_dt_f = " + getTaikeiKirikaeKijunDtFString()
			+ "  sonota_kijun_dt_1 = " + getSonotaKijunDt1String()
			+ "  sonota_kijun_dt_2 = " + getSonotaKijunDt2String()
			+ "  sonota_kijun_dt_3 = " + getSonotaKijunDt3String()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RMasterControlBean コピー後のクラス
	 */
	public RMasterControlBean createClone()
	{
		RMasterControlBean bean = new RMasterControlBean();
		bean.setTaikeiKirikaeKijunDtT(this.taikei_kirikae_kijun_dt_t);
		bean.setTaikeiKirikaeKijunDtJ(this.taikei_kirikae_kijun_dt_j);
		bean.setTaikeiKirikaeKijunDtG(this.taikei_kirikae_kijun_dt_g);
		bean.setTaikeiKirikaeKijunDtF(this.taikei_kirikae_kijun_dt_f);
		bean.setSonotaKijunDt1(this.sonota_kijun_dt_1);
		bean.setSonotaKijunDt2(this.sonota_kijun_dt_2);
		bean.setSonotaKijunDt3(this.sonota_kijun_dt_3);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
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
		if( !( o instanceof RMasterControlBean ) ) return false;
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
