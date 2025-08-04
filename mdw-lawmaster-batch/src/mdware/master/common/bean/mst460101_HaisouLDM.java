/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/16)初版作成
 */
public class mst460101_HaisouLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private ArrayList tenpoItiran = null;//店舗配列
	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String haisosaki_cd4 = null;	//配送先コード4桁
	/**
	 * コンストラクタ
	 */
	public mst460101_HaisouLDM()
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
		mst460101_HaisouLBean bean = new mst460101_HaisouLBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setHaisosakiCd( rest.getString("haisosaki_cd") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setKanaNa( rest.getString("kana_na") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanaRn( rest.getString("kana_rn") );
		bean.setTosanKb( rest.getString("tosan_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNm( rest.getString("insert_user_nm") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNm( rest.getString("update_user_nm") );
		bean.setTenpoCdl( rest.getString("tenpo_cdl") );
		bean.setTkanjiRnl( rest.getString("tkanji_rnl") );
		bean.setFlgl( rest.getString("flgl") );
		bean.setInsertTsl( rest.getString("insert_tsl") );
		bean.setInsertUserIdl( rest.getString("insert_user_idl") );
		bean.setUpdateTsl( rest.getString("update_tsl") );
		bean.setUpdateUserIdl( rest.getString("update_user_idl") );
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
		//店舗配列が設定されていないので空文字列を戻す
		if (tenpoItiran.size() == 0) {
			return "";
		}
		
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = " where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT \n");
		sb.append("	* \n");
		sb.append("FROM \n");
		sb.append("	( \n");
		sb.append("		SELECT \n");
		sb.append("			DISTINCT \n");
		sb.append("			NVL(KANRI_KB \n");
		sb.append("			, \n");
		sb.append("			'" + getKanriKb() + "') AS KANRI_KB \n");
		sb.append("			, \n");
		sb.append("			NVL(KANRI_CD \n");
		sb.append("			, \n");
		sb.append("			'" +  getKanriCd() + "') AS KANRI_CD \n");
		sb.append("			, \n");
		sb.append("			NVL(HAISOSAKI_CD \n");
		sb.append("			, \n");
		sb.append("			HAISOSAKI_CDX) AS HAISOSAKI_CD \n");
		sb.append("			, \n");
		sb.append("			KANJI_NA \n");
		sb.append("			, \n");
		sb.append("			KANA_NA \n");
		sb.append("			, \n");
		sb.append("			KANJI_RN \n");
		sb.append("			, \n");
		sb.append("			KANA_RN \n");
		sb.append("			, \n");
		sb.append("			TOSAN_KB \n");
		sb.append("			, \n");
		sb.append("			INSERT_TS \n");
		sb.append("			, \n");
		sb.append("			INSERT_USER_ID \n");
		sb.append("			, \n");
		sb.append("			TRIM(SY1.USER_NA) AS INSERT_USER_NM \n");
		sb.append("			, \n");
		sb.append("			UPDATE_TS \n");
		sb.append("			, \n");
		sb.append("			UPDATE_USER_ID \n");
		sb.append("			, \n");
		sb.append("			TRIM(SY2.USER_NA) AS UPDATE_USER_NM \n");
		sb.append("			, ");
		sb.append("			--店舗コード生成 \n");

		//店舗コード生成
		StringBuffer sql = new StringBuffer();
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
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' as col from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
										  "'),'0')||','||\n");
			} else {
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE +  "' as col from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
										  "'),'0') as flgl,\n");
			}

		}

		sb.append("--レコード登録日時 \n");
		//レコード登録日時
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_TS from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_TS from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_tsl,\n");
			}

		}

		sb.append("--レコード登録者ID \n");
		//レコード登録者ID
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_USER_ID from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_USER_ID from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +	
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_user_idl,\n");
			}

		}

		sb.append("--レコード更新日時 \n");
		//レコード更新日時
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_TS from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_TS from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_tsl,\n");
			}

		}

		sb.append("--レコード更新者ID \n");
		//レコード更新者ID
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_USER_ID from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_USER_ID from R_TENBETU_HAISOSAKI t1 where t1.KANRI_KB = tbl.KANRI_KB and " +
										  "t1.KANRI_CD = tbl.KANRI_CD and " +
//				↓↓ＤＢバージョンアップによる変更（2005.05.19）
//										  "t1.HAISOUSAKI_CD = tbl.HAISOSAKI_CD and " +
										  "t1.HAISOSAKI_CD = tbl.HAISOSAKI_CD and " +
