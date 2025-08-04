package mdware.master.batch.process.MSTB906;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:IF計上伝票加工取込</p>
 * <p>著作権: Copyright (c) </p>
 * <p>会社名: VINX Corp.</p>
 * @author o.uemura
 * @version 3.00 (2013/12/10) O.Uemura [CUS00050] ランドローム様対応 POS売価不一致勧告対応
 */
public class MSTB906020_IFKeijyoDenpyoImport {

	// 処理日間隔
	private static final int SPAN_DAYS = 1;
	
	private MasterDataBase db = null;
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// バッチ日付
	private String batchDate = null;
	// 有効日
	private String yukoDate = null;

	/*
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB906020_IFKeijyoDenpyoImport(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB906020_IFKeijyoDenpyoImport() {
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

			// 処理開始ログ
			writeLog(Level.INFO_INT, "IF計上伝票加工取込処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();
			writeLog(Level.INFO_INT, "バッチ日付：" + batchDate);
			writeLog(Level.INFO_INT, "有効日：" + yukoDate);

			// バッチ処理件数をカウント（ログ出力用）
			int iRec = 0; 

			// 一時テーブル(WK_IF_KEIJYO_DENPYO)のTRUNCATE
			writeLog(Level.INFO_INT, "WK_IF計上伝票(WK_IF_KEIJYO_DENPYO)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "WK_IF_KEIJYO_DENPYO");
			writeLog(Level.INFO_INT, "WK_IF計上伝票(WK_IF_KEIJYO_DENPYO)のデータをクリアしました。");
			
			// 一時テーブル(WK_IF_KEIJYO_DENPYO)へ全行登録する
			writeLog(Level.INFO_INT, "WK_IF計上伝票(WK_IF_KEIJYO_DENPYO)への登録処理を開始します。");
			iRec = db.executeUpdate(getInsWkIfKeijyoDenpyoSQL());
			db.commit();
			writeLog(Level.INFO_INT, "WK_IF計上伝票(WK_IF_KEIJYO_DENPYO)への登録処理を完了しました (" + iRec + "件)");
			
			
			// 計上伝票データ(DT_KEIJYO_DENPYO)への更新処理（重複するレコードが存在する場合）
			writeLog(Level.INFO_INT, "計上伝票データ(DT_KEIJYO_DENPYO)への更新処理を開始します。");
			iRec = 0; // 値のリセット
			iRec = db.executeUpdate(getUpdateDtKeijyoDenpyoSQL());
			writeLog(Level.INFO_INT, "計上伝票データ(DT_KEIJYO_DENPYO)への更新処理を完了しました (" + iRec + "件)");
			
			// 計上伝票データ(DT_KEIJYO_DENPYO)への登録処理（重複するレコードが存在しない場合）
			writeLog(Level.INFO_INT, "計上伝票データ(DT_KEIJYO_DENPYO)への登録処理を開始します。");
			iRec = 0; // 値のリセット
			iRec = db.executeUpdate(getInsDtKeijyoDenpyoSQL());
			writeLog(Level.INFO_INT, "計上伝票データ(DT_KEIJYO_DENPYO)への登録処理を完了しました (" + iRec + "件)");
			
			
			// 値引売変データ(DT_NEBIKI_HANBAI)のTRUNCATE
			writeLog(Level.INFO_INT, "値引売変データ(DT_NEBIKI_BAIHEN)のデータクリア処理を開始します。");
			DBUtil.truncateTable(db, "DT_NEBIKI_BAIHEN");
			writeLog(Level.INFO_INT, "値引売変データ(DT_NEBIKI_BAIHEN)のデータをクリアしました。");
			
			// 値引売変データ(DT_NEBIKI_HANBAI)への登録処理
			writeLog(Level.INFO_INT, "値引売変データ(DT_NEBIKI_BAIHEN)への登録処理を開始します。");
			iRec = 0; // 値のリセット
			iRec = db.executeUpdate(getInsDtNebikiBaihenSQL());
			db.commit();
			writeLog(Level.INFO_INT, "値引売変データ(DT_NEBIKI_BAIHEN)への登録処理を完了しました (" + iRec + "件)");
			
			writeLog(Level.INFO_INT, "IF計上伝票加工取込処理を終了します。");

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
     * <p>WK_IF計上伝票への登録処理</p>
     * 
     * 仕入管理の「計上伝票ワーク」「計上伝票明細ワーク」から該当レコードを抽出し、WK_IF計上伝票へ登録する。
     * 
     * @param なし
     */
	private String getInsWkIfKeijyoDenpyoSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append("    WK_IF_KEIJYO_DENPYO ");
		sql.append("( ");
		sql.append("     COMP_CD ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,SYOHIN_CD ");
		sql.append("    ,DENPYO_DT ");
		sql.append("    ,KEIJYO_DT ");
		sql.append("    ,DENPYO_KB ");
		sql.append("    ,RIYU_KB ");
		sql.append("    ,RIYU_SYOSAI_KB ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SURYO_QT ");
		sql.append("    ,BAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIKA_ZEIKOMI_VL ");
		sql.append("    ,INSERT_TS ");
		sql.append("    ,INSERT_USER_ID ");
		sql.append("    ,UPDATE_TS ");
		sql.append("    ,UPDATE_USER_ID ");
		sql.append(") ");
		sql.append("SELECT ");
		sql.append("     WKD.COMP_CD ");
		sql.append("    ,WKD.TENPO_CD ");
		sql.append("    ,WKM.SYOHIN_CD ");
		sql.append("    ,WKD.DENPYO_DT ");
		sql.append("    ,WKD.KANRI_DT ");
		sql.append("    ,WKD.DENPYO_KB ");
		sql.append("    ,WKD.RIYU_KB ");
		sql.append("    ,WKD.RIYU_SYOSAI_KB ");
		sql.append("    ,MAX(WKD.BUNRUI1_CD) AS BUNRUI1_CD ");
		sql.append("    ,MAX(WKM.BUNRUI2_CD) AS BUNRUI2_CD ");
		sql.append("    ,MAX(WKM.BUNRUI5_CD) AS BUNRUI5_CD ");
		sql.append("    ,SUM(WKM.SURYO_QT) AS SURYO_QT ");
		sql.append("    ,SUM(WKM.BAIKA_ZEINUKI_VL) AS BAIKA_ZEINUKI_VL ");
		sql.append("    ,SUM(WKM.BAIKA_ZEIKOMI_VL) AS BAIKA_ZEIKOMI_VL ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("FROM ");
		sql.append("     WK_KEIJO_DENPYO WKD ");
		sql.append("    ,WK_KEIJO_MEI WKM ");
		sql.append("WHERE ");
		sql.append("    WKD.COMP_CD = WKM.COMP_CD AND ");
		sql.append("    WKD.DATA_HASEIMOTO_KB = WKM.DATA_HASEIMOTO_KB AND ");
		sql.append("    WKD.TRAN_SEQ_NB = WKM.TRAN_SEQ_NB ");
		sql.append("GROUP BY ");
		sql.append("     WKD.COMP_CD ");
		sql.append("    ,WKD.TENPO_CD ");
		sql.append("    ,WKM.SYOHIN_CD ");
		sql.append("    ,WKD.DENPYO_DT ");
		sql.append("    ,WKD.KANRI_DT ");
		sql.append("    ,WKD.DENPYO_KB ");
		sql.append("    ,WKD.RIYU_KB ");
		sql.append("    ,WKD.RIYU_SYOSAI_KB ");
		
		return sql.toString();
	}

