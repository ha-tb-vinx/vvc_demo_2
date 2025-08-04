package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/24)初版作成
 * @version 1.1(2006/03/29)課金種別コード　追加 JINNO
 * @version 1.2(2006/09/24,2006/09/25,2006/09/26)障害票№0036対応 仕入先システム区分をUPDATE、INSERT、SELECT対象とする K.TANIGAWA
 * @version 1.3(2006/09/26)障害票№0060対応 作成者IDが空文字で更新(UPDATE)される→作成者IDを更新(UPDATE)対象から外す K.TANIGAWA
 */
public class mst440101_SiiresakiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst440101_SiiresakiDM()
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
		mst440101_SiiresakiBean bean = new mst440101_SiiresakiBean();
//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setBumonCd( rest.getString("kanri_cd") );
//	   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
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
		bean.setJohosyoriSeikyuKb( rest.getString("johosyori_seikyu_kb") );
		bean.setSyhincdPrintKb( rest.getString("syhincd_print_kb") );
		bean.setTaghakoGyosyaKb( rest.getString("taghako_gyosya_kb") );
		//↓↓削除（2005.06.30）
//		bean.setHakoBasyoNa( rest.getString("hako_basyo_na") );
		//↑↑削除（2005.06.30）
		bean.setHinbanbetuDenpyoKb( rest.getString("hinbanbetu_denpyo_kb") );
		bean.setMemoTx( rest.getString("memo_tx") );
		bean.setTosanKb( rest.getString("tosan_kb") );
//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
		bean.setAstyKb( rest.getString("asty_kb") );
		bean.setHanbaiKeiyakuKb( rest.getString("hanbai_keiyaku_kb") );
//	   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
		//ADD by Tanigawa 2006/9/25 仕入先システム区分表示 START
		bean.setSiireSystemKb( rest.getString("siire_system_kb") );
		//ADD by Tanigawa 2006/9/25 仕入先システム区分表示  END 
		
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		//↓↓仕様追加による変更（2005.05.24）
		bean.setEosKb( rest.getString("eos_kb") );
		//↑↑仕様追加による変更（2005.05.24）
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		bean.setKakinSyubetuCd( rest.getString("kakin_syubetu_cd") );
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
		
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
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("tb1.kanri_kb ");
		sb.append(", ");
		sb.append("tb1.kanri_cd kanri_cd ");
		sb.append(", ");
		sb.append("tb1.siiresaki_cd siiresaki_cd ");
		sb.append(", ");
		sb.append("tb1.kanji_na kanji_na ");
		sb.append(", ");
		sb.append("tb1.kana_na kana_na ");
		sb.append(", ");
		sb.append("tb1.kanji_rn kanji_rn ");
		sb.append(", ");
		sb.append("tb1.kana_rn kana_rn ");
		sb.append(", ");
		sb.append("tb1.yubin_cd yubin_cd ");
		sb.append(", ");
		sb.append("tb1.address_kanji1_na address_kanji1_na ");
		sb.append(", ");
		sb.append("tb1.address_kanji2_na address_kanji2_na ");
		sb.append(", ");
		sb.append("tb1.address_kana1_na address_kana1_na ");
		sb.append(", ");
		sb.append("tb1.address_kana2_na address_kana2_na ");
		sb.append(", ");
		sb.append("tb1.madoguchi_tanto_na madoguchi_tanto_na ");
		sb.append(", ");
		sb.append("tb1.tel1_na tel1_na ");
		sb.append(", ");
		sb.append("tb1.tel2_na tel2_na ");
		sb.append(", ");
		sb.append("tb1.fax1_na fax1_na ");
		sb.append(", ");
		sb.append("tb1.fax2_na fax2_na ");
		sb.append(", ");
		sb.append("tb1.night_tel_na night_tel_na ");
		sb.append(", ");
		sb.append("tb1.email_na email_na ");
		sb.append(", ");
		sb.append("tb1.johosyori_seikyu_kb johosyori_seikyu_kb ");
		sb.append(", ");
		sb.append("tb1.syhincd_print_kb syhincd_print_kb ");
		sb.append(", ");
		sb.append("tb1.taghako_gyosya_kb taghako_gyosya_kb ");
