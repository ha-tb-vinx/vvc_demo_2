package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:PC発行区分Dictionary </p>
 * <p>説明: PC発行区分を保持する</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 1.0
 */
public class mst010701_PcKbDictionary {

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
	private mst010701_PcKbDictionary(String code, String name) {
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
	public static mst010701_PcKbDictionary getStatus(String key) {
		mst010701_PcKbDictionary ret = (mst010701_PcKbDictionary)map.get(key);
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
	public static mst010701_PcKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010701_PcKbDictionary)) {
			return false;
		}
		String objStr = ((mst010701_PcKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010701_PcKbDictionary HAKKO_NASI	= new mst010701_PcKbDictionary("0", "発行なし");
	public static mst010701_PcKbDictionary HAKKO_ARI	= new mst010701_PcKbDictionary("1", "発行あり");
	public static mst010701_PcKbDictionary UNKNOWN		= new mst010701_PcKbDictionary("X", "UNKNOWN");	

}
