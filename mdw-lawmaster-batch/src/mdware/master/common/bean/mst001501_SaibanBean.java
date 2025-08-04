/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用共通関数群クラスクラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
  * @author Sirius Kikuchi
 * @version 1.0
 * @version 1.01 (2015.08.21) THAO.NTL FIVImart様対応
 * @version 1.02 (2016.01.11) Huy.NT FIVI様対応
 * @see なし
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst004401_SaibanKanriKbDictionary;
import mdware.master.common.dictionary.mst007301_SaibanDictionary;
import mdware.master.util.db.MasterDataBase;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.db.MDWareSeq;

/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用共通関数群クラス</P>
 * <P>説明 : 新ＭＤシステムで採番（単品コード）取得用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius Kikuchi
 * @version 1.0
 * @version 1.01 (2012.09.23) Y.Imai 【MM00158】青果部門内での重複チェック追加対応
 * @version 1.02 (2012.09.27) Y.Imai 【MM00158】青果部門内での重複チェック追加の不具合対応
 * @Version 3.1  (2013.05.12) M.Ayukawa [MSTC00004] ランド様 商品コードチェック仕様変更対応
 * @version 3.02 (2015.07.13) DAI.BQ FIVImart様対応
 * @version 3.03 (2017.02.07) T.Arimoto #1174対応 FIVImart様対応
 * @see なし
 */
public class mst001501_SaibanBean {
	private static final String MIN_INSTORE_TANPIN = "00000";

	private static final int MAX_INSTORE_TANPIN = 99999;
	
	//ログ出力用変数II
	private static BatchLog batchLog = BatchLog.getInstance();

	/**
	 * 採番（単品コード）取得[8桁商品]を行う
	 * <br>
	 * @param		String	hinsyuCd		: 品種コード
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	 * @return	String	retTanpinCd		: 単品コード(null:エラー)
	 */
	public static String getSaiban8Keta(String hinsyuCd, String tanpinCd, String jyotai_kanri_kb, String userId, mst000101_DbmsBean db) throws Exception, SQLException {
		String ret = null;

		try {
			ret = getSaiban8KetaProcess(hinsyuCd, tanpinCd, jyotai_kanri_kb, userId, db);
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		}

		return ret;

	}

	/**
	 * 採番（単品コード）取得[8桁商品]を行う
	 * <br>
	 * @param		String	hinsyuCd		: 品種コード
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	 * @return	String	retTanpinCd		: 単品コード(null:エラー)
	 */
	public static String getSaiban8KetaProcess(String hinsyuCd, String tanpinCd, String jyotai_kanri_kb, String userId, mst000101_DbmsBean db) throws Exception, SQLException {

		//単品コード
		String retTanpinCd = null;
		//自動採番枠品種別マスタ		
		int tanpin[] = getSaibanWakuHinsyu(db, hinsyuCd, userId); //[0]開始コード,[1]終了コード

		if (!tanpinCd.equals("")) {
			//単品コードが入力されている場合
			if (!getSaiban(db, "r_saiban_8keta", hinsyuCd, tanpinCd, jyotai_kanri_kb, userId, mst007301_SaibanDictionary.KETA8.getCode())) {
				retTanpinCd = null;
			} else {
				retTanpinCd = tanpinCd;
			}
		} else {
			retTanpinCd = getTanpin(db, tanpin[0], tanpin[1], jyotai_kanri_kb, mst007301_SaibanDictionary.KETA8.getCode(), hinsyuCd, userId);
		}

		return retTanpinCd;
	}

