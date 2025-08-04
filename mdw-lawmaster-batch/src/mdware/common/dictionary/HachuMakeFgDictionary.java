package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 相場商品・一般商品発注データ生成フラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author takata
 * @version 1.00 (2003.10.01) 初版作成
 * @version 2.00 S.Yunba (2004.08.09) Ver3.0より移行。
 */
public class HachuMakeFgDictionary {
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
	private HachuMakeFgDictionary(String code, String name) {
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
	 * @return HachuMakeFgDictionary
	 */
	public static HachuMakeFgDictionary getStatus(String key)
	{
		HachuMakeFgDictionary ret = (HachuMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMakeFgDictionary
	 */
	public static HachuMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuMakeFgDictionary
	 */
	public static HachuMakeFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static HachuMakeFgDictionary MI = new HachuMakeFgDictionary("0","未");
	public static HachuMakeFgDictionary SUMI = new HachuMakeFgDictionary("1","済");
	public static HachuMakeFgDictionary HACHU_SIMETYU = new HachuMakeFgDictionary("9","発注締中");
	public static HachuMakeFgDictionary UNKNOWN = new HachuMakeFgDictionary("X","UNKNOWN");

}