package mdware.common.util;

import java.util.*;
import java.math.*;
import java.text.*;
import mdware.common.util.*;
import jp.co.vinculumjapan.stc.util.calendar.StcStockDate;
/**
 * <p>タイトル: ＲＢＳＩＴＥ</p>
 * <p>説明: 2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vincuram Japan co</p>
 * @author 未入力
 * @version 1.0
 */

public class DateChanger {
    /**
     * yy年mm月dd日の形で月日に関しては１０未満は１桁で出力する。
     * @param date String
     * @return String 変換後の文字列
     */
    public static String toShortYearAndJp( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        String wk = "";
        wk += ssdt.getYear().substring(2,4);
        wk += "年";
        wk += ssdt.getMonth().replace('0',' ');
        wk += "月";
        //日付の1文字目が'0'だったときはそれを省く。　by yamanaka 20020920
                if(ssdt.getDay().charAt(0) == '0')
                  wk += ssdt.getDay().replace('0',' ');
                else
                  wk += ssdt.getDay();
        wk += "日";
        return wk;
    }
    /**
     * yy/mm/ddの形で月日に関しては１０未満は１桁で出力する。
     * @param date String
     * @return String 変換後の文字列
     */
    public static String toShortYearAndSla( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        String wk = "";
        wk += ssdt.getYear().substring(2,4);
        wk += "/";

// 20020110 @UPD nakazawa 全ての0を消してしまわないように start
//		wk += ssdt.getMonth().replace('0',' ');
//		wk += "/";
//		wk += ssdt.getDay().replace('0',' ');

        //1文字目が'0'だったときはそれを省く
        if(ssdt.getMonth().charAt(0) == '0'){
            wk += ssdt.getMonth().replace('0',' ');
        } else{
            wk += ssdt.getMonth();
        }
        wk += "/";
        //1文字目が'0'だったときはそれを省く
        if(ssdt.getDay().charAt(0) == '0'){
            wk += ssdt.getDay().replace('0',' ');
        } else{
            wk += ssdt.getDay();
        }
// 20020110 @UPD nakazawa 全ての0を消してしまわないように end

        return wk;
    }
    /**
     * 区切り文字の存在しない１４桁の年月日時分秒を返す。
     * yyyy/mm/dd hh/mi/ss → yyyymmddhhmiss<BR>
     * yyyymmddhhmiss → yyyymmddhhmiss<BR>
     * 2002/8/1 12/1/5 → 20020801120105<BR>
     * 2002/08/01 → 20020801000000<BR>
     * @param date String
     * @return String 区切り文字の存在しない１４桁の年月日時分秒を返す
     */
    public static String toFlatTimeStamp( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        return ssdt.toString().substring(0,14);
    }

    /**
     * 区切り文字の存在しない１２桁の年月日時分を返す。
     * yyyy/mm/dd hh/mi/ss → yyyymmddhhmi<BR>
     * yyyymmddhhmiss → yyyymmddhhmi<BR>
     * 2002/8/1 12/1/5 → 200208011201<BR>
     * 2002/08/01 → 200208010000<BR>
     * @param date String
     * @return String 区切り文字の存在しない１２桁の年月日時分秒を返す
     */
    public static String toFlatTimeStampNoSecond( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        return ssdt.toString().substring(0,12);
    }

