package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 停止反映フラグデータディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 */

public class TeisiHaneiFgDictionary {
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
	private TeisiHaneiFgDictionary(String code, String name) {
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
	 * @return TeisiHaneiFgDictionary
	 */
	public static TeisiHaneiFgDictionary getStatus(String key)
	{
		TeisiHaneiFgDictionary ret = (TeisiHaneiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeisiHaneiFgDictionary
	 */
	public static TeisiHaneiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TeisiHaneiFgDictionary
	 */
	public static TeisiHaneiFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals( Object obj )
	{
		if( !(obj instanceof TeisiHaneiFgDictionary) )
			return false;
		String objStr = ((TeisiHaneiFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static TeisiHaneiFgDictionary MI = new TeisiHaneiFgDictionary("0", "未");
	public static TeisiHaneiFgDictionary SUMI = new TeisiHaneiFgDictionary("1", "済");
	public static TeisiHaneiFgDictionary UNKNOWN = new TeisiHaneiFgDictionary("X","UNKNOWN");
}
