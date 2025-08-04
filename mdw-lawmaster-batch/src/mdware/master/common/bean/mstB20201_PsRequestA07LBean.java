package mdware.master.common.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）プライスシール発行リクエスト(実用)のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するプライスシール発行リクエスト(実用)のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author fanglh
 * @version 1.0(2005/03/20)初版作成
 */
public class mstB20201_PsRequestA07LBean {
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SEND_DT_MAX_LENGTH = 8;
	public static final int TENPO_CD_MAX_LENGTH = 6;
//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
//	public static final int TENPO_NM_MAX_LENGTH = 6;	
	public static final int TENPO_NM_MAX_LENGTH = 20;	
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
	public static final int UNIT_CD_MAX_LENGTH = 4;
	public static final int UNIT_NM_MAX_LENGTH = 20;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;
	public static final int SYOHIN_NA_MAX_LENGTH = 80;
	public static final int OLD_SYOHIN_NA_MAX_LENGTH = 46;
	public static final int ENTRY_KB_MAX_LENGTH = 1;
	public static final int ENTRY_KINO_KB_MAX_LENGTH = 1;
	public static final int UPDATE_TS_MAX_LENGTH = 20;			
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		
	public static final int INSERT_TS_MAX_LENGTH = 20;// の長さ
	public static final int INSERT_USE_ID_MAX_LENGTH = 10;// の長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;	
	public static final int BUMON_CD_MAX_LENGTH = 4;			
	public static final int BY_NO_MAX_LENGTH = 20;
	public static final int SEND_END_KB_MAX_LENGTH = 1;
	public static final int PC_KB_MAX_LENGTH = 1;
//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	public static final int HINBAN_CD_MAX_LENGTH = 4;
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
	
	private String pc_kb = null;
	private String bumon_cd = null;
	private String send_dt = null;
	private String tenpo_cd = null;
	private String tenpo_nm = null;
	private String unit_cd = null;
	private String unit_nm = null;
	private String syohin_cd = null;
	private String syohin_na = null;
	private long ps_maisu_qt = 0;
	private long old_ps_maisu_qt = 0;
//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	private String hinban_cd = null;
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
	private String entry_kb = null;
	private String entry_kino_kb = null;
	private String sentaku = "0";
	private String disbale = null;
	private String update_ts = null;	// 更新年月日
	private String update_user_id = null;	// 更新者ID
	private String insert_ts = null;	// 
	private String insert_user_id = null;	// 
	private String delete_fg = null;	// 削除フラグ
	private String by_no = null;	// 更新者ID
	private String send_end_kb = null;	// 削除フラグ
	private String old_syohin_na = null;
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）

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
	 * TestBeanを１件のみ抽出したい時に使用する
	 */
	public static mstB20201_PsRequestA07LBean getTestBean(DataHolder dataHolder)
	{
		mstB20201_PsRequestA07LBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstB20101_PsRequestA07LDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstB20201_PsRequestA07LBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	// insert_tslに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_tsl)
	{
		this.insert_ts = insert_tsl;
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


	// insert_user_idlに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USE_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USE_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USE_ID_MAX_LENGTH));
	}

	
	// pc_kbに対するセッターとゲッターの集合
	public boolean setPcKb(String pc_kb)
	{
		this.pc_kb = pc_kb;
		return true;
	}
	public String getPcKb()
	{
		return cutString(this.pc_kb,PC_KB_MAX_LENGTH);
	}
	public String getPcKbString()
	{
		return "'" + cutString(this.pc_kb,PC_KB_MAX_LENGTH) + "'";
	}
	public String getPcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_kb,PC_KB_MAX_LENGTH));
	}

	// syohin_naに対するセッターとゲッターの集合
	public boolean setOldSyohinNa(String old_syohin_na)
	{
		this.old_syohin_na = old_syohin_na;
		return true;
	}
	public String getOldSyohinNa()
	{
		return cutString(this.old_syohin_na,OLD_SYOHIN_NA_MAX_LENGTH);
	}
	public String getOldSyohinNaString()
	{
		return "'" + cutString(this.old_syohin_na,OLD_SYOHIN_NA_MAX_LENGTH) + "'";
	}
	public String getOldSyohinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.old_syohin_na,OLD_SYOHIN_NA_MAX_LENGTH));
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
	
	// by_noに対するセッターとゲッターの集合
	public boolean setSengEndKb(String send_end_kb)
	{
		this.send_end_kb = send_end_kb;
		return true;
	}
	public String getSengEndKb()
	{
		return cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH);
	}
	public String getSengEndKbString()
	{
		return "'" + cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH) + "'";
	}
	public String getSengEndKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH));
	}

	// by_noに対するセッターとゲッターの集合
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
	
	// entry_kino_kbに対するセッターとゲッターの集合
	public boolean setEntryKinoKb(String entry_kino_kb)
	{
		this.entry_kino_kb = entry_kino_kb;
		return true;
	}
	public String getEntryKinoKb()
	{
		return cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH);
	}
	public String getEntryKinoKbString()
	{
		return "'" + cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH) + "'";
	}
	public String getEntryKinoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH));
	}


	// entry_kbに対するセッターとゲッターの集合
	public boolean setEntryKb(String entry_kb)
	{
		this.entry_kb = entry_kb;
		return true;
	}
	public String getEntryKb()
	{
		return cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH);
	}
	public String getEntryKbString()
	{
		return "'" + cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH) + "'";
	}
	public String getEntryKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH));
	}

	// send_dtに対するセッターとゲッターの集合
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	public String getSendDt()
	{
		return cutString(this.send_dt,SEND_DT_MAX_LENGTH);
	}
	public String getSendDtString()
	{
		return "'" + cutString(this.send_dt,SEND_DT_MAX_LENGTH) + "'";
	}
	public String getSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_dt,SEND_DT_MAX_LENGTH));
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
	
	// tenpo_nmに対するセッターとゲッターの集合
	public boolean setTenpoNm(String tenpo_nm)
	{
		this.tenpo_nm = tenpo_nm;
		return true;
	}
	public String getTenpoNm()
	{
		return cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH);
	}
	public String getTenpoNmString()
	{
		return "'" + cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH) + "'";
	}
	public String getTenpoNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH));
	}


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

