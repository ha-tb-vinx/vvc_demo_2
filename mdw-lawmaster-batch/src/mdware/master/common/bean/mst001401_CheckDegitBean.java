/**
 * <P>タイトル : 新ＭＤシステムで使用するチェックデジット共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するチェックデジット共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius T.Makuta
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;

import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>タイトル : 新ＭＤシステムで使用するチェックデジット共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するチェックデジット共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius T.Makuta
 * @version 1.0
 * @see なし
 */
public class mst001401_CheckDegitBean
{
	/**
	 * モジュラス１１の取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus11("1234567",7) -&gt; "9"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus11(String srcCode,
										int Length) {
		String wrkCode = new String();

		//長さが0の場合はエラー
		if (srcCode.length() == 0) {
			return null;
		}
		//Lengthが0以外の場合srcCodeの文字数がLengthを超えている場合はエラー
		if (Length != 0 && wrkCode.length() >= Length) {
			return null;
		}

		wrkCode = srcCode;
		if (Length != 0 && wrkCode.length() < Length) {
			wrkCode = mst000401_LogicBean.formatZero(wrkCode, Length);
		}
		//各桁の重みで計算する
		int weight = wrkCode.length() + 1;
		int wrkVal = 0;
		for (int i = 0; i < wrkCode.length(); i++) {
			wrkVal += Integer.parseInt(wrkCode.substring(i, i + 1)) * weight;
			weight--;
		}
		int amari = Math.abs((wrkVal % 11) - 11);
		if (amari == 11) {
			amari = 1;
		} else if (amari == 10) {
			amari = 0;
		}
		return Integer.toString(amari);
	}

	/**
	 * モジュラス１０の取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus11("4917700",12) -&gt; "8"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus10(String srcCode,
										int Length) {
		String wrkCode = new String();

		//長さが0の場合はエラー
		if (srcCode.length() == 0) {
			return null;
		}

		wrkCode = srcCode;
		if (Length != 0 && wrkCode.length() < Length) {
			wrkCode = mst000401_LogicBean.formatZero(wrkCode, Length);
		}
		//各桁の重みで計算する
		int weight = 1;
		int wrkVal = 0;
		for (int i = 0; i < wrkCode.length(); i++) {
			wrkVal += Integer.parseInt(wrkCode.substring(i, i + 1)) * weight;
			if (weight == 1) {
				weight = 3;
			} else {
				weight = 1;
			}
		}
		int amari = Math.abs((wrkVal % 10) - 10);
		if (amari == 10) {
			amari = 0;
		}
		return Integer.toString(amari);
	}
	
	/**
	 * モジュラス９の取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus9("2122788",7) -&gt; "2"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus9(String srcCode,
										int Length) {
		String wrkCode = new String();

		//長さが0の場合はエラー
		if (srcCode.length() == 0) {
			return null;
		}

		wrkCode = srcCode;
		if (Length != 0 && wrkCode.length() < Length) {
			wrkCode = mst000401_LogicBean.formatZero(wrkCode, Length);
		}
		//各桁の重みで計算する
		int weight = wrkCode.length();
		int wrkVal = 0;
		for (int i = 0; i < wrkCode.length(); i++) {
			wrkVal += Integer.parseInt(wrkCode.substring(i, i + 1)) * weight;
			weight--;
		}
		//余りがチェックディジット　
		int amari = wrkVal % 9;
		return Integer.toString(amari);
	}

	/**
	 * モジュラス１１のチェックデジットを付加してコードを取得する
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus11Code("1234567",7) -&gt; "12345679"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus11Code(String srcCode,
											int Length) {
		String wrkCode = srcCode;

		wrkCode = getModulus11(wrkCode, Length);
		if (wrkCode == null) {
			return null;
		} else {
			return srcCode + wrkCode;
		}
	}

	/**
	 * モジュラス１０の取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus10Code("4917700",12) -&gt; "49177008"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus10Code(String srcCode,
											int Length) {
		String wrkCode = srcCode;

		wrkCode = getModulus10(wrkCode, Length);
		if (wrkCode == null) {
			return null;
		} else {
			return srcCode + wrkCode;
		}
	}
	
	/**
	 * モジュラス9のチェックデジットを付加してコードを取得する
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getModulus9Code("2122788",7) -&gt; "21227882"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getModulus9Code(String srcCode,
											int Length) {
		String wrkCode = srcCode;

		wrkCode = getModulus9(wrkCode, Length);
		if (wrkCode == null) {
			return null;
		} else {
			return srcCode + wrkCode;
		}
	}

	/**
	 * インストア0400コードの取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001401_CheckDegitBean.getInstore0400Code("1234567",12) -&gt; "0400123456799"<br>
	 * <br>
	 * @param		String	srcCode		: 元のコード
	 * @param		int		Length		: 元コードを右詰で計算させる場合のチェックデジットを除いた桁数（0の場合は元コードの長さで計算する）
	 * @return	String				: チェックデジット（エラー時はnullを返す）
	 */
	public static String getInstore0400Code(String srcCode,
											int Length) {
		String wrkCode = srcCode;
		String wrk0400 = new String();

		//自社品番はモジュラス１１
		wrkCode = getModulus11(wrkCode, wrkCode.length());
		if (wrkCode == null) {
			return null;
		} else {
			//0400を付加してモジュラス１０
			wrkCode = mst000101_ConstDictionary.INSTORE_04 + srcCode + wrkCode;
			wrk0400 = getModulus10(wrkCode, Length);
			if (wrk0400 == null) {
				return null;
			} else {
				return wrkCode + wrk0400;
			}
		}
	}

}
