package mdware.common.util.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * RBSITE用ユーティリティー（日時操作）。 <BR>
 * コンストラクタでインスタンス後日時を変更出来ます。 <BR>
 * 例えば、2006年10月01日13時45分32秒をコンストラクタにセットしたとしましょう。 <BR>
 * ３日前の日時を取得したい場合は次のようにします。 <BR>
 * ・new RbsiteDateUtility("20061001134532").addDateTime(Calendar.DAY_OF_MONTH,
 * -3).toString(); <BR>
 * また、日時の比較や、差を求めるメソッドも用意されています。 <BR>
 * 
 * @author telema_yugen777
 * @version 1.1 障害票№0156対応 isDateTimeメソッドの引数にnull及び、空文字を渡すと、日付と誤認識し、trueを返すため修正 K.Tanigawa
 */
public class MDWareDateUtility
{
	private Calendar cal = null;

	/**
	 * コンストラクタ
	 * 
	 * @param datetime 日時(DateChangerに理解できる形式)
	 */
	public MDWareDateUtility( String datetime )
	{
		cal = new GregorianCalendar();
		cal.setTime( DateChanger.stringToDate( datetime ) );
	}

	/**
	 * コンストラクタ
	 * 
	 * @param datetime
	 */
	public MDWareDateUtility( Date datetime )
	{
		cal = new GregorianCalendar();
		cal.setTime( datetime );
	}

	/**
	 * コンストラクタ
	 * 
	 * @param datetime
	 */
	public MDWareDateUtility( Calendar datetime )
	{
		cal = datetime;
	}

	/**
	 * 日時をフィールド毎に増減させます。
	 * 
	 * @param field　Calendar.YEAR、MONTH、DAY_OF_MONTH・・・を指定出来ます。
	 * @param amount　増減する数
	 * @return このクラス
	 */
	public MDWareDateUtility addDateTime( int field, int amount )
	{
		cal.add( field, amount );
		return this;
	}

	/**
	 * 各フィールドの値を返します。 <BR>
	 * 月はCalendarの仕様通り0から11が返ります。
	 * 
	 * @param field　Calendar.YEAR、MONTH、DAY_OF_MONTH・・・を指定出来ます。
	 * @return 各フィールドの値
	 */
	public int getField( int field )
	{
		return cal.get( field );
	}

	/**
	 * このクラスで保持、変更した日時をYYYYMMDDHHMISSの形式で出力します。
	 * 
	 * @return String 保持、変更した日時
	 */
	public String toString()
	{
		return DateChanger.dateToString( cal.getTime() );
	}
	
	/**
	 * このクラスで保持、変更した日時をYYYYMMDDの形式で出力します。
	 * 20061030
	 * @return String 保持、変更した日時
	 */
	public String toStringYYYYMMDD()
	{
		String y = String.valueOf( this.getField( Calendar.YEAR ) );
		String m = String.valueOf( ( this.getField( Calendar.MONTH ) + 1 ) );
		if( m.length() == 1 ) m = "0" + m;
		String d = String.valueOf( this.getField( Calendar.DAY_OF_MONTH ) );
		if( d.length() == 1 ) d = "0" + d;
		return  y + m + d;

	}
	
	/**
	 * このクラスで保持、変更した日時をYY/MM/DDの形式で出力します。
	 * 
	 * @return String 保持、変更した日時
	 */
	public String toStringYYMMDDandSlash()
	{
		String y = String.valueOf( this.getField( Calendar.YEAR ) ).substring(2);
		String m = String.valueOf( ( this.getField( Calendar.MONTH ) + 1 ) );
		if( m.length() == 1 ) m = "0" + m;
		String d = String.valueOf( this.getField( Calendar.DAY_OF_MONTH ) );
		if( d.length() == 1 ) d = "0" + d;
		return  y + "/" + m + "/" + d;

	}

	/**
	 * このクラスで保持、変更した日時を返します。
	 * 
	 * @return Date 保持、変更した日時
	 */
	public Date toDate()
	{
		return cal.getTime();
	}

	/**
	 * このクラスで保持、変更した日時を返します。
	 * 
	 * @return Calendar 保持、変更した日時
	 */
	public Calendar toCalendar()
	{
		return cal;
	}

