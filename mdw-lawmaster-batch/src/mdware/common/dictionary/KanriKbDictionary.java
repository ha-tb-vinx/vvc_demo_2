package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 管理区分データディクショナリ</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author ikemoto
 * @version 1.00
 */

public class KanriKbDictionary {
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
	private KanriKbDictionary(String code, String name) {
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
	 * @return AlarmKbDictionary
	 */
	public static KanriKbDictionary getStatus(String key)
	{
		KanriKbDictionary ret = (KanriKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static KanriKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static KanriKbDictionary getStatus(int key)
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
		if( !(obj instanceof KanriKbDictionary) )
			return false;
		String objStr = ((KanriKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static KanriKbDictionary TENHANKU = new KanriKbDictionary("2", "店販区");
	public static KanriKbDictionary UNKNOWN = new KanriKbDictionary("X","UNKNOWN");
}
