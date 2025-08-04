/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : アソートパターンディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kikuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : アソートパターンディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius Y.Koyama
 * @version 1.0
 * @see なし								
 */
public class mst005701_AsortPatternDictionary {
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
	private mst005701_AsortPatternDictionary(String code, String name) {
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
	 * @return TaisyoKbDictionary
	 */
	public static mst005701_AsortPatternDictionary getStatus(String key)
	{
		mst005701_AsortPatternDictionary ret = (mst005701_AsortPatternDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005701_AsortPatternDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005701_AsortPatternDictionary getStatus(int key)
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
		if( !(obj instanceof mst005501_TankaItirituSyuseiDictionary) )
			return false;
		String objStr = ((mst005501_TankaItirituSyuseiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst005701_AsortPatternDictionary PATTERN1 = new mst005701_AsortPatternDictionary("1", "パターン１");
	public static mst005701_AsortPatternDictionary PATTERN2 = new mst005701_AsortPatternDictionary("2", "パターン２");
	public static mst005701_AsortPatternDictionary PATTERN3 = new mst005701_AsortPatternDictionary("3", "パターン３");
	public static mst005701_AsortPatternDictionary PATTERN4 = new mst005701_AsortPatternDictionary("4", "パターン４");
	public static mst005701_AsortPatternDictionary PATTERN5 = new mst005701_AsortPatternDictionary("5", "パターン５");
	public static mst005701_AsortPatternDictionary UNKNOWN = new mst005701_AsortPatternDictionary("X","UNKNOWN");
}
