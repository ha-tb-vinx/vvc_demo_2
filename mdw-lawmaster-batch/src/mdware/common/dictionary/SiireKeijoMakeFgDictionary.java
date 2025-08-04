package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: SiireKeijoMakeFgDictionary</p>
 * <p>説明: SiireKeijoMakeFgDictionary</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Y.Teraoka
 * @version 1.00 2005/07/06 障害報告書No.732
 */
public class SiireKeijoMakeFgDictionary {

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
	private SiireKeijoMakeFgDictionary(String code, String name) {
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
	 * @return SiireKeijoMakeFgDictionary
	 */
	public static SiireKeijoMakeFgDictionary getStatus(String key) {
		SiireKeijoMakeFgDictionary ret = (SiireKeijoMakeFgDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SiireKeijoMakeFgDictionary
	 */
	public static SiireKeijoMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SiireKeijoMakeFgDictionary
	 */
	public static SiireKeijoMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SiireKeijoMakeFgDictionary))
			return false;
		String objStr = ((SiireKeijoMakeFgDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static SiireKeijoMakeFgDictionary MISYORI = new SiireKeijoMakeFgDictionary("0", "未処理");
	public static SiireKeijoMakeFgDictionary SYORIZUMI = new SiireKeijoMakeFgDictionary("1", "処理済み");
	public static SiireKeijoMakeFgDictionary SYORICHU = new SiireKeijoMakeFgDictionary("9", "処理中");
	public static SiireKeijoMakeFgDictionary UNKNOWN = new SiireKeijoMakeFgDictionary("X", "UNKNOWN");
}
