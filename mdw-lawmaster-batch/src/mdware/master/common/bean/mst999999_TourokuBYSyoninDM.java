package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <P>登録票バイヤー承認検索とか用DM</P>
 */
public class mst999999_TourokuBYSyoninDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst999999_TourokuBYSyoninDM()
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
	protected Object instanceBean( ResultSet rest )
		throws SQLException
	{
		mst999999_TourokuBYSyoninBean bean = new mst999999_TourokuBYSyoninBean();
		bean.setTorikomiDt( rest.getString("torikomi_dt") );
		bean.setExcelFileSyubetsu( rest.getString("excel_file_syubetsu") );
		bean.setUketsukeNo( rest.getString("uketsuke_no") );
		bean.setExcelFileNa( rest.getString("excel_file_na") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setUploadCommentTx( rest.getString("upload_comment_tx") );
		bean.setTorokuSyoninFg( rest.getString("toroku_syonin_fg") );
		bean.setTorokuSyoninTs( rest.getString("toroku_syonin_ts") );
		bean.setByNo( rest.getString("by_no") );
		bean.setSyoninCommentTx( rest.getString("syonin_comment_tx") );
		bean.setExcelSyohinKb( rest.getString("excel_syohin_kb") );
		bean.setExcelTanpinKb( rest.getString("excel_tanpin_kb") );
		bean.setExcelReigaiKb( rest.getString("excel_reigai_kb") );
		bean.setExcelSyokaiKb( rest.getString("excel_syokai_kb") );
		bean.setMinNeireRt( rest.getString("min_neire_rt") );
		bean.setMaxNeireRt( rest.getString("max_neire_rt") );
		bean.setMaxUritankaVl( rest.getString("max_uritanka_vl") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("TORIKOMI_DT,");
		sb.append("EXCEL_FILE_SYUBETSU,");
		sb.append("UKETSUKE_NO,");
		sb.append("EXCEL_FILE_NA,");
		sb.append("SIIRESAKI_CD,");
		sb.append("UPLOAD_COMMENT_TX,");
		sb.append("TOROKU_SYONIN_FG,");
		sb.append("TOROKU_SYONIN_TS,");
		sb.append("BY_NO,");
		sb.append("SYONIN_COMMENT_TX,");
		sb.append("EXCEL_SYOHIN_KB,");
		sb.append("EXCEL_TANPIN_KB,");
		sb.append("EXCEL_REIGAI_KB,");
		sb.append("EXCEL_SYOKAI_KB,");
		sb.append("MIN_NEIRE_RT,");
		sb.append("MAX_NEIRE_RT,");
		sb.append("MAX_URITANKA_VL,");
		sb.append("INSERT_TS,");
		sb.append("INSERT_USER_ID,");
		sb.append("UPDATE_TS,");
		sb.append("UPDATE_USER_ID ");
		sb.append(" FROM ");
		sb.append("TR_TOROKU_SYONIN ");
		
//		sb.append("WHERE ");
//		if(!mst000401_LogicBean.chkNullString((String)map.get("by_no")).equals("")){
//			sb.append("BY_NO = ");
//			sb.append("'" + conv.convertWhereString((String)map.get("by_no")) + "' ");
//			sb.append("	AND ");
//		}
//		sb.append(" TOROKU_SYONIN_FG = ");
//		sb.append("'" + conv.convertWhereString( (String)map.get("toroku_syonin_fg")) + "' ");
		
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
