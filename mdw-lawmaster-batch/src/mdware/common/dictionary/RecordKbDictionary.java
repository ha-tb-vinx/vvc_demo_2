package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: レコード区分ディクショナリ</p>
 * <p>説明: 
 * </p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hirai
 * @version 1.00 (2004.07.15) 初版作成
 */

public class RecordKbDictionary {
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
	private RecordKbDictionary(String code, String name) {
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
	 * @return NohinSyoriKbDictionary
	 */
	public static RecordKbDictionary getStatus(String key)
	{
		RecordKbDictionary ret = (RecordKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinSyoriKbDictionary
	 */
	public static RecordKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinSyoriKbDictionary
	 */
	public static RecordKbDictionary getStatus(int key)
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
		if( !(obj instanceof RecordKbDictionary) )
			return false;
		String objStr = ((RecordKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static RecordKbDictionary TEN_GOUKEI = new RecordKbDictionary("1", "店合計");
	public static RecordKbDictionary TEN_MEISAI = new RecordKbDictionary("2", "店明細");

    public static RecordKbDictionary UNKNOWN = new RecordKbDictionary("X","UNKNOWN");

}