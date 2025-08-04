/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.master.util.RMSTDATEUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
//↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
import mdware.common.util.StringUtility;
//↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
public class mst270101_SyokaiDonyuJyukyoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	private int insertFlg = 0;
	public mst270101_SyokaiDonyuJyukyoDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
	}
	public mst270101_SyokaiDonyuJyukyoDM(int flag)
	{
		super( mst000101_ConstDictionary.CONNECTION_STR );
		this.insertFlg = flag;
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
		mst270101_SyokaiDonyuJyukyoBean bean = new mst270101_SyokaiDonyuJyukyoBean();
//		bean.setHankuCd( rest.getString("hanku_cd") );
		
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		bean.setHachunoCd( rest.getString("hachuno_cd") );
		
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNm( rest.getString("tenpo_nm") );
		bean.setSuryoQt( rest.getString("suryo_qt") );
		bean.setSuryoQtOld( rest.getString("suryo_qt") );
		bean.setGentankaVl( rest.getString("gentanka_vl") );
		bean.setBaitankaVl( rest.getString("baitanka_vl") );
		bean.setHachutaniQt( rest.getString("hachutani_qt") );
		bean.setHatyuDt( rest.getString("hatyu_dt") );
		bean.setNohinDt( rest.getString("nohin_dt") );
		bean.setHanbaiStDt(rest.getString("hanbai_st_dt") );
		bean.setHanbaiEdDt(rest.getString("hanbai_ed_dt") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setChecked( rest.getString("checked") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setAnbun_rt( rest.getString("anbun_rt") );
//		↓↓2006/03/02 kim START
//		bean.setAutoHachuKb(rest.getString("jihatu_teisi_kb"));
//		↑↑2006/03/02 kim END

		//販区コードが存在する場合のみデータが存在する
//		if(rest.getString("hanku_cd") != null && !rest.getString("hanku_cd").equals("")){
			bean.setCreateDatabase();
//		}

		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
//	****** DB Var3.6 修正箇所 *****　確認！変更箇所あり　**********************			
	public String getSelectSql( Map map )
	{
		
		//--------------------------
		//SQL生成
		//--------------------------
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		String syorikb = (String)map.get("syorikb") ;
		
		if (syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			sb.append("SELECT ");
//			↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("  r_tenpo.tenpo_cd, ");
//			sb.append("  r_tenpo.kanji_rn as tenpo_nm, ");
			sb.append("  r_tenpor.tenpo_cd, ");
			sb.append("  r_tenpor.kanji_rn as tenpo_nm, ");
//			↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑
//          ↓↓移植（2006.05.11）↓↓
//			sb.append("  cast(null as char) as hanku_cd, ");
			sb.append("  cast(null as char) as hachuno_cd, ");
			sb.append("  cast(null as char) as syohin_cd, ");
			sb.append("  cast(null as char) as theme_cd, ");
			sb.append("  cast(null as char) as suryo_qt, ");
			sb.append("  cast(null as char) as hachutani_qt, ");
			sb.append("  cast(null as char) as gentanka_vl, ");
			sb.append("  cast(null as char) as baitanka_vl, ");
			sb.append("  cast(null as char) as hatyu_dt, ");
			sb.append("  cast(null as char) as nohin_dt, ");
			sb.append("  cast(null as char) as hanbai_st_dt, ");
			sb.append("  cast(null as char) as hanbai_ed_dt, ");
			sb.append("  cast(null as char) as insert_ts, ");
			sb.append("  cast(null as char) as insert_user_id, ");
			sb.append("  cast(null as char) as update_ts, ");
			sb.append("  cast(null as char) as update_user_id, ");
//			↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("   syokai.delete_fg   as delete_fg, ");
			sb.append("  cast(null as char) as delete_fg, ");
//			↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
			sb.append("  '' as checked, ");
			sb.append("  r_tengroup.anbun_rt as anbun_rt, ");
			sb.append("'0" + conv.convertWhereString((String)map.get("tx_bumon_cd")) + "'  as bumon_cd ");
//          ↑↑移植（2006.05.11）↑↑
			//  ↓↓2006/03/02 kim START
//			sb.append(" , ''	as jihatu_teisi_kb	");
			//　↑↑2006/03/02 kim END
			sb.append("FROM ");
			sb.append("  r_tengroup INNER JOIN ");
			sb.append("	   (SELECT ");
//			↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("       tenpo_cd, ");
//			sb.append("       kanji_rn ");
			sb.append("       r_tenpow.tenpo_cd, ");
			sb.append("       r_tenpow.kanji_rn ");
//			↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑
			sb.append("     FROM ");
//			↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("       r_tenpo ");
			sb.append("       r_tenpo r_tenpow ");
//			↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑
			sb.append("     WHERE ");
//			↓↓2006.09.18 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("       tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' AND ");
//			sb.append("       heiten_dt > '"+ RMSTDATEUtil.getMstDateDt() + "' ");
//			sb.append("    ) r_tenpo ON r_tengroup.tenpo_cd = r_tenpo.tenpo_cd ");
			sb.append("       r_tenpow.tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' AND ");
			sb.append("       r_tenpow.heiten_dt > '"+ RMSTDATEUtil.getMstDateDt() + "' ");
			sb.append("    ) r_tenpor ON r_tengroup.tenpo_cd = r_tenpor.tenpo_cd ");
//			↑↑2006.09.18 H.Yamamoto カスタマイズ修正↑↑
//			↓↓2006.10.04 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("  left outer join r_syokaidonyu  syokai");
//			sb.append("    on  r_tengroup.tenpo_cd = syokai.tenpo_cd");
//			sb.append("    and  r_tengroup.bumon_cd = syokai.bumon_cd");
//			sb.append("    and  syokai.delete_fg = '0' ");
//			sb.append("    and  syokai.bumon_cd =  '0" + conv.convertWhereString((String)map.get("tx_bumon_cd")) + "' ");
//			sb.append("    and  syokai.syohin_cd =  '" + conv.convertWhereString((String)map.get("ct_syohin_cd")) + "' ");
//			↑↑2006.10.04 H.Yamamoto カスタマイズ修正↑↑
			sb.append("WHERE ");
//			sb.append("  r_tengroup.l_gyosyu_cd = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS_CLOTHES + "' AND ");
//			sb.append("  r_tengroup.l_gyosyu_cd = '" + conv.convertWhereString((String)map.get("h_l_gyosyucd")) + "' AND ");
			sb.append("  r_tengroup.yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' AND ");
			sb.append("  r_tengroup.groupno_cd = '" + conv.convertWhereString((String)map.get("groupno_cd")) + "' AND ");
			sb.append("  r_tengroup.bumon_cd = '0" + conv.convertWhereString((String)map.get("tx_bumon_cd")) + "' AND ");
			sb.append("  r_tengroup.delete_fg = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "' ");
			sb.append("ORDER BY ");
			sb.append("  r_tengroup.rank_nb ");
			
		} else {
		
		sb.append("select \n ");
//		sb.append("	syokai.hanku_cd ");
//		sb.append("	, \n ");
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append("	syokai.siiresaki_cd ");
//		sb.append("	, \n ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append("	syokai.hachuno_cd ");
		sb.append("	, \n ");
		
		sb.append("	syokai.syohin_cd ");
		sb.append("	, \n ");
		sb.append("	syokai.theme_cd ");
		sb.append("	, \n ");
		sb.append("	tenpo.tenpo_cd ");
		sb.append("	, \n ");
		sb.append("	tenpo.kanji_rn as tenpo_nm ");
		sb.append("	, \n ");
		sb.append("	syokai.suryo_qt ");
		sb.append("	, \n ");
		sb.append("	syokai.gentanka_vl ");
		sb.append("	, \n ");
		sb.append("	syokai.baitanka_vl ");
		sb.append("	, \n ");
		sb.append("	syokai.hachutani_qt ");
		sb.append("	, \n ");
		sb.append("	syokai.hatyu_dt ");
		sb.append("	, \n ");
		sb.append("	syokai.nohin_dt ");
		sb.append("	, \n ");
		sb.append("	trim(syokai.hanbai_st_dt) hanbai_st_dt ");
		sb.append("	, \n ");
		sb.append("	trim(syokai.hanbai_ed_dt) hanbai_ed_dt ");
		sb.append("	, \n ");
		sb.append("	syokai.insert_ts ");
		sb.append("	, \n ");
		sb.append("	syokai.insert_user_id ");
		sb.append("	, \n ");
		sb.append("	syokai.update_ts ");
		sb.append("	, \n ");
		sb.append("	syokai.update_user_id ");
		sb.append("	, \n ");
		sb.append("	syokai.delete_fg, ");
		sb.append("  '' as checked, ");
		sb.append("  '' as anbun_rt, ");
		sb.append("'0" + conv.convertWhereString((String)map.get("tx_bumon_cd")) + "'  as bumon_cd ");
		//  ↓↓2006/03/02 kim START
//		sb.append("	, \n ");
//		sb.append("	syokai.jihatu_teisi_kb \n");
		//　↑↑2006/03/02 kim END
		sb.append("from \n ");

		//処理状況＝新規の場合
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			sb.append("( \n ");
			sb.append(" select \n ");
			sb.append(" tenpo.tenpo_cd ");
			sb.append("	, \n ");
			sb.append(" syokai.hanku_cd ");
//			****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//			sb.append("	, \n ");
//			sb.append(" syokai.siiresaki_cd ");

//			****** DB Var3.6 修正箇所 *****発注コード追加**********************
			sb.append("	syokai.hachuno_cd ");
			sb.append("	, \n ");
			
			sb.append(" syokai.syohin_cd ");
			sb.append("	, \n ");
			sb.append(" syokai.theme_cd ");
			sb.append("	, \n ");
			sb.append(" syokai.suryo_qt ");
			sb.append("	, \n ");
			sb.append(" syokai.gentanka_vl ");
			sb.append("	, \n ");
			sb.append(" syokai.baitanka_vl ");
			sb.append("	, \n ");
			sb.append(" syokai.hachutani_qt ");
			sb.append("	, \n ");
			sb.append(" syokai.hatyu_dt ");
			sb.append("	, \n ");
			sb.append(" syokai.nohin_dt ");
			sb.append("	, \n ");
			sb.append(" syokai.insert_ts ");
			sb.append("	, \n ");
			sb.append(" syokai.insert_user_id ");
			sb.append("	, \n ");
			sb.append(" syokai.update_ts ");
			sb.append("	, \n ");
			sb.append(" syokai.update_user_id ");
			sb.append("	, \n ");
			sb.append(" syokai.delete_fg ");
			sb.append("	, \n ");
//          ↓↓移植（2006.05.11）↓↓
			sb.append(" ( ");
			sb.append(" 　case syokai.hanku_cd ");
			sb.append(" when null then 0 ");
			sb.append(" else 1 ");
			sb.append(" end　) as exist_flg \n ");//初回導入マスタにデータが存在する場合は1とし存在しない場合は0とする
			sb.append(" from \n ");
			sb.append(" r_tengroup tenpo left outer join r_syokaidonyu syokai ");
			sb.append(" on tenpo.tenpo_cd = syokai.tenpo_cd \n ");
			sb.append("  and \n ");
//			****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************						
			sb.append(" syokai.hanku_cd = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "' ");
			sb.append("  and \n ");
			
//			****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//			sb.append("  and \n ");
//			sb.append(" syokai.siiresaki_cd(+) = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "' ");
//			****** DB Var3.6 修正箇所 *****発注コード追加**********************
			sb.append(" syokai.hachuno_cd = '" + conv.convertWhereString((String)map.get("hachuno_cd")) + "' ");
			sb.append("  and \n ");
////			****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//			sb.append(" syokai.syohin_cd(+) = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
//			sb.append("  and \n ");
//			sb.append(" syokai.theme_cd(+) = '" + conv.convertWhereString((String)map.get("theme_cd")) + "' ");
//			sb.append("  and \n ");
			sb.append(" syokai.delete_fg = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "' \n ");
//          ↑↑移植（2006.05.11）↑↑		
			sb.append(" where \n ");
//			sb.append(" tenpo.l_gyosyu_cd = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS_HOUSE + "' ");
			sb.append(" tenpo.l_gyosyu_cd = '" + conv.convertWhereString((String)map.get("h_l_gyosyucd")) + "' ");
			sb.append("  and \n ");
			sb.append(" tenpo.yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");				
			sb.append("  and \n ");
			sb.append(" tenpo.groupno_cd = '" + conv.convertWhereString((String)map.get("groupno_cd")) + "' ");
			sb.append("  and \n ");
			sb.append(" tenpo.delete_fg = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "' ");
			sb.append("	) syokai \n ");

		//処理状況＝修正・削除・照会の場合
		} else {
			sb.append("	( \n ");
			sb.append("	select \n ");
			sb.append("	 * \n ");
			sb.append("	from \n ");
			sb.append("	 r_syokaidonyu syokai \n ");
			sb.append("	where \n ");
//			****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//			sb.append(" syokai.hanku_cd = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "' ");
//			sb.append("  and \n ");
			
//			****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//			sb.append("  and \n ");
//			sb.append(" syokai.siiresaki_cd = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "' ");

//			****** DB Var3.6 修正箇所 *****発注コード追加**********************
//			↓↓仕様追加（2005.07.12）↓↓
//			sb.append(" syokai.hachuno_cd(+) = '" + conv.convertWhereString((String)map.get("hachuno_cd")) + "' ");
//			sb.append("  and \n ");
//			↑↑仕様追加（2005.07.12）↑↑
			
//			****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//			sb.append(" syokai.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
//			sb.append("  and \n ");
//			sb.append(" syokai.theme_cd = '" + conv.convertWhereString((String)map.get("theme_cd")) + "' ");
//			sb.append("  and \n ");

//			↓↓仕様追加（2005.07.12）↓↓
//			sb.append(" syokai.hanku_cd = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "' ");
//			sb.append("  and \n ");
			sb.append(" syokai.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
			sb.append("  and \n ");
			sb.append(" syokai.bumon_cd = '0" + conv.convertWhereString((String)map.get("tx_bumon_cd")) + "' ");
			sb.append("  and \n ");
//			↑↑仕様追加（2005.07.12）↑↑
			
			sb.append(" syokai.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n ");
			
//			↓↓仕様追加（2005.07.12）↓↓
			if (!syorikb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) {
				sb.append(" and \n ");
//				↓↓バグ修正（2005.08.25）↓↓
//				sb.append(" hatyu_dt > '" + RMSTDATEUtil.getMstDateDt() + "' \n ");
				sb.append(" hatyu_dt >= '" + RMSTDATEUtil.getMstDateDt() + "' \n ");
//				↑↑バグ修正（2005.08.25）↑↑
			}
//			↑↑仕様追加（2005.07.12）↑↑

			//更新時のチェック時の取得用
			String tenpo_cd =  conv.convertWhereString((String)map.get("tenpo_cd"));
			if(tenpo_cd != null && !tenpo_cd.equals("")){
				sb.append("  and \n ");
				sb.append(" syokai.tenpo_cd = '" + tenpo_cd + "' \n ");
			}
			String hachuno_cd =  conv.convertWhereString((String)map.get("hachuno_cd"));
			if(hachuno_cd != null && !hachuno_cd.equals("")){
				sb.append("  and \n ");
				sb.append(" syokai.hachuno_cd = '" + hachuno_cd + "' \n ");
			}

			sb.append("	) syokai \n ");
		}
//		↓↓仕様追加（2005.07.12）↓↓
		sb.append("	 inner join \n ");
		sb.append("	   (select \n ");
		sb.append("       tenpo_cd, \n ");
		sb.append("       kanji_rn \n ");
		sb.append("     from \n ");
		sb.append("       r_tenpo \n ");
		sb.append("     where \n ");
		sb.append("       tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' and \n ");
		sb.append("       heiten_dt > '"+ RMSTDATEUtil.getMstDateDt() + "' \n ");
		sb.append("    ) tenpo ON syokai.tenpo_cd = tenpo.tenpo_cd \n ");
//		sb.append("	,r_tenpo tenpo \n ");
//		↑↑仕様追加（2005.07.12）↑↑

		sb.append("where \n ");
		sb.append(" syokai.tenpo_cd = tenpo.tenpo_cd \n ");

		//処理状況＝新規の場合、初回導入マスタに存在しない店舗のみとする
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			sb.append(" and syokai.exist_flg = 0 \n ");
		}
		sb.append("order by syokai.hachuno_cd, syokai.tenpo_cd ");

		}

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		mst270101_SyokaiDonyuJyukyoBean bean = (mst270101_SyokaiDonyuJyukyoBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("insert into \n ");
		sb.append("r_syokaidonyu ( ");
//		sb.append(" hanku_cd ");
		sb.append(" bumon_cd ");
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(" ,siiresaki_cd ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append("	,hachuno_cd ");
		
		sb.append(" ,syohin_cd ");
//		sb.append(" ,theme_cd ");
		sb.append(" ,tenpo_cd ");
		sb.append(" ,suryo_qt ");
		sb.append(" ,hachutani_qt ");
		sb.append(" ,gentanka_vl ");
		sb.append(" ,baitanka_vl ");
//		sb.append(" ,hachutani_qt ");
		sb.append(" ,hatyu_dt ");
		sb.append(" ,nohin_dt ");
		sb.append(" ,hanbai_st_dt ");
		sb.append(" ,hanbai_ed_dt ");
//		↓↓仕様追加（2005.07.12）↓↓
//		sb.append(" ,color_cd ");
//		sb.append(" ,size_cd ");
//		sb.append(" ,asort_pattern_cd ");
//		↑↑仕様追加（2005.07.12）↑↑
		sb.append(" ,insert_ts ");
		sb.append(" ,insert_user_id ");
		sb.append(" ,update_ts ");
		sb.append(" ,update_user_id ");
		sb.append(" ,delete_fg ");
		//  ↓↓2006/03/02 kim START
//		sb.append("	,JIHAtu_teisi_kb ");
		sb.append("	,toroku_moto_kb ");
		//　↑↑2006/03/02 kim END
		sb.append(") values( \n ");

//		sb.append("'" + conv.convertString( bean.getHankuCd()
//		 ) + "' ");
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(",'" + conv.convertString( bean.getSiiresakiCd() ) + "' ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append("'" + conv.convertString( bean.getBumonCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getHachunoCd() ) + "' ");
		
		sb.append(",'" + conv.convertString( bean.getSyohinCd() ) + "' ");
//		sb.append(",'" + conv.convertString( bean.getThemeCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getTenpoCd() ) + "' ");
		sb.append("," + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSuryoQt()).equals("")?"null": mst000401_LogicBean.chkNullString(bean.getSuryoQt())) );
		sb.append("," + conv.convertString( mst000401_LogicBean.chkNullString(bean.getHachutaniQt()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getHachutaniQt())) );
		sb.append("," + conv.convertString( mst000401_LogicBean.chkNullString(bean.getGentankaVl()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getGentankaVl())) );
		sb.append("," + conv.convertString( mst000401_LogicBean.chkNullString(bean.getBaitankaVl()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getBaitankaVl())) );
//		sb.append(",'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getHachutaniQt()) ) + "' ");
		sb.append(",'" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHatyuDt()) ) + "' ");
		sb.append(",'" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getNohinDt()) ) + "' ");
		sb.append(",'" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHanbaiStDt()) ) + "' ");
		sb.append(",'" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHanbaiEdDt()) ) + "' ");
