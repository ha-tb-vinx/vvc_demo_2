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

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820201_ThemeItem用テーマ別アイテム一覧の表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820201_ThemeItem用テーマ別アイテム一覧の表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst820801_KeiryokiListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst820801_KeiryokiListDM()
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
		mst820801_KeiryokiListBean bean = new mst820801_KeiryokiListBean();
		bean.setSyohinYobidasi( rest.getString("syohin_yobidasi") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setKeiryokiNa( rest.getString("keiryoki_na") );
		bean.setKeiryoki2Na( rest.getString("keiryoki2_na") );
		bean.setKeiryoki3Na( rest.getString("keiryoki3_na") );
		bean.setBackLabelKb( rest.getString("back_label_kb") );
		bean.setSyomikikanVl( rest.getString("syomikikan_vl") );
		bean.setHozonOndotaiKb( rest.getString("hozon_ondotai_kb") );
		bean.setCommentKb( rest.getString("comment_kb") );
		bean.setBikoTx( rest.getString("biko_tx") );
		bean.setTeigakuVl( rest.getString("teigaku_vl") );
		bean.setTeigakujiTaniKb( rest.getString("teigakuji_tani_kb") );
		bean.setTankaVl( rest.getString("tanka_vl") );
		bean.setUpdateTs( rest.getString("update_ts") );


		bean.setHaneiDt( rest.getString("hanei_dt") );
		bean.setSyohinKbn( rest.getString("syohin_kbn") );
		bean.setReceiptHinmeiNa( rest.getString("receipt_hinmei_na") );
		bean.setTeigakuUpKb( rest.getString("teigaku_up_kb") );
		bean.setSyomikikanKb( rest.getString("syomikikan_kb") );
		bean.setSantiNb( rest.getString("santi_kb") );
		bean.setKakojikokuPrintKb( rest.getString("kakojikoku_print_kb") );
		bean.setChoriyoKokokubunKb( rest.getString("choriyo_kokokubun_kb") );
		bean.setStartKb( rest.getString("start_kb") );
		bean.setEiyoSeibunNa( rest.getString("eiyo_seibun_na") );
		bean.setGenzairyoNa( rest.getString("genzairyo_na") );
		bean.setTenkabutuNa( rest.getString("tenkabutu_na") );
		
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
		long cnt		=0;
		long start		= 0;
		long end		= 0;
		long startno 	= 0;
		long maxrows	= 9999;
		String blank = (String)map.get("h_blankkb");

		start = Long.parseLong((String)map.get("start_rows"));
		end   = Long.parseLong((String)map.get("end_rows"));

		if(end > maxrows + 1){
			end   = maxrows + 1 ;
		}
		
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			sb.append("	TO_CHAR(TBL1.CD,'FM0000') SYOHIN_YOBIDASI, ");
		} else {
			sb.append("	TBL2.SYOHIN_YOBIDASI, ");
		}
		sb.append("	TBL2.HINSYU_CD, ");					// 品種コード
		sb.append("	TBL2.SYOHIN_CD, ");					// 商品コード
		sb.append("	TBL2.KEIRYOKI_NA, ");				// 計量器名称	
		sb.append("	TBL2.KEIRYOKI2_NA, ");				// 計量器名称２
		sb.append("	TBL2.KEIRYOKI3_NA, ");				// 計量器名称３		
		sb.append("	TBL2.BACK_LABEL_KB, ");				// 裏面ラベル項目文区分
		sb.append("	TBL2.SYOMIKIKAN_VL, ");				// 賞味期間
		sb.append("	TBL2.HOZON_ONDOTAI_KB, ");			// 保存温度帯区分
		sb.append("	TBL2.COMMENT_KB, ");				// コメント区分
		sb.append("	TBL2.BIKO_TX, ");					// 備考
		sb.append("	TBL2.TEIGAKU_VL, ");				// 定額時内容量
		sb.append("	TBL2.TEIGAKUJI_TANI_KB, ");			// 定額時単位区分
		sb.append("	TBL2.TANKA_VL, ");					// 単価
		sb.append("	TBL2.UPDATE_TS, ");					// 更新年月日


		sb.append("	TBL2.HANEI_DT, ");					// 反映日
		sb.append("	TBL2.SYOHIN_KBN, ");				// 商品区分
		sb.append("	TBL2.RECEIPT_HINMEI_NA, ");			// レシート品名
		sb.append("	TBL2.TEIGAKU_UP_KB, ");				// 定額・UP区分
		sb.append("	TBL2.SYOMIKIKAN_KB, ");				// 賞味期間計算区分
		sb.append("	TBL2.SANTI_KB, ");					// 産地番号
		sb.append("	TBL2.KAKOJIKOKU_PRINT_KB, ");		// 加工時刻印字区分
		sb.append("	TBL2.CHORIYO_KOKOKUBUN_KB, ");		// 調理用広告文
		sb.append("	TBL2.START_KB, ");					// 開始日付区分
		sb.append("	TBL2.EIYO_SEIBUN_NA, ");			// 栄養成分表示
		sb.append("	TBL2.GENZAIRYO_NA, ");				// 原材料名称
		sb.append("	TBL2.TENKABUTU_NA ");				// 添加物名称

		
		sb.append("FROM ");

		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){

			sb.append("	( ");
			
			for(cnt=start; cnt < end ; cnt++ ){
				if(cnt!=start){
					sb.append("     UNION ALL ");
				}
				sb.append("		SELECT " + cnt + " AS CD FROM DUAL ");
			}
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
		sb.append("	TBL1.KEIRYOKI2_NA, ");				// 計量器名称２
		sb.append("	TBL1.KEIRYOKI3_NA, ");				// 計量器名称３		
		sb.append("	TBL1.BACK_LABEL_KB, ");
		sb.append("	TBL1.SYOMIKIKAN_VL, ");
		sb.append("	TBL1.HOZON_ONDOTAI_KB, ");
		sb.append("	TBL1.COMMENT_KB, ");
		sb.append("	TBL1.BIKO_TX, ");
		sb.append("	TBL1.TEIGAKU_VL, ");
		sb.append("	TBL1.TEIGAKUJI_TANI_KB, ");
		sb.append("	TBL1.TANKA_VL, ");
		sb.append("	TBL1.UPDATE_TS, ");

		sb.append("	TBL1.HANEI_DT, ");					// 反映日
		sb.append("	TBL1.SYOHIN_KBN, ");				// 商品区分
		sb.append("	TBL1.RECEIPT_HINMEI_NA, ");			// レシート品名
		sb.append("	TBL1.TEIGAKU_UP_KB, ");				// 定額・UP区分
		sb.append("	TBL1.SYOMIKIKAN_KB, ");				// 賞味期間計算区分
		sb.append("	TBL1.SANTI_KB, ");					// 産地番号
		sb.append("	TBL1.KAKOJIKOKU_PRINT_KB, ");		// 加工時刻印字区分
		sb.append("	TBL1.CHORIYO_KOKOKUBUN_KB, ");		// 調理用広告文
		sb.append("	TBL1.START_KB, ");					// 開始日付区分
		sb.append("	TBL1.EIYO_SEIBUN_NA, ");			// 栄養成分表示
		sb.append("	TBL1.GENZAIRYO_NA, ");				// 原材料名称
		sb.append("	TBL1.TENKABUTU_NA ");				// 添加物名称


		sb.append("FROM ");
		sb.append("	R_KEIRYOKI TBL1 ");
		sb.append("WHERE ");
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

		if(blank.equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
			sb.append("	WHERE ");
			sb.append(" TBL1.CD = TBL2.SYOHIN_YOBIDASI(+) ");
		}

		sb.append("ORDER BY ");
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
