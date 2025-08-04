package mdware.common.util.properties;

import java.sql.ResultSet;
import java.util.Vector;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.bean.RBatchParamBean;

/**
 * [R_BATCH_PARAM]テーブルの値を取得します。
 * DBに接続はしません。 DB接続オブジェクトは別途作成してください。
 * 
 * @author T.Yokoyama
 * 
 */
public class RBatchParamUtil {

	public static final int SORTTYPE_NO = 0; // ソートオーダー無

	public static final int SORTTYPE_SUBID = 1; // ソート：SUBID

	public static final int SORTTYPE_SUBID_P1 = 2; // ソート：SUBID, PARAM1

	public static final int SORTTYPE_SUBID_P1P2 = 3; // ソート：SUBID, PARAM1, PARAM2

	public static final int SORTTYPE_SUBID_P1P2P3 = 4; // ソート：SUBID, PARAM1, PARAM2, PARAM3

	private DataBase db;

	private int sortOrder;

	/** ***************** テーブル検索用SQL ******************** */
	private static final String SQL_1_PARAMETER = "select * from R_BATCH_PARAM where BATCH_ID = '%PAR1%'";

	private static final String SQL_2_PARAMETER = "select * from R_BATCH_PARAM where BATCH_ID = '%PAR1%' and SUB_ID = '%PAR2%'";

	private static final String SQL_3_PARAMETER = "select * from R_BATCH_PARAM where BATCH_ID = '%PAR1%' and SUB_ID = '%PAR2%' and PARAM1_TX = '%PAR3%'";

	private static final String SQL_4_PARAMETER = "select * from R_BATCH_PARAM where BATCH_ID = '%PAR1%' and SUB_ID = '%PAR2%' and PARAM1_TX = '%PAR3%' and PARAM2_TX = '%PAR4%'";

	private static final String SQL_SORT_TYPE_1 = " order by SUB_ID";

	private static final String SQL_SORT_TYPE_2 = " order by SUB_ID, PARAM1_TX";

	private static final String SQL_SORT_TYPE_3 = " order by SUB_ID, PARAM1_TX, PARAM2_TX";

	private static final String SQL_SORT_TYPE_4 = " order by SUB_ID, PARAM1_TX, PARAM2_TX, PARAM3_TX";

// 2009/02/26 SIC Okada chg(Oracle化にともないオプションを外す){
//	private static final String FRO = " for read only";
// 2009/02/26 SIC Okada chg(Oracle化にともないオプションを外す)}

	/** ********************************************************* */

	private RBatchParamBean singleResult;

	private Vector arrayResult;

//	private static final CategoryForBatchLog batchLog = BatchLog.getInstance().getLog();

	public RBatchParamUtil() {
		this.sortOrder = SORTTYPE_NO;
	}

	public RBatchParamUtil(DataBase db) {
		this();
		this.db = db;
	}

