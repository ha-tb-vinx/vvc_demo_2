/**
 * <P>タイトル : 新ＭＤシステム　（マスター管理）
 *               単品店自動作成制御マスタ登録 mst610101_TanTenSeigyo 用
 *               単品店自動作成制御マスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する mst610101_TanTenSeigyo用計量器マスタのレコード格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author C.Sawabe
 * @version 1.0	(2005/04/14)	新規作成
 * @see なし								
 */

package mdware.master.common.bean;

import java.util.*;

import mdware.portal.bean.MdwareUserSessionBean;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;

/**
 * <P>タイトル : 新ＭＤシステム（マスター管理） mst610101_TanTenSeigyo用単品店自動作成制御マスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst610101_TanTenSeigyo用単品店自動作成制御マスタのレコード格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author C.Sawabe
 * @version 1.0	(2005/04/13)	新規作成
 * @see なし								
 */
public class mst610201_KeepBean {

	/**
	 * 
	 */
	public mst610201_KeepBean() {
	}

	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final int KANRI_KANJI_RN_MAX_LENGTH  = 20;//管理名称
	public static final int INSERT_USER_NA_MAX_LENGTH  = 20;//作成者名
	public static final int INSERT_INITROWS_MAX_LENGTH = 10;//新規登録時の初期表示件数
	public static final int MAX_ROWS_LENGTH            = 30;//1ページの明細行表示件数
	
	//ログインユーザー
	private String user_id = null;
	
	//明細Bean
	public mst610301_KeepMeisaiBean meisaiBean = null;

	//画面表示項目
//    ↓↓2006.07.10 magp カスタマイズ修正↓↓	
//	private String kanri_kb      = null;		//管理区分
//	private String kanri_cd      = null;		//管理コード
//	private String kanri_cd_from = null;		//管理コード(from)
//	private String kanri_nm_from = null;		//管理名称(from)
//	private String kanri_cd_to   = null;		//管理コード(to)
//	private String kanri_nm_to   = null;		//管理名称(to)
//	private String kanri_kanji_rn= null;		//管理名称
	
	private String unit_cd_from = null;		//ユニットコード(from)
	private String unit_nm_from = null;		//ユニット名称(from)
	private String unit_cd_to   = null;		//ユニットコード(to)
	private String unit_nm_to   = null;		//ユニット名称(to)
	
//	↑↑2006.07.10 magp カスタマイズ修正↑↑	
	
	private String sentaku_flg   = null;		//選択チェック有無
		
	//処理制御フラグ
	private String processingdivision = null;	//処理状況
	private String errorflg           = null;	//エラーフラグ
	private String checkflg           = null;	//チェック処理判断
	private String existflg           = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg     = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg   = null;	//更新処理内容
	private Map mode = new HashMap();			//処理モード
	private String firstfocus         = null;	//フォーカスを最初に取得するオブジェクト名
	private String insertflg          = null;	//新規処理利用可能区分
	private String updateflg          = null;	//更新処理利用可能区分
	private String deleteflg          = null;	//削除処理利用可能区分
	private String referenceflg       = null;	//照会処理利用可能区分
	private String csvflg             = null;	//CSV処理利用可能区分
	private String printflg           = null;	//印刷処理利用可能区分
	private String systemKbflg           = null;	//業種
	private boolean disSysKbflg           = false;	//業種利用可能区分
	private String changflg           = null;	
	
	//画面共通表示項目
	private String errormessage       = null;				//エラーメッセージ
	private String[] menubar          = null;				//メニューバーアイテム
	private Map CtrlColor			   = new HashMap();	// コントロール前景色
	
	//画面遷移	
	private String before_disp_id = null;	//前画面ID
	private String disp_url       = null;	//現画面URL

	//更新処理内容配列
//	private List updateprocesslst;
	private BeanHolder meisaiBh;		//検索結果格納用BeanHolder

	private List meisaiList = null;	// 一覧の明細
	private int MaxRows    = 0;		// 1ページの最大表示件数
	private int InitRows   = 0;		// 新規登録時の表示行数
	private String move    = null;	// ページ遷移状態保持
	
	private int cntPage = 0;

