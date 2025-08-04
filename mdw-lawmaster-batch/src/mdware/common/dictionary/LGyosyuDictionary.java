/*
 * 作成日: 2005/01/13
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * @author タニガワ
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class LGyosyuDictionary {

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
	private LGyosyuDictionary(String code, String name) {
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
	 * @return LGyosyuDictionary
	 */
	public static LGyosyuDictionary getStatus(String key)
	{
		LGyosyuDictionary ret = (LGyosyuDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return LGyosyuDictionary
	 */
	public static LGyosyuDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return LGyosyuDictionary
	 */
	public static LGyosyuDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	//食品
	public static LGyosyuDictionary SYOKUHIN 	= new LGyosyuDictionary("0033","食品");
	//衣料
	public static LGyosyuDictionary IRYO 	   	= new LGyosyuDictionary("0011","衣料");
	public static LGyosyuDictionary FUKUSYOKU	= new LGyosyuDictionary("0044","服飾");
	public static LGyosyuDictionary SPORTS	= new LGyosyuDictionary("0061","スポージアム");
	//住生活
//20050618 add hirai 変更依頼書No.546 start
//	public static LGyosyuDictionary JUKYO		= new LGyosyuDictionary("0022","住居");
	public static LGyosyuDictionary JUKYO		= new LGyosyuDictionary("0022","住生活");
//	20050618 add hirai 変更依頼書No.546 end
	public static LGyosyuDictionary FORUSOME	= new LGyosyuDictionary("0077","フォルサム");
	//不明
	public static LGyosyuDictionary UNKNOWN	= new LGyosyuDictionary("X","UNKNOWN");

}
