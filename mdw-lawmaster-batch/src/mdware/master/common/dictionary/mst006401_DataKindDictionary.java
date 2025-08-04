/**
 * <P>タイトル : mst006401_DataKindDictionary</p>
 * <P>説明 : ファイル種別ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : mst006401_DataKindDictionary</p>
 * <P>説明 : ファイル種別ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst006401_DataKindDictionary {
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
	private mst006401_DataKindDictionary(String code, String name) {
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
	public static mst006401_DataKindDictionary getStatus(String key)
	{
		mst006401_DataKindDictionary ret = (mst006401_DataKindDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006401_DataKindDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006401_DataKindDictionary getStatus(int key)
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
		if( !(obj instanceof mst006401_DataKindDictionary) )
			return false;
		String objStr = ((mst006401_DataKindDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

//  ↓↓2006.06.14 xubq カスタマイズ修正↓↓
	public static mst006401_DataKindDictionary SYOHIN = new mst006401_DataKindDictionary("01", "商品マスタ");
//	public static mst006401_DataKindDictionary ASSORT = new mst006401_DataKindDictionary("02", "アソートメント");
//	public static mst006401_DataKindDictionary TANPINTEN = new mst006401_DataKindDictionary("03", "単品店");
//	public static mst006401_DataKindDictionary REIGAI = new mst006401_DataKindDictionary("04", "店別例外");
//	public static mst006401_DataKindDictionary SYOKAI = new mst006401_DataKindDictionary("05", "初回導入");
	public static mst006401_DataKindDictionary UNKNOWN = new mst006401_DataKindDictionary("X","UNKNOWN");
//	public static mst006401_DataKindDictionary TANPINTEN = new mst006401_DataKindDictionary("02", "単品店");
//	public static mst006401_DataKindDictionary REIGAI = new mst006401_DataKindDictionary("03", "店別例外");
//	public static mst006401_DataKindDictionary SYOKAI = new mst006401_DataKindDictionary("04", "初回導入");
	public static mst006401_DataKindDictionary KEIRYOKI  = new mst006401_DataKindDictionary("02", "計量器");
	public static mst006401_DataKindDictionary GIFT      = new mst006401_DataKindDictionary("03", "ギフト");
	public static mst006401_DataKindDictionary REIGAI    = new mst006401_DataKindDictionary("04", "店別例外");
	public static mst006401_DataKindDictionary TANPINTEN = new mst006401_DataKindDictionary("05", "単品店");
	public static mst006401_DataKindDictionary SYOKAI    = new mst006401_DataKindDictionary("06", "初回導入");
//  ↑↑2006.06.14 xubq カスタマイズ修正↑↑
//	===== Begin ===== Add By Kou 2006/7/8 使われていないクラスにもコンパイルエラー出ないようにするため =====
	public static mst006401_DataKindDictionary ASSORT = new mst006401_DataKindDictionary("99", "アソートメント");
//	===== End ===== Add By Kou 2006/7/8 使われていないクラスにもコンパイルエラー出ないようにするため =====

}
