package mdware.batch.process.b99;

import java.util.*;
import java.sql.*;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.batch.log.BatchLog;
import mdware.common.code.converter.ConvertUpcEtoUpcA;
import mdware.master.common.bean.mst001401_CheckDegitBean;

/**
 * <p>タイトル: B99-99-9 チェックデジットチェック</p>
 * <p>説明: 移行商品マスタ全件のチェックデジットが正しいかチェックする</p>
 * <p>著作権: Copyright (c) 2013</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.00 2013/06/04 新規作成　M.Ayukawa　ランドローム様対応
 */
public class B999999CheckDigitCheck {
	private DataBase dataBase = null;
	private BatchLog batchLog = BatchLog.getInstance();
	private boolean closeDb = false;

	private static final String BATCH_UID = "B99-99-99";
	private static final String BATCH_NA = "チェックデジットチェック処理";

	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public B999999CheckDigitCheck(DataBase dataBase) {
		this.dataBase = dataBase;
		if (dataBase == null) {
			this.dataBase = new DataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public B999999CheckDigitCheck() {
		this(new DataBase("rbsite_ora"));
		closeDb = true;
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		try {
			
			
			StringBuffer sql  = new StringBuffer("");
			sql.append("SELECT ");
			sql.append("     LPAD(TRIM(SYOHIN_CD),13,'0') SYOHIN_CD ");
			sql.append("FROM ");
			sql.append("    WK_IKO_SYOHIN RS ");
			
		    ResultSet rs = dataBase.executeQuery(sql.toString());
		    
		    int normalCount = 0;
		    int errorCount = 0;
		    
		    while (rs.next()) {
		    	
		    	normalCount++;
		    	
				// 商品コードのチェックデジットのチェック
		    	StringBuffer sbChkDeg = new StringBuffer();
		    	String syohinCd = rs.getString("SYOHIN_CD");	    	
		    	
				if(!DegitCheck(rs.getString("SYOHIN_CD"), sbChkDeg)){
					 batchLog.getLog().info(syohinCd + "\t" + syohinCd.substring(0,12) + sbChkDeg.toString() );
					errorCount++;
				}					
		    	
		    }

		    batchLog.getLog().info("エラー件数" + errorCount + "件\r\n");
		    batchLog.getLog().info("全体処理件数" + normalCount + "件\r\n");
		    
			dataBase.commit();
			
			if (closeDb) {
				this.dataBase.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataBase.rollback();
			if (closeDb) {
				if (dataBase != null) {
					dataBase.close();
				}
			}
			throw e;
		}
	}
	
	/**
	 * チェックデジットのチェックを行う
	 */	
	private boolean DegitCheck(String syohinCd, StringBuffer sbChkDeg) throws Exception {
		
		boolean resultFg = true;
		
		boolean syohinCheckFg = false;
		if (syohinCd.length() == 13){
			
			
// 2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(S)
			//  ・自動採番コードは 20,04,02,23 ⇒ 23,02
			//  ・生鮮EDI追加　前4桁ゼロ
			//  ・02の計量器もチェックデジットを行う
			//  ・キーPLUは頭9桁 or 8桁がゼロ
			//  ・UPC-Eの判定は前6桁
			//  ・UPC-Eのチェックデジットチェック必要
			if("23".equals(syohinCd.substring(0,2)) ){
				// 自動採番
				syohinCheckFg = true;
			} else if("02".equals(syohinCd.substring(0,2))  ){
//				// 末尾が"0"でなければエラー
//				if(!"0".equals(syohinCd.substring(12,13))){
//					return false;
//				}
				// 自動採番する
				syohinCheckFg = true;
			} else if("000000000".equals(syohinCd.substring(0,9)) || "00000000".equals(syohinCd.substring(0,8))){
				// キーPLU
				syohinCheckFg = true;
			} else if("000000".equals(syohinCd.substring(0,6))){
				// UPC-E				
				// UPC-Eチェックデジットチェック			    
				String upcECd = "0" + syohinCd.substring(0,12); 		// 末尾を除き、先頭に0を付与（チェックデジットなし7桁セット）
				String upcACd = ConvertUpcEtoUpcA.convert(upcECd);		// UPC-Aに変換
				if(  upcACd == null || !syohinCd.substring(12,13).equals(  upcACd.substring(12,13) )){
					resultFg = false;
				}
				// 通常のチェックデジットチェックはしない
				syohinCheckFg = false;
				
			} else if("45".equals(syohinCd.substring(0,2)) || "49".equals(syohinCd.substring(0,2))){
				// 49,45フラグ（13桁）
				syohinCheckFg = true;
			} else if("0000045".equals(syohinCd.substring(0,7)) || "0000049".equals(syohinCd.substring(0,7))){
				// 49,45フラグ（8桁）
				syohinCheckFg = true;
			} else if(Integer.parseInt(syohinCd.substring(0,1)) >= 3){
				// EAN（13桁）
				syohinCheckFg = true;
			} else if("00000".equals(syohinCd.substring(0,5)) && Integer.parseInt(syohinCd.substring(5,6)) >= 3){
				// EAN（8桁）
				syohinCheckFg = true;
			} else if("00".equals(syohinCd.substring(0,2)) || "01".equals(syohinCd.substring(0,2)) || "03".equals(syohinCd.substring(0,2)) ||
						"06".equals(syohinCd.substring(0,2)) || "07".equals(syohinCd.substring(0,2)) || "08".equals(syohinCd.substring(0,2)) ||
						"09".equals(syohinCd.substring(0,2))){
				// UPC-A
				syohinCheckFg = true;
			} else if("0000".equals(syohinCd.substring(0,4))){
				// 生鮮EDI
				syohinCheckFg = true;				
			} else{
				// その他
				syohinCheckFg = false;
			}
			
//			if("20".equals(syohinCd.substring(0,2)) || "04".equals(syohinCd.substring(0,2)) ){
//				// 自動採番
//				syohinCheckFg = true;
//			} else if("02".equals(syohinCd.substring(0,2)) || "23".equals(syohinCd.substring(0,2)) ){
//				// 末尾が"0"でなければエラー
//				if(!"0".equals(syohinCd.substring(12,13))){
//					return false;
//				}
//
//				// 自動採番はオフ
//				syohinCheckFg = false;
//			} else if("000000000".equals(syohinCd.substring(0,9))){
//				// キーPLU
//				syohinCheckFg = true;
//			} else if("0000000".equals(syohinCd.substring(0,7))){
//				// UPC-E
//				syohinCheckFg = false;
//			} else if("45".equals(syohinCd.substring(0,2)) || "49".equals(syohinCd.substring(0,2))){
//				// 49,45フラグ（13桁）
//				syohinCheckFg = true;
//			} else if("0000045".equals(syohinCd.substring(0,7)) || "0000049".equals(syohinCd.substring(0,7))){
//				// 49,45フラグ（8桁）
//				syohinCheckFg = true;
//			} else if(Integer.parseInt(syohinCd.substring(0,1)) >= 3){
//				// EAN（13桁）
//				syohinCheckFg = true;
//			} else if("00000".equals(syohinCd.substring(0,5)) && Integer.parseInt(syohinCd.substring(5,6)) >= 3){
//				// EAN（8桁）
//				syohinCheckFg = true;
//			} else if("00".equals(syohinCd.substring(0,2)) || "01".equals(syohinCd.substring(0,2)) || "03".equals(syohinCd.substring(0,2)) ||
//						"06".equals(syohinCd.substring(0,2)) || "07".equals(syohinCd.substring(0,2)) || "08".equals(syohinCd.substring(0,2)) ||
//						"09".equals(syohinCd.substring(0,2))){
//				// UPC-A
//				syohinCheckFg = true;
//			} else{
//				// その他
//				syohinCheckFg = false;
//			}
			
// 2013.05.06 [MSTC00004] 商品コードチェック仕様変更対応(E)
			
		}
		if (syohinCheckFg){
			String chkDeg = mst001401_CheckDegitBean.getModulus10( syohinCd.substring(0,12) ,12 );
			if( chkDeg == null || !chkDeg.equals( syohinCd.substring(12,13) )){
				resultFg = false;
			}
			sbChkDeg.append(chkDeg);
		}
		
		return resultFg;
	}	
}