//		↓↓仕様変更（2005.07.07）↓↓
//		sb.append(",' ' ");
//		sb.append(",' ' ");
//		sb.append(",' ' ");
//		↑↑仕様変更（2005.07.07）↑↑
//      ↓↓移植（2006.05.11）↓↓
		try {
			sb.append(",'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		}catch (SQLException e){
			e.printStackTrace();
		}
//      ↑↑移植（2006.05.11）↑↑		
		sb.append(",'" + conv.convertString( bean.getInsertUserId() ) + "' ");
		try {
			sb.append(",'" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		}catch (SQLException e){
			e.printStackTrace();
		}
//      ↑↑移植（2006.05.11）↑↑		
		sb.append(",'" + conv.convertString( bean.getInsertUserId() ) + "' ");
		sb.append(",'" + "0" + "' ");
		sb.append(",'" + "1" +"' ");
		sb.append(") ");

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	
//	****** DB Var3.6 修正箇所 *****　確認！変更箇所あり　**********************			
	public String getUpdateSql( Object beanMst )
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		mst270101_SyokaiDonyuJyukyoBean bean = (mst270101_SyokaiDonyuJyukyoBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("r_syokaidonyu set \n ");

		sb.append(" suryo_qt = ");
		sb.append(conv.convertString( mst000401_LogicBean.chkNullString(bean.getSuryoQt()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getSuryoQt()) ) );

		sb.append(" ,gentanka_vl = ");
		sb.append( conv.convertString( mst000401_LogicBean.chkNullString(bean.getGentankaVl()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getGentankaVl()) ) );

		sb.append(" ,baitanka_vl = ");
		sb.append( conv.convertString( mst000401_LogicBean.chkNullString(bean.getBaitankaVl()) .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getBaitankaVl()) ) );

		sb.append(" ,hachutani_qt = ");
		sb.append( conv.convertString( mst000401_LogicBean.chkNullString(bean.getHachutaniQt())  .equals("")?"null": mst000401_LogicBean.chkNullString(bean.getHachutaniQt())) );

		sb.append(" ,hatyu_dt = ");
		sb.append(" '" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHatyuDt()) ) + "' ");

		sb.append(" ,nohin_dt = ");
		sb.append(" '" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getNohinDt()) ) + "' ");

		sb.append(" ,hanbai_st_dt = ");
		sb.append(" '" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHanbaiStDt()) ) + "' ");

		sb.append(" ,hanbai_ed_dt = ");
		sb.append(" '" + conv.convertString(  mst000401_LogicBean.chkNullString(bean.getHanbaiEdDt()) ) + "' ");
		
		sb.append(" ,update_ts = ");
