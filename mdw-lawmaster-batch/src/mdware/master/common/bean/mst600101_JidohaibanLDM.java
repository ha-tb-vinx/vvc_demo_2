/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス
 *               自動廃番制御マスタ登録 mst600101_Jidohaiban 用
 *               自動廃番制御マスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する mst600101_Jidohaiban用計量器マスタのDMクラス(DmCreatorにより自動生成)</P>
 * <P>著作権: Copyright (c) 2005</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author C.Sawabe
 * @version 1.0	(2005/04/13)	新規作成
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
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス mst600101_Jidohaiban用自動廃番制御マスタのDMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst600101_Jidohaiban用自動廃番制御マスタのDMクラス(DmCreatorにより自動生成)</P>
 * <P>著作権: Copyright (c) 2005</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst600101_JidohaibanLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst600101_JidohaibanLDM()
	{
		super( mst000101_ConstDictionary.CONNECTION_STR);
	}
	/**
	 * 検索後にＢＥＡＮをインスタンス化する所。
	 * 検索した結果セットをＢＥＡＮとして持ち直す。
	 * DataModuleから呼び出され返したObjectをListに追加する。
	 * @param rest ResultSet
	 * @return Object インスタンス化されたＢＥＡＮ
	 */
	protected Object instanceBean( ResultSet rest ) throws SQLException {

		mst600401_KeepMeisaiBean bean = new mst600401_KeepMeisaiBean();
		String insertTsFormat = null;
		String updateTsFormat = null;

		bean.setKanriKb(rest.getString("kanri_kb"));				//管理区分
		bean.setKanriCdFrom(rest.getString("kanri_cd"));			//管理コード
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		bean.setHankuCd(rest.getString("hanku_cd"));				//販区コード
//		bean.setHankuNa(rest.getString("hanku_na"));				//販区名称
//		bean.setSGyousyuCd(rest.getString("syo_gyousyu_cd"));		//小業種コード
//		bean.setSGyousyuNa(rest.getString("syo_gyousyu_na"));		//小業種名称
		bean.setBumonCd(rest.getString("bumon_cd"));				    //部門コード
		bean.setBumonNa(rest.getString("bumon_na"));				   //部門名称
		bean.setHinbanCd(rest.getString("hinban_cd"));		       //品番コード
		bean.setHinbanNa(rest.getString("hinban_na"));		       //品番名称
		bean.setCheckSycle(rest.getString("check_sycle_fg"));		//チェックサイクル
		bean.setOutWeekdayQt(rest.getString("out_weekday_qt"));		//出力日(週曜日)
		bean.setOutMonthdayQt(rest.getInt("out_monthday_qt"));		//出力日(日)
		bean.setOutDayQt(rest.getInt("out_day_qt"));				//廃番予定出力(N日前)
		bean.setDeldaysRemain(rest.getString("deldays_remain_qt"));	//削除予定日
//		bean.setHachu_teisiyotei(rest.getString("hachu_teisiyotei_qt"));  //発注停止予定日
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//作成年月日のデータがある場合
		if (rest.getString("insert_ts") != null && rest.getString("insert_ts").trim().length() > 0) {
			insertTsFormat = rest.getString("insert_ts").trim();
			insertTsFormat = insertTsFormat.substring(0, 4) + "/"
			               + insertTsFormat.substring(4, 6) + "/"
			               + insertTsFormat.substring(6, 8);

			bean.setInsertTs(insertTsFormat);						//作成年月日(画面表示用)
			bean.setInsertTsDB(rest.getString("insert_ts"));		//作成年月日(DBチェック用)
			bean.setInsertUserId(rest.getString("insert_user_id"));	//作成者社員ID
			bean.setInsertUserNa(rest.getString("insert_user_na"));	//作成者名
		}

		//更新年月日のデータがある場合
		if (rest.getString("update_ts") != null && rest.getString("update_ts").trim().length() > 0) {
			updateTsFormat = rest.getString("update_ts").trim();
			updateTsFormat = updateTsFormat.substring(0, 4) + "/"
						   + updateTsFormat.substring(4, 6) + "/"
						   + updateTsFormat.substring(6, 8);

			bean.setUpdateTs(updateTsFormat);							//更新年月日(画面表示用)
			bean.setUpdateTsDB(rest.getString("update_ts"));			//更新年月日(DBチェック用)
			bean.setUpdateUserId(rest.getString("update_user_id"));		//更新者社員ID
			bean.setUpdateUserNa(rest.getString("update_user_na"));		//更新者名
		}

		bean.setDeleteFg(rest.getString("delete_fg"));				//削除フラグ

		return bean;
	}


	/**
	 * 検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( Map map ) {

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		mst600201_KeepBean bean = new mst600201_KeepBean();

		//▼select句
		sb.append(" SELECT ");
		//管理区分
		sb.append("		 KANRI_KB, ");
		//管理コード
		sb.append("		 KANRI_CD, ");
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		//販区コード
//		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN ");
//		sb.append("		KANRI_CD ");
//		sb.append("	ELSE ");
//    	sb.append("		(SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD) ");
//		sb.append("	END AS HANKU_CD, ");
//		//販区名称
//		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.HANKU.getCode() + "' THEN ");
//		sb.append("		(SELECT ");
//		sb.append("			KANJI_NA ");
//		sb.append("		FROM ");
//		sb.append("			R_NAMECTF ");
//		sb.append("		WHERE ");
//		sb.append("			TRIM(CODE_CD) = TRIM(KANRI_CD) ");
//		sb.append("		AND SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "') ");
//		sb.append("	ELSE ");
//		sb.append("		(SELECT ");
//		sb.append("			KANJI_NA ");
//		sb.append("		FROM ");
//		sb.append("			R_NAMECTF ");
//		sb.append("		WHERE ");
//		sb.append("			TRIM(CODE_CD) = TRIM((SELECT HANKU_CD FROM R_SYOHIN_TAIKEI WHERE HINSYU_CD = KANRI_CD)) ");
//		sb.append("		AND SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "') ");
//		sb.append("	END AS HANKU_NA, ");
//		//小業種コード
//		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN ");
//		sb.append("	     KANRI_CD ");
//		sb.append("	ELSE NULL ");
//		sb.append("	END AS SYO_GYOUSYU_CD, ");
//		//小業種名称
//		sb.append("	CASE KANRI_KB WHEN '" + mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode() + "' THEN ");
//		sb.append("	(SELECT ");
//		sb.append("		KANJI_RN ");
//		sb.append("	FROM ");
//		sb.append("		R_NAMECTF ");
//		sb.append("	WHERE ");
//		sb.append("		TRIM(CODE_CD) = TRIM(KANRI_CD) ");
//		sb.append("	AND SYUBETU_NO_CD = '" + mst000101_ConstDictionary.SMALL_TYPE_OF_BUSINESS + "') ELSE NULL END AS SYO_GYOUSYU_NA, ");
       // 品番コード
		sb.append("	case kanri_kb when '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' then ");
		sb.append("		kanri_cd ");
		sb.append("	else cast(null as char) ");
		sb.append("	end as hinban_cd, ");
		//品番名称
		sb.append("	case kanri_kb when '" + mst000901_KanriKbDictionary.HINBAN.getCode() + "' then ");
		sb.append("		(select ");
		sb.append("			kanji_na ");
		sb.append("		from ");
		sb.append("			r_namectf ");
		sb.append("		where ");
		sb.append("			trim(code_cd) = trim(kanri_cd) ");
		sb.append("		and syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "') ");
		sb.append("	else cast(null as char)");
		sb.append("	end as hinban_na, ");
		 // 部門コード
		sb.append("	case kanri_kb when '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' then ");
		sb.append("		kanri_cd ");
		sb.append("	else cast(null as char) ");
		sb.append("	end as bumon_cd, ");
		//部門名称
		sb.append("	case kanri_kb when '" + mst000901_KanriKbDictionary.BUMON.getCode() + "' then ");
		sb.append("		(select ");
		sb.append("			kanji_na ");
		sb.append("		from ");
		sb.append("			r_namectf ");
		sb.append("		where ");
		sb.append("			trim(code_cd) = trim(kanri_cd) ");
		sb.append("		and syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "') ");
		sb.append("	else cast(null as char) ");
		sb.append("	end as bumon_na, ");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//チェックサイクル
		sb.append("		CHECK_SYCLE_FG ");
		//出力日(日)
		sb.append("		,OUT_MONTHDAY_QT ");
		//出力日(週曜日)
		sb.append("		,OUT_WEEKDAY_QT ");
		//廃番予定出力(N日前)
		sb.append("		,OUT_DAY_QT ");
		//削除余裕日
		sb.append("		,DELDAYS_REMAIN_QT ");
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
		//発注停止予定日
//		sb.append("		,HACHU_TEISIYOTEI_QT ");
		//作成年月日
		sb.append("		,INSERT_TS ");
		//作成者社員ID
		sb.append("		,INSERT_USER_ID ");
//		//作成者名
//		sb.append("		,(SELECT ");
//		sb.append("			sya1.USER_NA ");
//		sb.append("		FROM ");
//		sb.append("			SYS_SOSASYA sya1");
//		sb.append("		WHERE ");
//		sb.append("			sya1.USER_ID = INSERT_USER_ID )");
//		sb.append("		AS INSERT_USER_NA ");

//		登録者
//		sb.append("		,(SELECT ");
//		sb.append("			rru1.RIYO_USER_NA ");
//		sb.append("		FROM ");
//		sb.append("			R_RIYO_USER rru1");
//		sb.append("		WHERE ");
//		sb.append("			rru1.RIYO_USER_ID =R_JIDOHAIBAN.INSERT_USER_ID )");
//		sb.append("		AS INSERT_USER_NA ");
		sb.append("		,(SELECT ");
		sb.append("			rru1.USER_NA ");
		sb.append("		FROM ");
		sb.append("			R_PORTAL_USER rru1");
		sb.append("		WHERE ");
		sb.append("			rru1.USER_ID = TRIM(R_JIDOHAIBAN.INSERT_USER_ID) )");
		sb.append("		AS INSERT_USER_NA ");

		//更新年月日
		sb.append("		,UPDATE_TS ");
		//更新者社員ID
		sb.append("		,UPDATE_USER_ID ");
//		//更新者名
//		sb.append("		,(SELECT ");
//		sb.append("			sya2.USER_NA ");
//		sb.append("		FROM ");
//		sb.append("			SYS_SOSASYA sya2");
//		sb.append("		WHERE ");
////		sb.append("			sya2.USER_ID = INSERT_USER_ID )");
//		sb.append("			sya2.USER_ID = UPDATE_USER_ID )");
//		sb.append("		AS UPDATE_USER_NA ");
//		更新者
//		sb.append("		,(SELECT ");
//		sb.append("			rru2.RIYO_USER_NA ");
//		sb.append("		FROM ");
//		sb.append("			R_RIYO_USER rru2");
//		sb.append("		WHERE ");
//		sb.append("			rru2.RIYO_USER_ID = R_JIDOHAIBAN.UPDATE_USER_ID )");
//		sb.append("		AS UPDATE_USER_NA ");
		sb.append("		,(SELECT ");
		sb.append("			rru2.USER_NA ");
		sb.append("		FROM ");
		sb.append("			R_PORTAL_USER rru2");
		sb.append("		WHERE ");
		sb.append("			rru2.USER_ID = TRIM(R_JIDOHAIBAN.UPDATE_USER_ID) )");
		sb.append("		AS UPDATE_USER_NA ");
		//削除フラグ
		sb.append("		,DELETE_FG ");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑

		//▼from句
		sb.append(" FROM ");
		sb.append("   R_JIDOHAIBAN ");

		//▼where句
		sb.append(whereStr);

		//管理区分 : kanri_kb
		sb.append("     KANRI_KB = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");

		//管理コード : kanri_cd
		//管理コード_from が選択された場合
		if( map.get("kanri_cdf") != null && ((String)map.get("kanri_cdf")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" KANRI_CD >= '" + conv.convertWhereString(StringUtility.charFormat( (String)map.get("kanri_cdf") ,4,"0",true)) + "'");
		}
		//管理コード_to が選択された場合
		if( map.get("kanri_cdt") != null && ((String)map.get("kanri_cdt")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" KANRI_CD <= '" +conv.convertWhereString(StringUtility.charFormat( (String)map.get("kanri_cdt") ,4,"0",true)) + "'");
		}
		//店舗コード
//		sb.append(andStr);
//		sb.append(" TENPO_CD = '000000' ");

		//チェックサイクル : check_sycle
		if( map.get("check_sycle") != null && ((String)map.get("check_sycle")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" CHECK_SYCLE_FG = '" + conv.convertWhereString((String) map.get("check_sycle")) + "'");

			if (map.get("check_sycle").equals("1")) {
				//出力日(日) : o_monthday_qt
				sb.append(andStr);
				sb.append(" OUT_MONTHDAY_QT = '" + conv.convertWhereString((String) map.get("o_monthday_qt")) + "'");
			} else if (map.get("check_sycle").equals("2")) {
				//出力日(週曜日) : o_weekday_qt
				sb.append(andStr);
				sb.append(" OUT_WEEKDAY_QT = '" + conv.convertWhereString((String) map.get("o_weekday_qt")) + "'");
			} else if (map.get("check_sycle").equals("3")) {
				//廃番予定出力(N日前) : o_day_qt
				sb.append(andStr);
				sb.append(" OUT_DAY_QT = '" + conv.convertWhereString((String) map.get("o_day_qt")) + "'");
			}
		}

		//削除余裕日 : tx_delday
		if( map.get("tx_delday") != null && ((String)map.get("tx_delday")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" DELDAYS_REMAIN_QT = '" + conv.convertWhereString( (String)map.get("tx_delday") ) + "'");
		}
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
		//発注停止予定日 :
//		if( map.get("tx_hachuteisiday") != null && ((String)map.get("tx_hachuteisiday")).trim().length() > 0 ) {
//			sb.append(andStr);
//			sb.append(" HACHU_TEISIYOTEI_QT = '" + conv.convertWhereString( (String)map.get("tx_hachuteisiday") ) + "'");
//		}
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//作成年月日 : insert_ts
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" INSERT_TS = '" + conv.convertWhereString( (String) map.get("insert_ts") ) + "'");
		}

		//作成者社員ID : insert_user_id
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" INSERT_USER_ID = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
		}

		//更新年月日 : update_ts
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" UPDATE_TS = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
		}

		//更新者社員ID : update_user_id
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" UPDATE_USER_ID = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
		}

		//削除フラグ : delete_fg
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 ) {
			sb.append(andStr);
			sb.append(" DELETE_FG = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		}

		//▼order by句
		sb.append(" ORDER BY ");
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		sb.append("	   KANRI_KB, ");
//		sb.append("	   KANRI_CD, ");
		sb.append("	   KANRI_CD  ");
//		sb.append("    TENPO_CD");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ
	 *
	 * @param List where句
	 * @return String 生成されたＳＱＬ sb.toString()
	 */
	public String getSelectUpdateSql(StringBuffer sbWhere) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append("	   KANRI_KB, ");
		sb.append("	   KANRI_CD, ");
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		sb.append("    TENPO_CD, ");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		sb.append("    TRIM(INSERT_TS) INSERT_TS, ");
		sb.append("    TRIM(UPDATE_TS) UPDATE_TS, ");
		sb.append("    TRIM(DELETE_FG) DELETE_FG ");
		sb.append(" FROM ");
		sb.append("	   R_JIDOHAIBAN" );
		sb.append(" WHERE ");
		sb.append("    " + sbWhere.toString() + " ");

		return sb.toString();
	}


	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ
	 *
	 * @param mst600401_KeepMeisaiBean bean
	 * @param mst600201_KeepBean keepb
	 * @return String 生成されたＳＱＬ sb.toString()
	 */
	public String getInsertSql(mst600201_KeepBean keepb ,mst600401_KeepMeisaiBean meisai) {

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst600201_KeepBean bean = (mst600201_KeepBean) keepb;
		StringBuffer sb = new StringBuffer();

		//▼登録項目設定
		sb.append(" INSERT INTO ");
		sb.append("      R_JIDOHAIBAN ( ");
		//管理区分
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() > 0 ) {
			sb.append(" KANRI_KB ");
		}
		//■管理コード
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		if( bean.getKanriKb().equals(mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode())) {
//			//小業種コード
//			if( meisai.getSGyousyuCd() != null && meisai.getSGyousyuCd().trim().length() > 0 ) {
//				sb.append(" ,KANRI_CD ");
//			}
//		} else {
//			//販区コード
//			if( meisai.getHankuCd() != null && meisai.getHankuCd().trim().length() > 0 ) {
//				sb.append(" ,KANRI_CD ");
//			}
//		}
			//品番コード
			if( meisai.getHinbanCd() != null && meisai.getHinbanCd().trim().length() > 0 ) {
				sb.append(" ,KANRI_CD ");
           //	部門コード
			} else if( meisai.getBumonCd() != null && meisai.getBumonCd().trim().length() > 0 ) {
				sb.append(" ,KANRI_CD ");
			}

		//店舗コード
//		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 ) {
//			sb.append(" ,TENPO_CD ");
//		}
//			 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//チェックサイクル : check_sycle
		if( meisai.getCheckSycle() != null && meisai.getCheckSycle().trim().length() > 0 ) {
			sb.append(" ,CHECK_SYCLE_FG ");
			if (meisai.getCheckSycle().equals("1")) {
				//出力日(週曜日) : o_weekday_qt
				sb.append(" ,OUT_WEEKDAY_QT ");
			} else if (meisai.getCheckSycle().equals("2")) {
				//出力日(日) : o_monthday_qt
				sb.append(" ,OUT_MONTHDAY_QT ");
			} else if (meisai.getCheckSycle().equals("3")) {
				//廃番予定出力(N日前) : o_day_qt
				sb.append(" ,OUT_DAY_QT ");
			}
		}

		//削除余裕日 : deldays_remain
		if( meisai.getDeldaysRemain() != null && meisai.getDeldaysRemain().trim().length() > 0 ) {
			sb.append(" ,DELDAYS_REMAIN_QT ");
		}
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
		//発注停止予定日 : deldays_remain
//		if( meisai.getDeldaysRemain() != null && meisai.getDeldaysRemain().trim().length() > 0 ) {
//			sb.append(" ,HACHU_TEISIYOTEI_QT ");
//		}
		sb.append(" ,INSERT_TS");
		sb.append(" ,INSERT_USER_ID");
		sb.append(" ,UPDATE_TS");
		sb.append(" ,UPDATE_USER_ID");
		sb.append(" ,DELETE_FG");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		sb.append(" )Values( ");

		//管理区分
		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() > 0 ) {
			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
		}
		//管理コード
		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() > 0 ) {
			sb.append(",'" + conv.convertString( bean.getKanriCd() ) + "'");
		}
		//■管理コード
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		//小業種コード
//		if( meisai.getSGyousyuCd() != null && meisai.getSGyousyuCd().trim().length() > 0 ) {
//			sb.append(",'" + conv.convertString( meisai.getSGyousyuCd() ) + "'");
//		}
//		//販区コード
//		if( meisai.getHankuCd() != null && meisai.getHankuCd().trim().length() > 0 ) {
//			sb.append(",'" + conv.convertString( meisai.getHankuCd() ) + "'");
//		}
//		品番コード
		if( meisai.getHinbanCd() != null && meisai.getHinbanCd().trim().length() > 0 ) {
			sb.append(",'" + conv.convertString( StringUtility.charFormat(meisai.getHinbanCd()  ,4,"0",true) ) + "'");
		}
		//部門コード
		if( meisai.getBumonCd() != null && meisai.getBumonCd().trim().length() > 0 ) {
			sb.append(",'" + conv.convertString( StringUtility.charFormat(meisai.getBumonCd()  ,4,"0",true) ) + "'");
		}
		//店舗コード *** 全店登録 ***
