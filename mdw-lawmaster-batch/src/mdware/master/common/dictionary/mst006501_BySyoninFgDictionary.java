/**
 * <P>タイトル : mst006501_BySyoninFgDictionary</p>
 * <P>説明 : バイヤー承認フラグディクショナリ</p>
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
 * <P>タイトル : mst006501_BySyoninFgDictionary</p>
 * <P>説明 : バイヤー承認フラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst006501_BySyoninFgDictionary {
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
	private mst006501_BySyoninFgDictionary(String code, String name) {
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
	public static mst006501_BySyoninFgDictionary getStatus(String key)
	{
		mst006501_BySyoninFgDictionary ret = (mst006501_BySyoninFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006501_BySyoninFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006501_BySyoninFgDictionary getStatus(int key)
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
		if( !(obj instanceof mst006501_BySyoninFgDictionary) )
			return false;
		String objStr = ((mst006501_BySyoninFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst006501_BySyoninFgDictionary MISYONIN = new mst006501_BySyoninFgDictionary("0", "未承認");
	public static mst006501_BySyoninFgDictionary SYONIN = new mst006501_BySyoninFgDictionary("1", "承認");
	public static mst006501_BySyoninFgDictionary HISYONIN = new mst006501_BySyoninFgDictionary("2", "非承認");
	public static mst006501_BySyoninFgDictionary UNKNOWN = new mst006501_BySyoninFgDictionary("X","UNKNOWN");
	public static mst006501_BySyoninFgDictionary ALL = new mst006501_BySyoninFgDictionary("9","すべて");
}
