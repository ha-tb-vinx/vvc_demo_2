package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品空番検索画面のDMクラス</p>
 * <p>説明: 商品空番検索画面のDMクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst250101_SyohinAkibanDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );

	/**
	 * コンストラクタ
	 */
	public mst250101_SyohinAkibanDM()
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
		mst250101_SyohinAkibanBean bean = new mst250101_SyohinAkibanBean();
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSentaku( rest.getString("sentaku") );
		bean.setSyohinCd( rest.getString("syohin_cd") );
		bean.setSyohinNa( rest.getString("syohin_na") );
		bean.setBumonCd( rest.getString("bumon_cd") );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setColorCd(rest.getString("color_cd") );
		bean.setColorNa(rest.getString("color_na") );
		bean.setSizeCd(rest.getString("size_cd") );
		bean.setSizeNa(rest.getString("size_na") );
		bean.setUnitCd(rest.getString("unit_cd") );
		bean.setUnitNa(rest.getString("unit_na") );
		bean.setSiiresakisyohinCd(rest.getString("siiresakisyohin_cd") );
		bean.setByNo(rest.getString("by_no") );
//		bean.setGyosyuKb( rest.getString("gyosyu_kb") );
//		bean.setHinsyuCd( rest.getString("hinsyu_cd") );
//		bean.setHinmeiKanjiNa( rest.getString("hinmei_kanji_na") );
		bean.setInsertTs( rest.getString("insert_ts") );
//		bean.setDeleteFg( rest.getString("delete_fg") );
		// 2005.10.03 状態区分追加
		bean.setJyotaiKb( rest.getString("jyotai_kb") );
		if (rest.getString("jyotai_kb").equals("登録済")) {
			bean.setUpdateTs( rest.getString("update_ts") );
			bean.setUpdateDate("");
		} else {
			bean.setUpdateTs("");
			bean.setUpdateDate(rest.getString("update_ts"));
		}
//		bean.setHinshuNa( rest.getString("hinshu_na") );
//		bean.setHankuNa( rest.getString("hanku_na") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
		bean.setCreateDatabase();
		// 2006/01/30 kim START
//		bean.setSyohin2Cd( rest.getString("syohin_2_cd"));   	//posコード
//		bean.setKikakuKanjiNa(rest.getString("kikaku_kanji_na")); //漢字規格
//		bean.setGentankaVl(rest.getLong("gentanka_vl"));	//原単価
//		bean.setBaitankaVl(rest.getLong("baitanka_vl"));	//販単価
//		bean.setSiiresakiCd(rest.getString("siiresaki_cd"));  //仕入先コード
//		bean.setSiiresakiNa(rest.getString("siiresaki_na"));  //仕入先名称
//		bean.setSiireHinbanCd(rest.getString("siire_hinban_cd")); //取引先品番
		// 2006/01/30 kim END
//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
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

//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
		//--------------------
		//検索条件の作成
		//--------------------
//		StringBuffer whereBuf = new StringBuffer();

//		String startSyohinCd = mst000401_LogicBean.chkNullString((String)map.get("startSyohinCd")).trim();
//		String checkCd = startSyohinCd + "0000";
//		String MasterDT = RMSTDATEUtil.getMstDateDt();
//		String yuko_dt = mst000401_LogicBean.chkNullString((String)map.get("yuko_dt")).trim();

//		 マスタ管理日付を取得する
		String MSTDATE = RMSTDATEUtil.getMstDateDt();

		//20060908  hiu@vjc  パフォーマンス改善対応start
		 String tempSyohinCd=null;
		if(map.containsKey("tempSyohinCd")){
			tempSyohinCd = mst000401_LogicBean.chkNullString((String)map.get("tempSyohinCd")).trim();
		}

		String frs =null;
	   if(map.containsKey("frs")){
		frs = mst000401_LogicBean.chkNullString((String)map.get("frs")).trim();
	   }

	   String tos=null;
	  if(map.containsKey("tos")){
		tos = mst000401_LogicBean.chkNullString((String)map.get("tos")).trim();
	  }

		//20060908  hiu@vjc  パフォーマンス改善対応end

