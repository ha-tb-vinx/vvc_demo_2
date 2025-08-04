/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 採番管理区分ディクショナリ</p>
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
 * <P>説明 : 採番管理区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst004401_SaibanKanriKbDictionary {
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
	private mst004401_SaibanKanriKbDictionary(String code, String name) {
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
	public static mst004401_SaibanKanriKbDictionary getStatus(String key)
	{
		mst004401_SaibanKanriKbDictionary ret = (mst004401_SaibanKanriKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst004401_SaibanKanriKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst004401_SaibanKanriKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst004401_SaibanKanriKbDictionary) )
			return false;
		String objStr = ((mst004401_SaibanKanriKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst004401_SaibanKanriKbDictionary WAKUGAI = new mst004401_SaibanKanriKbDictionary("*", "枠外で使用中");
	public static mst004401_SaibanKanriKbDictionary KARA = new mst004401_SaibanKanriKbDictionary("NULL", "空");
	public static mst004401_SaibanKanriKbDictionary JIDOU_KARI = new mst004401_SaibanKanriKbDictionary("3", "自動採番（仮登録）");
	public static mst004401_SaibanKanriKbDictionary JIDOU_HON = new mst004401_SaibanKanriKbDictionary("1", "自動採番（本登録）");
	public static mst004401_SaibanKanriKbDictionary SYUDOU = new mst004401_SaibanKanriKbDictionary("2", "手動採番");
	public static mst004401_SaibanKanriKbDictionary HAIBAN = new mst004401_SaibanKanriKbDictionary("9", "廃番");
	public static mst004401_SaibanKanriKbDictionary SYUDOU_KARI = new mst004401_SaibanKanriKbDictionary("4", "手動採番（仮登録）");
	public static mst004401_SaibanKanriKbDictionary UNKNOWN = new mst004401_SaibanKanriKbDictionary("X","UNKNOWN");
}
