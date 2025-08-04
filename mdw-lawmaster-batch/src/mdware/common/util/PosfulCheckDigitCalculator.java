package mdware.common.util;

/**
 * <p>タイトル: ポスフール様伝票番号のチェックディジットを計算する </p>
 * <p>説明: チェックディジットの計算方法はポスフール様の独自仕様です。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author takata
 * @version 1.0
 */
public class PosfulCheckDigitCalculator implements CheckDigitCalculator {

	/**
	 * チェックディジットを計算する。
	 * @param data
	 * @return
	 */
	public String calculate(String data) {

		try {
			int sum = Integer.parseInt(data);

			// 7で割った余りを求める。
			int mod = sum % 7;

			// 余りがそのままチェックディジットになる
			String ret = "" + mod;
			return ret;

		} catch (NumberFormatException e) {
			return "";				
		}
	}

	public static void main(String[] args) {
		
		PosfulCheckDigitCalculator cd = new PosfulCheckDigitCalculator();
		System.out.println("チェックディジットは" + cd.calculate("017548")); 
		
	}

}