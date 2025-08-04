package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: メンテ区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 */

public class MenteKbDictionary {
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
	private MenteKbDictionary(String code, String name) {
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
	 * @return MenteKbDictionary
	 */
	public static MenteKbDictionary getStatus(String key)
	{
		MenteKbDictionary ret = (MenteKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return MenteKbDictionary
	 */
	public static MenteKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return MenteKbDictionary
	 */
	public static MenteKbDictionary getStatus(int key)
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
		if( !(obj instanceof MenteKbDictionary) )
			return false;
		String objStr = ((MenteKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static MenteKbDictionary REP = new MenteKbDictionary("0", "全件置換");
	public static MenteKbDictionary INS = new MenteKbDictionary("1", "新規");
	public static MenteKbDictionary UPD = new MenteKbDictionary("2", "変更");
	public static MenteKbDictionary DEL = new MenteKbDictionary("3", "削除");
	public static MenteKbDictionary UNKNOWN = new MenteKbDictionary("X","UNKNOWN");
}