	/**
	 * 自動採番枠品種別マスタより開始単品コード/終了単品コードの取得を行う
	 * <br>
	 * @param		mst000101_DbmsBean db	: dbインスタンス
	 * @param		String hinsyuCd			: 品種コード
	 * @param		String userId			: ユーザーID 
	 * @return	int[]			    	: [0]開始単品コード,[1]終了単品コード
	 */
	public static int[] getSaibanWakuHinsyu(mst000101_DbmsBean db, String hinsyuCd, String userId) throws Exception, SQLException {
		//採番取得のDMモジュール
		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
		//抽出結果(ResultSet)
		ResultSet rset = null;
		//return   
		int[] ret = { 0, 899 };
		try {

			rset = db.executeQuery(saibanDM.getSaibanWakuHinsyuSelectSql(hinsyuCd)); //抽出結果(ResultSet)
			if (rset.next()) {
				ret[0] = rset.getInt("start_tanpin_cd");
				ret[1] = rset.getInt("end_tanpin_cd");
			} else {
				db.executeUpdate(saibanDM.getSaibanWakuHinsyuInsertSql(hinsyuCd, ret[0], ret[1], userId));
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}

	/**
	 * 採番（単品コード）取得[インストア]を行う
	 * <br>
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @return	String	retTanpinCd	: 単品コード(null:エラー)
	 */
	public static String getSaibanInstore(String tanpinCd, String jyotai_kanri_kb, String userId, mst000101_DbmsBean db) throws Exception, SQLException {
		String ret = null;

		try {
			ret = getSaibanInstoreProcess(tanpinCd, jyotai_kanri_kb, userId, db);
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		}

		return ret;

	}
	/**
	 * 採番（単品コード）取得[インストア]を行う
	 * <br>
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @return	String	retTanpinCd	: 単品コード(null:エラー)
	 */
	public static String getSaibanInstoreProcess(String tanpinCd, String jyotai_kanri_kb, String userId, mst000101_DbmsBean db) throws Exception, SQLException {

		//単品コード
		String retTanpinCd = null;
		if (!tanpinCd.equals("")) {
			if (!getSaiban(db, "r_saiban_instore", "", tanpinCd, jyotai_kanri_kb, userId, mst007301_SaibanDictionary.INSTORE.getCode())) {
				retTanpinCd = null;
			} else {
				retTanpinCd = tanpinCd;
			}

		} else {
			retTanpinCd = getTanpin(db, 0, MAX_INSTORE_TANPIN, jyotai_kanri_kb, mst007301_SaibanDictionary.INSTORE.getCode(), "", userId);
		}
		return retTanpinCd;
	}

	/**
	 * 指定単品コードのデータの取得を行う
	 * <br>
	 * @param		mst000101_DbmsBean db	: dbインスタンス
	 * @param		String tableNa			: 対象テーブル名称 
	 * @param		String hinsyuCd			: 品種コード
	 * @param		String tanpinCd			: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @param		String	saiban_kb		: 採番区分
	 * @return	boolean			    	: 処理結果
	 */
	public static boolean getSaiban(mst000101_DbmsBean db, String tableNa, String hinsyuCd, String tanpinCd, String jyotai_kanri_kb, String userId, String saiban_kb) throws Exception, SQLException {
		//採番取得のDMモジュール
		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
		//抽出結果(ResultSet)
		ResultSet rset = null;
		//return   
		boolean ret = false;
		try {
			rset = db.executeQuery(saibanDM.getSaibanSelectSql(tableNa, hinsyuCd, tanpinCd)); //抽出結果(ResultSet)
			if (rset.next()) {
				//状態管理区分の取得
				String kanri_kb = rset.getString("jyotai_kanri_kb");

				//自動採番（本登録）、手動採番以外は単品コードを取得してよい
				if (kanri_kb != null && !kanri_kb.equals(mst004401_SaibanKanriKbDictionary.JIDOU_HON.getCode()) && !kanri_kb.equals(mst004401_SaibanKanriKbDictionary.SYUDOU.getCode())) {

					if (saiban_kb.equals(mst007301_SaibanDictionary.KETA8.getCode())) {
						db.executeUpdate(saibanDM.getSaibanUpdateSql("r_saiban_8keta", hinsyuCd, tanpinCd, jyotai_kanri_kb, userId));
					} else {
						db.executeUpdate(saibanDM.getSaibanUpdateSql("r_saiban_instore", "", tanpinCd, jyotai_kanri_kb, userId));
					}
					ret = true;
				} else {
					ret = false;
				}
			} else {
				if (saiban_kb.equals(mst007301_SaibanDictionary.KETA8.getCode())) {
					//8桁商品
					db.executeUpdate(saibanDM.getSaiban8KetaInsertSql(hinsyuCd, tanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus11(hinsyuCd + tanpinCd, 0), userId));
				} else {
					//インストア
					db.executeUpdate(saibanDM.getSaibanInstoreInsertSql(tanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus10("02" + tanpinCd + "00000", 12), userId));
				}
				ret = true;
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}

	/**
	 * 単品コードの取得
	 * <br>
	 * Ex)<br>
	 * mst001501_SaibanBean.getTanpin(mst000101_DbmsBean db, int startCd, int endCd, int syoriCnt ,String flg,	String hinsyuCd ) -&gt; String<br>
	 * <br>
	 * @param		mst000101_DbmsBean db		: dbインスタンス
	 * @param		int startCd					: 開始単品コード
	 * @param		int endCd					: 終了単品コード
	 * @param		String flg					: 0:８桁商品,1:インストア
	 * @param		String saiban_kb			: 採番区分
	 * @param		String hinsyuCd				: 品種コード	 
	 * @return	String retTanpinCd			: 単品コード(null:エラー)
	 */
	public static String getTanpin(mst000101_DbmsBean db, int startCd, int endCd, String jyotai_kanri_kb, String saiban_kb, String hinsyuCd, String userId) throws Exception, SQLException {
		//採番取得のDMモジュール
		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
		//単品コード
		String retTanpinCd = null;
		//抽出結果(ResultSet)
		ResultSet rset = null;

		if (saiban_kb.equals(mst007301_SaibanDictionary.KETA8.getCode())) {
			DecimalFormat strNum = new DecimalFormat("000");
			rset = db.executeQuery(saibanDM.getTanpin8Keta(strNum.format(startCd), strNum.format(endCd), hinsyuCd)); //抽出結果(ResultSet)

			if (rset.next()) {
				retTanpinCd = rset.getString("tanpin_cd");

				if (retTanpinCd != null) {
					if (Integer.parseInt(retTanpinCd) > endCd) {
						//取得した番号が最大値より大きい場合
						retTanpinCd = null;
					} else {
						retTanpinCd = retTanpinCd.substring(1);
						db.executeUpdate(saibanDM.getSaiban8KetaInsertSql(hinsyuCd, retTanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus11(hinsyuCd + retTanpinCd, 0), userId));
					}
				} else {
					//データが1件もない場合
					retTanpinCd = strNum.format(startCd);
					db.executeUpdate(saibanDM.getSaiban8KetaInsertSql(hinsyuCd, retTanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus11(hinsyuCd + retTanpinCd, 0), userId));
				}
			}
		} else {
			rset = db.executeQuery(saibanDM.getTanpinInstore()); //抽出結果(ResultSet)

			if (rset.next()) {
				retTanpinCd = rset.getString("tanpin_cd");

				if (retTanpinCd != null) {
					if (Integer.parseInt(retTanpinCd) > MAX_INSTORE_TANPIN) {
						//取得した番号が最大値より大きい場合
						retTanpinCd = null;
					} else {
						retTanpinCd = retTanpinCd.substring(1);
						db.executeUpdate(saibanDM.getSaibanInstoreInsertSql(retTanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus10("02" + retTanpinCd + "00000", 12), userId));
					}
				} else {
					//データが1件もない場合
					retTanpinCd = MIN_INSTORE_TANPIN;
					db.executeUpdate(saibanDM.getSaibanInstoreInsertSql(retTanpinCd, jyotai_kanri_kb, mst001401_CheckDegitBean.getModulus10("02" + retTanpinCd + "00000", 12), userId));
				}
			}
		}

		return retTanpinCd;

	}

//	↓↓2007.01.26 H.Yamamoto カスタマイズ修正↓↓
////	↓↓2006.06.28 zhouzt カスタマイズ修正↓↓
//	/**
//	 * 自動採番処理(バッチ用)
//	 * @param saibanId 採番ID
//	 * @param bySikibetuNo  バイヤー識別NO
//	 * @param byNo    バイヤーNO
//	 * @param db        DataBase
//	 * @return String  商品コード
//	 * @throws Exception 
//	 */
//	public static String getSaibanKeta(String saibanId, String bySikibetuNo, String byNo, DataBase db) throws Exception {
	/**
	 * 自動採番処理(バッチ用)
	 * @param saibanId 採番ID
	 * @param bySikibetuNo  バイヤー識別NO
	 * @param yukoDt  有効日
	 * @param masterDt  マスタ管理日付
	 * @param byNo    バイヤーNO
	 * @param db        DataBase
	 * @return String  商品コード
	 * @throws Exception 
	 */
	public static String getSaibanKeta(String saibanId, String bySikibetuNo, String yukoDt, String masterDt, String byNo, DataBase db) throws Exception {
//	↑↑2007.01.26 H.Yamamoto カスタマイズ修正↑↑

		// 商品コード
		String shouhinCd = "";
		// ｓｑｌ文
		String sql = "";
		// 現在値
		String curCnt = "";
		// デジット
		String checkDegitCd = "";
		// 採番マスタのデータ件数
//		ResultSet rsCount = null;
		// 検索データ
		ResultSet rs = null;
		// 検索データの件数
//		String rCount = "";
		mst001501_SaibanDM saiban = new mst001501_SaibanDM();

//		↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
		// MasterDataBaseのexecuteQueryをループ中で使うと
		// CloseされていないStatementが大量に残るため、DataBaseを使用する
		DataBase dataBase = new DataBase("rbsite_ora");
//		↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
		try{
			// 実用の場合
			if("m_syohin_a07".equals(saibanId)){
				// 連番コードの取得
				sql = "";
				sql = saiban.getSaibanA07Sql(bySikibetuNo);
				rs = db.executeQuery(sql);
				
				// 検索データの連番コードを取る
				if(rs.next()){
					
					// ===BEGIN=== Modify by kou 2006/8/9
					// "=="の意味は、２つのオブジェクトが同一であるか否かの判別、
					// 値がおなじでも、違いオブジェクトであれば、falseになる
					//if(rs.getString("by_sikibetu_no") == bySikibetuNo){
					if(bySikibetuNo.equals(rs.getString("by_sikibetu_no"))){
					// ===END=== Modify by kou 2006/8/9
					
						curCnt = rs.getString("renban_cd");
					}else{
						curCnt = "0000";
					}
				}else{
					curCnt = "0000";
				}
				rs.close();

//				↓↓2006.09.27 H.Yamamoto カスタマイズ修正↓↓
//				// 採番マスタ（実用）に、連番コードが存在しない場合
//				if(curCnt == null || "".equals(curCnt)){
//					// 実用の採番登録処理
//					curCnt = "0";
//
//					// 現在値を計算して、第7桁の値を求める
//					curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
//					shouhinCd = bySikibetuNo.concat(curCnt);
//					checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
//					shouhinCd = shouhinCd.concat(checkDegitCd);
//	
//					// 採番マスタ(実用)の登録処理
//					curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, byNo, db);
//				}else{
//					if("9999".equals(curCnt)){
//						// エラーログ出力
//						batchLog.getLog().info("採番処理ができません。");
//					}else{
//						//	現在値を計算して、第7桁の値を求める
//						curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
//						curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
//						shouhinCd = bySikibetuNo.concat(curCnt);
//						checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
//						shouhinCd = shouhinCd.concat(checkDegitCd);
//						
//						// 採番マスタ(実用)の登録処理
//						curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, byNo, db);
//					}
//				}
				
				String jyotaiKanriFg;
				String deleteFg;
				// ===BEGIN=== Add by kou 2006/10/3
				if(curCnt != null)	curCnt = curCnt.trim();
				// ===END=== Add by kou 2006/10/3
				if(Integer.parseInt(curCnt) == 9999){
//					↓↓2008.03.01 M.Oohashi 商品コード空番検索↓↓
//					curCnt = "0000";
					ResultSet result = db.executeQuery(saiban.getSaibanA07MissingSyohinCdSql(bySikibetuNo));
					if(result.next()){
						curCnt = mst000401_LogicBean.formatZero(result.getString("akiban"),4);
					}
					result.close();
					if(curCnt.equals("10000")){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
						return null;
					}
//					↑↑2008.03.01 M.Oohashi 商品コード空番検索↑↑
					
				} else {
					curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
					curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
				}
				String startVal = curCnt;
				shouhinCd = bySikibetuNo.concat(curCnt);
				checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
				shouhinCd = shouhinCd.concat(checkDegitCd);
				
				while(true){
					sql = saiban.getSaibanA07JyotaiSql(shouhinCd.substring(0,6));
					rs = db.executeQuery(sql);
					jyotaiKanriFg = null;
					deleteFg = null;
					if(rs.next()){
						jyotaiKanriFg = rs.getString("jyotai_kanri_fg");
						deleteFg = rs.getString("delete_fg");
						if(deleteFg != null && deleteFg.trim().equals("1")){
							rs.close();
							sql = saiban.getSaibanA07DelSql(shouhinCd.substring(0,6));
							db.executeUpdate(sql);
							curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, byNo, db);
							return shouhinCd;
						}
						if(jyotaiKanriFg == null){
							rs.close();
							sql = saiban.getSaibanA07JyotaiUpdSql(shouhinCd.substring(0,6), byNo);
							db.executeUpdate(sql);
							return shouhinCd;
						}
					}
					rs.close();
					if(jyotaiKanriFg == null){
						curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, byNo, db);
						return shouhinCd;
					}

					//	現在値を計算して、第7桁の値を求める
					if(Integer.parseInt(curCnt) == 9999){
						curCnt = "0000";
					} else {
						curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
						curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
					}
					shouhinCd = bySikibetuNo.concat(curCnt);
					checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
					shouhinCd = shouhinCd.concat(checkDegitCd);
					if(startVal.equals(curCnt)){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
						return null;
					}
				}
//				↑↑2006.09.27 H.Yamamoto カスタマイズ修正↑↑

			// タグの場合
			}else if( "m_syohin_a08".equals(saibanId)){
//				↓↓2006.09.26 H.Yamamoto カスタマイズ修正↓↓
//				curCnt = mst000401_LogicBean.formatZero(curCnt, 7);
//				checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
//				shouhinCd = curCnt.concat(checkDegitCd);
//				// 連番コードの取得
//				sql = "";
//				sql = saiban.getSaibanA08Sql();
//				rs = db.executeQuery(sql);
//				
//				// 検索データの商品コードを取る
//				while(rs.next()){
//					curCnt = rs.getString("syohin_cd");
//				}
//				rs.close();
//
//				// 採番マスタ（タグ）に、商品コードが存在しない場合
//				if(curCnt == null || "".equals(curCnt)){
//					// タグの採番登録処理
//					sql = "";
//					curCnt = "1000000";
//	
//					// 現在値を計算して、第8桁の値を求める
//					curCnt = mst000401_LogicBean.formatZero(curCnt, 7);
//					checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
//					shouhinCd = curCnt.concat(checkDegitCd);
//	
//					//	採番マスタ(タグ)の登録処理
//					shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, byNo, db);
//					
//				}else{
//					curCnt = curCnt.substring(0, 7);
//					
//					if("9999999".equals(curCnt)){
//						// エラーログ出力
//						batchLog.getLog().info("採番処理ができません。");
//					}else{
//						//	現在値を計算して、第7桁の値を求める
//						curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
//						curCnt = mst000401_LogicBean.formatZero(curCnt, 7);
//						checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
//						shouhinCd = curCnt.concat(checkDegitCd);
//						
//						// 採番マスタ(タグ)の登録処理
//						shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, byNo, db);
//						
//					}
//				}
//
				String jyotaiKanriFg;
				String deleteFg;
				String startVal = MDWareSeq.nextValString("m_syohin_a08", byNo);
				startVal = mst000401_LogicBean.formatZero(startVal, 7);
				curCnt = startVal;
				checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
				shouhinCd = curCnt.concat(checkDegitCd);
				
				while(true){
					sql = saiban.getSaibanA08JyotaiSql(curCnt);
//					↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
//					rs = db.executeQuery(sql);
					rs = dataBase.executeQuery(sql);
//					↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
					jyotaiKanriFg = null;
					deleteFg = null;
					if(rs.next()){
						jyotaiKanriFg = rs.getString("jyotai_kanri_fg");
						deleteFg = rs.getString("delete_fg");
						if(deleteFg != null && deleteFg.trim().equals("1")){
							rs.close();
							sql = saiban.getSaibanA08DelSql(curCnt);
							db.executeUpdate(sql);
							shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, byNo, db);
//							↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
							dataBase.close();
//							↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
							return shouhinCd;
						}
						if(jyotaiKanriFg == null){
							rs.close();
							sql = saiban.getSaibanA08JyotaiUpdSql(curCnt, byNo);
							db.executeUpdate(sql);
//							↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
							dataBase.close();
//							↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
							return shouhinCd;
						}
					}
					rs.close();
					if(jyotaiKanriFg == null){
						shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, byNo, db);
//						↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
						dataBase.close();
//						↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
						return shouhinCd;
					}

					curCnt = MDWareSeq.nextValString("m_syohin_a08", byNo);
					checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
					shouhinCd = curCnt.concat(checkDegitCd);
					if(startVal.equals(curCnt)){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
//						↓↓2008.03.04 M.Oohashi 商品コード空番検索↓↓
						dataBase.close();
//						↑↑2008.03.04 M.Oohashi 商品コード空番検索↑↑
						return null;
					}
				}
//				↑↑2006.09.26 H.Yamamoto カスタマイズ修正↑↑
				

			// デイリー、グロ/バラの場合
			}else if("m_syohin_fre".equals(saibanId) || "m_syohin_gro".equals(saibanId)){	
//				↓↓2006.10.09 H.Yamamoto カスタマイズ修正↓↓
//				// 採番マスタのデータ件数の取得
//				sql = saiban.getSaibanCntSql(saibanId);
//				rsCount = db.executeQuery(sql);
//				
//				// 検索データの件数を取る
//				while(rsCount.next()){
//					rCount = rsCount.getString("count");
//				}
//		
//				if ("0".equals(rCount)) {
//		
//					// 採番マスタ登録処理
//					curCnt = "10000000";
//					sql = "";
//					sql = saiban.getSaibanInsert(saibanId, curCnt, byNo);
//					db.executeUpdate(sql);
//	
//					// 現在値を計算して、第8桁の値を求める
//					shouhinCd = "0400".concat(curCnt);
//					checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
//					shouhinCd = shouhinCd.concat(checkDegitCd);
//		
//				} else {
//					// 採番マスタの検索処理
//					sql = "";
//					sql = saiban.getSaibanSql(saibanId);
//					rs = db.executeQuery(sql);
//					
//					// 検索の現在値を取る
//					while(rs.next()){
//						curCnt = rs.getString("cur_cnt");
//					}
//					rs.close();
//					
//					// 現在値が最大値の場合
//					if("99999999".equals(curCnt)){
//						batchLog.getLog().info("採番処理ができません。");
//	
//					}else{	
//						// 現在値+1
//						curCnt = String.valueOf(Long.parseLong(curCnt) + 1);
//						curCnt = mst000401_LogicBean.formatZero(curCnt, 8);
//		
//						// 採番マスタの更新処理
//						sql = "";
//						sql = saiban.getSaibanUpdate(saibanId, curCnt, byNo);
//						db.executeUpdate(sql);
//
//						// 現在値を作成する
//						shouhinCd = mst000401_LogicBean.formatZero(curCnt, 8);
//						shouhinCd = "0400".concat(shouhinCd);
//		
//						// 現在値を計算して、第13桁の値を求める
//						checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
//						shouhinCd = shouhinCd.concat(checkDegitCd);
//
//					}
//				}
//				↓↓2007.01.26 H.Yamamoto カスタマイズ修正↓↓
//				curCnt = MDWareSeq.nextValString("m_syohin_gro", byNo);
//				curCnt = mst000401_LogicBean.formatZero(curCnt, 8);
				String startVal = MDWareSeq.nextValString("m_syohin_gro", byNo);
				startVal = mst000401_LogicBean.formatZero(startVal, 8);
				curCnt = startVal;
//				↑↑2007.01.26 H.Yamamoto カスタマイズ修正↑↑
				shouhinCd = "0400".concat(curCnt);
				checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
				shouhinCd = shouhinCd.concat(checkDegitCd);
//				↑↑2006.10.09 H.Yamamoto カスタマイズ修正↑↑
				
//				↓↓2007.01.26 H.Yamamoto カスタマイズ修正↓↓
				while(true){
					sql = saiban.getSyohinCntSql(shouhinCd, yukoDt, masterDt);
					rs = db.executeQuery(sql);
					if(rs.next()){
						if(rs.getInt("CNT") == 0) {
							rs.close();
							return shouhinCd;
						}
					}
					rs.close();

					curCnt = MDWareSeq.nextValString("m_syohin_gro", byNo);
					curCnt = mst000401_LogicBean.formatZero(curCnt, 8);
					if(startVal.equals(curCnt)){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
						return null;
					}
					shouhinCd = "0400".concat(curCnt);
					checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
					shouhinCd = shouhinCd.concat(checkDegitCd);
				}
//				↑↑2007.01.26 H.Yamamoto カスタマイズ修正↑↑
			}
			
			// 商品コードを戻る
			return shouhinCd;
		
		} catch (SQLException sqle) {
			rs.close();
			throw sqle;
		} catch (Exception e) {
			rs.close();
			throw e;
		}
	}

