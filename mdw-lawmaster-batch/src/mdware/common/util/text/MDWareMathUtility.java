package mdware.common.util.text;

import jp.co.vinculumjapan.stc.util.text.CurrencyUtility;

/**
 * RBSITE用ユーティリティー（数値の加工クラス）。 <BR>
 * double型の値を切り捨てや、四捨五入を行う。<BR>
 * @author telema_yugen777
 */
public class MDWareMathUtility
{
	private MDWareMathUtility()
	{

	}

	/**
	 * 小数点以下の指定箇所で切り捨てを行う。<BR>
	 * 位置が１より小さい時は、小数点第一位を切り捨てる。<BR>
	 * floor( 1.234, -1 )→1.0<BR>
	 * floor( 1.234, 0 )→1.0<BR>
	 * floor( 1.234, 1 )→1.0<BR>
	 * floor( 1.234, 2 )→1.2<BR>
	 * @param d 切捨てされる値
	 * @param n 切捨てする位置
	 * @return 切捨てした結果
	 */
	public static double floor( double d, int n )
	{
		n--;
		if( n < 0 )
		{
			n = 0;
		}
		return Math.floor( Math.pow( 10, (double) n ) * d ) / Math.pow( 10, (double) n );
	}

	/**
	 * 小数点以下の指定箇所で四捨五入を行う。<BR>
	 * 位置が１より小さい時は、小数点第一位を四捨五入する。<BR>
	 * round( 1.555, -1 )→2.0<BR>
	 * round( 1.555, 0 )→2.0<BR>
	 * round( 1.555, 1 )→2.0<BR>
	 * round( 1.555, 2 )→1.6<BR>
	 * @param d 四捨五入される値
	 * @param n 四捨五入する位置
	 * @return 四捨五入した結果
	 */
	public static double round( double d, int n )
	{
		n--;
		if( n < 0 )
		{
			n = 0;
		}
		StringBuffer format = new StringBuffer( "############" );
		if( n > 0 )
		{
			format.append( "." );
			for( int i = 0; i < n; i++ )
			{
				format.append( "#" );
			}
		}
		return Double.parseDouble( CurrencyUtility.format( format.toString(), d ) );
	}
}