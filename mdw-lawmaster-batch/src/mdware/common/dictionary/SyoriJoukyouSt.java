package mdware.common.dictionary;

import java.util.*;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: Ｅアップロードファイルヘッダ内の処理状況フラグデータディクショナリ</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class SyoriJoukyouSt {
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
	private SyoriJoukyouSt(String code, String name) {
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
	 * @return SyoriJoukyouSt
	 */
	public static SyoriJoukyouSt getStatus(String key)
	{
		SyoriJoukyouSt ret = (SyoriJoukyouSt)map.get(key);
		if( ret == null )
			ret = UNKNOWN;
		return ret;
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyoriJoukyouSt
	 */
	public static SyoriJoukyouSt getStatus(long key)
	{
		return getStatus(Long.toString(key));
	}

	/**
	 * コードで検索を行う
	 * @param key ＤＢ内のコード
	 * @return SyoriJoukyouSt
	 */
	public static SyoriJoukyouSt getStatus(int key)
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
		if( !(obj instanceof SyoriJoukyouSt) )
			return false;
		String objStr = ((SyoriJoukyouSt)obj).toString();
		String thisStr = toString();
		return objStr.equals( thisStr );
	}

	public static SyoriJoukyouSt MISOUSIN = new SyoriJoukyouSt("0","未送信");
	public static SyoriJoukyouSt SOUSINZUMI = new SyoriJoukyouSt("1","送信済");
	public static SyoriJoukyouSt ERROR = new SyoriJoukyouSt("2","エラー");
	public static SyoriJoukyouSt SAKUJO = new SyoriJoukyouSt("3","削除");
	public static SyoriJoukyouSt MISYORI = new SyoriJoukyouSt("4","未処理");
	public static SyoriJoukyouSt SYORIZUMI = new SyoriJoukyouSt("5","処理済");
	public static SyoriJoukyouSt UNKNOWN = new SyoriJoukyouSt("X","現在未定コード");
}