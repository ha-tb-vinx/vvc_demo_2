package mdware.master.common.bean;

import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）ギフトマスタのレコード格納用クラス</p>
 * <p>説明: 	：新ＭＤシステムで使用するギフトマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: 	：Copyright (c) 2005</p>
 * <p>会社名: 	：Vinculum Japan Corp.</p>
 * @author 	：VJC A.Tozaki
 * @version 	：1.0(2005/11/17)初版作成
 * 更新履歴		：2004-04-13 M.Kawamoto
 * 				  1.画面項目改訂による全面修正
 *   			　2006-04-24 M.Kawamoto
 * 				  2.登録者、更新者表示対応
 * 				  ユーザ名が取得できた場合はユーザ名を表示
 * 				  取得できなかった場合は、ログインＩＤを表示
 *                3.画面項目を可変にするための商品名＋規格のサイズ対応追加
 */

public class mst880101_GiftBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int GIFT_CD_MAX_LENGTH 				= 6;	// ギフトコードの長さ
	public static final int HANKU_CD_MAX_LENGTH 			= 4;	// 販区コードの長さ
	public static final int HANKU_NA_MAX_LENGTH 			= 20;	// 販区名の長さ
	public static final int SYOHIN_CD_MAX_LENGTH 			= 13;	// 商品コードの長さ
	public static final int SYOHIN_NA_MAX_LENGTH 			= 40;	// 商品名の長さ
	public static final int BAITANKA_VL_MAX_LENGTH			= 7;	// 商品売価の長さ
	public static final int HINSYU_CD_MAX_LENGTH			= 4;	// 品種コードの長さ
	public static final int ATUKAI_ST_DT_MAX_LENGTH 		= 8;	// 取扱開始日の長さ
	public static final int ATUKAI_ED_DT_MAX_LENGTH 		= 8;	// 取扱終了日コードの長さ
	public static final int SEND_DT_MAX_LENGTH 				= 8;	// 送信日の長さ
	public static final int INSERT_TS_MAX_LENGTH			= 20;	// 作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 		= 20;	// 作成者社員ＩＤの長さ
	public static final int UPDATE_TS_MAX_LENGTH 			= 20;	// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 		= 20;	// 更新者社員ＩＤの長さ
	public static final int DELETE_FG_MAX_LENGTH 			= 1;	// 削除フラグの長さ
	public static final int HENKO_DT_MAX_LENGTH 			= 8;	// 変更日の長さ

//　2006-04-13 M.Kawamoto 追加
	public static final int KIKAKU_KANJI_NA_MAX_LENGTH		= 40;	// 商品規格漢字
	public static final int JAN_CD_MAX_LENGTH 				= 13;	//JANコードの長さ
	public static final int HINSYU_NA_MAX_LENGTH 			= 20;	//品種名の長さ

	private String jan_cd 			= null;	// JANコード
	private String hinsyu_na			= null;	// 品種名
	private String kikaku_kanji_na	= null;	// 商品規格漢字
	private String syohin_kb			= null;	// 商品区分
