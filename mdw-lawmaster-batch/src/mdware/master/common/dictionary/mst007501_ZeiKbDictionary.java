/**
 * <P>タイトル : mst007501_ZeiKbDictionary</p>
 * <P>説明 : 税区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : mst007501_ZeiKbDictionary</p>
 * <P>説明 : 税区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst007501_ZeiKbDictionary {
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
	private mst007501_ZeiKbDictionary(String code, String name) {
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
	public static mst007501_ZeiKbDictionary getStatus(String key)
	{
		mst007501_ZeiKbDictionary ret = (mst007501_ZeiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst007501_ZeiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst007501_ZeiKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst007501_ZeiKbDictionary) )
			return false;
		String objStr = ((mst007501_ZeiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst007501_ZeiKbDictionary UTIZEI 	= new mst007501_ZeiKbDictionary("1", "内税");
	public static mst007501_ZeiKbDictionary SOTOZEI 	= new mst007501_ZeiKbDictionary("2", "外税");
	public static mst007501_ZeiKbDictionary HIKAZEI 	= new mst007501_ZeiKbDictionary("3", "非課税");

	//未使用
	public static mst007501_ZeiKbDictionary PRETAX = new mst007501_ZeiKbDictionary("1", "税込み");
	public static mst007501_ZeiKbDictionary TAXFREE = new mst007501_ZeiKbDictionary("2", "非課税");
	public static mst007501_ZeiKbDictionary UNKNOWN = new mst007501_ZeiKbDictionary("X","UNKNOWN");
}
