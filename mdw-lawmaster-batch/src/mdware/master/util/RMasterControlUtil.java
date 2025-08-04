package mdware.master.util;

import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.util.bean.RMasterControlBean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000611_SystemKbDictionary;
import mdware.master.common.dictionary.mst009301_TaikeiKirikaeJyotaiDictionary;

/**
 * <p>タイトル: マスタ制御情報</p>
 * <p>説明: マスタ制御情報ユーティリティクラス</p>
 * <p>著作権: Copyright (c) 2007</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0
 */

public class RMasterControlUtil {

	// マスタ制御情報のキャッシュ
	static  RMasterControlBean mstCtrlBean = new RMasterControlBean();

	/**
	 * マスタ制御情報を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return RMasterControlBean マスタ制御情報
	 */
	public static RMasterControlBean getMstCtrlBean()
	{
//		if(RMasterControlUtil.mstCtrlBean.getUpdateTs() == null){
		if(RMasterControlUtil.mstCtrlBean.getTaikeiKirikaeKijunDtF() == null){
			RMasterControlUtil.getMstCtrlBeanFromDataBase(); 
		}
		return RMasterControlUtil.mstCtrlBean;

	}

	public  static void getMstCtrlBeanFromDataBase(){
// システムコントロールの切替日を参照するように修正
//		DataHolder dh = new DataHolder();
//		RMasterControlBean mstCtrlBean = RMasterControlBean.getRMasterControlBean(dh);
		String kirikaeDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.TAIKEI_KIRIKAE_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
		RMasterControlBean mstCtrlBean = new RMasterControlBean();
		mstCtrlBean.setTaikeiKirikaeKijunDtF(kirikaeDt);
		mstCtrlBean.setTaikeiKirikaeKijunDtG(kirikaeDt);
		mstCtrlBean.setTaikeiKirikaeKijunDtJ(kirikaeDt);
		mstCtrlBean.setTaikeiKirikaeKijunDtT(kirikaeDt);
		
		synchronized(RMasterControlUtil.mstCtrlBean){		
			RMasterControlUtil.mstCtrlBean = mstCtrlBean;
		}
	}

	public  static void getMstCtrlBeanFromDataBase( RMasterControlBean mstCtrlBean ){
		if( mstCtrlBean == null ) {
			getMstCtrlBeanFromDataBase();
		} else {
			synchronized( RMasterControlUtil.mstCtrlBean ) {		
				RMasterControlUtil.mstCtrlBean= mstCtrlBean;
			}
		}
	}

	/**
	 * 商品体系切替基準日を取得する
	 * 取得できないときはNULLを返す。
	 * @return String 商品体系切替基準日
	 */
	public static String getTaikeiKirikaeDt(String systemKb)
	{
		if(systemKb == null) {
			return null;
		}
		RMasterControlBean mstCtrlBean = getMstCtrlBean();
		if(mstCtrlBean == null) {
			return null;
		}
		if(systemKb.equals(mst000611_SystemKbDictionary.T.getCode())){
			return mstCtrlBean.getTaikeiKirikaeKijunDtT();
		} else if(systemKb.equals(mst000611_SystemKbDictionary.J.getCode())){
			return mstCtrlBean.getTaikeiKirikaeKijunDtJ();
		} else if(systemKb.equals(mst000611_SystemKbDictionary.G.getCode())){
			return mstCtrlBean.getTaikeiKirikaeKijunDtG();
		} else if(systemKb.equals(mst000611_SystemKbDictionary.F.getCode())){
			return mstCtrlBean.getTaikeiKirikaeKijunDtF();
		} else {
			return null;
		}
	}

	/**
	 * 商品体系切替状態を判定する
	 * 判定できないときはNULLを返す。
	 * @return String 商品体系切替状態（1:切替済、2:切替前、3:切替後）
	 */
	public static String getTaikeiKirikaeJyotai(String systemKb, String mstDt, String yukoDt)
	{
		if(mstDt == null || mstDt.trim().length() != 8) {
			return null;
		}
		if(yukoDt == null || yukoDt.trim().length() != 8) {
			return null;
		}
		String kirikaeDt = getTaikeiKirikaeDt(systemKb);
		if(kirikaeDt == null || kirikaeDt.trim().length() != 8) {
			return null;
		}

		// マスタ日付≧切替基準日の場合は「切替済」
		if(Long.parseLong(mstDt) >= Long.parseLong(kirikaeDt)) {
			return mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEZUMI.getCode();
		} else if(Long.parseLong(yukoDt) >= Long.parseLong(kirikaeDt)) {
			// 有効日≧切替基準日の場合は「切替後」
			return mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEATO.getCode();
		} else {
			return mst009301_TaikeiKirikaeJyotaiDictionary.KIRIKAEMAE.getCode();
		}
	}

}