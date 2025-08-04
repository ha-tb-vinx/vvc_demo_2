package mdware.common.util;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.io.*;
import java.util.*;  // 20030111 @ADD yamanaka

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class StringCheck {
	private static final int NUMBER = 0;
	private static final int PERIOD = 1;
	private static final int SINGLE_BYTE_SPACE = 2;
	private static final int MULTI_BYTE_SPACE = 3;
	private static final int SINGLE_BYTE = 4;
	private static final int MULTI_BYTE = 5;
	private static final int ERROR = 6;

// 20030111 @ADD yamanaka 機能強化 start
	private static final char START_SINGLE_NUMBER	= 0x30;
	private static final char END_SINGLE_NUMBER		= 0x39;

	private static final char START_SINGLE_UPPER	= 0x41;
	private static final char END_SINGLE_UPPER		= 0x5a;

	private static final char START_SINGLE_LOWER	= 0x61;
	private static final char END_SINGLE_LOWER		= 0x7a;

	private static final char START_SINGLE_KANA		= 0xff66;
	private static final char END_SINGLE_KANA		= 0xff9e;

	private static final char START_MULTI_NUMBER	= 0xff10;
	private static final char END_MULTI_NUMBER		= 0xff19;

	private static final char START_MULTI_UPPER		= 0xff21;
	private static final char END_MULTI_UPPER		= 0xff3a;

	private static final char START_MULTI_LOWER		= 0xff41;
	private static final char END_MULTI_LOWER		= 0xff5a;

	private static final char START_MULTI_HIRA		= 0x3041;
	private static final char END_MULTI_HIRA		= 0x309e;

	private static final char START_MULTI_KANA		= 0x30a1;
	private static final char END_MULTI_KANA		= 0x30fe;

	private static final Map toSingle = new HashMap();
	private static final Map toMulti = new HashMap();

	static
	{
		toMulti.put(" ","　");
		toMulti.put("!","！");
		toMulti.put("#","＃");
		toMulti.put("$","＄");
		toMulti.put("%","％");
		toMulti.put("&","＆");
		toMulti.put("'","＇");
		toMulti.put("(","（");
		toMulti.put(")","）");
		toMulti.put("*","＊");
		toMulti.put("+","＋");
		toMulti.put(",","，");
		toMulti.put("-","－");
		toMulti.put(".","．");
		toMulti.put("/","／");
		toMulti.put("0","０");
		toMulti.put("1","１");
		toMulti.put("2","２");
		toMulti.put("3","３");
		toMulti.put("4","４");
		toMulti.put("5","５");
		toMulti.put("6","６");
		toMulti.put("7","７");
		toMulti.put("8","８");
		toMulti.put("9","９");
		toMulti.put(":","：");
		toMulti.put(";","；");
		toMulti.put("<","＜");
		toMulti.put("=","＝");
		toMulti.put(">","＞");
		toMulti.put("@","？");
		toMulti.put("?","＠");
		toMulti.put("A","Ａ");
		toMulti.put("B","Ｂ");
		toMulti.put("C","Ｃ");
		toMulti.put("D","Ｄ");
		toMulti.put("E","Ｅ");
		toMulti.put("F","Ｆ");
		toMulti.put("G","Ｇ");
		toMulti.put("H","Ｈ");
		toMulti.put("I","Ｉ");
		toMulti.put("J","Ｊ");
		toMulti.put("K","Ｋ");
		toMulti.put("L","Ｌ");
		toMulti.put("M","Ｍ");
		toMulti.put("N","Ｎ");
		toMulti.put("O","Ｏ");
		toMulti.put("P","Ｐ");
		toMulti.put("Q","Ｑ");
		toMulti.put("R","Ｒ");
		toMulti.put("S","Ｓ");
		toMulti.put("T","Ｔ");
		toMulti.put("U","Ｕ");
		toMulti.put("V","Ｖ");
		toMulti.put("W","Ｗ");
		toMulti.put("X","Ｘ");
		toMulti.put("Y","Ｙ");
		toMulti.put("Z","Ｚ");
		toMulti.put("[","［");
		toMulti.put("\\","＼");
		toMulti.put("]","］");
		toMulti.put("^","＾");
		toMulti.put("_","＿");
		toMulti.put("`","｀");
		toMulti.put("a","ａ");
		toMulti.put("b","ｂ");
		toMulti.put("c","ｃ");
		toMulti.put("d","ｄ");
		toMulti.put("e","ｅ");
		toMulti.put("f","ｆ");
		toMulti.put("g","ｇ");
		toMulti.put("h","ｈ");
		toMulti.put("i","ｉ");
		toMulti.put("j","ｊ");
		toMulti.put("k","ｋ");
		toMulti.put("l","ｌ");
		toMulti.put("m","ｍ");
		toMulti.put("n","ｎ");
		toMulti.put("o","ｏ");
		toMulti.put("p","ｐ");
		toMulti.put("q","ｑ");
		toMulti.put("r","ｒ");
		toMulti.put("s","ｓ");
		toMulti.put("t","ｔ");
		toMulti.put("u","ｕ");
		toMulti.put("v","ｖ");
		toMulti.put("w","ｗ");
		toMulti.put("x","ｘ");
		toMulti.put("y","ｙ");
		toMulti.put("z","ｚ");
		toMulti.put("{","｛");
		toMulti.put("|","｜");
		toMulti.put("}","｝");
		toMulti.put("ｧ","ァ");
		toMulti.put("ｨ","ィ");
		toMulti.put("ｩ","ゥ");
		toMulti.put("ｪ","ェ");
		toMulti.put("ｫ","ォ");
		toMulti.put("ｬ","ャ");
		toMulti.put("ｭ","ュ");
		toMulti.put("ｮ","ョ");
		toMulti.put("ｯ","ッ");
		toMulti.put("ｰ","ー");
		toMulti.put("ｱ","ア");
		toMulti.put("ｲ","イ");
		toMulti.put("ｳ","ウ");
		toMulti.put("ｴ","エ");
		toMulti.put("ｵ","オ");
		toMulti.put("ｶ","カ");
		toMulti.put("ｷ","キ");
		toMulti.put("ｸ","ク");
		toMulti.put("ｹ","ケ");
		toMulti.put("ｺ","コ");
		toMulti.put("ｻ","サ");
		toMulti.put("ｼ","シ");
		toMulti.put("ｽ","ス");
		toMulti.put("ｾ","セ");
		toMulti.put("ｿ","ソ");
		toMulti.put("ﾀ","タ");
		toMulti.put("ﾁ","チ");
		toMulti.put("ﾂ","ツ");
		toMulti.put("ﾃ","テ");
		toMulti.put("ﾄ","ト");
		toMulti.put("ﾅ","ナ");
		toMulti.put("ﾆ","ニ");
		toMulti.put("ﾇ","ヌ");
		toMulti.put("ﾈ","ネ");
		toMulti.put("ﾉ","ノ");
		toMulti.put("ﾊ","ハ");
		toMulti.put("ﾋ","ヒ");
		toMulti.put("ﾌ","フ");
		toMulti.put("ﾍ","ヘ");
		toMulti.put("ﾎ","ホ");
		toMulti.put("ﾏ","マ");
		toMulti.put("ﾐ","ミ");
		toMulti.put("ﾑ","ム");
		toMulti.put("ﾒ","メ");
		toMulti.put("ﾓ","モ");
		toMulti.put("ﾔ","ヤ");
		toMulti.put("ﾕ","ユ");
		toMulti.put("ﾖ","ヨ");
		toMulti.put("ﾗ","ラ");
		toMulti.put("ﾘ","リ");
		toMulti.put("ﾙ","ル");
		toMulti.put("ﾚ","レ");
		toMulti.put("ﾛ","ロ");
		toMulti.put("ﾜ","ワ");
		toMulti.put("ｦ","ヲ");
		toMulti.put("ﾝ","ン");
		toMulti.put("ﾞ","゛");
		toMulti.put("ﾟ","゜");
		toMulti.put("ｳﾞ","ヴ");
		toMulti.put("ｶﾞ","ガ");
		toMulti.put("ｷﾞ","ギ");
		toMulti.put("ｸﾞ","グ");
		toMulti.put("ｹﾞ","ゲ");
		toMulti.put("ｺﾞ","ゴ");
		toMulti.put("ｻﾞ","ザ");
		toMulti.put("ｼﾞ","ジ");
		toMulti.put("ｽﾞ","ズ");
		toMulti.put("ｾﾞ","ゼ");
		toMulti.put("ｿﾞ","ゾ");
		toMulti.put("ﾀﾞ","ダ");
		toMulti.put("ﾁﾞ","ヂ");
		toMulti.put("ﾂﾞ","ヅ");
		toMulti.put("ﾃﾞ","デ");
		toMulti.put("ﾄﾞ","ド");
		toMulti.put("ﾊﾞ","バ");
		toMulti.put("ﾋﾞ","ビ");
		toMulti.put("ﾌﾞ","ブ");
		toMulti.put("ﾍﾞ","ベ");
		toMulti.put("ﾎﾞ","ボ");
		toMulti.put("ﾊﾟ","パ");
		toMulti.put("ﾋﾟ","ピ");
		toMulti.put("ﾌﾟ","プ");
		toMulti.put("ﾍﾟ","ペ");
		toMulti.put("ﾎﾟ","ポ");

		toSingle.put("　"," ");
		toSingle.put("！","!");
		toSingle.put("＃","#");
		toSingle.put("＄","$");
		toSingle.put("％","%");
		toSingle.put("＆","&");
		toSingle.put("＇","'");
		toSingle.put("（","(");
		toSingle.put("）",")");
		toSingle.put("＊","*");
		toSingle.put("＋","+");
		toSingle.put("，",",");
		toSingle.put("－","-");
		toSingle.put("．",".");
		toSingle.put("／","/");
		toSingle.put("０","0");
		toSingle.put("１","1");
		toSingle.put("２","2");
		toSingle.put("３","3");
		toSingle.put("４","4");
		toSingle.put("５","5");
		toSingle.put("６","6");
		toSingle.put("７","7");
		toSingle.put("８","8");
		toSingle.put("９","9");
		toSingle.put("：",":");
		toSingle.put("；",";");
		toSingle.put("＜","<");
		toSingle.put("＝","=");
		toSingle.put("＞",">");
		toSingle.put("？","@");
		toSingle.put("＠","?");
		toSingle.put("Ａ","A");
		toSingle.put("Ｂ","B");
		toSingle.put("Ｃ","C");
		toSingle.put("Ｄ","D");
		toSingle.put("Ｅ","E");
		toSingle.put("Ｆ","F");
		toSingle.put("Ｇ","G");
		toSingle.put("Ｈ","H");
		toSingle.put("Ｉ","I");
		toSingle.put("Ｊ","J");
		toSingle.put("Ｋ","K");
		toSingle.put("Ｌ","L");
		toSingle.put("Ｍ","M");
		toSingle.put("Ｎ","N");
		toSingle.put("Ｏ","O");
		toSingle.put("Ｐ","P");
		toSingle.put("Ｑ","Q");
		toSingle.put("Ｒ","R");
		toSingle.put("Ｓ","S");
		toSingle.put("Ｔ","T");
		toSingle.put("Ｕ","U");
		toSingle.put("Ｖ","V");
		toSingle.put("Ｗ","W");
		toSingle.put("Ｘ","X");
		toSingle.put("Ｙ","Y");
		toSingle.put("Ｚ","Z");
		toSingle.put("［","[");
		toSingle.put("＼","\\");
		toSingle.put("］","]");
		toSingle.put("＾","^");
		toSingle.put("＿","_");
		toSingle.put("｀","`");
		toSingle.put("ａ","a");
		toSingle.put("ｂ","b");
		toSingle.put("ｃ","c");
		toSingle.put("ｄ","d");
		toSingle.put("ｅ","e");
		toSingle.put("ｆ","f");
		toSingle.put("ｇ","g");
		toSingle.put("ｈ","h");
		toSingle.put("ｉ","i");
		toSingle.put("ｊ","j");
		toSingle.put("ｋ","k");
		toSingle.put("ｌ","l");
		toSingle.put("ｍ","m");
		toSingle.put("ｎ","n");
		toSingle.put("ｏ","o");
		toSingle.put("ｐ","p");
		toSingle.put("ｑ","q");
		toSingle.put("ｒ","r");
		toSingle.put("ｓ","s");
		toSingle.put("ｔ","t");
		toSingle.put("ｕ","u");
		toSingle.put("ｖ","v");
		toSingle.put("ｗ","w");
		toSingle.put("ｘ","x");
		toSingle.put("ｙ","y");
		toSingle.put("ｚ","z");
		toSingle.put("｛","{");
		toSingle.put("｜","|");
		toSingle.put("｝","}");
		toSingle.put("ァ","ｧ");
		toSingle.put("ィ","ｨ");
		toSingle.put("ゥ","ｩ");
		toSingle.put("ェ","ｪ");
		toSingle.put("ォ","ｫ");
		toSingle.put("ャ","ｬ");
		toSingle.put("ュ","ｭ");
		toSingle.put("ョ","ｮ");
		toSingle.put("ッ","ｯ");
		toSingle.put("ー","ｰ");
		toSingle.put("ア","ｱ");
		toSingle.put("イ","ｲ");
		toSingle.put("ウ","ｳ");
		toSingle.put("エ","ｴ");
		toSingle.put("オ","ｵ");
		toSingle.put("カ","ｶ");
		toSingle.put("キ","ｷ");
		toSingle.put("ク","ｸ");
		toSingle.put("ケ","ｹ");
		toSingle.put("コ","ｺ");
		toSingle.put("サ","ｻ");
		toSingle.put("シ","ｼ");
		toSingle.put("ス","ｽ");
		toSingle.put("セ","ｾ");
		toSingle.put("ソ","ｿ");
		toSingle.put("タ","ﾀ");
		toSingle.put("チ","ﾁ");
		toSingle.put("ツ","ﾂ");
		toSingle.put("テ","ﾃ");
		toSingle.put("ト","ﾄ");
		toSingle.put("ナ","ﾅ");
		toSingle.put("ニ","ﾆ");
		toSingle.put("ヌ","ﾇ");
		toSingle.put("ネ","ﾈ");
		toSingle.put("ノ","ﾉ");
		toSingle.put("ハ","ﾊ");
		toSingle.put("ヒ","ﾋ");
		toSingle.put("フ","ﾌ");
		toSingle.put("ヘ","ﾍ");
		toSingle.put("ホ","ﾎ");
		toSingle.put("マ","ﾏ");
		toSingle.put("ミ","ﾐ");
		toSingle.put("ム","ﾑ");
		toSingle.put("メ","ﾒ");
		toSingle.put("モ","ﾓ");
		toSingle.put("ヤ","ﾔ");
		toSingle.put("ユ","ﾕ");
		toSingle.put("ヨ","ﾖ");
		toSingle.put("ラ","ﾗ");
		toSingle.put("リ","ﾘ");
		toSingle.put("ル","ﾙ");
		toSingle.put("レ","ﾚ");
		toSingle.put("ロ","ﾛ");
		toSingle.put("ワ","ﾜ");
		toSingle.put("ヲ","ｦ");
		toSingle.put("ン","ﾝ");
		toSingle.put("゛","ﾞ");
		toSingle.put("゜","ﾟ");
		toSingle.put("ヴ","ｳﾞ");
		toSingle.put("ガ","ｶﾞ");
		toSingle.put("ギ","ｷﾞ");
		toSingle.put("グ","ｸﾞ");
		toSingle.put("ゲ","ｹﾞ");
		toSingle.put("ゴ","ｺﾞ");
		toSingle.put("ザ","ｻﾞ");
		toSingle.put("ジ","ｼﾞ");
		toSingle.put("ズ","ｽﾞ");
		toSingle.put("ゼ","ｾﾞ");
		toSingle.put("ゾ","ｿﾞ");
		toSingle.put("ダ","ﾀﾞ");
		toSingle.put("ヂ","ﾁﾞ");
		toSingle.put("ヅ","ﾂﾞ");
		toSingle.put("デ","ﾃﾞ");
		toSingle.put("ド","ﾄﾞ");
		toSingle.put("バ","ﾊﾞ");
		toSingle.put("ビ","ﾋﾞ");
		toSingle.put("ブ","ﾌﾞ");
		toSingle.put("ベ","ﾍﾞ");
		toSingle.put("ボ","ﾎﾞ");
		toSingle.put("パ","ﾊ゜");
		toSingle.put("ピ","ﾋ゜");
		toSingle.put("プ","ﾌ゜");
		toSingle.put("ペ","ﾍ゜");
		toSingle.put("ポ","ﾎ゜");

	}
// 20030111 @ADD yamanaka 機能強化 end


	/**
	 * 渡された文字列が半角文字のみか？
	 * 全角文字や変換できない文字があれば false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingle( String base )
	{
		if( base == null )
			throw new NullPointerException();
		boolean ret[] = getCharType( base );
		if( ret[ERROR] )
			throw new RuntimeException();
		if( ret[MULTI_BYTE] || ret[MULTI_BYTE_SPACE] )
			return false;
		return true;
	}
	/**
	 * 渡された文字列が全角のみか？
	 * 半角文字が存在すると false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isMulti( String base )
	{
		if( base == null )
			throw new NullPointerException();
		boolean ret[] = getCharType( base );
		if( ret[ERROR] )
			throw new RuntimeException();
		if( ret[SINGLE_BYTE] || ret[SINGLE_BYTE_SPACE] || ret[PERIOD] || ret[NUMBER] )
			return false;
		return true;
	}
	/**
	 * 渡された文字列が数字か？
	 * 半角で数字と少数点以外は false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isNumeric( String base )
	{
		if( base == null )
			throw new NullPointerException();
		boolean ret[] = getCharType( base );
		if( ret[ERROR] )
			throw new RuntimeException();
		if( ret[SINGLE_BYTE] || ret[SINGLE_BYTE_SPACE] || ret[MULTI_BYTE] || ret[MULTI_BYTE_SPACE] )
			return false;
		if( ret[PERIOD] )
			for( int i = 0, count = 0; i < base.length(); i++ )
				if( base.charAt(i) == '.' )
				{
					count++;
					if( count > 1 )
						return false;
				}
		return true;
	}
	private static boolean[] getCharType( String base )
	{
		boolean ret[] = new boolean[ ERROR + 1 ];
		for( int i = 0; i < (ERROR + 1); i++ )
			ret[i] = false;

		for( int i = 0; i < base.length(); i++ )
		{
			try
			{
				char c = base.charAt(i);
				if( c >= '0' && c <= '9' )
					ret[NUMBER] = true;
				else
				if( c == '.' )
					ret[PERIOD] = true;
				else
				if( c == ' ' )
					ret[SINGLE_BYTE_SPACE] = true;
				else
				if( c == '　' )
					ret[MULTI_BYTE_SPACE] = true;
				else
				if( base.substring(i,i+1).getBytes("UTF-8").length == 1 )
					ret[SINGLE_BYTE] = true;
				else
					ret[MULTI_BYTE] = true;
			}
			catch(UnsupportedEncodingException ue)
			{
				ret[ERROR] = true;
				StcLog.getInstance().getLog().debug("次の文字をエンコードに失敗しました。[ "+base.substring(i,i+1)+" ]",ue);
			}
		}
		return ret;
	}
// 20021206 @ADD deguchi V2で当関数追加 start
	/**
	 * 非参照を空文字に変換する。
	 * 渡されたStringがnullであれば空文字を返す。
	 * それ以外の場合は渡された文字をそのまま返します。
	 * @param str 変換したいStringオブジェクト
	 * @return str 変換後の文字列
	 */
	public static String strIndisCvt( String str )
	{
		if ( str == null) str = "";
		return str;
	}
// 20021206 @ADD deguchi V2で当関数追加 end

// 20030111 @ADD yamanaka 機能強化 start
	/**
	 * 渡された文字列が数字か？
	 * 半角で数字と少数点以外は false
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingleNumber( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !(isSingleNumber(base.charAt(i))) )
				return false;
		return true;
	}

	/**
	 * 渡された文字列が実数？
	 * @param base 調べたい文字
	 * @return boolean 結果
	 */
	public static boolean isSingleDouble( String base )
	{
		if( base == null )
			throw new NullPointerException();

		boolean sine = false;
		boolean point = false;

		for( int i = 0; i < base.length(); i++ )
		{
			char c = base.charAt( i );
			if( isSinglePlusMinus(c) )
			{
				if( sine || i != 0 )
					return false;
				sine = true;
			}
			else
			if( isSinglePoint(c) )
			{
				if( point )
					return false;
				point = true;
			}
			else
			if( !isSingleNumber(c) )
				return false;
		}
		return true;
	}

	/**
	 * 渡された文字が半角整数？
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleLong( String base )
	{
		if( base == null )
			throw new NullPointerException();

		boolean sine = false;

		for( int i = 0; i < base.length(); i++ )
		{
			char c = base.charAt( i );
			if( isSinglePlusMinus(c) )
			{
				if( sine || i != 0 )
					return false;
				sine = true;
			}
			else
			if( !isSingleNumber(c) )
				return false;
		}
		return true;
	}

	/**
	 * 渡された文字が半角英大字のみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleUpper( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isSingleUpper( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が半角英小字のみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleLower( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isSingleLower( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が半角カタカナのみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isSingleKana( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
		{
			if( !isSingleKana( base.charAt(i) ) )
				return false;
		}
		return true;
	}

	/**
	 * 渡された文字が全角数字のみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiNumber( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isMultiNumber( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が全角実数？
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiDouble( String base )
	{
		if( base == null )
			throw new NullPointerException();

		boolean sine = false;
		boolean point = false;

		for( int i = 0; i < base.length(); i++ )
		{
			char c = base.charAt( i );
			if( isMultiPlusMinus(c) )
			{
				if( sine || i != 0 )
					return false;
				sine = true;
			}
			else
			if( isMultiPoint(c) )
			{
				if( point )
					return false;
				point = true;
			}
			else
			if( !isMultiNumber(c) )
				return false;
		}
		return true;
	}

	/**
	 * 渡された文字が全角整数？
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiLong( String base )
	{
		if( base == null )
			throw new NullPointerException();

		boolean sine = false;

		for( int i = 0; i < base.length(); i++ )
		{
			char c = base.charAt( i );
			if( isMultiPlusMinus(c) )
			{
				if( sine || i != 0 )
					return false;
				sine = true;
			}
			else
			if( !isMultiNumber(c) )
				return false;
		}
		return true;
	}

	/**
	 * 渡された文字が全角英大字のみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiUpper( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isMultiUpper( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が全角英小字のみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiLower( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isMultiLower( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が全角ひらかなのみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiHira( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isMultiHira( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 渡された文字が全角カタカナのみかを調べる。
	 * @param base String 調べる文字列
	 * @return 違う種類が混じっている時はfalse
	 */
	public static boolean isMultiKana( String base )
	{
		if( base == null )
			throw new NullPointerException();

		for( int i = 0; i < base.length(); i++ )
			if( !isMultiKana( base.charAt(i) ) )
				return false;
		return true;
	}

	/**
	 * 半角のものを全角に変換する。変換できないものはそのままにする。
	 * @param base
	 * @return 変換後の文字
	 */
	public static String toMulti( String base )
	{
		if( base == null )
			throw new NullPointerException();

		StringBuffer ret = new StringBuffer();
		for( int i = 0; i < base.length(); i++ )
		{
			String ss = base.substring(i,i+1);
			if( (i + 1) < base.length() )
			{
				String wk = ss + base.substring(i+1, i+2);
				if( toMulti.get( wk ) != null )
				{
					ret.append( toMulti.get( wk ) );
					i++;
					continue;
				}
			}
			if( toMulti.get(ss) == null )
				ret.append(ss);
			else
				ret.append( (String)toMulti.get(ss) );
		}
		return ret.toString();
	}

	/**
	 * 全角のものを半角に変換する。変換できないものはそのままにする。
	 * @param base
	 * @return 変換後の文字
	 */
	public static String toSingle( String base )
	{
		if( base == null )
			throw new NullPointerException();

		StringBuffer ret = new StringBuffer();
		for( int i = 0; i < base.length(); i++ )
		{
			String ss = base.substring(i,i+1);
			if( toSingle.get(ss) == null )
				ret.append(ss);
			else
				ret.append( (String)toSingle.get(ss) );
		}
		return ret.toString();
	}

// 20030616 @ADD deguchi 機能強化 start
	/**
	 * 渡された文字列が半角英数字のみか。
	 * 全角文字や変換できない文字があれば false
	 * @param base 調べたい文字
	 * @return boolean 結果  true - 妥当
	 */
	public static boolean isSingleAlphanumeric( String base )
	{
		if( base == null ) {
			throw new NullPointerException();
		}
		for( int i = 0; i < base.length(); i++ ) {
			if( isSingleNumber( base.charAt(i) ) || isSingleLower( base.charAt(i) ) || isSingleUpper( base.charAt(i) ) ) {
			} else {
				return false;
			}
		}
		return true;
	}
	//	20030616 @ADD deguchi 機能強化 end

	/**
	 * 渡された文字列が半角か。
	 * 全角文字や半角カナも false
	 * @param base 調べたい文字
	 * @return boolean 結果  true - 妥当
	 */
	public static boolean isSingleNotKana( String base )
	{
		if( base == null ) {
			throw new NullPointerException();
		}
		for( int i = 0; i < base.length(); i++ ) {
			if( isSingleNotKana( base.charAt(i) ) ) {
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 渡された文字が半角でかつ半角ｶﾅ以外かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleNotKana( char c )
	{
		return ( c <= 255 );
	}

	/**
	 * 渡された文字が半角かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingle( char c )
	{
		return ( c <= 255 || isSingleKana(c));
	}

	/**
	 * 渡された文字が半角空白かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleBlank( char c )
	{
		return ( c == ' ' );
	}

	/**
	 * 渡された文字が半角ピリオドかを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSinglePoint( char c )
	{
		return ( c == '.' );
	}

	/**
	 * 渡された文字が半角+-かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSinglePlusMinus( char c )
	{
		return ( c == '-' || c == '+' );
	}

	/**
	 * 渡された文字が半角数字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleNumber( char c )
	{
		return ( START_SINGLE_NUMBER <= c && c <= END_SINGLE_NUMBER );
	}

	/**
	 * 渡された文字が半角英大字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleUpper( char c )
	{
		return ( START_SINGLE_UPPER <= c && c <= END_SINGLE_UPPER );
	}

	/**
	 * 渡された文字が半角英小字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleLower( char c )
	{
		return ( START_SINGLE_LOWER <= c && c <= END_SINGLE_LOWER );
	}

	/**
	 * 渡された文字が半角カタカナかを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isSingleKana( char c )
	{
		return ( START_SINGLE_KANA <= c && c <= END_SINGLE_KANA );
	}

	/**
	 * 渡された文字が全角か調べる
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMulti( char c )
	{
		return ( c > 255 && !isSingleKana(c));
	}

	/**
	 * 渡された文字が全角空白かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiBlank( char c )
	{
		return ( c == '　' );
	}

	/**
	 * 渡された文字が全角ピリオドかを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiPoint( char c )
	{
		return ( c == '．' );
	}

	/**
	 * 渡された文字が全角+-かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiPlusMinus( char c )
	{
		return ( c == '－' || c == '＋' );
	}

	/**
	 * 渡された文字が全角数字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiNumber( char c )
	{
		return ( START_MULTI_NUMBER <= c && c <= END_MULTI_NUMBER );
	}

	/**
	 * 渡された文字が全角英大字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiUpper( char c )
	{
		return ( START_MULTI_UPPER <= c && c <= END_MULTI_UPPER );
	}

	/**
	 * 渡された文字が全角英小字かを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiLower( char c )
	{
		return ( START_MULTI_LOWER <= c && c <= END_MULTI_LOWER );
	}

	/**
	 * 渡された文字が全角ひらかなかを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiHira( char c )
	{
		return ( START_MULTI_HIRA <= c && c <= END_MULTI_HIRA );
	}

	/**
	 * 渡された文字が全角カタカナかを調べる。
	 * @param c 調べる文字
	 * @return 違う時はfalse
	 */
	protected static boolean isMultiKana( char c )
	{
		return ( START_MULTI_KANA <= c && c <= END_MULTI_KANA );
	}

// 20030111 @ADD yamanaka 機能強化 end

	public static void main( String argv[] ) throws Exception
	{
		out("1234567890");
		out("1234567 890");
		out("1234567.890");
		out("1234.567.890");
		out("1234　　567890");
		out("あいうえお123");
		out("あいうえお");
		out("あいう　えお");
		out("～");
		out("①");
		out("ｱｲｳｴｵ");
	}
	private static void out( String work )
	{
		System.out.println("\nisNumeric(" + work + ") = " + StringCheck.isNumeric(work));
		System.out.println("isSingle (" + work + ") = " + StringCheck.isSingle(work));
		System.out.println("isMulti  (" + work + ") = " + StringCheck.isMulti(work));
	}
}