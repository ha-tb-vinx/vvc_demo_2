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
public class mst560101_ButuryuTenpoLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コード (FK)の長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;//小業種コードの長さ
	public static final int BUTURYU_CENTER_CD_MAX_LENGTH = 6;//物流センターコードの長さ
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	public static final int HAISO_1_FG_MAX_LENGTH = 1;//１便配送の長さ
//	public static final int HAISO_2_FG_MAX_LENGTH = 1;//２便配送の長さ
//	public static final int HAISO_3_FG_MAX_LENGTH = 1;//３便配送の長さ
	public static final int BIN_KB_MAX_LENGTH = 1;//便区分の長さ
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int INSERT_USER_NM_MAX_LENGTH = 20;//作成者名の長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int UPDATE_USER_NM_MAX_LENGTH = 20;//更新者名の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;//処理選択
	public static final int BC_NA_MAX_LENGTH = 20;//物流センター名

	private String tenpo_cd = null;	//店舗コード (FK)
	private String kanji_rn = null;	//略式名称(漢字)
	private String s_gyosyu_cd = null;	//小業種コード
	private String buturyu_center_cd = null;	//物流センターコード
//	↓↓ＤＢバージョンアップによる変更（2005.05.19）
//	private String haiso_1_fg = null;	//１便配送
//	private String haiso_2_fg = null;	//２便配送
//	private String haiso_3_fg = null;	//３便配送
	private String bin_kb = null;
//	↑↑ＤＢバージョンアップによる変更（2005.05.19）
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String insert_user_nm = null;	//作成者名
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String update_user_nm = null;	//更新者名
	private String sentaku = null;	//処理選択
	private String bc_na = null;	//物流センター名

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
	 * mst560101_ButurtyTenpoLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst560101_ButuryuTenpoLBean getmst560101_ButurtyTenpoLBean(DataHolder dataHolder)
	{
		mst560101_ButuryuTenpoLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst560101_ButuryuTenpoLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst560101_ButuryuTenpoLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
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

	// bc_naに対するセッターとゲッターの集合
	public boolean setBcNa(String bc_na)
	{
		this.bc_na = bc_na;
		return true;
	}
	public String getBcNa()
	{
		return cutString(this.bc_na,BC_NA_MAX_LENGTH);
	}
	public String getBcNaString()
	{
		return "'" + cutString(this.bc_na,BC_NA_MAX_LENGTH) + "'";
	}
	public String getBcNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bc_na,BC_NA_MAX_LENGTH));
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


	// insert_user_nmに対するセッターとゲッターの集合
	public boolean setInsertUserNm(String insert_user_nm)
	{
		this.insert_user_nm = insert_user_nm;
		return true;
	}
	public String getInsertUserNm()
	{
		return cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH);
	}
	public String getInsertUserNmString()
	{
		return "'" + cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH) + "'";
	}
	public String getInsertUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_nm,INSERT_USER_NM_MAX_LENGTH));
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


	// update_user_nmに対するセッターとゲッターの集合
	public boolean setUpdateUserNm(String update_user_nm)
	{
		this.update_user_nm = update_user_nm;
		return true;
	}
	public String getUpdateUserNm()
	{
		return cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH);
	}
	public String getUpdateUserNmString()
	{
		return "'" + cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_nm,UPDATE_USER_NM_MAX_LENGTH));
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  kanji_rn = " + getKanjiRnString()
			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  buturyu_center_cd = " + getButuryuCenterCdString()
			+ "  bc_na = " + getBcNa()
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//			+ "  haiso_1_fg = " + getHaiso1FgString()
//			+ "  haiso_2_fg = " + getHaiso2FgString()
//			+ "  haiso_3_fg = " + getHaiso3FgString()
			+ "  bin_kb  = " + getBinKbString()
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  insert_user_nm = " + getInsertUserNmString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  update_user_nm = " + getUpdateUserNmString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst560101_ButurtyTenpoLBean コピー後のクラス
	 */
	public mst560101_ButuryuTenpoLBean createClone()
	{
		mst560101_ButuryuTenpoLBean bean = new mst560101_ButuryuTenpoLBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setKanjiRn(this.kanji_rn);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setButuryuCenterCd(this.buturyu_center_cd);
		bean.setBcNa(this.bc_na);
//		↓↓ＤＢバージョンアップによる変更（2005.05.19）
//		bean.setHaiso1Fg(this.haiso_1_fg);
//		bean.setHaiso2Fg(this.haiso_2_fg);
//		bean.setHaiso3Fg(this.haiso_3_fg);
		bean.setBinKb(this.bin_kb);
//		↑↑ＤＢバージョンアップによる変更（2005.05.19）
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setInsertUserNm(this.insert_user_nm);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateUserNm(this.update_user_nm);
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
		if( !( o instanceof mst560101_ButuryuTenpoLBean ) ) return false;
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
