package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 利用ユーザマスタの利用ユーザ種別のデータディクショナリ</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class UserSyubetu {
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
	private UserSyubetu(String code, String name) {
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
	 * @return UserSyubetu
	 */
	public static UserSyubetu getStatus(String key)
	{
		UserSyubetu ret = (UserSyubetu)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return UserSyubetu
	 */
	public static UserSyubetu getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return UserSyubetu
	 */
	public static UserSyubetu getStatus(int key)
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
		if( !(obj instanceof UserSyubetu) )
			return false;
		String objStr = ((UserSyubetu)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static UserSyubetu TORIHIKI = new UserSyubetu("1","取引先");
	public static UserSyubetu ADMIN = new UserSyubetu("2","小売管理者");
	public static UserSyubetu KOURI = new UserSyubetu("3","小売業務担当者");
	public static UserSyubetu UNKNOWN = new UserSyubetu("X","UNKNOWN");
}