	/**
	 * コンストラクタ
	 */
//  ↓↓2006.07.10 magp カスタマイズ修正↓↓		
//	public mst610201_KeepBean (mst000501_SysSosasyaBean sysUserBean) {
	public mst610201_KeepBean (MdwareUserSessionBean sysUserBean) {
//	↑↑2006.07.10 magp カスタマイズ修正↑↑		
		this.user_id     = sysUserBean.getUserId();							//ログインユーザー
		this.meisaiList  = new ArrayList();								//一覧の明細
		this.meisaiBh    = new BeanHolder(new mst610101_TanTenSeigyoDM());	//検索結果格納BeanHolder
		this.meisaiBean  = new mst610301_KeepMeisaiBean();					//明細Bean
		this.MaxRows     = MAX_ROWS_LENGTH;	//最大行数
		this.InitRows    = 0;					//表示行数								
		this.move = "";
		
//	    ↓↓2006.07.10 magp カスタマイズ修正↓↓			
//		this.kanri_kb      = "";	//管理区分
//		this.kanri_cd      = "";	//管理コード
//		this.kanri_cd_from = "";	//管理コード(from)
//		this.kanri_nm_from = "";	//管理コード名称(from)
//		this.kanri_cd_to   = "";	//管理コード(to)
//		this.kanri_nm_to   = "";	//管理コード名称(to)
//		this.kanri_kanji_rn= "";	//管理名称
		this.unit_cd_from = "";		//ユニットコード(from)
		this.unit_nm_from = "";		//ユニットコード名称(from)
		this.unit_cd_to   = "";		//ユニットコード(to)
		this.unit_nm_to   = "";		//ユニットコード名称(to)
//		↑↑2006.07.10 magp カスタマイズ修正↑↑		
		this.sentaku_flg   = "";	//処理選択
		
		//処理制御フラグ
		this.mode               = new HashMap();	//処理モード
		this.processingdivision = "";				//処理状況	
		this.firstfocus         = "";				//フォーカスを最初に取得するオブジェクト名
		this.errorflg           = "";				//エラーフラグ
		this.checkflg           = "";				//チェック処理判断
		this.existflg           = "";				//データ存在(検索[ｷｬﾝｾﾙ]時)
		this.searcherrorflg     = "";				//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
		this.updateprocessflg   = "";				//更新処理内容
		
		this.insertflg          = "";				//新規処理利用可能区分
		this.updateflg          = "";				//更新処理利用可能区分
		this.deleteflg          = "";				//削除処理利用可能区分
		this.referenceflg       = "";				//照会処理利用可能区分
		this.csvflg             = "";				//CSV処理利用可能区分
		this.printflg           = "";				//印刷処理利用可能区分
	
		//画面共通表示項目
		this.errormessage = "";					//エラーメッセージ
		this.CtrlColor    = new HashMap();			// コントロール前景色
	
		//画面遷移
		this.before_disp_id = "";					//前画面ID
		this.disp_url       = "";					//現画面URL
		
		this.cntPage = 0;
	}
	
	
	/**
	 * MeisaiListに対するセッターとゲッターの集合
	 * 明細行表示用
	 */
	public void setMeisaiList(List meisaiList) {
		this.meisaiList = meisaiList;
		int mLstSize = this.meisaiList.size();
		this.MaxRows = mLstSize;
	}	
	public List getMeisaiList() {
		return this.meisaiList;
	}
	
	/**
	 * MeisaiList
	 * 検索ボタン押下後の検索結果(BeanHolder)を
	 * 格納するためのリスト
	 * @param bh
	 */
	public void setMeisaiList(BeanHolder bh) {	
		this.meisaiList = bh.getBeanList();
		this.meisaiBh = new BeanHolder();
		this.meisaiBh = bh;
		this.meisaiBh.setRowsInPage(this.MAX_ROWS_LENGTH);		
	}
	
	/**
	 * ページ遷移を制御するためのBeanHolder
	 */
	public BeanHolder getMeisaiBh() {
		return this.meisaiBh;
	}
	public void setMeisaiBh(BeanHolder bh) {
		this.meisaiBh = bh;
	}
	
	/**
	 * mst610401_KeepMeisaiBean
	 * @param i
	 * @return
	 */	
	public mst610301_KeepMeisaiBean getMeisaiList(int i) {
		this.meisaiBean = (mst610301_KeepMeisaiBean) this.meisaiList.get(i);
		return this.meisaiBean;
	}
	
	/**
	 * MaxRowsに対するセッターとゲッターの集合
	 * 1ページあたりの最大表示行数を設定する
	 */
	public boolean setMaxRows(int MaxRows) {
		this.MaxRows = MaxRows;
		return true;
	}
	
	public int getMaxRows() {
		return this.MaxRows;
	}
	
	/**
	 * for の開始カウント数を設定する
	 * @return
	 */
	public int getCntPage() {
		return this.cntPage;
	}
	public void setCntPage(int i) {
		this.cntPage = i;
	}
	
