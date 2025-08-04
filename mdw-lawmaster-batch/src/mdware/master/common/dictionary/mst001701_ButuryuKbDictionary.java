/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 物流区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kikuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 物流区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @version 3.00 (2013.05.12)    M.Ayukawa [MSTC00013] ランドローム様  商品マスタ初期値設定対応
 * @see なし								
 */
public class mst001701_ButuryuKbDictionary {
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
	private mst001701_ButuryuKbDictionary(String code, String name) {
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
	public static mst001701_ButuryuKbDictionary getStatus(String key)
	{
		mst001701_ButuryuKbDictionary ret = (mst001701_ButuryuKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001701_ButuryuKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst001701_ButuryuKbDictionary getStatus(int key)
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
		if( !(obj instanceof mst001701_ButuryuKbDictionary) )
			return false;
		String objStr = ((mst001701_ButuryuKbDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

/*
	public static mst001701_ButuryuKbDictionary HINBAN_SHOUHIN = new mst001701_ButuryuKbDictionary("1", "店直納");
	public static mst001701_ButuryuKbDictionary TC = new mst001701_ButuryuKbDictionary("2", "ＴＣ");
	public static mst001701_ButuryuKbDictionary SOURYOU_IKKATU = new mst001701_ButuryuKbDictionary("3", "総量一括");
	public static mst001701_ButuryuKbDictionary DC = new mst001701_ButuryuKbDictionary("4", "ＤＣ");
	public static mst001701_ButuryuKbDictionary PC_CENTER = new mst001701_ButuryuKbDictionary("5", "ＰＣセンター");
*/
	// 必要分のみ定義
	public static mst001701_ButuryuKbDictionary RS = new mst001701_ButuryuKbDictionary("16","RS");																							
	public static mst001701_ButuryuKbDictionary UNKNOWN = new mst001701_ButuryuKbDictionary("X","UNKNOWN");																							
	
	
	//以下MM様では未使用だが、「SOURYOU_IKKATU」のみ未使用のソースで使用されている為、残しておく
	public static mst001701_ButuryuKbDictionary SOURYOU_IKKATU = new mst001701_ButuryuKbDictionary("1", "総量一括");
//	public static mst001701_ButuryuKbDictionary TC = new mst001701_ButuryuKbDictionary("2", "ＴＣ店別");
//	public static mst001701_ButuryuKbDictionary DC_JISYA = new mst001701_ButuryuKbDictionary("3", "ＤＣ自社");
//	public static mst001701_ButuryuKbDictionary DC = new mst001701_ButuryuKbDictionary("4", "ＤＣ預託");
//	public static mst001701_ButuryuKbDictionary TEN_CHOKUNO = new mst001701_ButuryuKbDictionary("5", "店直納");
//
//	public static mst001701_ButuryuKbDictionary INSUTOA_KAKOU = new mst001701_ButuryuKbDictionary("6", "インストア加工");
	
//  2013.05.06 [MSTC00013]  商品マスタ初期値設定対応 
	public static mst001701_ButuryuKbDictionary CHOKUNO = new mst001701_ButuryuKbDictionary("04","直納");


}
