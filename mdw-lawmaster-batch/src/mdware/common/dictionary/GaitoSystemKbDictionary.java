package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: EDI対象区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003-2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author shimoyama
 * @version 1.00 (2004.10.12) 初版作成
 * @version 1.01 (2005.01.28) takata 変更依頼書No.505への対応
 */

public class GaitoSystemKbDictionary {
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
	private GaitoSystemKbDictionary(String code, String name) {
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
	public static GaitoSystemKbDictionary getStatus(String key)
	{
		GaitoSystemKbDictionary ret = (GaitoSystemKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static GaitoSystemKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return EdiKbDictionary
	 */
	public static GaitoSystemKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

// 2005/01/28 udpate takata 変更依頼書No.505への対応 begin 
//	public static GaitoSystemKbDictionary EOS = new GaitoSystemKbDictionary("1","EOS");
	public static GaitoSystemKbDictionary EOS = new GaitoSystemKbDictionary("1","EOS(住生活)");
	public static GaitoSystemKbDictionary TAG = new GaitoSystemKbDictionary("2","TAG");
	public static GaitoSystemKbDictionary GROCERY = new GaitoSystemKbDictionary("3","グロサリ");
//	public static GaitoSystemKbDictionary FRESH = new GaitoSystemKbDictionary("3","生鮮");
	public static GaitoSystemKbDictionary FRESH = new GaitoSystemKbDictionary("4","生鮮");
	public static GaitoSystemKbDictionary UNKNOWN = new GaitoSystemKbDictionary("X","UNKNOWN");
//	2005/01/28 udpate takata end
}