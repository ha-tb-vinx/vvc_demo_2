package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: マスタ仕様不可区分ディクショナリ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Yunba
 * @version 1.00 (2004.08.08) 初版作成
 * @version 1.10 (2006.09.18) K.Tanigawa 一部可 追加
 */
public class MasterUseKbDictionary {
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
	private MasterUseKbDictionary(String code, String name) {
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
	 * @return GyosyuKbDictionary
	 */
	public static MasterUseKbDictionary getStatus(String key)
	{
		MasterUseKbDictionary ret = (MasterUseKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static MasterUseKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static MasterUseKbDictionary getStatus(int key)
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
		if( !(obj instanceof MasterUseKbDictionary) )
			return false;
		String objStr = ((MasterUseKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	
	public static MasterUseKbDictionary KA = new MasterUseKbDictionary("0", "使用可");
	public static MasterUseKbDictionary FUKA = new MasterUseKbDictionary("1", "使用不可");
	// ===BEGIN=== Add by Tanigawa 2006/9/18
	public static MasterUseKbDictionary ICHIBUKA = new MasterUseKbDictionary("2", "一部可");
	// === END === Add by Tanigawa 2006/9/18

	public static MasterUseKbDictionary UNKNOWN = new MasterUseKbDictionary("X","現在未定コード");
}
