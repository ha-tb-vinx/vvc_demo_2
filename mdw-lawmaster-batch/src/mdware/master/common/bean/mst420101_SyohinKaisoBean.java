/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius 
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品階層マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius 
 * @version 1.0(2005/03/24)初版作成
 */
public class mst420101_SyohinKaisoBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
//	  ↓↓2006.06.23  shiyue カスタマイズ追加↓↓	
	public static final int SYSTEM_KB_MAX_LENGTH = 1;//システム区分の長さ
//	  ↑↑2006.06.23  shiyue カスタマイズ追加↑↑	
	public static final int KAISOU_PATTERN_KB_MAX_LENGTH = 1;//階層パターンの長さ
	public static final int CODE1_CD_MAX_LENGTH = 4;//コード１の長さ
	public static final int CODE1_RN_MAX_LENGTH = 20;//コード１名称の長さ
	public static final int CODE2_CD_MAX_LENGTH = 4;//コード２の長さ
	public static final int CODE2_RN_MAX_LENGTH = 20;//コード２の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String yuko_dt = null;	//有効日
//	  ↓↓2006.06.23  shiyue カスタマイズ追加↓↓	
	private String system_kb = null;	//システム区分
//	  ↑↑2006.06.23  shiyue カスタマイズ追加↑↑
	private String kaisou_pattern_kb = null;	//階層パターン	
	private String code1_cd = null;	//コード１
	private String code1_rn = null;	//コード１名称
	private String code2_cd = null;	//コード２
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	private String delete_yoyaku_fg = null;	//削除予約有無フラグ
	private String gen_yuko_dt = null; //現在有効日
	private String lowCode = null; //下位コード
	private String lowCodeRn = null; //下位コード名称
	private String highCode = null; //上位コード
	private String highCodeRn = null; //上位コード名称


	//画面制御項目
	private String changeDt = null;	//切替日
	private String entryCd = null;	//入力コード
	private String entryRn = null;	//入力名称
	private String sentaku = null;	//画面入力CheckBox
	private Map    ctlColor = null;	//画面コントロール色
	private String disbale = null;	//明細入力可否
	private String[] ctlname = null;	//画面コントロール名

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
	 * RSYOHINKAISOBeanを１件のみ抽出したい時に使用する
	 */
	public static mst420101_SyohinKaisoBean getmst420101_SyohinKaisoBean(DataHolder dataHolder)
	{
		mst420101_SyohinKaisoBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst420101_SyohinKaisoDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst420101_SyohinKaisoBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	// gen_yuko_dtに対するセッターとゲッターの集合
	public boolean setGenYukoDt(String gen_yuko_dt)
	{
		this.gen_yuko_dt = gen_yuko_dt;
		return true;
	}
	public String getGenYukoDt()
	{
		return cutString(this.gen_yuko_dt,YUKO_DT_MAX_LENGTH);
	}	
	public String getGenYukoDtString()
	{
		return "'" + cutString(this.gen_yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getGenYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gen_yuko_dt,YUKO_DT_MAX_LENGTH));
	}

//	  ↓↓2006.06.23  shiyue カスタマイズ追加↓↓	
	// system_kbに対するセッターとゲッターの集合
	public boolean setSystemKb(String system_kb)
	{
		this.system_kb = system_kb;
		return true;
	}
	public String getSystemKb()
	{
		return cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH);
	}	
	public String getSystemKbString()
	{
		return "'" + cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH) + "'";
	}
	public String getSystemKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.system_kb,SYSTEM_KB_MAX_LENGTH));
	}
//	  ↑↑2006.06.23  shiyue カスタマイズ追加↑↑	
	
	// lowCodeに対するセッターとゲッターの集合
	public boolean setLowCode(String lowCode)
	{
		this.lowCode = lowCode;
		return true;
	}
	public String getLowCode()
	{
		return cutString(this.lowCode,CODE1_CD_MAX_LENGTH);
	}
	public String getLowCodeString()
	{
		return "'" + cutString(this.lowCode,CODE1_CD_MAX_LENGTH) + "'";
	}
	public String getLowCodeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.lowCode,CODE1_CD_MAX_LENGTH));
	}

	// lowCodeRnに対するセッターとゲッターの集合
	public boolean setLowCodeRn(String lowCodeRn)
	{
		this.lowCodeRn = lowCodeRn;
		return true;
	}
	public String getLowCodeRn()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START						
		//return cutString(this.lowCodeRn,CODE1_RN_MAX_LENGTH);
		return this.lowCodeRn;