	/**
	 * ページ遷移状態保持
	 * @return
	 */
	public String getMove() {
		return this.move;
	}
	public void setMove(String move) {
		this.move = move;
	}

//    ↓↓2006.07.10 magp カスタマイズ修正↓↓	
//	/**
//	 * kanri_kanji_rnに対するセッターとゲッターの集合
//	 */ 
//	public boolean setKanriKanjiRn(String kanri_kanji_rn)
//	{
//		this.kanri_kanji_rn = kanri_kanji_rn;
//		return true;
//	}
//	public String getKanriKanjiRn()
//	{
//		return cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH);
//	}
//	public String getKanriKanjiRnString()
//	{
//		return "'" + cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getKanriKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanri_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH));
//	}	
//	/**
// 	 * 管理区分
// 	 */
//	public boolean setKanriKb(String kanri_kb) {
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
//	public String getKanriKb() {
//		return this.kanri_kb;
//	}

//	/**
//	 * 管理コード(from)
//	 */
//	public boolean setKanriCdFrom(String kanri_cd_from) {
//		this.kanri_cd_from = kanri_cd_from;
//		return true;
//	}
//	public String getKanriCdFrom() {
//		return this.kanri_cd_from;
//	}
//	
//	/**
//	 * 管理コード(to)
//	 */
//	public boolean setKanriCdTo(String kanri_cd_to) {
//		this.kanri_cd_to = kanri_cd_to;
//		return true;
//	}
//	public String getKanriCdTo() {
//		return this.kanri_cd_to;
//	}
	
	/**
	 * ユニットコード(from)
	 */
	public boolean setUnitCdFrom(String unit_cd_from) {
		this.unit_cd_from = unit_cd_from;
		return true;
	}
	public String getUnitCdFrom() {
		return this.unit_cd_from;
	}
	
	/**
	 * ユニットコード(to)
	 */
	public boolean setUnitCdTo(String unit_cd_to) {
		this.unit_cd_to = unit_cd_to;
		return true;
	}
	public String getUnitCdTo() {
		return this.unit_cd_to;
	}
	
//	/**
//	 * 管理コード
//	 */
//	public boolean setKanriCd(String kanri_cd) {
//		this.kanri_cd = kanri_cd;
//		return true;
//	}
//	public String getKanriCd() {
//		return this.kanri_cd;
//	}
	
//	↑↑2006.07.10 magp カスタマイズ修正↑↑	
	
	/**
	 * 選択チェック：sentaku_flg に対するセッターとゲッターの集合
	 */
	public boolean setSentakuFlg(String sentaku_flg) {
		this.sentaku_flg = sentaku_flg;
		return true;
	}
	public String getSentakuFlg() {
		return this.sentaku_flg;
	}

	/**
	 * 処理状況
	 */
	public boolean setProcessingDivision(String processingdivision) {
		this.processingdivision = processingdivision;
		return true;
	}
	public String getProcessingDivision(){
		return this.processingdivision;
	}

	/**
	 * エラーフラグ
	 */
	public boolean setErrorFlg(String errorflg) {
		this.errorflg = errorflg;
		return true;
	}
	public String getErrorFlg(){
		return this.errorflg;
	}

	/**
	 * エラーメッセージ
	 */
	public boolean setErrorMessage(String errormessage) {
		this.errormessage = errormessage;
		return true;
	}
	public String getErrorMessage(){
		return this.errormessage;
	}

