package mdware.master.common.command;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;

import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000601_GyoshuKbDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:店別商品例外のチェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2006/06/28<BR>
 * @version 1.01 (2016.11.16)  M.Akagi #2256
 * @version 1.02 (2016.12.14)  S.Takayama #2796
 * @version 1.03 (2017.10.04)  S.Takayama #5994
 * @version 1.04 (2022.07.14)  TUNG.LT #6626
 */

public class mst38A103_ReigaiCheck extends OtherCheckAbstract{


	// データベース
	private MasterDataBase dataBase = null;
	// ログ出力用変数
//	private BatchLog batchLog = BatchLog.getInstance();
	mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();

//	private final String BATCH_ID = "MB83-A1-01";
//	private final String BATCH_NA = "登録票アップロードチェック処理";

	// 206.11.16 M.Akagi #2256 (S)
	// マスタ日付
	private String masterDT = null;
	// 206.11.16 M.Akagi #2256 (E)

	/**
	 * コンストラクタ
	 */
	public mst38A103_ReigaiCheck(MasterDataBase dataBase) throws SQLException {
		super(dataBase);

		// 2016.11.16 M.Akagi #2256 (S)
		// マスタ日付を取得する
		this.setMasterDT(RMSTDATEUtil.getMstDateDt());
		// 2016.11.16 M.Akagi #2256 (E)
	}

	/**
	 * 本処理
	 *
	 * @param  targetRS ResultSet
	 * @return 処理したデータセット
	 *
	 * @throws Exception
	 */
//	===BEGIN=== Modify by kou 2006/8/21
//	 public ResultSet process(ResultSet targetRS) throws Exception {
	 public ResultSet process(ResultSet targetRS, boolean isFromBatch) throws Exception {
//	===END=== Modify by kou 2006/8/21

		try {

			// 処理開始ログ
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "店別商品例外のチェックを開始します。");

		    // データ結果
			super.setTargetRS(targetRS);

			// ===BEGIN=== Add by kou 2006/8/21
			super.setFromBatch(isFromBatch);
			// ===END=== Add by kou 2006/8/21

			// チェックフラグ初期化
			super.setBlCheckFlg(true);

//			↓↓2006.07.19 H.Yamamoto カスタマイズ修正↓↓
//			// TRテーブルのキーのセット処理
//			super.setTRKey();
//			↑↑2006.07.19 H.Yamamoto カスタマイズ修正↑↑

			// 種別区分のセット処理
			super.setSyubetuKbn(mst006401_DataKindDictionary.REIGAI.getCode());

//			↓↓2006.07.19 H.Yamamoto カスタマイズ修正↓↓
			// TRテーブルのキーのセット処理
			super.setTRKey();
//			↑↑2006.07.19 H.Yamamoto カスタマイズ修正↑↑

			// データベースを取得する
			dataBase = super.getDataBase();
//			=== BEGIN === Delete by ryo 2006/8/1
//			// 共通チェックを行う
//			super.process();
//			=== END === Delete by ryo 2006/8/1
			// 店別商品例外のチェック
			if (!dataCheck(targetRS)) {
				super.setBlCheckFlg(false);
			}

			// メッセージテーブルに登録する
			super.dataUpdate();

			// 処理開始ログ
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "店別商品例外のチェックを終了します。");

