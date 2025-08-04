package mdware.portal.value;

/**
 * <P>タイトル : ポータルアラーム使用の固定値集合体</P>
 * <P>説明 : Dictionary系以外の固定値を集めています</P>
 * <P>集めた理由はＰＧ固有の値が分散してしまうのを回避したかったためです</P>
 * <P>著作権: Copyright (c) 2006</p>					
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author VJC K.Nakazawa
 * @version 1.0 (2006.06.02) 初版作成
 * @see なし				
 */
public interface PortalAlarmFinalValue {
	
	//---------------------------------------------------------
	// JOBIDの固有値
	//---------------------------------------------------------
		
	//初期化(取引先)
	public static final String JOBID_TORIHIKISAKI_ENTRY				= "ptl010201_TorihikisakiLoad";
	//ログイン(取引先)
	public static final String JOBID_TORIHIKISAKI_LOGIN 				= "ptl010201_TorihikisakiLogin";
	//練習用サイト初期化(取引先)
	public static final String JOBID_TORIHIKISAKI_PRACTICE_LOGIN 	= "ptl010202_TorihikisakiPracticeLoginCommand";
	//MyPage表示(取引先)
	public static final String JOBID_TORIHIKISAKI_MYPAGE_CLICK 		= "ptl020201_TorihikisakiMyPage";
	//業務タブクリック(取引先)
	public static final String JOBID_TORIHIKISAKI_GYOMU_TAB_CLICK 	= "ptl020201_TorihikisakiGyomuTabClick";
	//販売タブクリック(取引先)
	public static final String JOBID_TORIHIKISAKI_HANBAI_TAB_CLICK 	= "ptl020201_TorihikisakiHanbaiTabClick";
	//マニュアルタブクリック(取引先)
	public static final String JOBID_TORIHIKISAKI_MANUAL_TAB_CLICK 	= "ptl020201_TorihikisakiManualTabClick";
	//お知らせページ切り替え(取引先＆バイヤー共通)
	public static final String JOBID_OSHIRASE_PAGE_CHANGE 			= "ptl040202_PortalOshirasePageChange";
	//お知らせ詳細(取引先＆バイヤー共通)
	public static final String JOBID_OSHIRASE_DETAIL		 			= "ptl040203_PortalOshiraseDetail";
	
	//初期化(バイヤー)
	public static final String JOBID_BUYER_ENTRY			= "ptl010101_BuyerSsoEntry";
	//ログイン画面表示(バイヤー)
	public static final String JOBID_BUYER_LOAD 			= "ptl010102_BuyerLoad";
	//ログインボタン押下(バイヤー)
	public static final String JOBID_BUYER_LOGIN 		= "ptl010103_BuyerLogin";
	//MyPage表示(バイヤー)
	public static final String JOBID_BUYER_MYPAGE_CLICK 	= "ptl020101_BuyerMyPageLoad";
	//メニュークリック(バイヤー)
	public static final String JOBID_BUYER_MENU_CLICK   	= "ptl020101_BuyerMenuClick";
	//タブクリック(バイヤー)
	public static final String JOBID_BUYER_TAB_CLICK 	= "ptl020101_BuyerTabClick";
	
	//アラーム初期化(取引先＆バイヤー共通)
	public static final String JOBID_ALARM_INIT 			= "ptl030001_PortalAlarmDisplay";
	//アラームページ切り替え(取引先＆バイヤー共通)
	public static final String JOBID_ALARM_PAGE_CHANGE 	= "ptl030002_PortalAlarmPageChange";
	//アラーム承認済ボタン押下(取引先＆バイヤー共通)
	public static final String JOBID_ALARM_SYONIN_CLICK 	= "ptl030003_PortalAlarmDelete";

	//ポータルお取引先管理(初期化)
	public static final String JOBID_TORIHIKISAKI_USERKANRI_INIT			= "ptl060401_PortalTorihikisakiUserKanri_Init";
	//ポータルお取引先管理(検索)
	public static final String JOBID_TORIHIKISAKI_USERKANRI_SEARCH 		= "ptl060401_PortalTorihikisakiUserKanri_Search";
	//ポータルお取引先管理(検索(次の10件、前の10件など))
	public static final String JOBID_TORIHIKISAKI_USERKANRI_SEARCHMOVE	= "ptl060401_PortalTorihikisakiUserKanri_SearchMove";
	
