package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 食・非区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 */

public class SijiKbDictionary {
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
	private SijiKbDictionary(String code, String name) {
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
	 * @return SijiKbDictionary
	 */
	public static SijiKbDictionary getStatus(String key)
	{
		SijiKbDictionary ret = (SijiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SijiKbDictionary
	 */
	public static SijiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SijiKbDictionary
	 */
	public static SijiKbDictionary getStatus(int key)
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
		if( !(obj instanceof SijiKbDictionary) )
			return false;
		String objStr = ((SijiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static SijiKbDictionary ZORYO = new SijiKbDictionary("1", "増量");
	public static SijiKbDictionary TEISI = new SijiKbDictionary("2", "停止");
	public static SijiKbDictionary KAKUHAN = new SijiKbDictionary("3", "拡販");
	public static SijiKbDictionary HACHU_AUTO = new SijiKbDictionary("4", "発注（自動）");
	public static SijiKbDictionary HACHU_HOJU = new SijiKbDictionary("5", "発注（補充）");
	public static SijiKbDictionary UNKNOWN = new SijiKbDictionary("X","UNKNOWN");
}
