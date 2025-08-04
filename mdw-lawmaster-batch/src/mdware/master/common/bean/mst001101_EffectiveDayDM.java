package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.common.util.date.AbstractMDWareDateGetter;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 *            1.1 (2006/01/24) D.Matsumoto 商品マスタ販区切り替え対応
 */
public class mst001101_EffectiveDayDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst001101_EffectiveDayDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		mst000601_KoushinInfoBean bean = new mst000601_KoushinInfoBean();
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setInsertUserName( rest.getString("insert_user_name") );
		bean.setUpdateUserName( rest.getString("update_user_name") );
		bean.setCreateDatabase();
		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map )
	{
		return null;	
	}
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getDateSelectSql( String tbl,String clm, List whereList, String yukoDt )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		//データ存在チェック
		sb.append(" SELECT ");
		sb.append("	trim(DELETE_FG) DELETE_FG ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append("	AND " + clm + " = '"+ yukoDt + "'");
		// 2006/01/24 D.Matsumoto 追加開始
		//同じ有効日に有効データと無効データがあれば有効データを表示する
		sb.append("	ORDER BY  DELETE_FG ");
		// 2006/01/24 D.Matsumoto 追加ここまで
		return sb.toString();
	}	
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 未来日付有効データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureSelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//未来日付有効データ存在チェック
		sb.append(" SELECT ");
		sb.append( clm + " AS YUKO_DT_STR ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " >= TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days, 'YYYYMMDD') ");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "'");
//      ↑↑移植（2006.05.23）↑↑	
		

		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 修正用未来日付削除データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureDel1SelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//未来日付削除データ存在チェック
		sb.append(" SELECT ");
//      ↓↓移植（2006.05.23）↓↓
		sb.append(" TO_CHAR(TO_DATE("+ clm + ",'YYYYMMDD') - 1 days,'YYYYMMDD') AS YUKO_DT_END ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " >= TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days, 'YYYYMMDD') ");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.IRU.getCode() +"'");
//      ↑↑移植（2006.05.23）↑↑			

		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 修正用未来日付削除データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureDel2SelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//未来日付削除データ存在チェック
		sb.append(" SELECT ");		
		sb.append(" "+ clm + " AS YUKO_DT_END ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append("	AND	" + clm + " >= TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + "  + value + " days , 'YYYYMMDD')");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.IRU.getCode() +"'");
