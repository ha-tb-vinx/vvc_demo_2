package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: テスト区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003-2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author shimatani
 * @version 1.00 (2005.01.27) 初版作成
 */

public class TestKbDictionary {
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
	private TestKbDictionary(String code, String name) {
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
	 * @return EdiKbDictionary
	 */
	public static TestKbDictionary getStatus(String key)
	{
		TestKbDictionary ret = (TestKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static TestKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static TestKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static TestKbDictionary HONBAN = new TestKbDictionary("0","本番");
	public static TestKbDictionary TEST = new TestKbDictionary("1","テスト");
	public static TestKbDictionary UNKNOWN = new TestKbDictionary("X","UNKNOWN");

}