//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
////		↓↓仕様変更（2006.01.31）↓↓
//		int selGyosyuCd = Integer.parseInt((String)map.get("sel_gyosyu_cd"));	// 業種選択画面で選択された業種コード
////		↑↑仕様変更（2006.01.31）↑↑
//		String strOrderBy = "";		// ソート条件
//
//		// コード 検索
//		// 2006/01/30 kim START POSコード追加
//		// POSコード
//		if (select.equals(mst250201_KeepBean.SELECT_POS) && startSyohinCd.length()!=0 )
//		{
//			whereBuf.append(" syohin_2_cd >= '" + startSyohinCd + "'");
//		// 2006/01/30 kim END
//
//		}
//		// 品種コード
//		else if(select.equals(mst250201_KeepBean.SELECT_HINSYU) && startSyohinCd.length()!=0)
//		{
////			↓↓仕様変更（2006.01.31）↓↓
////			whereBuf.append(" hinsyu_cd ='" + startSyohinCd + "'");
////			↓↓仕様変更（2005.08.11）↓↓
////			whereBuf.append(" and gyosyu_kb in (");
////			whereBuf.append("'" + mst000601_GyoshuKbDictionary.A08.getCode() + "'");
////			whereBuf.append(",'" + mst000601_GyoshuKbDictionary.A12.getCode() + "'");
////			whereBuf.append(",'" + mst000601_GyoshuKbDictionary.LIV.getCode() + "'");
////			whereBuf.append(")");
////			↑↑仕様変更（2005.08.11）↑↑
//
//			if( selGyosyuCd == 1 || selGyosyuCd == 2 )
//			{
//				//衣料・住生活
//				whereBuf.append(" syohin_cd >='" + startSyohinCd + "'");
//			}
//			else if( selGyosyuCd == 3 || selGyosyuCd == 4 )
//			{
//				//加工食品・生鮮食品
//				whereBuf.append(" hinsyu_cd >='" + startSyohinCd + "'");
//			}
////			↑↑仕様変更（2006.01.31）↑↑
//
//		}
//		// 取引先品番
//		else if(select.equals(mst250201_KeepBean.SELECT_HINBAN) && startSyohinCd.length()!=0)
//		{
//
////			↓↓仕様変更（2006.01.31）↓↓
////			whereBuf.append(" siire_hinban_cd >= '" + startSyohinCd + "'");
//			whereBuf.append(" siire_hinban_cd like '" + startSyohinCd + "%'");
////			↑↑仕様変更（2006.01.31）↑↑
//
//		}
//
//		// 2006/01/30 kim START
//		// 仕入先コード 条件
//		if (map.get("ct_siiresakicd")!= null && ((String)map.get("ct_siiresakicd")).trim().length()!=0){
//			// 2006/02/24 kim START
//			if (startSyohinCd.length() !=0)
//			// 2006/02/24 kim END
//				whereBuf.append(" and ");
//			whereBuf.append(" siiresaki_cd = '"+ conv.convertWhereString((String)map.get("ct_siiresakicd"))+"'");
//		}
//		// 「販区」条件
//		if (map.get("ct_kanricd")!= null && ((String)map.get("ct_kanricd")).trim().length()!=0){
//			whereBuf.append(" and ");
//			whereBuf.append(" hanku_cd= '"+conv.convertWhereString((String)map.get("ct_kanricd"))+"'");
//		}
//		// 2006/01/30 kim END
//
//		// ソート 条件
//		// POSコード
//		if (select.equals(mst250201_KeepBean.SELECT_POS))
//		{
//			strOrderBy = " syohin_2_cd ";
//		}
//		// 品種コード
//		else if(select.equals(mst250201_KeepBean.SELECT_HINSYU))
//		{
//			strOrderBy = " hinsyu_cd, syohin_cd ";
//		}
//		// その他
//		else
//		{
//			strOrderBy = " syohin_cd, hinsyu_cd, hanku_cd ";
//		}
//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑

		//--------------------
		//SQLの作成
		//--------------------
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
		StringBuffer sb = new StringBuffer();
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

		sb.append("SELECT ");
		sb.append("	'0' AS SENTAKU ");
		sb.append(" ,R_SYOHIN.BUMON_CD ");
		sb.append(" ,R_SYOHIN.SYOHIN_CD ");
		sb.append(" ,R_SYOHIN.YUKO_DT ");
		sb.append(" ,R_SYOHIN.HINMEI_KANJI_NA AS SYOHIN_NA ");
		sb.append(" ,R_SYOHIN.COLOR_CD ");
		sb.append("	,(SELECT ");
		sb.append("	KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.COLOR, userLocal) + "' ");
		sb.append("		AND CODE_CD = R_SYOHIN.COLOR_CD ");
		sb.append(" 	AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		sb.append("	) AS COLOR_NA ");
		sb.append(" ,R_SYOHIN.SIZE_CD ");
		sb.append("	,(SELECT ");
		sb.append("	KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.SIZE, userLocal) + "' ");
		sb.append("		AND CODE_CD = R_SYOHIN.SIZE_CD ");
		sb.append(" 	AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		sb.append("	) AS SIZE_NA ");
		sb.append(" ,R_SYOHIN.UNIT_CD ");
		sb.append("	,(SELECT ");
		sb.append("	KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI5, userLocal) + "' ");
		sb.append("		AND CODE_CD = R_SYOHIN.SYSTEM_KB || R_SYOHIN.UNIT_CD ");
		sb.append(" 	AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		sb.append("	) AS UNIT_NA ");
		sb.append(" ,R_SYOHIN.SIIRE_HINBAN_CD AS SIIRESAKISYOHIN_CD ");
		sb.append(" ,R_SYOHIN.SYOKAI_USER_ID AS BY_NO ");
		sb.append(" ,'登録済' AS JYOTAI_KB ");
		sb.append(" ,R_SYOHIN.INSERT_TS ");
//		sb.append(", (SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//		sb.append("	WHERE RIYO_USER_ID = R_SYOHIN.INSERT_USER_ID ");
//		sb.append("	) AS INSERT_USER_NA");
		sb.append(", (SELECT USER_NA FROM R_PORTAL_USER ");
		sb.append("	WHERE USER_ID = TRIM(R_SYOHIN.INSERT_USER_ID) ");
		sb.append("	) AS INSERT_USER_NA");
		sb.append(" ,R_SYOHIN.UPDATE_TS ");
//		sb.append(", (SELECT RIYO_USER_NA FROM R_RIYO_USER ");
//		sb.append("	WHERE RIYO_USER_ID = R_SYOHIN.UPDATE_USER_ID ");
//		sb.append("	) AS UPDATE_USER_NA");
		sb.append(", (SELECT USER_NA FROM R_PORTAL_USER ");
		sb.append("	WHERE USER_ID = TRIM(R_SYOHIN.UPDATE_USER_ID) ");
		sb.append("	) AS UPDATE_USER_NA");
		sb.append(" FROM ");
		sb.append("		 R_SYOHIN ");
		sb.append("	WHERE ");
		//20060908  hiu@vjc  パフォーマンス改善対応start
		if(tempSyohinCd!=null && (frs==null && tos==null)){
			sb.append("		R_SYOHIN.SYOHIN_CD = '" + tempSyohinCd + "'");
		}else if(frs!=null && tos!=null){
			sb.append("		(   FLOAT(R_SYOHIN.SYOHIN_CD) BETWEEN FLOAT('" + frs + "') AND FLOAT('"+tos+"')  ) ");
		}


		//20060908  hiu@vjc  パフォーマンス改善対応end

		//===BEGIN=== Modify by kou 2006/11/17
		//sb.append("	    AND	R_SYOHIN.YUKO_DT = MSP710101_GETSYOHINYUKODT(R_SYOHIN.BUMON_CD, R_SYOHIN.SYOHIN_CD, '" + MSTDATE + "') ");
		//sb.append("	    AND	R_SYOHIN.YUKO_DT = (SELECT MAX(T1.YUKO_DT) FROM R_SYOHIN T1 WHERE T1.BUMON_CD=R_SYOHIN.BUMON_CD AND T1.SYOHIN_CD=R_SYOHIN.SYOHIN_CD) ");
		//元へ戻る
		sb.append("	    AND	R_SYOHIN.YUKO_DT = MSP710101_GETSYOHINYUKODT(R_SYOHIN.BUMON_CD, R_SYOHIN.SYOHIN_CD, '").append(MSTDATE).append("') ");
		//===END=== Modify by kou 2006/11/17
//		↓↓仕様変更（2006.02.03）↓↓
//		sb.append("select ");
//		sb.append("* ");
//		sb.append("from ");
//		sb.append("( ");
////		↑↑仕様変更（2006.02.03）↑↑
//		sb.append("select  ");
//		sb.append("	syohin1.hanku_cd ");
//		sb.append("	,  ");
//		sb.append("	syohin1.syohin_cd ");
//		sb.append("	,  ");
//		sb.append("	syohin1.yuko_dt ");
//		sb.append("	,  ");
//		sb.append("	syohin1.gyosyu_kb ");
//		sb.append("	,  ");
//		sb.append("	syohin1.hinsyu_cd ");
//		sb.append("	,  ");
//		sb.append("	syohin1.hinmei_kanji_na ");
//		sb.append("	,  ");
//		// 2006/01/30 kim START
//		sb.append(" syohin1.syohin_2_cd ");
//		sb.append("	,	");
//		sb.append(" syohin1.gentanka_vl ");
//		sb.append("	,  ");
//		sb.append(" syohin1.baitanka_vl ");
//		sb.append("	,  ");
//		sb.append(" syohin1.siiresaki_cd ");
//		sb.append("	,  ");
//		sb.append(" syohin1.siire_hinban_cd ");
//		sb.append("	,  ");
//		sb.append(" syohin1.kikaku_kanji_na ");
//		sb.append("	,  ");
//		sb.append(" (select kanji_na from r_siiresaki where  kanri_kb = '"+mst000901_KanriKbDictionary.HANKU.getCode()+"' and kanri_cd = syohin1.hanku_cd and siiresaki_cd = syohin1.siiresaki_cd ) as siiresaki_na ");
//		sb.append("	,  ");
//		// 2006/01/30 kim END
//		sb.append("	substr(syohin1.insert_ts, 1, 8 ) as insert_ts ");
//		sb.append("	,  ");
//		sb.append("	substr(syohin1.update_ts, 1, 8 ) as update_ts ");
//		sb.append("	,  ");
//		sb.append("	syohin1.delete_fg ");
//		sb.append("	,  ");
//
//		// 状態区分を追加 2005.10.03
//		sb.append("	case when syohin1.delete_fg = '0' ");
//		sb.append("	     then '登録済' ");
//		sb.append("	     else '削除' ");
//		sb.append("	 end as jyotai_kb ");
//		sb.append("	,  ");
//
//		sb.append("	(select ");
//		sb.append("		kanji_rn ");
//		sb.append("	from ");
//		sb.append("		r_namectf ");
//		sb.append("	where ");
//		sb.append("		trim(code_cd) = trim(syohin1.hinsyu_cd) ");
//		sb.append("	and syubetu_no_cd = '" + mst000101_ConstDictionary.KIND + "' ");
//		sb.append("	and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "') as hinshu_na ");
//		sb.append("	,  ");
//		sb.append("	(select ");
//		sb.append("		kanji_rn ");
//		sb.append("	from ");
//		sb.append("		r_namectf ");
//		sb.append("	where ");
//		sb.append("		trim(code_cd) = trim(syohin1.hanku_cd) ");
//		sb.append("	and syubetu_no_cd = '" + mst000101_ConstDictionary.H_SALE + "' ");
//		sb.append("	and delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode() + "') as hanku_na ");
//		sb.append("	,  ");
//		sb.append("	(select user_na from sys_sosasya where syohin1.insert_user_id = user_id and hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "') AS insert_user_na ");
//		sb.append("	,  ");
//		sb.append("	(select user_na from sys_sosasya where syohin1.update_user_id = user_id and hojin_cd = '" + mst000101_ConstDictionary.HOJIN_CD + "') AS update_user_na ");
////		↓↓仕様変更（2006.02.03）↓↓
//		sb.append(" , ");
//		sb.append(" (select distinct d_gyosyu_cd from r_syohin_taikei where hanku_cd = syohin1.hanku_cd) d_gyosyu_cd ");
//		sb.append(" , ");
//		sb.append(" (select distinct s_gyosyu_cd from r_syohin_taikei where hanku_cd = syohin1.hanku_cd) s_gyosyu_cd ");
////		↑↑仕様変更（2006.02.03）↑↑
//		sb.append("from ");
//		sb.append("	r_syohin syohin1  ");
//// 　　↓↓移植（20060602）↓↓
//		//現在有効レコードもしくは予約レコード取得用SQL(現在有効レコード優先)
//		sb.append("	,(  ");
//		sb.append("	select  ");
//		sb.append("	  tb1.hanku_cd,tb1.syohin_cd,min(tb1.yuko_dt) as yuko_dt  ");
//		sb.append("	from  ");
//		sb.append("	  (select hanku_cd,syohin_cd,max(yuko_dt) as yuko_dt  ");
//		sb.append("	   from r_syohin  ");
////		↓↓仕様変更（2005.09.27）↓↓
////		sb.append("	   where yuko_dt <= to_char(sysdate,'yyyymmdd')   ");
//		sb.append("	   where yuko_dt <= '" + MasterDT + "'   ");
////		↑↑仕様変更（2005.09.27）↑↑
//		sb.append("	         and " + whereBuf.toString() + "  ");
//		sb.append("	   group by hanku_cd,syohin_cd  ");
//		sb.append("	   union  ");
//		sb.append("	   select hanku_cd,syohin_cd,min(yuko_dt) as yuko_dt  ");
//		sb.append("	   from r_syohin  ");
////		↓↓仕様変更（2005.09.27）↓↓
////		sb.append("	   where yuko_dt > to_char(sysdate,'yyyymmdd')  ");
//		sb.append("	   where yuko_dt > '" + MasterDT + "'  ");
////		↑↑仕様変更（2005.09.27）↑↑
//		sb.append("	         and " + whereBuf.toString() + "  ");
//		sb.append("	   group by hanku_cd,syohin_cd  ");
//		sb.append("	  )  tb1");
//		sb.append("	group by tb1.hanku_cd,tb1.syohin_cd  ");
//		sb.append("	)  syohin2  ");
//		sb.append("where   ");
//		sb.append(" syohin1.hanku_cd = syohin2.hanku_cd  ");
//		sb.append(" and syohin1.syohin_cd = syohin2.syohin_cd  ");
//		sb.append(" and syohin1.yuko_dt = syohin2.yuko_dt ");
//
////		↓↓仕様変更（2006.01.24）↓↓
//
//		sb.append(") tb2 ");
//
//		//where区の初期化
//		String whereStr = "where ";
//		String andStr = " and ";
//
//		// selGyosyuCd に対するWHERE区
//		if( selGyosyuCd == 1 )
//		{
//			//衣料
//			sb.append(whereStr);
//			sb.append("tb2.d_gyosyu_cd in ('0011','0044','0061','0057')");
//			whereStr = andStr;
//		}
//		else if( selGyosyuCd == 3 )
//		{
//			//加工食品
//			sb.append(whereStr);
//			sb.append("tb2.d_gyosyu_cd = '0033'");
//			sb.append(andStr);
//			sb.append("tb2.s_gyosyu_cd in ('0087','0088')");
//			whereStr = andStr;
//		}
//		else if( selGyosyuCd == 4 )
//		{
//			//生鮮食品
//			sb.append(whereStr);
//			sb.append("tb2.d_gyosyu_cd = '0033'");
//			sb.append(andStr);
//			sb.append("tb2.s_gyosyu_cd not in ('0087', '0088')");
//			whereStr = andStr;
//		}
//		else if( selGyosyuCd == 2 )
//		{
//			//住生活
//			sb.append(whereStr);
//			sb.append("tb2.d_gyosyu_cd not in ('0011', '0044', '0061', '0057', '0033')");
//			whereStr = andStr;
//		}
//		// 2006/02/01 kim START
////		sb.append("  and rownum < 10002 ");
//		// 2006/02/01 kim END
//
////		sb.append("order by syohin1.syohin_cd, syohin1.hinsyu_cd, syohin1.hanku_cd");
////		sb.append("order by syohin_cd, hinsyu_cd, hanku_cd");
//		sb.append("order by ");
//		sb.append( strOrderBy );
//		sb.append("  fetch first 10000 rows only ");
////     ↑↑移植（2006.06.02）↑↑
////		↑↑仕様変更（2006.01.24）↑↑
//
//System.out.println("mst250101_syohinAkibanDM  "+sb.toString());
//		↑↑2006.07.19 xiongjun カスタマイズ修正↑↑
		return sb.toString();
	}

//	↓↓2006.07.24 xiongjun カスタマイズ修正↓↓
	/**
	 * 採番マスタの検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanSelectSql( DataHolder dataHolder )
	{
		String select = mst000401_LogicBean.chkNullString((String)dataHolder.getParameter("select")).trim();
		String tempSyohinCd = mst000401_LogicBean.chkNullString((String)dataHolder.getParameter("tempSyohinCd")).trim();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		//20060922  hiu@vjc  パフォーマンス改善対応start
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("	R_SAIBAN_A08.SYOHIN_CD AS SYOHIN_CD, ");//0
			sb.append("	'' AS DUMMY_CD, ");//1
		} else {

			sb.append("	R_SAIBAN_A07.BY_SIKIBETU_NO AS BY_SIKIBETU_NO , ");//0
			sb.append("	R_SAIBAN_A07.RENBAN_CD AS RENBAN_CD , ");//1
		}
		//20060922  hiu@vjc  パフォーマンス改善対応end

		sb.append("	'0' AS SENTAKU ");//2
		sb.append("	,CAST(NULL AS CHAR) AS BUMON_CD ");//3
		sb.append(" ,CAST(NULL AS CHAR) AS YUKO_DT ");//4
		sb.append(" ,CAST(NULL AS CHAR) AS SYOHIN_NA ");//5
		sb.append(" ,CAST(NULL AS CHAR) AS COLOR_CD ");//6
		sb.append("	,CAST(NULL AS CHAR) AS COLOR_NA ");//7
		sb.append(" ,CAST(NULL AS CHAR) AS SIZE_CD ");//8
		sb.append("	,CAST(NULL AS CHAR) AS SIZE_NA ");//9
		sb.append(" ,CAST(NULL AS CHAR) AS UNIT_CD ");//10
		sb.append("	,CAST(NULL AS CHAR) AS UNIT_NA ");//11
		sb.append(" ,CAST(NULL AS CHAR) AS SIIRESAKISYOHIN_CD ");//12
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,CASE R_SAIBAN_A08.JYOTAI_KANRI_FG ");//13
			sb.append("    WHEN '5' THEN  ");
			sb.append("      '予約済'  ");
			sb.append("    WHEN '9' THEN  ");
			sb.append("      '廃番中'   ");
			sb.append("    ELSE  ");
			sb.append("      '空番' ");
			sb.append("    END AS JYOTAI_KB ");
		} else {
			sb.append(" ,CASE R_SAIBAN_A07.JYOTAI_KANRI_FG ");//13
			sb.append("    WHEN '5' THEN  ");
			sb.append("      '予約済'  ");
			sb.append("    WHEN '9' THEN  ");
			sb.append("      '廃番中'   ");
			sb.append("    ELSE  ");
			sb.append("      '空番' ");
			sb.append("    END AS JYOTAI_KB ");
		}
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,CASE R_SAIBAN_A08.JYOTAI_KANRI_FG ");//14
			sb.append("    WHEN '5' THEN  ");
			sb.append(" R_SAIBAN_A08.BY_NO ");
			sb.append("    ELSE  ");
			sb.append(" CAST(NULL AS CHAR) ");
			sb.append("    END AS BY_NO ");

		} else {
			sb.append(" ,CASE R_SAIBAN_A07.JYOTAI_KANRI_FG ");//14
			sb.append("    WHEN '5' THEN  ");
			sb.append(" R_SAIBAN_A07.BY_NO ");
			sb.append("    ELSE  ");
			sb.append(" CAST(NULL AS CHAR) ");
			sb.append("    END AS BY_NO ");
		}
		sb.append(" ,CAST(NULL AS CHAR) AS INSERT_TS ");//15
		sb.append(" ,CAST(NULL AS CHAR) AS INSERT_USER_NA ");//16
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,R_SAIBAN_A08.UPDATE_TS ");//17
		} else {
			sb.append(" ,R_SAIBAN_A07.UPDATE_TS ");//17
		}
		sb.append(" ,CAST(NULL AS CHAR) AS UPDATE_USER_NA ");//18
		sb.append(" FROM ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("		 R_SAIBAN_A08 ");
		} else {
			sb.append("		 R_SAIBAN_A07 ");
		}
		sb.append("	WHERE ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			//20060908  hiu@vjc  パフォーマンス改善対応start

			sb.append("	     '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = R_SAIBAN_A08.DELETE_FG ");

			//20060908  hiu@vjc  パフォーマンス改善対応end

		} else {
			//20060908  hiu@vjc  パフォーマンス改善対応start
			sb.append("	     '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = R_SAIBAN_A07.DELETE_FG ");
			//20060908  hiu@vjc  パフォーマンス改善対応end

		}
		//20060922  hiu@vjc  パフォーマンス改善対応start

		String fr=dataHolder.getParameter("frs");
		String to=dataHolder.getParameter("tos");

		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("		AND R_SAIBAN_A08.SYOHIN_CD BETWEEN '" + fr.substring(0,7) + "' AND '"+to.substring(0,7)+"'");

		} else {
			sb.append("		AND (R_SAIBAN_A07.BY_SIKIBETU_NO BETWEEN '" + fr.substring(0,2) + "' AND '"+to.substring(0,2)+"' ) ");
			sb.append("		AND R_SAIBAN_A07.RENBAN_CD BETWEEN '" + fr.substring(2,6) + "' AND '"+to.substring(2,6)+"'");

		}
		//20060922  hiu@vjc  パフォーマンス改善対応end
		return sb.toString();
	}

	/**
	 * 採番マスタの検索用ＳＱＬの生成を行う。
	 * 渡されたMapを元にWHERE区を作成する。
	 * @param map Map
	 * @return String 生成されたＳＱＬ
	 */
	public String getSaibanSelectSqlWithSyohinCode( DataHolder dataHolder )
	{
		String select = mst000401_LogicBean.chkNullString((String)dataHolder.getParameter("select")).trim();
		String tempSyohinCd = mst000401_LogicBean.chkNullString((String)dataHolder.getParameter("tempSyohinCd")).trim();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	'0' AS SENTAKU ");
		sb.append("	,CAST(NULL AS CHAR) AS BUMON_CD ");
		sb.append(" ,CAST(NULL AS CHAR) AS YUKO_DT ");
		sb.append(" ,CAST(NULL AS CHAR) AS SYOHIN_NA ");
		sb.append(" ,CAST(NULL AS CHAR) AS COLOR_CD ");
		sb.append("	,CAST(NULL AS CHAR) AS COLOR_NA ");
		sb.append(" ,CAST(NULL AS CHAR) AS SIZE_CD ");
		sb.append("	,CAST(NULL AS CHAR) AS SIZE_NA ");
		sb.append(" ,CAST(NULL AS CHAR) AS UNIT_CD ");
		sb.append("	,CAST(NULL AS CHAR) AS UNIT_NA ");
		sb.append(" ,CAST(NULL AS CHAR) AS SIIRESAKISYOHIN_CD ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,CASE R_SAIBAN_A08.JYOTAI_KANRI_FG ");
			sb.append("    WHEN '5' THEN  ");
			sb.append("      '予約済'  ");
			sb.append("    WHEN '9' THEN  ");
			sb.append("      '廃番中'   ");
			sb.append("    ELSE  ");
			sb.append("      '空番' ");
			sb.append("    END AS JYOTAI_KB ");
		} else {
			sb.append(" ,CASE R_SAIBAN_A07.JYOTAI_KANRI_FG ");
			sb.append("    WHEN '5' THEN  ");
			sb.append("      '予約済'  ");
			sb.append("    WHEN '9' THEN  ");
			sb.append("      '廃番中'   ");
			sb.append("    ELSE  ");
			sb.append("      '空番' ");
			sb.append("    END AS JYOTAI_KB ");
		}
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,CASE R_SAIBAN_A08.JYOTAI_KANRI_FG ");
			sb.append("    WHEN '5' THEN  ");
			sb.append(" R_SAIBAN_A08.BY_NO ");
			sb.append("    ELSE  ");
			sb.append(" CAST(NULL AS CHAR) ");
			sb.append("    END AS BY_NO ");

		} else {
			sb.append(" ,CASE R_SAIBAN_A07.JYOTAI_KANRI_FG ");
			sb.append("    WHEN '5' THEN  ");
			sb.append(" R_SAIBAN_A07.BY_NO ");
			sb.append("    ELSE  ");
			sb.append(" CAST(NULL AS CHAR) ");
			sb.append("    END AS BY_NO ");
		}
		sb.append(" ,CAST(NULL AS CHAR) AS INSERT_TS ");
		sb.append(" ,CAST(NULL AS CHAR) AS INSERT_USER_NA ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append(" ,R_SAIBAN_A08.UPDATE_TS ");
		} else {
			sb.append(" ,R_SAIBAN_A07.UPDATE_TS ");
		}
		sb.append(" ,CAST(NULL AS CHAR) AS UPDATE_USER_NA ");
		sb.append(" FROM ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("		 R_SAIBAN_A08 ");
		} else {
			sb.append("		 R_SAIBAN_A07 ");
		}
		sb.append("	WHERE ");
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("		R_SAIBAN_A08.SYOHIN_CD = '" + tempSyohinCd.substring(0,7) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = R_SAIBAN_A08.DELETE_FG ");
		} else {
			sb.append("		R_SAIBAN_A07.BY_SIKIBETU_NO = '" + tempSyohinCd.substring(0,2) + "'");
			sb.append("		AND R_SAIBAN_A07.RENBAN_CD = '" + tempSyohinCd.substring(2,6) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = R_SAIBAN_A07.DELETE_FG ");
		}
		return sb.toString();
	}


//	↑↑2006.07.24 xiongjun カスタマイズ修正↑↑

//	↓↓2006.07.25 xiongjun カスタマイズ修正↓↓
	/**
	 * 挿入用ＳＱＬの生成を行う。
	 */
	public String getInsertSql( Object beanMst )
	{

		mst250101_SyohinAkibanBean bean = (mst250101_SyohinAkibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		String select = bean.getSelect();
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("INSERT INTO ");
			sb.append("R_SAIBAN_A08 (");
			sb.append(" SYOHIN_CD");
			sb.append(" ,CHECK_DEGIT_CD");
			sb.append(" ,JYOTAI_KANRI_FG");
			sb.append(" ,BY_NO");
			sb.append(" ,COMMENT_TX");
			sb.append(" ,HAIBAN_DT");
			sb.append(" ,YOYAKU_DT");
			sb.append(" ,DELETE_FG");
			sb.append(" ,INSERT_TS");
			sb.append(" ,INSERT_USER_ID");
			sb.append(" ,UPDATE_TS");
			sb.append(" ,UPDATE_USER_ID");
			sb.append(" )VALUES(");
			sb.append("'" + conv.convertString(bean.getSyohinCd().substring(0,7)) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSyohinCd().substring(7,8)) + "'");
			sb.append(",");
			sb.append("'5'");
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getSelByNo().toString(),10,"0",true)) + "'");
			sb.append(",");
			sb.append("NULL");
			sb.append(",");
			sb.append("NULL");
			sb.append(",");
			sb.append("'" + RMSTDATEUtil.getMstDateDt() + "'");
			sb.append(",");
			sb.append("'" +mst000801_DelFlagDictionary.INAI.getCode() + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getInsertTs()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getInsertUserNa()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(")");
		}
		if (select.equals(mst250201_KeepBean.SELECT_JITUYOU)) {
			sb.append("INSERT INTO ");
			sb.append("R_SAIBAN_A07 (");
			sb.append(" BY_SIKIBETU_NO");
			sb.append(" ,RENBAN_CD");
			sb.append(" ,CHECK_DEGIT_CD");
			sb.append(" ,JYOTAI_KANRI_FG");
			sb.append(" ,BY_NO");
			sb.append(" ,COMMENT_TX");
			sb.append(" ,HAIBAN_DT");
			sb.append(" ,YOYAKU_DT");
			sb.append(" ,DELETE_FG");
			sb.append(" ,INSERT_TS");
			sb.append(" ,INSERT_USER_ID");
			sb.append(" ,UPDATE_TS");
			sb.append(" ,UPDATE_USER_ID");
			sb.append(" )VALUES(");
			sb.append("'" + conv.convertString(bean.getSyohinCd().substring(0,2)) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSyohinCd().substring(2,6)) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSyohinCd().substring(6,7)) + "'");
			sb.append(",");
			sb.append("'5'");
			sb.append(",");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getSelByNo().toString(),10,"0",true)) + "'");
			sb.append(",");
			sb.append("NULL");
			sb.append(",");
			sb.append("NULL");
			sb.append(",");
			sb.append("'" + RMSTDATEUtil.getMstDateDt() + "'");
			sb.append(",");
			sb.append("'" + mst000801_DelFlagDictionary.INAI.getCode() + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getInsertTs()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getInsertUserNa()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(")");
		}

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。
	 */
	public String getUpdateSql( Object beanMst )
	{
		mst250101_SyohinAkibanBean bean = (mst250101_SyohinAkibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		String select = bean.getSelect();
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("UPDATE ");
			sb.append("R_SAIBAN_A08 SET");
			sb.append(" JYOTAI_KANRI_FG  = NULL ");
			sb.append(" ,YOYAKU_DT = NULL ");
			sb.append(" ,DELETE_FG = ");
			sb.append("'" + mst000801_DelFlagDictionary.IRU.getCode() + "'");
			sb.append(" ,UPDATE_TS = ");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(" ,UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(" WHERE");
			sb.append("		SYOHIN_CD = '" + conv.convertString(bean.getSyohinCd().substring(0,7)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
			sb.append("	    AND '5' = JYOTAI_KANRI_FG ");
		}
		if (select.equals(mst250201_KeepBean.SELECT_JITUYOU)) {
			sb.append("UPDATE ");
			sb.append("R_SAIBAN_A07 SET");
			sb.append(" JYOTAI_KANRI_FG = NULL ");
			sb.append(" ,YOYAKU_DT = NULL ");
			sb.append(" ,DELETE_FG = ");
			sb.append("'" + mst000801_DelFlagDictionary.IRU.getCode() + "'");
			sb.append(" ,UPDATE_TS = ");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(" ,UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(" WHERE");
			sb.append("		BY_SIKIBETU_NO = '" + conv.convertString(bean.getSyohinCd().substring(0,2)) + "'");
			sb.append("		AND RENBAN_CD = '" + conv.convertString(bean.getSyohinCd().substring(2,6)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
			sb.append("	    AND '5' = JYOTAI_KANRI_FG ");
		}

		return sb.toString();
	}

	/**
	 * 更新用ＳＱＬの生成を行う。予約済に更新 add by kema 06.09.18
	 */
	public String getUpdateYoyakuSql( Object beanMst )
	{
		mst250101_SyohinAkibanBean bean = (mst250101_SyohinAkibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		String select = bean.getSelect();
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("UPDATE ");
			sb.append("R_SAIBAN_A08 SET");
			sb.append(" JYOTAI_KANRI_FG  = '5' ");
			sb.append(" ,YOYAKU_DT = ");
			sb.append("'" + RMSTDATEUtil.getMstDateDt() + "'");
			sb.append(" ,BY_NO = ");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getSelByNo().toString(),10,"0",true)) + "'");
			sb.append(" ,UPDATE_TS = ");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(" ,UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(" WHERE");
			sb.append("		SYOHIN_CD = '" + conv.convertString(bean.getSyohinCd().substring(0,7)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		}
		if (select.equals(mst250201_KeepBean.SELECT_JITUYOU)) {
			sb.append("UPDATE ");
			sb.append("R_SAIBAN_A07 SET");
			sb.append(" JYOTAI_KANRI_FG  = '5' ");
			sb.append(" ,YOYAKU_DT = ");
			sb.append("'" + RMSTDATEUtil.getMstDateDt() + "'");
			sb.append(" ,BY_NO = ");
			sb.append("'" + conv.convertString(StringUtility.charFormat(bean.getSelByNo().toString(),10,"0",true)) + "'");
			sb.append(" ,UPDATE_TS = ");
			sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
			sb.append(" ,UPDATE_USER_ID = ");
			sb.append("'" + conv.convertString(bean.getUpdateUserNa()) + "'");
			sb.append(" WHERE");
			sb.append("		BY_SIKIBETU_NO = '" + conv.convertString(bean.getSyohinCd().substring(0,2)) + "'");
			sb.append("		AND RENBAN_CD = '" + conv.convertString(bean.getSyohinCd().substring(2,6)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.INAI.getCode()	+ "' = DELETE_FG ");
		}

		return sb.toString();
	}
	/**
	 * 削除用ＳＱＬの生成を行う。
	 */
	public String getDeleteSql( Object beanMst )
	{
		mst250101_SyohinAkibanBean bean = (mst250101_SyohinAkibanBean)beanMst;
		StringBuffer sb = new StringBuffer();
		String select = bean.getSelect();
		if (select.equals(mst250201_KeepBean.SELECT_TAGU)) {
			sb.append("DELETE FROM ");
			sb.append("R_SAIBAN_A08 WHERE ");
			sb.append("		SYOHIN_CD = '" + conv.convertString(bean.getSyohinCd().substring(0,7)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.IRU.getCode()	+ "' = DELETE_FG ");
		}
		if (select.equals(mst250201_KeepBean.SELECT_JITUYOU)) {
			sb.append("DELETE FROM ");
			sb.append("R_SAIBAN_A07 WHERE ");
			sb.append("		BY_SIKIBETU_NO = '" + conv.convertString(bean.getSyohinCd().substring(0,2)) + "'");
			sb.append("		AND RENBAN_CD = '" + conv.convertString(bean.getSyohinCd().substring(2,6)) + "'");
			sb.append("	    AND '" + mst000801_DelFlagDictionary.IRU.getCode()	+ "' = DELETE_FG ");
		}

		return sb.toString();
	}
//	↑↑2006.07.25 xiongjun カスタマイズ修正↑↑

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

//	↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//	/**
//	 * 空番行を追加する
//	 *
//	 * @param select  選択 0:02コード 1:04コード 2:品種コード 3:取引先品番
//	 * @param orgList 取得データ明細行
//	 * @return	List 空番追加した明細行
//	 *
//	 */
///*
//	public List getAkibanRow(String select , List orgList, mst000101_DbmsBean db) throws Exception{
//
//		List addList = new ArrayList();
//		List akibanList = new ArrayList();
//
//		String firstSyohinCd = "";
//		String firstExSyohinCd = "";
//
//		long firstCd = 0;
//		long firstExCd = 0;
//
//		for (int i = 0; i < orgList.size(); i++) {
//
//			//-------------------------
//			//該当行を追加
//			//-------------------------
//			mst250101_SyohinAkibanBean startBean = (mst250101_SyohinAkibanBean)orgList.get(i);
//
////			↓↓仕様変更（2005.10.08）↓↓　空番を単品 "000" ～ "999" まで取得
//			if ( i == 0 ) {
//
//				if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,4) + "000";
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//
//				} else if (select.equals(mst250201_KeepBean.SELECT_04)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,8) + "000";
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,11);
//
//				} else if (select.equals(mst250201_KeepBean.SELECT_HINSYU)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,4) + "000";
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//
//				}
//
//				firstCd = Long.parseLong(firstSyohinCd);
//				firstExCd = Long.parseLong(firstExSyohinCd);
//
//				addList = getAkibanList(db, select, akibanList, firstCd, firstExCd);
//			}
////			↑↑仕様変更（2005.10.08）↑↑　空番を単品 "000" ～ "999" まで取得
//
//			addList.add(startBean);
//
//			if (addList.size() > mst000101_ConstDictionary.SEARCH__MAX_LIMIT) {
//				return null;
//			}
//
//			//-------------------------
//			//最終行以外は次行を取得し空連番が存在する場合、空行作成
//			//-------------------------
//			if(i != orgList.size() - 1){
//
//				mst250101_SyohinAkibanBean nextBean = (mst250101_SyohinAkibanBean)orgList.get(i + 1);
//
//				//商品コード取得（連番対応）
//				long startCd = 0;
//				long nextCd = 0;
//
//				//02コード
//				if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//					String startSyohinCd = startBean.getSyohinCd().trim() + "0000000";
//					startCd = Long.parseLong(startSyohinCd.substring(0,7));
//
//					String nextSyohinCd = nextBean.getSyohinCd().trim() + "0000000";
//					nextCd = Long.parseLong(nextSyohinCd.substring(0,7));
//
//				//04コード
//				} else if(select.equals(mst250201_KeepBean.SELECT_04)){
//
//					String startSyohinCd = startBean.getSyohinCd().trim() + "00000000000";
//					startCd = Long.parseLong(startSyohinCd.substring(0,11));
//
//					String nextSyohinCd = nextBean.getSyohinCd().trim() + "00000000000";
//					nextCd = Long.parseLong(nextSyohinCd.substring(0,11));
//
//				//品種コード
//				} else if(select.equals(mst250201_KeepBean.SELECT_HINSYU)){
//
//					String startSyohinCd = startBean.getSyohinCd().trim() + "0000000";
//					startCd = Long.parseLong(startSyohinCd.substring(0,7));
//
//					String nextSyohinCd = nextBean.getSyohinCd().trim() + "0000000";
//					nextCd = Long.parseLong(nextSyohinCd.substring(0,7));
//
//				//02、04、品種コード以外は空番追加なしで返す
//				} else {
//					return orgList;
//				}
//
//				//空番行を追加
//				for (long j = startCd + 1 ; j < nextCd; j++) {
//
//					mst250101_SyohinAkibanBean akiBean = new mst250101_SyohinAkibanBean();
//
//					String akiSyohinCd = "";
//
//					//02コード
//					if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//						akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7) + "00000";
//						akiSyohinCd = mst001401_CheckDegitBean.getModulus10Code(akiSyohinCd,12);
//
//						// 2005.10.03 状態区分追加対応 START -----
//						ResultSet rset = null;
//						StringBuffer sb = new StringBuffer();
//
//						// データ存在チェック
//						sb.append(" SELECT ");
//						sb.append(" 	JYOTAI_KANRI_KB, ");
//						sb.append(" 	DELETE_FG ");
//						sb.append("   FROM ");
//						sb.append("     R_SAIBAN_INSTORE ");
//						sb.append("  WHERE ");
//						sb.append(" 	   TANPIN_CD    	= '" + akiSyohinCd.substring(2,7) + "'");
//
//						rset = db.executeQuery(sb.toString());
//
//						// 存在時
//						if (rset.next()) {
//
//							// 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							if(	rset.getString("DELETE_FG").equals("0")
//								&& (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									akiBean.setJyotaiKb("仮登録");
//
//							}else{
//
//								akiBean.setJyotaiKb("未登録");
//							}
//
//						// 存在しない場合は未登録扱い
//						}else{
//
//							akiBean.setJyotaiKb("未登録");
//						}
//
//						if(rset != null){
//							rset.close();
//						}
//
//						// 2005.10.03 状態区分追加対応 END -----
//
//					//04コード
//					} else if(select.equals(mst250201_KeepBean.SELECT_04)){
//
//						akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),11);
//						akiSyohinCd = akiSyohinCd.substring(4,11);
//						akiSyohinCd = mst001401_CheckDegitBean.getInstore0400Code(akiSyohinCd,12);
//
//						// 2005.10.03 状態区分追加対応 START -----
//						ResultSet rset = null;
//						StringBuffer sb = new StringBuffer();
//
//						// データ存在チェック
//						sb.append(" SELECT ");
//						sb.append(" 	JYOTAI_KANRI_KB, ");
//						sb.append(" 	DELETE_FG ");
//						sb.append("   FROM ");
//						sb.append("     R_SAIBAN_8KETA ");
//						sb.append("  WHERE ");
//						sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(4,8) + "'");
//						sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(8,11) + "'");
//
//						rset = db.executeQuery(sb.toString());
//
//						// 存在時
//						if (rset.next()) {
//
//							// 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							if(	rset.getString("DELETE_FG").equals("0")
//								&& (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									akiBean.setJyotaiKb("仮登録");
//
//							}else{
//
//								akiBean.setJyotaiKb("未登録");
//							}
//
//						// 存在しない場合は未登録扱い
//						}else{
//
//							akiBean.setJyotaiKb("未登録");
//						}
//
//						if(rset != null){
//							rset.close();
//						}
//
//						// 2005.10.03 状態区分追加対応 END -----
//
//
//					//品種コード
//					} else if(select.equals(mst250201_KeepBean.SELECT_HINSYU)){
//
//						akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7);
//						akiSyohinCd = mst001401_CheckDegitBean.getModulus11Code(akiSyohinCd,7);
//
//						// 2005.10.03 状態区分追加対応 START -----
//						ResultSet rset = null;
//						StringBuffer sb = new StringBuffer();
//
//						// データ存在チェック
//						sb.append(" SELECT ");
//						sb.append(" 	JYOTAI_KANRI_KB, ");
//						sb.append(" 	DELETE_FG ");
//						sb.append("   FROM ");
//						sb.append("     R_SAIBAN_8KETA ");
//						sb.append("  WHERE ");
//						sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(0,4) + "'");
//						sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(4,7) + "'");
//
//						rset = db.executeQuery(sb.toString());
//
//						// 存在時
//						if (rset.next()) {
//
//							// 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							if(	rset.getString("DELETE_FG").equals("0")
//								&& (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									akiBean.setJyotaiKb("仮登録");
//
//							}else{
//
//								akiBean.setJyotaiKb("未登録");
//							}
//
//						// 存在しない場合は未登録扱い
//						}else{
//
//							akiBean.setJyotaiKb("未登録");
//						}
//
//						if(rset != null){
//							rset.close();
//						}
//
//						// 2005.10.03 状態区分追加対応 END -----
//					}
//
//					akiBean.setSyohinCd(akiSyohinCd);
//					addList.add(akiBean);
//
//					if (addList.size() > mst000101_ConstDictionary.SEARCH__MAX_LIMIT) {
//						return null;
//					}
//				}
////			↓↓仕様変更（2005.10.08）↓↓　空番を単品 "000" ～ "999" まで取得
//			} else if (i == orgList.size() - 1) {
//				if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,4) + "999";
//
//				} else if (select.equals(mst250201_KeepBean.SELECT_04)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,11);
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,8) + "999";
//
//				} else if (select.equals(mst250201_KeepBean.SELECT_HINSYU)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,4) + "999";
//
//				}
//
//				firstCd = Long.parseLong(firstSyohinCd);
//				firstExCd = Long.parseLong(firstExSyohinCd);
//
//				addList = getAkibanList(db, select, akibanList, firstCd+1, firstExCd+1);
//			}
////			↑↑仕様変更（2005.10.08）↑↑　空番を単品 "000" ～ "999" まで取得
//		}
//		return addList;
//	}
//*/
////	↓↓仕様変更（2005.10.08）↓↓　空番を単品 "000" ～ "999" まで取得
//	public List getAkibanRow(String select , List orgList, mst000101_DbmsBean db, String inputSyohinCd, String selectGyosyu) throws Exception{
//
//		List addList = new ArrayList();
//		List akibanList = new ArrayList();
//
//		String firstSyohinCd = "";
//		String firstExSyohinCd = "";
//
//		long firstCd = 0;
//		long firstExCd = 0;
//
//		int inputLength = inputSyohinCd.trim().length();
//
//
////		↓↓仕様変更（2005.10.16）↓↓
//		String gyosyuCd = "";
//		if (selectGyosyu.equals("01")) {
//			gyosyuCd = "A08";
//		} else if (selectGyosyu.equals("02")) {
//			gyosyuCd = "LIV";
//		} else if (selectGyosyu.equals("03")) {
//			gyosyuCd = "GRO";
//		} else if (selectGyosyu.equals("04")) {
//			gyosyuCd = "FRE";
//		}
////		↑↑仕様変更（2005.10.16）↑↑
//
//		// 2006/02/03 kim START
//		// 加工食品や生鮮食品の場合、POSコードの検索結果が0400コードと02コードたげあったら、
//		// 空番を追加する。
////		   if (select.equals("0") && selectGyosyu.equals("03") || selectGyosyu.equals("04")){
////				for (int i=0; i< orgList.size(); i++){
////				mst250101_SyohinAkibanBean checkBean = (mst250101_SyohinAkibanBean)orgList.get(i);
////				 if (!checkBean.getSyohin2Cd().substring(0,4).equals("0400") && !checkBean.getSyohin2Cd().substring(0,4).equals("0200")){
////					return orgList;
////				 }
////				 }
////		   }
//		// 2006/02/03 kim END
//
//		for (int i = 0; i < orgList.size(); i++) {
//
////			if (select.equals("0") && selectGyosyu.equals("03") || selectGyosyu.equals("04")){
////			mst250101_SyohinAkibanBean checkBean = (mst250101_SyohinAkibanBean)orgList.get(i);
////				if (!checkBean.getSyohin2Cd().substring(0,4).equals("0400") ){
////						continue;
////				}
////			}
//			//-------------------------
//			//該当行を追加
//			//-------------------------
//			mst250101_SyohinAkibanBean startBean = (mst250101_SyohinAkibanBean)orgList.get(i);
//
//
////			↓↓仕様変更（2005.10.08）↓↓　空番を単品 "000" ～ "999" まで取得
//			if ( i == 0 ) {
///*2006/01/30 kim START  DELETE
//			if(select.equals(mst250201_KeepBean.SELECT_02)){
//				firstSyohinCd = numSet(inputSyohinCd, 7, "0");
//				firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//			} else if (select.equals(mst250201_KeepBean.SELECT_04)) {
//				firstSyohinCd = numSet(inputSyohinCd, 11, "0");
//				firstExSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,11);
//2006/01/30 kim END */
//// 2006/01/30 kim START　POSコードは食品のみで空番を含む。
//				if (select.equals(mst250201_KeepBean.SELECT_POS)){
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//// 2006/01/30 kim END
//				} else if (select.equals(mst250201_KeepBean.SELECT_HINSYU)) {
//					firstSyohinCd = numSet(inputSyohinCd, 7, "0");
//					firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//				}
//
//				firstCd = Long.parseLong(firstSyohinCd);
//				firstExCd = Long.parseLong(firstExSyohinCd);
//				addList = getAkibanList(db, select, akibanList, firstCd, firstExCd, gyosyuCd);
//			}
////			↑↑仕様変更（2005.10.08）↑↑　空番を単品 "000" ～ "999" まで取得
//
//			addList.add(startBean);
//
//			if (addList.size() > mst000101_ConstDictionary.SEARCH__MAX_LIMIT) {
//				return null;
//			}
//
//			//-------------------------
//			//最終行以外は次行を取得し空連番が存在する場合、空行作成
//			//-------------------------
//			if(i != orgList.size() - 1){
//				mst250101_SyohinAkibanBean nextBean = (mst250101_SyohinAkibanBean)orgList.get(i + 1);
//
//				//商品コード取得（連番対応）
//				long startCd = 0;
//				long nextCd = 0;
//
///* 2006/01/30 kim START  DELETE
//				// 02コード
//				 if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//					 String startSyohinCd = startBean.getSyohinCd().trim() + "0000000";
//					 startCd = Long.parseLong(startSyohinCd.substring(0,7));
//
//					 String nextSyohinCd = nextBean.getSyohinCd().trim() + "0000000";
//					 nextCd = Long.parseLong(nextSyohinCd.substring(0,7));
//
//				 //04コード
//				 } else if(select.equals(mst250201_KeepBean.SELECT_04)){
//
//					 String startSyohinCd = startBean.getSyohinCd().trim() + "00000000000";
//					 startCd = Long.parseLong(startSyohinCd.substring(0,11));
//
//					 String nextSyohinCd = nextBean.getSyohinCd().trim() + "00000000000";
//					 nextCd = Long.parseLong(nextSyohinCd.substring(0,11));
//2006/01/30 kim END */
//
//// 2006/01/30 kim START
//				// POS コード追加 POSコードは食品のみで空番を含む。
//				if(select.equals(mst250201_KeepBean.SELECT_POS)){
//
//					String startSyohinCd = startBean.getSyohinCd().trim() + "0000000";
//					startCd = Long.parseLong(startSyohinCd.substring(0,7));
//
//					String nextSyohinCd = nextBean.getSyohinCd().trim() + "0000000";
//					nextCd = Long.parseLong(nextSyohinCd.substring(0,7));
//// 2006/01/30 kim END
//				//品種コード
//				} else if(select.equals(mst250201_KeepBean.SELECT_HINSYU)){
//
//					String startSyohinCd = startBean.getSyohinCd().trim() + "0000000";
//					startCd = Long.parseLong(startSyohinCd.substring(0,7));
//
//					String nextSyohinCd = nextBean.getSyohinCd().trim() + "0000000";
//					nextCd = Long.parseLong(nextSyohinCd.substring(0,7));
//				//POSコード、品種コード以外は空番追加なしで返す
//				} else {
//					return orgList;
//				}
//
//				//空番行を追加
//				for (long j = startCd + 1 ; j < nextCd; j++) {
//					mst250101_SyohinAkibanBean akiBean = new mst250101_SyohinAkibanBean();
//
//					String akiSyohinCd = "";
//
///* 2006/01/30 kim START DELETE
//						//	02コード
//					  if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//						  akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7) + "00000";
//						  akiSyohinCd = mst001401_CheckDegitBean.getModulus10Code(akiSyohinCd,12);
//
//						  // 2005.10.03 状態区分追加対応 START -----
//						  ResultSet rset = null;
//						  StringBuffer sb = new StringBuffer();
//
//						  // データ存在チェック
//						  sb.append(" SELECT ");
//						  sb.append(" 	JYOTAI_KANRI_KB, ");
//						  sb.append(" 	DELETE_FG ");
//						  sb.append("   FROM ");
//						  sb.append("     R_SAIBAN_INSTORE ");
//						  sb.append("  WHERE ");
//						  sb.append(" 	   TANPIN_CD    	= '" + akiSyohinCd.substring(2,7) + "'");
//
//						  rset = db.executeQuery(sb.toString());
//
//						  // 存在時
//						  if (rset.next()) {
//
//							  // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							  if(	rset.getString("DELETE_FG").equals("0")
//								  && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									  akiBean.setJyotaiKb("仮登録");
//
//							  }else{
//
//								  akiBean.setJyotaiKb("未登録");
//							  }
//
//						  // 存在しない場合は未登録扱い
//						  }else{
//
//							  akiBean.setJyotaiKb("未登録");
//						  }
//
//						  if(rset != null){
//							  rset.close();
//						  }
//
//						  // 2005.10.03 状態区分追加対応 END -----
//
//					  //04コード
//					  } else if(select.equals(mst250201_KeepBean.SELECT_04)){
//
//						  akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),11);
//						  akiSyohinCd = akiSyohinCd.substring(4,11);
//						  akiSyohinCd = mst001401_CheckDegitBean.getInstore0400Code(akiSyohinCd,12);
//
//						  // 2005.10.03 状態区分追加対応 START -----
//						  ResultSet rset = null;
//						  StringBuffer sb = new StringBuffer();
//
//						  // データ存在チェック
//						  sb.append(" SELECT ");
//						  sb.append(" 	JYOTAI_KANRI_KB, ");
//						  sb.append(" 	DELETE_FG ");
//						  sb.append("   FROM ");
//						  sb.append("     R_SAIBAN_8KETA ");
//						  sb.append("  WHERE ");
//						  sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(4,8) + "'");
//						  sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(8,11) + "'");
//
//						  rset = db.executeQuery(sb.toString());
//
//						  // 存在時
//						  if (rset.next()) {
//
//							  // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							  if(	rset.getString("DELETE_FG").equals("0")
//								  && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									  akiBean.setJyotaiKb("仮登録");
//
//							  }else{
//
//								  akiBean.setJyotaiKb("未登録");
//							  }
//
//						  // 存在しない場合は未登録扱い
//						  }else{
//
//							  akiBean.setJyotaiKb("未登録");
//						  }
//
//						  if(rset != null){
//							  rset.close();
//						  }
//
//						  // 2005.10.03 状態区分追加対応 END -----
// 2006/01/30 kim END */
//
//// 2006/01/30 kim START POSコードは食品のみで空番を含む。
//
//						if(select.equals(mst250201_KeepBean.SELECT_POS)){
//
//							akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7);
//						   akiSyohinCd = mst001401_CheckDegitBean.getModulus11Code(akiSyohinCd,7);
//						   // 2005.10.03 状態区分追加対応 START -----
//						   ResultSet rset = null;
//						   StringBuffer sb = new StringBuffer();
//
//						   // データ存在チェック
//						   sb.append(" SELECT ");
//						   sb.append(" 	JYOTAI_KANRI_KB, ");
//						   sb.append(" 	DELETE_FG ");
//						   sb.append("   FROM ");
//						   sb.append("     R_SAIBAN_8KETA ");
//						   sb.append("  WHERE ");
//						   sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(0,4) + "'");
//						   sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(4,7) + "'");
//
//						   rset = db.executeQuery(sb.toString());
//
//						   // 存在時
//						   if (rset.next()) {
//
//							   // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							   if(	rset.getString("DELETE_FG").equals("0")
//								   && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									   akiBean.setJyotaiKb("仮登録");
//
//							   }else{
//
//								   akiBean.setJyotaiKb("未登録");
//							   }
//
//						   // 存在しない場合は未登録扱い
//						   }else{
//
//							   akiBean.setJyotaiKb("未登録");
//						   }
//
//						   if(rset != null){
//							   rset.close();
//						   }
//						   // 2005.10.03 状態区分追加対応 END -----
//// 2006/01/30 kim END
//					//品種コード
//					} else if(select.equals(mst250201_KeepBean.SELECT_HINSYU)){
//
//						akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7);
//						akiSyohinCd = mst001401_CheckDegitBean.getModulus11Code(akiSyohinCd,7);
//						// 2005.10.03 状態区分追加対応 START -----
//						ResultSet rset = null;
//						StringBuffer sb = new StringBuffer();
//
//						// データ存在チェック
//						sb.append(" SELECT ");
//						sb.append(" 	JYOTAI_KANRI_KB, ");
//						sb.append(" 	DELETE_FG ");
//						sb.append("   FROM ");
//						sb.append("     R_SAIBAN_8KETA ");
//						sb.append("  WHERE ");
//						sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(0,4) + "'");
//						sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(4,7) + "'");
//						rset = db.executeQuery(sb.toString());
//
//						// 存在時
//						if (rset.next()) {
//
//							// 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//							if(	rset.getString("DELETE_FG").equals("0")
//								&& (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//									akiBean.setJyotaiKb("仮登録");
//
//							}else{
//
//								akiBean.setJyotaiKb("未登録");
//							}
//
//						// 存在しない場合は未登録扱い
//						}else{
//
//							akiBean.setJyotaiKb("未登録");
//						}
//
//						if(rset != null){
//							rset.close();
//						}
//
//						// 2005.10.03 状態区分追加対応 END -----
//					}
//
//					akiBean.setGyosyuKb(gyosyuCd);
//					akiBean.setSyohinCd(akiSyohinCd);
//					addList.add(akiBean);
//
//					if (addList.size() > mst000101_ConstDictionary.SEARCH__MAX_LIMIT) {
//						return null;
//					}
//				}
////			↓↓仕様変更（2005.10.08）↓↓　空番を単品 "000" ～ "999" まで取得
//			} else if (i == orgList.size() - 1) {
///* 2006/01/30 kim START DELETE
//			   if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					firstExSyohinCd = numSet(inputSyohinCd, 7, "9");
//
//				} else if (select.equals(mst250201_KeepBean.SELECT_04)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "00000000000").substring(0,11);
//					firstExSyohinCd = numSet(inputSyohinCd, 11, "9");
//2006/01/30 kim END */
//
//// 2006/01/30 kim START
//				// POSコード
//				if  (select.equals(mst250201_KeepBean.SELECT_POS)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					//firstExSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					//品種コード+999までの空番を追加。
//					firstExSyohinCd = numSet(startBean.getSyohinCd().trim().substring(0,4), 7, "9");
//// 2006/01/30 kim END
//				//品種コード　
//				} else if (select.equals(mst250201_KeepBean.SELECT_HINSYU)) {
//
//					firstSyohinCd = (startBean.getSyohinCd().trim() + "0000000").substring(0,7);
//					firstExSyohinCd = numSet(inputSyohinCd, 7, "9");
//
//				}
//
//				firstCd = Long.parseLong(firstSyohinCd);
//				firstExCd = Long.parseLong(firstExSyohinCd);
//				addList = getAkibanList(db, select, akibanList, firstCd+1, firstExCd+1, gyosyuCd);
//			}
////			↑↑仕様変更（2005.10.08）↑↑　空番を単品 "000" ～ "999" まで取得
//		}
//		return addList;
//	}
//
//	/**
//	 * 空番行リストを取得する
//	 *
//	 * @param select  選択 0:02コード 1:04コード 2:品種コード 3:取引先品番
//	 * @param addList 空番を追加したい明細行
//	 * @param startCd 開始コード
//	 * @param startCd 終了コード
//	 * @return	List 空番追加した明細行
//	 *
//	 */
//	private List getAkibanList( mst000101_DbmsBean db, String select, List addList, long startCd, long nextCd, String gyosyuCd ) throws Exception{
//
//		for (long j = startCd ; j < nextCd; j++) {
//
//			mst250101_SyohinAkibanBean akiBean = new mst250101_SyohinAkibanBean();
//
//			String akiSyohinCd = "";
///* 2006/01/30 kim START  DELETE
////	02コード
//			  if(select.equals(mst250201_KeepBean.SELECT_02)){
//
//				  akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7) + "00000";
//				  akiSyohinCd = mst001401_CheckDegitBean.getModulus10Code(akiSyohinCd,12);
//
//				  // 2005.10.03 状態区分追加対応 START -----
//				  ResultSet rset = null;
//				  StringBuffer sb = new StringBuffer();
//
//				  // データ存在チェック
//				  sb.append(" SELECT ");
//				  sb.append(" 	JYOTAI_KANRI_KB, ");
//				  sb.append(" 	DELETE_FG ");
//				  sb.append("   FROM ");
//				  sb.append("     R_SAIBAN_INSTORE ");
//				  sb.append("  WHERE ");
//				  sb.append(" 	   TANPIN_CD    	= '" + akiSyohinCd.substring(2,7) + "'");
//
//				  rset = db.executeQuery(sb.toString());
//
//				  // 存在時
//				  if (rset.next()) {
//
//					  // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//					  if(	rset.getString("DELETE_FG").equals("0")
//						  && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//							  akiBean.setJyotaiKb("仮登録");
//
//					  }else{
//
//						  akiBean.setJyotaiKb("未登録");
//					  }
//
//				  // 存在しない場合は未登録扱い
//				  }else{
//
//					  akiBean.setJyotaiKb("未登録");
//				  }
//
//				  if(rset != null){
//					  rset.close();
//				  }
//
//				  // 2005.10.03 状態区分追加対応 END -----
//
//			  //04コード
//			  } else if(select.equals(mst250201_KeepBean.SELECT_04)){
//
//				  akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),11);
//				  akiSyohinCd = akiSyohinCd.substring(4,11);
//				  akiSyohinCd = mst001401_CheckDegitBean.getInstore0400Code(akiSyohinCd,12);
//
//				  // 2005.10.03 状態区分追加対応 START -----
//				  ResultSet rset = null;
//				  StringBuffer sb = new StringBuffer();
//
//				  // データ存在チェック
//				  sb.append(" SELECT ");
//				  sb.append(" 	JYOTAI_KANRI_KB, ");
//				  sb.append(" 	DELETE_FG ");
//				  sb.append("   FROM ");
//				  sb.append("     R_SAIBAN_8KETA ");
//				  sb.append("  WHERE ");
//				  sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(4,8) + "'");
//				  sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(8,11) + "'");
//
//				  rset = db.executeQuery(sb.toString());
//
//				  // 存在時
//				  if (rset.next()) {
//
//					  // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//					  if(	rset.getString("DELETE_FG").equals("0")
//						  && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//							  akiBean.setJyotaiKb("仮登録");
//
//					  }else{
//
//						  akiBean.setJyotaiKb("未登録");
//					  }
//
//				  // 存在しない場合は未登録扱い
//				  }else{
//
//					  akiBean.setJyotaiKb("未登録");
//				  }
//
//				  if(rset != null){
//					  rset.close();
//				  }
//
//				  // 2005.10.03 状態区分追加対応 END -----
// 2006/01/30 kim END */
//
//// 2006/01/30 kim START POSコードは食品のみで空番を含む。
//				if(select.equals(mst250201_KeepBean.SELECT_POS)){
//					akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7);
//					akiSyohinCd = mst001401_CheckDegitBean.getModulus11Code(akiSyohinCd,7);
//
//					 // 2005.10.03 状態区分追加対応 START -----
//					 ResultSet rset = null;
//					 StringBuffer sb = new StringBuffer();
//
//					 // データ存在チェック
//					 sb.append(" SELECT ");
//					 sb.append(" 	JYOTAI_KANRI_KB, ");
//					 sb.append(" 	DELETE_FG ");
//					 sb.append("   FROM ");
//					 sb.append("     R_SAIBAN_INSTORE ");
//					 sb.append("  WHERE ");
//					 sb.append(" 	   TANPIN_CD    	= '" + akiSyohinCd.substring(2,7) + "'");
//					 rset = db.executeQuery(sb.toString());
//
//					 // 存在時
//					 if (rset.next()) {
//
//						 // 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//						 if(	rset.getString("DELETE_FG").equals("0")
//							 && (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//								 akiBean.setJyotaiKb("仮登録");
//
//						 }else{
//
//							 akiBean.setJyotaiKb("未登録");
//						 }
//
//					 // 存在しない場合は未登録扱い
//					 }else{
//
//						 akiBean.setJyotaiKb("未登録");
//					 }
//
//					 if(rset != null){
//						 rset.close();
//					 }
//
//					 // 2005.10.03 状態区分追加対応 END -----
//// 2006/01/30 kim END
//			//品種コード
//			} else if(select.equals(mst250201_KeepBean.SELECT_HINSYU)){
//
//
//
//				akiSyohinCd = mst000401_LogicBean.formatZero(Long.toString(j),7);
//				akiSyohinCd = mst001401_CheckDegitBean.getModulus11Code(akiSyohinCd,7);
//				// 2005.10.03 状態区分追加対応 START -----
//				ResultSet rset = null;
//				StringBuffer sb = new StringBuffer();
//
//				// データ存在チェック
//				sb.append(" SELECT ");
//				sb.append(" 	JYOTAI_KANRI_KB, ");
//				sb.append(" 	DELETE_FG ");
//				sb.append("   FROM ");
//				sb.append("     R_SAIBAN_8KETA ");
//				sb.append("  WHERE ");
//				sb.append(" 	   HINSYU_CD    	= '" + akiSyohinCd.substring(0,4) + "'");
//				sb.append("    AND TANPIN_CD    	= '" + akiSyohinCd.substring(4,7) + "'");
//
//				rset = db.executeQuery(sb.toString());
//
//				// 存在時
//				if (rset.next()) {
//
//					// 削除フラグ = '0'　かつ　状態管理区分 = '3','4'
//					if(	rset.getString("DELETE_FG").equals("0")
//						&& (rset.getString("JYOTAI_KANRI_KB").equals("3") || rset.getString("JYOTAI_KANRI_KB").equals("4")) ){
//							akiBean.setJyotaiKb("仮登録");
//
//					}else{
//
//						akiBean.setJyotaiKb("未登録");
//					}
//
//				// 存在しない場合は未登録扱い
//				}else{
//
//					akiBean.setJyotaiKb("未登録");
//				}
//
//				if(rset != null){
//					rset.close();
//				}
//
//				// 2005.10.03 状態区分追加対応 END -----
//			}
//
//			akiBean.setGyosyuKb(gyosyuCd);
//			akiBean.setSyohinCd(akiSyohinCd);
//			addList.add(akiBean);
//
//			if (addList.size() > mst000101_ConstDictionary.SEARCH__MAX_LIMIT) {
//				return null;
//			}
//		}
//
//		return addList;
//	}
//	↑↑2006.07.19 xiongjun カスタマイズ修正↑↑

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
