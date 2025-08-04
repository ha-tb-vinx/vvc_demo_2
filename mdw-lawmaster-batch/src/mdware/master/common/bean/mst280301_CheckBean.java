/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス 価格チェックマスタ登録 における商品コードチェック専用クラス </P>
 * <P>説明 : 新ＭＤシステム　マスターメンテナンス 価格チェックマスタ登録 における商品コードチェック専用クラス </P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author Sirius M.Yamada
 * @version 1.0 (2005.03.22) 初版作成
 * @see なし
 */
package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.RMSTDATEUtil;


/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst510101用仕入先発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>
 *  <P>会社名: Vinculum Japan Corp.</P>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 * @see なし
 */
public class mst280301_CheckBean {

	/**
	 *  constructor.
	 */
	public mst280301_CheckBean() {
	}

	/**
	 *  商品コードのチェック.
	 *
	 *  @param keepBean   KeepBean
	 *  @param db         DbmsBean
	 *  @param ctrlColor  Map
	 *  @param map        TreeMap メッセージマップ
	 */
	public void checkSyohin(
		mst280201_KeepBean keepBean,
		mst000101_DbmsBean db,
		Map                ctrlColor,
		TreeMap            map,
		SessionManager      sessionManager

	) throws SQLException,Exception {
		///////////////////////
		// 商品コードのチェック
		///////////////////////
		//商品コード存在チェック
		mst000701_MasterInfoBean mstchk;
		//各種テーブルの更新情報の取得
		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		ArrayList lst = new ArrayList();
		mstchk = new mst000701_MasterInfoBean();
		String mstTableNa = "R_SYOHIN";
		//_LogicBean.chkNullString(dataHolder.getParameter("tx_hanku_cd")).trim()
//		↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//		if ( ! keepBean.getHankuCd().equals("")) {
//			// o
//			//    販区コード入力ありの場合
//			// o
//			//
//			// 商品コード存在チェック
//			//lst.add("HANKU_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_hanku_cd")) + "' ");
//			//lst.add(" and " + " SYOHIN_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohin_cd")) + "' ");
//			lst.add("HANKU_CD = '"             + keepBean.getHankuCd() + "'");
//			lst.add(" and " + " SYOHIN_CD = '" + keepBean.getSyohinCd() + "'");
//// 2006.01.24 Y.Inaba Mod ↓
////			mstchk = mst000401_LogicBean.getMasterCdName(db, mstTableNa,"HINMEI_KANJI_NA", lst, "0");
//			mstchk = mst000401_LogicBean.getMasterCdName(db, mstTableNa,"HINMEI_KANJI_NA", lst, "0", RMSTDATEUtil.getMstDateDt());
//// 2006.01.24 Y.Inaba Mod ↑
//			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
//				if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//					mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_syohin_cd");
//					keepBean.setFirstFocus("tx_syohin_cd");
//					keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//					keepBean.setErrorMessage(map.get("40026").toString());
//				}
//			}
//		} else {
//			//   販区コード未入力の場合
//			//  1. 販区コード複数登録チェック. 複数見つかった場合エラー
//			multiHankuCheck(keepBean, db, ctrlColor, map);
//
//			// 2. 取得販区に対する売区チェック			// ----------------------------------
//			String hankuCd = urikuCheck(keepBean, db, ctrlColor, map, sessionManager);
//
//			// 販区コードをkeepBeanに保存
//			if (hankuCd != null && hankuCd.length() > 0) {
//				hankuCd = hankuCd.trim();
//				keepBean.setHankuCd(hankuCd);
//			}
//		}
//		↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
	}

