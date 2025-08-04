package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: PT区分ディクショナリ(Host用)</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.08.18) 初版作成
 */

public class PTKbDictionary {
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
	private PTKbDictionary(String code, String name) {
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
	 * @return HaisoKbDictionary
	 */
	public static PTKbDictionary getStatus(String key)
	{
		PTKbDictionary ret = (PTKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisoKbDictionary
	 */
	public static PTKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisoKbDictionary
	 */
	public static PTKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static PTKbDictionary TC = new PTKbDictionary("3","ＴＣ通過");	//配送区分＝２の場合
	public static PTKbDictionary TENTYOKU = new PTKbDictionary("4","店直");	//配送区分＝５の場合
	public static PTKbDictionary UNKNOWN = new PTKbDictionary("X","UNKNOWN");
}
