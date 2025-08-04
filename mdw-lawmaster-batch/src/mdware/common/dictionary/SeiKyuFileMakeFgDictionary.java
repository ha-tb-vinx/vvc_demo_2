package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: AsnFileMakeFgDictionary</p>
 * <p>説明: AsnFileMakeFgDictionary</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hirai
 * @version 1.00 2005/07/21 新規作成
 */
public class SeiKyuFileMakeFgDictionary {

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
	private SeiKyuFileMakeFgDictionary(String code, String name) {
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
	public static SeiKyuFileMakeFgDictionary getStatus(String key) {
		SeiKyuFileMakeFgDictionary ret = (SeiKyuFileMakeFgDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AsnFileMakeFgDictionary
	 */
	public static SeiKyuFileMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AsnFileMakeFgDictionary
	 */
	public static SeiKyuFileMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SeiKyuFileMakeFgDictionary))
			return false;
		String objStr = ((SeiKyuFileMakeFgDictionary) obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static SeiKyuFileMakeFgDictionary MISYORI = new SeiKyuFileMakeFgDictionary("0", "未処理");
	public static SeiKyuFileMakeFgDictionary SYORIZUMI = new SeiKyuFileMakeFgDictionary("1", "処理済み");
	public static SeiKyuFileMakeFgDictionary ERROR = new SeiKyuFileMakeFgDictionary("2", "エラー");
	public static SeiKyuFileMakeFgDictionary SYORICHU = new SeiKyuFileMakeFgDictionary("9", "処理中");
	public static SeiKyuFileMakeFgDictionary UNKNOWN = new SeiKyuFileMakeFgDictionary("X", "UNKNOWN");
}
