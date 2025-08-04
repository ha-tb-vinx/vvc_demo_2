package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 初回導入区分ディクショナリ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Yunba
 * @version 1.00 (2004.08.11) 初版作成
 */
public class SyokaiKbDictionary {
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
	private SyokaiKbDictionary(String code, String name) {
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
	public static SyokaiKbDictionary getStatus(String key)
	{
		SyokaiKbDictionary ret = (SyokaiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static SyokaiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static SyokaiKbDictionary getStatus(int key)
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
		if( !(obj instanceof SyokaiKbDictionary) )
			return false;
		String objStr = ((SyokaiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	
	public static SyokaiKbDictionary KEIZOKU = new SyokaiKbDictionary("0", "継続");
	public static SyokaiKbDictionary SYOKAI = new SyokaiKbDictionary("1", "初回導入");
	public static SyokaiKbDictionary UNKNOWN = new SyokaiKbDictionary("X","現在未定コード");
}
