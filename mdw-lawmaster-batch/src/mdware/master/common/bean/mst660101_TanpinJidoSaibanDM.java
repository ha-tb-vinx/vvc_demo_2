package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/** * <p>タイトル: 新ＭＤシステム（マスター管理）単品自動採番枠登録画面のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品自動採番枠登録画面のDMクラス</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator for SQL (2005.03.31) Version 1.0.rbsite
 * @version X.X (Create time: 2004/8/16 19:26:37)
 */
public class mst660101_TanpinJidoSaibanDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst660101_TanpinJidoSaibanDM()
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
		mst660101_TanpinJidoSaibanBean bean = new mst660101_TanpinJidoSaibanBean();
		bean.setHinsyuKanjiNa( rest.getString("hinsyu_kanji_na") );
		bean.setHankuKanjiNa( rest.getString("hanku_kanji_na") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setStartTanpinCd( mst000401_LogicBean.formatZero(rest.getString("start_tanpin_cd"), 3) );
		bean.setEndTanpinCd( mst000401_LogicBean.formatZero(rest.getString("end_tanpin_cd"), 3) );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );

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

		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		if ( map.get("h_hinsyu_cd") != null && (conv.convertWhereString( (String)map.get("h_hinsyu_cd") )).trim().length() > 0 ) {
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			//品種コードが入力されている時の処理
			sb.append("select ");
			sb.append("	 hinsyu_cd, ");
			sb.append("	 hanku_cd, ");
			sb.append("	 rst1.kanji_na as hinsyu_kanji_na, ");
			sb.append("	 rst2.kanji_na as hanku_kanji_na, ");
			sb.append("	 start_tanpin_cd, ");
			sb.append("	 end_tanpin_cd, ");
			sb.append("	 insert_ts, ");
			sb.append("	 insert_user_id, ");
			sb.append("	 update_ts, ");
			sb.append("	 update_user_id ");
			sb.append("from ");
			sb.append("	 ((r_syohin_taikei ");
			sb.append("	 right join ");
			sb.append("  (select ");
			sb.append("     syubetu_no_cd, ");
			sb.append("     code_cd, ");
			sb.append("     kanji_na ");
			sb.append("   from ");
			sb.append("	    r_namectf ");
			sb.append("   where ");
			sb.append("     syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' " );
			sb.append("   ) rst1 ");
			sb.append("   on r_syohin_taikei.hinsyu_cd = rst1.code_cd) ");
			sb.append("  right join ");
			sb.append("  (select ");
			sb.append("     syubetu_no_cd, ");
			sb.append("     code_cd, ");
			sb.append("     kanji_na ");
			sb.append("   from ");
			sb.append("	    r_namectf ");
			sb.append("   where ");
			sb.append("     syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' " );
			sb.append("   ) rst2 ");
			sb.append("    on r_syohin_taikei.hanku_cd = rst2.code_cd) ");
			sb.append("  left join ");
			sb.append("  (select ");
			sb.append("     hinsyu_cd, ");
			sb.append("     start_tanpin_cd, ");
			sb.append("     end_tanpin_cd, ");
			sb.append("     insert_ts, ");
			sb.append("     insert_user_id, ");
			sb.append("     update_ts, ");
			sb.append("     update_user_id ");
			sb.append("   from ");
			sb.append("     r_saiban_waku_hinsyu ");
			sb.append("  ) rswh ");
			sb.append("   on r_syohin_taikei.hinsyu_cd = rswh.hinsyu_cd ");
			sb.append(whereStr);
			sb.append("    hinsyu_cd >= '" + conv.convertWhereString( (String)map.get("h_hinsyu_cd") ) + "' ");
			sb.append("  or ");
			sb.append("    hanku_cd = ");
			sb.append("    (select ");
			sb.append("       hanku_cd ");
			sb.append("     from ");
			sb.append("       r_syohin_taikei ");
			sb.append("     where ");
			sb.append("       hinsyu_cd = '" + conv.convertWhereString( (String)map.get("h_hinsyu_cd") ) + "' ");
			sb.append("     ) ");
			sb.append("order by ");
			sb.append("	hinsyu_cd, hanku_cd ");

		}
		if ( map.get("h_hanku_cd") != null && (conv.convertWhereString( (String)map.get("h_hanku_cd") )).trim().length() > 0 ) {
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			//販区コードが入力されている時の処理
			sb.append("select ");
			sb.append("  rst1.hanku_cd, ");
			sb.append("  rst1.hanku_kanji_na, ");
			sb.append("  rst1.hinsyu_cd, ");
			sb.append("  rst1.hinsyu_kanji_na, ");
			sb.append("  rswh.start_tanpin_cd, ");
			sb.append("  rswh.end_tanpin_cd, ");
			sb.append("  rswh.insert_user_id, ");
			sb.append("  rswh.insert_ts, ");
			sb.append("  rswh.update_user_id, ");
			sb.append("  rswh.update_ts ");
			sb.append("from ");
			sb.append("	 (select ");
			sb.append("		rst2.*, ");
			sb.append("     rst_parent.kanji_na as hanku_kanji_na, ");
			sb.append("     rst_parent2.kanji_na as hinsyu_kanji_na, ");
			sb.append("		dense_rank()over(order by rst2.hanku_cd ) as rank_no ");
			sb.append("	  from ");
			sb.append("		r_syohin_taikei rst2 ");
			sb.append("		inner join (select ");
			sb.append("						* ");
			sb.append("					from ");
			sb.append("						r_namectf rn ");
			sb.append("					where ");
			sb.append("						rn.code_cd >= '" + conv.convertWhereString( (String)map.get("h_hanku_cd") ) + "' ");
			sb.append("						and ");
			sb.append("						rn.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
			sb.append("					) rst_parent on rst2.hanku_cd = rst_parent.code_cd ");
			sb.append("		inner join (select ");
			sb.append("						* ");
			sb.append("					from ");
			sb.append("						r_namectf rn ");
			sb.append("					where ");
			sb.append("						rn.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' " );
			sb.append("					) rst_parent2 on rst2.hinsyu_cd = rst_parent2.code_cd ");
			sb.append("	 ) rst1 ");
			sb.append("	 left join ( select ");
			sb.append("					 hinsyu_cd, ");
			sb.append("					 start_tanpin_cd, ");
			sb.append("					 end_tanpin_cd, ");
			sb.append("					 insert_ts, ");
			sb.append("					 insert_user_id, ");
			sb.append("					 update_ts, ");
			sb.append("					 update_user_id ");
			sb.append("				 from ");
			sb.append("					 r_saiban_waku_hinsyu ");
			sb.append("				) rswh on rst1.hinsyu_cd = rswh.hinsyu_cd ");
			sb.append("where ");
			sb.append("	 rank_no = 1 ");
			sb.append("order by ");
			sb.append("	 rst1.hanku_cd, rst1.hinsyu_cd ");

		}

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst660101_TanpinJidoSaibanBean bean = ( mst660101_TanpinJidoSaibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("  r_saiban_waku_hinsyu ");
		sb.append("values( ");
		sb.append("  '" + bean.getHinsyuCheckCd() + "', ");
		sb.append("  '" + bean.getStartTanpinCd() + "', ");
		sb.append("  '" + bean.getEndTanpinCd() + "', ");
		sb.append("  to_char(sysdate,'YYYYMMDDHH24MISS'), ");
		sb.append("  '" + bean.getInsertUserId() + "', ");
		sb.append("  to_char(sysdate,'YYYYMMDDHH24MISS'), ");
		sb.append("  '" + bean.getUpdateUserId() + "', ");
		sb.append("  '0' ");
		sb.append(")");

//		System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst660101_TanpinJidoSaibanBean bean = ( mst660101_TanpinJidoSaibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("  r_saiban_waku_hinsyu ");
		sb.append("set ");
		sb.append("  start_tanpin_cd = '" + bean.getStartTanpinCd() + "' ");
		sb.append("  , ");
		sb.append("  end_tanpin_cd = '" + bean.getEndTanpinCd() + "' ");
		sb.append("  , ");
		sb.append("  update_user_id = '" + bean.getUpdateUserId() + "' ");
		sb.append("  , ");
		sb.append("  update_ts = ");
		sb.append("  to_char(sysdate,'YYYYMMDDHH24MISS') ");
		sb.append("where ");
		sb.append("  hinsyu_cd = '" + bean.getHinsyuCheckCd() + "' ");

//		System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		return null;
	}
}