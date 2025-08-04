package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 小売訂正フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author morita
 * @version 1.00 (2005.01.07) 初版作成
 */

public class KouriTeiseiFgDictionary {
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
	private KouriTeiseiFgDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code,this);
	}

	/**
	 * ＤＢ内コードの意味を返す
	 * @return String
	 */
	public String toString()
	{
		return name;
	}

	/**
	 * ＤＢ内のコードを返す
	 * @return String
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeiseiFgDictionary
	 */
	public static KouriTeiseiFgDictionary getStatus(String key)
	{
		KouriTeiseiFgDictionary ret = (KouriTeiseiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeiseiFgDictionary
	 */
	public static KouriTeiseiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeiseiFgDictionary
	 */
	public static KouriTeiseiFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static KouriTeiseiFgDictionary MI = new KouriTeiseiFgDictionary("0","未");
	public static KouriTeiseiFgDictionary SUMI = new KouriTeiseiFgDictionary("1","済");
	public static KouriTeiseiFgDictionary UNKNOWN = new KouriTeiseiFgDictionary("X","UNKNOWN");

}