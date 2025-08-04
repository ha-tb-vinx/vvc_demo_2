/**
 * <P>タイトル : 定数宣言クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する定数を宣言するクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.00
 * @version 1.01 (2006/02/06) D.Matsumoto 削除フラグの区分定数追加
 * @version 1.02 (2006/03/29) S.JINNO     課金種別コードの種別 及び 区分定数追加
 * @version 2.01 (2010/09/02) A.Fujiwara  販促データワーニングチェック対応
 * @version 2.02 (2010/09/13) S.Hashiguchi 彩裕アイキャッチ取扱IF期間追加
 * @version 2.03 (2011/02/25) Y.Imai 計量器保存温度帯区分追加対応 MM00111
 * @version 2.04 (2012/06/18) Y.Imai Add 【MM00088】不稼働商品一覧CSV出力処理
 * @version 3.00 (2013/05/20) S.Matsushita [MSTC00007] ランドローム様  AS400商品マスタIF作成
 * @see なし
 */
package mdware.master.common.dictionary;

/**
 * <P>タイトル : 定数宣言クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する定数を宣言するクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.00
 * @version 3.00 (2013/06/29) M.Ayukawa ランドローム様 便区分対応
 * @version 3.01 (2013/07/25) M.Ayukawa [MSTC00028] 商品変更リスト作成対応
 * @version 3.02 (2013/11/12) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応
 * @version 3.03 (2013/11/22) T.Ooshiro [CUS00063] POシステムインターフェイス仕様変更対応
 * @version 3.04 (2013/12/09) T.Ooshiro [CUS00066] POPシステムインターフェイス仕様変更対応
 * @version 3.05 (2013/12/16) O.Uemura  [CUS00050] POS売価不一致勧告対応
 * @version 3.06 (2013.12.20) T.Ooshiro [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応
 * @version 3.07 (2013.12.24) T.Ooshiro [CUS00059] D3(DWH)システムインターフェイス仕様変更対応 チェックディジット対応
 * @version 3.08 (2013.12.25) T.Ooshiro [CUS00059] [CUS00065] 計量器インターフェイス仕様変更対応
 * @Version 3.09 (2013.11.22) K.TO      [CUS00048] マスタ未使用項目
 * @Version 3.10 (2014.01.15) K.TO      [CUS00104] 計量器項目変更対応
 * @version 3.11 (2014.01.21) O.Uemura  [CUS00049] 商品マスタ変更リスト（バッチ）対応
 * @version 3.12 (2014.02.27) M.Ayukawa [シス0064] DWHマスタトリガーファイル作成
 * @version 3.13 (2014.03.21) Y.Tominaga[CUS00114] DWH（D3）消費税対応
 * @version 3.14 (2015.10.14) DAI.BQ FIVImart対応
 * @version 3.15 (2016.04.29) M.Kanno FIVImart様向け出力先フォルダ変更対応
 * @version 3.16 (2016.12.22) S.Takayama #3382 FIVIMART対応
 * @version 3.17 (2017.02.14) M.Son #3765 FIVIMART対応
 * @see なし
 */
public class mst000101_ConstDictionary {
	/**
	 * holdするもののsessionへの登録名
	 */
	public static final String SESSIONHOLDSNAME = "stclibSessionHoldName";
    /**
     * セションマネージャー引継用
     */
    public static final String[] SESSION_NAMES = new String[]{
        "userSession", "csvLine", "mstMenuBeanHolder", "mst000101_BackUrl",
        "mst000101_BackSesKeyName", "mst000101_BackSesKeyObject"
    };

	/**
	 * ＤＢ接続用の論理名
	 */
	public static final String CONNECTION_STR = "rbsite_ora";

    /**
     * 名称CTFマスター　種別No 大業種
     */
    public static final String LARGE_TYPE_OF_BUSINESS           = "0010";


//  ↓2009/01/20 コード"0020"～"0080"の統合・及び変数名変更
	/**
	 * 名称CTFマスター　分類１コード
	 */
	public static final String BUNRUI1			= "0020";

	/**
	 * 名称CTFマスター　分類２コード
	 */
	public static final String BUNRUI2			= "0030";

    /**
     * 名称CTFマスター　分類３コード
     */
    public static final String BUNRUI3			= "0040";

	/**
	 * 名称CTFマスター　分類４コード
	 */
	public static final String BUNRUI4			= "0050";

	/**
	 * 名称CTFマスター　分類５コード
	 */
	public static final String BUNRUI5			= "0060";

    /**
     * 名称CTFマスター　大分類１コード
     */
    public static final String DAIBUNRUI1			= "0070";

    /**
     * 名称CTFマスター　大分類２コード
     */
    public static final String DAIBUNRUI2			= "0080";

//  ↑2009/01/20 コード"0020"～"0080"の統合・及び変数名変更

	/**
	 * 名称CTFマスター　種別No 中間集計
	 */
	public static final String MIDDLE_TOTAL                     = "0025";

	/**
	 * 名称CTFマスター　種別No 副資材品種
	 */
	public static final String SUB_KIND                         = "0065";

    /**
     * 名称CTFマスター　種別No カラー(タグ）
     */
    public static final String COLOR                            = "0090";

	/** add by kema 06.09.19
	* 名称CTFマスター　種別No カラー(実用)
	*/
   public static final String COLOR2                            = "1150";

	/** add by Yamamoto 06.09.22
	* 名称CTFマスター　種別No 台帳区分
	*/
	public static final String DAICHO                           = "1160";

    /**
     * 名称CTFマスター　種別No サイズ
     */
    public static final String SIZE                             = "0100";

    /**
     * 名称CTFマスター　種別No ブランド
     */
    public static final String BRAND                            = "0110";

    /**
     * 名称CTFマスター　種別No 都道府県
     */
    public static final String ADMINISTRATIVE_DIVISIONS         = "0120";

    /**
     * 名称CTFマスター　種別No 国
     */
    public static final String COUNTRY                          = "0130";

    /**
     * 名称CTFマスター　種別No ＪＡＮメーカ名称
     */
    public static final String JAN_MAKER_NAME                   = "0140";

    /**
     * 名称CTFマスター　種別No メーカー名称
     */
    public static final String MAKER_NAME                       = "0150";

    /**
     * 名称CTFマスター　種別No 店別仕入先管理コード
     */
    public static final String SUPPLIER_MANAGEMENT_CODE_SHOP    = "0160";

    /**
     * 名称CTFマスター　種別No 集計品種
     */
    public static final String TOTAL_KIND                       = "0170";

    /**
     * 名称CTFマスター　種別No ゼロ品種
     */
    public static final String ZERO_KINDS                       = "0180";

    /**
     * 名称CTFマスター　種別No 輸入品区分
     */
    public static final String IMPORTS_DIVISION                 = "0190";

    /**
     * 名称CTFマスター　種別No ＥＯＳ区分
     */
    public static final String EOS_DIVISION                     = "0200";

    /**
     * 名称CTFマスター　種別No 発注停止区分
     */
    public static final String ORDER_STOP_DIVISION              = "0210";

    /**
     * 名称CTFマスター　種別No 商品区分
     */
    public static final String COMMODITY_DIVISION               = "0220";

    /**
     * 名称CTFマスター　種別No 物流区分
     */
    public static final String DISTRIBUTION_DIVISION            = "0230";

    /**
     * 名称CTFマスター　種別No リベート対象区分
     */
    public static final String REBATE_OBJECT_DIVISION           = "0240";

    /**
     * 名称CTFマスター　種別No 販売期限区分
     */
    public static final String SALES_TIME_LIMIT_DIVISION        = "0250";

    /**
     * 名称CTFマスター　種別No 自動削除対象区分
     */
    public static final String DIVISION_AUTOMATIC_DELETION      = "0260";

    /**
     * 名称CTFマスター　種別No ＧＯＴ無条件表示対象
     */
    public static final String GOT_UNCONDITIONAL_DISPLAYT       = "0270";

    /**
     * 名称CTFマスター　種別No ＰＣ発行区分
     */
    public static final String PC_ISSUE_DIVISION                = "0280";

    /**
     * 名称CTFマスター　種別No 値札区分
     */
    public static final String PRICE_TAG_DIVISION               = "0290";

    /**
     * 名称CTFマスター　種別No よりどり種類
     */
    public static final String VARIOUS_KINDS                    = "0300";

