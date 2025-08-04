package mdware.common.batch.log;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import mdware.batch.mail.control.BatchMailController;

/**
 * <P>タイトル:ログ実行クラス</P>
 * <P>説明: バッチ用のログを出力します。メッセージ出力時に改行コードが入らないようにするためだけに作成しました。</P>
 * <P>　　　Categoryクラスの肩代わりをしますが、ログ出力メソッド以外のメソッドは使えません。</P>
 * <P>著作権:	Copyright (c) 2001</P>
 * <P>会社名:	Vinculum Japan Corp.</P>
 * <PRE>
 *
 * </PRE>
 * @author Yamanaka
 * @version 1.00 (2003.01.30)
 * @version 2.00 (2004.05.25) 内部改修書No.004対応 yunba
 */

public class CategoryForBatchLog extends Logger {
	private static final String FQCN = CategoryForBatchLog.class.getName();

	private static ForBatchLogFactory fbFactory = new ForBatchLogFactory();

///// 20040423 yunba add start ///// 内部改修書No.004対応
	private BatchMailController mailController = null;	// メール送信オブジェクト
///// 20040423 yunba add end   /////

	protected CategoryForBatchLog(String name) {
		super(name);
	}

	public static Category getInstance(String name) {
		return Logger.getLogger(name, fbFactory);
	}

	/**
	 * debug 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param obj
	 */
	public void debug(Object obj) {

		if (repository.isDisabled(Level.DEBUG_INT))
		  return;

		if (obj instanceof String) {
			String newString = ((String)obj).replaceAll("\r", " ").replaceAll("\n", " ");

			if (Level.DEBUG.isGreaterOrEqual(this.getEffectiveLevel())) {
				forcedLog(FQCN, Level.DEBUG, newString, null);
			}
		} else {
			super.debug(obj);
		}
	}
	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param batch_id
	 * @param batch_na
	 * @param description
	 */
	public void debug(String batch_id, String batch_na, String description) {
		this.debug(batch_id + " " + batch_na + " ： " + description);
	}

	/**
	 * info 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param obj
	 */
	public void info(Object obj) {

		if (repository.isDisabled(Level.INFO_INT))
			return;
		
		if (obj instanceof String) {
			String newString = ((String)obj).replaceAll("\r", " ").replaceAll("\n", " ");

			if (Level.INFO.isGreaterOrEqual(this.getEffectiveLevel())) {
				forcedLog(FQCN, Level.INFO, newString, null);
			}

		} else {
			super.info(obj);
		}
	}
	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param batch_id
	 * @param batch_na
	 * @param description
	 */
	public void info(String batch_id, String batch_na, String description) {
		this.info(batch_id + " " + batch_na + " ： " + description);
	}

	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param obj
	 */
	public void error(Object obj) {

		if (repository.isDisabled(Level.ERROR_INT))
			return;

///// 20040423 yunba add start ///// 内部改修書No.004対応
		if( this.mailController != null ) {
			//forcedLog(FQCN, Level.DEBUG, "読み込んだXMLよりメール配信メソッドを呼び出す。error", null);	// コメントアウト対象
			try {
				this.mailController.sendBatchErrorMail(Level.ERROR_INT, obj);
			} catch (Exception e) {
				forcedLog(FQCN, Level.FATAL, "エラーメール送信処理でエラーが発生しました。", null);
			}
		}
///// 20040423 yunba add end   /////

		if (obj instanceof String) {
			String newString = ((String)obj).replaceAll("\r", " ").replaceAll("\n", " ");

			if (Level.ERROR.isGreaterOrEqual(this.getEffectiveLevel())) {
				forcedLog(FQCN, Level.ERROR, newString, null);
			}
		} else {
			super.error(obj);
		}
	}
	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param batch_id
	 * @param batch_na
	 * @param description
	 */
	public void error(String batch_id, String batch_na, String description) {
		this.error(batch_id + " " + batch_na + " ： " + description);
	}

	/**
	 * fatal 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param obj
	 */
	public void fatal(Object obj) {

		if (repository.isDisabled(Level.FATAL_INT))
			return;

///// 20040423 yunba add start ///// 内部改修書No.004対応
		if( this.mailController != null ) {
			//forcedLog(FQCN, Level.DEBUG, "読み込んだXMLよりメール配信メソッドを呼び出す。fatal", null);	// コメントアウト対象
			try {
				this.mailController.sendBatchErrorMail(Level.FATAL_INT, obj);
			} catch (Exception e) {
				forcedLog(FQCN, Level.FATAL, "エラーメール送信処理でエラーが発生しました。", null);
			}
		}
///// 20040423 yunba add end   /////

		if (obj instanceof String) {
			String newString = ((String)obj).replaceAll("\r", " ").replaceAll("\n", " ");

			if (Level.FATAL.isGreaterOrEqual(this.getEffectiveLevel())) {
				forcedLog(FQCN, Level.FATAL, newString, null);
			}
		} else {
			super.fatal(obj);
		}
	}
	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param batch_id
	 * @param batch_na
	 * @param description
	 */
	public void fatal(String batch_id, String batch_na, String description) {
		this.fatal(batch_id + " " + batch_na + " ： " + description);
	}

	/**
	 * warn 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param obj
	 */
	public void warn(Object obj) {

		if (repository.isDisabled(Level.WARN_INT))
			return;
			
///// 20040423 yunba add start ///// 内部改修書No.004対応
		if( this.mailController != null ) {
			//forcedLog(FQCN, Level.DEBUG, "読み込んだXMLよりメール配信メソッドを呼び出す。warn", null);	// コメントアウト対象
			try {
				this.mailController.sendBatchErrorMail(Level.WARN_INT, obj);
			} catch (Exception e) {
				e.printStackTrace();
				forcedLog(FQCN, Level.FATAL, "エラーメール送信処理でエラーが発生しました。", null);
			}
		}
///// 20040423 yunba add end   /////

		if (obj instanceof String) {
			String newString = ((String)obj).replaceAll("\r", " ").replaceAll("\n", " ");

			if (Level.WARN.isGreaterOrEqual(this.getEffectiveLevel())) {
				forcedLog(FQCN, Level.WARN, newString, null);
			}
		} else {
			super.warn(obj);
		}
	}
	/**
	 * error 引数がStringで改行コードが入っている場合は" "に変換します。
	 * @param batch_id
	 * @param batch_na
	 * @param description
	 */
	public void warn(String batch_id, String batch_na, String description) {
		this.warn(batch_id + " " + batch_na + " ： " + description);
	}


  public static Logger getLogger(String name) {
	return Logger.getLogger(name, fbFactory);
  }

///// 20040423 yunba add start ///// 内部改修書No.004対応
	/**
	 * BatchMailCntrollerのセッター。
	 */
	public void setMailController(BatchMailController batchMailController) {
		this.mailController = batchMailController;
	}
///// 20040423 yunba add end   /////

}