	/**
	 * メニューバーアイテム
	 */
	public boolean setMenuBar(String[] menubar){
		try {
			this.menubar = menubar;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	public String[] getMenuBar() {
		return this.menubar;
	}

	/**
	 * 処理モード
	 */
	public boolean setMode(Map mode) {
		try {
			this.mode = mode;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	public Map getMode() {
		return this.mode;
	}

	/**
	 * フォーカスを最初に取得するオブジェクト名
	 */
	public boolean setFirstFocus(String firstfocus) {
		this.firstfocus = firstfocus;
		return true;
	}
	public String getFirstFocus() {
		return this.firstfocus;
	}

	/**
	 * 新規処理利用可能区分
	 */
	public boolean setInsertFlg(String insertflg) {
		this.insertflg = insertflg;
		return true;
	}
	public String getInsertFlg() {
		return this.insertflg;
	}

	/**
	 * 更新処理利用可能区分
	 */
	public boolean setUpdateFlg(String updateflg) {
		this.updateflg = updateflg;
		return true;
	}
	public String getUpdateFlg() {
		return this.updateflg;
	}

	/**
	 * 削除処理利用可能区分
	 */
	public boolean setDeleteFlg(String deleteflg) {
		this.deleteflg = deleteflg;
		return true;
	}
	public String getDeleteFlg() {
		return this.deleteflg;
	}

	/**
	 * 照会処理利用可能区分
	 */
	public boolean setReferenceFlg(String referenceflg) {
		this.referenceflg = referenceflg;
		return true;
	}
	public String getReferenceFlg() {
		return this.referenceflg;
	}

	/**
	 * CSV処理利用可能区分
	 */
	public boolean setCsvFlg(String csvflg) {
		this.csvflg = csvflg;
		return true;
	}
	public String getCsvFlg() {
		return this.csvflg;
	}

	/**
	 * 印刷処理利用可能区分
	 */
	public boolean setPrintFlg(String printflg) {
		this.printflg = printflg;
		return true;
	}
	public String getPrintFlg() {
		return this.printflg;
	}

	/**
	 * 前画面ID
	 */
	public boolean setBeforeDispId(String before_disp_id) {
		this.before_disp_id = before_disp_id;
		return true;
	}
	public String getBeforeDispId() {
		return this.before_disp_id;
	}

	/**
	 * 現画面URL
	 */
	public boolean setDispUrl(String disp_url) {
		this.disp_url = disp_url;
		return true;
	}
	public String getDispUrl() {
		return this.disp_url;
	}

	/**
	 * チェック処理判断
	 */
	public boolean setCheckFlg(String checkflg) {
		this.checkflg = checkflg;
		return true;
	}
	public String getCheckFlg() {
		return this.checkflg;
	}

	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)
	 */
	public boolean setExistflg(String existflg) {
		this.existflg = existflg;
		return true;
	}
	public String getExistflg() {
		return this.existflg;
	}

	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	 */
	public boolean setSearcherrorflg(String searcherrorflg) {
		this.searcherrorflg = searcherrorflg;
		return true;
	}
	public String getSearcherrorflg() {
		return this.searcherrorflg;
	}

	/**
	 * 更新処理内容配列に対するゲッター<br>
	 */
//	public List getUpdateProcessLst() {
//		return updateprocesslst;
//	}
//
//	/**
//	 * 更新処理内容配列に対するセッター<br>
//	 */
//	public void setUpdateProcessLst(List list) {
//		updateprocesslst = list;
//	}

	/**
	 * 更新処理内容
	 */
	public boolean setUpdateProcessFlg(String updateprocessflg) {
		this.updateprocessflg = updateprocessflg;
		return true;
	}
	public String getUpdateProcessFlg() {
		return this.updateprocessflg;
	}

	/**
	 * CtrlColorに対するセッターとゲッターの集合
	 */ 
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}
	

	/**
	 * 明細1行を格納するためのBean
	 * @param bean
	 */
	public boolean setMeisaiBean(mst610301_KeepMeisaiBean bean) {
		this.meisaiBean = bean;
		return true;
	}
	public mst610301_KeepMeisaiBean getMeisaiBean() {
		return this.meisaiBean;
	}
	
	/**
	 * 表示件数設定
	 * @return
	 */
	public int getInitRows() {
		return InitRows;
	}
	public void setInitRows(int i) {
		InitRows = i;
	}

//  ↓↓2006.07.10 magp カスタマイズ修正↓↓	
//	/**
//	 * 管理名称from
//	 * @return
//	 */
//	public String getKanriNmFrom() {
//		return this.kanri_nm_from;
//	}
//	public boolean setKanriNmFrom(String kanri_nm_from) {
//		this.kanri_nm_from = kanri_nm_from;
//		return true;
//	}
//
//	/**
//	 * 管理名称to
//	 * @return
//	 */
//	public String getKanriNmTo() {
//		return this.kanri_nm_to;
//	}
//	public boolean setKanriNmTo(String kanri_nm_to) {
//		this.kanri_nm_to = kanri_nm_to;
//		return true;
//	}

	/**
	 * ユニット名称from
	 * @return
	 */
	public String getUnitNmFrom() {
		return this.unit_nm_from;
	}
	public boolean setUnitNmFrom(String unit_nm_from) {
		this.unit_nm_from = unit_nm_from;
		return true;
	}

	/**
	 * ユニット名称to
	 * @return
	 */
	public String getUnitNmTo() {
		return this.unit_nm_to;
	}
	public boolean setUnitNmTo(String unit_nm_to) {
		this.unit_nm_to = unit_nm_to;
		return true;
	}
//	↑↑2006.07.10 magp カスタマイズ修正↑↑	
	
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max ) {
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ ) {
			try {
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			} catch(Exception e) {
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}


	public String getSystemKbflg() {
		return systemKbflg;
	}


	public void setSystemKbflg(String systemKbflg) {
		this.systemKbflg = systemKbflg;
	}


	public boolean getDisSysKbflg() {
		return disSysKbflg;
	}


	public void setDisSysKbflg(boolean disSysKbflg) {
		this.disSysKbflg = disSysKbflg;
	}


	public String getChangflg() {
		return changflg;
	}


	public void setChangflg(String changflg) {
		this.changflg = changflg;
	}

}