    /**
     * 名称CTFマスター　種別No シーズン
     */
    public static final String SEASON                           = "0310";

    /**
     * 名称CTFマスター　種別No 服種
     */
    public static final String CLOTHES_KIND                     = "0320";

    /**
     * 名称CTFマスター　種別No スタイル
     */
    public static final String STYLE                            = "0330";

    /**
     * 名称CTFマスター　種別No シーン
     */
    public static final String SCENE                            = "0340";

    /**
     * 名称CTFマスター　種別No 性別
     */
    public static final String SEX                              = "0350";

    /**
     * 名称CTFマスター　種別No 世代
     */
    public static final String GENERATION                       = "0360";

    /**
     * 名称CTFマスター　種別No 年代
     */
    public static final String AGE                              = "0370";

    /**
     * 名称CTFマスター　種別No 素材
     */
    public static final String MATERIAL                         = "0380";

    /**
     * 名称CTFマスター　種別No 柄パターン
     */
    public static final String HANDLE_PATTERN                   = "0390";

    /**
     * 名称CTFマスター　種別No 織り編み
     */
    public static final String WEAVING_KNITTING                 = "0400";

    /**
     * 名称CTFマスター　種別No 素材付加機能
     */
    public static final String MATERIAL_ADDITION_FUNCTION       = "0410";

    /**
     * 名称CTFマスター　種別No センタ在庫区分
     */
    public static final String CENTER_STOCK_DIVISION            = "0420";

    /**
     * 名称CTFマスター　種別No 定貫区分
     */
    public static final String WEIGHT_OF_STANDARD               = "0430";

    /**
     * 名称CTFマスター　種別No ユニットプライス単位区分
     */
    public static final String UNIT_PRICE_UNIT_AMOUNT           = "0440";

    /**
     * 名称CTFマスター　種別No 納品温度帯
     */
    public static final String DELIVERED_TEMPERATURE_BELT       = "0450";


    /**
     * 名称CTFマスター　種別No 酒税報告分類
     */
    public static final String LIQUOR_TAX_REPORT_DIVIDE         = "0460";

    /**
     * 名称CTFマスター　種別No 相場商品
     */
    public static final String MARKET_PRICE_COMMODITY           = "0470";

    /**
     * 名称CTFマスター　種別No 横もち区分
     */
    public static final String SIDEWISE_POSSESSION_DIVISION     = "0480";

    /**
     * 名称CTFマスター　種別No 納品リードタイム
     */
    public static final String DELIVERED_LEAD_TIME              = "0490";

    /**
     * 名称CTFマスター　種別No キーＰＬＵタイム
     */
    public static final String KEY_PLU_TIME                     = "0500";

    /**
     * 名称CTFマスター　種別No 情報処理料金請求区分
     */
    public static final String INFORMATION_CHARGE_DIVISION      = "0510";

    /**
     * 名称CTFマスター　種別No 商品コード印字区分
     */
    public static final String COMMODITY_CODE_PRINT_DIVISION    = "0520";

    /**
     * 名称CTFマスター　種別No タグ発行代行業者
     */
    public static final String TAG_ISSUE_AGENCY_TRADER          = "0530";

    /**
     * 名称CTFマスター　種別No 店舗区分
     */
    public static final String STORE_DIVISION                   = "0540";

    /**
     * 名称CTFマスター　種別No 店舗タイプ
     */
    public static final String STORE_TYPE                       = "0550";

    /**
     * 名称CTFマスター　種別No 便配送
     */
    public static final String SERVICE_DELIVERY                 = "0560";


    /**
     * 名称CTFマスター　種別No 販売政策区分
     */
    public static final String SALES_POLICY_DIVISION            = "0570";


    /**
     * 名称CTFマスター　種別No 発注パターン
     */
    public static final String ORDER_PATTERN                    = "0580";

    /**
     * 名称CTFマスター　種別No 発行場所
     */
    public static final String ISSUE_PLACE                      = "0590";

    /**
     * 名称CTFマスター　種別No 大属性
     */
    public static final String LARGE_ATTRIBUTE                  = "0600";

    /**
     * 名称CTFマスター　種別No 小属性
     */
    public static final String SMALL_ATTRIBUTE                  = "0610";

    /**
     * 名称CTFマスター　種別No 品種レシート名
     */
    public static final String KIND_RECEIPT_NAME                = "0620";

    /**
     * 名称CTFマスター　種別No 発注締め時間
     */
    public static final String ORDER_TIGHTENING_TIME            = "0630";

	/**
	 * 名称CTFマスター　種別No センターパターン
	 */
	public static final String CENTER_PATTERN                   = "0640";

	/**
	 * 名称CTFマスター　種別No 品揃区分
	 */
	public static final String GOODS_SORO_DIVISION              = "0650";

	/**
	 * 名称CTFマスター　種別No テーマ区分
	 */
	public static final String THEME_DIVISION                   = "0660";

	/**
	 * 名称CTFマスター　種別No 品揃え区分(2005.05.24 Inaba 追加)
	 */
	public static final String SHINAZOROE_DIVISION              = "0690";

	/**
	 * 名称CTFマスター　非課税販区
	 */
	public static final String TAX_FREE                         = "0700";

    /**
     * 名称CTFマスター　種別No 計量販区
     */
    public static final String MEASUREMENT_SALES_DIVISION       = "0801";

    /**
     * 名称CTFマスター　種別No ＪＡＮ区分
     */
    public static final String JAN_DIVISION                     = "0802";

    /**
     * 名称CTFマスター　種別No 定額・ＵＰ区分
     */
    public static final String FIXED_AMOUNT_AND_UP_DIVISION     = "0803";

    /**
     * 名称CTFマスター　種別No 定額時単位
     */
    public static final String UNIT_OF_FIXED_AMOUNT             = "0804";

    /**
     * 名称CTFマスター　種別No 賞味期間計算区分
     */
    public static final String RELISH_PERIOD_DIVISION           = "0805";

    /**
     * 名称CTFマスター　種別No 産地番号
     */
    public static final String HOME_NUMBER                      = "0806";

    /**
     * 名称CTFマスター　種別No 加工時刻印字区分
     */
    public static final String PRINT_DIVISION_PROCESSING_TIME   = "0807";

    /**
     * 名称CTFマスター　種別No 調理用広告文
     */
    public static final String ADVERTISING_SENTENCE_COOKING     = "0808";

    /**
     * 名称CTFマスター　種別No 保存温度帯
     */
    public static final String PRESERVATION_TEMPERATURE_BELT    = "0809";

    /**
     * 名称CTFマスター　種別No 開始日付
     */
    public static final String BEGINNING_DATE                   = "0810";

    /**
     * 名称CTFマスター　種別No 裏面ラベル項目文
     */
    public static final String BACK_LABEL_ITEM_SENTENCE         = "0811";

	/**
	 * 名称CTFマスター　種別No コメント区分
	 */
	public static final String COMMENT					       = "0812";

	/**
	 * 名称CTFマスター　部門コード
	 */
	public static final String KAKIN_SYUBETU						= "0710";

	/**
	 * 名称CTFマスター　PB区分
	 */
	public static final String PB_DIVISION						= "1080";

	/**
	 * 名称CTFマスター　台紙NO（プライスカード種類）
	 */
	public static final String DAISHI_NO							= "1090";

	/**
	 * 名称CTFマスター　商品台帳（店舗）
	 */
	public static final String GOODS_LEDGER_STORE					= "1100";

	/**
	 * 名称CTFマスター　商品台帳（本部）
	 */
	public static final String GOODS_LEDGER_CENTER				= "1110";

	/**
	 * 名称CTFマスター　商品台帳（仕入先）
	 */
	public static final String GOODS_LEDGER_VENDOR				= "1120";

	/**
	 * 名称CTFマスター　値札受渡方法
	 */
	public static final String RECEIVE_OF_TAG						= "1070";

	/**
	 * 名称CTFマスター　地区コード
	 */
	public static final String AREA_CODE							= "1010";

	/**
	 * 名称CTFマスター　袖丈コード
	 */
	public static final String LENGTH_OF_SLEEVE					= "1140";

	/**
	 * 名称CTFマスター　FUJI商品区分
	 */
	public static final String GOODS_DIVISION						= "1060";

	/**
	 * 名称CTFマスター　本部在庫区分
	 */
	public static final String STOCK_DIVISION						= "1130";