	//ポータルお取引先新規登録(初期化)
	public static final String JOBID_TORIHIKISAKI_USERTOUROKU_INIT			= "ptl060402_PortalTorihikisakiUserTouroku_Init";
	//ポータルお取引先新規登録(登録)
	public static final String JOBID_TORIHIKISAKI_USERKANRI_REGIST 		= "ptl060401_PortalTorihikisakiUserKanri_Regist";
	//ポータルお取引先更新(初期化)
	public static final String JOBID_TORIHIKISAKI_USERHENKOU_INIT	= "ptl060402_PortalTorihikisakiUserHenkou_Init";
	//ポータルお取引先更新(更新)
	public static final String JOBID_TORIHIKISAKI_USERKANRI_UPDATE	= "ptl060401_PortalTorihikisakiUserKanri_Update";
	//ポータルお取引先更新(削除)
	public static final String JOBID_TORIHIKISAKI_USERKANRI_DELETE	= "ptl060401_PortalTorihikisakiUserKanri_Delete";
	//ポータルお取引先新規登録・更新、削除ボタン押下時、戻るボタン押下時
	public static final String JOBID_TORIHIKISAKI_USERKANRI_RETURN	= "ptl060401_PortalTorihikisakiUserKanri_Return";

	//ポータルお取引先ヘルプ(初期化)
	public static final String JOBID_OTORIHIKISAKI_HELP_INIT			= "ptl070401_OtorihikisakiHelpInit";
	//ポータルお取引先ヘルプ(検索)
	public static final String JOBID_OTORIHIKISAKI_HELP_SEARCH 		= "ptl070401_OtorihikisakiHelpSearch";
	//ポータルお取引先ヘルプ(検索(次の10件、前の10件など))
	public static final String JOBID_OTORIHIKISAKI_HELP_PAGE_CHANGE	= "ptl070401_OtorihikisakiHelpPageChange";
	//ポータルお取引先ヘルプログイン
	public static final String JOBID_OTORIHIKISAKI_HELP_LOGIN		= "ptl070401_OtorihikisakiHelpLogin";
	
	//ポータルお取引先サブ検索(初期化)
	public static final String JOBID_OTORIHIKISAKI_SEARCH_INIT			= "ptl060403_OtorihikisakiSearchInit";
	//ポータルお取引先サブ検索(検索)
	public static final String JOBID_OTORIHIKISAKI_SEARCH_SEARCH 		= "ptl060403_OtorihikisakiSearchSearch";
	//ポータルお取引先サブ検索(検索(次の10件、前の10件など))
	public static final String JOBID_OTORIHIKISAKI_SEARCH_PAGE_CHANGE	= "ptl060403_OtorihikisakiSearchPageChange";
	
	//---------------------------------------------------------
	// URLIDの固有値
	//---------------------------------------------------------
	
