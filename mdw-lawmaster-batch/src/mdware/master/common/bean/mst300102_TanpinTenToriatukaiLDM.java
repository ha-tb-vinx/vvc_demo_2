/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
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
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst300102_TanpinTenToriatukaiLDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
//  ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
	private ArrayList tenpoItiran = null;// 店舗配列
	private ArrayList syohinItiran = null;// 部門、商品コード
//	private String hanku_cd = null;	// 販区コード
	private String bumon_cd = null;	// 部門コード
	private String syohin_cd = null;	// 商品コード
//  ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

	/**
	 * コンストラクタ
	 */
	public mst300102_TanpinTenToriatukaiLDM()
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
		mst300102_TanpinTenToriatukaiLBean bean = new mst300102_TanpinTenToriatukaiLBean();
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setTanaNoNb( rest.getLong("tana_no_nb") );
		bean.setSiireHinbanCd( rest.getString("siire_hinban_cd") );
		bean.setTenHachuStDt( rest.getString("ten_hachu_st_dt") );
		bean.setTenHachuEdDt( rest.getString("ten_hachu_ed_dt") );
		bean.setEosKb( rest.getString("eos_kb") );
		bean.setHachutaniIrisuQt( rest.getLong("hachutani_irisu_qt") );
		bean.setSizeCd( rest.getString("size_cd") );
		bean.setColorCd( rest.getString("color_cd") );
		bean.setKikakuKanaNa( rest.getString("kikaku_kana_na") );
		bean.setKikakuKana2Na( rest.getString("kikaku_kana_2_na") );
		bean.setGentankaVl( rest.getDouble("gentanka_vl") );
		bean.setBaitankaVl( rest.getLong("baitanka_vl") );
		bean.setUnitCd( rest.getString("unit_cd") );
		bean.setSiiresakiCd( rest.getString("siiresaki_cd") );
		bean.setSiiresakiNa( rest.getString("siiresaki_na") );
		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
		bean.setHinsyuNa( rest.getString("hinsyu_na") );
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
		StringBuffer sb = new StringBuffer();