//		↓↓削除（2005.06.30）↓↓
//		sb.append(", ");
//		sb.append("hako_basyo_na ");
//		↑↑削除（2005.06.30）↑↑
		sb.append(", ");
		sb.append("tb1.hinbanbetu_denpyo_kb hinbanbetu_denpyo_kb ");
		sb.append(", ");
		sb.append("tb1.memo_tx memo_tx ");
		sb.append(", ");
		sb.append("tb1.tosan_kb tosan_kb ");
		sb.append(", ");
//	   ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
		sb.append("tb1.asty_kb asty_kb ");
		sb.append(", ");
		sb.append("tb1.hanbai_keiyaku_kb hanbai_keiyaku_kb ");
//	   ↑↑2006.06.23 wangzhg カスタマイズ修正↑↑
		sb.append(", ");
		
		//ADD by Tanigawa 2006/9/25 障害票№0036対応 START
		sb.append("tb1.siire_system_kb siire_system_kb ");
		sb.append(", ");
		//ADD by Tanigawa 2006/9/25 障害票№0036対応  END 

		sb.append("tb1.insert_ts insert_ts ");
		sb.append(", ");
		sb.append("tb1.insert_user_id insert_user_id ");
		sb.append(", ");
		sb.append("tb1.update_ts update_ts ");
		sb.append(", ");
		sb.append("tb1.update_user_id update_user_id ");
		sb.append(", ");
		sb.append("tb1.delete_fg delete_fg ");
		//↓↓仕様追加による変更（2005.05.24）
		sb.append(",");
		sb.append("tb1.eos_kb eos_kb ");
		//↑↑仕様追加による変更（2005.05.24）
//		↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		sb.append(", ");
//		sb.append("kakin_syubetu_cd ");
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		↑↑2006.06.23 wangzhg カスタマイズ修正↑↑
		
		sb.append("from r_siiresaki tb1 ");
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		wk = getWhereSqlStr(map, "kanri_kb", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
		sb.append(whereStr);
		sb.append("tb1.kanri_kb" + " = '" + mst000901_KanriKbDictionary.BUMON.getCode()  + "'");
		whereStr = andStr;
//		wk = getWhereSqlStr(map, "kanri_cd", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1.kanri_cd" + " = '" + mst000401_LogicBean.formatZero(conv.convertWhereString( (String)map.get("kanri_cd")),4) + "'");
			whereStr = andStr;
		}
