package mdware.common.util;

/**
 * <p>タイトル: モジュラス10 ウェイト3･1 </p>
 * <p>説明: モジュラス10 ウェイト3･1でチェックデジットを計算します。（主にJANコードやITFで使用されるチェックデジットを計算です。）2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author takata
 * @version 1.0
 */
public class Modulus10W31 implements CheckDigitCalculator {

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
		int wei = 0;
		for (int i = 0; i < len; i++) {
			if (i % 2 == 1) {
				wei = 1;
			} else {
				wei = 3;
			}
			sum += wei * (int)(ch[i] - '0');
		}

		// 10で割った余りを求める。
		int mod = sum % 10;

		// 10から余りを引いたものの1の位が求める値
		String ret = String.valueOf((10 - mod) % 10);
		return ret;
	}

}