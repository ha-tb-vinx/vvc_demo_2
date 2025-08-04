package mdware.master.batch.process.MSTB994;

import java.nio.CharBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.db.util.DBUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.command.MstbGetPluMaster;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst003601_TenpoKbDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 *
 * <p>タイトル: MSTB994111_IfStiPosSyubetuCreate.java クラス</p>
 * <p>説明　: IF_指定日POS支払種別メンテ、IF_指定日POS特売種別メンテを作成する。</p>
 * <p>著作権: Copyright (c) 2017</p>
 * <p>会社名: VINX</p>
 * @version 1.00 (2017.01.16) 新規作成 #1749対応 T.Han
 * @version 1.02 (2017.02.02) S.Takayama #3853 FIVIMART対応
 * @version 1.03 (2017.04.26) #4824 T.Han FIVImart対応
 * @version 1.04 (2017.05.19) #5044 M.Son FIVImart対応
 * @version 1.05 (2021.12.20) #6406 KHOI.ND
 * @version 1.06 (2022.05.05) SIEU.D #6553
 */
public class MSTB994111_IfStiPosSyubetuCreate {

	/** DBインスタンス */
	private DataBase db = null;
	/** DB接続フラグ */
	private boolean closeDb = false;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private BatchUserLog userLog = BatchUserLog.getInstance();

	// テーブル
	private static final String TABLE_IF_STI_POS_PAYMENT  = "IF_STI_POS_PAYMENT";  // IF_指定日_POS支払種別メンテ
	private static final String TABLE_IF_STI_POS_DISCOUNT = "IF_STI_POS_DISCOUNT"; // IF_指定日_POS特売種別メンテ

	// 2017.01.12 T.Han #3583対応（S)
	/** 自家消費レシート印字文言（VN） */
	private static final int LENGTH_SHIHARAI_SYUBETSU_SUB_NA = 40;
	// 2017.01.12 T.Han #3583対応（E)

	/** DB接続文字列 */
	private static final String CONNECTION_STR = mst000101_ConstDictionary.CONNECTION_STR;

	/** 最大レシート発行数 */
	private String maxImumReceipt = "";
	private String MAXIMUM_RECEIPT = "";

