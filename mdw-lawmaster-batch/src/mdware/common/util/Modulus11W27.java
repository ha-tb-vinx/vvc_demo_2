package mdware.common.util;

/**
 * <p>タイトル: モジュラス11 ウェイト2~7 </p>
 * <p>説明: モジュラス11 ウェイト2~7でチェックデジットを計算します。（主に何で使うんでしょう？）2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author takata
 * @version 1.0
 */
public class Modulus11W27 implements CheckDigitCalculator {

	/**
	 * チェックディジットを計算する。
	 * @param data
	 * @return
	 */
	public String calculate(String data) {

		int len = data.length();
		char[] ch = new char[len];
		//各桁を上位から配列に入れる
		for (int i = 0; i < len; i++) {
			ch[i] = data.charAt(len - i - 1); //文字を分割する
		}

		//各桁にウェイトを掛け、総和を求める
		int sum = 0;
		int wei = 2;
		for (int i = 0; i < len; i++) {
			// ウェイトは2.3.4....7を掛けていきます
			wei++;
			// ウェイトが7を超えるとまた2から始まります。
			if (wei > 7) {
				wei = 2;
			}
			sum += wei * (int)(ch[i] - '0');
		}

		// 11で割った余りを求める。
		int mod = sum % 11;

		// 11から余りを引いたものが求める値
		String ret = String.valueOf(11 - mod);
		return ret;
	}

}