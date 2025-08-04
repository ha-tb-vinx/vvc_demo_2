/**
 * <P>タイトル : RB Site</p>
 * <P>説明 : 発生元区分ディクショナリ</p>
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
 * <P>説明 : 発生元区分ディクショナリ</p>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst005601_HatuseiMotoKbDictionary {
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
	private mst005601_HatuseiMotoKbDictionary(String code, String name) {
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
	public static mst005601_HatuseiMotoKbDictionary getStatus(String key)
	{
		mst005601_HatuseiMotoKbDictionary ret = (mst005601_HatuseiMotoKbDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005601_HatuseiMotoKbDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst005601_HatuseiMotoKbDictionary getStatus(int key)
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

	public static mst005601_HatuseiMotoKbDictionary TANPIN_MISE_TOUROKU = new mst005601_HatuseiMotoKbDictionary("1", "単品店マスタ登録");
	public static mst005601_HatuseiMotoKbDictionary TANPIN_MISE_EXCEL = new mst005601_HatuseiMotoKbDictionary("2", "単品店excel登録");
	public static mst005601_HatuseiMotoKbDictionary TANPIN_JIDOU_SAKUSEI = new mst005601_HatuseiMotoKbDictionary("3", "単品店自動作成");
	public static mst005601_HatuseiMotoKbDictionary SHOKAI_DONYU_TOUROKU = new mst005601_HatuseiMotoKbDictionary("4", "初回導入登録");
	public static mst005601_HatuseiMotoKbDictionary SHOKAI_DONYU_EXCEL = new mst005601_HatuseiMotoKbDictionary("5", "初回導入excel登録");
	public static mst005601_HatuseiMotoKbDictionary HANSOKU_KEIKAKU_SINNKI_TANPIN_MISE = new mst005601_HatuseiMotoKbDictionary("6", "販促計画：新規単品店");
	public static mst005601_HatuseiMotoKbDictionary NON_ACT_JYOKEN = new mst005601_HatuseiMotoKbDictionary("7", "NON-ACT条件");
	public static mst005601_HatuseiMotoKbDictionary HOST_BOS = new mst005601_HatuseiMotoKbDictionary("8", "ホスト：ＢＯＳ");
	public static mst005601_HatuseiMotoKbDictionary JIDOU_SAIBANN = new mst005601_HatuseiMotoKbDictionary("9", "自動廃番");
	public static mst005601_HatuseiMotoKbDictionary KARI_TOUROKU = new mst005601_HatuseiMotoKbDictionary("10", "仮登録");
	public static mst005601_HatuseiMotoKbDictionary UNKNOWN = new mst005601_HatuseiMotoKbDictionary("X","UNKNOWN");
}
