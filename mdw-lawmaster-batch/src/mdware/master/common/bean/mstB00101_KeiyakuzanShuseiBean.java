/**
 * <p>タイトル: 新ＭＤシステム契約残確認・修正画面のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/12)初版作成
 */
package mdware.master.common.bean;

import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム契約残確認・修正画面のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する契約残確認・修正画面のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sunpt
 * @version 1.0(2006/07/12)初版作成
 */
public class mstB00101_KeiyakuzanShuseiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;//漢字品名の長さ
	public static final int TENPO_CDL_MAX_LENGTH = 1024;//店舗コード配列の長さ
	public static final int TKANJI_RNL_MAX_LENGTH = 1024;//店舗略称配列の長さ
	public static final int FLGL_MAX_LENGTH = 1024;//店舗存在フラグ配列の長さ
	public static final int INSERT_TSL_MAX_LENGTH = 1024;//の長さ
	public static final int INSERT_USER_IDL_MAX_LENGTH = 1024;//の長さ
	public static final int UPDATE_TSL_MAX_LENGTH = 1024;//更新日時配列の長さ
	public static final int UPDATE_USER_IDL_MAX_LENGTH = 1024;//の長さ
	public static final int SENTAKU_MAX_LENGTH = 1;//処理選択
	public static final int OLDFLGL_MAX_LENGTH = 1024;//店舗存在フラグ配列の長さ
	
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int SIIREHINBAN_CD_MAX_LENGTH = 15;//仕入先商品コードの長さ
	public static final int SIIRESAKI_CD_MAX_LENGTH = 9;//仕入先コードの長さ
	public static final int GENTANKA_VL_MAX_LENGTH = 9;//原価単価の長さ
	public static final int BAITANKA_VL_MAX_LENGTH = 7;//売価単価の長さ
	public static final int HANBAI_ST_DT_MAX_LENGTH = 8;//販売開始日の長さ
	public static final int HANBAI_ED_DT_MAX_LENGTH = 8;//販売終了日の長さ
	public static final int HACHU_ST_DT_MAX_LENGTH = 8;//発注開始日の長さ
	public static final int HANCH_ED_DT_MAX_LENGTH = 8;//発注終了日の長さ
//	↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
//	public static final int SIZE_NM_MAX_LENGTH = 80;//サイズ名の長さ
//	public static final int COLOR_NM_MAX_LENGTH = 80;//カラー名の長さ
	public static final int SIZE_NM_MAX_LENGTH = 10;//サイズ名の長さ
	public static final int COLOR_NM_MAX_LENGTH = 10;//カラー名の長さ
	public static final int NEFUDA_UKEWATASI_KB_MAX_LENGTH = 1;//値札受方法の長さ
	public static final int NEFUDA_UKEWATASI_NM_MAX_LENGTH = 80;//値札受方法名の長さ
//	↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
	public static final int SIIRESAKI_NM_MAX_LENGTH = 80;//仕入先名の長さ
//	↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
	public static final int SYSTEM_KB_MAX_LENGTH = 1;//システム区分の長さ
//	↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑

	//契約数管理マスタのカラム
	private String bumon_cd = null;	//部門コード
	private String syohin_cd = null;	//商品マスタ.商品コード
	private String syokaikeiyaku_qt = null;//初回契約数量
	private String ruikeikeiyaku_qt = null;	//累計契約数量
	private String ruikeihachu_qt = null;	//累計発注数量
	private String zensyuhachu_qt = null;	//前週発注数量
	private String ruikeihanbai_qt = null;	//累計販売数量
	private String zensyuhanbai_qt = null;	//前週販売数量
	private String ruikeituikeiyaku_qt = null;	//累計追加契約数量
	private String konkaituikeiyaku_qt = null;	//今回追加契約数量
	private String zenkaituikeiyaku_qt = null;	//前回追加契約数量
	private String mino_qt = null;	//未納数量
	private String tino_qt = null;	//遅納数量
	private String toriatsukaiten_qt = null;	//取扱店舗数
	private String syokaritu_rt = null;	//消化率
	private String syokayotei_nb = null;	//消化予定日数
	private String delete_flg = null;	//削除フラグ
	private String insert_ts = null;	//作成年月日
	private String insert_userid = null;	//作成者ID
	private String update_ts = null;	//更新年月日
	private String update_userid = null;	//更新者ID
