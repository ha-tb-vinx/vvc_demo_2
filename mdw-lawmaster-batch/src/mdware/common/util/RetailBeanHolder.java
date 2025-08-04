package mdware.common.util;

import java.util.*;
import java.sql.*;

import mdware.common.bean.RKouriBean;
import mdware.common.bean.RKouriDM;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: RetailBeanHolder</p>
 * <p>説明: メモリ上に常駐しているRKouriBeanを返します。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class RetailBeanHolder {
	private static boolean initilized = false;
	private static RKouriBean retailBean = null;

	/**
	 * コンストラクタ。最初のアクセス時のみ初期処理が実行されます。
	 */
    public RetailBeanHolder() {
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
		RKouriDM kouriDM = new RKouriDM();
		BeanHolder kouriBh = new BeanHolder(kouriDM);
		kouriBh.setRowsInPage(0);
		DataHolder dh = new DataHolder();
		kouriBh.setDataHolder(dh);
		//小売マスタデータの取得
		List kouriList = kouriBh.getFirstPageBeanList();
		kouriBh.close();

		retailBean = (RKouriBean)kouriList.get(0);

		initilized = true;
	}

	/**
	 * RKouriBeanを返します。
	 * @return
	 */
	public RKouriBean getRetailBean() {
		return retailBean;
	}

	public static void main(String args[]) {
		RetailBeanHolder rbh = new RetailBeanHolder();
		System.out.println(rbh.getRetailBean());
	}
}