package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 取引区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author S.Shimatani
 * @version 1.00 (2004.12.29) 初版作成
 */

public class SyoriJyokyoFgDictionary {
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
	private SyoriJyokyoFgDictionary(String code, String name) {
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
	public static SyoriJyokyoFgDictionary getStatus(String key)
	{
		SyoriJyokyoFgDictionary ret = (SyoriJyokyoFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorihikiKbDictionary
	 */
	public static SyoriJyokyoFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TorihikiKbDictionary
	 */
	public static SyoriJyokyoFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static SyoriJyokyoFgDictionary MIKAKUTEI = new SyoriJyokyoFgDictionary("0","未確定");
	public static SyoriJyokyoFgDictionary TORIHIKISAKI_KAKUTEI = new SyoriJyokyoFgDictionary("1","取引先確定済（小売未確定）");
	public static SyoriJyokyoFgDictionary KOURI_KAKUTEI = new SyoriJyokyoFgDictionary("2","小売確定済");
	public static SyoriJyokyoFgDictionary KEIJO_SUMI = new SyoriJyokyoFgDictionary("3","計上済");
	public static SyoriJyokyoFgDictionary SAKUJO = new SyoriJyokyoFgDictionary("4","削除");
	public static SyoriJyokyoFgDictionary UNKNOWN = new SyoriJyokyoFgDictionary("X","UNKNOWN");

}