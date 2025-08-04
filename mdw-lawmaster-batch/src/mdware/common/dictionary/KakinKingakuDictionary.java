package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 課金料金ディクショナリ</p>
 * <p>説明: 
 * </p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hirai
 * @version 1.00 (2005.07.15) 初版作成
 * @version 1.10 (2005.09.05) hirai 変更依頼書No.624 月額使用料を5000→5250
 * @version 1.11 (2005.09.26) yakushi 変更依頼書No.627 月額使用料を5250→5000 元に戻す
 */

public class KakinKingakuDictionary {
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
	private KakinKingakuDictionary(String code, String name) {
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
	public static KakinKingakuDictionary getStatus(String key)
	{
		KakinKingakuDictionary ret = (KakinKingakuDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinSyoriKbDictionary
	 */
	public static KakinKingakuDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return NohinSyoriKbDictionary
	 */
	public static KakinKingakuDictionary getStatus(int key)
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
		if( !(obj instanceof KakinKingakuDictionary) )
			return false;
		String objStr = ((KakinKingakuDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	public static KakinKingakuDictionary RECORD = new KakinKingakuDictionary("3", "課金金額（１０行）");  //課金金額(3円/10行）
//(2005.09.05) hirai 変更依頼書No.624 start→(2005.09.26) yakushi 変更依頼書No.627より元に戻す
    public static KakinKingakuDictionary MONTHLY = new KakinKingakuDictionary("5000", "月額使用料"); //月額使用料 5000円
	//public static KakinKingakuDictionary MONTHLY = new KakinKingakuDictionary("5250", "月額使用料"); //月額使用料 5250円
//	(2005.09.05) hirai 変更依頼書No.624 end
    public static KakinKingakuDictionary UNKNOWN = new KakinKingakuDictionary("X","UNKNOWN");
}