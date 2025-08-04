package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル:単価計算区分Dictionary </p>
 * <p>説明: 単価計算区分を保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 */
public class mst009901_TankaCalKbDictionary {

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
	private mst009901_TankaCalKbDictionary(String code, String name) {
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
	public static mst009901_TankaCalKbDictionary getStatus(String key) {
		mst009901_TankaCalKbDictionary ret = (mst009901_TankaCalKbDictionary)map.get(key);
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
	public static mst009901_TankaCalKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst009901_TankaCalKbDictionary)) {
			return false;
		}
		String objStr = ((mst009901_TankaCalKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst009901_TankaCalKbDictionary EN	= new mst009901_TankaCalKbDictionary("1", MessageUtil.getMessage("MST009901_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst009901_TankaCalKbDictionary SEN 	= new mst009901_TankaCalKbDictionary("2", MessageUtil.getMessage("MST009901_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst009901_TankaCalKbDictionary UNKNOWN		= new mst009901_TankaCalKbDictionary("X", "UNKNOWN");

}
