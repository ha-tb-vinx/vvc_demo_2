package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: NyukaYoteiMakeFgDictionary</p>
 * <p>説明: 店舗納入明細作成フラグのデータディクショナリを定義します</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author otani
 * @version 1.00 2005/06/22 新規作成
 */
public class TenpoNonyuMakeFgDictionary {

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
	private TenpoNonyuMakeFgDictionary(String code, String name) {
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
	public static TenpoNonyuMakeFgDictionary getStatus(String key) {
		TenpoNonyuMakeFgDictionary ret = (TenpoNonyuMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static TenpoNonyuMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKeijyoSumiFgDictionary
	 */
	public static TenpoNonyuMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof TenpoNonyuMakeFgDictionary))
			return false;
		String objStr = ((TenpoNonyuMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static TenpoNonyuMakeFgDictionary NYUKA_YOTEI_MAKE_FG =
		new TenpoNonyuMakeFgDictionary("TENPO_NONYU_MAKE_FG", "店舗納入明細作成フラグ");
	public static TenpoNonyuMakeFgDictionary MISYORI = new TenpoNonyuMakeFgDictionary("0", "未処理");
	public static TenpoNonyuMakeFgDictionary SYORIZUMI = new TenpoNonyuMakeFgDictionary("1", "処理済み");
	public static TenpoNonyuMakeFgDictionary SYORICHU = new TenpoNonyuMakeFgDictionary("9", "処理中");
	public static TenpoNonyuMakeFgDictionary UNKNOWN = new TenpoNonyuMakeFgDictionary("X", "UNKNOWN");

}
