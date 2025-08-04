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
import mdware.master.util.RMSTDATEUtil;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstB10101_TenpoKaTorokuSyokaiDM extends DataModule
{

	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mstB10101_TenpoKaTorokuSyokaiDM()
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
		mstB10101_TenpoKaTorokuSyokaiBean bean = new mstB10101_TenpoKaTorokuSyokaiBean();
		bean.setHinbanCd( rest.getLong("hinban_cd") );
		bean.setHinbanNa( encodingString(rest.getString("hinban_na")) );
		bean.setUnitCd( rest.getLong("unit_cd") );
		bean.setUnitNa( encodingString(rest.getString("unit_na")) );
		bean.setTenpoCd( rest.getLong("tenpo_cd") );
		bean.setJanCd(rest.getLong("jan_cd") );
		bean.setTanpinNa( encodingString(rest.getString("tanpin_na")) );
		bean.setKikakuNa( encodingString(rest.getString("kikaku_na")) );
		bean.setGenzaiTankaVl( rest.getLong("genzai_tanka_vl") );
		bean.setHyojunTankaVl( rest.getLong("hyojun_tanka_vl") );
		bean.setJisiDt("20" + rest.getString("jisi_dt") );
		bean.setTenpoNa( encodingString(rest.getString("tenpo_na")) );
		bean.setBaitankaVl( rest.getString("baitanka_vl") );
		bean.setSyohin2Cd(rest.getString("syohin_2_cd"));
		bean.setBumonCd( encodingString(rest.getString("bumon_cd")) );
		bean.setSyohinCd( encodingString(mst000401_LogicBean.chkNullString(rest.getString("syohin_cd"))) );
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
		if(rest.getString("rs_exists").equals("")){
			bean.setMessage("POSマスタなし");
		} else if(rest.getString("rtt_exists").equals("")){
			bean.setMessage("指示立てなし");
		}
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
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
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	it.hinban_cd ");
		sb.append("	, ");
		sb.append("	a.kanji_rn hinban_na ");
		sb.append("	, ");
		// ===BEGIN=== Modify by kou 2006/9/20
		//sb.append("	it.class_cd unit_cd ");
		sb.append("	substr(it.class_cd,3,4) unit_cd ");
		// ===END=== Modify by kou 2006/9/20
		sb.append("	, ");
		sb.append("	b.kanji_rn unit_na ");
		sb.append("	, ");
		sb.append("	it.tenpo_cd ");
		sb.append("	, ");
		sb.append("	it.pos_cd jan_cd ");
		sb.append("	, ");
		sb.append("	it.tanpin_na ");
		sb.append("	, ");
		sb.append("	it.kikaku_na ");
		sb.append("	, ");
		sb.append("	it.genzai_tanka_vl ");
		sb.append("	, ");
		sb.append("	it.hyojun_tanka_vl ");
		sb.append("	, ");
		sb.append("	lpad(to_char(it.jisi_nengapi_dt),6,'0') jisi_dt ");
		sb.append("	, ");
		sb.append("	rt.kanji_rn tenpo_na ");
		sb.append("	, ");
		sb.append("	rs.baitanka_vl ");
		sb.append("	, ");
		sb.append("	rs.syohin_2_cd");
		sb.append("	, ");
		sb.append("	rs.bumon_cd ");
		sb.append("	, ");
		sb.append("	rs.syohin_cd ");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	, ");
		sb.append("	coalesce(rs.bumon_cd,'') as rs_exists ");
		sb.append("	, ");
		sb.append("	coalesce(rtt.bumon_cd,'') as rtt_exists ");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from ");
		sb.append("	r_tenpo rt ");
		sb.append("	, ");
		sb.append("	int_tanpin_mentenance it ");
		sb.append("	left outer join ");
		sb.append("	r_syohin rs ");
		sb.append("on ");
		// ===BEGIN=== Modify by kou 2006/9/20
		//sb.append("	it.pos_cd = to_number(rs.syohin_2_cd) ");
		sb.append("	it.pos_cd = rs.syohin_2_cd ");
		// ===END=== Modify by kou 2006/9/20
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" and rs.system_kb = '" + conv.convertWhereString((String)map.get("system_kb")) + "'");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and rs.bumon_cd = '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true)) + "'");
		sb.append(" and ");
