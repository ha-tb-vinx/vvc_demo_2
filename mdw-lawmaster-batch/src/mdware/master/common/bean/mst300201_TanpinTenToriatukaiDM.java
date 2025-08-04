package mdware.master.common.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
 * @version 1.1(2006/10/31)障害票№0227対応 WASダウン対応 複数回実施されるINSERT,UPDATE,DELETE処理は、処理を効率化するためPreparedStatementを使用する
 */
public class mst300201_TanpinTenToriatukaiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

//	 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
	private PreparedStatement insertPS = null;
	private PreparedStatement deletePS = null;
	private PreparedStatement updatePS = null;
//	 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 
	
	/**
	 * コンストラクタ
	 */
	public mst300201_TanpinTenToriatukaiDM()
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
		mst300201_TanpinTenToriatukaiBean bean = new mst300201_TanpinTenToriatukaiBean();
//		      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setBumonCd( rest.getString("bumon_cd") );
//		      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setHanbaiStDt( rest.getString("hanbai_st_dt") );
		bean.setNonActKb( rest.getString("non_act_kb") );
		bean.setHaseimotoKb( rest.getString("haseimoto_kb") );
		bean.setTanawariPatern( rest.getString("tanawari_patern") );
// 2005.09.14 Y.Inaba 初期設定仕様変更に伴う修正 START
// 		bean.setJukiNb( rest.getLong("juki_nb") );
// 		bean.setTanaNb( rest.getLong("tana_nb") );
// 		bean.setFaceQt( rest.getLong("face_qt") );
// 		bean.setMinTinretuQt( rest.getLong("min_tinretu_qt") );
// 		bean.setMaxTinretuQt( rest.getLong("max_tinretu_qt") );
// 		bean.setBaseZaikoNisuQt( rest.getLong("base_zaiko_nisu_qt") );
		bean.setJukiNb( rest.getString("juki_nb") );
		bean.setTanaNb( rest.getString("tana_nb") );
		bean.setFaceQt( rest.getString("face_qt") );
		bean.setMinTinretuQt( rest.getString("min_tinretu_qt") );
		bean.setMaxTinretuQt( rest.getString("max_tinretu_qt") );
		bean.setBaseZaikoNisuQt( rest.getString("base_zaiko_nisu_qt") );
