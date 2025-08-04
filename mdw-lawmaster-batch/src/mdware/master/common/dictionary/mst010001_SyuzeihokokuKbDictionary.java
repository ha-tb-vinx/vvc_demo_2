package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:酒税報告分類Dictionary </p>
 * <p>説明: 酒税報告分類を保持する</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author Hattori
 * @version 1.0
 */
public class mst010001_SyuzeihokokuKbDictionary {

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
	private mst010001_SyuzeihokokuKbDictionary(String code, String name) {
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
	public static mst010001_SyuzeihokokuKbDictionary getStatus(String key) {
		mst010001_SyuzeihokokuKbDictionary ret = (mst010001_SyuzeihokokuKbDictionary)map.get(key);
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
	public static mst010001_SyuzeihokokuKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010001_SyuzeihokokuKbDictionary)) {
			return false;
		}
		String objStr = ((mst010001_SyuzeihokokuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010001_SyuzeihokokuKbDictionary TAISYOGAI	= new mst010001_SyuzeihokokuKbDictionary("00", "酒税報告対象外");	
	public static mst010001_SyuzeihokokuKbDictionary TAISYO 	= new mst010001_SyuzeihokokuKbDictionary("99", "酒税報告対象");
	public static mst010001_SyuzeihokokuKbDictionary UNKNOWN		= new mst010001_SyuzeihokokuKbDictionary("X", "UNKNOWN");	

}