//      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
////		StringBuffer sb2 = new StringBuffer();
//// 		sb.append(" SELECT ");
//		sb.append(" SELECT  DISTINCT");
////		sb.append("   shn.HANKU_CD as HANKU_CD, ");
////      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
//		sb.append("   shn.BUMON_CD as BUMON_CD, ");
//		sb.append("   shn.SYOHIN_CD as SYOHIN_CD, ");
////      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
//// 		sb.append("   shn.HINMEI_KANJI_NA as HINMEI_KANJI_NA,  ");
//		sb.append("   shn.HINMEI_KANJI_NA as HINMEI_KANJI_NA ");
//
//		****************/
//
//		/***************************************************
//		if(tenpoItiran.size() > 0){
//			sb.append(" , ");
//		}
//		// +++++++++++++++ 店舗情報生成 +++++++++++++++
//		// 店舗コード
//		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
//			if (intCnt != tenpoItiran.size() - 1) {
//				sb.append(" '" + tenpoItiran.get(intCnt) + "' ||', '|| ");
//			} else {
//				sb.append(" '" + tenpoItiran.get(intCnt) + "'  as tenpo_cdl, ");
//			}
//		}
//		// 店舗名
//		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
//			if (intCnt != tenpoItiran.size() - 1) {
//				sb.append(" (select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "') ||','|| ");
//			} else {
//				sb.append(" (select TRIM(KANJI_RN) from R_TENPO where TENPO_CD = '" + tenpoItiran.get(intCnt) + "')  as tkanji_rnl, ");
//			}
//		}
//		// フラグ
//		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
//			if (intCnt != tenpoItiran.size() - 1) {
////		      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
////				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' as col from R_TANPINTEN_TORIATUKAI t1 where t1.HANKU_CD = shn.HANKU_CD and " +
//				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' as col from R_TANPINTEN_TORIATUKAI t1 where t1.BUMON_CD = shn.BUMON_CD and " +
////		      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
//														  "t1.SYOHIN_CD = shn.SYOHIN_CD and " +
//										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
//										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
//										  "'),'0')||','|| ");
//			} else {
////		      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
////				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' as col from R_TANPINTEN_TORIATUKAI t1 where t1.HANKU_CD = shn.HANKU_CD and " +
//				sb.append("nvl((select '" + mst000101_ConstDictionary.RECORD_EXISTENCE + "' as col from R_TANPINTEN_TORIATUKAI t1 where t1.BUMON_CD = shn.BUMON_CD and " +
////		      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
//														  "t1.SYOHIN_CD = shn.SYOHIN_CD and " +
//										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
//										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
//										  "'),'0') as flgl, ");
//			}
//		}
//		// 更新年月日
//		for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
//			if (intCnt != tenpoItiran.size() - 1) {
////		      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
////				sb.append("nvl((select UPDATE_TS from R_TANPINTEN_TORIATUKAI t1 where t1.HANKU_CD = shn.HANKU_CD and " +
//				sb.append("nvl((select UPDATE_TS from R_TANPINTEN_TORIATUKAI t1 where t1.BUMON_CD = shn.BUMON_CD and " +
////		      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
//														  "t1.SYOHIN_CD = shn.SYOHIN_CD and " +
//										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
//										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
//										  "'),'@@@@@@@@')||','|| ");
//			} else {
////		      ↓↓2006.06.16 fanglh カスタマイズ修正↓↓
////				sb.append("nvl((select UPDATE_TS from R_TANPINTEN_TORIATUKAI t1 where t1.HANKU = shn.HANKU_CD and " +
//				sb.append("nvl((select UPDATE_TS from R_TANPINTEN_TORIATUKAI t1 where t1.BUMON_CD = shn.BUMON_CD and " +
////		      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
//														  "t1.SYOHIN_CD = shn.SYOHIN_CD and " +
//										  "t1.TENPO_CD = '" + tenpoItiran.get(intCnt) + "' and " +
//										  "t1.delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() +
//										  "'),'@@@@@@@@') as update_tsl ");
//			}
//		}

	//	sb.append(" FROM ");
//		sb.append("  (SELECT ");
		sb.append("  SELECT DISTINCT");
//		sb.append("    HANKU_CD, ");
//		sb.append("    SYOHIN_CD, ");
//		sb.append("    HINMEI_KANJI_NA, ");
//		sb.append("    DELETE_FG ");
//		sb.append("   FROM R_SYOHIN RS");

		sb.append("    rs.BUMON_CD, ");
		sb.append("    rs.SYOHIN_CD, ");
		sb.append("    rs.HINMEI_KANJI_NA, ");
		//sb.append("    rs.DELETE_FG, ");
		sb.append("    rs.tana_no_nb, ");
		sb.append("    rs.siire_hinban_cd, ");
		sb.append("    rs.ten_hachu_st_dt, ");
		sb.append("    rs.ten_hachu_ed_dt, ");
		sb.append("    rs.eos_kb, ");
		sb.append("    rs.hachutani_irisu_qt, ");
		sb.append("    rs.size_cd, ");
		sb.append("    rs.color_cd, ");
		sb.append("    rs.kikaku_kana_na, ");
		sb.append("    rs.kikaku_kana_2_na, ");
		sb.append("    rs.gentanka_vl, ");
		sb.append("    rs.baitanka_vl, ");
		sb.append("    rs.unit_cd, ");
		sb.append("    rs.siiresaki_cd, ");
		sb.append("    rsi.kanji_rn as siiresaki_na, ");
		sb.append("    rst.hinsyu_cd, ");
		sb.append("    rn.kanji_rn as hinsyu_na ");

		sb.append("   FROM R_SYOHIN_TAIKEI RST LEFT OUTER JOIN R_NAMECTF RN ON RN.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' AND rst.hinsyu_cd = rn.code_cd, ");
		if("G".equals(map.get("systemKb")) &&(map.get("tanawari_bangou_fm")!=null && map.get("tanawari_bangou_fm").toString().trim().length()!=0
				|| map.get("tanawari_bangou_to")!=null && map.get("tanawari_bangou_to").toString().trim().length()!=0)){
			sb.append("   R_TANA_LOCATION RTL,");
		}
		sb.append("   	     R_SYOHIN RS LEFT OUTER JOIN R_SIIRESAKI RSI ON RS.SIIRESAKI_CD = RSI.SIIRESAKI_CD LEFT OUTER JOIN R_TANPINTEN_TORIATUKAI RTT  ON");
		sb.append("         rtt.bumon_cd = rs.bumon_cd "+"AND ");
		sb.append("         rtt.syohin_cd = rs.syohin_cd "+"AND ");
		sb.append("         rtt.delete_fg = '0' ");
		sb.append("   WHERE");
