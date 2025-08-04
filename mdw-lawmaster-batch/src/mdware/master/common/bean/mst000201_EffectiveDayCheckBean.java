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

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.StringUtility;

public class mst000201_EffectiveDayCheckBean {
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
	public static String getYukoDtCheck(String tableNm, String yukoDt, Object Keepbeen, mst000101_DbmsBean db, String addValue, boolean delchk)
	 throws Exception,SQLException {

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

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

		//エラーチェック用
		boolean updateFg = false;
		boolean InsertFg = true;
		boolean existsFg = false;

		String errorChk = null;

		String MasterDT = RMSTDATEUtil.getMstDateDt();

//		//■新規登録の場合
//		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//			if(!getYukoDtCheckIns( db, tableNa, columnNa, whereList, addValue, tableNa )){
//				ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;
//				ErrorMessage= map.get("40001").toString();//指定されたデータは既に存在しています。
//			} else {
//				UpdateProcessFlg= mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;
//			}
//		}

//		↓↓2006.07.05 xubq カスタマイズ修正↓↓
//		String hankuCd = getValue( "getHankuCd",Keepbeen );
//		↑↑2006.07.05 xubq カスタマイズ修正↑↑
		String bumonCd = getValue( "getBumonCd",Keepbeen );
		if(bumonCd != null && !"".equals(bumonCd)){
			bumonCd = mst000401_LogicBean.chkNullString(StringUtility.charFormat(bumonCd,4,"0",true));	//部門コード	
		}
		String syohinCd = getValue( "getSyohinCd",Keepbeen );
		String chkFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;
		
		// ↓↓　2006/03/30 kim START mst000201_EffectiveDayCheckBeanでは共用クラスだので業種別,Keepbeanに移動
		// ↓↓　2006/03/17 kim START
		// 加工食品と生鮮の場合、販区を''にして有効日のチェックをする
		//String gyosyu_chk	= getValue("getGyosyuKb",Keepbeen);
		//if (gyosyu_chk != null && (gyosyu_chk.equals("GRO") || gyosyu_chk.equals("FRE")))
		//	hankuCd="";
		// ↑↑　2006/03/17 kim END
		//	↑↑　2006/03/30 kim END
		
		//■修正の場合
		if (syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
//			↓↓2006.07.05 xubq カスタマイズ修正↓↓
//			errorChk = isYukoDtCheckUpdete(db, map, tableNm, hankuCd, syohinCd, yukoDt ,addValue, delchk);
			errorChk = isYukoDtCheckUpdete(db, map, tableNm, bumonCd, syohinCd, yukoDt ,addValue, delchk);
//			↑↑2006.07.05 xubq カスタマイズ修正↑↑
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT) || errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)) {
				UpdateProcessFlg = errorChk;
			} else {
//				if(CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
					//検索データが存在しません。検索条件を変えて再度実行して下さい。
//					ErrorMessage = map.get("0005").toString();
//				} else {
					//選択されたデータは他のユーザによって更新されています。
//					ErrorMessage = map.get("40004").toString();
//				}
				ErrorMessage = errorChk;
				ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;	
			}
		}

		//■削除の場合
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
			UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;
//			↓↓2006.07.06xubq カスタマイズ修正↓↓
//			errorChk = isYukoDtCheckDelete(db, map, tableNm, hankuCd, syohinCd, yukoDt, addValue, delchk);
			errorChk = isYukoDtCheckDelete(db, map, tableNm, bumonCd, syohinCd, yukoDt, addValue, delchk);