//				↑↑ＤＢバージョンアップによる変更（2005.05.19）
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_user_idl\n");
			}

		}

		sb.append("FROM \n");
		sb.append("	( \n");
		sb.append("		SELECT \n");
		sb.append("			KANRI_CD, \n");
		sb.append("			KANRI_KB, \n");
		sb.append("			HAISOSAKI_CD, \n");
		sb.append("			KANJI_NA, \n");
		sb.append("			KANA_NA, \n");
		sb.append("			KANJI_RN, \n");
		sb.append("			KANA_RN, \n");
		sb.append("			TOSAN_KB, \n");
		sb.append("			INSERT_TS, \n");
		sb.append("			INSERT_USER_ID, \n");
		sb.append("			UPDATE_TS, \n");
		sb.append("			UPDATE_USER_ID, \n");
		sb.append("			DELETE_FG \n");
		sb.append("		FROM \n");
		sb.append("			R_HAISOU \n");
		sb.append("		WHERE \n");
		sb.append("			TRIM(KANRI_CD) = '" + getKanriCd() + "'\n");
		sb.append("			AND \n");
		sb.append("			DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
//		↓↓仕様変更（2005.09.26）↓↓
//		sb.append("			AND ");
//		sb.append("			TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "' \n");
//		↑↑仕様変更（2005.09.26）↑↑
		sb.append("			AND ");
		sb.append("			KANRI_KB = '" + getKanriKb() + "' \n");
		sb.append("			AND \n");
		sb.append("			SUBSTR(HAISOSAKI_CD, 0, 4) = '" + getHaisosakiCd4() + "' \n");
		sb.append(") TBL \n");
		sb.append(", \n");
		sb.append("( \n");
		for (int i=0; i<9; i++) {
			sb.append("	( \n");
			sb.append("	 SELECT \n");
			sb.append("			'" + getHaisosakiCd4() + i + "' AS HAISOSAKI_CDX FROM DUAL\n");
			sb.append("	)\n   UNION ALL \n");
		}
		sb.append("	( \n");
		sb.append("	 SELECT ");
		sb.append("			'" + getHaisosakiCd4() + "9' AS HAISOSAKI_CDX FROM DUAL\n");
		sb.append("	)\n");
		sb.append(") TB1, \n");
		sb.append("SYS_SOSASYA SY1,\n ");
		sb.append("SYS_SOSASYA SY2 \n");
		sb.append("WHERE \n");
		sb.append("		TB1.HAISOSAKI_CDX = TBL.HAISOSAKI_CD(+) \n");
		sb.append("		AND \n");
		sb.append("		INSERT_USER_ID = SY1.USER_ID(+) \n");
		sb.append("		AND ");
		sb.append("		UPDATE_USER_ID = SY2.USER_ID(+) \n");
		if( map.get("kanji_na") != null && ((String)map.get("kanji_na")).trim().length() > 0 )
		{
			sb.append(" and \n");
			sb.append("kanji_na is not null \n");
		}
		sb.append("		) ");
		sb.append("		HAISOU \n");
//		sb.append("WHERE \n");
//		sb.append("INSERT_USER_NM IS NULL \n");
//		sb.append("OR \n");
//		sb.append("INSERT_USER_NM IS NOT NULL \n");
//		sb.append("AND \n");
//		sb.append("UPDATE_USER_NM IS NOT NULL \n");
		sb.append("ORDER BY \n");
		sb.append("		HAISOSAKI_CD \n");


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
	 * 管理区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setKanriKb(String kanri_kb)
		{
			this.kanri_kb = kanri_kb;
			return true;
		}
	/**
	 * 管理区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getKanriKb()
		{
			return this.kanri_kb;
		}


	/**
	 * 管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setKanriCd(String kanri_cd)
		{
			this.kanri_cd = kanri_cd;
			return true;
		}
	/**
	 * 管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getKanriCd()
		{
			return this.kanri_cd;
		}

	/**
	 * 配送先コード4桁に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHaisosakiCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHaisosakiCd4(String haisosaki_cd)
		{
			this.haisosaki_cd4 = haisosaki_cd;
			return true;
		}
	/**
	 * 配送先コード4桁に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHaisosakiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHaisosakiCd4()
		{
			return this.haisosaki_cd4;
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
