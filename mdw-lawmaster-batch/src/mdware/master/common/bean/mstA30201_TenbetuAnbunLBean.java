/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006/07/19)初版作成
 */
package mdware.master.common.bean;

import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店グルーピングマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店グルーピングマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006/07/19)初版作成
 */
public class mstA30201_TenbetuAnbunLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH = 4;// 部門コード
	public static final int GROUPNO_CD_LENGTH = 2;	// グループコード
	public static final int YOTO_KB_MAX_LENGTH = 1;	// 用途区分
	public static final int TENPO_CD_MAX_LENGTH = 6; // 店舗コード
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	public static final int KANJI_RN_MAX_LENGTH = 20; // 略式名称(漢字)
	
	private String bumon_cd = null;// 部門コード
	private String groupno_cd = null;// グループコード
	private String copygroupno_cd = null;// コピーグループコード
	private String yoto_kb = null;		// 用途区分
	private String tenpo_cd = null; // 店舗コード
	private String tenpo_na = null;		// 略式名称(漢字)
	private double rank_nb = 0; // 順位
	private double anbun_rt = 0; //按分率
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
	 * mstA30201_TenbetuAnbunLBeanを１件のみ抽出したい時に使用する
	 */
	public static mstA30201_TenbetuAnbunLBean getTENBETUANBUNBean(DataHolder dataHolder)
	{
		mstA30201_TenbetuAnbunLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstA30201_TenbetuAnbunLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstA30201_TenbetuAnbunLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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
	
	// groupno_cdに対するセッターとゲッターの集合
	public boolean setGroupnoCd(String groupno_cd)
	{
		this.groupno_cd = groupno_cd;
		return true;
	}
	public String getGroupnoCd()
	{
		return cutString(this.groupno_cd,GROUPNO_CD_LENGTH);
	}
	public String getGroupnoCdString()
	{
		return "'" + cutString(this.groupno_cd,GROUPNO_CD_LENGTH) + "'";
	}
	public String getGroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.groupno_cd,GROUPNO_CD_LENGTH));
	}
	
	// copygroupno_cdに対するセッターとゲッターの集合
	public boolean setCopygroupnoCd(String copygroupno_cd)
	{
		this.copygroupno_cd = copygroupno_cd;
		return true;
	}
	public String getCopygroupnoCd()
	{
		return cutString(this.copygroupno_cd,GROUPNO_CD_LENGTH);
	}
	public String getCopygroupnoCdString()
	{
		return "'" + cutString(this.copygroupno_cd,GROUPNO_CD_LENGTH) + "'";
	}
	public String getCopygroupnoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.copygroupno_cd,GROUPNO_CD_LENGTH));
	}

	// yoto_kbに対するセッターとゲッターの集合
	public boolean setYotoKb(String yoto_kb)
	{
		this.yoto_kb = yoto_kb;
		return true;
	}
	public String getYotoKb()
	{
		return cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH);
	}
	public String getYotoKbString()
	{
		return "'" + cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH) + "'";
	}
	public String getYotoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yoto_kb,YOTO_KB_MAX_LENGTH));
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
		return cutString(this.tenpo_na,KANJI_RN_MAX_LENGTH);
	}
	public String getTenpoNaString()
	{
		return "'" + cutString(this.tenpo_na,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getTenpoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_na,KANJI_RN_MAX_LENGTH));
	}
		
	// rank_nbに対するセッターとゲッターの集合
	public boolean setRankNb(String rank_nb)
	{
		try
		{
			this.rank_nb = Double.parseDouble(rank_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setRankNb(double rank_nb)
	{
		this.rank_nb = rank_nb;
		return true;
	}
	public double getRankNb()
	{
		return this.rank_nb;
	}
	public String getRankNbString()
	{
		return String.valueOf(this.rank_nb);
	}

	// anbun_rtに対するセッターとゲッターの集合
	public boolean setAnbunRt(String anbun_rt)
	{
		try
		{
			this.anbun_rt = Double.parseDouble(anbun_rt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setAnbunRt(double anbun_rt)
	{
		this.anbun_rt = anbun_rt;
		return true;
	}
	public double getAnbunRt()
	{
		return this.anbun_rt;
	}
	public String getAnbunRtString()
	{
		return String.valueOf(this.anbun_rt);
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

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  yoto_kb = " + getYotoKbString()
			+ "  groupno_cd = " + getGroupnoCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  rank_nb = " + getRankNbString()
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
	 * @return mst11_SyohinBean コピー後のクラス
	 */
	public mstA30201_TenbetuAnbunLBean createClone()
	{
		mstA30201_TenbetuAnbunLBean bean = new mstA30201_TenbetuAnbunLBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setYotoKb(this.yoto_kb);
		bean.setGroupnoCd(this.groupno_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNa(this.tenpo_na);
		bean.setRankNb(this.rank_nb);
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
		if( !( o instanceof mstA30101_TenbetuAnbunBean ) ) return false;
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
