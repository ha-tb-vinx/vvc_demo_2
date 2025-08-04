package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 生鮮フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.07.27) 初版作成
 */

public class SeisenFgDictionary {
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
	private SeisenFgDictionary(String code, String name) {
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
	 * @return SeisenFgDictionary
	 */
	public static SeisenFgDictionary getStatus(String key)
	{
		SeisenFgDictionary ret = (SeisenFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SeisenFgDictionary
	 */
	public static SeisenFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SeisenFgDictionary
	 */
	public static SeisenFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static SeisenFgDictionary HISEISEN = new SeisenFgDictionary("0","非生鮮");
	public static SeisenFgDictionary SEISEN = new SeisenFgDictionary("1","生鮮");
	public static SeisenFgDictionary UNKNOWN = new SeisenFgDictionary("X","UNKNOWN");

}