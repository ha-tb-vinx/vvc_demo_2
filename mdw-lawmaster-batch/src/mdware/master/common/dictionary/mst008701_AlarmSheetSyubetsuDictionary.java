/**
 * <P>タイトル： マスタ管理システム</p>
 * <P>説明　　： アラームデータ出力用シート種別ディクショナリ</p>
 * <P>著作権　： Copyright (c) 2005</p>								
 * <P>会社名　： Vinculum Japan Corp.</P>								
 * @author 	 A.Tozaki
 * @version 	 1.0
 * @see		 なし								
 */
package mdware.master.common.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>タイトル： マスタ管理システム</p>
 * <P>説明　　： アラームデータ出力用シート種別ディクショナリ</p>
 * <P>著作権　： Copyright (c) 2005</p>								
 * <P>会社名　： Vinculum Japan Corp.</P>								
 * @author 	 A.Tozaki
 * @version 	 1.0
 * @see		 なし								
 */
public class mst008701_AlarmSheetSyubetsuDictionary {
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
	private mst008701_AlarmSheetSyubetsuDictionary(String code, String name) {
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
	public static mst008701_AlarmSheetSyubetsuDictionary getStatus(String key)
	{
		mst008701_AlarmSheetSyubetsuDictionary ret = (mst008701_AlarmSheetSyubetsuDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008701_AlarmSheetSyubetsuDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return TaisyoKbDictionary
	 */
	public static mst008701_AlarmSheetSyubetsuDictionary getStatus(int key)
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
		if( !(obj instanceof mst008701_AlarmSheetSyubetsuDictionary) )
			return false;
		String objStr = ((mst008701_AlarmSheetSyubetsuDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

//	public static mst008701_AlarmSheetSyubetsuDictionary SYOHIN			= new mst008701_AlarmSheetSyubetsuDictionary("01", "商品マスタ");
//	public static mst008701_AlarmSheetSyubetsuDictionary ASSORT			= new mst008701_AlarmSheetSyubetsuDictionary("02", "アソートメント");
//	public static mst008701_AlarmSheetSyubetsuDictionary TANPINTEN		= new mst008701_AlarmSheetSyubetsuDictionary("03", "単品店");
//	public static mst008701_AlarmSheetSyubetsuDictionary REIGAI			= new mst008701_AlarmSheetSyubetsuDictionary("04", "店別例外");
//	public static mst008701_AlarmSheetSyubetsuDictionary SYOKAI			= new mst008701_AlarmSheetSyubetsuDictionary("05", "初回導入");
	public static mst008701_AlarmSheetSyubetsuDictionary EXCEL			= new mst008701_AlarmSheetSyubetsuDictionary("01", "EXCEL登録");
	public static mst008701_AlarmSheetSyubetsuDictionary SYOHIN_IKKATU	= new mst008701_AlarmSheetSyubetsuDictionary("11", "商品一括登録");
	public static mst008701_AlarmSheetSyubetsuDictionary SAKUJYO			= new mst008701_AlarmSheetSyubetsuDictionary("12", "削除勧告");
	public static mst008701_AlarmSheetSyubetsuDictionary HAIBAN_KEIKOKU	= new mst008701_AlarmSheetSyubetsuDictionary("13", "廃番警告");
	public static mst008701_AlarmSheetSyubetsuDictionary HAIBAN_JISSI		= new mst008701_AlarmSheetSyubetsuDictionary("14", "廃番実施");
//	public static mst008701_AlarmSheetSyubetsuDictionary KEIRYOKI			= new mst008701_AlarmSheetSyubetsuDictionary("80", "計量器未送信");
//	public static mst008701_AlarmSheetSyubetsuDictionary BAIHEN			= new mst008701_AlarmSheetSyubetsuDictionary("90", "売価変更");
	public static mst008701_AlarmSheetSyubetsuDictionary UNKNOWN			= new mst008701_AlarmSheetSyubetsuDictionary("X","UNKNOWN");
	
}