//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	// hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getHinbanCdString()
	{
		return "'" + cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH));
	}
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑

	// unit_nmに対するセッターとゲッターの集合
	public boolean setUnitNm(String unit_nm)
	{
		this.unit_nm = unit_nm;
		return true;
	}
	public String getUnitNm()
	{
		return cutString(this.unit_nm,UNIT_NM_MAX_LENGTH);
	}
	public String getUnitNmString()
	{
		return "'" + cutString(this.unit_nm,UNIT_NM_MAX_LENGTH) + "'";
	}
	public String getUnitNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_nm,UNIT_NM_MAX_LENGTH));
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

	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(Map);<br>
	 * <br>
	 * 
	 * @param コントロールカラーMap
	 */
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}

	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return コントロールカラーMap
	 */
	public Map getCtrlColor() {
		return this.CtrlColor;
	}		

	// ps_maisu_qtに対するセッターとゲッターの集合
	public boolean setPsMaisuQt(String ps_maisu_qt)
	{
		try
		{
			this.ps_maisu_qt = Long.parseLong(ps_maisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPsMaisuQt(long ps_maisu_qt)
	{
		this.ps_maisu_qt = ps_maisu_qt;
		return true;
	}
	public double getPsMaisuQt()
	{
		return this.ps_maisu_qt;
	}
	public String getPsMaisuQtString()
	{
		return Long.toString(this.ps_maisu_qt);
	}

	// ps_maisu_qtに対するセッターとゲッターの集合
	public boolean setOldPsMaisuQt(String old_ps_maisu_qt)
	{
		try
		{
			this.old_ps_maisu_qt = Long.parseLong(old_ps_maisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setOldPsMaisuQt(long old_ps_maisu_qt)
	{
		this.old_ps_maisu_qt = old_ps_maisu_qt;
		return true;
	}
	public double getOldPsOldMaisuQt()
	{
		return this.old_ps_maisu_qt;
	}
	public String getOldPsMaisuQtString()
	{
		return Long.toString(this.old_ps_maisu_qt);
	}
	public String getSentaku() {
		return sentaku;
	}
	public void setSentaku(String sentaku) {
		this.sentaku = sentaku;
	}
	public String getDisbale() {
		return disbale;
	}
	public void setDisbale(String disbale) {
		this.disbale = disbale;
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  send_dt = " + getSendDtString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  bumon_cd = " + getBumonCdString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_nm = " + getUnitNmString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  syohin_na = " + getSyohinNaString()
			+ "  ps_maisu_qt = " + getPsMaisuQtString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  entry_kino_kb = " + getEntryKinoKbString()
			+ "  entry_kb = " + getEntryKbString()
			+ "  by_no = " + getByNoString()
			+ "  send_end_kb = " + getSengEndKbString()
			+ "  old_syohin_na = " + getOldSyohinNaString()
			+ "  old_ps_maisu_qt = " + getOldPsMaisuQtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。old_ps_maisu_qt
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mstB20201_PsRequestA07LBean コピー後のクラス
	 */
	public mstB20201_PsRequestA07LBean createClone()
	{
		mstB20201_PsRequestA07LBean bean = new mstB20201_PsRequestA07LBean();
		bean.setSendDt(this.send_dt);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNm(this.unit_nm);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setPsMaisuQt(this.ps_maisu_qt);
		bean.setBumonCd(this.bumon_cd);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setUpdateTs(this.insert_ts);
		bean.setUpdateUserId(this.insert_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setEntryKinoKb(this.entry_kino_kb);
		bean.setEntryKb(this.entry_kb);
		bean.setByNo(this.by_no);
		bean.setSengEndKb(this.send_end_kb);
		bean.setOldSyohinNa(this.old_syohin_na);
		bean.setOldPsMaisuQt(this.old_ps_maisu_qt);
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		bean.setHinbanCd(this.hinban_cd);
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
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
		if( !( o instanceof mstB20201_PsRequestA07LBean ) ) return false;
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
