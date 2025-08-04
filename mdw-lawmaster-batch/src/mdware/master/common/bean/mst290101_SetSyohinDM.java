/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス
 *               セット商品マスタ登録画面 mst290101_SetSyohin 用
 *               セット商品マスタ登録画面のレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する mst600101_Jidohaiban用計量器マスタのDMクラス(DmCreatorにより自動生成)</P>
 * <P>著作権: Copyright (c) 2005</p>								
 * <P>会社名: Vinculum Japan Corp.</P>								
 * @author k.hara
 * @version 1.0	(2005/04/13)	新規作成
 * @see なし								
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

	/**
	 * <p>タイトル: 新ＭＤシステム（マスター管理）セット商品マスタ登録画面のDMクラス</p>
	 * <p>説明: 新ＭＤシステムで使用するセット商品マスタ画面登録画面のDMクラス(DmCreatorにより自動生成)</p>
	 * <p>著作権: Copyright (c) 2005</p>
	 * <p>会社名: Vinculum Japan Corp.</p>
	 * @author Sirius Makuta
	 * @version 1.0(2005/03/16)初版作成
	 */
	public class mst290101_SetSyohinDM extends DataModule
	{
		private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		
		//バッチ日付
		private String batchDt = RMSTDATEUtil.getBatDateDt();
//		↓↓仕様変更（2005.07.28）↓↓
		private String mstDt = RMSTDATEUtil.getMstDateDt();
		/**
		 * コンストラクタ
		 */
		public mst290101_SetSyohinDM()
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

			List rlst = new ArrayList();		
			mst290401_KeepMeisaiBean bean = new  mst290401_KeepMeisaiBean();
			

			bean.setHankuCd1(rest.getString("HANKU_1_CD"));			//販区コード１
			bean.setSyohin1Cd(rest.getString("SYOHIN_1_CD"));		//商品コード
			bean.setHonsu1Qt(rest.getString("HONSU_1_QT"));			//本数
			
			bean.setHankuCd2(rest.getString("HANKU_2_CD"));			//販区コード２
			bean.setSyohin2Cd(rest.getString("SYOHIN_2_CD"));		//商品コード
			bean.setHonsu2Qt(rest.getString("HONSU_2_QT"));			//本数
			
			bean.setHankuCd3(rest.getString("HANKU_3_CD"));			////販区コード３
			bean.setSyohin3Cd(rest.getString("SYOHIN_3_CD"));		//商品コード
			bean.setHonsu3Qt(rest.getString("HONSU_3_QT"));			//本数
			
			bean.setHankuCd4(rest.getString("HANKU_4_CD"));			////販区コード4
			bean.setSyohin4Cd(rest.getString("SYOHIN_4_CD"));		//商品コード
			bean.setHonsu4Qt(rest.getString("HONSU_4_QT"));			//本数
			
			bean.setHankuCd5(rest.getString("HANKU_5_CD"));			////販区コード5
			bean.setSyohin5Cd(rest.getString("SYOHIN_5_CD"));		//商品コード
			bean.setHonsu5Qt(rest.getString("HONSU_5_QT"));			//本数
			
			bean.setHankuCd6(rest.getString("HANKU_6_CD"));			////販区コード6
			bean.setSyohin6Cd(rest.getString("SYOHIN_6_CD"));		//商品コード
			bean.setHonsu6Qt(rest.getString("HONSU_6_QT"));			//本数
			
			bean.setHankuCd7(rest.getString("HANKU_7_CD"));			////販区コード7
			bean.setSyohin7Cd(rest.getString("SYOHIN_7_CD"));		//商品コード
			bean.setHonsu7Qt(rest.getString("HONSU_7_QT"));			//本数
			
			bean.setHankuCd8(rest.getString("HANKU_8_CD"));			////販区コード8
			bean.setSyohin8Cd(rest.getString("SYOHIN_8_CD"));		//商品コード
			bean.setHonsu8Qt(rest.getString("HONSU_8_QT"));			//本数
			
			bean.setHankuCd9(rest.getString("HANKU_9_CD"));			////販区コード9
			bean.setSyohin9Cd(rest.getString("SYOHIN_9_CD"));		//商品コード
			bean.setHonsu9Qt(rest.getString("HONSU_9_QT"));			//本数
			
			bean.setHankuCd10(rest.getString("HANKU_10_CD"));		////販区コード１0
			bean.setSyohin10Cd(rest.getString("SYOHIN_10_CD"));		//商品コード
			bean.setHonsu10Qt(rest.getString("HONSU_10_QT"));		//本数
			
			bean.setHankuCd11(rest.getString("HANKU_11_CD"));		//販区コード１1
			bean.setSyohin11Cd(rest.getString("SYOHIN_11_CD"));		//商品コード
			bean.setHonsu11Qt(rest.getString("HONSU_11_QT"));		//本数
			
			bean.setHankuCd12(rest.getString("HANKU_12_CD"));		////販区コード１2
			bean.setSyohin12Cd(rest.getString("SYOHIN_12_CD"));		//商品コード
			bean.setHonsu12Qt(rest.getString("HONSU_12_QT"));		//本数
			
			bean.setInsertTs(rest.getString("INSERT_TS"));
			bean.setInsertUserId(rest.getString("INSERT_USER_ID"));
			bean.setUpdateTs(rest.getString("UPDATE_TS"));
			bean.setUpdateUserId(rest.getString("UPDATE_USER_ID"));
			bean.setSinki_toroku_dt(rest.getString("SINKI_TOROKU_DT"));
			bean.setHenko_dt(rest.getString("HENKO_DT"));

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
//			DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
			String whereStr = "where ";
			String andStr = " and ";
			StringBuffer sb = new StringBuffer();
		
	
			sb.append("SELECT ");

			for (int i=0; i <12; i++) {
		
				//販区コード		
				sb.append("  	sts.HANKU_" + (i+1) + "_CD ");
				sb.append("  	, ");
				//商品コード		
				sb.append("  	sts.SYOHIN_" + (i+1) + "_CD ");
				sb.append("  	, ");
				//本数				
				sb.append("  	sts.HONSU_" + (i+1) + "_QT ");

				sb.append("  	, ");

			
			}//FOR END
			
			sb.append(" sts.INSERT_TS, ");
			sb.append(" sts.INSERT_USER_ID, ");
			sb.append(" sts.UPDATE_TS, ");
			sb.append(" sts.UPDATE_USER_ID, ");
			sb.append(" sts.SINKI_TOROKU_DT, ");
			sb.append(" sts.HENKO_DT ");
		
		
			//FROM セット商品マスタテーブル				
			sb.append(" FROM  ");
			sb.append("		R_SETSYOHIN sts ");
	
			//WHERE句						
			sb.append(" WHERE ");
			sb.append(" 	sts.HANKU_CD = '" + map.get("hanku_cd") + "' ");
			sb.append(" AND	sts.SETSYOHIN_CD = '" + map.get("setsyohin_cd") + "' ");
			sb.append(" AND	sts.DELETE_FG = '" + map.get("delete_fg") + "' ");
	
		return sb.toString();
		}


  /**
   * 新規登録用ＳＱＬの生成を行う。
   * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
   * @param beanMst Object
   * @return String 生成されたＳＱＬ
   */
  public String getInsertSql( mst290201_KeepBean bean )
  {
	  boolean befKanma = false;
	  boolean aftKanma = false;
		
//	  DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	  StringBuffer sb = new StringBuffer();
	  sb.append("insert into ");
	  sb.append("R_SETSYOHIN (");

		//販区コード
		if( bean.getHankuCd()!= null && bean.getHankuCd().trim().length() != 0 )
		{
			befKanma = true;
			sb.append(" hanku_cd ");
		}
		//セット商品コード
		if( bean.getSetSyohinCd() != null && bean.getSetSyohinCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" setsyohin_cd ");
		}

		for (int i=0; i < 12; i++) {

			//セット商品内、販区コード
			if( bean.meisaiBean.getHankuCd(i+1)!= null && bean.meisaiBean.getHankuCd(i+1).trim().length() != 0 )
			{
	
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" hanku_" + (i+1) + "_cd ");
			}
		
			
		   //セット商品内、商品コード
			if( bean.meisaiBean.getSyohinCd(i+1)!= null && bean.meisaiBean.getSyohinCd(i+1).trim().length() != 0 )
			{
	
				if( befKanma ) sb.append(","); else befKanma = true;
				sb.append(" syohin_" + (i+1) + "_cd ");
			}
		  
		  
			//本数
			if( bean.meisaiBean.getHonsuQt(i+1) != null && bean.meisaiBean.getHonsuQt(i+1).trim().length() != 0 )
			{
			 	if( befKanma ) sb.append(","); else befKanma = true;
				  sb.append(" honsu_" + (i+1) + "_qt ");
			}
		  
		}//For END
			
		//登録日時
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" Insert_ts");

			
		//登録者ID
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" Insert_user_id");

			
		//更新日時
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
			
		//登録者ID
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
			
		//削除フラグ
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" delete_fg");

		//新規登録日
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" SINKI_TOROKU_DT");

				
		//変更日
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" HENKO_DT");

		sb.append(")Values(");
		  
			
		//販区コード
		if( bean.getHankuCd()!= null && bean.getHankuCd().trim().length() != 0 )
		{
		aftKanma = true;
		sb.append("'" + conv.convertString( bean.getHankuCd() ) + "'");
		}
		
		//セット商品コード
		if( bean.getSetSyohinCd() != null && bean.getSetSyohinCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSetSyohinCd() ) + "'");
		}
		  
		for(int i=0; i < 12; i++) {
		  
			//セット商品内販区コード１入力データ
			if( bean.meisaiBean.getHankuCd(i+1) != null && bean.meisaiBean.getHankuCd(i+1).trim().length() != 0 )
			{
	
				if( aftKanma ) sb.append(","); else aftKanma = true;
				sb.append("'" + conv.convertString( bean.meisaiBean.getHankuCd(i+1) ) + "'");
			}	
	  	
				
			//セット商品内商品コード１入力データ
			if( bean.meisaiBean.getSyohinCd(i+1) != null && bean.meisaiBean.getSyohinCd(i+1).trim().length() != 0 )
			{
	
				if( aftKanma ) sb.append(","); else aftKanma = true;
				sb.append("'" + conv.convertString( bean.meisaiBean.getSyohinCd(i+1) ) + "'");
			}
				
			//本数１入力データ
			if( bean.meisaiBean.getHonsuQt(i+1) != null && bean.meisaiBean.getHonsuQt(i+1).trim().length() != 0 )
			{
				if( aftKanma ) sb.append(","); else aftKanma = true;
				sb.append("'" + conv.convertString( bean.meisaiBean.getHonsuQt(i+1) ) + "'");
			}
		  
		}//For End
		  
		//登録日

		if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");

		//登録者ID
		if( aftKanma ) sb.append(","); else aftKanma = true;
		sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");

		//更新日
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		
		//更新者ID
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");			
		}
		
		//削除フラグ
		if( aftKanma ) sb.append(","); else aftKanma = true;
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		//新規登録日
		if( befKanma ) sb.append(","); else befKanma = true;
