/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する有効日を含むRetrieveCommand用（エラーチェック・データ取得）クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import java.util.*;
import java.sql.*;

import mdware.common.util.StringUtility;
import mdware.master.common.bean.mst000101_DbmsBean;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.bean.*;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import java.lang.reflect.Method;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
//import mdware.master.common.bean.mst001001_EffectiveDayBean;
//import mdware.master.common.bean.mst001101_EffectiveDayDM;
import mdware.master.common.bean.mst000201_EffectiveDayCheckBean;
import mdware.master.common.bean.mst000201_EffectiveDayCheckDM;
import mdware.master.util.RMSTDATEUtil;

/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する有効日を含むRetrieveCommand用（エラーチェック・データ取得）クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst001201_RetrieveBean {

	/**
	 * 有効日存在レコード用共通RetrieveCommand用
	 * <br>
	 * Ex)<br>
	 * mst001001_EffectiveDayBean.getRetrieve() -&gt; boolean[3]<br>
	 * <br>
	 * @param		String		tableNa		: 対象テーブル名称
	 * @param		String		columnNa	: 有効日カラム名称
	 * @param		DataHolder	DH			: 対象データホルダー
	 * @param		DataModule	DM			: 対象データモジュール
	 * @param					Keep		: 対象画面用Bean
	 * @param		Map			whereList	: 有効日を除くKEY部
	 * @param		String		yukoDt		: 入力有効日
	 * @param		String		addValue	: 暦日加算値
	 * @return		List					:[0]BH		データホルダー
	 * 										:[1]Keep	対象画面用Bean
	 * 										:[2]
	 */
	public static List getRetrieve(String tableNa, String columnNa, DataHolder DH, DataModule DM
									, Object keep, List whereList, String yukoDt, String addValue, mst000101_DbmsBean db) throws Exception,SQLException
	{

		List lst = new ArrayList();								//関数戻り値
		String errFlg	= mst000101_ConstDictionary.ERR_CHK_NORMAL;	//Getter戻り値 エラーフラグ
		String errMsg	= "";										//Getter戻り値 エラーメッセージ
		String process	= "";										//Getter戻り値 処理状況
		List	where	= new ArrayList();							//検索条件修正用
		BeanHolder BH	= new BeanHolder(DM);						//値取得用BeanHolder

		//エラーチェック用
		Map errParam = new HashMap();									//抽出条件格納用
		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();	//存在チェック用

		//マスタ日付取得
		String MSTDATE = RMSTDATEUtil.getMstDateDt();

		//現在有効日取得用
//		mst001101_EffectiveDayDM effectiveDm =	new mst001101_EffectiveDayDM();	//現在有効日取得用
		mst000201_EffectiveDayCheckDM effectiveDm = new mst000201_EffectiveDayCheckDM();

		Class entityClass = keep.getClass();
		Object objEntity = keep;
				
		//Method情報を取得
		Method method = null; //戻り値を保持する変数
		//引数の型のClassオブジェクトを保持する配列
		Class[]  params = new Class[1];
		params[0] = new String("").getClass();

		Object[] param = new Object[1];
		Object objReturn = null;

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//更新処理内容の取得
		String UpdateProcessFlg   = "";

		ResultSet rset = null;			//抽出結果(ResultSet)			

		try {
			//処理状況の取得
			process   = getValue( "getProcessingDivision",keep );
			if( process == null ){
				return null;
			}
			
			//新規時
			if(process.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
				UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;				
//			    ↓↓2006.06.26 maojm カスタマイズ修正↓↓
//				if(mst000401_LogicBean.chkNullString(DH.getParameter("tx_copykanricd")).trim() != null &&
//						!(mst000401_LogicBean.chkNullString(DH.getParameter("tx_copykanricd")).trim().equals("")) ){
//				//コピー販区指定時
				//コピー部門指定時
				if(mst000401_LogicBean.chkNullString(DH.getParameter("tx_copybumoncd")).trim() != null &&				
						!(mst000401_LogicBean.chkNullString(DH.getParameter("tx_copybumoncd")).trim().equals("")) ){

				where.clear();
					for( int j = 0 ;j<whereList.size();j++ ){
						if(whereList.get(j).toString().indexOf("KANRI_CD") == -1 ){
							where.add(whereList.get(j));
						} else {
//							where.add( " AND KANRI_CD  = '" + mst000401_LogicBean.chkNullString(DH.getParameter("tx_copykanricd")).trim() + "' " );						
							where.add( " AND KANRI_CD  =  '" + StringUtility.charFormat(mst000401_LogicBean.chkNullString(DH.getParameter("tx_copybumoncd")).trim(),4,"0",true) + "' " );
						}
					}
					
					//対象テーブルチェック					
//					rset = db.executeQuery(effectiveDm.getYukoDtSql( tableNa, columnNa, where, addValue ));    //抽出結果(ResultSet)
					rset = db.executeQuery(effectiveDm.getYukoDtSql( tableNa, columnNa, where, MSTDATE, false));
					if(rset.next()){
						if(!mst000401_LogicBean.chkNullString( (String)rset.getString("yuko_dt") ).equals("")){
							//データ非存在（コピー販区を販区コードにセットしレコードをBeanにセットし終了）
//							DH.updateParameterValue("kanri_cd",mst000401_LogicBean.chkNullString(DH.getParameter("tx_copykanricd")).trim());
							DH.updateParameterValue("kanri_cd",mst000401_LogicBean.chkNullString(DH.getParameter("tx_copybumoncd")).trim());
//				　↑↑2006.06.26 maojm カスタマイズ修正↑↑
							DH.updateParameterValue(columnNa,mst000401_LogicBean.chkNullString( (String)rset.getString("yuko_dt")).trim());							
						} else {
							//データ存在（エラー）
							errFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
							errMsg = map.get("0005").toString();							
						}
					} else {
						//データ存在（エラー）
						errFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
						errMsg = map.get("0005").toString();						
					}
				}
				
			}

			//修正・削除時
			if(process.equals(mst000101_ConstDictionary.PROCESS_UPDATE)
			|| process.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
				UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE;
				//対象テーブルチェック(有効日を含め)
//				rset = db.executeQuery(effectiveDm.getDateSelectSql( tableNa, columnNa, whereList, yukoDt ));    //抽出結果(ResultSet)
				rset = db.executeQuery(effectiveDm.getDateSelectSql( tableNa, columnNa, whereList, yukoDt));
				if(!rset.next()){
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;					
//					String genYukoDt = mst001001_EffectiveDayBean.getGenYoyaku( tableNa, columnNa, whereList, addValue ,db );
					String genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, tableNa, columnNa, whereList, yukoDt, true);
					//取得した現在有効日のレコードをBeanにセットし了
					DH.updateParameterValue(columnNa,genYukoDt);					
				} else {
					//処理状況=修正
					if( process.equals(mst000101_ConstDictionary.PROCESS_UPDATE) ){
						if(rset.getString("delete_fg").equals(mst000801_DelFlagDictionary.IRU.getCode()) ){
							//削除データ存在（エラー）
							errFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
							errMsg = map.get("0005").toString();							
						}
					}
					//処理状況=削除
					if( process.equals(mst000101_ConstDictionary.PROCESS_DELETE) ){
						if(rset.getString("delete_fg").equals("1") ){
							errMsg = map.get("40017").toString();
							//削除データは物理削除
							UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;							
						}
						//過去データが存在しないと物理削除
						lst.clear();
						for( int i = 0 ;i<whereList.size();i++ ){
							lst.add(whereList.get(i));				
						}
//						lst.add(" AND " + columnNa + " < TO_CHAR(SYSDATE + " + addValue  + " ,'YYYYMMDD') ");				
//					      ↓↓　移植(2006.05.25) ↓↓
						lst.add(" AND " + columnNa + " < TO_CHAR(TO_DATE('" + yukoDt + "') + " + addValue  + " DAYS ,'YYYYMMDD') ");				
//						if( !mst001001_EffectiveDayBean.getDateYukoDt( db, tableNa, columnNa, lst, "" ) ){
						if( !mst000201_EffectiveDayCheckBean.getDateYukoDt(db, tableNa, columnNa, lst, MSTDATE)){
							UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;
						} else {
							//直近予約レコードが削除レコードの場合、物理削除
							lst.clear();
							for( int i = 0 ;i<whereList.size();i++ ){
								lst.add(whereList.get(i));				
							}
//							lst.add(" AND " + columnNa + " >= TO_CHAR(SYSDATE + " + addValue  + " ,'YYYYMMDD') ");
							lst.add(" AND " + columnNa + " >= TO_CHAR(TO_DATE('" + yukoDt + "') + " + addValue  + " DAYS ,'YYYYMMDD') ");
//						      ↑↑　移植(2006.05.25) ↑↑
//							lst.add(" ORDER BY " + columnNa );				
//							rset = db.executeQuery(effectiveDm.getExistSelectSql( tableNa, columnNa, lst ));    //抽出結果(ResultSet)
							rset = db.executeQuery(effectiveDm.getExistSelectSql( tableNa, columnNa, lst ));
							if(rset.next()){
								if( mst000401_LogicBean.chkNullString( rset.getString("delete_fg") ).trim().equals( mst000801_DelFlagDictionary.IRU.getCode() ) ){
									UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;									
								}
							}						
						}
					}
				}
			}

			//照会時
			if(process.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)){
				String genYukoDt = "";
				if(yukoDt.equals("")){
						//現在有効日取得
//						genYukoDt = mst001001_EffectiveDayBean.getGenYoyaku( tableNa, columnNa, whereList, addValue, db );
					genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt( db, tableNa,columnNa,whereList, MSTDATE, true);
				} else {
//					genYukoDt = yukoDt;
					genYukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, tableNa,columnNa,whereList, yukoDt, true);
				}
//				rset = db.executeQuery(effectiveDm.getDateSelectSql( tableNa, columnNa, whereList, genYukoDt ));    //抽出結果(ResultSet)
				rset = db.executeQuery(effectiveDm.getDateSelectSql( tableNa, columnNa, whereList, genYukoDt ));
				if(!rset.next()){
					//データ非存在（エラー）
					errFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
					errMsg = map.get("0005").toString();
				} else {
					if(rset.getString("delete_fg").equals(mst000801_DelFlagDictionary.IRU.getCode())){
						errMsg = map.get("40017").toString();
					}
					DH.updateParameterValue(columnNa,genYukoDt);					
				}
			}
			
			//レコード取得
			BH.setDataHolder(DH);
			
			//パフォーマンス改善　当クラスで作成したリストを返却する（beanholderは無駄なデータベースアクセスが発生するためリストを使うのが望ましい）
			
			//BH.refresh();
			List ls=BH.getSpecificationPageBeanList(0);
			//戻り値設定
			lst.clear();
			lst.add(BH);
			lst.add(keep);
			//↓追加
			lst.add(ls);
			
			//KeepBeanにエラーフラグセット
			//引数の型のClassオブジェクトを保持する配列
			params = new Class[1];
			params[0] = new String("").getClass();
			try{
				method = entityClass.getMethod("setErrorFlg",params);
			}catch(NoSuchMethodException e){
//				System.out.println("メソッドが見つかりませんでした。");
//				System.exit(0);
				return null;
			}		
	
			param = new Object[1];
			param[0] = new String(errFlg);
			objReturn = null;
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
//				System.out.println("メソッドにアクセスに失敗しました。");
				return null;
			}

			//KeepBeanにエラーメッセージセット
			//引数の型のClassオブジェクトを保持する配列
			params = new Class[1];
			params[0] = new String("").getClass();
			try{
				method = entityClass.getMethod("setErrorMessage",params);
			}catch(NoSuchMethodException e){
//				System.out.println("メソッドが見つかりませんでした。");
//				System.exit(0);
				return null;
			}		
			param = new Object[1];
			param[0] = new String(errMsg);
			objReturn = null;
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
//				System.out.println("メソッドにアクセスに失敗しました。");
				return null;
			}
			
			//更新処理内容
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
			
						
		} catch(SQLException sqle) {					
			throw sqle;
		} catch(Exception e) {
			throw e;						
		} finally{
			if(db!=null){
				db.close();
			}
		}
		return lst;

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
