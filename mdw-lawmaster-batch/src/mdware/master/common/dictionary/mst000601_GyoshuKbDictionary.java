/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 業種区分ディクショナリ</p>
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
 * <P>説明 : 業種区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @see なし
 */
public class mst000601_GyoshuKbDictionary {
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
	private mst000601_GyoshuKbDictionary(String code, String name) {
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
	public static mst000601_GyoshuKbDictionary getStatus(String key)
	{
		mst000601_GyoshuKbDictionary ret = (mst000601_GyoshuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000601_GyoshuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst000601_GyoshuKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst000601_GyoshuKbDictionary) )
			return false;
		String objStr = ((mst000601_GyoshuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}
//  ↓↓2006.06.09 xubq カスタマイズ修正↓↓
//	public static mst000601_GyoshuKbDictionary A08 = new mst000601_GyoshuKbDictionary("A08", "衣料8桁");
//	public static mst000601_GyoshuKbDictionary A12 = new mst000601_GyoshuKbDictionary("A12", "衣料12桁");
//	public static mst000601_GyoshuKbDictionary LIV = new mst000601_GyoshuKbDictionary("LIV", "住生活");
//	public static mst000601_GyoshuKbDictionary GRO = new mst000601_GyoshuKbDictionary("GRO", "加工食品");
//	public static mst000601_GyoshuKbDictionary FRE = new mst000601_GyoshuKbDictionary("FRE", "生鮮");
	public static mst000601_GyoshuKbDictionary A08 = new mst000601_GyoshuKbDictionary("A08", MessageUtil.getMessage("MST000601_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000601_GyoshuKbDictionary A07= new mst000601_GyoshuKbDictionary("A07", MessageUtil.getMessage("MST000601_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000601_GyoshuKbDictionary GRO = new mst000601_GyoshuKbDictionary("GRO", MessageUtil.getMessage("MST000601_TXT_00003",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
//	↓↓2006.09.07 H.Yamamoto カスタマイズ修正↓↓
//	public static mst000601_GyoshuKbDictionary FRE = new mst000601_GyoshuKbDictionary("FRE", "ディリー");
	public static mst000601_GyoshuKbDictionary FRE = new mst000601_GyoshuKbDictionary("FRE", MessageUtil.getMessage("MST000601_TXT_00004",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
//	↑↑2006.09.07 H.Yamamoto カスタマイズ修正↑↑
//  ↑↑2006.06.09 xubq カスタマイズ修正↑↑	
//===== Begin ===== Add By Kou 2006/7/8 使われていないクラスにもコンパイルエラー出ないようにするため =====
	public static mst000601_GyoshuKbDictionary A12 = new mst000601_GyoshuKbDictionary("A12", MessageUtil.getMessage("MST000601_TXT_00005",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static mst000601_GyoshuKbDictionary LIV = new mst000601_GyoshuKbDictionary("LIV", MessageUtil.getMessage("MST000601_TXT_00006",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
//===== End ===== Add By Kou 2006/7/8 使われていないクラスにもコンパイルエラー出ないようにするため =====

	public static mst000601_GyoshuKbDictionary UNKNOWN = new mst000601_GyoshuKbDictionary("X","UNKNOWN");
}
