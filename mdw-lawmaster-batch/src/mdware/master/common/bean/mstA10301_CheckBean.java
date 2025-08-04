package mdware.master.common.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.SessionManager;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（POSー管理）POS売価変更指示（基準売価）検索の画面表示データチェック用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するPOS売価変更指示（基準売価）検索の画面表示データチェック用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.01 2014/09/15 ha.ntt 内結障害NO.001対応
 */
public class mstA10301_CheckBean{
	/**
	 *
	 * 権限チェック・KeepBeenへのセット・エラーチェックを行う。
	 * @param 		mstA10201_KeepBean 			Keepb
	 * @param 		DataHolder 					dataHolder
	 * @param		String						kengenCd		: 権限コード
 	 * @param		String 						GamenId 		: 画面ID
	 * @param		String 						FirstFocusCtl  	: 初期Focusｺﾝﾄﾛｰﾙ
	 * @param		String[] 					CtrlName 		: コントロール名
	 * @param		Map 						CtrlColor 		: コントロールカラー
	 * @param		SessionManager 				sessionManager 	: セションマネージャー
	 */
	public void Check(mst000101_DbmsBean db, mstA10201_KeepBean Keepb, DataHolder dataHolder, String kengenCd
						, String GamenId, String FirstFocusCtl, String[] CtrlName, Map CtrlColor, SessionManager sessionManager)
				throws SQLException,Exception {

		//メッセージ取得
		TreeMap map = new TreeMap(mst000401_LogicBean.getMsg());

		Keepb.setErrorFlg(mst000101_ConstDictionary.ERR_CHK_NORMAL);
		Keepb.setErrorMessage("");


		if(Keepb.getErrorFlg().equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)){

			//画面内容を保存
			Keepb.setTenpoCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenpoCd")).trim());
			Keepb.setTenpo_nm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenpo_na")).trim());
			Keepb.setUrikuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_urikuCd")).trim());
			Keepb.setUriku_nm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("uriku__na")).trim());
			Keepb.setTenhankuCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenhanku_cd")).trim());
			Keepb.setTenhanku_nm(mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenhanku_na")).trim());
			Keepb.setSakuseiDt(mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_sakuseiDt")).trim());
			Keepb.setPosCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("jan_cd")).trim());
			Keepb.setPosfulHinbanCd(mst000401_LogicBean.chkNullString(dataHolder.getParameter("posful_hinban_cd")).trim());


			Map param = new HashMap();		//抽出条件格納用
			ResultSet rset = null;			//抽出結果(ResultSet)

			//初期Focus
			Keepb.setFirstFocus(FirstFocusCtl);

			//エラーチェック
			boolean chkb = false;
			List lst = new ArrayList();	//マスタ存在チェック抽出条件
			String name = "";				//戻り値格納
			mst000701_MasterInfoBean mstchk = new mst000701_MasterInfoBean();
			String strCtlName = "";


			//検索処理チェック----------------------------------------------

			//店舗コード存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_tenpoCd")).equals("")){
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();
				strCtlName = "tx_tenpo_cd";
				lst.clear();
				lst.add( " TENPO_CD = '" + Keepb.getTenpoCd() + "' " );
				mstchk = mst000401_LogicBean.getMasterCdName(db, "R_TENPO", "KANJI_RN", lst, "0", "");
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					Keepb.setTenpo_nm("");
				} else {
					Keepb.setTenpo_nm(mstchk.getCdName());
				}
			}


			//売区コード存在チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("ct_urikuCd")).equals("")){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");

				StringBuffer buffSql = new StringBuffer();
									  // 売区名称取得
				buffSql.append("SELECT ");
				buffSql.append("	KANJI_RN ");
				buffSql.append("  FROM ( ");
				buffSql.append("		SELECT MAX(CTF.KANJI_RN) AS KANJI_RN ");
				buffSql.append("		  FROM R_NAMECTF CTF ");
				buffSql.append("		 INNER JOIN R_SYOHIN_TAIKEI TAIKEI ");
				buffSql.append("		    ON CTF.CODE_CD = TAIKEI.URIKU_CD ");
				buffSql.append("		 WHERE SYUBETU_NO_CD = '" + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI2, userLocal) + "' ");
				buffSql.append("		   AND DELETE_FG = '0' ");
				buffSql.append("		   AND CTF.CODE_CD = '" + Keepb.getUrikuCd() + "' ");
				buffSql.append("         GROUP BY ");
				buffSql.append("        	 CTF.CODE_CD ");
				buffSql.append("         ORDER BY ");
				buffSql.append("         	 CODE_CD ");
				buffSql.append("       ) ");
				rset = db.executeQuery(buffSql.toString());

				if(rset.next()){
					Keepb.setUriku_nm(rset.getString("KANJI_RN"));
				}else{
				Keepb.setUriku_nm("");
				}
				if(rset != null){
					rset.close();
				}
			}

			// ↓↓　2006/04/19 kim START
			// 店販区チェック
			if (!mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenhanku_cd")).equals("")){
				String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
				lst = new ArrayList();
				mstchk = new mst000701_MasterInfoBean();

				lst.add( " SYUBETU_NO_CD = " + MessageUtil.getMessage(mst000101_ConstDictionary.BUNRUI3, userLocal) );
				lst.add( " and "+ " CODE_CD = '" + mst000401_LogicBean.chkNullString(dataHolder.getParameter("tenhanku_cd")) + "' ");

				mstchk = mst000401_LogicBean.getMasterCdName(db,"R_NAMECTF","KANJI_RN", lst, "0", "" );
				if(!mstchk.getExistenceFlg().equals(mst000101_ConstDictionary.FUNCTION_TRUE)){
					Keepb.setTenhanku_nm("");
				} else {
					Keepb.setTenhanku_nm(mstchk.getCdName());
				}
			}
			//	↑↑　2006/04/19 kim END
		}


		Keepb.setCtrlColor(CtrlColor);
	}
}
