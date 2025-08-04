/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
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

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst920101_TagHakoRirekiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst920101_TagHakoRirekiDM()
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
		mst920101_TagHakoRirekiBean bean = new mst920101_TagHakoRirekiBean();

		bean.setSyohinCd(rest.getString("SYOHIN_CD"));
		bean.setColorCd(rest.getString("COLOR_CD"));
		bean.setSizeCd(rest.getString("SIZE_CD"));
		bean.setTagHakoDt(rest.getString("TAG_HAKO_DT"));
		bean.setHaisinKb(rest.getString("HAISIN_KB"));
		bean.setHaisinTime(rest.getString("HAISIN_TIME"));
		bean.setHaisinsakiCd(rest.getString("JCA_HAISINSAKI_CD"));
		bean.setTagHakoGyosyaCd(rest.getString("TAGHAKO_GYOSYA_CD"));
		bean.setTagHakoGyosyaNm(rest.getString("TAGHAKO_GYOSYA_NM"));
		bean.setSakuseiBasyoKb(rest.getString("SAKUSEI_BASYO_KB"));
		bean.setSakuseiBasyoNm(rest.getString("SAKUSEI_BASYO_NM"));
		bean.setKetasuKb(rest.getString("KETASU_KB"));
		bean.setNefudaKb(rest.getString("NEFUDA_KB"));
		bean.setHojyuHachuKb(rest.getString("HOJYU_HACHU_KB"));
		bean.setHankuCd(rest.getString("HANKU_CD"));
