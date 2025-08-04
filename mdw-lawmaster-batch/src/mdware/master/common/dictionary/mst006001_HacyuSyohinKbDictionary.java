/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 発注商品コード区分ディクショナリ</p>
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
 * <P>説明 : 発注商品コード区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius Makuta
 * @version 1.0
 * @see なし								
 */
public class mst006001_HacyuSyohinKbDictionary {
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
	private mst006001_HacyuSyohinKbDictionary(String code, String name) {
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
	public static mst006001_HacyuSyohinKbDictionary getStatus(String key)
	{
		mst006001_HacyuSyohinKbDictionary ret = (mst006001_HacyuSyohinKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006001_HacyuSyohinKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006001_HacyuSyohinKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst006001_HacyuSyohinKbDictionary) )
			return false;
		String objStr = ((mst006001_HacyuSyohinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst006001_HacyuSyohinKbDictionary JISYA_CD = new mst006001_HacyuSyohinKbDictionary("1", "自社品番");
	public static mst006001_HacyuSyohinKbDictionary JAN_CD = new mst006001_HacyuSyohinKbDictionary("2", "ＪＡＮコード");
	public static mst006001_HacyuSyohinKbDictionary UNKNOWN = new mst006001_HacyuSyohinKbDictionary("X","UNKNOWN");
}
