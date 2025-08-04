package mdware.common.util;

import mdware.common.dictionary.*;

/**
 * <p>タイトル: SyohinCodeConverter </p>
 * <p>説明: 伝票出力用の商品コード、品番コードに変換する。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author takata
 * @version 1.00
 * @version 1.01 2005/02/03 takata 障害報告書No.541への対応
 */
public class SyohinCodeConverter {
	
	/**
	 * <P>伝票に出力する商品コードに変換する</P>
	 * <P>食品か非食品の判断は大業種コードで行います。</P>
	 * @param l_gyosyu_cd 大業種コード
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param pos_cd　ＰＯＳコード
	 * @return 伝票に出力する商品コード
	 */
	public static synchronized String toDenpyoSyohinCode(String l_gyosyu_cd, String denpyo_hinban_kb, String jisya_syohin_cd, String pos_cd) {
		if (l_gyosyu_cd.equals(LGyosyuDictionary.SYOKUHIN.getCode())) {
			return toDenpyoSyohinCodeForFood(denpyo_hinban_kb, jisya_syohin_cd, pos_cd);
		} else {
			return toDenpyoSyohinCodeForNonFood(denpyo_hinban_kb, jisya_syohin_cd, pos_cd);
		}
		
	}
	
	/**
	 * <P>伝票に出力する品番コードに変換する</P>
	 * <P>食品か非食品の判断は大業種コードで行います。</P>
	 * @param l_gyosyu_cd 大業種コード
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param torihikisaki_syohin_cd 取引先商品コード
	 * @param pos_cd　ＰＯＳコード
	 * @return 伝票に出力する品番コード
	 */
	public static synchronized String toDenpyoHinbanCode(String l_gyosyu_cd, String denpyo_hinban_kb, String jisya_syohin_cd, String torihikisaki_syohin_cd, String pos_cd) {
		if (l_gyosyu_cd.equals(LGyosyuDictionary.SYOKUHIN.getCode())) {
			return toDenpyoHinbanCodeForFood(denpyo_hinban_kb, jisya_syohin_cd, torihikisaki_syohin_cd, pos_cd);
		} else {
			return toDenpyoHinbanCodeForNonFood(denpyo_hinban_kb, jisya_syohin_cd, torihikisaki_syohin_cd, pos_cd);
		}
	}

	/**
	 * <P>食品の伝票に出力する商品コードに変換する</P>
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param pos_cd　ＰＯＳコード
	 * @return 食品の伝票に出力する商品コード
	 */
	public static synchronized String toDenpyoSyohinCodeForFood(String denpyo_hinban_kb, String jisya_syohin_cd, String pos_cd) {
		String denpyo_syohin_cd = null;
		
// 2005/02/03 add takata begin
		if (jisya_syohin_cd == null)
			jisya_syohin_cd = "";
			
		if (pos_cd == null)
			pos_cd = "";
// 2005/02/03 add takata end

		// 伝票商品コードを判定
		if (denpyo_hinban_kb.equals("3") || denpyo_hinban_kb.equals("2")) {
			denpyo_syohin_cd = pos_cd;
		} else {
			denpyo_syohin_cd = jisya_syohin_cd.length() > 4 ? jisya_syohin_cd.substring(0, 4) : jisya_syohin_cd;
		}

		return denpyo_syohin_cd;
	}
	
	/**
	 * <P>食品の伝票に出力する品番コードに変換する</P>
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param torihikisaki_syohin_cd 取引先商品コード
	 * @param pos_cd　ＰＯＳコード
	 * @return 食品の伝票に出力する品番コード
	 */
	public static synchronized String toDenpyoHinbanCodeForFood(String denpyo_hinban_kb, String jisya_syohin_cd, String torihikisaki_syohin_cd, String pos_cd) {
		String denpyo_hinban_cd = null;
		
// 2005/02/03 add takata begin
		if (jisya_syohin_cd == null)
			jisya_syohin_cd = "";
			
		if (torihikisaki_syohin_cd == null)
			torihikisaki_syohin_cd = "";
		
		if (pos_cd == null)
			pos_cd = "";
// 2005/02/03 add takata end
		
		// 伝票品番コードを判定
		if (denpyo_hinban_kb.equals("3")) {
			denpyo_hinban_cd = jisya_syohin_cd.length() > 4 ? jisya_syohin_cd.substring(0, 4) : jisya_syohin_cd;
		} else {
			if (torihikisaki_syohin_cd.trim().length() > 0) {
				denpyo_hinban_cd = torihikisaki_syohin_cd;
			} else {
				if (pos_cd.startsWith("02") || pos_cd.startsWith("04") || pos_cd.startsWith("21") || pos_cd.trim().length() == 0) {
					denpyo_hinban_cd = "";
				} else {
					denpyo_hinban_cd = pos_cd;
				}
			}
		}
		
		return denpyo_hinban_cd;
	}