	/**
	 * 自動採番処理(バッチ用)
	 * @param saibanId 採番ID
	 * @param bySikibetuNo  バイヤー識別NO
	 * @param byNo    バイヤーNO
	 * @param db        DataBase
	 * @return String  商品コード
	 * @throws Exception 
	 */
	public static String addSaibanKeta(String saibanId, String syohinCd, String byNo, DataBase db) throws Exception {

		// ｓｑｌ文
		String sql = "";
		// ｓｑｌ文
//		String syouhinCd = "";
		// 状態フラグ
		String jyotaiKanriFg = null;
//		↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
		// 予約バイヤーNo.
		String reserveByNo = null;
//		↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
		// 削除フラグ
		String deleteFg = null;
		// 検索データ
		ResultSet rs = null;

		mst001501_SaibanDM saiban = new mst001501_SaibanDM();

		try{
			// 実用の場合
			if("m_syohin_a07".equals(saibanId)){
				
				sql = saiban.getSaibanA07JyotaiSql(syohinCd.substring(0,6));
				rs = db.executeQuery(sql);
				if(rs.next()){
					jyotaiKanriFg = rs.getString("jyotai_kanri_fg");
//					↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
					reserveByNo = rs.getString("by_no");
//					↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
					deleteFg = rs.getString("delete_fg");
					if(deleteFg != null && deleteFg.trim().equals("1")){
						rs.close();
						sql = saiban.getSaibanA07DelSql(syohinCd.substring(0,6));
						db.executeUpdate(sql);
						// syouhinCd = jitsuyouSaibanInsert(syohinCd.substring(0,2), syohinCd.substring(2,6), byNo, syohinCd.substring(6), byNo, db);
						jitsuyouSaibanInsert(syohinCd.substring(0,2), syohinCd.substring(2,6), byNo, syohinCd.substring(6), byNo, db);
						return syohinCd;
					} else {
						rs.close();
//						↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
						// 別バイヤーに予約済
						if(jyotaiKanriFg != null && jyotaiKanriFg.trim().equals("5")){
//							↓↓2006.12.14 H.Yamamoto カスタマイズ修正↓↓
//							if(reserveByNo != null && !reserveByNo.trim().equals(byNo)){
							if(reserveByNo != null && !reserveByNo.equals(byNo)){
//							↑↑2006.12.14 H.Yamamoto カスタマイズ修正↑↑
								return null;
							}
						}
//						↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
						sql = saiban.getSaibanA07JyotaiUpdSql(syohinCd.substring(0,6), byNo);
						db.executeUpdate(sql);
						return syohinCd;
					}
				} else {
					rs.close();
					//syouhinCd = jitsuyouSaibanInsert(syohinCd.substring(0,2), syohinCd.substring(2,6), byNo, syohinCd.substring(6), byNo, db);
					jitsuyouSaibanInsert(syohinCd.substring(0,2), syohinCd.substring(2,6), byNo, syohinCd.substring(6), byNo, db);
					return syohinCd;
				}

			// タグの場合
			} else if( "m_syohin_a08".equals(saibanId)){
				
				sql = saiban.getSaibanA08JyotaiSql(syohinCd.substring(0,7));
				rs = db.executeQuery(sql);
				if(rs.next()){
					jyotaiKanriFg = rs.getString("jyotai_kanri_fg");
//					↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
					reserveByNo = rs.getString("by_no");
//					↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
					deleteFg = rs.getString("delete_fg");
					if(deleteFg != null && deleteFg.trim().equals("1")){
						rs.close();
						sql = saiban.getSaibanA08DelSql(syohinCd.substring(0,7));
						db.executeUpdate(sql);
//						syouhinCd = tagSaibanInsert(syohinCd.substring(0,7), syohinCd.substring(7), byNo, byNo, db);
						tagSaibanInsert(syohinCd.substring(0,7), syohinCd.substring(7), byNo, byNo, db);
						return syohinCd;
					} else {
						rs.close();
//						↓↓2006.10.19 H.Yamamoto カスタマイズ修正↓↓
						// 別バイヤーに予約済
						if(jyotaiKanriFg != null && jyotaiKanriFg.trim().equals("5")){
							// ===BEGIN=== Modify by kou 2006/11/07
							//if(reserveByNo != null && !reserveByNo.trim().equals(byNo)){
							if(reserveByNo != null && !reserveByNo.equals(byNo)){
							// ===END=== Modify by kou 2006/11/07
								return null;
							}
						}
//						↑↑2006.10.19 H.Yamamoto カスタマイズ修正↑↑
						sql = saiban.getSaibanA08JyotaiUpdSql(syohinCd.substring(0,7), byNo);
						db.executeUpdate(sql);
						return syohinCd;
					}
				} else {
					rs.close();
					// syouhinCd = tagSaibanInsert(syohinCd.substring(0,7), syohinCd.substring(7), byNo, byNo, db);
					tagSaibanInsert(syohinCd.substring(0,7), syohinCd.substring(7), byNo, byNo, db);
					return syohinCd;
				}

			// デイリー、グロ/バラの場合は何もしない
			} else if("m_syohin_fre".equals(saibanId) || "m_syohin_gro".equals(saibanId)){	
				return syohinCd;
			} else {
				return null;
			}
		
		} catch (SQLException sqle) {
			rs.close();
			throw sqle;
		} catch (Exception e) {
			rs.close();
			throw e;
		}
	}

