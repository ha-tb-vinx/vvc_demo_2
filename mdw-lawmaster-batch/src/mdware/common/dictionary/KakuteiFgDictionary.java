package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 確定フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2003.10.17) 初版作成
 */

public class KakuteiFgDictionary {
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
	private KakuteiFgDictionary(String code, String name) {
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
	 * @return KakuteiFgDictionary
	 */
	public static KakuteiFgDictionary getStatus(String key)
	{
		KakuteiFgDictionary ret = (KakuteiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KakuteiFgDictionary
	 */
	public static KakuteiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KakuteiFgDictionary
	 */
	public static KakuteiFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static KakuteiFgDictionary MI = new KakuteiFgDictionary("0","未");
	public static KakuteiFgDictionary SUMI = new KakuteiFgDictionary("1","済");
	public static KakuteiFgDictionary UNKNOWN = new KakuteiFgDictionary("X","UNKNOWN");

}