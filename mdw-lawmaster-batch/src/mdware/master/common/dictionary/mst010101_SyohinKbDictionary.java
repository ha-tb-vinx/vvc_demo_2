package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:商品区分（仕入販売区分）Dictionary </p>
 * <p>説明: 商品区分（仕入販売区分）を保持する</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author Hattori
 * @version 1.0
 */
public class mst010101_SyohinKbDictionary {

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
	private mst010101_SyohinKbDictionary(String code, String name) {
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
	 * @return mst440301_SiireSystemKbDictionary
	 */
	public static mst010101_SyohinKbDictionary getStatus(String key) {
		mst010101_SyohinKbDictionary ret = (mst010101_SyohinKbDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;

	}	

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst440301_SiireSystemKbDictionary
	 */
	public static mst010101_SyohinKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010101_SyohinKbDictionary)) {
			return false;
		}
		String objStr = ((mst010101_SyohinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010101_SyohinKbDictionary SIIREHANBAI	= new mst010101_SyohinKbDictionary("1", "仕入=販売");	
	public static mst010101_SyohinKbDictionary HANBAI 	= new mst010101_SyohinKbDictionary("2", "販売");
	public static mst010101_SyohinKbDictionary SIIRE	= new mst010101_SyohinKbDictionary("3", "仕入");	
//	public static mst010101_SyohinKbDictionary KEIHI 	= new mst010101_SyohinKbDictionary("4", "経費");
	public static mst010101_SyohinKbDictionary UNKNOWN		= new mst010101_SyohinKbDictionary("X", "UNKNOWN");	

}
