package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:CGC返品区分Dictionary </p>
 * <p>説明: CGC返品区分を保持する。</p>
 * <p>著作権: Copyright  (C) 2009</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VJC
 * @version 1.0
 * @version 3.00 (2013.05.12)    M.Ayukawa [MSTC00013] ランドローム様  商品マスタ初期値設定対応  
 */
public class mst011201_CgcHenpinKbDictionary {

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
	private mst011201_CgcHenpinKbDictionary(String code, String name) {
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
	public static mst011201_CgcHenpinKbDictionary getStatus(String key) {
		mst011201_CgcHenpinKbDictionary ret = (mst011201_CgcHenpinKbDictionary)map.get(key);
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
	public static mst011201_CgcHenpinKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst011201_CgcHenpinKbDictionary)) {
			return false;
		}
		String objStr = ((mst011201_CgcHenpinKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst011201_CgcHenpinKbDictionary REITO_SYOKUHIN		= new mst011201_CgcHenpinKbDictionary("0", "冷凍食品");
	public static mst011201_CgcHenpinKbDictionary REITO_SYOKUHIN_IGAI	= new mst011201_CgcHenpinKbDictionary("1", "冷凍食品以外");
	public static mst011201_CgcHenpinKbDictionary UNKNOWN		= new mst011201_CgcHenpinKbDictionary("X", "UNKNOWN");
	
	// [MSTC00013] 商品マスタ初期値設定対応 必要項目のみ記載
	public static mst011201_CgcHenpinKbDictionary NASI					= new mst011201_CgcHenpinKbDictionary("0", "発行なし");	
	

}
