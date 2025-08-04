package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: CommonMakeFgDictionary</p>
 * <p>説明: 作成済フラグのデータディクショナリを定義します。</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Okuno
 * @version 1.00 2005/09/29 H.Okuno	新規作成
 */
public class CommonMakeFgDictionary {

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
	private CommonMakeFgDictionary(String code, String name) {
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
	 * @return CommonMakeFgDictionary
	 */
	public static CommonMakeFgDictionary getStatus(String key) {
		CommonMakeFgDictionary ret = (CommonMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return CommonMakeFgDictionary
	 */
	public static CommonMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return CommonMakeFgDictionary
	 */
	public static CommonMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof CommonMakeFgDictionary))
			return false;
		String objStr = ((CommonMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static CommonMakeFgDictionary UNPROCESSING = new CommonMakeFgDictionary("0", "未処理");
	public static CommonMakeFgDictionary PROCESSING = new CommonMakeFgDictionary("9", "処理中");
	public static CommonMakeFgDictionary PROCESSED = new CommonMakeFgDictionary("1", "処理済");
	public static CommonMakeFgDictionary UNKNOWN = new CommonMakeFgDictionary("X", "UNKNOWN");

}
