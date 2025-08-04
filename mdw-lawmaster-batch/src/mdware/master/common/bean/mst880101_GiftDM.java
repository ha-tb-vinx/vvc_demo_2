package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst008601_SentakuGyosyuCdDictionary;
import mdware.master.common.dictionary.mst008901_GiftSelectCodeDictionary;

/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）ギフトマスタのDMクラス</p>
 * <p>説明: 	：新ＭＤシステムで使用するギフトマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: 	：Copyright (c) 2005</p>
 * <p>会社名: 	：Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */

public class mst880101_GiftDM extends DataModule
{

	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	/**
	 * コンストラクタ
	 */
	public mst880101_GiftDM()
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

		mst880101_GiftBean bean = new mst880101_GiftBean();

		bean.setSentaku( "" );
		bean.setGiftCd( rest.getString("GIFT_CD") );
		bean.setHankuCd( rest.getString("HANKU_CD") );
		bean.setHankuNa( rest.getString("HANKU_NA") );
		bean.setSyohinCd( rest.getString("SYOHIN_CD") );
		bean.setJanCd( rest.getString("JAN_CD") );
		bean.setSyohinNa( rest.getString("SYOHIN_NA") );
		bean.setBaitankaVl( rest.getString("BAITANKA_VL") );
		bean.setHinsyuCd( rest.getString("HINSYU_CD") );
		bean.setHinsyuNa( rest.getString("HINSYU_NA") );
		bean.setAtukaiStDt( rest.getString("ATUKAI_ST_DT") );
		bean.setAtukaiEdDt( rest.getString("ATUKAI_ED_DT") );
		bean.setSendDt( rest.getString("SEND_DT") );
		bean.setInsertTs( rest.getString("INSERT_TS") );
		if (mst000401_LogicBean.chkNullString(rest.getString("INSERT_USER_NA")).length() == 0) {
			bean.setInsertUserId( rest.getString("INSERT_USER_ID") );
		}else{
			bean.setInsertUserId( rest.getString("INSERT_USER_NA") );
		}
		bean.setUpdateTs( rest.getString("UPDATE_TS") );
		if (mst000401_LogicBean.chkNullString(rest.getString("UPDATE_USER_NA")).length() == 0) {
			bean.setUpdateUserId( rest.getString("UPDATE_USER_ID") );
		}else{
			bean.setUpdateUserId( rest.getString("UPDATE_USER_NA") );
		}
		bean.setDeleteFg( rest.getString("DELETE_FG") );
		bean.setHenkoDt( rest.getString("HENKO_DT") );
		bean.setKikakuKanjiNa(rest.getString("KIKAKU_KANJI_NA"));
		bean.setCreateDatabase();
		return bean;
	}


	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE句を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map )
	{

		String whereStr = "WHERE ";
		String andStr = " AND ";
		String wk = "";

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("			GIFT.SYOHIN_CD 				 AS SYOHIN_CD, ");
		sb.append("			GIFT.GIFT_CD				 AS GIFT_CD, ");
		sb.append("			GIFT.HANKU_CD				 AS HANKU_CD, ");
		sb.append("			CTF.KANJI_RN 				 AS HANKU_NA, ");
		sb.append("			HIN.SYOHIN_2_CD				 AS JAN_CD, ");
		sb.append("			HIN.HINMEI_KANJI_NA 		 AS SYOHIN_NA, ");
		sb.append("			HIN.BAITANKA_VL 			 AS BAITANKA_VL, ");
		sb.append("			HIN.HINSYU_CD 				 AS HINSYU_CD, ");
		sb.append("			HIN.KIKAKU_KANJI_NA 		 AS KIKAKU_KANJI_NA, ");
		sb.append("			GIFT.ATUKAI_ST_DT 			 AS ATUKAI_ST_DT, ");
		sb.append("			GIFT.ATUKAI_ED_DT 			 AS ATUKAI_ED_DT, ");
		sb.append("			GIFT.SEND_DT 				 AS SEND_DT, ");
		sb.append("			SUBSTR(GIFT.INSERT_TS, 1, 8) AS INSERT_TS, ");
		sb.append("			GIFT.INSERT_USER_ID 		 AS INSERT_USER_ID, ");
		sb.append("			SOSASYA1.USER_NA			 AS INSERT_USER_NA,");
		sb.append("			SUBSTR(GIFT.UPDATE_TS, 1, 8) AS UPDATE_TS, ");
		sb.append("			GIFT.UPDATE_USER_ID 		 AS UPDATE_USER_ID, ");
		sb.append("			SOSASYA2.USER_NA			 AS UPDATE_USER_NA,");
		sb.append("			GIFT.DELETE_FG 				 AS DELETE_FG, ");
		sb.append("			GIFT.HENKO_DT 				 AS HENKO_DT, ");
		sb.append("			CTF2.KANJI_RN 				 AS HINSYU_NA ");

		// ギフトマスタ
		sb.append("	 	FROM ");
		sb.append("	 	  	R_GIFT GIFT ");

		// 商品マスタ
		sb.append("		INNER JOIN R_SYOHIN HIN ");
		sb.append("	   		ON GIFT.HANKU_CD = HIN.HANKU_CD ");
		sb.append("	  		AND GIFT.SYOHIN_CD = HIN.SYOHIN_CD ");
		sb.append("	  		AND HIN.YUKO_DT = MSP710101_GETSYOHINYUKODT(HIN.HANKU_CD, HIN.SYOHIN_CD,'" + conv.convertWhereString( (String)map.get("mst_date") ) + "') ");


		// selected_gyosyu_cd に対するWHERE句
		if( map.get("selected_gyosyu_cd") != null && ((String)map.get("selected_gyosyu_cd")).trim().length() > 0 )
		{

			if((map.get("selected_gyosyu_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_IRO.getCode()))){

				sb.append(" AND GYOSYU_KB IN('" + mst000601_GyoshuKbDictionary.A08.getCode() + "','" + mst000601_GyoshuKbDictionary.A12.getCode() + "') ");

			}else if((map.get("selected_gyosyu_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_LIV.getCode()))){

				sb.append(" AND GYOSYU_KB IN('" + mst000601_GyoshuKbDictionary.LIV.getCode() + "') ");

			}else if((map.get("selected_gyosyu_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_GRO.getCode()))){

				sb.append(" AND GYOSYU_KB IN('" + mst000601_GyoshuKbDictionary.GRO.getCode() + "') ");

			}else if((map.get("selected_gyosyu_cd").equals(mst008601_SentakuGyosyuCdDictionary.SEL_FRE.getCode()))){

				sb.append(" AND GYOSYU_KB IN('" + mst000601_GyoshuKbDictionary.FRE.getCode() + "') ");

			}else{

				sb.append(" AND GYOSYU_KB IN('UNKNOWN') ");
			}
		}
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		// 名称ＣＴＦマスタ
		sb.append("			INNER JOIN R_NAMECTF CTF ");
		sb.append("	  			 ON GIFT.HANKU_CD = CTF.CODE_CD ");
		sb.append("			AND CTF.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");
		sb.append("	  		AND GIFT.DELETE_FG = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");

		whereStr = andStr;
		if (mst008901_GiftSelectCodeDictionary.HANKU.getCode().equals((String)map.get("select_kb"))) {
			if (map.get("select_cd") != null && ((String)map.get("select_cd")).trim().length() > 0 ) {
				//販区の場合の指定
				sb.append(whereStr);
				sb.append("GIFT.HANKU_CD = '" + conv.convertWhereString( (String)map.get("select_cd") ) + "'");
				whereStr = andStr;
			}
		}else if (mst008901_GiftSelectCodeDictionary.HINSYU.getCode().equals((String)map.get("select_kb"))) {
				//品種の場合の指定
			if (map.get("select_cd") != null && ((String)map.get("select_cd")).trim().length() > 0 ) {
				sb.append(whereStr);
				sb.append("HIN.HINSYU_CD = '" + conv.convertWhereString( (String)map.get("select_cd") ) + "'");
				whereStr = andStr;
			}
		}else if (mst008901_GiftSelectCodeDictionary.GIFT.getCode().equals((String)map.get("select_kb"))) {
			// gift_cd(ギフトコード) に対するWHERE句
			if( map.get("gift_cd") != null && ((String)map.get("gift_cd")).trim().length() > 0 ){
				sb.append(whereStr);
				sb.append("GIFT.GIFT_CD = '" + conv.convertWhereString( (String)map.get("gift_cd") ) + "'");
				whereStr = andStr;
			}
		}else if (mst008901_GiftSelectCodeDictionary.SYOHIN.getCode().equals((String)map.get("select_kb"))) {
			// 商品コード に対するWHERE句
			if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 ){
				sb.append(whereStr);
				sb.append("GIFT.SYOHIN_CD = '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "'");
				whereStr = andStr;
			}
		}
		sb.append(" INNER JOIN R_NAMECTF CTF2 ");
		sb.append(" 	ON HIN.HINSYU_CD = CTF2.CODE_CD ");
		sb.append(" AND CTF2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");
		sb.append(" LEFT JOIN SYS_SOSASYA SOSASYA1 ");
		sb.append(" 	ON GIFT.INSERT_USER_ID = SOSASYA1.USER_ID ");
		sb.append(" LEFT JOIN SYS_SOSASYA SOSASYA2 ");
		sb.append(" 	ON GIFT.UPDATE_USER_ID = SOSASYA2.USER_ID ");
		sb.append("	ORDER BY ");
		sb.append("		GIFT.GIFT_CD ");



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

		mst880101_GiftBean bean = (mst880101_GiftBean)beanMst;
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO ");
		sb.append("		R_GIFT ");
		sb.append("		( ");

		// ギフトコード
		if( bean.getGiftCd() != null && bean.getGiftCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" GIFT_CD");
		}
		// 販区コード
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" HANKU_CD");
		}
		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" SYOHIN_CD");
		}

		// 作成日時
		sb.append(",");
		sb.append(" INSERT_TS");

		// 作成者ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" INSERT_USER_ID");
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
		// 送信日
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append(" SEND_DT ");
		}

		sb.append(")VALUES(");

		// ギフトコード
		if( bean.getGiftCd() != null && bean.getGiftCd().trim().length() != 0 )
		{
			aftKanma = true;
			sb.append("'" + conv.convertString( bean.getGiftCd() ) + "'");
		}
		// 販区コード
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		// 作成日時
		sb.append(",");
		sb.append(" TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
		// 作成者ID
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
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

		// 送信日
		if( bean.getSendDt() != null && bean.getSendDt().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
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

		mst880101_GiftBean bean = (mst880101_GiftBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("		R_GIFT ");
		sb.append("	  SET ");


		// 販区コード
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append("	HANKU_CD = ");
			sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		// 商品コード
		if( bean.getSyohinCd() != null && bean.getSyohinCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	SYOHIN_CD = ");
			sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		}
		// 更新日時
		sb.append(",");
		sb.append(" UPDATE_TS = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");

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

		// ギフトコード
		if( bean.getGiftCd() != null && bean.getGiftCd().trim().length() != 0 )
		{
			whereAnd = true;
			sb.append(" GIFT_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getGiftCd() ) + "'");
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
		mst880101_GiftBean bean = (mst880101_GiftBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append("		R_GIFT ");
		sb.append("	  SET DELETE_FG = '" + conv.convertWhereString( bean.getDeleteFg() ) + "'");

		// 更新日時
		sb.append(",");
		sb.append(" UPDATE_TS = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
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


		sb.append("	WHERE GIFT_CD    = '" + conv.convertWhereString( bean.getGiftCd() ) + "'");

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
		mst880101_GiftBean bean = (mst880101_GiftBean)beanMst;

		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM R_GIFT ");
		sb.append("	WHERE GIFT_CD 	= '" + conv.convertWhereString( bean.getGiftCd() ) + "'");

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

	/**
	 * 新規登録時の商品情報取得のためのＳＱＬを返します。
	 *
	 * @param syouhin_cd			商品コード
	 * @param mstDate				送信日
	 * @param strSelectedGyosyuCd	業種別コード
	 *
	 * @return　新規登録時の商品情報取得SQL
	 */
	public String getSyohinInfoSql(String syouhin_cd, String mstDate, String strSelectedGyosyuCd) {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("		HIN.SYOHIN_CD				 AS SYOHIN_CD, ");
		sb.append("		HIN.SYOHIN_2_CD				 AS JAN_CD, ");
		sb.append("		HIN.HINMEI_KANJI_NA 		 AS SYOHIN_NA, ");
		sb.append("		HIN.BAITANKA_VL 			 AS BAITANKA_VL, ");
		sb.append("		HIN.HINSYU_CD 				 AS HINSYU_CD, ");
		sb.append("		CTF2. KANJI_RN				 AS HINSYU_NA, ");
		sb.append("		HIN.HANKU_CD				 AS HANKU_CD,");
		sb.append("		HIN.KIKAKU_KANJI_NA			 AS KIKAKU_KANJI_NA,");
		sb.append("		HIN.SYOHIN_KB				 AS SYOHIN_KB,");
		sb.append("		CTF.KANJI_RN 				 AS HANKU_NA ");
		sb.append("FROM ");
		sb.append("			R_SYOHIN HIN INNER JOIN R_NAMECTF CTF ON HIN.HANKU_CD = CTF.CODE_CD ");
		sb.append("		AND CTF.SYUBETU_NO_CD =	'" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");
		sb.append("		AND	HIN.YUKO_DT = MSP710101_GETSYOHINYUKODT(HIN.HANKU_CD, HIN.SYOHIN_CD,'" + conv.convertWhereString( mstDate ) + "') ");
		sb.append("		AND HIN.SYOHIN_CD = '" + syouhin_cd + "'");
		sb.append("		AND HIN.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
		// 選択された業種
		if(strSelectedGyosyuCd.equals(mst008601_SentakuGyosyuCdDictionary.SEL_LIV.getCode())){
			//住居
			sb.append("    AND HIN.GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.LIV.getCode() + "'");
		}else if(strSelectedGyosyuCd.equals(mst008601_SentakuGyosyuCdDictionary.SEL_GRO.getCode())){
			//加工食品
			sb.append("    AND HIN.GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.GRO.getCode() + "'");
		}else if(strSelectedGyosyuCd.equals(mst008601_SentakuGyosyuCdDictionary.SEL_FRE.getCode())){
			//生鮮
			sb.append("    AND HIN.GYOSYU_KB = '" + mst000601_GyoshuKbDictionary.FRE.getCode() + "'");
		}else{
			//衣料
			sb.append("    AND GYOSYU_KB IN( '" + mst000601_GyoshuKbDictionary.A08.getCode() + "', '" + mst000601_GyoshuKbDictionary.A12.getCode() + "')");
		}
		sb.append("		INNER JOIN R_NAMECTF CTF2 ON HIN.HINSYU_CD = CTF2.CODE_CD ");
		sb.append("		AND CTF2.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'");


		return sb.toString();
	}

	/**
	 * ギフトマスタの情報を取得するためのＳＱＬを返します。
	 *
	 * @param checkCD		チェックするコード
	 * @param FLG			チェックするコードが商品コードかギフトコードのどちらかを指定します。
	 * 						0:ギフトコード
	 * 						1:商品コード
	 * @param DelFlg		0:デリートチェックしない
	 * 						1:デリートチェックする
	 * @return			ギフトマスタの情報を取得するためのＳＱＬ
	 */
	public String getGiftMastaInfoSql(String checkCD, String FLG, String DelFlg) {
		StringBuffer sb = new StringBuffer();
		// コードチェック
		sb.append(" SELECT ");
		sb.append("     R.GIFT_CD         			AS        GIFT_CD         ,");
		sb.append("     R.HANKU_CD        			AS        HANKU_CD        ,");
		sb.append("     R.SYOHIN_CD       			AS        SYOHIN_CD       ,");
		sb.append("     R.ATUKAI_ST_DT    			AS        ATUKAI_ST_DT    ,");
		sb.append("     R.ATUKAI_ED_DT    			AS        ATUKAI_ED_DT    ,");
		sb.append("     R.SEND_DT         			AS        SEND_DT         ,");
		sb.append("     SUBSTR(R.INSERT_TS, 1, 8)   AS        INSERT_TS       ,");
		sb.append("     R.INSERT_USER_ID  			AS        INSERT_USER_ID  ,");
		sb.append("		SOSASYA1.USER_NA			AS 		  INSERT_USER_NA  ,");
		sb.append("     SUBSTR(R.UPDATE_TS, 1, 8)   AS        UPDATE_TS       ,");
		sb.append("     R.UPDATE_USER_ID  			AS        UPDATE_USER_ID  ,");
		sb.append("		SOSASYA2.USER_NA			AS 		  UPDATE_USER_NA  ,");
		sb.append("     R.DELETE_FG       			AS        DELETE_FG       ,");
		sb.append("     R.HENKO_DT        			AS        HENKO_DT        ");
		sb.append("   FROM ");
		sb.append("     R_GIFT R");
		sb.append(" LEFT JOIN SYS_SOSASYA SOSASYA1 ");
		sb.append(" 	ON R.INSERT_USER_ID = SOSASYA1.USER_ID ");
		sb.append(" LEFT JOIN SYS_SOSASYA SOSASYA2 ");
		sb.append(" 	ON R.UPDATE_USER_ID = SOSASYA2.USER_ID ");
		sb.append("  WHERE ");
		if ("0".equals(FLG)) {
			sb.append(" 	R.GIFT_CD  = '" + checkCD + "'");
		}else{
			sb.append(" 	R.SYOHIN_CD = '" + checkCD + "'");
		}
		if ("1".equals(DelFlg)) {
			sb.append(" AND");
			sb.append("	R.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		}
		return sb.toString();
	}

}