// 2005.09.14 Y.Inaba 初期設定仕様変更に伴う修正 END
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
// 		******** ＤＢ Ver3.6修正箇所 *****************************************
		bean.setUpdateUserTs( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
// 		↓↓仕様追加による変更（2005.06.30）↓↓
		bean.setSinkiTorokuDt( rest.getString("sinki_toroku_dt") );
		bean.setHenkoDt( rest.getString("henko_dt") );
// 		↑↑仕様追加による変更（2005.06.30）↑↑
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
//		      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		sb.append("hanku_cd ");
		sb.append("bumon_cd ");
//		      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("syohin_cd ");
		sb.append(", ");
		sb.append("tenpo_cd ");
		sb.append(", ");
// 		↓↓仕様変更（2005.08.01）↓↓
// 		sb.append("yuko_dt ");
// 		sb.append(", ");
// 		sb.append("hanbai_st_dt ");
// 		sb.append(", ");
		sb.append("donyu_st_dt ");
		sb.append(", ");
		sb.append("donyu_ed_dt ");
		sb.append(", ");
// 		↑↑仕様変更（2005.08.01）↑↑
		sb.append("non_act_kb ");
		sb.append(", ");
		sb.append("haseimoto_kb ");
		sb.append(", ");
		sb.append("tanawari_patern ");
		sb.append(", ");
		sb.append("juki_nb ");
		sb.append(", ");
		sb.append("tana_nb ");
		sb.append(", ");
		sb.append("face_qt ");
		sb.append(", ");
		sb.append("min_tinretu_qt ");
		sb.append(", ");
		sb.append("max_tinretu_qt ");
		sb.append(", ");
		sb.append("base_zaiko_nisu_qt ");
		sb.append(", ");
		sb.append("insert_ts ");
		sb.append(", ");
		sb.append("insert_user_id ");
		sb.append(", ");
		sb.append("update_ts ");
		sb.append(", ");
// 		******** ＤＢ Ver3.6修正箇所 *****************************************
		sb.append("update_user_id ");
		sb.append(", ");
// 		sb.append("update_user_ts as update_user_id");
// 		sb.append(", ");
		sb.append("delete_fg ");
// 		↓↓仕様追加による変更（2005.06.30）↓↓
		sb.append(", ");
		sb.append("sinki_toroku_dt ");
		sb.append(", ");
		sb.append("henko_dt ");
// 		↑↑仕様追加による変更（2005.06.30）↑↑
		sb.append("from r_tanpinten_toriatukai ");
//	      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		wk = getWhereSqlStr(map, "hanku_cd", whereStr);
		wk = getWhereSqlStr(map, "bumon_cd", whereStr);
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "syohin_cd", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tenpo_cd", whereStr);
// ******** ＤＢ Ver3.6修正箇所 *****************************************
// 		sb.append(wk);
// 		whereStr = andStr;
// 		wk = getWhereSqlStr(map, "yuko_dt", whereStr);
// 		↓↓仕様変更（2005.08.01）↓↓
// 		sb.append(wk);
// 		whereStr = andStr;
// 		wk = getWhereSqlStr(map, "hanbai_st_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "donyu_st_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "donyu_ed_dt", whereStr);
// 		↑↑仕様変更（2005.08.01）↑↑
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "non_act_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "haseimoto_kb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "tanawari_patern", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "juki_nb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "tana_nb", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "face_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "min_tinretu_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "max_tinretu_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlOther(map, "base_zaiko_nisu_qt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "insert_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "update_ts", whereStr);
		sb.append(wk);
		whereStr = andStr;
// 		******** ＤＢ Ver3.6修正箇所 *****************************************
		wk = getWhereSqlStr(map, "update_user_id", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "delete_fg", whereStr);
		sb.append(wk);
		whereStr = andStr;
// 		↓↓仕様追加による変更（2005.06.30）↓↓
		wk = getWhereSqlStr(map, "sinki_toroku_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
		wk = getWhereSqlStr(map, "henko_dt", whereStr);
		sb.append(wk);
		whereStr = andStr;
// 		↑↑仕様追加による変更（2005.06.30）↑↑
		sb.append(" order by ");
//		      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		sb.append("hanku_cd ");
		sb.append("bumon_cd");
//		      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		sb.append(",");
		sb.append("syohin_cd");
		sb.append(",");
		sb.append("tenpo_cd");
// 		↓↓仕様変更（2005.08.01）↓↓
// 		sb.append(",");
// 		sb.append("yuko_dt");
// 		↑↑仕様変更（2005.08.01）↑↑
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + conv.convertWhereString( (String)map.get(Key) ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_like") != null && ((String)map.get(Key + "_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_bef_like") != null && ((String)map.get(Key + "_bef_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '%" + conv.convertWhereString( (String)map.get(Key + "_bef_like") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft_like") != null && ((String)map.get(Key + "_aft_like")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " like '" + conv.convertWhereString( (String)map.get(Key + "_aft_like") ) + "%'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= '" + conv.convertWhereString( (String)map.get(Key + "_bef") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= '" + conv.convertWhereString( (String)map.get(Key + "_aft") ) + "'");
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " = '" + (String)map.get(Key) + "'");
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_in") ),",","','") + "' )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( '" + replaceAll(conv.convertWhereString( (String)map.get(Key + "_not_in") ),",","','") + "' )");
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String whereStr = wstr;
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();



		// Keyに対するWHERE区
		if( map.get(Key + "_bef") != null && ((String)map.get(Key + "_bef")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " >= " + (String)map.get(Key + "_bef") );
			whereStr = andStr;
		}
		if( map.get(Key + "_aft") != null && ((String)map.get(Key + "_aft")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " <= " + (String)map.get(Key + "_aft") );
			whereStr = andStr;
		}
		if( map.get(Key) != null && ((String)map.get(Key)).trim().length() > 0  )
		{
			sb.append(whereStr);
			sb.append(Key + " = " + (String)map.get(Key));
			whereStr = andStr;
		}
		if( map.get(Key + "_in") != null && ((String)map.get(Key + "_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " in ( " + conv.convertWhereString( (String)map.get(Key + "_in") ) + " )");
			whereStr = andStr;
		}
		if( map.get(Key + "_not_in") != null && ((String)map.get(Key + "_not_in")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append(Key + " not in ( " + conv.convertWhereString( (String)map.get(Key + "_not_in") ) + " )");
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst300201_TanpinTenToriatukaiBean bean = (mst300201_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("r_tanpinten_toriatukai (");
//		      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			befKanma = true;
//			sb.append(" hanku_cd");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" bumon_cd");
		}
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
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
// 		↓↓仕様変更（2005.08.01）↓↓
// 		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
// 		{
// 			if( befKanma ) sb.append(","); else befKanma = true;
// 			sb.append(" yuko_dt");
// 		}
// 		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
// 		{
// 			if( befKanma ) sb.append(","); else befKanma = true;
// 			sb.append(" hanbai_st_dt");
// 		}
		if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_st_dt");
		}
		if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" donyu_ed_dt");
		}
// 		↑↑仕様変更（2005.08.01）↑↑
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" non_act_kb");
		}
		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" haseimoto_kb");
		}
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
// 	    if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
// 		{
// 			if( befKanma ) sb.append(","); else befKanma = true;
// 			sb.append(" tanawari_patern");
// 		}
// 		if( befKanma ) sb.append(",");

		if( befKanma ) sb.append(",");
		sb.append(" saishin_hacyu_dt");
		sb.append(",");
		sb.append(" saishin_shiire_dt");
		sb.append(",");
		sb.append(" saishin_uriage_dt");
		sb.append(",");
		sb.append(" non_act_soshin_dt");
		sb.append(",");
		sb.append(" tanawari_patern");
		sb.append(",");
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

		sb.append(" juki_nb");
		sb.append(",");
		sb.append(" tana_nb");
		sb.append(",");
		sb.append(" face_qt");
		sb.append(",");
		sb.append(" min_tinretu_qt");
		sb.append(",");
		sb.append(" max_tinretu_qt");
		sb.append(",");
		sb.append(" base_zaiko_nisu_qt");
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
// 			******** ＤＢ Ver3.6修正箇所 *****************************************
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" delete_fg");
		}
		// ↓↓仕様追加による変更（2005.06.29）↓↓
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" sinki_toroku_dt");
		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" henko_dt");
		}
		// ↑↑仕様追加による変更（2005.06.29）↑↑
		

		sb.append(")Values(");

