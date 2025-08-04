package mdware.common.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * <p>タイトル: CommonStatusBean クラス</p>
 * <p>説明: セションの有無に関係なくサブシステム共通の情報を保持する Bean です。</p>
 * <p>著作権: Copyright (c) 2002-2003</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author M.Ashizawa
 * @version 1.1 (2003.02.20) M.Ashizawa サブシステム ID を追加。
 * @version 1.0 (2003.01.31) 初版作成
 */
public class CommonStatusBean {

	/**
	 * サブシステム名の定数値です。
	 */
	private static final String SUB_SYSTEM_NAME = "勤怠管理";

	/**
	 * サブシステム名の定数値です。
	 */
	private static final String SUB_SYSTEM_ID   = "0004";

	/**
	 * 曜日（日本語）テーブルです。
	 */
	private static final String[] YOUBI = {"日","月","火","水","木","金","土"};

	/**
	 * 空文字定数。"" を保持します。
	 */
	private static final String BLANK = "";


	/**
	 * サブシステム名を返します。
	 *
	 * @return  サブシステム名の String
	 */
	public static String getSubSystemName() {
		return SUB_SYSTEM_NAME;
	}


	/**
	 * サブシステム ID を返します。
	 *
	 * @return  サブシステム ID の String
	 */
	public static String getSubSystemId() {
		return SUB_SYSTEM_ID;
	}


	/**
	 * APP システム日付を 'yyyy年mm月dd日' フォーマットで返します。
	 * <pre>
	 * 【注意】
	 *   年、月、日の数字の前ゼロは除去されます。
	 * </pre>
	 *
	 * @return  フォーマット変換された APP システム日付の String
	 */
	public static String getCurrentJpnDate() {
		Calendar calendar = GregorianCalendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR));
		sb.append('年');
		sb.append(calendar.get(Calendar.MONTH) + 1);
		sb.append('月');
		sb.append(calendar.get(Calendar.DATE));
		sb.append('日');

		return sb.toString();
	}


	/**
	 * APP システム曜日を返します。
	 *
	 * @return  曜日（日、月、火、水、木、金、土）の String
	 */
	public static String getCurrentYoubi() {
		Calendar calendar = GregorianCalendar.getInstance();
		return YOUBI[ calendar.get(Calendar.DAY_OF_WEEK) - 1 ];
	}


	/**
	 * APP システム日付、または APP システム日時を指定されたフォーマットで返します。
	 * <pre>
	 * 【使用例】
	 *   getCurrentDate("yyyyMMdd")             20030131
	 *   getCurrentDate("yyyy/MM/dd HH:mm:ss")  2003/01/31 12:53:40
	 * </pre>
	 *
	 * @param pattern   日付または日時のフォーマット
	 * @return          APP システム日付、または APP システム日時の String
	 * @see SimpleDateFormat
	 */
	public static String getCurrentDate(String pattern) {
		Calendar calendar = GregorianCalendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format( calendar.getTime() );
	}

	/**
	 * APP システム日付を 'yyyyMMdd' フォーマットで返します。
	 * このメソッドは getCurrentDate("yyyyMMdd") と等価です。
	 *
	 * @return APP システム日付の 8 桁の String
	 */
	public static String getCurrentFlatDate() {
		return getCurrentDate("yyyyMMdd");
	}


	/**
	 * APP システム日時を Oracle の DATE 型に適したフォーマットで返します。
	 * 具体的には以下のような String を返します。<br>
	 * &quot; to_date('20030131 121530','yyyymmdd hh24miss') &quot;<br>
	 * 両サイドには半角のスペースが１文字付加されます（安全の為）。
	 *
	 * @return  Oracle の DATE 型に適した APP システム日時の String
	 */
	public static String getCurrentDate4Oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append(" to_date('");
		sb.append(getCurrentDate("yyyyMMdd HHmmss"));
		sb.append("','yyyymmdd hh24miss') ");
		return sb.toString();
	}

}