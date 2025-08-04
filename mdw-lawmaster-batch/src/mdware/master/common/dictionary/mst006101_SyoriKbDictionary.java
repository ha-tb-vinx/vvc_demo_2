/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 処理区分（品種一括送信・単品一括送信）ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author FSIABC E.Yoshikawa
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 処理区分（品種一括送信・単品一括送信）ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author FSIABC E.Yoshikawa
 * @version 1.0
 * @see なし								
 */
public class mst006101_SyoriKbDictionary {
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
	private mst006101_SyoriKbDictionary(String code, String name) {
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
	public static mst006101_SyoriKbDictionary getStatus(String key)
	{
		mst006101_SyoriKbDictionary ret = (mst006101_SyoriKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006101_SyoriKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006101_SyoriKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst006101_SyoriKbDictionary) )
			return false;
		String objStr = ((mst006101_SyoriKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	// 登録データ作成・削除データ作成のコードは、バッチの予約処理区分に合わせている
	public static mst006101_SyoriKbDictionary TOUROKU   = new mst006101_SyoriKbDictionary("4", "登録データ作成");
	public static mst006101_SyoriKbDictionary SAKUZYO   = new mst006101_SyoriKbDictionary("3", "削除データ作成");
	public static mst006101_SyoriKbDictionary SYOKAI    = new mst006101_SyoriKbDictionary("1", "作成データ照会");
	public static mst006101_SyoriKbDictionary TORIKESHI = new mst006101_SyoriKbDictionary("2", "作成データ取消");
	public static mst006101_SyoriKbDictionary UNKNOWN = new mst006101_SyoriKbDictionary("X","UNKNOWN");
}
