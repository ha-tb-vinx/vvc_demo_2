/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * 
 * @author  kou  2006/9/22
 * @version 1.0 
 */
public class mst310101_TanpinTenToriatukaiDM2 extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst310101_TanpinTenToriatukaiDM2()
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
		mst310101_TanpinTenToriatukaiBean bean = new mst310101_TanpinTenToriatukaiBean();
		bean.setSentaku( "" );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoKanjiRn( rest.getString("tenpo_na") );
		bean.setDonyuStDt( rest.getString("donyu_st_dt") );
		bean.setNonActKb( rest.getString("non_act_kb") );
		bean.setHaseimotoKb( rest.getString("haseimoto_kb") );
		bean.setTanawariPatern( rest.getString("tanawari_patern") );
		bean.setJukiNb( rest.getString("juki_nb") );
		bean.setTanaNb( rest.getString("tana_nb") );
		bean.setFaceQt( rest.getString("face_qt") );
		bean.setMinTinretuQt( rest.getString("min_tinretu_qt") );
		bean.setMaxTinretuQt( rest.getString("max_tinretu_qt") );
		bean.setBaseZaikoNisuQt( rest.getString("base_zaiko_nisu_qt") );
		bean.setSentaku( rest.getString("sentaku") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNm( rest.getString("insert_user_nm") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserTs( rest.getString("update_user_id") );
		bean.setUpdateUserNm( rest.getString("update_user_nm") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
		bean.setHenkoDt( rest.getString("henko_dt") );
		bean.setDonyuStDtBefore( rest.getString("donyu_st_dt") );
		
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
		
		if (mst000101_ConstDictionary.PROCESS_INSERT.equals(map.get("mode"))) {
			// 新規
			sb.append(" SELECT ");
			sb.append(" 		'")
				.append(StringUtility.charFormat((String) map.get("bumon_cd"), 4, "0", true))
				.append("' BUMON_CD").append(", ");
			sb.append(" 		'")
				.append(((String)map.get("syohin_cd")).trim())
				.append("' SYOHIN_CD").append(", ");
			sb.append(" 		TRIM(TBL1.TENPO_CD) TENPO_CD").append(", ");
			sb.append("			TRIM(TBL1.KANJI_NA) tenpo_na").append(", ");
			sb.append("CASE WHEN TBL0.donyu_st_dt IS NOT NULL THEN TRIM(TBL0.donyu_st_dt) ")
				.append(" ELSE '").append(((String)map.get("yukodt")))
				.append("' END donyu_st_dt ");
			sb.append(", ");
			sb.append("TRIM(TBL0.non_act_kb) non_act_kb ");
			sb.append(", ");
			sb.append("TRIM(TBL0.haseimoto_kb) haseimoto_kb ");
			sb.append(", ");
			sb.append("TRIM(TBL0.tanawari_patern) tanawari_patern ");
			sb.append(", ");
			sb.append("TBL0.juki_nb juki_nb");
			sb.append(", ");
			sb.append("TBL0.tana_nb tana_nb");
			sb.append(", ");
			sb.append("TBL0.face_qt face_qt");
			sb.append(", ");
			sb.append("TBL0.min_tinretu_qt min_tinretu_qt");
			sb.append(", ");
			sb.append("TBL0.max_tinretu_qt max_tinretu_qt");
			sb.append(", ");
			sb.append("TBL0.base_zaiko_nisu_qt base_zaiko_nisu_qt");
			sb.append(", ");
			sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
			sb.append(", ");
			sb.append("TRIM(TBL0.insert_ts) insert_ts");
			sb.append(", ");
			sb.append("TRIM(TBL0.insert_user_id) insert_user_id");
			sb.append(", ");
			sb.append("	( ");
//			sb.append("		select ");
//			sb.append("			riyo_user_na ");
//			sb.append("		from ");
//			sb.append("			r_riyo_user ");
//			sb.append("		where ");
//			sb.append("			riyo_user_id = TBL0.insert_user_id ");
			sb.append("		select ");
			sb.append("			user_na ");
			sb.append("		from ");
			sb.append("			r_portal_user ");
			sb.append("		where ");
			sb.append("			user_id = TRIM(TBL0.insert_user_id) ");
			sb.append("	) AS insert_user_nm ");
			sb.append(", ");
			sb.append("TRIM(TBL0.update_ts) update_ts");
			sb.append(", ");
			sb.append("TRIM(TBL0.update_user_id) update_user_id");
			sb.append(", ");
			sb.append("	( ");
//			sb.append("		select ");
//			sb.append("			riyo_user_na ");
//			sb.append("		from ");
//			sb.append("			r_riyo_user ");
//			sb.append("		where ");
//			sb.append("			riyo_user_id = TBL0.update_user_id ");
			sb.append("		select ");
			sb.append("			user_na ");
			sb.append("		from ");
			sb.append("			r_portal_user ");
			sb.append("		where ");
			sb.append("			user_id = TRIM(TBL0.update_user_id) ");
			sb.append("	) AS update_user_nm ");
			sb.append(", ");
			sb.append("TRIM(TBL0.delete_fg) delete_fg ");
			sb.append(", ");
			sb.append("TBL0.sinki_toroku_dt sinki_toroku_dt ");
			sb.append(", ");
			sb.append("TBL0.henko_dt henko_dt ");
			sb.append("  from ");
			sb.append("		r_tenpo TBL1 LEFT JOIN r_tanpinten_toriatukai TBL0 ");
			sb.append("		ON ");
			sb.append("		       TBL1.tenpo_cd = TBL0.tenpo_cd");		
			sb.append("		 and   TBL0.bumon_cd = '")
				.append(StringUtility.charFormat((String) map.get("bumon_cd"), 4, "0", true))
				.append("' ");
			sb.append("		 and   TBL0.syohin_cd = '")
				.append(((String)map.get("syohin_cd")).trim()).append("' ");
			sb.append(" where ");
			sb.append("		  TBL1.tenpo_KB = '")
				.append(mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode()).append("'");
			sb.append("	  and TBL1.delete_fg = '0' ");
			sb.append(" order by ");
			sb.append("		TBL1.GYOTAI_KB, TBL0.tenpo_cd");
		} else {
			// 削除、照会
			sb.append("select ");
			sb.append("TRIM(TBL0.bumon_cd) bumon_cd ");
			sb.append(", ");
			sb.append("TRIM(TBL0.syohin_cd) syohin_cd ");
			sb.append(", ");
			sb.append("TRIM(TBL1.tenpo_cd) tenpo_cd ");
			sb.append(", ");
			sb.append("TRIM(TBL1.KANJI_NA) tenpo_na ");
			sb.append(", ");
			sb.append("TRIM(TBL0.donyu_st_dt) donyu_st_dt ");
			sb.append(", ");
			sb.append("TRIM(TBL0.non_act_kb) non_act_kb ");
			sb.append(", ");
			sb.append("TRIM(TBL0.haseimoto_kb) haseimoto_kb ");
			sb.append(", ");
			sb.append("TRIM(TBL0.tanawari_patern) tanawari_patern ");
			sb.append(", ");
			sb.append("TBL0.juki_nb juki_nb");
			sb.append(", ");
			sb.append("TBL0.tana_nb tana_nb");
			sb.append(", ");
			sb.append("TBL0.face_qt face_qt");
			sb.append(", ");
			sb.append("TBL0.min_tinretu_qt min_tinretu_qt");
			sb.append(", ");
			sb.append("TBL0.max_tinretu_qt max_tinretu_qt");
			sb.append(", ");
			sb.append("TBL0.base_zaiko_nisu_qt base_zaiko_nisu_qt");
			sb.append(", ");
			sb.append(	mst000101_ConstDictionary.RECORD_NON_EXISTENCE +     "   sentaku ");
			sb.append(", ");
			sb.append("TRIM(TBL0.insert_ts) insert_ts");
			sb.append(", ");
			sb.append("TRIM(TBL0.insert_user_id) insert_user_id");
			sb.append(", ");
			sb.append("	( ");
//			sb.append("		select ");
//			sb.append("			riyo_user_na ");
//			sb.append("		from ");
//			sb.append("			r_riyo_user ");
//			sb.append("		where ");
//			sb.append("			riyo_user_id = TBL0.insert_user_id ");
			sb.append("		select ");
			sb.append("			user_na ");
			sb.append("		from ");
			sb.append("			r_portal_user ");
			sb.append("		where ");
			sb.append("			user_id = TRIM(TBL0.insert_user_id) ");
			sb.append("	) AS insert_user_nm ");
			sb.append(", ");
			sb.append("TRIM(TBL0.update_ts) update_ts");
			sb.append(", ");
			sb.append("TRIM(TBL0.update_user_id) update_user_id");
			sb.append(", ");
			sb.append("	( ");
//			sb.append("		select ");
//			sb.append("			riyo_user_na ");
//			sb.append("		from ");
//			sb.append("			r_riyo_user ");
//			sb.append("		where ");
//			sb.append("			riyo_user_id = TBL0.update_user_id ");
			sb.append("		select ");
			sb.append("			user_na ");
			sb.append("		from ");
			sb.append("			r_portal_user ");
			sb.append("		where ");
			sb.append("			user_id = TRIM(TBL0.update_user_id) ");
			sb.append("	) AS update_user_nm ");
			sb.append(", ");
			sb.append("TRIM(TBL0.delete_fg) delete_fg ");
			sb.append(", ");
			sb.append("TBL0.sinki_toroku_dt sinki_toroku_dt ");
			sb.append(", ");
			sb.append("TBL0.henko_dt henko_dt ");
			sb.append("from r_tanpinten_toriatukai TBL0");
			sb.append(", ");
			sb.append("		r_tenpo TBL1 ");
			sb.append(" where ");
		
			sb.append("		  TBL1.tenpo_KB = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' ");
			sb.append(" and   TBL1.tenpo_cd = TBL0.tenpo_cd");		
			sb.append(" and   TBL1.delete_fg = '0' "); //
			sb.append(" and   TBL0.bumon_cd = '")
				.append(StringUtility.charFormat((String) map.get("bumon_cd"), 4, "0", true))
				.append("' ");
			sb.append(" and   TBL0.syohin_cd = '").append(((String)map.get("syohin_cd")).trim()).append("' ");
			if(map.get("tenpo_cd") != null && !"".equals(((String)map.get("tenpo_cd")).trim()))
			{
				sb.append("	 and   TBL0.tenpo_cd = '").append(((String)map.get("tenpo_cd")).trim()).append("'");		
			}
			else
			{
				sb.append(" and   TBL0.delete_fg = '0' ");

				sb.append(" order by ");
				sb.append("		TBL1.GYOTAI_KB ");
				sb.append(",");
				sb.append("		TBL0.tenpo_cd");
				sb.append(",");
				sb.append("		TBL0.donyu_st_dt");
			}
		
		}

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean aftKanma = false;
		mst310101_TanpinTenToriatukaiBean bean = (mst310101_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tanpinten_toriatukai (");
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 ){
//			befKanma = true;
//			sb.append(" hanku_cd");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 ){
			befKanma = true;
			sb.append(" bumon_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_st_dt");
		}
		if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_ed_dt");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" non_act_kb");
		}
//		↓↓2006.07.14xubqカスタマイズ修正↓↓
		if( befKanma ) sb.append(",");
		sb.append(" non_act_soshin_dt");
		sb.append(",");
		sb.append(" saishin_hacyu_dt");
		sb.append(",");
		sb.append(" saishin_shiire_dt");
		sb.append(",");
		sb.append(" saishin_uriage_dt");
		sb.append(",");
//		↑↑2006.07.14xubqカスタマイズ修正↑↑
		sb.append(" haseimoto_kb");
		sb.append(",");
		sb.append(" tanawari_patern");
		sb.append(",");
		sb.append(" juki_nb");
		sb.append(",");
		sb.append(" tana_nb");
		sb.append(",");
		sb.append(" face_qt");
		sb.append(",");
		sb.append(" min_tinretu_qt");
		sb.append(",");
		sb.append(" max_tinretu_qt");
		sb.append(",");
		sb.append(" base_zaiko_nisu_qt");
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 ){
			sb.append(",");
			sb.append(" henko_dt");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
			sb.append(",");
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			sb.append(",");
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 ){
			sb.append(",");
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			sb.append(",");
			sb.append(" delete_fg");
		}
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 ){
			sb.append(",");
			sb.append(" sinki_toroku_dt");
		}

		sb.append(")Values(");
//		↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 ){
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 ){
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
		}