    /**
     * <p>計上伝票データへの更新処理</p>
     * 
     * 計上伝票データに対し、「会社コード」「店舗コード」「商品コード」「伝票日」「計上日」<br>
     * 「伝票区分」「理由区分」「理由詳細区分」が一致するレコードの更新処理を行う
     * 
     * @param なし
     */
	private String getUpdateDtKeijyoDenpyoSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE ");
		sql.append("    DT_KEIJYO_DENPYO DKD ");
		sql.append("SET ");
		sql.append("( ");
		sql.append("     BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SURYO_QT ");
		sql.append("    ,BAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIKA_ZEIKOMI_VL ");
		sql.append("    ,UPDATE_TS ");
		sql.append("    ,UPDATE_USER_ID ");
		sql.append(") = ( ");
		sql.append("    SELECT ");
		sql.append("     BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SURYO_QT ");
		sql.append("    ,BAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIKA_ZEIKOMI_VL ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("    FROM ");
		sql.append("        WK_IF_KEIJYO_DENPYO WIKD ");
		sql.append("    WHERE ");
		sql.append("        DKD.COMP_CD = WIKD.COMP_CD AND ");
		sql.append("        DKD.TENPO_CD = WIKD.TENPO_CD AND ");
		sql.append("        DKD.SYOHIN_CD = WIKD.SYOHIN_CD AND ");
		sql.append("        DKD.DENPYO_DT = WIKD.DENPYO_DT AND ");
		sql.append("        DKD.KEIJYO_DT = WIKD.KEIJYO_DT AND ");
		sql.append("        DKD.DENPYO_KB = WIKD.DENPYO_KB AND ");
		sql.append("        DKD.RIYU_KB = WIKD.RIYU_KB AND ");
		sql.append("        DKD.RIYU_SYOSAI_KB = WIKD.RIYU_SYOSAI_KB ");
		sql.append(") ");
		sql.append("WHERE EXISTS ");
		sql.append("( ");
		sql.append("    SELECT ");
		sql.append("        1 ");
		sql.append("    FROM ");
		sql.append("        WK_IF_KEIJYO_DENPYO WIKD_EXST ");
		sql.append("    WHERE ");
		sql.append("        WIKD_EXST.COMP_CD = DKD.COMP_CD AND ");
		sql.append("        WIKD_EXST.TENPO_CD = DKD.TENPO_CD AND ");
		sql.append("        WIKD_EXST.SYOHIN_CD = DKD.SYOHIN_CD AND ");
		sql.append("        WIKD_EXST.DENPYO_DT = DKD.DENPYO_DT AND ");
		sql.append("        WIKD_EXST.KEIJYO_DT = DKD.KEIJYO_DT AND ");
		sql.append("        WIKD_EXST.DENPYO_KB = DKD.DENPYO_KB AND ");
		sql.append("        WIKD_EXST.RIYU_KB = DKD.RIYU_KB AND ");
		sql.append("        WIKD_EXST.RIYU_SYOSAI_KB = DKD.RIYU_SYOSAI_KB ");
		sql.append(") ");
		
		return sql.toString();
	}

