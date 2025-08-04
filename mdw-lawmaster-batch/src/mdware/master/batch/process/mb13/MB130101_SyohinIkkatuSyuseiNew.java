package mdware.master.batch.process.mb13;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.mdware.common.util.StringUtility;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.batch.process.mb38.MB380001_CommonMessage;
import mdware.master.batch.process.mb38.MB380007_CommonSyohinSql;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst005501_TankaItirituSyuseiDictionary;
import mdware.master.common.dictionary.mst006901_AlarmMakeFgDictionary;
import mdware.master.common.dictionary.mst009801_SyohinIkkatuDelTargetDictionary;
import mdware.master.common.dictionary.mst009901_TankaCalKbDictionary;
import mdware.master.common.dictionary.mst010001_SyoriSyubetsuKbDictionary;
import mdware.master.common.dictionary.mst011701_KazeiKbDictionary;
import mdware.master.common.dictionary.mst910020_EmgFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;
/**
 * <p>タイトル:商品マスタ一括修正処理</p>
 * <p>説明:マスタ管理</p>
 * <p>【商品マスタ一括修正】(R_SYOHIN_IKKATU_MENTE)より当日のデータを抽出し</p>
 * <p> その抽出データを元に編集したデータで【商品マスタ】(R_SYOHIN)を更新する</p>
 * <p> 更新内容を業種区分別に【アラームテーブル】(R_ALARM)と
 * <p>【商品マスタ一括修正T/R】(TR_SYOHIN_IKKATU_MENTE)に登録する</p>
 * <p>【マスタ日付テーブル】(SYSTEM_CONTROL)のバッチ日付を当日とする</p>
 * <p>当日までで直近のもの</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 * @Version 1.01  (2015.06.24) DAI.BQ FIVImart様対応
 * @Version 1.02  (2015.08.17) THE.VV FIVImart様対応
 * @Version 1.03  (2015.11.26) HUY.NT FIVI様対応
 * @Version 1.04  (2016.01.11) HUY.NT FIVI様対応
 * @Version 1.05  (2016.03.16) HUY.NT 設計書No.621(#1155) FIVImart対応
 * @Version 1.06  (2016.03.18) Vuong.LT 設計書No.624(#1177) FIVImart対応
 * @Version 1.07  (2016.08.04) H.Sakamoto #1899対応
 * @Version 1.08  (2016.09.23) S.Li #2133対応
 * @Version 1.09  (2016.09.28) T.Arimoto #2192対応
 * @Version 1.10  (2016.10.06) M.Akagi #2295対応
 * @Version 1.11  (2016.10.31) H.Sakamoto #2551対応
 * @Version 1.12  (2016.11.09) M.Akagi #2722対応
 * @version 1.13  (2016.11.10) t.han #2256対応
 * @Version 1.14  (2016.11.21) nv.cuong #2800対応
 * @Version 1.15  (2016.12.01) Li.Sheng #3086対応
 * @Version 1.16  (2016.12.05) Li.Sheng #3124対応
 * @Version 1.17  (2016.12.06) T.Arimoto #3148対応
 * @Version 1.18  (2017.01.19) T.Arimoto #3608対応
 * @Version 1.19  (2017.01.31) T.Arimoto #3822対応
 * @Version 1.20  (2017.02.02) T.Arimoto #3829対応
 * @Version 1.20  (2017.04.03) M.Akagi #4509
 * @version 1.21  (2017.06.28) S.Takayama #5518対応
 * @version 1.22  (2017.09.15) Li.Sheng #5938対応
 * @version 1.23  (2017.09.27) M.Akagi #5974
 * @version 1.24  (2017.10.04) M.Akagi #5994
 * @version 1.24  (2017.10.11) M.Akagi #6002
 * @version 1.25  (2020.10.30) THONG.VQ #6265 MKV対応
 */

public class MB130101_SyohinIkkatuSyuseiNew
{

	private MasterDataBase db = null;

	//バッチ日付取得
	String batchDt = RMSTDATEUtil.getBatDateDt();

	private BatchUserLog userLog = BatchUserLog.getInstance();

	private String sysDt = null; // システム日付

	//受付SEQ№
	private int A08cnt = 0;
	private int A07cnt = 0;
	private int GROcnt = 0;
	private int FREcnt = 0;
	private int updSyohincnt = 0; //商品マスタの更新件数カウント
	private int insSyohincnt = 0; //商品マスタの登録件数カウント
	private int delSyohincnt = 0; //商品マスタの削除件数カウント

	// 2015/06/20 DAI.BQ (S)
	private int insSyohinANScnt = 0;
	private int delSyohinASNcnt = 0;
	// 2015/06/20 DAI.BQ (E)

	private int updTbsiiresakicnt = 0; //店別仕入先マスタの更新件数カウント
	private int insSyikkmentecnt = 0; //商品一括修正TRの登録件数カウト
	//2016/11/02 VINX h.sakamoto #2551対応 (S)
	private int updKanricnt = 0;//商品一括修正管理の更新件数カウント
	//2016/11/02 VINX h.sakamoto #2551対応 (E)
	// 2016.11.09 M.Akagi #2722 (S)
	private int updErrcnt = 0;//商品一括修正エラーデータの更新件数カウント
	// 2016.11.09 M.Akagi #2722 (E)
	private boolean tankaChkFlg = false; //原価単価、売価単価のチェックフラグ
	//エラーメッセージ
	private List errmsg = new ArrayList();
	private Map msgMap = MB380001_CommonMessage.getMsg();

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private static final String BATCH_ID = "MB13-01-01";
	private static final String BATCH_NA = "BAT_商品マスタ一括修正処理";
	private String batchID = "";

	private static final String SYORI_KB_MISYORI   = "0";
	private static final String SYORI_KB_SYORITYU  = "9";
	private static final String SYORI_KB_SYORIZUMI = "1";
	// 2016.11.09 M.Akagi #2722 (S)
	private static final String SYORI_KB_ERROR = "2";
	// 2016.11.09 M.Akagi #2722 (E)

	//2016/10/31 VINX h.sakamoto #2551対応 (S)
	private static final String KANRI_SYORI_STATUS_KB_MISYORI = "0";
	private static final int KANRI_SYORI_STATUS_KB_SEIJO = 1;
	private static final int KANRI_SYORI_STATUS_KB_ERROR = 2;
	//2016/10/31 VINX h.sakamoto #2551対応 (E)

	// 2017.10.04 M.Akagi #5994 (S)
	private static final double GENKA_MAX = 999999999.99;
	private static final double BAIKA_MAX = 999999999;
	// 2017.10.04 M.Akagi #5994 (E)

	//20061003 hiu@vjc  パフォーマンス改善対応start
	private PreparedStatement selectSyohinPreparedStatement = null;
	private PreparedStatement insertTRPreparedStatement = null;
	//	20061003 hiu@vjc  パフォーマンス改善対応end
	//2016/10/31 VINX h.sakamoto #2551対応 (S)
	private PreparedStatement updateKanriPreparedStatement = null;
	//2016/10/31 VINX h.sakamoto #2551対応 (E)

	private PreparedStatement psSiiresakCheck= null;

	// 2015/06/20 DAI.BQ (S)
	private PreparedStatement psSyohinASNIns = null;
	// 2015/06/20 DAI.BQ (E)
	private PreparedStatement psGiftIns = null; 			//ギフト商品マスタInsert用
	private PreparedStatement psGiftUpd = null; 			//ギフト商品マスタUpdate用
	private PreparedStatement psKeiryokiIns = null; 		//計量器マスタInsert用
	private PreparedStatement psKeiryokiUpd = null; 		//計量器マスタUpdate用
	private PreparedStatement psHachuIns = null; 			//商品発注基準日マスタInsert用
	private PreparedStatement psHachuUpd = null; 			//商品発注納品基準日マスタUpdate用

	//2016/11/02 VINX h.sakamoto #2551対応 (S)
//	private String uketsukeNo = null;
	//2016/11/02 VINX h.sakamoto #2551対応 (E)

	private boolean errorFg = false;

