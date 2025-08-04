/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 業種区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kikuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 業種区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @see なし
 */
public class mst000611_SystemKbDictionary {
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
	private mst000611_SystemKbDictionary(String code, String name) {
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
	public static mst000611_SystemKbDictionary getStatus(String key)
	{
		mst000611_SystemKbDictionary ret = (mst000611_SystemKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000611_SystemKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000611_SystemKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst000611_SystemKbDictionary) )
			return false;
		String objStr = ((mst000611_SystemKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst000611_SystemKbDictionary T = new mst000611_SystemKbDictionary("T", MessageUtil.getMessage("MST000611_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000611_SystemKbDictionary J = new mst000611_SystemKbDictionary("J", MessageUtil.getMessage("MST000611_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000611_SystemKbDictionary G = new mst000611_SystemKbDictionary("G", MessageUtil.getMessage("MST000611_TXT_00003",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000611_SystemKbDictionary F = new mst000611_SystemKbDictionary("F", MessageUtil.getMessage("MST000611_TXT_00004",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000611_SystemKbDictionary UNKNOWN = new mst000611_SystemKbDictionary("X","UNKNOWN");
}
