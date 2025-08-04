package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 部署区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2003.10.01) 初版作成
 */

public class BusyoKbDictionary {
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
	private BusyoKbDictionary(String code, String name) {
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
	 * @return BusyoKbDictionary
	 */
	public static BusyoKbDictionary getStatus(String key)
	{
		BusyoKbDictionary ret = (BusyoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BusyoKbDictionary
	 */
	public static BusyoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BusyoKbDictionary
	 */
	public static BusyoKbDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static BusyoKbDictionary TAISYOGAI = new BusyoKbDictionary("00","発注対象外");
	public static BusyoKbDictionary HONBU = new BusyoKbDictionary("10","本部");
	public static BusyoKbDictionary TENPO = new BusyoKbDictionary("20","店舗");
	public static BusyoKbDictionary TENPO_CANCEL = new BusyoKbDictionary("21","発注対象外");
	public static BusyoKbDictionary TENPO_TO_HONBU = new BusyoKbDictionary("22","本部");
	public static BusyoKbDictionary UNUSED = new BusyoKbDictionary("99","未使用");
	public static BusyoKbDictionary UNKNOWN = new BusyoKbDictionary("X","UNKNOWN");

}