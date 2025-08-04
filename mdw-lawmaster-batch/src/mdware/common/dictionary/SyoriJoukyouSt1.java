package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: Ｅダウンロードファイルヘッダ内の処理状況フラグデータディクショナリ</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class SyoriJoukyouSt1 {
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
	private SyoriJoukyouSt1(String code, String name) {
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
	 * @return SyoriJoukyouSt1
	 */
	public static SyoriJoukyouSt1 getStatus(String key)
	{
		SyoriJoukyouSt1 ret = (SyoriJoukyouSt1)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyoriJoukyouSt1
	 */
	public static SyoriJoukyouSt1 getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyoriJoukyouSt1
	 */
	public static SyoriJoukyouSt1 getStatus(int key)
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
		if( !(obj instanceof SyoriJoukyouSt1) )
			return false;
		String objStr = ((SyoriJoukyouSt1)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static SyoriJoukyouSt1 MISOUSIN = new SyoriJoukyouSt1("0","未送信");
	public static SyoriJoukyouSt1 JUSINZUMI = new SyoriJoukyouSt1("1","受信済");
	public static SyoriJoukyouSt1 ERROR = new SyoriJoukyouSt1("2","エラー");
	public static SyoriJoukyouSt1 SAKUJO = new SyoriJoukyouSt1("3","削除");
	public static SyoriJoukyouSt1 MISYORI = new SyoriJoukyouSt1("4","未処理");
	public static SyoriJoukyouSt1 SYORIZUMI = new SyoriJoukyouSt1("5","処理済");
	public static SyoriJoukyouSt1 UNKNOWN = new SyoriJoukyouSt1("X","現在未定コード");
}