//		wk = getWhereSqlStr(map, "siiresaki_cd", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1.siiresaki_cd" + " = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") )
					    + conv.convertWhereString( (String)map.get("area_cd") ) + "'");
			whereStr = andStr;
		}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		wk = getWhereSqlStr(map, "kanji_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kana_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kanji_rn", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "kana_rn", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "yubin_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "address_kanji1_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "address_kanji2_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "address_kana1_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "address_kana2_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "madoguchi_tanto_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tel1_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tel2_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "fax1_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "fax2_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "night_tel_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "email_na", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "johosyori_seikyu_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syhincd_print_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "taghako_gyosya_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
//		↓↓削除（2005.06.30）↓↓
//		wk = getWhereSqlStr(map, "hako_basyo_na", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
//		↑↑削除（2005.06.30）↑↑
		wk = getWhereSqlStr(map, "hinbanbetu_denpyo_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "memo_tx", whereStr);
		sb.append(wk);
		whereStr = andStr;
//		↓↓2006.08.08 jianglm カスタマイズ修正↓↓		
//		wk = getWhereSqlStr(map, "tosan_kb", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
//		↑↑2006.08.08 jianglm カスタマイズ修正↑↑
//	   ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
		wk = getWhereSqlStr(map, "asty_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "hanbai_keiyaku_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
//	   ↑↑2006.06.23 wangzhg カスタマイズ修正↑↑
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		sb.append(wk);
		whereStr = andStr;
		//↓↓仕様追加による変更（2005.05.24）
		wk = getWhereSqlStr(map, "eos_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		//↑↑仕様追加による変更（2005.05.24）
		
//		↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//		↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		wk = getWhereSqlStr(map, "kakin_syubetu_cd", whereStr);
//		sb.append(wk);
//		whereStr = andStr;
//		↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
		sb.append(" order by ");
		sb.append("tb1.kanri_cd");
		sb.append(",");
		sb.append("tb1.siiresaki_cd");
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ文字型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlStr(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();


		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " = '" + conv.convertWhereString( (String)map.get(Key) ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_like") != null && ((String)map.get(Key + "_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_bef_like") != null && ((String)map.get(Key + "_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft_like") != null && ((String)map.get(Key + "_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " like '" + conv.convertWhereString( (String)map.get(Key + "_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ日付・時刻型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlDate(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " = '" + (String)map.get(Key) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプ数値型）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlNum(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
		}
		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬのWhereを（カラムタイプOTHER）生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getWhereSqlOther(Map map, String Key, String wstr)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		//Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tb1." + Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
			whereStr = andStr;
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst440101_SiiresakiBean bean = (mst440101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_siiresaki (");
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
			befKanma = true;
			sb.append(" kanri_kb");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" kanri_cd");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanri_cd");
		}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
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
//		↓↓削除（2005.06.30）↓↓
//		if( bean.getHakoBasyoNa() != null && bean.getHakoBasyoNa().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hako_basyo_na");
//		}
//		↑↑削除（2005.06.30）↑↑
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
//	   ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
		if( bean.getAstyKb() != null && bean.getAstyKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" asty_kb");
		}
		if( bean.getHanbaiKeiyakuKb() != null && bean.getHanbaiKeiyakuKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_keiyaku_kb");
		}
//	   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

		// ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分を登録内容に追加 START
		if( bean.getSiireSystemKb() != null && bean.getSiireSystemKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_system_kb");
		}
		// ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分を登録内容に追加  END 
		
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
		//↓↓仕様追加による変更（2005.05.24）
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" eos_kb");
		}
		//↑↑仕様追加による変更（2005.05.24）

//		↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//		//↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		if( bean.getKakinSyubetuCd() != null && bean.getKakinSyubetuCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" kakin_syubetu_cd");
//		}
//		//↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
		sb.append(")Values(");


//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
			aftKanma = true;
			sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
		    sb.append("'" + mst000401_LogicBean.formatZero(conv.convertString( bean.getBumonCd() ),4) + "'");
	    }
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiiresakiCd() )
					+ conv.convertWhereString( bean.getTikuCd() ) + "'");
		}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
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
//		↓↓削除（2005.06.30）↓↓
//		if( bean.getHakoBasyoNa() != null && bean.getHakoBasyoNa().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHakoBasyoNa() ) + "'");
//		}
//		↑↑削除（2005.06.30）↑↑
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
//		↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
		if( bean.getAstyKb() != null && bean.getAstyKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAstyKb() ) + "'");
		}
		if( bean.getHanbaiKeiyakuKb() != null && bean.getHanbaiKeiyakuKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHanbaiKeiyakuKb() ) + "'");
		}
