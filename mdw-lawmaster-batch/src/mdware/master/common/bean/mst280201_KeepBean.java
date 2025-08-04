/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import java.util.*;
import java.text.NumberFormat;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.master.common.dictionary.mst004501_KakakuKbDictionary;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）価格チェックマスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する価格チェックマスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Yamada
 * @version 1.0(2005/03/18)初版作成
 */
public class mst280201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	//↓↓2006.07.10 chencl カスタマイズ修正↓↓
//	private static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	private static final int BUMON_CD_MAX_LENGTH = 4;//部門コードの長さ

//	private static final int HANKU_NM_MAX_LENGTH = 20;//販区名称の長さ
	private static final int BUMON_NA_MAX_LENGTH = 20;//部門名称の長さ
	//↑↑2006.07.10 chencl カスタマイズ修正↑↑
	private static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	private static final int SYOHIN_NM_MAX_LENGTH = 80;//商品名称の長さ
	private static final int KAKAKU_KB_MAX_LENGTH = 1;//価格区分の長さ
	private static final int CHECK_ST_DT_MAX_LENGTH = 8;//チェック開始日の長さ
	private static final int CHECK_ED_DT_MAX_LENGTH = 8;//チェック終了日の長さ
	private static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	private static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	private static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	private static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	private static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	//↓↓2006.07.10 chencl カスタマイズ修正↓↓
//	private String hanku_cd = null;	//販区コード
	private String bumon_cd = null;	//部門コード