	/**
	 * 各フィールド毎に最小値をセットします。 <BR>
	 * 年はセット出来ません。
	 * 
	 * @param field　Calendar.MONTH、DAY_OF_MONTH、HOUR・・・を指定出来ます。
	 * @return このクラス
	 */
	public MDWareDateUtility toMinValue( int field )
	{
		if( field == Calendar.MONTH )
		{
			cal.set( field, 0 );
		}
		else if( field == Calendar.DAY_OF_MONTH )
		{
			cal.set( field, 1 );
		}
		else if( field == Calendar.HOUR_OF_DAY || field == Calendar.MINUTE || field == Calendar.SECOND || field == Calendar.MILLISECOND )
		{
			cal.set( field, 0 );
		}
		else
		{
			throw new IllegalArgumentException( "パラメータにセットできる項目は、Calendar.MONTH、DAY_OF_MONTH、HOUR_OF_DAY、MINUTE、SECOND、MILLISECONDです。" );
		}
		return this;
	}

	/**
	 * 各フィールド毎に最大値をセットします。 <BR>
	 * 年はセット出来ません。
	 * 
	 * @param field　Calendar.MONTH、DAY_OF_MONTH、HOUR・・・を指定出来ます。
	 * @return このクラス
	 */
	public MDWareDateUtility toMaxValue( int field )
	{
		if( field == Calendar.MONTH )
		{
			cal.set( field, 11 );
		}
		else if( field == Calendar.DAY_OF_MONTH )
		{
			int y = cal.get( Calendar.YEAR );
			int m = cal.get( Calendar.MONTH ) + 1;
			if( m == 4 || m == 6 || m == 9 || m == 11 )
			{
				cal.set( field, 30 );
			}
			else if( m != 2 )
			{
				cal.set( field, 31 );
			}
			else if( y % 400 == 0 )
			{
				cal.set( field, 29 );
			}
			else if( y % 100 == 0 )
			{
				cal.set( field, 28 );
			}
			else if( y % 4 == 0 )
			{
				cal.set( field, 29 );
			}
			else
			{
				cal.set( field, 28 );
			}
		}
		else if( field == Calendar.HOUR_OF_DAY )
		{
			cal.set( field, 23 );
		}
		else if( field == Calendar.MINUTE )
		{
			cal.set( field, 59 );
		}
		else if( field == Calendar.SECOND )
		{
			cal.set( field, 59 );
		}
		else if( field == Calendar.MILLISECOND )
		{
			cal.set( field, 999 );
		}
		else
		{
			throw new IllegalArgumentException( "パラメータにセットできる項目は、Calendar.MONTH、DAY_OF_MONTH、HOUR_OF_DAY、MINUTE、SECOND、MILLISECONDです。" );
		}
		return this;
	}

	/**
	 * 第一引数の日時と第三引数の日時の間に第二引数の日時が存在するかを判定します。 <BR>
	 * 第二引数の日時が、間に入っているかどちらかと同じ場合はtrue <BR>
	 * 外の場合はfalse <BR>
	 * 
	 * @param befStr 第二引数より前にある事を期待する日時
	 * @param nowStr 判定する日時
	 * @param aftStr 第二引数より後にある事を期待する日時
	 * @return true:間にある false:外にある
	 */
	public static boolean isInTime( String befStr, String nowStr, String aftStr )
	{
		long bef = DateChanger.stringToDate( befStr ).getTime();
		long now = DateChanger.stringToDate( nowStr ).getTime();
		long aft = DateChanger.stringToDate( aftStr ).getTime();
		return bef <= now && now <= aft;
	}

	/**
	 * 第一引数の日時と第三引数の日時の間に第二引数の日時が存在するかを判定します。 <BR>
	 * 第二引数の日時が、間に入っているかどちらかと同じ場合はtrue <BR>
	 * 外の場合はfalse <BR>
	 * 
	 * @param bef 第二引数より前にある事を期待する日時
	 * @param now 判定する日時
	 * @param aft 第二引数より後にある事を期待する日時
	 * @return true:間にある false:外にある
	 */
	public static boolean isInTime( Date bef, Date now, Date aft )
	{
		String befStr = DateChanger.dateToString(bef);
		String nowStr = DateChanger.dateToString(now);
		String aftStr = DateChanger.dateToString(aft);
		return isInTime( befStr, nowStr, aftStr );
	}

	/**
	 * 第一引数の日時と第三引数の日時の間に第二引数の日時が存在するかを判定します。 <BR>
	 * 第二引数の日時が、間に入っているかどちらかと同じ場合はtrue <BR>
	 * 外の場合はfalse <BR>
	 * 
	 * @param bef 第二引数より前にある事を期待する日時
	 * @param now 判定する日時
	 * @param aft 第二引数より後にある事を期待する日時
	 * @return true:間にある false:外にある
	 */
	public static boolean isInTime( Calendar bef, Calendar now, Calendar aft )
	{
		String befStr = DateChanger.dateToString(bef.getTime());
		String nowStr = DateChanger.dateToString(now.getTime());
		String aftStr = DateChanger.dateToString(aft.getTime());
		return isInTime( befStr, nowStr, aftStr );
	}