	/**
	 * BatchIdを指定してプロパティを１つ取得します。
	 * 
	 * @param BatchId
	 * @return BatchPropertiesBean 結果がなければnull
	 * @throws Exception
	 */
	public RBatchParamBean getSingleProperty(String BatchId) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "]");
		String sql = _setParameter(SQL_1_PARAMETER, BatchId, null, null, null);
		return _getSingleResult(sql);
	}

	/**
	 * BatchId, SubIdを指定してプロパティを１つ取得します。
	 * 
	 * @param ID
	 * @param subId
	 * @return BatchPropertiesBean 結果がなければnull
	 * @throws Exception
	 */
	public RBatchParamBean getSingleProperty(String BatchId, String subId) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "]");
		String sql = _setParameter(SQL_2_PARAMETER, BatchId, subId, null, null);
		return _getSingleResult(sql);
	}

	/**
	 * BatchId, SubId, Param1_TXを指定してプロパティを１つ取得します。
	 * 
	 * @param ID
	 * @param subId
	 * @param name
	 * @return BatchPropertiesBean 結果がなければnull
	 * @throws Exception
	 */
	public RBatchParamBean getSingleProperty(String BatchId, String subId, String Param1_TX) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "][" + Param1_TX + "]");
		String sql = _setParameter(SQL_3_PARAMETER, BatchId, subId, Param1_TX, null);
		return _getSingleResult(sql);
	}

	/**
	 * BatchId, SubId, Param1_TX, Param2_TXを指定してプロパティを１つ取得します。
	 * 
	 * @param BatchId
	 * @param subId
	 * @param Param1_TX
	 * @param Param2_TX
	 * @return RBatchParamBean 結果がなければnull
	 * @throws Exception
	 */
	public RBatchParamBean getSingleProperty(String BatchId, String subId, String Param1_TX, String Param2_TX) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "][" + Param1_TX + "][" + Param2_TX);
		String sql = _setParameter(SQL_4_PARAMETER, BatchId, subId, Param1_TX, Param2_TX);
		return _getSingleResult(sql);
	}

	/**
	 * BatchIdを指定して見つかったプロパティを全て取得します。
	 * 
	 * @param BatchId
	 * @return BatchPropertiesBean 結果がなければnull
	 * @throws Exception
	 */
	public Vector getProperty(String BatchId) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "]");
		String sql = _setParameter(SQL_1_PARAMETER, BatchId, null, null, null);
		return _getArrayResult(sql);
	}

	/**
	 * BatchId, SubIDを指定して見つかったプロパティを全て取得します。[
	 * 
	 * @param BatchId
	 * @param subId
	 * @return BatchPropertiesBeanのVector。結果が無ければ空のVectorを返す。
	 * @throws Exception
	 */
	public Vector getProperty(String BatchId, String subId) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "]");
		String sql = _setParameter(SQL_2_PARAMETER, BatchId, subId, null, null);
		return _getArrayResult(sql);
	}

	/**
	 * BatchId, SubID, parame1_TXを指定して見つかったプロパティを全て取得します。[
	 * 
	 * @param BatchId
	 * @param subId
	 * @param prame1_TX
	 * @return BatchPropertiesBeanのVector。結果が無ければ空のVectorを返す。
	 * @throws Exception
	 */
	public Vector getProperty(String BatchId, String subId, String parame1_TX) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "][" + parame1_TX + "]");
		String sql = _setParameter(SQL_3_PARAMETER, BatchId, subId, parame1_TX, null);
		return _getArrayResult(sql);
	}

	/**
	 * BatchId, SubID, parame1_TX, parame2_TXを指定して見つかったプロパティを全て取得します。[
	 * 
	 * @param BatchId
	 * @param subId
	 * @param prame1_TX
	 * @param param2_TX
	 * @return BatchPropertiesBeanのVector。結果が無ければ空のVectorを返す。
	 * @throws Exception
	 */
	public Vector getProperty(String BatchId, String subId, String parame1_TX, String parame2_TX) throws Exception {
//		batchLog.debug("R_BATCH_PARAMテーブルからパラメータを取得します[" + BatchId + "][" + subId + "][" + parame1_TX + "][" + parame2_TX + "]");
		String sql = _setParameter(SQL_4_PARAMETER, BatchId, subId, parame1_TX, parame2_TX);
		return _getArrayResult(sql);
	}

	/**
	 * 任意のSQLを実行して得られた結果をRBatchParamBeanのVectorにして返します。
	 * 
	 * @param sql
	 * @return BatchPropertiesBeanのVector。結果が無ければ空のVectorを返す。
	 */
	public Vector executeQuery(String sql) throws Exception {
		return _getArrayResult(sql);
	}

	/** *********************************************** */
	/** *********** Setter and Getter... ************** */
	/** *********************************************** */
	public void setDataBase(DataBase db) {
		this.db = db;
	}

	public void setSortOrder(int type) {
		this.sortOrder = type;
	}

	public RBatchParamBean getSingleResult() {
		return singleResult;
	}

	public Vector getArrayResult() {
		return arrayResult;
	}

	/** *********************************************** */
	/** ************ Private... *********************** */
	/** *********************************************** */

	private RBatchParamBean _setBean(ResultSet rs) throws Exception {
		RBatchParamBean bean = new RBatchParamBean();
		bean.setBatch_id(trim(rs.getString("BATCH_ID")));
		bean.setSub_id(trim(rs.getString("SUB_ID")));
		bean.setParame1_tx(trim(rs.getString("PARAM1_TX")));
		bean.setParame2_tx(trim(rs.getString("PARAM2_TX")));
		bean.setParame3_tx(trim(rs.getString("PARAM3_TX")));
		return bean;
	}

	/**
	 * SQLを実行し、結果をRBatchParamBeanにして返します。 返すのは最初に見つかった１つのみ。
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private RBatchParamBean _getSingleResult(String sql) throws Exception {
		ResultSet rs = db.executeQuery(sql);
		if (rs.next()) {
			singleResult = _setBean(rs);
			return singleResult;
		}
		rs.close();
		return null;
	}

	/**
	 * SQLを実行し、結果をRBatchParamBeanのVectorにして返します。
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private Vector _getArrayResult(String sql) throws Exception {
		arrayResult = new Vector();
		ResultSet rs = db.executeQuery(sql);
		while (rs.next()) {
			arrayResult.add(_setBean(rs));
		}
		rs.close();
		return arrayResult;
	}

	/**
	 * SQL を置換してパラメータをセットします。
	 * 
	 * @param base
	 * @param par1
	 *            パラメータ1 "%PAR1%" が置換されます。
	 * @param par2
	 *            パラメータ2 "%PAR2%" が置換されます。
	 * @param par3
	 *            パラメータ3 "%PAR3%" が置換されます。
	 * @param par4
	 *            パラメータ3 "%PAR4%" が置換されます。
	 * @return パラメータ文字列が置換されたSQL
	 */
	private String _setParameter(String base, String par1, String par2, String par3, String par4) {
		/** パラメータ置換 * */
		if (par1 != null) {
			base = base.replaceAll("%PAR1%", par1);
		}
		if (par2 != null) {
			base = base.replaceAll("%PAR2%", par2);
		}
		if (par3 != null) {
			base = base.replaceAll("%PAR3%", par3);
		}
		if (par4 != null) {
			base = base.replaceAll("%PAR4%", par4);
		}

		/** ソート順 * */
		switch (sortOrder) {
		case SORTTYPE_SUBID:
			base += SQL_SORT_TYPE_1;
			break;

		case SORTTYPE_SUBID_P1:
			base += SQL_SORT_TYPE_2;
			break;

		case SORTTYPE_SUBID_P1P2:
			base += SQL_SORT_TYPE_3;
			break;

		case SORTTYPE_SUBID_P1P2P3:
			base += SQL_SORT_TYPE_4;
			break;
		}
// 2009/02/26 SIC Okada chg(Oracle化にともないオプションを外す){
//		return base + FRO; // " for read only" をつけて返す
// 2009/02/26 SIC Okada chg(Oracle化にともないオプションを外す)}
		return base;
	}

	/**
	 * Trim
	 * 
	 * @param value
	 * @return 引数がnullの時はnull, nullでなければtrim()した結果
	 */
	private String trim(String value) {
		if (value == null) {
			return null;
		}
		return value.trim();
	}

	/**
	 * 以下テスト用
	 */

	private void _println(RBatchParamBean b) {
		if (b == null) {
			System.out.println("null");
		} else {
			System.out.println("[" + b.getBatch_id() + "][" + b.getSub_id() + "][" + b.getParame1_tx() + "][" + b.getParame2_tx() + "][" + b.getParame3_tx() + "]");
		}
	}

	public void singleResultPrintln() {
		_println(singleResult);
	}

	public void arrayResultPrintln() {
		if (arrayResult == null) {
			System.out.println("Array null");
		} else {
			for (int tmp = 0; tmp < arrayResult.size(); tmp++) {
				_println((RBatchParamBean) arrayResult.get(tmp));
			}
		}
	}
}
