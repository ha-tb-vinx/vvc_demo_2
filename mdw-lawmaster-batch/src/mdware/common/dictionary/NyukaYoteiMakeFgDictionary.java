package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: NyukaYoteiMakeFgDictionary</p>
 * <p>説明: 入荷予定作成フラグのデータディクショナリを定義します</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author ikemoto
 * @version 1.00 2005/06/20 新規作成
 */
public class NyukaYoteiMakeFgDictionary {

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
	private NyukaYoteiMakeFgDictionary(String code, String name) {
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
	public static NyukaYoteiMakeFgDictionary getStatus(String key) {
		NyukaYoteiMakeFgDictionary ret = (NyukaYoteiMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NyukaYoteiMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static NyukaYoteiMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof NyukaYoteiMakeFgDictionary))
			return false;
		String objStr = ((NyukaYoteiMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static NyukaYoteiMakeFgDictionary NYUKA_YOTEI_MAKE_FG =
		new NyukaYoteiMakeFgDictionary("NYUKA_YOTEI_MAKE_FG", "入荷予定作成フラグ");
	public static NyukaYoteiMakeFgDictionary MISYORI = new NyukaYoteiMakeFgDictionary("0", "未処理");
	public static NyukaYoteiMakeFgDictionary SYORIZUMI = new NyukaYoteiMakeFgDictionary("1", "処理済み");
	public static NyukaYoteiMakeFgDictionary SYORICHU = new NyukaYoteiMakeFgDictionary("9", "処理中");
	public static NyukaYoteiMakeFgDictionary UNKNOWN = new NyukaYoteiMakeFgDictionary("X", "UNKNOWN");

}
