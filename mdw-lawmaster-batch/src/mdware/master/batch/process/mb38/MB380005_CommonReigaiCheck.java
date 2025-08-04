package mdware.master.batch.process.mb38;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mdware.common.util.DateChanger;
import mdware.common.util.DateDiff;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;

/**
 * <p>タイトル:店別商品例外マスタ生成バッチ共通チェッククラス</p>
 * <p>説明:共通チェッククラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/25<BR>
 * @version 1.01 (2016.11.16) M.Akagi #2256
 * @version 1.02 (2023.04.14) TUNG.LT #13650 MKH対応
 * @version 1.03 (2024.10.08) SIEU.D #13650
 * @author shimoyama
 */
//20060926 hiu@vjc パフォーマンス改善対応　  :  指定日に一番近い過去日付を取得する処理　と　指定日に一番近い未来日付を取得するSQLが重いので、データきりわけをAppServで行う　
public class MB380005_CommonReigaiCheck {

	//項目の削除を表す文字
	private final String deleteString = "*";

	//メッセージを保持するMap
	private Map map = null;

	//DBクラス
	private MasterDataBase dataBase = null;

	//メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;

	//店別商品例外マスタチェック用PreparedStatement
	private PreparedStatement reigaiBefCheck = null;
	private PreparedStatement reigaiAftCheck = null;
	private PreparedStatement reigaiCount = null;
//	↓↓2006.07.26 guohy カスタマイズ修正↓↓
	private PreparedStatement regaiCheck = null;
//	↑↑2006.07.26 guohy カスタマイズ修正↑↑
	// #13650 ADD 2023.05.12 TUNG.LT (S)
	private PreparedStatement reigaiAftDifBunruiCheck = null;
	// #13650 ADD 2023.05.12 TUNG.LT (E)

	//TRテーブルのキー
	private String[] key = null;

	//マスタ日付
	private String MasterDT = RMSTDATEUtil.getMstDateDt();

	//Insert or Updateを判断するフラグ
	private boolean InsertFg = true;

//	↓↓2006.07.26 guohy カスタマイズ修正↓↓
	//	Delete かどうか判断するフラグ
	private boolean DeleteFg = false;
	//	Insert かどうか判断するフラグ
	private boolean UpInsertFg = false;
//	↑↑2006.07.26 guohy カスタマイズ修正↑↑
	//Update時のキーになる有効日
	// #13650 ADD 2023.05.12 TUNG.LT (S)
	private Map<String, String> insertedReigai;
	// #13650 ADD 2023.05.12 TUNG.LT (E)

	private String YukoDt = "";

	public MB380005_CommonReigaiCheck(PreparedStatement pstmt, Map map, MasterDataBase dataBase) throws SQLException {

		this.pstmt = pstmt;
		this.map = map;
		this.dataBase = dataBase;

		reigaiBefCheck = getPreparedBefCheckSQL(this.dataBase);
		reigaiAftCheck = getPreparedAftCheckSQL(this.dataBase);
		reigaiCount = getPreparedCountSQL(this.dataBase);
		regaiCheck = getPreparedRegaiSQL(this.dataBase);
		// #13650 ADD 2023.05.12 TUNG.LT (S)
		reigaiAftDifBunruiCheck = getPreparedReigaiAftDifBunruiCheckSQL(this.dataBase);
		insertedReigai = new HashMap<String, String>();
		// #13650 ADD 2023.05.12 TUNG.LT (E)
	}

	/**
	 * TRテーブルのキーのセット処理
	 * @return
	 */
	public void setKey(String[] key) {
		this.key = key;
	}


