package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: EDI対象区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003-2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2004.08.16) 初版作成
 */

public class EdiKbDictionary {
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
	private EdiKbDictionary(String code, String name) {
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
	public static EdiKbDictionary getStatus(String key)
	{
		EdiKbDictionary ret = (EdiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static EdiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static EdiKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static EdiKbDictionary TAISYOGAI = new EdiKbDictionary("0","EDI対象外");
	public static EdiKbDictionary HACHU_NOHIN_TAISYO = new EdiKbDictionary("1","発注納品対象");
	public static EdiKbDictionary NOHIN_TAISYO = new EdiKbDictionary("2","納品のみ対象");
	public static EdiKbDictionary UNKNOWN = new EdiKbDictionary("X","UNKNOWN");

}