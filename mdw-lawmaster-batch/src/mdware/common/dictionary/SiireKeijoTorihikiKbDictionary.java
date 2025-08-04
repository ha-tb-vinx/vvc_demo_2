package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: SiireKeijoTorihikiKbDictionary</p>
 * <p>説明: SiireKeijoTorihikiKbDictionary</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Y.Teraoka
 * @version 1.00 2005/07/06 障害報告書No.732
 */
public class SiireKeijoTorihikiKbDictionary {

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
	private SiireKeijoTorihikiKbDictionary(String code, String name) {
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
	 * @return SiireKeijoTorihikiKbDictionary
	 */
	public static SiireKeijoTorihikiKbDictionary getStatus(String key) {
		SiireKeijoTorihikiKbDictionary ret = (SiireKeijoTorihikiKbDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SiireKeijoTorihikiKbDictionary
	 */
	public static SiireKeijoTorihikiKbDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SiireKeijoTorihikiKbDictionary
	 */
	public static SiireKeijoTorihikiKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SiireKeijoTorihikiKbDictionary))
			return false;
		String objStr = ((SiireKeijoTorihikiKbDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static SiireKeijoTorihikiKbDictionary IPPAN = new SiireKeijoTorihikiKbDictionary("0112", "一般仕入れ");
	public static SiireKeijoTorihikiKbDictionary TORIKESI = new SiireKeijoTorihikiKbDictionary("0118", "取り消し");
	public static SiireKeijoTorihikiKbDictionary UNKNOWN = new SiireKeijoTorihikiKbDictionary("X", "UNKNOWN");
}
