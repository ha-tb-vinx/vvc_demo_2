package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品空番検索画面（空番を含む）のDMクラス</p>
 * <p>説明: 商品空番検索画面（空番を含む）のDMクラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst250102_SyohinAkibanHukumuDM extends DataModule
{
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
	private ArrayList syohinList = null;	//商品コード配列
	/**
	 * コンストラクタ
	 */
	public mst250102_SyohinAkibanHukumuDM()
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
		String SyohinCd = rest.getString("syohin_cd").trim();

//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
//		//空番にチェックデジットを付加
//		if (rest.getString("akiban_kb").equals("YES"))
//		{
//			if (SyohinCd.substring(0,4).equals("0400"))
//			{
//				SyohinCd = SyohinCd.substring(4,11);
//				SyohinCd = mst001401_CheckDegitBean.getInstore0400Code( SyohinCd, 12 );
//			}
//			else if (SyohinCd.substring(0,2).equals("02"))
//			{
//					SyohinCd = mst001401_CheckDegitBean.getModulus10Code( SyohinCd, SyohinCd.length() );
//			}
//			else
//			{
//				SyohinCd = mst001401_CheckDegitBean.getModulus11Code( SyohinCd, SyohinCd.length() );
//			}
//		}
//		//0400仮登録番号にチェックデジットを付加
//		else if (rest.getString("jyotai_kanri_kb") != null &&
//					(rest.getString("jyotai_kanri_kb").equals("3") || rest.getString("jyotai_kanri_kb").equals("4")))
//		{
//			if (SyohinCd.substring(0,4).equals("0400"))
//			{
//				SyohinCd = SyohinCd.substring(4,11);
//				SyohinCd = mst001401_CheckDegitBean.getInstore0400Code( SyohinCd, 12 );
//			}
//		}

//		bean.setHankuCd( rest.getString("hanku_cd") );
		bean.setSyohinCd( SyohinCd );
		bean.setYukoDt( rest.getString("yuko_dt") );
		bean.setSyohinNa(rest.getString("syohin_na") );
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
		bean.setUpdateTs( rest.getString("update_ts") );
		bean.setDeleteFg( rest.getString("delete_fg") );
		bean.setJyotaiKb( rest.getString("jyotai_kb") );
//		bean.setHinshuNa( rest.getString("hinshu_na") );
//		bean.setHankuNa( rest.getString("hanku_na") );
		bean.setInsertUserNa( rest.getString("insert_user_na") );
		bean.setUpdateUserNa( rest.getString("update_user_na") );
		bean.setCreateDatabase();
//		bean.setSyohin2Cd( rest.getString("syohin_2_cd"));   	//posコード
//		bean.setKikakuKanjiNa(rest.getString("kikaku_kanji_na")); //漢字規格
//		bean.setGentankaVl(rest.getLong("gentanka_vl"));	//原単価
//		bean.setBaitankaVl(rest.getLong("baitanka_vl"));	//販単価
//		bean.setSiiresakiCd(rest.getString("siiresaki_cd"));  //仕入先コード
//		bean.setSiiresakiNa(rest.getString("siiresaki_na"));  //仕入先名称
//		bean.setSiireHinbanCd(rest.getString("siire_hinban_cd")); //取引先品番
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

		//--------------------
		//検索条件の作成
		//--------------------
//		↓↓2006.07.19 xiongjun カスタマイズ修正↓↓
		StringBuffer whereBuf = new StringBuffer();
		StringBuffer whereBufHinTan = new StringBuffer();
		StringBuffer whereBufGyosyu = new StringBuffer();

		String select = mst000401_LogicBean.chkNullString((String)map.get("select")).trim();
		String startSyohinCd = mst000401_LogicBean.chkNullString((String)map.get("startSyohinCd")).trim();
		String startSyohinCd_Mae2Keta = (startSyohinCd + "00").substring(0,2);
		String startHinsyuCd = "0000";
		String hankuCd = conv.convertWhereString((String)map.get("ct_kanricd"));
		String checkCd = startSyohinCd + "0000";
		String MasterDT = RMSTDATEUtil.getMstDateDt();
		int selGyosyuCd = Integer.parseInt((String)map.get("sel_gyosyu_cd"));	// 業種選択画面で選択された業種コード
		String gyoshuKb = null;
		String strOrderBy = "";		// ソート条件



//		// コード 検索
//		// POSコード
//		if (select.equals(mst250201_KeepBean.SELECT_POS) && startSyohinCd.length()!=0 )
//		{
//			whereBuf.append(" syohin_2_cd >= '" + startSyohinCd + "'");
//
//		}
//		// 品種コード
//		else if(select.equals(mst250201_KeepBean.SELECT_HINSYU) && startSyohinCd.length()!=0)
//		{
//
//			if( selGyosyuCd == 1 || selGyosyuCd == 2 )
//			{
//				//衣料・住生活
//				whereBuf.append(" syohin_cd >='" + startSyohinCd + "'");
//				whereBuf.append(" and ");
//				whereBuf.append(" hinsyu_cd >='" + (startSyohinCd + "0000").substring(0,4) + "'");
//
//				if (startSyohinCd.length() > 4)
//				{
//					//チックディジットを排除して検索する。
//					whereBufHinTan.append(" and ");
//					whereBufHinTan.append(" hinsyu_tanpin_list.syohin_cd >= '" + (startSyohinCd + "0000000").substring(0,7) + "' ");
//				}
//
//				// 品種検索時、商品マスタから１件のデータも検索されなかった場合、
//				// 入力された品種コードで自動採番枠品種別マスタを検索する。
//				startHinsyuCd = (startSyohinCd + "0000").substring(0,4);
//			}
//			else if( selGyosyuCd == 3 || selGyosyuCd == 4 )
//			{
//				//加工食品・生鮮食品
//				//whereBuf.append(" hinsyu_cd >='" + startSyohinCd + "'");
//				startSyohinCd = "0400" + startSyohinCd;
//				whereBuf.append(" syohin_cd >='" + startSyohinCd + "'");
//				whereBuf.append(" and ");
//				whereBuf.append(" hinsyu_cd >='" + (startSyohinCd + "00000000").substring(4,8) + "'");
//
//				if (startSyohinCd.length() > 8)
//				{
//					//チックディジットを排除して検索する。
//					whereBufHinTan.append(" and ");
//					whereBufHinTan.append(" hinsyu_tanpin_list.syohin_cd >= '" + (startSyohinCd + "0000000").substring(0,11) + "' ");
//				}
//
//				// 品種検索時、商品マスタから１件のデータも検索されなかった場合、
//				// 入力された品種コードで自動採番枠品種別マスタを検索する。
//				startHinsyuCd = (startSyohinCd + "0000").substring(4,8);
//			}
//
//		}
//		// 取引先品番
//		else if(select.equals(mst250201_KeepBean.SELECT_HINBAN) && startSyohinCd.length()!=0)
//		{
//			whereBuf.append(" siire_hinban_cd like '" + startSyohinCd + "%'");
//		}
//
//		// 仕入先コード 条件
//		if (map.get("ct_siiresakicd")!= null && ((String)map.get("ct_siiresakicd")).trim().length()!=0){
//			// 2006/02/24 kim START
//			if (startSyohinCd.length() != 0)	whereBuf.append(" and ");
//			// 2006/02/24 kim END
//			whereBuf.append(" siiresaki_cd = '"+ conv.convertWhereString((String)map.get("ct_siiresakicd"))+"'");
//		}
//
//		// 販区 条件
//		if (map.get("ct_kanricd")!= null && ((String)map.get("ct_kanricd")).trim().length()!=0){
//			whereBuf.append(" and ");
//			whereBuf.append(" hanku_cd= '" + hankuCd + "'");
//		}
//
//		//業種 条件
//		// selGyosyuCd に対するWHERE区
//		if( selGyosyuCd == 1 )
//		{
//			//衣料
//			whereBufGyosyu.append(" d_gyosyu_cd in ('0011','0044','0061','0057') ");
//			gyoshuKb = mst000601_GyoshuKbDictionary.A08.getCode();
//		}
//		else if( selGyosyuCd == 3 )
//		{
//			//加工食品
//			whereBufGyosyu.append(" d_gyosyu_cd = '0033' ");
//			whereBufGyosyu.append(" and ");
//			whereBufGyosyu.append(" s_gyosyu_cd in ('0087','0088') ");
//			gyoshuKb = mst000601_GyoshuKbDictionary.GRO.getCode();
//		}
//		else if( selGyosyuCd == 4 )
//		{
//			//生鮮食品
//			whereBufGyosyu.append(" d_gyosyu_cd = '0033' ");
//			whereBufGyosyu.append(" and ");
//			whereBufGyosyu.append(" s_gyosyu_cd not in ('0087', '0088') ");
//			gyoshuKb =mst000601_GyoshuKbDictionary.FRE.getCode();
//		}
//		else if( selGyosyuCd == 2 )
//		{
//			//住生活
//			whereBufGyosyu.append(" d_gyosyu_cd not in ('0011', '0044', '0061', '0057', '0033') ");
//			gyoshuKb=mst000601_GyoshuKbDictionary.LIV.getCode();
//		}
//
//		// ソート 条件
//		// POSコード
//		if (select.equals(mst250201_KeepBean.SELECT_POS))
//		{
//			//加工食品・生鮮食品
//			//※空番の場合、POSコードがNullの為
//			if( selGyosyuCd == 3 || selGyosyuCd == 4 )
//			{
//				strOrderBy = " syohin_cd ";
//			}
//			else
//			{
//				strOrderBy = " syohin_2_cd ";
//			}
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

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//--------------------
		//SQLの作成
		//--------------------
		StringBuffer sb = new StringBuffer();

		sb.append("WITH SYOHIN AS ");
		sb.append("(SELECT ");
		sb.append("	* ");
		sb.append("FROM ");
		sb.append("(SELECT ");
		sb.append("	SYOHIN1.HANKU_CD, ");
		sb.append("	TRIM(SYOHIN1.SYOHIN_CD) SYOHIN_CD, ");
		sb.append("	SYOHIN1.YUKO_DT, ");
		sb.append("	SYOHIN1.GYOSYU_KB, ");
		sb.append("	SYOHIN1.HINSYU_CD, ");
		sb.append("	SYOHIN1.HINMEI_KANJI_NA, ");
		sb.append("	SYOHIN1.SYOHIN_2_CD, ");
		sb.append("	SYOHIN1.GENTANKA_VL, ");
		sb.append("	SYOHIN1.BAITANKA_VL, ");
		sb.append("	SYOHIN1.SIIRESAKI_CD, ");
		sb.append("	SYOHIN1.SIIRE_HINBAN_CD, ");
		sb.append("	SYOHIN1.KIKAKU_KANJI_NA, ");
		sb.append("	SYOHIN1.INSERT_TS, ");
		sb.append("	SYOHIN1.INSERT_USER_ID, ");
		sb.append("	SYOHIN1.UPDATE_TS, ");
		sb.append("	SYOHIN1.UPDATE_USER_ID, ");
		sb.append("	SYOHIN1.DELETE_FG, ");
		sb.append("	'' JYOTAI_KANRI_KB, ");
		sb.append("	'' CODE_DELETE_FG ");
		sb.append("FROM ");
		sb.append("	R_SYOHIN SYOHIN1, ");
		sb.append("	(SELECT ");
		sb.append("		HANKU_CD, ");
		sb.append("		SYOHIN_CD, ");
		sb.append("		MIN(YUKO_DT) AS YUKO_DT ");
		sb.append("	FROM ");
		sb.append("		((SELECT ");
		sb.append("			* ");
		sb.append("		FROM ");
		sb.append("			(SELECT ");
		sb.append("				HANKU_CD, ");
		sb.append("				SYOHIN_CD, ");
		sb.append("				MAX(YUKO_DT) AS YUKO_DT, ");
		sb.append("				(SELECT DISTINCT ");
		sb.append("					D_GYOSYU_CD ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN_TAIKEI ");
		sb.append("				WHERE ");
		sb.append("					HANKU_CD = RS.HANKU_CD ");
		sb.append("				) D_GYOSYU_CD  , ");
		sb.append("				(SELECT DISTINCT ");
		sb.append("					S_GYOSYU_CD ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN_TAIKEI ");
		sb.append("				WHERE ");
		sb.append("					HANKU_CD = RS.HANKU_CD ");
		sb.append("				) S_GYOSYU_CD ");
		sb.append(" ");
		sb.append("			FROM ");
		sb.append("				R_SYOHIN RS ");
		sb.append("			WHERE ");
		sb.append("				YUKO_DT <= '" + MasterDT + "' AND ");
		sb.append("				" + whereBuf.toString() + "  ");
		sb.append("			GROUP BY ");
		sb.append("				HANKU_CD, ");
		sb.append("				SYOHIN_CD ");
		sb.append("			)  tab1");
		sb.append("		WHERE ");
		sb.append("			" + whereBufGyosyu.toString() + " ");
		sb.append("		) ");
		sb.append("		UNION ALL ");
		sb.append("		(SELECT ");
		sb.append("			* ");
		sb.append("		FROM ");
		sb.append("			(SELECT ");
		sb.append("				HANKU_CD, ");
		sb.append("				SYOHIN_CD, ");
		sb.append("				MIN(YUKO_DT) AS YUKO_DT, ");
		sb.append("				(SELECT DISTINCT ");
		sb.append("					D_GYOSYU_CD ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN_TAIKEI ");
		sb.append("				WHERE ");
		sb.append("					HANKU_CD = RS.HANKU_CD ");
		sb.append("				) D_GYOSYU_CD  , ");
		sb.append("				(SELECT DISTINCT ");
		sb.append("					S_GYOSYU_CD ");
		sb.append("				FROM ");
		sb.append("					R_SYOHIN_TAIKEI ");
		sb.append("				WHERE ");
		sb.append("					HANKU_CD = RS.HANKU_CD ");
		sb.append("				) S_GYOSYU_CD ");
		sb.append("			FROM ");
		sb.append("				R_SYOHIN RS ");
		sb.append("			WHERE ");
		sb.append("				YUKO_DT <= '" + MasterDT + "' AND ");
		sb.append("				" + whereBuf.toString() + "  ");
		sb.append("			GROUP BY ");
		sb.append("				HANKU_CD, ");
		sb.append("				SYOHIN_CD ");
		sb.append("			)  tab2");
		sb.append("		WHERE ");
		sb.append("			" + whereBufGyosyu.toString() + " ");
		sb.append("		) ");
		sb.append("	)  tab3");
		sb.append("	GROUP BY ");
		sb.append("		HANKU_CD, ");
		sb.append("		SYOHIN_CD ");
		sb.append("	)  SYOHIN2 ");
		sb.append("WHERE ");
		sb.append("	SYOHIN1.HANKU_CD = SYOHIN2.HANKU_CD   AND ");
		sb.append("	SYOHIN1.SYOHIN_CD = SYOHIN2.SYOHIN_CD   AND ");
		sb.append("	SYOHIN1.YUKO_DT = SYOHIN2.YUKO_DT ");
		sb.append("ORDER BY ");
		sb.append( 	strOrderBy );
		sb.append(") tab4 ");
		sb.append("fetch first 10000 rows only ");
		sb.append(") ");

		sb.append("SELECT ");
		sb.append("	SYOHIN.HANKU_CD 	, ");
		sb.append("	SYOHIN.SYOHIN_CD 	, ");
		sb.append("	SYOHIN.YUKO_DT 	, ");
		sb.append(" CASE SYOHIN.GYOSYU_KB WHEN CAST(NULL AS CHAR) THEN '"+ gyoshuKb +"' ");
		sb.append("                                      WHEN '' THEN '"+ gyoshuKb +"' ");
		sb.append("	ELSE  SYOHIN.GYOSYU_KB END  AS  gyosyu_kb , ");
		sb.append("	SYOHIN.HINSYU_CD 	, ");
		sb.append("	SYOHIN.HINMEI_KANJI_NA 	, ");
		sb.append("	SYOHIN.SYOHIN_2_CD 	, ");
		sb.append("	SYOHIN.GENTANKA_VL 	, ");
		sb.append("	SYOHIN.BAITANKA_VL 	, ");
		sb.append("	SYOHIN.SIIRESAKI_CD 	, ");
		sb.append("	SYOHIN.SIIRE_HINBAN_CD 	, ");
		sb.append("	SYOHIN.KIKAKU_KANJI_NA 	, ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_NA ");
		sb.append("	FROM ");
		sb.append("		R_SIIRESAKI ");
		sb.append("	WHERE ");
		sb.append("		KANRI_KB = '2' AND ");
		sb.append("		KANRI_CD = SYOHIN.HANKU_CD AND ");
		sb.append("		SIIRESAKI_CD = SYOHIN.SIIRESAKI_CD ");
		sb.append("	) AS SIIRESAKI_NA 	, ");
		sb.append("	SUBSTR(SYOHIN.INSERT_TS, 1, 8 ) AS INSERT_TS 	, ");
		sb.append("	SUBSTR(SYOHIN.UPDATE_TS, 1, 8 ) AS UPDATE_TS 	, ");
		sb.append("	SYOHIN.DELETE_FG 	, ");
		sb.append("	CASE WHEN SYOHIN.DELETE_FG = '0' THEN '登録済' WHEN SYOHIN.DELETE_FG = '1' THEN '削除' ");
		sb.append("	WHEN SYOHIN.CODE_DELETE_FG = '0' AND SYOHIN.JYOTAI_KANRI_KB IN ('1','2') THEN '登録済' ");
		sb.append("	WHEN SYOHIN.CODE_DELETE_FG = '0' AND SYOHIN.JYOTAI_KANRI_KB IN ('3','4') THEN '仮登録' ELSE '未登録' ");
		sb.append("	END AS JYOTAI_KB 	, ");
		sb.append("	CASE WHEN SYOHIN.DELETE_FG = '0' THEN 'NO' WHEN SYOHIN.DELETE_FG = '1' THEN 'NO' ");
		sb.append("	WHEN SYOHIN.CODE_DELETE_FG = '0' AND SYOHIN.JYOTAI_KANRI_KB IN ('1','2') THEN 'NO' ");
		sb.append("	WHEN SYOHIN.CODE_DELETE_FG = '0' AND SYOHIN.JYOTAI_KANRI_KB IN ('3','4') THEN 'NO' ELSE 'YES' ");
		sb.append("	END AS AKIBAN_KB 	, ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		TRIM(CODE_CD) = TRIM(SYOHIN.HINSYU_CD) 	AND ");
		sb.append("		SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) + "' 	AND ");
		sb.append("		DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	) AS HINSHU_NA 	, ");
		sb.append("	(SELECT ");
		sb.append("		KANJI_RN ");
		sb.append("	FROM ");
		sb.append("		R_NAMECTF ");
		sb.append("	WHERE ");
		sb.append("		TRIM(CODE_CD) = TRIM(SYOHIN.HANKU_CD) 	AND ");
//		sb.append("		SYUBETU_NO_CD = '" + mst000101_ConstDictionary.H_SALE + "' 	AND ");
		sb.append("		DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		sb.append("	) AS HANKU_NA 	, ");
		sb.append("	(SELECT ");
		sb.append("		USER_NA ");
		sb.append("	FROM ");
		sb.append("		SYS_SOSASYA ");
		sb.append("	WHERE ");
		sb.append("		SYOHIN.INSERT_USER_ID = USER_ID AND ");
		sb.append("		HOJIN_CD = '00000' ");
		sb.append("	) AS INSERT_USER_NA 	, ");
		sb.append("	(SELECT ");
		sb.append("		USER_NA ");
		sb.append("	FROM ");
		sb.append("		SYS_SOSASYA ");
		sb.append("	WHERE ");
		sb.append("		SYOHIN.UPDATE_USER_ID = USER_ID AND ");
		sb.append("		HOJIN_CD = '00000' ");
		sb.append("	) AS UPDATE_USER_NA  , ");
		sb.append("	JYOTAI_KANRI_KB ");
		sb.append(" ");
		sb.append("FROM ");
		sb.append("(");


	//02コード・04コード・自社コード以外の商品コードを取得
	if( selGyosyuCd == 3 || selGyosyuCd == 4 )
	{
		//加工食品・生鮮食品のみあり

		sb.append(" select * from 	(SELECT ");
			sb.append("	SYOHIN.HANKU_CD, ");
			sb.append("	TRIM(SYOHIN.SYOHIN_CD) SYOHIN_CD, ");
			sb.append("	SYOHIN.YUKO_DT, ");
			sb.append("	SYOHIN.GYOSYU_KB, ");
			sb.append("	SYOHIN.HINSYU_CD, ");
			sb.append("	SYOHIN.HINMEI_KANJI_NA, ");
			sb.append("	SYOHIN.SYOHIN_2_CD, ");
			sb.append("	SYOHIN.GENTANKA_VL, ");
			sb.append("	SYOHIN.BAITANKA_VL, ");
			sb.append("	SYOHIN.SIIRESAKI_CD, ");
			sb.append("	SYOHIN.SIIRE_HINBAN_CD, ");
			sb.append("	SYOHIN.KIKAKU_KANJI_NA, ");
			sb.append("	SYOHIN.INSERT_TS, ");
			sb.append("	SYOHIN.INSERT_USER_ID, ");
			sb.append("	SYOHIN.UPDATE_TS, ");
			sb.append("	SYOHIN.UPDATE_USER_ID, ");
			sb.append("	SYOHIN.DELETE_FG, ");
			sb.append("	SYOHIN.JYOTAI_KANRI_KB, ");
			sb.append("	SYOHIN.CODE_DELETE_FG ");
		sb.append("	FROM ");
		sb.append("		SYOHIN ");
		sb.append("	WHERE ");
		sb.append("		SUBSTR(SYOHIN_CD,1,4) <> '0400' AND ");
		sb.append("		SUBSTR(SYOHIN_CD,1,2) <> '02'  ");
		sb.append("			fetch first 10000 rows only  ");
		sb.append("	)  tab3");

		sb.append("	UNION ALL ");

	}


	//04コード・自社コードの展開
		sb.append("   select * from 	(SELECT ");
		sb.append("		HANKU_CD, ");
		sb.append("		SYOHIN_CD, ");
		sb.append("		YUKO_DT, ");
		sb.append("		GYOSYU_KB, ");
		sb.append("		HINSYU_CD, ");
		sb.append("		HINMEI_KANJI_NA, ");
		sb.append("		SYOHIN_2_CD, ");
		sb.append("		GENTANKA_VL, ");
		sb.append("		BAITANKA_VL, ");
		sb.append("		SIIRESAKI_CD, ");
		sb.append("		SIIRE_HINBAN_CD, ");
		sb.append("		KIKAKU_KANJI_NA, ");
		sb.append("		INSERT_TS, ");
		sb.append("		INSERT_USER_ID, ");
		sb.append("		UPDATE_TS, ");
		sb.append("		UPDATE_USER_ID, ");
		sb.append("		DELETE_FG, ");
		sb.append("		JYOTAI_KANRI_KB, ");
		sb.append("		CODE_DELETE_FG ");
		sb.append("	FROM ");
		sb.append("		(SELECT ");
//		sb.append("			NVL(SYOHIN_LIST.HANKU_CD,'') HANKU_CD, ");
		sb.append("			NVL(HINSYU_TANPIN_LIST.HANKU_CD,'') HANKU_CD, ");
		sb.append("			NVL(SYOHIN_LIST.SYOHIN_CD,HINSYU_TANPIN_LIST.SYOHIN_CD) SYOHIN_CD, ");
		sb.append("			NVL(SYOHIN_LIST.YUKO_DT,'') YUKO_DT, ");
		sb.append("			NVL(SYOHIN_LIST.GYOSYU_KB,'') GYOSYU_KB, ");
//		sb.append("			NVL(SYOHIN_LIST.HINSYU_CD,'') HINSYU_CD, ");
		sb.append("			NVL(HINSYU_TANPIN_LIST.HINSYU_CD,'') HINSYU_CD, ");
		sb.append("			NVL(SYOHIN_LIST.HINMEI_KANJI_NA,'') HINMEI_KANJI_NA, ");
		sb.append("			NVL(SYOHIN_LIST.SYOHIN_2_CD,'') SYOHIN_2_CD, ");
		sb.append("			case SYOHIN_LIST.GENTANKA_VL when cast(null as char) then '' else SYOHIN_LIST.GENTANKA_VL end as GENTANKA_VL, ");
		sb.append("			case SYOHIN_LIST.BAITANKA_VL when cast(null as char) then '' else SYOHIN_LIST.BAITANKA_VL end as  BAITANKA_VL, ");
		sb.append("			NVL(SYOHIN_LIST.SIIRESAKI_CD,'') SIIRESAKI_CD, ");
		sb.append("			NVL(SYOHIN_LIST.SIIRE_HINBAN_CD,'') SIIRE_HINBAN_CD, ");
		sb.append("			NVL(SYOHIN_LIST.KIKAKU_KANJI_NA,'') KIKAKU_KANJI_NA, ");
		sb.append("			NVL(SYOHIN_LIST.INSERT_TS,'') INSERT_TS, ");
		sb.append("			NVL(SYOHIN_LIST.INSERT_USER_ID,'') INSERT_USER_ID, ");
		sb.append("			NVL(SYOHIN_LIST.UPDATE_TS,'') UPDATE_TS, ");
		sb.append("			NVL(SYOHIN_LIST.UPDATE_USER_ID,'') UPDATE_USER_ID, ");
		sb.append("			NVL(SYOHIN_LIST.DELETE_FG,'') DELETE_FG, ");
		sb.append("			NVL(HINSYU_TANPIN_LIST.JYOTAI_KANRI_KB,'N') JYOTAI_KANRI_KB, ");
		sb.append("			NVL(HINSYU_TANPIN_LIST.CODE_DELETE_FG,'') CODE_DELETE_FG ");
		sb.append("		FROM ");
		sb.append("			(SELECT ");
	if( selGyosyuCd == 3 || selGyosyuCd == 4 )
	{
		//加工食品・生鮮食品
		sb.append("				'0400' || HINSYU_TANPIN_LIST2.HINSYU_CD || HINSYU_TANPIN_LIST2.TANPIN_CD || NVL(R_SAIBAN_8KETA.CHECK_DEGIT_CD, ' ') SYOHIN_CD, ");
	}
	else
	{
		//衣料・住生活
		sb.append("				HINSYU_TANPIN_LIST2.HINSYU_CD || HINSYU_TANPIN_LIST2.TANPIN_CD || NVL(R_SAIBAN_8KETA.CHECK_DEGIT_CD, ' ') SYOHIN_CD, ");
	}
		sb.append("				HINSYU_TANPIN_LIST2.HANKU_CD, ");
		sb.append("				HINSYU_TANPIN_LIST2.HINSYU_CD, ");
		sb.append("				R_SAIBAN_8KETA.JYOTAI_KANRI_KB, ");
		sb.append("				R_SAIBAN_8KETA.DELETE_FG CODE_DELETE_FG ");
		sb.append("			FROM ");
		sb.append("				(SELECT ");
		sb.append("					HINSYU_LIST.HANKU_CD, ");
		sb.append("					HINSYU_LIST.HINSYU_CD, ");
		sb.append("					TANPIN_LIST.TANPIN_CD ");
		sb.append("				FROM ");
		sb.append("					(SELECT ");
		sb.append("						* ");
		sb.append("					FROM ");
		sb.append("						(SELECT ");
		sb.append("							RST.HANKU_CD, ");
		sb.append("							RSWH.HINSYU_CD ");
		sb.append("						FROM ");
		sb.append("							R_SAIBAN_WAKU_HINSYU RSWH, ");
		sb.append("							R_SYOHIN_TAIKEI RST ");
		sb.append("						WHERE ");
		sb.append("							RSWH.HINSYU_CD = RST.HINSYU_CD ");
		sb.append("							AND " + whereBufGyosyu.toString() + " ");
	if( !hankuCd.equals("") ){
		sb.append("							AND RST.HANKU_CD = '" + hankuCd + "' ");
	}
	if( !startHinsyuCd.equals("") ){
		//コード検索：品種コード
		sb.append("							AND RSWH.HINSYU_CD >= '" + startHinsyuCd + "' ");
//sb.append("							AND RSWH.HINSYU_CD <= '" + startHinsyuCd + "' ");
	}
	else
	{
		//コード検索：品種コード以外
		sb.append("							AND RSWH.HINSYU_CD >= 	(SELECT ");
		sb.append("														SYOHIN.HINSYU_CD ");
		sb.append("													FROM ");
		sb.append("														SYOHIN ");
		sb.append("													WHERE ");
		sb.append("														fetch first 1 rows only ");
		sb.append("													) ");
	}
		sb.append("							AND RSWH.DELETE_FG = '0' ");
		sb.append("						ORDER BY ");
		sb.append("							RSWH.HINSYU_CD ");
		sb.append("						)  tab1 ");
		sb.append("						fetch first 11 rows only ");
		sb.append("					) HINSYU_LIST, ");
		sb.append("					R_TANPIN_WAKU_3KETA TANPIN_LIST ");
		sb.append("				) HINSYU_TANPIN_LIST2  ");
		sb.append(" 				left outer join	");
		sb.append("				(SELECT ");
		sb.append("					* ");
		sb.append("				FROM ");
		sb.append("					R_SAIBAN_8KETA ");
		sb.append("				WHERE ");
		sb.append("					DELETE_FG = '0' ");
		sb.append("				) R_SAIBAN_8KETA ");
		sb.append("			on ");
		sb.append("				HINSYU_TANPIN_LIST2.HINSYU_CD = R_SAIBAN_8KETA.HINSYU_CD  AND ");
		sb.append("				HINSYU_TANPIN_LIST2.TANPIN_CD = R_SAIBAN_8KETA.TANPIN_CD  ");
//		sb.append("				ROWNUM < 11001 ");
		sb.append("			) HINSYU_TANPIN_LIST  ");
		sb.append(" 				left outer join	");
		sb.append("			SYOHIN SYOHIN_LIST ");
		sb.append("		on ");
		sb.append("			HINSYU_TANPIN_LIST.SYOHIN_CD = SYOHIN_LIST.SYOHIN_CD  ");
		sb.append("			" + whereBufHinTan.toString() + "  ");
		sb.append("		)  tab2 ");
		sb.append("	WHERE ");
		//■状態管理区分
		//「N：空番・3：仮登録（自動採番）・4：仮登録（手動採番）」の場合には、
		//　検索条件（仕入先コードと販区コード）に影響をもらわないので、全て表示する。
		//「1：使用中（自動採番）・2：使用中（手動採番）・9：廃番」の場合には、
		//　検索条件（仕入先コードと販区コード）に当たらない商品コードは表示しない。
		sb.append("		(JYOTAI_KANRI_KB in ('N','3','4') OR (JYOTAI_KANRI_KB IN ('1','2','9') AND YUKO_DT <> cast(null as char)))   ");
		sb.append("		fetch first 10000 rows only ");
		sb.append("	) tab4  ");


	//02コードの展開
	if( ( selGyosyuCd == 3 || selGyosyuCd == 4 )
		&& ( startSyohinCd_Mae2Keta.equals("00") || startSyohinCd_Mae2Keta.equals("01") || startSyohinCd_Mae2Keta.equals("02") ) )
	{
		//加工食品・生鮮食品且つ、開始コードの前二桁が'02'より小さい場合のみあり

		//02コードの開始コード
		String startTanpinCd = "00000";
		if( (startSyohinCd + "00").substring(0,2).equals("02") )
		{
			startTanpinCd = (startSyohinCd + "0000000").substring(2,7);
		}

		sb.append("	UNION ALL ");
		sb.append(" select * from 	(SELECT ");
		sb.append("		HANKU_CD, ");
		sb.append("		SYOHIN_CD, ");
		sb.append("		YUKO_DT, ");
		sb.append("		GYOSYU_KB, ");
		sb.append("		HINSYU_CD, ");
		sb.append("		HINMEI_KANJI_NA, ");
		sb.append("		SYOHIN_2_CD, ");
		sb.append("		GENTANKA_VL, ");
		sb.append("		BAITANKA_VL, ");
		sb.append("		SIIRESAKI_CD, ");
		sb.append("		SIIRE_HINBAN_CD, ");
		sb.append("		KIKAKU_KANJI_NA, ");
		sb.append("		INSERT_TS, ");
		sb.append("		INSERT_USER_ID, ");
		sb.append("		UPDATE_TS, ");
		sb.append("		UPDATE_USER_ID, ");
		sb.append("		DELETE_FG, ");
		sb.append("		JYOTAI_KANRI_KB, ");
		sb.append("		CODE_DELETE_FG ");
		sb.append("	FROM ");
		sb.append("		(SELECT ");
		sb.append("			NVL(SYOHIN_LIST.HANKU_CD,'') HANKU_CD, ");
		sb.append("			NVL(SYOHIN_LIST.SYOHIN_CD,TANPIN_LIST.SYOHIN_CD) SYOHIN_CD, ");
		sb.append("			NVL(SYOHIN_LIST.YUKO_DT,'') YUKO_DT, ");
		sb.append("			NVL(SYOHIN_LIST.GYOSYU_KB,'') GYOSYU_KB, ");
		sb.append("			NVL(SYOHIN_LIST.HINSYU_CD,'') HINSYU_CD, ");
		sb.append("			NVL(SYOHIN_LIST.HINMEI_KANJI_NA,'') HINMEI_KANJI_NA, ");
		sb.append("			NVL(SYOHIN_LIST.SYOHIN_2_CD,'') SYOHIN_2_CD, ");
		sb.append("			case SYOHIN_LIST.GENTANKA_VL when cast(null as char) then '' else SYOHIN_LIST.GENTANKA_VL end as GENTANKA_VL, ");
		sb.append("			case SYOHIN_LIST.BAITANKA_VL when cast(null as char) then '' else SYOHIN_LIST.BAITANKA_VL end as  BAITANKA_VL, ");
		sb.append("			NVL(SYOHIN_LIST.SIIRESAKI_CD,'') SIIRESAKI_CD, ");
		sb.append("			NVL(SYOHIN_LIST.SIIRE_HINBAN_CD,'') SIIRE_HINBAN_CD, ");
		sb.append("			NVL(SYOHIN_LIST.KIKAKU_KANJI_NA,'') KIKAKU_KANJI_NA, ");
		sb.append("			NVL(SYOHIN_LIST.INSERT_TS,'') INSERT_TS, ");
		sb.append("			NVL(SYOHIN_LIST.INSERT_USER_ID,'') INSERT_USER_ID, ");
		sb.append("			NVL(SYOHIN_LIST.UPDATE_TS,'') UPDATE_TS, ");
		sb.append("			NVL(SYOHIN_LIST.UPDATE_USER_ID,'') UPDATE_USER_ID, ");
		sb.append("			NVL(SYOHIN_LIST.DELETE_FG,'') DELETE_FG, ");
		sb.append("			NVL(TANPIN_LIST.JYOTAI_KANRI_KB,'N') JYOTAI_KANRI_KB, ");
		sb.append("			NVL(TANPIN_LIST.CODE_DELETE_FG,'') CODE_DELETE_FG ");
		sb.append("		FROM ");
		sb.append("			(SELECT ");
		sb.append("				'02' || NVL(R_SAIBAN_INSTORE.TANPIN_CD, TANPIN_LIST2.TANPIN_CD) || '00000' || NVL(R_SAIBAN_INSTORE.CHECK_DEGIT_CD, ' ') SYOHIN_CD, ");
		sb.append("				R_SAIBAN_INSTORE.JYOTAI_KANRI_KB, ");
		sb.append("				R_SAIBAN_INSTORE.DELETE_FG CODE_DELETE_FG ");
		sb.append("			FROM ");
		sb.append("				R_TANPIN_WAKU_5KETA TANPIN_LIST2  ");
		sb.append(" 				left outer join	");
		sb.append("				R_SAIBAN_INSTORE ");
		sb.append("				on TANPIN_LIST2.TANPIN_CD = R_SAIBAN_INSTORE.TANPIN_CD  ");
		sb.append("			WHERE ");
		sb.append("				TANPIN_LIST2.TANPIN_CD >= '" + startTanpinCd + "'  ");
		sb.append("			) TANPIN_LIST ");
		sb.append(" 				left outer join	");
		sb.append("			SYOHIN SYOHIN_LIST ");
		sb.append("		on ");
		sb.append("			TANPIN_LIST.SYOHIN_CD = SYOHIN_LIST.SYOHIN_CD ");
		sb.append("		) tab6 ");
		sb.append("	WHERE ");
		//■状態管理区分
		//「N：空番・3：仮登録（自動採番）・4：仮登録（手動採番）」の場合には、
		//　検索条件（仕入先コードと販区コード）に影響をもらわないので、全て表示する。
		//「1：使用中（自動採番）・2：使用中（手動採番）・9：廃番」の場合には、
		//　検索条件（仕入先コードと販区コード）に当たらない商品コードは表示しない。
		sb.append("		(JYOTAI_KANRI_KB in ('N','3','4') OR (JYOTAI_KANRI_KB IN ('1','2','9') AND YUKO_DT <> cast(null as char)))  ");
		sb.append("			fetch first 10000 rows only  ");
		sb.append("	)  tab5 ");

	}

		sb.append("ORDER BY ");
//		sb.append("	SYOHIN_CD, ");
//		sb.append("	HINSYU_CD, ");
//		sb.append("	HANKU_CD ");
		sb.append( 	strOrderBy );

		sb.append(") SYOHIN ");

		sb.append("	fetch first 10000 rows only  ");
		//sb.append("	ROWNUM < 10002 ");

//System.out.println("mst250101_syohinAkibanDM  "+sb.toString());
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
	 * 商品コード配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinList();　戻り値　商品コード配列<br>
	 * <br>
	 * @return ArrayList 商品コード配列
	 */
	public ArrayList getSyohinList() {
		return syohinList;
	}

	/**
	 * 商品コード配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinList("商品コード配列");<br>
	 * <br>
	 * @param list 商品コード配列
	 */
	public void setSyohinList(ArrayList syohinList) {
		this.syohinList = syohinList;
	}

}
