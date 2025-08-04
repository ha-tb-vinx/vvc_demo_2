package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 納品管理生成済みフラグディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author hirai
 * @version 1.00 (2005.7.15) 初版作成
 * @version 1.00	2005.10.16	H.Okuno	変更依頼No.721
 */

public class NohinKanriMakeFgDictionary {
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
	private NohinKanriMakeFgDictionary(String code, String name) {
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
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinKanriMakeFgDictionary getStatus(String key)
	{
		NohinKanriMakeFgDictionary ret = (NohinKanriMakeFgDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinKanriMakeFgDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return HachuDataMakeFgDictionary
	 */
	public static NohinKanriMakeFgDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	public static NohinKanriMakeFgDictionary MI = new NohinKanriMakeFgDictionary("0","未");
//BEGIN	2005.10.16	H.Okuno
//	public static NohinKanriMakeFgDictionary SUMI = new NohinKanriMakeFgDictionary("1","済");
//	public static NohinKanriMakeFgDictionary SYORITYU = new NohinKanriMakeFgDictionary("9","処理中");
	public static NohinKanriMakeFgDictionary SUMI_MINO = new NohinKanriMakeFgDictionary("1","未納データ作成済");
	public static NohinKanriMakeFgDictionary SUMI_CHINO_SEINO = new NohinKanriMakeFgDictionary("2","遅納・正納データ作成済");
	public static NohinKanriMakeFgDictionary SYORITYU_MINO = new NohinKanriMakeFgDictionary("8","未納データ作成中");
	public static NohinKanriMakeFgDictionary SYORITYU_CHINO_SEINO = new NohinKanriMakeFgDictionary("9","遅納・正納データ作成中");
//END	2005.10.16	H.Okuno
	public static NohinKanriMakeFgDictionary UNKNOWN = new NohinKanriMakeFgDictionary("X","UNKNOWN");

}