package mdware.common.util;

import java.util.Date;
import jp.co.vinculumjapan.stc.util.calendar.StcFromToDate;

/**
 * <p>タイトル: ＲＢＳＩＴＥ</p>
 * <p>説明: 2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author 未入力
 * @version 1.0
 */

public class FromToDate {
	StcFromToDate fromTo = null;
	public FromToDate() {
		fromTo = new StcFromToDate(-1,0);
	}
	public FromToDate(int from,int to) {
		fromTo = new StcFromToDate(from,to);
	}
	public void setFrom( Date date )
	{
		fromTo.setFromDate(date);
	}
	public void setTo( Date date )
	{
		fromTo.setToDate(date);
	}
	public void setFrom( String date )
	{
		fromTo.setFromDate(DateChanger.stringToDate(date));
	}
	public void setTo( String date )
	{
		fromTo.setToDate(DateChanger.stringToDate(date));
	}
	public String getFromDate()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += fromTo.getFromDateMonth();
		retStr += fromTo.getFromDateDay();
		return retStr;
	}
	public String getFromDateAndTime()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += fromTo.getFromDateMonth();
		retStr += fromTo.getFromDateDay();
		retStr += fromTo.getFromDateHour();
		retStr += fromTo.getFromDateMinut();
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromTime()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateHour();
		retStr += fromTo.getFromDateMinut();
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromDateSla()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "/";
		retStr += fromTo.getFromDateMonth();
		retStr += "/";
		retStr += fromTo.getFromDateDay();
		return retStr;
	}
	public String getFromDateAndTimeSla()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "/";
		retStr += fromTo.getFromDateMonth();
		retStr += "/";
		retStr += fromTo.getFromDateDay();
		retStr += " ";
		retStr += fromTo.getFromDateHour();
		retStr += ":";
		retStr += fromTo.getFromDateMinut();
		retStr += ":";
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromTimeSepa()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateHour();
		retStr += ":";
		retStr += fromTo.getFromDateMinut();
		retStr += ":";
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromDateHaifun()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "-";
		retStr += fromTo.getFromDateMonth();
		retStr += "-";
		retStr += fromTo.getFromDateDay();
		return retStr;
	}
	public String getFromDateAndTimeHaifun()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "-";
		retStr += fromTo.getFromDateMonth();
		retStr += "-";
		retStr += fromTo.getFromDateDay();
		retStr += " ";
		retStr += fromTo.getFromDateHour();
		retStr += ":";
		retStr += fromTo.getFromDateMinut();
		retStr += ":";
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromDateJP()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "年";
		retStr += fromTo.getFromDateMonth();
		retStr += "月";
		retStr += fromTo.getFromDateDay();
		retStr += "月";
		return retStr;
	}
	public String getFromDateAndTimeJP()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateYear();
		retStr += "-";
		retStr += fromTo.getFromDateMonth();
		retStr += "-";
		retStr += fromTo.getFromDateDay();
		retStr += " ";
		retStr += fromTo.getFromDateHour();
		retStr += ":";
		retStr += fromTo.getFromDateMinut();
		retStr += ":";
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}
	public String getFromTimeJP()
	{
		String retStr = "";
		if( fromTo.getFromDate() == null )
			return "";
		retStr += fromTo.getFromDateHour();
		retStr += ":";
		retStr += fromTo.getFromDateMinut();
		retStr += ":";
		retStr += fromTo.getFromDateSecond();
		return retStr;
	}

	public String getToDate()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += fromTo.getToDateMonth();
		retStr += fromTo.getToDateDay();
		return retStr;
	}
	public String getToDateAndTime()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += fromTo.getToDateMonth();
		retStr += fromTo.getToDateDay();
		retStr += fromTo.getToDateHour();
		retStr += fromTo.getToDateMinut();
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToTime()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateHour();
		retStr += fromTo.getToDateMinut();
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToDateSla()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "/";
		retStr += fromTo.getToDateMonth();
		retStr += "/";
		retStr += fromTo.getToDateDay();
		return retStr;
	}
	public String getToDateAndTimeSla()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "/";
		retStr += fromTo.getToDateMonth();
		retStr += "/";
		retStr += fromTo.getToDateDay();
		retStr += " ";
		retStr += fromTo.getToDateHour();
		retStr += ":";
		retStr += fromTo.getToDateMinut();
		retStr += ":";
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToTimeSepa()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateHour();
		retStr += ":";
		retStr += fromTo.getToDateMinut();
		retStr += ":";
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToDateHaifun()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "-";
		retStr += fromTo.getToDateMonth();
		retStr += "-";
		retStr += fromTo.getToDateDay();
		return retStr;
	}
	public String getToDateAndTimeHaifun()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "-";
		retStr += fromTo.getToDateMonth();
		retStr += "-";
		retStr += fromTo.getToDateDay();
		retStr += " ";
		retStr += fromTo.getToDateHour();
		retStr += ":";
		retStr += fromTo.getToDateMinut();
		retStr += ":";
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToDateJP()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "年";
		retStr += fromTo.getToDateMonth();
		retStr += "月";
		retStr += fromTo.getToDateDay();
		retStr += "月";
		return retStr;
	}
	public String getToDateAndTimeJP()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateYear();
		retStr += "-";
		retStr += fromTo.getToDateMonth();
		retStr += "-";
		retStr += fromTo.getToDateDay();
		retStr += " ";
		retStr += fromTo.getToDateHour();
		retStr += ":";
		retStr += fromTo.getToDateMinut();
		retStr += ":";
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public String getToTimeJP()
	{
		String retStr = "";
		if( fromTo.getToDate() == null )
			return "";
		retStr += fromTo.getToDateHour();
		retStr += ":";
		retStr += fromTo.getToDateMinut();
		retStr += ":";
		retStr += fromTo.getToDateSecond();
		return retStr;
	}
	public static void main(String argv[])
		throws Exception
	{
		FromToDate ftd = new FromToDate();
		ftd.setFrom("");
		ftd.setTo("");
		System.out.println(ftd.getFromDateAndTimeSla());
		System.out.println(ftd.getToDateAndTimeSla());

		ftd.setFrom("西暦2002年8月19日13時40分27秒003ミリ秒");
		ftd.setTo("西暦2002年9月1日3時5分7秒2ミリ秒");
		System.out.println(ftd.getFromDateAndTimeSla());
		System.out.println(ftd.getToDateAndTimeSla());

		ftd.setFrom("20020819134027003");
		ftd.setTo("20020901030507200");
		System.out.println(ftd.getFromDateAndTimeSla());
		System.out.println(ftd.getToDateAndTimeSla());

		ftd.setFrom("200");
		ftd.setTo("200");
		System.out.println(ftd.getFromDateAndTimeSla());
		System.out.println(ftd.getToDateAndTimeSla());
	}
}