//		↓↓仕様変更（2005.07.28）↓↓
//		sb.append(" '"+ batchDt +"' ");
		sb.append(" '"+ mstDt +"' ");
//		↑↑仕様変更（2005.07.28）↑↑
					
		//変更日
		if( befKanma ) sb.append(","); else befKanma = true;
//		↓↓仕様変更（2005.07.28）↓↓
//		sb.append(" '"+ batchDt +"' ");
		sb.append(" '"+ mstDt +"' ");
//		↑↑仕様変更（2005.07.28）↑↑
		 
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
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst290201_KeepBean bean = (mst290201_KeepBean) beanMst;
		boolean whereAnd = false;
		StringBuffer sb = new StringBuffer();
		String SetStr = " set ";
		
		//▼更新項目設定
		sb.append(" UPDATE ");
		sb.append("     R_SETSYOHIN ");
	
		for (int i=0; i <12; i++) {
		
			//販区コード
			if( bean.meisaiBean.getHankuCd(i+1) != null ) {
				sb.append(SetStr);
				sb.append("  	HANKU_" + (i+1) + "_CD = ");
				sb.append("'" + conv.convertString( bean.meisaiBean.getHankuCd(i+1) ) + "'");
				SetStr = "  	, ";
			}		
										
			//商品コード
			if( bean.meisaiBean.getSyohinCd(i+1) != null ) {		
				sb.append(SetStr);
				sb.append("  	SYOHIN_" + (i+1) + "_CD =");
				sb.append("'" + conv.convertString( bean.meisaiBean.getSyohinCd(i+1) ) + "'");
				SetStr = "  	, ";
			}
				
			//本数
			if( bean.meisaiBean.getHonsuQt(i+1) != null  ) {						
				sb.append(SetStr);
				sb.append("  	HONSU_" + (i+1) + "_QT = ");
				sb.append("'" + conv.convertString( bean.meisaiBean.getHonsuQt(i+1) ) + "'");
				SetStr = "  	, ";
			}
						
		}//FOR END
		
		//更新日
		sb.append(" ,UPDATE_TS = ");
		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "',");
			
		//更新者ID
		sb.append("UPDATE_USER_ID = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "',");
		
		//変更日
		sb.append(" HENKO_DT = ");
//		↓↓仕様変更（2005.07.28）↓↓
//		sb.append(" '"+ batchDt +"' ");
		sb.append(" '"+ mstDt +"' ");
//		↑↑仕様変更（2005.07.28）↑↑

		//WHERE句
		sb.append(" WHERE ");
		
		if( bean.getHankuCd() != null && bean.getHankuCd().trim().length() > 0 ) {												
			sb.append(" 	HANKU_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getHankuCd() ) + "'");
			sb.append(" AND	SETSYOHIN_CD = ");
			sb.append("'" + conv.convertWhereString( bean.getSetSyohinCd() ) + "'");
		}

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
		
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst290201_KeepBean bean = (mst290201_KeepBean) beanMst;
		StringBuffer sb = new StringBuffer();
	
		sb.append(" UPDATE ");
		
		sb.append("     R_SETSYOHIN ");
		
		sb.append(" SET ");		
		//更新日
		sb.append(" UPDATE_TS = ");
		sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "',");
				
		//更新者ID
		sb.append("UPDATE_USER_ID = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "',");
			
		//変更日
		sb.append(" HENKO_DT = ");
//		↓↓仕様変更（2005.07.28）↓↓
//		sb.append(" '"+ batchDt +"' ");
		sb.append(" '"+ mstDt +"', ");
//		↑↑仕様変更（2005.07.28）↑↑

		//削除フラグ
		sb.append(" DELETE_FG = ");		
		sb.append("'" + mst000801_DelFlagDictionary.IRU.getCode() + "'");
		
		sb.append(" WHERE ");
		sb.append("     HANKU_CD = '" + conv.convertWhereString( bean.getHankuCd() ) + "'");
		sb.append(" AND");
		sb.append("     SETSYOHIN_CD = '" + conv.convertWhereString( bean.getSetSyohinCd() ) + "'");

		return sb.toString();
	}
	


	/**
	 * 商品名検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectSyohinSql( Map map )
	{
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("	hanku_cd ");
		sb.append(", ");
		sb.append("	syohin_cd ");
		sb.append(", ");
		sb.append("	yuko_dt ");
		sb.append(", ");
		sb.append("	hinsyu_cd ");
		sb.append(", ");
		sb.append("	hinmei_kanji_na ");
		sb.append(", ");
		sb.append("	delete_fg ");
		sb.append("from R_SYOHIN ");
		sb.append("where ");
		sb.append("	hanku_cd = '" + conv.convertWhereString( (String)map.get("hanku_cd") ) + "' ");

		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append("AND ");
			sb.append("	syohin_cd >= '" + conv.convertWhereString( (String)map.get("syohin_cd") ) + "' ");
		}

		sb.append("order by ");
		sb.append("	syohin_cd");
		sb.append(",");
		sb.append("	yuko_dt desc");
	
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
	 * セット商品マスタデータの削除フラグを取得する
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectMstDlSql( String hankuCd, String setsyohinCd )
	{

		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		sb.append("	R_SETSYOHIN " );
		sb.append("WHERE ");

		sb.append( " HANKU_CD = '" + hankuCd + "' " );
		sb.append( " AND " );
		sb.append( " SETSYOHIN_CD = '" + setsyohinCd + "' " );
		sb.append( " AND " );
		sb.append( " DELETE_FG = '1' " );

		return sb.toString();
	}
	
	public String getInsertSql(Object bean) {
		return null;
	}
}