	/**
	 * 新規登録時の店別商品例外マスタチェック
	 * @return boolean
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public boolean insertReigaiCheck(ResultSet rs, String hanku_col) throws SQLException {
//	=== BEGIN === Modify by ryo 2006/8/3
//	public boolean insertReigaiCheck(ResultSet rs, String bumon_col) throws SQLException {
//		String syohin_cd = rs.getString("syohin_cd");
	public boolean insertReigaiCheck(ResultSet rs, String bumon_col, String syohin_cd) throws SQLException {
//	=== END === Modify by ryo 2006/8/3
//		String hanku_cd = rs.getString(hanku_col);
		String bunrui1_cd = rs.getString(bumon_col);
		String tenpo_cd = rs.getString("tenpo_cd");
		String yuko_dt = rs.getString("yuko_dt");

		// ===BEGIN=== Add by kou 2006/8/25
		if(yuko_dt == null) {
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}
		// ===END=== Add by kou 2006/8/25

		this.InsertFg = true;
		boolean existsFg = false;

		this.DeleteFg = false;

		//20060926 hiu@vjc パフォーマンス改善対応　start
		setPreparedReigaiBefCheckSQL(reigaiBefCheck, bunrui1_cd, syohin_cd, tenpo_cd);
		HashMap data=getNearDay(resultSetConverter(reigaiBefCheck.executeQuery()) ,  yuko_dt , true);
//		20060926 hiu@vjc パフォーマンス改善対応　end

//		↑↑2006.06.30 guohy カスタマイズ修正↑↑


		//登録する有効日以前のデータチェック
//		20060926 hiu@vjc パフォーマンス改善対応　start
		if (data!=null) {
			String tmp_yuko = (String)data.get("YUKO_DT");
			String tmp_del = (String)data.get("DELETE_FG");
//			20060926 hiu@vjc パフォーマンス改善対応　end
//			if (DateDiff.getDiffDays(tmp_yuko, MasterDT) > 0 && tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
			if (DateDiff.getDiffDays(tmp_yuko, MasterDT) >= 0 && tmp_del.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
				//データが存在する場合、削除データで有効日がマスタ日付より過去のときのみ有効
//				↓↓2006.06.30 guohy カスタマイズ修正↓↓
				if (yuko_dt.equals(tmp_yuko)){
					DeleteFg = true;
				}
//				↑↑2006.06.30 guohy カスタマイズ修正↑↑
				existsFg = false;
			} else {
				existsFg = true;
			}
		} else {
			existsFg = false;
		}

		//登録する有効日より未来のデータチェック
		if (!existsFg) {
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			20060926 hiu@vjc パフォーマンス改善対応　start
			setPreparedReigaiAftCheckSQL(reigaiAftCheck, bunrui1_cd, syohin_cd, tenpo_cd);

			HashMap data_a=getNearDay(resultSetConverter(reigaiAftCheck.executeQuery()) ,  yuko_dt , false);

//			↑↑2006.06.30 guohy カスタマイズ修正↑↑
			//res = reigaiAftCheck.executeQuery();

			//未来のデータが存在する場合エラー
			if (data_a!=null) {
//				20060926 hiu@vjc パフォーマンス改善対応　end
				existsFg = true;
			}
		}

		//dataBase.closeResultSet(res);

		if (existsFg) {
			setPreparedMessageSQL("0009",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		// #13650 ADD 2023.04.14 TUNG.LT (S)
		// #13650 DEL 2023.05.12 TUNG.LT (S)
		// PreparedStatement reigaiAftDifBunruiCheck = getPreparedReigaiAftDifBunruiCheckSQL(this.dataBase);
		// #13650 DEL 2023.05.12 TUNG.LT (E)
		setPreparedReigaiAftCheckSQL(reigaiAftDifBunruiCheck, bunrui1_cd, syohin_cd, tenpo_cd);
		ArrayList resultList = resultSetConverter(reigaiAftDifBunruiCheck.executeQuery());

		if (resultList != null && resultList.size() > 0) {
			setPreparedMessageSQL("0607");
			pstmt.addBatch();
			return false;
		}

		// #13650 MOD 2024.10.08 SIEU.D (S)
		// String insertedReigaiKey = rs.getString("tenpo_cd").concat("_").concat(rs.getString("syohin_cd"));
		String insertedReigaiKey = tenpo_cd.concat("_").concat(syohin_cd);
		// #13650 MOD 2024.10.08 SIEU.D (E)
		if (!insertedReigai.containsKey(insertedReigaiKey)) {
			insertedReigai.put(insertedReigaiKey, "");
		} else {
			setPreparedMessageSQL("0607");
			pstmt.addBatch();
			return false;
		}

		// #13650 ADD 2023.04.14 TUNG.LT (E)
		return true;
	}

	/**
	 * 更新時の店別商品例外マスタチェック
	 * @return boolean
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public boolean updateReigaiCheck(ResultSet rs, String hanku_col) throws SQLException {
	public boolean updateReigaiCheck(ResultSet rs, String bumon_col) throws SQLException {
		String syohin_cd = rs.getString("syohin_cd");
//		String hanku_cd = rs.getString(hanku_col);
		String bunrui1_cd = rs.getString(bumon_col);
		String tenpo_cd = rs.getString("tenpo_cd");
		String yuko_dt = rs.getString("yuko_dt");

//		===BEGIN=== Add by ryo 2006/8/4
		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}
//		===END=== Add by ryo 2006/8/4

		boolean existsFg = false; //データが存在するか
		boolean updateFg = true; //データを修正してよいか

		this.UpInsertFg = false;

//		↓↓2006.07.26 guohy カスタマイズ修正↓↓
		setPreparedRegaiSQL(regaiCheck, bunrui1_cd, syohin_cd, tenpo_cd);

		ResultSet re = regaiCheck.executeQuery();

		//店舗コード存在チェック
		if (!re.next()) {
//			↓↓2007.03.05 H.Yamamoto カスタマイズ修正↓↓
			dataBase.closeResultSet(re);
//			↑↑2007.03.05 H.Yamamoto カスタマイズ修正↑↑
			this.UpInsertFg = true;
			return true;
		}
		dataBase.closeResultSet(re);

//		setPreparedReigaiBefCheckSQL(reigaiBefCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
		//setPreparedReigaiBefCheckSQL(reigaiBefCheck, bunrui1_cd, syohin_cd, tenpo_cd, yuko_dt);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		//ResultSet res = reigaiBefCheck.executeQuery();
//		20060926 hiu@vjc パフォーマンス改善対応　start
		setPreparedReigaiBefCheckSQL(reigaiBefCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		HashMap data=getNearDay(resultSetConverter(reigaiBefCheck.executeQuery()) ,  yuko_dt , true);
//		20060926 hiu@vjc パフォーマンス改善対応　end
		//指定有効日以前の最新日を取得

		//登録する有効日以前のデータチェック
//		20060926 hiu@vjc パフォーマンス改善対応　start
		if (data!=null) {
			String tmp_yuko = (String)data.get("YUKO_DT");
			String tmp_del = (String)data.get("DELETE_FG");
//			20060926 hiu@vjc パフォーマンス改善対応　end
			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
				if (tmp_yuko.equals(yuko_dt)) {
					//有効日が同じ場合はUPDATE
					this.InsertFg = false;
				} else {
					//有効日が異なる場合はINSERT
					this.InsertFg = true;
				}
			} else {
				existsFg = false;
// 追加
				// 2016.11.16 M.Akagi #2256 (S)
				//updateFg = false;
				// 2016.11.16 M.Akagi #2256 (E)


			}
		}

		// 2016.11.16 M.Akagi #2256（S)
		//登録する有効日より未来のデータチェック
//		if (existsFg) {
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			setPreparedReigaiAftCheckSQL(reigaiAftCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
		//	setPreparedReigaiAftCheckSQL(reigaiAftCheck, bunrui1_cd, syohin_cd, tenpo_cd, yuko_dt);
//			↑↑2006.06.30 guohy カスタマイズ修正↑↑
		//	res = reigaiAftCheck.executeQuery();
//		20060926 hiu@vjc パフォーマンス改善対応　start
//		setPreparedReigaiAftCheckSQL(reigaiAftCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
//		HashMap data_a=getNearDay(resultSetConverter(reigaiAftCheck.executeQuery()) ,  yuko_dt , false);

		//指定有効日以後の最近日を取得


//			if (data_a!=null) {
//				String tmp_del = (String)data_a.get("DELETE_FG");
//				20060926 hiu@vjc パフォーマンス改善対応　end
//				if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
					//有効データの場合エラー
//					updateFg = false;
//				} else {
					//未来のデータは削除データのみ有効
//					updateFg = true;
//				}
//			} else {
//				updateFg = true;
//			}
//		}
		// 2016.11.16 M.Akagi #2256（E)

		//dataBase.closeResultSet(res);

		if (!updateFg) {
			//修正不可
			setPreparedMessageSQL("0012",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		if (!existsFg) {
			//修正対象の店別商品例外データが存在しない
			setPreparedMessageSQL("0310",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		//未来データは2件まで有効
		// 2016.11.16 M.Akagi #2256（S) オンライン日付にてInsert可能とする対応
		//if (InsertFg) {
		if (InsertFg && !yuko_dt.equals(MasterDT)) {
			// 2016.11.16 M.Akagi #2256（E)
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			setPreparedReigaiCountSQL(reigaiCount, hanku_cd, syohin_cd, tenpo_cd);
			setPreparedReigaiCountSQL(reigaiCount, bunrui1_cd, syohin_cd, tenpo_cd);
//			↑↑2006.06.30 guohy カスタマイズ修正↑↑
			ResultSet rset = reigaiCount.executeQuery();

			if (rset.next()) {
				int icount = rset.getInt("count");
				dataBase.closeResultSet(rset);

				if (icount > 1) {
					setPreparedMessageSQL("0015",rs.getString("by_no"));
					pstmt.addBatch();
					return false;
				}
			}
//			↓↓2007.03.05 H.Yamamoto カスタマイズ修正↓↓
			dataBase.closeResultSet(rset);
//			↑↑2007.03.05 H.Yamamoto カスタマイズ修正↑↑
		}

		return true;
	}

	/**
	 * 削除時の店別商品例外マスタチェック
	 * @return boolean
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public boolean deleteReigaiCheck(ResultSet rs, String hanku_col) throws SQLException {
	public boolean deleteReigaiCheck(ResultSet rs, String bumon_col) throws SQLException {
		String syohin_cd = rs.getString("syohin_cd");
//		String hanku_cd = rs.getString(hanku_col);
		String bunrui1_cd = rs.getString(bumon_col);
		String tenpo_cd = rs.getString("tenpo_cd");
		String yuko_dt = rs.getString("yuko_dt");

//		===BEGIN=== Add by ryo 2006/8/4
		// 有効日未入力の場合、管理日付の翌日でセット
		if(yuko_dt == null || yuko_dt.trim().equals("")){
			yuko_dt = DateChanger.addDate(MasterDT, 1);
		}
//		===END=== Add by ryo 2006/8/4

		boolean existsFg = false; //データが存在するか
		boolean deleteFg = true; //データを削除してよいか
//		20060926 hiu@vjc パフォーマンス改善対応　start
//		setPreparedReigaiBefCheckSQL(reigaiBefCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
		setPreparedReigaiBefCheckSQL(reigaiBefCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		HashMap data=getNearDay(resultSetConverter(reigaiBefCheck.executeQuery()) ,  yuko_dt , true);
		//指定有効日以前の最新日を取得

		//登録する有効日以前のデータチェック
		if (data!=null) {
			String tmp_yuko = (String)data.get("YUKO_DT");
			String tmp_del = (String)data.get("DELETE_FG");
//			20060926 hiu@vjc パフォーマンス改善対応　end

			if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
				//有効データのときのみ有効
				existsFg = true;
				this.YukoDt = tmp_yuko;
				if (tmp_yuko.equals(yuko_dt)) {
					//有効日が同じ場合はUPDATE
					this.InsertFg = false;
				} else {
					//有効日が異なる場合はINSERT
					this.InsertFg = true;
				}
			} else {
				existsFg = false;
			}
		} else {
			existsFg = false;
		}

		//登録する有効日より未来のデータチェック
		if (existsFg) {
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			setPreparedReigaiAftCheckSQL(reigaiAftCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
//			20060926 hiu@vjc パフォーマンス改善対応　start
			setPreparedReigaiAftCheckSQL(reigaiAftCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//			↑↑2006.06.30 guohy カスタマイズ修正↑↑
			HashMap data_a=getNearDay(resultSetConverter(reigaiAftCheck.executeQuery()) ,  yuko_dt , false);

			if (data_a!=null) {
//				20060926 hiu@vjc パフォーマンス改善対応　end

				//未来が存在する場合エラー
				deleteFg = false;
			} else {

				deleteFg = true;
			}
		}

		//dataBase.closeResultSet(res);

		if (!existsFg) {
			//削除対象の商品が存在しない
			setPreparedMessageSQL("0311",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		if (!deleteFg) {
			//削除不可
			setPreparedMessageSQL("0014",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		//未来データは2件まで有効
		if (InsertFg) {
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			setPreparedReigaiCountSQL(reigaiCount, hanku_cd, syohin_cd, tenpo_cd);
			setPreparedReigaiCountSQL(reigaiCount, bunrui1_cd, syohin_cd, tenpo_cd);
//			↑↑2006.06.30 guohy カスタマイズ修正↑↑

			ResultSet rset = reigaiCount.executeQuery();

			if (rset.next()) {
				int icount = rset.getInt("count");
				dataBase.closeResultSet(rset);

				if (icount > 1) {
					setPreparedMessageSQL("0015",rs.getString("by_no"));
					pstmt.addBatch();
					return false;
				}
			}
//			↓↓2007.03.05 H.Yamamoto カスタマイズ修正↓↓
			dataBase.closeResultSet(rset);
//			↑↑2007.03.05 H.Yamamoto カスタマイズ修正↑↑
		}

		return true;
	}

	/**
	 * 予約取消時の店別商品例外マスタチェック
	 * @return boolean
	 */
	public boolean cancelReigaiCheck(ResultSet rs, String bumon_col) throws SQLException {
		String syohin_cd = rs.getString("syohin_cd");
		String bunrui1_cd = rs.getString(bumon_col);
		String tenpo_cd = rs.getString("tenpo_cd");
		String yuko_dt = rs.getString("yuko_dt");

		boolean existsFg = false; //データが存在するか
		boolean deleteFg = true; //データを削除してよいか
//		20060926 hiu@vjc パフォーマンス改善対応　start
//		setPreparedReigaiBefCheckSQL(reigaiBefCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
		setPreparedReigaiBefCheckSQL(reigaiBefCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		HashMap data=getNearDay(resultSetConverter(reigaiBefCheck.executeQuery()) ,  yuko_dt , true);
		//指定有効日以前の最新日を取得

		//登録する有効日以前のデータチェック
		if (data!=null) {
			String tmp_yuko = (String)data.get("YUKO_DT");
			if (tmp_yuko.equals(yuko_dt)) {
				existsFg = true;
			} else {
				existsFg = false;
			}
		} else {
			existsFg = false;
		}

		// 2016.11.16 M.Akagi #2256（S)
		//登録する有効日より未来のデータチェック
//		if (existsFg) {
//			↓↓2006.06.30 guohy カスタマイズ修正↓↓
//			setPreparedReigaiAftCheckSQL(reigaiAftCheck, hanku_cd, syohin_cd, tenpo_cd, yuko_dt);
//			20060926 hiu@vjc パフォーマンス改善対応　start
			setPreparedReigaiAftCheckSQL(reigaiAftCheck, bunrui1_cd, syohin_cd, tenpo_cd);
//			↑↑2006.06.30 guohy カスタマイズ修正↑↑
			HashMap data_a=getNearDay(resultSetConverter(reigaiAftCheck.executeQuery()) ,  yuko_dt , false);

//			if (data_a!=null) {
//				20060926 hiu@vjc パフォーマンス改善対応　end

//				String tmp_del = (String)data_a.get("DELETE_FG");
//				if (tmp_del.equals(mst000801_DelFlagDictionary.INAI.getCode())) {
					//未来が存在する場合エラー
//					deleteFg = false;
//				} else {
					//未来が削除の場合は認める
//					deleteFg = true;
//				}
//			} else {

//				deleteFg = true;
//			}
//		}

		//dataBase.closeResultSet(res);

			if( yuko_dt.equals(MasterDT) ){
				// オンライン日付の場合は、予約取消不可
				deleteFg = false;
			}
		// 2016.11.16 M.Akagi #2256（E)
		if (!existsFg) {
			//削除対象の商品が存在しない
			setPreparedMessageSQL("0432",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		if (!deleteFg) {
			//削除不可
			setPreparedMessageSQL("0014",rs.getString("by_no"));
			pstmt.addBatch();
			return false;
		}

		return true;
	}

	public boolean getInsertFg() {
		return this.InsertFg;
	}
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
	public boolean getDeleteFg() {
		return this.DeleteFg;
	}
	public boolean getUpInsertFg() {
		return this.UpInsertFg;
	}
//	↑↑2006.06.30 guohy カスタマイズ修正↑↑
	public String getYukoDt() {
		return this.YukoDt;
	}


	/**
	 * 商品が既に削除されているか
	 * @return boolean
	 */
	public boolean syohinDeleteCheck(ResultSet rs) throws SQLException {

		String syohinDeleteFg =	rs.getString("s_delete_fg");

		if (syohinDeleteFg != null && syohinDeleteFg.equals(mst000801_DelFlagDictionary.IRU.getCode())) {
			setPreparedMessageSQL("0312",rs.getString("by_no"));
			pstmt.addBatch();
			return true;
		}

		return false;
	}


	/**
	 * 店舗発注開始・終了日のチェック
	 * @return boolean
	 */
	public boolean hachuDtStEdCheck(ResultSet rs) throws SQLException {
		String ten_hachu_st_dt = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
		String ten_hachu_ed_dt = rs.getString("ten_hachu_ed_dt"); //店舗発注終了日
		String s_ten_hachu_st_dt = rs.getString("s_ten_hachu_st_dt"); //商品マスタ値
		String s_ten_hachu_ed_dt = rs.getString("s_ten_hachu_ed_dt"); //商品マスタ値
		boolean dateFg = true;

		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(ten_hachu_ed_dt) && !ten_hachu_ed_dt.equals("99999999")) {
			if (DateDiff.getDiffDays(ten_hachu_st_dt, ten_hachu_ed_dt) < 0) {
				//日付の大小関係エラー
				setPreparedMessageSQL("0022",rs.getString("by_no"));
				pstmt.addBatch();
				return false;
			}
		}

		//発注開始 >= 発注開始（商品マスタ）
		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(s_ten_hachu_st_dt)) {
			if (DateDiff.getDiffDays(ten_hachu_st_dt, s_ten_hachu_st_dt) > 0) {
				setPreparedMessageSQL("0093",rs.getString("by_no"));
				pstmt.addBatch();
				dateFg = false;
			}
		}

		//発注開始 <= 発注終了（商品マスタ）
		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(s_ten_hachu_ed_dt) && !s_ten_hachu_ed_dt.equals("99999999")) {
			if (DateDiff.getDiffDays(ten_hachu_st_dt, s_ten_hachu_ed_dt) < 0) {
				setPreparedMessageSQL("0093",rs.getString("by_no"));
				pstmt.addBatch();
				dateFg = false;
			}
		}

		//発注終了 >= 発注開始（商品マスタ）
		if (isNotBlank(ten_hachu_ed_dt) && isNotBlank(s_ten_hachu_st_dt) && !ten_hachu_ed_dt.equals("99999999")) {
			if (DateDiff.getDiffDays(ten_hachu_ed_dt, s_ten_hachu_st_dt) > 0) {
				setPreparedMessageSQL("0094",rs.getString("by_no"));
				pstmt.addBatch();
				dateFg = false;
			}
		}

		//発注終了 <= 発注終了（商品マスタ）
		if (isNotBlank(ten_hachu_ed_dt) && isNotBlank(s_ten_hachu_ed_dt) && !s_ten_hachu_ed_dt.equals("99999999")) {
			if (ten_hachu_ed_dt.equals("99999999")) {
				setPreparedMessageSQL("0094",rs.getString("by_no"));
				pstmt.addBatch();
				dateFg = false;
			} else if (DateDiff.getDiffDays(ten_hachu_ed_dt, s_ten_hachu_ed_dt) < 0) {
				setPreparedMessageSQL("0094",rs.getString("by_no"));
				pstmt.addBatch();
				dateFg = false;
			}
		}

		return dateFg;
	}
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	/**
//	 * 店舗発注開始のチェック
//	 * @return boolean
//	 */
//	public boolean hachuDtStCheck(ResultSet rs) throws SQLException {
//		String ten_hachu_st_dt = rs.getString("ten_hachu_st_dt"); //店舗発注開始日
//		String s_ten_hachu_st_dt = rs.getString("s_ten_hachu_st_dt"); //商品マスタ値
//		String s_ten_hachu_ed_dt = rs.getString("s_ten_hachu_ed_dt"); //商品マスタ値
//		boolean dateFg = true;
//
//		//発注開始 >= 発注開始（商品マスタ）
//		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(s_ten_hachu_st_dt)) {
//			if (DateDiff.getDiffDays(ten_hachu_st_dt, s_ten_hachu_st_dt) > 0) {
//				setPreparedMessageSQL("0093",rs.getString("by_no"));
//				pstmt.addBatch();
//				dateFg = false;
//			}
//		}
//
//		//発注開始 <= 発注終了（商品マスタ）
//		if (isNotBlank(ten_hachu_st_dt) && isNotBlank(s_ten_hachu_ed_dt) && !s_ten_hachu_ed_dt.equals("99999999")) {
//			if (DateDiff.getDiffDays(ten_hachu_st_dt, s_ten_hachu_ed_dt) < 0) {
//				setPreparedMessageSQL("0093",rs.getString("by_no"));
//				pstmt.addBatch();
//				dateFg = false;
//			}
//		}
//
//		return dateFg;
//	}
//	↑↑2006.06.30 guohy カスタマイズ修正↑↑

//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	/**
//	 * 原価単価のチェック（副資材チェック有り）
//	 * @return boolean
//	 */
//	public boolean gentankaCheck(ResultSet rs) throws SQLException {
//		String hukusizai_ck = rs.getString("hukusizai_ck"); //副資材コード
//		String tmp = rs.getString("gentanka_vl"); //原単価
//
//		//		if (tmp != null && tmp.trim().length() > 0) {
//		if (isNotBlank(tmp)) {
//			double gentanka_vl = Double.parseDouble(tmp.trim());
//
//			if (hukusizai_ck == null && gentanka_vl <= 0) {
//				//副資材品種以外で０の場合エラー
//				setPreparedMessageSQL("0023",rs.getString("by_no"));
//				pstmt.addBatch();
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * 売価単価のチェック（副資材チェック有り）
//	 * @return boolean
//	 */
//	public boolean baitankaCheck(ResultSet rs) throws SQLException {
//		String hukusizai_ck = rs.getString("hukusizai_ck"); //副資材コード
//		String tmp = rs.getString("baitanka_vl"); //売単価
//
//		if (isNotBlank(tmp)) {
//			long baitanka_vl = Long.parseLong(tmp.trim());
//
//			if (hukusizai_ck == null && baitanka_vl <= 0) {
//				//副資材品種以外で０の場合エラー
//				setPreparedMessageSQL("0024",rs.getString("by_no"));
//				pstmt.addBatch();
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * 単価チェック
//	 * １：大小チェック
//	 * @return boolean
//	 */
//	public boolean tankaCheck(ResultSet rs, boolean hukusizaiFg) throws SQLException {
//		boolean tankaFg = true;
//		String gentanka_vl = rs.getString("gentanka_vl");
//		String baitanka_vl = rs.getString("baitanka_vl");
//		String s_gentanka_vl = rs.getString("s_gentanka_vl"); //商品マスタ値
//		String s_baitanka_vl = rs.getString("s_baitanka_vl"); //商品マスタ値
//		double dgenka = 0;
//		double dbaika = 0;
//
//		if (isNotBlank(gentanka_vl)) {
//			dgenka = Double.parseDouble(gentanka_vl.trim());
//		} else {
//			dgenka = Double.parseDouble(s_gentanka_vl.trim());
//		}
//
//		if (isNotBlank(baitanka_vl)) {
//			dbaika = Double.parseDouble(baitanka_vl.trim());
//		} else {
//			dbaika = Double.parseDouble(s_baitanka_vl.trim());
//		}
//
//		if (hukusizaiFg) {
//			String hukusizai_ck = rs.getString("hukusizai_ck"); //副資材コード
//			if (hukusizai_ck != null && dgenka == 0 && dbaika != 0) {
//				//原単価が０の場合、売単価 が０以外の場合エラー
//				setPreparedMessageSQL("0096",rs.getString("by_no"));
//				pstmt.addBatch();
//				return false;
//			}
//		}
//
//		if (dgenka > 0 && dbaika > 0) {
//			double wariai = NumberUtility.round(dgenka / dbaika, 4);
//
//			if (wariai <= 0.09) {
//				//原単価/売単価 <= 0.09の場合エラー
//				setPreparedMessageSQL("0025",rs.getString("by_no"));
//				pstmt.addBatch();
//				tankaFg = false;
//			}
//
//			if (wariai >= 11) {
//				//原単価/売単価 >= 11の場合エラー
//				setPreparedMessageSQL("0026",rs.getString("by_no"));
//				pstmt.addBatch();
//				tankaFg = false;
//			}
//		}
//
//		return tankaFg;
//	}
//
//	/**
//	 * EOS区分チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean eosCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "eos_kb", "eos_ck", "0028");
//
//		return checkFg;
//	}
//
//	/**
//	 * 仕入先コードチェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean siiresakiCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "siiresaki_cd", "siiresaki_ck", "0031");
//
//		return checkFg;
//	}
//
//	/**
//	 * 物流区分
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean buturyuKbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "buturyu_kb", "buturyu_ck", "0045");
//
//		return checkFg;
//	}
//
//	/**
//	 * ①便チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean bin1KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "bin_1_kb", "bin_1_ck", "0035");
//
//		return checkFg;
//	}
//	↑↑2006.06.30 guohy カスタマイズ修正↑↑

