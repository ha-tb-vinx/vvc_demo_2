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
public class mst008101_JidoHachuHonbuSuryoTeisiDictionary {
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
	private mst008101_JidoHachuHonbuSuryoTeisiDictionary(String code, String name) {
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
	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary getStatus(String key)
	{
		mst008101_JidoHachuHonbuSuryoTeisiDictionary ret = (mst008101_JidoHachuHonbuSuryoTeisiDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary getStatus(int key)
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
		if( !(obj instanceof mst008101_JidoHachuHonbuSuryoTeisiDictionary) )
			return false;
		String objStr = ((mst008101_JidoHachuHonbuSuryoTeisiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary TIKAN = new mst008101_JidoHachuHonbuSuryoTeisiDictionary("1", "置換");
	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary KASAN = new mst008101_JidoHachuHonbuSuryoTeisiDictionary("2", "加算");
	public static mst008101_JidoHachuHonbuSuryoTeisiDictionary UNKNOWN = new mst008101_JidoHachuHonbuSuryoTeisiDictionary("X","UNKNOWN");
}
