package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 商品区分ディクショナリ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author jinno
 * @version 1.00 (2005.10.28) 初版作成 (新MD用)
 */
public class SyohinKbDictionary {
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
	private SyohinKbDictionary(String code, String name) {
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
	public static SyohinKbDictionary getStatus(String key)
	{
		SyohinKbDictionary ret = (SyohinKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static SyohinKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static SyohinKbDictionary getStatus(int key)
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
		if( !(obj instanceof SyohinKbDictionary) )
			return false;
		String objStr = ((SyohinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	
	public static SyohinKbDictionary SYOHIN = new SyohinKbDictionary("1", "商品");
	public static SyohinKbDictionary ZAIRYO = new SyohinKbDictionary("2", "材料");
	public static SyohinKbDictionary INSTORE = new SyohinKbDictionary("3", "インストア加工");
	public static SyohinKbDictionary YOUDO = new SyohinKbDictionary("4", "用度品");
	public static SyohinKbDictionary UNKNOWN = new SyohinKbDictionary("","");
}
