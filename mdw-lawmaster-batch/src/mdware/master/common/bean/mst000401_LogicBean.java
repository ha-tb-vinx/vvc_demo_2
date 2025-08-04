/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.0
 *            1.1  D.Matsumoto 2006.01.18 4桁販区対応  
 * @see なし								
 */
package mdware.master.common.bean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.stc.util.db.PreparedStatementDataBase;
import mdware.common.util.date.MDWareDateUtility;
import mdware.common.util.text.MDWareStringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst004801_NewSyoriRiyoukbDictionary;
import mdware.master.common.dictionary.mst004901_UpdateSyoriRiyoukbDictionary;
import mdware.master.common.dictionary.mst005001_DelRiyoukbDictionary;
import mdware.master.common.dictionary.mst005101_ViewRiyoukbDictionary;
import mdware.master.common.dictionary.mst005201_CsvRiyoukbDictionary;
import mdware.master.common.dictionary.mst005301_PrnRiyoukbDictionary;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 *            1.1 （2006.01.18）D.Matsumoto  4桁販区対応
 * @see なし
 * 変更履歴 :（2006.03.23）K.Satobo 削除時の下位コード存在チェック対応
 * 変更履歴 :（2006.09.26）K.Tanigawa 障害票№0051対応 郵便番号フォーマットメソッド追加
 */
public class mst000401_LogicBean {
	protected static final InfoStrings infoStrings = InfoStrings.getInstance();
	/* デフォルト値 数値データ用 */
	public static final String DEFAULT_VL = "0";
	/** 
	 * SQLのLIKE文用のエスケープ文字列<br>
	 * 文字列連結用に文字列の前後に半角空白を設定している。
	 */
	public static final String LIKE_ESCAPE_STR = " ESCAPE '@' ";
	public static Map errMap = null;
	
//	パフォーマンス改善対応　都度有効日をＤＢに確認するのではなく、command取得時に一度だけ取得しキャッシュするようにする hiu@vjc 20060901
	static String masterYukoDateStart="";
	
	static String masterYukoDateEnd="";

	/**
	 * str中のsrcをdestに置換します。<br>
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.substitute("abcdeefg", "cde", "CDE") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、長さ0の文字列になります。<br>
	 * 引数src及びdestがnullの場合は、引数strの値をそのまま返します。<br>
	 * <br>
	 * @param str 文字列
	 * @param src 置換する文字列
	 * @param dest 置換後の文字列
	 */
	public static String substitute( String str, String src, String dest ){

		if(str == null){
			// ----- 空文字をリターン
			return "";
		}
		else if((src == null) || (dest == null)){
			// ----- 置換せずにそのままリターン
			return str;
		}
		else{
			// ----- 置換処理してリターン
			if( str.indexOf( src ) != -1 ){

				int pos = str.length();
				StringBuffer sb = new StringBuffer( str );

				while( (pos = str.lastIndexOf( src, pos - 1 ) ) != -1 ){
					sb.delete( pos, pos + src.length()  );
					sb.insert( pos, dest );
				}

				str = sb.toString();
			}

			return str;
		}
	}
	/**
	 * str中のshift_jisに変換しバイト数を返します。<br>
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.shift_length("あああ") -&gt; 6<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、0を返します。<br>
	 * <br>
	 * @param str 文字列
	 */
	public static int shift_length( String str ){
		int iLength = 0;
		jp.co.vinculumjapan.stc.log.StcLog stcLog = jp.co.vinculumjapan.stc.log.StcLog.getInstance();
		if(str == null){
			iLength = 0;
		}else{
			try{
				iLength = str.getBytes("shift_jis").length;
				
			}catch(Exception e){
				stcLog.getAccess().fatal("StrUtils#shift_lengthにおいて例外が発生しました。内容は以下の通りです。",e);
				stcLog.getAccess().fatal(e.toString());
			}
		}

		return iLength;
	}
	/**
	 * SQL文字列用にシングルクォーテーション対応を行う。<br>
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.convertString("ああ'あ") -&gt; "ああ''あ"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、nullを返します。<br>
	 * <br>
	 * @param strSql 置換対象文字列
	 */
	public static String convertString(String strSql)
	{
		if( strSql == null ){
			return null;
		}
		String work =null;
		work = strSql.replaceAll( "'", "''" );
		return work;
	}

	/**
	 * SQLのLIKE演算子の文字列にエスケープ文字を適用する<br>
	 * 「%」「_」「'」を変換する<br>
	 * エスケープ文字自身「@」も変換します。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.convertString("A%B_C'D@E") -&gt; "A@%B@_C''D@@E"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、nullを返します。<br>
	 * <br>
	 * @param strSql 置換対象文字列
	 */
	public static String convertLIKEString(String strSql)
	{
		if( strSql == null ){
			return null;
		}
		String work = strSql;
		work = work.replaceAll( "@", "@@" );
		work = work.replaceAll( "%", "@%" );
		work = work.replaceAll( "_", "@_" );
		work = work.replaceAll( "'", "''" );
		return work;
	}

	/**
	 * 数字を3桁ごとにカンマ区切りする
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.addComma("9999999") -&gt; "9,999,999"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、nullを返します。<br>
	 * <br>
	 * @param motoNum 変換対象文字列
	 */
	public static String addComma(String motoNum){
		if( motoNum == null ){
			return null;
		}
		String strWork= "";
		int intlen = motoNum.length();
		int intCnt = 0;
		for (intCnt=intlen-1; intCnt>= 0; intCnt--)
		{
			strWork = motoNum.charAt(intCnt) + strWork;
			if ((((intlen - intCnt) % 3) == 0) && ((intCnt > 1) || ((intCnt == 1) && (motoNum.charAt(0) != '-')))){
				strWork = "," + strWork;
			}
		}
		return strWork;
	}

	/**
	 * カンマ区切りされた数字からカンマを削除する
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.addComma("9999999") -&gt; "9,999,999"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、nullを返します。<br>
	 * <br>
	 * @param motoNum 変換対象文字列
	 */
	public static String removeComma( String motoNum )
	{
		if( motoNum == null )
			motoNum = null;
		String strWork = "";
		strWork = motoNum.replaceAll( ",", "" );
		return strWork;
	}
	
	/**
	* システム日付の文字列の取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSystemDate() -&gt; "システム日付(YYYY/MM/DD)"<br>
	 * <br>
	*@return 日付の文字列を返す
	*/
	public static String getSystemDate() {
		Date 		sysDate = null;    // システム日付(Date)
		String 		strDate = null;    //システム日付(String)
		DateFormat	dateFormat = null; //日付フォーマット用

		Calendar cal = Calendar.getInstance( Locale.JAPAN );
		// システム日付を取得する
		sysDate = cal.getTime();
		// 年月日形式の場合
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// フォーマット変換して文字列を作成
		strDate = dateFormat.format( sysDate );
		// 文字列を返す
		return strDate;
	}

	/**
	 * 指定したマックス桁数が足らない場合は、前ゼロ埋めして返す
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.formatZero("123","5") -&gt; "00123"<br>
	 * <br>
	 * 引数strOldDataがnullの場合の戻り値は、長さ0の文字列になります。
	 * 引数strOldDataが長さ0の文字列の場合の戻り値は、引数strOldDataの値をそのまま返します。
	 * @param strOldData フォーマット元の文字列
	 * @param iMaxLength フォーマットサイズ
	 * @return	String 前ゼロ埋めした結果
	 */	
	public static String formatZero( String strOldData,int iMaxLength ) {

		String strNewData = "";   //戻り値用
		if (null == strOldData){
			return strNewData;
		}
		strOldData = strOldData.trim();
		int iLength = strOldData.length();    //Baseのレングス
		if (0 != iLength){
			// 桁数足りない場合は、０埋め
			for (int i = 0; i< (iMaxLength - iLength); i++){
				strNewData = strNewData.concat( "0" );
			}
		}
		strNewData = strNewData.concat( strOldData );
		return strNewData;
	}

	/**
	 * 指定した文字列(整数)の先頭文字が[+]の場合は、[+]を取って返す
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.formatNumber("+123") -&gt; "123"<br>
	 * <br>
	 * @param strOldData フォーマット元の文字列(整数)
	 * @return	String フォーマット結果
	 */	
	public static String formatNumber( String strOldData ) {

		String strNewData = "";   //戻り値用
		int iStart = 0;           //処理文字位置
		if (null == strOldData){
			return strNewData;
		}
		strOldData = strOldData.trim();
		if (0 != strOldData.length()){
			if ( '+' == strOldData.charAt( 0 )  ){
				iStart = 1;
			}
			strNewData = strOldData.substring(iStart );
		}
		return strNewData;
	}

	/**
	 * 指定した文字列(正負整数)のフォーマットを行う
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.formatSingleLong("+0123") -&gt; "123"<br>
	 * <br>
	 * 指定した文字列(正負整数)の先頭文字が[+]の場合は、[+]を取る
	 * 指定した文字列(正負整数)の符号を除いた先頭文字が[0]の場合は、[0]を取る
	 * @param strOldData フォーマット元の文字列(正負整数)
	 * @return	String フォーマット結果
	 */	
	public static String formatSingleLong(String strOldData) {
		long lData;   //編集用ワーク
		strOldData = mst000401_LogicBean.formatNumber(strOldData);
		if ( 0 == strOldData.length() ){
			strOldData = DEFAULT_VL;
		}
		lData = Long.parseLong(strOldData);
		strOldData = Long.toString(lData);

		return strOldData;
	}

	/**
	 * 指定した文字列(符号無し整数)のフォーマットを行う
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.formatSingleNumber("0123") -&gt; "123"<br>
	 * <br>
	 * 指定した文字列(符号無し整数)の先頭文字が[0]の場合は、[0]を取る
	 * @param strOldData フォーマット元の文字列(符号無し整数)
	 * @return	String フォーマット結果
	 */	
	public static String formatSingleNumber(String strOldData) {
		long lData;   //編集用ワーク
		lData = Long.parseLong(strOldData);
		strOldData = Long.toString(lData);

		return strOldData;
	}

	/**
	 * ExceptionStackTraceを取得
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getExceptionStackTrace(e) -&gt; ExceptionStackTrace<br>
	 * <br>
	 * @param strOldData エラーオブジェクト
	 * @return	String ExceptionStackTrace
	 */	
	public static String getExceptionStackTrace( Throwable e ) {
		
		String strException = "";     //戻り値用
		try {
		  // StackTraceを書き込むWriterを作成
		  StringWriter buf = new StringWriter();
		  PrintWriter pw = new PrintWriter(buf);

		  // PrintWriterにStackTraceを書き込む
		  e.printStackTrace(pw);

		  // WriterからStackTraceを取得
		  strException = buf.getBuffer().toString();

		  // 後処理
		  pw.flush();
		  pw.close();
		  pw = null;
		  buf.flush();
		  buf.close();
		  buf = null;
		}catch (java.io.IOException ie){}
		
		return strException;
	}
	
