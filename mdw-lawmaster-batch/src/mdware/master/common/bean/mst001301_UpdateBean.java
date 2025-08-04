package mdware.master.common.bean;

import java.util.*;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.lang.reflect.Method;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.bean.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mst001301_UpdateBean
{
	/**
	 * データの存在チェックを行う
	 * <br>
	 * Ex)<br>
	 * mst001301_UpdateBean.getUpdate( String tableNa, String columnNa, DataModule DM ,Object been, List whereList,Object Keepbeen 
	 * 																				DataHolder dataHolder, String yukoDt) -&gt; List<br>
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件	 
	 * @param		String	yukoDt		: 入力有効日
	 * @return		boolean			    : 存在判定
	 */
	public static List getUpdate( String tableNa, String columnNa, DataModule DM ,Object been, List whereList 
					,Object Keepbeen ,DataHolder dataHolder, String yukoDt, mst000101_DbmsBean db )	throws Exception,SQLException {
		
//		mst001101_EffectiveDayDM effectivedaydm = new mst001101_EffectiveDayDM();    //対象テーブルの有効日情報のDMモジュール

		mst000201_EffectiveDayCheckDM effectivedaydm = new mst000201_EffectiveDayCheckDM();	//対象テーブルの有効日情報のDMモジュール
		BeanHolder BH 			= new BeanHolder(DM);		
		String 	   ErrorFlg		= mst000101_ConstDictionary.ERR_CHK_NORMAL;	// エラーフラグ
		String 	   ErrorMessage	= "";	// エラーメッセージ
		List 	   lst 			= new ArrayList();//WHERE条件
		List 	   ret          = new ArrayList();		
		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());
		//処理状況の取得
		String ProcessingDivision = getValue( "getProcessingDivision",Keepbeen );
		if( ProcessingDivision == null ){
			return null;
		}
		//更新処理内容の取得
		String UpdateProcessFlg   = getValue( "getUpdateProcessFlg",Keepbeen );
		if( UpdateProcessFlg == null ){
			return null;
		}

		try {
			//新規
			if( ProcessingDivision.equals(mst000101_ConstDictionary.PROCESS_INSERT) ){

				lst.clear();
				for( int j = 0 ;j<whereList.size();j++ ){
					lst.add(whereList.get(j));
				}
				lst.add(" and " + columnNa + " = '" + yukoDt + "' ");
// 2006.01.12 Y.Inaba Mod ↓
//				ResultSet rset = db.executeQuery(effectivedaydm.getExistSelectSql(tableNa, columnNa, lst));    //抽出結果(ResultSet)
				ResultSet rset = db.executeQuery(effectivedaydm.getExistSelectSql( tableNa, columnNa, lst ));
// 2006.01.12 Y.Inaba Mod ↑

				if(rset.next()){
//					if(rset.getString("DELETE_FG").equals(mst000801_DelFlagDictionary.INAI.getCode())){
						ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
						ErrorMessage = map.get("40001").toString();					
//					} else{
//						db.executeUpdate(DM.getUpdateSql(been));
//					}
				} else {
					//Insert
					db.executeUpdate(DM.getInsertSql(been));
				}						
				rset.close();	//add by kema 06.09.19
			}			
			
			//修正・削除
			if( ProcessingDivision.equals(mst000101_ConstDictionary.PROCESS_UPDATE) ||
				 ProcessingDivision.equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
				String InsertTs = mst001301_UpdateBean.getValue( "getInsertTs",Keepbeen ).trim();
				if( InsertTs == null ){
					return null;
				}
				String UpdateTs = mst000401_LogicBean.chkNullString(mst001301_UpdateBean.getValue( "getUpdateTs",Keepbeen )).trim();
				lst.clear();
				for( int j = 0 ;j<whereList.size();j++ ){
					lst.add(whereList.get(j));
				}
				lst.add(" and " + columnNa + " = '" + yukoDt + "' ");
//Change START
//				lst.add(" for update nowait "); 			 	
//Change END
				ResultSet rset = db.executeQuery(effectivedaydm.getExistSelectSql( tableNa, columnNa, lst ));
//				ResultSet のtrue判定を素通りするため by kema 06.09.25
				if(rset.next() == false){
					 if(UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)){
						 //Insert
						 db.executeUpdate(DM.getInsertSql(been));
					 } else {
						 ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
						 ErrorMessage = map.get("40001").toString();						
					 }					
				} else {
					if(UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)){
						ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
						ErrorMessage = map.get("40004").toString();						
					} else {
						if(!InsertTs.equals(rset.getString("insert_ts"))){
							ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
							ErrorMessage = map.get("40004").toString();
						} else {
//				1行ずつ更新がかかりエラー時点で終わらすため、この判定をいれるとすべて更新中になってしまう by kema 06.09.26
//							if( UpdateTs.equals(mst000401_LogicBean.chkNullString(rset.getString("update_ts")).toString()) ){
								if(UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE) ){
									//Update									
									db.executeUpdate(DM.getUpdateSql(been));
								}
								if(UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_DELETE) ){
									//Delete
									db.executeUpdate(DM.getDeleteSql(been));									
								}
//							} else {
//								ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
//								ErrorMessage = map.get("40004").toString();							
//							}
						}
					}
				}
				rset.close();	//add by kema 06.09.19
			}
			
			//レコード取得
			if(ErrorFlg.equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
				//エラー				
				Class entityClass = Keepbeen.getClass();
				Object objEntity  = Keepbeen;				
				//Method情報を取得
				Method method   = null; //戻り値を保持する変数
				//引数の型のClassオブジェクトを保持する配列
				Class[]  params = new Class[1];
				params[0]       = new String("").getClass();
				//エラーフラグ　セット
				try{
					method = entityClass.getMethod("setErrorFlg",params);
					
				}catch(NoSuchMethodException e){
					return null;
				}		

				Object[] param   = new Object[1];
				param[0]         = new String(ErrorFlg);
				Object objReturn = null;
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
			} else {
				//正常
				BH.setDataHolder(dataHolder);
//				List arrItiran = (ArrayList)BH.getFirstPageBeanList();				
			}			


			//戻り値設定
			ret.add(BH);
			ret.add(Keepbeen);
			
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
	public static String getValue( String methodNa ,Object obj ){
		
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
	 * 指定メソッドに値の設定を行う
	 * <br>
	 * Ex)<br>
	 * mst001301_UpdateBean.setValue( String methodNa ,Object obj ,String Value )-&gt; Object<br>
	 * <br>
	 * @param		String methodNa		: 対象Method名称
	 * @param		Object obj			: 対象Object
	 * @param		String Value		: セット値
	 * @return		Object			    : Object
	 */
	public static Object setValue( String methodNa ,Object obj ,String Value ){
		
		Class entityClass = obj.getClass();
		Object objEntity  = obj;
				
		//Method情報を取得
		Method method   = null; //戻り値を保持する変数
		//引数の型のClassオブジェクトを保持する配列
		Class[]  params = new Class[1];
		params[0]       = new String("").getClass();
		try{
			method = entityClass.getMethod(methodNa,params);
		}catch(NoSuchMethodException e){
			return null;
		}		

		Object[] param   = new Object[1];
		param[0]         = new String(Value);
		Object objReturn = null;
		try{
			//メソッドの呼び出し
			objReturn = method.invoke(objEntity,param);
			
		}catch(Exception e){
			return null;
		}
		
		return objEntity;
		
	}	

}