	private MB380007_CommonSyohinSql comSyohin = new MB380007_CommonSyohinSql(batchDt, BATCH_ID, mst000601_GyoshuKbDictionary.GRO.getCode()); //商品マスタ生成共通SQLクラス

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception
	{
		batchID = batchId;
		execute();
	}

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB130101_SyohinIkkatuSyuseiNew(MasterDataBase db)
	{
		this.db = db;
		if (db == null)
		{
			this.db = new MasterDataBase("rbsite_ora");
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB130101_SyohinIkkatuSyuseiNew()
	{
		this(new MasterDataBase("rbsite_ora"));
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception
	{

		String jobId = userLog.getJobId();
		int cntUp = 0;
		errorFg = false;

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");
            
            //システム日付を初期化
			sysDt = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");

			psGiftIns     = comSyohin.getPreparedGiftSyohinInsSQL(db, BATCH_ID);
			psGiftUpd     = comSyohin.getPreparedSyohinSubDelUpSQL(db, "R_GIFT_SYOHIN");
			psKeiryokiIns = comSyohin.getPreparedKeiryokiInsSQL(db, BATCH_ID);
			psKeiryokiUpd = comSyohin.getPreparedSyohinSubDelUpSQL(db, "R_KEIRYOKI");
			psHachuIns    = comSyohin.getPreparedSyohinHachuNohinkijunbiInsSQL(db, BATCH_ID);
			psHachuUpd    = comSyohin.getPreparedSyohinSubDelUpSQL(db, "R_SYOHIN_HACHUNOHINKIJUNBI");
			String bunrui1Cd = "";

			//処理対象件数カウント用変数IF
			int iProcTargetDataNum = 0;

			// 処理区分を「処理中」に変更
			writeLog(Level.INFO_INT, "処理区分を「処理中」に変更します。");
			// 2016.11.09 M.Akagi #2722 (S)
			//cntUp = db.executeUpdate(getUpdateSyoriKb(SYORI_KB_MISYORI, SYORI_KB_SYORITYUl));
			cntUp = db.executeUpdate(getUpdateSyoriKb(SYORI_KB_MISYORI, SYORI_KB_SYORITYU, null));
			// 2016.11.09 M.Akagi #2722 (E)
			writeLog(Level.INFO_INT, cntUp + "件更新しました。");

			// 処理対象確認
			if (cntUp > 0) {

				//商品マスタ一括修正テーブルからデータを検索SQL
				ResultSet rs1 = db.executeQuery(setSelecSyohinikkatumente());

				while (rs1.next()){

					//処理対象件数をカウントする
					iProcTargetDataNum = iProcTargetDataNum + 1;

					// エラーメッセージをクリアー
					errmsg.clear();

					//分類1コードが異なる場合は受付№を採番
					if (!bunrui1Cd.equals(rs1.getString("BUNRUI1_CD"))) {

						//分類1コードを退避
						bunrui1Cd = rs1.getString("BUNRUI1_CD");

						// 受付№取得
						//2016/11/02 VINX h.sakamoto #2551対応 (S)
//						uketsukeNo = MDWareSeq.nextValString("bat_uketsuke_no", BATCH_ID);
//						writeLog(Level.INFO_INT, "受付№を取得しました [" + uketsukeNo + "]");
						//2016/11/02 VINX h.sakamoto #2551対応 (E)

						//受付SEQ
						GROcnt = 0;
					}

					//登録更新処理判断項目の変数を設定する
					tankaChkFlg = false;

					//商品マスタの各フィールド値
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//					double[] price = new double[4];
					// add 2016.11.22 #2800 nv.cuong(S)
					//double[] price = new double[6];
					double[] price = new double[7];
					// add 2016.11.22 #2800 nv.cuong(E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
					price[0] = 0; 						//原価単価
					price[1] = 0; 						//売価単価
					price[2] = 0; 						//原価単価(税抜）
					price[3] = 0; 						//売価単価(税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (S)
					price[4] = 0; 						//卸売価単価
					price[5] = 0; 						//卸売価単価（税抜）
					
					// add 2016.11.22 #2800 nv.cuong(S)
					price[6] = 0;						//卸売価単価(小売税)
					// add 2016.11.22 #2800 nv.cuong(E)
					// 2017/01/19 T.Arimoto #3608対応（S)
					BigDecimal orosiBaitankaVlBd = null;// 卸売価単価（税抜）nullチェック用
					// 2017/01/19 T.Arimoto #3608対応（E)
					
//2016/08/04 VINX h.sakamoto #1899対応 (E)
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
					String mGyosyukb = null;			//業種区分
					String yukoDt = null;				//有効日
					String deleteFg = null;				//削除フラグ
					String mSiiresakikanricd = null;	//店別仕入先管理コード
					String mSiiresakiCd = null;

					String itmYukoDt = rs1.getString("yuko_dt"); //一括修正で指定した有効日

					String del_target = rs1.getString("del_target"); //一括削除対象


					//=======================
					// 後者優先の確認
					//=======================
					if 	(isBlank(rs1.getString("syori_taisyo_ck")))	{

						errmsg.add(msgMap.get("0003"));
						ResultSet rs2 = executeSelectSyohinPreparedStatement(rs1);
						if (rs2.next()) {
							// #6265 MB130101 UPD 2020.10.30 THONG.VQ (S)
							//price[0] = new Double(rs2.getString("gentanka_vl")).doubleValue(); //原価単価
							//price[1] = new Double(rs2.getString("baitanka_vl")).doubleValue(); //売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
							//price[2] = new Double(rs2.getString("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
							//price[4] = new Double(rs2.getString("orosi_baitanka_vl")).doubleValue(); //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
							//price[5] = new Double(rs2.getString("orosi_baitanka_nuki_vl")).doubleValue(); //卸売価単価（税抜）
							if( null != rs2.getString("gentanka_vl") ){
								price[0] = new Double(rs2.getString("gentanka_vl")).doubleValue(); //原価単価
							}
							if( null != rs2.getString("baitanka_vl") ){
								price[1] = new Double(rs2.getString("baitanka_vl")).doubleValue(); //売価単価
							}
							if( null != rs2.getString("gentanka_nuki_vl") ){
								price[2] = new Double(rs2.getString("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
							}
							if( null != rs2.getString("orosi_baitanka_nuki_vl") ){
								price[5] = new Double(rs2.getString("orosi_baitanka_nuki_vl")).doubleValue(); //卸売価単価（税抜）
							}
							// #6265 MB130101 UPD 2020.10.30 THONG.VQ (E)
							// 2017/01/19 T.Arimoto #3608対応（S)
							orosiBaitankaVlBd = rs2.getBigDecimal("orosi_baitanka_nuki_vl");//卸売価単価（税抜）
							// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
						} else {
							price[0] = 0; 		//原価単価
							price[1] = 0; 		//売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
							price[2] = 0;		//原価単価（税抜）
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
							//price[4] = 0; //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
							price[5] = 0; //卸売価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						}
						db.closeResultSet(rs2);
						mGyosyukb = rs1.getString("GYOSYU_KB");
						// 2017/01/19 T.Arimoto #3608対応（S)
						//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
						MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
						// 2017/01/19 T.Arimoto #3608対応（E)
						continue;
					}

					//=======================
					// 有効日の確認
					//=======================
					// 2016.10.13 T.han #2256対応（S)
					//if (batchDt.compareTo(itmYukoDt) >= 0)
					if (batchDt.compareTo(itmYukoDt) > 0)
					// 2016.10.13 T.han #2256対応（E)
					{
//						errmsg.add("一括修正で指定した有効日はバッチ日付より過去なので、修正できません。");
						errmsg.add(msgMap.get("0265"));
						ResultSet rs2 = executeSelectSyohinPreparedStatement(rs1);
						if (rs2.next()) {
							price[0] = new Double(rs2.getString("gentanka_vl")).doubleValue(); //原価単価
							price[1] = new Double(rs2.getString("baitanka_vl")).doubleValue(); //売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
							price[2] = new Double(rs2.getString("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
							//price[4] = new Double(rs2.getString("orosi_baitanka_vl")).doubleValue(); //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
							price[5] = new Double(rs2.getString("orosi_baitanka_nuki_vl")).doubleValue(); //卸売価単価（税抜）
							// 2017/01/19 T.Arimoto #3608対応（S)
							orosiBaitankaVlBd = rs2.getBigDecimal("orosi_baitanka_nuki_vl"); //卸売価単価（税抜）
							// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						} else {
							price[0] = 0; 		//原価単価
							price[1] = 0; 		//売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
							price[2] = 0; //原価単価（税抜）
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
							//price[4] = 0; //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
							price[5] = 0; //卸売価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						}
						mGyosyukb = rs1.getString("GYOSYU_KB");
						// 2017/01/19 T.Arimoto #3608対応（S)
						//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
						MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
						// 2017/01/19 T.Arimoto #3608対応（E)
						continue;
					}

					//===========================================================
					// 商品マスタから（部門＋商品）条件でデータを取る
					// ResultSetをListへ書き換え（頻繁にDBへのアクセスを省くため）
					//===========================================================
					ResultSet rs2 = executeSelectSyohinPreparedStatement(rs1);
					List rowList = cnvResultSet2List(rs2);
					db.closeResultSet(rs2);

					if (rowList.size() <= 0) {

						// 商品なし
						errmsg.add(msgMap.get("0117"));
						mGyosyukb = rs1.getString("GYOSYU_KB");
						price[0] = 0; 		//原価単価
						price[1] = 0; 		//売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
						price[2] = 0; 		//原価単価（税抜）
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
						//price[4] = 0; //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
						price[5] = 0; //卸売価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						// 2017/01/19 T.Arimoto #3608対応（S)
						orosiBaitankaVlBd = null;// 卸売価単価（税抜）
						//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
						MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
						// 2017/01/19 T.Arimoto #3608対応（E)

					} else {

						// 有効日が最大のデータを取得
						HashMap tmpM = (HashMap) rowList.get(0);
						yukoDt = (String) tmpM.get("yuko_dt");
						// #6265 MB130101 UPD 2020.10.30 THONG.VQ (S)
						//price[0] = new Double((String) tmpM.get("gentanka_vl")).doubleValue();	//原価単価
						//price[1] = new Double((String) tmpM.get("baitanka_vl")).doubleValue();	//売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
						//price[2] = new Double((String) tmpM.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
						if( null != tmpM.get("gentanka_vl") ){
							price[0] = new Double((String) tmpM.get("gentanka_vl")).doubleValue(); //原価単価
						}
						if( null != tmpM.get("baitanka_vl") ){
							price[1] = new Double((String) tmpM.get("baitanka_vl")).doubleValue(); //売価単価
						}
						if( null != tmpM.get("gentanka_nuki_vl") ){
							price[2] = new Double((String) tmpM.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
						}
						// #6265 MB130101 UPD 2020.10.30 THONG.VQ (E)
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
//						price[4] = new Double((String) tmpM.get("orosi_baitanka_vl")).doubleValue();	//卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
						// 2017/01/19 T.Arimoto #3608対応（S)
						//price[5] = new Double((String) tmpM.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						if( null != tmpM.get("orosi_baitanka_nuki_vl") ){
							orosiBaitankaVlBd = new BigDecimal((String)tmpM.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
							price[5] = new Double((String) tmpM.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
							price[6] = new Double((String) tmpM.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価(小売税)
						} else {
							price[5] = 0;
							price[6] = 0;	//卸売価単価(小売税)
							orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
						}
						// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
						
						// 2017/01/19 T.Arimoto #3608対応（S)
						// add 2016.11.22 #2800 nv.cuong(S)
						//price[6] = new Double((String) tmpM.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価(小売税)
						// add 2016.11.22 #2800 nv.cuong(E)
						// 2017/01/19 T.Arimoto #3608対応（E)
						
						mGyosyukb = (String) tmpM.get("gyosyu_kb"); 							//業種区分
						deleteFg = (String) tmpM.get("delete_fg");								//削除フラグ
						mSiiresakikanricd = (String) tmpM.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
						mSiiresakiCd = (String) tmpM.get("siiresaki_cd");						//仕入先コード


						// 2016.10.13 T.han #2256対応（S)
						// 先付けの登録がある場合は修正不可
						//if (yukoDt.compareTo(itmYukoDt) > 0) {
						// 先付けの場合
						if (yukoDt.compareTo(itmYukoDt) > 0) {

							// 削除対象の場合
							if(del_target != null && del_target.equals(mst009801_SyohinIkkatuDelTargetDictionary.TAISYO.getCode())){

								errmsg.add(msgMap.get("0012"));
								// 2017/01/19 T.Arimoto #3608対応（S)
								//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
								MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
								// 2017/01/19 T.Arimoto #3608対応（E)
// #3124 2016/12/05 Add Li.Sheng 対応 (S)							
								continue;
// #3124 2016/12/05 Add Li.Sheng 対応 (E)	
							}

							// 未来世代数のチェック
							int cnt = 0;
							String wYukoDt = null;
							for (int i = 0; i < rowList.size(); i++) {
								HashMap hsWk = (HashMap) rowList.get(i);
								wYukoDt = (String) hsWk.get("yuko_dt");
								if (wYukoDt.compareTo(batchDt) > 0) {
									cnt++;
								}
							}

							// 有効日がバッチ日付の場合
							if (itmYukoDt.compareTo(batchDt) == 0) {
								// 現世代有効日がバッチ日付かどうかをチェック
								int btcnt = 0;
								String wbtYukoDt = null;
								for (int i = 0; i < rowList.size(); i++) {
									HashMap hsbtWk = (HashMap) rowList.get(i);
									wbtYukoDt = (String) hsbtWk.get("yuko_dt");
									if (wbtYukoDt.compareTo(batchDt) == 0) {
										btcnt++;
									}
								}

								// 2017/02/02 T.Arimoto #3829対応（S)
								// 有効日が前回のデータを取得
//								HashMap tmpOld = (HashMap) rowList.get(cnt + btcnt);
//								yukoDt = (String) tmpOld.get("yuko_dt");
//								price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
//								price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
//								price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
//								// 2017/01/19 T.Arimoto #3608対応（S)
//								//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//								if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
//									price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//									orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
//								} else {
//									price[5] = 0;	//卸売価単価（税抜）
//									orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
//								}
//								// 2017/01/19 T.Arimoto #3608対応（E)
//								mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
//								deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
//								mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
//								mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
								// 2017/02/02 T.Arimoto #3829対応（E)

								if (btcnt >= 1) {

									// 有効日がバッチ日付のデータを取得
									HashMap tmpBt = (HashMap) rowList.get(cnt);
									// 2017/02/02 T.Arimoto #3829対応（S)
									yukoDt = (String) tmpBt.get("yuko_dt");
									price[0] = new Double((String) tmpBt.get("gentanka_vl")).doubleValue();	//原価単価
									price[1] = new Double((String) tmpBt.get("baitanka_vl")).doubleValue();	//売価単価
									price[2] = new Double((String) tmpBt.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
									// 2017/01/19 T.Arimoto #3608対応（S)
									//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
									if( null != tmpBt.get("orosi_baitanka_nuki_vl") ){
										price[5] = new Double((String) tmpBt.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
										orosiBaitankaVlBd = new BigDecimal((String)tmpBt.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
									} else {
										price[5] = 0;	//卸売価単価（税抜）
										orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
									}
									// 2017/01/19 T.Arimoto #3608対応（E)
									mGyosyukb = (String) tmpBt.get("gyosyu_kb"); 							//業種区分
									deleteFg = (String) tmpBt.get("delete_fg");								//削除フラグ
									mSiiresakikanricd = (String) tmpBt.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
									mSiiresakiCd = (String) tmpBt.get("siiresaki_cd");						//仕入先コード

									HashMap tmpOld = null;
									if(rowList.size() > cnt + btcnt ){
										// 更新データより前の世代がある場合、前の世代を取得
										tmpOld = (HashMap) rowList.get(cnt + btcnt);
									}
									// 2017/02/02 T.Arimoto #3829対応（E)
									//データのチェック及び更新(update)
									// 2017/01/19 T.Arimoto #3608対応（S)
									//updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, itmYukoDt, tmpBt, tmpOld);
									updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, itmYukoDt, orosiBaitankaVlBd, tmpBt, tmpOld);
									// 2017/01/19 T.Arimoto #3608対応（E)

								} else {
									// 2017/02/02 T.Arimoto #3829対応（S)
									// 更新データより前の世代のデータを取得
									HashMap tmpOld = (HashMap) rowList.get(cnt);
									yukoDt = (String) tmpOld.get("yuko_dt");
									price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
									price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
									price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
									if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
										price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
										orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
									} else {
										price[5] = 0;	//卸売価単価（税抜）
										orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
									}
									mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
									deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
									mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
									mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
									// 2017/02/02 T.Arimoto #3829対応（E)
								
									//データのチェック及び更新(Insert)
									// 2017/01/19 T.Arimoto #3608対応（S)
									//insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpOld);
									insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, orosiBaitankaVlBd, tmpOld);
									// 2017/01/19 T.Arimoto #3608対応（E)

								}

								// 引続き商品マスタ更新
								if (cnt == 2) {
									ResultSet rs3 = executeSelectSyohinPreparedStatement(rs1);
									List rowList1 = cnvResultSet2List(rs3);
									HashMap tmpMirai1 = (HashMap) rowList1.get(1);
									HashMap tmpNow = (HashMap) rowList1.get(2);
									// 未来世代１データ更新
									updateDataContinue(tmpMirai1, tmpNow);

									ResultSet rs4 = executeSelectSyohinPreparedStatement(rs1);
									List rowList2 = cnvResultSet2List(rs4);
									HashMap tmpMirai2 = (HashMap) rowList2.get(0);
									HashMap tmpMirai = (HashMap) rowList2.get(1);
									// 未来世代２データ更新
									updateDataContinue(tmpMirai2, tmpMirai);
									
								} else if (cnt == 1) {
									
									ResultSet rs5 = executeSelectSyohinPreparedStatement(rs1);
									List rowList3 = cnvResultSet2List(rs5);
									HashMap tmpMirai1 = (HashMap) rowList3.get(0);
									HashMap tmpNow = (HashMap) rowList3.get(1);
									// 未来世代１データ更新
									updateDataContinue(tmpMirai1, tmpNow);
								}

							} else {
								
								// 2017/01/31 T.Arimoto #3822対応（S)
								// 有効日が前回のデータを取得
//								HashMap tmpOld = (HashMap) rowList.get(cnt);
//								yukoDt = (String) tmpOld.get("yuko_dt");
//								price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
//								price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
//								price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
//								// 2017/01/19 T.Arimoto #3608対応（S)
//								//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//								if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
//									price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//									orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
//								} else {
//									price[5] = 0;	//卸売価単価（税抜）
//									orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
//								}
//								// 2017/01/19 T.Arimoto #3608対応（E)
//								mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
//								deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
//								mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
//								mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
								// 2017/01/31 T.Arimoto #3822対応（E)

								if (cnt >= 2) {

									// 未来世代１のデータを取得
									HashMap tmpM1 = (HashMap) rowList.get(1);
									yukoDt = (String) tmpM1.get("yuko_dt");

									if (itmYukoDt.compareTo(yukoDt) == 0) {
										// 2017/02/02 T.Arimoto #3829対応（S)
//										// 2017/01/31 T.Arimoto #3822対応（S)
//										// 有効日が前回のデータを取得
//										HashMap tmpOld = (HashMap) rowList.get(cnt);
//										//yukoDt = (String) tmpOld.get("yuko_dt");
//										price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
//										price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
//										price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
//										// 2017/01/19 T.Arimoto #3608対応（S)
//										//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//										if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
//											price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//											orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
//										} else {
//											price[5] = 0;	//卸売価単価（税抜）
//											orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
//										}
//										// 2017/01/19 T.Arimoto #3608対応（E)
//										mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
//										deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
//										mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
//										mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
//										// 2017/01/31 T.Arimoto #3822対応（E)

										// 未来世代１のデータを取得
										//yukoDt = (String) tmpOld.get("yuko_dt");
										price[0] = new Double((String) tmpM1.get("gentanka_vl")).doubleValue();	//原価単価
										price[1] = new Double((String) tmpM1.get("baitanka_vl")).doubleValue();	//売価単価
										price[2] = new Double((String) tmpM1.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
										if( null != tmpM1.get("orosi_baitanka_nuki_vl") ){
											price[5] = new Double((String) tmpM1.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
											orosiBaitankaVlBd = new BigDecimal((String)tmpM1.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
										} else {
											price[5] = 0;	//卸売価単価（税抜）
											orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
										}
										mGyosyukb = (String) tmpM1.get("gyosyu_kb"); 							//業種区分
										deleteFg = (String) tmpM1.get("delete_fg");								//削除フラグ
										mSiiresakikanricd = (String) tmpM1.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
										mSiiresakiCd = (String) tmpM1.get("siiresaki_cd");						//仕入先コード
										
										HashMap tmpOld = null;
										if(rowList.size() > cnt ){
											// 更新データより前の世代がある場合、前の世代を取得
											tmpOld = (HashMap) rowList.get(cnt);
										}
										// 2017/02/02 T.Arimoto #3829対応（E)

										// データのチェック及び更新(update)
										// 2017/01/19 T.Arimoto #3608対応（S)
										//updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpM1, tmpOld);
										updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, orosiBaitankaVlBd, tmpM1, tmpOld);
										// 2017/01/19 T.Arimoto #3608対応（E)

										// 引続き商品マスタ更新
										ResultSet rs6 = executeSelectSyohinPreparedStatement(rs1);
										List rowList1 = cnvResultSet2List(rs6);
										HashMap tmpMirai2 = (HashMap) rowList1.get(0);
										HashMap tmpMirai1 = (HashMap) rowList1.get(1);
										// 未来世代２データ更新
										updateDataContinue(tmpMirai2, tmpMirai1);
										
									} else {
										
										// 未来のレコードが既に2件以上存在するため、修正できません
										errmsg.add(msgMap.get("0015"));
										// 2017/01/19 T.Arimoto #3608対応（S)
										//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
										MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
										// 2017/01/19 T.Arimoto #3608対応（E)
									}

								} else {
									// 2017/01/31 T.Arimoto #3822対応（S)
									// 有効日が前回のデータを取得
									HashMap tmpOld = (HashMap) rowList.get(cnt);
									yukoDt = (String) tmpOld.get("yuko_dt");
									price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
									price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
									price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
									// 2017/01/19 T.Arimoto #3608対応（S)
									//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
									if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
										price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
										orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
									} else {
										price[5] = 0;	//卸売価単価（税抜）
										orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
									}
									// 2017/01/19 T.Arimoto #3608対応（E)
									mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
									deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
									mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
									mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
									// 2017/01/31 T.Arimoto #3822対応（E)
									
									//データのチェック及び更新(Insert)
									// 2017/01/19 T.Arimoto #3608対応（S)
									//insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpOld);
									insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, orosiBaitankaVlBd, tmpOld);
									// 2017/01/19 T.Arimoto #3608対応（E)

									// 引続き商品マスタ更新
									ResultSet rs7 = executeSelectSyohinPreparedStatement(rs1);
									List rowList1 = cnvResultSet2List(rs7);
									HashMap tmpMirai2 = (HashMap) rowList1.get(0);
									HashMap tmpMirai1 = (HashMap) rowList1.get(1);
									// 未来世代２データ更新
									updateDataContinue(tmpMirai2, tmpMirai1);
									
								}
								
							}
							//errmsg.add(msgMap.get("0012"));
							//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
							// 2016.10.13 T.han #2256対応（E)

						// 同日の場合
						} else if (yukoDt.equals(itmYukoDt)) {

							// 削除データの場合
							if (mst000801_DelFlagDictionary.IRU.getCode().equals(deleteFg)) {

								errmsg.add(msgMap.get("0012"));
								// 2017/01/19 T.Arimoto #3608対応（S)
								//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
								MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
								// 2017/01/19 T.Arimoto #3608対応（E)

							} else 	{

								// 削除対象の場合、削除処理を実行
								if(del_target != null && del_target.equals(mst009801_SyohinIkkatuDelTargetDictionary.TAISYO.getCode())){

									//データの削除(delete)
									// 2017/01/19 T.Arimoto #3608対応（S)
									//deleteData(rs1, price, mGyosyukb, yukoDt);
									deleteData(rs1, price, mGyosyukb, orosiBaitankaVlBd, yukoDt);
									// 2017/01/19 T.Arimoto #3608対応（E)

								} else {

									// 2016.10.13 T.han #2256対応（S)
									// 有効日が前回のデータを取得
									// 2017/02/02 T.Arimoto #3829対応（S)
//									HashMap tmpOld = (HashMap) rowList.get(1);
//									price[0] = new Double((String) tmpOld.get("gentanka_vl")).doubleValue();	//原価単価
//									price[1] = new Double((String) tmpOld.get("baitanka_vl")).doubleValue();	//売価単価
//									price[2] = new Double((String) tmpOld.get("gentanka_nuki_vl")).doubleValue(); //原価単価（税抜）
//									// 2017/01/19 T.Arimoto #3608対応（S)
//									//price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//									if( null != tmpOld.get("orosi_baitanka_nuki_vl") ){
//										price[5] = new Double((String) tmpOld.get("orosi_baitanka_nuki_vl")).doubleValue();	//卸売価単価（税抜）
//										orosiBaitankaVlBd = new BigDecimal((String)tmpOld.get("orosi_baitanka_nuki_vl"));	//卸売価単価（税抜） nullチェック用
//									} else {
//										price[5] = 0;	//卸売価単価（税抜）
//										orosiBaitankaVlBd = null;	//卸売価単価（税抜） nullチェック用
//									}
//									// 2017/01/19 T.Arimoto #3608対応（E)
//									mGyosyukb = (String) tmpOld.get("gyosyu_kb"); 							//業種区分
//									deleteFg = (String) tmpOld.get("delete_fg");								//削除フラグ
//									mSiiresakikanricd = (String) tmpOld.get("ten_siiresaki_kanri_cd");		//店別仕入先管理コード
//									mSiiresakiCd = (String) tmpOld.get("siiresaki_cd");						//仕入先コード
									
									HashMap tmpOld = null;
									if(rowList.size() > 1 ){
										// 更新データより前の世代がある場合、前の世代を取得
										tmpOld = (HashMap) rowList.get(1);
									}
									// 2017/02/02 T.Arimoto #3829対応（E)
									
									//データのチェック及び更新(update)
									//updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpM);
									// 2017/01/19 T.Arimoto #3608対応（S)
									//updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpM, tmpOld);
									updateData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, orosiBaitankaVlBd, tmpM, tmpOld);
									// 2017/01/19 T.Arimoto #3608対応（E)
									// 2016.10.13 T.han #2256対応（E)
								}
							}

						// 過去の場合
						} else {

							// 削除データの場合
							if (mst000801_DelFlagDictionary.IRU.getCode().equals(deleteFg)) {

								//商品マスタなし
								errmsg.add(msgMap.get("0117"));
								mGyosyukb = rs1.getString("GYOSYU_KB");
								price[0] = 0; 		//原価単価
								price[1] = 0; 		//売価単価
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
								price[2] = 0; 		//原価単価（税抜）
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
								//price[4] = 0; //卸売価単価
//2016/09/23 VINX Del S.Li #2133対応 (E)
								price[5] = 0; //卸売価単価（税抜）
//2016/08/04 VINX h.sakamoto #1899対応 (E)

								// 2017/01/19 T.Arimoto #3608対応（S)
								orosiBaitankaVlBd = null;// 卸売価単価（税抜）
								//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
								MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
								// 2017/01/19 T.Arimoto #3608対応（E)

							} else {

								// 世代数のチェック
								int cnt = 0;
								String wYukoDt = null;
								for (int i = 0; i < rowList.size(); i++) {
									HashMap hsWk = (HashMap) rowList.get(i);
									wYukoDt = (String) hsWk.get("yuko_dt");
									if (wYukoDt.compareTo(batchDt) > 0) {
										cnt++;
									}
								}
								if (cnt >= 2) {
			//						errmsg.add("未来のレコードが既に2件以上存在するため、修正できません。");
									errmsg.add(msgMap.get("0015"));
									// 2017/01/19 T.Arimoto #3608対応（S)
									//MB130301_SyohinIkkatuSyuseiTR(rs1, price, mGyosyukb);
									MB130301_SyohinIkkatuSyuseiTR(rs1, price, orosiBaitankaVlBd, mGyosyukb);
									// 2017/01/19 T.Arimoto #3608対応（E)

								} else {

									// 削除対象の場合、削除処理を実行
									if(del_target != null && del_target.equals(mst009801_SyohinIkkatuDelTargetDictionary.TAISYO.getCode())){

										//削除登録(DelInsert)
										// 2017/01/19 T.Arimoto #3608対応（S)
										//insertDelData(rs1, price, mGyosyukb, yukoDt);
										insertDelData(rs1, price, mGyosyukb, orosiBaitankaVlBd, yukoDt);
										// 2017/01/19 T.Arimoto #3608対応（E)
									} else {

										//データのチェック及び更新(Insert)
										// 2017/01/19 T.Arimoto #3608対応（S)
										//insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, tmpM);
										insertData(rs1, price, mGyosyukb, mSiiresakiCd, mSiiresakikanricd, yukoDt, orosiBaitankaVlBd, tmpM);
										// 2017/01/19 T.Arimoto #3608対応（E)
									}
								}
							}
						}
					}
				}
				db.closeResultSet(rs1);

				if (iProcTargetDataNum > 0 && insertTRPreparedStatement != null) {
					insertTRPreparedStatement.executeBatch();
				}

				// 処理区分を「処理済」に変更
				writeLog(Level.INFO_INT, "処理区分を「処理済」に変更します。");
				// 2016.11.09 M.Akagi #2722 (S)
				//cntUp = db.executeUpdate(getUpdateSyoriKb(SYORI_KB_SYORITYU, SYORI_KB_SYORIZUMI));
				cntUp = db.executeUpdate(getUpdateSyoriKb(SYORI_KB_SYORITYU, SYORI_KB_SYORIZUMI, null));
				// 2016.11.09 M.Akagi #2722 (E)
				writeLog(Level.INFO_INT, cntUp + "件更新しました。");

				// バッチ処理結果TRへの登録
				writeLog(Level.INFO_INT, "バッチ処理結果TRに登録します");
				cntUp = db.executeUpdate(getInsBatchSyoriKekka());
				writeLog(Level.INFO_INT, cntUp + "件登録しました。");
			}

			db.commit();

			//2016/10/31 VINX h.sakamoto #2551対応 (S)
			if (iProcTargetDataNum > 0 && insertTRPreparedStatement != null) {
				// 商品一括修正管理 更新処理
				ResultSet errCntList = db.executeQuery(getErrorCountSql(false));
				while(errCntList.next()){
					updateKanriPreparedStatement(errCntList);
					updKanricnt++ ;
				}

				updateKanriPreparedStatement.executeBatch();

				// 2016.11.09 M.Akagi #2722 (S)
				ResultSet errCntList1= db.executeQuery(getErrorCountSql(true));
				while(errCntList1.next()){
					updErrcnt = db.executeUpdate(getUpdateSyoriKb(SYORI_KB_SYORIZUMI, SYORI_KB_ERROR, errCntList1));
					updErrcnt ++ ;
				}
				// 2016.11.09 M.Akagi #2722 (E)

				db.commit();
			}

			//2016/10/31 VINX h.sakamoto #2551対応 (E)
			//追加、更新ログ出力
			writeLog(Level.INFO_INT, updSyohincnt + "件の商品マスタを更新しました。");
			writeLog(Level.INFO_INT, insSyohincnt + "件の商品マスタを登録しました。");
			writeLog(Level.INFO_INT, delSyohincnt + "件の商品マスタを削除しました。");
			writeLog(Level.INFO_INT, insSyikkmentecnt + "件の商品一括修正TRを登録しました。");
			//2016/10/31 VINX h.sakamoto #2551対応 (S)
			writeLog(Level.INFO_INT, updKanricnt + "件の商品一括修正管理を更新しました。");
			//2016/10/31 VINX h.sakamoto #2551対応 (E)
			// 2016.11.09 M.Akagi #2722 (S)
			writeLog(Level.INFO_INT, "処理区分を「エラー」に変更します。");
			writeLog(Level.INFO_INT, updErrcnt + "件更新しました。");
			// 2016.11.09 M.Akagi #2722 (E)

			psGiftIns.close();
			psKeiryokiIns.close();
			psHachuIns.close();
			if (psSiiresakCheck != null) psSiiresakCheck.close();

			//SQLエラーが発生した場合の処理
		}
		catch (SQLException se)
		{
			db.rollback();
			writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		}
		catch (Exception e)
		{
			db.rollback();
			writeError(e);
			throw e;

			//SQL終了処理
		}
		finally
		{
			db.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 * @param rs
	 * @return
	 */
	private List cnvResultSet2List(ResultSet rs) throws SQLException
	{
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			HashMap hmap = new HashMap();
			hmap.put("BUNRUI1_CD", rs.getString("BUNRUI1_CD"));
			hmap.put("syohin_cd", rs.getString("syohin_cd"));
			hmap.put("yuko_dt", rs.getString("yuko_dt"));
			hmap.put("delete_fg", rs.getString("delete_fg"));
			hmap.put("gentanka_vl", rs.getString("gentanka_vl"));
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
			hmap.put("gentanka_nuki_vl",  rs.getString("gentanka_nuki_vl"));
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
			hmap.put("baitanka_vl", rs.getString("baitanka_vl"));
//2016/08/03 VINX h.sakamoto #1899対応 (S)
			hmap.put("orosi_baitanka_vl", rs.getString("orosi_baitanka_vl"));
			hmap.put("orosi_baitanka_nuki_vl", rs.getString("orosi_baitanka_nuki_vl"));
//2016/08/03 VINX h.sakamoto #1899対応 (E)
			hmap.put("gyosyu_kb", rs.getString("gyosyu_kb"));
			hmap.put("ten_siiresaki_kanri_cd", rs.getString("ten_siiresaki_kanri_cd"));
			hmap.put("update_user_id", rs.getString("update_user_id"));
			hmap.put("syokai_user_id", rs.getString("syokai_user_id"));
			hmap.put("siiresaki_cd", rs.getString("siiresaki_cd"));
			hmap.put("hanbai_st_dt", rs.getString("hanbai_st_dt"));
			hmap.put("hanbai_ed_dt", rs.getString("hanbai_ed_dt"));
			hmap.put("ten_hachu_st_dt", rs.getString("ten_hachu_st_dt"));
			hmap.put("ten_hachu_ed_dt", rs.getString("ten_hachu_ed_dt"));
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
			hmap.put("zei_kb", rs.getString("zei_kb"));
			hmap.put("tax_rt", rs.getString("tax_rt"));
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
//2016/08/03 VINX h.sakamoto #1899対応 (S)
			hmap.put("siire_tax_rt", rs.getString("siire_tax_rt"));
			hmap.put("orosi_tax_rt", rs.getString("orosi_tax_rt"));
			hmap.put("siire_zei_kb", rs.getString("siire_zei_kb"));
			hmap.put("siire_tax_rate_kb", rs.getString("siire_tax_rate_kb"));
			hmap.put("orosi_zei_kb", rs.getString("orosi_zei_kb"));
			hmap.put("orosi_tax_rate_kb", rs.getString("orosi_tax_rate_kb"));
//2016/08/03 VINX h.sakamoto #1899対応 (E)
			// 2016.10.13 T.han #2256対応（S)
			hmap.put("cur_generation_yuko_dt", rs.getString("cur_generation_yuko_dt"));
			hmap.put("cur_generation_gentanka_vl", rs.getString("cur_generation_gentanka_vl"));
			hmap.put("one_generation_yuko_dt", rs.getString("one_generation_yuko_dt"));
			hmap.put("one_generation_gentanka_vl", rs.getString("one_generation_gentanka_vl"));
			hmap.put("two_generation_yuko_dt", rs.getString("two_generation_yuko_dt"));
			hmap.put("two_generation_gentanka_vl", rs.getString("two_generation_gentanka_vl"));
			// 2016.10.13 T.han #2256対応（E)
			list.add(hmap);
		}
		return list;
	}

//2016/08/03 VINX h.sakamoto (S)
	/**
	 * 原価・売価・卸売価の計算処理
	 * @param genChgKb 原価変更区分
	 * @param baiChgKb 売価変更区分
	 * @param oroshiBaiKb 卸売単価変更区分
	 * @param moroshiBaiVal 卸売価変動値
	 * @param sZeiKb 仕入税区分
	 * @param sTaxRt 仕入税率区分
	 * @param mOZeiKb 卸税区分
	 * @param mOTaxRt 卸税率区分
	 * @param priceVal 単価(原価、売価)
	 * @param mgenpriceVal 原価変動値
	 * @param mbaipriceVal 売価変動値
	 * @param nheKb 値入率変更区分
	 * @param nRt 値入率
	 * @param genCalKb 原価計算区分
	 * @param neireCalKb 値入率計算区分
	 * @return 原価・売価の計算値
	 * @throws Exception
	 * @throws IllegalArgumentException
//	/**
//	 * 原価・売価の計算処理
//	 * @param genChgKb 原価変更区分
//	 * @param baiChgKb 売価変更区分
//	 * @param priceVal 単価(原価、売価)
//	 * @param mgenpriceVal 原価変動値
//	 * @param mbaipriceVal 売価変動値
//	 * @param nheKb 値入率変更区分
//	 * @param nRt 値入率
//	 * @param genCalKb 原価計算区分
//	 * @param neireCalKb 値入率計算区分
//	 * @return 原価・売価の計算値
//	 * @throws Exception
//	 * @throws IllegalArgumentException
	*/
//2016/08/03 VINX h.sakamoto (E)
	private double[] Calculation(
		String genChgKb,
		String baiChgKb,
//2016/08/03 VINX h.sakamoto (S)
		String oroshiBaiChgKb,
		double moroshiBaiChgVal,
		String sZeiKb,
		String sTaxRt,
		String mOZeiKb,
		String mOTaxRt,
//2016/08/03 VINX h.sakamoto (E)
		double[] priceVal,
		double mgenpriceVal,
		double mbaipriceVal,
		String nheKb,
		double nRt
		,String genCalKb
		,String neireCalKb
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
		,String zeiKb
		,String taxRt
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
		// 2017/01/19 T.Arimoto #3608対応（S)
		,BigDecimal orosiBaitankaVlBd
		// 2017/01/19 T.Arimoto #3608対応（E)
	) throws IllegalArgumentException, Exception
	{
		String userLocal =ResorceUtil.getInstance().getPropertie("USER_LOCAL");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
//		double setGenPriceVal = 0;
//		double setBaiPriceVal = 0;
//		setGenPriceVal = priceVal[0];
//		setBaiPriceVal = priceVal[1];
		double setGenPriceVal = 0;
		double setBaiPriceVal = 0;
		double setGenNukiPriceVal = 0;
		setBaiPriceVal = priceVal[1];
		setGenNukiPriceVal = priceVal[2];
//2016/08/03 VINX h.sakamoto #1899対応 (S)
//2016/09/23 VINX Del S.Li #2133対応 (S)
		//double setOroshipriceVal = 0;
//2016/09/23 VINX Del S.Li #2133対応 (E)
		double setOroshiNukipriceVal = 0;
//2016/09/23 VINX Del S.Li #2133対応 (S)
//		setOroshipriceVal = priceVal[4];
//2016/09/23 VINX Del S.Li #2133対応 (E)
		setOroshiNukipriceVal = priceVal[5];
//2016/08/03 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)

// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
//		//%OFF
//		if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_OFF.getCode()).equals(genChgKb)){
//			setGenPriceVal = priceVal[0] * (100 - mgenpriceVal) * 0.01;
//
//			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
//				// 円単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal);
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal))));
//			}else{
//				// 銭単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal*100)/100;
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal*100))))/100;
//			}
//		}else if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_UP.getCode()).equals(genChgKb)){
//			setGenPriceVal = priceVal[0] * (100 + mgenpriceVal) * 0.01;
//
//			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
//				// 円単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal);
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal))));
//			}else{
//				// 銭単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal*100)/100;
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal*100))))/100;
//			}
//
//		//円引き
//		}else if ((mst005501_TankaItirituSyuseiDictionary.ENHIKI.getCode()).equals(genChgKb)){
//			setGenPriceVal = priceVal[0] - mgenpriceVal;
//
//			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
//				// 円単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal);
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal))));
//			}else{
//				// 銭単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal*100)/100;
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal*100))))/100;
//			}
//
//		//円指定
//		}else if ((mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode()).equals(genChgKb)){
//			setGenPriceVal = mgenpriceVal;
//
//			if(genCalKb == null || genCalKb.equals("")){
//				// genCalKb がnull の場合は差し戻しデータのため、何もしない(登録されたデータをそのまま使う)
//
//			}else if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
//				// 円単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal);
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal))));
//			}else{
//				// 銭単位で切り捨て
//				//setGenPriceVal = Math.floor(setGenPriceVal*100)/100;
//				setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal*100))))/100;
//			}
//		}

		//原価単価（税抜）の計算
		//%OFF
		if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_OFF.getCode()).equals(genChgKb)){
			setGenNukiPriceVal = priceVal[2] * (100 - mgenpriceVal) * 0.01;

			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
				// 円単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal))));
			}else{
				// 銭単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal*100))))/100;
			}
		//%UP
		}else if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_UP.getCode()).equals(genChgKb)){
			setGenNukiPriceVal = priceVal[2] * (100 + mgenpriceVal) * 0.01;

			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
				// 円単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal))));
			}else{
				// 銭単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal*100))))/100;
			}

		//円引き
		}else if ((mst005501_TankaItirituSyuseiDictionary.ENHIKI.getCode()).equals(genChgKb)){
			setGenNukiPriceVal = priceVal[2] - mgenpriceVal;

			if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
				// 円単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal))));
			}else{
				// 銭単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal*100))))/100;
			}

		//円指定
		}else if ((mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode()).equals(genChgKb)){
			setGenNukiPriceVal = mgenpriceVal;

			if(genCalKb == null || genCalKb.equals("")){
				// genCalKb がnull の場合は差し戻しデータのため、何もしない(登録されたデータをそのまま使う)

			}else if(genCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
				// 円単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal))));
			}else{
				// 銭単位で切り捨て
				setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal*100))))/100;
			}
		}
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)

		//売価単価の計算
		//%OFF
		if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_OFF.getCode()).equals(baiChgKb)){
			setBaiPriceVal = priceVal[1] * (100 - mbaipriceVal) * 0.01;
			//setBaiPriceVal = Math.floor(setBaiPriceVal);
			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));

		//%UP
		}else if ((mst005501_TankaItirituSyuseiDictionary.PERCENT_UP.getCode()).equals(baiChgKb)){
			setBaiPriceVal = priceVal[1] * (100 + mbaipriceVal) * 0.01;
			//setBaiPriceVal = Math.floor(setBaiPriceVal);
			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));

		//円引き
		}else if ((mst005501_TankaItirituSyuseiDictionary.ENHIKI.getCode()).equals(baiChgKb)){
			setBaiPriceVal = priceVal[1] - mbaipriceVal;
			//setBaiPriceVal = Math.floor(setBaiPriceVal);
			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));

		//円指定
		}else if ((mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode()).equals(baiChgKb)){
			setBaiPriceVal = mbaipriceVal;
			//setBaiPriceVal = Math.floor(setBaiPriceVal);
			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));
		}
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
		setBaiPriceVal = round(setBaiPriceVal,0);
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
// 2016/08/05 VINX h.sakamoto #1899対応 (S)
		// 卸売単価の計算
		// %OFF
		if((mst005501_TankaItirituSyuseiDictionary.PERCENT_OFF.getCode()).equals(oroshiBaiChgKb)){
			// 卸売価単価（税抜) *  (100 - 卸売単価変動値) * 0.01) 　で、修正後卸価単価(税抜)を求める。
			// 項a.の修正後卸売価単価(税抜)を卸売価単価(税抜)にセットする。
			setOroshiNukipriceVal = priceVal[5] * (100 - moroshiBaiChgVal) * 0.01;
			setOroshiNukipriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setOroshiNukipriceVal))));
		// %UP
		}else if((mst005501_TankaItirituSyuseiDictionary.PERCENT_UP.getCode()).equals(oroshiBaiChgKb)){
			// 卸売価単価（税抜)*  (100 + 卸売単価変動値) * 0.01) 　で、修正後卸価単価（税抜)を求める。
			// 項a.の修正後卸売価単価(税抜)を卸売価単価(税抜)にセットする。
			setOroshiNukipriceVal = priceVal[5] * (100 + moroshiBaiChgVal) * 0.01;
			setOroshiNukipriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setOroshiNukipriceVal))));
		// 円引き
		}else if((mst005501_TankaItirituSyuseiDictionary.ENHIKI.getCode()).equals(oroshiBaiChgKb)){
			// 卸売価単価（税抜) - 卸売単価変動値 で、修正後卸売価単価（税抜)を求める。
			// 項a.の修正後卸売価単価(税抜)を卸売価単価(税抜)にセットする。
			setOroshiNukipriceVal = priceVal[5] - moroshiBaiChgVal;
			setOroshiNukipriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setOroshiNukipriceVal))));

		// 2016/09/27 T.Arimoto #2192対応（S)
		}else if ((mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode()).equals(oroshiBaiChgKb)){
			//円指定
			setOroshiNukipriceVal = moroshiBaiChgVal;
			setOroshiNukipriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setOroshiNukipriceVal))));
		// 2016/09/27 T.Arimoto #2192対応（E)
		}
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
		setOroshiNukipriceVal = round(setOroshiNukipriceVal,0);
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
// 2016/08/05 VINX h.sakamoto #1899対応 (E)

// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
//		//原価固定 by kema 06.09.07
//		if (("1").equals(nheKb))
//		{
//			setBaiPriceVal = priceVal[0] * (1 + nRt);
//			//	setBaiPriceVal = Math.floor(setBaiPriceVal);
//			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));
//
//			setGenPriceVal = priceVal[0];
//
//
//		//売価固定by kema 06.09.07
//		}else if (("2").equals(nheKb)){
//			setGenPriceVal = priceVal[1] * (1 - nRt);
//
//			if(neireCalKb != null && !neireCalKb.trim().equals("")){
//				if(neireCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
//					// 円単位で切り捨て
//					//setGenPriceVal = Math.floor(setGenPriceVal);
//					setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal))));
//				}else{
//					// 銭単位で切り捨て
//					//setGenPriceVal = Math.floor(setGenPriceVal*100)/100;
//					setGenPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenPriceVal*100))))/100;
//				}
//			}
//			//setBaiPriceVal = (long) priceVal[1];
//			setBaiPriceVal = priceVal[1];
//		}

		//原価固定
		if (("1").equals(nheKb)){
			//原価単価（税抜）を税込に変換
			// 2016.10.06 M.Akgai #2295 (S)
			//int zei_kb = Integer.parseInt(zeiKb);
			//BigDecimal tax_rt = new BigDecimal(taxRt);
			int zei_kb = Integer.parseInt(sZeiKb);
			BigDecimal tax_rt = new BigDecimal(sTaxRt);
			// 2016.10.06 M.Akgai #2295 (E)
			BigDecimal gentanka_nuki_vl = new BigDecimal(priceVal[2]);
			if(zei_kb == 1){
				zei_kb = 2;
			}
			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_nuki_vl,
					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
					MoneyUtil.getFractionCostUnitMode());
			// 2016.10.06 M.Akgai #2295 (S)
			//if(("1").equals(zeiKb)){
			if(("1").equals(sZeiKb)){
			// 2016.10.06 M.Akgai #2295 (E)
				setGenPriceVal = ctu.getTaxIn().doubleValue();
			}else{
				setGenPriceVal = ctu.getTaxOut().doubleValue();
			}
			//原価単価（税込）を元に修正後原価単価を算出
			setBaiPriceVal = setGenPriceVal / (1 - nRt);
			setBaiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.sellUnitFormat(Double.toString(setBaiPriceVal))));
			// 2017.09.27 M.Akagi #5974 (S)
			setBaiPriceVal = round(setBaiPriceVal,0);
			// 2017.09.27 M.Akagi #5974 (E)

		//売価固定
		}else if (("2").equals(nheKb)){
			//売価を元に修正後原価単価（税込）を算出
			setGenPriceVal = priceVal[1] * (1 - nRt);
			//修正後原価単価（税込）を元に修正後原価単価（税抜）を算出
//2016/08/03 VINX h.sakamoto #1899(S)
//			int zei_kb = Integer.parseInt(zeiKb);
//			BigDecimal tax_rt = new BigDecimal(taxRt);
			int zei_kb = Integer.parseInt(sZeiKb);
			BigDecimal tax_rt = new BigDecimal(sTaxRt);
//2016/08/03 VINX h.sakamoto #1899 (E)
			BigDecimal gentanka_vl = new BigDecimal(setGenPriceVal);
			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
					MoneyUtil.getFractionCostUnitMode());

				setGenNukiPriceVal = ctu.getTaxOut().doubleValue();

			if(neireCalKb != null && !neireCalKb.trim().equals("")){
				if(neireCalKb.equals(mst009901_TankaCalKbDictionary.EN.getCode())){
					// 円単位で切り捨て
					setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal))));
				}else{
					// 銭単位で切り捨て
					setGenNukiPriceVal = Double.parseDouble(MoneyUtil.removeFormatMoney(MoneyUtil.costUnitFormat(Double.toString(setGenNukiPriceVal*100))))/100;
				}
			}
		}
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)


