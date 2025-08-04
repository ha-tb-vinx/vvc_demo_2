/**
 * <p>タイトル: 新ＭＤシステム（マスタ管理）自動発注本部数量停止マスタのDMクラス</p>
 * <p>説明: 	新ＭＤシステムで使用する自動発注本部数量停止マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: 	Copyright (c) 2005</p>
 * <p>会社名: 	Vinculum Japan Corp.</p>
 * @author 	VJC A.Tozaki
 * @version 	1.0(2005/08/08)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスタ管理）自動発注本部数量停止マスタのDMクラス</p>
 * <p>説明: 	新ＭＤシステムで使用する自動発注本部数量停止マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: 	Copyright (c) 2005</p>
 * <p>会社名: 	Vinculum Japan Corp.</p>
 * @author 	VJC A.Tozaki
 * @version 	1.0(2005/08/08)初版作成
 */
public class mst680101_JidoHachuHonbuSuryoTeisiDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst680101_JidoHachuHonbuSuryoTeisiDM()
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

		mst680101_JidoHachuHonbuSuryoTeisiBean bean = new mst680101_JidoHachuHonbuSuryoTeisiBean();

		bean.setSentaku( "" );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setKanjiNa( rest.getString("kanji_na") );
		bean.setTenHankuCd( rest.getString("tenhanku_cd") );
		bean.setHankuCd( rest.getString("tenhanku_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setTeisiQt( Double.toString(rest.getDouble("teisi_qt")) );
		bean.setChikanKb( rest.getString("chikan_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserTs( rest.getString("update_user_id") );
		bean.setTeisiSuryoQt( Double.toString(rest.getDouble("teisi_suryo_qt")) );
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

		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("		TEN.TENPO_CD AS TENPO_CD, ");
		sb.append("		TEN.KANJI_NA AS KANJI_NA, ");
		sb.append("		NVL(JIDO.TENHANKU_CD, MSP680101_GET_TENHANKUCD(TEN.TENPO_CD, '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "', '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "', '" + conv.convertWhereString( (String)map.get("mst_date_next") ) + "')) AS TENHANKU_CD, ");
		sb.append("		JIDO.SYOHIN_CD AS SYOHIN_CD, ");
		sb.append("		JIDO.YUKO_DT AS YUKO_DT, ");
		sb.append("		JIDO.TEISI_QT AS TEISI_QT, ");
		sb.append("		NVL(JIDO.CHIKAN_KB, '" + mst008101_JidoHachuHonbuSuryoTeisiDictionary.TIKAN.getCode() + "') AS CHIKAN_KB, ");
		sb.append("		JIDO.INSERT_TS AS INSERT_TS, ");
		sb.append("		JIDO.INSERT_USER_ID AS INSERT_USER_ID, ");
		sb.append("		JIDO.UPDATE_TS AS UPDATE_TS, ");
		sb.append("		JIDO.UPDATE_USER_ID AS UPDATE_USER_ID, ");

		sb.append("		NVL((SELECT ");
		sb.append("				COM.TEISI_SURYO_QT ");
		sb.append("			   FROM ");
		sb.append("				DT_COM_SYOHIN_HACHU COM ");
		sb.append("			  WHERE COM.TENPO_CD	= TEN.TENPO_CD");
		sb.append("				AND COM.TENHANKU_CD = MSP680101_GET_TENHANKUCD(TEN.TENPO_CD, '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "', '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "', '" + conv.convertWhereString( (String)map.get("mst_date_next") ) + "')");
		sb.append("				AND COM.SYOHIN_CD 	= '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'), 0) AS TEISI_SURYO_QT ");

		sb.append("	 FROM ");

		sb.append("	 	  R_TENPO TEN ");


		// 店配列が検索条件に指定されたときは店グループより抽出
		if( map.get("group_no") != null && ((String)map.get("group_no")).trim().length() > 0 ){
		
			sb.append("	INNER JOIN( ");
			sb.append("				SELECT TENPO_CD, RANK_NB ");
			sb.append("				  FROM R_TENGROUP TEN ");
			sb.append("				 INNER JOIN R_TENGROUPNO NO ");
			sb.append("				    ON TEN.L_GYOSYU_CD = NO.L_GYOSYU_CD ");
			sb.append("				   AND TEN.YOTO_KB     = NO.YOTO_KB ");
			sb.append("				   AND TEN.GROUPNO_CD  = NO.GROUPNO_CD ");
			sb.append("				 WHERE TEN.YOTO_KB     = '" + mst003901_YotoKbDictionary.HACHU.getCode() + "'");
			sb.append("				   AND TEN.GROUPNO_CD  = " + conv.convertWhereString( (String)map.get("group_no") ));
			if(map.get("selected_gyosyu_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_LIV.getCode())){
				sb.append("				   AND TEN.L_GYOSYU_CD = '0022' ");
			}else{
				sb.append("				   AND TEN.L_GYOSYU_CD = '0033' ");
			}
			sb.append("				 ORDER BY ");
			sb.append("				 	TEN.RANK_NB ");
			sb.append("			  ) GRP ");
			sb.append("	   ON TEN.TENPO_CD = GRP.TENPO_CD ");
		}


		// 処理状況「１：更新」以外は登録データのみ抽出
		if(!map.get("syori_kb").equals("0")){
			sb.append("	INNER JOIN R_JIDOHACHU_HONBU_SURYOTEISI JIDO ");
		}else{
			sb.append("	 LEFT JOIN R_JIDOHACHU_HONBU_SURYOTEISI JIDO ");
		}
		
		sb.append("	   ON TEN.TENPO_CD    = JIDO.TENPO_CD ");
		sb.append("	  AND JIDO.TENHANKU_CD = MSP680101_GET_TENHANKUCD(TEN.TENPO_CD, '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "', '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "', '" + conv.convertWhereString( (String)map.get("mst_date_next") ) + "')");
		sb.append("	  AND JIDO.SYOHIN_CD   = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");

		sb.append("	 LEFT JOIN DT_COM_SYOHIN_HACHU COM ");
		sb.append("	   ON JIDO.TENPO_CD    = COM.TENPO_CD ");
		sb.append("	  AND JIDO.TENHANKU_CD = COM.TENHANKU_CD ");
		sb.append("	  AND JIDO.SYOHIN_CD   = COM.SYOHIN_CD ");

		sb.append("	WHERE 1 = 1 ");
		sb.append("	  AND TEN.DELETE_FG = '0'");
		sb.append("	  AND TEN.TENPO_KB = '1'");
		sb.append("	  AND NVL(TEN.HEITEN_DT, '99999999') >= '" + conv.convertWhereString( (String)map.get("mst_date_next") ) + "'");


		sb.append("	ORDER BY ");

		// group_no(店配列) に対するWHERE句
		if( map.get("group_no") != null && ((String)map.get("group_no")).trim().length() > 0 ){
			sb.append("		GRP.RANK_NB ");
		}else{
			sb.append("		TEN.TENPO_CD ");
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
		
		mst680101_JidoHachuHonbuSuryoTeisiBean bean = (mst680101_JidoHachuHonbuSuryoTeisiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO ");
		sb.append("		R_JIDOHACHU_HONBU_SURYOTEISI ");
		sb.append("		( ");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" TENPO_CD");
		}
		// 店販区コード
		if( bean.getTenHankuCd() != null && bean.getTenHankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" TENHANKU_CD");
		}
		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" SYOHIN_CD");
		}
		// 有効日
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" YUKO_DT");
		}
		// 停止数量
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" TEISI_QT");
		// 置換区分
		if( bean.getChikanKb() != null && bean.getChikanKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" CHIKAN_KB");
		}

		// 作成日時
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" INSERT_TS");
		}
		// 作成者ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" INSERT_USER_ID");
		}
		// 更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_TS");
		}
		// 更新者ID
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_USER_ID");
		}

		sb.append(")VALUES(");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}

		// 店販区コード
		if( bean.getTenHankuCd() != null && bean.getTenHankuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenHankuCd() ) + "'");
		}

		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}

		// 有効日
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		// 停止数量
		if( aftKanma ) sb.append(","); else aftKanma = true;
		sb.append( bean.getTeisiQt() );
		// 置換区分
		if( bean.getChikanKb() != null && bean.getChikanKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getChikanKb() ) + "'");
		}
		// 作成日時
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		// 作成者ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		
		// 更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		// 更新者ID
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
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

		mst680101_JidoHachuHonbuSuryoTeisiBean bean = (mst680101_JidoHachuHonbuSuryoTeisiBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("		R_JIDOHACHU_HONBU_SURYOTEISI ");
		sb.append("	  SET ");


		// 有効日
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" YUKO_DT = ");
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		
		// 停止数量
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" TEISI_QT = ");
		sb.append( bean.getTeisiQt() );

		// 置換区分
		if( bean.getChikanKb() != null && bean.getChikanKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" CHIKAN_KB = ");
			sb.append("'" + conv.convertString( bean.getChikanKb() ) + "'");
		}
		// 作成日時
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" INSERT_TS = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		// 作成者ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" INSERT_USER_ID = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		// 更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_TS = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		// 更新者ID
		if( bean.getUpdateUserTs() != null && bean.getUpdateUserTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserTs() ) + "'");
		}

		sb.append(" WHERE");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" TENPO_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		// 店販区コード
		if( bean.getTenHankuCd() != null && bean.getTenHankuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" TENHANKU_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getTenHankuCd() ) + "'");
		}
		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" SYOHIN_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getSyohinCd() ) + "'");
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
		mst680101_JidoHachuHonbuSuryoTeisiBean bean = (mst680101_JidoHachuHonbuSuryoTeisiBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM ");
		sb.append("		R_JIDOHACHU_HONBU_SURYOTEISI ");
		sb.append("	WHERE TENPO_CD    = '" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append("   AND TENHANKU_CD = '" + conv.convertWhereString( bean.getTenHankuCd() ) + "'");
		sb.append("   AND SYOHIN_CD   = '" + conv.convertWhereString( bean.getSyohinCd() ) + "'");

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
