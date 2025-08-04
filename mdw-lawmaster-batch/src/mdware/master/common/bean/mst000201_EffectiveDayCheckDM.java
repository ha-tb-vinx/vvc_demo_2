package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/** * <p>タイトル: 有効日チェック用共通関数クラス</p>
 * <p>説明: 有効日チェック用共通関数クラス</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 *            1.1 (2006/01/24) D.Matsumoto 販区切り替え表示対応
 */
public class mst000201_EffectiveDayCheckDM extends DataModule {
	
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	
	/**
	 * コンストラクタ
	 */
	public mst000201_EffectiveDayCheckDM()
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
	protected Object instanceBean( ResultSet rest ) throws SQLException {
		
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
	public String getSelectSql( Map map ) {
		return null;	
	}
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 * @version 1.1 (2006/01/24) D.Matsumoto 同じ有効日に有効データと無効データがあれば有効データを表示する
	 */
	public String getDateSelectSql(String tbl, String clm, List whereList, String yukoDt) {

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
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 未来日付有効データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureSelectSql(String tbl, String clm, List whereList, String value) {

		StringBuffer sb = new StringBuffer();
		
		String MasterDT = RMSTDATEUtil.getMstDateDt();
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(value));

		//未来日付有効データ存在チェック
		sb.append(" SELECT ");
		sb.append( clm + " AS YUKO_DT_STR ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

		sb.append("	AND	" + clm + " >= '" + addMasterDT + "' ");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "'");

		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 修正用未来日付削除データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureDel1SelectSql(String tbl,String clm, List whereList, String value) {

		StringBuffer sb = new StringBuffer();
		
		String MasterDT = RMSTDATEUtil.getMstDateDt();
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(value));

		//未来日付削除データ存在チェック
		sb.append(" SELECT ");		
//    ↓↓移植（2006.05.30）↓↓
		sb.append(" TO_CHAR(TO_DATE("+ clm + ",'YYYYMMDD') - 1 DAYS,'YYYYMMDD') AS YUKO_DT_END ");
//    ↑↑移植（2006.05.30）↑↑	
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " >= '" + addMasterDT + "' ");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.IRU.getCode() +"'");
		

		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 修正用未来日付削除データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getFutureDel2SelectSql(String tbl,String clm, List whereList, String chkdate) {

		StringBuffer sb = new StringBuffer();

		//--- NULLを 'null'とDB2がやりおるので、仕方なく by kema 2006.08.23
		if(chkdate == null){
			chkdate = "00000000";
		}
		//未来日付削除データ存在チェック
		sb.append(" SELECT ");		
		sb.append(" "+ clm + " AS YUKO_DT_END ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		sb.append("	AND	" + clm + " >= '"+ chkdate +"'");
		sb.append(" AND DELETE_FG = '"+ mst000801_DelFlagDictionary.IRU.getCode() +"'");
		
		return sb.toString();
	}
	
	
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 過去日付データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getPastSelectSql(String tbl, String clm, List whereList, String chkdate) {

		StringBuffer sb = new StringBuffer();

		//--- NULLを 'null'とDB2がやりおるので、仕方なく by kema 2006.08.23
		if(chkdate == null){
			chkdate = "00000000";
		}
		//過去日付データ存在チェック
		sb.append(" SELECT ");
		sb.append("		" + clm + " AS YUKO_DT ");
		sb.append(" FROM ");
		sb.append("		" + tbl + " ");
		sb.append(" WHERE ");
		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		
		sb.append("	AND	" + clm + " <= '"+ chkdate +"'");
		sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		return sb.toString();
	}
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 新規の時のデータ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getNewSelectSql(String tbl, String clm, List whereList, String chkdate) {

		StringBuffer sb = new StringBuffer();

		//--- NULLを 'null'とDB2がやりおるので、仕方なく by kema 2006.08.23
		if(chkdate == null){
			chkdate = "00000000";
		}
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
		sb.append("    ((" + clm + " <= '"+ chkdate +"'");
		sb.append(" 	 AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode()+"'  )");
		sb.append("	     OR	");
		sb.append("    (" + clm + " >= '"+ chkdate +"') ");
		sb.append("		)	");
				
		return sb.toString();
	}
	
	
//	/**
//	 * 予約レコード件数算出ＳＱＬの生成を行う。
//	 * 渡されたMapを元にWHERE区を作成する。
//	 * @param map Map
//	 * @return String 生成されたＳＱＬ
//	 */
//	public String getReserveSql(String tbl, String clm, List whereList, String chkdate, String yukoDt) {
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT ");
//		sb.append("	count(*) cnt ");
//		sb.append(" FROM ");
//		sb.append("	" + tbl + " " );
//		sb.append(" WHERE ");
//
//		//キー値セット
//		for (int i=0; i < whereList.size(); i++) {
//			sb.append(whereList.get(i));
//		}
//		
//		//有効日
//		sb.append(" AND	"+ clm +" >= '"+ chkdate +"'");
//		if(!yukoDt.equals("")){
//			sb.append(" AND	"+ clm +" != '" + yukoDt + "'");
//		}
//		
//		return sb.toString();
//	}
	
	
	/**
	 * 現在有効日取得ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getYukoDtSql(String tbl,String clm, List whereList, String chkdate, boolean delete_chk) {

		//--- NULLを 'null'とDB2がやりおるので、仕方なく by kema 2006.08.23
		if(chkdate == null){
			chkdate = "00000000";
		}
//		↓↓移植(2006.05.24)↓↓
		StringBuffer sb = new StringBuffer();
		sb.append("	 SELECT YUKO_DT ");
		sb.append("   FROM " + tbl + " " );
		sb.append("	  WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
//		↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//		sb.append("   AND "+ clm +" = ( ");
		if (whereList != null && whereList.size() == 0) {
			sb.append("    "+ clm +" = ( ");
		} else {
			sb.append("   AND "+ clm +" = ( ");
		}
//		↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
		sb.append("			SELECT  MAX("+ clm +") YUKO_DT ");
		sb.append("			FROM " + tbl + " " );
		sb.append("			WHERE ");
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
//		↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//		sb.append("			AND "+ clm +" <= '"+ chkdate +"' ) ");
		if (whereList != null && whereList.size() == 0) {
			sb.append("			 "+ clm +" <= '"+ chkdate +"' ) ");
		} else {
			sb.append("			AND "+ clm +" <= '"+ chkdate +"' ) ");
		}
//		↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
		if (delete_chk) {
			sb.append("   AND DELETE_FG = '"+mst000801_DelFlagDictionary.INAI.getCode()+"'");
		}

		sb.append("  UNION ");
		sb.append("	 SELECT  MIN("+ clm +") YUKO_DT ");
		sb.append("  FROM " + tbl + " " );
		sb.append("	 WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
//		↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//		sb.append("  AND "+ clm +" >= '"+ chkdate +"'");
		if (whereList != null && whereList.size() == 0) {
			sb.append("   "+ clm +" >= '"+ chkdate +"'");
		} else {
			sb.append(" AND  "+ clm +" >= '"+ chkdate +"'");
		}
//		↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
		sb.append("  ORDER BY  YUKO_DT ");
//		↑↑移植(2006.05.24)↑↑
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 対象テーブルの指定された条件で存在チェックを行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getExistSelectSql(String tbl,String clm, List whereList) {

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
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		
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
