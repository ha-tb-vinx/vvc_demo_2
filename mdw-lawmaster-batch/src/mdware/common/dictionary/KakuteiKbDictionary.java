package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 確定区分ディクショナリ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Yunba
 * @version 1.00 (2004.08.10) 初版作成
 */
public class KakuteiKbDictionary {
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
	private KakuteiKbDictionary(String code, String name) {
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
	public static KakuteiKbDictionary getStatus(String key)
	{
		KakuteiKbDictionary ret = (KakuteiKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static KakuteiKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static KakuteiKbDictionary getStatus(int key)
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
		if( !(obj instanceof KakuteiKbDictionary) )
			return false;
		String objStr = ((KakuteiKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	
	public static KakuteiKbDictionary TEIBAN = new KakuteiKbDictionary("0", "定番");
	public static KakuteiKbDictionary SYOHINBU_SYUSEI_FUKA = new KakuteiKbDictionary("1", "商品部発注（店修正不可）");
	public static KakuteiKbDictionary SYOHINBU_SYUSEI_KA = new KakuteiKbDictionary("2", "商品部発注（店修正可）");
	public static KakuteiKbDictionary KIKAN_TOKUBAI = new KakuteiKbDictionary("3", "期間特売（案内登録）");
	public static KakuteiKbDictionary SEISEN_TOKUBAI = new KakuteiKbDictionary("4", "生鮮特売（週間発注／月間特売）");
	public static KakuteiKbDictionary TENHANSOKU = new KakuteiKbDictionary("5", "店販促");
	public static KakuteiKbDictionary UNKNOWN = new KakuteiKbDictionary("X","現在未定コード");
}
