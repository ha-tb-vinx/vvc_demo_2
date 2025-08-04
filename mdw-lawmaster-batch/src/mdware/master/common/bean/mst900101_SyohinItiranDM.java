/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius koyama
 * @version 1.0(2005/03/22)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius koyama
 * @version 1.0(2005/03/22)初版作成
 */
public class mst900101_SyohinItiranDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst900101_SyohinItiranDM()
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
		mst900101_SyohinItiranBean bean = new mst900101_SyohinItiranBean();
		bean.setHachuNo( rest.getString("hachu_no") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setUrikuCd( rest.getString("uriku_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setNouhinbiDt( rest.getString("nouhinbi_dt") );
		bean.setGenkaVl( rest.getString("genka_vl") );
		bean.setBaikaVl( rest.getString("baika_vl") );
		bean.setHachuQt( rest.getString("hachu_qt") );
		bean.setJyotaiKb( rest.getString("jyotai_kb"));
//		bean.setJyotaiFrsKb( rest.getString("jyotai_frs_kb"));
		
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

		//--------------------
		//SQLの作成
		//--------------------
		StringBuffer sb = new StringBuffer();
		sb.append("select  ");
		sb.append("	hansoku.hachu_no as hachu_no ");
		sb.append("	,  ");
		sb.append("	hansoku.tenpo_cd as tenpo_cd ");
		sb.append("	,  ");
		sb.append("	hansoku.uriku_cd as uriku_cd ");
		sb.append("	,  ");
		sb.append("	hansoku.tenhanku_cd as tenhanku_cd ");
		sb.append("	,  ");
		sb.append("	hansoku.siiresaki_cd as siiresaki_cd ");
		sb.append("	,  ");
		sb.append("	hansoku.syohin_cd as syohin_cd ");
		sb.append("	,  ");
		sb.append("	hansoku.nouhinbi_dt as nouhinbi_dt ");
		sb.append("	,  ");
		sb.append("	hansoku.genka_vl as genka_vl ");
		sb.append("	,  ");
		sb.append("	hansoku.baika_vl as baika_vl ");
		sb.append("	,  ");
		sb.append("	hansoku.hachu_qt as hachu_qt ");
		if (!map.get("gyosyu_kb").equals("04")) {
			sb.append("	,  ");
			sb.append("	bat1.syohin_cd as jyotai_kb ");
		} else {
			sb.append("	,  ");
			sb.append("	bat2.syohin_cd as jyotai_kb ");
		}
		
//		sb.append("	( select ");
//		sb.append("		count(*) ");
//		sb.append("	  from  ");
//		sb.append("		bat_hachu_master hachu ");
//		sb.append("	  where ");
//		sb.append("		hansoku.tenpo_cd = hachu.tenpo_cd and ");
//		sb.append("		hansoku.tenhanku_cd = hachu.tenhanku_cd and ");
//		sb.append("		hansoku.syohin_cd = hachu.syohin_cd and ");
//		sb.append("		hansoku.denpatu_dt = '20051003' ");
//		sb.append("	) as jyotai_kb ");
//		sb.append("	,  ");
//		sb.append("	( select ");
//		sb.append("		count(*) ");
//		sb.append("	  from  ");
//		sb.append("		bat_hachu_master_frs hachuf ");
//		sb.append("	  where ");
//		sb.append("		hansoku.tenpo_cd = hachuf.tenpo_cd and ");
//		sb.append("		hansoku.tenhanku_cd = hachuf.tenhanku_cd and ");
//		sb.append("		hansoku.syohin_cd = hachuf.syohin_cd and ");
//		sb.append("		hansoku.nouhinbi_dt = hachuf.nohin_dt ");
////		sb.append("		hansoku.bin_kb = hachuf.bin_kb ");
//		sb.append("	) as jyotai_frs_kb ");

		sb.append("from ");
		sb.append("	dt_eob_hansoku hansoku ");
		
		if (!map.get("gyosyu_kb").equals("04")) {		
			sb.append(" left join ( ");
			sb.append("		select ");
			sb.append("			tenpo_cd, ");
			sb.append("			tenhanku_cd, ");
			sb.append("			syohin_cd, ");
			sb.append("			hachu_dt ");
			sb.append("	from ");
			sb.append("		bat_hachu_master ");
			sb.append("	) bat1 ");
			sb.append("		on  hansoku.tenpo_cd = bat1.tenpo_cd and ");
			sb.append("			hansoku.tenhanku_cd = bat1.tenhanku_cd and ");
			sb.append("			hansoku.syohin_cd = bat1.syohin_cd and ");
			sb.append("			hansoku.denpatu_dt = bat1.hachu_dt ");
		} else {
			sb.append(" left join ( ");
			sb.append("		select ");
			sb.append("			tenpo_cd, ");
			sb.append("			tenhanku_cd, ");
			sb.append("			syohin_cd, ");
			sb.append("			nohin_dt ");
			sb.append("	from ");
			sb.append("		bat_hachu_master_frs ");
			sb.append("	) bat2 ");
			sb.append("		on  hansoku.tenpo_cd = bat2.tenpo_cd and ");
			sb.append("			hansoku.tenhanku_cd = bat2.tenhanku_cd and ");
			sb.append("			hansoku.syohin_cd = bat2.syohin_cd and ");
			sb.append("			hansoku.nouhinbi_dt = bat2.nohin_dt ");
		}
		
		sb.append("where ");
		sb.append("	hansoku.denpatu_dt = '" + conv.convertWhereString( (String)map.get("denpatu_dt") ) + "' ");
		if (map.get("uriku_cd") != null && ((String)map.get("uriku_cd")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	hansoku.uriku_cd = '" + conv.convertWhereString( (String)map.get("uriku_cd") ) + "' ");
		}
		if (map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	hansoku.tenhanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "' ");
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
