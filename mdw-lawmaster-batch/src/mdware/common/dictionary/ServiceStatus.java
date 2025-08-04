package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 生鮮の使用可能判断で使用</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author nakazawa
 * @version 1.0
 */

public class ServiceStatus {
	
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
	private ServiceStatus(String code, String name) {
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
	 * @return ServiceStatus
	 */
	public static ServiceStatus getStatus(String key)
	{
		ServiceStatus ret = (ServiceStatus)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return AlarmKbDictionary
	 */
	public static ServiceStatus getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ServiceStatus
	 */
	public static ServiceStatus getStatus(int key)
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
		if( !(obj instanceof ServiceStatus) )
			return false;
		String objStr = ((ServiceStatus)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static ServiceStatus SERVICE_START 	= new ServiceStatus("0","全サービス開始期間");
	public static ServiceStatus SERVICE_START1 	= new ServiceStatus("1","サービス開始第１段階");
	public static ServiceStatus SERVICE_START2 	= new ServiceStatus("2","サービス開始第２段階");
	public static ServiceStatus SERVICE_STOP 		= new ServiceStatus("9","全サービス停止期間");
	public static ServiceStatus UNKNOWN 			= new ServiceStatus("X","UNKNOWN");
}
