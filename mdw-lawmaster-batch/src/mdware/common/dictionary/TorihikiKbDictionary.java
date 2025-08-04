package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 取引区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author H.Okuno
 * @version 1.00 (2004.11.20) 初版作成
 */

public class TorihikiKbDictionary {
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
	private TorihikiKbDictionary(String code, String name) {
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
	 * @return TorihikiKbDictionary
	 */
	public static TorihikiKbDictionary getStatus(String key)
	{
		TorihikiKbDictionary ret = (TorihikiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorihikiKbDictionary
	 */
	public static TorihikiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorihikiKbDictionary
	 */
	public static TorihikiKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static TorihikiKbDictionary IPAN = new TorihikiKbDictionary("0112","一般仕入");
	public static TorihikiKbDictionary IDO = new TorihikiKbDictionary("0171","移動仕入");
	public static TorihikiKbDictionary UNKNOWN = new TorihikiKbDictionary("X","UNKNOWN");

}