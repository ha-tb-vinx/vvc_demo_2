package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:税率区分Dictionary </p>
 * <p>説明: 税率区分を保持する。</p>
 * <p>著作権: Copyright  (C) 2013</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 3.00 (2013.05.12)    M.Ayukawa [MSTC00013] ランドローム様  商品マスタ初期値設定対応   
 */
public class mst012301_TaxRateKbDictionary {

	/**
	 * DB内のコード
	 */
	private String code = null;
	/**
	 * DB内コードの意味
	 */
	private String name = null;
	/**
	 * 対応したクラスを保持するマップ
	 */
	private static final Map map = new HashMap();

	/**
	 * コンストラクタ
	 * @param code DB内のコード
	 * @param name DB内コードの意味
	 */
	private mst012301_TaxRateKbDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code,this);
	}

	/**
	 * DB内コードの意味を返す
	 * @return String
	 */
	public String toString() {
		return name;
	}

	/**
	 * DB内のコードを返す
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * コードで検索を行う
	 * @param String DB内のコード(key)
	 * @return mst010101_SyoriSyubetsuKbDictionary
	 */
	public static mst012301_TaxRateKbDictionary getStatus(String key) {
		mst012301_TaxRateKbDictionary ret = (mst012301_TaxRateKbDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;

	}	

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst010101_SyoriSyubetsuKbDictionary
	 */
	public static mst012301_TaxRateKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst012301_TaxRateKbDictionary)) {
			return false;
		}
		String objStr = ((mst012301_TaxRateKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	// 初期値指定として必要な値のみ宣言
	public static mst012301_TaxRateKbDictionary PERCENT_5 = new mst012301_TaxRateKbDictionary("1", "5%");
	public static mst012301_TaxRateKbDictionary UNKNOWN   = new mst012301_TaxRateKbDictionary("X","UNKNOWN");
}
