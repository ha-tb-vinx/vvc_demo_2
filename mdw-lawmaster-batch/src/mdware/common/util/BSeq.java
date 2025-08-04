package mdware.common.util;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: RB Site連番管理テーブル(seq)より新しい連番を取得する。（バッチ処理用）</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author takeuchi
 * @version 1.0
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.stc.util.db.PreparedStatementDataBase;

/**
 * @author Administrator
 *
 * 2006/05/01以降使用禁止
 */
public class BSeq {

	//採番マスタMax番号の許容される最大値
	private static final int SEQ_MAX_NUMBER = 1999999999;
	
	private PreparedStatementDataBase dataBase = null;
	private PreparedStatement selectStatement = null;
	private PreparedStatement updateStatement = null;
	
	private String seqName = null;

	private BSeq() {	
	}
	
	public BSeq(String seqName) {
		this.seqName = seqName;
	}

	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public int nextVal() {

		
		if (dataBase == null) {
			dataBase = new PreparedStatementDataBase();
		}

		ResultSet resultSet = null;
		int nextVal = 0;	// 新しい連番

		try{
			
			if (selectStatement == null) {
				selectStatement = dataBase.createPreparedStatement("select max_nb +1 from seq  where rtrim(saiban_id) = '" + this.seqName + "' FOR UPDATE"); 
			}
			
			if (updateStatement == null) {
				updateStatement = dataBase.createPreparedStatement("update seq set max_nb = ? where rtrim(saiban_id) = '" + this.seqName + "'");			
			}
			
			// 新しい連番の取得
			resultSet = selectStatement.executeQuery();
			resultSet.next();
			nextVal = resultSet.getInt(1);
			
			// 採番出来た場合
			if(nextVal != 0){

				if ( nextVal <= SEQ_MAX_NUMBER ) {
				} else {
					//nextValが許容される最大値を超えた場合
					nextVal = 1;
				}

				// 新しい連番の更新
				updateStatement.setInt(1, nextVal);
				// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
				if(updateStatement.executeUpdate() == 1) {
					dataBase.commit();
					StcLog.getInstance().getLog().info("連番の更新を終了します。");
				}
				else {
					dataBase.rollback();
					StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
					nextVal = 0; // 返値に0をセット
				}
				
			}
			// 採番出来なかった場合
			else{
				StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + seqName);
			}
		}
		catch(SQLException sqle) {
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			nextVal = 0; // 返値に0をセット
		}
		finally {
			try{resultSet.close();}catch(SQLException sqle){};
			resultSet = null;
			
		}

		return nextVal;
	}

	public void close() {
		try{
			selectStatement.close();
			updateStatement.close();
			dataBase.close();
		}catch(Exception e){
		}
		selectStatement = null;
		updateStatement = null;
		dataBase = null;
	}

	protected void finalize() throws Throwable
	{
		close();
	}

	/**
	 * テスト実行用メイン
	 * @param args
	 */
	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";

		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);

		BSeq ref = new BSeq("denpyonb");
		System.out.println(ref.nextVal());
	}
}
