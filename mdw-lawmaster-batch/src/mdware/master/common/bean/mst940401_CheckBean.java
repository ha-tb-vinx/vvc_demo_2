/**
 * <p>タイトル: mst940401_CheckBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索画面データの入力(検索条件)チェツククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst008801_SyohinTaikeiDictionary;

/**
 * <p>タイトル: mst940401_CheckBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索画面データの入力(検索条件)チェツククラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mst940401_CheckBean {

	/**
	 * ヘッダー情報(検索条件)チェック<p>
	 * @param db データベース接続コネクション
	 * @param Keepb 商品体系検索画面表示データ格納クラス
	 * @param CtrlColor 画面コントロールカラー
	 * @return なし
	 * @throws SQLException, Exception
	 */
	public void CheckHeader(mst000101_DbmsBean db, mst940201_KeepBean Keepb, Map CtrlColor)
		throws SQLException, Exception {

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		//検索条件にコードが指定されている場合、名称を取得
		if (Keepb.getCodeCd() != null && !Keepb.getCodeCd().equals("")) {
			String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
			List whereList = new ArrayList();
			whereList.clear();
			whereList.add(" SYUBETU_NO_CD = '" + MessageUtil.getMessage(convSyubetuNo(Keepb.getKaisouPatternKb()), userLocal) + "' ");
			whereList.add(" AND ");
			whereList.add(" CODE_CD = '" + Keepb.getCodeCd() + "' ");
			mst000701_MasterInfoBean mstchk = mst000401_LogicBean.getMasterCdName(db, "R_NAMECTF", "KANJI_RN", whereList, "0", "");
			//名称が取得できない場合はエラー
			if (!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)) {
				Keepb.setCodeNm("");//名称の初期化
				mst000401_LogicBean.setErrorBackColor(CtrlColor,"ct_codecd");
				Keepb.setFirstFocus("ct_codecd");
				Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_ERROR);
				Keepb.setErrorMessage("指定されたコード" + map.get("40007").toString());
				Keepb.setCodeErr(true);
			} else {
				Keepb.setCodeNm(mstchk.getCdName());//名称の設定
			}
		} else {
			Keepb.setCodeNm("");//名称の初期化
		}
	}//CheckHeader

	/**
	 * 種別番号変換<p>
	 * 商品体系検索画面用の階層パターン区分を該当する種別番号に変換する。<br>
	 * <pre>
	 *	商品体系検索画面	種別番号
	 *	0:指定なし		(空白)
	 *	1:品種			"0060"
	 *	2:品群			"0050"
	 *	3:販区			"0040"
	 *	4:売区			"0030"
	 *	5:中間集計		"0025"
	 *	6:小業種		"0020"
	 *	7:大業種		"0010"
	 * </pre>
	 * @param	kaisoPatternKb	階層パターン区分
	 * @return	種別番号
	 */
	private String convSyubetuNo(String kaisoPatternKb) {

		//品種
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.HINSYU.getCode())) {
			return mst000101_ConstDictionary.BUNRUI3;
		}
		//品群
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.HINGUN.getCode())) {
			return mst000101_ConstDictionary.BUNRUI4;
		}
		//販区
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.HANKU.getCode())) {
			return mst000101_ConstDictionary.BUNRUI3;
		}
		//売区
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.URIKU.getCode())) {
			return mst000101_ConstDictionary.BUNRUI2;
		}
		//中間集計
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.TYUKANSYUKEI.getCode())) {
			return mst000101_ConstDictionary.MIDDLE_TOTAL;
		}
		//小業種
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.SYOGYOSYU.getCode())) {
			return mst000101_ConstDictionary.BUNRUI1;
		}
		//大業種
		if (kaisoPatternKb.equals(mst008801_SyohinTaikeiDictionary.DAIGYOSYU.getCode())) {
			return mst000101_ConstDictionary.LARGE_TYPE_OF_BUSINESS;
		}
		return "";
	}//convKaisoPatternKb
}