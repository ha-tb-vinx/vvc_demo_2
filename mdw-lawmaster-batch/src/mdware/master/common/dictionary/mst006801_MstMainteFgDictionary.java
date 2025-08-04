/**
 * <P>タイトル : mst006801_MstMainteFgDictionary</p>
 * <P>説明 : マスタメンテフラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @version 1.1 (2006/04/03) Takayama 【アラーム対応】
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : mst006801_MstMainteFgDictionary</p>
 * <P>説明 : マスタメンテフラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst006801_MstMainteFgDictionary {
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
	private mst006801_MstMainteFgDictionary(String code, String name) {
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
	public static mst006801_MstMainteFgDictionary getStatus(String key)
	{
		mst006801_MstMainteFgDictionary ret = (mst006801_MstMainteFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006801_MstMainteFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006801_MstMainteFgDictionary getStatus(int key)
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
		if( !(obj instanceof mst006801_MstMainteFgDictionary) )
			return false;
		String objStr = ((mst006801_MstMainteFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst006801_MstMainteFgDictionary MISYORI = new mst006801_MstMainteFgDictionary("0", "未処理");
	public static mst006801_MstMainteFgDictionary SYORIZUMI = new mst006801_MstMainteFgDictionary("1", "処理済（正常）");
	// 2006.04.03 takayama Add 「2：警告」を追加 ↓
	public static mst006801_MstMainteFgDictionary KEIKOKU = new mst006801_MstMainteFgDictionary("2", "処理済（警告）");
	// 2006.04.03 takayama Add ↑
	// 2006.04.03 takayama Add コード「2」→「3」に変更 ↓
	public static mst006801_MstMainteFgDictionary ERROR = new mst006801_MstMainteFgDictionary("3", "処理済（エラー）");
	// 2006.04.03 takayama Add ↑
	public static mst006801_MstMainteFgDictionary SYORITYU = new mst006801_MstMainteFgDictionary("9", "処理中");
	public static mst006801_MstMainteFgDictionary UNKNOWN = new mst006801_MstMainteFgDictionary("X","UNKNOWN");
}
