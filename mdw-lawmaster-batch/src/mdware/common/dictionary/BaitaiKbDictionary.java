package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 媒体区分ディクショナリ</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author 李武勇
 * @version 1.00 (2005.06.23) 初版作成
 */
public class BaitaiKbDictionary {
	
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
	private BaitaiKbDictionary(String code, String name) {
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
	 * @return BaitaiKbDictionary
	 */
	public static BaitaiKbDictionary getStatus(String key) {
		BaitaiKbDictionary ret = (BaitaiKbDictionary) map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BaitaiKbDictionary
	 */
	public static BaitaiKbDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return BaitaiKbDictionary
	 */
	public static BaitaiKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	public static BaitaiKbDictionary EDI_SIYOKIKAN = new BaitaiKbDictionary("30", "EDI試用期間");
	public static BaitaiKbDictionary JCA_TAISYO = new BaitaiKbDictionary("33", "JCA対象");
	public static BaitaiKbDictionary INTERNET_EDI = new BaitaiKbDictionary("35", "インタネットEDI");
	public static BaitaiKbDictionary UNKNOWN = new BaitaiKbDictionary("X", "UNKNOWN");
}