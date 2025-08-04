package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.RMSTDATEUtil;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 * 変更履歴 : (2006.03.23) K.Satobo  削除時の下位コード存在チェック対応
 */
public class mst000701_MasterInfoDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst000701_MasterInfoDM()
	{
		super(  mst000101_ConstDictionary.CONNECTION_STR);
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
		mst000701_MasterInfoBean bean = new mst000701_MasterInfoBean();
		bean.setCdName( rest.getString("cd_name") );
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
		return null;	
	}
	
	/**
	 * マスタの情報を取得する
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSql( String tbl,String Column, List whereList, String dtchk, String yukodt)
	throws Exception {
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	" + Column + " AS CD_NAME ");
		sb.append("FROM ");
		sb.append("	" + tbl + " " );
		sb.append("WHERE ");
		if(!dtchk.equals("1")){
			sb.append("	delete_fg = '0' and ");
		}

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		//有効日
		if(dtchk.equals("1")){
			String yukoDt = new String();
			String MSTDATE = new String();
			//===BEGIN=== Modify by kou 2006/11/15
			// Add try--catch--finally block to close db
//			mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();
//			if(yukodt.equals(""))
//				MSTDATE = RMSTDATEUtil.getMstDateDt();
//			else
//				MSTDATE = yukodt;
//							
////			yukoDt=mst001001_EffectiveDayBean.getGenYoyakuJsp(tbl,"yuko_dt",whereList,"1");
//			//現在有効日取得
//			yukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, tbl,"yuko_dt",whereList, MSTDATE, true);
			mst000101_DbmsBean db = null;
			try
			{
							db = mst000101_DbmsBean.getInstance();
							if(yukodt.equals(""))
								MSTDATE = RMSTDATEUtil.getMstDateDt();
							else
								MSTDATE = yukodt;
							
				//			yukoDt=mst001001_EffectiveDayBean.getGenYoyakuJsp(tbl,"yuko_dt",whereList,"1");
							//現在有効日取得
							yukoDt = mst000201_EffectiveDayCheckBean.getNowYukoDt(db, tbl,"yuko_dt",whereList, MSTDATE, true);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw e;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
			finally
			{
				if(db != null) db.close();
				db = null;
			}
			//===END=== Modify by kou 2006/11/15

//			↓↓2006.07.13 xiongjun カスタマイズ修正↓↓
//			sb.append(" and ");
//			sb.append(" YUKO_DT ='" + yukoDt + "' ");
			if (whereList != null && whereList.size() == 0) {
				sb.append(" YUKO_DT ='" + yukoDt + "' ");
			} else {
				sb.append(" and ");
				sb.append(" YUKO_DT ='" + yukoDt + "' ");
			}
//			↑↑2006.07.13 xiongjun カスタマイズ修正↑↑
		}
//		↓↓2006.11.21 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" for read only ");
//		↑↑2006.11.21 H.Yamamoto カスタマイズ修正↑↑
		return sb.toString();
	}

	/**
	 * マスタの情報を取得する
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectAnySql( String tbl, List Columns, List whereList, String dtchk)
	throws Exception {
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		
		StringBuffer sb = new StringBuffer();

	try{
		sb.append("SELECT ");
		if (Columns == null) {
			sb.append("	NULL ");
		} else {
			for (int i = 0;i < Columns.size();i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append((String)Columns.get(i)); 
			}
		}
		sb.append(" FROM ");
		sb.append("	" + tbl + " " );
		sb.append("WHERE ");

		if(!dtchk.equals("1")){
			sb.append("	delete_fg = '0' and ");
		}

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}
		//有効日
		if(dtchk.equals("1")){
			String yukoDt = new String();
			yukoDt=mst001001_EffectiveDayBean.getGenYoyakuJsp(tbl,"yuko_dt",whereList,"1");
			sb.append(" and ");
			sb.append(" YUKO_DT ='" + yukoDt + "' ");
		}

		} catch(Exception e) {					
			throw e;
		} finally {
		}
		return sb.toString();
	}

	
	/**
	 * マスタの存在チェック用データを取得する
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectMstSql( String tbl, List whereList )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	delete_fg ");
		sb.append("FROM ");
		sb.append("	" + tbl + " " );
		sb.append("WHERE ");
		sb.append("	delete_fg = '0' AND ");

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

		return sb.toString();
	}

// [NO.18] 2006.03.23 K.Satobo ADD ↓ 削除時の下位コード存在チェック対応
	/**
	 * マスタの存在チェック用データを取得する
	 * 受け取ったパラメータを元にWHERE区を作成する。（商品階層マスタで使用）
	 * <br>
	 * ※getSelectMstSql(String, List) との相違点
	 * 　→ 削除フラグの状態に関わらず、存在チェックを行う。
	 *     (削除予約のレコードも存在と見なす)
	 * <br>
	 * @param tbl			テーブル名称
	 * @param whereList	WHERE条件リスト
	 * @return String		生成されたSQL
	 */
	public String getSelectMstContDelSql( String tbl, List whereList )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	delete_fg ");
		sb.append("FROM ");
		sb.append("	" + tbl + " " );
		sb.append("WHERE ");

		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

		return sb.toString();
	}
// [NO.18] 2006.03.23 K.Satobo ADD ↑

	/**
	 * 対象レコードのCOUNT取得用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getCountSql( String tbl, List whereList )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("		COUNT(*) CNT ");
		sb.append(" FROM ");
		sb.append("		" +  tbl + " ");
		sb.append(" WHERE ");

		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
		}

		return sb.toString();
	}
	/**
	 * 商品体系マスタの情報を取得する
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSyohinTaikeiSelectSql( String Column, List whereList )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	" + Column + " AS CD_NAME ");
		sb.append("FROM ");
		sb.append("	r_syohin_taikei " );
		sb.append("WHERE ");
		//キー値セット
		for (int i=0; i < whereList.size(); i++) {
			sb.append(whereList.get(i));
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
