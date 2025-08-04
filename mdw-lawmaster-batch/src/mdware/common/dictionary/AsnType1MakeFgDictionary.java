package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: AsnType1MakeFgDictionary</p>
 * <p>説明: AsnFileMakeFgDictionary</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 李武勇
 * @version 1.00 2005/06/16 新規作成
 * @version 1.01 2005/06/20 sakai 名称が長いので修正
 * @version 1.02 2005/07/14 otani エラーフラグ追加
 */
public class AsnType1MakeFgDictionary {

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
	private AsnType1MakeFgDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code, this);
	}

	/**
	 * ＤＢ内コードの意味を返す
	 * @return String
	 */
	public String toString() {
		return name;
	}

	/**
	 * ＤＢ内のコードを返す
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AsnFileMakeFgDictionary
	 */
	public static AsnType1MakeFgDictionary getStatus(String key) {
		AsnType1MakeFgDictionary ret = (AsnType1MakeFgDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AsnFileMakeFgDictionary
	 */
	public static AsnType1MakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AsnFileMakeFgDictionary
	 */
	public static AsnType1MakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof AsnType1MakeFgDictionary))
			return false;
		String objStr = ((AsnType1MakeFgDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static AsnType1MakeFgDictionary KOSINTAISYO = new AsnType1MakeFgDictionary("1", "更新対象");
	public static AsnType1MakeFgDictionary ERROR = new AsnType1MakeFgDictionary("2", "エラー");
	public static AsnType1MakeFgDictionary UNKNOWN = new AsnType1MakeFgDictionary("X", "UNKNOWN");
}
