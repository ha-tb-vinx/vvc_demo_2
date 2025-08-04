package mdware.common.util.db;

/**
 * <p>タイトル: 連番取得クラス</p>
 * <p>説明: R_SEQより新しい連番を取得する</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author nob
 * @version 1.0
 * @version 2.0 2004.12.06 takuwa フジ対応
 * @version 2.1 2004.12.16 takuwa 伝票番号20桁対応
 * @version 2.2 2004.12.21 takuwa STCLIB DB2対応による修正
 * @version 3.0 2006.06.06 nakazawa MDWAREのユーティリティとしてリリース
 * @version 3.1 2006.06.07 kuzuhara 桁合わせ対応、更新日時、更新者ID、更新日時を更新項目に追加、各メソッドの引数にログインIDを追加
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.util.date.AbstractMDWareDateGetter;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;

public class MDWareSeq {

	private static final String databaseName = "rbsite_ora";
	private static final String ZERO_STRING = "0";
	private static final String FIRST_STRING = "1";
	
	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @param loginId ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	protected synchronized static String nextNb(String seqName, String loginId) {

		DataBase db           = null; //データベース
		ResultSet rset        = null; //検索結果レコード

		BigDecimal nextVal   = null; //新番号
		
		BigDecimal curCnt    = null;  //現在番号
		BigDecimal minCnt    = null;  //最小値
		BigDecimal maxCnt    = null;  //最大値
		int  seqLength = 0;  //桁数
		//String chk_digit_kb = "0"; //チェックデジット区分
		
		//パラメータ未設定時は戻り値"0"
		if(seqName.trim().length()==0){
			return ZERO_STRING;  // 返値に0をセット
		}
		
		db = new DataBase(databaseName);

		try{

			//ＤＢ開始処理
			db.commit();	// 暗黙のトランザクション開始

			// 新しい連番の取得
			StringBuffer selectSb = new StringBuffer();
			selectSb.append("select ");
			selectSb.append("cur_cnt,");
			selectSb.append("min_cnt,");
			selectSb.append("max_cnt, ");
			selectSb.append("ketasu ");
			//selectSb.append("chk_digit_kb ");
			selectSb.append("from ");
			selectSb.append("r_seq ");
			selectSb.append("where ");
			selectSb.append("saiban_id = '" + seqName + "' ");
//			selectSb.append("FOR UPDATE WITH RS ");
			selectSb.append("FOR UPDATE ");
			 
			rset = db.executeQuery(selectSb.toString());
			//System.out.println(selectSb.toString());
			if (rset.next()) {
				// 採番出来た場合

				//現在番号、その他採番設定項目を取得
				curCnt       = rset.getBigDecimal("cur_cnt");
				minCnt       = rset.getBigDecimal("min_cnt");
				maxCnt       = rset.getBigDecimal("max_cnt");
				//chk_digit_kb = rset.getString("chk_digit_kb");
				seqLength    = rset.getInt("ketasu");

				if(curCnt == null){curCnt = new BigDecimal(ZERO_STRING);}
				if(minCnt == null){minCnt = new BigDecimal(ZERO_STRING);}
				if(maxCnt == null){maxCnt = new BigDecimal(ZERO_STRING);}

				//現在番号に１加算した値を採番番号とする
				nextVal = new BigDecimal("1").add(curCnt) ;

				//最大値＞最小値かつ採番番号＞最大値の場合は
				//採番番号は最小値とする。
				//※最大値＝最小値、最大値＜最小値の場合はカウントは元に戻らない
				if ((maxCnt.compareTo(minCnt) > 0 ) && (nextVal.compareTo(maxCnt) > 0 )){
					nextVal = minCnt;
				}
				
				//最小値＞採番番号の場合は
				//採番番号は最小値とする。
				if ((maxCnt.compareTo(minCnt) > 0 ) && (nextVal.compareTo(minCnt) < 0 )){
					nextVal = minCnt;
				}
				
				// タイムスタンプを取得
				String updateTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(databaseName);
				
				//採番番号を現在番号に更新する
				StringBuffer updateSb = new StringBuffer();
				updateSb.append("update ");
				updateSb.append(" r_seq " );
				updateSb.append("set ");
				updateSb.append(" cur_cnt = " + nextVal.toString() + ", ");
				updateSb.append(" update_ts = '" + updateTs + "', ");
				updateSb.append(" update_user_id = '" + loginId + "' ");
				updateSb.append("where ");
				updateSb.append("saiban_id = '" + seqName + "' ");

				// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
				if(db.executeUpdate(updateSb.toString()) == 1) {
					db.commit();
				}
				else {
					db.rollback();
					StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
					return ZERO_STRING; // 返値に0をセット
				}
			} else{
				// 採番出来なかった場合
				StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + seqName);
				return ZERO_STRING; // 返値に0をセット
			}
		}
		catch(Exception e) {
			// 例外処理が発生した場合
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			return ZERO_STRING; // 返値に0をセット
		}
		finally {
			//ＤＢ終了処理
			try{if(rset != null){rset.close();}}catch(SQLException sqle){};
			try{if (db != null){db.close();}}catch(Exception e){};
			rset = null;
			db = null;
		}
		
		StringBuffer retVal = new StringBuffer();
		
		// 桁数が採番番号桁数と異なる場合は0詰めして桁をあわせる
		// 桁数が未設定の場合は現在値をそのまま返す
		if (curCnt.toString().length() != seqLength) {
			for (int i = curCnt.toString().length();i < seqLength;i++) {
				retVal.append("0");
			}
		}
		
		retVal.append(curCnt.toString());
		
		return retVal.toString();
	}

	/**
	 * 連番の取得を行う。※long型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @param loginId ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static long nextValLong(String seqName, String loginId) {
		long nextVal;
		nextVal = Long.parseLong(nextNb(seqName, loginId));
		return nextVal;
	}

	/**
	 * 連番の取得を行う。※int型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @param loginId ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static int nextValInteger(String seqName, String loginId) {
		int nextVal;
		nextVal = Integer.parseInt(nextNb(seqName, loginId));
		return nextVal;
	}

	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @param loginId ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static int nextVal(String seqName, String loginId) {
		return nextValInteger(seqName, loginId);
	}
	
	/**
	 * 連番の取得を行う。※String型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @param loginId ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static String nextValString(String seqName, String loginId) {
		return nextNb(seqName, loginId);
	}

// 2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(S)	
	/**
	 * 連番の取得を行う（R_SEQ未登録時、自動登録を行う）※String型
	 * @param seqName   連番名称(table:seq  column:saiban_id)
	 * @param loginId   ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @param seqMin    最小値  （未登録時に利用）
	 * @param seqMax    最大値  （未登録時に利用）
	 * @param seqKetasu 最大桁数（未登録時に利用）
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static String nextValAutoString(String seqName, String loginId, String seqMin, String seqMax, int seqKetasu) {
		return nextNbAuto(seqName, loginId, seqMin, seqMax, seqKetasu);
	}	
	
	/**
	 * 連番の取得を行う（R_SEQ未登録時、自動登録を行う）※String型
	 * @param seqName   連番名称(table:seq  column:saiban_id)
	 * @param loginId   ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @param seqMax    最大値（未登録時に利用）
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static String nextValAutoString(String seqName, String loginId, String seqMax) {
		return nextNbAuto(seqName, loginId, FIRST_STRING, seqMax, seqMax.length() );
	}
	
	/**
	 * 連番の取得を行う。（R_SEQ未登録時、自動登録を行う）
	 * @param seqName      連番名称(table:seq  column:saiban_id)
	 * @param loginId      ログインID(table:seq  column:update_user_id) 更新時に使用
	 * @param seqMin       最小値  （未登録時に利用）
	 * @param seqMax       最大値  （未登録時に利用）
	 * @param seqKetasu    最大桁数（未登録時に利用）
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	protected synchronized static String nextNbAuto(String seqName, String loginId, String seqMin, String seqMax, int seqKetasu) {

		DataBase db          = null; 	// データベース
		ResultSet rset       = null; 	// 検索結果レコード

		BigDecimal nextVal   = null; 	// 新番号
		
		BigDecimal curCnt    = null;  	// 現在番号
		BigDecimal minCnt    = null;  	// 最小値
		BigDecimal maxCnt    = null;  	// 最大値
		
		//パラメータ未設定時は戻り値"0"
		if(seqName.trim().length()==0){
			return ZERO_STRING;  // 返値に0をセット
		}
		
		db = new DataBase(databaseName);

		try{

			//ＤＢ開始処理
			db.commit();	// 暗黙のトランザクション開始
			
			// タイムスタンプを取得
			String updateTs = AbstractMDWareDateGetter.getDefaultProductTimestamp(databaseName);

			// 新しい連番の取得
			StringBuffer selectSb = new StringBuffer();
			selectSb.append("SELECT 		");
			selectSb.append("	 CUR_CNT	");
			selectSb.append("	,MIN_CNT	");
			selectSb.append("	,MAX_CNT	");
			selectSb.append("	,KETASU		");
			selectSb.append("FROM 			");
			selectSb.append("	R_SEQ 		");
			selectSb.append("WHERE 			");
			selectSb.append("	SAIBAN_ID = '" + seqName + "' ");
			selectSb.append("FOR UPDATE 	");
			 
			rset = db.executeQuery(selectSb.toString());

			if (rset.next()) {
				// R_SEQに存在する場合

				//現在番号、その他採番設定項目を取得
				curCnt       = rset.getBigDecimal("CUR_CNT");
				minCnt       = rset.getBigDecimal("MIN_CNT");
				maxCnt       = rset.getBigDecimal("MAX_CNT");

				seqKetasu    = rset.getInt("KETASU");

				if(curCnt == null){curCnt = new BigDecimal(ZERO_STRING);}
				if(minCnt == null){minCnt = new BigDecimal(ZERO_STRING);}
				if(maxCnt == null){maxCnt = new BigDecimal(ZERO_STRING);}

				//現在番号に１加算した値を採番番号とする
				nextVal = new BigDecimal("1").add(curCnt) ;

				//最大値＞最小値かつ採番番号＞最大値の場合は
				//採番番号は最小値とする。
				//※最大値＝最小値、最大値＜最小値の場合はカウントは元に戻らない
				if ((maxCnt.compareTo(minCnt) > 0 ) && (nextVal.compareTo(maxCnt) > 0 )){
					nextVal = minCnt;
				}
				
				//最小値＞採番番号の場合は
				//採番番号は最小値とする。
				if ((maxCnt.compareTo(minCnt) > 0 ) && (nextVal.compareTo(minCnt) < 0 )){
					nextVal = minCnt;
				}
				
				
				//採番番号を現在番号に更新する
				StringBuffer updateSb = new StringBuffer();
				updateSb.append("UPDATE 			");
				updateSb.append("	R_SEQ 			");
				updateSb.append("SET 				");
				updateSb.append("	 CUR_CNT 		= " + nextVal.toString() + "");
				updateSb.append("	,UPDATE_TS 		= '" + updateTs + "' 		");
				updateSb.append("	,UPDATE_USER_ID = '" + loginId + "' 		");
				updateSb.append("WHERE 				");
				updateSb.append("	SAIBAN_ID = '" + seqName + "' ");

				// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
				if(db.executeUpdate(updateSb.toString()) == 1) {
					db.commit();
				}else {
					db.rollback();
					StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
					return ZERO_STRING; // 返値に0をセット
				}
			} else{
				// R_SEQに存在しない場合				
				
				//INSERTする
				StringBuffer insertSb = new StringBuffer();
				insertSb.append("INSERT INTO R_SEQ				");
				insertSb.append("	(                   		");
				insertSb.append("	 SAIBAN_ID          		");
				insertSb.append("	,CUR_CNT            		");
				insertSb.append("	,MIN_CNT            		");
				insertSb.append("	,MAX_CNT            		");
				insertSb.append("	,KETASU             		");
				insertSb.append("	,INSERT_TS          		");
				insertSb.append("	,INSERT_USER_ID     		");
				insertSb.append("	,UPDATE_TS          		");
				insertSb.append("	,UPDATE_USER_ID     		");
				insertSb.append("	)                   		");
				insertSb.append("VALUES                 		");
				insertSb.append("	(                   		");
				insertSb.append("	 '" + seqName + "'  		");
				insertSb.append("	,'" + FIRST_STRING + "' + 1 ");
				insertSb.append("	,'" + seqMin + "'			");
				insertSb.append("	,'" + seqMax + "'    		");
				insertSb.append("	,'" + seqKetasu + "'		");
				insertSb.append("	,'" + updateTs + "' 		");
				insertSb.append("	,'" + loginId + "'  		");
				insertSb.append("	,'" + updateTs + "' 		");
				insertSb.append("	,'" + loginId + "'  		");
				insertSb.append("	)                   		");

				// 初期番号をセット
				curCnt = new BigDecimal(FIRST_STRING);
				
				// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
				if(db.executeUpdate(insertSb.toString()) == 1) {
					db.commit();
					StcLog.getInstance().getLog().info("新規ID=「 " + seqName + "」 R_SEQへ作成しました。");
				}else {
					db.rollback();
					StcLog.getInstance().getLog().fatal("連番の作成に失敗しました。");
					return ZERO_STRING; // 返値に0をセット
				}
			}
		}
		catch(Exception e) {
			// 例外処理が発生した場合
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			return ZERO_STRING; // 返値に0をセット
		}
		finally {
			//ＤＢ終了処理
			try{if(rset != null){rset.close();}}catch(SQLException sqle){};
			try{if (db != null){db.close();}}catch(Exception e){};
			rset = null;
			db = null;
		}
		
		StringBuffer retVal = new StringBuffer();
		
		// 桁数が採番番号桁数と異なる場合は0詰めして桁をあわせる
		// 桁数が未設定の場合は現在値をそのまま返す
		if (curCnt.toString().length() != seqKetasu) {
			for (int i = curCnt.toString().length();i < seqKetasu;i++) {
				retVal.append("0");
			}
		}
		
		retVal.append(curCnt.toString());
		
		return retVal.toString();
	}
	
//  2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(E)	
}
