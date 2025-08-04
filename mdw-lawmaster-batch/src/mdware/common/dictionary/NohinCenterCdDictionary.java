package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 納品データセンターコードディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author Otani
 * @version 1.00 (2005.06.20) 初版作成
 * @version 1.01 2005/07/08 M.Ikemoto  障害報告No.760対応
 */

public class NohinCenterCdDictionary {
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
	private NohinCenterCdDictionary(String code, String name) {
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
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinCenterCdDictionary getStatus(String key)
	{
		NohinCenterCdDictionary ret = (NohinCenterCdDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinCenterCdDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinCenterCdDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}
	//2005/07/08 M.Ikemoto 障害報告No.760対応 START
//	public static NohinCenterCdDictionary ASNTYPE2_CENTER_CD = new NohinCenterCdDictionary("9739","北広島");
	public static NohinCenterCdDictionary NOHIN_CENTER_CD = new NohinCenterCdDictionary("9739","北広島");
	//2005/07/08 M.Ikemoto 障害報告No.760対応 END
	public static NohinCenterCdDictionary UNKNOWN = new NohinCenterCdDictionary("X","UNKNOWN");

}