//		if( bean.getHakoBasyoNa() != null && bean.getHakoBasyoNa().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getHakoBasyoNa() ) + "'");
//		}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

		// ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分を登録内容に追加 START
		if( bean.getSiireSystemKb() != null && bean.getSiireSystemKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiireSystemKb() ) + "'");
		}
		// ADD by Tanigawa 2006/9/24 障害票№0036対応 仕入先システム区分を登録内容に追加  END 
		
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
//			↓↓2006.07.24 jianglm カスタマイズ修正↓↓
//			if( bean.getTosanKb() != null && bean.getTosanKb().trim().length() != 0 )
//			{
//				if (mst003701_TousanKbDictionary.TOUSAN.getCode().equals(bean.getTosanKb())){
//					
//					bean.setDeleteFg(mst000801_DelFlagDictionary.IRU.getCode());
//				}
//			}
//			   ↑↑2006.07.24 jianglm カスタマイズ修正↑↑
			
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
		//↓↓仕様追加による変更（2005.05.24）
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		//↑↑仕様追加による変更（2005.05.24）
//		↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//		//↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		if( bean.getKakinSyubetuCd() != null && bean.getKakinSyubetuCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKakinSyubetuCd() ) + "'");
//		}
//		
//		//↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
		
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
		mst440101_SiiresakiBean bean = (mst440101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_siiresaki set ");
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
			befKanma = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
//		}
//BUGNO-S034 2005.04.23 S.Murata START
//		if( bean.getKanjiNa() != null && bean.getKanjiNa().trim().length() != 0 )
//		{
		if (!bean.getDeleteFg().equals(mst000801_DelFlagDictionary.IRU.getCode())) {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_na = ");
			sb.append("'" + conv.convertString( bean.getKanjiNa() ) + "'");
//		}
//		if( bean.getKanaNa() != null && bean.getKanaNa().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_na = ");
			sb.append("'" + conv.convertString( bean.getKanaNa() ) + "'");
//		}
//		if( bean.getKanjiRn() != null && bean.getKanjiRn().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kanji_rn = ");
			sb.append("'" + conv.convertString( bean.getKanjiRn() ) + "'");
//		}
//		if( bean.getKanaRn() != null && bean.getKanaRn().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kana_rn = ");
			sb.append("'" + conv.convertString( bean.getKanaRn() ) + "'");
//		}
//		if( bean.getYubinCd() != null && bean.getYubinCd().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yubin_cd = ");
			sb.append("'" + conv.convertString( bean.getYubinCd() ) + "'");
//		}
//		if( bean.getAddressKanji1Na() != null && bean.getAddressKanji1Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji1_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanji1Na() ) + "'");
//		}
//		if( bean.getAddressKanji2Na() != null && bean.getAddressKanji2Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kanji2_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKanji2Na() ) + "'");
//		}
//		if( bean.getAddressKana1Na() != null && bean.getAddressKana1Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana1_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKana1Na() ) + "'");
//		}
//		if( bean.getAddressKana2Na() != null && bean.getAddressKana2Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" address_kana2_na = ");
			sb.append("'" + conv.convertString( bean.getAddressKana2Na() ) + "'");
//		}
//		if( bean.getMadoguchiTantoNa() != null && bean.getMadoguchiTantoNa().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" madoguchi_tanto_na = ");
			sb.append("'" + conv.convertString( bean.getMadoguchiTantoNa() ) + "'");
//		}
//		if( bean.getTel1Na() != null && bean.getTel1Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel1_na = ");
			sb.append("'" + conv.convertString( bean.getTel1Na() ) + "'");
//		}
//		if( bean.getTel2Na() != null && bean.getTel2Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tel2_na = ");
			sb.append("'" + conv.convertString( bean.getTel2Na() ) + "'");
//		}
//		if( bean.getFax1Na() != null && bean.getFax1Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax1_na = ");
			sb.append("'" + conv.convertString( bean.getFax1Na() ) + "'");
//		}
//		if( bean.getFax2Na() != null && bean.getFax2Na().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" fax2_na = ");
			sb.append("'" + conv.convertString( bean.getFax2Na() ) + "'");
//		}
//		if( bean.getNightTelNa() != null && bean.getNightTelNa().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" night_tel_na = ");
			sb.append("'" + conv.convertString( bean.getNightTelNa() ) + "'");
//		}
//		if( bean.getEmailNa() != null && bean.getEmailNa().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" email_na = ");
			sb.append("'" + conv.convertString( bean.getEmailNa() ) + "'");
//		}
//		if( bean.getJohosyoriSeikyuKb() != null && bean.getJohosyoriSeikyuKb().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" johosyori_seikyu_kb = ");
			sb.append("'" + conv.convertString( bean.getJohosyoriSeikyuKb() ) + "'");
//		}
//		if( bean.getSyhincdPrintKb() != null && bean.getSyhincdPrintKb().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syhincd_print_kb = ");
			sb.append("'" + conv.convertString( bean.getSyhincdPrintKb() ) + "'");
//		}
//		if( bean.getTaghakoGyosyaKb() != null && bean.getTaghakoGyosyaKb().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" taghako_gyosya_kb = ");
			sb.append("'" + conv.convertString( bean.getTaghakoGyosyaKb() ) + "'");
//		}
//		if( bean.getHakoBasyoNa() != null && bean.getHakoBasyoNa().trim().length() != 0 )
//		{
//			↓↓削除（2005.06.30）↓↓
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hako_basyo_na = ");
//			sb.append("'" + conv.convertString( bean.getHakoBasyoNa() ) + "'");
//			↑↑削除（2005.06.30）↑↑
//		}
//		if( bean.getHinbanbetuDenpyoKb() != null && bean.getHinbanbetuDenpyoKb().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinbanbetu_denpyo_kb = ");
			sb.append("'" + conv.convertString( bean.getHinbanbetuDenpyoKb() ) + "'");
//		}
//		if( bean.getMemoTx() != null && bean.getMemoTx().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" memo_tx = ");
			sb.append("'" + conv.convertString( bean.getMemoTx() ) + "'");
//		}
//		if( bean.getTosanKb() != null && bean.getTosanKb().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tosan_kb = ");
			sb.append("'" + conv.convertString( bean.getTosanKb() ) + "'");
//		}
		//↓↓仕様追加による変更（2005.05.24）
//	  if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
//	  {
		  if( befKanma ) sb.append(","); else befKanma = true;
		  sb.append(" eos_kb = ");
		  sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
//	  }
	  //↑↑仕様追加による変更（2005.05.24）

//		 ↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
// ↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓
//		  if( befKanma ) sb.append(","); else befKanma = true;
//		  sb.append(" kakin_syubetu_cd = ");
//		  sb.append("'" + conv.convertString( bean.getKakinSyubetuCd() ) + "'");
// ↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
		  
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" asty_kb = ");
			sb.append("'" + conv.convertString( bean.getAstyKb() ) + "'");
			
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_keiyaku_kb = ");
			sb.append("'" + conv.convertString( bean.getHanbaiKeiyakuKb() ) + "'");
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			
			//ADD by Tanigawa 2006/9/25 障害票№0036 仕入先システム区分を更新対象に追加 START
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_system_kb = ");
			sb.append("'" + conv.convertString( bean.getSiireSystemKb() ) + "'");
			//ADD by Tanigawa 2006/9/25 障害票№0036 仕入先システム区分を更新対象に追加  END 
			
		//DEL by Tanigawa 2006/9/26 障害票№0060対応 更新時は、作成者IDフィールド及び、作成年月日フィールドは更新対象外
		//(更新対象になると、いつ、誰が作成したデータか分からなくなるため) START
////		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
////		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_ts = ");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
////		}
////		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
////		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_user_id = ");
//			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
////		}
		//DEL by Tanigawa 2006/9/26 障害票№0060対応  END

//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
//		{
		}
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
//		}
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
			
		}
		
//BUGNO-S034 2005.04.23 S.Murata END


		sb.append(" WHERE");

//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" kanri_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + mst000401_LogicBean.formatZero(conv.convertWhereString( bean.getBumonCd() ),4) + "'");
		}
//		BUGNO-S005 2005.04.22 T.Makuta START
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_kb = ");
			sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
//		}
//		BUGNO-S005 2005.04.22 T.Makuta END
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() )
					+ conv.convertWhereString( bean.getTikuCd() ) + "'");
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
		mst440101_SiiresakiBean bean = (mst440101_SiiresakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_siiresaki where ");
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		sb.append(" kanri_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" kanri_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		sb.append("'" + mst000401_LogicBean.formatZero(conv.convertWhereString( bean.getBumonCd() ),4) + "'");
		sb.append(" AND");
		sb.append(" kanri_kb = ");
		sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode() + "'");
		sb.append(" AND");
		sb.append(" siiresaki_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() )
				+ conv.convertWhereString( bean.getTikuCd() ) + "'");
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
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
