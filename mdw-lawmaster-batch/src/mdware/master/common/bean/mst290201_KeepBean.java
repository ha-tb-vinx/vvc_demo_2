/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/16)初版作成
 */
public class mst290201_KeepBean {

	public mst290201_KeepBean() {

		this.meisaiList  		= new ArrayList();	// 一覧の明細
		this.meisaiBean 		= new mst290401_KeepMeisaiBean();//明細Bean
		this.updateprocesslst	= new ArrayList();//更新処理内容配列
		this.meisaiBh 			= new BeanHolder(new mst290101_SetSyohinDM());
	
		this.hanku_cd 			= "";	//販区コード
		this.kanji_na 			= "";	//販区名
		this.setsyohin_cd 		= "";	//セット商品コード
		this.hinmei_kanji_rn 	= "";	//商品名
	
	
		//処理制御フラグ
		this.mode = new HashMap();		//処理モード
		this.processingdivision = "";	//処理状況	
		this.firstfocus 		= "";	//フォーカスを最初に取得するオブジェクト名
		this.errorflg 			= "";	//エラーフラグ
		this.checkflg			= "";	//チェック処理判断
		this.existflg		    = "";	//データ存在(検索[ｷｬﾝｾﾙ]時)
		this.searcherrorflg 	= "";	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
		this.updateprocessflg 	= "";	//更新処理内容
	
		this.insertflg 		= "";	//新規処理利用可能区分
		this.updateflg			= "";	//更新処理利用可能区分
		this.deleteflg 		= "";	//削除処理利用可能区分
		this.referenceflg 		= "";	//照会処理利用可能区分
		this.csvflg 			= "";	//CSV処理利用可能区分
		this.printflg 			= "";	//印刷処理利用可能区分
	
		//画面共通表示項目
		this.errormessage = "";//エラーメッセージ
		this.CtrlColor = new HashMap();	// コントロール前景色
	
		//画面遷移
		this.before_disp_id = "";//前画面ID
		this.disp_url = "";//現画面URL
	
		this.MaxRows        = MAX_ROWS_LENGTH;// 最大行数
		this.InitRows = 12;
	}

	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final int KANRI_KANJI_RN_MAX_LENGTH = 20;		//管理者名称
	public static final int INSERT_USER_NA_MAX_LENGTH = 20;		//作成者名称
	public static final int INSERT_INITROWS_MAX_LENGTH = 12;		//新規
	public static final int AX_ROWS_LENGTH = 20;					//作成者名称
	public static final int HANKU_KANJI_RN_MAX_LENGTH = 80;		//販区名称
	public static final int HINMEI_KANJI_RN_MAX_LENGTH = 80;		//商品名称
	public static final int MAX_ROWS_LENGTH            = 12;		//作成者名
	
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int INSERT_USER_ID_MAX_LENGTH = 8;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 8;
	public static final int DELETE_FG_MAX_LENGTH = 1;
	
	//ログインユーザー
	private String user_id = null;
	
	//明細表示項目Bean
	public mst290401_KeepMeisaiBean meisaiBean = null;
	
	//初期画面表示項目
	private String hanku_cd 			= null;	//販区コード
	private String kanji_na 			= null;	//販区名
	private String setsyohin_cd 		= null;	//セット商品コード
	private String hinmei_kanji_rn 	= null;	//商品名称
	
//	private String sentaku_flg  		= null;		//処理選択
	private String insert_ts     		= null;	//作成年月日
	private String insert_user_id		= null;	//作成者社員ID
	private String insert_user_na		= null;	//作成者名
	private String update_ts     		= null;	//更新年月日
	private String update_user_id		= null;	//更新者社員ID
	private String delete_fg 			= null;

	//処理制御フラグ