//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	/**
//	 * ①発注パターン区分チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean hachuPattern1KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_1_kb", "hachu_pattern_1_ck", "0036");
//
//		return checkFg;
//	}
//
//	/**
//	 * ②便チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean bin2KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "bin_2_kb", "bin_2_ck", "0037");
//
//		return checkFg;
//	}
//
//	/**
//	 * ②発注パターン区分チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean hachuPattern2KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_2_kb", "hachu_pattern_2_ck", "0038");
//
//		return checkFg;
//	}
//
//	/**
//	 * ③便チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean bin3KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "bin_3_kb", "bin_3_ck", "0039");
//
//		return checkFg;
//	}
//
//	/**
//	 * ③発注パターン区分チェック
//	 * １：CTFマスタに存在するか
//	 * @return boolean
//	 */
//	public boolean hachuPattern3KbCheck(ResultSet rs) throws SQLException {
//
//		boolean checkFg = rNamectfCheck(rs, "hachu_pattern_3_kb", "hachu_pattern_3_ck", "0040");
//
//		return checkFg;
//	}
//
//	/**
//	 * ①②③便チェック
//	 * １：同じ便が存在するか
//	 * @return boolean
//	 */
//	public boolean binKbCheck(ResultSet rs) throws SQLException {
//		String bin_1_kb = rs.getString("bin_1_kb"); //①便区分
//		String bin_2_kb = rs.getString("bin_2_kb"); //②便区分
//		String bin_3_kb = rs.getString("bin_3_kb"); //③便区分
//
//		boolean checkFg = false;
//
//		if (isNotBlank(bin_1_kb) && isNotBlank(bin_2_kb)) {
//			if (bin_1_kb.equals(bin_2_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (!checkFg && isNotBlank(bin_1_kb) && isNotBlank(bin_3_kb)) {
//			if (bin_1_kb.equals(bin_3_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (!checkFg && isNotBlank(bin_2_kb) && isNotBlank(bin_3_kb)) {
//			if (bin_2_kb.equals(bin_3_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (checkFg) { //同じ便が指定されている
//			setPreparedMessageSQL("0041",rs.getString("by_no"));
//			pstmt.addBatch();
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * ①②③発注パターン区分チェック
//	 * １：同じ発注パターンが存在するか
//	 * @return boolean
//	 */
//	public boolean hachuPatternKbCheck(ResultSet rs) throws SQLException {
//		String hachu_pattern_1_kb = rs.getString("hachu_pattern_1_kb"); //①発注パターン区分
//		String hachu_pattern_2_kb = rs.getString("hachu_pattern_2_kb"); //②発注パターン区分
//		String hachu_pattern_3_kb = rs.getString("hachu_pattern_3_kb"); //②発注パターン区分
//
//		boolean checkFg = false;
//
//		if (isNotBlank(hachu_pattern_1_kb) && isNotBlank(hachu_pattern_2_kb)) {
//			if (hachu_pattern_1_kb.equals(hachu_pattern_2_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (!checkFg && isNotBlank(hachu_pattern_1_kb) && isNotBlank(hachu_pattern_3_kb)) {
//			if (hachu_pattern_1_kb.equals(hachu_pattern_3_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (!checkFg && isNotBlank(hachu_pattern_2_kb) && isNotBlank(hachu_pattern_3_kb)) {
//			if (hachu_pattern_2_kb.equals(hachu_pattern_3_kb)) {
//				checkFg = true;
//			}
//		}
//
//		if (checkFg) { //同じ発注パターンが指定されている
//			setPreparedMessageSQL("0042",rs.getString("by_no"));
//			pstmt.addBatch();
//			return false;
//		}
//
//		return true;
//	}
//	↑↑2006.06.30 guohy カスタマイズ修正↑↑


