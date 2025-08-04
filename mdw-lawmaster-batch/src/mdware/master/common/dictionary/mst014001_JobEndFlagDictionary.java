/**
 * <p>タイトル:ジョブ終了フラグDictionary </p>
 * <P>説明　　: ジョブ終了フラグディクショナリ</p>
 * <P>著作権　: Copyright (c) 2013</p>
 * <P>会社名　: VINX Corp.</P>
 * @author S.Matsushita
 * @version 3.00 (2013/05/30) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @see なし
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:ジョブ終了フラグDictionary </p>
 * <P>説明　　: ジョブ終了フラグディクショナリ</p>
 * <P>著作権　: Copyright (c) 2013</p>
 * <P>会社名　: VINX Corp.</P>
 * @author S.Matsushita
 * @version 3.00 (2013/05/30) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @see なし
 */
public class mst014001_JobEndFlagDictionary {
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
	private mst014001_JobEndFlagDictionary(String code, String name) {
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
	public static mst014001_JobEndFlagDictionary getStatus(String key)
	{
		mst014001_JobEndFlagDictionary ret = (mst014001_JobEndFlagDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst014001_JobEndFlagDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst014001_JobEndFlagDictionary getStatus(int key)
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
		if( !(obj instanceof mst014001_JobEndFlagDictionary) )
			return false;
		String objStr = ((mst014001_JobEndFlagDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst014001_JobEndFlagDictionary TAIKI = new mst014001_JobEndFlagDictionary("0", "待機");
	public static mst014001_JobEndFlagDictionary SYURYO = new mst014001_JobEndFlagDictionary("1", "終了");
	public static mst014001_JobEndFlagDictionary UNKNOWN = new mst014001_JobEndFlagDictionary("X","UNKNOWN");
}