	/**
	 * 名称CTFマスター　新旧POS区分
	 */
	public static final String POS_DIVISION						= "1040";

	/**
	 * 名称CTFマスター　プライスシール種類
	 */
	public static final String PRICE_SEAL_KIND					= "1090";

	/**
	 * 名称CTFマスター　業態区分
	 */
	public static final String GYOUTAI_DIVISION					= "1050";

	/**
	 * 名称CTFマスター　処理状態：エラーなしチェック済
	 */
	public static final String CHECKED_OK							= "0";

	/**
	 * 名称CTFマスター　処理状態：エラーチェック未済
	 */
	public static final String NO_CHECKED							= "1";

	/**
	 * 名称CTFマスター　処理状態：エラーありチェック済
	 */
	public static final String CHECKED_ERR						= "2";

//2010.09.02 A.Fujiwara Add 販促データワーニングチェック対応 Start
	/**
	 * 名称CTFマスター　処理状態：エラーありチェック済
	 */
	public static final String CHECKED_WARN						= "3";
//2010.09.02 A.Fujiwara Add 販促データワーニングチェック対応 End

	/**
	 * 名称CTFマスター　処理状態：エラーチェックバッチ処理中
	 */
	public static final String BATCH_CHECKING						= "9";

	/**
	 * 名称CTFマスター　アスティ区分
	 */
	public static final String ASTY_DIVISION                       = "1020";

	/**
	 * 名称CTFマスター　販売データ契約区分
	 */
	public static final String H_SALE_DATADIVISION                  = "1030";
//  ↑↑2006.06.09 xubq カスタマイズ修正↑↑

	/**
	 * 名称CTFマスター　仕入販売区分
	 */
	public static final String SIIRE_HANBAI_DIVISION                = "3010";

	/**
	 * 名称CTFマスター　税区分
	 */
	public static final String TAX_DIVISION                         = "3020";

	/**
	 * 名称CTFマスター　発注単位区分
	 */
	public static final String HACHU_TANI_DIVISION                  = "3030";

	/**
	 * 名称CTFマスター　販売単位区分
	 */
	public static final String HANBAI_TANI_DIVISION                 = "3040";

//2013.11.22 [CUS00048]  マスタ未使用項目 (S)
	/**
	 * 名称CTFマスター　任意区分１
	 */
	public static final String FREE_1_KB                            = "3710";

	/**
	 * 名称CTFマスター　任意区分２
	 */
	public static final String FREE_2_KB                            = "3720";

	/**
	 * 名称CTFマスター　任意区分３
	 */
	public static final String FREE_3_KB                            = "3730";

	/**
	 * 名称CTFマスター　任意区分４
	 */
	public static final String FREE_4_KB                            = "3740";

	/**
	 * 名称CTFマスター　任意区分５
	 */
	public static final String FREE_5_KB                            = "3750";
//2013.11.22 [CUS00048]  マスタ未使用項目 (E)

//2014.01.09 [CUS00104]  計量器項目変更対応 (S)
	/**
	 * 名称CTFマスター　種別No 加工日印字区分
	 */
	public static final String KAKOBI_PRINT_KB						= "3780";

	/**
	 * 名称CTFマスター　種別No 加工時刻印字区分
	 */
	public static final String KAKOJIKOKU_PRINT_KB					= "3790";

	/**
	 * 名称CTFマスター　種別No 選択コメントコード
	 */
	public static final String SENTAKU_COMMENT_CD					= "3800";

	/**
	 * 名称CTFマスター　種別No トレサビリティフラグ
	 */
	public static final String TRACEABILITY_FG						= "3810";

	/**
	 * 名称CTFマスター　種別No 定貫単位区分
	 */
	public static final String TEIKAN_TANI_KB						= "3820";
//2014.01.09 [CUS00104]  計量器項目変更対応 (E)

	/**
	 * 名称CTFマスター　F便区分
	 */
	public static final String FBIN_DIVISION                        = "3050";

	/**
	 * 名称CTFマスター　発注締時間
	 */
	public static final String SIME_TIME                            = "3060";

	/**
	 * 名称CTFマスター　便区分
	 */
	public static final String BIN_DIVISION                         = "3070";

	/**
	 * 名称CTFマスター　ケース発注区分
	 */
	public static final String CASE_HACHU_DIVISION                  = "3080";

	/**
	 * 名称CTFマスター　販売基準単位
	 */
	public static final String HANBAI_KIJUN_TANI                    = "3090";

	/**
	 * 名称CTFマスター　入荷基準単位
	 */
	public static final String NYUKA_KIJUN_TANI                     = "3100";

	/**
	 * 名称CTFマスター　CGC返品区分
	 */
	public static final String CGC_HENPIN_DIVISION                  = "3110";

	/**
	 * 名称CTFマスター　センター賞味期間区分
	 */
	public static final String CENTER_SYOMI_KIKAN_DIVISION          = "3120";

	/**
	 * 名称CTFマスター　アイキャッチNo.
	 */
	public static final String EYE_CATCH_NO                         = "3130";

	/**
	 * 名称CTFマスター　アイキャッチモード
	 */
	public static final String EYE_CATCH_MODE                       = "3140";

	/**
	 * 名称CTFマスター　M商品区分
	 */
	public static final String MSYOHIN_DIVISION                     = "3150";

	/**
	 * 名称CTFマスター　賞味期間区分
	 */
	public static final String SYOMI_KIKAN_DIVISION                 = "3160";

	/**
	 * 名称CTFマスター　配送区分
	 */
	public static final String HAISO_DIVISION                       = "3170";

	/**
	 * 名称CTFマスター　課税区分
	 */
	public static final String KAZEI_DIVISION                       = "3180";

	/**
	 * 名称CTFマスター　経由区分
	 */
	public static final String KEIYU_DIVISION                       = "3190";

	/**
	 * 名称CTFマスター　送料区分
	 */
	public static final String SORYO_DIVISION                       = "3200";

	/**
	 * 名称CTFマスター　会社コード
	 */
	public static final String COMP_CD                              = "3210";

	/**
	 * 名称CTFマスター　地区コード
	 */
	public static final String TIKU_CD                              = "3220";

	/**
	 * 名称CTFマスター　エリアコード
	 */
	public static final String AREA_CD                              = "3230";

	/**
	 * 名称CTFマスター　店舗規模区分
	 */
	public static final String TENPO_KIBO_DIVISION                  = "3240";

	/**
	 * 名称CTFマスター　全店区分
	 */
	public static final String ZENTEN_DIVISION                      = "3250";

	/**
	 * 名称CTFマスター　取扱区分
	 */
	public static final String TORIATUKAI_DIVISION                  = "3260";

	/**
	 * 名称CTFマスター　棚卸原価計算区分
	 */
	public static final String TANAOROSI_GENKA_DIVISION             = "3270";

	/**
	 * 名称CTFマスター　棚卸周期区分
	 */
	public static final String TANAOROSI_SYUKI_DIVISION             = "3280";

	/**
	 * 名称CTFマスター　電子棚札導入区分
	 */
	public static final String DENSI_TANAFUDA_DIVISION              = "3290";

	/**
	 * 名称CTFマスター　棚割使用区分
	 */
	public static final String TANAWARI_USE_DIVISION                = "3300";

	/**
	 * 名称CTFマスター　リードタイム
	 */
	public static final String LEAD_TIME                            = "3310";

	/**
	 * 名称CTFマスター　表示順（計量器呼出コード）
	 */
	public static final String KEIRYOKI_ORDERBY_DIVISION            = "3340";

	/**
	 * 名称CTFマスター　改ページ（計量器呼出コード）
	 */
	public static final String KEIRYOKI_KAIPAGE_DIVISION            = "3350";

	/**
	 * 名称CTFマスター　定額区分
	 */
	public static final String TEIGAKU_DIVISION                     = "3370";

	/**
	 * 名称CTFマスター　伝票区分
	 */
	public static final String DENPYO_DIVISION						= "3380";

	/**
	 * 名称CTFマスター　物流区分（店別例外）
	 */
	public static final String DISTRIBUTION_DIVISION_REIGAI         = "3390";

	/**
	 * 名称CTFマスター　抽出対象（計量器呼出コード）
	 */
	public static final String KEIRYOKI_WHERE_DIVISION              = "3400";

//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
	/**
	 * 名称CTFマスター　保存温度帯区分
	 */
	public static final String HOZON_ONDOTAI_DIVISION               = "3420";
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 End

