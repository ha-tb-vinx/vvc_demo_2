package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.resorces.util.SqlSupportUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 * @version 1.01 (2012.09.23) Y.Imai 【MM00158】青果部門内での重複チェック追加対応
 */
public class mst001501_SaibanDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mst001501_SaibanDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
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
		mst000601_KoushinInfoBean bean = new mst000601_KoushinInfoBean();
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setInsertUserName( rest.getString("insert_user_name") );
		bean.setUpdateUserName( rest.getString("update_user_name") );
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
		return null;	
	}
	
	/**
	 * 自動採番枠品種別マスタ検索用ＳＱＬの生成を行う。
	 * データ存在チェック
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanWakuHinsyuSelectSql( String hinsyuCd )
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select ");
		sb.append(" start_tanpin_cd ");
		sb.append(",");
		sb.append(" end_tanpin_cd ");
		sb.append(",");
		sb.append(" delete_fg ");		
		sb.append(" from ");
		sb.append(" r_saiban_waku_hinsyu ");
		sb.append(" where ");
		sb.append(" hinsyu_cd = '" + hinsyuCd + "' ");
		return sb.toString();
	}
		
	
	/**
	 * 自動採番枠品種別マスタ挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanWakuHinsyuInsertSql( String hinsyuCd, int stTanpincd, int edTanpincd, String UserId ) throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_saiban_waku_hinsyu (");
		sb.append(" hinsyu_cd ");
	 	sb.append(",");
		sb.append(" start_tanpin_cd ");
		sb.append(",");
		sb.append(" end_tanpin_cd ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(")Values(");
		sb.append("'" + hinsyuCd + "'");
		sb.append(",");		
		sb.append(stTanpincd);
		sb.append(",");		
		sb.append(edTanpincd);
		sb.append(",");		
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '" );
		sb.append(",");		
		sb.append("'" + UserId + "'");
		sb.append(",");		
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");		
		sb.append(")");
		
		return sb.toString();
	}
	
	/**
	 * 自動採番マスタ(8桁商品)検索用ＳＱＬの生成を行う。
	 * データ存在チェック
	 * 渡された条件を元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanSelectSql( String tableNa, String hinsyuCd, String tanpinCd )
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select ");
		sb.append(" * ");
		sb.append(" from ");
		sb.append(" " + tableNa + " ");
		sb.append(" where ");
		sb.append(" tanpin_cd = '" + tanpinCd + "' ");
		if(!hinsyuCd.equals("")){
			sb.append(" and ");			
			sb.append(" hinsyu_cd = '" + hinsyuCd + "' ");
		}
		
		return sb.toString();
	}
	
	/**
	 * 自動採番マスタマスタ検索用ＳＱＬの生成を行う。
	 * 件数取得
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanCntSelectSql(
			String tbl,
			String clm,
	 		String hinsyuCd, 
	 		String stTanpinCd, 
	 		String edTanpinCd,
			List whereList)	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select ");
		sb.append("	count(*) cnt ");
		sb.append(" from ");
		sb.append( tbl );
		sb.append(" where ");
		sb.append( clm + " between '" + stTanpinCd + "' and '" + edTanpinCd + "' ");
		if(!hinsyuCd.equals("")){
			sb.append(" and ");
			sb.append(" hinsyu_cd = '" + hinsyuCd + "' ");
		}		
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

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
	 * 自動採番マスタ(8桁商品)挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaiban8KetaInsertSql( String hinsyuCd, String tanpincd,String kanriKb,String chkDegi,String userId  ) throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_saiban_8keta (");
		sb.append(" hinsyu_cd ");
		sb.append(",");
		sb.append(" tanpin_cd ");
		sb.append(",");
		sb.append(" jyotai_kanri_kb ");
		sb.append(",");
		sb.append(" check_degit_cd ");
		sb.append(",");
		sb.append(" haiban_dt ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(")Values(");
		sb.append("'" + hinsyuCd + "'");
		sb.append(",");		
		sb.append("'" + tanpincd + "'");
		sb.append(",");		
		sb.append("'" + kanriKb + "'");
		sb.append(",");		
		sb.append("'" + chkDegi + "'");
		sb.append(",");		
		sb.append(" null ");
		sb.append(",");		
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '" );
		sb.append(",");		
		sb.append("'" + userId + "'");
		sb.append(",");		
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");		
		sb.append(")");
		
		return sb.toString();
	}

	/**
	 * 自動採番マスタ(インストア)挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanInstoreInsertSql( String tanpincd,String kanriKb,String chkDegi,String userId  ) throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_saiban_instore (");
		sb.append(" tanpin_cd ");
		sb.append(",");
		sb.append(" jyotai_kanri_kb ");
		sb.append(",");
		sb.append(" check_degit_cd ");
		sb.append(",");
		sb.append(" haiban_dt ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(")Values(");
		sb.append("'" + tanpincd + "'");
		sb.append(",");		
		sb.append("'" + kanriKb + "'");
		sb.append(",");		
		sb.append("'" + chkDegi + "'");
		sb.append(",");		
		sb.append(" null ");
		sb.append(",");		
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '" );
		sb.append(",");		
		sb.append("'" + userId + "'");
		sb.append(",");		
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");		
		sb.append(")");
		
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanUpdateSql( 
					String tableNa, 
					String hinsyuCd, 
					String tanpincd,
					String kanriKb,
					String userId  ) throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(tableNa + " set ");
		sb.append(" jyotai_kanri_kb = '" + kanriKb + "'");
		sb.append(",");		
		sb.append(" haiban_dt = null");
		sb.append(",");
		sb.append(" update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "  '");
		sb.append(",");
		sb.append(" update_user_id = '" + userId + "'");
		sb.append(" WHERE");
		sb.append(" tanpin_cd = '" + tanpincd + "' ");
		if(!hinsyuCd.equals("")){
			sb.append(" and ");
			sb.append(" hinsyu_cd = '" + hinsyuCd + "' ");
		}
		return sb.toString();
	}

	/**
	 * 8桁商品の単品コードを取得するSQL
	 */
	public String getTanpin8Keta(String startCd, String endCd, String hinsyuCd) {
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append("    coalesce(");
		sb.append("        (select");
		sb.append("            lpad(to_char(min(seq)),4,'0')");
		sb.append("        from ");
		sb.append("            (select");
		sb.append("                lpad(tanpin_cd,4,'0') as tanpin_cd,");
		sb.append("                ((select count(*) from  r_saiban_8keta rs2 ");
		sb.append("                                            where rs2.tanpin_cd <= rs1.tanpin_cd and ");
		sb.append("                rs2.hinsyu_cd = '").append(hinsyuCd).append("' and");
		sb.append("                rs2.tanpin_cd between '").append(startCd).append("' and '").append(endCd).append("'");
		sb.append("                                ) + ").append(startCd).append(" - 1) as seq");
		sb.append("            from");
		sb.append("                r_saiban_8keta rs1");
		sb.append("            where");
		sb.append("                rs1.hinsyu_cd = '").append(hinsyuCd).append("' and");
		sb.append("                rs1.tanpin_cd between '").append(startCd).append("' and '").append(endCd).append("'");
		sb.append("        ) rs");
		sb.append("        where");
		sb.append("            tanpin_cd <> seq");
		sb.append("        ),");
		sb.append("        (select");
		sb.append("            lpad(to_char(to_number(max(tanpin_cd)) + 1),4,'0')");
		sb.append("        from");
		sb.append("            r_saiban_8keta");
		sb.append("        where");
		sb.append("            hinsyu_cd = '").append(hinsyuCd).append("' and");
		sb.append("            tanpin_cd between '").append(startCd).append("' and '").append(endCd).append("'");
		sb.append("        )");
		sb.append("    ) as tanpin_cd ");
		sb.append("from");
		sb.append("    dual");

		return sb.toString();
	}

	/**
	 * インストアの単品コードを取得するSQL
	 */
	public String getTanpinInstore() {
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append("    coalesce(");
		sb.append("        (select");
		sb.append("            lpad(to_char(min(seq)),6,'0')");
		sb.append("        from");
		sb.append("            (select");
		sb.append("                lpad(rsi1.tanpin_cd,6,'0') as tanpin_cd,");
		sb.append("                ((select count(*) from  r_saiban_instore rsi2 where rsi2.tanpin_cd <= rsi1.tanpin_cd) - 1) as seq");
		sb.append("            from");
		sb.append("                r_saiban_instore rsi1");
		sb.append("            ) rsi");
		sb.append("        where");
		sb.append("            tanpin_cd <> seq");
		sb.append("        ),");
		sb.append("        (select");
		sb.append("            lpad(to_char(to_number(max(tanpin_cd)) + 1),6,'0')");
		sb.append("        from");
		sb.append("            r_saiban_instore");
		sb.append("        )");
		sb.append("    ) as tanpin_cd ");
		sb.append("from");
		sb.append("    dual");
		
		return sb.toString();
	}

	/**
	 * 採番マスタの件数取得ＳＱＬの生成を行う
	 * @param saibanId 採番ID
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanCntSql(String saibanId){
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append("    count(*) count  ");
		sb.append("from");
		sb.append("    r_seq ");
		sb.append("where");
		sb.append("    saiban_id = '" + saibanId + "' ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(実用)の連番コード取得ＳＱＬの生成を行う
	 * @param bySikibetuNo バイヤー識別NO
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA07Sql(String bySikibetuNo){
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append(" by_sikibetu_no, ");
		sb.append(" max(renban_cd) as renban_cd ");
		sb.append("from");
		sb.append("    r_saiban_a07 ");
		sb.append("where");
		sb.append("    by_sikibetu_no = '" + bySikibetuNo + "' ");
		sb.append(" group by by_sikibetu_no");
		sb.append(" for read only ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(タグ)の商品コード取得ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA08Sql(){
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append("    max(syohin_cd) as syohin_cd ");
		sb.append("from");
		sb.append("    r_saiban_a08 ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(タグ)の状態取得ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA08JyotaiSql(String syohinCd){
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("    jyotai_kanri_fg as jyotai_kanri_fg ");
		sb.append(",");
		sb.append("    by_no as by_no ");
		sb.append(",");
		sb.append("    delete_fg as delete_fg ");
		sb.append("from ");
		sb.append("    r_saiban_a08 ");
		sb.append("where ");
		sb.append("    syohin_cd = '").append(syohinCd).append("' ");
		sb.append(" for read only ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(タグ)の状態更新ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA08JyotaiUpdSql(String syohinCd, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("    r_saiban_a08 ");
		sb.append("set ");
		sb.append(" jyotai_kanri_fg = '1'");
		sb.append(",");
		sb.append(" update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
		sb.append(",");
		sb.append(" update_user_id = '" +userId + "'" );
		sb.append("where");
		sb.append("    syohin_cd = '").append(syohinCd).append("' ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(タグ)の物理削除ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA08DelSql(String syohinCd) {
		StringBuffer sb = new StringBuffer();

		sb.append("delete ");
		sb.append("from ");
		sb.append("    r_saiban_a08 ");
		sb.append("where");
		sb.append("    syohin_cd = '").append(syohinCd).append("' ");
		
		return sb.toString();
	}

	/**
	 * 採番マスタに登録ＳＱＬの生成を行う
	 * @param saibanId 採番ID
	 * @param keTasu  採番番号桁数
	 * @param userId    userId
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanInsert(String saibanId, String curCnt, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();
		
		sb.append("insert into ");
		sb.append("     r_seq (");
		sb.append(" saiban_id ");
		sb.append(",");
		sb.append(" cur_cnt ");
		sb.append(",");
		sb.append(" min_cnt ");
		sb.append(",");
		sb.append(" max_cnt ");
		sb.append(",");
		sb.append(" ketasu ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" update_ts ");
		sb.append(",");
		sb.append(" update_user_id ");
		sb.append(")Values(");
		sb.append("'" + saibanId + "'");
		sb.append(",");
		sb.append(curCnt);
		sb.append(",");
		sb.append(curCnt);
		sb.append(",");
		sb.append(" 99999999");
		sb.append(",");
		sb.append(" 8");
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(")");
		
		return sb.toString();
	}

	/**
	 * 採番マスタ検索ＳＱＬの生成を行う
	 * @param saibanId 採番ID
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanSql(String saibanId){
		StringBuffer sb = new StringBuffer();
		
		sb.append("select");
		sb.append("    saiban_id ");
		sb.append(",");
		sb.append("    cur_cnt ");
		sb.append(",");
		sb.append("    min_cnt ");
		sb.append(",");
		sb.append("    max_cnt ");
		sb.append(",");
		sb.append("    ketasu ");
		sb.append(",");
		sb.append("    insert_ts ");
		sb.append(",");
		sb.append("    insert_user_id ");
		sb.append(",");
		sb.append("    update_ts ");
		sb.append(",");
		sb.append("    update_user_id ");
		sb.append("from");
		sb.append("    r_seq ");
		sb.append("where");
		sb.append("    saiban_id = '" + saibanId + "'");
		
		return sb.toString();
		
	}

	/**
	 * 採番マスタに更新ＳＱＬの生成を行う
	 * @param saibanId 採番ID
	 * @param curCnt  現在値
	 * @param userId   userId
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String getSaibanUpdate(String saibanId, String curCnt, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();
		
		sb.append("update ");
		sb.append("    r_seq ");
		sb.append("set ");
		sb.append(" cur_cnt = " + curCnt);
		sb.append(",");
		sb.append(" update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
		sb.append(",");
		sb.append(" update_user_id = '" +userId + "'");
		sb.append("where");
		sb.append("    saiban_id = '" + saibanId + "'");
		
		return sb.toString();
		
	}
	
	/**
	 * 採番マスタ(タグ)に登録ＳＱＬの生成を行う
	 * @param curCnt 現在値
	 * @param byNo  バイヤーNO
	 * @param userId   userId
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String tagSaibanInsert(String curCnt, String checkDegitCd, String byNo, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();
		
		sb.append("insert into ");
		sb.append("     r_saiban_a08 (");
		sb.append(" syohin_cd ");
		sb.append(",");
		sb.append(" check_degit_cd ");
		sb.append(",");
		sb.append(" jyotai_kanri_fg ");
		sb.append(",");
		sb.append(" by_no ");
		sb.append(",");
		sb.append(" comment_tx ");
		sb.append(",");
		sb.append(" haiban_dt ");
		sb.append(",");
		sb.append(" yoyaku_dt ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" update_ts ");
		sb.append(",");
		sb.append(" update_user_id ");
		sb.append(")Values(");
		sb.append("'" + curCnt + "'");
		sb.append(",");
		sb.append(" '" + checkDegitCd + "'");
		sb.append(",");
		sb.append(" '1'");
		sb.append(",");
		sb.append(" '" + byNo + "'");
		sb.append(",");
		sb.append(" null");
		sb.append(",");
		sb.append(" null" );
		sb.append(",");
		sb.append(" null");
		sb.append(",");
		sb.append("  '0'" );
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(")");
		
		return sb.toString();
	}

	
	/**
	 * 採番マスタ(実用)の状態取得ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA07JyotaiSql(String syohinCd){
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("    jyotai_kanri_fg as jyotai_kanri_fg ");
		sb.append(",");
		sb.append("    by_no as by_no ");
		sb.append(",");
		sb.append("    delete_fg as delete_fg ");
		sb.append("from ");
		sb.append("    r_saiban_a07 ");
		sb.append("where ");
		sb.append("    by_sikibetu_no = '").append(syohinCd.substring(0,2)).append("' ");
		sb.append("and renban_cd = '").append(syohinCd.substring(2,6)).append("' ");
		sb.append(" for read only ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(実用)の状態更新ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA07JyotaiUpdSql(String syohinCd, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("    r_saiban_a07 ");
		sb.append("set ");
		sb.append(" jyotai_kanri_fg = '1'");
		sb.append(",");
		sb.append(" update_ts = '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'");
		sb.append(",");
		sb.append(" update_user_id = '" +userId + "'" );
		sb.append("where");
		sb.append("    by_sikibetu_no = '").append(syohinCd.substring(0,2)).append("' ");
		sb.append("and renban_cd = '").append(syohinCd.substring(2,6)).append("' ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ(実用)の物理削除ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA07DelSql(String syohinCd) {
		StringBuffer sb = new StringBuffer();

		sb.append("delete ");
		sb.append("from ");
		sb.append("    r_saiban_a07 ");
		sb.append("where");
		sb.append("    by_sikibetu_no = '").append(syohinCd.substring(0,2)).append("' ");
		sb.append("and renban_cd = '").append(syohinCd.substring(2,6)).append("' ");
		
		return sb.toString();
	}
	
	/**
	 * 採番マスタ（実用）に登録ＳＱＬの生成を行う
	 * @param curCnt 現在値
	 * @param byNo    バイヤーNO
	 * @param bySikibetuNo   識別NO
	 * 	@param checkDegitCd デジット
	 * @return userId userId
	 * @return String 生成されたＳＱＬ
	 * @throws SQLException 
	 */
	public String jitsuyouSaibanInsert(String curCnt, String byNo, String bySikibetuNo,  String checkDegitCd, String userId) throws SQLException{
		StringBuffer sb = new StringBuffer();
		
		sb.append("insert into ");
		sb.append("     r_saiban_a07 (");
		sb.append(" by_sikibetu_no ");
		sb.append(",");
		sb.append(" renban_cd ");
		sb.append(",");
		sb.append(" check_degit_cd ");
		sb.append(",");
		sb.append(" jyotai_kanri_fg ");
		sb.append(",");
		sb.append(" by_no ");
		sb.append(",");
		sb.append(" comment_tx ");
		sb.append(",");
		sb.append(" haiban_dt ");
		sb.append(",");
		sb.append(" yoyaku_dt ");
		sb.append(",");
		sb.append(" delete_fg ");
		sb.append(",");
		sb.append(" insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id ");
		sb.append(",");
		sb.append(" update_ts ");
		sb.append(",");
		sb.append(" update_user_id ");
		sb.append(")Values(");
		sb.append("'" + bySikibetuNo + "'");
		sb.append(",");
		sb.append(" '" + curCnt + "'");
		sb.append(",");
		sb.append(" '" + checkDegitCd + "'");
		sb.append(",");
		sb.append(" '1'");
		sb.append(",");
		sb.append(" '" + byNo + "'");
		sb.append(",");
		sb.append(" null");
		sb.append(",");
		sb.append(" null" );
		sb.append(",");
		sb.append(" null");
		sb.append(",");
		sb.append("  '0'" );
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(",");
		sb.append("  '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "'" );
		sb.append(",");
		sb.append(" '" + userId + "'");
		sb.append(")");
		
		
		return sb.toString();
	}
	
	/**
	 * 同一商品の有無取得ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSyohinCntSql(String syohinCd, String yukoDt, String masterDt){

		StringBuffer sb = new StringBuffer();

		if (yukoDt == null || yukoDt.trim().equals("")) {
			yukoDt = masterDt;
		}

		sb.append("select");
		sb.append("    count(rs.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_syohin rs ");
		sb.append("where ");
		sb.append("    rs.syohin_cd = '").append(syohinCd).append("' ");
		sb.append(" and ");
//↓↓2011.03.02 ADD T.Urano
		sb.append("( ");
		sb.append("    rs.yuko_dt >= '").append(yukoDt).append("' ");
		sb.append("    or ");
//↑↑2011.03.02 ADD T.Urano
		sb.append("    rs.yuko_dt >= ");
		sb.append("( ");
		sb.append("select");
		sb.append("    max(yuko_dt) ");
		sb.append("from ");
		sb.append("    r_syohin rsw ");
		sb.append("where ");
//		sb.append("    rsw.bumon_cd = rs.bumon_cd ");
		sb.append("    rsw.bunrui1_cd = rs.bunrui1_cd ");
		sb.append(" and ");
		sb.append("    rsw.syohin_cd = rs.syohin_cd ");
		sb.append(" and ");
		sb.append("    rsw.yuko_dt <= '").append(yukoDt).append("' ");
		sb.append(") ");
//↓↓2011.03.02 ADD T.Urano
		sb.append(") ");
//↑↑2011.03.02 ADD T.Urano
		sb.append(" and ");
		sb.append("    rs.delete_fg = '0' ");
		
		return sb.toString();
	}

//↓↓2011.03.02 ADD T.Urano
	/**
	 * 指定した計量器呼出NOが登録されているかチェックするＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getKeiryokiSyohinYobidasiCntSql(List lstBunrui1Cd, String yobidasiCd, String yukoDt, String masterDt){

		StringBuffer sb = new StringBuffer();

		if (yukoDt == null || yukoDt.trim().equals("")) {
			yukoDt = masterDt;
		}

		sb.append("select");
		sb.append("    count(rk.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_keiryoki rk ");
		sb.append("where ");
		sb.append("    rk.syohin_yobidasi = '").append(yobidasiCd).append("' ");
		sb.append("and rk.syohin_cd like '02%' "); 
		sb.append("and rk.bunrui1_cd in (").append( SqlSupportUtil.createInString(lstBunrui1Cd.toArray())).append(") "); 
		sb.append("and rk.delete_fg = '0' ");
		sb.append("and (");
		sb.append("        rk.yuko_dt > '").append(yukoDt).append("' ");
		sb.append("      or ");
		sb.append("        rk.yuko_dt >= ");
		sb.append("        ( ");
		sb.append("           select");
		sb.append("               max(yuko_dt) ");
		sb.append("           from ");
		sb.append("               r_keiryoki ");
		sb.append("           where ");
		sb.append("               bunrui1_cd = rk.bunrui1_cd ");
		sb.append("           and ");
		sb.append("               syohin_cd  = rk.syohin_cd ");
		sb.append("           and ");
		sb.append("               yuko_dt <= '").append(yukoDt).append("' ");
		sb.append("        ) ");
		sb.append("    ) ");
		
		return sb.toString();
	}
//↑↑2011.03.02 ADD T.Urano

//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (S)
	/**
	 * 登録対象商品の部門取得
	 * @return String 生成されたＳＱＬ
	 */
	public String getBumonCdSel(String bunrui1Cd){
		StringBuffer sb = new StringBuffer();
		
		sb.append("	select ");
		sb.append("		distinct daibunrui2_cd ");
		sb.append("	from ");
		sb.append("		r_syohin_taikei ");
		sb.append("	where bunrui1_cd = '").append(bunrui1Cd).append("' ");
	
		return sb.toString();
	}

	/**
	 * 青果部門内で指定した計量器呼出NOが登録されているかチェックするＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getKeiryokiSyohinYobidasiCntSql2(String yobidasiCd, String yukoDt, String masterDt, String daibunrui2Cd){

		StringBuffer sb = new StringBuffer();

		if (yukoDt == null || yukoDt.trim().equals("")) {
			yukoDt = masterDt;
		}

		sb.append("select");
		sb.append("    count(rk.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_keiryoki rk ");
		sb.append("where ");
		sb.append("    rk.syohin_yobidasi = '").append(yobidasiCd).append("' ");
		sb.append("and rk.bunrui1_cd in ");
		sb.append("		( ");
		sb.append("			select ");
		sb.append("				distinct bunrui1_cd ");
		sb.append("			from ");
		sb.append("				r_syohin_taikei ");
		sb.append("			where ");
		sb.append("				daibunrui2_cd = '").append(daibunrui2Cd).append("' ");
		sb.append("		) ");
		sb.append("and rk.delete_fg = '0' ");
		sb.append("and (");
		sb.append("        rk.yuko_dt > '").append(yukoDt).append("' ");
		sb.append("      or ");
		sb.append("        rk.yuko_dt >= ");
		sb.append("        ( ");
		sb.append("           select");
		sb.append("               max(yuko_dt) ");
		sb.append("           from ");
		sb.append("               r_keiryoki ");
		sb.append("           where ");
		sb.append("               bunrui1_cd = rk.bunrui1_cd ");
		sb.append("           and ");
		sb.append("               syohin_cd  = rk.syohin_cd ");
		sb.append("           and ");
		sb.append("               yuko_dt <= '").append(yukoDt).append("' ");
		sb.append("        ) ");
		sb.append("    ) ");
		
		return sb.toString();
	}
	
	/**
	 * 計量器IF部門か青果部門内で、指定した採番NOと同一の商品コード4～7桁の商品が
	 * 存在するかチェックするＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSyohinCdKetaCntSql(String yobidasiCd, String yukoDt, String masterDt, String daibunrui2Cd, List lstBunrui1Cd){

		StringBuffer sb = new StringBuffer();

		if (yukoDt == null || yukoDt.trim().equals("")) {
			yukoDt = masterDt;
		}

		sb.append("select ");
		sb.append("    count(rs.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_syohin rs ");
		sb.append("where ");
		sb.append("    substr(rs.syohin_cd, 4, 4) = '").append(yobidasiCd).append("' ");
		if(daibunrui2Cd.equals("03")){
			sb.append("and ( ");
			sb.append("        rs.syohin_cd like '02%' or ");
			sb.append("        rs.syohin_cd like '04%' ");
			sb.append("    ) ");
			sb.append("and rs.bunrui1_cd in  ");
			sb.append("    ( ");
			sb.append("        select distinct bunrui1_cd ");
			sb.append("        from r_syohin_taikei ");
			sb.append("        where daibunrui2_cd = '").append(daibunrui2Cd).append("' ");
			sb.append("    ) ");
		} else {
			sb.append("and rs.syohin_cd like '02%' ");
			sb.append("and rs.bunrui1_cd in (").append( SqlSupportUtil.createInString(lstBunrui1Cd.toArray())).append(") ");
		}
		sb.append("and rs.delete_fg = '0' ");
		sb.append("and ( ");
		sb.append("    rs.yuko_dt > '").append(yukoDt).append("' ");
		sb.append("    or rs.yuko_dt >=  ");
		sb.append("        ( ");
		sb.append("            select max(yuko_dt) ");
		sb.append("            from r_syohin ");
		sb.append("            where bunrui1_cd = rs.bunrui1_cd ");
		sb.append("            and syohin_cd = rs.syohin_cd ");
		sb.append("            and yuko_dt <= '").append(yukoDt).append("' ");
		sb.append("        ) ");
		sb.append("    ) ");
		
		return sb.toString();
	}
//2012.09.23 Y.Imai Add 【MM00158】青果部門内での重複チェック追加対応 (E)

	/**
	 * 商品コードの空番検索ＳＱＬの生成を行う
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanA07MissingSyohinCdSql(String bySikibetuNo){

		StringBuffer sb = new StringBuffer();
		
		sb.append(" select ");
		sb.append("     case when ");
		sb.append("         (select ");
		sb.append("             min(renban_cd) ");
		sb.append("         from ");
		sb.append("             r_saiban_a07 ");
		sb.append("         where ");
		sb.append("             by_sikibetu_no = '").append(bySikibetuNo).append("' ");
		sb.append("         and ");
		sb.append("             delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("         and ");
		sb.append("             jyotai_kanri_fg is not null ) ");
		sb.append("         > '0000' ");
		sb.append("     then '0000' ");
		sb.append("     else ");
		sb.append("     (select ");
		sb.append("         min(renban_cd+1) ");
		sb.append("     from ");
		sb.append("         r_saiban_a07 ");
		sb.append("     where ");
		sb.append("         renban_cd + 1 not in ");
		sb.append("         (select ");
		sb.append("             renban_cd ");
		sb.append("         from ");
		sb.append("             r_saiban_a07 ");
		sb.append("         where ");
		sb.append("             by_sikibetu_no = '").append(bySikibetuNo).append("' ");
		sb.append("         and ");
		sb.append("             delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("         and ");
		sb.append("             jyotai_kanri_fg is not null) ");
		sb.append("         and ");
		sb.append("             by_sikibetu_no = '").append(bySikibetuNo).append("' ");
		sb.append("     ) end  akiban ");
		sb.append("     from dual ");

		return sb.toString();
	}

	/**
	 * ディクショナリコントロール検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param String paramId
	 * @return String 生成されたＳＱＬ
	 */
	public String getDictionaryCtlSelectSql( String paramId )
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select ");
		sb.append(" parameter_tx ");
		sb.append(" from ");
		sb.append(" r_dictionary_control ");
		sb.append(" where ");
		sb.append(" subsystem_id = '" + mst000101_ConstDictionary.SUBSYSTEM_DIVISION + "' ");
		sb.append(" and parameter_id = '" + mst000101_ConstDictionary.SAIBAN_CD + "' ");
		sb.append(" and dictionary_id = '" + paramId + "' ");
		return sb.toString();
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
