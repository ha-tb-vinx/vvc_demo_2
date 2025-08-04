package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 売掛データ種類 Dictionary</p>
 * <p>説明:  売掛データ種類ディクショナリクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hirai
 * @version 1.00 2005/07/14 hirai 新規作成
 */
public class UrikakeDataKbDictionary {

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
	private UrikakeDataKbDictionary(String code, String name) {
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
	public static UrikakeDataKbDictionary getStatus(String key) {
		UrikakeDataKbDictionary ret = (UrikakeDataKbDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static UrikakeDataKbDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static UrikakeDataKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof UrikakeDataKbDictionary))
			return false;
		String objStr = ((UrikakeDataKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}
	public static UrikakeDataKbDictionary EOS_DENP_FEE = new UrikakeDataKbDictionary("31", "EOS伝票代");
	public static UrikakeDataKbDictionary ONLINE_HACHU_FEE = new UrikakeDataKbDictionary("32", "オンライン発注料");
	public static UrikakeDataKbDictionary WEEKLY_ORDER_PLAN = new UrikakeDataKbDictionary("33", "生鮮週間予定");
	public static UrikakeDataKbDictionary UNKNOWN = new UrikakeDataKbDictionary("X", "UNKNOWN");
}
