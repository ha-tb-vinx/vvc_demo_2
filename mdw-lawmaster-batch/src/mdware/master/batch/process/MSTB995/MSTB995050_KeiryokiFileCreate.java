package mdware.master.batch.process.MSTB995;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

import org.apache.log4j.Level;

/**
 * 
 * <p>タイトル: MSTB995050_KeiryokiFileCreate.java クラス</p>
 * <p>説明　: 商品マスタデータファイル、添加物マスタデータファイルを作成する。</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: VINX</p>
 * @version 3.00 (2013.12.25) T.Ooshiro [CUS00065] ランドローム様対応 計量器インターフェイス仕様変更対応
 * @version 3.01 (2014.04.16) M.Ayukawa [シス0194] ファイル作成不備
 *
 */
public class MSTB995050_KeiryokiFileCreate {

	// 区切り文字（カンマ区切り）
	private static final String SPLIT_CODE = ",";
	// 改行文字
	private static final String RETURN_CODE = System.getProperty("line.separator");
	// 変換文字（ワイルドカードの文字）
	private static final String WILD_CARD_CODE = "*";

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	/** CSVファイルパス */
	private String csvFilePath = null;
	/** 商品マスタデータファイル名 */
	private String keiryokiFtpSyohin = null;
	/** 店別商品マスタデータファイル名 */
	private String keiryokiFtpTenkabutu = null;

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	// バッチ日付
	private String batchDt = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB995050_KeiryokiFileCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB995050_KeiryokiFileCreate() {
		this(new DataBase(CONNECTION_STR));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		try {

			//バッチ処理件数をカウント（ログ出力用）
			int iRec = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール項目取得
			getSystemControl();

			writeLog(Level.INFO_INT, "出力先ディレクトリ：" + csvFilePath);

			// 商品マスタデータファイル作成
			writeLog(Level.INFO_INT, "商品マスタデータファイル・添加物マスタデータファイル作成処理を開始します。");
			iRec = createCSVFile(getIfKeiryokiSelectSql());
			writeLog(Level.INFO_INT, "商品マスタデータファイル・添加物マスタデータファイルを" + iRec + "ファイル作成しました。");

			writeLog(Level.INFO_INT, "処理を終了します。");

			//SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(se);
			throw se;

			//その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			writeLog(Level.ERROR_INT, "ロールバックしました。");
			this.writeError(e);
			throw e;

			//SQL終了処理
		} finally {
			if (closeDb || db != null) {
				db.close();
			}
		}

	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		// CSVファイルパス取得
		csvFilePath = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.PATH_SEND_KEIRYOKI);
		if (csvFilePath == null || csvFilePath.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、ＣＳＶ出力先のパスが取得できませんでした");
			throw new Exception();
		}

