/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM商品登録画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM商品登録画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/19)初版作成
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

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BM商品登録画面の検索用DMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBM商品登録画面の検索用DMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst750301_BMSyohinListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst750301_BMSyohinListDM()
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
		mst750301_BMSyohinListBean bean = new mst750301_BMSyohinListBean();
		bean.setHankuCd( rest.getString("hanku_cd"));
		bean.setKanjiRn( rest.getString("kanji_rn"));
		bean.setSyohinCd( rest.getString("syohin_cd"));
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na"));
		bean.setBaitankaVl( rest.getLong("baitanka_vl"));
		bean.setInsertTs( rest.getString("insert_ts"));
		bean.setInsertUserId( rest.getString("insert_user_id"));
		bean.setUpdateTs( rest.getString("update_ts"));
		bean.setUpdateUserId( rest.getString("update_user_id"));
		bean.setDeleteFg( rest.getString("delete_fg"));
		bean.setInsertUserNm( rest.getString("insert_user_nm"));
		bean.setUpdateUserNm( rest.getString("update_user_nm"));
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

		sb.append(" SELECT TBL1.* , TBL2.USER_NA AS insert_user_nm, TBL3.USER_NA AS update_user_nm, TBL4.kanji_rn as kanji_rn, TBL5.hinmei_kanji_na as hinmei_kanji_na, TBL5.baitanka_vl as baitanka_vl \n");

		sb.append("FROM R_BUNDLEMIX_SYOHIN TBL1\n");
		sb.append("LEFT OUTER JOIN SYS_SOSASYA TBL2\n");
		sb.append("ON TBL1.insert_user_id = TBL2.user_id\n");
		sb.append("AND TBL2.hojin_cd ='" +  mst000101_ConstDictionary.HOJIN_CD + "'\n");
		sb.append("LEFT OUTER JOIN SYS_SOSASYA TBL3\n");
		sb.append("ON TBL1.update_user_id = TBL3.user_id\n");
		sb.append("AND TBL3.hojin_cd ='" +  mst000101_ConstDictionary.HOJIN_CD + "'\n");
		sb.append("LEFT OUTER JOIN R_NAMECTF TBL4\n");
		sb.append("ON TBL1.hanku_cd = TBL4.CODE_CD AND TBL4.SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) +"'\n");;
		sb.append("LEFT OUTER JOIN \n");
// 2005.09.01 Inaba View名変更 START
		sb.append("VW_R_SYOHIN ");
// 2005.09.01 Inaba View名変更 END
		sb.append("TBL5 \n");
		sb.append("ON TBL1.HANKU_CD = TBL5.HANKU_CD AND TBL1.SYOHIN_CD = TBL5.SYOHIN_CD\n");


		sb.append("WHERE TBL1.DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' \n");
		sb.append("AND TBL1.BUNDLEMIX_CD = '" + map.get("bundlemix_cd") + "'\n");
		sb.append("ORDER BY TBL1.HANKU_CD, TBL1.SYOHIN_CD");

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
