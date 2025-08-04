package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 受注リスト出力区分データディクショナリ</p>
 * <p>説明: 
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hashimoto
 * @version 1.00 (2004.08.05) 初版作成
 */

public class JuchuListOutputKbDictionary {
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
	private JuchuListOutputKbDictionary(String code, String name) {
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
	 * @return JuchuListOutputKbDictionary
	 */
	public static JuchuListOutputKbDictionary getStatus(String key)
	{
		JuchuListOutputKbDictionary ret = (JuchuListOutputKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return JuchuListOutputKbDictionary
	 */
	public static JuchuListOutputKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return JuchuListOutputKbDictionary
	 */
	public static JuchuListOutputKbDictionary getStatus(int key)
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
		if( !(obj instanceof JuchuListOutputKbDictionary) )
			return false;
		String objStr = ((JuchuListOutputKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static JuchuListOutputKbDictionary MISYUTURYOKU = new JuchuListOutputKbDictionary("0", "未出力");
	public static JuchuListOutputKbDictionary SYUTURYOKUZUMI = new JuchuListOutputKbDictionary("1", "出力済");

    public static JuchuListOutputKbDictionary UNKNOWN = new JuchuListOutputKbDictionary("X","UNKNOWN");

}