	/**
	 * 日時として認識できるかを判定する。 <BR>
	 * DateChangerでは、日時として扱えない場合は例外を上げていて <BR>
	 * try-catchで囲む必要があった。 <BR>
	 * if文で利用出来るように考慮した。 <BR>
	 * 
	 * @param datetime DateChangerで理解できる形式
	 * @return true:日時として利用出来る false:利用不可能
	 */
	public static boolean isDateTime( String datetime )
	{
		//ADD by Tanigawa 2006/10/11 障害票№0156対応  START
		//datetimeがnullか空文字であればfalseを返す(日付ではないので…)
		if( datetime == null || datetime.trim().length() <= 0 ){
			return false;
		}
		//ADD by Tanigawa 2006/10/11 障害票№0156対応   END 
		
		try
		{
			DateChanger.stringToDate( datetime );
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}

	/**
	 * 日時と日時の差をミリ秒単位で返す。 <BR>
	 * 第一引数が前の日時、第二引数が後の日時を指定する。 <BR>
	 * ミリ秒単位で差を求めて返すため秒、分、時、日単位の時は計算する必要がある。 <BR>
	 * 秒：/1000 <BR>
	 * 分：/60000 <BR>
	 * 時：/3600000 <BR>
	 * 日：/86400000 <BR>
	 * 端数の計算はアプリによって違う(切捨て、切り上げ、四捨五入)為、ミリ秒単位の差で求める。 <BR>
	 * 
	 * @param befStr 前の日時
	 * @param aftStr 後の日時
	 * @return ミリ秒単位での差
	 */
	public static long diffTime( String befStr, String aftStr )
	{
		return DateChanger.stringToDate( aftStr ).getTime() - DateChanger.stringToDate( befStr ).getTime();
	}

	/**
	 * 日時と日時の差をミリ秒単位で返す。 <BR>
	 * 第一引数が前の日時、第二引数が後の日時を指定する。 <BR>
	 * ミリ秒単位で差を求めて返すため秒、分、時、日単位の時は計算する必要がある。 <BR>
	 * 秒：/1000 <BR>
	 * 分：/60000 <BR>
	 * 時：/3600000 <BR>
	 * 日：/86400000 <BR>
	 * 端数の計算はアプリによって違う(切捨て、切り上げ、四捨五入)為、ミリ秒単位の差で求める。 <BR>
	 * 
	 * @param bef 前の日時
	 * @param aft 後の日時
	 * @return ミリ秒単位での差
	 */
	public static long diffTime( Date bef, Date aft )
	{
		String befStr = DateChanger.dateToString(bef);
		String aftStr = DateChanger.dateToString(aft);
		return diffTime( befStr, aftStr );
	}
	
	/**
	 * 日時と日時の差をミリ秒単位で返す。 <BR>
	 * 第一引数が前の日時、第二引数が後の日時を指定する。 <BR>
	 * ミリ秒単位で差を求めて返すため秒、分、時、日単位の時は計算する必要がある。 <BR>
	 * 秒：/1000 <BR>
	 * 分：/60000 <BR>
	 * 時：/3600000 <BR>
	 * 日：/86400000 <BR>
	 * 端数の計算はアプリによって違う(切捨て、切り上げ、四捨五入)為、ミリ秒単位の差で求める。 <BR>
	 * 
	 * @param bef 前の日時
	 * @param aft 後の日時
	 * @return ミリ秒単位での差
	 */
	public static long diffTime( Calendar bef, Calendar aft )
	{
		String befStr = DateChanger.dateToString(bef.getTime());
		String aftStr = DateChanger.dateToString(aft.getTime());
		return diffTime( befStr, aftStr );
	}
	
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
	
	/**
	 * 指定日の曜日をjava.lang.Calendarのフィールドに基づいて返す。
	 * @param yyyyMMdd	日付
	 * @return
	 */
	public int getYoubiOfWeek() {
		
		String day = toStringYYYYMMDD();
		Calendar cal = Calendar.getInstance();
		String tmpDayOfWeek = new String();
		String ret = new String();
		
		int yyyy = Integer.parseInt(day.substring(0, 4));
		int mm	 = Integer.parseInt(day.substring(4, 6));
		int dd	 = Integer.parseInt(day.substring(6, 8));
		
		cal.set(yyyy, mm - 1, dd);
		
		ret = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		
		return Integer.parseInt(ret);
	}
	
}