	/**
	 * 渡された文字列が半角文字のみか？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingle("０0") -&gt; true:半角のみ false:全角文字や変換できない文字が含まれる<br>
	 * <br>
	 * 全角文字や変換できない文字があれば false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingle( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingle(base);
		return bResult;
	}

	/**
	 * 渡された文字列が数字か？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleNumber("0.123") -&gt; true:数字のみ false:半角で数字と少数点以外の文字が含まれる<br>
	 * <br>
	 * 半角で数字と少数点以外は false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingleNumber( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleNumber(base);
		return bResult;
	}

	/**
	 * 渡された文字列が実数？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleDouble("0.123") -&gt; true:実数 false:実数以外<br>
	 * <br>
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingleDouble( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleDouble(base);
		if( true == bResult ) {
			if ( ( true == base.equals("+") ) ||
				 ( true == base.equals("-")) ) { 	
				return false;
			}
			return true;
		}
		return bResult;
	}

	/**
	 * 渡された文字が半角整数？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleLong("0.123") -&gt; true:半角整数 false:半角整数以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleLong( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleLong(base);
		if( true == bResult ) {
			if ( ( true == base.equals("+") ) ||
				 ( true == base.equals("-")) ) { 	
				return false;
			}
			return true;
		}
		return bResult;
	}
	/**
	 * 渡された文字が半角英大字のみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleUpper("AaBb") -&gt; true:半角英大字 false:半角英大字以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleUpper( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleUpper(base);
		return bResult;
	}

	/**
	 * 渡された文字が半角英小字のみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleLower("AaBb") -&gt; true:半角英小字 false:半角英小字以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleLower( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleLower(base);
		return bResult;
	}

	/**
	 * 渡された文字が半角カタカナのみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isSingleKana("AaBb") -&gt; true:半角カタカナ false:半角カタカナ以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleKana( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isSingleKana(base);
		return bResult;
	}
	/**
	 * 渡された文字列が全角のみか？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMulti("AaBb") -&gt; true:全角 false:全角以外<br>
	 * <br>
	 * 半角文字が存在すると false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isMulti( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMulti(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角数字のみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiNumber("０0") -&gt; true:全角数字 false:全角数字以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiNumber( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiNumber(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角実数？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiDouble("１２３") -&gt; true:全角実数 false:全角実数以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiDouble( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiDouble(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角整数？
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiLong("１２３") -&gt; true:全角整数 false:全角整数以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiLong( String base )	{
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiLong(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角英大字のみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiUpper("Ａａ") -&gt; true:全角英大字 false:全角英大字以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiUpper( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiUpper(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角英小字のみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiLower("Ａａ") -&gt; true:全角英小字 false:全角英小字以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiLower( String base ) {
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiLower(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角ひらかなのみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiHira("あいう") -&gt; true:全角ひらかな false:全角ひらかな以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiHira( String base )	{
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiHira(base);
		return bResult;
	}

	/**
	 * 渡された文字が全角カタカナのみかを調べる。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.isMultiKana("アイウ") -&gt; true:全角カタカナ false:全角カタカナ以外<br>
	 * <br>
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiKana( String base )	{
		boolean bResult = false;  //戻り値用
		bResult = jp.co.vinculumjapan.stc.util.text.StringCheck.isMultiKana(base);
		return bResult;
	}

	/**
	 * 半角のものを全角に変換する。変換できないものはそのままにする。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.toMulti("ｱｲｳ") -&gt; "アイウ"<br>
	 * <br>
	 * @param base
	 * @return 変換後の文字
	 */
	public static String toMulti( String base ) {
		return jp.co.vinculumjapan.stc.util.text.StringCheck.toMulti(base);
	}

	/**
	 * 全角のものを半角に変換する。変換できないものはそのままにする。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.toSingle("アイウ") -&gt; "ｱｲｳ"<br>
	 * <br>
	 * @param base
	 * @return 変換後の文字
	 */
	public static String toSingle( String base ) {
		return jp.co.vinculumjapan.stc.util.text.StringCheck.toSingle(base);
	}
	/**
	 * 日付かチェックする
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.DateChk("20050217") -&gt; true:日付 false:日付でない<br>
	 * <br>
	 * @param myDate
	 * @return 日付でない場合、8桁でない場合はfalseを返す。
	 */
	public static boolean DateChk(String myDate) {

		   //入力文字列が８桁以外の場合エラー
		   
		   if (myDate.length() != 8) {
			   return false;
		   }

		   for (int i=0;i < myDate.length();i++) {
				char charData = myDate.charAt(i);
				if ((charData < '0') || (charData > '9')) {
				   return false;
			   }
		   }
		   int intYear;   //年
		   int intMonth;  //月
		   int intDay;    //日

		   if (myDate.length() > 3) { 
			   intYear = java.lang.Integer.parseInt(myDate.substring(0,4));
		   } else { 
			   intYear = 0;
		   }
		   if (myDate.length() > 5) { 
			   intMonth = java.lang.Integer.parseInt(myDate.substring(4,6));
		   }
		   else { 
			   intMonth = 0;
		   }
		   if (myDate.length() == 8) { 
			   intDay = java.lang.Integer.parseInt(myDate.substring(6,8));
		   }
		   else { 
			   intDay = 0;
		   }
		          
		   Calendar cal = new GregorianCalendar();    //カレンダーオブジェクト
		   cal.setLenient( false );
		   cal.set( intYear , intMonth-1 , intDay );
		       
		   try {
			   java.util.Date ud = cal.getTime();
		   } catch (IllegalArgumentException iae) {
			   return false;
		   }
		   return true;
	}

	/**
	* システム日付の文字列の取得関数（スラッシュ無し）
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSystemDateNotSlash() -&gt; "20050217"<br>
	 * <br>
	*@return 日付の文字列を返す
	* 2004/12/31ではなく20041231
	*/
	public static String getSystemDateNotSlash() {
		Date 		sysDate = null;    //システム日付(Date)
		String 		strDate = null;    //システム日付(String)
		DateFormat	dateFormat = null; //日付編集用

		Calendar cal = Calendar.getInstance( Locale.JAPAN );
		// システム日付を取得する
		sysDate = cal.getTime();
		// 年月日形式の場合
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		// フォーマット変換して文字列を作成
		strDate = dateFormat.format( sysDate );
		// 文字列を返す
		return strDate;
	}

	/**
	* NULLを空文字列に変換する関数（Base）
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.chkNullString(null) -&gt; ""<br>
	 * <br>
	*@return NULLの場合空文字列
	*        NULL以外の場合Baseをそのまま戻す
	*/
	public static String chkNullString(String value){
	  if(value == null)
		return "";
	  else
		return value;
	}

	/**
	 * <や>や&をHTML上に出力したい時に変換を行う。
	 * ＨＴＭＬに文字として出力を目的として使用します。
	 * convertは空白を&nbspとして出力します。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.convert("&") -&gt; "&amp;"<br>
	 * <br>
	 * @param base
	 * @return
	 */
	public static String convert( String base )
	{
		if( base == null )
			base = "";
		String work = toMS932(base);
		work = replaceAll( work, "&", "&amp;" );
		work = replaceAll( work, ">", "&gt;" );
		work = replaceAll( work, "<", "&lt;" );
		work = replaceAll( work, "\"", "&quot;" );
		work = replaceAll( work, " ", "&nbsp;" );

		return work;
	}

	/**
	 * <や>や&をHTML上に出力したい時に変換を行う。
	 * ＨＴＭＬに文字として出力を目的として使用します。
	 * convertForInputは空白の変換は行いません。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.convertForInput("&") -&gt; "&amp;"<br>
	 * <br>
	 * @param base
	 * @return
	 */
	public static String convertForInput( String base )
	{
		if( base == null )
			base = "";
		String work = toMS932(base);
		work = replaceAll( work, "&", "&amp;" );
		work = replaceAll( work, ">", "&gt;" );
		work = replaceAll( work, "<", "&lt;" );
		work = replaceAll( work, "\"", "&quot;" );

		return work;
	}

	/**
	 * <や>や&をHTML上に出力したい時に変換を行う。
	 * ＨＴＭＬのTEXTAREA内に文字として出力を目的として使用します。
	 * convertForTextAreaは空白を&#32(0x20)として出力します。
	 * &nbspで出力すると0xA0としてjavaでは受け取りユニコード変換で失敗し
	 * 文字化けを起こすため&nbspはTEXTAREA内には出力できないので
	 * TEXTAREA内にＨＴＭＬタグを出力する時はこちらを使用してください。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.convertForTextArea("&") -&gt; "&amp;"<br>
	 * <br>
	 * @param base
	 * @return
	 */
	public static String convertForTextArea( String base )
	{
		if( base == null )
			base = "";
		String work = toMS932(base);
		work = replaceAll( work, "&", "&amp;" );
		work = replaceAll( work, ">", "&gt;" );
		work = replaceAll( work, "<", "&lt;" );
		work = replaceAll( work, "\"", "&quot;" );
		work = replaceAll( work, " ", "&#32;" );

		return work;
	}
	/**
	 * 置き換え文字をＨＴＭＬのタグなどに変換する
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.reverseToHTML("\n") -&gt; "<BR>"<br>
	 * <br>
	 * @param base
	 * @return
	 */
	public static String reverseToHTML( String base )
	{
		if( base == null )
			base = "";
		String work = new String(base);
		work = replaceAll( work, "\n", "<BR>" );
		work = replaceAll( work, "&gt;", ">" );
		work = replaceAll( work, "&lt;", "<" );
		work = replaceAll( work, "&quot;", "\"" );
		work = replaceAll( work, "&#32;", " " );
		work = replaceAll( work, "&amp;", "&" );

		return work;
	}
	/**
	 * 文字列の置換関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.replaceAll("abcdefg","c","1") -&gt; "ab1defg"<br>
	 * <br>
	 * @param base
	 * @param before
	 * @param after
	 * @return
	 */
	public static String replaceAll( String base, String before, String after )
	{
		String retStr = "";   //戻り値用

		if( base == null )
			return "";
		if( before == null || after == null )
			return base;

		for( int i = 0; i < base.length(); i++ )
		{
			if( base.startsWith( before, i ) )
			{
				retStr += after;
				i += before.length() - 1;
			}
			else
				retStr += base.substring(i,i+1);
		}
		return retStr;
	}

	/**
	 * 区切り文字付き文字列を分解し、配列に設定する関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.separate("abc,defg",",") -&gt; 配列[0]"abc" 配列[1]"defg"<br>
	 * <br>
	 * @param     value    区切り文字付き文字
	 * @param     sep      区切り文字
	 * @throws	Exception;
	 * @return    String   分割後配列
	*/
	public static String[] separate(String value, String sep)
	{

		//パラメータの展開
		StringTokenizer tparam = new StringTokenizer(value,sep);  //文字列分解用オブジェクト
		String[] param         = new String[tparam.countTokens()];//分解文字列格納用String配列
		int i = 0;    //ループ回数
		//区切り文字列数分ループ
		while (tparam.hasMoreTokens()) {
			//要素の取り出し
			param[i] = tparam.nextToken();
			i++;
		}
		return param;
	}