    /**
     * 区切り文字の存在しない８桁の年月日を返す。
     * yyyy/mm/dd hh/mi/ss → yyyymmdd<BR>
     * yyyymmddhhmiss → yyyymmdd<BR>
     * 2002/8/1 12/1/5 → 20020801<BR>
     * 2002/08/01 → 20020801<BR>
     * @param date String
     * @return String 区切り文字の存在しない８桁の年月日を返す
     */
    public static String toFlatDate( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        return ssdt.toString().substring(0,8);
    }
    /**
     * 区切り文字のある、１６文字の年月日時分秒を返す。
     * yyyy/mm/dd hh/mi/ss → yyyy/mm/dd hh:mi<BR>
     * yyyymmddhhmiss → yyyy/mm/dd hh:mi<BR>
     * 2002/8/1 12/1/5 → 2002/08/01 12:01<BR>
     * 2002/08/01 → 2002/08/01 00:00<BR>
     * @param date String
     * @return String 区切り文字のある年月日時分秒を返す
     */
    public static String toSeparatorTimeStampNoSecond( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        String wk = "";
        wk += ssdt.getYear();
        wk += "/";
        wk += ssdt.getMonth();
        wk += "/";
        wk += ssdt.getDay();
        wk += " ";
        wk += ssdt.getHour();
        wk += ":";
        wk += ssdt.getMinut();
        return wk;
    }
    /**
     * 区切り文字のある、１９文字の年月日時分秒を返す。
     * yyyy/mm/dd hh/mi/ss → yyyy/mm/dd hh:mi:ss<BR>
     * yyyymmddhhmiss → yyyy/mm/dd hh:mi:ss<BR>
     * 2002/8/1 12/1/5 → 2002/08/01 12:01:05<BR>
     * 2002/08/01 → 2002/08/01 00:00:00<BR>
     * @param date String
     * @return String 区切り文字のある年月日時分秒を返す
     */
    public static String toSeparatorTimeStamp( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        String wk = "";
        wk += ssdt.getYear();
        wk += "/";
        wk += ssdt.getMonth();
        wk += "/";
        wk += ssdt.getDay();
        wk += " ";
        wk += ssdt.getHour();
        wk += ":";
        wk += ssdt.getMinut();
        wk += ":";
        wk += ssdt.getSecond();
        return wk;
    }
    /**
     * 区切り文字のある、１０文字の年月日を返す。
     * yyyy/mm/dd hh/mi/ss → yyyy/mm/dd<BR>
     * yyyymmddhhmiss → yyyy/mm/dd<BR>
     * 2002/8/1 12/1/5 → 2002/08/01<BR>
     * 2002/08/01 → 2002/08/01 00:00:00<BR>
     * @param date String
     * @return String 区切り文字のある年月日時分秒を返す
     */
    public static String toSeparatorDate( String date )
    {
        StcStockDate ssdt = new StcStockDate();
        ssdt.setDate(stringToDate(date));
        if( ssdt.getDate() == null )
            return "";
        String wk = "";
        wk += ssdt.getYear();
        wk += "/";
        wk += ssdt.getMonth();
        wk += "/";
        wk += ssdt.getDay();
        return wk;
    }

    /**
     * 渡された文字列を解析しDateに変換し返す。
     * @param String yyyymmddhhmissfff or yyyy/mm/dd hh:mi:ss.fffなど
     * @return Date
     */
    public static Date stringToDate( String dateStr )
    {
        if( dateStr == null || dateStr.trim().length() == 0 )
        {
            return (Date)null;
        }
        String wkStr = "";
        boolean isSep = false;
        //まずセパレータ文字がつながっていたときの事を考え一文字にする。
        for( int i = 0; i < dateStr.length(); i++ )
        {
            try
            {
                String c = (new Integer(dateStr.substring(i,i+1))).toString();
                wkStr += c;
                isSep = false;
            }
            catch(Exception e)
            {
                if( !isSep )
                    wkStr += " ";
                isSep = true;
            }
        }
        wkStr = wkStr.trim();
        // 日付と時間の入力された値がおかしくないかの確認を行う。(追加 20020812 yoshi)
        wkStr = checkDateStr( wkStr );

        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.parseInt(wkStr.substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(wkStr.substring(4,6)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(wkStr.substring(6,8)));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(wkStr.substring(8,10)));
        cal.set(Calendar.MINUTE, Integer.parseInt(wkStr.substring(10,12)));
        cal.set(Calendar.SECOND, Integer.parseInt(wkStr.substring(12,14)));
        cal.set(Calendar.MILLISECOND, Integer.parseInt(wkStr.substring(14)));

        return cal.getTime();
    }
    /**
     * 第一次変換の終わった文字列をフラットな形の日時に変換し返す
     * @param ckDate
     * @return
     */
    private static String checkDateStr( String ckDate )
    {
        int startPos = 4;
        String monoStr[] = new String[7];
        if( ckDate.length() < 4 )
            throw new java.lang.IllegalArgumentException("年を４桁で指定してください。");
        StringBuffer wkStr = new StringBuffer(ckDate.substring(0,4));
        int pos = 0;
        for( ; startPos < ckDate.length(); startPos++ )
        {
            if( (pos != 6) && wkStr.toString().length() >= 2 || !isNumeric(ckDate.charAt(startPos)) )
            {
                monoStr[pos] = wkStr.toString();
                wkStr = new StringBuffer();
                pos++;
            }
            if( isNumeric(ckDate.charAt(startPos)) )
                wkStr.append(ckDate.charAt(startPos));
        }
        if( wkStr.toString().length() > 0 )
        {
            monoStr[pos] = wkStr.toString();
        }
        int monoCal[] = new int[7];
        for( int i = 0; i < 7; i++ )
// 20020311 @UPD deguchi YYYYMMDDの7桁入力禁止により start
//			if( monoStr[i] != null && monoStr[i].length() > 0 )
            if( monoStr[i] != null && monoStr[i].length() > 1 )
// 20020311 @UPD deguchi YYYYMMDDの7桁入力禁止により end
                monoCal[ i ] = Integer.parseInt(monoStr[i]);
            else
                monoCal[i] = 0;
        return checkDateIntArray( monoCal );
    }