	/**
	 *  販区コード複数登録チェック. 複数見つかった場合エラー.
	 *  @param keepBean   KeepBean
	 *  @param db         DbmsBean
	 *  @param ctrlColor  control color map
	 *  @param map        message map
	 */
	private void multiHankuCheck(
		mst280201_KeepBean  keepBean,
		mst000101_DbmsBean  db,
		Map                 ctrlColor,
		TreeMap             map
	) throws SQLException {
		// ---------------------------------------------
		// 1. 販区コード複数登録チェック
		//    複数見つかった場合エラー
		// ---------------------------------------------
		ArrayList lst = new ArrayList();
		mst000701_MasterInfoBean  stchk = new mst000701_MasterInfoBean();
		//lst.add(" SYOHIN_CD  = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tx_syohin_cd")) + "' ");
		lst.add(" SYOHIN_CD  = '" + keepBean.getSyohinCd() + "'");
		lst.add(" and " + " DELETE_FG = '" + mst000801_DelFlagDictionary.INAI.getCode() + "' ");
		lst.add(" GROUP BY ");
		lst.add(" 		SYOHIN_CD,HANKU_CD ");
		mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
		//ResultSet rset = db.executeQuery( mstDM.getCountSql(mstTableNa,lst) );  //抽出結果(ResultSet)
		ResultSet rset = db.executeQuery( mstDM.getCountSql("R_SYOHIN",lst) );  //抽出結果(ResultSet)
		int intCnt = 0;
		while (rset.next()) {
			intCnt = intCnt + 1;
		}

		if (intCnt > 1){
			if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
				mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_hanku_cd");
				keepBean.setFirstFocus("tx_hanku_cd");
				keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				keepBean.setErrorMessage("販区コード" + map.get("0021").toString());
			}
		} else if (intCnt == 0) {
			// データが存在しない
			if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
				mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_syohin_cd");
				keepBean.setFirstFocus("tx_syohin_cd");
				keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				keepBean.setErrorMessage(map.get("40026").toString());
			}
		}
	}

	/**
	 *  取得販区に対する売区チェック.
	 *
	 *  @param keepBean        KeepBean
	 *  @param db              mst000101_DbmsBean
	 *  @param ctrlColor       color contrl map
	 *  @param map             message map
	 *  @param sessionManager  SessionManager object
	 *  @return 商品マスタの該当レコードが１件に限定される場合はその販区コードを返す。
	 *          それ以外はnullを返す。
	 */
	private String urikuCheck(
		mst280201_KeepBean keepBean,
		mst000101_DbmsBean db,
		Map                ctrlColor,
		TreeMap            map,
		SessionManager     sessionManager
	) throws SQLException,Exception {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		String hankuCd = null;
		// 商品マスタから販区コードを取得
		String sql = new StringBuffer()
		.append("SELECT SYOHIN_CD, HANKU_CD ")
		.append(" FROM R_SYOHIN ")
		.append(" WHERE ")
		.append(" SYOHIN_CD=").append("'").append(keepBean.getSyohinCd()).append("'")
		.append(" AND ")
		.append(" DELETE_FG=").append("'").append(mst000801_DelFlagDictionary.INAI.getCode()).append("'")
		.append(" GROUP BY SYOHIN_CD, HANKU_CD ")
		.toString();
		ResultSet rset = db.executeQuery(sql);
		if (rset.next()) {
			// 商品マスタから取得した販区コード
			// rsetに１件しかない場合に販区コードが限定される
			// rsetに複数件ある場合もここでは先頭の１件のみ抽出する。
			hankuCd = mst000401_LogicBean.chkNullString(rset.getString("hanku_cd")).trim();

			// 販区コード存在チェック
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			ArrayList lst = new ArrayList();
			lst.add( "SYUBETU_NO_CD = " + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal));
			lst.add( " and "+ " CODE_CD = '" + hankuCd + "' ");
// 2006.01.24 Y.Inaba Mod ↓
//			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0");
			mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", RMSTDATEUtil.getMstDateDt());
// 2006.01.24 Y.Inaba Mod ↑
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				// 販区コード存在エラー
				mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_hanku_cd");
				keepBean.setFirstFocus("tx_hanku_cd");
				if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
					mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_hanku_cd");
					keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
					keepBean.setErrorMessage("指定された販区コード"+map.get("40007").toString());
				}
			} else {
				// 売り区１～３の配下にない場合はエラー
//				↓↓2006.07.20 H.Yamamoto ソースエラー回避修正↓↓
//				keepBean.setHankuNm(mstchk.getCdName()); // 販区名称設定
//				if ( ! mst000401_LogicBean.getHankuCdCheck(db, hankuCd, sessionManager)) {
//					mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_syohin_cd");
//					keepBean.setFirstFocus("tx_syohin_cd");
//					if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
//						mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_syohin_cd");
//						keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
//						keepBean.setErrorMessage(map.get("40026").toString());
//						//keepBean.setErrorMessage(map.get("40026").toString());
//					}
//				}
//				↑↑2006.07.20 H.Yamamoto ソースエラー回避修正↑↑
			}

		} else {
			if (keepBean.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)) {
				mst000401_LogicBean.setErrorBackColor(ctrlColor,"tx_syohin_cd");
				keepBean.setFirstFocus("tx_syohin_cd");
				keepBean.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				keepBean.setErrorMessage(map.get("40026").toString());
				//keepBean.setErrorMessage(map.get("40026").toString());
			}
		}
		return hankuCd;
	}
}