	/**
	 * エラーメッセージ配列を取得する関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMsg() -&gt; メッセージ配列<br>
	 * <br>
	 * @return    メッセージMap
	*/
	public static Map getMsg()
	{
		TreeMap map = new TreeMap(infoStrings.getInfoStringMap());    //infoStringsの配列
		if (errMap == null) {
			errMap = new HashMap();
			Set set = map.entrySet();   //MapのEntryを取得
			Iterator i = set.iterator();//MapのIteratorを取得
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				errMap.put(entry.getKey(), infoStrings.getInfo((String)entry.getKey()));
			}
		}
		return errMap;
	}

	/**
	 * 禁則文字エラーメッセージを取得する関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMsgNaiyo() -&gt; メッセージ<br>
	 * <br>
	 * @return    メッセージ
	*/
	public static String getMsgNaiyo()
	{

		return infoStrings.getInfo(mst000101_ConstDictionary.ERR_MSG_CODE);    //infoStringsの配列
	}

	/**
	 * 各種テーブルの更新情報取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKoushinInfoBean("R_XXXXXXXX",map) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String table
	 * @param map    where条件
	 * @return		mst000601_KoushinInfoBean
	 */
	public static mst000601_KoushinInfoBean getKoushinInfoBean(String tbl, List whereList) throws SQLException {
		mst000601_KoushinInfoBean be = new mst000601_KoushinInfoBean();
		be.setInsertTs("");
		be.setInsertUserName("");
		be.setUpdateTs("");
		be.setUpdateUserName("");

		//引数の処理対象テーブルをmst000601_KoushinInfoBeanにセット
		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		
		//各種テーブルの更新情報の取得
		mst000601_KoushinInfoDM kinf = new mst000601_KoushinInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectSql(tbl, whereList));    //抽出結果(ResultSet)
			if (rset.next()) {
				be.setInsertTs(chkNullString(rset.getString("insert_ts")));
				be.setInsertUserName(HTMLStringUtil.convert(chkNullString(rset.getString("insert_user_name")).trim()));
				be.setUpdateTs(chkNullString(rset.getString("update_ts")));
				be.setUpdateUserName(HTMLStringUtil.convert(chkNullString(rset.getString("update_user_name")).trim()));
			}
		} catch(SQLException sqle) {					
			return be;
		
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return be;

	}

	/**
	 * 各種テーブルの更新情報取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKoushinInfoBean("R_XXXXXXXX",map) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String table
	 * @param map    where条件
	 * @return		mst000601_KoushinInfoBean
	 */
	public static mst000601_KoushinInfoBean getKoushinInfoBean(mst000101_DbmsBean db, String tbl, List whereList) throws SQLException {
		mst000601_KoushinInfoBean be = new mst000601_KoushinInfoBean();
		be.setInsertTs("");
		be.setInsertUserName("");
		be.setUpdateTs("");
		be.setUpdateUserName("");

		//引数の処理対象テーブルをmst000601_KoushinInfoBeanにセット
		
		//各種テーブルの更新情報の取得
		mst000601_KoushinInfoDM kinf = new mst000601_KoushinInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectSql(tbl, whereList));    //抽出結果(ResultSet)
			if (rset.next()) {
				be.setInsertTs(chkNullString(rset.getString("insert_ts")));
				be.setInsertUserName(chkNullString(rset.getString("insert_user_name")));
				be.setInsertUserName(HTMLStringUtil.convert(chkNullString(rset.getString("insert_user_name")).trim()));
				be.setUpdateTs(chkNullString(rset.getString("update_ts")));
				be.setUpdateUserName(HTMLStringUtil.convert(chkNullString(rset.getString("update_user_name")).trim()));
			}
		} catch(SQLException sqle) {					
			return be;
		} finally {
		}
		return be;
	}

	/**
	 * 各種マスタ名称取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName("R_XXXXXXXX","XXXXX_NA",map,0) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @param String 有効日
	 * @return		mst000701_MasterInfoBean
	 */
	public static mst000701_MasterInfoBean getMasterCdName(mst000101_DbmsBean db,String tbl, String column, List whereList, String dtchk, String yukodt)
		throws SQLException,Exception
	{
		
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//引数の処理対象テーブルをmst000701_MasterInfoBeanにセット
		
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectSql(tbl, column, whereList,dtchk, yukodt));    //抽出結果(ResultSet)
			if (rset.next() == true) {
				be.setCdName(HTMLStringUtil.convert(chkNullString(rset.getString("cd_name")).trim()));
				be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return be;
	}

	/**
	 * 各種マスタ名称取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName2("R_XXXXXXXX","XXXXX_NA",map,0) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @return		mst000701_MasterInfoBean
	 */
	public static mst000701_MasterInfoBean getMasterCdName2(mst000101_DbmsBean db,String tbl, String column, List whereList, String dtchk,String yukodt)
		throws SQLException,Exception
	{
		
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectSql(tbl, column, whereList,dtchk, yukodt));    //抽出結果(ResultSet)
			while (rset.next()) {
				CdName = CdName + chkNullString(rset.getString("cd_name")).trim();
			}
			be.setCdName(CdName);
			if (!CdName.equals("")){
				be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		}
		return be;
	}
	
	/**
	 * 各種マスタ名称取得関数(JSP用)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName("R_XXXXXXXX","XXXXX_NA",map,0) -&gt; mst000701_MasterInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @return		mst000701_MasterInfoBean
	 */
	public static mst000701_MasterInfoBean getMasterCdNameJsp(String tbl, String column, List whereList, String dtchk, String yukodt)
		throws SQLException,Exception
	{
		
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//引数の処理対象テーブルをmst000701_MasterInfoBeanにセット
		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectSql(tbl, column, whereList,dtchk, yukodt));    //抽出結果(ResultSet)
			if (rset.next()) {
				be.setCdName(HTMLStringUtil.convert(chkNullString(rset.getString("cd_name")).trim()));
				be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return be;
	}
	
	/**
	 * 各種マスタ存在チェック関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName("R_XXXXXXXX","XXXXX_NA",map) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @return		boolean
	 */
	public static boolean getMasterChk(mst000101_DbmsBean db,String tbl, List whereList ) throws SQLException {
		
		boolean ret = false;		

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectMstSql(tbl, whereList ));    //抽出結果(ResultSet)
			if (rset.next()) {
				ret = true;
			} 
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
		return ret;
	}
	
// [NO.18] 2006.03.23 K.Satobo ADD ↓ 削除時の下位コード存在チェック対応
	/**
	 * 各種マスタ存在チェック関数（商品階層マスタで使用）
	 * <br>
	 * @param db				マスタメンテナンス用DB接続クラス
	 * @param tbl				テーブル名
	 * @param whereList    	WHERE条件リスト
	 * @return boolean		該当データ有りの場合は true を返す
	 * @throws SQLException
	 */
	public static boolean getMasterContDelChk(mst000101_DbmsBean db,String tbl, List whereList) throws SQLException {
		
		boolean ret = false;		

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectMstContDelSql(tbl, whereList));    //抽出結果(ResultSet)
			if (rset.next()) {
				ret = true;
			} 
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
		return ret;
	}
