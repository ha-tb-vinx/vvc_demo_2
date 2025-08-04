package mdware.common.util;

import java.util.*;
import java.sql.*;

import mdware.common.bean.RKinoBean;
import mdware.common.bean.RKinoDM;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: FunctionBeanHolder</p>
 * <p>説明: 機能マスタを読込み、内部に保持します。機能マスタに対する操作はこのクラス内メソッドからのみ行って下さい。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Yamanaka
 * @version 1.0
 */

public class FunctionBeanHolder {
	private static boolean initilized = false;
	private static List functionList = null;
	private static Map dataClassMap = new HashMap();

// 20021215 @ADD start
	public static final String FUNC_ORDER       = "1";
	public static final String FUNC_DELIVERY    = "2";
	public static final String FUNC_CLAIM       = "3";
	public static final String FUNC_DATACHANGE  = "4";
	public static final String FUNC_MERCHANDISE = "5";
	public static final String FUNC_MESSAGE     = "6";
	public static final String FUNC_QUESTION    = "7";
	public static final String FUNC_BBS         = "8";
	public static final String FUNC_SYSTEM      = "9";

	public static final String HOST_NO_IF       = "0";
	public static final String HOST_UPLOAD      = "1";
	public static final String HOST_DOWNLOAD    = "2";
// 20021215 @ADD end

	/**
	 * コンストラクタ。最初のアクセス時のみ初期処理が実行されます。
	 */
    public FunctionBeanHolder() {
		if (!initilized) {
			try {
				init();
			} catch (SQLException e) {
			}
		}
    }

	/**
	 * 初期処理。機能マスタよりデータを取得し、内部変数に保持します。
	 * @throws SQLException
	 */
	private void init() throws java.sql.SQLException {
		RKinoDM kinoDM = new RKinoDM();
		BeanHolder kinoBh = new BeanHolder(kinoDM);
		kinoBh.setRowsInPage(0);
		DataHolder dh = new DataHolder();
		kinoBh.setDataHolder(dh);
		//機能マスタデータの取得
		functionList = kinoBh.getFirstPageBeanList();
		kinoBh.close();

		//データ種別コードをＭＡＰに入れる
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (kinoBean.getDataSyubetuCd() != null && !dataClassMap.containsKey(kinoBean.getDataSyubetuCd().trim())) {
			    dataClassMap.put(kinoBean.getDataSyubetuCd().trim(), kinoBean.getDataSyubetuNa());
			}
		}

