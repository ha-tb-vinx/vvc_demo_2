package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Level;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.resorces.util.SqlSupportUtil;

/**
 * <p>タイトル:単品店取扱マスタ生成</p>
 * <p>説明:単品店取扱マスタ生成を行います。  </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2006/06/27<BR>
 * @author zhouj
 */

public class MB380C07_CreateTanpinten {

	private boolean closeDb 		= false;

	//DataBaseクラス
	private MasterDataBase db	= null;

	//バッチ日付
	String MasterDT = RMSTDATEUtil.getMstDateDt();

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();
	private static final String BATCH_ID = "MB38-0C-07";
	private static final String BATCH_NA = "単品店自動作成";
	private String batchID = "";

	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB380C07_CreateTanpinten(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB380C07_CreateTanpinten() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

		ArrayList listTenpo = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		String jobId = userLog.getJobId();
		String deleteFg = null;
		String sinkiTorokuDt = null;
		String tenpoCd = null;
		int cnt = 0;

		try {
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理を開始します。");

        	PreparedStatement psSelTanpinten = db.getPrepareStatement(getSelTanpintenSQL());
        	PreparedStatement psDelTanpinten = db.getPrepareStatement(getDelTanpintenSQL());
        	PreparedStatement psInsTanpinten = db.getPrepareStatement(getInsTanpintenSQL());

            // 登録対象の店舗を取得
            listTenpo = getTenpoList();

            // 本日登録分の商品を取得
			rs = db.executeQuery(getSelSyohinSql());

			while (rs.next()) {

				// 対象店舗分繰り返し
				for (int i = 0; i < listTenpo.size(); i++) {

					// 店舗コード取得
					tenpoCd = (String)listTenpo.get(i);

					// 単品店取扱マスタ検索
					psSelTanpinten.setString(1, rs.getString("BUNRUI1_CD"));
					psSelTanpinten.setString(2, rs.getString("SYOHIN_CD"));
					psSelTanpinten.setString(3, tenpoCd);
					rs2 = psSelTanpinten.executeQuery();

					if (rs2.next()) {
						sinkiTorokuDt = rs2.getString("SINKI_TOROKU_DT");
						deleteFg = rs2.getString("DELETE_FG");
					} else {
						sinkiTorokuDt = null;
						deleteFg = null;
					}

					db.closeResultSet(rs2);

					// 単品店取扱マスタ登録
					if (sinkiTorokuDt != null && sinkiTorokuDt.equals(MasterDT) && mst000801_DelFlagDictionary.INAI.getCode().equals(deleteFg)) {

						// 本日登録分は処理しない

					} else {

						// 単品店取扱マスタが存在する場合は物理削除
						if (sinkiTorokuDt != null) {
							psDelTanpinten.setString(1, rs.getString("BUNRUI1_CD"));
							psDelTanpinten.setString(2, rs.getString("SYOHIN_CD"));
							psDelTanpinten.setString(3, tenpoCd);
							psDelTanpinten.executeUpdate();
						}

						// 単品店取扱マスタ登録
						psInsTanpinten.setString(1, rs.getString("BUNRUI1_CD"));		// 分類１コード
						psInsTanpinten.setString(2, rs.getString("SYOHIN_CD"));			// 商品コード
						psInsTanpinten.setString(3, tenpoCd);							// 店舗コード
						psInsTanpinten.setString(4, rs.getString("SYOKAI_USER_ID"));	// 作成者ID
						psInsTanpinten.setString(5, rs.getString("SYOKAI_USER_ID"));	// 更新者ID
						cnt += psInsTanpinten.executeUpdate();
					}
				}

			}
			db.closeResultSet(rs);
			psSelTanpinten.close();
			psDelTanpinten.close();
			psInsTanpinten.close();

			writeLog(Level.INFO_INT, cnt + "件の単品店取扱マスタを登録しました");

			db.commit();

		//SQLエラーが発生した場合の処理
		}catch(SQLException se){
			db.rollback();
			writeError(se);
			throw se;

		//その他のエラーが発生した場合の処理
		}catch(Exception e){
			db.rollback();
			writeError(e);
			throw e;

		//SQL終了処理
		}finally{
			db.close();
            userLog.info(Jobs.getInstance().get(jobId).getJobName() + "処理が終了しました。");
		}
	}


