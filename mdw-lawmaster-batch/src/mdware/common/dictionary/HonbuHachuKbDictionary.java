package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 本部発注区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2004.08.19) 初版作成
 */

public class HonbuHachuKbDictionary {
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
	private HonbuHachuKbDictionary(String code, String name) {
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
	 * @return HonbuHachuKbDictionary
	 */
	public static HonbuHachuKbDictionary getStatus(String key)
	{
		HonbuHachuKbDictionary ret = (HonbuHachuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HonbuHachuKbDictionary
	 */
	public static HonbuHachuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HonbuHachuKbDictionary
	 */
	public static HonbuHachuKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HonbuHachuKbDictionary NASI = new HonbuHachuKbDictionary("0","本部発注なし");
	public static HonbuHachuKbDictionary KA_ARI = new HonbuHachuKbDictionary("1","本部発注修正可あり");
	public static HonbuHachuKbDictionary FUKA_ARI = new HonbuHachuKbDictionary("2","本部発注修正不可あり");
	public static HonbuHachuKbDictionary KONZAI = new HonbuHachuKbDictionary("3","本部発注修正可不可混在");
	public static HonbuHachuKbDictionary UNKNOWN = new HonbuHachuKbDictionary("X","UNKNOWN");

}