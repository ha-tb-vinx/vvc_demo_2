package mdware.master.util;

import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: MakerCodeクラス</p>
 * <p>説明: メーカーコードに関する共通処理を記述します。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author t.takayama
 * @version 1.0
 */

public class MakerCode {

	/**
	 * ＪＡＮコードよりＪＡＮメーカーコードを取得する<br>
	 * 
	 * @param syohin2Cd ＪＡＮコード
	 * @return ＪＡＮメーカーコード
	 */
	public static String getMakerCd(String syohin2Cd) {
		//ＪＡＮコードが短縮８桁の場合
		if (syohin2Cd.length() == 8) {
			return syohin2Cd.substring(0, 6);	//先頭６桁をＪＡＮメーカーコード
		}
		
		// ＪＡＮコードが１３桁で
		if (syohin2Cd.length() == 13) {
			//ＪＡＮコードが"456000001"～"45999999"の場合
			if (syohin2Cd.substring(0, 3).compareTo(mst000101_ConstDictionary.JAN_MAKER_9_ST) >= 0
			 && syohin2Cd.substring(0, 3).compareTo(mst000101_ConstDictionary.JAN_MAKER_9_ED) <= 0) { 
				return syohin2Cd.substring(0, 9);	//先頭９桁をＪＡＮメーカーコード
			}
			//ＪＡＮコードが"4500001"～"4559999"の場合
			if (syohin2Cd.substring(0, 3).compareTo(mst000101_ConstDictionary.JAN_MAKER_7_ST) >= 0
			 && syohin2Cd.substring(0, 3).compareTo(mst000101_ConstDictionary.JAN_MAKER_7_ED) <= 0) { 
				return syohin2Cd.substring(0, 7);	//先頭７桁をＪＡＮメーカーコード
			}
			//ＪＡＮコードが"4900001"～"4999999"の場合
			if (syohin2Cd.substring(0, 2).equals(mst000101_ConstDictionary.JAN_MAKER_49)) {
				return syohin2Cd.substring(0, 7);	//先頭７桁をＪＡＮメーカーコード
			}
		}
		return "";
	}

}