// 2005.11.07 処理区分により条件分岐を行う

// 		if(map.get("hanku_cd")!=null && map.get("hanku_cd").toString().trim().length()!=0){
// 			sb.append("     HANKU_CD = '"+ map.get("hanku_cd") +"' AND ");
// 		}
// 		sb.append("     SYOHIN_CD LIKE '"+ map.get("syohin_cd") +"%' ");
// 		sb.append("     AND DELETE_FG = '0'");
//    	      ↓↓　移植(2006.05.31) ↓↓
// 		sb.append("     AND YUKO_DT = MSP710101_GETSYOHINYUKODT(HANKU_CD, SYOHIN_CD,'') ");
//    	      ↑↑　移植(2006.05.31) ↑↑Keepb.getHankuCd()
		sb.append("     rst.bumon_cd = '"+ StringUtility.charFormat(map.get("bumon_cd").toString(),4,"0",true) +"' AND ");
//------管理メニューから来た場合、デイリーを除く処理追加 by kema 06.09.05
		if(map.get("systemKb")!=null&& map.get("systemKb").toString().trim().length()!=0){
			sb.append("     rs.system_kb = '"+ map.get("systemKb") +"' AND ");
		}else{
			sb.append("     rs.system_kb != '"+ mst000611_SystemKbDictionary.F.getCode() +"' AND ");
		}
		if(map.get("hinban_cd")!=null && map.get("hinban_cd").toString().trim().length()!=0){
			sb.append("     rst.hinban_cd = '"+ StringUtility.charFormat(map.get("hinban_cd").toString(),4,"0",true) +"' AND ");
		}
		if(map.get("hinsyu_cd")!=null && map.get("hinsyu_cd").toString().trim().length()!=0){
			sb.append("     rst.hinsyu_cd = '"+ StringUtility.charFormat(map.get("hinsyu_cd").toString(),4,"0",true) +"' AND ");
		}
		if(map.get("line_cd")!=null && map.get("line_cd").toString().trim().length()!=0){
			sb.append("     rst.line_cd = '"+ map.get("line_cd") +"' AND ");
		}
		if(map.get("unit_cd")!=null && map.get("unit_cd").toString().trim().length()!=0){
			sb.append("     rst.unit_cd = '"+ map.get("unit_cd") +"' AND ");
		}

		sb.append("    rst.bumon_cd = rs.bumon_cd "+"AND ");
		sb.append("    rst.unit_cd = rs.unit_cd "+"AND ");

		if(map.get("siiresaki_cd")!=null && map.get("siiresaki_cd").toString().trim().length()!=0){
			sb.append("     rs.siiresaki_cd = '"+ map.get("siiresaki_cd") +"' AND ");
		}
		if(map.get("siiresaki_syohin_cd")!=null && map.get("siiresaki_syohin_cd").toString().trim().length()!=0){
			sb.append("     rs.SIIRE_HINBAN_CD = '"+ map.get("siiresaki_syohin_cd") +"' AND ");
		}
		if(map.get("by_no")!=null && map.get("by_no").toString().trim().length()!=0){
			sb.append("     rs.syokai_user_id = '"+ StringUtility.charFormat(map.get("by_no").toString(),10,"0",true) +"' AND ");
		}
		sb.append("     rs.delete_fg = '0'" +"AND ");

		if(map.get("baitanka_vl_fm")!=null && map.get("baitanka_vl_fm").toString().trim().length()!=0){
			sb.append("     rs.baitanka_vl >= "+ mst000401_LogicBean.removeComma(map.get("baitanka_vl_fm").toString()) +" AND ");
		}
		if(map.get("baitanka_vl_to")!=null && map.get("baitanka_vl_to").toString().trim().length()!=0){
			sb.append("     rs.baitanka_vl <= "+ mst000401_LogicBean.removeComma(map.get("baitanka_vl_to").toString()) +" AND ");
		}
		if("J".equals(map.get("systemKb"))){
			if(map.get("tanawari_bangou_fm")!=null && map.get("tanawari_bangou_fm").toString().trim().length()!=0){
				sb.append("     rs.tana_no_nb >= "+ mst000401_LogicBean.removeComma(map.get("tanawari_bangou_fm").toString()) +" AND ");
				}
			if(map.get("tanawari_bangou_to")!=null && map.get("tanawari_bangou_to").toString().trim().length()!=0){
					sb.append("     rs.tana_no_nb <= "+ mst000401_LogicBean.removeComma(map.get("tanawari_bangou_to").toString()) +" AND ");
				}
		}else if("G".equals(map.get("systemKb")) &&(map.get("tanawari_bangou_fm")!=null && map.get("tanawari_bangou_fm").toString().trim().length()!=0
				|| map.get("tanawari_bangou_to")!=null && map.get("tanawari_bangou_to").toString().trim().length()!=0)){
//			↓↓2006.09.21 H.Yamamoto カスタマイズ修正↓↓
//			sb.append("    rtl.bumon_cd = rs.bumon_cd "+"AND ");
//			sb.append("    rtl.keikaku_syohin_cd = rs.syohin_cd "+"AND ");
			sb.append("    rtl.syohin_cd = rs.syohin_cd "+"AND ");
//			↑↑2006.09.21 H.Yamamoto カスタマイズ修正↑↑
			if(map.get("tanawari_bangou_fm")!=null && map.get("tanawari_bangou_fm").toString().trim().length()!=0){
				sb.append("     rtl.tanadai_nb >= '"+ map.get("tanawari_bangou_fm") +"' AND ");
				}
			if(map.get("tanawari_bangou_to")!=null && map.get("tanawari_bangou_to").toString().trim().length()!=0){
					sb.append("     rtl.tanadai_nb <= '"+ map.get("tanawari_bangou_to") +"' AND ");
				}

		}
		if(map.get("syohin_cd")!=null && map.get("syohin_cd").toString().trim().length()!=0){
			sb.append("     rs.syohin_cd = '"+ map.get("syohin_cd") +"' AND ");
		}
			sb.append("   rs.YUKO_DT = MSP710101_GETSYOHINYUKODT(rs.BUMON_CD, rs.SYOHIN_CD,cast(null as char)) ");
