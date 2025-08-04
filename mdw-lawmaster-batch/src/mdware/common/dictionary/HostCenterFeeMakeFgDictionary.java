package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: ホストセンターフィー作成フラグ Dictionary</p>
 * <p>説明: ホストセンターフィー作成フラグ（センターフィー）ディクショナリ</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/06/23 S.Kitamura 新規作成
 * @version 1.02 2005/07/06 S.Kitamura 障害報告No.741　対応
 */
public class HostCenterFeeMakeFgDictionary {

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
	private HostCenterFeeMakeFgDictionary(String code, String name) {
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
	public static HostCenterFeeMakeFgDictionary getStatus(String key) {
		HostCenterFeeMakeFgDictionary ret = (HostCenterFeeMakeFgDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static HostCenterFeeMakeFgDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static HostCenterFeeMakeFgDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof HostCenterFeeMakeFgDictionary))
			return false;
		String objStr = ((HostCenterFeeMakeFgDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}
	//	2005/07/06 S.Kitamura　障害報告書No.741 START
	public static HostCenterFeeMakeFgDictionary TABLE_DT_CENTER_FEE =
		new HostCenterFeeMakeFgDictionary("BR_DT_CENTER_FEE", "センターフィーテーブル");

	public static HostCenterFeeMakeFgDictionary MISAKUSEI_FLG = new HostCenterFeeMakeFgDictionary("0", "未作成");
	public static HostCenterFeeMakeFgDictionary SAKUSEIZUMI_FLG = new HostCenterFeeMakeFgDictionary("1", "作成済み");
	public static HostCenterFeeMakeFgDictionary SAKUSEICHU_FLG = new HostCenterFeeMakeFgDictionary("9", "作成中");

	public static HostCenterFeeMakeFgDictionary UNKNOWN = new HostCenterFeeMakeFgDictionary("X", "UNKNOWN");
	//	2005/07/06 S.Kitamura　障害報告書No.741 END
}
