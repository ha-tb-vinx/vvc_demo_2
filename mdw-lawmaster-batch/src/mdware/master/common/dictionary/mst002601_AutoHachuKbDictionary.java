/**
 * <P>タイトル : mst002601_AutoHachuKbDictionary</p>
 * <P>説明 : 自動発注ディクショナリ</p>
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
 * <P>タイトル : mst002601_AutoHachuKbDictionary</p>
 * <P>説明 : 自動発注ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst002601_AutoHachuKbDictionary {
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
	private mst002601_AutoHachuKbDictionary(String code, String name) {
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
	public static mst002601_AutoHachuKbDictionary getStatus(String key)
	{
		mst002601_AutoHachuKbDictionary ret = (mst002601_AutoHachuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst002601_AutoHachuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst002601_AutoHachuKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst002601_AutoHachuKbDictionary) )
			return false;
		String objStr = ((mst002601_AutoHachuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst002601_AutoHachuKbDictionary TAISYOGAI  = new mst002601_AutoHachuKbDictionary("0", "対象外");
	public static mst002601_AutoHachuKbDictionary TAISYO  = new mst002601_AutoHachuKbDictionary("1", "対象");	
	public static mst002601_AutoHachuKbDictionary UNKNOWN = new mst002601_AutoHachuKbDictionary("X","UNKNOWN");
}