//		bean.setHankuNm(rest.getString("HANKU_NM"));
		bean.setSiiresakiCd(rest.getString("SIIRESAKI_CD"));
		bean.setSiiresakiNm(rest.getString("SIIRESAKI_NM"));
		bean.setKumisuKb(rest.getString("KUMISU_KB"));
		bean.setBaitankaVl(rest.getInt("BAITANKA_VL"));
		bean.setSiiresakiHinban(rest.getString("SIIRE_HINBAN_CD"));
		bean.setSyohinNm(rest.getString("SYOHIN_NM"));
		bean.setSizeNm(rest.getString("SIZE_NA"));
		bean.setColorSizeCd(rest.getString("COLOR_SIZE_CD"));
		bean.setTaghyojiBaikaVl(rest.getInt("TAG_HYOJI_BAIKA_VL"));
		bean.setTagHakoQt(rest.getInt("TAG_HAKO_QT"));
		bean.setInsertTs(rest.getString("INSERT_TS"));
		bean.setInsertUserId(rest.getString("INSERT_USER_ID"));
		bean.setUpdateTs(rest.getString("UPDATE_TS"));
		bean.setUpdateUserId(rest.getString("UPDATE_USER_ID"));
		bean.setDeleteFg(rest.getString("DELETE_FG"));
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
		StringBuffer sb = new StringBuffer();
		//▼SELECT句
		sb.append(" SELECT ");
		sb.append(" tg.SYOHIN_CD as SYOHIN_CD,");
		sb.append(" tg.COLOR_CD as COLOR_CD,");
		sb.append(" tg.SIZE_CD as SIZE_CD,");
		sb.append(" tg.TAG_HAKO_DT as TAG_HAKO_DT,");
		sb.append(" tg.HAISIN_KB as HAISIN_KB,");
		sb.append(" CASE tg.HAISIN_KB");
		sb.append("   WHEN '0' THEN '21:30'");
		sb.append("   WHEN '3' THEN '11:30'");
		sb.append("   WHEN '5' THEN '14:30'");
		sb.append("   WHEN '6' THEN '15:30'");
		sb.append("   WHEN '7' THEN '16:00'");
		sb.append("   WHEN '9' THEN '19:30'");
		sb.append(" END as HAISIN_TIME,");
		sb.append(" tg.JCA_HAISINSAKI_CD as JCA_HAISINSAKI_CD,");
		sb.append(" tg.TAGHAKO_GYOSYA_CD as TAGHAKO_GYOSYA_CD,");
		sb.append(" CASE tg.TAGHAKO_GYOSYA_CD WHEN '4' THEN 'ベンダー'");
		sb.append(" ELSE (SELECT KANJI_NA FROM R_NAMECTF ");
		sb.append("       WHERE SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.TAG_ISSUE_AGENCY_TRADER, userLocal) +"'");
		sb.append("       AND   CODE_CD = tg.TAGHAKO_GYOSYA_CD || '01')");
		sb.append(" END as TAGHAKO_GYOSYA_NM,");
		sb.append(" tg.SAKUSEI_BASYO_KB as SAKUSEI_BASYO_KB,");
		sb.append(" CASE tg.SAKUSEI_BASYO_KB");
		sb.append("   WHEN '03' THEN '東京'");
		sb.append("   WHEN '04' THEN '岐阜'");
		sb.append("   WHEN '05' THEN '名古屋'");
		sb.append("   WHEN '06' THEN '大阪'");
		sb.append("   WHEN '08' THEN '広島'");
		sb.append("   WHEN '09' THEN '福岡'");
		sb.append("   ELSE ' '");
		sb.append(" END as SAKUSEI_BASYO_NM,");
		sb.append(" tg.KETASU_KB as KETASU_KB,");
		sb.append(" tg.NEFUDA_KB as NEFUDA_KB,");
		sb.append(" tg.HOJYU_HACHU_KB as HOJYU_HACHU_KB,");
		sb.append(" tg.HANKU_CD,");
//		sb.append("	(SELECT nmctf.KANJI_NA FROM R_NAMECTF nmctf ");
//		sb.append("  WHERE nmctf.SYUBETU_NO_CD = '"+ mst000101_ConstDictionary.H_SALE +"'");
//		sb.append("  AND   nmctf.CODE_CD = tg.HANKU_CD ");
//		sb.append("  AND   nmctf.DELETE_FG = '0'");
//		sb.append(" ) as HANKU_NM,");
		sb.append(" tg.SIIRESAKI_CD as SIIRESAKI_CD,");
		sb.append(" (SELECT srk.KANJI_NA FROM R_SIIRESAKI srk");
		sb.append("  WHERE srk.DELETE_FG = '0'");
		sb.append("  AND   srk.KANRI_KB = '"+ mst000901_KanriKbDictionary.HANKU.getCode() +"'");
		sb.append("  AND   srk.KANRI_CD = tg.HANKU_CD ");
		sb.append("  AND   srk.SIIRESAKI_CD = tg.SIIRESAKI_CD ");
		sb.append(" ) as SIIRESAKI_NM,");
		sb.append(" tg.KUMISU_KB as KUMISU_KB,");
		sb.append(" tg.BAITANKA_VL as BAITANKA_VL,");
		sb.append(" tg.SIIRE_HINBAN_CD as SIIRE_HINBAN_CD,");
		sb.append(" tg.SYOHIN_NA as SYOHIN_NM,");
		sb.append(" tg.SIZE_NA as SIZE_NA,");
		sb.append(" tg.COLOR_SIZE_CD as COLOR_SIZE_CD,");
		sb.append(" tg.TAG_HYOJI_BAIKA_VL as TAG_HYOJI_BAIKA_VL,");
		sb.append(" tg.TAG_HAKO_QT as TAG_HAKO_QT,");
		sb.append(" tg.INSERT_TS as INSERT_TS,");
		sb.append(" tg.INSERT_USER_ID as INSERT_USER_ID,");
		sb.append(" tg.UPDATE_TS as UPDATE_TS,");
		sb.append(" tg.UPDATE_USER_ID as UPDATE_USER_ID,");
		sb.append(" tg.DELETE_FG as DELETE_FG");
		//▼FROM句
		sb.append(" FROM ");
		sb.append("   DT_TAGHAKO_RIREKI tg");
		//▼WHERE句
		sb.append(" WHERE ");
		sb.append("   DELETE_FG <> '1' ");

		//処理日from
		if (map.get("tx_syoriDt_from") != null && map.get("tx_syoriDt_from").toString().trim().length() > 0 ) {
			sb.append(" AND tg.TAG_HAKO_DT >= '"+ map.get("tx_syoriDt_from") +"'");
		}
		//処理日to
		if (map.get("tx_syoriDt_to") != null && map.get("tx_syoriDt_to").toString().trim().length() > 0 ) {
			sb.append(" AND tg.TAG_HAKO_DT <= '"+ map.get("tx_syoriDt_to") +"'");
		}
		//仕入先コード
		if (map.get("tx_siiresakicd") != null && map.get("tx_siiresakicd").toString().trim().length() > 0 ) {
			sb.append(" AND tg.SIIRESAKI_CD = '"+ map.get("tx_siiresakicd") +"'");
		}
		//タグ発行業者
		if (map.get("s_taghakogyosya") != null && map.get("s_taghakogyosya").toString().trim().length() > 0 ) {
			sb.append(" AND tg.TAGHAKO_GYOSYA_CD = '"+ map.get("s_taghakogyosya") +"'");
		}
		//作業場所区分
		if (map.get("s_sakuseibasyokb") != null && map.get("s_sakuseibasyokb").toString().trim().length() > 0 ) {
			sb.append(" AND tg.SAKUSEI_BASYO_KB = '"+ map.get("s_sakuseibasyokb") +"'");
		}
		//販区コード
		if (map.get("tx_hankucd") != null && map.get("tx_hankucd").toString().trim().length() > 0 ) {
			sb.append(" AND tg.HANKU_CD = '"+ map.get("tx_hankucd") +"'");
		}
		//商品コード
		if (map.get("tx_syohincd") != null && map.get("tx_syohincd").toString().trim().length() > 0 ) {
			sb.append(" AND tg.SYOHIN_CD = '"+ map.get("tx_syohincd") +"'");
		}

		//▼ORDER BY句
		sb.append(" ORDER BY ");
		sb.append(" tg.TAG_HAKO_DT,");
		sb.append(" tg.HAISIN_KB,");
		sb.append(" tg.HANKU_CD,");
		sb.append(" tg.SYOHIN_CD,");
		sb.append(" tg.COLOR_CD,");
		sb.append(" tg.SIZE_CD");

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
		return null;
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql( Object beanMst )
	{
		return null;
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
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