//	private String hanku_nm = null;	//販区名称

	private String bumon_na = null;	//部門名称
	//↑↑2006.07.10 chencl カスタマイズ修正↑↑
	private String syohin_cd = null;	//商品コード
	private String syohin_nm = null;	//商品名称
	private String kakaku_kb = null;	//価格区分
	private long min_kakaku_vl = 0;	//最低価格
	private String check_st_dt = null;	//チェック開始日
	private String check_ed_dt = null;	//チェック終了日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

	private String[] menuBar   = null;         //メニューバーアイテム
	private Map ctrlColor      = new HashMap();//コントロール背景色/前景色
	private String decisionFlg = "";           // 確定ボタン押下フラグ（エラー判定時用）

	private String processingDivision = null;//処理状況
	private String errorFlg           = null;//エラーフラグ
	private String errorMessage       = null;//エラーメッセージ
	private String mode               = null;//処理モード
	private String firstFocus         = null;//フォーカスを最初に取得するオブジェクト名
	private String insertFlg          = null;//新規処理利用可能区分
	private String updateFlg          = null;//更新処理利用可能区分
	private String deleteFlg          = null;//削除処理利用可能区分
	private String referenceFlg       = null;//照会処理利用可能区分
	private String csvFlg             = null;//CSV処理利用可能区分
	private String printFlg           = null;//印刷処理利用可能区分
	private String beforeDispKb       = "";//前画面区分(Menu:0 Menu以外:1)
	private String beforeDispUrl      = "";//前画面URL
	private String dispUrl            = null;//現画面URL

	private String checkFlg = null;//チェック処理判断

	private NumberFormat numberFormat = NumberFormat.getInstance();

	private static final String INIT_PAGE = "mst280101_KakakuCheckInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst280201_KakakuCheckEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst280301_KakakuCheckView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得

	// checkFlgに対するセッターとゲッターの集合
	public boolean setCheckFlg(String s) {
		this.checkFlg = s;
		return true;
	}
	public String getCheckFlg() {
		return this.checkFlg;
	}

	// processingDivisionに対するセッターとゲッターの集合
	public boolean setProcessingDivision(String s)
	{
		this.processingDivision = s;
		return true;
	}
	public String getProcessingDivision()
	{
		return this.processingDivision;
	}

	// errorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String s)
	{
		this.errorFlg = s;
		return true;
	}
	public String getErrorFlg()
	{
		return this.errorFlg;
	}

	// errorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String s)
	{
		this.errorMessage = s;
		return true;
	}
	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	// modeに対するセッターとゲッターの集合
	public boolean setMode(String s)
	{
		this.mode = s;
		return true;
	}
	public String getMode()
	{
		return this.mode;
	}

	// menuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] ss)
	{
		this.menuBar = ss;
		return true;
	}
	public String[] getMenuBar()
	{
		return this.menuBar;
	}

	// ctrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map map)
	{
		this.ctrlColor = map;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}

	// firstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String s)
	{
		this.firstFocus = s;
		return true;
	}
	public String getFirstFocus()
	{
		return this.firstFocus;
	}
	// insertFlgに対するセッターとゲッターの集合
	public boolean setInsertFlg(String s)
	{
		this.insertFlg = s;
		return true;
	}
	public String getInsertFlg()
	{
		return this.insertFlg;
	}
	// updateFlgに対するセッターとゲッターの集合
	public boolean setUpdateFlg(String s)
	{
		this.updateFlg = s;
		return true;
	}
	public String getUpdateFlg()
	{
		return this.updateFlg;
	}
	// deleteFlgに対するセッターとゲッターの集合
	public boolean setDeleteFlg(String s)
	{
		this.deleteFlg = s;
		return true;
	}
	public String getDeleteFlg()
	{
		return this.deleteFlg;
	}
	// referenceFlgに対するセッターとゲッターの集合
	public boolean setReferenceFlg(String s)
	{
		this.referenceFlg = s;
		return true;
	}
	public String getReferenceFlg()
	{
		return this.referenceFlg;
	}
	// csvFlgに対するセッターとゲッターの集合
	public boolean setCsvFlg(String s)
	{
		this.csvFlg = s;
		return true;
	}
	public String getCsvFlg()
	{
		return this.csvFlg;
	}
	// printFlgに対するセッターとゲッターの集合
	public boolean setPrintFlg(String s)
	{
		this.printFlg = s;
		return true;
	}
	public String getPrintFlg()
	{
		return this.printFlg;
	}
	// beforeDispKbに対するセッターとゲッターの集合
	public boolean setBeforeDispKb(String s)
	{
		this.beforeDispKb = s;
		return true;
	}
	public String getBeforeDispKb()
	{
		return this.beforeDispKb;
	}
	// dispUrlに対するセッターとゲッターの集合
	public boolean setDispUrl(String s)
	{
		this.dispUrl = s;
		return true;
	}
	public String getDispUrl()
	{
		return this.dispUrl;
	}
	// beforeDispUrlに対するセッターとゲッターの集合
	public boolean setBeforeDispUrl(String s)
	{
		this.beforeDispUrl = s;
		return true;
	}
	public String getBeforeDispUrl()
	{
		return this.beforeDispUrl;
	}
	// decisionFlgに対するセッターとゲッターの集合
	public boolean setDecisionFlg(String s)
	{
		this.decisionFlg = s;
		return true;
	}
	public String getDecisionFlg()
	{
		return this.decisionFlg;
	}
	//↓↓2006.07.10 chencl カスタマイズ修正↓↓
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
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//
//	// hanku_nmに対するセッターとゲッターの集合
//	public boolean setHankuNm(String hanku_nm)
//	{
//		this.hanku_nm = hanku_nm;
//		return true;
//	}
//	public String getHankuNm()
//	{
//		return cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH);
//	}
//	public String getHankuNmString()
//	{
//		return cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH);
//	}
//	public String getHankuNmHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_nm,HANKU_NM_MAX_LENGTH));
//	}
	//↑↑2006.07.10 chencl カスタマイズ修正↑↑
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
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}

	// bumon_naに対するセッターとゲッターの集合
	public boolean setBumonNa(String bumon_na)
	{
		this.bumon_na = bumon_na;
		return true;
	}
	public String getBumonNa()
	{
		return cutString(this.bumon_na,BUMON_NA_MAX_LENGTH);
	}
	public String getBumonNaString()
	{
		return cutString(this.bumon_na,BUMON_NA_MAX_LENGTH);
	}
	public String getBumonNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_na,BUMON_NA_MAX_LENGTH));
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
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}
	
	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinNm(String syohin_nm)
	{
		this.syohin_nm = syohin_nm;
		return true;
	}
	public String getSyohinNm()
	{
		return cutString(this.syohin_nm,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinNmString()
	{
		return "'" + cutString(this.syohin_nm,SYOHIN_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_nm,SYOHIN_NM_MAX_LENGTH));
	}


	// kakaku_kbに対するセッターとゲッターの集合
	public boolean setKakakuKb(String kakaku_kb)
	{
		this.kakaku_kb = kakaku_kb;
		return true;
	}
	public String getKakakuKb()
	{
		return cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH);
	}
	public String getKakakuKbString()
	{
		return "'" + cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH) + "'";
	}
	public String getKakakuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kakaku_kb,KAKAKU_KB_MAX_LENGTH));
	}


	// min_kakaku_vlに対するセッターとゲッターの集合
	public boolean setMinKakakuVl(String min_kakaku_vl)
	{
		try
		{
			this.min_kakaku_vl = Long.parseLong(min_kakaku_vl.replaceAll(",", ""));
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMinKakakuVl(long min_kakaku_vl)
	{
		this.min_kakaku_vl = min_kakaku_vl;
		return true;
	}
	public long getMinKakakuVl()
	{
		return this.min_kakaku_vl;
	}
	public String getMinKakakuVlString()
	{
		return Long.toString(this.min_kakaku_vl);
	}
	/**
	 *   Returns number formatted 123456 --> "123,456"
	 *   @return number formatted string.
	 */
	public String getFormattedMinKakakuVlString() {
		return numberFormat.format(min_kakaku_vl);
	}


	// check_st_dtに対するセッターとゲッターの集合
	public boolean setCheckStDt(String check_st_dt)
	{
		this.check_st_dt = check_st_dt;
		return true;
	}
	public String getCheckStDt()
	{
		return cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH);
	}
	public String getCheckStDtString()
	{
		return "'" + cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH) + "'";
	}
	public String getCheckStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.check_st_dt,CHECK_ST_DT_MAX_LENGTH));
	}


	// check_ed_dtに対するセッターとゲッターの集合
	public boolean setCheckEdDt(String check_ed_dt)
	{
		this.check_ed_dt = check_ed_dt;
		return true;
	}
	public String getCheckEdDt()
	{
		return cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH);
	}
	public String getCheckEdDtString()
	{
		return "'" + cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH) + "'";
	}
	public String getCheckEdDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.check_ed_dt,CHECK_ED_DT_MAX_LENGTH));
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

	/**
	 *  作成年月日の時刻部分を除いて西暦年月日のみを返す.
	 *  @return 西暦年月日を返す.
	 */
	public String getInsertTsWithoutTime() {
		String s = getInsertTsHTMLString();
		if (s.length() >= 8) {
			return s.substring(0, 8);
		} else {
			return s;
		}
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

	/**
	 *  更新年月日の'時分秒'部分を除いて'年月日'のみを返す.
	 *  @return 西暦年月日を返す.
	 */
	public String getUpdateTsWithoutTime() {
		String s = getUpdateTsHTMLString();
		if (s.length() >= 8) {
			return s.substring(0, 8);
		} else {
			return s;
		}
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
	 *  本KeepBeanの価格区分は原単価, 売単価どちらかを示しているのかを返す.
	 *
	 *  @return 原単価の場合trueを返す.
	 *          なお,価格区分がnullの場合は原単価とみなし、trueを返す.
	 */
	private boolean isGenChecked() {
		String genCode = mst004501_KakakuKbDictionary.GEN_TANKA.getCode(); // 原単価
		String uriCode = mst004501_KakakuKbDictionary.URI_TANKA.getCode(); // 売単価
		String kbn = this.getKakakuKb();
		boolean genChecked = true; // default 
		if (kbn != null) {
			if (kbn.equals(genCode)) { // 原単価
				;
			} else if (kbn.equals(uriCode)) { // 売単価
				genChecked = false;
			} else {
				;
			}
		} else {
			;
		}
		return genChecked;
	}

	private static final String CHECKED = "checked";
	private static final String SP      = "";

	/**
	 * 原単価が選択されている場合は"checked"を返す.
	 * それ以外は""を返す.
	 */
	public String getGenChecked() {
		return isGenChecked() ? CHECKED : SP;
	}

	/**
	 * 売単価が選択されている場合は"checked"を返す.
	 * それ以外は""を返す.
	 */
	public String getUriChecked() {
		return isGenChecked() ? SP : CHECKED;
	}

  /**
   * 初期画面URL取得(ログ出力有り)
   * <br>
   * Ex)<br>
   * getInitUrl("logHeader","logMsg") -&gt; String<br>
   * <br>
   * @param String logHeader
   * @param String logMsg
   * @return		String
   */
	public String getInitUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		return	INIT_PAGE;
	}

	/**
	 * 初期画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getInitUrl()
	{
		return	INIT_PAGE;
	}

	/**
	 * 登録画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getEditUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getEditUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	EDIT_PAGE;
	}
	/**
	 * 登録画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getInitUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getEditUrl()
	{
		return	EDIT_PAGE;
	}

	/**
	 * 照会画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getViewUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	public String getViewUrl(String logHeader,String logMsg)
	{
		//画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		//処理終了ログ
		if(!logMsg.equals("")){
			stcLog.getLog().info(logHeader + logMsg);
		}
		
		return	VIEW_PAGE;
	}
	/**
	 * 照会画面URL取得(ログ出力なし)
	 * <br>
	 * Ex)<br>
	 * getViewUrl() -&gt; String<br>
	 * <br>
	 * @return		String
	 */
	public String getViewUrl()
	{
		return	VIEW_PAGE;
	}

	/**
	 * 権限エラー画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getKengenErr("logHeader") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @return		String
	 */
	public String getKengenErr(String logHeader)
	{
		stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		return KENGEN_PAGE;
	}
}