//	      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append(" hanku_cd");
//		}

		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
		}
//	      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		
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
// 		↓↓仕様変更（2005.08.01）↓↓
// 		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
// 		{
// 			if( aftKanma ) sb.append(","); else aftKanma = true;
// 			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
// 		}
// 		if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
// 		{
// 			if( aftKanma ) sb.append(","); else aftKanma = true;
// 			sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
// 		}
		if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDonyuStDt() ) + "'");
		}
		if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDonyuEdDt() ) + "'");
		}
// 		↑↑仕様変更（2005.08.01）↑↑
		if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
		}
		if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHaseimotoKb() ) + "'");
		}
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
// 		if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
// 		{
// 			if( aftKanma ) sb.append(","); else aftKanma = true;
// 			sb.append("'" + conv.convertString( bean.getTanawariPatern() ) + "'");
// 		}
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		if( aftKanma ) sb.append(",");
// 2005.09.14 Y.Inaba 初期設定仕様変更に伴う修正 START
// 		sb.append( bean.getJukiNbString()+",");
// 		sb.append( bean.getTanaNbString()+",");
// 		sb.append( bean.getFaceQtString()+",");
// 		sb.append( bean.getMinTinretuQtString()+",");
// 		sb.append( bean.getMaxTinretuQtString()+",");
// 		sb.append( bean.getBaseZaikoNisuQtString());
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
		sb.append( bean.getSaihinHacyuDt()+",");
		sb.append( bean.getSaihinShiireDt()+",");
		sb.append( bean.getSaihinEriageDt()+",");
		sb.append( bean.getNonActSoshinDt()+",");
		sb.append( bean.getTanawariPatern()+",");
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		sb.append( bean.getJukiNb()+",");
		sb.append( bean.getTanaNb()+",");
		sb.append( bean.getFaceQt()+",");
		sb.append( bean.getMinTinretuQt()+",");
		sb.append( bean.getMaxTinretuQt()+",");
		sb.append( bean.getBaseZaikoNisuQt());