	/**
	 * 名称CTFマスター　産直区分
	 */
	public static final String SANCHOKU_DIVISION                    = "3600";

	/**
	 * 名称CTFマスター　在庫区分
	 */
	public static final String ZAIKO_DIVISION                       = "3610";

	/**
	 * 名称CTFマスター　CGC区分
	 */
	public static final String CGC_DIVISION                         = "3620";
	
	//#3765 Add 2017.02.14 M.Son (S)
	/**
	 * 名称CTFマスター　国区分
	 */
	public static final String COUNTRY_DIVISION                     = "4070";
	//#3765 Add 2017.02.14 M.Son (E)

	/**
	 * ページフッター　リンク先
	 */
	public static final String[] PAGE_FOOTER_LINKS = new String[]{

		"mst020101_Menu",
		"returnMyPage"
	};

	/**
	 * 名称CTFマスター　サブシステムID名称
	 */
	public static final String SUBSYSTEMID_NAME                         = "1000";

	/**
	 * JSPコントロールエラー時前景色
	 */
	public static final String CONTROL_ERROR_COLOR = "#FF0000";

	/**
	 * JSPコントロールエラー時背景色
	 */
	public static final String CONTROL_ERROR_BACKCOLOR = "YELLOW";

	/**
	 * ログインパス
	 */
	public static final String LOGIN_PATH = "app?JobID=mst010101_LoginSyoki";

	/**
	 * メニューパス
	 */
	public static final String MENU_PATH = "mst010301_ReturnMyPage";

	/**
	 * MovePage First
	 */
	public static final String MOVEPAGE_FIRST = "first";

	/**
	 * MovePage PREV
	 */
	public static final String MOVEPAGE_PREV = "prev";

	/**
	 * MovePage KEEP
	 */
	public static final String MOVEPAGE_KEEP = "keep";

	/**
	 * MovePage NEXT
	 */
	public static final String MOVEPAGE_NEXT = "next";

	/**
	 * MovePage LAST
	 */
	public static final String MOVEPAGE_LAST = "last";

	/**
	 * 画面処理区分 新規
	 */
	public static final String PROCESS_INSERT = "0";

	/**
	 * 画面処理区分 更新
	 */
	public static final String PROCESS_UPDATE = "1";

	/**
	 * 画面処理区分 削除
	 */
	public static final String PROCESS_DELETE = "2";

	/**
	 * 画面処理区分 参照 通常用
	 */
	public static final String PROCESS_REFERENCE = "3";

	/**
	 * 画面処理区分 参照 mst420101(商品階層増マスタ)用
	 */
	public static final String PROCESS_REFERENCE_EXC = "4";

	/**
	 * 画面処理区分 予約取消用
	 */
	public static final String PROCESS_RESERVE_CANCEL = "5";

	/**
	 * 関数戻り値　データ存在
	 */
	public static final String FUNCTION_TRUE = "1";

	/**
	 * 関数戻り値　データ非存在
	 */
	public static final String FUNCTION_FALSE = "0";

	/**
	 * エラー区分　正常
	 */
	public static final String ERR_CHK_NORMAL = "N";

	/**
	 * エラー区分　エラー
	 */
	public static final String ERR_CHK_ERROR = "E";

	/**
	 * エラー区分　権限エラー
	 */
	public static final String ERR_CHK_AUTHORITY_ERROR = "S";

	/**
	 * 検索区分　データ部破棄検索
	 */
	public static final String CLEAR_SEARCH = "clearsearch";

	/**
	 * 検索区分　データ部保存検索
	 */
	public static final String HOLD_SEARCH = "holdsearch";
	/**
	 * 確定　連続登録
	 */
	public static final String CONTINUATION_INSERT = "continuation";

	/**
	 * 全店舗コード
	 */
	public static final String ALL_TENPO_CD = "000000";

	/**
	 * 全DPTコード
	 */
	public static final String ALL_DPT_CD = "00";

	/**
	 * チェック処理　初期
	 */
	public static final String CHECK_INIT = "0";

	/**
	 * チェック処理　検索
	 */
	public static final String CHECK_SEARCH = "1";

	/**
	 * チェック処理　更新（登録/修正/削除)
	 */
	public static final String CHECK_UPDATE = "2";

	/**
	 * チェック処理　照会
	 */
	public static final String CHECK_VIEW = "3";

	/**
	 * チェック処理　その他
	 */
	public static final String CHECK_OTHERS = "4";

	/**
	 * 更新処理内容 INSERT
	 */
	public static final String UPDATE_PROCESS_INSERT = "I";

	/**
	 * 更新処理内容 UPDATE
	 */
	public static final String UPDATE_PROCESS_UPDATE = "U";

	/**
	 * 更新処理内容 DELETE
	 */
	public static final String UPDATE_PROCESS_DELETE = "D";

	/**
	 * 法人コード
	 */
	public static final String HOJIN_CD = "00000";

	/**
	 * 取引先マスタ　会社コード
	 */
	public static final String TORIHIKISAKI_COMP_CD = "0000";

	/**
	 * 帳合区分　支払先
	 */
	public static final String CHOAI_DIVISION_SIHATAISAKI = "0";

	/**
	 * 帳合区分　仕入先
	 */
	public static final String CHOAI_DIVISION_SIIRESAKI = "1";

	/**
	 * 取引停止区分　有効
	 */
	public static final String TORIHIKI_TEISHI_DIVISION_YUKO = "0";

	/**
	 * 取引停止区分　停止
	 */
	public static final String TORIHIKI_TEISHI_DIVISION_TEISI = "1";

	/**
	 * 管理区分　DPT+取引先
	 */
	public static final String KANRI_DIVISION_DPT = "1";

	/**
	 * 管理区分　商品
	 */
	public static final String KANRI_DIVISION_SYOHIN = "2";

	/**
	 * 取消フラグ　しない
	 */
	public static final String TORIKESHI_FG_NOR = "0";

	/**
	 * 取消フラグ　する
	 */
	public static final String TORIKESHI_FG_DEL = "1";

	/**
	 * 発注方法区分
	 */
	public static final String HACHU_HOHO_DIVISION = "1";

	/**
	 * Dummyコード(All0(4桁))
	 */
	public static final String DUMMY_CD = "0000";


	/**
	 * Forwardcommand
	 */
	public static final String FORWARD_COMMAND = "Forward";

	/**
	 * 生鮮ユーザ判定用業種コード
	 */
	public static final String SEISEN_LGYOSHU = "0310";

	/**
	 * 生鮮ユーザ判定用小業種コード
	 */
	public static final String SEISEN_SGYOSHU = "3700";

	/**
	 * 運用ユーザ判定用権限コード
	 */
	public static final String[] UNYOU_KENGEN = new String[]{

		"01",
		"16",
		"17",
		"18",
		"19"
	};

	/**
	 * レコード存在判定用 存在コード
	 */
	public static final String RECORD_EXISTENCE = "1";

	/**
	 * レコード存在判定用 非存在コード
	 */
	public static final String RECORD_NON_EXISTENCE = "0";

	/**
	 * 全商品コード
	 */
	public static final String ALL_SYOHIN_CD = "0000000000000";

	/**
	 * 選択（チェックされた場合）
	 */
	public static final String SENTAKU_CHOICE = "1";

	/**
	 * JANメーカー判断コード
	 */
	public static final String[] JAN_MAKER = new String[]{

		"49",
		"45",
	};

	/**
	 * JANメーカー7桁or9桁判定フラグ
	 */
	public static final String JAN_MAKER_49   = "49";

	public static final String JAN_MAKER_45   = "45";

	public static final String JAN_MAKER_7_ST = "450";

	public static final String JAN_MAKER_7_ED = "455";

	public static final String JAN_MAKER_9_ST = "456";

	public static final String JAN_MAKER_9_ED = "459";

	/**
	 * 関数引数パターン_0
	 */
	public static final String FUNCTION_PARAM_0 = "0";
	/**
	 * 関数引数パターン_1
	 */
	public static final String FUNCTION_PARAM_1 = "1";
	/**
	 * 関数引数パターン_2
	 */
	public static final String FUNCTION_PARAM_2 = "2";
	/**
	 * 関数引数パターン_3
	 */
	public static final String FUNCTION_PARAM_3 = "3";

