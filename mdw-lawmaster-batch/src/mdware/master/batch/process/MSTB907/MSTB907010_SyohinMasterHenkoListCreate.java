package mdware.master.batch.process.MSTB907;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.bean.MSTB907010_DtAlarmBeanForSyohinMasterHenkoList;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.bean.mst010181_SyohinMasterHenkoListOutputBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.portal.dictionary.AlarmProcessKbDic;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Level;

/**
 * <p>タイトル:商品マスタ変更リスト作成</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author o.uemura
 * @version 3.00 (2014/01/09) O.Uemura [CUS00049] ランドローム様対応 商品マスタ変更リスト対応
 * @Version 3.01 (2015/12/04) Nhat.NgoM FIVImart様対応
 * @Version 3.02 (2017/02/02) X.Liu #2799 FIVImart様対応
 */
public class MSTB907010_SyohinMasterHenkoListCreate {

	// 処理日間隔
	private static final int SPAN_DAYS = 1;
	// 帳票名
    private static final String CHOHYO_ID = "SyohinMasterHenkoList_batch_";
    private static final String CHOHYO_NA = "商品マスタ変更リスト";

    // マスタ販促管理（WEB）ルートＵＲＬ
    private static final String MASTERHANSOKU_ROOT_URL = ResorceUtil.getInstance().getPropertie("MASTERHANSOKU_ROOT_URL");
    // 帳票ダウンロード用JSP名称
    private static final String downloadPDFForSyohinMasterHenkoList = "MSTB907010_downloadPDFForSyohinMasterHenkoList.jsp?id=";

    // No.136 Add 2015.12.08 Nhat.NgoM (S)
    private static final String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
    // No.136 Add 2015.12.08 Nhat.NgoM (E)

	private MasterDataBase db = null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
//	// 有効日
//	private String yukoDate = null;
	// 出力対象DPTコード
	private String dptCd = null;
	// 開始日
	private String startDate = null;
	// 終了日
	private String endDate = null;
	// 出力区分
	private String outputKb = null;
	// 出力除外フラグ（仕入のみ商品）
	private String outputJogaiFgShiire = null;
	// 出力除外フラグ（その他改廃項目）
	private String outputJogaiFgOthers = null;
	// 本部権限非保有者の表示制御フラグ
	private String SyohinMasterHenkoListVisibilityFg = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_VISIBILITY_FG);
	// 区切り文字
	private static final String SPLIT_CODE = ",";
	// 非表示用文字
	private static final String MASK = "***";
	// PDF行番号用変数
	private int seq = 1;
	// 抽出可能上限レコード数
	private static final int SYOHIN_MASTER_HENKO_LIST_MAX_RECORD = Integer.parseInt(ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_MAX_RECORD));


	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB907010_SyohinMasterHenkoListCreate(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB907010_SyohinMasterHenkoListCreate() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try{

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false);	// false : ログ有り  true : ログ無し
			mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); // DBインスタンス作成

			// 処理開始ログ
			writeLog(Level.INFO_INT, "商品マスタ変更リスト作成処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);
//			writeLog(Level.INFO_INT, "有効日：" + yukoDate);
			writeLog(Level.INFO_INT, "対象DPT：" + dptCd);
			writeLog(Level.INFO_INT, "対象期間：" + startDate + "～" + endDate);
			writeLog(Level.INFO_INT, "出力区分（0:新規、1:修正、2:削除、9:すべて）：" + outputKb);
			writeLog(Level.INFO_INT, "出力除外フラグ（仕入のみ商品）（0:除外しない、1:除外する）：" + outputJogaiFgShiire);
			writeLog(Level.INFO_INT, "出力除外フラグ（その他改廃項目）（0:除外しない、1:除外する）：" + outputJogaiFgOthers);
			writeLog(Level.INFO_INT, "出力上限件数：" + SYOHIN_MASTER_HENKO_LIST_MAX_RECORD);


		    // 処理件数
			ResultSet rs = null;
			int iRec = 0;
			//CSVデータ作成処理
			StringBuffer strSyohin   = new StringBuffer();
			StringBuffer strCSV = new StringBuffer();

			// 想定される明細データ件数を取得する
			rs = db.executeQuery(countSyohinMasterHenkoListSQL());
			while (rs.next()) {
				iRec = Integer.parseInt(rs.getString("SEQ"));
			}
			// 入力されたDPTコード、期間から想定される抽出件数の最大値を算出する
			if (iRec >= SYOHIN_MASTER_HENKO_LIST_MAX_RECORD){
				writeLog(Level.INFO_INT, "想定される出力件数が" + SYOHIN_MASTER_HENKO_LIST_MAX_RECORD + "件を超過しているため、処理を中止します。");
				throw new Exception();
			}

			// 商品マスタより出力データを取得する
			rs = db.executeQuery(createSyohinMasterHenkoListSQL());
			// 処理件数カウント用変数のリセット
			iRec = 0;

			// 帳票作成の元となるCSVファイルの生成を行う
			// ヘッダ行の挿入
			// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (S)
			/*strSyohin.append("1世代前_行番号").append(SPLIT_CODE);
			strSyohin.append("1世代前_区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_有効日").append(SPLIT_CODE);
			strSyohin.append("1世代前_PLU反映日").append(SPLIT_CODE);
			strSyohin.append("1世代前_DPTコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_クラスコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_メーカー名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_POSレシート品名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品名_カナ").append(SPLIT_CODE);
			strSyohin.append("1世代前_規格名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_売価単価_込").append(SPLIT_CODE);
			strSyohin.append("1世代前_売価配信").append(SPLIT_CODE);
			strSyohin.append("1世代前_原価単価_込").append(SPLIT_CODE);
			strSyohin.append("1世代前_取引先コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_入数").append(SPLIT_CODE);
			strSyohin.append("1世代前_EOS区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_便区分１").append(SPLIT_CODE);
			strSyohin.append("1世代前_便区分２").append(SPLIT_CODE);
			strSyohin.append("1世代前_更新者ID").append(SPLIT_CODE);
			strSyohin.append("1世代前_更新時刻").append(SPLIT_CODE);
			strSyohin.append("1世代前_その他").append(SPLIT_CODE);
			strSyohin.append("行番号").append(SPLIT_CODE);
			strSyohin.append("区分").append(SPLIT_CODE);
			strSyohin.append("商品コード").append(SPLIT_CODE);
			strSyohin.append("有効日").append(SPLIT_CODE);
			strSyohin.append("PLU反映日").append(SPLIT_CODE);
			strSyohin.append("DPTコード").append(SPLIT_CODE);
			strSyohin.append("クラスコード").append(SPLIT_CODE);
			strSyohin.append("メーカー名_漢字").append(SPLIT_CODE);
			strSyohin.append("商品名_漢字").append(SPLIT_CODE);
			strSyohin.append("POSレシート品名_漢字").append(SPLIT_CODE);
			strSyohin.append("商品名_カナ").append(SPLIT_CODE);
			strSyohin.append("規格名_漢字").append(SPLIT_CODE);
			strSyohin.append("売価単価_込").append(SPLIT_CODE);
			strSyohin.append("売価配信").append(SPLIT_CODE);
			strSyohin.append("原価単価_込").append(SPLIT_CODE);
			strSyohin.append("取引先コード").append(SPLIT_CODE);
			strSyohin.append("入数").append(SPLIT_CODE);
			strSyohin.append("EOS区分").append(SPLIT_CODE);
			strSyohin.append("便区分１").append(SPLIT_CODE);
			strSyohin.append("便区分２").append(SPLIT_CODE);
			strSyohin.append("更新者ID").append(SPLIT_CODE);
			strSyohin.append("更新時刻").append(SPLIT_CODE);
			strSyohin.append("その他");*/
			//#2799 X.Liu 2017.02.07 Add (S)
			/*strSyohin.append("1世代前_行番号").append(SPLIT_CODE);
			strSyohin.append("1世代前_区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_有効日").append(SPLIT_CODE);
			strSyohin.append("1世代前_PLU反映日").append(SPLIT_CODE);
			strSyohin.append("1世代前_PLU反映時間").append(SPLIT_CODE);
			strSyohin.append("1世代前_DPTコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_クラスコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_規格内容").append(SPLIT_CODE);
			strSyohin.append("1世代前_売価単価_込").append(SPLIT_CODE);
			strSyohin.append("1世代前_原価単価_抜").append(SPLIT_CODE);
			strSyohin.append("1世代前_卸売価単価_抜").append(SPLIT_CODE);
			strSyohin.append("1世代前_取引先コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_入数").append(SPLIT_CODE);
			strSyohin.append("1世代前_最低発注数").append(SPLIT_CODE);
			strSyohin.append("1世代前_更新者ID").append(SPLIT_CODE);
			strSyohin.append("1世代前_更新時刻").append(SPLIT_CODE);
			strSyohin.append("1世代前_ラインコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_担当バイヤー").append(SPLIT_CODE);
			strSyohin.append("1世代前_仕入販売区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_相場商品区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_定貫区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_在庫集計コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品サイズ_高さ").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品サイズ_幅").append(SPLIT_CODE);
			strSyohin.append("1世代前_商品サイズ_奥行き").append(SPLIT_CODE);
			strSyohin.append("1世代前_標準小売価格").append(SPLIT_CODE);
			strSyohin.append("1世代前_平準重量").append(SPLIT_CODE);
			strSyohin.append("1世代前_国コード").append(SPLIT_CODE);
			strSyohin.append("1世代前_メーカーコード").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売方法").append(SPLIT_CODE);
			strSyohin.append("1世代前_メーカー_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_規格名_漢字").append(SPLIT_CODE);
			strSyohin.append("1世代前_POSレシート品名(漢字)").append(SPLIT_CODE);
			strSyohin.append("1世代前_納品区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_売価単価（抜）").append(SPLIT_CODE);
			strSyohin.append("1世代前_原価単価（込）").append(SPLIT_CODE);
			strSyohin.append("1世代前_卸税込売価(卸税適用)").append(SPLIT_CODE);
			strSyohin.append("1世代前_卸税込売価(小売税適用)").append(SPLIT_CODE);
			strSyohin.append("1世代前_税区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_税率区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_仕入税区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_仕入税率区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_卸税区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_卸税率区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_発注単位").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売単位").append(SPLIT_CODE);
			strSyohin.append("1世代前_最低在庫数").append(SPLIT_CODE);
			strSyohin.append("1世代前_最大発注数").append(SPLIT_CODE);
			strSyohin.append("1世代前_F便区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_バラ入数").append(SPLIT_CODE);
			strSyohin.append("1世代前_ケース発注区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売開始日").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売終了日").append(SPLIT_CODE);
			strSyohin.append("1世代前_店舗納品開始日").append(SPLIT_CODE);
			strSyohin.append("1世代前_店舗納品終了日").append(SPLIT_CODE);
			strSyohin.append("1世代前_季節商品開始日").append(SPLIT_CODE);
			strSyohin.append("1世代前_季節商品終了日").append(SPLIT_CODE);
			strSyohin.append("1世代前_プライスカード発行区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_プライスカード種類").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売基準日").append(SPLIT_CODE);
			strSyohin.append("1世代前_販売基準単位").append(SPLIT_CODE);
			strSyohin.append("1世代前_入荷基準日").append(SPLIT_CODE);
			strSyohin.append("1世代前_入荷基準単位").append(SPLIT_CODE);
			strSyohin.append("1世代前_ユニット単位").append(SPLIT_CODE);
			strSyohin.append("1世代前_ユニット内容量").append(SPLIT_CODE);
			strSyohin.append("1世代前_ユニット基準量").append(SPLIT_CODE);
			strSyohin.append("1世代前_消費期限表示パターン").append(SPLIT_CODE);
			strSyohin.append("1世代前_消費期限時間").append(SPLIT_CODE);
			strSyohin.append("1世代前_計量器名").append(SPLIT_CODE);
			strSyohin.append("1世代前_ラベル成分").append(SPLIT_CODE);
			strSyohin.append("1世代前_ラベル保管方法").append(SPLIT_CODE);
			strSyohin.append("1世代前_ラベル使い方").append(SPLIT_CODE);
			strSyohin.append("1世代前_CGC返品区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_年齢制限区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_年齢").append(SPLIT_CODE);
			strSyohin.append("1世代前_瓶区分").append(SPLIT_CODE);
			strSyohin.append("1世代前_保証金").append(SPLIT_CODE);
			strSyohin.append("1世代前_セキュリティタグ").append(SPLIT_CODE);
			strSyohin.append("1世代前_ハンパー商品").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意区分１").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意区分２").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意区分３").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意区分４").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意区分５").append(SPLIT_CODE);
			strSyohin.append("1世代前_コメント１").append(SPLIT_CODE);
			strSyohin.append("1世代前_コメント２").append(SPLIT_CODE);
			strSyohin.append("1世代前_任意コード").append(SPLIT_CODE);
			;*/
			//#2799 X.Liu 2017.02.07 Add (E)
			// #2799 Del X.Liu 2017.02.01 (S)
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00001", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00002", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00003", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00004", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00005", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00006", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00007", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00008=メーカー名漢字
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00008", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00009", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00010=1世代前_POSレシート品名_漢字
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00010", userLocal)).append(SPLIT_CODE);
//
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00012", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00013", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00014=1世代前_売価配信
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00014", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00015=1世代前_原価単価_込
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00015", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00016", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00017", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00018=1世代前_EOS区分
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00018", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00019=1世代前_便区分１
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00019", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00020=1世代前_便区分２
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00020", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00021", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00022", userLocal)).append(SPLIT_CODE);
////			MST01018_TXT_00023=1世代前_その他
////			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00023", userLocal)).append(SPLIT_CODE);
//			
//			
//			
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00024", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00025", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00026", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00027", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00028", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00029", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00030", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00031", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00032", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00033", userLocal)).append(SPLIT_CODE);
//
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00035", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00036", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00037", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00038", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00039", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00040", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00041", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00042", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00043", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00044", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00045", userLocal)).append(SPLIT_CODE);
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00046", userLocal));
//			// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (E)
			// #2799 Del X.Liu 2017.02.01 (E)
			// #2799 Add X.Liu 2017.02.01 (S)
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00002", userLocal)).append(SPLIT_CODE);//区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00003", userLocal)).append(SPLIT_CODE);//商品コード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00004", userLocal)).append(SPLIT_CODE);//有効日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00005", userLocal)).append(SPLIT_CODE);//PLU反映日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00174", userLocal)).append(SPLIT_CODE);//PLU反映時間
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00006", userLocal)).append(SPLIT_CODE);//DPTコード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00007", userLocal)).append(SPLIT_CODE);//クラスコード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00009", userLocal)).append(SPLIT_CODE);//商品名_漢字
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00012", userLocal)).append(SPLIT_CODE);//規格名_漢字
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00013", userLocal)).append(SPLIT_CODE);//売価単価_込
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00175", userLocal)).append(SPLIT_CODE);//原価単価_抜
//			
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00176", userLocal)).append(SPLIT_CODE);//卸売価単価_抜
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00016", userLocal)).append(SPLIT_CODE);//取引先コード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00017", userLocal)).append(SPLIT_CODE);//入数
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00177", userLocal)).append(SPLIT_CODE);//最低発注数
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00021", userLocal)).append(SPLIT_CODE);//更新者ID
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00022", userLocal)).append(SPLIT_CODE);//更新時刻
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00178", userLocal)).append(SPLIT_CODE);//ラインコード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00179", userLocal)).append(SPLIT_CODE);//登録日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00181", userLocal)).append(SPLIT_CODE);//担当バイヤー
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00182", userLocal)).append(SPLIT_CODE);//仕入販売区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00183", userLocal)).append(SPLIT_CODE);//相場商品区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00184", userLocal)).append(SPLIT_CODE);//定貫区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00185", userLocal)).append(SPLIT_CODE);//在庫集計コード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00186", userLocal)).append(SPLIT_CODE);//商品サイズ_高さ
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00187", userLocal)).append(SPLIT_CODE);//商品サイズ_幅
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00188", userLocal)).append(SPLIT_CODE);//商品サイズ_奥行き
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00189", userLocal)).append(SPLIT_CODE);//標準小売価格
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00190", userLocal)).append(SPLIT_CODE);//平均重量
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00191", userLocal)).append(SPLIT_CODE);//国コード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00192", userLocal)).append(SPLIT_CODE);//メーカーコード
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00193", userLocal)).append(SPLIT_CODE);//販売方法
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00008", userLocal)).append(SPLIT_CODE);//メーカー名_漢字
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00012", userLocal)).append(SPLIT_CODE);//規格名_漢字
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00010", userLocal)).append(SPLIT_CODE);//POSレシート品名_漢字
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00194", userLocal)).append(SPLIT_CODE);//納品区分
//			
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00195", userLocal)).append(SPLIT_CODE);//売価単価_抜
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00015", userLocal)).append(SPLIT_CODE);//原価単価_込
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00196", userLocal)).append(SPLIT_CODE);//卸税込売価_卸税適用
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00197", userLocal)).append(SPLIT_CODE);//卸税込売価_小売税適用
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00198", userLocal)).append(SPLIT_CODE);//税区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00199", userLocal)).append(SPLIT_CODE);//税率区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00200", userLocal)).append(SPLIT_CODE);//仕入税区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00201", userLocal)).append(SPLIT_CODE);//仕入税率区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00202", userLocal)).append(SPLIT_CODE);//卸税区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00203", userLocal)).append(SPLIT_CODE);//卸税率区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00204", userLocal)).append(SPLIT_CODE);//発注単位
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00205", userLocal)).append(SPLIT_CODE);//販売単位
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00206", userLocal)).append(SPLIT_CODE);//最低在庫数
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00207", userLocal)).append(SPLIT_CODE);//最大発注数
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00208", userLocal)).append(SPLIT_CODE);//F便区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00209", userLocal)).append(SPLIT_CODE);//バラ入数
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00210", userLocal)).append(SPLIT_CODE);//ケース発注区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00211", userLocal)).append(SPLIT_CODE);//規格内容
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00212", userLocal)).append(SPLIT_CODE);//プライスカード発行区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00213", userLocal)).append(SPLIT_CODE);//プライスカード種類
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00214", userLocal)).append(SPLIT_CODE);//販売開始日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00215", userLocal)).append(SPLIT_CODE);//販売終了日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00216", userLocal)).append(SPLIT_CODE);//店舗納品開始日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00217", userLocal)).append(SPLIT_CODE);//店舗納品終了日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00218", userLocal)).append(SPLIT_CODE);//季節商品開始日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00219", userLocal)).append(SPLIT_CODE);//季節商品終了日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00220", userLocal)).append(SPLIT_CODE);//販売基準日
//			
//			
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00221", userLocal)).append(SPLIT_CODE);//販売基準単位
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00222", userLocal)).append(SPLIT_CODE);//入荷基準日
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00223", userLocal)).append(SPLIT_CODE);//入荷基準単位
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00224", userLocal)).append(SPLIT_CODE);//ユニット単位
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00225", userLocal)).append(SPLIT_CODE);//ユニット内容量
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00226", userLocal)).append(SPLIT_CODE);//ユニット基準量
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00227", userLocal)).append(SPLIT_CODE);//消費期限表示パターン
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00228", userLocal)).append(SPLIT_CODE);//消費期限時間
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00229", userLocal)).append(SPLIT_CODE);//計量器名
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00230", userLocal)).append(SPLIT_CODE);//ラベル成分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00231", userLocal)).append(SPLIT_CODE);//ラベル保管方法
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00232", userLocal)).append(SPLIT_CODE);//ラベル使い方
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00233", userLocal)).append(SPLIT_CODE);//CGC返品区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00234", userLocal)).append(SPLIT_CODE);//年齢制限区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00235", userLocal)).append(SPLIT_CODE);//年齢
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00236", userLocal)).append(SPLIT_CODE);//瓶区分
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00237", userLocal)).append(SPLIT_CODE);//保証金
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00238", userLocal)).append(SPLIT_CODE);//セキュリティタグ 
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00239", userLocal)).append(SPLIT_CODE);//ハンパー商品
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00240", userLocal)).append(SPLIT_CODE);//任意区分１
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00241", userLocal)).append(SPLIT_CODE);//任意区分２
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00242", userLocal)).append(SPLIT_CODE);//任意区分３
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00243", userLocal)).append(SPLIT_CODE);//任意区分４
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00244", userLocal)).append(SPLIT_CODE);//任意区分５
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00245", userLocal)).append(SPLIT_CODE);//コメント１
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00246", userLocal)).append(SPLIT_CODE);//コメント２
//			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00247", userLocal)).append(SPLIT_CODE);//任意コード
			
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00025", userLocal)).append(SPLIT_CODE);//区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00026", userLocal)).append(SPLIT_CODE);//商品コード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00027", userLocal)).append(SPLIT_CODE);//有効日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00028", userLocal)).append(SPLIT_CODE);//PLU反映日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00074", userLocal)).append(SPLIT_CODE);//PLU反映時間
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00029", userLocal)).append(SPLIT_CODE);//DPTコード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00030", userLocal)).append(SPLIT_CODE);//クラスコード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00032", userLocal)).append(SPLIT_CODE);//商品名_漢字
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00035", userLocal)).append(SPLIT_CODE);//規格名_漢字
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00036", userLocal)).append(SPLIT_CODE);//売価単価_込
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00075", userLocal)).append(SPLIT_CODE);//原価単価_抜
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00076", userLocal)).append(SPLIT_CODE);//卸売価単価_抜
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00039", userLocal)).append(SPLIT_CODE);//取引先コード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00040", userLocal)).append(SPLIT_CODE);//入数
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00077", userLocal)).append(SPLIT_CODE);//最低発注数
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00044", userLocal)).append(SPLIT_CODE);//更新者ID
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00045", userLocal)).append(SPLIT_CODE);//更新時刻
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00078", userLocal)).append(SPLIT_CODE);//ラインコード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00079", userLocal)).append(SPLIT_CODE);//登録日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00081", userLocal)).append(SPLIT_CODE);//担当バイヤー
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00082", userLocal)).append(SPLIT_CODE);//仕入販売区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00083", userLocal)).append(SPLIT_CODE);//相場商品区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00084", userLocal)).append(SPLIT_CODE);//定貫区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00085", userLocal)).append(SPLIT_CODE);//在庫集計コード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00086", userLocal)).append(SPLIT_CODE);//商品サイズ_高さ
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00087", userLocal)).append(SPLIT_CODE);//商品サイズ_幅
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00088", userLocal)).append(SPLIT_CODE);//商品サイズ_奥行き
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00089", userLocal)).append(SPLIT_CODE);//標準小売価格
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00090", userLocal)).append(SPLIT_CODE);//平準重量
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00091", userLocal)).append(SPLIT_CODE);//国コード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00092", userLocal)).append(SPLIT_CODE);//メーカーコード
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00093", userLocal)).append(SPLIT_CODE);//販売方法
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00031", userLocal)).append(SPLIT_CODE);//メーカー名_漢字
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00035", userLocal)).append(SPLIT_CODE);//規格名_漢字
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00033", userLocal)).append(SPLIT_CODE);//POSレシート品名_漢字
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00094", userLocal)).append(SPLIT_CODE);//納品区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00095", userLocal)).append(SPLIT_CODE);//売価単価_抜
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00038", userLocal)).append(SPLIT_CODE);//原価単価_込
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00096", userLocal)).append(SPLIT_CODE);//卸税込売価_卸税適用
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00097", userLocal)).append(SPLIT_CODE);//卸税込売価_小売税適用
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00098", userLocal)).append(SPLIT_CODE);//税区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00099", userLocal)).append(SPLIT_CODE);//税率区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00100", userLocal)).append(SPLIT_CODE);//仕入税区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00101", userLocal)).append(SPLIT_CODE);//仕入税率区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00102", userLocal)).append(SPLIT_CODE);//卸税区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00103", userLocal)).append(SPLIT_CODE);//卸税率区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00104", userLocal)).append(SPLIT_CODE);//発注単位
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00105", userLocal)).append(SPLIT_CODE);//販売単位
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00106", userLocal)).append(SPLIT_CODE);//最低在庫数
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00107", userLocal)).append(SPLIT_CODE);//最大発注数
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00108", userLocal)).append(SPLIT_CODE);//F便区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00109", userLocal)).append(SPLIT_CODE);//バラ入数
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00110", userLocal)).append(SPLIT_CODE);//ケース発注区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00111", userLocal)).append(SPLIT_CODE);//規格内容
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00112", userLocal)).append(SPLIT_CODE);//プライスカード発行区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00113", userLocal)).append(SPLIT_CODE);//プライスカード種類
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00114", userLocal)).append(SPLIT_CODE);//販売開始日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00115", userLocal)).append(SPLIT_CODE);//販売終了日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00116", userLocal)).append(SPLIT_CODE);//店舗納品開始日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00117", userLocal)).append(SPLIT_CODE);//店舗納品終了日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00118", userLocal)).append(SPLIT_CODE);//季節商品開始日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00119", userLocal)).append(SPLIT_CODE);//季節商品終了日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00120", userLocal)).append(SPLIT_CODE);//販売基準日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00121", userLocal)).append(SPLIT_CODE);//販売基準単位
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00122", userLocal)).append(SPLIT_CODE);//入荷基準日
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00123", userLocal)).append(SPLIT_CODE);//入荷基準単位
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00124", userLocal)).append(SPLIT_CODE);//ユニット単位
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00125", userLocal)).append(SPLIT_CODE);//ユニット内容量
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00126", userLocal)).append(SPLIT_CODE);//ユニット基準量
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00127", userLocal)).append(SPLIT_CODE);//消費期限表示パターン
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00128", userLocal)).append(SPLIT_CODE);//消費期限時間
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00129", userLocal)).append(SPLIT_CODE);//計量器名
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00130", userLocal)).append(SPLIT_CODE);//ラベル成分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00131", userLocal)).append(SPLIT_CODE);//ラベル保管方法
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00132", userLocal)).append(SPLIT_CODE);//ラベル使い方
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00133", userLocal)).append(SPLIT_CODE);//CGC返品区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00134", userLocal)).append(SPLIT_CODE);//年齢制限区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00135", userLocal)).append(SPLIT_CODE);//年齢
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00136", userLocal)).append(SPLIT_CODE);//瓶区分
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00137", userLocal)).append(SPLIT_CODE);//保証金
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00138", userLocal)).append(SPLIT_CODE);//セキュリティタグ 
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00139", userLocal)).append(SPLIT_CODE);//ハンパー商品
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00140", userLocal)).append(SPLIT_CODE);//任意区分１
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00141", userLocal)).append(SPLIT_CODE);//任意区分２
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00142", userLocal)).append(SPLIT_CODE);//任意区分３
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00143", userLocal)).append(SPLIT_CODE);//任意区分４
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00144", userLocal)).append(SPLIT_CODE);//任意区分５
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00145", userLocal)).append(SPLIT_CODE);//コメント１
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00146", userLocal)).append(SPLIT_CODE);//コメント２
			strSyohin.append(MessageUtil.getMessage("MST01018_TXT_00147", userLocal)).append(SPLIT_CODE);//任意コード
			// #2799 Add X.Liu 2017.02.01 (E)
			
			// 明細行の編集
			//明細部（直近世代、1世代前のレコードをペアで抽出し、比較を行う）
