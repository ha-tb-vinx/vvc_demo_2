package mdware.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル: RTorihikisakiDM クラス</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003</p>
 * <p>会社名: </p>
 * @author DataModule Creator(2002.09.09) Version 1.0.IST_CUSTOM.1
 * @version X.X (Create time: 2003/11/26 10:3:26)
 * @version 1.0 20040210 本番リリース
 * @version 1.1 20040221 変更№70対応
 * @version 1.2 20050302 WEB-EDI本番移行対応
 */
public class RTorihikisakiDM extends DataModule
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
				sequence = Integer.parseInt(super.getNextSequence("------","R_Torihikisaki"));
			sequence++;
			retSeq = sequence;
		}
		return retSeq;
	}
	/**
	 * コンストラクタ
	 */
	public RTorihikisakiDM()
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
		RTorihikisakiBean bean = new RTorihikisakiBean();
		bean.setTorihikisakiCd( rest.getString("torihikisaki_cd") );
		bean.setTorihikisakiNa( rest.getString("torihikisaki_na") );
		bean.setTorihikisakiKa( rest.getString("torihikisaki_ka") );
		bean.setTelNb( rest.getString("tel_nb") );
		bean.setTorihikisakiKb( rest.getString("torihikisaki_kb") );
		bean.setHachuyoIfFg( rest.getString("hachuyo_if_fg") );
		bean.setHachuIfFg( rest.getString("hachu_if_fg") );
		bean.setHachuIfHaisinFg( rest.getString("hachu_if_haisin_fg") );
		bean.setNohinIfFg( rest.getString("nohin_if_fg") );
		bean.setJuryoIfFg( rest.getString("juryo_if_fg") );
		bean.setSiharaiIfFg( rest.getString("siharai_if_fg") );
		bean.setHonStartDt( rest.getString("hon_start_dt") ); //2004.02.21 @ADD yamanaka
		bean.setHonEndDt( rest.getString("hon_end_dt") ); //2004.02.21 @ADD yamanaka
// 2005/03/02 add takata begin
		bean.setSeisenWebEdiTaisyoFg( rest.getString("seisen_web_edi_taisyo_fg") );
		bean.setSeisenHonStartDt( rest.getString("seisen_hon_start_dt") );
		bean.setSeisenHonEndDt( rest.getString("seisen_hon_end_dt") );
		bean.setGroceryWebEdiTaisyoFg( rest.getString("grocery_web_edi_taisyo_fg") );
		bean.setGroceryHonStartDt( rest.getString("grocery_hon_start_dt") );
		bean.setGroceryHonEndDt( rest.getString("grocery_hon_end_dt") );
		bean.setIryoWebEdiTaisyoFg( rest.getString("iryo_web_edi_taisyo_fg") );
		bean.setIryoHonStartDt( rest.getString("iryo_hon_start_dt") );
		bean.setIryoHonEndDt( rest.getString("iryo_hon_end_dt") );
		bean.setJuseikatuWebEdiTaisyoFg( rest.getString("juseikatu_web_edi_taisyo_fg") );
		bean.setJuseikatuHonStartDt( rest.getString("juseikatu_hon_start_dt") );
		bean.setJuseikatuHonEndDt( rest.getString("juseikatu_hon_end_dt") );
