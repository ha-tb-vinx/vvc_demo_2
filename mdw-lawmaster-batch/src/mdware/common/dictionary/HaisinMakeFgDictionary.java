package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 配信作成フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba
 * @version 1.00 (2003.12.02) 初版作成
 */

public class HaisinMakeFgDictionary {
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
	private HaisinMakeFgDictionary(String code, String name) {
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
	 * @return HaisinMakeFgDictionary
	 */
	public static HaisinMakeFgDictionary getStatus(String key)
	{
		HaisinMakeFgDictionary ret = (HaisinMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisinMakeFgDictionary
	 */
	public static HaisinMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisinMakeFgDictionary
	 */
	public static HaisinMakeFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HaisinMakeFgDictionary MI = new HaisinMakeFgDictionary("0","未");
	public static HaisinMakeFgDictionary SUMI = new HaisinMakeFgDictionary("1","済");
	public static HaisinMakeFgDictionary UNKNOWN = new HaisinMakeFgDictionary("X","UNKNOWN");

}