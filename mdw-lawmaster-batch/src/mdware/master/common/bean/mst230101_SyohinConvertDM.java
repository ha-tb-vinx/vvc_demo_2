/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/29)初版作成
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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）旧・新商品コード変換マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する旧・新商品コード変換マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst230101_SyohinConvertDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	//マスタ日付取得
	String mstDt = RMSTDATEUtil.getBatDateDt();

	/**
	 * コンストラクタ
	 */
	public mst230101_SyohinConvertDM()
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
		mst230101_SyohinConvertBean bean = new mst230101_SyohinConvertBean();
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setHankuNm( rest.getString("hanku_nm") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setSyohinNm( rest.getString("syohin_nm") );
		bean.setHankuAfterCd( rest.getString("hanku_after_cd") );
		bean.setHankuAfterNm( rest.getString("hanku_after_nm") );
		bean.setSyohinAfterCd( rest.getString("syohin_after_cd") );
		bean.setSyohinAfterNm( rest.getString("syohin_after_nm") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setInsertUserNm( rest.getString("insert_user_nm") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setUpdateUserNm( rest.getString("update_user_nm") );
		bean.setDeleteFg( rest.getString("delete_fg") );

		//販区コード（変換後）が存在する場合のみデータが存在する
		if(rest.getString("hanku_after_cd") != null && !rest.getString("hanku_after_cd").equals("")){
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
	public String getSelectSql( Map map )
	{

		String hankuCd = mst000401_LogicBean.chkNullString((String)map.get("hanku_cd"));
		String syohinCd = mst000401_LogicBean.chkNullString((String)map.get("syohin_cd"));
		String syorikb = mst000401_LogicBean.chkNullString((String)map.get("syorikb")) ;

		//Update時の存在チェック用商品コード
		String syohinCdUpdateChk = mst000401_LogicBean.chkNullString((String)map.get("syohin_cd_update_chk"));
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//---------------------------------------
		//SQL作成
		//---------------------------------------
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		//■変換前
		//販区コード
		sb.append(" cnvt.hanku_cd, ");
		//販区名称
		sb.append("	(SELECT kanji_rn FROM r_namectf ");
		sb.append("	 WHERE syubetu_no_cd = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) +"' ");
		sb.append("	 AND code_cd = cnvt.hanku_cd ");
		sb.append("	) as hanku_nm, ");
		//商品コード
		sb.append("	cnvt.syohin_cd, ");
		//商品名称
		sb.append(" (SELECT shn1.hinmei_kanji_na FROM r_syohin shn1");
		sb.append("  WHERE shn1.yuko_dt = (SELECT MAX(YUKO_DT) FROM R_SYOHIN");
		sb.append("                        WHERE HANKU_CD = shn1.HANKU_CD");
		sb.append("                        AND SYOHIN_CD = shn1.SYOHIN_CD");
		sb.append("                        AND YUKO_DT <= '"+ mstDt +"')");
		sb.append("  AND shn1.hanku_cd = cnvt.hanku_cd");
		sb.append("  AND shn1.syohin_cd = cnvt.syohin_cd");
		sb.append("  AND shn1.delete_fg = '0'");
		sb.append("	) as syohin_nm,");

		//■変換後
		//販区コード
		sb.append("	cnvt.hanku_after_cd, ");
		//販区名称
		sb.append("	(select kanji_rn from r_namectf ");
		sb.append("	 where syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' ");
		sb.append("	 and code_cd = cnvt.hanku_after_cd ");
		sb.append("	) as hanku_after_nm, ");
		//商品コード
		sb.append("	trim(cnvt.syohin_after_cd) as syohin_after_cd, ");
		//商品名称
		sb.append(" (SELECT shn2.hinmei_kanji_na FROM r_syohin shn2");
		sb.append("  WHERE shn2.yuko_dt = (SELECT MAX(YUKO_DT) FROM R_SYOHIN");
		sb.append("                        WHERE HANKU_CD = shn2.HANKU_CD");
		sb.append("                        AND SYOHIN_CD = shn2.SYOHIN_CD");
		sb.append("                        AND YUKO_DT <= '"+ mstDt +"')");
		sb.append("  AND shn2.hanku_cd = cnvt.hanku_after_cd");
		sb.append("  AND shn2.syohin_cd = cnvt.syohin_after_cd");
		sb.append("  AND shn2.delete_fg = '0'");
		sb.append("	) as syohin_after_nm, ");
		//作成年月日
		sb.append("	cnvt.insert_ts, ");
		//作成者社員ID
		sb.append("	cnvt.insert_user_id as insert_user_id, ");
		//作成者社員名
		sb.append("	(select user_na from sys_sosasya ");
		sb.append("	 where cnvt.insert_user_id = user_id and hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "') ");
		sb.append("	 AS insert_user_nm, ");
		//
		sb.append("	cnvt.update_ts, ");
		//更新者社員ID
		sb.append("	cnvt.update_user_id as update_user_id, ");
		//更新者社員名
		sb.append("	(select user_na from sys_sosasya ");
		sb.append("	 where cnvt.update_user_id = user_id and hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "') ");
		sb.append("	 AS update_user_nm, ");
		//削除フラグ
		sb.append("	cnvt.delete_fg ");

		//▼FROM句
		sb.append(" FROM ");

		//処理状況＝新規の場合
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){

			sb.append("(");
			sb.append(" select ");
			sb.append("	syohin.hanku_cd, ");
			sb.append("	syohin.syohin_cd, ");
			sb.append("	convert.hanku_after_cd, ");
			sb.append("	convert.syohin_after_cd, ");
			sb.append("	convert.insert_ts, ");
			sb.append("	convert.insert_user_id, ");
			sb.append("	convert.update_ts, ");
			sb.append("	convert.update_user_id, ");
			sb.append("	convert.delete_fg, ");
			//旧・新商品コード変換マスタにデータが存在する場合は1とし存在しない場合は0とする
			sb.append(" decode(convert.hanku_cd,null,0,1) as exist_flg ");
			sb.append(" from ");
			sb.append("	r_syohin_convert convert, ");

			//商品マスタ現在有効レコード（通常データ） Start---
			sb.append("	(");
			sb.append(" select ");
			sb.append("  syohin2.hanku_cd, ");
			sb.append("  syohin2.syohin_cd, ");
			sb.append("  syohin2.hinmei_kanji_na ");
			sb.append(" from ");
			sb.append("  (select hanku_cd,syohin_cd,max(yuko_dt) as yuko_dt ");
			sb.append("   from r_syohin ");
			sb.append("   where ");
//			↓↓仕様変更（2005.09.28）↓↓
//			sb.append("    yuko_dt <= to_char(sysdate,'yyyymmdd') ");
			sb.append("    yuko_dt <= '" + mstDt + "' ");
//			↑↑仕様変更（2005.09.28）↑↑
			sb.append("    and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
			sb.append("    and syohin_cd like '" + conv.convertWhereString(syohinCd) + "'");
			if(hankuCd != null && !hankuCd.equals("")){
				sb.append("    and hanku_cd = '" + conv.convertWhereString(hankuCd) + "'");
			}
			sb.append("   group by hanku_cd,syohin_cd ) syohin1,");
			sb.append("  r_syohin syohin2 ");
			sb.append(" where ");
			sb.append("  syohin1.hanku_cd = syohin2.hanku_cd ");
			sb.append("  and syohin1.syohin_cd = syohin2.syohin_cd ");
			sb.append("  and syohin1.yuko_dt = syohin2.yuko_dt ");

			sb.append("	) syohin ");
			//商品マスタ現在有効レコード（通常データ） end---

			sb.append("where ");
			sb.append(" syohin.hanku_cd = convert.hanku_cd(+) ");
			sb.append(" and syohin.syohin_cd = convert.syohin_cd(+) ");
			sb.append(" and convert.delete_fg(+) = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
			sb.append(" ) cnvt ");


		//処理状況＝修正・削除・照会の場合
		} else {

			sb.append("(");
			sb.append("select ");
			sb.append("	hanku_cd, ");
			sb.append("	syohin_cd, ");
			sb.append("	hanku_after_cd, ");
			sb.append("	syohin_after_cd, ");
			sb.append("	insert_ts, ");
			sb.append("	insert_user_id, ");
			sb.append("	update_ts, ");
			sb.append("	update_user_id, ");
			sb.append("	delete_fg ");
			sb.append("from ");
			sb.append("	r_syohin_convert ");
			sb.append("where ");

			//Update時の存在チェック
			if(syohinCdUpdateChk != null && !syohinCdUpdateChk.equals("")){
				sb.append(" syohin_cd = '" + conv.convertWhereString(syohinCdUpdateChk) + "'");

			//検索ボタン押下時の検索時
			} else {
				sb.append(" syohin_cd = '" + conv.convertWhereString(syohinCd) + "'");
			}

			if(hankuCd != null && !hankuCd.equals("")){
				sb.append(" and hanku_cd = '" + conv.convertWhereString(hankuCd) + "'");
			}
			sb.append(" and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
			sb.append(" ) cnvt ");
		}

		//▼WHERE句
		sb.append(" WHERE ");
		sb.append("   cnvt.hanku_cd in (SELECT DISTINCT TBL00.hanku_cd ");
		sb.append("   			        FROM R_SYOHIN_TAIKEI TBL00 ");
		sb.append("   			        WHERE ");

		// 小業種コードが入っている場合
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(" TBL00.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");

		}else{

			if(map.get("selected_gyousyu_cd").equals("01")){

				// 選択業種が01(衣料)
				sb.append(" TBL00.D_GYOSYU_CD = '0011'");

			}else if(map.get("selected_gyousyu_cd").equals("02")){

				// 選択業種が02(住生活)
				sb.append(" TBL00.D_GYOSYU_CD NOT IN('0011', '0033', '0044')");

			}else if(map.get("selected_gyousyu_cd").equals("03")){

				// 選択業種が03(加工食品)
				sb.append(" TBL00.D_GYOSYU_CD = '0033'");
				sb.append(" AND TBL00.S_GYOSYU_CD = '0087'");

			}else if(map.get("selected_gyousyu_cd").equals("04")){

				// 選択業種が03(加工食品)
				sb.append(" TBL00.D_GYOSYU_CD = '0033'");
				sb.append(" AND TBL00.S_GYOSYU_CD <> '0087'");
			}
		}

		//処理状況＝新規の場合、旧・新商品コード変換マスタに存在しない店舗のみとする
		if(syorikb.equals(mst000101_ConstDictionary.PROCESS_INSERT)){
			sb.append(" AND cnvt.exist_flg = 0 ");
		}
		sb.append(" ) ");
		sb.append(" order by cnvt.hanku_cd , cnvt.syohin_cd ");

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{

		mst230101_SyohinConvertBean bean = (mst230101_SyohinConvertBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("insert into ");
		sb.append("r_syohin_convert ( ");
		sb.append(" hanku_cd ");
		sb.append(" ,syohin_cd ");
		sb.append(" ,hanku_after_cd ");
		sb.append(" ,syohin_after_cd ");
		sb.append(" ,insert_ts ");
		sb.append(" ,insert_user_id ");
		sb.append(" ,delete_fg ");
		sb.append(") values( ");

		sb.append("'" + conv.convertString( bean.getHankuCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getSyohinCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getHankuAfterCd() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getSyohinAfterCd() ) + "' ");
		sb.append(",TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
		sb.append(",'" + conv.convertString( bean.getInsertUserId() ) + "' ");
		sb.append(",'" + conv.convertString( bean.getDeleteFg() ) + "' ");
		sb.append(") ");

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{

		mst230101_SyohinConvertBean bean = (mst230101_SyohinConvertBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("update ");
		sb.append("r_syohin_convert set ");

		sb.append(" hanku_after_cd = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getHankuAfterCd()) ) + "' ");

		sb.append(" ,syohin_after_cd = ");
		sb.append(" '" + conv.convertString( mst000401_LogicBean.chkNullString(bean.getSyohinAfterCd()) ) + "' ");

		sb.append(" ,update_ts = ");
		sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");

		sb.append(" ,update_user_id = ");
		sb.append(" '" + conv.convertString( bean.getUpdateUserId() ) + "' ");

		sb.append(" ,delete_fg = ");
		sb.append(" '" + conv.convertString( bean.getDeleteFg() ) + "' ");

		//削除フラグ=1データ存在時の新規作成時の場合
		if(!bean.isCreateDatabase()){

			sb.append(" ,insert_ts = ");
			sb.append(" TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");

			sb.append(" ,insert_user_id = ");
			sb.append(" '" + conv.convertString( bean.getInsertUserId() ) + "' ");

		}

		sb.append("where ");
		sb.append(" hanku_cd = '" + conv.convertString( bean.getHankuCd() ) + "' ");
		sb.append(" and syohin_cd = '" + conv.convertString( bean.getSyohinCd() ) + "' ");

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
