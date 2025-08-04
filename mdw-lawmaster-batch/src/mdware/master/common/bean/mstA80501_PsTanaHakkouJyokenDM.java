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

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstA80501_PsTanaHakkouJyokenDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mstA80501_PsTanaHakkouJyokenDM()
	{
		super( "rbsite_ora");
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
		mstA80501_PsTanaHakkouJyokenBean bean = new mstA80501_PsTanaHakkouJyokenBean();
		bean.setIraiNo( encodingString(StringUtility.null2string(rest, "irai_no")));
		bean.setEntryKb( encodingString(StringUtility.null2string(rest, "entry_kb")));
		bean.setBumonCd( encodingString(StringUtility.null2string(rest, "bumon_cd")));
		bean.setBumonNm( encodingString(StringUtility.null2string(rest, "bumon_nm")));
		bean.setHinbanCdFrom( encodingString(StringUtility.null2string(rest, "from_hinban_cd")));
		bean.setHinbanNmFrom( encodingString(StringUtility.null2string(rest, "from_hinban_nm")));
		bean.setHinbanCdTo( encodingString(StringUtility.null2string(rest, "to_hinban_cd")));
		bean.setHinbanNmTo( encodingString(StringUtility.null2string(rest, "to_hinban_nm")));
		bean.setHinsyuCd( encodingString(StringUtility.null2string(rest, "hinsyu_cd")));
		bean.setHinsyuNm( encodingString(StringUtility.null2string(rest, "hinsyu_nm")));
		bean.setLineCd( encodingString(StringUtility.null2string(rest, "line_cd")));
		bean.setLineNm( encodingString(StringUtility.null2string(rest, "line_nm")));
		bean.setUnitCd( encodingString(StringUtility.null2string(rest, "unit_cd")));
		bean.setUnitNm( encodingString(StringUtility.null2string(rest, "unit_nm")));
		bean.setTenpocd( encodingString(StringUtility.null2string(rest, "tenpo_fukusu_cd")));
		bean.setTenpoNm( encodingString(StringUtility.null2string(rest, "tenpo_nm")));
		bean.setSendDT( encodingString(StringUtility.null2string(rest, "send_dt")));
		bean.setSofusakiKb( encodingString(StringUtility.null2string(rest, "ps_sofusaki_kb")));
		bean.setRequest_kb( encodingString(StringUtility.null2string(rest, "ps_arinasi_kb")));
		bean.setTanaJyoken( encodingString(StringUtility.null2string(rest, "daityo_kb")));
		bean.setTrPsTanaRequest( encodingString(StringUtility.null2string(rest, "tanawari_gai_kb")));
		bean.setKaiPageKb( encodingString(StringUtility.null2string(rest, "kai_page_kb")));
		bean.setByNo( encodingString(StringUtility.null2string(rest, "by_no")));
		bean.setTanadaiNb( encodingString(StringUtility.null2string(rest, "kobetsu_gondora_nb")));
		bean.setTanadaiNb1From( encodingString(StringUtility.null2string(rest, "from_gondora_1_nb")));
		bean.setTanadaiNb1To( encodingString(StringUtility.null2string(rest, "to_gondora_1_nb")));
		bean.setTanadaiNb2From( encodingString(StringUtility.null2string(rest, "from_gondora_2_nb")));
		bean.setTanadaiNb2To( encodingString(StringUtility.null2string(rest, "to_gondora_2_nb")));
		bean.setTanadaiNb3From( encodingString(StringUtility.null2string(rest, "from_gondora_3_nb")));
		bean.setTanadaiNb3To( encodingString(StringUtility.null2string(rest, "to_gondora_3_nb")));
		bean.setCommentTx( encodingString(StringUtility.null2string(rest, "comment_tx")));
		bean.setDeleteFg( encodingString(StringUtility.null2string(rest, "delete_fg")));
		bean.setInsertTs( encodingString(StringUtility.null2string(rest, "insert_ts")));
		bean.setInsertUserId( encodingString(StringUtility.null2string(rest, "insert_user_id")));
		bean.setUpdateTs( encodingString(StringUtility.null2string(rest, "update_ts")));
		bean.setUpdateUserId( encodingString(StringUtility.null2string(rest, "update_user_id")));

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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	   tptj.irai_no ");
		sb.append(",");
		sb.append("	   tptj.entry_kb ");
		sb.append(",");
		sb.append("	   tptj.bumon_cd ");
		sb.append(",");
		sb.append("	   tptj.from_hinban_cd ");
		sb.append(",");
		sb.append("	   tptj.to_hinban_cd ");
		sb.append(",");
		sb.append("	   tptj.hinsyu_cd ");
		sb.append(",");
		sb.append("	   tptj.line_cd ");
		sb.append(",");
		sb.append("	   tptj.unit_cd ");
		sb.append(",");
		sb.append("	   tptj.tenpo_fukusu_cd ");
		sb.append(",");
		sb.append("	   tptj.send_dt ");
		sb.append(",");
		sb.append("	   tptj.ps_sofusaki_kb ");
		sb.append(",");
		sb.append("	   tptj.ps_arinasi_kb ");
		sb.append(",");
		sb.append("	   tptj.daityo_kb ");
		sb.append(",");
		sb.append("	   tptj.tanawari_gai_kb ");
		sb.append(",");
		sb.append("	   tptj.kai_page_kb ");
		sb.append(",");
		sb.append("	   tptj.by_no ");
		sb.append(",");
		sb.append("	   tptj.kobetsu_gondora_nb ");
		sb.append(",");
		sb.append("	   tptj.from_gondora_1_nb ");
		sb.append(",");
		sb.append("	   tptj.to_gondora_1_nb ");
		sb.append(",");
		sb.append("	   tptj.from_gondora_2_nb ");
		sb.append(",");
		sb.append("	   tptj.to_gondora_2_nb ");
		sb.append(",");
		sb.append("	   tptj.from_gondora_3_nb ");
		sb.append(",");
		sb.append("	   tptj.to_gondora_3_nb ");
		sb.append(",");
		sb.append("	   tptj.comment_tx ");
		sb.append(",");
		sb.append("	   tptj.delete_fg ");
		sb.append(",");
		sb.append("	   tptj.insert_ts ");
		sb.append(",");
		sb.append("	   tptj.insert_user_id ");
		sb.append(",");
		sb.append("	   tptj.update_ts ");
		sb.append(",");
		sb.append("	   tptj.update_user_id ");
		sb.append(",");
		sb.append("	   bumon_rn.kanji_rn as bumon_nm ");
		sb.append(",");
		sb.append("	   from_hinban_rn.kanji_rn as from_hinban_nm ");
		sb.append(",");
		sb.append("	   to_hinban_rn.kanji_rn as to_hinban_nm ");
		sb.append(",");
		sb.append("	   hinsyu_rn.kanji_rn as hinsyu_nm ");
		sb.append(",");
		sb.append("	   line_rn.kanji_rn as line_nm ");
		sb.append(",");
		sb.append("	   unit_rn.kanji_rn as unit_nm ");
//		sb.append(",");
//		sb.append("	   rt.kanji_rn as tenpo_nm ");
		sb.append(",");
		sb.append("	   '' as tenpo_nm ");
		sb.append("from ");
		sb.append("	   tr_ps_tana_jyoken tptj ");
		sb.append("left join ");
		sb.append("    r_namectf bumon_rn ");
		sb.append(" on ");
		sb.append("	   bumon_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   bumon_rn.code_cd = tptj.bumon_cd ");
		sb.append(" and ");
		sb.append("	   bumon_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("left join ");
		sb.append("    r_namectf from_hinban_rn ");
		sb.append(" on ");
		sb.append("	   from_hinban_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   from_hinban_rn.code_cd = tptj.from_hinban_cd ");
		sb.append(" and ");
		sb.append("	   from_hinban_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("left join ");
		sb.append("    r_namectf to_hinban_rn ");
		sb.append(" on ");
		sb.append("	   to_hinban_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   to_hinban_rn.code_cd = tptj.to_hinban_cd ");
		sb.append(" and ");
		sb.append("	   to_hinban_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("left join ");
		sb.append("    r_namectf hinsyu_rn ");
		sb.append(" on ");
		sb.append("	   hinsyu_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   hinsyu_rn.code_cd = tptj.hinsyu_cd ");
		sb.append(" and ");
		sb.append("	   hinsyu_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("left join ");
		sb.append("    r_namectf line_rn ");
		sb.append(" on ");
		sb.append("	   line_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI4, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   line_rn.code_cd = tptj.line_cd ");
		sb.append(" and ");
		sb.append("	   line_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("left join ");
		sb.append("    r_namectf unit_rn ");
		sb.append(" on ");
		sb.append("	   unit_rn.syubetu_no_cd = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		sb.append(" and ");
		sb.append("	   unit_rn.code_cd = '").append(map.get("system_kb").toString()).append("' || tptj.unit_cd ");
		sb.append(" and ");
		sb.append("	   unit_rn.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		sb.append("left join ");
//		sb.append("    r_tenpo rt ");
//		sb.append(" on ");
//		sb.append("	   rt.tenpo_cd = '000' || substr(tptj.tenpo_fukusu_cd,1,3) ");
//		sb.append(" and ");
//		sb.append("	   rt.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		sb.append("where ");
		sb.append("	   tptj.irai_no = ").append(map.get("irai_no").toString()).append(" ");
		sb.append(" and ");
		sb.append("	   tptj.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

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

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mstA80501_PsTanaHakkouJyokenBean bean = (mstA80501_PsTanaHakkouJyokenBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("	   tr_ps_tana_jyoken ");
		sb.append(" ( ");
		sb.append("	   irai_no ");
		sb.append(",");
		sb.append("	   entry_kb ");
		sb.append(",");
		sb.append("	   bumon_cd ");
		sb.append(",");
		sb.append("	   from_hinban_cd ");
		sb.append(",");
		sb.append("	   to_hinban_cd ");
		sb.append(",");
		sb.append("	   hinsyu_cd ");
		sb.append(",");
		sb.append("	   line_cd ");
		sb.append(",");
		sb.append("	   unit_cd ");
		sb.append(",");
		sb.append("	   tenpo_fukusu_cd ");
		sb.append(",");
		sb.append("	   send_dt ");
		sb.append(",");
		sb.append("	   ps_sofusaki_kb ");
		sb.append(",");
		sb.append("	   ps_arinasi_kb ");
		sb.append(",");
		sb.append("	   daityo_kb ");
		sb.append(",");
		sb.append("	   tanawari_gai_kb ");
		sb.append(",");
		sb.append("	   kai_page_kb ");
		sb.append(",");
		sb.append("	   by_no ");
		sb.append(",");
		sb.append("	   kobetsu_gondora_nb ");
		sb.append(",");
		sb.append("	   from_gondora_1_nb ");
		sb.append(",");
		sb.append("	   to_gondora_1_nb ");
		sb.append(",");
		sb.append("	   from_gondora_2_nb ");
		sb.append(",");
		sb.append("	   to_gondora_2_nb ");
		sb.append(",");
		sb.append("	   from_gondora_3_nb ");
		sb.append(",");
		sb.append("	   to_gondora_3_nb ");
		sb.append(",");
		sb.append("	   comment_tx ");
		//削除フラグ
		sb.append(",");
		sb.append("	   delete_fg ");
		//作成年月日
		sb.append(",");
		sb.append("	   insert_ts ");
		sb.append(",");
		sb.append("	   insert_user_id ");
		sb.append(",");
		sb.append("	   update_ts ");
		sb.append(",");
		sb.append("	   update_user_id ");

		sb.append(")Values(");
		sb.append("'" + conv.convertString( bean.getIraiNo() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getEntryKb() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getBumonCd(),4,"0",true)) + "'");
		if( bean.getHinbanCdFrom() != null && bean.getHinbanCdFrom().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getHinbanCdFrom(),4,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getHinbanCdTo() != null && bean.getHinbanCdTo().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getHinbanCdTo(),4,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getHinsyuCd(),4,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getLineCd() != null && bean.getLineCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getLineCd(),4,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getUnitCd() != null && bean.getUnitCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getUnitCd(),4,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getTenpocd() != null && bean.getTenpocd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" +  conv.convertString(bean.getTenpocd()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getSendDT() != null && bean.getSendDT().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSendDT()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getSofusakiKb() != null && bean.getSofusakiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSofusakiKb()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getRequest_kb() != null && bean.getRequest_kb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getRequest_kb()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getTanaJyoken() != null && bean.getTanaJyoken().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getTanaJyoken()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getTrPsTanaRequest() != null && bean.getTrPsTanaRequest().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getTrPsTanaRequest()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getKaiPageKb() != null && bean.getKaiPageKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getKaiPageKb()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getByNo(),10,"0",true)) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb() != null && bean.getTanadaiNb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getTanadaiNb()) + "'");
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb1From() != null && bean.getTanadaiNb1From().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb1From()));
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb1To() != null && bean.getTanadaiNb1To().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb1To()));
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb2From() != null && bean.getTanadaiNb2From().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb2From()));
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb2To() != null && bean.getTanadaiNb2To().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb2To()));
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb3From() != null && bean.getTanadaiNb3From().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb3From()));
		} else {
			sb.append(", null ");
		}
		if( bean.getTanadaiNb3To() != null && bean.getTanadaiNb3To().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(conv.convertString(bean.getTanadaiNb3To()));
		} else {
			sb.append(", null ");
		}
		if( bean.getCommentTx() != null && bean.getCommentTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getCommentTx()) + "'");
		} else {
			sb.append(", null ");
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
		} else {
			sb.append(", null ");
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
		} else {
			sb.append(", null ");
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName() );
		mstA80501_PsTanaHakkouJyokenBean bean = (mstA80501_PsTanaHakkouJyokenBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("	update ");
		sb.append("		tr_ps_tana_jyoken ");
		sb.append(" set ");
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
		sb.append(" irai_no = ");
		sb.append(bean.getIraiNo() + " ");

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mstA80501_PsTanaHakkouJyokenBean bean = (mstA80501_PsTanaHakkouJyokenBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("	delete ");
		sb.append(" from ");
		sb.append("		tr_ps_tana_jyoken ");
		sb.append(" where ");
		sb.append(" irai_no = ");
		sb.append(bean.getIraiNo() + " ");
		sb.append("	and ");
		sb.append("	delete_fg = '" + mst000801_DelFlagDictionary.IRU.getCode() + "' ");

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
