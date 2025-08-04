package mdware.common.dictionary;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>タイトル: 受領IF取引先配信フラグ(取引先マスタ)データディクショナリ</p>
 * <p>説明: 受領IF取引先配信フラグ(取引先マスタ)のデータディクショナリを定義します
 * </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Shimatani
 * @version 1.00 (2005.01.20) 初版作成
 */

public class ReceiptIfTorihikisakiFgDictionary {
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
	private ReceiptIfTorihikisakiFgDictionary(String code, String name) {
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
	 * @return HachuyoIfFgDictionary
	 */
	public static ReceiptIfTorihikisakiFgDictionary getStatus(String key)
	{
		ReceiptIfTorihikisakiFgDictionary ret = (ReceiptIfTorihikisakiFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuyoIfFgDictionary
	 */
	public static ReceiptIfTorihikisakiFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuyoIfFgDictionary
	 */
	public static ReceiptIfTorihikisakiFgDictionary getStatus(int key)
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
		if( !(obj instanceof ReceiptIfTorihikisakiFgDictionary) )
			return false;
		String objStr = ((ReceiptIfTorihikisakiFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static ReceiptIfTorihikisakiFgDictionary TAISYOGAI = new ReceiptIfTorihikisakiFgDictionary("0", "対象外");
	public static ReceiptIfTorihikisakiFgDictionary TAISYO = new ReceiptIfTorihikisakiFgDictionary("1", "対象");
    public static ReceiptIfTorihikisakiFgDictionary UNKNOWN = new ReceiptIfTorihikisakiFgDictionary("X","UNKNOWN");
}
