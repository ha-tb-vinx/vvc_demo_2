/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : ＥＯＳ区分ディクショナリ</p>
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
 * <P>説明 : ＥＯＳ区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst001201_EosKbDictionary {
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
	private mst001201_EosKbDictionary(String code, String name) {
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
	public static mst001201_EosKbDictionary getStatus(String key)
	{
		mst001201_EosKbDictionary ret = (mst001201_EosKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001201_EosKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001201_EosKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst001201_EosKbDictionary) )
			return false;
		String objStr = ((mst001201_EosKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst001201_EosKbDictionary EOS_TAISYO 		= new mst001201_EosKbDictionary("1", "ＥＯＳ対象");
	public static mst001201_EosKbDictionary EOS_TAISYOGAI 		= new mst001201_EosKbDictionary("2", "ＥＯＳ対象外");
	public static mst001201_EosKbDictionary SEISEN_WEB_TAISYO 	= new mst001201_EosKbDictionary("3", "生鮮ＷＥＢ対象");
//	public static mst001201_EosKbDictionary FAX_TAISYO 		= new mst001201_EosKbDictionary("4", "ＦＡＸ発注");
//	public static mst001201_EosKbDictionary JIDO_TAISYOGAI 	= new mst001201_EosKbDictionary("5", "自動発注対象外");
	public static mst001201_EosKbDictionary JIDO_TAISYOGAI 	= new mst001201_EosKbDictionary("4", "自動発注対象外");
	public static mst001201_EosKbDictionary UNKNOWN = new mst001201_EosKbDictionary("X","UNKNOWN");
}
