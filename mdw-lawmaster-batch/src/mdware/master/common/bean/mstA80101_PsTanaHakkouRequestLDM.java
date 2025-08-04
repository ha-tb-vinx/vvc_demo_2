/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）TPS／棚割リクエストTRのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するPS／棚割リクエストTRのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）PS／棚割リクエストTRのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するPS／棚割リクエストTRのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
public class mstA80101_PsTanaHakkouRequestLDM extends DataModule
{
	private String syoriKb = null;// 処理状況
	/**
	 * コンストラクタ
	 */
	public mstA80101_PsTanaHakkouRequestLDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
	}

	/**
	 * コンストラクタ
	 */
	public mstA80101_PsTanaHakkouRequestLDM(String syoriKb)
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
		mstA80101_PsTanaHakkouRequestLBean bean = new mstA80101_PsTanaHakkouRequestLBean();
		if(mst000101_ConstDictionary.PROCESS_INSERT.equals(this.syoriKb)){
			// 登録データ作成時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setPsSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na")) );//商品名称
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setDaisiNoNb( encodingString(rest.getString("daisi_no_nb")) );// 台紙NO
			if(rest.getString("daisi_no_nb") != null
				&& "1".equals(((String)rest.getString("daisi_no_nb")).trim())) {
				if(rest.getString("rec_hinmei_kanji_na") != null
					&& rest.getString("rec_hinmei_kanji_na").length() > 14) {
						bean.setPsSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na").substring(0,14)) );//商品名称
				}
			} else if(rest.getString("daisi_no_nb") != null
						&& "2".equals(((String)rest.getString("daisi_no_nb")).trim())) {
				if(rest.getString("rec_hinmei_kanji_na") != null
					&& rest.getString("rec_hinmei_kanji_na").length() > 20) {
						bean.setPsSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na").substring(0,20)) );//商品名称
				}
			} else {
				bean.setDaisiNoNb("3");// 台紙NO
				if(rest.getString("rec_hinmei_kanji_na") != null
					&& rest.getString("rec_hinmei_kanji_na").length() > 23) {
						bean.setPsSyohinNa( encodingString(rest.getString("rec_hinmei_kanji_na").substring(0,23)) );//商品名称
				}
			}
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
		} else {
			// 作成データ照会、或いは、作成データ取消時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setSendDt( encodingString(rest.getString("send_dt")) );// 出力希望日
			bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na")) );// PS商品名
			bean.setDaisiNoNb( encodingString(rest.getString("daisi_no_nb")) );// 台紙NO
			bean.setPsMaisuQt( rest.getString("ps_maisu_qt") );// PS枚数
		}
		bean.setTenpoCd( encodingString(rest.getString("tenpo_cd")) );
		bean.setTenpoNm( encodingString(rest.getString("kanji_rn")) );
		bean.setUnitCd( encodingString(rest.getString("unit_cd")) );
		bean.setUnitNm( encodingString(rest.getString("unit_nm")) );
		bean.setSyohinCd( encodingString(rest.getString("syohin_cd")) );
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		bean.setHinbanCd( encodingString(rest.getString("hinban_cd")) );// 品番コード
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
		StringBuffer sq = new StringBuffer();
		StringBuffer nb1 = new StringBuffer();
		StringBuffer nb2 = new StringBuffer();
		StringBuffer nb3 = new StringBuffer();
		sb.append("select ");
		sb.append("	rt.send_dt ");
		sb.append("	, ");
		sb.append("	rt.tenpo_cd ");
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
		sb.append("	trim(rt.syohin_cd) as syohin_cd ");
		sb.append("	, ");
		sb.append("	rt.ps_syohin_na ");
		sb.append("	, ");
		sb.append("	trim(rt.daisi_no_nb) as daisi_no_nb ");
		sb.append("	, ");
		sb.append("	rt.ps_maisu_qt ");
		sb.append("from ");
		sb.append("	r_tenpo tp ,");
		sb.append("	r_syohin rs ");
		sb.append("left outer join ");
		sb.append("	r_namectf  rn ");
		sb.append("on ");
		sb.append("	rn.code_cd =  rs.system_kb||  rs.unit_cd ");
		sb.append("	and ");
		sb.append("	rn.syubetu_no_cd='" + MessageUtil.getMessage("0060", userLocal) +"' ");
		sb.append("	and ");
		sb.append("	rn.delete_fg ='"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");
		sb.append("	, ");
		sb.append("	( ");
		sb.append("		select ");
		sb.append("			max(tr.ps_syohin_na) ps_syohin_na ");
		sb.append("			, ");
		sb.append("			max(tr.daisi_no_nb) daisi_no_nb ");
		sb.append("			, ");
		sb.append("			max(tr.ps_maisu_qt ) ps_maisu_qt ");
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
		sb.append("			tr.ps_sofusaki_kb ");
		sb.append("			, ");
		sb.append("			tr.entry_kino_kb ");
		sb.append("		from ");
		sb.append("			tr_ps_tana_request tr ");
		sb.append("			, ");
		sb.append("			( ");

		//tanadaiNb_from に対するWHERE区
		//tanadaiNb_to に対するWHERE区
		if(map.get("tanadaiNb1From")!=null && map.get("tanadaiNb1From").toString().trim().length()!=0){

			nb1.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb1From") );
		}
		if(map.get("tanadaiNb1To")!=null && map.get("tanadaiNb1To").toString().trim().length()!=0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			nb1.append(" and ");
			if (nb1.length() >0){
				nb1.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb1.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb1To") );
		}
		if(map.get("tanadaiNb2From")!=null && map.get("tanadaiNb2From").toString().trim().length()!=0){
			nb2.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb2From"));
		}
		if(map.get("tanadaiNb2To")!=null && map.get("tanadaiNb2To").toString().trim().length()!=0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			nb2.append(" and ");
			if (nb2.length() >0){
				nb2.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb2.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb2To") );
		}
		if(map.get("tanadaiNb3From")!=null && map.get("tanadaiNb3From").toString().trim().length()!=0){
			nb3.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb3From"));
		}
		if(map.get("tanadaiNb3To")!=null && map.get("tanadaiNb3To").toString().trim().length()!=0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			nb3.append(" and ");
			if (nb3.length() >0){
				nb3.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb3.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb3To"));
		}

		if (nb1.length() >0){
			sq.append(" ( ");
			sq.append(nb1.toString());
			sq.append(" ) ");
		}
		if (sq.length() >0 && nb2.length() > 0){
			sq.append(" or ");
			sq.append(" ( ");
			sq.append(nb2.toString());
			sq.append(" ) ");
		}else if(sq.length() == 0 && nb2.length() > 0){
			sq.append(" ( ");
			sq.append(nb2.toString());
			sq.append(" ) ");
		}
		if (sq.length() >0 && nb3.length() > 0){
			sq.append(" or ");
			sq.append(" ( ");
			sq.append(nb3.toString());
			sq.append(" ) ");
		}else if(sq.length() == 0 && nb3.length() > 0){
			sq.append(" ( ");
			sq.append(nb3.toString());
			sq.append(" ) ");
		}

		sb.append("				select ");
		sb.append("					b.bumon_cd ");
		sb.append("					, ");
		sb.append("					b.syohin_cd ");
		sb.append("				from ");
		sb.append("					r_syohin_taikei  a ");
		sb.append("					, ");
		sb.append("					r_syohin b ");

		if (sq.length() >0){
			sb.append("					, ");
			sb.append("					r_tana_location h ");
		}

		sb.append("				where ");
		sb.append("					a.bumon_cd = b.bumon_cd ");
		sb.append("					and ");
		sb.append("					a.unit_cd = b.unit_cd ");
		sb.append("					and ");
		sb.append("					a.system_kb = '" + map.get("sysKb") + "' ");
		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd_from") != null && ((String)map.get("hinban_cd_from")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinban_cd >= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd_from") , 4, "0", true)) + "'");
		}
		if( map.get("hinban_cd_to") != null && ((String)map.get("hinban_cd_to")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinban_cd <= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd_to") , 4, "0", true)) + "'");
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


		if (sq.length() >0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("					and ");
//			sb.append("					h.bumon_cd = b.bumon_cd ");
//			sb.append("					and ");
//			sb.append("					h.keikaku_syohin_cd = b.syohin_cd ");
			sb.append("					and ");
			sb.append("					h.syohin_cd = b.syohin_cd ");
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			sb.append(" and ");
			sb.append(" ( ");
			sb.append(sq.toString());
			sb.append(" ) ");
		}

		sb.append("					and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		{
			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,'" + (String)map.get("send_dt") + "') ");
		} else {
			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
		}
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		if (sq.length() >0){
			sb.append("				group by ");
			sb.append("					b.bumon_cd ");
			sb.append("					, ");
			sb.append("					b.syohin_cd ");
		}
		sb.append("					) rs1 ");
		sb.append("				where ");
		sb.append("					tr.bumon_cd = rs1.bumon_cd ");
		sb.append("					and ");
		sb.append("					tr.syohin_cd = rs1.syohin_cd ");

		if(map.get("send_dt")!=null && map.get("send_dt").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.send_dt = '"+ map.get("send_dt") +"'  ");
		}

		if(map.get("tenpo_cd")!=null && map.get("tenpo_cd").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.tenpo_cd =  '"+conv.convertWhereString(StringUtility.charFormat( (String)map.get("tenpo_cd") , 6, "0", true)) + "'");
		}

		if((map.get("request_kb")!=null && map.get("request_kb").toString().trim().length()!=0) && !(map.get("request_kb_tan")!=null && map.get("request_kb_tan").toString().trim().length()!=0) ){
			sb.append(" and ");
			sb.append("     tr.ps_request_kb = '"+ map.get("request_kb") +"'  ");
		}
		if(!(map.get("request_kb")!=null && map.get("request_kb").toString().trim().length()!=0) && (map.get("request_kb_tan")!=null && map.get("request_kb_tan").toString().trim().length()!=0)){
			sb.append(" and ");
			sb.append("     tr.ps_request_kb = '"+ map.get("request_kb_tan") +"'  ");
		}
		if((map.get("request_kb")!=null && map.get("request_kb").toString().trim().length()!=0) && (map.get("request_kb_tan")!=null && map.get("request_kb_tan").toString().trim().length()!=0)){
			sb.append(" and ");
			sb.append("    ( tr.ps_request_kb = '"+ map.get("request_kb") +"'  ");
			sb.append(" or ");
			sb.append("     tr.ps_request_kb = '"+ map.get("request_kb_tan") +"')  ");
		}
		if(map.get("sofusakiKb")!=null && map.get("sofusakiKb").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.ps_sofusaki_kb = '"+ map.get("sofusakiKb") +"'  ");
		}

		if(map.get("by_no")!=null && map.get("by_no").toString().trim().length()!=0){
			sb.append(" and ");
			sb.append("     tr.by_no =  '"+conv.convertWhereString(StringUtility.charFormat( (String)map.get("by_no") , 10, "0", true)) + "'");
		}

		sb.append("					and ");
		sb.append("					tr.entry_kino_kb ='2' ");
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
		sb.append("					tr.ps_sofusaki_kb ");
		sb.append("					, ");
		sb.append("					tr.entry_kino_kb) rt ");
		sb.append("				where ");
		sb.append("					rs.bumon_cd = rt.bumon_cd ");
		sb.append("					and ");
		sb.append("					rs.syohin_cd = rt.syohin_cd ");
		sb.append("					and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		{
			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd,'" + (String)map.get("send_dt") + "')");
		} else {
			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
		}
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
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
		StringBuffer sq = new StringBuffer();
		StringBuffer nb1 = new StringBuffer();
		StringBuffer nb2 = new StringBuffer();
		StringBuffer nb3 = new StringBuffer();

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
		sb.append("	trim(ta.syohin_cd) as syohin_cd ");
		sb.append("	, ");
