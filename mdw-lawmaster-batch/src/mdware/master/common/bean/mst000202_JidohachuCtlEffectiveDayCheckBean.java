/**
 * <P>タイトル : 有効日チェック用共通関数クラス</P>
 * <P>説明 : 有効日チェック共通関数クラス</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author 
 * @version 1.0
 * @see 							
 */
package mdware.master.common.bean;

import java.util.*;
import java.sql.*;
import java.lang.reflect.Method;

import mdware.common.util.DateDiff;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

import mdware.common.util.DateChanger;

public class mst000202_JidohachuCtlEffectiveDayCheckBean {
	
	/**
	 * 有効データの存在チェック
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	addValue	: 暦日加算値 
	 * @param		String	yukoDt		: 入力有効日
	 * @param		String	syorikb		: 処理状況
	 */
	public static String getYukoDtCheck(String tableNm, String yukoDt, String kanriKb, String kanriCd, String tenpoCd, Object Keepbeen, mst000101_DbmsBean db, String addValue)
	 throws Exception,SQLException {
		
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
	
		String ErrorFlg = "";
		String ErrorMessage = "";
		
		ErrorFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
		//WHERE条件	(work)
		List lst = new ArrayList();
		int i = 0;
		
		//エラーの取得		
		String errorFlg_sv = getValue( "getErrorFlg",Keepbeen );
		if( errorFlg_sv == null ){
			return null;
		}				
		//処理状況の取得
		String syorikb = getValue( "getProcessingDivision",Keepbeen );
		if( syorikb == null ){
			return null;
		}
		
		//エラーチェック用
		boolean updateFg = false;
		boolean InsertFg = true;
		boolean existsFg = false;
		
		String errorChk = null;

		String chkFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
		String MasterDT = RMSTDATEUtil.getMstDateDt();
		
		//■新規登録の場合		
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			if(!isYukoDtCheckInsert(db, kanriKb, kanriCd, tenpoCd, yukoDt)){
				ErrorFlg = "EDEL";

			} else {
				ErrorFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;

			}
		}