	private String processingdivision 	= null;	//処理状況
	private String errorflg 			= null;	//エラーフラグ
	private String checkflg 			= null;	//チェック処理判断
	private String existflg 			= null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg 		= null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)


	private String updateprocessflg 	= null;	//更新処理内容	
	private Map mode 					= new HashMap();//処理モード	
	private String firstfocus 			= null;	//フォーカスを最初に取得するオブジェクト名
	
	private String insertflg 			= null;	//新規処理利用可能区分
	private String updateflg 			= null;	//更新処理利用可能区分
	private String deleteflg 			= null;	//削除処理利用可能区分
	private String referenceflg 		= null;	//照会処理利用可能区分
	private String csvflg 				= null;	//CSV処理利用可能区分
	private String printflg 			= null;	//印刷処理利用可能区分


	//画面共通表示項目
	private String errormessage 		= null;//エラーメッセージ
	private String[] menuBar		    = null;//メニューバーアイテム
	private Map CtrlColor				= new HashMap();// コントロール前景色
	
	
	//画面遷移
	private String before_disp_id 		= null;	//前画面ID
	private String disp_url 			= null;	//現画面URL
	private boolean meisyo_flg		= false;	//名称再取得用フラグ
	
	//更新処理内容配列
	private List updateprocesslst = new ArrayList();
	private BeanHolder meisaiBh;
	private List meisaiList;						// 一覧の明細
	private int MaxRows			    = 12;		// 最大行数
	private int InitRows        		= 0;		// 新規登録時の表示行数
	private int InitMeisai        	= 0;		// 明細行表示数




	/**
	 * コンストラクタ
	 */
	public mst290201_KeepBean (mst000501_SysSosasyaBean sysUserBean) {
		
		this.user_id 			= sysUserBean.getUserId();//ログインユーザー
		this.meisaiList  		= new ArrayList();	// 一覧の明細
		this.meisaiBean 		= new mst290401_KeepMeisaiBean();//明細Bean
		this.updateprocesslst	= new ArrayList();//更新処理内容配列
		this.meisaiBh 			= new BeanHolder(new mst290101_SetSyohinDM());
		
		this.hanku_cd 			= "";	//販区コード
		this.kanji_na 			= "";	//販区名
		this.setsyohin_cd 		= "";	//セット商品コード
		this.hinmei_kanji_rn 	= "";	//商品名
		
//		this.sentaku_flg 		= "";	//処理選択
		this.insert_ts 		= "";	//作成年月日
		this.insert_user_id	= "";	//作成者社員ID
		this.update_ts 		= "";	//更新年月日
		this.update_user_id 	= "";	//更新者社員ID
		this.delete_fg 		= "";	//削除フラグ
		
		//処理制御フラグ
		this.mode = new HashMap();		//処理モード
		this.processingdivision = "";	//処理状況	
		this.firstfocus 		= "";	//フォーカスを最初に取得するオブジェクト名
		this.errorflg 			= "";	//エラーフラグ
		this.checkflg			= "";	//チェック処理判断
		this.existflg		    = "";	//データ存在(検索[ｷｬﾝｾﾙ]時)
		this.searcherrorflg 	= "";	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
		this.updateprocessflg 	= "";	//更新処理内容
		
		this.insertflg 		= "";	//新規処理利用可能区分
		this.updateflg			= "";	//更新処理利用可能区分
		this.deleteflg 		= "";	//削除処理利用可能区分
		this.referenceflg 		= "";	//照会処理利用可能区分
		this.csvflg 			= "";	//CSV処理利用可能区分
		this.printflg 			= "";	//印刷処理利用可能区分
	
		//画面共通表示項目
		this.errormessage = "";//エラーメッセージ
		this.CtrlColor = new HashMap();	// コントロール前景色
	
		//画面遷移
		this.before_disp_id 	= "";		//前画面ID
		this.disp_url 			= "";		//現画面URL
		this.meisyo_flg		=false;	//名称再取得判別用フラグ
		
		this.MaxRows            = MAX_ROWS_LENGTH;// 最大行数
		this.InitRows 			= 12;

	}
	

	// MeisaiListに対するセッターとゲッターの集合
	public boolean setMeisaiList(List meisaiList) {
		this.meisaiList = meisaiList;
		int mLstSize = this.meisaiList.size();
		this.MaxRows = mLstSize;
		return true;
	}
	
	public List getMeisaiList() {
		return this.meisaiList;
	}
	public mst290401_KeepMeisaiBean getMeisaiList(int i) {
		this.meisaiBean = (mst290401_KeepMeisaiBean) this.meisaiList.get(i);
		return this.meisaiBean;
	}

	/**
	 * 販区コードに対するセッター<br>
	 * setHankuCd("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	/**
	 * 販区コードに対するに対するゲッター<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getHankuCd()
	{
		return this.hanku_cd;
	}


	/**
	 * セット商品コードに対するセッター<br>
	 * setSetSyohinCd("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setSetSyohinCd(String setsyohin_cd)
	{
		this.setsyohin_cd = setsyohin_cd;
		return true;
	}
	/**
	 * セット商品コードに対するゲッター<br>
	 * getSetSyohinCd();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getSetSyohinCd()
	{
		return this.setsyohin_cd;
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

//	// update_user_naに対するセッターとゲッターの集合
//	public boolean setUpdateUserNa(String update_user_na)
//	{
//		this.update_user_na = update_user_na;
//		return true;
//	}
//	public String getUpdateUserNa()
//	{
//		return cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH);
//	}
//	public String getUpdateUserNaString()
//	{
//		return "'" + cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH) + "'";
//	}
//	public String getUpdateUserNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_NA_MAX_LENGTH));
//	}


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
	 * 処理状況に対するセッター<br>
	 * setProcessingdivision("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setProcessingDivision(String processingdivision)
	{
		this.processingdivision = processingdivision;
		return true;
	}
	/**
	 * 処理状況に対するゲッター<br>
	 * getProcessingdivision();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getProcessingDivision()
	{
		return this.processingdivision;
	}


	/**
	 * エラーフラグに対するセッター<br>
	 * setErrorflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorFlg(String errorflg)
	{
		this.errorflg = errorflg;
		return true;
	}
	/**
	 * エラーフラグに対するゲッター<br>
	 * getErrorflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getErrorFlg()
	{
		return this.errorflg;
	}


	/**
	 * エラーメッセージに対するセッター<br>
	 * setErrormessage("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setErrorMessage(String errormessage)
	{
		this.errormessage = errormessage;
		return true;
	}
	/**
	 * エラーメッセージに対するゲッター<br>
	 * getErrormessage();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getErrorMessage()
	{
		return this.errormessage;
	}

//	menuBarに対するセッターとゲッターの集合
	 public boolean setMenuBar(String[] ss)
	 {
		 this.menuBar = ss;
		 return true;
	 }
	 public String[] getMenuBar()
	 {
		 return this.menuBar;
	 }


	/**
	 * 処理モードに対するセッター<br>
	 * setMode("Map");<br>
	 * @param Map 設定するMap配列
	 */
	public boolean setMode(Map mode)
	{
		try
		{
			this.mode = mode;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * 処理モードに対するゲッター<br>
	 * getMode();　戻り値　Map配列<br>
	 * @return String[] Map配列
	 */
	public Map getMode()
	{
		return this.mode;
	}

	
	/**
	 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
	 * setFirstfocus("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setFirstFocus(String firstfocus)
	{
		this.firstfocus = firstfocus;
		return true;
	}
	/**
	 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
	 * getFirstfocus();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getFirstFocus()
	{
		return this.firstfocus;
	}


	/**
	 * 新規処理利用可能区分に対するセッター<br>
	 * setInsertflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setInsertFlg(String insertflg)
	{
		this.insertflg = insertflg;
		return true;
	}
	/**
	 * 新規処理利用可能区分に対するゲッター<br>
	 * getInsertflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getInsertFlg()
	{
		return this.insertflg;
	}


	/**
	 * 更新処理利用可能区分に対するセッター<br>
	 * setUpdateflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateFlg(String updateflg)
	{
		this.updateflg = updateflg;
		return true;
	}
	/**
	 * 更新処理利用可能区分に対するゲッター<br>
	 * getUpdateflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getUpdateFlg()
	{
		return this.updateflg;
	}


	/**
	 * 削除処理利用可能区分に対するセッター<br>
	 * setDeleteflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setDeleteFlg(String deleteflg)
	{
		this.deleteflg = deleteflg;
		return true;
	}
	/**
	 * 削除処理利用可能区分に対するゲッター<br>
	 * getDeleteflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getDeleteFlg()
	{
		return this.deleteflg;
	}


	/**
	 * 照会処理利用可能区分に対するセッター<br>
	 * setReferenceflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setReferenceFlg(String referenceflg)
	{
		this.referenceflg = referenceflg;
		return true;
	}
	/**
	 * 照会処理利用可能区分に対するゲッター<br>
	 * getReferenceflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getReferenceFlg()
	{
		return this.referenceflg;
	}


	/**
	 * CSV処理利用可能区分に対するセッター<br>
	 * setCsvflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setCsvFlg(String csvflg)
	{
		this.csvflg = csvflg;
		return true;
	}
	/**
	 * CSV処理利用可能区分に対するゲッター<br>
	 * getCsvflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getCsvFlg()
	{
		return this.csvflg;
	}


	/**
	 * 印刷処理利用可能区分に対するセッター<br>
	 * setPrintflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setPrintFlg(String printflg)
	{
		this.printflg = printflg;
		return true;
	}
	/**
	 * 印刷処理利用可能区分に対するゲッター<br>
	 * getPrintflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getPrintFlg()
	{
		return this.printflg;
	}


	/**
	 * 前画面IDに対するセッター<br>
	 * setBeforeDispId("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setBeforeDispId(String before_disp_id)
	{
		this.before_disp_id = before_disp_id;
		return true;
	}
	/**
	 * 前画面IDに対するゲッター<br>
	 * getBeforeDispId();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getBeforeDispId()
	{
		return this.before_disp_id;
	}


	/**
	 * 現画面URLに対するセッター<br>
	 * setDispUrl("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setDispUrl(String disp_url)
	{
		this.disp_url = disp_url;
		return true;
	}
	/**
	 * 現画面URLに対するゲッター<br>
	 * getDispUrl();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getDispUrl()
	{
		return this.disp_url;
	}


	/**
	 * チェック処理判断に対するセッター<br>
	 * setCheckflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setCheckFlg(String checkflg)
	{
		this.checkflg = checkflg;
		return true;
	}
	/**
	 * チェック処理判断に対するゲッター<br>
	 * getCheckflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getCheckFlg()
	{
		return this.checkflg;
	}


	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * setExistflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setExistflg(String existflg)
	{
		this.existflg = existflg;
		return true;
	}
	/**
	 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * getExistflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getExistflg()
	{
		return this.existflg;
	}


	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
	 * setSearcherrorflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setSearcherrorflg(String searcherrorflg)
	{
		this.searcherrorflg = searcherrorflg;
		return true;
	}
	/**
	 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
	 * getSearcherrorflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getSearcherrorflg()
	{
		return this.searcherrorflg;
	}


	/**
	 * 更新処理内容配列に対するゲッター<br>
	 * getMeisai();　戻り値　更新処理内容配列<br>
	 * @return List 更新処理内容配列
	 */
	public List getUpdateProcessLst() {
		return updateprocesslst;
	}

	/**
	 * 更新処理内容配列に対するセッター<br>
	 * setYukoChkLst(List);<br>
	 * @param List 設定する更新処理内容配列
	 */
	public void setUpdateProcessLst(List list) {
		updateprocesslst = list;
	}

	/**
	 * 更新処理内容に対するセッター<br>
	 * setUpdateprocessflg("文字列");<br>
	 * @param String 設定する文字列
	 */
	public boolean setUpdateProcessFlg(String updateprocessflg)
	{
		this.updateprocessflg = updateprocessflg;
		return true;
	}
	/**
	 * 更新処理内容に対するゲッター<br>
	 * getUpdateprocessflg();　戻り値　文字列<br>
	 * @return String 文字列
	 */
	public String getUpdateProcessFlg()
	{
		return this.updateprocessflg;
	}

	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor)
	{
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor()
	{
		return this.CtrlColor;
	}

	/**
	 * 明細1行を格納するためのBeanに対するセッター<br>
	 * @return meisaiBean
	 */
	public mst290401_KeepMeisaiBean getMeisaiBean() {
		
		return this.meisaiBean;
	}

	/**
	 * 明細1行を格納するためのBeanに対するゲッター<br>
	 * @param bean
	 */
	public void setMeisaiBean(mst290401_KeepMeisaiBean bean) {
		this.meisaiBean = bean;
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

	/**
	 * HankuKanjiRnに対するセッターとゲッターの集合
	 */ 
	public boolean setHankuKanjiRn(String kanji_Na)
	{
		this.kanji_na = kanji_Na;
		return true;
	}
	public String getHankuKanjiRn()
	{
		return cutString(this.kanji_na,HANKU_KANJI_RN_MAX_LENGTH);
	}
	public String getHankuKanjiRnString()
	{
		return "'" + cutString(this.kanji_na,HANKU_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHankuKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na,HANKU_KANJI_RN_MAX_LENGTH));
	}
	
	
	/**
	 * hinmei_kanji_rnに対するセッターとゲッターの集合
	 */ 
	public boolean setHinmeiKanjiRn(String hinmei_kanji_rn)
	{
		this.hinmei_kanji_rn = hinmei_kanji_rn;
		return true;
	}
	public String getHinmeiKanjiRn()
	{
		return cutString(this.hinmei_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH);
	}
	public String getHinmeiKanjiRnString()
	{
		return "'" + cutString(this.hinmei_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_rn,KANRI_KANJI_RN_MAX_LENGTH));
	
	}
	
	/**
	 * @return
	 */
	public int getInitRows() {
		return InitRows;
	}

	/**
	 * @param i
	 */
	public void setInitRows(int i) {
		InitRows = i;
	}
	
	/**
	 * MeisaiList
	 * @param bh
	 */
	public void setMeisaiList(BeanHolder bh) {	
		this.meisaiList = bh.getBeanList();
		this.meisaiBh = new BeanHolder();
		this.meisaiBh = bh;
//			this.meisaiBh.setRowsInPage(this.MAX_ROWS_LENGTH);		
	}
	
	/**
	 * getBeanHolder
	 * @return meisaiBh
	 */
	public BeanHolder getMeisaiBh() {
		return this.meisaiBh;
	}

	/**
	 * setBeanHolder
	 * @param BeanHolder bh
	 */
	public void setMeisaiBh(BeanHolder bh) {
		this.meisaiBh = bh;
	}
	
	/**
	 * @return
	 */
	public boolean getMeisyo_flg() {
		return meisyo_flg;
	}

	/**
	 * @param b
	 */
	public void setMeisyo_flg(boolean b) {
		meisyo_flg = b;
	}

	/**
	 * @return
	 */
	public int getInitMeisai() {
		return InitMeisai;
	}

	/**
	 * @param i
	 */
	public void setInitMeisai(int i) {
		InitMeisai = i;
	}

}
