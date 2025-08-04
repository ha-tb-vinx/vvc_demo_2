/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : ユニットプライス単位区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kikuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : ユニットプライス単位区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst002501_UnitPriceKbDictionary {
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
	private mst002501_UnitPriceKbDictionary(String code, String name) {
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
	public static mst002501_UnitPriceKbDictionary getStatus(String key)
	{
		mst002501_UnitPriceKbDictionary ret = (mst002501_UnitPriceKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst002501_UnitPriceKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst002501_UnitPriceKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst002501_UnitPriceKbDictionary) )
			return false;
		String objStr = ((mst002501_UnitPriceKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst002501_UnitPriceKbDictionary NASI = new mst002501_UnitPriceKbDictionary("0", "計算しない");
	public static mst002501_UnitPriceKbDictionary GRAM = new mst002501_UnitPriceKbDictionary("1", "グラム");
	public static mst002501_UnitPriceKbDictionary MILLILITER = new mst002501_UnitPriceKbDictionary("2", "ミリリットル");
////	↓↓追加（2005.08.02）↓↓
	public static mst002501_UnitPriceKbDictionary KILOGRAM = new mst002501_UnitPriceKbDictionary("02", "キログラム");
//	public static mst002501_UnitPriceKbDictionary MILLILITER = new mst002501_UnitPriceKbDictionary("03", "ミリリットル");
//	public static mst002501_UnitPriceKbDictionary LITRE = new mst002501_UnitPriceKbDictionary("04", "リットル");
//	public static mst002501_UnitPriceKbDictionary MAI = new mst002501_UnitPriceKbDictionary("07", "枚");
//	public static mst002501_UnitPriceKbDictionary KO = new mst002501_UnitPriceKbDictionary("08", "個");
//	public static mst002501_UnitPriceKbDictionary CASE = new mst002501_UnitPriceKbDictionary("09", "ケース");
//	public static mst002501_UnitPriceKbDictionary BOWL = new mst002501_UnitPriceKbDictionary("10", "ボール");
//	public static mst002501_UnitPriceKbDictionary BI = new mst002501_UnitPriceKbDictionary("11", "尾");
////	↑↑追加（2005.08.02）↑↑
	public static mst002501_UnitPriceKbDictionary UNKNOWN = new mst002501_UnitPriceKbDictionary("X","UNKNOWN");
}
