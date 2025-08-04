/*
 * 作成日: 2006/10/22
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package mdware.master.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.bean.mst000101_DbmsBean;
import mdware.master.common.bean.mst001401_CheckDegitBean;
//↑↑2007.04.27 H.Yamamoto カスタマイズ修正↑↑


/**
 * @author totyu
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class MstCommonUtil
{

	public static String convertPluKbFromSyohinCd(String syohinCd)
	{
		String strWkPluCd = syohinCd;
		String strWkPluKbn = null;
		BigDecimal bdcWkPluCd = new BigDecimal(strWkPluCd);

		String str2 = strWkPluCd.trim().substring(0,2);
		String str5 = strWkPluCd.trim().substring(0,5);
		String str57 = strWkPluCd.trim().substring(5,7);
		String str7 = strWkPluCd.trim().substring(0,7);
		int strLength = strWkPluCd.trim().length();

		 //商品コードが0400000000001～0400999999999の場合
		if ("0400".equals(strWkPluCd.trim().substring(0,4))){
			strWkPluKbn = "4";
		//商品コードが3桁以下の場合
		}else if("0000000000".equals(strWkPluCd.trim().substring(0,10))){
			strWkPluKbn = "3";
		//商品コード頭2桁"49"の場合(13桁、8桁)
		}else if ("49".equals(strWkPluCd.trim().substring(0,2)) || "0000049".equals(strWkPluCd.trim().substring(0,7)) ){
			strWkPluKbn = "1";
		//商品コードが0020000000001～0020000099999の場合
		}else if (bdcWkPluCd.compareTo(new BigDecimal("0020000000001")) >= 0  && bdcWkPluCd.compareTo(new BigDecimal("0020000099999")) <= 0){
			strWkPluKbn = "6";
		//商品コードが2000001000000～2009999000000の場合
		}else if (bdcWkPluCd.compareTo(new BigDecimal("2000001000000")) >= 0  && bdcWkPluCd.compareTo(new BigDecimal("2009999000000")) <= 0){
			strWkPluKbn = "6";
		}
		// 13桁コード・8桁コードの上2桁が｢49｣でも｢00｣～｢09｣でもない時
		else if (
				// ここ来たときも13桁だが、もともと8桁の場合
				   (( "00000".equals(str5) && !"49".equals(str57) )
				&&	( "00000".equals(str5) && !"00".equals(str57) )
				&&	( "00000".equals(str5) && !"01".equals(str57) )
				&&	( "00000".equals(str5) && !"02".equals(str57) )
				&&	( "00000".equals(str5) && !"03".equals(str57) )
				&&	( "00000".equals(str5) && !"04".equals(str57) )
				&&	( "00000".equals(str5) && !"05".equals(str57) )
				&&	( "00000".equals(str5) && !"06".equals(str57) )
				&&	( "00000".equals(str5) && !"07".equals(str57) )
				&&	( "00000".equals(str5) && !"08".equals(str57) )
				&&	( "00000".equals(str5) && !"09".equals(str57) ))
				// もともと13桁
				||
//				↓↓2006.10.23 H.Yamamoto カスタマイズ修正↓↓
//				   (( !"00000".equals(str5) && !"49".equals(str2) )
//				&&	( !"00000".equals(str5) && !"00".equals(str2) )
//				&&	( !"00000".equals(str5) && !"01".equals(str2) )
//				&&	( !"00000".equals(str5) && !"02".equals(str2) )
//				&&	( !"00000".equals(str5) && !"03".equals(str2) )
//				&&	( !"00000".equals(str5) && !"04".equals(str2) )
//				&&	( !"00000".equals(str5) && !"05".equals(str2) )
//				&&	( !"00000".equals(str5) && !"06".equals(str2) )
//				&&	( !"00000".equals(str5) && !"07".equals(str2) )
//				&&	( !"00000".equals(str5) && !"08".equals(str2) )
//				&&	( !"00000".equals(str5) && !"09".equals(str2) ))
					( !"00000".equals(str5) )
//				↑↑2006.10.23 H.Yamamoto カスタマイズ修正↑↑
//					( strLength == 13 && !"49".equals(str2) )
//				&&	( strLength == 13 && !"00".equals(str2) )
//				&&	( strLength == 13 && !"01".equals(str2) )
//				&&	( strLength == 13 && !"02".equals(str2) )
//				&&	( strLength == 13 && !"03".equals(str2) )
//				&&	( strLength == 13 && !"04".equals(str2) )
//				&&	( strLength == 13 && !"05".equals(str2) )
//				&&	( strLength == 13 && !"06".equals(str2) )
//				&&	( strLength == 13 && !"07".equals(str2) )
//				&&	( strLength == 13 && !"08".equals(str2) )
//				&&	( strLength == 13 && !"09".equals(str2) )
//				// 8桁→13桁へ変換した場合
//				&&	( strLength == 13 && !"0000049".equals(str7) )
//				&&	( strLength == 13 && !"0000000".equals(str7) )
//				&&	( strLength == 13 && !"0000001".equals(str7) )
//				&&	( strLength == 13 && !"0000002".equals(str7) )
//				&&	( strLength == 13 && !"0000003".equals(str7) )
//				&&	( strLength == 13 && !"0000004".equals(str7) )
//				&&	( strLength == 13 && !"0000005".equals(str7) )
//				&&	( strLength == 13 && !"0000006".equals(str7) )
//				&&	( strLength == 13 && !"0000007".equals(str7) )
//				&&	( strLength == 13 && !"0000008".equals(str7) )
//				&&	( strLength == 13 && !"0000009".equals(str7) )
//				// 8桁の場合
//				&&	( strLength == 8 && !"49".equals(str2) )
//				&&	( strLength == 8 && !"00".equals(str2) )
//				&&	( strLength == 8 && !"01".equals(str2) )
//				&&	( strLength == 8 && !"02".equals(str2) )
//				&&	( strLength == 8 && !"03".equals(str2) )
//				&&	( strLength == 8 && !"04".equals(str2) )
//				&&	( strLength == 8 && !"05".equals(str2) )
//				&&	( strLength == 8 && !"06".equals(str2) )
//				&&	( strLength == 8 && !"07".equals(str2) )
//				&&	( strLength == 8 && !"08".equals(str2) )
//				&&	( strLength == 8 && !"09".equals(str2) )
			)
		{
			strWkPluKbn = "2";
		}
//		//商品コードが13桁の場合
//		else if (!"00000".equals(strWkPluCd.trim().substring(0,5))){
//			strWkPluKbn = "2";
//		//商品コード頭2桁"49","00"～"09"以外の場合(8桁)
//		}else if (!"0000049".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000000".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000001".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000002".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000003".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000004".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000005".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000006".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000007".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000008".equals(strWkPluCd.trim().substring(0,7)) &&
//					!"0000009".equals(strWkPluCd.trim().substring(0,7))){
//			strWkPluKbn = "2";
//		}
		else {
			strWkPluKbn = "3";
		}

		return strWkPluKbn;

	}

	/**
	 * 複数ユーザ名取得関数
	 * @param String[] userID[]
	 * @return	Map userNames
	 */
	public static Map getUserNames(String[] userID) throws SQLException {

		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用

		HashMap userNames = new HashMap();
		StringBuffer sb = new StringBuffer();
//		sb.append(" SELECT RIYO_USER_ID, RIYO_USER_NA FROM R_RIYO_USER ");
		sb.append(" SELECT USER_ID AS RIYO_USER_ID, USER_NA AS RIYO_USER_NA FROM R_PORTAL_USER ");
		sb.append("  WHERE ");
		for(int i=0; i<userID.length; i++) {
			if(i == 0) {
//				sb.append(" RIYO_USER_ID = '").append(userID[i]).append("'");
				sb.append(" USER_ID = '").append( userID[i].trim() ).append("'");
			} else {
//				sb.append(" OR RIYO_USER_ID = '").append(userID[i]).append("'");
				sb.append(" OR USER_ID = '").append( userID[i].trim() ).append("'");
			}
		}

		try {
			ResultSet rset = db.executeQuery( sb.toString() );    //抽出結果(ResultSet)
			while(rset.next()) {
				String tmpId = cns(rset.getString("RIYO_USER_ID").trim());
				String tmpNa = cns(HTMLStringUtil.convert(rset.getString("RIYO_USER_NA").trim()));
				userNames.put(tmpId, tmpNa);
			}
		} catch(SQLException sqle) {
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return userNames;
	}

	/**
	 * 複数NameCTF名取得関数
	 * @param Map[] code
	 * @return	Map ctfNames
	 */
	public static Map getCtfNames(NameCtfStructure[] code) throws SQLException {
		String userLocal = ResorceUtil.getInstance().getPropertie("USER_LOCAL");
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用

		HashMap ctfNames = new HashMap();

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SYUBETU_NO_CD ");
		sb.append(" 		, CODE_CD ");
		sb.append(" 		, KANJI_NA ");
		sb.append("   FROM R_NAMECTF ");
		sb.append("  WHERE ");
		for(int i=0; i<code.length; i++) {
			if(i == 0) {
				sb.append(" (	SYUBETU_NO_CD = '").append(MessageUtil.getMessage(code[i].getSyubetu(), userLocal)).append("'");
				sb.append(" AND	CODE_CD = '").append(code[i].getCode()).append("' ) ");
			} else {
				sb.append(" OR (	SYUBETU_NO_CD = '").append(MessageUtil.getMessage(code[i].getSyubetu(), userLocal)).append("'");
				sb.append(" 	AND	CODE_CD = '").append(code[i].getCode()).append("' ) ");
			}
		}

		try {
			ResultSet rset = db.executeQuery( sb.toString() );    //抽出結果(ResultSet)
			while(rset.next()) {
				for(int i=0; i<code.length; i++) {
					if(code[i].getSyubetu().equals(cns(rset.getString("SYUBETU_NO_CD")))
					&& code[i].getCode().equals(cns(rset.getString("CODE_CD")))) {
						NameCtfStructure tmpKey = code[i];
						String tmpVal = cns(HTMLStringUtil.convert(rset.getString("KANJI_NA").trim()));
						ctfNames.put(tmpKey, tmpVal);
						break;
					}
				}
			}
		} catch(SQLException sqle) {
			throw sqle;
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return ctfNames;
	}

	/**
	* NULLを空文字列に変換する関数（Base）
	*@return NULLの場合空文字列
	*         NULL以外の場合そのまま戻す
	*/
	public static String cns(String value){
	  if(value == null)
		return "";
	  else
		return value;
	}

//	↓↓2007.04.27 H.Yamamoto カスタマイズ修正↓↓
	public static String convertHachuSyohinCdFromSyohinCd(String syohinCd)
	{

		if (syohinCd == null || syohinCd.trim().length() != 13) {
			return null;
		}

		String strWkHachuSyohinCd = syohinCd.trim();
		String strWkSyohinCdSyubetsu = null;
		BigDecimal bdcWkHachuSyohinCd = new BigDecimal(strWkHachuSyohinCd);

		//商品コード頭4桁"0400"の場合
		if ("0400".equals(strWkHachuSyohinCd.substring(0,4))){
			strWkSyohinCdSyubetsu = "1";
		//商品コードが3桁以下の場合
		}else if("0000000000".equals(strWkHachuSyohinCd.substring(0,10))){
			strWkSyohinCdSyubetsu = "2";
		//商品コードが6桁以下の場合
		}else if("0000000".equals(strWkHachuSyohinCd.substring(0,7))){
			strWkSyohinCdSyubetsu = "3";
		//商品コード頭2桁"49","45"の場合(13桁)
		}else if ("49".equals(strWkHachuSyohinCd.substring(0,2)) || "45".equals(strWkHachuSyohinCd.substring(0,2)) ){
			strWkSyohinCdSyubetsu = "4";
		//商品コード頭2桁"49","45"の場合(8桁)
		}else if ("0000049".equals(strWkHachuSyohinCd.substring(0,7)) || "0000045".equals(strWkHachuSyohinCd.substring(0,7)) ){
			strWkSyohinCdSyubetsu = "5";
		//商品コード頭1桁"3"以上の場合(13桁)
		}else if (Integer.parseInt(strWkHachuSyohinCd.substring(0,1)) >= 3 ){
			strWkSyohinCdSyubetsu = "6";
		//商品コード頭1桁"3"以上の場合(8桁)
		}else if ("00000".equals(strWkHachuSyohinCd.substring(0,5)) && Integer.parseInt(strWkHachuSyohinCd.substring(5,6)) >= 3 ){
			strWkSyohinCdSyubetsu = "7";
		//商品コード頭1桁"00","01","03","06","07","08","09"の場合
		}else if ("00".equals(strWkHachuSyohinCd.substring(0,2)) || "01".equals(strWkHachuSyohinCd.substring(0,2)) ||
					"03".equals(strWkHachuSyohinCd.substring(0,2)) || "06".equals(strWkHachuSyohinCd.substring(0,2)) ||
					"07".equals(strWkHachuSyohinCd.substring(0,2)) || "08".equals(strWkHachuSyohinCd.substring(0,2)) ||
					"09".equals(strWkHachuSyohinCd.substring(0,2)) ){
			strWkSyohinCdSyubetsu = "8";
		} else {
			strWkSyohinCdSyubetsu = "9";
		}

		if (strWkSyohinCdSyubetsu.equals("8")) {
			strWkHachuSyohinCd = syohinCd.trim().substring(1,13);
			strWkHachuSyohinCd = strWkHachuSyohinCd + mst001401_CheckDegitBean.getModulus10( strWkHachuSyohinCd ,12 );
		}

		return strWkHachuSyohinCd;
	}
//	↑↑2007.04.27 H.Yamamoto カスタマイズ修正↑↑

	public static void main(String[] args)
	{
		String a[] = {"",""};
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0020000000005") + "=6");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0020000099995") + "=6");

		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("2000001000005") + "=6");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("2009999000000") + "=6");

		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0020000100005") + "=3");

		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("4520000100005") + "=2");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("4920000100005") + "=1");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0120000100005") + "=3");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0920000100005") + "=3");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("1120000100005") + "=2");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("5920000100005") + "=2");

		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000049012345") + "=1");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000009012345") + "=3");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000001012345") + "=3");

		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000045012345") + "=2");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000019012345") + "=2");
		System.out.println(MstCommonUtil.convertPluKbFromSyohinCd("0000081012345") + "=2");
	}

}