//	↓↓2006.07.26 guohy カスタマイズ修正↓↓
	/**
	 * 店別商品例外マスタチェックSQL
	 * @param pstmt PreparedStatement
	 * @param bunrui1_cd 部門コード
	 * @param syohin_cd 商品コード
	 * @param tenpo_cd 店舗コード
	 * @throws Exception
	 */
	public void setPreparedRegaiSQL(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String tenpo_cd) throws SQLException {
		int idx = 0;

		// 部門コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//店舗コード
		idx++;
		pstmt.setString(idx, tenpo_cd);

	}

	/**
	 * 店別商品例外マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedRegaiSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  delete_fg ");
		sql.append("from");
		sql.append("  r_tensyohin_reigai ");
		sql.append("where ");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ?  and");
		sql.append("  delete_fg = '0' ");

		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
//	↑↑2006.07.26 guohy カスタマイズ修正↑↑

	/**
	 * CTFマスタに存在するかのチェック（共通部）
	 * @param ResultSet rs
	 * @param String col1 入力値が入っているカラム名
	 * @param String col2 CTFマスタのチェック結果のカラム名
	 * @param String msgNo エラー時のメッセージNo
	 * @return boolean
	 */
	public boolean rNamectfCheck(ResultSet rs, String col1, String col2, String msgNo) throws SQLException {
		String str1 = rs.getString(col1);
		String str2 = rs.getString(col2);

		if (isNotBlank(str1)) {
			if (str2 == null) {
				//CTFに存在しない
				setPreparedMessageSQL(msgNo,rs.getString("by_no"));
				pstmt.addBatch();
				return false;
			}
		}
		return true;
	}

	/**
	 * 結果メッセージ作成SQL
	 * @throws Exception
	 */
	public void setPreparedMessageSQL(String megNo,String userId) throws SQLException {

		int idx = 0;
		//取込日
		idx++;
		pstmt.setString(idx, key[0]);
		//EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);
		//受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);
		//受付SEQNo
		idx++;
		pstmt.setString(idx, key[3]);
		//シート種別
		idx++;
		pstmt.setString(idx, key[4]);
		//結果メッセージコード
		idx++;
		pstmt.setString(idx, megNo);
		//結果メッセージ
		idx++;
		pstmt.setString(idx, (String)map.get(megNo));
		// 作成年月日
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") );
		// 作成者ID
		idx++;
		pstmt.setString(idx,userId);
		// 更新年月日
		idx++;
		pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") );
		// 更新者ID
		idx++;
		pstmt.setString(idx,userId);
	}