		//■修正の場合		
		if (syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {

			errorChk = isYukoDtCheckUpdete(db, map, tableNm, kanriKb, kanriCd, tenpoCd, yukoDt ,addValue);
				
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)
			   || errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)) {
				
				ErrorFlg = errorChk;
				
			} else {
				ErrorFlg = errorChk;
			}
		}

		//■削除の場合		
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
			
			errorChk = isYukoDtCheckDelete(db, map, tableNm, kanriKb, kanriCd, tenpoCd, yukoDt, addValue);
			
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_DELETE)) {
				
				ErrorFlg = errorChk;
				
			} else {
				ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
				ErrorMessage = errorChk;
				
			}
		}

		//エラーの場合				
		Class entityClass = Keepbeen.getClass();
		Object objEntity  = Keepbeen;
		//Method情報を取得
		Method method   = null; //戻り値を保持する変数
		//引数の型のClassオブジェクトを保持する配列
		Class[]  params = new Class[1];
		params[0]       = new String("").getClass();
		try{
			method = entityClass.getMethod("setErrorFlg",params);
		}catch(NoSuchMethodException e){
			return null;
		}		

		Object[] param   = new Object[1];
		param[0]         = new String(ErrorFlg);
		Object objReturn = null;
		

		//エラーフラグ　セット
		try{
			//メソッドの呼び出し
			objReturn = method.invoke(objEntity,param);
		}catch(Exception e){
			return null;
		}
		
		//エラーメッセージ　セット
		try{
			method = entityClass.getMethod("setErrorMessage",params);
		}catch(NoSuchMethodException e){
			return null;
		}		
		param[0]         = new String(ErrorMessage);
		try{
			//メソッドの呼び出し
			objReturn = method.invoke(objEntity,param);
		}catch(Exception e){
			return null;
		}

		try{
			method = entityClass.getMethod("setUpdateProcessFlg",params);
		}catch(NoSuchMethodException e){

			return null;
		}		
		param[0]         = new String(ErrorFlg);
		try{
			//メソッドの呼び出し
			objReturn = method.invoke(objEntity,param);
		}catch(Exception e){
			return null;
		}

		return ErrorFlg;
	}
	
			
	/**
	 * 新規登録時の有効日の存在チェック
	 * 
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean			    : 存在判定
	 */
	public static boolean chkYukoDtInsert(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String yukoDt) 
																throws Exception,SQLException {
		//対象テーブルの有効日情報の取得
		mst000201_EffectiveDayCheckDM effectivedaydm = new mst000201_EffectiveDayCheckDM();
		ResultSet rset = null;
		boolean ret = false;
		List lst = new ArrayList();
		for( int i = 0 ;i<whereList.size();i++ ){
			lst.add(whereList.get(i));				
		}
		if(yukoDt != null && !yukoDt.equals("")){
			lst.add(" AND " + columnNa + "= '" + yukoDt + "' ");
		}
		try {
			rset = db.executeQuery(effectivedaydm.getExistSelectSql(tableNa,columnNa,lst));
			if (rset.next()){
				ret = true;
			}
		
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;			
	}		

	
	/**
	 * 現在有効日取得
	 * <br>
	 * Ex)<br>
	 * mst001001_getGenYoyaku(tableNa, columnNa, whereList, addValue) -&gt; String<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @return		String				: 現在有効日 
	 */	
	public static String getNowYukoDt(mst000101_DbmsBean db, String table, String column, List whereList, String MSTDATE, boolean delete_chk)  throws Exception,SQLException {
			
		String ret = " ";
		
		//各種テーブルの更新情報の取得
		mst000201_EffectiveDayCheckDM effectivedaydm = new mst000201_EffectiveDayCheckDM();
		try {
			ResultSet rset = db.executeQuery(effectivedaydm.getYukoDtSql(table, column, whereList, MSTDATE, delete_chk));
			if (rset.next()) {
				ret = mst000401_LogicBean.chkNullString((String)rset.getString("yuko_dt"));
			}						
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;
				
	}

	
	/**
	 * 指定メソッドから値の取得を行う
	 * <br>
	 * Ex)<br>
	 * mst001301_UpdateBean.getValue( String methodNa ,Object obj )-&gt; String<br>
	 * <br>
	 * @param		String methodNa		: 対象Method名称
	 * @param		Object obj			: 対象Object
	 * @return		String			    : Methodの値
	 */
	public static String getValue( String methodNa ,Object obj ) {
		
		Class entityClass = obj.getClass();
		Object objEntity  = obj;
		
		String ret = "";
		
		Class[]  params  = new Class[1];		
		Object[] param   = new Object[1];
		param[0]         = new String("");
		Object objReturn = null;

		//Method情報を取得
		Method method = null; //戻り値を保持する変数
		try{
			params = null;
			method = entityClass.getMethod(methodNa,params);
		}catch(NoSuchMethodException e){
			return null;
		}
		try{
			//メソッドの呼び出し
			param     = null;
			objReturn = method.invoke(objEntity,param);
			ret       = objReturn.toString();
		}catch(Exception e){
			return null;
		}
				
		return ret;		
		
	}
	
	
	/**
	 * 新規登録時の自動発注制御マスタチェック
	 * @return boolean
	 */
	public static boolean isYukoDtCheckInsert(mst000101_DbmsBean db,
										String kanriKb, String kanriCd, String tenpoCd, String yukoDt) throws SQLException {
		
		mst000201_EffectiveDayCheckDM efctchkdm = new mst000201_EffectiveDayCheckDM();
		
		StringBuffer sql = new StringBuffer();
		boolean existsFg = false; //データが存在するか
		boolean updateFg = false; //データを修正してよいか
		String UpdateProcessFlg = null;
		
		//マスタ日付
		String MasterDT = RMSTDATEUtil.getMstDateDt();
		//過去データ取得
		ResultSet resPst = db.executeQuery(selYukoDtPast("R_JIDOHACHU_CTL", kanriKb, kanriCd, tenpoCd, yukoDt));

		//■登録する有効日以前のデータチェック
		if (resPst.next()) {
			String tmp_yuko = resPst.getString("yuko_dt");
			String tmp_del = resPst.getString("delete_fg");

			if (DateDiff.getDiffDays(tmp_yuko, MasterDT) > 0 && tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
				//データが存在する場合、削除データで有効日がマスタ日付より過去のときのみ有効
				existsFg = false;
			} else {
				existsFg = true;
			}
		}
		
		//■登録する有効日より未来のデータチェック
		if (!existsFg) {
			//未来データ取得
			ResultSet resFtr = db.executeQuery(selYukoDtFuture("R_JIDOHACHU_CTL", kanriKb, kanriCd, tenpoCd, yukoDt));

			if (resFtr.next()) {
				//有効データの場合エラー
				updateFg = true;
			}
			resFtr.close();
		}
		
		//データが存在しない
		if (!existsFg) {
			updateFg = true;

		}

		return updateFg;
	}

	/**
	 * 更新時の自動発注制御マスタチェック
	 * @return boolean
	 */
	public static String isYukoDtCheckUpdete(mst000101_DbmsBean db, Map msg, String tableNm,
									   String kanriKb, String kanriCd, String tenpoCd, String yukoDt, String addValue) throws SQLException {
		
		boolean existsFg = false; //データが存在するか
		boolean updateFg = true; //データを修正してよいか
		
		String UpdateProcessFlg = null;
		
		String MasterDT = RMSTDATEUtil.getMstDateDt();	//マスタ日付
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(addValue));	//チェック用マスタ日付（生鮮用）
		
		//過去データ取得
		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, kanriKb, kanriCd, tenpoCd, yukoDt));

		//■登録する有効日以前のデータチェック
		if (resPst.next()) {
			//過去データが存在する場合
			String tmp_yuko = resPst.getString("yuko_dt");
			String tmp_del = resPst.getString("delete_fg");

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
				if (tmp_yuko.equals(yukoDt)) {
					//有効日が同じ場合はUPDATE
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE;
					
				} else {
					//有効日が異なる場合はINSERT
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;
					
					//■未来データは2件まで有効
					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(kanriKb, kanriCd, tenpoCd, addMasterDT));
					if (rsCnt.next()) {
						int recCnt = rsCnt.getInt("count");
						rsCnt.close();

						//未来データが2件以上ある場合
						if (recCnt > 1) {
							UpdateProcessFlg = "EFTR";

						}
					}
				}
			} else {
				//過去データの削除フラグが"1"の場合
				existsFg = false;
			}
		}
		resPst.close();

		//■登録する有効日より未来のデータチェック
		if (existsFg && updateFg) {
			
			//未来データ取得
			ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, kanriKb, kanriCd, tenpoCd, yukoDt));

			if (resFtr.next()) {
				String tmp_yuko = resFtr.getString("yuko_dt");
				String tmp_del = resFtr.getString("delete_fg");

				if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
					//有効データの場合エラー
					updateFg = false;
				} else {
					//未来のデータは削除データのみ有効
					updateFg = true;
				}
			} else {
				updateFg = true;
			}
			resFtr.close();
		}
		
		//修正対象データが存在しない
		if (!existsFg) {
			UpdateProcessFlg = msg.get("0027").toString();//更新対象となるデータがありません。

		}
		//修正不可
