package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2005/1/6 22:52:16)
 */
public class DtHachuMeisaiDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public DtHachuMeisaiDM()
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
		DtHachuMeisaiBean bean = new DtHachuMeisaiBean();
		bean.setDataDenpNb( rest.getString("data_denp_nb") );
		bean.setDataDenpgyoNb( rest.getLong("data_denpgyo_nb") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setLGyosyuCd( rest.getString("l_gyosyu_cd") );
		bean.setSGyosyuCd( rest.getString("s_gyosyu_cd") );
		bean.setLHankuCd( rest.getString("l_hanku_cd") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setNohinDt( rest.getString("nohin_dt") );
		bean.setDenpyoNb( rest.getString("denpyo_nb") );
		bean.setDenpyogyoNb( rest.getLong("denpyogyo_nb") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setJisyaSyohinCd( rest.getString("jisya_syohin_cd") );
		bean.setNohinSyohinCd( rest.getString("nohin_syohin_cd") );
		bean.setPosCd( rest.getString("pos_cd") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setSyohinNa( rest.getString("syohin_na") );
		bean.setSyohinKa( rest.getString("syohin_ka") );
		bean.setKikakuKa( rest.getString("kikaku_ka") );
		bean.setHachuTaniNa( rest.getString("hachu_tani_na") );
		bean.setHachuTaniKa( rest.getString("hachu_tani_ka") );
		bean.setSantiCd( rest.getString("santi_cd") );
		bean.setTokaikyuCd( rest.getString("tokaikyu_cd") );
		bean.setKikakuCd( rest.getString("kikaku_cd") );
		bean.setSantiNa( rest.getString("santi_na") );
		bean.setTokaikyuNa( rest.getString("tokaikyu_na") );
		bean.setKikakuNa( rest.getString("kikaku_na") );
		bean.setColorCd( rest.getString("color_cd") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setColorKa( rest.getString("color_ka") );
		bean.setSizeKa( rest.getString("size_ka") );
		bean.setKeiretuKb( rest.getString("keiretu_kb") );
		bean.setSyohinKikakuNa( rest.getString("syohin_kikaku_na") );
		bean.setTorihikisakiSyohinCd( rest.getString("torihikisaki_syohin_cd") );
		bean.setTeikanKb( rest.getString("teikan_kb") );
		bean.setHanbaiSeisakuKb( rest.getString("hanbai_seisaku_kb") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setSyukoTankaVl( rest.getDouble("syuko_tanka_vl") );
		bean.setIrisuQt( rest.getDouble("irisu_qt") );
		bean.setHachuTaniQt( rest.getLong("hachu_tani_qt") );
		bean.setHachuQt( rest.getLong("hachu_qt") );
		bean.setHachuSuryoQt( rest.getDouble("hachu_suryo_qt") );
		bean.setKakuteiQt( rest.getLong("kakutei_qt") );
		bean.setKakuteiSuryoQt( rest.getDouble("kakutei_suryo_qt") );
		bean.setGenkaVl( rest.getDouble("genka_vl") );
		bean.setZeigakuVl( rest.getDouble("zeigaku_vl") );
		bean.setBaikaVl( rest.getLong("baika_vl") );
		bean.setSyukoVl( rest.getDouble("syuko_vl") );
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
		sb.append("data_denpgyo_nb ");
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
		sb.append("denpyogyo_nb ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("jisya_syohin_cd ");
		sb.append(", ");
		sb.append("nohin_syohin_cd ");
		sb.append(", ");
		sb.append("pos_cd ");
		sb.append(", ");
		sb.append("denpyo_hinban_cd ");
		sb.append(", ");
		sb.append("denpyo_syohin_cd ");
		sb.append(", ");
		sb.append("hinsyu_cd ");
		sb.append(", ");
		sb.append("tenpo_na ");
		sb.append(", ");
		sb.append("syohin_na ");
		sb.append(", ");
		sb.append("syohin_ka ");
		sb.append(", ");
		sb.append("kikaku_ka ");
		sb.append(", ");
		sb.append("hachu_tani_na ");
		sb.append(", ");
		sb.append("hachu_tani_ka ");
		sb.append(", ");
		sb.append("santi_cd ");
		sb.append(", ");
		sb.append("tokaikyu_cd ");
		sb.append(", ");
		sb.append("kikaku_cd ");
		sb.append(", ");
		sb.append("santi_na ");
		sb.append(", ");
		sb.append("tokaikyu_na ");
		sb.append(", ");
		sb.append("kikaku_na ");
		sb.append(", ");
		sb.append("color_cd ");
		sb.append(", ");
		sb.append("size_cd ");
		sb.append(", ");
		sb.append("color_ka ");
		sb.append(", ");
		sb.append("size_ka ");
		sb.append(", ");
		sb.append("keiretu_kb ");
		sb.append(", ");
		sb.append("syohin_kikaku_na ");
		sb.append(", ");
		sb.append("torihikisaki_syohin_cd ");
		sb.append(", ");
		sb.append("teikan_kb ");
		sb.append(", ");
		sb.append("hanbai_seisaku_kb ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("syuko_tanka_vl ");
		sb.append(", ");
		sb.append("irisu_qt ");
		sb.append(", ");
		sb.append("hachu_tani_qt ");
		sb.append(", ");
		sb.append("hachu_qt ");
		sb.append(", ");
		sb.append("hachu_suryo_qt ");
		sb.append(", ");
		sb.append("kakutei_qt ");
		sb.append(", ");
		sb.append("kakutei_suryo_qt ");
		sb.append(", ");
		sb.append("genka_vl ");
		sb.append(", ");
		sb.append("zeigaku_vl ");
		sb.append(", ");
		sb.append("baika_vl ");
		sb.append(", ");
		sb.append("syuko_vl ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from dt_hachu_meisai ");


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


		// data_denpgyo_nb に対するWHERE区
		if( map.get("data_denpgyo_nb_bef") != null && ((String)map.get("data_denpgyo_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb >= " + (String)map.get("data_denpgyo_nb_bef") );
			whereStr = andStr;
		}
		if( map.get("data_denpgyo_nb_aft") != null && ((String)map.get("data_denpgyo_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb <= " + (String)map.get("data_denpgyo_nb_aft") );
			whereStr = andStr;
		}
		if( map.get("data_denpgyo_nb") != null && ((String)map.get("data_denpgyo_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb = " + (String)map.get("data_denpgyo_nb"));
			whereStr = andStr;
		}
		if( map.get("data_denpgyo_nb_in") != null && ((String)map.get("data_denpgyo_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb in ( " + conv.convertWhereString( (String)map.get("data_denpgyo_nb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("data_denpgyo_nb_not_in") != null && ((String)map.get("data_denpgyo_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb not in ( " + conv.convertWhereString( (String)map.get("data_denpgyo_nb_not_in") ) + " )");
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


		// denpyogyo_nb に対するWHERE区
		if( map.get("denpyogyo_nb_bef") != null && ((String)map.get("denpyogyo_nb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb >= " + (String)map.get("denpyogyo_nb_bef") );
			whereStr = andStr;
		}
		if( map.get("denpyogyo_nb_aft") != null && ((String)map.get("denpyogyo_nb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb <= " + (String)map.get("denpyogyo_nb_aft") );
			whereStr = andStr;
		}
		if( map.get("denpyogyo_nb") != null && ((String)map.get("denpyogyo_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb = " + (String)map.get("denpyogyo_nb"));
			whereStr = andStr;
		}
		if( map.get("denpyogyo_nb_in") != null && ((String)map.get("denpyogyo_nb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb in ( " + conv.convertWhereString( (String)map.get("denpyogyo_nb_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("denpyogyo_nb_not_in") != null && ((String)map.get("denpyogyo_nb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb not in ( " + conv.convertWhereString( (String)map.get("denpyogyo_nb_not_in") ) + " )");
			whereStr = andStr;
		}


		// syohin_cd に対するWHERE区
		if( map.get("syohin_cd_bef") != null && ((String)map.get("syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft") != null && ((String)map.get("syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd <= '" + conv.convertWhereString( (String)map.get("syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_like") != null && ((String)map.get("syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_bef_like") != null && ((String)map.get("syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '%" + conv.convertWhereString( (String)map.get("syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_aft_like") != null && ((String)map.get("syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd like '" + conv.convertWhereString( (String)map.get("syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_in") != null && ((String)map.get("syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_cd_not_in") != null && ((String)map.get("syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// jisya_syohin_cd に対するWHERE区
		if( map.get("jisya_syohin_cd_bef") != null && ((String)map.get("jisya_syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd >= '" + conv.convertWhereString( (String)map.get("jisya_syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_aft") != null && ((String)map.get("jisya_syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd <= '" + conv.convertWhereString( (String)map.get("jisya_syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd") != null && ((String)map.get("jisya_syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd = '" + conv.convertWhereString( (String)map.get("jisya_syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_like") != null && ((String)map.get("jisya_syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd like '%" + conv.convertWhereString( (String)map.get("jisya_syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_bef_like") != null && ((String)map.get("jisya_syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd like '%" + conv.convertWhereString( (String)map.get("jisya_syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_aft_like") != null && ((String)map.get("jisya_syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd like '" + conv.convertWhereString( (String)map.get("jisya_syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_in") != null && ((String)map.get("jisya_syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("jisya_syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("jisya_syohin_cd_not_in") != null && ((String)map.get("jisya_syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("jisya_syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("jisya_syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nohin_syohin_cd に対するWHERE区
		if( map.get("nohin_syohin_cd_bef") != null && ((String)map.get("nohin_syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd >= '" + conv.convertWhereString( (String)map.get("nohin_syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_aft") != null && ((String)map.get("nohin_syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd <= '" + conv.convertWhereString( (String)map.get("nohin_syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd") != null && ((String)map.get("nohin_syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd = '" + conv.convertWhereString( (String)map.get("nohin_syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_like") != null && ((String)map.get("nohin_syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd like '%" + conv.convertWhereString( (String)map.get("nohin_syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_bef_like") != null && ((String)map.get("nohin_syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd like '%" + conv.convertWhereString( (String)map.get("nohin_syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_aft_like") != null && ((String)map.get("nohin_syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd like '" + conv.convertWhereString( (String)map.get("nohin_syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_in") != null && ((String)map.get("nohin_syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nohin_syohin_cd_not_in") != null && ((String)map.get("nohin_syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nohin_syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pos_cd に対するWHERE区
		if( map.get("pos_cd_bef") != null && ((String)map.get("pos_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd >= '" + conv.convertWhereString( (String)map.get("pos_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pos_cd_aft") != null && ((String)map.get("pos_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd <= '" + conv.convertWhereString( (String)map.get("pos_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pos_cd") != null && ((String)map.get("pos_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd = '" + conv.convertWhereString( (String)map.get("pos_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pos_cd_like") != null && ((String)map.get("pos_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd like '%" + conv.convertWhereString( (String)map.get("pos_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pos_cd_bef_like") != null && ((String)map.get("pos_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd like '%" + conv.convertWhereString( (String)map.get("pos_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pos_cd_aft_like") != null && ((String)map.get("pos_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd like '" + conv.convertWhereString( (String)map.get("pos_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pos_cd_in") != null && ((String)map.get("pos_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pos_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pos_cd_not_in") != null && ((String)map.get("pos_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pos_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pos_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// denpyo_hinban_cd に対するWHERE区
		if( map.get("denpyo_hinban_cd_bef") != null && ((String)map.get("denpyo_hinban_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd >= '" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_aft") != null && ((String)map.get("denpyo_hinban_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd <= '" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd") != null && ((String)map.get("denpyo_hinban_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd = '" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_like") != null && ((String)map.get("denpyo_hinban_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd like '%" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_bef_like") != null && ((String)map.get("denpyo_hinban_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd like '%" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_aft_like") != null && ((String)map.get("denpyo_hinban_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd like '" + conv.convertWhereString( (String)map.get("denpyo_hinban_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_in") != null && ((String)map.get("denpyo_hinban_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_hinban_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("denpyo_hinban_cd_not_in") != null && ((String)map.get("denpyo_hinban_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_hinban_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_hinban_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// denpyo_syohin_cd に対するWHERE区
		if( map.get("denpyo_syohin_cd_bef") != null && ((String)map.get("denpyo_syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd >= '" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_aft") != null && ((String)map.get("denpyo_syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd <= '" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd") != null && ((String)map.get("denpyo_syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd = '" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_like") != null && ((String)map.get("denpyo_syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd like '%" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_bef_like") != null && ((String)map.get("denpyo_syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd like '%" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_aft_like") != null && ((String)map.get("denpyo_syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd like '" + conv.convertWhereString( (String)map.get("denpyo_syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_in") != null && ((String)map.get("denpyo_syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("denpyo_syohin_cd_not_in") != null && ((String)map.get("denpyo_syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("denpyo_syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd_bef") != null && ((String)map.get("hinsyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd >= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_aft") != null && ((String)map.get("hinsyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd <= '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd = '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_like") != null && ((String)map.get("hinsyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_bef_like") != null && ((String)map.get("hinsyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '%" + conv.convertWhereString( (String)map.get("hinsyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_aft_like") != null && ((String)map.get("hinsyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd like '" + conv.convertWhereString( (String)map.get("hinsyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_in") != null && ((String)map.get("hinsyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinsyu_cd_not_in") != null && ((String)map.get("hinsyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_cd_not_in") ),",","','") + "' )");
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


		// syohin_na に対するWHERE区
		if( map.get("syohin_na_bef") != null && ((String)map.get("syohin_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na >= '" + conv.convertWhereString( (String)map.get("syohin_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_na_aft") != null && ((String)map.get("syohin_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na <= '" + conv.convertWhereString( (String)map.get("syohin_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_na") != null && ((String)map.get("syohin_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na = '" + conv.convertWhereString( (String)map.get("syohin_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_na_like") != null && ((String)map.get("syohin_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na like '%" + conv.convertWhereString( (String)map.get("syohin_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_na_bef_like") != null && ((String)map.get("syohin_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na like '%" + conv.convertWhereString( (String)map.get("syohin_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_na_aft_like") != null && ((String)map.get("syohin_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na like '" + conv.convertWhereString( (String)map.get("syohin_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_na_in") != null && ((String)map.get("syohin_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_na_not_in") != null && ((String)map.get("syohin_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_ka に対するWHERE区
		if( map.get("syohin_ka_bef") != null && ((String)map.get("syohin_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka >= '" + conv.convertWhereString( (String)map.get("syohin_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_aft") != null && ((String)map.get("syohin_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka <= '" + conv.convertWhereString( (String)map.get("syohin_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka") != null && ((String)map.get("syohin_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka = '" + conv.convertWhereString( (String)map.get("syohin_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_like") != null && ((String)map.get("syohin_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka like '%" + conv.convertWhereString( (String)map.get("syohin_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_bef_like") != null && ((String)map.get("syohin_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka like '%" + conv.convertWhereString( (String)map.get("syohin_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_aft_like") != null && ((String)map.get("syohin_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka like '" + conv.convertWhereString( (String)map.get("syohin_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_in") != null && ((String)map.get("syohin_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_ka_not_in") != null && ((String)map.get("syohin_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kikaku_ka に対するWHERE区
		if( map.get("kikaku_ka_bef") != null && ((String)map.get("kikaku_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka >= '" + conv.convertWhereString( (String)map.get("kikaku_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_aft") != null && ((String)map.get("kikaku_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka <= '" + conv.convertWhereString( (String)map.get("kikaku_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka") != null && ((String)map.get("kikaku_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka = '" + conv.convertWhereString( (String)map.get("kikaku_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_like") != null && ((String)map.get("kikaku_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka like '%" + conv.convertWhereString( (String)map.get("kikaku_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_bef_like") != null && ((String)map.get("kikaku_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka like '%" + conv.convertWhereString( (String)map.get("kikaku_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_aft_like") != null && ((String)map.get("kikaku_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka like '" + conv.convertWhereString( (String)map.get("kikaku_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_in") != null && ((String)map.get("kikaku_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_ka_not_in") != null && ((String)map.get("kikaku_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_tani_na に対するWHERE区
		if( map.get("hachu_tani_na_bef") != null && ((String)map.get("hachu_tani_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na >= '" + conv.convertWhereString( (String)map.get("hachu_tani_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_aft") != null && ((String)map.get("hachu_tani_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na <= '" + conv.convertWhereString( (String)map.get("hachu_tani_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na") != null && ((String)map.get("hachu_tani_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na = '" + conv.convertWhereString( (String)map.get("hachu_tani_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_like") != null && ((String)map.get("hachu_tani_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na like '%" + conv.convertWhereString( (String)map.get("hachu_tani_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_bef_like") != null && ((String)map.get("hachu_tani_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na like '%" + conv.convertWhereString( (String)map.get("hachu_tani_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_aft_like") != null && ((String)map.get("hachu_tani_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na like '" + conv.convertWhereString( (String)map.get("hachu_tani_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_in") != null && ((String)map.get("hachu_tani_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tani_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_na_not_in") != null && ((String)map.get("hachu_tani_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tani_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_tani_ka に対するWHERE区
		if( map.get("hachu_tani_ka_bef") != null && ((String)map.get("hachu_tani_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka >= '" + conv.convertWhereString( (String)map.get("hachu_tani_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_aft") != null && ((String)map.get("hachu_tani_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka <= '" + conv.convertWhereString( (String)map.get("hachu_tani_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka") != null && ((String)map.get("hachu_tani_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka = '" + conv.convertWhereString( (String)map.get("hachu_tani_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_like") != null && ((String)map.get("hachu_tani_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka like '%" + conv.convertWhereString( (String)map.get("hachu_tani_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_bef_like") != null && ((String)map.get("hachu_tani_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka like '%" + conv.convertWhereString( (String)map.get("hachu_tani_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_aft_like") != null && ((String)map.get("hachu_tani_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka like '" + conv.convertWhereString( (String)map.get("hachu_tani_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_in") != null && ((String)map.get("hachu_tani_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tani_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_ka_not_in") != null && ((String)map.get("hachu_tani_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_tani_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// santi_cd に対するWHERE区
		if( map.get("santi_cd_bef") != null && ((String)map.get("santi_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd >= '" + conv.convertWhereString( (String)map.get("santi_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_cd_aft") != null && ((String)map.get("santi_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd <= '" + conv.convertWhereString( (String)map.get("santi_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_cd") != null && ((String)map.get("santi_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd = '" + conv.convertWhereString( (String)map.get("santi_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_cd_like") != null && ((String)map.get("santi_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd like '%" + conv.convertWhereString( (String)map.get("santi_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("santi_cd_bef_like") != null && ((String)map.get("santi_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd like '%" + conv.convertWhereString( (String)map.get("santi_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_cd_aft_like") != null && ((String)map.get("santi_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd like '" + conv.convertWhereString( (String)map.get("santi_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("santi_cd_in") != null && ((String)map.get("santi_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("santi_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("santi_cd_not_in") != null && ((String)map.get("santi_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("santi_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tokaikyu_cd に対するWHERE区
		if( map.get("tokaikyu_cd_bef") != null && ((String)map.get("tokaikyu_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd >= '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_aft") != null && ((String)map.get("tokaikyu_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd <= '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd") != null && ((String)map.get("tokaikyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd = '" + conv.convertWhereString( (String)map.get("tokaikyu_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_like") != null && ((String)map.get("tokaikyu_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '%" + conv.convertWhereString( (String)map.get("tokaikyu_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_bef_like") != null && ((String)map.get("tokaikyu_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '%" + conv.convertWhereString( (String)map.get("tokaikyu_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_aft_like") != null && ((String)map.get("tokaikyu_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd like '" + conv.convertWhereString( (String)map.get("tokaikyu_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_in") != null && ((String)map.get("tokaikyu_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd_not_in") != null && ((String)map.get("tokaikyu_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kikaku_cd に対するWHERE区
		if( map.get("kikaku_cd_bef") != null && ((String)map.get("kikaku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd >= '" + conv.convertWhereString( (String)map.get("kikaku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_aft") != null && ((String)map.get("kikaku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd <= '" + conv.convertWhereString( (String)map.get("kikaku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd") != null && ((String)map.get("kikaku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd = '" + conv.convertWhereString( (String)map.get("kikaku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_like") != null && ((String)map.get("kikaku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd like '%" + conv.convertWhereString( (String)map.get("kikaku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_bef_like") != null && ((String)map.get("kikaku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd like '%" + conv.convertWhereString( (String)map.get("kikaku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_aft_like") != null && ((String)map.get("kikaku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd like '" + conv.convertWhereString( (String)map.get("kikaku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_in") != null && ((String)map.get("kikaku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd_not_in") != null && ((String)map.get("kikaku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// santi_na に対するWHERE区
		if( map.get("santi_na_bef") != null && ((String)map.get("santi_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na >= '" + conv.convertWhereString( (String)map.get("santi_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_na_aft") != null && ((String)map.get("santi_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na <= '" + conv.convertWhereString( (String)map.get("santi_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_na") != null && ((String)map.get("santi_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na = '" + conv.convertWhereString( (String)map.get("santi_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_na_like") != null && ((String)map.get("santi_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na like '%" + conv.convertWhereString( (String)map.get("santi_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("santi_na_bef_like") != null && ((String)map.get("santi_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na like '%" + conv.convertWhereString( (String)map.get("santi_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("santi_na_aft_like") != null && ((String)map.get("santi_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na like '" + conv.convertWhereString( (String)map.get("santi_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("santi_na_in") != null && ((String)map.get("santi_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("santi_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("santi_na_not_in") != null && ((String)map.get("santi_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("santi_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// tokaikyu_na に対するWHERE区
		if( map.get("tokaikyu_na_bef") != null && ((String)map.get("tokaikyu_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na >= '" + conv.convertWhereString( (String)map.get("tokaikyu_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_aft") != null && ((String)map.get("tokaikyu_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na <= '" + conv.convertWhereString( (String)map.get("tokaikyu_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na") != null && ((String)map.get("tokaikyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na = '" + conv.convertWhereString( (String)map.get("tokaikyu_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_like") != null && ((String)map.get("tokaikyu_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '%" + conv.convertWhereString( (String)map.get("tokaikyu_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_bef_like") != null && ((String)map.get("tokaikyu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '%" + conv.convertWhereString( (String)map.get("tokaikyu_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_aft_like") != null && ((String)map.get("tokaikyu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na like '" + conv.convertWhereString( (String)map.get("tokaikyu_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_in") != null && ((String)map.get("tokaikyu_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na_not_in") != null && ((String)map.get("tokaikyu_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("tokaikyu_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kikaku_na に対するWHERE区
		if( map.get("kikaku_na_bef") != null && ((String)map.get("kikaku_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na >= '" + conv.convertWhereString( (String)map.get("kikaku_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_aft") != null && ((String)map.get("kikaku_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na <= '" + conv.convertWhereString( (String)map.get("kikaku_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na") != null && ((String)map.get("kikaku_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na = '" + conv.convertWhereString( (String)map.get("kikaku_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_like") != null && ((String)map.get("kikaku_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na like '%" + conv.convertWhereString( (String)map.get("kikaku_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_bef_like") != null && ((String)map.get("kikaku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na like '%" + conv.convertWhereString( (String)map.get("kikaku_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_aft_like") != null && ((String)map.get("kikaku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na like '" + conv.convertWhereString( (String)map.get("kikaku_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_in") != null && ((String)map.get("kikaku_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kikaku_na_not_in") != null && ((String)map.get("kikaku_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kikaku_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// color_cd に対するWHERE区
		if( map.get("color_cd_bef") != null && ((String)map.get("color_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd >= '" + conv.convertWhereString( (String)map.get("color_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_aft") != null && ((String)map.get("color_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd <= '" + conv.convertWhereString( (String)map.get("color_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd") != null && ((String)map.get("color_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd = '" + conv.convertWhereString( (String)map.get("color_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_like") != null && ((String)map.get("color_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd like '%" + conv.convertWhereString( (String)map.get("color_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_cd_bef_like") != null && ((String)map.get("color_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd like '%" + conv.convertWhereString( (String)map.get("color_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_cd_aft_like") != null && ((String)map.get("color_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd like '" + conv.convertWhereString( (String)map.get("color_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_cd_in") != null && ((String)map.get("color_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("color_cd_not_in") != null && ((String)map.get("color_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// size_cd に対するWHERE区
		if( map.get("size_cd_bef") != null && ((String)map.get("size_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd >= '" + conv.convertWhereString( (String)map.get("size_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_aft") != null && ((String)map.get("size_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd <= '" + conv.convertWhereString( (String)map.get("size_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd") != null && ((String)map.get("size_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd = '" + conv.convertWhereString( (String)map.get("size_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_like") != null && ((String)map.get("size_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd like '%" + conv.convertWhereString( (String)map.get("size_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_cd_bef_like") != null && ((String)map.get("size_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd like '%" + conv.convertWhereString( (String)map.get("size_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_cd_aft_like") != null && ((String)map.get("size_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd like '" + conv.convertWhereString( (String)map.get("size_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_cd_in") != null && ((String)map.get("size_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("size_cd_not_in") != null && ((String)map.get("size_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// color_ka に対するWHERE区
		if( map.get("color_ka_bef") != null && ((String)map.get("color_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka >= '" + conv.convertWhereString( (String)map.get("color_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_ka_aft") != null && ((String)map.get("color_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka <= '" + conv.convertWhereString( (String)map.get("color_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_ka") != null && ((String)map.get("color_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka = '" + conv.convertWhereString( (String)map.get("color_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_ka_like") != null && ((String)map.get("color_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka like '%" + conv.convertWhereString( (String)map.get("color_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_ka_bef_like") != null && ((String)map.get("color_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka like '%" + conv.convertWhereString( (String)map.get("color_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("color_ka_aft_like") != null && ((String)map.get("color_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka like '" + conv.convertWhereString( (String)map.get("color_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("color_ka_in") != null && ((String)map.get("color_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("color_ka_not_in") != null && ((String)map.get("color_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("color_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("color_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// size_ka に対するWHERE区
		if( map.get("size_ka_bef") != null && ((String)map.get("size_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka >= '" + conv.convertWhereString( (String)map.get("size_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_ka_aft") != null && ((String)map.get("size_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka <= '" + conv.convertWhereString( (String)map.get("size_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_ka") != null && ((String)map.get("size_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka = '" + conv.convertWhereString( (String)map.get("size_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_ka_like") != null && ((String)map.get("size_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka like '%" + conv.convertWhereString( (String)map.get("size_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_ka_bef_like") != null && ((String)map.get("size_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka like '%" + conv.convertWhereString( (String)map.get("size_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("size_ka_aft_like") != null && ((String)map.get("size_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka like '" + conv.convertWhereString( (String)map.get("size_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("size_ka_in") != null && ((String)map.get("size_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("size_ka_not_in") != null && ((String)map.get("size_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("size_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("size_ka_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		
		// keiretu_kb に対するWHERE区
		if( map.get("keiretu_kb_bef") != null && ((String)map.get("keiretu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb >= '" + conv.convertWhereString( (String)map.get("keiretu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_aft") != null && ((String)map.get("keiretu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb <= '" + conv.convertWhereString( (String)map.get("keiretu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb") != null && ((String)map.get("keiretu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb = '" + conv.convertWhereString( (String)map.get("keiretu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_like") != null && ((String)map.get("keiretu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb like '%" + conv.convertWhereString( (String)map.get("keiretu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_bef_like") != null && ((String)map.get("keiretu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb like '%" + conv.convertWhereString( (String)map.get("keiretu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_aft_like") != null && ((String)map.get("keiretu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb like '" + conv.convertWhereString( (String)map.get("keiretu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_in") != null && ((String)map.get("keiretu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiretu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("keiretu_kb_not_in") != null && ((String)map.get("keiretu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("keiretu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("keiretu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syohin_kikaku_na に対するWHERE区
		if( map.get("syohin_kikaku_na_bef") != null && ((String)map.get("syohin_kikaku_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na >= '" + conv.convertWhereString( (String)map.get("syohin_kikaku_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_aft") != null && ((String)map.get("syohin_kikaku_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na <= '" + conv.convertWhereString( (String)map.get("syohin_kikaku_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na") != null && ((String)map.get("syohin_kikaku_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na = '" + conv.convertWhereString( (String)map.get("syohin_kikaku_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_like") != null && ((String)map.get("syohin_kikaku_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na like '%" + conv.convertWhereString( (String)map.get("syohin_kikaku_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_bef_like") != null && ((String)map.get("syohin_kikaku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na like '%" + conv.convertWhereString( (String)map.get("syohin_kikaku_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_aft_like") != null && ((String)map.get("syohin_kikaku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na like '" + conv.convertWhereString( (String)map.get("syohin_kikaku_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_in") != null && ((String)map.get("syohin_kikaku_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kikaku_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syohin_kikaku_na_not_in") != null && ((String)map.get("syohin_kikaku_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_kikaku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syohin_kikaku_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// torihikisaki_syohin_cd に対するWHERE区
		if( map.get("torihikisaki_syohin_cd_bef") != null && ((String)map.get("torihikisaki_syohin_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd >= '" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_aft") != null && ((String)map.get("torihikisaki_syohin_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd <= '" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd") != null && ((String)map.get("torihikisaki_syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd = '" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_like") != null && ((String)map.get("torihikisaki_syohin_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd like '%" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_bef_like") != null && ((String)map.get("torihikisaki_syohin_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd like '%" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_aft_like") != null && ((String)map.get("torihikisaki_syohin_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd like '" + conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_in") != null && ((String)map.get("torihikisaki_syohin_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_syohin_cd_not_in") != null && ((String)map.get("torihikisaki_syohin_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_syohin_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("torihikisaki_syohin_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// teikan_kb に対するWHERE区
		if( map.get("teikan_kb_bef") != null && ((String)map.get("teikan_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb >= '" + conv.convertWhereString( (String)map.get("teikan_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_aft") != null && ((String)map.get("teikan_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb <= '" + conv.convertWhereString( (String)map.get("teikan_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb") != null && ((String)map.get("teikan_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb = '" + conv.convertWhereString( (String)map.get("teikan_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_like") != null && ((String)map.get("teikan_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb like '%" + conv.convertWhereString( (String)map.get("teikan_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_bef_like") != null && ((String)map.get("teikan_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb like '%" + conv.convertWhereString( (String)map.get("teikan_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_aft_like") != null && ((String)map.get("teikan_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb like '" + conv.convertWhereString( (String)map.get("teikan_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_in") != null && ((String)map.get("teikan_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teikan_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("teikan_kb_not_in") != null && ((String)map.get("teikan_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("teikan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("teikan_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanbai_seisaku_kb に対するWHERE区
		if( map.get("hanbai_seisaku_kb_bef") != null && ((String)map.get("hanbai_seisaku_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb >= '" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_aft") != null && ((String)map.get("hanbai_seisaku_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb <= '" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb") != null && ((String)map.get("hanbai_seisaku_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb = '" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_like") != null && ((String)map.get("hanbai_seisaku_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_bef_like") != null && ((String)map.get("hanbai_seisaku_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_aft_like") != null && ((String)map.get("hanbai_seisaku_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb like '" + conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_in") != null && ((String)map.get("hanbai_seisaku_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_seisaku_kb_not_in") != null && ((String)map.get("hanbai_seisaku_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_seisaku_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_seisaku_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// gentanka_vl に対するWHERE区
		if( map.get("gentanka_vl_bef") != null && ((String)map.get("gentanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl >= " + (String)map.get("gentanka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_aft") != null && ((String)map.get("gentanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl <= " + (String)map.get("gentanka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("gentanka_vl") != null && ((String)map.get("gentanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl = " + (String)map.get("gentanka_vl"));
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_in") != null && ((String)map.get("gentanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl in ( " + conv.convertWhereString( (String)map.get("gentanka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl_not_in") != null && ((String)map.get("gentanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl not in ( " + conv.convertWhereString( (String)map.get("gentanka_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baitanka_vl に対するWHERE区
		if( map.get("baitanka_vl_bef") != null && ((String)map.get("baitanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl >= " + (String)map.get("baitanka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_aft") != null && ((String)map.get("baitanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl <= " + (String)map.get("baitanka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("baitanka_vl") != null && ((String)map.get("baitanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl = " + (String)map.get("baitanka_vl"));
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_in") != null && ((String)map.get("baitanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl in ( " + conv.convertWhereString( (String)map.get("baitanka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baitanka_vl_not_in") != null && ((String)map.get("baitanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl not in ( " + conv.convertWhereString( (String)map.get("baitanka_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// syuko_tanka_vl に対するWHERE区
		if( map.get("syuko_tanka_vl_bef") != null && ((String)map.get("syuko_tanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_tanka_vl >= " + (String)map.get("syuko_tanka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("syuko_tanka_vl_aft") != null && ((String)map.get("syuko_tanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_tanka_vl <= " + (String)map.get("syuko_tanka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("syuko_tanka_vl") != null && ((String)map.get("syuko_tanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("syuko_tanka_vl = " + (String)map.get("syuko_tanka_vl"));
			whereStr = andStr;
		}
		if( map.get("syuko_tanka_vl_in") != null && ((String)map.get("syuko_tanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_tanka_vl in ( " + conv.convertWhereString( (String)map.get("syuko_tanka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("syuko_tanka_vl_not_in") != null && ((String)map.get("syuko_tanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_tanka_vl not in ( " + conv.convertWhereString( (String)map.get("syuko_tanka_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// irisu_qt に対するWHERE区
		if( map.get("irisu_qt_bef") != null && ((String)map.get("irisu_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("irisu_qt >= " + (String)map.get("irisu_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("irisu_qt_aft") != null && ((String)map.get("irisu_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("irisu_qt <= " + (String)map.get("irisu_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("irisu_qt") != null && ((String)map.get("irisu_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("irisu_qt = " + (String)map.get("irisu_qt"));
			whereStr = andStr;
		}
		if( map.get("irisu_qt_in") != null && ((String)map.get("irisu_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("irisu_qt in ( " + conv.convertWhereString( (String)map.get("irisu_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("irisu_qt_not_in") != null && ((String)map.get("irisu_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("irisu_qt not in ( " + conv.convertWhereString( (String)map.get("irisu_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// hachu_tani_qt に対するWHERE区
		if( map.get("hachu_tani_qt_bef") != null && ((String)map.get("hachu_tani_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt >= " + (String)map.get("hachu_tani_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("hachu_tani_qt_aft") != null && ((String)map.get("hachu_tani_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt <= " + (String)map.get("hachu_tani_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("hachu_tani_qt") != null && ((String)map.get("hachu_tani_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt = " + (String)map.get("hachu_tani_qt"));
			whereStr = andStr;
		}
		if( map.get("hachu_tani_qt_in") != null && ((String)map.get("hachu_tani_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt in ( " + conv.convertWhereString( (String)map.get("hachu_tani_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("hachu_tani_qt_not_in") != null && ((String)map.get("hachu_tani_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt not in ( " + conv.convertWhereString( (String)map.get("hachu_tani_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// hachu_qt に対するWHERE区
		if( map.get("hachu_qt_bef") != null && ((String)map.get("hachu_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_qt >= " + (String)map.get("hachu_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("hachu_qt_aft") != null && ((String)map.get("hachu_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_qt <= " + (String)map.get("hachu_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("hachu_qt") != null && ((String)map.get("hachu_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_qt = " + (String)map.get("hachu_qt"));
			whereStr = andStr;
		}
		if( map.get("hachu_qt_in") != null && ((String)map.get("hachu_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_qt in ( " + conv.convertWhereString( (String)map.get("hachu_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("hachu_qt_not_in") != null && ((String)map.get("hachu_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_qt not in ( " + conv.convertWhereString( (String)map.get("hachu_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// hachu_suryo_qt に対するWHERE区
		if( map.get("hachu_suryo_qt_bef") != null && ((String)map.get("hachu_suryo_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt >= " + (String)map.get("hachu_suryo_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("hachu_suryo_qt_aft") != null && ((String)map.get("hachu_suryo_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt <= " + (String)map.get("hachu_suryo_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("hachu_suryo_qt") != null && ((String)map.get("hachu_suryo_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt = " + (String)map.get("hachu_suryo_qt"));
			whereStr = andStr;
		}
		if( map.get("hachu_suryo_qt_in") != null && ((String)map.get("hachu_suryo_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt in ( " + conv.convertWhereString( (String)map.get("hachu_suryo_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("hachu_suryo_qt_not_in") != null && ((String)map.get("hachu_suryo_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt not in ( " + conv.convertWhereString( (String)map.get("hachu_suryo_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// kakutei_qt に対するWHERE区
		if( map.get("kakutei_qt_bef") != null && ((String)map.get("kakutei_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt >= " + (String)map.get("kakutei_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("kakutei_qt_aft") != null && ((String)map.get("kakutei_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt <= " + (String)map.get("kakutei_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("kakutei_qt") != null && ((String)map.get("kakutei_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt = " + (String)map.get("kakutei_qt"));
			whereStr = andStr;
		}
		if( map.get("kakutei_qt_in") != null && ((String)map.get("kakutei_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt in ( " + conv.convertWhereString( (String)map.get("kakutei_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("kakutei_qt_not_in") != null && ((String)map.get("kakutei_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt not in ( " + conv.convertWhereString( (String)map.get("kakutei_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// kakutei_suryo_qt に対するWHERE区
		if( map.get("kakutei_suryo_qt_bef") != null && ((String)map.get("kakutei_suryo_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt >= " + (String)map.get("kakutei_suryo_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("kakutei_suryo_qt_aft") != null && ((String)map.get("kakutei_suryo_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt <= " + (String)map.get("kakutei_suryo_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("kakutei_suryo_qt") != null && ((String)map.get("kakutei_suryo_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt = " + (String)map.get("kakutei_suryo_qt"));
			whereStr = andStr;
		}
		if( map.get("kakutei_suryo_qt_in") != null && ((String)map.get("kakutei_suryo_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt in ( " + conv.convertWhereString( (String)map.get("kakutei_suryo_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("kakutei_suryo_qt_not_in") != null && ((String)map.get("kakutei_suryo_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt not in ( " + conv.convertWhereString( (String)map.get("kakutei_suryo_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// genka_vl に対するWHERE区
		if( map.get("genka_vl_bef") != null && ((String)map.get("genka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_vl >= " + (String)map.get("genka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("genka_vl_aft") != null && ((String)map.get("genka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_vl <= " + (String)map.get("genka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("genka_vl") != null && ((String)map.get("genka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("genka_vl = " + (String)map.get("genka_vl"));
			whereStr = andStr;
		}
		if( map.get("genka_vl_in") != null && ((String)map.get("genka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_vl in ( " + conv.convertWhereString( (String)map.get("genka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("genka_vl_not_in") != null && ((String)map.get("genka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("genka_vl not in ( " + conv.convertWhereString( (String)map.get("genka_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// zeigaku_vl に対するWHERE区
		if( map.get("zeigaku_vl_bef") != null && ((String)map.get("zeigaku_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zeigaku_vl >= " + (String)map.get("zeigaku_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("zeigaku_vl_aft") != null && ((String)map.get("zeigaku_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zeigaku_vl <= " + (String)map.get("zeigaku_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("zeigaku_vl") != null && ((String)map.get("zeigaku_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zeigaku_vl = " + (String)map.get("zeigaku_vl"));
			whereStr = andStr;
		}
		if( map.get("zeigaku_vl_in") != null && ((String)map.get("zeigaku_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zeigaku_vl in ( " + conv.convertWhereString( (String)map.get("zeigaku_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zeigaku_vl_not_in") != null && ((String)map.get("zeigaku_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zeigaku_vl not in ( " + conv.convertWhereString( (String)map.get("zeigaku_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baika_vl に対するWHERE区
		if( map.get("baika_vl_bef") != null && ((String)map.get("baika_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_vl >= " + (String)map.get("baika_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("baika_vl_aft") != null && ((String)map.get("baika_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_vl <= " + (String)map.get("baika_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("baika_vl") != null && ((String)map.get("baika_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baika_vl = " + (String)map.get("baika_vl"));
			whereStr = andStr;
		}
		if( map.get("baika_vl_in") != null && ((String)map.get("baika_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_vl in ( " + conv.convertWhereString( (String)map.get("baika_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baika_vl_not_in") != null && ((String)map.get("baika_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baika_vl not in ( " + conv.convertWhereString( (String)map.get("baika_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// syuko_vl に対するWHERE区
		if( map.get("syuko_vl_bef") != null && ((String)map.get("syuko_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_vl >= " + (String)map.get("syuko_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("syuko_vl_aft") != null && ((String)map.get("syuko_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_vl <= " + (String)map.get("syuko_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("syuko_vl") != null && ((String)map.get("syuko_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("syuko_vl = " + (String)map.get("syuko_vl"));
			whereStr = andStr;
		}
		if( map.get("syuko_vl_in") != null && ((String)map.get("syuko_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_vl in ( " + conv.convertWhereString( (String)map.get("syuko_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("syuko_vl_not_in") != null && ((String)map.get("syuko_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_vl not in ( " + conv.convertWhereString( (String)map.get("syuko_vl_not_in") ) + " )");
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
		sb.append(",");
		sb.append("data_denpgyo_nb");
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
		DtHachuMeisaiBean bean = (DtHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("dt_hachu_meisai (");
		sb.append("data_denp_nb ");
		sb.append(", ");
		sb.append("data_denpgyo_nb ");
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
		sb.append("denpyogyo_nb ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("jisya_syohin_cd ");
		sb.append(", ");
		sb.append("nohin_syohin_cd ");
		sb.append(", ");
		sb.append("pos_cd ");
		sb.append(", ");
		sb.append("hinsyu_cd ");
		sb.append(", ");
		sb.append("tenpo_na ");
		sb.append(", ");
		sb.append("syohin_na ");
		sb.append(", ");
		sb.append("syohin_ka ");
		sb.append(", ");
		sb.append("kikaku_ka ");
		sb.append(", ");
		sb.append("hachu_tani_na ");
		sb.append(", ");
		sb.append("hachu_tani_ka ");
		sb.append(", ");
		sb.append("santi_cd ");
		sb.append(", ");
		sb.append("tokaikyu_cd ");
		sb.append(", ");
		sb.append("kikaku_cd ");
		sb.append(", ");
		sb.append("santi_na ");
		sb.append(", ");
		sb.append("tokaikyu_na ");
		sb.append(", ");
		sb.append("kikaku_na ");
		sb.append(", ");
		sb.append("color_cd ");
		sb.append(", ");
		sb.append("size_cd ");
		sb.append(", ");
		sb.append("color_ka ");
		sb.append(", ");
		sb.append("size_ka ");
		sb.append(", ");
		sb.append("keiretu_kb ");
		sb.append(", ");
		sb.append("syohin_kikaku_na ");
		sb.append(", ");
		sb.append("torihikisaki_syohin_cd ");
		sb.append(", ");
		sb.append("teikan_kb ");
		sb.append(", ");
		sb.append("hanbai_seisaku_kb ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("syuko_tanka_vl ");
		sb.append(", ");
		sb.append("irisu_qt ");
		sb.append(", ");
		sb.append("hachu_tani_qt ");
		sb.append(", ");
		sb.append("hachu_qt ");
		sb.append(", ");
		sb.append("hachu_suryo_qt ");
		sb.append(", ");
		sb.append("kakutei_qt ");
		sb.append(", ");
		sb.append("kakutei_suryo_qt ");
		sb.append(", ");
		sb.append("genka_vl ");
		sb.append(", ");
		sb.append("zeigaku_vl ");
		sb.append(", ");
		sb.append("baika_vl ");
		sb.append(", ");
		sb.append("syuko_vl ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");

		sb.append(")Values(");

		sb.append(bean.getDataDenpNbString());
		sb.append(",");
		sb.append(bean.getDataDenpgyoNbString());
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
		sb.append(bean.getDenpyogyoNbString());
		sb.append(",");
		sb.append(bean.getSyohinCdString());
		sb.append(",");
		sb.append(bean.getJisyaSyohinCdString());
		sb.append(",");
		sb.append(bean.getNohinSyohinCdString());
		sb.append(",");
		sb.append(bean.getPosCdString());
		sb.append(",");
		sb.append(bean.getHinsyuCdString());
		sb.append(",");
		sb.append(bean.getTenpoNaString());
		sb.append(",");
		sb.append(bean.getSyohinNaString());
		sb.append(",");
		sb.append(bean.getSyohinKaString());
		sb.append(",");
		sb.append(bean.getKikakuKaString());
		sb.append(",");
		sb.append(bean.getHachuTaniNaString());
		sb.append(",");
		sb.append(bean.getHachuTaniKaString());
		sb.append(",");
		sb.append(bean.getSantiCdString());
		sb.append(",");
		sb.append(bean.getTokaikyuCdString());
		sb.append(",");
		sb.append(bean.getKikakuCdString());
		sb.append(",");
		sb.append(bean.getSantiNaString());
		sb.append(",");
		sb.append(bean.getTokaikyuNaString());
		sb.append(",");
		sb.append(bean.getKikakuNaString());
		sb.append(",");
		sb.append(bean.getColorCdString());
		sb.append(",");
		sb.append(bean.getSizeCdString());
		sb.append(",");
		sb.append(bean.getColorKaString());
		sb.append(",");
		sb.append(bean.getSizeKaString());
		sb.append(",");
		sb.append(bean.getKeiretuKbString());
		sb.append(",");
		sb.append(bean.getSyohinKikakuNaString());
		sb.append(",");
		sb.append(bean.getTorihikisakiSyohinCdString());
		sb.append(",");
		sb.append(bean.getTeikanKbString());
		sb.append(",");
		sb.append(bean.getHanbaiSeisakuKbString());
		sb.append(",");
		sb.append(bean.getGentankaVlString());
		sb.append(",");
		sb.append(bean.getBaitankaVlString());
		sb.append(",");
		sb.append(bean.getSyukoTankaVlString());
		sb.append(",");
		sb.append(bean.getIrisuQtString());
		sb.append(",");
		sb.append(bean.getHachuTaniQtString());
		sb.append(",");
		sb.append(bean.getHachuQtString());
		sb.append(",");
		sb.append(bean.getHachuSuryoQtString());
		sb.append(",");
		sb.append(bean.getKakuteiQtString());
		sb.append(",");
		sb.append(bean.getKakuteiSuryoQtString());
		sb.append(",");
		sb.append(bean.getGenkaVlString());
		sb.append(",");
		sb.append(bean.getZeigakuVlString());
		sb.append(",");
		sb.append(bean.getBaikaVlString());
		sb.append(",");
		sb.append(bean.getSyukoVlString());
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
		DtHachuMeisaiBean bean = (DtHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("dt_hachu_meisai set ");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
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
		if( befKanma ) sb.append(",");
		sb.append(" denpyogyo_nb = ");
		sb.append( bean.getDenpyogyoNbString());
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getJisyaSyohinCd() != null && bean.getJisyaSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" jisya_syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getJisyaSyohinCd() ) + "'");
		}
		if( bean.getNohinSyohinCd() != null && bean.getNohinSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getNohinSyohinCd() ) + "'");
		}
		if( bean.getPosCd() != null && bean.getPosCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" pos_cd = ");
			sb.append("'" + conv.convertString( bean.getPosCd() ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hinsyu_cd = ");
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		if( bean.getTenpoNa() != null && bean.getTenpoNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tenpo_na = ");
			sb.append("'" + conv.convertString( bean.getTenpoNa() ) + "'");
		}
		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_na = ");
			sb.append("'" + conv.convertString( bean.getSyohinNa() ) + "'");
		}
		if( bean.getSyohinKa() != null && bean.getSyohinKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_ka = ");
			sb.append("'" + conv.convertString( bean.getSyohinKa() ) + "'");
		}
		if( bean.getKikakuKa() != null && bean.getKikakuKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kikaku_ka = ");
			sb.append("'" + conv.convertString( bean.getKikakuKa() ) + "'");
		}
		if( bean.getHachuTaniNa() != null && bean.getHachuTaniNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_tani_na = ");
			sb.append("'" + conv.convertString( bean.getHachuTaniNa() ) + "'");
		}
		if( bean.getHachuTaniKa() != null && bean.getHachuTaniKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_tani_ka = ");
			sb.append("'" + conv.convertString( bean.getHachuTaniKa() ) + "'");
		}
		if( bean.getSantiCd() != null && bean.getSantiCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" santi_cd = ");
			sb.append("'" + conv.convertString( bean.getSantiCd() ) + "'");
		}
		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tokaikyu_cd = ");
			sb.append("'" + conv.convertString( bean.getTokaikyuCd() ) + "'");
		}
		if( bean.getKikakuCd() != null && bean.getKikakuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kikaku_cd = ");
			sb.append("'" + conv.convertString( bean.getKikakuCd() ) + "'");
		}
		if( bean.getSantiNa() != null && bean.getSantiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" santi_na = ");
			sb.append("'" + conv.convertString( bean.getSantiNa() ) + "'");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tokaikyu_na = ");
			sb.append("'" + conv.convertString( bean.getTokaikyuNa() ) + "'");
		}
		if( bean.getKikakuNa() != null && bean.getKikakuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kikaku_na = ");
			sb.append("'" + conv.convertString( bean.getKikakuNa() ) + "'");
		}
		if( bean.getColorCd() != null && bean.getColorCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" color_cd = ");
			sb.append("'" + conv.convertString( bean.getColorCd() ) + "'");
		}
		if( bean.getSizeCd() != null && bean.getSizeCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" size_cd = ");
			sb.append("'" + conv.convertString( bean.getSizeCd() ) + "'");
		}
		if( bean.getColorKa() != null && bean.getColorKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" color_ka = ");
			sb.append("'" + conv.convertString( bean.getColorKa() ) + "'");
		}
		if( bean.getSizeKa() != null && bean.getSizeKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" size_ka = ");
			sb.append("'" + conv.convertString( bean.getSizeKa() ) + "'");
		}
		if( bean.getKeiretuKb() != null && bean.getKeiretuKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" keiretu_kb = ");
			sb.append("'" + conv.convertString( bean.getKeiretuKb() ) + "'");
		}
		if( bean.getSyohinKikakuNa() != null && bean.getSyohinKikakuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_kikaku_na = ");
			sb.append("'" + conv.convertString( bean.getSyohinKikakuNa() ) + "'");
		}
		if( bean.getTorihikisakiSyohinCd() != null && bean.getTorihikisakiSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" torihikisaki_syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getTorihikisakiSyohinCd() ) + "'");
		}
		if( bean.getTeikanKb() != null && bean.getTeikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" teikan_kb = ");
			sb.append("'" + conv.convertString( bean.getTeikanKb() ) + "'");
		}
		if( bean.getHanbaiSeisakuKb() != null && bean.getHanbaiSeisakuKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hanbai_seisaku_kb = ");
			sb.append("'" + conv.convertString( bean.getHanbaiSeisakuKb() ) + "'");
		}
		sb.append(",");
		sb.append(" gentanka_vl = ");
		sb.append( bean.getGentankaVlString());
		sb.append(",");
		sb.append(" baitanka_vl = ");
		sb.append( bean.getBaitankaVlString());
		sb.append(",");
		sb.append(" syuko_tanka_vl = ");
		sb.append( bean.getSyukoTankaVlString());
		sb.append(",");
		sb.append(" irisu_qt = ");
		sb.append( bean.getIrisuQtString());
		sb.append(",");
		sb.append(" hachu_tani_qt = ");
		sb.append( bean.getHachuTaniQtString());
		sb.append(",");
		sb.append(" hachu_qt = ");
		sb.append( bean.getHachuQtString());
		sb.append(",");
		sb.append(" hachu_suryo_qt = ");
		sb.append( bean.getHachuSuryoQtString());
		sb.append(",");
		sb.append(" kakutei_qt = ");
		sb.append( bean.getKakuteiQtString());
		sb.append(",");
		sb.append(" kakutei_suryo_qt = ");
		sb.append( bean.getKakuteiSuryoQtString());
		sb.append(",");
		sb.append(" genka_vl = ");
		sb.append( bean.getGenkaVlString());
		sb.append(",");
		sb.append(" zeigaku_vl = ");
		sb.append( bean.getZeigakuVlString());
		sb.append(",");
		sb.append(" baika_vl = ");
		sb.append( bean.getBaikaVlString());
		sb.append(",");
		sb.append(" syuko_vl = ");
		sb.append( bean.getSyukoVlString());
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
		if( whereAnd ) sb.append(" AND ");
		sb.append(" data_denpgyo_nb = ");
		sb.append( bean.getDataDenpgyoNbString());
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
		DtHachuMeisaiBean bean = (DtHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("dt_hachu_meisai where ");
		sb.append(" data_denp_nb = ");
		sb.append("'" + conv.convertWhereString( bean.getDataDenpNb() ) + "'");
		sb.append(" AND");
		sb.append(" data_denpgyo_nb = ");
		sb.append( bean.getDataDenpgyoNbString());
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
