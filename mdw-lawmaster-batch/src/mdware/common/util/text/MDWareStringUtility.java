package mdware.common.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RBSITE用ユーティリティー（文字列の加工クラス）。 <BR>
 * J2SDKで用意されたtrimは、通常半角空白と改行、タブなど表示上形に出来ない<BR>
 * 文字をトリムします。<BR>
 * このクラスでは、指定した文字列に含まれる文字を対象とし、左、右、両側からトリム出来ます。<BR>
 * また、指定の長さで切り取りや、指定サイズまで文字で埋めるなどの機能が存在します。<BR>
 * @author telema_yugen777
 */
public class MDWareStringUtility
{
	private StringBuffer sb = null;

	/**
	 * コンストラクタ。
	 * ここで渡された文字列を加工する。
	 * @param baseStr 加工したい文字列
	 */
	public MDWareStringUtility( String baseStr )
	{
		if ( baseStr == null ) baseStr = "";
		sb = new StringBuffer( baseStr );
	}

	/**
	 * 左側から指定された文字(複数種類)を取り除く
	 * @param trimStrs 取り除きたい文字の集まり
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility ltrim( String trimStrs )
	{
		char[] ts = new char[trimStrs.length()];
		for( int i = 0; i < trimStrs.length(); i++ )
		{
			ts[i] = trimStrs.charAt( i );
		}
		while (sb.length() > 0)
		{
			boolean isSame = false;
			char c = sb.charAt( 0 );
			for( int i = 0; i < ts.length && !isSame; i++ )
			{
				isSame = (c == ts[i]);
			}
			if( isSame )
			{
				sb.delete( 0, 1 );
			}
			else
			{
				break;
			}
		}
		return this;
	}

	/**
	 * 右側から指定された文字(複数種類)を取り除く
	 * @param trimStrs 取り除きたい文字の集まり
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility rtrim( String trimStrs )
	{
		char[] ts = new char[trimStrs.length()];
		for( int i = 0; i < trimStrs.length(); i++ )
		{
			ts[i] = trimStrs.charAt( i );
		}
		while (sb.length() > 0)
		{
			boolean isSame = false;
			char c = sb.charAt( sb.length() - 1 );
			for( int i = 0; i < ts.length && !isSame; i++ )
			{
				isSame = (c == ts[i]);
			}
			if( isSame )
			{
				sb.delete( sb.length() - 1, sb.length() );
			}
			else
			{
				break;
			}
		}
		return this;
	}

	/**
	 * 両側から指定された文字(複数種類)を取り除く
	 * @param trimStrs 取り除きたい文字列の集まり
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility trim( String trimStrs )
	{
		return ltrim( trimStrs ).rtrim( trimStrs );
	}

	/**
	 * 指定文字を指定された長さになるように左に埋める。<BR>
	 * 指定の長さ、または指定の長さを超えていた時は何も行わない。<BR>
	 * @param c 埋める文字
	 * @param maxLen 期待する長さ
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility lpad( char c, int maxLen )
	{
		while (sb.length() < maxLen)
		{
			sb.insert( 0, c );
		}
		return this;
	}

	/**
	 * 指定文字を指定された長さになるように右に埋める。<BR>
	 * 指定の長さ、または指定の長さを超えていた時は何も行わない。<BR>
	 * @param c 埋める文字
	 * @param maxLen 期待する長さ
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility rpad( char c, int maxLen )
	{
		while (sb.length() < maxLen)
		{
			sb.append( c );
		}
		return this;
	}

	/**
	 * 指定された長さを超えていたとき、指定された長さになるように左側を削る。
	 * もし、超えていない時は何も行わない。
	 * @param maxLen 期待する長さ
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility lcut( int maxLen )
	{
		if( sb.length() > maxLen )
		{
			int delLen = sb.length() - maxLen;
			sb.delete( 0, delLen );
		}
		return this;
	}

	/**
	 * 指定された長さを超えていたとき、指定された長さになるように右側を削る。
	 * もし、超えていない時は何も行わない。
	 * @param maxLen 期待する長さ
	 * @return 加工された文字列を保持したこのクラス
	 */
	public MDWareStringUtility rcut( int maxLen )
	{
		if( sb.length() > maxLen )
		{
			int delLen = sb.length() - maxLen;
			sb.delete( maxLen, maxLen + delLen );
		}
		return this;
	}

	/**
	 * 加工された文字列を返す。
	 */
	public String toString()
	{
		return sb.toString();
	}
	
	/**
	 * trim関数(全角空白対応)
	 * @author K.Nakazawa From FujiSeisenEDI
	 */
	public MDWareStringUtility ztrim( ) {
		
		Pattern p = Pattern.compile("^[　 \t]+|[　 \t]+$");
		Matcher m = p.matcher( sb.toString() );
		sb = new StringBuffer( m.replaceAll("") );
		
		return this;
	}
}