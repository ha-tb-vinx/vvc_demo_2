package mdware.master.common.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.calendar.DateDifference;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.master.batch.process.mb38.MB380000_CommonSql;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst000401_LogicBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst006401_DataKindDictionary;
import mdware.master.common.dictionary.mst006701_SyuseiKbDictionary;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.util.db.MasterDataBase;
import mdware.common.util.DateChanger;
import mdware.master.util.bean.RMasterControlBean;
import mdware.master.util.RMasterControlUtil;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst009301_TaikeiKirikaeJyotaiDictionary;

/**
 * <P>タイトル:  登録票アップロードチェック処理</P>
 * <P>説明:      登録票アップロードチェック処理商品マスタTR用抽象クラス。</P>
 * <P>著作権:	 Copyright (c) 2006</P>
 * <P>会社名:	 Vinculum Japan Corp.</P>
 * @author      jiangb
 * @version     1.0
 * @version 1.01 (2015.10.09) DAI.BQ FIVImart対応
 * @version 1.02 (2016.10.26) T.Arimoto #2256 FIVImart対応
 * @version 1.03 (2017.10.04) T.Arimoto #5994 FIVImart対応
 * @version 1.04 2022/01/04 HOAI.TTT #6409 対応
 */
public abstract class SyohinCheckAbstract implements InterfaceCheck {	
    
	// データ結果
	private ResultSet targetRS = null;
	// TRテーブルのキー
	private String[] key = null;
	// メッセージ登録用PreparedStatement
	private PreparedStatement pstmt = null;
    // 共通メッセージ
	private Map map = null;
    // 共通SQLクラス
	private MB380000_CommonSql comSql = new MB380000_CommonSql(); 
	// データベース
	private MasterDataBase dataBase = null;
    // 共通項目チェッククラス
	private mst38A101_CheckItem checkItem = null;
    // 共通項目チェッククラス
	private boolean blCheckFlg = true;
	// マスタ日付
	private String masterDT = null;	
	// デフォルト有効日
	private String defYukoDt = null;	

	private final String BATCH_ID = "MB83-A1-01";
	private final String BATCH_NA = "登録票アップロードチェック処理";
    // データ種別
	private final String SYUBETU = mst006401_DataKindDictionary.SYOHIN.getCode();
	// 項目の削除を表す文字
	private final String deleteString = "*";
	
	// バッチでは、商品コードの有効レコードのチェックをされている、
	// CheckItemでのチェックは有効無効関係なく、あったらエラーになる。それは間違い結果になる
	// バッチからの新規チェックする時、CheckItemでの商品コードの
	// 商品コードをチェックするか否か、デフォルトはチェックする
	protected boolean chkSyohin = true;
	
   private PreparedStatement syohinSelectPstmt = null;	//商品マスタ検索用PreparedStatement

   // パンチデータ用チェック対応用  [true:パンチデータ false:通常データ（デフォルト）]
   protected boolean punchKb = false;

   // マスタ制御情報
   protected String taikeiKirikaeJyotai = null;
   protected RMasterControlBean mstCtrlBean = null;

	/**
	 * コントラクト
	 * 
	 * @throws SQLException
	 */
	public SyohinCheckAbstract(MasterDataBase dataBase) throws SQLException {
		// データベース設定
		this.setDataBase(dataBase);
		
		// 共通メッセージを取得
		//#6409 Mod 2022.01.04 HOAI.TTT (S)
		//map = mst38A101_CommonMessage.getMsg();
		map = mst38A100_CommonMessage.getMsg();
		//#6409 Mod 2022.01.04 HOAI.TTT (E)
		
		// ステートメント作成
		pstmt = comSql.getPreparedMessageSQL(dataBase);
		
		// マスタ日付を取得する
		this.setMasterDT(RMSTDATEUtil.getMstDateDt());

		// デフォルト有効日を取得する
		defYukoDt = DateChanger.addDate(masterDT, 1);

		//制御情報を取得する
		RMasterControlUtil.getMstCtrlBeanFromDataBase();
		mstCtrlBean = RMasterControlUtil.getMstCtrlBean();
	}
	
	/**
	 * 全体共通項目チェック
	 * 
	 * @param なし
	 * @return なし
	 * 
	 * @throws Exception
	 */
	public void dataCheck() throws Exception {
		
		// 全体共通項目チェック処理クラス
		checkItem = new mst38A101_CheckItem(this.targetRS, this.pstmt,	this.map, this.SYUBETU);
		
		checkItem.setChkSyohin(chkSyohin);
		checkItem.setPunchKb(punchKb);
		
		// 全体共通項目のチェック処理を行う
		this.setBlCheckFlg(checkItem.commonCheck());
		
		// 商品マスタ共通項目チェック処理
		if (!masterCommonCheck()){
			this.setBlCheckFlg(false);
		}
	}
	
