package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 物品受領作成済フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author 李武勇
 * @version 1.00 (2005.06.23) 初版作成
 */
public class BupinJyuryoMakeFgDictionary {
	
	/**
	 * ＤＢ内のコード
	 */
	private String code = null;
	
	/**
	 * ＤＢ内コードの意味
	 */
	private String name = null;

	/**
	 * 対応したクラスを保持するマップ
	 */
	private static final Map map = new HashMap();

	/**
	 * コンストラクタ
	 * @param code ＤＢ内のコード
	 * @param name ＤＢ内コードの意味
	 */
	private BupinJyuryoMakeFgDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code, this);
	}

	/**
	 * ＤＢ内コードの意味を返す
	 * @return String
	 */
	public String toString() {
		return name;
	}

	/**
	 * ＤＢ内のコードを返す
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BupinJyuryoMakeFgDictionary
	 */
	public static BupinJyuryoMakeFgDictionary getStatus(String key) {
		BupinJyuryoMakeFgDictionary ret = (BupinJyuryoMakeFgDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BupinJyuryoMakeFgDictionary
	 */
	public static BupinJyuryoMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BupinJyuryoMakeFgDictionary
	 */
	public static BupinJyuryoMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	public static BupinJyuryoMakeFgDictionary MISYORI = new BupinJyuryoMakeFgDictionary("0", "未処理");
	public static BupinJyuryoMakeFgDictionary SYORIZUMI = new BupinJyuryoMakeFgDictionary("1", "処理済");
	public static BupinJyuryoMakeFgDictionary SYORICHU = new BupinJyuryoMakeFgDictionary("9", "処理中");
	public static BupinJyuryoMakeFgDictionary UNKNOWN = new BupinJyuryoMakeFgDictionary("X", "UNKNOWN");
}