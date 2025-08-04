package mdware.master.common.command;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import jp.co.vinculumjapan.stc.util.calendar.DateDifference;
import mdware.common.batch.log.BatchLog;
import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.bean.mst001401_CheckDegitBean;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.common.dictionary.mst010001_SyuzeihokokuKbDictionary;
import mdware.master.common.dictionary.mst011701_BaikaHaishinFlagDictionary;
import mdware.master.util.db.MasterDataBase;


/**
 * <p>タイトル:商品マスタTR(グロ・バラ)の特徴項目チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2006/06/28<BR>
 * @Version 3.00 (2013.05.21) M.Ayukawa [MSTC00001] ランドローム様対応 メーカコード コード管理対応
 * @Version 3.01 (2013.10.30) K.TO [CUS00051] ランドローム様対応 メーカーコード名称対応
 * @Version 3.02 (2015.10.02) THAO.NTL FIVImart様対応
 * @Version 3.03 (2016.11.24) T.Arimoto #2803 FIVImart様対応
 * @Version 3.04 (2016.12.14) M.Akagi #2796
 * @Version 3.05 (2017.03.15) T.Arimoto #4358対応
 * @Version 3.06 (2017.10.04) T.Arimoto #5994対応
 */

public class mst38A101_SyohinCheckGRO extends SyohinCheckAbstract{

	// データベース
	private MasterDataBase dataBase = null;

	// ログ出力用変数
//	private BatchLog batchLog = BatchLog.getInstance();

	private final String BATCH_ID = "MB83-A1-01";
	private final String BATCH_NA = "登録票アップロードチェック処理";

	// 2017/10/04 T.Arimoto #5994対応 (S)
	/** 削除文字列 */
	private final String DELETE_STR = "*";
	/** 原単価、卸売価最大値 */
	private final BigDecimal KINGAKU_MAX = BigDecimal.valueOf(1000000000);
	// 2017/10/04 T.Arimoto #5994対応 (E)

	/**
	 * コンストラクタ
	 */
	public mst38A101_SyohinCheckGRO(MasterDataBase dataBase) throws SQLException {
		super(dataBase);
	}

	/**
	 * 本処理
	 *
	 * @param  targetRS ResultSet
	 * @return 処理したデータセット
	 *
	 * @throws Exception
	 */
	public ResultSet process(ResultSet targetRS) throws Exception {

		try {

			// 処理開始ログ
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "商品マスタ(グロ・バラ)の特徴項目のチェックを開始します。");

		    // データ結果
			super.setTargetRS(targetRS);

			// チェックフラグ初期化
			super.setBlCheckFlg(true);

			// TRテーブルのキーのセット処理
			super.setTRKey();

			// データベースを取得する
			dataBase = super.getDataBase();

			// 共通チェックを行う
			super.process();

			// 商品マスタ(グロ・バラ)の特徴チェック
			if (!dataCheck(targetRS)) {
				super.setBlCheckFlg(false);
			}

			// メッセージテーブルに登録する
			super.dataUpdate();

			// 処理開始ログ
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "商品マスタ(グロ・バラ)の特徴項目のチェックを終了します。");

			// エラーがある場合、nullを戻す
			if (!super.isBlCheckFlg()) {
				return null;
			}
			return targetRS;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * データチェック及び登録処理
	 *
	 * @param   rs ResultSet
	 * @return  エラーある：false、エラーなし：true
	 *
	 * @throws　SQLException
	 *
	 */
	private boolean dataCheck(ResultSet rs) throws SQLException {

		boolean checkFg = true; // エラーフラグ
		// 2017/10/04 T.Arimoto #5994対応 (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		// 2017/10/04 T.Arimoto #5994対応 (E)

		// 修正区分
		String syusei_kb = rs.getString("syusei_kb");
		String str = null;

		// 削除の場合は、主キー以外の項目をチェックする必要がない
		if(syusei_kb == null){
			return false;
		}else if(syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode()) ||syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())){
			return true;
		}
//2013.10.30 [CUS00051]  メーカーコード名称対応 (S)
		//  2013.05.17 [MSTC00001]  メーカコード コード管理対応(S)
		// メーカーコード
		//if (!isNotBlank(rs.getString("MAKER_CD"))) {
		//	if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				// 新規の場合、必須
		//		super.setPreparedMessageSQL("0629");
		//		checkFg = false;
		//	}
		//} else {
			// 名称CTFマスタに存在しない場合エラー
		//	if (!rNamectfCheck(rs, "MAKER_CK", "0631")) {
		//		checkFg = false;
		//	}
		//}

		//  2013.05.17 [MSTC00001]  メーカコード コード管理対応(E)
