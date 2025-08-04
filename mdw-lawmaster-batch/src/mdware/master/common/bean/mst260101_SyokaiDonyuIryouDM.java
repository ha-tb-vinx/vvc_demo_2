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

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
public class mst260101_SyokaiDonyuIryouDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst260101_SyokaiDonyuIryouDM()
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
		mst260101_SyokaiDonyuIryouBean bean = new mst260101_SyokaiDonyuIryouBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		bean.setHachunoCd( rest.getString("hachuno_cd") );
		
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setColorCd( rest.getString("color_cd") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setAsortPatternCd( rest.getString("asort_pattern_cd") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoNm( rest.getString("tenpo_nm").trim() );
		bean.setThemeCd( rest.getString("theme_cd") );
		bean.setSuryoQt( rest.getString("suryo_qt") );
		bean.setSuryoQtOld( rest.getString("suryo_qt") );
//		↓↓仕様変更（2005.10.14）↓↓	
		bean.setHachutaniQt( rest.getString("hachutani_qt") );
//		↑↑仕様変更（2005.10.14）↑↑
		bean.setGentankaVl( rest.getString("gentanka_vl") );
		bean.setBaitankaVl( rest.getString("baitanka_vl") );
		bean.setHatyuDt( rest.getString("hatyu_dt") );
		bean.setNohinDt( rest.getString("nohin_dt") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );

		//販区コードが存在する場合のみデータが存在する
		if(rest.getString("hanku_cd") != null && !rest.getString("hanku_cd").equals("")){
			bean.setCreateDatabase();
		}

		return bean;
	}

	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
//	****** DB Var3.6 修正箇所 *****　確認！ **********************			
	public String getSelectSql( Map map )
	{
		
		//--------------------------
		//SQL生成
		//--------------------------
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		String syorikb = (String)map.get("syorikb") ;
		// ↓↓　2006/03/16 kim START
		//	検索する条件でカラー/サイズ追加チェック向け 
		String gyosyu_kb= (String)map.get("gyosyu_kb");
		// ↑↑　2006/03/16 kim END
		
		
		if (syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)) {
			sb.append("SELECT ");
			sb.append("  r_tenpo.tenpo_cd, ");
			sb.append("  r_tenpo.kanji_rn as tenpo_nm, ");
			sb.append("  null as hanku_cd, ");
			sb.append("  null as hachuno_cd, ");
			sb.append("  null as syohin_cd, ");
			sb.append("  null as theme_cd, ");
			sb.append("  null as suryo_qt, ");
//			↓↓仕様変更（2005.10.14）↓↓
			sb.append("	 null as hachutani_qt, ");
//			↑↑仕様変更（2005.10.14）↑↑
			sb.append("  null as gentanka_vl, ");
			sb.append("  null as baitanka_vl, ");
			sb.append("  null as color_cd, ");
			sb.append("  null as size_cd, ");
			sb.append("  null as asort_pattern_cd, ");
			sb.append("  null as hatyu_dt, ");
			sb.append("  null as nohin_dt, ");
			sb.append("  null as insert_ts, ");
			sb.append("  null as insert_user_id, ");
			sb.append("  null as update_ts, ");
			sb.append("  null as update_user_id, ");
			sb.append("  null as delete_fg ");
			sb.append("FROM ");
			sb.append("  r_tengroup INNER JOIN ");
			sb.append("	   (SELECT ");
			sb.append("       tenpo_cd, ");
			sb.append("       kanji_rn ");
			sb.append("     FROM ");
			sb.append("       r_tenpo ");
			sb.append("     WHERE ");
			sb.append("       tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' AND ");
			sb.append("       heiten_dt > '"+ RMSTDATEUtil.getMstDateDt() + "' ");
			sb.append("    ) r_tenpo ON r_tengroup.tenpo_cd = r_tenpo.tenpo_cd ");
			sb.append("WHERE ");
//			sb.append("  r_tengroup.l_gyosyu_cd = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS_CLOTHES + "' AND ");
			sb.append("  r_tengroup.l_gyosyu_cd = '" + conv.convertWhereString((String)map.get("h_l_gyosyucd")) + "' AND ");
			sb.append("  r_tengroup.yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' AND ");
			sb.append("  r_tengroup.groupno_cd = '" + conv.convertWhereString((String)map.get("groupno_cd")) + "' AND ");
			sb.append("  r_tengroup.delete_fg = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "' ");
			sb.append("ORDER BY ");
			sb.append("  r_tenpo.tenpo_cd ");
			
		} else {
		
			sb.append("select  ");
			sb.append("	syokai.hanku_cd ");
			sb.append("	,  ");
//			****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//			sb.append("	syokai.siiresaki_cd ");
//			sb.append("	,  ");
			
			sb.append("	syokai.syohin_cd ");
			sb.append("	,  ");
			sb.append("	syokai.color_cd ");
			sb.append("	,  ");
			sb.append("	syokai.size_cd ");
			sb.append("	,  ");
			sb.append("	syokai.asort_pattern_cd ");
			sb.append("	,  ");
			sb.append("	tenpo.tenpo_cd ");
			sb.append("	,  ");
			sb.append("	tenpo.kanji_rn as tenpo_nm ");
			sb.append("	,  ");
			sb.append("	syokai.suryo_qt ");
			sb.append("	,  ");
//			↓↓仕様変更（2005.10.14）↓↓
			sb.append("	syokai.hachutani_qt ");
			sb.append("	,  ");
//			↑↑仕様変更（2005.10.14）↑↑
			sb.append("	syokai.gentanka_vl ");
			sb.append("	,  ");
			sb.append("	syokai.baitanka_vl ");
			sb.append("	,  ");
			sb.append("	syokai.hatyu_dt ");
			sb.append("	,  ");
			sb.append("	syokai.nohin_dt ");
			sb.append("	,  ");
			sb.append("	syokai.theme_cd ");
			sb.append("	,  ");
//			****** DB Var3.6 修正箇所 *****発注コード追加**********************
			sb.append("	syokai.hachuno_cd ");
			sb.append("	,  ");
			
			sb.append("	syokai.insert_ts ");
			sb.append("	,  ");
			sb.append("	syokai.insert_user_id ");
			sb.append("	,  ");
			sb.append("	syokai.update_ts ");
			sb.append("	,  ");
			sb.append("	syokai.update_user_id ");
			sb.append("	,  ");
			sb.append("	syokai.delete_fg ");
			sb.append("from  ");
	
			//処理状況＝新規の場合
			if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
	
				sb.append("(  ");
				sb.append(" select  ");
				sb.append(" tenpo.tenpo_cd ");
				sb.append("	,  ");
				sb.append(" syokai.hanku_cd ");
				sb.append("	,  ");
//				****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//				sb.append(" syokai.siiresaki_cd ");
//				sb.append("	,  ");
	
//				****** DB Var3.6 修正箇所 *****発注コード追加**********************
				sb.append("	syokai.hachuno_cd ");
				sb.append("	,  ");
				
				sb.append(" syokai.syohin_cd ");
				sb.append("	,  ");
				sb.append(" syokai.theme_cd ");
				sb.append("	,  ");
				sb.append(" syokai.suryo_qt ");
				sb.append("	,  ");
//				↓↓仕様変更（2005.10.14）↓↓
				sb.append(" syokai.hachutani_qt ");
				sb.append("	,  ");
//				↑↑仕様変更（2005.10.14）↑↑
				sb.append(" syokai.gentanka_vl ");
				sb.append("	,  ");
				sb.append(" syokai.baitanka_vl ");
				sb.append("	,  ");
				sb.append(" syokai.color_cd ");
				sb.append("	,  ");
				sb.append(" syokai.size_cd ");
				sb.append("	,  ");
				sb.append(" syokai.asort_pattern_cd ");
				sb.append("	,  ");
				sb.append(" syokai.hatyu_dt ");
				sb.append("	,  ");
				sb.append(" syokai.nohin_dt ");
				sb.append("	,  ");
				sb.append(" syokai.insert_ts ");
				sb.append("	,  ");
				sb.append(" syokai.insert_user_id ");
				sb.append("	,  ");
				sb.append(" syokai.update_ts ");
				sb.append("	,  ");
				sb.append(" syokai.update_user_id ");
				sb.append("	,  ");
				sb.append(" syokai.delete_fg ");
				sb.append("	,  ");
				sb.append(" decode(syokai.hanku_cd,null,0,1) as exist_flg  ");//初回導入マスタにデータが存在する場合は1とし存在しない場合は0とする
				sb.append(" from  ");
				sb.append(" r_tengroup tenpo  ");
				sb.append(" ,r_syokaidonyu syokai  ");
				sb.append(" where  ");
				sb.append(" tenpo.tenpo_cd = syokai.tenpo_cd(+) ");
				sb.append("  and  ");
//				sb.append(" tenpo.l_gyosyu_cd = '"+ mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS_CLOTHES +"' ");
				sb.append(" tenpo.l_gyosyu_cd = '" + conv.convertWhereString((String)map.get("h_l_gyosyucd")) + "' ");
				sb.append("  and  ");
				sb.append(" tenpo.yoto_kb = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "' ");				
				sb.append("  and  ");
				sb.append(" tenpo.groupno_cd = '" + conv.convertWhereString((String)map.get("groupno_cd")) + "' ");
				sb.append("  and  ");
				sb.append(" tenpo.delete_fg = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "' ");
				sb.append("  and  ");
				sb.append(" syokai.hanku_cd(+) = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "' ");
				sb.append("  and  ");
//				****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//				sb.append(" syokai.siiresaki_cd(+) = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "' ");
//				sb.append("  and  ");
//				****** DB Var3.6 修正箇所 *****発注コード追加**********************
//				↓↓仕様変更（2005.07.07）↓↓
//				sb.append(" syokai.hachuno_cd(+) = '" + conv.convertWhereString((String)map.get("hachuno_cd")) + "' ");
//				sb.append("  and  ");
//				↑↑仕様変更（2005.07.07）↑↑
//				****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//				sb.append(" syokai.syohin_cd(+) = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
//				sb.append("  and  ");
//				sb.append(" syokai.theme_cd(+) = '" + conv.convertWhereString((String)map.get("theme_cd")) + "' ");
//				sb.append("  and  ");
	
				sb.append(" syokai.delete_fg(+) = '" + conv.convertWhereString(mst000801_DelFlagDictionary.INAI.getCode()) + "'  ");
				sb.append("	) syokai  ");
				
			//処理状況＝修正・削除・照会の場合
			} else {
				sb.append("	(  ");
				sb.append("	select  ");
				sb.append("	 *  ");
				sb.append("	from  ");
				sb.append("	 r_syokaidonyu syokai  ");
				sb.append("	where  ");
				sb.append(" syokai.hanku_cd = '" + conv.convertWhereString((String)map.get("hanku_cd")) + "' ");
				sb.append("  and  ");
//				****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//				sb.append(" syokai.siiresaki_cd = '" + conv.convertWhereString((String)map.get("siiresaki_cd")) + "' ");
//				sb.append("  and  ");
	
//				****** DB Var3.6 修正箇所 *****発注コード追加**********************
//				sb.append(" syokai.hachuno_cd(+) = '" + conv.convertWhereString((String)map.get("hachuno_cd")) + "' ");
//				sb.append("  and  ");
				
//				****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//				sb.append(" syokai.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
//				sb.append("  and  ");
//				sb.append(" syokai.theme_cd = '" + conv.convertWhereString((String)map.get("theme_cd")) + "' ");
//				sb.append("  and  ");
//				↓↓仕様追加（2005.07.12）↓↓
				sb.append(" syokai.syohin_cd = '" + conv.convertWhereString((String)map.get("syohin_cd")) + "' ");
				sb.append("  and  ");
//				↑↑仕様追加（2005.07.12）↑↑
	
				sb.append(" syokai.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  ");
				
//				↓↓仕様追加（2005.07.12）↓↓
				if (!syorikb.equals(mst000101_ConstDictionary.PROCESS_REFERENCE)) {
					sb.append(" and  ");
//					↓↓バグ修正（2005.08.25）↓↓
//					sb.append(" hatyu_dt > '" + RMSTDATEUtil.getMstDateDt() + "'  ");
					sb.append(" hatyu_dt >= '" + RMSTDATEUtil.getMstDateDt() + "'  ");
//					↑↑バグ修正（2005.08.25）↑↑
				}
//				↑↑仕様追加（2005.07.12）↑↑
				
				//更新時のチェック時の取得用
				String tenpo_cd =  conv.convertWhereString((String)map.get("tenpo_cd"));
				if(tenpo_cd != null && !tenpo_cd.equals("")){
					sb.append("  and  ");
					sb.append(" syokai.tenpo_cd = '" + tenpo_cd + "'  ");
				}
				String hachuno_cd =  conv.convertWhereString((String)map.get("hachuno_cd"));
				if(hachuno_cd != null && !hachuno_cd.equals("")){
					sb.append("  and  ");
					sb.append(" syokai.hachuno_cd = '" + hachuno_cd + "'  ");
				}
				
				// ↓↓　2006/03/17 kim START
				// 		業種がA12の場合、検索条件でカラー/サイズコードとアソートパターン条件追加
				if (gyosyu_kb != null && gyosyu_kb.equals("A12")){
					String color_cd = conv.convertWhereString((String)map.get("color_cd"));
					String size_cd =	conv.convertWhereString((String)map.get("size_cd"));
					String asort_pattern_cd	=	conv.convertWhereString((String)map.get("asort_pattern_cd"));
					if (color_cd != null && !color_cd.equals("")){
						sb.append("	and	");
						sb.append("	syokai.color_cd =	'"+color_cd+"'	");
					}
					if (size_cd != null && !size_cd.equals("")){
						sb.append("	and	");
						sb.append("	syokai.size_cd	=	'"+size_cd+"'	");
					}
					if	 (asort_pattern_cd != null && !asort_pattern_cd.equals("")){
						sb.append("	and	");
						sb.append("	syokai.asort_pattern_cd =	'"+asort_pattern_cd+"'	");
					}
				}
				// ↑↑　2006/03/17 kim END
				
				sb.append("	) syokai  ");
			}
//			↓↓仕様追加（2005.07.12）↓↓
			sb.append("	 inner join  ");
			sb.append("	   (select  ");
			sb.append("       tenpo_cd,  ");
			sb.append("       kanji_rn  ");
			sb.append("     from  ");
			sb.append("       r_tenpo  ");
			sb.append("     where  ");
			sb.append("       tenpo_kb = '" + mst003601_TenpoKbDictionary.EIGYO_TENPO.getCode() + "' and  ");
			sb.append("       heiten_dt > '"+ RMSTDATEUtil.getMstDateDt() + "'  ");
			sb.append("    ) tenpo ON syokai.tenpo_cd = tenpo.tenpo_cd  ");
//			sb.append("	,r_tenpo tenpo  ");
//			↑↑仕様追加（2005.07.12）↑↑
	
			sb.append("where  ");
			sb.append(" syokai.tenpo_cd = tenpo.tenpo_cd  ");
	
			//処理状況＝新規の場合、初回導入マスタに存在しない店舗のみとする
			if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
//				sb.append(" and syokai.exist_flg = 0  ");
			}
			
			sb.append("order by syokai.hachuno_cd , syokai.tenpo_cd ");
		
		}

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
//	****** DB Var3.6 修正箇所 *****　確認！変更箇所あり　**********************			
	public String getInsertSql( Object beanMst )
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

		mst260101_SyokaiDonyuIryouBean bean = (mst260101_SyokaiDonyuIryouBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("insert into  ");
		sb.append("r_syokaidonyu ( ");
		sb.append(" hanku_cd ");
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(" ,siiresaki_cd ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append("	,hachuno_cd ");
		
		sb.append(" ,syohin_cd ");
		sb.append(" ,theme_cd ");
		sb.append(" ,tenpo_cd ");
		sb.append(" ,suryo_qt ");
//		↓↓仕様変更（2005.10.14）↓↓
		sb.append(" ,hachutani_qt ");
//		↑↑仕様変更（2005.10.14）↑↑
		sb.append(" ,gentanka_vl ");
		sb.append(" ,baitanka_vl ");
		sb.append(" ,color_cd ");
		sb.append(" ,size_cd ");
		sb.append(" ,asort_pattern_cd ");
		sb.append(" ,hatyu_dt ");
		sb.append(" ,nohin_dt ");
		sb.append(" ,insert_ts ");
		sb.append(" ,insert_user_id ");
		sb.append(" ,delete_fg ");
		//  ↓↓2006/03/02 kim START 衣類の場合、自動発注一時/停止をデフォルト'0'で登録
		sb.append("	,jihatu_teisi_kb ");
		//　↑↑2006/03/02 kim END
		sb.append(") values(  ");

		sb.append("'" + conv.convertString( bean.getHankuCd() ) + "' ");
		
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(",'" + conv.convertString( bean.getSiiresakiCd() ) + "' ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
		sb.append(",'" + conv.convertString( bean.getHachunoCd() ) + "' ");
		
		sb.append(",'" + conv.convertString( bean.getSyohinCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getThemeCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getTenpoCd() ) + "' ");
		sb.append(",'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSuryoQt()) ) + "' ");
//		↓↓仕様変更（2005.10.14）↓↓
		sb.append(",'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getHachutaniQt()) ) + "' ");
//		↑↑仕様変更（2005.10.14）↑↑
		sb.append(",'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getGentankaVl()) ) + "' ");
		sb.append(",'" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getBaitankaVl()) ) + "' ");
//		↓↓仕様変更（2005.07.07）↓↓
		if (bean.getColorCd() == null || bean.getColorCd().length() == 0) {
			sb.append(",' ' ");
			sb.append(",' ' ");
		} else {
			sb.append(",'" + conv.convertString( bean.getColorCd() ) + "' ");
			sb.append(",'" + conv.convertString( bean.getSizeCd() ) + "' ");
		}
		if (bean.getAsortPatternCd() == null || bean.getAsortPatternCd().length() == 0) {
			sb.append(",' ' ");
		} else {
			sb.append(",'" + conv.convertString( bean.getAsortPatternCd() ) + "' ");
		}
//		sb.append(",'" + conv.convertString( bean.getColorCd() ) + "' ");
//		sb.append(",'" + conv.convertString( bean.getSizeCd() ) + "' ");
//		sb.append(",'" + conv.convertString( bean.getAsortPatternCd() ) + "' ");
//		↑↑仕様変更（2005.07.07）↑↑
		sb.append(",'" + conv.convertString( bean.getHatyuDt() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getNohinDt() ) + "' ");
		sb.append(",TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
		sb.append(",'" + conv.convertString( bean.getInsertUserId() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getDeleteFg() ) + "' ");
		//  ↓↓2006/03/02 kim START　衣類の場合、自動発注一時/停止をデフォルト'0'で登録
		sb.append(", '0' ");
		//　↑↑2006/03/02 kim END
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

		mst260101_SyokaiDonyuIryouBean bean = (mst260101_SyokaiDonyuIryouBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("r_syokaidonyu set  ");

		sb.append(" suryo_qt = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSuryoQt()) ) + "' ");
		
//		↓↓仕様変更（2005.10.14）↓↓
		sb.append(" ,hachutani_qt = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getHachutaniQt()) ) + "' ");
//		↑↑仕様変更（2005.10.14）↑↑

		sb.append(" ,gentanka_vl = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getGentankaVl()) ) + "' ");

		sb.append(" ,baitanka_vl = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getBaitankaVl()) ) + "' ");

//		↓↓仕様変更（2005.07.07）↓↓
		if (bean.getColorCd() == null || bean.getColorCd().length() == 0) {
			sb.append(" ,color_cd = ");
			sb.append(" ' ' ");
			sb.append(" ,size_cd = ");
			sb.append(" ' ' ");
		} else {
			sb.append(" ,color_cd = ");
			sb.append(" '" + conv.convertString( bean.getColorCd() ) + "' ");
			sb.append(" ,size_cd = ");
			sb.append(" '" + conv.convertString( bean.getSizeCd() ) + "' ");
		}
		if (bean.getAsortPatternCd() == null || bean.getAsortPatternCd().length() == 0) {
			sb.append(" ,asort_pattern_cd = ");
			sb.append(" ' ' ");
		} else {
			sb.append(" ,asort_pattern_cd = ");
			sb.append(" '" + conv.convertString( bean.getAsortPatternCd() ) + "' ");
		}

//		sb.append(" ,color_cd = ");
//		sb.append(" '" + conv.convertString( bean.getColorCd() ) + "' ");
//
//		sb.append(" ,size_cd = ");
//		sb.append(" '" + conv.convertString( bean.getSizeCd() ) + "' ");
//
//		sb.append(" ,asort_pattern_cd = ");
//		sb.append(" '" + conv.convertString( bean.getAsortPatternCd() ) + "' ");
//		↑↑仕様変更（2005.07.07）↑↑

		sb.append(" ,hatyu_dt = ");
		sb.append(" '" + conv.convertString( bean.getHatyuDt() ) + "' ");

		sb.append(" ,nohin_dt = ");
		sb.append(" '" + conv.convertString( bean.getNohinDt() ) + "' ");

		sb.append(" ,update_ts = ");
		sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");

		sb.append(" ,update_user_id = ");
		sb.append(" '" + conv.convertString( bean.getUpdateUserId() ) + "' ");

		sb.append(" ,delete_fg = ");
		sb.append(" '" + conv.convertString( bean.getDeleteFg() ) + "'  ");
		
		//削除フラグ=1データ存在時の新規作成時の場合
		if(!bean.isCreateDatabase()){

			sb.append(" ,insert_ts = ");
			sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");

			sb.append(" ,insert_user_id = ");
			sb.append(" '" + conv.convertString( bean.getInsertUserId() ) + "' ");

		}
		
//		↓↓仕様変更（2005.07.07）↓↓
		sb.append(" ,theme_cd = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getThemeCd()) ) + "'");
//		↑↑仕様変更（2005.07.07）↑↑

		sb.append("where  ");

//		****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//		sb.append(" hanku_cd = ");
//		sb.append(" '" + conv.convertString( bean.getHankuCd() ) + "' ");

//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		sb.append(" and siiresaki_cd = ");
//		sb.append(" '" + conv.convertString( bean.getSiiresakiCd() ) + "' ");

//		****** DB Var3.6 修正箇所 *****発注コード追加**********************
//		↓↓仕様変更（2005.07.07）↓↓
//		sb.append(" and hachuno_cd = ");
		sb.append(" hachuno_cd = ");
//		↑↑仕様変更（2005.07.07）↑↑
		sb.append(" '" + conv.convertString( bean.getHachunoCd() ) + "' ");
		
//		****** DB Var3.6 修正箇所 *****販区、商品、テーマ従属項目に変更**********************			
//		sb.append(" and syohin_cd = ");
//		sb.append(" '" + conv.convertString( bean.getSyohinCd() ) + "' ");
//
//		sb.append(" and theme_cd = ");
//		sb.append(" '" + conv.convertString( bean.getThemeCd() ) + "' ");

		sb.append(" and tenpo_cd = ");
		sb.append(" '" + conv.convertString( bean.getTenpoCd() ) + "' ");
		
		// ↓↓　2006/03/17 kim START
		sb.append(" and hanku_cd = ");
		sb.append(" '" + conv.convertString( bean.getHankuCd() ) + "' ");

		sb.append(" and syohin_cd = ");
		sb.append(" '" + conv.convertString( bean.getSyohinCd() ) + "' ");

		// ↑↑　2006/03/17 kim END
	
		return sb.toString();
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