    private static String checkDateIntArray( int wk[] )
    {
        boolean isLeap = false;
        if( wk[0] <= 0 )
            throw new IllegalArgumentException("年を確認してください。");
        if( wk[1] < 1 || wk[1] > 12 )
            throw new IllegalArgumentException("月を確認してください。");
        if( (wk[0] % 400) == 0 || (wk[0] % 100) != 0 && (wk[0] % 4) == 0 )
            isLeap = true;
        if( wk[2] < 1 || wk[2] > 31 )
            throw new IllegalArgumentException("日を確認してください。");
        if( wk[1] == 2 && isLeap && wk[2] > 29 )
            throw new IllegalArgumentException("日を確認してください。");
        if( wk[1] == 2 && !isLeap && wk[2] > 28 )
            throw new IllegalArgumentException("日を確認してください。");
        if( ( wk[1] == 4 || wk[1] == 6 || wk[1] == 9 ||wk[1] == 11 ) && wk[2] > 30 )
            throw new IllegalArgumentException("日を確認してください。");
        if( wk[3] < 0 || wk[3] > 24 )
            throw new IllegalArgumentException("時を確認してください。");
        if( wk[4] < 0 || wk[4] > 59 )
            throw new IllegalArgumentException("分を確認してください。");
        if( wk[5] < 0 || wk[5] > 59 )
            throw new IllegalArgumentException("秒を確認してください。");
        if( wk[6] < 0 || wk[6] > 999 )
            throw new IllegalArgumentException("ミリ秒を確認してください。");
        long dt = 0;
        long tm = 1000000000;
        dt += wk[0] * 10000;
        dt += wk[1] * 100;
        dt += wk[2];
        tm += wk[3] * 10000000;
        tm += wk[4] * 100000;
        tm += wk[5] * 1000;
        tm += wk[6];
        String retStr = Long.toString(dt) + Long.toString(tm).substring(1);
        return retStr;
    }
    private static boolean isNumeric( char c )
    {
        if( c < '0' || '9' < c )
            return false;
        return true;
    }

// 20030702 @UPD deguchi 帳票フォーマット追加より start
    /**
     * 年月日を「-」繋ぎ(yy-mm-dd)で返す。（帳票年月日用）
     * yyyymmdd → yy-mm-dd<BR>
     * yyyy/mm/dd hh/mi/ss → yy-mm-dd<BR>
     * yyyymmddhhmiss → yy-mm-dd<BR>
     * 20020801 → 02-08-01<BR>
     * 2002/8/1 12/1/5 → 02-08-01<BR>
     * 2002/08/01 → 02-08-01<BR>
     * @param date String
     * @return String 区切り文字の存在しない８桁の年月日を返す
     */
    public static String toFlatDateForPrint( String date ) {
        String flatDate = "";
        String retDate = "";
        flatDate = toFlatDate( date ).trim();
        retDate = flatDate.substring(2,4) + "-" + flatDate.substring(4,6) + "-" + flatDate.substring(6);
        return retDate;
    }
// 20030702 @UPD deguchi 帳票フォーマット追加より end






    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 20031002 追加分 start
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 曜日（日本語）テーブルです。
     */
    private static final String[] YOUBI = {"日","月","火","水","木","金","土"};

    /**
     * 空文字定数。"" を保持します。
     */
    private static final String BLANK = "";

    /**
     * toSeparatorDate(String val) の BigDecimal 対応版です。
     * 区切り文字のある、１０文字の年月日を返す。
     * yyyy/mm/dd hh/mi/ss → yyyy/mm/dd<BR>
     * yyyymmddhhmiss → yyyy/mm/dd<BR>
     * 2002/8/1 12/1/5 → 2002/08/01<BR>
     * 2002/08/01 → 2002/08/01 00:00:00<BR>
     * @param date String
     * @return String 区切り文字のある年月日時分秒を返す
     * @author M.Ashizawa
     */
    public static String toSeparatorDate(BigDecimal val) {
        String s = null;
        s = (val != null ? val.toString() : null);
        return toSeparatorDate(s);
    }

