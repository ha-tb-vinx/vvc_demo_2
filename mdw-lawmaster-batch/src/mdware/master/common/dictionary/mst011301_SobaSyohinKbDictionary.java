package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:相場商品区分Dictionary </p>
 * <p>説明: 相場商品区分を保持する。</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 1.0
 */
public class mst011301_SobaSyohinKbDictionary {

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
	private mst011301_SobaSyohinKbDictionary(String code, String name) {
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
	public static mst011301_SobaSyohinKbDictionary getStatus(String key) {
		mst011301_SobaSyohinKbDictionary ret = (mst011301_SobaSyohinKbDictionary)map.get(key);
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
	public static mst011301_SobaSyohinKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst011301_SobaSyohinKbDictionary)) {
			return false;
		}
		String objStr = ((mst011301_SobaSyohinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst011301_SobaSyohinKbDictionary SOBA_SYOHIN	 	= new mst011301_SobaSyohinKbDictionary("1", "相場商品");
	public static mst011301_SobaSyohinKbDictionary HISOBA_SYOHIN	= new mst011301_SobaSyohinKbDictionary("2", "非相場商品");
	public static mst011301_SobaSyohinKbDictionary UNKNOWN		= new mst011301_SobaSyohinKbDictionary("X", "UNKNOWN");	

}