//		sb.append(",'000000'");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//チェックサイクル : check_sycle
		if( meisai.getCheckSycle() != null && meisai.getCheckSycle().trim().length() > 0 ) {
			if (meisai.getCheckSycle().equals("1")) {
				sb.append(", '"+ conv.convertString( meisai.getCheckSycle() ) + "'");
				//出力日(週曜日) : o_weekday_qt
				sb.append(",'" + conv.convertString( meisai.getOutWeekdayQt() )+ "'" );
			} else if (meisai.getCheckSycle().equals("2")) {
				sb.append(",'" + conv.convertString( meisai.getCheckSycle() )+ "'");
				//出力日(日) : o_monthday_qt
				sb.append("," + conv.convertString( meisai.getOutMonthdayQtString() ));
			} else if (meisai.getCheckSycle().equals("3")) {
				sb.append(",'" + conv.convertString( meisai.getCheckSycle() ) + "'");
				//廃番予定出力(N日前) : o_day_qt
				sb.append("," + conv.convertString( meisai.getOutDayQtString() ) );
			}
		}
		//削除余裕日 : deldays_remain
		if( meisai.getDeldaysRemain() != null && meisai.getDeldaysRemain().trim().length() > 0 ) {
			sb.append(","+conv.convertString( meisai.getDeldaysRemain() ) );
		}
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		発注停止予定日
//		if( meisai.getHachu_teisiyotei() != null && meisai.getHachu_teisiyotei() .trim().length() > 0 ) {
//			sb.append(",'" + conv.convertString( meisai.getHachu_teisiyotei() ) + "'");
//		}
//      ↓↓移植（2006.05.22）↓↓
		sb.append(", '" + meisai.getInsertTs() + "' ");
