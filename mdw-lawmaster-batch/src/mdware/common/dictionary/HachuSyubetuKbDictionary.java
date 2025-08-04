package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注種別区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 */

public class HachuSyubetuKbDictionary {
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
	private HachuSyubetuKbDictionary(String code, String name) {
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
	 * @return HachuSyubetuKbDictionary
	 */
	public static HachuSyubetuKbDictionary getStatus(String key)
	{
		HachuSyubetuKbDictionary ret = (HachuSyubetuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuSyubetuKbDictionary
	 */
	public static HachuSyubetuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuSyubetuKbDictionary
	 */
	public static HachuSyubetuKbDictionary getStatus(int key)
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
		if( !(obj instanceof HachuSyubetuKbDictionary) )
			return false;
		String objStr = ((HachuSyubetuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static HachuSyubetuKbDictionary SONOTA = new HachuSyubetuKbDictionary("0", "その他");
	public static HachuSyubetuKbDictionary SEISEN = new HachuSyubetuKbDictionary("1", "生鮮");
	public static HachuSyubetuKbDictionary AUTO = new HachuSyubetuKbDictionary("2", "自動");
	public static HachuSyubetuKbDictionary HOJU = new HachuSyubetuKbDictionary("3", "補充");
	public static HachuSyubetuKbDictionary ZORYO = new HachuSyubetuKbDictionary("4", "増量");
	public static HachuSyubetuKbDictionary KAKUHAN = new HachuSyubetuKbDictionary("5", "拡販");
	public static HachuSyubetuKbDictionary UNKNOWN = new HachuSyubetuKbDictionary("X","UNKNOWN");
}