//2013.10.30 [CUS00051]  メーカーコード名称対応 (E)

		//  漢字品名
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			// 新規の場合、必須
			if (!isNotBlank(rs.getString("hinmei_kanji_na"))) {
				super.setPreparedMessageSQL("0146");
				checkFg = false;
			}
		}
		// 漢字規格　　チェックなし
		// 漢字規格２　チェックなし

		//#768対応 2015.10.02 THAO.NTL Del (S)
//		// カナ品名　　チェックなし
//		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//			// 新規の場合、必須
//			if (!isNotBlank(rs.getString("hinmei_kana_na"))) {
//				super.setPreparedMessageSQL("0147");
//				checkFg = false;
//			}
//		}
		//#768対応 2015.10.02 THAO.NTL Del (E)

		// カナ規格　　チェックなし
		// カナ規格２　チェックなし


//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		// 売価配信区分
		// 2016/11/22 T.Arimoto #2803対応（S)
//		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				if(isNotBlank(rs.getString("baika_haishin_fg"))){
				// 新規の場合、０だとエラー
//				if (rs.getString("baika_haishin_fg").equals(mst011701_BaikaHaishinFlagDictionary.SINAI.getCode())) {
//					super.setPreparedMessageSQL("0632");
//					checkFg = false;
//				}
//			}
//		}
		// 2016/11/22 T.Arimoto #2803対応（S)
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)

		// POSレシート品名（漢字）　チェックなし
		// POSレシート品名（カナ）　チェックなし

		// PLU送信日
        if (isNotBlank(rs.getString("plu_send_dt"))) {

			//  日付共通チェック
			if (!mst000401_LogicBean.DateChk(rs.getString("plu_send_dt"))) {
				super.setPreparedMessageSQL("0180");
				checkFg = false;
			} else {
				// 有効日が空ではない場合
				if (isNotBlank(rs.getString("yuko_dt"))) {
					// PLU送信日>=有効日ではない場合エラー
					if (DateDifference.getDifferenceDays(rs.getString("yuko_dt"), rs.getString("plu_send_dt")) < 0) {
						if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
							// 新規の場合、必須
							super.setPreparedMessageSQL("0181");
							checkFg = false;
						}
					}
					// 有効日が空の場合
				} else {
					// PLU送信日>=マスタ管理日付翌日ではない場合エラー
					if (DateDifference.getDifferenceDays(super.getMasterDT(),rs.getString("plu_send_dt")) < 1) {
						super.setPreparedMessageSQL("0182");
						checkFg = false;
					}
				}
			}
		}

//        // 店別仕入先コード（店別仕入先管理コード)

		// 仕入先商品コード　チェックなし

//		// 本部在庫区分

		// 発注単位(入数)
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
			// 新規の場合、必須
			if (!isNotBlank(rs.getString("hachutani_irisu_qt"))) {
				super.setPreparedMessageSQL("0192");
				checkFg = false;
			}
		}

		// 発注単位呼称
		if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応
//			// 新規の場合、必須
//			if (!isNotBlank(rs.getString("hachu_tani_na"))) {
//				super.setPreparedMessageSQL("0193");
//				checkFg = false;
//			}
		}

		// 最大発注単位
		if (isNotBlank(rs.getString("max_hachutani_qt"))) {
			// 2016.12.14 M.Akagi #2796 (S)
//			double i = Double.parseDouble(rs.getString("max_hachutani_qt").trim());
			//  整数以外なら、エラー
//			if (!(i == (int) i)) {
//				super.setPreparedMessageSQL("0194");
//				checkFg = false;
//			}
			// 2016.12.14 M.Akagi #2796 (E)
			//  0なら、エラー
			if (Double.parseDouble(rs.getString("max_hachutani_qt").trim()) == 0) {
				super.setPreparedMessageSQL("0194");
				checkFg = false;
			}
		}
//		//  メーカー希望小売価格（税込）

        // PC発行区分
		if (!isNotBlank(rs.getString("pc_kb"))) {
//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応
//			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
//				// 新規の場合、必須
//				super.setPreparedMessageSQL("0186");
//				checkFg = false;
//			}
		} else {
			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "pc_ck", "0187")) {
				checkFg = false;
			}
		}

        // 台紙NO
		if (isNotBlank(rs.getString("daisi_no_nb"))) {

			// 名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "daisi_no_nb_ck", "0189")) {
				checkFg = false;
			}
		}

