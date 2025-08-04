package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注修正可フラグフラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author S.Yunba
 * @version 1.00 S.Yunba (2004.08.09) 初版作成
 */
public class HachuSyuseiFgDictionary {
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
	private HachuSyuseiFgDictionary(String code, String name) {
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
	 * @return HachuMakeFgDictionary
	 */
	public static HachuSyuseiFgDictionary getStatus(String key)
	{
		HachuSyuseiFgDictionary ret = (HachuSyuseiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMakeFgDictionary
	 */
	public static HachuSyuseiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMakeFgDictionary
	 */
	public static HachuSyuseiFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HachuSyuseiFgDictionary KA = new HachuSyuseiFgDictionary("0","可");
	public static HachuSyuseiFgDictionary FUKA = new HachuSyuseiFgDictionary("1","不可");
	public static HachuSyuseiFgDictionary UNKNOWN = new HachuSyuseiFgDictionary("X","UNKNOWN");

}