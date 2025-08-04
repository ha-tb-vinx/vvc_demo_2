package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 相場区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.08.23) 初版作成
 */

public class SobaKbDictionary {
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
	private SobaKbDictionary(String code, String name) {
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
	 * @return SobaKbDictionary
	 */
	public static SobaKbDictionary getStatus(String key)
	{
		SobaKbDictionary ret = (SobaKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SobaKbDictionary
	 */
	public static SobaKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SobaKbDictionary
	 */
	public static SobaKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static SobaKbDictionary HISOBA = new SobaKbDictionary("0","非相場");
	public static SobaKbDictionary SOBA = new SobaKbDictionary("1","相場");
	public static SobaKbDictionary UNKNOWN = new SobaKbDictionary("X","UNKNOWN");

}