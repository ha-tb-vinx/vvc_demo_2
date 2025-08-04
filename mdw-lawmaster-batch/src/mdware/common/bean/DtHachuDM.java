package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2005/1/5 10:58:50)
 */
public class DtHachuDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public DtHachuDM()
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
		DtHachuBean bean = new DtHachuBean();
		bean.setDataDenpNb( rest.getString("data_denp_nb") );
		bean.setFileHeadNb( rest.getString("file_head_nb") );
		bean.setGaitoSystemKb( rest.getString("gaito_system_kb") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setLGyosyuCd( rest.getString("l_gyosyu_cd") );
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setLHankuCd( rest.getString("l_hanku_cd") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setNohinDt( rest.getString("nohin_dt") );
		bean.setDenpyoNb( rest.getString("denpyo_nb") );
		bean.setHachuDt( rest.getString("hachu_dt") );
		bean.setKbBusyoKb( rest.getString("kb_busyo_kb") );
		bean.setBinNb( rest.getString("bin_nb") );
		bean.setTeiBinNb( rest.getString("tei_bin_nb") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setTenpoKa( rest.getString("tenpo_ka") );
		bean.setTenhankuNa( rest.getString("tenhanku_na") );
		bean.setSGyosyuNa( rest.getString("s_gyosyu_na") );
		bean.setTorihikisakiNa( rest.getString("torihikisaki_na") );
		bean.setTorihikisakiKa( rest.getString("torihikisaki_ka") );
		bean.setGenkaKeiVl( rest.getLong("genka_kei_vl") );
		bean.setBaikaKeiVl( rest.getLong("baika_kei_vl") );
		bean.setTeiGenkaKeiVl( rest.getLong("tei_genka_kei_vl") );
		bean.setTeiBaikaKeiVl( rest.getLong("tei_baika_kei_vl") );
		bean.setSeisenFg( rest.getString("seisen_fg") );
		bean.setKbSobaKb( rest.getString("kb_soba_kb") );
		bean.setHaisoKb( rest.getString("haiso_kb") );
		bean.setTeiHaisoKb( rest.getString("tei_haiso_kb") );
		bean.setDenpyoKb( rest.getString("denpyo_kb") );
		bean.setZeiKb( rest.getString("zei_kb") );
		bean.setHachuKb( rest.getString("hachu_kb") );
		bean.setTenpoHachuKb( rest.getString("tenpo_hachu_kb") );
		bean.setIkatuDenpFg( rest.getString("ikatu_denp_fg") );
		bean.setSyohinThemeNa( rest.getString("syohin_theme_na") );
		bean.setHaisosakiCd( rest.getString("haisosaki_cd") );
		bean.setHaisosakiNa( rest.getString("haisosaki_na") );
		bean.setNohinCenterCd( rest.getString("nohin_center_cd") );
		bean.setKeiyuCenterCd( rest.getString("keiyu_center_cd") );
		bean.setTenhaiCenterCd( rest.getString("tenhai_center_cd") );
		bean.setZaikoCenterCd( rest.getString("zaiko_center_cd") );
		bean.setTeiNohinCenterCd( rest.getString("tei_nohin_center_cd") );
		bean.setTeiKeiyuCenterCd( rest.getString("tei_keiyu_center_cd") );
		bean.setTeiTenhaiCenterCd( rest.getString("tei_tenhai_center_cd") );
		bean.setTeiZaikoCenterCd( rest.getString("tei_zaiko_center_cd") );
		bean.setJuchuListOutputKb( rest.getString("juchu_list_output_kb") );
		bean.setNohinSyoriKb( rest.getString("nohin_syori_kb") );
		bean.setHaisinsakiCd( rest.getString("haisinsaki_cd") );
		bean.setHojinCd( rest.getString("hojin_cd") );
		bean.setHojinNa( rest.getString("hojin_na") );
		bean.setNohinMakeFg( rest.getString("nohin_make_fg") );
		bean.setNohinMakeDt( rest.getString("nohin_make_dt") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
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
		sb.append("data_denp_nb ");
		sb.append(", ");
		sb.append("file_head_nb ");
		sb.append(", ");
		sb.append("gaito_system_kb ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("tenhanku_cd ");
		sb.append(", ");
		sb.append("l_gyosyu_cd ");
		sb.append(", ");
		sb.append("s_gyosyu_cd ");
		sb.append(", ");
		sb.append("l_hanku_cd ");
		sb.append(", ");
		sb.append("torihikisaki_cd ");
		sb.append(", ");
		sb.append("nohin_dt ");
		sb.append(", ");
		sb.append("denpyo_nb ");
		sb.append(", ");
		sb.append("hachu_dt ");
		sb.append(", ");
		sb.append("kb_busyo_kb ");
		sb.append(", ");
		sb.append("bin_nb ");
		sb.append(", ");
		sb.append("tei_bin_nb ");
		sb.append(", ");
		sb.append("tenpo_na ");
		sb.append(", ");
		sb.append("tenpo_ka ");
		sb.append(", ");
		sb.append("tenhanku_na ");
		sb.append(", ");
		sb.append("s_gyosyu_na ");
		sb.append(", ");
		sb.append("torihikisaki_na ");
		sb.append(", ");
		sb.append("torihikisaki_ka ");
		sb.append(", ");
		sb.append("genka_kei_vl ");
		sb.append(", ");
		sb.append("baika_kei_vl ");
		sb.append(", ");
		sb.append("tei_genka_kei_vl ");
		sb.append(", ");
		sb.append("tei_baika_kei_vl ");
		sb.append(", ");
		sb.append("seisen_fg ");
		sb.append(", ");
		sb.append("kb_soba_kb ");
		sb.append(", ");
		sb.append("haiso_kb ");
		sb.append(", ");
		sb.append("tei_haiso_kb ");
		sb.append(", ");
		sb.append("denpyo_kb ");
		sb.append(", ");
		sb.append("zei_kb ");
		sb.append(", ");
		sb.append("hachu_kb ");
		sb.append(", ");
		sb.append("tenpo_hachu_kb ");
		sb.append(", ");
		sb.append("ikatu_denp_fg ");
		sb.append(", ");
		sb.append("syohin_theme_na ");
		sb.append(", ");
		sb.append("haisosaki_cd ");
		sb.append(", ");
		sb.append("haisosaki_na ");
		sb.append(", ");
		sb.append("nohin_center_cd ");
		sb.append(", ");
		sb.append("keiyu_center_cd ");
		sb.append(", ");
		sb.append("tenhai_center_cd ");
		sb.append(", ");
		sb.append("zaiko_center_cd ");
		sb.append(", ");
		sb.append("tei_nohin_center_cd ");
		sb.append(", ");
		sb.append("tei_keiyu_center_cd ");
		sb.append(", ");
		sb.append("tei_tenhai_center_cd ");
		sb.append(", ");
		sb.append("tei_zaiko_center_cd ");
		sb.append(", ");
		sb.append("juchu_list_output_kb ");
		sb.append(", ");
		sb.append("nohin_syori_kb ");
		sb.append(", ");
		sb.append("haisinsaki_cd ");
		sb.append(", ");
		sb.append("hojin_cd ");
		sb.append(", ");
		sb.append("hojin_na ");
		sb.append(", ");
		sb.append("nohin_make_fg ");
		sb.append(", ");
		sb.append("nohin_make_dt ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from dt_hachu ");


		// data_denp_nb に対するWHERE区
		if( map.get("data_denp_nb_bef") != null && ((String)map.get("data_denp_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb >= '" + conv.convertWhereString( (String)map.get("data_denp_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_aft") != null && ((String)map.get("data_denp_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb <= '" + conv.convertWhereString( (String)map.get("data_denp_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb") != null && ((String)map.get("data_denp_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb = '" + conv.convertWhereString( (String)map.get("data_denp_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_like") != null && ((String)map.get("data_denp_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb like '%" + conv.convertWhereString( (String)map.get("data_denp_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_bef_like") != null && ((String)map.get("data_denp_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb like '%" + conv.convertWhereString( (String)map.get("data_denp_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_aft_like") != null && ((String)map.get("data_denp_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb like '" + conv.convertWhereString( (String)map.get("data_denp_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_in") != null && ((String)map.get("data_denp_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("data_denp_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("data_denp_nb_not_in") != null && ((String)map.get("data_denp_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("data_denp_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// file_head_nb に対するWHERE区
		if( map.get("file_head_nb_bef") != null && ((String)map.get("file_head_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb >= '" + conv.convertWhereString( (String)map.get("file_head_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_aft") != null && ((String)map.get("file_head_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb <= '" + conv.convertWhereString( (String)map.get("file_head_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb") != null && ((String)map.get("file_head_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb = '" + conv.convertWhereString( (String)map.get("file_head_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_like") != null && ((String)map.get("file_head_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '%" + conv.convertWhereString( (String)map.get("file_head_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_bef_like") != null && ((String)map.get("file_head_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '%" + conv.convertWhereString( (String)map.get("file_head_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_aft_like") != null && ((String)map.get("file_head_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb like '" + conv.convertWhereString( (String)map.get("file_head_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_in") != null && ((String)map.get("file_head_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_head_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("file_head_nb_not_in") != null && ((String)map.get("file_head_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("file_head_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("file_head_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// gaito_system_kb に対するWHERE区
		if( map.get("gaito_system_kb_bef") != null && ((String)map.get("gaito_system_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb >= '" + conv.convertWhereString( (String)map.get("gaito_system_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_aft") != null && ((String)map.get("gaito_system_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb <= '" + conv.convertWhereString( (String)map.get("gaito_system_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb") != null && ((String)map.get("gaito_system_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb = '" + conv.convertWhereString( (String)map.get("gaito_system_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_like") != null && ((String)map.get("gaito_system_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb like '%" + conv.convertWhereString( (String)map.get("gaito_system_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_bef_like") != null && ((String)map.get("gaito_system_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb like '%" + conv.convertWhereString( (String)map.get("gaito_system_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_aft_like") != null && ((String)map.get("gaito_system_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb like '" + conv.convertWhereString( (String)map.get("gaito_system_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_in") != null && ((String)map.get("gaito_system_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gaito_system_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("gaito_system_kb_not_in") != null && ((String)map.get("gaito_system_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gaito_system_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("gaito_system_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd_bef") != null && ((String)map.get("tenpo_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd >= '" + conv.convertWhereString( (String)map.get("tenpo_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft") != null && ((String)map.get("tenpo_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd <= '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_like") != null && ((String)map.get("tenpo_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_bef_like") != null && ((String)map.get("tenpo_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '%" + conv.convertWhereString( (String)map.get("tenpo_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_aft_like") != null && ((String)map.get("tenpo_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd like '" + conv.convertWhereString( (String)map.get("tenpo_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_in") != null && ((String)map.get("tenpo_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd_not_in") != null && ((String)map.get("tenpo_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenhanku_cd に対するWHERE区
		if( map.get("tenhanku_cd_bef") != null && ((String)map.get("tenhanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd >= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_aft") != null && ((String)map.get("tenhanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd <= '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd") != null && ((String)map.get("tenhanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd = '" + conv.convertWhereString( (String)map.get("tenhanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_like") != null && ((String)map.get("tenhanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_bef_like") != null && ((String)map.get("tenhanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '%" + conv.convertWhereString( (String)map.get("tenhanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_aft_like") != null && ((String)map.get("tenhanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd like '" + conv.convertWhereString( (String)map.get("tenhanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_in") != null && ((String)map.get("tenhanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenhanku_cd_not_in") != null && ((String)map.get("tenhanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// l_gyosyu_cd に対するWHERE区
		if( map.get("l_gyosyu_cd_bef") != null && ((String)map.get("l_gyosyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_aft") != null && ((String)map.get("l_gyosyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd") != null && ((String)map.get("l_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_like") != null && ((String)map.get("l_gyosyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_bef_like") != null && ((String)map.get("l_gyosyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_aft_like") != null && ((String)map.get("l_gyosyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd like '" + conv.convertWhereString( (String)map.get("l_gyosyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_in") != null && ((String)map.get("l_gyosyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_gyosyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("l_gyosyu_cd_not_in") != null && ((String)map.get("l_gyosyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_gyosyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_gyosyu_cd に対するWHERE区
		if( map.get("s_gyosyu_cd_bef") != null && ((String)map.get("s_gyosyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd >= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft") != null && ((String)map.get("s_gyosyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd <= '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_like") != null && ((String)map.get("s_gyosyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_bef_like") != null && ((String)map.get("s_gyosyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_aft_like") != null && ((String)map.get("s_gyosyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd like '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_in") != null && ((String)map.get("s_gyosyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_cd_not_in") != null && ((String)map.get("s_gyosyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// l_hanku_cd に対するWHERE区
		if( map.get("l_hanku_cd_bef") != null && ((String)map.get("l_hanku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd >= '" + conv.convertWhereString( (String)map.get("l_hanku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_aft") != null && ((String)map.get("l_hanku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd <= '" + conv.convertWhereString( (String)map.get("l_hanku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd") != null && ((String)map.get("l_hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd = '" + conv.convertWhereString( (String)map.get("l_hanku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_like") != null && ((String)map.get("l_hanku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd like '%" + conv.convertWhereString( (String)map.get("l_hanku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_bef_like") != null && ((String)map.get("l_hanku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd like '%" + conv.convertWhereString( (String)map.get("l_hanku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_aft_like") != null && ((String)map.get("l_hanku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd like '" + conv.convertWhereString( (String)map.get("l_hanku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_in") != null && ((String)map.get("l_hanku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_hanku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("l_hanku_cd_not_in") != null && ((String)map.get("l_hanku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("l_hanku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("l_hanku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// torihikisaki_cd に対するWHERE区
		if( map.get("torihikisaki_cd_bef") != null && ((String)map.get("torihikisaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd >= '" + conv.convertWhereString( (String)map.get("torihikisaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_aft") != null && ((String)map.get("torihikisaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd <= '" + conv.convertWhereString( (String)map.get("torihikisaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + conv.convertWhereString( (String)map.get("torihikisaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_like") != null && ((String)map.get("torihikisaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd like '%" + conv.convertWhereString( (String)map.get("torihikisaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_bef_like") != null && ((String)map.get("torihikisaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd like '%" + conv.convertWhereString( (String)map.get("torihikisaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_aft_like") != null && ((String)map.get("torihikisaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd like '" + conv.convertWhereString( (String)map.get("torihikisaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_in") != null && ((String)map.get("torihikisaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd_not_in") != null && ((String)map.get("torihikisaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_dt に対するWHERE区
		if( map.get("nohin_dt_bef") != null && ((String)map.get("nohin_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt >= '" + conv.convertWhereString( (String)map.get("nohin_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_aft") != null && ((String)map.get("nohin_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt <= '" + conv.convertWhereString( (String)map.get("nohin_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt") != null && ((String)map.get("nohin_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt = '" + conv.convertWhereString( (String)map.get("nohin_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_like") != null && ((String)map.get("nohin_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt like '%" + conv.convertWhereString( (String)map.get("nohin_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_bef_like") != null && ((String)map.get("nohin_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt like '%" + conv.convertWhereString( (String)map.get("nohin_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_aft_like") != null && ((String)map.get("nohin_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt like '" + conv.convertWhereString( (String)map.get("nohin_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_in") != null && ((String)map.get("nohin_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_dt_not_in") != null && ((String)map.get("nohin_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// denpyo_nb に対するWHERE区
		if( map.get("denpyo_nb_bef") != null && ((String)map.get("denpyo_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb >= '" + conv.convertWhereString( (String)map.get("denpyo_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_aft") != null && ((String)map.get("denpyo_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb <= '" + conv.convertWhereString( (String)map.get("denpyo_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb") != null && ((String)map.get("denpyo_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb = '" + conv.convertWhereString( (String)map.get("denpyo_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_like") != null && ((String)map.get("denpyo_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb like '%" + conv.convertWhereString( (String)map.get("denpyo_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_bef_like") != null && ((String)map.get("denpyo_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb like '%" + conv.convertWhereString( (String)map.get("denpyo_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_aft_like") != null && ((String)map.get("denpyo_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb like '" + conv.convertWhereString( (String)map.get("denpyo_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_in") != null && ((String)map.get("denpyo_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb_not_in") != null && ((String)map.get("denpyo_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_dt に対するWHERE区
		if( map.get("hachu_dt_bef") != null && ((String)map.get("hachu_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt >= '" + conv.convertWhereString( (String)map.get("hachu_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_aft") != null && ((String)map.get("hachu_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt <= '" + conv.convertWhereString( (String)map.get("hachu_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt") != null && ((String)map.get("hachu_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt = '" + conv.convertWhereString( (String)map.get("hachu_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_like") != null && ((String)map.get("hachu_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt like '%" + conv.convertWhereString( (String)map.get("hachu_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_bef_like") != null && ((String)map.get("hachu_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt like '%" + conv.convertWhereString( (String)map.get("hachu_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_aft_like") != null && ((String)map.get("hachu_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt like '" + conv.convertWhereString( (String)map.get("hachu_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_in") != null && ((String)map.get("hachu_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_dt_not_in") != null && ((String)map.get("hachu_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kb_busyo_kb に対するWHERE区
		if( map.get("kb_busyo_kb_bef") != null && ((String)map.get("kb_busyo_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb >= '" + conv.convertWhereString( (String)map.get("kb_busyo_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_aft") != null && ((String)map.get("kb_busyo_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb <= '" + conv.convertWhereString( (String)map.get("kb_busyo_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb") != null && ((String)map.get("kb_busyo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb = '" + conv.convertWhereString( (String)map.get("kb_busyo_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_like") != null && ((String)map.get("kb_busyo_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb like '%" + conv.convertWhereString( (String)map.get("kb_busyo_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_bef_like") != null && ((String)map.get("kb_busyo_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb like '%" + conv.convertWhereString( (String)map.get("kb_busyo_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_aft_like") != null && ((String)map.get("kb_busyo_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb like '" + conv.convertWhereString( (String)map.get("kb_busyo_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_in") != null && ((String)map.get("kb_busyo_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kb_busyo_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kb_busyo_kb_not_in") != null && ((String)map.get("kb_busyo_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_busyo_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kb_busyo_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// bin_nb に対するWHERE区
		if( map.get("bin_nb_bef") != null && ((String)map.get("bin_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb >= '" + conv.convertWhereString( (String)map.get("bin_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_nb_aft") != null && ((String)map.get("bin_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb <= '" + conv.convertWhereString( (String)map.get("bin_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_nb") != null && ((String)map.get("bin_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb = '" + conv.convertWhereString( (String)map.get("bin_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_nb_like") != null && ((String)map.get("bin_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb like '%" + conv.convertWhereString( (String)map.get("bin_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_nb_bef_like") != null && ((String)map.get("bin_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb like '%" + conv.convertWhereString( (String)map.get("bin_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("bin_nb_aft_like") != null && ((String)map.get("bin_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb like '" + conv.convertWhereString( (String)map.get("bin_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("bin_nb_in") != null && ((String)map.get("bin_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("bin_nb_not_in") != null && ((String)map.get("bin_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("bin_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_bin_nb に対するWHERE区
		if( map.get("tei_bin_nb_bef") != null && ((String)map.get("tei_bin_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb >= '" + conv.convertWhereString( (String)map.get("tei_bin_nb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_aft") != null && ((String)map.get("tei_bin_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb <= '" + conv.convertWhereString( (String)map.get("tei_bin_nb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb") != null && ((String)map.get("tei_bin_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb = '" + conv.convertWhereString( (String)map.get("tei_bin_nb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_like") != null && ((String)map.get("tei_bin_nb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb like '%" + conv.convertWhereString( (String)map.get("tei_bin_nb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_bef_like") != null && ((String)map.get("tei_bin_nb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb like '%" + conv.convertWhereString( (String)map.get("tei_bin_nb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_aft_like") != null && ((String)map.get("tei_bin_nb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb like '" + conv.convertWhereString( (String)map.get("tei_bin_nb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_in") != null && ((String)map.get("tei_bin_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_bin_nb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_bin_nb_not_in") != null && ((String)map.get("tei_bin_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_bin_nb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_bin_nb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_na に対するWHERE区
		if( map.get("tenpo_na_bef") != null && ((String)map.get("tenpo_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na >= '" + conv.convertWhereString( (String)map.get("tenpo_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_aft") != null && ((String)map.get("tenpo_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na <= '" + conv.convertWhereString( (String)map.get("tenpo_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na") != null && ((String)map.get("tenpo_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na = '" + conv.convertWhereString( (String)map.get("tenpo_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_like") != null && ((String)map.get("tenpo_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na like '%" + conv.convertWhereString( (String)map.get("tenpo_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_bef_like") != null && ((String)map.get("tenpo_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na like '%" + conv.convertWhereString( (String)map.get("tenpo_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_aft_like") != null && ((String)map.get("tenpo_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na like '" + conv.convertWhereString( (String)map.get("tenpo_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_in") != null && ((String)map.get("tenpo_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_na_not_in") != null && ((String)map.get("tenpo_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_ka に対するWHERE区
		if( map.get("tenpo_ka_bef") != null && ((String)map.get("tenpo_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka >= '" + conv.convertWhereString( (String)map.get("tenpo_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_aft") != null && ((String)map.get("tenpo_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka <= '" + conv.convertWhereString( (String)map.get("tenpo_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka") != null && ((String)map.get("tenpo_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka = '" + conv.convertWhereString( (String)map.get("tenpo_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_like") != null && ((String)map.get("tenpo_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka like '%" + conv.convertWhereString( (String)map.get("tenpo_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_bef_like") != null && ((String)map.get("tenpo_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka like '%" + conv.convertWhereString( (String)map.get("tenpo_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_aft_like") != null && ((String)map.get("tenpo_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka like '" + conv.convertWhereString( (String)map.get("tenpo_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_in") != null && ((String)map.get("tenpo_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_ka_not_in") != null && ((String)map.get("tenpo_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenhanku_na に対するWHERE区
		if( map.get("tenhanku_na_bef") != null && ((String)map.get("tenhanku_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na >= '" + conv.convertWhereString( (String)map.get("tenhanku_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_aft") != null && ((String)map.get("tenhanku_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na <= '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na") != null && ((String)map.get("tenhanku_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na = '" + conv.convertWhereString( (String)map.get("tenhanku_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_like") != null && ((String)map.get("tenhanku_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_bef_like") != null && ((String)map.get("tenhanku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na like '%" + conv.convertWhereString( (String)map.get("tenhanku_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_aft_like") != null && ((String)map.get("tenhanku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na like '" + conv.convertWhereString( (String)map.get("tenhanku_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_in") != null && ((String)map.get("tenhanku_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenhanku_na_not_in") != null && ((String)map.get("tenhanku_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhanku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhanku_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// s_gyosyu_na に対するWHERE区
		if( map.get("s_gyosyu_na_bef") != null && ((String)map.get("s_gyosyu_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na >= '" + conv.convertWhereString( (String)map.get("s_gyosyu_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_aft") != null && ((String)map.get("s_gyosyu_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na <= '" + conv.convertWhereString( (String)map.get("s_gyosyu_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na") != null && ((String)map.get("s_gyosyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na = '" + conv.convertWhereString( (String)map.get("s_gyosyu_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_like") != null && ((String)map.get("s_gyosyu_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_bef_like") != null && ((String)map.get("s_gyosyu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na like '%" + conv.convertWhereString( (String)map.get("s_gyosyu_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_aft_like") != null && ((String)map.get("s_gyosyu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na like '" + conv.convertWhereString( (String)map.get("s_gyosyu_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_in") != null && ((String)map.get("s_gyosyu_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("s_gyosyu_na_not_in") != null && ((String)map.get("s_gyosyu_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("s_gyosyu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("s_gyosyu_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// torihikisaki_na に対するWHERE区
		if( map.get("torihikisaki_na_bef") != null && ((String)map.get("torihikisaki_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na >= '" + conv.convertWhereString( (String)map.get("torihikisaki_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_aft") != null && ((String)map.get("torihikisaki_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na <= '" + conv.convertWhereString( (String)map.get("torihikisaki_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na") != null && ((String)map.get("torihikisaki_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na = '" + conv.convertWhereString( (String)map.get("torihikisaki_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_like") != null && ((String)map.get("torihikisaki_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na like '%" + conv.convertWhereString( (String)map.get("torihikisaki_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_bef_like") != null && ((String)map.get("torihikisaki_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na like '%" + conv.convertWhereString( (String)map.get("torihikisaki_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_aft_like") != null && ((String)map.get("torihikisaki_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na like '" + conv.convertWhereString( (String)map.get("torihikisaki_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_in") != null && ((String)map.get("torihikisaki_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_na_not_in") != null && ((String)map.get("torihikisaki_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// torihikisaki_ka に対するWHERE区
		if( map.get("torihikisaki_ka_bef") != null && ((String)map.get("torihikisaki_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka >= '" + conv.convertWhereString( (String)map.get("torihikisaki_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_aft") != null && ((String)map.get("torihikisaki_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka <= '" + conv.convertWhereString( (String)map.get("torihikisaki_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka") != null && ((String)map.get("torihikisaki_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka = '" + conv.convertWhereString( (String)map.get("torihikisaki_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_like") != null && ((String)map.get("torihikisaki_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka like '%" + conv.convertWhereString( (String)map.get("torihikisaki_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_bef_like") != null && ((String)map.get("torihikisaki_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka like '%" + conv.convertWhereString( (String)map.get("torihikisaki_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_aft_like") != null && ((String)map.get("torihikisaki_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka like '" + conv.convertWhereString( (String)map.get("torihikisaki_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_in") != null && ((String)map.get("torihikisaki_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_ka_not_in") != null && ((String)map.get("torihikisaki_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// genka_kei_vl に対するWHERE区
		if( map.get("genka_kei_vl_bef") != null && ((String)map.get("genka_kei_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_kei_vl >= " + (String)map.get("genka_kei_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("genka_kei_vl_aft") != null && ((String)map.get("genka_kei_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_kei_vl <= " + (String)map.get("genka_kei_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("genka_kei_vl") != null && ((String)map.get("genka_kei_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("genka_kei_vl = " + (String)map.get("genka_kei_vl"));
			whereStr = andStr;
		}
		if( map.get("genka_kei_vl_in") != null && ((String)map.get("genka_kei_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_kei_vl in ( " + conv.convertWhereString( (String)map.get("genka_kei_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("genka_kei_vl_not_in") != null && ((String)map.get("genka_kei_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_kei_vl not in ( " + conv.convertWhereString( (String)map.get("genka_kei_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baika_kei_vl に対するWHERE区
		if( map.get("baika_kei_vl_bef") != null && ((String)map.get("baika_kei_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_kei_vl >= " + (String)map.get("baika_kei_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("baika_kei_vl_aft") != null && ((String)map.get("baika_kei_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_kei_vl <= " + (String)map.get("baika_kei_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("baika_kei_vl") != null && ((String)map.get("baika_kei_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baika_kei_vl = " + (String)map.get("baika_kei_vl"));
			whereStr = andStr;
		}
		if( map.get("baika_kei_vl_in") != null && ((String)map.get("baika_kei_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_kei_vl in ( " + conv.convertWhereString( (String)map.get("baika_kei_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baika_kei_vl_not_in") != null && ((String)map.get("baika_kei_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_kei_vl not in ( " + conv.convertWhereString( (String)map.get("baika_kei_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// tei_genka_kei_vl に対するWHERE区
		if( map.get("tei_genka_kei_vl_bef") != null && ((String)map.get("tei_genka_kei_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_genka_kei_vl >= " + (String)map.get("tei_genka_kei_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("tei_genka_kei_vl_aft") != null && ((String)map.get("tei_genka_kei_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_genka_kei_vl <= " + (String)map.get("tei_genka_kei_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("tei_genka_kei_vl") != null && ((String)map.get("tei_genka_kei_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("tei_genka_kei_vl = " + (String)map.get("tei_genka_kei_vl"));
			whereStr = andStr;
		}
		if( map.get("tei_genka_kei_vl_in") != null && ((String)map.get("tei_genka_kei_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_genka_kei_vl in ( " + conv.convertWhereString( (String)map.get("tei_genka_kei_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("tei_genka_kei_vl_not_in") != null && ((String)map.get("tei_genka_kei_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_genka_kei_vl not in ( " + conv.convertWhereString( (String)map.get("tei_genka_kei_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// tei_baika_kei_vl に対するWHERE区
		if( map.get("tei_baika_kei_vl_bef") != null && ((String)map.get("tei_baika_kei_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_baika_kei_vl >= " + (String)map.get("tei_baika_kei_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("tei_baika_kei_vl_aft") != null && ((String)map.get("tei_baika_kei_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_baika_kei_vl <= " + (String)map.get("tei_baika_kei_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("tei_baika_kei_vl") != null && ((String)map.get("tei_baika_kei_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("tei_baika_kei_vl = " + (String)map.get("tei_baika_kei_vl"));
			whereStr = andStr;
		}
		if( map.get("tei_baika_kei_vl_in") != null && ((String)map.get("tei_baika_kei_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_baika_kei_vl in ( " + conv.convertWhereString( (String)map.get("tei_baika_kei_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("tei_baika_kei_vl_not_in") != null && ((String)map.get("tei_baika_kei_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_baika_kei_vl not in ( " + conv.convertWhereString( (String)map.get("tei_baika_kei_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// seisen_fg に対するWHERE区
		if( map.get("seisen_fg_bef") != null && ((String)map.get("seisen_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg >= '" + conv.convertWhereString( (String)map.get("seisen_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_aft") != null && ((String)map.get("seisen_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg <= '" + conv.convertWhereString( (String)map.get("seisen_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg") != null && ((String)map.get("seisen_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg = '" + conv.convertWhereString( (String)map.get("seisen_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_like") != null && ((String)map.get("seisen_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg like '%" + conv.convertWhereString( (String)map.get("seisen_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_bef_like") != null && ((String)map.get("seisen_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg like '%" + conv.convertWhereString( (String)map.get("seisen_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_aft_like") != null && ((String)map.get("seisen_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg like '" + conv.convertWhereString( (String)map.get("seisen_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_in") != null && ((String)map.get("seisen_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("seisen_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("seisen_fg_not_in") != null && ((String)map.get("seisen_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("seisen_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("seisen_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kb_soba_kb に対するWHERE区
		if( map.get("kb_soba_kb_bef") != null && ((String)map.get("kb_soba_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb >= '" + conv.convertWhereString( (String)map.get("kb_soba_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_aft") != null && ((String)map.get("kb_soba_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb <= '" + conv.convertWhereString( (String)map.get("kb_soba_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb") != null && ((String)map.get("kb_soba_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb = '" + conv.convertWhereString( (String)map.get("kb_soba_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_like") != null && ((String)map.get("kb_soba_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb like '%" + conv.convertWhereString( (String)map.get("kb_soba_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_bef_like") != null && ((String)map.get("kb_soba_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb like '%" + conv.convertWhereString( (String)map.get("kb_soba_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_aft_like") != null && ((String)map.get("kb_soba_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb like '" + conv.convertWhereString( (String)map.get("kb_soba_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_in") != null && ((String)map.get("kb_soba_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kb_soba_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kb_soba_kb_not_in") != null && ((String)map.get("kb_soba_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_soba_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kb_soba_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// haiso_kb に対するWHERE区
		if( map.get("haiso_kb_bef") != null && ((String)map.get("haiso_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb >= '" + conv.convertWhereString( (String)map.get("haiso_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_aft") != null && ((String)map.get("haiso_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb <= '" + conv.convertWhereString( (String)map.get("haiso_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb") != null && ((String)map.get("haiso_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb = '" + conv.convertWhereString( (String)map.get("haiso_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_like") != null && ((String)map.get("haiso_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb like '%" + conv.convertWhereString( (String)map.get("haiso_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_bef_like") != null && ((String)map.get("haiso_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb like '%" + conv.convertWhereString( (String)map.get("haiso_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_aft_like") != null && ((String)map.get("haiso_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb like '" + conv.convertWhereString( (String)map.get("haiso_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_in") != null && ((String)map.get("haiso_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haiso_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("haiso_kb_not_in") != null && ((String)map.get("haiso_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haiso_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haiso_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_haiso_kb に対するWHERE区
		if( map.get("tei_haiso_kb_bef") != null && ((String)map.get("tei_haiso_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb >= '" + conv.convertWhereString( (String)map.get("tei_haiso_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_aft") != null && ((String)map.get("tei_haiso_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb <= '" + conv.convertWhereString( (String)map.get("tei_haiso_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb") != null && ((String)map.get("tei_haiso_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb = '" + conv.convertWhereString( (String)map.get("tei_haiso_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_like") != null && ((String)map.get("tei_haiso_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb like '%" + conv.convertWhereString( (String)map.get("tei_haiso_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_bef_like") != null && ((String)map.get("tei_haiso_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb like '%" + conv.convertWhereString( (String)map.get("tei_haiso_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_aft_like") != null && ((String)map.get("tei_haiso_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb like '" + conv.convertWhereString( (String)map.get("tei_haiso_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_in") != null && ((String)map.get("tei_haiso_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_haiso_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_haiso_kb_not_in") != null && ((String)map.get("tei_haiso_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_haiso_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_haiso_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// denpyo_kb に対するWHERE区
		if( map.get("denpyo_kb_bef") != null && ((String)map.get("denpyo_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb >= '" + conv.convertWhereString( (String)map.get("denpyo_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_aft") != null && ((String)map.get("denpyo_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb <= '" + conv.convertWhereString( (String)map.get("denpyo_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb") != null && ((String)map.get("denpyo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb = '" + conv.convertWhereString( (String)map.get("denpyo_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_like") != null && ((String)map.get("denpyo_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb like '%" + conv.convertWhereString( (String)map.get("denpyo_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_bef_like") != null && ((String)map.get("denpyo_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb like '%" + conv.convertWhereString( (String)map.get("denpyo_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_aft_like") != null && ((String)map.get("denpyo_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb like '" + conv.convertWhereString( (String)map.get("denpyo_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_in") != null && ((String)map.get("denpyo_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("denpyo_kb_not_in") != null && ((String)map.get("denpyo_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// zei_kb に対するWHERE区
		if( map.get("zei_kb_bef") != null && ((String)map.get("zei_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb >= '" + conv.convertWhereString( (String)map.get("zei_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zei_kb_aft") != null && ((String)map.get("zei_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb <= '" + conv.convertWhereString( (String)map.get("zei_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zei_kb") != null && ((String)map.get("zei_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb = '" + conv.convertWhereString( (String)map.get("zei_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zei_kb_like") != null && ((String)map.get("zei_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb like '%" + conv.convertWhereString( (String)map.get("zei_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zei_kb_bef_like") != null && ((String)map.get("zei_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb like '%" + conv.convertWhereString( (String)map.get("zei_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zei_kb_aft_like") != null && ((String)map.get("zei_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb like '" + conv.convertWhereString( (String)map.get("zei_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zei_kb_in") != null && ((String)map.get("zei_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zei_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("zei_kb_not_in") != null && ((String)map.get("zei_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zei_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zei_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_kb に対するWHERE区
		if( map.get("hachu_kb_bef") != null && ((String)map.get("hachu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb >= '" + conv.convertWhereString( (String)map.get("hachu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_aft") != null && ((String)map.get("hachu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb <= '" + conv.convertWhereString( (String)map.get("hachu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb") != null && ((String)map.get("hachu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb = '" + conv.convertWhereString( (String)map.get("hachu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_like") != null && ((String)map.get("hachu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb like '%" + conv.convertWhereString( (String)map.get("hachu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_bef_like") != null && ((String)map.get("hachu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb like '%" + conv.convertWhereString( (String)map.get("hachu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_aft_like") != null && ((String)map.get("hachu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb like '" + conv.convertWhereString( (String)map.get("hachu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_in") != null && ((String)map.get("hachu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_kb_not_in") != null && ((String)map.get("hachu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenpo_hachu_kb に対するWHERE区
		if( map.get("tenpo_hachu_kb_bef") != null && ((String)map.get("tenpo_hachu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb >= '" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_aft") != null && ((String)map.get("tenpo_hachu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb <= '" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb") != null && ((String)map.get("tenpo_hachu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb = '" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_like") != null && ((String)map.get("tenpo_hachu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_bef_like") != null && ((String)map.get("tenpo_hachu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb like '%" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_aft_like") != null && ((String)map.get("tenpo_hachu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb like '" + conv.convertWhereString( (String)map.get("tenpo_hachu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_in") != null && ((String)map.get("tenpo_hachu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_hachu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenpo_hachu_kb_not_in") != null && ((String)map.get("tenpo_hachu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_hachu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenpo_hachu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ikatu_denp_fg に対するWHERE区
		if( map.get("ikatu_denp_fg_bef") != null && ((String)map.get("ikatu_denp_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg >= '" + conv.convertWhereString( (String)map.get("ikatu_denp_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_aft") != null && ((String)map.get("ikatu_denp_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg <= '" + conv.convertWhereString( (String)map.get("ikatu_denp_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg") != null && ((String)map.get("ikatu_denp_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg = '" + conv.convertWhereString( (String)map.get("ikatu_denp_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_like") != null && ((String)map.get("ikatu_denp_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg like '%" + conv.convertWhereString( (String)map.get("ikatu_denp_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_bef_like") != null && ((String)map.get("ikatu_denp_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg like '%" + conv.convertWhereString( (String)map.get("ikatu_denp_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_aft_like") != null && ((String)map.get("ikatu_denp_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg like '" + conv.convertWhereString( (String)map.get("ikatu_denp_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_in") != null && ((String)map.get("ikatu_denp_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ikatu_denp_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ikatu_denp_fg_not_in") != null && ((String)map.get("ikatu_denp_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ikatu_denp_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ikatu_denp_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_theme_na に対するWHERE区
		if( map.get("syohin_theme_na_bef") != null && ((String)map.get("syohin_theme_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na >= '" + conv.convertWhereString( (String)map.get("syohin_theme_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_aft") != null && ((String)map.get("syohin_theme_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na <= '" + conv.convertWhereString( (String)map.get("syohin_theme_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na") != null && ((String)map.get("syohin_theme_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na = '" + conv.convertWhereString( (String)map.get("syohin_theme_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_like") != null && ((String)map.get("syohin_theme_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na like '%" + conv.convertWhereString( (String)map.get("syohin_theme_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_bef_like") != null && ((String)map.get("syohin_theme_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na like '%" + conv.convertWhereString( (String)map.get("syohin_theme_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_aft_like") != null && ((String)map.get("syohin_theme_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na like '" + conv.convertWhereString( (String)map.get("syohin_theme_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_in") != null && ((String)map.get("syohin_theme_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_theme_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_theme_na_not_in") != null && ((String)map.get("syohin_theme_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_theme_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_theme_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// haisosaki_cd に対するWHERE区
		if( map.get("haisosaki_cd_bef") != null && ((String)map.get("haisosaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd >= '" + conv.convertWhereString( (String)map.get("haisosaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_aft") != null && ((String)map.get("haisosaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd <= '" + conv.convertWhereString( (String)map.get("haisosaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd") != null && ((String)map.get("haisosaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd = '" + conv.convertWhereString( (String)map.get("haisosaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_like") != null && ((String)map.get("haisosaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd like '%" + conv.convertWhereString( (String)map.get("haisosaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_bef_like") != null && ((String)map.get("haisosaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd like '%" + conv.convertWhereString( (String)map.get("haisosaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_aft_like") != null && ((String)map.get("haisosaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd like '" + conv.convertWhereString( (String)map.get("haisosaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_in") != null && ((String)map.get("haisosaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisosaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("haisosaki_cd_not_in") != null && ((String)map.get("haisosaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisosaki_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// haisosaki_na に対するWHERE区
		if( map.get("haisosaki_na_bef") != null && ((String)map.get("haisosaki_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na >= '" + conv.convertWhereString( (String)map.get("haisosaki_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_aft") != null && ((String)map.get("haisosaki_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na <= '" + conv.convertWhereString( (String)map.get("haisosaki_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na") != null && ((String)map.get("haisosaki_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na = '" + conv.convertWhereString( (String)map.get("haisosaki_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_like") != null && ((String)map.get("haisosaki_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na like '%" + conv.convertWhereString( (String)map.get("haisosaki_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_bef_like") != null && ((String)map.get("haisosaki_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na like '%" + conv.convertWhereString( (String)map.get("haisosaki_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_aft_like") != null && ((String)map.get("haisosaki_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na like '" + conv.convertWhereString( (String)map.get("haisosaki_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_in") != null && ((String)map.get("haisosaki_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisosaki_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("haisosaki_na_not_in") != null && ((String)map.get("haisosaki_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("haisosaki_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("haisosaki_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_center_cd に対するWHERE区
		if( map.get("nohin_center_cd_bef") != null && ((String)map.get("nohin_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd >= '" + conv.convertWhereString( (String)map.get("nohin_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_aft") != null && ((String)map.get("nohin_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd <= '" + conv.convertWhereString( (String)map.get("nohin_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd") != null && ((String)map.get("nohin_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd = '" + conv.convertWhereString( (String)map.get("nohin_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_like") != null && ((String)map.get("nohin_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd like '%" + conv.convertWhereString( (String)map.get("nohin_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_bef_like") != null && ((String)map.get("nohin_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd like '%" + conv.convertWhereString( (String)map.get("nohin_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_aft_like") != null && ((String)map.get("nohin_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd like '" + conv.convertWhereString( (String)map.get("nohin_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_in") != null && ((String)map.get("nohin_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_center_cd_not_in") != null && ((String)map.get("nohin_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// keiyu_center_cd に対するWHERE区
		if( map.get("keiyu_center_cd_bef") != null && ((String)map.get("keiyu_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd >= '" + conv.convertWhereString( (String)map.get("keiyu_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_aft") != null && ((String)map.get("keiyu_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd <= '" + conv.convertWhereString( (String)map.get("keiyu_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd") != null && ((String)map.get("keiyu_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd = '" + conv.convertWhereString( (String)map.get("keiyu_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_like") != null && ((String)map.get("keiyu_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd like '%" + conv.convertWhereString( (String)map.get("keiyu_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_bef_like") != null && ((String)map.get("keiyu_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd like '%" + conv.convertWhereString( (String)map.get("keiyu_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_aft_like") != null && ((String)map.get("keiyu_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd like '" + conv.convertWhereString( (String)map.get("keiyu_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_in") != null && ((String)map.get("keiyu_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiyu_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("keiyu_center_cd_not_in") != null && ((String)map.get("keiyu_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiyu_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiyu_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tenhai_center_cd に対するWHERE区
		if( map.get("tenhai_center_cd_bef") != null && ((String)map.get("tenhai_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd >= '" + conv.convertWhereString( (String)map.get("tenhai_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_aft") != null && ((String)map.get("tenhai_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd <= '" + conv.convertWhereString( (String)map.get("tenhai_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd") != null && ((String)map.get("tenhai_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd = '" + conv.convertWhereString( (String)map.get("tenhai_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_like") != null && ((String)map.get("tenhai_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd like '%" + conv.convertWhereString( (String)map.get("tenhai_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_bef_like") != null && ((String)map.get("tenhai_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd like '%" + conv.convertWhereString( (String)map.get("tenhai_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_aft_like") != null && ((String)map.get("tenhai_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd like '" + conv.convertWhereString( (String)map.get("tenhai_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_in") != null && ((String)map.get("tenhai_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhai_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tenhai_center_cd_not_in") != null && ((String)map.get("tenhai_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenhai_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tenhai_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// zaiko_center_cd に対するWHERE区
		if( map.get("zaiko_center_cd_bef") != null && ((String)map.get("zaiko_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd >= '" + conv.convertWhereString( (String)map.get("zaiko_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_aft") != null && ((String)map.get("zaiko_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd <= '" + conv.convertWhereString( (String)map.get("zaiko_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd") != null && ((String)map.get("zaiko_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd = '" + conv.convertWhereString( (String)map.get("zaiko_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_like") != null && ((String)map.get("zaiko_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd like '%" + conv.convertWhereString( (String)map.get("zaiko_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_bef_like") != null && ((String)map.get("zaiko_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd like '%" + conv.convertWhereString( (String)map.get("zaiko_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_aft_like") != null && ((String)map.get("zaiko_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd like '" + conv.convertWhereString( (String)map.get("zaiko_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_in") != null && ((String)map.get("zaiko_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zaiko_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("zaiko_center_cd_not_in") != null && ((String)map.get("zaiko_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zaiko_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("zaiko_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_nohin_center_cd に対するWHERE区
		if( map.get("tei_nohin_center_cd_bef") != null && ((String)map.get("tei_nohin_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd >= '" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_aft") != null && ((String)map.get("tei_nohin_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd <= '" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd") != null && ((String)map.get("tei_nohin_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd = '" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_like") != null && ((String)map.get("tei_nohin_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_bef_like") != null && ((String)map.get("tei_nohin_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_aft_like") != null && ((String)map.get("tei_nohin_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd like '" + conv.convertWhereString( (String)map.get("tei_nohin_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_in") != null && ((String)map.get("tei_nohin_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_nohin_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_nohin_center_cd_not_in") != null && ((String)map.get("tei_nohin_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_nohin_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_nohin_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_keiyu_center_cd に対するWHERE区
		if( map.get("tei_keiyu_center_cd_bef") != null && ((String)map.get("tei_keiyu_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd >= '" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_aft") != null && ((String)map.get("tei_keiyu_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd <= '" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd") != null && ((String)map.get("tei_keiyu_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd = '" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_like") != null && ((String)map.get("tei_keiyu_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_bef_like") != null && ((String)map.get("tei_keiyu_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_aft_like") != null && ((String)map.get("tei_keiyu_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd like '" + conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_in") != null && ((String)map.get("tei_keiyu_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_keiyu_center_cd_not_in") != null && ((String)map.get("tei_keiyu_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_keiyu_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_keiyu_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_tenhai_center_cd に対するWHERE区
		if( map.get("tei_tenhai_center_cd_bef") != null && ((String)map.get("tei_tenhai_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd >= '" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_aft") != null && ((String)map.get("tei_tenhai_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd <= '" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd") != null && ((String)map.get("tei_tenhai_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd = '" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_like") != null && ((String)map.get("tei_tenhai_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_bef_like") != null && ((String)map.get("tei_tenhai_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_aft_like") != null && ((String)map.get("tei_tenhai_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd like '" + conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_in") != null && ((String)map.get("tei_tenhai_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_tenhai_center_cd_not_in") != null && ((String)map.get("tei_tenhai_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_tenhai_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_tenhai_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tei_zaiko_center_cd に対するWHERE区
		if( map.get("tei_zaiko_center_cd_bef") != null && ((String)map.get("tei_zaiko_center_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd >= '" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_aft") != null && ((String)map.get("tei_zaiko_center_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd <= '" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd") != null && ((String)map.get("tei_zaiko_center_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd = '" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_like") != null && ((String)map.get("tei_zaiko_center_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_bef_like") != null && ((String)map.get("tei_zaiko_center_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd like '%" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_aft_like") != null && ((String)map.get("tei_zaiko_center_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd like '" + conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_in") != null && ((String)map.get("tei_zaiko_center_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tei_zaiko_center_cd_not_in") != null && ((String)map.get("tei_zaiko_center_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tei_zaiko_center_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tei_zaiko_center_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// juchu_list_output_kb に対するWHERE区
		if( map.get("juchu_list_output_kb_bef") != null && ((String)map.get("juchu_list_output_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb >= '" + conv.convertWhereString( (String)map.get("juchu_list_output_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_aft") != null && ((String)map.get("juchu_list_output_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb <= '" + conv.convertWhereString( (String)map.get("juchu_list_output_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb") != null && ((String)map.get("juchu_list_output_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb = '" + conv.convertWhereString( (String)map.get("juchu_list_output_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_like") != null && ((String)map.get("juchu_list_output_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb like '%" + conv.convertWhereString( (String)map.get("juchu_list_output_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_bef_like") != null && ((String)map.get("juchu_list_output_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb like '%" + conv.convertWhereString( (String)map.get("juchu_list_output_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_aft_like") != null && ((String)map.get("juchu_list_output_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb like '" + conv.convertWhereString( (String)map.get("juchu_list_output_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_in") != null && ((String)map.get("juchu_list_output_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("juchu_list_output_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb_not_in") != null && ((String)map.get("juchu_list_output_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("juchu_list_output_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_syori_kb に対するWHERE区
		if( map.get("nohin_syori_kb_bef") != null && ((String)map.get("nohin_syori_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb >= '" + conv.convertWhereString( (String)map.get("nohin_syori_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_aft") != null && ((String)map.get("nohin_syori_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb <= '" + conv.convertWhereString( (String)map.get("nohin_syori_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb") != null && ((String)map.get("nohin_syori_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb = '" + conv.convertWhereString( (String)map.get("nohin_syori_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_like") != null && ((String)map.get("nohin_syori_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb like '%" + conv.convertWhereString( (String)map.get("nohin_syori_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_bef_like") != null && ((String)map.get("nohin_syori_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb like '%" + conv.convertWhereString( (String)map.get("nohin_syori_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_aft_like") != null && ((String)map.get("nohin_syori_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb like '" + conv.convertWhereString( (String)map.get("nohin_syori_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_in") != null && ((String)map.get("nohin_syori_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_syori_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb_not_in") != null && ((String)map.get("nohin_syori_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_syori_kb_not_in") ),",","','") + "' )");
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


		// hojin_cd に対するWHERE区
		if( map.get("hojin_cd_bef") != null && ((String)map.get("hojin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd >= '" + conv.convertWhereString( (String)map.get("hojin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_aft") != null && ((String)map.get("hojin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd <= '" + conv.convertWhereString( (String)map.get("hojin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd") != null && ((String)map.get("hojin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd = '" + conv.convertWhereString( (String)map.get("hojin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_like") != null && ((String)map.get("hojin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '%" + conv.convertWhereString( (String)map.get("hojin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_bef_like") != null && ((String)map.get("hojin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '%" + conv.convertWhereString( (String)map.get("hojin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_aft_like") != null && ((String)map.get("hojin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd like '" + conv.convertWhereString( (String)map.get("hojin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_in") != null && ((String)map.get("hojin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hojin_cd_not_in") != null && ((String)map.get("hojin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hojin_na に対するWHERE区
		if( map.get("hojin_na_bef") != null && ((String)map.get("hojin_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na >= '" + conv.convertWhereString( (String)map.get("hojin_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_na_aft") != null && ((String)map.get("hojin_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na <= '" + conv.convertWhereString( (String)map.get("hojin_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_na") != null && ((String)map.get("hojin_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na = '" + conv.convertWhereString( (String)map.get("hojin_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_na_like") != null && ((String)map.get("hojin_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na like '%" + conv.convertWhereString( (String)map.get("hojin_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_na_bef_like") != null && ((String)map.get("hojin_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na like '%" + conv.convertWhereString( (String)map.get("hojin_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hojin_na_aft_like") != null && ((String)map.get("hojin_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na like '" + conv.convertWhereString( (String)map.get("hojin_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hojin_na_in") != null && ((String)map.get("hojin_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hojin_na_not_in") != null && ((String)map.get("hojin_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hojin_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hojin_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_make_fg に対するWHERE区
		if( map.get("nohin_make_fg_bef") != null && ((String)map.get("nohin_make_fg_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg >= '" + conv.convertWhereString( (String)map.get("nohin_make_fg_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_aft") != null && ((String)map.get("nohin_make_fg_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg <= '" + conv.convertWhereString( (String)map.get("nohin_make_fg_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg") != null && ((String)map.get("nohin_make_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg = '" + conv.convertWhereString( (String)map.get("nohin_make_fg") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_like") != null && ((String)map.get("nohin_make_fg_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg like '%" + conv.convertWhereString( (String)map.get("nohin_make_fg_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_bef_like") != null && ((String)map.get("nohin_make_fg_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg like '%" + conv.convertWhereString( (String)map.get("nohin_make_fg_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_aft_like") != null && ((String)map.get("nohin_make_fg_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg like '" + conv.convertWhereString( (String)map.get("nohin_make_fg_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_in") != null && ((String)map.get("nohin_make_fg_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_make_fg_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_make_fg_not_in") != null && ((String)map.get("nohin_make_fg_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_fg not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_make_fg_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_make_dt に対するWHERE区
		if( map.get("nohin_make_dt_bef") != null && ((String)map.get("nohin_make_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt >= '" + conv.convertWhereString( (String)map.get("nohin_make_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_aft") != null && ((String)map.get("nohin_make_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt <= '" + conv.convertWhereString( (String)map.get("nohin_make_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt") != null && ((String)map.get("nohin_make_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt = '" + conv.convertWhereString( (String)map.get("nohin_make_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_like") != null && ((String)map.get("nohin_make_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt like '%" + conv.convertWhereString( (String)map.get("nohin_make_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_bef_like") != null && ((String)map.get("nohin_make_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt like '%" + conv.convertWhereString( (String)map.get("nohin_make_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_aft_like") != null && ((String)map.get("nohin_make_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt like '" + conv.convertWhereString( (String)map.get("nohin_make_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_in") != null && ((String)map.get("nohin_make_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_make_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_make_dt_not_in") != null && ((String)map.get("nohin_make_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_make_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_make_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// riyo_user_id に対するWHERE区
		if( map.get("riyo_user_id_bef") != null && ((String)map.get("riyo_user_id_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id >= '" + conv.convertWhereString( (String)map.get("riyo_user_id_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_aft") != null && ((String)map.get("riyo_user_id_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id <= '" + conv.convertWhereString( (String)map.get("riyo_user_id_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + conv.convertWhereString( (String)map.get("riyo_user_id") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_like") != null && ((String)map.get("riyo_user_id_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '%" + conv.convertWhereString( (String)map.get("riyo_user_id_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_bef_like") != null && ((String)map.get("riyo_user_id_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '%" + conv.convertWhereString( (String)map.get("riyo_user_id_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_aft_like") != null && ((String)map.get("riyo_user_id_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id like '" + conv.convertWhereString( (String)map.get("riyo_user_id_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_in") != null && ((String)map.get("riyo_user_id_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id in ( '" + replaceAll(conv.convertWhereString( (String)map.get("riyo_user_id_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id_not_in") != null && ((String)map.get("riyo_user_id_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("riyo_user_id_not_in") ),",","','") + "' )");
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
		sb.append(" order by ");
		sb.append("data_denp_nb");
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
		DtHachuBean bean = (DtHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("dt_hachu (");
		sb.append("data_denp_nb ");
		sb.append(", ");
		sb.append("file_head_nb ");
		sb.append(", ");
		sb.append("gaito_system_kb ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("tenhanku_cd ");
		sb.append(", ");
		sb.append("l_gyosyu_cd ");
		sb.append(", ");
		sb.append("s_gyosyu_cd ");
		sb.append(", ");
		sb.append("l_hanku_cd ");
		sb.append(", ");
		sb.append("torihikisaki_cd ");
		sb.append(", ");
		sb.append("nohin_dt ");
		sb.append(", ");
		sb.append("denpyo_nb ");
		sb.append(", ");
		sb.append("hachu_dt ");
		sb.append(", ");
		sb.append("kb_busyo_kb ");
		sb.append(", ");
		sb.append("bin_nb ");
		sb.append(", ");
		sb.append("tei_bin_nb ");
		sb.append(", ");
		sb.append("tenpo_na ");
		sb.append(", ");
		sb.append("tenpo_ka ");
		sb.append(", ");
		sb.append("tenhanku_na ");
		sb.append(", ");
		sb.append("s_gyosyu_na ");
		sb.append(", ");
		sb.append("torihikisaki_na ");
		sb.append(", ");
		sb.append("torihikisaki_ka ");
		sb.append(", ");
		sb.append("genka_kei_vl ");
		sb.append(", ");
		sb.append("baika_kei_vl ");
		sb.append(", ");
		sb.append("tei_genka_kei_vl ");
		sb.append(", ");
		sb.append("tei_baika_kei_vl ");
		sb.append(", ");
		sb.append("seisen_fg ");
		sb.append(", ");
		sb.append("kb_soba_kb ");
		sb.append(", ");
		sb.append("haiso_kb ");
		sb.append(", ");
		sb.append("tei_haiso_kb ");
		sb.append(", ");
		sb.append("denpyo_kb ");
		sb.append(", ");
		sb.append("zei_kb ");
		sb.append(", ");
		sb.append("hachu_kb ");
		sb.append(", ");
		sb.append("tenpo_hachu_kb ");
		sb.append(", ");
		sb.append("ikatu_denp_fg ");
		sb.append(", ");
		sb.append("syohin_theme_na ");
		sb.append(", ");
		sb.append("haisosaki_cd ");
		sb.append(", ");
		sb.append("haisosaki_na ");
		sb.append(", ");
		sb.append("nohin_center_cd ");
		sb.append(", ");
		sb.append("keiyu_center_cd ");
		sb.append(", ");
		sb.append("tenhai_center_cd ");
		sb.append(", ");
		sb.append("zaiko_center_cd ");
		sb.append(", ");
		sb.append("tei_nohin_center_cd ");
		sb.append(", ");
		sb.append("tei_keiyu_center_cd ");
		sb.append(", ");
		sb.append("tei_tenhai_center_cd ");
		sb.append(", ");
		sb.append("tei_zaiko_center_cd ");
		sb.append(", ");
		sb.append("juchu_list_output_kb ");
		sb.append(", ");
		sb.append("nohin_syori_kb ");
		sb.append(", ");
		sb.append("haisinsaki_cd ");
		sb.append(", ");
		sb.append("hojin_cd ");
		sb.append(", ");
		sb.append("hojin_na ");
		sb.append(", ");
		sb.append("nohin_make_fg ");
		sb.append(", ");
		sb.append("nohin_make_dt ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");

		sb.append(")Values(");

		sb.append(bean.getDataDenpNbString());
		sb.append(",");
		sb.append(bean.getFileHeadNbString());
		sb.append(",");
		sb.append(bean.getGaitoSystemKbString());
		sb.append(",");
		sb.append(bean.getTenpoCdString());
		sb.append(",");
		sb.append(bean.getTenhankuCdString());
		sb.append(",");
		sb.append(bean.getLGyosyuCdString());
		sb.append(",");
		sb.append(bean.getSGyosyuCdString());
		sb.append(",");
		sb.append(bean.getLHankuCdString());
		sb.append(",");
		sb.append(bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append(bean.getNohinDtString());
		sb.append(",");
		sb.append(bean.getDenpyoNbString());
		sb.append(",");
		sb.append(bean.getHachuDtString());
		sb.append(",");
		sb.append(bean.getKbBusyoKbString());
		sb.append(",");
		sb.append(bean.getBinNbString());
		sb.append(",");
		sb.append(bean.getTeiBinNbString());
		sb.append(",");
		sb.append(bean.getTenpoNaString());
		sb.append(",");
		sb.append(bean.getTenpoKaString());
		sb.append(",");
		sb.append(bean.getTenhankuNaString());
		sb.append(",");
		sb.append(bean.getSGyosyuNaString());
		sb.append(",");
		sb.append(bean.getTorihikisakiNaString());
		sb.append(",");
		sb.append(bean.getTorihikisakiKaString());
		sb.append(",");
		sb.append(bean.getGenkaKeiVlString());
		sb.append(",");
		sb.append(bean.getBaikaKeiVlString());
		sb.append(",");
		sb.append(bean.getTeiGenkaKeiVlString());
		sb.append(",");
		sb.append(bean.getTeiBaikaKeiVlString());
		sb.append(",");
		sb.append(bean.getSeisenFgString());
		sb.append(",");
		sb.append(bean.getKbSobaKbString());
		sb.append(",");
		sb.append(bean.getHaisoKbString());
		sb.append(",");
		sb.append(bean.getTeiHaisoKbString());
		sb.append(",");
		sb.append(bean.getDenpyoKbString());
		sb.append(",");
		sb.append(bean.getZeiKbString());
		sb.append(",");
		sb.append(bean.getHachuKbString());
		sb.append(",");
		sb.append(bean.getTenpoHachuKbString());
		sb.append(",");
		sb.append(bean.getIkatuDenpFgString());
		sb.append(",");
		sb.append(bean.getSyohinThemeNaString());
		sb.append(",");
		sb.append(bean.getHaisosakiCdString());
		sb.append(",");
		sb.append(bean.getHaisosakiNaString());
		sb.append(",");
		sb.append(bean.getNohinCenterCdString());
		sb.append(",");
		sb.append(bean.getKeiyuCenterCdString());
		sb.append(",");
		sb.append(bean.getTenhaiCenterCdString());
		sb.append(",");
		sb.append(bean.getZaikoCenterCdString());
		sb.append(",");
		sb.append(bean.getTeiNohinCenterCdString());
		sb.append(",");
		sb.append(bean.getTeiKeiyuCenterCdString());
		sb.append(",");
		sb.append(bean.getTeiTenhaiCenterCdString());
		sb.append(",");
		sb.append(bean.getTeiZaikoCenterCdString());
		sb.append(",");
		sb.append(bean.getJuchuListOutputKbString());
		sb.append(",");
		sb.append(bean.getNohinSyoriKbString());
		sb.append(",");
		sb.append(bean.getHaisinsakiCdString());
		sb.append(",");
		sb.append(bean.getHojinCdString());
		sb.append(",");
		sb.append(bean.getHojinNaString());
		sb.append(",");
		sb.append(bean.getNohinMakeFgString());
		sb.append(",");
		sb.append(bean.getNohinMakeDtString());
		sb.append(",");
		sb.append(bean.getRiyoUserIdString());
		sb.append(",");
		sb.append(bean.getInsertTsString());
		sb.append(",");
		sb.append(bean.getUpdateTsString());
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
		DtHachuBean bean = (DtHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("dt_hachu set ");
		if( bean.getFileHeadNb() != null && bean.getFileHeadNb().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" file_head_nb = ");
			sb.append("'" + conv.convertString( bean.getFileHeadNb() ) + "'");
		}
		if( bean.getGaitoSystemKb() != null && bean.getGaitoSystemKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" gaito_system_kb = ");
			sb.append("'" + conv.convertString( bean.getGaitoSystemKb() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_cd = ");
			sb.append("'" + conv.convertString( bean.getTenhankuCd() ) + "'");
		}
		if( bean.getLGyosyuCd() != null && bean.getLGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" l_gyosyu_cd = ");
			sb.append("'" + conv.convertString( bean.getLGyosyuCd() ) + "'");
		}
		if( bean.getSGyosyuCd() != null && bean.getSGyosyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_gyosyu_cd = ");
			sb.append("'" + conv.convertString( bean.getSGyosyuCd() ) + "'");
		}
		if( bean.getLHankuCd() != null && bean.getLHankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" l_hanku_cd = ");
			sb.append("'" + conv.convertString( bean.getLHankuCd() ) + "'");
		}
		if( bean.getTorihikisakiCd() != null && bean.getTorihikisakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" torihikisaki_cd = ");
			sb.append("'" + conv.convertString( bean.getTorihikisakiCd() ) + "'");
		}
		if( bean.getNohinDt() != null && bean.getNohinDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_dt = ");
			sb.append("'" + conv.convertString( bean.getNohinDt() ) + "'");
		}
		if( bean.getDenpyoNb() != null && bean.getDenpyoNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" denpyo_nb = ");
			sb.append("'" + conv.convertString( bean.getDenpyoNb() ) + "'");
		}
		if( bean.getHachuDt() != null && bean.getHachuDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_dt = ");
			sb.append("'" + conv.convertString( bean.getHachuDt() ) + "'");
		}
		if( bean.getKbBusyoKb() != null && bean.getKbBusyoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kb_busyo_kb = ");
			sb.append("'" + conv.convertString( bean.getKbBusyoKb() ) + "'");
		}
		if( bean.getBinNb() != null && bean.getBinNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" bin_nb = ");
			sb.append("'" + conv.convertString( bean.getBinNb() ) + "'");
		}
		if( bean.getTeiBinNb() != null && bean.getTeiBinNb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tei_bin_nb = ");
			sb.append("'" + conv.convertString( bean.getTeiBinNb() ) + "'");
		}
		if( bean.getTenpoNa() != null && bean.getTenpoNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_na = ");
			sb.append("'" + conv.convertString( bean.getTenpoNa() ) + "'");
		}
		if( bean.getTenpoKa() != null && bean.getTenpoKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_ka = ");
			sb.append("'" + conv.convertString( bean.getTenpoKa() ) + "'");
		}
		if( bean.getTenhankuNa() != null && bean.getTenhankuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_na = ");
			sb.append("'" + conv.convertString( bean.getTenhankuNa() ) + "'");
		}
		if( bean.getSGyosyuNa() != null && bean.getSGyosyuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" s_gyosyu_na = ");
			sb.append("'" + conv.convertString( bean.getSGyosyuNa() ) + "'");
		}
		if( bean.getTorihikisakiNa() != null && bean.getTorihikisakiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" torihikisaki_na = ");
			sb.append("'" + conv.convertString( bean.getTorihikisakiNa() ) + "'");
		}
		if( bean.getTorihikisakiKa() != null && bean.getTorihikisakiKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" torihikisaki_ka = ");
			sb.append("'" + conv.convertString( bean.getTorihikisakiKa() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" genka_kei_vl = ");
		sb.append( bean.getGenkaKeiVlString());
		sb.append(",");
		sb.append(" baika_kei_vl = ");
		sb.append( bean.getBaikaKeiVlString());
		sb.append(",");
		sb.append(" tei_genka_kei_vl = ");
		sb.append( bean.getTeiGenkaKeiVlString());
		sb.append(",");
		sb.append(" tei_baika_kei_vl = ");
		sb.append( bean.getTeiBaikaKeiVlString());
		if( bean.getSeisenFg() != null && bean.getSeisenFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" seisen_fg = ");
			sb.append("'" + conv.convertString( bean.getSeisenFg() ) + "'");
		}
		if( bean.getKbSobaKb() != null && bean.getKbSobaKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kb_soba_kb = ");
			sb.append("'" + conv.convertString( bean.getKbSobaKb() ) + "'");
		}
		if( bean.getHaisoKb() != null && bean.getHaisoKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" haiso_kb = ");
			sb.append("'" + conv.convertString( bean.getHaisoKb() ) + "'");
		}
		if( bean.getTeiHaisoKb() != null && bean.getTeiHaisoKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tei_haiso_kb = ");
			sb.append("'" + conv.convertString( bean.getTeiHaisoKb() ) + "'");
		}
		if( bean.getDenpyoKb() != null && bean.getDenpyoKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" denpyo_kb = ");
			sb.append("'" + conv.convertString( bean.getDenpyoKb() ) + "'");
		}
		if( bean.getZeiKb() != null && bean.getZeiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" zei_kb = ");
			sb.append("'" + conv.convertString( bean.getZeiKb() ) + "'");
		}
		if( bean.getHachuKb() != null && bean.getHachuKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuKb() ) + "'");
		}
		if( bean.getTenpoHachuKb() != null && bean.getTenpoHachuKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenpo_hachu_kb = ");
			sb.append("'" + conv.convertString( bean.getTenpoHachuKb() ) + "'");
		}
		if( bean.getIkatuDenpFg() != null && bean.getIkatuDenpFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" ikatu_denp_fg = ");
			sb.append("'" + conv.convertString( bean.getIkatuDenpFg() ) + "'");
		}
		if( bean.getSyohinThemeNa() != null && bean.getSyohinThemeNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_theme_na = ");
			sb.append("'" + conv.convertString( bean.getSyohinThemeNa() ) + "'");
		}
		if( bean.getHaisosakiCd() != null && bean.getHaisosakiCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" haisosaki_cd = ");
			sb.append("'" + conv.convertString( bean.getHaisosakiCd() ) + "'");
		}
		if( bean.getHaisosakiNa() != null && bean.getHaisosakiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" haisosaki_na = ");
			sb.append("'" + conv.convertString( bean.getHaisosakiNa() ) + "'");
		}
		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_center_cd = ");
			sb.append("'" + conv.convertString( bean.getNohinCenterCd() ) + "'");
		}
		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" keiyu_center_cd = ");
			sb.append("'" + conv.convertString( bean.getKeiyuCenterCd() ) + "'");
		}
		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenhai_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTenhaiCenterCd() ) + "'");
		}
		if( bean.getZaikoCenterCd() != null && bean.getZaikoCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" zaiko_center_cd = ");
			sb.append("'" + conv.convertString( bean.getZaikoCenterCd() ) + "'");
		}
		if( bean.getTeiNohinCenterCd() != null && bean.getTeiNohinCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tei_nohin_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTeiNohinCenterCd() ) + "'");
		}
		if( bean.getTeiKeiyuCenterCd() != null && bean.getTeiKeiyuCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tei_keiyu_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTeiKeiyuCenterCd() ) + "'");
		}
		if( bean.getTeiTenhaiCenterCd() != null && bean.getTeiTenhaiCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tei_tenhai_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTeiTenhaiCenterCd() ) + "'");
		}
		if( bean.getTeiZaikoCenterCd() != null && bean.getTeiZaikoCenterCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tei_zaiko_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTeiZaikoCenterCd() ) + "'");
		}
		if( bean.getJuchuListOutputKb() != null && bean.getJuchuListOutputKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" juchu_list_output_kb = ");
			sb.append("'" + conv.convertString( bean.getJuchuListOutputKb() ) + "'");
		}
		if( bean.getNohinSyoriKb() != null && bean.getNohinSyoriKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_syori_kb = ");
			sb.append("'" + conv.convertString( bean.getNohinSyoriKb() ) + "'");
		}
		if( bean.getHaisinsakiCd() != null && bean.getHaisinsakiCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" haisinsaki_cd = ");
			sb.append("'" + conv.convertString( bean.getHaisinsakiCd() ) + "'");
		}
		if( bean.getHojinCd() != null && bean.getHojinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hojin_cd = ");
			sb.append("'" + conv.convertString( bean.getHojinCd() ) + "'");
		}
		if( bean.getHojinNa() != null && bean.getHojinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hojin_na = ");
			sb.append("'" + conv.convertString( bean.getHojinNa() ) + "'");
		}
		if( bean.getNohinMakeFg() != null && bean.getNohinMakeFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_make_fg = ");
			sb.append("'" + conv.convertString( bean.getNohinMakeFg() ) + "'");
		}
		if( bean.getNohinMakeDt() != null && bean.getNohinMakeDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_make_dt = ");
			sb.append("'" + conv.convertString( bean.getNohinMakeDt() ) + "'");
		}
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" riyo_user_id = ");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}


		sb.append(" WHERE");


		if( bean.getDataDenpNb() != null && bean.getDataDenpNb().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" data_denp_nb = ");
			sb.append("'" + conv.convertWhereString( bean.getDataDenpNb() ) + "'");
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
		DtHachuBean bean = (DtHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("dt_hachu where ");
		sb.append(" data_denp_nb = ");
		sb.append("'" + conv.convertWhereString( bean.getDataDenpNb() ) + "'");
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