//2011.04.08 Y.Imai Mod 原単価・売単価=0登録可対応 AM00077 Start
//		//計算結果が1円未満になった場合エラーとする
//		if (setGenPriceVal < 1)
//		{
//			errmsg.add("原価単価が１円未満になるため更新しませんでした。");
//			tankaChkFlg = true;
//		}
//
//		//計算結果が1円未満になった場合エラーとする
//		if (setBaiPriceVal < 1)
//		{
//			errmsg.add("売価単価が１円未満になるため更新しませんでした。");
//			tankaChkFlg = true;
//		}

// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
		//計算結果が0円未満になった場合エラーとする
		//if (setGenPriceVal < 0)
		if (setGenNukiPriceVal < 0)
		{
			//errmsg.add(MessageUtil.getMessage("MST000601_TXT_00001",userLocal));
			errmsg.add(MessageUtil.getMessage("MB130101_TXT_00001",userLocal));
			tankaChkFlg = true;
		}
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)

		//計算結果が1円未満になった場合エラーとする
		if (setBaiPriceVal < 0)
		{
			errmsg.add(MessageUtil.getMessage("MB130101_TXT_00005",userLocal));
			tankaChkFlg = true;
		}
//2011.04.08 Y.Imai Mod 原単価・売単価=0登録可対応 AM00077 End

//2016/08/04 VINX h.sakamoto #1899対応 (S)
		// 卸売価計算結果が0円未満になった場合エラーとする
		// 2017/01/19 T.Arimoto #3608対応（S)
		//if(setOroshiNukipriceVal < 0)
		if( null != orosiBaitankaVlBd  && setOroshiNukipriceVal < 0 )
		// 2017/01/19 T.Arimoto #3608対応（E)
		{
			errmsg.add(MessageUtil.getMessage("MB130101_TXT_00006", userLocal));
			tankaChkFlg = true;
		}
		//2016/08/04 VINX h.sakamoto #1899対応 (E)

// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
//		priceVal[0] = setGenPriceVal;
//		priceVal[1] = setBaiPriceVal;
		priceVal[1] = setBaiPriceVal;
		priceVal[2] = setGenNukiPriceVal;
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
		// 修正後卸売価単価（税抜）
		priceVal[5] = setOroshiNukipriceVal;
//2016/08/04 VINX h.sakamoto #1899対応 (E)

//2013.12.18 [CUS00048]  マスタ未使用項目 (S)

// No.182 MSTB051 Del 2015.11.26 HUY.NT (S)
//		//原価単価（税抜）
//		BigDecimal gentanka_vl =new BigDecimal(priceVal[0]);
//		//円未満四捨五入の対応
//		BigDecimal num =new BigDecimal("100");
//		gentanka_vl = gentanka_vl.multiply(num);
//		int zei_kb = Integer.parseInt(zeiKb);
//		BigDecimal tax_rt = new BigDecimal(taxRt);
//
//		if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
//				&& ! gentanka_vl.equals("")){
//			//CalculateTaxUtility ctu = new  CalculateTaxUtility(gentanka_vl, zei_kb, tax_rt,CalculateTaxUtility.STR_MIMANSISYAGONYU);
//			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionCostUnitMode());
//			priceVal[2] = (ctu.getTaxOut().divide(num)).doubleValue();
//		}else{
//			priceVal[2] =  0.00;
//		}
// No.182 MSTB051 Del 2015.11.26 HUY.NT (E)

// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
		//原価単価（税込）
		BigDecimal gentanka_nuki_vl =new BigDecimal(priceVal[2]);
		int zei_kb = Integer.parseInt(zeiKb);
		BigDecimal tax_rt = new BigDecimal(taxRt);
//2016/08/04 VINX h.sakamoto #1899対応 (S)
		int siire_zei_kb = Integer.parseInt(sZeiKb);
		BigDecimal siire_tax_rt = new BigDecimal(sTaxRt);
//			if(zei_kb == 1){
//				zei_kb = 2;
//			}
		if (zeiKb.equals("1")) {
			zei_kb = 2;

		} else {
			zei_kb = Integer.parseInt(zeiKb);

		}
		if (sZeiKb.equals("1")) {
			siire_zei_kb = 2;

		} else {
			siire_zei_kb = Integer.parseInt(sZeiKb);

		}
//2016/08/04 VINX h.sakamoto #1899対応 (E)

		if(!gentanka_nuki_vl.equals("")){
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_nuki_vl,
//					MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionCostUnitMode());
			CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_nuki_vl,
					MoneyUtil.getFractionCostUnitLen(), siire_zei_kb, siire_tax_rt,
					MoneyUtil.getFractionCostUnitMode());
			if(("1").equals(sZeiKb)){
//2016/08/04 VINX h.sakamoto #1899対応 (E)
				priceVal[0] = ctu.getTaxIn().doubleValue();
			}else{
				priceVal[0] = ctu.getTaxOut().doubleValue();
			}
		}
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)

		//売価単価(税抜）
		BigDecimal baitanka_vl = new BigDecimal( priceVal[1]);
// No.182 MSTB051 Add 2015.11.30 HUY.NT (S)
		zei_kb = Integer.parseInt(zeiKb);
// No.182 MSTB051 Add 2015.11.30 HUY.NT (E)
// #5518 Add 2017.06.29 S.Takayama (S)
//		if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())
//				&& ! baitanka_vl.equals("")){
//			//CalculateTaxUtility ctu = new  CalculateTaxUtility(baitanka_vl, zei_kb, tax_rt);
//			CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl,
//					MoneyUtil.getFractionSellUnitLen(), zei_kb, tax_rt,
//					MoneyUtil.getFractionSellUnitMode());
//			priceVal[3] = ctu.getTaxOut().doubleValue();
//		}else{
//			priceVal[3] =  0;
//		}
		if(!baitanka_vl.equals("")){
			CalculateTaxUtility ctu = new CalculateTaxUtility(baitanka_vl,
					MoneyUtil.getFractionSellUnitLen(), zei_kb, tax_rt,
					MoneyUtil.getFractionSellUnitMode());
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
//			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())){
//				priceVal[3] = ctu.getTaxIn().doubleValue();
//			}else{
//				priceVal[3] = ctu.getTaxOut().doubleValue();
//			}
			if(zei_kb == Integer.parseInt(mst011701_KazeiKbDictionary.UTIZEI.getCode())){
				priceVal[3] = ctu.getTaxOut().doubleValue();
			}else{
				priceVal[3] = ctu.getTaxIn().doubleValue();
			}
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
		}
// #5518 Add 2017.06.29 S.Takayama (E)
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
		//卸売価単価（税込）計算
		BigDecimal oroshiNukipriceVal =new BigDecimal(priceVal[5]);
		int orosi_zei_kb =0;
		BigDecimal orosi_tax_rt = null;

		if(!isBlank(mOZeiKb) && !isBlank(mOTaxRt)){
			orosi_zei_kb = Integer.parseInt(mOZeiKb);
			orosi_tax_rt = new BigDecimal(mOTaxRt);
		}else{
			orosi_zei_kb = Integer.parseInt(zeiKb);
			orosi_tax_rt = new BigDecimal(taxRt);
			mOZeiKb = zeiKb;
		}
		if(mOZeiKb.equals("1")){
			orosi_zei_kb = 2;
		}
		if(!oroshiNukipriceVal.equals("")){
			CalculateTaxUtility ctu = new CalculateTaxUtility(oroshiNukipriceVal,
					MoneyUtil.getFractionCostUnitLen(), orosi_zei_kb, orosi_tax_rt,
					MoneyUtil.getFractionCostUnitMode());
			if(("1").equals(mOZeiKb)){
				priceVal[4] = ctu.getTaxIn().doubleValue();
			}else{
				priceVal[4] = ctu.getTaxOut().doubleValue();
			}
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
			priceVal[4] = round(priceVal[4],0);
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
		}
//2016/08/04 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.22 #2800 nv.cuong(S)
		//卸税込売価(小売税適用)計算
		// add 2016.11.22 #2800 nv.cuong(S)
		BigDecimal kouripriceVal =new BigDecimal(priceVal[5]);
		// add 2016.11.22 #2800 nv.cuong(E)
		int kouri_sei_kb = Integer.parseInt(zeiKb);
		if("1".equals(zeiKb)){
			kouri_sei_kb = 2;
		}
		if(!kouripriceVal.equals("")){
			CalculateTaxUtility ctu = new CalculateTaxUtility(kouripriceVal,
					MoneyUtil.getFractionCostUnitLen(), kouri_sei_kb, new BigDecimal(taxRt),
					MoneyUtil.getFractionCostUnitMode());
			if(("1").equals(zeiKb)){
				priceVal[6] = ctu.getTaxIn().doubleValue();
			}else{
				priceVal[6] = ctu.getTaxOut().doubleValue();
			}
		}
		// add 2016.11.22 #2800 nv.cuong(E)
		
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
		priceVal[3] = round(priceVal[3],0);
		priceVal[6] = round(priceVal[6],0);
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
		
		// 2017.10.04 M.Akagi #5994 (S)
		// 原単価（税抜）の桁数チェック
		if (priceVal[2] > GENKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "原単価(税抜)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00075",userLocal)));
			tankaChkFlg = true;
			priceVal[2] = GENKA_MAX;
		}
		// 原単価（税込）の桁数チェック
		if (priceVal[0] > GENKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "原単価(税込)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00038",userLocal)));
			tankaChkFlg = true;
			priceVal[0] = GENKA_MAX;
		}
		// 売単価（税込）の桁数チェック
		if (priceVal[1] > BAIKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "売単価(税込)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00036",userLocal)));
			tankaChkFlg = true;
			priceVal[1] = BAIKA_MAX;
		}
		// 卸売単価（税抜）
		if (priceVal[5] > BAIKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "卸売単価(税抜)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00076",userLocal)));
			tankaChkFlg = true;
			priceVal[5] = BAIKA_MAX;
		}
		// 卸売単価（卸税適用）
		if (priceVal[4] > BAIKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "卸売単価(卸税適用)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00096",userLocal)));
			tankaChkFlg = true;
			priceVal[4] = BAIKA_MAX;
		}
		// 卸売単価（小売税適用）
		if (priceVal[6] > BAIKA_MAX) {
			//errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, "卸売単価(小売税適用)"));
			errmsg.add(MessageUtil.getMessage("COMMONM_MSG_00201",userLocal, MessageUtil.getMessage("MST01018_TXT_00097",userLocal)));
			tankaChkFlg = true;
			priceVal[6] = BAIKA_MAX;
		}
		// 2017.10.04 M.Akagi #5994 (E)

		return priceVal;
	}

	/**
	 * 仕入先コードとメーカーコード(仕入先管理コード)の整合性チェック処理
	 * @param	siiresakiCd 仕入先コード
	 * @param  Siiresakikanricd 仕入先管理コード
	 */
	private boolean siiresakicdCheck(String siiresakiCd, String yukoDt) throws SQLException{

		StringBuffer strSql = null;
		ResultSet recCnt;
		boolean checkFg = false;

		// 仕入先コードが登録されていた場合は存在チェックを行う
		if(!isBlank(siiresakiCd)){

			if (psSiiresakCheck == null) {

				strSql = new StringBuffer();
				strSql.append("SELECT * ");
				strSql.append("  FROM R_TORIHIKISAKI S");
				strSql.append(" WHERE COMP_CD            = '").append("0000").append("' ");
				strSql.append("   AND CHOAI_KB           = '").append("1").append("' ");
				strSql.append("   AND TORIHIKISAKI_CD    = ? ");
				strSql.append("   AND DELETE_FG          = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
				strSql.append("   AND TORIHIKI_TEISHI_KB = '").append("0").append("' ");
				strSql.append("   AND TORIKESHI_FG       = '").append("0").append("' ");
				strSql.append("   AND TEKIYO_START_DT    = ");
				strSql.append("       (SELECT MAX(TEKIYO_START_DT) ");
				strSql.append("          FROM R_TORIHIKISAKI ");
				strSql.append("         WHERE COMP_CD          = S.COMP_CD ");
				strSql.append("           AND CHOAI_KB         = S.CHOAI_KB ");
				strSql.append("           AND TORIHIKISAKI_CD  = S.TORIHIKISAKI_CD ");
				strSql.append("           AND TORIKESHI_FG     = '").append("0").append("' ");
				strSql.append("           AND TEKIYO_START_DT <= ? ");
				strSql.append("       )");
				psSiiresakCheck = db.getPrepareStatement(strSql.toString());
			}

			psSiiresakCheck.setString(1, siiresakiCd);
			psSiiresakCheck.setString(2, yukoDt);
			recCnt = psSiiresakCheck.executeQuery();

			//該当データが存在しなかった場合
			if (!recCnt.next()) {
//					errmsg.add("仕入先コードが仕入先マスタに存在しないため、更新しませんでした。");
				errmsg.add(msgMap.get("0130"));
				checkFg = true;
			}
			db.closeResultSet(recCnt);
		}

		return checkFg;
	}

	/**
	 * 更新データ取得SQL生成(R_SYOHIN)
	 * @param rs 修正のデータ
	 * @param price 単価
	 * @param yukoDt
	 * @throws
	 */
	//2016.10.13 T.han #2256対応（S)
	//private String setUpdateSyohin(ResultSet rs, double[] price, String yukoDt) throws SQLException
	// 2017/01/19 T.Arimoto #3608対応（S)
	//private String setUpdateSyohin(ResultSet rs, double[] price, String yukoDt, HashMap oSyohin) throws SQLException
	private String setUpdateSyohin(ResultSet rs, double[] price, String yukoDt, HashMap oSyohin, BigDecimal orosiBaitankaVl) throws SQLException
	// 2017/01/19 T.Arimoto #3608対応（E)
	//2016.10.13 T.han #2256対応（E)
	{
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (S)
		//2016.10.13 T.han #2256対応（S)
//		Double old_gentanka_nuki_vl = Double.parseDouble((String) oSyohin.get("gentanka_nuki_vl"));						// 前回の原価単価（税抜）
//		String old_cur_generation_yuko_dt = (String) oSyohin.get("cur_generation_yuko_dt");								// 前回の有効日(現世代)
//		Double old_cur_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("cur_generation_gentanka_vl"));	// 前回の原価単価(現世代)
//		String old_one_generation_yuko_dt = (String) oSyohin.get("one_generation_yuko_dt");								// 前回の有効日(1世代前)
//		Double old_one_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("one_generation_gentanka_vl"));	// 前回の原価単価(1世代前)
//		String old_two_generation_yuko_dt = (String) oSyohin.get("two_generation_yuko_dt");								// 前回の有効日(2世代前)
//		Double old_two_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("two_generation_gentanka_vl"));	// 前回の原価単価(2世代前)
		//2016.10.13 T.han #2256対応（E)

		// 2017/02/02 T.Arimoto #3829対応（S)
//		String old_gentanka_nuki_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("gentanka_nuki_vl"));						// 前回の原価単価（税抜）
//		String old_cur_generation_yuko_dt = (String) oSyohin.get("cur_generation_yuko_dt");								// 前回の有効日(現世代)
//		String old_cur_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("cur_generation_gentanka_vl"));	// 前回の原価単価(現世代)
//		String old_one_generation_yuko_dt = (String) oSyohin.get("one_generation_yuko_dt");								// 前回の有効日(1世代前)
//		String old_one_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("one_generation_gentanka_vl"));	// 前回の原価単価(1世代前)
//		String old_two_generation_yuko_dt = (String) oSyohin.get("two_generation_yuko_dt");								// 前回の有効日(2世代前)
//		String old_two_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("two_generation_gentanka_vl"));	// 前回の原価単価(2世代前)
		String old_gentanka_nuki_vl = "";
		String old_cur_generation_yuko_dt = "";
		String old_cur_generation_gentanka_vl = "";
		String old_one_generation_yuko_dt = "";
		String old_one_generation_gentanka_vl = "";
		String old_two_generation_yuko_dt = "";
		String old_two_generation_gentanka_vl = "";
		if( null != oSyohin){
			old_gentanka_nuki_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("gentanka_nuki_vl"));						// 前回の原価単価（税抜）
			// 2017/02/02 T.Arimoto #3829対応（S)
			//old_cur_generation_yuko_dt = (String) oSyohin.get("cur_generation_yuko_dt");								// 前回の有効日(現世代)
			old_cur_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin.get("cur_generation_yuko_dt"));			// 前回の有効日(現世代)
			// 2017/02/02 T.Arimoto #3829対応（E)
			old_cur_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("cur_generation_gentanka_vl"));	// 前回の原価単価(現世代)
			// 2017/02/02 T.Arimoto #3829対応（S)
			//old_one_generation_yuko_dt = (String) oSyohin.get("one_generation_yuko_dt");								// 前回の有効日(1世代前)
			old_one_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin.get("one_generation_yuko_dt"));			// 前回の有効日(1世代前)
			// 2017/02/02 T.Arimoto #3829対応（E)
			old_one_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("one_generation_gentanka_vl"));	// 前回の原価単価(1世代前)
			// 2017/02/02 T.Arimoto #3829対応（S)
			//old_two_generation_yuko_dt = (String) oSyohin.get("two_generation_yuko_dt");								// 前回の有効日(2世代前)
			old_two_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin.get("two_generation_yuko_dt"));			// 前回の有効日(2世代前)
			// 2017/02/02 T.Arimoto #3829対応（E)
			old_two_generation_gentanka_vl = mst000401_LogicBean.chkNullString((String) oSyohin.get("two_generation_gentanka_vl"));	// 前回の原価単価(2世代前)
		}
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (E)
//2016/08/03 VINX h.sakamoto #1899対応 (S)
//			String[] syuseikoku = new String[12]; //修正項目
		String[] syuseikoku = new String[13]; //修正項目
