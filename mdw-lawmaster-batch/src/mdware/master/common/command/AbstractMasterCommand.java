package mdware.master.common.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import jp.co.vinculumjapan.stc.ptier.AbstractCommand;
import mdware.common.bean.SystemControlBean;
import mdware.common.dictionary.MasterUseKbDictionary;
import mdware.common.util.system.MDWareSystemControlUtil;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.util.RMSTDATEUtil;
import mdware.portal.bean.MdwareUserSessionBean;
//↓↓2007.02.05 H.Yamamoto カスタマイズ修正↓↓
import mdware.master.util.RMasterControlUtil;
import mdware.master.util.bean.RMasterControlBean;
//↑↑2007.02.05 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: AbstractMasterCommand</p>
 * <p>説明: MASTERの基底クラスです</p>
 * <p>著作権: Copyright (c) 2002-2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author S.Deguchi(MJC)
 * @version 1.0 (2003.06.10) (MJC)Deguchi  AbstractIstCommand(author M.Ashizawa) より 初版作成
 * @version 1.1 (2005.05.11) shimoyama マスタ管理システム用に修正
 * @version 1.2 (2006.09.18) Tanigawa  20:00～23:00間、特定の画面のみ遷移できる様に修正
 * @version 1.3 (2006.09.19) Tanigawa  本部投入数量確認画面のみ、ログインせずに遷移できる様に修正
 * @version 1.4 (2006.10.04) Tanigawa  障害票№0123対応 バイヤーメニュー画面も、20:00～23:00間、遷移できる様に修正
 * @version 1.5 (2006.11.14) Nakazawa	幾多の修正でコメントがあまりにも多すぎて可読性が低下しているためコメントを大幅削除
 * 										DBへのアクセスが4回あるので1回で取得するように修正、あとは全体的に無駄なコードを削除
 */
abstract public class AbstractMasterCommand extends AbstractCommand {

	/**
	 * 使用する接続先データベース名です。
	 */
	protected static final String RB_DATABASE_NAME	= "rbsite_ora";

	/**
	 * ログインユーザの情報を保持する Bean です。
	 */
	protected MdwareUserSessionBean sysSosasyaBean_ = null;

//	↓↓2007.02.05 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * マスタ制御情報を保持する Bean です。
	 */
	protected RMasterControlBean mstCtrlBean_ = null;
//	↑↑2007.02.05 H.Yamamoto カスタマイズ修正↑↑
	
	/**
	 * 空文字定数 "" を保持します。
	 */
	protected	static	final	String BLANK 		= "";
	
	private 	static final	String KEY_JOB_ID   = "JobID";
	private 	static final 	String KEY_URL_ID   = "UrlID";

	/**
	 * セション無効時の遷移先 URL ID
	 */
	private static final String noSessionUrl_ = urls.getUrl( mdware.portal.value.PortalAlarmFinalValue.URLID_SESSION_ERROR );

	//ログインユーザの利用ユーザコード（利用ユーザＩＤ）
	private String userId_ = null;

	//画面出力メッセージ用 InfoStringBean を保持する List
	private List infoStringBeanList_ = new ArrayList();

	//エラー保持フラグ
	private boolean errorExistFlag_ = false;

	// moveposフラグ
	protected String movepos = BLANK;

	/**
	 * ログインユーザの利用ユーザコード（利用ユーザＩＤ）を返します。
	 */
	protected String getUserId() {
		return this.userId_;
	}

