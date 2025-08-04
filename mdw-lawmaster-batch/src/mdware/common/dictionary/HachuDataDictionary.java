package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 発注データ区分ディクショナリ</p>
 * <p>説明: 発注データ区分に必要なデータディクショナリを定義します
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kawata
 * @version 1.00 kawata 2004/07/30 新規作成
 */

public class HachuDataDictionary {
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
	private HachuDataDictionary(String code, String name) {
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
	 * @return HachuDataDictionary
	 */
	public static HachuDataDictionary getStatus(String key)
	{
		HachuDataDictionary ret = (HachuDataDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataDictionary
	 */
	public static HachuDataDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataDictionary
	 */
	public static HachuDataDictionary getStatus(int key)
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
		if( !(obj instanceof HachuDataDictionary) )
			return false;
		String objStr = ((HachuDataDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static HachuDataDictionary SYOHIN_BETU_HACHU = new HachuDataDictionary("0","商品別受注");
	public static HachuDataDictionary HACHU = new HachuDataDictionary("1","受注");
    public static HachuDataDictionary UNKNOWN = new HachuDataDictionary("X","UNKNOWN");

}