//      ↑↑移植（2006.05.22）↑↑
		sb.append(",'" + conv.convertString( meisai.getInsertUserId() ) + "'");
		sb.append(", '" + meisai.getUpdateTs() + "' ");
		sb.append(",'" +  conv.convertString( meisai.getUpdateUserId() ) + "'");
		sb.append(",'0'");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		sb.append(")");

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql(mst600201_KeepBean keepb, mst600401_KeepMeisaiBean meisai)
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//▼更新項目設定
		sb.append(" UPDATE ");
		sb.append("     R_JIDOHAIBAN SET ");

//		//管理区分
//		if( keepb.getKanriKb() != null && keepb.getKanriKb().trim().length() > 0 ) {
//			sb.append(" KANRI_KB = ");
//			sb.append("'" + conv.convertString(keepb.getKanriKb()) + "'");
//		}
		//■管理コード
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		if( keepb.getKanriKb().equals(mst000901_KanriKbDictionary.SYO_GYOUSYU.getCode())) {
//			//小業種コード
//			if( meisai.getSGyousyuCd() != null && meisai.getSGyousyuCd().trim().length() > 0 ) {
//				sb.append(" ,KANRI_CD = ");
//				sb.append("'" + conv.convertString(meisai.getSGyousyuCd()) + "'");
//			}
//		} else {
//			//販区コード
//			if( meisai.getHankuCd() != null && meisai.getHankuCd().trim().length() > 0 ) {
//				sb.append(" ,KANRI_CD = ");
//				sb.append("'" + conv.convertString(meisai.getHankuCd()) + "'");
//			}
//		}
		//店舗コード
//		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 ) {
//			sb.append(" ,TENPO_CD = '000000'");
//		}
//			 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		//チェックサイクル : check_sycle
		if( meisai.getCheckSycle() != null && meisai.getCheckSycle().trim().length() > 0 ) {
			sb.append(" CHECK_SYCLE_FG = " );
			sb.append("'" + conv.convertString( meisai.getCheckSycle() ) + "'");
//	        ↓↓移植（2006.05.31）↓↓
			if (meisai.getCheckSycle().equals("1")) {
				//出力日(週曜日) : o_weekday_qt
				sb.append(" ,OUT_WEEKDAY_QT = ");
				sb.append("'" + conv.convertString( meisai.getOutWeekdayQt() ) + "'");
				sb.append(" ,OUT_MONTHDAY_QT = null");
				sb.append(" ,OUT_DAY_QT = null");

			} else if (meisai.getCheckSycle().equals("2")) {
				//出力日(日) : o_monthday_qt
				sb.append(" ,OUT_MONTHDAY_QT = ");
				sb.append(meisai.getOutMonthdayQt() );
				sb.append(" ,OUT_WEEKDAY_QT = ''");
				sb.append(" ,OUT_DAY_QT =null");

			} else if (meisai.getCheckSycle().equals("3")) {
				//廃番予定出力(N日前) : o_day_qt
				sb.append(" ,OUT_DAY_QT = ");
				sb.append(meisai.getOutDayQt());
				sb.append(" ,OUT_WEEKDAY_QT = ''");
				sb.append(" ,OUT_MONTHDAY_QT = null");
			}
		}
		//削除余裕日 : deldays_remain
		if( meisai.getDeldaysRemain() != null && meisai.getDeldaysRemain().trim().length() > 0 ) {
			sb.append(" ,DELDAYS_REMAIN_QT = ");
			sb.append(conv.convertString( meisai.getDeldaysRemain() ) );
		}
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
		//発注停止予定日
//		if( meisai.getHachu_teisiyotei() != null && meisai.getHachu_teisiyotei() .trim().length() > 0 ) {
//			sb.append(" ,HACHU_TEISIYOTEI_QT = ");
//			sb.append("" +  conv.convertString( meisai.getHachu_teisiyotei() ) + " ");
//		}
//        ↑↑移植（2006.05.31）↑↑
//		if( meisai.getInsertTsDB() != null && meisai.getInsertTsDB().trim().length() > 0 ) {
//			sb.append(" ,INSERT_TS = ");
//			sb.append("'" + conv.convertString( meisai.getInsertTsDB() ) + "'");
//		}
//		if( meisai.getInsertUserId() != null && meisai.getInsertUserId().trim().length() > 0 ) {
//			sb.append(" ,INSERT_USER_ID = ");
//			sb.append("'" + conv.convertString( meisai.getInsertUserId() ) + "'");
//		}
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
//		if( meisai.getUpdateTs() != null && meisai.getUpdateTs().trim().length() > 0 ) {
			sb.append(" ,UPDATE_TS = ");
//	        ↓↓移植（2006.05.22）↓↓
			try {
				sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
			}catch (SQLException e){
				e.printStackTrace();
			}
//	        ↑↑移植（2006.05.22）↑↑
//		}
//		if( meisai.getUpdateUserId() != null && meisai.getUpdateUserId().trim().length() > 0 ) {
			sb.append(" ,UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString( meisai.getUpdateUserId() ) + "'");
//		}
		if( meisai.getDeleteFg() != null && meisai.getDeleteFg().trim().length() > 0 ) {
			sb.append(" ,DELETE_FG = ");
			sb.append("'" + conv.convertString( meisai.getDeleteFg() ) + "'");
		}

		//▼where句
		sb.append(" WHERE");
		if( keepb.getKanriKb() != null && keepb.getKanriKb().trim().length() > 0 ) {
			sb.append(" KANRI_KB = ");
			sb.append("'" + conv.convertWhereString( meisai.getKanriKb() ) + "'");
		}
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
//		if( meisai.getSGyousyuCd() != null && meisai.getSGyousyuCd().trim().length() > 0 ) {
//			sb.append(" AND ");
//			sb.append(" KANRI_CD = ");
//			sb.append("'" + conv.convertWhereString( meisai.getSGyousyuCd() ) + "'");
//		}
//		if( meisai.getHankuCd() != null && meisai.getHankuCd().trim().length() > 0 ) {
//			sb.append(" AND ");
//			sb.append(" KANRI_CD = ");
//			sb.append("'" + conv.convertWhereString( meisai.getHankuCd() ) + "'");
//		}
		if( meisai.getHinbanCd() != null && meisai.getHinbanCd().trim().length() > 0 ) {
			sb.append(" AND ");
			sb.append(" KANRI_CD = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(meisai.getHinbanCd()  ,4,"0",true) ) + "'");
		}
		if( meisai.getBumonCd() != null && meisai.getBumonCd().trim().length() > 0 ) {
			sb.append(" AND ");
			sb.append(" KANRI_CD = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(meisai.getBumonCd()  ,4,"0",true) ) + "'");
		}
//		sb.append(" AND ");
//		sb.append(" TENPO_CD = '000000'");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getUpdateSql2(mst600201_KeepBean keepb, mst600401_KeepMeisaiBean meisai)
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		//▼更新項目設定
		sb.append(" UPDATE ");
		sb.append("     R_JIDOHAIBAN SET ");
		sb.append(" UPDATE_TS = ");
		try {
			sb.append(" '" + AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "' ");
		}catch (SQLException e){
			e.printStackTrace();
		}
		sb.append(" ,UPDATE_USER_ID = ");
		sb.append("'" + conv.convertString( meisai.getUpdateUserId() ) + "'");
		if( meisai.getDeleteFg() != null && meisai.getDeleteFg().trim().length() > 0 ) {
			sb.append(" ,DELETE_FG = ");
			sb.append("'" + conv.convertString( meisai.getDeleteFg() ) + "'");
		}
		//▼where句
		sb.append(" WHERE");
		if( keepb.getKanriKb() != null && keepb.getKanriKb().trim().length() > 0 ) {
			sb.append(" KANRI_KB = ");
			sb.append("'" + conv.convertWhereString( meisai.getKanriKb() ) + "'");
		}
		if( meisai.getHinbanCd() != null && meisai.getHinbanCd().trim().length() > 0 ) {
			sb.append(" AND ");
			sb.append(" KANRI_CD = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(meisai.getHinbanCd()  ,4,"0",true) ) + "'");
		}
		if( meisai.getBumonCd() != null && meisai.getBumonCd().trim().length() > 0 ) {
			sb.append(" AND ");
			sb.append(" KANRI_CD = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(meisai.getBumonCd()  ,4,"0",true) ) + "'");
		}
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getDeleteSql(mst600201_KeepBean keepb, mst600401_KeepMeisaiBean meisai)
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst600201_KeepBean bean = (mst600201_KeepBean) keepb;
		StringBuffer sb = new StringBuffer();
//		 ↓↓2006.06.23 wangluping カスタマイズ修正↓↓
		sb.append(" DELETE FROM ");
		sb.append("     R_JIDOHAIBAN ");
		sb.append(" WHERE ");
		sb.append("     KANRI_KB = '" + conv.convertWhereString( bean.getKanriKb() ) + "'");
		//小業種コードが存在する場合
//		if(meisai.getSGyousyuCd() != null && meisai.getSGyousyuCd().trim().length() > 0) {
//			sb.append(" AND ");
//			sb.append(" KANRI_CD = ");
//			sb.append("'" + conv.convertWhereString( meisai.getSGyousyuCd() ) + "'");
//		//販区コードが存在する場合
//		} else if( meisai.getHankuCd() != null && meisai.getHankuCd().trim().length() > 0 ) {
//				sb.append(" AND ");
//				sb.append(" KANRI_CD = ");
//				sb.append("'" + conv.convertWhereString( meisai.getHankuCd() ) + "'");
//		}
//		品番コードが存在する場合
		if(meisai.getHinbanCd() != null && meisai.getHinbanCd().trim().length() > 0) {
			sb.append(" and ");
			sb.append(" kanri_cd = ");
			sb.append("'" + conv.convertWhereString( StringUtility.charFormat(meisai.getHinbanCd()  ,4,"0",true)) + "'");
		//部門コードが存在する場合
		} else if( meisai.getBumonCd() != null && meisai.getBumonCd().trim().length() > 0 ) {
				sb.append(" and ");
				sb.append(" kanri_cd = ");
				sb.append("'" + conv.convertWhereString(  StringUtility.charFormat(meisai.getBumonCd()  ,4,"0",true) ) + "'");
		}
		sb.append(" AND");
		sb.append(" delete_fg = ");
		sb.append("'" + mst000801_DelFlagDictionary.IRU.getCode() + "'");
//		sb.append(" AND");
//		sb.append("     TENPO_CD = '000000'");
//		 ↑↑2006.06.23 wangluping カスタマイズ修正↑↑
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
	 * @see jp.co.vinculumjapan.stc.util.db.DataModule#getInsertSql(java.lang.Object)
	 */
	public String getInsertSql(Object bean) {

		return null;
	}
	/**
	 * @see jp.co.vinculumjapan.stc.util.db.DataModule#getUpdateSql(java.lang.Object)
	 */
	public String getUpdateSql(Object bean) {
		return null;
	}
	/**
	 * @see jp.co.vinculumjapan.stc.util.db.DataModule#getDeleteSql(java.lang.Object)
	 */
	public String getDeleteSql(Object bean) {
		return null;
	}

}
