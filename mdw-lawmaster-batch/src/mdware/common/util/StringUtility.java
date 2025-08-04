package mdware.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.0
 */

public class StringUtility {

	/**
	 * 文字列を数値に変換します。変換できない場合は０を返します。
	 * @param str 数値文字列
	 * @return 数値
	 */
	public synchronized static int string2int(String str) {
		try {
			return Integer.parseInt(str.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * フォーマットされた文字列を返します。
	 * @param doubleValue 小数値
	 * @param valueFormatString フォーマット文字列
	 * @return フォーマットされた文字列
	 */
	public synchronized static String getFormatedString(double doubleValue, String valueFormatString) {
		if (valueFormatString != null && valueFormatString.trim().length() > 0) {
			try {
				DecimalFormat decimalFormatter = new DecimalFormat(valueFormatString);
				return decimalFormatter.format(doubleValue);
			} catch (Exception e) {
			}
		}
		return String.valueOf(doubleValue);
	}

	/**
	 * フォーマットされた文字列を返します。
	 * @param intValue 整数値
	 * @param valueFormatString フォーマット文字列
	 * @return フォーマットされた文字列
	 */
	public synchronized static String getFormatedString(int intValue, String valueFormatString) {
		if (valueFormatString != null && valueFormatString.trim().length() > 0) {
			try {
				DecimalFormat decimalFormatter = new DecimalFormat(valueFormatString);
				return decimalFormatter.format(intValue);
			} catch (Exception e) {
			}
		}
		return String.valueOf(intValue);
	}

	/**
	 * フォーマットされた文字列を返します。
	 * @param intValueString 整数を表す文字列
	 * @param valueFormatString フォーマット文字列
	 * @return フォーマットされた文字列
	 */
	public synchronized static String getFormatedIntString(String intValueString, String valueFormatString) {
		return getFormatedString(Integer.parseInt(intValueString), valueFormatString);
	}

	/**
	 * 指定の文字（列）を付加します。２バイト以上の文字列を付加した場合、指定の桁数を超えてしまう可能性があるので注意して下さい。
	 * @param addedString 付加される文字列
	 * @param keta 付加後の桁数
	 * @param aStr 付加する文字列
	 * @param addLeft true - 左側に付加　true - 右側に付加
	 * @return 付加された文字列
	 */
	public synchronized static String charFormat(String addedString, int keta, String aStr, boolean addLeft) {
		String retString = addedString;
		while (keta - retString.getBytes().length > 0) {
			if (addLeft) {
				retString = aStr + retString;
			} else {
				retString = retString + aStr;
			}
		}
		return retString;
	}

	/**
	 * ゼロフォーマットします。
	 * @param str 付加される文字列
	 * @param keta 付加後の桁数
	 * @return 付加された文字列
	 */
	public synchronized static String zeroFormat(String str, int keta) {
		return charFormat(str, keta, "0", true);
	}

	/**
	 * ゼロフォーマットします。
	 * @param intValue 付加される整数
	 * @param keta 付加後の桁数
	 * @return 付加された文字列
	 */
	public synchronized static String zeroFormat(int intValue, int keta) {
		return zeroFormat(String.valueOf(intValue), keta);
	}

	/**
	 * スペースフォーマットします。
	 * @param addedString 付加される文字列
	 * @param keta 付加後の桁数
	 * @param addLeft true - 左側に付加　true - 右側に付加
	 * @return 付加された文字列
	 */
	public synchronized static String spaceFormat(String addedString, int keta, boolean addLeft) {
		return charFormat(addedString, keta, " ", addLeft);
	}

	/**
	 * スペースフォーマットします。
	 * @param addedString 付加される文字列
	 * @param keta 付加後の桁数
	 * @return 付加された文字列
	 */
	public synchronized static String spaceFormat(String addedString, int keta) {
		return charFormat(addedString, keta, " ", false);
	}

	/**
	 * 文字列を指定バイト数でカットします。
	 * @param str カットしたい文字列
	 * @param byteCnt カット後のバイト数
	 * @return カット後の文字列
	 */
	public synchronized static String cutString(String str, int byteCnt) {
		if (str == null) {
			return "";
		}
		byte[] strBytes = str.getBytes();
		if (strBytes.length <= byteCnt) {
			return str;
		} else {
			String wkStr = "";
			try {
				for (int i = 0, cnt = 0; i < str.length() - 1; i++) {
					byte[] chByte = str.substring(i, i + 1).getBytes();
					cnt += chByte.length;
					if (cnt > byteCnt) {
						break;
					}
					wkStr += new String(chByte);
				}
			} catch (Exception e) {
			}

			//足りない部分は空白で埋める
			wkStr += spaceFormat("", byteCnt - wkStr.getBytes().length);

			return wkStr;
		}
	}

	// 20021224 @ADD yamanaka start
	/**
	 * 文字列を指定バイト数でカットします。
	 * @param str
	 * @param startByte
	 * @param byteCnt
	 * @return
	 */
	public synchronized static String cutString(String str, int startByte, int byteCnt) {
		if (str == null) {
			return spaceFormat("", byteCnt);
		}
		byte[] strBytes = str.getBytes();
		String wkStr = "";
		try {
			byte[] chByte = new byte[byteCnt];
			int cnt = 0;
			for (int i = startByte; i < strBytes.length; i++) {
				chByte[cnt] = strBytes[i];
				if (cnt > byteCnt) {
					break;
				}
				cnt++;
			}
			byte[] chByteRet = new byte[cnt];
			for (int i = 0; i < cnt; i++) {
				chByteRet[i] = chByte[i];
			}
			wkStr += new String(chByteRet);
		} catch (Exception e) {
		}

		//足りない部分は空白で埋める
		wkStr += spaceFormat("", byteCnt - wkStr.getBytes().length);

		return wkStr;
	}
	// 20021224 @ADD yamanaka end

	/**
	 * 文字列が指定バイト数に満たない場合は空白で補い、超えた場合はカットします。
	 * @param str 処理したい文字列
	 * @param byteCnt 処理後のバイト数
	 * @param addLeft 空白を左に付加するかどうかのフラグ
	 * @return 処理後の文字列
	 */
	public synchronized static String adjustStringWithSpace(String str, int byteCnt, boolean addLeft) {
		return cutString(spaceFormat(str, byteCnt, addLeft), byteCnt);
	}

	/**
	 * 文字列が指定バイト数に満たない場合は空白で補い、超えた場合はカットします。
	 * @param str 処理したい文字列
	 * @param byteCnt 処理後のバイト数
	 * @return
	 */
	public synchronized static String adjustStringWithSpace(String str, int byteCnt) {
		return cutString(spaceFormat(str, byteCnt), byteCnt);
	}

	/**
	 * 文字列が指定バイト数に満たない場合は0で補い、超えた場合はカットします。
	 * @param str 処理したい文字列
	 * @param byteCnt 処理後のバイト数
	 * @return
	 */
	public synchronized static String adjustStringWithZero(String str, int byteCnt) {
		return cutString(zeroFormat(str, byteCnt), byteCnt);
	}

	/**
	 * 文字列が指定バイト数に満たない場合は0で補い、超えた場合はカットします。
	 * @param val 処理したい整数
	 * @param byteCnt 処理後のバイト数
	 * @return
	 */
	public synchronized static String adjustStringWithZero(int val, int byteCnt) {
		return cutString(zeroFormat(val, byteCnt), byteCnt);
	}

	/**
	 * SQL生成時に単一引用符をwhere句で利用する場合"'"→"''"に置き換える。※DataModuleより抜粋
	 * @param 文字列（通常はwhere句内で利用されるSQL文のホスト変数内の文字列）
	 * @return String "'"→"''"に置き換わった文字列
	 * @author nob
	 */
	public synchronized static String singleQuotesfilter(String src) {
		if (src == null)
			return new String("");

		StringBuffer dest = new StringBuffer("");
		char c;

		for (int i = 0, n = src.length(); i < n; i++) {
			if ((c = src.charAt(i)) == '\'')
				dest.append('\'');

			dest.append(c);
		}

		return dest.toString();
	}

	// 20030120 @UPD yamanaka start
	/**
	 * ResultSet.getString()時にnullならば空文字を返します。
	 * @param rs ResultSet
	 * @param columnName フィールド名
	 * @return 変換後文字列
	 */
	public synchronized static String null2string(ResultSet rs, String columnName) {
		String retStr = "";
		try {
			retStr = rs.getString(columnName);
			if (rs.wasNull()) {
				return "";
			}
		} catch (SQLException sqlex) {
		}
		return retStr;
	}

	/**
	 * ResultSet.getString()時にnullならば空文字を返します。
	 * @param rs ResultSet
	 * @param columnIndex フィールド№
	 * @return 変換後文字列
	 */
	public synchronized static String null2string(ResultSet rs, int columnIndex) {
		String retStr = "";
		try {
			retStr = rs.getString(columnIndex);
			if (rs.wasNull()) {
				return "";
			}
		} catch (SQLException sqlex) {
		}
		return retStr;
	}

	/**
	 * ResultSet.getInt()時にnullならば0を返します。
	 * @param rs ResultSet
	 * @param columnName フィールド名
	 * @return 変換後文字列
	 */
	public synchronized static int null2int(ResultSet rs, String columnName) {
		int retValue = 0;
		try {
			retValue = rs.getInt(columnName);
			if (rs.wasNull()) {
				return 0;
			}
		} catch (SQLException sqlex) {
		}
		return retValue;
	}
	// 20030120 @UPD yamanaka end

	/**
	 * 先頭と最後の全角、半角の空白を除いた文字列を返します。
	 * @param str 処理したい文字列
	 * @return フォーマットされた文字列
	 */
	public synchronized static String trim(String str) {
		int strLen;
		int start = 0;
		int end;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
			start++;
		}
		str = str.substring(start);
		end = str.length();
		while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
			end--;
		}
		return str.substring(0, end);
	}

	//	20050629 @UPD 李武勇 start
	/**
	 * 文字列を【'】で囲む。
	 * @param src 文字列
	 * @param withSingleQuotesfilter singleQuotesfilter(String str)の処理も含む。(true：含む、false：含まない)
	 * @return String "【文字列】"→"'【文字列】'"に置き換わった文字列
	 * @author 李武勇
	 */
	public synchronized static String elcloseBySingleQuotes(String src, boolean withSingleQuotesfilter) {

		//パラメータがヌルだったら、【""】を返す。
		if (src == null) {
			return new String("");
		}

		StringBuffer dest = new StringBuffer("");

		//① 文字列の前の方に【'】を付ける。
		dest.append("'");
		if (withSingleQuotesfilter) {
			//② 文字列の中に【'】が入っていたら、内容として認識されるようにする。
			char c;
			for (int i = 0, n = src.length(); i < n; i++) {
				if ((c = src.charAt(i)) == '\'')
					dest.append('\'');

				dest.append(c);
			}
		}

		//③ 文字列の後ろの方に【'】を付ける。
		dest.append("'");

		return dest.toString();
	}
	
	/**
	 * 文字列を【'】で囲む。singleQuotesfilter(String str)の処理も含む。
	 * @param src 文字列
	 * @return String "【文字列】"→"'【文字列】'"に置き換わった文字列
	 * @author 李武勇
	 */
	public synchronized static String elcloseBySingleQuotes(String src) {

		return elcloseBySingleQuotes(src, true);
	}
	//	20050629 @UPD 李武勇 end
	
	/**
	 * <pre>
	 * 指定の文字列を付加します。
	 * 指定の桁数を越える場合は付与しません。
	 * 付加される文字列が桁数を越える場合には文字列をカットします。
	 * 
	 * </pre>
	 * @param addedString 付加される文字列
	 * @param keta 付加後の文字数(※Byte数ではありません)
	 * @param aStr 付加する文字列
	 * @param addLeft true - 左側に付加　false - 右側に付加
	 * @return 付加された文字列
	 */
	public synchronized static String charFormatForMultiByte(String addedString, int keta, String aStr, boolean addLeft) {
		keta = keta * 2;	// ２バイト文字数の指定をバイト数に。
		StringBuffer retString = new StringBuffer(keta);
		if(addedString != null){
			retString.append(addedString);
		}
		int addStrLength = retString.toString().getBytes().length;
		int aStrLength = aStr.getBytes().length;

		if(addStrLength > keta ){
			return cutStringNoSpace(addedString, keta);
		}
		
		for(int tmp = keta - addStrLength - aStrLength; tmp >= 0; tmp -= aStrLength){
			if(addLeft){
				retString.insert(0, aStr);
			}else{
				retString.append(aStr);
			}
		}

		return retString.toString();
	}
	
	/**
	 * 文字列を指定バイト数でカットします。
	 * （cutStringメソッドとは違い、カットした後不足分をスペースで埋めません）
	 * @param str カットしたい文字列
	 * @param byteCnt カット後のバイト数
	 * @return カット後の文字列
	 */
	public synchronized static String cutStringNoSpace(String str, int byteCnt) {
		if (str == null) {
			return "";
		}
		byte[] strBytes = str.getBytes();
		if (strBytes.length <= byteCnt) {
			return str;
		} else {
			String wkStr = "";
			try {
				for (int i = 0, cnt = 0; i < str.length() - 1; i++) {
					byte[] chByte = str.substring(i, i + 1).getBytes();
					cnt += chByte.length;
					if (cnt > byteCnt) {
						break;
					}
					wkStr += new String(chByte);
				}
			} catch (Exception e) {
			}
			return wkStr;
		}
	}
}