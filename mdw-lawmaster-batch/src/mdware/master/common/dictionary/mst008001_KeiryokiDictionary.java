/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 計量器ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author T.Ueda
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 計量器ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author T.Ueda
 * @version 1.0
 * @see なし								
 */
public class mst008001_KeiryokiDictionary {
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
	private mst008001_KeiryokiDictionary(String code, String name) {
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
	public static mst008001_KeiryokiDictionary getStatus(String key)
	{
		mst008001_KeiryokiDictionary ret = (mst008001_KeiryokiDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008001_KeiryokiDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008001_KeiryokiDictionary getStatus(int key)
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
		if( !(obj instanceof mst008001_KeiryokiDictionary) )
			return false;
		String objStr = ((mst008001_KeiryokiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst008001_KeiryokiDictionary NOUSAN = new mst008001_KeiryokiDictionary("1", "農産");
	public static mst008001_KeiryokiDictionary SOUZAI = new mst008001_KeiryokiDictionary("2", "惣菜");
	public static mst008001_KeiryokiDictionary SUISAN = new mst008001_KeiryokiDictionary("3", "水産");
	public static mst008001_KeiryokiDictionary TIKUSAN = new mst008001_KeiryokiDictionary("4", "畜産");
	public static mst008001_KeiryokiDictionary MANUFACTURER1 = new mst008001_KeiryokiDictionary("1", "イシダ");
	public static mst008001_KeiryokiDictionary MANUFACTURER2 = new mst008001_KeiryokiDictionary("2", "寺岡");
	public static mst008001_KeiryokiDictionary DGYOSYU = new mst008001_KeiryokiDictionary("0033","食品");
	public static mst008001_KeiryokiDictionary GROSSARY = new mst008001_KeiryokiDictionary("0087","グロッサリー");
	public static mst008001_KeiryokiDictionary UNKNOWN = new mst008001_KeiryokiDictionary("X","UNKNOWN");
}