			// エラーがある場合、nullを戻す
			if (!super.isBlCheckFlg()) {
				return null;
			}
			return targetRS;

		} catch (Exception e) {
			dataBase.rollback();
//			batchLog.getLog().error(BATCH_ID, BATCH_NA, e.toString());
			throw e;
		}
	}

	/**
	 * データチェック
	 *
	 * @param   rs ResultSet
	 * @return  エラーある：false、エラーなし：true
	 *
	 * @throws　SQLException
	 *
	 */
	private boolean dataCheck(ResultSet rs) throws SQLException {

		boolean checkFg = true; // エラーフラグ
		// #5994 2017.10.04 S.Takayama (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		// #5994 2017.10.04 S.Takayama (E)

		//  修正区分
		String syusei_kb = rs.getString("syusei_kb");


		// 部門コードの存在チェック
		if (!isNotBlank(rs.getString("bunrui1_cd"))) {
			super.setPreparedMessageSQL("0113");
			checkFg = false;
		} else {
			// 名称マスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "bunrui1_ck", "0114")) {
				checkFg = false;
			}
		}

		// 商品コード
		// 新規の場合
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			// 空ではない場合
			// ===BEGIN=== Modify by kou 2006/8/21
			if(isFromBatch()){
				// バッチから呼ばされた場合
//				↓↓2006.09.08 H.Yamamoto カスタマイズ修正↓↓
//				if (isNotBlank(rs.getString("syohin_cd"))) {
				if (isNotBlank(rs.getString("syohin_cd")) && rs.getString("syohin_cd").trim().length() > 3){
//				↑↑2006.09.08 H.Yamamoto カスタマイズ修正↑↑
					// 商品マスタは空場合、エラー
					if (!isNotBlank(rs.getString("s_syohin_cd"))) {
//						=== BEGIN === Add by ryo 2006/8/7
//						super.setPreparedMessageSQL("0115");
						super.setPreparedMessageSQL("0226");
//						=== END === Add by ryo 2006/8/7
						checkFg = false;
					}
				}
			}
			// ===END== Modify by kou 2006/8/21
		// 修正の場合
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.UPDATE.getCode())) {
			// 商品コードが空なら、エラー
//			↓↓2006.09.08 H.Yamamoto カスタマイズ修正↓↓
//			if (!isNotBlank(rs.getString("syohin_cd"))) {
			if (!isNotBlank(rs.getString("syohin_cd")) || rs.getString("syohin_cd").trim().length() < 3){
//			↑↑2006.09.08 H.Yamamoto カスタマイズ修正↑↑
				super.setPreparedMessageSQL("0116");
				checkFg = false;
			} else {
				// 商品マスタになしまたは削除状態であれば、エラー
				if (!isNotBlank(rs.getString("s_syohin_cd"))) {
					super.setPreparedMessageSQL("0117");
					checkFg = false;
				}
			}
		// 削除の場合
		} else if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode())) {
			// 商品コードが空なら、エラー
//			↓↓2006.09.08 H.Yamamoto カスタマイズ修正↓↓
//			if (!isNotBlank(rs.getString("syohin_cd"))){
			if (!isNotBlank(rs.getString("syohin_cd")) || rs.getString("syohin_cd").trim().length() < 3){
//			↑↑2006.09.08 H.Yamamoto カスタマイズ修正↑↑
				super.setPreparedMessageSQL("0116");
				checkFg = false;
			} else {
				// 商品マスタになしまたは削除状態であれば、エラー
				if (!isNotBlank(rs.getString("s_syohin_cd"))) {
					super.setPreparedMessageSQL("0118");
					checkFg = false;
				}
			}

//MMここでは行わない
//			// 店別商品例外マスタに対象のレコードが存在しない場合はエラー
//			if (!isNotBlank(rs.getString("REIGAI_SYOHIN_CD"))) {
//				super.setPreparedMessageSQL("0118");
//				checkFg = false;
//			}
		}
//		=== END === Add by ryo 2006/8/1


		// 店舗コード
		if (!("000000").equals(rs.getString("tenpo_cd"))){
			//  店マスタに当該店舗コードが存在しない
			if (!rNamectfCheck(rs, "tenpo_ck", "0205")) {
				checkFg = false;
			}
		}

		// 有効日
		// 2016.11.16 M.Akagi #2256（S)
		if( null == rs.getString("ck_yuko_dt") ){
			setPreparedMessageSQL("0214");
			checkFg = false;
			return checkFg;
		}
		// 2016.11.16 M.Akagi #2256（E)
