/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）キーPLUマスタ画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するキーPLUマスタ画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/13)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）キーPLUマスタ画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するキーPLUマスタ画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst710301_KeyPluListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private ArrayList tenpoItiran = null;	//店舗配列
	private String hanku_cd = null;		//販区コード
	private String syohin_cd = null;		//商品コード

	/**
	 * コンストラクタ
	 */
	public mst710301_KeyPluListDM()
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
		mst710301_KeyPluListBean bean = new mst710301_KeyPluListBean();
		bean.setKeypluNoNb(rest.getLong("keyplu_no_nb"));
		bean.setKanjiNa( rest.getString("kanji_na"));
		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setKanjiRn( rest.getString("kanji_rn"));
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setYukoDt( rest.getString("yuko_dt"));
		bean.setTenpoCdl( rest.getString("tenpo_cdl") );
		bean.setTkanjiRnl( rest.getString("tkanji_rnl") );
		bean.setFlgl( rest.getString("flgl") );
		bean.setInsertTsl( rest.getString("insert_tsl") );
		bean.setInsertUserIdl( rest.getString("insert_user_idl") );
		bean.setUpdateTsl( rest.getString("update_tsl") );
		bean.setUpdateUserIdl( rest.getString("update_user_idl") );
		bean.setInsertTs( rest.getString("insert_ts"));
		bean.setInsertUserId( rest.getString("insert_user_id"));
		bean.setUpdateTs( rest.getString("update_ts"));
		bean.setUpdateUserId( rest.getString("update_user_id"));
		bean.setDeleteFg( rest.getString("delete_fg"));
		bean.setOldFlgl( rest.getString("flgl") );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT SYOHIN.HINMEI_KANJI_NA, NAMECTF.KANJI_RN, TBL.* , \n");

		sb.append("			--店舗コード生成 \n");
		//店舗コード生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("(select TENPO_CD from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "')||','||\n");
			} else {
				sb.append("(select TENPO_CD from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "') as tenpo_cdl,\n");
			}

		}

		sb.append("			--店舗名生成 \n");
		//店舗名生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("(select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "')||','||\n");
			} else {
				sb.append("(select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "') as tkanji_rnl,\n");
			}

		}
		sb.append("			--店舗存在フラグ \n");

		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE
						+ "' as col from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'0')||','||\n");
			} else {
				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE
						+ "' as col from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'0') as flgl,\n");
			}
		}

		sb.append("			--レコード登録日時 \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_TS from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_TS from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_tsl,\n");
			}

		}

		sb.append("			--レコード登録者ID \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select INSERT_USER_ID from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select INSERT_USER_ID from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as insert_user_idl,\n");
			}

		}

		sb.append("			--レコード更新日時 \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_TS from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_TS from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_tsl,\n");
			}

		}

		sb.append("			--レコード更新者ID \n");
		//フラグ生成
		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
			if (intCnt != tenpoItiran.size() - 1) {
				sb.append("nvl((select UPDATE_USER_ID from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@')||','||\n");
			} else {
				sb.append("nvl((select UPDATE_USER_ID from R_TENBETU_KEYPLU t1 where t1.KEYPLU_NO_NB = tbl.KEYPLU_NO_NB and " +
										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "'),'@@@@@@@@') as update_user_idl\n");
			}

		}

		sb.append("FROM R_KEYPLU TBL, R_NAMECTF NAMECTF, ");
// 2005.09.01 Inaba View名変更 START
		sb.append("VW_R_SYOHIN SYOHIN \n");
// 2005.09.01 Inaba View名変更 START
		sb.append("WHERE SYOHIN.SYOHIN_CD = TBL.SYOHIN_CD AND SYOHIN.HANKU_CD = TBL.HANKU_CD ");
		sb.append("AND TBL.HANKU_CD = NAMECTF.CODE_CD AND NAMECTF.SYUBETU_NO_CD = '" + MessageUtil.getMessage("0040", userLocal) + "'");
		sb.append("AND TBL.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
		sb.append("ORDER BY TBL.KEYPLU_NO_NB ");

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
	/**
	 * 店舗配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpoItiran();　戻り値　店舗配列<br>
	 * <br>
	 * @return ArrayList 店舗配列
	 */
	public ArrayList getTenpoItiran() {
		return tenpoItiran;
	}

	/**
	 * 店舗配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpoItiran("店舗配列");<br>
	 * <br>
	 * @param list 店舗配列
	 */
	public void setTenpoItiran(ArrayList list) {
		tenpoItiran = list;
	}

	/**
	 * 販区コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	/**
	 * 販区コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHankuCd()
	{
		return this.hanku_cd;
	}

	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}

	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohinCd()
	{
		return this.syohin_cd;
	}
}