// ===BEGIN=== Add by kou 2006/8/23
   /**
	* 結果メッセージ作成SQL
	* @throws Exception
	*/
   public void setPreparedMessageSQL(String megNo) throws SQLException {

	   int idx = 0;
	   //取込日
	   idx++;
	   pstmt.setString(idx, key[0]);
	   //EXCELファイル種別
	   idx++;
	   pstmt.setString(idx, key[1]);
	   //受付ファイルNo
	   idx++;
	   pstmt.setString(idx, key[2]);
	   //受付SEQNo
	   idx++;
	   pstmt.setString(idx, key[3]);
	   //シート種別
	   idx++;
	   pstmt.setString(idx, key[4]);
	   //結果メッセージコード
	   idx++;
	   pstmt.setString(idx, megNo);
	   //結果メッセージ
	   idx++;
	   pstmt.setString(idx, (String)map.get(megNo));
	   // 作成年月日
	   idx++;
	   pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") );
	   // 作成者ID
	   idx++;
	   pstmt.setString(idx,key[5]);
	   // 更新年月日
	   idx++;
	   pstmt.setString(idx, AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") );
	   // 更新者ID
	   idx++;
	   pstmt.setString(idx,key[5]);
   }

// ===END=== Add by kou 2006/8/23

	/**
	 * 店別商品例外マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedBefCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rt.yuko_dt as yuko_dt,");
		sql.append("  rt.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_tensyohin_reigai rt ");
		sql.append("where ");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("  hanku_cd = ? and");
		sql.append("  bunrui1_cd = ? and");
		sql.append("  syohin_cd = ? and");
//		20060926 hiu@vjc パフォーマンス改善対応　start
		sql.append("  tenpo_cd = ? ");
//		sql.append("  yuko_dt =");
//		sql.append("            (select");
//		sql.append("               max(yuko_dt)");
//		sql.append("             from");
//		sql.append("               r_tensyohin_reigai sub");
//		sql.append("             where");
//		sql.append("               sub.hanku_cd = rt.hanku_cd AND");
//		sql.append("               sub.bunrui1_cd = rt.bunrui1_cd AND");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
//		sql.append("               sub.syohin_cd = rt.syohin_cd AND");
//		sql.append("               sub.tenpo_cd = rt.tenpo_cd AND");
//		sql.append("               sub.yuko_dt <= ?) ");
//		sql.append("for update");
//		20060926 hiu@vjc パフォーマンス改善対応　end
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外マスタチェックSQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPreparedReigaiBefCheckSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String tenpo_cd, String yuko_dt) throws SQLException {
	public void setPreparedReigaiBefCheckSQL(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String tenpo_cd) throws SQLException {
		int idx = 0;
//		//販区コード
//		idx++;
//		pstmt.setString(idx, hanku_cd);
		//部門コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//店舗コード
		idx++;
		pstmt.setString(idx, tenpo_cd);
//		20060926 hiu@vjc パフォーマンス改善対応　start
		//有効日
	//	idx++;
	//	pstmt.setString(idx, yuko_dt);
//	20060926 hiu@vjc パフォーマンス改善対応　end
	}

	/**
	 * 店別商品例外マスタチェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedAftCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rt.yuko_dt as yuko_dt,");
		sql.append("  rt.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_tensyohin_reigai rt ");
		sql.append("where ");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("  hanku_cd = ? and");
		sql.append("  bunrui1_cd = ? and");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append("  syohin_cd = ? and");
//		20060926 hiu@vjc パフォーマンス改善対応　start
		sql.append("  tenpo_cd = ? ");
	//	sql.append("  yuko_dt =");
	//	sql.append("            (select ");
	//	sql.append("               min(yuko_dt)");
		//sql.append("             from");
	//	sql.append("               r_tensyohin_reigai sub");
	//	sql.append("             where");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append("               sub.hanku_cd = rt.hanku_cd AND");
	//	sql.append("               sub.bunrui1_cd = rt.bunrui1_cd AND");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
	//	sql.append("               sub.syohin_cd = rt.syohin_cd AND");
	//	sql.append("               sub.tenpo_cd = rt.tenpo_cd AND");
	//	sql.append("               sub.yuko_dt > ?) ");
	//	sql.append("for update");
//	20060926 hiu@vjc パフォーマンス改善対応　end
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	// #13650 ADD 2023.04.14 TUNG.LT (S)
	/**
	 * ReigaiAftDifBunruiCheckPreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedReigaiAftDifBunruiCheckSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append("  rt.yuko_dt as yuko_dt,");
		sql.append("  rt.delete_fg as delete_fg ");
		sql.append("from");
		sql.append("  r_tensyohin_reigai rt ");
		sql.append("where ");
		sql.append("  bunrui1_cd <> ? and");
		sql.append("  syohin_cd = ? and");
		sql.append("  tenpo_cd = ? ");
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}
	// #13650 ADD 2023.04.14 TUNG.LT (E)


	/**
	 * 店別商品例外マスタチェックSQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPreparedReigaiAftCheckSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String tenpo_cd, String yuko_dt) throws SQLException {
	public void setPreparedReigaiAftCheckSQL(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String tenpo_cd) throws SQLException {
		int idx = 0;
//		//販区コード
//		idx++;
//		pstmt.setString(idx, hanku_cd);
		//部門コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//店舗コード
		idx++;
		pstmt.setString(idx, tenpo_cd);
//		20060926 hiu@vjc パフォーマンス改善対応　start
		//有効日
	//	idx++;
	//	pstmt.setString(idx, yuko_dt);
//	20060926 hiu@vjc パフォーマンス改善対応　end
	}

	/**
	 * 店別商品例外マスタ未来データ数チェック用PreparedStatement
	 * @throws
	 */
	public PreparedStatement getPreparedCountSQL(MasterDataBase dataBase) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" count(*) as count ");
		sql.append("from");
		sql.append(" r_tensyohin_reigai ");
		sql.append("where ");
