package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <P>登録票バイヤー承認ヘッダーテーブル検索とか。</p>
							
 */
public class mst999999_TourokuBYSyoninBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TORIKOMI_DT_MAX_LENGTH = 8;
	public static final int EXCEL_FILE_SYUBETSU_MAX_LENGTH = 3;
	public static final int UKETSUKE_NO_MAX_LENGTH = 4;
	public static final int EXCEL_FILE_NA_MAX_LENGTH = 20;//VARCHAR
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;
	public static final int UPLOAD_COMMENT_TX_MAX_LENGTH = 40;//varchar
	public static final int TOROKU_SYONIN_FG_MAX_LENGTH = 1;
	public static final int TOROKU_SYONIN_TS_MAX_LENGTH = 14;
	public static final int BY_NO_MAX_LENGTH = 6;
	public static final int SYONIN_COMMENT_TX_MAX_LENGTH = 40;//varchar
	public static final int EXCEL_SYOHIN_KB_MAX_LENGTH = 1;
	public static final int EXCEL_TANPIN_KB_MAX_LENGTH = 1;
	public static final int EXCEL_REIGAI_KB_MAX_LENGTH= 1;
	public static final int EXCEL_SYOKAI_KB_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;

	
	private String torikomi_dt = null;
	private String excel_file_syubetsu = null;
	private String uketsuke_no = null;
	private String excel_file_na = null;
	private String siiresaki_cd = null;
	private String upload_comment_tx = null;
	private String toroku_syonin_fg = null;
	private String toroku_syonin_ts = null;
	private String by_no = null;
	private String syonin_comment_tx = null;
	private String excel_syohin_kb = null;
	private String excel_tanpin_kb = null;
	private String excel_reigai_kb = null;
	private String excel_syokai_kb = null;
	private double min_neire_rt = 0.0;
	private double max_neire_rt = 0.0;
	private double max_uritanka_vl = 0.0;

	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID

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
	 * ThemeBeanを１件のみ抽出したい時に使用する
	 */
	public static mst999999_TourokuBYSyoninBean getTourokuBYSyoninBean(DataHolder dataHolder)
	{
		mst999999_TourokuBYSyoninBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst999999_TourokuBYSyoninDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst999999_TourokuBYSyoninBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	public boolean setTorikomiDt(String torikomi_dt)
	{
		this.torikomi_dt = torikomi_dt;
		return true;
	}
	public String getTorikomiDt()
	{
		return cutString(this.torikomi_dt,TORIKOMI_DT_MAX_LENGTH);
	}
	public String getTorikomiDtString()
	{
		return "'" + cutString(this.torikomi_dt,TORIKOMI_DT_MAX_LENGTH) + "'";
	}
	public String getTorikomiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torikomi_dt,TORIKOMI_DT_MAX_LENGTH));
	}

	public boolean setExcelFileSyubetsu(String excel_file_syubetsu)
	{
		this.excel_file_syubetsu = excel_file_syubetsu;
		return true;
	}
	public String getExcelFileSyubetsu()
	{
		return cutString(this.excel_file_syubetsu,EXCEL_FILE_SYUBETSU_MAX_LENGTH);
	}
	public String getExcelFileSyubetsuString()
	{
		return "'" + cutString(this.excel_file_syubetsu,EXCEL_FILE_SYUBETSU_MAX_LENGTH) + "'";
	}
	public String getExcelFileSyubetsuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_file_syubetsu,EXCEL_FILE_SYUBETSU_MAX_LENGTH));
	}

	public boolean setUketsukeNo(String uketsuke_no)
	{
		this.uketsuke_no = uketsuke_no;
		return true;
	}
	public String getUketsukeNo()
	{
		return cutString(this.uketsuke_no,UKETSUKE_NO_MAX_LENGTH);
	}
	public String getUketsukeNoString()
	{
		return "'" + cutString(this.uketsuke_no,UKETSUKE_NO_MAX_LENGTH) + "'";
	}
	public String getUketsukeNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uketsuke_no,UKETSUKE_NO_MAX_LENGTH));
	}

	public boolean setExcelFileNa(String excel_file_na)
	{
		this.excel_file_na = excel_file_na;
		return true;
	}
	public String getExcelFileNa()
	{
		return cutString(this.excel_file_na,EXCEL_FILE_NA_MAX_LENGTH);
	}
	public String getExcelFileNaString()
	{
		return "'" + cutString(this.excel_file_na,EXCEL_FILE_NA_MAX_LENGTH) + "'";
	}
	public String getExcelFileNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_file_na,EXCEL_FILE_NA_MAX_LENGTH));
	}

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
	public String getSiiresakiCdHTMLSting()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}

	public boolean setUploadCommentTx(String upload_comment_tx)
	{
		this.upload_comment_tx = upload_comment_tx;
		return true;
	}
	public String getUploadCommentTx()
	{
		return cutString(this.upload_comment_tx,UPLOAD_COMMENT_TX_MAX_LENGTH);
	}
	public String getUploadCommentTxString()
	{
		return "'" + cutString(this.upload_comment_tx,UPLOAD_COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getUploadCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.upload_comment_tx,UPLOAD_COMMENT_TX_MAX_LENGTH));
	}

	public boolean setTorokuSyoninFg(String toroku_syonin_fg)
	{
		this.toroku_syonin_fg = toroku_syonin_fg;
		return true;
	}
	public String getTorokuSyoninFg()
	{
		return cutString(this.toroku_syonin_fg,TOROKU_SYONIN_FG_MAX_LENGTH);
	}
	public String getTorokuSyoninFgString()
	{
		return "'" + cutString(this.toroku_syonin_fg,TOROKU_SYONIN_FG_MAX_LENGTH) + "'";
	}
	public String getTorokuSyoninFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.toroku_syonin_fg,TOROKU_SYONIN_FG_MAX_LENGTH));
	}
	
	public boolean setTorokuSyoninTs(String toroku_syonin_ts)
	{
		this.toroku_syonin_ts = toroku_syonin_ts;
		return true;
	}
	public String getTorokuSyoninTs()
	{
		return cutString(this.toroku_syonin_ts,TOROKU_SYONIN_TS_MAX_LENGTH);
	}
	public String getTorokuSyoninTsString()
	{
		return "'" + cutString(this.toroku_syonin_ts,TOROKU_SYONIN_TS_MAX_LENGTH) + "'";
	}
	public String getTorokuSyoninTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.toroku_syonin_ts,TOROKU_SYONIN_TS_MAX_LENGTH));
	}

	public boolean setByNo(String by_no)
	{
		this.by_no = by_no;
		return true;
	}
	public String getByNo()
	{
		return cutString(this.by_no,BY_NO_MAX_LENGTH);
	}
	public String getByNoString()
	{
		return "'" + cutString(this.by_no,BY_NO_MAX_LENGTH) + "'";
	}
	public String getByNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.by_no,BY_NO_MAX_LENGTH));
	}

	public boolean setSyoninCommentTx(String syonin_comment_tx)
	{
		this.syonin_comment_tx = syonin_comment_tx;
		return true;
	}
	public String getSyoninCommentTx()
	{
		return cutString(this.syonin_comment_tx,SYONIN_COMMENT_TX_MAX_LENGTH);
	}
	public String getSyoninCommentTxString()
	{
		return "'" + cutString(this.syonin_comment_tx,SYONIN_COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getSyoninCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syonin_comment_tx,SYONIN_COMMENT_TX_MAX_LENGTH));
	}

	public boolean setExcelSyohinKb(String excel_syohin_kb)
	{
		this.excel_syohin_kb = excel_syohin_kb;
		return true;
	}
	public String getExcelSyohinKb()
	{
		return cutString(this.excel_syohin_kb,EXCEL_SYOHIN_KB_MAX_LENGTH);
	}
	public String getExcelSyohinKbString()
	{
		return "'" + cutString(this.excel_syohin_kb,EXCEL_SYOHIN_KB_MAX_LENGTH) + "'";
	}
	public String getExcelSyohinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_syohin_kb,EXCEL_SYOHIN_KB_MAX_LENGTH));
	}

	public boolean setExcelTanpinKb(String excel_tanpin_kb)
	{
		this.excel_tanpin_kb = excel_tanpin_kb;
		return true;
	}
	public String getExcelTanpinKb()
	{
		return cutString(this.excel_tanpin_kb,EXCEL_TANPIN_KB_MAX_LENGTH);
	}
	public String getExcelTanpinKbString()
	{
		return "'" + cutString(this.excel_tanpin_kb,EXCEL_TANPIN_KB_MAX_LENGTH) + "'";
	}
	public String getExcelTanpinKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_tanpin_kb,EXCEL_TANPIN_KB_MAX_LENGTH));
	}

	public boolean setExcelReigaiKb(String excel_reigai_kb)
	{
		this.excel_reigai_kb = excel_reigai_kb;
		return true;
	}
	public String getExcelReigaiKb()
	{
		return cutString(this.excel_reigai_kb,EXCEL_REIGAI_KB_MAX_LENGTH);
	}
	public String getExcelReigaiKbString()
	{
		return "'" + cutString(this.excel_reigai_kb,EXCEL_REIGAI_KB_MAX_LENGTH) + "'";
	}
	public String getExcelReigaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_reigai_kb,EXCEL_REIGAI_KB_MAX_LENGTH));
	}

	public boolean setExcelSyokaiKb(String excel_syokai_kb)
	{
		this.excel_syokai_kb = excel_syokai_kb;
		return true;
	}
	public String getExcelSyokaiKb()
	{
		return cutString(this.excel_syokai_kb,EXCEL_SYOKAI_KB_MAX_LENGTH);
	}
	public String getExcelSyokaiKbString()
	{
		return "'" + cutString(this.excel_syokai_kb,EXCEL_SYOKAI_KB_MAX_LENGTH) + "'";
	}
	public String getExcelSyokaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.excel_syokai_kb,EXCEL_SYOKAI_KB_MAX_LENGTH));
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


	public boolean setMinNeireRt(String min_neire_rt)
	{
		try
		{
			this.min_neire_rt = Double.parseDouble(min_neire_rt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMinNeireRt(double min_neire_rt)
	{
		this.min_neire_rt = min_neire_rt;
		return true;
	}
	public double getMinNeireRt()
	{
		return this.min_neire_rt;
	}
	public String getMinNeireRtString()
	{
		return Double.toString(this.min_neire_rt);
	}

	
	
	public boolean setMaxNeireRt(String max_neire_rt)
	{
		try
		{
			this.max_neire_rt = Double.parseDouble(max_neire_rt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxNeireRt(double max_neire_rt)
	{
		this.max_neire_rt = max_neire_rt;
		return true;
	}
	public double getMaxNeireRt()
	{
		return this.max_neire_rt;
	}
	public String getMaxNeireRtString()
	{
		return Double.toString(this.max_neire_rt);
	}
	

	public boolean setMaxUritankaVl(String max_uritanka_vl)
	{
		try
		{
			this.max_uritanka_vl = Double.parseDouble(max_uritanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMaxUritankaVl(double max_uritanka_vl)
	{
		this.max_uritanka_vl = max_uritanka_vl;
		return true;
	}
	public double getMaxUritankaVl()
	{
		return this.max_uritanka_vl;
	}
	public String getMaxUritankaVlString()
	{
		return Double.toString(this.max_uritanka_vl);
	}

//2006/05/24 SWC okada add(プロト対応){
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択の長さ
	private String sentaku = null;								//処理選択
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
//2006/05/24 SWC okada add(プロト対応)}


	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return 
		
		   " torikomi_dt = " + getTorikomiDtString()
			 + " excel_file_syubetsu = " + getExcelFileSyubetsuString()
			 + " uketsuke_no = " + getUketsukeNoString()
			 + " excel_file_na = " + getExcelFileNaString()
			 + " siiresaki_cd = " + getSiiresakiCdString()
			 + " upload_comment_tx = " + getUploadCommentTxString()
			 + " toroku_syonin_fg = " + getTorokuSyoninFgString()
			 + " toroku_syonin_ts = " + getTorokuSyoninTsString()
			 + " by_no = " + getByNoString()
			 + " syonin_comment_tx = " + getSyoninCommentTxString()
			 + " excel_syohin_kb = " + getExcelSyohinKbString()
			 + " excel_tanpin_kb = " + getExcelTanpinKbString()
			 + " excel_reigai_kb = " + getExcelReigaiKbString()
			 + " excel_syokai_kb = " + getExcelSyokaiKbString()
			 + " min_neire_rt = " + getMinNeireRtString()
			 + " max_neire_rt = " + getMaxNeireRtString()
			 + " max_uritanka_vl = " + getMaxUritankaVlString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
		
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return ThemeBean コピー後のクラス
	 */
	public mst999999_TourokuBYSyoninBean createClone()
	{
		mst999999_TourokuBYSyoninBean bean = new mst999999_TourokuBYSyoninBean();

		bean.setTorikomiDt(this.torikomi_dt);
		bean.setExcelFileSyubetsu(this.excel_file_syubetsu);
		bean.setUketsukeNo(this.uketsuke_no);
		bean.setExcelFileNa(this.excel_file_na);
		bean.setSiiresakiCd(this.siiresaki_cd);
		bean.setUploadCommentTx(this.upload_comment_tx);
		bean.setTorokuSyoninFg(this.toroku_syonin_fg);
		bean.setTorokuSyoninTs(this.toroku_syonin_ts);
		bean.setByNo(this.by_no);
		bean.setSyoninCommentTx(this.syonin_comment_tx);
		bean.setExcelSyohinKb(this.excel_syohin_kb);
		bean.setExcelTanpinKb(this.excel_tanpin_kb);
		bean.setExcelReigaiKb(this.excel_reigai_kb);
		bean.setExcelSyokaiKb(this.excel_syokai_kb);
		bean.setMinNeireRt(this.min_neire_rt);
		bean.setMaxNeireRt(this.max_neire_rt);
		bean.setMaxUritankaVl(this.max_uritanka_vl);
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
		if( !( o instanceof mst999999_TourokuBYSyoninBean ) ) return false;
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
