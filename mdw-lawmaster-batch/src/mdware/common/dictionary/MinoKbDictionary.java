package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 未納理由区分ディクショナリ</p>
 * <p>説明:未納理由区分の値を保持します。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author kirihara
 * @version 1.00 (2004.08.23) 初版作成
 */

public class MinoKbDictionary {
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
	private MinoKbDictionary(String code, String name) {
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
	public static MinoKbDictionary getStatus(String key)
	{
		MinoKbDictionary ret = (MinoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KakuteiFgDictionary
	 */
	public static MinoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KakuteiFgDictionary
	 */
	public static MinoKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static MinoKbDictionary S_OKURE = new MinoKbDictionary("0","メーカー発注手配遅れ");
	public static MinoKbDictionary S_KEPIN = new MinoKbDictionary("1","メーカー欠品");
	public static MinoKbDictionary S_FUBI = new MinoKbDictionary("2","取引先マスタ不備");
	public static MinoKbDictionary R_SYURYO = new MinoKbDictionary("3","販売期間終了");
	public static MinoKbDictionary R_TENMIS = new MinoKbDictionary("4","店発注ミス");
	public static MinoKbDictionary R_HONBUMIS = new MinoKbDictionary("5","本部発注ミス");
	public static MinoKbDictionary UNKNOWN = new MinoKbDictionary("X","UNKNOWN");

}