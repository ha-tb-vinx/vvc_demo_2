/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst810101_Keiryoki用計量器マスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst810101_Keiryoki用計量器マスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst810101_Keiryoki用計量器マスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst810101_Keiryoki用計量器マスタのDMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst810101_KeiryokiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst810101_KeiryokiDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR);
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
		mst810101_KeiryokiBean bean = new mst810101_KeiryokiBean();
		bean.setKeiryoHankuCd( rest.getString("keiryo_hanku_cd") );
		bean.setSyohinYobidasi( rest.getString("syohin_yobidasi") );
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setSyohinKbn( rest.getString("syohin_kbn") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setKeiryokiNa( rest.getString("keiryoki_na") );
		bean.setKeiryoki2Na( rest.getString("keiryoki2_na") );
		bean.setKeiryoki3Na( rest.getString("keiryoki3_na") );
		bean.setReceiptHinmeiNa( rest.getString("receipt_hinmei_na") );
		bean.setTeigakuUpKb( rest.getString("teigaku_up_kb") );
		bean.setTankaVl( rest.getLong("tanka_vl") );
		bean.setTeigakuVl( rest.getLong("teigaku_vl") );
		bean.setTeigakujiTaniKb( rest.getString("teigakuji_tani_kb") );
		bean.setSyomikikanKb( rest.getString("syomikikan_kb") );
		bean.setSyomikikanVl( rest.getLong("syomikikan_vl") );
		bean.setSantiKb( rest.getString("santi_kb") );
		bean.setKakojikokuPrintKb( rest.getString("kakojikoku_print_kb") );
		bean.setChoriyoKokokubunKb( rest.getString("choriyo_kokokubun_kb") );
		bean.setHozonOndotaiKb( rest.getString("hozon_ondotai_kb") );
		bean.setStartKb( rest.getString("start_kb") );
		bean.setBackLabelKb( rest.getString("back_label_kb") );
		bean.setEiyoSeibunNa( rest.getString("eiyo_seibun_na") );
		bean.setCommentKb( rest.getString("comment_kb") );
		bean.setBikoTx( rest.getString("biko_tx") );
		bean.setGenzairyoNa( rest.getString("genzairyo_na") );
		bean.setTenkabutuNa( rest.getString("tenkabutu_na") );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("TRIM(TBL1.keiryo_hanku_cd) keiryo_hanku_cd ");
		sb.append(", ");
		sb.append("TRIM(TBL1.syohin_yobidasi) syohin_yobidasi ");
		sb.append(", ");
		sb.append("TRIM(TBL1.s_gyosyu_cd) s_gyosyu_cd ");
		sb.append(", ");
		sb.append("TRIM (theme_cd) theme_cd ");
		sb.append(", ");
		sb.append("TRIM(TBL1.hanei_dt) hanei_dt ");
		sb.append(", ");
		sb.append("TRIM(TBL1.syohin_kbn) syohin_kbn ");
		sb.append(", ");
		sb.append("TRIM(TBL1.syohin_cd) syohin_cd ");
		sb.append(", ");

		sb.append("( ");
		sb.append("		SELECT ");
		sb.append("			HINMEI_KANJI_NA ");
		sb.append("		FROM ");
		sb.append("			R_SYOHIN SYOHIN ");
		sb.append("		WHERE ");
		sb.append("			SYOHIN.YUKO_DT = ");
		sb.append("			( ");
		sb.append("				SELECT ");
		sb.append("					MAX(YUKO_DT) YUKO_DT ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN ");
		sb.append("				WHERE ");
// 2006.01.24 Y.Inaba Mod ↓
//		sb.append("					YUKO_DT<=to_char(SYSDATE,'yyyymmdd') ");
		sb.append("					YUKO_DT<='" + RMSTDATEUtil.getMstDateDt() + "' ");
// 2006.01.24 Y.Inaba Mod ↑
		sb.append("				AND	SYOHIN_CD = TBL1.SYOHIN_CD ");
		sb.append("			) ");
		sb.append("		AND	SYOHIN_CD = TBL1.SYOHIN_CD ");
		sb.append("		AND	ROWNUM <= 1 ");
		sb.append(") hinmei_kanji_na, ");

		sb.append("TBL1.keiryoki_na ");
		sb.append(", ");
		sb.append("TBL1.keiryoki2_na ");
		sb.append(", ");
		sb.append("TBL1.keiryoki3_na ");
		sb.append(", ");
		sb.append("TRIM(TBL1.receipt_hinmei_na) receipt_hinmei_na ");
		sb.append(", ");
		sb.append("TRIM(TBL1.teigaku_up_kb) teigaku_up_kb ");
		sb.append(", ");
		sb.append("TBL1.tanka_vl ");
		sb.append(", ");
		sb.append("TBL1.teigaku_vl ");
		sb.append(", ");
		sb.append("TRIM(TBL1.teigakuji_tani_kb) teigakuji_tani_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.syomikikan_kb) syomikikan_kb ");
		sb.append(", ");
		sb.append("TBL1.syomikikan_vl ");
		sb.append(", ");
		sb.append("TRIM(TBL1.santi_kb) santi_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.kakojikoku_print_kb) kakojikoku_print_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.choriyo_kokokubun_kb) choriyo_kokokubun_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.hozon_ondotai_kb) hozon_ondotai_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.start_kb) start_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL1.back_label_kb) back_label_kb ");
		sb.append(", ");
		sb.append("TBL1.eiyo_seibun_na ");
		sb.append(", ");
		sb.append("TRIM(TBL1.comment_kb) comment_kb ");
		sb.append(", ");
		sb.append("TBL1.biko_tx ");
		sb.append(", ");
		sb.append("TBL1.genzairyo_na ");
		sb.append(", ");
		sb.append("TBL1.tenkabutu_na ");
		sb.append(", ");
		sb.append("TRIM(TBL1.insert_ts) insert_ts ");
		sb.append(", ");
		sb.append("TRIM(TBL1.insert_user_id) insert_user_id ");
		sb.append(", ");
		sb.append("TRIM(TBL1.update_ts) update_ts ");
		sb.append(", ");
		sb.append("TRIM(TBL1.update_user_id) update_user_id ");
		sb.append(", ");
		sb.append("TBL1.delete_fg ");
		sb.append("from r_keiryoki TBL1 ");


		// keiryo_hanku_cd に対するWHERE区
		if( map.get("keiryo_hanku_cd_bef") != null && ((String)map.get("keiryo_hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd >= '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_aft") != null && ((String)map.get("keiryo_hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd <= '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd") != null && ((String)map.get("keiryo_hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_like") != null && ((String)map.get("keiryo_hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd like '%" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_bef_like") != null && ((String)map.get("keiryo_hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd like '%" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_aft_like") != null && ((String)map.get("keiryo_hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd like '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_in") != null && ((String)map.get("keiryo_hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryo_hanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("keiryo_hanku_cd_not_in") != null && ((String)map.get("keiryo_hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryo_hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryo_hanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_yobidasi に対するWHERE区
		if( map.get("syohin_yobidasi_bef") != null && ((String)map.get("syohin_yobidasi_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi >= '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_aft") != null && ((String)map.get("syohin_yobidasi_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi <= '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi") != null && ((String)map.get("syohin_yobidasi")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi = '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_like") != null && ((String)map.get("syohin_yobidasi_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi like '%" + conv.convertWhereString( (String)map.get("syohin_yobidasi_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_bef_like") != null && ((String)map.get("syohin_yobidasi_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi like '%" + conv.convertWhereString( (String)map.get("syohin_yobidasi_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_aft_like") != null && ((String)map.get("syohin_yobidasi_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi like '" + conv.convertWhereString( (String)map.get("syohin_yobidasi_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_in") != null && ((String)map.get("syohin_yobidasi_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_yobidasi_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_yobidasi_not_in") != null && ((String)map.get("syohin_yobidasi_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_yobidasi not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_yobidasi_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_gyosyu_cd に対するWHERE区
		if( map.get("s_gyosyu_cd_bef") != null && ((String)map.get("s_gyosyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft") != null && ((String)map.get("s_gyosyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_like") != null && ((String)map.get("s_gyosyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_bef_like") != null && ((String)map.get("s_gyosyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft_like") != null && ((String)map.get("s_gyosyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd like '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_in") != null && ((String)map.get("s_gyosyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_not_in") != null && ((String)map.get("s_gyosyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.s_gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// theme_cd に対するWHERE区
		if( map.get("theme_cd_bef") != null && ((String)map.get("theme_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd >= '" + conv.convertWhereString( (String)map.get("theme_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_aft") != null && ((String)map.get("theme_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd <= '" + conv.convertWhereString( (String)map.get("theme_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd") != null && ((String)map.get("theme_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_like") != null && ((String)map.get("theme_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd like '%" + conv.convertWhereString( (String)map.get("theme_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_bef_like") != null && ((String)map.get("theme_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd like '%" + conv.convertWhereString( (String)map.get("theme_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_aft_like") != null && ((String)map.get("theme_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd like '" + conv.convertWhereString( (String)map.get("theme_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("theme_cd_in") != null && ((String)map.get("theme_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("theme_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("theme_cd_not_in") != null && ((String)map.get("theme_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.theme_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("theme_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanei_dt に対するWHERE区
		if( map.get("hanei_dt_bef") != null && ((String)map.get("hanei_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt >= '" + conv.convertWhereString( (String)map.get("hanei_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_aft") != null && ((String)map.get("hanei_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt <= '" + conv.convertWhereString( (String)map.get("hanei_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt") != null && ((String)map.get("hanei_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt = '" + conv.convertWhereString( (String)map.get("hanei_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_like") != null && ((String)map.get("hanei_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt like '%" + conv.convertWhereString( (String)map.get("hanei_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_bef_like") != null && ((String)map.get("hanei_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt like '%" + conv.convertWhereString( (String)map.get("hanei_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_aft_like") != null && ((String)map.get("hanei_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt like '" + conv.convertWhereString( (String)map.get("hanei_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_in") != null && ((String)map.get("hanei_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanei_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanei_dt_not_in") != null && ((String)map.get("hanei_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hanei_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanei_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_kbn に対するWHERE区
		if( map.get("syohin_kbn_bef") != null && ((String)map.get("syohin_kbn_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn >= '" + conv.convertWhereString( (String)map.get("syohin_kbn_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_aft") != null && ((String)map.get("syohin_kbn_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn <= '" + conv.convertWhereString( (String)map.get("syohin_kbn_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn") != null && ((String)map.get("syohin_kbn")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn = '" + conv.convertWhereString( (String)map.get("syohin_kbn") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_like") != null && ((String)map.get("syohin_kbn_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn like '%" + conv.convertWhereString( (String)map.get("syohin_kbn_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_bef_like") != null && ((String)map.get("syohin_kbn_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn like '%" + conv.convertWhereString( (String)map.get("syohin_kbn_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_aft_like") != null && ((String)map.get("syohin_kbn_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn like '" + conv.convertWhereString( (String)map.get("syohin_kbn_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_in") != null && ((String)map.get("syohin_kbn_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kbn_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_kbn_not_in") != null && ((String)map.get("syohin_kbn_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_kbn not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kbn_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd_bef") != null && ((String)map.get("syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft") != null && ((String)map.get("syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd <= '" + conv.convertWhereString( (String)map.get("syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_like") != null && ((String)map.get("syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_bef_like") != null && ((String)map.get("syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft_like") != null && ((String)map.get("syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd like '" + conv.convertWhereString( (String)map.get("syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_in") != null && ((String)map.get("syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_not_in") != null && ((String)map.get("syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// keiryoki_na に対するWHERE区
		if( map.get("keiryoki_na_bef") != null && ((String)map.get("keiryoki_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na >= '" + conv.convertWhereString( (String)map.get("keiryoki_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_aft") != null && ((String)map.get("keiryoki_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na <= '" + conv.convertWhereString( (String)map.get("keiryoki_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na") != null && ((String)map.get("keiryoki_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na = '" + conv.convertWhereString( (String)map.get("keiryoki_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_like") != null && ((String)map.get("keiryoki_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na like '%" + conv.convertWhereString( (String)map.get("keiryoki_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_bef_like") != null && ((String)map.get("keiryoki_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na like '%" + conv.convertWhereString( (String)map.get("keiryoki_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_aft_like") != null && ((String)map.get("keiryoki_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na like '" + conv.convertWhereString( (String)map.get("keiryoki_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_in") != null && ((String)map.get("keiryoki_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryoki_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("keiryoki_na_not_in") != null && ((String)map.get("keiryoki_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.keiryoki_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiryoki_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// receipt_hinmei_na に対するWHERE区
		if( map.get("receipt_hinmei_na_bef") != null && ((String)map.get("receipt_hinmei_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na >= '" + conv.convertWhereString( (String)map.get("receipt_hinmei_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_aft") != null && ((String)map.get("receipt_hinmei_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na <= '" + conv.convertWhereString( (String)map.get("receipt_hinmei_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na") != null && ((String)map.get("receipt_hinmei_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na = '" + conv.convertWhereString( (String)map.get("receipt_hinmei_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_like") != null && ((String)map.get("receipt_hinmei_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na like '%" + conv.convertWhereString( (String)map.get("receipt_hinmei_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_bef_like") != null && ((String)map.get("receipt_hinmei_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na like '%" + conv.convertWhereString( (String)map.get("receipt_hinmei_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_aft_like") != null && ((String)map.get("receipt_hinmei_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na like '" + conv.convertWhereString( (String)map.get("receipt_hinmei_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_in") != null && ((String)map.get("receipt_hinmei_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("receipt_hinmei_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("receipt_hinmei_na_not_in") != null && ((String)map.get("receipt_hinmei_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.receipt_hinmei_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("receipt_hinmei_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// teigaku_up_kb に対するWHERE区
		if( map.get("teigaku_up_kb_bef") != null && ((String)map.get("teigaku_up_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb >= '" + conv.convertWhereString( (String)map.get("teigaku_up_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_aft") != null && ((String)map.get("teigaku_up_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb <= '" + conv.convertWhereString( (String)map.get("teigaku_up_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb") != null && ((String)map.get("teigaku_up_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb = '" + conv.convertWhereString( (String)map.get("teigaku_up_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_like") != null && ((String)map.get("teigaku_up_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb like '%" + conv.convertWhereString( (String)map.get("teigaku_up_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_bef_like") != null && ((String)map.get("teigaku_up_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb like '%" + conv.convertWhereString( (String)map.get("teigaku_up_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_aft_like") != null && ((String)map.get("teigaku_up_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb like '" + conv.convertWhereString( (String)map.get("teigaku_up_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_in") != null && ((String)map.get("teigaku_up_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teigaku_up_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("teigaku_up_kb_not_in") != null && ((String)map.get("teigaku_up_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_up_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teigaku_up_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tanka_vl に対するWHERE区
		if( map.get("tanka_vl_bef") != null && ((String)map.get("tanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tanka_vl >= " + (String)map.get("tanka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("tanka_vl_aft") != null && ((String)map.get("tanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tanka_vl <= " + (String)map.get("tanka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("tanka_vl") != null && ((String)map.get("tanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("TBL1.tanka_vl = " + (String)map.get("tanka_vl"));
			whereStr = andStr;
		}
		if( map.get("tanka_vl_in") != null && ((String)map.get("tanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tanka_vl in ( " + conv.convertWhereString( (String)map.get("tanka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("tanka_vl_not_in") != null && ((String)map.get("tanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tanka_vl not in ( " + conv.convertWhereString( (String)map.get("tanka_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// teigaku_vl に対するWHERE区
		if( map.get("teigaku_vl_bef") != null && ((String)map.get("teigaku_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_vl >= " + (String)map.get("teigaku_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("teigaku_vl_aft") != null && ((String)map.get("teigaku_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_vl <= " + (String)map.get("teigaku_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("teigaku_vl") != null && ((String)map.get("teigaku_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_vl = " + (String)map.get("teigaku_vl"));
			whereStr = andStr;
		}
		if( map.get("teigaku_vl_in") != null && ((String)map.get("teigaku_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_vl in ( " + conv.convertWhereString( (String)map.get("teigaku_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("teigaku_vl_not_in") != null && ((String)map.get("teigaku_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigaku_vl not in ( " + conv.convertWhereString( (String)map.get("teigaku_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// teigakuji_tani_kb に対するWHERE区
		if( map.get("teigakuji_tani_kb_bef") != null && ((String)map.get("teigakuji_tani_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb >= '" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_aft") != null && ((String)map.get("teigakuji_tani_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb <= '" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb") != null && ((String)map.get("teigakuji_tani_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb = '" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_like") != null && ((String)map.get("teigakuji_tani_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb like '%" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_bef_like") != null && ((String)map.get("teigakuji_tani_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb like '%" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_aft_like") != null && ((String)map.get("teigakuji_tani_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb like '" + conv.convertWhereString( (String)map.get("teigakuji_tani_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_in") != null && ((String)map.get("teigakuji_tani_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teigakuji_tani_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("teigakuji_tani_kb_not_in") != null && ((String)map.get("teigakuji_tani_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.teigakuji_tani_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teigakuji_tani_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syomikikan_kb に対するWHERE区
		if( map.get("syomikikan_kb_bef") != null && ((String)map.get("syomikikan_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb >= '" + conv.convertWhereString( (String)map.get("syomikikan_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_aft") != null && ((String)map.get("syomikikan_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb <= '" + conv.convertWhereString( (String)map.get("syomikikan_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb") != null && ((String)map.get("syomikikan_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb = '" + conv.convertWhereString( (String)map.get("syomikikan_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_like") != null && ((String)map.get("syomikikan_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb like '%" + conv.convertWhereString( (String)map.get("syomikikan_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_bef_like") != null && ((String)map.get("syomikikan_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb like '%" + conv.convertWhereString( (String)map.get("syomikikan_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_aft_like") != null && ((String)map.get("syomikikan_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb like '" + conv.convertWhereString( (String)map.get("syomikikan_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_in") != null && ((String)map.get("syomikikan_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_not_in") != null && ((String)map.get("syomikikan_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syomikikan_vl に対するWHERE区
		if( map.get("syomikikan_vl_bef") != null && ((String)map.get("syomikikan_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl >= '" + conv.convertWhereString( (String)map.get("syomikikan_vl_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_aft") != null && ((String)map.get("syomikikan_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl <= '" + conv.convertWhereString( (String)map.get("syomikikan_vl_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl") != null && ((String)map.get("syomikikan_vl")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl = '" + conv.convertWhereString( (String)map.get("syomikikan_vl") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_like") != null && ((String)map.get("syomikikan_vl_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl like '%" + conv.convertWhereString( (String)map.get("syomikikan_vl_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_bef_like") != null && ((String)map.get("syomikikan_vl_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl like '%" + conv.convertWhereString( (String)map.get("syomikikan_vl_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_aft_like") != null && ((String)map.get("syomikikan_vl_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl like '" + conv.convertWhereString( (String)map.get("syomikikan_vl_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_in") != null && ((String)map.get("syomikikan_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_vl_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syomikikan_vl_not_in") != null && ((String)map.get("syomikikan_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.syomikikan_vl not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_vl_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// santi_kb に対するWHERE区
		if( map.get("santi_kb_bef") != null && ((String)map.get("santi_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.santi_kb >= " + (String)map.get("santi_kb_bef") );
			whereStr = andStr;
		}
		if( map.get("santi_kb_aft") != null && ((String)map.get("santi_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.santi_kb <= " + (String)map.get("santi_kb_aft") );
			whereStr = andStr;
		}
		if( map.get("santi_kb") != null && ((String)map.get("santi_kb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("TBL1.santi_kb = " + (String)map.get("santi_kb"));
			whereStr = andStr;
		}
		if( map.get("santi_kb_in") != null && ((String)map.get("santi_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.santi_kb in ( " + conv.convertWhereString( (String)map.get("santi_kb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("santi_kb_not_in") != null && ((String)map.get("santi_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.santi_kb not in ( " + conv.convertWhereString( (String)map.get("santi_kb_not_in") ) + " )");
			whereStr = andStr;
		}


		// kakojikoku_print_kb に対するWHERE区
		if( map.get("kakojikoku_print_kb_bef") != null && ((String)map.get("kakojikoku_print_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb >= '" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_aft") != null && ((String)map.get("kakojikoku_print_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb <= '" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb") != null && ((String)map.get("kakojikoku_print_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb = '" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_like") != null && ((String)map.get("kakojikoku_print_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb like '%" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_bef_like") != null && ((String)map.get("kakojikoku_print_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb like '%" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_aft_like") != null && ((String)map.get("kakojikoku_print_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb like '" + conv.convertWhereString( (String)map.get("kakojikoku_print_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_in") != null && ((String)map.get("kakojikoku_print_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kakojikoku_print_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kakojikoku_print_kb_not_in") != null && ((String)map.get("kakojikoku_print_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.kakojikoku_print_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kakojikoku_print_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// choriyo_kokokubun_kb に対するWHERE区
		if( map.get("choriyo_kokokubun_kb_bef") != null && ((String)map.get("choriyo_kokokubun_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb >= '" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_aft") != null && ((String)map.get("choriyo_kokokubun_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb <= '" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb") != null && ((String)map.get("choriyo_kokokubun_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb = '" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_like") != null && ((String)map.get("choriyo_kokokubun_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb like '%" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_bef_like") != null && ((String)map.get("choriyo_kokokubun_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb like '%" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_aft_like") != null && ((String)map.get("choriyo_kokokubun_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb like '" + conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_in") != null && ((String)map.get("choriyo_kokokubun_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("choriyo_kokokubun_kb_not_in") != null && ((String)map.get("choriyo_kokokubun_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.choriyo_kokokubun_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("choriyo_kokokubun_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hozon_ondotai_kb に対するWHERE区
		if( map.get("hozon_ondotai_kb_bef") != null && ((String)map.get("hozon_ondotai_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb >= '" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_aft") != null && ((String)map.get("hozon_ondotai_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb <= '" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb") != null && ((String)map.get("hozon_ondotai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb = '" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_like") != null && ((String)map.get("hozon_ondotai_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb like '%" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_bef_like") != null && ((String)map.get("hozon_ondotai_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb like '%" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_aft_like") != null && ((String)map.get("hozon_ondotai_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb like '" + conv.convertWhereString( (String)map.get("hozon_ondotai_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_in") != null && ((String)map.get("hozon_ondotai_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hozon_ondotai_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hozon_ondotai_kb_not_in") != null && ((String)map.get("hozon_ondotai_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.hozon_ondotai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hozon_ondotai_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// start_kb に対するWHERE区
		if( map.get("start_kb_bef") != null && ((String)map.get("start_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb >= '" + conv.convertWhereString( (String)map.get("start_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("start_kb_aft") != null && ((String)map.get("start_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb <= '" + conv.convertWhereString( (String)map.get("start_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("start_kb") != null && ((String)map.get("start_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb = '" + conv.convertWhereString( (String)map.get("start_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("start_kb_like") != null && ((String)map.get("start_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb like '%" + conv.convertWhereString( (String)map.get("start_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("start_kb_bef_like") != null && ((String)map.get("start_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb like '%" + conv.convertWhereString( (String)map.get("start_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("start_kb_aft_like") != null && ((String)map.get("start_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb like '" + conv.convertWhereString( (String)map.get("start_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("start_kb_in") != null && ((String)map.get("start_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("start_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("start_kb_not_in") != null && ((String)map.get("start_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.start_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("start_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// back_label_kb に対するWHERE区
		if( map.get("back_label_kb_bef") != null && ((String)map.get("back_label_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb >= '" + conv.convertWhereString( (String)map.get("back_label_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_aft") != null && ((String)map.get("back_label_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb <= '" + conv.convertWhereString( (String)map.get("back_label_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb") != null && ((String)map.get("back_label_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb = '" + conv.convertWhereString( (String)map.get("back_label_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_like") != null && ((String)map.get("back_label_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb like '%" + conv.convertWhereString( (String)map.get("back_label_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_bef_like") != null && ((String)map.get("back_label_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb like '%" + conv.convertWhereString( (String)map.get("back_label_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_aft_like") != null && ((String)map.get("back_label_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb like '" + conv.convertWhereString( (String)map.get("back_label_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_in") != null && ((String)map.get("back_label_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("back_label_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("back_label_kb_not_in") != null && ((String)map.get("back_label_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.back_label_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("back_label_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// eiyo_seibun_na に対するWHERE区
		if( map.get("eiyo_seibun_na_bef") != null && ((String)map.get("eiyo_seibun_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na >= '" + conv.convertWhereString( (String)map.get("eiyo_seibun_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_aft") != null && ((String)map.get("eiyo_seibun_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na <= '" + conv.convertWhereString( (String)map.get("eiyo_seibun_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na") != null && ((String)map.get("eiyo_seibun_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na = '" + conv.convertWhereString( (String)map.get("eiyo_seibun_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_like") != null && ((String)map.get("eiyo_seibun_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na like '%" + conv.convertWhereString( (String)map.get("eiyo_seibun_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_bef_like") != null && ((String)map.get("eiyo_seibun_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na like '%" + conv.convertWhereString( (String)map.get("eiyo_seibun_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_aft_like") != null && ((String)map.get("eiyo_seibun_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na like '" + conv.convertWhereString( (String)map.get("eiyo_seibun_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_in") != null && ((String)map.get("eiyo_seibun_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eiyo_seibun_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("eiyo_seibun_na_not_in") != null && ((String)map.get("eiyo_seibun_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.eiyo_seibun_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("eiyo_seibun_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// comment_kb に対するWHERE区
		if( map.get("comment_kb_bef") != null && ((String)map.get("comment_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb >= '" + conv.convertWhereString( (String)map.get("comment_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("comment_kb_aft") != null && ((String)map.get("comment_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb <= '" + conv.convertWhereString( (String)map.get("comment_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("comment_kb") != null && ((String)map.get("comment_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb = '" + conv.convertWhereString( (String)map.get("comment_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("comment_kb_like") != null && ((String)map.get("comment_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb like '%" + conv.convertWhereString( (String)map.get("comment_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("comment_kb_bef_like") != null && ((String)map.get("comment_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb like '%" + conv.convertWhereString( (String)map.get("comment_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("comment_kb_aft_like") != null && ((String)map.get("comment_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb like '" + conv.convertWhereString( (String)map.get("comment_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("comment_kb_in") != null && ((String)map.get("comment_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("comment_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("comment_kb_not_in") != null && ((String)map.get("comment_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.comment_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("comment_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// biko_tx に対するWHERE区
		if( map.get("biko_tx_bef") != null && ((String)map.get("biko_tx_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx >= '" + conv.convertWhereString( (String)map.get("biko_tx_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("biko_tx_aft") != null && ((String)map.get("biko_tx_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx <= '" + conv.convertWhereString( (String)map.get("biko_tx_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("biko_tx") != null && ((String)map.get("biko_tx")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx = '" + conv.convertWhereString( (String)map.get("biko_tx") ) + "'");
			whereStr = andStr;
		}
		if( map.get("biko_tx_like") != null && ((String)map.get("biko_tx_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx like '%" + conv.convertWhereString( (String)map.get("biko_tx_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("biko_tx_bef_like") != null && ((String)map.get("biko_tx_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx like '%" + conv.convertWhereString( (String)map.get("biko_tx_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("biko_tx_aft_like") != null && ((String)map.get("biko_tx_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx like '" + conv.convertWhereString( (String)map.get("biko_tx_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("biko_tx_in") != null && ((String)map.get("biko_tx_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx in ( '" + replaceAll(conv.convertWhereString( (String)map.get("biko_tx_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("biko_tx_not_in") != null && ((String)map.get("biko_tx_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.biko_tx not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("biko_tx_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// genzairyo_na に対するWHERE区
		if( map.get("genzairyo_na_bef") != null && ((String)map.get("genzairyo_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na >= '" + conv.convertWhereString( (String)map.get("genzairyo_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_aft") != null && ((String)map.get("genzairyo_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na <= '" + conv.convertWhereString( (String)map.get("genzairyo_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na") != null && ((String)map.get("genzairyo_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na = '" + conv.convertWhereString( (String)map.get("genzairyo_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_like") != null && ((String)map.get("genzairyo_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na like '%" + conv.convertWhereString( (String)map.get("genzairyo_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_bef_like") != null && ((String)map.get("genzairyo_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na like '%" + conv.convertWhereString( (String)map.get("genzairyo_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_aft_like") != null && ((String)map.get("genzairyo_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na like '" + conv.convertWhereString( (String)map.get("genzairyo_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_in") != null && ((String)map.get("genzairyo_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("genzairyo_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("genzairyo_na_not_in") != null && ((String)map.get("genzairyo_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.genzairyo_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("genzairyo_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenkabutu_na に対するWHERE区
		if( map.get("tenkabutu_na_bef") != null && ((String)map.get("tenkabutu_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na >= '" + conv.convertWhereString( (String)map.get("tenkabutu_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_aft") != null && ((String)map.get("tenkabutu_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na <= '" + conv.convertWhereString( (String)map.get("tenkabutu_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na") != null && ((String)map.get("tenkabutu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na = '" + conv.convertWhereString( (String)map.get("tenkabutu_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_like") != null && ((String)map.get("tenkabutu_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na like '%" + conv.convertWhereString( (String)map.get("tenkabutu_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_bef_like") != null && ((String)map.get("tenkabutu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na like '%" + conv.convertWhereString( (String)map.get("tenkabutu_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_aft_like") != null && ((String)map.get("tenkabutu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na like '" + conv.convertWhereString( (String)map.get("tenkabutu_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_in") != null && ((String)map.get("tenkabutu_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenkabutu_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenkabutu_na_not_in") != null && ((String)map.get("tenkabutu_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.tenkabutu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenkabutu_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_ts に対するWHERE区
		if( map.get("insert_ts_bef") != null && ((String)map.get("insert_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts >= '" + conv.convertWhereString( (String)map.get("insert_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft") != null && ((String)map.get("insert_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts <= '" + conv.convertWhereString( (String)map.get("insert_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_like") != null && ((String)map.get("insert_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_bef_like") != null && ((String)map.get("insert_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '%" + conv.convertWhereString( (String)map.get("insert_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_aft_like") != null && ((String)map.get("insert_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts like '" + conv.convertWhereString( (String)map.get("insert_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_ts_in") != null && ((String)map.get("insert_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_ts_not_in") != null && ((String)map.get("insert_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id_bef") != null && ((String)map.get("insert_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id >= '" + conv.convertWhereString( (String)map.get("insert_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft") != null && ((String)map.get("insert_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id <= '" + conv.convertWhereString( (String)map.get("insert_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_like") != null && ((String)map.get("insert_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_bef_like") != null && ((String)map.get("insert_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '%" + conv.convertWhereString( (String)map.get("insert_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_aft_like") != null && ((String)map.get("insert_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id like '" + conv.convertWhereString( (String)map.get("insert_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_in") != null && ((String)map.get("insert_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_user_id_not_in") != null && ((String)map.get("insert_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.insert_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_ts に対するWHERE区
		if( map.get("update_ts_bef") != null && ((String)map.get("update_ts_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts >= '" + conv.convertWhereString( (String)map.get("update_ts_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft") != null && ((String)map.get("update_ts_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts <= '" + conv.convertWhereString( (String)map.get("update_ts_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_like") != null && ((String)map.get("update_ts_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_bef_like") != null && ((String)map.get("update_ts_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '%" + conv.convertWhereString( (String)map.get("update_ts_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts_aft_like") != null && ((String)map.get("update_ts_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts like '" + conv.convertWhereString( (String)map.get("update_ts_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_ts_in") != null && ((String)map.get("update_ts_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_ts_not_in") != null && ((String)map.get("update_ts_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_ts not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_ts_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_user_id に対するWHERE区
		if( map.get("update_user_id_bef") != null && ((String)map.get("update_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id >= '" + conv.convertWhereString( (String)map.get("update_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft") != null && ((String)map.get("update_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id <= '" + conv.convertWhereString( (String)map.get("update_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_like") != null && ((String)map.get("update_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_bef_like") != null && ((String)map.get("update_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '%" + conv.convertWhereString( (String)map.get("update_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_aft_like") != null && ((String)map.get("update_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id like '" + conv.convertWhereString( (String)map.get("update_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_user_id_in") != null && ((String)map.get("update_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_user_id_not_in") != null && ((String)map.get("update_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.update_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_user_id_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// delete_fg に対するWHERE区
		if( map.get("delete_fg_bef") != null && ((String)map.get("delete_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg >= '" + conv.convertWhereString( (String)map.get("delete_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft") != null && ((String)map.get("delete_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg <= '" + conv.convertWhereString( (String)map.get("delete_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_like") != null && ((String)map.get("delete_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_bef_like") != null && ((String)map.get("delete_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '%" + conv.convertWhereString( (String)map.get("delete_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_aft_like") != null && ((String)map.get("delete_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg like '" + conv.convertWhereString( (String)map.get("delete_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("delete_fg_in") != null && ((String)map.get("delete_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("delete_fg_not_in") != null && ((String)map.get("delete_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL1.delete_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("delete_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("TBL1.keiryo_hanku_cd");
		sb.append(",");
		sb.append("TBL1.syohin_yobidasi");
		sb.append(",");
		sb.append("TBL1.s_gyosyu_cd");
		sb.append(",");
		sb.append("TBL1.theme_cd");
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
		mst810101_KeiryokiBean bean = (mst810101_KeiryokiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_keiryoki (");
		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" keiryo_hanku_cd");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_yobidasi");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_gyosyu_cd");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" theme_cd");
		}
		if( bean.getHaneiDt() != null && bean.getHaneiDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanei_dt");
		}
		if( bean.getSyohinKbn() != null && bean.getSyohinKbn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_kbn");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getKeiryokiNa() != null && bean.getKeiryokiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki_na");
		}
		if( bean.getKeiryoki2Na() != null && bean.getKeiryoki2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki2_na");
		}
		if( bean.getKeiryoki3Na() != null && bean.getKeiryoki3Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki3_na");
		}
		if( bean.getReceiptHinmeiNa() != null && bean.getReceiptHinmeiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" receipt_hinmei_na");
		}
		if( bean.getTeigakuUpKb() != null && bean.getTeigakuUpKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" teigaku_up_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" tanka_vl");
		sb.append(",");
		sb.append(" teigaku_vl");
		if( bean.getTeigakujiTaniKb() != null && bean.getTeigakujiTaniKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" teigakuji_tani_kb");
		}
		if( bean.getSyomikikanKb() != null && bean.getSyomikikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syomikikan_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" syomikikan_vl");
		if( bean.getSantiKb() != null && bean.getSantiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" santi_kb");
		}
		if( bean.getKakojikokuPrintKb() != null && bean.getKakojikokuPrintKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kakojikoku_print_kb");
		}
		if( bean.getChoriyoKokokubunKb() != null && bean.getChoriyoKokokubunKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" choriyo_kokokubun_kb");
		}
		if( bean.getHozonOndotaiKb() != null && bean.getHozonOndotaiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hozon_ondotai_kb");
		}
		if( bean.getStartKb() != null && bean.getStartKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" start_kb");
		}
		if( bean.getBackLabelKb() != null && bean.getBackLabelKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" back_label_kb");
		}
		if( bean.getEiyoSeibunNa() != null && bean.getEiyoSeibunNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eiyo_seibun_na");
		}
		if( bean.getCommentKb() != null && bean.getCommentKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" comment_kb");
		}
		if( bean.getBikoTx() != null && bean.getBikoTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" biko_tx");
		}
		if( bean.getGenzairyoNa() != null && bean.getGenzairyoNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" genzairyo_na");
		}
		if( bean.getTenkabutuNa() != null && bean.getTenkabutuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenkabutu_na");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}


		sb.append(")Values(");


		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getKeiryoHankuCd() ) + "')");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getSyohinYobidasi() ) + "')");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getSGyosyuCd() ) + "')");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getThemeCd() ) + "')");
		}
		if( bean.getHaneiDt() != null && bean.getHaneiDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getHaneiDt() ) + "')");
		}
		if( bean.getSyohinKbn() != null && bean.getSyohinKbn().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getSyohinKbn() ) + "')");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getSyohinCd() ) + "')");
		}
		if( bean.getKeiryokiNa() != null && bean.getKeiryokiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiryokiNa() ) + "'");
		}
		if( bean.getKeiryoki2Na() != null && bean.getKeiryoki2Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiryoki2Na() ) + "'");
		}
		if( bean.getKeiryoki3Na() != null && bean.getKeiryoki3Na().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiryoki3Na() ) + "'");
		}
		if( bean.getReceiptHinmeiNa() != null && bean.getReceiptHinmeiNa().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getReceiptHinmeiNa() ) + "')");
		}
		if( bean.getTeigakuUpKb() != null && bean.getTeigakuUpKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("TRIM('" + conv.convertString( bean.getTeigakuUpKb() ) + "')");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getTankaVlString());
		sb.append(",");
		sb.append( bean.getTeigakuVlString());
		if( bean.getTeigakujiTaniKb() != null && bean.getTeigakujiTaniKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getTeigakujiTaniKb() ) + "')");
		}
		if( bean.getSyomikikanKb() != null && bean.getSyomikikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getSyomikikanKb() ) + "')");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getSyomikikanVl() );
		if( bean.getSantiKb() != null && bean.getSantiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getSantiKb() ) + "')");
		}
		if( bean.getKakojikokuPrintKb() != null && bean.getKakojikokuPrintKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getKakojikokuPrintKb() ) + "')");
		}
		if( bean.getChoriyoKokokubunKb() != null && bean.getChoriyoKokokubunKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getChoriyoKokokubunKb() ) + "')");
		}
		if( bean.getHozonOndotaiKb() != null && bean.getHozonOndotaiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getHozonOndotaiKb() ) + "')");
		}
		if( bean.getStartKb() != null && bean.getStartKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getStartKb() ) + "')");
		}
		if( bean.getBackLabelKb() != null && bean.getBackLabelKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getBackLabelKb() ) + "')");
		}
		if( bean.getEiyoSeibunNa() != null && bean.getEiyoSeibunNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getEiyoSeibunNa() ) + "'");
		}
		if( bean.getCommentKb() != null && bean.getCommentKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getCommentKb() ) + "')");
		}
		if( bean.getBikoTx() != null && bean.getBikoTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getBikoTx() ) + "'");
		}
		if( bean.getGenzairyoNa() != null && bean.getGenzairyoNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getGenzairyoNa() ) + "'");
		}
		if( bean.getTenkabutuNa() != null && bean.getTenkabutuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getTenkabutuNa() ) + "')");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getInsertUserId() ) + "')");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
//			sb.append("TRIM('" + conv.convertString( bean.getUpdateTs() ) + "')");
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("TRIM('" + conv.convertString( bean.getUpdateUserId() ) + "')");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
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
		mst810101_KeiryokiBean bean = (mst810101_KeiryokiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_keiryoki set ");
		if( bean.getSyohinKbn() != null && bean.getSyohinKbn().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" syohin_kbn = ");
			sb.append("TRIM('" + conv.convertString( bean.getSyohinKbn() ) + "')");
		}
		if( bean.getSyohinKbn() != null && bean.getSyohinKbn().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanei_dt = ");
			sb.append("TRIM('" + conv.convertString( bean.getHaneiDt() ) + "')");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd = ");
			sb.append("TRIM('" + conv.convertString( bean.getSyohinCd() ) + "')");
		}
		if( bean.getKeiryokiNa() != null && bean.getKeiryokiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki_na = ");
			sb.append("'" + conv.convertString( bean.getKeiryokiNa() ) + "'");
		}
		if( bean.getKeiryoki2Na() != null && bean.getKeiryoki2Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki2_na = ");
			sb.append("'" + conv.convertString( bean.getKeiryoki2Na() ) + "'");
		}
		if( bean.getKeiryoki3Na() != null && bean.getKeiryoki3Na().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiryoki3_na = ");
			sb.append("'" + conv.convertString( bean.getKeiryoki3Na() ) + "'");
		}
		if( bean.getReceiptHinmeiNa() != null && bean.getReceiptHinmeiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" receipt_hinmei_na = ");
			sb.append("TRIM('" + conv.convertString( bean.getReceiptHinmeiNa() ) + "')");
		}
		if( bean.getTeigakuUpKb() != null && bean.getTeigakuUpKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" teigaku_up_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getTeigakuUpKb() ) + "')");
		}
		if( befKanma ) sb.append(",");
		sb.append(" tanka_vl = ");
		sb.append( bean.getTankaVlString());
		sb.append(",");
		sb.append(" teigaku_vl = ");
		sb.append( bean.getTeigakuVlString());
		if( bean.getTeigakujiTaniKb() != null && bean.getTeigakujiTaniKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" teigakuji_tani_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getTeigakujiTaniKb() ) + "')");
		}
		if( bean.getSyomikikanKb() != null && bean.getSyomikikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syomikikan_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getSyomikikanKb() ) + "')");
		}
		if( befKanma ) sb.append(",");
		sb.append(" syomikikan_vl = ");
		sb.append( bean.getSyomikikanVl() );
		if( bean.getSantiKb() != null && bean.getSantiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" santi_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getSantiKb() ) + "')");
		}
		if( bean.getKakojikokuPrintKb() != null && bean.getKakojikokuPrintKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kakojikoku_print_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getKakojikokuPrintKb() ) + "')");
		}
		if( bean.getChoriyoKokokubunKb() != null && bean.getChoriyoKokokubunKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" choriyo_kokokubun_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getChoriyoKokokubunKb() ) + "')");
		}
		if( bean.getHozonOndotaiKb() != null && bean.getHozonOndotaiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hozon_ondotai_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getHozonOndotaiKb() ) + "')");
		}
		if( bean.getStartKb() != null && bean.getStartKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" start_kb = ");
			sb.append("TRIM('" + conv.convertString( bean.getStartKb() ) + "')");
		}
		if( bean.getBackLabelKb() != null && bean.getBackLabelKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" back_label_kb = ");
			sb.append("'" + conv.convertString( bean.getBackLabelKb() ) + "'");
		}
		sb.append(",");
		sb.append(" eiyo_seibun_na = ");
		sb.append("TRIM('" + conv.convertString( bean.getEiyoSeibunNa() ) + "')");
		sb.append(",");
		sb.append(" comment_kb = ");
		sb.append("TRIM('" + conv.convertString( bean.getCommentKb() ) + "')");
		sb.append(",");
		sb.append(" biko_tx = ");
		sb.append("'" + conv.convertString( bean.getBikoTx() ) + "'");
		if( bean.getGenzairyoNa() != null && bean.getGenzairyoNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" genzairyo_na = ");
			sb.append("'" + conv.convertString( bean.getGenzairyoNa() ) + "'");
		}
		if( bean.getTenkabutuNa() != null && bean.getTenkabutuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenkabutu_na = ");
			sb.append("'" + conv.convertString( bean.getTenkabutuNa() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts = ");
/*
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
*/
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_user_id = ");
			sb.append("TRIM('" + conv.convertString( bean.getInsertUserId() ) + "')");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
/*
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
*/
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");

		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_user_id = ");
			sb.append("TRIM('" + conv.convertString( bean.getUpdateUserId() ) + "')");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" keiryo_hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_yobidasi = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" theme_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
		}
		return sb.toString();
	}

	/**
	 * DeleteFg更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateGenzairyoSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst810101_KeiryokiBean bean = (mst810101_KeiryokiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_keiryoki set ");

		sb.append(" genzairyo_na = ");
		sb.append("'" + conv.convertString( bean.getGenzairyoNa() ) + "'");
		sb.append(",");
		sb.append(" tenkabutu_na = ");
		sb.append("'" + conv.convertString( bean.getTenkabutuNa() ) + "'");
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		sb.append(",");
		sb.append(" update_user_id = ");
		sb.append("TRIM('" + conv.convertString( bean.getUpdateUserId() ) + "')");


		sb.append(" WHERE");


		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" keiryo_hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_yobidasi = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" theme_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
		}
		return sb.toString();
	}
	
	/**
	 * DeleteFg更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteFgUpdateSql( Object beanMst )
	{
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst810101_KeiryokiBean bean = (mst810101_KeiryokiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_keiryoki set ");

		sb.append(" update_ts = ");
		sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		sb.append(",");
		sb.append(" update_user_id = ");
		sb.append(" TRIM('" + conv.convertString( bean.getUpdateUserId() ) + "')");
		sb.append(",");
		sb.append(" delete_fg = '1' ");


		sb.append(" WHERE");


		if( bean.getKeiryoHankuCd() != null && bean.getKeiryoHankuCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" keiryo_hanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		}
		if( bean.getSyohinYobidasi() != null && bean.getSyohinYobidasi().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_yobidasi = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getThemeCd() != null && bean.getThemeCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" theme_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
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
		mst810101_KeiryokiBean bean = (mst810101_KeiryokiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_keiryoki where ");
		sb.append(" keiryo_hanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKeiryoHankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_yobidasi = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinYobidasi() ) + "'");
		sb.append(" AND");
		sb.append(" s_gyosyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSGyosyuCd() ) + "'");
		sb.append(" AND");
		sb.append(" theme_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getThemeCd() ) + "'");
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