//		=== BEGIN === Modify by ryo 2006/8/4
//		if (!isNotBlank(rs.getString("yuko_dt"))) {
		if (isNotBlank(rs.getString("yuko_dt"))) {
//		=== END === Modify by ryo 2006/8/4
		    // 日付共通の妥当性チェック
		    if (!mst000401_LogicBean.DateChk(rs.getString("yuko_dt"))) {
		    	super.setPreparedMessageSQL("0214");
				checkFg = false;
			} else {
				String[] strReturn = new String[3];
				// 2016.11.16 M.Akagi #2256（S)
//				// マスタ管理日付の翌日から一年後までの間でなければエラー
				// マスタ管理日付から一年後までの間でなければエラー
				if( !rs.getString("yuko_dt").equals(masterDT) ){
					// 2016.11.16 M.Akagi #2256（E)
					strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db,rs.getString("yuko_dt"));
					// エラーの場合
					if (strReturn[0].equals(mst000101_ConstDictionary.ERR_CHK_ERROR)) {
						super.setPreparedMessageSQL("0214");
						checkFg = false;
					}
				}
			}
		}else{
			// 2008/09/18 Yokoyama グロバラ／デイリーの有効日の必須化
			if(mst000601_GyoshuKbDictionary.GRO.getCode().equals(rs.getString("excel_file_syubetsu"))
			|| mst000601_GyoshuKbDictionary.FRE.getCode().equals(rs.getString("excel_file_syubetsu"))){
				super.setPreparedMessageSQL("0264");
				checkFg = false;
			}
		}

		// 削除、または取消時は、キー項目のチェックのみでよい
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode()) || syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())) {
			return checkFg;
		}


		//		// 原価単価と売価単価のチェック
//		if ((!isNotBlank(rs.getString("gentanka_vl")))
//				&&(!isNotBlank(rs.getString("baitanka_vl")))) {
////			=== BEGIN === Delete by ryo 2006/8/1
////			// 新規の場合必須
////			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
////				super.setPreparedMessageSQL("0123");
////				checkFg = false;
////			}
////			=== END === Delete by ryo 2006/8/1
//		} else {
//			// 原価単価＞０ではない場合エラー
////			if (Double.parseDouble(rs.getString("gentanka_vl").trim()) <=0 ){
////				super.setPreparedMessageSQL("0124");
////				checkFg = false;
////			}
////			// 売価単価＞０ではない場合エラー
////			if (Double.parseDouble(rs.getString("baitanka_vl").trim()) <=0 ){
////				super.setPreparedMessageSQL("0125");
////				checkFg = false;
////			}
//			// 売価単価＞原価単価ではない場合エラー
//			if (rs.getString("baitanka_vl") != null && rs.getString("gentanka_vl") != null
//				&& Double.parseDouble(rs.getString("baitanka_vl").trim()) <=
//				Double.parseDouble(rs.getString("gentanka_vl").trim())) {
//				super.setPreparedMessageSQL("0126");
//				checkFg = false;
//			}
//		}

//		 ↓↓2006.07.12 Jiangb カスタマイズ↓↓
//		// 地区コード
//		if (!isNotBlank(rs.getString("area_cd"))) {
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				// 新規の場合、必須
//				super.setPreparedMessageSQL("0127");
//				checkFg = false;
//			}
//		} else {
//			// 名称CTFマスタに存在しない場合エラー
//			if (!rNamectfCheck(rs, "area_ck", "0128")) {
//				checkFg = false;
//			}
//		}
//		 ↑↑2006.07.12 Jiangb カスタマイズ↑↑
		// 仕入先コード
		if (!isNotBlank(rs.getString("siiresaki_cd"))) {
//			=== BEGIN === Delete by ryo 2006/8/1
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				// 新規の場合、必須
//				super.setPreparedMessageSQL("0129");
//				checkFg = false;
//			}
//			=== END === Delete by ryo 2006/8/1
		} else {
			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "siiresaki_ck", "0130")) {
				checkFg = false;
			}
		}

		// EOS区分
		if (!isNotBlank(rs.getString("eos_kb"))) {
//			=== BEGIN === Delete by ryo 2006/8/1
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				// 新規の場合、必須
//				super.setPreparedMessageSQL("0141");
//				checkFg = false;
//			}
//			=== END === Delete by ryo 2006/8/1
		} else {
//			↓↓2007.05.10 oohashi EOS対応区分
			// 値が1,2,3以外の場合、エラー
//			if (!(Long.parseLong(rs.getString("eos_kb").trim()) == 1
//					|| Long.parseLong(rs.getString("eos_kb").trim()) == 2)) {
			if (!isNotBlank(rs.getString("eos_ck"))) {
//			↑↑2007.05.10 oohashi EOS対応区分
				super.setPreparedMessageSQL("0142");
				checkFg = false;
			}
		}