		// 商品マスタデータファイル名取得
		keiryokiFtpSyohin = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.KEIRYOKI_FTP_SYOHIN);
		if (keiryokiFtpSyohin == null || keiryokiFtpSyohin.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、商品マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// 添加物マスタデータファイル名取得
		keiryokiFtpTenkabutu = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.KEIRYOKI_FTP_TENKABUTU);
		if (keiryokiFtpTenkabutu == null || keiryokiFtpTenkabutu.trim().length() == 0) {
			this.writeLog(Level.INFO_INT, "システムコントロールから、添加物マスタデータファイル名が取得できませんでした");
			throw new Exception();
		}

		// バッチ日付
		batchDt = RMSTDATEUtil.getBatDateDt();

	}

	/**
	 * CSVファイルを作成します。
	 * @param sqlStatement	検索SQL
	 * @return ファイル出力件数
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	private int createCSVFile(String sqlStatement) throws IOException, SQLException, Exception {

		ResultSet rs = null;
		String fileFullName = null;
		File file = null;
		FileWriter fwSyohin = null;
		BufferedWriter bwSyohin = null;
		FileWriter fwTenkabutsu = null;
		BufferedWriter bwTenkabutsu = null;
		StringBuffer sbSyohin = new StringBuffer();
		StringBuffer sbTenkabutsu = new StringBuffer();
		int dataCnt = 0;
		int fileCnt = 0;
		String tmpDptCd = "";

		try {
			// CSVファイル格納パス、ファイル名
			file = new File(csvFilePath);

			if (file.exists() == false) {
				// ディレクトリが見つからなければ
				this.writeLog(Level.ERROR_INT, csvFilePath + " が存在しません。");
				throw new Exception();
			}

			// データ取得
			rs = db.executeQuery(sqlStatement);

			while (rs.next()) {

				if (!tmpDptCd.equals(rs.getString("BUNRUI1_CD").trim())) {

					// ファイルクローズ
					if (bwSyohin != null) {
						bwSyohin.close();
					}
					if (fwSyohin != null) {
						fwSyohin.close();
					}
					if (bwTenkabutsu != null) {
						bwTenkabutsu.close();
					}
					if (fwTenkabutsu != null) {
						fwTenkabutsu.close();
					}

					if (dataCnt > 0) {

						writeLog(Level.INFO_INT, "商品マスタデータファイル・添加物マスタデータファイルを" + dataCnt + "件作成しました。");
						dataCnt = 0;
					}

					// DPTコード退避・ファイル名取得
					tmpDptCd = rs.getString("BUNRUI1_CD").trim();
					String syohinFileName = keiryokiFtpSyohin.replace(WILD_CARD_CODE, tmpDptCd);
					String tenkabutsuFileName = keiryokiFtpTenkabutu.replace(WILD_CARD_CODE, tmpDptCd);
					writeLog(Level.INFO_INT, "商品マスタデータファイル（" + syohinFileName + "）・添加物マスタデータファイル（" + tenkabutsuFileName + "）作成処理を開始します。");

					// ファイルオープン(商品マスタ)
					fileFullName = file + "/" + syohinFileName;
					fwSyohin = new FileWriter(fileFullName, false);
					bwSyohin = new BufferedWriter(fwSyohin);

					// ファイルオープン(添加物マスタ)
					fileFullName = file + "/" + tenkabutsuFileName;
					fwTenkabutsu = new FileWriter(fileFullName, false);
					bwTenkabutsu = new BufferedWriter(fwTenkabutsu);
					
					fileCnt++;
				}

				// 行データ作成(商品マスタ)
				sbSyohin.append(createSyohinRowDataString(rs));

				// 行データ出力(商品マスタ)
				bwSyohin.write(sbSyohin.toString());
				sbSyohin.setLength(0);

				// 行データ作成(添加物マスタ)
				sbTenkabutsu.append(createTenkabutsuRowDataString(rs));

				// 行データ出力(添加物マスタ)
				bwTenkabutsu.write(sbTenkabutsu.toString());
				sbTenkabutsu.setLength(0);

				dataCnt++;

			}

			if (dataCnt > 0) {

				writeLog(Level.INFO_INT, "商品マスタデータファイル・添加物マスタデータファイルを" + dataCnt + "件作成しました。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// ファイルクローズ
			if (bwSyohin != null) {
				bwSyohin.close();
			}
			if (fwSyohin != null) {
				fwSyohin.close();
			}
			if (bwTenkabutsu != null) {
				bwTenkabutsu.close();
			}
			if (fwTenkabutsu != null) {
				fwTenkabutsu.close();
			}
		}

		return fileCnt;

	}

	/**
	 * CSVファイルへ出力する明細データを作成する(商品マスタ)
	 * @param		ResultSet			取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createSyohinRowDataString(ResultSet rs) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		sb.append(createCsvString(batchDt));								// 日付
		sb.append(createCsvString(""));										// 機種区分
		sb.append(createCsvString(rs.getString("BUNRUI1_CD")));				// 呼出部門
		sb.append(createCsvString(rs.getString("SYOHIN_YOBIDASI")));		// 商品呼出コード（PLU#)
		sb.append(createCsvString(rs.getString("SONZAI_FG")));				// 存在フラグ
		sb.append(createCsvString(rs.getString("SYOHIN_CD")));				// JANコード
		sb.append(createCsvString(rs.getString("BAR_KB")));					// バー区分
		sb.append(createCsvString(rs.getString("HINMEI_FG")));				// 品名１行目文字フラグ
		sb.append(createCsvString(rs.getString("HINMEI_NA")));				// 品名１行目
		sb.append(createCsvString(""));										// 品名２行目文字フラグ
		sb.append(createCsvString(""));										// 品名２行目
		sb.append(createCsvString(""));										// 品名３行目文字フラグ
		sb.append(createCsvString(""));										// 品名３行目
		sb.append(createCsvString(""));										// 品名４行目文字フラグ
		sb.append(createCsvString(""));										// 品名４行目
		sb.append(createCsvString(""));										// 品名５行目文字フラグ
		sb.append(createCsvString(""));										// 品名５行目
		sb.append(createCsvString(""));										// 小分類
		sb.append(createCsvString(rs.getString("TEIKAN_KB")));				// 計量区分
		sb.append(createCsvString(""));										// 単位建値
		sb.append(createCsvString(""));										// 単価
		sb.append(createCsvString(""));										// マークダウン区分
		sb.append(createCsvString(""));										// マークダウン金額
		sb.append(createCsvString(rs.getString("TEIKAN_TANI_KB")));			// 定貫単位
		sb.append(createCsvString(rs.getString("TEIKAN_WEIGHT_QT")));		// 内容量
		sb.append(createCsvString(rs.getString("FUTAI_WEIGHT_QT")));		// 風袋量
		sb.append(createCsvString(rs.getString("KAKOBI_PRINT_KB")));		// 加工日印字区分
		sb.append(createCsvString(""));										// 加工日加算日数
		sb.append(createCsvString(rs.getString("SYOMIKIKAN_KB")));			// 賞味日印字区分
		sb.append(createCsvString(rs.getString("SYOMIKIKAN_VL")));			// 賞味期間
		sb.append(createCsvString(""));										// 有効日印字区分
		sb.append(createCsvString(""));										// 有効期限
		sb.append(createCsvString(rs.getString("KAKOJIKOKU_PRINT_KB")));	// 加工時刻印字区分
		sb.append(createCsvString(""));										// 加工時刻指定
		sb.append(createCsvString(""));										// 発行枚数
		sb.append(createCsvString(""));										// ラベラー連動区分
		sb.append(createCsvString(""));										// 発行ラベラー区分
		sb.append(createCsvString(""));										// 広告文番号
		sb.append(createCsvString(""));										// 産地名称１番号
		sb.append(createCsvString(""));										// イメージ１番号
		sb.append(createCsvString(""));										// カロリー
		sb.append(createCsvString(""));										// 製造者名番号
		sb.append(createCsvString(rs.getString("SENTAKU_COMMENT1_CD")));	// 選択コメント１ 
		sb.append(createCsvString(rs.getString("SENTAKU_COMMENT2_CD")));	// 選択コメント２
		sb.append(createCsvString(""));										// 選択コメント３
		sb.append(createCsvString(""));										// 選択コメント４
		sb.append(createCsvString(""));										// 選択コメント５
		sb.append(createCsvString(""));										// ラベルサイズ番号
		sb.append(createCsvString(""));										// 連動ラベルサイズ番号（第2ﾗﾍﾞﾗｰ連動時ﾗﾍﾞﾙ）
		sb.append(createCsvString(rs.getString("TENKABUTU_YOBIDASI")));		// 添加物呼出コード
		sb.append(createCsvString(""));										// トレイ番号
		sb.append(createCsvString(""));										// 上限重量
		sb.append(createCsvString(""));										// 下限重量
		sb.append(createCsvString(""));										// 会員売価
		sb.append(createCsvString(""));										// 税金種類
		sb.append(createCsvString(rs.getString("REC_HINMEI_KANJI_NA")));	// レシート品名
		sb.append(createCsvString(""));										// フリーコード１
		sb.append(createCsvString(""));										// フリーコード２
		sb.append(createCsvString(""));										// フリーコード３
		sb.append(createCsvString(""));										// 原価
		sb.append(createCsvString(""));										// 売価変更
		sb.append(createCsvString(""));										// 小計値下
		sb.append(createCsvString(""));										// 時間帯実績
		sb.append(createCsvString(""));										// 一括削除
		sb.append(createCsvString(""));										// 割戻商品
		sb.append(createCsvString(""));										// 割戻倍率
		sb.append(createCsvString(""));										// 分類コード
		sb.append(createCsvString(""));										// 無償商品
		sb.append(createCsvString(""));										// 減算商品
		sb.append(createCsvString(""));										// 販促商品
		sb.append(createCsvString(""));										// ＮＵＴＲＩＴＩＯＮ商品
		sb.append(createCsvString(""));										// ＦＳＤイメージ１
		sb.append(createCsvString(""));										// ＦＳＤイメージ２
		sb.append(createCsvString(""));										// ｎｏｎｅＦＳＤイメージ１
		sb.append(createCsvString(""));										// ｎｏｎｅＦＳＤイメージ２
		sb.append(createCsvString(""));										// ボーナスポイント
		sb.append(createCsvString(""));										// ステップディスカウント開始日
		sb.append(createCsvString(""));										// ステップディスカウント終了日
		sb.append(createCsvString(""));										// ステップ値引ステータス
		sb.append(createCsvString(""));										// ステップ値引ポイント１
		sb.append(createCsvString(""));										// ステップ値引下限１
		sb.append(createCsvString(""));										// ステップ値引ポイント２
		sb.append(createCsvString(""));										// ステップ値引下限２
		sb.append(createCsvString(""));										// ステップディスカウント開始時間
		sb.append(createCsvString(""));										// ステップディスカウント終了時間
		sb.append(createCsvString(""));										// マーク印字用ステータス
		sb.append(createCsvString(""));										// スペシャルメッセージ呼出番号
		sb.append(createCsvString(""));										// イメージ１０
		sb.append(createCsvString(""));										// イメージ７
		sb.append(createCsvString(""));										// イメージ２
		sb.append(createCsvString(""));										// イメージ３
		sb.append(createCsvString(""));										// イメージ４
		sb.append(createCsvString(""));										// 風袋量２
		sb.append(createCsvString(""));										// 添加物呼出コード２
		sb.append(createCsvString(""));										// 単価計算方法
		sb.append(createCsvString(""));										// 定重量
		sb.append(createCsvString(""));										// 定単価
		sb.append(createCsvString(""));										// 未使用
		sb.append(createCsvEndString(rs.getString("TRACEABILITY_FG")));		// トレースフラッグ

		return sb;
	}

	/**
	 * CSVファイルへ出力する明細データを作成する(添加物マスタ)
	 * @param		ResultSet			取得データ
	 * @return		StringBuffer	１行分の文字列
	 * @throws		SQLException
	 * @throws		Exception
	 */
	private StringBuffer createTenkabutsuRowDataString(ResultSet rs) throws SQLException, Exception {
		StringBuffer sb = new StringBuffer();

		sb.append(createCsvString(batchDt));							// 日付
		sb.append(createCsvString(""));									// 機種区分
		sb.append(createCsvString(rs.getString("BUNRUI1_CD")));			// 呼出部門
		sb.append(createCsvString(rs.getString("SYOHIN_YOBIDASI")));	// 添加物呼出コード
		sb.append(createCsvString(rs.getString("SONZAI_FG")));			// 存在フラグ
		sb.append(createCsvString(rs.getString("TENKABUTU_01_FG")));	// 添加物1行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_01_NA")));	// 添加物1行目
		sb.append(createCsvString(rs.getString("TENKABUTU_02_FG")));	// 添加物2行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_02_NA")));	// 添加物2行目
		sb.append(createCsvString(rs.getString("TENKABUTU_03_FG")));	// 添加物3行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_03_NA")));	// 添加物3行目
		sb.append(createCsvString(rs.getString("TENKABUTU_04_FG")));	// 添加物4行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_04_NA")));	// 添加物4行目
		sb.append(createCsvString(rs.getString("TENKABUTU_05_FG")));	// 添加物5行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_05_NA")));	// 添加物5行目
		sb.append(createCsvString(rs.getString("TENKABUTU_06_FG")));	// 添加物6行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_06_NA")));	// 添加物6行目
		sb.append(createCsvString(rs.getString("TENKABUTU_07_FG")));	// 添加物7行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_07_NA")));	// 添加物7行目
		sb.append(createCsvString(rs.getString("TENKABUTU_08_FG")));	// 添加物8行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_08_NA")));	// 添加物8行目
		sb.append(createCsvString(rs.getString("TENKABUTU_09_FG")));	// 添加物9行目文字ﾌﾗｸﾞ
		sb.append(createCsvString(rs.getString("TENKABUTU_09_NA")));	// 添加物9行目
		sb.append(createCsvString(rs.getString("TENKABUTU_10_FG")));	// 添加物10行目文字ﾌﾗｸﾞ
		sb.append(createCsvEndString(rs.getString("TENKABUTU_10_NA")));	// 添加物10行目

		return sb;
	}

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_KEIRYOKIを取得するSQLを取得する
	 * 
	 * @return IF_KEIRYOKI取得SQL
	 */
	private String getIfKeiryokiSelectSql() throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("	 IK.BUNRUI1_CD ");
		sql.append("	,IK.SYOHIN_YOBIDASI ");
		sql.append("	,IK.SONZAI_FG ");
		sql.append("	,IK.SYOHIN_CD ");
		sql.append("	,IK.BAR_KB ");
		sql.append("	,IK.HINMEI_FG ");
		sql.append("	,IK.HINMEI_NA ");
		sql.append("	,IK.TEIKAN_KB ");
		sql.append("	,IK.TEIKAN_TANI_KB ");
		sql.append("	,IK.TEIKAN_WEIGHT_QT ");
		sql.append("	,IK.FUTAI_WEIGHT_QT ");
		sql.append("	,IK.KAKOBI_PRINT_KB ");
		sql.append("	,IK.SYOMIKIKAN_KB ");
		sql.append("	,IK.SYOMIKIKAN_VL ");
		sql.append("	,IK.KAKOJIKOKU_PRINT_KB ");
		sql.append("	,IK.SENTAKU_COMMENT1_CD ");
		sql.append("	,IK.SENTAKU_COMMENT2_CD ");
		sql.append("	,IK.TENKABUTU_YOBIDASI ");
		sql.append("	,IK.REC_HINMEI_KANJI_NA ");
		sql.append("	,IK.TRACEABILITY_FG ");
		sql.append("	,IK.TENKABUTU_01_FG ");
		sql.append("	,IK.TENKABUTU_01_NA ");
		sql.append("	,IK.TENKABUTU_02_FG ");
		sql.append("	,IK.TENKABUTU_02_NA ");
		sql.append("	,IK.TENKABUTU_03_FG ");
		sql.append("	,IK.TENKABUTU_03_NA ");
		sql.append("	,IK.TENKABUTU_04_FG ");
		sql.append("	,IK.TENKABUTU_04_NA ");
		sql.append("	,IK.TENKABUTU_05_FG ");
		sql.append("	,IK.TENKABUTU_05_NA ");
		sql.append("	,IK.TENKABUTU_06_FG ");
		sql.append("	,IK.TENKABUTU_06_NA ");
		sql.append("	,IK.TENKABUTU_07_FG ");
		sql.append("	,IK.TENKABUTU_07_NA ");
		sql.append("	,IK.TENKABUTU_08_FG ");
		sql.append("	,IK.TENKABUTU_08_NA ");
		sql.append("	,IK.TENKABUTU_09_FG ");
		sql.append("	,IK.TENKABUTU_09_NA ");
		sql.append("	,IK.TENKABUTU_10_FG ");
		sql.append("	,IK.TENKABUTU_10_NA ");
		sql.append("FROM ");
		sql.append("	IF_KEIRYOKI IK ");
		sql.append("ORDER BY ");
		sql.append("	 IK.BUNRUI1_CD ");
		sql.append("	,IK.SYOHIN_YOBIDASI ");

		return sql.toString();
	}

	/********** 共通処理 **********/

	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @return CSV出力データ
	 */
	private String createCsvString(String str) {
		return createCsvStringCommon(str, false);
	}

	private String createCsvEndString(String str) {
		return createCsvStringCommon(str, true);
	}

	/**
	 * CSV出力データ編集共通処理
	 * @param str
	 * @param endFg true:最終項目、false:最終項目以外
	 * @return CSV出力データ
	 */
	private String createCsvStringCommon(String str, boolean endFg) {
		String val = "";
		if (str != null) {
			val = str.trim();
		}

		// セパレータの判定。最終項目の場合は改行する。
		if (endFg) {
			val += RETURN_CODE;
		} else {
			val += SPLIT_CODE;
		}

		return val;
	}

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message) {
		String jobId = userLog.getJobId();

		switch (level) {
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId, Jobs.getInstance().get(jobId).getJobName(), message);
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
		batchLog.getLog().error(jobId, Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}

}
