package mdware.common.util;

import java.util.*;
import java.text.*;
import java.math.BigDecimal;

/**
 * <p>タイトル: NumberUtility - 数量Utilityクラス</p>
 * <p>説明: 2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author deguchi(MJC)
 * @version 1.0  20030815(deguchi)
 */

public class NumberUtility {


	/**
	 * doubleの切捨てを行い、返します。
	 * @param dbl 数量(double)
	 * @param keta 許容する小数点以下桁数
	 * @return 切捨て処理された後の数量(double)
	 */
	public synchronized static double floor(double dbl, int keta) {
		BigDecimal dblBd = (new BigDecimal(dbl)).setScale(keta, BigDecimal.ROUND_DOWN);
		return dblBd.doubleValue();
	}


	/**
	 * doubleの四捨五入を行い、返します。
	 * @param dbl 数量(double)
	 * @param keta 許容する小数点以下桁数
	 * @return 四捨五入された数量(double)
	 */
	public synchronized static double round(double dbl, int keta) {
		BigDecimal dblBd = (new BigDecimal(dbl)).setScale(keta, BigDecimal.ROUND_HALF_UP);
		return dblBd.doubleValue();
	}
	
	
	
}