//			↑↑2006.07.06xubq カスタマイズ修正↑↑
			
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_DELETE)) {
				UpdateProcessFlg = errorChk;
			} else {
//				if(CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
					//検索データが存在しません。検索条件を変えて再度実行して下さい。
//					ErrorMessage = map.get("0005").toString();						
//				} else {
					//選択されたデータは他のユーザによって更新されています。
//					ErrorMessage = map.get("40004").toString();
//				}
				ErrorMessage = errorChk;
				ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
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

//		if(!errorFlg_sv.equals(mst000101_ConstDictionary.ERR_CHK_ERROR)){
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
//		}
//		if( CheckFlg.equals(mst000101_ConstDictionary.CHECK_SEARCH) ){
			try{
				method = entityClass.getMethod("setUpdateProcessFlg",params);
			}catch(NoSuchMethodException e){
				return null;
			}
			param[0]         = new String(UpdateProcessFlg);
			try{
				//メソッドの呼び出し
				objReturn = method.invoke(objEntity,param);
			}catch(Exception e){
				return null;
			}
//		}
		return ErrorFlg;
	}

	/**
	 * 有効データの存在チェック(管理区分使用時用)
	 * <br>
	 * @param		String	tableNa		: 対象テーブル名称
	 * @param		String	columnNa	: 対象カラム名称
	 * @param		Map		whereList	: WHERE条件
	 * @param		String	addValue	: 暦日加算値
	 * @param		String	yukoDt		: 入力有効日
	 * @param		String	syorikb		: 処理状況
	 */
	public static String getYukoDtCheckKanri(String tableNm, String yukoDt, Object Keepbeen, mst000101_DbmsBean db, String addValue, boolean delchk)
	 throws Exception,SQLException {

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

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
		//エラーチェック用
		boolean updateFg = false;
		boolean InsertFg = true;
		boolean existsFg = false;

		String errorChk = null;

		String MasterDT = RMSTDATEUtil.getMstDateDt();

//		//■新規登録の場合
//		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//			if(!getYukoDtCheckIns( db, tableNa, columnNa, whereList, addValue, tableNa )){
//				ErrorFlg= mst000101_ConstDictionary.ERR_CHK_ERROR;
//				ErrorMessage= map.get("40001").toString();//指定されたデータは既に存在しています。
//			} else {
//				UpdateProcessFlg= mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;
//			}
//		}
		
//		↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//		String hankuCd = "";
		String bumonCd = "";
//		↑↑2006.07.13 xiongjun カスタマイズ修正↑↑		
		String syohinCd = getValue( "getSyohinCd",Keepbeen );
		String chkFlg = mst000101_ConstDictionary.ERR_CHK_NORMAL;

		if(getValue("getKanriKb",Keepbeen).equals(mst000901_KanriKbDictionary.HANKU)
			|| getValue("getKanriKb",Keepbeen).equals(mst000901_KanriKbDictionary.HANKU_SYOUHINCD)){
//			↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//			hankuCd = getValue("getKanriCd",Keepbeen);
			bumonCd = getValue("getKanriCd",Keepbeen);
//			↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
		}

		//■修正の場合
		if (syorikb.equals(mst000101_ConstDictionary.PROCESS_UPDATE)) {
//			↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//			errorChk = isYukoDtCheckUpdete(db, map, tableNm, hankuCd, syohinCd, yukoDt ,addValue, delchk);
			errorChk = isYukoDtCheckUpdete(db, map, tableNm, bumonCd, syohinCd, yukoDt ,addValue, delchk);
//			↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT) ||
				 errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)) {
				UpdateProcessFlg = errorChk;
			} else {
				ErrorMessage = errorChk;
				ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;	
			}
		}

		//■削除の場合		
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_DELETE)){
			UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_DELETE;
//			↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//			errorChk = isYukoDtCheckDelete(db, map, tableNm, hankuCd, syohinCd, yukoDt, addValue, delchk);
			errorChk = isYukoDtCheckDelete(db, map, tableNm, bumonCd, syohinCd, yukoDt, addValue, delchk);
//			↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
			if(errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE)
				|| errorChk.equals(mst000101_ConstDictionary.UPDATE_PROCESS_DELETE)) {
				UpdateProcessFlg = errorChk;
			} else {
				ErrorMessage = errorChk;
				ErrorFlg = mst000101_ConstDictionary.ERR_CHK_ERROR;
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
		param[0]         = new String(UpdateProcessFlg);
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
	public static String getNowYukoDt(mst000101_DbmsBean db, String table, String column, List whereList, String MSTDATE, boolean delete_chk)
										throws Exception,SQLException {
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

	/**
	 * 更新時の商品マスタチェック
	 * @return boolean
	 */
	public static String isYukoDtCheckUpdete(mst000101_DbmsBean db, Map msg, String tableNm,
									   String bumonCd, String syohinCd, String yukoDt, String addValue, boolean delchk) throws SQLException {

		boolean existsFg = false; //データが存在するか
		boolean updateFg = true; //データを修正してよいか

		String UpdateProcessFlg = null;

		String MasterDT = RMSTDATEUtil.getMstDateDt();	//マスタ日付
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(addValue));	//チェック用マスタ日付（生鮮用）

		//過去データ取得
//		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, hankuCd, syohinCd, yukoDt, delchk));
		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, bumonCd, syohinCd, yukoDt, delchk));
		
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
//					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(hankuCd, syohinCd, yukoDt));
//					↓↓2006.07.06xubq カスタマイズ修正↓↓
//					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(hankuCd, syohinCd, addMasterDT));
					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(bumonCd, syohinCd, addMasterDT));
