package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator for SQL (2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/8/5 14:40:24)
 */
public class MaSyohinDM extends DataModule
{
/* DM 生成時に使用した SQL 文です。
select * from ma_syohin
*/
	/**
	 * コンストラクタ
	 */
	public MaSyohinDM()
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
		MaSyohinBean bean = new MaSyohinBean();
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setBeginDt( rest.getString("begin_dt") );
		bean.setNohinSyohinCd( rest.getString("nohin_syohin_cd") );
		bean.setPosCd( rest.getString("pos_cd") );
		bean.setJisyaSyohinCd( rest.getString("jisya_syohin_cd") );
		bean.setSyohinNa( rest.getString("syohin_na") );
		bean.setSyohinKa( rest.getString("syohin_ka") );
		bean.setKikakuNa( rest.getString("kikaku_na") );
		bean.setKikakuKa( rest.getString("kikaku_ka") );
		bean.setLHankuCd( rest.getString("l_hanku_cd") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setHinsyuNa( rest.getString("hinsyu_na") );
		bean.setHinmokuCd( rest.getString("hinmoku_cd") );
		bean.setHinmokuNa( rest.getString("hinmoku_na") );
		bean.setHinmokuKa( rest.getString("hinmoku_ka") );
		bean.setColorKa( rest.getString("color_ka") );
		bean.setSizeKa( rest.getString("size_ka") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setTorihikisakiNa( rest.getString("torihikisaki_na") );
		bean.setMakerCd( rest.getString("maker_cd") );
		bean.setKetasuKb( rest.getString("ketasu_kb") );
		bean.setHachuTaniQt( rest.getLong("hachu_tani_qt") );
		bean.setIrisuQt( rest.getLong("irisu_qt") );
		bean.setTeikanKb( rest.getString("teikan_kb") );
		bean.setSyomikikanKb( rest.getString("syomikikan_kb") );
		bean.setSyomikikan( rest.getString("syomikikan") );
		bean.setOndotaiKb( rest.getString("ondotai_kb") );
		bean.setGentankaVl( rest.getLong("gentanka_vl") );
		bean.setSyukoGentankaVl( rest.getLong("syuko_gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setHanbaiBeginDt( rest.getString("hanbai_begin_dt") );
		bean.setHanbaiEndDt( rest.getString("hanbai_end_dt") );
		bean.setNefudaKb( rest.getString("nefuda_kb") );
		bean.setPcKb( rest.getString("pc_kb") );
		bean.setPcTaniKb( rest.getString("pc_tani_kb") );
		bean.setPcTaniSuryo( rest.getLong("pc_tani_suryo") );
		bean.setPcKijunTani( rest.getLong("pc_kijun_tani") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setHaisoKb( rest.getString("haiso_kb") );
		bean.setKbSobaKb( rest.getString("kb_soba_kb") );
		bean.setBinRt1( rest.getLong("bin_rt_1") );
		bean.setBinRt2( rest.getLong("bin_rt_2") );
		bean.setBinRt3( rest.getLong("bin_rt_3") );
		bean.setBinRt4( rest.getLong("bin_rt_4") );
		bean.setHanbaiKikanKb( rest.getString("hanbai_kikan_kb") );
		bean.setHachuTeisiKb( rest.getString("hachu_teisi_kb") );
		bean.setSiyofukaKb( rest.getString("siyofuka_kb") );
		bean.setInsertDt( rest.getString("insert_dt") );
		bean.setUpdateDt( rest.getString("update_dt") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setHibridTenhankuCd( rest.getString("hibrid_tenhanku_cd"));
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
		StringBuffer sb = new StringBuffer();
		String whereStr = "where ";
		String andStr = " and ";
		boolean orSyohinPosFg = false;
				
				
	
		sb.append("select ");
		sb.append("t1.SYOHIN_CD, ");
		sb.append("t1.BEGIN_DT, ");
		sb.append("t1.NOHIN_SYOHIN_CD, ");
		sb.append("t1.POS_CD, ");
		sb.append("t1.JISYA_SYOHIN_CD, ");
		sb.append("t1.SYOHIN_NA, ");
		sb.append("t1.SYOHIN_KA, ");
		sb.append("t1.KIKAKU_NA, ");
		sb.append("t1.KIKAKU_KA, ");
		sb.append("t1.L_HANKU_CD, ");
		sb.append("t1.HINSYU_CD, ");
		sb.append("t1.HINSYU_NA, ");
		sb.append("t1.HINMOKU_CD, ");
		sb.append("t1.HINMOKU_NA, ");
		sb.append("t1.HINMOKU_KA, ");
		sb.append("t1.COLOR_KA, ");
		sb.append("t1.SIZE_KA, ");
		sb.append("t1.TORIHIKISAKI_CD, ");
		sb.append("t1.TORIHIKISAKI_NA, ");
		sb.append("t1.MAKER_CD, ");
		sb.append("t1.KETASU_KB, ");
		sb.append("t1.HACHU_TANI_QT, ");
		sb.append("t1.IRISU_QT, ");
		sb.append("t1.TEIKAN_KB, ");
		sb.append("t1.SYOMIKIKAN_KB, ");
		sb.append("t1.SYOMIKIKAN, ");
		sb.append("t1.ONDOTAI_KB, ");
		sb.append("t1.GENTANKA_VL, ");
		sb.append("t1.SYUKO_GENTANKA_VL, ");
		sb.append("t1.BAITANKA_VL, ");
		sb.append("t1.HANBAI_BEGIN_DT, ");
		sb.append("t1.HANBAI_END_DT, ");
		sb.append("t1.NEFUDA_KB, ");
		sb.append("t1.PC_KB, ");
		sb.append("t1.PC_TANI_KB, ");
		sb.append("t1.PC_TANI_SURYO, ");
		sb.append("t1.PC_KIJUN_TANI, ");
		sb.append("t1.EOS_KB, ");
		sb.append("t1.HAISO_KB, ");
		sb.append("t1.KB_SOBA_KB, ");
		sb.append("t1.BIN_RT_1, ");
		sb.append("t1.BIN_RT_2, ");
		sb.append("t1.BIN_RT_3, ");
		sb.append("t1.BIN_RT_4, ");
		sb.append("t1.HANBAI_SEISAKU_KB, ");
		sb.append("t1.HANBAI_KIKAN_KB, ");
		sb.append("t1.HACHU_TEISI_KB, ");
		sb.append("t1.SIYOFUKA_KB, ");
		sb.append("t1.INSERT_DT, ");
		sb.append("t1.UPDATE_DT, ");
		sb.append("t1.RIYO_USER_ID, ");
		sb.append("t1.INSERT_TS, ");
		sb.append("t1.UPDATE_TS, ");
		sb.append("t2.TENHANKU_CD AS HIBRID_TENHANKU_CD ");
		sb.append("from ma_syohin t1, ma_hibrid t2, ");
		sb.append("(select ");
		sb.append("	syohin_cd, ");
		sb.append("	min(begin_dt) as begin_dt ");
		sb.append("from ");
		sb.append("	ma_syohin ");


		// syohin_cd に対するWHERE区
		// syohin_cdまたはpos_cd 条件追加 start
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			if( map.get("nohin_syohin_cd") != null && ((String)map.get("nohin_syohin_cd")).trim().length() > 0 )
			{
				if( map.get("pos_cd") != null && ((String)map.get("pos_cd")).trim().length() > 0 )
				{
					sb.append(whereStr);
					sb.append("(syohin_cd = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
					sb.append(" or nohin_syohin_cd = '" + conv.convertWhereString( (String)map.get("nohin_syohin_cd") ) + "'");
					sb.append(" or pos_cd = '" + conv.convertWhereString( (String)map.get("pos_cd") ) + "'");
					sb.append(")");
					whereStr = andStr;
					orSyohinPosFg = true;
				}
			}
		}
		if (orSyohinPosFg == false){
		// syohin_cdまたはpos_cd 条件追加 end
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
		// syohin_cdまたはpos_cd 条件追加 start
		}
		// syohin_cdまたはpos_cd 条件追加 end

		// begin_dt に対するWHERE区
		if( map.get("begin_dt_bef") != null && ((String)map.get("begin_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt >= '" + conv.convertWhereString( (String)map.get("begin_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("begin_dt_aft") != null && ((String)map.get("begin_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt <= '" + conv.convertWhereString( (String)map.get("begin_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("begin_dt") != null && ((String)map.get("begin_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt = '" + conv.convertWhereString( (String)map.get("begin_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("begin_dt_like") != null && ((String)map.get("begin_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt like '%" + conv.convertWhereString( (String)map.get("begin_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("begin_dt_bef_like") != null && ((String)map.get("begin_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt like '%" + conv.convertWhereString( (String)map.get("begin_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("begin_dt_aft_like") != null && ((String)map.get("begin_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt like '" + conv.convertWhereString( (String)map.get("begin_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("begin_dt_in") != null && ((String)map.get("begin_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("begin_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("begin_dt_not_in") != null && ((String)map.get("begin_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("begin_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("begin_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}

		if (orSyohinPosFg == false){
		// syohin_cdまたはpos_cd 条件追加 end
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
			// syohin_cdまたはpos_cd 条件追加 start
		}

		// syohin_cdまたはpos_cd 条件追加 start
		if (orSyohinPosFg == false){
		// syohin_cdまたはpos_cd 条件追加 end
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
		// syohin_cdまたはpos_cd 条件追加 start
		}
		// syohin_cdまたはpos_cd 条件追加 end

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


		// hinsyu_na に対するWHERE区
		if( map.get("hinsyu_na_bef") != null && ((String)map.get("hinsyu_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na >= '" + conv.convertWhereString( (String)map.get("hinsyu_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_aft") != null && ((String)map.get("hinsyu_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na <= '" + conv.convertWhereString( (String)map.get("hinsyu_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na") != null && ((String)map.get("hinsyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na = '" + conv.convertWhereString( (String)map.get("hinsyu_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_like") != null && ((String)map.get("hinsyu_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na like '%" + conv.convertWhereString( (String)map.get("hinsyu_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_bef_like") != null && ((String)map.get("hinsyu_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na like '%" + conv.convertWhereString( (String)map.get("hinsyu_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_aft_like") != null && ((String)map.get("hinsyu_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na like '" + conv.convertWhereString( (String)map.get("hinsyu_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_in") != null && ((String)map.get("hinsyu_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinsyu_na_not_in") != null && ((String)map.get("hinsyu_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinsyu_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinsyu_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinmoku_cd に対するWHERE区
		if( map.get("hinmoku_cd_bef") != null && ((String)map.get("hinmoku_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd >= '" + conv.convertWhereString( (String)map.get("hinmoku_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_aft") != null && ((String)map.get("hinmoku_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd <= '" + conv.convertWhereString( (String)map.get("hinmoku_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd") != null && ((String)map.get("hinmoku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd = '" + conv.convertWhereString( (String)map.get("hinmoku_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_like") != null && ((String)map.get("hinmoku_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd like '%" + conv.convertWhereString( (String)map.get("hinmoku_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_bef_like") != null && ((String)map.get("hinmoku_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd like '%" + conv.convertWhereString( (String)map.get("hinmoku_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_aft_like") != null && ((String)map.get("hinmoku_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd like '" + conv.convertWhereString( (String)map.get("hinmoku_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_in") != null && ((String)map.get("hinmoku_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinmoku_cd_not_in") != null && ((String)map.get("hinmoku_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinmoku_na に対するWHERE区
		if( map.get("hinmoku_na_bef") != null && ((String)map.get("hinmoku_na_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na >= '" + conv.convertWhereString( (String)map.get("hinmoku_na_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_aft") != null && ((String)map.get("hinmoku_na_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na <= '" + conv.convertWhereString( (String)map.get("hinmoku_na_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na") != null && ((String)map.get("hinmoku_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na = '" + conv.convertWhereString( (String)map.get("hinmoku_na") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_like") != null && ((String)map.get("hinmoku_na_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na like '%" + conv.convertWhereString( (String)map.get("hinmoku_na_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_bef_like") != null && ((String)map.get("hinmoku_na_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na like '%" + conv.convertWhereString( (String)map.get("hinmoku_na_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_aft_like") != null && ((String)map.get("hinmoku_na_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na like '" + conv.convertWhereString( (String)map.get("hinmoku_na_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_in") != null && ((String)map.get("hinmoku_na_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_na_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinmoku_na_not_in") != null && ((String)map.get("hinmoku_na_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_na not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_na_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hinmoku_ka に対するWHERE区
		if( map.get("hinmoku_ka_bef") != null && ((String)map.get("hinmoku_ka_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka >= '" + conv.convertWhereString( (String)map.get("hinmoku_ka_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_aft") != null && ((String)map.get("hinmoku_ka_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka <= '" + conv.convertWhereString( (String)map.get("hinmoku_ka_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka") != null && ((String)map.get("hinmoku_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka = '" + conv.convertWhereString( (String)map.get("hinmoku_ka") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_like") != null && ((String)map.get("hinmoku_ka_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka like '%" + conv.convertWhereString( (String)map.get("hinmoku_ka_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_bef_like") != null && ((String)map.get("hinmoku_ka_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka like '%" + conv.convertWhereString( (String)map.get("hinmoku_ka_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_aft_like") != null && ((String)map.get("hinmoku_ka_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka like '" + conv.convertWhereString( (String)map.get("hinmoku_ka_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_in") != null && ((String)map.get("hinmoku_ka_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_ka_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hinmoku_ka_not_in") != null && ((String)map.get("hinmoku_ka_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hinmoku_ka not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hinmoku_ka_not_in") ),",","','") + "' )");
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


		// maker_cd に対するWHERE区
		if( map.get("maker_cd_bef") != null && ((String)map.get("maker_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd >= '" + conv.convertWhereString( (String)map.get("maker_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("maker_cd_aft") != null && ((String)map.get("maker_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd <= '" + conv.convertWhereString( (String)map.get("maker_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("maker_cd") != null && ((String)map.get("maker_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd = '" + conv.convertWhereString( (String)map.get("maker_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("maker_cd_like") != null && ((String)map.get("maker_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd like '%" + conv.convertWhereString( (String)map.get("maker_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("maker_cd_bef_like") != null && ((String)map.get("maker_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd like '%" + conv.convertWhereString( (String)map.get("maker_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("maker_cd_aft_like") != null && ((String)map.get("maker_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd like '" + conv.convertWhereString( (String)map.get("maker_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("maker_cd_in") != null && ((String)map.get("maker_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("maker_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("maker_cd_not_in") != null && ((String)map.get("maker_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("maker_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("maker_cd_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ketasu_kb に対するWHERE区
		if( map.get("ketasu_kb_bef") != null && ((String)map.get("ketasu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb >= '" + conv.convertWhereString( (String)map.get("ketasu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_aft") != null && ((String)map.get("ketasu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb <= '" + conv.convertWhereString( (String)map.get("ketasu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb") != null && ((String)map.get("ketasu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb = '" + conv.convertWhereString( (String)map.get("ketasu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_like") != null && ((String)map.get("ketasu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb like '%" + conv.convertWhereString( (String)map.get("ketasu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_bef_like") != null && ((String)map.get("ketasu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb like '%" + conv.convertWhereString( (String)map.get("ketasu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_aft_like") != null && ((String)map.get("ketasu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb like '" + conv.convertWhereString( (String)map.get("ketasu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_in") != null && ((String)map.get("ketasu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ketasu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ketasu_kb_not_in") != null && ((String)map.get("ketasu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ketasu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ketasu_kb_not_in") ),",","','") + "' )");
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


		// syomikikan_kb に対するWHERE区
		if( map.get("syomikikan_kb_bef") != null && ((String)map.get("syomikikan_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb >= '" + conv.convertWhereString( (String)map.get("syomikikan_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_aft") != null && ((String)map.get("syomikikan_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb <= '" + conv.convertWhereString( (String)map.get("syomikikan_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb") != null && ((String)map.get("syomikikan_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb = '" + conv.convertWhereString( (String)map.get("syomikikan_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_like") != null && ((String)map.get("syomikikan_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb like '%" + conv.convertWhereString( (String)map.get("syomikikan_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_bef_like") != null && ((String)map.get("syomikikan_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb like '%" + conv.convertWhereString( (String)map.get("syomikikan_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_aft_like") != null && ((String)map.get("syomikikan_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb like '" + conv.convertWhereString( (String)map.get("syomikikan_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_in") != null && ((String)map.get("syomikikan_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syomikikan_kb_not_in") != null && ((String)map.get("syomikikan_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// syomikikan に対するWHERE区
		if( map.get("syomikikan_bef") != null && ((String)map.get("syomikikan_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan >= '" + conv.convertWhereString( (String)map.get("syomikikan_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_aft") != null && ((String)map.get("syomikikan_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan <= '" + conv.convertWhereString( (String)map.get("syomikikan_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan") != null && ((String)map.get("syomikikan")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan = '" + conv.convertWhereString( (String)map.get("syomikikan") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_like") != null && ((String)map.get("syomikikan_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan like '%" + conv.convertWhereString( (String)map.get("syomikikan_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_bef_like") != null && ((String)map.get("syomikikan_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan like '%" + conv.convertWhereString( (String)map.get("syomikikan_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_aft_like") != null && ((String)map.get("syomikikan_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan like '" + conv.convertWhereString( (String)map.get("syomikikan_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("syomikikan_in") != null && ((String)map.get("syomikikan_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("syomikikan_not_in") != null && ((String)map.get("syomikikan_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syomikikan not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("syomikikan_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// ondotai_kb に対するWHERE区
		if( map.get("ondotai_kb_bef") != null && ((String)map.get("ondotai_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb >= '" + conv.convertWhereString( (String)map.get("ondotai_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_aft") != null && ((String)map.get("ondotai_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb <= '" + conv.convertWhereString( (String)map.get("ondotai_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb") != null && ((String)map.get("ondotai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb = '" + conv.convertWhereString( (String)map.get("ondotai_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_like") != null && ((String)map.get("ondotai_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb like '%" + conv.convertWhereString( (String)map.get("ondotai_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_bef_like") != null && ((String)map.get("ondotai_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb like '%" + conv.convertWhereString( (String)map.get("ondotai_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_aft_like") != null && ((String)map.get("ondotai_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb like '" + conv.convertWhereString( (String)map.get("ondotai_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_in") != null && ((String)map.get("ondotai_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ondotai_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("ondotai_kb_not_in") != null && ((String)map.get("ondotai_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("ondotai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("ondotai_kb_not_in") ),",","','") + "' )");
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


		// syuko_gentanka_vl に対するWHERE区
		if( map.get("syuko_gentanka_vl_bef") != null && ((String)map.get("syuko_gentanka_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_gentanka_vl >= " + (String)map.get("syuko_gentanka_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("syuko_gentanka_vl_aft") != null && ((String)map.get("syuko_gentanka_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_gentanka_vl <= " + (String)map.get("syuko_gentanka_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("syuko_gentanka_vl") != null && ((String)map.get("syuko_gentanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("syuko_gentanka_vl = " + (String)map.get("syuko_gentanka_vl"));
			whereStr = andStr;
		}
		if( map.get("syuko_gentanka_vl_in") != null && ((String)map.get("syuko_gentanka_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_gentanka_vl in ( " + conv.convertWhereString( (String)map.get("syuko_gentanka_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("syuko_gentanka_vl_not_in") != null && ((String)map.get("syuko_gentanka_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syuko_gentanka_vl not in ( " + conv.convertWhereString( (String)map.get("syuko_gentanka_vl_not_in") ) + " )");
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


		// hanbai_begin_dt に対するWHERE区
		if( map.get("hanbai_begin_dt_bef") != null && ((String)map.get("hanbai_begin_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_begin_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_aft") != null && ((String)map.get("hanbai_begin_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt <= '" + conv.convertWhereString( (String)map.get("hanbai_begin_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt") != null && ((String)map.get("hanbai_begin_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt = '" + conv.convertWhereString( (String)map.get("hanbai_begin_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_like") != null && ((String)map.get("hanbai_begin_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_begin_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_bef_like") != null && ((String)map.get("hanbai_begin_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_begin_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_aft_like") != null && ((String)map.get("hanbai_begin_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt like '" + conv.convertWhereString( (String)map.get("hanbai_begin_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_in") != null && ((String)map.get("hanbai_begin_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_begin_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_begin_dt_not_in") != null && ((String)map.get("hanbai_begin_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_begin_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_begin_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hanbai_end_dt に対するWHERE区
		if( map.get("hanbai_end_dt_bef") != null && ((String)map.get("hanbai_end_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt >= '" + conv.convertWhereString( (String)map.get("hanbai_end_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_aft") != null && ((String)map.get("hanbai_end_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt <= '" + conv.convertWhereString( (String)map.get("hanbai_end_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt") != null && ((String)map.get("hanbai_end_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt = '" + conv.convertWhereString( (String)map.get("hanbai_end_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_like") != null && ((String)map.get("hanbai_end_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_end_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_bef_like") != null && ((String)map.get("hanbai_end_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt like '%" + conv.convertWhereString( (String)map.get("hanbai_end_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_aft_like") != null && ((String)map.get("hanbai_end_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt like '" + conv.convertWhereString( (String)map.get("hanbai_end_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_in") != null && ((String)map.get("hanbai_end_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_end_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_end_dt_not_in") != null && ((String)map.get("hanbai_end_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_end_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_end_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// nefuda_kb に対するWHERE区
		if( map.get("nefuda_kb_bef") != null && ((String)map.get("nefuda_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb >= '" + conv.convertWhereString( (String)map.get("nefuda_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_aft") != null && ((String)map.get("nefuda_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb <= '" + conv.convertWhereString( (String)map.get("nefuda_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb") != null && ((String)map.get("nefuda_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb = '" + conv.convertWhereString( (String)map.get("nefuda_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_like") != null && ((String)map.get("nefuda_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb like '%" + conv.convertWhereString( (String)map.get("nefuda_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_bef_like") != null && ((String)map.get("nefuda_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb like '%" + conv.convertWhereString( (String)map.get("nefuda_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_aft_like") != null && ((String)map.get("nefuda_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb like '" + conv.convertWhereString( (String)map.get("nefuda_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_in") != null && ((String)map.get("nefuda_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nefuda_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("nefuda_kb_not_in") != null && ((String)map.get("nefuda_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nefuda_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("nefuda_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pc_kb に対するWHERE区
		if( map.get("pc_kb_bef") != null && ((String)map.get("pc_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb >= '" + conv.convertWhereString( (String)map.get("pc_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_kb_aft") != null && ((String)map.get("pc_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb <= '" + conv.convertWhereString( (String)map.get("pc_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_kb") != null && ((String)map.get("pc_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb = '" + conv.convertWhereString( (String)map.get("pc_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_kb_like") != null && ((String)map.get("pc_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb like '%" + conv.convertWhereString( (String)map.get("pc_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pc_kb_bef_like") != null && ((String)map.get("pc_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb like '%" + conv.convertWhereString( (String)map.get("pc_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_kb_aft_like") != null && ((String)map.get("pc_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb like '" + conv.convertWhereString( (String)map.get("pc_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pc_kb_in") != null && ((String)map.get("pc_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pc_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pc_kb_not_in") != null && ((String)map.get("pc_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pc_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pc_tani_kb に対するWHERE区
		if( map.get("pc_tani_kb_bef") != null && ((String)map.get("pc_tani_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb >= '" + conv.convertWhereString( (String)map.get("pc_tani_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_aft") != null && ((String)map.get("pc_tani_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb <= '" + conv.convertWhereString( (String)map.get("pc_tani_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb") != null && ((String)map.get("pc_tani_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb = '" + conv.convertWhereString( (String)map.get("pc_tani_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_like") != null && ((String)map.get("pc_tani_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb like '%" + conv.convertWhereString( (String)map.get("pc_tani_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_bef_like") != null && ((String)map.get("pc_tani_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb like '%" + conv.convertWhereString( (String)map.get("pc_tani_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_aft_like") != null && ((String)map.get("pc_tani_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb like '" + conv.convertWhereString( (String)map.get("pc_tani_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_in") != null && ((String)map.get("pc_tani_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pc_tani_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("pc_tani_kb_not_in") != null && ((String)map.get("pc_tani_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("pc_tani_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// pc_tani_suryo に対するWHERE区
		if( map.get("pc_tani_suryo_bef") != null && ((String)map.get("pc_tani_suryo_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_suryo >= " + (String)map.get("pc_tani_suryo_bef") );
			whereStr = andStr;
		}
		if( map.get("pc_tani_suryo_aft") != null && ((String)map.get("pc_tani_suryo_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_suryo <= " + (String)map.get("pc_tani_suryo_aft") );
			whereStr = andStr;
		}
		if( map.get("pc_tani_suryo") != null && ((String)map.get("pc_tani_suryo")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("pc_tani_suryo = " + (String)map.get("pc_tani_suryo"));
			whereStr = andStr;
		}
		if( map.get("pc_tani_suryo_in") != null && ((String)map.get("pc_tani_suryo_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_suryo in ( " + conv.convertWhereString( (String)map.get("pc_tani_suryo_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("pc_tani_suryo_not_in") != null && ((String)map.get("pc_tani_suryo_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_tani_suryo not in ( " + conv.convertWhereString( (String)map.get("pc_tani_suryo_not_in") ) + " )");
			whereStr = andStr;
		}


		// pc_kijun_tani に対するWHERE区
		if( map.get("pc_kijun_tani_bef") != null && ((String)map.get("pc_kijun_tani_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kijun_tani >= " + (String)map.get("pc_kijun_tani_bef") );
			whereStr = andStr;
		}
		if( map.get("pc_kijun_tani_aft") != null && ((String)map.get("pc_kijun_tani_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kijun_tani <= " + (String)map.get("pc_kijun_tani_aft") );
			whereStr = andStr;
		}
		if( map.get("pc_kijun_tani") != null && ((String)map.get("pc_kijun_tani")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("pc_kijun_tani = " + (String)map.get("pc_kijun_tani"));
			whereStr = andStr;
		}
		if( map.get("pc_kijun_tani_in") != null && ((String)map.get("pc_kijun_tani_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kijun_tani in ( " + conv.convertWhereString( (String)map.get("pc_kijun_tani_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("pc_kijun_tani_not_in") != null && ((String)map.get("pc_kijun_tani_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_kijun_tani not in ( " + conv.convertWhereString( (String)map.get("pc_kijun_tani_not_in") ) + " )");
			whereStr = andStr;
		}


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


		// bin_rt_1 に対するWHERE区
		if( map.get("bin_rt_1_bef") != null && ((String)map.get("bin_rt_1_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_1 >= " + (String)map.get("bin_rt_1_bef") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_1_aft") != null && ((String)map.get("bin_rt_1_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_1 <= " + (String)map.get("bin_rt_1_aft") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_1") != null && ((String)map.get("bin_rt_1")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("bin_rt_1 = " + (String)map.get("bin_rt_1"));
			whereStr = andStr;
		}
		if( map.get("bin_rt_1_in") != null && ((String)map.get("bin_rt_1_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_1 in ( " + conv.convertWhereString( (String)map.get("bin_rt_1_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("bin_rt_1_not_in") != null && ((String)map.get("bin_rt_1_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_1 not in ( " + conv.convertWhereString( (String)map.get("bin_rt_1_not_in") ) + " )");
			whereStr = andStr;
		}


		// bin_rt_2 に対するWHERE区
		if( map.get("bin_rt_2_bef") != null && ((String)map.get("bin_rt_2_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_2 >= " + (String)map.get("bin_rt_2_bef") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_2_aft") != null && ((String)map.get("bin_rt_2_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_2 <= " + (String)map.get("bin_rt_2_aft") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_2") != null && ((String)map.get("bin_rt_2")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("bin_rt_2 = " + (String)map.get("bin_rt_2"));
			whereStr = andStr;
		}
		if( map.get("bin_rt_2_in") != null && ((String)map.get("bin_rt_2_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_2 in ( " + conv.convertWhereString( (String)map.get("bin_rt_2_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("bin_rt_2_not_in") != null && ((String)map.get("bin_rt_2_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_2 not in ( " + conv.convertWhereString( (String)map.get("bin_rt_2_not_in") ) + " )");
			whereStr = andStr;
		}


		// bin_rt_3 に対するWHERE区
		if( map.get("bin_rt_3_bef") != null && ((String)map.get("bin_rt_3_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_3 >= " + (String)map.get("bin_rt_3_bef") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_3_aft") != null && ((String)map.get("bin_rt_3_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_3 <= " + (String)map.get("bin_rt_3_aft") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_3") != null && ((String)map.get("bin_rt_3")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("bin_rt_3 = " + (String)map.get("bin_rt_3"));
			whereStr = andStr;
		}
		if( map.get("bin_rt_3_in") != null && ((String)map.get("bin_rt_3_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_3 in ( " + conv.convertWhereString( (String)map.get("bin_rt_3_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("bin_rt_3_not_in") != null && ((String)map.get("bin_rt_3_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_3 not in ( " + conv.convertWhereString( (String)map.get("bin_rt_3_not_in") ) + " )");
			whereStr = andStr;
		}


		// bin_rt_4 に対するWHERE区
		if( map.get("bin_rt_4_bef") != null && ((String)map.get("bin_rt_4_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_4 >= " + (String)map.get("bin_rt_4_bef") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_4_aft") != null && ((String)map.get("bin_rt_4_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_4 <= " + (String)map.get("bin_rt_4_aft") );
			whereStr = andStr;
		}
		if( map.get("bin_rt_4") != null && ((String)map.get("bin_rt_4")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("bin_rt_4 = " + (String)map.get("bin_rt_4"));
			whereStr = andStr;
		}
		if( map.get("bin_rt_4_in") != null && ((String)map.get("bin_rt_4_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_4 in ( " + conv.convertWhereString( (String)map.get("bin_rt_4_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("bin_rt_4_not_in") != null && ((String)map.get("bin_rt_4_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bin_rt_4 not in ( " + conv.convertWhereString( (String)map.get("bin_rt_4_not_in") ) + " )");
			whereStr = andStr;
		}


		// hanbai_kikan_kb に対するWHERE区
		if( map.get("hanbai_kikan_kb_bef") != null && ((String)map.get("hanbai_kikan_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb >= '" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_aft") != null && ((String)map.get("hanbai_kikan_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb <= '" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb") != null && ((String)map.get("hanbai_kikan_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb = '" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_like") != null && ((String)map.get("hanbai_kikan_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_bef_like") != null && ((String)map.get("hanbai_kikan_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb like '%" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_aft_like") != null && ((String)map.get("hanbai_kikan_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb like '" + conv.convertWhereString( (String)map.get("hanbai_kikan_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_in") != null && ((String)map.get("hanbai_kikan_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_kikan_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hanbai_kikan_kb_not_in") != null && ((String)map.get("hanbai_kikan_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hanbai_kikan_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hanbai_kikan_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// hachu_teisi_kb に対するWHERE区
		if( map.get("hachu_teisi_kb_bef") != null && ((String)map.get("hachu_teisi_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb >= '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_aft") != null && ((String)map.get("hachu_teisi_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb <= '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb") != null && ((String)map.get("hachu_teisi_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb = '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_like") != null && ((String)map.get("hachu_teisi_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb like '%" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_bef_like") != null && ((String)map.get("hachu_teisi_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb like '%" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_aft_like") != null && ((String)map.get("hachu_teisi_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb like '" + conv.convertWhereString( (String)map.get("hachu_teisi_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_in") != null && ((String)map.get("hachu_teisi_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_teisi_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_teisi_kb_not_in") != null && ((String)map.get("hachu_teisi_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_teisi_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_teisi_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// siyofuka_kb に対するWHERE区
		if( map.get("siyofuka_kb_bef") != null && ((String)map.get("siyofuka_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb >= '" + conv.convertWhereString( (String)map.get("siyofuka_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_aft") != null && ((String)map.get("siyofuka_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb <= '" + conv.convertWhereString( (String)map.get("siyofuka_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb") != null && ((String)map.get("siyofuka_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb = '" + conv.convertWhereString( (String)map.get("siyofuka_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_like") != null && ((String)map.get("siyofuka_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb like '%" + conv.convertWhereString( (String)map.get("siyofuka_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_bef_like") != null && ((String)map.get("siyofuka_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb like '%" + conv.convertWhereString( (String)map.get("siyofuka_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_aft_like") != null && ((String)map.get("siyofuka_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb like '" + conv.convertWhereString( (String)map.get("siyofuka_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_in") != null && ((String)map.get("siyofuka_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siyofuka_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("siyofuka_kb_not_in") != null && ((String)map.get("siyofuka_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("siyofuka_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("siyofuka_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// insert_dt に対するWHERE区
		if( map.get("insert_dt_bef") != null && ((String)map.get("insert_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt >= '" + conv.convertWhereString( (String)map.get("insert_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_dt_aft") != null && ((String)map.get("insert_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt <= '" + conv.convertWhereString( (String)map.get("insert_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_dt") != null && ((String)map.get("insert_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt = '" + conv.convertWhereString( (String)map.get("insert_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_dt_like") != null && ((String)map.get("insert_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt like '%" + conv.convertWhereString( (String)map.get("insert_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_dt_bef_like") != null && ((String)map.get("insert_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt like '%" + conv.convertWhereString( (String)map.get("insert_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("insert_dt_aft_like") != null && ((String)map.get("insert_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt like '" + conv.convertWhereString( (String)map.get("insert_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("insert_dt_in") != null && ((String)map.get("insert_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("insert_dt_not_in") != null && ((String)map.get("insert_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("insert_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// update_dt に対するWHERE区
		if( map.get("update_dt_bef") != null && ((String)map.get("update_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt >= '" + conv.convertWhereString( (String)map.get("update_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_aft") != null && ((String)map.get("update_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt <= '" + conv.convertWhereString( (String)map.get("update_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt") != null && ((String)map.get("update_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt = '" + conv.convertWhereString( (String)map.get("update_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_like") != null && ((String)map.get("update_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '%" + conv.convertWhereString( (String)map.get("update_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_dt_bef_like") != null && ((String)map.get("update_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '%" + conv.convertWhereString( (String)map.get("update_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("update_dt_aft_like") != null && ((String)map.get("update_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt like '" + conv.convertWhereString( (String)map.get("update_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("update_dt_in") != null && ((String)map.get("update_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("update_dt_not_in") != null && ((String)map.get("update_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("update_dt_not_in") ),",","','") + "' )");
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
		}

		sb.append(" group by syohin_cd) t3");
		sb.append(" where t1.syohin_cd=t3.syohin_cd");
		sb.append(" and t1.begin_dt=t3.begin_dt");
		sb.append(" and t1.hinsyu_cd=t2.hinsyu_cd");
		sb.append(" and t2.tenpo_cd='" +  map.get("tenpo_cd") + "'");
		sb.append(" and t2.uriku_cd='" +  map.get("uriku_cd") + "'");
		sb.append(" order by hibrid_tenhanku_cd");
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
