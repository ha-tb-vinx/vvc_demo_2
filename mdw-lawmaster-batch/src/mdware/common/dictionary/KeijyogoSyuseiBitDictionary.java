package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 計上後修正ビットディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author 李武勇
 * @version 1.00 (2005.06.23) 初版作成
 */
public class KeijyogoSyuseiBitDictionary {
	
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
	private KeijyogoSyuseiBitDictionary(String code, String name) {
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
	 * @return KeijyogoSyuseiBitDictionary
	 */
	public static KeijyogoSyuseiBitDictionary getStatus(String key) {
		KeijyogoSyuseiBitDictionary ret = (KeijyogoSyuseiBitDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KeijyogoSyuseiBitDictionary
	 */
	public static KeijyogoSyuseiBitDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KeijyogoSyuseiBitDictionary
	 */
	public static KeijyogoSyuseiBitDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	public static KeijyogoSyuseiBitDictionary SYOKICHI = new KeijyogoSyuseiBitDictionary("0", "初期値");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_1 = new KeijyogoSyuseiBitDictionary("1", "訂正データ_1");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_2 = new KeijyogoSyuseiBitDictionary("2", "訂正データ_2");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_3 = new KeijyogoSyuseiBitDictionary("3", "訂正データ_3");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_4 = new KeijyogoSyuseiBitDictionary("4", "訂正データ_4");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_5 = new KeijyogoSyuseiBitDictionary("5", "訂正データ_5");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_6 = new KeijyogoSyuseiBitDictionary("6", "訂正データ_6");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_7 = new KeijyogoSyuseiBitDictionary("7", "訂正データ_7");
	public static KeijyogoSyuseiBitDictionary TEISEIDATA_8 = new KeijyogoSyuseiBitDictionary("8", "訂正データ_8");
	public static KeijyogoSyuseiBitDictionary TORIKESIDATA = new KeijyogoSyuseiBitDictionary("9", "取消データ");
	public static KeijyogoSyuseiBitDictionary UNKNOWN = new KeijyogoSyuseiBitDictionary("X", "UNKNOWN");
}