//					↑↑2006.07.06xubq カスタマイズ修正↑↑
					
					if (rsCnt.next()) {
						int recCnt = rsCnt.getInt("count");
						rsCnt.close();

						//未来データが2件以上ある場合
						if (recCnt > 1) {
							UpdateProcessFlg = msg.get("40011").toString();//将来データが2件、既に存在しています。
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
			if (UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)) {
				//未来データ取得
//				ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, hankuCd, syohinCd, yukoDt));
				ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, bumonCd, syohinCd, yukoDt));
				
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
		}

		//修正対象データが存在しない
		if (!existsFg) {
			UpdateProcessFlg = msg.get("0027").toString();//更新対象となるデータがありません。
		}
		//修正不可
		if (!updateFg) {
			UpdateProcessFlg = "指定された有効日では修正できません。";
		}
		return UpdateProcessFlg;
	}

	/**
	 * 削除時の商品マスタチェック
	 * @return boolean
	 */
	public static String isYukoDtCheckDelete(mst000101_DbmsBean db, Map msg, String tableNm,
									   String bumonCd, String syohinCd, String yukoDt, String addValue, boolean delchk) throws SQLException {

		boolean existsFg = false; //データが存在するか
		boolean deleteFg = true; //データを削除してよいか
		boolean yoyakuFg = false;

		String MasterDT = RMSTDATEUtil.getMstDateDt();	//マスタ日付
		String addMasterDT = DateChanger.addDate(MasterDT, Integer.parseInt(addValue));

		String UpdateProcessFlg = null;

		//過去データ取得
//		↓↓2006.07.06xubq カスタマイズ修正↓↓
//		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, hankuCd, syohinCd, addMasterDT));
		ResultSet resPst = db.executeQuery(selYukoDtPast(tableNm, bumonCd, syohinCd, yukoDt, delchk));
//		↑↑2006.07.06xubq カスタマイズ修正↑↑

		//■登録する有効日以前のデータチェック
		if (resPst.next()) {
			//過去データが存在する場合
			String tmp_yuko = resPst.getString("yuko_dt");
			String tmp_del = resPst.getString("delete_fg");

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
//				this.YukoDt = tmp_yuko;

				if (tmp_yuko.equals(yukoDt)) {
					//有効日が同じ場合はUPDATE
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_UPDATE;
				} else {
					//有効日が異なる場合はINSERT
					UpdateProcessFlg = mst000101_ConstDictionary.UPDATE_PROCESS_INSERT;

					//■未来データは2件まで有効
//					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(hankuCd, syohinCd, yukoDt));
//					↓↓2006.07.06xubq カスタマイズ修正↓↓
//					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(hankuCd, syohinCd, addMasterDT));
					ResultSet rsCnt = db.executeQuery(getFtrDateCnt(bumonCd, syohinCd, addMasterDT));
//					↑↑2006.07.06xubq カスタマイズ修正↑↑
					if (rsCnt.next()) {
						int recCnt = rsCnt.getInt("count");
						rsCnt.close();

						//未来データが2件以上ある場合
						if (recCnt > 1) {
							UpdateProcessFlg = msg.get("40011").toString();//将来データが2件、既に存在しています。
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
			if (UpdateProcessFlg.equals(mst000101_ConstDictionary.UPDATE_PROCESS_INSERT)) {
//				↓↓2006.07.06xubq カスタマイズ修正↓↓				
				//未来データ取得
//				ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, hankuCd, syohinCd, yukoDt));
				ResultSet resFtr = db.executeQuery(selYukoDtFuture(tableNm, bumonCd, syohinCd, yukoDt));
//				↑↑2006.07.06xubq カスタマイズ修正↑↑			
				if (resFtr.next()) {
					//未来が存在する場合エラー
					deleteFg = false;
				} else {
					deleteFg = true;
				}
				resFtr.close();
			}
		}
		if (!existsFg) {
			//削除対象の商品が存在しない
			UpdateProcessFlg = "削除対象のデータが存在しません。";
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
	 * 商品マスタチェック用 (過去データ)
	 * @throws
	 */
	public static String selYukoDtPast(String tableNm, String bumonCd, String syohinCd, String yukoDt,boolean delchk) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   mst.YUKO_DT as YUKO_DT, ");
		sql.append("   mst.DELETE_FG as DELETE_FG, ");
		sql.append("   mst.SINKI_TOROKU_DT as SINKI_TOROKU_DT ");
		sql.append(" FROM ");
		sql.append("   "+ tableNm +" mst " );
		sql.append(" WHERE ");
		if (!bumonCd.equals("")) {
//			↓↓2006.07.06xubq カスタマイズ修正↓↓
//			sql.append("     HANKU_CD = '"+ hankuCd +"'");
			sql.append("     BUMON_CD = '"+ bumonCd +"'");
//			↑↑2006.07.06xubq カスタマイズ修正↑↑
			sql.append(" AND ");
		}
		sql.append(" SYOHIN_CD = '"+ syohinCd +"'");
		//有効日の当日までの直近データ取得
		sql.append(" AND mst.YUKO_DT = ");
		sql.append("     (SELECT max(YUKO_DT)");
		sql.append("      FROM ").append(tableNm);
//		↓↓2006.07.06xubq カスタマイズ修正↓↓
//		sql.append("      WHERE HANKU_CD = mst.HANKU_CD");
//		↑↑2006.07.06xubq カスタマイズ修正↑↑
		sql.append("      WHERE BUMON_CD = mst.BUMON_CD");
		sql.append("      AND   SYOHIN_CD = mst.SYOHIN_CD");
		sql.append("      AND   YUKO_DT <= '"+ yukoDt +"') ");
// 2006.01.12 Y.Inaba Add ↓
		if(delchk){
			sql.append(" AND DELETE_FG= '0' ");
		}
// 2006.01.12 Y.Inaba Add ↑
// 		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sql.append(" for read only ");
// 		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		return sql.toString();
	}

	/**
	 * 商品マスタチェック用 (未来データ)
	 * @throws
	 */
	public static String selYukoDtFuture(String tableNm, String bumonCd, String syohinCd, String yukoDt) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   mst.YUKO_DT as YUKO_DT, ");
		sql.append("   mst.DELETE_FG as DELETE_FG ");
		sql.append(" FROM ");
		sql.append("   "+ tableNm +" mst " );
		sql.append(" WHERE ");
		if (!bumonCd.equals("")) {
//			↓↓2006.07.06xubq カスタマイズ修正↓↓
//			sql.append("     HANKU_CD = '"+ hankuCd +"'");
			sql.append("     BUMON_CD = '"+ bumonCd +"'");
//			↑↑2006.07.06xubq カスタマイズ修正↑↑
			sql.append(" AND ");
		}
		sql.append(" SYOHIN_CD = '"+ syohinCd +"'");
		//有効日の当日までの直近データ取得
		sql.append(" AND mst.YUKO_DT = ");
		sql.append("     (SELECT min(YUKO_DT)");
		sql.append("      FROM ").append(tableNm);
		sql.append("      WHERE BUMON_CD = mst.BUMON_CD");
		sql.append("      AND   SYOHIN_CD = mst.SYOHIN_CD");
		sql.append("      AND   YUKO_DT > '"+ yukoDt +"') ");
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
		sql.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑

		return sql.toString();
	}

	/**
	 * 未来データ件数取得
	 * 商品マスタ用
	 * @throws
	 */
	public static String getFtrDateCnt(String bumonCd, String syohinCd, String MasterDT) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append("   COUNT(*) as COUNT ");
		sql.append(" FROM ");
		sql.append("   R_SYOHIN ");
		sql.append(" WHERE ");
		if (!bumonCd.equals("")) {
//			↓↓2006.07.06xubq カスタマイズ修正↓↓
//			sql.append("     HANKU_CD = '"+ hankuCd +"'");
			sql.append("   BUMON_CD = '"+ bumonCd +"' and ");
//			↑↑2006.07.06xubq カスタマイズ修正↑↑
		}
		sql.append(" syohin_cd = '"+ syohinCd +"' and ");
		sql.append(" yuko_dt > '"+ MasterDT +"'");

		return sql.toString();
	}
}