package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 自動発注停止区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 */

public class AutoOrderKikanTeisiKbDictionary {
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
	private AutoOrderKikanTeisiKbDictionary(String code, String name) {
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
	 * @return AutoOrderKikanTeisiKbDictionary
	 */
	public static AutoOrderKikanTeisiKbDictionary getStatus(String key)
	{
		AutoOrderKikanTeisiKbDictionary ret = (AutoOrderKikanTeisiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AutoOrderKikanTeisiKbDictionary
	 */
	public static AutoOrderKikanTeisiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AutoOrderKikanTeisiKbDictionary
	 */
	public static AutoOrderKikanTeisiKbDictionary getStatus(int key)
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
		if( !(obj instanceof AutoOrderKikanTeisiKbDictionary) )
			return false;
		String objStr = ((AutoOrderKikanTeisiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static AutoOrderKikanTeisiKbDictionary NO = new AutoOrderKikanTeisiKbDictionary("0", "停止なし");
	public static AutoOrderKikanTeisiKbDictionary TEISI = new AutoOrderKikanTeisiKbDictionary("1", "自動発注停止");
	public static AutoOrderKikanTeisiKbDictionary UNKNOWN = new AutoOrderKikanTeisiKbDictionary("X","UNKNOWN");
}
