/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタの表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス(DmCreatorにより自動生成)</P>
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
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst840201_Tenkabutu用添加物マスタマスタの表示用DMクラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
public class mst840201_TenkabutuListDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst840201_TenkabutuListDM()
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
		mst840201_TenkabutuListBean bean = new mst840201_TenkabutuListBean();
		bean.setCheckFlg( rest.getString("check_flg") );
		bean.setOptFlg( rest.getString("opt_flg") );
		bean.setTenkabutuCd( rest.getString("tenkabutu_cd") );
		bean.setTenkabutuNa( rest.getString("tenkabutu_na") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateTsShort( rest.getString("update_ts_short") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setChangeFlg("0");
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
		String blank = mst000401_LogicBean.chkNullString((String)map.get("h_blankkb")).trim();

		start = Long.parseLong((String)map.get("start_rows"));
		end   = Long.parseLong((String)map.get("end_rows"));
		if(end > maxrows + 1){
			end   = maxrows + 1 ;
		}

//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT \n");
		sb.append("	'0' check_flg,");
		sb.append(" '0' opt_flg,");
		sb.append(" DECODE(TB2.TENKABUTU_CD,NULL, "
					+ mst000801_DelFlagDictionary.IRU.getCode() 
					+ "," + mst000801_DelFlagDictionary.INAI.getCode() 
					+ ") AS DELETE_FG, \n");
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("	TO_CHAR(TB1.CD,'FM0000')	AS TENKABUTU_CD, \n");
		} else {
			sb.append("	TO_CHAR(TB2.TENKABUTU_CD,'FM0000')	AS TENKABUTU_CD, \n");
		}
		sb.append("	RTRIM(NVL(TB2.TENKABUTU_NA,'')) AS TENKABUTU_NA, \n");
		sb.append("	NVL(TB2.UPDATE_TS,' ')    AS UPDATE_TS, \n");
		sb.append("	SUBSTR(NVL(TB2.UPDATE_TS,''),0,8)    AS UPDATE_TS_SHORT \n");
		sb.append("FROM \n");
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("	( \n");
			for(cnt=start; cnt < end ; cnt++ ){
				if(cnt!=start){
					sb.append("     UNION ALL ");
				}
				sb.append("		SELECT " + cnt + " AS CD FROM DUAL \n");
			}
			sb.append("			) TB1 , \n");
		}
		sb.append("			(SELECT \n");
		sb.append("			   TENKABUTU_CD, \n");
		sb.append("			   TENKABUTU_NA, \n");
		sb.append("			   UPDATE_TS \n");
		sb.append("			 FROM \n");
		sb.append("			   R_TENKABUTU TB2 \n");
		sb.append("			 WHERE \n");
		sb.append("			   DELETE_FG IS NULL OR DELETE_FG<>'" + mst000801_DelFlagDictionary.IRU.getCode() + "' \n");
		sb.append("			) TB2 \n");
//BUGNO-S023 20050422 S.Murata START
//		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
//			sb.append("		WHERE \n");
//			sb.append("			TB1.CD = TB2.TENKABUTU_CD(+) \n");
//		}
//		// tenkabutu_cd に対するWHERE区
//		if( map.get("tenkabutu_cd_bef") != null && ((String)map.get("tenkabutu_cd_bef")).trim().length() > 0 )
//		{
//			sb.append(" AND ");
//			if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
//				sb.append("TB1.CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
//			} else {
//				sb.append("TB2.TENKABUTU_CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
//			}
//		}
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append("		WHERE \n");
			sb.append("			TB1.CD = TB2.TENKABUTU_CD(+) \n");
			if( map.get("tenkabutu_cd_bef") != null && ((String)map.get("tenkabutu_cd_bef")).trim().length() > 0 )
			{
				sb.append(" AND ");
				if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
					sb.append("TB1.CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
				} else {
					sb.append("TB2.TENKABUTU_CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
				}
			}
		} else {
			// tenkabutu_cd に対するWHERE区
			if( map.get("tenkabutu_cd_bef") != null && ((String)map.get("tenkabutu_cd_bef")).trim().length() > 0 )
			{
				sb.append("		WHERE \n");
				if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
					sb.append("TB1.CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
				} else {
					sb.append("TB2.TENKABUTU_CD >= '" + conv.convertWhereString( (String)map.get("tenkabutu_cd_bef") ) + "' \n");
				}
			}
		}
//BUGNO-S023 20050422 S.Murata END
		if(!blank.equals(mst000101_ConstDictionary.FUNCTION_FALSE)){
			sb.append(" ORDER BY TB1.CD ");
		} else {
			sb.append(" ORDER BY TB2.TENKABUTU_CD ");
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
