package mdware.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2004.07.12) Version 1.0.rbsite
 * @version X.X (Create time: 2004/8/13 19:0:45)
 */
public class DtFreshHachuDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public DtFreshHachuDM()
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
		DtFreshHachuBean bean = new DtFreshHachuBean();
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenhankuCd( rest.getString("tenhanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setStdTorihikisakiCd( rest.getString("std_torihikisaki_cd") );
		bean.setSantiCd( rest.getString("santi_cd") );
		bean.setTokaikyuCd( rest.getString("tokaikyu_cd") );
		bean.setKikakuCd( rest.getString("kikaku_cd") );
		bean.setKakuteiKb( rest.getString("kakutei_kb") );
		bean.setKakuteiSeq( rest.getLong("kakutei_seq") );
		bean.setNohinSyohinCd( rest.getString("nohin_syohin_cd") );
		bean.setPosCd( rest.getString("pos_cd") );
		bean.setJisyaSyohinCd( rest.getString("jisya_syohin_cd") );
		bean.setSyohinNa( rest.getString("syohin_na") );
		bean.setSyohinKa( rest.getString("syohin_ka") );
		bean.setHachuTaniNa( rest.getString("hachu_tani_na") );
		bean.setHachuTaniKa( rest.getString("hachu_tani_ka") );
		bean.setSantiNa( rest.getString("santi_na") );
		bean.setTokaikyuNa( rest.getString("tokaikyu_na") );
		bean.setKikakuNa( rest.getString("kikaku_na") );
		bean.setTorihikisakiNa( rest.getString("torihikisaki_na") );
		bean.setTorihikisakiKa( rest.getString("torihikisaki_ka") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setHinmokuCd( rest.getString("hinmoku_cd") );
		bean.setIrisuQt( rest.getDouble("irisu_qt") );
		bean.setHachuTaniQt( rest.getLong("hachu_tani_qt") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setKbSobaKb( rest.getString("kb_soba_kb") );
		bean.setTeikanKb( rest.getString("teikan_kb") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setHachuPatternKb( rest.getString("hachu_pattern_kb") );
		bean.setSimejikanTm( rest.getString("simejikan_tm") );
		bean.setZenjituTeiHanQt( rest.getDouble("zenjitu_tei_han_qt") );
		bean.setZenjituTeiHanVl( rest.getLong("zenjitu_tei_han_vl") );
		bean.setZensyuTeiHanQt( rest.getDouble("zensyu_tei_han_qt") );
		bean.setZensyuTeiHanVl( rest.getLong("zensyu_tei_han_vl") );
		bean.setZenjituTokuHanQt( rest.getDouble("zenjitu_toku_han_qt") );
		bean.setZenjituTokuHanVl( rest.getLong("zenjitu_toku_han_vl") );
		bean.setZensyuTokuHanQt( rest.getDouble("zensyu_toku_han_qt") );
		bean.setZensyuTokuHanVl( rest.getLong("zensyu_toku_han_vl") );
		bean.setBaihenSuryoQt( rest.getDouble("baihen_suryo_qt") );
		bean.setBaihenSuryoWorst( rest.getLong("baihen_suryo_worst") );
		bean.setBaihenVl( rest.getLong("baihen_vl") );
		bean.setBaihenVlWorst( rest.getLong("baihen_vl_worst") );
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
		sb.append("tenpo_cd ");
		sb.append(", ");
		sb.append("tenhanku_cd ");
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("std_torihikisaki_cd ");
		sb.append(", ");
		sb.append("santi_cd ");
		sb.append(", ");
		sb.append("tokaikyu_cd ");
		sb.append(", ");
		sb.append("kikaku_cd ");
		sb.append(", ");
		sb.append("kakutei_kb ");
		sb.append(", ");
		sb.append("kakutei_seq ");
		sb.append(", ");
		sb.append("nohin_syohin_cd ");
		sb.append(", ");
		sb.append("pos_cd ");
		sb.append(", ");
		sb.append("jisya_syohin_cd ");
		sb.append(", ");
		sb.append("syohin_na ");
		sb.append(", ");
		sb.append("syohin_ka ");
		sb.append(", ");
		sb.append("hachu_tani_na ");
		sb.append(", ");
		sb.append("hachu_tani_ka ");
		sb.append(", ");
		sb.append("santi_na ");
		sb.append(", ");
		sb.append("tokaikyu_na ");
		sb.append(", ");
		sb.append("kikaku_na ");
		sb.append(", ");
		sb.append("torihikisaki_na ");
		sb.append(", ");
		sb.append("torihikisaki_ka ");
		sb.append(", ");
		sb.append("hinsyu_cd ");
		sb.append(", ");
		sb.append("hinmoku_cd ");
		sb.append(", ");
		sb.append("irisu_qt ");
		sb.append(", ");
		sb.append("hachu_tani_qt ");
		sb.append(", ");
		sb.append("gentanka_vl ");
		sb.append(", ");
		sb.append("baitanka_vl ");
		sb.append(", ");
		sb.append("kb_soba_kb ");
		sb.append(", ");
		sb.append("teikan_kb ");
		sb.append(", ");
		sb.append("eos_kb ");
		sb.append(", ");
		sb.append("hachu_pattern_kb ");
		sb.append(", ");
		sb.append("simejikan_tm ");
		sb.append(", ");
		sb.append("zenjitu_tei_han_qt ");
		sb.append(", ");
		sb.append("zenjitu_tei_han_vl ");
		sb.append(", ");
		sb.append("zensyu_tei_han_qt ");
		sb.append(", ");
		sb.append("zensyu_tei_han_vl ");
		sb.append(", ");
		sb.append("zenjitu_toku_han_qt ");
		sb.append(", ");
		sb.append("zenjitu_toku_han_vl ");
		sb.append(", ");
		sb.append("zensyu_toku_han_qt ");
		sb.append(", ");
		sb.append("zensyu_toku_han_vl ");
		sb.append(", ");
		sb.append("baihen_suryo_qt ");
		sb.append(", ");
		sb.append("baihen_suryo_worst ");
		sb.append(", ");
		sb.append("baihen_vl ");
		sb.append(", ");
		sb.append("baihen_vl_worst ");
		sb.append(", ");
		sb.append("riyo_user_id ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append("from Dt_Fresh_Hachu ");


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


		// std_torihikisaki_cd に対するWHERE区
		if( map.get("std_torihikisaki_cd_bef") != null && ((String)map.get("std_torihikisaki_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd >= '" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_aft") != null && ((String)map.get("std_torihikisaki_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd <= '" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd") != null && ((String)map.get("std_torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd = '" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_like") != null && ((String)map.get("std_torihikisaki_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd like '%" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_bef_like") != null && ((String)map.get("std_torihikisaki_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd like '%" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_aft_like") != null && ((String)map.get("std_torihikisaki_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd like '" + conv.convertWhereString( (String)map.get("std_torihikisaki_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_in") != null && ((String)map.get("std_torihikisaki_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("std_torihikisaki_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("std_torihikisaki_cd_not_in") != null && ((String)map.get("std_torihikisaki_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("std_torihikisaki_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("std_torihikisaki_cd_not_in") ),",","','") + "' )");
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


		// kakutei_kb に対するWHERE区
		if( map.get("kakutei_kb_bef") != null && ((String)map.get("kakutei_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb >= '" + conv.convertWhereString( (String)map.get("kakutei_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_aft") != null && ((String)map.get("kakutei_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb <= '" + conv.convertWhereString( (String)map.get("kakutei_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb") != null && ((String)map.get("kakutei_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb = '" + conv.convertWhereString( (String)map.get("kakutei_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_like") != null && ((String)map.get("kakutei_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb like '%" + conv.convertWhereString( (String)map.get("kakutei_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_bef_like") != null && ((String)map.get("kakutei_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb like '%" + conv.convertWhereString( (String)map.get("kakutei_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_aft_like") != null && ((String)map.get("kakutei_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb like '" + conv.convertWhereString( (String)map.get("kakutei_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_in") != null && ((String)map.get("kakutei_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kakutei_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("kakutei_kb_not_in") != null && ((String)map.get("kakutei_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("kakutei_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// kakutei_seq に対するWHERE区
		if( map.get("kakutei_seq_bef") != null && ((String)map.get("kakutei_seq_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_seq >= " + (String)map.get("kakutei_seq_bef") );
			whereStr = andStr;
		}
		if( map.get("kakutei_seq_aft") != null && ((String)map.get("kakutei_seq_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_seq <= " + (String)map.get("kakutei_seq_aft") );
			whereStr = andStr;
		}
		if( map.get("kakutei_seq") != null && ((String)map.get("kakutei_seq")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("kakutei_seq = " + (String)map.get("kakutei_seq"));
			whereStr = andStr;
		}
		if( map.get("kakutei_seq_in") != null && ((String)map.get("kakutei_seq_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_seq in ( " + conv.convertWhereString( (String)map.get("kakutei_seq_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("kakutei_seq_not_in") != null && ((String)map.get("kakutei_seq_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kakutei_seq not in ( " + conv.convertWhereString( (String)map.get("kakutei_seq_not_in") ) + " )");
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


		// hachu_pattern_kb に対するWHERE区
		if( map.get("hachu_pattern_kb_bef") != null && ((String)map.get("hachu_pattern_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb >= '" + conv.convertWhereString( (String)map.get("hachu_pattern_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_aft") != null && ((String)map.get("hachu_pattern_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb <= '" + conv.convertWhereString( (String)map.get("hachu_pattern_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb") != null && ((String)map.get("hachu_pattern_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb = '" + conv.convertWhereString( (String)map.get("hachu_pattern_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_like") != null && ((String)map.get("hachu_pattern_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_bef_like") != null && ((String)map.get("hachu_pattern_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb like '%" + conv.convertWhereString( (String)map.get("hachu_pattern_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_aft_like") != null && ((String)map.get("hachu_pattern_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb like '" + conv.convertWhereString( (String)map.get("hachu_pattern_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_in") != null && ((String)map.get("hachu_pattern_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("hachu_pattern_kb_not_in") != null && ((String)map.get("hachu_pattern_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_pattern_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("hachu_pattern_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// simejikan_tm に対するWHERE区
		if( map.get("simejikan_tm_bef") != null && ((String)map.get("simejikan_tm_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm >= '" + conv.convertWhereString( (String)map.get("simejikan_tm_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_aft") != null && ((String)map.get("simejikan_tm_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm <= '" + conv.convertWhereString( (String)map.get("simejikan_tm_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm") != null && ((String)map.get("simejikan_tm")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm = '" + conv.convertWhereString( (String)map.get("simejikan_tm") ) + "'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_like") != null && ((String)map.get("simejikan_tm_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm like '%" + conv.convertWhereString( (String)map.get("simejikan_tm_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_bef_like") != null && ((String)map.get("simejikan_tm_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm like '%" + conv.convertWhereString( (String)map.get("simejikan_tm_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_aft_like") != null && ((String)map.get("simejikan_tm_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm like '" + conv.convertWhereString( (String)map.get("simejikan_tm_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_in") != null && ((String)map.get("simejikan_tm_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm in ( '" + replaceAll(conv.convertWhereString( (String)map.get("simejikan_tm_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("simejikan_tm_not_in") != null && ((String)map.get("simejikan_tm_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("simejikan_tm not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("simejikan_tm_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// zenjitu_tei_han_qt に対するWHERE区
		if( map.get("zenjitu_tei_han_qt_bef") != null && ((String)map.get("zenjitu_tei_han_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_qt >= " + (String)map.get("zenjitu_tei_han_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_qt_aft") != null && ((String)map.get("zenjitu_tei_han_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_qt <= " + (String)map.get("zenjitu_tei_han_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_qt") != null && ((String)map.get("zenjitu_tei_han_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_qt = " + (String)map.get("zenjitu_tei_han_qt"));
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_qt_in") != null && ((String)map.get("zenjitu_tei_han_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_qt in ( " + conv.convertWhereString( (String)map.get("zenjitu_tei_han_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_qt_not_in") != null && ((String)map.get("zenjitu_tei_han_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_qt not in ( " + conv.convertWhereString( (String)map.get("zenjitu_tei_han_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// zenjitu_tei_han_vl に対するWHERE区
		if( map.get("zenjitu_tei_han_vl_bef") != null && ((String)map.get("zenjitu_tei_han_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_vl >= " + (String)map.get("zenjitu_tei_han_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_vl_aft") != null && ((String)map.get("zenjitu_tei_han_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_vl <= " + (String)map.get("zenjitu_tei_han_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_vl") != null && ((String)map.get("zenjitu_tei_han_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_vl = " + (String)map.get("zenjitu_tei_han_vl"));
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_vl_in") != null && ((String)map.get("zenjitu_tei_han_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_vl in ( " + conv.convertWhereString( (String)map.get("zenjitu_tei_han_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zenjitu_tei_han_vl_not_in") != null && ((String)map.get("zenjitu_tei_han_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_tei_han_vl not in ( " + conv.convertWhereString( (String)map.get("zenjitu_tei_han_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// zensyu_tei_han_qt に対するWHERE区
		if( map.get("zensyu_tei_han_qt_bef") != null && ((String)map.get("zensyu_tei_han_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_qt >= " + (String)map.get("zensyu_tei_han_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_qt_aft") != null && ((String)map.get("zensyu_tei_han_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_qt <= " + (String)map.get("zensyu_tei_han_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_qt") != null && ((String)map.get("zensyu_tei_han_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_qt = " + (String)map.get("zensyu_tei_han_qt"));
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_qt_in") != null && ((String)map.get("zensyu_tei_han_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_qt in ( " + conv.convertWhereString( (String)map.get("zensyu_tei_han_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_qt_not_in") != null && ((String)map.get("zensyu_tei_han_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_qt not in ( " + conv.convertWhereString( (String)map.get("zensyu_tei_han_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// zensyu_tei_han_vl に対するWHERE区
		if( map.get("zensyu_tei_han_vl_bef") != null && ((String)map.get("zensyu_tei_han_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_vl >= " + (String)map.get("zensyu_tei_han_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_vl_aft") != null && ((String)map.get("zensyu_tei_han_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_vl <= " + (String)map.get("zensyu_tei_han_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_vl") != null && ((String)map.get("zensyu_tei_han_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_vl = " + (String)map.get("zensyu_tei_han_vl"));
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_vl_in") != null && ((String)map.get("zensyu_tei_han_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_vl in ( " + conv.convertWhereString( (String)map.get("zensyu_tei_han_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zensyu_tei_han_vl_not_in") != null && ((String)map.get("zensyu_tei_han_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_tei_han_vl not in ( " + conv.convertWhereString( (String)map.get("zensyu_tei_han_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// zenjitu_toku_han_qt に対するWHERE区
		if( map.get("zenjitu_toku_han_qt_bef") != null && ((String)map.get("zenjitu_toku_han_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_qt >= " + (String)map.get("zenjitu_toku_han_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_qt_aft") != null && ((String)map.get("zenjitu_toku_han_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_qt <= " + (String)map.get("zenjitu_toku_han_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_qt") != null && ((String)map.get("zenjitu_toku_han_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_qt = " + (String)map.get("zenjitu_toku_han_qt"));
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_qt_in") != null && ((String)map.get("zenjitu_toku_han_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_qt in ( " + conv.convertWhereString( (String)map.get("zenjitu_toku_han_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_qt_not_in") != null && ((String)map.get("zenjitu_toku_han_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_qt not in ( " + conv.convertWhereString( (String)map.get("zenjitu_toku_han_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// zenjitu_toku_han_vl に対するWHERE区
		if( map.get("zenjitu_toku_han_vl_bef") != null && ((String)map.get("zenjitu_toku_han_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_vl >= " + (String)map.get("zenjitu_toku_han_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_vl_aft") != null && ((String)map.get("zenjitu_toku_han_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_vl <= " + (String)map.get("zenjitu_toku_han_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_vl") != null && ((String)map.get("zenjitu_toku_han_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_vl = " + (String)map.get("zenjitu_toku_han_vl"));
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_vl_in") != null && ((String)map.get("zenjitu_toku_han_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_vl in ( " + conv.convertWhereString( (String)map.get("zenjitu_toku_han_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zenjitu_toku_han_vl_not_in") != null && ((String)map.get("zenjitu_toku_han_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zenjitu_toku_han_vl not in ( " + conv.convertWhereString( (String)map.get("zenjitu_toku_han_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// zensyu_toku_han_qt に対するWHERE区
		if( map.get("zensyu_toku_han_qt_bef") != null && ((String)map.get("zensyu_toku_han_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_qt >= " + (String)map.get("zensyu_toku_han_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_qt_aft") != null && ((String)map.get("zensyu_toku_han_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_qt <= " + (String)map.get("zensyu_toku_han_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_qt") != null && ((String)map.get("zensyu_toku_han_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_qt = " + (String)map.get("zensyu_toku_han_qt"));
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_qt_in") != null && ((String)map.get("zensyu_toku_han_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_qt in ( " + conv.convertWhereString( (String)map.get("zensyu_toku_han_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_qt_not_in") != null && ((String)map.get("zensyu_toku_han_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_qt not in ( " + conv.convertWhereString( (String)map.get("zensyu_toku_han_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// zensyu_toku_han_vl に対するWHERE区
		if( map.get("zensyu_toku_han_vl_bef") != null && ((String)map.get("zensyu_toku_han_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_vl >= " + (String)map.get("zensyu_toku_han_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_vl_aft") != null && ((String)map.get("zensyu_toku_han_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_vl <= " + (String)map.get("zensyu_toku_han_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_vl") != null && ((String)map.get("zensyu_toku_han_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_vl = " + (String)map.get("zensyu_toku_han_vl"));
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_vl_in") != null && ((String)map.get("zensyu_toku_han_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_vl in ( " + conv.convertWhereString( (String)map.get("zensyu_toku_han_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("zensyu_toku_han_vl_not_in") != null && ((String)map.get("zensyu_toku_han_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("zensyu_toku_han_vl not in ( " + conv.convertWhereString( (String)map.get("zensyu_toku_han_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baihen_suryo_qt に対するWHERE区
		if( map.get("baihen_suryo_qt_bef") != null && ((String)map.get("baihen_suryo_qt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_qt >= " + (String)map.get("baihen_suryo_qt_bef") );
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_qt_aft") != null && ((String)map.get("baihen_suryo_qt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_qt <= " + (String)map.get("baihen_suryo_qt_aft") );
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_qt") != null && ((String)map.get("baihen_suryo_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_qt = " + (String)map.get("baihen_suryo_qt"));
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_qt_in") != null && ((String)map.get("baihen_suryo_qt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_qt in ( " + conv.convertWhereString( (String)map.get("baihen_suryo_qt_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_qt_not_in") != null && ((String)map.get("baihen_suryo_qt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_qt not in ( " + conv.convertWhereString( (String)map.get("baihen_suryo_qt_not_in") ) + " )");
			whereStr = andStr;
		}


		// baihen_suryo_worst に対するWHERE区
		if( map.get("baihen_suryo_worst_bef") != null && ((String)map.get("baihen_suryo_worst_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_worst >= " + (String)map.get("baihen_suryo_worst_bef") );
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_worst_aft") != null && ((String)map.get("baihen_suryo_worst_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_worst <= " + (String)map.get("baihen_suryo_worst_aft") );
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_worst") != null && ((String)map.get("baihen_suryo_worst")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_worst = " + (String)map.get("baihen_suryo_worst"));
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_worst_in") != null && ((String)map.get("baihen_suryo_worst_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_worst in ( " + conv.convertWhereString( (String)map.get("baihen_suryo_worst_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baihen_suryo_worst_not_in") != null && ((String)map.get("baihen_suryo_worst_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_suryo_worst not in ( " + conv.convertWhereString( (String)map.get("baihen_suryo_worst_not_in") ) + " )");
			whereStr = andStr;
		}


		// baihen_vl に対するWHERE区
		if( map.get("baihen_vl_bef") != null && ((String)map.get("baihen_vl_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl >= " + (String)map.get("baihen_vl_bef") );
			whereStr = andStr;
		}
		if( map.get("baihen_vl_aft") != null && ((String)map.get("baihen_vl_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl <= " + (String)map.get("baihen_vl_aft") );
			whereStr = andStr;
		}
		if( map.get("baihen_vl") != null && ((String)map.get("baihen_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baihen_vl = " + (String)map.get("baihen_vl"));
			whereStr = andStr;
		}
		if( map.get("baihen_vl_in") != null && ((String)map.get("baihen_vl_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl in ( " + conv.convertWhereString( (String)map.get("baihen_vl_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baihen_vl_not_in") != null && ((String)map.get("baihen_vl_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl not in ( " + conv.convertWhereString( (String)map.get("baihen_vl_not_in") ) + " )");
			whereStr = andStr;
		}


		// baihen_vl_worst に対するWHERE区
		if( map.get("baihen_vl_worst_bef") != null && ((String)map.get("baihen_vl_worst_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl_worst >= " + (String)map.get("baihen_vl_worst_bef") );
			whereStr = andStr;
		}
		if( map.get("baihen_vl_worst_aft") != null && ((String)map.get("baihen_vl_worst_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl_worst <= " + (String)map.get("baihen_vl_worst_aft") );
			whereStr = andStr;
		}
		if( map.get("baihen_vl_worst") != null && ((String)map.get("baihen_vl_worst")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baihen_vl_worst = " + (String)map.get("baihen_vl_worst"));
			whereStr = andStr;
		}
		if( map.get("baihen_vl_worst_in") != null && ((String)map.get("baihen_vl_worst_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl_worst in ( " + conv.convertWhereString( (String)map.get("baihen_vl_worst_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get("baihen_vl_worst_not_in") != null && ((String)map.get("baihen_vl_worst_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("baihen_vl_worst not in ( " + conv.convertWhereString( (String)map.get("baihen_vl_worst_not_in") ) + " )");
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
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("tenhanku_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("std_torihikisaki_cd");
		sb.append(",");
		sb.append("santi_cd");
		sb.append(",");
		sb.append("tokaikyu_cd");
		sb.append(",");
		sb.append("kikaku_cd");
		sb.append(",");
		sb.append("kakutei_kb");
		sb.append(",");
		sb.append("kakutei_seq");
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
		DtFreshHachuBean bean = (DtFreshHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("Dt_Fresh_Hachu (");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhanku_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getStdTorihikisakiCd() != null && bean.getStdTorihikisakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" std_torihikisaki_cd");
		}
		if( bean.getSantiCd() != null && bean.getSantiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" santi_cd");
		}
		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tokaikyu_cd");
		}
		if( bean.getKikakuCd() != null && bean.getKikakuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_cd");
		}
		if( bean.getKakuteiKb() != null && bean.getKakuteiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kakutei_kb");
		}
		if( befKanma ) sb.append(",");
		sb.append(" kakutei_seq");
		if( bean.getNohinSyohinCd() != null && bean.getNohinSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" nohin_syohin_cd");
		}
		if( bean.getPosCd() != null && bean.getPosCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" pos_cd");
		}
		if( bean.getJisyaSyohinCd() != null && bean.getJisyaSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" jisya_syohin_cd");
		}
		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_na");
		}
		if( bean.getSyohinKa() != null && bean.getSyohinKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" syohin_ka");
		}
		if( bean.getHachuTaniNa() != null && bean.getHachuTaniNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_tani_na");
		}
		if( bean.getHachuTaniKa() != null && bean.getHachuTaniKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_tani_ka");
		}
		if( bean.getSantiNa() != null && bean.getSantiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" santi_na");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" tokaikyu_na");
		}
		if( bean.getKikakuNa() != null && bean.getKikakuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kikaku_na");
		}
		if( bean.getTorihikisakiNa() != null && bean.getTorihikisakiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" torihikisaki_na");
		}
		if( bean.getTorihikisakiKa() != null && bean.getTorihikisakiKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" torihikisaki_ka");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hinsyu_cd");
		}
		if( bean.getHinmokuCd() != null && bean.getHinmokuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hinmoku_cd");
		}
		sb.append(",");
		sb.append(" irisu_qt");
		sb.append(",");
		sb.append(" hachu_tani_qt");
		sb.append(",");
		sb.append(" gentanka_vl");
		sb.append(",");
		sb.append(" baitanka_vl");
		if( bean.getKbSobaKb() != null && bean.getKbSobaKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kb_soba_kb");
		}
		if( bean.getTeikanKb() != null && bean.getTeikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" teikan_kb");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eos_kb");
		}
		if( bean.getHachuPatternKb() != null && bean.getHachuPatternKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_pattern_kb");
		}
		if( bean.getSimejikanTm() != null && bean.getSimejikanTm().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" simejikan_tm");
		}
		sb.append(",");
		sb.append(" zenjitu_tei_han_qt");
		sb.append(",");
		sb.append(" zenjitu_tei_han_vl");
		sb.append(",");
		sb.append(" zensyu_tei_han_qt");
		sb.append(",");
		sb.append(" zensyu_tei_han_vl");
		sb.append(",");
		sb.append(" zenjitu_toku_han_qt");
		sb.append(",");
		sb.append(" zenjitu_toku_han_vl");
		sb.append(",");
		sb.append(" zensyu_toku_han_qt");
		sb.append(",");
		sb.append(" zensyu_toku_han_vl");
		sb.append(",");
		sb.append(" baihen_suryo_qt");
		sb.append(",");
		sb.append(" baihen_suryo_worst");
		sb.append(",");
		sb.append(" baihen_vl");
		sb.append(",");
		sb.append(" baihen_vl_worst");
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" riyo_user_id");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" insert_ts");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" update_ts");
		}


		sb.append(")Values(");


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getStdTorihikisakiCd() != null && bean.getStdTorihikisakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getStdTorihikisakiCd() ) + "'");
		}
		if( bean.getSantiCd() != null && bean.getSantiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSantiCd() ) + "'");
		}
		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTokaikyuCd() ) + "'");
		}
		if( bean.getKikakuCd() != null && bean.getKikakuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKikakuCd() ) + "'");
		}
		if( bean.getKakuteiKb() != null && bean.getKakuteiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKakuteiKb() ) + "'");
		}
		if( aftKanma ) sb.append(",");
		sb.append( bean.getKakuteiSeqString());
		if( bean.getNohinSyohinCd() != null && bean.getNohinSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getNohinSyohinCd() ) + "'");
		}
		if( bean.getPosCd() != null && bean.getPosCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPosCd() ) + "'");
		}
		if( bean.getJisyaSyohinCd() != null && bean.getJisyaSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getJisyaSyohinCd() ) + "'");
		}
		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyohinNa() ) + "'");
		}
		if( bean.getSyohinKa() != null && bean.getSyohinKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSyohinKa() ) + "'");
		}
		if( bean.getHachuTaniNa() != null && bean.getHachuTaniNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHachuTaniNa() ) + "'");
		}
		if( bean.getHachuTaniKa() != null && bean.getHachuTaniKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHachuTaniKa() ) + "'");
		}
		if( bean.getSantiNa() != null && bean.getSantiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSantiNa() ) + "'");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTokaikyuNa() ) + "'");
		}
		if( bean.getKikakuNa() != null && bean.getKikakuNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKikakuNa() ) + "'");
		}
		if( bean.getTorihikisakiNa() != null && bean.getTorihikisakiNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTorihikisakiNa() ) + "'");
		}
		if( bean.getTorihikisakiKa() != null && bean.getTorihikisakiKa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTorihikisakiKa() ) + "'");
		}
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		if( bean.getHinmokuCd() != null && bean.getHinmokuCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHinmokuCd() ) + "'");
		}
		sb.append(",");
		sb.append( bean.getIrisuQtString());
		sb.append(",");
		sb.append( bean.getHachuTaniQtString());
		sb.append(",");
		sb.append( bean.getGentankaVlString());
		sb.append(",");
		sb.append( bean.getBaitankaVlString());
		if( bean.getKbSobaKb() != null && bean.getKbSobaKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKbSobaKb() ) + "'");
		}
		if( bean.getTeikanKb() != null && bean.getTeikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTeikanKb() ) + "'");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		if( bean.getHachuPatternKb() != null && bean.getHachuPatternKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHachuPatternKb() ) + "'");
		}
		if( bean.getSimejikanTm() != null && bean.getSimejikanTm().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSimejikanTm() ) + "'");
		}
		sb.append(",");
		sb.append( bean.getZenjituTeiHanQtString());
		sb.append(",");
		sb.append( bean.getZenjituTeiHanVlString());
		sb.append(",");
		sb.append( bean.getZensyuTeiHanQtString());
		sb.append(",");
		sb.append( bean.getZensyuTeiHanVlString());
		sb.append(",");
		sb.append( bean.getZenjituTokuHanQtString());
		sb.append(",");
		sb.append( bean.getZenjituTokuHanVlString());
		sb.append(",");
		sb.append( bean.getZensyuTokuHanQtString());
		sb.append(",");
		sb.append( bean.getZensyuTokuHanVlString());
		sb.append(",");
		sb.append( bean.getBaihenSuryoQtString());
		sb.append(",");
		sb.append( bean.getBaihenSuryoWorstString());
		sb.append(",");
		sb.append( bean.getBaihenVlString());
		sb.append(",");
		sb.append( bean.getBaihenVlWorstString());
		if( bean.getRiyoUserId() != null && bean.getRiyoUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getRiyoUserId() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
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
		DtFreshHachuBean bean = (DtFreshHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("Dt_Fresh_Hachu set ");
		if( bean.getNohinSyohinCd() != null && bean.getNohinSyohinCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" nohin_syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getNohinSyohinCd() ) + "'");
		}
		if( bean.getPosCd() != null && bean.getPosCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" pos_cd = ");
			sb.append("'" + conv.convertString( bean.getPosCd() ) + "'");
		}
		if( bean.getJisyaSyohinCd() != null && bean.getJisyaSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" jisya_syohin_cd = ");
			sb.append("'" + conv.convertString( bean.getJisyaSyohinCd() ) + "'");
		}
		if( bean.getSyohinNa() != null && bean.getSyohinNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_na = ");
			sb.append("'" + conv.convertString( bean.getSyohinNa() ) + "'");
		}
		if( bean.getSyohinKa() != null && bean.getSyohinKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_ka = ");
			sb.append("'" + conv.convertString( bean.getSyohinKa() ) + "'");
		}
		if( bean.getHachuTaniNa() != null && bean.getHachuTaniNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_tani_na = ");
			sb.append("'" + conv.convertString( bean.getHachuTaniNa() ) + "'");
		}
		if( bean.getHachuTaniKa() != null && bean.getHachuTaniKa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_tani_ka = ");
			sb.append("'" + conv.convertString( bean.getHachuTaniKa() ) + "'");
		}
		if( bean.getSantiNa() != null && bean.getSantiNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" santi_na = ");
			sb.append("'" + conv.convertString( bean.getSantiNa() ) + "'");
		}
		if( bean.getTokaikyuNa() != null && bean.getTokaikyuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tokaikyu_na = ");
			sb.append("'" + conv.convertString( bean.getTokaikyuNa() ) + "'");
		}
		if( bean.getKikakuNa() != null && bean.getKikakuNa().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kikaku_na = ");
			sb.append("'" + conv.convertString( bean.getKikakuNa() ) + "'");
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
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinsyu_cd = ");
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		if( bean.getHinmokuCd() != null && bean.getHinmokuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hinmoku_cd = ");
			sb.append("'" + conv.convertString( bean.getHinmokuCd() ) + "'");
		}
		if( befKanma ) sb.append(",");
		sb.append(" irisu_qt = ");
		sb.append( bean.getIrisuQtString());
		sb.append(",");
		sb.append(" hachu_tani_qt = ");
		sb.append( bean.getHachuTaniQtString());
		sb.append(",");
		sb.append(" gentanka_vl = ");
		sb.append( bean.getGentankaVlString());
		sb.append(",");
		sb.append(" baitanka_vl = ");
		sb.append( bean.getBaitankaVlString());
		if( bean.getKbSobaKb() != null && bean.getKbSobaKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" kb_soba_kb = ");
			sb.append("'" + conv.convertString( bean.getKbSobaKb() ) + "'");
		}
		if( bean.getTeikanKb() != null && bean.getTeikanKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" teikan_kb = ");
			sb.append("'" + conv.convertString( bean.getTeikanKb() ) + "'");
		}
		if( bean.getEosKb() != null && bean.getEosKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" eos_kb = ");
			sb.append("'" + conv.convertString( bean.getEosKb() ) + "'");
		}
		if( bean.getHachuPatternKb() != null && bean.getHachuPatternKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" hachu_pattern_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuPatternKb() ) + "'");
		}
		if( bean.getSimejikanTm() != null && bean.getSimejikanTm().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" simejikan_tm = ");
			sb.append("'" + conv.convertString( bean.getSimejikanTm() ) + "'");
		}
		sb.append(",");
		sb.append(" zenjitu_tei_han_qt = ");
		sb.append( bean.getZenjituTeiHanQtString());
		sb.append(",");
		sb.append(" zenjitu_tei_han_vl = ");
		sb.append( bean.getZenjituTeiHanVlString());
		sb.append(",");
		sb.append(" zensyu_tei_han_qt = ");
		sb.append( bean.getZensyuTeiHanQtString());
		sb.append(",");
		sb.append(" zensyu_tei_han_vl = ");
		sb.append( bean.getZensyuTeiHanVlString());
		sb.append(",");
		sb.append(" zenjitu_toku_han_qt = ");
		sb.append( bean.getZenjituTokuHanQtString());
		sb.append(",");
		sb.append(" zenjitu_toku_han_vl = ");
		sb.append( bean.getZenjituTokuHanVlString());
		sb.append(",");
		sb.append(" zensyu_toku_han_qt = ");
		sb.append( bean.getZensyuTokuHanQtString());
		sb.append(",");
		sb.append(" zensyu_toku_han_vl = ");
		sb.append( bean.getZensyuTokuHanVlString());
		sb.append(",");
		sb.append(" baihen_suryo_qt = ");
		sb.append( bean.getBaihenSuryoQtString());
		sb.append(",");
		sb.append(" baihen_suryo_worst = ");
		sb.append( bean.getBaihenSuryoWorstString());
		sb.append(",");
		sb.append(" baihen_vl = ");
		sb.append( bean.getBaihenVlString());
		sb.append(",");
		sb.append(" baihen_vl_worst = ");
		sb.append( bean.getBaihenVlWorstString());
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


		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getTenhankuCd() != null && bean.getTenhankuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenhanku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenhankuCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getStdTorihikisakiCd() != null && bean.getStdTorihikisakiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" std_torihikisaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getStdTorihikisakiCd() ) + "'");
		}
		if( bean.getSantiCd() != null && bean.getSantiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" santi_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSantiCd() ) + "'");
		}
		if( bean.getTokaikyuCd() != null && bean.getTokaikyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tokaikyu_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTokaikyuCd() ) + "'");
		}
		if( bean.getKikakuCd() != null && bean.getKikakuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kikaku_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getKikakuCd() ) + "'");
		}
		if( bean.getKakuteiKb() != null && bean.getKakuteiKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kakutei_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getKakuteiKb() ) + "'");
		}
		if( whereAnd ) sb.append(" AND ");
		sb.append(" kakutei_seq = ");
		sb.append( bean.getKakuteiSeqString());
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
		DtFreshHachuBean bean = (DtFreshHachuBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("Dt_Fresh_Hachu where ");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenhanku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenhankuCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" std_torihikisaki_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getStdTorihikisakiCd() ) + "'");
		sb.append(" AND");
		sb.append(" santi_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSantiCd() ) + "'");
		sb.append(" AND");
		sb.append(" tokaikyu_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTokaikyuCd() ) + "'");
		sb.append(" AND");
		sb.append(" kikaku_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKikakuCd() ) + "'");
		sb.append(" AND");
		sb.append(" kakutei_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKakuteiKb() ) + "'");
		sb.append(" AND");
		sb.append(" kakutei_seq = ");
		sb.append( bean.getKakuteiSeqString());
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
