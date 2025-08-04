/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会画面表示データDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する値札タグ発行情報照会画面表示データDMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0(2006/12/25)初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会画面表示データDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する値札タグ発行情報照会画面表示データDMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstB40101_NefudaTagHakkoInfoDM extends DataModule
{
	/**
	 * コンストラクタ
	 */
	public mstB40101_NefudaTagHakkoInfoDM()
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
		mstB40101_NefudaTagHakkoInfoBean bean = new mstB40101_NefudaTagHakkoInfoBean();
		bean.setHinbanCd(encodingString(mst000401_LogicBean.chkNullString( rest.getString("HINBAN_CD") )));
		bean.setUnitCd(encodingString(mst000401_LogicBean.chkNullString( rest.getString("UNIT_CD") )));
		bean.setSiireHinbanCd(encodingString(mst000401_LogicBean.chkNullString( rest.getString("SIIRE_HINBAN_CD") )));
		bean.setSyohinCd(encodingString(mst000401_LogicBean.chkNullString( rest.getString("SYOHIN_CD") )));
		bean.setSyohinNm(encodingString(mst000401_LogicBean.chkNullString( rest.getString("HINMEI_KANJI_NA") )));
		bean.setColorNa(encodingString(mst000401_LogicBean.chkNullString( rest.getString("COLOR_NA") )));
		bean.setSizeNa(encodingString(mst000401_LogicBean.chkNullString( rest.getString("SIZE_NA") )));
		bean.setBaitankaVl(encodingString(mst000401_LogicBean.chkNullString( rest.getString("BAITANKA_VL") )));
		bean.setKeshiBaikaVl(encodingString(mst000401_LogicBean.chkNullString( rest.getString("KESHI_BAIKA_VL") )));
		bean.setSyohinKbNm(encodingString(mst000401_LogicBean.chkNullString( rest.getString("SYOHIN_KB_NM") )));
		bean.setNefudaKbNm(encodingString(mst000401_LogicBean.chkNullString( rest.getString("NEFUDA_KB_NM") )));
		bean.setRuikeiKeiyakuQt(encodingString(mst000401_LogicBean.chkNullString( rest.getString("RUIKEI_KEIYAKU_QT") )));
		bean.setKonkaiTuikeiyakuQt(encodingString(mst000401_LogicBean.chkNullString( rest.getString("KONKAI_TUIKEIYAKU_QT") )));
		bean.setHanbaiStDt(encodingString(mst000401_LogicBean.chkNullString( rest.getString("HANBAI_ST_DT") )));
		bean.setHanbaiEnDt(encodingString(mst000401_LogicBean.chkNullString( rest.getString("HANBAI_ED_DT") )));
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
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("S.HINBAN_CD as HINBAN_CD ");
		sb.append(", ");
		sb.append("S.UNIT_CD as UNIT_CD ");
		sb.append(", ");
		sb.append("coalesce(S.SIIRE_HINBAN_CD,'') as SIIRE_HINBAN_CD ");
		sb.append(", ");
		sb.append("S.SYOHIN_CD as SYOHIN_CD ");
		sb.append(", ");
		sb.append("S.HINMEI_KANJI_NA AS HINMEI_KANJI_NA ");
		if (map.get("system_kb") != null && map.get("system_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			//タグ衣料の場合は、名称・CTFマスタよりカラー名称、サイズ名称を取得
			sb.append(", ");
			sb.append("H.KANJI_RN AS COLOR_NA ");	//カラー名称(漢字略称)
			sb.append(", ");
			sb.append("I.KANJI_RN AS SIZE_NA ");	//サイズ名称(漢字略称)
		}else if ( map.get("system_kb") != null && map.get("system_kb").equals("J") ){
			//実用衣料の場合は、商品マスタよりカラー名称、サイズ名称を取得
			sb.append(", ");
			sb.append("S.COLOR_NA AS COLOR_NA ");	//カラー名称(漢字規格)
			sb.append(", ");
			sb.append("S.SIZE_NA AS SIZE_NA ");	//サイズ名称(漢字規格２)
		}else {
			//衣料以外
			sb.append(", ");
			sb.append("'' AS COLOR_NA ");	//カラー名称(漢字規格)
			sb.append(", ");
			sb.append("'' AS SIZE_NA ");	//サイズ名称(漢字規格２)
		}
		sb.append(", ");
		sb.append("S.BAITANKA_VL AS BAITANKA_VL ");
		sb.append(", ");
		sb.append("S.KESHI_BAIKA_VL AS KESHI_BAIKA_VL ");
		sb.append(", ");
		sb.append("E.KANJI_RN as SYOHIN_KB_NM ");
		sb.append(", ");
		sb.append("F.KANJI_RN as NEFUDA_KB_NM ");
//		sb.append(", ");
//		sb.append("G.KANJI_RN as SIIRESAKI_NM ");
		sb.append(", ");
		sb.append("'' as SIIRESAKI_NM ");
		sb.append(", ");
		sb.append("C.RUIKEI_KEIYAKU_QT as RUIKEI_KEIYAKU_QT ");
		sb.append(", ");
		sb.append("C.KONKAI_TUIKEIYAKU_QT as KONKAI_TUIKEIYAKU_QT ");
		sb.append(", ");
		sb.append("S.HANBAI_ST_DT as HANBAI_ST_DT ");
		sb.append(", ");
		sb.append("S.HANBAI_ED_DT as HANBAI_ED_DT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT ");
		sb.append("B.SYSTEM_KB as SYSTEM_KB ");
		sb.append(", ");
		sb.append("B.BUMON_CD as BUMON_CD ");
		sb.append(", ");
		sb.append("B.HINBAN_CD as HINBAN_CD ");
		sb.append(", ");
		sb.append("B.UNIT_CD as UNIT_CD ");
		sb.append(", ");
		sb.append("B.SIIRESAKI_CD as SIIRESAKI_CD ");
		sb.append(", ");
		sb.append("B.SIIRE_HINBAN_CD as SIIRE_HINBAN_CD ");
		sb.append(", ");
		sb.append("B.SYOHIN_CD as SYOHIN_CD ");
		sb.append(", ");
		sb.append("B.HINMEI_KANJI_NA AS HINMEI_KANJI_NA ");
		sb.append(", ");
		sb.append("B.COLOR_CD AS COLOR_CD ");
		sb.append(", ");
		sb.append("B.SIZE_CD AS SIZE_CD ");
		sb.append(", ");
		sb.append("B.KIKAKU_KANJI_NA AS COLOR_NA ");
		sb.append(", ");
		sb.append("B.KIKAKU_KANJI_2_NA AS SIZE_NA ");
		sb.append(", ");
		sb.append("B.BAITANKA_VL AS BAITANKA_VL ");
		sb.append(", ");
		sb.append("B.KESHI_BAIKA_VL AS KESHI_BAIKA_VL ");
		sb.append(", ");
		sb.append("B.FUJI_SYOHIN_KB  as FUJI_SYOHIN_KB ");
		sb.append(", ");
		sb.append("B.NEFUDA_KB  as NEFUDA_KB ");
		sb.append(", ");
		sb.append("B.HANBAI_ST_DT as HANBAI_ST_DT ");
		sb.append(", ");
		sb.append("B.HANBAI_ED_DT as HANBAI_ED_DT ");
		sb.append("FROM ");
		sb.append("R_SYOHIN_TAIKEI A ");
		sb.append(", ");
		sb.append("R_SYOHIN B ");
		sb.append("WHERE ");
		sb.append("A.SYSTEM_KB = '"+ map.get("system_kb") +"' ");

		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
		    sb.append(" AND ");
		    sb.append("A.bumon_cd = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true))).append("' ");
		}
		//hinban_cd に対するWHERE区
		if( map.get("hinban_cd") != null && ((String)map.get("hinban_cd")).trim().length() > 0 )
		{
		    sb.append(" AND ");
		    sb.append("A.hinban_cd = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinban_cd") , 4, "0", true))).append("' ");
		}
		//hinsyu_cd に対するWHERE区
		if( map.get("hinsyu_cd") != null && ((String)map.get("hinsyu_cd")).trim().length() > 0 )
		{
		    sb.append(" AND ");
		    sb.append("A.hinsyu_cd = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("hinsyu_cd") , 4, "0", true))).append("' ");
		}
		//line_cd に対するWHERE区
		if( map.get("line_cd") != null && ((String)map.get("line_cd")).trim().length() > 0 )
		{
		    sb.append(" AND ");
		    sb.append("A.line_cd = '").append(conv.convertWhereString( (String)map.get("line_cd") )).append("' ");
		}
		//unit_cd に対するWHERE区
		if( map.get("unit_cd") != null && ((String)map.get("unit_cd")).trim().length() > 0 )
		{
		    sb.append(" AND ");
		    sb.append("A.unit_cd = '").append(conv.convertWhereString( (String)map.get("unit_cd") )).append("' ");
		}
		sb.append(" AND ");
		sb.append("B.BUMON_CD = A.BUMON_CD ");
		sb.append(" AND ");
		sb.append("B.UNIT_CD = A.UNIT_CD ");
		sb.append(" AND ");
		sb.append("B.YUKO_DT = ");
		sb.append("( ");
		sb.append("SELECT ");
		sb.append("max(B2.YUKO_DT) ");
		sb.append("FROM ");
		sb.append("R_SYOHIN B2 ");
		sb.append("WHERE ");
		sb.append("B2.BUMON_CD = B.BUMON_CD ");
		sb.append(" AND ");
		sb.append("B2.SYOHIN_CD = B.SYOHIN_CD ");
		sb.append(") ");
		sb.append(" AND ");
		sb.append("B.SYSTEM_KB = '"+ map.get("system_kb") +"' ");
		sb.append(" AND ");
		sb.append("B.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		//bumon_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("B.bumon_cd = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("bumon_cd") , 4, "0", true))).append("' ");
		}
		//syohin_cd に対するWHERE区
		if( map.get("syohin_cd") != null && ((String)map.get("syohin_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("B.syohin_cd = '").append(conv.convertWhereString( (String)map.get("syohin_cd") )).append("' ");
		}
		//siiresaki_cd に対するWHERE区
		if( map.get("siiresaki_cd") != null && ((String)map.get("siiresaki_cd")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("SUBSTR(B.siiresaki_cd, 1, 6) = '").append(conv.convertWhereString( (String)map.get("siiresaki_cd") )).append("' ");
		}
		//siiresaki_syohin_cd に対するWHERE区
		if( map.get("siiresaki_syohin_cd") != null && ((String)map.get("siiresaki_syohin_cd")).trim().length() > 0 )
		{
			if( map.get("sscd_flg") != null && ((String)map.get("sscd_flg")).trim().equals("1") )
			{
				String ssCd = ((String)map.get("siiresaki_syohin_cd")).trim();
				sb.append(" AND ");
				sb.append("SUBSTR(B.siire_hinban_cd,1,").append(String.valueOf(ssCd.length())).append(") = '").append(conv.convertWhereString(ssCd)).append("' ");
			} else {
				sb.append(" AND ");
				sb.append("B.siire_hinban_cd = '").append(conv.convertWhereString( (String)map.get("siiresaki_syohin_cd") )).append("' ");
			}
		}
		//sinki_toroku_dt に対するWHERE区
		if( map.get("sinki_toroku_dt") != null && ((String)map.get("sinki_toroku_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("B.sinki_toroku_dt = '").append(conv.convertWhereString( (String)map.get("sinki_toroku_dt") )).append("' ");
		}
		//by_no に対するWHERE区
		if( map.get("by_no") != null && ((String)map.get("by_no")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("B.syokai_user_id = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("by_no") , 10, "0", true))).append("' ");
		}
		//hanbai_en_dt に対するWHERE区
		if (map.get("hanbai_st_dt") != null && ((String)map.get("hanbai_st_dt")).trim().length() > 0 ){
			if (map.get("hanbai_en_dt") != null && ((String)map.get("hanbai_en_dt")).trim().length() > 0){
				sb.append(" AND ");
				sb.append("B.hanbai_st_dt >= '").append(conv.convertWhereString( (String)map.get("hanbai_st_dt") )).append("' ");
				sb.append(" AND ");
				sb.append("B.hanbai_ed_dt <= '").append(conv.convertWhereString( (String)map.get("hanbai_en_dt") )).append("' ");
			} else {
				sb.append(" AND ");
				sb.append("B.hanbai_st_dt = '").append(conv.convertWhereString( (String)map.get("hanbai_st_dt") )).append("' ");
			}
		} else {
			if (map.get("hanbai_en_dt") != null && ((String)map.get("hanbai_en_dt")).trim().length() > 0 ){
				sb.append(" AND ");
				sb.append("B.hanbai_ed_dt = '").append(conv.convertWhereString( (String)map.get("hanbai_en_dt") )).append("' ");
			}
		}
		sb.append(") S ");

		sb.append("inner join ");
		sb.append("R_KEIYAKUSU C ");
		sb.append(" on ");
		sb.append("S.BUMON_CD = C.BUMON_CD ");
		sb.append(" AND ");
		sb.append("S.SYOHIN_CD = C.SYOHIN_CD ");
		sb.append(" AND ");
		sb.append("C.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

		sb.append("left outer join ");
		sb.append("R_NAMECTF E ");
		sb.append(" on ");
		sb.append("E.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.GOODS_DIVISION, userLocal)).append("' ");
		sb.append(" AND ");
		sb.append("E.CODE_CD = S.SYSTEM_KB || S.FUJI_SYOHIN_KB ");
		sb.append(" AND ");
		sb.append("E.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");

		sb.append("left outer join ");
		sb.append("R_NAMECTF F ");
		sb.append(" on ");
		sb.append("F.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.PRICE_TAG_DIVISION, userLocal)).append("' ");
		sb.append(" AND ");
		sb.append("F.CODE_CD = S.SYSTEM_KB || S.NEFUDA_KB ");
		sb.append(" AND ");
		sb.append("F.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//
//		sb.append("left outer join ");
//		sb.append("R_SIIRESAKI G ");
//		sb.append(" on ");
//		sb.append("G.KANRI_KB = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
//		sb.append(" AND ");
//		sb.append("G.KANRI_CD ='0000' ");
//		sb.append(" AND ");
//		sb.append("G.SIIRESAKI_CD = ");
//		sb.append("( ");
//		sb.append("SELECT ");
//		sb.append("max(G2.SIIRESAKI_CD) ");
//		sb.append("FROM ");
//		sb.append("R_SIIRESAKI G2 ");
//		sb.append("WHERE ");
//		sb.append("G2.KANRI_KB = '").append(mst000901_KanriKbDictionary.BUMON.getCode()).append("' ");
//		sb.append(" AND ");
//		sb.append("G2.KANRI_CD ='0000' ");
//		sb.append(" AND ");
//		sb.append("(G2.SIIRESAKI_CD = S.SIIRESAKI_CD ");
//		sb.append(" OR ");
//		sb.append("SUBSTR(G2.SIIRESAKI_CD,1,6) = S.SIIRESAKI_CD) ");
//		sb.append(" AND ");
//		sb.append("G2.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		sb.append(") ");

		if (map.get("system_kb") != null && map.get("system_kb").equals("T")) {				//イキナリ比較すると、NULL時にNullPointerExceptionが発生するため、nullでないことを確認
			// タグ
			sb.append("left outer join ");
			sb.append("R_NAMECTF H ");
			sb.append(" on ");
			sb.append("H.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal)).append("' ");
			sb.append(" AND ");
			sb.append("H.CODE_CD = S.COLOR_CD ");
			sb.append(" AND ");
			sb.append("H.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
			sb.append("left outer join ");
			sb.append("R_NAMECTF I ");
			sb.append(" on ");
			sb.append("I.SYUBETU_NO_CD = '").append(MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal)).append("' ");
			sb.append(" AND ");
			sb.append("I.CODE_CD = S.SIZE_CD ");
			sb.append(" AND ");
			sb.append("I.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		}

		sb.append("WHERE ");

		sb.append("EXISTS ");
		sb.append("( ");
		sb.append("SELECT ");
		sb.append("D.BUMON_CD as BUMON_CD ");
		sb.append(", ");
		sb.append("D.SYOHIN_CD  as SYOHIN_CD ");
		sb.append(", ");
		sb.append("D.HATYU_DT as HATYU_DT ");
		sb.append("FROM ");
		sb.append("R_SYOKAIDONYU D ");

		sb.append("inner join ");
		sb.append("R_SYOHIN J ");
		sb.append(" on ");
		sb.append("J.BUMON_CD = D.BUMON_CD ");
		sb.append(" AND ");
		sb.append("J.SYOHIN_CD = D.SYOHIN_CD ");
		sb.append(" AND ");
		sb.append("J.YUKO_DT = ");
		sb.append("( ");
		sb.append("SELECT ");
		sb.append("max(J2.YUKO_DT) ");
		sb.append("FROM ");
		sb.append("R_SYOHIN J2 ");
		sb.append("WHERE ");
		sb.append("J2.BUMON_CD = J.BUMON_CD ");
		sb.append(" AND ");
		sb.append("J2.SYOHIN_CD = J.SYOHIN_CD ");
		sb.append(" AND ");
		sb.append("J2.YUKO_DT <= D.HATYU_DT ");
		sb.append(") ");
		sb.append(" AND ");
		sb.append("J.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
		//sinki_toroku_dt に対するWHERE区
		if( map.get("sinki_toroku_dt") != null && ((String)map.get("sinki_toroku_dt")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("J.sinki_toroku_dt = '").append(conv.convertWhereString( (String)map.get("sinki_toroku_dt") )).append("' ");
		}
		//by_no に対するWHERE区
		if( map.get("by_no") != null && ((String)map.get("by_no")).trim().length() > 0 )
		{
			sb.append(" AND ");
			sb.append("J.syokai_user_id = '").append(conv.convertWhereString(StringUtility.charFormat( (String)map.get("by_no") , 10, "0", true))).append("' ");
		}

		sb.append("WHERE ");
		sb.append("D.BUMON_CD = S.BUMON_CD ");
		sb.append(" AND ");
		sb.append("D.SYOHIN_CD = S.SYOHIN_CD ");
		sb.append(" AND ");
		sb.append("D.DELETE_FG = '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("' ");
//		↓↓2007.09.05 oohashi 本番に合わせるため修正
        if(map.get("tenpo_l") != null && ((String)map.get("tenpo_l")).trim().length() > 0)
        {
            sb.append(" AND ");
            sb.append("D.TENPO_CD IN (").append(conv.convertWhereString((String)map.get("tenpo_l"))).append(") ");
        }
//		↑↑2007.09.05 oohashi 本番に合わせるため修正

		//hachu_en_dt に対するWHERE区
		if(map.get("hachu_st_dt") != null && map.get("hachu_st_dt").toString().trim().length() != 0){
			if(map.get("hachu_en_dt") != null && map.get("hachu_en_dt").toString().trim().length() != 0){
				sb.append(" AND ");
				sb.append("D.HATYU_DT <= '").append(map.get("hachu_en_dt")).append("' ");
				sb.append(" AND ");
				sb.append("D.HATYU_DT >= '").append(map.get("hachu_st_dt")).append("' ");
			} else {
				sb.append(" AND ");
				sb.append("D.HATYU_DT >= '").append(map.get("hachu_st_dt")).append("' ");
			}
		}
		//nohin_en_dt に対するWHERE区
		if(map.get("nohin_st_dt") != null && map.get("nohin_st_dt").toString().trim().length() != 0){
			if(map.get("nohin_en_dt") != null && map.get("nohin_en_dt").toString().trim().length() != 0){
				sb.append(" AND ");
				sb.append("D.NOHIN_DT <= '").append(map.get("nohin_en_dt")).append("' ");
				sb.append(" AND ");
				sb.append("D.NOHIN_DT >= '").append(map.get("nohin_st_dt")).append("' ");
			} else {
				sb.append(" AND ");
				sb.append("D.NOHIN_DT >= '").append(map.get("nohin_st_dt")).append("' ");
			}
		}
		sb.append(") ");

		sb.append("ORDER BY ");
		sb.append("S.HINBAN_CD ");
		sb.append(", ");
		sb.append("S.SIIRE_HINBAN_CD ");
		sb.append(", ");
		sb.append("S.SYOHIN_CD ");
		sb.append(" for read only ");

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
