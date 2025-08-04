package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: アラーム区分データディクショナリ</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kirihara
 * @version 1.0
 * @version 1.1 2005/11/04 jinno 変更依頼書No.227 年末年始対応で使用する為、コード値を増加。
 */

public class AlarmKbDictionary {
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
	private AlarmKbDictionary(String code, String name) {
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
	 * @return AlarmKbDictionary
	 */
	public static AlarmKbDictionary getStatus(String key)
	{
		AlarmKbDictionary ret = (AlarmKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static AlarmKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static AlarmKbDictionary getStatus(int key)
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
		if( !(obj instanceof AlarmKbDictionary) )
			return false;
		String objStr = ((AlarmKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static AlarmKbDictionary NOTHING = new AlarmKbDictionary("00", "アラームなし");
	public static AlarmKbDictionary TAISYOGAI = new AlarmKbDictionary("01", "発注対象外");
	public static AlarmKbDictionary BUSYOHENKO = new AlarmKbDictionary("02", "発注部署変更");
	public static AlarmKbDictionary GENTANKA = new AlarmKbDictionary("03", "原単価金額変更");
	public static AlarmKbDictionary SAKUJO = new AlarmKbDictionary("04", "相場商品情報削除");
	public static AlarmKbDictionary MASTER = new AlarmKbDictionary("05", "マスタ変更による発注停止");
	public static AlarmKbDictionary KETURAKU = new AlarmKbDictionary("06", "欠落商品あり");
	public static AlarmKbDictionary TANI = new AlarmKbDictionary("07", "発注単位数変更");
	public static AlarmKbDictionary TEISI = new AlarmKbDictionary("08", "停止数過剰");
	
// 2005/11/04 jinno 変更依頼書No.227 年末年始対応で使用する為、コード値を増加。	
	public static AlarmKbDictionary IKKATUDENPATU = new AlarmKbDictionary("90", "一括伝発商品");
// 2005/11/04 jinno 変更依頼書No.227 年末年始対応で使用する為、コード値を増加。		
	
	public static AlarmKbDictionary UNKNOWN = new AlarmKbDictionary("X","UNKNOWN");
}
