package mdware.master.common.dictionary;

/**
 * 
 * <p>タイトル: IF用定数宣言クラス</p>
 * <p>説明　: 新ＭＤシステムで使用する定数を宣言するクラス(IF関連用)</P>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.11.05) T.Ooshiro [CUS00059] ランドローム様対応 D3(DWH)システムインターフェイス仕様変更対応
 * @version 3.01 (2013.11.26) T.Ooshiro [CUS00063] POSインターフェイス仕様変更対応
 * @version 3.02 (2013.12.16) T.Ooshiro [CUS00066] POPインターフェイス仕様変更対応
 * @version 3.03 (2013.12.24) T.Ooshiro [CUS00065] 計量器インターフェイス仕様変更対応
 *
 */
public class mst000102_IfConstDictionary {

	/** DWH連携用 企業コード*/
	public static final String DWH_COMP_CD = "0001";
	/** DWH連携用 削除区分：営業中・運用中 等(削除対象外)【0】 */
	public static final String DWH_DELETE_KB_ACTIVE = "0";
	/** DWH連携用 削除区分：廃止【9】 */
	public static final String DWH_DELETE_KB_HAISI = "9";
	/** DWH連携用 カテゴリ階層レベル デプト */
	public static final String CATEGORY_LEVEL_DPT = "1";
	/** DWH連携用 カテゴリ階層レベル ライン */
	public static final String CATEGORY_LEVEL_LINE = "2";
	/** DWH連携用 カテゴリ階層レベル クラス */
	public static final String CATEGORY_LEVEL_CLASS = "3";

	/** エラー区分：正常 */
	public static final String ERROR_KB_NORMAL = "00";
	/** エラー区分：桁数エラー */
	public static final String ERROR_KB_01 = "01";
	/** エラー区分：店舗マスタエラー */
	public static final String ERROR_KB_02 = "02";
	/** エラー区分：商品マスタエラー */
	public static final String ERROR_KB_03 = "03";
//  2013/12/24 [CUS00065] 計量器インターフェイス仕様変更対応(S)
	/** エラー区分：コード重複エラー */
	public static final String ERROR_KB_04 = "04";
//  2013/12/24 [CUS00065] 計量器インターフェイス仕様変更対応(E)

	/** 商品コード種別：２３コード */
	public static final String SYOHIN_CD_SYUBETU_23 = "01";
	/** 商品コード種別：０２コード */
	public static final String SYOHIN_CD_SYUBETU_02 = "02";
	/** 商品コード種別：生鮮ＥＤＩコードコード */
	public static final String SYOHIN_CD_SYUBETU_EDI = "03";
	/** 商品コード種別：キーPLU4コード */
	public static final String SYOHIN_CD_SYUBETU_KEY_PLU_4 = "04";
	/** 商品コード種別：キーPLU5コード */
	public static final String SYOHIN_CD_SYUBETU_KEY_PLU_5 = "05";
	/** 商品コード種別：JAN13コード */
	public static final String SYOHIN_CD_SYUBETU_JAN_13 = "06";
	/** 商品コード種別：JAN8コード */
	public static final String SYOHIN_CD_SYUBETU_JAN_8 = "07";
	/** 商品コード種別：EAN13コード */
	public static final String SYOHIN_CD_SYUBETU_EAN_13 = "08";
	/** 商品コード種別：EAN8コード */
	public static final String SYOHIN_CD_SYUBETU_EAN_8 = "09";
	/** 商品コード種別：UPC-A */
	public static final String SYOHIN_CD_SYUBETU_UPC_A = "10";
	/** 商品コード種別：UPC-E */
	public static final String SYOHIN_CD_SYUBETU_UPC_E = "11";
//  2013/12/16 [CUS00066] POPインターフェイス仕様変更対応(S)
	/** 商品コード種別：代表商品コード */
	public static final String SYOHIN_CD_SYUBETU_DAIHYO = "12";
//  2013/12/16 [CUS00066] POPインターフェイス仕様変更対応(E)

//  2013/11/22 [CUS00063] POSインターフェイス仕様変更対応(S)
	/** データ種別 新規・訂正 */
	public static final String DATA_KB_SHINKI_TEISEI = "10";
	/** データ種別 削除 */
	public static final String DATA_KB_SAKUJO = "20";
//  2013/11/22 [CUS00063] POSインターフェイス仕様変更対応(E)
	
	//add 2016.08.31 nv.cuong(S)
	//登録ID
	public static final String TOROKU_ID = "A";
	
	//DEL_登録ID
	public static final String TOROKU_ID_DEL = "D";
	//add 2016.08.31 nv.cuong(E)

}
