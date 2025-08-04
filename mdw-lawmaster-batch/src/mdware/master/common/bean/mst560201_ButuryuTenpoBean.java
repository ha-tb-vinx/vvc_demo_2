/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流店舗マスタ（生鮮）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流店舗マスタ（生鮮）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/19)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流店舗マスタ（生鮮）のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流店舗マスタ（生鮮）のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/19)初版作成
 */
public class mst560201_ButuryuTenpoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;//小業種コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int BUTURYU_CENTER_CD_MAX_LENGTH = 6;//物流センターコードの長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int HAISO_1_FG_MAX_LENGTH = 1;//１便配送の長さ
//	public static final int HAISO_2_FG_MAX_LENGTH = 1;//２便配送の長さ
//	public static final int HAISO_3_FG_MAX_LENGTH = 1;//３便配送の長さ
	public static final int BIN_KB_MAX_LENGTH = 1;//便区分の長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String s_gyosyu_cd = null;	//小業種コード
	private String tenpo_cd = null;	//店舗コード (FK)
	private String buturyu_center_cd = null;	//物流センターコード
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String haiso_1_fg = null;	//１便配送
//	private String haiso_2_fg = null;	//２便配送
//	private String haiso_3_fg = null;	//３便配送
	private String bin_kb = null;
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
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
	 * mst560201_ButuryuTenpoBeanを１件のみ抽出したい時に使用する
	 */
	public static mst560201_ButuryuTenpoBean getmst560201_ButuryuTenpoBean(DataHolder dataHolder)
	{
		mst560201_ButuryuTenpoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst560201_ButuryuTenpoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst560201_ButuryuTenpoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
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


	// buturyu_center_cdに対するセッターとゲッターの集合
	public boolean setButuryuCenterCd(String buturyu_center_cd)
	{
		this.buturyu_center_cd = buturyu_center_cd;
		return true;
	}
	public String getButuryuCenterCd()
	{
		return cutString(this.buturyu_center_cd,BUTURYU_CENTER_CD_MAX_LENGTH);
	}
	public String getButuryuCenterCdString()
	{
		return "'" + cutString(this.buturyu_center_cd,BUTURYU_CENTER_CD_MAX_LENGTH) + "'";
	}
	public String getButuryuCenterCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.buturyu_center_cd,BUTURYU_CENTER_CD_MAX_LENGTH));
	}


//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
	// haiso_1_fgに対するセッターとゲッターの集合
//	public boolean setHaiso1Fg(String haiso_1_fg)
//	{
//		this.haiso_1_fg = haiso_1_fg;
//		return true;
//	}
//	public String getHaiso1Fg()
//	{
//		return cutString(this.haiso_1_fg,HAISO_1_FG_MAX_LENGTH);
//	}
//	public String getHaiso1FgString()
//	{
//		return "'" + cutString(this.haiso_1_fg,HAISO_1_FG_MAX_LENGTH) + "'";
//	}
//	public String getHaiso1FgHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.haiso_1_fg,HAISO_1_FG_MAX_LENGTH));
//	}


	// haiso_2_fgに対するセッターとゲッターの集合
//	public boolean setHaiso2Fg(String haiso_2_fg)
//	{
//		this.haiso_2_fg = haiso_2_fg;
//		return true;
//	}
//	public String getHaiso2Fg()
//	{
//		return cutString(this.haiso_2_fg,HAISO_2_FG_MAX_LENGTH);
//	}
//	public String getHaiso2FgString()
//	{
//		return "'" + cutString(this.haiso_2_fg,HAISO_2_FG_MAX_LENGTH) + "'";
//	}
//	public String getHaiso2FgHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.haiso_2_fg,HAISO_2_FG_MAX_LENGTH));
//	}


	// haiso_3_fgに対するセッターとゲッターの集合
//	public boolean setHaiso3Fg(String haiso_3_fg)
//	{
//		this.haiso_3_fg = haiso_3_fg;
//		return true;
//	}
//	public String getHaiso3Fg()
//	{
//		return cutString(this.haiso_3_fg,HAISO_3_FG_MAX_LENGTH);
//	}
//	public String getHaiso3FgString()
//	{
//		return "'" + cutString(this.haiso_3_fg,HAISO_3_FG_MAX_LENGTH) + "'";
//	}
//	public String getHaiso3FgHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.haiso_3_fg,HAISO_3_FG_MAX_LENGTH));
//	}
	// bin_kbに対するセッターとゲッターの集合
	public boolean setBinKb(String bin_kb)
	{
		this.bin_kb = bin_kb;
		return true;
	}
	public String getBinKb()
	{
		return cutString(this.bin_kb,BIN_KB_MAX_LENGTH);
	}
	public String getBinKbString()
	{
		return "'" + cutString(this.bin_kb,BIN_KB_MAX_LENGTH) + "'";
	}
	public String getBinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bin_kb,BIN_KB_MAX_LENGTH));
	}
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）



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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  buturyu_center_cd = " + getButuryuCenterCdString()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  haiso_1_fg = " + getHaiso1FgString()
//			+ "  haiso_2_fg = " + getHaiso2FgString()
//			+ "  haiso_3_fg = " + getHaiso3FgString()
			+ "  bin_kb  = " + getBinKbString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst560201_ButuryuTenpoBean コピー後のクラス
	 */
	public mst560201_ButuryuTenpoBean createClone()
	{
		mst560201_ButuryuTenpoBean bean = new mst560201_ButuryuTenpoBean();
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setButuryuCenterCd(this.buturyu_center_cd);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setHaiso1Fg(this.haiso_1_fg);
//		bean.setHaiso2Fg(this.haiso_2_fg);
//		bean.setHaiso3Fg(this.haiso_3_fg);
		bean.setBinKb(this.bin_kb);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
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
		if( !( o instanceof mst560201_ButuryuTenpoBean ) ) return false;
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
