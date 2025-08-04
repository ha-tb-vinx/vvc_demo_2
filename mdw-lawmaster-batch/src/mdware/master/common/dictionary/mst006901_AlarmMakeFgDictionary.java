/**
 * <P>タイトル : mst006901_AlarmMakeFgDictionary</p>
 * <P>説明 : アラーム生成フラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import mdware.common.resorces.util.ResorceUtil;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;

/**
 * <P>タイトル : mst006901_AlarmMakeFgDictionary</p>
 * <P>説明 : アラーム生成フラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @see なし
 */
public class mst006901_AlarmMakeFgDictionary {
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
	private mst006901_AlarmMakeFgDictionary(String code, String name) {
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
	public static mst006901_AlarmMakeFgDictionary getStatus(String key)
	{
		mst006901_AlarmMakeFgDictionary ret = (mst006901_AlarmMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006901_AlarmMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006901_AlarmMakeFgDictionary getStatus(int key)
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
		if( !(obj instanceof mst006901_AlarmMakeFgDictionary) )
			return false;
		String objStr = ((mst006901_AlarmMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst006901_AlarmMakeFgDictionary MI = new mst006901_AlarmMakeFgDictionary("0", MessageUtil.getMessage("MST006901_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst006901_AlarmMakeFgDictionary ZUMI = new mst006901_AlarmMakeFgDictionary("1", MessageUtil.getMessage("MST006901_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst006901_AlarmMakeFgDictionary UNKNOWN = new mst006901_AlarmMakeFgDictionary("X","UNKNOWN");
}