//      ↑↑移植（2006.05.23）↑↑		

		return sb.toString();
	}
	

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 削除用未来日付削除データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	/*■■■未使用■■■	
	public String getDelFutureDelSelectSql( String tbl,String clm, List whereList, String value )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//未来日付削除データ存在チェック
		sb.append(" SELECT ");
		sb.append("		TO_CHAR(TO_DATE(MAX(" + clm + ")) + 1,'YYYYMMDD') AS YUKO_DT_STR, ");
		sb.append("		TO_CHAR(ADD_MONTHS(SYSDATE,12),'YYYYMMDD') AS YUKO_DT_END ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " > TO_CHAR(SYSDATE,'YYYYMMDD') ");
		sb.append(" AND DELETE_FG = '1'");

		return sb.toString();
	}
/*	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 過去日付データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getPastSelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//過去日付データ存在チェック
		sb.append(" SELECT ");
		sb.append("		" + clm + " AS YUKO_DT ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " <= TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + 
						Integer.toString(Integer.parseInt(value) - 1) + " days , 'YYYYMMDD') ");
		sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
//      ↑↑移植（2006.05.23）↑↑	
		
		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 新規の時のデータ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getNewSelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//未来日付データ存在チェック
		sb.append(" SELECT ");
		sb.append("		" + clm + " AS YUKO_DT ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}		

		sb.append("	AND	");
		sb.append("    ((" + clm + " <= TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " 
								+ Integer.toString(Integer.parseInt(value) - 1) + " days , 'YYYYMMDD') ");
		sb.append(" 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()+"'  )");
		sb.append("	     OR	");
		sb.append(" 	 AND "+ clm +" >= TO_CHAR( to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days, 'YYYYMMDD' ) ");
		sb.append("		)	");
//      ↑↑移植（2006.05.23）↑↑	
		
		return sb.toString();
	}
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 有効日の範囲
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getRangeSelectSql( String tbl,String clm, List whereList, String value )
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("		TO_CHAR(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days, 'YYYYMMDD') AS YUKO_DT_STR ");
		sb.append("		TO_CHAR(ADD_MONTHS(to_date('" + systemtime + "','YYYYMMDDHH24MISS'),12),'YYYYMMDD') AS YUKO_DT_END ");
		sb.append(" FROM DUAL");
//      ↑↑移植（2006.05.23）↑↑	
		return sb.toString();
	}
	
	
	/**
	 * 予約レコード件数算出ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getReserveSql( String tbl,String clm, List whereList, String value ,String yukoDt )	
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	count(*) cnt ");
		sb.append(" FROM ");
		sb.append("	" + tbl + " " );
		sb.append(" WHERE ");

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		//有効日
		sb.append(" 	 AND "+ clm +" >= TO_CHAR( to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days , 'YYYYMMDD') ");
		if(!yukoDt.equals("")){
			sb.append(" AND	"+ clm +" != '" + yukoDt + "'");
		}
//      ↑↑移植（2006.05.23）↑↑	
		
		return sb.toString();
	}
	/**
	 * 現在有効日取得ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getYukoDtSql( String tbl,String clm, List whereList, String value)
	{
//      ↓↓移植（2006.05.23）↓↓
		String systemtime = "";
		try {
			systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
		} catch(SQLException e){
			e.printStackTrace();
		}
//      ↑↑移植（2006.05.23）↑↑			
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	MIN(TBLA."+ clm +") YUKO_DT ");
		sb.append("FROM ");
//      ↓↓移植（2006.05.23）↓↓		
		sb.append("	( ");
//BUGNO-S071 2005.07.14 T.Makuta START
/*
		sb.append("	 SELECT  MAX("+ clm +") YUKO_DT ");
		sb.append("   FROM " + tbl + " " );
		sb.append("	  WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append(" 	 AND "+ clm +" <= TO_CHAR(SYSDATE + " + Integer.toString(Integer.parseInt(value) - 1) + " ,'YYYYMMDD') ");
		sb.append(" 	 AND DELETE_FG = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");
*/
		sb.append("	 SELECT YUKO_DT ");
		sb.append("   FROM " + tbl + " " );
		sb.append("	  WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append("   AND "+ clm +" = ( ");
		sb.append("			SELECT  MAX("+ clm +") YUKO_DT ");
		sb.append("			FROM " + tbl + " " );
		sb.append("			WHERE ");
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append("			AND "+ clm +" <= to_char(to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + Integer.toString(Integer.parseInt(value) - 1) + " days , 'YYYYMMDD')");
		sb.append("   ) AND DELETE_FG = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");
		sb.append("	)TBLA ");
//BUGNO-S071 2005.07.14 T.Makuta END
		sb.append("     UNION ALL ");
		sb.append("SELECT ");
		sb.append("	MIN(TBLB."+ clm +") YUKO_DT ");
		sb.append("FROM ");
		sb.append("	( ");
//BUGNO-S071 2005.07.14 T.Makuta START
/*
		sb.append("	 SELECT  MAX("+ clm +") YUKO_DT ");
		sb.append("   FROM " + tbl + " " );
		sb.append("	  WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append(" 	 AND "+ clm +" <= TO_CHAR(SYSDATE + " + Integer.toString(Integer.parseInt(value) - 1) + " ,'YYYYMMDD') ");
		sb.append(" 	 AND DELETE_FG = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");
*/
		sb.append("	 SELECT  MIN("+ clm +") YUKO_DT ");
		sb.append("   FROM " + tbl + " " );
		sb.append("	  WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}		
		sb.append(" 	 AND "+ clm +" >= TO_CHAR( to_date('" + systemtime + "','YYYYMMDDHH24MISS') + " + value + " days , 'YYYYMMDD') ");
		sb.append(" 	) TBLB ");
//      ↑↑移植（2006.05.23）↑↑
		
		return sb.toString();
	}

	/**
	 * 対象テーブルの指定された条件で存在チェックを行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getExistSelectSql( String tbl,String clm, List whereList)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	"+ clm +" YUKO_DT ");
		sb.append(" , ");
		sb.append(" TRIM(INSERT_TS) INSERT_TS ");
		sb.append(" , ");		
		sb.append(" TRIM(UPDATE_TS) UPDATE_TS ");
		sb.append(" , ");		
		sb.append(" TRIM(DELETE_FG) DELETE_FG ");		
		sb.append("FROM ");
		sb.append("	" + tbl + " " );
		sb.append("WHERE ");

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
	}

	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return
	 */
	protected String replaceAll( String base, String before, String after )
	{
		if( base == null )
			return base;
		int pos = base.lastIndexOf(before);
		if( pos < 0 )
			return base;
		int befLen = before.length();
		StringBuffer sb = new StringBuffer( base );
		while( pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0 )
		{
			sb.delete(pos,pos + befLen);
			sb.insert(pos, after);
			pos--;
		}
		return sb.toString();
	}
}