//		  ↓↓2007.06.29 oohashi 物流区分対応
//		  値が登録されていて、且つ名称CTFマスタに存在しない場合エラー
		if (isNotBlank(rs.getString("buturyu_kb")) && !isNotBlank(rs.getString("buturyu_ck"))) {
			setPreparedMessageSQL("0261");
			checkFg = false;
		}
//		  ↑↑2007.06.29 oohashi 物流区分対応

		// ｸﾞﾛｻﾘｰ/ﾊﾞﾗｴﾃｨ（GRO)
		if ("GRO".equals(rs.getString("excel_file_syubetsu"))){
			if(isNotBlank(rs.getString("max_hachutani_qt"))){
				//#2796 MSTB011050 2016.12.14 S.Takayama (S)
				//double i = Double.parseDouble(rs.getString("max_hachutani_qt"));
				// 整数以外なら、エラー
				//if (!(i == (int)i)){
				//	 super.setPreparedMessageSQL("0194");
				//	 checkFg = false;
				//}
				//#2796 MSTB011050 2016.12.14 S.Takayama (E)
				// 0なら、エラー
				if (Double.parseDouble(rs.getString("max_hachutani_qt").trim()) == 0){
					 super.setPreparedMessageSQL("0194");
					 checkFg = false;
				}
			}
		}

		// ﾃﾞｲﾘｰ（FRE)
		if ("FRE".equals(rs.getString("excel_file_syubetsu"))){
			if(isNotBlank(rs.getString("max_hachutani_qt"))){
				 double i = Double.parseDouble(rs.getString("max_hachutani_qt").trim());
				 // 整数以外なら、エラー
				if (!(i == (int)i)){
					 super.setPreparedMessageSQL("0194");
					 checkFg = false;
				}
				// 0なら、エラー
				if (Double.parseDouble(rs.getString("max_hachutani_qt").trim()) == 0){
					 super.setPreparedMessageSQL("0194");
					 checkFg = false;
				}
			}

//			// ①便区分
//			if (isNotBlank(rs.getString("bin_1_kb"))){
//				// 1～8の値以外場合、エラー
//				if (1 > Long.parseLong(rs.getString("bin_1_kb").trim())
//						|| 8 < Long.parseLong(rs.getString("bin_1_kb").trim())){
//					 super.setPreparedMessageSQL("0203");
//					 checkFg = false;
//				}
//			}
//
//			// ②便区分
//			if (isNotBlank(rs.getString("bin_2_kb"))){
//				// 1～8の値以外場合、エラー
//				if (1 > Long.parseLong(rs.getString("bin_2_kb").trim())
//						|| 8 < Long.parseLong(rs.getString("bin_2_kb").trim())){
//					 super.setPreparedMessageSQL("0223");
//					 checkFg = false;
//				}
//			}
//
//			// 優先便区分
//			if(!isNotBlank(rs.getString("bin_1_kb"))
//					&& !isNotBlank(rs.getString("bin_2_kb"))){
//					if (isNotBlank(rs.getString("yusen_bin_kb"))){
//						super.setPreparedMessageSQL("0204");
//						 checkFg = false;
//					}
//			}else{
//				// 1～8の値以外場合、エラー
//				if (1 > Long.parseLong(rs.getString("yusen_bin_kb").trim())
//						|| 8 < Long.parseLong(rs.getString("yusen_bin_kb").trim())){
//					 super.setPreparedMessageSQL("0224");
//					 checkFg = false;
//				}
//			}
		}
		// #5994 2017.10.04 S.Takayama (S)
		// 原価単価(税計算処理)
		String GentankaVl = rs.getString("gentanka_vl");
		if (isNotBlank(GentankaVl)) {
			// 仕入税区分・仕入税率区分取得処理
			String zeiKb = null;
			int zei_kb = 0;
			BigDecimal tax_rt = null;
			String str_tax_rt = null;
			// 仕入税区分・仕入税率区分取得処理
			ResultSet result = dataBase.executeQuery(sqlCalculateTax(rs.getString("syohin_cd"), rs.getString("yuko_dt")));
			if (result.next()) {
				zeiKb = result.getString("SIIRE_ZEI_KB");
				if ("1".equals(zeiKb)) {
					zei_kb = 2;
				} else {
					zei_kb = Integer.parseInt(zeiKb);
				}
				str_tax_rt = result.getString("SIIRE_TAX_RATE");
				if (isNotBlank(str_tax_rt)) {
					tax_rt = new BigDecimal(str_tax_rt.trim());
				} else {
					tax_rt = null;
				}
			}
			dataBase.closeResultSet(result);

			BigDecimal gentanka_vl = new BigDecimal(GentankaVl.trim());
			String MSG = "";
			try {
				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
						MoneyUtil.getFractionCostUnitMode());
				if ("1".equals(zeiKb)) {
					GentankaVl = ctu.getTaxIn().toString();
					MSG = MessageUtil.getMessage("MST01018_TXT_00038", userLocal);
				} else {
					GentankaVl = ctu.getTaxOut().toString();
					MSG = MessageUtil.getMessage("MST01018_TXT_00075", userLocal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// #6626 Mod 2022.07.14 TUNG.LT (S)
			// BigDecimal strGentanka = new BigDecimal(GentankaVl);
			BigDecimal strGentanka = new BigDecimal(GentankaVl.trim());
			// #6626 Mod 2022.07.14 TUNG.LT (E)
			BigDecimal MAXGENTANKA = new BigDecimal("999999999.99");
			// 原単価(桁数チェック)
			if (MAXGENTANKA.compareTo(strGentanka) < 0) {
				super.setPreparedMessageSQL("0704",MSG);
				checkFg = false;
			}
		}
		// #5994 2017.10.04 S.Takayama (E)
		return checkFg;
	}

	// ===BEGIN=== Add by kou 2006/8/21
	/**
	 * @param rs
	 * @param b
	 */
	public void process(ResultSet rs) throws Exception {

		process(rs, false);

	}
	// ===END=== Add by kou 2006/8/21

	public String getMasterDT() {
		return masterDT;
	}

	public void setMasterDT(String masterDT) {
		this.masterDT = masterDT;
	}
	
	// 2017.10.04 S.Takayama (S)
	public String sqlCalculateTax(String syohinCd,String yukoDt){
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("	TR.SIIRE_ZEI_KB AS SIIRE_ZEI_KB, ");
		sql.append("	RTR.TAX_RT AS SIIRE_TAX_RATE ");
		sql.append("FROM ");
		sql.append(" ( ");
		sql.append("SELECT ");
		sql.append("	RS.SIIRE_ZEI_KB, ");
		sql.append("	RS.SIIRE_TAX_RATE_KB ");
		sql.append("FROM R_SYOHIN RS ");
		sql.append("WHERE ");
		sql.append("	RS.SYOHIN_CD = '"+syohinCd+"' ");
		sql.append("AND RS.DELETE_FG = 0 ");
		sql.append("AND RS.YUKO_DT = ( ");
		sql.append("	SELECT ");
		sql.append("		MAX(YUKO_DT) ");
		sql.append("	FROM R_SYOHIN ");
		sql.append("	WHERE ");
		sql.append("		SYOHIN_CD = '"+syohinCd+"'	");
		sql.append("	AND	YUKO_DT <= '"+yukoDt+"'	");
		sql.append(") ");
		sql.append(")	TR ");
		sql.append("INNER JOIN ");
		sql.append("R_TAX_RATE	RTR ");
		sql.append("ON ");
		sql.append("		RTR.TAX_RATE_KB = TR.SIIRE_TAX_RATE_KB ");
		sql.append("AND		RTR.YUKO_DT = ( ");
		sql.append("	SELECT ");
		sql.append("		MAX(RTR.YUKO_DT) ");
		sql.append("	FROM R_TAX_RATE RTR ");
		sql.append("	WHERE ");
		sql.append("		RTR.TAX_RATE_KB = TR.SIIRE_TAX_RATE_KB ");
		sql.append("	AND	RTR.YUKO_DT <= '"+yukoDt+"' ");
		sql.append(")");
		
		return sql.toString();
	}
	// 2017.10.04 S.Takayama (E)
}

