package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.common.util.DateUtility;
import mdware.common.util.StringUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;

/** * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstA90101_PsTanpinHakkouRequestDM extends DataModule
{
    private String syoriKb = null;// 処理状況
//	↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
	private String yukoDt = null; // 有効日（I/Fタイミング考慮）
//	↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * コンストラクタ
	 */
	public mstA90101_PsTanpinHakkouRequestDM()
	{
		super( "rbsite_ora");
	}

	/**
	 * コンストラクタ
	 */
	public mstA90101_PsTanpinHakkouRequestDM(String syoriKb)
	{
		super( "rbsite_ora");
		this.syoriKb = syoriKb;
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
		mstA90101_PsTanpinHakkouRequestBean bean = new mstA90101_PsTanpinHakkouRequestBean();
		if(mst000101_ConstDictionary.PROCESS_INSERT.equals(this.syoriKb)){
			// 登録データ作成時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setPcKb( encodingString(rest.getString("pc_kb")) );// PC発行区分
			bean.setBumonCd( encodingString(rest.getString("bumon_cd")) );// 部門コード
			bean.setSyokaiUserId( rest.getString("syokai_user_id") );// 初回登録社員ID
//			↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
			bean.setDaisiNoNb( encodingString(rest.getString("daisi_no_nb")) );// 台紙NO
//			↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
		} else {
			// 作成データ照会、或いは、作成データ取消時
//			↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
			bean.setSentaku(mst000101_ConstDictionary.SENTAKU_CHOICE);
//			↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
			bean.setSendDt( encodingString(rest.getString("send_dt")) );// 出力希望日
			bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na")) );// PS商品名
			bean.setDaisiNoNb( encodingString(rest.getString("daisi_no_nb")) );// 台紙NO
			bean.setPsMaisuQt( encodingString(rest.getString("ps_maisu_qt")) );// PS枚数
			bean.setUpdateTs( encodingString(rest.getString("update_ts") ));// 更新年月日
		}
		bean.setTenpoCd( encodingString(rest.getString("tenpo_cd")) );// 店舗コード
		bean.setTenpoNm( encodingString(rest.getString("tenpo_nm")) );// 店舗名称
		bean.setUnitCd( encodingString(rest.getString("unit_cd")) );// ユニットコード
		bean.setUnitNm( encodingString(rest.getString("unit_nm")) );// ユニット名称
		bean.setSyohinCd( encodingString(rest.getString("syohin_cd")) );// 商品コード
		bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na")) );// 商品名称
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		bean.setHinbanCd( encodingString(rest.getString("hinban_cd")) );// 品番コード
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		// ===BEGIN=== Add by kou 2006/10/3
//		↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
		if(mst000101_ConstDictionary.PROCESS_INSERT.equals(this.syoriKb)){
			if(rest.getString("system_kb") != null
				&& "J".equals(((String)rest.getString("system_kb")).trim()))
			{
//		↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
				if(rest.getString("pc_kb") != null
					&& "1".equals(((String)rest.getString("pc_kb")).trim()))
				{
					if(rest.getString("ps_syohin_na") != null
						&& rest.getString("ps_syohin_na").length() > 18) {
							bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na").substring(0,18)) );//商品名称
					}
				} else {
					if(rest.getString("ps_syohin_na") != null
						&& rest.getString("ps_syohin_na").length() > 12) {
							bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na").substring(0,12)) );//商品名称
					}
				}
//		↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
			} else {
				if(rest.getString("daisi_no_nb") != null
					&& "1".equals(((String)rest.getString("daisi_no_nb")).trim())) {
					if(rest.getString("ps_syohin_na") != null
						&& rest.getString("ps_syohin_na").length() > 14) {
							bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na").substring(0,14)) );//商品名称
					}
				} else if(rest.getString("daisi_no_nb") != null
							&& "2".equals(((String)rest.getString("daisi_no_nb")).trim())) {
					if(rest.getString("ps_syohin_na") != null
						&& rest.getString("ps_syohin_na").length() > 20) {
							bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na").substring(0,20)) );//商品名称
					}
				} else {
					bean.setDaisiNoNb("3");// 台紙NO
					if(rest.getString("ps_syohin_na") != null
						&& rest.getString("ps_syohin_na").length() > 23) {
							bean.setPsSyohinNa( encodingString(rest.getString("ps_syohin_na").substring(0,23)) );//商品名称
					}
				}
			}
		}
//		↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
		// ===END=== Add by kou 2006/10/3

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
		String sql = null;

//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		String sendDt = map.get("send_dt").toString();
//		↓↓2006.12.08 H.Yamamoto カスタマイズ修正↓↓
//		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
//			// 有効日＝送信日
//			yukoDt = sendDt;
//		} else {
//		↑↑2006.12.08 H.Yamamoto カスタマイズ修正↑↑
			String masterDt = DateChanger.addDate(sendDt, -1);
			int masterYobi = DateUtility.getDayOfWeek(masterDt);
			// 木曜締（日～木曜日）の翌サイクル月曜日処理
			if (masterYobi >= 1 && masterYobi <= 5) { // 翌週月曜日
				yukoDt = DateChanger.addDate(masterDt, 9 - masterYobi);
			} else { // 翌々週月曜日
				yukoDt = DateChanger.addDate(masterDt, 16 - masterYobi);
			}
//		↓↓2006.12.08 H.Yamamoto カスタマイズ修正↓↓
//		}
//		↑↑2006.12.08 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		if(mst000101_ConstDictionary.PROCESS_INSERT.equals(map.get("rb_syori_kb"))){
			// 登録データ作成時用ＳＱＬ
			sql = getSelectInsertSql(map);
		} else {
			// 作成データ照会、或いは、作成データ取消時用ＳＱＬ
			sql = getSelectDeleteSql(map);
		}
		return sql;
	}

	/**
	 * 作成データ照会、或いは、作成データ取消時用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectDeleteSql( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		//===BEGIN=== Add by kou 2006/10/31
		sb.append(" WITH TMP (YUKO_DT) AS ( ");
		sb.append(" 	SELECT MAX(YUKO_DT) FROM R_SYOHIN ");
		sb.append(" 	 WHERE ");
		sb.append(" 			BUMON_CD = '").append(map.get("bumon_cd")).append("'");
		sb.append(" 		AND SYOHIN_CD = '").append(map.get("syohin_cd")).append("'");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append(" 	AND YUKO_DT <= '").append(map.get("send_dt")).append("'");
//		} else {
//			sb.append(" 	AND YUKO_DT <= '").append(RMSTDATEUtil.getMstDateDt()).append("'");
//		}
		sb.append(" 	AND YUKO_DT <= '").append(this.yukoDt).append("'");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" ) ");
		//===END=== Add by kou 2006/10/31
		sb.append("select ");
		sb.append(" rt.send_dt as send_dt  ");
		sb.append(" , ");
		sb.append(" rt.tenpo_cd as tenpo_cd  ");
		sb.append(" , ");
		sb.append(" case rt.tenpo_cd when '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' then ");
		sb.append("   	'全店' ");
		sb.append(" else ");
		sb.append("   	rt.tenpo_nm ");
		sb.append(" end as tenpo_nm ");
		sb.append(" , ");
		sb.append(" rt.unit_cd as unit_cd  ");
		sb.append(" , ");
		sb.append(" rn.kanji_rn  as unit_nm  ");
		sb.append(" , ");
		sb.append(" trim(rt.syohin_cd) as syohin_cd  ");
//		↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rsn.system_kb as system_kb ");
		sb.append(" , ");
		sb.append(" rsn.pc_kb as pc_kb ");
//		↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rsn.hinban_cd as hinban_cd ");
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" , ");
		sb.append(" rt.ps_syohin_na as ps_syohin_na  ");
		sb.append(" , ");
		sb.append("trim( rt.daisi_no_nb) as daisi_no_nb  ");
		sb.append(" , ");
		sb.append(" rt.ps_maisu_qt as ps_maisu_qt  ");
		sb.append(" , ");
		sb.append(" rt.update_ts as update_ts  ");
		sb.append(" from  ");
		sb.append(" r_syohin rsn  ");
		//===BEGIN=== Modify by kou 2006/10/31
		//sb.append(" left outer join  r_namectf  rn ");
		//sb.append(" on  rn.code_cd =  rsn.system_kb||rsn.unit_cd ");
		//sb.append(" and  ");
		//sb.append(" rn.syubetu_no_cd= '"+mst000101_ConstDictionary.UNIT+"' ");
		//sb.append(" and  ");
		//sb.append(" rn.delete_fg= '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" , ");
		sb.append(" R_NAMECTF RN ");
		//===END=== Modify by kou 2006/10/31
		sb.append(" ,  ");
		sb.append(" (  ");
		sb.append("select ");
		sb.append(" trt.send_dt as send_dt ");
		sb.append(" , ");
		sb.append(" trt.tenpo_cd  as tenpo_cd ");
		sb.append(" , ");
		sb.append(" rs.bumon_cd  as bumon_cd ");
		sb.append(" , ");
		sb.append(" ( ");
		sb.append(" select ");
		sb.append(" kanji_rn ");
		sb.append(" from ");
		sb.append(" r_tenpo ");
		sb.append(" where ");
		sb.append(" tenpo_cd = trt.tenpo_cd ");
		sb.append(" and ");
		sb.append(" delete_fg= '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" )as tenpo_nm ");
		sb.append(" , ");
		sb.append(" rs.unit_cd  as unit_cd ");
		sb.append(" , ");
		sb.append(" trt.syohin_cd  as syohin_cd ");
		sb.append(" , ");
		sb.append(" trt.ps_syohin_na as ps_syohin_na ");
		sb.append(" , ");
		sb.append(" trt.daisi_no_nb as daisi_no_nb ");
		sb.append(" , ");
		sb.append(" trt.ps_maisu_qt as ps_maisu_qt ");
		sb.append(" , ");
		sb.append(" trt.update_ts as update_ts ");
		sb.append(" from ");
		sb.append(" r_syohin_taikei rst ");
		sb.append(" , ");
		sb.append(" r_syohin rs ");
		sb.append(" , ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" tr_ps_tana_request trt ");
		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
			sb.append(" tr_ps_tana_request trt ");
		} else {
			sb.append(" tr_ps_tana_request_gro trt ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" where ");
		sb.append(" rst.system_kb =  '"+ conv.convertWhereString( (String)map.get("system_kb") ) +"'");
		sb.append(" and ");
		sb.append(" rst.bumon_cd=  '"+ conv.convertWhereString( (String)map.get("bumon_cd") ) +"'");
		sb.append(" and ");
		sb.append(" rs.bumon_cd=rst.bumon_cd ");
		sb.append(" and ");
		sb.append(" rs.unit_cd=rst.unit_cd ");
		sb.append(" and ");
		sb.append(" rs.syohin_cd=  '"+ conv.convertWhereString( (String)map.get("syohin_cd") ) +"'");
		sb.append(" and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd, cast(null as char)) ");
		//===BEGIN=== Modify by kou 2006/10/31
		//if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		//{
		//	sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd,'" + (String)map.get("send_dt") + "') ");
		//} else {
		//	sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd, cast(null as char)) ");
		//}
		sb.append(" rs.yuko_dt = (SELECT YUKO_DT FROM TMP) ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" and ");
		sb.append(" rs.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		//===END=== Modify by kou 2006/10/31
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and ");
		sb.append(" trt.send_dt=  '"+ conv.convertWhereString( (String)map.get("send_dt") ) +"'");
		sb.append(" and ");
		sb.append(" trt.bumon_cd=rs.bumon_cd ");
		sb.append(" and ");
		sb.append(" trt.syohin_cd=rs.syohin_cd ");
		if(!mst000101_ConstDictionary.ALL_TENPO_CD.equals((String)map.get("tenpo_cd"))){
			sb.append(" and ");
			sb.append(" trt.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
		}
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" and ");
//		sb.append(" trt.ps_request_kb='1' ");
		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
			sb.append(" and ");
			sb.append(" trt.ps_request_kb='1' ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		if("J".equals(map.get("system_kb"))){
		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
			sb.append(" trt.ps_sofusaki_kb='0'");
		} else {
			sb.append(" trt.ps_sofusaki_kb='" + conv.convertWhereString( (String)map.get("ps_sofusaki_kb") ) + "'");
		}
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" and ");
//		if("J".equals(map.get("system_kb"))){
		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
			sb.append(" and ");
			sb.append(" trt.entry_kino_kb='3' ");
		} else {
//			↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//			sb.append(" trt.entry_kino_kb='1' ");
//			↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		}
		//===BEGIN=== Add by kou 2006/10/31
		sb.append(" and ");
		sb.append(" trt.entry_kb='0' ");
		//===END=== Add by kou 2006/10/31
		sb.append(" and ");
		sb.append(" trt.send_end_kb='0' ");
		sb.append(" and ");
		sb.append(" trt.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" ) rt  ");
		sb.append(" where  ");
		sb.append(" rsn.bumon_cd = rt.bumon_cd  ");
		sb.append(" and  ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd, cast(null as char))  ");
		//===BEGIN=== Modify by kou 2006/10/31
		//if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		//{
		//	sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd,'" + (String)map.get("send_dt") + "')  ");
		//} else {
		//	sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd, cast(null as char))  ");
		//}
		sb.append(" rsn.yuko_dt = (SELECT YUKO_DT FROM TMP) ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" and ");
		sb.append(" rsn.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		//===END=== Modify by kou 2006/10/31
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and  ");
		sb.append(" rsn.syohin_cd = rt.syohin_cd  ");
		//===BEGIN=== Add by kou 2006/10/31
		sb.append(" AND RSN.BUMON_CD ='").append(map.get("bumon_cd")).append("'");
		sb.append(" AND RSN.SYOHIN_CD = '").append(map.get("syohin_cd")).append("'");
		sb.append(" AND rn.code_cd =  rsn.system_kb||rsn.unit_cd ");
		sb.append(" AND  ");
		sb.append(" rn.syubetu_no_cd= '").append(MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)).append("' ");
		sb.append(" and  ");
		sb.append(" rn.delete_fg= '").append(conv.convertWhereString( (String)map.get("delete_fg") )).append("'");
		//===END=== Add by kou 2006/10/31
		//===BEGIN=== Comment out by kou 2006/10/31
		//sb.append(" order by ");
		//sb.append(" rt.tenpo_cd ");
		//sb.append(" , ");
		//sb.append(" rt.unit_cd ");
		//sb.append(" , ");
		//sb.append(" rt.syohin_cd ");
		//===END=== Comment out by kou 2006/10/31
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * @param string
	 * @return
	 */
	private String getTenpoCd(String string)
	{
		String tmp[] = string.split(",");
		String tenpoCd = "";
		for(int i=0; i<tmp.length; i++) {
			if(i==0) {
				tenpoCd = "'" + StringUtility.charFormat(tmp[i], 6, "0", true) +  "'";
			} else {
				tenpoCd = tenpoCd +  ",'" + StringUtility.charFormat(tmp[i], 6, "0", true) +  "'";
			}
		}
		return tenpoCd;
	}

	/**
	 * 登録データ作成時用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectInsertSql( Map map )
	{
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		//===BEGIN=== Add by kou 2006/10/31
		sb.append(" WITH TMP (YUKO_DT) AS ( ");
		sb.append(" 	SELECT MAX(YUKO_DT) FROM R_SYOHIN ");
		sb.append(" 	 WHERE ");
		sb.append(" 			BUMON_CD = '").append(map.get("bumon_cd")).append("'");
		sb.append(" 		AND SYOHIN_CD = '").append(map.get("syohin_cd")).append("'");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
//		{
//			sb.append(" 	AND YUKO_DT <= '").append(map.get("send_dt")).append("'");
//		} else {
//			sb.append(" 	AND YUKO_DT <= '").append(RMSTDATEUtil.getMstDateDt()).append("'");
//		}
		sb.append(" 	AND YUKO_DT <= '").append(this.yukoDt).append("'");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" ) ");
		//===END=== Add by kou 2006/10/31
		sb.append("select ");
		sb.append(" rt.tenpo_cd as tenpo_cd  ");
		sb.append(" , ");
		sb.append(" case rt.tenpo_cd when '" + mst000101_ConstDictionary.ALL_TENPO_CD + "' then ");
		sb.append("   	'全店' ");
		sb.append(" else ");
		sb.append("   	rt.tenpo_nm ");
		sb.append(" end as tenpo_nm ");
		sb.append(" , ");
		sb.append(" rt.bumon_cd as bumon_cd  ");
		sb.append(" , ");
		sb.append(" rt.unit_cd as unit_cd  ");
		sb.append(" , ");
		sb.append(" trim(rt.syohin_cd) as syohin_cd  ");
//		↓↓2006.10.11 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rsn.system_kb as system_kb ");
		sb.append(" , ");
		sb.append("	trim(rsn.daisi_no_nb) as daisi_no_nb ");
//		↑↑2006.10.11 H.Yamamoto カスタマイズ修正↑↑
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" , ");
		sb.append(" rsn.hinban_cd as hinban_cd ");
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" , ");
		sb.append(" rn.kanji_rn  as unit_nm  ");
		sb.append(" , ");
		sb.append(" rt.syohin_cd as syohin_cd  ");
		sb.append(" , ");
		sb.append(" rt.ps_syohin_na as ps_syohin_na  ");
		sb.append(" , ");
		sb.append(" rt.pc_kb as pc_kb  ");
		sb.append(" , ");
		sb.append(" rt.syokai_user_id as syokai_user_id  ");
		sb.append(" from  ");
		sb.append(" r_syohin rsn  ");
		sb.append(" left outer join  r_namectf  rn ");
		sb.append(" on  rn.code_cd =  rsn.system_kb||rsn.unit_cd ");
		sb.append(" and  ");
		sb.append(" rn.syubetu_no_cd= '"+MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal)+"' ");
		sb.append(" and  ");
		sb.append(" rn.delete_fg= '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" ,  ");
		sb.append(" (  ");
		sb.append("select ");
		sb.append(" rtt.tenpo_cd as tenpo_cd  ");
		sb.append(" , ");
		sb.append(" ( ");
		sb.append(" select ");
		sb.append(" kanji_rn ");
		sb.append(" from ");
		sb.append(" r_tenpo ");
		sb.append(" where ");
		sb.append(" tenpo_cd = rtt.tenpo_cd ");
		sb.append(" and ");
		sb.append(" delete_fg= '" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" )as tenpo_nm ");
		sb.append(" , ");
		sb.append(" rs.unit_cd as unit_cd ");
		sb.append(" , ");
		sb.append(" rtt.syohin_cd as syohin_cd ");
		sb.append(" , ");
		// ===BEGIN=== Add by kou 2006/10/3
		// POSレシート品名が空白の場合、商品の漢字品名で埋める
		//sb.append(" rs.rec_hinmei_kanji_na  as ps_syohin_na ");
		sb.append("	case when rs.rec_hinmei_kanji_na is null then rs.hinmei_kanji_na ");
		sb.append("	     when trim(rs.rec_hinmei_kanji_na) = '' then rs.hinmei_kanji_na ");
		sb.append("	     else rs.rec_hinmei_kanji_na end ps_syohin_na ");
		// ===END=== Add by kou 2006/10/3
		sb.append(" , ");
		sb.append(" rs.pc_kb as pc_kb ");
		sb.append(" , ");
		sb.append(" rs.bumon_cd as bumon_cd  ");
		sb.append(" , ");
		sb.append(" rs.syokai_user_id as syokai_user_id");
		sb.append(" from ");
		sb.append(" r_syohin_taikei rst ");
		sb.append(" , ");
		sb.append(" r_syohin rs ");
		sb.append(" , ");
		sb.append(" r_tanpinten_toriatukai rtt ");
		sb.append(" where ");
		sb.append(" rst.system_kb =  '"+ conv.convertWhereString( (String)map.get("system_kb") ) +"'");
		sb.append(" and ");
		sb.append(" rst.bumon_cd=  '"+ conv.convertWhereString( (String)map.get("bumon_cd") ) +"'");
		sb.append(" and ");
		sb.append(" rs.bumon_cd=rst.bumon_cd ");
		sb.append(" and ");
		sb.append(" rs.unit_cd=rst.unit_cd ");
		sb.append(" and ");
		sb.append(" rs.syohin_cd=  '"+ conv.convertWhereString( (String)map.get("syohin_cd") ) +"'");
		sb.append(" and ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd, cast(null as char)) ");
		//===BEGIN=== Modify by kou 2006/10/31
		//if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		//{
		//	sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd,'" + (String)map.get("send_dt") + "') ");
		//} else {
		//	sb.append(" rs.yuko_dt = msp710101_getsyohinyukodt(rs.bumon_cd ,rs.syohin_cd, cast(null as char)) ");
		//}
		sb.append(" rs.yuko_dt = (SELECT YUKO_DT FROM TMP) ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" and ");
		sb.append(" rs.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		//===END=== Modify by kou 2006/10/31
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and ");
		sb.append(" rtt.bumon_cd=  rs.bumon_cd ");
		sb.append(" and ");
		sb.append(" rtt.syohin_cd=  rs.syohin_cd ");
		if(!mst000101_ConstDictionary.ALL_TENPO_CD.equals((String)map.get("tenpo_cd"))){
			sb.append(" and ");
			//===BEGIN=== Modify by kou 2006/10/31
			//sb.append(" rtt.tenpo_cd = '" + conv.convertWhereString( (String)map.get("tenpo_cd") ) + "'");
			sb.append(" rtt.tenpo_cd IN (")
				.append(getTenpoCd((String)map.get("tenpo_cd"))).append(")");
			//===END=== Modify by kou 2006/10/31
		}
		sb.append(" and ");
		sb.append(" rtt.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "'");
		sb.append(" )rt  ");
		sb.append(" where  ");
		sb.append(" rsn.bumon_cd = rt.bumon_cd  ");
		sb.append(" and  ");
//		↓↓2006.08.28 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd, cast(null as char))  ");
		//===BEGIN=== Modify by kou 2006/10/31
		//if( map.get("send_dt") != null && ((String)map.get("send_dt")).trim().length() > 0 )
		//{
		//	sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd,'" + (String)map.get("send_dt") + "')  ");
		//} else {
		//	sb.append(" rsn.yuko_dt = msp710101_getsyohinyukodt(rsn.bumon_cd ,rsn.syohin_cd, cast(null as char))  ");
		//}
		sb.append(" rsn.yuko_dt = (SELECT YUKO_DT FROM TMP) ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" and ");
		sb.append(" rsn.delete_fg='" + conv.convertWhereString( (String)map.get("delete_fg") ) + "' ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		//===END=== Modify by kou 2006/10/31
//		↑↑2006.08.28 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" and  ");
		sb.append(" rsn.syohin_cd = rt.syohin_cd  ");
		sb.append(" order by ");
		sb.append(" rt.tenpo_cd ");
		sb.append(" , ");
		sb.append(" rt.unit_cd ");
		sb.append(" , ");
		sb.append(" rt.syohin_cd ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 更新年月日ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSelectUpdates( Map map )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" 	delete_fg, ");
		sb.append(" 	update_ts ");
		sb.append(" 	from ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" 	tr_ps_tana_request ");
		if("3".equals((String)map.get("entry_kino_kb"))){
			sb.append(" 	tr_ps_tana_request ");
		} else {
			sb.append(" 	tr_ps_tana_request_gro ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" 	where ");
		sb.append(" send_dt     = '" + conv.convertWhereString( (String)map.get("send_dt") ) +"' ");
		sb.append(" and bumon_cd = '" +  conv.convertWhereString( (String)map.get("bumon_cd") )+ "' ");
		sb.append(" and syohin_cd = '" +  conv.convertWhereString( (String)map.get("syohin_cd") ) + "' ");
		sb.append(" and tenpo_cd = '" +  conv.convertWhereString( (String)map.get("tenpo_cd") )+ "' ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" and ps_request_kb = '" +  conv.convertWhereString( (String)map.get("ps_request_kb") ) + "' ");
//		sb.append(" and ps_sofusaki_kb = '" +  conv.convertWhereString( (String)map.get("ps_sofusaki_kb") ) + "' ");
//		sb.append(" and entry_kino_kb = '" +   conv.convertWhereString( (String)map.get("entry_kino_kb") )+ "' ");
		if(mst000611_SystemKbDictionary.J.getCode().equals((String)map.get("system_kb"))){
			sb.append(" and ps_request_kb = '" +  conv.convertWhereString( (String)map.get("ps_request_kb") ) + "' ");
			sb.append(" and ps_sofusaki_kb = '" +  conv.convertWhereString( (String)map.get("ps_sofusaki_kb") ) + "' ");
			sb.append(" and entry_kino_kb = '" +   conv.convertWhereString( (String)map.get("entry_kino_kb") )+ "' ");
		} else {
			sb.append(" and entry_kb = '0' ");
			sb.append(" and ps_sofusaki_kb = '" +  conv.convertWhereString( (String)map.get("ps_sofusaki_kb") ) + "' ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		return sb.toString();
	}

	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
		mstA90101_PsTanpinHakkouRequestBean bean = (mstA90101_PsTanpinHakkouRequestBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	   tr_ps_tana_request ");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append("	   tr_ps_tana_request ");
		} else {
			sb.append("	   tr_ps_tana_request_gro ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" ( ");
		sb.append("	   send_dt ");
		sb.append(" , ");
		sb.append("	   bumon_cd ");
		sb.append(" , ");
		sb.append("	   syohin_cd ");
		sb.append(" , ");
		sb.append("	   tenpo_cd ");
		sb.append(" , ");
		sb.append("	   entry_kb ");
		sb.append(" , ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("	   ps_request_kb ");
//		sb.append(" , ");
//		sb.append("	   ps_sofusaki_kb ");
//		sb.append(" , ");
//		sb.append("	   entry_kino_kb ");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append("	   ps_request_kb ");
			sb.append(" , ");
			sb.append("	   ps_sofusaki_kb ");
			sb.append(" , ");
			sb.append("	   entry_kino_kb ");
		} else {
			sb.append("	   ps_sofusaki_kb ");
			sb.append(" , ");
			sb.append("	   gondora_nb ");
			sb.append(" , ");
			sb.append("	   tanadan_nb ");
			sb.append(" , ");
			sb.append("	   tanaichi_nb ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//		if( bean.getPsSyohinNa() != null && bean.getPsSyohinNa().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   ps_syohin_na ");
//		}
//		if( bean.getKaiPageKb() != null && bean.getKaiPageKb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   kai_page_kb ");
//		}
//		if( bean.getPcKb() != null && bean.getPcKb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   pc_kb ");
//		}
//		if( bean.getDaisiNoNb() != null && bean.getDaisiNoNb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   daisi_no_nb ");
//		}
//
//		sb.append(",");
//		sb.append("	   ps_maisu_qt ");
//		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   by_no ");
//		}
//		if( bean.getCommentTx() != null && bean.getCommentTx().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   comment_tx ");
//		}
//		//棚割外区分
//		if( bean.getTanawariGaiKb() != null && bean.getTanawariGaiKb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   tanawari_gai_kb ");
//		}
		sb.append(",");
		sb.append("	   ps_syohin_na ");
		sb.append(",");
		sb.append("	   kai_page_kb ");
		sb.append(",");
		sb.append("	   pc_kb ");
		sb.append(",");
		sb.append("	   daisi_no_nb ");
		sb.append(",");
		sb.append("	   ps_maisu_qt ");
		sb.append(",");
		sb.append("	   by_no ");
		sb.append(",");
		sb.append("	   comment_tx ");
		sb.append(",");
		sb.append("	   tanawari_gai_kb ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑

		//送信済区分
		if( bean.getSendEndKb() != null && bean.getSendEndKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("	   send_end_kb ");
		}
		//削除フラグ
		sb.append(",");
		sb.append("	   delete_fg ");
		//作成年月日
		sb.append(",");
		sb.append("	   insert_ts ");
		sb.append(",");
		sb.append(" insert_user_id");
		sb.append(",");
		sb.append(" update_ts");
		sb.append(",");
		sb.append(" update_user_id");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
//		//品番コード
//		if( bean.getHinbanCd() != null && bean.getHinbanCd().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   hinban_cd ");
//		}
//		if( bean.getDaityoKb() != null && bean.getDaityoKb().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   daityo_kb ");
//		}
//		if( bean.getIraiNo() != null && bean.getIraiNo().trim().length() != 0 )
//		{
//			sb.append(",");
//			sb.append("	   irai_no ");
//		}
		sb.append(",");
		sb.append("	   hinban_cd ");
		sb.append(",");
		sb.append("	   daityo_kb ");
		sb.append(",");
		sb.append("	   irai_no ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑

		sb.append(")Values(");
		sb.append("'" + conv.convertString( bean.getSendDt() ) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getBumonCd(),4,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getSyohinCd() ) + "'");
		sb.append(",");
		sb.append("'" +  conv.convertString(StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)) + "'");
		sb.append(",");
		sb.append("'" + conv.convertString( bean.getEntryKb() ) + "'");
		sb.append(",");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("'" + conv.convertString( bean.getPsRequestKb() ) + "'");
//		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getPsSofusakiKb() ) + "'");
//		sb.append(",");
//		sb.append("'" + conv.convertString( bean.getEntryKinoKb() ) + "'");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append("'" + conv.convertString( bean.getPsRequestKb() ) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPsSofusakiKb() ) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getEntryKinoKb() ) + "'");
		} else {
			sb.append("'" + conv.convertString( bean.getPsSofusakiKb() ) + "'");
			sb.append(",");
			sb.append("999");
			sb.append(",");
			sb.append("999");
			sb.append(",");
			sb.append("999");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑

		if( bean.getPsSyohinNa() != null && bean.getPsSyohinNa().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPsSyohinNa() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getKaiPageKb() != null && bean.getKaiPageKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getKaiPageKb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getPcKb() != null && bean.getPcKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getPcKb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getDaisiNoNb() != null && bean.getDaisiNoNb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDaisiNoNb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		sb.append(",");
		sb.append( bean.getPsMaisuQtString() );
		if( bean.getByNo() != null && bean.getByNo().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getByNo() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		if( bean.getCommentTx() != null && bean.getCommentTx().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getCommentTx() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		//棚割外区分
		if( bean.getTanawariGaiKb() != null && bean.getTanawariGaiKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getTanawariGaiKb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		//送信済区分
		if( bean.getSendEndKb() != null && bean.getSendEndKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getSendEndKb() ) + "'");
		}
		sb.append(",");
		sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");

		sb.append(",");
        try {
        	sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

		if( bean.getInsertUserId() != null && bean.getInsertUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getInsertUserId() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		sb.append(",");
        try {
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

		if( bean.getUpdateUserId() != null && bean.getUpdateUserId().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getHinbanCd() != null && bean.getHinbanCd().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getHinbanCd() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getDaityoKb() != null && bean.getDaityoKb().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getDaityoKb() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}

		if( bean.getIraiNo() != null && bean.getIraiNo().trim().length() != 0 )
		{
			sb.append(",");
			sb.append("'" + conv.convertString( bean.getIraiNo() ) + "'");
//		↓↓2006.10.16 H.Yamamoto カスタマイズ修正↓↓
		} else {
			sb.append(", null ");
//		↑↑2006.10.16 H.Yamamoto カスタマイズ修正↑↑
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName() );
		mstA90101_PsTanpinHakkouRequestBean bean = (mstA90101_PsTanpinHakkouRequestBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("tr_ps_tana_request set ");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append("tr_ps_tana_request set ");
		} else {
			sb.append("tr_ps_tana_request_gro set ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" update_ts = ");

        try {
		sb.append("'"+AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora")+"'");
        } catch (SQLException e) {
			e.printStackTrace();
		}

        sb.append(",");
		sb.append(" update_user_id = ");
		sb.append("'" + conv.convertString( bean.getUpdateUserId() ) + "'");

		sb.append(",");
		sb.append(" delete_fg = ");
		sb.append("'" +mst000801_DelFlagDictionary.IRU.getCode() + "'");

		sb.append(" where ");

		sb.append(" send_dt = ");
		sb.append("'" + bean.getSendDt()+ "'");
		sb.append(" and");
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" ps_request_kb = ");
//		sb.append("'" + bean.getPsRequestKb()+ "'");
//		sb.append(" and");
//		sb.append(" ps_sofusaki_kb = ");
//		sb.append("'" + bean.getPsSofusakiKb()+ "'");
//		sb.append(" and");
//		sb.append(" entry_kino_kb = ");
//		sb.append("'" + bean.getEntryKinoKb()+ "'");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append(" ps_request_kb = ");
			sb.append("'" + bean.getPsRequestKb()+ "'");
			sb.append(" and");
			sb.append(" ps_sofusaki_kb = ");
			sb.append("'" + bean.getPsSofusakiKb()+ "'");
			sb.append(" and");
			sb.append(" entry_kino_kb = ");
			sb.append("'" + bean.getEntryKinoKb()+ "'");
		} else {
			sb.append(" entry_kb = '0' ");
			sb.append(" and");
			sb.append(" ps_sofusaki_kb = ");
			sb.append("'" + bean.getPsSofusakiKb()+ "'");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	and ");
		sb.append("	delete_fg = '"+mst000801_DelFlagDictionary.INAI.getCode() +"' ");

		return sb.toString();
	}

	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mstA90101_PsTanpinHakkouRequestBean bean = (mstA90101_PsTanpinHakkouRequestBean)beanMst;
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append(" from ");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("tr_ps_tana_request ");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append("tr_ps_tana_request ");
		} else {
			sb.append("tr_ps_tana_request_gro ");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append(" where ");
		sb.append(" send_dt = ");
		sb.append("'" + bean.getSendDt()+ "'");
		sb.append(" and");
		sb.append(" bumon_cd=");
		sb.append("'"+StringUtility.charFormat(bean.getBumonCd(),4,"0",true) + "'");
		sb.append(" and");
		sb.append(" tenpo_cd = ");
		sb.append("'" + StringUtility.charFormat(bean.getTenpoCd(),6,"0",true)+ "'");
		sb.append(" and");
		sb.append(" syohin_cd = ");
		sb.append("'" + bean.getSyohinCd()+ "'");
		sb.append(" and");
//		↓↓2006.12.05 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" ps_request_kb = ");
//		sb.append("'" + bean.getPsRequestKb()+ "'");
//		sb.append(" and");
//		sb.append(" ps_sofusaki_kb = ");
//		sb.append("'" + bean.getPsSofusakiKb()+ "'");
//		sb.append(" and");
//		sb.append(" entry_kino_kb = ");
//		sb.append("'" + bean.getEntryKinoKb()+ "'");
		if(bean.getEntryKinoKb().equals("3")){
			sb.append(" ps_request_kb = ");
			sb.append("'" + bean.getPsRequestKb()+ "'");
			sb.append(" and");
			sb.append(" ps_sofusaki_kb = ");
			sb.append("'" + bean.getPsSofusakiKb()+ "'");
			sb.append(" and");
			sb.append(" entry_kino_kb = ");
			sb.append("'" + bean.getEntryKinoKb()+ "'");
		} else {
			sb.append(" entry_kb = '0' ");
			sb.append(" and");
			sb.append(" ps_sofusaki_kb = ");
			sb.append("'" + bean.getPsSofusakiKb()+ "'");
		}
//		↑↑2006.12.05 H.Yamamoto カスタマイズ修正↑↑
		sb.append("	and ");
		sb.append("	delete_fg = '"+mst000801_DelFlagDictionary.IRU.getCode() +"' ");

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
