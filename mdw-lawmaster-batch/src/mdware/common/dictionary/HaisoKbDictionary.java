package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 配送区分ディクショナリ(Host用)</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.08.18) 初版作成
 */

public class HaisoKbDictionary {
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
	private HaisoKbDictionary(String code, String name) {
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
	public static HaisoKbDictionary getStatus(String key)
	{
		HaisoKbDictionary ret = (HaisoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisoKbDictionary
	 */
	public static HaisoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HaisoKbDictionary
	 */
	public static HaisoKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HaisoKbDictionary SORYO = new HaisoKbDictionary("1","総量一括");
	public static HaisoKbDictionary TC = new HaisoKbDictionary("2","ＴＣ店別");
	public static HaisoKbDictionary ZAIKO_JISYA = new HaisoKbDictionary("3","在庫(自社)");
	public static HaisoKbDictionary ZAIKO_ITAKU = new HaisoKbDictionary("4","在庫(預託)");
	public static HaisoKbDictionary TENTYOKU = new HaisoKbDictionary("5","店直");
	public static HaisoKbDictionary UNKNOWN = new HaisoKbDictionary("X","UNKNOWN");
}
