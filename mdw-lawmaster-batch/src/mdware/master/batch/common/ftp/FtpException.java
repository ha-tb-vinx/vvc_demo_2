package mdware.master.batch.common.ftp;

/**
 * <p>タイトル: FtpExceptionクラス</p>
 * <p>説明:FTP送信時にエラーが発生した際の例外クラス</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * 
 * @author T.Kuzuhara
 * @version 1.0 (2009/07/02) 初版作成
 */
public class FtpException extends Exception {

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public FtpException(String message) {
		super(message);
	}
	
}