//		↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDonyuStDt() ) + "'");
		}
		if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDonyuEdDt() ) + "'");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 ){
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
		}
//		↓↓2006.07.14xubqカスタマイズ修正↓↓
		if( aftKanma ) sb.append(",");
		sb.append(bean.getNonActSoshinDt()+",");
		sb.append(bean.getSaiShinHachuDt()+",");
		sb.append(bean.getSaishinShiireDt()  + ",");
		sb.append( bean.getSaishinUriageDt()  + ",");
//		↑↑2006.07.14xubqカスタマイズ修正↑↑
		sb.append(bean.getHaseimotoKb()  + ",");
		sb.append(bean.getTanawariPatern() + ",");
		sb.append( bean.getJukiNb()+",");
		sb.append( bean.getTanaNb()+",");
		sb.append( bean.getFaceQt()+",");
		sb.append( bean.getMinTinretuQt()+",");
		sb.append( bean.getMaxTinretuQt()+",");
		sb.append( bean.getBaseZaikoNisuQt());
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 ){
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean whereAnd = false;
		mst310101_TanpinTenToriatukaiBean bean = (mst310101_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tanpinten_toriatukai set ");
		if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_st_dt = ");
			sb.append("'" + conv.convertString( bean.getDonyuStDt() ) + "'");
		}
		if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_ed_dt = ");
			sb.append("'" + conv.convertString( bean.getDonyuEdDt() ) + "'");
		}
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" non_act_kb = ");
			sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
		}
