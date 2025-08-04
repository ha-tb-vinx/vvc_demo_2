package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（posー管理）POS売価変更指示（基準売価）検索画面のDMクラス</p>
 * <p>説明: POS売価変更指示（基準売価）検索画面のDMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 
 * @version 1.0(2006/03/27)初版作成
 
 */
public class mstA10101_BaihenDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mstA10101_BaihenDM()
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
		mstA10101_BaihenBean bean = new mstA10101_BaihenBean();
		bean.setSakuseiDt(rest.getString("sakusei_dt"));
		bean.setTenpoCd(rest.getString("tenpo_cd"));
		bean.setSgyosyuCd(rest.getString("s_gyosyu_cd"));
		bean.setUrikuCd(rest.getString("uriku_cd"));
		bean.setTenhankuCd(rest.getString("tenhanku_cd"));
		bean.setHankuCd(rest.getString("hanku_cd"));
		bean.setSyohinCd(rest.getString("syohin_cd"));
		bean.setYukoDt(rest.getString("yuko_dt"));
		bean.setHinmeiKanjiNa(rest.getString("hinmei_kanji_na"));
		bean.setKikakuKanjiNa(rest.getString("kikaku_kanji_na"));
		bean.setPosfulHinbanCd(rest.getString("posful_hinban_cd"));
		bean.setPosCd(rest.getString("pos_cd"));
		bean.setBaitankaVl(rest.getLong("baitanka_vl"));
		bean.setOldBaitankaVl(rest.getLong("old_baitanka_vl"));
		bean.setHaneiDt(rest.getString("hanei_dt"));
		bean.setHachuHaneiDt(rest.getString("hachu_hanei_dt"));
		bean.setSiiresakiCd(rest.getString("siiresaki_cd"));
		bean.setSiiresakiNa(rest.getString("siiresaki_na"));
		bean.setInsertTs(rest.getString("insert_ts"));
		bean.setInsertUserId(rest.getString("insert_user_id"));
		bean.setUpdateTs(rest.getString("update_ts"));
		bean.setUpdateUserId(rest.getString("update_user_id"));
		bean.setDeleteFg(rest.getString("delete_fg"));
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
	//--------------------
	//SQLの作成
	//--------------------
		StringBuffer sb = new StringBuffer();
		sb.append("select  ");
		sb.append("	sakusei_dt,");
		sb.append("	tenpo_cd,");
		sb.append("	s_gyosyu_cd,");
		sb.append("	uriku_cd,");
		sb.append("	tenhanku_cd,");
		sb.append("	hanku_cd,");
		sb.append("	syohin_cd,");
		sb.append("	yuko_dt,");
		sb.append("	hinmei_kanji_na,");
		sb.append("	kikaku_kanji_na,");
		sb.append("	posful_hinban_cd,");
		sb.append("	pos_cd,");
		sb.append("	baitanka_vl,");
		
		sb.append("	old_baitanka_vl,");
		sb.append("	hanei_dt,");
		sb.append("	hachu_hanei_dt,");
		sb.append("	siiresaki_cd,");
		sb.append("	siiresaki_na,");
		sb.append("	insert_ts,");
		sb.append("	insert_user_id,");
		sb.append("	update_ts,");
		sb.append("	update_user_id,");
		sb.append("	delete_fg	");
		sb.append("	from R_POS_BAIHEN	");
			  
		sb.append("	where	");
		// ↓↓　2006/04/12 kim START
		// キー値変更
		if (map.get("ct_tenpoCd") != null && ((String)map.get("ct_tenpoCd")).trim().length() > 0) {
			sb.append("	tenpo_cd = '" + conv.convertWhereString( (String)map.get("ct_tenpoCd") ) + "' ");
		}
		if (map.get("ct_urikuCd") != null && ((String)map.get("ct_urikuCd")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	uriku_cd = '" + conv.convertWhereString( (String)map.get("ct_urikuCd") ) + "' ");
		}
		if (map.get("ct_sakuseiDt") != null && ((String)map.get("ct_sakuseiDt")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	sakusei_dt = '" + conv.convertWhereString( (String)map.get("ct_sakuseiDt") ) + "' ");
		}
		// ↑↑　2006/04/12 kim END
		if (map.get("tenhanku_cd") != null && ((String)map.get("tenhanku_cd")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	tenhanku_cd = '" + conv.convertWhereString( (String)map.get("tenhanku_cd") ) + "' ");
		}
		// 2006/04/17 kim START MOD
		//	キー名変更
		if (map.get("jan_cd") != null && ((String)map.get("jan_cd")).trim().length()	>0	)	{
			sb.append("	and	");
			sb.append("	pos_cd=	'"+	conv.convertWhereString((String)map.get("jan_cd")) +"' ");
		}
		// 2006/04/17 kim END MOD
		if (map.get("posful_hinban_cd") != null && ((String)map.get("posful_hinban_cd")).trim().length() > 0) {
			sb.append("  and ");
			sb.append("	posful_hinban_cd = '" + conv.convertWhereString( (String)map.get("posful_hinban_cd") ) + "' ");
		}
		
		sb.append(" order by hanei_dt DESC, ");
		sb.append("	hanku_cd ASC ,");
		sb.append("	pos_cd ASC	"); 
		System.out.println("mstA10101_BaihenDM sql "+sb.toString());
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
	 * 指定したマックス桁数が足らない場合は、前ゼロ埋めして返す
	 * <br>
	 * Ex)<br>
	 * mst000401_LogicBean.formatZero("123","5") -&gt; "00123"<br>
	 * <br>
	 * 引数strOldDataがnullの場合の戻り値は、長さ0の文字列になります。
	 * 引数strOldDataが長さ0の文字列の場合の戻り値は、引数strOldDataの値をそのまま返します。
	 * @param strOldData フォーマット元の文字列
	 * @param iMaxLength フォーマットサイズ
	 * @return	String 前ゼロ埋めした結果
	 */	
	public static String numSet( String strData,int iMaxLength, String addData ) {

		if (null == strData){
			return "";
		}
		strData = strData.trim();
		int iLength = strData.length();    //Baseのレングス
		if (0 != iLength){
			// 桁数足りない場合は、０埋め
			for (int i = 0; i< (iMaxLength - iLength); i++){
				strData = strData.concat( addData );
			}
		}
		return strData;
	}
//	↑↑仕様変更（2005.10.08）↑↑
}
