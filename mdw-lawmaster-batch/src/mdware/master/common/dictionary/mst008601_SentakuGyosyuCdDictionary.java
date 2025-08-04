/**
 * <P>タイトル： マスタ管理システム</p>
 * <P>説明　　： 選択業種コードディクショナリ</p>
 * <P>著作権　： Copyright (c) 2005</p>								
 * <P>会社名　： Vinculum Japan Corp.</P>								
 * @author 	 A.Tozaki
 * @version 	 1.0
 * @see		 なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル： マスタ管理システム</p>
 * <P>説明　　： 選択業種コードディクショナリ</p>
 * <P>著作権　： Copyright (c) 2005</p>								
 * <P>会社名　： Vinculum Japan Corp.</P>								
 * @author 	 A.Tozaki
 * @version 	 1.0
 * @see		 なし								
 */
public class mst008601_SentakuGyosyuCdDictionary {
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
	private mst008601_SentakuGyosyuCdDictionary(String code, String name) {
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
	public static mst008601_SentakuGyosyuCdDictionary getStatus(String key)
	{
		mst008601_SentakuGyosyuCdDictionary ret = (mst008601_SentakuGyosyuCdDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008601_SentakuGyosyuCdDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008601_SentakuGyosyuCdDictionary getStatus(int key)
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
		if( !(obj instanceof mst008601_SentakuGyosyuCdDictionary) )
			return false;
		String objStr = ((mst008601_SentakuGyosyuCdDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst008601_SentakuGyosyuCdDictionary SEL_IRO	= new mst008601_SentakuGyosyuCdDictionary("01", "衣料服飾");
	public static mst008601_SentakuGyosyuCdDictionary SEL_LIV	= new mst008601_SentakuGyosyuCdDictionary("02", "住生活");
	public static mst008601_SentakuGyosyuCdDictionary SEL_GRO	= new mst008601_SentakuGyosyuCdDictionary("03", "加工食品");
	public static mst008601_SentakuGyosyuCdDictionary SEL_FRE	= new mst008601_SentakuGyosyuCdDictionary("04", "生鮮食品");
	public static mst008601_SentakuGyosyuCdDictionary UNKNOWN	= new mst008601_SentakuGyosyuCdDictionary("X","UNKNOWN");
}
