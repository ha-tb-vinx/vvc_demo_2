package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 物品受領フラグデータディクショナリ</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author ikemoto
 * @version 1.00
 */

public class BupinJyuryoFgDictionary {
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
	private BupinJyuryoFgDictionary(String code, String name) {
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
	public static BupinJyuryoFgDictionary getStatus(String key)
	{
		BupinJyuryoFgDictionary ret = (BupinJyuryoFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static BupinJyuryoFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static BupinJyuryoFgDictionary getStatus(int key)
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
		if( !(obj instanceof BupinJyuryoFgDictionary) )
			return false;
		String objStr = ((BupinJyuryoFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static BupinJyuryoFgDictionary WEB = new BupinJyuryoFgDictionary("1", "WEB");
	public static BupinJyuryoFgDictionary JCA = new BupinJyuryoFgDictionary("2", "JCA");
	public static BupinJyuryoFgDictionary UNKNOWN = new BupinJyuryoFgDictionary("X","UNKNOWN");
}
