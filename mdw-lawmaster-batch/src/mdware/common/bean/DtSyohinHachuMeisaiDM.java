package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル: DtSeiHachuMeiDM クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2002.09.09) Version 1.0.IST_CUSTOM.1
 * @version X.X (Create time: 2003/11/7 14:42:12)
 */
public class DtSyohinHachuMeisaiDM extends DataModule
{
	private static int sequence = -1;
	private static Object o = new Object();
	/**
	 * 連番を使用しINSERTを行う時はこのメソッドを呼び出してください。
	 * @return 最大の連番＋１を返す。
	 */
	private synchronized int getNextSeq()
	{
		int retSeq = -1;
		synchronized(o)
		{
			if( sequence < 0 )
				sequence = Integer.parseInt(super.getNextSequence("------","dt_sei_hachu_mei"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public DtSyohinHachuMeisaiDM()
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
		DtSyohinHachuMeisaiBean bean = new DtSyohinHachuMeisaiBean();
		bean.setDataDenpNb( rest.getString("data_denp_nb") );
		bean.setDataDenpgyoNb( rest.getLong("data_denpgyo_nb") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setSantiCd( rest.getString("santi_cd") );
		bean.setTokaikyuCd( rest.getString("tokaikyu_cd") );
		bean.setKikakuCd( rest.getString("kikaku_cd") );
		bean.setNohinDt( rest.getString("nohin_dt") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setSantiNa( rest.getString("santi_na") );
		bean.setTokaikyuNa( rest.getString("tokaikyu_na") );
		bean.setKikakuNa( rest.getString("kikaku_na") );
		bean.setTenpoNa( rest.getString("tenpo_na") );
		bean.setSyohinNa( rest.getString("syohin_na") );
		bean.setSyohinKa( rest.getString("syohin_ka") );
		bean.setGentankaVl( rest.getBigDecimal("gentanka_vl") );
		bean.setBaitankaVl( rest.getBigDecimal("baitanka_vl") );
		bean.setHachuTaniQt( rest.getBigDecimal("hachu_tani_qt") );
		bean.setHachuQt( rest.getBigDecimal("hachu_qt") );
		bean.setHachuSuryoQt( rest.getBigDecimal("hachu_suryo_qt") );
		bean.setKakuteiQt( rest.getBigDecimal("kakutei_qt") );
		bean.setKakuteiSuryoQt( rest.getBigDecimal("kakutei_suryo_qt") );
		bean.setDenpyoHakoKb( rest.getString("denpyo_hako_kb") );
		bean.setHachuDenpyoKb( rest.getString("hachu_denpyo_kb") );
		bean.setNohinHohoKb( rest.getString("nohin_hoho_kb") );
		bean.setButuryuKb( rest.getString("buturyu_kb") );
		bean.setPcDcKb( rest.getString("pc_dc_kb") );
		bean.setKbTokubaiKb( rest.getString("kb_tokubai_kb") );
		bean.setTokubaiCd( rest.getString("tokubai_cd") );
		bean.setJuchuListOutputKb( rest.getString("juchu_list_output_kb") );
		bean.setNohinSyoriKb( rest.getString("nohin_syori_kb") );
		bean.setDenpyoNb( rest.getString("denpyo_nb") );
		bean.setDenpyogyoNb( rest.getLong("denpyogyo_nb") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
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
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dt_syohin_hachu_meisai ");
		if( map.get("data_denp_nb") != null && ((String)map.get("data_denp_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("data_denp_nb = '" + (String)map.get("data_denp_nb") + "'");
			whereStr = andStr;
		}
		if( map.get("data_denpgyo_nb") != null && ((String)map.get("data_denpgyo_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("data_denpgyo_nb = " + (String)map.get("data_denpgyo_nb"));
			whereStr = andStr;
		}
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("bumon_cd = '" + (String)map.get("bumon_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_cd = '" + (String)map.get("syohin_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("santi_cd") != null && ((String)map.get("santi_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_cd = '" + (String)map.get("santi_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_cd") != null && ((String)map.get("tokaikyu_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_cd = '" + (String)map.get("tokaikyu_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_cd") != null && ((String)map.get("kikaku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_cd = '" + (String)map.get("kikaku_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_dt") != null && ((String)map.get("nohin_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_dt = '" + (String)map.get("nohin_dt") + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_cd = '" + (String)map.get("tenpo_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("santi_na") != null && ((String)map.get("santi_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("santi_na = '" + (String)map.get("santi_na") + "'");
			whereStr = andStr;
		}
		if( map.get("tokaikyu_na") != null && ((String)map.get("tokaikyu_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokaikyu_na = '" + (String)map.get("tokaikyu_na") + "'");
			whereStr = andStr;
		}
		if( map.get("kikaku_na") != null && ((String)map.get("kikaku_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kikaku_na = '" + (String)map.get("kikaku_na") + "'");
			whereStr = andStr;
		}
		if( map.get("tenpo_na") != null && ((String)map.get("tenpo_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tenpo_na = '" + (String)map.get("tenpo_na") + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_na") != null && ((String)map.get("syohin_na")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_na = '" + (String)map.get("syohin_na") + "'");
			whereStr = andStr;
		}
		if( map.get("syohin_ka") != null && ((String)map.get("syohin_ka")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("syohin_ka = '" + (String)map.get("syohin_ka") + "'");
			whereStr = andStr;
		}
		if( map.get("gentanka_vl") != null && ((String)map.get("gentanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("gentanka_vl = " + (String)map.get("gentanka_vl"));
			whereStr = andStr;
		}
		if( map.get("baitanka_vl") != null && ((String)map.get("baitanka_vl")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("baitanka_vl = " + (String)map.get("baitanka_vl"));
			whereStr = andStr;
		}
		if( map.get("hachu_tani_qt") != null && ((String)map.get("hachu_tani_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_tani_qt = " + (String)map.get("hachu_tani_qt"));
			whereStr = andStr;
		}
		if( map.get("hachu_qt") != null && ((String)map.get("hachu_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_qt = " + (String)map.get("hachu_qt"));
			whereStr = andStr;
		}
		if( map.get("hachu_suryo_qt") != null && ((String)map.get("hachu_suryo_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("hachu_suryo_qt = " + (String)map.get("hachu_suryo_qt"));
			whereStr = andStr;
		}
		if( map.get("kakutei_qt") != null && ((String)map.get("kakutei_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("kakutei_qt = " + (String)map.get("kakutei_qt"));
			whereStr = andStr;
		}
		if( map.get("kakutei_suryo_qt") != null && ((String)map.get("kakutei_suryo_qt")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("kakutei_suryo_qt = " + (String)map.get("kakutei_suryo_qt"));
			whereStr = andStr;
		}
		if( map.get("denpyo_hako_kb") != null && ((String)map.get("denpyo_hako_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_denpyo_kb = '" + (String)map.get("hachu_denpyo_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("hachu_denpyo_kb") != null && ((String)map.get("hachu_denpyo_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachu_denpyo_kb = '" + (String)map.get("hachu_denpyo_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_hoho_kb") != null && ((String)map.get("nohin_hoho_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_hoho_kb = '" + (String)map.get("nohin_hoho_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb") != null && ((String)map.get("buturyu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb = '" + (String)map.get("buturyu_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("pc_dc_kb") != null && ((String)map.get("pc_dc_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("pc_dc_kb = '" + (String)map.get("pc_dc_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("kb_tokubai_kb") != null && ((String)map.get("kb_tokubai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("kb_tokubai_kb = '" + (String)map.get("kb_tokubai_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("tokubai_cd") != null && ((String)map.get("tokubai_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tokubai_cd = '" + (String)map.get("tokubai_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("denpyo_nb") != null && ((String)map.get("denpyo_nb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("denpyo_nb = '" + (String)map.get("denpyo_nb") + "'");
			whereStr = andStr;
		}
		if( map.get("denpyogyo_nb") != null && ((String)map.get("denpyogyo_nb")).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append("denpyogyo_nb = " + (String)map.get("denpyogyo_nb"));
			whereStr = andStr;
		}
		if( map.get("juchu_list_output_kb") != null && ((String)map.get("juchu_list_output_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("juchu_list_output_kb = '" + (String)map.get("juchu_list_output_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("nohin_syori_kb") != null && ((String)map.get("nohin_syori_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("nohin_syori_kb = '" + (String)map.get("nohin_syori_kb") + "'");
			whereStr = andStr;
		}
		if( map.get("riyo_user_id") != null && ((String)map.get("riyo_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("riyo_user_id = '" + (String)map.get("riyo_user_id") + "'");
			whereStr = andStr;
		}
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("insert_ts = '" + (String)map.get("insert_ts") + "'");
			whereStr = andStr;
		}
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("update_ts = '" + (String)map.get("update_ts") + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("data_denp_nb");
		sb.append(",");
		sb.append("data_denpgyo_nb");
		sb.append(",");
		sb.append("bumon_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("torihikisaki_cd");
		sb.append(",");
		sb.append("santi_cd");
		sb.append(",");
		sb.append("tokaikyu_cd");
		sb.append(",");
		sb.append("kikaku_cd");
		sb.append(",");
		sb.append("nohin_dt");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("santi_na");
		sb.append(",");
		sb.append("tokaikyu_na");
		sb.append(",");
		sb.append("kikaku_na");
		sb.append(",");
		sb.append("tenpo_na");
		sb.append(",");
		sb.append("syohin_na");
		sb.append(",");
		sb.append("syohin_ka");
		sb.append(",");
		sb.append("gentanka_vl");
		sb.append(",");
		sb.append("baitanka_vl");
		sb.append(",");
		sb.append("hachu_tani_qt");
		sb.append(",");
		sb.append("hachu_qt");
		sb.append(",");
		sb.append("hachu_suryo_qt");
		sb.append(",");
		sb.append("kakutei_qt");
		sb.append(",");
		sb.append("kakutei_suryo_qt");
		sb.append(",");
		sb.append("denpyo_hako_kb");
		sb.append(",");
		sb.append("hachu_denpyo_kb");
		sb.append(",");
		sb.append("nohin_hoho_kb");
		sb.append(",");
		sb.append("buturyu_kb");
		sb.append(",");
		sb.append("pc_dc_kb");
		sb.append(",");
		sb.append("kb_tokubai_kb");
		sb.append(",");
		sb.append("tokubai_cd");
		sb.append(",");
		sb.append("denpyo_nb");
		sb.append(",");
		sb.append("denpyogyo_nb");
		sb.append(",");
		sb.append("juchu_list_output_kb");
		sb.append(",");
		sb.append("nohin_syori_kb");
		sb.append(",");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
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
		DtSyohinHachuMeisaiBean bean = (DtSyohinHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("dt_syohin_hachu_meisai (");
		sb.append(" data_denp_nb");
		sb.append(",");
		sb.append(" data_denpgyo_nb");
		sb.append(",");
		sb.append(" bumon_cd");
		sb.append(",");
		sb.append(" syohin_cd");
		sb.append(",");
		sb.append(" torihikisaki_cd");
		sb.append(",");
		sb.append(" santi_cd");
		sb.append(",");
		sb.append(" tokaikyu_cd");
		sb.append(",");
		sb.append(" kikaku_cd");
		sb.append(",");
		sb.append(" nohin_dt");
		sb.append(",");
		sb.append(" tenpo_cd");
		sb.append(",");
		sb.append(" santi_na");
		sb.append(",");
		sb.append(" tokaikyu_na");
		sb.append(",");
		sb.append(" kikaku_na");
		sb.append(",");
		sb.append(" tenpo_na");
		sb.append(",");
		sb.append(" syohin_na");
		sb.append(",");
		sb.append(" syohin_ka");
		sb.append(",");
		sb.append(" gentanka_vl");
		sb.append(",");
		sb.append(" baitanka_vl");
		sb.append(",");
		sb.append(" hachu_tani_qt");
		sb.append(",");
		sb.append(" hachu_qt");
		sb.append(",");
		sb.append(" hachu_suryo_qt");
		sb.append(",");
		sb.append(" kakutei_qt");
		sb.append(",");
		sb.append(" kakutei_suryo_qt");
		sb.append(",");
		sb.append("denpyo_hako_kb");
		sb.append(",");
		sb.append("hachu_denpyo_kb");
		sb.append(",");
		sb.append("nohin_hoho_kb");
		sb.append(",");
		sb.append("buturyu_kb");
		sb.append(",");
		sb.append("pc_dc_kb");
		sb.append(",");
		sb.append(" kb_tokubai_kb");
		sb.append(",");
		sb.append("tokubai_cd");
		sb.append(",");
		sb.append(" denpyo_nb");
		sb.append(",");
		sb.append(" denpyogyo_nb");
		sb.append(",");
		sb.append(" juchu_list_output_kb");
		sb.append(",");
		sb.append(" nohin_syori_kb");
		sb.append(",");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getDataDenpNbString());
		sb.append(",");
		sb.append( bean.getDataDenpgyoNbString());
		sb.append(",");
		sb.append( bean.getBumonCdString());
		sb.append(",");
		sb.append( bean.getSyohinCdString());
		sb.append(",");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append( bean.getSantiCdString());
		sb.append(",");
		sb.append( bean.getTokaikyuCdString());
		sb.append(",");
		sb.append( bean.getKikakuCdString());
		sb.append(",");
		sb.append( bean.getNohinDtString());
		sb.append(",");
		sb.append( bean.getTenpoCdString());
		sb.append(",");
		sb.append( bean.getSantiNaString());
		sb.append(",");
		sb.append( bean.getTokaikyuNaString());
		sb.append(",");
		sb.append( bean.getKikakuNaString());
		sb.append(",");
		sb.append( bean.getTenpoNaString());
		sb.append(",");
		sb.append( bean.getSyohinNaString());
		sb.append(",");
		sb.append( bean.getSyohinKaString());
		sb.append(",");
		sb.append( bean.getGentankaVlString());
		sb.append(",");
		sb.append( bean.getBaitankaVlString());
		sb.append(",");
		sb.append( bean.getHachuTaniQtString());
		sb.append(",");
		sb.append( bean.getHachuQtString());
		sb.append(",");
		sb.append( bean.getHachuSuryoQtString());
		sb.append(",");
		sb.append( bean.getKakuteiQtString());
		sb.append(",");
		sb.append( bean.getKakuteiSuryoQtString());
		sb.append(",");
		sb.append( bean.getDenpyoHakoKbString());
		sb.append(",");
		sb.append( bean.getHachuDenpyoKbString());
		sb.append(",");
		sb.append( bean.getNohinHohoKbString());
		sb.append(",");
		sb.append( bean.getButuryuKbString());
		sb.append(",");
		sb.append( bean.getPcDcKbString());
		sb.append(",");
		sb.append( bean.getKbTokubaiKbString());
		sb.append(",");
		sb.append( bean.getTokubaiCdString());
		sb.append(",");
		sb.append( bean.getDenpyoNbString());
		sb.append(",");
		sb.append( bean.getDenpyogyoNbString());
		sb.append(",");
		sb.append( bean.getJuchuListOutputKbString());
		sb.append(",");
		sb.append( bean.getNohinSyoriKbString());
		sb.append(",");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		DtSyohinHachuMeisaiBean bean = (DtSyohinHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("dt_syohin_hachu_meisai set ");
		sb.append(" data_denp_nb = ");
		sb.append( bean.getDataDenpNbString());
		sb.append(",");
		sb.append(" data_denpgyo_nb = ");
		sb.append( bean.getDataDenpgyoNbString());
		sb.append(",");
		sb.append(" bumon_cd = ");
		sb.append( bean.getBumonCdString());
		sb.append(",");
		sb.append(" syohin_cd = ");
		sb.append( bean.getSyohinCdString());
		sb.append(",");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append(" santi_cd = ");
		sb.append( bean.getSantiCdString());
		sb.append(",");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(",");
		sb.append(" kikaku_cd = ");
		sb.append( bean.getKikakuCdString());
		sb.append(",");
		sb.append(" nohin_dt = ");
		sb.append( bean.getNohinDtString());
		sb.append(",");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
		sb.append(",");
		sb.append(" santi_na = ");
		sb.append( bean.getSantiNaString());
		sb.append(",");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(",");
		sb.append(" kikaku_na = ");
		sb.append( bean.getKikakuNaString());
		sb.append(",");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(",");
		sb.append(" syohin_na = ");
		sb.append( bean.getSyohinNaString());
		sb.append(",");
		sb.append(" syohin_ka = ");
		sb.append( bean.getSyohinKaString());
		sb.append(",");
		sb.append(" gentanka_vl = ");
		sb.append( bean.getGentankaVlString());
		sb.append(",");
		sb.append(" baitanka_vl = ");
		sb.append( bean.getBaitankaVlString());
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
		sb.append(" denpyo_hako_kb = ");
		sb.append( bean.getDenpyoHakoKbString());
		sb.append(",");
		sb.append(" hachu_denpyo_kb = ");
		sb.append( bean.getHachuDenpyoKbString());
		sb.append(",");
		sb.append(" nohin_hoho_kb = ");
		sb.append( bean.getNohinHohoKbString());
		sb.append(",");
		sb.append(" buturyu_kb = ");
		sb.append( bean.getButuryuKbString());
		sb.append(",");
		sb.append(" pc_dc_kb = ");
		sb.append( bean.getPcDcKbString());
		sb.append(",");
		sb.append(" kb_tokubai_kb = ");
		sb.append( bean.getKbTokubaiKbString());
		sb.append(",");
		sb.append(" tokubai_cd = ");
		sb.append( bean.getTokubaiCdString());
		sb.append(",");
		sb.append(" denpyo_nb = ");
		sb.append( bean.getDenpyoNbString());
		sb.append(",");
		sb.append(" denpyogyo_nb = ");
		sb.append( bean.getDenpyogyoNbString());
		sb.append(",");
		sb.append(" juchu_list_output_kb = ");
		sb.append( bean.getJuchuListOutputKbString());
		sb.append(",");
		sb.append(" nohin_syori_kb = ");
		sb.append( bean.getNohinSyoriKbString());
		sb.append(",");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" data_denp_nb = ");
		sb.append( bean.getDataDenpNbString());
		sb.append(" AND");
		sb.append(" data_denpgyo_nb = ");
		sb.append( bean.getDataDenpgyoNbString());
		sb.append(" AND");
		sb.append(" bumon_cd = ");
		sb.append( bean.getBumonCdString());
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append( bean.getSyohinCdString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(" AND");
		sb.append(" santi_cd = ");
		sb.append( bean.getSantiCdString());
		sb.append(" AND");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(" AND");
		sb.append(" kikaku_cd = ");
		sb.append( bean.getKikakuCdString());
		sb.append(" AND");
		sb.append(" nohin_dt = ");
		sb.append( bean.getNohinDtString());
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
		sb.append(" AND");
		sb.append(" santi_na = ");
		sb.append( bean.getSantiNaString());
		sb.append(" AND");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(" AND");
		sb.append(" kikaku_na = ");
		sb.append( bean.getKikakuNaString());
		sb.append(" AND");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(" AND");
		sb.append(" syohin_na = ");
		sb.append( bean.getSyohinNaString());
		sb.append(" AND");
		sb.append(" syohin_ka = ");
		sb.append( bean.getSyohinKaString());
		sb.append(" AND");
		sb.append(" gentanka_vl = ");
		sb.append( bean.getGentankaVlString());
		sb.append(" AND");
		sb.append(" baitanka_vl = ");
		sb.append( bean.getBaitankaVlString());
		sb.append(" AND");
		sb.append(" hachu_tani_qt = ");
		sb.append( bean.getHachuTaniQtString());
		sb.append(" AND");
		sb.append(" hachu_qt = ");
		sb.append( bean.getHachuQtString());
		sb.append(" AND");
		sb.append(" hachu_suryo_qt = ");
		sb.append( bean.getHachuSuryoQtString());
		sb.append(" AND");
		sb.append(" kakutei_qt = ");
		sb.append( bean.getKakuteiQtString());
		sb.append(" AND");
		sb.append(" kakutei_suryo_qt = ");
		sb.append( bean.getKakuteiSuryoQtString());
		sb.append(" AND");
		sb.append(" denpyo_hako_kb = ");
		sb.append( bean.getDenpyoHakoKbString());
		sb.append(" AND");
		sb.append(" hachu_denpyo_kb = ");
		sb.append( bean.getHachuDenpyoKbString());
		sb.append(" AND");
		sb.append(" nohin_hoho_kb = ");
		sb.append( bean.getNohinHohoKbString());
		sb.append(" AND");
		sb.append(" buturyu_kb = ");
		sb.append( bean.getButuryuKbString());
		sb.append(" AND");
		sb.append(" pc_dc_kb = ");
		sb.append( bean.getPcDcKbString());
		sb.append(" AND");
		sb.append(" kb_tokubai_kb = ");
		sb.append( bean.getKbTokubaiKbString());
		sb.append(" AND");
		sb.append(" tokubai_cd = ");
		sb.append( bean.getTokubaiCdString());
		sb.append(" AND");
		sb.append(" denpyo_nb = ");
		sb.append( bean.getDenpyoNbString());
		sb.append(" AND");
		sb.append(" denpyogyo_nb = ");
		sb.append( bean.getDenpyogyoNbString());
		sb.append(" AND");
		sb.append(" juchu_list_output_kb = ");
		sb.append( bean.getJuchuListOutputKbString());
		sb.append(" AND");
		sb.append(" nohin_syori_kb = ");
		sb.append( bean.getNohinSyoriKbString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		DtSyohinHachuMeisaiBean bean = (DtSyohinHachuMeisaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("dt_syohin_hachu_meisai where ");
		sb.append(" data_denp_nb = ");
		sb.append( bean.getDataDenpNbString());
		sb.append(" AND");
		sb.append(" data_denpgyo_nb = ");
		sb.append( bean.getDataDenpgyoNbString());
		sb.append(" AND");
		sb.append(" bumon_cd = ");
		sb.append( bean.getBumonCdString());
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append( bean.getSyohinCdString());
		sb.append(" AND");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(" AND");
		sb.append(" santi_cd = ");
		sb.append( bean.getSantiCdString());
		sb.append(" AND");
		sb.append(" tokaikyu_cd = ");
		sb.append( bean.getTokaikyuCdString());
		sb.append(" AND");
		sb.append(" kikaku_cd = ");
		sb.append( bean.getKikakuCdString());
		sb.append(" AND");
		sb.append(" nohin_dt = ");
		sb.append( bean.getNohinDtString());
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append( bean.getTenpoCdString());
		sb.append(" AND");
		sb.append(" santi_na = ");
		sb.append( bean.getSantiNaString());
		sb.append(" AND");
		sb.append(" tokaikyu_na = ");
		sb.append( bean.getTokaikyuNaString());
		sb.append(" AND");
		sb.append(" kikaku_na = ");
		sb.append( bean.getKikakuNaString());
		sb.append(" AND");
		sb.append(" tenpo_na = ");
		sb.append( bean.getTenpoNaString());
		sb.append(" AND");
		sb.append(" syohin_na = ");
		sb.append( bean.getSyohinNaString());
		sb.append(" AND");
		sb.append(" syohin_ka = ");
		sb.append( bean.getSyohinKaString());
		sb.append(" AND");
		sb.append(" gentanka_vl = ");
		sb.append( bean.getGentankaVlString());
		sb.append(" AND");
		sb.append(" baitanka_vl = ");
		sb.append( bean.getBaitankaVlString());
		sb.append(" AND");
		sb.append(" hachu_tani_qt = ");
		sb.append( bean.getHachuTaniQtString());
		sb.append(" AND");
		sb.append(" hachu_qt = ");
		sb.append( bean.getHachuQtString());
		sb.append(" AND");
		sb.append(" hachu_suryo_qt = ");
		sb.append( bean.getHachuSuryoQtString());
		sb.append(" AND");
		sb.append(" kakutei_qt = ");
		sb.append( bean.getKakuteiQtString());
		sb.append(" AND");
		sb.append(" kakutei_suryo_qt = ");
		sb.append( bean.getKakuteiSuryoQtString());
		sb.append(" AND");
		sb.append(" denpyo_hako_kb = ");
		sb.append( bean.getDenpyoHakoKbString());
		sb.append(" AND");
		sb.append(" hachu_denpyo_kb = ");
		sb.append( bean.getHachuDenpyoKbString());
		sb.append(" AND");
		sb.append(" nohin_hoho_kb = ");
		sb.append( bean.getNohinHohoKbString());
		sb.append(" AND");
		sb.append(" buturyu_kb = ");
		sb.append( bean.getButuryuKbString());
		sb.append(" AND");
		sb.append(" pc_dc_kb = ");
		sb.append( bean.getPcDcKbString());
		sb.append(" AND");
		sb.append(" kb_tokubai_kb = ");
		sb.append( bean.getKbTokubaiKbString());
		sb.append(" AND");
		sb.append(" tokubai_cd = ");
		sb.append( bean.getTokubaiCdString());
		sb.append(" AND");
		sb.append(" denpyo_nb = ");
		sb.append( bean.getDenpyoNbString());
		sb.append(" AND");
		sb.append(" denpyogyo_nb = ");
		sb.append( bean.getDenpyogyoNbString());
		sb.append(" AND");
		sb.append(" juchu_list_output_kb = ");
		sb.append( bean.getJuchuListOutputKbString());
		sb.append(" AND");
		sb.append(" nohin_syori_kb = ");
		sb.append( bean.getNohinSyoriKbString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

}
