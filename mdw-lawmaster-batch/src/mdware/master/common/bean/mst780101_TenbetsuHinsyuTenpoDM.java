/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）店別品種(店舗)マスタのDMクラス</p>
 * <p>説明: 	：新ＭＤシステムで使用する店別品種(店舗)マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: 	：Copyright (c) 2005</p>
 * <p>会社名: 	：Vinculum Japan Corp.</p>
 * @author 	：VJC A.Tozaki
 * @version 	：1.0(2005/11/02)初版作成
 *                1.1(2006/01/19) D.Matsumoto PC_KBがNULLの場合の値を変更する
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

public class mst780101_TenbetsuHinsyuTenpoDM extends DataModule
{

	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	/**
	 * コンストラクタ
	 */
	public mst780101_TenbetsuHinsyuTenpoDM()
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

		mst780101_TenbetsuHinsyuTenpoBean bean = new mst780101_TenbetsuHinsyuTenpoBean();

		bean.setSentaku( "" );
		bean.setTenpoCd( rest.getString("TENPO_CD") );
		bean.setHinsyuCd( rest.getString("HINSYU_CD") );
		bean.setHinsyuNa( rest.getString("HINSYU_NA") );
		bean.setAtukaiStDt( rest.getString("ATUKAI_ST_DT") );
		bean.setAtukaiEdDt( rest.getString("ATUKAI_ED_DT") );
		bean.setPcSendKb( rest.getString("PC_SEND_KB") );
		bean.setSendDt( rest.getString("SEND_DT") );
		bean.setInsertTs( rest.getString("INSERT_TS") );
		bean.setInsertUserId( rest.getString("INSERT_USER_ID") );
		bean.setUpdateTs( rest.getString("UPDATE_TS") );
		bean.setUpdateUserId( rest.getString("UPDATE_USER_ID") );
		bean.setDeleteFg( rest.getString("DELETE_FG") );
		bean.setTanpinQt( rest.getString("TANPIN_QT") );
		bean.setHenkoDt( rest.getString("HENKO_DT") );
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
		sb.append("		TAIKEI.HINSYU_CD AS HINSYU_CD, ");
		sb.append("		MSP760101_GET_REC_HINSYUNM(TAIKEI.HINSYU_CD, 3) AS HINSYU_NA, ");
		sb.append("		TEN.ATUKAI_ST_DT AS ATUKAI_ST_DT, ");
		sb.append("		TEN.ATUKAI_ED_DT AS ATUKAI_ED_DT, ");
		
		// ＰＣ区分　デフォルト：選択状態
		// 2006/01/19 D.Matsumoto デフォルトを変更する
		//sb.append("		NVL(TEN.PC_SEND_KB, '1') AS PC_SEND_KB, ");
		if( map.get("syori_kb").equals("0")){
			sb.append("		NVL(TEN.PC_SEND_KB, '1') AS PC_SEND_KB, ");
		}else{
			sb.append("		TEN.PC_SEND_KB AS PC_SEND_KB, ");
		}
		// 2006/01/19 変更ここまで
		sb.append("		TEN.SEND_DT AS SEND_DT, ");
		sb.append("		SUBSTR(TEN.INSERT_TS, 1, 8) AS INSERT_TS, ");
		sb.append("		TEN.INSERT_USER_ID AS INSERT_USER_ID, ");
		sb.append("		SUBSTR(TEN.UPDATE_TS, 1, 8) AS UPDATE_TS, ");
		sb.append("		TEN.UPDATE_USER_ID AS UPDATE_USER_ID, ");
		sb.append("		TEN.DELETE_FG AS DELETE_FG, ");
		sb.append("		TEN.HENKO_DT AS HENKO_DT, ");
		sb.append("		NVL(C.TANPIN_QT, 0) AS TANPIN_QT ");
		
		// 商品体系マスタ
		sb.append("	 FROM ");	
		sb.append("	 	  R_SYOHIN_TAIKEI TAIKEI ");
		
		// syori_kb(処理区分) に対するWHERE句
		// 「0：新規」は店別品種マスタに登録されていない分のみ抽出
		if( map.get("syori_kb").equals("0"))
		{
			sb.append("	 LEFT JOIN R_TENHINSYU TEN ");
		}else if(map.get("syori_kb").equals("1")){
			sb.append("	INNER JOIN R_TENHINSYU TEN ");
		}else if(map.get("syori_kb").equals("2")){
			sb.append("	INNER JOIN R_TENHINSYU TEN ");
		}else if(map.get("syori_kb").equals("3")){
			sb.append("	 LEFT JOIN R_TENHINSYU TEN ");
		}



