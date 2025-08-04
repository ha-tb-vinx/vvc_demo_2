/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発行管理マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する発行管理マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/04)初版作成
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
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst001001_ShihaiKbDictionary;
import mdware.master.common.dictionary.mst003701_TousanKbDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）発行管理マスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する発行管理マスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/04)初版作成
 *
 * @version 1.1(2005/05/19)新ER対応
 *
 * 　　　　　追加…発行区分(hakou_kb)
 *                 発行場所コード(hakou_basyo_cd)
 *
 *           修正…商品マスタ登録情報区分(syohin_toroku_kb) ⇒ 商品ﾏｽﾀ登録情報媒体区分(syohin_toroku_batai_kb)
 *                 初回導入提案区分(syohin_syokai_donyu_kb) ⇒ 初回導入提案媒体区分(syohin_syokai_donyu_batai_kb)
 *                 発注区分(hachu_kb)             ⇒ 発注媒体区分(hachu_batai_kb)
 *                 納品(ASN)区分(nohin_asn_kb)    ⇒ 納品(ASN)媒体区分(nohin_asn_batai_kb)
 *                 欠品区分(kepin_kb)             ⇒ 欠品媒体区分(kepin_batai_kb)
 *                 仕入計上区分(siire_keijo_kb)   ⇒ 仕入計上媒体区分(siire_keijo_batai_kb)
 *                 請求区分(seikyu_kb)            ⇒ 請求媒体区分(seikyu_batai_kb)
 *                 支払区分(siharai_kb)           ⇒ 支払媒体区分(siharai_batai_kb)
 *                 販売区分(hanbai_kb)            ⇒ 販売媒体区分(hanbai_batai_kb)
 *                 発注勧告区分(hachu_kankoku_kb) ⇒ 発注勧告媒体区分(hachu_kankoku_batai_kb)
 *                 在庫区分(zaiko_kb)             ⇒ 在庫媒体区分(zaiko_batai_kb)
 *                 タグ区分(tag_kb)               ⇒ タグ媒体区分(tag_batai_kb)
 */
public class mst480101_HakoKanriDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	/**
	 * コンストラクタ
	 */
	public mst480101_HakoKanriDM()
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
		mst480101_HakoKanriBean bean = new mst480101_HakoKanriBean();
//	　　↓↓2006.06.20 maojm カスタマイズ修正↓↓
//		bean.setKanriKb( rest.getString("kanri_kb") );
//		bean.setKanriCd( rest.getString("kanri_cd") );
//		bean.setKanriKanjiRn( rest.getString("kanri_kanji_rn") );
        bean.setBumonCd(rest.getString("kanri_cd"));
        bean.setBumonKanjiRn(rest.getString("bumon_kanji_rn"));