	/**
	 * <P>非食品の伝票に出力する商品コードに変換する</P>
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param pos_cd　ＰＯＳコード
	 * @return 非食品の伝票に出力する商品コード
	 */
	public static synchronized String toDenpyoSyohinCodeForNonFood(String denpyo_hinban_kb, String jisya_syohin_cd, String pos_cd) {
		String denpyo_syohin_cd = null;
		
// 2005/02/03 add takata begin
		if (jisya_syohin_cd == null)
			jisya_syohin_cd = "";
			
		if (pos_cd == null)
			pos_cd = "";
// 2005/02/03 add takata end

		// 伝票商品コードを判定
		if (denpyo_hinban_kb.equals("2")) {
			if (jisya_syohin_cd.startsWith("0400")) {
				denpyo_syohin_cd = jisya_syohin_cd;
			} else {
				denpyo_syohin_cd = pos_cd;
			}
			
		} else if (denpyo_hinban_kb.equals("3")) {
			denpyo_syohin_cd = pos_cd;
		} else {
			denpyo_syohin_cd = jisya_syohin_cd;
		}
		
		return denpyo_syohin_cd;
	}
	
	/**
	 * <P>非食品の伝票に出力する品番コードに変換する</P>
	 * @param denpyo_hinban_kb 取引先マスタの伝票品番区分
	 * @param jisya_syohin_cd 自社商品コード 
	 * @param torihikisaki_syohin_cd 取引先商品コード
	 * @param pos_cd　ＰＯＳコード
	 * @return 非食品の伝票に出力する品番コード
	 */
	public static synchronized String toDenpyoHinbanCodeForNonFood(String denpyo_hinban_kb, String jisya_syohin_cd, String torihikisaki_syohin_cd, String pos_cd) {
		String denpyo_hinban_cd = null;
		
// 2005/02/03 add takata begin
		if (jisya_syohin_cd == null)
			jisya_syohin_cd = "";
			
		if (torihikisaki_syohin_cd == null)
			torihikisaki_syohin_cd = "";
		
		if (pos_cd == null)
			pos_cd = "";
// 2005/02/03 add takata end
		
		// 伝票品番コードを判定
		if (denpyo_hinban_kb.equals("3")) {
			denpyo_hinban_cd = jisya_syohin_cd.length() > 4 ? jisya_syohin_cd.substring(0, 4) : jisya_syohin_cd;
		} else {
			if (jisya_syohin_cd.startsWith("0400")) {
				if (torihikisaki_syohin_cd.trim().length() > 0) {
					denpyo_hinban_cd = pos_cd;
				} else {
					denpyo_hinban_cd = "";
				}
			} else if (jisya_syohin_cd.startsWith("02") || jisya_syohin_cd.startsWith("04") || jisya_syohin_cd.startsWith("21") || jisya_syohin_cd.trim().length() == 0) {
				denpyo_hinban_cd = "";
			} else {
				if (torihikisaki_syohin_cd.trim().length() > 0) {
					denpyo_hinban_cd = torihikisaki_syohin_cd;
				} else {
					denpyo_hinban_cd = pos_cd;
				}
			}
		}
		
		return denpyo_hinban_cd;
	}

	public static void main(String[] args) {
			
		System.out.println("denpyo_syohin_cd = " + toDenpyoSyohinCode("0033", "2", "9307", "0400930708029"));
		System.out.println("denpyo_hinban_cd = " + toDenpyoHinbanCode("0033", "2", "", "sdfase", "0400930708029"));
		
	}

}