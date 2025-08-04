package mdware.common.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mdware.common.util.text.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.bean.RTenpoBean;
import mdware.common.bean.RTenpoDM;
/**
 * 
 * <p>タイトル: 店舗マスタへの共通アクセスクラス</p>
 * <p>説明: 店舗マスタへアクセスし、RTenpoBeanを取得します。</p>
 * <p>      またグルーピングマスタとJOINし絞りこみも行います。</p>
 * <p>      結果が複数ヒットする場合はListで返します。</p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * <pre>
 * 入力
 *
 * 出力
 *
 * 戻り値
 *
 * </pre>
 *
 * @author vjc
 * @version 1.0 (2006/11/09) 初版作成 S.Shimatani
 */
public class RTenpoAccesser {

	private final StcLog stcLog = StcLog.getInstance();

	/**
	 * R_TENPOから引数の店舗コードを条件にMaTenpoBeanを取得する。 取得できない場合はNullを返す。
	 * 
	 * @param tenpoCd 店舗コード
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）
	 */
	public RTenpoBean getTenpoBean(String tenpoCd) {
		if (tenpoCd == null || tenpoCd.trim().equals("")) {
			this.stcLog.getLog().warn("引数がnullまたは空白です。処理を終了します。");
			//System.out.println("引数がnullまたは空白です。処理を終了します。");
			return null;
		}

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd", tenpoCd);

		try {
			return this.getRTenpoBean(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}
	/**
	 * R_TENPOとR_TENGROUPとJOINを行い引数を条件にRTenpoBeanを取得する。 取得できない場合はNullを返す。
	 * 
	 * @param bumonCd 部門コード
	 * @param YotoKb 用途区分
	 * @param groupnoCd グルーピングNo
	 * @param tenpoCd 店舗コード
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）
	 */
	public RTenpoBean getTenpoBean(String bumonCd, String YotoKb,
			String groupnoCd, String tenpoCd) {

		DataHolder whereDh = new DataHolder();

		whereDh.updateParameterValue("bumon_cd", bumonCd);
		whereDh.updateParameterValue("yoto_kb", YotoKb);
		whereDh.updateParameterValue("groupno_cd", groupnoCd);
		whereDh.updateParameterValue("tenpo_cd", tenpoCd);

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd_select_in", this
				.getRTenpoWhereInSql(whereDh));

		try {
			return this.getRTenpoBean(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}
	
	/**
	 * R_TENPOから全店舗のMaTenpoBeanを取得する。取得できない場合はNullを返す。
	 * 
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getAllTenpoBeanList() {

		DataHolder searchDh = new DataHolder();
		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}

	/**
	 * R_TENPOから引数の店舗コードListを条件にMaTenpoBeanを取得する。 取得できない場合はNullを返す。
	 * 
	 * @param 中身String の店舗コードList
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getTenpoBeanList(List tenpoCdList) {
		if (tenpoCdList == null || tenpoCdList.size() == 0) {
			this.stcLog.getLog().warn("引数がnullまたは値がセットされていません。処理を終了します。");
			//System.out.println("引数がnullまたは値がセットされていません。処理を終了します。");
			return null;
		}

		DataHolder searchDh = new DataHolder();
		// ListからDMへ渡した時にin句になるようStringの変更を行い、検索条件へセットします。
		searchDh.updateParameterValue("tenpo_cd_in", MDWareStringChangeUtility.getCommaPauseString(tenpoCdList));

		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}


	/**
	 * R_TENPOとR_TENGROUPとJOINを行い引数の条件を元にMaTenpoBeanを取得する。取得できない場合はNullを返す。
	 * 
	 * @param bumonCd 部門コード
	 * @param YotoKb 用途区分
	 * @param groupnoCd グルーピングNo
	 * @param tenpoCdList 中身String の店舗コードList
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getTenpoBeanList(String bumonCd, String YotoKb,
			String groupnoCd, List tenpoCdList) {

		DataHolder whereDh = new DataHolder();

		whereDh.updateParameterValue("bumon_cd", bumonCd);
		whereDh.updateParameterValue("yoto_kb", YotoKb);
		whereDh.updateParameterValue("groupno_cd", groupnoCd);

		if (tenpoCdList != null && tenpoCdList.size() != 0) {
			// ListからDMへ渡した時にin句になるようStringの変更を行い、検索条件へセットします。
			whereDh.updateParameterValue("tenpo_cd_in", MDWareStringChangeUtility.getCommaPauseString(tenpoCdList));
		}

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd_select_in", this
				.getRTenpoWhereInSql(whereDh));
		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}

	/**
	 * R_TENPOとR_TENGROUPとJOINを行い引数の条件を元にMaTenpoBeanを取得する。取得できない場合はNullを返す。
	 * 
	 * @param bumonCd 部門コード
	 * @param YotoKb 用途区分
	 * @param groupnoCd グルーピングNo
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getTenpoBeanList(String bumonCd, String YotoKb, String groupnoCd) {

		DataHolder whereDh = new DataHolder();

		whereDh.updateParameterValue("bumon_cd", bumonCd);
		whereDh.updateParameterValue("yoto_kb", YotoKb);
		whereDh.updateParameterValue("groupno_cd", groupnoCd);

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd_select_in", this
				.getRTenpoWhereInSql(whereDh));
		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}

	/**
	 * R_TENPOとR_TENGROUPとJOINを行い引数の条件を元にMaTenpoBeanを取得する。取得できない場合はNullを返す。
	 * 
	 * @param bumonCd 部門コード
	 * @param YotoKb 用途区分
	 * @param groupnoCdList 中身String のグルーピングNoList
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getTenpoBeanList(String bumonCd, String YotoKb,
			List groupnoCdList) {

		DataHolder whereDh = new DataHolder();

		whereDh.updateParameterValue("bumon_cd", bumonCd);
		whereDh.updateParameterValue("yoto_kb", YotoKb);
		if (groupnoCdList != null && groupnoCdList.size() != 0) {
			// ListからDMへ渡した時にin句になるようStringの変更を行い、検索条件へセットします。
			whereDh.updateParameterValue("groupno_cd_in", MDWareStringChangeUtility.getCommaPauseString(groupnoCdList));
			
//			System.out.println(MDWareStringchangeUtility.getCommaPauseString(groupnoCdList));
			
		}

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd_select_in", this
				.getRTenpoWhereInSql(whereDh));
		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}

	/**
	 * R_TENPOとR_TENGROUPとJOINを行い引数の条件を元にMaTenpoBeanを取得する。取得できない場合はNullを返す。
	 * 
	 * @param bumonCd 部門コード
	 * @param YotoKb 用途区分
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）のList
	 */
	public List getTenpoBeanList(String bumonCd, String YotoKb) {

		DataHolder whereDh = new DataHolder();

		whereDh.updateParameterValue("bumon_cd", bumonCd);
		whereDh.updateParameterValue("yoto_kb", YotoKb);

		DataHolder searchDh = new DataHolder();
		searchDh.updateParameterValue("tenpo_cd_select_in", this
				.getRTenpoWhereInSql(whereDh));
		try {
			return this.getRTenpoBeanList(searchDh);
		} catch (SQLException se) {
			this.stcLog.getLog().warn("検索処理でエラーが発生しました。" + se.getMessage());
			//System.out.println("検索処理でエラーが発生しました。" + se.getMessage());
			return null;
		}
	}
	
	/**
	 * DataHolderの中身より、グルーピングマスタより、店舗コードのみ取得するSQLを取得します。
	 * 本クラス内での使用方法は、店舗マスタへ店舗コードを絞り込むための条件式として使用します。
	 * @param searchDataHolder
	 * @return String select文
	 */
	private String getRTenpoWhereInSql(DataHolder searchDataHolder) {
		String whereStr = "where ";
		String andStr = " and ";
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("tenpo_cd ");
		sb.append("from R_Tengroup  ");
		// bumon_cd に対するWHERE区
		if (searchDataHolder.getParameter("bumon_cd") != null
				&& ((String) searchDataHolder.getParameter("bumon_cd")).trim()
						.length() > 0) {
			sb.append(whereStr);
			sb.append("bumon_cd = '"
					+ (String) searchDataHolder.getParameter("bumon_cd") + "'");
			whereStr = andStr;
		}
		if (searchDataHolder.getParameter("bumon_cd_in") != null
				&& ((String) searchDataHolder.getParameter("bumon_cd_in"))
						.trim().length() > 0) {
			sb.append(whereStr);
			sb.append("bumon_cd in ( '"
					+ (String) searchDataHolder.getParameter("bumon_cd_in")
							.replaceAll(",","','") + "' )");
			whereStr = andStr;
		}

		// yoto_kb に対するWHERE区
		if (searchDataHolder.getParameter("yoto_kb") != null
				&& ((String) searchDataHolder.getParameter("yoto_kb")).trim()
						.length() > 0) {
			sb.append(whereStr);
			sb.append("yoto_kb = '"
					+ (String) searchDataHolder.getParameter("yoto_kb") + "'");
			whereStr = andStr;
		}
		if (searchDataHolder.getParameter("yoto_kb_in") != null
				&& ((String) searchDataHolder.getParameter("yoto_kb_in"))
						.trim().length() > 0) {
			sb.append(whereStr);
			sb.append("yoto_kb in ( '"
					+ (String) searchDataHolder.getParameter("yoto_kb_in")
							.replaceAll(",","','") + "' )");
			whereStr = andStr;
		}
		// groupno_cd に対するWHERE区
		if (searchDataHolder.getParameter("groupno_cd") != null
				&& ((String) searchDataHolder.getParameter("groupno_cd"))
						.trim().length() > 0) {
			sb.append(whereStr);
			sb.append("groupno_cd = '"
					+ (String) searchDataHolder.getParameter("groupno_cd")
					+ "'");
			whereStr = andStr;
		}
		if (searchDataHolder.getParameter("groupno_cd_in") != null
				&& ((String) searchDataHolder.getParameter("groupno_cd_in"))
						.trim().length() > 0) {
			sb.append(whereStr);
			sb.append("groupno_cd in ( '"
					+ (String) searchDataHolder.getParameter("groupno_cd_in")
							.replaceAll(",","','") + "' )");
			whereStr = andStr;
		}

		// tenpo_cd に対するWHERE区
		if (searchDataHolder.getParameter("tenpo_cd") != null
				&& ((String) searchDataHolder.getParameter("tenpo_cd")).trim()
						.length() > 0) {
			sb.append(whereStr);
			sb.append("tenpo_cd = '"
					+ (String) searchDataHolder.getParameter("tenpo_cd") + "'");
			whereStr = andStr;
		}
		if (searchDataHolder.getParameter("tenpo_cd_in") != null
				&& ((String) searchDataHolder.getParameter("tenpo_cd_in"))
						.trim().length() > 0) {
			sb.append(whereStr);
			sb.append("tenpo_cd in ( '"
					+ ((String) searchDataHolder.getParameter("tenpo_cd_in"))
							.replaceAll(",","','") + "' )");
			whereStr = andStr;
		}

		return sb.toString();
	}

	/**
	 * DBへ検索を行い、RTenpoBeanを取得します。 検索結果が2件以上の場合nullを返します。
	 * 
	 * @param searchDataHolder 検索条件
	 * @return RTenpoBean（DMCreterで作成されたそのままのBean）
	 * @throws SQLException
	 */
	private RTenpoBean getRTenpoBean(DataHolder searchDataHolder)
			throws SQLException {
		try {
			List searchList = this.getRTenpoBeanList(searchDataHolder);
			if (searchList == null) {
				return null;
			} else if (searchList.size() >= 2) {
				this.stcLog.getLog().warn("店舗マスタの検索結果が2件以上存在しました。");
				//System.out.println("店舗マスタの検索結果が2件以上存在しました。");
				return null;
			}
			RTenpoBean bean = (RTenpoBean) searchList.get(0);
			return bean;
		} catch (SQLException ex) {
			throw ex;
		}
	}

	/**
	 * DBへ検索を行い、R_TENPOからデータを取得します。 件数結果が0件の場合nullを返します。
	 * 
	 * @param searchDataHolder 検索条件
	 * @return R_TENPO（DMCreaterで作成された単表のBean)のList
	 * @throws SQLException
	 */
	private List getRTenpoBeanList(DataHolder searchDataHolder)
			throws SQLException {
		try {
			BeanHolder searchBh = this.getRTenpoBeanHolder(searchDataHolder);
			List searchList = searchBh.getBeanList();
			if (searchList.size() == 0) {
				this.stcLog.getLog().warn("店舗マスタの検索結果が0件でした。");
				//System.out.println("店舗マスタの検索結果が0件でした。");
				return null;
			}
			return searchList;
		} catch (SQLException ex) {
			throw ex;
		}
	}

	/**
	 * DBへ検索を行い、R_TENPOからデータを取得します。
	 * 
	 * @param searchDataHolder 検索条件
	 * @return R_TENPO（DMCreaterで作成された単表のBean)のBeanHolder
	 * @throws SQLException
	 */
	private BeanHolder getRTenpoBeanHolder(DataHolder searchDataHolder)
			throws SQLException {
		RTenpoDM searchDm = new RTenpoDM();
		BeanHolder searchBh = new BeanHolder(searchDm);
		searchBh.setDataHolder(searchDataHolder);
		try {
			searchBh.getFirstPageBeanList();
		} catch (SQLException ex) {
			throw ex;
		}
		return searchBh;
	}

	/**
	 * テスト実行用main
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			String propertyDir = "defaultroot/WEB-INF/properties";
//			String executeMode = "release";
//			String databaseUse = "use";
//
//			mdware.common.batch.util.control.BatchController controller = new mdware.common.batch.util.control.BatchController();
//			controller.init(propertyDir, executeMode, databaseUse);
//
//			String bumon_cd = "0211";
//			String yoto_kb = "4";
//			String groupno_cd = "01";
//			String tenpo_cd = "000395";
//
//			List tenpo_list = new ArrayList();
//			tenpo_list.add("000102");
//			tenpo_list.add("000103");
//			tenpo_list.add("000106");
//			tenpo_list.add("000107");
//
//			List groupno_list = new ArrayList();
//			groupno_list.add("01");
//			groupno_list.add("02");
//
//			DataHolder dh = new DataHolder();
//			RTenpoBean bean = null;
//			List list = null;
//			String where = "";
//			DataHolder wheredh = new DataHolder();
//
//			dh.updateParameterValue("bumon_cd", bumon_cd);
//			dh.updateParameterValue("yoto_kb", yoto_kb);
//			dh.updateParameterValue("groupno_cd", groupno_cd);
//			dh.updateParameterValue("tenpo_cd", tenpo_cd);
//
//			RTenpoAccesser test = new RTenpoAccesser();

			// //テスト1
			// bean = test.getRTenpoBean(dh);
			// System.out.println("テスト１");
			// System.out.println("Bean="+bean.toString());
			//			
			// //テスト２
			// System.out.println("テスト２");
			// list = test.getRTenpoBeanList(dh);
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			//
			// //テスト３
			// System.out.println("テスト３");
			// where = test.getRTenpoWhereInSql(dh);
			// System.out.println("where句="+where);
			//			
			// //テスト４
			// System.out.println("テスト４");
			// wheredh.updateParameterValue("tenpo_cd_select_in",where);
			// list = test.getRTenpoBeanList(wheredh);
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			//
			// //テスト５
			// System.out.println("テスト５");
			// dh.updateParameterValue("tenpo_cd","");
			// where = test.getRTenpoWhereInSql(dh);
			// wheredh.updateParameterValue("tenpo_cd_select_in",where);
			// list = test.getRTenpoBeanList(wheredh);
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			//			
			// //テスト６
			// System.out.println("テスト６");
			// bean = test.getTenpoBean(bumon_cd , yoto_kb , groupno_cd
			// ,tenpo_cd);
			// System.out.println("Bean="+bean.toString());
			//			
			// //テスト７
			// System.out.println("テスト７");
			// list = test.getTenpoBeanList(bumon_cd , yoto_kb , groupno_cd
			// ,tenpo_list);
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			// //テスト８
			// System.out.println("テスト８");
			// list = test.getTenpoBeanList(bumon_cd , yoto_kb , groupno_cd );
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			// //テスト９
			// System.out.println("テスト９");
			// list = test.getTenpoBeanList(bumon_cd , yoto_kb , groupno_list );
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }
			// //テスト１０
			// System.out.println("テスト１０");
			// list = test.getTenpoBeanList(bumon_cd , yoto_kb );
			// for(int i = 0 ; i < list.size();i++)
			// {
			// bean = (RTenpoBean)list.get(i);
			// System.out.println("int = " + i);
			// System.out.println("Bean="+bean.toString());
			// }

			// テスト１１
//			bean = test.getRTenpoBean(new DataHolder());
//			System.out.println("テスト１");
//			System.out.println(bean == null);
//
//			// テスト１２
//			System.out.println("テスト１２");
//			list = test.getRTenpoBeanList(new DataHolder());
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//
//			// テスト１３
//			System.out.println("テスト１３");
//			where = test.getRTenpoWhereInSql(new DataHolder());
//			System.out.println("where句=" + where);
//
//			// テスト１４
//			System.out.println("テスト１４");
//			wheredh.updateParameterValue("tenpo_cd_select_in", where);
//			list = test.getRTenpoBeanList(wheredh);
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//
//			// テスト１６
//			System.out.println("テスト１６");
//			bean = test.getTenpoBean("", "", "", "");
//			System.out.println(bean == null);
//
//			// テスト７
//			System.out.println("テスト１７");
//			list = test.getTenpoBeanList(null, null, null, null);
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//			// テスト１８
//			System.out.println("テスト１８");
//			list = test.getTenpoBeanList("", "", "");
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//			// テスト１９
//			System.out.println("テスト１９");
//			list = test.getTenpoBeanList(null, null, new ArrayList());
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//			// テスト２０
//			System.out.println("テスト２０");
//			list = test.getTenpoBeanList("", "");
//			for (int i = 0; i < list.size(); i++) {
//				bean = (RTenpoBean) list.get(i);
//				System.out.println("int = " + i);
//				System.out.println("Bean=" + bean.toString());
//			}
//		} catch (Exception e) {
//			System.out.println("Exception = " + e.getMessage());
//
//		}
//
//	}

}
