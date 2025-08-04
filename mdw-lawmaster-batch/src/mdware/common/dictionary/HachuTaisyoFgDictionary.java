package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注対象フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2003.10.01) 初版作成
 */

public class HachuTaisyoFgDictionary {
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
	private HachuTaisyoFgDictionary(String code, String name) {
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
	 * @return HachuTaisyoFgDictionary
	 */
	public static HachuTaisyoFgDictionary getStatus(String key)
	{
		HachuTaisyoFgDictionary ret = (HachuTaisyoFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuTaisyoFgDictionary
	 */
	public static HachuTaisyoFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuTaisyoFgDictionary
	 */
	public static HachuTaisyoFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HachuTaisyoFgDictionary TAISYOGAI = new HachuTaisyoFgDictionary("0","発注対象外");
	public static HachuTaisyoFgDictionary TAISYO = new HachuTaisyoFgDictionary("1","発注対象");
	public static HachuTaisyoFgDictionary UNKNOWN = new HachuTaisyoFgDictionary("X","UNKNOWN");

}