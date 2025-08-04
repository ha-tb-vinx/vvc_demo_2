/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先発注納品不可日マスタ（生鮮）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先発注納品不可日マスタ（生鮮）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius kikuchi
 * @version 1.0(2005/02/23)初版作成
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst004201_HatyuFukaKbDictionary;
import mdware.master.common.dictionary.mst006101_SystemKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先発注納品不可日マスタ（生鮮）のDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先発注納品不可日マスタ（生鮮）のDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius kikuchi
 * @version 1.0(2005/02/23)初版作成
 */
public class mst510101_SiireHachuNohinNgDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst510101_SiireHachuNohinNgDM()
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
		mst510101_SiireHachuNohinNgBean bean = new mst510101_SiireHachuNohinNgBean();

		bean.setDispDt( rest.getString("dt") );
		bean.setKanriKb( rest.getString("kanri_kb") );
		bean.setKanriCd( rest.getString("kanri_cd") );
		bean.setKanriKanjiNa( rest.getString("kanji_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setDateDt( rest.getString("date_dt") );
		bean.setHachuFg( rest.getString("hachu_fg") );
		bean.setNohinFg( rest.getString("nohin_fg") );
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
	public String getSelectSql( Map map ) {

		StringBuffer sb = new StringBuffer();

		//月度
		String dateYm = (String) map.get("date_dt_ym");
		//種別No
		String syubetuNo = (String) map.get("syubetu_no_cd");
		//仕入先コード
		String siiresakiCd = (String) map.get("siiresaki_cd");

		//▼SELECT句
		sb.append(" SELECT ");
		sb.append("   dmy1.DT as DT,");
		sb.append("   dmy2.KANRI_KB as KANRI_KB,");
		sb.append("   dmy1.KANRI_CD as KANRI_CD,");
		sb.append("   dmy1.KANJI_NA as KANJI_NA,");
		sb.append("   dmy2.SIIRESAKI_CD as SIIRESAKI_CD,");
		sb.append("   dmy2.DATE_DT as DATE_DT,");
		sb.append("   dmy2.HACHU_FG as HACHU_FG,");
		sb.append("   dmy2.NOHIN_FG as NOHIN_FG,");
		sb.append("   dmy2.INSERT_TS as INSERT_TS,");
		sb.append("   dmy2.INSERT_USER_ID as INSERT_USER_ID,");
		sb.append("   dmy2.UPDATE_TS as UPDATE_TS,");
		sb.append("   dmy2.UPDATE_USER_ID as UPDATE_USER_ID,");
		sb.append("   dmy2.DELETE_FG as DELETE_FG");

		//▼FROM句
		sb.append(" FROM ");

		sb.append("   (SELECT * FROM ");
		sb.append("     (SELECT '"+ dateYm +"' || TO_CHAR(ROWNUM, 'FM00') DT ");
		sb.append("     FROM R_SYOHIN ");//31件以上あるテーブル

		//月末を取得
		sb.append("     WHERE ROWNUM <= TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE('"+ dateYm +"01','YYYYMMDD')), 'DD')) ");
		sb.append("     ) ");
		sb.append("   CROSS JOIN ");
		sb.append("     (SELECT ");
		sb.append("        nmctf.CODE_CD as KANRI_CD, ");
		sb.append("        MAX(nmctf.KANJI_NA) as  KANJI_NA,");

		sb.append("      CASE WHEN MAX(HACHU_FG) = '"+ mst004201_HatyuFukaKbDictionary.HACHUFUKA.getCode() +"'");
		sb.append("           OR MAX(NOHIN_FG) = '"+ mst004201_HatyuFukaKbDictionary.HACHUFUKA.getCode() +"'");
		sb.append("           THEN '0'");
		sb.append("           ELSE '1'");
		sb.append("      END AS SORTKEY");

		sb.append("     FROM ");
		sb.append("	  	  R_NAMECTF nmctf ");
		sb.append("	  	    LEFT OUTER JOIN ");
		sb.append("	  	    R_SIIRE_HACHUNOHIN_NG hcn ");
		sb.append("	  	    ON hcn.KANRI_KB = '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"'");
		sb.append("   	    AND nmctf.CODE_CD = hcn.KANRI_CD ");
		sb.append("         AND hcn.SIIRESAKI_CD = '"+ siiresakiCd +"' ");
		sb.append("         AND hcn.DELETE_FG = '0'");

		/* 2006/04/04 Modify By Kou for Bug No.76 ===BEGIN=== */
		//sb.append("         AND hcn.DATE_DT LIKE '"+ dateYm +"%' ");
		sb.append("         AND hcn.DATE_DT >= ADD_MONTHS(TO_DATE('"+ dateYm +"','YYYYMM'),-1) ");
		sb.append("         AND hcn.DATE_DT < ADD_MONTHS(TO_DATE('"+ dateYm +"','YYYYMM'),1) ");
		/* 2006/04/04 Modify By Kou for Bug No.76 ===END=== */
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sb.append("     WHERE ");
		sb.append("   	  nmctf.syubetu_no_cd = '" + MessageUtil.getMessage(syubetuNo, userLocal) + "'");
		sb.append("     AND nmctf.ZOKUSEI_CD = '"+ mst006101_SystemKbDictionary.FRESH.getCode() +"' ");
		sb.append("     AND nmctf.DELETE_FG = '0'");

		sb.append("     GROUP BY ");
		sb.append("       nmctf.CODE_CD ");
		sb.append("     )");
		sb.append("   ) dmy1 ");

		sb.append(" LEFT OUTER JOIN ");
		sb.append("   (SELECT ");
		sb.append("      KANRI_KB,");
		sb.append("      KANRI_CD,");
		sb.append("      SIIRESAKI_CD,");
		sb.append("      DATE_DT,");
		sb.append("      HACHU_FG,");
		sb.append("      NOHIN_FG, ");
		sb.append("      INSERT_TS, ");
		sb.append("      INSERT_USER_ID, ");
		sb.append("      UPDATE_TS, ");
		sb.append("      UPDATE_USER_ID, ");
		sb.append("      DELETE_FG ");

		sb.append("   FROM R_SIIRE_HACHUNOHIN_NG ");
		sb.append("   WHERE KANRI_KB = '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"' ");
		sb.append("   AND SIIRESAKI_CD = '"+ siiresakiCd +"' ");
		sb.append("   AND DELETE_FG = '0'");
		sb.append("   AND DATE_DT LIKE '"+ dateYm +"%' ");
		sb.append("   ) dmy2 ");

		sb.append(" ON ");
		sb.append("   dmy1.DT = dmy2.DATE_DT ");
		sb.append(" AND dmy1.KANRI_CD = dmy2.KANRI_CD ");

		//▼ORDER BY句
		sb.append(" ORDER BY ");
		sb.append("   dmy1.SORTKEY,");
		sb.append("   dmy1.KANRI_CD,");
		sb.append("   dmy1.DT");

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst ) {

		mst510101_SiireHachuNohinNgBean bean = (mst510101_SiireHachuNohinNgBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO ");
		sb.append("   R_SIIRE_HACHUNOHIN_NG (");

		sb.append("   KANRI_KB,");
		sb.append("   KANRI_CD,");
		sb.append("   SIIRESAKI_CD,");
		sb.append("   DATE_DT,");
		sb.append("   HACHU_FG,");
		sb.append("   NOHIN_FG,");
		sb.append("   INSERT_TS,");
		sb.append("   INSERT_USER_ID,");
		sb.append("   UPDATE_TS,");
		sb.append("   UPDATE_USER_ID,");
		sb.append("   DELETE_FG ");

		sb.append(" ) VALUES (");

		//管理区分:販区
		sb.append(" '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"',");
		//管理コード
		sb.append(" '"+ bean.getKanriCd() +"',");
		//仕入先コード
		sb.append(" '"+ bean.getSiiresakiCd() +"',");
		//日付
		if( bean.getDispDt() != null && bean.getDispDt().trim().length() != 0 ) {
			sb.append("'"+ bean.getDispDt() +"',");
		} else {
			sb.append("'',");
		}
		//発注フラグ
		if( bean.getHachuFg() != null && bean.getHachuFg().trim().length() != 0 ) {
			sb.append("'" + bean.getHachuFg() +"',");
		} else {
			sb.append("'" + mst004201_HatyuFukaKbDictionary.HACHU.getCode() +"',");
		}
		//納品フラグ
		if( bean.getNohinFg() != null && bean.getNohinFg().trim().length() != 0 ) {
			sb.append("'"+ bean.getNohinFg() +"',");
		} else {
			sb.append("'" + mst004201_HatyuFukaKbDictionary.HACHU.getCode() +"',");
		}

		//作成年月日
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 ) {
			sb.append("'"+ bean.getInsertTs() +"',");
		} else {
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),");
		}

		//作成者社員ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 ) {
			sb.append("'"+ bean.getInsertUserId() +"',");
		} else {
			sb.append("'',");
		}

		/* 2006-04-06 Modify By Kou ===BEGIN===*/
		/* 新規の時、更新動作また発生してないはず*/
//		//更新年月日
//		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 ) {
//			sb.append("'"+ bean.getUpdateTs() +"',");
//		} else {
//			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),");
//
//		}
		sb.append("'',");
//		//更新者社員ID
//		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 ) {
//			sb.append("'" + bean.getUpdateUserId() +"',");
//		} else {
//			sb.append("'',");
//		}
		sb.append("'',");
		/* 2006-04-06 Modify By Kou ===END===*/

		//削除フラグ
		sb.append(" '0'");
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
		mst510101_SiireHachuNohinNgBean bean = (mst510101_SiireHachuNohinNgBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_SIIRE_HACHUNOHIN_NG set ");
		if( bean.getHachuFg() != null && bean.getHachuFg().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hachu_fg = ");
			sb.append("'" + conv.convertString( bean.getHachuFg() ) + "'");
		}
		if( bean.getNohinFg() != null && bean.getNohinFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_fg = ");
			sb.append("'" + conv.convertString( bean.getNohinFg() ) + "'");
		}

		/* 2006-04-05 Modify By Kou ===BEGIN=== */
		/* Updateする時、新規情報を触らないように*/
//		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_ts = ");
//			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
//		}
//		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" insert_user_id = ");
//			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		}
		/* 2006-04-05 Modify By Kou ===END=== */

		//if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		//{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			//sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			sb.append("TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')");
		//}
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
		if( bean.getSiiresakiCd() != null && bean.getSiiresakiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" siiresaki_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getSiiresakiCd() ) + "'");
		}
		if( bean.getDateDt() != null && bean.getDateDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" date_dt = ");
			/* 2006-04-05 Modify By Kou ===BEGIN=== */
			/* bean.getDateDt()は年月の情報だけ、日を含めって居ない*/
			//sb.append("'" + conv.convertWhereString( bean.getDateDt() ) + "'");
			sb.append("'" + conv.convertWhereString( bean.getDispDt() ) + "'");
			/* 2006-04-05 Modify By Kou ===END=== */
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

		mst510101_SiireHachuNohinNgBean bean = (mst510101_SiireHachuNohinNgBean) beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("   R_SIIRE_HACHUNOHIN_NG ");
		sb.append(" WHERE ");
		sb.append("   KANRI_KB = '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"'");
		sb.append(" AND ");
		sb.append("   KANRI_CD = '"+ bean.getKanriCd() +"'");
		sb.append(" AND");
		sb.append("   SIIRESAKI_CD = '"+ bean.getSiiresakiCd() +"'");
		sb.append(" AND");
		//sb.append("   DATE_DT = '" + bean.getDispDt() + "'");
		sb.append("   DATE_DT BETWEEN '" + bean.getDateDt().substring(0,6) + "01'");
		sb.append("           AND '" + bean.getDateDt().substring(0,6) + "31'");

		/* 2006-04-05 Add By Kou ===BEGIN===*/
		/* DBから物理的な削除ではなく、削除フラグを１に更新する*/
		/* 今回は、削除処理を使ってない。もし必要があれば、下の文を使ってください。
//		sb.append(" UPDATE ");
//		sb.append("   R_SIIRE_HACHUNOHIN_NG ");
//		sb.append(" SET ");
//		sb.append("   DELETE_FG = '1'");
//		sb.append(" WHERE ");
//		sb.append("   KANRI_KB = '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"'");
//		sb.append(" AND ");
//		sb.append("   KANRI_CD = '"+ bean.getKanriCd() +"'");
//		sb.append(" AND");
//		sb.append("   SIIRESAKI_CD = '"+ bean.getSiiresakiCd() +"'");
//		sb.append(" AND");
//		sb.append("   DATE_DT = '" + bean.getDispDt() + "'");
		/* 2006-04-05 Add By Kou ===END===*/

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