	/**
	 * インストア02コード
	 */
	public static final String INSTORE_02 = "02";
	/**
	 * インストア04コード
	 */
	public static final String INSTORE_04 = "0400";
	/**
	 * インストア04コード(頭2桁)
	 */
	public static final String INSTORE_04s = "04";
	/**
	 * インストア21コード
	 */
	public static final String INSTORE_21 = "21";

	/**
	 * 空白行あり
	 */
	public static final String BLANK_EXISTENCE = "1";

	/**
	 * 空白行なし
	 */
	public static final String BLANK_NON_EXISTENCE = "0";

	/**
	 * 処理状況設定用　使用可否
	 */
	public static final String PROCESS_DISABLED = "DISABLED";

	/**
	 * 処理状況設定用　チェック
	 */
	public static final String PROCESS_CHECKED = "CHECKED";

	/**
	 * 禁則文字チェック エラーメッセージ
	 */
	public static final String ERR_MSG_CODE = "40029";

	/**
	 * 便区分　１便
	 */
	public static final String SERVICE_DIVISION_FIRST = "1";
	/**
	 * 便区分　２便
	 */
	public static final String SERVICE_DIVISION_SECOND = "2";
	/**
	 * 便区分　３便
	 */
	public static final String SERVICE_DIVISION_THIRD = "3";
	/**
	 * 大業種コード指定（衣料）
	 */
	public static final String LARGE_TYPE_OF_BUSINESS_CLOTHES  = "0010";
	/**
	 * 大業種コード指定（住居）
	 */
	public static final String LARGE_TYPE_OF_BUSINESS_HOUSE  = "0120";
	/**
	 * 大業種コード指定（食品）
	 */
	public static final String LARGE_TYPE_OF_FOOD  = "0033";
	/**
	 * 小業種コード指定（グロッサリ）
	 */
	public static final String SMALL_TYPE_OF_GROCERY  = "0087";

	/**
	 * 名称CTFマスタ ALL*
	 */
//	public static final String NAMECTF_SYUBETU = "**********";
	public static final String NAMECTF_SYUBETU = "0000";
	/**
	 * 処理対象１
	 */
	public static final String PROCESSING_OBJECT1 = "1";
	/**
	 * 処理対象２
	 */
	public static final String PROCESSING_OBJECT2 = "2";
	/**
	 * 処理対象３
	 */
	public static final String PROCESSING_OBJECT3 = "3";
	/**
	 * 店別商品例外　存在（有）
	 */
	public static final String TENREIGAI = "有り";
	/**
	 * 店別商品例外　存在（無）
	 */
	public static final String TENREIGAI_NOT = "無し";
	//2006.01.22 T.hattori 追加　開始　先付け登録項目追加
	/**
	* 先付け登録確認　存在（有）
	*/
	public static final String SAKIDUKETOUROKU = "有り";
	/**
	* 先付け登録確認　存在（無）
	*/
	public static final String SAKIDUKETOUROKU_NOT = "無し";
	//2006.01.22 T.hattori 追加　開始　先付け登録項目追加
	/**
	 * 商品属性　存在（有）
	 */
	public static final String ZOKUSEI = "有り";
	/**
	 * 商品属性　存在（無）
	 */
	public static final String ZOKUSEI_NOT = "無し";
	/**
	 * アソートメント　存在（有）
	 */
	public static final String ASORTMENT = "有り";
	/**
	 * アソートメント　存在（無）
	 */
	public static final String ASORTMENT_NOT = "無し";

	/**
	 * 商品空番検索件数上限
	 */
	public static final long SEARCH__MAX_LIMIT = 10000;

	/**
	 * メニューでの表示最大行
	 */
	public static final String MENU_MAX_GYOU = "6";

	/**
	 * メニューでの表示最大列
	 */
	public static final String MENU_MAX_RETU = "5";

	/**
	 * サブメニューＩＤ(商品)
	 */
	public static final String SUB_MENU_ID_SYOHIN = "mst020101";

	/**
	 * サブメニューＩＤ(サブマスタ)
	 */
	public static final String SUB_MENU_ID_SUB = "mst030101";

	/**
	 * サブメニューＩＤ(PLU)
	 */
	public static final String SUB_MENU_ID_PLU = "mst040101";

	/**
	 * サブメニューＩＤ(外部システム連携)
	 */
	public static final String SUB_MENU_ID_RENKEI = "mst050101";

	// 2006/02/06 追加 削除フラグの区分定数追加
	/**
	 * 削除フラグ区分　通常
	 */
	public static final String DELETE_FG_NOR = "0";

	/**
	 * 削除フラグ区分　削除
	 */
	public static final String DELETE_FG_DEL = "1";

	/**
	 * システム区分
	 */
	public static final String SYSTEM_DIVISION = "G";

	/**
	 * システムコントロール　サブシステム区分
	 */
	public static final String SUBSYSTEM_DIVISION = "MASTER";

	/**
	 * システムコントロール　パラメータID 不稼動期間
	 */
	public static final String NONACT_SPAN = "NONACT_SPAN";

	/**
	 * システムコントロール　パラメータID 不稼動除外期間
	 */
	public static final String ACT_SPAN = "ACT_SPAN";

	/**
	 * システムコントロール　パラメータID 不要過去データ除外期間
	 */
	public static final String FUYOKAKO_DATA_TAISYOGAI_SPAN = "FUYOKAKO_DATA_TAISYOGAI_SPAN";


	/**
	 * システムコントロール　パラメータID 商品分類体系作成日
	 */
	public static final String TAIKEI_SAKUSEI_DT = "TAIKEI_SAKUSEI_DT";

	/**
	 * システムコントロール　パラメータID 商品分類体系切替日
	 */
	public static final String TAIKEI_KIRIKAE_DT = "TAIKEI_KIRIKAE_DT";

	/**
	 * システムコントロール　パラメータID オンラインフラグ
	 */
	public static final String ONLINE_FG = "ONLINE_FG";

	/**
	 * システムコントロール　パラメータID オンライン日付
	 */
	public static final String ONLINE_DT = "ONLINE_DT";

	/**
	 * システムコントロール　パラメータID バッチ日付
	 */
	public static final String BATCH_DT = "BATCH_DT";

	/**
	 * システムコントロール　パラメータID アップロード正常データ保持期間
	 */
	public static final String UPLOAD_OK_SPAN = "UPLOAD_OK_SPAN";

	/**
	 * システムコントロール　パラメータID アップロードエラーデータ保持期間
	 */
	public static final String UPLOAD_NG_SPAN = "UPLOAD_NG_SPAN";

	/**
	 * システムコントロール　パラメータID 商品マスタ一括修正上限数
	 */
	public static final String IKKATU_LIMIT = "IKKATU_LIMIT";

	/**
	 * システムコントロール　パラメータID 論理削除データ保持期間
	 */
	public static final String RONRI_SAKUJO_SPAN = "RONRI_SAKUJO_SPAN";

	/**
	 * システムコントロール　パラメータID バックアップデータ保持期間
	 */
	public static final String BK_DATA_SPAN = "BK_DATA_SPAN";

	/**
	 * システムコントロール　パラメータID 店舗区分（抽出対象）
	 */
	public static final String WHERE_TENPO_KB = "WHERE_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 採番コード
	 */
	public static final String SAIBAN_CD = "SAIBAN_CD";

	/**
	 * システムコントロール　パラメータID 商品マスタ一括修正上限数
	 */
	public static final String IKKATSU_CAPACITY_QT = "IKKATSU_CAPACITY_QT";

	/**
	 * システムコントロール　パラメータID バッチ処理結果照会画面処理種別
	 */
	public static final String BATCH_SYORIKEKKA_KB = "BATCH_SYORIKEKKA_KB";
	public static final String BATCH_KB_ALL = "00";		// すべて
	public static final String BATCH_KB_IKKATU = "01";		// 商品一括修正反映
	public static final String BATCH_KB_MST_SEIGOU = "02";	//マスタ整合性チェック
	public static final String BATCH_KB_FUKADOU = "03";		// 不稼働商品反映

	/**
	 * オンラインフラグ オンライン
	 */
	public static final String SYSTEM_ONLINE = "0";

	/**
	 * オンラインフラグ オフライン
	 */
	public static final String SYSTEM_OFFLINE = "1";