//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd, rs.syohin_cd,cast(null as char)) ");
		sb.append("rs.yuko_dt = ");
		sb.append("( ");
		sb.append("select ");
		sb.append("max(rsw.yuko_dt) ");
		sb.append("from ");
		sb.append("r_syohin rsw ");
		sb.append("where ");
		sb.append("rsw.bumon_cd = rs.bumon_cd ");
		sb.append(" and ");
		sb.append("rsw.syohin_cd = rs.syohin_cd ");
		sb.append(" and ");
		sb.append("rsw.yuko_dt <= '" + RMSTDATEUtil.getMstDateDt() + "' ");
		sb.append(") ");
		sb.append(" and ");
		sb.append("rs.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑
		// syohin_cd
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("lpad(trim(rs.syohin_cd),13,'0') = '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("syohin_cd") , 13, "0", true)) + "'");
		}
		// unit_cd
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			sb.append("rs.unit_cd = '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("unit_cd") , 4, "0", true)) + "'");
		}

//		↓↓2007.01.24 H.Yamamoto カスタマイズ修正↓↓
		sb.append("	left outer join ");
		sb.append(" r_tanpinten_toriatukai rtt ");
		sb.append("on ");
		sb.append(" rtt.bumon_cd = rs.bumon_cd ");
		sb.append("	and ");
		sb.append(" rtt.syohin_cd = rs.syohin_cd ");
		sb.append("	and ");
		sb.append(" rtt.tenpo_cd = it.tenpo_cd ");
//		↑↑2007.01.24 H.Yamamoto カスタマイズ修正↑↑

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append(" left outer join ");
		sb.append("	r_namectf a ");
		sb.append("on ");
		sb.append("	a.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal)  + "'");
		sb.append("	and ");
		// ===BEGIN=== Modify by kou 2006/9/20
		//sb.append("	a.code_cd = lpad(to_char(it.hinban_cd),4,'0') ");
		sb.append("	a.code_cd = it.hinban_cd ");
		// ===END=== Modify by kou 2006/9/20
		sb.append("	and ");
		sb.append("	a.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append(" left outer join ");
		sb.append("	r_namectf b ");
		sb.append("on ");
		sb.append("	b.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "'");
		sb.append("	and ");
		// ===BEGIN=== Modify by kou 2006/9/20
		//sb.append("	b.code_cd = '" + (String)map.get("system_kb") + "'" + " || lpad(to_char(it.class_cd),4,'0') ");
		sb.append("	b.code_cd = '")
			.append(map.get("system_kb"))
			.append("' || substr(it.class_cd,3,4)" ) ;
		// ===END=== Modify by kou 2006/9/20
		sb.append("	and ");
		sb.append("	b.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		sb.append(" where ");
		// ===BEGIN=== Modify by kou 2006/9/20
		//sb.append("to_number(rt.tenpo_cd) = it.tenpo_cd");
		sb.append(" rt.tenpo_cd = it.tenpo_cd");
		// ===END=== Modify by kou 2006/9/20

		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			// ===BEGIN=== Modify by kou 2006/9/20
			//sb.append("it.hinban_cd = " +  Integer.parseInt((String)map.get("hinban_cd")));
			sb.append("it.hinban_cd = '0' || '").append(map.get("hinban_cd")).append("'");
			// ===END=== Modify by kou 2006/9/20
		}

		//tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			// ===BEGIN=== Modify by kou 2006/9/20
			//sb.append("it.tenpo_cd = " +  Integer.parseInt((String)map.get("tenpo_cd")));
			sb.append("it.tenpo_cd = '000' || '").append(map.get("tenpo_cd")).append("'");
			// ===END=== Modify by kou 2006/9/20
		}
		//unit_cd に対するWHERE区
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
			sb.append(" and ");
			// ===BEGIN=== Modify by kou 2006/9/20
			//sb.append("it.class_cd = " + Integer.parseInt((String)map.get("unit_cd")));
			sb.append("it.class_cd = '00' || '").append(map.get("unit_cd")).append("'");
			// ===END=== Modify by kou 2006/9/20
		}
		sb.append(" and ");
		sb.append("rt.delete_fg = '0'");

		sb.append(" order by ");
		sb.append("it.hinban_cd,");
		sb.append("it.class_cd,");
		sb.append("it.tenpo_cd,");
		sb.append("it.pos_cd,");
		sb.append("it.jisi_nengapi_dt");

		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param bumon_cd  String
	 * @param syohin_cd String
	 * @param tenpo_cd  String
	 * @return String 生成されたＳＱＬ
	 */
	public String getCountSql( String bumon_cd,String syohin_cd,String tenpo_cd)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" 	count(*) as cnt");
		sb.append(" from ");
		sb.append(" 	r_tanpinten_toriatukai rt ");
		sb.append(" where ");
		sb.append("     rt.bumon_cd = " + bumon_cd);
		sb.append(" and ");
		sb.append("     rt.syohin_cd = " + syohin_cd);
		sb.append(" and ");
		sb.append("     rt.delete_fg = '0'");
		sb.append(" and ");
		sb.append(" rt.tenpo_cd = '" + StringUtility.charFormat( tenpo_cd , 6, "0", true) + "'");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param bumon_cd String
	 * @param unit_cd String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSystemKbSql(String bumon_cd, String unit_cd)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" 	system_kb");
		sb.append(" from ");
		sb.append(" 	r_syohin_taikei ");
		sb.append(" where ");
		sb.append("     bumon_cd = '" + StringUtility.charFormat( bumon_cd , 4, "0", true) + "'");
		sb.append(" and ");
		sb.append("     unit_cd = '" + StringUtility.charFormat( unit_cd , 4, "0", true) + "'");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param bumon_cd String
	 * @param syohin_cd String
	 * @return String 生成されたＳＱＬ
	 */
	public String getSyohinNmSql(String bumon_cd, String syohin_cd)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" 	hinmei_kanji_na ");
		sb.append(" from ");
		sb.append(" 	r_syohin ");
		sb.append(" where ");
		sb.append("     bumon_cd = '" + StringUtility.charFormat( bumon_cd , 4, "0", true) + "'");
		sb.append(" and ");
		sb.append("     lpad(trim(syohin_cd),13,'0') = '" + syohin_cd + "'");
		sb.append(" and ");
		sb.append("     yuko_dt = msp710101_getsyohinyukodt(bumon_cd, syohin_cd,cast(null as char)) ");
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
