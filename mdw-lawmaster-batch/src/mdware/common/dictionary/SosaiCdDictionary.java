package mdware.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル: 相殺コード Dictionary</p>
 * <p>説明: 相殺コード(課金相殺)ディクショナリクラス</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Kitamura
 * @version 1.00 2005/07/06 S.Kitamura 新規作成
 * @version 1.01 2005/07/06 S.kitamura 障害報告No.740対応
 */
public class SosaiCdDictionary {

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
	private SosaiCdDictionary(String code, String name) {
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
	public static SosaiCdDictionary getStatus(String key) {
		SosaiCdDictionary ret = (SosaiCdDictionary)map.get(key);
		if (ret == null)
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static SosaiCdDictionary getStatus(long key) {
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ASNDictionary
	 */
	public static SosaiCdDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SosaiCdDictionary))
			return false;
		String objStr = ((SosaiCdDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}
	//	2005/07/06 S.Kitamura　障害報告書No.740 START
	public static SosaiCdDictionary SOSAI_CD_A3 = new SosaiCdDictionary("0871", "センター利用料");
	public static SosaiCdDictionary SOSAI_CD_A2 = new SosaiCdDictionary("0872", "開梱手数料");
	public static SosaiCdDictionary SOSAI_CD_C1 = new SosaiCdDictionary("0873", "返品処理料");
	public static SosaiCdDictionary SOSAI_CD_A1 = new SosaiCdDictionary("0874", "集荷料");
	public static SosaiCdDictionary SOSAI_CD_X1 = new SosaiCdDictionary("0874", "道外物流集荷");
   //20050721課金相殺データ生成バッチ(EDI)用に追加 start
	public static SosaiCdDictionary SOSAI_CD_EOS_DENP = new SosaiCdDictionary("0312", "EOS伝票代");
	public static SosaiCdDictionary SOSAI_CD_ONLINE_HACHU = new SosaiCdDictionary("0318", "オンライン発注料");
	public static SosaiCdDictionary SOSAI_CD_WEEKLY_ORDER_PLAN = new SosaiCdDictionary("0316", "生鮮週間予定");
	//20050721課金相殺データ生成バッチ(EDI)用に追加 end
	public static SosaiCdDictionary UNKNOWN = new SosaiCdDictionary("X", "UNKNOWN");
	//	2005/07/06 S.Kitamura　障害報告書No.740 END
}
