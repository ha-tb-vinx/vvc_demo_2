/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス用データベース接続クラス（シリウス担当用）</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用データベース接続クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;
//↓↓2007.01.31 H.Yamamoto カスタマイズ修正↓↓
import java.sql.PreparedStatement;
//↑↑2007.01.31 H.Yamamoto カスタマイズ修正↑↑
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス用データベース接続クラス（シリウス担当用）</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用データベース接続クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし
 */
public class mst000101_DbmsBean extends DataBase {

	/**
	 * ＤＢ接続トータル回数の確認用変数
	 */
	private static int iUseCntTotal = 0;
	/**
	 * ＤＢ接続回数の確認用変数
	 */
	private int iUseCnt = 0;

	/**
	 * インスタンス生成関数
	*/
	public static mst000101_DbmsBean getInstance()
	{
		return new mst000101_DbmsBean();
	}

	/**
	 *	コンストラクタ
	 */
	public mst000101_DbmsBean()	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
	}

	/**
	 * クローズ関数
	 */
	public void close() {
		super.close();
	}

	/**
	 * コミット関数
	 */
	public void commit() {
		super.commit();
	}

	/**
	 * ローグバック関数
	 */
	public void rollback() {
		super.rollback();
	}

	/**
	 * SQL実行関数（Insert用）
	 */
	public void execute(String strSQL) throws SQLException {
		super.execute(strSQL);
	}

	/**
	 * SQL実行関数（Select用）
	 */
	public ResultSet executeQuery(String strSQL) throws SQLException {
		return super.executeQuery(strSQL);
	}

	/**
	 * SQL実行関数（Update用）
	 */
	public int executeUpdate(String strSQL) throws SQLException {
		return super.executeUpdate(strSQL);
	}

	/**
	 * ファイナライズ関数
	 */
	protected void finalize() throws Throwable {
		// コネクションがない場合、
		if ( null == this.conn) {
			return ;
		}
		// コネクションが切断されていない場合、クローズする
		if( false == conn.isClosed() ) {
			this.close();
		}
	}

	/**
	 * ＤＢの接続状態がクローズであるかどうかを返す
	 * @return Connection がクローズされている場合は true、まだオープンの状態の場合は false
	 * @throws SQLException
	 */
	public boolean isClosed() throws SQLException {
		// コネクションがない場合、
		if ( null == this.conn) {
			// 切断中とする
			return true;
		}
		// クローズ状態を返す
		// （Connection がクローズされている場合は true、まだオープンの状態の場合は false）
		return conn.isClosed();
	}

	/**
	 *	更新(Update Delete用)
	 *  引数で渡された更新年月日とテーブルの更新年月日を比較しbooleanで結果を戻す
	 *	@param String updateSql	//Update Delete用Sql
	 *	@param String chkSql		//更新年月日取得用Sql
	 *	@param String chkDT		//保存していた更新年月日
	 *  @return boolean true:更新日に差異がない（更新もおこなう）
	 *                   false:更新日に差異がある（更新はおこなわない）
	 *	@exception java.sql.SQLException
	 */
	public boolean executeUpdate( String updateSql, String chkSql, String chkDT )
		throws SQLException
	{
		ResultSet rset = this.executeQuery(chkSql);
		if (rset.next() == false) {		//if(rset.next()) は勝手に素通りするので change by kema 06.09.11
			return false;
		}

		if(chkDT != null && !chkDT.equals("")){
			if ((String)rset.getString("update_ts") == null) {
				return false;
			}
		}

		String wkChkDt = (String)rset.getString("update_ts");	//更新年月日取得用

		if(chkDT == null || chkDT.equals("")){
			if (wkChkDt != null && !wkChkDt.equals("")) {
				return false;
			}
		} else {
			if (!wkChkDt.trim().equals(chkDT)) {
				return false;
			}
		}
		rset.close();	//closeをしないと rset.next() を勝手に素通りするので change by kema 06.09.13

		this.executeUpdate(updateSql);

		return true;
	}

	/**
	 *	追加(Insert用)
	 *  引数で渡された条件で更新する。成功/失敗を返す
	 *	@param 	String	updateSql	//Insert用Sql
	 *	@param 	String	chkSql		//更新年月日取得用Sql
	 *	@param 	String	chkDT		//保存していた更新年月日
	 *  @return 	boolean	true  :成功（更新条件に満たない場合も成功で返す。エラーフラグで判断）
	 *                   	false :失敗（Bean,Keepへの値の取得設定に失敗した場合、失敗を返す。）
	 *	@exception java.sql.SQLException
	 */
	public boolean executeInsert( DataModule DM, Object Bean,Object Keep, String UserId, String TableNa, List whereList,boolean flg )
		throws SQLException, Exception
	{

		mst000601_KoushinInfoDM Info = new mst000601_KoushinInfoDM();	//レコード更新状態の照会
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); 		//STCLIBのDBインスタンス格納用
		ResultSet rset = null;											//レコード更新状態戻り値
		String[] ret = new String[2];									//本関数戻り値一時保存

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());		// メッセージ取得

		//削除フラグ=0のデータが存在する場合、エラー
		if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
				return false;
			}
			return false;
		}
		whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));
		whereList.remove(whereList.size()-1);
		if(rset.next()){
			//削除フラグ=0 レコード存在
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("40001").toString()) == null){
				return false;
			}
			return false;
		} else {
			//削除フラグ=0 レコード非存在
			//削除フラグ=1のデータが存在する場合、Update
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.IRU.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

//			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList) + "for update with rs");
			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));
			whereList.remove(whereList.size()-1);
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			if(rset.next()){
				//削除フラグ=1 レコード存在
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,this.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}

				if (flg){

					if(mst001301_UpdateBean.setValue( "setUpdateUserId",Bean,UserId ) == null){
						if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
							return false;
						}
						if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
							return false;
						}
						return false;
					}
					if(mst001301_UpdateBean.setValue( "setUpdateTs",Bean,this.getDBSysdate() ) == null){
						if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
							return false;
						}
						if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
							return false;
						}
						return false;
					}
				}
			    this.executeUpdate(DM.getDeleteSql(Bean));
			    this.execute(DM.getInsertSql(Bean));
			} else {
				//レコード非存在
				//レコードが存在しない場合、Insert
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,this.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}

				if (flg){

					if(mst001301_UpdateBean.setValue( "setUpdateUserId",Bean,UserId ) == null){
						if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
							return false;
						}
						if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
							return false;
						}
						return false;
					}
					if(mst001301_UpdateBean.setValue( "setUpdateTs",Bean,this.getDBSysdate() ) == null){
						if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
							return false;
						}
						if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
							return false;
						}
						return false;
					}
				}
				this.execute(DM.getInsertSql(Bean));
			}
			//更新情報再取得
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));
			whereList.remove(whereList.size()-1);
			if(rset.next()){
				if(mst001301_UpdateBean.setValue("setInsertTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setInsertUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_user_id")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_user_id")).trim()) == null){
					return false;
				}
			}
		}


		if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_NORMAL) == null){
			return false;
		}
		if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0031").toString()) == null){
			return false;
		}
		rset.close();	//closeをしないと rset.next() を勝手に素通りするので change by kema 06.09.13

		return true;

	}
