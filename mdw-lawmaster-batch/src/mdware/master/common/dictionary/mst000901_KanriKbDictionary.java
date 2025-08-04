/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 管理区分ディクショナリ</p>
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
 * <P>説明 : 管理区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst000901_KanriKbDictionary {
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
	private mst000901_KanriKbDictionary(String code, String name) {
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
	public static mst000901_KanriKbDictionary getStatus(String key)
	{
		mst000901_KanriKbDictionary ret = (mst000901_KanriKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000901_KanriKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000901_KanriKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst000901_KanriKbDictionary) )
			return false;
		String objStr = ((mst000901_KanriKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst000901_KanriKbDictionary DAI_GYOUSYU = new mst000901_KanriKbDictionary("1", "大業種");
	public static mst000901_KanriKbDictionary SYO_GYOUSYU = new mst000901_KanriKbDictionary("4", "小業種");
	public static mst000901_KanriKbDictionary HANKU = new mst000901_KanriKbDictionary("2", "販区");
	public static mst000901_KanriKbDictionary HINSYU = new mst000901_KanriKbDictionary("3", "品種");
	public static mst000901_KanriKbDictionary HANKU_SYOUHINCD = new mst000901_KanriKbDictionary("9", "販区＋商品コード");
	public static mst000901_KanriKbDictionary UNKNOWN = new mst000901_KanriKbDictionary("X","UNKNOWN");
//  ↓↓2006.06.09 xubq カスタマイズ修正↓↓
	public static mst000901_KanriKbDictionary BUMON = new mst000901_KanriKbDictionary("1", "部門");
	public static mst000901_KanriKbDictionary HINBAN = new mst000901_KanriKbDictionary("2", "品番");
	public static mst000901_KanriKbDictionary LINE = new mst000901_KanriKbDictionary("4", "ライン");
	public static mst000901_KanriKbDictionary UNIT = new mst000901_KanriKbDictionary("5", "ユニット");
//↑↑2006.06.09 xubq カスタマイズ修正↑↑

	
}