	//ログイン(取引先)
	public static final String URLID_TORIHIKISAKI_LOGIN 	= "ptl010201_TorihikisakiLogin";
	//マイページ(取引先)
	public static final String URLID_TORIHIKISAKI_MYPAGE	= "ptl020201_TorihikisakiMyPage";
	//生鮮EDIリダイレクト(取引先)
	public static final String URLID_TORIHIKISAKI_SEISEN	= "ptl020202_TorihikisakiSeisenRedirect";
	//生鮮EDIリダイレクト(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_SEISEN	= "ptl020202_torihikisaki_seisen_redirect.jsp";
	//練習用サイト遷移画面(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_PRACTICE = "ptl020203_torihikisaki_practice.jsp";
	//練習用サイト用画面(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_PRACTICE_SIGN = "ptl020204_torihikisaki_practice_sign.jsp";
	//業務タブ表示(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_GYOMU  = "ptl020205_torihikisaki_gyomu.jsp";
	//販売タブ表示(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_HANBAI = "ptl020206_torihikisaki_hanbai.jsp";
	//マニュアルタブ表示(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_MANUAL = "ptl020207_torihikisaki_manual.jsp";
	//ログアウト(取引先)
	public static final String URLID_TORIHIKISAKI_LOGOUT	= "ptl010202_TorihikisakiLogout";
	//ログアウト(取引先＆バイヤー共通、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_LOGOUT	= "ptl010202_torihikisaki_logout.jsp";
	//資料集(取引先＆バイヤー共通)
	public static final String URLID_SHIRYOSYU_DESPLAY	= "ptl050001_SiryosyuItiran";
	//資料集(取引先＆バイヤー共通、JSP名直指定)
	public static final String JSPNM_SHIRYOSYU_DESPLAY	= "ptl050001_siryosyu_itiran.jsp";
	//お知らせ(取引先)
	public static final String URLID_TORIHIKISAKI_OSHIRASE			= "ptl040201_TorihikisakiOshirase";
	//お知らせ(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_OSHIRASE			= "ptl040201_torihikisaki_oshirase.jsp";
	//お知らせ詳細(取引先)
	public static final String URLID_TORIHIKISAKI_OSHIRASE_DETAIL	= "ptl040202_TorihikisakiOshiraseDetail";
	//お知らせ詳細(取引先、JSP名直指定)
	public static final String JSPNM_TORIHIKISAKI_OSHIRASE_DETAIL 	= "ptl040202_torihikisaki_oshirase_detail.jsp";
	
	public static final String JSPNM_TORIHIKISAKI_SHIRYO 	= "ptl000104_torihikisaki_siryosyu.jsp";

	//ポータルお取引先ID・PW管理検索画面
	public static final String URL_ID_PORTAL_TORIHIKISAKI_USER_KANRI 	= "ptl060401_PortalTorihikisakiUserKanriInit";
	//ポータルお取引先新規登録画面
	public static final String URL_ID_PORTAL_TORIHIKISAKI_USER_TOUROKU 	= "ptl060402_PortalTorihikisakiUserKanri_Regist";
	//ポータルお取引先更新画面
	public static final String URL_ID_PORTAL_TORIHIKISAKI_USER_KOUSIN 	= "ptl060402_PortalTorihikisakiUserKanri_Update";
	//ポータルお取引先ヘルプ画面
	public static final String URL_ID_PORTAL_OTORIHIKISAKI_HELP 			= "ptl070401_OtorihikisakiHelp";
	//ポータルお取引先検索サブ画面
	public static final String URL_ID_PORTAL_OTORIHIKISAKI_SEARCH 		= "ptl060403_OtorihikisakiSearch";
	//ポータルお取引先ヘルプリダイレクトログイン画面
	public static final String URL_ID_PORTAL_OTORIHIKISAKI_HELP_REDIRECT	= "ptl070402_OtorihikisakiHelpRedirect";
	//ポータルお取引先ヘルプリダイレクトログイン画面(JSP名直指定)
	public static final String JSPNM_PORTAL_OTORIHIKISAKI_HELP_REDIRECT	= "ptl070402_OtorihikisakiHelpRedirect.jsp";
	
	//初期化(バイヤー)
	public static final String URLID_BUYER_SSO	= "ptl010101_BuyerInit";
	//ログイン(バイヤー)
	public static final String URLID_BUYER_LOGIN  = "ptl010102_BuyerLogin";
	//生鮮EDIリダイレクト(取引先、JSP名直指定)
	public static final String JSPNM_BUYER_SEISEN = "ptl020102_buyer_seisen_redirect.jsp";
	//ログアウト(バイヤー)
	public static final String URLID_BUYER_LOGOUT = "ptl010103_BuyerLogout";
	//ログアウト(取引先＆バイヤー共通、JSP名直指定)
	public static final String JSPNM_BUYER_LOGOUT = "ptl010103_buyer_logout.jsp";
	//マイページ(バイヤー)
	public static final String URLID_BUYER_MYPAGE = "ptl020101_BuyerMyPage";
	//資料集(バイヤー)
	public static final String URLID_BUYER_SHIRYO = "ptl000005_BuyerShiryosyu";
	//資料集(バイヤー、JSP名直指定)
	public static final String JSPNM_BUYER_SHIRYO = "ptl000005_buyer_siryosyu.jsp";
	//生鮮ＥＤＩリダイレクト
	public static final String URLID_BUYER_SEISEN = "ptl020102_BuyerSeisenRedirect";

