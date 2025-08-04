/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 単価一律修正区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kikuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 単価一律修正区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @see なし
 */
public class mst005501_TankaItirituSyuseiDictionary {
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
	private mst005501_TankaItirituSyuseiDictionary(String code, String name) {
		this.code = code;
		this.name = name;
		map.put(code,this);
	}

	/**
	 * ＤＢ内コードの意味を返す
	 * @return String
	 */
	public String toString()
	{
		return name;
	}

	/**
	 * ＤＢ内のコードを返す
	 * @return String
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005501_TankaItirituSyuseiDictionary getStatus(String key)
	{
		mst005501_TankaItirituSyuseiDictionary ret = (mst005501_TankaItirituSyuseiDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005501_TankaItirituSyuseiDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005501_TankaItirituSyuseiDictionary getStatus(int key)
	{
		return getStatus(Integer.toString(key));
	}

	/**
	 * 同じかどうかを比較する
	 * @param obj 比較の対象オブジェクト
	 * @return おなじかどうか
	 */
	public boolean equals( Object obj )
	{
		if( !(obj instanceof mst005501_TankaItirituSyuseiDictionary) )
			return false;
		String objStr = ((mst005501_TankaItirituSyuseiDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static mst005501_TankaItirituSyuseiDictionary PERCENT_OFF = new mst005501_TankaItirituSyuseiDictionary("1", MessageUtil.getMessage("MST005501_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst005501_TankaItirituSyuseiDictionary PERCENT_UP = new mst005501_TankaItirituSyuseiDictionary("2", MessageUtil.getMessage("MST005501_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst005501_TankaItirituSyuseiDictionary ENHIKI = new mst005501_TankaItirituSyuseiDictionary("3", MessageUtil.getMessage("MST005501_TXT_00003",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
//	↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
	public static mst005501_TankaItirituSyuseiDictionary ENSITEI = new mst005501_TankaItirituSyuseiDictionary("4", MessageUtil.getMessage("MST005501_TXT_00004",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
//	↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
	public static mst005501_TankaItirituSyuseiDictionary UNKNOWN = new mst005501_TankaItirituSyuseiDictionary("X","UNKNOWN");
}
