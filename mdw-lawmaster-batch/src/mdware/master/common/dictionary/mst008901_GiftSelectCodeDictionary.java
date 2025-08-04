/*
 * 作成日: 2006/04/12
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : mst008801_SyohinTaikeiDictionaryクラス</p>
 * <P>説明 : 商品体系検索ディクショナリ</p>
 * <P>著作権: Copyright (c) 2006</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author M.Kawamoto
 * @version 1.0 (2006/04/12) 初版作成
 */
public class mst008901_GiftSelectCodeDictionary {
	
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
	private mst008901_GiftSelectCodeDictionary(String code, String name) {
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
	 * @return mst009401_SyohinTaikeiDictionary
	 */
	public static mst008901_GiftSelectCodeDictionary getStatus(String key) {
		mst008901_GiftSelectCodeDictionary ret = (mst008901_GiftSelectCodeDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;

	}	

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst009401_SyohinTaikeiDictionary
	 */
	public static mst008901_GiftSelectCodeDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst008901_GiftSelectCodeDictionary)) {
			return false;
		}
		String objStr = ((mst008901_GiftSelectCodeDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst008901_GiftSelectCodeDictionary SITEINASHI	= new mst008901_GiftSelectCodeDictionary("0", "（指定なし）");
	public static mst008901_GiftSelectCodeDictionary HANKU = new mst008901_GiftSelectCodeDictionary("1", "販区コード");
	public static mst008901_GiftSelectCodeDictionary HINSYU	= new mst008901_GiftSelectCodeDictionary("2", "品種コード");
	public static mst008901_GiftSelectCodeDictionary GIFT = new mst008901_GiftSelectCodeDictionary("3", "ギフトコード");
	public static mst008901_GiftSelectCodeDictionary SYOHIN = new mst008901_GiftSelectCodeDictionary("4", "商品コード");
	public static mst008901_GiftSelectCodeDictionary UNKNOWN = new mst008901_GiftSelectCodeDictionary("X", "UNKNOWN");
}
