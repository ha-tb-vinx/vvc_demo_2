package mdware.master.batch.process.mb38;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jp.co.vinculumjapan.stc.common.util.MoneyUtil;
import mdware.common.calculate.util.CalculateTaxUtility;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst910020_EmgFlagDictionary;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:店別商品例外マスタ生成バッチ共通SQLクラス）</p>
 * <p>説明:データを登録するSQLを生成します</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.00 (2014/08/07) NGHIA-HT 海外LAWSON様通貨対応
 * @version 1.01 (2015.11.30) TU.TD FIVImart様対応
 * @Version 1.03 (2016.09.07) S.Li #1964 FIVImart対応
 * @Version 1.04 (2016.11.29) S.Takayama #2803 FIVImart対応
 * @Version 1.05 (2016.12.31) T.Han #3784 FIVImart対応
 * @Version 1.06 (2017.02.20) T.Arimoto #4102 FIVImart対応
 * @version 1.07 (2017.03.06) S.Takayama #4234 FIVImart対応
 * @version 1.08 (2017.03.15) M.Akagi #4234 FIVImart対応
 * @version 1.09 (2017.04.03) M.Akagi #4509
 * @version 1.10 (2017.04.17) S.Takayama #4694
 * @version 1.11 (2017.06.27) DAU.TQP #5488
 * @version 1.12 (2022.06.16) DINH.TP #6605
 * @version 1.13 (2022.06.29) DINH.TP #6605
 */

public class MB380008_CommonReigaiSql {

	//マスタ日付
	private String MasterDT = "";

	private final String deleteString = "*"; //削除を表す文字

	public MB380008_CommonReigaiSql(String MasterDT) {
		this.MasterDT = MasterDT;
	}

	/**
	 * 店別商品例外マスタデータ修正（新規登録）用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiUpInsSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		//販区コード
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql1.append("hanku_cd,");
		sql1.append("bunrui1_cd,");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql2.append(" ?,");

		//商品コード
		sql1.append("syohin_cd,");
		sql2.append(" ?,");

		//店舗コード
		sql1.append("tenpo_cd,");
		sql2.append(" ?,");

		//有効日
		sql1.append("yuko_dt,");
		sql2.append(" ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt,");
		sql2.append(" ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt,");
		sql2.append(" ?,");

		//原価単価
		sql1.append("gentanka_vl,");
		sql2.append(" ?,");

		//売価単価
		sql1.append("baitanka_vl,");
		sql2.append(" ?,");

		//最大発注単位
		sql1.append("max_hachutani_qt,");
		sql2.append(" ?,");

		//EOS区分
		sql1.append("eos_kb,");
		sql2.append(" ?,");

		//仕入先コード
		sql1.append("siiresaki_cd,");
		sql2.append(" ?,");

		//店別配送先コード
		sql1.append("tenbetu_haiso_cd,");
		sql2.append(" ?,");

		//①便区分
		sql1.append("bin_1_kb,");
		sql2.append(" ?,");

		//①発注パターン区分
		sql1.append("hachu_pattern_1_kb,");
		sql2.append(" ?,");

		//①締め時間
		sql1.append("sime_time_1_qt,");
		sql2.append(" ?,");

		//①センタ納品リードタイム
		sql1.append("c_nohin_rtime_1_kb,");
		sql2.append(" ?,");

		//①店納品リードタイム
		sql1.append("ten_nohin_rtime_1_kb,");
		sql2.append(" ?,");

		//①店納品時間帯
		sql1.append("ten_nohin_time_1_kb,");
		sql2.append(" ?,");

		//②便区分
		sql1.append("bin_2_kb,");
		sql2.append(" ?,");

		//②発注パターン区分
		sql1.append("hachu_pattern_2_kb,");
		sql2.append(" ?,");

		//②締め時間
		sql1.append("sime_time_2_qt,");
		sql2.append(" ?,");

		//②センタ納品リードタイム
		sql1.append("c_nohin_rtime_2_kb,");
		sql2.append(" ?,");

		//②店納品リードタイム
		sql1.append("ten_nohin_rtime_2_kb,");
		sql2.append(" ?,");

		//②店納品時間帯
		sql1.append("ten_nohin_time_2_kb,");
		sql2.append(" ?,");

		//③便区分
		sql1.append("bin_3_kb,");
		sql2.append(" ?,");

		//③発注パターン区分
		sql1.append("hachu_pattern_3_kb,");
		sql2.append(" ?,");

		//③締め時間
		sql1.append("sime_time_3_qt,");
		sql2.append(" ?,");

		//③センタ納品リードタイム
		sql1.append("c_nohin_rtime_3_kb,");
		sql2.append(" ?,");

		//③店納品リードタイム
		sql1.append("ten_nohin_rtime_3_kb,");
		sql2.append(" ?,");

		//③店納品時間帯
		sql1.append("ten_nohin_time_3_kb,");
		sql2.append(" ?,");

		//センタ納品リードタイム
		sql1.append("c_nohin_rtime_kb,");
		sql2.append(" ?,");

		//商品区分
		sql1.append("syohin_kb,");
		sql2.append(" ?,");

		//物流区分
		sql1.append("buturyu_kb,");
		sql2.append(" ?,");

		//店在庫区分
		sql1.append("ten_zaiko_kb,");
		sql2.append(" ?,");

//		↓↓2006.10.05 H.Yamamoto カスタマイズ修正↓↓
		//優先便区分
		sql1.append("yusen_bin_kb,");
		sql2.append(" ?,");
//		↑↑2006.10.05 H.Yamamoto カスタマイズ修正↑↑

		//新規登録日
		sql1.append("sinki_toroku_dt,");
		sql2.append(" ?,");

		//変更日
		sql1.append("henko_dt,");
		sql2.append("'").append(MasterDT).append("',");

		//作成年月日
		sql1.append("insert_ts,");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql2.append(" to_char(sysdate,'yyyymmddhh24miss'),");
		sql2.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//作成者ID
		sql1.append("insert_user_id,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("update_ts,");
//		sql2.append(" to_char(sysdate,'yyyymmddhh24miss'),");
		sql2.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//更新者ID
		sql1.append("update_user_id,");
		sql2.append(" ?,");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑

		//削除フラグ
		sql1.append("delete_fg");
		sql2.append(" '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		sql.append("insert into r_tensyohin_reigai ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ修正（新規登録）SQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPrepareReigaiUpInsSQL(PreparedStatement pstmt, ResultSet rs, String hanku_cd) throws SQLException {
	//No.187 MSTB011 Mod 2015.12.09 TU.TD (S)
	//public void setPrepareReigaiUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bunrui1_cd) throws SQLException {
	public void setPrepareReigaiUpInsSQL(PreparedStatement pstmt, ResultSet rs, String bunrui1_cd, MasterDataBase database) throws SQLException {
	//No.187 MSTB011 Mod 2015.12.09 TU.TD (E)

		int idx = 0;

		String str = "";
		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString(bunrui1_cd));

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

//		//有効日
		idx++;
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}

		//店舗発注開始日
		idx++;
		str = rs.getString("ten_hachu_st_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str.trim());
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_ten_hachu_st_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//店舗発注終了日
		idx++;
		str = rs.getString("ten_hachu_ed_dt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_ten_hachu_ed_dt");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//原価単価
		idx++;
		//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
		String zeiKb = null;
		int zei_kb = 0;
		BigDecimal tax_rt = null ;
		String str_tax_rt = null ;
		// #4234 MSTB011050 2017.03.06 S.Takayama (S)
		//ResultSet result = database.executeQuery(sqlCalculateTax(rs.getString("syohin_cd")));
		ResultSet result = database.executeQuery(sqlCalculateTax(rs.getString("syohin_cd"),rs.getString("yuko_dt")));
		// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		if (result.next()) {
			/* #1964 Mod 2016.09.07 Li.Sheng (S) */
			//zeiKb = result.getString("ZEI_KB");
			zeiKb = result.getString("SIIRE_ZEI_KB");
			/* #1964 Mod 2016.09.07 Li.Sheng (E) */
			if ("1".equals(zeiKb)) {
				zei_kb = 2;
			}else {
				zei_kb = Integer.parseInt(zeiKb);
			}
			/* #1964 Mod 2016.09.07 Li.Sheng (S) */
			//str_tax_rt = result.getString("TAX_RT");
			str_tax_rt = result.getString("SIIRE_TAX_RATE");
			/* #1964 Mod 2016.09.07 Li.Sheng (E) */
			if (isNotBlank(str_tax_rt)) {
				tax_rt = new BigDecimal(str_tax_rt.trim());
			}else {
				tax_rt = null;
			}
		}
        // #5488 Add 2017.06.27 DAU.TQP (S)
        database.closeResultSet(result);
        // #5488 Add 2017.06.27 DAU.TQP (E)
		//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
		// #6605 Mod 2022.06.29 DINH.TP (S)
			//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
