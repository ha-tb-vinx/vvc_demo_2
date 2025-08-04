package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: センター送信データ生成済フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003-2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yakushi
 * @version 1.00 (2005.06.20) 初版作成
 */

public class CenterSobaHachuFgDictionary {
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
	private CenterSobaHachuFgDictionary(String code, String name) {
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
	 * @return EdiKbDictionary
	 */
	public static CenterSobaHachuFgDictionary getStatus(String key)
	{
		CenterSobaHachuFgDictionary ret = (CenterSobaHachuFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static CenterSobaHachuFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static CenterSobaHachuFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static CenterSobaHachuFgDictionary MI = new CenterSobaHachuFgDictionary("0","未作成");
	public static CenterSobaHachuFgDictionary SUMI = new CenterSobaHachuFgDictionary("1","作成済");
	public static CenterSobaHachuFgDictionary UNKNOWN = new CenterSobaHachuFgDictionary("X","UNKNOWN");

}