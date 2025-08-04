package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 定貫区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba
 * @version 1.00 (2003.10.01) 初版作成
 */

public class TeikanKbDictionary {
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
	private TeikanKbDictionary(String code, String name) {
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
	 * @return TeikanKbDictionary
	 */
	public static TeikanKbDictionary getStatus(String key)
	{
		TeikanKbDictionary ret = (TeikanKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeikanKbDictionary
	 */
	public static TeikanKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeikanKbDictionary
	 */
	public static TeikanKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

//	2004.09.09 N.Sekiya MOD(ポスフール様用にコードを変更)
	public static TeikanKbDictionary TEIKAN = new TeikanKbDictionary("1","定貫");
	public static TeikanKbDictionary FUTEIKAN = new TeikanKbDictionary("2","不定貫");
	public static TeikanKbDictionary UNKNOWN = new TeikanKbDictionary("X","UNKNOWN");

}