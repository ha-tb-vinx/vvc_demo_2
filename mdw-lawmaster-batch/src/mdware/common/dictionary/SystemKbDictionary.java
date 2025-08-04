package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: システム区分データディクショナリ</p>
 * <p>説明　　: </p>
 * <p>著作権　: Copyright (c) 2004</p>
 * <p>会社名　: Vinculum Japan Corp.</p>
 * @author H.Okuno
 * @version 1.0
 */

public class SystemKbDictionary {
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
	private SystemKbDictionary(String code, String name) {
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
	 * @return SystemKbDictionary
	 */
	public static SystemKbDictionary getStatus(String key)
	{
		SystemKbDictionary ret = (SystemKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SystemKbDictionary
	 */
	public static SystemKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SystemKbDictionary
	 */
	public static SystemKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals( Object obj )
	{
		if( !(obj instanceof SystemKbDictionary) )
			return false;
		String objStr = ((SystemKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
//マスタ
	public static SystemKbDictionary EOS_MASTER = new SystemKbDictionary("L", "EOS（住生活）");
	public static SystemKbDictionary TAG_MASTER = new SystemKbDictionary("A", "TAG(衣料）");
	public static SystemKbDictionary GLOSSALY_MASTER = new SystemKbDictionary("G", "グロサリ");
	public static SystemKbDictionary SEISEN_MASTER = new SystemKbDictionary("F", "生鮮");
	public static SystemKbDictionary UNKNOWN_MASTER = new SystemKbDictionary("X","UNKNOWN");
//発注データ（BR_DT_HACHU、DT_HACHU）
	public static SystemKbDictionary EOS_HACHU = new SystemKbDictionary("1", "EOS（住生活）");
	public static SystemKbDictionary TAG_HACHU = new SystemKbDictionary("2", "TAG(衣料）");
	public static SystemKbDictionary GLOSSALY_HACHU = new SystemKbDictionary("3", "グロサリ");
	public static SystemKbDictionary SEISEN_HACHU = new SystemKbDictionary("4", "生鮮");
	public static SystemKbDictionary UNKNOWN_HACHU = new SystemKbDictionary("0","UNKNOWN");
//納品管理データ（BR_TRS_NOHIN_KANRI）
	public static SystemKbDictionary EOS_NOHIN_KANRI = new SystemKbDictionary("01", "EOS（住生活）");
	public static SystemKbDictionary TAG_NOHIN_KANRI = new SystemKbDictionary("21", "TAG(衣料）");
	public static SystemKbDictionary GLOSSALY_NOHIN_KANRI = new SystemKbDictionary("01", "グロサリ");
	public static SystemKbDictionary SEISEN_NOHIN_KANRI = new SystemKbDictionary("22", "生鮮");
	public static SystemKbDictionary UNKNOWN_NOHIN_KANRI = new SystemKbDictionary("00","UNKNOWN");

	public static SystemKbDictionary UNKNOWN = new SystemKbDictionary("X","UNKNOWN");
}
