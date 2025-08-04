package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル:商品マスタ一括削除対象項目Dictionary </p>
 * <p>説明: 商品マスタ一括削除対象項目を保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 */
public class mst009801_SyohinIkkatuDelTargetDictionary {

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
	private mst009801_SyohinIkkatuDelTargetDictionary(String code, String name) {
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
	public static mst009801_SyohinIkkatuDelTargetDictionary getStatus(String key) {
		mst009801_SyohinIkkatuDelTargetDictionary ret = (mst009801_SyohinIkkatuDelTargetDictionary)map.get(key);
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
	public static mst009801_SyohinIkkatuDelTargetDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst009801_SyohinIkkatuDelTargetDictionary)) {
			return false;
		}
		String objStr = ((mst009801_SyohinIkkatuDelTargetDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst009801_SyohinIkkatuDelTargetDictionary TAISYO	= new mst009801_SyohinIkkatuDelTargetDictionary("1",MessageUtil.getMessage("MST009801_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")) );
	public static mst009801_SyohinIkkatuDelTargetDictionary TAISYOGAI 	= new mst009801_SyohinIkkatuDelTargetDictionary("9", MessageUtil.getMessage("MST009801_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst009801_SyohinIkkatuDelTargetDictionary UNKNOWN		= new mst009801_SyohinIkkatuDelTargetDictionary("X", "UNKNOWN");

}
