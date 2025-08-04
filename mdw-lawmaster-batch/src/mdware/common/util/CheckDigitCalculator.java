package mdware.common.util;

/**
 * <p>タイトル: CheckDigitCalculator </p>
 * <p>説明: チェックディジットを計算する。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author takata
 * @version 1.0
 */
public interface CheckDigitCalculator {

	/**
	 * チェックディジットを計算する。
	 * @param data
	 * @return
	 */
	public String calculate(String data);
	
}