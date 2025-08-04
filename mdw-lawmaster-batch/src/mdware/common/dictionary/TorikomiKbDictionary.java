package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: TorikomiKbDictionary</p>
 * <p>説明: TorikomiKbDictionary</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 李武勇
 * @version 1.00 2005/06/16 新規作成
 */
public class TorikomiKbDictionary {

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
	private TorikomiKbDictionary(String code, String name) {
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
	 * @return TorikomiKbDictionary
	 */
	public static TorikomiKbDictionary getStatus(String key) {
		TorikomiKbDictionary ret = (TorikomiKbDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorikomiKbDictionary
	 */
	public static TorikomiKbDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorikomiKbDictionary
	 */
	public static TorikomiKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof TorikomiKbDictionary))
			return false;
		String objStr = ((TorikomiKbDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static TorikomiKbDictionary TORIKOMI_KB = new TorikomiKbDictionary("TORIKOMI_KB", "取込区分");
	public static TorikomiKbDictionary TORIKOMI_KB_WEB = new TorikomiKbDictionary("1", "WEB経由（ファイルアップロード）");
	public static TorikomiKbDictionary TORIKOMI_KB_HOST = new TorikomiKbDictionary("2", "HOST経由");
	public static TorikomiKbDictionary UNKNOWN = new TorikomiKbDictionary("X", "UNKNOWN");

}
