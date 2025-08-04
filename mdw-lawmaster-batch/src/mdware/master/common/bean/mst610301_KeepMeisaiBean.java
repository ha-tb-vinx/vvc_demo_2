package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2005/4/13 13:28:33)
 */
public class mst610301_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SELECT_FG_MAX_LENGTH = 1;
	public static final int KANRI_KB_MAX_LENGTH = 1;
//  ↓↓2006.07.11 magp カスタマイズ修正↓↓	
//	public static final int KANRI_CD_MAX_LENGTH = 4;
//	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;
//	public static final int S_GYOSYU_NA_MAX_LENGTH = 20;
//	public static final int HANKU_CD_MAX_LENGTH = 4;
//	public static final int HANKU_NA_MAX_LENGTH = 20;
//	public static final int HINSYU_CD_MAX_LENGTH = 4;
//	public static final int HINSYU_NA_MAX_LENGTH = 20;
	public static final int UNIT_CD_MAX_LENGTH = 4;
	public static final int UNIT_NA_MAX_LENGTH = 20;
//	↑↑2006.07.11 magp カスタマイズ修正↑↑	
	public static final int TENPO_CD_MAX_LENGTH = 6;
	public static final int TENPO_NA_MAX_LENGTH = 20;
	public static final int SYOHIN_ADDTIME_FG_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 20;
//  ↓↓2006.07.11 magp カスタマイズ修正↓↓	
//	public static final int INSERT_USER_ID_MAX_LENGTH = 7;
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;
//	↑↑2006.07.11 magp カスタマイズ修正↑↑	
	public static final int INSERT_USER_NA_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
//  ↓↓2006.07.11 magp カスタマイズ修正↓↓	
//	public static final int UPDATE_USER_ID_MAX_LENGTH = 7;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;
//	↑↑2006.07.11 magp カスタマイズ修正↑↑	
	public static final int UPDATE_USER_NA_MAX_LENGTH = 20;
	public static final int DELETE_FG_MAX_LENGTH = 1;

	private String select_fg = "";
	private String kanri_kb = "";
//  ↓↓2006.07.11 magp カスタマイズ修正↓↓	
//	private String kanri_cd = "";
//	private String s_gyosyu_cd = "";
//	private String s_gyosyu_na = "";
//	private String hanku_cd = "";
//	private String hanku_na = "";
//	private String hinsyu_cd = "";
//	private String hinsyu_na = "";
	private String unit_cd = "";
	private String unit_na = "";
//	↑↑2006.07.11 magp カスタマイズ修正↑↑	
	private String tenpo_cd = "";
	private String tenpo_na = "";
	private String syohin_addtime_fg = "";
	private String syohin_addtime_fg_csv = "";
	private String insert_ts = "";
	private String insert_user_id = "";
	private String insert_user_na = "";
	private String update_ts = "";
	private String update_user_id = "";
	private String update_user_na = "";
	private String delete_fg = "";
	private String system_kb = "";

	private ArrayList Meisai	= new ArrayList();	// 一覧の明細
	private String CurrentPageNo	= "";				// 現在表示ページ
	private String LastPageNo		= "";				// 最終ページ
	private String MaxRows			= "";				// 最大行数
	private String EndRowInPage	= "";				// 現在ページの終了行
	private String StartRowInPage	= "";				// 現在ページの開始行

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
	 * mst610301_KeepMeisaiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst610301_KeepMeisaiBean getMst610101TanTenSeigyoBean(DataHolder dataHolder)
	{
		mst610301_KeepMeisaiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst610101_TanTenSeigyoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst610301_KeepMeisaiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(ArrayList Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public ArrayList getMeisai() {
		return this.Meisai;
	}

	// CurrentPageNoに対するセッターとゲッターの集合
	public boolean setCurrentPageNo(String CurrentPageNo) {
		this.CurrentPageNo = CurrentPageNo;
		return true;
	}
	
	public String getCurrentPageNo() {
		return this.CurrentPageNo;
	}
	
	// LastPageNoに対するセッターとゲッターの集合
	public boolean setLastPageNo(String LastPageNo) {
		this.LastPageNo = LastPageNo;
		return true;
	}
	
	public String getLastPageNo() {
		return this.LastPageNo;
	}
	
	// MaxRowsに対するセッターとゲッターの集合
	public boolean setMaxRows(String MaxRows) {
		this.MaxRows = MaxRows;
		return true;
	}
	
	public String getMaxRows() {
		return this.MaxRows;
	}
	
	// EndRowInPageに対するセッターとゲッターの集合
	public boolean setEndRowInPage(String EndRowInPage) {
		this.EndRowInPage = EndRowInPage;
		return true;
	}
	
	public String getEndRowInPage() {
		return this.EndRowInPage;
	}

	// StartRowInPageに対するセッターとゲッターの集合
	public boolean setStartRowInPage(String StartRowInPage) {
		this.StartRowInPage = StartRowInPage;
		return true;
	}
	
	public String getStartRowInPage() {
		return this.StartRowInPage;
	}


	// select_fgに対するセッターとゲッターの集合
	public boolean setSelectFg(String select_fg)
	{
		this.select_fg = select_fg;
		return true;
	}
	public String getSelectFg()
	{
		return cutString(this.select_fg,SELECT_FG_MAX_LENGTH);
	}
	public String getSelectFgString()
	{
		return "'" + cutString(this.select_fg,SELECT_FG_MAX_LENGTH) + "'";
	}
	public String getSelectFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.select_fg,SELECT_FG_MAX_LENGTH));
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

