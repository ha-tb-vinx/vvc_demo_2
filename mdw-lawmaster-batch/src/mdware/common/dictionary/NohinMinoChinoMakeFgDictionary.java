package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: NohinMinoChinoMakeFgDictionary</p>
 * <p>説明: 未納遅納情報作成済フラグのデータディクショナリを定義します。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/07/21 新規作成
 */
public class NohinMinoChinoMakeFgDictionary {

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
	private NohinMinoChinoMakeFgDictionary(String code, String name) {
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
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NohinMinoChinoMakeFgDictionary getStatus(String key) {
		NohinMinoChinoMakeFgDictionary ret = (NohinMinoChinoMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NohinMinoChinoMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NohinMinoChinoMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof NohinMinoChinoMakeFgDictionary))
			return false;
		String objStr = ((NohinMinoChinoMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static NohinMinoChinoMakeFgDictionary UNPROCESSING = new NohinMinoChinoMakeFgDictionary("0", "未納遅納情報未処理");
	public static NohinMinoChinoMakeFgDictionary PROCESSING = new NohinMinoChinoMakeFgDictionary("9", "未納遅納情報処理中");
	public static NohinMinoChinoMakeFgDictionary PROCESSED = new NohinMinoChinoMakeFgDictionary("1", "未納遅納情報処理済");
	public static NohinMinoChinoMakeFgDictionary UNKNOWN = new NohinMinoChinoMakeFgDictionary("X", "UNKNOWN");

}