//BUGNO-006 2005.04.22 T.Kikuchi END
	}
	public String getLowCodeRnString()
	{
		
		return "'" + cutString(this.lowCodeRn,CODE1_RN_MAX_LENGTH) + "'";
	}
	public String getLowCodeRnHTMLString()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START		
		//return HTMLStringUtil.convert(cutString(this.lowCodeRn,CODE1_RN_MAX_LENGTH));
		return HTMLStringUtil.convert(this.lowCodeRn);		
//BUGNO-006 2005.04.22 T.Kikuchi START		
	}

	// highCodeに対するセッターとゲッターの集合
	public boolean setHighCode(String highCode)
	{
		this.highCode = highCode;
		return true;
	}
	public String getHighCode()
	{
		return cutString(this.highCode,CODE1_CD_MAX_LENGTH);
	}
	public String getHighCodeString()
	{
		return "'" + cutString(this.highCode,CODE1_CD_MAX_LENGTH) + "'";
	}
	public String getHighCodeHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.highCode,CODE1_CD_MAX_LENGTH));
	}

	// highCodeRnに対するセッターとゲッターの集合
	public boolean setHighCodeRn(String highCodeRn)
	{
		this.highCodeRn = highCodeRn;
		return true;
	}
	public String getHighCodeRn()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START
		//return cutString(this.highCodeRn,CODE2_RN_MAX_LENGTH);
		return this.highCodeRn;		
//BUGNO-006 2005.04.22 T.Kikuchi ENd		
	}
	public String getHighCodeRnString()
	{
		return "'" + cutString(this.highCodeRn,CODE2_RN_MAX_LENGTH) + "'";
	}
	public String getHighCodeRnHTMLString()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START		
		//return HTMLStringUtil.convert(cutString(this.highCodeRn,CODE2_RN_MAX_LENGTH));
		return HTMLStringUtil.convert(this.highCodeRn);
//BUGNO-006 2005.04.22 T.Kikuchi END		
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


	// kaisou_pattern_kbに対するセッターとゲッターの集合
	public boolean setKaisouPatternKb(String kaisou_pattern_kb)
	{
		this.kaisou_pattern_kb = kaisou_pattern_kb;
		return true;
	}
	public String getKaisouPatternKb()
	{
		return cutString(this.kaisou_pattern_kb,KAISOU_PATTERN_KB_MAX_LENGTH);
	}
	public String getKaisouPatternKbString()
	{
		return "'" + cutString(this.kaisou_pattern_kb,KAISOU_PATTERN_KB_MAX_LENGTH) + "'";
	}
	public String getKaisouPatternKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kaisou_pattern_kb,KAISOU_PATTERN_KB_MAX_LENGTH));
	}


	// code1_cdに対するセッターとゲッターの集合
	public boolean setCode1Cd(String code1_cd)
	{
		//this.code1_cd = mst000401_LogicBean.formatZero(code1_cd,4);
		this.code1_cd = code1_cd;
		return true;
	}
	public String getCode1Cd()
	{
		return cutString(this.code1_cd,CODE1_CD_MAX_LENGTH);
	}
	public String getCode1CdString()
	{
		return "'" + cutString(this.code1_cd,CODE1_CD_MAX_LENGTH) + "'";
	}
	public String getCode1CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.code1_cd,CODE1_CD_MAX_LENGTH));
	}

	// code1_rnに対するセッターとゲッターの集合
	public boolean setCode1Rn(String code1_rn)
	{
		this.code1_rn = code1_rn;
		return true;
	}
	public String getCode1Rn()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START		
		//return cutString(this.code1_rn,CODE1_RN_MAX_LENGTH);
		return this.code1_rn;
