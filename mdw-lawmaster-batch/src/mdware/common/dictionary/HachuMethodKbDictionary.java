package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 発注方法区分データディクショナリ</p>
 * <p>説明: 発注方法区分表示に必要なデータディクショナリを定義します
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.00 (2004.07.29) 初版作成
 */

public class HachuMethodKbDictionary {
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
	private HachuMethodKbDictionary(String code, String name) {
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
	 * @return HachuMethodKbDictionary
	 */
	public static HachuMethodKbDictionary getStatus(String key)
	{
		HachuMethodKbDictionary ret = (HachuMethodKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMethodKbDictionary
	 */
	public static HachuMethodKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMethodKbDictionary
	 */
	public static HachuMethodKbDictionary getStatus(int key)
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
		if( !(obj instanceof HachuMethodKbDictionary) )
			return false;
		String objStr = ((HachuMethodKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static HachuMethodKbDictionary EOB = new HachuMethodKbDictionary("0", "EOB");
	public static HachuMethodKbDictionary SPOT = new HachuMethodKbDictionary("1", "スポット買い");
	public static HachuMethodKbDictionary NONEOS = new HachuMethodKbDictionary("2", "非EOS");
	public static HachuMethodKbDictionary OTHER = new HachuMethodKbDictionary("3", "その他");
    public static HachuMethodKbDictionary UNKNOWN = new HachuMethodKbDictionary("X","UNKNOWN");

}