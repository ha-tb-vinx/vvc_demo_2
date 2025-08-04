package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 発注パターン区分ディクショナリ(Host用)</p>
 * <p>説明:発注兼品揃データの発注パターン区分にのみ対応</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.08.18) 初版作成
 * @version 1.01 (2004.09.18) 障害報告書No.135対応 kirihara 発注パターン区分の文言変更。
 */

public class HostHachuPatternKbDictionary {
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
	private HostHachuPatternKbDictionary(String code, String name) {
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
	 * @return HostHachuPatternKbDictionary
	 */
	public static HostHachuPatternKbDictionary getStatus(String key)
	{
		HostHachuPatternKbDictionary ret = (HostHachuPatternKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HostHachuPatternKbDictionary
	 */
	public static HostHachuPatternKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HostHachuPatternKbDictionary
	 */
	public static HostHachuPatternKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HostHachuPatternKbDictionary ZEN_18 = new HostHachuPatternKbDictionary("0","18:00〆前日");
// 20040918 kirihara mod 障害報告書No.135対応 ↓
//	public static HostHachuPatternKbDictionary ZEN_20 = new HostHachuPatternKbDictionary("1","20:00〆前日（生鮮相場）");
	public static HostHachuPatternKbDictionary ZEN_20 = new HostHachuPatternKbDictionary("1","18:00〆前日");
// 20040918 kirihara mod 障害報告書No.135対応 ↑
	public static HostHachuPatternKbDictionary ZENZEN_12_SEISEN = new HostHachuPatternKbDictionary("2","12:00〆前々日");
	public static HostHachuPatternKbDictionary ZEN_12 = new HostHachuPatternKbDictionary("3","12:00〆前日");
	public static HostHachuPatternKbDictionary ZEN_15 = new HostHachuPatternKbDictionary("4","15:00〆前日");
	public static HostHachuPatternKbDictionary ZENZEN_12_KAKAKU = new HostHachuPatternKbDictionary("5","12:00〆前々日（価格変更）");
	public static HostHachuPatternKbDictionary ZENZEN_22_KAKAKU = new HostHachuPatternKbDictionary("6","22:00〆前々日（価格変更）");
	public static HostHachuPatternKbDictionary ZENZEN_22_NOSAN = new HostHachuPatternKbDictionary("7","22:00〆前々日（農産相場）");
	public static HostHachuPatternKbDictionary ZENZEN_22 = new HostHachuPatternKbDictionary("8","22:00〆前々日");
	public static HostHachuPatternKbDictionary ZENZEN_18 = new HostHachuPatternKbDictionary("9","18:00〆前々日");
	public static HostHachuPatternKbDictionary UNKNOWN = new HostHachuPatternKbDictionary("X","UNKNOWN");

}