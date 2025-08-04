package mdware.common.util;

import java.util.*;
import java.sql.*;
import mdware.common.bean.RBumonBean;
import mdware.common.bean.RBumonDM;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: BumonBeanHolder</p>
 * <p>説明: 部門マスタを読込み、内部に保持します。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kirihara(MJC)
 * @version 1.0 deguchi(MJC) TenpoBeanHolderより新規作成
 */

public class BumonBeanHolder {
	private static boolean initilized = false;
	private static List bumonList = null;
	private static Map dataClassMap = new HashMap();

	/**
	 * コンストラクタ。最初のアクセス時のみ初期処理が実行されます。
	 */
    public BumonBeanHolder() {
		if (!initilized) {
			try {
				init();
			} catch (SQLException e) {
			}
		}
    }

	/**
	 * 初期処理。部門マスタよりデータを取得し、内部変数に保持します。
	 * @throws SQLException
	 */
	private void init() throws java.sql.SQLException {
		RBumonDM bumonDM = new RBumonDM();
		BeanHolder bumonBh = new BeanHolder(bumonDM);
		bumonBh.setRowsInPage(0);
		DataHolder dh = new DataHolder();
		bumonBh.setDataHolder(dh);
		// 部門マスタデータの取得
		bumonList = bumonBh.getFirstPageBeanList();
		bumonBh.close();

		// 部門コードをKeyに,部門名称をMAPに格納
		Iterator ite = bumonList.iterator();
		while (ite.hasNext()) {
			RBumonBean bumonBean = (RBumonBean)ite.next();
			if (bumonBean.getBumonCd() != null && !dataClassMap.containsKey(bumonBean.getBumonCd().trim())) {
			    dataClassMap.put(bumonBean.getBumonCd().trim(), bumonBean.getBumonNa().trim());
			}
		}

		initilized = true;
	}

	/**
	 * 部門マスタデータリストを返します。
	 * @return RBumonBeanのリスト
	 */
	public List getBumonList() {
		return bumonList;
	}

	/**
	 * 部門名称を返します。指定部門コードが存在しない場合空文字列を返します。
	 * @param bumonCode 部門コード
	 * @return
	 */
	public String getBumonName(String bumonCode) {
		String bumonName = "";
		Iterator ite = bumonList.iterator();
		while (ite.hasNext()) {
			RBumonBean bumonBean = (RBumonBean)ite.next();
			if (bumonCode.equals(bumonBean.getBumonCd().trim())) {
				bumonName = bumonBean.getBumonNa();
				break;
			}
		}
		return bumonName;
	}

	/**
	 * その部門の生鮮フラグを返します。指定部門コードが存在しない場合空文字列を返します。
	 * @param bumonCode 部門コード
	 * @return
	 */
	public String getSeisenFg(String bumonCode) {
		String seisenFg = "";
		Iterator ite = bumonList.iterator();
		while (ite.hasNext()) {
			RBumonBean bumonBean = (RBumonBean)ite.next();
			if (bumonCode.equals(bumonBean.getBumonCd().trim())) {
				seisenFg = bumonBean.getSeisenFg();
				break;
			}
		}
		return seisenFg;
	}

	/**
	 * 存在チェックを行います。
	 * @param bumonCode 部門コード
	 * @return
	 */
	public boolean isExisted(String bumonCode) {
		if(dataClassMap.get(bumonCode) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * マスタMAPを返します。
	 * @return 部門マスタMAP(key - 部門コード, value - 部門名称)
	 */
	public Map getDataClassificationMap() {
		return dataClassMap;
	}

	/**
	 * 全部門コードを返します。ソートは行いません。
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
}