//	   ↑↑2006.07.24 jianglm カスタマイズ修正↑↑

	/**
	 *	追加(Insert用)
	 *  引数で渡された条件で更新する。成功/失敗を返す
	 *	@param 	String	updateSql	//Insert用Sql
	 *	@param 	String	chkSql		//更新年月日取得用Sql
	 *	@param 	String	chkDT		//保存していた更新年月日
	 *  @return 	boolean	true  :成功（更新条件に満たない場合も成功で返す。エラーフラグで判断）
	 *                   	false :失敗（Bean,Keepへの値の取得設定に失敗した場合、失敗を返す。）
	 *	@exception java.sql.SQLException
	 */
	public boolean executeInsert( DataModule DM, Object Bean,Object Keep, String UserId, String TableNa, List whereList )
		throws SQLException, Exception
	{

		mst000601_KoushinInfoDM Info = new mst000601_KoushinInfoDM();	//レコード更新状態の照会
//		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); 		//STCLIBのDBインスタンス格納用
		ResultSet rset = null;											//レコード更新状態戻り値
		String[] ret = new String[2];									//本関数戻り値一時保存

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());		// メッセージ取得
		//削除フラグ=0のデータが存在する場合、エラー
		if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
				return false;
			}
			return false;
		}
		whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));
		whereList.remove(whereList.size()-1);
		if(rset.next() == true){
			//削除フラグ=0 レコード存在
			if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
				return false;
			}
			if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("40001").toString()) == null){
				return false;
			}
			return false;
		} else {
			//削除フラグ=0 レコード非存在
			//削除フラグ=1のデータが存在する場合、Update
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.IRU.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

//			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList) + " for update with rs");
			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));

			whereList.remove(whereList.size()-1);
			if(mst001301_UpdateBean.setValue( "setDeleteFg",Bean,mst000801_DelFlagDictionary.INAI.getCode() ) == null){
				if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
					return false;
				}
				return false;
			}
			if(rset.next()){
				//削除フラグ=1 レコード存在
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,this.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
			    this.executeUpdate(DM.getDeleteSql(Bean));
			    this.execute(DM.getInsertSql(Bean));
			} else {
				//レコード非存在
				//レコードが存在しない場合、Insert
				if(mst001301_UpdateBean.setValue( "setInsertUserId",Bean,UserId ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				if(mst001301_UpdateBean.setValue( "setInsertTs",Bean,this.getDBSysdate() ) == null){
					if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_ERROR) == null){
						return false;
					}
					if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0009").toString()) == null){
						return false;
					}
					return false;
				}
				this.execute(DM.getInsertSql(Bean));
			}
			//更新情報再取得
			whereList.add(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			rset = this.executeQuery(Info.getStatusSql(TableNa,whereList));
			whereList.remove(whereList.size()-1);
			if(rset.next()){
				if(mst001301_UpdateBean.setValue("setInsertTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateTs", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_ts")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setInsertUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("insert_user_id")).trim()) == null){
					return false;
				}
				if(mst001301_UpdateBean.setValue("setUpdateUserId", Keep, mst000401_LogicBean.chkNullString(rset.getString("update_user_id")).trim()) == null){
					return false;
				}
			}
		}


		if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_NORMAL) == null){
			return false;
		}
		if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,map.get("0031").toString()) == null){
			return false;
		}

		rset.close();	//closeをしないと rset.next() を勝手に素通りするので change by kema 06.09.13
		return true;

	}
	/**
	 *	DBSysDate
	 *  DBのSYSDATEを返す
	 *  @return 	String	:DBのSysDate
	 *	@exception java.sql.SQLException
	 */
	public String getDBSysdate()
		throws SQLException
	{
		//フェーズ1によるＤＢアクセス見直しにより修正 start 20061115 nakazawa
		//いちいちＤＢのシステム日付を取得していたのを共通関数(ＤＢアクセスなし)化した

		//ResultSet rset = null;					//戻り値
		//String systemDate = "";					//関数戻り値
		//StringBuffer sb = new StringBuffer();
		//try{
		//	sb.append(" SELECT ");
		//  ↓↓　移植(2006.05.25) ↓↓
		//	sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' " +" AS SYSTEM_DATE ");
		//  ↑↑　移植(2006.05.25) ↑↑
		//	sb.append(" FROM ");
		//	sb.append("  DUAL ");
		//	rset = this.executeQuery(sb.toString());
		//	if(rset.next()){
		//		systemDate = rset.getString("system_date").trim();
		//	}
		//} catch(SQLException sqle) {
		//	throw sqle;
		//} finally {
		//}
		//rset.close();	//closeをしないと rset.next() を勝手に素通りするので change by kema 06.09.13
		//return systemDate;

		return AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");

		//20061115 end
	}

	/**
	 *	PreparedStatement処理
	 *	@param sql String PreparedStatement作成を行うＳＱＬ
	 *	@return PreparedStatement :作成したPreparedStatement
	 *	@exception java.sql.SQLException
	 */
	public PreparedStatement getPrepareStatement(String sql) throws SQLException	{
		// コネクションがない場合、
		if ( null == this.conn) {
			super.connect( mst000101_ConstDictionary.CONNECTION_STR );
		}

		return this.conn.prepareStatement( sql );
	}
	/**
	 *	更新(Update Delete用)
	 *  引数で渡された更新年月日とテーブルの更新年月日を比較しbooleanで結果を戻す
	 *	@param PreparedStatement updPsmt	//Update Delete用PreparedStatement
	 *	@param PreparedStatement chkPsmt	//更新年月日取得用PreparedStatement
	 *	@param String chkDT				//保存していた更新年月日
	 *  @return boolean true:更新日に差異がない（更新もおこなう）
	 *                   false:更新日に差異がある（更新はおこなわない）
	 *	@exception java.sql.SQLException
	 */
	public boolean executeUpdate( PreparedStatement updPsmt, PreparedStatement chkPsmt, String chkDT )
		throws SQLException
	{
		ResultSet rset = chkPsmt.executeQuery();
		if (!rset.next()) {
			rset.close();
			return false;
		}
		String wkChkDt = (String)rset.getString("update_ts");	//更新年月日取得用
		rset.close();

		if(chkDT != null && !chkDT.equals("")){
			if (wkChkDt == null || wkChkDt.equals("")) {
				return false;
			} else if (!wkChkDt.trim().equals(chkDT)) {
				return false;
			}
		} else {
			if (wkChkDt != null && !wkChkDt.equals("")) {
				return false;
			}
		}

		updPsmt.executeUpdate();

		return true;
	}

	/**
	 *	挿入(Insert用)
	 *  データ有無をチェックしbooleanで結果を戻す
	 *	@param PreparedStatement insPsmt	//Insert用PreparedStatement
	 *	@param PreparedStatement chkPsmt	//削除フラグ取得用PreparedStatement
	 *	@param PreparedStatement delPsmt	//Delete用PreparedStatement
	 *  @return boolean true:データがない、又は削除済データがある（更新もおこなう）
	 *                   false:未削除のデータがある（更新はおこなわない）
	 *	@exception java.sql.SQLException
	 */
	public boolean executeInsert( PreparedStatement insPsmt, PreparedStatement chkPsmt, PreparedStatement delPsmt )
		throws SQLException
	{
		ResultSet rset = chkPsmt.executeQuery();
		if (rset.next()) {
			String wkChkFg = (String)rset.getString("delete_fg");	//削除フラグ取得用
			if(wkChkFg != null && wkChkFg.equals(mst000801_DelFlagDictionary.IRU.getCode())){
				delPsmt.executeUpdate();
			} else {
				rset.close();
				return false;
			}
		}
		rset.close();

		insPsmt.executeUpdate();

		return true;
	}
}