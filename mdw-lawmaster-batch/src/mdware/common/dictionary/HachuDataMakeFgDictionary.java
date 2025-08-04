package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注データ生成済みフラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author k.arai
 * @version 1.00 (2003.10.01) 初版作成
 */

public class HachuDataMakeFgDictionary {
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
	private HachuDataMakeFgDictionary(String code, String name) {
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
	 * @return HachuDataMakeFgDictionary
	 */
	public static HachuDataMakeFgDictionary getStatus(String key)
	{
		HachuDataMakeFgDictionary ret = (HachuDataMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static HachuDataMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static HachuDataMakeFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HachuDataMakeFgDictionary MI = new HachuDataMakeFgDictionary("0","未");
	public static HachuDataMakeFgDictionary SUMI = new HachuDataMakeFgDictionary("1","済");
	public static HachuDataMakeFgDictionary UNKNOWN = new HachuDataMakeFgDictionary("X","UNKNOWN");

}