		initilized = true;
	}

	/**
	 * 機能マスタデータリストを返します。
	 * @return RKinoBeanのリスト
	 */
	public List getFunctionList() {
		return functionList;
	}

	/**
	 * 機能名称を返します。
	 * @param functionCode 機能コード
	 * @return
	 */
	public String getFunctionName(String functionCode) {
		String functionName = "";
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (functionCode.equals(kinoBean.getKinoCd().trim())) {
				functionName = kinoBean.getKinoNa();
				break;
			}
		}
		return functionName;
	}

	/**
	 * データ種別ＭＡＰを返します。
	 * @return データ種別ＭＡＰ（key - データ種別コード, value - データ種別名称）
	 */
	public Map getDataClassificationMap() {
		return dataClassMap;
	}

	/**
	 * データ種別コードを返します。
	 * @param functionCode 機能コード
	 * @param hostIfKbn ホスト送受信区分
	 * @return
	 */
	public String[] getDataClassificationCode(String functionCode, String hostIfKbn) {
		ArrayList codeList = new ArrayList();
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (functionCode.equals(kinoBean.getKinoCd().trim()) && hostIfKbn.equals(kinoBean.getHostUpDownKb().trim())) {
				codeList.add(kinoBean.getDataSyubetuCd().trim());
			}
		}
		if (codeList.isEmpty()) {
			return null;
		}
		String[] codes = new String[codeList.size()];
		for (int i = 0 ; i < codeList.size() ; i++) {
			codes[i] = (String)codeList.get(i);
		}
		return codes;
	}

	/**
	 * データ種別コードを返します。
	 * @param functionCode 機能コード
	 * @return
	 */
	public String[] getDataClassificationCode(String functionCode) {
		ArrayList codeList = new ArrayList();
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (functionCode.equals(kinoBean.getKinoCd().trim())) {
				codeList.add(kinoBean.getDataSyubetuCd().trim());
			}
		}
		if (codeList.isEmpty()) {
			return null;
		}
		String[] codes = new String[codeList.size()];
		for (int i = 0 ; i < codeList.size() ; i++) {
			codes[i] = (String)codeList.get(i);
		}
		return codes;
	}

	/**
	 * 全データ種別コードを返します。ソートは行いません。
	 * @return
	 */
	public String[] getDataClassificationCode() {
		if (dataClassMap.isEmpty()) {
			return null;
		}

		String[] dataClassCodes = new String[dataClassMap.size()];
		Set dataClassCodeSet = dataClassMap.keySet();
		Iterator ite = dataClassCodeSet.iterator();
		int idx = 0;
		while (ite.hasNext()) {
			dataClassCodes[idx] = (String)ite.next();
			idx++;
		}
		return dataClassCodes;
	}

	/**
	 * データ種別名称を返します。
	 * @param dataClassificationCode
	 * @return
	 */
	public String getDataClassificationName(String dataClassificationCode) {
		String dataClassName = (String)dataClassMap.get(dataClassificationCode);
		if (dataClassName == null) {
			return "";
		} else {
			return dataClassName.trim();
		}
	}

	/**
	 * ホスト送受信区分を返します。
	 * @param dataClassificationCode
	 * @return
	 */
	public String getHostIFKbn(String dataClassificationCode) {
		String ifKbn = "0";
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (dataClassificationCode.equals(kinoBean.getDataSyubetuCd().trim())) {
				ifKbn = kinoBean.getHostUpDownKb().trim();
				break;
			}
		}
		return ifKbn;
	}

	/**
	 * データ保持期間を返します。
	 * @param dataClassificationCode
	 * @return
	 */
	public long getDataKeepDays(String dataClassificationCode) {
		long period = 0;
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (dataClassificationCode.equals(kinoBean.getDataSyubetuCd().trim())) {
				period = kinoBean.getDataHojiKikanDy();
				break;
			}
		}
		return period;
	}

// 20021215 @ADD start
	/**
	 * シェアディレクトリ保管ファイル接頭辞名称を返します。
	 * @param dataClassificationCode
	 * @return
	 */
	public String getIfFileHeadName(String dataClassificationCode) {
		String hdName = null;
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (dataClassificationCode.equals(kinoBean.getDataSyubetuCd().trim())) {
				hdName = kinoBean.getIfFileHeadNa().trim();
				break;
			}
		}
		return hdName;
	}

	/**
	 * データ種別が指定された機能コード内に存在するかどうかを返す。
	 * @param functionCode
	 * @param dataClassificationCode
	 * @return
	 */
	public boolean containsDataClassification(String functionCode, String dataClassificationCode) {
		String[] dataClasses = getDataClassificationCode(functionCode);
		String eqData = null;
		if (dataClasses != null) {
			for (int i = 0 ; i < dataClasses.length ; i++) {
				if (dataClassificationCode.equals(dataClasses[i])) {
					return true;
				}
			}
		}
		return false;
	}
// 20021215 @ADD end

// 20030124 @ADD start
	/**
	 * データ種別接頭辞を返します。
	 * @param dataClassificationCode データ種別コード
	 * @return
	 */
	public String[] getDataHeadName(String dataClassificationCode) {
		ArrayList codeList = new ArrayList();
		Iterator ite = functionList.iterator();
		while (ite.hasNext()) {
			RKinoBean kinoBean = (RKinoBean)ite.next();
			if (dataClassificationCode.equals(kinoBean.getDataSyubetuCd().trim())) {
				codeList.add(kinoBean.getIfFileHeadNa().trim());
			}
		}
		if (codeList.isEmpty()) {
			return null;
		}
		String[] name = new String[codeList.size()];
		for (int i = 0 ; i < codeList.size() ; i++) {
			name[i] = (String)codeList.get(i);
		}
		return name;
	}
// 20030124 @ADD end
}
