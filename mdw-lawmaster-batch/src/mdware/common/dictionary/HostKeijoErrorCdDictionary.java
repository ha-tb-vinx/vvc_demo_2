package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: センター送信対象エラーコード一覧</p>
 * <p>説明: センターに送信必要な場合のエラーコードを掲載</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/07/04 S.Kitamura 新規作成
 * @version 1.01 2005/07/06 S.Kitamura 障害報告No.743対応
 * @version 1.02 2005/07/27 a.maeno 変更依頼書No.602対応
 */
public class HostKeijoErrorCdDictionary {

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
	private HostKeijoErrorCdDictionary(String code, String name) {
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
	public static HostKeijoErrorCdDictionary getStatus(String key) {
		HostKeijoErrorCdDictionary ret = (HostKeijoErrorCdDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static HostKeijoErrorCdDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static HostKeijoErrorCdDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof HostKeijoErrorCdDictionary))
			return false;
		String objStr = ((HostKeijoErrorCdDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static HostKeijoErrorCdDictionary TABLE_TMP_CENTER_FEE =
		new HostKeijoErrorCdDictionary("BR_TMP_CENTER_FEE", "センターフィーTMPテーブル");
	// 2005/07/27 a.maeno 変更依頼書No.602 START	
	public static HostKeijoErrorCdDictionary CORD_000 = new HostKeijoErrorCdDictionary("000", "エラー");
	// 2005/07/27 a.maeno 変更依頼書No.602 END
	public static HostKeijoErrorCdDictionary CORD_002 = new HostKeijoErrorCdDictionary("002", "管理日付エラー");
	public static HostKeijoErrorCdDictionary CORD_003 = new HostKeijoErrorCdDictionary("003", "伝票番号エラー");
	public static HostKeijoErrorCdDictionary CORD_004 = new HostKeijoErrorCdDictionary("004", "取引先CDエラー");
	public static HostKeijoErrorCdDictionary CORD_005 = new HostKeijoErrorCdDictionary("005", "店コード１エラー");
	public static HostKeijoErrorCdDictionary CORD_009 = new HostKeijoErrorCdDictionary("009", "伝票日付エラー");
	public static HostKeijoErrorCdDictionary CORD_013 = new HostKeijoErrorCdDictionary("013", "商品明細・数量エラー");
	public static HostKeijoErrorCdDictionary CORD_014 = new HostKeijoErrorCdDictionary("014", "商品明細・単価１エラー");
	public static HostKeijoErrorCdDictionary CORD_015 = new HostKeijoErrorCdDictionary("015", "商品明細・単価２エラー");
	public static HostKeijoErrorCdDictionary CORD_016 = new HostKeijoErrorCdDictionary("016", "金額１エラー");
	public static HostKeijoErrorCdDictionary CORD_017 = new HostKeijoErrorCdDictionary("017", "金額２エラー");
	public static HostKeijoErrorCdDictionary CORD_102 = new HostKeijoErrorCdDictionary("102", "管理日付指定エラー");
	public static HostKeijoErrorCdDictionary CORD_104 = new HostKeijoErrorCdDictionary("104", "伝票日付指定エラー");
	public static HostKeijoErrorCdDictionary CORD_105 = new HostKeijoErrorCdDictionary("105", "取引先CDエラー");
	public static HostKeijoErrorCdDictionary CORD_107 = new HostKeijoErrorCdDictionary("107", "合計金額10万以上差額あり");
	public static HostKeijoErrorCdDictionary CORD_108 = new HostKeijoErrorCdDictionary("108", "合計金額10万以上差額あり");
	public static HostKeijoErrorCdDictionary CORD_111 = new HostKeijoErrorCdDictionary("111", "1店コードが存在しない");
	// 2005/07/27 a.maeno 変更依頼書No.602 START
	public static HostKeijoErrorCdDictionary CORD_112 = new HostKeijoErrorCdDictionary("112", "1部門コードが存在しない");
	// 2005/07/27 a.maeno 変更依頼書No.602 END
	public static HostKeijoErrorCdDictionary CORD_115 = new HostKeijoErrorCdDictionary("115", "仕入先コード未登録");
	public static HostKeijoErrorCdDictionary CORD_116 = new HostKeijoErrorCdDictionary("116", "EOS店コードエラー");
	public static HostKeijoErrorCdDictionary CORD_119 = new HostKeijoErrorCdDictionary("119", "同一No２度打ち");
	public static HostKeijoErrorCdDictionary CORD_120 = new HostKeijoErrorCdDictionary("120", "修正金額相違");

	public static HostKeijoErrorCdDictionary UNKNOWN = new HostKeijoErrorCdDictionary("X", "UNKNOWN");
}
