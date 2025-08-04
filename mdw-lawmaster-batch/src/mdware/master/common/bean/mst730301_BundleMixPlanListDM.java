/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売登録画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売登録画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/14)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM企画特売登録画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM企画特売登録画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/14)初版作成
 */
public class mst730301_BundleMixPlanListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private ArrayList tenpoItiran = null;	//店舗配列

	/**
	 * コンストラクタ
	 */
	public mst730301_BundleMixPlanListDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
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
		mst730301_BundleMixPlanListBean bean = new mst730301_BundleMixPlanListBean();
		bean.setKikakuTokubaiNoCd(rest.getString("kikaku_tokubaino_cd"));
		bean.setExistSyohin( rest.getString("exist_syohin"));
		bean.setNameNa( rest.getString("name_na"));
		bean.setKikakuStDt( rest.getString("kikaku_st_dt") );
		bean.setKikakuEdDt( rest.getString("kikaku_ed_dt"));
		bean.setTenpoCdl( rest.getString("tenpo_cdl") );
		bean.setTkanjiRnl( rest.getString("tkanji_rnl") );
		bean.setFlgl( rest.getString("flgl") );
		bean.setInsertTsl( rest.getString("insert_tsl") );
		bean.setInsertUserIdl( rest.getString("insert_user_idl") );
		bean.setUpdateTsl( rest.getString("update_tsl") );
		bean.setUpdateUserIdl( rest.getString("update_user_idl") );
		bean.setInsertTs( rest.getString("insert_ts"));
		bean.setInsertUserId( rest.getString("insert_user_id"));
		bean.setUpdateTs( rest.getString("update_ts"));
		bean.setUpdateUserId( rest.getString("update_user_id"));
		bean.setDeleteFg( rest.getString("delete_fg"));
		bean.setOldFlgl( rest.getString("flgl") );		
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT TBL.* , \n");
				
		sb.append("			--店舗コード生成 \n");
		//店舗コード生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("(select TENPO_CD from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "')||','||\n");
			} else {
				sb.append("(select TENPO_CD from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "') as tenpo_cdl,\n");
			}

		}

		sb.append("			--店舗名生成 \n");
		//店舗名生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("(select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "')||','||\n");
			} else {
				sb.append("(select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "') as tkanji_rnl,\n");
			}

		}
		sb.append("			--店舗存在フラグ \n");
		
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE 
						+ "' as col from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and t1.DELETE_FG = '0'),'0')||','||\n");
			} else {
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE 
						+ "' as col from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and t1.DELETE_FG = '0'),'0') as flgl,\n");
			}
		}

		sb.append("			--レコード登録日時 \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_TS from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " + 
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_TS from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_tsl,\n");
			}

		}

		sb.append("			--レコード登録者ID \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_USER_ID from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_USER_ID from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_user_idl,\n");
			}

		}

		sb.append("			--レコード更新日時 \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_TS from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_TS from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_tsl,\n");
			}

		}

		sb.append("			--レコード更新者ID \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_USER_ID from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_USER_ID from R_TENKIKAKUTOKUBAI t1 where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_user_idl,\n");
			}

		}

		sb.append("			--取扱商品有無 \n");
		//取扱商品有無
		sb.append("nvl((select distinct '" + mst000101_ConstDictionary.RECORD_EXISTENCE 
				//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//				+ "' as col from R_BM_KIKAKUTOKUBAI_RENKAN t1, R_BANDLEMIX_CODE t2, R_BANDLEMIX_SYOHIN t3 "
				+ "' as col from R_BM_KIKAKUTOKUBAI_RENKAN t1, R_BUNDLEMIX_CD t2, R_BUNDLEMIX_SYOHIN t3 "
				//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
				+ " where t1.KIKAKU_TOKUBAINO_CD = tbl.KIKAKU_TOKUBAINO_CD and " 
				+ " t1.BUNDLEMIX_CD = t2.BUNDLEMIX_CD and "
				+ " t2.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' and "
				+ " t2.BUNDLEMIX_CD = t3.BUNDLEMIX_CD and "
				+ " t3.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'),'0') as exist_syohin\n");


		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//		sb.append("FROM R_BANDLEMIX_KIKAKUTOKUBAI TBL\n");
		sb.append("FROM R_BUNDLEMIX_KIKAKUTOKUBAI TBL\n");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
		
		sb.append("WHERE TBL.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
		sb.append("AND TBL.KIKAKU_TOKUBAINO_CD >= '" + map.get("kikaku_tokubaino_cd") + "'\n");
		sb.append("ORDER BY TBL.KIKAKU_TOKUBAINO_CD ");

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
	/**
	 * 店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoItiran();　戻り値　店舗配列<br>
	 * <br>
	 * @return ArrayList 店舗配列
	 */
	public ArrayList getTenpoItiran() {
		return tenpoItiran;
	}

	/**
	 * 店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoItiran("店舗配列");<br>
	 * <br>
	 * @param list 店舗配列
	 */
	public void setTenpoItiran(ArrayList list) {
		tenpoItiran = list;
	}
}