//  ↓↓2006.07.11 magp カスタマイズ修正↓↓
//	// kanri_cdに対するセッターとゲッターの集合
//	public boolean setKanriCd(String kanri_cd)
//	{
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd()
//	{
//		return cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH);
//	}
//	public String getKanriCdString()
//	{
//		return "'" + cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH) + "'";
//	}
//	public String getKanriCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_cd,KANRI_CD_MAX_LENGTH));
//	}
//
//	// s_gyosyu_cdに対するセッターとゲッターの集合
//	public boolean setSGyosyuCd(String s_gyosyu_cd)
//	{
//		this.s_gyosyu_cd = s_gyosyu_cd;
//		return true;
//	}
//	public String getSGyosyuCd()
//	{
//		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
//	}
//	public String getSGyosyuCdString()
//	{
//		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getSGyosyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
//	}
//
//
//	// s_gyosyu_naに対するセッターとゲッターの集合
//	public boolean setSGyosyuNa(String s_gyosyu_na)
//	{
//		this.s_gyosyu_na = s_gyosyu_na;
//		return true;
//	}
//	public String getSGyosyuNa()
//	{
//		return cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH);
//	}
//	public String getSGyosyuNaString()
//	{
//		return "'" + cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH) + "'";
//	}
//	public String getSGyosyuNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.s_gyosyu_na,S_GYOSYU_NA_MAX_LENGTH));
//	}
//
//
//	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//
//
//	// hanku_naに対するセッターとゲッターの集合
//	public boolean setHankuNa(String hanku_na)
//	{
//		this.hanku_na = hanku_na;
//		return true;
//	}
//	public String getHankuNa()
//	{
//		return cutString(this.hanku_na,HANKU_NA_MAX_LENGTH);
//	}
//	public String getHankuNaString()
//	{
//		return "'" + cutString(this.hanku_na,HANKU_NA_MAX_LENGTH) + "'";
//	}
//	public String getHankuNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_na,HANKU_NA_MAX_LENGTH));
//	}
//
//
//	// hinsyu_cdに対するセッターとゲッターの集合
//	public boolean setHinsyuCd(String hinsyu_cd)
//	{
//		this.hinsyu_cd = hinsyu_cd;
//		return true;
//	}
//	public String getHinsyuCd()
//	{
//		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
//	}
//	public String getHinsyuCdString()
//	{
//		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHinsyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
//	}
//
//
//	// hinsyu_naに対するセッターとゲッターの集合
//	public boolean setHinsyuNa(String hinsyu_na)
//	{
//		this.hinsyu_na = hinsyu_na;
//		return true;
//	}
//	public String getHinsyuNa()
//	{
//		return cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH);
//	}
//	public String getHinsyuNaString()
//	{
//		return "'" + cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH) + "'";
//	}
//	public String getHinsyuNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH));
//	}
	
	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}

	// unit_naに対するセッターとゲッターの集合
	public boolean setUnitNa(String unit_na)
	{
		this.unit_na = unit_na;
		return true;
	}
	public String getUnitNa()
	{
		return cutString(this.unit_na,UNIT_NA_MAX_LENGTH);
	}
	public String getUnitNaString()
	{
		return "'" + cutString(this.unit_na,UNIT_NA_MAX_LENGTH) + "'";
	}
	public String getUnitNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_na,UNIT_NA_MAX_LENGTH));
	}
	
