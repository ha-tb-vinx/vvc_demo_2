package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 計上データ生成済みフラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author Shimatani
 * @version 1.00 (2004.12.01) 初版作成
 */

public class KeijoDataMakeFgDictionary {
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
	private KeijoDataMakeFgDictionary(String code, String name) {
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
	public static KeijoDataMakeFgDictionary getStatus(String key)
	{
		KeijoDataMakeFgDictionary ret = (KeijoDataMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static KeijoDataMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static KeijoDataMakeFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static KeijoDataMakeFgDictionary MI = new KeijoDataMakeFgDictionary("0","未");
	public static KeijoDataMakeFgDictionary SUMI = new KeijoDataMakeFgDictionary("1","済");
	public static KeijoDataMakeFgDictionary SYORITYU = new KeijoDataMakeFgDictionary("9","計上処理中");
	public static KeijoDataMakeFgDictionary UNKNOWN = new KeijoDataMakeFgDictionary("X","UNKNOWN");

}