/**
 * <P>タイトル : mst008801_SyohinTaikeiDictionaryクラス</p>
 * <P>説明 : 商品体系ディクショナリ</p>
 * <P>著作権: Copyright (c) 2006</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : mst008801_SyohinTaikeiDictionaryクラス</p>
 * <P>説明 : 商品体系ディクショナリ</p>
 * <P>著作権: Copyright (c) 2006</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
public class mst008801_SyohinTaikeiDictionary {

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
	private mst008801_SyohinTaikeiDictionary(String code, String name) {
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
	public static mst008801_SyohinTaikeiDictionary getStatus(String key) {
		mst008801_SyohinTaikeiDictionary ret = (mst008801_SyohinTaikeiDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param long DB内のコード(key)
	 * @return mst009401_SyohinTaikeiDictionary
	 */
	public static mst008801_SyohinTaikeiDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst009401_SyohinTaikeiDictionary
	 */
	public static mst008801_SyohinTaikeiDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst008801_SyohinTaikeiDictionary)) {
			return false;
		}
		String objStr = ((mst008801_SyohinTaikeiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	//階層データ(DB内のコード、DB内コードの意味)
	public static mst008801_SyohinTaikeiDictionary SITEINASHI	= new mst008801_SyohinTaikeiDictionary("0", "（指定なし）");
	public static mst008801_SyohinTaikeiDictionary HINSYU	= new mst008801_SyohinTaikeiDictionary("1", "品種");
	public static mst008801_SyohinTaikeiDictionary HINGUN	= new mst008801_SyohinTaikeiDictionary("2", "品群");
	public static mst008801_SyohinTaikeiDictionary HANKU = new mst008801_SyohinTaikeiDictionary("3", "販区");
	public static mst008801_SyohinTaikeiDictionary URIKU = new mst008801_SyohinTaikeiDictionary("4", "売区");
	public static mst008801_SyohinTaikeiDictionary TYUKANSYUKEI = new mst008801_SyohinTaikeiDictionary("5", "中間集計");
	public static mst008801_SyohinTaikeiDictionary SYOGYOSYU = new mst008801_SyohinTaikeiDictionary("6", "小業種");
	public static mst008801_SyohinTaikeiDictionary DAIGYOSYU = new mst008801_SyohinTaikeiDictionary("7", "大業種");
	public static mst008801_SyohinTaikeiDictionary UNKNOWN = new mst008801_SyohinTaikeiDictionary("X", "UNKNOWN");
}