	/**
	 * 本処理
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		
		// 処理開始ログ
//		batchLog.getLog().info(BATCH_ID, BATCH_NA, "共通項目のチェックを開始します。");
		
		// 共通チェック処理
		this.dataCheck();
		
		// 処理開始ログ
//		batchLog.getLog().info(BATCH_ID, BATCH_NA, "共通項目のチェックを終了します。");
	}
	
	/**
	 * 処理結果メッセージテーブルへ追加する
	 * 
	 * @throws Exception
	 */
	public void dataUpdate() throws Exception {
		
		// メッセージテーブルに登録する
		messageUpdate();
	}
	
	/**
	 * 商品マスタ共通項目チェック
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws SQLException
	 */
	private boolean masterCommonCheck() throws SQLException {
		
        // エラーフラグ
		boolean checkFg = true; 

		// 修正区分
		String syusei_kb = this.targetRS.getString("syusei_kb");
		String strSyubetsu = targetRS.getString("excel_file_syubetsu").trim();

		// システム区分
		String systemKb = null;
		if("A08".equals(strSyubetsu)){
			systemKb = mst000611_SystemKbDictionary.T.getCode();
		} else if("A07".equals(strSyubetsu)){
			systemKb = mst000611_SystemKbDictionary.J.getCode();
		} else if("GRO".equals(strSyubetsu)){
			systemKb = mst000611_SystemKbDictionary.G.getCode();
		} else if("FRE".equals(strSyubetsu)){
			systemKb = mst000611_SystemKbDictionary.F.getCode();
		}

		// 有効日
		String yukoDt = null;
		yukoDt = targetRS.getString("yuko_dt");
		
		// 2016/10/27 T.Arimoto #2256対応（S)
		if( null == yukoDt ){
			setPreparedMessageSQL("0214");
			checkFg = false;
			return checkFg;
		}
		// 2016/10/27 T.Arimoto #2256対応（E)
		
		// 商品体系切替状態取得
		RMasterControlUtil.getMstCtrlBeanFromDataBase(mstCtrlBean);
		taikeiKirikaeJyotai = RMasterControlUtil.getTaikeiKirikaeJyotai(systemKb, masterDT, yukoDt);

		mst000101_DbmsBean db = null;
		try
		{
			// DBインスタンス作成
			db = mst000101_DbmsBean.getInstance();

			// 有効日のチェック
			if (isNotBlank(targetRS.getString("yuko_dt"))) {
			
				// 日付共通の適当性チェック
				if (!mst000401_LogicBean.DateChk(targetRS.getString("yuko_dt"))) {
					setPreparedMessageSQL("0214");
					checkFg = false;
				} else {
					String[] strReturn = new String[3];
					// 2016/10/25 T.Arimoto #2256対応（S)
//					// マスタ管理日付の翌日から一年後までの間でなければエラー
					// マスタ管理日付から一年後までの間でなければエラー
					if( !targetRS.getString("yuko_dt").equals(masterDT) ){
					// 2016/10/25 T.Arimoto #2256対応（E)
						strReturn = mst000401_LogicBean.getYukoDtRangeCheck(db,
						targetRS.getString("yuko_dt"));
						// エラーの場合
						if (strReturn[0]
								.equals(mst000101_ConstDictionary.ERR_CHK_ERROR)) {
							setPreparedMessageSQL("0214");
							checkFg = false;
						}
					// 2016/10/25 T.Arimoto #2256対応（S)
					}
					// 2016/10/25 T.Arimoto #2256対応（E)
				}
//			}else{
//				setPreparedMessageSQL("0264");	// "有効日が入力されていません。"
//				checkFg = false;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (db != null) db.close();
			db = null;
		}

		// 削除、予約取消の場合は、主キー以外の項目をチェックする必要がない
		if(syusei_kb == null){
			return false;
		}else if(syusei_kb.equals(mst006701_SyuseiKbDictionary.DELETE.getCode()) || syusei_kb.equals(mst006701_SyuseiKbDictionary.CANCEL.getCode())){
			return checkFg;
		}
		
		if(!checkFg){
			return checkFg;
		} else {

			// ユニットコード
			if (!isNotBlank(targetRS.getString("bunrui5_cd"))) {
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
					// 新規の場合、必須
					setPreparedMessageSQL("0119");
					checkFg = false;
				}
			} else {

				if(!taikeiKirikaeJyotai.equals(mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEATO.getCode())) {
					if (!isNotBlank(targetRS.getString("rst_unit_ck"))) {
						setPreparedMessageSQL("0121");
						checkFg = false;
					}
				} else {
					if (!isNotBlank(targetRS.getString("rstk_unit_ck"))) {
						setPreparedMessageSQL("0121");
						checkFg = false;
					}
				}
			}

			// 配布コード・チェックなし
//			// サブクラスコード
//			// タグだけある

			//商品区分
			if (isNotBlank(targetRS.getString("SYOHIN_KB")) && !isNotBlank(targetRS.getString("SYOHIN_KB_CK"))) {
				setPreparedMessageSQL("0428");
				checkFg = false;
			}
			
			//税区分
			if (isNotBlank(targetRS.getString("ZEI_KB")) && !isNotBlank(targetRS.getString("ZEI_CK"))) {
				setPreparedMessageSQL("0400");
				checkFg = false;
			}
			
			//税率区分
			if (isNotBlank(targetRS.getString("TAX_RATE_KB")) && !isNotBlank(targetRS.getString("TAX_RATE_CK"))) {
				setPreparedMessageSQL("0401");
				checkFg = false;
			}
			if (isNotBlank2(targetRS.getString("TAX_RATE_KB")) && deleteString.equals(targetRS.getString("TAX_RATE_KB").trim())) {
				setPreparedMessageSQL("0431");
				checkFg = false;
			}
			
			// 原価単価≦売価単価チェック
			String strGenTanka = targetRS.getString("gentanka_vl");
			String strBaiTanka = targetRS.getString("baitanka_vl");

			if (!isNotBlank(strGenTanka)) {
				strGenTanka = targetRS.getString("RS_GENTANKA_VL");
			}

			if (!isNotBlank(strBaiTanka)) {
				strBaiTanka = targetRS.getString("RS_BAITANKA_VL");
			}
			
//			if (isNotBlank(strGenTanka) && isNotBlank(strBaiTanka) && Double.parseDouble(strGenTanka.trim()) > Double.parseDouble(strBaiTanka.trim())) {
//				setPreparedMessageSQL("0126");
//				checkFg = false;
//			}
					
			// 仕入先コード
			if (!isNotBlank(targetRS.getString("siiresaki_cd"))) {
				// 新規の場合必須
				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
					setPreparedMessageSQL("0129");
					checkFg = false;
				}
			} else {
				// 仕入先マスタに存在しない場合エラー
				if (!isNotBlank(targetRS.getString("siiresaki_ck"))) {
					setPreparedMessageSQL("0130");
					checkFg = false;
				}
			}

			// EOS区分
			if (!isNotBlank(targetRS.getString("eos_kb"))) {
//  2013.05.17 [MSTC00013]  商品マスタ初期値設定対応
//				// 新規の場合必須
//				if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT
//						.getCode())) {
//					setPreparedMessageSQL("0141");
//					checkFg = false;
//				}
			} else {
				// 名称CTFマスタに存在しない場合エラー
				if (!isNotBlank(targetRS.getString("eos_ck"))) {
					setPreparedMessageSQL("0142");
					checkFg = false;
				}
			}

			// 発注単位呼称
			if (isNotBlank(targetRS.getString("HACHU_TANI_NA")) && !isNotBlank(targetRS.getString("HACHU_TANI_CK"))) {
				setPreparedMessageSQL("0402");
				checkFg = false;
			}			

			// 販売単位呼称
			if (isNotBlank(targetRS.getString("HANBAI_TANI")) && !isNotBlank(targetRS.getString("HANBAI_TANI_CK"))) {
				setPreparedMessageSQL("0403");
				checkFg = false;
			}			

			// ケース発注区分
			if (isNotBlank(targetRS.getString("CASE_HACHU_KB")) && !isNotBlank(targetRS.getString("CASE_HACHU_CK"))) {
				setPreparedMessageSQL("0404");
				checkFg = false;
			}			

			if (isNotBlank2(targetRS.getString("hanbai_st_dt")) && deleteString.equals(targetRS.getString("hanbai_st_dt").trim())) {
				setPreparedMessageSQL("0132");
				checkFg = false;
			} else if (isNotBlank2(targetRS.getString("hanbai_ed_dt")) && deleteString.equals(targetRS.getString("hanbai_ed_dt").trim())) {
				setPreparedMessageSQL("0132");
				checkFg = false;
			}
			
			if ((isNotBlank(targetRS.getString("hanbai_st_dt")))
					&& (isNotBlank(targetRS.getString("hanbai_ed_dt")))) {
						if (!mst000401_LogicBean.DateChk(targetRS.getString("hanbai_st_dt"))
								|| (!"99999999".equals(targetRS.getString("hanbai_ed_dt"))
									 && !mst000401_LogicBean.DateChk(targetRS.getString("hanbai_ed_dt")))
							) {
							setPreparedMessageSQL("0133");
							checkFg = false;
						} else {

//							自動補正の為、補正後に対応したチェックに変更
//							// 有効日が空場合
//							// 有効日が空ではない場合
//							// 販売終了日＞販売開始日ではない場合エラー
//							// 販売終了日が99999999の場合はチェックの必要がなし
							
							String str1 = targetRS.getString("hanbai_st_dt");
							String str2 = targetRS.getString("hanbai_ed_dt");
							if (syusei_kb.equals(mst006701_SyuseiKbDictionary.INSERT.getCode())) {
								String strw = targetRS.getString("yuko_dt");
								if(strw == null || strw.trim().equals("")){
									strw = DateChanger.addDate(this.getMasterDT(), 1);
								}
								if (isNotBlank(strw)) {
									if (Long.parseLong(str1) < Long.parseLong(strw)) {
										str1 = strw;
									}
								}
							}
							if (Long.parseLong(str1) >= Long.parseLong(str2)) {
								setPreparedMessageSQL("0606");
								checkFg = false;
							}
						}
					}

			// 発注開始日と終了日のチェック
			if (isNotBlank(targetRS.getString("ten_hachu_st_dt"))
					&& isNotBlank(targetRS.getString("ten_hachu_ed_dt"))) {
				// 発注開始日,発注終了日適当性チェック
				if (!mst000401_LogicBean.DateChk(targetRS.getString("ten_hachu_st_dt"))
						|| (!"99999999".equals(targetRS.getString("ten_hachu_ed_dt"))
							&& !mst000401_LogicBean.DateChk(targetRS.getString("ten_hachu_ed_dt")))
					) {
					setPreparedMessageSQL("0137");
					checkFg = false;
				} else {
					
// 販売日と発注日の絡みのチェックを外す
//					// 発注開始日＞＝販売開始日ではない場合エラー
//					// 発注終了日＜＝販売終了日ではない場合エラー
//					// 販売終了日または発注終了日が99999999の場合はチェックの必要がなし
//					// 発注終了日＞発注開始日ではない場合エラー
//					// 販売終了日が99999999の場合はチェックの必要がなし
					
					// 発注終了日≧発注開始日ではない場合エラー
					// 発注終了日が99999999の場合はチェックの必要がなし					
					if(!"99999999".equals(targetRS.getString("ten_hachu_ed_dt"))){
						if (DateDifference.getDifferenceDays(targetRS
								.getString("ten_hachu_st_dt"), targetRS
								.getString("ten_hachu_ed_dt")) < 0) {
							setPreparedMessageSQL("0140");
							checkFg = false;
						}
					}
				}
			}

			// 定貫区分
			if (isNotBlank(targetRS.getString("TEIKAN_KB")) && !isNotBlank(targetRS.getString("TEIKAN_CK"))) {
				setPreparedMessageSQL("0405");
				checkFg = false;
			}
			
			// 相場商品区分
			if (isNotBlank(targetRS.getString("SOBA_SYOHIN_KB")) && !isNotBlank(targetRS.getString("SOBA_SYOHIN_CK"))) {
				setPreparedMessageSQL("0427");
				checkFg = false;
			}
			
			// ①便区分
			if (isNotBlank(targetRS.getString("BIN_1_KB")) && !isNotBlank(targetRS.getString("BIN1_CK"))) {
				setPreparedMessageSQL("0203");
				checkFg = false;
			}
			
			// ①締め時間
			if (isNotBlank(targetRS.getString("SIME_TIME_1_QT")) && !isNotBlank(targetRS.getString("SIME_TIME1_CK"))) {
				setPreparedMessageSQL("0406");
				checkFg = false;
			}
			
			// ②便区分
			if (isNotBlank(targetRS.getString("BIN_2_KB")) && !isNotBlank(targetRS.getString("BIN2_CK"))) {
				setPreparedMessageSQL("0223");
				checkFg = false;
			}
			
			// ②締め時間
			if (isNotBlank(targetRS.getString("SIME_TIME_2_QT")) && !isNotBlank(targetRS.getString("SIME_TIME2_CK"))) {
				setPreparedMessageSQL("0407");
				checkFg = false;
			}
			
			// Ｆ便区分
			if (isNotBlank(targetRS.getString("F_BIN_KB")) && !isNotBlank(targetRS.getString("F_BIN_CK"))) {
				setPreparedMessageSQL("0430");
				checkFg = false;
			}
			
//			物流区分
//			値が登録されていて、且つ名称CTFマスタに存在しない場合エラー
			if (isNotBlank(targetRS.getString("buturyu_kb")) && !isNotBlank(targetRS.getString("buturyu_ck"))) {
				setPreparedMessageSQL("0261");
				checkFg = false;
			}

			// センター在庫区分
			if (isNotBlank(targetRS.getString("CENTER_ZAIKO_KB")) && !isNotBlank(targetRS.getString("CENTER_ZAIKO_CK"))) {
				setPreparedMessageSQL("0408");
				checkFg = false;
			}
			
			// センター許容区分
			if (isNotBlank(targetRS.getString("CENTER_KYOYO_KB")) && !isNotBlank(targetRS.getString("CENTER_KYOYO_CK"))) {
				setPreparedMessageSQL("0409");
				checkFg = false;
			}
			
			// センター賞味期間区分
			if (isNotBlank(targetRS.getString("CENTER_SYOMI_KIKAN_KB")) && !isNotBlank(targetRS.getString("CENTER_SYOMI_KIKAN_CK"))) {
				setPreparedMessageSQL("0410");
				checkFg = false;
			}
			
			// ＣＧＣ返品区分
			if (isNotBlank(targetRS.getString("CGC_HENPIN_KB")) && !isNotBlank(targetRS.getString("CGC_HENPIN_CK"))) {
				setPreparedMessageSQL("0411");
				checkFg = false;
			}
			
			// 販売期間区分
			if (isNotBlank(targetRS.getString("HANBAI_LIMIT_KB")) && !isNotBlank(targetRS.getString("HANBAI_LIMIT_CK"))) {
				setPreparedMessageSQL("0412");
				checkFg = false;
			}
			
			
//			// PB区分のチェック
			
//			// 商品区分（FUJI)のチェック
		}
		return checkFg;
	}

	/**
	 * エラーメッセージテーブル登録処理
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws SQLException
	 */
	private void messageUpdate() throws SQLException {
		
		// エラーがある場合
		if (!this.isBlCheckFlg()) {
			// メッセージテーブルに登録する
			pstmt.executeBatch();
		}
	}

	/**
	 * 結果メッセージ作成SQL
	 * 
	 * @param  メッセージID
	 * @return なし
	 * 
	 * @throws Exception
	 */
	// 2017/10/04 T.Arimoto #5994対応 (S)
	// public void setPreparedMessageSQL(String megNo) throws SQLException {
	public void setPreparedMessageSQL(String megNo, Object... args ) throws SQLException {
		// 2017/10/04 T.Arimoto #5994対応 (E)

		int idx = 0;
		String strDefaultProductTimestamp = AbstractMDWareDateGetter
		.getDefaultProductTimestamp("rbsite_ora"); // システム日時
		//Add 2015.10.09 DAI.BQ (S)
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		//Add 2015.10.09 DAI.BQ (E)
		// 取込日
		idx++;
		pstmt.setString(idx, key[0]);
		// EXCELファイル種別
		idx++;
		pstmt.setString(idx, key[1]);
		// 受付ファイルNo
		idx++;
		pstmt.setString(idx, key[2]);
		// 受付SEQNo
		idx++;
		pstmt.setString(idx, key[3]);
		// シート種別
		idx++;
		pstmt.setString(idx, key[4]);
		// 結果メッセージコード
		idx++;
		pstmt.setString(idx, megNo);
		// 結果メッセージ
		idx++;
		// 2017/10/04 T.Arimoto #5994対応 (S)
		// pstmt.setString(idx, MessageUtil.getMessage((String)map.get(megNo), userLocal));
		// 2017/10/04 T.Arimoto #5994対応 (E)
		//#6409 Mod 2022/01/04 HOAI.TTT (S)
		//pstmt.setString(idx, MessageUtil.getMessage((String)map.get(megNo), userLocal, args));
		pstmt.setString(idx,(String)map.get(megNo));
		//#6409 Mod 2022/01/04 HOAI.TTT (E)
		// 作成年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 作成ID
		idx++;
		pstmt.setString(idx, key[5]);
		// 更新年月日
		idx++;
		pstmt.setString(idx, strDefaultProductTimestamp);
		// 更新ID
		idx++;
		pstmt.setString(idx, key[5]);
		
		pstmt.addBatch();
	}
	
	/**
	 * CTFマスタに存在するかのチェック（共通部）
	 * 
	 * @param ResultSet rs
	 * @param String col1 CTFマスタのチェック結果のカラム名
	 * @param String msgNo エラー時のメッセージNo
	 * @return boolean 存在しないチェック
	 * 
	 * @throws SQLException
	 */
	public boolean rNamectfCheck(ResultSet rs, String col1, String msgNo) throws SQLException {
		String strValue = rs.getString(col1);
		
		if (!isNotBlank(strValue)) {
			// CTFに存在しない
			setPreparedMessageSQL(msgNo);
			return false;
		}
		return true;
	}

	/**
	 * 入力された値がブランクかのチェック
	 * 
	 * @param val String
	 * @return ある：true;ない:false
	 */
	public boolean isNotBlank(String val) {
		if (val != null && !val.trim().equals(deleteString) && val.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 入力された値がブランクかのチェック
	 * 
	 * @param val String
	 * @return ある：true;ない:false
	 */
	public boolean isNotBlank2(String val) {
		if (val != null && val.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * TRテーブルのキーのセット処理
	 * 
	 * @param  なし
	 * @return なし
	 * 
	 * @throws SQLException
	 */
	public void setTRKey() throws SQLException {
		String[] key = new String[6]; // キー項目

		key[0] = this.getTargetRS().getString("torikomi_dt"); // 取込日
		key[1] = this.getTargetRS().getString("excel_file_syubetsu"); // EXCELファイル種別
		key[2] = this.getTargetRS().getString("uketsuke_no"); // 受付No.
		key[3] = this.getTargetRS().getString("uketsuke_seq"); // 受付SEQNo.
		key[4] = this.SYUBETU; // シート種別
		key[5] = this.getTargetRS().getString("by_no"); // 登録承認BY_NO
		// メッセージ登録用
		this.setKey(key);
//		System.out.println(key[0] + "	" + key[1] + "	" + key[2] + "	" + key[3] + "	" + key[4] + "	" + key[5]);
	}	
	
	/**
	 * TRテーブルのキーのセット処理
	 * @param  key
	 * @return なし
	 */
	public void setKey(String[] key) {
		this.key = key;
	}
	
	/**
	 * データセットのゲット処理
	 * 
	 * @param  なし
	 * @return targetRS
	 */
	public ResultSet getTargetRS() {
		return targetRS;
	}
	
	/**
	 * データセットのセット処理
	 * 
	 * @param  targetRS
	 * @return なし
	 */
	public void setTargetRS(ResultSet targetRS) {
		this.targetRS = targetRS;
	}
	
	/**
	 * データベースのゲット処理
	 * 
	 * @param  なし
	 * @return dataBase
	 */
	public MasterDataBase getDataBase() {
		return dataBase;
	}
	
	/**
	 * データベースのセット処理
	 * 
	 * @param  なし
	 * @return dataBase
	 */
	public void setDataBase(MasterDataBase dataBase) {
		this.dataBase = dataBase;
	}

	/**
	 * チェックフラグ
	 * 
	 * @param  blCheckFlg
	 * @return dataBase
	 */
	public boolean isBlCheckFlg() {
		return blCheckFlg;
	}
	
	/**
	 * チェックフラグのセット処理
	 * 
	 * @param  blCheckFlg
	 * @return なし
	 */
	public void setBlCheckFlg(boolean blCheckFlg) {
		this.blCheckFlg = blCheckFlg;
	}
	
	/**
	 * マスタ日付のゲット処理
	 * 
	 * @param  なし
	 * @return masterDT
	 */
	public String getMasterDT() {
		return masterDT;
	}
	
	/**
	 * マスタ日付のセット処理
	 * 
	 * @param  masterDT
	 * @return なし
	 */
	public void setMasterDT(String masterDT) {
		this.masterDT = masterDT;
	}
	
	/**
	 * @param b
	 */
	public void setChkSyohin(boolean b) {
		chkSyohin = b;
	}
	
	/**
	 * @param b
	 */
	public void setPunchKb(boolean b) {
		punchKb = b;
	}
	
	/**
	 * 商品体系切替状態のゲット処理
	 * 
	 * @param  なし
	 * @return masterDT
	 */
	public String getTaikeiKirikaeJyotai() {
		return taikeiKirikaeJyotai;
	}

	/**
	 * POSコードの重複チェック
	 * Add by kou 2006/10/3
	 */
	public boolean isValidPosCD(String tableName, String uketsukeNo, String posCd)
		 throws SQLException {
		 	
		StringBuffer sb = new StringBuffer();
		
		sb.append(" 	SELECT COUNT(*) CNT FROM R_SYOHIN ");
		sb.append(" 	 WHERE SYOHIN_2_CD = '").append(posCd).append("'");

		ResultSet rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 0) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
//		rs.close();
		dataBase.closeResultSet(rs);
		
		sb = new StringBuffer();
		sb.append(" 	SELECT COUNT(*) CNT FROM ").append(tableName);
		sb.append(" 	 WHERE POS_CD ='").append(posCd).append("'");
		sb.append(" 	   AND UKETSUKE_NO = ").append(uketsukeNo);

		rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 1) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
		
//		rs.close();
		dataBase.closeResultSet(rs);
		return true;
	}

	/**
	 * POSコードの重複チェック
	 * Add by kou 2006/10/3
	 */
	public boolean isValidPosCD(String tableName, String uketsukeNo, String posCd, String syohinCd, String yukoDt)
		 throws SQLException {
		 	
		String syohin_cd = syohinCd;
		String syohin_2_cd = posCd;
		String yuko_dt = yukoDt;
		StringBuffer sb = new StringBuffer();
		
		if (!isNotBlank(yuko_dt)) {
			yuko_dt = DateChanger.addDate(masterDT, 1);
		}

		sb.append("select");
		sb.append("    count(rs.syohin_2_cd) cnt ");
		sb.append("from ");
		sb.append("    r_syohin rs ");
		sb.append("where ");
		sb.append("    rs.syohin_2_cd = '").append(syohin_2_cd).append("' ");
		sb.append(" and ");
//		↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bunrui1_cd, rs.syohin_cd, '").append(yuko_dt).append("') ");
//		↓↓2007.01.26 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    rs.yuko_dt = ");
		sb.append("    rs.yuko_dt >= ");
//		↑↑2007.01.26 H.Yamamoto カスタマイズ修正↑↑
		sb.append("( ");
		sb.append("select");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    max(yuko_dt) ");
		sb.append("    coalesce(max(yuko_dt),'00000000') ");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from ");
		sb.append("    r_syohin rsw ");
		sb.append("where ");
		sb.append("    rsw.bunrui1_cd = rs.bunrui1_cd ");
		sb.append(" and ");
		sb.append("    rsw.syohin_cd = rs.syohin_cd ");
		sb.append(" and ");
		sb.append("    rsw.yuko_dt <= '").append(yuko_dt).append("' ");
		sb.append(") ");
		sb.append(" and ");
		sb.append("    rs.delete_fg = '0' ");
//		↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑
		if (isNotBlank(syohin_cd) && syohin_cd.trim().length() > 2) {
			sb.append(" and ");
			sb.append("    rs.syohin_cd <> '").append(syohin_cd).append("' ");
		}
//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑

		ResultSet rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 0) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
//		rs.close();
		dataBase.closeResultSet(rs);
//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑
		
		sb = new StringBuffer();
		sb.append(" 	SELECT COUNT(*) CNT FROM ").append(tableName);
		sb.append(" 	 WHERE POS_CD ='").append(posCd).append("'");
		sb.append(" 	   AND UKETSUKE_NO = ").append(uketsukeNo);
//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
		sb.append(" for read only ");
//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑

		rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 1) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
		
//		rs.close();
		dataBase.closeResultSet(rs);
		return true;
	}
