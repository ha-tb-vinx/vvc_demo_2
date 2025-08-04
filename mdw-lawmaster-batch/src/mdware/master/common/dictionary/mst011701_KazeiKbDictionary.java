/**
 * <P>タイトル : mst007501_ZeiKbDictionary</p>
 * <P>説明 : 税区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <P>タイトル : mst007501_ZeiKbDictionary</p>
 * <P>説明 : 課税区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @see なし
 */
public class mst011701_KazeiKbDictionary {
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
	private mst011701_KazeiKbDictionary(String code, String name) {
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
	public static mst011701_KazeiKbDictionary getStatus(String key)
	{
		mst011701_KazeiKbDictionary ret = (mst011701_KazeiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011701_KazeiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011701_KazeiKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst011701_KazeiKbDictionary) )
			return false;
		String objStr = ((mst011701_KazeiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst011701_KazeiKbDictionary SOTOZEI 	= new mst011701_KazeiKbDictionary("0", MessageUtil.getMessage("MST011701_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst011701_KazeiKbDictionary UTIZEI 	= new mst011701_KazeiKbDictionary("1", MessageUtil.getMessage("MST011701_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst011701_KazeiKbDictionary HIKAZEI 	= new mst011701_KazeiKbDictionary("3", MessageUtil.getMessage("MST011701_TXT_00003",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst011701_KazeiKbDictionary UNKNOWN = new mst011701_KazeiKbDictionary("X","UNKNOWN");
}
