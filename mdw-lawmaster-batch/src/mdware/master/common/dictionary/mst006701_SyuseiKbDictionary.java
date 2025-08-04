/**
 * <P>タイトル : mst006701_SyuseiKbDictionary</p>
 * <P>説明 : 修正区分ディクショナリ</p>
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
 * <P>タイトル : mst006701_SyuseiKbDictionary</p>
 * <P>説明 : 修正区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author shimoyama
 * @version 1.0
 * @see なし								
 */
public class mst006701_SyuseiKbDictionary {
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
	private mst006701_SyuseiKbDictionary(String code, String name) {
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
	public static mst006701_SyuseiKbDictionary getStatus(String key)
	{
		mst006701_SyuseiKbDictionary ret = (mst006701_SyuseiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006701_SyuseiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst006701_SyuseiKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst006701_SyuseiKbDictionary) )
			return false;
		String objStr = ((mst006701_SyuseiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst006701_SyuseiKbDictionary INSERT = new mst006701_SyuseiKbDictionary("1", "新規登録");
	public static mst006701_SyuseiKbDictionary UPDATE = new mst006701_SyuseiKbDictionary("2", "更新");
	public static mst006701_SyuseiKbDictionary DELETE = new mst006701_SyuseiKbDictionary("3", "削除");
	public static mst006701_SyuseiKbDictionary CANCEL = new mst006701_SyuseiKbDictionary("9", "登録取消");
	public static mst006701_SyuseiKbDictionary UNKNOWN = new mst006701_SyuseiKbDictionary("X","UNKNOWN");
}