//	↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
	private String system_kb = null;	//システム区分
//	↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑

	
	//商品マスタのカラム
	private String yuko_dt = null;//有効日
	private String siirehinban_cd = null;//仕入先商品コード
	private String siiresaki_cd = null; //仕入先コード
	private String gentanka_vl = null; //原価単価
	private String baitanka_vl = null; //売価単価
	private String syohin_nm = null; //漢字品名
	private String hanbai_start_dt = null; //販売開始日
	private String hanbai_end_dt = null; //販売終了日
	private String save_hanbai_end_dt = null; //販売終了日退避 by kema 06.09.19
	private String hachu_start_dt = null; //発注開始日
	private String hachu_end_dt = null; //発注終了日
	private String save_hachu_end_dt = null; //発注終了日 退避 by kema 06.09.19
	private String size_cd = null;//サイズコード
	private String color_cd = null;//カラーコード
//	↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
	private String nefuda_ukewatasi_kb = null;//値札受方法
	private String save_nefuda_ukewatasi_kb = null; //値札受方法退避
	private String nefuda_ukewatasi_nm = null; //値札受方法名称
//	↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
	//名称
	private String siiresaki_nm = null; //仕入先名
	private String size_nm = null;//サイズ名
	private String color_nm = null;//カラー名
	
	private int rowSpanCnt = 0;
	private boolean spaceCell = false;
		
	private String insertTs = null;
	private String updateTs = null;
	private String update_tsl = null;	//更新日時配列
	private String update_user_idl = null;	//
	private String sentaku = null;	//処理選択
	private String oldFlgl = null;	//更新前店舗存在フラグ配列
	private String dbSystemDate = null;
	private String henkoDT = null;
	private String updateUserId = null;
	
	private String originalHanbaiEndDt = null;
	private String originalHachuEndDt = null;
	private String tuiKeiyakusuQt = ""; //追加契約数量

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
	 * mst300101_TanpinTenToriatukaiLBeanを１件のみ抽出したい時に使用する
	 */
	public static mst300101_TanpinTenToriatukaiLBean getmst300101_TanpinTenToriatukaiLBean(DataHolder dataHolder)
	{
		mst300101_TanpinTenToriatukaiLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst300101_TanpinTenToriatukaiLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst300101_TanpinTenToriatukaiLBean )ite.next();
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


	// oldFlglに対するセッターとゲッターの集合
	public boolean setOldFlgl(String oldFlgl)
	{
		this.oldFlgl = oldFlgl;
		return true;
	}
	public String getOldFlgl()
	{
		return cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH);
	}
	public String getOldFlglString()
	{
		return "'" + cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH) + "'";
	}
	public String getOldFlglHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.oldFlgl,OLDFLGL_MAX_LENGTH));
	}

	// update_tslに対するセッターとゲッターの集合
	public boolean setUpdateTsl(String update_tsl)
	{
		this.update_tsl = update_tsl;
		return true;
	}
	public String getUpdateTsl()
	{
		return cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH);
	}
	public String getUpdateTslString()
	{
		return "'" + cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH) + "'";
	}
	public String getUpdateTslHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_tsl,UPDATE_TSL_MAX_LENGTH));
	}


	// update_user_idlに対するセッターとゲッターの集合
	public boolean setUpdateUserIdl(String update_user_idl)
	{
		this.update_user_idl = update_user_idl;
		return true;
	}
	public String getUpdateUserIdl()
	{
		return cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH);
	}
	public String getUpdateUserIdlString()
	{
		return "'" + cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_idl,UPDATE_USER_IDL_MAX_LENGTH));
	}

