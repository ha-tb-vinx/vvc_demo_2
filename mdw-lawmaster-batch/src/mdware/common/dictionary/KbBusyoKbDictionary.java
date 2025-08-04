package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 部署区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author hashimoto
 * @version 1.00 (2004.08.02) 初版作成
 */

public class KbBusyoKbDictionary {
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
	private KbBusyoKbDictionary(String code, String name) {
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
	 * @return KbBusyoKbDictionary
	 */
	public static KbBusyoKbDictionary getStatus(String key)
	{
		KbBusyoKbDictionary ret = (KbBusyoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KbBusyoKbDictionary
	 */
	public static KbBusyoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return KbBusyoKbDictionary
	 */
	public static KbBusyoKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static KbBusyoKbDictionary TAISYOGAI = new KbBusyoKbDictionary("00","発注対象外");
	public static KbBusyoKbDictionary HONBU = new KbBusyoKbDictionary("10","本部");
	public static KbBusyoKbDictionary TENPO = new KbBusyoKbDictionary("20","店舗");
	public static KbBusyoKbDictionary TENPO_CANCEL = new KbBusyoKbDictionary("21","店舗発注取消");
	public static KbBusyoKbDictionary TENPO_TO_HONBU = new KbBusyoKbDictionary("22","本部発注変更");
	public static KbBusyoKbDictionary MISIYOU = new KbBusyoKbDictionary("99","未使用");

	public static KbBusyoKbDictionary UNKNOWN = new KbBusyoKbDictionary("X","UNKNOWN");

}