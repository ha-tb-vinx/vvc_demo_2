package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <p>タイトル:処理種別区分Dictionary </p>
 * <p>説明: 処理種別区分を保持する</p>
 * <p>著作権: Copyright  (C) 2007</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 */
public class mst010001_SyoriSyubetsuKbDictionary {

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
	private mst010001_SyoriSyubetsuKbDictionary(String code, String name) {
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
	 * @return mst010001_SyoriSyubetsuKbDictionary
	 */
	public static mst010001_SyoriSyubetsuKbDictionary getStatus(String key) {
		mst010001_SyoriSyubetsuKbDictionary ret = (mst010001_SyoriSyubetsuKbDictionary)map.get(key);
		if (ret == null) {
			ret = UNKNOWN;
		}
		return ret;

	}	

	/**
	 * コードで検索を行う
	 * @param int DB内のコード(key)
	 * @return mst010001_SyoriSyubetsuKbDictionary
	 */
	public static mst010001_SyoriSyubetsuKbDictionary getStatus(int key) {
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof mst010001_SyoriSyubetsuKbDictionary)) {
			return false;
		}
		String objStr = ((mst010001_SyoriSyubetsuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals(thisStr);
	}

	public static mst010001_SyoriSyubetsuKbDictionary IKKATU_SYUSEI	= new mst010001_SyoriSyubetsuKbDictionary("01", MessageUtil.getMessage("MST010001_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst010001_SyoriSyubetsuKbDictionary MST_SEIGOUSEI_CHK	= new mst010001_SyoriSyubetsuKbDictionary("02", MessageUtil.getMessage("MST010001_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst010001_SyoriSyubetsuKbDictionary MST_FUKADO_CHK	= new mst010001_SyoriSyubetsuKbDictionary("03", MessageUtil.getMessage("MST010001_TXT_00003",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst010001_SyoriSyubetsuKbDictionary UNKNOWN			= new mst010001_SyoriSyubetsuKbDictionary("X", "UNKNOWN");

}
