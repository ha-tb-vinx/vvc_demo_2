/**
 * <p>タイトル: mst940101_SyohinTaikeiDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのDMクラス(商品体系検索用)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst008801_SyohinTaikeiDictionary;

/**
 * <p>タイトル: mst940101_SyohinTaikeiDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品階層マスタのDMクラス(商品体系検索用)</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst940101_SyohinTaikeiDM extends DataModule {

	/**
	 * コンストラクタ
	 */
	public mst940101_SyohinTaikeiDM() {
		super(mst000101_ConstDictionary.CONNECTION_STR);
	}

	/**
	 * 検索後にBEANをインスタンス化する<br>
	 * 検索した結果セットをBEANとして持ち直す。<br>
	 * DataModuleから呼び出され返したObjectをListに追加する。<br>
	 * @param ResultSet 検索結果
	 * @return インスタンス化されたBEAN
	 */
	protected Object instanceBean(ResultSet rest) throws SQLException {

		mst940101_SyohinTaikeiBean bean = new mst940101_SyohinTaikeiBean();

		bean.setKaisoPatternNm(rest.getString("KAISO_PT"));//階層パターン ※CSV用
		bean.setYukoDt(rest.getString("YUKO_DT"));//検索対象日(有効日) ※CSV用
		bean.setHinsyuCd(rest.getString("HINSYU_CD"));//品種コード
		bean.setHinsyuNm(rest.getString("HINSYU_NM"));//品種名
		bean.setHingunCd(rest.getString("HINGUN_CD"));//品群コード
		bean.setHingunNm(rest.getString("HINGUN_NM"));//品群名
		bean.setHankuCd(rest.getString("HANKU_CD"));//販区コード
		bean.setHankuNm(rest.getString("HANKU_NM"));//販区名
		bean.setUrikuCd(rest.getString("URIKU_CD"));//売区コード
		bean.setUrikuNm(rest.getString("URIKU_NM"));//売区名
		bean.setTyukansyukeiCd(rest.getString("C_SYUKEI_CD"));//中間集計コード
		bean.setTyukansyukeiNm(rest.getString("C_SYUKEI_NM"));//中間集計名
		bean.setSyogyosyuCd(rest.getString("S_GYOUSYU"));//小業種コード
		bean.setSyogyosyuNm(rest.getString("S_GYOUSYU_NM"));//小業種名
		bean.setDaigyosyuCd(rest.getString("D_GYOUSYU"));//大業種コード
		bean.setDaigyosyuNm(rest.getString("D_GYOUSYU_NM"));//大業種名

		bean.setCreateDatabase();

		return bean;
	}//instanceBean

	/**
	 * 検索用SQLの生成を行う。<br>
	 * @param map 検索パラメータ
	 * @return 生成されたSQL
	 */
	public String getSelectSql(Map map) {

		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		StringBuffer sb = new StringBuffer();

		sb.append("WITH T AS (");
		sb.append("SELECT A.KAISOU_PATTERN_KB,");
		sb.append("       A.CODE1_CD,");
		sb.append("       A.CODE2_CD");
		sb.append("  FROM R_SYOHINKAISO A");
		sb.append("  INNER JOIN");
		sb.append("  ( ");
		sb.append("     SELECT KAISOU_PATTERN_KB, ");
		sb.append("            CODE1_CD, ");
		sb.append("            MAX(YUKO_DT) AS YUKO_DT ");
		sb.append("         FROM R_SYOHINKAISO ");
		sb.append("         WHERE YUKO_DT <= '" + map.get("ct_yukodt") + "'");
		sb.append("         GROUP BY KAISOU_PATTERN_KB, CODE1_CD");
		sb.append("  ) B ");
		sb.append("  ON A.YUKO_DT = B.YUKO_DT");
		sb.append("  AND A.KAISOU_PATTERN_KB = B.KAISOU_PATTERN_KB");
		sb.append("  AND A.CODE1_CD = B.CODE1_CD ");
		sb.append("  WHERE A.DELETE_FG = '0'");
		sb.append("  ) ");

		sb.append("SELECT");
		sb.append("    '" + getKaisoPatternKbNm(map.get("ct_kaisoupatternkb")) + "' AS KAISO_PT,");
		sb.append("    '" + map.get("ct_yukodt") + "'" + " AS YUKO_DT,");
		sb.append("    A.CODE1_CD AS HINSYU_CD,");//品種(階層パターン1の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0060", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = A.CODE1_CD) AS HINSYU_NM,");
		sb.append("    B.CODE1_CD AS HINGUN_CD,");//品群(階層パターン2の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0050", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = B.CODE1_CD) AS HINGUN_NM,");
		sb.append("    C.CODE1_CD AS HANKU_CD,");//販区(階層パターン3の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0040", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = C.CODE1_CD) AS HANKU_NM,");
		sb.append("    D.CODE1_CD AS URIKU_CD,");//売区(階層パターン4の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0030", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = D.CODE1_CD) AS URIKU_NM,");
		sb.append("    E.CODE1_CD AS C_SYUKEI_CD,");//中間集計(階層パターン5の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0025", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = E.CODE1_CD) AS C_SYUKEI_NM,");
		sb.append("    F.CODE1_CD AS S_GYOUSYU,");//小業種(階層パターン6の下位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0020", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = F.CODE1_CD) AS S_GYOUSYU_NM,");
		sb.append("    F.CODE2_CD AS D_GYOUSYU,");//大業種(階層パターン6の上位コード)
		sb.append("    (SELECT KANJI_NA FROM R_NAMECTF WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage("0010", userLocal) + "' AND DELETE_FG = '0' AND CODE_CD = F.CODE2_CD) AS D_GYOUSYU_NM");

		//1:品種→品群
		sb.append("  FROM (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='1') A");
		sb.append("  INNER JOIN");
		//2:品群→販区
		sb.append("       (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='2') B");
		sb.append("    ON A.CODE2_CD = B.CODE1_CD");
		sb.append("  INNER JOIN");
		//3:販区→売区
		sb.append("       (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='3') C");
		sb.append("    ON B.CODE2_CD = C.CODE1_CD");
		sb.append("  INNER JOIN");
		//4:売区→中間集計
		sb.append("       (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='4') D");
		sb.append("    ON C.CODE2_CD = D.CODE1_CD");
		sb.append("  INNER JOIN");
		//5:中間集計→小業種
		sb.append("       (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='5') E");
		sb.append("    ON D.CODE2_CD = E.CODE1_CD");
		sb.append("  INNER JOIN");
		//6:小業種→大業種
		sb.append("       (SELECT CODE1_CD, CODE2_CD FROM T WHERE KAISOU_PATTERN_KB ='6') F");
		sb.append("    ON E.CODE2_CD = F.CODE1_CD");

		//検索条件----
		//階層パターンとコードで検索(指定なしの場合は全件検索)
		String kaisoupatternkb = "";
		if (map.get("ct_kaisoupatternkb") != null) {
			kaisoupatternkb = (String)map.get("ct_kaisoupatternkb");
		}
		String codecd = "";
		if (map.get("ct_codecd") != null) {
			codecd = (String)map.get("ct_codecd");
		}
		if (!kaisoupatternkb.equals("") && !codecd.equals("")) {
			//指定なし(全件検索)
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.SITEINASHI.getCode())) {
				//条件追加なし
			}
			//品種指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.HINSYU.getCode())) {
				sb.append(" WHERE A.CODE1_CD = '" + codecd + "'");
			}
			//品群指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.HINGUN.getCode())) {
				sb.append(" WHERE B.CODE1_CD = '" + codecd + "'");
			}
			//販区指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.HANKU.getCode())) {
				sb.append(" WHERE C.CODE1_CD = '" + codecd + "'");
			}
			//売区指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.URIKU.getCode())) {
				sb.append(" WHERE D.CODE1_CD = '" + codecd + "'");
			}
			//中間集計指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.TYUKANSYUKEI.getCode())) {
				sb.append(" WHERE E.CODE1_CD = '" + codecd + "'");
			}
			//小業種指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.SYOGYOSYU.getCode())) {
				sb.append(" WHERE F.CODE1_CD = '" + codecd + "'");
			}
			//大業種指定
			if (kaisoupatternkb.equals(mst008801_SyohinTaikeiDictionary.DAIGYOSYU.getCode())) {
				sb.append(" WHERE F.CODE2_CD = '" + codecd + "'");
			}
		}
		//整列条件
		sb.append(" ORDER BY D_GYOUSYU, S_GYOUSYU, C_SYUKEI_CD, URIKU_CD, HANKU_CD, HINGUN_CD, HINSYU_CD");

		return sb.toString();
	}//getSelectSql

	/**
	 * 挿入用SQLの生成を行う。<br>
	 * 渡されたBEANをDBに挿入するためのSQL<br>
	 * ※DataModuleのgetInsertSql(Object)をインプリメントする必要があるが、<br>
	 * 　本機能では不要なメソッドであるため、ロジックは未実装とする。<br>
	 * 　また戻り値は""(空文字)を返す。<br>
	 * @param	beanMst	Object
	 * @return 空文字
	 */
	public String getInsertSql(Object beanMst) {
		return "";
	}

	/**
	 * 更新用SQLの生成を行う。<br>
	 * 渡されたBEANを元にDBを更新するためのSQL<br>
	 * ※DataModuleのgetInsertSql(Object)をインプリメントする必要があるが、<br>
	 * 　本機能では不要なメソッドであるため、ロジックは未実装とする。<br>
	 * 　また戻り値は""(空文字)を返す。<br>
	 * @param beanMst Object
	 * @return 空文字
	 */
	public String getUpdateSql(Object beanMst) {
		return "";
	}

	/**
	 * 削除用SQLの生成を行う。<br>
	 * 渡されたBEANをDBから削除するためのSQL<br>
	 * ※DataModuleのgetInsertSql(Object)をインプリメントする必要があるが、<br>
	 * 　本機能では不要なメソッドであるため、ロジックは未実装とする。<br>
	 * 　また戻り値は""(空文字)を返す。<br>
	 * @param beanMst Object
	 * @return 空文字
	 */
	public String getDeleteSql(Object beanMst) {
		return "";
	}

	/**
	 * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
	 * @param base
	 * @param before
	 * @param after
	 * @return
	 */
	protected String replaceAll(String base, String before, String after) {
		if(base == null) {
			return base;
		}
		int pos = base.lastIndexOf(before);
		if(pos < 0) {
			return base;
		}
		int befLen = before.length();
		StringBuffer sb = new StringBuffer(base);
		while(pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0) {
			sb.delete(pos,pos + befLen);
			sb.insert(pos, after);
			pos--;
		}
		return sb.toString();
	}

	/**
	 * 階層パターン区分名称を取得する。<br>
	 * 取得できない場合は""(空文字)を返す。<br>
	 * @param Object 階層パターン区分
	 * @return 階層パターン区分名称
	 */
	private String getKaisoPatternKbNm(Object obj) {
		if (obj != null) {
			return mst008801_SyohinTaikeiDictionary.getStatus((String)obj).toString();
		}
		return "";
	}
}