/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）品種一括送信の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する品種一括送信画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/05/10)初版作成
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
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst006101_SyoriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）品種一括送信の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する品種一括送信画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst760301_HinsyuSendListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst760301_HinsyuSendListDM()
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
		mst760301_HinsyuSendListBean bean = new mst760301_HinsyuSendListBean();
		bean.setSyoriSts(rest.getString("syori_sts"));
		bean.setTenpoCd(rest.getString("tenpo_cd"));
		bean.setTenpoKanjiRn(rest.getString("tenpo_kanji_rn"));
		bean.setHinsyuCd(rest.getString("hinsyu_cd"));
		bean.setHinsyuKanjiRn(rest.getString("hinsyu_kanji_rn"));
		bean.setMstTanpinCnt(rest.getLong("mst_tanpin_cnt"));
		bean.setSendDt(rest.getString("send_dt"));
		bean.setInsertTs( rest.getString("insert_ts"));
		bean.setInsertUserId( rest.getString("insert_user_id"));
		bean.setUpdateTs( rest.getString("update_ts"));
		bean.setUpdateUserId( rest.getString("update_user_id"));
		bean.setDeleteFg( rest.getString("delete_fg"));
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
		String rb_syori_kb = (String)map.get("rb_syori_kb");
		//「登録データ作成」「削除データ作成」時は新規検索
		if (rb_syori_kb.equals(mst006101_SyoriKbDictionary.TOUROKU.getCode()) ||
			rb_syori_kb.equals(mst006101_SyoriKbDictionary.SAKUZYO.getCode())) {
			return getSelectSqlForNewSearch(map);
		}
		//「作成データ照会」「作成データ取消」時は既存データ検索
		else {
			return getSelectSqlForExistSearch(map);
		}
	}

	/**
	 * 検索用ＳＱＬ（新規検索）の生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlForNewSearch( Map map )
	{

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		// 「全店」モード
		boolean isZenten = false;
		if (map.get("tx_tenpo_cd").equals("000000")) {
			isZenten = true;
		}

		sb.append("SELECT \n");
		sb.append("NULL SYORI_STS, \n");
		if (isZenten) {
			sb.append("'000000' TENPO_CD, \n");
			sb.append("'全店' TENPO_KANJI_RN, \n");
		} else {
			sb.append("R_TENPO.TENPO_CD TENPO_CD, \n");
			sb.append("R_TENPO.KANJI_RN TENPO_KANJI_RN, \n");
		}
		sb.append("R_SYOHIN_TAIKEI.HINSYU_CD, \n");
		sb.append("R_NAMECTF.KANJI_RN HINSYU_KANJI_RN, \n");
//		if (isZenten) {
			sb.append("'0' MST_TANPIN_CNT, \n");
//		} else {
//			sb.append("MSP760101_GETMSTTANPINCNT(R_TENPO.TENPO_CD,  \n");
//			sb.append("                          R_SYOHIN_TAIKEI.HANKU_CD,  \n");
//			sb.append("                          R_SYOHIN_TAIKEI.HINSYU_CD) MST_TANPIN_CNT, \n");
//		}
		sb.append("'" + map.get("tx_send_dt") + "' SEND_DT, \n");
		sb.append("NULL as INSERT_TS, NULL AS INSERT_USER_ID, NULL AS UPDATE_TS, NULL AS UPDATE_USER_ID, '0' as DELETE_FG  \n");

		sb.append("FROM  \n");
		if (!isZenten) {
			sb.append("R_TENPO,  \n");
		}
		sb.append("R_SYOHIN_TAIKEI, \n");
		sb.append("R_NAMECTF \n");

		sb.append("WHERE  \n");
		if (!isZenten) {
			sb.append("R_TENPO.TENPO_CD = '" + map.get("tx_tenpo_cd") + "'  \n");
			sb.append("AND  \n");
		}

		//販区指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HANKU.getCode())) {
			sb.append("R_SYOHIN_TAIKEI.HANKU_CD = '" + map.get("tx_kanri_cd") + "' \n");
		}
		//品種指定の場合
		if (map.get("rb_kanri_kb").equals(mst000901_KanriKbDictionary.HINSYU.getCode())) {
			sb.append("R_SYOHIN_TAIKEI.HINSYU_CD = '" + map.get("tx_kanri_cd") + "' \n");
		}

		sb.append("AND R_SYOHIN_TAIKEI.HINSYU_CD = R_NAMECTF.CODE_CD  \n");
		sb.append("AND R_NAMECTF.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'  \n");

		if (!isZenten) {
			sb.append("AND R_TENPO.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		}
		sb.append("AND R_NAMECTF.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		sb.append("ORDER BY TENPO_CD, R_SYOHIN_TAIKEI.HINSYU_CD \n");

		return sb.toString();
	}

	/**
	 * 検索用ＳＱＬ（既存データ）の生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSqlForExistSearch( Map map )
	{

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		// 「全店」モード
		boolean isZenten = false;
		if (map.get("tx_tenpo_cd").equals("000000")) {
			isZenten = true;
		}
		sb.append("SELECT  \n");
		sb.append("R_HINSYU_SEND.ENTRY_KB SYORI_STS,  \n");
		if (isZenten) {
			sb.append("'000000' TENPO_CD,  \n");
			sb.append("'全店' TENPO_KANJI_RN,  \n");
		} else {
			sb.append("R_HINSYU_SEND.TENPO_CD TENPO_CD,  \n");
			sb.append("R_TENPO.KANJI_RN TENPO_KANJI_RN,  \n");
		}
		sb.append("R_HINSYU_SEND.HINSYU_CD, \n");
		sb.append("R_NAMECTF.KANJI_RN HINSYU_KANJI_RN,  \n");
//		if (isZenten) {
			sb.append("'0' MST_TANPIN_CNT, \n");
//		} else {
//			sb.append("MSP760101_GETMSTTANPINCNT(R_HINSYU_SEND.TENPO_CD,  \n");
//			sb.append("                          R_SYOHIN_TAIKEI.HANKU_CD,  \n");
//			sb.append("                          R_HINSYU_SEND.HINSYU_CD) MST_TANPIN_CNT, \n");
//		}
		sb.append("R_HINSYU_SEND.SEND_DT,  \n");
		sb.append("R_HINSYU_SEND.INSERT_TS,  \n");
		sb.append("R_HINSYU_SEND.INSERT_USER_ID,  \n");
		sb.append("R_HINSYU_SEND.UPDATE_TS,  \n");
		sb.append("R_HINSYU_SEND.UPDATE_USER_ID,  \n");
		sb.append("R_HINSYU_SEND.DELETE_FG  \n");

		sb.append("FROM  \n");
		sb.append("R_HINSYU_SEND, \n");
		if (!isZenten) {
			sb.append("R_TENPO,  \n");
			sb.append("R_SYOHIN_TAIKEI, \n");
		}
		sb.append("R_NAMECTF \n");

		sb.append("WHERE R_HINSYU_SEND.KANRI_KB = '" + map.get("rb_kanri_kb") + "'  \n");
		sb.append("AND R_HINSYU_SEND.KANRI_CD = '" + map.get("tx_kanri_cd") + "'  \n");
		sb.append("AND R_HINSYU_SEND.SEND_DT = '" + map.get("tx_send_dt") + "'  \n");
		sb.append("AND R_HINSYU_SEND.TENPO_CD = '" + map.get("tx_tenpo_cd") + "'  \n");
		sb.append("AND R_HINSYU_SEND.SELECT_KB = '" + map.get("rb_select_kb") + "'  \n");

		if (!isZenten) {
			sb.append("AND R_HINSYU_SEND.TENPO_CD = R_TENPO.TENPO_CD  \n");
			sb.append("AND R_SYOHIN_TAIKEI.HINSYU_CD = R_HINSYU_SEND.HINSYU_CD \n");
		}
		sb.append("AND R_NAMECTF.CODE_CD = R_HINSYU_SEND.HINSYU_CD \n");
		sb.append("AND R_NAMECTF.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "'  \n");

		sb.append("AND R_HINSYU_SEND.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		if (!isZenten) {
			sb.append("AND R_TENPO.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");
		}
		sb.append("AND R_NAMECTF.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "'  \n");

		sb.append("ORDER BY TENPO_CD, R_HINSYU_SEND.HINSYU_CD \n");

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
