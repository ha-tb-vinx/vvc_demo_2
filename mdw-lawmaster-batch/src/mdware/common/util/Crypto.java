package mdware.common.util;

import java.io.*;
import java.util.*;

/**
 *
 * <p>タイトル: 独自ロジックによる、文字列の暗号化・複合化CLASS。2006/05/01以降使用禁止</p>
 * <p>説明: 独自ロジックにより、文字列の暗号化と複合化を行ないます<BR>
 * ※元の文字列を平文と呼び、暗号化された文字列を暗号文と呼びます<BR>
 * 平文から暗号文に変換することを「暗号化（エンコード）」と呼びます。<BR>
 * その逆に、暗号文を平文に変換することを「復号化（デコード）」と呼びます。<BR>
 * 2バイトコードには非対応です
 * </p>
 * @version 1.0
 */
public class Crypto
{
	/**
	 * インスタンス化は不要<BR>
	 * encode( String org );又は<BR>
	 * decode( String crypto );を使用する<BR>
	 */
	public Crypto()
	{
	}

	/**
	 * 暗号化<BR>
	 * 平文の文字列長が０バイトならば、""を返します
	 * @param org 平文の文字列
	 * @return 暗号化された文字列を返します
	 */
	public static String encode( String org )
	{
		String ret_buff = "";
		int iloop = 0;
		int ch = 0;
		Integer itmp = new Integer(0);
		while( iloop < org.length() )
		{
			ch = (int)org.charAt(iloop);
			ch = ~ch;
			ret_buff += itmp.toHexString( (int)( ch & 0x0F ) );
			ret_buff += itmp.toHexString( (int)( ch & 0xF0 ) / 0x10 );
			iloop++;
		}
		return( ret_buff );
	}


	/**
	 * 複号化<BR>
	 * 暗号文の文字列長が０バイトならば、""を返します
	 * @param crypto 暗号文の文字列
	 * @return 複号化された文字列を返します
	 */
	public static String decode( String crypto )
	{
		String stmp = "";
		int iloop = 0;
		int ch;
		Integer itmp = new Integer(0);
		byte bstr[] = new byte[ crypto.length()/2 ];
		while( iloop < crypto.length() )
		{
			stmp = crypto.substring( iloop, iloop+2 );
			if ( 'a' > stmp.charAt(0) ){
				ch = (stmp.charAt(0) - '0');
			} else {
				ch = (stmp.charAt(0) - 'a' + 10);
			}
			if ( 'a' > stmp.charAt(1) ){
				ch += (stmp.charAt(1) - '0') * 0x10;
			} else {
				ch += (stmp.charAt(1) - 'a' + 10) * 0x10;
			}
			bstr[iloop/2] = (byte)(~ch);
			iloop++;
			iloop++;
		}
		String ret_buff = "";
		try{
			String tbuff = new String( bstr,"JISAutoDetect" );
			ret_buff = tbuff;
		}
		catch (Exception e){}

		return( ret_buff );
	}

	public static void main(String[] args) {
		System.out.println(Crypto.encode("00000000"));
		System.out.println(Crypto.encode("00000001"));
		System.out.println(Crypto.encode("00000031"));
		System.out.println(Crypto.encode("00254789"));
		System.out.println(Crypto.encode("00254790"));
	}
}