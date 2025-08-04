/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.*;
import java.sql.*;
import java.lang.reflect.Method;

import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する有効日を含むテーブルチェック用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst001001_EffectiveDayBean {
	/**
	 * データの存在チェックを行う
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getDateYukoDt(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	addValue	: 暦日加算値 
	 * @param		String	yukoDt		: 入力有効日
	 * @param		String	syorikb		: 処理状況
	 * @return		String[3]			:[0]エラー判定 [1]エラーメッセージ[2]更新処理内容（登録/修正/削除)
	 */
	public static String getYukoDtCheck(String tableNa, String columnNa, List whereList, String addValue, String yukoDt,
		 	Object Keepbeen, mst000101_DbmsBean db ) throws Exception,SQLException {
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		
		//String[] ret = new String[3];
		String ErrorFlg = "";
		String ErrorMessage = "";		
		String UpdateProcessFlg		= "";		
		
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
		//チェック処理判断
		String CheckFlg = getValue( "getCheckFlg",Keepbeen );
		if( CheckFlg == null ){
			return null;
		}
		
		//新規の場合		
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			if(!getYukoDtCheckIns( db, tableNa, columnNa, whereList, addValue, tableNa )){
				ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;
				ErrorMessage= map.get("40001").toString();
			} else {
				UpdateProcessFlg= mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;
			}
		}
		//修正・削除の場合		
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE)  || 
				syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE) ){										
			//該当データが存在しない場合									
			if(!(getDateYukoDt( db, tableNa, columnNa, whereList, yukoDt ))){
				UpdateProcessFlg= mst000101_ConstDictionary.UPDATE_PROCESS_INSERT; 
				//既に予約レコードが２件以上ある
				if(!(getChkYoyaku( db, tableNa, columnNa, whereList, addValue, yukoDt ))){
					ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;
					ErrorMessage= map.get("40011").toString();					
				} else {					
					String[] chks = new String[3];
					chks[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
					chks[1] = "";
					chks[2] = "";										
					//有効日のチェック
					if(syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
						//修正
						chks = getYukoDtCheckUpdete( db, tableNa, columnNa, whereList, addValue, yukoDt );					
					}
					if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
						//削除						
						chks = getYukoDtCheckDelete( db, tableNa, columnNa, whereList, addValue, yukoDt );					
					}					
					if(!chks[0].equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){
						if(chks[1].equals("") && chks[2].equals("")){
							if( CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
								ErrorMessage= map.get("0005").toString();						
							} else {
								ErrorMessage= map.get("40004").toString();
							}
						} else {
							if( Long.parseLong(chks[1]) >  Long.parseLong(chks[2]) ){
								ErrorMessage= map.get("40027").toString();
							} else {
								ErrorMessage= chks[1].trim() + "から"+ chks[2].trim() + "まで" +map.get("40002").toString();
							}
							
						}
						ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;	
					}					
				}				
			} else {
				//該当データが存在する場合				
				UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE;
				lst.clear();
				for( i = 0 ;i<whereList.size();i++ ){
					lst.add(whereList.get(i));				
				}								
				lst.add(" AND DELETE_FG = '"+mst000801_DelFlagDictionary.IRU.getCode()+"' ");
				
//		      ↓↓移植（2006.06.06）↓↓
				String systemtime = "";
				try {
					systemtime = AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora");
				} catch(SQLException e){
					e.printStackTrace();
				}
//		      ↑↑移植（2006.06.06）↑↑
				
				if(getDateYukoDt( db, tableNa, columnNa, lst, yukoDt )){
					//修正時はエラー
					if(syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE) ){						
						ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;
						if( CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
							ErrorMessage= map.get("0005").toString();						
						} else {
							ErrorMessage= map.get("40004").toString();
						}						
					}
					//削除時は物理削除
					if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
						UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;						
					}
				} else {
					if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
						lst.clear();
						for( i = 0 ;i<whereList.size();i++ ){
							lst.add(whereList.get(i));				
						}
//					    ↓↓移植（2006.05.30）↓↓
						lst.add(" AND " + columnNa + " < TO_CHAR('" + systemtime + "'" + addValue  + "DAYS ,'YYYYMMDD') ");
//					    ↑↑移植（2006.05.30）↑↑
						//削除で通常データが存在しても過去データが存在しないと物理削除
						if(!getDateYukoDt( db, tableNa, columnNa ,lst ,"")){
							UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;
						}
					}
				}
			}
		} 			
		
		//エラー				
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
			//System.out.println("メソッドが見つかりませんでした。");
			//System.exit(0);
			return null;
		}		

		Object[] param   = new Object[1];
		param[0]         = new String(ErrorFlg);
		Object objReturn = null;
		
		if(!errorFlg_sv.equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
			//エラーフラグ　セット
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
				//System.out.println("メソッドにアクセスに失敗しました。");
				return null;
			}
			
			//エラーメッセージ　セット
			try{
				method = entityClass.getMethod("setErrorMessage",params);
			}catch(NoSuchMethodException e){
				//System.out.println("メソッドが見つかりませんでした。");
				//System.exit(0);
				return null;
			}		
			param[0]         = new String(ErrorMessage);
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
				//System.out.println("メソッドにアクセスに失敗しました。");
				return null;
			}
			
		}

		if( CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
			try{
				method = entityClass.getMethod("setUpdateProcessFlg",params);
			}catch(NoSuchMethodException e){
				//System.out.println("メソッドが見つかりませんでした。");
				//System.exit(0);
				return null;
			}		
			param[0]         = new String(UpdateProcessFlg);
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
				//System.out.println("メソッドにアクセスに失敗しました。");
				return null;
			}
			
		}

		return ErrorFlg;
	}
	
	/**
	 * データの存在チェックを行う
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getDateYukoDt(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean			    : 存在判定
	 */
	public static boolean getDateYukoDt(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String yukoDt) 
																throws Exception,SQLException {
		//対象テーブルの有効日情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
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
			rset = db.executeQuery(effectivedaydm.getExistSelectSql(tableNa,columnNa,lst));		//抽出結果(ResultSet)
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
	 * 有効日の範囲チェックを行う(登録)
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getYukoDtCheck(tableNa, columnNa, whereList, addValue, yukoDt) -&gt; boolean<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean			    : 新規判定
	 */
	public static boolean getYukoDtCheckIns(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String addValue, String yukoDt) 
																	throws Exception,SQLException {

		//対象テーブルの有効日情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		ResultSet rset = null;    //抽出結果(ResultSet)
		boolean ret = false;
		
		try {			
			//新規時の判定
			rset = db.executeQuery(effectivedaydm.getNewSelectSql(tableNa,columnNa,whereList,addValue));		//抽出結果(ResultSet)
			if (rset.next()) {
				ret = false;
			} else {
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
	 * 有効日の範囲チェックを行う(修正)
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getYukoDtCheck(tableNa, columnNa, whereList, addValue, yukoDt) -&gt; boolean[3]<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @return		String[3]			:[0]エラー判定 [1]日付1 [2]日付2
	 */
	public static String[] getYukoDtCheckUpdete(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String addValue, String yukoDt)  throws Exception,SQLException {
		
		//対象テーブルの有効日情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		ResultSet rset = null;    //抽出結果(ResultSet)
		String[] ret = { mst000101_ConstDictionary.ERR_CHK_ERROR, "", "" };	
		
		String yukoFr = "";
		String yukoTo = "";	
		
		try {		
//			rset = db.executeQuery(effectivedaydm.getRangeSelectSql(tableNa,columnNa,whereList,addValue));		//抽出結果(ResultSet)
//			if (rset.next()) {				
//				yukoFr = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"));
//				yukoTo = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"));
//			}			
			//未来日付の有効データが存在する場合
			rset = db.executeQuery(effectivedaydm.getFutureSelectSql(tableNa,columnNa,whereList,addValue));		//抽出結果(ResultSet)
			if (rset.next()) {				
//				if(Long.parseLong( mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"))) <= Long.parseLong(yukoDt)
//					&& Long.parseLong( yukoTo ) >= Long.parseLong(yukoDt)){
						ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//				} else {
//					ret[1] = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"));
//					ret[2] = yukoTo;
//				}					
			} else {
				//過去日付又は等しいデータが存在する場合
				rset = db.executeQuery(effectivedaydm.getPastSelectSql(tableNa,columnNa,whereList,addValue));			//抽出結果(ResultSet)
				if (rset.next()) {
					//未来日付の削除予約データが存在する場合
					rset = db.executeQuery(effectivedaydm.getFutureDel1SelectSql(tableNa,columnNa,whereList,addValue));	//抽出結果(ResultSet)
					if (rset.next()) {
//						if(Long.parseLong(yukoFr) <= Long.parseLong(yukoDt)
//						&& Long.parseLong(mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"))) >= Long.parseLong(yukoDt)){
								ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//						} else {
//							ret[1] = yukoFr;
//							ret[2] = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"));
//						}					
					} else {
//						//未来日付の削除予約データが存在しない場合						
//						if(Long.parseLong(yukoFr.trim()) <= Long.parseLong(yukoDt)	&& Long.parseLong(yukoTo.trim()) >= Long.parseLong(yukoDt)){
							ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//						} else {
//							ret[1] = yukoFr;
//							ret[2] = yukoTo;							
//						}
					}		
				}			
			}									
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;

	}
	
	/**
	 * 有効日の範囲チェックを行う(削除)
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getYukoDtCheck(tableNa, columnNa, whereList, addValue, yukoDt) -&gt; boolean[3]<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @return		String[3]			:[0]エラー判定 [1]日付1 [2]日付2
	 */
	public static String[] getYukoDtCheckDelete(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String addValue, String yukoDt)  throws Exception,SQLException {
		
		//対象テーブルの有効日情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		ResultSet rset = null;    //抽出結果(ResultSet)
		String[] ret = { mst000101_ConstDictionary.ERR_CHK_ERROR, "", "" };	
		
		String yukoFr = "";
		String yukoTo = "";	
				
				
		try {			
//			rset = db.executeQuery(effectivedaydm.getRangeSelectSql(tableNa,columnNa,whereList,addValue));		//抽出結果(ResultSet)
//			if (rset.next()) {				
//				yukoFr = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"));
//				yukoTo = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"));
//			}
			//未来日付の有効データが存在する場合
			rset = db.executeQuery(effectivedaydm.getFutureSelectSql(tableNa,columnNa,whereList,addValue));		//抽出結果(ResultSet)
			if (rset.next()) {				
//				if(Long.parseLong( mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"))) < Long.parseLong(yukoDt)
//					&& Long.parseLong(yukoDt) <= Long.parseLong( yukoTo ) ){
						ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//				} else {
//					ret[1] = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_str"));
//					ret[2] = yukoTo;
//				}
			} else {
				//過去日付又は等しいデータが存在する場合
				rset = db.executeQuery(effectivedaydm.getPastSelectSql(tableNa,columnNa,whereList,addValue));			//抽出結果(ResultSet)
				if (rset.next()) {
					//未来日付の削除予約データが存在する場合
					rset = db.executeQuery(effectivedaydm.getFutureDel2SelectSql(tableNa,columnNa,whereList,addValue));	//抽出結果(ResultSet)
					if (rset.next()) {
//						if(Long.parseLong(mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"))) <= Long.parseLong(yukoDt)
//						&& Long.parseLong(yukoDt) <= Long.parseLong( yukoTo ) ){							
								ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//						} else {
//							ret[1] = mst000401_LogicBean.chkNullString(rset.getString("yuko_dt_end"));
//							ret[2] = yukoTo;
//						}					
					} else {
//						//未来日付の削除予約データが存在しない場合						
//						if(Long.parseLong(yukoFr.trim()) <= Long.parseLong(yukoDt)	
//						&&  Long.parseLong(yukoDt) <= Long.parseLong(yukoTo.trim()) ){
							ret[0] = mst000101_ConstDictionary.ERR_CHK_NORMAL;
//						} else {
//							ret[1] = yukoFr;
//							ret[2] = yukoTo;							
//						}
					}		
				}			
			}									
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;
	}

	/**
	 * 予約レコード存在チェック
	 * <br>
	 * Ex)<br>
	 * mst001001_getChkYoyaku(tableNa, columnNa, whereList, addValue, yukoDt) -&gt; boolean<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean				: false/エラー	  
	 */
	public static boolean getChkYoyaku(mst000101_DbmsBean db, String tableNa, String columnNa, List whereList, String addValue, String yukoDt)  throws Exception,SQLException {
			
		boolean ret = true;

		//各種テーブルの更新情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery( effectivedaydm.getReserveSql( tableNa, columnNa, whereList,addValue,yukoDt ));//抽出結果(ResultSet)
			if (rset.next()) {
				if(Integer.parseInt((String)rset.getString("cnt"))>1){
					ret =  false;
				}
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
	public static String getGenYoyaku( String tableNa, String columnNa, List whereList, String addValue, mst000101_DbmsBean db )  throws Exception,SQLException {
			
		String ret = " ";
		
		
		//各種テーブルの更新情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(effectivedaydm.getYukoDtSql(tableNa, columnNa, whereList ,addValue ));    //抽出結果(ResultSet)
			if (rset.next()) {
				ret = mst000401_LogicBean.chkNullString( (String)rset.getString("yuko_dt") );
			}						
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		}
		return ret;
				
	}
	
	/**
	 * 現在有効日取得(Jsp用)
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
	public static String getGenYoyakuJsp( String tableNa, String columnNa, List whereList, String addValue)  throws Exception,SQLException {
			
		String ret = " ";
		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();
		
		//各種テーブルの更新情報の取得
		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール
		try {
			ResultSet rset = db.executeQuery(effectivedaydm.getYukoDtSql(tableNa, columnNa, whereList ,addValue ));    //抽出結果(ResultSet)
			if (rset.next()) {
				ret = mst000401_LogicBean.chkNullString( (String)rset.getString("yuko_dt") );
			}						
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {					
			throw e;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
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
			//System.out.println("メソッドが見つかりませんでした。");
			return null;
		}
		try{
			//メソッドの呼び出し
			param     = null;
			objReturn = method.invoke(objEntity,param);
			ret       = objReturn.toString();
		}catch(Exception e){
			//System.out.println("メソッドにアクセスに失敗しました。");
			return null;
		}
				
		return ret;		
		
	}
}
