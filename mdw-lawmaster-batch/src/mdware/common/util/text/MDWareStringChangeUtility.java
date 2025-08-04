package mdware.common.util.text;

import java.util.List;

/**
 * 指定のObjectからStringへ変換するクラスです。 現在はSQLへ使いやすいように変更するメソッドを実装しています。
 * 
 * @author S.Shimatani
 */
public class MDWareStringChangeUtility {

	/**
	 * 中身StringのListを渡すと　,　区切りのStringで返してくれる。
	 * @param stringList 中身StringのLIST
	 * 
	 * @return
	 */
	public static String getCommaPauseString(List stringList) {
		// 引数がnullの場合""を返します。
		if (stringList == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < stringList.size(); i++) {
			sb.append((String) stringList.get(i));
			if ((i + 1) != stringList.size()) {
				sb.append(",");
			}
		}
		return sb.toString();

	}
	/**
	 * 中身StringのListを渡すと　,　区切りのStringで返してくれる。
	 * @param stringList 中身StringのLIST
	 * 
	 * @return
	 */
	public static String getSigleQuoteAndCommaPauseString(List stringList) {
		// 引数がnullの場合""を返します。
		if (stringList == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("'");
		for (int i = 0; i < stringList.size(); i++) {
			sb.append((String) stringList.get(i));
			if ((i + 1) != stringList.size()) {
				sb.append("','");
			}
		}
		sb.append("'");
		
		return sb.toString();

	}
}