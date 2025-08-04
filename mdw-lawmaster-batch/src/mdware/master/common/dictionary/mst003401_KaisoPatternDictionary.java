/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 階層パターンディクショナリ</p>
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
 * <P>説明 : 階層パターンディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst003401_KaisoPatternDictionary {
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
	private mst003401_KaisoPatternDictionary(String code, String name) {
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
	public static mst003401_KaisoPatternDictionary getStatus(String key)
	{
		mst003401_KaisoPatternDictionary ret = (mst003401_KaisoPatternDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst003401_KaisoPatternDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst003401_KaisoPatternDictionary getStatus(int key)
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
		if( !(obj instanceof mst003401_KaisoPatternDictionary) )
			return false;
		String objStr = ((mst003401_KaisoPatternDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

//  ↓↓2006.06.13 xubq カスタマイズ修正↓↓
//	public static mst003401_KaisoPatternDictionary HINSYU_HINGUN = new mst003401_KaisoPatternDictionary("1", "品種→品群");
//	public static mst003401_KaisoPatternDictionary HINGUN_HANKU = new mst003401_KaisoPatternDictionary("2", "品群→販区");
//	public static mst003401_KaisoPatternDictionary HANKU_URIKU = new mst003401_KaisoPatternDictionary("3", "販区→売区");
//	public static mst003401_KaisoPatternDictionary URIKU_TYUKANSYUKEI = new mst003401_KaisoPatternDictionary("4", "売区→中間集計");
//	public static mst003401_KaisoPatternDictionary TYUKANSYUKEI_SYOGYOSYU = new mst003401_KaisoPatternDictionary("5", "中間集計→小業種");
//	public static mst003401_KaisoPatternDictionary SYOGYOSYU_DAIGYOSYU = new mst003401_KaisoPatternDictionary("6", "小業種→大業種");	
//	public static mst003401_KaisoPatternDictionary UNKNOWN = new mst003401_KaisoPatternDictionary("X","UNKNOWN");
	public static mst003401_KaisoPatternDictionary UNIT_LINE = new mst003401_KaisoPatternDictionary("1", "ユニット→ライン");
	public static mst003401_KaisoPatternDictionary LINE_HINSYU = new mst003401_KaisoPatternDictionary("2", "ライン→品種");
	public static mst003401_KaisoPatternDictionary HINSYU_HINBAN = new mst003401_KaisoPatternDictionary("3", "品種→品番");
	public static mst003401_KaisoPatternDictionary HINBAN_BUMON = new mst003401_KaisoPatternDictionary("4", "品番→部門");
	
	public static mst003401_KaisoPatternDictionary CLASS_LINE = new mst003401_KaisoPatternDictionary("1", "クラス→ライン");
	public static mst003401_KaisoPatternDictionary LINE_DPT = new mst003401_KaisoPatternDictionary("2", "ライン→DPT");
	public static mst003401_KaisoPatternDictionary DPT_BUMON = new mst003401_KaisoPatternDictionary("3", "DPT→部門");
	public static mst003401_KaisoPatternDictionary BUMON_GRP = new mst003401_KaisoPatternDictionary("4", "部門→グループ");
	
	public static mst003401_KaisoPatternDictionary UNKNOWN = new mst003401_KaisoPatternDictionary("X","UNKNOWN");
//  ↑↑2006.06.13 xubq カスタマイズ修正↑↑
}
