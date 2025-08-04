package mdware.common.util.jsp;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * RBSITE用ユーティリティー（JSP用クラス）。 <BR>
 * JSPのラジオボタンや、セレクトタグが以前選択されていた時は、画面遷移後も
 * 選択状態にしたいと思います。
 * その時、以前の選択されていたデータと現在のデータを比較し
 * 選択状態にする文字列を返すためのクラスです。
 * 例：
 * <PRE>
 * <SELECT>
 * <%
 * 　　Iterator ite = list.iterator();
 * 　　while( ite.hasNext() )
 * 　　{
 * 　　　　String data = (String)ite.next();
 * %>
 * <OPTION VALUE="<%=data%>" <%=RbsiteHTMLUtility.isSelected(befStr, data)%>><%=data%></OPTION>
 * <%
 * 　　}
 * %>
 * </SELECT>
 * </PRE>
 * @author telema_yugen777
 */
public class MDWareHTMLUtility
{
	private MDWareHTMLUtility()
	{
		//間違ってインスタンス化しないようにしている。
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、セレクトタグを選択状態にする
	 * @param befStr 前回選択されていた文字列
	 * @param nowStr 現在の文字列
	 * @return 前回と同じ時は SELECTED を返すが、違う時は空文字を返す。
	 */
	public static String isSelected( String befStr, String nowStr )
	{
		if( befStr == null || nowStr == null )
		{
			return "";
		}
		if( befStr.trim().equals( nowStr.trim() ) )
		{
			return "SELECTED";
		}
		else
		{
			return "";
		}
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、セレクトタグを選択状態にする
	 * @param bef 前回選択されていた文字列
	 * @param now 現在の文字列
	 * @return 前回と同じ時は SELECTED を返すが、違う時は空文字を返す。
	 */
	public static String isSelected( int bef, int now )
	{
		if( bef == now )
		{
			return "SELECTED";
		}
		else
		{
			return "";
		}
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、セレクトタグを選択状態にする
	 * @param bef 前回選択されていた文字列
	 * @param now 現在の文字列
	 * @return 前回と同じ時は SELECTED を返すが、違う時は空文字を返す。
	 */
	public static String isSelected( long bef, long now )
	{
		if( bef == now )
		{
			return "SELECTED";
		}
		else
		{
			return "";
		}
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、ラジオボタンを選択状態にする。
	 * @param befStr 前回選択されていた文字列
	 * @param nowStr 現在の文字列
	 * @return 前回と同じ時は CHECKED を返すが、違う時は空文字を返す。
	 */
	public static String isChecked( String befStr, String nowStr )
	{
		if( befStr == null || nowStr == null )
		{
			return "";
		}
		if( befStr.trim().equals( nowStr.trim() ) )
		{
			return "CHECKED";
		}
		else
		{
			return "";
		}
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、ラジオボタンを選択状態にする。
	 * @param bef 前回選択されていた文字列
	 * @param now 現在の文字列
	 * @return 前回と同じ時は CHECKED を返すが、違う時は空文字を返す。
	 */
	public static String isChecked( int bef, int now )
	{
		if( bef == now )
		{
			return "CHECKED";
		}
		else
		{
			return "";
		}
	}

	/**
	 * 前回選択されていた情報と、現在のデータを比較し、ラジオボタンを選択状態にする。
	 * @param bef 前回選択されていた文字列
	 * @param now 現在の文字列
	 * @return 前回と同じ時は CHECKED を返すが、違う時は空文字を返す。
	 */
	public static String isChecked( long bef, long now )
	{
		if( bef == now )
		{
			return "CHECKED";
		}
		else
		{
			return "";
		}
	}

	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 空文字定数です。
	 */
	private static final String BLANK = "";

	/**
	 * HTML 文字参照 &amp;nbsp; 定数です。
	 */
	private static final String ENTITY_REF_NBSP   = "&nbsp;";
	/**
	 * HTML 文字参照 &amp;amp; 定数です。
	 */
	private static final String ENTITY_REF_AMP    = "&amp;";
	/**
	 * HTML 文字参照 &amp;gt; 定数です。
	 */
	private static final String ENTITY_REF_GT     = "&gt;";
	/**
	 * HTML 文字参照 &amp;lt; 定数です。
	 */
	private static final String ENTITY_REF_LT     = "&lt;";
	/**
	 * HTML 文字参照 &amp;quot; 定数です。
	 */
	private static final String ENTITY_REF_QUOT   = "&quot;";


	/**
	 * 指定された文字列を、テキストフィールド（INPUT エレメントの type アトリビュート値が 'text'）に
	 * 適した文字列に変換します。
	 * 指定された String が null の場合は空文字を返します。
	 *
	 * @param val   変換する文字列
	 * @return      変換された文字列
	 */
	public static String parseSafeText(String val) {
		String rtnStr = null;

		if (val != null) {
			//val = val.trim();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < val.length(); i++) {
				char c = val.charAt(i);
				if (c == '&') {
					// 2003.01.14 ADD
				/*	if (val.indexOf(ENTITY_REF_AMP,	 i) == -1
					 && val.indexOf(ENTITY_REF_QUOT, i) == -1
					 && val.indexOf(ENTITY_REF_LT, 	 i) == -1
					 && val.indexOf(ENTITY_REF_GT,   i) == -1) {
						sb.append(ENTITY_REF_AMP);
					} else {*/
						sb.append('&');
				//	}

				/* 2002.02.16 DEL M.Ashizawa
				テキストフィールドの value に &amp;nbsp; が存在する場合、IE の画面では半角空白文字として表示されます。
				しかし、submit して servlet で受け取ったときは &amp;nbsp; の String として取得してしまいます。
				そのまま、DB 等へ書き込むと &amp;nbsp; という文字列として更新されてしまうので、半角空白文字をテキスト
				フィールドの value 値として扱う場合はそのまま手を加えずに半角空白文字として出力します。
				*/
				//} else if (c == ' ') {
				//	sb.append(SPACE_ENTITY_REF);
				} else if (c == '"') {
					sb.append(ENTITY_REF_QUOT);
				} else if (c == '<') {
					sb.append(ENTITY_REF_LT);
				} else if (c == '>') {
					sb.append(ENTITY_REF_GT);
				} else {
					sb.append(c);
				}
			}
			// 両サイドの空白を除去します。
			// これは DB の CHAR 項目の後ろスペースを統一的に除去するために行っています。
			rtnStr = sb.toString().trim();
		} else {
			rtnStr = BLANK;
		}
		return rtnStr;
	}


	/**
	 * long の値をテキストフィールド（INPUT エレメントの type アトリビュート値が 'text'）に
	 * 適した文字列に変換します。
	 *
	 * @param val   long 値
	 * @return      変換された文字列
	 */
	public static String toText(long val) {
		return toText( String.valueOf(val) );
	}


	/**
	 * double の値をテキストフィールド（INPUT エレメントの type アトリビュート値が 'text'）に
	 * 適した文字列に変換します。
	 *
	 * @param val   double 値
	 * @return      変換された文字列
	 */
	public static String toText(double val) {
		return toText( String.valueOf(val) );
	}


	/**
	 * BigDecimal の値をテキストフィールド（INPUT エレメントの type アトリビュート値が 'text'）に
	 * 適した文字列に変換します。
	 * 指定された BigDecimal が null の場合は空文字を返します。
	 *
	 * @param val   BigDecimal オブジェクト
	 * @return      変換された文字列
	 */
	public static String toText(BigDecimal val) {
		String s = null;
		if (val != null) {
			s = val.toString();
		}
		return toText(s);
	}


	/**
	 * String をテキストフィールド（INPUT エレメントの type アトリビュート値が 'text'）に
	 * 適した文字列に変換します。
	 * 指定された String が null の場合は空文字を返します。
	 *
	 * @param val   変換する文字列
	 * @return      変換された文字列
	 */
	public static String toText(String val) {
		if (val == null) {
			val = BLANK;
		}
		return parseSafeText( val );
	}


	/**
	 * 指定された文字列を、HTML に適した文字列に変換します。
	 * 指定された文字列が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val   変換する文字列
	 * @return      変換された文字列
	 */
	public static String parseSafeHtml(String val) {
		String rtnStr = null;

		if (val != null) {
			// 両サイドの空白を除去します。
			// これは DB の CHAR 項目の後ろスペースを統一的に除去するために行っています。
			val = val.trim();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < val.length(); i++) {
				char c = val.charAt(i);
				if (c == '&') {
					// 2003.02.19 MDF
					//sb.append("&amp;");
					if (val.indexOf(ENTITY_REF_AMP,	 i) == -1
					 && val.indexOf(ENTITY_REF_QUOT, i) == -1
					 && val.indexOf(ENTITY_REF_NBSP, i) == -1
					 && val.indexOf(ENTITY_REF_LT, 	 i) == -1
					 && val.indexOf(ENTITY_REF_GT,   i) == -1) {
						sb.append(ENTITY_REF_AMP);
					} else {
						sb.append('&');
					}

				} else if (c == ' ') {
					sb.append(ENTITY_REF_NBSP);
				} else if (c == '"') {
					sb.append(ENTITY_REF_QUOT);
				} else if (c == '<') {
					sb.append(ENTITY_REF_LT);
				} else if (c == '>') {
					sb.append(ENTITY_REF_GT);
				} else {
					sb.append(c);
				}
			}
			rtnStr = sb.toString();
			if (BLANK.equals(rtnStr)) {
				rtnStr = ENTITY_REF_NBSP;
			}
		} else {
			rtnStr = ENTITY_REF_NBSP;
		}
		return rtnStr;
	}


	/**
	 * 指定された文字列を、HTML に適した文字列に変換します。
	 * 指定された文字列が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val   変換する文字列
	 * @return      変換された文字列
	 */
	public static String toLabel(String val) {
		return parseSafeHtml(val);
	}


	/**
	 * 指定された文字列を、length で指定されたバイト数に切り捨ててから
	 * HTML に適した文字列に変換します。
	 * 指定された文字列が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val       変換する文字列
	 * @param length    有効とするバイト数
	 * @return          変換された文字列
	 */
	public static String toLabel(String val, int length) {
		// 2003.02.18 MDF M.Ashizawa
		//return cutString( parseSafeHtml(val), length );
		return parseSafeHtml( cutString(val, length) );
	}


	/**
	 * BigDecimal の値を HTML に適した文字列に変換します。
	 * 指定された BigDecimal が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val   BigDecimal オブジェクト
	 * @return      変換された文字列
	 */
	public static String toLabel(BigDecimal val) {
		String s = null;
		if (val != null) {
			s = val.toString();
		}
		return toLabel(s);
	}


	/**
	 * BigDecimal の値を pattern で指定された形式の HTML に適した文字列に変換します。
	 * 指定された BigDecimal が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val       BigDecimal オブジェクト
	 * @param pattern   数字のフォーマット
	 * @return          変換された文字列
	 * @see DecimalFormat
	 */
	public static String toLabel(BigDecimal val, String pattern) {
		String s = null;
		if (val != null) {
			DecimalFormat df = new DecimalFormat(pattern);
			s = df.format(val.doubleValue());
		}
		return toLabel(s);
	}


	/**
	 * 指定された<b>数字</b>の文字列を pattern で指定された形式の HTML に適した文字列に変換します。
	 * 指定された文字列が null の場合は &amp;nbsp; を返します。
	 *
	 * @param val       変換する文字列
	 * @param pattern   数字のフォーマット
	 * @return          変換された文字列
	 * @see DecimalFormat
	 */
	public static String toLabel(String val, String pattern) {
		String rtnStr = null;
		if (val == null || val.trim().length() == 0) {
			rtnStr = ENTITY_REF_NBSP;
		} else {
			try {
				rtnStr = toLabel(new BigDecimal(val), pattern);
			} catch(NumberFormatException e) {
				rtnStr = "NaN";
			}
		}
		return rtnStr;
	}


	/**
	 * 指定された<b>8 桁の日付文字列（yyyyMMdd 形式）</b>を pattern で指定された形式の日付文字列に変換します。
	 * 指定された文字列が null の場合は空文字を返します。
	 * 日付に変換できなかった場合は val をそのまま返します。
	 *
	 * @param val       8 桁の日付文字列（yyyyMMdd 形式）
	 * @param pattern   日付のフォーマット
	 * @return          変換された文字列
	 * @see SimpleDateFormat
	 */
	public static String toDate(String val, String pattern) {
		String s = BLANK;
		//if (val != null) {    2003.02.18 MDF M.Ashizawa
		if (val != null && val.trim().length() > 0) {
			try {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
				sdf1.setLenient(false); 	// 日付の厳格なチェックを行う。
				sdf2.setLenient(false); 	// 日付の厳格なチェックを行う。
				s = sdf2.format( sdf1.parse(val) ); 			 // YYYYMMDD → Date 型 → 指定されたフォーマットの日付文字列へ
			} catch(ParseException ex) {
				s = val;
			}
		}
		return s;
	}


	/**
	 * BigDecimal の値を pattern で指定された形式の日付文字列に変換します。
	 * 指定された BigDecimal が null の場合は空文字を返します。
	 * 日付に変換できなかった場合は val をそのまま返します。
	 *
	 * @param val       BigDecimal オブジェクト
	 * @param pattern   日付のフォーマット
	 * @return          変換された文字列
	 * @see SimpleDateFormat
	 */
	public static String toDate(BigDecimal val, String pattern) {
		/* 2003.02.18 MDF M.Ashizawa
		String s = BLANK;*/
		String s = null;
		if (val != null) {
			s = val.toString();
		}
		return toDate(s, pattern);
	}


	/**
	 * Array（１次元配列）用のサフィックスを返します。
	 * <pre>
	 * 【Note】
	 *   ID 属性は、NAME 属性名などと同じに、英字から始まり英数字とハイフン "-" とピリオド "." から成らなければなりません。
	 *   HTML4.0(J)では、下線 "_" とコロン ":" が追加されました。
	 *
	 * 【注意】2003.02.03 追記
	 *   ID、NAME 属性に ":" を使用すると JavaScript 内のエレメントを指定するステートメントの一部がラベルと認識されて
	 *   しまいます。
	 *   【例】
	 *      document.MainForm.ten_code:1.value = "";		← エラーとなる
	 *   上記のことから ID、NAME 属性に ":" を使用するのは危険です。同様に "." も危険です。
	 *   安全のために英数字と "_" のみに制限します。
	 * </pre>
	 *
	 * @param index 行番号
	 * @return	'_' + 行番号の String
	 * @see AbstractIstCommand クラスにも同じメソッドが存在します。
	 *      また、JavaScript 外部ファイル ist_common.js にも同機能の関数 arraySuffix, gridSuffix があります。
	 */
	public static String suffix(int index) {
		return "_" + index;
	}


	/**
	 * Grid（２次元配列）用のサフィックスを返します。
	 * <pre>
	 * 【Note】
	 *   ID 属性は、NAME 属性名などと同じに、英字から始まり英数字とハイフン "-" とピリオド "." から成らなければなりません。
	 *   HTML4.0(J)では、下線 "_" とコロン ":" が追加されました。
	 *
	 * 【注意】2003.02.03 追記
	 *   ID、NAME 属性に ":" を使用すると JavaScript 内のエレメントを指定するステートメントの一部がラベルと認識されて
	 *   しまいます。
	 *   【例】
	 *      document.MainForm.ten_code:1.value = "";		← エラーとなる
	 *   上記のことから ID、NAME 属性に ":" を使用するのは危険です。同様に "." も危険です。
	 *   安全のために英数字と "_" のみに制限します。
	 * </pre>
	 *
	 * @param rowIndex	行番号
	 * @param colIndex	列番号
	 * @return	'_' + 行番号 + '_' + 列番号の String
	 * @see AbstractIstCommand クラスにも同じメソッドが存在します。
	 *      また、JavaScript 外部ファイル ist_common.js にも同機能の関数 arraySuffix, gridSuffix があります。
	 */
	public static String suffix(int rowIndex, int colIndex) {
		return "_" + rowIndex + "_" + colIndex;
	}

	/**
	 * radioボタン(input type="radio")のcheck付加制御を行います。
	 * データとして渡ってきたvalがチェック対象のvalと同じ値のとき、checkedを返します。
	 * 引数のどちらかがnullならば空文字定数BLANKを返します。
	 *
	 * @param dataVal	データとして渡ってきたradioボタンのvalue
	 * @param thisVal	チェック対象のradioボタンのvalue
	 * @return			"checked" または ""
	 */
	public static String toRadioCheck(String dataVal, String thisVal) {
		String checkStr = BLANK;
		if ( dataVal != null && thisVal != null && dataVal.trim().equals(thisVal)) {
			checkStr = "checked";
		}
		return checkStr;
	}


	/**
	 * 指定された文字列を許容される最大バイト数で切り捨てます。
	 * シングルクォートの安全化処理は行われません。
	 *
	 * @param base  変換対象文字列
	 * @param max   許容される最大バイト数
	 * @return      許容される最大バイト数で切り捨てられた String
	 * @see DM Creator によって生成された Bean の cutString( String base, int max )
	 */
	public static String cutString( String base, int max ) {
		if ( base == null ) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) {
			String tmp = null;
			try {
				tmp = base.substring(pos, pos + 1);
				byte bt[] = tmp.getBytes(CHARSET_NAME);
				count += bt.length;
				if (count > max) {
					break;
				}
				sb.append(tmp);

			} catch(UnsupportedEncodingException e) {

			}
		}
		return sb.toString();
	}
}
