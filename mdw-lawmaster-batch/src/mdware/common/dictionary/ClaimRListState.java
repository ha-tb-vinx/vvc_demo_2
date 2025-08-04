package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: 請求データ受信一覧画面処理状況用データディクショナリ</p>
 * <p>説明: 請求データ受信一覧画面で処理状況表示に必要なデータディクショナリを定義します。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author M.Kurata
 * @version 0.01
 */

public class ClaimRListState {
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
	private ClaimRListState(String code, String name) {
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
	 * @return ClaimRListState
	 */
	public static ClaimRListState getStatus(String key)
	{
		ClaimRListState ret = (ClaimRListState)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ClaimRListState
	 */
	public static ClaimRListState getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return ClaimRListState
	 */
	public static ClaimRListState getStatus(int key)
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
		if( !(obj instanceof ClaimRListState) )
			return false;
		String objStr = ((ClaimRListState)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static ClaimRListState RECIEVED = new ClaimRListState("1","受信済");
	public static ClaimRListState FINISH = new ClaimRListState("4","処理済");
	public static ClaimRListState ERROR = new ClaimRListState("2","エラー");
	public static ClaimRListState DELETE = new ClaimRListState("3","削除");
    public static ClaimRListState UNKNOWN = new ClaimRListState("X","UNKNOWN");

}