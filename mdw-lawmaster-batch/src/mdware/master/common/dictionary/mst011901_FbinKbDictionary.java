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
 * <P>タイトル : mst011901_FbinKbDictionary</p>
 * <P>説明 : F便区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2013</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author VJC
 * @Version 3.0  (2013.05.20) M.Ayukawa ランドローム様対応
 * @see なし								
 */
public class mst011901_FbinKbDictionary {
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
	private mst011901_FbinKbDictionary(String code, String name) {
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
	public static mst011901_FbinKbDictionary getStatus(String key)
	{
		mst011901_FbinKbDictionary ret = (mst011901_FbinKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011901_FbinKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011901_FbinKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst011901_FbinKbDictionary) )
			return false;
		String objStr = ((mst011901_FbinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst011901_FbinKbDictionary TAISYOGAI 	= new mst011901_FbinKbDictionary("0", "F便対象外");
	public static mst011901_FbinKbDictionary TAISYO 	= new mst011901_FbinKbDictionary("1", "F便対象");
	public static mst011901_FbinKbDictionary UNKNOWN 	= new mst011901_FbinKbDictionary("X","UNKNOWN");
}