//	↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
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
//	↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑
	

	/**
	 * 商品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohin_nm() {
		return syohin_nm;
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_nm,HINMEI_KANJI_NA_MAX_LENGTH));
	}

	/**
	 * 商品名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohin_nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohin_nm(String string) {
		syohin_nm = string;
	}

	
	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  bumon_cd = " + getBumonCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			
			+ "  update_tsl = " + getUpdateTslString()
			+ "  update_user_idl = " + getUpdateUserIdlString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst300101_TanpinTenToriatukaiLBean コピー後のクラス
	 */
	public mst300101_TanpinTenToriatukaiLBean createClone()
	{
		mst300101_TanpinTenToriatukaiLBean bean = new mst300101_TanpinTenToriatukaiLBean();
		bean.setBumonCd(this.bumon_cd);
		bean.setSyohinCd(this.syohin_cd);
		
		bean.setUpdateTsl(this.update_tsl);
		bean.setUpdateUserIdl(this.update_user_idl);
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
		if( !( o instanceof mst300101_TanpinTenToriatukaiLBean ) ) return false;
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

	/**
	 * 商品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBaitanka_vl() {
		return baitanka_vl;
	}
	public void setBaitanka_vl(String baitanka_vl) {
		this.baitanka_vl = baitanka_vl;
	}
	
	/**
	 * 削除フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getDelete_flg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getDelete_flg() {
		return delete_flg;
	}
	public void setDelete_flg(String delete_flg) {
		this.delete_flg = delete_flg;
	}
	
	/**
	 * 原価単価に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGentanka_vl();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGentanka_vl() {
		return gentanka_vl;
	}
	public void setGentanka_vl(String gentanka_vl) {
		this.gentanka_vl = gentanka_vl;
	}
	
	/**
	 * 発注終了日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHachu_end_dt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHachu_end_dt() {
		return hachu_end_dt;
	}
	public void setHachu_end_dt(String hachu_end_dt) {
		this.hachu_end_dt = hachu_end_dt;
	}
	public String getHachu_end_dtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_end_dt, HANCH_ED_DT_MAX_LENGTH));
	}
	
	/**
	 * 発注開始日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHachu_start_dt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHachu_start_dt() {
		return hachu_start_dt;
	}
	public void setHachu_start_dt(String hachu_start_dt) {
		this.hachu_start_dt = hachu_start_dt;
	}
	public String getHachu_start_dtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_start_dt, HACHU_ST_DT_MAX_LENGTH));
	}
	
	/**
	 * 発注終了日DB退避分に対するゲッター<br> by kema 06.09.19
	 * getSaveHachu_end_dt();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getSaveHachu_end_dt() {
		return save_hachu_end_dt;
	}
	public void setSaveHachu_end_dt(String save_hachu_end_dt) {
		this.save_hachu_end_dt = save_hachu_end_dt;
	}

	/**
	 * 販売終了日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHanbai_end_dt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHanbai_end_dt() {
		return hanbai_end_dt;
	}
	public void setHanbai_end_dt(String hanbai_end_dt) {
		this.hanbai_end_dt = hanbai_end_dt;
	}
	public String getHanbai_end_dtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_end_dt, HANBAI_ED_DT_MAX_LENGTH));
	}
	
	/**
	 * 販売開始日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHanbai_start_dt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHanbai_start_dt() {
		return hanbai_start_dt;
	}
	public void setHanbai_start_dt(String hanbai_start_dt) {
		this.hanbai_start_dt = hanbai_start_dt;
	}
	public String getHanbai_start_dtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_start_dt, HANBAI_ST_DT_MAX_LENGTH));
	}
	
	/**
	 * 販売終了日DB退避分に対するゲッター<br> by kema 06.09.19
	 * getSaveHanbai_end_dt();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getSaveHanbai_end_dt() {
		return save_hanbai_end_dt;
	}
	public void setSaveHanbai_end_dt(String save_hanbai_end_dt) {
		this.save_hanbai_end_dt = save_hanbai_end_dt;
	}

	/**
	 * 作成年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsert_ts();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getInsert_ts() {
		return insert_ts;
	}
	public void setInsert_ts(String insert_ts) {
		this.insert_ts = insert_ts;
	}
	
	/**
	 * 作成者IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getInsert_userid();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getInsert_userid() {
		return insert_userid;
	}
	public void setInsert_userid(String insert_userid) {
		this.insert_userid = insert_userid;
	}
	
	/**
	 * 今回追加契約数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKonkaituikeiyaku_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getKonkaituikeiyaku_qt() {
		return konkaituikeiyaku_qt;
	}
	public void setKonkaituikeiyaku_qt(String konkaituikeiyaku_qt) {
		this.konkaituikeiyaku_qt = konkaituikeiyaku_qt;
	}
	
	/**
	 * 未納数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMino_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getMino_qt() {
		return mino_qt;
	}
	public void setMino_qt(String mino_qt) {
		this.mino_qt = mino_qt;
	}
	
	/**
	 * 累計発注数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRuikeihachu_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getRuikeihachu_qt() {
		return ruikeihachu_qt;
	}
	public void setRuikeihachu_qt(String ruikeihachu_qt) {
		this.ruikeihachu_qt = ruikeihachu_qt;
	}
	
	/**
	 * 累計販売数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRuikeihanbai_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getRuikeihanbai_qt() {
		return ruikeihanbai_qt;
	}
	public void setRuikeihanbai_qt(String ruikeihanbai_qt) {
		this.ruikeihanbai_qt = ruikeihanbai_qt;
	}
	
	/**
	 * 累計契約数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRuikeikeiyaku_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getRuikeikeiyaku_qt() {
		return ruikeikeiyaku_qt;
	}
	public void setRuikeikeiyaku_qt(String ruikeikeiyaku_qt) {
		this.ruikeikeiyaku_qt = ruikeikeiyaku_qt;
	}
	
	/**
	 * 累計追加契約数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getRuikeituikeiyaku_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getRuikeituikeiyaku_qt() {
		return ruikeituikeiyaku_qt;
	}
	public void setRuikeituikeiyaku_qt(String ruikeituikeiyaku_qt) {
		this.ruikeituikeiyaku_qt = ruikeituikeiyaku_qt;
	}
	
	/**
	 * 仕入先商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiirehinban_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiirehinban_cd() {
		return cutString(this.siirehinban_cd, SIIREHINBAN_CD_MAX_LENGTH);
	}
	public void setSiirehinban_cd(String siirehinban_cd) {
		this.siirehinban_cd = siirehinban_cd;
	}
	public String getSiirehinban_cdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siirehinban_cd, SIIREHINBAN_CD_MAX_LENGTH));
	}
	
	/**
	 * 仕入先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresaki_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresaki_cd() {
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public void setSiiresaki_cd(String siiresaki_cd) {
		this.siiresaki_cd = siiresaki_cd;
	}
	public String getSiiresaki_cdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd, SIIRESAKI_CD_MAX_LENGTH));
	}
	
	/**
	 * 初回契約数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyokaikeiyaku_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyokaikeiyaku_qt() {
		return syokaikeiyaku_qt;
	}
	public void setSyokaikeiyaku_qt(String syokaikeiyaku_qt) {
		this.syokaikeiyaku_qt = syokaikeiyaku_qt;
	}
	
	/**
	 * 消化率に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyokaritu_rt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyokaritu_rt() {
		return syokaritu_rt;
	}
	public void setSyokaritu_rt(String syokaritu_rt) {
		this.syokaritu_rt = syokaritu_rt;
	}
	
	/**
	 * 消化予定日数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyokayotei_nb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyokayotei_nb() {
		return syokayotei_nb;
	}
	public void setSyokayotei_nb(String syokayotei_nb) {
		this.syokayotei_nb = syokayotei_nb;
	}
	
	/**
	 * 遅納数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTino_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTino_qt() {
		return tino_qt;
	}
	public void setTino_qt(String tino_qt) {
		this.tino_qt = tino_qt;
	}
	
	/**
	 * 取扱店舗数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * toriatsukaiten_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getToriatsukaiten_qt() {
		return toriatsukaiten_qt;
	}
	public void setToriatsukaiten_qt(String toriatsukaiten_qt) {
		this.toriatsukaiten_qt = toriatsukaiten_qt;
	}
	
	/**
	 * 更新年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdate_ts();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdate_Ts() {
		return update_ts;
	}
	public void setUpdate_Ts(String update_ts) {
		this.update_ts = update_ts;
	}
	
	/**
	 * 更新者IDに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdate_userid();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdate_userid() {
		return update_userid;
	}
	public void setUpdate_userid(String update_userid) {
		this.update_userid = update_userid;
	}
	
	/**
	 * 前回追加契約数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getZenkaituikeiyaku_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getZenkaituikeiyaku_qt() {
		return zenkaituikeiyaku_qt;
	}
	public void setZenkaituikeiyaku_qt(String zenkaituikeiyaku_qt) {
		this.zenkaituikeiyaku_qt = zenkaituikeiyaku_qt;
	}
	
	/**
	 * 前週発注数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getZensyuhachu_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getZensyuhachu_qt() {
		return zensyuhachu_qt;
	}
	public void setZensyuhachu_qt(String zensyuhachu_qt) {
		this.zensyuhachu_qt = zensyuhachu_qt;
	}
	
	/**
	 * 前週販売数量に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getZensyuhanbai_qt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getZensyuhanbai_qt() {
		return zensyuhanbai_qt;
	}
	public void setZensyuhanbai_qt(String zensyuhanbai_qt) {
		this.zensyuhanbai_qt = zensyuhanbai_qt;
	}
	
	/**
	 * カラーコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getColor_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getColor_cd() {
		return color_cd;
	}
	public void setColor_cd(String color_cd) {
		this.color_cd = color_cd;
	}
	
	/**
	 * サイズコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSize_cd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSize_cd() {
		return size_cd;
	}
	public void setSize_cd(String size_cd) {
		this.size_cd = size_cd;
	}

//	↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
	//値札受渡方法に対するセッターとゲッターの集合
	public String getNefudaUkewatasiKb()
	{
		return cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH);
	}
	public void setNefudaUkewatasiKb(String nefuda_ukewatasi_kb)
	{
		this.nefuda_ukewatasi_kb = nefuda_ukewatasi_kb;
	}
	public String getNefudaUkewatasiKbString()
	{
		return "'" + cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH) + "'";
	}
	public String getNefudaUkewatasiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH));
	}

	//値札受渡方法退避に対するセッターとゲッターの集合
	public String getSaveNefudaUkewatasiKb()
	{
		return cutString(this.save_nefuda_ukewatasi_kb,NEFUDA_UKEWATASI_KB_MAX_LENGTH);
	}
	public void setSaveNefudaUkewatasiKb(String nefuda_ukewatasi_kb)
	{
		this.save_nefuda_ukewatasi_kb = nefuda_ukewatasi_kb;
	}

	//値札受渡方法名称に対するセッターとゲッターの集合
	public String getNefudaUkewatasiNm() {
		return cutString(this.nefuda_ukewatasi_nm,NEFUDA_UKEWATASI_NM_MAX_LENGTH);
	}
	public boolean setNefudaUkewatasiNm(String nefuda_ukewatasi_nm) {
		this.nefuda_ukewatasi_nm = nefuda_ukewatasi_nm;
		return true;
	}
	public String getNefudaUkewatasiNmString()
	{
		return "'" + cutString(this.nefuda_ukewatasi_nm,NEFUDA_UKEWATASI_NM_MAX_LENGTH) + "'";
	}
	public String getNefudaUkewatasiNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_ukewatasi_nm,NEFUDA_UKEWATASI_NM_MAX_LENGTH));
	}
//	↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑
	
	/**
	 * カラー名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getColor_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getColor_nm() {
		return cutString(this.color_nm,COLOR_NM_MAX_LENGTH);
	}
	public boolean setColor_nm(String color_nm) {
		this.color_nm = color_nm;
		return true;
	}
	public String getColor_nmString()
	{
		return "'" + cutString(this.color_nm,COLOR_NM_MAX_LENGTH) + "'";
	}
	public String getColor_nmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_nm,COLOR_NM_MAX_LENGTH));
	}
	
	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresaki_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public boolean setSiiresaki_nm(String siiresaki_nm) {
		this.siiresaki_nm = siiresaki_nm;
		return true;
	}
	public String getSiiresaki_nm() {
		return cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH);
	}
	public String getSiiresaki_nmString()
	{
		return "'" + cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH) + "'";
	}
	public String getSiiresaki_nmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_nm,SIIRESAKI_NM_MAX_LENGTH));
	}
	
	/**
	 * サイズ名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSize_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSize_nm() {
		return cutString(this.size_nm,SIZE_NM_MAX_LENGTH);
	}
	public boolean setSize_nm(String size_nm) {
		this.size_nm = size_nm;
		return true;
	}
	public String getSizeNMString()
	{
		return "'" + cutString(this.size_nm,SIZE_NM_MAX_LENGTH) + "'";
	}
	public String getSizeNMHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_nm,SIZE_NM_MAX_LENGTH));
	}
	
	/**
	 * 有効日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getYuko_dt();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public boolean setYuko_dt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYuko_dt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDTString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}
	public String getInsertTs() {
		return insertTs;
	}
	public void setInsertTs(String insertTs) {
		this.insertTs = insertTs;
	}
	
	/**
	 * 更新年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateTs(String updateTs)
	{
		this.updateTs = updateTs;
		return true;
	}
	/**
	 * 更新年月日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUpdateTs();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUpdateTs()
	{
		return this.updateTs;
	}
		
	public int getRowSpanCnt() {
		return rowSpanCnt;
	}
	public void setRowSpanCnt(int rowSpanCnt) {
		this.rowSpanCnt = rowSpanCnt;
	}
	public boolean isSpaceCell() {
		return spaceCell;
	}
	public void setSpaceCell(boolean spaceCell) {
		this.spaceCell = spaceCell;
	}
	public String getDbSystemDate() {
		return dbSystemDate;
	}
	public void setDbSystemDate(String dbSystemDate) {
		this.dbSystemDate = dbSystemDate;
	}
	public String getHenkoDT() {
		return henkoDT;
	}
	public void setHenkoDT(String henkoDT) {
		this.henkoDT = henkoDT;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getOriginalHachuEndDt() {
		return originalHachuEndDt;
	}
	public void setOriginalHachuEndDt(String originalHachuEndDt) {
		this.originalHachuEndDt = originalHachuEndDt;
	}
	public String getOriginalHanbaiEndDt() {
		return originalHanbaiEndDt;
	}
	public void setOriginalHanbaiEndDt(String originalHanbaiEndDt) {
		this.originalHanbaiEndDt = originalHanbaiEndDt;
	}
	public String getTuiKeiyakusuQt() {
		return tuiKeiyakusuQt;
	}
	public void setTuiKeiyakusuQt(String tuiKeiyakusuQt) {
		this.tuiKeiyakusuQt = tuiKeiyakusuQt;
	}
}