    /**
     * <p>計上伝票データへの登録処理</p>
     * 
     * 計上伝票データに対し、「会社コード」「店舗コード」「商品コード」「伝票日」「計上日」<br>
     * 「伝票区分」「理由区分」「理由詳細区分」が一致しない（：新規）レコードの登録処理を行う
     * 
     * @param なし
     */
	private String getInsDtKeijyoDenpyoSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append("    DT_KEIJYO_DENPYO DKD ");
		sql.append("( ");
		sql.append("     COMP_CD ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,SYOHIN_CD ");
		sql.append("    ,DENPYO_DT ");
		sql.append("    ,KEIJYO_DT ");
		sql.append("    ,DENPYO_KB ");
		sql.append("    ,RIYU_KB ");
		sql.append("    ,RIYU_SYOSAI_KB ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SURYO_QT ");
		sql.append("    ,BAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIKA_ZEIKOMI_VL ");
		sql.append("    ,INSERT_TS ");
		sql.append("    ,INSERT_USER_ID ");
		sql.append("    ,UPDATE_TS ");
		sql.append("    ,UPDATE_USER_ID ");
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("     COMP_CD ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,SYOHIN_CD ");
		sql.append("    ,DENPYO_DT ");
		sql.append("    ,KEIJYO_DT ");
		sql.append("    ,DENPYO_KB ");
		sql.append("    ,RIYU_KB ");
		sql.append("    ,RIYU_SYOSAI_KB ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SURYO_QT ");
		sql.append("    ,BAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIKA_ZEIKOMI_VL ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("FROM ");
		sql.append("    WK_IF_KEIJYO_DENPYO WIKD ");
		sql.append("WHERE NOT EXISTS ");
		sql.append("( ");
		sql.append("    SELECT ");
		sql.append("        * ");
		sql.append("    FROM ");
		sql.append("        DT_KEIJYO_DENPYO DKD_EXST ");
		sql.append("    WHERE ");
		sql.append("        DKD_EXST.COMP_CD = WIKD.COMP_CD AND ");
		sql.append("        DKD_EXST.TENPO_CD = WIKD.TENPO_CD AND ");
		sql.append("        DKD_EXST.SYOHIN_CD = WIKD.SYOHIN_CD AND ");
		sql.append("        DKD_EXST.DENPYO_DT = WIKD.DENPYO_DT AND ");
		sql.append("        DKD_EXST.KEIJYO_DT = WIKD.KEIJYO_DT AND ");
		sql.append("        DKD_EXST.DENPYO_KB = WIKD.DENPYO_KB AND ");
		sql.append("        DKD_EXST.RIYU_KB = WIKD.RIYU_KB AND ");
		sql.append("        DKD_EXST.RIYU_SYOSAI_KB = WIKD.RIYU_SYOSAI_KB ");
		sql.append(") ");
		
		return sql.toString();
	}

