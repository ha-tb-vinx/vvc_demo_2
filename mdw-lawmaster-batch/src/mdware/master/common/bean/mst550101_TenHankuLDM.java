/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Makuta
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別販区マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別販区マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst550101_TenHankuLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst550101_TenHankuLDM()
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
		mst550101_TenHankuLBean bean = new mst550101_TenHankuLBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setHankuNa( rest.getString("hanku_na") );
		bean.setHinshuCd( rest.getString("hinshu_cd") );
		bean.setHinshuNa( rest.getString("hinshu_na") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNa( mst000401_LogicBean.chkNullString(rest.getString("tenpo_na")).trim() );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setTenhankuNa( rest.getString("tenhanku_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setYukoDtReal( rest.getString("yuko_dt_real") );
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
		String BaseSql = new String();

		if (map.get("mode").equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			//新規時
			BaseSql = getBaseSQL0(map);
		} else {
			//修正・削除・照会時
			if (map.get("yuko_dt") == null || map.get("yuko_dt").toString().length() == 0) {
				//有効日無し
				BaseSql = getBaseSQL1(map);
			} else {
				//有効日有り
				BaseSql = getBaseSQL2(map);
			}
		}
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append("SELECT ");
		sb.append("	KANRI_KB ");
		sb.append("	, ");
		sb.append("	KANRI_CD ");
		sb.append("	, ");
		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN ");
		sb.append("		KANRI_CD ");
		sb.append("	ELSE ");
		sb.append("		(SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD) ");
		sb.append("	END AS HANKU_CD ");
		sb.append("	, ");
		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN ");
		sb.append("		(SELECT ");
		sb.append("			KANJI_RN ");
		sb.append("		FROM ");
		sb.append("			R_NAMECTF ");
		sb.append("		WHERE ");
		sb.append("			TRIM(CODE_CD) = TRIM(KANRI_CD) ");
		sb.append("		AND SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "') ");
		sb.append("	ELSE ");
		sb.append("		(SELECT ");
		sb.append("			KANJI_RN ");
		sb.append("		FROM ");
		sb.append("			R_NAMECTF ");
		sb.append("		WHERE ");
		sb.append("			TRIM(CODE_CD) = TRIM((SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD)) ");
		sb.append("		AND SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "') ");
		sb.append("	END AS HANKU_NA ");
		sb.append("	, ");
		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN KANRI_CD ELSE NULL END AS HINSHU_CD ");
		sb.append("	, ");
		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' THEN ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		TRIM(CODE_CD) = TRIM(KANRI_CD) ");
		sb.append("	AND SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "') ELSE NULL END AS HINSHU_NA ");
		sb.append("	, ");
		sb.append("	TENPO_CD ");
		sb.append("	, ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_TENPO ");
		sb.append("	WHERE ");
//BUGNO-S006 2005.04.21 S.Takahashi START
		//sb.append("		R_TENPO.TENPO_CD = TBL1.TENPO_CD) AS TENPO_NA ");
		sb.append("		R_TENPO.TENPO_CD = TBL1.TENPO_CD ");
		sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ) AS TENPO_NA ");
//BUGNO-S006 2005.04.21 S.Takahashi END
		sb.append("	, ");
		sb.append("	YUKO_DT ");
		sb.append("	, ");
		sb.append("	TENHANKU_CD ");
		sb.append("	, ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		TRIM(CODE_CD) = TRIM(TENHANKU_CD) ");
//BUGNO-S006 2005.04.21 S.Takahashi START
		sb.append(" AND DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
//BUGNO-S006 2005.04.21 S.Takahashi END
		sb.append("	AND SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "') AS TENHANKU_NA ");
		sb.append("	, ");
		sb.append("	INSERT_TS ");
		sb.append("	, ");
		sb.append("	INSERT_USER_ID ");
		sb.append("	, ");
		sb.append("	(SELECT USER_NA FROM SYS_SOSASYA WHERE INSERT_USER_ID = USER_ID AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "') AS INSERT_USER_NA ");
		sb.append("	, ");
		sb.append("	UPDATE_TS ");
		sb.append("	, ");
		sb.append("	UPDATE_USER_ID ");
		sb.append("	, ");
		sb.append("	(SELECT USER_NA FROM SYS_SOSASYA WHERE UPDATE_USER_ID = USER_ID AND HOJIN_CD = '" + mst000101_ConstDictionary.HOJIN_CD + "') AS UPDATE_USER_NA ");
		sb.append("	, ");
		sb.append("	DELETE_FG ");
		sb.append("	, ");
		sb.append("	NULL AS YUKO_DT_REAL ");
		sb.append("FROM ");
		sb.append("	(" + BaseSql + ") TBL1 ");
/*
		// kanri_kb に対するWHERE区
		if( map.get("kanri_kb_bef") != null && ((String)map.get("kanri_kb_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb >= '" + conv.convertWhereString( (String)map.get("kanri_kb_bef") ) + "'");
		}
		if( map.get("kanri_kb_aft") != null && ((String)map.get("kanri_kb_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb <= '" + conv.convertWhereString( (String)map.get("kanri_kb_aft") ) + "'");
		}
		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
		}
		if( map.get("kanri_kb_like") != null && ((String)map.get("kanri_kb_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_like") ) + "%'");
		}
		if( map.get("kanri_kb_bef_like") != null && ((String)map.get("kanri_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_bef_like") ) + "'");
		}
		if( map.get("kanri_kb_aft_like") != null && ((String)map.get("kanri_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb like '" + conv.convertWhereString( (String)map.get("kanri_kb_aft_like") ) + "%'");
		}
		if( map.get("kanri_kb_in") != null && ((String)map.get("kanri_kb_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_in") ),",","','") + "' )");
		}
		if( map.get("kanri_kb_not_in") != null && ((String)map.get("kanri_kb_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_not_in") ),",","','") + "' )");
		}


		// kanri_cd に対するWHERE区
		if( map.get("kanri_cd_bef") != null && ((String)map.get("kanri_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_bef") ) + "'");
		}
		if( map.get("kanri_cd_aft") != null && ((String)map.get("kanri_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_aft") ) + "'");
		}
		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
		}
		if( map.get("kanri_cd_like") != null && ((String)map.get("kanri_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_like") ) + "%'");
		}
		if( map.get("kanri_cd_bef_like") != null && ((String)map.get("kanri_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_bef_like") ) + "'");
		}
		if( map.get("kanri_cd_aft_like") != null && ((String)map.get("kanri_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd like '" + conv.convertWhereString( (String)map.get("kanri_cd_aft_like") ) + "%'");
		}
		if( map.get("kanri_cd_in") != null && ((String)map.get("kanri_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_in") ),",","','") + "' )");
		}
		if( map.get("kanri_cd_not_in") != null && ((String)map.get("kanri_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("kanri_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_not_in") ),",","','") + "' )");
		}


		// hanku_cd に対するWHERE区
		if( map.get("hanku_cd_bef") != null && ((String)map.get("hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd >= '" + conv.convertWhereString( (String)map.get("hanku_cd_bef") ) + "'");
		}
		if( map.get("hanku_cd_aft") != null && ((String)map.get("hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd <= '" + conv.convertWhereString( (String)map.get("hanku_cd_aft") ) + "'");
		}
		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		}
		if( map.get("hanku_cd_like") != null && ((String)map.get("hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_like") ) + "%'");
		}
		if( map.get("hanku_cd_bef_like") != null && ((String)map.get("hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd like '%" + conv.convertWhereString( (String)map.get("hanku_cd_bef_like") ) + "'");
		}
		if( map.get("hanku_cd_aft_like") != null && ((String)map.get("hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd like '" + conv.convertWhereString( (String)map.get("hanku_cd_aft_like") ) + "%'");
		}
		if( map.get("hanku_cd_in") != null && ((String)map.get("hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_in") ),",","','") + "' )");
		}
		if( map.get("hanku_cd_not_in") != null && ((String)map.get("hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_cd_not_in") ),",","','") + "' )");
		}


		// hanku_na に対するWHERE区
		if( map.get("hanku_na_bef") != null && ((String)map.get("hanku_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na >= '" + conv.convertWhereString( (String)map.get("hanku_na_bef") ) + "'");
		}
		if( map.get("hanku_na_aft") != null && ((String)map.get("hanku_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na <= '" + conv.convertWhereString( (String)map.get("hanku_na_aft") ) + "'");
		}
		if( map.get("hanku_na") != null && ((String)map.get("hanku_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na = '" + conv.convertWhereString( (String)map.get("hanku_na") ) + "'");
		}
		if( map.get("hanku_na_like") != null && ((String)map.get("hanku_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na like '%" + conv.convertWhereString( (String)map.get("hanku_na_like") ) + "%'");
		}
		if( map.get("hanku_na_bef_like") != null && ((String)map.get("hanku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na like '%" + conv.convertWhereString( (String)map.get("hanku_na_bef_like") ) + "'");
		}
		if( map.get("hanku_na_aft_like") != null && ((String)map.get("hanku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na like '" + conv.convertWhereString( (String)map.get("hanku_na_aft_like") ) + "%'");
		}
		if( map.get("hanku_na_in") != null && ((String)map.get("hanku_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_na_in") ),",","','") + "' )");
		}
		if( map.get("hanku_na_not_in") != null && ((String)map.get("hanku_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hanku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanku_na_not_in") ),",","','") + "' )");
		}


		// hinshu_cd に対するWHERE区
		if( map.get("hinshu_cd_bef") != null && ((String)map.get("hinshu_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd >= '" + conv.convertWhereString( (String)map.get("hinshu_cd_bef") ) + "'");
		}
		if( map.get("hinshu_cd_aft") != null && ((String)map.get("hinshu_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd <= '" + conv.convertWhereString( (String)map.get("hinshu_cd_aft") ) + "'");
		}
		if( map.get("hinshu_cd") != null && ((String)map.get("hinshu_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd = '" + conv.convertWhereString( (String)map.get("hinshu_cd") ) + "'");
		}
		if( map.get("hinshu_cd_like") != null && ((String)map.get("hinshu_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd like '%" + conv.convertWhereString( (String)map.get("hinshu_cd_like") ) + "%'");
		}
		if( map.get("hinshu_cd_bef_like") != null && ((String)map.get("hinshu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd like '%" + conv.convertWhereString( (String)map.get("hinshu_cd_bef_like") ) + "'");
		}
		if( map.get("hinshu_cd_aft_like") != null && ((String)map.get("hinshu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd like '" + conv.convertWhereString( (String)map.get("hinshu_cd_aft_like") ) + "%'");
		}
		if( map.get("hinshu_cd_in") != null && ((String)map.get("hinshu_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinshu_cd_in") ),",","','") + "' )");
		}
		if( map.get("hinshu_cd_not_in") != null && ((String)map.get("hinshu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinshu_cd_not_in") ),",","','") + "' )");
		}


		// hinshu_na に対するWHERE区
		if( map.get("hinshu_na_bef") != null && ((String)map.get("hinshu_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na >= '" + conv.convertWhereString( (String)map.get("hinshu_na_bef") ) + "'");
		}
		if( map.get("hinshu_na_aft") != null && ((String)map.get("hinshu_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na <= '" + conv.convertWhereString( (String)map.get("hinshu_na_aft") ) + "'");
		}
		if( map.get("hinshu_na") != null && ((String)map.get("hinshu_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na = '" + conv.convertWhereString( (String)map.get("hinshu_na") ) + "'");
		}
		if( map.get("hinshu_na_like") != null && ((String)map.get("hinshu_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na like '%" + conv.convertWhereString( (String)map.get("hinshu_na_like") ) + "%'");
		}
		if( map.get("hinshu_na_bef_like") != null && ((String)map.get("hinshu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na like '%" + conv.convertWhereString( (String)map.get("hinshu_na_bef_like") ) + "'");
		}
		if( map.get("hinshu_na_aft_like") != null && ((String)map.get("hinshu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na like '" + conv.convertWhereString( (String)map.get("hinshu_na_aft_like") ) + "%'");
		}
		if( map.get("hinshu_na_in") != null && ((String)map.get("hinshu_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinshu_na_in") ),",","','") + "' )");
		}
		if( map.get("hinshu_na_not_in") != null && ((String)map.get("hinshu_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("hinshu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinshu_na_not_in") ),",","','") + "' )");
		}


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
		}


		// tenpo_na に対するWHERE区
		if( map.get("tenpo_na_bef") != null && ((String)map.get("tenpo_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na >= '" + conv.convertWhereString( (String)map.get("tenpo_na_bef") ) + "'");
		}
		if( map.get("tenpo_na_aft") != null && ((String)map.get("tenpo_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na <= '" + conv.convertWhereString( (String)map.get("tenpo_na_aft") ) + "'");
		}
		if( map.get("tenpo_na") != null && ((String)map.get("tenpo_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na = '" + conv.convertWhereString( (String)map.get("tenpo_na") ) + "'");
		}
		if( map.get("tenpo_na_like") != null && ((String)map.get("tenpo_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na like '%" + conv.convertWhereString( (String)map.get("tenpo_na_like") ) + "%'");
		}
		if( map.get("tenpo_na_bef_like") != null && ((String)map.get("tenpo_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na like '%" + conv.convertWhereString( (String)map.get("tenpo_na_bef_like") ) + "'");
		}
		if( map.get("tenpo_na_aft_like") != null && ((String)map.get("tenpo_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na like '" + conv.convertWhereString( (String)map.get("tenpo_na_aft_like") ) + "%'");
		}
		if( map.get("tenpo_na_in") != null && ((String)map.get("tenpo_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_na_in") ),",","','") + "' )");
		}
		if( map.get("tenpo_na_not_in") != null && ((String)map.get("tenpo_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenpo_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_na_not_in") ),",","','") + "' )");
		}


		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt_bef") != null && ((String)map.get("yuko_dt_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt >= '" + conv.convertWhereString( (String)map.get("yuko_dt_bef") ) + "'");
		}
		if( map.get("yuko_dt_aft") != null && ((String)map.get("yuko_dt_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt <= '" + conv.convertWhereString( (String)map.get("yuko_dt_aft") ) + "'");
		}
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
		}
		if( map.get("yuko_dt_like") != null && ((String)map.get("yuko_dt_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_like") ) + "%'");
		}
		if( map.get("yuko_dt_bef_like") != null && ((String)map.get("yuko_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_bef_like") ) + "'");
		}
		if( map.get("yuko_dt_aft_like") != null && ((String)map.get("yuko_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt like '" + conv.convertWhereString( (String)map.get("yuko_dt_aft_like") ) + "%'");
		}
		if( map.get("yuko_dt_in") != null && ((String)map.get("yuko_dt_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_in") ),",","','") + "' )");
		}
		if( map.get("yuko_dt_not_in") != null && ((String)map.get("yuko_dt_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("yuko_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_not_in") ),",","','") + "' )");
		}


		// tenhanku_cd に対するWHERE区
		if( map.get("tenhanku_cd_bef") != null && ((String)map.get("tenhanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd >= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef") ) + "'");
		}
		if( map.get("tenhanku_cd_aft") != null && ((String)map.get("tenhanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd <= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft") ) + "'");
		}
		if( map.get("tenhanku_cd") != null && ((String)map.get("tenhanku_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd = '" + conv.convertWhereString( (String)map.get("tenhanku_cd") ) + "'");
		}
		if( map.get("tenhanku_cd_like") != null && ((String)map.get("tenhanku_cd_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_like") ) + "%'");
		}
		if( map.get("tenhanku_cd_bef_like") != null && ((String)map.get("tenhanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef_like") ) + "'");
		}
		if( map.get("tenhanku_cd_aft_like") != null && ((String)map.get("tenhanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd like '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft_like") ) + "%'");
		}
		if( map.get("tenhanku_cd_in") != null && ((String)map.get("tenhanku_cd_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_in") ),",","','") + "' )");
		}
		if( map.get("tenhanku_cd_not_in") != null && ((String)map.get("tenhanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_not_in") ),",","','") + "' )");
		}


		// tenhanku_na に対するWHERE区
		if( map.get("tenhanku_na_bef") != null && ((String)map.get("tenhanku_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na >= '" + conv.convertWhereString( (String)map.get("tenhanku_na_bef") ) + "'");
		}
		if( map.get("tenhanku_na_aft") != null && ((String)map.get("tenhanku_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na <= '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft") ) + "'");
		}
		if( map.get("tenhanku_na") != null && ((String)map.get("tenhanku_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na = '" + conv.convertWhereString( (String)map.get("tenhanku_na") ) + "'");
		}
		if( map.get("tenhanku_na_like") != null && ((String)map.get("tenhanku_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_like") ) + "%'");
		}
		if( map.get("tenhanku_na_bef_like") != null && ((String)map.get("tenhanku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_bef_like") ) + "'");
		}
		if( map.get("tenhanku_na_aft_like") != null && ((String)map.get("tenhanku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na like '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft_like") ) + "%'");
		}
		if( map.get("tenhanku_na_in") != null && ((String)map.get("tenhanku_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_in") ),",","','") + "' )");
		}
		if( map.get("tenhanku_na_not_in") != null && ((String)map.get("tenhanku_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("tenhanku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_not_in") ),",","','") + "' )");
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
		}


		// insert_user_na に対するWHERE区
		if( map.get("insert_user_na_bef") != null && ((String)map.get("insert_user_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na >= '" + conv.convertWhereString( (String)map.get("insert_user_na_bef") ) + "'");
		}
		if( map.get("insert_user_na_aft") != null && ((String)map.get("insert_user_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na <= '" + conv.convertWhereString( (String)map.get("insert_user_na_aft") ) + "'");
		}
		if( map.get("insert_user_na") != null && ((String)map.get("insert_user_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na = '" + conv.convertWhereString( (String)map.get("insert_user_na") ) + "'");
		}
		if( map.get("insert_user_na_like") != null && ((String)map.get("insert_user_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na like '%" + conv.convertWhereString( (String)map.get("insert_user_na_like") ) + "%'");
		}
		if( map.get("insert_user_na_bef_like") != null && ((String)map.get("insert_user_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na like '%" + conv.convertWhereString( (String)map.get("insert_user_na_bef_like") ) + "'");
		}
		if( map.get("insert_user_na_aft_like") != null && ((String)map.get("insert_user_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na like '" + conv.convertWhereString( (String)map.get("insert_user_na_aft_like") ) + "%'");
		}
		if( map.get("insert_user_na_in") != null && ((String)map.get("insert_user_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_na_in") ),",","','") + "' )");
		}
		if( map.get("insert_user_na_not_in") != null && ((String)map.get("insert_user_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("insert_user_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_na_not_in") ),",","','") + "' )");
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
		}


		// update_user_na に対するWHERE区
		if( map.get("update_user_na_bef") != null && ((String)map.get("update_user_na_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na >= '" + conv.convertWhereString( (String)map.get("update_user_na_bef") ) + "'");
		}
		if( map.get("update_user_na_aft") != null && ((String)map.get("update_user_na_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na <= '" + conv.convertWhereString( (String)map.get("update_user_na_aft") ) + "'");
		}
		if( map.get("update_user_na") != null && ((String)map.get("update_user_na")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na = '" + conv.convertWhereString( (String)map.get("update_user_na") ) + "'");
		}
		if( map.get("update_user_na_like") != null && ((String)map.get("update_user_na_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na like '%" + conv.convertWhereString( (String)map.get("update_user_na_like") ) + "%'");
		}
		if( map.get("update_user_na_bef_like") != null && ((String)map.get("update_user_na_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na like '%" + conv.convertWhereString( (String)map.get("update_user_na_bef_like") ) + "'");
		}
		if( map.get("update_user_na_aft_like") != null && ((String)map.get("update_user_na_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na like '" + conv.convertWhereString( (String)map.get("update_user_na_aft_like") ) + "%'");
		}
		if( map.get("update_user_na_in") != null && ((String)map.get("update_user_na_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_na_in") ),",","','") + "' )");
		}
		if( map.get("update_user_na_not_in") != null && ((String)map.get("update_user_na_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("update_user_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_na_not_in") ),",","','") + "' )");
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
		}
*/
		return sb.toString();
	}

	/**
	 * 新規時ベースSQLの生成
	 * @return String SQL文字列
	 */
	private String getBaseSQL0( Map map )
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	 NULL AS YUKO_DT ");
		sb.append("		,'" + map.get("kanri_kb") + "' AS KANRI_KB ");
		sb.append("		,'" + map.get("kanri_cd") + "' AS KANRI_CD ");
		sb.append("		,NULL AS TENPO_CD ");
		sb.append("		,NULL AS TENHANKU_CD ");
		sb.append("		,NULL AS INSERT_TS ");
		sb.append("		,NULL AS INSERT_USER_ID ");
		sb.append("		,NULL AS UPDATE_TS ");
		sb.append("		,NULL AS UPDATE_USER_ID ");
		sb.append("		,NULL AS DELETE_FG ");
		sb.append("	FROM ");
		sb.append(" 	DUAL ");
		return sb.toString();
	}

	/**
	 * 新規時以外ベースSQLの生成（有効日なし）
	 * @return String SQL文字列
	 */
	private String getBaseSQL1( Map map )
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	 NULL AS YUKO_DT ");
		sb.append("		,KANRI_KB ");
		sb.append("		,KANRI_CD ");
		sb.append("		,TENPO_CD ");
		sb.append("		,NULL AS TENHANKU_CD ");
		sb.append("		,NULL AS INSERT_TS ");
		sb.append("		,NULL AS INSERT_USER_ID ");
		sb.append("		,NULL AS UPDATE_TS ");
		sb.append("		,NULL AS UPDATE_USER_ID ");
		sb.append("		,NULL AS DELETE_FG ");
		sb.append("	FROM ");
		sb.append(" 	R_TENHANKU ");
		sb.append("	WHERE ");
		sb.append(" 	KANRI_KB = '" + map.get("kanri_kb") + "' ");
		sb.append(" AND	KANRI_CD = '" + map.get("kanri_cd") + "' ");
		sb.append("	GROUP BY ");
		sb.append("		 KANRI_KB ");
		sb.append("		,KANRI_CD ");
		sb.append("		,TENPO_CD ");
		if (map.get("kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
			sb.append("	UNION ");
			sb.append(" SELECT ");
			sb.append(" 	 NULL AS YUKO_DT ");
			sb.append("		,KANRI_KB ");
			sb.append("		,KANRI_CD ");
			sb.append("		,TENPO_CD ");
			sb.append("		,NULL AS TENHANKU_CD ");
			sb.append("		,NULL AS INSERT_TS ");
			sb.append("		,NULL AS INSERT_USER_ID ");
			sb.append("		,NULL AS UPDATE_TS ");
			sb.append("		,NULL AS UPDATE_USER_ID ");
			sb.append("		,NULL AS DELETE_FG ");
			sb.append("	FROM ");
			sb.append(" 	 R_TENHANKU ");
			sb.append(" 	,R_SYOHIN_TAIKEI ");
			sb.append("	WHERE ");
			sb.append(" 	R_TENHANKU.KANRI_CD = R_SYOHIN_TAIKEI.HINSYU_CD ");
			sb.append(" AND	KANRI_KB = '" + mst000901_KanriKbDictionary.HINSYU.getCode() + "' ");
			sb.append(" AND	KANRI_CD IN (SELECT HINSYU_CD FROM R_SYOHIN_TAIKEI WHERE HANKU_CD = '" + map.get("kanri_cd") + "') ");
			sb.append("	GROUP BY ");
			sb.append("		 KANRI_KB ");
			sb.append("		,KANRI_CD ");
			sb.append("		,TENPO_CD ");
		}
		return sb.toString();
	}

	/**
	 * 新規時以外ベースSQLの生成（有効日あり）
	 * @return String SQL文字列
	 */
	private String getBaseSQL2( Map map )
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	 YUKO_DT ");
		sb.append("		,KANRI_KB ");
		sb.append("		,KANRI_CD ");
		sb.append("		,TENPO_CD ");
		sb.append("		,TENHANKU_CD ");
		sb.append("		,INSERT_TS ");
		sb.append("		,INSERT_USER_ID ");
		sb.append("		,UPDATE_TS ");
		sb.append("		,UPDATE_USER_ID ");
		sb.append("		,DELETE_FG ");
		sb.append("	FROM ");
		sb.append(" 	R_TENHANKU ");
		sb.append("	WHERE ");
		sb.append(" 	YUKO_DT  = '" + map.get("yuko_dt") + "' ");
		sb.append(" AND	KANRI_KB = '" + map.get("kanri_kb") + "' ");
		sb.append(" AND	KANRI_CD = '" + map.get("kanri_cd") + "' ");
		sb.append(" AND	TENPO_CD = '" + map.get("tenpo_cd") + "' ");
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