//		↑↑2006.06.20 maojm カスタマイズ修正↑↑
		bean.setShihaiKb( rest.getString("shihai_kb") );
		bean.setShihaiCd( rest.getString("shihai_cd") );
		bean.setShihaiKanjiRn( rest.getString("shihai_kanji_rn") );
		bean.setTenpoCd( rest.getString("tenpo_cd") );
		bean.setTenpoKanjiRn( rest.getString("tenpo_kanji_rn") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setWebHaisinsakiCd( rest.getString("web_haisinsaki_cd") );
		bean.setJcaHaisinsakiCd( rest.getString("jca_haisinsaki_cd") );

		//新ER対応 *** 項目追加 start ***
//		 ↓↓2006.06.20 maojm カスタマイズ修正↓↓
//		bean.setHakouKb(rest.getString("hakou_kb"));			//発行区分
//		bean.setHakouBasyoCd(rest.getString("hakou_basyo_cd"));	//発行場所コード
//		 ↑↑2006.06.20 maojm カスタマイズ修正↑↑
		//新ER対応 *** 項目追加 end ***

		//新ER対応 *** 項目名変更 start ***
		bean.setSyohinTorokuBataiKb(rest.getString("syohin_toroku_batai_kb"));				//商品ﾏｽﾀ登録情報媒体区分
		bean.setSyohinSyokaiDonyuBataiKb(rest.getString("syohin_syokai_donyu_batai_kb"));	//初回導入提案媒体区分
		bean.setHachuBataiKb(rest.getString("hachu_batai_kb"));								//発注媒体区分
		bean.setNohinAsnBataiKb(rest.getString("nohin_asn_batai_kb"));						//納品(ASN)媒体区分
		bean.setKepinBataiKb(rest.getString("kepin_batai_kb"));              				//欠品媒体区分
		bean.setSiireKeijoBataiKb(rest.getString("siire_keijo_batai_kb"));					//仕入計上媒体区分
		bean.setSeikyuBataiKb(rest.getString("seikyu_batai_kb"));							//請求媒体区分
		bean.setSiharaiBataiKb(rest.getString("siharai_batai_kb"));							//支払媒体区分
		bean.setHanbaiBataiKb(rest.getString("hanbai_batai_kb"));							//販売媒体区分
		bean.setHachuKankokuBataiKb(rest.getString("hachu_kankoku_batai_kb"));				//発注勧告媒体区分
		bean.setZaikoBataiKb(rest.getString("zaiko_batai_kb"));								//在庫媒体区分
		bean.setTagBataiKb(rest.getString("tag_batai_kb"));									//タグ媒体区分
		//新ER対応 *** 項目名変更 end ***
/*
		bean.setSyohinTorokuKb( rest.getString("syohin_toroku_kb") );
		bean.setSyohinSyokaiDonyuKb( rest.getString("syohin_syokai_donyu_kb") );
		bean.setHachuKb( rest.getString("hachu_kb") );
		bean.setNohinAsnKb( rest.getString("nohin_asn_kb") );
		bean.setKepinKb( rest.getString("kepin_kb") );
		bean.setSiireKeijoKb( rest.getString("siire_keijo_kb") );
		bean.setSeikyuKb( rest.getString("seikyu_kb") );
		bean.setSiharaiKb( rest.getString("siharai_kb") );
		bean.setHanbaiKb( rest.getString("hanbai_kb") );
		bean.setHachuKankokuKb( rest.getString("hachu_kankoku_kb") );
		bean.setZaikoKb( rest.getString("zaiko_kb") );
		bean.setTagKb( rest.getString("tag_kb") );
*/
		bean.setSyukanhachuyoteiKb( rest.getString("syukanhachuyotei_kb") );
		bean.setTenmeisaiKb( rest.getString("tenmeisai_kb") );
		bean.setInsertTs( rest.getString("insert_ts") );
		bean.setInsertUserId( rest.getString("insert_user_id") );
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setUpdateUserId( rest.getString("update_user_id") );
		bean.setDeleteFg( rest.getString("delete_fg") );
//		↓↓仕様変更（2005.07.28）↓↓
		bean.setSyokaiTorokuTs( mst000401_LogicBean.chkNullString(rest.getString("syokai_toroku_ts")) );
		bean.setSyokaiUserId( mst000401_LogicBean.chkNullString(rest.getString("syokai_user_id")) );
//		↑↑仕様変更（2005.07.28）↑↑
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
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("TRIM(TBL2.kanri_kb) kanri_kb ");
		sb.append(", ");
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		sb.append("TRIM(TBL2.kanri_cd) kanri_cd ");
//		sb.append(", ");
//		sb.append("TRIM(TBL3.kanji_rn) kanri_kanji_rn ");
		sb.append("trim(tbl2.kanri_cd) kanri_cd ");
		sb.append(", ");
		sb.append("trim(tbl3.kanji_rn) bumon_kanji_rn ");
//	　　↑↑2006.06.21 maojm カスタマイズ修正↑↑
		sb.append(", ");
		sb.append("TRIM(TBL2.shihai_kb) shihai_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.shihai_cd) shihai_cd ");
		sb.append(", ");
//		↓↓仕様変更（2005.09.29）↓↓
//		↓↓移植（2006.05.12）↓↓
//		sb.append("TRIM(TBL4.kanji_rn) shihai_kanji_rn ");
		if( map.get("shihai_rn") != null && ((String)map.get("shihai_rn")).trim().length() > 0 ) {
			sb.append("TRIM(TBL4.kanji_rn) shihai_kanji_rn ");
		} else {
			sb.append("cast(null as char) as shihai_kanji_rn ");
		}
//		↑↑移植（2006.05.12）↑↑
//		↑↑仕様変更（2005.09.29）↑↑
		sb.append(", ");
		sb.append("TRIM(TBL2.tenpo_cd) tenpo_cd ");
		sb.append(", ");
		sb.append("TRIM(TBL1.kanji_rn) tenpo_kanji_rn ");
		sb.append(", ");
		sb.append("TRIM(TBL2.yuko_dt) yuko_dt ");
		sb.append(", ");
		sb.append("TRIM(TBL2.web_haisinsaki_cd) web_haisinsaki_cd ");
		sb.append(", ");
		sb.append("TRIM(TBL2.jca_haisinsaki_cd) jca_haisinsaki_cd ");
		sb.append(", ");

//新ER対応 *** 項目追加 start ***
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		  sb.append("TRIM(TBL2.hakou_kb) hakou_kb ");//発行区分
//		  sb.append(", ");
//		  sb.append("TRIM(TBL2.hakou_basyo_cd) hakou_basyo_cd ");//発行場所コード
//		  sb.append(", ");
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
//新ER対応 *** 項目追加 end ***

//新ER対応 *** 項目名変更 start ***
		  sb.append("TRIM(TBL2.syohin_toroku_batai_kb) syohin_toroku_batai_kb ");//商品ﾏｽﾀ登録情報媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.syohin_syokai_donyu_batai_kb) syohin_syokai_donyu_batai_kb ");//初回導入提案媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.hachu_batai_kb) hachu_batai_kb ");//発注媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.nohin_asn_batai_kb) nohin_asn_batai_kb ");//納品(ASN)媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.kepin_batai_kb) kepin_batai_kb ");//欠品媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.siire_keijo_batai_kb) siire_keijo_batai_kb ");//仕入計上媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.seikyu_batai_kb) seikyu_batai_kb ");//請求媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.siharai_batai_kb) siharai_batai_kb ");//支払媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.hanbai_batai_kb) hanbai_batai_kb ");//販売媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.hachu_kankoku_batai_kb) hachu_kankoku_batai_kb ");//発注勧告媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.zaiko_batai_kb) zaiko_batai_kb ");//在庫媒体区分
		  sb.append(", ");
		  sb.append("TRIM(TBL2.tag_batai_kb) tag_batai_kb ");//タグ媒体区分
		  sb.append(", ");
//新ER対応 *** 項目名変更 end ***

/*
		sb.append("TRIM(TBL2.syohin_toroku_kb) syohin_toroku_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.syohin_syokai_donyu_kb) syohin_syokai_donyu_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.hachu_kb) hachu_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.nohin_asn_kb) nohin_asn_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.kepin_kb) kepin_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.siire_keijo_kb) siire_keijo_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.seikyu_kb) seikyu_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.siharai_kb) siharai_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.hanbai_kb) hanbai_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.hachu_kankoku_kb) hachu_kankoku_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.zaiko_kb) zaiko_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.tag_kb) tag_kb ");
		sb.append(", ");
*/
		sb.append("TRIM(TBL2.syukanhachuyotei_kb) syukanhachuyotei_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.tenmeisai_kb) tenmeisai_kb ");
		sb.append(", ");
		sb.append("TRIM(TBL2.insert_ts) insert_ts ");
		sb.append(", ");
		sb.append("TRIM(TBL2.insert_user_id) insert_user_id ");
		sb.append(", ");
		sb.append("TRIM(TBL2.update_ts) update_ts ");
		sb.append(", ");
		sb.append("TRIM(TBL2.update_user_id) update_user_id ");
		sb.append(", ");
		sb.append("TRIM(TBL2.delete_fg) delete_fg ");
//		↓↓仕様変更（2005.07.28）↓↓
		sb.append(", ");
		sb.append("TRIM(TBL2.syokai_toroku_ts) syokai_toroku_ts ");
		sb.append(", ");
		sb.append("TRIM(TBL2.syokai_user_id) syokai_user_id ");
//		↑↑仕様変更（2005.07.28）↑↑
//		↓↓移植（2006.05.12）↓↓
		sb.append("from ");
		sb.append("     R_NAMECTF TBL3 ");
//		↓↓仕様変更（2005.09.29）↓↓--最外部のif文追加--

		if( map.get("shihai_rn") != null && ((String)map.get("shihai_rn")).trim().length() > 0 ) {
			sb.append(", ");
			if(((String)map.get("shihai_kb")).equals(mst001001_ShihaiKbDictionary.SIRESAKI.getCode())){
				sb.append(" R_SIIRESAKI TBL4 ");
			}
//			 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//			if(((String)map.get("shihai_kb")).equals(mst001001_ShihaiKbDictionary.HAISOUSAKI.getCode())){
//				sb.append(" R_HAISOU TBL4 ");
//			}
		}
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
//		↑↑仕様変更（2005.09.29）↑↑--最外部のif文追加--
		sb.append(", ");
		sb.append("	   R_HAKOKANRI TBL2 ");
		sb.append("	   left outer join R_TENPO TBL1 ");
		sb.append("     on TBL2.tenpo_cd =TBL1.tenpo_cd ");
		sb.append(whereStr);
//		↓↓移植（2006.05.22）↓↓
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		if(((String)map.get("kanri_kb")).equals(mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode())){
//			sb.append(" TBL3.syubetu_no_cd = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS + "' and ");
//		}
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		sb.append(" tbl3.syubetu_no_cd = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI1, userLocal) + "' and ");

//		if(((String)map.get("kanri_kb")).equals(mst000901_KanriKbDictionary.HANKU.getCode())){
//			sb.append(" TBL3.syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' and ");
//		}
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
//		↑↑移植（2006.05.22）↑↑
		sb.append(" TBL2.kanri_cd  = TBL3.code_cd ");
//		↑↑移植（2006.05.12）↑↑
//		↓↓仕様変更（2005.09.29）↓↓--最外部のif文追加--
		if( map.get("shihai_rn") != null && ((String)map.get("shihai_rn")).trim().length() > 0 ) {

			sb.append(" and TBL2.kanri_kb  = TBL4.kanri_kb");

//			 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//			sb.append(" and TBL2.kanri_cd  = TBL4.kanri_cd");
//			//選択業種が生鮮の場合
//			if (map.get("gyosyu_id").equals("04")) {
//				sb.append(" and TBL4.kanri_kb = '" + mst000901_KanriKbDictionary.DAI_GYOUSYU.getCode() + "'");
//				sb.append(" and TBL4.kanri_cd = '" + mst000101_ConstDictionary.LARGE_TYPE_OF_FOOD + "'");
//
//			//選択業種が生鮮以外の場合
//			} else {
//				sb.append(" and TBL4.kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
//				sb.append(" and TBL4.kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
//
//			}
	    sb.append(" and tbl4.kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' ");
		sb.append(" and tbl4.kanri_cd = '0000' ");
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
		sb.append(" and TBL4.delete_fg = '0' ");
	    sb.append("	and TBL4.TOSAN_KB = '" + mst003701_TousanKbDictionary.TOUSAN_SITENAI.getCode() + "'");


			if(((String)map.get("shihai_kb")).equals(mst001001_ShihaiKbDictionary.SIRESAKI.getCode())){
				sb.append(" and TBL2.shihai_cd = TBL4.siiresaki_cd");
			}
			if(((String)map.get("shihai_kb")).equals(mst001001_ShihaiKbDictionary.HAISOUSAKI.getCode())){
				sb.append(" and TBL2.shihai_cd = TBL4.haisosaki_cd");
			}
		}
//		↑↑仕様変更（2005.09.29）↑↑--最外部のif文追加--

		whereStr = andStr;
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		// kanri_kb に対するWHERE区
//		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL2.kanri_kb = '" + conv.convertWhereString( (String)map.get("kanri_kb") ) + "'");
//			whereStr = andStr;
//		}

//		// kanri_cd に対するWHERE区
//		if( map.get("kanri_cd") != null && ((String)map.get("kanri_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL2.kanri_cd = '" + conv.convertWhereString( (String)map.get("kanri_cd") ) + "'");
//			whereStr = andStr;
//		}
//
//		 kanri_kb に対するWHERE区
//		if( map.get("kanri_kb") != null && ((String)map.get("kanri_kb")).trim().length() > 0 )
//		{
			sb.append(whereStr);
			sb.append("tbl2.kanri_kb = '" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' ");
			whereStr = andStr;
//		}

		// kanri_cd に対するWHERE区
		if( map.get("bumon_cd") != null && ((String)map.get("bumon_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("tbl2.kanri_cd =  ");
			sb.append("'" + StringUtility.charFormat( conv.convertWhereString( (String)map.get("bumon_cd") ),4,"0",true) + "'");
			whereStr = andStr;
		}

//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
		// shihai_kb に対するWHERE区
		if( map.get("shihai_kb") != null && ((String)map.get("shihai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.shihai_kb = '" + conv.convertWhereString( (String)map.get("shihai_kb") ) + "'");
			whereStr = andStr;
		}

		// shihai_cd に対するWHERE区
		if( map.get("shihai_cd") != null && ((String)map.get("shihai_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.shihai_cd = '" + conv.convertWhereString( (String)map.get("shihai_cd") ) + "'");
			whereStr = andStr;
		}

		// tenpo_cd に対するWHERE区
		if( map.get("tenpo_cd") != null && ((String)map.get("tenpo_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
//			 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//			sb.append("TBL2.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			sb.append("TBL2.tenpo_cd = ");
            sb.append("'"+ StringUtility.charFormat(  conv.convertWhereString( (String)map.get("tenpo_cd") ),6,"000",true)+"'");

//   		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
			whereStr = andStr;
		}

		// yuko_dt に対するWHERE区
		if( map.get("yuko_dt") != null && ((String)map.get("yuko_dt")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.yuko_dt = '" + conv.convertWhereString( (String)map.get("yuko_dt") ) + "'");
			whereStr = andStr;
		}

		// web_haisinsaki_cd に対するWHERE区
		if( map.get("web_haisinsaki_cd") != null && ((String)map.get("web_haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.web_haisinsaki_cd = '" + conv.convertWhereString( (String)map.get("web_haisinsaki_cd") ) + "'");
			whereStr = andStr;
		}

		// jca_haisinsaki_cd に対するWHERE区
		if( map.get("jca_haisinsaki_cd") != null && ((String)map.get("jca_haisinsaki_cd")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.jca_haisinsaki_cd = '" + conv.convertWhereString( (String)map.get("jca_haisinsaki_cd") ) + "'");
			whereStr = andStr;
		}

	/****************************************************************************************************
	 * 							(2005/05/19)新ER対応    追加項目	@author sawabe
	 ****************************************************************************************************/
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		/**
//		 * 発行区分
//		 * syohin_toroku_batai_kb に対するWHERE区
//		 */
//		if( map.get("hakou_kb") != null && ((String)map.get("hakou_kb")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL2.hakou_kb = '" + conv.convertWhereString( (String)map.get("hakou_kb") ) + "'");
//			whereStr = andStr;
//		}

//		/**
//		 * 発行場所コード
//		 * hakou_basyo_cd に対するWHERE区
//		 */
//		if( map.get("hakou_basyo_cd") != null && ((String)map.get("hakou_basyo_cd")).trim().length() > 0 )
//		{
//			sb.append(whereStr);
//			sb.append("TBL2.hakou_basyo_cd = '" + conv.convertWhereString( (String)map.get("hakou_basyo_cd") ) + "'");
//			whereStr = andStr;
//		}
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
	/******************************************************************************************************************************
	 * 							      (2005/05/19)新ER対応    追加名修正	@author sawabe
	 ******************************************************************************************************************************/
		/**
		 * 商品ﾏｽﾀ登録情報媒体区分
		 * syohin_toroku_batai_kb に対するWHERE区
		 */
		if( map.get("syohin_toroku_batai_kb") != null && ((String)map.get("syohin_toroku_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.syohin_toroku_batai_kb = '" + conv.convertWhereString( (String)map.get("syohin_toroku_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 初回導入提案媒体区分
		 * syohin_syokai_donyu_batai_kb に対するWHERE区
		 */
		if( map.get("syohin_syokai_donyu_batai_kb") != null && ((String)map.get("syohin_syokai_donyu_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.syohin_syokai_donyu_batai_kb = '" + conv.convertWhereString( (String)map.get("syohin_syokai_donyu_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 発注媒体区分
		 * hachu_batai_kb に対するWHERE区
		 */
		if( map.get("hachu_batai_kb") != null && ((String)map.get("hachu_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.hachu_batai_kb = '" + conv.convertWhereString( (String)map.get("hachu_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 納品(ASN)媒体区分
		 * nohin_asn_batai_kb に対するWHERE区
		 */
		if( map.get("nohin_asn_batai_kb") != null && ((String)map.get("nohin_asn_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.nohin_asn_batai_kb = '" + conv.convertWhereString( (String)map.get("nohin_asn_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 欠品媒体区分
		 * kepin_batai_kb に対するWHERE区
		 */
		if( map.get("kepin_batai_kb") != null && ((String)map.get("kepin_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.kepin_batai_kb = '" + conv.convertWhereString( (String)map.get("kepin_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 仕入計上媒体区分
		 * siire_keijo_batai_kb に対するWHERE区
		 */
		if( map.get("siire_keijo_batai_kb") != null && ((String)map.get("siire_keijo_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.siire_keijo_batai_kb = '" + conv.convertWhereString( (String)map.get("siire_keijo_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 請求媒体区分
		 * seikyu_batai_kb に対するWHERE区
		 */
		if( map.get("seikyu_batai_kb") != null && ((String)map.get("seikyu_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.seikyu_batai_kb = '" + conv.convertWhereString( (String)map.get("seikyu_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 支払媒体区分
		 * siharai_batai_kb に対するWHERE区
		 */
		if( map.get("siharai_batai_kb") != null && ((String)map.get("siharai_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.siharai_batai_kb = '" + conv.convertWhereString( (String)map.get("siharai_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 販売媒体区分
		 * hanbai_batai_kb に対するWHERE区
		 */
		if( map.get("hanbai_batai_kb") != null && ((String)map.get("hanbai_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.hanbai_batai_kb = '" + conv.convertWhereString( (String)map.get("hanbai_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 発注勧告媒体区分
		 * hachu_kankoku_hachu_kankoku_batai_kbbatai_kb に対するWHERE区
		 */
		if( map.get("hachu_kankoku_batai_kb") != null && ((String)map.get("hachu_kankoku_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.hachu_kankoku_batai_kb = '" + conv.convertWhereString( (String)map.get("hachu_kankoku_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * 在庫媒体区分
		 * zaiko_batai_kzaiko_batai_kbb に対するWHERE区
		 */
		if( map.get("zaiko_batai_kb") != null && ((String)map.get("zaiko_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.zaiko_batai_kb = '" + conv.convertWhereString( (String)map.get("zaiko_batai_kb") ) + "'");
			whereStr = andStr;
		}

		/**
		 * タグ媒体区分
		 * tag_batag_batai_kbtai_kb に対するWHERE区
		 */
		if( map.get("tag_batai_kb") != null && ((String)map.get("tag_batai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.tag_batai_kb = '" + conv.convertWhereString( (String)map.get("tag_batai_kb") ) + "'");
			whereStr = andStr;
		}

		// syukanhachuyotei_kb に対するWHERE区
		if( map.get("syukanhachuyotei_kb") != null && ((String)map.get("syukanhachuyotei_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.syukanhachuyotei_kb = '" + conv.convertWhereString( (String)map.get("syukanhachuyotei_kb") ) + "'");
			whereStr = andStr;
		}

		// tenmeisai_kb に対するWHERE区
		if( map.get("tenmeisai_kb") != null && ((String)map.get("tenmeisai_kb")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.tenmeisai_kb = '" + conv.convertWhereString( (String)map.get("tenmeisai_kb") ) + "'");
			whereStr = andStr;
		}

		// insert_ts に対するWHERE区
		if( map.get("insert_ts") != null && ((String)map.get("insert_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.insert_ts = '" + conv.convertWhereString( (String)map.get("insert_ts") ) + "'");
			whereStr = andStr;
		}

		// insert_user_id に対するWHERE区
		if( map.get("insert_user_id") != null && ((String)map.get("insert_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.insert_user_id = '" + conv.convertWhereString( (String)map.get("insert_user_id") ) + "'");
			whereStr = andStr;
		}

		// update_ts に対するWHERE区
		if( map.get("update_ts") != null && ((String)map.get("update_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.update_ts = '" + conv.convertWhereString( (String)map.get("update_ts") ) + "'");
			whereStr = andStr;
		}

		// update_user_id に対するWHERE区
		if( map.get("update_user_id") != null && ((String)map.get("update_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.update_user_id = '" + conv.convertWhereString( (String)map.get("update_user_id") ) + "'");
			whereStr = andStr;
		}

		// delete_fg に対するWHERE区
		if( map.get("delete_fg") != null && ((String)map.get("delete_fg")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.delete_fg = '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
			whereStr = andStr;
		}

//		↓↓仕様変更（2005.07.28）↓↓
		// syokai_toroku_ts に対するWHERE区
		if( map.get("syokai_toroku_ts") != null && ((String)map.get("syokai_toroku_ts")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.syokai_toroku_ts = '" + conv.convertWhereString( (String)map.get("syokai_toroku_ts") ) + "'");
			whereStr = andStr;
		}

		// syokai_user_id に対するWHERE区
		if( map.get("syokai_user_id") != null && ((String)map.get("syokai_user_id")).trim().length() > 0 )
		{
			sb.append(whereStr);
			sb.append("TBL2.syokai_user_id = '" + conv.convertWhereString( (String)map.get("syokai_user_id") ) + "'");
			whereStr = andStr;
		}
//		↑↑仕様変更（2005.07.28）↑↑

		sb.append(" order by ");
		sb.append("kanri_kb");
		sb.append(",");
	   sb.append("kanri_cd");
		sb.append(",");
		sb.append("shihai_kb");
		sb.append(",");
		sb.append("shihai_cd");
		sb.append(",");
		sb.append("tenpo_cd");
		sb.append(",");
		sb.append("yuko_dt");

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
		boolean befKanma = false;
		boolean aftKanma = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst480101_HakoKanriBean bean = (mst480101_HakoKanriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append("R_HAKOKANRI (");
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			befKanma = true;
//			//////変更（2005.06.14）///////////////////////////////////////////////
//			sb.append(" kanri_kb");
		    befKanma = true;
			sb.append(" kanri_kb");
//			//////変更（2005.06.14）///////////////////////////////////////////////
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" kanri_cd");
//		}
	if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
	{
		if( befKanma ) sb.append(","); else befKanma = true;
		sb.append(" kanri_cd");
	}
//	 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
	if( bean.getShihaiKb() != null && bean.getShihaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" shihai_kb");
		}
		if( bean.getShihaiCd() != null && bean.getShihaiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" shihai_cd");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenpo_cd");
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" yuko_dt");
		}
		if( bean.getWebHaisinsakiCd() != null && bean.getWebHaisinsakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" web_haisinsaki_cd");
		}
		if( bean.getJcaHaisinsakiCd() != null && bean.getJcaHaisinsakiCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" jca_haisinsaki_cd");
		}

		/******************** 新ER対応 *** 項目追加 start ********************/
//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		//発行区分
//		if( bean.getHakouKb() != null && bean.getHakouKb().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hakou_kb" );
//		}
//		//発行場所コード
//		if( bean.getHakouBasyoCd() != null && bean.getHakouBasyoCd().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" hakou_basyo_cd" );
//		}
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
		/******************** 新ER対応 *** 項目追加 end ********************/

		/******************** 新ER対応 *** 項目名変更 start ********************/
		//商品ﾏｽﾀ登録情報媒体区分
		if( bean.getSyohinTorokuBataiKb() != null && bean.getSyohinTorokuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_toroku_batai_kb");
		}
		//初回導入提案媒体区分
		if( bean.getSyohinSyokaiDonyuBataiKb() != null && bean.getSyohinSyokaiDonyuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_syokai_donyu_batai_kb");
		}
		//発注媒体区分
		if( bean.getHachuBataiKb() != null && bean.getHachuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_batai_kb");
		}
		//納品(ASN)媒体区分
		if( bean.getNohinAsnBataiKb() != null && bean.getNohinAsnBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_asn_batai_kb");
		}
		//欠品媒体区分
		if( bean.getKepinBataiKb() != null && bean.getKepinBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kepin_batai_kb");
		}
		//仕入計上媒体区分
		if( bean.getSiireKeijoBataiKb() != null && bean.getSiireKeijoBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_keijo_batai_kb");
		}
		//請求媒体区分
		if( bean.getSeikyuBataiKb() != null && bean.getSeikyuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" seikyu_batai_kb");
		}
		//支払媒体区分
		if( bean.getSiharaiBataiKb() != null && bean.getSiharaiBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siharai_batai_kb");
		}
		//販売媒体区分
		if( bean.getHanbaiBataiKb() != null && bean.getHanbaiBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_batai_kb");
		}
		//発注勧告媒体区分
		if( bean.getHachuKankokuBataiKb() != null && bean.getHachuKankokuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_kankoku_batai_kb");
		}
		//在庫媒体区分
		if( bean.getZaikoBataiKb() != null && bean.getZaikoBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" zaiko_batai_kb");
		}
		//タグ媒体区分
// 2005.09.01 Inaba 項目修正 ZaikoBataiKb→TagBataiKb START
//		if( bean.getZaikoBataiKb() != null && bean.getZaikoBataiKb().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" tag_batai_kb");
//		}
		if( bean.getTagBataiKb() != null && bean.getTagBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tag_batai_kb");
		}
// 2005.09.01 Inaba 項目修正 ZaikoBataiKb→TagBataiKb END
		/******************** 新ER対応 *** 項目名変更 end ********************/

		if( bean.getSyukanhachuyoteiKb() != null && bean.getSyukanhachuyoteiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukanhachuyotei_kb");
		}
		if( bean.getTenmeisaiKb() != null && bean.getTenmeisaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenmeisai_kb");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg");
		}
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_toroku_ts");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_user_id");
		}
//		↑↑仕様変更（2005.07.28）↑↑


		sb.append(")Values(");

//		 ↓↓2006.06.21 maojm カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getKanriCd() ) + "'");
//		}
		aftKanma = true;
		sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' ");
		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
//			sb.append("'" + conv.convertString( bean.getBumonCd() ) + "'");
			sb.append(" '" + StringUtility.charFormat(conv.convertString( bean.getBumonCd() ),4,"0",true) + "' ");
		}
//		 ↑↑2006.06.21 maojm カスタマイズ修正↑↑
		if( bean.getShihaiKb() != null && bean.getShihaiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getShihaiKb() ) + "'");
		}
		if( bean.getShihaiCd() != null && bean.getShihaiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getShihaiCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
//			↓↓2006.06.22 maojm カスタマイズ修正↓↓
//			sb.append("'" + conv.convertString( bean.getTenpoCd() ) + "'");
			sb.append("'" + StringUtility.charFormat(conv.convertString( bean.getTenpoCd() ),6,"000",true) + "' ");
//			↑↑2006.06.22 maojm カスタマイズ修正↑↑
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getYukoDt() ) + "'");
		}
		if( bean.getWebHaisinsakiCd() != null && bean.getWebHaisinsakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getWebHaisinsakiCd() ) + "'");
		}
		if( bean.getJcaHaisinsakiCd() != null && bean.getJcaHaisinsakiCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getJcaHaisinsakiCd() ) + "'");
		}

		/*********************** 新ER対応 *** 項目追加 start ***********************/
//		 ↓↓2006.06.22 maojm カスタマイズ修正↓↓
/*		//発行区分
		if( bean.getHakouKb() != null && bean.getHakouKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHakouKb() ) + "'");
		}
		//発行場所コード
		if( bean.getHakouBasyoCd() != null && bean.getHakouBasyoCd().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHakouBasyoCd() ) + "'");
		}
		*/
//		 ↑↑2006.06.22 maojm カスタマイズ修正↑↑
		/*********************** 新ER対応 *** 項目追加 end ***********************/

		/*********************** 新ER対応 *** 項目名変更 start ***********************/
		//商品ﾏｽﾀ登録情報媒体区分
		if( bean.getSyohinTorokuBataiKb() != null && bean.getSyohinTorokuBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinTorokuBataiKb() ) + "'");
		}
		//初回導入提案媒体区分
		if( bean.getSyohinSyokaiDonyuBataiKb() != null && bean.getSyohinSyokaiDonyuBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyohinSyokaiDonyuBataiKb() ) + "'");
		}
		//発注媒体区分
		if( bean.getHachuBataiKb() != null && bean.getHachuBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuBataiKb() ) + "'");
		}
		//納品(ASN)媒体区分
		if( bean.getNohinAsnBataiKb() != null && bean.getNohinAsnBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getNohinAsnBataiKb() ) + "'");
		}
		//欠品媒体区分
		if( bean.getKepinBataiKb() != null && bean.getKepinBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getKepinBataiKb() ) + "'");
		}
		//仕入計上媒体区分
		if( bean.getSiireKeijoBataiKb() != null && bean.getSiireKeijoBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiireKeijoBataiKb() ) + "'");
		}
		//請求媒体区分
		if( bean.getSeikyuBataiKb() != null && bean.getSeikyuBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSeikyuBataiKb() ) + "'");
		}
		//支払媒体区分
		if( bean.getSiharaiBataiKb() != null && bean.getSiharaiBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSiharaiBataiKb() ) + "'");
		}
		//販売媒体区分
		if( bean.getHanbaiBataiKb() != null && bean.getHanbaiBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHanbaiBataiKb() ) + "'");
		}
		//発注勧告媒体区分
		if( bean.getHachuKankokuBataiKb() != null && bean.getHachuKankokuBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getHachuKankokuBataiKb() ) + "'");
		}
		//在庫媒体区分
		if( bean.getZaikoBataiKb() != null && bean.getZaikoBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getZaikoBataiKb() ) + "'");
		}
		//タグ媒体区分
		if( bean.getTagBataiKb() != null && bean.getTagBataiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTagBataiKb() ) + "'");
		}
		/*********************** 新ER対応 *** 項目名変更 end ***********************/

		if( bean.getSyukanhachuyoteiKb() != null && bean.getSyukanhachuyoteiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getSyukanhachuyoteiKb() ) + "'");
		}
		if( bean.getTenmeisaiKb() != null && bean.getTenmeisaiKb().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getTenmeisaiKb() ) + "'");
		}
//      ↓↓移植(2006.05.22)↓↓
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			//sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
			try {
		    	sb.append("'"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+ "'");
		   	}catch (SQLException e){
		        e.printStackTrace();
		   	}
		}
//      ↑↑移植(2006.05.22)↑↑
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
		}
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( aftKanma ) sb.append(","); else aftKanma = true;
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append("'" + conv.convertString( bean.getSyokaiTorokuTs() ) + "'");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append("'" + conv.convertString( bean.getSyokaiUserId() ) + "'");
		}
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
		boolean befKanma = false;
		boolean whereAnd = false;
//		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mst480101_HakoKanriBean bean = (mst480101_HakoKanriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append("R_HAKOKANRI set ");
//		if( bean.getWebHaisinsakiCd() != null && bean.getWebHaisinsakiCd().trim().length() != 0 )
//		{
			befKanma = true;
			sb.append(" web_haisinsaki_cd = ");
			sb.append("'" + conv.convertString( bean.getWebHaisinsakiCd() ) + "'");
//		}
//		if( bean.getJcaHaisinsakiCd() != null && bean.getJcaHaisinsakiCd().trim().length() != 0 )
//		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" jca_haisinsaki_cd = ");
			sb.append("'" + conv.convertString( bean.getJcaHaisinsakiCd() ) + "'");
//		}

		/******************** 新ER対応 *** 項目追加 start ********************/
//	　　↓↓2006.06.22 maojm カスタマイズ修正↓↓
/*		//発行区分
		if( bean.getHakouKb() != null && bean.getHakouKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hakou_kb = " );
			sb.append("'" + conv.convertString( bean.getHakouKb() ) + "'");
		}
		//発行場所コード
		if( bean.getHakouBasyoCd() != null && bean.getHakouBasyoCd().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hakou_basyo_cd = " );
			sb.append("'" + conv.convertString( bean.getHakouBasyoCd() ) + "'");
		}
*/
//	　　↑↑2006.06.22 maojm カスタマイズ修正↑↑
		/******************** 新ER対応 *** 項目追加 end ********************/

		/******************** 新ER対応 *** 項目名変更 start ********************/
		//商品ﾏｽﾀ登録情報媒体区分
		if( bean.getSyohinTorokuBataiKb() != null && bean.getSyohinTorokuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_toroku_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getSyohinTorokuBataiKb() ) + "'");
		}
		//初回導入提案媒体区分
		if( bean.getSyohinSyokaiDonyuBataiKb() != null && bean.getSyohinSyokaiDonyuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syohin_syokai_donyu_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getSyohinSyokaiDonyuBataiKb() ) + "'");
		}
		//発注媒体区分
		if( bean.getHachuBataiKb() != null && bean.getHachuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuBataiKb() ) + "'");
		}
		//納品(ASN)媒体区分
		if( bean.getNohinAsnBataiKb() != null && bean.getNohinAsnBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" nohin_asn_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getNohinAsnBataiKb() ) + "'");
		}
		//欠品媒体区分
		if( bean.getKepinBataiKb() != null && bean.getKepinBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" kepin_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getKepinBataiKb() ) + "'");
		}
		//仕入計上媒体区分
		if( bean.getSiireKeijoBataiKb() != null && bean.getSiireKeijoBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siire_keijo_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getSiireKeijoBataiKb() ) + "'");
		}
		//請求媒体区分
		if( bean.getSeikyuBataiKb() != null && bean.getSeikyuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" seikyu_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getSeikyuBataiKb() ) + "'");
		}
		//支払媒体区分
		if( bean.getSiharaiBataiKb() != null && bean.getSiharaiBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" siharai_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getSiharaiBataiKb() ) + "'");
		}
		//販売媒体区分
		if( bean.getHanbaiBataiKb() != null && bean.getHanbaiBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hanbai_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getHanbaiBataiKb() ) + "'");
		}
		//発注勧告媒体区分
		if( bean.getHachuKankokuBataiKb() != null && bean.getHachuKankokuBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" hachu_kankoku_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getHachuKankokuBataiKb() ) + "'");
		}
		//在庫媒体区分
		if( bean.getZaikoBataiKb() != null && bean.getZaikoBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" zaiko_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getZaikoBataiKb() ) + "'");
		}
		//タグ媒体区分
// 2005.09.01 Inaba 項目修正 ZaikoBataiKb→TagBataiKb START
//		if( bean.getZaikoBataiKb() != null && bean.getZaikoBataiKb().trim().length() != 0 )
//		{
//			if( befKanma ) sb.append(","); else befKanma = true;
//			sb.append(" tag_batai_kb = ");
//			sb.append("'" + conv.convertString( bean.getZaikoBataiKb() ) + "'");
//		}
		if( bean.getTagBataiKb() != null && bean.getTagBataiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tag_batai_kb = ");
			sb.append("'" + conv.convertString( bean.getTagBataiKb() ) + "'");
		}
//		2005.09.01 Inaba 項目修正 ZaikoBataiKb→TagBataiKb START
		/******************** 新ER対応 *** 項目名変更 end ********************/

		if( bean.getSyukanhachuyoteiKb() != null && bean.getSyukanhachuyoteiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syukanhachuyotei_kb = ");
			sb.append("'" + conv.convertString( bean.getSyukanhachuyoteiKb() ) + "'");
		}
		if( bean.getTenmeisaiKb() != null && bean.getTenmeisaiKb().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" tenmeisai_kb = ");
			sb.append("'" + conv.convertString( bean.getTenmeisaiKb() ) + "'");
		}
		if( bean.getInsertTs() != null && bean.getInsertTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_ts = ");
			sb.append("'" + conv.convertString( bean.getInsertTs() ) + "'");
		}
		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" insert_user_id = ");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
		}
//      ↓↓移植(2006.05.12)↓↓
		if( bean.getUpdateTs() != null && bean.getUpdateTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_ts = ");
			//sb.append("'" + conv.convertString( bean.getUpdateTs() ) + "'");
			try {
			    	sb.append("'"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+ "'");
			   	}catch (SQLException e){
			        e.printStackTrace();
			   	}
		}
//      ↑↑移植(2006.05.12)↑↑
		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" update_user_id = ");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
		}
		if( bean.getDeleteFg() != null && bean.getDeleteFg().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" delete_fg = ");
			sb.append("'" + conv.convertString( bean.getDeleteFg() ) + "'");
		}
//		↓↓仕様変更（2005.07.28）↓↓
		if( bean.getSyokaiTorokuTs() != null && bean.getSyokaiTorokuTs().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_toroku_ts = ");
			sb.append("'" + conv.convertString( bean.getSyokaiTorokuTs() ) + "'");
		}
		if( bean.getSyokaiUserId() != null && bean.getSyokaiUserId().trim().length() != 0 )
		{
			if( befKanma ) sb.append(","); else befKanma = true;
			sb.append(" syokai_user_id = ");
			sb.append("'" + conv.convertString( bean.getSyokaiUserId() ) + "'");
		}
//		↑↑仕様変更（2005.07.28）↑↑


		sb.append(" WHERE");

//		 ↓↓2006.06.22 maojm カスタマイズ修正↓↓
//		if( bean.getKanriKb() != null && bean.getKanriKb().trim().length() != 0 )
//		{
//			whereAnd = true;
//			sb.append(" kanri_kb = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		}
//		if( bean.getKanriCd() != null && bean.getKanriCd().trim().length() != 0 )
//		{
//			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
//			sb.append(" kanri_cd = ");
//			sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
//		}
		whereAnd = true;
    	sb.append(" kanri_kb = ");
	    sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' ");

		if( bean.getBumonCd() != null && bean.getBumonCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" kanri_cd = ");
			sb.append("'" + StringUtility.charFormat( conv.convertWhereString( bean.getBumonCd()),4,"0",true) + "'");
		}

//		 ↑↑2006.06.22 maojm カスタマイズ修正↑↑
		if( bean.getShihaiKb() != null && bean.getShihaiKb().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" shihai_kb = ");
			sb.append("'" + conv.convertWhereString( bean.getShihaiKb() ) + "'");
		}
		if( bean.getShihaiCd() != null && bean.getShihaiCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" shihai_cd = ");
			sb.append("'" + conv.convertWhereString( bean.getShihaiCd() ) + "'");
		}
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" tenpo_cd = ");
//			↓↓2006.06.22 maojm カスタマイズ修正↓↓
//			sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
			sb.append("'" + StringUtility.charFormat( conv.convertWhereString(bean.getTenpoCd()),6,"000",true) + "'");
//		　　↑↑2006.06.22 maojm カスタマイズ修正↑↑
		}
		if( bean.getYukoDt() != null && bean.getYukoDt().trim().length() != 0 )
		{
			if( whereAnd ) sb.append(" AND "); else whereAnd = true;
			sb.append(" yuko_dt = ");
			sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
		mst480101_HakoKanriBean bean = (mst480101_HakoKanriBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append("R_HAKOKANRI where ");
//		 ↓↓2006.06.22 maojm カスタマイズ修正↓↓
//		sb.append(" kanri_kb = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriKb() ) + "'");
//		sb.append(" AND");
//		sb.append(" kanri_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getKanriCd() ) + "'");
		sb.append(" kanri_kb = ");
		sb.append("'" + mst000901_KanriKbDictionary.BUMON.getCode()+ "' ");
		sb.append(" AND ");
		sb.append(" kanri_cd = ");
		sb.append("'" + StringUtility.charFormat( conv.convertWhereString(bean.getBumonCd()),4,"0",true) + "'");
		sb.append(" AND");
		sb.append(" shihai_kb = ");
		sb.append("'" + conv.convertWhereString( bean.getShihaiKb() ) + "'");
		sb.append(" AND");
		sb.append(" shihai_cd = ");
		sb.append("'" + conv.convertWhereString( bean.getShihaiCd() ) + "'");
//		sb.append(" AND");
//		sb.append(" tenpo_cd = ");
//		sb.append("'" + conv.convertWhereString( bean.getTenpoCd() ) + "'");
		if( bean.getTenpoCd() != null && bean.getTenpoCd().trim().length() != 0 )
		 {	sb.append(" AND");
		    sb.append(" tenpo_cd = ");
		    sb.append("'" + StringUtility.charFormat( conv.convertWhereString(bean.getTenpoCd() ),6,"000",true) + "'");
		 }
//	　　↑↑2006.06.22 maojm カスタマイズ修正↑↑
		sb.append(" AND");
		sb.append(" yuko_dt = ");
		sb.append("'" + conv.convertWhereString( bean.getYukoDt() ) + "'");
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
}
