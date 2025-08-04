package mdware.master.batch.process.mb83;

import java.sql.SQLException;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.common.dictionary.mst010501_NaibuIFFgDictionary;


/**
 * <p>タイトル: MB83-04-51 ONLINE店別商品データ作成 - ONLINE店別例外反映処理</p>
 * <p>説明: TMP 店別仕入先商品データにTMP BAT 店別例外マスタを組み合わせ
 * 			店別商品情報を確定させた、店別商品データを作成する。
 * <p>著作権: Copyright (c) 2014</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.0 2015/07/21 Sou ORACLE11対応
 */
public class MB830451_OnlineTenbetuReigaiHanei {

	//各種宣言
	private DataBase db = null;											//DBアクセス用 オブジェクト(DataBaseクラスを継承）
	private BatchLog batchLog = BatchLog.getInstance();					//バッチログ出力用 オブジェクト
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private boolean closeDb = false;										//DBステータス管理用


	//バッチID & バッチ名
	private String BATCH_UID = "MB83-04-51";
	private static final String BATCH_NA  = "ONLINE店別商品データ作成 - ONLINE店別例外反映処理";

	// システム日付
	private String sysDate = null;

	// 当日日付
	private String today = null;

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB830451_OnlineTenbetuReigaiHanei(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase("rbsite_ora",true); // ジャーナル無し対応版
//			this.db = new DataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB830451_OnlineTenbetuReigaiHanei() {
		this(new DataBase("rbsite_ora",true)); // ジャーナル無し対応版
//		this(new DataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		BATCH_UID = batchId;
		execute();
	}

	/**
	 *  主処理
	 *  @param
	 *  @return
	 *  @exception Exception
　	*/

	public void execute() throws Exception {

		String jobId = userLog.getJobId();

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

			//STEP1：システム日時　取得処理
			getDates();

			//STEP3：店別商品データ一括削除処理
			truncateTenbetuSyohin();

			//STEP5：店別商品データ作成処理
			createTenbetuSyohin();

		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			e.printStackTrace();
			db.rollback();
			writeError(e);
			throw e;

		} finally {
			//DBアクセスクラス開放
			if (closeDb || db != null) {
				this.db.close();
			}
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}

	/**
	 *  STEP1：システム日時取得処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  共通日付管理マスタより、本日日付とシステム日付を取得する処理
　	*/
	private void getDates() throws Exception
	{

		try {
			//開始ログ出力
			String LogInfo = "システム日時　取得処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");

			// システム日付取得
			sysDate = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");

			//開始ログ出力
			LogInfo = "本日日付　取得処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");

			//本日日付取得
			today = RMSTDATEUtil.getBatDateDt();

			//本日日付ログ出力
			writeLog(Level.INFO_INT, "本日日付は" + today + "です");

