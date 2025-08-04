package mdware.common.batch.util.analyze;

import jp.co.vinculumjapan.stc.util.db.DataBase;

/**
 * <p>タイトル: データベースの分析などを行うクラス</p>
 * <p>説明: DBMS_STATSを使用し、分析を行うPL/SQLを呼び出すクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * <p>(使用するPL/SQL及び、オブジェクト情報の初期セットアップが必要。)<p>
 * <p>PL/SQL: TUNING_KIT<p>
 * <p>TABLE: SYSTEM_DEFINED_INDEX,SYSTEM_DEFINED_INDEX_COLUMNS<p>
 * <p>DATA: SELECT UI.*, NVL(AC.CONSTRAINT_TYPE,'I') AS KEY_TYPE FROM USER_INDEXES UI LEFT OUTER JOIN ALL_CONSTRAINTS AC ON UI.TABLE_NAME = AC.TABLE_NAME AND UI.INDEX_NAME = AC.CONSTRAINT_NAME AND AC.OWNER = USER AND AC.CONSTRAINT_TYPE ='P'<p>
 * <p>DATA: SELECT AIS.* FROM ALL_IND_COLUMNS AIS INNER JOIN SYSTEM_DEFINED_INDEX SDI ON AIS.INDEX_NAME = SDI.INDEX_NAME WHERE INDEX_OWNER = USER ORDER BY 2,COLUMN_POSITION<p>
 * @author tokuda
 * @version 1.01 2005/09/20 T.Tokuda 新規作成
 * @version 1.05 2005/10/03 T.Tokuda 見積パーセンテージ追加
 * @version 緊急対応№106 2005/10/04 H.Okuno 見積パーセンテージがnullの場合NullPointerExceptionが発生していた不具合への対応
 * @version 1.08 2005/10/09 T.Tokuda 変更依頼書No.218
 */
public class DbAnalyze {

	/**
	 * データベースオブジェクト
	 */
	private DataBase dataBase;

	/**
	 * 見積のパーセンテージ
	 */
	private String estimatePercent;

	/**
	 * コンストラクタ
	 * @param dataBase データベースオブジェクト
	 */
	public DbAnalyze(DataBase dataBase) {
		super();
		this.dataBase = dataBase;
		this.estimatePercent = "10";
	}

	/**
	 * 分析対象のインデックスデータを登録
	 * 初期・再セットアップに使用
	 */
	public void insertIndexTable() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.INSERT_INDEX_TABLE()");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * 分析対象のインデックスデータを登録
	 * 初期・再セットアップに使用
	 */
	public void insertIndexTable(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.INSERT_INDEX_TABLE('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * 分析対象のインデックスデータを削除
	 * 初期・再セットアップに使用
	 */
	public void deleteIndexTable() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.DELETE_INDEX_TABLE()");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * 分析対象のインデックスデータを削除
	 * 初期・再セットアップに使用
	 */
	public void deleteIndexTable(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.DELETE_INDEX_TABLE('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * インデックスの無効化
	 * @param objectName 対象のインデックス名
	 */
	public void disableIndexes(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.DISABLE_INDEXES('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * インデックスの有効化
	 * @param objectName 対象のインデックス名
	 */
	public void enableIndexes(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.ENABLE_INDEXES('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * テーブルを分析
	 * @param objectName 対象のテーブル名
	 */
	public void analyzeTable(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.ANALYZE_FOR_STATS_TABLE('" + objectName + "',"+ estimatePercent +")");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * テーブルに属するインデックスを分析
	 * @param objectName 対象のテーブル名
	 */
	public void analyzeIndexes(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.ANALYZE_FOR_STATS_INDEXES('" + objectName + "',"+ estimatePercent +")");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * テーブルの分析情報を削除
	 * @param objectName 対象のテーブル名
	 */
	public void deleteAnalyzeTable(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.DEL_ANALYZE_FOR_STATS_TABLE('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * テーブルに属するインデックスの分析情報を削除
	 * @param objectName 対象のテーブル名
	 */
	public void deleteAnalyzeIndexes(String objectName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("CALL TUNING_KIT.DEL_ANALYZE_FOR_STATS_INDEXES('" + objectName + "')");
		this.dataBase.execute(sb.toString());		
	}

	/**
	 * 見積パーセンテージをセット
	 * @param integer
	 * @exception nullもしくは、0 ～ 100以外をエラーとする
	 */
	public void setEstimatePercent(Integer integer) throws Exception {
//BEGIN	2005.10.04	H.Okuno	緊急対応№106
//		if (integer != null) {
		if (integer == null) {
//END	2005.10.04	H.Okuno
			estimatePercent = "null";
		} else if  (integer.intValue() >= 0 && integer.intValue() <= 100) { 
			estimatePercent = integer.toString();
		} else {
			throw new Exception("値が不正です。");
		}
	}

}