//			BigDecimal gentanka_vl = new BigDecimal(str.trim());
//			try {
//				CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl,
//						MoneyUtil.getFractionCostUnitLen(), zei_kb, tax_rt,
//						MoneyUtil.getFractionCostUnitMode());
//				if ("1".equals(zeiKb)) {
//					str = ctu.getTaxIn().toString();
//				}else {
//					str = ctu.getTaxOut().toString();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//No.187 MSTB011 Add 2015.12.09 TU.TD (E)
//			if (!str.trim().equals(deleteString)) {
//			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
//		} else {
//				pstmt.setNull(idx, Types.DOUBLE);
//			}
			if (!str.trim().equals(deleteString)) {
				BigDecimal gentanka_vl = new BigDecimal(str.trim());
				try {
					CalculateTaxUtility ctu = new CalculateTaxUtility(gentanka_vl, MoneyUtil.getFractionCostUnitLen(),
							zei_kb, tax_rt, MoneyUtil.getFractionCostUnitMode());
					if ("1".equals(zeiKb)) {
						str = ctu.getTaxIn().toString();
					} else {
						str = ctu.getTaxOut().toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		// #6605 Mod 2022.06.29 DINH.TP (E)
		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
			str = rs.getString("rtr_gentanka_vl");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//売価単価(税込)
		idx++;
		str = rs.getString("baitanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
		} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
//			pstmt.setNull(idx, Types.BIGINT);
			str = rs.getString("rtr_baitanka_vl");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}

		//最大発注単位
		idx++;
		str = rs.getString("max_hachutani_qt");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setDouble(idx, Double.parseDouble(str.trim()));
		} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		} else {
//			pstmt.setNull(idx, Types.BIGINT);
			str = rs.getString("rtr_max_hachutani_qt");
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.BIGINT);
			}
		}

		//EOS区分
		idx++;
		str = rs.getString("eos_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_eos_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//仕入先コード
		idx++;
		str = rs.getString("siiresaki_cd");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_siiresaki_cd");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//店別配送先コード
		idx++;
		str = rs.getString("tenbetu_haiso_cd");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①便区分
		idx++;
		str = rs.getString("bin_1_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_bin_1_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//①発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①締め時間
		idx++;
		str = rs.getString("sime_time_1_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//①センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//①店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_1_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②便区分
		idx++;
		str = rs.getString("bin_2_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
			str = rs.getString("rtr_bin_2_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//②発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②締め時間
		idx++;
		str = rs.getString("sime_time_2_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//②センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//②店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_2_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③便区分
		idx++;
		str = rs.getString("bin_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③発注パターン区分
		idx++;
		str = rs.getString("hachu_pattern_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③締め時間
		idx++;
		str = rs.getString("sime_time_3_qt");
		if (isNotBlank(str)) {
			pstmt.setLong(idx, Long.parseLong(str.trim()));
		} else {
			pstmt.setNull(idx, Types.BIGINT);
		}

		//③センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③店納品リードタイム
		idx++;
		str = rs.getString("ten_nohin_rtime_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//③店納品時間帯
		idx++;
		str = rs.getString("ten_nohin_time_3_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//センタ納品リードタイム
		idx++;
		str = rs.getString("c_nohin_rtime_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//商品区分
		idx++;
		str = rs.getString("syohin_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//物流区分
		idx++;
		str = rs.getString("buturyu_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_buturyu_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//店在庫区分
		idx++;
		str = rs.getString("ten_zaiko_kb");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//優先便区分
		idx++;
		str = rs.getString("yusen_bin_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_yusen_bin_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}

		//PLU送信日
		// #2803 MSTB011050 2016.11.29 S.Takayama (S)
		String pluSendDt = "";
		// #2803 MSTB011050 2016.11.29 S.Takayama (E)
		idx++;
		str = rs.getString("plu_send_dt");
		if (!isNotBlank(str)) {
			str = rs.getString("rtr_plu_send_dt");
		}
		if (!isNotBlank(str)) {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		} else {
//			if (!str.trim().equals(deleteString) && DateDiff.getDiffDays(rs.getString("yuko_dt"), str) >= 0) {
			if (!str.trim().equals(deleteString) && rs.getString("yuko_dt").compareTo(str) <= 0) {

				pstmt.setString(idx, str);
				// #2803 MSTB011050 2016.11.29 S.Takayama (S)
				pluSendDt = str;
				// #2803 MSTB011050 2016.11.29 S.Takayama (E)
			} else {
				pstmt.setString(idx, rs.getString("yuko_dt"));
				// #2803 MSTB011050 2016.11.29 S.Takayama (S)
				pluSendDt = rs.getString("yuko_dt");
				// #2803 MSTB011050 2016.11.29 S.Takayama (E)
			}
		}

		//新規登録日
		idx++;
		str = rs.getString("sinki_toroku_dt");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		idx++;
		str = rs.getString("gentanka_vl");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			// #36271 MOD 2025.07.08 THONG.LT (S)
			//str = rs.getString("rtr_gentanka_vl");
			str = rs.getString("rtr_gentanka_nuki_vl");
			// #36271 MOD 2025.07.08 THONG.LT (E)
			if (isNotBlank(str)) {
				pstmt.setDouble(idx, Double.parseDouble(str.trim()));
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
		//	作成者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}

		//	更新者ID
		idx++;
		str = rs.getString("by_no");
		if (isNotBlank(str)) {
			pstmt.setString(idx, str);
		} else {
			pstmt.setNull(idx, Types.VARCHAR);
		}
		
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		//緊急配信フラグ
		idx++;
		// #2803 MSTB011050 2016.11.29 S.Takayama (S)
		//pstmt.setString(idx, "0");
		// PLU反映時間の取得
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (str.trim().equals(deleteString)) {
				str = "";
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (!isNotBlank(str) || !pluSendDt.equals(rs.getString("rtr_plu_send_dt"))) {
				str = "";
			}
		}
		if (pluSendDt.equals(MasterDT) || isNotBlank(str)) {
			// PLU反映日がオンライン日付かPLU反映時間が設定されている場合
			pstmt.setString(idx, "1");
		}else{
			pstmt.setString(idx, "0");
		}
		// #2803 MSTB011050 2016.11.29 S.Takayama (E)
		
		//PLU反映時間
		idx++;
		// 2016/11/29 T.Arimoto #2803対応（S)
//		str = rs.getString("plu_hanei_time");
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		str = rs.getString("plu_hanei_time");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_plu_hanei_time");
			if (isNotBlank(str) && pluSendDt.equals(rs.getString("rtr_plu_send_dt"))) {
				// PLU反映日を引き継いでいた場合、PLU反映時間も引き継ぐ
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)
		
		//発注不可フラグ
		idx++;
		str = rs.getString("hachu_fuka_flg");
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.VARCHAR);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_hachu_fuka_flg");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				// 2017.01.31 T.han #3784対応（S)
				//pstmt.setNull(idx, Types.VARCHAR);
				pstmt.setString(idx, "0");
				// 2017.01.31 T.han #3784対応（E)
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)
		
		//卸売価単価(税抜)
		idx++;
		str = rs.getString("orosi_baitanka_nuki_vl");
		// 2016/11/29 T.Arimoto #2803対応（S)
//		if (isNotBlank(str)) {
//			pstmt.setString(idx, str);
//		} else {
//			pstmt.setNull(idx, Types.DOUBLE);
//		}
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		} else {
			str = rs.getString("rtr_orosi_baitanka_nuki_vl");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setNull(idx, Types.DOUBLE);
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)
		  
        /* #1964 Add 2016.09.07 Li.Sheng (E) */
		
		// 2016/11/29 T.Arimoto #2803対応（S)
		idx++;
		// 取扱停止
		str = rs.getString("BAIKA_HAISHIN_FG");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		} else {
			str = rs.getString("RTR_BAIKA_HAISHIN_FG");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}
		// 2016/11/29 T.Arimoto #2803対応（E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		idx++;
		str = rs.getString("siire_kahi_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_siire_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}

		//返品可否区分
		idx++;
		str = rs.getString("henpin_kahi_kb");
		if (isNotBlank(str)) {
			if (!str.trim().equals(deleteString)) {
			pstmt.setString(idx, str);
		} else {
				pstmt.setNull(idx, Types.VARCHAR);
			}
		} else {
			str = rs.getString("rtr_henpin_kahi_kb");
			if (isNotBlank(str)) {
				pstmt.setString(idx, str);
			} else {
				pstmt.setString(idx, "0");
			}
		}
// 2017.01.31 T.han #3784対応（E)

	}

	/**
	 * 店別商品例外マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiDelInsSQL(MasterDataBase dataBase,ResultSet rs) throws SQLException {
		StringBuffer sql = new StringBuffer();

//		↓↓2006.09.19 H.Yamamoto カスタマイズ修正↓↓
		String delYukoDt = rs.getString("yuko_dt");
		if(delYukoDt==null || delYukoDt.trim().equals("")){
			delYukoDt = DateChanger.addDate(MasterDT, 1);
		}
//		↑↑2006.09.19 H.Yamamoto カスタマイズ修正↑↑

		sql.append("insert into ");
		sql.append("r_tensyohin_reigai ");
		sql.append("(");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("  hanku_cd,");
		sql.append("  bunrui1_cd,");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append("  syohin_cd,");
		sql.append("  tenpo_cd,");
		sql.append("  yuko_dt,");
		sql.append("  ten_hachu_st_dt,");
		sql.append("  ten_hachu_ed_dt,");
		sql.append("  gentanka_vl,");
		sql.append("  baitanka_vl,");
		sql.append("  max_hachutani_qt,");
		sql.append("  eos_kb,");
		sql.append("  siiresaki_cd,");
		sql.append("  tenbetu_haiso_cd,");
		sql.append("  bin_1_kb,");
		sql.append("  hachu_pattern_1_kb,");
		sql.append("  sime_time_1_qt,");
		sql.append("  c_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_time_1_kb,");
		sql.append("  bin_2_kb,");
		sql.append("  hachu_pattern_2_kb,");
		sql.append("  sime_time_2_qt,");
		sql.append("  c_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_time_2_kb,");
		sql.append("  bin_3_kb,");
		sql.append("  hachu_pattern_3_kb,");
		sql.append("  sime_time_3_qt,");
		sql.append("  c_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_time_3_kb,");
		sql.append("  c_nohin_rtime_kb,");
		sql.append("  syohin_kb,");
		sql.append("  buturyu_kb,");
		sql.append("  ten_zaiko_kb,");
		sql.append("  sinki_toroku_dt,");
		sql.append("  henko_dt,");
		sql.append("  insert_ts,");
		sql.append("  insert_user_id,");
		sql.append("  update_ts,");
		sql.append("  update_user_id,");
		sql.append("  delete_fg");
		sql.append(") ");
		sql.append("(");
		sql.append("select");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("  hanku_cd,");
		sql.append("  bunrui1_cd,");
		sql.append("  syohin_cd,");
		sql.append("  tenpo_cd,");
//		↓↓2006.09.19 H.Yamamoto カスタマイズ修正↓↓
//		sql.append("   '"+ rs.getString("yuko_dt") + "',");
		sql.append("   '"+ delYukoDt + "',");
//		↑↑2006.09.19 H.Yamamoto カスタマイズ修正↑↑
		sql.append("  ten_hachu_st_dt,");
		sql.append("  ten_hachu_ed_dt,");
// ↓↓(2005.10.11)カンマ制御処理追加
//		sql.append("  replace(gentanka_vl,','),");
//		sql.append("  replace(baitanka_vl,','),");
		sql.append("  gentanka_vl,");
		sql.append("  baitanka_vl,");
// ↑↑(2005.10.11)カンマ制御処理追加
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append("  max_hachutani_qt,");
		sql.append("  eos_kb,");
		sql.append("  siiresaki_cd,");
		sql.append("  tenbetu_haiso_cd,");
		sql.append("  bin_1_kb,");
		sql.append("  hachu_pattern_1_kb,");
		sql.append("  sime_time_1_qt,");
		sql.append("  c_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_time_1_kb,");
		sql.append("  bin_2_kb,");
		sql.append("  hachu_pattern_2_kb,");
		sql.append("  sime_time_2_qt,");
		sql.append("  c_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_time_2_kb,");
		sql.append("  bin_3_kb,");
		sql.append("  hachu_pattern_3_kb,");
		sql.append("  sime_time_3_qt,");
		sql.append("  c_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_time_3_kb,");
		sql.append("  c_nohin_rtime_kb,");
		sql.append("  syohin_kb,");
		sql.append("  buturyu_kb,");
		sql.append("  ten_zaiko_kb,");
		sql.append("  sinki_toroku_dt,");
		sql.append("'").append(MasterDT).append("',");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("  to_char(sysdate,'yyyymmddhh24miss'),");
//		sql.append("'").append(BATCH_ID).append("',");
//		sql.append("  to_char(sysdate,'yyyymmddhh24miss'),");
//		sql.append("'").append(BATCH_ID).append("',");
		sql.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '"+ rs.getString("by_no") + "',");
		sql.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '"+ rs.getString("by_no") + "',");
		sql.append("'").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		sql.append("from");
		sql.append("  r_tensyohin_reigai ");
		sql.append("where ");
//		sql.append("  hanku_cd = ? and");
		sql.append("  bunrui1_cd = ? and");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ? and");
		sql.append("  yuko_dt = ? )");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ削除SQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPrepareReigaiDelInsSQL(PreparedStatement pstmt, ResultSet rs, String delete_dt, String hanku_cd) throws SQLException {
	public void setPrepareReigaiDelInsSQL(PreparedStatement pstmt, ResultSet rs, String delete_dt, String bunrui1_cd) throws SQLException {
// ↑↑2006.06.30 guohy カスタマイズ修正↑↑
		int idx = 0;

//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		//有効日
//		idx++;
//		pstmt.setString(idx, rs.getString("yuko_dt"));

//		//販区コード
//		idx++;
//		pstmt.setString(idx, rs.getString(hanku_cd));

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString(bunrui1_cd));
//		 ↑↑2006.06.30 guohy カスタマイズ修正↑↑

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

		//有効日（削除対象の有効日）
		idx++;
//		↓↓2007.03.05 H.Yamamoto カスタマイズ修正↓↓
//		pstmt.setString(idx, delete_dt);
//		↑↑2007.03.05 H.Yamamoto カスタマイズ修正↑↑
//      ===BEGIN=== Modify by ryo 2006/8/3
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
//      pstmt.setString(idx, delete_dt);
		if (delete_dt == null || delete_dt.trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, delete_dt);
		}
//      ===END=== Modify by ryo 2006/8/3
	}

	/**
	 * 店別商品例外マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiDelUpSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("update ");
		sql.append("  r_tensyohin_reigai ");
		sql.append("set ");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
		sql.append("  henko_dt = '").append(MasterDT).append("',");
//		sql.append("  update_ts = to_char(sysdate,'yyyymmddhh24miss'),");
//		sql.append("  update_user_id = '").append(BATCH_ID).append("',");
		sql.append("  update_ts =  '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  update_user_id = ?,");
		sql.append("  delete_fg = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		sql.append("where ");
//		sql.append("  hanku_cd = ? and");
		sql.append("  bunrui1_cd = ? and");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ? and");
		sql.append("  yuko_dt = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタデータ削除SQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPrepareReigaiDelUpSQL(PreparedStatement pstmt, ResultSet rs, String hanku_cd) throws SQLException {
	public void setPrepareReigaiDelUpSQL(PreparedStatement pstmt, ResultSet rs, String bunrui1_cd) throws SQLException {

		int idx = 0;
//		//販区コード
//		idx++;
//		pstmt.setString(idx, rs.getString(hanku_cd));

		//更新者ID
		idx++;
		pstmt.setString(idx, rs.getString("by_no"));

		//部門コード
		idx++;
		pstmt.setString(idx, rs.getString(bunrui1_cd));
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑

		//商品コード
		idx++;
		pstmt.setString(idx, rs.getString("syohin_cd"));

		//店舗コード
		idx++;
		pstmt.setString(idx, rs.getString("tenpo_cd"));

		//有効日
		idx++;
//		===BEGIN=== Modify by ryo 2006/8/4
//		pstmt.setString(idx, rs.getString("yuko_dt"));
		// 有効開始日が未入力の場合、管理日付の翌日でセットする
		if (rs.getString("yuko_dt")==null || rs.getString("yuko_dt").trim().equals("")) {
			String startDt = DateChanger.addDate(MasterDT, 1);
			pstmt.setString(idx, startDt);
		} else {
			pstmt.setString(idx, rs.getString("yuko_dt"));
		}
//		===END=== Modify by ryo 2006/8/4
	}

	private boolean isNotBlank(String val) {
		// #6605 MOD DINH.TP 29.06.2022 (S)
		// #6605 MOD DINH.TP 16.06.2022 (S)
//		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		// #6605 MOD DINH.TP 16.06.2022 (E)
		// #6605 MOD DINH.TP 29.06.2022 (E)
		return false;
	}

	//No.187 MSTB011 Add 2015.12.09 TU.TD (S)
	// #4234 MSTB011050 2017.03.06 S.Takayama (S)
	//public String sqlCalculateTax(String syohinCd){
	public String sqlCalculateTax(String syohinCd,String yukoDt){
	// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		//2016/08/04 VINX s.sakamoto #対応 (S)
//		sql.append("	TR.TR_ZEI_KB AS ZEI_KB, ");
//		sql.append("	RTR.TAX_RT AS TAX_RT ");
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("	TR.TR_SIIRE_ZEI_KB AS SIIRE_ZEI_KB, ");
		sql.append("	TR.SIIRE_ZEI_KB AS SIIRE_ZEI_KB, ");
		// 2017/02/20 T.Arimoto #4102対応（E)
		sql.append("	RTR.TAX_RT AS SIIRE_TAX_RATE ");
		//2016/08/04 VINX s.sakamoto #対応 (E)
		sql.append("FROM ");
		sql.append(" ( ");
		sql.append("SELECT ");
		//2016/08/04 VINX s.sakamoto #対応 (S)
//		sql.append("	TS.TR_ZEI_KB, ");
//		sql.append("	TS.TR_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("	TS.TR_SIIRE_ZEI_KB, ");
		//sql.append("	TS.TR_SIIRE_TAX_RATE_KB ");
		sql.append("	RS.SIIRE_ZEI_KB, ");
		sql.append("	RS.SIIRE_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（E)
		//2016/08/04 VINX s.sakamoto #対応 (E)
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("FROM TR_SYOHIN TS ");
		sql.append("FROM R_SYOHIN RS ");
		// 2017/02/20 T.Arimoto #4102対応（E)
		sql.append("WHERE ");
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("	TS.TR_SYOHIN_CD = '"+syohinCd+"' ");
		//sql.append("AND TS.DELETE_FG = 0 ");
		//sql.append("AND TS.TR_YUKO_DT = ( ");
		sql.append("	RS.SYOHIN_CD = '"+syohinCd+"' ");
		sql.append("AND RS.DELETE_FG = 0 ");
		// 2017.03.15 M.Akagi #4234 (S)
// #4234 MSTB011050 2017.03.06 S.Takayama (S)
		sql.append("AND RS.YUKO_DT = ( ");
//		// 2017/02/20 T.Arimoto #4102対応（E)
		sql.append("	SELECT ");
//		// 2017/02/20 T.Arimoto #4102対応（S)
//		//sql.append("		MAX(TR_YUKO_DT) ");
//		//sql.append("	FROM TR_SYOHIN ");
		sql.append("		MAX(YUKO_DT) ");
		sql.append("	FROM R_SYOHIN ");
//		// 2017/02/20 T.Arimoto #4102対応（E)
		sql.append("	WHERE ");
//		// 2017/02/20 T.Arimoto #4102対応（S)
//		//sql.append("		TR_SYOHIN_CD = '"+syohinCd+"'	");
//		//sql.append("	AND	TR_YUKO_DT <= '"+MasterDT+"'	");
		sql.append("		SYOHIN_CD = '"+syohinCd+"'	");
//		sql.append("	AND	YUKO_DT <= '"+MasterDT+"'	");
		sql.append("	AND	YUKO_DT <= '"+yukoDt+"'	");
//		// 2017/02/20 T.Arimoto #4102対応（E)
//		sql.append("	AND	DELETE_FG = 0 ");
		sql.append(") ");
//		sql.append("AND RS.YUKO_DT <= '"+yukoDt+"' ");
// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		// 2017.03.15 M.Akagi #4234 (E)
		sql.append(")	TR ");
		sql.append("INNER JOIN ");
		sql.append("R_TAX_RATE	RTR ");
		sql.append("ON ");
		//2016/08/04 VINX s.sakamoto #対応 (S)
//		sql.append("		RTR.TAX_RATE_KB = TR.TR_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("		RTR.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB ");
		sql.append("		RTR.TAX_RATE_KB = TR.SIIRE_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（E)
		//2016/08/04 VINX s.sakamoto #対応 (E)
		sql.append("AND		RTR.YUKO_DT = ( ");
		sql.append("	SELECT ");
		sql.append("		MAX(RTR.YUKO_DT) ");
		sql.append("	FROM R_TAX_RATE RTR ");
		sql.append("	WHERE ");
		//2016/08/04 VINX s.sakamoto #対応 (S)
//		sql.append("		RTR.TAX_RATE_KB = TR.TR_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（S)
		//sql.append("		RTR.TAX_RATE_KB = TR.TR_SIIRE_TAX_RATE_KB ");
		sql.append("		RTR.TAX_RATE_KB = TR.SIIRE_TAX_RATE_KB ");
		// 2017/02/20 T.Arimoto #4102対応（E)
		//2016/08/04 VINX s.sakamoto #対応(E)
		// #4234 MSTB011050 2017.03.06 S.Takayama (S)
		//sql.append("	AND	RTR.YUKO_DT <= '"+MasterDT+"' ");
		sql.append("	AND	RTR.YUKO_DT <= '"+yukoDt+"' ");
		// #4234 MSTB011050 2017.03.06 S.Takayama (E)
		sql.append(")");

		return sql.toString();
	}
	//No.187 MSTB011 Add 2015.12.09 TU.TD (E)


//================================================================
//  MM用
//================================================================

	/**
	 * 店別商品例外マスタデータ修正（新規登録）用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiUpInsSQL(MasterDataBase dataBase, String batchId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		// 分類１コード
		sql1.append("bunrui1_cd,");
		sql2.append(" ?,");

		//商品コード
		sql1.append("syohin_cd,");
		sql2.append(" ?,");

		//店舗コード
		sql1.append("tenpo_cd,");
		sql2.append(" ?,");

		//有効日
		sql1.append("yuko_dt,");
		sql2.append(" ?,");

		//店舗発注開始日
		sql1.append("ten_hachu_st_dt,");
		sql2.append(" ?,");

		//店舗発注終了日
		sql1.append("ten_hachu_ed_dt,");
		sql2.append(" ?,");

		//原価単価
		sql1.append("gentanka_vl,");
		sql2.append(" ?,");

		//売価単価
		sql1.append("baitanka_vl,");
		sql2.append(" ?,");

		//最大発注単位
		sql1.append("max_hachutani_qt,");
		sql2.append(" ?,");

		//EOS区分
		sql1.append("eos_kb,");
		sql2.append(" ?,");

		//仕入先コード
		sql1.append("siiresaki_cd,");
		sql2.append(" ?,");

		//店別配送先コード
		sql1.append("tenbetu_haiso_cd,");
		sql2.append(" ?,");

		//①便区分
		sql1.append("bin_1_kb,");
		sql2.append(" ?,");

		//①発注パターン区分
		sql1.append("hachu_pattern_1_kb,");
		sql2.append(" ?,");

		//①締め時間
		sql1.append("sime_time_1_qt,");
		sql2.append(" ?,");

		//①センタ納品リードタイム
		sql1.append("c_nohin_rtime_1_kb,");
		sql2.append(" ?,");

		//①店納品リードタイム
		sql1.append("ten_nohin_rtime_1_kb,");
		sql2.append(" ?,");

		//①店納品時間帯
		sql1.append("ten_nohin_time_1_kb,");
		sql2.append(" ?,");

		//②便区分
		sql1.append("bin_2_kb,");
		sql2.append(" ?,");

		//②発注パターン区分
		sql1.append("hachu_pattern_2_kb,");
		sql2.append(" ?,");

		//②締め時間
		sql1.append("sime_time_2_qt,");
		sql2.append(" ?,");

		//②センタ納品リードタイム
		sql1.append("c_nohin_rtime_2_kb,");
		sql2.append(" ?,");

		//②店納品リードタイム
		sql1.append("ten_nohin_rtime_2_kb,");
		sql2.append(" ?,");

		//②店納品時間帯
		sql1.append("ten_nohin_time_2_kb,");
		sql2.append(" ?,");

		//③便区分
		sql1.append("bin_3_kb,");
		sql2.append(" ?,");

		//③発注パターン区分
		sql1.append("hachu_pattern_3_kb,");
		sql2.append(" ?,");

		//③締め時間
		sql1.append("sime_time_3_qt,");
		sql2.append(" ?,");

		//③センタ納品リードタイム
		sql1.append("c_nohin_rtime_3_kb,");
		sql2.append(" ?,");

		//③店納品リードタイム
		sql1.append("ten_nohin_rtime_3_kb,");
		sql2.append(" ?,");

		//③店納品時間帯
		sql1.append("ten_nohin_time_3_kb,");
		sql2.append(" ?,");

		//センタ納品リードタイム
		sql1.append("c_nohin_rtime_kb,");
		sql2.append(" ?,");

		//商品区分
		sql1.append("syohin_kb,");
		sql2.append(" ?,");

		//物流区分
		sql1.append("buturyu_kb,");
		sql2.append(" ?,");

		//店在庫区分
		sql1.append("ten_zaiko_kb,");
		sql2.append(" ?,");

		//優先便区分
		sql1.append("yusen_bin_kb,");
		sql2.append(" ?,");

		//新規登録日
		sql1.append("plu_send_dt,");
		sql2.append(" ?,");

		//新規登録日
		sql1.append("sinki_toroku_dt,");
		sql2.append(" ?,");

		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		//原価単価（税抜）
		sql1.append("gentanka_nuki_vl,");
		sql2.append(" ?,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)

		//変更日
		sql1.append("henko_dt,");
		sql2.append("'").append(MasterDT).append("',");

		//作成年月日
		sql1.append("insert_ts,");
		sql2.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//作成者ID
		sql1.append("insert_user_id,");
		sql2.append(" ?,");

		//更新年月日
		sql1.append("update_ts,");
		sql2.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//更新者ID
		sql1.append("update_user_id,");
		sql2.append(" ?,");

		//バッチ更新年月日
		sql1.append("batch_update_ts,");
		sql2.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");

		//バッチ更新者ID
		sql1.append("batch_update_id,");
		sql2.append(" '" + batchId + "', ");
		
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		sql1.append("emg_flag,");
		sql2.append(" ?,");
		sql1.append("plu_hanei_time,");
		sql2.append(" ?,");
		sql1.append("hachu_fuka_flg,");
		sql2.append(" ?,");
		sql1.append("orosi_baitanka_nuki_vl,");
		sql2.append(" ?,");   
        /* #1964 Add 2016.09.07 Li.Sheng (E) */

		// #2803 MSTB011050 2016.11.29 S.Takayama (S)
		// 取扱停止
		sql1.append("BAIKA_HAISHIN_FG,");
		sql2.append(" ?,");
		// #2803 MSTB011050 2016.11.29 S.Takayama (E)

// 2017.01.31 T.han #3784対応（S)
		//仕入可否区分
		sql1.append("siire_kahi_kb, ");
		sql2.append(" ?,");
		//返品可否区分
		sql1.append("henpin_kahi_kb, ");
		sql2.append(" ?,");
// 2017.01.31 T.han #3784対応（E)

		//削除フラグ
		sql1.append("delete_fg");
		sql2.append(" '").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'");

		sql.append("insert into r_tensyohin_reigai ");
		sql.append("( ");
		sql.append(sql1.toString());
		sql.append(") values ( ");
		sql.append(sql2.toString());
		sql.append(") ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * 店別商品例外マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiDelUpSQL(MasterDataBase dataBase, String batchId) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("update ");
		sql.append("  r_tensyohin_reigai ");
		sql.append("set ");
		sql.append("  henko_dt = '").append(MasterDT).append("',");
		sql.append("  update_ts =  '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  update_user_id = ?,");
		sql.append("  batch_update_ts =  '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("  batch_update_id =  '"+ batchId + "', ");
		sql.append("  delete_fg = '").append(mst000801_DelFlagDictionary.IRU.getCode()).append("' ");
		// 2017.04.03 M.Akagi #4509 (S)
		sql.append("  ,PLU_HANEI_TIME = NULL ");
		sql.append("  ,EMG_FLAG = '" + mst910020_EmgFlagDictionary.OFF.getCode() + "' ");
		// 2017.04.03 M.Akagi #4509 (E)
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ? and");
		sql.append("  yuko_dt = ? ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}


	/**
	 * 店別商品例外マスタデータ削除用PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedReigaiDelInsSQL(MasterDataBase dataBase,ResultSet rs, String batchId) throws SQLException {
		StringBuffer sql = new StringBuffer();

//		↓↓2006.09.19 H.Yamamoto カスタマイズ修正↓↓
		String delYukoDt = rs.getString("yuko_dt");
		if(delYukoDt==null || delYukoDt.trim().equals("")){
			delYukoDt = DateChanger.addDate(MasterDT, 1);
		}
//		↑↑2006.09.19 H.Yamamoto カスタマイズ修正↑↑

		sql.append("insert into ");
		sql.append("r_tensyohin_reigai ");
		sql.append("(");
		sql.append("  bunrui1_cd,");
		sql.append("  syohin_cd,");
		sql.append("  tenpo_cd,");
		sql.append("  yuko_dt,");
		sql.append("  ten_hachu_st_dt,");
		sql.append("  ten_hachu_ed_dt,");
		sql.append("  gentanka_vl,");
		sql.append("  baitanka_vl,");
		sql.append("  max_hachutani_qt,");
		sql.append("  eos_kb,");
		sql.append("  siiresaki_cd,");
		sql.append("  tenbetu_haiso_cd,");
		sql.append("  bin_1_kb,");
		sql.append("  hachu_pattern_1_kb,");
		sql.append("  sime_time_1_qt,");
		sql.append("  c_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_time_1_kb,");
		sql.append("  bin_2_kb,");
		sql.append("  hachu_pattern_2_kb,");
		sql.append("  sime_time_2_qt,");
		sql.append("  c_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_time_2_kb,");
		sql.append("  bin_3_kb,");
		sql.append("  hachu_pattern_3_kb,");
		sql.append("  sime_time_3_qt,");
		sql.append("  c_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_time_3_kb,");
		sql.append("  c_nohin_rtime_kb,");
		sql.append("  syohin_kb,");
		sql.append("  buturyu_kb,");
		sql.append("  ten_zaiko_kb,");
		sql.append("  sinki_toroku_dt,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		sql.append("  gentanka_nuki_vl,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)
		sql.append("  henko_dt,");
		sql.append("  insert_ts,");
		sql.append("  insert_user_id,");
		sql.append("  update_ts,");
		sql.append("  update_user_id,");
		sql.append("  batch_update_ts,");
		sql.append("  batch_update_id,");
		sql.append("  delete_fg,");
		sql.append("  yusen_bin_kb,");
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		sql.append("  emg_flag,");
		sql.append("  plu_hanei_time,");
		sql.append("  hachu_fuka_flg,");
		sql.append("  orosi_baitanka_nuki_vl,");     
        /* #1964 Add 2016.09.07 Li.Sheng (E) */		
		// 2017.01.31 T.han #3784対応（S)
		sql.append("  siire_kahi_kb,");
		sql.append("  henpin_kahi_kb,");
		// 2017.01.31 T.han #3784対応（E)
		sql.append("  plu_send_dt");
		// #4694 MSTB011050 2017.04.17 S.Takayama (S)
		sql.append("  , baika_haishin_fg");
		// #4694 MSTB011050 2017.04.17 S.Takayama (E)
		sql.append(") ");
		sql.append("(");
		sql.append("select");
		sql.append("  bunrui1_cd,");
		sql.append("  syohin_cd,");
		sql.append("  tenpo_cd,");
		sql.append("   '"+ delYukoDt + "',");
		sql.append("  ten_hachu_st_dt,");
		sql.append("  ten_hachu_ed_dt,");
		sql.append("  gentanka_vl,");
		sql.append("  baitanka_vl,");
		sql.append("  max_hachutani_qt,");
		sql.append("  eos_kb,");
		sql.append("  siiresaki_cd,");
		sql.append("  tenbetu_haiso_cd,");
		sql.append("  bin_1_kb,");
		sql.append("  hachu_pattern_1_kb,");
		sql.append("  sime_time_1_qt,");
		sql.append("  c_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_rtime_1_kb,");
		sql.append("  ten_nohin_time_1_kb,");
		sql.append("  bin_2_kb,");
		sql.append("  hachu_pattern_2_kb,");
		sql.append("  sime_time_2_qt,");
		sql.append("  c_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_rtime_2_kb,");
		sql.append("  ten_nohin_time_2_kb,");
		sql.append("  bin_3_kb,");
		sql.append("  hachu_pattern_3_kb,");
		sql.append("  sime_time_3_qt,");
		sql.append("  c_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_rtime_3_kb,");
		sql.append("  ten_nohin_time_3_kb,");
		sql.append("  c_nohin_rtime_kb,");
		sql.append("  syohin_kb,");
		sql.append("  buturyu_kb,");
		sql.append("  ten_zaiko_kb,");
		sql.append("  sinki_toroku_dt,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (S)
		sql.append("  gentanka_nuki_vl,");
		// No.187 MSTB011 Add 2015.11.30 TU.TD (E)
		sql.append("'").append(MasterDT).append("',");
		sql.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '"+ rs.getString("by_no") + "',");
		sql.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '"+ rs.getString("by_no") + "',");
		sql.append("    '"+ AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") + "',");
		sql.append("    '"+ batchId + "',");
		sql.append("'").append(mst000801_DelFlagDictionary.IRU.getCode()).append("', ");
		sql.append("  yusen_bin_kb, ");
		// 2017.04.03 M.Akagi #4509 (S)
        /* #1964 Add 2016.09.07 Li.Sheng (S) */
		//sql.append("  emg_flag,");
		//sql.append("  plu_hanei_time,");
		sql.append("'").append(mst910020_EmgFlagDictionary.OFF.getCode()).append("', ");
		sql.append("  NULL,");
		// 2017.04.03 M.Akagi #4509 (E)
		sql.append("  hachu_fuka_flg,");
		sql.append("  orosi_baitanka_nuki_vl,");     
        /* #1964 Add 2016.09.07 Li.Sheng (E) */		
		// 2017.01.31 T.han #3784対応（S)
		sql.append("  siire_kahi_kb,");
		sql.append("  henpin_kahi_kb,");
		// 2017.01.31 T.han #3784対応（E)
		sql.append("   '"+ delYukoDt + "' ");
		// #4694 MSTB011050 2017.04.17 S.Takayama (S)
		sql.append("  , baika_haishin_fg ");
		// #4694 MSTB011050 2017.04.17 S.Takayama (E)
		sql.append("from");
		sql.append("  r_tensyohin_reigai ");
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ? and");
		sql.append("  yuko_dt = ? )");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

}