	/**
	 * 自動採番処理(画面用)
	 * @param saibanId 採番ID
	 * @param bySikibetuNo  バイヤー識別NO
	 * @param byNo     バイヤーNO
	 * 	@param userId   userId
	 * @param db        DataBase
	 * @return String  商品コード
	 * @throws Exception 
	 */
	public static String getSaibanKeta(String saibanId, String bySikibetuNo, String byNo, String userId, DataBase db) throws Exception {

		// 商品コード
		String shouhinCd = "";
		// ｓｑｌ文
		String sql = "";
		// 現在値
		String curCnt = "";
		// デジット
		String checkDegitCd = "";
		// 採番マスタのデータ件数
		ResultSet rsCount = null;
		// 検索データ
		ResultSet rs = null;
		// 検索データの件数
		String rCount = "";
		mst001501_SaibanDM saiban = new mst001501_SaibanDM();

		try{
			// 実用の場合
			if("m_syohin_a07".equals(saibanId)){
				// 連番コードの取得
				sql = "";
				sql = saiban.getSaibanA07Sql(bySikibetuNo);
				rs = db.executeQuery(sql);
				
				// 検索データの連番コードを取る
				if(rs.next()){
					if(rs.getString("by_sikibetu_no").equals(bySikibetuNo)){
						curCnt = rs.getString("renban_cd");
					}else{
						curCnt = "0000";
					}
				}else{
					curCnt = "0000";
				}
				rs.close();
				
				// 採番マスタ（実用）に、連番コードが存在しない場合
				if(curCnt == null || "".equals(curCnt)){
					// 実用の採番登録処理
					curCnt = "0";

					// 現在値を計算して、第7桁の値を求める
					curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
					shouhinCd = bySikibetuNo.concat(curCnt);
					checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
					shouhinCd = shouhinCd.concat(checkDegitCd);
	
					// 採番マスタ(実用)の登録処理
					curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, userId, db);
				}else{
					if("9999".equals(curCnt)){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
					}else{
						//	現在値を計算して、第7桁の値を求める
						curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
						curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
						shouhinCd = bySikibetuNo.concat(curCnt);
						checkDegitCd = mst001401_CheckDegitBean.getModulus9(shouhinCd, 6);
						shouhinCd = shouhinCd.concat(checkDegitCd);
						
						// 採番マスタ(実用)の登録処理
						curCnt = jitsuyouSaibanInsert(bySikibetuNo, curCnt, byNo, checkDegitCd, userId, db);
					}
				}

			// タグの場合
			}else if( "m_syohin_a08".equals(saibanId)){
				// 連番コードの取得
				sql = "";
				sql = saiban.getSaibanA08Sql();
				rs = db.executeQuery(sql);

				// 検索データの商品コードを取る
				while(rs.next()){
					curCnt = rs.getString("syohin_cd");
				}
				rs.close();

				// 採番マスタ（タグ）に、商品コードが存在しない場合
				if(curCnt == null || "".equals(curCnt)){
					// タグの採番登録処理
					sql = "";
					curCnt = "1000000";

					// 現在値を計算して、第8桁の値を求める
					curCnt = mst000401_LogicBean.formatZero(curCnt, 7);
					checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
					shouhinCd = curCnt.concat(checkDegitCd);

					//	採番マスタ(タグ)の登録処理
					shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, userId, db);

				}else{
					curCnt = curCnt.substring(0, 7);

					if("9999999".equals(curCnt)){
						// エラーログ出力
						batchLog.getLog().info("採番処理ができません。");
					}else{
						//	現在値を計算して、第7桁の値を求める
						curCnt = String.valueOf(Integer.parseInt(curCnt) + 1);
						curCnt = mst000401_LogicBean.formatZero(curCnt, 7);
						checkDegitCd = mst001401_CheckDegitBean.getModulus9(curCnt, 7);
						shouhinCd = curCnt.concat(checkDegitCd);
					
						// 採番マスタ(タグ)の登録処理
						shouhinCd = tagSaibanInsert(curCnt, checkDegitCd, byNo, userId, db);

					}
				}

			// デイリー、グロ/バラの場合
			}else if("m_syohin_fre".equals(saibanId) || "m_syohin_gro".equals(saibanId)){		
				// 採番マスタのデータ件数の取得
				sql = saiban.getSaibanCntSql(saibanId);
				rsCount = db.executeQuery(sql);

				// 検索データの件数を取る
				while(rsCount.next()){
					rCount = rsCount.getString("count");
				}
	
				if ("0".equals(rCount)) {

					// 採番マスタ登録処理
					curCnt = "10000000";
					sql = "";
					sql = saiban.getSaibanInsert(saibanId, curCnt, userId);
					db.executeUpdate(sql);
	
					// 現在値を計算して、第8桁の値を求める
					shouhinCd = "0400".concat(curCnt);
					checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
					shouhinCd = shouhinCd.concat(checkDegitCd);
		
				} else {
					// 採番マスタの検索処理
					sql = "";
					sql = saiban.getSaibanSql(saibanId);
					rs = db.executeQuery(sql);
					
					// 検索の現在値を取る
					while(rs.next()){
						curCnt = rs.getString("cur_cnt");
					}
					rs.close();
					
					// 現在値が最大値の場合
					if("99999999".equals(curCnt)){
						batchLog.getLog().info("採番処理ができません。");
	
					}else{	
						// 現在値+1
						curCnt = String.valueOf(Long.parseLong(curCnt) + 1);
						curCnt = mst000401_LogicBean.formatZero(curCnt, 8);
		
						// 採番マスタの更新処理
						sql = "";
						sql = saiban.getSaibanUpdate(saibanId, curCnt, byNo);
						db.executeUpdate(sql);

						// 現在値を作成する
						shouhinCd = mst000401_LogicBean.formatZero(curCnt, 8);
						shouhinCd = "0400".concat(shouhinCd);
		
						// 現在値を計算して、第13桁の値を求める
						checkDegitCd = mst001401_CheckDegitBean.getModulus10(shouhinCd, 12);
						shouhinCd = shouhinCd.concat(checkDegitCd);

					}
				}
			}
			
			// 商品コードを戻る
			return shouhinCd;
		
		} catch (SQLException sqle) {
			rs.close();
			throw sqle;
		} catch (Exception e) {
			rs.close();
			throw e;
		}
	}
	
	/**
	 * 自動採番（タグ）の登録処理
	 * @param shouhinCd 商品コード
	 * @param buyNo  バイヤーNO
	 * @param userId  userId
	 * @return String  商品コード
	 * @throws SQLException 
	 */
	public static String tagSaibanInsert(String shouhinCd, String checkDegitCd, String buyNo, String userId, DataBase db) throws SQLException {
		// ｓｑｌ文
		String sql = "";
		mst001501_SaibanDM dm = new mst001501_SaibanDM();

		// 自動採番（タグ）の登録処理
		sql = dm.tagSaibanInsert(shouhinCd, checkDegitCd, buyNo, userId);
		db.executeUpdate(sql);
		shouhinCd = shouhinCd.concat(checkDegitCd);

		return shouhinCd;
	}

	/**
	 * 自動採番（実用）の登録処理
	 * @param bySikibetuNo バイヤー識別NO
	 * @param curCnt        現在値
	 * @param buyNo         バイヤーNO
	 * @param checkDegitCd   デジット
	 * @param userId             userId
	 * @return String             商品コード
	 * @throws SQLException 
	 */
	public static String jitsuyouSaibanInsert(String bySikibetuNo, String curCnt, String buyNo, String checkDegitCd, String userId, DataBase db) throws SQLException{
		// ｓｑｌ文
		String sql = "";
		mst001501_SaibanDM dm = new mst001501_SaibanDM();
		
		// 自動採番（実用）の登録処理
		sql = dm.jitsuyouSaibanInsert(curCnt, buyNo, bySikibetuNo, checkDegitCd, userId);
		db.executeUpdate(sql);
		
		return curCnt;
	}
