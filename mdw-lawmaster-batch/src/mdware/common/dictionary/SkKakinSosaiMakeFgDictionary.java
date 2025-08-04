package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: KakinSosaiFileMakeFgDictionary</p>
 * <p>説明: KakinSosaiFileMakeFgDictionary</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author akane.maeno
 * @version 1.00 2005/07/06 新規作成
 */
public class SkKakinSosaiMakeFgDictionary {

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
	private SkKakinSosaiMakeFgDictionary(String code, String name) {
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
	 * @return SkKakinSosaiMakeFgDictionary
	 */
	public static SkKakinSosaiMakeFgDictionary getStatus(String key) {
		SkKakinSosaiMakeFgDictionary ret = (SkKakinSosaiMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SkKakinSosaiMakeFgDictionary
	 */
	public static SkKakinSosaiMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SkKakinSosaiMakeFgDictionary
	 */
	public static SkKakinSosaiMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return 同じかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SkKakinSosaiMakeFgDictionary))
			return false;
		String objStr = ((SkKakinSosaiMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static SkKakinSosaiMakeFgDictionary MISYORI 		= new SkKakinSosaiMakeFgDictionary("0", "未処理");
	public static SkKakinSosaiMakeFgDictionary SYORIZUMI 	= new SkKakinSosaiMakeFgDictionary("1", "処理済み");
	public static SkKakinSosaiMakeFgDictionary SYORICHU 	= new SkKakinSosaiMakeFgDictionary("9", "処理中");
	public static SkKakinSosaiMakeFgDictionary UNKNOWN 		= new SkKakinSosaiMakeFgDictionary("X", "UNKNOWN");
}
