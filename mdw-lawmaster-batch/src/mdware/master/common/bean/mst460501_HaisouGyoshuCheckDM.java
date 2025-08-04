/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/18)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.db.*;
import mdware.master.common.dictionary.*;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）配送先マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する配送先マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/18)初版作成
 */
public class mst460501_HaisouGyoshuCheckDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private String kanri_kb = null;	//管理区分
	private String kanri_cd = null;	//管理コード
	private String haisosaki_cd = null;	//配送先コード
	private String tenpo    = null;	//店舗コード

	/**
	 * コンストラクタ
	 */
	public mst460501_HaisouGyoshuCheckDM()
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
		mst460501_HaisouGyoshuCheckBean bean = new mst460501_HaisouGyoshuCheckBean();
		bean.setCnt( rest.getLong("cnt") );
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
		sb.append("	SUM(CNT) AS CNT FROM ");
		sb.append("(SELECT ");
		sb.append("	CNT ");
		sb.append("FROM ");

		if( map.get("syohin") != null && ((String)map.get("syohin")).trim().length() > 0 )
		{
			sb.append("	( ");
			sb.append("	 SELECT ");
			sb.append("	  COUNT(*) AS CNT ");
			sb.append("	 FROM ");
			sb.append("	  R_SYOHIN T1,");
//			sb.append("	  R_SYOHIN_TAIKEI T2,");
//			sb.append("	  R_HAISOU T3 ");
			sb.append("	  R_SYOHIN_TAIKEI T2");
			sb.append("	 WHERE ");
			sb.append("	  T2.D_GYOSYU_CD = '" + this.kanri_cd + "' ");
			sb.append("	  AND ");
			sb.append("	  T1.HANKU_CD = T2.HANKU_CD ");
			sb.append("	  AND ");
//			sb.append("	  T1.DAIHYO_HAISO_CD = '" + this.haisosaki_cd + "' ");
			sb.append("	  T1.DAIHYO_HAISO_CD = '" + this.haisosaki_cd.substring(0,4) + "' ");
			sb.append("	  AND ");
//			sb.append("	  T1.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			sb.append("	  T3.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//			sb.append("	  AND ");
			sb.append("	  T1.DELETE_FG = '0') UNION ALL ");
		}

		sb.append("	( ");
		sb.append("	 SELECT ");
		sb.append("	  COUNT(*) AS CNT ");
		sb.append("	 FROM ");
		sb.append("	  R_TENSYOHIN_REIGAI T1, ");
//		sb.append("	  R_SYOHIN_TAIKEI T2, ");
//		sb.append("	  R_HAISOU T3 ");
		sb.append("	  R_SYOHIN_TAIKEI T2");
		sb.append("	 WHERE ");
		sb.append("	  T2.D_GYOSYU_CD = '" + this.kanri_cd + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.HANKU_CD = T2.HANKU_CD ");
		sb.append("	  AND ");
		sb.append("	  T1.TENBETU_HAISO_CD = '" + this.haisosaki_cd + "' ");
		if( map.get("tenpo") != null && ((String)map.get("tenpo")).trim().length() > 0 )
		{
			sb.append("   AND ");
			sb.append("   T1.TENPO_CD = '" + this.tenpo + "' ");
		}
		sb.append("   AND ");
//		sb.append("   T1.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("   T3.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("   AND ");
		sb.append("	  T1.DELETE_FG = '0') UNION ALL ");
		sb.append("	( ");
		sb.append("	 SELECT ");
		sb.append("	  COUNT(*) AS CNT ");
		sb.append("	 FROM ");
		sb.append("	  R_HAKOKANRI T1 ");
		sb.append("	 WHERE ");
		sb.append("	  T1.KANRI_KB = '" + this.kanri_kb + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.KANRI_CD = '" + this.kanri_cd + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.SHIHAI_CD = '" + this.haisosaki_cd + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.SHIHAI_KB = '" + mst001001_ShihaiKbDictionary.HAISOUSAKI.getCode() + "' ");
		if( map.get("tenpo") != null && ((String)map.get("tenpo")).trim().length() > 0 )
		{
			sb.append("	  AND ");
			sb.append("   T1.TENPO_CD = '" + this.tenpo + "' ");
		}
		sb.append("   AND ");
//		sb.append("   T1.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("	  AND ");
		sb.append("	  T1.DELETE_FG = '0') UNION ALL ");
		sb.append("	( ");
		sb.append("	 SELECT ");
		sb.append("	  COUNT(*) AS CNT ");
		sb.append("	  FROM ");
		sb.append("	  R_BUTURYUKEIRO T1 ");
		sb.append("	 WHERE ");
		sb.append("	  T1.KANRI_KB = '" + this.kanri_kb + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.KANRI_CD = '" + this.kanri_cd + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.SIHAI_CD = '" + this.haisosaki_cd + "' ");
		sb.append("	  AND ");
		sb.append("	  T1.SIHAI_KB = '" + mst001001_ShihaiKbDictionary.HAISOUSAKI.getCode() + "' ");
		if( map.get("tenpo") != null && ((String)map.get("tenpo")).trim().length() > 0 )
		{
			sb.append("	  AND ");
			sb.append("	  T1.TENPO_CD = '" + this.tenpo + "' ");
		}
		sb.append("	  AND ");
//		sb.append("	  T1.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");
//		sb.append("	  AND ");
		sb.append("	  T1.DELETE_FG = '0' ");
		sb.append("	) ");
		sb.append(")");

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
	 * 管理区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setKanriKb(String kanri_kb)
		{
			this.kanri_kb = kanri_kb;
			return true;
		}
	/**
	 * 管理区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getKanriKb()
		{
			return this.kanri_kb;
		}


	/**
	 * 管理コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setKanriCd(String kanri_cd)
		{
			this.kanri_cd = kanri_cd;
			return true;
		}
	/**
	 * 管理コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanriCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getKanriCd()
		{
			return this.kanri_cd;
		}


	/**
	 * 配送先コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHaisosakiCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHaisosakiCd(String haisosaki_cd)
		{
			this.haisosaki_cd = haisosaki_cd;
			return true;
		}
	/**
	 * 配送先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHaisosakiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHaisosakiCd()
		{
			return this.haisosaki_cd;
		}

	/**
	 * 店舗コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTenpo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTenpo() {
		return tenpo;
	}

	/**
	 * 店舗コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTenpo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTenpo(String string) {
		tenpo = string;
	}

}