			//終了ログ出力
			writeLog(Level.INFO_INT, LogInfo + "を終了します");

		} catch (Exception e) {
			//エラー時は、ROLLBACKしてログ出力
			e.printStackTrace();
			db.rollback();
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

	}

	/**
	 *  STEP3：店別商品データ一括削除処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  店別商品データを全件削除する。
　	*/
	private void truncateTenbetuSyohin() throws Exception
	{

		try {

			//開始ログ出力
			String LogInfo = "ONLINE店別商品データ 一括削除処理";
			writeLog(Level.INFO_INT, LogInfo + "を開始します");

			//TRUNCATE処理
			DBUtil.truncateTable(db, "DT_TENBETU_SYOHIN_ONLINE");

			//削除ログ出力
			writeLog(Level.INFO_INT, "店別商品データを削除しました");

			//終了ログ出力
			writeLog(Level.INFO_INT, LogInfo + "を終了します");

		} catch (Exception e) {
			//エラー時は、呼び出し元にスロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}
	}


	/**
	 *  STEP5：店別商品データ作成処理
	 *  @param
	 *  @return
	 *  @exception Exception
	 *  TMP BAT 店別仕入先マスタ、TMP 店別仕入先商品データ、TMP 店別商品例外データから、 店別商品データを作成する。
　	*/
	private void createTenbetuSyohin() throws Exception
	{

		try {

			//変数宣言
			int sqlCount = 0;
			String LogInfo = null;
			String sql = null;

			//開始ログ出力
			LogInfo = "ONLINE店別商品データ　作成処理を開始します";
			writeLog(Level.INFO_INT, LogInfo);

			// 商品有効日分　店別商品データ作成SQLセット
			sql = getInsertTenbetuSyohin();

			//SQL実行
			sqlCount = db.executeUpdate(sql);
			db.commit();

			//結果ログ出力
			LogInfo = "ONLINE店別商品データを" + sqlCount + "件、作成しました。";
			writeLog(Level.INFO_INT, LogInfo);

			// 店別例外有効日分　店別商品データ作成SQLセット
			sql = getInsertTenbetuSyohinReigai();

			//SQL実行
			sqlCount = db.executeUpdate(sql);
			db.commit();

			//結果ログ出力
			LogInfo = "ONLINE店別商品データを" + sqlCount + "件、作成しました。(店別例外から追加）";
			writeLog(Level.INFO_INT, LogInfo);

			// 内部IF対象フラグの調整SQLセット
			sql = getUpdateNaibuIfFg();

			//SQL実行
			sqlCount = db.executeUpdate(sql);
			db.commit();

			//結果ログ出力
			LogInfo = "ONLINE店別商品データを" + sqlCount + "件、処理しました。(内部IF対象フラグの調整）";
			writeLog(Level.INFO_INT, LogInfo);

			//終了ログ出力
			LogInfo = "ONLINE店別商品データ　作成処理を終了します";
			writeLog(Level.INFO_INT, LogInfo);

		} catch (Exception e) {
			//エラー時は、呼び出し元にエラースロー
			batchLog.getLog().error(BATCH_UID, BATCH_NA, e.getMessage());
			throw e;
		}

	}


	/**
	 *  店別商品データ作成SQL
	 *  @param
	 *  @return String
	 *  @exception Exception
	 *  商品マスタ有効日を主とする 店別商品データ INSERT 用 SQLを出力する。
　	*/
	private String getInsertTenbetuSyohin() throws Exception
	{

		//SQL組み立て
		StringBuffer sb = null;

		sb = new StringBuffer("");

		sb.append(" INSERT /*+ APPEND */ INTO DT_TENBETU_SYOHIN_ONLINE NOLOGGING ");
		sb.append(" (");
		sb.append("        TENPO_CD, ");													// 店舗コード
		sb.append("        BUNRUI1_CD, ");													// 分類１コード
		sb.append("        SYOHIN_CD, ");													// 商品コード
		sb.append("        YUKO_DT, ");														// 有効日
		sb.append("        SYOHIN_YUKO_DT, ");												// 商品有効日
		sb.append("        SYOHIN_DELETE_FG, ");											// 商品削除フラグ
		sb.append("        KEIRYOKI_DELETE_FG, ");											// 計量器削除フラグ
		sb.append("        REIGAI_YUKO_DT, ");												// 例外有効日
		sb.append("        REIGAI_DELETE_FG, ");											// 例外削除フラグ
		sb.append("        SIIRESAKI_CD, ");												// 仕入先コード
		sb.append("        GENTANKA_VL, ");													// 原単価
		sb.append("        BAITANKA_VL, ");													// 売単価
		sb.append("        BUTURYU_KB, ");													// 納品区分
		sb.append("        EOS_KB, ");														// EOS区分
		sb.append("        NON_ACT_KB, ");													// NON-ACT区分
		sb.append("        TENPO_KB, ");													// 店舗区分
		sb.append("        HENKO_DT, ");													// 変更日
		sb.append("        SIIRESAKI_SET_KB, ");											// 仕入先コードセット区分
		sb.append("        GENTANKA_SET_KB, ");												// 原単価セット区分
		sb.append("        BAITANKA_SET_KB, ");												// 売単価セット区分
		sb.append("        BUTURYU_SET_KB, ");												// 納品区分セット区分
		sb.append("        EOS_SET_KB, ");													// EOS区分セット区分
		sb.append("        HENKO_SET_KB, ");												// 変更日セット区分
		sb.append("        NAIBU_IF_FG ");													// 内部IF対象フラグ
		sb.append(" ) ");
		sb.append(" SELECT S.TENPO_CD, ");													// 店舗コード
		sb.append("        S.BUNRUI1_CD, ");												// 分類１コード
		sb.append("        S.SYOHIN_CD, ");													// 商品コード
		sb.append("        S.YUKO_DT, ");													// 有効日
		sb.append("        S.YUKO_DT, ");													// 商品有効日
		sb.append("        S.SYOHIN_DELETE_FG, ");											// 商品削除フラグ
		sb.append("        S.KEIRYOKI_DELETE_FG, ");										// 計量器削除フラグ
		sb.append("        R.YUKO_DT, ");													// 例外有効日
		sb.append("        R.DELETE_FG, ");													// 例外削除フラグ
		sb.append("        COALESCE(R.SIIRESAKI_CD, S.SIIRESAKI_CD) AS SIIRESAKI_CD,");		// 仕入先コード
		sb.append("        COALESCE(R.GENTANKA_VL,  S.GENTANKA_VL)  AS GENTANKA_VL, ");		// 原単価
		sb.append("        COALESCE(R.BAITANKA_VL,  S.BAITANKA_VL)  AS BAITANKA_VL, ");		// 売単価
		sb.append("        COALESCE(R.BUTURYU_KB,   S.BUTURYU_KB)   AS BUTURYU_KB,");		// 納品区分
		sb.append("        COALESCE(R.EOS_KB,       S.EOS_KB)       AS EOS_KB, ");			// EOS区分
		sb.append("        S.NON_ACT_KB, ");												// NON-ACT区分
		sb.append("        S.TENPO_KB, ");													// 店舗区分
		sb.append("        CASE WHEN R.HENKO_DT > S.HENKO_DT ");							// 変更日
		sb.append("             THEN R.HENKO_DT ");											//
		sb.append("             ELSE S.HENKO_DT ");											//
		sb.append("        END AS HENKO_DT,");												//
		sb.append("        CASE WHEN R.SIIRESAKI_CD IS NOT NULL ");							// 仕入先コードセット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS SIIRESAKI_SET_KB, ");										//
		sb.append("        CASE WHEN R.GENTANKA_VL IS NOT NULL ");							// 原単価セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS GENTANKA_SET_KB, ");										//
		sb.append("        CASE WHEN R.BAITANKA_VL IS NOT NULL ");							// 売単価セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS BAITANKA_SET_KB, ");										//
		sb.append("        CASE WHEN R.BUTURYU_KB IS NOT NULL ");							// 納品区分セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS BUTURYU_SET_KB, ");										//
		sb.append("        CASE WHEN R.EOS_KB IS NOT NULL ");								// EOS区分セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS EOS_KB_SET_KB, ");										//
		sb.append("        CASE WHEN R.HENKO_DT > S.HENKO_DT ");							// 変更日セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS HENKO_DT, ");												//
		sb.append("        S.NAIBU_IF_FG ");												// 内部IF対象フラグ

		sb.append("   FROM (SELECT W.*, ");
		sb.append("                (SELECT MAX(YUKO_DT) ");
		sb.append("                   FROM TMP_BAT_TENSYOHIN_REI_ONLINE ");
		sb.append("                  WHERE BUNRUI1_CD = W.BUNRUI1_CD ");
		sb.append("                    AND SYOHIN_CD  = W.SYOHIN_CD ");
		sb.append("                    AND TENPO_CD   = W.TENPO_CD ");
		sb.append("                    AND YUKO_DT   <= W.YUKO_DT ");
		sb.append("                ) AS REIGAI_YUKO_DT ");
		sb.append("           FROM WK_MB83_TENBETU_SYOHIN_ONLINE W ");
		// #6620 ADD 2022.11.30 TUNG.LT (S)
		sb.append("           WHERE W.NAIBU_IF_FG   =  '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "'");
		// #6620 ADD 2022.11.30 TUNG.LT (E)
		sb.append("        ) S ");
		sb.append("   LEFT JOIN ");
		sb.append("        TMP_BAT_TENSYOHIN_REI_ONLINE R ");
		sb.append("     ON R.BUNRUI1_CD = S.BUNRUI1_CD ");
		sb.append("    AND R.SYOHIN_CD  = S.SYOHIN_CD ");
		sb.append("    AND R.TENPO_CD   = S.TENPO_CD ");
		sb.append("    AND R.YUKO_DT    = S.REIGAI_YUKO_DT ");

		return sb.toString();
	}

	/**
	 *  店別商品データ作成SQL
	 *  @param
	 *  @return String
	 *  @exception Exception
	 *  店別例外マスタ有効日を主とする 店別商品データ INSERT 用 SQLを出力する。
　	*/
	private String getInsertTenbetuSyohinReigai() throws Exception
	{

		//SQL組み立て
		StringBuffer sb = null;

		sb = new StringBuffer("");

		sb.append(" INSERT INTO DT_TENBETU_SYOHIN_ONLINE ");
		sb.append(" (");
		sb.append("        TENPO_CD, ");													// 店舗コード
		sb.append("        BUNRUI1_CD, ");													// 分類１コード
		sb.append("        SYOHIN_CD, ");													// 商品コード
		sb.append("        YUKO_DT, ");														// 有効日
		sb.append("        SYOHIN_YUKO_DT, ");												// 商品有効日
		sb.append("        SYOHIN_DELETE_FG, ");											// 商品削除フラグ
		sb.append("        KEIRYOKI_DELETE_FG, ");											// 計量器削除フラグ
		sb.append("        REIGAI_YUKO_DT, ");												// 例外有効日
		sb.append("        REIGAI_DELETE_FG, ");											// 例外削除フラグ
		sb.append("        SIIRESAKI_CD, ");												// 仕入先コード
		sb.append("        GENTANKA_VL, ");													// 原単価
		sb.append("        BAITANKA_VL, ");													// 売単価
		sb.append("        BUTURYU_KB, ");													// 納品区分
		sb.append("        EOS_KB, ");														// EOS区分
		sb.append("        NON_ACT_KB, ");													// NON-ACT区分
		sb.append("        TENPO_KB, ");													// 店舗区分
		sb.append("        HENKO_DT, ");													// 変更日
		sb.append("        SIIRESAKI_SET_KB, ");											// 仕入先コードセット区分
		sb.append("        GENTANKA_SET_KB, ");												// 原単価セット区分
		sb.append("        BAITANKA_SET_KB, ");												// 売単価セット区分
		sb.append("        BUTURYU_SET_KB, ");												// 納品区分セット区分
		sb.append("        EOS_SET_KB, ");													// EOS区分セット区分
		sb.append("        HENKO_SET_KB, ");												// 変更日セット区分
		sb.append("        NAIBU_IF_FG ");													// 内部IF対象フラグ
		sb.append(" ) ");
		sb.append(" SELECT S.TENPO_CD, ");													// 店舗コード
		sb.append("        S.BUNRUI1_CD, ");												// 分類１コード
		sb.append("        S.SYOHIN_CD, ");													// 商品コード
		sb.append("        R.YUKO_DT, ");													// 有効日
		sb.append("        S.YUKO_DT, ");													// 商品有効日
		sb.append("        S.SYOHIN_DELETE_FG, ");											// 商品削除フラグ
		sb.append("        S.KEIRYOKI_DELETE_FG, ");										// 計量器削除フラグ
		sb.append("        R.YUKO_DT, ");													// 例外有効日
		sb.append("        R.DELETE_FG, ");													// 例外削除フラグ
		sb.append("        COALESCE(R.SIIRESAKI_CD, S.SIIRESAKI_CD) AS SIIRESAKI_CD,");		// 仕入先コード
		sb.append("        COALESCE(R.GENTANKA_VL,  S.GENTANKA_VL)  AS GENTANKA_VL, ");		// 原単価
		sb.append("        COALESCE(R.BAITANKA_VL,  S.BAITANKA_VL)  AS BAITANKA_VL, ");		// 売単価
		sb.append("        COALESCE(R.BUTURYU_KB,   S.BUTURYU_KB)   AS BUTURYU_KB,");		// 納品区分
		sb.append("        COALESCE(R.EOS_KB,       S.EOS_KB)       AS EOS_KB, ");			// EOS区分
		sb.append("        S.NON_ACT_KB, ");												// NON-ACT区分
		sb.append("        S.TENPO_KB, ");													// 店舗区分
		sb.append("        CASE WHEN R.HENKO_DT > S.HENKO_DT ");							// 変更日
		sb.append("             THEN R.HENKO_DT ");											//
		sb.append("             ELSE S.HENKO_DT ");											//
		sb.append("        END AS HENKO_DT,");												//
		sb.append("        CASE WHEN R.SIIRESAKI_CD IS NOT NULL ");							// 仕入先コードセット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS SIIRESAKI_SET_KB, ");										//
		sb.append("        CASE WHEN R.GENTANKA_VL IS NOT NULL ");							// 原単価セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS GENTANKA_SET_KB, ");										//
		sb.append("        CASE WHEN R.BAITANKA_VL IS NOT NULL ");							// 売単価セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS BAITANKA_SET_KB, ");										//
		sb.append("        CASE WHEN R.BUTURYU_KB IS NOT NULL ");							// 納品区分セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS BUTURYU_SET_KB, ");										//
		sb.append("        CASE WHEN R.EOS_KB IS NOT NULL ");								// EOS区分セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS EOS_KB_SET_KB, ");										//
		sb.append("        CASE WHEN R.HENKO_DT > S.HENKO_DT ");							// 変更日セット区分
		sb.append("             THEN '2' ");												//
		sb.append("             ELSE '1' ");												//
		sb.append("        END AS HENKO_DT, ");												//
		sb.append("        S.NAIBU_IF_FG ");												// 内部IF対象フラグ

		sb.append("   FROM TMP_BAT_TENSYOHIN_REI_ONLINE R ");
		sb.append("  INNER JOIN ");
		sb.append("        WK_MB83_TENBETU_SYOHIN_ONLINE S ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append("     ON S.BUNRUI1_CD = R.BUNRUI1_CD ");
//		sb.append("    AND S.SYOHIN_CD  = R.SYOHIN_CD ");
		sb.append("     ON  ");
		sb.append("    	 S.SYOHIN_CD  = R.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sb.append("    AND S.TENPO_CD   = R.TENPO_CD ");
		sb.append("    AND S.YUKO_DT    = R.SYOHIN_YUKO_DT ");

		sb.append("  WHERE R.YUKO_DT <> R.SYOHIN_YUKO_DT ");

		return sb.toString();
	}


	/**
	 *  内部IF対象フラグ更新SQL
	 *  @param
	 *  @return String
	 *  @exception Exception
	 *  内部IF対象フラグを調整するSQLを出力する。
　	*/
	private String getUpdateNaibuIfFg() throws Exception
	{

		//SQL組み立て
		StringBuffer sb = null;

		sb = new StringBuffer("");

		// この方がわかりやすい？
	      // 2015/07/21 Sou ORACLE11対応 Start
//		sb.append(" UPDATE ( ");
//		sb.append("     SELECT /*+ BYPASS_UJVC */ ");
//		sb.append("            B.NAIBU_IF_FG ");
//		sb.append("       FROM (SELECT BUNRUI1_CD, SYOHIN_CD, TENPO_CD, MAX(YUKO_DT) AS YUKO_DT ");
//		sb.append("               FROM DT_TENBETU_SYOHIN_ONLINE ");
//		sb.append("              WHERE YUKO_DT    <= '" + DateChanger.addDate(today, 1) + "' ");
//		sb.append("                AND NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
//		sb.append("              GROUP BY ");
//		sb.append("                    BUNRUI1_CD, SYOHIN_CD, TENPO_CD ");
//		sb.append("             HAVING COUNT(*) > 1 ");
//		sb.append("            ) A ");
//		sb.append("      INNER JOIN ");
//		sb.append("            DT_TENBETU_SYOHIN_ONLINE B ");
//		sb.append("         ON B.BUNRUI1_CD = A.BUNRUI1_CD ");
//		sb.append("        AND B.SYOHIN_CD  = A.SYOHIN_CD ");
//		sb.append("        AND B.TENPO_CD   = A.TENPO_CD ");
//		sb.append("      WHERE B.YUKO_DT    < A.YUKO_DT ");
//		sb.append("        AND B.NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
//		sb.append(" ) SET NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYOGAI.getCode() + "' ");

		sb.append(" MERGE INTO ");
		sb.append("     DT_TENBETU_SYOHIN_ONLINE B ");
		sb.append(" USING ");
		sb.append("      (SELECT BUNRUI1_CD, SYOHIN_CD, TENPO_CD, MAX(YUKO_DT) AS YUKO_DT ");
		sb.append("                FROM DT_TENBETU_SYOHIN_ONLINE ");
		sb.append("               WHERE YUKO_DT    <= '" + DateChanger.addDate(today, 1) + "' ");
		sb.append("                 AND NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
		sb.append("               GROUP BY ");
		sb.append("                     BUNRUI1_CD, SYOHIN_CD, TENPO_CD ");
		sb.append("              HAVING COUNT(*) > 1 ");
		sb.append("       ) A ");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append(" ON (B.BUNRUI1_CD = A.BUNRUI1_CD ");
//		sb.append("    AND B.SYOHIN_CD  = A.SYOHIN_CD ");
		sb.append(" ON ( ");
		sb.append("    	 B.SYOHIN_CD  = A.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sb.append("    AND B.TENPO_CD   = A.TENPO_CD ");
		sb.append("    AND B.YUKO_DT    < A.YUKO_DT ) ");
		sb.append(" WHEN MATCHED THEN ");
		sb.append("  UPDATE SET ");
		sb.append("     B.NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYOGAI.getCode() + "' ");
		sb.append("  WHERE B.NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
		// 2015/07/21 Sou ORACLE11対応 End
//		sb.append(" UPDATE ( ");
//		sb.append("     SELECT /*+ BYPASS_UJVC */ ");
//		sb.append("            TS.NAIBU_IF_FG ");
//		sb.append("       FROM (SELECT /*+ NO_MERGE */ ");
//		sb.append("                    BUNRUI1_CD, ");
//		sb.append("                    SYOHIN_CD, ");
//		sb.append("                    TENPO_CD, ");
//		sb.append("                    YUKO_DT ");
//		sb.append("               FROM TMP_BAT_TENSYOHIN_REIGAI W ");
//		sb.append("              WHERE YUKO_DT <> SYOHIN_YUKO_DT ");
//		sb.append("                AND YUKO_DT <= '" + DateChanger.addDate(today, 1) + "' ");
//		sb.append("                AND YUKO_DT = ");
//		sb.append("                        (SELECT MAX(YUKO_DT) ");
//		sb.append("                           FROM TMP_BAT_TENSYOHIN_REIGAI ");
//		sb.append("                          WHERE BUNRUI1_CD = W.BUNRUI1_CD ");
//		sb.append("                            AND SYOHIN_CD  = W.SYOHIN_CD ");
//		sb.append("                            AND TENPO_CD   = W.TENPO_CD ");
//		sb.append("                            AND YUKO_DT   <= '" + DateChanger.addDate(today, 1) + "' ");
//		sb.append("                        ) ");
//		sb.append("            ) R ");
//		sb.append("      INNER JOIN ");
//		sb.append("            DT_TENBETU_SYOHIN TS ");
//		sb.append("         ON TS.TENPO_CD   = R.TENPO_CD ");
//		sb.append("        AND TS.BUNRUI1_CD = R.BUNRUI1_CD ");
//		sb.append("        AND TS.SYOHIN_CD  = R.SYOHIN_CD ");
//		sb.append("        AND TS.YUKO_DT    < R.YUKO_DT ");
//		sb.append("    WHERE TS.YUKO_DT    <= '" + DateChanger.addDate(today, 1) + "' ");
//		sb.append("      AND TS.NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYO.getCode() + "' ");
//		sb.append(" ) ");
//		sb.append(" SET NAIBU_IF_FG = '" + mst010501_NaibuIFFgDictionary.TAISYOGAI.getCode() + "' ");

		return sb.toString();
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


	/**
	 * test実行用メイン関数
	 * @param args
	 */
	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";
		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);
		MB830451_OnlineTenbetuReigaiHanei batch = new MB830451_OnlineTenbetuReigaiHanei();
		try {
			batch.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
