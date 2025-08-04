/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 削除フラグディクショナリ</p>
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
 * <P>タイトル : Emg Flag</p>
 * <P>説明 : 緊急配信フラグディクショナリ</p>
 * <p>著作権: Copyright (c) 2015</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2015.10.14) NGUYEN.NTM FIVIMART対応
 */
public class mst910020_EmgFlagDictionary {
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
	private mst910020_EmgFlagDictionary(String code, String name) {
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
	 * @return mst910020_EmgFlagDictionary
	 */
	public static mst910020_EmgFlagDictionary getStatus(String key)
	{
		mst910020_EmgFlagDictionary ret = (mst910020_EmgFlagDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return mst910020_EmgFlagDictionary
	 */
	public static mst910020_EmgFlagDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return mst910020_EmgFlagDictionary
	 */
	public static mst910020_EmgFlagDictionary getStatus(int key)
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
		if( !(obj instanceof mst910020_EmgFlagDictionary) )
			return false;
		String objStr = ((mst910020_EmgFlagDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst910020_EmgFlagDictionary ON = new mst910020_EmgFlagDictionary("1", "ON");
	public static mst910020_EmgFlagDictionary OFF = new mst910020_EmgFlagDictionary("0", "OFF");
	public static mst910020_EmgFlagDictionary UNKNOWN = new mst910020_EmgFlagDictionary("X","UNKNOWN");
}
