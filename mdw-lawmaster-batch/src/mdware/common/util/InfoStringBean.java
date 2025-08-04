package mdware.common.util;


/**
 * <p>タイトル: InfoStringBean クラス</p>
 * <p>説明: メッセージ情報（ステータス、メッセージ、対象エレメント名）を保持するクラスです。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002-2003</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author RB Site
 * @author M.Ashizawa
 * @version 1.0 deguchi(MJC)  RB Siteと原材料版より新規作成
 */
public class InfoStringBean {

	/**
	 * ステータスコード（インフォメーション）の定数値です。
	 */
	public static final int INFO    = 0;

	/**
	 * ステータスコード（警告）の定数値です。
	 */
	public static final int WARN    = 1;

	/**
	 * ステータスコード（エラー）の定数値です。
	 */
	public static final int ERROR   = 2;

	/**
	 * ステータスコード（致命的エラー）の定数値です。
	 */
	public static final int FATAL   = 9;

	// このオブジェクトが保持するステータスコード
	private int status = INFO;

	// このオブジェクトが保持するメッセージ
	private String infoString = null;

	// このオブジェクトが保持するメッセージ対象エレメント名
	private String elementName = null;


	/**
	 * InfoStringBean のコンストラクタ。
	 *
	 * @param status        ステータス
	 * @param infoString    メッセージ
	 */
	public InfoStringBean( int status, String infoString ) {
		this.status = status;
		this.infoString = infoString;
	}

	/**
	 * InfoStringBean のコンストラクタ。
	 *
	 * @param status        ステータス
	 * @param infoString    メッセージ
	 * @param elementName   対象エレメント名
	 */
	public InfoStringBean( int status, String infoString, String elementName ) {
		this.status      = status;
		this.infoString  = infoString;
		this.elementName = elementName;
	}


	/**
	 * このオブジェクトが保持するステータスコードを返します。
	 *
	 * @return  ステータスコード
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * このオブジェクトが保持するメッセージを返します。
	 *
	 * @return  メッセージ
	 */
	public String getInfoString() {
		return infoString;
	}

	/**
	 * このオブジェクトが保持するメッセージ対象エレメント名を返します。
	 *
	 * @return  画面のエレメント名
	 */
	public String getElementName() {
		return elementName;
	}

}