// 2005/03/02 add takata end
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
		sb.append("select * from R_Torihikisaki ");
		if( map.get("torihikisaki_cd") != null && ((String)map.get("torihikisaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("torihikisaki_cd = '" + (String)map.get("torihikisaki_cd") + "'");
			whereStr = andStr;
		}
		if( map.get("hachuyo_if_fg") != null && ((String)map.get("hachuyo_if_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("hachuyo_if_fg = '" + (String)map.get("hachuyo_if_fg") + "'");
			whereStr = andStr;
		}
		sb.append(" order by ");
		sb.append("torihikisaki_cd");
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
		RTorihikisakiBean bean = (RTorihikisakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_Torihikisaki (");
		sb.append(" torihikisaki_cd");
		sb.append(",");
		sb.append(" torihikisaki_na");
		sb.append(",");
		sb.append(" torihikisaki_ka");
		sb.append(",");
		sb.append(" tel_nb");
		sb.append(",");
		sb.append(" torihikisaki_kb");
		sb.append(",");
		sb.append(" hachuyo_if_fg");
		sb.append(",");
		sb.append(" hachu_if_fg");
		sb.append(",");
		sb.append(" hachu_if_haisin_fg");
		sb.append(",");
		sb.append(" nohin_if_fg");
		sb.append(",");
		sb.append(" juryo_if_fg");
		sb.append(",");
		sb.append(" siharai_if_fg");
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 start
		sb.append(" hon_start_dt");
		sb.append(",");
		sb.append(" hon_end_dt");
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 end

// 2005/03/02 add takata begin
		sb.append(" seisen_web_edi_taisyo_fg");
		sb.append(",");
		sb.append(" seisen_hon_start_dt");
		sb.append(",");
		sb.append(" seisen_hon_end_dt");
		sb.append(",");
		sb.append(" grocery_web_edi_taisyo_fg");
		sb.append(",");
		sb.append(" grocery_hon_start_dt");
		sb.append(",");
		sb.append(" grocery_hon_end_dt");
		sb.append(",");
		sb.append(" iryo_web_edi_taisyo_fg");
		sb.append(",");
		sb.append(" iryo_hon_start_dt");
		sb.append(",");
		sb.append(" iryo_hon_end_dt");
		sb.append(",");
		sb.append(" juseikatu_web_edi_taisyo_fg");
		sb.append(",");
		sb.append(" juseikatu_hon_start_dt");
		sb.append(",");
		sb.append(" juseikatu_hon_end_dt");
		sb.append(",");
// 2005/03/02 add takata end
		sb.append(" insert_ts");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(")Values(");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append( bean.getTorihikisakiNaString());
		sb.append(",");
		sb.append( bean.getTorihikisakiKaString());
		sb.append(",");
		sb.append( bean.getTelNbString());
		sb.append(",");
		sb.append( bean.getTorihikisakiKbString());
		sb.append(",");
		sb.append( bean.getHachuyoIfFgString());
		sb.append(",");
		sb.append( bean.getHachuIfFgString());
		sb.append(",");
		sb.append( bean.getHachuIfHaisinFgString());
		sb.append(",");
		sb.append( bean.getNohinIfFgString());
		sb.append(",");
		sb.append( bean.getJuryoIfFgString());
		sb.append(",");
		sb.append( bean.getSiharaiIfFgString());
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 start
		sb.append( bean.getHonStartDtString());
		sb.append(",");
		sb.append( bean.getHonEndDtString());
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 end
// 2005/03/02 add takata begin
		sb.append( bean.getSeisenWebEdiTaisyoFgString());
		sb.append(",");
		sb.append( bean.getSeisenHonStartDtString());
		sb.append(",");
		sb.append( bean.getSeisenHonEndDtString());
		sb.append(",");
		sb.append( bean.getGroceryWebEdiTaisyoFgString());
		sb.append(",");
		sb.append( bean.getGroceryHonStartDtString());
		sb.append(",");
		sb.append( bean.getGroceryHonEndDtString());
		sb.append(",");
		sb.append( bean.getIryoWebEdiTaisyoFgString());
		sb.append(",");
		sb.append( bean.getIryoHonStartDtString());
		sb.append(",");
		sb.append( bean.getIryoHonEndDtString());
		sb.append(",");
		sb.append( bean.getJuseikatuWebEdiTaisyoFgString());
		sb.append(",");
		sb.append( bean.getJuseikatuHonStartDtString());
		sb.append(",");
		sb.append( bean.getJuseikatuHonEndDtString());
		sb.append(",");
// 2005/03/02 add takata end
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
		RTorihikisakiBean bean = (RTorihikisakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_Torihikisaki set ");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		sb.append(",");
		sb.append(" torihikisaki_na = ");
		sb.append( bean.getTorihikisakiNaString());
		sb.append(",");
		sb.append(" torihikisaki_ka = ");
		sb.append( bean.getTorihikisakiKaString());
		sb.append(",");
		sb.append(" tel_nb = ");
		sb.append( bean.getTelNbString());
		sb.append(",");
		sb.append(" torihikisaki_kb = ");
		sb.append( bean.getTorihikisakiKbString());
		sb.append(",");
		sb.append(" hachuyo_if_fg = ");
		sb.append( bean.getHachuyoIfFgString());
		sb.append(",");
		sb.append(" hachu_if_fg = ");
		sb.append( bean.getHachuIfFgString());
		sb.append(",");
		sb.append(" hachu_if_haisin_fg = ");
		sb.append( bean.getHachuIfHaisinFgString());
		sb.append(",");
		sb.append(" nohin_if_fg = ");
		sb.append( bean.getNohinIfFgString());
		sb.append(",");
		sb.append(" juryo_if_fg = ");
		sb.append( bean.getJuryoIfFgString());
		sb.append(",");
		sb.append(" siharai_if_fg = ");
		sb.append( bean.getSiharaiIfFgString());
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 start
		sb.append(" hon_start_dt = ");
		sb.append( bean.getHonStartDtString());
		sb.append(",");
		sb.append(" hon_end_dt = ");
		sb.append( bean.getHonEndDtString());
		sb.append(",");
//2004.02.21 @ADD yamanaka 変更№70対応 end
// 2005/03/02 add takata begin
		sb.append(" seisen_web_edi_taisyo_fg = ");
		sb.append( bean.getSeisenWebEdiTaisyoFgString());
		sb.append(",");
		sb.append(" seisen_hon_start_dt = ");
		sb.append( bean.getSeisenHonStartDtString());
		sb.append(",");
		sb.append(" seisen_hon_end_dt = ");
		sb.append( bean.getSeisenHonEndDtString());
		sb.append(",");
		sb.append(" grocery_web_edi_taisyo_fg = ");
		sb.append( bean.getGroceryWebEdiTaisyoFgString());
		sb.append(",");
		sb.append(" grocery_hon_start_dt = ");
		sb.append( bean.getGroceryHonStartDtString());
		sb.append(",");
		sb.append(" grocery_hon_end_dt = ");
		sb.append( bean.getGroceryHonEndDtString());
		sb.append(",");
		sb.append(" iryo_web_edi_taisyo_fg = ");
		sb.append( bean.getIryoWebEdiTaisyoFgString());
		sb.append(",");
		sb.append(" iryo_hon_start_dt = ");
		sb.append( bean.getIryoHonStartDtString());
		sb.append(",");
		sb.append(" iryo_hon_end_dt = ");
		sb.append( bean.getIryoHonEndDtString());
		sb.append(",");
		sb.append(" juseikatu_web_edi_taisyo_fg = ");
		sb.append( bean.getJuseikatuWebEdiTaisyoFgString());
		sb.append(",");
		sb.append(" juseikatu_hon_start_dt = ");
		sb.append( bean.getJuseikatuHonStartDtString());
		sb.append(",");
		sb.append(" juseikatu_hon_end_dt = ");
		sb.append( bean.getJuseikatuHonEndDtString());
		sb.append(",");
// 2005/03/02 add takata end
		sb.append(" insert_ts = ");
		sb.append( bean.getInsertTsString());
		sb.append(",");
		sb.append(" update_ts = ");
		sb.append( bean.getUpdateTsString());
		sb.append(" where");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
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
		RTorihikisakiBean bean = (RTorihikisakiBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_Torihikisaki where ");
		sb.append(" torihikisaki_cd = ");
		sb.append( bean.getTorihikisakiCdString());
		return sb.toString();
	}

}