//		↓↓2006.07.14 xubq カスタマイズ修正↓↓
//		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 ){
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" haseimoto_kb = ");
//			sb.append("'" + conv.convertString( bean.getHaseimotoKb() ) + "'");
//		}
//		if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 ){
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" tanawari_patern = ");
//			sb.append("'" + conv.convertString( bean.getTanawariPatern() ) + "'");
//		}
//		if( befKanma ) sb.append(",");
//		sb.append(" juki_nb = "+ bean.getJukiNb()+",");
//		sb.append(" tana_nb = "+ bean.getTanaNb()+",");
//		sb.append(" face_qt = "+ bean.getFaceQt()+",");
//		sb.append(" min_tinretu_qt = "+ bean.getMinTinretuQt()+",");
//		sb.append(" max_tinretu_qt = "+ bean.getMaxTinretuQt()+",");
//		sb.append(" base_zaiko_nisu_qt = "+ bean.getBaseZaikoNisuQt());
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ){
//			sb.append(",");
//			sb.append(" insert_ts = ");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ){
//			sb.append(",");
//			sb.append(" insert_user_id = ");
//			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
//		↑↑2006.07.14 xubq カスタマイズ修正↑↑
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
//		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 ){
//			sb.append(",");
//			sb.append(" sinki_toroku_dt = ");
//			sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
//		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 ){
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" henko_dt = ");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}

		sb.append(" WHERE");
