/**
 * <P>タイトル : mst007001_MstSiyofukaKbDictionary</p>
 * <P>説明 : マスタ使用不可区分フラグディクショナリ</p>
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
 * <P>タイトル : mst007001_MstSiyofukaKbDictionary</p>
 * <P>説明 : マスタ使用不可区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst007001_MstSiyofukaKbDictionary {
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
	private mst007001_MstSiyofukaKbDictionary(String code, String name) {
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
	public static mst007001_MstSiyofukaKbDictionary getStatus(String key)
	{
		mst007001_MstSiyofukaKbDictionary ret = (mst007001_MstSiyofukaKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst007001_MstSiyofukaKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst007001_MstSiyofukaKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst007001_MstSiyofukaKbDictionary) )
			return false;
		String objStr = ((mst007001_MstSiyofukaKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst007001_MstSiyofukaKbDictionary KA = new mst007001_MstSiyofukaKbDictionary("0", "使用可");
	public static mst007001_MstSiyofukaKbDictionary FUKA = new mst007001_MstSiyofukaKbDictionary("1", "使用不可");
	public static mst007001_MstSiyofukaKbDictionary UNKNOWN = new mst007001_MstSiyofukaKbDictionary("X","UNKNOWN");
}
