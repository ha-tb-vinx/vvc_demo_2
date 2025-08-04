package mdware.master.common.bean;

/**
 * <P>タイトル : 本部初回発注用ＤＢアクセスクラス</P>
 * <P>説明 : mst000101_DbmsBeanを継承して作成</P>
 *  <P>著作権: Copyright (c) 2006</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author K.NAKAZAWA
 * @version 1.0
 * @see なし								
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

public class mst270001_DbmsBean extends mst000101_DbmsBean {
	
	private static final InfoStrings infoStrings = InfoStrings.getInstance();
	
	private PreparedStatement ps = null;

	/**
	 *	コンストラクタ
	 */
	public mst270001_DbmsBean()	{
		super();
	}

	/**
	 * クローズ関数
	 */
	public void close() {
		
		if( ps != null ) {
			try{
				ps.close();
			}catch(SQLException e){
				StcLog.getInstance().getLog().error("PreparedStatementのcloseに失敗しました" + e.toString());
			}finally{
				ps = null;
			}
		}
		
		super.close();
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
	public boolean executeUpdate( Object Bean, String chkSql, String chkDT, int mode )
		throws SQLException
	{

		ResultSet rset = this.executeQuery(chkSql);

		if ( !rset.next() ) {
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
		rset.close();
		
		if( ps == null ) {
			if( mode == 0 ) ps = getPreparedStatement( getUpdateSQLStr() );
			else ps = getPreparedStatement( getDeleteSQLStr() );
		} 

		String now = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		mst270101_SyokaiDonyuJyukyoBean sdjBean = (mst270101_SyokaiDonyuJyukyoBean)Bean;
		
		if( mode == 0 ) {
			ps.setString(  1, nullToString( sdjBean.getSuryoQt(), true ) );
			ps.setString(  2, nullToString( sdjBean.getGentankaVl(), true ) );
			ps.setString(  3, nullToString( sdjBean.getBaitankaVl(), true ) );
			ps.setString(  4, nullToString( sdjBean.getHachutaniQt(), true ) );
			ps.setString(  5, nullToString( sdjBean.getHatyuDt(), false ) );
			ps.setString(  6, nullToString( sdjBean.getNohinDt(), false ) );
			ps.setString(  7, nullToString( sdjBean.getHanbaiStDt(), false ) );
			ps.setString(  8, nullToString( sdjBean.getHanbaiEdDt(), false ) );
			ps.setString(  9, nullToString( now, false ) );
			ps.setString( 10, nullToString( sdjBean.getUpdateUserId(), false ) );
			ps.setString( 11, "0" );//←元のソースmst270101_SyokaiDonyuJyukyoDMより
			ps.setString( 12, nullToString( sdjBean.getHachunoCd(), false ) );
			ps.setString( 13, nullToString( sdjBean.getSyohinCd(), false ) );
			ps.setString( 14, nullToString( sdjBean.getBumonCd(), false ) );
			ps.setString( 15, nullToString( sdjBean.getTenpoCd(), false ) );
		}else{
			ps.setString( 1, nullToString( now, false ) );
			ps.setString( 2, nullToString( sdjBean.getUpdateUserId(), false ) );
			ps.setString( 3, "1" );//←元のソースmst270101_SyokaiDonyuJyukyoDMより
			ps.setString( 4, nullToString( sdjBean.getHachunoCd(), false ) );
			ps.setString( 5, nullToString( sdjBean.getSyohinCd(), false ) );
			ps.setString( 6, nullToString( sdjBean.getBumonCd(), false ) );
			ps.setString( 7, nullToString( sdjBean.getTenpoCd(), false ) );
		}
		
		ps.executeUpdate();
		
		return true;
	}

	/**
	 * 新規追加(Insert)
	 * 引数で渡された条件でデータの新規追加を行う。
	 * 
	 * @return boolean
	 */
	public boolean executeInsert( DataModule DM, Object Bean,Object Keep, String UserId, String TableNa, List whereList )
		throws SQLException, Exception
	{
		if( ps == null ) {
			ps = getPreparedStatement( getInsertSQLStr() );
		} 
		
		mst270101_SyokaiDonyuJyukyoBean sdjBean = (mst270101_SyokaiDonyuJyukyoBean)Bean;
		
		int suryo = 0;
		try{
			suryo = Integer.parseInt( sdjBean.getSuryoQt() );
		}catch(Exception e){
			suryo = 0;
			
		}
		
		String now = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		
		ps.setString(  1, nullToString( sdjBean.getBumonCd(), false ) );
		ps.setString(  2, nullToString( sdjBean.getHachunoCd(), false ) );
		ps.setString(  3, nullToString( sdjBean.getSyohinCd(), false ) );
		ps.setString(  4, nullToString( sdjBean.getTenpoCd(), false ) );
		ps.setString(  5, nullToString( sdjBean.getSuryoQt(), true ) );
		ps.setString(  6, nullToString( sdjBean.getHachutaniQt(), true ) );
		ps.setString(  7, nullToString( sdjBean.getGentankaVl(), true ) );
		ps.setString(  8, nullToString( sdjBean.getBaitankaVl(), true ) );
		ps.setString(  9, nullToString( sdjBean.getHatyuDt(), false ) );
		ps.setString( 10, nullToString( sdjBean.getNohinDt(), false ) );
		ps.setString( 11, nullToString( sdjBean.getHanbaiStDt(), false ) );
		ps.setString( 12, nullToString( sdjBean.getHanbaiEdDt(), false ) );
		ps.setString( 13, nullToString( now, false ) );
		ps.setString( 14, nullToString( sdjBean.getInsertUserId(), false ) );
		ps.setString( 15, nullToString( now, false ) );
		ps.setString( 16, nullToString( sdjBean.getUpdateUserId(), false ) );
		ps.setString( 17, "0" );//←元のソースmst270101_SyokaiDonyuJyukyoDMより
		ps.setString( 18, "1");//←元のソースmst270101_SyokaiDonyuJyukyoDMより

		ps.executeUpdate();
		
		//更新情報再取得
		
		mst000601_KoushinInfoDM Info = new mst000601_KoushinInfoDM();	//レコード更新状態の照会
		ResultSet rset = null;											//レコード更新状態戻り値
			
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

		if(mst001301_UpdateBean.setValue("setErrorFlg",Keep,mst000101_ConstDictionary.ERR_CHK_NORMAL) == null){
			return false;
		}
		if(mst001301_UpdateBean.setValue("setErrorMessage",Keep,infoStrings.getInfo("0031").toString()) == null){
			return false;
		}

		rset.close();
		
		return true;
	}
	
	/**
	 * PreparedStatementを返す
	 * @return PreparedStatement
	 */
	public PreparedStatement getPreparedStatement( String sql ) {
		
		try{
			return conn.prepareStatement( sql );
		} catch( SQLException e ) {
			StcLog.getInstance().getLog().error("PreparedStatementの取得に失敗しました");
		}
		
		return null; 
	}
	
	/**
	 * INSERT用のSQL文を返す(PreparedStatement対応)
	 * 
	 * @return String
	 */
	private String getInsertSQLStr() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO R_SYOKAIDONYU ( ");
		sb.append(" 	BUMON_CD, ");
		sb.append(" 	HACHUNO_CD, ");
		sb.append(" 	SYOHIN_CD, ");
		sb.append(" 	TENPO_CD, ");
		sb.append(" 	SURYO_QT, ");
		sb.append(" 	HACHUTANI_QT, ");
		sb.append(" 	GENTANKA_VL, ");
		sb.append(" 	BAITANKA_VL, ");
		sb.append(" 	HATYU_DT, ");
		sb.append(" 	NOHIN_DT, ");
		sb.append(" 	HANBAI_ST_DT, ");
		sb.append(" 	HANBAI_ED_DT, ");
		sb.append(" 	INSERT_TS, ");
		sb.append(" 	INSERT_USER_ID, ");
		sb.append(" 	UPDATE_TS, ");
		sb.append(" 	UPDATE_USER_ID, ");
		sb.append(" 	DELETE_FG, ");
		sb.append(" 	TOROKU_MOTO_KB ");
		sb.append(" ) VALUES ( ");
		sb.append(" 	?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ");
		sb.append(" ) ");
		
		return sb.toString();
	}
	
	/**
	 * UPDATE用のSQL文を返す(PreparedStatement対応)
	 * 
	 * @return String
	 */
	private String getUpdateSQLStr() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" UPDATE ");
		sb.append(" 	R_SYOKAIDONYU ");
		sb.append(" SET ");
		sb.append(" 	SURYO_QT = ?, ");
		sb.append(" 	GENTANKA_VL = ?, ");
		sb.append(" 	BAITANKA_VL = ?, ");
		sb.append(" 	HACHUTANI_QT = ?, ");
		sb.append(" 	HATYU_DT = ?, ");
		sb.append(" 	NOHIN_DT = ?, ");
		sb.append(" 	HANBAI_ST_DT = ?, ");
		sb.append(" 	HANBAI_ED_DT = ?, ");
		sb.append(" 	UPDATE_TS = ?, ");
		sb.append(" 	UPDATE_USER_ID = ?, ");
		sb.append(" 	DELETE_FG = ? ");
		sb.append(" WHERE ");
		sb.append(" 	HACHUNO_CD =  ? AND ");
		sb.append(" 	SYOHIN_CD = ? AND ");
		sb.append(" 	BUMON_CD = ? AND ");
		sb.append(" 	TENPO_CD = ? ");
		
		return sb.toString();
	}
	
	/**
	 * DELETE用のSQL文を返す(PreparedStatement対応)
	 * 
	 * @return String
	 */
	private String getDeleteSQLStr() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" UPDATE ");
		sb.append(" 	r_syokaidonyu ");
		sb.append(" SET ");
		sb.append(" 	update_ts =  ?, ");
		sb.append(" 	update_user_id =  ?, ");
		sb.append(" 	delete_fg =  ? ");
		sb.append(" WHERE ");
		sb.append(" 	hachuno_cd = ? AND ");
		sb.append(" 	syohin_cd = ? AND ");
		sb.append(" 	bumon_cd = ? AND ");
		sb.append(" 	tenpo_cd = ? ");
		
		return sb.toString();
	}
	

	
	/**
	 * NULLなら空白を返す
	 * 引数booleanがtrueの場合NULLや空白で"0"を返す
	 * @return String
	 */
	private String nullToString( String str, boolean intFg ) {
		if( intFg ) {
			if( str == null ) return "0";
			else if( str.trim().equals("") ) return "0";
		}
		if( str == null ) return "";
		return str.trim();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}