    /**
     * yyyyMMdd 形式の日付 String の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 時間を指定された時間フィールドに加えます。
     * たとえば、Calendar の現在時刻から 5 日を引くには、以下のように呼び出します。 <br>
     * <br>
     * add("20030101", Calendar.DATE, -5) <br>
     *
     * @param yyyyMMdd  演算する日付の String（yyyyMMdd フォーマット)
     * @param field     時間フィールド
     * @param amount    フィールドに追加される日付または時刻の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String add(String yyyyMMdd, int field, int amount) {
        String rtnDate = null;
        if (yyyyMMdd != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);      // 日付を厳密に解釈します 。
            Calendar calendar = sdf.getCalendar();
            try {
                calendar.setTime( sdf.parse(yyyyMMdd) );
                calendar.add( field, amount );
                rtnDate = sdf.format(calendar.getTime());
            } catch(ParseException e) {
                ;
            }
        }
        return rtnDate;
    }

    /**
     * yyyyMMdd 形式の日付 String の日数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 日数を指定された日数フィールドに加えます。
     *
     * @param yyyyMMdd  演算する日付の String（yyyyMMdd フォーマット)
     * @param amount    フィールドに追加される日数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addDate(String yyyyMMdd, int amount) {
        return add(yyyyMMdd, Calendar.DATE, amount);
    }

    /**
     * 8 桁の日付 BigDecimal の日数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 日数を指定された日数フィールドに加えます。
     *
     * @param val       演算する日付の 8 桁の BigDecimal
     * @param amount    フィールドに追加される日数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addDate(BigDecimal val, int amount) {
        if (val == null) {
            return null;
        }
        return add( val.toString(), Calendar.DATE, amount);
    }

    /**
     * yyyyMMdd 形式の日付 String の月数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 月数を指定された月数フィールドに加えます。
     *
     * @param yyyyMMdd  演算する日付の String（yyyyMMdd フォーマット)
     * @param amount    フィールドに追加される月数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addMonth(String yyyyMMdd, int amount) {
        return add(yyyyMMdd, Calendar.MONTH, amount);
    }

    /**
     * 8 桁の日付 BigDecimal の月数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 月数を指定された月数フィールドに加えます。
     *
     * @param val       演算する日付の 8 桁の BigDecimal
     * @param amount    フィールドに追加される月数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addMonth(BigDecimal val, int amount) {
        if (val == null) {
            return null;
        }
        return add( val.toString(), Calendar.MONTH, amount);
    }

    /**
     * yyyyMMdd 形式の日付 String の年数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 年数を指定された月数フィールドに加えます。
     *
     * @param yyyyMMdd  演算する日付の String（yyyyMMdd フォーマット)
     * @param amount    フィールドに追加される年数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addYear(String yyyyMMdd, int amount) {
        return add(yyyyMMdd, Calendar.YEAR, amount);
    }

    /**
     * 8 桁の日付 BigDecimal の年数の演算機能です。
     * Calendar の規則に従って、指定された (符合付きの) 年数を指定された月数フィールドに加えます。
     *
     * @param val       演算する日付の 8 桁の BigDecimal
     * @param amount    フィールドに追加される年数の量
     * @return  加算（または減算）された日付の String（yyyyMMdd フォーマット)。
     *          変換できなかった場合は null を返します。
     * @author M.Ashizawa
     */
    public static String addYear(BigDecimal val, int amount) {
        if (val == null) {
            return null;
        }
        return add( val.toString(), Calendar.YEAR, amount);
    }