//      ↓↓移植（2006.05.11）↓↓
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		}catch (SQLException e){
			e.printStackTrace();
		}
//      ↑↑移植（2006.05.11）↑↑

		sb.append(" ,update_user_id = ");
		sb.append(" '" + conv.convertString( bean.getUpdateUserId() ) + "' ");

		sb.append(" ,delete_fg = ");
		sb.append(" '" + conv.convertString( bean.getDeleteFg() ) + "' \n ");
		
		//  ↓↓2006/03/03 kim START
//		sb.append(" ,jihatu_teisi_kb = ");
//				sb.append(" '" + conv.convertString( bean.getAutoHachuKb() ) + "' \n ");
		//　↑↑2006/03/03 kim END
		
		//削除フラグ=1データ存在時の新規作成時の場合
		if(insertFlg == 1){

			sb.append(" ,insert_ts = ");
//	        ↓↓移植（2006.05.11）↓↓
			try {
				sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
			}catch (SQLException e){
				e.printStackTrace();
			}
//	        ↑↑移植（2006.05.11）↑↑

			sb.append(" ,insert_user_id = ");
			sb.append(" '" + conv.convertString( bean.getInsertUserId() ) + "' ");

		}
		
//		↓↓仕様変更（2005.07.07）↓↓
//		sb.append(" ,theme_cd = ");
//		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getThemeCd()) ) + "'");
//		↑↑仕様変更（2005.07.07）↑↑

		sb.append("where \n ");