/*	↓↓ Add by kema 2006/11/06
	 * 商品コード・部門コードの重複チェック
	 */
	public boolean isValidSyohinBumon(String syohinCd, String bumonCd)
		 throws SQLException {
		 	
		StringBuffer sb = new StringBuffer();
		
		//===BEGIN=== Modify by kou 2006/11/29
//		sb.append("select");
//		sb.append("   syohin_cd ");
//		sb.append("from ");
//		sb.append("    r_syohin ");
//		sb.append("where ");
//		sb.append("    syohin_cd = '").append(syohinCd.trim()).append("' ");
//		sb.append(" and ");
//		sb.append("    bunrui1_cd != '").append(bumonCd.trim()).append(" '");
//		sb.append(" and ");
//		sb.append("    delete_fg = '0' ");
//
//		ResultSet rs = dataBase.executeQuery(sb.toString());
		
		if(syohinSelectPstmt == null) {
			sb.append("select");
			sb.append("   syohin_cd ");
			sb.append("from ");
			sb.append("    r_syohin ");
			sb.append("where ");
			sb.append("    syohin_cd = ? ");
			sb.append(" and ");
			sb.append("    bunrui1_cd != ? ");
			sb.append(" and ");
			sb.append("    delete_fg = '0' ");
			syohinSelectPstmt = dataBase.getPrepareStatement(sb.toString());
		}
		syohinSelectPstmt.setString(1,syohinCd);
		syohinSelectPstmt.setString(2,bumonCd);

		ResultSet rs = syohinSelectPstmt.executeQuery();
		//===END=== Modify by kou 2006/11/29
		
		if(rs.next()) {
//			rs.close();
			dataBase.closeResultSet(rs);
			return false;
		} 
		
//		rs.close();
		dataBase.closeResultSet(rs);
		return true;
	}
