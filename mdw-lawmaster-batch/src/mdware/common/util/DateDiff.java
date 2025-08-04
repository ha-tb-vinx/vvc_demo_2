package mdware.common.util;

import mdware.common.util.DateChanger;
import java.util.*;

/**
 * <p>タイトル: DateDiff </p>
 * <p>説明: 日付に対する汎用メソッドを提供するクラスです。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author takata
 * @version 1.0
 */
public class DateDiff {

	/**
	 * <P>引数で渡される２つの日付間の差を日数で返します。</P>
	 * <P>Date型に時分秒を含む場合、正しい結果が得られなくなります。
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @return 日数の差
	 */
	public static long getDiffDays(Date fromDate, Date toDate) {

		long days = 0;

		days = ((toDate.getTime() - fromDate.getTime()) / (24 * 60 * 60 * 1000));

		return days;

	}

	/**
	 * <P>引数で渡される２つの日付間の差を日数で返します。</P>
	 * @param fromDate 開始日付（yyyyMMdd形式）
	 * @param toDate 終了日付（yyyyMMdd形式）
	 * @return 日数の差
	 */
	public static long getDiffDays(String date1, String date2) {

		Date fromDate = DateChanger.stringToDate(date1);
		Date toDate = DateChanger.stringToDate(date2);

		return getDiffDays(fromDate, toDate);
	}
}