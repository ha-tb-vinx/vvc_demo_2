package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:内部IF対象フラグDictionary </p>
 * <p>説明: 内部IF対象データを判別するフラグを保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 1.0
 */
public class mst010501_NaibuIFFgDictionary {

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
	private mst010501_NaibuIFFgDictionary(String code, String name) {
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
	public static mst010501_NaibuIFFgDictionary getStatus(String key) {
		mst010501_NaibuIFFgDictionary ret = (mst010501_NaibuIFFgDictionary)map.get(key);
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
	public static mst010501_NaibuIFFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010501_NaibuIFFgDictionary)) {
			return false;
		}
		String objStr = ((mst010501_NaibuIFFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010501_NaibuIFFgDictionary TAISYOGAI		= new mst010501_NaibuIFFgDictionary("0", "対象外");
	public static mst010501_NaibuIFFgDictionary TAISYO			= new mst010501_NaibuIFFgDictionary("1", "対象");
	public static mst010501_NaibuIFFgDictionary UNKNOWN		= new mst010501_NaibuIFFgDictionary("X", "UNKNOWN");	

}
