package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:商品体系切替状態Dictionary </p>
 * <p>説明: 商品体系切替状態を保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author H.Yamamoto
 * @version 1.0
 */
public class mst009301_TaikeiKirikaeJyotaiDictionary {

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
	private mst009301_TaikeiKirikaeJyotaiDictionary(String code, String name) {
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
	public static mst009301_TaikeiKirikaeJyotaiDictionary getStatus(String key) {
		mst009301_TaikeiKirikaeJyotaiDictionary ret = (mst009301_TaikeiKirikaeJyotaiDictionary)map.get(key);
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
	public static mst009301_TaikeiKirikaeJyotaiDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst009301_TaikeiKirikaeJyotaiDictionary)) {
			return false;
		}
		String objStr = ((mst009301_TaikeiKirikaeJyotaiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst009301_TaikeiKirikaeJyotaiDictionary KIRIKAEZUMI	= new mst009301_TaikeiKirikaeJyotaiDictionary("1", "切替済");	
	public static mst009301_TaikeiKirikaeJyotaiDictionary KIRIKAEMAE 	= new mst009301_TaikeiKirikaeJyotaiDictionary("2", "切替前");
	public static mst009301_TaikeiKirikaeJyotaiDictionary KIRIKAEATO	= new mst009301_TaikeiKirikaeJyotaiDictionary("3", "切替後");	
	public static mst009301_TaikeiKirikaeJyotaiDictionary UNKNOWN		= new mst009301_TaikeiKirikaeJyotaiDictionary("XX", "UNKNOWN");	

}
