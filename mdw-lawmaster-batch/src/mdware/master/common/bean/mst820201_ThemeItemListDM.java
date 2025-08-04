/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820201_ThemeItem用添テーマ別アイテム一覧の表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820201_ThemeItem用テーマ別アイテム一覧の表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし
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

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820201_ThemeItem用テーマ別アイテム一覧の表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820201_ThemeItem用テーマ別アイテム一覧の表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 *
 */
public class mst820201_ThemeItemListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst820201_ThemeItemListDM()
	{
		super(mst000101_ConstDictionary.CONNECTION_STR);
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
		mst820201_ThemeItemListBean bean = new mst820201_ThemeItemListBean();

		bean.setSyohinYobidasi( rest.getString("syohin_yobidasi") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setKeiryokiNa( rest.getString("keiryoki_na") );
		bean.setBackLabelKb( rest.getString("back_label_kb") );
		bean.setSyomikikanVl( rest.getString("syomikikan_vl") );
		bean.setSyomikikanKb( rest.getString("syomikikan_kb") );
		bean.setHozonOndotaiKb( rest.getString("hozon_ondotai_kb") );
		bean.setCommentKb( rest.getString("comment_kb") );
		bean.setBikoTx( rest.getString("biko_tx") );
		bean.setTeigakuVl( rest.getString("teigaku_vl") );
		bean.setTeigakujiTaniKb( rest.getString("teigakuji_tani_kb") );
		bean.setTeigakuUpKb( rest.getString("teigaku_up_kb") );
		bean.setTankaVl( rest.getString("tanka_vl") );
		bean.setUpdateTs( rest.getString("update_ts") );

		bean.setTenkabutuNm( rest.getString("TENKABUTU_KB") );

		bean.setSantiKb(rest.getString("SANTI_KB") );
		bean.setChoriyoKokokubunKb(rest.getString("CHORIYO_KOKOKUBUN_KB") );
		//2006-03-29 M.Kawamoto 追加 計量単位
		bean.setKeiRyoUnitName( rest.getString("KeiRyoUnitName") );
		//
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
//		kawamoto
//		long cnt		=0;
	 	long start		= 0;
//	  	long end		= 0;
		long maxrows	= 9999;
			  String blank = (String)map.get("h_blankkb");

//			  kawamoto
//			  start		= 1;
//			  end		= 10000;
//			  kawamoto
		 start = Long.parseLong((String)map.get("start_rows"));
//		 end   = Long.parseLong((String)map.get("end_rows"));
		 maxrows = maxrows - start;
//		 if(end > maxrows + 1){
//			  end   = maxrows + 1 ;
//		  }
//		ここまで
//		kawamoto
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append("SELECT ");
		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			sb.append("	TO_CHAR(TBL1.CD,'FM0000') SYOHIN_YOBIDASI, ");
		} else {
			sb.append("	TBL2.SYOHIN_YOBIDASI, ");
		}
//2006-03-29 M.Kawamoto 追加 計量単位
		sb.append("	TBL2.KeiRyoUnitName, ");
//ここまで
		sb.append("	TBL2.HINSYU_CD, ");
		sb.append("	TBL2.SYOHIN_CD, ");
		sb.append("	TBL2.SYOHIN_CD, ");
		sb.append("	TBL2.KEIRYOKI_NA, ");
		sb.append("	TBL2.BACK_LABEL_KB, ");
		sb.append("	TBL2.SYOMIKIKAN_VL, ");
		sb.append("	TBL2.SYOMIKIKAN_KB, ");
		sb.append("	TBL2.HOZON_ONDOTAI_KB, ");
		sb.append("	TBL2.COMMENT_KB,");
		sb.append("	TBL2.BIKO_TX, ");
		sb.append("	TBL2.TEIGAKU_VL, ");
		sb.append("	TBL2.TEIGAKUJI_TANI_KB, ");
		sb.append("	TBL2.TEIGAKU_UP_KB, ");
		sb.append("	TBL2.TANKA_VL, ");
		sb.append("	TBL2.UPDATE_TS, ");
		sb.append("	TBL2.TENKABUTU_KB, ");
		sb.append("	TBL2.SANTI_KB, ");
		sb.append("	TBL2.CHORIYO_KOKOKUBUN_KB ");


		sb.append("FROM ");

		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){

			sb.append("	( SELECT CD FROM (");
//2006-03-27 M.Kawamoto ページ遷移ごとにSQLを発行させないようにする
//			for(cnt=start; cnt < 70 ; cnt++ ){
//				if(cnt!=start){
//					sb.append("     UNION ALL ");
//				}
//				sb.append("		SELECT " + cnt + " AS CD FROM DUAL ");
//			}
			sb.append("SELECT TO_NUMBER(TANPIN_CD) AS CD FROM R_TANPIN_WAKU_5KETA ");
			sb.append("ORDER BY TANPIN_CD )");
			sb.append("WHERE ROWNUM <= " + maxrows + " ");
			sb.append("AND CD >= " + start);
			sb.append("	) TBL1, ");
		}
		sb.append("	( ");
		sb.append("SELECT ");
		sb.append("	TBL1.SYOHIN_YOBIDASI, ");
		sb.append(" ( ");
		sb.append("		SELECT ");
		sb.append("			HINSYU_CD ");
		sb.append("		FROM ");
		sb.append("			( ");
		sb.append("				SELECT ");
		sb.append("					SYOHIN_CD, ");
		sb.append("					HINSYU_CD ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN ");
		sb.append("				ORDER BY ");
		sb.append("					HANKU_CD,SYOHIN_CD,YUKO_DT ");
		sb.append("			) ");
		sb.append("		WHERE ");
		sb.append("			SYOHIN_CD = TBL1.SYOHIN_CD ");
		sb.append("		AND	ROWNUM <= 1 ");
		sb.append(" ) HINSYU_CD, ");
		sb.append("	TBL1.SYOHIN_CD, ");
		sb.append("	TBL1.KEIRYOKI_NA, ");
		sb.append("	TBL1.BACK_LABEL_KB, ");
		sb.append("	TBL1.SYOMIKIKAN_VL, ");
		sb.append("	TBL1.SYOMIKIKAN_KB, ");
		sb.append("	TBL1.HOZON_ONDOTAI_KB, ");
		sb.append("	TBL1.COMMENT_KB, ");
		sb.append("	TBL1.BIKO_TX, ");
		sb.append("	TBL1.TEIGAKU_VL, ");
		sb.append("	TBL1.TEIGAKUJI_TANI_KB, ");
		sb.append("	TBL1.TEIGAKU_UP_KB, ");
		sb.append("	TBL1.TANKA_VL, ");
		sb.append("	TBL1.UPDATE_TS, ");
		//添加物
		sb.append(" (SELECT SUBSTR(nmctf1.ZOKUSEI_CD, 1, 1) ");
		sb.append("  FROM R_NAMECTF nmctf1");
		sb.append("  WHERE nmctf1.SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.BACK_LABEL_ITEM_SENTENCE, userLocal) +"'");
		sb.append("  AND nmctf1.CODE_CD = (SELECT SUBSTR(nmctf2.ZOKUSEI_CD, 2, 1) || TBL1.back_label_kb ");
		sb.append("                FROM R_NAMECTF nmctf2");
		sb.append("                WHERE nmctf2.SYUBETU_NO_CD = '"+ MessageUtil.getMessage(mst000101_ConstDictionary.MEASUREMENT_SALES_DIVISION, userLocal) +"'");
		sb.append("                AND nmctf2.CODE_CD = TBL1.keiryo_hanku_cd)");
		sb.append(" ) as TENKABUTU_KB,  ");
//2006-03-29 M.Kawamoto 追加 計量単位
  		sb.append("	(SELECT nmctf3.KANJI_NA");
		sb.append("	FROM R_NAMECTF nmctf3");
		// s_gyosyu_cd に対するWHERE区
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append("	WHERE nmctf3.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.UNIT_OF_FIXED_AMOUNT, userLocal) + "'");
		}
		sb.append("	AND");
		if( map.get("keiryo_type_cd") != null && ((String)map.get("keiryo_type_cd")).trim().length() > 0 )
		{
			sb.append("	nmctf3.CODE_CD =  '" + conv.convertWhereString( (String)map.get("keiryo_type_cd") ) + "'");
		}
		sb.append("	|| TBL1.TEIGAKUJI_TANI_KB");
		sb.append("	AND");
		sb.append(" nmctf3.CODE_CD not in ( '0000' )");
		sb.append("	AND");
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
		sb.append("	nmctf3.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}
		sb.append("	) as KeiRyoUnitName, ");
//ここまで　追加

		sb.append("	TBL1.SANTI_KB, ");
		sb.append("	TBL1.CHORIYO_KOKOKUBUN_KB ");

		sb.append(" FROM ");
		sb.append("	R_KEIRYOKI TBL1 ");
		sb.append(" WHERE ");
		sb.append(" TBL1.DELETE_FG = '0' ");
		// s_gyosyu_cd に対するWHERE区
		if( map.get("s_gyosyu_cd") != null && ((String)map.get("s_gyosyu_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("TBL1.S_GYOSYU_CD = '" + conv.convertWhereString( (String)map.get("s_gyosyu_cd") ) + "'");
		}

		// theme_cd に対するWHERE区
		if( map.get("theme_cd") != null && ((String)map.get("theme_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("TBL1.THEME_CD = '" + conv.convertWhereString( (String)map.get("theme_cd") ) + "'");
		}

		// keiryo_hanku_cd に対するWHERE区
		if( map.get("keiryo_hanku_cd") != null && ((String)map.get("keiryo_hanku_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("TBL1.KEIRYO_HANKU_CD = '" + conv.convertWhereString( (String)map.get("keiryo_hanku_cd") ) + "'");
		}

		// syohin_yobidasi に対するWHERE区
		if( map.get("syohin_yobidasi") != null && ((String)map.get("syohin_yobidasi")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("TBL1.SYOHIN_YOBIDASI >= '" + conv.convertWhereString( (String)map.get("syohin_yobidasi") ) + "'");
		}
		sb.append("	) TBL2 ");
//
  		sb.append("	WHERE ");
		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			sb.append(" TBL1.CD = TBL2.SYOHIN_YOBIDASI(+) ");
		}else{
			long subStartNo = start - 1;
			sb.append("TBL2.SYOHIN_YOBIDASI >= " + start + "");
		}

		sb.append("ORDER BY ");
//
		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			sb.append("	TBL1.CD, ");
		} else {
			sb.append("	TBL2.SYOHIN_YOBIDASI, ");
		}
		sb.append("	TBL2.HINSYU_CD, ");
		sb.append("	TBL2.SYOHIN_CD ");

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
