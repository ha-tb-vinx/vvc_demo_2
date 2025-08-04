package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: PC発行区分ディクショナリ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.00 (2004.08.25) 初版作成
 * @version 1.01 (2004.09.06) 障害報告書No.013対応 kirihara プライスカード発行区分の"区分/名称"の定義を新規追加。
 */
public class PcKbDictionary {
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
	private PcKbDictionary(String code, String name) {
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
	public static PcKbDictionary getStatus(String key)
	{
		PcKbDictionary ret = (PcKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static PcKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}
	
	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return GyosyuKbDictionary
	 */
	public static PcKbDictionary getStatus(int key)
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
		if( !(obj instanceof PcKbDictionary) )
			return false;
		String objStr = ((PcKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
	
	public static PcKbDictionary FRESH_SEISEN 			= new PcKbDictionary("331", "日配品");
	public static PcKbDictionary OS_SYOKUHIN_LARGE 		= new PcKbDictionary("332", "食品　大");
	public static PcKbDictionary OS_SYOKUHIN_SMALL 		= new PcKbDictionary("334", "食品　小");
	public static PcKbDictionary OS_FAMILY_FAMILY 		= new PcKbDictionary("112", "ファミリー　S,M,L");
	public static PcKbDictionary OS_BEDDING_FAMILY		= new PcKbDictionary("222", "ファミリー　S,M,L");
	public static PcKbDictionary OS_FAMILY_SMALL 		= new PcKbDictionary("114", "シール　小");
	public static PcKbDictionary OS_COSMETICS_SMALL 	= new PcKbDictionary("444", "シール　小");
	public static PcKbDictionary OS_LIVE_SMALL 			= new PcKbDictionary("224", "シール　小");
	public static PcKbDictionary OS_LIVE_LARGE 			= new PcKbDictionary("225", "シール　大");
	public static PcKbDictionary OS_OTHERS_SMALL 		= new PcKbDictionary("554", "シール　小");
	public static PcKbDictionary OS_OTHERS_LARGE 		= new PcKbDictionary("555", "シール　大");
	public static PcKbDictionary OS_CULTURE_SMALL 		= new PcKbDictionary("624", "シール　小");
	public static PcKbDictionary OS_CULTURE_LARGE 		= new PcKbDictionary("625", "シール　大");
	public static PcKbDictionary OS_FOLSOM_LARGE 		= new PcKbDictionary("775", "シール　大");
	public static PcKbDictionary OS_FOLSOM_SMALL 		= new PcKbDictionary("774", "シール　小");
	public static PcKbDictionary OS_ELEC_SMALL 			= new PcKbDictionary("227", "シール　小");
	public static PcKbDictionary OS_LIVE_POS 			= new PcKbDictionary("228", "シール　POS");
	public static PcKbDictionary OS_COSMETICS_POS 		= new PcKbDictionary("448", "シール　POS");
	public static PcKbDictionary OS_OTHERS_POS 			= new PcKbDictionary("558", "シール　POS");
	public static PcKbDictionary OS_CULTURE_POS 		= new PcKbDictionary("628", "シール　POS");
	public static PcKbDictionary OS_FOLSOM_POS 			= new PcKbDictionary("778", "シール　POS");
	public static PcKbDictionary BOOM_BEDDING_SMALL 	= new PcKbDictionary("224", "シール　小");
	public static PcKbDictionary BOOM_BEDDING_POS1 		= new PcKbDictionary("22A", "シール　POS");
	public static PcKbDictionary BOOM_BEDDING_POS2 		= new PcKbDictionary("22B", "シール　POS");
	public static PcKbDictionary BOOM_BEDDING_POS3 		= new PcKbDictionary("22C", "シール　POS");
	public static PcKbDictionary BOOM_BEDDING_POS4 		= new PcKbDictionary("22X", "シール　POS");
	public static PcKbDictionary BOOM_ELEC_SMALL 		= new PcKbDictionary("744", "シール　小");
	public static PcKbDictionary BOOM_ELEC_POS1 		= new PcKbDictionary("74A", "シール　POS");
	public static PcKbDictionary BOOM_ELEC_POS2 		= new PcKbDictionary("74B", "シール　POS");
	public static PcKbDictionary BOOM_ELEC_POS3 		= new PcKbDictionary("74C", "シール　POS");
	public static PcKbDictionary BOOM_ELEC_POS4 		= new PcKbDictionary("74X", "シール　POS");
	public static PcKbDictionary BOOM_OTHERS_SMALL 		= new PcKbDictionary("764", "シール　小");
	public static PcKbDictionary BOOM_OTHERS_POS1 		= new PcKbDictionary("76A", "シール　POS");
	public static PcKbDictionary BOOM_OTHERS_POS2 		= new PcKbDictionary("76B", "シール　POS");
	public static PcKbDictionary BOOM_OTHERS_POS3 		= new PcKbDictionary("76C", "シール　POS");
	public static PcKbDictionary BOOM_OTHERS_POS4 		= new PcKbDictionary("76X", "シール　POS");
	public static PcKbDictionary TAG_BEDDING_SMALL 		= new PcKbDictionary("22", "シール　小");
	public static PcKbDictionary TAG_SPORSIAM_SMALL 	= new PcKbDictionary("57", "シール　小");
	public static PcKbDictionary TAG_OTHERS_SMALL 		= new PcKbDictionary("61", "シール　小");
	public static PcKbDictionary TAG_CLOTHING_SMALL 	= new PcKbDictionary("11", "シール　小");
	public static PcKbDictionary TAG_DRESS_SMALL 		= new PcKbDictionary("44", "シール　小");
	public static PcKbDictionary TAG_KIMONO_SMALL 		= new PcKbDictionary("71", "シール　小");
	public static PcKbDictionary NON 					= new PcKbDictionary("3",  "");

	public static PcKbDictionary UNKNOWN = new PcKbDictionary("", "");
}