//BUGNO-006 2005.04.22 T.Kikuchi END		
	}
	public String getCode1RnString()
	{
		return "'" + cutString(this.code1_rn,CODE1_RN_MAX_LENGTH) + "'";
	}
	public String getCode1RnHTMLString()
	{
//BUGNO-006 2005.04.22 T.Kikuchi START		
		//return HTMLStringUtil.convert(cutString(this.code1_rn,CODE1_RN_MAX_LENGTH));
		return HTMLStringUtil.convert(this.code1_rn);		
//BUGNO-006 2005.04.22 T.Kikuchi START		
	}

	// code2_cdに対するセッターとゲッターの集合
	public boolean setCode2Cd(String code2_cd)
	{
		this.code2_cd = code2_cd;
		//this.code2_cd = mst000401_LogicBean.formatZero(code2_cd,4);
		return true;
	}
	public String getCode2Cd()
	{
		return cutString(this.code2_cd,CODE2_CD_MAX_LENGTH);
	}
	public String getCode2CdString()
	{
		return "'" + cutString(this.code2_cd,CODE2_CD_MAX_LENGTH) + "'";
	}
	public String getCode2CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.code2_cd,CODE2_CD_MAX_LENGTH));
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

	// delete_yoyaku_fgに対するセッターとゲッターの集合
	public boolean setDeleteYoyakuFg(String delete_yoyaku_fg)
	{
		this.delete_yoyaku_fg = delete_yoyaku_fg;
		return true;
	}
	public String getDeleteYoyakuFg()
	{
		return cutString(this.delete_yoyaku_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteYoyakuFgString()
	{
		return "'" + cutString(this.delete_yoyaku_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteYoyakuFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_yoyaku_fg,DELETE_FG_MAX_LENGTH));
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */

	public String toString()
	{
		return "  yuko_dt = " + getYukoDtString()
//	  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓			
			+"system_kb=" + getSystemKbString()
//	  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑			
			+ "  kaisou_pattern_kb = " + getKaisouPatternKbString()
			+ "  code1_cd = " + getCode1CdString()
			+ "  code2_cd = " + getCode2CdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  gen_yuko_dt = " + getGenYukoDtString()
			+ "  lowCode = " + getLowCodeString()
			+ "  lowCodeRn = " + getLowCodeRnString()
			+ "  highCode = " + getHighCodeString()
			+ "  highCodeRn = " + getHighCodeString()
			+ "  entryCd =  " + getEntryCd()
			+ "  entryRn = " + getEntryRn() 
			+ "  ctlColor = " + getCtlColor()
			+ "  disbale = " + getDisbale()
			+ "  ctlname = " + getCtlname().toString()
			+ " createDatabase  = " + createDatabase;
	}

	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst420101_SyohinKaisoBean コピー後のクラス
	 */
	
	public mst420101_SyohinKaisoBean createClone()
	{
		mst420101_SyohinKaisoBean bean = new mst420101_SyohinKaisoBean();
		bean.setYukoDt(this.yuko_dt);
//		  ↓↓2006.06.23  shiyue カスタマイズ修正↓↓		
		bean.setSystemKb(this.system_kb);		
//		  ↑↑2006.06.23  shiyue カスタマイズ修正↑↑		
		bean.setKaisouPatternKb(this.kaisou_pattern_kb);
		bean.setCode1Cd(this.code1_cd);
		bean.setCode2Cd(this.code2_cd);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setGenYukoDt(this.gen_yuko_dt);
		bean.setLowCode(this.lowCode);
		bean.setLowCodeRn(this.lowCodeRn);
		bean.setHighCode(this.highCode);
		bean.setHighCodeRn(this.highCodeRn);
		bean.setEntryCd(this.entryCd);
		bean.setEntryRn(this.entryRn);
		bean.setCtlColor(this.ctlColor);
		bean.setDisbale(this.disbale);
		bean.setCtlname(this.ctlname);
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
		if( !( o instanceof mst420101_SyohinKaisoBean ) ) return false;
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
	/**
	 * @return
	 */
	public Map getCtlColor() {
		return ctlColor;
	}

	/**
	 * @return
	 */
	public String[] getCtlname() {
		return ctlname;
	}

	/**
	 * @return
	 */
	public String getDisbale() {
		return disbale;
	}

	/**
	 * @param map
	 */
	public void setCtlColor(Map map) {
		ctlColor = map;
	}

	/**
	 * @param strings
	 */
	public void setCtlname(String[] strings) {
		ctlname = strings;
	}

	/**
	 * @param string
	 */
	public void setDisbale(String string) {
		disbale = string;
	}

	/**
	 * @return
	 */
	public String getSentaku() {
		return sentaku;
	}

	/**
	 * @param string
	 */
	public void setSentaku(String string) {
		sentaku = string;
	}

	/**
	 * @return
	 */
	public String getChangeDt() {
		return changeDt;
	}

	/**
	 * @return
	 */
	public String getEntryCd() {
		return entryCd;
	}

	/**
	 * @return
	 */
	public String getEntryRn() {
		return entryRn;
	}

	/**
	 * @param string
	 */
	public void setChangeDt(String string) {
		changeDt = string;
	}

	/**
	 * @param string
	 */
	public void setEntryCd(String string) {
		entryCd = string;
	}

	/**
	 * @param string
	 */
	public void setEntryRn(String string) {
		entryRn = string;
	}

}
