package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 個数マイナス符号 Dictionary</p>
 * <p>説明: 個数マイナスフラグ(課金相殺)ディクショナリ</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/06/23 S.Kitamura 新規作成
 */
public class CenterFeeSignDictionary {

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
	private CenterFeeSignDictionary(String code, String name) {
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
	 * @return ASNDictionary
	 */
	public static CenterFeeSignDictionary getStatus(String key) {
		CenterFeeSignDictionary ret = (CenterFeeSignDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static CenterFeeSignDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static CenterFeeSignDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof CenterFeeSignDictionary))
			return false;
		String objStr = ((CenterFeeSignDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static CenterFeeSignDictionary TABLE_DT_CENTER_FEE =
		new CenterFeeSignDictionary("BR_DT_CENTER_FEE", "センターフィーテーブル");
		
	public static CenterFeeSignDictionary PLUS_FLG = new CenterFeeSignDictionary("0","正数");
	public static CenterFeeSignDictionary MINUS_FLG = new CenterFeeSignDictionary("1","負数");
	
	public static CenterFeeSignDictionary UNKNOWN = new CenterFeeSignDictionary("X", "UNKNOWN");
}