//	↑↑ Add by kema 2006/11/06
	/**
	 * 商品コードの重複チェック
	 * Add by kou 2006/10/27
	 */
	public boolean isValidSyohinCD(String tableName, String uketsukeNo, String syohinCd, String yukoDt)
		 throws SQLException {
		 	
		StringBuffer sb = new StringBuffer();
		
		if (!isNotBlank(yukoDt)) {
			yukoDt = DateChanger.addDate(masterDT, 1);
		}

		sb.append("select");
		sb.append("    count(rs.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_syohin rs ");
		sb.append("where ");
		sb.append("    rs.syohin_cd = '").append(syohinCd).append("' ");
		sb.append(" and ");
//		↓↓2006.12.15 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    rs.yuko_dt = MSP710101_GETSYOHINYUKODT(rs.bunrui1_cd, rs.syohin_cd, '")
//			.append(yukoDt).append("') ");
//		↓↓2007.01.26 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    rs.yuko_dt = ");
		sb.append("    rs.yuko_dt >= ");
//		↑↑2007.01.26 H.Yamamoto カスタマイズ修正↑↑
		sb.append("( ");
		sb.append("select");
//		↓↓2007.02.06 H.Yamamoto カスタマイズ修正↓↓
//		sb.append("    max(yuko_dt) ");
		sb.append("    coalesce(max(yuko_dt),'00000000') ");
//		↑↑2007.02.06 H.Yamamoto カスタマイズ修正↑↑
		sb.append("from ");
		sb.append("    r_syohin rsw ");
		sb.append("where ");
		sb.append("    rsw.bunrui1_cd = rs.bunrui1_cd ");
		sb.append(" and ");
		sb.append("    rsw.syohin_cd = rs.syohin_cd ");
		sb.append(" and ");
		sb.append("    rsw.yuko_dt <= '").append(yukoDt).append("' ");
		sb.append(") ");
		sb.append(" and ");
		sb.append("    rs.delete_fg = '0' ");
//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
//		sb.append(" for read only ");
//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑
//		↑↑2006.12.15 H.Yamamoto カスタマイズ修正↑↑

		ResultSet rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 0) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
