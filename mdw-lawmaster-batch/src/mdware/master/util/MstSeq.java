package mdware.master.util;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: RB Site連番管理テーブル(seq)より新しい連番を取得する。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author nob
 * @version 1.0
 * @version 2.0 2004.12.06 takuwa フジ対応
 * @version 2.1 2004.12.16 takuwa 伝票番号20桁対応
 * @version 2.2 2004.12.21 takuwa STCLIB DB2対応による修正
 * @version 3.0 2005.06.10 shimoyama ポスフールマスタ管理対応
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.util.StringUtility;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;

public class MstSeq {

	private static final String databaseName = "rbsite_ora";
	private static final String ZERO_STRING = "0";

	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	protected synchronized static String nextNb(String seqName) {

		DataBase db = null; //データベース
		ResultSet rset = null; //検索結果レコード

		BigDecimal nextVal = null; //新番号

		BigDecimal curCnt = null; //現在番号
		BigDecimal minCnt = null; //最小値
		BigDecimal maxCnt = null; //最大値
		int seqLength = 0; //桁数

		//パラメータ未設定時は戻り値"0"
		if (seqName.trim().length() == 0) {
			return ZERO_STRING; // 返値に0をセット
		}

		db = new DataBase(databaseName);

		try {

			//ＤＢ開始処理
			db.commit(); // 暗黙のトランザクション開始

			// 新しい連番の取得
			StringBuffer selectSb = new StringBuffer();
			selectSb.append("select ");
			selectSb.append("cur_cnt,");
			selectSb.append("min_cnt,");
			selectSb.append("max_cnt,");
			selectSb.append("ketasu ");
			selectSb.append("from ");
//			↓↓2006.07.04 xubq カスタマイズ修正↓↓			
//			selectSb.append("r_mst_seq ");
//			↑↑2006.07.04 xubq カスタマイズ修正↑↑		
			selectSb.append("r_seq ");
			selectSb.append("where ");
			selectSb.append("saiban_id = '" + seqName + "' ");
//			↓↓2006.07.29 H.Yamamoto 障害対応修正↓↓			
//			selectSb.append("for update ");
//			↑↑2006.07.29 H.Yamamoto 障害対応修正↑↑		

			rset = db.executeQuery(selectSb.toString());

			if (rset.next()) {
				// 採番出来た場合

				//現在番号、その他採番設定項目を取得
				curCnt = rset.getBigDecimal("cur_cnt");
				minCnt = rset.getBigDecimal("min_cnt");
				maxCnt = rset.getBigDecimal("max_cnt");
				seqLength = rset.getInt("ketasu");

				if (curCnt == null) {
					curCnt = new BigDecimal(ZERO_STRING);
				}
				if (minCnt == null) {
					minCnt = new BigDecimal(ZERO_STRING);
				}
				if (maxCnt == null) {
					maxCnt = new BigDecimal(ZERO_STRING);
				}

				//現在番号に１加算した値を採番番号とする
				nextVal = new BigDecimal("1").add(curCnt);

				//最大値＞最小値かつ採番番号＞最大値の場合は
				//採番番号は最小値とする。
				//※最大値＝最小値、最大値＜最小値の場合はカウントは元に戻らない
				if ((maxCnt.compareTo(minCnt) > 0) && (nextVal.compareTo(maxCnt) > 0)) {
					nextVal = minCnt;
				}

				//最小値＞採番番号の場合は
				//採番番号は最小値とする。
				if ((maxCnt.compareTo(minCnt) > 0) && (nextVal.compareTo(minCnt) < 0)) {
					nextVal = minCnt;
				}

				//採番番号を現在番号に更新する
				StringBuffer updateSb = new StringBuffer();
				updateSb.append("update ");
//				↓↓2006.07.04 xubq カスタマイズ修正↓↓		
//				updateSb.append(" r_mst_seq ");
//				↑↑2006.07.04 xubq カスタマイズ修正↑↑		
				updateSb.append(" r_seq ");
				updateSb.append("set ");
				updateSb.append(" cur_cnt = " + nextVal.toString() + " ");
				updateSb.append("where ");
				updateSb.append("saiban_id = '" + seqName + "' ");

				// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
				if (db.executeUpdate(updateSb.toString()) == 1) {
					db.commit();
				} else {
					db.rollback();
					StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
					return ZERO_STRING; // 返値に0をセット
				}
			} else {
				// 採番出来なかった場合
				StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + seqName);
				return ZERO_STRING; // 返値に0をセット
			}
		} catch (Exception e) {
			// 例外処理が発生した場合
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			return ZERO_STRING; // 返値に0をセット
		} finally {
			//ＤＢ終了処理
			try {
				if (rset != null) {
					rset.close();
				}
			} catch (SQLException sqle) {
			};
			try {
				if (db != null) {
					db.close();
				}
			} catch (Exception e) {
			};
			rset = null;
			db = null;
		}

		//採番番号がゼロを超える場合かつ採番桁数が指定されている場合は
		//ゼロサプレスを行う
		if ((nextVal.compareTo(new BigDecimal(ZERO_STRING)) > 0) && (seqLength > 0)) {
			return StringUtility.zeroFormat(nextVal.toString(), seqLength);
		} else {
			return nextVal.toString();
		}

	}

	/**
	 * 連番の取得を行う。※String型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static String nextValString(String seqName) {
		String nextVal;
		nextVal = nextNb(seqName);
		return nextVal;
	}

	/**
	 * 連番の取得を行う。※long型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static long nextValLong(String seqName) {
		long nextVal;
		nextVal = Long.parseLong(nextNb(seqName));
		return nextVal;
	}

	/**
	 * 連番の取得を行う。※int型
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static int nextValInteger(String seqName) {
		int nextVal;
		nextVal = Integer.parseInt(nextNb(seqName));
		return nextVal;
	}

	/**
	 * 連番の取得を行う。
	 * @param seqName 連番名称(table:seq  column:saiban_id)
	 * @return 新しい連番( > 0 )、もし0なら取得失敗
	 */
	public static int nextVal(String seqName) {
		return nextValInteger(seqName);
	}

}
