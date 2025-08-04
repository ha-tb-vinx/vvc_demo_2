/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius FUTAGAMI
 * @version 1.0(2005/03/15)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）物流経路マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する物流経路マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst570101_ButuryuKeiroDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst570101_ButuryuKeiroDM()
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
		mst570101_ButuryuKeiroBean bean = new mst570101_ButuryuKeiroBean();
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setSihaiKb( rest.getString("sihai_kb") );
		bean.setSihaiCd( rest.getString("sihai_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		bean.setButuryuKb( rest.getString("buturyu_kb") );
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***
		bean.setNohinCenterCd( rest.getString("nohin_center_cd") );
		bean.setKeiyuCenterCd( rest.getString("keiyu_center_cd") );
		bean.setTenhaiCenterCd( rest.getString("tenhai_center_cd") );
		bean.setNohinPatternRm( rest.getString("nohin_pattern_rm") );
		bean.setCenterNohinReadTime( rest.getString("center_nohin_read_time") );
		bean.setCenterNohinReadTimeRm( rest.getString("center_nohin_read_time_rm") );
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
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("R_BUTURYUKEIRO.kanri_kb ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.kanri_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.sihai_kb ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.sihai_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.syohin_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.tenpo_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.yuko_dt ");
		sb.append(", ");

//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		sb.append("R_BUTURYUKEIRO.buturyu_kb ");
		sb.append(", ");
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***

		sb.append("R_BUTURYUKEIRO.nohin_center_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.keiyu_center_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.tenhai_center_cd ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.center_nohin_read_time ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.insert_ts ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.insert_user_id ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.update_ts ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.update_user_id ");
		sb.append(", ");
		sb.append("R_BUTURYUKEIRO.delete_fg ");
		sb.append(", ");
		sb.append("R_NAMECTF0640.KANJI_RN AS nohin_pattern_rm  ");
		sb.append(", ");
		sb.append("R_NAMECTF0490.KANJI_RN AS center_nohin_read_time_rm  ");
		sb.append("from R_BUTURYUKEIRO LEFT OUTER JOIN (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.CENTER_PATTERN, userLocal)).append("') R_NAMECTF0640 ON ");
//BUGNO-016 2005.04.21 S.Takahashi START
//		sb.append("     R_BUTURYUKEIRO.nohin_center_cd || R_BUTURYUKEIRO.keiyu_center_cd || R_BUTURYUKEIRO.tenhai_center_cd = R_NAMECTF0640.ZOKUSEI_CD ");
		sb.append("     trim(R_BUTURYUKEIRO.nohin_center_cd) || trim(R_BUTURYUKEIRO.keiyu_center_cd) || trim(R_BUTURYUKEIRO.tenhai_center_cd) = R_NAMECTF0640.ZOKUSEI_CD ");
//BUGNO-016 2005.04.21 S.Takahashi END
		sb.append("     LEFT OUTER JOIN  (SELECT * FROM R_NAMECTF WHERE SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.DELIVERED_LEAD_TIME, userLocal)).append("') R_NAMECTF0490 ON ");
		sb.append("     R_BUTURYUKEIRO.center_nohin_read_time = R_NAMECTF0490.CODE_CD ");

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


		// sihai_kb に対するWHERE区
		if( map.get("sihai_kb_bef") != null && ((String)map.get("sihai_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb >= '" + conv.convertWhereString( (String)map.get("sihai_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_aft") != null && ((String)map.get("sihai_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb <= '" + conv.convertWhereString( (String)map.get("sihai_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb") != null && ((String)map.get("sihai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb = '" + conv.convertWhereString( (String)map.get("sihai_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_like") != null && ((String)map.get("sihai_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb like '%" + conv.convertWhereString( (String)map.get("sihai_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_bef_like") != null && ((String)map.get("sihai_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb like '%" + conv.convertWhereString( (String)map.get("sihai_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_aft_like") != null && ((String)map.get("sihai_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb like '" + conv.convertWhereString( (String)map.get("sihai_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_in") != null && ((String)map.get("sihai_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sihai_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sihai_kb_not_in") != null && ((String)map.get("sihai_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sihai_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}


		// sihai_cd に対するWHERE区
		if( map.get("sihai_cd_bef") != null && ((String)map.get("sihai_cd_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd >= '" + conv.convertWhereString( (String)map.get("sihai_cd_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_aft") != null && ((String)map.get("sihai_cd_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd <= '" + conv.convertWhereString( (String)map.get("sihai_cd_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd") != null && ((String)map.get("sihai_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd = '" + conv.convertWhereString( (String)map.get("sihai_cd") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_like") != null && ((String)map.get("sihai_cd_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd like '%" + conv.convertWhereString( (String)map.get("sihai_cd_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_bef_like") != null && ((String)map.get("sihai_cd_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd like '%" + conv.convertWhereString( (String)map.get("sihai_cd_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_aft_like") != null && ((String)map.get("sihai_cd_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd like '" + conv.convertWhereString( (String)map.get("sihai_cd_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_in") != null && ((String)map.get("sihai_cd_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sihai_cd_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("sihai_cd_not_in") != null && ((String)map.get("sihai_cd_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("sihai_cd not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("sihai_cd_not_in") ),",","','") + "' )");
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


		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt_bef") != null && ((String)map.get("yuko_dt_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt >= '" + conv.convertWhereString( (String)map.get("yuko_dt_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft") != null && ((String)map.get("yuko_dt_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt <= '" + conv.convertWhereString( (String)map.get("yuko_dt_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_like") != null && ((String)map.get("yuko_dt_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_bef_like") != null && ((String)map.get("yuko_dt_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '%" + conv.convertWhereString( (String)map.get("yuko_dt_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_aft_like") != null && ((String)map.get("yuko_dt_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt like '" + conv.convertWhereString( (String)map.get("yuko_dt_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_in") != null && ((String)map.get("yuko_dt_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("yuko_dt_not_in") != null && ((String)map.get("yuko_dt_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("yuko_dt not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("yuko_dt_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}

/***********************************************************************************************************
 *							2005_05_20 新ER ver3.6 対応  物流区分追加 start
 ***********************************************************************************************************/
		//物流区分 buturyu_kb に対するWHERE区
		if( map.get("buturyu_kb_bef") != null && ((String)map.get("buturyu_kb_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb >= '" + conv.convertWhereString( (String)map.get("buturyu_kb_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_aft") != null && ((String)map.get("buturyu_kb_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb <= '" + conv.convertWhereString( (String)map.get("buturyu_kb_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb") != null && ((String)map.get("buturyu_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb = '" + conv.convertWhereString( (String)map.get("buturyu_kb") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_like") != null && ((String)map.get("buturyu_kb_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '%" + conv.convertWhereString( (String)map.get("buturyu_kb_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_bef_like") != null && ((String)map.get("buturyu_kb_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '%" + conv.convertWhereString( (String)map.get("buturyu_kb_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_aft_like") != null && ((String)map.get("buturyu_kb_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb like '" + conv.convertWhereString( (String)map.get("buturyu_kb_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_in") != null && ((String)map.get("buturyu_kb_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb in ( '" + replaceAll(conv.convertWhereString( (String)map.get("buturyu_kb_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("buturyu_kb_not_in") != null && ((String)map.get("buturyu_kb_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("buturyu_kb not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("buturyu_kb_not_in") ),",","','") + "' )");
			whereStr = andStr;
		}
/********************************* 2005_05_20 新ER ver3.6 対応  物流区分追加 end ********************************************/

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


		// center_nohin_read_time に対するWHERE区
		if( map.get("center_nohin_read_time_bef") != null && ((String)map.get("center_nohin_read_time_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time >= '" + conv.convertWhereString( (String)map.get("center_nohin_read_time_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_aft") != null && ((String)map.get("center_nohin_read_time_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time <= '" + conv.convertWhereString( (String)map.get("center_nohin_read_time_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time") != null && ((String)map.get("center_nohin_read_time")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time = '" + conv.convertWhereString( (String)map.get("center_nohin_read_time") ) + "'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_like") != null && ((String)map.get("center_nohin_read_time_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time like '%" + conv.convertWhereString( (String)map.get("center_nohin_read_time_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_bef_like") != null && ((String)map.get("center_nohin_read_time_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time like '%" + conv.convertWhereString( (String)map.get("center_nohin_read_time_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_aft_like") != null && ((String)map.get("center_nohin_read_time_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time like '" + conv.convertWhereString( (String)map.get("center_nohin_read_time_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_in") != null && ((String)map.get("center_nohin_read_time_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time in ( '" + replaceAll(conv.convertWhereString( (String)map.get("center_nohin_read_time_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get("center_nohin_read_time_not_in") != null && ((String)map.get("center_nohin_read_time_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("center_nohin_read_time not in ( '" + replaceAll(conv.convertWhereString( (String)map.get("center_nohin_read_time_not_in") ),",","','") + "' )");
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
		sb.append("sihai_kb");
		sb.append(",");
		sb.append("sihai_cd");
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("yuko_dt");
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
		mst570101_ButuryuKeiroBean bean = (mst570101_ButuryuKeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_BUTURYUKEIRO (");
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
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sihai_kb");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" sihai_cd");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}

//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 ) {
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" buturyu_kb");
		}
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***

//BUGNO-016 2005.04.21 S.Takahashi START
//		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_center_cd");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiyu_center_cd");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhai_center_cd");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//BUGNO-016 2005.04.21 S.Takahashi END
		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_nohin_read_time");
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
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSihaiKb() ) + "'");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSihaiCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}

//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 ) {
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getButuryuKb() ) + "'");
		}
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***

//BUGNO-016 2005.04.21 S.Takahashi START
//		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNohinCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKeiyuCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenhaiCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//BUGNO-016 2005.04.21 S.Takahashi END
		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getCenterNohinReadTime() ) + "'");
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
		mst570101_ButuryuKeiroBean bean = (mst570101_ButuryuKeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_BUTURYUKEIRO set ");
//BUGNO-016 2005.04.21 S.Takahashi START
//		if( bean.getNohinCenterCd() != null && bean.getNohinCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			befKanma = true;
			sb.append(" nohin_center_cd = ");
			sb.append("'" + conv.convertString( bean.getNohinCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getKeiyuCenterCd() != null && bean.getKeiyuCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" keiyu_center_cd = ");
			sb.append("'" + conv.convertString( bean.getKeiyuCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//		if( bean.getTenhaiCenterCd() != null && bean.getTenhaiCenterCd().trim().length() != 0 )
//		{
//BUGNO-016 2005.04.21 S.Takahashi END
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenhai_center_cd = ");
			sb.append("'" + conv.convertString( bean.getTenhaiCenterCd() ) + "'");
//BUGNO-016 2005.04.21 S.Takahashi START
//		}
//BUGNO-016 2005.04.21 S.Takahashi END
		if( bean.getCenterNohinReadTime() != null && bean.getCenterNohinReadTime().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" center_nohin_read_time = ");
			sb.append("'" + conv.convertString( bean.getCenterNohinReadTime() ) + "'");
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
		if( bean.getSihaiKb() != null && bean.getSihaiKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" sihai_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getSihaiKb() ) + "'");
		}
		if( bean.getSihaiCd() != null && bean.getSihaiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" sihai_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSihaiCd() ) + "'");
		}
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" syohin_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		}

//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		if( bean.getButuryuKb() != null && bean.getButuryuKb().trim().length() != 0 ) {
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" buturyu_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getButuryuKb() ) + "'");
		}
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***

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
		mst570101_ButuryuKeiroBean bean = (mst570101_ButuryuKeiroBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_BUTURYUKEIRO where ");
		sb.append(" kanri_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		sb.append(" AND");
		sb.append(" kanri_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" AND");
		sb.append(" sihai_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getSihaiKb() ) + "'");
		sb.append(" AND");
		sb.append(" sihai_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSihaiCd() ) + "'");
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append(" AND");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");

//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 start ***
		sb.append(" AND");
		sb.append(" buturyu_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getButuryuKb() ) + "'");
//2005_05_20 新ER ver3.6 対応 *** 物流区分追加 end ***

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