    /** 伝送ヘッダーレコードリスト */
    private List densoRecordList = null;
    /** 受付No */
    private String uketsukeNo = "";
    /** 店舗コード */
    private String tenpoCd = "";
    /** 対象日 */
    private String taisyoDt = "";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MSTB994111_IfStiPosSyubetuCreate(DataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new DataBase(CONNECTION_STR);
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MSTB994111_IfStiPosSyubetuCreate() {
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
			int iRec1 = 0;
			int iRec2 = 0;

			// トランザクションログ有無（AutoCommitモード）
			// （trueを指定すると、トランザクションログ出力をしない分の速度向上
			// 　が見込めますが、コミット・ロールバックが無効となります。）
			db.setDisableTransaction(false); // false : ログ有り  true : ログ無し

			// 処理開始ログ
			writeLog(Level.INFO_INT, "処理を開始します。");

			RMSTDATEUtil.getBatDateDt();
			// システムコントロール情報取得
			this.getSystemControl();
			MAXIMUM_RECEIPT = maxImumReceipt;

			// IF_指定日_POS支払種別メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_PAYMENT);
			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテ 削除処理を終了します。");

			// IF_指定日_POS特売種別メンテのTRUNCATE
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテ 削除処理を開始します。");
			DBUtil.truncateTable(db, TABLE_IF_STI_POS_DISCOUNT);
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテ 削除処理を終了します。");

            // PLU指定日マスタ取得
			ResultSet rsData = new MstbGetPluMaster().process(db);

			densoRecordList = new ArrayList();
            while (rsData.next()){
                // 引数情報 受付No、店舗コード、対象日取得
            	uketsukeNo = rsData.getString("UKETSUKE_NO");
                tenpoCd = rsData.getString("TENPO_CD");
                taisyoDt = rsData.getString("TAISYO_DT");
                MSTB994111_IfStiPosCategoryCreateRow rowData = new MSTB994111_IfStiPosCategoryCreateRow(uketsukeNo, tenpoCd, taisyoDt);
                densoRecordList.add(rowData);
            }

			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテ 登録処理を開始します。");
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテ 登録処理を開始します。");

			for (Object densoRecord : densoRecordList) {
				MSTB994111_IfStiPosCategoryCreateRow rowData = (MSTB994111_IfStiPosCategoryCreateRow) densoRecord;
            	uketsukeNo = rowData.getUketsukeNo();
                tenpoCd = rowData.getTenpoCd();
                taisyoDt = rowData.getTaisyoDt();
    			// IF_指定日_POS支払種別メンテの登録
    			iRec1 += db.executeUpdate(getIfStiPosPaymentInsertSql(uketsukeNo, tenpoCd, taisyoDt));
    			// IF_指定日_POS特売種別メンテの登録
    			iRec2 += db.executeUpdate(getIfStiPosDiscountInsertSql(uketsukeNo, tenpoCd, taisyoDt));

    			db.commit();
			}

			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテを" + iRec1 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテを" + iRec2 + "件作成しました。");
			writeLog(Level.INFO_INT, "IF_指定日_POS支払種別メンテ登録処理（WK→IF）を終了します。");
			writeLog(Level.INFO_INT, "IF_指定日_POS特売種別メンテ登録処理（WK→IF）を終了します。");

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

/********** ＳＱＬ生成処理 **********/

	/**
	 * IF_指定日_POS支払種別メンテを作成するSQLを取得する
	 *
	 * @return IF_STI_POS_PAYMENT登録SQL
	 */
	private String getIfStiPosPaymentInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_PAYMENT ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,SHIHARAI_SYUBETSU_SEQ ");
		sql.append("	,SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("	,SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("	,POS_SEQ ");
		sql.append("	,OVER_TYPE ");
		sql.append("	,NEED_AUTHORITY ");
		sql.append("	,NEED_EXPIRY ");
		sql.append("	,CARD_NUMBER ");
		sql.append("	,PROCESS_TYPE ");
		sql.append("	,SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("	,CARD_LENGTH ");
		sql.append("	,SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	,DISPLAY_FG ");
		sql.append("	,VOID_FG ");
		sql.append("	,RETURN_FG ");
		sql.append("	,OPEN_DRAWER_FG ");
		sql.append("	,EXTRA_RECEIPT ");
		sql.append("	,MAXIMUM_RECEIPT ");
		sql.append("	,YUKO_START_DT ");
		sql.append("	,YUKO_END_DT ");
		sql.append("	,GYOTAI_KB");
		sql.append("	,JIKASYOHI_KB ");
		sql.append("	,JIKASYOHI_RECEIPT_VN_NA ");
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO ");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT ");
		sql.append("	,'" + tenpoCd + "' AS TENPO_CD ");
		sql.append("	,'A' AS TOROKU_ID ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SEQ ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_EN_NA ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_VN_NA ");
		sql.append("	,RP.POS_SEQ ");
		sql.append("	,RP.OVER_TYPE ");
		sql.append("	,' ' AS NEED_AUTHORITY ");
		sql.append("	,' ' AS NEED_EXPIRY ");
		sql.append("	,' ' AS CARD_NUMBER ");
		sql.append("	,RP.PROCESS_TYPE ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_GROUP_SEQ ");
		sql.append("	,' ' AS CARD_LENGTH ");
        // #3853 MSTB908 2017.02.02 S.Takayama (S)
		//sql.append("	,CASE WHEN RDC.PARAMETER_TX = '1' AND RP.SHIHARAI_SYUBETSU_GROUP_SEQ IS NULL THEN '     ' ");
		//sql.append("	ELSE RP.SHIHARAI_SYUBETSU_SUB_CD END SHIHARAI_SYUBETSU_SUB_CD ");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SUB_CD ");
        // #3853 MSTB908 2017.02.02 S.Takayama (E)
		sql.append("	,RP.DISPLAY_FG ");
		sql.append("	,RP.VOID_FG ");
		sql.append("	,RP.RETURN_FG ");
		sql.append("	,RP.OPEN_DRAWER_FG ");
		sql.append("	,RP.EXTRA_RECEIPT ");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAXIMUM_RECEIPT");
		sql.append("	,NVL(RP.YUKO_START_DT, '        ') AS YUKO_START_DT ");
		sql.append("	,NVL(RP.YUKO_END_DT, '        ') AS YUKO_END_DT ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append("    ,CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN 'Y' ELSE 'N' END JIKASYOHI_KB ");
		sql.append("    ,CASE WHEN  RP.SHIHARAI_SYUBETSU_CD = 'MRS' THEN RP.SHIHARAI_SYUBETSU_SUB_NA ELSE '"+spaces(LENGTH_SHIHARAI_SYUBETSU_SUB_NA)+"' END JIKASYOHI_RECEIPT_VN_NA ");
		sql.append(" FROM ");
		sql.append("	R_PAYMENT RP ");
        // #3853 MSTB908 2017.02.02 S.Takayama (S)
		//sql.append("	LEFT JOIN ");
		//sql.append("		R_DICTIONARY_CONTROL RDC ");
		//sql.append("	ON ");
		//sql.append("		RP.SHIHARAI_SYUBETSU_GROUP_CD = RDC.DICTIONARY_ID ");
		//sql.append("		AND RDC.PARAMETER_ID = 'SHIHARAI_SYUBETSU_GROUP_CD' ");
        // #3853 MSTB908 2017.02.02 S.Takayama (E)
		sql.append("	, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");

		return sql.toString();
	}

	/**
	 * IF_指定日_POS特売種別メンテを作成するSQLを取得する
	 *
	 * @return IF_STI_POS_DISCOUNT登録SQL
	 */
	private String getIfStiPosDiscountInsertSql(String uketsukeNo, String tenpoCd, String taisyoDt) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT /*+ APPEND */ INTO IF_STI_POS_DISCOUNT ");
		sql.append("	( ");
		sql.append("	 UKETSUKE_NO ");
		sql.append("	,EIGYO_DT ");
		sql.append("	,TENPO_CD ");
		sql.append("	,TOROKU_ID ");
		sql.append("	,DISCOUNT_CD ");
		sql.append("	,SUB_DISCOUNT_CD ");
		sql.append("	,DISCOUNT_NA ");
		sql.append("	,SUB_DISCOUNT_NA ");
		sql.append("	,RECEIPT_QT ");
		sql.append("	,MAX_RECEIPT_QT ");
		sql.append("	,NEBIKI_RITU_VL ");
		sql.append("	,YUKO_START_DT ");
		sql.append("	,YUKO_END_DT ");
		sql.append("	,MAX_NEBIKI_GAKU_VL ");
		sql.append("	,GYOTAI_KB");
		sql.append("	,CARD_KB");
		// 2017.04.26 T.Han #4824対応（S)
		sql.append("	,SHIHARAI_JOKEN_CD");
		// 2017.04.26 T.Han #4824対応（E)
		sql.append("	) ");
		sql.append("SELECT ");
		sql.append("	 '" + uketsukeNo + "' AS UKETSUKE_NO ");
		sql.append("	,'" + taisyoDt + "' AS TAISYO_DT ");
		sql.append("	,'" + tenpoCd + "' AS TENPO_CD ");
		sql.append("	,'A' AS TOROKU_ID ");
		sql.append("	,RD.DISCOUNT_CD ");
		sql.append("	,RD.SUB_DISCOUNT_CD ");
		sql.append("	,RD.DISCOUNT_NA ");
		sql.append("	,RD.SUB_DISCOUNT_NA ");
		sql.append("	,RD.RECEIPT_QT ");
		sql.append("  ,'" + MAXIMUM_RECEIPT + "' AS MAX_RECEIPT_QT");
		sql.append("	,NVL(RD.NEBIKI_RITU_VL, '  ') AS NEBIKI_RITU_VL ");
		sql.append("	,NVL(RD.YUKO_START_DT, '        ') AS YUKO_START_DT ");
		sql.append("	,NVL(RD.YUKO_END_DT, '        ') AS YUKO_END_DT ");
		sql.append("	,NVL(RD.MAX_NEBIKI_GAKU_VL, 00000000000000.00) AS MAX_NEBIKI_GAKU_VL ");
		sql.append("	,TRT.GYOTAI_KB AS GYOTAI_KB");
		sql.append("	,RD.CARD_KB");
		// 2017.04.26 T.Han #4824対応（S)
		// 2017.05.19 M.Son #5044対応（S)
		//sql.append("	,RD.SHIHARAI_JOKEN_CD");
		sql.append("	,RP.SHIHARAI_SYUBETSU_SEQ ");
		// 2017.05.19 M.Son #5044対応（E)
		// 2017.04.26 T.Han #4824対応（E)
		sql.append(" FROM ");
		sql.append("	R_DISCOUNT RD ");
		// 2017.05.19 M.Son #5044対応（S)
		//sql.append("	, ");
		sql.append(" CROSS JOIN ");
		// 2017.05.19 M.Son #5044対応（E)
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			TRT.TENPO_CD ");
		sql.append("			,TRT.GYOTAI_KB");
		sql.append("		FROM ");
		sql.append("			TMP_R_TENPO TRT ");
		sql.append("		WHERE ");
		// #6406 Del 2021.12.14 KHOI.ND (S)
		// sql.append("			TRT.TENPO_KB 		 = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "'	AND ");
		// #6406 Del 2021.12.14 KHOI.ND (E)
		sql.append("			TRT.TENPO_CD		 = '" + tenpoCd + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (S)
		// sql.append("			TRT.KAITEN_DT		<= '" + taisyoDt + "'	AND ");
        // #6553 DEL 2022.05.05 SIEU.D (E)
		sql.append("			TRT.ZAIMU_END_DT	>= '" + taisyoDt + "'	AND ");
		sql.append("			TRT.DELETE_FG		 = '" + mst000101_ConstDictionary.DELETE_FG_NOR + "' ");
		sql.append("	) TRT ");
		// 2017.05.19 M.Son #5044対応（S)
		sql.append(" LEFT JOIN ");
		sql.append("	R_PAYMENT RP ");
		sql.append(" ON ");
		sql.append("	RD.SHIHARAI_JOKEN_CD = RP.SHIHARAI_SYUBETSU_SUB_CD ");
		// 2017.05.19 M.Son #5044対応（E)

		return sql.toString();
	}

	/**
	 * @param spaces
	 * @return String
	 */
	private String spaces(int spaces){
		return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
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
	
	/**
	 * システムコントロール情報取得
	 * 
	 * @param なし
	 * @throws Exception
	 *             例外
	 */
	private void getSystemControl() throws Exception {
		maxImumReceipt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.MAX_RECEIPT_QT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
					}

    /**
     * <p>タイトル: MSTB992071_PosFileCreateRow クラス</p>
     * <p>説明　: 伝送ヘッダーデータを保持する</p>
     *
     */
    class MSTB994111_IfStiPosCategoryCreateRow {

        /** 受付No */
        private String uketsukeNo;
        /** 店舗コード */
        private String tenpoCd;
        /** 対象日 */
        private String taisyoDt;

        /**
         * MSTB992071_PosFileCreateRow を生成
         *
         * @param uketsukeNo 受付No
         * @param tenpoCd 店舗コード
         * @param taisyoDt 対象日
         */
        public MSTB994111_IfStiPosCategoryCreateRow(String uketsukeNo, String tenpoCd, String taisyoDt) {
            this.uketsukeNo = uketsukeNo;
            this.tenpoCd = tenpoCd;
            this.taisyoDt = taisyoDt;
        }

        /**
         * 受付Noを取得します。
         * @return 受付No
         */
        public String getUketsukeNo() {
            return uketsukeNo;
        }

        /**
         * 店舗コードを取得します。
         * @return 店舗コード
         */
        public String getTenpoCd() {
            return tenpoCd;
        }
        
        /**
         * 対象日を取得します。
         * @return 対象日
         */
        public String getTaisyoDt() {
            return taisyoDt;
        }

    }

}