//			for (int i = 0; i <= iRec; i += 2) {
			mst010181_SyohinMasterHenkoListOutputBean meisaiData_old = new mst010181_SyohinMasterHenkoListOutputBean();
			mst010181_SyohinMasterHenkoListOutputBean meisaiData_new = new mst010181_SyohinMasterHenkoListOutputBean();

			while (rs.next()) {

				if(rs.getRow() % 2 == 1){
					// 奇数行: 1世代前のレコード
					meisaiData_old = new mst010181_SyohinMasterHenkoListOutputBean();
					
					meisaiData_old.setGeneration(rs.getString("GENERATION"));
					meisaiData_old.setPer_txt(rs.getString("PER_TXT"));
					meisaiData_old.setKb(rs.getString("KB"));
					meisaiData_old.setSyohin_Cd(rs.getString("SYOHIN_CD"));
					meisaiData_old.setYuko_Dt(rs.getString("YUKO_DT"));
					meisaiData_old.setPlu_Send_Dt(rs.getString("PLU_SEND_DT"));
					meisaiData_old.setBunrui1_Cd(rs.getString("BUNRUI1_CD"));
					meisaiData_old.setBunrui5_Cd(rs.getString("BUNRUI5_CD"));
					meisaiData_old.setKikaku_Kanji_2_Na(rs.getString("KIKAKU_KANJI_2_NA"));
					meisaiData_old.setHinmei_Kanji_Na(rs.getString("HINMEI_KANJI_NA"));
					meisaiData_old.setRec_Hinmei_Kanji_Na(rs.getString("REC_HINMEI_KANJI_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setHinmei_Kana_Na(rs.getString("HINMEI_KANA_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setKikaku_Kanji_Na(rs.getString("KIKAKU_KANJI_NA"));
					meisaiData_old.setBaitanka_Vl(rs.getString("BAITANKA_VL"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setBaika_Haishin_Fg(rs.getString("BAIKA_HAISHIN_FG"));
					// No.136 MST01018 Mod 2015.12.04 Nhat.NgoM (S)
					// meisaiData_old.setGentanka_Vl(rs.getString("GENTANKA_VL"));
//					CalculateTaxBean calcInBean = null;
//			        CalculateTaxBean calcOutBean = null;
//			        calcInBean = new CalculateTaxBean();
//			        calcInBean.setKingaku(decConver(rs.getString("GENTANKA_VL")));
//			        calcInBean.setFractionDigit(MoneyUtil.getFractionCostUnitLen());
//			        calcInBean.setTaxKb(rs.getString("ZEI_KB"));
//			        calcInBean.setTaxRt(decConver(rs.getString("TAX_RT")));
//			        calcInBean.setMarumeKb(MoneyUtil.getFractionCostUnitMode());
//			        calcOutBean = CalculateTax.getTaxBeanByJava(calcInBean);
//			        meisaiData_old.setGentanka_Vl(calcOutBean.getTaxOut().toString());
					// No.136 MST01018 Mod 2015.12.04 Nhat.NgoM (E)
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setSiiresaki_Cd(rs.getString("SIIRESAKI_CD"));
					meisaiData_old.setHachutani_Irisu_Qt(rs.getString("HACHUTANI_IRISU_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setEos_Kb(rs.getString("EOS_KB"));
//					meisaiData_old.setBin_1_Kb(rs.getString("BIN_1_KB"));
//					meisaiData_old.setBin_2_Kb(rs.getString("BIN_2_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setUpdate_User_Id(rs.getString("UPDATE_USER_ID"));
					meisaiData_old.setUpdate_Ts(rs.getString("UPDATE_TS"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setOthers_Mod_Kb(rs.getString("OTHERS_MOD_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setBunrui1_Cd_For_Kaipage(rs.getString("BUNRUI1_CD_FOR_KAIPAGE"));
					meisaiData_old.setSystem_Kb(rs.getString("SYSTEM_KB"));
					meisaiData_old.setGyosyu_Kb(rs.getString("GYOSYU_KB"));
					meisaiData_old.setSyohin_Kb(rs.getString("SYOHIN_KB"));
					meisaiData_old.setBunrui2_Cd(rs.getString("BUNRUI2_CD"));
					meisaiData_old.setSyohin_2_Cd(rs.getString("SYOHIN_2_CD"));
					meisaiData_old.setZaiko_Syohin_Cd(rs.getString("ZAIKO_SYOHIN_CD"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setJyoho_Syohin_Cd(rs.getString("JYOHO_SYOHIN_CD"));
//					meisaiData_old.setKikaku_Kana_Na(rs.getString("KIKAKU_KANA_NA"));
//					meisaiData_old.setKikaku_Kana_2_Na(rs.getString("KIKAKU_KANA_2_NA"));
//					meisaiData_old.setRec_Hinmei_Kana_Na(rs.getString("REC_HINMEI_KANA_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setSyohin_Width_Qt(rs.getString("SYOHIN_WIDTH_QT"));
					meisaiData_old.setSyohin_Height_Qt(rs.getString("SYOHIN_HEIGHT_QT"));
					meisaiData_old.setSyohin_Depth_Qt(rs.getString("SYOHIN_DEPTH_QT"));
					meisaiData_old.setZei_Kb(rs.getString("ZEI_KB"));
					meisaiData_old.setTax_Rate_Kb(rs.getString("TAX_RATE_KB"));
					meisaiData_old.setGentanka_Nuki_Vl(rs.getString("GENTANKA_NUKI_VL"));
					meisaiData_old.setBaitanka_Nuki_Vl(rs.getString("BAITANKA_NUKI_VL"));
					meisaiData_old.setMaker_Kibo_Kakaku_Vl(rs.getString("MAKER_KIBO_KAKAKU_VL"));
					meisaiData_old.setHacyu_Syohin_Kb(rs.getString("HACYU_SYOHIN_KB"));
					meisaiData_old.setHacyu_Syohin_Cd(rs.getString("HACYU_SYOHIN_CD"));
					meisaiData_old.setHachu_Tani_Na(rs.getString("HACHU_TANI_NA"));
					meisaiData_old.setHanbai_Tani(rs.getString("HANBAI_TANI"));
					meisaiData_old.setMax_Hachutani_Qt(rs.getString("MAX_HACHUTANI_QT"));
					meisaiData_old.setCase_Hachu_Kb(rs.getString("CASE_HACHU_KB"));
					meisaiData_old.setBara_Irisu_Qt(rs.getString("BARA_IRISU_QT"));
					meisaiData_old.setTen_Hachu_St_Dt(rs.getString("TEN_HACHU_ST_DT"));
					meisaiData_old.setTen_Hachu_Ed_Dt(rs.getString("TEN_HACHU_ED_DT"));
					meisaiData_old.setHanbai_St_Dt(rs.getString("HANBAI_ST_DT"));
					meisaiData_old.setHanbai_Ed_Dt(rs.getString("HANBAI_ED_DT"));
					meisaiData_old.setHanbai_Kikan_Kb(rs.getString("HANBAI_KIKAN_KB"));
					meisaiData_old.setTeikan_Kb(rs.getString("TEIKAN_KB"));
					meisaiData_old.setSoba_Syohin_Kb(rs.getString("SOBA_SYOHIN_KB"));
					meisaiData_old.setNohin_Kigen_Kb(rs.getString("NOHIN_KIGEN_KB"));
					meisaiData_old.setNohin_Kigen_Qt(rs.getString("NOHIN_KIGEN_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setHachu_Pattern_1_Kb(rs.getString("HACHU_PATTERN_1_KB"));
//					meisaiData_old.setSime_Time_1_Qt(rs.getString("SIME_TIME_1_QT"));
//					meisaiData_old.setC_Nohin_Rtime_1_Kb(rs.getString("C_NOHIN_RTIME_1_KB"));
//					meisaiData_old.setTen_Nohin_Rtime_1_Kb(rs.getString("TEN_NOHIN_RTIME_1_KB"));
//					meisaiData_old.setTen_Nohin_Time_1_Kb(rs.getString("TEN_NOHIN_TIME_1_KB"));
//					meisaiData_old.setHachu_Pattern_2_Kb(rs.getString("HACHU_PATTERN_2_KB"));
//					meisaiData_old.setSime_Time_2_Qt(rs.getString("SIME_TIME_2_QT"));
//					meisaiData_old.setC_Nohin_Rtime_2_Kb(rs.getString("C_NOHIN_RTIME_2_KB"));
//					meisaiData_old.setTen_Nohin_Rtime_2_Kb(rs.getString("TEN_NOHIN_RTIME_2_KB"));
//					meisaiData_old.setTen_Nohin_Time_2_Kb(rs.getString("TEN_NOHIN_TIME_2_KB"));
//					meisaiData_old.setYusen_Bin_Kb(rs.getString("YUSEN_BIN_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setF_Bin_Kb(rs.getString("F_BIN_KB"));
					meisaiData_old.setButuryu_Kb(rs.getString("BUTURYU_KB"));
					meisaiData_old.setGot_Start_Mm(rs.getString("GOT_START_MM"));
					meisaiData_old.setGot_End_Mm(rs.getString("GOT_END_MM"));
					meisaiData_old.setHachu_Teisi_Kb(rs.getString("HACHU_TEISI_KB"));
					meisaiData_old.setCgc_Henpin_Kb(rs.getString("CGC_HENPIN_KB"));
					meisaiData_old.setHanbai_Limit_Kb(rs.getString("HANBAI_LIMIT_KB"));
					meisaiData_old.setHanbai_Limit_Qt(rs.getString("HANBAI_LIMIT_QT"));
					meisaiData_old.setPlu_hanei_time(rs.getString("PLU_HANEI_TIME"));
					meisaiData_old.setKeyplu_Fg(rs.getString("KEYPLU_FG"));
					meisaiData_old.setPlu_Kb(rs.getString("PLU_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_old.setSyuzei_Hokoku_Kb(rs.getString("SYUZEI_HOKOKU_KB"));
//					meisaiData_old.setSake_Naiyoryo_Qt(rs.getString("SAKE_NAIYORYO_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_old.setTag_Hyoji_Baika_Vl(rs.getString("TAG_HYOJI_BAIKA_VL"));
					meisaiData_old.setKeshi_Baika_Vl(rs.getString("KESHI_BAIKA_VL"));
					meisaiData_old.setYoridori_Kb(rs.getString("YORIDORI_KB"));
					meisaiData_old.setPc_Kb(rs.getString("PC_KB"));
					meisaiData_old.setDaisi_No_Nb(rs.getString("DAISI_NO_NB"));
					meisaiData_old.setUnit_Price_Tani_Kb(rs.getString("UNIT_PRICE_TANI_KB"));
					meisaiData_old.setUnit_Price_Naiyoryo_Qt(rs.getString("UNIT_PRICE_NAIYORYO_QT"));
					meisaiData_old.setUnit_Price_Kijun_Tani_Qt(rs.getString("UNIT_PRICE_KIJUN_TANI_QT"));
					meisaiData_old.setSyohi_Kigen_Kb(rs.getString("SYOHI_KIGEN_KB"));
					meisaiData_old.setSyohi_Kigen_Dt(rs.getString("SYOHI_KIGEN_DT"));
					meisaiData_old.setFree_1_Kb(rs.getString("FREE_1_KB"));
					meisaiData_old.setFree_2_Kb(rs.getString("FREE_2_KB"));
					meisaiData_old.setFree_3_Kb(rs.getString("FREE_3_KB"));
					meisaiData_old.setFree_4_Kb(rs.getString("FREE_4_KB"));
					meisaiData_old.setFree_5_Kb(rs.getString("FREE_5_KB"));
					meisaiData_old.setComment_1_Tx(rs.getString("COMMENT_1_TX"));
					meisaiData_old.setComment_2_Tx(rs.getString("COMMENT_2_TX"));
					meisaiData_old.setFree_Cd(rs.getString("FREE_CD"));
					meisaiData_old.setComment_Tx(rs.getString("COMMENT_TX"));
					// #2799対応 2017.02.02 X.Liu Add  (S)
					meisaiData_old.setHamper_syohin_fg(rs.getString("HAMPER_SYOHIN_FG"));
					meisaiData_old.setSecurity_tag_fg(rs.getString("SECURITY_TAG_FG"));
					meisaiData_old.setHoshoukin(rs.getString("HOSHOUKIN"));
					meisaiData_old.setKan_kb(rs.getString("KAN_KB"));
					meisaiData_old.setNenrei(rs.getString("NENREI"));
					meisaiData_old.setNenrei_seigen_kb(rs.getString("NENREI_SEIGEN_KB"));
					meisaiData_old.setLabel_tukaikata(rs.getString("LABEL_TUKAIKATA"));
					meisaiData_old.setLabel_hokan_hoho(rs.getString("LABEL_HOKAN_HOHO"));
					meisaiData_old.setLabel_seibun(rs.getString("LABEL_SEIBUN"));
					meisaiData_old.setSyohin_eiji_na(rs.getString("SYOHIN_EIJI_NA"));
					meisaiData_old.setSyohi_kigen_hyoji_patter(rs.getString("SYOHI_KIGEN_HYOJI_PATTER"));
					meisaiData_old.setCenter_kyoyo_kb(rs.getString("CENTER_KYOYO_KB"));
					meisaiData_old.setCenter_kyoyo_dt(rs.getString("CENTER_KYOYO_DT"));
					meisaiData_old.setMin_zaiko_qt(rs.getString("MIN_ZAIKO_QT"));
					meisaiData_old.setOrosi_zei_kb(rs.getString("OROSI_ZEI_KB"));
					meisaiData_old.setOrosi_tax_rate_kb(rs.getString("OROSI_TAX_RATE_KB"));
					meisaiData_old.setSiire_tax_rate_kb(rs.getString("SIIRE_TAX_RATE_KB"));
					meisaiData_old.setSiire_zei_kb(rs.getString("SIIRE_ZEI_KB"));
					meisaiData_old.setOrosi_baitanka_nuki_vl(rs.getString("OROSI_BAITANKA_NUKI_VL"));
					meisaiData_old.setOrosi_baitanka_vl(rs.getString("OROSI_BAITANKA_VL"));
					meisaiData_old.setOrosi_baitanka_vl_kouri(rs.getString("OROSI_BAITANKA_VL_KOURI"));
					meisaiData_old.setGentanka_Vl(rs.getString("GENTANKA_VL"));
					meisaiData_old.setHanbai_hoho_kb(rs.getString("HANBAI_HOHO_KB"));
					meisaiData_old.setMaker_cd(rs.getString("MAKER_CD"));
					meisaiData_old.setPack_weight_qt(rs.getString("PACK_WIDTH_QT"));
					meisaiData_old.setSyokai_user_id(rs.getString("SYOKAI_USER_ID"));
					meisaiData_old.setSinki_toroku_dt(rs.getString("SINKI_TOROKU_DT"));
					meisaiData_old.setCountry_cd(rs.getString("COUNTRY_CD"));
					meisaiData_old.setOrosi_baitanka_nuki_vl(rs.getString("OROSI_BAITANKA_NUKI_VL"));
					meisaiData_old.setMin_hachu_qt(rs.getString("MIN_HACHU_QT"));
					meisaiData_old.setUpdate_User_Id(rs.getString("UPDATE_USER_ID"));
					// #2799対応 2017.02.02 X.Liu Add  (E)
				} else {
					// 偶数行: 直近世代のレコード
					meisaiData_new = new mst010181_SyohinMasterHenkoListOutputBean();
					meisaiData_new.setGeneration(rs.getString("GENERATION"));
					meisaiData_new.setKb(rs.getString("KB"));
					meisaiData_new.setPer_txt(rs.getString("PER_TXT"));
					meisaiData_new.setSyohin_Cd(rs.getString("SYOHIN_CD"));
					meisaiData_new.setYuko_Dt(rs.getString("YUKO_DT"));
					meisaiData_new.setPlu_Send_Dt(rs.getString("PLU_SEND_DT"));
					meisaiData_new.setBunrui1_Cd(rs.getString("BUNRUI1_CD"));
					meisaiData_new.setBunrui5_Cd(rs.getString("BUNRUI5_CD"));
					meisaiData_new.setKikaku_Kanji_2_Na(rs.getString("KIKAKU_KANJI_2_NA"));
					meisaiData_new.setHinmei_Kanji_Na(rs.getString("HINMEI_KANJI_NA"));
					meisaiData_new.setRec_Hinmei_Kanji_Na(rs.getString("REC_HINMEI_KANJI_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setHinmei_Kana_Na(rs.getString("HINMEI_KANA_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setKikaku_Kanji_Na(rs.getString("KIKAKU_KANJI_NA"));
					meisaiData_new.setBaitanka_Vl(rs.getString("BAITANKA_VL"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setBaika_Haishin_Fg(rs.getString("BAIKA_HAISHIN_FG"));
					// No.136 MST01018 Mod 2015.12.04 Nhat.NgoM (S)
					// meisaiData_new.setGentanka_Vl(rs.getString("GENTANKA_VL"));
//					CalculateTaxBean calcInBean = null;
//			        CalculateTaxBean calcOutBean = null;
//			        calcInBean = new CalculateTaxBean();
//			        calcInBean.setKingaku(decConver(rs.getString("GENTANKA_VL")));
//			        calcInBean.setFractionDigit(MoneyUtil.getFractionCostUnitLen());
//			        calcInBean.setTaxKb(rs.getString("ZEI_KB"));
//			        calcInBean.setTaxRt(decConver(rs.getString("TAX_RT")));
//			        calcInBean.setMarumeKb(MoneyUtil.getFractionCostUnitMode());
//			        calcOutBean = CalculateTax.getTaxBeanByJava(calcInBean);
//			        meisaiData_new.setGentanka_Vl(calcOutBean.getTaxOut().toString());
					// No.136 MST01018 Mod 2015.12.04 Nhat.NgoM (E)
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setSiiresaki_Cd(rs.getString("SIIRESAKI_CD"));
					meisaiData_new.setHachutani_Irisu_Qt(rs.getString("HACHUTANI_IRISU_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setEos_Kb(rs.getString("EOS_KB"));
//					meisaiData_new.setBin_1_Kb(rs.getString("BIN_1_KB"));
//					meisaiData_new.setBin_2_Kb(rs.getString("BIN_2_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setUpdate_User_Id(rs.getString("UPDATE_USER_ID"));
					meisaiData_new.setUpdate_Ts(rs.getString("UPDATE_TS"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setOthers_Mod_Kb(rs.getString("OTHERS_MOD_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setBunrui1_Cd_For_Kaipage(rs.getString("BUNRUI1_CD_FOR_KAIPAGE"));
					meisaiData_new.setSystem_Kb(rs.getString("SYSTEM_KB"));
					meisaiData_new.setGyosyu_Kb(rs.getString("GYOSYU_KB"));
					meisaiData_new.setSyohin_Kb(rs.getString("SYOHIN_KB"));
					meisaiData_new.setBunrui2_Cd(rs.getString("BUNRUI2_CD"));
					meisaiData_new.setSyohin_2_Cd(rs.getString("SYOHIN_2_CD"));
					meisaiData_new.setZaiko_Syohin_Cd(rs.getString("ZAIKO_SYOHIN_CD"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setJyoho_Syohin_Cd(rs.getString("JYOHO_SYOHIN_CD"));
//					meisaiData_new.setKikaku_Kana_Na(rs.getString("KIKAKU_KANA_NA"));
//					meisaiData_new.setKikaku_Kana_2_Na(rs.getString("KIKAKU_KANA_2_NA"));
//					meisaiData_new.setRec_Hinmei_Kana_Na(rs.getString("REC_HINMEI_KANA_NA"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setSyohin_Width_Qt(rs.getString("SYOHIN_WIDTH_QT"));
					meisaiData_new.setSyohin_Height_Qt(rs.getString("SYOHIN_HEIGHT_QT"));
					meisaiData_new.setSyohin_Depth_Qt(rs.getString("SYOHIN_DEPTH_QT"));
					meisaiData_new.setZei_Kb(rs.getString("ZEI_KB"));
					meisaiData_new.setTax_Rate_Kb(rs.getString("TAX_RATE_KB"));
					meisaiData_new.setGentanka_Nuki_Vl(rs.getString("GENTANKA_NUKI_VL"));
					meisaiData_new.setBaitanka_Nuki_Vl(rs.getString("BAITANKA_NUKI_VL"));
					meisaiData_new.setMaker_Kibo_Kakaku_Vl(rs.getString("MAKER_KIBO_KAKAKU_VL"));
					meisaiData_new.setHacyu_Syohin_Kb(rs.getString("HACYU_SYOHIN_KB"));
					meisaiData_new.setHacyu_Syohin_Cd(rs.getString("HACYU_SYOHIN_CD"));
					meisaiData_new.setHachu_Tani_Na(rs.getString("HACHU_TANI_NA"));
					meisaiData_new.setHanbai_Tani(rs.getString("HANBAI_TANI"));
					meisaiData_new.setMax_Hachutani_Qt(rs.getString("MAX_HACHUTANI_QT"));
					meisaiData_new.setCase_Hachu_Kb(rs.getString("CASE_HACHU_KB"));
					meisaiData_new.setBara_Irisu_Qt(rs.getString("BARA_IRISU_QT"));
					meisaiData_new.setTen_Hachu_St_Dt(rs.getString("TEN_HACHU_ST_DT"));
					meisaiData_new.setTen_Hachu_Ed_Dt(rs.getString("TEN_HACHU_ED_DT"));
					meisaiData_new.setHanbai_St_Dt(rs.getString("HANBAI_ST_DT"));
					meisaiData_new.setHanbai_Ed_Dt(rs.getString("HANBAI_ED_DT"));
					meisaiData_new.setHanbai_Kikan_Kb(rs.getString("HANBAI_KIKAN_KB"));
					meisaiData_new.setTeikan_Kb(rs.getString("TEIKAN_KB"));
					meisaiData_new.setSoba_Syohin_Kb(rs.getString("SOBA_SYOHIN_KB"));
					meisaiData_new.setNohin_Kigen_Kb(rs.getString("NOHIN_KIGEN_KB"));
					meisaiData_new.setNohin_Kigen_Qt(rs.getString("NOHIN_KIGEN_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setHachu_Pattern_1_Kb(rs.getString("HACHU_PATTERN_1_KB"));
//					meisaiData_new.setSime_Time_1_Qt(rs.getString("SIME_TIME_1_QT"));
//					meisaiData_new.setC_Nohin_Rtime_1_Kb(rs.getString("C_NOHIN_RTIME_1_KB"));
//					meisaiData_new.setTen_Nohin_Rtime_1_Kb(rs.getString("TEN_NOHIN_RTIME_1_KB"));
//					meisaiData_new.setTen_Nohin_Time_1_Kb(rs.getString("TEN_NOHIN_TIME_1_KB"));
//					meisaiData_new.setHachu_Pattern_2_Kb(rs.getString("HACHU_PATTERN_2_KB"));
//					meisaiData_new.setSime_Time_2_Qt(rs.getString("SIME_TIME_2_QT"));
//					meisaiData_new.setC_Nohin_Rtime_2_Kb(rs.getString("C_NOHIN_RTIME_2_KB"));
//					meisaiData_new.setTen_Nohin_Rtime_2_Kb(rs.getString("TEN_NOHIN_RTIME_2_KB"));
//					meisaiData_new.setTen_Nohin_Time_2_Kb(rs.getString("TEN_NOHIN_TIME_2_KB"));
//					meisaiData_new.setYusen_Bin_Kb(rs.getString("YUSEN_BIN_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setF_Bin_Kb(rs.getString("F_BIN_KB"));
					meisaiData_new.setButuryu_Kb(rs.getString("BUTURYU_KB"));
					meisaiData_new.setGot_Start_Mm(rs.getString("GOT_START_MM"));
					meisaiData_new.setGot_End_Mm(rs.getString("GOT_END_MM"));
					meisaiData_new.setHachu_Teisi_Kb(rs.getString("HACHU_TEISI_KB"));
					meisaiData_new.setCgc_Henpin_Kb(rs.getString("CGC_HENPIN_KB"));
					meisaiData_new.setHanbai_Limit_Kb(rs.getString("HANBAI_LIMIT_KB"));
					meisaiData_new.setHanbai_Limit_Qt(rs.getString("HANBAI_LIMIT_QT"));
					meisaiData_new.setPlu_hanei_time(rs.getString("PLU_HANEI_TIME"));
					meisaiData_new.setKeyplu_Fg(rs.getString("KEYPLU_FG"));
					meisaiData_new.setPlu_Kb(rs.getString("PLU_KB"));
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					meisaiData_new.setSyuzei_Hokoku_Kb(rs.getString("SYUZEI_HOKOKU_KB"));
//					meisaiData_new.setSake_Naiyoryo_Qt(rs.getString("SAKE_NAIYORYO_QT"));
					// #2799対応 2017.02.02 X.Liu Del  (E)
					meisaiData_new.setTag_Hyoji_Baika_Vl(rs.getString("TAG_HYOJI_BAIKA_VL"));
					meisaiData_new.setKeshi_Baika_Vl(rs.getString("KESHI_BAIKA_VL"));
					meisaiData_new.setYoridori_Kb(rs.getString("YORIDORI_KB"));
					meisaiData_new.setPc_Kb(rs.getString("PC_KB"));
					meisaiData_new.setDaisi_No_Nb(rs.getString("DAISI_NO_NB"));
					meisaiData_new.setUnit_Price_Tani_Kb(rs.getString("UNIT_PRICE_TANI_KB"));
					meisaiData_new.setUnit_Price_Naiyoryo_Qt(rs.getString("UNIT_PRICE_NAIYORYO_QT"));
					meisaiData_new.setUnit_Price_Kijun_Tani_Qt(rs.getString("UNIT_PRICE_KIJUN_TANI_QT"));
					meisaiData_new.setSyohi_Kigen_Kb(rs.getString("SYOHI_KIGEN_KB"));
					meisaiData_new.setSyohi_Kigen_Dt(rs.getString("SYOHI_KIGEN_DT"));
					meisaiData_new.setFree_1_Kb(rs.getString("FREE_1_KB"));
					meisaiData_new.setFree_2_Kb(rs.getString("FREE_2_KB"));
					meisaiData_new.setFree_3_Kb(rs.getString("FREE_3_KB"));
					meisaiData_new.setFree_4_Kb(rs.getString("FREE_4_KB"));
					meisaiData_new.setFree_5_Kb(rs.getString("FREE_5_KB"));
					meisaiData_new.setComment_1_Tx(rs.getString("COMMENT_1_TX"));
					meisaiData_new.setComment_2_Tx(rs.getString("COMMENT_2_TX"));
					meisaiData_new.setFree_Cd(rs.getString("FREE_CD"));
					meisaiData_new.setComment_Tx(rs.getString("COMMENT_TX"));
					// #2799対応 2017.02.02 X.Liu Add  (S)
					meisaiData_new.setHamper_syohin_fg(rs.getString("HAMPER_SYOHIN_FG"));
					meisaiData_new.setSecurity_tag_fg(rs.getString("SECURITY_TAG_FG"));
					meisaiData_new.setHoshoukin(rs.getString("HOSHOUKIN"));
					meisaiData_new.setKan_kb(rs.getString("KAN_KB"));
					meisaiData_new.setNenrei(rs.getString("NENREI"));
					meisaiData_new.setNenrei_seigen_kb(rs.getString("NENREI_SEIGEN_KB"));
					meisaiData_new.setLabel_tukaikata(rs.getString("LABEL_TUKAIKATA"));
					meisaiData_new.setLabel_hokan_hoho(rs.getString("LABEL_HOKAN_HOHO"));
					meisaiData_new.setLabel_seibun(rs.getString("LABEL_SEIBUN"));
					meisaiData_new.setSyohin_eiji_na(rs.getString("SYOHIN_EIJI_NA"));
					meisaiData_new.setSyohi_kigen_hyoji_patter(rs.getString("SYOHI_KIGEN_HYOJI_PATTER"));
					meisaiData_new.setCenter_kyoyo_kb(rs.getString("CENTER_KYOYO_KB"));
					meisaiData_new.setCenter_kyoyo_dt(rs.getString("CENTER_KYOYO_DT"));
					meisaiData_new.setMin_zaiko_qt(rs.getString("MIN_ZAIKO_QT"));
					meisaiData_new.setOrosi_zei_kb(rs.getString("OROSI_ZEI_KB"));
					meisaiData_new.setOrosi_tax_rate_kb(rs.getString("OROSI_TAX_RATE_KB"));
					meisaiData_new.setSiire_tax_rate_kb(rs.getString("SIIRE_TAX_RATE_KB"));
					meisaiData_new.setSiire_zei_kb(rs.getString("SIIRE_ZEI_KB"));
					meisaiData_new.setOrosi_baitanka_nuki_vl(rs.getString("OROSI_BAITANKA_NUKI_VL"));
					meisaiData_new.setOrosi_baitanka_vl(rs.getString("OROSI_BAITANKA_VL"));
					meisaiData_new.setOrosi_baitanka_vl_kouri(rs.getString("OROSI_BAITANKA_VL_KOURI"));
					meisaiData_new.setGentanka_Vl(rs.getString("GENTANKA_VL"));
					meisaiData_new.setHanbai_hoho_kb(rs.getString("HANBAI_HOHO_KB"));
					meisaiData_new.setMaker_cd(rs.getString("MAKER_CD"));
					meisaiData_new.setPack_weight_qt(rs.getString("PACK_WIDTH_QT"));
					meisaiData_new.setSyokai_user_id(rs.getString("SYOKAI_USER_ID"));
					meisaiData_new.setSinki_toroku_dt(rs.getString("SINKI_TOROKU_DT"));
					meisaiData_new.setCountry_cd(rs.getString("COUNTRY_CD"));
					meisaiData_new.setOrosi_baitanka_nuki_vl(rs.getString("OROSI_BAITANKA_NUKI_VL"));
					meisaiData_new.setMin_hachu_qt(rs.getString("MIN_HACHU_QT"));
					meisaiData_new.setUpdate_User_Id(rs.getString("UPDATE_USER_ID"));
					// #2799対応 2017.02.02 X.Liu Add  (E)
				}

				// 偶数行 = 1世代前、直近世代のペアが設定されている状態
				// 出力項目の加工を行う
				if(rs.getRow() % 2 == 0){
					if(meisaiData_old.getKb().equals("0")) {
						meisaiData_old.setYuko_Dt("-");
						meisaiData_old.setPlu_Send_Dt("-");
						// #2799対応 2017.02.02 X.Liu Add  (S)
						meisaiData_old.setPlu_hanei_time("-");
						// #2799対応 2017.02.02 X.Liu Add  (E)
					}
					// 区分「1:修正」の場合、直近世代レコードに対して、更新のあった箇所以外の項目を非表示にする
					if(meisaiData_old.getKb().equals("1")) {
						if(meisaiData_old.getBunrui1_Cd() != null && meisaiData_old.getBunrui1_Cd().equals(meisaiData_new.getBunrui1_Cd())){
							meisaiData_new.setBunrui1_Cd(null);
						}
						if(meisaiData_old.getBunrui5_Cd() != null && meisaiData_old.getBunrui5_Cd().equals(meisaiData_new.getBunrui5_Cd())){
							meisaiData_new.setBunrui5_Cd(null);
						}
						if(meisaiData_old.getKikaku_Kanji_2_Na() != null && meisaiData_old.getKikaku_Kanji_2_Na().equals(meisaiData_new.getKikaku_Kanji_2_Na())){
							meisaiData_new.setKikaku_Kanji_2_Na(null);
						}
						if(meisaiData_old.getHinmei_Kanji_Na() != null && meisaiData_old.getHinmei_Kanji_Na().equals(meisaiData_new.getHinmei_Kanji_Na())){
							meisaiData_new.setHinmei_Kanji_Na(null);
						}
						if(meisaiData_old.getRec_Hinmei_Kanji_Na() != null && meisaiData_old.getRec_Hinmei_Kanji_Na().equals(meisaiData_new.getRec_Hinmei_Kanji_Na())){
							meisaiData_new.setRec_Hinmei_Kanji_Na(null);
						}
						// No.136 MST01018 Del 2015.12.08 Nhat.NgoM (S)
//						if(meisaiData_old.getHinmei_Kana_Na() != null && meisaiData_old.getHinmei_Kana_Na().equals(meisaiData_new.getHinmei_Kana_Na())){
//							meisaiData_new.setHinmei_Kana_Na(null);
//						}
						// No.136 MST01018 Del 2015.12.08 Nhat.NgoM (E)
						if(meisaiData_old.getKikaku_Kanji_Na() != null && meisaiData_old.getKikaku_Kanji_Na().equals(meisaiData_new.getKikaku_Kanji_Na())){
							meisaiData_new.setKikaku_Kanji_Na(null);
						}
						if(meisaiData_old.getBaitanka_Vl() != null && meisaiData_old.getBaitanka_Vl().equals(meisaiData_new.getBaitanka_Vl())){
							meisaiData_new.setBaitanka_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(meisaiData_old.getBaika_Haishin_Fg() != null && meisaiData_old.getBaika_Haishin_Fg().equals(meisaiData_new.getBaika_Haishin_Fg())){
						//	meisaiData_new.setBaika_Haishin_Fg(null);
						//}
						//
						//if(meisaiData_old.getGentanka_Vl() != null && meisaiData_old.getGentanka_Vl().equals(meisaiData_new.getGentanka_Vl())){
						//	meisaiData_new.setGentanka_Vl(null);
						//}
						if(meisaiData_old.getGentanka_Nuki_Vl() != null && meisaiData_old.getGentanka_Nuki_Vl().equals(meisaiData_new.getGentanka_Nuki_Vl())){
						    meisaiData_new.setGentanka_Nuki_Vl(null);
						}
						if(meisaiData_old.getOrosi_baitanka_nuki_vl() != null && meisaiData_old.getOrosi_baitanka_nuki_vl().equals(meisaiData_new.getOrosi_baitanka_nuki_vl())){
						    meisaiData_new.setOrosi_baitanka_nuki_vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						if(meisaiData_old.getSiiresaki_Cd() != null && meisaiData_old.getSiiresaki_Cd().equals(meisaiData_new.getSiiresaki_Cd())){
							meisaiData_new.setSiiresaki_Cd(null);
						}
						
						if(meisaiData_old.getHachutani_Irisu_Qt() != null && meisaiData_old.getHachutani_Irisu_Qt().equals(meisaiData_new.getHachutani_Irisu_Qt())){
							meisaiData_new.setHachutani_Irisu_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Add  (S)
						if(meisaiData_old.getMin_hachu_qt() != null && meisaiData_old.getMin_hachu_qt().equals(meisaiData_new.getMin_hachu_qt())){
						    meisaiData_new.setMin_hachu_qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Add  (E)
						// #2799対応 2017.02.02 X.Liu Del  (S)
						//if(meisaiData_old.getEos_Kb() != null && meisaiData_old.getEos_Kb().equals(meisaiData_new.getEos_Kb())){
						//	meisaiData_new.setEos_Kb(null);
						//}
						//if(meisaiData_old.getBin_1_Kb() != null && meisaiData_old.getBin_1_Kb().equals(meisaiData_new.getBin_1_Kb())){
						//	meisaiData_new.setBin_1_Kb(null);
						//}
						//if(meisaiData_old.getBin_2_Kb() != null && meisaiData_old.getBin_2_Kb().equals(meisaiData_new.getBin_2_Kb())){
						//	meisaiData_new.setBin_2_Kb(null);
						//}
						// #2799対応 2017.02.02 X.Liu Del  (E)

						// その他項目に対して変更があった場合、直近世代のその他項目に ● を付加する
						int othersFlg = 0;
						meisaiData_new.setOthers_Mod_Kb("");

						
						// #2799対応 2017.02.02 X.Liu Add  (S)
						if(meisaiData_old.getSyokai_user_id() != null && meisaiData_old.getSyokai_user_id().equals(meisaiData_new.getSyokai_user_id())){
						    meisaiData_new.setSyokai_user_id(null);
						}
						// #2799対応 2017.02.02 X.Liu Add  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSystem_Kb() != null && !meisaiData_old.getSystem_Kb().equals(meisaiData_new.getSystem_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSystem_Kb() != null && meisaiData_old.getSystem_Kb().equals(meisaiData_new.getSystem_Kb())){
						    meisaiData_new.setSystem_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getGyosyu_Kb() != null && !meisaiData_old.getGyosyu_Kb().equals(meisaiData_new.getGyosyu_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getGyosyu_Kb() != null && meisaiData_old.getGyosyu_Kb().equals(meisaiData_new.getGyosyu_Kb())){
						    meisaiData_new.setGyosyu_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohin_Kb() != null && !meisaiData_old.getSyohin_Kb().equals(meisaiData_new.getSyohin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohin_Kb() != null && meisaiData_old.getSyohin_Kb().equals(meisaiData_new.getSyohin_Kb())){
						    meisaiData_new.setSyohin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getBunrui2_Cd() != null && !meisaiData_old.getBunrui2_Cd().equals(meisaiData_new.getBunrui2_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getBunrui2_Cd() != null && meisaiData_old.getBunrui2_Cd().equals(meisaiData_new.getBunrui2_Cd())){
						    meisaiData_new.setBunrui2_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohin_2_Cd() != null && !meisaiData_old.getSyohin_2_Cd().equals(meisaiData_new.getSyohin_2_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohin_2_Cd() != null && meisaiData_old.getSyohin_2_Cd().equals(meisaiData_new.getSyohin_2_Cd())){
						    meisaiData_new.setSyohin_2_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getZaiko_Syohin_Cd() != null && !meisaiData_old.getZaiko_Syohin_Cd().equals(meisaiData_new.getZaiko_Syohin_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getZaiko_Syohin_Cd() != null && meisaiData_old.getZaiko_Syohin_Cd().equals(meisaiData_new.getZaiko_Syohin_Cd())){
						    meisaiData_new.setZaiko_Syohin_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getJyoho_Syohin_Cd() != null && !meisaiData_old.getJyoho_Syohin_Cd().equals(meisaiData_new.getJyoho_Syohin_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getJyoho_Syohin_Cd() != null && meisaiData_old.getJyoho_Syohin_Cd().equals(meisaiData_new.getJyoho_Syohin_Cd())){
						    meisaiData_new.setJyoho_Syohin_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
//						if(othersFlg == 0 && meisaiData_old.getKikaku_Kana_Na() != null && !meisaiData_old.getKikaku_Kana_Na().equals(meisaiData_new.getKikaku_Kana_Na())){
//							meisaiData_new.setOthers_Mod_Kb("●");
//							othersFlg = 1;
//						}
//						if(othersFlg == 0 && meisaiData_old.getKikaku_Kana_2_Na() != null && !meisaiData_old.getKikaku_Kana_2_Na().equals(meisaiData_new.getKikaku_Kana_2_Na())){
//							meisaiData_new.setOthers_Mod_Kb("●");
//							othersFlg = 1;
//						}
//						if(othersFlg == 0 && meisaiData_old.getRec_Hinmei_Kana_Na() != null && !meisaiData_old.getRec_Hinmei_Kana_Na().equals(meisaiData_new.getRec_Hinmei_Kana_Na())){
//							meisaiData_new.setOthers_Mod_Kb("●");
//							othersFlg = 1;
//						}
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohin_Width_Qt() != null && !meisaiData_old.getSyohin_Width_Qt().equals(meisaiData_new.getSyohin_Width_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohin_Width_Qt() != null && meisaiData_old.getSyohin_Width_Qt().equals(meisaiData_new.getSyohin_Width_Qt())){
						    meisaiData_new.setSyohin_Width_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohin_Height_Qt() != null && !meisaiData_old.getSyohin_Height_Qt().equals(meisaiData_new.getSyohin_Height_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohin_Height_Qt() != null && meisaiData_old.getSyohin_Height_Qt().equals(meisaiData_new.getSyohin_Height_Qt())){
						    meisaiData_new.setSyohin_Height_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohin_Depth_Qt() != null && !meisaiData_old.getSyohin_Depth_Qt().equals(meisaiData_new.getSyohin_Depth_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohin_Depth_Qt() != null && meisaiData_old.getSyohin_Depth_Qt().equals(meisaiData_new.getSyohin_Depth_Qt())){
						    meisaiData_new.setSyohin_Depth_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getZei_Kb() != null && !meisaiData_old.getZei_Kb().equals(meisaiData_new.getZei_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getZei_Kb() != null && meisaiData_old.getZei_Kb().equals(meisaiData_new.getZei_Kb())){
						    meisaiData_new.setZei_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTax_Rate_Kb() != null && !meisaiData_old.getTax_Rate_Kb().equals(meisaiData_new.getTax_Rate_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTax_Rate_Kb() != null && meisaiData_old.getTax_Rate_Kb().equals(meisaiData_new.getTax_Rate_Kb())){
						    meisaiData_new.setTax_Rate_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getGentanka_Nuki_Vl() != null && !meisaiData_old.getGentanka_Nuki_Vl().equals(meisaiData_new.getGentanka_Nuki_Vl())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getGentanka_Nuki_Vl() != null && meisaiData_old.getGentanka_Nuki_Vl().equals(meisaiData_new.getGentanka_Nuki_Vl())){
						    meisaiData_new.setGentanka_Nuki_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getBaitanka_Nuki_Vl() != null && !meisaiData_old.getBaitanka_Nuki_Vl().equals(meisaiData_new.getBaitanka_Nuki_Vl())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getBaitanka_Nuki_Vl() != null && meisaiData_old.getBaitanka_Nuki_Vl().equals(meisaiData_new.getBaitanka_Nuki_Vl())){
						    meisaiData_new.setBaitanka_Nuki_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getMaker_Kibo_Kakaku_Vl() != null && !meisaiData_old.getMaker_Kibo_Kakaku_Vl().equals(meisaiData_new.getMaker_Kibo_Kakaku_Vl())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getMaker_Kibo_Kakaku_Vl() != null && meisaiData_old.getMaker_Kibo_Kakaku_Vl().equals(meisaiData_new.getMaker_Kibo_Kakaku_Vl())){
						    meisaiData_new.setMaker_Kibo_Kakaku_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHacyu_Syohin_Kb() != null && !meisaiData_old.getHacyu_Syohin_Kb().equals(meisaiData_new.getHacyu_Syohin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHacyu_Syohin_Kb() != null && meisaiData_old.getHacyu_Syohin_Kb().equals(meisaiData_new.getHacyu_Syohin_Kb())){
						    meisaiData_new.setHacyu_Syohin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHacyu_Syohin_Cd() != null && !meisaiData_old.getHacyu_Syohin_Cd().equals(meisaiData_new.getHacyu_Syohin_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHacyu_Syohin_Cd() != null && meisaiData_old.getHacyu_Syohin_Cd().equals(meisaiData_new.getHacyu_Syohin_Cd())){
						    meisaiData_new.setHacyu_Syohin_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHachu_Tani_Na() != null && !meisaiData_old.getHachu_Tani_Na().equals(meisaiData_new.getHachu_Tani_Na())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHachu_Tani_Na() != null && meisaiData_old.getHachu_Tani_Na().equals(meisaiData_new.getHachu_Tani_Na())){
						    meisaiData_new.setHachu_Tani_Na(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_Tani() != null && !meisaiData_old.getHanbai_Tani().equals(meisaiData_new.getHanbai_Tani())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_Tani() != null && meisaiData_old.getHanbai_Tani().equals(meisaiData_new.getHanbai_Tani())){
						    meisaiData_new.setHanbai_Tani(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getMax_Hachutani_Qt() != null && !meisaiData_old.getMax_Hachutani_Qt().equals(meisaiData_new.getMax_Hachutani_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getMax_Hachutani_Qt() != null && meisaiData_old.getMax_Hachutani_Qt().equals(meisaiData_new.getMax_Hachutani_Qt())){
						    meisaiData_new.setMax_Hachutani_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getCase_Hachu_Kb() != null && !meisaiData_old.getCase_Hachu_Kb().equals(meisaiData_new.getCase_Hachu_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getCase_Hachu_Kb() != null && meisaiData_old.getCase_Hachu_Kb().equals(meisaiData_new.getCase_Hachu_Kb())){
						    meisaiData_new.setCase_Hachu_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getBara_Irisu_Qt() != null && !meisaiData_old.getBara_Irisu_Qt().equals(meisaiData_new.getBara_Irisu_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getBara_Irisu_Qt() != null && meisaiData_old.getBara_Irisu_Qt().equals(meisaiData_new.getBara_Irisu_Qt())){
						    meisaiData_new.setBara_Irisu_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Hachu_St_Dt() != null && !meisaiData_old.getTen_Hachu_St_Dt().equals(meisaiData_new.getTen_Hachu_St_Dt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Hachu_St_Dt() != null && meisaiData_old.getTen_Hachu_St_Dt().equals(meisaiData_new.getTen_Hachu_St_Dt())){
						    meisaiData_new.setTen_Hachu_St_Dt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Hachu_Ed_Dt() != null && !meisaiData_old.getTen_Hachu_Ed_Dt().equals(meisaiData_new.getTen_Hachu_Ed_Dt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Hachu_Ed_Dt() != null && meisaiData_old.getTen_Hachu_Ed_Dt().equals(meisaiData_new.getTen_Hachu_Ed_Dt())){
						    meisaiData_new.setTen_Hachu_Ed_Dt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_St_Dt() != null && !meisaiData_old.getHanbai_St_Dt().equals(meisaiData_new.getHanbai_St_Dt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_St_Dt() != null && meisaiData_old.getHanbai_St_Dt().equals(meisaiData_new.getHanbai_St_Dt())){
						    meisaiData_new.setHanbai_St_Dt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_Ed_Dt() != null && !meisaiData_old.getHanbai_Ed_Dt().equals(meisaiData_new.getHanbai_Ed_Dt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_Ed_Dt() != null && meisaiData_old.getHanbai_Ed_Dt().equals(meisaiData_new.getHanbai_Ed_Dt())){
						    meisaiData_new.setHanbai_Ed_Dt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_Kikan_Kb() != null && !meisaiData_old.getHanbai_Kikan_Kb().equals(meisaiData_new.getHanbai_Kikan_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_Kikan_Kb() != null && meisaiData_old.getHanbai_Kikan_Kb().equals(meisaiData_new.getHanbai_Kikan_Kb())){
						    meisaiData_new.setHanbai_Kikan_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTeikan_Kb() != null && !meisaiData_old.getTeikan_Kb().equals(meisaiData_new.getTeikan_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTeikan_Kb() != null && meisaiData_old.getTeikan_Kb().equals(meisaiData_new.getTeikan_Kb())){
						    meisaiData_new.setTeikan_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSoba_Syohin_Kb() != null && !meisaiData_old.getSoba_Syohin_Kb().equals(meisaiData_new.getSoba_Syohin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSoba_Syohin_Kb() != null && meisaiData_old.getSoba_Syohin_Kb().equals(meisaiData_new.getSoba_Syohin_Kb())){
						    meisaiData_new.setSoba_Syohin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getNohin_Kigen_Kb() != null && !meisaiData_old.getNohin_Kigen_Kb().equals(meisaiData_new.getNohin_Kigen_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getNohin_Kigen_Kb() != null && meisaiData_old.getNohin_Kigen_Kb().equals(meisaiData_new.getNohin_Kigen_Kb())){
						    meisaiData_new.setNohin_Kigen_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getNohin_Kigen_Qt() != null && !meisaiData_old.getNohin_Kigen_Qt().equals(meisaiData_new.getNohin_Kigen_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getNohin_Kigen_Qt() != null && meisaiData_old.getNohin_Kigen_Qt().equals(meisaiData_new.getNohin_Kigen_Qt())){
						    meisaiData_new.setNohin_Kigen_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHachu_Pattern_1_Kb() != null && !meisaiData_old.getHachu_Pattern_1_Kb().equals(meisaiData_new.getHachu_Pattern_1_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHachu_Pattern_1_Kb() != null && meisaiData_old.getHachu_Pattern_1_Kb().equals(meisaiData_new.getHachu_Pattern_1_Kb())){
						    meisaiData_new.setHachu_Pattern_1_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSime_Time_1_Qt() != null && !meisaiData_old.getSime_Time_1_Qt().equals(meisaiData_new.getSime_Time_1_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSime_Time_1_Qt() != null && meisaiData_old.getSime_Time_1_Qt().equals(meisaiData_new.getSime_Time_1_Qt())){
						    meisaiData_new.setSime_Time_1_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getC_Nohin_Rtime_1_Kb() != null && !meisaiData_old.getC_Nohin_Rtime_1_Kb().equals(meisaiData_new.getC_Nohin_Rtime_1_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getC_Nohin_Rtime_1_Kb() != null && meisaiData_old.getC_Nohin_Rtime_1_Kb().equals(meisaiData_new.getC_Nohin_Rtime_1_Kb())){
						    meisaiData_new.setC_Nohin_Rtime_1_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Nohin_Rtime_1_Kb() != null && !meisaiData_old.getTen_Nohin_Rtime_1_Kb().equals(meisaiData_new.getTen_Nohin_Rtime_1_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Nohin_Rtime_1_Kb() != null && meisaiData_old.getTen_Nohin_Rtime_1_Kb().equals(meisaiData_new.getTen_Nohin_Rtime_1_Kb())){
						    meisaiData_new.setTen_Nohin_Rtime_1_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Nohin_Time_1_Kb() != null && !meisaiData_old.getTen_Nohin_Time_1_Kb().equals(meisaiData_new.getTen_Nohin_Time_1_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Nohin_Time_1_Kb() != null && meisaiData_old.getTen_Nohin_Time_1_Kb().equals(meisaiData_new.getTen_Nohin_Time_1_Kb())){
						    meisaiData_new.setTen_Nohin_Time_1_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHachu_Pattern_2_Kb() != null && !meisaiData_old.getHachu_Pattern_2_Kb().equals(meisaiData_new.getHachu_Pattern_2_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHachu_Pattern_2_Kb() != null && meisaiData_old.getHachu_Pattern_2_Kb().equals(meisaiData_new.getHachu_Pattern_2_Kb())){
						    meisaiData_new.setHachu_Pattern_2_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSime_Time_2_Qt() != null && !meisaiData_old.getSime_Time_2_Qt().equals(meisaiData_new.getSime_Time_2_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSime_Time_2_Qt() != null && meisaiData_old.getSime_Time_2_Qt().equals(meisaiData_new.getSime_Time_2_Qt())){
						    meisaiData_new.setSime_Time_2_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getC_Nohin_Rtime_2_Kb() != null && !meisaiData_old.getC_Nohin_Rtime_2_Kb().equals(meisaiData_new.getC_Nohin_Rtime_2_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getC_Nohin_Rtime_2_Kb() != null && meisaiData_old.getC_Nohin_Rtime_2_Kb().equals(meisaiData_new.getC_Nohin_Rtime_2_Kb())){
						    meisaiData_new.setC_Nohin_Rtime_2_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Nohin_Rtime_2_Kb() != null && !meisaiData_old.getTen_Nohin_Rtime_2_Kb().equals(meisaiData_new.getTen_Nohin_Rtime_2_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Nohin_Rtime_2_Kb() != null && meisaiData_old.getTen_Nohin_Rtime_2_Kb().equals(meisaiData_new.getTen_Nohin_Rtime_2_Kb())){
						    meisaiData_new.setTen_Nohin_Rtime_2_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTen_Nohin_Time_2_Kb() != null && !meisaiData_old.getTen_Nohin_Time_2_Kb().equals(meisaiData_new.getTen_Nohin_Time_2_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTen_Nohin_Time_2_Kb() != null && meisaiData_old.getTen_Nohin_Time_2_Kb().equals(meisaiData_new.getTen_Nohin_Time_2_Kb())){
						    meisaiData_new.setTen_Nohin_Time_2_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getYusen_Bin_Kb() != null && !meisaiData_old.getYusen_Bin_Kb().equals(meisaiData_new.getYusen_Bin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getYusen_Bin_Kb() != null && meisaiData_old.getYusen_Bin_Kb().equals(meisaiData_new.getYusen_Bin_Kb())){
						    meisaiData_new.setYusen_Bin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getF_Bin_Kb() != null && !meisaiData_old.getF_Bin_Kb().equals(meisaiData_new.getF_Bin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getF_Bin_Kb() != null && meisaiData_old.getF_Bin_Kb().equals(meisaiData_new.getF_Bin_Kb())){
						    meisaiData_new.setF_Bin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getButuryu_Kb() != null && !meisaiData_old.getButuryu_Kb().equals(meisaiData_new.getButuryu_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getButuryu_Kb() != null && meisaiData_old.getButuryu_Kb().equals(meisaiData_new.getButuryu_Kb())){
						    meisaiData_new.setButuryu_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getGot_Start_Mm() != null && !meisaiData_old.getGot_Start_Mm().equals(meisaiData_new.getGot_Start_Mm())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getGot_Start_Mm() != null && meisaiData_old.getGot_Start_Mm().equals(meisaiData_new.getGot_Start_Mm())){
						    meisaiData_new.setGot_Start_Mm(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getGot_End_Mm() != null && !meisaiData_old.getGot_End_Mm().equals(meisaiData_new.getGot_End_Mm())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getGot_End_Mm() != null && meisaiData_old.getGot_End_Mm().equals(meisaiData_new.getGot_End_Mm())){
						    meisaiData_new.setGot_End_Mm(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHachu_Teisi_Kb() != null && !meisaiData_old.getHachu_Teisi_Kb().equals(meisaiData_new.getHachu_Teisi_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHachu_Teisi_Kb() != null && meisaiData_old.getHachu_Teisi_Kb().equals(meisaiData_new.getHachu_Teisi_Kb())){
						    meisaiData_new.setHachu_Teisi_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getCgc_Henpin_Kb() != null && !meisaiData_old.getCgc_Henpin_Kb().equals(meisaiData_new.getCgc_Henpin_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getCgc_Henpin_Kb() != null && meisaiData_old.getCgc_Henpin_Kb().equals(meisaiData_new.getCgc_Henpin_Kb())){
						    meisaiData_new.setCgc_Henpin_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_Limit_Kb() != null && !meisaiData_old.getHanbai_Limit_Kb().equals(meisaiData_new.getHanbai_Limit_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_Limit_Kb() != null && meisaiData_old.getHanbai_Limit_Kb().equals(meisaiData_new.getHanbai_Limit_Kb())){
						    meisaiData_new.setHanbai_Limit_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getHanbai_Limit_Qt() != null && !meisaiData_old.getHanbai_Limit_Qt().equals(meisaiData_new.getHanbai_Limit_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getHanbai_Limit_Qt() != null && meisaiData_old.getHanbai_Limit_Qt().equals(meisaiData_new.getHanbai_Limit_Qt())){
						    meisaiData_new.setHanbai_Limit_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getKeyplu_Fg() != null && !meisaiData_old.getKeyplu_Fg().equals(meisaiData_new.getKeyplu_Fg())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getKeyplu_Fg() != null && meisaiData_old.getKeyplu_Fg().equals(meisaiData_new.getKeyplu_Fg())){
						    meisaiData_new.setKeyplu_Fg(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getPlu_Kb() != null && !meisaiData_old.getPlu_Kb().equals(meisaiData_new.getPlu_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getPlu_Kb() != null && meisaiData_old.getPlu_Kb().equals(meisaiData_new.getPlu_Kb())){
						    meisaiData_new.setPlu_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyuzei_Hokoku_Kb() != null && !meisaiData_old.getSyuzei_Hokoku_Kb().equals(meisaiData_new.getSyuzei_Hokoku_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyuzei_Hokoku_Kb() != null && meisaiData_old.getSyuzei_Hokoku_Kb().equals(meisaiData_new.getSyuzei_Hokoku_Kb())){
						    meisaiData_new.setSyuzei_Hokoku_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSake_Naiyoryo_Qt() != null && !meisaiData_old.getSake_Naiyoryo_Qt().equals(meisaiData_new.getSake_Naiyoryo_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSake_Naiyoryo_Qt() != null && meisaiData_old.getSake_Naiyoryo_Qt().equals(meisaiData_new.getSake_Naiyoryo_Qt())){
						    meisaiData_new.setSake_Naiyoryo_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getTag_Hyoji_Baika_Vl() != null && !meisaiData_old.getTag_Hyoji_Baika_Vl().equals(meisaiData_new.getTag_Hyoji_Baika_Vl())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getTag_Hyoji_Baika_Vl() != null && meisaiData_old.getTag_Hyoji_Baika_Vl().equals(meisaiData_new.getTag_Hyoji_Baika_Vl())){
						    meisaiData_new.setTag_Hyoji_Baika_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getKeshi_Baika_Vl() != null && !meisaiData_old.getKeshi_Baika_Vl().equals(meisaiData_new.getKeshi_Baika_Vl())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getKeshi_Baika_Vl() != null && meisaiData_old.getKeshi_Baika_Vl().equals(meisaiData_new.getKeshi_Baika_Vl())){
						    meisaiData_new.setKeshi_Baika_Vl(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getYoridori_Kb() != null && !meisaiData_old.getYoridori_Kb().equals(meisaiData_new.getYoridori_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getYoridori_Kb() != null && meisaiData_old.getYoridori_Kb().equals(meisaiData_new.getYoridori_Kb())){
						    meisaiData_new.setYoridori_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getPc_Kb() != null && !meisaiData_old.getPc_Kb().equals(meisaiData_new.getPc_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getPc_Kb() != null && meisaiData_old.getPc_Kb().equals(meisaiData_new.getPc_Kb())){
						    meisaiData_new.setPc_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getDaisi_No_Nb() != null && !meisaiData_old.getDaisi_No_Nb().equals(meisaiData_new.getDaisi_No_Nb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getDaisi_No_Nb() != null && meisaiData_old.getDaisi_No_Nb().equals(meisaiData_new.getDaisi_No_Nb())){
						    meisaiData_new.setDaisi_No_Nb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getUnit_Price_Tani_Kb() != null && !meisaiData_old.getUnit_Price_Tani_Kb().equals(meisaiData_new.getUnit_Price_Tani_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getUnit_Price_Tani_Kb() != null && meisaiData_old.getUnit_Price_Tani_Kb().equals(meisaiData_new.getUnit_Price_Tani_Kb())){
						    meisaiData_new.setUnit_Price_Tani_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getUnit_Price_Naiyoryo_Qt() != null && !meisaiData_old.getUnit_Price_Naiyoryo_Qt().equals(meisaiData_new.getUnit_Price_Naiyoryo_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getUnit_Price_Naiyoryo_Qt() != null && meisaiData_old.getUnit_Price_Naiyoryo_Qt().equals(meisaiData_new.getUnit_Price_Naiyoryo_Qt())){
						    meisaiData_new.setUnit_Price_Naiyoryo_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getUnit_Price_Kijun_Tani_Qt() != null && !meisaiData_old.getUnit_Price_Kijun_Tani_Qt().equals(meisaiData_new.getUnit_Price_Kijun_Tani_Qt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getUnit_Price_Kijun_Tani_Qt() != null && meisaiData_old.getUnit_Price_Kijun_Tani_Qt().equals(meisaiData_new.getUnit_Price_Kijun_Tani_Qt())){
						    meisaiData_new.setUnit_Price_Kijun_Tani_Qt(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohi_Kigen_Kb() != null && !meisaiData_old.getSyohi_Kigen_Kb().equals(meisaiData_new.getSyohi_Kigen_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getOthers_Mod_Kb() != null && meisaiData_old.getSyohi_Kigen_Kb().equals(meisaiData_new.getSyohi_Kigen_Kb())){
						    meisaiData_new.setOthers_Mod_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getSyohi_Kigen_Dt() != null && !meisaiData_old.getSyohi_Kigen_Dt().equals(meisaiData_new.getSyohi_Kigen_Dt())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getSyohi_Kigen_Dt() != null && meisaiData_old.getSyohi_Kigen_Dt().equals(meisaiData_new.getSyohi_Kigen_Dt())){
						    meisaiData_new.setSyohi_Kigen_Dt(null);
						}
					    
						if(meisaiData_old.getSyohin_eiji_na() != null && meisaiData_old.getSyohin_eiji_na().equals(meisaiData_new.getSyohin_eiji_na())){
						    meisaiData_new.setSyohin_eiji_na(null);
						}
						if(meisaiData_old.getLabel_seibun() != null && meisaiData_old.getLabel_seibun().equals(meisaiData_new.getLabel_seibun())){
						    meisaiData_new.setLabel_seibun(null);
						}
						if(meisaiData_old.getLabel_hokan_hoho() != null && meisaiData_old.getLabel_hokan_hoho().equals(meisaiData_new.getLabel_hokan_hoho())){
						    meisaiData_new.setLabel_hokan_hoho(null);
						}
						if(meisaiData_old.getLabel_tukaikata() != null && meisaiData_old.getLabel_tukaikata().equals(meisaiData_new.getLabel_tukaikata())){
						    meisaiData_new.setLabel_tukaikata(null);
						}
						if(meisaiData_old.getCgc_Henpin_Kb() != null && meisaiData_old.getCgc_Henpin_Kb().equals(meisaiData_new.getCgc_Henpin_Kb())){
						    meisaiData_new.setCgc_Henpin_Kb(null);
						}
						if(meisaiData_old.getNenrei_seigen_kb() != null && meisaiData_old.getNenrei_seigen_kb().equals(meisaiData_new.getNenrei_seigen_kb())){
						    meisaiData_new.setNenrei_seigen_kb(null);
						}
						if(meisaiData_old.getNenrei() != null && meisaiData_old.getNenrei().equals(meisaiData_new.getNenrei())){
						    meisaiData_new.setNenrei(null);
						}
						if(meisaiData_old.getKan_kb() != null && meisaiData_old.getKan_kb().equals(meisaiData_new.getKan_kb())){
						    meisaiData_new.setKan_kb(null);
						}
						if(meisaiData_old.getHoshoukin() != null && meisaiData_old.getHoshoukin().equals(meisaiData_new.getHoshoukin())){
						    meisaiData_new.setHoshoukin(null);
						}
						if(meisaiData_old.getSecurity_tag_fg() != null && meisaiData_old.getSecurity_tag_fg().equals(meisaiData_new.getSecurity_tag_fg())){
						    meisaiData_new.setSecurity_tag_fg(null);
						}
						if(meisaiData_old.getHamper_syohin_fg() != null && meisaiData_old.getHamper_syohin_fg().equals(meisaiData_new.getHamper_syohin_fg())){
						    meisaiData_new.setHamper_syohin_fg(null);
						}

						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_1_Kb() != null && !meisaiData_old.getFree_1_Kb().equals(meisaiData_new.getFree_1_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_1_Kb() != null && meisaiData_old.getFree_1_Kb().equals(meisaiData_new.getFree_1_Kb())){
						    meisaiData_new.setFree_1_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_2_Kb() != null && !meisaiData_old.getFree_2_Kb().equals(meisaiData_new.getFree_2_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_2_Kb() != null && meisaiData_old.getFree_2_Kb().equals(meisaiData_new.getFree_2_Kb())){
						    meisaiData_new.setFree_2_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_3_Kb() != null && !meisaiData_old.getFree_3_Kb().equals(meisaiData_new.getFree_3_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_3_Kb() != null && meisaiData_old.getFree_3_Kb().equals(meisaiData_new.getFree_3_Kb())){
						    meisaiData_new.setFree_3_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_4_Kb() != null && !meisaiData_old.getFree_4_Kb().equals(meisaiData_new.getFree_4_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_4_Kb() != null && meisaiData_old.getFree_4_Kb().equals(meisaiData_new.getFree_4_Kb())){
						    meisaiData_new.setFree_4_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_5_Kb() != null && !meisaiData_old.getFree_5_Kb().equals(meisaiData_new.getFree_5_Kb())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_5_Kb() != null && meisaiData_old.getFree_5_Kb().equals(meisaiData_new.getFree_5_Kb())){
						    meisaiData_new.setFree_5_Kb(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getComment_1_Tx() != null && !meisaiData_old.getComment_1_Tx().equals(meisaiData_new.getComment_1_Tx())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getComment_1_Tx() != null && meisaiData_old.getComment_1_Tx().equals(meisaiData_new.getComment_1_Tx())){
						    meisaiData_new.setComment_1_Tx(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getComment_2_Tx() != null && !meisaiData_old.getComment_2_Tx().equals(meisaiData_new.getComment_2_Tx())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getComment_2_Tx() != null && meisaiData_old.getComment_2_Tx().equals(meisaiData_new.getComment_2_Tx())){
						    meisaiData_new.setComment_2_Tx(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getFree_Cd() != null && !meisaiData_old.getFree_Cd().equals(meisaiData_new.getFree_Cd())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getFree_Cd() != null && meisaiData_old.getFree_Cd().equals(meisaiData_new.getFree_Cd())){
						    meisaiData_new.setFree_Cd(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//if(othersFlg == 0 && meisaiData_old.getComment_Tx () != null && !meisaiData_old.getComment_Tx ().equals(meisaiData_new.getComment_Tx ())){
						//	meisaiData_new.setOthers_Mod_Kb(MessageUtil.getMessage("MST01018_TXT_00070", userLocal));
						//	othersFlg = 1;
						if(meisaiData_old.getComment_Tx() != null && meisaiData_old.getComment_Tx ().equals(meisaiData_new.getComment_Tx ())){
						    meisaiData_new.setComment_Tx(null);
						}
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						// #2799対応 2017.02.02 X.Liu Add  (S)SIIRE_TAX_RATE_KB
						if(meisaiData_old.getSinki_toroku_dt() != null && meisaiData_old.getSinki_toroku_dt ().equals(meisaiData_new.getSinki_toroku_dt())){
						    meisaiData_new.setSinki_toroku_dt(null);
						}
						if(meisaiData_old.getGentanka_Vl() != null && meisaiData_old.getGentanka_Vl().equals(meisaiData_new.getGentanka_Vl())){
						    meisaiData_new.setGentanka_Vl(null);
						}
						if(meisaiData_old.getSiire_tax_rate_kb() != null && meisaiData_old.getSiire_tax_rate_kb().equals(meisaiData_new.getSiire_tax_rate_kb())){
						    meisaiData_new.setSiire_tax_rate_kb(null);
						}
						if(meisaiData_old.getOrosi_tax_rate_kb() != null && meisaiData_old.getOrosi_tax_rate_kb().equals(meisaiData_new.getOrosi_tax_rate_kb())){
						    meisaiData_new.setOrosi_tax_rate_kb(null);
						}
						if(meisaiData_old.getSiire_tax_rate_kb() != null && meisaiData_old.getSiire_tax_rate_kb().equals(meisaiData_new.getSiire_tax_rate_kb())){
						    meisaiData_new.setSiire_tax_rate_kb(null);
						}
						if(meisaiData_old.getUpdate_User_Id() != null && meisaiData_old.getUpdate_User_Id().equals(meisaiData_new.getUpdate_User_Id())){
						    meisaiData_new.setUpdate_User_Id(null);
						}
						if(meisaiData_old.getUpdate_Ts() != null && meisaiData_old.getUpdate_Ts().equals(meisaiData_new.getUpdate_Ts())){
						    meisaiData_new.setUpdate_Ts(null);
						}
						if(meisaiData_old.getCountry_cd() != null && meisaiData_old.getCountry_cd().equals(meisaiData_new.getCountry_cd())){
						    meisaiData_new.setCountry_cd(null);
						}
						if(meisaiData_old.getPack_weight_qt() != null && meisaiData_old.getPack_weight_qt().equals(meisaiData_new.getPack_weight_qt())){
						    meisaiData_new.setPack_weight_qt(null);
						}
						if(meisaiData_old.getMaker_cd() != null && meisaiData_old.getMaker_cd().equals(meisaiData_new.getMaker_cd())){
						    meisaiData_new.setMaker_cd(null);
						}
						if(meisaiData_old.getHanbai_hoho_kb() != null && meisaiData_old.getHanbai_hoho_kb().equals(meisaiData_new.getHanbai_hoho_kb())){
						    meisaiData_new.setHanbai_hoho_kb(null);
						}
						if(meisaiData_old.getOrosi_baitanka_vl() != null && meisaiData_old.getOrosi_baitanka_vl().equals(meisaiData_new.getOrosi_baitanka_vl())){
						    meisaiData_new.setOrosi_baitanka_vl(null);
						}
						if(meisaiData_old.getOrosi_baitanka_vl_kouri() != null && meisaiData_old.getOrosi_baitanka_vl_kouri().equals(meisaiData_new.getOrosi_baitanka_vl_kouri())){
						    meisaiData_new.setOrosi_baitanka_vl_kouri(null);
						}
						if(meisaiData_old.getOrosi_zei_kb() != null && meisaiData_old.getOrosi_zei_kb().equals(meisaiData_new.getOrosi_zei_kb())){
						    meisaiData_new.setOrosi_zei_kb(null);
						}
						if(meisaiData_old.getSiire_zei_kb() != null && meisaiData_old.getSiire_zei_kb().equals(meisaiData_new.getSiire_zei_kb())){
						    meisaiData_new.setSiire_zei_kb(null);
						}
						if(meisaiData_old.getMin_zaiko_qt() != null && meisaiData_old.getMin_zaiko_qt().equals(meisaiData_new.getMin_zaiko_qt())){
						    meisaiData_new.setMin_zaiko_qt(null);
						}
						if(meisaiData_old.getCenter_kyoyo_dt() != null && meisaiData_old.getCenter_kyoyo_dt().equals(meisaiData_new.getCenter_kyoyo_dt())){
						    meisaiData_new.setCenter_kyoyo_dt(null);
						}
						if(meisaiData_old.getCenter_kyoyo_kb() != null && meisaiData_old.getCenter_kyoyo_kb().equals(meisaiData_new.getCenter_kyoyo_kb())){
						    meisaiData_new.setCenter_kyoyo_kb(null);
						}
						if(meisaiData_old.getSyohi_kigen_hyoji_patter() != null && meisaiData_old.getSyohi_kigen_hyoji_patter().equals(meisaiData_new.getSyohi_kigen_hyoji_patter())){
						    meisaiData_new.setSyohi_kigen_hyoji_patter(null);
						}
						if(meisaiData_old.getPer_txt() != null && meisaiData_old.getPer_txt().equals(meisaiData_new.getPer_txt())){
						    meisaiData_new.setPer_txt(null);
						}
						// #2799対応 2017.02.02 X.Liu Add  (E)
					}
					// 区分「2:削除」の場合、直近世代の項目名を非表示にする
					if(meisaiData_old.getKb().equals("2")) {
						meisaiData_new.setBunrui1_Cd(null);
						meisaiData_new.setBunrui5_Cd(null);
						meisaiData_new.setKikaku_Kanji_2_Na(null);
						meisaiData_new.setHinmei_Kanji_Na(null);
						meisaiData_new.setRec_Hinmei_Kanji_Na(null);
//						meisaiData_new.setHinmei_Kana_Na(null);
						meisaiData_new.setKikaku_Kanji_Na(null);
						meisaiData_new.setBaitanka_Vl(null);
						// #2799対応 2017.02.02 X.Liu Mod  (S)
						//meisaiData_new.setBaika_Haishin_Fg(null);
						meisaiData_new.setGentanka_Vl(null);
						meisaiData_new.setOrosi_baitanka_nuki_vl(null);
						// #2799対応 2017.02.02 X.Liu Mod  (E)
						meisaiData_new.setSiiresaki_Cd(null);
						meisaiData_new.setHachutani_Irisu_Qt(null);
						// #2799対応 2017.02.02 X.Liu Del  (S)
						//meisaiData_new.setEos_Kb(null);
						//meisaiData_new.setBin_1_Kb(null);
						//meisaiData_new.setBin_2_Kb(null);
						// #2799対応 2017.02.02 X.Liu Del  (E)
						// #2799対応 2017.02.02 X.Liu Add  (S)
						meisaiData_new.setGentanka_Nuki_Vl(null);
						meisaiData_new.setMin_hachu_qt(null);
						meisaiData_new.setUpdate_User_Id(null);
						meisaiData_new.setUpdate_Ts(null);
						meisaiData_new.setBunrui2_Cd(null);
						meisaiData_new.setSinki_toroku_dt(null);
						meisaiData_new.setSyokai_user_id(null);
						meisaiData_new.setSyohin_Kb(null);
						meisaiData_new.setSoba_Syohin_Kb(null);
						meisaiData_new.setTeikan_Kb(null);
						meisaiData_new.setZaiko_Syohin_Cd(null);
						meisaiData_new.setSyohin_Height_Qt(null);
						meisaiData_new.setSyohin_Width_Qt(null);
						meisaiData_new.setSyohin_Depth_Qt(null);
						meisaiData_new.setMaker_Kibo_Kakaku_Vl(null);
						meisaiData_new.setPack_weight_qt(null);
						meisaiData_new.setCountry_cd(null);
						meisaiData_new.setMaker_cd(null);
						meisaiData_new.setHanbai_hoho_kb(null);
						meisaiData_new.setButuryu_Kb(null);
						meisaiData_new.setBaitanka_Nuki_Vl(null);
						meisaiData_new.setOrosi_baitanka_vl(null);
						meisaiData_new.setOrosi_baitanka_vl_kouri(null);
						meisaiData_new.setZei_Kb(null);
						meisaiData_new.setTax_Rate_Kb(null);
						meisaiData_new.setSiire_zei_kb(null);
						meisaiData_new.setOrosi_zei_kb(null);
						meisaiData_new.setHachu_Tani_Na(null);
						meisaiData_new.setHanbai_Tani(null);
						meisaiData_new.setMin_zaiko_qt(null);
						meisaiData_new.setMax_Hachutani_Qt(null);
						meisaiData_new.setF_Bin_Kb(null);
						meisaiData_new.setBara_Irisu_Qt(null);
						meisaiData_new.setCase_Hachu_Kb(null);
						meisaiData_new.setPer_txt(null);
						meisaiData_new.setPc_Kb(null);
						meisaiData_new.setDaisi_No_Nb(null);
						meisaiData_new.setHanbai_St_Dt(null);
						meisaiData_new.setHanbai_Ed_Dt(null);
						meisaiData_new.setTen_Hachu_St_Dt(null);
						meisaiData_new.setTen_Hachu_Ed_Dt(null);
						meisaiData_new.setGot_Start_Mm(null);
						meisaiData_new.setGot_End_Mm(null);
						meisaiData_new.setHanbai_Limit_Qt(null);
						meisaiData_new.setHanbai_Limit_Kb(null);
						meisaiData_new.setCenter_kyoyo_dt(null);
						meisaiData_new.setCenter_kyoyo_kb(null);
						meisaiData_new.setUnit_Price_Tani_Kb(null);
						meisaiData_new.setUnit_Price_Naiyoryo_Qt(null);
						meisaiData_new.setUnit_Price_Kijun_Tani_Qt(null);
						meisaiData_new.setSyohi_kigen_hyoji_patter(null);
						meisaiData_new.setSyohi_Kigen_Dt(null);
						meisaiData_new.setSyohin_eiji_na(null);
						meisaiData_new.setLabel_seibun(null);
						meisaiData_new.setLabel_tukaikata(null);
						meisaiData_new.setCgc_Henpin_Kb(null);
						meisaiData_new.setNenrei_seigen_kb(null);
						meisaiData_new.setNenrei(null);
						meisaiData_new.setKan_kb(null);
						meisaiData_new.setHoshoukin(null);
						meisaiData_new.setSecurity_tag_fg(null);
						meisaiData_new.setHamper_syohin_fg(null);
						meisaiData_new.setFree_1_Kb(null);
						meisaiData_new.setFree_2_Kb(null);
						meisaiData_new.setFree_3_Kb(null);
						meisaiData_new.setFree_4_Kb(null);
						meisaiData_new.setFree_5_Kb(null);
						meisaiData_new.setComment_1_Tx(null);
						meisaiData_new.setComment_2_Tx(null);
						meisaiData_new.setFree_Cd(null);
						meisaiData_new.setSiire_tax_rate_kb(null);
						meisaiData_new.setOrosi_tax_rate_kb(null);
						meisaiData_new.setLabel_hokan_hoho(null);
						// #2799対応 2017.02.02 X.Liu Add  (E)
					}

					// 出力用に区分を日本語に変換する
					// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (S)
					/*switch (Integer.parseInt(meisaiData_old.getKb())){
					case 0:
						meisaiData_new.setKb(null);
						meisaiData_old.setKb("新規");
						break;
					case 1:
						meisaiData_new.setKb("修正");
						meisaiData_old.setKb("修正");
						break;
					case 2:
						meisaiData_new.setKb(null);
						meisaiData_old.setKb("削除");
						break;
					default:
					}*/
					// #2799対応 2017.02.02 X.Liu Mod  (S)
//					switch (Integer.parseInt(meisaiData_old.getKb())){
//					case 0:
//						meisaiData_new.setKb(null);
//						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00047", userLocal));
//						break;
//					case 1:
//						meisaiData_new.setKb(MessageUtil.getMessage("MST01018_TXT_00048", userLocal));
//						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00048", userLocal));
//						break;
//					case 2:
//						meisaiData_new.setKb(null);
//						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00049", userLocal));
//						break;
//					default:
//					}
					// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (E)
					// 出力用に区分を日本語に変換する
					// CSVファイル向け
					switch (Integer.parseInt(meisaiData_old.getKb())){
					case 0:
						meisaiData_new.setKb(MessageUtil.getMessage("MST01018_TXT_00047", userLocal));
						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00047", userLocal));
						break;
					case 1:
						meisaiData_new.setKb(MessageUtil.getMessage("MST01018_TXT_00048", userLocal));
						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00048", userLocal));
						break;
					case 2:
						meisaiData_new.setKb(MessageUtil.getMessage("MST01018_TXT_00049", userLocal));
						meisaiData_old.setKb(MessageUtil.getMessage("MST01018_TXT_00049", userLocal));
						break;
					default:
					}
					// #2799対応 2017.02.02 X.Liu Mod  (E)
					// 本部権限非保有者の表示制御が「非表示」である場合、マスキングを行う
					if(SyohinMasterHenkoListVisibilityFg != null && SyohinMasterHenkoListVisibilityFg.equals("1")){
						// #2799対応 2017.02.02 X.Liu Del  (S)
//						if(meisaiData_old.getGentanka_Vl() != null){
//							meisaiData_old.setGentanka_Vl(MASK);
//						}
//						if(meisaiData_new.getGentanka_Vl() != null){
//							meisaiData_new.setGentanka_Vl(MASK);
//						}
						// #2799対応 2017.02.02 X.Liu Del  (E)
						if(meisaiData_old.getSiiresaki_Cd() != null){
							meisaiData_old.setSiiresaki_Cd(MASK);
						}
						if(meisaiData_new.getSiiresaki_Cd() != null){
							meisaiData_new.setSiiresaki_Cd(MASK);
						}
					}

					// #2799対応 2017.02.02 X.Liu Del  (S)
					// 「その他改廃項目除外」が指定されており、かつ該当明細行の「その他改廃項目」がTRUEの場合、出力を行わない
//					if(outputJogaiFgOthers.equals("1") && meisaiData_new.getOthers_Mod_Kb() != null && meisaiData_new.getOthers_Mod_Kb().equals("●")){
//						// 「その他改廃項目除外」が指定されており、かつ該当明細行の「その他改廃項目」がTRUEの場合
//					} else {
					// #2799対応 2017.02.02 X.Liu Del  (E)
						for(int j = 0; j < 2; j++){
							if (j == 0) {
//								setDaichoCSV(meisaiData_old, strSyohin, seq, "old");
							} else {
								setDaichoCSV(meisaiData_new, strSyohin, seq, "new");
								seq++;
							}
//							DownloadList.close();
							strCSV.append(strSyohin);
							strSyohin.setLength(0);
						}
					// #2799対応 2017.02.02 X.Liu Del  (S)
//					}
					// #2799対応 2017.02.02 X.Liu Del  (E)
				}
				iRec++;
			}

			// 出力件数が０件でない場合、CSVファイル生成・PDFファイル生成を行う。
			if(iRec != 0) {

				// 帳票（PDF）生成の元となるCSVを作成する
		        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
				String csvFileName = CHOHYO_ID + systemDt + ".csv";
				String localCsvPath = ResorceUtil.getInstance().getPropertie("AP_CHOHYOCSV_DIR") + "/" + csvFileName;

				String csvStr = strCSV.toString();
				OutputStream out = new BufferedOutputStream(new FileOutputStream(localCsvPath));
				out.write(csvStr.getBytes());
				out.close();

				// #2799対応 2017.02.02 X.Liu Del  (S)
				// ＰＤＦストリームを取得
//				byte[] bytes = mdware.common.MkPdf.getInstance().createPdfStc(localCsvPath, csvFileName,
//				// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (S)
////				         ResorceUtil.getInstance().getPropertie("SYOHIN_MASTER_HENKO_LIST_SHEET_XML"),
//						 I18nUtil.addLocaleSuffix(ResorceUtil.getInstance().getPropertie("SYOHIN_MASTER_HENKO_LIST_SHEET_XML"), userLocal),
//				// No.136 MST01018 Mod 2015.12.08 Nhat.NgoM (E)
//				         ResorceUtil.getInstance().getPropertie("CHOHYO_CSV_RESOURCE")
//				         );
//
//				String pdfFileName = CHOHYO_ID + systemDt + ".pdf";
//				String localPdfPath = ResorceUtil.getInstance().getPropertie("SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_DIR") + "/" + pdfFileName;
//				OutputStream os = new BufferedOutputStream(new FileOutputStream(localPdfPath));
//				os.write(bytes);
//				os.close();
//
//				//アラーム情報
//				String AlarmKb = AlarmProcessKbDic.BATCH.getCode();
//				String AlarmId = null;
//				String downloadPdfFilepath = MASTERHANSOKU_ROOT_URL + downloadPDFForSyohinMasterHenkoList + pdfFileName;
//
//				// アラーム登録対象店舗一覧取得
//				rs = db.executeQuery(getTenpoListSQL());
//
//				//アラームを登録する
//				while (rs.next()) {
//					setAlarm(AlarmKb, AlarmId, rs.getString("TENPO_CD"), downloadPdfFilepath, systemDt);
//				}
				// #2799対応 2017.02.02 X.Liu Del  (E)
			} else {
				// 出力件数が０件の場合、その旨をログに出力する。
				writeLog(Level.INFO_INT, "条件が一致する 商品マスタの変更情報はありませんでした。");
			}

			// 処理終了ログ
			writeLog(Level.INFO_INT, "商品マスタ変更リスト作成処理を終了します。");

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

    /**
	 * アラーム情報の登録と削除。
	 *
	 * @param  strAlarmKb String アラーム区分
	 * @param  AlarmId String アラームID
	 * @param  strTenpoCd String 店舗コード
	 * @param  strUrltx String 帳票URL
	 * @return なし
	 */
    private void setAlarm(String strAlarmKb, String AlarmId, String strTenpoCd, String strUrltx, String systemDt) {

    	// アラーム情報登録クラス
    	MSTB907010_PortalAlarmInterfaceForSyohinMasterHenkoList alarm = new MSTB907010_PortalAlarmInterfaceForSyohinMasterHenkoList();
    	MSTB907010_DtAlarmBeanForSyohinMasterHenkoList alarmBean = new MSTB907010_DtAlarmBeanForSyohinMasterHenkoList();
    	String strProcess = null ;

    	// alarm_kb
    	alarmBean.setAlarmKb(strAlarmKb);
    	// alarm_id
//    	alarmBean.setAlarmId(AlarmId);
    	// destomatopm_tenpo_cd
    	alarmBean.setDestinationTenpoCd(strTenpoCd);
    	// url_tx
    	alarmBean.setUrlTx(strUrltx);
    	// del_key_tx
//    	alarmBean.setInsertUserId();
    	alarmBean.setAlarmTypeNa(CHOHYO_NA);
    	alarmBean.setContentTx("商品マスタが変更されました。");
    	alarmBean.setYukoSyuryoDt(systemDt);


    	strProcess = AlarmProcessKbDic.ONLINE.getCode();

    	// アラーム情報を登録する
    	alarm.setAlarmMessage(alarmBean, strProcess);

	}

    /**
     * <p>アラーム登録対象店舗一覧取得</p>
     *
     * 商品マスタ変更リスト（バッチ）のアラーム登録対象となる店舗一覧を取得する
     *
     * @param なし
     */
	private String getTenpoListSQL()  throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append("    TENPO_CD ");
		sql.append("FROM ");
		sql.append("    R_TENPO ");
		sql.append("WHERE ");
		sql.append("    TENPO_KB = '1' ");
		sql.append("    AND HEITEN_DT >= " + batchDate );

		return sql.toString();
	}

    /**
     * <p>商品マスタ変更リスト想定件数取得処理</p>
     *
     * 指定条件のうち「期間」「対象DPT」を用いて、想定される商品マスタ変更リスト件数を取得する。
     *
     * @param なし
     */
	private String countSyohinMasterHenkoListSQL()  throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append("    COUNT(*) * 2 AS SEQ ");
		sql.append("FROM ");
		sql.append("    R_SYOHIN ");
		sql.append("WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("    BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("    YUKO_DT BETWEEN " + startDate + " AND " + endDate );

		return sql.toString();
	}

    /**
     * <p>商品マスタ変更リスト作成処理</p>
     *
     * 商品マスタ変更リストの
     *
     * @param なし
     */
	private String createSyohinMasterHenkoListSQL()  throws SQLException {
		StringBuffer sql = new StringBuffer();

		// #2799対応 2017.02.02 X.Liu Del  (S)
//		// No.136 MST01018 Add 2015.12.08 Nhat.NgoM (S)
//		sql.append("SELECT RTR.TAX_RT,BASE.*  ");
//		sql.append("FROM (  ");
//		// No.136 MST01018 Add 2015.12.08 Nhat.NgoM (E)
		// #2799対応 2017.02.02 X.Liu Del  (E)
		sql.append("SELECT ");
		// #2799対応 2017.02.02 X.Liu Add  (S)
		sql.append("    DISTINCT ");
		// #2799対応 2017.02.02 X.Liu Add  (E)
		sql.append("    REC_TYPE.GENERATION ");
		sql.append("   ,REC_TYPE.SYOHIN_CD ");
		sql.append("   ,SYOHIN_KB.BUNRUI1_CD_FOR_KAIPAGE ");
		sql.append("   ,SYOHIN_KB.KB ");
		sql.append("   ,REC_TYPE.YUKO_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PLU_SEND_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PLU_SEND_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PLU_SEND_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS PLU_SEND_DT ");
		// #2799対応 2017.02.03 X.Liu Add  (S)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PLU_HANEI_TIME  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PLU_HANEI_TIME  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PLU_HANEI_TIME  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS PLU_HANEI_TIME ");
		// #2799対応 2017.02.03 X.Liu Add  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BUNRUI1_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BUNRUI1_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BUNRUI1_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS BUNRUI1_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BUNRUI5_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BUNRUI5_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BUNRUI5_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS BUNRUI5_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.KIKAKU_KANJI_2_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.KIKAKU_KANJI_2_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.KIKAKU_KANJI_2_NA  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS KIKAKU_KANJI_2_NA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HINMEI_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HINMEI_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HINMEI_KANJI_NA  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HINMEI_KANJI_NA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.REC_HINMEI_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.REC_HINMEI_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.REC_HINMEI_KANJI_NA  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS REC_HINMEI_KANJI_NA ");
		// #2799対応 2017.02.02 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.HINMEI_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.HINMEI_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.HINMEI_KANA_NA  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS HINMEI_KANA_NA ");
		// #2799対応 2017.02.02 X.Liu Del  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.KIKAKU_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.KIKAKU_KANJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.KIKAKU_KANJI_NA  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS KIKAKU_KANJI_NA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BAITANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BAITANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BAITANKA_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS BAITANKA_VL ");
		// #2799対応 2017.02.02 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    AND RS_FOR_TYPE_BC.BAIKA_HAISHIN_FG = '1'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    AND RS_FOR_TYPE_E.BAIKA_HAISHIN_FG = '1'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    AND RS_FOR_TYPE_F.BAIKA_HAISHIN_FG = '1'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS BAIKA_HAISHIN_FG ");
		// #2799対応 2017.02.02 X.Liu Del  (E)
		// #2799 Add 2017.02.03 X.Liu (S)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.OROSI_BAITANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.OROSI_BAITANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.OROSI_BAITANKA_NUKI_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS OROSI_BAITANKA_NUKI_VL ");
		// #2799 Add 2017.02.03 X.Liu (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.GENTANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.GENTANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.GENTANKA_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS GENTANKA_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SIIRESAKI_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SIIRESAKI_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SIIRESAKI_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SIIRESAKI_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HACHUTANI_IRISU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HACHUTANI_IRISU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HACHUTANI_IRISU_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS HACHUTANI_IRISU_QT ");
		// #2799 Add 2017.02.03 X.Liu (S)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.MIN_HACHU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.MIN_HACHU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.MIN_HACHU_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS MIN_HACHU_QT ");
		// #2799 Add 2017.02.03 X.Liu (E)
		// #2799対応 2017.02.02 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    AND RS_FOR_TYPE_BC.EOS_KB = '2'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    AND RS_FOR_TYPE_BC.EOS_KB = '2'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    AND RS_FOR_TYPE_BC.EOS_KB = '2'  ");
//		sql.append("    THEN '★'  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS EOS_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.BIN_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.BIN_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.BIN_1_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS BIN_1_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.BIN_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.BIN_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.BIN_2_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS BIN_2_KB ");
		// #2799対応 2017.02.02 X.Liu Del  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.UPDATE_USER_ID  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.UPDATE_USER_ID  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.UPDATE_USER_ID  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS UPDATE_USER_ID ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.UPDATE_TS  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.UPDATE_TS  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.UPDATE_TS  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS UPDATE_TS ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYSTEM_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYSTEM_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYSTEM_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SYSTEM_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.GYOSYU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.GYOSYU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.GYOSYU_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS GYOSYU_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SYOHIN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BUNRUI2_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BUNRUI2_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BUNRUI2_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS BUNRUI2_CD ");
		// #2799 Add 2017.02.03 X.Liu (S)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SINKI_TOROKU_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SINKI_TOROKU_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SINKI_TOROKU_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SINKI_TOROKU_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOKAI_USER_ID  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOKAI_USER_ID  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOKAI_USER_ID  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SYOKAI_USER_ID ");
		// #2799 Add 2017.02.03 X.Liu (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_2_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_2_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_2_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SYOHIN_2_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.ZAIKO_SYOHIN_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.ZAIKO_SYOHIN_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.ZAIKO_SYOHIN_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS ZAIKO_SYOHIN_CD ");
		// #2799対応 2017.02.02 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.JYOHO_SYOHIN_CD  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.JYOHO_SYOHIN_CD  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.JYOHO_SYOHIN_CD  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS JYOHO_SYOHIN_CD ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.KIKAKU_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.KIKAKU_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.KIKAKU_KANA_NA  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS KIKAKU_KANA_NA ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.KIKAKU_KANA_2_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.KIKAKU_KANA_2_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.KIKAKU_KANA_2_NA  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS KIKAKU_KANA_2_NA ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.REC_HINMEI_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.REC_HINMEI_KANA_NA  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.REC_HINMEI_KANA_NA  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS REC_HINMEI_KANA_NA ");
		// #2799対応 2017.02.02 X.Liu Del  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_WIDTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_WIDTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_WIDTH_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHIN_WIDTH_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_HEIGHT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_HEIGHT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_HEIGHT_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHIN_HEIGHT_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_DEPTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_DEPTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_DEPTH_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHIN_DEPTH_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.ZEI_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS ZEI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.TAX_RATE_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS TAX_RATE_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.GENTANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.GENTANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.GENTANKA_NUKI_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS GENTANKA_NUKI_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BAITANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BAITANKA_NUKI_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BAITANKA_NUKI_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS BAITANKA_NUKI_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.MAKER_KIBO_KAKAKU_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.MAKER_KIBO_KAKAKU_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.MAKER_KIBO_KAKAKU_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS MAKER_KIBO_KAKAKU_VL ");
		// #2799対応 2017.02.06 X.Liu Add  (S)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PACK_WEIGHT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PACK_WEIGHT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PACK_WEIGHT_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS PACK_WEIGHT_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.COUNTRY_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.COUNTRY_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.COUNTRY_CD  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS COUNTRY_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_HOHO_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_HOHO_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_HOHO_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS HANBAI_HOHO_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.MAKER_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.MAKER_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.MAKER_CD  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS MAKER_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PACK_WIDTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PACK_WIDTH_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PACK_WIDTH_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS PACK_WIDTH_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SIIRE_ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SIIRE_ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SIIRE_ZEI_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SIIRE_ZEI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.OROSI_BAITANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.OROSI_BAITANKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.OROSI_BAITANKA_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS OROSI_BAITANKA_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.OROSI_BAITANKA_VL_KOURI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.OROSI_BAITANKA_VL_KOURI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.OROSI_BAITANKA_VL_KOURI  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS OROSI_BAITANKA_VL_KOURI ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SIIRE_TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SIIRE_TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SIIRE_TAX_RATE_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SIIRE_TAX_RATE_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.OROSI_TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.OROSI_TAX_RATE_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.OROSI_TAX_RATE_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS OROSI_TAX_RATE_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.OROSI_ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.OROSI_ZEI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.OROSI_ZEI_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS OROSI_ZEI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.MIN_ZAIKO_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.MIN_ZAIKO_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.MIN_ZAIKO_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS MIN_ZAIKO_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PER_TXT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PER_TXT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PER_TXT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS PER_TXT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.CENTER_KYOYO_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.CENTER_KYOYO_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.CENTER_KYOYO_DT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS CENTER_KYOYO_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.CENTER_KYOYO_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.CENTER_KYOYO_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.CENTER_KYOYO_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS CENTER_KYOYO_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHI_KIGEN_HYOJI_PATTER ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHIN_EIJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHIN_EIJI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHIN_EIJI_NA  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHIN_EIJI_NA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.LABEL_SEIBUN  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.LABEL_SEIBUN  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.LABEL_SEIBUN  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS LABEL_SEIBUN ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.LABEL_HOKAN_HOHO  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.LABEL_HOKAN_HOHO  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.LABEL_HOKAN_HOHO  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS LABEL_HOKAN_HOHO ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.LABEL_TUKAIKATA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.LABEL_TUKAIKATA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.LABEL_TUKAIKATA  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS LABEL_TUKAIKATA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.NENREI_SEIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.NENREI_SEIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.NENREI_SEIGEN_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS NENREI_SEIGEN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.NENREI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.NENREI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.NENREI  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS NENREI ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.KAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.KAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.KAN_KB  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS KAN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HOSHOUKIN  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HOSHOUKIN  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HOSHOUKIN  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS HOSHOUKIN ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SECURITY_TAG_FG  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SECURITY_TAG_FG  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SECURITY_TAG_FG  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SECURITY_TAG_FG ");
        sql.append("   ,CASE  ");
        sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
        sql.append("    THEN RS_FOR_TYPE_BC.HAMPER_SYOHIN_FG  ");
        sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
        sql.append("    THEN RS_FOR_TYPE_E.HAMPER_SYOHIN_FG  ");
        sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
        sql.append("    THEN RS_FOR_TYPE_F.HAMPER_SYOHIN_FG  ");
        sql.append("    ELSE NULL  ");
        sql.append("    END AS HAMPER_SYOHIN_FG ");
        // #2799対応 2017.02.06 X.Liu Add  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HACYU_SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HACYU_SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HACYU_SYOHIN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HACYU_SYOHIN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HACYU_SYOHIN_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HACYU_SYOHIN_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HACYU_SYOHIN_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HACYU_SYOHIN_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HACHU_TANI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HACHU_TANI_NA  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HACHU_TANI_NA  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HACHU_TANI_NA ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_TANI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_TANI  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_TANI  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HANBAI_TANI ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.MAX_HACHUTANI_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.MAX_HACHUTANI_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.MAX_HACHUTANI_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS MAX_HACHUTANI_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.CASE_HACHU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.CASE_HACHU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.CASE_HACHU_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS CASE_HACHU_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BARA_IRISU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BARA_IRISU_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BARA_IRISU_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS BARA_IRISU_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.TEN_HACHU_ST_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.TEN_HACHU_ST_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.TEN_HACHU_ST_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS TEN_HACHU_ST_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.TEN_HACHU_ED_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.TEN_HACHU_ED_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.TEN_HACHU_ED_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS TEN_HACHU_ED_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_ST_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_ST_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_ST_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HANBAI_ST_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_ED_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_ED_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_ED_DT  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HANBAI_ED_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_KIKAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_KIKAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_KIKAN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HANBAI_KIKAN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.TEIKAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.TEIKAN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.TEIKAN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS TEIKAN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SOBA_SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SOBA_SYOHIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SOBA_SYOHIN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SOBA_SYOHIN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.NOHIN_KIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.NOHIN_KIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.NOHIN_KIGEN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS NOHIN_KIGEN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.NOHIN_KIGEN_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.NOHIN_KIGEN_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.NOHIN_KIGEN_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS NOHIN_KIGEN_QT ");
		// #2799対応 2017.02.06 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.HACHU_PATTERN_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.HACHU_PATTERN_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.HACHU_PATTERN_1_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS HACHU_PATTERN_1_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.SIME_TIME_1_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.SIME_TIME_1_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.SIME_TIME_1_QT  ");
//		sql.append("    ELSE NULL  ");
//		sql.append("    END AS SIME_TIME_1_QT ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.C_NOHIN_RTIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.C_NOHIN_RTIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.C_NOHIN_RTIME_1_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS C_NOHIN_RTIME_1_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.TEN_NOHIN_RTIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.TEN_NOHIN_RTIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.TEN_NOHIN_RTIME_1_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS TEN_NOHIN_RTIME_1_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.TEN_NOHIN_TIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.TEN_NOHIN_TIME_1_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.TEN_NOHIN_TIME_1_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS TEN_NOHIN_TIME_1_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.HACHU_PATTERN_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.HACHU_PATTERN_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.HACHU_PATTERN_2_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS HACHU_PATTERN_2_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.SIME_TIME_2_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.SIME_TIME_2_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.SIME_TIME_2_QT  ");
//		sql.append("    ELSE NULL  ");
//		sql.append("    END AS SIME_TIME_2_QT ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.C_NOHIN_RTIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.C_NOHIN_RTIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.C_NOHIN_RTIME_2_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS C_NOHIN_RTIME_2_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.TEN_NOHIN_RTIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.TEN_NOHIN_RTIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.TEN_NOHIN_RTIME_2_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS TEN_NOHIN_RTIME_2_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.TEN_NOHIN_TIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.TEN_NOHIN_TIME_2_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.TEN_NOHIN_TIME_2_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS TEN_NOHIN_TIME_2_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.YUSEN_BIN_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.YUSEN_BIN_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.YUSEN_BIN_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS YUSEN_BIN_KB ");
		// #2799対応 2017.02.06 X.Liu Del  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.F_BIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.F_BIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.F_BIN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS F_BIN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.BUTURYU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.BUTURYU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.BUTURYU_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS BUTURYU_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.GOT_START_MM  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.GOT_START_MM  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.GOT_START_MM  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS GOT_START_MM ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.GOT_END_MM  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.GOT_END_MM  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.GOT_END_MM  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS GOT_END_MM ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HACHU_TEISI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HACHU_TEISI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HACHU_TEISI_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HACHU_TEISI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.CGC_HENPIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.CGC_HENPIN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.CGC_HENPIN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS CGC_HENPIN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_LIMIT_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_LIMIT_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_LIMIT_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS HANBAI_LIMIT_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.HANBAI_LIMIT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.HANBAI_LIMIT_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.HANBAI_LIMIT_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS HANBAI_LIMIT_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.KEYPLU_FG  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.KEYPLU_FG  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.KEYPLU_FG  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS KEYPLU_FG ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PLU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PLU_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PLU_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS PLU_KB ");
		// #2799対応 2017.02.06 X.Liu Del  (S)
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.SYUZEI_HOKOKU_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.SYUZEI_HOKOKU_KB  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.SYUZEI_HOKOKU_KB  ");
//		sql.append("    ELSE ''  ");
//		sql.append("    END AS SYUZEI_HOKOKU_KB ");
//		sql.append("   ,CASE  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
//		sql.append("    THEN RS_FOR_TYPE_BC.SAKE_NAIYORYO_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
//		sql.append("    THEN RS_FOR_TYPE_E.SAKE_NAIYORYO_QT  ");
//		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
//		sql.append("    THEN RS_FOR_TYPE_F.SAKE_NAIYORYO_QT  ");
//		sql.append("    ELSE NULL  ");
//		sql.append("    END AS SAKE_NAIYORYO_QT ");
		// #2799対応 2017.02.06 X.Liu Del  (E)
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.TAG_HYOJI_BAIKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.TAG_HYOJI_BAIKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.TAG_HYOJI_BAIKA_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS TAG_HYOJI_BAIKA_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.KESHI_BAIKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.KESHI_BAIKA_VL  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.KESHI_BAIKA_VL  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS KESHI_BAIKA_VL ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.YORIDORI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.YORIDORI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.YORIDORI_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS YORIDORI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.PC_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.PC_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.PC_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS PC_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.DAISI_NO_NB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.DAISI_NO_NB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.DAISI_NO_NB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS DAISI_NO_NB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.UNIT_PRICE_TANI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.UNIT_PRICE_TANI_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.UNIT_PRICE_TANI_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS UNIT_PRICE_TANI_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.UNIT_PRICE_NAIYORYO_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.UNIT_PRICE_NAIYORYO_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.UNIT_PRICE_NAIYORYO_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS UNIT_PRICE_NAIYORYO_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.UNIT_PRICE_KIJUN_TANI_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.UNIT_PRICE_KIJUN_TANI_QT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.UNIT_PRICE_KIJUN_TANI_QT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS UNIT_PRICE_KIJUN_TANI_QT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHI_KIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHI_KIGEN_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHI_KIGEN_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS SYOHI_KIGEN_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.SYOHI_KIGEN_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.SYOHI_KIGEN_DT  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.SYOHI_KIGEN_DT  ");
		sql.append("    ELSE NULL  ");
		sql.append("    END AS SYOHI_KIGEN_DT ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_1_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_1_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_1_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_1_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_2_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_2_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_2_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_2_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_3_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_3_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_3_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_3_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_4_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_4_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_4_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_4_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_5_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_5_KB  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_5_KB  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_5_KB ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.COMMENT_1_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.COMMENT_1_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.COMMENT_1_TX  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS COMMENT_1_TX ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.COMMENT_2_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.COMMENT_2_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.COMMENT_2_TX  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS COMMENT_2_TX ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.FREE_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.FREE_CD  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.FREE_CD  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS FREE_CD ");
		sql.append("   ,CASE  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE IN ('B', 'C')  ");
		sql.append("    THEN RS_FOR_TYPE_BC.COMMENT_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'E'  ");
		sql.append("    THEN RS_FOR_TYPE_E.COMMENT_TX  ");
		sql.append("    WHEN REC_TYPE.REC_TYPE = 'F'  ");
		sql.append("    THEN RS_FOR_TYPE_F.COMMENT_TX  ");
		sql.append("    ELSE ''  ");
		sql.append("    END AS COMMENT_TX  ");
		// #1097の対応 Add X.Liu 2017.02.03 (S)
		sql.append("   ,RTR.TAX_RT ");
		// #1097の対応 Add X.Liu 2017.02.03 (E)
		sql.append("FROM ");
		sql.append("(  ");
		// 商品別世代別６分類と、商品マスタからの値を抽出する
		sql.append("    SELECT ");
		sql.append("        CNT.GENERATION ");
		sql.append("       ,CNT.SYOHIN_CD ");
		sql.append("       ,CNT.YUKO_DT ");
		sql.append("       ,CASE  ");
		sql.append("        WHEN CNT_REC = 0  ");
		sql.append("        AND CNT_DELFG = 0  ");
		sql.append("        THEN 'A'  ");
		sql.append("        WHEN CNT_REC = 1  ");
		sql.append("        AND CNT_DELFG = 0  ");
		sql.append("        THEN 'B'  ");
		sql.append("        WHEN CNT_REC = 1  ");
		sql.append("        AND CNT_DELFG = 1  ");
		sql.append("        THEN 'C'  ");
		sql.append("        WHEN CNT_REC = 2  ");
		sql.append("        AND CNT_DELFG = 0  ");
		sql.append("        THEN 'D'  ");
		sql.append("        WHEN CNT_REC = 2  ");
		sql.append("        AND CNT_DELFG = 1  ");
		sql.append("        THEN 'E'  ");
		sql.append("        WHEN CNT_REC = 2  ");
		sql.append("        AND CNT_DELFG = 2  ");
		sql.append("        THEN 'F'  ");
		sql.append("        ELSE ''  ");
		sql.append("        END AS REC_TYPE  ");
		sql.append("    FROM ");
		sql.append("    (  ");
		sql.append("        SELECT ");
		sql.append("            GEN.GENERATION ");
		sql.append("           ,NVL(RS3.SYOHIN_CD, GEN.SYOHIN_CD) AS SYOHIN_CD ");
		sql.append("           ,RS3.YUKO_DT ");
		sql.append("           ,COUNT(RS3.SYOHIN_CD) AS CNT_REC ");
		sql.append("           ,SUM(CASE RS3.DELETE_FG WHEN '1' THEN 1 ELSE 0 END) AS CNT_DELFG  ");
		sql.append("        FROM ");
		sql.append("        (  ");
		// 直近世代(0)を特定する
		sql.append("            SELECT ");
		sql.append("                SYOHIN_CD ");
		sql.append("               ,MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("               ,'0' AS GENERATION  ");
		sql.append("            FROM ");
		sql.append("                R_SYOHIN  ");
		sql.append("            WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		if(outputJogaiFgShiire.equals("1")){
			// 仕入のみ商品を除外する
			sql.append("            AND SYOHIN_KB <> '3' ");
		}
		sql.append("            GROUP BY ");
		sql.append("                '0' ");
		sql.append("               ,SYOHIN_CD  ");
		sql.append("            UNION ");
		// １世代前(-1)を特定する
		sql.append("            SELECT ");
		sql.append("                NVL(RS.SYOHIN_CD, PRESENT_GEN.SYOHIN_CD) AS SYOHIN_CD ");
		sql.append("               ,MAX(RS.YUKO_DT) AS YUKO_DT ");
		sql.append("               ,'-1' AS GENERATION  ");
		sql.append("            FROM ");
		sql.append("            (  ");
		// 直近世代の特定(どの商品の1世代前を抽出するかを特定する)
		sql.append("                SELECT ");
		sql.append("                    SYOHIN_CD ");
		sql.append("                   ,MAX(YUKO_DT) AS YUKO_DT  ");
		sql.append("                FROM ");
		sql.append("                    R_SYOHIN  ");
		sql.append("                WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                    BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                    YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		if(outputJogaiFgShiire.equals("1")){
			// 仕入のみ商品を除外する
			sql.append("                AND SYOHIN_KB <> '3' ");
		}
		sql.append("                GROUP BY ");
		sql.append("                    SYOHIN_CD ");
		sql.append("            ) PRESENT_GEN  ");
		sql.append("            LEFT OUTER JOIN ");
		sql.append("                R_SYOHIN RS  ");
		sql.append("            ON ");
		sql.append("                RS.SYOHIN_CD = PRESENT_GEN.SYOHIN_CD AND ");
		sql.append("                RS.YUKO_DT < PRESENT_GEN.YUKO_DT  ");
		sql.append("            GROUP BY ");
		sql.append("                '-1' ");
		sql.append("               ,NVL(RS.SYOHIN_CD, PRESENT_GEN.SYOHIN_CD) ");
		sql.append("        ) GEN  ");
		sql.append("        LEFT OUTER JOIN ");
		sql.append("            R_SYOHIN RS3  ");
		sql.append("        ON ");
		sql.append("            RS3.SYOHIN_CD = GEN.SYOHIN_CD AND ");
		sql.append("            RS3.YUKO_DT = GEN.YUKO_DT  ");
		sql.append("        GROUP BY ");
		sql.append("            GEN.GENERATION ");
		sql.append("           ,NVL(RS3.SYOHIN_CD, GEN.SYOHIN_CD) ");
		sql.append("           ,RS3.YUKO_DT ");
		sql.append("        ) CNT ");
		sql.append("    ) REC_TYPE  ");
		sql.append("LEFT OUTER JOIN ");
		// #2799 Add 2017.02.03 X.Liu (S)
		sql.append("    ( ");
		sql.append("    SELECT ");
		sql.append("        R_S_BC.SYOHIN_CD ");
		sql.append("       ,R_S_BC.YUKO_DT ");
		sql.append("       ,R_S_BC.BUNRUI1_CD  AS BUNRUI1_CD ");
		sql.append("       ,R_S_BC.BUNRUI5_CD  AS BUNRUI5_CD ");
		sql.append("       ,R_S_BC.KIKAKU_KANJI_2_NA  AS KIKAKU_KANJI_2_NA ");
		sql.append("       ,R_S_BC.HINMEI_KANJI_NA  AS HINMEI_KANJI_NA ");
		sql.append("       ,R_S_BC.REC_HINMEI_KANJI_NA  AS REC_HINMEI_KANJI_NA ");
		sql.append("       ,R_S_BC.HINMEI_KANA_NA  AS HINMEI_KANA_NA ");
		sql.append("       ,R_S_BC.KIKAKU_KANJI_NA  AS KIKAKU_KANJI_NA ");
		sql.append("       ,R_S_BC.BAITANKA_VL  AS BAITANKA_VL ");
		sql.append("       ,R_S_BC.BAIKA_HAISHIN_FG  AS BAIKA_HAISHIN_FG ");
		sql.append("       ,R_S_BC.GENTANKA_VL  AS GENTANKA_VL ");
		sql.append("       ,R_S_BC.SIIRESAKI_CD  AS SIIRESAKI_CD ");
		sql.append("       ,R_S_BC.HACHUTANI_IRISU_QT  AS HACHUTANI_IRISU_QT ");
		sql.append("       ,R_S_BC.EOS_KB  AS EOS_KB ");
		sql.append("       ,R_S_BC.BIN_1_KB  AS BIN_1_KB ");
		sql.append("       ,R_S_BC.BIN_2_KB  AS BIN_2_KB ");
		sql.append("       ,R_S_BC.UPDATE_USER_ID  AS UPDATE_USER_ID ");
		sql.append("       ,R_S_BC.UPDATE_TS  AS UPDATE_TS ");
		sql.append("       ,R_S_BC.SYSTEM_KB  AS SYSTEM_KB ");
		sql.append("       ,R_S_BC.GYOSYU_KB  AS GYOSYU_KB ");
		sql.append("       ,R_S_BC.SYOHIN_KB  AS SYOHIN_KB ");
		sql.append("       ,R_S_BC.BUNRUI2_CD  AS BUNRUI2_CD ");
		sql.append("       ,R_S_BC.SYOHIN_2_CD  AS SYOHIN_2_CD ");
		sql.append("       ,R_S_BC.ZAIKO_SYOHIN_CD  AS ZAIKO_SYOHIN_CD ");
		sql.append("       ,R_S_BC.JYOHO_SYOHIN_CD  AS JYOHO_SYOHIN_CD ");
		sql.append("       ,R_S_BC.KIKAKU_KANA_NA  AS KIKAKU_KANA_NA ");
		sql.append("       ,R_S_BC.KIKAKU_KANA_2_NA  AS KIKAKU_KANA_2_NA ");
		sql.append("       ,R_S_BC.REC_HINMEI_KANA_NA  AS REC_HINMEI_KANA_NA ");
		sql.append("       ,R_S_BC.SYOHIN_WIDTH_QT  AS SYOHIN_WIDTH_QT ");
		sql.append("       ,R_S_BC.SYOHIN_HEIGHT_QT  AS SYOHIN_HEIGHT_QT ");
		sql.append("       ,R_S_BC.SYOHIN_DEPTH_QT  AS SYOHIN_DEPTH_QT ");
		sql.append("       ,R_S_BC.ZEI_KB  AS ZEI_KB ");
		sql.append("       ,R_S_BC.TAX_RATE_KB  AS TAX_RATE_KB ");
		sql.append("       ,R_S_BC.GENTANKA_NUKI_VL  AS GENTANKA_NUKI_VL ");
		sql.append("       ,R_S_BC.BAITANKA_NUKI_VL  AS BAITANKA_NUKI_VL ");
		sql.append("       ,R_S_BC.MAKER_KIBO_KAKAKU_VL  AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("       ,R_S_BC.HACYU_SYOHIN_KB  AS HACYU_SYOHIN_KB ");
		sql.append("       ,R_S_BC.HACYU_SYOHIN_CD  AS HACYU_SYOHIN_CD ");
		sql.append("       ,R_S_BC.HACHU_TANI_NA  AS HACHU_TANI_NA ");
		sql.append("       ,R_S_BC.HANBAI_TANI  AS HANBAI_TANI ");
		sql.append("       ,R_S_BC.MAX_HACHUTANI_QT  AS MAX_HACHUTANI_QT ");
		sql.append("       ,R_S_BC.CASE_HACHU_KB  AS CASE_HACHU_KB ");
		sql.append("       ,R_S_BC.BARA_IRISU_QT  AS BARA_IRISU_QT ");
		sql.append("       ,R_S_BC.TEN_HACHU_ST_DT  AS TEN_HACHU_ST_DT ");
		sql.append("       ,R_S_BC.TEN_HACHU_ED_DT  AS TEN_HACHU_ED_DT ");
		sql.append("       ,R_S_BC.HANBAI_ST_DT  AS HANBAI_ST_DT ");
		sql.append("       ,R_S_BC.HANBAI_ED_DT  AS HANBAI_ED_DT ");
		sql.append("       ,R_S_BC.HANBAI_KIKAN_KB  AS HANBAI_KIKAN_KB ");
		sql.append("       ,R_S_BC.TEIKAN_KB  AS TEIKAN_KB ");
		sql.append("       ,R_S_BC.SOBA_SYOHIN_KB  AS SOBA_SYOHIN_KB ");
		sql.append("       ,R_S_BC.NOHIN_KIGEN_KB  AS NOHIN_KIGEN_KB ");
		sql.append("       ,R_S_BC.NOHIN_KIGEN_QT  AS NOHIN_KIGEN_QT ");
		sql.append("       ,R_S_BC.HACHU_PATTERN_1_KB  AS HACHU_PATTERN_1_KB ");
		sql.append("       ,R_S_BC.SIME_TIME_1_QT  AS SIME_TIME_1_QT ");
		sql.append("       ,R_S_BC.C_NOHIN_RTIME_1_KB  AS C_NOHIN_RTIME_1_KB ");
		sql.append("       ,R_S_BC.TEN_NOHIN_RTIME_1_KB  AS TEN_NOHIN_RTIME_1_KB ");
		sql.append("       ,R_S_BC.TEN_NOHIN_TIME_1_KB  AS TEN_NOHIN_TIME_1_KB ");
		sql.append("       ,R_S_BC.HACHU_PATTERN_2_KB  AS HACHU_PATTERN_2_KB ");
		sql.append("       ,R_S_BC.SIME_TIME_2_QT  AS SIME_TIME_2_QT ");
		sql.append("       ,R_S_BC.C_NOHIN_RTIME_2_KB  AS C_NOHIN_RTIME_2_KB ");
		sql.append("       ,R_S_BC.TEN_NOHIN_RTIME_2_KB  AS TEN_NOHIN_RTIME_2_KB ");
		sql.append("       ,R_S_BC.TEN_NOHIN_TIME_2_KB  AS TEN_NOHIN_TIME_2_KB ");
		sql.append("       ,R_S_BC.YUSEN_BIN_KB  AS YUSEN_BIN_KB ");
		sql.append("       ,R_S_BC.F_BIN_KB  AS F_BIN_KB ");
		sql.append("       ,R_S_BC.BUTURYU_KB  AS BUTURYU_KB ");
		sql.append("       ,R_S_BC.GOT_START_MM  AS GOT_START_MM ");
		sql.append("       ,R_S_BC.GOT_END_MM  AS GOT_END_MM ");
		sql.append("       ,R_S_BC.HACHU_TEISI_KB  AS HACHU_TEISI_KB ");
		sql.append("       ,R_S_BC.CGC_HENPIN_KB  AS CGC_HENPIN_KB ");
		sql.append("       ,R_S_BC.HANBAI_LIMIT_KB  AS HANBAI_LIMIT_KB ");
		sql.append("       ,R_S_BC.HANBAI_LIMIT_QT  AS HANBAI_LIMIT_QT ");
		sql.append("       ,R_S_BC.PLU_SEND_DT  AS PLU_SEND_DT ");
		sql.append("       ,R_S_BC.KEYPLU_FG  AS KEYPLU_FG ");
		sql.append("       ,R_S_BC.PLU_KB  AS PLU_KB ");
		sql.append("       ,R_S_BC.SYUZEI_HOKOKU_KB  AS SYUZEI_HOKOKU_KB ");
		sql.append("       ,R_S_BC.SAKE_NAIYORYO_QT  AS SAKE_NAIYORYO_QT ");
		sql.append("       ,R_S_BC.TAG_HYOJI_BAIKA_VL  AS TAG_HYOJI_BAIKA_VL ");
		sql.append("       ,R_S_BC.KESHI_BAIKA_VL  AS KESHI_BAIKA_VL ");
		sql.append("       ,R_S_BC.YORIDORI_KB  AS YORIDORI_KB ");
		sql.append("       ,R_S_BC.PC_KB  AS PC_KB ");
		sql.append("       ,R_S_BC.DAISI_NO_NB  AS DAISI_NO_NB ");
		sql.append("       ,R_S_BC.UNIT_PRICE_TANI_KB  AS UNIT_PRICE_TANI_KB ");
		sql.append("       ,R_S_BC.UNIT_PRICE_NAIYORYO_QT  AS UNIT_PRICE_NAIYORYO_QT ");
		sql.append("       ,R_S_BC.UNIT_PRICE_KIJUN_TANI_QT  AS UNIT_PRICE_KIJUN_TANI_QT ");
		sql.append("       ,R_S_BC.SYOHI_KIGEN_KB  AS SYOHI_KIGEN_KB ");
		sql.append("       ,R_S_BC.SYOHI_KIGEN_DT  AS SYOHI_KIGEN_DT ");
		sql.append("       ,R_S_BC.FREE_1_KB  AS FREE_1_KB ");
		sql.append("       ,R_S_BC.FREE_2_KB  AS FREE_2_KB ");
		sql.append("       ,R_S_BC.FREE_3_KB  AS FREE_3_KB ");
		sql.append("       ,R_S_BC.FREE_4_KB  AS FREE_4_KB ");
		sql.append("       ,R_S_BC.FREE_5_KB  AS FREE_5_KB ");
		sql.append("       ,R_S_BC.COMMENT_1_TX  AS COMMENT_1_TX ");
		sql.append("       ,R_S_BC.COMMENT_2_TX  AS COMMENT_2_TX ");
		sql.append("       ,R_S_BC.FREE_CD  AS FREE_CD ");
		sql.append("       ,R_S_BC.COMMENT_TX  AS COMMENT_TX  ");
		sql.append("       ,R_S_BC.NENREI_SEIGEN_KB  AS NENREI_SEIGEN_KB  ");
		sql.append("       ,R_S_BC.NENREI  AS NENREI  ");
		sql.append("       ,R_S_BC.KAN_KB  AS KAN_KB  ");
		sql.append("       ,R_S_BC.HOSHOUKIN  AS HOSHOUKIN  ");
		sql.append("       ,R_S_BC.MIN_HACHU_QT  AS MIN_HACHU_QT  ");
		sql.append("       ,R_S_BC.PER_TXT  AS PER_TXT  ");
		sql.append("       ,R_S_BC.SIIRE_ZEI_KB  AS SIIRE_ZEI_KB  ");
		sql.append("       ,R_S_BC.OROSI_ZEI_KB  AS OROSI_ZEI_KB  ");
		sql.append("       ,R_S_BC.OROSI_BAITANKA_VL  AS OROSI_BAITANKA_VL  ");
		sql.append("       ,R_S_BC.OROSI_BAITANKA_NUKI_VL  AS OROSI_BAITANKA_NUKI_VL  ");
		sql.append("       ,R_S_BC.SYOHI_KIGEN_HYOJI_PATTER  AS SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("       ,R_S_BC.PLU_HANEI_TIME  AS PLU_HANEI_TIME  ");
		sql.append("       ,R_S_BC.OROSI_BAITANKA_VL_KOURI  AS OROSI_BAITANKA_VL_KOURI  ");
		sql.append("       ,R_S_BC.SYOKAI_USER_ID  AS SYOKAI_USER_ID  ");
		sql.append("       ,R_S_BC.PACK_WEIGHT_QT  AS PACK_WEIGHT_QT  ");
		sql.append("       ,R_S_BC.SINKI_TOROKU_DT  AS SINKI_TOROKU_DT  ");
		sql.append("       ,R_S_BC.PACK_WIDTH_QT  AS PACK_WIDTH_QT  ");
		sql.append("       ,R_S_BC.SIIRE_TAX_RATE_KB  AS SIIRE_TAX_RATE_KB  ");
		sql.append("       ,R_S_BC.OROSI_TAX_RATE_KB  AS OROSI_TAX_RATE_KB  ");
		sql.append("       ,R_S_BC.CENTER_KYOYO_DT  AS CENTER_KYOYO_DT  ");
        sql.append("       ,R_S_BC.CENTER_KYOYO_KB  AS CENTER_KYOYO_KB  ");
		sql.append("       ,R_S_BC.DELETE_FG  AS DELETE_FG  ");
		sql.append("       ,ASN_BC.SYOHIN_EIJI_NA  AS SYOHIN_EIJI_NA  ");
		sql.append("       ,ASN_BC.COUNTRY_CD  AS COUNTRY_CD  ");
		sql.append("       ,ASN_BC.MAKER_CD  AS MAKER_CD  ");
		sql.append("       ,ASN_BC.MIN_ZAIKO_QT  AS MIN_ZAIKO_QT  ");
		sql.append("       ,ASN_BC.SECURITY_TAG_FG  AS SECURITY_TAG_FG  ");
		sql.append("       ,ASN_BC.HAMPER_SYOHIN_FG  AS HAMPER_SYOHIN_FG  ");
		sql.append("       ,ASN_BC.HANBAI_HOHO_KB  AS HANBAI_HOHO_KB  ");
		sql.append("       ,ASN_BC.LABEL_SEIBUN  AS LABEL_SEIBUN  ");
		sql.append("       ,ASN_BC.LABEL_HOKAN_HOHO  AS LABEL_HOKAN_HOHO  ");
		sql.append("       ,ASN_BC.LABEL_TUKAIKATA  AS LABEL_TUKAIKATA  ");
		sql.append("    FROM ");
		sql.append("        R_SYOHIN R_S_BC  ");
		sql.append("    LEFT OUTER JOIN ");
		sql.append("        R_SYOHIN_ASN ASN_BC  ");
		sql.append("    ON ");
		sql.append("        R_S_BC.SYOHIN_CD = ASN_BC.SYOHIN_CD  ");
		sql.append("    AND ");
		sql.append("        R_S_BC.YUKO_DT = ASN_BC.YUKO_DT ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("    AND  ");
//		sql.append("       R_S_BC.BUNRUI1_CD = ASN_BC.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("    ) ");
		// #2799 Add 2017.01.31 X.Liu (E)
		// #2799 Mod 2017.01.31 X.Liu (S)
		//sql.append("    R_SYOHIN RS_FOR_TYPE_BC  ");
		sql.append("    RS_FOR_TYPE_BC  ");
		// #2799 Add 2017.01.31 X.Liu (E)
		sql.append("ON ");
		sql.append("    REC_TYPE.SYOHIN_CD = RS_FOR_TYPE_BC.SYOHIN_CD AND ");
		sql.append("    REC_TYPE.YUKO_DT = RS_FOR_TYPE_BC.YUKO_DT  ");
		sql.append("LEFT OUTER JOIN ");
		// #2799 Add 2017.01.31 X.Liu (S)
		sql.append("    ( ");
		sql.append("    SELECT ");
		sql.append("        R_S_E.SYOHIN_CD ");
		sql.append("       ,R_S_E.YUKO_DT ");
		sql.append("       ,R_S_E.BUNRUI1_CD  AS BUNRUI1_CD ");
		sql.append("       ,R_S_E.BUNRUI5_CD  AS BUNRUI5_CD ");
		sql.append("       ,R_S_E.KIKAKU_KANJI_2_NA  AS KIKAKU_KANJI_2_NA ");
		sql.append("       ,R_S_E.HINMEI_KANJI_NA  AS HINMEI_KANJI_NA ");
		sql.append("       ,R_S_E.REC_HINMEI_KANJI_NA  AS REC_HINMEI_KANJI_NA ");
		sql.append("       ,R_S_E.HINMEI_KANA_NA  AS HINMEI_KANA_NA ");
		sql.append("       ,R_S_E.KIKAKU_KANJI_NA  AS KIKAKU_KANJI_NA ");
		sql.append("       ,R_S_E.BAITANKA_VL  AS BAITANKA_VL ");
		sql.append("       ,R_S_E.BAIKA_HAISHIN_FG  AS BAIKA_HAISHIN_FG ");
		sql.append("       ,R_S_E.GENTANKA_VL  AS GENTANKA_VL ");
		sql.append("       ,R_S_E.SIIRESAKI_CD  AS SIIRESAKI_CD ");
		sql.append("       ,R_S_E.HACHUTANI_IRISU_QT  AS HACHUTANI_IRISU_QT ");
		sql.append("       ,R_S_E.EOS_KB  AS EOS_KB ");
		sql.append("       ,R_S_E.BIN_1_KB  AS BIN_1_KB ");
		sql.append("       ,R_S_E.BIN_2_KB  AS BIN_2_KB ");
		sql.append("       ,R_S_E.UPDATE_USER_ID  AS UPDATE_USER_ID ");
		sql.append("       ,R_S_E.UPDATE_TS  AS UPDATE_TS ");
		sql.append("       ,R_S_E.SYSTEM_KB  AS SYSTEM_KB ");
		sql.append("       ,R_S_E.GYOSYU_KB  AS GYOSYU_KB ");
		sql.append("       ,R_S_E.SYOHIN_KB  AS SYOHIN_KB ");
		sql.append("       ,R_S_E.BUNRUI2_CD  AS BUNRUI2_CD ");
		sql.append("       ,R_S_E.SYOHIN_2_CD  AS SYOHIN_2_CD ");
		sql.append("       ,R_S_E.ZAIKO_SYOHIN_CD  AS ZAIKO_SYOHIN_CD ");
		sql.append("       ,R_S_E.JYOHO_SYOHIN_CD  AS JYOHO_SYOHIN_CD ");
		sql.append("       ,R_S_E.KIKAKU_KANA_NA  AS KIKAKU_KANA_NA ");
		sql.append("       ,R_S_E.KIKAKU_KANA_2_NA  AS KIKAKU_KANA_2_NA ");
		sql.append("       ,R_S_E.REC_HINMEI_KANA_NA  AS REC_HINMEI_KANA_NA ");
		sql.append("       ,R_S_E.SYOHIN_WIDTH_QT  AS SYOHIN_WIDTH_QT ");
		sql.append("       ,R_S_E.SYOHIN_HEIGHT_QT  AS SYOHIN_HEIGHT_QT ");
		sql.append("       ,R_S_E.SYOHIN_DEPTH_QT  AS SYOHIN_DEPTH_QT ");
		sql.append("       ,R_S_E.ZEI_KB  AS ZEI_KB ");
		sql.append("       ,R_S_E.TAX_RATE_KB  AS TAX_RATE_KB ");
		sql.append("       ,R_S_E.GENTANKA_NUKI_VL  AS GENTANKA_NUKI_VL ");
		sql.append("       ,R_S_E.BAITANKA_NUKI_VL  AS BAITANKA_NUKI_VL ");
		sql.append("       ,R_S_E.MAKER_KIBO_KAKAKU_VL  AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("       ,R_S_E.HACYU_SYOHIN_KB  AS HACYU_SYOHIN_KB ");
		sql.append("       ,R_S_E.HACYU_SYOHIN_CD  AS HACYU_SYOHIN_CD ");
		sql.append("       ,R_S_E.HACHU_TANI_NA  AS HACHU_TANI_NA ");
		sql.append("       ,R_S_E.HANBAI_TANI  AS HANBAI_TANI ");
		sql.append("       ,R_S_E.MAX_HACHUTANI_QT  AS MAX_HACHUTANI_QT ");
		sql.append("       ,R_S_E.CASE_HACHU_KB  AS CASE_HACHU_KB ");
		sql.append("       ,R_S_E.BARA_IRISU_QT  AS BARA_IRISU_QT ");
		sql.append("       ,R_S_E.TEN_HACHU_ST_DT  AS TEN_HACHU_ST_DT ");
		sql.append("       ,R_S_E.TEN_HACHU_ED_DT  AS TEN_HACHU_ED_DT ");
		sql.append("       ,R_S_E.HANBAI_ST_DT  AS HANBAI_ST_DT ");
		sql.append("       ,R_S_E.HANBAI_ED_DT  AS HANBAI_ED_DT ");
		sql.append("       ,R_S_E.HANBAI_KIKAN_KB  AS HANBAI_KIKAN_KB ");
		sql.append("       ,R_S_E.TEIKAN_KB  AS TEIKAN_KB ");
		sql.append("       ,R_S_E.SOBA_SYOHIN_KB  AS SOBA_SYOHIN_KB ");
		sql.append("       ,R_S_E.NOHIN_KIGEN_KB  AS NOHIN_KIGEN_KB ");
		sql.append("       ,R_S_E.NOHIN_KIGEN_QT  AS NOHIN_KIGEN_QT ");
		sql.append("       ,R_S_E.HACHU_PATTERN_1_KB  AS HACHU_PATTERN_1_KB ");
		sql.append("       ,R_S_E.SIME_TIME_1_QT  AS SIME_TIME_1_QT ");
		sql.append("       ,R_S_E.C_NOHIN_RTIME_1_KB  AS C_NOHIN_RTIME_1_KB ");
		sql.append("       ,R_S_E.TEN_NOHIN_RTIME_1_KB  AS TEN_NOHIN_RTIME_1_KB ");
		sql.append("       ,R_S_E.TEN_NOHIN_TIME_1_KB  AS TEN_NOHIN_TIME_1_KB ");
		sql.append("       ,R_S_E.HACHU_PATTERN_2_KB  AS HACHU_PATTERN_2_KB ");
		sql.append("       ,R_S_E.SIME_TIME_2_QT  AS SIME_TIME_2_QT ");
		sql.append("       ,R_S_E.C_NOHIN_RTIME_2_KB  AS C_NOHIN_RTIME_2_KB ");
		sql.append("       ,R_S_E.TEN_NOHIN_RTIME_2_KB  AS TEN_NOHIN_RTIME_2_KB ");
		sql.append("       ,R_S_E.TEN_NOHIN_TIME_2_KB  AS TEN_NOHIN_TIME_2_KB ");
		sql.append("       ,R_S_E.YUSEN_BIN_KB  AS YUSEN_BIN_KB ");
		sql.append("       ,R_S_E.F_BIN_KB  AS F_BIN_KB ");
		sql.append("       ,R_S_E.BUTURYU_KB  AS BUTURYU_KB ");
		sql.append("       ,R_S_E.GOT_START_MM  AS GOT_START_MM ");
		sql.append("       ,R_S_E.GOT_END_MM  AS GOT_END_MM ");
		sql.append("       ,R_S_E.HACHU_TEISI_KB  AS HACHU_TEISI_KB ");
		sql.append("       ,R_S_E.CGC_HENPIN_KB  AS CGC_HENPIN_KB ");
		sql.append("       ,R_S_E.HANBAI_LIMIT_KB  AS HANBAI_LIMIT_KB ");
		sql.append("       ,R_S_E.HANBAI_LIMIT_QT  AS HANBAI_LIMIT_QT ");
		sql.append("       ,R_S_E.PLU_SEND_DT  AS PLU_SEND_DT ");
		sql.append("       ,R_S_E.KEYPLU_FG  AS KEYPLU_FG ");
		sql.append("       ,R_S_E.PLU_KB  AS PLU_KB ");
		sql.append("       ,R_S_E.SYUZEI_HOKOKU_KB  AS SYUZEI_HOKOKU_KB ");
		sql.append("       ,R_S_E.SAKE_NAIYORYO_QT  AS SAKE_NAIYORYO_QT ");
		sql.append("       ,R_S_E.TAG_HYOJI_BAIKA_VL  AS TAG_HYOJI_BAIKA_VL ");
		sql.append("       ,R_S_E.KESHI_BAIKA_VL  AS KESHI_BAIKA_VL ");
		sql.append("       ,R_S_E.YORIDORI_KB  AS YORIDORI_KB ");
		sql.append("       ,R_S_E.PC_KB  AS PC_KB ");
		sql.append("       ,R_S_E.DAISI_NO_NB  AS DAISI_NO_NB ");
		sql.append("       ,R_S_E.UNIT_PRICE_TANI_KB  AS UNIT_PRICE_TANI_KB ");
		sql.append("       ,R_S_E.UNIT_PRICE_NAIYORYO_QT  AS UNIT_PRICE_NAIYORYO_QT ");
		sql.append("       ,R_S_E.UNIT_PRICE_KIJUN_TANI_QT  AS UNIT_PRICE_KIJUN_TANI_QT ");
		sql.append("       ,R_S_E.SYOHI_KIGEN_KB  AS SYOHI_KIGEN_KB ");
		sql.append("       ,R_S_E.SYOHI_KIGEN_DT  AS SYOHI_KIGEN_DT ");
		sql.append("       ,R_S_E.FREE_1_KB  AS FREE_1_KB ");
		sql.append("       ,R_S_E.FREE_2_KB  AS FREE_2_KB ");
		sql.append("       ,R_S_E.FREE_3_KB  AS FREE_3_KB ");
		sql.append("       ,R_S_E.FREE_4_KB  AS FREE_4_KB ");
		sql.append("       ,R_S_E.FREE_5_KB  AS FREE_5_KB ");
		sql.append("       ,R_S_E.COMMENT_1_TX  AS COMMENT_1_TX ");
		sql.append("       ,R_S_E.COMMENT_2_TX  AS COMMENT_2_TX ");
		sql.append("       ,R_S_E.FREE_CD  AS FREE_CD ");
		sql.append("       ,R_S_E.COMMENT_TX  AS COMMENT_TX  ");
		sql.append("       ,R_S_E.NENREI_SEIGEN_KB  AS NENREI_SEIGEN_KB  ");
		sql.append("       ,R_S_E.NENREI  AS NENREI  ");
		sql.append("       ,R_S_E.KAN_KB  AS KAN_KB  ");
		sql.append("       ,R_S_E.HOSHOUKIN  AS HOSHOUKIN  ");
		sql.append("       ,R_S_E.MIN_HACHU_QT  AS MIN_HACHU_QT  ");
		sql.append("       ,R_S_E.PER_TXT  AS PER_TXT  ");
		sql.append("       ,R_S_E.SIIRE_ZEI_KB  AS SIIRE_ZEI_KB  ");
		sql.append("       ,R_S_E.OROSI_ZEI_KB  AS OROSI_ZEI_KB  ");
		sql.append("       ,R_S_E.OROSI_BAITANKA_VL  AS OROSI_BAITANKA_VL  ");
		sql.append("       ,R_S_E.OROSI_BAITANKA_NUKI_VL  AS OROSI_BAITANKA_NUKI_VL  ");
		sql.append("       ,R_S_E.SYOHI_KIGEN_HYOJI_PATTER  AS SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("       ,R_S_E.PLU_HANEI_TIME  AS PLU_HANEI_TIME  ");
		sql.append("       ,R_S_E.OROSI_BAITANKA_VL_KOURI  AS OROSI_BAITANKA_VL_KOURI  ");
		sql.append("       ,R_S_E.SYOKAI_USER_ID  AS SYOKAI_USER_ID  ");
		sql.append("       ,R_S_E.PACK_WEIGHT_QT  AS PACK_WEIGHT_QT  ");
		sql.append("       ,R_S_E.DELETE_FG  AS DELETE_FG  ");
		sql.append("       ,R_S_E.SINKI_TOROKU_DT  AS SINKI_TOROKU_DT  ");
		sql.append("       ,R_S_E.PACK_WIDTH_QT  AS PACK_WIDTH_QT  ");
		sql.append("       ,R_S_E.SIIRE_TAX_RATE_KB  AS SIIRE_TAX_RATE_KB  ");
		sql.append("       ,R_S_E.OROSI_TAX_RATE_KB  AS OROSI_TAX_RATE_KB  ");
		sql.append("       ,R_S_E.CENTER_KYOYO_DT  AS CENTER_KYOYO_DT  ");
		sql.append("       ,R_S_E.CENTER_KYOYO_KB  AS CENTER_KYOYO_KB  ");
		sql.append("       ,ASN_E.SYOHIN_EIJI_NA  AS SYOHIN_EIJI_NA  ");
		sql.append("       ,ASN_E.COUNTRY_CD  AS COUNTRY_CD  ");
		sql.append("       ,ASN_E.MAKER_CD  AS MAKER_CD  ");
		sql.append("       ,ASN_E.MIN_ZAIKO_QT  AS MIN_ZAIKO_QT  ");
		sql.append("       ,ASN_E.SECURITY_TAG_FG  AS SECURITY_TAG_FG  ");
		sql.append("       ,ASN_E.HAMPER_SYOHIN_FG  AS HAMPER_SYOHIN_FG  ");
		sql.append("       ,ASN_E.HANBAI_HOHO_KB  AS HANBAI_HOHO_KB  ");
		sql.append("       ,ASN_E.LABEL_SEIBUN  AS LABEL_SEIBUN  ");
		sql.append("       ,ASN_E.LABEL_HOKAN_HOHO  AS LABEL_HOKAN_HOHO  ");
		sql.append("       ,ASN_E.LABEL_TUKAIKATA  AS LABEL_TUKAIKATA  ");
		sql.append("    FROM ");
		sql.append("        R_SYOHIN R_S_E  ");
		sql.append("    LEFT OUTER JOIN ");
		sql.append("        R_SYOHIN_ASN ASN_E  ");
		sql.append("    ON ");
		sql.append("        R_S_E.SYOHIN_CD = ASN_E.SYOHIN_CD  ");
		sql.append("    AND ");
		sql.append("        R_S_E.YUKO_DT = ASN_E.YUKO_DT ");
		// #6620 DEL 2022.11.18 VU.TD (S)
//		sql.append("    AND ");
//		sql.append("       R_S_E.BUNRUI1_CD = ASN_E.BUNRUI1_CD ");
		// #6620 DEL 2022.11.18 VU.TD (E)
		sql.append("    ) ");
		// #2799 Add 2017.01.31 X.Liu (E)
		// #2799 Mod 2017.01.31 X.Liu (S)
		//sb.append("    R_SYOHIN RS_FOR_TYPE_E  ");
		sql.append("    RS_FOR_TYPE_E  ");
		// #2799 Mod 2017.01.31 X.Liu (E)
		sql.append("ON ");
		sql.append("    REC_TYPE.SYOHIN_CD = RS_FOR_TYPE_E.SYOHIN_CD AND ");
		sql.append("    REC_TYPE.YUKO_DT = RS_FOR_TYPE_E.YUKO_DT AND ");
		sql.append("    RS_FOR_TYPE_E.DELETE_FG = '0'  ");
		sql.append("LEFT OUTER JOIN ");
		sql.append("(  ");
		// 分類Fは祝対象レコードを特定できないため、便宜上各カラムのMAX値を使用する
		// #2799 Mod 2017.01.31 X.Liu (S)
//		sql.append("    SELECT ");
//		sql.append("        SYOHIN_CD ");
//		sql.append("       ,YUKO_DT ");
//		sql.append("       ,MAX(BUNRUI1_CD) AS BUNRUI1_CD ");
//		sql.append("       ,MAX(BUNRUI5_CD) AS BUNRUI5_CD ");
//		sql.append("       ,MAX(KIKAKU_KANJI_2_NA) AS KIKAKU_KANJI_2_NA ");
//		sql.append("       ,MAX(HINMEI_KANJI_NA) AS HINMEI_KANJI_NA ");
//		sql.append("       ,MAX(REC_HINMEI_KANJI_NA) AS REC_HINMEI_KANJI_NA ");
//		sql.append("       ,MAX(HINMEI_KANA_NA) AS HINMEI_KANA_NA ");
//		sql.append("       ,MAX(KIKAKU_KANJI_NA) AS KIKAKU_KANJI_NA ");
//		sql.append("       ,MAX(BAITANKA_VL) AS BAITANKA_VL ");
//		sql.append("       ,MAX(BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
//		sql.append("       ,MAX(GENTANKA_VL) AS GENTANKA_VL ");
//		sql.append("       ,MAX(SIIRESAKI_CD) AS SIIRESAKI_CD ");
//		sql.append("       ,MAX(HACHUTANI_IRISU_QT) AS HACHUTANI_IRISU_QT ");
//		sql.append("       ,MAX(EOS_KB) AS EOS_KB ");
//		sql.append("       ,MAX(BIN_1_KB) AS BIN_1_KB ");
//		sql.append("       ,MAX(BIN_2_KB) AS BIN_2_KB ");
//		sql.append("       ,MAX(UPDATE_USER_ID) AS UPDATE_USER_ID ");
//		sql.append("       ,MAX(UPDATE_TS) AS UPDATE_TS ");
//		sql.append("       ,MAX(SYSTEM_KB) AS SYSTEM_KB ");
//		sql.append("       ,MAX(GYOSYU_KB) AS GYOSYU_KB ");
//		sql.append("       ,MAX(SYOHIN_KB) AS SYOHIN_KB ");
//		sql.append("       ,MAX(BUNRUI2_CD) AS BUNRUI2_CD ");
//		sql.append("       ,MAX(SYOHIN_2_CD) AS SYOHIN_2_CD ");
//		sql.append("       ,MAX(ZAIKO_SYOHIN_CD) AS ZAIKO_SYOHIN_CD ");
//		sql.append("       ,MAX(JYOHO_SYOHIN_CD) AS JYOHO_SYOHIN_CD ");
//		sql.append("       ,MAX(KIKAKU_KANA_NA) AS KIKAKU_KANA_NA ");
//		sql.append("       ,MAX(KIKAKU_KANA_2_NA) AS KIKAKU_KANA_2_NA ");
//		sql.append("       ,MAX(REC_HINMEI_KANA_NA) AS REC_HINMEI_KANA_NA ");
//		sql.append("       ,MAX(SYOHIN_WIDTH_QT) AS SYOHIN_WIDTH_QT ");
//		sql.append("       ,MAX(SYOHIN_HEIGHT_QT) AS SYOHIN_HEIGHT_QT ");
//		sql.append("       ,MAX(SYOHIN_DEPTH_QT) AS SYOHIN_DEPTH_QT ");
//		sql.append("       ,MAX(ZEI_KB) AS ZEI_KB ");
//		sql.append("       ,MAX(TAX_RATE_KB) AS TAX_RATE_KB ");
//		sql.append("       ,MAX(GENTANKA_NUKI_VL) AS GENTANKA_NUKI_VL ");
//		sql.append("       ,MAX(BAITANKA_NUKI_VL) AS BAITANKA_NUKI_VL ");
//		sql.append("       ,MAX(MAKER_KIBO_KAKAKU_VL) AS MAKER_KIBO_KAKAKU_VL ");
//		sql.append("       ,MAX(HACYU_SYOHIN_KB) AS HACYU_SYOHIN_KB ");
//		sql.append("       ,MAX(HACYU_SYOHIN_CD) AS HACYU_SYOHIN_CD ");
//		sql.append("       ,MAX(HACHU_TANI_NA) AS HACHU_TANI_NA ");
//		sql.append("       ,MAX(HANBAI_TANI) AS HANBAI_TANI ");
//		sql.append("       ,MAX(MAX_HACHUTANI_QT) AS MAX_HACHUTANI_QT ");
//		sql.append("       ,MAX(CASE_HACHU_KB) AS CASE_HACHU_KB ");
//		sql.append("       ,MAX(BARA_IRISU_QT) AS BARA_IRISU_QT ");
//		sql.append("       ,MAX(TEN_HACHU_ST_DT) AS TEN_HACHU_ST_DT ");
//		sql.append("       ,MAX(TEN_HACHU_ED_DT) AS TEN_HACHU_ED_DT ");
//		sql.append("       ,MAX(HANBAI_ST_DT) AS HANBAI_ST_DT ");
//		sql.append("       ,MAX(HANBAI_ED_DT) AS HANBAI_ED_DT ");
//		sql.append("       ,MAX(HANBAI_KIKAN_KB) AS HANBAI_KIKAN_KB ");
//		sql.append("       ,MAX(TEIKAN_KB) AS TEIKAN_KB ");
//		sql.append("       ,MAX(SOBA_SYOHIN_KB) AS SOBA_SYOHIN_KB ");
//		sql.append("       ,MAX(NOHIN_KIGEN_KB) AS NOHIN_KIGEN_KB ");
//		sql.append("       ,MAX(NOHIN_KIGEN_QT) AS NOHIN_KIGEN_QT ");
//		sql.append("       ,MAX(HACHU_PATTERN_1_KB) AS HACHU_PATTERN_1_KB ");
//		sql.append("       ,MAX(SIME_TIME_1_QT) AS SIME_TIME_1_QT ");
//		sql.append("       ,MAX(C_NOHIN_RTIME_1_KB) AS C_NOHIN_RTIME_1_KB ");
//		sql.append("       ,MAX(TEN_NOHIN_RTIME_1_KB) AS TEN_NOHIN_RTIME_1_KB ");
//		sql.append("       ,MAX(TEN_NOHIN_TIME_1_KB) AS TEN_NOHIN_TIME_1_KB ");
//		sql.append("       ,MAX(HACHU_PATTERN_2_KB) AS HACHU_PATTERN_2_KB ");
//		sql.append("       ,MAX(SIME_TIME_2_QT) AS SIME_TIME_2_QT ");
//		sql.append("       ,MAX(C_NOHIN_RTIME_2_KB) AS C_NOHIN_RTIME_2_KB ");
//		sql.append("       ,MAX(TEN_NOHIN_RTIME_2_KB) AS TEN_NOHIN_RTIME_2_KB ");
//		sql.append("       ,MAX(TEN_NOHIN_TIME_2_KB) AS TEN_NOHIN_TIME_2_KB ");
//		sql.append("       ,MAX(YUSEN_BIN_KB) AS YUSEN_BIN_KB ");
//		sql.append("       ,MAX(F_BIN_KB) AS F_BIN_KB ");
//		sql.append("       ,MAX(BUTURYU_KB) AS BUTURYU_KB ");
//		sql.append("       ,MAX(GOT_START_MM) AS GOT_START_MM ");
//		sql.append("       ,MAX(GOT_END_MM) AS GOT_END_MM ");
//		sql.append("       ,MAX(HACHU_TEISI_KB) AS HACHU_TEISI_KB ");
//		sql.append("       ,MAX(CGC_HENPIN_KB) AS CGC_HENPIN_KB ");
//		sql.append("       ,MAX(HANBAI_LIMIT_KB) AS HANBAI_LIMIT_KB ");
//		sql.append("       ,MAX(HANBAI_LIMIT_QT) AS HANBAI_LIMIT_QT ");
//		sql.append("       ,MAX(PLU_SEND_DT) AS PLU_SEND_DT ");
//		sql.append("       ,MAX(KEYPLU_FG) AS KEYPLU_FG ");
//		sql.append("       ,MAX(PLU_KB) AS PLU_KB ");
//		sql.append("       ,MAX(SYUZEI_HOKOKU_KB) AS SYUZEI_HOKOKU_KB ");
//		sql.append("       ,MAX(SAKE_NAIYORYO_QT) AS SAKE_NAIYORYO_QT ");
//		sql.append("       ,MAX(TAG_HYOJI_BAIKA_VL) AS TAG_HYOJI_BAIKA_VL ");
//		sql.append("       ,MAX(KESHI_BAIKA_VL) AS KESHI_BAIKA_VL ");
//		sql.append("       ,MAX(YORIDORI_KB) AS YORIDORI_KB ");
//		sql.append("       ,MAX(PC_KB) AS PC_KB ");
//		sql.append("       ,MAX(DAISI_NO_NB) AS DAISI_NO_NB ");
//		sql.append("       ,MAX(UNIT_PRICE_TANI_KB) AS UNIT_PRICE_TANI_KB ");
//		sql.append("       ,MAX(UNIT_PRICE_NAIYORYO_QT) AS UNIT_PRICE_NAIYORYO_QT ");
//		sql.append("       ,MAX(UNIT_PRICE_KIJUN_TANI_QT) AS UNIT_PRICE_KIJUN_TANI_QT ");
//		sql.append("       ,MAX(SYOHI_KIGEN_KB) AS SYOHI_KIGEN_KB ");
//		sql.append("       ,MAX(SYOHI_KIGEN_DT) AS SYOHI_KIGEN_DT ");
//		sql.append("       ,MAX(FREE_1_KB) AS FREE_1_KB ");
//		sql.append("       ,MAX(FREE_2_KB) AS FREE_2_KB ");
//		sql.append("       ,MAX(FREE_3_KB) AS FREE_3_KB ");
//		sql.append("       ,MAX(FREE_4_KB) AS FREE_4_KB ");
//		sql.append("       ,MAX(FREE_5_KB) AS FREE_5_KB ");
//		sql.append("       ,MAX(COMMENT_1_TX) AS COMMENT_1_TX ");
//		sql.append("       ,MAX(COMMENT_2_TX) AS COMMENT_2_TX ");
//		sql.append("       ,MAX(FREE_CD) AS FREE_CD ");
//		sql.append("       ,MAX(COMMENT_TX) AS COMMENT_TX  ");
//		sql.append("    FROM ");
//		sql.append("        R_SYOHIN  ");
//		sql.append("    GROUP BY ");
//		sql.append("        SYOHIN_CD ");
//		sql.append("       ,YUKO_DT ");
		sql.append("    SELECT ");
		sql.append("        R_S_F.SYOHIN_CD ");
		sql.append("       ,R_S_F.YUKO_DT ");
		sql.append("       ,MAX(R_S_F.BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("       ,MAX(R_S_F.BUNRUI5_CD) AS BUNRUI5_CD ");
		sql.append("       ,MAX(R_S_F.KIKAKU_KANJI_2_NA) AS KIKAKU_KANJI_2_NA ");
		sql.append("       ,MAX(R_S_F.HINMEI_KANJI_NA) AS HINMEI_KANJI_NA ");
		sql.append("       ,MAX(R_S_F.REC_HINMEI_KANJI_NA) AS REC_HINMEI_KANJI_NA ");
		sql.append("       ,MAX(R_S_F.HINMEI_KANA_NA) AS HINMEI_KANA_NA ");
		sql.append("       ,MAX(R_S_F.KIKAKU_KANJI_NA) AS KIKAKU_KANJI_NA ");
		sql.append("       ,MAX(R_S_F.BAITANKA_VL) AS BAITANKA_VL ");
		sql.append("       ,MAX(R_S_F.BAIKA_HAISHIN_FG) AS BAIKA_HAISHIN_FG ");
		sql.append("       ,MAX(R_S_F.GENTANKA_VL) AS GENTANKA_VL ");
		sql.append("       ,MAX(R_S_F.SIIRESAKI_CD) AS SIIRESAKI_CD ");
		sql.append("       ,MAX(R_S_F.HACHUTANI_IRISU_QT) AS HACHUTANI_IRISU_QT ");
		sql.append("       ,MAX(R_S_F.EOS_KB) AS EOS_KB ");
		sql.append("       ,MAX(R_S_F.BIN_1_KB) AS BIN_1_KB ");
		sql.append("       ,MAX(R_S_F.BIN_2_KB) AS BIN_2_KB ");
		sql.append("       ,MAX(R_S_F.UPDATE_USER_ID) AS UPDATE_USER_ID ");
		sql.append("       ,MAX(R_S_F.UPDATE_TS) AS UPDATE_TS ");
		sql.append("       ,MAX(R_S_F.SYSTEM_KB) AS SYSTEM_KB ");
		sql.append("       ,MAX(R_S_F.GYOSYU_KB) AS GYOSYU_KB ");
		sql.append("       ,MAX(R_S_F.SYOHIN_KB) AS SYOHIN_KB ");
		sql.append("       ,MAX(R_S_F.BUNRUI2_CD) AS BUNRUI2_CD ");
		sql.append("       ,MAX(R_S_F.SYOHIN_2_CD) AS SYOHIN_2_CD ");
		sql.append("       ,MAX(R_S_F.ZAIKO_SYOHIN_CD) AS ZAIKO_SYOHIN_CD ");
		sql.append("       ,MAX(R_S_F.JYOHO_SYOHIN_CD) AS JYOHO_SYOHIN_CD ");
		sql.append("       ,MAX(R_S_F.KIKAKU_KANA_NA) AS KIKAKU_KANA_NA ");
		sql.append("       ,MAX(R_S_F.KIKAKU_KANA_2_NA) AS KIKAKU_KANA_2_NA ");
		sql.append("       ,MAX(R_S_F.REC_HINMEI_KANA_NA) AS REC_HINMEI_KANA_NA ");
		sql.append("       ,MAX(R_S_F.SYOHIN_WIDTH_QT) AS SYOHIN_WIDTH_QT ");
		sql.append("       ,MAX(R_S_F.SYOHIN_HEIGHT_QT) AS SYOHIN_HEIGHT_QT ");
		sql.append("       ,MAX(R_S_F.SYOHIN_DEPTH_QT) AS SYOHIN_DEPTH_QT ");
		sql.append("       ,MAX(R_S_F.ZEI_KB) AS ZEI_KB ");
		sql.append("       ,MAX(R_S_F.TAX_RATE_KB) AS TAX_RATE_KB ");
		sql.append("       ,MAX(R_S_F.GENTANKA_NUKI_VL) AS GENTANKA_NUKI_VL ");
		sql.append("       ,MAX(R_S_F.BAITANKA_NUKI_VL) AS BAITANKA_NUKI_VL ");
		sql.append("       ,MAX(R_S_F.MAKER_KIBO_KAKAKU_VL) AS MAKER_KIBO_KAKAKU_VL ");
		sql.append("       ,MAX(R_S_F.HACYU_SYOHIN_KB) AS HACYU_SYOHIN_KB ");
		sql.append("       ,MAX(R_S_F.HACYU_SYOHIN_CD) AS HACYU_SYOHIN_CD ");
		sql.append("       ,MAX(R_S_F.HACHU_TANI_NA) AS HACHU_TANI_NA ");
		sql.append("       ,MAX(R_S_F.HANBAI_TANI) AS HANBAI_TANI ");
		sql.append("       ,MAX(R_S_F.MAX_HACHUTANI_QT) AS MAX_HACHUTANI_QT ");
		sql.append("       ,MAX(R_S_F.CASE_HACHU_KB) AS CASE_HACHU_KB ");
		sql.append("       ,MAX(R_S_F.BARA_IRISU_QT) AS BARA_IRISU_QT ");
		sql.append("       ,MAX(R_S_F.TEN_HACHU_ST_DT) AS TEN_HACHU_ST_DT ");
		sql.append("       ,MAX(R_S_F.TEN_HACHU_ED_DT) AS TEN_HACHU_ED_DT ");
		sql.append("       ,MAX(R_S_F.HANBAI_ST_DT) AS HANBAI_ST_DT ");
		sql.append("       ,MAX(R_S_F.HANBAI_ED_DT) AS HANBAI_ED_DT ");
		sql.append("       ,MAX(R_S_F.HANBAI_KIKAN_KB) AS HANBAI_KIKAN_KB ");
		sql.append("       ,MAX(R_S_F.TEIKAN_KB) AS TEIKAN_KB ");
		sql.append("       ,MAX(R_S_F.SOBA_SYOHIN_KB) AS SOBA_SYOHIN_KB ");
		sql.append("       ,MAX(R_S_F.NOHIN_KIGEN_KB) AS NOHIN_KIGEN_KB ");
		sql.append("       ,MAX(R_S_F.NOHIN_KIGEN_QT) AS NOHIN_KIGEN_QT ");
		sql.append("       ,MAX(R_S_F.HACHU_PATTERN_1_KB) AS HACHU_PATTERN_1_KB ");
		sql.append("       ,MAX(R_S_F.SIME_TIME_1_QT) AS SIME_TIME_1_QT ");
		sql.append("       ,MAX(R_S_F.C_NOHIN_RTIME_1_KB) AS C_NOHIN_RTIME_1_KB ");
		sql.append("       ,MAX(R_S_F.TEN_NOHIN_RTIME_1_KB) AS TEN_NOHIN_RTIME_1_KB ");
		sql.append("       ,MAX(R_S_F.TEN_NOHIN_TIME_1_KB) AS TEN_NOHIN_TIME_1_KB ");
		sql.append("       ,MAX(R_S_F.HACHU_PATTERN_2_KB) AS HACHU_PATTERN_2_KB ");
		sql.append("       ,MAX(R_S_F.SIME_TIME_2_QT) AS SIME_TIME_2_QT ");
		sql.append("       ,MAX(R_S_F.C_NOHIN_RTIME_2_KB) AS C_NOHIN_RTIME_2_KB ");
		sql.append("       ,MAX(R_S_F.TEN_NOHIN_RTIME_2_KB) AS TEN_NOHIN_RTIME_2_KB ");
		sql.append("       ,MAX(R_S_F.TEN_NOHIN_TIME_2_KB) AS TEN_NOHIN_TIME_2_KB ");
		sql.append("       ,MAX(R_S_F.YUSEN_BIN_KB) AS YUSEN_BIN_KB ");
		sql.append("       ,MAX(R_S_F.F_BIN_KB) AS F_BIN_KB ");
		sql.append("       ,MAX(R_S_F.BUTURYU_KB) AS BUTURYU_KB ");
		sql.append("       ,MAX(R_S_F.GOT_START_MM) AS GOT_START_MM ");
		sql.append("       ,MAX(R_S_F.GOT_END_MM) AS GOT_END_MM ");
		sql.append("       ,MAX(R_S_F.HACHU_TEISI_KB) AS HACHU_TEISI_KB ");
		sql.append("       ,MAX(R_S_F.CGC_HENPIN_KB) AS CGC_HENPIN_KB ");
		sql.append("       ,MAX(R_S_F.HANBAI_LIMIT_KB) AS HANBAI_LIMIT_KB ");
		sql.append("       ,MAX(R_S_F.HANBAI_LIMIT_QT) AS HANBAI_LIMIT_QT ");
		sql.append("       ,MAX(R_S_F.PLU_SEND_DT) AS PLU_SEND_DT ");
		sql.append("       ,MAX(R_S_F.KEYPLU_FG) AS KEYPLU_FG ");
		sql.append("       ,MAX(R_S_F.PLU_KB) AS PLU_KB ");
		sql.append("       ,MAX(R_S_F.SYUZEI_HOKOKU_KB) AS SYUZEI_HOKOKU_KB ");
		sql.append("       ,MAX(R_S_F.SAKE_NAIYORYO_QT) AS SAKE_NAIYORYO_QT ");
		sql.append("       ,MAX(R_S_F.TAG_HYOJI_BAIKA_VL) AS TAG_HYOJI_BAIKA_VL ");
		sql.append("       ,MAX(R_S_F.KESHI_BAIKA_VL) AS KESHI_BAIKA_VL ");
		sql.append("       ,MAX(R_S_F.YORIDORI_KB) AS YORIDORI_KB ");
		sql.append("       ,MAX(R_S_F.PC_KB) AS PC_KB ");
		sql.append("       ,MAX(R_S_F.DAISI_NO_NB) AS DAISI_NO_NB ");
		sql.append("       ,MAX(R_S_F.UNIT_PRICE_TANI_KB) AS UNIT_PRICE_TANI_KB ");
		sql.append("       ,MAX(R_S_F.UNIT_PRICE_NAIYORYO_QT) AS UNIT_PRICE_NAIYORYO_QT ");
		sql.append("       ,MAX(R_S_F.UNIT_PRICE_KIJUN_TANI_QT) AS UNIT_PRICE_KIJUN_TANI_QT ");
		sql.append("       ,MAX(R_S_F.SYOHI_KIGEN_KB) AS SYOHI_KIGEN_KB ");
		sql.append("       ,MAX(R_S_F.SYOHI_KIGEN_DT) AS SYOHI_KIGEN_DT ");
		sql.append("       ,MAX(R_S_F.FREE_1_KB) AS FREE_1_KB ");
		sql.append("       ,MAX(R_S_F.FREE_2_KB) AS FREE_2_KB ");
		sql.append("       ,MAX(R_S_F.FREE_3_KB) AS FREE_3_KB ");
		sql.append("       ,MAX(R_S_F.FREE_4_KB) AS FREE_4_KB ");
		sql.append("       ,MAX(R_S_F.FREE_5_KB) AS FREE_5_KB ");
		sql.append("       ,MAX(R_S_F.COMMENT_1_TX) AS COMMENT_1_TX ");
		sql.append("       ,MAX(R_S_F.COMMENT_2_TX) AS COMMENT_2_TX ");
		sql.append("       ,MAX(R_S_F.FREE_CD) AS FREE_CD ");
		sql.append("       ,MAX(R_S_F.COMMENT_TX) AS COMMENT_TX  ");
		sql.append("       ,MAX(R_S_F.NENREI_SEIGEN_KB) AS NENREI_SEIGEN_KB  ");
		sql.append("       ,MAX(R_S_F.NENREI) AS NENREI  ");
		sql.append("       ,MAX(R_S_F.KAN_KB) AS KAN_KB  ");
		sql.append("       ,MAX(R_S_F.HOSHOUKIN) AS HOSHOUKIN  ");
		sql.append("       ,MAX(R_S_F.MIN_HACHU_QT) AS MIN_HACHU_QT  ");
		sql.append("       ,MAX(R_S_F.PER_TXT) AS PER_TXT  ");
		sql.append("       ,MAX(R_S_F.SIIRE_ZEI_KB) AS SIIRE_ZEI_KB  ");
		sql.append("       ,MAX(R_S_F.OROSI_ZEI_KB) AS OROSI_ZEI_KB  ");
		sql.append("       ,MAX(R_S_F.OROSI_BAITANKA_VL) AS OROSI_BAITANKA_VL  ");
		sql.append("       ,MAX(R_S_F.OROSI_BAITANKA_NUKI_VL) AS OROSI_BAITANKA_NUKI_VL  ");
		sql.append("       ,MAX(R_S_F.SYOHI_KIGEN_HYOJI_PATTER) AS SYOHI_KIGEN_HYOJI_PATTER  ");
		sql.append("       ,MAX(R_S_F.PLU_HANEI_TIME) AS PLU_HANEI_TIME  ");
		sql.append("       ,MAX(R_S_F.OROSI_BAITANKA_VL_KOURI) AS OROSI_BAITANKA_VL_KOURI  ");
		sql.append("       ,MAX(R_S_F.SYOKAI_USER_ID) AS SYOKAI_USER_ID  ");
		sql.append("       ,MAX(R_S_F.PACK_WEIGHT_QT) AS PACK_WEIGHT_QT  ");
		sql.append("       ,MAX(R_S_F.SINKI_TOROKU_DT)  AS SINKI_TOROKU_DT  ");
		sql.append("       ,MAX(R_S_F.PACK_WIDTH_QT)  AS PACK_WIDTH_QT  ");
		sql.append("       ,MAX(R_S_F.SIIRE_TAX_RATE_KB) AS SIIRE_TAX_RATE_KB  ");
		sql.append("       ,MAX(R_S_F.OROSI_TAX_RATE_KB) AS OROSI_TAX_RATE_KB  ");
		sql.append("       ,MAX(R_S_F.CENTER_KYOYO_DT) AS CENTER_KYOYO_DT  ");
		sql.append("       ,MAX(R_S_F.CENTER_KYOYO_KB) AS CENTER_KYOYO_KB  ");
		sql.append("       ,MAX(R_S_F.DELETE_FG) AS DELETE_FG  ");
		sql.append("       ,MAX(ASN_F.SYOHIN_EIJI_NA) AS SYOHIN_EIJI_NA  ");
		sql.append("       ,MAX(ASN_F.COUNTRY_CD) AS COUNTRY_CD  ");
		sql.append("       ,MAX(ASN_F.MAKER_CD) AS MAKER_CD  ");
		sql.append("       ,MAX(ASN_F.MIN_ZAIKO_QT) AS MIN_ZAIKO_QT  ");
		sql.append("       ,MAX(ASN_F.SECURITY_TAG_FG) AS SECURITY_TAG_FG  ");
		sql.append("       ,MAX(ASN_F.HAMPER_SYOHIN_FG) AS HAMPER_SYOHIN_FG  ");
		sql.append("       ,MAX(ASN_F.HANBAI_HOHO_KB) AS HANBAI_HOHO_KB  ");
		sql.append("       ,MAX(ASN_F.LABEL_SEIBUN) AS LABEL_SEIBUN  ");
		sql.append("       ,MAX(ASN_F.LABEL_HOKAN_HOHO) AS LABEL_HOKAN_HOHO  ");
		sql.append("       ,MAX(ASN_F.LABEL_TUKAIKATA) AS LABEL_TUKAIKATA  ");
		sql.append("    FROM ");
		sql.append("        R_SYOHIN R_S_F  ");
		sql.append("    LEFT OUTER JOIN ");
		sql.append("        R_SYOHIN_ASN ASN_F  ");
		sql.append("    ON ");
		sql.append("        R_S_F.SYOHIN_CD = ASN_F.SYOHIN_CD  ");
		sql.append("    GROUP BY ");
		sql.append("        R_S_F.SYOHIN_CD ");
		sql.append("       ,R_S_F.YUKO_DT ");
		// #2799 Mod 2017.01.31 X.Liu (E)
		sql.append(") RS_FOR_TYPE_F  ");
		sql.append("ON ");
		sql.append("    REC_TYPE.SYOHIN_CD = RS_FOR_TYPE_F.SYOHIN_CD AND ");
		sql.append("    REC_TYPE.YUKO_DT = RS_FOR_TYPE_F.YUKO_DT  ");
		sql.append("LEFT OUTER JOIN  ");
		sql.append("(  ");
		// 区分（0:新規、1:修正、2:削除）を特定する
		sql.append("    SELECT ");
		sql.append("        PRESENT_GEN.SYOHIN_CD ");
		sql.append("       ,CASE  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE IN ('B', 'C')  ");
		sql.append("        THEN BUNRUI1_CD_FOR_TYPE_BC  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        THEN BUNRUI1_CD_FOR_TYPE_E  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'F'  ");
		sql.append("        THEN BUNRUI1_CD_FOR_TYPE_F  ");
		sql.append("        ELSE 'ERR'  ");
		sql.append("        END AS BUNRUI1_CD_FOR_KAIPAGE ");
		sql.append("       ,CASE  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'B'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'A'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'B'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'B'  ");
		sql.append("        THEN '1'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'B'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'C'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'B'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'E'  ");
		sql.append("        THEN '1'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'B'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'F'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'C'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'B'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'C'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'C'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'C'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'E'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'C'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'F'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'A'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'B'  ");
		sql.append("        THEN '1'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'C'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'E'  ");
		sql.append("        THEN '1'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'E'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'F'  ");
		sql.append("        THEN '0'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'F'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'B'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'F'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'C'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'F'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'E'  ");
		sql.append("        THEN '2'  ");
		sql.append("        WHEN PRESENT_GEN.REC_TYPE = 'F'  ");
		sql.append("        AND LAST_GEN.REC_TYPE = 'F'  ");
		sql.append("        THEN '2'  ");
		sql.append("        ELSE 'ERR'  ");
		sql.append("        END AS KB  ");
		sql.append("    FROM ");
		sql.append("    (  ");
		// 直近世代の６分類を行う
		sql.append("        SELECT ");
		// #2799対応 2017.02.06 X.Liu Mod  (S)
//		sql.append("            GENERATION ");
//		sql.append("           ,SYOHIN_CD ");
//		sql.append("           ,YUKO_DT ");
		sql.append("            GEN.GENERATION ");
		sql.append("           ,GEN.SYOHIN_CD ");
		sql.append("           ,GEN.YUKO_DT ");
		// #2799対応 2017.02.06 X.Liu Mod  (E)
		sql.append("           ,RS_FOR_TYPE_BC.BUNRUI1_CD AS BUNRUI1_CD_FOR_TYPE_BC ");
		sql.append("           ,RS_FOR_TYPE_E.BUNRUI1_CD AS BUNRUI1_CD_FOR_TYPE_E ");
		sql.append("           ,RS_FOR_TYPE_F.BUNRUI1_CD AS BUNRUI1_CD_FOR_TYPE_F ");
		sql.append("           ,CASE  ");
		sql.append("            WHEN CNT_REC = 0  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'A'  ");
		sql.append("            WHEN CNT_REC = 1  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'B'  ");
		sql.append("            WHEN CNT_REC = 1  ");
		sql.append("            AND CNT_DELFG = 1  ");
		sql.append("            THEN 'C'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'D'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 1  ");
		sql.append("            THEN 'E'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 2  ");
		sql.append("            THEN 'F'  ");
		sql.append("            ELSE ''  ");
		sql.append("            END AS REC_TYPE  ");
		sql.append("        FROM ");
		sql.append("        (  ");
		sql.append("            SELECT ");
		sql.append("                GEN.GENERATION ");
		sql.append("               ,RS3.SYOHIN_CD ");
		sql.append("               ,RS3.YUKO_DT ");
		sql.append("               ,COUNT(RS3.SYOHIN_CD) AS CNT_REC ");
		sql.append("               ,SUM(CASE RS3.DELETE_FG WHEN '1' THEN 1 ELSE 0 END) AS CNT_DELFG  ");
		sql.append("            FROM ");
		sql.append("                R_SYOHIN RS3 ");
		sql.append("               ,(  ");
		// 直近世代(0)を特定する
		sql.append("                    SELECT ");
		sql.append("                        SYOHIN_CD ");
		sql.append("                       ,MAX(YUKO_DT) AS YUKO_DT ");
		sql.append("                       ,'0' AS GENERATION  ");
		sql.append("                    FROM ");
		sql.append("                        R_SYOHIN  ");
		sql.append("                    WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                        BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                        YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		// #2799対応 2017.02.02 X.Liu Del  (S)
//		if(outputJogaiFgShiire.equals("1")){
//			// 仕入のみ商品を除外する
//			sql.append("                    AND SYOHIN_KB <> '3' ");
//		}
		// #2799対応 2017.02.02 X.Liu Del  (E)
		sql.append("                    GROUP BY ");
		sql.append("                        '0' ");
		sql.append("                       ,SYOHIN_CD ");
		sql.append("                ) GEN  ");
		sql.append("            WHERE ");
		sql.append("                RS3.SYOHIN_CD = GEN.SYOHIN_CD AND ");
		sql.append("                RS3.YUKO_DT = GEN.YUKO_DT  ");
		sql.append("            GROUP BY ");
		sql.append("                GEN.GENERATION ");
		sql.append("               ,RS3.SYOHIN_CD ");
		sql.append("               ,RS3.YUKO_DT ");
		sql.append("            ) GEN  ");
		sql.append("        LEFT OUTER JOIN  ");
		sql.append("        (  ");
		sql.append("            SELECT ");
		sql.append("                BUNRUI1_CD ");
		sql.append("               ,SYOHIN_CD ");
		sql.append("               ,YUKO_DT  ");
		sql.append("            FROM ");
		sql.append("                R_SYOHIN  ");
		sql.append("            WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		sql.append("        ) RS_FOR_TYPE_BC  ");
		sql.append("        ON ");
		sql.append("            GEN.SYOHIN_CD = RS_FOR_TYPE_BC.SYOHIN_CD AND ");
		sql.append("            GEN.YUKO_DT = RS_FOR_TYPE_BC.YUKO_DT  ");
		sql.append("        LEFT OUTER JOIN ");
		sql.append("        (  ");
		sql.append("            SELECT ");
		sql.append("                BUNRUI1_CD ");
		sql.append("               ,SYOHIN_CD ");
		sql.append("               ,YUKO_DT  ");
		sql.append("            FROM ");
		sql.append("                R_SYOHIN  ");
		sql.append("            WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                YUKO_DT BETWEEN " + startDate + " AND " + endDate + " AND ");
		sql.append("                DELETE_FG = '0' ");
		sql.append("        ) RS_FOR_TYPE_E  ");
		sql.append("        ON ");
		sql.append("            GEN.SYOHIN_CD = RS_FOR_TYPE_E.SYOHIN_CD AND ");
		sql.append("            GEN.YUKO_DT = RS_FOR_TYPE_E.YUKO_DT  ");
		sql.append("        LEFT OUTER JOIN ");
		sql.append("        (  ");
		sql.append("            SELECT ");
		sql.append("                MAX(BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("               ,SYOHIN_CD ");
		sql.append("               ,YUKO_DT  ");
		sql.append("            FROM ");
		sql.append("                R_SYOHIN  ");
		sql.append("            WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		sql.append("            GROUP BY ");
		sql.append("                SYOHIN_CD ");
		sql.append("               ,YUKO_DT ");
		sql.append("        ) RS_FOR_TYPE_F  ");
		sql.append("        ON ");
		sql.append("            GEN.SYOHIN_CD = RS_FOR_TYPE_F.SYOHIN_CD AND ");
		sql.append("            GEN.YUKO_DT = RS_FOR_TYPE_F.YUKO_DT ");
		sql.append("    ) PRESENT_GEN ");
		sql.append("   ,(  ");
		// １世代前の６分類を行う
		sql.append("        SELECT ");
		sql.append("            CNT.GENERATION ");
		sql.append("           ,CNT.SYOHIN_CD ");
		sql.append("           ,CNT.YUKO_DT ");
		sql.append("           ,CASE  ");
		sql.append("            WHEN CNT_REC = 0  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'A'  ");
		sql.append("            WHEN CNT_REC = 1  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'B'  ");
		sql.append("            WHEN CNT_REC = 1  ");
		sql.append("            AND CNT_DELFG = 1  ");
		sql.append("            THEN 'C'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 0  ");
		sql.append("            THEN 'D'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 1  ");
		sql.append("            THEN 'E'  ");
		sql.append("            WHEN CNT_REC = 2  ");
		sql.append("            AND CNT_DELFG = 2  ");
		sql.append("            THEN 'F'  ");
		sql.append("            ELSE ''  ");
		sql.append("            END AS REC_TYPE  ");
		sql.append("        FROM ");
		sql.append("        (  ");
		sql.append("            SELECT ");
		sql.append("                GEN.GENERATION ");
		sql.append("               ,NVL(RS3.SYOHIN_CD, GEN.SYOHIN_CD) AS SYOHIN_CD ");
		sql.append("               ,RS3.YUKO_DT ");
		sql.append("               ,COUNT(RS3.SYOHIN_CD) AS CNT_REC ");
		sql.append("               ,SUM(CASE RS3.DELETE_FG WHEN '1' THEN 1 ELSE 0 END) AS CNT_DELFG  ");
		sql.append("            FROM ");
		sql.append("            (  ");
		// １世代前(-1)を特定する
		sql.append("                SELECT ");
		sql.append("                    NVL(RS.SYOHIN_CD, PRESENT_GEN.SYOHIN_CD) AS SYOHIN_CD ");
		sql.append("                   ,MAX(RS.YUKO_DT) AS YUKO_DT ");
		sql.append("                   ,'-1' AS GENERATION  ");
		sql.append("                FROM ");
		sql.append("                (  ");
		// 直近世代の特定
		sql.append("                    SELECT ");
		sql.append("                        SYOHIN_CD ");
		sql.append("                       ,MAX(YUKO_DT) AS YUKO_DT  ");
		sql.append("                    FROM ");
		sql.append("                        R_SYOHIN  ");
		sql.append("                    WHERE ");
		if(!dptCd.equals("ALL")){
			// DPT指定がされている場合、抽出条件に加える
			sql.append("                        BUNRUI1_CD = '" + dptCd + "' AND " );
		}
		sql.append("                        YUKO_DT BETWEEN " + startDate + " AND " + endDate );
		// #2799対応 2017.02.06 X.Liu Del  (S)
//		if(outputJogaiFgShiire.equals("1")){
//			// 仕入のみ商品を除外する
//			sql.append("                    AND SYOHIN_KB <> '3' ");
//		}
		// #2799対応 2017.02.06 X.Liu Del  (E)
		sql.append("                    GROUP BY ");
		sql.append("                      SYOHIN_CD ");
		sql.append("                ) PRESENT_GEN  ");
		sql.append("                LEFT OUTER JOIN ");
		sql.append("                    R_SYOHIN RS  ");
		sql.append("                ON ");
		sql.append("                    RS.SYOHIN_CD = PRESENT_GEN.SYOHIN_CD AND ");
		sql.append("                    RS.YUKO_DT < PRESENT_GEN.YUKO_DT  ");
		sql.append("                GROUP BY ");
		sql.append("                    '-1' ");
		sql.append("                   ,NVL(RS.SYOHIN_CD, PRESENT_GEN.SYOHIN_CD) ");
		sql.append("            ) GEN  ");
		sql.append("            LEFT OUTER JOIN ");
		sql.append("                R_SYOHIN RS3  ");
		sql.append("            ON ");
		sql.append("                RS3.SYOHIN_CD = GEN.SYOHIN_CD AND ");
		sql.append("                RS3.YUKO_DT = GEN.YUKO_DT  ");
		sql.append("            GROUP BY ");
		sql.append("                GEN.GENERATION ");
		sql.append("               ,NVL(RS3.SYOHIN_CD, GEN.SYOHIN_CD) ");
		sql.append("               ,RS3.YUKO_DT ");
		sql.append("        ) CNT ");
		sql.append("    ) LAST_GEN  ");
		sql.append("    WHERE ");
		sql.append("        PRESENT_GEN.SYOHIN_CD = LAST_GEN.SYOHIN_CD ");
		sql.append(") SYOHIN_KB  ");
		sql.append("ON ");
		sql.append("    REC_TYPE.SYOHIN_CD = SYOHIN_KB.SYOHIN_CD  ");
		// #2799対応 2017.02.06 X.Liu Mod  (S)
		sql.append("    LEFT OUTER JOIN (SELECT * FROM R_TAX_RATE RTR ");
		sql.append("    WHERE RTR.YUKO_DT = (SELECT MAX(RT.YUKO_DT)  ");
		sql.append("                         FROM R_TAX_RATE RT  ");
		sql.append("                         WHERE RT.YUKO_DT <= " + batchDate + "  ");
		sql.append("                         AND RT.DELETE_FG = '0')) RTR   ");
		sql.append("    ON RTR.TAX_RATE_KB = RS_FOR_TYPE_BC.TAX_RATE_KB  ");
		sql.append("    OR RTR.TAX_RATE_KB = RS_FOR_TYPE_E.TAX_RATE_KB  ");
		sql.append("    OR RTR.TAX_RATE_KB =  RS_FOR_TYPE_F.TAX_RATE_KB  ");
		// #1097の対応 Add 2016.01.25 Hung.NT (S)
		sql.append("WHERE ");
		if(outputKb.equals("0")){
			// 区分「0:新規」を出力する
			sql.append("    SYOHIN_KB.KB = '0' ");
		} else if (outputKb.equals("1")){
			// 区分「1:修正」を出力する
			sql.append("    SYOHIN_KB.KB = '1' ");
		} else if (outputKb.equals("2")){
			// 区分「2:削除」を出力する
			sql.append("    SYOHIN_KB.KB = '2' ");
		} else {
			// 全区分を出力する
			sql.append("    SYOHIN_KB.KB IN ('0', '1', '2') ");
		}
		sql.append("ORDER BY ");
		sql.append("    BUNRUI1_CD_FOR_KAIPAGE ");
		sql.append("   ,SYOHIN_KB.KB ");
		sql.append("   ,REC_TYPE.SYOHIN_CD ");
		sql.append("   ,GENERATION ");
//		sql.append("WHERE ");
//		if(outputKb.equals("0")){
//			// 区分「0:新規」を出力する
//			sql.append("    SYOHIN_KB.KB = '0' ");
//		} else if (outputKb.equals("1")){
//			// 区分「1:修正」を出力する
//			sql.append("    SYOHIN_KB.KB = '1' ");
//		} else if (outputKb.equals("2")){
//			// 区分「2:削除」を出力する
//			sql.append("    SYOHIN_KB.KB = '2' ");
//		} else {
//			// 全区分を出力する
//			sql.append("    SYOHIN_KB.KB IN ('0', '1', '2') ");
//		}
//		sql.append("ORDER BY ");
//		sql.append("    BUNRUI1_CD_FOR_KAIPAGE ");
//		sql.append("   ,SYOHIN_KB.KB ");
//		sql.append("   ,REC_TYPE.SYOHIN_CD ");
//		sql.append("   ,GENERATION ");
//		// No.136 MST01018 Add 2015.12.08 Nhat.NgoM (S)
//		sql.append("  ) BASE  ");
//		sql.append("LEFT OUTER JOIN R_TAX_RATE RTR  ");
//		sql.append("ON RTR.TAX_RATE_KB = BASE.TAX_RATE_KB  ");
//		sql.append("WHERE RTR.YUKO_DT   =  ");
//		sql.append("  (SELECT MAX(RTM.YUKO_DT)  ");
//		sql.append("  FROM R_TAX_RATE RTM  ");
//		sql.append("  WHERE RTM.YUKO_DT  <= " + batchDate + "  ");
//		sql.append("  AND RTM.TAX_RATE_KB = BASE.TAX_RATE_KB  ");
//		sql.append("  AND RTM.DELETE_FG = '0'  ");
//		sql.append("  )  ");
//		// No.136 MST01018 Add 2015.12.08 Nhat.NgoM (E)
		// #2799対応 2017.02.06 X.Liu Mod  (E)
		return sql.toString();
		
		
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// バッチ日付取得
		batchDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);
		if(batchDate == null || batchDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
			throw new Exception();
		}

//		// 有効日
//		yukoDate = DateChanger.addDate(batchDate, SPAN_DAYS);

		// 出力対象DPTコード取得
		dptCd = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_DPT);
		if(dptCd == null || dptCd.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力対象DPTコードが取得できませんでした");
			throw new Exception();
		}
		// 開始日取得
		startDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_START_DT);
		if(startDate == null || startDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力対象開始日が取得できませんでした");
			throw new Exception();
		}
		if(startDate.equals("BATCH_DT")){
			// 開始日にバッチ日付を使用するよう指示されている場合、開始日にバッチ日付を代入する
			startDate = batchDate;
		}
		// 終了日取得
		endDate = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_END_DT);
		if(endDate == null || endDate.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力対象終了日が取得できませんでした");
			throw new Exception();
		}
		if(endDate.equals("BATCH_DT")){
			// 終了日にバッチ日付を使用するよう指示されている場合、終了日にバッチ日付を代入する
			endDate = batchDate;
		}

		// 出力区分取得
		 outputKb = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_KB);
		if(outputKb == null || outputKb.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力対象出力区分が取得できませんでした");
			throw new Exception();
		}
		// 出力除外フラグ（仕入のみ商品）取得
		 outputJogaiFgShiire = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_SHIIRE);
		if(outputJogaiFgShiire == null || outputJogaiFgShiire.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力除外フラグ（仕入のみ商品）が取得できませんでした");
			throw new Exception();
		}
		// 出力除外フラグ（その他改廃項目）取得
		 outputJogaiFgOthers = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.SYOHIN_MASTER_HENKO_LIST_BATCH_OUTPUT_JOGAI_OTHERS);
		if(outputJogaiFgOthers == null || outputJogaiFgOthers.trim().length() == 0){
			this.writeLog(Level.INFO_INT, "システムコントロールから、出力除外フラグ（その他改廃項目）が取得できませんでした");
			throw new Exception();
		}
	}

	/**
	 * 商品マスタ変更リストCSV作成
	 * @throws Exception
	 */
	private void setDaichoCSV(mst010181_SyohinMasterHenkoListOutputBean meisaiData, StringBuffer str, int seq, String rec_type) throws SQLException {

		//#2799 Mod X.Liu 2017.02.06 (S)
		// 旧世代行である場合、改行を行う
//		if(rec_type.equals("old")) {
//			str.append("\r\n");
//		}
		str.append("\r\n");
		//#2799 Mod X.Liu 2017.02.06 (E)
		// #2799対応 2017.02.06 X.Liu Mod  (S)
//		str.append(seq).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKb()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Cd()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getYuko_Dt()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPlu_Send_Dt()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui1_Cd()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui5_Cd()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKikaku_Kanji_2_Na()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHinmei_Kanji_Na()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getRec_Hinmei_Kanji_Na()).trim()).append(SPLIT_CODE);
////		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHinmei_Kana_Na()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKikaku_Kanji_Na()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBaitanka_Vl()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBaika_Haishin_Fg()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getGentanka_Vl()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSiiresaki_Cd()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHachutani_Irisu_Qt()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getEos_Kb()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBin_1_Kb()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBin_2_Kb()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUpdate_User_Id()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUpdate_Ts()).trim()).append(SPLIT_CODE);
//		str.append(mst000401_LogicBean.chkNullString(meisaiData.getOthers_Mod_Kb()).trim());

		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getYuko_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPlu_Send_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPlu_hanei_time()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui1_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui5_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHinmei_Kanji_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKikaku_Kanji_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(meisaiData.getBaitanka_Vl())).trim())).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getGentanka_Nuki_Vl()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getOrosi_baitanka_nuki_vl()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSiiresaki_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHachutani_Irisu_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getMin_hachu_qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUpdate_User_Id()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUpdate_Ts()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui2_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSinki_toroku_dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyokai_user_id()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSoba_Syohin_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getTeikan_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getZaiko_Syohin_Cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Height_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Width_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_Depth_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getMaker_Kibo_Kakaku_Vl()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPack_weight_qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getCountry_cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getMaker_cd()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_hoho_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKikaku_Kanji_2_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKikaku_Kanji_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getRec_Hinmei_Kanji_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getButuryu_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getBaitanka_Nuki_Vl()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getGentanka_Vl()).trim()))).append(SPLIT_CODE);
        str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getOrosi_baitanka_vl()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(MoneyUtil.removeFormatMoney(MoneyUtil.formatCostUnitString(meisaiData.getOrosi_baitanka_vl_kouri()).trim()))).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getZei_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getTax_Rate_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSiire_zei_kb()).trim()).append(SPLIT_CODE);
        str.append(mst000401_LogicBean.chkNullString(meisaiData.getSiire_tax_rate_kb()).trim()).append(SPLIT_CODE);
        str.append(mst000401_LogicBean.chkNullString(meisaiData.getOrosi_zei_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getOrosi_tax_rate_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHachu_Tani_Na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_Tani()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getMin_zaiko_qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getMax_Hachutani_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getF_Bin_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getBara_Irisu_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getCase_Hachu_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPer_txt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getPc_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getDaisi_No_Nb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_St_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_Ed_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getTen_Hachu_St_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getTen_Hachu_Ed_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getGot_Start_Mm()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getGot_End_Mm()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_Limit_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHanbai_Limit_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getCenter_kyoyo_dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getCenter_kyoyo_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUnit_Price_Tani_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUnit_Price_Naiyoryo_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getUnit_Price_Kijun_Tani_Qt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohi_kigen_hyoji_patter()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohi_Kigen_Dt()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSyohin_eiji_na()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getLabel_seibun()).trim()).append(SPLIT_CODE);
        str.append(mst000401_LogicBean.chkNullString(meisaiData.getLabel_hokan_hoho()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getLabel_tukaikata()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getCgc_Henpin_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getNenrei_seigen_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getNenrei()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getKan_kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHoshoukin()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getSecurity_tag_fg()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getHamper_syohin_fg()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_1_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_2_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_3_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_4_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_5_Kb()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getComment_1_Tx()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getComment_2_Tx()).trim()).append(SPLIT_CODE);
		str.append(mst000401_LogicBean.chkNullString(meisaiData.getFree_Cd()).trim()).append(SPLIT_CODE);
		
		// 旧世代行である場合
//		if(rec_type.equals("old")) {
//			// 次処理にて同行に直近世代行を出力するため、区切り文字を挿入する
//			str.append(SPLIT_CODE);
//		}
//		// 直近世代行である場合
//		if(rec_type.equals("new")) {
//			// 改ページ用のDPTコードを挿入する
//			str.append(SPLIT_CODE);
//			str.append(mst000401_LogicBean.chkNullString(meisaiData.getBunrui1_Cd_For_Kaipage()).trim());
//		}
		// #2799対応 2017.02.06 X.Liu Mod  (E)
	}

/********** 共通処理 **********/

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}

	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 *
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

	// No.136 MST01018 Add 2015.12.02 Nhat.NgoM (S)
    /**
     * 対象転化する(BigDecimal)
     * @param Object ob
     * @return BigDecimal ob
     */
    private BigDecimal decConver(Object ob) {
        if (ob == null || !NumberUtils.isNumber(ob.toString())) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(ob.toString());
        }
    }
  // No.136 MST01018 Add 2015.12.02 Nhat.NgoM (E)
    
}
