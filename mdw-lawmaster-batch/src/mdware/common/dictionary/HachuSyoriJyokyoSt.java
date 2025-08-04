package mdware.common.dictionary;

import java.util.*;
/**
 * <p>タイトル: 発注処理状況フラグデータディクショナリ</p>
 * <p>説明: E発注ファイルヘッダ情報内の処理状況フラグデータディクショナリクラスです。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yukiko yamanaka
 * @version 0.01
 */

public class HachuSyoriJyokyoSt {

    /**
     * DB内のコード
     */
    private String code = null;

    /**
     * DB内コードの意味
     */
    private String name = null;

    /**
     * 対応したクラスを保持するマップ
     */
    private static final Map map = new HashMap();

    /**
     * コンストラクタ
     * @param code
     * @param name
     */
    private HachuSyoriJyokyoSt(String code, String name) {
        this.code = code;
        this.name = name;
        map.put(code,this);
    }

    /**
     * DB内コードの意味を返す
     * @return String
     */
    public String toString()
    {
        return name;
    }

    /**
     * DB内のコードを返す
     * @return String
     */
    public String getCode()
    {
        return code;
    }

    /**
     * コードで検索を行う
     * @param key DB内のコード
     * @return HattyuSyoriJoukyouSt
     */
    public static HachuSyoriJyokyoSt getStatus(String key)
    {
        HachuSyoriJyokyoSt ret = (HachuSyoriJyokyoSt)map.get(key);
        if(ret == null)
            ret = UNKNOWN;
        return ret;
    }

    /**
     * コードで検索を行う
     * @param key DB内のコード
     * @return HattyuSyoriJoukyouSt
     */
    public static HachuSyoriJyokyoSt getStatus(long key)
    {
        return getStatus(Long.toString(key));
    }

    /**
     * コードで検索を行う
     * @param key DB内のコード
     * @return HattyuSyoriJoukyouSt
     */
    public static HachuSyoriJyokyoSt getStatus(int key)
    {
        return getStatus(Integer.toString(key));
    }

    /**
     * 同じかどうかを比較する
     * @param obj 比較の対象のオブジェクト
     * @return 同じかどうか
     */
    public boolean equals( Object obj )
    {
        if( !(obj instanceof HachuSyoriJyokyoSt ) )
            return false;
        String objStr = ((HachuSyoriJyokyoSt)obj).toString();
        String thisStr = toString();
        return objStr.equals(thisStr);
    }

// 20030412 add start by nakazawa for POROROCA
    //public static HachuSyoriJyokyoSt NEW = new HachuSyoriJyokyoSt("0","新規");
    //public static HachuSyoriJyokyoSt RENEW = new HachuSyoriJyokyoSt("2","再処理");
// 20030623 fuwa ﾏﾏｽﾄｱ用対応 start
	//public static HachuSyoriJyokyoSt NEW = new HachuSyoriJyokyoSt("0","未処理");
	public static HachuSyoriJyokyoSt NEW = new HachuSyoriJyokyoSt("0","未受信");
// 20030623 fuwa ﾏﾏｽﾄｱ用対応 end
	public static HachuSyoriJyokyoSt RENEW = new HachuSyoriJyokyoSt("2","処理済");
// 20030412 add end
// 20030623 fuwa ﾏﾏｽﾄｱ用対応 start
	public static HachuSyoriJyokyoSt FINISH = new HachuSyoriJyokyoSt("1","受信済");
// 20030623 fuwa ﾏﾏｽﾄｱ用対応 end
	public static HachuSyoriJyokyoSt CANCEL_NEW = new HachuSyoriJyokyoSt("3","取消（新規）");
    public static HachuSyoriJyokyoSt CANCEL_FINISH = new HachuSyoriJyokyoSt("4","取消（処理済）");
// 20021212 @ADD yamanaka V2でDB変更により start
    public static HachuSyoriJyokyoSt WITHOUT_APPROVAL = new HachuSyoriJyokyoSt("5","未承認");
// 20021212 @ADD yamanaka V2でDB変更により end
    public static HachuSyoriJyokyoSt UNKNOWN = new HachuSyoriJyokyoSt("X","UNKNOWN");

}