//		if (!updateFg) {
//			UpdateProcessFlg = "指定された有効日では修正できません。";
//
//		}

		return UpdateProcessFlg;
	}
	
	
	/**
	 * 削除時の自動発注制御マスタチェック
	 * @return boolean
	 */
	public static String isYukoDtCheckDelete(mst000101_DbmsBean db, Map msg, String tableNm,
											   String kanriKb, String kanriCd, String tenpoCd, String yukoDt, String addValue) throws SQLException {

		boolean existsFg = false; //データが存在するか
		boolean deleteFg = true; //データを削除してよいか
		
		boolean yoyakuFg = false;

		String MasterDT = RMSTDATEUtil.getMstDateDt();	//マスタ日付
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(addValue));
		
		String UpdateProcessFlg = null;
		
		//過去データ取得
		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, kanriKb, kanriCd, tenpoCd, yukoDt));

		//■登録する有効日以前のデータチェック
		if (resPst.next()) {
			//過去データが存在する場合
			String tmp_yuko = resPst.getString("yuko_dt");
			String tmp_del = resPst.getString("delete_fg");

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;

				if (tmp_yuko.equals(yukoDt)) {
					//有効日が同じ場合はUPDATE
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE;
					
				} else {
					//有効日が異なる場合はINSERT
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;
					
					//■未来データは2件まで有効
					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(kanriKb, kanriCd, tenpoCd, addMasterDT));
					if (rsCnt.next()) {
						int recCnt = rsCnt.getInt("count");
						rsCnt.close();

						//未来データが2件以上ある場合
						if (recCnt > 1) {
							UpdateProcessFlg = "EFTR";

						}
					}
				}
				
			//削除データの削除は物理削除
			} else {
				if (tmp_yuko.equals(yukoDt)) {
					//有効日が同じ場合はUPDATE
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;
					existsFg = true;
					deleteFg = true;
				}
			}
		}

		//■登録する有効日より未来のデータチェック
		if (existsFg && deleteFg) {

			//未来データ取得
			ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, kanriKb, kanriCd, tenpoCd, yukoDt));

			if (resFtr.next()) {
				//未来が存在する場合エラー
				deleteFg = false;
			} else {
				deleteFg = true;
				existsFg = false;
			}
			resFtr.close();
		}
		
		if (existsFg) {
			//削除対象の商品が存在しない
//			UpdateProcessFlg = "削除対象のデータが存在しません。";
			UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;

		}

		if (!deleteFg) {
			//削除不可
			UpdateProcessFlg = "指定された有効日では削除できません。";

		}
		return UpdateProcessFlg;
	}
	
	
	/**
	 * 対象テーブルの有効日情報の取得
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean			    : 存在判定
	 */
	public static boolean getDateYukoDt(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String yukoDt) 
																throws Exception,SQLException {
		//対象テーブルの有効日情報の取得
		mst000201_EffectiveDayCheckDM efctDM = new mst000201_EffectiveDayCheckDM();
		ResultSet rset = null;    //抽出結果(ResultSet)
		boolean ret = false;
		List lst = new ArrayList();
		for( int i = 0 ;i<whereList.size();i++ ){
			lst.add(whereList.get(i));				
		}
		if(yukoDt != null && !yukoDt.equals("")){
			lst.add(" AND " + columnNa + "= '" + yukoDt + "' ");
		}
		try {
			rset = db.executeQuery(efctDM.getExistSelectSql(tableNa,columnNa,lst));
			if (rset.next()){
				ret = true;
			}
		
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;
	}	
	
	
	/**
	 * 自動発注制御マスタチェック用 (過去データ)
	 * @throws
	 */
	public static String selYukoDtPast(String tableNm, String kanriKb, String kanriCd, String tenpoCd, String yukoDt) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ");
		sql.append("   mst.YUKO_DT as YUKO_DT, ");
		sql.append("   mst.DELETE_FG as DELETE_FG ");
		sql.append(" FROM ");
		sql.append("   "+ tableNm +" mst " );
		sql.append(" WHERE ");
		sql.append("   KANRI_KB = '"+ kanriKb +"'");
		sql.append(" AND ");
		sql.append("   KANRI_CD = '"+ kanriCd +"'");
		sql.append(" AND ");
		sql.append("   TENPO_CD = '"+ tenpoCd +"'");
		//有効日の当日までの直近データ取得
		sql.append(" AND mst.YUKO_DT = ");
		sql.append("     (SELECT max(YUKO_DT)");
		sql.append("      FROM ").append(tableNm);
		sql.append("      WHERE KANRI_KB = mst.KANRI_KB");
		sql.append("      AND   KANRI_CD = mst.KANRI_CD");
		sql.append("      AND   TENPO_CD = mst.TENPO_CD");
		sql.append("      AND   YUKO_DT <= '"+ yukoDt +"') ");

		return sql.toString();
	}
	
	
	/**
	 * 自動発注制御マスタチェック用 (未来データ)
	 * @throws
	 */
	public static String selYukoDtFuture(String tableNm, String kanriKb, String kanriCd, String tenpoCd, String yukoDt) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ");
		sql.append("   mst.YUKO_DT as YUKO_DT, ");
		sql.append("   mst.DELETE_FG as DELETE_FG ");
		sql.append(" FROM ");
		sql.append("   "+ tableNm +" mst " );
		sql.append(" WHERE ");
		sql.append("   KANRI_KB = '"+ kanriKb +"'");
		sql.append(" AND ");
		sql.append("   KANRI_CD = '"+ kanriCd +"'");
		sql.append(" AND ");
		sql.append("   TENPO_CD = '"+ tenpoCd +"'");
		//有効日の未来での直近データ取得
		sql.append(" AND mst.YUKO_DT = ");
		sql.append("     (SELECT min(YUKO_DT)");
		sql.append("      FROM ").append(tableNm);
		sql.append("      WHERE KANRI_KB = mst.KANRI_KB");
		sql.append("      AND   KANRI_CD = mst.KANRI_CD");
		sql.append("      AND   TENPO_CD = mst.TENPO_CD");
		sql.append("      AND   YUKO_DT > '"+ yukoDt +"') ");

		return sql.toString();
	}
	
	
	/**
	 * 未来データ件数取得
	 * 自動発注制御マスタ用
	 * @throws
	 */
	public static String getFtrDateCnt(String kanriKb, String kanriCd, String tenpoCd, String MasterDT) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ");
		sql.append("   COUNT(*) as COUNT ");
		sql.append(" FROM ");
		sql.append("   R_JIDOHACHU_CTL ");
		sql.append(" WHERE KANRI_KB = '"+ kanriKb +"'");
		sql.append(" AND   KANRI_CD = '"+ kanriCd +"'");
		sql.append(" AND   TENPO_CD = '"+ tenpoCd +"'");
		sql.append(" AND   YUKO_DT > '"+ MasterDT +"'");

		return sql.toString();
	}
	
}
