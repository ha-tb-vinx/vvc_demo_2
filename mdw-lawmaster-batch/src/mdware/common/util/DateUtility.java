package mdware.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

/**
 * <p>タイトル: DateUtility</p>
 * <p>説明: システム日付などの取得を行うユーティリティクラスです。2006/05/01以降使用禁止
 * 　Ver3.1より、DBからシステム日付を取得するようになったため、当クラスに改修を加えています。
 * 　また、Ver3.0以前の修正コメントを削除しています。修正コメントの参照はVer3.0を見てください。
 * 　
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Yunba
 * @version 1.00 本番リリース
 * @version 1.01 2003.04.22 yamanaka 障害報告№002(ist)・007(poro)対応
 * @version 2.00 2004.08.03 S.Yunba Ver3.1用に改修。
 * @version 2.01 2005.01.25 shiomi 変更依頼No.165
 * @version 2.02 2005.03.08 shiomi 障害対応No.293
 * @version 2.03 2005.06.24 hirai 変更依頼No.547
 */
public class DateUtility {
	
	/**
	 * yyyyMMddHHmmss形式の14桁の日付を取得します。
	 * yyyyMMddはDBよりオンライン年月日を取得し、HHmmssはマシン日付を取得します。
	 * 
	 * @return
	 */
	public static String longNow() {
		
		// DBサーバよりシステム日付を取得。
		String onlineDate = SystemControlUtil.getOnlineDate();
		
		// APPサーバのシステム時間を取得。
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateFormat dateformat = new SimpleDateFormat("HHmmss");
		
		return onlineDate + dateformat.format(date);
	}
	
	/**
	 * yyyyMMddHHmmss形式の14桁の日付を取得します。
	 * yyyyMMddはDBよりバッチ年月日を取得し、HHmmssはマシン日付を取得します。
	 * 
	 * @return
	 */
	public static String longNowBatch() {
		
		// DBサーバよりシステム日付を取得。
		String batchDate = SystemControlUtil.getBatchDate();
		
		// APPサーバのシステム時間を取得。
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateFormat dateformat = new SimpleDateFormat("HHmmss");
		
		return batchDate + dateformat.format(date);
	}
	
	/**
	 * Ver3.1以降、共通関数として使用を推奨しません。
	 * SystemControlUtil.getOnlineDate()、または、SystemControlUtil.getBatchDate()を使用してください。
	 * 
	 * yyyyMMdd形式の日付を取得します。
	 * 
	 * @return
	 */
	public static String today() {
		// DBサーバよりシステム日付を取得。
		return SystemControlUtil.getOnlineDate();
	}
	
	/**
	 * offsetで与えた日付分、前後したyyyyMMdd形式の日付を取得します。
	 * 注：オンライン日付を元に取得します。
	 * Ver3.0で存在したメソッドを改修しています。
	 * 
	 * @param offset
	 * @return
	 */
	public static String getToday(int offset) {
		return DateUtility.getOffsetDay(offset);
	}
	
	/**
	 * offsetで与えた日付分、前後したyyyyMMdd形式の日付を取得します。
	 * 注：オンライン日付を元に取得します。
	 * 
	 * @param offset
	 * @return
	 */
	public static String getOffsetDay(int offset) {
		
		String month = "";
		String day = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);	// 日付を厳密に解釈します 。
		Calendar calendar = sdf.getCalendar();
		try {
			calendar.setTime( sdf.parse(SystemControlUtil.getOnlineDate()) );
		} catch(Exception e) {
			return null;
		}
		
		//	指定偏差分、日付を補正
		calendar.add(Calendar.DATE, offset);
		//	YYYY/MM/DD 形式に編集
		month = String.valueOf(calendar.get(Calendar.MONTH)+1) ;
		if(month.length() == 1){
			month = "0" + month;
		}
		day = String.valueOf(calendar.get(Calendar.DATE));
		if (day.length() == 1){
			day = "0" + day;
		}
		return (calendar.get(Calendar.YEAR) + "/" + month + "/" + day);
	}
	
	/**
	 * 指定日の曜日をjava.lang.Calendarのフィールドに基づいて返す。
	 * @param yyyyMMdd	日付
	 * @return
	 */
	public static int getDayOfWeek(String yyyyMMdd) {
		Calendar cal = Calendar.getInstance();
		String tmpDayOfWeek = new String();
		String ret = new String();
		
		int yyyy = Integer.parseInt(yyyyMMdd.substring(0, 4));
		int mm	 = Integer.parseInt(yyyyMMdd.substring(4, 6));
		int dd	 = Integer.parseInt(yyyyMMdd.substring(6, 8));
		
		cal.set(yyyy, mm - 1, dd);
		
		ret = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		
		return Integer.parseInt(ret);
	}
	
	/**
	 * オンライン日付の曜日をjava.lang.Calendarのフィールドに基づいて返す。
	 * @param yyyyMMdd	日付
	 * @return
	 */
	public static int getDayOfWeek() {
		// DBサーバよりシステム日付を取得。
		return DateUtility.getDayOfWeek( SystemControlUtil.getOnlineDate() );
	}
	
	/**
	 * バッチ日付の曜日をjava.lang.Calendarのフィールドに基づいて返す。
	 * @param yyyyMMdd	日付
	 * @return
	 */
	public static int getDayOfWeekBatch() {
		// DBサーバよりシステム日付を取得。
		return DateUtility.getDayOfWeek( SystemControlUtil.getBatchDate() );
	}
	
	/**
	 * APPサーバの時間を返す。
	 * @return hhmm 時間
	 */
	public static String getTotime() {
		String hhmm = "";
		
		try {
			hhmm = longNow().substring(8, 12);
		} catch(IndexOutOfBoundsException iooe) { }
		
		return hhmm;
	}
	
	/**
	 * @param yyyyMMdd
	 * @return
	 * @throws NumberFormatException
	 */
	public static String dayOfTheWeekUnites(String yyyyMMdd) throws NumberFormatException {
		
		String beforeDate = "";
		int year;
		int monthDay;
		try {
			year = Integer.parseInt(yyyyMMdd.substring(0, 4));
			monthDay = Integer.parseInt(yyyyMMdd.substring(4, 8));
		} catch (NumberFormatException e) {
			throw e;
		}
		GregorianCalendar cal = new GregorianCalendar();
		// まずは、年月日 -1年 + 1日で閏年以外は前年の同曜日になる
		beforeDate = DateChanger.addDate(DateChanger.addYear(yyyyMMdd, -1), 1);
		// 年月日が閏年で、且つ2月29日以降なら 年月日 -1年 + 2日なのでさらに +1日
		if ((cal.isLeapYear(year) && monthDay > 228) || (cal.isLeapYear(year - 1) && monthDay < 228)){
			beforeDate = DateChanger.addDate(beforeDate, 1);
		}
		return beforeDate;
	}
	
