package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 納品休日区分データディクショナリ</p>
 * <p>説明: 
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.00 (2004.07.27) 初版作成
 */

public class NohinKyujitsuKbDictionary {
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
	private NohinKyujitsuKbDictionary(String code, String name) {
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
	 * @return NohinKyujitsuKbDictionary
	 */
	public static NohinKyujitsuKbDictionary getStatus(String key)
	{
		NohinKyujitsuKbDictionary ret = (NohinKyujitsuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKyujitsuKbDictionary
	 */
	public static NohinKyujitsuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinKyujitsuKbDictionary
	 */
	public static NohinKyujitsuKbDictionary getStatus(int key)
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
		if( !(obj instanceof NohinKyujitsuKbDictionary) )
			return false;
		String objStr = ((NohinKyujitsuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static NohinKyujitsuKbDictionary NOHINKA = new NohinKyujitsuKbDictionary("0", "納品可");
	public static NohinKyujitsuKbDictionary NOHINFUKA = new NohinKyujitsuKbDictionary("1", "納品不可");
    public static NohinKyujitsuKbDictionary UNKNOWN = new NohinKyujitsuKbDictionary("X","UNKNOWN");

}