//		rs.close();
		dataBase.closeResultSet(rs);	
		rs = null;
//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑
		//後者優先処理は別で行っているため省く		
//		if (syohinCd != null && syohinCd.trim().length() > 2) {
//			sb = new StringBuffer();
//			sb.append(" 	SELECT COUNT(*) CNT FROM ").append(tableName);
//			sb.append(" 	 WHERE TR_SYOHIN_CD ='").append(syohinCd).append("'");
//			sb.append(" 	   AND UKETSUKE_NO = ").append(uketsukeNo);
//	//		↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
//	//		sb.append(" for read only ");
//	//		↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑
//	
//			rs = dataBase.executeQuery(sb.toString());
//			
//			if(rs.next()) {
//				if(rs.getInt("CNT") > 1) {
////					rs.close();
//					dataBase.closeResultSet(rs);
//					return false;
//				} 
//			} 
////			rs.close();
//			dataBase.closeResultSet(rs);	
//		}
		
		return true;
	}

//	↓↓2007.02.13 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 商品コードの終了チェック
	 */
	public boolean isEndSyohinCD(String tableName, String uketsukeNo, String syohinCd, String yukoDt)
		 throws SQLException {
		
		if (!isNotBlank(yukoDt)) {
			return true;
		}
		if (yukoDt.equals(DateChanger.addDate(masterDT, 1))){
			return true;
		}
		 	
		StringBuffer sb = new StringBuffer();

		sb.append("select");
		sb.append("    count(rs.syohin_cd) cnt ");
		sb.append("from ");
		sb.append("    r_syohin rs ");
		sb.append("where ");
		sb.append("    rs.syohin_cd = '").append(syohinCd).append("' ");
		sb.append(" and ");
		sb.append("    rs.yuko_dt > '").append(masterDT).append("' ");
		sb.append(" and ");
		sb.append("    rs.yuko_dt < '").append(yukoDt).append("' ");
		sb.append(" and ");
		sb.append("    rs.delete_fg = '1' ");

		ResultSet rs = dataBase.executeQuery(sb.toString());
		
		if(rs.next()) {
			if(rs.getInt("CNT") > 0) {
//				rs.close();
				dataBase.closeResultSet(rs);
				return false;
			} 
		} 
//		rs.close();
		dataBase.closeResultSet(rs);

		return true;
	}
//	↑↑2007.02.13 H.Yamamoto カスタマイズ修正↑↑

}