//		↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
		// POSレシート品名が空白の場合、商品の漢字品名で埋める
//		sb.append("	rs.rec_hinmei_kanji_na ");
		sb.append("	case when rs.rec_hinmei_kanji_na is null then rs.hinmei_kanji_na ");
		sb.append("	     when trim(rs.rec_hinmei_kanji_na) = '' then rs.hinmei_kanji_na ");
		sb.append("	     else rs.rec_hinmei_kanji_na end rec_hinmei_kanji_na ");
		sb.append("	, ");
		sb.append("	trim(rs.daisi_no_nb) as daisi_no_nb ");
//		↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑

		sb.append("from ");
		sb.append("	 r_tanpinten_toriatukai ta , ");
		sb.append("  r_tenpo tp, ");
		sb.append("	r_syohin rs ");
		sb.append("left outer join ");
		sb.append("	r_namectf  rn ");
		sb.append("on ");
		sb.append("	rn.code_cd =  rs.system_kb||  rs.unit_cd ");
		sb.append("	and ");
		sb.append("	rn.syubetu_no_cd='" + MessageUtil.getMessage("0060", userLocal) +"' ");
		sb.append("	and ");
		sb.append("	rn.delete_fg ='" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	, ");
		sb.append("	( ");

		//tanadaiNb_from に対するWHERE区
		//nadaiNb_to に対するWHERE区
		if(map.get("tanadaiNb1From")!=null && map.get("tanadaiNb1From").toString().trim().length()!=0){

			nb1.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb1From") );
		}
		if(map.get("tanadaiNb1To")!=null && map.get("tanadaiNb1To").toString().trim().length()!=0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			nb1.append(" and ");
			if (nb1.length() >0){
				nb1.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb1.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb1To") );
		}
		if(map.get("tanadaiNb2From")!=null && map.get("tanadaiNb2From").toString().trim().length()!=0){
			nb2.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb2From"));
		}
		if(map.get("tanadaiNb2To")!=null && map.get("tanadaiNb2To").toString().trim().length()!=0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			nb2.append(" and ");
			if (nb2.length() >0){
				nb2.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb2.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb2To") );
		}
		if(map.get("tanadaiNb3From")!=null && map.get("tanadaiNb3From").toString().trim().length()!=0){
			nb3.append("     to_number(h.tanadai_nb) >= "+ map.get("tanadaiNb3From"));
		}
		if(map.get("tanadaiNb3To")!=null && map.get("tanadaiNb3To").toString().trim().length()!=0){
//			nb3.append(" and ");
			if (nb3.length() >0){
				nb3.append(" and ");
			}
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			nb3.append("     to_number(h.tanadai_nb) <= "+ map.get("tanadaiNb3To"));
		}

		if (nb1.length() >0){
			sq.append(" ( ");
			sq.append(nb1.toString());
			sq.append(" ) ");
		}
		if (sq.length() >0 && nb2.length() > 0){
			sq.append(" or ");
			sq.append(" ( ");
			sq.append(nb2.toString());
			sq.append(" ) ");
		}else if(sq.length() == 0 && nb2.length() > 0){
			sq.append(" ( ");
			sq.append(nb2.toString());
			sq.append(" ) ");
		}
		if (sq.length() >0 && nb3.length() > 0){
			sq.append(" or ");
			sq.append(" ( ");
			sq.append(nb3.toString());
			sq.append(" ) ");
		}else if(sq.length() == 0 && nb3.length() > 0){
			sq.append(" ( ");
			sq.append(nb3.toString());
			sq.append(" ) ");
		}

		sb.append("				select ");
		sb.append("					b.bumon_cd ");
		sb.append("					, ");
		sb.append("					b.syohin_cd ");
		sb.append("				from ");
		sb.append("					r_syohin_taikei  a ");
		sb.append("					, ");
		sb.append("					r_syohin b ");
		if (sq.length() >0){
			sb.append("					, ");
			sb.append("					r_tana_location h ");
		}
		sb.append("				where ");
		sb.append("					a.bumon_cd = b.bumon_cd ");
		sb.append("					and ");
		sb.append("					a.unit_cd = b.unit_cd ");
		sb.append("					and ");
		sb.append("					a.system_kb = '" + map.get("sysKb") + "' ");
		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.bumon_cd = '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		}

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd_from") != null && ((String)map.get("hinban_cd_from")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinban_cd >= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd_from") , 4, "0", true)) + "'");
		}
		if( map.get("hinban_cd_to") != null && ((String)map.get("hinban_cd_to")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("a.hinban_cd <= '" +  conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd_to") , 4, "0", true)) + "'");
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


		if (sq.length() >0){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("					and ");
//			sb.append("					h.bumon_cd = b.bumon_cd ");
//			sb.append("					and ");
//			sb.append("					h.keikaku_syohin_cd = b.syohin_cd ");
			sb.append("					and ");
			sb.append("					h.syohin_cd = b.syohin_cd ");
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			sb.append(" and ");
			sb.append(" ( ");
			sb.append(sq.toString());
			sb.append(" ) ");
		}
		sb.append("					and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		{
			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,'" + (String)map.get("send_dt") + "') ");
		} else {
			sb.append("					b.yuko_dt = msp710101_getsyohinyukodt(b.bumon_cd,b.syohin_cd,cast(null as char)) ");
		}
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		if (sq.length() >0){
			sb.append("				group by ");
			sb.append("					b.bumon_cd ");
			sb.append("					, ");
			sb.append("					b.syohin_cd ");
		}
		sb.append("					) rs1 ");
		sb.append("				where ");

		sb.append("					rs.bumon_cd = rs1.bumon_cd ");
		sb.append("					and ");
		sb.append("					rs.syohin_cd = rs1.syohin_cd ");
		sb.append("					and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		{
			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd,'" + (String)map.get("send_dt") + "')");
		} else {
			sb.append("					rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd,rs.syohin_cd, cast(null as char ))");
		}
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append("					and ");
		sb.append("					ta.BUMON_CD = rs1.BUMON_CD and ");
		sb.append("					ta.SYOHIN_CD = rs1.SYOHIN_CD  ");
		if(map.get("tenpo_cd")!=null && map.get("tenpo_cd").toString().trim().length()!=0){
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
		mstA80101_PsTanaHakkouRequestLBean bean = (mstA80101_PsTanaHakkouRequestLBean)beanMst;
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
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//		//台帳出力区分：DAITYO_KB
//		if( bean.getDaityo_kb() != null && bean.getDaityo_kb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   daityo_kb ");
//		}
//		//依頼NO：IRAI_NO
//		if( bean.getIrai_no() != null && bean.getIrai_no().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   irai_no ");
//		}
//
//		if( bean.getPsSyohinNa() != null && bean.getPsSyohinNa().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   ps_syohin_na ");
//		}
//		if( bean.getKai_page_kb() != null && bean.getKai_page_kb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   kai_page_kb ");
//		}
//		if( bean.getPc_kb() != null && bean.getPc_kb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   pc_kb ");
//		}
//		if( bean.getDaisiNoNb() != null && bean.getDaisiNoNb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   daisi_no_nb ");
//		}
//
//		sb.append(",");
//		sb.append("	   ps_maisu_qt ");
//		if( bean.getBy_no() != null && bean.getBy_no().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   by_no ");
//		}
//		if( bean.getComment_tx() != null && bean.getComment_tx().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   comment_tx ");
//		}
//		//棚割外区分
//		if( bean.getTanawari_gai_kb() != null && bean.getTanawari_gai_kb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   tanawari_gai_kb ");
//		}
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		sb.append(",");
		sb.append("	   daityo_kb ");
		sb.append(",");
		sb.append("	   irai_no ");
		sb.append(",");
		sb.append("	   ps_syohin_na ");
		sb.append(",");
		sb.append("	   kai_page_kb ");
		sb.append(",");
		sb.append("	   pc_kb ");
		sb.append(",");
		sb.append("	   daisi_no_nb ");
		sb.append(",");
		sb.append("	   ps_maisu_qt ");
		sb.append(",");
		sb.append("	   by_no ");
		sb.append(",");
		sb.append("	   comment_tx ");
		sb.append(",");
		sb.append("	   tanawari_gai_kb ");

		//送信済区分
		if( bean.getSend_end_kb() != null && bean.getSend_end_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   send_end_kb ");
		}
		//削除フラグ
		sb.append(",");
		sb.append("	   delete_fg ");
		//作成年月日
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
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑


		sb.append(")Values(");
		sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getBumon_cd(),4,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		sb.append(",");
		sb.append("'" +  conv.convertString(StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getEntry_kb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getPs_request_kb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getPs_sofusaki_kb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getEntry_kino_kb() ) + "'");

		//台帳出力区分：DAITYO_KB
		if( bean.getDaityo_kb() != null && bean.getDaityo_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDaityo_kb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		//依頼NO：IRAI_NO
		if( bean.getIrai_no() != null && bean.getIrai_no().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getIrai_no() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getPsSyohinNa() != null && bean.getPsSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPsSyohinNa() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getKai_page_kb() != null && bean.getKai_page_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKai_page_kb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getPc_kb() != null && bean.getPc_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPc_kb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getDaisiNoNb() != null && bean.getDaisiNoNb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDaisiNoNb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		sb.append(",");
		sb.append(conv.convertString( bean.getPsMaisuQtString() ));
		if( bean.getBy_no() != null && bean.getBy_no().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertWhereString(StringUtility.charFormat( bean.getBy_no() , 10, "0", true)) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getComment_tx() != null && bean.getComment_tx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getComment_tx() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		//棚割外区分
		if( bean.getTanawari_gai_kb() != null && bean.getTanawari_gai_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTanawari_gai_kb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		//送信済区分
		if( bean.getSend_end_kb() != null && bean.getSend_end_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSend_end_kb() ) + "'");
		}
		sb.append(",");
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		sb.append(",");
        try {
        	sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
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

		sb.append(",");
        try {
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
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
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName() );
		mstA80101_PsTanaHakkouRequestLBean bean = (mstA80101_PsTanaHakkouRequestLBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("tr_ps_tana_request set ");
		sb.append(" update_ts = ");

        try {
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

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
		sb.append("'"+StringUtility.charFormat(bean.getBumon_cd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
		sb.append(" ps_request_kb = ");
		sb.append("'" + bean.getPs_request_kb()+ "'");
		sb.append(" and");
		sb.append(" ps_sofusaki_kb = ");
		sb.append("'" + bean.getPs_sofusaki_kb()+ "'");
		sb.append(" and");
		sb.append(" entry_kino_kb = ");
		sb.append("'" + bean.getEntry_kino_kb()+ "'");
		sb.append(" and");
		sb.append("	send_end_kb ='0' ");
		sb.append("	and ");
		sb.append("	delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mstA80101_PsTanaHakkouRequestLBean bean = (mstA80101_PsTanaHakkouRequestLBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("	delete ");
		sb.append(" from ");
		sb.append("		tr_ps_tana_request  ");
		sb.append(" where ");

		sb.append(" send_dt = ");
		sb.append("'" + bean.getSendDt()+ "'");
		sb.append(" and");
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumon_cd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
		sb.append(" entry_kb = ");
		sb.append("'" + bean.getEntry_kb()+ "'");
		sb.append(" and");
		sb.append(" ps_request_kb = ");
		sb.append("'" + bean.getPs_request_kb()+ "'");
		sb.append(" and");
		sb.append(" ps_sofusaki_kb = ");
		sb.append("'" + bean.getPs_sofusaki_kb()+ "'");
		sb.append(" and");
		sb.append(" entry_kino_kb = ");
		sb.append("'" + bean.getEntry_kino_kb()+ "'");
		sb.append("	and ");
		sb.append("	delete_fg = '"+mst000801_DelFlagDictionary.IRU.getCode() +"' ");

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