	/**
	 * システムコントロール　情報分析用CSVファイルパス
	 */
	public static final String CSVPATH_JOHO = "CSVPATH_JOHO";

/**
	 * システムコントロール　外部IFファイルパス（PLU）
	 */
	public static final String PATH_SEND_TANAWARI = "PATH_SEND_TANAWARI";

	/**
	 * システムコントロール　外部IFファイルパス（PLU）
	 */
	public static final String PATH_SEND_PLU = "PATH_SEND_PLU";

	/**
	 * システムコントロール　外部IFファイルパス（ソシオ）
	 */
 	public static final String PATH_SEND_SOCIO = "PATH_SEND_SOCIO";

	/**
	 * システムコントロール　外部IFファイルパス（彩裕）
	 */
 	public static final String PATH_SEND_SAIYU = "PATH_SEND_SAIYU";

	/**
	 * システムコントロール　外部IFファイルパス（計量器）
	 */
 	public static final String PATH_SEND_KEIRYOKI = "PATH_SEND_KEIRYOKI";

	/**
	 * システムコントロール　外部IFファイルパス（PC）
	 */
 	public static final String PATH_SEND_PC = "PATH_SEND_PC";


 	/**
	 * 全店グループコード
	 */
	public static final String ALL_TEN_GROUP = "00";

	/**
	 * システムコントロール　パラメータID マスタ管理の各画面で登録可能な店舗区分
	 */
	public static final String MASTER_USE_TENPO_KB = "MASTER_USE_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 単品店取扱マスタ作成対象の店舗区分
	 */
	public static final String TANPINTEN_CREATE_TENPO_KB = "TANPINTEN_CREATE_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 単品店取扱マスタ作成対象の店舗コード
	 */
	public static final String TANPINTEN_CREATE_TENPO_CD = "TANPINTEN_CREATE_TENPO_CD";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（彩裕）
	 */
	public static final String MASTER_IF_JOHO_TENPO_KB = "MASTER_IF_JOHO_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（PLU）
	 */
	public static final String MASTER_IF_TENPO_KB = "MASTER_IF_PLU_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（ソシオ）
	 */
	public static final String MASTER_IF_SOCIO_TENPO_KB = "MASTER_IF_SOCIO_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（彩裕）
	 */
	public static final String MASTER_IF_SAIYU_TENPO_KB = "MASTER_IF_SAIYU_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の納品区分（彩裕）
	 */
	public static final String MASTER_IF_SAIYU_NOHIN_KB = "MASTER_IF_SAIYU_NOHIN_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（計量器）
	 */
	public static final String MASTER_IF_KEIRYOKI_TENPO_KB = "MASTER_IF_KEIRYOKI_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の店舗区分（PC）
	 */
	public static final String MASTER_IF_PC_TENPO_KB = "MASTER_IF_PC_TENPO_KB";

	/**
	 * システムコントロール　パラメータID 外部IF対象の分類１コード（彩裕）
	 */
	public static final String MASTER_IF_SAIYU_BUNRUI1_CD = "MASTER_IF_SAIYU_BUNRUI1_CD";

	/**
	 * システムコントロール　パラメータID 外部IF計量器用の部門コード
	 */
	public static final String MASTER_IF_KEIRYOKI_BUMON_CD = "MASTER_IF_KEIRYOKI_BUMON_CD";

	/**
	 * システムコントロール　パラメータID 計量商品作成分類１コード
	 */
	public static final String MASTER_KEIRYO_SYOHIN_BUNRUI1_CD = "MASTER_KEIRYO_SYOHIN_BUNRUI1_CD";

	/**
	 * システムコントロール　パラメータID 外部IF計量器の削除IF猶予期間
	 */
	public static final String MASTER_IF_KEIRYOKI_DEL_SPN = "MASTER_IF_KEIRYOKI_DEL_SPN";

	/**
	 * システムコントロール　パラメータID 前回発効日（PC）
	 */
	public static final String MASTER_IF_PC_ZENKAI_HAKKO_DT = "MASTER_IF_PC_ZENKAI_HAKKO_DT";

	/**
	 * システムコントロール　内部IF用スキーマ名
	 */
	public static final String IF_SCHEMA = "IF_SCHEMA";

	// 2010.09.13 S.Hashiguchi Add Start
	/**
	 * システムコントロール　パラメータID 外部IF彩裕アイキャッチ取扱IF期間
	 */
	public static final String MASTER_IF_SAIYU_TORIATUKAI_KIKAN_SPN = "MASTER_IF_SAIYU_TORIATUKAI_KIKAN_SPN";
	// 2010.09.13 S.Hashiguchi Add End

//2012.06.18 Y.Imai Add 【MM00088】 不稼働商品一覧CSV出力処理 (S)
 	/**
	 * システムコントロール　出力ファイルパス（不稼働商品）
	 */
 	public static final String PATH_SEND_FUKADO_SYOHIN = "PATH_SEND_FUKADO_SYOHIN";
 	/**
	 * システムコントロール　不稼働商品出力処理ID
	 */
	public static final String FUKADO_SYOHIN_PG_ID = "FUKADO_SYOHIN_PG_ID";

	/** バッチステータス：完了 */
	public static final String BAT_STATUS_KB_KANRYO = "0";

	/** バッチステータス：実行待ち */
	public static final String BAT_STATUS_KB_JIKKOMATI = "1";

	/** バッチステータス：実行中 */
	public static final String BAT_STATUS_KB_JIKKOTYU = "2";

	/** バッチステータス：パラメータエラー */
	public static final String BAT_STATUS_KB_ERR = "3";
//2012.06.18 Y.Imai Add 【MM00088】 不稼働商品一覧CSV出力処理 (E)

//  2013/05/20 [MSTC00007] AS400商品マスタIF作成 (S)
	/** システムコントロール　FTPホスト名 */
	public static final String AS400_FTP_HOST = "AS400_FTP_HOST";

	/** システムコントロール　FTPポート番号 */
	public static final String AS400_FTP_PORT = "AS400_FTP_PORT";

	/** システムコントロール　FTPログイン名 */
	public static final String AS400_FTP_LOGIN_NAME = "AS400_FTP_LOGIN_NAME";

	/** システムコントロール　FTPパスワード */
	public static final String AS400_FTP_PASSWORD = "AS400_FTP_PASSWORD";

	/** システムコントロール　FTPファイルタイプ*/
	public static final String AS400_FTP_FILE_TYPE = "AS400_FTP_FILE_TYPE";

	/** システムコントロール　FTP転送元ディレクトリ名（ＣＳＶ出力先・バックアップ元）*/
	public static final String AS400_FTP_SOURCE_DIR_NAME = "AS400_FTP_SOURCE_DIR_NAME";

	/** システムコントロール　FTP転送先ディレクトリ名*/
	public static final String AS400_FTP_DST_DEST_NAME = "AS400_FTP_DEST_DIR_NAME";

	/** システムコントロール　バックアップ先ディレクトリ名*/
	public static final String AS400_BACKUP_DIR_NAME = "AS400_BACKUP_DIR_NAME";

	/** システムコントロール　送信ファイル名 商品マスタ（新規） */
	public static final String AS400_FTP_FILE_SYOHIN_NEW = "AS400_FTP_FILE_SYOHIN_NEW";

	/** システムコントロール　送信ファイル名 商品マスタ（修正） */
	public static final String AS400_FTP_FILE_SYOHIN_ADD = "AS400_FTP_FILE_SYOHIN_ADD";

	/** システムコントロール　送信ファイル名 商品変更リスト */
	public static final String AS400_FTP_FILE_SYOHIN_UPDLIST = "AS400_FTP_FILE_SYOHIN_UPDLIST";

	/** システムコントロール　送信ファイル名 商品マスタ（新規）完了ファイル */
	public static final String AS400_FTP_FILE_SYOHIN_NEW_FINISH = "AS400_FTP_FILE_SYOHIN_NEW_FINISH";

	/** システムコントロール　送信ファイル名 商品マスタ（修正）完了ファイル */
	public static final String AS400_FTP_FILE_SYOHIN_ADD_FINISH = "AS400_FTP_FILE_SYOHIN_ADD_FINISH";

	/** システムコントロール　送信ファイル名 商品変更リスト完了ファイル */
	public static final String AS400_FTP_FILE_SYOHIN_UPDLIST_FINISH = "AS400_FTP_FILE_SYOHIN_UPDLIST_FINISH";

