package mdware.master.batch.common.util;

import java.sql.*;

import mdware.common.batch.log.BatchLog;
import mdware.common.util.db.StatementManager;
import mdware.common.util.StringUtility;
import mdware.common.util.db.MDWareDBUtility;

/**
 * <p>タイトル: MB8X系バッチ 共通処理</p>
 * <p>説明: 発注マスタ作成系バッチの共通処理です。</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 *  @author S.JINNO
 *  @version 1.0 
 */

public class MBBatch_CommonUtility {
	
	//各種宣言
	private StatementManager stMg = null;						//DBアクセス用 オブジェクト(DataBaseクラスを継承）
	private boolean closeDb = false;							//DBステータス管理用
	private BatchLog batchLog = BatchLog.getInstance();		//バッチログ出力用 オブジェクト
	//バッチID & バッチ名
	private static final String BATCH_UID = "MBBatch_CommonUtility";
	private static final String BATCH_NA  = "MBバッチ共通処理";
	
	//接続スキーマ名
	private static final String SCHEMA_NAME = MDWareDBUtility.getSchemaName();
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MBBatch_CommonUtility(StatementManager stMg) {
		this.stMg = stMg;
		if (stMg == null) {
			this.stMg = new StatementManager("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MBBatch_CommonUtility() {
		this(new StatementManager("rbsite_ora"));
		closeDb = true;
	}

	
	/**
	 *  主キー削除SQL
	 *  @param  IN : String tableName
	 *  @param  IN : String indexName
	 *  @return OUT: String Sql
	 *  @exception Exception
	 *  主キー名をINPUTとして、DROPするSQLを返却する。
　	 */
	public String dropPrimarySql(String tableName, String primaryName) throws Exception
	{	
		try {
			//主キー制約削除 SQL組み立て
			StringBuffer sb = new StringBuffer("");
			sb = new StringBuffer("ALTER TABLE " + tableName + " DROP CONSTRAINT " + primaryName + " ");
			return sb.toString();
		} catch (Exception e) {
			//エラー時は、上位階層に投げる。
			throw e;
		}
	}
	
	
	/**
	 *  索引削除SQL
	 *  @param  IN : String tableName
	 *  @param  IN : String indexName
	 *  @return OUT: String Sql
	 *  @exception Exception
	 *  索引名をINPUTとして、DROPするSQLを返却する。
	 *  実ロジックをJAVA実装したバージョン(フジ案件にて使用）
　	*/	
	public String dropIndexSql(String indexName) throws Exception
	{	
		
		try {
			
			//主キー制約削除 SQL組み立て
			StringBuffer sb = new StringBuffer("");
			sb = new StringBuffer("DROP INDEX " + indexName + " ");
			return sb.toString();
			
		} catch (Exception e) {
			//エラー時は、上位階層に投げる。
			throw e;
		}
		
	}
	
	/**
	 *  テーブルDROPSQL
	 *  @param  IN : String tableName
	 *  @param  IN : String indexName
	 *  @return OUT: String Sql
	 *  @exception Exception
	 *  テーブル名をINPUTとして、DROPするSQLを返却する。
　	*/	
	public String dropTableSql(String tableName) throws Exception
	{	
		
		try {
			
			//主キー制約削除 SQL組み立て
			StringBuffer sb = new StringBuffer("");
			sb = new StringBuffer("DROP TABLE " + tableName + " ");
			return sb.toString();
			
		} catch (Exception e) {
			//エラー時は、上位階層に投げる。
			throw e;
		}
		
	}
	
	/**
	 *  テーブル全削除処理
	 *  @param  IN : String tableName
	 *  @exception Exception
	 *  テーブル名をINPUTとして、truncate処理を行う。
	 *  DB2では、TRUNCATE文が存在しない為、同様の処理を行う為のメソッドを作成。
	 *  ORACLEでの使用は出来ない。
　	*/	
	public void truncateTable(String tableName) throws Exception
	{	

		ResultSet rs = null;			//結果セット宣言
		String shortTableName = null;	//テーブル短縮名
		String clCommand = null;		//SQLプロシージャの第１引数（CLコマンド文字列 格納用）
		String clCommandFormat = null;	//SQLプロシージャの第２引数（CLコマンド文字列形式 格納用）
		String sql = null;				//SQLプロシージャ呼び出し文 格納用
		
		try {
			
			
			//テーブル短縮名取得
			StringBuffer sb = new StringBuffer("");
			sb.append("	SELECT DISTINCT  ");
			sb.append("		SYSTEM_TABLE_NAME ");
			sb.append("	FROM ");
			sb.append("		QSYS2.SYSCOLUMNS			t1");
			sb.append("	WHERE ");
			sb.append("		t1.TABLE_NAME = '" + tableName + "'");
			sb.append("	AND");
			sb.append("		t1.SYSTEM_TABLE_SCHEMA = '" + this.SCHEMA_NAME + "'");
			sb.append("	");
			
			//結果セット取得
			rs = stMg.executeQuery(sb.toString());
			
			//結果セットを変数に保存の後、閉じる。
			if(rs.next()){
				shortTableName = rs.getString("SYSTEM_TABLE_NAME");
			}
			rs.close();
			
			//テーブル名と、CLコマンドをCLコマンド文字列にセット
			clCommand = "CLRPFM " + shortTableName;
			//CLコマンド文字列形式をセッティング
			clCommandFormat = StringUtility.getFormatedString(clCommand.length(), "0000000000.00000");
			
			// CLを実行するSQLプロシージャ実行 (DB2における、TRUNCATEと同様の動きをするCL)
			sql = "CALL QSYS.QCMDEXC ('" + clCommand + "', " + clCommandFormat + ")";
			stMg.executeUpdate(sql);
			stMg.commit();
			
			
		} catch (Exception e) {
			//エラー時は、ROLLBACKして上位階層に投げる。
			batchLog.getLog().error(BATCH_UID, BATCH_NA, tableName + "の削除に失敗しました。");	
			stMg.rollback();
			throw e;
			
		} finally {
			//結果セット開放
			if (rs != null) {
				rs.close();
			}
		}
		
	}

	/**
	 *  テーブル全削除処理
	 *  @param  IN : String tableName
	 *  		 IN : String schemaName
	 *  @exception Exception
	 *  テーブル名とスキーマ名（ライブラリ名）をINPUTとして、truncate処理を行う。
	 *  接続ユーザーのデフォルトスキーマ以外のスキーマにあるデータをtruncateしたい場合に使用する。
	 *  主にIF データ集信後の削除処理での使用を想定。
　	*/	
	public void truncateTable(String tableName, String schemaName) throws Exception
	{	

		ResultSet rs = null;			//結果セット宣言
		String shortTableName = null;	//テーブル短縮名
		String clCommand = null;		//SQLプロシージャの第１引数（CLコマンド文字列 格納用）
		String clCommandFormat = null;	//SQLプロシージャの第２引数（CLコマンド文字列形式 格納用）
		String sql = null;				//SQLプロシージャ呼び出し文 格納用
		
		try {
			
			
			//テーブル短縮名取得
			StringBuffer sb = new StringBuffer("");
			sb.append("	SELECT DISTINCT  ");
			sb.append("		SYSTEM_TABLE_NAME ");
			sb.append("	FROM ");
			sb.append("		QSYS2.SYSCOLUMNS			t1");
			sb.append("	WHERE ");
			sb.append("		t1.TABLE_NAME = '" + tableName + "'");
			sb.append("	AND");
			sb.append("		t1.SYSTEM_TABLE_SCHEMA = '" + schemaName + "'");
			sb.append("	");
			
			//結果セット取得
			rs = stMg.executeQuery(sb.toString());
			
			//結果セットを変数に保存の後、閉じる。
			if(rs.next()){
				shortTableName = rs.getString("SYSTEM_TABLE_NAME");
			}
			rs.close();
			
			//テーブル名と、CLコマンドをCLコマンド文字列にセット
			clCommand = "CLRPFM " + schemaName + "/" + shortTableName;
			//CLコマンド文字列形式をセッティング
			clCommandFormat = StringUtility.getFormatedString(clCommand.length(), "0000000000.00000");
			
			// CLを実行するSQLプロシージャ実行 (DB2における、TRUNCATEと同様の動きをするCL)
			sql = "CALL QSYS.QCMDEXC ('" + clCommand + "', " + clCommandFormat + ")";
			stMg.executeUpdate(sql);
			stMg.commit();
			
			
		} catch (Exception e) {
			//エラー時は、ROLLBACKして上位階層に投げる。
			batchLog.getLog().error(BATCH_UID, BATCH_NA, tableName + "の削除に失敗しました。");	
			stMg.rollback();
			throw e;
			
		} finally {
			//結果セット開放
			if (rs != null) {
				rs.close();
			}
		}
		
	}
	
	/**
	 *  DBアクセスクラス　クローズ
	 *  @exception Exception
	 *  内部で使用しているStatementManagerを閉じるメソッド
	 *  呼び出し元で必ず使用すること。
　	*/	
	public void close() throws Exception
	{	
		
		try {
			
			if (closeDb || stMg != null) {
				stMg.close();
			}
			
		} catch (Exception e) {
			//エラー時は、上位階層に投げる。
			throw e;
		}
		
	}
	/**
	 *  MDWARE側インターフェーススキーマ名の取得
	 *  @return OUT: String SchemeName
　	*/	
	public String getMdInterfaceSchemeName()
	{	
		String strSchemeName = null;
		if ( SCHEMA_NAME.toUpperCase().trim().equals("MDWDBLIB") ) {
			strSchemeName = "MDWTRLIB";
		} else if ( SCHEMA_NAME.toUpperCase().trim().equals("MDWDBLIBDS") ) {
			strSchemeName = "MDWTRLIBDS";
		} else {
			strSchemeName = SCHEMA_NAME.toUpperCase().trim();
		}
		return strSchemeName;
	}
	
	/**
	 *  M2側インターフェーススキーマ名の取得
	 *  @return OUT: String SchemeName
　	*/	
	public String getM2InterfaceSchemeName()
	{	
		String strSchemeName = null;
		if ( SCHEMA_NAME.toUpperCase().trim().equals("MDWDBLIB") ) {
			strSchemeName = "PSGTRLIB";
		} else if ( SCHEMA_NAME.toUpperCase().trim().equals("MDWDBLIBDS") ) {
			strSchemeName = "PSGTRLIBDS";
		}
		return strSchemeName;
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
		MBBatch_CommonUtility batch = new MBBatch_CommonUtility();
		try {
			batch.truncateTable("TMP_M17_SYOKAI_SYOHIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
