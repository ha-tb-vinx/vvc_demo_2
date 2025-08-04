/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/02/23)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/02/23)初版作成
 * @version 1.1(2005/12/26)管理コードまたは、仕入先コードだけで検索できるように変更する。
 * @version 1.2(2006/01/24)業種選択画面で選択された業種コードを検索条件に追加する。
 * @version 1.3(2006/01/27)速度改善のため、名称ＣＴＦマスタの当て方を変更する。
 * @version 1.1(2006/03/29)課金種別コード　追加 JINNO
 */
public class mst430101_SiiresakiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst430101_SiiresakiDM()
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
		mst430101_SiiresakiBean bean = new mst430101_SiiresakiBean();
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		↓↓仕様変更（2005.12.26）↓↓
//		bean.setKanriKb( mst000401_LogicBean.chkNullString(rest.getString("kanri_kb")).trim() );
//		bean.setKanriCd( mst000401_LogicBean.chkNullString(rest.getString("kanri_cd")).trim() );
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
//		bean.setKanriNm( rest.getString("kanri_nm") );
		bean.setBumonCd( rest.getString("kanri_cd") );
//		bean.setBumonNm( rest.getString("bumon_nm") );
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑		
//		↑↑仕様変更（2005.12.26）↑↑
		bean.setSiiresakiCd( mst000401_LogicBean.chkNullString(rest.getString("siiresaki_cd")).trim() );
		bean.setKanjiNa( mst000401_LogicBean.chkNullString(rest.getString("kanji_na")).trim() );
		bean.setKanaNa( rest.getString("kana_na") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanaRn( rest.getString("kana_rn") );
		bean.setYubinCd( mst000401_LogicBean.chkNullString(rest.getString("yubin_cd")).trim() );
		bean.setAddressKanji1Na( mst000401_LogicBean.chkNullString(rest.getString("address_kanji1_na")).trim() );
		bean.setAddressKanji2Na( rest.getString("address_kanji2_na") );
		bean.setAddressKana1Na( rest.getString("address_kana1_na") );
		bean.setAddressKana2Na( rest.getString("address_kana2_na") );
		bean.setMadoguchiTantoNa( rest.getString("madoguchi_tanto_na") );
		bean.setTel1Na( mst000401_LogicBean.chkNullString(rest.getString("tel1_na")).trim() );
		bean.setTel2Na( rest.getString("tel2_na") );
		bean.setFax1Na( rest.getString("fax1_na") );
		bean.setFax2Na( rest.getString("fax2_na") );
		bean.setNightTelNa( rest.getString("night_tel_na") );
		bean.setEmailNa( rest.getString("email_na") );
		bean.setJohosyoriSeikyuKb( rest.getString("johosyori_seikyu_kb") );
		bean.setSyhincdPrintKb( rest.getString("syhincd_print_kb") );
		bean.setTaghakoGyosyaKb( rest.getString("taghako_gyosya_kb") );
		bean.setHinbanbetuDenpyoKb( rest.getString("hinbanbetu_denpyo_kb") );
		bean.setMemoTx( rest.getString("memo_tx") );
		bean.setTosanKb( rest.getString("tosan_kb") );
		bean.setInsertTs( mst000401_LogicBean.chkNullString(rest.getString("insert_ts")).trim() );
		bean.setInsertTsShort( mst000401_LogicBean.chkNullString(rest.getString("insert_ts_short")).trim() );
		bean.setInsertUserId( mst000401_LogicBean.chkNullString(rest.getString("insert_user_id")).trim() );
		bean.setInsertUserNm( mst000401_LogicBean.chkNullString(rest.getString("insert_user_name")).trim() );
		bean.setUpdateTs( mst000401_LogicBean.chkNullString(rest.getString("update_ts")).trim() );
		bean.setUpdateTsShort( mst000401_LogicBean.chkNullString(rest.getString("update_ts_short")).trim() );
		bean.setUpdateUserId( mst000401_LogicBean.chkNullString(rest.getString("update_user_id")).trim() );
		bean.setUpdateUserNm( mst000401_LogicBean.chkNullString(rest.getString("update_user_name")).trim() );
		bean.setDeleteFg( rest.getString("delete_fg") );
		
//		↓↓2006.06.15 guohy カスタマイズ修正↓↓		
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		bean.setKakinSyubetuNa( rest.getString("kakin_syubetu_na") );
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑		
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
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		↓↓仕様変更（2006.01.24）↓↓
//		int selGyosyuCd = Integer.parseInt((String)map.get("sel_gyosyu_cd"));	// 業種選択画面で選択された業種コード
//		↑↑仕様変更（2006.01.24）↑↑
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑

//		↓↓2006.08.03 guohy カスタマイズ修正↓↓
		StringBuffer sb = new StringBuffer();
//		↓↓仕様変更（2006.01.24）↓↓
//		↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		sb.append("select ");
//		sb.append("select distinct ");
//      ↑↑2006.06.15 guohy カスタマイズ修正↑↑
//		sb.append("rb.* ");
//		sb.append("from ");
//		sb.append("( ");
//		↑↑仕様変更（2006.01.24）↑↑
//      ↑↑2006.08.03 guohy カスタマイズ修正↑↑
		sb.append("select ");
		sb.append("rs.kanri_kb ");
		sb.append(", ");
		sb.append("rs.kanri_cd ");
		sb.append(", ");
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		↓↓仕様変更（2005.12.26）↓↓
//		↓↓仕様変更（2006.01.27）↓↓
//		sb.append("rn1.kanji_rn bumon_nm ");
//		sb.append(", ");
//		sb.append("(select kanji_rn from r_namectf where delete_fg = '0' and syubetu_no_cd = decode(rs.kanri_kb, " + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "," + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS + "," + mst000901_KanriKbDictionary.HANKU.getCode() + "," + mst000101_ConstDictionary.H_SALE + ") and code_cd = rs.kanri_cd) kanri_nm ");
//      sb.append("rn.kanji_rn kanri_nm ");
//		↑↑仕様変更（2006.01.27）↑↑
//		↑↑仕様変更（2005.12.26）↑↑
//		↓↓仕様変更（2006.01.24）↓↓
//		sb.append("(case ");
//		sb.append("		when kanri_kb = " + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + " then rs.kanri_cd ");
//		sb.append("		when kanri_kb = " + mst000901_KanriKbDictionary.HANKU.getCode() + " then (select distinct d_gyosyu_cd from r_syohin_taikei where hanku_cd = rs.kanri_cd) ");
//		sb.append(" end) d_gyosyu_cd ");
//		sb.append(", ");
//		sb.append("(case ");
//		sb.append("		when kanri_kb = " + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + " then '' ");
//		sb.append("		when kanri_kb = " + mst000901_KanriKbDictionary.HANKU.getCode() + " then (select distinct s_gyosyu_cd from r_syohin_taikei where hanku_cd = rs.kanri_cd) ");
//		sb.append(" end) s_gyosyu_cd ");
//		sb.append(", ");
//		↑↑仕様変更（2006.01.24）↑↑
//      ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		sb.append("rs.siiresaki_cd ");
		sb.append(", ");
		sb.append("rs.kanji_na ");
		sb.append(", ");
		sb.append("rs.kana_na ");
		sb.append(", ");
		sb.append("rs.kanji_rn ");
		sb.append(", ");
		sb.append("rs.kana_rn ");
		sb.append(", ");
		sb.append("rs.yubin_cd ");
		sb.append(", ");
		sb.append("rs.address_kanji1_na ");
		sb.append(", ");
		sb.append("rs.address_kanji2_na ");
		sb.append(", ");
		sb.append("rs.address_kana1_na ");
		sb.append(", ");
		sb.append("rs.address_kana2_na ");
		sb.append(", ");
		sb.append("rs.madoguchi_tanto_na ");
		sb.append(", ");
		sb.append("rs.tel1_na ");
		sb.append(", ");
		sb.append("rs.tel2_na ");
		sb.append(", ");
		sb.append("rs.fax1_na ");
		sb.append(", ");
		sb.append("rs.fax2_na ");
		sb.append(", ");
		sb.append("rs.night_tel_na ");
		sb.append(", ");
		sb.append("rs.email_na ");
		sb.append(", ");
		sb.append("rs.johosyori_seikyu_kb ");
		sb.append(", ");
		sb.append("rs.syhincd_print_kb ");
		sb.append(", ");
		sb.append("rs.taghako_gyosya_kb ");
		sb.append(", ");
//		sb.append("rs.hako_basyo_na ");
//		sb.append(", ");
		sb.append("rs.hinbanbetu_denpyo_kb ");
		sb.append(", ");
		sb.append("rs.memo_tx ");
		sb.append(", ");
		sb.append("rs.tosan_kb ");
		sb.append(", ");
		sb.append("rs.insert_ts ");
		sb.append(", ");
		sb.append("substr(rs.insert_ts,1,8) insert_ts_short ");
		sb.append(", ");
		sb.append("rs.insert_user_id ");
		sb.append(", ");
//	    ↓↓2006.06.16 guohy カスタマイズ修正↓↓
//		sb.append("(select user_na from sys_sosasya where hojin_cd = '"+ mst000101_ConstDictionary.HOJIN_CD + "' and user_id = rs.insert_user_id) insert_user_name ");
		sb.append("user1.riyo_user_na insert_user_name  ");
//		   ↑↑2006.06.16 guohy カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("rs.update_ts ");
		sb.append(", ");
		sb.append("substr(rs.update_ts,1,8) update_ts_short ");
		sb.append(", ");
		sb.append("rs.update_user_id ");
		sb.append(", ");
//	    ↓↓2006.06.16 guohy カスタマイズ修正↓↓
//		sb.append("(select user_na from sys_sosasya where hojin_cd = '"+ mst000101_ConstDictionary.HOJIN_CD + "' and user_id = rs.update_user_id) update_user_name ");
		sb.append("user2.riyo_user_na update_user_name ");
//		   ↑↑2006.06.16 guohy カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("rs.delete_fg ");
		
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓ 一旦 CASE分の固定値で書くが、本来は名称CTFからの取得としたい。
//		sb.append(", ");
//		sb.append("CASE");
//      sb.append("    WHEN  rs.kakin_syubetu_cd = '0310' THEN");
//      sb.append("    'JCA配信 (５円）'");
//      sb.append("    WHEN  rs.kakin_syubetu_cd = '0312' THEN");
//      sb.append("    'EDI配信 (３円）'");
//      sb.append("    WHEN  rs.kakin_syubetu_cd = '0313' THEN");
//      sb.append("    'ASN 配信 (３円）'");
//      sb.append("END kakin_syubetu_na");
//	    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		sb.append(", ");
//		sb.append("RN_KAKIN.KANJI_NA AS KAKIN_SYUBETU_NA ");
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
	
		sb.append("from ");
		sb.append("R_SIIRESAKI rs ");
//		↓↓移植（2006.5.11）↓↓
//		sb.append("left outer join R_NAMECTF RN_KAKIN ");
//		sb.append("on RN_KAKIN.syubetu_no_cd = '"+ mst000101_ConstDictionary.KAKIN_SYUBETU + "' ");
//		sb.append("and rs.KAKIN_SYUBETU_CD = RN_KAKIN.code_cd ");
//		sb.append("and RN_KAKIN.delete_fg = '0' ");
//		↑↑移植（2006.5.11）↑↑
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
//		↓↓仕様変更（2006.01.27）↓↓
//	    ↓↓2006.08.03 guohy カスタマイズ修正↓↓
//	    ↓↓2006.06.16 guohy カスタマイズ修正↓↓		
//		sb.append(" left outer join r_namectf rn1 on rs.kanri_cd = '0000'  and rn1.syubetu_no_cd = '"+ mst000101_ConstDictionary.SECTION +"'");
//		sb.append(" left outer join r_riyo_user  user1 on user1.riyo_user_id =  rs.insert_user_id ");
//		sb.append(" left outer join r_riyo_user  user2 on user2.riyo_user_id =  rs.update_user_id ");				
////		   ↑↑2006.06.16 guohy カスタマイズ修正↑↑		
		sb.append(" left outer join r_portal_user  user1 on user1.user_id =  TRIM(rs.insert_user_id) ");
		sb.append(" left outer join r_portal_user  user2 on user2.user_id =  TRIM(rs.update_user_id) ");				
//		sb.append(", ");
//		sb.append("R_NAMECTF rn ");
//		↑↑2006.08.03 guohy カスタマイズ修正↑↑	
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓	
		sb.append("where ");
//	    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		↓↓移植（2006.5.11）↓↓
//		sb.append("rn.syubetu_no_cd = (case rs.kanri_kb ");
//		sb.append("                        when " + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode());
//		sb.append("                        then " + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS);
//		sb.append("                        when " + mst000901_KanriKbDictionary.HANKU.getCode());
//		sb.append("                        then " + mst000101_ConstDictionary.H_SALE + " end ) ");
//		sb.append("                         end ) ");
//		↑↑移植（2006.5.11）↑↑
//		sb.append("and ");
		sb.append("rs.kanri_kb = '"+ mst000901_KanriKbDictionary.BUMON.getCode() +"' ");
		sb.append("and ");
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		sb.append(" rs.kanri_cd = '0000' ");
//	    ↓↓2006.08.03 guohy カスタマイズ修正↓↓
//		sb.append("and ");
//		sb.append("rn.delete_fg = '0' ");
//		↑↑2006.08.03 guohy カスタマイズ修正↑↑
//		↑↑仕様変更（2006.01.27）↑↑
// 課金種別は、一応外部結合にしておく　↓↓ JINNO		
		
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
		
//		↓↓仕様変更（2006.01.27）↓↓
		whereStr = andStr;
//		↑↑仕様変更（2006.01.27）↑↑

//	    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		kanri_kb に対するWHERE区
//		if( map.get("kanri_kb_bef") != null && ((String)map.get("kanri_kb_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb >= '" + conv.convertWhereString( (String)map.get("kanri_kb_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_aft") != null && ((String)map.get("kanri_kb_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb <= '" + conv.convertWhereString( (String)map.get("kanri_kb_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_like") != null && ((String)map.get("kanri_kb_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_bef_like") != null && ((String)map.get("kanri_kb_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_aft_like") != null && ((String)map.get("kanri_kb_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb like '" + conv.convertWhereString( (String)map.get("kanri_kb_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_in") != null && ((String)map.get("kanri_kb_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("kanri_kb_not_in") != null && ((String)map.get("kanri_kb_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		

		// kanri_cd に対するWHERE区
		if( map.get("kanri_cd_bef") != null && ((String)map.get("kanri_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd >= '" + conv.convertWhereString( (String)map.get("kanri_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_aft") != null && ((String)map.get("kanri_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd <= '" + conv.convertWhereString( (String)map.get("kanri_cd_aft") ) + "'");
			whereStr = andStr;
		}
//	    ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
//     }
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd = '0000'");
			whereStr = andStr;
		}
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		if( map.get("kanri_cd_like") != null && ((String)map.get("kanri_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_bef_like") != null && ((String)map.get("kanri_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '%" + conv.convertWhereString( (String)map.get("kanri_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_aft_like") != null && ((String)map.get("kanri_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd like '" + conv.convertWhereString( (String)map.get("kanri_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_in") != null && ((String)map.get("kanri_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanri_cd_not_in") != null && ((String)map.get("kanri_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		
		
		// siiresaki_cd に対するWHERE区
		if( map.get("siiresaki_cd_bef") != null && ((String)map.get("siiresaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd >= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft") != null && ((String)map.get("siiresaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd <= '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) +"'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_like") != null && ((String)map.get("siiresaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_bef_like") != null && ((String)map.get("siiresaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_aft_like") != null && ((String)map.get("siiresaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			if  (((String)map.get("siiresaki_cd_aft_like")).trim().length() < 6 ){
				sb.append("siiresaki_cd like '%" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft_like") ) + "'");
			} else{
			sb.append("siiresaki_cd like '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft_like") ) + "%'");
			}
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_in") != null && ((String)map.get("siiresaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("siiresaki_cd_not_in") != null && ((String)map.get("siiresaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siiresaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_na に対するWHERE区
		if( map.get("kanji_na_bef") != null && ((String)map.get("kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na >= '" + conv.convertWhereString( (String)map.get("kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft") != null && ((String)map.get("kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na <= '" + conv.convertWhereString( (String)map.get("kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na") != null && ((String)map.get("kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na = '" + conv.convertWhereString( (String)map.get("kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_like") != null && ((String)map.get("kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_bef_like") != null && ((String)map.get("kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '%" + conv.convertWhereString( (String)map.get("kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_aft_like") != null && ((String)map.get("kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na like '" + conv.convertWhereString( (String)map.get("kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_na_in") != null && ((String)map.get("kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_na_not_in") != null && ((String)map.get("kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_na に対するWHERE区
		if( map.get("kana_na_bef") != null && ((String)map.get("kana_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na >= '" + conv.convertWhereString( (String)map.get("kana_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft") != null && ((String)map.get("kana_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na <= '" + conv.convertWhereString( (String)map.get("kana_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na") != null && ((String)map.get("kana_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na = '" + conv.convertWhereString( (String)map.get("kana_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_like") != null && ((String)map.get("kana_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_bef_like") != null && ((String)map.get("kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '%" + conv.convertWhereString( (String)map.get("kana_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_na_aft_like") != null && ((String)map.get("kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na like '" + conv.convertWhereString( (String)map.get("kana_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_na_in") != null && ((String)map.get("kana_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_na_not_in") != null && ((String)map.get("kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kanji_rn に対するWHERE区
		if( map.get("kanji_rn_bef") != null && ((String)map.get("kanji_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn >= '" + conv.convertWhereString( (String)map.get("kanji_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft") != null && ((String)map.get("kanji_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn <= '" + conv.convertWhereString( (String)map.get("kanji_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn") != null && ((String)map.get("kanji_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn = '" + conv.convertWhereString( (String)map.get("kanji_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_like") != null && ((String)map.get("kanji_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_bef_like") != null && ((String)map.get("kanji_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '%" + conv.convertWhereString( (String)map.get("kanji_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_aft_like") != null && ((String)map.get("kanji_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn like '" + conv.convertWhereString( (String)map.get("kanji_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_in") != null && ((String)map.get("kanji_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanji_rn_not_in") != null && ((String)map.get("kanji_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanji_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanji_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kana_rn に対するWHERE区
		if( map.get("kana_rn_bef") != null && ((String)map.get("kana_rn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn >= '" + conv.convertWhereString( (String)map.get("kana_rn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft") != null && ((String)map.get("kana_rn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn <= '" + conv.convertWhereString( (String)map.get("kana_rn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn") != null && ((String)map.get("kana_rn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn = '" + conv.convertWhereString( (String)map.get("kana_rn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_like") != null && ((String)map.get("kana_rn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_bef_like") != null && ((String)map.get("kana_rn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '%" + conv.convertWhereString( (String)map.get("kana_rn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_aft_like") != null && ((String)map.get("kana_rn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn like '" + conv.convertWhereString( (String)map.get("kana_rn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kana_rn_in") != null && ((String)map.get("kana_rn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kana_rn_not_in") != null && ((String)map.get("kana_rn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kana_rn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kana_rn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// yubin_cd に対するWHERE区
		if( map.get("yubin_cd_bef") != null && ((String)map.get("yubin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd >= '" + conv.convertWhereString( (String)map.get("yubin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_aft") != null && ((String)map.get("yubin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd <= '" + conv.convertWhereString( (String)map.get("yubin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd") != null && ((String)map.get("yubin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd = '" + conv.convertWhereString( (String)map.get("yubin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_like") != null && ((String)map.get("yubin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_bef_like") != null && ((String)map.get("yubin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '%" + conv.convertWhereString( (String)map.get("yubin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_aft_like") != null && ((String)map.get("yubin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd like '" + conv.convertWhereString( (String)map.get("yubin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_in") != null && ((String)map.get("yubin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yubin_cd_not_in") != null && ((String)map.get("yubin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yubin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yubin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kanji1_na に対するWHERE区
		if( map.get("address_kanji1_na_bef") != null && ((String)map.get("address_kanji1_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na >= '" + conv.convertWhereString( (String)map.get("address_kanji1_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_aft") != null && ((String)map.get("address_kanji1_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na <= '" + conv.convertWhereString( (String)map.get("address_kanji1_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na") != null && ((String)map.get("address_kanji1_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na = '" + conv.convertWhereString( (String)map.get("address_kanji1_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_like") != null && ((String)map.get("address_kanji1_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na like '%" + conv.convertWhereString( (String)map.get("address_kanji1_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_bef_like") != null && ((String)map.get("address_kanji1_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na like '%" + conv.convertWhereString( (String)map.get("address_kanji1_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_aft_like") != null && ((String)map.get("address_kanji1_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na like '" + conv.convertWhereString( (String)map.get("address_kanji1_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_in") != null && ((String)map.get("address_kanji1_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji1_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kanji1_na_not_in") != null && ((String)map.get("address_kanji1_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji1_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji1_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kanji2_na に対するWHERE区
		if( map.get("address_kanji2_na_bef") != null && ((String)map.get("address_kanji2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na >= '" + conv.convertWhereString( (String)map.get("address_kanji2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_aft") != null && ((String)map.get("address_kanji2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na <= '" + conv.convertWhereString( (String)map.get("address_kanji2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na") != null && ((String)map.get("address_kanji2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na = '" + conv.convertWhereString( (String)map.get("address_kanji2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_like") != null && ((String)map.get("address_kanji2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na like '%" + conv.convertWhereString( (String)map.get("address_kanji2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_bef_like") != null && ((String)map.get("address_kanji2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na like '%" + conv.convertWhereString( (String)map.get("address_kanji2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_aft_like") != null && ((String)map.get("address_kanji2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na like '" + conv.convertWhereString( (String)map.get("address_kanji2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_in") != null && ((String)map.get("address_kanji2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kanji2_na_not_in") != null && ((String)map.get("address_kanji2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kanji2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kanji2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kana1_na に対するWHERE区
		if( map.get("address_kana1_na_bef") != null && ((String)map.get("address_kana1_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na >= '" + conv.convertWhereString( (String)map.get("address_kana1_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_aft") != null && ((String)map.get("address_kana1_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na <= '" + conv.convertWhereString( (String)map.get("address_kana1_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na") != null && ((String)map.get("address_kana1_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na = '" + conv.convertWhereString( (String)map.get("address_kana1_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_like") != null && ((String)map.get("address_kana1_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na like '%" + conv.convertWhereString( (String)map.get("address_kana1_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_bef_like") != null && ((String)map.get("address_kana1_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na like '%" + conv.convertWhereString( (String)map.get("address_kana1_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_aft_like") != null && ((String)map.get("address_kana1_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na like '" + conv.convertWhereString( (String)map.get("address_kana1_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_in") != null && ((String)map.get("address_kana1_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana1_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kana1_na_not_in") != null && ((String)map.get("address_kana1_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana1_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana1_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// address_kana2_na に対するWHERE区
		if( map.get("address_kana2_na_bef") != null && ((String)map.get("address_kana2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na >= '" + conv.convertWhereString( (String)map.get("address_kana2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_aft") != null && ((String)map.get("address_kana2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na <= '" + conv.convertWhereString( (String)map.get("address_kana2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na") != null && ((String)map.get("address_kana2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na = '" + conv.convertWhereString( (String)map.get("address_kana2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_like") != null && ((String)map.get("address_kana2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na like '%" + conv.convertWhereString( (String)map.get("address_kana2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_bef_like") != null && ((String)map.get("address_kana2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na like '%" + conv.convertWhereString( (String)map.get("address_kana2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_aft_like") != null && ((String)map.get("address_kana2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na like '" + conv.convertWhereString( (String)map.get("address_kana2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_in") != null && ((String)map.get("address_kana2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("address_kana2_na_not_in") != null && ((String)map.get("address_kana2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("address_kana2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("address_kana2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// madoguchi_tanto_na に対するWHERE区
		if( map.get("madoguchi_tanto_na_bef") != null && ((String)map.get("madoguchi_tanto_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na >= '" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_aft") != null && ((String)map.get("madoguchi_tanto_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na <= '" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na") != null && ((String)map.get("madoguchi_tanto_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na = '" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_like") != null && ((String)map.get("madoguchi_tanto_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na like '%" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_bef_like") != null && ((String)map.get("madoguchi_tanto_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na like '%" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_aft_like") != null && ((String)map.get("madoguchi_tanto_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na like '" + conv.convertWhereString( (String)map.get("madoguchi_tanto_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_in") != null && ((String)map.get("madoguchi_tanto_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("madoguchi_tanto_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("madoguchi_tanto_na_not_in") != null && ((String)map.get("madoguchi_tanto_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("madoguchi_tanto_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("madoguchi_tanto_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tel1_na に対するWHERE区
		if( map.get("tel1_na_bef") != null && ((String)map.get("tel1_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na >= '" + conv.convertWhereString( (String)map.get("tel1_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel1_na_aft") != null && ((String)map.get("tel1_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na <= '" + conv.convertWhereString( (String)map.get("tel1_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel1_na") != null && ((String)map.get("tel1_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na = '" + conv.convertWhereString( (String)map.get("tel1_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel1_na_like") != null && ((String)map.get("tel1_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na like '%" + conv.convertWhereString( (String)map.get("tel1_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel1_na_bef_like") != null && ((String)map.get("tel1_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na like '%" + conv.convertWhereString( (String)map.get("tel1_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel1_na_aft_like") != null && ((String)map.get("tel1_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na like '" + conv.convertWhereString( (String)map.get("tel1_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel1_na_in") != null && ((String)map.get("tel1_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel1_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tel1_na_not_in") != null && ((String)map.get("tel1_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel1_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel1_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tel2_na に対するWHERE区
		if( map.get("tel2_na_bef") != null && ((String)map.get("tel2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na >= '" + conv.convertWhereString( (String)map.get("tel2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel2_na_aft") != null && ((String)map.get("tel2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na <= '" + conv.convertWhereString( (String)map.get("tel2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel2_na") != null && ((String)map.get("tel2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na = '" + conv.convertWhereString( (String)map.get("tel2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel2_na_like") != null && ((String)map.get("tel2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na like '%" + conv.convertWhereString( (String)map.get("tel2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel2_na_bef_like") != null && ((String)map.get("tel2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na like '%" + conv.convertWhereString( (String)map.get("tel2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tel2_na_aft_like") != null && ((String)map.get("tel2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na like '" + conv.convertWhereString( (String)map.get("tel2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tel2_na_in") != null && ((String)map.get("tel2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tel2_na_not_in") != null && ((String)map.get("tel2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tel2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tel2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// fax1_na に対するWHERE区
		if( map.get("fax1_na_bef") != null && ((String)map.get("fax1_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na >= '" + conv.convertWhereString( (String)map.get("fax1_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax1_na_aft") != null && ((String)map.get("fax1_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na <= '" + conv.convertWhereString( (String)map.get("fax1_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax1_na") != null && ((String)map.get("fax1_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na = '" + conv.convertWhereString( (String)map.get("fax1_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax1_na_like") != null && ((String)map.get("fax1_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na like '%" + conv.convertWhereString( (String)map.get("fax1_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax1_na_bef_like") != null && ((String)map.get("fax1_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na like '%" + conv.convertWhereString( (String)map.get("fax1_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax1_na_aft_like") != null && ((String)map.get("fax1_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na like '" + conv.convertWhereString( (String)map.get("fax1_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax1_na_in") != null && ((String)map.get("fax1_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax1_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("fax1_na_not_in") != null && ((String)map.get("fax1_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax1_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax1_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// fax2_na に対するWHERE区
		if( map.get("fax2_na_bef") != null && ((String)map.get("fax2_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na >= '" + conv.convertWhereString( (String)map.get("fax2_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax2_na_aft") != null && ((String)map.get("fax2_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na <= '" + conv.convertWhereString( (String)map.get("fax2_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax2_na") != null && ((String)map.get("fax2_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na = '" + conv.convertWhereString( (String)map.get("fax2_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax2_na_like") != null && ((String)map.get("fax2_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na like '%" + conv.convertWhereString( (String)map.get("fax2_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax2_na_bef_like") != null && ((String)map.get("fax2_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na like '%" + conv.convertWhereString( (String)map.get("fax2_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("fax2_na_aft_like") != null && ((String)map.get("fax2_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na like '" + conv.convertWhereString( (String)map.get("fax2_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("fax2_na_in") != null && ((String)map.get("fax2_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax2_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("fax2_na_not_in") != null && ((String)map.get("fax2_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("fax2_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("fax2_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// night_tel_na に対するWHERE区
		if( map.get("night_tel_na_bef") != null && ((String)map.get("night_tel_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na >= '" + conv.convertWhereString( (String)map.get("night_tel_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_aft") != null && ((String)map.get("night_tel_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na <= '" + conv.convertWhereString( (String)map.get("night_tel_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na") != null && ((String)map.get("night_tel_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na = '" + conv.convertWhereString( (String)map.get("night_tel_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_like") != null && ((String)map.get("night_tel_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na like '%" + conv.convertWhereString( (String)map.get("night_tel_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_bef_like") != null && ((String)map.get("night_tel_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na like '%" + conv.convertWhereString( (String)map.get("night_tel_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_aft_like") != null && ((String)map.get("night_tel_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na like '" + conv.convertWhereString( (String)map.get("night_tel_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_in") != null && ((String)map.get("night_tel_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("night_tel_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("night_tel_na_not_in") != null && ((String)map.get("night_tel_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("night_tel_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("night_tel_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// email_na に対するWHERE区
		if( map.get("email_na_bef") != null && ((String)map.get("email_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na >= '" + conv.convertWhereString( (String)map.get("email_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("email_na_aft") != null && ((String)map.get("email_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na <= '" + conv.convertWhereString( (String)map.get("email_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("email_na") != null && ((String)map.get("email_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na = '" + conv.convertWhereString( (String)map.get("email_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("email_na_like") != null && ((String)map.get("email_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na like '%" + conv.convertWhereString( (String)map.get("email_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("email_na_bef_like") != null && ((String)map.get("email_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na like '%" + conv.convertWhereString( (String)map.get("email_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("email_na_aft_like") != null && ((String)map.get("email_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na like '" + conv.convertWhereString( (String)map.get("email_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("email_na_in") != null && ((String)map.get("email_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("email_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("email_na_not_in") != null && ((String)map.get("email_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("email_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("email_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// johosyori_seikyu_kb に対するWHERE区
		if( map.get("johosyori_seikyu_kb_bef") != null && ((String)map.get("johosyori_seikyu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb >= '" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_aft") != null && ((String)map.get("johosyori_seikyu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb <= '" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb") != null && ((String)map.get("johosyori_seikyu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb = '" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_like") != null && ((String)map.get("johosyori_seikyu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb like '%" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_bef_like") != null && ((String)map.get("johosyori_seikyu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb like '%" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_aft_like") != null && ((String)map.get("johosyori_seikyu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb like '" + conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_in") != null && ((String)map.get("johosyori_seikyu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("johosyori_seikyu_kb_not_in") != null && ((String)map.get("johosyori_seikyu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("johosyori_seikyu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("johosyori_seikyu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syhincd_print_kb に対するWHERE区
		if( map.get("syhincd_print_kb_bef") != null && ((String)map.get("syhincd_print_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb >= '" + conv.convertWhereString( (String)map.get("syhincd_print_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_aft") != null && ((String)map.get("syhincd_print_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb <= '" + conv.convertWhereString( (String)map.get("syhincd_print_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb") != null && ((String)map.get("syhincd_print_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb = '" + conv.convertWhereString( (String)map.get("syhincd_print_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_like") != null && ((String)map.get("syhincd_print_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb like '%" + conv.convertWhereString( (String)map.get("syhincd_print_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_bef_like") != null && ((String)map.get("syhincd_print_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb like '%" + conv.convertWhereString( (String)map.get("syhincd_print_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_aft_like") != null && ((String)map.get("syhincd_print_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb like '" + conv.convertWhereString( (String)map.get("syhincd_print_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_in") != null && ((String)map.get("syhincd_print_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syhincd_print_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syhincd_print_kb_not_in") != null && ((String)map.get("syhincd_print_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syhincd_print_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syhincd_print_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// taghako_gyosya_kb に対するWHERE区
		if( map.get("taghako_gyosya_kb_bef") != null && ((String)map.get("taghako_gyosya_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb >= '" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_aft") != null && ((String)map.get("taghako_gyosya_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb <= '" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb") != null && ((String)map.get("taghako_gyosya_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb = '" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_like") != null && ((String)map.get("taghako_gyosya_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb like '%" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_bef_like") != null && ((String)map.get("taghako_gyosya_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb like '%" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_aft_like") != null && ((String)map.get("taghako_gyosya_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb like '" + conv.convertWhereString( (String)map.get("taghako_gyosya_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_in") != null && ((String)map.get("taghako_gyosya_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taghako_gyosya_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("taghako_gyosya_kb_not_in") != null && ((String)map.get("taghako_gyosya_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("taghako_gyosya_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("taghako_gyosya_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}




		// hinbanbetu_denpyo_kb に対するWHERE区
		if( map.get("hinbanbetu_denpyo_kb_bef") != null && ((String)map.get("hinbanbetu_denpyo_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb >= '" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_aft") != null && ((String)map.get("hinbanbetu_denpyo_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb <= '" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb") != null && ((String)map.get("hinbanbetu_denpyo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb = '" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_like") != null && ((String)map.get("hinbanbetu_denpyo_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb like '%" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_bef_like") != null && ((String)map.get("hinbanbetu_denpyo_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb like '%" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_aft_like") != null && ((String)map.get("hinbanbetu_denpyo_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb like '" + conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_in") != null && ((String)map.get("hinbanbetu_denpyo_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinbanbetu_denpyo_kb_not_in") != null && ((String)map.get("hinbanbetu_denpyo_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinbanbetu_denpyo_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinbanbetu_denpyo_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// memo_tx に対するWHERE区
		if( map.get("memo_tx_bef") != null && ((String)map.get("memo_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx >= '" + conv.convertWhereString( (String)map.get("memo_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("memo_tx_aft") != null && ((String)map.get("memo_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx <= '" + conv.convertWhereString( (String)map.get("memo_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("memo_tx") != null && ((String)map.get("memo_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx = '" + conv.convertWhereString( (String)map.get("memo_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("memo_tx_like") != null && ((String)map.get("memo_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx like '%" + conv.convertWhereString( (String)map.get("memo_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("memo_tx_bef_like") != null && ((String)map.get("memo_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx like '%" + conv.convertWhereString( (String)map.get("memo_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("memo_tx_aft_like") != null && ((String)map.get("memo_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx like '" + conv.convertWhereString( (String)map.get("memo_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("memo_tx_in") != null && ((String)map.get("memo_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("memo_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("memo_tx_not_in") != null && ((String)map.get("memo_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("memo_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("memo_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		

//		↓↓2006.08.11 guohy カスタマイズ修正↓↓
//		// tosan_kb に対するWHERE区
//		if( map.get("tosan_kb_bef") != null && ((String)map.get("tosan_kb_bef")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb >= '" + conv.convertWhereString( (String)map.get("tosan_kb_bef") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_aft") != null && ((String)map.get("tosan_kb_aft")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb <= '" + conv.convertWhereString( (String)map.get("tosan_kb_aft") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb") != null && ((String)map.get("tosan_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb = '" + conv.convertWhereString( (String)map.get("tosan_kb") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_like") != null && ((String)map.get("tosan_kb_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb like '%" + conv.convertWhereString( (String)map.get("tosan_kb_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_bef_like") != null && ((String)map.get("tosan_kb_bef_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb like '%" + conv.convertWhereString( (String)map.get("tosan_kb_bef_like") ) + "'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_aft_like") != null && ((String)map.get("tosan_kb_aft_like")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb like '" + conv.convertWhereString( (String)map.get("tosan_kb_aft_like") ) + "%'");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_in") != null && ((String)map.get("tosan_kb_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tosan_kb_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		if( map.get("tosan_kb_not_in") != null && ((String)map.get("tosan_kb_not_in")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("tosan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tosan_kb_not_in") ),",","','") + "' )");
//			whereStr = andStr;
//		}
//		↑↑2006.08.11 guohy カスタマイズ修正↑↑
		

		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("rs.delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		
//		↓↓仕様変更（2006.01.24）↓↓
//		↓↓移植（2006.5.22）↓↓
//		↓↓2006.08.03 guohy カスタマイズ修正↓↓
//		sb.append(") as rb");
//	    ↑↑2006.08.03 guohy カスタマイズ修正↑↑
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		where区の初期化
//		whereStr = " where ";	
		
		// selGyosyuCd に対するWHERE区
//		if( selGyosyuCd == 1 )
//		{
			//衣料
//			sb.append(whereStr);
//			sb.append("rb.d_gyosyu_cd in ('0011','0044','0061','0057')");
//			whereStr = andStr;
//		} 
//		else if( selGyosyuCd == 3 )
//		{
			//加工食品
//			sb.append(whereStr);
//			sb.append("rb.d_gyosyu_cd = '0033'");
//			if(((String)map.get("kanri_kb")).equals(mst000901_KanriKbDictionary.HANKU.getCode()))
//			{
//				sb.append(andStr);
//				sb.append("rb.s_gyosyu_cd in ('0087','0088')");
//			}
//			whereStr = andStr;
//		}
//		else if( selGyosyuCd == 4 )
//		{
			//生鮮食品
//			sb.append(whereStr);
//			sb.append("rb.d_gyosyu_cd = '0033'");
//			if(((String)map.get("kanri_kb")).equals(mst000901_KanriKbDictionary.HANKU.getCode()))
//			{
//				sb.append(andStr);
//				sb.append("rb.s_gyosyu_cd <> '0087'");
//				sb.append(andStr);
//				sb.append("rb.s_gyosyu_cd <> '0088'");
//			}
//			whereStr = andStr;
//		}
//		else if( selGyosyuCd == 2 )
//		{
			//住生活
//			sb.append(whereStr);
//			sb.append("rb.d_gyosyu_cd <> '0011'");
//			sb.append(andStr);
//			sb.append("rb.d_gyosyu_cd <> '0044'");
//			sb.append(andStr);
//			sb.append("rb.d_gyosyu_cd <> '0061'");
//			sb.append(andStr);
//			sb.append("rb.d_gyosyu_cd <> '0057'");
//			sb.append(andStr);
//			sb.append("rb.d_gyosyu_cd <> '0033'");
//			whereStr = andStr;
//		}
//		↑↑仕様変更（2006.01.24）↑↑
//		↑↑移植（2006.5.22）↑↑
		sb.append(" order by ");
		sb.append("kanri_cd");
		sb.append(",");
		sb.append("siiresaki_cd");
//		sb.append("kanri_kb");
//		sb.append(",");
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑

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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst430101_SiiresakiBean bean = (mst430101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_SIIRESAKI (");
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" kanri_kb");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" kanri_cd");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bumon_cd");
		}
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd");
		}
		if( bean.getAddressKanji1Na() != null && bean.getAddressKanji1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji1_na");
		}
		if( bean.getAddressKanji2Na() != null && bean.getAddressKanji2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji2_na");
		}
		if( bean.getAddressKana1Na() != null && bean.getAddressKana1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana1_na");
		}
		if( bean.getAddressKana2Na() != null && bean.getAddressKana2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana2_na");
		}
		if( bean.getMadoguchiTantoNa() != null && bean.getMadoguchiTantoNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" madoguchi_tanto_na");
		}
		if( bean.getTel1Na() != null && bean.getTel1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel1_na");
		}
		if( bean.getTel2Na() != null && bean.getTel2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel2_na");
		}
		if( bean.getFax1Na() != null && bean.getFax1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax1_na");
		}
		if( bean.getFax2Na() != null && bean.getFax2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax2_na");
		}
		if( bean.getNightTelNa() != null && bean.getNightTelNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" night_tel_na");
		}
		if( bean.getEmailNa() != null && bean.getEmailNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" email_na");
		}
		if( bean.getJohosyoriSeikyuKb() != null && bean.getJohosyoriSeikyuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" johosyori_seikyu_kb");
		}
		if( bean.getSyhincdPrintKb() != null && bean.getSyhincdPrintKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syhincd_print_kb");
		}
		if( bean.getTaghakoGyosyaKb() != null && bean.getTaghakoGyosyaKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taghako_gyosya_kb");
		}
		if( bean.getHinbanbetuDenpyoKb() != null && bean.getHinbanbetuDenpyoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinbanbetu_denpyo_kb");
		}
		if( bean.getMemoTx() != null && bean.getMemoTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" memo_tx");
		}
		if( bean.getTosanKb() != null && bean.getTosanKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tosan_kb");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
//		}

		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
		}
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
		}
		if( bean.getAddressKanji1Na() != null && bean.getAddressKanji1Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKanji1Na() ) + "'");
		}
		if( bean.getAddressKanji2Na() != null && bean.getAddressKanji2Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKanji2Na() ) + "'");
		}
		if( bean.getAddressKana1Na() != null && bean.getAddressKana1Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKana1Na() ) + "'");
		}
		if( bean.getAddressKana2Na() != null && bean.getAddressKana2Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAddressKana2Na() ) + "'");
		}
		if( bean.getMadoguchiTantoNa() != null && bean.getMadoguchiTantoNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMadoguchiTantoNa() ) + "'");
		}
		if( bean.getTel1Na() != null && bean.getTel1Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTel1Na() ) + "'");
		}
		if( bean.getTel2Na() != null && bean.getTel2Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTel2Na() ) + "'");
		}
		if( bean.getFax1Na() != null && bean.getFax1Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getFax1Na() ) + "'");
		}
		if( bean.getFax2Na() != null && bean.getFax2Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getFax2Na() ) + "'");
		}
		if( bean.getNightTelNa() != null && bean.getNightTelNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNightTelNa() ) + "'");
		}
		if( bean.getEmailNa() != null && bean.getEmailNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEmailNa() ) + "'");
		}
		if( bean.getJohosyoriSeikyuKb() != null && bean.getJohosyoriSeikyuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getJohosyoriSeikyuKb() ) + "'");
		}
		if( bean.getSyhincdPrintKb() != null && bean.getSyhincdPrintKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyhincdPrintKb() ) + "'");
		}
		if( bean.getTaghakoGyosyaKb() != null && bean.getTaghakoGyosyaKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTaghakoGyosyaKb() ) + "'");
		}
		if( bean.getHinbanbetuDenpyoKb() != null && bean.getHinbanbetuDenpyoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHinbanbetuDenpyoKb() ) + "'");
		}
		if( bean.getMemoTx() != null && bean.getMemoTx().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getMemoTx() ) + "'");
		}
		if( bean.getTosanKb() != null && bean.getTosanKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTosanKb() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst430101_SiiresakiBean bean = (mst430101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SIIRESAKI set ");
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanji_na = ");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
		}
		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na = ");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
		}
		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn = ");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
		}
		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn = ");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
		}
		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd = ");
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
		}
		if( bean.getAddressKanji1Na() != null && bean.getAddressKanji1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji1_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanji1Na() ) + "'");
		}
		if( bean.getAddressKanji2Na() != null && bean.getAddressKanji2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji2_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanji2Na() ) + "'");
		}
		if( bean.getAddressKana1Na() != null && bean.getAddressKana1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana1_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKana1Na() ) + "'");
		}
		if( bean.getAddressKana2Na() != null && bean.getAddressKana2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana2_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKana2Na() ) + "'");
		}
		if( bean.getMadoguchiTantoNa() != null && bean.getMadoguchiTantoNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" madoguchi_tanto_na = ");
			sb.append("'" + conv.convertString( bean.getMadoguchiTantoNa() ) + "'");
		}
		if( bean.getTel1Na() != null && bean.getTel1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel1_na = ");
			sb.append("'" + conv.convertString( bean.getTel1Na() ) + "'");
		}
		if( bean.getTel2Na() != null && bean.getTel2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel2_na = ");
			sb.append("'" + conv.convertString( bean.getTel2Na() ) + "'");
		}
		if( bean.getFax1Na() != null && bean.getFax1Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax1_na = ");
			sb.append("'" + conv.convertString( bean.getFax1Na() ) + "'");
		}
		if( bean.getFax2Na() != null && bean.getFax2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax2_na = ");
			sb.append("'" + conv.convertString( bean.getFax2Na() ) + "'");
		}
		if( bean.getNightTelNa() != null && bean.getNightTelNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" night_tel_na = ");
			sb.append("'" + conv.convertString( bean.getNightTelNa() ) + "'");
		}
		if( bean.getEmailNa() != null && bean.getEmailNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" email_na = ");
			sb.append("'" + conv.convertString( bean.getEmailNa() ) + "'");
		}
		if( bean.getJohosyoriSeikyuKb() != null && bean.getJohosyoriSeikyuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" johosyori_seikyu_kb = ");
			sb.append("'" + conv.convertString( bean.getJohosyoriSeikyuKb() ) + "'");
		}
		if( bean.getSyhincdPrintKb() != null && bean.getSyhincdPrintKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syhincd_print_kb = ");
			sb.append("'" + conv.convertString( bean.getSyhincdPrintKb() ) + "'");
		}
		if( bean.getTaghakoGyosyaKb() != null && bean.getTaghakoGyosyaKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taghako_gyosya_kb = ");
			sb.append("'" + conv.convertString( bean.getTaghakoGyosyaKb() ) + "'");
		}
		if( bean.getHinbanbetuDenpyoKb() != null && bean.getHinbanbetuDenpyoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinbanbetu_denpyo_kb = ");
			sb.append("'" + conv.convertString( bean.getHinbanbetuDenpyoKb() ) + "'");
		}
		if( bean.getMemoTx() != null && bean.getMemoTx().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" memo_tx = ");
			sb.append("'" + conv.convertString( bean.getMemoTx() ) + "'");
		}
		if( bean.getTosanKb() != null && bean.getTosanKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tosan_kb = ");
			sb.append("'" + conv.convertString( bean.getTosanKb() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");

//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" kanri_kb = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append(" kanri_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		}
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst430101_SiiresakiBean bean = (mst430101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_SIIRESAKI where ");
//		   ↓↓2006.06.15 guohy カスタマイズ修正↓↓
//		sb.append(" kanri_kb = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		sb.append(" AND");
		sb.append(" kanri_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
//	    ↑↑2006.06.15 guohy カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" siiresaki_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
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