//	↑↑2006.07.11 magp カスタマイズ修正↑↑	
	
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

	// syohin_addtime_fgに対するセッターとゲッターの集合
	public boolean setSyohinAddtimeFg(String syohin_addtime_fg)
	{
		this.syohin_addtime_fg = syohin_addtime_fg;
		return true;
	}
	public String getSyohinAddtimeFg()
	{
		return cutString(this.syohin_addtime_fg,SYOHIN_ADDTIME_FG_MAX_LENGTH);
	}
	public String getSyohinAddtimeFgString()
	{
		return "'" + cutString(this.syohin_addtime_fg,SYOHIN_ADDTIME_FG_MAX_LENGTH) + "'";
	}
	public String getSyohinAddtimeFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_addtime_fg,SYOHIN_ADDTIME_FG_MAX_LENGTH));
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

	// insert_user_naに対するセッターとゲッターの集合
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.insert_user_na = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,INSERT_USER_NA_MAX_LENGTH));
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

	// update_user_naに対するセッターとゲッターの集合
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_na = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
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
		return "  kanri_kb = " + getKanriKbString()
//  ↓↓2006.07.11 magp カスタマイズ修正↓↓		
//			+ "  kanri_cd = " + getKanriCdString()
//			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
//			+ "  s_gyosyu_na = " + getSGyosyuNaString()
//			+ "  hanku_cd = " + getHankuCdString()
//			+ "  hanku_na = " + getHankuNaString()
//			+ "  hinsyu_cd = " + getHinsyuCdString()
//			+ "  hinsyu_na = " + getHinsyuNaString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_na = " + getUnitNaString()
//	↑↑2006.07.11 magp カスタマイズ修正↑↑			
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_na = " + getTenpoNaString()
			+ "  syohin_addtime_fg = " + getSyohinAddtimeFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_na = " + getInsertUserNaString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_na = " + getUpdateUserNaString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst610301_KeepMeisaiBean コピー後のクラス
	 */
	public mst610301_KeepMeisaiBean createClone()
	{
		mst610301_KeepMeisaiBean bean = new mst610301_KeepMeisaiBean();
		bean.setKanriKb(this.kanri_kb);
//		  ↓↓2006.07.11 magp カスタマイズ修正↓↓		
//		bean.setKanriCd(this.kanri_cd);
//		bean.setSGyosyuCd(this.s_gyosyu_cd);
//		bean.setSGyosyuNa(this.s_gyosyu_na);
//		bean.setHankuCd(this.hanku_cd);
//		bean.setHankuNa(this.hanku_na);
//		bean.setHinsyuCd(this.hinsyu_cd);
//		bean.setHinsyuNa(this.hinsyu_na);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNa(this.unit_na);
//	  ↑↑2006.07.11 magp カスタマイズ修正↑↑		
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNa(this.tenpo_na);
		bean.setSyohinAddtimeFg(this.syohin_addtime_fg);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserNa(this.insert_user_na);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserNa(this.update_user_na);
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
		if( !( o instanceof mst610301_KeepMeisaiBean ) ) return false;
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
	public String getSystemkb() {
		return system_kb;
	}
	public void setSystemkb(String system_kb) {
		this.system_kb = system_kb;
	}
	public String getSyohin_addtime_fg_csv() {
		return syohin_addtime_fg_csv;
	}
	public void setSyohin_addtime_fg_csv(String syohin_addtime_fg_csv) {
		this.syohin_addtime_fg_csv = syohin_addtime_fg_csv;
	}
}