// 		2005.09.14 Y.Inaba 初期設定仕様変更に伴う修正 START
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
		// ↓↓仕様追加による変更（2005.06.29）↓↓
		if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
		}
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}
		// ↑↑仕様追加による変更（2005.06.29）↑↑
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst300201_TanpinTenToriatukaiBean bean = (mst300201_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("r_tanpinten_toriatukai set ");
		
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
		if(bean.getSyoriFlg().equals("delete")){
			
			if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
			{
				sb.append(" update_ts = ");
				sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			}
			if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" update_user_id = ");
				sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
			}
			if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" delete_fg = ");
				sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
			}
			
		}else if(bean.getSyoriFlg().equals("insert")){
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

// 			↓↓仕様変更（2005.08.01）↓↓
// 			if( bean.getHanbaiStDt() != null && bean.getHanbaiStDt().trim().length() != 0 )
// 			{
// 				befKanma = true;
// 				sb.append(" hanbai_st_dt = ");
// 				sb.append("'" + conv.convertString( bean.getHanbaiStDt() ) + "'");
// 			}
			if( bean.getDonyuStDt() != null && bean.getDonyuStDt().trim().length() != 0 )
			{
				befKanma = true;
				sb.append(" donyu_st_dt = ");
				sb.append("'" + conv.convertString( bean.getDonyuStDt() ) + "'");
			}
			if( bean.getDonyuEdDt() != null && bean.getDonyuEdDt().trim().length() != 0 )
			{
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" donyu_ed_dt = ");
				sb.append("'" + conv.convertString( bean.getDonyuEdDt() ) + "'");
			}
// 			↑↑仕様変更（2005.08.01）↑↑
			if( bean.getNonActKb() != null && bean.getNonActKb().trim().length() != 0 )
			{
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" non_act_kb = ");
				sb.append("'" + conv.convertString( bean.getNonActKb() ) + "'");
			}
// 	BUGNO-S074 2005.07.19 Sirius START
// 			↓↓仕様変更（2005.08.01）↓↓
// 			else {
// 				if( befKanma ) sb.append(","); else befKanma = true;
// 				sb.append(" non_act_kb = null ");
// 			}
// 			↑↑仕様変更（2005.08.01）↑↑
// 	BUGNO-S074 2005.07.19 Sirius END			
			if( bean.getHaseimotoKb() != null && bean.getHaseimotoKb().trim().length() != 0 )
			{
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" haseimoto_kb = ");
				sb.append("'" + conv.convertString( bean.getHaseimotoKb() ) + "'");
			}
			if( bean.getTanawariPatern() != null && bean.getTanawariPatern().trim().length() != 0 )
			{
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" tanawari_patern = ");
				sb.append("'" + conv.convertString( bean.getTanawariPatern() ) + "'");
			}
// 	BUGNO-S074 2005.07.19 Sirius START
// 			↓↓仕様変更（2005.08.01）↓↓
		 	else {
				if( befKanma ) sb.append(","); else befKanma = true;
					sb.append(" tanawari_patern = null ");
			}
// 			↑↑仕様変更（2005.08.01）↑↑
// 	BUGNO-S074 2005.07.19 Sirius END			
			if( befKanma ) sb.append(",");
// 			sb.append(" juki_nb = "+ bean.getJukiNbString()+",");
// 			sb.append(" tana_nb = "+ bean.getTanaNbString()+",");
// 			sb.append(" face_qt = "+ bean.getFaceQtString()+",");
// 			sb.append(" min_tinretu_qt = "+ bean.getMinTinretuQtString()+",");
// 			sb.append(" max_tinretu_qt = "+ bean.getMaxTinretuQtString()+",");
// 			sb.append(" base_zaiko_nisu_qt = "+ bean.getBaseZaikoNisuQtString()+",");
			sb.append(" juki_nb = "+ bean.getJukiNb()+",");
			sb.append(" tana_nb = "+ bean.getTanaNb()+",");
			sb.append(" face_qt = "+ bean.getFaceQt()+",");
			sb.append(" min_tinretu_qt = "+ bean.getMinTinretuQt()+",");
			sb.append(" max_tinretu_qt = "+ bean.getMaxTinretuQt()+",");
			sb.append(" base_zaiko_nisu_qt = "+ bean.getBaseZaikoNisuQt()+",");