//	↑↑2006.06.28 zhouzt カスタマイズ修正↑↑
	
	//	/**
	//	 * 採番（単品コード）取得[8桁商品]を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaiban8Keta(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		String	hinsyuCd		: 品種コード
	//	 * @param		String	tanpinCd		: 単品コード
	//	 * @param		String	saibanKanriKb	: 採番管理区分
	//	 * @param		String	userId			: ユーザーID
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	//	 * @return		String	retTanpinCd		: 単品コード(null:エラー)
	//	 */
	//	
	//	public static String getSaiban8Keta(String hinsyuCd, String tanpinCd , String saibanKanriKb ,
	//								 String userId, mst000101_DbmsBean db )	throws Exception,SQLException {
	//		String ret = null;
	//
	//		DecimalFormat strNum = new DecimalFormat("000");
	//		int cnt = 0;
	//		boolean flg = false;		
	//		for (cnt = 0 ; cnt < 15 ;cnt++){
	//			try{
	//				flg = true;
	//				ret = getSaiban8KetaProcess(hinsyuCd, tanpinCd , saibanKanriKb , userId, db );				
	////			} catch(Exception e) {
	////				db.rollback();
	////				flg = false;
	////				continue;
	////			}
	//			} catch (SQLException se) {
	//				if (se.getErrorCode() == ORA_00001) {
	//					flg = false;
	//					continue;
	//				} else {
	//					throw se;
	//				}
	//			} catch (Exception e) {
	//				throw e;
	//			}
	//				if (flg) {cnt = 99;} 
	//		}
	//		if(cnt < 99){		
	//			ret = null;
	//		}
	//		
	//		return ret;
	//			
	//	}
	//		
	//	/**
	//	 * 採番（単品コード）取得[8桁商品]を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaiban8Keta(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		String	hinsyuCd		: 品種コード
	//	 * @param		String	tanpinCd		: 単品コード
	//	 * @param		String	saibanKanriKb	: 採番管理区分
	//	 * @param		String	userId			: ユーザーID
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	//	 * @return		String	retTanpinCd		: 単品コード(null:エラー)
	//	 */
	//	public static String getSaiban8KetaProcess(String hinsyuCd, String tanpinCd , String saibanKanriKb ,
	//								 String userId, mst000101_DbmsBean db )	throws Exception,SQLException {
	//		
	//		//単品コード
	//		String retTanpinCd = null;
	//		int stTanpinCd = 0;
	//		int edTanpinCd = 0;
	//		//自動採番枠品種別マスタ		
	//		int tanpin[] = getSaibanWakuHinsyu( db, hinsyuCd, userId );//[0]開始コード,[1]終了コード
	//		if (!tanpinCd.equals("") ){ 
	//			//単品コードが入力されている場合
	//			if (tanpin[0] > Integer.parseInt(tanpinCd)  
	//				|| Integer.parseInt(tanpinCd) > tanpin[1] ) {
	//				retTanpinCd = null;
	//			} else {
	//				List whereList  = new ArrayList();
	//				whereList.add("	and ");				
	//				whereList.add("  ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.JIDOU_HON.getCode() + "' ");
	//				whereList.add("    or  ");
	//				whereList.add("    jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.SYUDOU.getCode() + "' ");
	//				whereList.add("    or  ");
	//				whereList.add("    ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.HAIBAN.getCode() + "' ");
	//				whereList.add(" 	 and ");
	//				whereList.add("     haiban_dt >= TO_CHAR(ADD_MONTHS(SYSDATE,-6) ,'YYYYMMDD') ");
	//				whereList.add(" 	and ");
	//				whereList.add("     delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "')) ");				
	//				if(getSaiban( db, "r_saiban_8keta", hinsyuCd, tanpinCd, whereList)){
	//					retTanpinCd = null;
	//				} else {
	//					retTanpinCd = tanpinCd;
	//				}
	//			}
	//		} else {
	//			retTanpinCd =  getTanpin( db, tanpin[0], tanpin[1], 200, mst000101_ConstDictionary.FUNCTION_PARAM_0, hinsyuCd );
	//		}
	//
	//		//自動採番マスタ(8桁)更新
	//		if(retTanpinCd != null){
	//			if(!getSaiban8KetaIns(db,hinsyuCd,retTanpinCd,saibanKanriKb,userId)){
	//				retTanpinCd = null;
	//			}
	//		}
	//		return retTanpinCd;
	//	}
	//	/**
	//	 * 採番（単品コード）取得[インストア]を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaibanInstore(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		String	tanpinCd		: 単品コード
	//	 * @param		String	saibanKanriKb	: 採番管理区分
	//	 * @param		String	userId			: ユーザーID
	//	 * @return		String	retTanpinCd	: 単品コード(null:エラー)
	//	 */
	//	public static String getSaibanInstore(String tanpinCd , String saibanKanriKb , 
	//											String userId, mst000101_DbmsBean db ) throws Exception,SQLException {
	//		String ret = null;
	//
	//		DecimalFormat strNum = new DecimalFormat("000");
	//		int cnt = 0;
	//		boolean flg = false;		
	//		for (cnt = 0 ; cnt < 15 ;cnt++){
	//			try{
	//				flg = true;
	//				ret = getSaibanInstoreProcess( tanpinCd , saibanKanriKb , userId, db );				
	////			} catch(Exception e) {
	////				db.rollback();
	////				flg = false;
	////				continue;
	////			}
	//			} catch (SQLException se) {
	//				if (se.getErrorCode() == ORA_00001) {
	//					flg = false;
	//					continue;
	//				} else {
	//					throw se;
	//				}
	//			} catch (Exception e) {
	//				throw e;
	//			}
	//				if (flg) {cnt = 99;} 
	//		}
	//		if(cnt < 99){		
	//			ret = null;
	//		}
	//		
	//		return ret;
	//	
	//	}
	//	/**
	//	 * 採番（単品コード）取得[インストア]を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaibanInstore(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		String	tanpinCd		: 単品コード
	//	 * @param		String	saibanKanriKb	: 採番管理区分
	//	 * @param		String	userId			: ユーザーID
	//	 * @return		String	retTanpinCd	: 単品コード(null:エラー)
	//	 */
	//	public static String getSaibanInstoreProcess(String tanpinCd , String saibanKanriKb , 
	//												String userId, mst000101_DbmsBean db ) throws Exception,SQLException {
	//		
	//		//単品コード
	//		String retTanpinCd = null;
	//		if (!tanpinCd.equals("") ){
	//			List whereList  = new ArrayList();
	//			whereList.add("	and ");			
	//			whereList.add("  ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.JIDOU_HON.getCode() + "' ");
	//			whereList.add("    or  ");
	//			whereList.add("    jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.SYUDOU.getCode() + "' ");
	//			whereList.add("    or  ");
	//			whereList.add("    ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.HAIBAN.getCode() + "' ");
	//			whereList.add(" 	 and ");
	//			whereList.add("     haiban_dt >= TO_CHAR(ADD_MONTHS(SYSDATE,-6) ,'YYYYMMDD') ");
	//			whereList.add(" 	and ");
	//			whereList.add("     delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "')) ");				
	//			if(getSaiban( db, "r_saiban_instore", "", tanpinCd, whereList)){
	//				retTanpinCd = null;
	//			} else {
	//				retTanpinCd = tanpinCd;
	//			}
	//			
	//		} else {
	//			retTanpinCd =  getTanpin( db, 0, 99999, 200, mst000101_ConstDictionary.FUNCTION_PARAM_1, "" );			
	//		}
	//
	//		//自動採番マスタ(インストア)更新
	//		if(retTanpinCd != null){
	//			if(!getSaibanInstoreIns(db,retTanpinCd,saibanKanriKb,userId)){
	//				retTanpinCd = null;
	//			}
	//		}
	//		return retTanpinCd;
	//	}
	//	
	//	/**
	//	 * 自動採番枠品種別マスタより開始単品コード/終了単品コードの取得を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaibanWakuHinsyu(mst000101_DbmsBean db, String hinsyuCd, String userId) -&gt; int[]<br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス
	//	 * @param		String hinsyuCd			: 品種コード
	//	 * @param		String userId			: ユーザーID 
	//	 * @return		int[]			    	: [0]開始単品コード,[1]終了単品コード
	//	 */
	//	public static int[] getSaibanWakuHinsyu( mst000101_DbmsBean db, String hinsyuCd, String userId ) throws Exception,SQLException {
	//		//採番取得のDMモジュール
	//		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
	//		//抽出結果(ResultSet)
	//		ResultSet rset = null;
	//		//return   
	//		int[] ret = {0,999};
	//		try {
	//			
	//			rset = db.executeQuery(saibanDM.getSaibanWakuHinsyuSelectSql(hinsyuCd));//抽出結果(ResultSet)
	//			if (rset.next()){
	//				if(mst000401_LogicBean.chkNullString(rset.getString("delete_fg")).equals(mst000801_DelFlagDictionary.INAI.getCode())){
	//					ret[0] =  Integer.parseInt(mst000401_LogicBean.chkNullString(rset.getString("start_tanpin_cd")));
	//					ret[1] =  Integer.parseInt(mst000401_LogicBean.chkNullString(rset.getString("end_tanpin_cd")));
	//				} else {
	//					ret[0] =  0;
	//					ret[1] =  0;
	//				}
	//			} else {
	//				db.executeUpdate(saibanDM.getSaibanWakuHinsyuInsertSql(hinsyuCd,ret[0],ret[1],db.getDBSysdate(),userId));				
	////				db.commit();				
	//			}
	//		
	//		} catch(SQLException sqle) {					
	//			throw sqle;
	//		} catch(Exception e) {					
	//			throw e;
	//		}
	//		return ret;			
	//	}
	//			
	//	/**
	//	 * 指定単品コードのデータの取得を行う
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaiban(mst000101_DbmsBean db, String hinsyuCd, String tanpinCd) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス
	//	 * @param		String tbl				: 対象テーブル名称 
	//	 * @param		String hinsyuCd			: 品種コード
	//	 * @param		String tanpinCd			: 単品コード
	//	 * @param		List whereList			: 抽出条件 
	//	 * @return		boolean			    	: 存在判定
	//	 */
	//	public static boolean getSaiban( mst000101_DbmsBean db, String tableNa, String hinsyuCd, String tanpinCd, List whereList )
	//															throws Exception,SQLException {
	//		//採番取得のDMモジュール
	//		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
	//		//抽出結果(ResultSet)
	//		ResultSet rset = null;
	//		//return   
	//		boolean ret = false;							
	//		try {
	//			rset = db.executeQuery(saibanDM.getSaibanSelectSql(tableNa,hinsyuCd,tanpinCd,whereList));//抽出結果(ResultSet)
	//			if (rset.next()){
	//				ret = true;
	//			}
	//		
	//		} catch(SQLException sqle) {					
	//			throw sqle;
	//		} catch(Exception e) {					
	//			throw e;
	//		}
	//		return ret;			
	//	}
	//			
	//	/**
	//	 * 開始単品コード/終了単品コードの件数を取得し空番の有無を判定する
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaibanCnt(String tableNa, String columnNa, List whereList, String yukoDt) -&gt; boolean<br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス
	//	 * @param		String tbl				: 対象テーブル名称
	//	 * @param		String clm				: 単品カラム名称
	//	 * @param		String kanriclm			: 状態管理区分カラム名称
	//	 * @param		String hinsyuCd			: 品種コード
	//	 * @param		String stTanpinCd		: 開始単品コード
	//	 * @param		String edTanpinCd		: 終了単品コード
	//	 * @param		List whereList			: 抽出条件   
	//	 * @return		boolean			    	: 存在判定
	//	 */
	//	public static boolean getSaibanCnt(  mst000101_DbmsBean db, String tbl, String clm, String hinsyuCd, String stTanpinCd, 
	//											String edTanpinCd, List whereList)	throws Exception,SQLException {
	//		//採番取得のDMモジュール
	//		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
	//		//抽出結果(ResultSet)
	//		ResultSet rset = null;
	//		//return   
	//		boolean ret = false;						
	//		try {
	//			rset = db.executeQuery(saibanDM.getSaibanCntSelectSql(tbl,clm,hinsyuCd,stTanpinCd,edTanpinCd,whereList));//抽出結果(ResultSet)
	//			if (rset.next()){
	//				int cnt = Integer.parseInt((String)rset.getString("cnt"));
	//				if(Integer.parseInt(edTanpinCd) - Integer.parseInt(stTanpinCd) + 1 == cnt ){
	//					//空番なし
	//					ret = true;
	//				}
	//			}
	//		
	//		} catch(SQLException sqle) {					
	//			throw sqle;
	//		} catch(Exception e) {					
	//			throw e;
	//		}
	//		return ret;			
	//	}		
	//
	//	
	//	/**
	//	 * 自動採番マスタ(8桁商品)に追加
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaiban8KetaIns(mst000101_DbmsBean db, String hinsyuCd, String tanpinCd,String kanriKb,String userId) <br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス
	//	 * @param		String hinsyuCd			: 品種コード
	//	 * @param		String tanpinCd			: 単品コード
	//	 * @param		String kanriKb			: 状態管理区分  
	//	 * @param		String userId			: ユーザーID 
	//	 */
	//	public static boolean getSaiban8KetaIns( mst000101_DbmsBean db, String hinsyuCd, 
	//														String tanpinCd,String kanriKb,String userId) throws Exception,SQLException {
	//		//採番取得のDMモジュール
	//		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
	//		//抽出結果(ResultSet)
	//		ResultSet rset = null;   
	//		boolean ret = true;
	//		List whereList  = new ArrayList();
	//		
	//		try {
	//			rset = db.executeQuery(saibanDM.getSaibanCntSelectSql("r_saiban_8keta","tanpin_cd",hinsyuCd,tanpinCd,tanpinCd,whereList));//抽出結果(ResultSet)
	//			if (rset.next()){
	//				int cnt = Integer.parseInt((String)rset.getString("cnt"));
	//				if(cnt == 0){
	//					db.executeUpdate(saibanDM.getSaiban8KetaInsertSql(hinsyuCd,tanpinCd,kanriKb,mst001401_CheckDegitBean.getModulus11(hinsyuCd + tanpinCd,0),db.getDBSysdate(),userId));
	//				} else {
	//					db.executeUpdate(saibanDM.getSaibanUpdateSql("r_saiban_8keta",hinsyuCd,tanpinCd,kanriKb,db.getDBSysdate(),userId));
	//				}
	//			} else {
	//				db.executeUpdate(saibanDM.getSaiban8KetaInsertSql(hinsyuCd,tanpinCd,kanriKb,mst001401_CheckDegitBean.getModulus11(hinsyuCd + tanpinCd,0),db.getDBSysdate(),userId));
	//			}
	//		} catch(SQLException sqle) {
	//			throw sqle;
	//		} catch(Exception e) {
	//			throw e;
	//		}
	//		return ret;
	//	}
	//	
	//	/**
	//	 * 自動採番マスタ(インストア)に追加
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getSaiban8KetaIns(mst000101_DbmsBean db, String tanpinCd,String kanriKb,String userId) <br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db	: dbインスタンス
	//	 * @param		String tanpinCd			: 単品コード
	//	 * @param		String kanriKb			: 状態管理区分  
	//	 * @param		String userId			: ユーザーID 
	//	 */
	//	public static boolean getSaibanInstoreIns( mst000101_DbmsBean db, String tanpinCd,String kanriKb,String userId) throws Exception,SQLException {
	//		//採番取得のDMモジュール
	//		mst001501_SaibanDM saibanDM = new mst001501_SaibanDM();
	//		//抽出結果(ResultSet)
	//		ResultSet rset = null;
	//		boolean ret = true;
	//		List whereList  = new ArrayList();
	//		try {
	//			rset = db.executeQuery(saibanDM.getSaibanCntSelectSql("r_saiban_instore","tanpin_cd","",tanpinCd,tanpinCd,whereList));//抽出結果(ResultSet)
	//			if (rset.next()){
	//				int cnt = Integer.parseInt((String)rset.getString("cnt"));
	//				if(cnt == 0){
	//					db.executeUpdate(saibanDM.getSaibanInstoreInsertSql(tanpinCd,kanriKb,mst001401_CheckDegitBean.getModulus10("02" + tanpinCd + "00000",12),db.getDBSysdate(),userId));
	//				} else {
	//					db.executeUpdate(saibanDM.getSaibanUpdateSql("r_saiban_instore","",tanpinCd,kanriKb,db.getDBSysdate(),userId));
	//				}
	//			} else {
	//				db.executeUpdate(saibanDM.getSaibanInstoreInsertSql(tanpinCd,kanriKb,mst001401_CheckDegitBean.getModulus10("02" + tanpinCd + "00000",12),db.getDBSysdate(),userId));
	//			}
	//		} catch(SQLException sqle) {
	//			ret = false;							
	//			throw sqle;
	//		} catch(Exception e) {
	//			ret = false;					
	//			throw e;
	//		}
	//		return ret; 
	//	}
	//	/**
	//	 * 単品コードの取得
	//	 * <br>
	//	 * Ex)<br>
	//	 * mst001501_SaibanBean.getTanpin(mst000101_DbmsBean db, int startCd, int endCd, int syoriCnt ,String flg,	String hinsyuCd ) -&gt; String<br>
	//	 * <br>
	//	 * @param		mst000101_DbmsBean db		: dbインスタンス
	// 	 * @param		int startCd					: 開始単品コード
	// 	 * @param		int endCd					: 終了単品コード
	// 	 * @param		int syoriCnt				: 一度に処理する件数 
	// 	 * @param		String flg					: 0:８桁商品,1:インストア
	// 	 * @param		String hinsyuCd				: 品種コード	 
	//	 * @return		String	retTanpinCd			: 単品コード(null:エラー)
	//	 */
	//	public static String getTanpin( mst000101_DbmsBean db, int startCd, int endCd, int syoriCnt ,String flg,	String hinsyuCd  ) throws Exception,SQLException {
	//		
	//		//単品コード
	//		String retTanpinCd = null;
	//		if(!(startCd == 0 && endCd == 0)){
	//			int stTanpinCd = startCd;			    //開始単品コード
	//			int edTanpinCd = startCd + syoriCnt;	//終了単品コード
	//			if(edTanpinCd > endCd){
	//				edTanpinCd = endCd;
	//			}
	//			//8桁商品
	//			String	tableNa  	  = "r_saiban_8keta";//テーブル名称
	//			String	tanpinCol 	  = "tanpin_cd"; 	 //単品コードのカラム名称
	//			DecimalFormat strNum = new DecimalFormat("000");
	//			if(flg.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
	//				//インストア
	//				tableNa  	  = "r_saiban_instore";
	//				tanpinCol 	  = "tanpin_cd";
	//				strNum = new DecimalFormat("00000");			
	//			}
	//			//抽出条件
	//			List whereList  = new ArrayList();
	//			whereList.add("	and ");		
	//			whereList.add("  ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.JIDOU_HON.getCode() + "' ");
	//			whereList.add("    or  ");
	//			whereList.add("    jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.SYUDOU.getCode() + "' ");
	//			whereList.add("    or  ");
	//			//2005.06.22 修正 start 自動採番のときは仮採番されている番号も対象外
	//			whereList.add("    jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.JIDOU_KARI.getCode() + "' ");
	//			whereList.add("    or  ");
	//			whereList.add("    jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.SYUDOU_KARI.getCode() + "' ");
	//			whereList.add("    or  ");
	//			//2005.06.22 修正 end
	//			whereList.add("    ( jyotai_kanri_kb = '" + mst004401_SaibanKanriKbDictionary.HAIBAN.getCode() + "' ");
	//			whereList.add(" 	 and ");
	//			whereList.add("     haiban_dt >= TO_CHAR(ADD_MONTHS(SYSDATE,-6) ,'YYYYMMDD') ");
	//			whereList.add(" 	and ");
	//			whereList.add("     delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "')) ");
	//			
	//			for(String endSw = "";endSw.equals("");){
	//				if(edTanpinCd > endCd){
	//					endSw = "END";
	//				}
	////				if(!getSaibanCnt( db, tableNa, tanpinCol, hinsyuCd, 
	////									Integer.toString(stTanpinCd),Integer.toString(edTanpinCd),whereList)){
	//				if(!getSaibanCnt( db, tableNa, tanpinCol, hinsyuCd, 
	//				strNum.format(stTanpinCd),strNum.format(edTanpinCd),whereList)){
	//					//空番あり
	//					for( int i = 0 ;stTanpinCd + i <= edTanpinCd && endSw.equals("") ;i++){
	//						if(!getSaiban( db, tableNa, hinsyuCd, strNum.format(stTanpinCd + i) ,whereList )){
	//							retTanpinCd = strNum.format(stTanpinCd + i);
	//							endSw = "END";
	//						}
	//					}
	//				} else {
	//					//空番なし
	//					edTanpinCd = edTanpinCd + syoriCnt;
	//					if(stTanpinCd == 0){
	//						stTanpinCd = stTanpinCd + syoriCnt + 1;
	//					} else {
	//						stTanpinCd = stTanpinCd + syoriCnt;
	//					}
	//				}
	//			}
	//		}			
	//		return retTanpinCd;
	//		
	//	}
	

	/**
	 * DictionaryControlマスタより商品コード頭3桁の取得を行う
	 * <br>
	 * @param		String bunrui1Cd		: 分類１コード
	 * @param		String syohinCd			: 商品コード
	 * @return		String		    		: 採番コード
	 */
	public static String getSaibanCd(String bunrui1Cd, String syohinCd) throws Exception {

		String returnCd = null;
		
		String chkCd = "";
		
		try {
			
			//分類１コードと商品コード頭3桁の組み合わせMapを取得する
			Map saibanMap = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.SAIBAN_CD);
			
			//分類1コード+商品コード頭3桁が、組み合わせMapに存在するかチェック
			if (syohinCd != null && syohinCd.length() >=3) {
				
				chkCd = bunrui1Cd.concat(syohinCd.substring(0,3));
				
				//存在すれば、Mapの値(商品コード頭3桁)を返す
				if(saibanMap.containsKey(chkCd)){
					returnCd = (String)saibanMap.get(chkCd);
					return returnCd;
				}
			}

			//分類1コード+商品コード頭2桁が、組み合わせMapに存在するかチェック
			if (syohinCd != null && syohinCd.length() >=2) {
				chkCd = bunrui1Cd.concat(syohinCd.substring(0,2));

				if(saibanMap.containsKey(chkCd)){
					returnCd = (String)saibanMap.get(chkCd);
				}
			}

			return returnCd;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 自動採番処理
	 * @param saibanId 採番ID
	 * @param bunrui1Cd  分類１コード
	 * @param syohinCd  商品コード
	 * @param yukoDt  有効日
	 * @param masterDt  マスタ管理日付
	 * @param byNo    バイヤーNO
	 * @param db        DataBase
	 * @return String  商品コード
	 * @throws Exception 
	 */
//	public static String getSaibanKeta2(String saibanId, String bunrui1Cd, String syohinCd, String yukoDt, String masterDt, String byNo, DataBase db) throws Exception {
	public static String getSaibanKeta2(String saibanId, String bunrui1Cd, String syohinCd, String yukoDt, String masterDt, String byNo, MasterDataBase db) throws Exception {

		// 商品コード
		String saibanSyohinCd = null;
		// ｓｑｌ文
		String sql = "";
		// 現在値
		String curCnt = "";
		// デジット
		String checkDegitCd = "";
		// 検索データ
		ResultSet rs = null;

		mst001501_SaibanDM saiban = new mst001501_SaibanDM();

		try{
			// 実用の場合
			if("m_syohin_a07".equals(saibanId)){
				//マミーマートではグロ/バラ固定のため、処理せず

			// タグの場合
			}else if( "m_syohin_a08".equals(saibanId)){
				//マミーマートではグロ/バラ固定のため、処理せず
				
			// デイリー、グロ/バラの場合
			}else if("m_syohin_fre".equals(saibanId) || "m_syohin_gro".equals(saibanId)){
				
				//頭３桁を取得
				String headStr = getSaibanCd(bunrui1Cd.trim(), syohinCd.trim());
				if( headStr==null || headStr.equals("")){
					return null;
				}
				
				//取得した頭３桁と、入力された商品コードの頭３桁が異なっていたらnullを返す
				if(syohinCd.length() >= 3){
					if(!syohinCd.substring(0,3).equals(headStr)){
						return null;
					}
				}
				
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (S)
				// 部門コード
				String daibunrui2Cd = "";
				// 登録商品の部門コードを取得
				sql = saiban.getBumonCdSel(bunrui1Cd.trim());
				rs = db.executeQuery(sql);
				
				if(rs.next()){
					daibunrui2Cd = rs.getString("daibunrui2_cd");
				}
				db.closeResultSet(rs);
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (E)
				
				//採番
				String startVal = MDWareSeq.nextValString("m_syohin_".concat(headStr), byNo);
				
				// 原材料（仕入）の自動採番コードの場合
				if("20".equals(syohinCd.trim().substring(0,2))){
					startVal = mst000401_LogicBean.formatZero(startVal, 9);
					curCnt = startVal;
					//チェックデジット値を設定
					String tempSyohinCd = headStr.concat(curCnt);
					checkDegitCd = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
					saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					
				// 販売・仕入＝販売の自動採番コードの場合
				} else if("04".equals(syohinCd.trim().substring(0,2))){
					startVal = mst000401_LogicBean.formatZero(startVal, 4);
					curCnt = startVal;
					String tempSyohinCd = headStr.concat(curCnt).concat("00000");

					//チェックデジット値を設定
					checkDegitCd = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
					saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					
				} else {
					startVal = mst000401_LogicBean.formatZero(startVal, 4);
					curCnt = startVal;
					saibanSyohinCd = headStr.concat(curCnt).concat("000000");
				}

//↓↓2011.03.02 ADD T.Urano
				// 計量器用部門コードを取得する（02商品のみ）
				List lstBumonCd = new ArrayList();
				if ("02".equals(saibanSyohinCd.substring(0, 2))) {
					Map mapKeiryoBumonCd = ResorceUtil.getInstance().getPropertieMap(mst000101_ConstDictionary.MASTER_IF_KEIRYOKI_BUMON_CD);	
					String keiryokiBumonCd = (String)mapKeiryoBumonCd.get(bunrui1Cd.trim());

					if (keiryokiBumonCd != null && keiryokiBumonCd.trim().length() > 0) {
						Iterator it = mapKeiryoBumonCd.keySet().iterator();
				        while (it.hasNext()) {
				            Object objKey = it.next();
				            if (keiryokiBumonCd.equals((String)mapKeiryoBumonCd.get(objKey))) {
				            	lstBumonCd.add( (String)objKey );
				            }
				        }
					}
				}
//↑↑2011.03.02 ADD T.Urano
				
				while(true){

//↓↓2011.03.02 MOD T.Urano
//					sql = saiban.getSyohinCntSql(saibanSyohinCd, yukoDt, masterDt);
//					rs = db.executeQuery(sql);
//					if(rs.next()){
//						if(rs.getInt("CNT") == 0) {
////							rs.close();
//							db.closeResultSet(rs);
//							return saibanSyohinCd;
//						}
//					}
////					rs.close();
//					db.closeResultSet(rs);

					int syohinCnt = 0;
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (S)
					int syohinCnt2 = 0;
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (E)
					int yobidasiCnt = 0;

					//商品コード存在チェック
					sql = saiban.getSyohinCntSql(saibanSyohinCd, yukoDt, masterDt);
					rs = db.executeQuery(sql);
					if(rs.next()){
						syohinCnt = rs.getInt("CNT");
					}
//					rs.close();
					db.closeResultSet(rs);
					
					//呼出NOの存在チェック(計量器IF部門、02商品のみ）
					if (syohinCnt == 0 && lstBumonCd.size() > 0) {
					
						//呼出No存在チェック
						sql = saiban.getKeiryokiSyohinYobidasiCntSql(lstBumonCd, saibanSyohinCd.substring(3,7), yukoDt, masterDt);
						rs = db.executeQuery(sql);
						if(rs.next()){
							yobidasiCnt = rs.getInt("CNT");
						}
//						rs.close();
						db.closeResultSet(rs);
					}
					
//2012.09.27 Y.Imai Add 【MM00158】青果部門内での重複チェック追加不具合対応 (S)
					// 頭02と04から始まる商品のみチェック
					if ("02".equals(saibanSyohinCd.substring(0, 2)) || "04".equals(saibanSyohinCd.substring(0, 2))) {
//2012.09.27 Y.Imai Add 【MM00158】青果部門内での重複チェック追加不具合対応 (E)
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (S)
						//青果部門内で呼出№の存在チェック（青果部門:03の計量器・ハローラベルの場合のみ）
						if (syohinCnt == 0 && daibunrui2Cd.trim().equals("03")) {
	
							//呼出No存在チェック
							sql = saiban.getKeiryokiSyohinYobidasiCntSql2(saibanSyohinCd.substring(3,7), yukoDt, masterDt, daibunrui2Cd.trim());
							rs = db.executeQuery(sql);
							if(rs.next()){
								yobidasiCnt = rs.getInt("CNT");
							}
							db.closeResultSet(rs);
						}
						
						//商品コード4～7桁の存在チェック(計量器IF部門の02商品 または 青果部門の02、04商品のみ）
						if (syohinCnt == 0 && yobidasiCnt == 0 && (lstBumonCd.size() > 0 || daibunrui2Cd.trim().equals("03"))) {
						
							//商品コード4～7桁存在チェック
							sql = saiban.getSyohinCdKetaCntSql(saibanSyohinCd.substring(3,7), yukoDt, masterDt, daibunrui2Cd.trim(), lstBumonCd);
							rs = db.executeQuery(sql);
							if(rs.next()){
								syohinCnt2 = rs.getInt("CNT");
							}
							db.closeResultSet(rs);
						}
//2012.09.27 Y.Imai Add 【MM00158】青果部門内での重複チェック追加不具合対応 (S)
					}
//2012.09.27 Y.Imai Add 【MM00158】青果部門内での重複チェック追加不具合対応 (E)
	
	//					//存在しない場合は、自動採番した商品コードを返却
	//					if (syohinCnt == 0 && yobidasiCnt == 0) {
	//						return saibanSyohinCd;
	//					}
						
						//存在しない場合は、自動採番した商品コードを返却
						if (syohinCnt == 0 && yobidasiCnt == 0 && syohinCnt2 == 0) {
							return saibanSyohinCd;
						}
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (E)

//↑↑2011.03.02 MOD T.Urano
					
					curCnt = MDWareSeq.nextValString("m_syohin_".concat(headStr), byNo);
					if("20".equals(syohinCd.trim().substring(0,2))){
						curCnt = mst000401_LogicBean.formatZero(curCnt, 9);
					} else {
						curCnt = mst000401_LogicBean.formatZero(curCnt, 4);
					}
					if(startVal.equals(curCnt)){
						// エラーログ出力
						return null;
					}
					if("20".equals(syohinCd.trim().substring(0,2))){
						//チェックデジット値を設定
						String tempSyohinCd = headStr.concat(curCnt);
						checkDegitCd = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
						saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					}else if("04".equals(syohinCd.trim().substring(0,2))){
						//チェックデジット値を設定
						String tempSyohinCd = headStr.concat(curCnt).concat("00000");
						checkDegitCd = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
						saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					} else {
						saibanSyohinCd = headStr.concat(curCnt).concat("000000");
					}
				}

			}
			
			// 商品コードを戻す
			return saibanSyohinCd;
		
		} catch (SQLException sqle) {
//↓↓2011.03.03 MOD T.Urano
//			rs.close();
			db.closeResultSet(rs);
//↑↑2011.03.03 MOD T.Urano
			throw sqle;
		} catch (Exception e) {
//↓↓2011.03.03 MOD T.Urano
//			rs.close();
			db.closeResultSet(rs);
//↑↑2011.03.03 MOD T.Urano
			throw e;
		}
	}
	
//  2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(S)	
	/**
	 * 自動採番処理
	 * @param saibanId     採番ID
	 * @param bunrui1Cd    分類１コード
	 * @param syohinCd     商品コード
	 * @param siiresakiCd  仕入先コード
	 * @param yukoDt       有効日
	 * @param masterDt     マスタ管理日付
	 * @param byNo         バイヤーNO
	 * @param db           DataBase
	 * @return String      商品コード  
	 * @throws Exception 
	 */
	public static String getSaibanKetalaw(String saibanId, String bunrui1Cd, String siiresakiCd, String syohinCd, String yukoDt, String masterDt, String byNo, MasterDataBase db) throws Exception {
		
		ResultSet rs = null;
		
		try{
			
			String  saibanSyohinCd = ""; // 採番商品コード
			
			// 実用の場合
			if("m_syohin_a07".equals(saibanId)){
				//-------ランド様ではグロ/バラ固定のため、処理せず

			// タグの場合
			}else if( "m_syohin_a08".equals(saibanId)){
				//-------ランド様ではグロ/バラ固定のため、処理せず
				
			// デイリー、グロ/バラの場合
			}else if("m_syohin_fre".equals(saibanId) || "m_syohin_gro".equals(saibanId)){
				

				String  curCnt                = "";
				String  checkDegitCd          = "";
				String  startVal              = "";
				String  tempSyohinCd          = "";
				String  firstSaibanSyohinCd   = "";

				String sql = "";
				mst001501_SaibanDM saiban = new mst001501_SaibanDM();
				
				while(true){

					//*-----------------------------------------------------------------------------
					//  原材料（仕入）の自動採番コードの場合
					//    【構成】23(2桁) + 取引先コード(6桁) + 自動採番(4桁) + C/D(1桁)  
					//*-----------------------------------------------------------------------------
					if("23".equals(syohinCd.trim().substring(0,2))){
						
						//頭2桁＋仕入先を取得
						String headStr  = syohinCd.substring(0,2).concat(siiresakiCd.trim());

						//採番
						startVal     = MDWareSeq.nextValAutoString("syohin_".concat(headStr), byNo,"9999");
						startVal     = mst000401_LogicBean.formatZero(startVal, 4);
						curCnt       = startVal;
						tempSyohinCd = headStr.concat(curCnt); // 後ろゼロ詰めなし
						
					//*-----------------------------------------------------------------------------
					//  販売の自動採番コードの場合
					//    【構成】02(2桁) + DPT割当コード(1桁) + 自動採番(5桁) + ゼロ(4桁) + C/D(1桁)  
					//*-----------------------------------------------------------------------------
					} else if("02".equals(syohinCd.trim().substring(0,2))){
						
						//頭3桁を取得
						String headStr = getSaibanCd(bunrui1Cd.trim(), syohinCd.trim());
						if( headStr==null || headStr.equals("")){
							return null;
						}
						
						//取得した頭3桁と、入力された商品コードの頭3桁が異なっていたらnullを返す
						if(syohinCd.length() >= 3){
							if(!syohinCd.substring(0,3).equals(headStr)){
								return null;
							}
						}

						//採番
						startVal = MDWareSeq.nextValAutoString("syohin_".concat(headStr), byNo, "99999");
						startVal = mst000401_LogicBean.formatZero(startVal, 5);
						curCnt   = startVal;
						tempSyohinCd = headStr.concat(curCnt).concat("0000");
						
					}
					
					//チェックデジット値を設定
					checkDegitCd   = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
					saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					
					
					// 初回採番コード＝今回採番コードの場合、空きがない為nullを返す
					if(firstSaibanSyohinCd.equals(saibanSyohinCd)){
						return null;
					}
					// 初回採番コードに値をセット
					if(firstSaibanSyohinCd.equals("")){
						firstSaibanSyohinCd = saibanSyohinCd;
					}

					//*-------------------------
					// 商品コード存在チェック
					//*-------------------------
					int syohinCnt = 0;
					sql = saiban.getSyohinCntSql(saibanSyohinCd, yukoDt, masterDt);
					rs = db.executeQuery(sql);
					if(rs.next()){
						syohinCnt = rs.getInt("CNT");
					}
					rs.close();

					//存在しない場合は、自動採番した商品コードを返却
					if (syohinCnt == 0) {
						return saibanSyohinCd;
					}

				}

			// Add 2015.08.21 THAO.NTL (S)
			// FIVImartの場合
			} else if("fi_syohin".equals(saibanId)){


				String  curCnt                = "";
				String  checkDegitCd          = "";
				String  startVal              = "";
				String  tempSyohinCd          = "";
				String  firstSaibanSyohinCd   = "";

				String sql = "";
				mst001501_SaibanDM saiban = new mst001501_SaibanDM();

				while(true){
					//*-----------------------------------------------------------------------------
					//  不定貫販売コードの場合
					//    【構成】02(2桁) + DPT割当コード(1桁) + 自動採番(4桁) + ゼロ(6桁)
					//*-----------------------------------------------------------------------------
					// Mod 2016.01.11 Huy.NT 頭2桁「04」の自動採番を無くす (S)
					/* if ("02".equals(syohinCd.trim().substring(0,2)) || "04".equals(syohinCd.trim().substring(0,2))) { */
					if ("02".equals(syohinCd.trim().substring(0,2))) {
					// Mod 2016.01.11 Huy.NT 頭2桁「04」の自動採番を無くす (S)
						//頭3桁を取得
						String headStr = getSaibanCd(bunrui1Cd.trim(), syohinCd.trim());
						if( headStr==null || headStr.equals("")){
							return null;
						}

						//取得した頭3桁と、入力された商品コードの頭3桁が異なっていたらnullを返す
						if(syohinCd.length() >= 3){
							if(!syohinCd.substring(0,3).equals(headStr)){
								return null;
							}
						}

						//採番
						startVal = MDWareSeq.nextValAutoString("fi_syohin_".concat(syohinCd.trim().substring(0,2)).concat("_").concat(headStr.substring(2,3)), byNo, "9999");
						startVal = mst000401_LogicBean.formatZero(startVal, 4);
						curCnt   = startVal;
						tempSyohinCd = headStr.concat(curCnt).concat("000000");

					//*-----------------------------------------------------------------------------
					//  自社コードの場合
					//    【構成】20(2桁) + 自動採番(10桁) + C/D(1桁)
					//*-----------------------------------------------------------------------------
					} else if ("20".equals(syohinCd.trim().substring(0,2))) {

						//頭2桁
						String headStr  = syohinCd.substring(0,2);

						//採番
						startVal     = MDWareSeq.nextValAutoString("fi_syohin_20", byNo,"9999999999");
						startVal     = mst000401_LogicBean.formatZero(startVal, 10);
						curCnt       = startVal;
						tempSyohinCd = headStr.concat(curCnt);
					}

					//チェックデジット値を設定
					checkDegitCd   = mst001401_CheckDegitBean.getModulus10(tempSyohinCd, 12);
					saibanSyohinCd = tempSyohinCd.concat(checkDegitCd);
					// Mod 2016.01.11 Huy.NT 頭2桁「04」の自動採番を無くす (S)
					/* if ("02".equals(syohinCd.trim().substring(0,2)) || "04".equals(syohinCd.trim().substring(0,2))) { */
					if ("02".equals(syohinCd.trim().substring(0,2))) {
					// Mod 2016.01.11 Huy.NT 頭2桁「04」の自動採番を無くす (E)
						saibanSyohinCd = tempSyohinCd;
					}

					// 初回採番コード＝今回採番コードの場合、空きがない為nullを返す
					if(firstSaibanSyohinCd.equals(saibanSyohinCd)){
						return null;
					}
					// 初回採番コードに値をセット
					if(firstSaibanSyohinCd.equals("")){
						firstSaibanSyohinCd = saibanSyohinCd;
					}

					//*-------------------------
					// 商品コード存在チェック
					//*-------------------------
					int syohinCnt = 0;
					sql = saiban.getSyohinCntSql(saibanSyohinCd, yukoDt, masterDt);
					rs = db.executeQuery(sql);
					if(rs.next()){
						syohinCnt = rs.getInt("CNT");
					}
					rs.close();

					//存在しない場合は、自動採番した商品コードを返却
					if (syohinCnt == 0) {
						return saibanSyohinCd;
					}

				}

			}
			//Add 2015.08.21 THAO.NTL (E)
			
			// 商品コードを戻す
			return saibanSyohinCd;
		
		} catch (SQLException sqle) {
			db.closeResultSet(rs);
			throw sqle;
		} catch (Exception e) {
			db.closeResultSet(rs);
			throw e;
		// 2017/02/07 T.Arimoto #1174対応（S)
		} finally{
			db.closeResultSet(rs);
		// 2017/02/07 T.Arimoto #1174対応（E)
		}
		
	}
//  2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(E)	
}