//		↓↓2006.06.30 guohy カスタマイズ修正↓↓
//		sql.append(" hanku_cd = ? and");
		sql.append(" bunrui1_cd = ? and");
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		sql.append(" syohin_cd = ? and");
		sql.append(" tenpo_cd = ? and");
		sql.append(" yuko_dt > '").append(MasterDT).append("'");
		PreparedStatement pstmt = dataBase.getPrepareStatement(sql.toString());
		return pstmt;
	}

	/**
	 * 店別商品例外スタ未来データ数チェックSQL
	 * @throws Exception
	 */
//	↓↓2006.06.30 guohy カスタマイズ修正↓↓
//	public void setPreparedReigaiCountSQL(PreparedStatement pstmt, String hanku_cd, String syohin_cd, String tenpo_cd) throws SQLException {
	public void setPreparedReigaiCountSQL(PreparedStatement pstmt, String bunrui1_cd, String syohin_cd, String tenpo_cd) throws SQLException {
		int idx = 0;
//		//販区コード
//		idx++;
//		pstmt.setString(idx, hanku_cd);
		//部門コード
		idx++;
		pstmt.setString(idx, bunrui1_cd);
//		↑↑2006.06.30 guohy カスタマイズ修正↑↑
		//商品コード
		idx++;
		pstmt.setString(idx, syohin_cd);
		//店舗コード
		idx++;
		pstmt.setString(idx, tenpo_cd);
	}

	private boolean isNotBlank(String val) {
		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
			return true;
		}
		return false;
	}

	// ===BEGIN=== Add by kou 2006/8/23
	/**
	 * @param syohin_cd
	 * @return
	 */
	public boolean syohinNullCheck(String syohin_cd) throws SQLException {
		// 商品コードを取得できない場合、エラー
//		↓↓2006.09.08 H.Yamamoto カスタマイズ修正↓↓
//		if(syohin_cd == null || "".equals(syohin_cd)){
		if(syohin_cd == null || "".equals(syohin_cd.trim()) || syohin_cd.trim().length() < 3){
//		↑↑2006.09.08 H.Yamamoto カスタマイズ修正↑↑
			setPreparedMessageSQL("0300");
			pstmt.addBatch();
			return false;
		}
		return true;
	}
	// ===END=== Add by kou 2006/8/23