// 			BUGNO-S074 2005.07.19 Sirius START
			sb.append(" non_act_soshin_dt = null ");
			sb.append(",");
			sb.append(" saishin_hacyu_dt = null ");
			sb.append(",");
			sb.append(" saishin_shiire_dt = null ");
			sb.append(",");
			sb.append(" saishin_uriage_dt = null ");
// 	BUGNO-S074 2005.07.19 Sirius END
			if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" insert_ts = ");
				sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
			}
			if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" insert_user_id = ");
				sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
			}
			if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" update_ts = ");
				sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			}
			if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
			{
				sb.append(",");
// 				******** ＤＢ Ver3.6修正箇所 *****************************************
				sb.append(" update_user_id = ");
				sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
			}
			if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" delete_fg = ");
				sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
			}
			// ↓↓仕様追加による変更（2005.06.29）↓↓
			if( bean.getSinkiTorokuDt() != null && bean.getSinkiTorokuDt().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" sinki_toroku_dt = ");
				sb.append("'" + conv.convertString( bean.getSinkiTorokuDt() ) + "'");
			}
			if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
			{
				sb.append(",");
				sb.append(" henko_dt = ");
				sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
			}
			// ↑↑仕様追加による変更（2005.06.29）↑↑
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
		}
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑


		sb.append(" WHERE");

//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
//		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" hanku_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
//		}
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" bumon_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
		}
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

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
// 		↓↓仕様変更（2005.08.01）↓↓
// 		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
// 		{
// 			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
// 			sb.append(" yuko_dt = ");
// 			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
// 		}
// 		↑↑仕様変更（2005.08.01）↑↑
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
// 		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst300201_TanpinTenToriatukaiBean bean = (mst300201_TanpinTenToriatukaiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("r_tanpinten_toriatukai where ");
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓	
//		sb.append(" hanku_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" bumon_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getBumonCd() ) + "'");
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" syohin_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
		sb.append(" AND");
		sb.append(" tenpo_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
// 		******** ＤＢ Ver3.6修正箇所 *****************************************
// 		sb.append(" AND");
// 		sb.append(" yuko_dt = ");
// 		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
		return sb.toString();
	}