//

	private String gift_cd 				= null;	// ギフトコード 
	private String hanku_cd 				= null;	// 販区コード 
	private String hanku_na 				= null;	// 販区名 
	private String syohin_cd 				= null;	// 商品コード 
	private String syohin_na 				= null;	// 商品名 
	private String baitanka_vl			= null;	// 商品売価 
	private String hinsyu_cd			= null;	// 品種コード
	private String atukai_st_dt		= null;	// 取扱開始日
	private String atukai_ed_dt		= null;	// 取扱終了日
	private String send_dt 			= null;	// 送信日
	private String insert_ts 		  	= null;	// 作成年月日
	private String insert_user_id 	  	= null;	// 作成者社員ＩＤ
	private String update_ts 		  	= null;	// 更新年月日
	private String update_user_id 	  	= null;	// 更新者社員ＩＤ
	private String delete_fg 	  		= null;	// 削除フラグ
	private String henko_dt			= null;	// 変更日
	
	private String sentaku 			= "";		//画面入力CheckBox
	private Map    ctlColor 			= null;	//画面コントロール色	
	private String disbale 			= null;	//明細入力可否

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
	 * mst880101_GiftBeanを１件のみ抽出したい時に使用する
	 */
	public static mst880101_GiftBean getmst880101_GiftBean(DataHolder dataHolder)
	{
		mst880101_GiftBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst880101_GiftDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst880101_GiftBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	/**
	 * 商品区分を取得します。
	 * 
	 * @return 商品区分
	 */
	public String getSyohinkb() {
		return this.syohin_kb;
	}

	/**
	 * 商品区分をセットします。
	 * 
	 * @param syohin_kb
	 */
	public void setSyohinkb(String syohin_kb) {
		this.syohin_kb = syohin_kb;
	}


	/**
	 * Janコードをセットします。
	 * @param jan_cd	セットするＪａｎコード
	 */
	public boolean setJanCd(String jan_cd) {
		this.jan_cd = jan_cd;
		return true;
	}

	/**
	 * Janコードを指定の長さまで取得します。
	 * @return Janコード
	 */
	public String getJanCd() {
		return cutString(this.jan_cd,JAN_CD_MAX_LENGTH);
	}

	/**
	 * Janコードを指定の長さまで取得し、文字列クォーテーションで囲んだ形で返します。
	 * @return Janコード
	 */
	public String getJanCdString() {
		return "'" + cutString(this.jan_cd,JAN_CD_MAX_LENGTH) + "'";
	}
	
	/**
	 * Janコードを指定の長さまで取得し、HTML上の禁則文字対応等(HTMLエンコーディング)を行い返します。
	 * @return Janコード
	 */
	public String getJanCdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.jan_cd, JAN_CD_MAX_LENGTH));
	}
	
	/**
	 * 品種コード名をセットします。
	 * @param hinsyu_na　品種コード名
	 */
	public void setHinsyuNa(String hinsyu_na) {
		this.hinsyu_na = hinsyu_na;
	}

	/**
	 * 品種コード名を指定の長さまで取得します。
	 * @return　品種コード名
	 */
	public String getHinsyuNa() {
		return cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH);
	}

	/**
	 * 品種コード名を指定の長さまで取得し、文字列クォーテーションで囲んだ形で返します。
	 * @return　品種コード名
	 */
	public String getHinsyuNaString() {
		return "'" + cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH) + "'";
	}

	/**
	 * 品種コード名を指定の長さまで取得し、HTML上の禁則文字対応等(HTMLエンコーディング)を行い返します。
	 * @return 品種コード名
	 */
	public String getHinsyuNaHTMLString(){
		return HTMLStringUtil.convert(cutString(this.hinsyu_na, HINSYU_NA_MAX_LENGTH));
	}

	/**
	 * 商品規格漢字名をセットします。
	 * @param kikaku_kanji_na　商品規格漢字名
	 */
	public void setKikakuKanjiNa(String kikaku_kanji_na) {
		this.kikaku_kanji_na = kikaku_kanji_na;
	}

	/**
	 * 商品規格漢字名を指定の長さまで取得します。
	 * @return　商品規格漢字名
	 */
	public String getKikakuKanjiNa() {
		return cutString(this.kikaku_kanji_na, KIKAKU_KANJI_NA_MAX_LENGTH);
	}

	/**
	 * 商品規格漢字名を指定の長さまで取得し、文字列クォーテーションで囲んだ形で返します。
	 * @return　商品規格漢字名
	 */
	public String getKikakuKanjiNaString() {
		return "'" + cutString(this.kikaku_kanji_na, KIKAKU_KANJI_NA_MAX_LENGTH) + "'";
	}

	/**
	 * 商品規格漢字名を指定の長さまで取得し、HTML上の禁則文字対応等(HTMLエンコーディング)を行い返します。
	 * @return 商品規格漢字名
	 */
	public String getKikakuKanjiNaHTMLString(){
		return HTMLStringUtil.convert(cutString(this.kikaku_kanji_na, KIKAKU_KANJI_NA_MAX_LENGTH));
	}

	// gift_cdに対するセッターとゲッターの集合
	public boolean setGiftCd(String gift_cd)
	{
		this.gift_cd = gift_cd;
		return true;
	}
	public String getGiftCd()
	{
		return cutString(this.gift_cd,GIFT_CD_MAX_LENGTH);
	}
	public String getGiftCdString()
	{
		return "'" + cutString(this.gift_cd,GIFT_CD_MAX_LENGTH) + "'";
	}
	public String getGiftCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gift_cd,GIFT_CD_MAX_LENGTH));
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

	// hanku_naに対するセッターとゲッターの集合
	public boolean setHankuNa(String hanku_na)
	{
		this.hanku_na = hanku_na;
		return true;
	}
	public String getHankuNa()
	{
		return cutString(this.hanku_na,HANKU_NA_MAX_LENGTH);
	}
	public String getHankuNaString()
	{
		return "'" + cutString(this.hanku_na,HANKU_NA_MAX_LENGTH) + "'";
	}
	public String getHankuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_na,HANKU_NA_MAX_LENGTH));
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
	
	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitankaVl()
	{
		return cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH);
	}
	public String getBaitankaVlString()
	{
		return "'" + cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH) + "'";
	}
	public String getBaitankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.baitanka_vl,BAITANKA_VL_MAX_LENGTH));
	}

	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH));
	}

	// atukai_st_dtに対するセッターとゲッターの集合
	public boolean setAtukaiStDt(String atukai_st_dt)
	{
		this.atukai_st_dt = atukai_st_dt;
		return true;
	}
	public String getAtukaiStDt()
	{
		return cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH);
	}
	public String getAtukaiStDtString()
	{
		return "'" + cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getAtukaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.atukai_st_dt, ATUKAI_ST_DT_MAX_LENGTH));
	}

	// atukai_ed_dtに対するセッターとゲッターの集合
	public boolean setAtukaiEdDt(String atukai_ed_dt)
	{
		this.atukai_ed_dt = atukai_ed_dt;
		return true;
	}
	public String getAtukaiEdDt()
	{
		return cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH);
	}
	public String getAtukaiEdDtString()
	{
		return "'" + cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH) + "'";
	}
	public String getAtukaiEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.atukai_ed_dt, ATUKAI_ED_DT_MAX_LENGTH));
	}

	// send_dtに対するセッターとゲッターの集合
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	public String getSendDt()
	{
		return cutString(this.send_dt, SEND_DT_MAX_LENGTH);
	}
	public String getSendDtString()
	{
		return "'" + cutString(this.send_dt, SEND_DT_MAX_LENGTH) + "'";
	}
	public String getSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_dt, SEND_DT_MAX_LENGTH));
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
		return cutString(this.delete_fg, DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg, DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg, DELETE_FG_MAX_LENGTH));
	}

	// henko_dtに対するセッターとゲッターの集合
	public boolean setHenkoDt(String henko_dt)
	{
		this.henko_dt = henko_dt;
		return true;
	}
	public String getHenkoDt()
	{
		return cutString(this.henko_dt, HENKO_DT_MAX_LENGTH);
	}
	public String getHenkoDtString()
	{
		return "'" + cutString(this.henko_dt, HENKO_DT_MAX_LENGTH) + "'";
	}
	public String getHenkoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.henko_dt, HENKO_DT_MAX_LENGTH));
	}

	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return this.sentaku;
	}

	// ctlColorに対するセッターとゲッターの集合
	public boolean setCtlColor(Map ctlColor)
	{
		this.ctlColor = ctlColor;
		return true;
	}
	public Map getCtlColor()
	{
		return this.ctlColor;
	}
	
	// disbaleに対するセッターとゲッターの集合
	public boolean setDisbale(String disbale)
	{
		this.disbale = disbale;
		return true;
	}
	public String getDisbale()
	{
		return this.disbale;
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hanku_cd = " + getHankuCdString()
			+ "  hanku_na " + getHankuNaString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  syohin_na " + getSyohinNaString()
			+ "  atukai_st_dt = " + getAtukaiStDtString()
			+ "  atukai_ed_dt = " + getAtukaiEdDtString()
			+ "  send_dt = " + getSendDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  henko_dt = " + getHenkoDtString()
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst880101_GiftBean コピー後のクラス
	 */
	public mst880101_GiftBean createClone()
	{
		mst880101_GiftBean bean = new mst880101_GiftBean();
		bean.setJanCd(this.jan_cd);
		bean.setHinsyuNa(this.hinsyu_na);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHankuCd(this.hanku_cd);
		bean.setHankuNa(this.hanku_na);
		bean.setSyohinCd(this.syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setAtukaiStDt(this.atukai_st_dt);
		bean.setAtukaiEdDt(this.atukai_ed_dt);
		bean.setSendDt(this.send_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		bean.setHenkoDt(this.henko_dt);
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
		if( !( o instanceof mst880101_GiftBean ) ) return false;
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
