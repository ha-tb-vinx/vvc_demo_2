package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: NohinKeijyoSumiFgDictionary</p>
 * <p>説明: HOST計上済フラグのデータディクショナリを定義します。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author ikemoto
 * @version 1.00 2005/06/16 新規作成
 */
public class NohinKeijyoSumiFgDictionary {

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
	private NohinKeijyoSumiFgDictionary(String code, String name) {
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
	public static NohinKeijyoSumiFgDictionary getStatus(String key) {
		NohinKeijyoSumiFgDictionary ret = (NohinKeijyoSumiFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NohinKeijyoSumiFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NohinKeijyoSumiFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof NohinKeijyoSumiFgDictionary))
			return false;
		String objStr = ((NohinKeijyoSumiFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static NohinKeijyoSumiFgDictionary HOST_KEIJYO_SUMI_FG =
		new NohinKeijyoSumiFgDictionary("HOST_KEIJO_SUMI_FG", "HOST計上済フラグ");
	public static NohinKeijyoSumiFgDictionary HOST_KEIJYO_SUMI = new NohinKeijyoSumiFgDictionary("1", "HOST計上済");
	public static NohinKeijyoSumiFgDictionary HOST_KEIJYO_MI = new NohinKeijyoSumiFgDictionary("0", "HOST未計上");
	public static NohinKeijyoSumiFgDictionary UNKNOWN = new NohinKeijyoSumiFgDictionary("X", "UNKNOWN");

}