	//セッションエラー(全ユーザ共通)
	public static final String URLID_SESSION_ERROR = "ptl010001_SessionError";
	//アラーム(取引先＆バイヤー共通)
	public static final String URLID_PORTAL_ALARM = "ptl030001_PortalAlarm";
	//アラーム(取引先＆バイヤー共通、JSP名直指定)
	public static final String JSPNM_PORTAL_ALARM = "ptl030001_alarm_info.jsp";
	
	//---------------------------------------------------------
	// メッセージ定義
	//---------------------------------------------------------
	
	//ログイン情報が渡されていないため利用出来ません。もう一度ブラウザを閉じて最初から行ってください。
	public static final String MSG_EP000000_ROGIN_INFO_NO_EXIST	= "EP000000";
	//部門コードを入力して下さい。
	public static final String MSG_EP000001_BUMON_CD_NO_INPUT	= "EP000001";
	//部門コードが間違っています。もう一度入力してください。
	public static final String MSG_EP000002_BUMON_CD_FAIL		= "EP000002";
	//ユーザまたはパスワードが間違っているか期限が切れています。管理者に問い合わせてください。
	public static final String MSG_EP000003_USER_OR_PASSWD_FAIL	= "EP000003";
	//現在繋がりにくくなっています。少し時間をおいて再度検索を行ってください。
	public static final String MSG_EP000005_TIME_OUT_ERROR	= "EP000005";
	//ユーザが存在しないか期限が切れています。管理者に問い合わせてください。
	public static final String MSG_EP000007_BUYER_LOGIN_FAIL	= "EP000007";
	//取引先IDには、半角英数字を9桁で入力して下さい。
	public static final String MSG_EP000008_TORIHIKISAKI_ID_CHECK_FAIL	= "EP000008";
	//取引先名称は、20文字以内で入力して下さい。
	public static final String MSG_EP000009_TORIHIKISAKI_NA_CHECK_FAIL	= "EP000009";
	//不正な遷移が検出されました。もう一度最初からやり直してください。
	public static final String MSG_EP000012_ACCESS_PATH_FAIL	= "EP000012";
	//エラーが発生しました。もう一度同じ操作を実行してみて下さい。
	public static final String MSG_0015_ERROR_EXIST	= "0015";
	//検索処理でエラーが発生しました。
	public static final String MSG_0007_DB_SEARCH_ERROR	= "0007";
	//登録処理でエラーが発生しました。
	public static final String MSG_0009_DB_INSERT_ERROR	= "0009";
	//更新処理でエラーが発生しました。
	public static final String MSG_0010_DB_UPDATE_ERROR	= "0010";
	//削除処理でエラーが発生しました。
	public static final String MSG_0011_DB_DELETE_ERROR	= "0011";
	//検索データが存在しません。検索条件を変えて再度実行して下さい。
	public static final String MSG_IP000001_SEARCH_ZERO	= "IP000001";
	//件のデータが見つかりました。
	public static final String MSG_0029_DB_SEARCH_RESULT_NUM	= "IP000002";
	
	//---------------------------------------------------------
	// ＤＢカラムの固有値
	//---------------------------------------------------------
	
	//アラームIDのその他コード固定値
	public static final String DB_ALARM_ID_OTHERS = "999999999999999999";
	
	//名称CTFマスタの種別コードの部門固定値
	public static final String DB_NAME_CTF_SYUBETU_BUMON = "0020";
	
	//---------------------------------------------------------
	// ＰＧ内のみ使用の固定値
	//---------------------------------------------------------
	
	//初期にバイヤーか特殊ユーザかを見分ける
	public static final String NOTES_SSO_BUYER_USER 		= "0";
	public static final String NOTES_SSO_SPECIAL_USER	= "1";
	
	//メッセージ表示用セッション名称
	public static final String INFO_STRING_LIST = "infoStringList";
	
	//DBスキーマ名
	public static final String DB_SCHEMA_NAME = "rbsite_ora";
	
