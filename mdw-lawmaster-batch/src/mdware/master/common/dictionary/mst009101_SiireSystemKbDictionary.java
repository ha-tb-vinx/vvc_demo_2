package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:仕入先システム区分保持用Dictionary </p>
 * <p>説明: 仕入先マスタ．仕入先システム区分に格納する値を保持する</p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * <pre>
 * 入力
 *
 * 出力
 *
 * 戻り値
 *
 * </pre>
 *
 * @author k_tanigawa
 * @version 1.0 (2006/09/24) 初版作成
 */
public class mst009101_SiireSystemKbDictionary {

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
	private mst009101_SiireSystemKbDictionary(String code, String name) {
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
	public static mst009101_SiireSystemKbDictionary getStatus(String key) {
		mst009101_SiireSystemKbDictionary ret = (mst009101_SiireSystemKbDictionary)map.get(key);
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
	public static mst009101_SiireSystemKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst009101_SiireSystemKbDictionary)) {
			return false;
		}
		String objStr = ((mst009101_SiireSystemKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst009101_SiireSystemKbDictionary POS     = new mst009101_SiireSystemKbDictionary("1", "POS");		//1:POS
	public static mst009101_SiireSystemKbDictionary TAG     = new mst009101_SiireSystemKbDictionary("2", "TAG");		//2:TAG
	public static mst009101_SiireSystemKbDictionary POS_TAG = new mst009101_SiireSystemKbDictionary("3", "POS_TAG");	//3:POS_TAG
	public static mst009101_SiireSystemKbDictionary UNKNOWN = new mst009101_SiireSystemKbDictionary("X", "UNKNOWN");	//UNKNOWN
	public static mst009101_SiireSystemKbDictionary NULL = new mst009101_SiireSystemKbDictionary("", "BLANK");		//空

}