//		// 商品台帳（店舗）

//		// 商品台帳（本部）
//
//		// 商品台帳（仕入先）

		// 酒税報告区分（グロサリーのみ）
		str = rs.getString("syuzei_hokoku_kb");
		if (isNotBlank(str)) {

			// 空ではない場合,名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "syuzei_hokoku_ck", "0200")) {
				checkFg = false;
			}

			if 	(str.trim().equals(mst010001_SyuzeihokokuKbDictionary.TAISYOGAI.getCode())) {

				// 対象外の場合はチェックなし

			} else {

				// 酒内容量が未入力の場合エラー
				if (!isNotBlank(rs.getString("sake_naiyoryo_qt"))) {
					setPreparedMessageSQL("0413");
					checkFg = false;
				} else if (rs.getLong("sake_naiyoryo_qt") <= 0) {
					setPreparedMessageSQL("0414");
					checkFg = false;
				}
			}
		}

		// ユニットプライス-単位区分
		if (isNotBlank(rs.getString("unit_price_tani_kb"))) {
			// 空ではない場合,名称CTFマスタに存在しない場合エラー
			if (!rNamectfCheck(rs, "unit_price_tani_ck", "0201")) {
				checkFg = false;
			}
		}

//		// ユニットプライス-内容量 　　　チェックなし
//		// ユニットプライス-基準単位量   チェックなし
		// ユニットプライス-内容量
		if (isNotBlank(rs.getString("unit_price_naiyoryo_qt"))) {
			if (Long.parseLong(rs.getString("unit_price_naiyoryo_qt").trim()) <= 0) {
				super.setPreparedMessageSQL("0237");
				checkFg = false;
			}
		}
		// ユニットプライス-基準単位量
		if (isNotBlank(rs.getString("unit_price_kijun_tani_qt"))) {
			if (Long.parseLong(rs.getString("unit_price_kijun_tani_qt").trim()) <= 0) {
				super.setPreparedMessageSQL("0238");
				checkFg = false;
			}
		}

//		// プライス除数（＝ユニットプライス-内容量÷ユニットプライス-基準単位量）