//      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
		if(!map.get("ct_syorikb").toString().equals(mst000101_ConstDictionary.PROCESS_INSERT) &&
			!map.get("ct_syorikb").toString().equals(mst000101_ConstDictionary.PROCESS_UPDATE)){
			sb.append("  AND EXISTS ");
			sb.append("   (SELECT BUMON_CD,SYOHIN_CD ");
//			sb.append("   (SELECT HANKU_CD,SYOHIN_CD ");
			sb.append("    FROM R_TANPINTEN_TORIATUKAI RT ");
			sb.append("    WHERE RT.BUMON_CD=RS.BUMON_CD ");
//			sb.append("    WHERE RT.HANKU_CD=RS.HANKU_CD ");
			sb.append("    AND RT.SYOHIN_CD=RS.SYOHIN_CD ");
			sb.append("    AND RT.DELETE_FG='0'");
			sb.append("    AND RT.TENPO_CD IN( ");
			for(int intCnt = 0;intCnt < tenpoItiran.size();intCnt++){
				if(intCnt == 0){
					sb.append("'"+tenpoItiran.get(intCnt)+"'");
				}else{
					sb.append(", '"+tenpoItiran.get(intCnt)+"'");
				}
			}
			sb.append("    ))  ");
		}
		sb.append("   ORDER BY ");
		sb.append("     BUMON_CD,SYOHIN_CD ");