//	20050125 mod shiomi 変更依頼No.165 start
	/**
	 * 日付・週テーブルを取得します。
	 * DBサーバのシステム日付の「年」３月１日を第一週とした、日付・週テーブルを取得します。
	 * 
	 * @return Hashtable
	 */
	public static Hashtable weekTableSystemControl() {
		return DateUtility.weekTable(DateUtility.today());
	}
	
	/**
	 * 日付・週テーブルを取得します。
	 * パラメータ年月日の「年」の３月１日を第一週とした、日付・週テーブルを取得します。
	 * （前年・今年の２年分）
	 * 
	 * @return Hashtable
	 */
	public static Hashtable weekTable(String today) {
		Hashtable ht = new Hashtable();
		int dayOfWeek = 0;
		String addDate = "";
		
		// APPサーバのシステム時間を取得。
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateFormat dateformat = new SimpleDateFormat("yyyy");
	
		String march = dateformat.format(date) + "0301";

		//today＝１～２月　⇒　昨年の３月１日を第一週		
		//today＝３～12月　⇒　本年の３月１日を第一週		
//		20050308 mod shiomi 障害修正No.293 start
//		int addY = 0;
//		if (Integer.parseInt(today.substring(4, 6)) < 3){
//			addY = -1;
//		}
		int addY = -1;
		if (Integer.parseInt(today.substring(4, 6)) < 3){
			addY = -2;
		}
//		20050308 mod shiomi 障害修正No.293 end
		String calDate = DateChanger.addYear(march, addY);

		int week = 1;
//		20050308 mod shiomi 障害修正No.293 start
//		if ((today.substring(4, 8).equals("0301"))){
//			ht.put(DateChanger.addDate(calDate, -1), "0");	//３月１日の前日は「０週」
//		}
//		ht.put(calDate, String.valueOf(week));			//３月１日は「１週」
//		20050308 mod shiomi 障害修正No.293 start

		int i = 0;
//		20050308 mod shiomi 障害修正No.293 start
//		for (i=0; i<367; i++){
		for (i=0; i<(366 * 2); i++){
//		20050308 mod shiomi 障害修正No.293 end
			addDate = DateChanger.addDate(calDate, 1);
			calDate = addDate;

			dayOfWeek = getDayOfWeek(calDate);

			//週テーブル番号の算出
			//月曜日は「次週」へ繰り上げ
			if (dayOfWeek == Calendar.MONDAY){		//月<始>～日<終>
				week++;
			}
			//３月１日は「第１週」（デフォルト）
			if ((calDate.substring(4, 8).equals("0301"))){
				week = 1;
			}
//		20050308 mod shiomi 障害修正No.293 end
			ht.put(calDate, String.valueOf(week));
		}
		
		return ht;
	}
//	20050125 mod shiomi 変更依頼No.165 end

//	20050624 add hirai 変更依頼No.547 start
	/**
	 * 指定時刻が与えられた時刻を過ぎているか判定する。
	 *@param 指定時刻・制限時刻 HHMM形式
	 *@return true：制限時刻を過ぎている。
	 */
	public static boolean isRestrictionTime(String currentTime,String restrictionTime) {
		boolean isRestrictionTime = false;
		if(restrictionTime.compareTo(currentTime) <= 0  ){
			isRestrictionTime = true;
		}
		return isRestrictionTime;
	}

	/**
	 * 現在時刻が与えられた時刻を過ぎているか判定する。
	 *@param 制限時刻 HHMM形式
	 *@return true：制限時刻を過ぎている。
	 */
	public static boolean isRestrictionTimeForPresentTime(String restrictionTime) {
		return isRestrictionTime(DateUtility.getTotime(),restrictionTime);
	}
//	20050624 add hirai 変更依頼No.547 end
}