	//お知らせファイルDL機能のパス
	public static final String OSHIRASE_FILE_DIRECTRY_PATH = "";
	
	//資料集ファイルDL機能のパス
	public static final String SIRYOSYU_FILE_DIRECTRY_PATH = ""; 
	
	//タイムアウトエラーコード
	public static final int SQL_ERROR_LOCK_TIMEOUT = -913;
	
	//アラーム最大行数
	public static final int PORTAL_ALARM_MAX_LOWS_BUYER 			= 15;
	public static final int PORTAL_ALARM_MAX_LOWS_TORIHIKISAKI 	= 7;
	
	//お知らせ最大行数
	public static final int PORTAL_OSIRASE_MAX_LOWS_TORIHIKISAKI = 3;
	
	//お知らせ管理一覧ページ毎　行数
	public static final int PORTAL_OSIRASE_KANRI_MAX_LOWS = 10;
	
	//
	public static final int PORTAL_TORIHIKISAKI_USER_KANRI_MAX_ROWS = 10;
	
	//お取引先ヘルプ最大行数
	public static final int PORTAL_OTORIHIKISAKI_HELP_MAX_ROWS = 10;
	
	//お取引先検索サブ画面最大行数
	public static final int PORTAL_OTORIHIKISAKI_SEARCH_MAX_ROWS = 10;
	
	//営業企画部でログイン時に自動出力する画面のJobID
	public static final String EIGYO_KIKAKU_DEFAULT_PAGE_JOBID = "hsk103000_TirasiList";

	//本番用練習用を見分けるプロパティファイルの位置と内容
	public static final String SITE_JUDGE_FILE_ADDRESS 	= "";
	public static final String REAL_SITE_STRING			= "real";
	public static final String PRACTICE_SITE_STRING		= "practice";
	
	//生鮮側R_RIYO_USERの取引先のユーザ種別区分
	public static final String SEISEN_USER_SYUBETU_TORIHIKISAKI = "1";
	
	//マニュアル列数
	public static final int MANUAL_MAX_COLUMN = 4;
	//マニュアル最小行数
	public static final int MANUAL_MIN_ROW = 4;
	
	//資料集列数
	public static final int SIRYOSYU_MAX_COLUMN = 3;
	//資料集最小行数
	public static final int SIRYOSYU_MIN_ROW = 6;

	//資料集最大行数
	public static final int SIRYOSYU_MAX_ROW = 50;//←マニュアルでも使用

	//マニュアル区分 マニュアル 0  資料集 1
	public static final int MANUAL_KBN_MANUAL = 0;
	//マニュアル区分 マニュアル 0  資料集 1
	public static final int MANUAL_KBN_SIRYO = 1;
	
	//コンテンツ　　（DT_CONTENTへ格納するアップロードファイル　お知らせ添付、マニュアル、資料集）の最大サイズ(バイト)
	
	public static final int CONTENT_MAX_SIZE=2000000;
	
	//表示区分
	/*
	              バイヤー　取引先　   両方
	生鮮          0         1          2
	非生鮮        3         4          5
	両方          6         7          8
	
	*/
	public static final int HYOJI_KBN_BUY_SEISEN = 0;
	public static final int HYOJI_KBN_TORI_SEISEN = 1;
	public static final int HYOJI_KBN_BOTH_SEISEN = 2;
	public static final int HYOJI_KBN_BUY_HISEISEN = 3;
	public static final int HYOJI_KBN_TORI_HISEISEN = 4;
	public static final int HYOJI_KBN_BOTH_HISEISEN = 5;
	public static final int HYOJI_KBN_BUY_BOTH = 6;
	public static final int HYOJI_KBN_TORI_BOTH = 7;
	public static final int HYOJI_KBN_BOTH_BOTH = 8;
	
	//NEW!の表示を行う日数
	public static final int NEW_STR_DISP_DT = 14;
	
	//取引先新規登録時のデフォルトの有効終了日
	public static final String EXPIRY_END_DT = "29991231";
	
	//登録画面か変更画面のいずれかを判断するための区分　登録 0　変更 1
	public static final int TOUROKUHENKOU_KBN_TOROKU = 0;
	public static final int TOUROKUHENKOU_KBN_HENKOU = 1;
}