    /**
     * yyyyMMdd 形式の日付 String を元に java.util.Calendar オブジェクトを返します。
     *
     * @param yyyyMMdd  演算する日付の String（yyyyMMdd フォーマット)
     * @return  変換された Calendar オブジェクト。<br>変換に失敗した場合は、値が不定の Calendar オブジェクトを返します
     * @author M.Ashizawa
     */
    public static Calendar getCalendar(String yyyyMMdd) {
        // yyyyMMdd を Calendar へ
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);      // 日付を厳密に解釈します。
        Calendar cal = sdf.getCalendar();
        try {
            cal.setTime( sdf.parse( yyyyMMdd + "000000" ));
        } catch(ParseException e) {
            ;   // 例外を握りつぶします。
        }
        return cal;
    }

    /**
     * yyyyMMdd の日付から曜日（日本語）を求めます。
     *
     * @param   yyyyMMdd の BigDecimal 値
     * @return  曜日（日、月、火、水、木、金、土）の String。変換できない場合は空文字 &quot;&quot; を返します
     */
    public static String getYoubi(BigDecimal yyyyMMdd) {
        if (yyyyMMdd == null) {
            return BLANK;
        }
        return getYoubi(yyyyMMdd.toString());
    }

    /**
     * yyyyMMdd の日付から曜日（日本語）を求めます。
     *
     * @param   yyyyMMdd の String 値
     * @return  曜日（日、月、火、水、木、金、土）の String。変換できない場合は空文字 &quot;&quot; を返します
     */
    public static String getYoubi(String yyyyMMdd) {
        if (yyyyMMdd == null) {
            return BLANK;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = sdf.getCalendar();
        try {
            calendar.setTime( sdf.parse(yyyyMMdd.toString()) );
            return YOUBI[ calendar.get(Calendar.DAY_OF_WEEK) - 1 ];

        } catch(ParseException e) {
            return BLANK;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 20031002 追加分 end
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void main(String argv[]) throws Exception
    {/*
        String wk = null;
        wk = "20020801123005123";
        System.out.println("\n" + wk);
        System.out.println(DateChanger.toFlatTimeStamp(wk));
        System.out.println(DateChanger.toSeparatorTimeStamp(wk));
        System.out.println(DateChanger.toFlatTimeStampNoSecond(wk));
        System.out.println(DateChanger.toSeparatorTimeStampNoSecond(wk));
        System.out.println(DateChanger.toFlatDate(wk));
        System.out.println(DateChanger.toSeparatorDate(wk));

        wk = "";
        System.out.println("\n" + wk);
        System.out.println(DateChanger.toFlatTimeStamp(wk));
        System.out.println(DateChanger.toSeparatorTimeStamp(wk));
        System.out.println(DateChanger.toFlatTimeStampNoSecond(wk));
        System.out.println(DateChanger.toSeparatorTimeStampNoSecond(wk));
        System.out.println(DateChanger.toFlatDate(wk));
        System.out.println(DateChanger.toSeparatorDate(wk));

        wk = "2002/08/01 12:30:05.123";
        System.out.println("\n" + wk);
        System.out.println(DateChanger.toFlatTimeStamp(wk));
        System.out.println(DateChanger.toSeparatorTimeStamp(wk));
        System.out.println(DateChanger.toFlatTimeStampNoSecond(wk));
        System.out.println(DateChanger.toSeparatorTimeStampNoSecond(wk));
        System.out.println(DateChanger.toFlatDate(wk));
        System.out.println(DateChanger.toSeparatorDate(wk));

        wk = "2002/8/1 1:2:5.1";
        System.out.println("\n" + wk);
        System.out.println(DateChanger.toFlatTimeStamp(wk));
        System.out.println(DateChanger.toSeparatorTimeStamp(wk));
        System.out.println(DateChanger.toFlatTimeStampNoSecond(wk));
        System.out.println(DateChanger.toSeparatorTimeStampNoSecond(wk));
        System.out.println(DateChanger.toFlatDate(wk));
        System.out.println(DateChanger.toSeparatorDate(wk));

        wk = "2002/08/01";
        System.out.println("\n" + wk);
        System.out.println(DateChanger.toFlatTimeStamp(wk));
        System.out.println(DateChanger.toSeparatorTimeStamp(wk));
        System.out.println(DateChanger.toFlatTimeStampNoSecond(wk));
        System.out.println(DateChanger.toSeparatorTimeStampNoSecond(wk));
        System.out.println(DateChanger.toFlatDate(wk));
        System.out.println(DateChanger.toSeparatorDate(wk));
*/
        String wk = "20040130";
        System.out.println("\n" + wk);
        System.out.println(
            DateDiff.getDiffDays(DateUtility.today() ,
                DateChanger.addMonth(DateUtility.today() , 1).substring(0 , 6) + "01" )
        );
        System.out.println( DateDiff.getDiffDays( wk , "20030202") );
        System.out.println( DateChanger.addMonth(wk , 1)  );
        System.out.println(DateChanger.addDate(wk, 1) );
    }
}