	/**
	 * doProcess() の実装です。
	 * @return String VIEWのJSPのURL
	 * @throws IOException
	 * @throws ServletException
	 * @deprecated doProcessIst(String) を使用してください。
	 */
	protected String doProcess() throws IOException, ServletException {

		String url      = BLANK;        // 遷移先 URL を格納します。
		String urlId    = BLANK;        // 遷移先 URL ID を格納します。

		// システム稼動時間内かどうかをチェック
		SystemControlBean scBean = MDWareSystemControlUtil.getSystemControlBean();
		String mstUseFgStr = scBean.getMstUseFg();
		if( mstUseFgStr == null ){
			return urls.getUrl("mdw000001_TimeError");
		}

//		↓↓2007.02.05 H.Yamamoto カスタマイズ修正↓↓
		//制御情報をコマンド起動時に取得しておく
		RMasterControlUtil.getMstCtrlBeanFromDataBase();
		mstCtrlBean_ = RMasterControlUtil.getMstCtrlBean();
		if( mstCtrlBean_ == null ){
			stcLog.getLog().info("マスタ制御情報の取得に失敗しました。");
			return noSessionUrl_;
		}
//		↑↑2007.02.05 H.Yamamoto カスタマイズ修正↑↑
		
		// ===BEGIN=== Mod by Tanigawa 2006/9/18 特定の画面だけ20:00～23:00間も動作する様に修正
		//			   Mod by Tanigawa 2006/9/22 時間外メッセージをマスタ使用可能フラグを見て変更する
		//システム稼働時間外の場合
		if ( !mstUseFgStr.trim().equals(MasterUseKbDictionary.KA.getCode() ) ) {

			//純粋にシステム稼動時間外の場合エラー
			if ( mstUseFgStr.trim().equals(MasterUseKbDictionary.FUKA.getCode() ) ) {
				return urls.getUrl("mdw000001_TimeError");
			}

			//システム稼動時間外だけど、一部遷移可の場合
			if ( mstUseFgStr.trim().equals(MasterUseKbDictionary.ICHIBUKA.getCode()) ) {
				String strJobID = dataHolder.getParameter(KEY_JOB_ID);
				//一部遷移可な画面へのJobIDか否かを判定(true：遷移可能な画面へのJobID、false：遷移不可能な画面へのJobID)
				boolean bIchibukaGamen = strJobID.equals("mst380101_ExcelUploadInit")             || //提案シートアップロード・初期表示時
										  strJobID.equals("mst380201_ExcelUploadConfirm")          || //提案シートアップロード・確認ボタン押下時
										  strJobID.equals("mst380301_ExcelUploadReg")              || //提案シートアップロード・検索ボタン押下時
										  strJobID.equals("mst380401_ExcelUploadCsv")              || //提案シートアップロード・詳細書出ボタン押下時
										  strJobID.equals("mst380101_UploadChangePage")            || //提案シートアップロード・ページ切替時
										  strJobID.equals("mstA70101_TorokuhyoBYSyoninInit")       || //商品提案シートバイヤー承認・初期表示時
										  strJobID.equals("mstA70201_TorokuhyoBYSyoninRetrieve")   || //商品提案シートバイヤー承認・検索ボタン押下時
										  strJobID.equals("mstA70301_TorokuhyoBYSyoninEachUpdate") || //商品提案シートバイヤー承認・確定ボタン押下時
										  strJobID.equals("mstA70401_TorokuhyoBYSyoninEachDelete") || //商品提案シートバイヤー承認・削除ボタン押下時
										  strJobID.equals("mstA70501_TorokuhyoBYSyoninCsv")        || //商品提案シートバイヤー承認・詳細書出ボタン押下時
										  strJobID.equals("mstA70601_TorokuhyoBYSyoninChangePage") || //商品提案シートバイヤー承認・ページ切替時
										  strJobID.equals("mst370101_ExcelDownloadInit")           || //(提案シート用)商品マスタダウンロード・初期表示
										  strJobID.equals("mst370201_ExcelDownloadSearch")         || //(提案シート用)商品マスタダウンロード・検索ボタン押下時、ページ切替時
										  strJobID.equals("mst370301_ExcelDownloadCsv")			   || //(提案シート用)商品マスタダウンロード・商品マスタ書出時
										  //ADD by Tanigawa 2006/10/04 障害票№0123対応 遷移可能な画面にメニューを追加 START
										  strJobID.equals("mst030101_GyomuMenuA08")			       || //タグ衣料メニュー画面
										  strJobID.equals("mst030201_GyomuMenuA07")			       || //実用衣料メニュー画面
										  strJobID.equals("mst030301_GyomuMenuGro")			       || //グロバラメニュー画面
										  strJobID.equals("mst030401_GyomuMenuFre")			       || //デイリーメニュー画面
										  strJobID.equals("mst030501_GyomuMenuAdm");		          //管理者メニュー画面
										  //ADD by Tanigawa 2006/10/04 障害票№0123対応 END

				//システム稼動時間外で、一部遷移可な画面でもない場合はエラー
				if( !bIchibukaGamen ){
					return urls.getUrl("mdw000002_TimeErrorIchibuka");	//純粋にシステム時間外の場合に表示するJSPとは、異なるJSPを表示
				}
			}
		}
		// === END === Mod by Tanigawa 2006/9/18 特定の画面だけ20:00～23:00間も動作する様に修正
		//			   Mod by Tanigawa 2006/9/22 時間外メッセージをマスタ使用可能フラグを見て変更する

		//userSessionの取得
		sysSosasyaBean_ = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");
		
		// ===BEGIN=== Mod by Tanigawa 2006/9/18 店舗ユーザ確認用に本部投入数量確認画面だけ、ログインなしでも遷移可にする
		//ログインユーザIDの取り出し
		if( sysSosasyaBean_ != null ) {
			this.userId_ = sysSosasyaBean_.getUserId();	
			//ユーザセッションが存在しても中身がないとエラー
			if( sysSosasyaBean_.getUserId() == null || sysSosasyaBean_.getUserId().trim().equals("") ){
				if( !(dataHolder.getParameter(KEY_JOB_ID).substring(0,5).equals("mstA6")) ){
					stcLog.getLog().info("不正な画面遷移を検出しました。（ログインユーザーコード無し）");
					return noSessionUrl_;
				}
			}
		} else {
			//ログインユーザ情報が存在せず、本部投入数量確認画面へのJobIDでもない場合
			//セッションが切れている旨のメッセージを表示
			if( !(dataHolder.getParameter(KEY_JOB_ID).equals("mstA60101_HonbuTonyuInit")) ){
				stcLog.getLog().info("セションが切断されています。");
				return noSessionUrl_;
			}
			stcLog.getLog().info("店舗ユーザがログインせずに本部投入数量確認画面へ遷移する試みを検知しました。"+
								"例外的に、ログイン処理を省きます。");
			this.userId_ = "未ログイン店舗ユーザ";
		}
		// === END === Mod by Tanigawa 2006/9/18 店舗ユーザ確認用に本部投入数量確認画面だけ、ログインなしでも遷移可にする

		// このコマンドクラスを呼び出した画面の URL ID を取得します。
		String requestJobId = dataHolder.getParameter(KEY_JOB_ID);
		if (requestJobId == null) {
			requestJobId = BLANK;
		}
		String requestUrlId = dataHolder.getParameter(KEY_URL_ID);
		if (requestUrlId == null) {
			requestUrlId = BLANK;
		}

		//パフォーマンス改善の為、時刻をコマンド起動時に取得しておく
		RMSTDATEUtil.getMasDateDtFromDataBase( scBean.getMstDateDt() );
		mst000401_LogicBean.getYukoDtRangeFromDataBase( scBean.getMstDateDt() );

		//各個別業務ロジックを実行し戻値をurlとして設定する
		url = urls.getUrl( doProcessMaster( requestJobId, requestUrlId ) );

		//各必須セッションのセットを行います
		sessionManager.setAttribute( "userSession", sysSosasyaBean_ );
		
		return url;
	}

	/**
	 * 各クラス別の処理実行メソッド
	 * @param   requestJobId 呼び出された時の 	JOB ID
	 * @param   requestUrlId 呼出元画面の 		URL ID
	 * @return  次画面の URL ID
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract String doProcessMaster(String requestJobId, String requestUrlId) throws ServletException, IOException;
}