//		↓↓2006.06.21 jiangyan カスタマイズ修正↓↓
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 ){
//			whereAnd = true;
//			sb.append(" hanku_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 ){
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(conv.convertWhereString( bean.getBumonCd() ), 4, "0", true)) + "' ");
		}
//		↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 ){
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}

		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 ){
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		return sb.toString();
	}
	

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql( Object beanMst )
	{
		mst310101_TanpinTenToriatukaiBean bean = (mst310101_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tanpinten_toriatukai where ");
//		↓↓2006.06.21 jiangyan カスタマイズ修正↓↓	
//		sb.append(" hanku_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" bumon_cd = ");
		sb.append("'" + mst000401_LogicBean.chkNullString(StringUtility.charFormat(conv.convertWhereString( bean.getBumonCd() ), 4, "0", true)) + "' ");
//		↑↑2006.06.21 jiangyan カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");	
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");		
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

//	↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
	public String getSiiresakiKanriCd(mst000101_DbmsBean db, String bumonCd, String syohinCd, String yukoDt) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		String tenSiiresakiKanriCd = "";

		sql.append("SELECT ");
		sql.append("	 rs.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd ");
		sql.append("FROM ");
		sql.append("	 r_syohin rs ");
		sql.append("WHERE ");
		sql.append("	 rs.bumon_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.syohin_cd = '").append(syohinCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '").append(yukoDt).append("') ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			if (rs.getString("ten_siiresaki_kanri_cd") != null) {
				tenSiiresakiKanriCd = rs.getString("ten_siiresaki_kanri_cd").trim();
			}
			rs.close();
		}

		return tenSiiresakiKanriCd;

	}

	public boolean isSiiresakiTenpo(mst000101_DbmsBean db, String systemKb, String bumonCd, String tenSiiresakiKanriCd, String tenpoCd) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		if (!systemKb.equals(mst000611_SystemKbDictionary.G.getCode()) && !systemKb.equals(mst000611_SystemKbDictionary.F.getCode())) {
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("9999")) {
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("")) {
			return false;
		}

		sql.append("SELECT ");
		sql.append("	 rts.tenpo_cd as tenpo_cd ");
		sql.append("FROM ");
		sql.append("	 r_tenbetu_siiresaki rts ");
		sql.append("WHERE ");
		sql.append("	 rts.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.kanri_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.ten_siiresaki_kanri_cd = '").append(tenSiiresakiKanriCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.tenpo_cd = '").append(tenpoCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			rs.close();
			return true;
		} else {
			return false;
		}

	}
//	↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
}
