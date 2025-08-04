package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 自動発注反映フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author sekiya
 * @version 1.00 (2004.08.13) 初版作成
 */

public class HaneiFgDictionary {
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
	private HaneiFgDictionary(String code, String name) {
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
	 * @return HaneiFgDictionary
	 */
	public static HaneiFgDictionary getStatus(String key)
	{
		HaneiFgDictionary ret = (HaneiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaneiFgDictionary
	 */
	public static HaneiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaneiFgDictionary
	 */
	public static HaneiFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HaneiFgDictionary MI = new HaneiFgDictionary("0","未");
	public static HaneiFgDictionary SUMI = new HaneiFgDictionary("1","済");
	public static HaneiFgDictionary UNKNOWN = new HaneiFgDictionary("X","UNKNOWN");

}