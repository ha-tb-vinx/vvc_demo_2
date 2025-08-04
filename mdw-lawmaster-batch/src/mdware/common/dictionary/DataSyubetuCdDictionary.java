package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: データ種別コード Dictionary</p>
 * <p>説明: データ種別(センターフィー)ディクショナリクラス</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/06/23 S.Kitamura 新規作成
 * @version 1.01 2005/07/06 S.kitamura 障害報告No.737対応
 */
public class DataSyubetuCdDictionary {

	/**
	 * ＤＢ内のコード
	 */
	private String code = null;
	/**
	 * ＤＢ内コードの意味
	 */
	private String name = null;
	/**
	 * 対応したクラスを保持するマップ
	 */
	private static final Map map = new HashMap();

	/**
	 * コンストラクタ
	 * @param code ＤＢ内のコード
	 * @param name ＤＢ内コードの意味
	 */
	private DataSyubetuCdDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code, this);
	}

	/**
	 * ＤＢ内コードの意味を返す
	 * @return String
	 */
	public String toString() {
		return name;
	}

	/**
	 * ＤＢ内のコードを返す
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static DataSyubetuCdDictionary getStatus(String key) {
		DataSyubetuCdDictionary ret = (DataSyubetuCdDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static DataSyubetuCdDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static DataSyubetuCdDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof DataSyubetuCdDictionary))
			return false;
		String objStr = ((DataSyubetuCdDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static DataSyubetuCdDictionary TABLE_TMP_CENTER_FEE =
		new DataSyubetuCdDictionary("BR_TMP_CENTER_FEE", "センターフィーTMPテーブル");

	public static DataSyubetuCdDictionary DATAKIND_CD_A3 = new DataSyubetuCdDictionary("A3", "センター利用料");
	public static DataSyubetuCdDictionary DATAKIND_CD_A2 = new DataSyubetuCdDictionary("A2", "開梱");
	public static DataSyubetuCdDictionary DATAKIND_CD_C1 = new DataSyubetuCdDictionary("C1", "返品");
	public static DataSyubetuCdDictionary DATAKIND_CD_A1 = new DataSyubetuCdDictionary("A1", "集荷");
	public static DataSyubetuCdDictionary DATAKIND_CD_B1 = new DataSyubetuCdDictionary("B1", "移動");
	public static DataSyubetuCdDictionary DATAKIND_CD_X1 = new DataSyubetuCdDictionary("X1", "道外物流集荷");

	public static DataSyubetuCdDictionary SOSAI_CD_A3 = new DataSyubetuCdDictionary("0871", "センター利用料");
	public static DataSyubetuCdDictionary SOSAI_CD_A2 = new DataSyubetuCdDictionary("0872", "開梱手数料");
	public static DataSyubetuCdDictionary SOSAI_CD_C1 = new DataSyubetuCdDictionary("0873", "返品処理料");
	public static DataSyubetuCdDictionary SOSAI_CD_A1 = new DataSyubetuCdDictionary("0874", "集荷料");
	//	2005/07/06 S.Kitamura　障害報告書No.737 START
	//	public static CenterFeeDataKindDictionary SOSAI_CD_B1 = new CenterFeeDataKindDictionary("9999", "移動料");
	//	2005/07/06 S.Kitamura　障害報告書No.737 END
	public static DataSyubetuCdDictionary SOSAI_CD_X1 = new DataSyubetuCdDictionary("0874", "道外物流集荷");

	// 2005/07/06 S.Kitamura　障害報告書No.737 START
	//	public static CenterFeeDataKindDictionary URIKAKE_DATA_KB_A3 = new CenterFeeDataKindDictionary("AA", "センター利用料");
	//	public static CenterFeeDataKindDictionary URIKAKE_DATA_KB_A2 = new CenterFeeDataKindDictionary("BB", "開梱");
	//	public static CenterFeeDataKindDictionary URIKAKE_DATA_KB_C1 = new CenterFeeDataKindDictionary("CC", "返品");
	//	public static CenterFeeDataKindDictionary URIKAKE_DATA_KB_A1 = new CenterFeeDataKindDictionary("DD", "集荷");
	//	public static CenterFeeDataKindDictionary URIKAKE_DATA_KB_X1 = new CenterFeeDataKindDictionary("EE", "道外物流集荷");
	//	2005/07/06 S.Kitamura　障害報告書No.737 END

	public static DataSyubetuCdDictionary UNKNOWN = new DataSyubetuCdDictionary("X", "UNKNOWN");
}