//		sb.append("     HANKU_CD,SYOHIN_CD ) shn ");
//      ↑↑2006.06.16 fanglh カスタマイズ修正↑↑
// 2005.11.07 処理区分により条件分岐を行う
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
	 * 部門コード、商品コード取得配列に対するセッター
	 * @param bumon_cd
	 * @return
	 */
	public void setSyohinItiran(ArrayList list){
		syohinItiran = list;
	}

	/**
	 * 部門コード、商品コード取得配列に対するゲッター
	 * @param bumon_cd
	 * @return
	 */
	public ArrayList getSyohinItiran(){
		return syohinItiran;
	}

//  ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
//	/**
//	 * 販区コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setHankuCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setHankuCd(String hanku_cd)
//		{
//			this.hanku_cd = hanku_cd;
//			return true;
//		}
//	/**
//	 * 販区コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getHankuCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getHankuCd()
//		{
//			return this.hanku_cd;
//		}
//


	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setBumonCd(String bumon_cd)
		{
			this.bumon_cd = bumon_cd;
			return true;
		}
	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getBumonCd()
		{
			return this.bumon_cd;
		}
//	     ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

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

//	↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
	public String getSiiresakiKanriCd(mst000101_DbmsBean db, String bumonCd, String syohinCd, String yukoDt) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		String tenSiiresakiKanriCd = "";

		sql.append("SELECT ");
		sql.append("	 rs.ten_siiresaki_kanri_cd as ten_siiresaki_kanri_cd ");
		sql.append("FROM ");
		sql.append("	 r_syohin rs ");
		sql.append("WHERE ");
		sql.append("	 rs.bumon_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.syohin_cd = '").append(syohinCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bumon_cd, rs.syohin_cd, '").append(yukoDt).append("') ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			if (rs.getString("ten_siiresaki_kanri_cd") != null) {
				tenSiiresakiKanriCd = rs.getString("ten_siiresaki_kanri_cd").trim();
			}
			rs.close();
		}

		return tenSiiresakiKanriCd;

	}

	public boolean isSiiresakiTenpo(mst000101_DbmsBean db, String systemKb, String bumonCd, String tenSiiresakiKanriCd, String tenpoCd) throws SQLException {

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		if (!systemKb.equals(mst000611_SystemKbDictionary.G.getCode()) && !systemKb.equals(mst000611_SystemKbDictionary.F.getCode())) {
			return true;
		}

		if (tenSiiresakiKanriCd.equals("9999")) {
			return true;
		}

		if (tenSiiresakiKanriCd.equals("")) {
			return false;
		}

		sql.append("SELECT ");
		sql.append("	 rts.tenpo_cd as tenpo_cd ");
		sql.append("FROM ");
		sql.append("	 r_tenbetu_siiresaki rts ");
		sql.append("WHERE ");
		sql.append("	 rts.kanri_kb = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.kanri_cd = '").append(StringUtility.charFormat(bumonCd,4,"0",true)).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.ten_siiresaki_kanri_cd = '").append(tenSiiresakiKanriCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.tenpo_cd = '").append(tenpoCd).append("' ");
		sql.append("	AND ");
		sql.append("	 rts.delete_fg = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		rs = db.executeQuery(sql.toString());
		if (rs.next()) {
			rs.close();
			return true;
		} else {
			return false;
		}

	}
//	↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
}