// [NO.18] 2006.03.23 K.Satobo ADD ↑ 

	/**
	 * 各種マスタ存在チェック関数(JSP用)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName("R_XXXXXXXX","XXXXX_NA",map) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @return		boolean
	 */
	public static boolean getMasterChkJsp(String tbl, List whereList ) throws SQLException {
		
		boolean ret = false;		

		//引数の処理対象テーブルをmst000701_MasterInfoBeanにセット
		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectMstSql(tbl, whereList ));    //抽出結果(ResultSet)
			if (rset.next()) {
				ret = true;
			} 
		} catch(SQLException sqle) {					
			throw sqle;
		
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return ret;
	}
	/**
	 * 商品体系マスタ取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterCdName("R_XXXXXXXX","XXXXX_NA",map,0) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String tbl
	 * @param String column
	 * @param map    where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @return		mst000701_MasterInfoBean
	 */
	public static mst000701_MasterInfoBean getSyohinTaikei(mst000101_DbmsBean db, String column, List whereList )
		throws SQLException
	{
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//引数の処理対象テーブルをmst000701_MasterInfoBeanにセット
		
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSyohinTaikeiSelectSql(column, whereList));    //抽出結果(ResultSet)
			if (rset.next()) {
				be.setCdName(HTMLStringUtil.convert(chkNullString(rset.getString("cd_name")).trim()));
				be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_TRUE);
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		}
		
		return be;
	}

	/**
	 * システム共通メニューバー ファイル名称取得
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getCommonMenubar() -&gt; String[]<br>
	 * <br>
	 * @return		String[]	menu
	 */
	public static String[] getCommonMenubar(mst000101_DbmsBean db,Map map) throws SQLException {
		
		int i = 0;
		int j = 0;
		int maxrow = 9;
		String[] menu = null;
		String[] str  = new String[maxrow];
		
		mst000901_MenuBarDM sysdm = new mst000901_MenuBarDM();

		try {
			ResultSet rset = db.executeQuery(sysdm.getSelectSql(map));    //抽出結果(ResultSet)
			while (rset.next()) {
				str[i] = rset.getString("gamen_id").trim();
				i = i + 1;
				if(i >= maxrow){
					break;
				}
			}
			menu = new String[i];
			for(j=0; j<maxrow; j++){
				if(j >= i){
					break;
				}
				menu[j] = str[j];
			}
			return menu;
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
	}

	/**
	 * システム共通メニューバー ファイル名称取得
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getCommonMenubar() -&gt; String[]<br>
	 * <br>
	 * @return		String[]	menu
	 */
	public static String[] getCommonMenubarJsp(Map map) throws SQLException {
		
		int i = 0;
		int j = 0;
		int maxrow = 9;
		String[] menu = null;
		String[] str  = new String[maxrow];
		
		mst000901_MenuBarDM sysdm = new mst000901_MenuBarDM();
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用

		try {
			ResultSet rset = db.executeQuery(sysdm.getSelectSql(map));    //抽出結果(ResultSet)
			while (rset.next()) {
				str[i] = rset.getString("gamen_id").trim();
				i = i + 1;
				if(i >= maxrow){
					break;
				}
			}
			menu = new String[i];
			for(j=0; j<maxrow; j++){
				if(j >= i){
					break;
				}
				menu[j] = str[j];
			}
			return menu;
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}			
	}
	
	/**
	 * JSPコントロール前景色初期値設定処理
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getColorInit(String[]) -&gt; Map<br>
	 * <br>
	 * @param      String[] コントロール名配列
	 * @return		Map	     コントロール前景色初期値配列
	 */
	public static Map getColorInit(String[] ctl) {
		
		Map map = new HashMap();//コントロール前景色初期値配列
		
		for (int i=0; i < ctl.length; i++) {
			map.put(ctl[i], "#000000");
		}
		return map;
	}
	
	/**
	 * JSPコントロールエラー時前景色設定処理
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getColorInit(String[]) -&gt; Map<br>
	 * <br>
	 * @param		Map	     コントロール前景色配列
	 * @param      String   コントロール名
	 */
	public static void setErrorColor(Map map, String ctl) {
			map.put(ctl, mst000101_ConstDictionary.CONTROL_ERROR_COLOR);
	}
	
	/**
	 * JSPコントロール背景色初期値設定処理
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getColorInit(String[]) -&gt; Map<br>
	 * <br>
	 * @param      String[] コントロール名配列
	 * @return		Map	     コントロール前景色初期値配列
	 */
	public static Map getBackColorInit(String[] ctl) {
		
		Map map = new HashMap();//コントロール前景色初期値配列
		
		for (int i=0; i < ctl.length; i++) {
			map.put(ctl[i], "#FFFFFF");
		}
		return map;
	}
	
	/**
	 * JSPコントロールエラー時背景色設定処理
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getColorInit(String[]) -&gt; Map<br>
	 * <br>
	 * @param		Map	     コントロール前景色配列
	 * @param      String   コントロール名
	 */
	public static void setErrorBackColor(Map map, String ctl) {
			map.put(ctl, mst000101_ConstDictionary.CONTROL_ERROR_BACKCOLOR);
	}

	/**
	 * 画面IDに対するログイン者の権限を処理ごとに取得する
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKengen(cd, id) -&gt; boolean<br>
	 * <br>
	 * @param		String	 cd:ログイン者の権限コード
	 * @param      String   id:画面ID
	 * @return		map 
	 */
	public static Map getKengen(mst000101_DbmsBean db,String cd, String id) throws SQLException {
		
		mst000801_SysKengenRiyouDM sysdm = new mst000801_SysKengenRiyouDM();
		Map map = new HashMap();//DM抽出条件
		map.put("kengen_cd", cd);
		map.put("gamen_id" , id);
		Map ret = new HashMap();

		try {
			ResultSet rset = db.executeQuery(sysdm.getSelectSql(map));    //抽出結果(ResultSet)

			if(rset.next()) {
				ret.put("insert_ok_kb", rset.getString("insert_ok_kb"));
				ret.put("update_ok_kb", rset.getString("update_ok_kb"));
				ret.put("delete_ok_kb",	rset.getString("delete_ok_kb"));
				ret.put("reference_ok_kb",	rset.getString("reference_ok_kb"));
				ret.put("csv_ok_kb",	rset.getString("csv_ok_kb"));
				ret.put("print_ok_kb",	rset.getString("print_ok_kb"));
			} else {
				// 対象レコードなし。処理不可能
				ret.put("insert_ok_kb", mst004801_NewSyoriRiyoukbDictionary.FUKANOU.getCode());
				ret.put("update_ok_kb", mst004901_UpdateSyoriRiyoukbDictionary.FUKANOU.getCode());
				ret.put("delete_ok_kb",	mst005001_DelRiyoukbDictionary.FUKANOU.getCode());
				ret.put("reference_ok_kb",	mst005101_ViewRiyoukbDictionary.FUKANOU.getCode());
				ret.put("csv_ok_kb",	mst005201_CsvRiyoukbDictionary.FUKANOU.getCode());
				ret.put("print_ok_kb",	mst005301_PrnRiyoukbDictionary.FUKANOU.getCode());
			}
			return ret;
		} catch(SQLException sqle) {					
			// 対象レコードなし。処理不可能
			throw sqle;
		} finally {
		}
	}
	
	/**
	 * 画面IDに対するログイン者の権限を処理ごとに取得する(JSP用)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKengen(cd, id) -&gt; boolean<br>
	 * <br>
	 * @param		String	 cd:ログイン者の権限コード
	 * @param      String   id:画面ID
	 * @return		map 
	 */
	public static Map getKengenJsp(String cd, String id) throws SQLException {
		
		mst000801_SysKengenRiyouDM sysdm = new mst000801_SysKengenRiyouDM();
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		Map map = new HashMap();//DM抽出条件
		map.put("kengen_cd", cd);
		map.put("gamen_id" , id);
		Map ret = new HashMap();

		try {
			ResultSet rset = db.executeQuery(sysdm.getSelectSql(map));    //抽出結果(ResultSet)

			if(rset.next()) {
				ret.put("insert_ok_kb", rset.getString("insert_ok_kb"));
				ret.put("update_ok_kb", rset.getString("update_ok_kb"));
				ret.put("delete_ok_kb",	rset.getString("delete_ok_kb"));
				ret.put("reference_ok_kb",	rset.getString("reference_ok_kb"));
				ret.put("csv_ok_kb",	rset.getString("csv_ok_kb"));
				ret.put("print_ok_kb",	rset.getString("print_ok_kb"));
			} else {
				// 対象レコードなし。処理不可能
				ret.put("insert_ok_kb", mst004801_NewSyoriRiyoukbDictionary.FUKANOU.getCode());
				ret.put("update_ok_kb", mst004901_UpdateSyoriRiyoukbDictionary.FUKANOU.getCode());
				ret.put("delete_ok_kb",	mst005001_DelRiyoukbDictionary.FUKANOU.getCode());
				ret.put("reference_ok_kb",	mst005101_ViewRiyoukbDictionary.FUKANOU.getCode());
				ret.put("csv_ok_kb",	mst005201_CsvRiyoukbDictionary.FUKANOU.getCode());
				ret.put("print_ok_kb",	mst005301_PrnRiyoukbDictionary.FUKANOU.getCode());
			}
			return ret;
		} catch(SQLException sqle) {					
			// 対象レコードなし。処理不可能
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
	}


	/**
	 * ユーザ名取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKoushinInfoBean("R_XXXXXXXX",map) -&gt; mst000601_KoushinInfoBean<br>
	 * <br>
	 * @param String table
	 * @param map    where条件
	 * @return		mst000601_KoushinInfoBean
	 */
	public static String getUSerName(String UserId) throws SQLException {
		mst000601_KoushinInfoBean be = new mst000601_KoushinInfoBean();
		
		//引数の処理対象テーブルをmst000601_KoushinInfoBeanにセット		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		
		String userName ="";
		//各種テーブルの更新情報の取得
		mst000601_KoushinInfoDM kinf = new mst000601_KoushinInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery( kinf.getNameSelectSql(UserId) );    //抽出結果(ResultSet)
			if (rset.next()) {
				userName = chkNullString(HTMLStringUtil.convert(rset.getString("user_na").trim()));
			}
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return userName;
	}

	/**
	 * 販区コード妥当性チェック
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getHankuCdCheck(HankuCd) -&gt;<br>
	 * <br>
	 * @param 		String			HankuCd			入力した販区コード
	 * @param 		SessionManager	sessionManager	セッションマネージャー
	 * @return		boolean	true	OK
	 *						false	NG
	 */
//	public static boolean getHankuCdCheck(mst000101_DbmsBean db,String HankuCd,SessionManager sessionManager) throws SQLException {
//
//		// userSession取得
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//		SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//
//		boolean flg   = false;							// 通過フラグ
//		boolean ret   = false;							// 戻り値
//
//		String strGyosyuCd = "";							// 変換後の業種コード
//		String strSGyosyuCd = "";							// 小業種コード
//		String strSelGyosyuCd = "";							// 業種選択画面で選択された業種コード
//		strSelGyosyuCd = SysUserBean.getSelectedGyosyuCd();
//		String strSosasyaSGyosyuCd = "";					// 操作者情報.小業種コード
//		strSosasyaSGyosyuCd = mst000401_LogicBean.chkNullString(SysUserBean.getSGyosyuCd());
//		
//		try {
//
//			StringBuffer buffSql = new StringBuffer();
//			ResultSet rset = null;
//		
//			// 商品体系マスタより販区コードから大業種を求める
//			buffSql.append("SELECT ");
//			buffSql.append("	CASE D_GYOSYU_CD ");
//			buffSql.append("		WHEN '0011' THEN '01' ");
//			buffSql.append("		WHEN '0022' THEN '02' ");
//			buffSql.append("		WHEN '0033' THEN '03' ");
//			buffSql.append("		WHEN '0044' THEN '01' ");
//// 2005.11.24 Y.Inaba 追加
//// 2006.01.18 D.Matsumoto 4桁販区対応開始
//			buffSql.append("        WHEN '0057' THEN '01' ");
//// 2006.01.18 D.Matsumoto 4桁販区対応終了
//// 2005.11.24 Y.Inaba 追加
//			buffSql.append("		WHEN '0061' THEN '01' ");
//			buffSql.append("		ELSE '02'");
//			buffSql.append("	 END AS GYOSYU_CD, ");
//			buffSql.append("	 S_GYOSYU_CD ");
//			buffSql.append("  FROM R_SYOHIN_TAIKEI ");
//			buffSql.append(" WHERE HANKU_CD = '" + HankuCd + "'");
//
//			rset = db.executeQuery(buffSql.toString());
//				
//			if(rset.next()){
//				strGyosyuCd = rset.getString("GYOSYU_CD");
//				strSGyosyuCd = rset.getString("S_GYOSYU_CD");
//
//				// 操作者情報.小業種がセットされている場合
//				if(!strSosasyaSGyosyuCd.equals("")){
//					
//					// 操作者情報.小業種コードで比較
//					if(strSGyosyuCd.equals(strSosasyaSGyosyuCd)){
//						ret = true;
//					}else{
//						ret = false;
//					}			
//				}else{
//					// 加工食品か生鮮食品を決定
////					↓↓仕様変更（2005.09.27）↓↓
////					if(strGyosyuCd.equals("03") && (!strSGyosyuCd.equals("0087"))){
//					if(strGyosyuCd.equals("03") && (!(strSGyosyuCd.equals("0087") || strSGyosyuCd.equals("0088")))){
////					↑↑仕様変更（2005.09.27）↑↑
//						strGyosyuCd = "04";
//					}
//
//					// 選択業種コードと比較
//					if(strGyosyuCd.equals(strSelGyosyuCd)){
//						ret = true;
//					}else{
//						ret = false;
//					}			
//				}
//
//			}else{
//				ret = false;
//			}
//			if(rset != null){
//				rset.close();
//			}
//		} catch(SQLException sqle) {					
//			throw sqle;
//		} finally {
//		}
//		return ret;
//	}

	/**
	 * 販区コード妥当性チェック(JSP用)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getHankuCdCheck(HankuCd) -&gt;<br>
	 * <br>
	 * @param 		String			HankuCd			入力した販区コード
	 * @param 		SessionManager	sessionManager	セッションマネージャー
	 * @return		boolean	true	OK
	 *						false	NG
	 */
//	public static boolean getHankuCdCheckJsp(String HankuCd,SessionManager sessionManager) throws SQLException {
//
//		//userSession取得
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//		SysUserBean=(mst000501_SysSosasyaBean)sessionManager.getAttribute("userSession");
//
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
//
//		//各種テーブルの更新情報の取得
//		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
//		
//		String tableNa = "R_SYOHIN_TAIKEI";		//テーブル名称
//		List whereList = new ArrayList();		//抽出条件格納
//		boolean flg   = false;				//通過フラグ
//		boolean ret   = false;				//戻り値
//		
//		try {
//			//ログインユーザー.売区1～3取得
//			if(mst000401_LogicBean.chkNullString(SysUserBean.getUriku1Cd()).trim().equals("")
//			&& mst000401_LogicBean.chkNullString(SysUserBean.getUriku2Cd()).trim().equals("")
//			&& mst000401_LogicBean.chkNullString(SysUserBean.getUriku3Cd()).trim().equals("")){
//				return true;
//			}
//			
//			//WHERE句作成
//			whereList.add("     HANKU_CD = '" + HankuCd + "' ");
//			if(!mst000401_LogicBean.chkNullString(SysUserBean.getUriku1Cd()).trim().equals("")){
//				if(flg){
//					whereList.add(" OR URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku1Cd()).trim() + "' ");
//				} else {
//					whereList.add(" AND ( URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku1Cd()).trim() + "' ");
//				}
//				flg = true;
//			}
//			if(!mst000401_LogicBean.chkNullString(SysUserBean.getUriku2Cd()).trim().equals("")){
//				if(flg){
//					whereList.add(" OR URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku2Cd()).trim() + "' ");
//				} else {
//					whereList.add(" AND ( URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku2Cd()).trim() + "' ");
//				}
//				flg = true;
//			}
//			if(!mst000401_LogicBean.chkNullString(SysUserBean.getUriku3Cd()).trim().equals("")){
//				if(flg){
//					whereList.add(" OR URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku3Cd()).trim() + "' ");
//				} else {
//					whereList.add(" AND ( URIKU_CD = '" + mst000401_LogicBean.chkNullString(SysUserBean.getUriku3Cd()).trim() + "' ");
//				}
//				flg = true;
//			}
//			whereList.add(" ) ");
//
//			//DB検索
//			ResultSet rset = db.executeQuery( mstDM.getCountSql(tableNa,whereList) );    //抽出結果(ResultSet)
//			if (rset.next()) {
//				if( rset.getInt("cnt") > 0 ){
//					ret = true;
//				} else {
//					ret = false;
//				}
//			} else {
//				ret = false;
//			}
//		} catch(SQLException sqle) {					
//			throw sqle;
//		} finally {
//			if( null != db ) {
//				db.close();
//				db = null;
//			}
//		}
//		return ret;
//	}

	/**
	 * 品種コード妥当性チェック
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getHinshuCdCheck(HinshuCd) -&gt;<br>
	 * <br>
	 * @param 		String			HinshuCd		入力した品種コード
	 * @param 		SessionManager	sessionManager	セッションマネージャー
	 * @return		boolean	true	OK
	 *						false	NG
	 */
	public static boolean getHinshuCdCheck(mst000101_DbmsBean db,String HinshuCd,SessionManager sessionManager) throws SQLException {

		// SysSosasyaSesson取得
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//		SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
		MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
		SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");

		boolean flg   = false;							// 通過フラグ
		boolean ret   = false;							// 戻り値

		String strGyosyuCd = "";							// 変換後の業種コード
		String strSGyosyuCd = "";							// 小業種コード
		String strSelGyosyuCd = "";							// 業種選択画面で選択された業種コード
//		strSelGyosyuCd = SysUserBean.getSelectedGyosyuCd();
		String strSosasyaSGyosyuCd = "";					// 操作者情報.小業種コード
//		strSosasyaSGyosyuCd = mst000401_LogicBean.chkNullString(SysUserBean.getSGyosyuCd());
		strSosasyaSGyosyuCd = "";

		try {
			StringBuffer buffSql = new StringBuffer();
			ResultSet rset = null;
		
			// 商品体系マスタより品種コードから大業種を求める
			buffSql.append("SELECT ");
			buffSql.append("	CASE D_GYOSYU_CD ");
			buffSql.append("		WHEN '0011' THEN '01' ");
			buffSql.append("		WHEN '0022' THEN '02' ");
			buffSql.append("		WHEN '0033' THEN '03' ");
			buffSql.append("		WHEN '0044' THEN '01' ");
// 2005.11.24 Y.Inaba 追加
// 2006.01.18 D.Matsumoto 4桁販区対応開始
			buffSql.append("        WHEN '0057' THEN '01' ");
// 2006.01.18 D.Matsumoto 4桁販区対応終了
// 2005.11.24 Y.Inaba 追加
			buffSql.append("		WHEN '0061' THEN '01' ");
			buffSql.append("		ELSE '02'");
			buffSql.append("	 END AS GYOSYU_CD, ");
			buffSql.append("	 S_GYOSYU_CD ");
			buffSql.append("  FROM R_SYOHIN_TAIKEI ");
			buffSql.append(" WHERE HINSYU_CD = '" + HinshuCd + "'");
				
			rset = db.executeQuery(buffSql.toString());
				
			if(rset.next()){
				strGyosyuCd = rset.getString("GYOSYU_CD");
				strSGyosyuCd = rset.getString("S_GYOSYU_CD");

				// 操作者情報.小業種がセットされている場合
				if(!strSosasyaSGyosyuCd.equals("")){
					// 操作者情報.小業種コードで比較
					if(strSGyosyuCd.equals(strSosasyaSGyosyuCd)){
						ret = true;
					}else{
						ret = false;
					}
				}else{
					// 加工食品か生鮮食品を決定
//					↓↓仕様変更（2005.09.27）↓↓
//					if(strGyosyuCd.equals("03") && (!strSGyosyuCd.equals("0087"))){
					if(strGyosyuCd.equals("03") && (!(strSGyosyuCd.equals("0087") || strSGyosyuCd.equals("0088")))){
//					↑↑仕様変更（2005.09.27）↑↑
						strGyosyuCd = "04";
					}

					// 選択業種コードと比較
					if(strGyosyuCd.equals(strSelGyosyuCd)){
						ret = true;
					}else{
						ret = false;
					}			
				}
			}else{
				ret = false;
			}
			if(rset != null){
				rset.close();
			}
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
		return ret;
	}

	/**
	 * 小業種コード妥当性チェック
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSGyosyuCdCheck(HankuCd) -&gt;<br>
	 * <br>
	 * @param 		String			SGyosyuCd		入力した小業種コード
	 * @param 		SessionManager	sessionManager	セッションマネージャー
	 * @return		boolean	true	OK
	 *						false	NG
	 */
	public static boolean getSGyosyuCdCheck(mst000101_DbmsBean db, String SGyosyuCd, SessionManager sessionManager) throws SQLException {

		// SysSosasyaSesson取得
//		mst000501_SysSosasyaBean SysUserBean = new mst000501_SysSosasyaBean();	// ログインユーザー情報
//		SysUserBean = (mst000501_SysSosasyaBean)sessionManager.getAttribute("SysSosasyaSesson");
		MdwareUserSessionBean SysUserBean = new MdwareUserSessionBean();	// ログインユーザー情報
		SysUserBean = (MdwareUserSessionBean)sessionManager.getAttribute("userSession");

		boolean flg   = false;							// 通過フラグ
		boolean ret   = false;							// 戻り値

		String strGyosyuCd = "";							// 変換後の業種コード
		String strSGyosyuCd = "";							// 小業種コード
		String strSelGyosyuCd = "";							// 業種選択画面で選択された業種コード
//		strSelGyosyuCd = SysUserBean.getSelectedGyosyuCd();
		String strSosasyaSGyosyuCd = "";					// 操作者情報.小業種コード
//		strSosasyaSGyosyuCd = mst000401_LogicBean.chkNullString(SysUserBean.getSGyosyuCd());
		strSosasyaSGyosyuCd = "";

		try {
			StringBuffer buffSql = new StringBuffer();
			ResultSet rset = null;

			// 商品体系マスタより小業種コードから大業種を求める
			buffSql.append("SELECT ");
			buffSql.append("	CASE D_GYOSYU_CD ");
			buffSql.append("		WHEN '0011' THEN '01' ");
			buffSql.append("		WHEN '0022' THEN '02' ");
			buffSql.append("		WHEN '0033' THEN '03' ");
			buffSql.append("		WHEN '0044' THEN '01' ");
// 2005.11.24 Y.Inaba 追加
// 2006.01.18 D.Matsumoto 4桁販区対応開始
			buffSql.append("        WHEN '0057' THEN '01' ");
// 2006.01.18 D.Matsumoto 4桁販区対応終了
// 2005.11.24 Y.Inaba 追加
			buffSql.append("		WHEN '0061' THEN '01' ");
			buffSql.append("		ELSE '02'");
			buffSql.append("	 END AS GYOSYU_CD, ");
			buffSql.append("	 S_GYOSYU_CD ");
			buffSql.append("  FROM R_SYOHIN_TAIKEI ");
			buffSql.append(" WHERE S_GYOSYU_CD = '" + SGyosyuCd + "'");

			rset = db.executeQuery(buffSql.toString());

			if(rset.next()){
				strGyosyuCd = rset.getString("GYOSYU_CD");
				strSGyosyuCd = rset.getString("S_GYOSYU_CD");

				// 操作者情報.小業種がセットされている場合
				if(!strSosasyaSGyosyuCd.equals("")){
					// 操作者情報.小業種コードで比較
					if(strSGyosyuCd.equals(strSosasyaSGyosyuCd)){
						ret = true;
					}else{
						ret = false;
					}			
				}else{
					// 加工食品か生鮮食品を決定
//					↓↓仕様変更（2005.09.27）↓↓
//					if(strGyosyuCd.equals("03") && (!strSGyosyuCd.equals("0087"))){
					if(strGyosyuCd.equals("03") && (!(strSGyosyuCd.equals("0087") || strSGyosyuCd.equals("0088")))){
//					↑↑仕様変更（2005.09.27）↑↑
						strGyosyuCd = "04";
					}

					// 選択業種コードと比較
					if(strGyosyuCd.equals(strSelGyosyuCd)){
						ret = true;
					}else{
						ret = false;
					}			
				}
			}else{
				ret = false;
			}

			if(rset != null){
				rset.close();
			}
			
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
		return ret;
	}

	/**
	 * 画面遷移用セション引継情報を削除する
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.clearHoldSession(HttpSession, String) -&gt; <br>
	 * <br>
	 * @param HttpSession セションObject
	 * @param String    セションKey名
	 */
	public static void clearHoldSession(HttpSession ses, String key) {
		List holds = (List)ses.getAttribute( mst000101_ConstDictionary.SESSIONHOLDSNAME );

		Iterator ite = holds.iterator();
		while( ite.hasNext() )
			if( key.equals((String)ite.next()) )
				ite.remove();

		ses.setAttribute( mst000101_ConstDictionary.SESSIONHOLDSNAME, holds);
	}

	/**
	 * 画面遷移用セション引継情報を設定する
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.setHoldSession(HttpSession, String) -&gt; <br>
	 * <br>
	 * @param SessionManager
	 * @param List    セションKey名List
	 */
	public static void setHoldSession(SessionManager ses, List key) {
		List backKey = (List)ses.getAttribute("mst000101_BackSesKeyName");
		
		if (backKey == null) {
			backKey = new ArrayList();
		}
		
		backKey.add(key);
		ses.setAttribute("mst000101_BackSesKeyName", backKey);
		
		if (key.size() != 0) {
			List objList1 = (List)ses.getAttribute("mst000101_BackSesKeyObject"); 
			if (objList1 == null) {
				objList1 = new ArrayList();
			}
			List objList2 = new ArrayList(); 
			for (int i=0; i < key.size(); i++) {

				Object obj = (Object)ses.getAttribute( (String)key.get(i) );
				objList2.add(obj);
			}
			objList1.add(objList2);
			ses.setAttribute("mst000101_BackSesKeyObject", objList1);
		}
	}

	/**
	 * 各種マスタチェック＆情報取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterItemValues("R_XXXXXXXX",list,list,0) -&gt; Map<br>
	 * 該当レコード複数件の場合、先頭レコードのみ返却<br>
	 * 該当レコード無しの場合、返却されるMapの件数で判断する<br>
	 * <br>
	 * @param String tbl
	 * @param list   columns
	 * @param list   where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @return		Map
	 */
	public static Map getMasterItemValues(mst000101_DbmsBean db,String tbl, List columns, List whereList, String dtchk)
		throws SQLException,Exception
	{
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//値返却用ハッシュ配列
		HashMap retMap = new HashMap();

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectAnySql(tbl, columns, whereList,dtchk));    //抽出結果(ResultSet)
			if (rset.next()) {
				for (int i =0;i < columns.size();i++) {
					String column = (String)columns.get(i);
					retMap.put(column,HTMLStringUtil.convert(chkNullString(rset.getString(column)).trim()));
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return retMap;
	}
	
	/**
	 * 各種マスタチェック＆情報取得関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMasterItemValuesJsp("R_XXXXXXXX",list,list,0) -&gt; Map<br>
	 * 該当レコード複数件の場合、先頭レコードのみ返却<br>
	 * 該当レコード無しの場合、返却されるMapの件数で判断する<br>
	 * <br>
	 * @param String tbl
	 * @param list   columns
	 * @param list   where条件
	 * @param String 有効日判定(0:使用しない 1:使用する)
	 * @return		Map
	 */
	public static Map getMasterItemValuesJsp(String tbl, List columns, List whereList, String dtchk)
		throws SQLException,Exception
	{
		String CdName = "";		//取得名称格納
		mst000701_MasterInfoBean be = new mst000701_MasterInfoBean();
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		
		be.setCdName("");
		be.setExistenceFlg(mst000101_ConstDictionary.FUNCTION_FALSE);

		//値返却用ハッシュ配列
		HashMap retMap = new HashMap();

		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM kinf = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(kinf.getSelectAnySql(tbl, columns, whereList,dtchk));    //抽出結果(ResultSet)
			if (rset.next()) {
				for (int i =0;i < columns.size();i++) {
					String column = (String)columns.get(i);
					retMap.put(column,HTMLStringUtil.convert(chkNullString(rset.getString(column)).trim()));
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return retMap;
	}
	
	/**
	 * 数字を3桁ごとにカンマ区切りするし、小数部の編集を行う
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.addCommaDecimal("9999999.99") -&gt; "9,999,999.99"<br>
	 * <br>
	 * 引数strがnullの場合の戻り値は、nullを返します。<br>
	 * <br>
	 * @param motoNum 変換対象文字列
	 */
	public static String addCommaDecimal(String motoNum,String param ,String digits){
		if( motoNum == null || motoNum.equals("")){
			return motoNum;
		}
		
		String strWork= "";
		String strDigitsWork = "";
		if(motoNum.indexOf(".") != -1 ){
			strDigitsWork = motoNum.substring(motoNum.indexOf(".")+1,motoNum.length());
//			↓↓2006.08.02 xubq カスタマイズ修正↓↓
			motoNum =  motoNum.substring(0,motoNum.lastIndexOf("."));
		}
//		motoNum =  motoNum.substring(0,motoNum.lastIndexOf("."));
//		↑↑2006.08.02 xubq カスタマイズ修正↑↑
		//カンマ編集あり
		if(param.equals(mst000101_ConstDictionary.FUNCTION_PARAM_0)){
			int intlen = motoNum.length();
			int intCnt = 0;
			for (intCnt=intlen-1; intCnt>= 0; intCnt--)
			{
				strWork = motoNum.charAt(intCnt) + strWork;
				if ((((intlen - intCnt) % 3) == 0) && ((intCnt > 1) || ((intCnt == 1) && (motoNum.charAt(0) != '-')))){
					strWork = "," + strWork;
				}
			}
		
		// カンマ編集なし			
		}else{
			strWork = motoNum;
		}
		
		String strDigit = "";
		for( int i = 0;i<Integer.parseInt(digits);i++){
			if(i<strDigitsWork.length()){
				if(strDigitsWork.substring(i,i+1).equals("")){
					strDigit = strDigit + "0";
				} else {
					strDigit = strDigit + strDigitsWork.substring(i,i+1);
				}
			} else {
				strDigit = strDigit + "0";
			}
		}
		
		strWork = strWork + "." + strDigit;
		return strWork;
	}
	/**
	 * 数字を3桁ごとにカンマ区切りする編集を行う(小数部対応)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.NumericEdit("9999999.99") -&gt; "9,999,999.99"<br>
	 * <br>
	 * 引数strが数値以外である場合の戻り値は、そのまま返す。<br>
	 * <br>
	 * @param motoNum 変換対象文字列
	 */
	public static String NumericEdit(String Str){
		String ret = Str;
		int len 		= Str.length();
		int point  	= Str.indexOf(".");
		if(point == -1 ){
			if(isSingleNumber(removeComma(Str))){
				ret = addComma(removeComma(Str));
			}			
		} else {
			String numberPart  =  Str.substring(0, point);
			String decimalPart =  Str.substring(point + 1, len);
			if(isSingleNumber(removeComma(numberPart)) 
			  && isSingleNumber(decimalPart)){
				ret = addComma(removeComma(numberPart)) + "." + decimalPart;
			  }
		}
		return ret;
	}
	
	/**
	 * 商品コードより商品マスタを検索し、販区コードを得る(JSP用)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSyohinCd2HankuCd("12345678", "A08") -&gt; "000000"<br>
	 * <br>
	 * 戻り値がnull時は１商品コードに対して販区コードが２レコード以上ある。<br>
	 * <br>
	 * @param db		STCLIBのDBインスタンス
	 * @param syohinCd 商品マスタ.商品コード
	 * @param gyosyuKb 商品マスタ.業種区分
	 * @return		String
	 */
	public static String getSyohinCd2HankuCdJsp(String syohinCd,String gyosyuKb)
		throws SQLException
	{
		String strSQL = new String();
		String HankuCd = new String();
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用

		try {
			//販区コードが入力されていない場合は先に販区コードを決定する
			strSQL = strSQL + "SELECT ";
			strSQL = strSQL + "    DISTINCT ";
			strSQL = strSQL + "    HANKU_CD ";
			strSQL = strSQL + "   ,SYOHIN_CD ";
			strSQL = strSQL + "FROM ";
			strSQL = strSQL + "    R_SYOHIN ";
			strSQL = strSQL + "WHERE ";
			strSQL = strSQL + "    SYOHIN_CD = '" + syohinCd.trim() + "' ";
			if(!gyosyuKb.equals("")){
				strSQL = strSQL + "AND GYOSYU_KB = '" + gyosyuKb + "' ";
			}
			ResultSet rset = db.executeQuery(strSQL);    //抽出結果(ResultSet)
			if (rset.next()) {
				HankuCd = rset.getString("hanku_cd").trim();
				if (rset.next()) {
					//2件以上ある場合はエラー
					HankuCd = null;
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return HankuCd;
	}

	/**
	 * 商品コードより商品マスタを検索し、販区コードを得る
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSyohinCd2HankuCd("12345678", "A08") -&gt; "000000"<br>
	 * <br>
	 * 戻り値がnull時は１商品コードに対して販区コードが２レコード以上ある。<br>
	 * <br>
	 * @param db		STCLIBのDBインスタンス
	 * @param syohinCd 商品マスタ.商品コード
	 * @param gyosyuKb 商品マスタ.業種区分
	 * @return		String
	 */
	public static String getSyohinCd2HankuCd(mst000101_DbmsBean db, String syohinCd,String gyosyuKb)
		throws SQLException
	{
		String strSQL = new String();
		String HankuCd = new String();

		try {
			//販区コードが入力されていない場合は先に販区コードを決定する
			strSQL = strSQL + "SELECT ";
			strSQL = strSQL + "    DISTINCT ";
			strSQL = strSQL + "    HANKU_CD ";
			strSQL = strSQL + "   ,SYOHIN_CD ";
			strSQL = strSQL + "FROM ";
			strSQL = strSQL + "    R_SYOHIN ";
			strSQL = strSQL + "WHERE ";
			strSQL = strSQL + "    SYOHIN_CD = '" + syohinCd.trim() + "' ";
			strSQL = strSQL + "AND GYOSYU_KB = '" + gyosyuKb + "' ";
			ResultSet rset = db.executeQuery(strSQL);    //抽出結果(ResultSet)
			if (rset.next()) {
				HankuCd = rset.getString("hanku_cd").trim();
				if (rset.next()) {
					//2件以上ある場合はエラー
					HankuCd = null;
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return HankuCd;
	}
	
	/**
	 * 商品コードより商品マスタをLIKE検索し、販区コードを得る
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getSyohinCd2HankuCd("12345678", "A08") -&gt; "000000"<br>
	 * <br>
	 * 戻り値がnull時は１商品コードに対して販区コードが２レコード以上ある。<br>
	 * <br>
	 * @param db		STCLIBのDBインスタンス
	 * @param syohinCd 商品マスタ.商品コード
	 * @param gyosyuKb 商品マスタ.業種区分
	 * @return		String
	 */
	public static String getLikeSyohinCd2HankuCd(mst000101_DbmsBean db, String syohinCd,String gyosyuKb)
		throws SQLException
	{
		String strSQL = new String();
		String HankuCd = new String();

		try {
			//販区コードが入力されていない場合は先に販区コードを決定する
			strSQL = strSQL + "SELECT ";
			strSQL = strSQL + "    DISTINCT ";
			strSQL = strSQL + "    HANKU_CD ";
			strSQL = strSQL + "   ,SYOHIN_CD ";
			strSQL = strSQL + "FROM ";
			strSQL = strSQL + "    R_SYOHIN ";
			strSQL = strSQL + "WHERE ";
			strSQL = strSQL + "    SYOHIN_CD LIKE '" + syohinCd.trim() + "%' ";
			strSQL = strSQL + "AND GYOSYU_KB = '" + gyosyuKb + "' ";
			ResultSet rset = db.executeQuery(strSQL);    //抽出結果(ResultSet)
			if (rset.next()) {
				HankuCd = rset.getString("hanku_cd").trim();
				if (rset.next()) {
					//2件以上ある場合はエラー
					HankuCd = null;
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return HankuCd;
	}

	/**
	 * 処理区分押下可否、初期値を設定する(新規・修正・削除・照会)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getPermission(KeepBean) -&gt; "000000"<br>
	 * <br>
	 * 権限により処理状況の押下可否及び初期値を設定する<br>
	 * <br>
	 * @param	Bean	KeepBean
	 * @return	Map		map
	 */
	public static Map getPermission(Object Keepb)
	{
		
		Map map = new HashMap();
		String disable	= "disabled";	//使用不可
		String check	= "checked";	//使用可
		String enable	= "";			//チェック
		String uncheck	= "";			//アンチェック
		boolean flg	= true;		//エラーフラグ

		try {
			//KeepBeanより値取得
			String ins = mst001301_UpdateBean.getValue("getInsertFlg",Keepb);
			if( ins == null ){
				flg = false;
			}
			String upd = mst001301_UpdateBean.getValue("getUpdateFlg",Keepb);
			if( upd == null ){
				flg = false;
			}
			String del = mst001301_UpdateBean.getValue("getDeleteFlg",Keepb);
			if( del == null ){
				flg = false;
			}
			String ref = mst001301_UpdateBean.getValue("getReferenceFlg",Keepb);
			if( ref == null ){
				flg = false;
			}
			String pd  = mst001301_UpdateBean.getValue("getProcessingDivision",Keepb);		
			if( pd == null ){
				pd = mst000101_ConstDictionary.PROCESS_REFERENCE;
			}
			//初期値
			if(flg){
				//値取得成功
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT,enable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE,enable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,enable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,enable);
				
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,uncheck);
			} else {
				//値取得失敗
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT,disable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE,disable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,disable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,disable);
				
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,uncheck);
	
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keepb,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return map;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keepb,map.get("0015").toString()) == null){
					return map;
				}
				return map;
			}
			//操作可否設定
			if(ins.equals(mst004801_NewSyoriRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT,disable);
			}
			if(upd.equals(mst004901_UpdateSyoriRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE,disable);
			}
			if(del.equals(mst005001_DelRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,disable);
			}
			if(ref.equals(mst005101_ViewRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,disable);
			}
			
			//初期値設定
			if(chkNullString(pd).trim().equals("") || pd.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				}
			}
		} catch(Exception e) {					
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keepb,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return map;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keepb,map.get("0015").toString()) == null){
				return map;
			}
		} finally {
		}
		return map;
	}

	/**
	 * 処理区分押下可否、初期値を設定する(登録・削除・照会)
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getPermission(KeepBean) -&gt; "000000"<br>
	 * <br>
	 * 権限により処理状況の押下可否及び初期値を設定する<br>
	 * <br>
	 * @param	Bean	KeepBean
	 * @return	Map		map
	 */
	public static Map getPermission2(Object Keepb)
	{
		Map map = new HashMap();
		String disable	= "disabled";	//使用不可
		String check	= "checked";	//使用可
		String enable	= "";			//チェック
		String uncheck	= "";			//アンチェック
		boolean flg	= true;		//エラーフラグ

		try {
			//KeepBeanより値取得
			String ins = mst001301_UpdateBean.getValue("getInsertFlg",Keepb);
			if( ins == null ){
				flg = false;
			}
			String upd = mst001301_UpdateBean.getValue("getUpdateFlg",Keepb);
			if( upd == null ){
				flg = false;
			}
			String del = mst001301_UpdateBean.getValue("getDeleteFlg",Keepb);
			if( del == null ){
				flg = false;
			}
			String ref = mst001301_UpdateBean.getValue("getReferenceFlg",Keepb);
			if( ref == null ){
				flg = false;
			}
			String pd  = mst001301_UpdateBean.getValue("getProcessingDivision",Keepb);		
			if( pd == null ){
				pd = mst000101_ConstDictionary.PROCESS_INSERT;
			}
			//初期値
			if(flg){
				//値取得成功
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,enable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,enable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,enable);
				
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,uncheck);
			} else {
				//値取得失敗
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,disable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,disable);
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,disable);
				
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,uncheck);
				map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,uncheck);
	
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keepb,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return map;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keepb,map.get("0015").toString()) == null){
					return map;
				}
				return map;
			}
			//操作可否設定
			if(ins.equals(mst004801_NewSyoriRiyoukbDictionary.KANOU.getCode()) || upd.equals(mst004901_UpdateSyoriRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,disable);
			}
			if(del.equals(mst005001_DelRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE,disable);
			}
			if(ref.equals(mst005101_ViewRiyoukbDictionary.KANOU.getCode())){
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,enable);
			} else {
				map.put(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE,disable);
			}
			
			//初期値設定
			if(chkNullString(pd).trim().equals("") || pd.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				}
			}
			if(pd.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)){
				if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_REFERENCE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_REFERENCE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_INSERT + mst000101_ConstDictionary.PROCESS_UPDATE,check);
				} else if(map.get(mst000101_ConstDictionary.PROCESS_DISABLED + mst000101_ConstDictionary.PROCESS_DELETE).toString().equals(enable)){
					map.put(mst000101_ConstDictionary.PROCESS_CHECKED + mst000101_ConstDictionary.PROCESS_DELETE,check);
				}
			}
		} catch(Exception e) {					
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keepb,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return map;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keepb,map.get("0015").toString()) == null){
				return map;
			}
		} finally {
		}
		return map;
	}

	/**
	 * 価格チェックマスタとのつき合わせチェック
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getKakakuCheck(db, "0000", "11111111", "1", 5000, "20050501") -&gt; "N"<br>
	 * <br>
	 * 戻り値はディクショナリのエラー値<br>
	 * <br>
	 * @param db		STCLIBのDBインスタンス
	 * @param hankuCd	販区コード
	 * @param syohinCd	商品コード
	 * @param kakakuKb	価格区分
	 * @param kakaku	比較する価格
	 * @param yukoDt	チェック日
	 * @return		String
	 */
	public static String getKakakuCheck(mst000101_DbmsBean db,
											String hankuCd,
											String syohinCd,
											String kakakuKb,
											double kakaku,
											String yukoDt) throws SQLException
	{
		String strSQL = new String();
		String ret = mst000101_ConstDictionary.ERR_CHK_NORMAL;
		double minKakaku = 0;

		try {
			//販区コードが入力されていない場合は先に販区コードを決定する
			strSQL = strSQL + "SELECT ";
			strSQL = strSQL + "    MIN_KAKAKU_VL ";
			strSQL = strSQL + "   ,CHECK_ST_DT ";
			strSQL = strSQL + "   ,CHECK_ED_DT ";
			strSQL = strSQL + "FROM ";
			strSQL = strSQL + "    R_KAKAKUCHECK ";
			strSQL = strSQL + "WHERE ";
			strSQL = strSQL + "    HANKU_CD = '" + hankuCd + "' ";
			strSQL = strSQL + "AND SYOHIN_CD = '" + syohinCd + "' ";
			strSQL = strSQL + "AND KAKAKU_KB = '" + kakakuKb + "' ";
			strSQL = strSQL + "AND CHECK_ST_DT <= '" + yukoDt + "' ";
			strSQL = strSQL + "AND CHECK_ED_DT >= '" + yukoDt + "' ";
			strSQL = strSQL + "AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ";

			ResultSet rset = db.executeQuery(strSQL);    //抽出結果(ResultSet)
			//データが存在する場合価格チェックを行う
			if (rset.next()) {
				minKakaku = rset.getDouble("min_kakaku_vl");
				if (kakaku < minKakaku) {
					//価格を下回っている場合はエラー
					ret = mst000101_ConstDictionary.ERR_CHK_ERROR;
				}
			}
		} catch(SQLException  sqle) {					
			throw sqle;
		} finally {
		}
		return ret;
	}

	/**
	 * エラーメッセージ配列を取得する関数
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getMsg() -&gt; メッセージ配列<br>
	 * <br>
	 * @return    メッセージMap
	*/
	public static Map getPageLink()
	{
		Map retMap  = new HashMap();

		retMap.put("mst110101","mst110101_SyohinIryouHatiketaInit");
		retMap.put("mst120101","mst120101_SyohinIryouJuniketaInit");
		retMap.put("mst130101","mst130101_SyohinJyukyoInit");
		retMap.put("mst140101","mst140101_SyohinKakouSyokuhinInit");
		retMap.put("mst150101","mst150101_SyohinSeisenInit");
		retMap.put("mst160101","mst160101_SyohinIkkatuMenteInit");
		retMap.put("mst180101","mst180101_TensyohinReigaiInit");
		retMap.put("mst190101","mst190101_TensyohinReigaiInit");
		retMap.put("mst200101","mst200101_TensyohinReigaiInit");
		retMap.put("mst210101","mst210101_TensyohinReigaiInit");
		retMap.put("mst220101","mst220101_TensyohinReigaiInit");
		retMap.put("mst230101","mst230101_SyohinConvertInit");
		retMap.put("mst230601","mst230601_SyohinConvertInsert");
		retMap.put("mst240101","mst240101_AsortmentInit");
		retMap.put("mst250101","mst250101_SyohinAkibanInit");
		retMap.put("mst260101","mst260101_SyokaiDonyuIryouInit");
		retMap.put("mst270101","mst270101_SyokaiDonyuJyukyoInit");
		retMap.put("mst280101","mst280101_KakakuCheckInit");
		retMap.put("mst290101","mst290101_SetSyohinInit");
		retMap.put("mst300101","mst300101_TanpinTenToriatukaiInit");
		retMap.put("mst310101","mst310101_TanpinTenToriatukaiInit");
		retMap.put("mst370101","mst370101_ExcelDownloadInit");
		retMap.put("mst380101","mst380101_ExcelUploadInit");
		retMap.put("mst410101","mst410101_NameCtfInit");
		retMap.put("mst420101","mst420101_SyohinKaisoInit");
		retMap.put("mst430101","mst430101_SiiresakiItiranInit");
		retMap.put("mst440101","mst440101_SiiresakiInit");
		retMap.put("mst450101","mst450101_TenbetuSiiresakiInit");
		retMap.put("mst460101","mst460101_HaisouInit");
		retMap.put("mst470101","mst470101_HakoKanriInit");
		retMap.put("mst480101","mst480101_HakoKanriInit");
		retMap.put("mst490101","mst490101_JidohachuseigyoInit");
		retMap.put("mst500101","mst500101_HachuNohinKijunbiInit");
		retMap.put("mst510101","mst510101_SiireHachuNohinNgInit");
		retMap.put("mst510201","mst510201_SiireHachuNohinNgRetrieve");
		retMap.put("mst510301","mst510301_SiireHachuNohinNgUpdate");
		retMap.put("mst520101","mst520101_TenpoListInit");
		retMap.put("mst530101","mst530101_TenpoInit  ");
		retMap.put("mst540101","mst540101_HachuNohinNgInit");
		retMap.put("mst550101","mst550101_TenHankuInit");
		retMap.put("mst560101","mst560101_ButuryuTenpoInit");
		retMap.put("mst570101","mst570101_ButuryuKeiroInit");
		retMap.put("mst580101","mst580101_TenGroupNoInit");
		retMap.put("mst600101","mst600101_JidohaibanInit");
		retMap.put("mst610101","mst610101_TanTenSeigyoInit");
		retMap.put("mst620101","mst620101_SyohinZokuseiInit");
		retMap.put("mst630101","mst630101_SyuukeiSyubetuInit");			//集計種別登録
		retMap.put("mst640101","mst640101_SyuukeiSyubetuInfoInit");		//集計品種情報登録
		retMap.put("mst650101","mst650101_SyuukeiSyubetuDetailInit");	//集計品種明細登録
		retMap.put("mst660101","mst660101_TanpinJidoSaibanInit");		//単品自動採番登録
		retMap.put("mst710101","mst710101_KeyPluInit");					//キーPLUマスタ登録
		retMap.put("mst730101","mst730101_BundleMixPlanInit");			//BM企画特売登録
		retMap.put("mst740101","mst740101_BundleMixCodeInit");			//BMコード登録
		retMap.put("mst750101","mst750101_BundleMixGoodsInit");			//BM商品登録
		retMap.put("mst760101","mst760101_HinsyuIkkatsuSendInit");		//品種一括送信
		retMap.put("mst770101","mst770101_TanpinIkkatsuSendInit");		//単品一括送信
		retMap.put("mst780101","mst780101_TenbetsuHinsyuTenpoInit");		//店別品種登録（店舗）
		retMap.put("mst790101","mst790101_TenbetsuHinsyuHinsyuInit");		//店別品種登録（品種）
		retMap.put("mst800101","mst800101_KeiryokiInit");
		retMap.put("mst820101","mst820101_ThemeItemInit");
		retMap.put("mst830101","mst830101_KeiryokiThemeInit");
		retMap.put("mst840101","mst840101_TenkabutuInit");
		retMap.put("mst870101","mst870101_KeiryokiUploadInit");

		return retMap;
	}

	/**
	 * JDK1.3のShift_JISに対応した文字列へのエンコーディングを行う。
	 * ～の文字を例とすると0x301Cを0xff5eに変換する。
	 * @param string 変換を行う文字列
	 * @return 変換後の文字列
	 */
	public static String toMS932( String string )
	{
		StringBuffer buf = new StringBuffer();
		char c;

		for(int i = 0, n = string.length(); i < n; i++)
		{
			switch(c = string.charAt(i))
			{
				case 0x203e: c = 0xffe3; break; 	//----- '￣' 0x8150(SJIS)
				case 0x301c: c = 0xff5e; break;		//----- '～' 0x8160(SJIS)
				case 0x2016: c = 0x2225; break;		//----- '∥' 0x8161(SJIS)
				case 0x2212: c = 0xff0d; break;		//----- '－' 0x817c(SJIS)
			}
			buf.append(c);
		}
		return buf.toString();
	}

	/**
	 * 有効日範囲チェック
	 * 入力された有効日がマスタ日付の範囲内かどうかをチェックします。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getYukoDtRangeCheck(db, yukoDt) -&gt;<br>
	 * <br>
	 * @param 		mst000101_DbmsBean	db			データベース
	 * @param 		String				yukoDt		入力した有効日
	 * @return		String[]	１番目：正常(N) エラー(E)
	 * 							２番目：有効開始日
	 * 							３番目：有効終了日
	 * 							※有効開始日と有効終了日はエラー時、エラーメッセージ表示のために使用
	 */
	public static String[] getYukoDtRangeCheck(mst000101_DbmsBean db, String yukoDt) throws SQLException {
//		パフォーマンス改善対応　都度有効日をＤＢに確認するのではなく、command取得時に一度だけ取得しキャッシュするようにする hiu@vjc 20060901
		String[] strReturn = new String[3]; 
		String strRet = "E";

		String strYukoDtSt = "";							// 開始有効日
		String strYukoDtEd = "";							// 終了有効日

	
		// ===BEGIN=== Modify by kou 2006/9/4
//		if(mst000401_LogicBean.masterYukoDateStart!=null && mst000401_LogicBean.masterYukoDateStart.length()!=0 && mst000401_LogicBean.masterYukoDateEnd !=null && mst000401_LogicBean.masterYukoDateEnd.length()!=0){
//			//基本的にありえない
//			mst000401_LogicBean.getYukoDtRangeFromDataBase(); 	
//		}
//	
//				
//		if(mst000401_LogicBean.masterYukoDateStart!=null && mst000401_LogicBean.masterYukoDateStart.length()!=0 && mst000401_LogicBean.masterYukoDateEnd !=null && mst000401_LogicBean.masterYukoDateEnd.length()!=0){
//
//			strYukoDtSt = mst000401_LogicBean.masterYukoDateStart;
//			strYukoDtEd = mst000401_LogicBean.masterYukoDateEnd ;
//
//			// 範囲チェック
//			if((	Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt) 
//				 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
//					
//				strRet = "N";
//			}
//		}else{
////			基本的にありえない
//			strRet = "E";
//		}
		
		if(mst000401_LogicBean.masterYukoDateStart!=null && mst000401_LogicBean.masterYukoDateStart.length()!=0 && mst000401_LogicBean.masterYukoDateEnd !=null && mst000401_LogicBean.masterYukoDateEnd.length()!=0){

//			↓↓2006.09.05 H.Yamamoto カスタマイズ修正↓↓
			strYukoDtSt = mst000401_LogicBean.masterYukoDateStart;
			strYukoDtEd = mst000401_LogicBean.masterYukoDateEnd ;
//			↑↑2006.09.05 H.Yamamoto カスタマイズ修正↑↑

			// 開始と終了日付がある場合、直接範囲チェック
			if((	Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt) 
				 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
					
				strRet = "N";
			}
		} else {
			//ない場合、DBから取る、基本的にありえない
			mst000401_LogicBean.getYukoDtRangeFromDataBase(); 	

			strYukoDtSt = mst000401_LogicBean.masterYukoDateStart;
			strYukoDtEd = mst000401_LogicBean.masterYukoDateEnd ;

			// 範囲チェック
			if((	Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt) 
				 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
					
				strRet = "N";
			}
		}		
		// ===END=== Modify by kou 2006/9/4
		
		strReturn[0] = strRet;
		strReturn[1] = strYukoDtSt;
		strReturn[2] = strYukoDtEd;

		return strReturn;
	}

	//20061114 add start 
	//引数をセットすることでDBアクセスをなくすようメソッド追加
	public  static void getYukoDtRangeFromDataBase( String str ){
		if( str == null || str.trim().length() != 8 || !MDWareDateUtility.isDateTime( str ) ) {
			getYukoDtRangeFromDataBase();
		} else {
			Calendar t;
			MDWareDateUtility mdate = new MDWareDateUtility( str );
			String s = mdate.addDateTime( Calendar.DAY_OF_MONTH, 1 ).toStringYYYYMMDD();
			String e = mdate.addDateTime( Calendar.MONTH, 12 ).toStringYYYYMMDD();
			synchronized(mst000401_LogicBean.masterYukoDateStart){
				synchronized(mst000401_LogicBean.masterYukoDateEnd){
					mst000401_LogicBean.masterYukoDateStart = s;
					mst000401_LogicBean.masterYukoDateEnd  = e;
				}
			}
		}
	}
	//20061114 add end
	
	//パフォーマンス改善対応　都度有効日をＤＢに確認するのではなく、command取得時に一度だけ取得しキャッシュするようにする hiu@vjc 20060901
	//AbstractMasterCommandから呼ばれる
	public static void getYukoDtRangeFromDataBase()  {

		PreparedStatementDataBase db = new PreparedStatementDataBase ();
		ResultSet rset = null;

		try {
		
			// システムコントロール取得用共通関数を使用する
			String mstDateDtStr = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.ONLINE_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
			MDWareDateUtility mdate = new MDWareDateUtility( mstDateDtStr );
			String s = mdate.addDateTime( Calendar.DAY_OF_MONTH, 1 ).toStringYYYYMMDD();
			String e = mdate.addDateTime( Calendar.MONTH, 12 ).toStringYYYYMMDD();
			synchronized(mst000401_LogicBean.masterYukoDateStart){
				synchronized(mst000401_LogicBean.masterYukoDateEnd){
					mst000401_LogicBean.masterYukoDateStart = s;
					mst000401_LogicBean.masterYukoDateEnd  = e;
				}
			}
			
		} finally {
			try{
				rset.close();
				
			}catch(Exception e){
				rset=null;	
			}
			try{
				db.close();
				
			}catch(Exception e){
				db=null;	
			}	  
			
		}
		
	}


	/**
	 * 有効日範囲チェック(生鮮用)
	 * 入力された有効日がマスタ日付の範囲内かどうかをチェックします。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getYukoDtRangeCheck(db, yukoDt) -&gt;<br>
	 * <br>
	 * @param 		mst000101_DbmsBean	db			データベース
	 * @param 		String				yukoDt		入力した有効日
	 * @return		String[]	１番目：正常(N) エラー(E)
	 * 							２番目：有効開始日
	 * 							３番目：有効終了日
	 * 							※有効開始日と有効終了日はエラー時、エラーメッセージ表示のために使用
	 */
	public static String[] getYukoDtRangeCheckFre(mst000101_DbmsBean db, String yukoDt) throws SQLException {

		String[] strReturn = new String[3]; 
		String strRet = "E";

		String strYukoDtSt = "";							// 開始有効日
		String strYukoDtEd = "";							// 終了有効日

		try {
			StringBuffer buffSql = new StringBuffer();
			ResultSet rset = null;
		
			// 開始有効日(マスタ日付＋２日)、終了有効日(マスタ日付＋２日の１年後)
			buffSql.append(" SELECT ");
//		      ↓↓　移植(2006.05.25) ↓↓
			buffSql.append("		TO_CHAR(TO_DATE(MST_DATE_DT, 'YYYYMMDD')+2 DAYS, 'YYYYMMDD') AS YUKO_DT_STR, ");
			buffSql.append("		TO_CHAR(ADD_MONTHS(TO_DATE(MST_DATE_DT, 'YYYYMMDD'), 12)+1 DAYS, 'YYYYMMDD') AS YUKO_DT_END ");
//		      ↑↑　移植(2006.05.25) ↑↑
			buffSql.append(" FROM SYSTEM_CONTROL ");
	
			rset = db.executeQuery(buffSql.toString());
				
			if(rset.next()){
				strYukoDtSt = rset.getString("YUKO_DT_STR");
				strYukoDtEd = rset.getString("YUKO_DT_END");

				// 範囲チェック
				if((	Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt) 
					 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
					strRet = "N";
				}
			}else{
				strRet = "E";
			}
			if(rset != null){
				rset.close();
			}
			strReturn[0] = strRet;
			strReturn[1] = strYukoDtSt;
			strReturn[2] = strYukoDtEd;
		} catch(SQLException sqle) {					
			throw sqle;
		} finally {
		}
		return strReturn;
	}

	/**
	 * 商品マスタ用　有効日範囲チェック（即伝対応）
	 * 入力された有効日がマスタ日付の範囲内かどうかをチェックします。
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.getYukoDtRangeCheckSyohin(db, yukoDt) -&gt;<br>
	 * <br>
	 * @param 		mst000101_DbmsBean	db			データベース
	 * @param 		String				yukoDt		入力した有効日
	 * @return		String[]	１番目：正常(N) エラー(E)
	 * 							２番目：有効開始日
	 * 							３番目：有効終了日
	 * 							※有効開始日と有効終了日はエラー時、エラーメッセージ表示のために使用
	 */
	public static String[] getYukoDtRangeCheckSyohin(mst000101_DbmsBean db, String yukoDt) throws SQLException {

		String[] strReturn = new String[3]; 
		String strRet = "E";

		String strYukoDtSt = "";							// 開始有効日
		String strYukoDtEd = "";							// 終了有効日

		try {
			StringBuffer buffSql = new StringBuffer();
			ResultSet rset = null;
		
//			RMSTDATEUtil.getMstDateDt
			
			// 開始有効日(マスタ日付)、終了有効日(マスタ日付の１年後)
			buffSql.append(" SELECT ");
			buffSql.append("		TO_CHAR(TO_DATE(MST_DATE_DT, 'YYYYMMDD'), 'YYYYMMDD') AS YUKO_DT_STR, ");
			buffSql.append("		TO_CHAR(ADD_MONTHS(TO_DATE(MST_DATE_DT, 'YYYYMMDD'), 12), 'YYYYMMDD') AS YUKO_DT_END ");
//			buffSql.append(" FROM SYSTEM_CONTROL ");
			buffSql.append(" FROM (SELECT PARAMETER_TX AS MST_DATE_DT FROM SYSTEM_CONTROL WHERE SUBSYSTEM_ID = 'MST' AND PARAMETER_ID = 'ONLINE_DT') A");

			rset = db.executeQuery(buffSql.toString());

			if(rset.next()){
				strYukoDtSt = rset.getString("YUKO_DT_STR");
				strYukoDtEd = rset.getString("YUKO_DT_END");

				// 範囲チェック
				if((	Long.parseLong(yukoDt) >= Long.parseLong(strYukoDtSt) 
					 && Long.parseLong(yukoDt) <= Long.parseLong(strYukoDtEd))){
					strRet = "N";
				}
			}else{
				strRet = "E";
			}
			if(rset != null){
				rset.close();
			}

			strReturn[0] = strRet;
			strReturn[1] = strYukoDtSt;
			strReturn[2] = strYukoDtEd;
		} catch(SQLException sqle) {					
			throw sqle;
		}
		return strReturn;
	}


	//ADD by Tanigawa 2006/9/26 障害票№0051対応 引数で受け取った値をXXX-XXXXフォーマットにして返す。
	//							3桁以下の場合は、フォーマットせずに値を返す START
	public static String formatYubinCd(String yubin_cd){

		//NULLチェック
		String tmpYubinCd = ( (yubin_cd == null) ? "" : yubin_cd.trim() );	//nullなら空文字、null以外ならtrimして取得
		if( tmpYubinCd.trim().length() <= 3 ){			//郵便番号が3桁以下ならそのまま(念には念を、trim、trim)
			tmpYubinCd = tmpYubinCd.trim();
		}else if( tmpYubinCd.trim().length() >= 4 ){	//郵便番号が4桁以上ならフォーマット
			MDWareStringUtility mdsuYubinCd = new MDWareStringUtility(tmpYubinCd.trim());
			String bufStr = mdsuYubinCd.rpad(' ',7).toString();							//空文字で無理矢理7桁文字列作成
			tmpYubinCd = (bufStr.substring(0,3) + "-" + bufStr.substring(3,7).trim());		//FORMAT!!!
		}else{										//その他の怪しいデータは、そのまま
			tmpYubinCd = tmpYubinCd.trim();
		}
		
		return tmpYubinCd;
	}
	//ADD by Tanigawa 2006/9/26 障害票№0051  END 
	
}