	/** システムコントロール　商品マスタファイルのバックアップファイル */
	public static final String AS400_FTP_FILE_SYOHIN_BACKUP = "AS400_FTP_FILE_SYOHIN_BACKUP";

	/** システムコントロール　IFAS400保持日数・バックアップファイル保持日数 */
	public static final String AS400_IF_BACKUP_SPAN = "AS400_IF_BACKUP_SPAN";

	/** データ区分（新規） */
	public static final String AS400_DATA_KB_NEW = "1";

	/** データ区分（修正） */
	public static final String AS400_DATA_KB_ADD = "2";

	/** データ区分（変更リスト） */
	public static final String AS400_DATA_KB_UPDLIST= "3";

	/** 変換情報　入数（最大値） */
	public static final String AS400_MAX_IRISU_QT = "999";

	/** 変換情報　OB区分 */
	public static final String AS400_OB_KB = "1";

	/** 変換情報　商品区分（商品） */
	public static final String AS400_SYOHIN_KB_SYOHIN = "1";

	/** 変換情報　商品区分（原料） */
	public static final String AS400_SYOHIN_KB_GENRYO = "3";

	/** 変換情報　送信区分（送信する） */
	public static final String AS400_SEND_KB = "1";

	/** 変換情報　便区分（パン） */
	public static final String AS400_BIN_KB = "1"; // 2013.06.29 便区分値変更

	/** 変換情報　停止区分（停止） */
	public static final String AS400_TEISI_KB = "5";

	/** 変換情報　停止区分（停止） */
	public static final String AS400_TEISI_KAIJO_KB = "★（解除）";

	/** 変換情報　空 */
	public static final String AS400_SPACE = "★（空）";

//  2013/05/20 [MSTC00007] AS400商品マスタIF作成 (E)

	// 2016.10.13 M.Akgai #2277 (S)
	/** 取引先区分　仕入先 */
	public static final String TORIHIKISAKI_KB_SHIIRESAKI = "1";

	/** 取引先区分　経費取引先 */
	public static final String TORIHIKISAKI_KB_KEIHITORIHIKISAKI = "2";

	/** 取引先区分　メーカー*/
	public static final String TORIHIKISAKI_KB_MAKER = "3";

	/** 取引先区分　テナント（売上仕入）*/
	public static final String TORIHIKISAKI_KB_TENANT_URIAGE = "4";

	/** 取引先区分　テナント（賃料清算）*/
	public static final String TORIHIKISAKI_KB_TENANT_SEISAN = "5";

	/** 取引先区分　センター（発注用）*/
	public static final String TORIHIKISAKI_KB_CENTER_HACHUYO = "6";

	/** 取引先区分　センター（物流費）*/
	public static final String TORIHIKISAKI_KB_CENTER_BUTSUTYUHI = "7";

	/** 取引先区分　自社*/
	public static final String TORIHIKISAKI_KB_JISYA = "9";
	// 2016.10.13 M.Akgai #2277 (E)

//  2013/11/12 [CUS00059] D3(DWH)システムインターフェイス仕様変更対応(S)
	/** システムコントロール　DWHデータ出力ディレクトリパス */
	public static final String PATH_SEND_DWH = "PATH_SEND_DWH";

	/** システムコントロール　DWHデータFTP送信先ディレクトリパス */
	public static final String PATH_SEND_DEST_DWH = "PATH_SEND_DEST_DWH";

	/** システムコントロール　DWHｻｰﾊﾞｰログイン名 */
	public static final String FTP_DWH_LOGIN_NAME = "FTP_DWH_LOGIN_NAME";

	/** システムコントロール　DWHｻｰﾊﾞｰパスワード */
	public static final String FTP_DWH_PASSWORD = "FTP_DWH_PASSWORD";

	/** システムコントロール　DWHｻｰﾊﾞｰIPアドレス */
	public static final String FTP_DWH_IP = "FTP_DWH_IP";

	/** システムコントロール　DWHｻｰﾊﾞｰホスト名 */
	public static final String FTP_DWH_HOST = "FTP_DWH_HOST";

	/** システムコントロール　DWHｻｰﾊﾞポート番号 */
	public static final String FTP_DWH_PORT = "FTP_DWH_PORT";

	/** システムコントロール　DWHｻｰﾊﾞ接続タイプ */
	public static final String FTP_DWH_FILE_TYPE = "FTP_DWH_FILE_TYPE";

	/** システムコントロール　店舗マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_MST_TENPO = "DWH_FTP_FILE_MST_TENPO";

	/** システムコントロール　カテゴリマスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_MST_CTGRY = "DWH_FTP_FILE_MST_CTGRY";

	/** システムコントロール　商品マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_SYO_SYOHN = "DWH_FTP_FILE_SYO_SYOHN";

	/** システムコントロール　店別商品マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_SYO_SYOHN_MS = "DWH_FTP_FILE_SYO_SYOHN_MS";

	/** システムコントロール　仕入先マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_SIR_SIIRE = "DWH_FTP_FILE_SIR_SIIRE";

	/** システムコントロール　特売企画マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_TOK_KIKAK = "DWH_FTP_FILE_TOK_KIKAK";

	/** システムコントロール　特売商品マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_TOK_SYOHN = "DWH_FTP_FILE_TOK_SYOHN";

	/** システムコントロール　特売店別商品マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_TOK_SYOHN_MS = "DWH_FTP_FILE_TOK_SYOHN_MS";

	/** システムコントロール　商品マスタ（DWH）データファイル名 */
	public static final String DWH_FTP_FILE_MST_TRG = "DWH_FTP_FILE_MST_TRG";

//  2013/11/12 [CUS00059] D3(DWH)システムインターフェイス仕様変更対応(E)

	/** システムコントロール　商品マスタ（DWH）税有効日 */
	public static final String DWH_TAX_YUKO_DT = "DWH_TAX_YUKO_DT";

