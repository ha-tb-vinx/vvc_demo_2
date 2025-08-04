package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 相場発注制限時間ディクショナリ</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 * @version 1.1 20050704 hirai 変更依頼書No.558 ＥＤＩ利用可能時刻を14:30→15:30に変更。
 */

public class SobaSeigenJikanDictionary {
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
	private SobaSeigenJikanDictionary(String code, String name) {
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
	 * @return TaisyoKbDictionary
	 */
	public static SobaSeigenJikanDictionary getStatus(String key)
	{
		SobaSeigenJikanDictionary ret = (SobaSeigenJikanDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static SobaSeigenJikanDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static SobaSeigenJikanDictionary getStatus(int key)
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
		if( !(obj instanceof SobaSeigenJikanDictionary) )
			return false;
		String objStr = ((SobaSeigenJikanDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
//20050704 mod hirai 変更依頼書No.558 start
//	public static SobaSeigenJikanDictionary EDI_TIME = new SobaSeigenJikanDictionary("1430", "EDI相場発注制限時間(14:30)");
	public static SobaSeigenJikanDictionary EDI_TIME = new SobaSeigenJikanDictionary("1530", "EDI相場発注制限時間(15:30)");
//20050704 mod hirai 変更依頼書No.558 end
	public static SobaSeigenJikanDictionary EOB_TIME = new SobaSeigenJikanDictionary("1530", "EOB相場発注制限時間(15:30)");
	public static SobaSeigenJikanDictionary UNKNOWN = new SobaSeigenJikanDictionary("X","UNKNOWN");
}