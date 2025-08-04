package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル: MaChubunruiDM クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2002.09.09) Version 1.0.IST_CUSTOM.1
 * @version X.X (Create time: 2004/1/21 17:51:30)
 */
public class MaChubunruiDM extends DataModule
{
	private static int sequence = -1;
	private static Object o = new Object();
	/**
	 * 連番を使用しINSERTを行う時はこのメソッドを呼び出してください。
	 * @return 最大の連番＋１を返す。
	 */
	private synchronized int getNextSeq()
	{
		int retSeq = -1;
		synchronized(o)
		{
			if( sequence < 0 )
				sequence = Integer.parseInt(super.getNextSequence("------","Ma_Chubunrui"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public MaChubunruiDM()
	{
		super( "rbsite_ora");
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
		MaChubunruiBean bean = new MaChubunruiBean();
		bean.setChubunruiCd( rest.getString("chubunrui_cd") );
		bean.setChubunruiNa( rest.getString("chubunrui_na") );
		bean.setChubunruiKa( rest.getString("chubunrui_ka") );
		bean.setDaibunruiCd( rest.getString("daibunrui_cd") );
		bean.setRiyoUserId( rest.getString("riyo_user_id") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setUpdateTs( rest.getString("update_ts") );
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
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select * from Ma_Chubunrui ");
		if( map.get("chubunrui_cd") != null && ((String)map.get("chubunrui_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("chubunrui_cd = '" + (String)map.get("chubunrui_cd") + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("chubunrui_cd");
		sb.append(",");
		sb.append("chubunrui_na");
		sb.append(",");
		sb.append("chubunrui_ka");
		sb.append(",");
		sb.append("daibunrui_cd");
		sb.append(",");
		sb.append("riyo_user_id");
		sb.append(",");
		sb.append("insert_ts");
		sb.append(",");
		sb.append("update_ts");
		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢに挿入するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	public String getInsertSql( Object beanMst )
	{
		MaChubunruiBean bean = (MaChubunruiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("Ma_Chubunrui (");
		sb.append(" chubunrui_cd");
		sb.append(",");
		sb.append(" chubunrui_na");
		sb.append(",");
		sb.append(" chubunrui_ka");
		sb.append(",");
		sb.append(" daibunrui_cd");
		sb.append(",");
		sb.append(" riyo_user_id");
		sb.append(",");
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getChubunruiCdString());
		sb.append(",");
		sb.append( bean.getChubunruiNaString());
		sb.append(",");
		sb.append( bean.getChubunruiKaString());
		sb.append(",");
		sb.append( bean.getDaibunruiCdString());
		sb.append(",");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append( bean.getUpdateTsString());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 * 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getUpdateSql( Object beanMst )
	{
		MaChubunruiBean bean = (MaChubunruiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("Ma_Chubunrui set ");
		sb.append(" chubunrui_cd = ");
		sb.append( bean.getChubunruiCdString());
		sb.append(",");
		sb.append(" chubunrui_na = ");
		sb.append( bean.getChubunruiNaString());
		sb.append(",");
		sb.append(" chubunrui_ka = ");
		sb.append( bean.getChubunruiKaString());
		sb.append(",");
		sb.append(" daibunrui_cd = ");
		sb.append( bean.getDaibunruiCdString());
		sb.append(",");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(",");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" chubunrui_cd = ");
		sb.append( bean.getChubunruiCdString());
		sb.append(" AND");
		sb.append(" chubunrui_na = ");
		sb.append( bean.getChubunruiNaString());
		sb.append(" AND");
		sb.append(" chubunrui_ka = ");
		sb.append( bean.getChubunruiKaString());
		sb.append(" AND");
		sb.append(" daibunrui_cd = ");
		sb.append( bean.getDaibunruiCdString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 * 渡されたBEANをＤＢから削除するためのＳＱＬ。
	 * @param beanMst Object
	 * @return String 生成されたＳＱＬ
	 */
	//  検索キーが分からないのでＷＨＥＲＥの所は修正してください。
	public String getDeleteSql( Object beanMst )
	{
		MaChubunruiBean bean = (MaChubunruiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("Ma_Chubunrui where ");
		sb.append(" chubunrui_cd = ");
		sb.append( bean.getChubunruiCdString());
		sb.append(" AND");
		sb.append(" chubunrui_na = ");
		sb.append( bean.getChubunruiNaString());
		sb.append(" AND");
		sb.append(" chubunrui_ka = ");
		sb.append( bean.getChubunruiKaString());
		sb.append(" AND");
		sb.append(" daibunrui_cd = ");
		sb.append( bean.getDaibunruiCdString());
		sb.append(" AND");
		sb.append(" riyo_user_id = ");
		sb.append( bean.getRiyoUserIdString());
		sb.append(" AND");
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(" AND");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		return sb.toString();
	}

}
