package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst006301_SendEndKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）PS／棚割リクエストTRのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するPS／棚割リクエストTRのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author fanglh
 * @version 1.0(2006/07/12)初版作成
 */
public class mstB20101_PsRequestA07LDM  extends DataModule
{
	private String syoriKb = null;// 処理状況
	/**
	 * コンストラクタ
	 */
	public mstB20101_PsRequestA07LDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
	}

	/**
	 * コンストラクタ
	 */
	public mstB20101_PsRequestA07LDM(String syoriKb)
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
		this.syoriKb = syoriKb;
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
		mstB20201_PsRequestA07LBean bean = new mstB20201_PsRequestA07LBean();
		if(mst000101_ConstDictionary.PROCESS_INSERT.equals(this.syoriKb)){
			// 登録データ作成時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na")) );//商品名称
			// ===BEGIN=== Add by kou 2006/10/2
			if(rest.getString("pc_kb") != null
				&& "1".equals(((String)rest.getString("pc_kb")).trim()))
			{
				if(rest.getString("rec_hinmei_kanji_na") != null
					&& rest.getString("rec_hinmei_kanji_na").length() > 18) {
						bean.setSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na").substring(0,18)) );//商品名称
				}
			} else {
				if(rest.getString("rec_hinmei_kanji_na") != null
					&& rest.getString("rec_hinmei_kanji_na").length() > 12) {
						bean.setSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na").substring(0,12)) );//商品名称
				}
			}
			// ===END=== Add by kou 2006/10/2
			bean.setPcKb( encodingString(rest.getString("pc_kb")) );//PC発行区分
		} else {
			// 作成データ照会、或いは、作成データ取消時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setSendDt( encodingString(rest.getString("send_dt")) );// 出力希望日
			bean.setSyohinNa( encodingString(rest.getString("ps_syohin_na")) );// PS商品名
			bean.setPsMaisuQt( rest.getLong("ps_maisu_qt") );// PS枚数
			bean.setEntryKb( rest.getString("entry_kb") );// 登録区分
			bean.setEntryKinoKb( rest.getString("entry_kino_kb") );// 登録機能区分
			bean.setUpdateTs( rest.getString("update_ts") );// 登録区分
		}
		bean.setTenpoCd( encodingString(rest.getString("tenpo_cd")) );
		bean.setTenpoNm( encodingString(rest.getString("kanji_rn")) );
		bean.setUnitCd( encodingString(rest.getString("unit_cd")) );
		bean.setUnitNm( encodingString(rest.getString("unit_nm")) );
		bean.setSyohinCd( encodingString(rest.getString("syohin_cd")) );
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		bean.setHinbanCd( encodingString(rest.getString("hinban_cd")) );
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
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
		String sql = "";
		if (mst000101_ConstDictionary.PROCESS_INSERT.equals(map.get("syori_kb"))){
			//登録データ作成時用ＳＱＬ
			sql = getSelectInsertSql(map);
		}else{
			//作成データ照会、或いは、作成データ取消時用ＳＱＬ
			sql = getSelectViewSql(map);
		}
		return sql;
	}

	/**
	 * 照会検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectViewSql( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	rt.send_dt ");
		sb.append("	, ");
		sb.append("	rt.tenpo_cd ");
		sb.append("	, ");
		sb.append("	tp.kanji_rn ");
		sb.append("	, ");
		sb.append("	rs.unit_cd ");
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rs.hinban_cd as hinban_cd ");
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	rn.kanji_rn as unit_nm ");
		sb.append("	, ");
		sb.append("	trim(rt.syohin_cd) as syohin_cd ");
		sb.append("	, ");
		sb.append("	rt.ps_syohin_na ");
		sb.append("	, ");
		sb.append("	rt.ps_maisu_qt ");
		sb.append("	, ");
		sb.append("	rt.update_ts ");
		sb.append("	, ");
		sb.append("	rt.entry_kb ");
		sb.append("	, ");
		sb.append("	rt.entry_kino_kb ");
		sb.append("from ");
		sb.append("	r_tenpo tp ,");
		sb.append("	r_syohin rs ");
		sb.append("left outer join ");
		sb.append("	r_namectf  rn ");
		sb.append("on ");
		sb.append("	rn.code_cd =  rs.system_kb||  rs.unit_cd ");
		sb.append("	and ");
		sb.append("	rn.syubetu_no_cd='"+ MessageUtil.getMessage("0060", userLocal) +"' ");
		sb.append("	and ");
		sb.append("	rn.delete_fg ='"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");
		sb.append("	, ");
		sb.append("	( ");
		sb.append("		select ");
		sb.append("			max(tr.ps_syohin_na) ps_syohin_na ");
		sb.append("			, ");
		sb.append("			max(tr.ps_maisu_qt ) ps_maisu_qt ");
		sb.append("			, ");
		sb.append("			max(tr.update_ts ) update_ts ");
		sb.append("			, ");
		sb.append("			tr.send_dt ");
		sb.append("			, ");
		sb.append("			tr.bumon_cd ");
		sb.append("			, ");
		sb.append("			tr.syohin_cd ");
		sb.append("			, ");
		sb.append("			tr.tenpo_cd ");
		sb.append("			, ");
		sb.append("			tr.entry_kb ");
		sb.append("			, ");
		sb.append("			tr.entry_kino_kb ");
		sb.append("		from ");
		sb.append("			tr_ps_tana_request tr ");
		sb.append("			, ");
		sb.append("			( ");
		sb.append("				select ");
		sb.append("					b.bumon_cd ");
		sb.append("					, ");
		sb.append("					b.syohin_cd ");
		sb.append("				from ");
		sb.append("					r_syohin_taikei  a ");
		sb.append("					, ");
		sb.append("					r_syohin b ");
		sb.append("				where ");
		sb.append("					a.bumon_cd = b.bumon_cd ");
		sb.append("					and ");
		sb.append("					a.unit_cd = b.unit_cd ");
		sb.append("					and ");
		sb.append("					a.system_kb = '" + map.get("systemKb") + "' ");
		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			// ===BEGIN=== Modify by kou 2006/10/2
			//sb.append("a.hinban_cd <= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)) + "'");
			sb.append("a.hinban_cd = '")
				.append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)))
				.append("'");
			// ===END=== Modify by kou 2006/10/2
		}

		//hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinsyu_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinsyu_cd") , 4, "0", true)) + "'");
		}

		//line_cd に対するWHERE区
		if( map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.line_cd = '" + conv.convertWhereString( (String)map.get("line_cd") ) + "'");
		}

		//unit_cd に対するWHERE区
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.unit_cd = '" + conv.convertWhereString( (String)map.get("unit_cd") ) + "'");
		}

		//tanadaiNb_from に対するWHERE区
		//tanadaiNb_to に対するWHERE区
		if(map.get("ct_tanawaribangoufm")!=null && map.get("ct_tanawaribangoufm").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     b.tana_no_nb >= "+ conv.convertWhereString( (String)map.get("ct_tanawaribangoufm")) );
		}
		if(map.get("ct_tanawaribangouto")!=null && map.get("ct_tanawaribangouto").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     b.tana_no_nb <= "+ conv.convertWhereString( (String)map.get("ct_tanawaribangouto")));
		}
		sb.append("					and ");
//		↓↓2006.10.31 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,'" + (String)map.get("send_dt") + "') ");
//		} else {
//			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
//		}
////		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append("           b.yuko_dt = (");
		sb.append(" select ");
		sb.append(" max(b2.yuko_dt) ");
		sb.append(" from ");
		sb.append(" r_syohin  b2 ");
		sb.append(" where ");
		sb.append(" b.bumon_cd = b2.bumon_cd ");
		sb.append(" and ");
		sb.append(" b.syohin_cd = b2.syohin_cd ");
		sb.append(" and ");
		sb.append(" b2.yuko_dt <= '").append((String)map.get("send_dt")).append("' ");
		sb.append(" ) ");
		sb.append(" and ");
		sb.append(" b.delete_fg = '0' ");
//		↑↑2006.10.31 H.Yamamoto カスタマイズ修正↑↑
		sb.append("					) rs1 ");
		sb.append("				where ");
		sb.append("					tr.bumon_cd = rs1.bumon_cd ");
		sb.append("					and ");
		sb.append("					tr.syohin_cd = rs1.syohin_cd ");

		if(map.get("send_dt")!=null && map.get("send_dt").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.send_dt = '"+ map.get("send_dt") +"'  ");
		}

		if(map.get("tenpo_cd")!=null && map.get("tenpo_cd").toString().trim().length()!=0 && !"000".equals(map.get("tenpo_cd"))){
			sb.append(" and ");
			sb.append("     tr.tenpo_cd =  '"+conv.convertWhereString(StringUtility.charFormat( (String)map.get("tenpo_cd") , 6, "0", true)) + "'");
		}

		if(map.get("by_no")!=null && map.get("by_no").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.by_no =  '"+conv.convertWhereString(StringUtility.charFormat( (String)map.get("by_no") , 10, "0", true)) + "'");
		}

		sb.append("					and ");
		sb.append("					tr.entry_kino_kb ='4' ");
		sb.append("					and ");
		sb.append("					tr.send_end_kb ='0' ");
		sb.append("					and ");
		sb.append("					tr.delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");
		sb.append("				group by ");
		sb.append("					tr.send_dt ");
		sb.append("					, ");
		sb.append("					tr.bumon_cd ");
		sb.append("					, ");
		sb.append("					tr.syohin_cd ");
		sb.append("					, ");
		sb.append("					tr.tenpo_cd ");
		sb.append("					, ");
		sb.append("					tr.entry_kb ");
		sb.append("					, ");
		sb.append("					tr.entry_kino_kb) rt ");
		sb.append("				where ");
		sb.append("					rs.bumon_cd = rt.bumon_cd ");
		sb.append("					and ");
		sb.append("					rs.syohin_cd = rt.syohin_cd ");
		sb.append("					and ");
//		↓↓2006.10.31 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd,'" + (String)map.get("send_dt") + "')");
//		} else {
//			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
//		}
////		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append("           rs.yuko_dt = (");
		sb.append(" select ");
		sb.append(" max(b3.yuko_dt) ");
		sb.append(" from ");
		sb.append(" r_syohin  b3 ");
		sb.append(" where ");
		sb.append(" rs.bumon_cd = b3.bumon_cd ");
		sb.append(" and ");
		sb.append(" rs.syohin_cd = b3.syohin_cd ");
		sb.append(" and ");
		sb.append(" b3.yuko_dt <= '").append((String)map.get("send_dt")).append("' ");
		sb.append(" ) ");
		sb.append(" and ");
		sb.append(" rs.delete_fg = '0' ");
//		↑↑2006.10.31 H.Yamamoto カスタマイズ修正↑↑
		sb.append("					and ");
		sb.append("					tp.tenpo_cd = rt.tenpo_cd ");
		sb.append("		order by ");
		sb.append("			rt.tenpo_cd ");
		sb.append("			, ");
		sb.append("			rs.unit_cd ");
		sb.append("			, ");
		sb.append("			rt.syohin_cd ");

		return sb.toString();
	}

	/**
	 * 登録検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectInsertSql( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("	ta.tenpo_cd ");
		sb.append("	, ");
		sb.append(" tp.kanji_rn ");
		sb.append("	, ");
		sb.append("	rs.unit_cd ");
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rs.hinban_cd as hinban_cd ");
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	, ");
		sb.append("	rn.kanji_rn as unit_nm ");
		sb.append("	, ");
		sb.append("	trim(ta.syohin_cd) as syohin_cd	 ");
		sb.append("	, ");
		// ===BEGIN=== Add by kou 2006/10/2
		// POSレシート品名が空白の場合、商品の漢字品名で埋める
		//sb.append("	rs.rec_hinmei_kanji_na ");
		sb.append("	case when rs.rec_hinmei_kanji_na is null then rs.hinmei_kanji_na ");
		sb.append("	     when trim(rs.rec_hinmei_kanji_na) = '' then rs.hinmei_kanji_na ");
		sb.append("	     else rs.rec_hinmei_kanji_na end rec_hinmei_kanji_na ");
		// ===END=== Add by kou 2006/10/2
		sb.append("	, ");
		sb.append("	rs.pc_kb ");
		sb.append("from ");
		sb.append("	 r_tanpinten_toriatukai ta , ");
		sb.append("  r_tenpo tp, ");
		sb.append("	r_syohin rs ");
		sb.append("left outer join ");
		sb.append("	r_namectf  rn ");
		sb.append("on ");
		sb.append("	rn.code_cd =  rs.system_kb||  rs.unit_cd ");
		sb.append("	and ");
		sb.append("	rn.syubetu_no_cd='"+ MessageUtil.getMessage("0060", userLocal) +"' ");
		sb.append("	and ");
		sb.append("	rn.delete_fg ='" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	, ");
		sb.append("	( ");
		sb.append("				select ");
		sb.append("					b.bumon_cd ");
		sb.append("					, ");
		sb.append("					b.syohin_cd ");
		sb.append("				from ");
		sb.append("					r_syohin_taikei  a ");
		sb.append("					, ");
		sb.append("					r_syohin b ");
		sb.append("				where ");
		sb.append("					a.bumon_cd = b.bumon_cd ");
		sb.append("					and ");
		sb.append("					a.unit_cd = b.unit_cd ");
		sb.append("					and ");
		sb.append("					a.system_kb = '" + map.get("systemKb") + "' ");
		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			// ===BEGIN=== Modify by kou 2006/10/2
			//sb.append("a.hinban_cd <= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)) + "'");
			sb.append("a.hinban_cd = '")
				.append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true)))
				.append("'");
			// ===END=== Modify by kou 2006/10/2
		}

		//hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinsyu_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinsyu_cd") , 4, "0", true)) + "'");
		}

		//line_cd に対するWHERE区
		if( map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.line_cd = '" + conv.convertWhereString( (String)map.get("line_cd") ) + "'");
		}

		//unit_cd に対するWHERE区
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.unit_cd = '" + conv.convertWhereString( (String)map.get("unit_cd") ) + "'");
		}

		//tanadaiNb_from に対するWHERE区
		//tanadaiNb_to に対するWHERE区
		if(map.get("ct_tanawaribangoufm")!=null && map.get("ct_tanawaribangoufm").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     b.tana_no_nb >= "+ conv.convertWhereString( (String)map.get("ct_tanawaribangoufm")) );
		}
		if(map.get("ct_tanawaribangouto")!=null && map.get("ct_tanawaribangouto").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     b.tana_no_nb <= "+ conv.convertWhereString( (String)map.get("ct_tanawaribangouto")) );
		}
		sb.append("					and ");
//		↓↓2006.10.31 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,'" + (String)map.get("send_dt") + "') ");
//		} else {
//			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
//		}
////		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append("           b.yuko_dt = (");
		sb.append(" select ");
		sb.append(" max(b2.yuko_dt) ");
		sb.append(" from ");
		sb.append(" r_syohin  b2 ");
		sb.append(" where ");
		sb.append(" b.bumon_cd = b2.bumon_cd ");
		sb.append(" and ");
		sb.append(" b.syohin_cd = b2.syohin_cd ");
		sb.append(" and ");
		sb.append(" b2.yuko_dt <= '").append((String)map.get("send_dt")).append("' ");
		sb.append(" ) ");
		sb.append(" and ");
		sb.append(" b.delete_fg = '0' ");
//		↑↑2006.10.31 H.Yamamoto カスタマイズ修正↑↑
		sb.append("					) rs1 ");
		sb.append("				where ");
		sb.append("					rs.bumon_cd = rs1.bumon_cd ");
		sb.append("					and ");
		sb.append("					rs.syohin_cd = rs1.syohin_cd ");
		sb.append("					and ");
//		↓↓2006.10.31 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
////		sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd,'" + (String)map.get("send_dt") + "')");
//		} else {
//			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
//		}
////		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append("           rs.yuko_dt = (");
		sb.append(" select ");
		sb.append(" max(b3.yuko_dt) ");
		sb.append(" from ");
		sb.append(" r_syohin  b3 ");
		sb.append(" where ");
		sb.append(" rs.bumon_cd = b3.bumon_cd ");
		sb.append(" and ");
		sb.append(" rs.syohin_cd = b3.syohin_cd ");
		sb.append(" and ");
		sb.append(" b3.yuko_dt <= '").append((String)map.get("send_dt")).append("' ");
		sb.append(" ) ");
		sb.append(" and ");
		sb.append(" rs.delete_fg = '0' ");
//		↑↑2006.10.31 H.Yamamoto カスタマイズ修正↑↑
		sb.append("					and ");
		sb.append("					ta.bumon_cd = rs1.bumon_cd and ");
		sb.append("					ta.syohin_cd = rs1.syohin_cd  ");
		if(map.get("tenpo_cd")!=null && map.get("tenpo_cd").toString().trim().length()!=0&& !"000".equals(map.get("tenpo_cd"))){
			sb.append(" and ");
			sb.append("     ta.tenpo_cd =  '"+conv.convertWhereString(StringUtility.charFormat( (String)map.get("tenpo_cd") , 6, "0", true)) + "'");
		}
		sb.append(" and ");
		sb.append("					ta.delete_fg = '"+ mst000801_DelFlagDictionary.INAI.getCode() + "'  ");
		sb.append(" and ");
		sb.append("					tp.tenpo_cd = ta.tenpo_cd ");
		sb.append("		order by ");
		sb.append("			ta.tenpo_cd ");
		sb.append("			, ");
		sb.append("			rs.unit_cd ");
		sb.append("			, ");
		sb.append("			ta.syohin_cd ");
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mstB20201_PsRequestA07LBean bean = (mstB20201_PsRequestA07LBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("	   tr_ps_tana_request ");
		sb.append(" ( ");
		sb.append("	   send_dt ");
		sb.append(" , ");
		sb.append("	   bumon_cd ");
		sb.append(" , ");
		sb.append("	   syohin_cd ");
		sb.append(" , ");
		sb.append("	   tenpo_cd ");
		sb.append(" , ");
		sb.append("	   entry_kb ");
		sb.append(" , ");
		sb.append("	   ps_request_kb ");
		sb.append(" , ");
		sb.append("	   ps_sofusaki_kb ");
		sb.append(" , ");
		sb.append("	   entry_kino_kb ");
		sb.append(" , ");
		sb.append("	   daityo_kb ");

//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   ps_syohin_na ");
//		}
//
//		sb.append(",");
//		sb.append("	   kai_page_kb ");
//
//		if( bean.getPcKb() != null && bean.getPcKb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   pc_kb ");
//		}
//		sb.append(",");
//		sb.append("	   ps_maisu_qt ");
//		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   by_no ");
//		}
		sb.append(",");
		sb.append("	   ps_syohin_na ");
		sb.append(",");
		sb.append("	   kai_page_kb ");
		sb.append(",");
		sb.append("	   pc_kb ");
		sb.append(",");
		sb.append("	   ps_maisu_qt ");
		sb.append(",");
		sb.append("	   by_no ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑

		sb.append(",");
		sb.append("	   tanawari_gai_kb ");
		sb.append(",");
		sb.append("	   send_end_kb ");
		sb.append(",");
		sb.append("	   delete_fg ");
		sb.append(",");
		sb.append("	   insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(",");
		sb.append(" update_user_id");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
////		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
//		//品番コード
//		if( bean.getHinbanCd() != null && bean.getHinbanCd().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   hinban_cd ");
//		}
////		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		sb.append(",");
		sb.append("	   hinban_cd ");
		sb.append(",");
		sb.append("	   irai_no ");
		sb.append(",");
		sb.append("	   daisi_no_nb ");
		sb.append(",");
		sb.append("	   comment_tx ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		sb.append(")Values(");
		sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getBumonCd(),4,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		sb.append(",");
		sb.append("'" +  conv.convertString(StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getEntryKb() ) + "'");
		sb.append(",");
		sb.append("'1'");
		sb.append(",");
		sb.append("'1'");
		sb.append(",");
		sb.append("'4'");
		sb.append(",");
		sb.append("'0'");

		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyohinNa() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		sb.append(",");
		sb.append("'0'");
		if( bean.getPcKb() != null && bean.getPcKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPcKb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		sb.append(",");
		sb.append(conv.convertString( bean.getPsMaisuQtString() ));

		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertWhereString(StringUtility.charFormat( bean.getByNo() , 10, "0", true)) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		sb.append(",");
		sb.append("'0'");
		sb.append(",");
		sb.append("'0'");
		sb.append(",");
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		if( bean.getHinbanCd() != null && bean.getHinbanCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHinbanCd() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		sb.append(", null ");
		sb.append(", null ");
		sb.append(", null ");
		sb.append(")");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName() );
		mstB20201_PsRequestA07LBean bean = (mstB20201_PsRequestA07LBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("tr_ps_tana_request set ");
		sb.append(" update_ts = ");
		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
        sb.append(",");
		sb.append(" update_user_id = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		sb.append(",");
		sb.append(" delete_fg = ");
		sb.append("'" +mst000801_DelFlagDictionary.IRU.getCode() + "'");
		sb.append(" where ");
		sb.append(" send_dt = ");
		sb.append("'" + bean.getSendDt()+ "'");
		sb.append(" and");
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
		sb.append(" entry_kino_kb = '4'");
		sb.append(" and");
		sb.append("	send_end_kb = ");
		sb.append("'" + mst006301_SendEndKbDictionary.MISOUSIN.getCode()+ "'");
		sb.append("	and ");
		sb.append("	delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");
		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 ) {
			sb.append("	and ");
			sb.append(" by_no = ");
			sb.append("'"+StringUtility.charFormat(bean.getByNo(),10,"0",true) + "'");
		}

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mstB20201_PsRequestA07LBean bean = (mstB20201_PsRequestA07LBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("tr_ps_tana_request where ");
		sb.append(" send_dt = ");
		sb.append("'" + bean.getSendDt()+ "'");
		sb.append(" and");
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
		sb.append(" entry_kb = '0'");
		sb.append(" and");
		sb.append(" ps_request_kb = '1'");
		sb.append(" and");
		sb.append(" ps_sofusaki_kb = '1'");
		sb.append(" and");
		sb.append(" entry_kino_kb = '4'");
		return sb.toString();
	}

	/**
	 * 更新年月日ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectUpdates( Map map )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" 	delete_fg, ");
		sb.append(" 	update_ts ");
		sb.append(" 	from ");
		sb.append(" 	tr_ps_tana_request ");
		sb.append(" 	where ");
		sb.append(" send_dt     = '" + conv.convertWhereString( (String)map.get("send_dt") ) +"' ");
		sb.append(" and bumon_cd = '" +  conv.convertWhereString( (String)map.get("bumon_cd") )+ "' ");
		sb.append(" and syohin_cd = '" +  conv.convertWhereString( (String)map.get("syohin_cd") ) + "' ");
		sb.append(" and tenpo_cd = '" +  conv.convertWhereString( (String)map.get("tenpo_cd") )+ "' ");
		sb.append(" and entry_kino_kb = '" +   conv.convertWhereString( (String)map.get("entry_kino_kb") )+ "' ");

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
