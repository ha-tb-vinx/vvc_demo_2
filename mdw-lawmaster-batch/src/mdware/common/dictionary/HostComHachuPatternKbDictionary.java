package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注パターン区分ディクショナリ(Host用(一般商品))</p>
 * <p>説明:発注兼品揃データの発注パターン区分(一般商品)にのみ対応</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.09.20) 初版作成
 */

public class HostComHachuPatternKbDictionary {
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
	private HostComHachuPatternKbDictionary(String code, String name) {
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
	 * @return HostComHachuPatternKbDictionary
	 */
	public static HostComHachuPatternKbDictionary getStatus(String key)
	{
		HostComHachuPatternKbDictionary ret = (HostComHachuPatternKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HostComHachuPatternKbDictionary
	 */
	public static HostComHachuPatternKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HostComHachuPatternKbDictionary
	 */
	public static HostComHachuPatternKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HostComHachuPatternKbDictionary COM_SIME_12 = new HostComHachuPatternKbDictionary("1","12:00〆");
	public static HostComHachuPatternKbDictionary COM_SIME_20 = new HostComHachuPatternKbDictionary("2","20:00〆");
	public static HostComHachuPatternKbDictionary UNKNOWN = new HostComHachuPatternKbDictionary("X","UNKNOWN");

}