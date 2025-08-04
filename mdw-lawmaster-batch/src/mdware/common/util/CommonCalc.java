/*
 * 作成日: 2004/10/15
 */
package mdware.common.util;

import java.math.BigDecimal;

import mdware.common.bean.SystemConfBean;

/**
 * <P>2006/05/01以降使用禁止(このタイプのクラスはサブシステムごとのUTILとしてリリースすること)</P>
 *  @author タニガワ
 *  @version 1.0
 **/
public class CommonCalc {

	//定数の宣言
	private static final int NEIRE_RT_DECIMAL = 1;	//値入率少数点以下桁数
	private static final int GENTANKA_DECIMAL = 2;	//原単価少数点以下桁数
	private static final int BAITANKA_DECIMAL = 0;	//売単価少数点以下桁数
	private static final int HACHU_SURYO_DECIMAL = 1;//発注数量少数点以下桁数

	/*						*
	 これより、原単価変換処理
	 *						*/

	/**
	 * @param 	BigDecimal 原単価
	 * @return	BigDecimal 原単価(整形後)
	 **/
	public static BigDecimal ModifyGentanka(BigDecimal bdGentanka){

		return bdGentanka.setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN);
	}

//	/**
//	 * @param 	double 原単価
//	 * @return	BigDecimal 原単価(整形後)
//	 **/
//	public static BigDecimal ModifyGentanka(double dGentanka){
//	
//		String strGentanka = new Double(dGentanka).toString();
//
//		return ModifyGentanka(strGentanka);
//	}

	/**
	 * @param 	String 原単価
	 * @return	BigDecimal 原単価(整形後)
	 **/
	public static BigDecimal ModifyGentanka(String strGentanka){
	
		BigDecimal bdGentanka = new BigDecimal(strGentanka).setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN);

		return bdGentanka;
	}


	/*						*
	 これより、原価計算処理 
	 *						*/
	/**
	 * @param BigDecimal  原単価
	 * @param double      発注数量
	 * @return BigDecimal 原価
	 **/	
	public static BigDecimal CalcGenka(final BigDecimal bdGentanka, final double dHachuSuryo){
		
		return CalcGenka(bdGentanka.setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN).toString(), dHachuSuryo);
	}

	/**
	 * @param double      原単価
	 * @param double      発注数量
	 * @return BigDecimal 原価
	 **/	
	public static BigDecimal CalcGenka(final double dGentanka, final double dHachuSuryo){
	
		return CalcGenka(Double.toString(dGentanka), dHachuSuryo);
	}

	/**
	 * @param long        原単価
	 * @param double      発注数量
	 * @return BigDecimal 原価
	 **/	
	public static BigDecimal CalcGenka(final long lGentanka, final double dHachuSuryo){
	
		return CalcGenka(Double.toString(lGentanka), dHachuSuryo);
	}

	/**
	 * @param String      原単価
	 * @param double      発注数量
	 * @return BigDecimal 原価
	 **/	
	public static BigDecimal CalcGenka(final String strGentanka, final double dHachuSuryo){

		//変数の宣言
		String strHachuSuryo = Double.toString(dHachuSuryo);
		BigDecimal bdGentanka  = new BigDecimal(strGentanka);
		BigDecimal bdHachuSuryo= new BigDecimal(strHachuSuryo);
		BigDecimal bdGenka = null;

		//小数点以下の桁数を適切なものに変換
		bdGentanka.setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN);
		bdHachuSuryo.setScale(HACHU_SURYO_DECIMAL, BigDecimal.ROUND_DOWN);

		//原価計算処理をここに追加します
		bdGenka = bdGentanka.multiply(bdHachuSuryo);

		return bdGenka.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	/*						*
	  これより、売価計算処理
	 *						*/
	/**
	 * @param BigDecimal  売単価
	 * @param double      発注数量
	 * @return BigDecimal 売価
	 **/	
	public static long CalcBaika(final BigDecimal bdBaitanka, final double dHachuSuryo){
		
		return CalcBaika(bdBaitanka.setScale(BAITANKA_DECIMAL, BigDecimal.ROUND_DOWN).toString(), dHachuSuryo);
	}

	/**
	 * @param double      売単価
	 * @param double      発注数量
	 * @return BigDecimal 売価
	 **/	
	public static long CalcBaika(final double dBaitanka, final double dHachuSuryo){
	
		return CalcBaika(Double.toString(dBaitanka), dHachuSuryo);
	}

	/**
	 * @param long        売単価
	 * @param double      発注数量
	 * @return BigDecimal 売価
	 **/	
	public static long CalcBaika(final long lBaitanka, final double dHachuSuryo){
	
		return CalcBaika(Long.toString(lBaitanka), dHachuSuryo);
	}

	/**
	 * @param String      売単価
	 * @param double      発注数量
	 * @return BigDecimal 売価
	 **/	
	public static long CalcBaika(final String strBaitanka, final double dHachuSuryo){

		//変数の宣言
		String strHachuSuryo = Double.toString(dHachuSuryo);
		BigDecimal bdBaitanka  = new BigDecimal(strBaitanka);
		BigDecimal bdHachuSuryo= new BigDecimal(strHachuSuryo);
		BigDecimal bdBaika = null;

		//小数点以下の桁数を適切なものに変換
		bdBaitanka.setScale(BAITANKA_DECIMAL, BigDecimal.ROUND_DOWN);
		bdHachuSuryo.setScale(HACHU_SURYO_DECIMAL, BigDecimal.ROUND_DOWN);

		//売価計算処理をここに追加します
		bdBaika = bdBaitanka.multiply(bdHachuSuryo);

		return bdBaika.setScale(0,BigDecimal.ROUND_HALF_UP).longValue();
	}

	/*						*
	  これより、値入率計算処理
	 *						*/

	/**
	 * @param BigDecimal	原単価
	 * @param long			売単価
	 * @return double		値入率
	 **/	
	public static double getNeireRt(final BigDecimal bdGentanka, final long lBaitanka){

		return getNeireRt(bdGentanka.setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN).toString(), lBaitanka);
	}

	/**
	 * @param double	原単価
	 * @param long		売単価
	 * @return double	値入率
	 **/	
	public static double getNeireRt(final double dGentanka, final long lBaitanka){

		return getNeireRt(Double.toString(dGentanka), lBaitanka);
	}

	/**
	 * @param String	原単価
	 * @param long		売単価
	 * @return double	値入率
	 **/	
	public static double getNeireRt(final String strGentanka, final long lBaitanka){

		//変数の宣言
		BigDecimal bdGentanka 		= null;//原単価は、税抜きとみなす
		BigDecimal bdBaitanka 		= null;//売単価(税抜き前)
		BigDecimal bdBaitankaNoTax 	= null;//売単価は、税込みとみなす
		BigDecimal bdNeireRt 	= null;//値入率

		//エラーチェック
		//原単価が0.0で、売単価が1以下のものは、無条件で0を返す
		//ArithmeticException回避のため
		if( (Double.parseDouble(strGentanka) == 0.0) || lBaitanka <= 1 ){
			return 0.0;
		}

		//小数点以下の桁数を適切なものに変換
		bdGentanka = new BigDecimal(strGentanka).setScale(GENTANKA_DECIMAL, BigDecimal.ROUND_DOWN);
		bdBaitanka = new BigDecimal(Long.toString(lBaitanka));

		bdBaitankaNoTax = bdBaitanka.divide( new BigDecimal(Double.toString(1+getTax())), 0, BigDecimal.ROUND_DOWN );

		//売単価の税抜き計算(計算後の小数点以下は切り捨て)
//		dBaitankaNoTax = lBaitanka / (1+getTax());
//		bdBaitankaNoTax = new BigDecimal(new Double(dBaitankaNoTax).toString()).setScale(0, BigDecimal.ROUND_DOWN);

		//値入率計算
		bdNeireRt = bdGentanka.divide(bdBaitankaNoTax,8,BigDecimal.ROUND_DOWN);
		bdNeireRt = new BigDecimal("1").subtract(bdNeireRt);

		bdNeireRt =	bdNeireRt.multiply(new BigDecimal("100"));
		return bdNeireRt.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();

	}


	public static double getTax(){

		//システムパラメータから税率を取得
		SystemConfBeanHolder systemConfBeanHolder = new SystemConfBeanHolder();
		SystemConfBean systemConfBean = systemConfBeanHolder.getSysConfBean();

		//税率を返す
		return systemConfBean.getSyohiZeiRtDouble();
	}

	/**
	 * テスト用メインメソッド 
	 **/
    public static void main(String[] args) {

//		System.out.println(CalcGenka("1.12", 2.2).toString());
//
//		System.out.println(new BigDecimal(1.129).setScale(2, BigDecimal.ROUND_DOWN));
//
//		System.out.println("1.123 * 1.121    ："+CalcGenka(new BigDecimal(1.123).setScale(2, BigDecimal.ROUND_HALF_DOWN), 1.12));
//		System.out.println("1.234 * 1.232    ："+CalcGenka(new BigDecimal(1.234), 1.23));
//		System.out.println("1.345 * 1.343    ："+CalcGenka(new BigDecimal(1.345), 1.34));
//		System.out.println("1.456 * 1.454    ："+CalcGenka(new BigDecimal(1.456), 1.45));
//		System.out.println("1.567 * 1.565    ："+CalcGenka(new BigDecimal(1.567), 1.56));
//
//		System.out.println("23.123 * 1.126   ："+CalcGenka(new BigDecimal(23.123), 1.12));
//		System.out.println("59.456 * 1.127   ："+CalcGenka(new BigDecimal(59.456), 1.12));
//		System.out.println("17.237 * 1.128   ："+CalcGenka(new BigDecimal(17.237), 1.12));
//		System.out.println("31.907 * 1.129   ："+CalcGenka(new BigDecimal(31.907), 1.12));
//		System.out.println("53.903 * 1.120   ："+CalcGenka(new BigDecimal(53.903), 1.12));
//
//		System.out.println("900.343 * 1.23 ："+CalcGenka(new BigDecimal(900.343), 1.23));
//		System.out.println("777.34 * 1.34   ："+CalcGenka(new BigDecimal(777.34), 1.34));
//		System.out.println("583.2354 * 1.45 ："+CalcGenka(new BigDecimal(583.2354), 1.45));
//		System.out.println("309.233 * 1.56  ："+CalcGenka(new BigDecimal(309.233), 1.56));
//		System.out.println("645.233 * 1.56  ："+CalcGenka(new BigDecimal(645.233), 1.56));
//
//		System.out.println("1234.567 * 1.12 ："+CalcGenka(new BigDecimal(1234.567), 1.12));
//		System.out.println("2345.343 * 1.23 ："+CalcGenka(new BigDecimal(2345.343), 1.23));
//		System.out.println("4732.34 * 1.34  ："+CalcGenka(new BigDecimal(4732.34), 1.34));
//		System.out.println("9127.2354 * 1.45："+CalcGenka(new BigDecimal(9127.2354), 1.45));
//		System.out.println("7348.233 * 1.56 ："+CalcGenka(new BigDecimal("7348.233"), 1.56));
//
//
//		System.out.println("1.123 * 1.121    ："+CalcBaika(new BigDecimal(1.123).setScale(2, BigDecimal.ROUND_HALF_DOWN), 1.12));
//		System.out.println("1.234 * 1.232    ："+CalcBaika(new BigDecimal(1.234), 1.23));
//		System.out.println("1.345 * 1.343    ："+CalcBaika(new BigDecimal(1.345), 1.34));
//		System.out.println("1.456 * 1.454    ："+CalcBaika(new BigDecimal(1.456), 1.45));
//		System.out.println("1.567 * 1.565    ："+CalcBaika(new BigDecimal(1.567), 1.56));
//
//		System.out.println("23.123 * 1.126   ："+CalcBaika(new BigDecimal(23.123), 1.12));
//		System.out.println("59.456 * 1.127   ："+CalcBaika(new BigDecimal(59.456), 1.12));
//		System.out.println("17.237 * 1.128   ："+CalcBaika(new BigDecimal(17.237), 1.12));
//		System.out.println("31.907 * 1.129   ："+CalcBaika(new BigDecimal(31.907), 1.12));
//		System.out.println("53.903 * 1.120   ："+CalcBaika(new BigDecimal(53.903), 1.12));
//
//		System.out.println("900.343 * 1.123 ："+CalcBaika(new BigDecimal(900.343), 1.23));
//		System.out.println("777.34 * 1.34   ："+CalcBaika(new BigDecimal("777.34"), 1.34));
//		System.out.println("583.2354 * 1.45 ："+CalcBaika(new BigDecimal(583.2354), 1.45));
//		System.out.println("309.233 * 1.56  ："+CalcBaika(new BigDecimal(309.233), 1.56));
//		System.out.println("645.233 * 1.56  ："+CalcBaika(new BigDecimal(645.233), 1.56));
//
//		System.out.println("1234.567 * 1.12 ："+CalcBaika(new BigDecimal(1234.567), 1.12));
//		System.out.println("2345.343 * 1.23 ："+CalcBaika(new BigDecimal(2345.343), 1.23));
//		System.out.println("4732.34 * 1.34  ："+CalcBaika(new BigDecimal(4732.34), 1.34));
//		System.out.println("9127.2354 * 1.45："+CalcBaika(new BigDecimal(9127.2354), 1.45));
//		System.out.println("7348.233 * 1.56 ："+CalcBaika(new BigDecimal("7348.233"), 1.56));
//
//
//		long lBaitanka = 900;
//		double dGentanka = 200.2222;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 345.345;
//		lBaitanka = 678;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 123.3456;
//		lBaitanka = 456;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 798.123;
//		lBaitanka = 987;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 76574.1234;
//		lBaitanka = 91238;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 12321.12312;
//		lBaitanka = 23432;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 7987.1231;
//		lBaitanka = 43534;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 543.739;
//		lBaitanka = 739;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 20432.2222;
//		lBaitanka = 94340;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 4321.2222;
//		lBaitanka = 7476;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 2341.2222;
//		lBaitanka = 6543;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 5436.653;
//		lBaitanka = 5678;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 9285.2954;
//		lBaitanka = 10932;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");
//
//		dGentanka = 9385.2738;
//		lBaitanka = 14832;
//		System.out.println("原単価："+dGentanka+"\n売単価："+lBaitanka+"\n"+getNeireRt(dGentanka, lBaitanka)+"\n");

		System.out.println("値入率チェック："+getNeireRt("0.55", 1));

		System.exit(1);
    }
}
