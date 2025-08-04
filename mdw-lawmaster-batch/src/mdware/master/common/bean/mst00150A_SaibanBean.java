package mdware.master.common.bean;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.common.util.StringUtility;


public class mst00150A_SaibanBean {

	private static final String ZERO_STRING = "0";

	/**
	 * 採番（商品コード）取得[8桁商品]を行う
	 * <br>
	 * @param		String	hinsyuCd		: 品種コード
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	 * @return	String	retTanpinCd		: 単品コード(null:エラー)
	 */

	public static String getSaibanA08(String seqName, String byNo, String userId,mst000101_DbmsBean db) throws Exception, SQLException {
		String ret = null;

		try {
			ret = getSaibanA08Process(seqName, byNo, userId, db);
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		}

		return ret;

	}
	
	/**
	 * 採番（商品コード）取得[8桁商品]を行う
	 * <br>
	 * @param		String	hinsyuCd		: 品種コード
	 * @param		String	tanpinCd		: 単品コード
	 * @param		String	jyotai_kanri_kb	: 状態管理区分
	 * @param		String	userId			: ユーザーID
	 * @param		mst000101_DbmsBean db	: dbインスタンス 
	 * @return	String	retTanpinCd		: 単品コード(null:エラー)
	 */
	public static String getSaibanA08Process(String seqName, String byNo, String userId, mst000101_DbmsBean db) throws Exception, SQLException {

		BigDecimal nextVal = null; //新番号
		String SyohinCd = null;
		String strRtn = null;
		String strChkDegit = null;
		mst00150A_SaibanDM saibanDM = new mst00150A_SaibanDM();
		try {
			//採番マスタから、商品コードを採番する
			ResultSet rset = null;
   
			rset = db.executeQuery(saibanDM.getSaibanA08SelectSql()); //抽出結果(ResultSet)
			if (rset.next()) {
				nextVal = rset.getBigDecimal("syohin_cd");

				if (nextVal == null) {
					nextVal = new BigDecimal("1000000");
				}else{
					//現在番号に１加算した値を採番番号とする
					nextVal = new BigDecimal("1").add(nextVal);
				}

			}else{
				nextVal = new BigDecimal("1000000");
			}
			SyohinCd = nextVal.toString();
			// チェックディジットを算出
			strChkDegit = mst001401_CheckDegitBean.getModulus9(SyohinCd,7);
			strRtn = SyohinCd + strChkDegit;
			// 採番マスタタグを更新する
			db.execute(saibanDM.getSaiban8KetaInsertSql(SyohinCd,strChkDegit,byNo,userId));
			db.commit();
		}catch (SQLException sqle) {
			throw sqle;
		}  catch (Exception e) {
			// 例外処理が発生した場合
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			return null; // 返値に0をセット
		}finally {
            if (db != null) {
                db.rollback();
                db.close();
            }
        }

		return strRtn;
	}

	/**
	 * 採番処理を行う
	 * <br>
	 * @param		mst000101_DbmsBean db	: dbインスタンス
	 * @param		String seqName			: 採番ID
	 * @return	string			    	: 採番NO
	 */
	public static String getSaiban(mst000101_DbmsBean db, String seqName) throws Exception, SQLException {
		
		BigDecimal nextVal = null; //新番号

		BigDecimal curCnt = null; //現在番号
		BigDecimal minCnt = null; //最小値
		BigDecimal maxCnt = null; //最大値
		int seqLength = 0; //桁数
		//採番取得のDMモジュール
		mst00150A_SaibanDM saibanDM = new mst00150A_SaibanDM();
		//抽出結果(ResultSet)
		ResultSet rset = null;
   
		rset = db.executeQuery(saibanDM.getSaibanCntSelectSql(seqName)); //抽出結果(ResultSet)
		if (rset.next()) {
//				現在番号、その他採番設定項目を取得
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
			// 採番マスタを更新する。
			// 更新が成功した場合はcommit、失敗した場合はrollbackし0を返す。
			if (db.executeUpdate(saibanDM.getSaibanUpdateSql(seqName,nextVal.toString())) != 1) {
				db.rollback();
				StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
				return ZERO_STRING; // 返値に0をセット
			}
		} else {
			// 採番出来なかった場合
			StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + seqName);
			return ZERO_STRING; // 返値に0をセット
		}
		
		if(rset != null){
			rset.close();
		}
		//採番番号がゼロを超える場合かつ採番桁数が指定されている場合は
		//ゼロサプレスを行う
		if ((nextVal.compareTo(new BigDecimal(ZERO_STRING)) > 0) && (seqLength > 0)) {
			return StringUtility.zeroFormat(nextVal.toString(), seqLength);
		} else {
			return nextVal.toString();
		}
	}
}