	/**
	 * 登録対象となる店舗の一覧を返す<br>
	 *
	 * @param なし
	 * @return 検索用SQL文
	 */
	private ArrayList getTenpoList() throws SQLException {

		Map tenpoKbMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.TANPINTEN_CREATE_TENPO_KB);
		Map tenpoCdMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.TANPINTEN_CREATE_TENPO_CD);
		ArrayList listTenpo = new ArrayList();
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TENPO_CD ");
		sb.append("  FROM R_TENPO ");
		sb.append(" WHERE ( ");
		sb.append("          TENPO_KB IN (" + SqlSupportUtil.createInString(tenpoKbMap.keySet().toArray()) + ") ");
		sb.append("          OR ");
		sb.append("          TENPO_CD IN (" + SqlSupportUtil.createInString(tenpoCdMap.keySet().toArray()) + ") ");
		sb.append("       ) ");
		sb.append("   AND COALESCE(HEITEN_DT, '99999999') >= '" + MasterDT + "' ");
		sb.append("   AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append(" ORDER BY ");
		sb.append("       TENPO_CD ");

		rs = db.executeQuery(sb.toString());

		while (rs.next()) {
			listTenpo.add(rs.getString("TENPO_CD"));
		}
		db.closeResultSet(rs);

		return listTenpo;
	}


	/**
	 * 本日登録された新規商品を取得するSQL文を生成<br>
	 *
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String getSelSyohinSql() {

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT BUNRUI1_CD, SYOHIN_CD, MAX(SYOKAI_USER_ID) AS SYOKAI_USER_ID ");
		sb.append("  FROM R_SYOHIN ");
		sb.append(" WHERE SINKI_TOROKU_DT = '" + MasterDT + "' ");
		sb.append("   AND DELETE_FG       = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append(" GROUP BY ");
		sb.append("       BUNRUI1_CD, SYOHIN_CD ");
		sb.append(" ORDER BY ");
		sb.append("       BUNRUI1_CD, SYOHIN_CD ");

		return sb.toString();
	}


	/**
	 * 単品店取扱マスタ抽出用のSQLを返す<br>
	 *
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String getSelTanpintenSQL() throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SINKI_TOROKU_DT, DELETE_FG ");
		sb.append("  FROM R_TANPINTEN_TORIATUKAI ");
		sb.append(" WHERE BUNRUI1_CD = ? ");
		sb.append("   AND SYOHIN_CD  = ? ");
		sb.append("   AND TENPO_CD   = ? ");

		return sb.toString();
	}


	/**
	 * 単品店取扱マスタ削除用のSQLを返す<br>
	 *
	 * @param なし
	 * @return 検索用SQL文
	 */
	private String getDelTanpintenSQL() throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("DELETE ");
		sb.append("  FROM R_TANPINTEN_TORIATUKAI ");
		sb.append(" WHERE BUNRUI1_CD = ? ");
		sb.append("   AND SYOHIN_CD  = ? ");
		sb.append("   AND TENPO_CD   = ? ");

		return sb.toString();
	}


	/**
	 * 単品店取扱マスタデータ新規登録用PreparedStatement
	 * @throws Exception
	 */
	private String getInsTanpintenSQL() throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		// 部門コード
		sql1.append("BUNRUI1_CD,");
		sql2.append(" ?,");

		// 商品コード
		sql1.append("SYOHIN_CD,");
		sql2.append(" ?,");

		// 店舗コード
		sql1.append("TENPO_CD,");
		sql2.append(" ?,");

		// 導入開始日
		sql1.append("DONYU_ST_DT,");
		sql2.append("'"+ MasterDT + "', ");

		// 導入終了日
		sql1.append("DONYU_ED_DT,");
		sql2.append("'99999999',");

		// NON_ACT区分
		sql1.append("NON_ACT_KB,");
		sql2.append("'1',");

		// NON_ACT送信日
		sql1.append("NON_ACT_SOSHIN_DT,");
		sql2.append("NULL,");

		// 最新発注日
		sql1.append("SAISHIN_HACYU_DT,");
		sql2.append("NULL,");

		// 最新仕入日
		sql1.append("SAISHIN_SHIIRE_DT,");
		sql2.append("NULL,");

		// 最新売上日
		sql1.append("SAISHIN_URIAGE_DT,");
		sql2.append("NULL,");

		// 発生元区分
		sql1.append("HASEIMOTO_KB,");
		sql2.append("'1',");

		// 棚割パターン
		sql1.append("TANAWARI_PATERN,");
		sql2.append("NULL,");

		// 什器NO
		sql1.append("JUKI_NB,");
		sql2.append("NULL,");

		// 棚NO
		sql1.append("TANA_NB,");
		sql2.append("NULL,");

		// フェイス数
		sql1.append("FACE_QT,");
		sql2.append("NULL,");

		// 最低陳列数
		sql1.append("MIN_TINRETU_QT,");
		sql2.append("NULL,");

		// 最大陳列数
		sql1.append("MAX_TINRETU_QT,");
		sql2.append("NULL,");

		// 基準在庫日数
		sql1.append("BASE_ZAIKO_NISU_QT,");
		sql2.append("NULL,");

		// 変更日
		sql1.append("HENKO_DT,");
		sql2.append("'").append(MasterDT).append("',");

		// 作成年月日
		sql1.append("INSERT_TS,");
		sql2.append("'").append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")).append("',");

		// 作成者ID
		sql1.append("INSERT_USER_ID,");
		sql2.append("?,");

		// 更新年月日
		sql1.append("UPDATE_TS,");
		sql2.append("'").append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")).append("',");

		// 更新者ID
		sql1.append("UPDATE_USER_ID,");
		sql2.append("?,");

		// バッチ更新年月日
		sql1.append("BATCH_UPDATE_TS,");
		sql2.append("'").append(AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")).append("',");

		// バッチ更新者ID
		sql1.append("BATCH_UPDATE_ID,");
		sql2.append("'" + BATCH_ID + "', ");

		// 削除フラグ
		sql1.append("DELETE_FG,");
		sql2.append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("',");

		// 新規登録日
		sql1.append("SINKI_TOROKU_DT ");
		sql2.append("'").append(MasterDT).append("' ");

		sql.append("INSERT INTO R_TANPINTEN_TORIATUKAI ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") VALUES ( ");
		sql.append(sql2.toString());
		sql.append(") ");

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

}