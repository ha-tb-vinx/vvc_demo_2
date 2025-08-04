package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル　: RB Site</p>
 * <p>説明　　　: 納品ファイルヘッダ 処理状況フラグディクショナリ</p>
 * <p>著作権　　: Copyright (c) 2004</p>
 * <p>会社名　　: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */
public class DeliveryJyokyoDictionary {
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
	private DeliveryJyokyoDictionary(String code, String name) {
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
	 * @return DeliveryJyokyoDictionary
	 */
	public static DeliveryJyokyoDictionary getStatus(String key)
	{
		DeliveryJyokyoDictionary ret = (DeliveryJyokyoDictionary)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return DeliveryJyokyoDictionary
	 */
	public static DeliveryJyokyoDictionary getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return DeliveryJyokyoDictionary
	 */
	public static DeliveryJyokyoDictionary getStatus(int key)
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
		if( !(obj instanceof DeliveryJyokyoDictionary) )
			return false;
		String objStr = ((DeliveryJyokyoDictionary)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	// 画面で表示させたい文言を採用しています
	// ("未確定"が2つありますが、小売側納品業務で未確定に該当するコードは TORIHIKISAKI_KAKUTEI になります)。
	// 実定義名は各右記のコメントの通りです。
	public static DeliveryJyokyoDictionary MIKAKUTEI = new DeliveryJyokyoDictionary("0", "未確定");				// 未確定
	public static DeliveryJyokyoDictionary TORIHIKISAKI_KAKUTEI = new DeliveryJyokyoDictionary("1", "未確定");	// 取引先確定済(小売未確定)
	public static DeliveryJyokyoDictionary KOURI_KAKUTEI = new DeliveryJyokyoDictionary("2", "確定");			// 小売確定済
	public static DeliveryJyokyoDictionary KEIJO = new DeliveryJyokyoDictionary("3", "計上済");					// 計上済
	public static DeliveryJyokyoDictionary SAKUJO = new DeliveryJyokyoDictionary("4", "削除");					// 削除
	public static DeliveryJyokyoDictionary UNKNOWN = new DeliveryJyokyoDictionary("X","現在未定コード");
}