		sb.append("	   ON TAIKEI.HINSYU_CD    = TEN.HINSYU_CD ");
		sb.append("	  AND TEN.TENPO_CD = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		sb.append("	  AND TEN.DELETE_FG = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");

		// 単品数取得
		sb.append("	 LEFT JOIN ");
		sb.append("	 ( ");
		sb.append("	 	SELECT ");
		sb.append("	 		B.HINSYU_CD, ");
		sb.append("	 		COUNT(*) AS TANPIN_QT ");
		sb.append("	 	  FROM ");
		sb.append("	 	  	( ");
		sb.append("	 	  		SELECT ");
		sb.append("	 	  			TAIKEI.HINSYU_CD, ");
		sb.append("	 	  			HIN.HANKU_CD, ");
		sb.append("	 	  			HIN.SYOHIN_CD, ");
		sb.append("	 	  			HIN.YUKO_DT ");
		sb.append("	 	  		  FROM ");
		sb.append("	 	  		  	R_SYOHIN_TAIKEI TAIKEI ");
		sb.append("	 	  		 INNER JOIN R_SYOHIN HIN ");
		sb.append("	 	  		    ON TAIKEI.HINSYU_CD = HIN.HINSYU_CD ");
		sb.append("	 	  		   AND TAIKEI.HANKU_CD = HIN.HANKU_CD ");
		sb.append("	 	  		 INNER JOIN R_TANPINTEN_TORIATUKAI ATUKAI ");
		sb.append("	 	  		    ON HIN.HANKU_CD = ATUKAI.HANKU_CD ");
		sb.append("	 	  		   AND HIN.SYOHIN_CD = ATUKAI.SYOHIN_CD ");
		sb.append("	 	  		 WHERE TAIKEI.HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		sb.append("	 	  		   AND HIN.HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		sb.append("	 	  		   AND ATUKAI.TENPO_CD = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		sb.append("	 	  		   AND ATUKAI.DELETE_FG = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append("	 	  	) B ");
		sb.append("	 	 WHERE 1 = 1 ");
		sb.append("	 	   AND B.YUKO_DT = MSP710101_GETSYOHINYUKODT(B.HANKU_CD, B.SYOHIN_CD, '" + conv.convertWhereString( (String)map.get("mst_date") ) + "') ");
		sb.append("	 	 GROUP BY ");
		sb.append("	 	 	B.HINSYU_CD ");
		sb.append("	 ) C ");

		sb.append("	   ON TAIKEI.HINSYU_CD = C.HINSYU_CD ");


		sb.append("	WHERE 1 = 1 ");

		whereStr = andStr;

		// tenpo_cd(店舗コード) に対するWHERE句
		if( map.get("hanku_cd") != null && ((String)map.get("hanku_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TAIKEI.HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
			whereStr = andStr;
		}

		// syori_kb(処理区分) に対するWHERE句
		// 「0：新規」以外は店別品種マスタに登録されている分のみ抽出
		if( map.get("syori_kb").equals("2"))
		{
			sb.append(whereStr);
			sb.append("TEN.TENPO_CD = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			whereStr = andStr;
		}

		// syori_kb(処理区分) に対するWHERE句
		// 「0：新規」は店別品種マスタに登録されていない分のみ抽出
		if( map.get("syori_kb").equals("0"))
		{
			sb.append(whereStr);
			sb.append("TEN.HINSYU_CD IS NULL ");
			whereStr = andStr;
		}

//		sb.append(whereStr);
//		sb.append("TANPIN_QT <> 0 ");
		

		sb.append("	ORDER BY ");
		sb.append("		TAIKEI.HINSYU_CD ");

		return sb.toString();
	}


	/**
	 * 検索用ＳＱＬの生成を行う。(単品数取得用)
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectTanpinQtSql( Map map )
	{

		String whereStr = "where ";
		String andStr = " and ";
		String wk = "";

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("		NVL(SUM(C.TANPIN_QT), 0) AS TANPIN_QT_TOTAL ");
		sb.append("	 FROM ");
		// 単品数取得
		sb.append("	 ( ");
		sb.append("	 	SELECT ");
		sb.append("	 		B.HINSYU_CD, ");
		sb.append("	 		COUNT(*) AS TANPIN_QT ");
		sb.append("	 	  FROM ");
		sb.append("	 	  	( ");
		sb.append("	 	  		SELECT ");
		sb.append("	 	  			TAIKEI.HINSYU_CD, ");
		sb.append("	 	  			HIN.HANKU_CD, ");
		sb.append("	 	  			HIN.SYOHIN_CD, ");
		sb.append("	 	  			HIN.YUKO_DT ");
		sb.append("	 	  		  FROM ");
		sb.append("	 	  		  	R_SYOHIN_TAIKEI TAIKEI ");
		sb.append("	 	  		 INNER JOIN R_SYOHIN HIN ");
		sb.append("	 	  		    ON TAIKEI.HINSYU_CD = HIN.HINSYU_CD ");
		sb.append("	 	  		   AND TAIKEI.HANKU_CD = HIN.HANKU_CD ");
		sb.append("	 	  		 INNER JOIN R_TANPINTEN_TORIATUKAI ATUKAI ");
		sb.append("	 	  		    ON HIN.HANKU_CD = ATUKAI.HANKU_CD ");
		sb.append("	 	  		   AND HIN.SYOHIN_CD = ATUKAI.SYOHIN_CD ");
		sb.append("	 	  		 WHERE TAIKEI.HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		sb.append("	 	  		   AND HIN.HANKU_CD = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "'");
		sb.append("	 	  		   AND ATUKAI.TENPO_CD = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		sb.append("	 	  		   AND ATUKAI.DELETE_FG = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append("	 	  	) B ");
		sb.append("	 	 WHERE 1 = 1 ");
		sb.append("	 	   AND B.YUKO_DT =  MSP710101_GETSYOHINYUKODT(B.HANKU_CD, B.SYOHIN_CD, '" + conv.convertWhereString( (String)map.get("mst_date") ) + "') ");
		sb.append("	 	 GROUP BY ");
		sb.append("	 	 	B.HINSYU_CD ");
		sb.append("	 ) C ");

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
		
		mst780101_TenbetsuHinsyuTenpoBean bean = (mst780101_TenbetsuHinsyuTenpoBean)beanMst;
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO ");
		sb.append("		R_TENHINSYU ");
		sb.append("		( ");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" TENPO_CD");
		}
		// 品種コード
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" HINSYU_CD");
		}
		// 取扱開始日
		if( bean.getAtukaiStDt() != null && bean.getAtukaiStDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ATUKAI_ST_DT");
		}
		// 取扱終了日
		if( bean.getAtukaiEdDt() != null && bean.getAtukaiEdDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" ATUKAI_ED_DT");
		}
		// ＰＣ送信区分
		if( bean.getPcSendKb() != null && bean.getPcSendKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" PC_SEND_KB");
		}
		// 送信日
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" SEND_DT");
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
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_USER_ID");
		}
		// 削除フラグ
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" DELETE_FG");
		}
		// 変更日
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" HENKO_DT ");
		}

		sb.append(")VALUES(");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
		}
		// 品種コード
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHinsyuCd() ) + "'");
		}
		// 取扱開始日
		if( bean.getAtukaiStDt() != null && bean.getAtukaiStDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAtukaiStDt() ) + "'");
		}
		// 取扱終了日
		if( bean.getAtukaiEdDt() != null && bean.getAtukaiEdDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getAtukaiEdDt() ) + "'");
		}
		// ＰＣ送信区分
		if( bean.getPcSendKb() != null && bean.getPcSendKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getPcSendKb() ) + "'");
		}
		// 送信日
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
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
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		// 削除フラグ
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
		// 変更日
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
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

		mst780101_TenbetsuHinsyuTenpoBean bean = (mst780101_TenbetsuHinsyuTenpoBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("		R_TENHINSYU ");
		sb.append("	  SET ");

		// 取扱開始日
		if( bean.getAtukaiStDt() != null && bean.getAtukaiStDt().trim().length() != 0 )
		{
			befKanma = true;
			sb.append("	ATUKAI_ST_DT = ");
			sb.append("'" + conv.convertString( bean.getAtukaiStDt() ) + "'");
		}
		// 取扱終了日
		if( bean.getAtukaiEdDt() != null && bean.getAtukaiEdDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	ATUKAI_ED_DT = ");
			sb.append("'" + conv.convertString( bean.getAtukaiEdDt() ) + "'");
		}
		// ＰＣ送信区分
		if( bean.getPcSendKb() != null && bean.getPcSendKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	PC_SEND_KB = ");
			sb.append("'" + conv.convertString( bean.getPcSendKb() ) + "'");
		}
		// 更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_TS = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		// 更新者ID
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		// 変更日
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" HENKO_DT = ");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}

		sb.append(" WHERE");

		// 店舗コード
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" TENPO_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		}
		// 品種コード
		if( bean.getHinsyuCd() != null && bean.getHinsyuCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" HINSYU_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");
		}

		return sb.toString();
	}


	/**
	 * 論理削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getLogicalDeleteSql( Object beanMst )
	{
		mst780101_TenbetsuHinsyuTenpoBean bean = (mst780101_TenbetsuHinsyuTenpoBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("		R_TENHINSYU ");
		sb.append("	  SET DELETE_FG = '" + conv.convertWhereString( bean.getDeleteFg() ) + "'");

		// 更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_TS = ");
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		// 更新者ID
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		// 変更日
		if( bean.getHenkoDt() != null && bean.getHenkoDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" HENKO_DT = ");
			sb.append("'" + conv.convertString( bean.getHenkoDt() ) + "'");
		}

		sb.append("	WHERE TENPO_CD    = '" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append("   AND HINSYU_CD = '" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");

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
		mst780101_TenbetsuHinsyuTenpoBean bean = (mst780101_TenbetsuHinsyuTenpoBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM R_TENHINSYU ");
		sb.append("	WHERE TENPO_CD 	= '" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		sb.append("   AND HINSYU_CD	= '" + conv.convertWhereString( bean.getHinsyuCd() ) + "'");

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
