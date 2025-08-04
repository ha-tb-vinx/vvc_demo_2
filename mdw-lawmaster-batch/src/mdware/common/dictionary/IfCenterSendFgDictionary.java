package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: センターシステム連携フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2021</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author KHAI.NN
 * @version 1.00 (2021.12.27) 初版作成
 */

public class IfCenterSendFgDictionary {
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
	private IfCenterSendFgDictionary(String code, String name) {
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
	 * @return IfCenterSendFgDictionary
	 */
	public static IfCenterSendFgDictionary getStatus(String key)
	{
		IfCenterSendFgDictionary ret = (IfCenterSendFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return IfCenterSendFgDictionary
	 */
	public static IfCenterSendFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return IfCenterSendFgDictionary
	 */
	public static IfCenterSendFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static IfCenterSendFgDictionary ALL = new IfCenterSendFgDictionary("0","全件");
	public static IfCenterSendFgDictionary DIFFERENCE = new IfCenterSendFgDictionary("1","差分連携");
	public static IfCenterSendFgDictionary UNKNOWN = new IfCenterSendFgDictionary("X","UNKNOWN");

}