package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 売価配信フラグディクショナリ</p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: Vinx </P>
 * @author K.TO
 * @version 3.00 (2013.12.26) K.TO [CUS00050] POS売価不一致勧告対応
 */
public class mst011701_BaikaHaishinFlagDictionary {
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
	private mst011701_BaikaHaishinFlagDictionary(String code, String name) {
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
	public static mst011701_BaikaHaishinFlagDictionary getStatus(String key)
	{
		mst011701_BaikaHaishinFlagDictionary ret = (mst011701_BaikaHaishinFlagDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011701_BaikaHaishinFlagDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst011701_BaikaHaishinFlagDictionary getStatus(int key)
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
		if( !(obj instanceof mst011701_BaikaHaishinFlagDictionary) )
			return false;
		String objStr = ((mst011701_BaikaHaishinFlagDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst011701_BaikaHaishinFlagDictionary SINAI = new mst011701_BaikaHaishinFlagDictionary("0", "売価配信しない");
	public static mst011701_BaikaHaishinFlagDictionary SURU = new mst011701_BaikaHaishinFlagDictionary("1", "売価配信する");
	public static mst011701_BaikaHaishinFlagDictionary UNKNOWN = new mst011701_BaikaHaishinFlagDictionary("","UNKNOWN");
}