	/** システムコントロール　商品マスタ（DWH）税処理日 */
	public static final String DWH_TAX_SYORI_DT = "DWH_TAX_SYORI_DT";

//  2013/11/22 [CUS00063] POSインターフェイス仕様変更対応(S)
	/** システムコントロール　POSIF特売メンテナンス単品部の最大繰り返し数 */
	public static final String POS_REPEAT_MAX_NUM= "POS_REPEAT_MAX_NUM";
	/** システムコントロール　POSデータFTP送信先ディレクトリパス */
	public static final String PATH_SEND_DEST_PLU= "PATH_SEND_DEST_PLU";
	/** システムコントロール　POSｻｰﾊﾞ接続タイプ */
	public static final String FTP_POS_FILE_TYPE= "FTP_POS_FILE_TYPE";
	/** システムコントロール　POSｻｰﾊﾞｰホスト名：POSサーバーのホスト名 */
	public static final String FTP_POS_HOST= "FTP_POS_HOST";
	/** システムコントロール　POSｻｰﾊﾞｰIPアドレス：POSサーバーのIPアドレス */
	public static final String FTP_POS_IP= "FTP_POS_IP";
	/** システムコントロール　POSｻｰﾊﾞｰログイン名：POSサーバーへのログインID */
	public static final String FTP_POS_LOGIN_NAME= "FTP_POS_LOGIN_NAME";
	/** システムコントロール　POSｻｰﾊﾞｰパスワード：POSサーバーへのログインパスワード */
	public static final String FTP_POS_PASSWORD= "FTP_POS_PASSWORD";
	/** システムコントロール　POSｻｰﾊﾞポート番号：POSサーバーのポート番号 */
	public static final String FTP_POS_PORT= "FTP_POS_PORT";
	/** システムコントロール　単品メンテナンス */
	public static final String POS_FTP_TANPIN_MAINTE= "POS_FTP_TANPIN_MAINTE";
	/** システムコントロール　単品メンテナンス(ヘッダーファイル) */
	public static final String POS_FTP_TANPIN_MAINTE_HDS= "POS_FTP_TANPIN_MAINTE_HDS";
	/** システムコントロール　特売メンテナンス */
	public static final String POS_FTP_TOKUBAI_MAINTE= "POS_FTP_TOKUBAI_MAINTE";
	/** システムコントロール　特売メンテナンス(ヘッダーファイル) */
	public static final String POS_FTP_TOKUBAI_MAINTE_HDS= "POS_FTP_TOKUBAI_MAINTE_HDS";
	/** システムコントロール　クラスメンテナンス */
	public static final String POS_FTP_CLASS_MAINTE= "POS_FTP_CLASS_MAINTE";
	/** システムコントロール　クラスメンテナンス(ヘッダーファイル) */
	public static final String POS_FTP_CLASS_MAINTE_HDS= "POS_FTP_CLASS_MAINTE_HDS";
	/** システムコントロール　ラインメンテナンス */
	public static final String POS_FTP_LINE_MAINTE= "POS_FTP_LINE_MAINTE";
	/** システムコントロール　ラインメンテナンス(ヘッダーファイル) */
	public static final String POS_FTP_LINE_MAINTE_HDS= "POS_FTP_LINE_MAINTE_HDS";
//  2013/11/22 [CUS00063] POSインターフェイス仕様変更対応(E)

//  2013/12/09 [CUS00066] POPインターフェイス仕様変更対応(S)
	/** システムコントロール　POPデータ出力ディレクトリパス */
	public static final String PATH_SEND_POP= "PATH_SEND_POP";
	/** システムコントロール　POPデータコピー先ディレクトリパス */
	public static final String PATH_SEND_DEST_POP= "PATH_SEND_DEST_POP";
	/** システムコントロール　POP商品マスタ */
	public static final String POP_FTP_SYOHIN= "POP_FTP_SYOHIN";
	/** システムコントロール　POP商品マスタ(エンドファイル) */
	public static final String POP_FTP_SYOHIN_END= "POP_FTP_SYOHIN_END";
	/** システムコントロール　POP店別商品マスタ */
	public static final String POP_FTP_TENBETU_SYOHIN= "POP_FTP_TENBETU_SYOHIN";
	/** システムコントロール　POP店別商品マスタ(エンドファイル) */
	public static final String POP_FTP_TENBETU_SYOHIN_END= "POP_FTP_TENBETU_SYOHIN_END";
//  2013/12/09 [CUS00066] POPインターフェイス仕様変更対応(E)

//  2013/12/16 [CUS00050] POS売価不一致勧告 (S)
	/** システムコントロール　POS単価変更ログデータ ディレクトリ名 */
	public static final String POS_TANKA_HENKO_LOG_SOURCE_DIR_NAME = "POS_TANKA_HENKO_LOG_SOURCE_DIR_NAME";
	/** システムコントロール　POS単価変更ログデータ ファイル名 */
	public static final String POS_TANKA_HENKO_LOG_SOURCE_FILE_NAME = "POS_TANKA_HENKO_LOG_SOURCE_FILE_NAME";
	/** システムコントロール　予約フラグ */
	public static final String POS_TANKA_HENKO_LOG_SAIYO_YOYAKU_FG = "POS_TANKA_HENKO_LOG_SAIYO_YOYAKU_FG";
//  2013/12/16 [CUS00050] POS売価不一致勧告 (E)

//  2013/12/09 [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応(S)
	/** システムコントロール　VANデータ出力ディレクトリパス */
	public static final String PATH_SEND_VAN= "PATH_SEND_VAN";
	/** システムコントロール　VAN商品マスタ */
	public static final String VAN_TCP_IP_SYOHIN= "VAN_TCP_IP_SYOHIN";
	/** システムコントロール　VANEOS確認期間 */
	public static final String VAN_EOS_CHECK_TERM = "VAN_EOS_CHECK_TERM";
//  2013/12/09 [CUS00102] レクサスEDIインターフェイス仕様変更対応（マスタ）対応(E)

//  2013/12/24 [CUS00059] D3(DWH)システムインターフェイス仕様変更対応(S)
	/** DWHC/Dフラグ */
	public static final String DWH_CD_FG = "DWH_CD_FG";
	/** DWHC/D付加あり */
	public static final String DWH_CD_ON = "1";
//  2013/12/24 [CUS00059] D3(DWH)システムインターフェイス仕様変更対応(E)

//  2013/12/25 [CUS00065] 計量器インターフェイス仕様変更対応(S)
	/** システムコントロール　計量器送信処理用（接続先ホスト） */
	public static final String KEIRYOKI_FTP_HOST = "KEIRYOKI_FTP_HOST";
	/** システムコントロール　計量器送信処理用（サーバーのIPアドレス） */
	public static final String KEIRYOKI_FTP_IP = "KEIRYOKI_FTP_IP";
	/** システムコントロール　計量器送信処理用（接続先ポート番号） */
	public static final String KEIRYOKI_FTP_PORT = "KEIRYOKI_FTP_PORT";
	/** システムコントロール　計量器送信処理用（ユーザ名） */
	public static final String KEIRYOKI_FTP_LOGIN_NAME = "KEIRYOKI_FTP_LOGIN_NAME";
	/** システムコントロール　計量器送信処理用（パスワード） */
	public static final String KEIRYOKI_FTP_PASSWORD = "KEIRYOKI_FTP_PASSWORD";
	/** システムコントロール　計量器送信処理用（リモート側のディレクトリ） */
	public static final String KEIRYOKI_FTP_DEST_DIR_NAME= "KEIRYOKI_FTP_DEST_DIR_NAME";
	/** システムコントロール　計量器送信処理用（ｻｰﾊﾞ接続タイプ） */
	public static final String KEIRYOKI_FTP_FILE_TYPE= "KEIRYOKI_FTP_FILE_TYPE";

	/** システムコントロール　計量器 商品マスタ */
	public static final String KEIRYOKI_FTP_SYOHIN= "KEIRYOKI_FTP_SYOHIN";
	/** システムコントロール　計量器 添加物マスタ */
	public static final String KEIRYOKI_FTP_TENKABUTU= "KEIRYOKI_FTP_TENKABUTU";
//  2013/12/25 [CUS00065] 計量器インターフェイス仕様変更対応(E)

//  2014/01/21 [CUS00049] 商品マスタ変更リスト（バッチ）対応(S)
	/** システムコントロール　商品マスタ変更リスト 出力対象DPTコード */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_DPT = "SYOHIN_MASTER_HENKO_LIST_BATCH_DPT";
	/** システムコントロール　商品マスタ変更リスト 出力対象開始日 */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_START_DT = "SYOHIN_MASTER_HENKO_LIST_BATCH_START_DT";
	/** システムコントロール　商品マスタ変更リスト 出力対象終了日 */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_END_DT = "SYOHIN_MASTER_HENKO_LIST_BATCH_END_DT";
	/** システムコントロール　商品マスタ変更リスト 出力対象出力区分 */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_KB = "SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_KB";
	/** システムコントロール　商品マスタ変更リスト 出力除外（仕入のみ商品） */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_SHIIRE = "SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_SHIIRE";
	/** システムコントロール　商品マスタ変更リスト 出力除外（その他改廃項目） */
	public static final String SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_OTHERS = "SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_OTHERS";
	/** システムコントロール　パラメータID 商品マスタ変更リスト_抽出可能上限レコード数 */
	public static final String SYOHIN_MASTER_HENKO_LIST_MAX_RECORD = "SYOHIN_MASTER_HENKO_LIST_MAX_RECORD";
	/** システムコントロール　パラメータID 商品マスタ変更リスト_本部権限非保有者表示制御フラグ */
	public static final String SYOHIN_MASTER_HENKO_LIST_VISIBILITY_FG = "SYOHIN_MASTER_HENKO_LIST_VISIBILITY_FG";
//  2014/01/21 [CUS00049] 商品マスタ変更リスト（バッチ）対応(E)

	//Add 2015.10.13 DAI.BQ (S)
	/**システムコントロール．パラメータＩＤ*/
	//出力先フォルダ変更対応 2016.04.29 M.Kanno (S)
	//public static final String PATH_SEND_BLK = "PATH_SEND_BLK";
	public static final String FIVI_IFDIR_CRM = "FIVI_IFDIR_CRM";
	//出力先フォルダ変更対応 2016.04.29 M.Kanno (E)
	//Add 2015.10.13 DAI.BQ (E)
	
	// #3382 MSTB993 2016.12.22 S.Takayama (S)
	/**
	 * システムコントロール　パラメータID 最大レシート発行枚数
	 */
	public static final String MAX_RECEIPT_QT = "MAX_RECEIPT_QT";
	// #3382 MSTB993 2016.12.22 S.Takayama (E)
}