    /**
     * <p>値引売変データへの登録処理</p>
     * 
     * 値引売変データに対し、レコードの登録処理を行う
     * 
     * @param なし
     */
	private String getInsDtNebikiBaihenSQL()  throws SQLException {
        String systemDt = AbstractMDWareDateGetter.getDefaultProductTimestamp( mst000101_ConstDictionary.CONNECTION_STR );
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append("    DT_NEBIKI_BAIHEN DNB ");
		sql.append("( ");
		sql.append("     HENKO_DT ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,TANPIN_CD ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,BAIHEN_QT ");
		sql.append("    ,BAIHENBAIKA_ZEINUKI_VL ");
		sql.append("    ,BAIHENBAIKA_ZEIKOMI_VL ");
		sql.append("    ,NEBIKI_QT ");
		sql.append("    ,NEBIKIBAIKA_ZEINUKI_VL ");
		sql.append("    ,NEBIKIBAIKA_ZEIKOMI_VL ");
		sql.append("    ,HAIKI_QT ");
		sql.append("    ,HAIKIBAIKA_ZEINUKI_VL ");
		sql.append("    ,HAIKIBAIKA_ZEIKOMI_VL ");
		sql.append("    ,INSERT_TS ");
		sql.append("    ,INSERT_USER_ID ");
		sql.append("    ,UPDATE_TS ");
		sql.append("    ,UPDATE_USER_ID ");
		sql.append(") ");
		sql.append("SELECT  ");
		sql.append("     KEIJYO_DT ");
		//sql.append("    ,SUBSTR(TRIM(TENPO_CD), LENGTH(TRIM(TENPO_CD))-3,4) ");
		sql.append("    ,SUBSTR(TRIM(TENPO_CD), LENGTH(TRIM(TENPO_CD))-5,6) ");
		sql.append("    ,SYOHIN_CD ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('01','02') AND RIYU_SYOSAI_KB IN ('91','93') THEN SURYO_QT ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB = '01' AND RIYU_SYOSAI_KB = '91' THEN BAIKA_ZEINUKI_VL ");
		sql.append("                     WHEN RIYU_KB = '02' AND RIYU_SYOSAI_KB = '93' THEN - BAIKA_ZEINUKI_VL ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB = '01' AND RIYU_SYOSAI_KB = '91' THEN BAIKA_ZEIKOMI_VL ");
		sql.append("                     WHEN RIYU_KB = '02' AND RIYU_SYOSAI_KB = '93' THEN - BAIKA_ZEIKOMI_VL ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('02') AND RIYU_SYOSAI_KB IN ('81','87','89') THEN SURYO_QT ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('02') AND RIYU_SYOSAI_KB IN ('81','87','89') THEN BAIKA_ZEINUKI_VL ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('02') AND RIYU_SYOSAI_KB IN ('81','87','89') THEN BAIKA_ZEIKOMI_VL ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('03') THEN SURYO_QT ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('03') THEN BAIKA_ZEINUKI_VL ELSE 0 END) ");
		sql.append("    ,SUM(CASE WHEN RIYU_KB IN ('03')  THEN BAIKA_ZEIKOMI_VL ELSE 0 END) ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("    ,'"+systemDt+"' ");
		sql.append("    ,'"+userLog.getJobId()+"' ");
		sql.append("FROM ");
		sql.append("    DT_KEIJYO_DENPYO DKD ");
		sql.append("WHERE ");
		sql.append("    DKD.DENPYO_KB = '20' ");
		sql.append("GROUP BY ");
		sql.append("     KEIJYO_DT ");
		sql.append("    ,TENPO_CD ");
		sql.append("    ,SYOHIN_CD ");
		sql.append("    ,BUNRUI1_CD ");
		sql.append("    ,BUNRUI2_CD ");
		sql.append("    ,BUNRUI5_CD ");

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

		// 有効日
		yukoDate = DateChanger.addDate(batchDate, SPAN_DAYS);
		
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

}
