package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:作成先テーブル区分Dictionary </p>
 * <p>説明: 作成先テーブル区分を保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 1.0
 */
public class mst010401_CreateTableKbDictionary {

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
	private mst010401_CreateTableKbDictionary(String code, String name) {
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
	public static mst010401_CreateTableKbDictionary getStatus(String key) {
		mst010401_CreateTableKbDictionary ret = (mst010401_CreateTableKbDictionary)map.get(key);
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
	public static mst010401_CreateTableKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010401_CreateTableKbDictionary)) {
			return false;
		}
		String objStr = ((mst010401_CreateTableKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010401_CreateTableKbDictionary BK				= new mst010401_CreateTableKbDictionary("1", "BKテーブル");
	public static mst010401_CreateTableKbDictionary TMP			= new mst010401_CreateTableKbDictionary("2", "TMPテーブル");
	public static mst010401_CreateTableKbDictionary KK			= new mst010401_CreateTableKbDictionary("3", "緊急TMPテーブル");
	public static mst010401_CreateTableKbDictionary UNKNOWN		= new mst010401_CreateTableKbDictionary("X", "UNKNOWN");

}
