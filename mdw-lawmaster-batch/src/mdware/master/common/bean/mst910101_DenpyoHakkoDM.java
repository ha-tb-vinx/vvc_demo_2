/**
 * <p>タイトル	: 新ＭＤシステム（マスタ管理）発注（センター）のDMクラス</p>
 * <p>説明		: 新ＭＤシステムで使用する商品マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権	: Copyright (c) 2005</p>
 * <p>会社名	: Vinculum Japan Corp.</p>
 * @author 	VJC A.Tozaki
 * @version	1.0(2005/10/15)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;
import mdware.master.util.RMSTDATEUtil;


public class mst910101_DenpyoHakkoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst910101_DenpyoHakkoDM()
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
		mst910101_DenpyoHakkoBean bean = new mst910101_DenpyoHakkoBean();
		bean.setDenpyoNb( rest.getString("DENPYO_NB") );
		bean.setDenpyoGyoNb( rest.getString("DENPYOGYO_NB") );
		bean.setNohinDt( rest.getString("NOHIN_DT") );
		bean.setHachuDt( rest.getString("HACHU_DT") );
		bean.setLHankuCd( rest.getString("L_HANKU_CD") );
		bean.setTorihikisakiCd( rest.getString("TORIHIKISAKI_CD") );
		bean.setTorihikisakiNa( rest.getString("TORIHIKISAKI_NA") );
		bean.setTenpoCd( rest.getString("TENPO_CD") );
		bean.setSyohinCd( rest.getString("SYOHIN_CD") );
		bean.setSyohinNa( rest.getString("SYOHIN_KA") );
		bean.setGenkaVl( rest.getString("GENKA_VL") );
		bean.setBaikaVl( rest.getString("BAIKA_VL") );
		bean.setHachuTaniQt( rest.getString("HACHU_TANI_QT") );
		bean.setHachuQt( rest.getString("HACHU_QT") );
		bean.setHachuKb( rest.getString("HACHU_KB") );
		bean.setHaisinsakiCd( rest.getString("HAISINSAKI_CD"));
		bean.setSaKb( rest.getString("SA_KB"));
		bean.setKeiyuCenterCd( rest.getString("KEIYU_CENTER_CD"));
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

		//--------------------
		//SQLの作成
		//--------------------
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT  ");
		sb.append("		DENPYO_NB		AS DENPYO_NB, ");
		sb.append("		DENPYOGYO_NB	AS DENPYOGYO_NB, ");
		sb.append("		NOHIN_DT		AS NOHIN_DT, ");
		sb.append("		HACHU_DT		AS HACHU_DT, ");
		sb.append("		L_HANKU_CD		AS L_HANKU_CD, ");
		sb.append("		TORIHIKISAKI_CD	AS TORIHIKISAKI_CD, ");
		sb.append("		TORIHIKISAKI_NA AS TORIHIKISAKI_NA, ");
		sb.append("		TENPO_CD		AS TENPO_CD, ");
		sb.append("		SYOHIN_CD		AS SYOHIN_CD, ");
		sb.append("		SYOHIN_KA		AS SYOHIN_KA, ");
		sb.append("		GENKA_VL		AS GENKA_VL, ");
		sb.append("		BAIKA_VL		AS BAIKA_VL, ");
		sb.append("		HACHU_TANI_QT	AS HACHU_TANI_QT, ");
		sb.append("		HACHU_QT		AS HACHU_QT, ");
		sb.append("		CASE HACHU_KB ");
		sb.append("			WHEN '01' THEN '店舗発注' ");
		sb.append("			WHEN '02' THEN '本部発注' ");
		sb.append("			ELSE '' ");
		sb.append("		 END AS HACHU_KB, ");
		sb.append("		HAISINSAKI_CD	AS HAISINSAKI_CD, ");
		sb.append("		SA_KB			AS SA_KB, ");
		sb.append("		KEIYU_CENTER_CD AS KEIYU_CENTER_CD ");
		
		sb.append("  FROM ");
		sb.append("		R_HAKKO_RIREKI ");

		sb.append(" WHERE 1 = 1");

		// 伝票発行日
		if (map.get("denpyo_hakko_dt_from") != null && ((String)map.get("denpyo_hakko_dt_from")).trim().length() > 0) {
			sb.append("	AND HACHU_DT >= '" + conv.convertWhereString( (String)map.get("denpyo_hakko_dt_from") ) + "' ");
		}
		if (map.get("denpyo_hakko_dt_to") != null && ((String)map.get("denpyo_hakko_dt_to")).trim().length() > 0) {
			sb.append("	AND HACHU_DT <= '" + conv.convertWhereString( (String)map.get("denpyo_hakko_dt_to") ) + "' ");
		}
		// 売区コード
		if (map.get("uriku_cd") != null && ((String)map.get("uriku_cd")).trim().length() > 0) {
			sb.append("	AND URIKU_CD = '" + conv.convertWhereString( (String)map.get("uriku_cd") ) + "' ");
		}
		// 販区コード
		if (map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0) {
			sb.append("	AND L_HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "' ");
		}
		// 仕入先コード
		if (map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0) {
			sb.append("	AND TORIHIKISAKI_CD = '" + conv.convertWhereString( (String)map.get("siiresaki_cd") ) + "' ");
		}
		// 商品コード
		if (map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0) {
			// 衣料の場合はＬＩＫＥ検索（８桁＋カラーサイズ）
			if(map.get("selected_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_IRO.getCode())){
				sb.append("	AND SYOHIN_CD LIKE '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "%' ");
			}else{
				sb.append("	AND SYOHIN_CD = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "' ");
			}
		}
		// 品種コード
		if (map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0) {
			sb.append("	AND HINBAN_CD LIKE '" + conv.convertWhereString( (String)map.get("hinsyu_cd") ) + "%' ");
		}
		// 納品日
		if (map.get("nohin_dt") != null && ((String)map.get("nohin_dt")).trim().length() > 0) {
			sb.append("	AND NOHIN_DT = '" + conv.convertWhereString( (String)map.get("nohin_dt") ) + "' ");
		}
		// 店舗コード
		if (map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0) {
			sb.append("	AND TENPO_CD = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "' ");
		}

		// 発注区分
		if (map.get("hachu_kb") != null && ((String)map.get("hachu_kb")).trim().length() > 0) {
			if(map.get("hachu_kb").equals("2")){
				
				// 本部発注
				sb.append("	AND HACHU_KB = '02' ");
				
			}else if(map.get("hachu_kb").equals("3")){

				// 店舗発注
				sb.append("	AND HACHU_KB = '01' ");
			}
		}


		// 並び順
		if (map.get("sort_cd") != null && ((String)map.get("sort_cd")).trim().length() > 0) {
			if(map.get("sort_cd").equals("1")){
				
				// 販区→仕入先→納品日→店舗
				sb.append("	ORDER BY ");
				sb.append("		L_HANKU_CD, ");
				sb.append("		TORIHIKISAKI_CD, ");
				sb.append("		NOHIN_DT, ");
				sb.append("		TENPO_CD ");
				
			}else if(map.get("sort_cd").equals("2")){

				// 仕入先→販区→店舗→納品日
				sb.append("	ORDER BY ");
				sb.append("		TORIHIKISAKI_CD, ");
				sb.append("		L_HANKU_CD, ");
				sb.append("		TENPO_CD, ");
				sb.append("		NOHIN_DT ");
				
			}else{
				
				// 店舗→販区→仕入先→納品日
				sb.append("	ORDER BY ");
				sb.append("		TENPO_CD, ");
				sb.append("		L_HANKU_CD, ");
				sb.append("		TORIHIKISAKI_CD, ");
				sb.append("		NOHIN_DT ");
			}
		}
		

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
