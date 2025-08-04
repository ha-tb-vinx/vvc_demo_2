/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 店在庫区分ディクショナリ</p>
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
 * <P>説明 : 店在庫区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst001901_MiseZaikoKbDictionary {
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
	private mst001901_MiseZaikoKbDictionary(String code, String name) {
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
	public static mst001901_MiseZaikoKbDictionary getStatus(String key)
	{
		mst001901_MiseZaikoKbDictionary ret = (mst001901_MiseZaikoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001901_MiseZaikoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001901_MiseZaikoKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst001901_MiseZaikoKbDictionary) )
			return false;
		String objStr = ((mst001901_MiseZaikoKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst001901_MiseZaikoKbDictionary KAITORI = new mst001901_MiseZaikoKbDictionary("1", "買取");
	public static mst001901_MiseZaikoKbDictionary ITAKU = new mst001901_MiseZaikoKbDictionary("2", "委託");
	public static mst001901_MiseZaikoKbDictionary SYOUKA = new mst001901_MiseZaikoKbDictionary("3", "消化");
	public static mst001901_MiseZaikoKbDictionary KAITORI_ZAIKO_JOGAI = new mst001901_MiseZaikoKbDictionary("4", "買取在庫除外");
	public static mst001901_MiseZaikoKbDictionary ITAKU_ZAIKO_JOGAI = new mst001901_MiseZaikoKbDictionary("5", "委託在庫除外");
	public static mst001901_MiseZaikoKbDictionary UNKNOWN = new mst001901_MiseZaikoKbDictionary("X","UNKNOWN");
}
