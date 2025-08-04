package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 非食・食品区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author takata
 * @version 1.0
 */

public class SyokuhinKbDictionary {
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
	private SyokuhinKbDictionary(String code, String name) {
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
	 * @return SyokuhinKbDictionary
	 */
	public static SyokuhinKbDictionary getStatus(String key)
	{
		SyokuhinKbDictionary ret = (SyokuhinKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyokuhinKbDictionary
	 */
	public static SyokuhinKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyokuhinKbDictionary
	 */
	public static SyokuhinKbDictionary getStatus(int key)
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
		if( !(obj instanceof SyokuhinKbDictionary) )
			return false;
		String objStr = ((SyokuhinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static SyokuhinKbDictionary HISYOKU = new SyokuhinKbDictionary("1", "非食");
	public static SyokuhinKbDictionary SYOKUHIN = new SyokuhinKbDictionary("2", "食品");
	public static SyokuhinKbDictionary UNKNOWN = new SyokuhinKbDictionary("X","UNKNOWN");
}