//		// 消費期限

        // 商品コードの重複チェック
		if (isNotBlank(rs.getString("syohin_cd"))) {
			if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
				if(!isValidSyohinCD("TR_SYOHIN", rs.getString("uketsuke_no"), rs.getString("syohin_cd"), rs.getString("yuko_dt"))) {
				super.setPreparedMessageSQL("0607");
				checkFg = false;
				}
			}
		}

		// 2017/03/15 T.Arimoto #4358対応（S)
		// 税区分
		if ( !isNotBlank(rs.getString("ZEI_KB")) && !isNotBlank(rs.getString("RS_ZEI_KB")) ) {
			super.setPreparedMessageSQL("0700");
			checkFg = false;
		}
		// 税率
		if ( !isNotBlank(rs.getString("TAX_RATE_KB")) && !isNotBlank(rs.getString("RS_TAX_RATE_KB")) ) {
			super.setPreparedMessageSQL("0701");
			checkFg = false;
		}
		// 仕入税区分
		if ( !isNotBlank(rs.getString("SIIRE_ZEI_KB")) && !isNotBlank(rs.getString("RS_SIIRE_ZEI_KB")) ) {
			super.setPreparedMessageSQL("0702");
			checkFg = false;
		}
		// 仕入税率
		if ( !isNotBlank(rs.getString("SIIRE_TAX_RATE_KB")) && !isNotBlank(rs.getString("RS_SIIRE_TAX_RATE_KB")) ) {
			super.setPreparedMessageSQL("0703");
			checkFg = false;
		}
		// 2017/03/15 T.Arimoto #4358対応（E)
		
		// 2017/10/04 T.Arimoto #5994対応 (S)
		/*** 原単価（税込み）・卸売単価（卸税適用）、卸売単価（小売税適用）チェック処理 ***/
		// 原単価（税込）計算処理
		String zeiKb = null ;
		zeiKb = rs.getString("SIIRE_ZEI_KB");
		if (!isNotBlank(zeiKb)) {
			zeiKb = rs.getString("RS_SIIRE_ZEI_KB");
		}
		int zei_kb = 0;
		if (zeiKb.equals("1")) {
			zei_kb = 2;
		} else {
			zei_kb = Integer.parseInt(zeiKb);
		}

		String gentankaNukiStr = rs.getString("gentanka_vl");
		if (!isNotBlank(gentankaNukiStr)) {
			// TR_SYOHINにてnullだった場合、R_SYOHINから取得
			gentankaNukiStr = rs.getString("rs_gentanka_nuki_vl");
		}

		//税率取得
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		str_tax_rt = rs.getString("SIIRETAX_RT");

		if (isNotBlank(str_tax_rt)) {
			if (!str_tax_rt.trim().equals(DELETE_STR)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt=null;
			}
		} else {
			str_tax_rt = rs.getString("RS_SIIRETAX_RT");
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			} else {
				tax_rt = null ;
			}
		}
		if (isNotBlank(gentankaNukiStr) && !DELETE_STR.equals(gentankaNukiStr) && null != tax_rt ) {
			BigDecimal gentanka_nuki_vl =new BigDecimal(gentankaNukiStr.trim());
			CalculateTaxUtility ctu = null;
			try {
				ctu = new CalculateTaxUtility(gentanka_nuki_vl,
						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
						MoneyUtil.getFractionCostUnitMode());
			} catch (Exception e) {
				super.setPreparedMessageSQL("0401");
				checkFg = false;
				e.printStackTrace();
				// 2017/10/06 T.Arimoto #5994対応 (S)
				return checkFg;
				// 2017/10/06 T.Arimoto #5994対応 (E)
			}
			if( null != ctu ){
				BigDecimal gentankaVl = null;
				if (zeiKb.equals("1")) {
					gentankaVl = ctu.getTaxIn();
				} else {
					gentankaVl = ctu.getTaxOut();
				}
				if( KINGAKU_MAX.compareTo(gentankaVl) <= 0 ){
					// {0}が最大値を超しました。入力金額を見直してください。
					super.setPreparedMessageSQL("0704",MessageUtil.getMessage("MST01018_TXT_00038", userLocal) );
					checkFg = false;
				}
			}
		}
		
		// 卸売単価（小売税適用・卸税適用）計算処理
		str = rs.getString("OROSI_BAITANKA_NUKI_VL");
		if(!isNotBlank(str)){
			// TR_SYOHINが空欄だった場合
			str = rs.getString("RS_OROSI_BAITANKA_NUKI_VL");
		}

		if (isNotBlank(str)) {
			String temp_orosi_st_zei_kb = rs.getString("ZEI_KB"); // 税区分
			if (!isNotBlank(temp_orosi_st_zei_kb)) {
				temp_orosi_st_zei_kb = rs.getString("RS_ZEI_KB");
			}

			String temp_tax_rt = rs.getString("TAX_RT");
			if (!isNotBlank(temp_tax_rt)) {
				temp_tax_rt = rs.getString("RS_TAX_RT");
			}
			BigDecimal temp_orosi_tax_rt = new BigDecimal(temp_tax_rt); // 税率

			int temp_orosi_zei_kb = 0; // 計算用税区分

			// 内税か判定
			if ("1".equals(temp_orosi_st_zei_kb)) {
				temp_orosi_zei_kb = 2;
			} else {
				temp_orosi_zei_kb = Integer.parseInt(temp_orosi_st_zei_kb);
			}

			// 卸売価単価(税抜き)
			BigDecimal orosi_baitanka_nuki_vl = new BigDecimal(str.trim());

			// 税計算処理
			CalculateTaxUtility ctu = null;
			try {
				ctu = new CalculateTaxUtility(orosi_baitanka_nuki_vl,
						MoneyUtil.getFractionCostUnitLen(), temp_orosi_zei_kb,
						temp_orosi_tax_rt, MoneyUtil.getFractionCostUnitMode());
			} catch (Exception e) {
				super.setPreparedMessageSQL("0401");
				checkFg = false;
				e.printStackTrace();
				// 2017/10/06 T.Arimoto #5994対応 (S)
				return checkFg;
				// 2017/10/06 T.Arimoto #5994対応 (E)
			}

			if( null != ctu ){
				BigDecimal orosiBaitankaKouri = null;
				if ("1".equals(temp_orosi_st_zei_kb)) {
					// 2017/10/06 T.Arimoto #5994対応 (S)
					// orosiBaitankaKouri = ctu.getTaxIn();
					orosiBaitankaKouri = new BigDecimal(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxIn().toString())));
					// 2017/10/06 T.Arimoto #5994対応 (E)
				} else {
					// 2017/10/06 T.Arimoto #5994対応 (S)
					// orosiBaitankaKouri = ctu.getTaxOut();
					orosiBaitankaKouri = new BigDecimal(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString())));
					// 2017/10/06 T.Arimoto #5994対応 (E)
				}
				if( KINGAKU_MAX.compareTo(orosiBaitankaKouri) <= 0){
					// {0}が最大値を超しました。入力金額を見直してください。
					// 2017/10/06 T.Arimoto #5994対応 (S)
					// super.setPreparedMessageSQL("0704",MessageUtil.getMessage("MST01018_TXT_00097", userLocal) );
					super.setPreparedMessageSQL("0705",MessageUtil.getMessage("MST01018_TXT_00097", userLocal) );
					// 2017/10/06 T.Arimoto #5994対応 (E)
					checkFg = false;
				}
			}
			
			// 卸売価単価(卸税適用)
			String oZeiKb = null ;
			oZeiKb = rs.getString("OROSI_ZEI_KB");
			if (isNotBlank(oZeiKb)) {
				if (oZeiKb.trim().equals(DELETE_STR)) {
					oZeiKb = null;
				}
			} else {
				oZeiKb = rs.getString("RS_OROSI_ZEI_KB");
			}

			// 卸税率区分
			String oZeiRtKb = null ;
			oZeiRtKb = rs.getString("OROSI_TAX_RATE_KB");
			if (isNotBlank(oZeiRtKb)) {
				if (oZeiRtKb.trim().equals(DELETE_STR)) {
					oZeiRtKb = null;
				}
			} else {
				oZeiRtKb = rs.getString("RS_OROSI_TAX_RATE_KB");
			}

			if (isNotBlank(oZeiKb) && isNotBlank(oZeiRtKb)){
				// 卸税率、卸税区分がともに入力が有ればチェックを行います
				zeiKb = oZeiKb;
				zei_kb = 0;
				if (zeiKb.equals("1")) {
					zei_kb = 2;
				} else {
					zei_kb = Integer.parseInt(zeiKb);
				}

				//卸税率取得
				str_tax_rt = rs.getString("OROSHITAX_RT");
				if (isNotBlank(str_tax_rt)) {
					if (!str_tax_rt.trim().equals(DELETE_STR)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				} else {
					str_tax_rt = rs.getString("RS_OROSHITAX_RT");
					if (isNotBlank(str_tax_rt)) {
						tax_rt = new BigDecimal(str_tax_rt.trim());
					} else {
						tax_rt = null ;
					}
				}
				// 卸売価単価(卸税適用) 計算処理
				if( null != tax_rt ){
					BigDecimal orosiBaitankaOrosi;

					ctu = null;
					try {
						ctu = new CalculateTaxUtility(orosi_baitanka_nuki_vl,
								MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
								MoneyUtil.getFractionCostUnitMode());
					} catch (Exception e) {
						super.setPreparedMessageSQL("0401");
						checkFg = false;
						e.printStackTrace();
						// 2017/10/06 T.Arimoto #5994対応 (S)
						return checkFg;
						// 2017/10/06 T.Arimoto #5994対応 (E)
					}
					if ( null !=ctu ){
						if (zeiKb.equals("1")) {
							// 2017/10/06 T.Arimoto #5994対応 (S)
							// orosiBaitankaOrosi = ctu.getTaxIn();
							orosiBaitankaOrosi = new BigDecimal(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxIn().toString())));
							// 2017/10/06 T.Arimoto #5994対応 (E)
						} else {
							// 2017/10/06 T.Arimoto #5994対応 (S)
							// orosiBaitankaOrosi = ctu.getTaxOut();
							orosiBaitankaOrosi = new BigDecimal(MoneyUtil.removeFormatMoney(MoneyUtil.formatSellUnitString(ctu.getTaxOut().toString())));
							// 2017/10/06 T.Arimoto #5994対応 (E)
						}
						if( KINGAKU_MAX.compareTo(orosiBaitankaOrosi) <= 0){
							// {0}が最大値を超しました。入力金額を見直してください。
							// 2017/10/06 T.Arimoto #5994対応 (S)
							// super.setPreparedMessageSQL("0704",MessageUtil.getMessage("MST01018_TXT_00096", userLocal) );
							super.setPreparedMessageSQL("0706",MessageUtil.getMessage("MST01018_TXT_00096", userLocal) );
							// 2017/10/06 T.Arimoto #5994対応 (E)
							checkFg = false;
						}
					}
				}
			}
		}
		// 2017/10/04 T.Arimoto #5994対応 (E)

		// チェックデジットチェック　取込時にチェック済

		return checkFg;
	}
}