//		****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//		sb.append(" hanku_cd = ");
//		sb.append(" '" + conv.convertString( bean.getHankuCd() ) + "' ");
		
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(" and siiresaki_cd = ");
//		sb.append(" '" + conv.convertString( bean.getSiiresakiCd() ) + "' ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append(" hachuno_cd = ");
		sb.append(" '" + conv.convertString( bean.getHachunoCd() ) + "' ");
		
//		****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//		sb.append(" and syohin_cd = ");
//		sb.append(" '" + conv.convertString( bean.getSyohinCd() ) + "' ");
//
//		sb.append(" and theme_cd = ");
//		sb.append(" '" + conv.convertString( bean.getThemeCd() ) + "' ");
		sb.append(" and syohin_cd = ");
		sb.append(" '" + conv.convertString( bean.getSyohinCd() ) + "' ");
		sb.append(" and bumon_cd = ");
		sb.append(" '" + conv.convertString( bean.getBumonCd() ) + "' ");
		sb.append(" and tenpo_cd = ");
		sb.append(" '" + conv.convertString( bean.getTenpoCd() ) + "' ");
		
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mst270101_SyokaiDonyuJyukyoBean bean = (mst270101_SyokaiDonyuJyukyoBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("delete from ");
		sb.append("r_syokaidonyu  \n ");
		sb.append("where \n ");
		sb.append(" hachuno_cd = ");
		sb.append(" '" + conv.convertString( bean.getHachunoCd() ) + "' ");
		sb.append(" and syohin_cd = ");
		sb.append(" '" + conv.convertString( bean.getSyohinCd() ) + "' ");
		sb.append(" and bumon_cd = ");
		sb.append(" '" + conv.convertString( bean.getBumonCd() ) + "' ");
		sb.append(" and tenpo_cd = ");
		sb.append(" '" + conv.convertString( bean.getTenpoCd() ) + "' ");
		
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

//	↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
	public String getSiiresakiKanriCd(mst000101_DbmsBean db, String bumonCd, String syohinCd, String yukoDt) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		String tenSiiresakiKanriCd = "";

		sql.append("SELECT ");
		sql.append("	 rs.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd ");
		sql.append("FROM ");
		sql.append("	 r_syohin rs ");
		sql.append("WHERE ");
		sql.append("	 rs.bumon_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.syohin_cd = '").append(syohinCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '").append(yukoDt).append("') ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			if (rs.getString("ten_siiresaki_kanri_cd") != null) {
				tenSiiresakiKanriCd = rs.getString("ten_siiresaki_kanri_cd").trim();
			}
			rs.close();
		}

		return tenSiiresakiKanriCd;

	}

	public boolean isSiiresakiTenpo(mst000101_DbmsBean db, String systemKb, String bumonCd, String tenSiiresakiKanriCd, String tenpoCd) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		if (!systemKb.equals(mst000611_SystemKbDictionary.G.getCode()) && !systemKb.equals(mst000611_SystemKbDictionary.F.getCode())) {
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("9999")) {
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("")) {
			return false;
		}

		sql.append("SELECT ");
		sql.append("	 rts.tenpo_cd as tenpo_cd ");
		sql.append("FROM ");
		sql.append("	 r_tenbetu_siiresaki rts ");
		sql.append("WHERE ");
		sql.append("	 rts.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.kanri_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.ten_siiresaki_kanri_cd = '").append(tenSiiresakiKanriCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.tenpo_cd = '").append(tenpoCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			rs.close();
			return true;
		} else {
			return false;
		}

	}