//2016/08/03 VINX h.sakamoto #1899対応 (E)
		syuseikoku[0] = rs.getString("BUNRUI1_CD"); //部門コード
		syuseikoku[1] = rs.getString("syohin_cd"); //商品コード
		syuseikoku[2] = rs.getString("siiresaki_cd"); //仕入先コード
		syuseikoku[3] = rs.getString("hanbai_st_dt"); //販売開始日
		syuseikoku[4] = rs.getString("hanbai_ed_dt"); //販売終了日
		syuseikoku[5] = rs.getString("ten_siiresaki_kanri_cd"); //店別仕入先管理コード
		syuseikoku[6] = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
		syuseikoku[7] = rs.getString("ten_hachu_ed_dt"); //店舗発注終了日

		syuseikoku[8] = rs.getString("eos_kb"); //EOS区分
		syuseikoku[9] = rs.getString("BUNRUI5_CD"); //ユニットコード
		syuseikoku[10] = yukoDt; //有効日
		syuseikoku[11] = rs.getString("buturyu_kb"); //物流区分
		
		// add 2016.11.30 #2800 nv.cuong(S)
		syuseikoku[12] = rs.getString("plu_send_dt"); //PLU送信日
		// add 2016.11.30 #2800 nv.cuong(S)
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append("    r_syohin");
		sql.append(" set");
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append("    YUKO_DT = " + yukoDt + ",");
		
		if (!nullToEmpty(syuseikoku[12]).equals(""))
		{
			sql.append("    PLU_SEND_DT = " + syuseikoku[12] + ",");
		}
		
		// add 2016.11.30 #2800 nv.cuong(E)
		
		if (price[0] > 0)
		{
			sql.append("    gentanka_vl = " + price[0] + ",");
		}
		if (price[1] > 0)
		{
			//sql.append("    baitanka_vl = " + (long) (price[1]) + ",");
			sql.append("    baitanka_vl = " + (price[1]) + ",");
		}
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
		if (price[2] > 0)
		{
			sql.append("    gentanka_nuki_vl = " + price[2] + ",");
		}
		if (price[3] > 0)
		{
			//sql.append("    baitanka_nuki_vl = " + (long) (price[3]) + ",");
			sql.append("    baitanka_nuki_vl = " + (price[3]) + ",");
		}
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
//2016/08/05 VINX h.sakamoto #1899対応 (S)
		// 2017/01/19 T.Arimoto #3608対応（S)
		if( null == orosiBaitankaVl){
			sql.append("    orosi_baitanka_vl = null, ");
		} else {
		// 2017/01/19 T.Arimoto #3608対応（E)
			if (price[4] > 0)
			{
				sql.append("    orosi_baitanka_vl = " + price[4] + ",");
			}
		// 2017/01/19 T.Arimoto #3608対応（S)
		}
		if( null == orosiBaitankaVl){
			sql.append("    orosi_baitanka_nuki_vl = null, ");
		} else {
		// 2017/01/19 T.Arimoto #3608対応（E)
			if (price[5] > 0)
			{
				sql.append("    orosi_baitanka_nuki_vl = " + (price[5]) + ",");
			}
		// 2017/01/19 T.Arimoto #3608対応（S)
		}
		if( null == orosiBaitankaVl){
			sql.append("   OROSI_BAITANKA_VL_KOURI = null, ");
		} else {
		// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/05 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.22 #2800 nv.cuong(S)
			if (price[6] > 0)
			{
				sql.append("   OROSI_BAITANKA_VL_KOURI = " + (price[6]) + ",");
			}
		// 2017/01/19 T.Arimoto #3608対応（S)
		}
		// 2017/01/19 T.Arimoto #3608対応（E)
		// add 2016.11.22 #2800 nv.cuong(E)
		
		if (nullToEmpty(syuseikoku[2]).equals(""))
		{
		}
		else
		{
			sql.append("    siiresaki_cd = '" + syuseikoku[2] + "',");
		}
		if (nullToEmpty(syuseikoku[3]).equals(""))
		{
		}
		else
		{
			sql.append("    hanbai_st_dt = '" + syuseikoku[3] + "',");
		}
		if (nullToEmpty(syuseikoku[4]).equals(""))
		{
		}
		else
		{
			sql.append("    hanbai_ed_dt = '" + syuseikoku[4] + "',");
		}
		if (nullToEmpty(syuseikoku[5]).equals(""))
		{
		}
		else
		{
			sql.append("    ten_siiresaki_kanri_cd = '" + syuseikoku[5] + "',");
		}
		if (nullToEmpty(syuseikoku[6]).equals(""))
		{
		}
		else
		{
			sql.append("    ten_hachu_st_dt = '" + syuseikoku[6] + "',");
		}
		if (nullToEmpty(syuseikoku[7]).equals(""))
		{
		}
		else
		{
			sql.append("    ten_hachu_ed_dt = '" + syuseikoku[7] + "',");
		}
		if (nullToEmpty(syuseikoku[8]).equals(""))
		{
		}
		else
		{
			sql.append("    eos_kb = '" + syuseikoku[8] + "',");
		}
		if (nullToEmpty(syuseikoku[9]).equals(""))
		{
		}
		else
		{
			sql.append("    BUNRUI5_CD = '" + syuseikoku[9] + "',");
		}
		if (nullToEmpty(rs.getString("syokai_user_id")).equals(""))
		{
		}
		else
		{
			sql.append("    syokai_user_id = '" + rs.getString("syokai_user_id") + "',");
		}
		if (nullToEmpty(syuseikoku[11]).equals(""))
		{
		}
		else
		{
			sql.append("    buturyu_kb = '" + syuseikoku[11] + "',");
		}
		sql.append("    update_ts = '" + sysDt + "',");
		sql.append("    update_user_id = '").append(rs.getString("toroku_user_id")).append("',");
		sql.append("    delete_fg = '0', ");
		sql.append("    henko_dt = '" + batchDt + "', ");
		sql.append("    BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");	// バッチ更新年月日
		sql.append("    BATCH_UPDATE_ID = '" + BATCH_ID + "' ");															// バッチ更新者ID
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(S)
		// 2016.10.13 T.han #2256対応（S)
		//sql.append("    , CUR_GENERATION_YUKO_DT 	 = YUKO_DT ");
		//sql.append("    , CUR_GENERATION_GENTANKA_VL = '").append(price[2]).append("' ");
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (S)
//		if (price[2] == old_gentanka_nuki_vl) {
		// 2016/12/06 T.Arimoto #3148対応（S)
		//if (old_gentanka_nuki_vl.equals(String.valueOf(price[2]))) { 
		if ( StringUtility.decimalCompare(old_gentanka_nuki_vl,String.valueOf(price[2])) == 0 ) { // 1 と 1.0を比較して異なると判定してしまう不具合を修正
			// 2016/12/06 T.Arimoto #3148対応（E)
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (E)
			sql.append("    , CUR_GENERATION_YUKO_DT 	 = '" + old_cur_generation_yuko_dt + "' ");
			sql.append("    , CUR_GENERATION_GENTANKA_VL = '" + old_cur_generation_gentanka_vl + "' ");
			sql.append("    , ONE_GENERATION_YUKO_DT 	 = '" + old_one_generation_yuko_dt + "' ");
			sql.append("    , ONE_GENERATION_GENTANKA_VL = '" + old_one_generation_gentanka_vl + "' ");
			sql.append("    , TWO_GENERATION_YUKO_DT 	 = '" + old_two_generation_yuko_dt + "' ");
			sql.append("    , TWO_GENERATION_GENTANKA_VL = '" + old_two_generation_gentanka_vl + "' ");
		} else {
			sql.append("    , CUR_GENERATION_YUKO_DT 	 = YUKO_DT ");
			sql.append("    , CUR_GENERATION_GENTANKA_VL = '").append(price[2]).append("' ");
			sql.append("    , ONE_GENERATION_YUKO_DT 	 = '" + old_cur_generation_yuko_dt + "' ");
			sql.append("    , ONE_GENERATION_GENTANKA_VL = '" + old_cur_generation_gentanka_vl + "' ");
			sql.append("    , TWO_GENERATION_YUKO_DT 	 = '" + old_one_generation_yuko_dt + "' ");
			sql.append("    , TWO_GENERATION_GENTANKA_VL = '" + old_one_generation_gentanka_vl + "' ");
		}
		// 2016.10.13 T.han #2256対応（E)
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(E)
		sql.append(" where BUNRUI1_CD = '" + syuseikoku[0] + "' and ");
		sql.append("       syohin_cd = '" + syuseikoku[1] + "' and ");
		sql.append("       yuko_dt = '" + syuseikoku[10] + "'");

		return sql.toString();

	}

	// 2016.10.13 T.han #2256対応（S)
	/**
	 * 引続き更新データ取得SQL生成(R_SYOHIN)
	 * @param rs 修正のデータ
	 * @param price 単価
	 * @param yukoDt
	 * @throws
	 */
	private String setUpdateSyohinContinue(HashMap mSyohin, HashMap oSyohin) throws SQLException
	{

		String bunrui_cd = (String) mSyohin.get("BUNRUI1_CD"); //部門コード
		String syohin_cd = (String) mSyohin.get("syohin_cd");  //商品コード
		String yukoDt    = (String) mSyohin.get("yuko_dt");    //有効日
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (S)
//		Double old_gentanka_nuki_vl = Double.parseDouble((String) oSyohin.get("gentanka_nuki_vl"));						// 前回の原価単価（税抜）
//		String old_cur_generation_yuko_dt = (String) oSyohin.get("cur_generation_yuko_dt");								// 前回の有効日(現世代)
//		Double old_cur_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("cur_generation_gentanka_vl"));	// 前回の原価単価(現世代)
//		String old_one_generation_yuko_dt = (String) oSyohin.get("one_generation_yuko_dt");								// 前回の有効日(1世代前)
//		Double old_one_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("one_generation_gentanka_vl"));	// 前回の原価単価(1世代前)
//		String old_two_generation_yuko_dt = (String) oSyohin.get("two_generation_yuko_dt");								// 前回の有効日(2世代前)
//		Double old_two_generation_gentanka_vl = Double.parseDouble((String) oSyohin.get("two_generation_gentanka_vl"));	// 前回の原価単価(2世代前)
//		Double gentanka_nuki_vl = Double.parseDouble((String) mSyohin.get("gentanka_nuki_vl"));							// 今回の原価単価（税抜）
		String old_gentanka_nuki_vl = mst000401_LogicBean
				.chkNullString((String) oSyohin.get("gentanka_nuki_vl")); // 前回の原価単価（税抜）
		// 2017/02/02 T.Arimoto #3829対応（S)
		//String old_cur_generation_yuko_dt = (String) oSyohin
		//		.get("cur_generation_yuko_dt"); // 前回の有効日(現世代)
		String old_cur_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin
				.get("cur_generation_yuko_dt")); // 前回の有効日(現世代)
		// 2017/02/02 T.Arimoto #3829対応（E)
		String old_cur_generation_gentanka_vl = mst000401_LogicBean
				.chkNullString((String) oSyohin
						.get("cur_generation_gentanka_vl")); // 前回の原価単価(現世代)
		// 2017/02/02 T.Arimoto #3829対応（S)
		//String old_one_generation_yuko_dt = (String) oSyohin
		//		.get("one_generation_yuko_dt"); // 前回の有効日(1世代前)
		String old_one_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin
				.get("one_generation_yuko_dt")); // 前回の有効日(1世代前)
		// 2017/02/02 T.Arimoto #3829対応（E)
		String old_one_generation_gentanka_vl = mst000401_LogicBean
				.chkNullString((String) oSyohin
						.get("one_generation_gentanka_vl")); // 前回の原価単価(1世代前)
		// 2017/02/02 T.Arimoto #3829対応（S)
		//String old_two_generation_yuko_dt = (String) oSyohin
		//		.get("two_generation_yuko_dt"); // 前回の有効日(2世代前)
		String old_two_generation_yuko_dt = mst000401_LogicBean.chkNullString((String) oSyohin
				.get("two_generation_yuko_dt")); // 前回の有効日(2世代前)
		// 2017/02/02 T.Arimoto #3829対応（E)
		String old_two_generation_gentanka_vl = mst000401_LogicBean
				.chkNullString((String) oSyohin
						.get("two_generation_gentanka_vl")); // 前回の原価単価(2世代前)
		String gentanka_nuki_vl = mst000401_LogicBean
				.chkNullString((String) mSyohin.get("gentanka_nuki_vl"));					// 今回の原価単価（税抜）
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (E)
		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append("    r_syohin");
		sql.append(" set");
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (S)
//		if (gentanka_nuki_vl.doubleValue() == old_gentanka_nuki_vl.doubleValue()) {
		if (gentanka_nuki_vl.equals(old_gentanka_nuki_vl)) {
//2016/12/01 VINX Mod Li.Sheng #3086 対応 (E)
			sql.append("      CUR_GENERATION_YUKO_DT 	 = '" + old_cur_generation_yuko_dt + "' ");
			sql.append("    , CUR_GENERATION_GENTANKA_VL = '" + old_cur_generation_gentanka_vl + "' ");
			sql.append("    , ONE_GENERATION_YUKO_DT 	 = '" + old_one_generation_yuko_dt + "' ");
			sql.append("    , ONE_GENERATION_GENTANKA_VL = '" + old_one_generation_gentanka_vl + "' ");
			sql.append("    , TWO_GENERATION_YUKO_DT 	 = '" + old_two_generation_yuko_dt + "' ");
			sql.append("    , TWO_GENERATION_GENTANKA_VL = '" + old_two_generation_gentanka_vl + "' ");
		} else {
			sql.append("      CUR_GENERATION_YUKO_DT 	 = '" + yukoDt + "' ");
			sql.append("    , ONE_GENERATION_YUKO_DT 	 = '" + old_cur_generation_yuko_dt + "' ");
			sql.append("    , ONE_GENERATION_GENTANKA_VL = '" + old_cur_generation_gentanka_vl + "' ");
			sql.append("    , TWO_GENERATION_YUKO_DT 	 = '" + old_one_generation_yuko_dt + "' ");
			sql.append("    , TWO_GENERATION_GENTANKA_VL = '" + old_one_generation_gentanka_vl + "' ");
		}
		sql.append(" where BUNRUI1_CD = '" + bunrui_cd + "' and ");
		sql.append("       syohin_cd = '" + syohin_cd + "' and ");
		sql.append("       yuko_dt = '" + yukoDt + "'");

		return sql.toString();

	}
	// 2016.10.13 T.han #2256対応（E)

	/**
	 * 削除SQL生成(R_SYOHIN)
	 * @param rs 削除のデータ
	 * @param yukoDt
	 * @throws
	 */
	private String setDeleteSyohin(ResultSet rs,  String yukoDt) throws SQLException
	{

		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append("    r_syohin");
		sql.append(" set");
		sql.append("    delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "',");
		sql.append("    update_ts = '" + sysDt + "',");
		sql.append("    update_user_id = '").append(rs.getString("toroku_user_id")).append("',");
		sql.append("    henko_dt = '" + batchDt + "', ");
		sql.append("    BATCH_UPDATE_TS = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");	// バッチ更新年月日
		sql.append("    BATCH_UPDATE_ID = '" + BATCH_ID + "' ");															// バッチ更新者ID
		// 2017.04.03 M.Akagi #4509 (S)
		sql.append("    ,PLU_HANEI_TIME = NULL ");
		sql.append("    ,EMG_FLAG = '" + mst910020_EmgFlagDictionary.OFF.getCode() + "' ");
		// 2017.04.03 M.Akagi #4509 (E)
		sql.append(" where BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' and ");
		sql.append("       syohin_cd = '" + rs.getString("syohin_cd") + "' and ");
		sql.append("       yuko_dt = '" + yukoDt + "'");

		return sql.toString();

	}

	// 2015/06/20 DAI.BQ (S)
	/**
	 * 削除SQL生成(R_SYOHIN_ASN)
	 * @param rs 削除のデータ
	 * @param yukoDt
	 * @throws
	 */
	private String setDeleteSyohinASN(ResultSet rs,  String yukoDt) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append("    R_SYOHIN_ASN");
		sql.append(" set");
		sql.append("    delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "',");
		sql.append("    update_ts = '" + sysDt + "',");
		sql.append("    update_user_id = '").append(rs.getString("toroku_user_id") + "'");
		sql.append(" where BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' and ");
		sql.append("       syohin_cd = '" + rs.getString("syohin_cd") + "' and ");
		sql.append("       yuko_dt = '" + yukoDt + "'");

		return sql.toString();
	}
	// 2015/06/20 DAI.BQ (E)

	/**
	 * 削除登録SQL生成(R_SYOHIN)
	 * @param rs 削除のデータ
	 * @param yukoDt
	 * @throws
	 */
	private String getInsertDelSql(ResultSet rs,  String oldYukoDt) throws SQLException
	{

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");							// 分類１コード
		sql.append("    SYOHIN_CD, ");							// 商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (S)
		sql.append("    OLD_SYOHIN_CD, ");						// 旧商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (E)
		sql.append("    YUKO_DT, ");							// 有効日
		sql.append("    SYSTEM_KB, ");							// システム区分
		sql.append("    GYOSYU_KB, ");							// 業種区分
		sql.append("    SYOHIN_KB, ");							// 商品区分
		sql.append("    KETASU_KB, ");							// 桁数区分
		sql.append("    BUNRUI2_CD, ");							// 分類２コード
		sql.append("    BUNRUI5_CD, ");							// 分類５コード
		sql.append("    HINMOKU_CD, ");							// 品目
		sql.append("    SYOHIN_2_CD, ");						// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");					// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");					// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");							// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");					// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");					// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");					// 漢字規格２
		sql.append("    REC_HINMEI_KANJI_NA, ");				// POSレシート品名(漢字)
		sql.append("    HINMEI_KANA_NA, ");						// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");						// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");					// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");					// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");					// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");					// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");					// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");							// Eショップ区分
		sql.append("    PB_KB, ");								// PB区分
		sql.append("    SUBCLASS_CD, ");						// サブクラスコード
		sql.append("    HAIFU_CD, ");							// 配布コード
		sql.append("    ZEI_KB, ");								// 税区分
		sql.append("    TAX_RATE_KB, ");						// 税率区分
		sql.append("    GENTANKA_VL, ");						// 原価単価
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
		sql.append("    GENTANKA_NUKI_VL, ");					// 原価単価（税抜）
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
		sql.append("    BAITANKA_VL, ");						// 売価単価
		sql.append("    TOSYO_BAIKA_VL, ");						// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");					// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");					// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");					// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");				// メーカー希望小売り価格
		sql.append("    SIIRESAKI_CD, ");						// 仕入先コード
		sql.append("    DAIHYO_HAISO_CD, ");					// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");				// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");					// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");					// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");					// 発注商品コード
		sql.append("    EOS_KB, ");								// EOS区分
		sql.append("    HACHU_TANI_NA, ");						// 発注単位呼称
		sql.append("    HANBAI_TANI, ");						// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");					// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");					// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");						// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");						// バラ入数
		sql.append("    TEN_HACHU_ST_DT, ");					// 店舗発注開始日
		sql.append("    TEN_HACHU_ED_DT, ");					// 店舗発注終了日
		sql.append("    HANBAI_ST_DT, ");						// 販売開始日
		sql.append("    HANBAI_ED_DT, ");						// 販売終了日
		sql.append("    HANBAI_KIKAN_KB, ");					// 販売期間区分
		sql.append("    TEIKAN_KB, ");							// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");						// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");						// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");						// 納品期限
		sql.append("    BIN_1_KB, ");							// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");					// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");						// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");					// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");				// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");				// ①店納品時間帯
		sql.append("    BIN_2_KB, ");							// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");					// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");						// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");					// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");				// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");				// ②店納品時間帯
		sql.append("    BIN_3_KB, ");							// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");					// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");						// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");					// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");				// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");				// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");					// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");						// 優先便区分
		sql.append("    F_BIN_KB, ");							// F便区分
		sql.append("    BUTURYU_KB, ");							// 物流区分
		sql.append("    GOT_MUJYOKEN_FG, ");					// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");						// 季節開始月日
		sql.append("    GOT_END_MM, ");							// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");						// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");					// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");					// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");					// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");					// 入荷時商品コード２
		sql.append("    ITF_CD, ");								// ITFコード
		sql.append("    GTIN_CD, ");							// GTINコード
		sql.append("    VENDER_MAKER_CD, ");					// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");					// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");					// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");					// センター重量
		sql.append("    PACK_WIDTH_QT, ");						// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");						// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");						// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");						// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");				// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");				// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");				// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");					// 最低入数
		sql.append("    CASE_IRISU_QT, ");						// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");					// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");						// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");						// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");					// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");					// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");					// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");					// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");					// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");				// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");				// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");						// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");						// TC情報
		sql.append("    NOHIN_ONDO_KB, ");						// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");						// 横もち区分
		sql.append("    SHINAZOROE_KB, ");						// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");						// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");						// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");					// 販売政策区分
		sql.append("    HENPIN_NB, ");							// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");					// 返品原価
		sql.append("    CGC_HENPIN_KB, ");						// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");					// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");					// 販売期限
		sql.append("    PLU_SEND_DT, ");						// PLU送信日
		sql.append("    KEYPLU_FG, ");							// キーPLU対象
		sql.append("    PLU_KB, ");								// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");					// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");					// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");					// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");						// 消札売価
		sql.append("    YORIDORI_KB, ");						// よりどり種類
		sql.append("    YORIDORI_VL, ");						// よりどり価格
		sql.append("    YORIDORI_QT, ");						// よりどり数量
		sql.append("    BLAND_CD, ");							// ブランドコード
		sql.append("    SEASON_CD, ");							// シーズンコード
		sql.append("    HUKUSYU_CD, ");							// 服種コード
		sql.append("    STYLE_CD, ");							// スタイルコード
		sql.append("    SCENE_CD, ");							// シーンコード
		sql.append("    SEX_CD, ");								// 性別コード
		sql.append("    AGE_CD, ");								// 年代コード
		sql.append("    GENERATION_CD, ");						// 世代コード
		sql.append("    SOZAI_CD, ");							// 素材コード
		sql.append("    PATTERN_CD, ");							// パターンコード
		sql.append("    ORIAMI_CD, ");							// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");						// 付加機能コード
		sql.append("    SODE_CD, ");							// 袖丈コード
		sql.append("    SIZE_CD, ");							// サイズコード
		sql.append("    COLOR_CD, ");							// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");						// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");					// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");						// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");						// 契約終了期間
		sql.append("    KUMISU_KB, ");							// 組数区分
		sql.append("    NEFUDA_KB, ");							// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");				// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");				// 値札受渡方法
		sql.append("    PC_KB, ");								// PC発行区分
		sql.append("    DAISI_NO_NB, ");						// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");					// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");				// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");			// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");						// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");						// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");					// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");					// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");				// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");							// 棚NO
		sql.append("    DAN_NO_NB, ");							// 段NO
		sql.append("    REBATE_FG, ");							// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");						// マークグループ
		sql.append("    MARK_CD, ");							// マークコード
		sql.append("    YUNYUHIN_KB, ");						// 輸入品区分
		sql.append("    SANTI_CD, ");							// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");						// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");						// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");						// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");						// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");						// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");						// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");						// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");						// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");						// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");						// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");						// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");						// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");						// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");						// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");						// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");						// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");						// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");						// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");					// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");					// 小属性１０
		sql.append("    FUJI_SYOHIN_KB, ");						// F商品区分
		sql.append("    COMMENT_TX, ");							// コメント
		sql.append("    AUTO_DEL_KB, ");						// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");					// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");							// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");					// 新規登録日
		sql.append("    HENKO_DT, ");							// 変更日
		sql.append("    SYOKAI_TOROKU_TS, ");					// 初回登録日
		sql.append("    SYOKAI_USER_ID, ");						// 初回登録社員ID
		sql.append("    INSERT_TS, ");							// 作成年月日
		sql.append("    INSERT_USER_ID, ");						// 作成者ID
		sql.append("    UPDATE_TS, ");							// 更新年月日
		sql.append("    UPDATE_USER_ID, ");						// 更新者ID
		sql.append("    BATCH_UPDATE_TS, ");					// バッチ更新年月日
		sql.append("    BATCH_UPDATE_ID, ");					// バッチ更新者ID
		sql.append("    DELETE_FG ");							// 削除フラグ
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT (S)
		sql.append("    , CUR_GENERATION_YUKO_DT ");			// 有効日(現世代)
		sql.append("    , CUR_GENERATION_GENTANKA_VL ");		// 原価単価(現世代)
		sql.append("    , ONE_GENERATION_YUKO_DT ");			// 有効日(1世代前)
		sql.append("    , ONE_GENERATION_GENTANKA_VL ");		// 原価単価(1世代前)
		sql.append("    , TWO_GENERATION_YUKO_DT ");			// 有効日(2世代前)
		sql.append("    , TWO_GENERATION_GENTANKA_VL ");		// 原価単価(2世代前)
		
		// add 2016.11.31 #2800 nv.cuong(S)
		sql.append("    , MIN_HACHU_QT ");						// 最低発注数
		sql.append("    , HACHU_FUKA_FLG ");					// 発注不可フラグ
		sql.append("    , PER_TXT ");							// 規格内容
		// add 2016.11.31 #2800 nv.cuong(E)
		
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT (E)
		// 2016/08/03 VINX h.sakamoto #1899対応 (S)
		sql.append("    , SIIRE_ZEI_KB ");						// 仕入税区分
		sql.append("    , SIIRE_TAX_RATE_KB ");					// 仕入税率区分
		sql.append("    , OROSI_ZEI_KB ");						// 卸税区分
		sql.append("    , OROSI_TAX_RATE_KB ");					// 卸税率区分
		sql.append("    , OROSI_BAITANKA_VL ");					// 卸売価単価
		sql.append("    , OROSI_BAITANKA_NUKI_VL ");			// 卸売価単価（税抜）
		// 2016/08/03 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append("    , SYOHI_KIGEN_HYOJI_PATTER ");			// 消費期限表示パターン
		sql.append("    , PLU_HANEI_TIME ");					// PLU反映時間
		// add 2016.11.30 #2800 nv.cuong(E)
		// 2017.04.03 M.Akagi #4509 (S)
		sql.append("    , EMG_FLAG ");					// 緊急配信フラグ
		// 2017.04.03 M.Akagi #4509 (E)
		// add 2016.11.22 #2800 nv.cuong(S)
		sql.append("    , OROSI_BAITANKA_VL_KOURI ");			// 卸売価単価(小売税)
		// add 2016.11.22 #2800 nv.cuong(E)
		
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");							// 分類１コード
		sql.append("    SYOHIN_CD, ");							// 商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (S)
		sql.append("    OLD_SYOHIN_CD, ");						// 旧商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (E)
		sql.append("    '" + rs.getString("YUKO_DT") + "', ");	// 有効日
		sql.append("    SYSTEM_KB, ");							// システム区分
		sql.append("    GYOSYU_KB, ");							// 業種区分
		sql.append("    SYOHIN_KB, ");							// 商品区分
		sql.append("    KETASU_KB, ");							// 桁数区分
		sql.append("    BUNRUI2_CD, ");							// 分類２コード
		sql.append("    BUNRUI5_CD, ");							// 分類５コード
		sql.append("    HINMOKU_CD, ");							// 品目
		sql.append("    SYOHIN_2_CD, ");						// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");					// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");					// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");							// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");					// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");					// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");					// 漢字規格２
		sql.append("    REC_HINMEI_KANJI_NA, ");				// POSレシート品名(漢字)
		sql.append("    HINMEI_KANA_NA, ");						// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");						// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");					// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");					// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");					// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");					// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");					// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");							// Eショップ区分
		sql.append("    PB_KB, ");								// PB区分
		sql.append("    SUBCLASS_CD, ");						// サブクラスコード
		sql.append("    HAIFU_CD, ");							// 配布コード
		sql.append("    ZEI_KB, ");								// 税区分
		sql.append("    TAX_RATE_KB, ");						// 税率区分
		sql.append("    GENTANKA_VL, ");						// 原価単価
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
		sql.append("    GENTANKA_NUKI_VL, ");					// 原価単価（税抜）
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
		sql.append("    BAITANKA_VL, ");						// 売価単価
		sql.append("    TOSYO_BAIKA_VL, ");						// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");					// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");					// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");					// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");				// メーカー希望小売り価格
		sql.append("    SIIRESAKI_CD, ");						// 仕入先コード
		sql.append("    DAIHYO_HAISO_CD, ");					// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");				// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");					// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");					// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");					// 発注商品コード
		sql.append("    EOS_KB, ");								// EOS区分
		sql.append("    HACHU_TANI_NA, ");						// 発注単位呼称
		sql.append("    HANBAI_TANI, ");						// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");					// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");					// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");						// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");						// バラ入数
		sql.append("    TEN_HACHU_ST_DT, ");					// 店舗発注開始日
		sql.append("    TEN_HACHU_ED_DT, ");					// 店舗発注終了日
		sql.append("    HANBAI_ST_DT, ");						// 販売開始日
		sql.append("    HANBAI_ED_DT, ");						// 販売終了日
		sql.append("    HANBAI_KIKAN_KB, ");					// 販売期間区分
		sql.append("    TEIKAN_KB, ");							// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");						// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");						// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");						// 納品期限
		sql.append("    BIN_1_KB, ");							// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");					// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");						// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");					// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");				// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");				// ①店納品時間帯
		sql.append("    BIN_2_KB, ");							// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");					// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");						// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");					// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");				// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");				// ②店納品時間帯
		sql.append("    BIN_3_KB, ");							// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");					// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");						// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");					// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");				// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");				// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");					// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");						// 優先便区分
		sql.append("    F_BIN_KB, ");							// F便区分
		sql.append("    BUTURYU_KB, ");							// 物流区分
		sql.append("    GOT_MUJYOKEN_FG, ");					// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");						// 季節開始月日
		sql.append("    GOT_END_MM, ");							// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");						// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");					// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");					// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");					// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");					// 入荷時商品コード２
		sql.append("    ITF_CD, ");								// ITFコード
		sql.append("    GTIN_CD, ");							// GTINコード
		sql.append("    VENDER_MAKER_CD, ");					// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");					// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");					// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");					// センター重量
		sql.append("    PACK_WIDTH_QT, ");						// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");						// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");						// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");						// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");				// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");				// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");				// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");					// 最低入数
		sql.append("    CASE_IRISU_QT, ");						// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");					// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");						// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");						// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");					// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");					// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");					// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");					// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");					// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");				// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");				// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");						// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");						// TC情報
		sql.append("    NOHIN_ONDO_KB, ");						// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");						// 横もち区分
		sql.append("    SHINAZOROE_KB, ");						// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");						// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");						// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");					// 販売政策区分
		sql.append("    HENPIN_NB, ");							// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");					// 返品原価
		sql.append("    CGC_HENPIN_KB, ");						// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");					// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");					// 販売期限
//		sql.append("    PLU_SEND_DT, ");						// PLU送信日
		sql.append("    '" + rs.getString("YUKO_DT") + "', ");	//
		sql.append("    KEYPLU_FG, ");							// キーPLU対象
		sql.append("    PLU_KB, ");								// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");					// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");					// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");					// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");						// 消札売価
		sql.append("    YORIDORI_KB, ");						// よりどり種類
		sql.append("    YORIDORI_VL, ");						// よりどり価格
		sql.append("    YORIDORI_QT, ");						// よりどり数量
		sql.append("    BLAND_CD, ");							// ブランドコード
		sql.append("    SEASON_CD, ");							// シーズンコード
		sql.append("    HUKUSYU_CD, ");							// 服種コード
		sql.append("    STYLE_CD, ");							// スタイルコード
		sql.append("    SCENE_CD, ");							// シーンコード
		sql.append("    SEX_CD, ");								// 性別コード
		sql.append("    AGE_CD, ");								// 年代コード
		sql.append("    GENERATION_CD, ");						// 世代コード
		sql.append("    SOZAI_CD, ");							// 素材コード
		sql.append("    PATTERN_CD, ");							// パターンコード
		sql.append("    ORIAMI_CD, ");							// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");						// 付加機能コード
		sql.append("    SODE_CD, ");							// 袖丈コード
		sql.append("    SIZE_CD, ");							// サイズコード
		sql.append("    COLOR_CD, ");							// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");						// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");					// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");						// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");						// 契約終了期間
		sql.append("    KUMISU_KB, ");							// 組数区分
		sql.append("    NEFUDA_KB, ");							// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");				// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");				// 値札受渡方法
		sql.append("    PC_KB, ");								// PC発行区分
		sql.append("    DAISI_NO_NB, ");						// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");					// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");				// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");			// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");						// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");						// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");					// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");					// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");				// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");							// 棚NO
		sql.append("    DAN_NO_NB, ");							// 段NO
		sql.append("    REBATE_FG, ");							// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");						// マークグループ
		sql.append("    MARK_CD, ");							// マークコード
		sql.append("    YUNYUHIN_KB, ");						// 輸入品区分
		sql.append("    SANTI_CD, ");							// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");						// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");						// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");						// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");						// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");						// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");						// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");						// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");						// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");						// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");						// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");						// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");						// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");						// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");						// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");						// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");						// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");						// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");						// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");					// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");					// 小属性１０
		sql.append("    FUJI_SYOHIN_KB, ");						// F商品区分
		sql.append("    COMMENT_TX, ");							// コメント
		sql.append("    AUTO_DEL_KB, ");						// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");					// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");							// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");					// 新規登録日
//2011.06.09 Y.Imai Mod 不稼働判定の店舗発注反映対応 AM00083 Start
//		sql.append("    HENKO_DT, ");							// 変更日
		sql.append("    '" + batchDt + "', ");					// 変更日
//2011.06.09 Y.Imai Mod 不稼働判定の店舗発注反映対応 AM00083 End
		sql.append("    SYOKAI_TOROKU_TS, ");					// 初回登録日
		sql.append("    SYOKAI_USER_ID, ");						// 初回登録社員ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 更新者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// バッチ更新年月日
		sql.append("    '" + BATCH_ID + "', ");																// バッチ更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");								// 削除フラグ
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(S)
		sql.append("    , CUR_GENERATION_YUKO_DT ");
		sql.append("    , CUR_GENERATION_GENTANKA_VL ");
		sql.append("    , ONE_GENERATION_YUKO_DT ");
		sql.append("    , ONE_GENERATION_GENTANKA_VL ");
		sql.append("    , TWO_GENERATION_YUKO_DT ");
		sql.append("    , TWO_GENERATION_GENTANKA_VL ");
		
		// add 2016.11.31 #2800 nv.cuong(S)
		sql.append("    , MIN_HACHU_QT ");						// 最低発注数
		sql.append("    , HACHU_FUKA_FLG ");					// 発注不可フラグ
		sql.append("    , PER_TXT ");							// 規格内容
		// add 2016.11.31 #2800 nv.cuong(E)
		
		// 2016/08/03 VINX h.sakamoto #1899対応 (S)
		sql.append("    , SIIRE_ZEI_KB ");						// 仕入税区分
		sql.append("    , SIIRE_TAX_RATE_KB ");					// 仕入税率区分
		sql.append("    , OROSI_ZEI_KB ");						// 卸税区分
		sql.append("    , OROSI_TAX_RATE_KB ");					// 卸税率区分
		sql.append("    , OROSI_BAITANKA_VL ");					// 卸売価単価
		sql.append("    , OROSI_BAITANKA_NUKI_VL ");			// 卸売価単価（税抜）
		// 2016/08/03 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append("    , SYOHI_KIGEN_HYOJI_PATTER ");			// 消費期限表示パターン
		// 2017.04.03 M.Akagi #4509 (S)
		//sql.append("    , PLU_HANEI_TIME ");					// PLU反映時間
		sql.append("    , NULL ");					// PLU反映時間
		sql.append("    ,'" + mst910020_EmgFlagDictionary.OFF.getCode() + "' ");					// 緊急配信フラグ
		// 2017.04.03 M.Akagi #4509 (E)
		// add 2016.11.30 #2800 nv.cuong(E)
		
		// add 2016.11.22 #2800 nv.cuong(S)
		sql.append("    , OROSI_BAITANKA_VL_KOURI ");			// 卸売価単価(小売税)
		// add 2016.11.22 #2800 nv.cuong(E)
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(E)
		sql.append("  FROM R_SYOHIN ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "'");
		sql.append("   AND YUKO_DT    = '" + oldYukoDt + "'");

		return sql.toString();

	}

	// Add 2015/06/20 DAI.BQ (S)
	/**
	 * 削除登録SQL生成(R_SYOHIN_ASN)
	 * @param rs 削除のデータ
	 * @param oldYukoDt
	 * @throws
	 */
	private String getInsertDelASNSql(ResultSet rs,  String oldYukoDt) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R_SYOHIN_ASN ");
		sql.append("( BUNRUI1_CD, ");
		sql.append("SYOHIN_CD, ");
		sql.append("YUKO_DT, ");
		sql.append("SYOHIN_EIJI_NA, ");
		sql.append("COUNTRY_CD, ");
		sql.append("MIN_ZAIKO_QT, ");
		sql.append("SECURITY_TAG_FG, ");

		//Add 2015.08.12 The-VV (S)
		sql.append("MAKER_CD, ");
		//Add 2015.08.12 The-VV (E)

		//Add 2016.01.11 Huy.NT (S)
		sql.append("HANBAI_HOHO_KB, ");
		//Add 2016.01.11 Huy.NT (E)

		sql.append("MEMBER_DISCOUNT_FG, ");
		sql.append("HAMPER_SYOHIN_FG, ");
		sql.append("INSERT_TS, ");
		sql.append("INSERT_USER_ID, ");
		sql.append("UPDATE_TS, ");
		sql.append("UPDATE_USER_ID, ");
		sql.append("DELETE_FG");
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append(",LABEL_SEIBUN");       //ラベル成分
		sql.append(",LABEL_HOKAN_HOHO");   //ラベル保管方法
		sql.append(",LABEL_TUKAIKATA)");    //ラベル使い方
		// add 2016.11.30 #2800 nv.cuong(E)
		
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");																		// 分類１コード
		sql.append("    SYOHIN_CD, ");																		// 商品コード
		sql.append("    '" + rs.getString("YUKO_DT") + "', ");												// 有効日
		sql.append("	SYOHIN_EIJI_NA, ");																	//商品名（英字）
		sql.append("	COUNTRY_CD, ");																		//国コード
		sql.append("	MIN_ZAIKO_QT, ");																	//最低在庫数量
		sql.append("	SECURITY_TAG_FG, ");																//セキュリティタグフラグ

		//Add 2015.08.12 The-VV (S)
		sql.append("	MAKER_CD, ");																		//メーカーコード
		//Add 2015.08.12 The-VV (E)

		//Add 2016.01.11 Huy.NT (S)
		sql.append("	HANBAI_HOHO_KB, ");																	//販売方法
		//Add 2016.01.11 Huy.NT (E)

		sql.append("	MEMBER_DISCOUNT_FG, ");																//メンバーディ割引対象外商品フラグ
		sql.append("	HAMPER_SYOHIN_FG, ");																//ハンパー商品フラグ
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");								// 削除フラグ
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append(",LABEL_SEIBUN");       //ラベル成分
		sql.append(",LABEL_HOKAN_HOHO");   //ラベル保管方法
		sql.append(",LABEL_TUKAIKATA");    //ラベル使い方
		// add 2016.11.30 #2800 nv.cuong(E)
		
		sql.append("  FROM R_SYOHIN_ASN ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "'");
		sql.append("   AND YUKO_DT    = '" + oldYukoDt + "'");
		return sql.toString();
	}
	// Add 2015/06/20 DAI.BQ (E)

	/**
	 * 商品マスタ一括修正テーブルより当日のデータを抽出するSQL文の取得
	 *
	 */
	private String setSelecSyohinikkatumente()
	{

		StringBuffer strSql = new StringBuffer();

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		strSql.append("select ");
		strSql.append("   rsim.bunrui1_cd bunrui1_cd, ");
		strSql.append("   rsim.syohin_cd syohin_cd, ");
		strSql.append("   trim(nvl(rsim.siiresaki_cd, '')) siiresaki_cd, ");
		strSql.append("   rsim.genka_chg_kb genka_chg_kb, ");
		strSql.append("   rsim.genka_chg_vl genka_chg_vl, ");
		strSql.append("   rsim.baika_chg_kb baika_chg_kb, ");
		strSql.append("   rsim.baika_chg_vl baika_chg_vl, ");
//2016/08/03 VINX h.sakamoto #1899対応 (S)
		strSql.append("   rsim.orosi_baika_chg_kb orosi_baika_chg_kb, ");
		strSql.append("   rsim.orosi_baika_chg_vl orosi_baika_chg_vl, ");
//2016/08/03 VINX h.sakamoto #1899対応 (E)
		strSql.append("   trim(nvl(rsim.hanbai_st_dt, '')) hanbai_st_dt, ");
		strSql.append("   trim(nvl(rsim.hanbai_ed_dt, '')) hanbai_ed_dt, ");
		strSql.append("   rsim.yuko_dt yuko_dt, ");
		strSql.append("   trim(nvl(rsim.ten_siiresaki_kanri_cd, '')) ten_siiresaki_kanri_cd, ");
		strSql.append("   rsim.neire_rt neire_rt, ");
		strSql.append("   trim(nvl(rsim.ten_hachu_st_dt, '')) ten_hachu_st_dt, ");
		strSql.append("   trim(nvl(rsim.ten_hachu_ed_dt, '')) ten_hachu_ed_dt, ");
		strSql.append("   trim(nvl(rsim.eos_kb, '')) eos_kb, ");
		strSql.append("   trim(nvl(rsim.BUNRUI5_CD, '')) BUNRUI5_CD, ");
		strSql.append("   rsim.neire_henko_kb neire_henko_kb, ");
		strSql.append("   rsim.tana_no_nb tana_no_nb, ");
		strSql.append("   nvl(rsim.toroku_user_id,'') toroku_user_id, ");
		strSql.append("   nvl(rsim.syokai_user_id,'') syokai_user_id, ");
		strSql.append("   nvl(rsim.buturyu_kb,'') buturyu_kb, ");
		strSql.append("   rsim.del_target del_target, ");
//		strSql.append("   rsim.genka_cal_kb genka_cal_kb, ");
		strSql.append("   nvl(rsim.genka_cal_kb, '" + mst009901_TankaCalKbDictionary.SEN.getCode() + "') as genka_cal_kb, ");
//		strSql.append("   rsim.neire_cal_kb neire_cal_kb, ");
		strSql.append("   nvl(rsim.neire_cal_kb, '" + mst009901_TankaCalKbDictionary.SEN.getCode() + "') as neire_cal_kb, ");
		strSql.append("   rsim.gyosyu_kb, ");
		strSql.append("   rsim.plu_send_dt, ");
		strSql.append("   rsimn.bunrui1_cd as syori_taisyo_ck, ");
		strSql.append("   BUTURYU_CK.CODE_CD AS BUTURYU_CK, ");
		strSql.append("   EOS_CK.CODE_CD AS EOS_CK, ");
		strSql.append("   BUNRUI5_CK.CODE_CD AS BUNRUI5_CK, ");
		strSql.append("   RST.BUNRUI1_CD AS TAIKEI_BUNRUI1_CD ");
		//2016/10/31 VINX h.sakamoto #2551対応 (S)
		strSql.append("   ,rsim.UKETSUKE_NO AS UKETSUKE_NO ");
		//2016/10/31 VINX h.sakamoto #2551対応 (E)

		strSql.append(" from r_syohin_ikkatu_mente rsim");
		strSql.append(" left join ");
		strSql.append("  (select ");
		strSql.append("     rsimw.bunrui1_cd bunrui1_cd, ");
		strSql.append("     rsimw.syohin_cd syohin_cd, ");
		strSql.append("     rsimw.yuko_dt yuko_dt, ");
		strSql.append("     max(rsimw.touroku_ts) touroku_ts ");
		strSql.append("   from r_syohin_ikkatu_mente rsimw");
		strSql.append("   where syori_kb = '" + SYORI_KB_SYORITYU + "'");
		strSql.append("     and rsimw.delete_fg = '0'");
		strSql.append("   group by ");
		strSql.append("     rsimw.bunrui1_cd, ");
		strSql.append("     rsimw.syohin_cd, ");
		strSql.append("     rsimw.yuko_dt ");
		strSql.append(" ) rsimn ");

		strSql.append(" on ");
		strSql.append("   rsim.bunrui1_cd = rsimn.bunrui1_cd ");
		strSql.append("   and rsim.syohin_cd = rsimn.syohin_cd ");
		strSql.append("   and rsim.yuko_dt = rsimn.yuko_dt ");
		strSql.append("   and rsim.touroku_ts = rsimn.touroku_ts ");

		//物流区分
		strSql.append("  LEFT JOIN ");
		strSql.append("       R_NAMECTF BUTURYU_CK ");
		strSql.append("    ON BUTURYU_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DISTRIBUTION_DIVISION, userLocal)).append("' ");
		strSql.append("   AND BUTURYU_CK.CODE_CD       = rsim.buturyu_kb ");
		strSql.append("   AND BUTURYU_CK.DELETE_FG     = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		//EOS区分
		strSql.append("  LEFT JOIN ");
		strSql.append("       R_NAMECTF EOS_CK ");
		strSql.append("    ON EOS_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.EOS_DIVISION, userLocal)).append("' ");
		strSql.append("   AND EOS_CK.CODE_CD       = rsim.EOS_KB ");
		strSql.append("   AND EOS_CK.DELETE_FG     = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		//分類５コード
		strSql.append("  LEFT JOIN ");
		strSql.append("       R_NAMECTF BUNRUI5_CK ");
		strSql.append("    ON BUNRUI5_CK.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		strSql.append("   AND BUNRUI5_CK.CODE_CD       = '").append(mst000611_SystemKbDictionary.G.getCode()).append("' || rsim.BUNRUI5_CD ");
		strSql.append("   AND BUNRUI5_CK.DELETE_FG     = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		//商品分類体系
		strSql.append("  LEFT JOIN ");
		strSql.append("       R_SYOHIN_TAIKEI RST ");
		strSql.append("    ON RST.SYSTEM_KB  = '" + mst000611_SystemKbDictionary.G.getCode() + "' ");
		strSql.append("   AND RST.BUNRUI5_CD = rsim.BUNRUI5_CD ");
		strSql.append("   AND RST.BUNRUI1_CD = rsim.BUNRUI1_CD ");

		strSql.append(" where rsim.syori_kb = '" + SYORI_KB_SYORITYU + "'");

		strSql.append(" order by ");
		strSql.append("   rsim.bunrui1_cd, ");
		strSql.append("   rsim.syohin_cd, ");
		strSql.append("   rsim.yuko_dt ");

		return strSql.toString();
	}

	/**
	 * 商品マスタ一括修正TRへ追加する
	 * @param rs
	 * @param syokaiuserid
	 * @param price
	 * @param errormessage
	 * @param gyosyukb
	 * @return
	 * @throws SQLException
	 */
	private void MB130301_SyohinIkkatuSyuseiTR(
		ResultSet rs,
		double[] price,
		// 2017/01/19 T.Arimoto #3608対応（S)
		BigDecimal orosiBaitankaVlBd,
		// 2017/01/19 T.Arimoto #3608対応（E)
		String gyosyukb)
		throws SQLException
	{
		String userLocal=ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//タグ
		if (mst000601_GyoshuKbDictionary.A08.getCode().equals(gyosyukb))
		{
//			if (A08cnt == 0)
//			{
//				db.executeUpdate(getDeleteTRSql(gyosyukb));
//			}

			if (errmsg.size() > 0)
			{
				errorFg = true;

				//エラーメッセージごと商品一括修正TRに追加する
				for (int i = 0; i < errmsg.size(); i++)
				{
					A08cnt++;
					//addInsertTRPreparedStatement(rs, price[0], (long) price[1], A08cnt, errmsg.get(i).toString(), gyosyukb, "2");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
					//addInsertTRPreparedStatement(rs, price[0], price[1], A08cnt, errmsg.get(i).toString(), gyosyukb, "2");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//						addInsertTRPreparedStatement(rs, price[0], price[2], price[1], A08cnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（S)
					//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], A08cnt, errmsg.get(i).toString(), gyosyukb, "2");
					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, A08cnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				}
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + errmsg.size();
			}
			else
			{
				A08cnt++;
				//addInsertTRPreparedStatement(rs, price[0], (long) price[1], A08cnt, "正常に登録しました。", gyosyukb, "1");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
				//addInsertTRPreparedStatement(rs, price[0],  price[1], A08cnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], A08cnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（S)
				//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], A08cnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, A08cnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + 1;
			}
		}
		//実用
		else if (mst000601_GyoshuKbDictionary.A07.getCode().equals(gyosyukb))
		{
//			if (A07cnt == 0)
//			{
//				db.executeUpdate(getDeleteTRSql(gyosyukb));
//			}

			if (errmsg.size() > 0)
			{
				errorFg = true;

				//エラーメッセージごと商品一括修正TRに追加する
				for (int i = 0; i < errmsg.size(); i++)
				{
					A07cnt++;
					//addInsertTRPreparedStatement(rs, price[0], (long) price[1], A07cnt, errmsg.get(i).toString(), gyosyukb, "2");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
					//addInsertTRPreparedStatement(rs, price[0],  price[1], A07cnt, errmsg.get(i).toString(), gyosyukb, "2");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//						addInsertTRPreparedStatement(rs, price[0], price[2], price[1], A07cnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（S)
					//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], A07cnt, errmsg.get(i).toString(), gyosyukb, "2");
					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, A07cnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				}
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + errmsg.size();
			}
			else
			{
				A07cnt++;
				//addInsertTRPreparedStatement(rs, price[0], (long) price[1], A07cnt, "正常に登録しました。", gyosyukb, "1");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
				//addInsertTRPreparedStatement(rs, price[0],  price[1], A07cnt,  MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], A07cnt,  MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（S)
				//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], A07cnt,  MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, A07cnt,  MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)

				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + 1;
			}
		}
		//グロサリー/バラエティ
		else if (mst000601_GyoshuKbDictionary.GRO.getCode().equals(gyosyukb))
		{
//			if (GROcnt == 0)
//			{
//				db.executeUpdate(getDeleteTRSql(gyosyukb));
//			}

			if (errmsg.size() > 0)
			{
				errorFg = true;

				//エラーメッセージごと商品一括修正TRに追加する
				for (int i = 0; i < errmsg.size(); i++)
				{
					GROcnt++;
					//addInsertTRPreparedStatement(rs, price[0], (long) price[1], GROcnt, errmsg.get(i).toString(), gyosyukb, "2");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
					//addInsertTRPreparedStatement(rs, price[0],  price[1], GROcnt, errmsg.get(i).toString(), gyosyukb, "2");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], GROcnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（S)
					//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], GROcnt, errmsg.get(i).toString(), gyosyukb, "2");
					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, GROcnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				}
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + errmsg.size();
			}
			else
			{
				GROcnt++;
				//addInsertTRPreparedStatement(rs,price[0], (long) price[1], GROcnt, "正常に登録しました。", gyosyukb, "1");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
				//addInsertTRPreparedStatement(rs,price[0],  price[1], GROcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//				addInsertTRPreparedStatement(rs,price[0], price[2], price[1], GROcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（S)
				//addInsertTRPreparedStatement(rs,price[0], price[2], price[1], price[5], GROcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				addInsertTRPreparedStatement(rs,price[0], price[2], price[1], orosiBaitankaVlBd, GROcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + 1;
			}
		}
		//ディリー
		else if (mst000601_GyoshuKbDictionary.FRE.getCode().equals(gyosyukb))
		{
//			if (FREcnt == 0)
//			{
//				db.executeUpdate(getDeleteTRSql(gyosyukb));
//			}

			if (errmsg.size() > 0)
			{
				errorFg = true;

				//エラーメッセージごと商品一括修正TRに追加する
				for (int i = 0; i < errmsg.size(); i++)
				{
					FREcnt++;
					//addInsertTRPreparedStatement(rs, price[0], (long) price[1], FREcnt, errmsg.get(i).toString(), gyosyukb, "2");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
					//addInsertTRPreparedStatement(rs, price[0],  price[1], FREcnt, errmsg.get(i).toString(), gyosyukb, "2");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], FREcnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（S)
					//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], FREcnt, errmsg.get(i).toString(), gyosyukb, "2");
					addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, FREcnt, errmsg.get(i).toString(), gyosyukb, "2");
					// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				}
				//追加件数の累計
				insSyikkmentecnt = insSyikkmentecnt + errmsg.size();
			}
			else
			{
				FREcnt++;
				//addInsertTRPreparedStatement(rs, price[0], (long) price[1], FREcnt, "正常に登録しました。", gyosyukb, "1");
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (S)
				//addInsertTRPreparedStatement(rs, price[0],  price[1], FREcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//				addInsertTRPreparedStatement(rs, price[0], price[2], price[1], FREcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（S)
				//addInsertTRPreparedStatement(rs, price[0], price[2], price[1], price[5], FREcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				addInsertTRPreparedStatement(rs, price[0], price[2], price[1], orosiBaitankaVlBd, FREcnt, MessageUtil.getMessage("MB130101_TXT_00002",userLocal), gyosyukb, "1");
				// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
// No.182 MSTB051 Mod 2015.11.26 HUY.NT (E)
				insSyikkmentecnt = insSyikkmentecnt + 1;

			}
		}

	}

	/**
	 * 商品マスタに追加するSQLの取得
	 * @param rset1 商品一括修正のレコード
	 * @param genprice 原価単価
	 * @param baiprice 売価単価
	 * @param gennukiprice 原価単価(税抜)
	 * @param bainukiprice 売価単価(税抜)
// 2016/08/03 VINX h.sakamoto #1899対応 (E)
	 * @param obaiprice 卸売価単価
	 * @param obainukiprice 卸売価単価(税抜)
// 2016/08/03 VINX h.sakamoto #1899対応 (E)
	 * @param yukoDt コピー元の有効日
	 * @return
	 * @throws SQLException
	 */
	//private String getInsertSql(ResultSet rset1, double genprice, long baiprice, double gennukiprice, long bainukiprice, String yukoDt) throws SQLException {

// 2016/08/03 VINX h.sakamoto #1899対応 (S)
	//	private String getInsertSql(ResultSet rset1, double genprice, double baiprice, double gennukiprice, double bainukiprice, String yukoDt) throws SQLException {
	// 2017/01/19 T.Arimoto #3608対応（S)
	//private String getInsertSql(ResultSet rset1, double genprice, double baiprice, double gennukiprice, double bainukiprice,double obaiprice, double obainukiprice, double kouriprice, String yukoDt) throws SQLException {
	private String getInsertSql(ResultSet rset1, double genprice, double baiprice, double gennukiprice, double bainukiprice,double obaiprice, double obainukiprice, double kouriprice, BigDecimal orosiBaitankaVlBd, String yukoDt) throws SQLException {
	// 2017/01/19 T.Arimoto #3608対応（E)
// 2016/08/03 VINX h.sakamoto #1899対応 (E)

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO R_SYOHIN ");
		sql.append("( ");
		sql.append("    BUNRUI1_CD, ");								// 分類１コード
		sql.append("    SYOHIN_CD, ");								// 商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (S)
		sql.append("    OLD_SYOHIN_CD, ");							// 旧商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (E)
		sql.append("    YUKO_DT, ");								// 有効日
		sql.append("    SYSTEM_KB, ");								// システム区分
		sql.append("    GYOSYU_KB, ");								// 業種区分
		sql.append("    SYOHIN_KB, ");								// 商品区分
		sql.append("    KETASU_KB, ");								// 桁数区分
		sql.append("    BUNRUI2_CD, ");								// 分類２コード
		sql.append("    BUNRUI5_CD, ");								// 分類５コード
		sql.append("    HINMOKU_CD, ");								// 品目
		sql.append("    SYOHIN_2_CD, ");							// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");						// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");						// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");								// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");						// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");						// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");						// 漢字規格２
		sql.append("    REC_HINMEI_KANJI_NA, ");					// POSレシート品名(漢字)
		sql.append("    HINMEI_KANA_NA, ");							// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");							// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");						// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");						// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");						// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");						// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");						// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");								// Eショップ区分
		sql.append("    PB_KB, ");									// PB区分
		sql.append("    SUBCLASS_CD, ");							// サブクラスコード
		sql.append("    HAIFU_CD, ");								// 配布コード
		sql.append("    ZEI_KB, ");									// 税区分
		sql.append("    TAX_RATE_KB, ");							// 税率区分
		sql.append("    GENTANKA_VL, ");							// 原価単価
		sql.append("    BAITANKA_VL, ");							// 売価単価
//2013.12.17 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    GENTANKA_NUKI_VL, ");						// 原価単価(税抜)
		sql.append("    BAITANKA_NUKI_VL, ");						// 売価単価(税抜)
		sql.append("    BAIKA_HAISHIN_FG, ");						// 売価配信フラグ
		sql.append("    FREE_1_KB, ");								// 任意区分１
		sql.append("    FREE_2_KB, ");								// 任意区分２
		sql.append("    FREE_3_KB, ");								// 任意区分３
		sql.append("    FREE_4_KB, ");								// 任意区分４
		sql.append("    FREE_5_KB, ");								// 任意区分５
		sql.append("    COMMENT_1_TX, ");							// コメント１
		sql.append("    COMMENT_2_TX, ");							// コメント２
		sql.append("    FREE_CD, ");								// 任意コード
//2013.12.17 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TOSYO_BAIKA_VL, ");							// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");						// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");						// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");						// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");					// メーカー希望小売り価格
		sql.append("    SIIRESAKI_CD, ");							// 仕入先コード
		sql.append("    DAIHYO_HAISO_CD, ");						// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");					// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");						// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");						// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");						// 発注商品コード
		sql.append("    EOS_KB, ");									// EOS区分
		sql.append("    HACHU_TANI_NA, ");							// 発注単位呼称
		sql.append("    HANBAI_TANI, ");							// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");						// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");						// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");							// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");							// バラ入数
		sql.append("    TEN_HACHU_ST_DT, ");						// 店舗発注開始日
		sql.append("    TEN_HACHU_ED_DT, ");						// 店舗発注終了日
		sql.append("    HANBAI_ST_DT, ");							// 販売開始日
		sql.append("    HANBAI_ED_DT, ");							// 販売終了日
		sql.append("    HANBAI_KIKAN_KB, ");						// 販売期間区分
		sql.append("    TEIKAN_KB, ");								// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");							// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");							// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");							// 納品期限
		sql.append("    BIN_1_KB, ");								// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");						// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");							// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");						// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");					// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");					// ①店納品時間帯
		sql.append("    BIN_2_KB, ");								// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");						// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");							// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");						// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");					// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");					// ②店納品時間帯
		sql.append("    BIN_3_KB, ");								// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");						// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");							// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");						// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");					// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");					// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");						// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");							// 優先便区分
		sql.append("    F_BIN_KB, ");								// F便区分
		sql.append("    BUTURYU_KB, ");								// 物流区分
		sql.append("    GOT_MUJYOKEN_FG, ");						// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");							// 季節開始月日
		sql.append("    GOT_END_MM, ");								// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");							// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");						// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");						// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");						// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");						// 入荷時商品コード２
		sql.append("    ITF_CD, ");									// ITFコード
		sql.append("    GTIN_CD, ");								// GTINコード
		sql.append("    VENDER_MAKER_CD, ");						// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");						// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");						// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");						// センター重量
		sql.append("    PACK_WIDTH_QT, ");							// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");							// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");							// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");							// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");					// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");					// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");					// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");						// 最低入数
		sql.append("    CASE_IRISU_QT, ");							// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");						// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");							// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");							// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");						// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");						// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");						// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");						// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");						// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");					// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");					// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");							// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");							// TC情報
		sql.append("    NOHIN_ONDO_KB, ");							// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");							// 横もち区分
		sql.append("    SHINAZOROE_KB, ");							// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");							// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");							// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");						// 販売政策区分
		sql.append("    HENPIN_NB, ");								// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");						// 返品原価
		sql.append("    CGC_HENPIN_KB, ");							// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");						// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");						// 販売期限
		sql.append("    PLU_SEND_DT, ");							// PLU送信日
		sql.append("    KEYPLU_FG, ");								// キーPLU対象
		sql.append("    PLU_KB, ");									// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");						// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");						// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");						// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");							// 消札売価
		sql.append("    YORIDORI_KB, ");							// よりどり種類
		sql.append("    YORIDORI_VL, ");							// よりどり価格
		sql.append("    YORIDORI_QT, ");							// よりどり数量
		sql.append("    BLAND_CD, ");								// ブランドコード
		sql.append("    SEASON_CD, ");								// シーズンコード
		sql.append("    HUKUSYU_CD, ");								// 服種コード
		sql.append("    STYLE_CD, ");								// スタイルコード
		sql.append("    SCENE_CD, ");								// シーンコード
		sql.append("    SEX_CD, ");									// 性別コード
		sql.append("    AGE_CD, ");									// 年代コード
		sql.append("    GENERATION_CD, ");							// 世代コード
		sql.append("    SOZAI_CD, ");								// 素材コード
		sql.append("    PATTERN_CD, ");								// パターンコード
		sql.append("    ORIAMI_CD, ");								// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");							// 付加機能コード
		sql.append("    SODE_CD, ");								// 袖丈コード
		sql.append("    SIZE_CD, ");								// サイズコード
		sql.append("    COLOR_CD, ");								// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");							// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");						// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");							// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");							// 契約終了期間
		sql.append("    KUMISU_KB, ");								// 組数区分
		sql.append("    NEFUDA_KB, ");								// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");					// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");					// 値札受渡方法
		sql.append("    PC_KB, ");									// PC発行区分
		sql.append("    DAISI_NO_NB, ");							// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");						// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");					// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");				// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");							// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");							// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");						// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");						// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");					// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");								// 棚NO
		sql.append("    DAN_NO_NB, ");								// 段NO
		sql.append("    REBATE_FG, ");								// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");							// マークグループ
		sql.append("    MARK_CD, ");								// マークコード
		sql.append("    YUNYUHIN_KB, ");							// 輸入品区分
		sql.append("    SANTI_CD, ");								// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");							// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");							// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");							// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");							// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");							// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");							// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");							// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");							// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");							// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");							// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");							// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");							// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");							// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");							// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");							// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");							// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");							// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");							// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");						// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");						// 小属性１０
		sql.append("    FUJI_SYOHIN_KB, ");							// F商品区分
		sql.append("    COMMENT_TX, ");								// コメント
		sql.append("    AUTO_DEL_KB, ");							// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");						// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");								// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");						// 新規登録日
		sql.append("    HENKO_DT, ");								// 変更日
		sql.append("    SYOKAI_TOROKU_TS, ");						// 初回登録日
		sql.append("    SYOKAI_USER_ID, ");							// 初回登録社員ID
		sql.append("    INSERT_TS, ");								// 作成年月日
		sql.append("    INSERT_USER_ID, ");							// 作成者ID
		sql.append("    UPDATE_TS, ");								// 更新年月日
		sql.append("    UPDATE_USER_ID, ");							// 更新者ID
		sql.append("    BATCH_UPDATE_TS, ");						// バッチ更新年月日
		sql.append("    BATCH_UPDATE_ID, ");						// バッチ更新者ID
		sql.append("    DELETE_FG ");								// 削除フラグ
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT (S)
		sql.append("    , CUR_GENERATION_YUKO_DT ");				// 有効日(現世代)
		sql.append("    , CUR_GENERATION_GENTANKA_VL ");			// 原価単価(現世代)
		sql.append("    , ONE_GENERATION_YUKO_DT ");				// 有効日(1世代前)
		sql.append("    , ONE_GENERATION_GENTANKA_VL ");			// 原価単価(1世代前)
		sql.append("    , TWO_GENERATION_YUKO_DT ");				// 有効日(2世代前)
		sql.append("    , TWO_GENERATION_GENTANKA_VL ");			// 原価単価(2世代前)
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT (E)
		

		// add 2016.11.31 #2800 nv.cuong(S)
		sql.append("    , MIN_HACHU_QT ");						// 最低発注数
		sql.append("    , HACHU_FUKA_FLG ");					// 発注不可フラグ
		sql.append("    , PER_TXT ");							// 規格内容
		// add 2016.11.31 #2800 nv.cuong(E)
		
		// 2016/08/03 VINX h.sakamoto #1899対応 (S)
		sql.append("    , SIIRE_ZEI_KB ");							// 仕入税区分
		sql.append("    , SIIRE_TAX_RATE_KB ");						// 仕入税率区分
		sql.append("    , OROSI_ZEI_KB ");							// 卸税区分
		sql.append("    , OROSI_TAX_RATE_KB ");						// 卸税率区分
		sql.append("    , OROSI_BAITANKA_VL ");						// 卸売単価
		sql.append("    , OROSI_BAITANKA_NUKI_VL ");				// 卸売単価(税抜)
		// 2016/08/03 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append("    , SYOHI_KIGEN_HYOJI_PATTER ");			// 消費期限表示パターン
		sql.append("    , PLU_HANEI_TIME ");					// PLU反映時間
		// add 2016.11.30 #2800 nv.cuong(E)
		
		// add 2016.11.22 #2800 nv.cuong(S)
		sql.append("    , OROSI_BAITANKA_VL_KOURI ");				// 卸売価単価(小売税)
		// add 2016.11.22 #2800 nv.cuong(E)
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");								// 分類１コード
		sql.append("    SYOHIN_CD, ");								// 商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (S)
		sql.append("    OLD_SYOHIN_CD, ");							// 旧商品コード
		// No.621 MSTB051010 Add 2016.03.16 Huy.NT (E)
//		sql.append("    ?, ");										// 有効日
		sql.append("  '" + rset1.getString("yuko_dt") + "',");
		sql.append("    SYSTEM_KB, ");								// システム区分
		sql.append("    GYOSYU_KB, ");								// 業種区分
		sql.append("    SYOHIN_KB, ");								// 商品区分
		sql.append("    KETASU_KB, ");								// 桁数区分
		sql.append("    BUNRUI2_CD, ");								// 分類２コード

//		sql.append("    BUNRUI5_CD, ");								// 分類５コード
		if (isBlank(rset1.getString("BUNRUI5_CD"))) {
			sql.append("  BUNRUI5_CD,");
		} else {
			sql.append("  '" + rset1.getString("BUNRUI5_CD") + "',");
		}
		sql.append("    HINMOKU_CD, ");								// 品目
		sql.append("    SYOHIN_2_CD, ");							// 商品コード２
		sql.append("    ZAIKO_SYOHIN_CD, ");						// 在庫用商品集計コード
		sql.append("    JYOHO_SYOHIN_CD, ");						// 情報系用商品集計コード
		sql.append("    MAKER_CD, ");								// ＪＡＮメーカーコード
		sql.append("    HINMEI_KANJI_NA, ");						// 漢字品名
		sql.append("    KIKAKU_KANJI_NA, ");						// 漢字規格
		sql.append("    KIKAKU_KANJI_2_NA, ");						// 漢字規格２
		sql.append("    REC_HINMEI_KANJI_NA, ");					// POSレシート品名(漢字)
		sql.append("    HINMEI_KANA_NA, ");							// カナ品名
		sql.append("    KIKAKU_KANA_NA, ");							// カナ規格
		sql.append("    KIKAKU_KANA_2_NA, ");						// カナ規格２
		sql.append("    REC_HINMEI_KANA_NA, ");						// POSレシート品名(カナ)
		sql.append("    SYOHIN_WIDTH_QT, ");						// 商品サイズ(幅)
		sql.append("    SYOHIN_HEIGHT_QT, ");						// 商品サイズ(高さ)
		sql.append("    SYOHIN_DEPTH_QT, ");						// 商品サイズ(奥行き)
		sql.append("    E_SHOP_KB, ");								// Eショップ区分
		sql.append("    PB_KB, ");									// PB区分
		sql.append("    SUBCLASS_CD, ");							// サブクラスコード
		sql.append("    HAIFU_CD, ");								// 配布コード
		sql.append("    ZEI_KB, ");									// 税区分
		sql.append("    TAX_RATE_KB, ");							// 税率区分
		sql.append("    " + genprice + ", ");						// 原価単価
		sql.append("    " + baiprice + ", ");						// 売価単価
//2013.12.17 [CUS00048]  マスタ未使用項目 (S)
		sql.append("    " + gennukiprice + ", ");					// 原価単価(税抜)
		sql.append("    " + bainukiprice + ",");					// 売価単価(税抜)
		sql.append("    BAIKA_HAISHIN_FG, ");						// 売価配信フラグ
		sql.append("    FREE_1_KB, ");								// 任意区分１
		sql.append("    FREE_2_KB, ");								// 任意区分２
		sql.append("    FREE_3_KB, ");								// 任意区分３
		sql.append("    FREE_4_KB, ");								// 任意区分４
		sql.append("    FREE_5_KB, ");								// 任意区分５
		sql.append("    COMMENT_1_TX, ");							// コメント１
		sql.append("    COMMENT_2_TX, ");							// コメント２
		sql.append("    FREE_CD, ");								// 任意コード
//2013.12.17 [CUS00048]  マスタ未使用項目 (E)
		sql.append("    TOSYO_BAIKA_VL, ");							// 当初売価
		sql.append("    PRE_GENTANKA_VL, ");						// 前回原価単価
		sql.append("    PRE_BAITANKA_VL, ");						// 前回売価単価
		sql.append("    TOKUBETU_GENKA_VL, ");						// 特別原価
		sql.append("    MAKER_KIBO_KAKAKU_VL, ");					// メーカー希望小売り価格

//		sql.append("    SIIRESAKI_CD, ");							// 仕入先コード
		if (isBlank(rset1.getString("SIIRESAKI_CD"))) {
			sql.append("  SIIRESAKI_CD,");
		} else {
			sql.append("  '" + rset1.getString("SIIRESAKI_CD") + "',");
		}

		sql.append("    DAIHYO_HAISO_CD, ");						// 代表配送先コード
		sql.append("    TEN_SIIRESAKI_KANRI_CD, ");					// 店別仕入先管理コード
		sql.append("    SIIRE_HINBAN_CD, ");						// 仕入先品番
		sql.append("    HACYU_SYOHIN_KB, ");						// 発注商品コード区分
		sql.append("    HACYU_SYOHIN_CD, ");						// 発注商品コード

//		sql.append("    EOS_KB, ");									// EOS区分
		if (isBlank(rset1.getString("eos_kb"))) {
			sql.append("  eos_kb,");
		} else {
			sql.append("  '" + rset1.getString("eos_kb") + "',");
		}

		sql.append("    HACHU_TANI_NA, ");							// 発注単位呼称
		sql.append("    HANBAI_TANI, ");							// 販売単位呼称
		sql.append("    HACHUTANI_IRISU_QT, ");						// 発注単位(入数)
		sql.append("    MAX_HACHUTANI_QT, ");						// 最大発注単位数
		sql.append("    CASE_HACHU_KB, ");							// ケース発注区分
		sql.append("    BARA_IRISU_QT, ");							// バラ入数

//		sql.append("    TEN_HACHU_ST_DT, ");						// 店舗発注開始日
		if (isBlank(rset1.getString("TEN_HACHU_ST_DT")))	{
			sql.append("  TEN_HACHU_ST_DT,");
		} else {
			sql.append("  '" + rset1.getString("TEN_HACHU_ST_DT") + "',");
		}

//		sql.append("    TEN_HACHU_ED_DT, ");						// 店舗発注終了日
		if (isBlank(rset1.getString("TEN_HACHU_ED_DT"))) {
			sql.append("  TEN_HACHU_ED_DT,");
		} else {
			sql.append("  '" + rset1.getString("TEN_HACHU_ED_DT") + "',");
		}

//		sql.append("    HANBAI_ST_DT, ");							// 販売開始日
		if (isBlank(rset1.getString("HANBAI_ST_DT"))) {
			sql.append("  HANBAI_ST_DT,");
		} else {
			sql.append("  '" + rset1.getString("HANBAI_ST_DT") + "',");
		}

//		sql.append("    HANBAI_ED_DT, ");							// 販売終了日
		if (isBlank(rset1.getString("HANBAI_ED_DT"))) {
			sql.append("  HANBAI_ED_DT,");
		} else {
			sql.append("  '" + rset1.getString("HANBAI_ED_DT") + "',");
		}

		sql.append("    HANBAI_KIKAN_KB, ");						// 販売期間区分
		sql.append("    TEIKAN_KB, ");								// 定貫区分
		sql.append("    SOBA_SYOHIN_KB, ");							// 相場商品区分
		sql.append("    NOHIN_KIGEN_KB, ");							// 納品期限区分
		sql.append("    NOHIN_KIGEN_QT, ");							// 納品期限
		sql.append("    BIN_1_KB, ");								// ①便区分
		sql.append("    HACHU_PATTERN_1_KB, ");						// ①発注パターン区分
		sql.append("    SIME_TIME_1_QT, ");							// ①締め時間
		sql.append("    C_NOHIN_RTIME_1_KB, ");						// ①センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_1_KB, ");					// ①店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_1_KB, ");					// ①店納品時間帯
		sql.append("    BIN_2_KB, ");								// ②便区分
		sql.append("    HACHU_PATTERN_2_KB, ");						// ②発注パターン区分
		sql.append("    SIME_TIME_2_QT, ");							// ②締め時間
		sql.append("    C_NOHIN_RTIME_2_KB, ");						// ②センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_2_KB, ");					// ②店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_2_KB, ");					// ②店納品時間帯
		sql.append("    BIN_3_KB, ");								// ③便区分
		sql.append("    HACHU_PATTERN_3_KB, ");						// ③発注パターン区分
		sql.append("    SIME_TIME_3_QT, ");							// ③締め時間
		sql.append("    C_NOHIN_RTIME_3_KB, ");						// ③センタ納品リードタイム
		sql.append("    TEN_NOHIN_RTIME_3_KB, ");					// ③店納品リードタイム
		sql.append("    TEN_NOHIN_TIME_3_KB, ");					// ③店納品時間帯
		sql.append("    C_NOHIN_RTIME_KB, ");						// センタ納品リードタイム
		sql.append("    YUSEN_BIN_KB, ");							// 優先便区分
		sql.append("    F_BIN_KB, ");								// F便区分

//		sql.append("    BUTURYU_KB, ");								// 物流区分
		if (isBlank(rset1.getString("BUTURYU_KB"))) {
			sql.append("  BUTURYU_KB,");
		} else {
			sql.append("  '" + rset1.getString("BUTURYU_KB") + "',");
		}

		sql.append("    GOT_MUJYOKEN_FG, ");						// GOT無条件表示対象
		sql.append("    GOT_START_MM, ");							// 季節開始月日
		sql.append("    GOT_END_MM, ");								// 季節終了月日
		sql.append("    HACHU_TEISI_KB, ");							// 発注停止区分
		sql.append("    CENTER_ZAIKO_KB, ");						// センター在庫区分
		sql.append("    NOHIN_SYOHIN_CD, ");						// 納品商品コード
		sql.append("    NYUKA_SYOHIN_CD, ");						// 入荷時商品コード
		sql.append("    NYUKA_SYOHIN_2_CD, ");						// 入荷時商品コード２
		sql.append("    ITF_CD, ");									// ITFコード
		sql.append("    GTIN_CD, ");								// GTINコード
		sql.append("    VENDER_MAKER_CD, ");						// ベンダーメーカーコード
		sql.append("    ZAIKO_CENTER_CD, ");						// 在庫センターコード
		sql.append("    ZAIKO_HACHU_SAKI, ");						// 在庫補充発注先
		sql.append("    CENTER_WEIGHT_QT, ");						// センター重量
		sql.append("    PACK_WIDTH_QT, ");							// 外箱サイズ幅
		sql.append("    PACK_HEIGTH_QT, ");							// 外箱サイズ高さ
		sql.append("    PACK_DEPTH_QT, ");							// 外箱サイズ奥行き
		sql.append("    PACK_WEIGHT_QT, ");							// 外箱重量
		sql.append("    CENTER_HACHUTANI_KB, ");					// センター発注単位区分
		sql.append("    CENTER_HACHUTANI_QT, ");					// センター発注単位数
		sql.append("    CENTER_BARA_IRISU_QT, ");					// センターバラ入数
		sql.append("    CENTER_IRISU_QT, ");						// 最低入数
		sql.append("    CASE_IRISU_QT, ");							// ケース入り数
		sql.append("    CENTER_IRISU_2_QT, ");						// 梱り合せ数
		sql.append("    MIN_ZAIKOSU_QT, ");							// 最小在庫数
		sql.append("    MAX_ZAIKOSU_QT, ");							// 最大在庫数
		sql.append("    KIJUN_ZAIKOSU_QT, ");						// 基準在庫日数
		sql.append("    MIN_ZAIKONISSU_QT, ");						// 最小在庫日数
		sql.append("    MAX_ZAIKONISSU_QT, ");						// 最大在庫日数
		sql.append("    CENTER_KYOYO_KB, ");						// センター許容区分
		sql.append("    CENTER_KYOYO_DT, ");						// センター許容日
		sql.append("    CENTER_SYOMI_KIKAN_KB, ");					// センター賞味期間区分
		sql.append("    CENTER_SYOMI_KIKAN_DT, ");					// センター賞味期間
		sql.append("    TEN_GROUPNO_CD, ");							// 店グルーピングNO(物流）
		sql.append("    TC_JYOUHO_NA, ");							// TC情報
		sql.append("    NOHIN_ONDO_KB, ");							// 納品温度帯
		sql.append("    YOKOMOTI_KB, ");							// 横もち区分
		sql.append("    SHINAZOROE_KB, ");							// 品揃区分
		sql.append("    HONBU_ZAI_KB, ");							// 本部在庫区分
		sql.append("    TEN_ZAIKO_KB, ");							// 店在庫区分
		sql.append("    HANBAI_SEISAKU_KB, ");						// 販売政策区分
		sql.append("    HENPIN_NB, ");								// 返品契約書NO
		sql.append("    HENPIN_GENKA_VL, ");						// 返品原価
		sql.append("    CGC_HENPIN_KB, ");							// CGC返品区分
		sql.append("    HANBAI_LIMIT_KB, ");						// 販売期限区分
		sql.append("    HANBAI_LIMIT_QT, ");						// 販売期限

//		sql.append("    PLU_SEND_DT, ");							// PLU送信日
		if (!isBlank(rset1.getString("PLU_SEND_DT"))) {
			sql.append("  '" + rset1.getString("PLU_SEND_DT") + "',");
		} else {
			sql.append("  '" + rset1.getString("YUKO_DT") + "',");
		}

		sql.append("    KEYPLU_FG, ");								// キーPLU対象
		sql.append("    PLU_KB, ");									// PLU区分
		sql.append("    SYUZEI_HOKOKU_KB, ");						// 酒税報告分類
		sql.append("    SAKE_NAIYORYO_QT, ");						// 酒内容量
		sql.append("    TAG_HYOJI_BAIKA_VL, ");						// タグ表示売価
		sql.append("    KESHI_BAIKA_VL, ");							// 消札売価
		sql.append("    YORIDORI_KB, ");							// よりどり種類
		sql.append("    YORIDORI_VL, ");							// よりどり価格
		sql.append("    YORIDORI_QT, ");							// よりどり数量
		sql.append("    BLAND_CD, ");								// ブランドコード
		sql.append("    SEASON_CD, ");								// シーズンコード
		sql.append("    HUKUSYU_CD, ");								// 服種コード
		sql.append("    STYLE_CD, ");								// スタイルコード
		sql.append("    SCENE_CD, ");								// シーンコード
		sql.append("    SEX_CD, ");									// 性別コード
		sql.append("    AGE_CD, ");									// 年代コード
		sql.append("    GENERATION_CD, ");							// 世代コード
		sql.append("    SOZAI_CD, ");								// 素材コード
		sql.append("    PATTERN_CD, ");								// パターンコード
		sql.append("    ORIAMI_CD, ");								// 織り編みコード
		sql.append("    HUKA_KINO_CD, ");							// 付加機能コード
		sql.append("    SODE_CD, ");								// 袖丈コード
		sql.append("    SIZE_CD, ");								// サイズコード
		sql.append("    COLOR_CD, ");								// カラーコード
		sql.append("    KEIYAKU_SU_QT, ");							// 契約数
		sql.append("    KEIYAKU_PATTERN_KB, ");						// 契約パターン
		sql.append("    KEIYAKU_ST_DT, ");							// 契約開始期間
		sql.append("    KEIYAKU_ED_DT, ");							// 契約終了期間
		sql.append("    KUMISU_KB, ");								// 組数区分
		sql.append("    NEFUDA_KB, ");								// 値札区分
		sql.append("    NEFUDA_UKEWATASI_DT, ");					// 値札受渡日
		sql.append("    NEFUDA_UKEWATASI_KB, ");					// 値札受渡方法
		sql.append("    PC_KB, ");									// PC発行区分
		sql.append("    DAISI_NO_NB, ");							// 台紙NO
		sql.append("    UNIT_PRICE_TANI_KB, ");						// ユニットプライス-単位区分
		sql.append("    UNIT_PRICE_NAIYORYO_QT, ");					// ユニットプライス-内容量
		sql.append("    UNIT_PRICE_KIJUN_TANI_QT, ");				// ユニットプライス-基準単位量
		sql.append("    SYOHI_KIGEN_KB, ");							// 消費期限区分
		sql.append("    SYOHI_KIGEN_DT, ");							// 消費期限
		sql.append("    DAICHO_TENPO_KB, ");						// 商品台帳(店舗)
		sql.append("    DAICHO_HONBU_KB, ");						// 商品台帳(本部)
		sql.append("    DAICHO_SIIRESAKI_KB, ");					// 商品台帳(仕入先)
		sql.append("    TANA_NO_NB, ");								// 棚NO
		sql.append("    DAN_NO_NB, ");								// 段NO
		sql.append("    REBATE_FG, ");								// リベート対象フラグ
		sql.append("    MARK_GROUP_CD, ");							// マークグループ
		sql.append("    MARK_CD, ");								// マークコード
		sql.append("    YUNYUHIN_KB, ");							// 輸入品区分
		sql.append("    SANTI_CD, ");								// 原産国/産地コード
		sql.append("    D_ZOKUSEI_1_NA, ");							// 大属性１
		sql.append("    S_ZOKUSEI_1_NA, ");							// 小属性１
		sql.append("    D_ZOKUSEI_2_NA, ");							// 大属性２
		sql.append("    S_ZOKUSEI_2_NA, ");							// 小属性２
		sql.append("    D_ZOKUSEI_3_NA, ");							// 大属性３
		sql.append("    S_ZOKUSEI_3_NA, ");							// 小属性３
		sql.append("    D_ZOKUSEI_4_NA, ");							// 大属性４
		sql.append("    S_ZOKUSEI_4_NA, ");							// 小属性４
		sql.append("    D_ZOKUSEI_5_NA, ");							// 大属性５
		sql.append("    S_ZOKUSEI_5_NA, ");							// 小属性５
		sql.append("    D_ZOKUSEI_6_NA, ");							// 大属性６
		sql.append("    S_ZOKUSEI_6_NA, ");							// 小属性６
		sql.append("    D_ZOKUSEI_7_NA, ");							// 大属性７
		sql.append("    S_ZOKUSEI_7_NA, ");							// 小属性７
		sql.append("    D_ZOKUSEI_8_NA, ");							// 大属性８
		sql.append("    S_ZOKUSEI_8_NA, ");							// 小属性８
		sql.append("    D_ZOKUSEI_9_NA, ");							// 大属性９
		sql.append("    S_ZOKUSEI_9_NA, ");							// 小属性９
		sql.append("    D_ZOKUSEI_10_NA, ");						// 大属性１０
		sql.append("    S_ZOKUSEI_10_NA, ");						// 小属性１０
		sql.append("    FUJI_SYOHIN_KB, ");							// F商品区分
		sql.append("    COMMENT_TX, ");								// コメント
		sql.append("    AUTO_DEL_KB, ");							// 自動削除対象区分
		sql.append("    MST_SIYOFUKA_KB, ");						// マスタ使用不可区分
		sql.append("    HAIBAN_FG, ");								// 廃番実施フラグ
		sql.append("    SINKI_TOROKU_DT, ");						// 新規登録日
//2011.06.09 Y.Imai Mod 不稼働判定の店舗発注反映対応 AM00083 Start
//		sql.append("    HENKO_DT, ");								// 変更日
		sql.append("    '" + batchDt + "'  , ");					// 変更日
//2011.06.09 Y.Imai Mod 不稼働判定の店舗発注反映対応 AM00083 End
		sql.append("    SYOKAI_TOROKU_TS, ");						// 初回登録日

//		sql.append("    SYOKAI_USER_ID, ");							// 初回登録社員ID
		if (isBlank(rset1.getString("SYOKAI_USER_ID"))) {
			sql.append("  SYOKAI_USER_ID,");
		} else {
			sql.append("  '" + rset1.getString("SYOKAI_USER_ID") + "',");
		}
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + rset1.getString("toroku_user_id") + "',");										// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + rset1.getString("toroku_user_id") + "',");										// 更新者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// バッチ更新年月日
		sql.append("    '" + BATCH_ID + "', ");																// バッチ更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(S)
		String iYukoDt =  mst000401_LogicBean.chkNullString(rset1.getString("YUKO_DT"));
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN '" + iYukoDt + "' 			ELSE CUR_GENERATION_YUKO_DT END) ");
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN " + gennukiprice + " 		ELSE CUR_GENERATION_GENTANKA_VL END) ");
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN CUR_GENERATION_YUKO_DT 		ELSE ONE_GENERATION_YUKO_DT END) ");
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN CUR_GENERATION_GENTANKA_VL 	ELSE ONE_GENERATION_GENTANKA_VL END) ");
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN ONE_GENERATION_YUKO_DT 		ELSE TWO_GENERATION_YUKO_DT END) ");
		sql.append("    , (CASE WHEN GENTANKA_NUKI_VL <> " + gennukiprice + " THEN ONE_GENERATION_GENTANKA_VL 	ELSE TWO_GENERATION_GENTANKA_VL END) ");
		
		// add 2016.11.31 #2800 nv.cuong(S)
		sql.append("    , MIN_HACHU_QT ");						// 最低発注数
		sql.append("    , HACHU_FUKA_FLG ");					// 発注不可フラグ
		sql.append("    , PER_TXT ");							// 規格内容
		// add 2016.11.31 #2800 nv.cuong(E)
		
		// 2016/08/03 VINX h.sakamoto #1899対応 (S)
		sql.append("    , SIIRE_ZEI_KB ");							// 仕入税区分
		sql.append("    , SIIRE_TAX_RATE_KB ");						// 仕入税率区分
		sql.append("    , OROSI_ZEI_KB ");							// 卸税区分
		sql.append("    , OROSI_TAX_RATE_KB ");						// 卸税率区分
		// 2017/01/19 T.Arimoto #3608対応（S)
		//sql.append("    , " + obaiprice);					// 卸売単価
		//sql.append("    , " + obainukiprice);					// 卸売単価(税抜)
		if( null == orosiBaitankaVlBd ){
			sql.append("    , null ");							// 卸売単価
			sql.append("    , null ");							// 卸売単価(税抜)
		} else {
			sql.append("    , " + obaiprice);					// 卸売単価
			sql.append("    , " + obainukiprice);				// 卸売単価(税抜)
		}
		// 2017/01/19 T.Arimoto #3608対応（E)
		// 2016/08/03 VINX h.sakamoto #1899対応 (E)
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append("    , SYOHI_KIGEN_HYOJI_PATTER ");			// 消費期限表示パターン
		sql.append("    , PLU_HANEI_TIME ");					// PLU反映時間
		// add 2016.11.30 #2800 nv.cuong(E)
		
		// add 2016.11.22 #2800 nv.cuong(S)
		// 2017/01/19 T.Arimoto #3608対応（S)
		//sql.append("    , " + kouriprice);					// 卸売価単価(小売税)
		if( null == orosiBaitankaVlBd ){
			sql.append("    , null ");							// 卸売価単価(小売税)
		} else {
			sql.append("    , " + kouriprice);					// 卸売価単価(小売税)
		}
		// 2017/01/19 T.Arimoto #3608対応（E)
		// add 2016.11.22 #2800 nv.cuong(E)
		// No.624 MSTB051010 Add 2016.03.18 Vuong.LT(E)
		sql.append("  FROM R_SYOHIN ");
		sql.append(" WHERE BUNRUI1_CD = '" + rset1.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rset1.getString("SYOHIN_CD") + "'");
		sql.append("   AND YUKO_DT    = '" + yukoDt + "'");

		return sql.toString();
	}

	// Add 2015/06/22 DAI.BQ (S)
	/**
	 * 商品マスタASNに追加するSQLの取得
	 * @param yukoDt コピー元の有効日
	 * @return
	 * @throws SQLException
	 */
	private String getInsertASNSql(ResultSet rs, String yukoDt) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R_SYOHIN_ASN ");
		sql.append("( BUNRUI1_CD, ");
		sql.append("SYOHIN_CD, ");
		sql.append("YUKO_DT, ");
		sql.append("SYOHIN_EIJI_NA, ");
		sql.append("COUNTRY_CD, ");
		sql.append("MIN_ZAIKO_QT, ");
		sql.append("SECURITY_TAG_FG, ");
		sql.append("MEMBER_DISCOUNT_FG, ");
		sql.append("HAMPER_SYOHIN_FG, ");
		sql.append("INSERT_TS, ");
		sql.append("INSERT_USER_ID, ");
		sql.append("UPDATE_TS, ");
		sql.append("UPDATE_USER_ID, ");
		sql.append("DELETE_FG)");
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");																		// 分類１コード
		sql.append("    SYOHIN_CD, ");																		// 商品コード
		sql.append("    '" + rs.getString("YUKO_DT") + "', ");												// 有効日
		sql.append("	SYOHIN_EIJI_NA, ");																	//商品名（英字）
		sql.append("	COUNTRY_CD, ");																		//国コード
		sql.append("	MIN_ZAIKO_QT, ");																	//最低在庫数量
		sql.append("	SECURITY_TAG_FG, ");																//セキュリティタグフラグ
		sql.append("	MEMBER_DISCOUNT_FG, ");																//メンバーディ割引対象外商品フラグ
		sql.append("	HAMPER_SYOHIN_FG, ");																//ハンパー商品フラグ
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + rs.getString("toroku_user_id") + "',");										// 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");								// 削除フラグ
		sql.append("  FROM R_SYOHIN ");
		sql.append(" WHERE BUNRUI1_CD = '" + rs.getString("BUNRUI1_CD") + "' ");
		sql.append("   AND SYOHIN_CD  = '" + rs.getString("SYOHIN_CD") + "'");
		sql.append("   AND YUKO_DT    < '" + yukoDt + "'");
		return sql.toString();
	}

	private PreparedStatement getPreparedInsSyohinASNSql() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R_SYOHIN_ASN ");
		sql.append("( BUNRUI1_CD, ");
		sql.append("SYOHIN_CD, ");
		sql.append("YUKO_DT, ");
		sql.append("SYOHIN_EIJI_NA, ");
		sql.append("COUNTRY_CD, ");
		sql.append("MIN_ZAIKO_QT, ");
		sql.append("SECURITY_TAG_FG, ");

		//Add 2015.08.12 The-VV (S)
		sql.append("MAKER_CD, ");
		//Add 2015.08.12 The-VV (E)

		//Add 2016.01.11 Huy.NT (S)
		sql.append("HANBAI_HOHO_KB, ");
		//Add 2016.01.11 Huy.NT (E)

		sql.append("MEMBER_DISCOUNT_FG, ");
		sql.append("HAMPER_SYOHIN_FG, ");
		sql.append("INSERT_TS, ");
		sql.append("INSERT_USER_ID, ");
		sql.append("UPDATE_TS, ");
		sql.append("UPDATE_USER_ID, ");
		sql.append("DELETE_FG");
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append(",LABEL_SEIBUN");       //ラベル成分
		sql.append(",LABEL_HOKAN_HOHO");   //ラベル保管方法
		sql.append(",LABEL_TUKAIKATA)");    //ラベル使い方
		// add 2016.11.30 #2800 nv.cuong(E)
		
		sql.append("SELECT  ");
		sql.append("    BUNRUI1_CD, ");																		// 分類１コード
		sql.append("    SYOHIN_CD, ");																		// 商品コード
		sql.append("    ?, ");																				// 有効日
		sql.append("	SYOHIN_EIJI_NA, ");																	//商品名（英字）
		sql.append("	COUNTRY_CD, ");																		//国コード
		sql.append("	MIN_ZAIKO_QT, ");																	//最低在庫数量
		sql.append("	SECURITY_TAG_FG, ");																//セキュリティタグフラグ

		//Add 2015.08.12 The-VV (S)
		sql.append("	MAKER_CD, ");																		//メーカーコード
		//Add 2015.08.12 The-VV (E)

		//Add 2016.01.11 Huy.NT (S)
		sql.append("	HANBAI_HOHO_KB, ");																	//販売方法
		//Add 2016.01.11 Huy.NT (E)

		sql.append("	MEMBER_DISCOUNT_FG, ");																//メンバーディ割引対象外商品フラグ
		sql.append("	HAMPER_SYOHIN_FG, ");																//ハンパー商品フラグ
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    ?,");										// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    ?,");										// 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ
		
		// add 2016.11.30 #2800 nv.cuong(S)
		sql.append(",LABEL_SEIBUN");       //ラベル成分
		sql.append(",LABEL_HOKAN_HOHO");   //ラベル保管方法
		sql.append(",LABEL_TUKAIKATA");    //ラベル使い方
		// add 2016.11.30 #2800 nv.cuong(E)
		
		sql.append("  FROM R_SYOHIN_ASN ");
		sql.append(" WHERE BUNRUI1_CD = ?");
		sql.append("   AND SYOHIN_CD  = ?");
		sql.append("   AND YUKO_DT    = ?");

		PreparedStatement pstmt = db.getPrepareStatement(sql.toString());
		return pstmt;
	}

	public void setPreparedInsSyohinASNSql(PreparedStatement pstmt, ResultSet rs, String yukoDt) throws SQLException {
		int idx = 0;

		// 有効日
		idx++;
		pstmt.setString(idx, rs.getString("yuko_dt"));

		idx++;
		pstmt.setString(idx, rs.getString("toroku_user_id"));

		idx++;
		pstmt.setString(idx, rs.getString("toroku_user_id"));

		idx++;
		pstmt.setString(idx, rs.getString("BUNRUI1_CD"));

		idx++;
		pstmt.setString(idx, rs.getString("SYOHIN_CD"));

		idx++;
		pstmt.setString(idx, yukoDt);
	}

	// Add 2015/06/22 DAI.BQ (E)

	/**
	 * 商品一括修正TRへ追加前に削除のSQL文の取得
	 * @param gyosyukb 業種区分
	 * @return
	 */
	private String getDeleteTRSql(String gyosyukb)
	{
		StringBuffer strSql = new StringBuffer();

		strSql.append(" delete from");
		strSql.append("    tr_syohin_ikkatu_mente");
		strSql.append(" where");
		strSql.append("    syori_dt = '" + batchDt + "' ");
		strSql.append(" and excel_file_syubetsu = '" + gyosyukb + "' ");
		strSql.append(" and uketsuke_no = 1 ");

		return strSql.toString();
	}

	/**
	 * 商品一括修正のデータより、商品マスタに更新する、商品一括修正TRへ追加する
	 * @param rset1 商品一括修正レコード
	 * @param price
	 * @param mGyosyukb
	 * @param mSiiresakikanricd
	 * @param yukoDt
	 * @param syokaiUserId
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	private void updateData(
		ResultSet rset1,
		double[] price,
		String mGyosyukb,
		String mSiiresakiCd,
		String mSiiresakikanricd,
		String yukoDt,
		// 2017/01/19 T.Arimoto #3608対応（S)
		BigDecimal orosiBaitankaVlBd,
		// 2017/01/19 T.Arimoto #3608対応（E)
		//2016.10.13 T.han #2256対応（S)
		//HashMap mSyohin)throws IllegalArgumentException, Exception{
		HashMap mSyohin, HashMap oSyohin)throws IllegalArgumentException, Exception{
		// 2016.10.13 T.han #2256対応（E)

		//入力値チェック
		if (isInputErr(rset1, mSyohin) == false) {

			String mGenkaChgKb = rset1.getString("genka_chg_kb"); //原価変更区分
			String mBaikaChgKb = rset1.getString("baika_chg_kb"); //売価変更区分
//2016/08/05 VINX h.sakamoto #1899対応 (S)
			String mObaikaChgKb = rset1.getString("orosi_baika_chg_kb"); //卸売価変更区分
			double mOBaikaChgVl = rset1.getDouble("orosi_baika_chg_vl"); //卸売価変更値
//2016/08/05 VINX h.sakamoto #1899対応 (E)
			double mGenkaChgVl = rset1.getDouble("genka_chg_vl"); //原価変更値
			double mBaikaChgVl = rset1.getDouble("baika_chg_vl"); //売価変更値
			String mNeireHenkoKb = rset1.getString("neire_henko_kb"); //値入率変更区分
			double mNeirert = rset1.getDouble("neire_rt"); //値入率

			String mGenkaCalKb = rset1.getString("genka_cal_kb");	// 原価計算区分
			String mNeireCalKb = rset1.getString("neire_cal_kb");	// 値入率計算区分

//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
			String mZeiKb = (String) mSyohin.get("zei_kb");
			String mTaxRt = (String) mSyohin.get("tax_rt");
//2016/08/05 VINX h.sakamoto #1899対応 (S)
			String mOZeiKb = (String) mSyohin.get("orosi_zei_kb");			//卸税区分
			String mOTaxRt = (String) mSyohin.get("orosi_tax_rt");				//卸税率
			String sZeiKb = (String) mSyohin.get("siire_zei_kb");			//仕入税区分
			String sTaxRt = (String) mSyohin.get("siire_tax_rt");			//仕入税率
			// 2017/01/19 T.Arimoto #3608対応（S)
			if (mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode().equals(mObaikaChgKb)){
				// 円指定の場合
				if( null == rset1.getBigDecimal("orosi_baika_chg_vl") ){
					// 商品マスタ一括修正.卸売単価変動値 = nullの場合
					orosiBaitankaVlBd = null;
				} else {
					orosiBaitankaVlBd = BigDecimal.valueOf(price[5]);
				}
			}
			// 2017/01/19 T.Arimoto #3608対応（E)

			//原価単価、原価単価（税抜）、売価単価、売価単価（税抜）の計算
//			price = Calculation(mGenkaChgKb, mBaikaChgKb, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt);
			//原価単価、原価単価（税抜）、売価単価、売価単価（税抜）、卸売価単価、卸売価単価（税抜）の計算
			// 2017/01/19 T.Arimoto #3608対応（S)
			//price = Calculation(mGenkaChgKb, mBaikaChgKb, mObaikaChgKb, mOBaikaChgVl, sZeiKb, sTaxRt, mOZeiKb, mOTaxRt, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt);
			price = Calculation(mGenkaChgKb, mBaikaChgKb, mObaikaChgKb, mOBaikaChgVl, sZeiKb, sTaxRt, mOZeiKb, mOTaxRt, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt, orosiBaitankaVlBd);
			// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/05 VINX h.sakamoto #1899対応 (E)
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)

			//エラーがない、商品マスタのデータを更新する
			if (tankaChkFlg == false) {

				// 商品マスタ
				//2016.10.13 T.han #2256対応（S)
				//updSyohincnt += db.executeUpdate(setUpdateSyohin(rset1, price, yukoDt));
				// 2017/01/19 T.Arimoto #3608対応（S)
				//updSyohincnt += db.executeUpdate(setUpdateSyohin(rset1, price, yukoDt, oSyohin));
				updSyohincnt += db.executeUpdate(setUpdateSyohin(rset1, price, yukoDt, oSyohin, orosiBaitankaVlBd));
				// 2017/01/19 T.Arimoto #3608対応（E)
				//2016.10.13 T.han #2256対応（E)
			}

		}

		//商品一括修正TRへ追加する
		// 2017/01/19 T.Arimoto #3608対応（S)
		//MB130301_SyohinIkkatuSyuseiTR(rset1, price, mGyosyukb);
		if( null != orosiBaitankaVlBd ){
			orosiBaitankaVlBd = BigDecimal.valueOf(price[5]); // 計算後の値を設定
		}
		MB130301_SyohinIkkatuSyuseiTR(rset1, price, orosiBaitankaVlBd, mGyosyukb);
		// 2017/01/19 T.Arimoto #3608対応（E)
	}

	//2016.10.13 T.han #2256対応（S)
	/**
	 * 商品マスタに引続き更新する
	 * @param mSyohin
	 * @param oSyohin
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	private void updateDataContinue(HashMap mSyohin, HashMap oSyohin)throws IllegalArgumentException, Exception{
		
		db.executeUpdate(setUpdateSyohinContinue(mSyohin, oSyohin));
	}
	//2016.10.13 T.han #2256対応（E)

	/**
	 * 商品一括修正のデータより、商品マスタへ追加する、商品一括修正TRへ追加する
	 * @param rset1 商品一括修正レコード
	 * @param price
	 * @param mGyosyukb
	 * @param mSiiresakikanricd
	 * @param syokaiUserId
	 * @param dataFlg
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	private void insertData(
		ResultSet rset1,
		double[] price,
		String mGyosyukb,
		String mSiiresakicd,
		String mSiiresakikanricd,
		String yukoDt,
		// 2017/01/19 T.Arimoto #3608対応（S)
		BigDecimal orosiBaitankaVlBd,
		// 2017/01/19 T.Arimoto #3608対応（E)
		HashMap mSyohin)
		throws IllegalArgumentException, Exception
	{
		//入力値チェック
		if (isInputErr(rset1, mSyohin) == false) {

			String mGenkaChgKb = rset1.getString("genka_chg_kb");			//原価変更区分
			String mBaikaChgKb = rset1.getString("baika_chg_kb"); 			//売価変更区分
//2016/08/05 VINX h.sakamoto #1899対応 (S)
			String mObaikaChgKb = rset1.getString("orosi_baika_chg_kb"); 	//卸売価変更区分
			double mOBaikaChgVl = rset1.getDouble("orosi_baika_chg_vl");	//卸売価変更値
			String mOZeiKb = (String) mSyohin.get("orosi_zei_kb");			//卸税区分
			// 2016/09/27 T.Arimoto #2192対応（S)
			// String mOTaxRt = (String) mSyohin.get("o_tax_rt");			//卸税率
			String mOTaxRt = (String) mSyohin.get("orosi_tax_rt");			//卸税率
			// 2016/09/27 T.Arimoto #2192対応（E)
			String sZeiKb = (String) mSyohin.get("siire_zei_kb");			//仕入税区分
			String sTaxRt = (String) mSyohin.get("siire_tax_rt");			//仕入税率
//2016/08/05 VINX h.sakamoto #1899対応 (E)
			double mGenkaChgVl = rset1.getDouble("genka_chg_vl"); 			//原価変更値
			double mBaikaChgVl = rset1.getDouble("baika_chg_vl"); 			//売価変更値
			String mNeireHenkoKb = rset1.getString("neire_henko_kb"); 		//値入率変更区分
			double mNeirert = rset1.getDouble("neire_rt"); 					//値入率

			String mGenkaCalKb = rset1.getString("genka_cal_kb");			//原価計算区分
			String mNeireCalKb = rset1.getString("neire_cal_kb");			//値入率計算区分

//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
			String mZeiKb = (String) mSyohin.get("zei_kb");
			String mTaxRt = (String) mSyohin.get("tax_rt");

			// 2017/01/19 T.Arimoto #3608対応（S)
			if (mst005501_TankaItirituSyuseiDictionary.ENSITEI.getCode().equals(mObaikaChgKb)){
				// 円指定の場合
				if( null == rset1.getBigDecimal("orosi_baika_chg_vl") ){
					// 商品マスタ一括修正.卸売単価変動値 = nullの場合
					orosiBaitankaVlBd = null;
				} else {
					orosiBaitankaVlBd = BigDecimal.valueOf(price[5]);
				}
			}
			// 2017/01/19 T.Arimoto #3608対応（E)

//2016/08/04 VINX h.sakamoto #1899対応 (S)
			//原価単価、原価単価（税抜）、売価単価、売価単価（税抜）の計算
//				price =	Calculation(mGenkaChgKb, mBaikaChgKb, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt);
			//原価単価、原価単価（税抜）、売価単価、売価単価（税抜）、卸売価単価、卸売価単価（税抜）の計算
			// 2017/01/19 T.Arimoto #3608対応（S)
			//price =	Calculation(mGenkaChgKb, mBaikaChgKb, mObaikaChgKb, mOBaikaChgVl, sZeiKb, sTaxRt, mOZeiKb, mOTaxRt, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt);
			price =	Calculation(mGenkaChgKb, mBaikaChgKb, mObaikaChgKb, mOBaikaChgVl, sZeiKb, sTaxRt, mOZeiKb, mOTaxRt, price, mGenkaChgVl, mBaikaChgVl, mNeireHenkoKb, mNeirert, mGenkaCalKb, mNeireCalKb,mZeiKb,mTaxRt,orosiBaitankaVlBd);
			// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)

			//エラーがない、商品マスタのデータを更新する
			if (tankaChkFlg == false) {
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//				insSyohincnt += db.executeUpdate(getInsertSql(rset1, price[0],  price[1], price[2],  price[3],yukoDt));
				// 2017/01/19 T.Arimoto #3608対応（S)
				//insSyohincnt += db.executeUpdate(getInsertSql(rset1, price[0],  price[1], price[2],  price[3], price[4],  price[5], price[6], yukoDt));
				insSyohincnt += db.executeUpdate(getInsertSql(rset1, price[0],  price[1], price[2],  price[3], price[4],  price[5], price[6], orosiBaitankaVlBd, yukoDt));
				// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
//				insSyohincnt += db.executeUpdate(getInsertSql(rset1, price[0], (long) price[1],yukoDt));
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)

				// 2015/06/22 DAI.BQ (S)
				//insSyohinANScnt += db.executeUpdate(getInsertASNSql(rset1,yukoDt));
				psSyohinASNIns = getPreparedInsSyohinASNSql();
				setPreparedInsSyohinASNSql(psSyohinASNIns, rset1, yukoDt);
				psSyohinASNIns.executeUpdate();
				// 2015/06/22 DAI.BQ (E)

				String mSetYukoDt = rset1.getString("YUKO_DT");			//有効日
				String mBunrui1Cd = rset1.getString("BUNRUI1_CD");		//分類１コード
				String mSyohinCd  = rset1.getString("SYOHIN_CD");		//商品コード
				String mUserId    = rset1.getString("toroku_user_id");	//更新ユーザ
				String mDelFg     = mst000801_DelFlagDictionary.INAI.getCode();

				//ギフト商品マスタ
				comSyohin.setPreparedGiftSyohinInsSQL(psGiftIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
				psGiftIns.executeUpdate();

				// 計量器商品マスタ
				comSyohin.setPreparedKeiryokiInsSQL(psKeiryokiIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
				psKeiryokiIns.executeUpdate();

				// 商品発注納品基準日マスタ
				comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(psHachuIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
				psHachuIns.executeUpdate();
			}
		}

		//商品一括修正TRへ追加する
		// 2017/01/19 T.Arimoto #3608対応（S)
		//MB130301_SyohinIkkatuSyuseiTR(rset1, price, mGyosyukb);
		if ( null != orosiBaitankaVlBd ){
			orosiBaitankaVlBd = BigDecimal.valueOf(price[5]);
		}
		MB130301_SyohinIkkatuSyuseiTR(rset1, price, orosiBaitankaVlBd, mGyosyukb);
		// 2017/01/19 T.Arimoto #3608対応（E)
	}


	/**
	 * 商品一括修正で入力された各コードのチェックを行う
	 * @param rset1 商品一括修正レコード
	 * @param price
	 * @param mGyosyukb
	 * @param yukoDt
	 * @param syokaiUserId
	 * @throws SQLException
	 */
	private boolean isInputErr(ResultSet rs, HashMap mSyohin)
		throws SQLException{

		String mInputVal = null;
		String mCheck = null;
		String startDt = null;
		String endDt   = null;
		boolean isSiiresakiErr = false;

		// 仕入先コードのチェック
		mInputVal = rs.getString("SIIRESAKI_CD");
		if (!isBlank(mInputVal)) {
			isSiiresakiErr = siiresakicdCheck(mInputVal, rs.getString("yuko_dt"));
			if (isSiiresakiErr) {
				return true;
			}
		}

		// PLU送信日
		mInputVal = rs.getString("PLU_SEND_DT");
		if (!isBlank(mInputVal)) {

			// 有効日＞PLU送信日の場合エラー
//			if (DateDifference.getDifferenceDays(rs.getString("yuko_dt"), mInputVal) < 0) {
			if (rs.getString("yuko_dt").compareTo(mInputVal) > 0) {
				errmsg.add(msgMap.get("0181"));
				return true;
			}
		}

		//物流区分のチェック
		mInputVal = rs.getString("BUTURYU_KB");
		mCheck    = rs.getString("BUTURYU_CK");
		if (!isBlank(mInputVal) && isBlank(mCheck)) {
			errmsg.add(msgMap.get("0261"));
			return true;
		}

		//EOS区分のチェック
		mInputVal = rs.getString("EOS_KB");
		mCheck    = rs.getString("EOS_CK");
		if (!isBlank(mInputVal) && isBlank(mCheck)) {
			errmsg.add(msgMap.get("0028"));
			return true;
		}

		//分類５のチェック
		mInputVal = rs.getString("BUNRUI5_CD");
		mCheck    = rs.getString("BUNRUI5_CK");
		if (!isBlank(mInputVal) && isBlank(mCheck)) {
			errmsg.add(msgMap.get("0120"));
			return true;
		}

		//分類５のチェック（商品体系）
		mInputVal = rs.getString("BUNRUI5_CD");
		mCheck    = rs.getString("TAIKEI_BUNRUI1_CD");
		if (!isBlank(mInputVal) && isBlank(mCheck)) {
			errmsg.add(msgMap.get("0121"));
			return true;
		}

		//販売開始日、終了日のチェック
		startDt = rs.getString("HANBAI_ST_DT");
		endDt   = rs.getString("HANBAI_ED_DT");
		if (!isBlank(startDt) || !isBlank(endDt)) {

			if (isBlank(startDt)) {
				startDt = (String) mSyohin.get("hanbai_st_dt");
			}
			if (isBlank(endDt)) {
				endDt = (String) mSyohin.get("hanbai_ed_dt");
			}

//			if (DateDifference.getDifferenceDays(startDt, endDt) < 0) {
			if (startDt.compareTo(endDt) > 0) {
				errmsg.add(msgMap.get("0136"));
				return true;
			}
		}

		//店舗発注開始日、終了日のチェック
		startDt = rs.getString("TEN_HACHU_ST_DT");
		endDt   = rs.getString("TEN_HACHU_ED_DT");
		if (!isBlank(startDt) || !isBlank(endDt)) {

			if (isBlank(startDt)) {
				startDt = (String) mSyohin.get("ten_hachu_st_dt");
			}
			if (isBlank(endDt)) {
				endDt = (String) mSyohin.get("ten_hachu_ed_dt");
			}

//			if (DateDifference.getDifferenceDays(startDt, endDt) < 0) {
			if (startDt.compareTo(endDt) > 0) {
				errmsg.add(msgMap.get("0140"));
				return true;
			}
		}

		return false;
	}



	/**
	 * 商品一括修正のデータより、商品マスタを削除更新する、商品一括修正TRへ追加する
	 * @param rset1 商品一括修正レコード
	 * @param price
	 * @param mGyosyukb
	 * @param yukoDt
	 * @param syokaiUserId
	 * @throws SQLException
	 */
	private void deleteData(
		ResultSet rset1,
		double[] price,
		String mGyosyukb,
		// 2017/01/19 T.Arimoto #3608対応（S)
		BigDecimal orosiBaitankaVlBd,
		// 2017/01/19 T.Arimoto #3608対応（E)
		String yukoDt)
		throws SQLException{

		String mBunrui1Cd = rset1.getString("BUNRUI1_CD");		//分類１コード
		String mSyohinCd  = rset1.getString("SYOHIN_CD");		//商品コード
		String mUserId    = rset1.getString("toroku_user_id");	//更新ユーザ

		// 商品マスタ
		delSyohincnt += db.executeUpdate(setDeleteSyohin(rset1, yukoDt));

		// 2015/06/20 DAI.BQ (S)
		delSyohinASNcnt += db.executeUpdate(setDeleteSyohinASN(rset1, yukoDt));
		// 2015/06/20 DAI.BQ (E)

		//ギフト商品マスタ
		comSyohin.setPreparedSyohinSubDelUpSQL(psGiftUpd, mUserId, mBunrui1Cd, mSyohinCd, yukoDt);
		psGiftUpd.executeUpdate();

		// 計量器商品マスタ
		comSyohin.setPreparedSyohinSubDelUpSQL(psKeiryokiUpd, mUserId, mBunrui1Cd, mSyohinCd, yukoDt);
		psKeiryokiUpd.executeUpdate();

		// 商品発注納品基準日マスタ
		comSyohin.setPreparedSyohinSubDelUpSQL(psHachuUpd, mUserId, mBunrui1Cd, mSyohinCd, yukoDt);
		psHachuUpd.executeUpdate();

		//商品一括修正TRへ追加する
		// 2017/01/19 T.Arimoto #3608対応（S)
		//MB130301_SyohinIkkatuSyuseiTR(rset1, price, mGyosyukb);
		MB130301_SyohinIkkatuSyuseiTR(rset1, price, orosiBaitankaVlBd, mGyosyukb);
		// 2017/01/19 T.Arimoto #3608対応（E)
	}

	/**
	 * 商品一括修正のデータより、商品マスタへ削除登録する、商品一括修正TRへ追加する
	 * @param rset1 商品一括修正レコード
	 * @param price
	 * @param mGyosyukb
	 * @param syokaiUserId
	 * @param dataFlg
	 * @throws SQLException
	 */
	private void insertDelData(
		ResultSet rset1,
		double[] price,
		String mGyosyukb,
		// 2017/01/19 T.Arimoto #3608対応（S)
		BigDecimal orosiBaitankaVlBd,
		// 2017/01/19 T.Arimoto #3608対応（E)
		String yukoDt)
		throws SQLException
	{

		String mSetYukoDt = rset1.getString("YUKO_DT");			//有効日
		String mBunrui1Cd = rset1.getString("BUNRUI1_CD");		//分類１コード
		String mSyohinCd  = rset1.getString("SYOHIN_CD");		//商品コード
		String mUserId    = rset1.getString("toroku_user_id");	//更新ユーザ
		String mDelFg     = mst000801_DelFlagDictionary.IRU.getCode();

		// 商品マスタ
		delSyohincnt += db.executeUpdate(getInsertDelSql(rset1, yukoDt));

		// 2015/06/20 DAI.BQ (S)
		delSyohinASNcnt += db.executeUpdate(getInsertDelASNSql(rset1, yukoDt));
		// 2015/06/20 DAI.BQ (E)

		//ギフト商品マスタ
		comSyohin.setPreparedGiftSyohinInsSQL(psGiftIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
		psGiftIns.executeUpdate();

		// 計量器商品マスタ
		comSyohin.setPreparedKeiryokiInsSQL(psKeiryokiIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
		psKeiryokiIns.executeUpdate();

		// 商品発注納品基準日マスタ
		comSyohin.setPreparedSyohinHachuNohinkijunbiInsSQL(psHachuIns, mSetYukoDt, mUserId, mDelFg, mBunrui1Cd, mSyohinCd, yukoDt);
		psHachuIns.executeUpdate();

		//商品一括修正TRへ追加する
		// 2017/01/19 T.Arimoto #3608対応（S)
		//MB130301_SyohinIkkatuSyuseiTR(rset1, price, mGyosyukb);
		MB130301_SyohinIkkatuSyuseiTR(rset1, price, orosiBaitankaVlBd, mGyosyukb);
		// 2017/01/19 T.Arimoto #3608対応（E)
	}


	private String nullToEmpty(String s){
		if (s == null){
			return "";
		}

		return s.trim();
	}


	private boolean isBlank(String val) {
		if (val != null && val.trim().length() > 0) {
			return false;
		}
		return true;
	}


	/**
	 * 商品一括修正TRへ追加のpreparedStatement を　addBatchする
	 * @param rs 商品一括修正レコード
	 * @param genprice 原価単価
	 * @param gennukiprice 原価単価（税抜）
	 * @param baiprice 売価単価
	 * @param seqNo 受付SeqNo
	 * @param errorMsg エラーメッセージ
	 * @param syokaiuserid 初回登録者ID
	 * @param gyosyukb 業種区分
	 * @param menteFlg メンテフラグ
	 * @return
	 * @throws SQLException
	 */
	private int addInsertTRPreparedStatement(ResultSet rs,
		double genprice,
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
		double gennukiprice,
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
		//long baiprice,
		double baiprice,
//2016/08/04 VINX h.sakamoto #1899対応 (S)
		// 卸売価単価（税抜）
		// 2017/01/19 T.Arimoto #3608対応（S)
		//double oroshiBaiprice,
		BigDecimal oroshiBaiprice,
		// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)
		int seqNo,
		String errorMsg,
		String gyosyukb,
		String menteFlg)
		throws SQLException
	{

		int idx = 1;

		if (insertTRPreparedStatement == null)
		{

			StringBuffer strSql = new StringBuffer();

			strSql.append("insert into ");
			strSql.append("  tr_syohin_ikkatu_mente (");
			//取込日
			strSql.append("  syori_dt,");
			//EXCELファイル種別
			strSql.append("  excel_file_syubetsu,");
			//受付NO
			strSql.append("  uketsuke_no,");
			//受付SEQ №
			strSql.append("  uketsuke_seq,");
			//部門コード
			strSql.append("  BUNRUI1_CD,");
			//商品コード
			strSql.append("  syohin_cd,");
			//有効日
			strSql.append("  yuko_dt,");
			//原価単価
			strSql.append("  gentanka_vl,");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
			//原価単価（税抜）
			strSql.append("  gentanka_nuki_vl,");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
			//売価単価（税込）
			strSql.append("  baitanka_vl,");
			//仕入先コード
			strSql.append("  siiresaki_cd,");
			//販売開始日
			strSql.append("  hanbai_st_dt,");
			//販売終了日
			strSql.append("  hanbai_ed_dt,");
			//店別仕入先管理コード
			strSql.append("  ten_siiresaki_kanri_cd,");
			//店舗発注開始日
			strSql.append("  ten_hachu_st_dt,");
			//店舗発注終了日
			strSql.append("  ten_hachu_ed_dt,");
			//EOS区分
			strSql.append("  eos_kb,");
			//ユニットコード
			strSql.append("  BUNRUI5_CD,");
			//初回登録社員ID
			strSql.append("  syokai_user_id,");
			//棚NO
			strSql.append("  tana_no_nb,");
			//PLU反映日
			strSql.append("  PLU_SEND_DT,");
			//エラーメッセージ
			strSql.append("  err_msg,");
			//マスタメンテフラグ
			strSql.append("  mst_mainte_fg,");
			//物流区分
			strSql.append("  buturyu_kb,");
			//一括削除対象
			strSql.append("  del_target,");
			//アラーム作成フラグ
			strSql.append("  alarm_make_fg,");
			//作成年月日
			strSql.append("  insert_ts,");
			//作成者ID
			strSql.append("  insert_user_id,");
			//更新年月日
			strSql.append("  update_ts,");
			//更新者ID
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//				strSql.append("  update_user_id");
			strSql.append("  update_user_id,");
			// 卸売価単価(税抜)
			strSql.append("  orosi_baitanka_nuki_vl");
//2016/08/04 VINX h.sakamoto #1899対応 (E)
			strSql.append(" ) values ( ");
			strSql.append("  '" + batchDt + "',");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
			strSql.append("  ? ,");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  ? ,");
			strSql.append("  '" + mst006901_AlarmMakeFgDictionary.MI.getCode() + "', ");
			strSql.append("  '" + sysDt + "',");
			strSql.append("  '" + BATCH_ID + "',");
			strSql.append("  '" + sysDt + "',");
//2016/08/04 VINX h.sakamoto #1899対応 (S)
//				strSql.append("  '" + BATCH_ID + "'");
			strSql.append("  '" + BATCH_ID + "',");
			strSql.append("  ? ");
//2016/08/04 VINX h.sakamoto #1899対応 (E)
			strSql.append(") ");
			insertTRPreparedStatement = db.getPrepareStatement(strSql.toString());
		}
		insertTRPreparedStatement.setString(idx++, gyosyukb);
		//2016/10/31 VINX h.sakamoto #2551対応 (S)
//		insertTRPreparedStatement.setString(idx++, uketsukeNo);
		insertTRPreparedStatement.setString(idx++, rs.getString("UKETSUKE_NO"));
		//2016/10/31 VINX h.sakamoto #2551対応 (E)
		insertTRPreparedStatement.setLong(idx++, seqNo);
		insertTRPreparedStatement.setString(idx++, rs.getString("BUNRUI1_CD"));
		insertTRPreparedStatement.setString(idx++, rs.getString("syohin_cd"));
		insertTRPreparedStatement.setString(idx++, rs.getString("yuko_dt"));
		insertTRPreparedStatement.setDouble(idx++, genprice);
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
		insertTRPreparedStatement.setDouble(idx++, gennukiprice);
		// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
		//insertTRPreparedStatement.setLong(idx++, baiprice);
		insertTRPreparedStatement.setDouble(idx++, baiprice);
		insertTRPreparedStatement.setString(idx++, rs.getString("siiresaki_cd"));
		insertTRPreparedStatement.setString(idx++, rs.getString("hanbai_st_dt"));
		insertTRPreparedStatement.setString(idx++, rs.getString("hanbai_ed_dt"));
		insertTRPreparedStatement.setString(idx++, rs.getString("ten_siiresaki_kanri_cd"));
		insertTRPreparedStatement.setString(idx++, rs.getString("ten_hachu_st_dt"));
		insertTRPreparedStatement.setString(idx++, rs.getString("ten_hachu_ed_dt"));
		insertTRPreparedStatement.setString(idx++, rs.getString("eos_kb"));
		insertTRPreparedStatement.setString(idx++, rs.getString("BUNRUI5_CD"));
		insertTRPreparedStatement.setString(idx++, rs.getString("syokai_user_id"));
		insertTRPreparedStatement.setLong(idx++, rs.getLong("tana_no_nb"));
		insertTRPreparedStatement.setString(idx++, rs.getString("PLU_SEND_DT"));
		insertTRPreparedStatement.setString(idx++, errorMsg);
		insertTRPreparedStatement.setString(idx++, menteFlg);
		insertTRPreparedStatement.setString(idx++, rs.getString("buturyu_kb"));
		insertTRPreparedStatement.setString(idx++, rs.getString("del_target"));
//2016/08/04 VINX h.sakamoto #1899対応 (S)
		// 2017/01/19 T.Arimoto #3608対応（S)
		//insertTRPreparedStatement.setDouble(idx++, oroshiBaiprice);
		// #6265 MB130101 UPD 2020.10.30 THONG.VQ (S)
		//insertTRPreparedStatement.setDouble(idx++, oroshiBaiprice.doubleValue());
		if (oroshiBaiprice != null) {
			insertTRPreparedStatement.setDouble(idx++, oroshiBaiprice.doubleValue());
		} else {
			insertTRPreparedStatement.setDouble(idx++, 0);
		}
		// #6265 MB130101 UPD 2020.10.30 THONG.VQ (E)
		// 2017/01/19 T.Arimoto #3608対応（E)
//2016/08/04 VINX h.sakamoto #1899対応 (E)

		insertTRPreparedStatement.addBatch();
		return 0;
	}

	//2016/10/31 VINX h.sakamoto #2551対応 (S)
	/**
	 * 商品一括修正管理 更新処理用 preparedStatement
	 * @param rs 一括修正TR 受付番号別エラー件数
	 */
	private void updateKanriPreparedStatement(ResultSet rs) throws SQLException{
		int index = 1;
		int syori_status_kb = 0;
		// 処理区分分岐
		if(KANRI_SYORI_STATUS_KB_MISYORI.equals(rs.getString("ERR_CNT"))){
			syori_status_kb = KANRI_SYORI_STATUS_KB_SEIJO;
		}else{
			syori_status_kb = KANRI_SYORI_STATUS_KB_ERROR;
		}
		if (updateKanriPreparedStatement == null)
		{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE R_SYOHIN_IKKATU_MENTE_KANRI  ");
			sql.append("SET ");
			sql.append("  SYORI_DT = '" + batchDt + "' ");			// 取込日
			sql.append("  , ERR_QT = ? ");			// エラー件数
			sql.append("  , SYORI_STATUS_KB = ? ");	// 処理状況
			sql.append("  , INSERT_TS = '" + sysDt + "' ");		// 作成年月日
			sql.append("  , INSERT_USER_ID = '" + BATCH_ID + "' ");	// 作成者ID
			sql.append("  , UPDATE_TS = '" + sysDt + "' ");		// 更新年月日
			sql.append("  , UPDATE_USER_ID = '" + BATCH_ID + "' ");	// 更新者ID
			sql.append(" WHERE UKETSUKE_NO = ? ");	// 更新者ID
			updateKanriPreparedStatement = db.getPrepareStatement(sql.toString());
		}
		updateKanriPreparedStatement.setString(index++, rs.getString("ERR_CNT"));		// エラー件数
		updateKanriPreparedStatement.setInt(index++, syori_status_kb);					// エラー無=1 , エラー有=2
		updateKanriPreparedStatement.setString(index++, rs.getString("UKETSUKE_NO"));	// 受付番号

		updateKanriPreparedStatement.addBatch();
	}
	//2016/10/31 VINX h.sakamoto #2551対応 (E)

	/**
	 * 商品マスタからデータのPreparedStatement実行
	 * @param reslt 商品マスタ一括修正レコード
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeSelectSyohinPreparedStatement(ResultSet reslt) throws SQLException
	{

		if (selectSyohinPreparedStatement == null)
		{

			StringBuffer strSql = new StringBuffer();
//2013.12.18 [CUS00048]  マスタ未使用項目 (S)
			//▼SELECT句
			strSql.append("SELECT ");
			strSql.append("   RS.BUNRUI1_CD, ");
			strSql.append("   RS.SYOHIN_CD, ");
			strSql.append("   RS.YUKO_DT, ");
			strSql.append("   RS.DELETE_FG, ");
			strSql.append("   RS.GENTANKA_VL, ");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
			strSql.append("   RS.GENTANKA_NUKI_VL, ");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
			strSql.append("   RS.BAITANKA_VL, ");
			strSql.append("   NVL(RS.GYOSYU_KB,'') GYOSYU_KB, ");
			strSql.append("   TRIM(NVL(RS.SIIRESAKI_CD,'')) SIIRESAKI_CD, ");
			strSql.append("   TRIM(NVL(RS.TEN_SIIRESAKI_KANRI_CD,'')) TEN_SIIRESAKI_KANRI_CD, ");
			strSql.append("   NVL(RS.UPDATE_USER_ID,'') UPDATE_USER_ID, ");
			strSql.append("   NVL(RS.SYOKAI_USER_ID,'') SYOKAI_USER_ID, ");
			strSql.append("   RS.HANBAI_ST_DT, ");
			strSql.append("   RS.HANBAI_ED_DT, ");
			strSql.append("   RS.TEN_HACHU_ST_DT, ");
			strSql.append("   RS.TEN_HACHU_ED_DT, ");
			strSql.append("   RS.TAX_RATE_KB, ");
			strSql.append("   RS.ZEI_KB, ");
//2016/08/05 VINX h.sakamoto #1899対応 (S)
//			strSql.append("   RTR.TAX_RT ");
			// 2017.09.27 M.Akagi #5974 (S)
			//strSql.append("   RTR.TAX_RT, ");
			// 2017.09.27 M.Akagi #5974 (E)
			strSql.append("   RS.SIIRE_ZEI_KB,");
			strSql.append("   RS.SIIRE_TAX_RATE_KB,");
			strSql.append("   RS.OROSI_ZEI_KB,");
			strSql.append("   RS.OROSI_TAX_RATE_KB,");
			strSql.append("   RS.OROSI_BAITANKA_VL,");
			strSql.append("   RS.OROSI_BAITANKA_NUKI_VL,");
			// 2016.10.13 T.han #2256対応（S)
			strSql.append("   RS.CUR_GENERATION_YUKO_DT,");
			strSql.append("   RS.CUR_GENERATION_GENTANKA_VL,");
			strSql.append("   RS.ONE_GENERATION_YUKO_DT,");
			strSql.append("   RS.ONE_GENERATION_GENTANKA_VL,");
			strSql.append("   RS.TWO_GENERATION_YUKO_DT,");
			strSql.append("   RS.TWO_GENERATION_GENTANKA_VL,");
			// 2016.10.13 T.han #2256対応（E)
			// 2017.09.27 M.Akagi #5974 (S)
			// 税率
			strSql.append("   (  ");
			strSql.append("   		SELECT ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
			strSql.append("        FROM ");
			strSql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
			strSql.append("        WHERE ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
			strSql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
			strSql.append("    	     						   	SELECT MAX(YUKO_DT) ");
			strSql.append("   			  				            FROM R_TAX_RATE ");
			strSql.append("   						                WHERE TAX_RATE_KB = RS.TAX_RATE_KB 						AND		");
			strSql.append("   	                                          YUKO_DT <= ?	");
			strSql.append("   	                                    )					AND ");
			strSql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
			strSql.append("    ) AS TAX_RT ,");
			// 2017.09.27 M.Akagi #5974 (E)
			// 仕入税率
			strSql.append("   (  ");
			strSql.append("   		SELECT ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
			strSql.append("        FROM ");
			strSql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
			strSql.append("        WHERE ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB	AND ");
			strSql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
			strSql.append("    	     						   	SELECT MAX(YUKO_DT) ");
			strSql.append("   			  				            FROM R_TAX_RATE ");
			strSql.append("   						                WHERE TAX_RATE_KB = RS.SIIRE_TAX_RATE_KB 						AND		");
			// 2017.09.27 M.Akagi #5974 (S)
			//strSql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  ?)	");
			strSql.append("   	                                          YUKO_DT <= ?	");
			// 2017.09.27 M.Akagi #5974 (E)
			strSql.append("   	                                    )					AND ");
			strSql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
			strSql.append("    )AS SIIRE_TAX_RT ,");
			// 卸税率
			strSql.append("   (  ");
			strSql.append("   		SELECT ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RT ");
			strSql.append("        FROM ");
			strSql.append("   	         R_TAX_RATE TR_TAX_RATE_CK ");
			strSql.append("        WHERE ");
			strSql.append("   	         TR_TAX_RATE_CK.TAX_RATE_KB = RS.OROSI_TAX_RATE_KB	AND ");
			strSql.append("    	     TR_TAX_RATE_CK.YUKO_DT = ( ");
			strSql.append("    	     						   	SELECT MAX(YUKO_DT) ");
			strSql.append("   			  				            FROM R_TAX_RATE ");
			strSql.append("   						                WHERE TAX_RATE_KB = RS.OROSI_TAX_RATE_KB 						AND		");
			// 2017.09.27 M.Akagi #5974 (S)
			//strSql.append("   	                                          YUKO_DT <= COALESCE(RS.YUKO_DT,  ?)	");
			strSql.append("   	                                          YUKO_DT <= ?	");
			// 2017.09.27 M.Akagi #5974 (E)
			strSql.append("   	                                    )					AND ");
			strSql.append("             TR_TAX_RATE_CK.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
			strSql.append("    )AS OROSI_TAX_RT ");
//2016/08/05 VINX h.sakamoto #1899対応 (E)
			//▼FROM句
			strSql.append("FROM ");
			strSql.append(" (  ");
			strSql.append("		SELECT ");
			strSql.append("   		BUNRUI1_CD, ");
			strSql.append("         SYOHIN_CD, ");
			strSql.append("  	    YUKO_DT, ");
			strSql.append("   		DELETE_FG, ");
			strSql.append("   		GENTANKA_VL, ");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (S)
			strSql.append("   		GENTANKA_NUKI_VL, ");
// No.182 MSTB051 Add 2015.11.26 HUY.NT (E)
			strSql.append("   		BAITANKA_VL, ");
			strSql.append("   		NVL(GYOSYU_KB,'') GYOSYU_KB, ");
			strSql.append("   		TRIM(NVL(SIIRESAKI_CD,'')) SIIRESAKI_CD, ");
			strSql.append("   		TRIM(NVL(TEN_SIIRESAKI_KANRI_CD,'')) TEN_SIIRESAKI_KANRI_CD, ");
			strSql.append("   		NVL(UPDATE_USER_ID,'') UPDATE_USER_ID, ");
			strSql.append("   		NVL(SYOKAI_USER_ID,'') SYOKAI_USER_ID, ");
			strSql.append("   		HANBAI_ST_DT, ");
			strSql.append("   		HANBAI_ED_DT, ");
			strSql.append("   		TEN_HACHU_ST_DT, ");
			strSql.append("   		TEN_HACHU_ED_DT, ");
			strSql.append("   		TAX_RATE_KB, ");
			strSql.append("   		ZEI_KB, ");
			//2016/08/05 VINX h.sakamoto #1899対応 (S)
			strSql.append("   		SIIRE_ZEI_KB,");
			strSql.append("   		SIIRE_TAX_RATE_KB,");
			strSql.append("   		OROSI_ZEI_KB,");
			strSql.append("   		OROSI_TAX_RATE_KB,");
			strSql.append("   		OROSI_BAITANKA_VL,");
			strSql.append("   		OROSI_BAITANKA_NUKI_VL,");
			//2016/08/05 VINX h.sakamoto #1899対応 (E)
			// 2016.10.13 T.han #2256対応（S)
			strSql.append("			CUR_GENERATION_YUKO_DT,");
			strSql.append("			CUR_GENERATION_GENTANKA_VL,");
			strSql.append("			ONE_GENERATION_YUKO_DT,");
			strSql.append("			ONE_GENERATION_GENTANKA_VL,");
			strSql.append("			TWO_GENERATION_YUKO_DT,");
			strSql.append("			TWO_GENERATION_GENTANKA_VL,");
			// 2016.10.13 T.han #2256対応（E)
			strSql.append("   	    ( 	");
			strSql.append("   	    	SELECT ");
			strSql.append("   	    		MAX(RTR1.YUKO_DT) ");
			strSql.append("   	    	FROM ");
			strSql.append("   	    		R_TAX_RATE RTR1");
			strSql.append("   	    	WHERE ");
			// No.624 MSTB051010 Mod 2016.03.28 Vuong.LT (S)
			// strSql.append("   				RTR1.TAX_RATE_KB = TAX_RATE_KB	AND ");
			strSql.append("   				RTR1.TAX_RATE_KB = R_SYOHIN.TAX_RATE_KB	AND ");
			// No.624 MSTB051010 Mod 2016.03.28 Vuong.LT (E)
			strSql.append("   	    		RTR1.YUKO_DT <= ?				    ");
			strSql.append("   	     )MAX_YUKO_DT ");
			strSql.append("   	 FROM ");
			strSql.append("   	   	R_SYOHIN ");
			//▼WHERE句
			strSql.append("	     WHERE ");
			strSql.append("   		BUNRUI1_CD =  ");
			strSql.append(" 		? AND ");
			strSql.append("  	    SYOHIN_CD =  ");
			strSql.append("		    ? ");
			strSql.append("		 ORDER BY ");
			strSql.append("  		   YUKO_DT DESC ");
			strSql.append(" )RS  ");
			// 2017.09.27 M.Akagi #5974 (S)
//			strSql.append("   LEFT JOIN ");
//			strSql.append("   	R_TAX_RATE RTR ");
//			strSql.append("   ON ");
//			strSql.append("   	RTR.TAX_RATE_KB = RS.TAX_RATE_KB	AND ");
//			strSql.append("   	RTR.YUKO_DT = MAX_YUKO_DT			AND ");
//			strSql.append("   	RTR.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");
			// 2017.09.27 M.Akagi #5974 (E)
//			//▼SELECT句
//			strSql.append("   BUNRUI1_CD, ");
//			strSql.append("   syohin_cd, ");
//			strSql.append("   yuko_dt, ");
//			strSql.append("   delete_fg, ");
//			strSql.append("   gentanka_vl, ");
//			strSql.append("   baitanka_vl, ");
//			strSql.append("   nvl(gyosyu_kb,'') gyosyu_kb, ");
//			strSql.append("   trim(nvl(siiresaki_cd,'')) siiresaki_cd, ");
//			strSql.append("   trim(nvl(ten_siiresaki_kanri_cd,'')) ten_siiresaki_kanri_cd, ");
//			strSql.append("   nvl(UPDATE_USER_ID,'') UPDATE_USER_ID, ");
//			strSql.append("   nvl(syokai_user_id,'') syokai_user_id, ");
//			strSql.append("   hanbai_st_dt, ");
//			strSql.append("   hanbai_ed_dt, ");
//			strSql.append("   ten_hachu_st_dt, ");
//			strSql.append("   ten_hachu_ed_dt ");
//			//▼FROM句
//			strSql.append("    from   r_syohin ");
//			//▼WHERE句
//			strSql.append(" where ");
//			strSql.append("   BUNRUI1_CD =  ");
//			strSql.append(" ? ");
//			strSql.append("   and syohin_cd =  ");
//			strSql.append(" ? ");
//			strSql.append(" order by ");
//			strSql.append("   yuko_dt desc ");
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
			selectSyohinPreparedStatement = db.getPrepareStatement(strSql.toString());

		}

		String[] Syohinkomoku = new String[3]; //修正項目
		Syohinkomoku[0] = reslt.getString("yuko_dt");
		Syohinkomoku[1] = reslt.getString("BUNRUI1_CD");
		Syohinkomoku[2] = reslt.getString("syohin_cd");

//2016/08/05 VINX h.sakamoto #1899対応 (S)
//		selectSyohinPreparedStatement.setString(1, Syohinkomoku[0]);
//		selectSyohinPreparedStatement.setString(2, Syohinkomoku[1]);
//		selectSyohinPreparedStatement.setString(3, Syohinkomoku[2]);
		selectSyohinPreparedStatement.setString(1, Syohinkomoku[0]);
		selectSyohinPreparedStatement.setString(2, Syohinkomoku[0]);
		selectSyohinPreparedStatement.setString(3, Syohinkomoku[0]);
		// 2017.09.27 M.Akagi #5974 (S)
		selectSyohinPreparedStatement.setString(4, Syohinkomoku[0]);
		// 2017.09.27 M.Akagi #5974 (E)
		selectSyohinPreparedStatement.setString(5, Syohinkomoku[1]);
		selectSyohinPreparedStatement.setString(6, Syohinkomoku[2]);
//2016/08/05 VINX h.sakamoto #1899対応 (E)

//		Syohinkomoku[0] = reslt.getString("BUNRUI1_CD");
//		Syohinkomoku[1] = reslt.getString("syohin_cd");
//		//Syohinkomoku[2] = reslt.getString("yuko_dt");
//		selectSyohinPreparedStatement.setString(1, Syohinkomoku[0]);
//		selectSyohinPreparedStatement.setString(2, Syohinkomoku[1]);
//		//selectSyohinPreparedStatement.setString(3, Syohinkomoku[2]);
//2013.12.18 [CUS00048]  マスタ未使用項目 (E)
		return selectSyohinPreparedStatement.executeQuery();
	}

	/**
	 * 商品マスタ一括修正テーブルより当日のデータを抽出するSQL文の取得
	 *
	 */
	private boolean existSyoriTaisyo() throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		boolean existFg = false;

		sql.append("SELECT 1 AS CNT ");
		sql.append("  FROM DUAL ");
		sql.append(" WHERE EXISTS ");
		sql.append("       (SELECT * ");
		sql.append("          FROM R_SYOHIN_IKKATU_MENTE ");
		sql.append("         WHERE TOUROKU_TS >= '"+ DateChanger.addDate(batchDt, -1) + "000000' ");
		sql.append("           AND SYORI_KB   =  '" + SYORI_KB_MISYORI + "' ");
		sql.append("           AND DELETE_FG  =  '" + mst000801_DelFlagDictionary.INAI.getCode() + " '");
		sql.append("       ) ");

		ResultSet rs = db.executeQuery(sql.toString());

		if (rs.next()) {
			existFg = true;
		}
		db.closeResultSet(rs);

		return existFg;
	}

	//2016/10/31 VINX h.sakamoto #2551対応 (S)
	/**
	 * 商品マスタ一括修正テーブルより受付番号別エラー件数を抽出するSQL文の取得
	 * @return 受付番号(UKETSUKE_NO),エラー件数(ERR_CNT)
	 */
	private String getErrorCountSql(Boolean sqlFg){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT ");
		sql.append("  TR_MENTE.UKETSUKE_NO ");
		// 2016.11.09 M.Akagi #2722 (S)
		if (sqlFg) {
			sql.append("  , ERR_MENTE.BUNRUI1_CD AS BUNRUI1_CD ");
			sql.append("  , ERR_MENTE.SYOHIN_CD AS SYOHIN_CD ");
		}
		else {
			sql.append("  , NVL(ERR_MENTE.ERRCNT,'0') AS ERR_CNT ");
		}
		// 2016.11.09 M.Akagi #2722 (E)
		sql.append("FROM ");
		sql.append("  TR_SYOHIN_IKKATU_MENTE TR_MENTE  ");
		sql.append("  LEFT JOIN (  ");
		sql.append("    SELECT ");
		sql.append("      UKETSUKE_NO ");
		// 2016.11.09 M.Akagi #2722 (S)
		if (sqlFg) {
			sql.append("      , BUNRUI1_CD  ");
			sql.append("      , SYOHIN_CD  ");
		}
		else {
			sql.append("      , COUNT(*) AS ERRCNT  ");
		}
		// 2016.11.09 M.Akagi #2722 (E)
		sql.append("    FROM ");
		sql.append("      TR_SYOHIN_IKKATU_MENTE TR_MENTE_2  ");
		sql.append("    WHERE ");
		sql.append("      SYORI_DT = '" + batchDt + "'  ");
		sql.append("      AND INSERT_TS >= '" + sysDt + "'  ");
		sql.append("      AND MST_MAINTE_FG = '2'  ");
		sql.append("    GROUP BY ");
		sql.append("      UKETSUKE_NO ");
		// 2016.11.09 M.Akagi #2722 (S)
		if (sqlFg) {
			sql.append("      ,BUNRUI1_CD ");
			sql.append("      ,SYOHIN_CD ");
		}
		// 2016.11.09 M.Akagi #2722 (E)
		sql.append("  ) ERR_MENTE  ");
		sql.append("    ON TR_MENTE.UKETSUKE_NO = ERR_MENTE.UKETSUKE_NO  ");
		sql.append("WHERE ");
		sql.append("  TR_MENTE.SYORI_DT = '" + batchDt + "'  ");
		sql.append("  AND TR_MENTE.INSERT_TS >= '" + sysDt + "' ");
		if (sqlFg) {
			sql.append("  AND TR_MENTE.MST_MAINTE_FG = '2' ");
		}
		return sql.toString();
	}
	//2016/10/31 VINX h.sakamoto #2551対応 (E)


	/**
	 * 処理区分を変更する
	 * @param
	 * @return
	 * @throws
	 */
	private String getUpdateSyoriKb(String syoriKb, String newSyoriKb, ResultSet rs) throws SQLException
	{

		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE R_SYOHIN_IKKATU_MENTE ");
		sql.append("   SET SYORI_KB       = '" + newSyoriKb + "', ");
		sql.append("       UPDATE_TS      = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("       UPDATE_USER_ID = '" + BATCH_ID + "' ");
		sql.append(" WHERE TOUROKU_TS >= '"+ DateChanger.addDate(batchDt, -1) + "000000' ");
		sql.append("   AND SYORI_KB   =  '" + syoriKb + "' ");
		sql.append("   AND DELETE_FG  =  '" + mst000801_DelFlagDictionary.INAI.getCode() + " '");

		// 2016.11.09 M.Akagi #2722 (S)
		if (rs != null) {
			sql.append("   AND BUNRUI1_CD   =  '" + rs.getString("BUNRUI1_CD") + "' ");
			sql.append("   AND SYOHIN_CD   =  '" + rs.getString("SYOHIN_CD") + "' ");
		}
		// 2016.11.09 M.Akagi #2722 (E)

		return sql.toString();
	}


	/**
	 * バッチ処理結果TRへの登録
	 * @param
	 * @return
	 * @throws
	 */
	private String getInsBatchSyoriKekka() throws SQLException
	{
		String userLocal =ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO TR_BATCH_SYORIKEKKA (");
		sql.append("    SYORI_DT, ");																		// 処理日
		sql.append("    SYORI_SYUBETSU_KB, ");																// 処理種別区分
		sql.append("    UKETSUKE_NO, ");																	// 受付№
		sql.append("    MESSAGE_TX, ");																		// メッセージ内容
		sql.append("    INSERT_TS, ");																		// 作成年月日
		sql.append("    INSERT_USER_ID, ");																	// 作成者ID
		sql.append("    UPDATE_TS, ");																		// 更新年月日
		sql.append("    UPDATE_USER_ID, ");																	// 更新者ID
		sql.append("    DELETE_FG ");																		// 削除フラグ
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("    '" + batchDt + "', ");																// 処理日
		sql.append("    '" + mst010001_SyoriSyubetsuKbDictionary.IKKATU_SYUSEI.getCode() + "', ");			// 処理種別区分
		sql.append("    UKETSUKE_NO, ");																	// 受付№
		sql.append("    CASE WHEN MAX(MST_MAINTE_FG) = '1' ");												// メッセージ内容
		sql.append("         THEN '" + MessageUtil.getMessage("MB130101_TXT_00003",userLocal) + "'");														// メッセージ内容
		sql.append("         ELSE '" + MessageUtil.getMessage("MB130101_TXT_00004",userLocal) + "'");								// メッセージ内容
		sql.append("    END, ");
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 作成年月日
		sql.append("    '" + BATCH_ID + "',");																// 作成者ID
		sql.append("    '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");		// 更新年月日
		sql.append("    '" + BATCH_ID + "',");																// 更新者ID
		sql.append("    '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");							// 削除フラグ
		sql.append("  FROM TR_SYOHIN_IKKATU_MENTE ");
		sql.append(" WHERE SYORI_DT   = '" + batchDt + "' ");
		sql.append("   AND INSERT_TS >= '" + sysDt + "' ");
		sql.append(" GROUP BY ");
		sql.append("       UKETSUKE_NO ");

		return sql.toString();
	}

	/**
	 * ユーザーログとバッチログにログを出力します。
	 *
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
			userLog.error("ＳＱＬエラーが発生しました");
		} else {
			userLog.error("エラーが発生しました");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
	
// 2017.09.15 Add Li.Sheng #5938 対応 (S)
	/**
	 * doubleの四捨五入を行い、返します。
	 * @param dbl 数量(double)
	 * @param keta 許容する小数点以下桁数
	 * @return 四捨五入された数量(double)
	 */
	private static int round(double dbl, int keta) {
		BigDecimal dblBd = (new BigDecimal(dbl)).setScale(keta, BigDecimal.ROUND_HALF_UP);
		return dblBd.intValue();
	}
// 2017.09.15 Add Li.Sheng #5938 対応 (E)
}
