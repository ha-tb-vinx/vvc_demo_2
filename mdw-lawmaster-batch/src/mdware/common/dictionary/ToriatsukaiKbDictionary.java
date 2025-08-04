package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: ToriatsukaiKbDictionary</p>
 * <p>説明: ToriatsukaiKbDictionary</p>
 * <p>著作権: Copyright (c) 2010</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Y.IMAI
 * @version 1.00 2010/08/24 新規作成
 */
public class ToriatsukaiKbDictionary {

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
	private ToriatsukaiKbDictionary(String code, String name) {
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
	 * @return ToriatsukaiKbDictionary
	 */
	public static ToriatsukaiKbDictionary getStatus(String key) {
		ToriatsukaiKbDictionary ret = (ToriatsukaiKbDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ToriatsukaiKbDictionary
	 */
	public static ToriatsukaiKbDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ToriatsukaiKbDictionary
	 */
	public static ToriatsukaiKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof ToriatsukaiKbDictionary))
			return false;
		String objStr = ((ToriatsukaiKbDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static ToriatsukaiKbDictionary TORIATSUKAI_NASHI = new ToriatsukaiKbDictionary("0", "取扱無");
	public static ToriatsukaiKbDictionary TORIATSUKAI_ARI = new ToriatsukaiKbDictionary("1", "取扱有");
	public static ToriatsukaiKbDictionary UNKNOWN = new ToriatsukaiKbDictionary("X", "UNKNOWN");

}

