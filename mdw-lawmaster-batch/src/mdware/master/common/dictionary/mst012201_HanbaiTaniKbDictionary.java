package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:販売単位区分Dictionary </p>
 * <p>説明: 販売単位区分を保持する。</p>
 * <p>著作権: Copyright  (C) 2013</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 3.00 (2013.05.12)    M.Ayukawa [MSTC00013] ランドローム様  商品マスタ初期値設定対応
 * @Version 3.01 (2016.09.29)    M.Akagi #2145 FIVImart対応
 */
public class mst012201_HanbaiTaniKbDictionary {

	/**
	 * DB内のコード
	 */
	private String code = null;
	/**
	 * DB内コードの意味
	 */
	private String name = null;
	/**
	 * 対応したクラスを保持するマップ
	 */
	private static final Map map = new HashMap();

	/**
	 * コンストラクタ
	 * @param code DB内のコード
	 * @param name DB内コードの意味
	 */
	private mst012201_HanbaiTaniKbDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code,this);
	}

	/**
	 * DB内コードの意味を返す
	 * @return String
	 */
	public String toString() {
		return name;
	}

	/**
	 * DB内のコードを返す
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * コードで検索を行う
	 * @param String DB内のコード(key)
	 * @return mst010101_SyoriSyubetsuKbDictionary
	 */
	public static mst012201_HanbaiTaniKbDictionary getStatus(String key) {
		mst012201_HanbaiTaniKbDictionary ret = (mst012201_HanbaiTaniKbDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;

	}

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst010101_SyoriSyubetsuKbDictionary
	 */
	public static mst012201_HanbaiTaniKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst012201_HanbaiTaniKbDictionary)) {
			return false;
		}
		String objStr = ((mst012201_HanbaiTaniKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	// 初期値指定として必要な値のみ宣言
	// 2016.09.29 M.Akagi #2145 (S)
	//public static mst012201_HanbaiTaniKbDictionary KO	 	= new mst012201_HanbaiTaniKbDictionary("03", "個");
	public static mst012201_HanbaiTaniKbDictionary KO	 	= new mst012201_HanbaiTaniKbDictionary("6", "個");
	// 2016.09.29 M.Akagi #2145 (E)
	public static mst012201_HanbaiTaniKbDictionary UNKNOWN   = new mst012201_HanbaiTaniKbDictionary("X","UNKNOWN");
}
