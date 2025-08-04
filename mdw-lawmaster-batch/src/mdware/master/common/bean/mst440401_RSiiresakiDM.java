package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author DataModule Creator More Table (2004.11.25) Version 1.1.rbsite
 * @version 1.0 (Create time: 2006/9/25 15:07:37) K.Tanigawa
 * @version 1.1 (2006/10/08) 障害票№0133対応 POS→「タグ衣料」、POS→「POS、タグ衣料」にシステム区分を修正した際のチェック様にWHERE句追加 K.Tanigawa
 */
public class mst440401_RSiiresakiDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mst440401_RSiiresakiDM()
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
		mst440401_RSiiresakiBean bean = new mst440401_RSiiresakiBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setKanaNa( rest.getString("kana_na") );
		bean.setKanjiRn( rest.getString("kanji_rn") );
		bean.setKanaRn( rest.getString("kana_rn") );
		bean.setYubinCd( rest.getString("yubin_cd") );
		bean.setAddressKanji1Na( rest.getString("address_kanji1_na") );
		bean.setAddressKanji2Na( rest.getString("address_kanji2_na") );
		bean.setAddressKana1Na( rest.getString("address_kana1_na") );
		bean.setAddressKana2Na( rest.getString("address_kana2_na") );
		bean.setMadoguchiTantoNa( rest.getString("madoguchi_tanto_na") );
		bean.setTel1Na( rest.getString("tel1_na") );
		bean.setTel2Na( rest.getString("tel2_na") );
		bean.setFax1Na( rest.getString("fax1_na") );
		bean.setFax2Na( rest.getString("fax2_na") );
		bean.setNightTelNa( rest.getString("night_tel_na") );
		bean.setEmailNa( rest.getString("email_na") );
		bean.setEigyosyoKanjiNa( rest.getString("eigyosyo_kanji_na") );
		bean.setEigyosyoKanaNa( rest.getString("eigyosyo_kana_na") );
		bean.setJohosyoriSeikyuKb( rest.getString("johosyori_seikyu_kb") );
		bean.setSyhincdPrintKb( rest.getString("syhincd_print_kb") );
		bean.setTaghakoGyosyaKb( rest.getString("taghako_gyosya_kb") );
		bean.setHinbanbetuDenpyoKb( rest.getString("hinbanbetu_denpyo_kb") );
		bean.setMemoTx( rest.getString("memo_tx") );
		bean.setTosanKb( rest.getString("tosan_kb") );
		bean.setHanbaiKeiyakuKb( rest.getString("hanbai_keiyaku_kb") );
		bean.setAstyKb( rest.getString("asty_kb") );
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
		bean.setSiireSystemKb( rest.getString("siire_system_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("kanri_kb ");
		sb.append(", ");
		sb.append("kanri_cd ");
		sb.append(", ");
		sb.append("siiresaki_cd ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("kanji_na ");
		sb.append(", ");
		sb.append("kana_na ");
		sb.append(", ");
		sb.append("kanji_rn ");
		sb.append(", ");
		sb.append("kana_rn ");
		sb.append(", ");
		sb.append("yubin_cd ");
		sb.append(", ");
		sb.append("address_kanji1_na ");
		sb.append(", ");
		sb.append("address_kanji2_na ");
		sb.append(", ");
		sb.append("address_kana1_na ");
		sb.append(", ");
		sb.append("address_kana2_na ");
		sb.append(", ");
		sb.append("madoguchi_tanto_na ");
		sb.append(", ");
		sb.append("tel1_na ");
		sb.append(", ");
		sb.append("tel2_na ");
		sb.append(", ");
		sb.append("fax1_na ");
		sb.append(", ");
		sb.append("fax2_na ");
		sb.append(", ");
		sb.append("night_tel_na ");
		sb.append(", ");
		sb.append("email_na ");
		sb.append(", ");
		sb.append("eigyosyo_kanji_na ");
		sb.append(", ");
		sb.append("eigyosyo_kana_na ");
		sb.append(", ");
		sb.append("johosyori_seikyu_kb ");
		sb.append(", ");
		sb.append("syhincd_print_kb ");
		sb.append(", ");
		sb.append("taghako_gyosya_kb ");
		sb.append(", ");
		sb.append("hinbanbetu_denpyo_kb ");
		sb.append(", ");
		sb.append("memo_tx ");
		sb.append(", ");
		sb.append("tosan_kb ");
		sb.append(", ");
		sb.append("hanbai_keiyaku_kb ");
		sb.append(", ");
		sb.append("asty_kb ");
		sb.append(", ");
		sb.append("haisinsaki_cd ");
		sb.append(", ");
		sb.append("siire_system_kb ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
		sb.append("update_user_id ");
		sb.append(", ");
		sb.append("delete_fg ");
		sb.append("from r_siiresaki ");


		// kanri_kb に対するWHERE区
		if( map.get("kanri_kb_bef") != null && ((String)map.get("kanri_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb >= '" + conv.convertWhereString( (String)map.get("kanri_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_aft") != null && ((String)map.get("kanri_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb <= '" + conv.convertWhereString( (String)map.get("kanri_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_like") != null && ((String)map.get("kanri_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_bef_like") != null && ((String)map.get("kanri_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '%" + conv.convertWhereString( (String)map.get("kanri_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_aft_like") != null && ((String)map.get("kanri_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb like '" + conv.convertWhereString( (String)map.get("kanri_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_in") != null && ((String)map.get("kanri_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kanri_kb_not_in") != null && ((String)map.get("kanri_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kanri_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


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
		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
			whereStr = andStr;
		}
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
			sb.append("siiresaki_cd = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "'");
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
			sb.append("siiresaki_cd like '" + conv.convertWhereString( (String)map.get("siiresaki_cd_aft_like") ) + "%'");
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

		//ADD by Tanigawa 2006/10/08 障害票№0122対応 更新時、更新対象の仕入先コードは、SELECT対象外とする(SELECT対象とすると必ず1件はデータが返ってくるため、重複チェック処理でひっかかってしまう) START
		if( map.get("siiresaki_cd_not_equals") != null && ((String)map.get("siiresaki_cd_not_equals")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siiresaki_cd != '" + conv.convertWhereString( (String)map.get("siiresaki_cd_not_equals") ) + "'");
			whereStr = andStr;
		}
		//ADD by Tanigawa 2006/10/08 障害票№0122対応  END 


		// eos_kb に対するWHERE区
		if( map.get("eos_kb_bef") != null && ((String)map.get("eos_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb >= '" + conv.convertWhereString( (String)map.get("eos_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_aft") != null && ((String)map.get("eos_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb <= '" + conv.convertWhereString( (String)map.get("eos_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb") != null && ((String)map.get("eos_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb = '" + conv.convertWhereString( (String)map.get("eos_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_like") != null && ((String)map.get("eos_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '%" + conv.convertWhereString( (String)map.get("eos_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_bef_like") != null && ((String)map.get("eos_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '%" + conv.convertWhereString( (String)map.get("eos_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_aft_like") != null && ((String)map.get("eos_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb like '" + conv.convertWhereString( (String)map.get("eos_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eos_kb_in") != null && ((String)map.get("eos_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eos_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eos_kb_not_in") != null && ((String)map.get("eos_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eos_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eos_kb_not_in") ),",","','") + "' )");
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


		// eigyosyo_kanji_na に対するWHERE区
		if( map.get("eigyosyo_kanji_na_bef") != null && ((String)map.get("eigyosyo_kanji_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na >= '" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_aft") != null && ((String)map.get("eigyosyo_kanji_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na <= '" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na") != null && ((String)map.get("eigyosyo_kanji_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na = '" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_like") != null && ((String)map.get("eigyosyo_kanji_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na like '%" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_bef_like") != null && ((String)map.get("eigyosyo_kanji_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na like '%" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_aft_like") != null && ((String)map.get("eigyosyo_kanji_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na like '" + conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_in") != null && ((String)map.get("eigyosyo_kanji_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kanji_na_not_in") != null && ((String)map.get("eigyosyo_kanji_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kanji_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eigyosyo_kanji_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// eigyosyo_kana_na に対するWHERE区
		if( map.get("eigyosyo_kana_na_bef") != null && ((String)map.get("eigyosyo_kana_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na >= '" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_aft") != null && ((String)map.get("eigyosyo_kana_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na <= '" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na") != null && ((String)map.get("eigyosyo_kana_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na = '" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_like") != null && ((String)map.get("eigyosyo_kana_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na like '%" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_bef_like") != null && ((String)map.get("eigyosyo_kana_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na like '%" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_aft_like") != null && ((String)map.get("eigyosyo_kana_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na like '" + conv.convertWhereString( (String)map.get("eigyosyo_kana_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_in") != null && ((String)map.get("eigyosyo_kana_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eigyosyo_kana_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eigyosyo_kana_na_not_in") != null && ((String)map.get("eigyosyo_kana_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("eigyosyo_kana_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eigyosyo_kana_na_not_in") ),",","','") + "' )");
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


		// tosan_kb に対するWHERE区
		if( map.get("tosan_kb_bef") != null && ((String)map.get("tosan_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb >= '" + conv.convertWhereString( (String)map.get("tosan_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_aft") != null && ((String)map.get("tosan_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb <= '" + conv.convertWhereString( (String)map.get("tosan_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb") != null && ((String)map.get("tosan_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb = '" + conv.convertWhereString( (String)map.get("tosan_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_like") != null && ((String)map.get("tosan_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb like '%" + conv.convertWhereString( (String)map.get("tosan_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_bef_like") != null && ((String)map.get("tosan_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb like '%" + conv.convertWhereString( (String)map.get("tosan_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_aft_like") != null && ((String)map.get("tosan_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb like '" + conv.convertWhereString( (String)map.get("tosan_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_in") != null && ((String)map.get("tosan_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tosan_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tosan_kb_not_in") != null && ((String)map.get("tosan_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tosan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tosan_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanbai_keiyaku_kb に対するWHERE区
		if( map.get("hanbai_keiyaku_kb_bef") != null && ((String)map.get("hanbai_keiyaku_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb >= '" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_aft") != null && ((String)map.get("hanbai_keiyaku_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb <= '" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb") != null && ((String)map.get("hanbai_keiyaku_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb = '" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_like") != null && ((String)map.get("hanbai_keiyaku_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_bef_like") != null && ((String)map.get("hanbai_keiyaku_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_aft_like") != null && ((String)map.get("hanbai_keiyaku_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb like '" + conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_in") != null && ((String)map.get("hanbai_keiyaku_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_keiyaku_kb_not_in") != null && ((String)map.get("hanbai_keiyaku_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_keiyaku_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_keiyaku_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// asty_kb に対するWHERE区
		if( map.get("asty_kb_bef") != null && ((String)map.get("asty_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb >= '" + conv.convertWhereString( (String)map.get("asty_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("asty_kb_aft") != null && ((String)map.get("asty_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb <= '" + conv.convertWhereString( (String)map.get("asty_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("asty_kb") != null && ((String)map.get("asty_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb = '" + conv.convertWhereString( (String)map.get("asty_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("asty_kb_like") != null && ((String)map.get("asty_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb like '%" + conv.convertWhereString( (String)map.get("asty_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("asty_kb_bef_like") != null && ((String)map.get("asty_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb like '%" + conv.convertWhereString( (String)map.get("asty_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("asty_kb_aft_like") != null && ((String)map.get("asty_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb like '" + conv.convertWhereString( (String)map.get("asty_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("asty_kb_in") != null && ((String)map.get("asty_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("asty_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("asty_kb_not_in") != null && ((String)map.get("asty_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("asty_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("asty_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// haisinsaki_cd に対するWHERE区
		if( map.get("haisinsaki_cd_bef") != null && ((String)map.get("haisinsaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd >= '" + conv.convertWhereString( (String)map.get("haisinsaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_aft") != null && ((String)map.get("haisinsaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd <= '" + conv.convertWhereString( (String)map.get("haisinsaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd") != null && ((String)map.get("haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd = '" + conv.convertWhereString( (String)map.get("haisinsaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_like") != null && ((String)map.get("haisinsaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd like '%" + conv.convertWhereString( (String)map.get("haisinsaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_bef_like") != null && ((String)map.get("haisinsaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd like '%" + conv.convertWhereString( (String)map.get("haisinsaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_aft_like") != null && ((String)map.get("haisinsaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd like '" + conv.convertWhereString( (String)map.get("haisinsaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_in") != null && ((String)map.get("haisinsaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisinsaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("haisinsaki_cd_not_in") != null && ((String)map.get("haisinsaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisinsaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisinsaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// siire_system_kb に対するWHERE区
		if( map.get("siire_system_kb_bef") != null && ((String)map.get("siire_system_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb >= '" + conv.convertWhereString( (String)map.get("siire_system_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_aft") != null && ((String)map.get("siire_system_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb <= '" + conv.convertWhereString( (String)map.get("siire_system_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb") != null && ((String)map.get("siire_system_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb = '" + conv.convertWhereString( (String)map.get("siire_system_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_like") != null && ((String)map.get("siire_system_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb like '%" + conv.convertWhereString( (String)map.get("siire_system_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_bef_like") != null && ((String)map.get("siire_system_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb like '%" + conv.convertWhereString( (String)map.get("siire_system_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_aft_like") != null && ((String)map.get("siire_system_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb like '" + conv.convertWhereString( (String)map.get("siire_system_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_in") != null && ((String)map.get("siire_system_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siire_system_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("siire_system_kb_not_in") != null && ((String)map.get("siire_system_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siire_system_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siire_system_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


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
			sb.append("delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("kanri_kb");
		sb.append(",");
		sb.append("kanri_cd");
		sb.append(",");
		sb.append("siiresaki_cd");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst440401_RSiiresakiBean bean = (mst440401_RSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_siiresaki (");
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" kanri_kb");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siiresaki_cd");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eos_kb");
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
		if( bean.getEigyosyoKanjiNa() != null && bean.getEigyosyoKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eigyosyo_kanji_na");
		}
		if( bean.getEigyosyoKanaNa() != null && bean.getEigyosyoKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eigyosyo_kana_na");
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
		if( bean.getHanbaiKeiyakuKb() != null && bean.getHanbaiKeiyakuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_keiyaku_kb");
		}
		if( bean.getAstyKb() != null && bean.getAstyKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" asty_kb");
		}
		if( bean.getHaisinsakiCd() != null && bean.getHaisinsakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" haisinsaki_cd");
		}
		if( bean.getSiireSystemKb() != null && bean.getSiireSystemKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_system_kb");
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
		}
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
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
		if( bean.getEigyosyoKanjiNa() != null && bean.getEigyosyoKanjiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEigyosyoKanjiNa() ) + "'");
		}
		if( bean.getEigyosyoKanaNa() != null && bean.getEigyosyoKanaNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEigyosyoKanaNa() ) + "'");
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
		if( bean.getHanbaiKeiyakuKb() != null && bean.getHanbaiKeiyakuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHanbaiKeiyakuKb() ) + "'");
		}
		if( bean.getAstyKb() != null && bean.getAstyKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAstyKb() ) + "'");
		}
		if( bean.getHaisinsakiCd() != null && bean.getHaisinsakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHaisinsakiCd() ) + "'");
		}
		if( bean.getSiireSystemKb() != null && bean.getSiireSystemKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiireSystemKb() ) + "'");
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst440401_RSiiresakiBean bean = (mst440401_RSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_siiresaki set ");
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" eos_kb = ");
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
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
		if( bean.getEigyosyoKanjiNa() != null && bean.getEigyosyoKanjiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eigyosyo_kanji_na = ");
			sb.append("'" + conv.convertString( bean.getEigyosyoKanjiNa() ) + "'");
		}
		if( bean.getEigyosyoKanaNa() != null && bean.getEigyosyoKanaNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eigyosyo_kana_na = ");
			sb.append("'" + conv.convertString( bean.getEigyosyoKanaNa() ) + "'");
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
		if( bean.getHanbaiKeiyakuKb() != null && bean.getHanbaiKeiyakuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_keiyaku_kb = ");
			sb.append("'" + conv.convertString( bean.getHanbaiKeiyakuKb() ) + "'");
		}
		if( bean.getAstyKb() != null && bean.getAstyKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" asty_kb = ");
			sb.append("'" + conv.convertString( bean.getAstyKb() ) + "'");
		}
		if( bean.getHaisinsakiCd() != null && bean.getHaisinsakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" haisinsaki_cd = ");
			sb.append("'" + conv.convertString( bean.getHaisinsakiCd() ) + "'");
		}
		if( bean.getSiireSystemKb() != null && bean.getSiireSystemKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_system_kb = ");
			sb.append("'" + conv.convertString( bean.getSiireSystemKb() ) + "'");
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


		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		}
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		}
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst440401_RSiiresakiBean bean = (mst440401_RSiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_siiresaki where ");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
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