//	↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑

//add start by nakazawa 2006/11/09 障害報告233対応
	/**
	 * <P>上のisSiiresakiTenpoと同様の条件で店舗一覧を取得しHashMapにして保持する</P>
	 * <P>ＳＱＬをＤＢへ投げる回数を減らすことを目的とする</P>
	 * @author Administrator
	 */
	private HashMap hm = new HashMap();
	private boolean searchFg = false;
	
	public boolean getSiiresakiTenpoItiran
	( mst000101_DbmsBean db, String systemKb, String bumonCd, String tenSiiresakiKanriCd, String tenpoCd ) throws SQLException
	{
		//無条件でtrueを返す仕様のチェック(isSiiresakiTenpoよりコピー)
		if (!systemKb.equals(mst000611_SystemKbDictionary.G.getCode()) 
		&& !systemKb.equals(mst000611_SystemKbDictionary.F.getCode())) 
		{
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("9999")) {
			return true;
		}
		
		if (tenSiiresakiKanriCd.equals("")) {
			return false;
		}
		//チェックここまで
		
		String			nm	= "";
		ResultSet 		rs 	= null;
		StringBuffer 	sb 	= new StringBuffer();
		
		//初回メソッド呼び出し時のみDBにアクセスする
		if( searchFg == false ) {
			
			sb.append("SELECT ");
			sb.append("	 rts.tenpo_cd as tenpo_cd ");
			sb.append("FROM ");
			sb.append("	 r_tenbetu_siiresaki rts ");
			sb.append("WHERE ");
			sb.append("	 rts.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
			sb.append("	AND ");
			sb.append("	 rts.kanri_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
			sb.append("	AND ");
			sb.append("	 rts.ten_siiresaki_kanri_cd = '").append(tenSiiresakiKanriCd).append("' ");
			//sb.append("	AND ");
			//sb.append("	 rts.tenpo_cd = '").append(tenpoCd).append("' ");
			sb.append("	AND ");
			sb.append("	 rts.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

			rs = db.executeQuery(sb.toString());
			
			while( rs.next() ) {
				nm = rs.getString("TENPO_CD");
				hm.put( nm, nm );
			}
			
			rs.close();
			rs = null;

			searchFg = true;
		}
		
		if( tenpoCd != null && (String)hm.get( tenpoCd.trim() ) != null && !((String)hm.get( tenpoCd.trim() )).trim().equals("") ) 
		{
			return true;	
		} else {
			return false;
		}
	}
//add end


}