//	 ADD by Tanigawa 2006/10/31 障害票№0227対応 START
	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getCheckSelectSqlForPreparedStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT              ");
		sb.append("  bumon_cd           ");
		sb.append(" ,syohin_cd          ");
		sb.append(" ,tenpo_cd           ");
		sb.append(" ,donyu_st_dt        ");
		sb.append(" ,donyu_ed_dt        ");
		sb.append(" ,non_act_kb         ");
		sb.append(" ,haseimoto_kb       ");
		sb.append(" ,tanawari_patern    ");
		sb.append(" ,juki_nb            ");
		sb.append(" ,tana_nb            ");
		sb.append(" ,face_qt            ");
		sb.append(" ,min_tinretu_qt     ");
		sb.append(" ,max_tinretu_qt     ");
		sb.append(" ,base_zaiko_nisu_qt ");
		sb.append(" ,insert_ts          ");
		sb.append(" ,insert_user_id     ");
		sb.append(" ,update_ts          ");
		sb.append(" ,update_user_id     ");
		sb.append(" ,delete_fg          ");
		sb.append(" ,sinki_toroku_dt    ");
		sb.append(" ,henko_dt           ");
		sb.append(" FROM r_tanpinten_toriatukai ");
		sb.append(" WHERE               ");
		sb.append("      BUMON_CD = ?   ");	//1
		sb.append("  AND SYOHIN_CD= ?   ");	//2
		sb.append("  AND TENPO_CD = ?   ");	//3

		return sb.toString();
	}
	
	public String getInsertSqlForPreparedStatement(){

		StringBuffer sb = new StringBuffer("");

		sb.append("insert into ");
		sb.append("r_tanpinten_toriatukai (");
		sb.append("  bumon_cd");			//1
		sb.append(" ,syohin_cd");			//2
		sb.append(" ,tenpo_cd");			//3
		sb.append(" ,donyu_st_dt");			//4
		sb.append(" ,donyu_ed_dt");			//5
		sb.append(" ,non_act_kb");			//6
		sb.append(" ,haseimoto_kb");		//7
//		sb.append(" ,saishin_hacyu_dt");
//		sb.append(" ,saishin_shiire_dt");
//		sb.append(" ,saishin_uriage_dt");
//		sb.append(" ,non_act_soshin_dt");
//		sb.append(" ,tanawari_patern");
//		sb.append(" ,juki_nb");
//		sb.append(" ,tana_nb");
//		sb.append(" ,face_qt");
//		sb.append(" ,min_tinretu_qt");
//		sb.append(" ,max_tinretu_qt");
//		sb.append(" ,base_zaiko_nisu_qt");
		sb.append(" ,insert_ts");			//8
		sb.append(" ,insert_user_id");		//9
		sb.append(" ,update_ts");			//10
		sb.append(" ,update_user_id");		//11
		sb.append(" ,delete_fg");			//12
		sb.append(" ,sinki_toroku_dt");		//13
		sb.append(" ,henko_dt");			//14

		sb.append(")Values(");

		sb.append("  ? ");					//1
		sb.append(" ,? ");					//2
		sb.append(" ,? ");					//3
		sb.append(" ,? ");					//4
		sb.append(" ,? ");					//5
		sb.append(" ,? ");					//6
		sb.append(" ,? ");					//7
//		sb.append( bean.getSaihinHacyuDt()+",");
//		sb.append( bean.getSaihinShiireDt()+",");
//		sb.append( bean.getSaihinEriageDt()+",");
//		sb.append( bean.getNonActSoshinDt()+",");
//		sb.append( bean.getTanawariPatern()+",");
//		sb.append( bean.getJukiNb()+",");
//		sb.append( bean.getTanaNb()+",");
//		sb.append( bean.getFaceQt()+",");
//		sb.append( bean.getMinTinretuQt()+",");
//		sb.append( bean.getMaxTinretuQt()+",");
//		sb.append( bean.getBaseZaikoNisuQt());
		sb.append(" ,? ");					//8
		sb.append(" ,? ");					//9
		sb.append(" ,? ");					//10
		sb.append(" ,? ");					//11
		sb.append(" ,? ");					//12
		sb.append(" ,? ");					//13
		sb.append(" ,? ");					//14
		sb.append(")");

		return sb.toString();
	}
	
	public String getUpdateSqlForPreparedStatement(){

		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append(" r_tanpinten_toriatukai set ");
		sb.append("      henko_dt = ?      ");	// add by kema 06.11.10
		sb.append("     ,update_ts = ?      ");
		sb.append("     ,update_user_id = ? ");
		sb.append("     ,delete_fg =      ? ");
		sb.append(" WHERE");
		sb.append("      bumon_cd  = ? ");
		sb.append("  AND syohin_cd = ? ");
		sb.append("  AND tenpo_cd  = ? ");

		return sb.toString();	
	}
	
	public String getDeleteSqlForPreparedStatement(){

		StringBuffer sb = new StringBuffer("");
		sb.append("delete from ");
		sb.append("r_tanpinten_toriatukai where ");
		sb.append(" bumon_cd = ? ");
		sb.append(" AND");
		sb.append(" syohin_cd = ? ");
		sb.append(" AND");
		sb.append(" tenpo_cd = ? ");
			
//		deletePS.setString(1,conv.convertWhereString( bean.getBumonCd() ));
//		deletePS.setString(2,conv.convertWhereString( bean.getSyohinCd() ));
//		deletePS.setString(3,conv.convertWhereString( bean.getTenpoCd() ));
		
		return sb.toString();
	}
//	 ADD by Tanigawa 2006/10/31 障害票№0227対応  END 

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