//	20060926 hiu@vjc パフォーマンス改善対応　start
	//ResultSet をArrayListにバインドする
	public   ArrayList resultSetConverter(ResultSet st)  {
		try{
		ArrayList ret = new ArrayList();

		if(st==null){
			System.out.println("ResultSetがnull!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return ret;

		}




		while (st.next()) {

			HashMap mp = new HashMap();

			for (int i = 1; i < st.getMetaData().getColumnCount() + 1; i++) {
				int type = st.getMetaData().getColumnType(i);

				String name = st.getMetaData().getColumnName(i);

				if (type == java.sql.Types.INTEGER || type == java.sql.Types.NUMERIC || type==java.sql.Types.BIGINT) {
					//整数

					mp.put(name.toUpperCase() , Long.toString(st.getLong(i)));

				} else if (type == java.sql.Types.VARCHAR || type == java.sql.Types.CHAR || type == java.sql.Types.LONGVARCHAR ) {
					//文字

					String tmp=st.getString(i);

					if(tmp==null){
						tmp="";
					}

					mp.put(name.toUpperCase(), tmp);

				} else {

					//ソレ以外は未実装  必要ならば実装して下さい
					//System.out.println("????????="+type);
					mp.put(name.toUpperCase(), "");
				}
			}

			ret.add(mp);
		}
		dataBase.closeResultSet( st );
		//st.close();

		return ret;
		}catch(Exception e){
			throw new RuntimeException ("データベースからデータの取得中にエラーが発生しました"+e.getMessage() );
		}
	}
	//mode  true:指定日付より以前の一番未来の日付　　false:指定日付より以後の一番過去の日付
	private HashMap getNearDay(ArrayList ar, String yuko_dt, boolean mode){
		HashMap ret=null;
		if(mode){
			int max_yuko_dt=0;
			int regDt=Integer.parseInt(yuko_dt);
			for(int i=0; i<ar.size() ; i++){
				HashMap m=(HashMap)ar.get(i);
				String tdt=(String)m.get("YUKO_DT");

				int dt=Integer.parseInt (tdt);
				if(dt>max_yuko_dt && regDt>=dt){
					max_yuko_dt=dt;
					ret=m;
				}
			}
		}else{
			int min_yuko_dt=99999999;
			int regDt=Integer.parseInt(yuko_dt);
			for(int i=0; i<ar.size() ; i++){
				HashMap m=(HashMap)ar.get(i);
				String tdt=(String)m.get("YUKO_DT");

				int dt=Integer.parseInt (tdt);
				if(dt<min_yuko_dt && regDt<dt){
					min_yuko_dt=dt;
					ret=m;
				}
			}


		}
		return ret;
	}
//	20060926 hiu@vjc パフォーマンス改善対応　end
}
