/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用<OPTION>タグ作成クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用<OPTION>タグ作成クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst008001_KeiryokiDictionary;
import jp.co.vinculumjapan.stc.util.bean.*;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.log.*;
import java.sql.*;
import java.util.*;
/**
 * <P>タイトル : 新ＭＤシステムで使用するシリウス担当用<OPTION>タグ作成クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するシリウス担当用<OPTION>タグ作成クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Murata
 * @version 1.0
 * @see なし								
 */
public class mst000301_SelectBean {
	private static final StcLog stcLog = StcLog.getInstance(); //STCLIBのStcLogインスタンス格納用

	private HashMap ctfmap = new HashMap();

	/**
	 * コンストラクタ<br>
	 * <br>
	 * mst000301_SelectBean() -&gt; <br>
	 */
	public mst000301_SelectBean() throws SQLException {
	}

	/**
	 * コンストラクタ<br>
	 * <br>
	 * mst000301_SelectBean(new String[]{"0001","0002"}) -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetuArgsがnullの場合は、名称ＣＴＦマスタの内容を全て配列に保持します。<br>
	 * 引数syubetuArgsがnot nullの場合は、指定された種別の名称ＣＴＦマスタを配列に保持します。<br>
	 * <br>
	 * @param syubetuArgs 	種別NO配列
	 */

	public mst000301_SelectBean(String hinsyu_cd) {
		
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();	//STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();	//mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);				//mst000301_SelectBean用名称CTFのBeanHolder
							
			Map map = new HashMap();	//mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用

			map.put("hinsyu_cd",hinsyu_cd);
			map.put("assort","true");
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));	//抽出結果(ResultSet)
			StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用
	
			String SaveSyubetuCdNo = new String("");
			ArrayList ctfList = null;
			while (rset.next()) {
				Map recMap = new HashMap();
				
				if (!SaveSyubetuCdNo.equals(rset.getString("syubetu_no_cd").trim())) {
					if (SaveSyubetuCdNo.length() > 0) {
						ctfmap.put(SaveSyubetuCdNo, ctfList);
					}
					SaveSyubetuCdNo = rset.getString("syubetu_no_cd").trim();
					ctfList = new ArrayList();
				}
				recMap.put("code_cd",mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
				recMap.put("kanji_rn",mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim());
				recMap.put("zokusei_cd",mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
				ctfList.add(recMap);
			}
			if (SaveSyubetuCdNo.length() > 0) {
				ctfmap.put(SaveSyubetuCdNo, ctfList);
			}

			
		} catch(SQLException sqle) {

			stcLog.getLog().fatal("mst000301_SelectBean mst000301_SelectBean : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean mst000301_SelectBean : " + sqle.toString());

		
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean mst000301_SelectBean : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean mst000301_SelectBean : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
	}
	
	/**
	 * コンストラクタ<br>
	 * <br>
	 * mst000301_SelectBean(new String[]{"0001","0002"}) -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetuArgsがnullの場合は、名称ＣＴＦマスタの内容を全て配列に保持します。<br>
	 * 引数syubetuArgsがnot nullの場合は、指定された種別の名称ＣＴＦマスタを配列に保持します。<br>
	 * <br>
	 * @param syubetuArgs 	種別NO配列
	 */
	public mst000301_SelectBean(String[] syubetuArgs) {
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();	//STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();	//mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);				//mst000301_SelectBean用名称CTFのBeanHolder
							
			Map map = new HashMap();	//mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			if (syubetuArgs != null) {
				StringBuffer syubetuNoCdIn = new StringBuffer();
				for (int i = 0;i < syubetuArgs.length;i++ ) {
					if (i != 0) {
						syubetuNoCdIn.append(",");
					}
					syubetuNoCdIn.append(syubetuArgs[i]);
				}
				map.put("syubetu_no_cd_in",syubetuNoCdIn.toString());
			}
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));	//抽出結果(ResultSet)
			StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用
	
			String SaveSyubetuCdNo = new String("");
			ArrayList ctfList = null;
			while (rset.next()) {
				Map recMap = new HashMap();
				
				if (!SaveSyubetuCdNo.equals(rset.getString("syubetu_no_cd").trim())) {
					if (SaveSyubetuCdNo.length() > 0) {
						ctfmap.put(SaveSyubetuCdNo, ctfList);
					}
					SaveSyubetuCdNo = rset.getString("syubetu_no_cd").trim();
					ctfList = new ArrayList();
				}
				recMap.put("code_cd",mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
				recMap.put("kanji_rn",mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim());
				recMap.put("zokusei_cd",mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
				ctfList.add(recMap);
			}
			if (SaveSyubetuCdNo.length() > 0) {
				ctfmap.put(SaveSyubetuCdNo, ctfList);
			}

			
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean mst000301_SelectBean : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean mst000301_SelectBean : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean mst000301_SelectBean : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean mst000301_SelectBean : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
	}

	/**
	 * キャッシュに保持している内容で
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag1Cash( String syubetu_no_cd, String code_cd, String param1 ) 
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					code_cd.equals(recMap.get("code_cd").toString())) {
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
					stb.append("\" SELECTED>");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
						stb.append(":");
					}
					//サイズコード表示
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
						stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
					stb.append("</OPTION>\n");
					flg = true;
				} else {
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
						stb.append(":");
					}
					//サイズコード表示
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
						stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
					stb.append("</OPTION>\n");
				}
				cnt++;
			}
		}

		return stb.toString();

	}

	/**
	 * キャッシュに保持している内容で
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param systemuKB		システム区分
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag1Cash( String syubetu_no_cd, String systemKB, String code_cd, String param1 ) 
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if(((String)recMap.get("code_cd")).substring(0,1).equals(systemKB)) {
					if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
						code_cd.equals(recMap.get("code_cd").toString().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
					}
					cnt++;
				}	
			}
		}

		return stb.toString();

	}

//	↓↓2006.12.12 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * キャッシュに保持している内容で
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param systemuKB		システム区分
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 * @param nameLen			名称長さ
	 */
	public String subCtfOptTag1Cash( String syubetu_no_cd, String systemKB, String code_cd, String param1, int nameLen) 
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if(((String)recMap.get("code_cd")).substring(0,1).equals(systemKB)) {
					if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
						code_cd.equals(recMap.get("code_cd").toString().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						if(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString()).length() <= nameLen) {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						} else {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString()).substring(0,nameLen)));
						}
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						if(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString()).length() <= nameLen) {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						} else {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString()).substring(0,nameLen)));
						}
						stb.append("</OPTION>\n");
					}
					cnt++;
				}	
			}
		}

		return stb.toString();

	}
//	↑↑2006.12.12 H.Yamamoto カスタマイズ修正↑↑

	/**
	 * キャッシュに保持している内容で
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag2Cash( String syubetu_no_cd, String code_cd, String param1 ) 
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if ( cnt == 0 ) {
					stb.append("<OPTION VALUE='' > </OPTION>\n");
				}
				if(!flg && code_cd.equals(recMap.get("code_cd").toString())){
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
					stb.append("\" SELECTED>");

					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
						stb.append(":");
					}
					//サイズコード表示
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
						stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
					stb.append("</OPTION>\n");
					flg = true;
					
				} else {
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
						stb.append(":");
					}
					//サイズコード表示
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
						stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
					stb.append("</OPTION>\n");
				}
				cnt++;
			}
		}

		return stb.toString();

	}

	/**
	 * キャッシュに保持している内容で
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param systemKB			システム区分
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag2Cash( String syubetu_no_cd, String systemKB, String code_cd, String param1 ) 
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if(((String)recMap.get("code_cd")).substring(0,1).equals(systemKB)) {
					if ( cnt == 0 ) {
						stb.append("<OPTION VALUE='' > </OPTION>\n");
					}
					if(!flg && code_cd.equals(recMap.get("code_cd").toString().substring(1))){
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\" SELECTED>");

						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
						flg = true;
					
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString().substring(1)));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("code_cd").toString()));
							stb.append(":");
						}
						//サイズコード表示
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_2) && code_cd.length() == 6){
							stb.append(mst000401_LogicBean.chkNullString(((String)recMap.get("code_cd")).substring(4,6)));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
					}
					cnt++;
				}
			}
		}

		return stb.toString();

	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag3("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag3Cash( String syubetu_no_cd, String code_cd, String param1 ) 
	{

		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

//		stcLog.getLog().fatal( list);

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					  code_cd.equals(recMap.get("zokusei_cd").toString())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag18("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag18Cash( String syubetu_no_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用

		//種別に対応するリストを取得
		ArrayList list = (ArrayList)ctfmap.get(syubetu_no_cd);
		int cnt = 0;            	//処理回数
		boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

//		stcLog.getLog().fatal( list);

		if (list != null) {
			for (int i = 0;i < list.size(); i ++) {
				Map recMap = (Map)list.get(i);
				//長さ18以下のコードのみ処理
				if( mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()).trim().length() <= 18 ){
					if (!flg && (cnt == 0 && (code_cd == null || code_cd.trim().length() == 0)) || 
						code_cd.trim().equals(recMap.get("zokusei_cd").toString())) {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
							stb.append("\" SELECTED>");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
							stb.append("</OPTION>\n");
							flg = true;
					} else {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
							stb.append("\">");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(recMap.get("zokusei_cd").toString()));
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(recMap.get("kanji_rn").toString())));
							stb.append("</OPTION>\n");
					}
					cnt++;
				}
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag1( String syubetu_no_cd, String code_cd, String param1 ) 
    {
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();	//STCLIBのDBインスタンス格納用
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();	//mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);				//mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();	//mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));	//抽出結果(ResultSet)
			int cnt = 0;            	//処理回数
			boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					code_cd.equals(rset.getString("code_cd").trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}

						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));

						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag1 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag1 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag1 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag1 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag2("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag2( String syubetu_no_cd, String code_cd, String param1 )
    {
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag2 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag2 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag2 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTagKeiryoHanku("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param sgyosyu_cd 		選択された小業種コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTagKeiryoHanku( String syubetu_no_cd, String code_cd, String sgyosyu_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用

		try {
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)
   
			stb.append("SELECT * ");
			stb.append("  FROM R_NAMECTF ");
			stb.append(" WHERE SYUBETU_NO_CD = '" + syubetu_no_cd + "'");
			stb.append("   AND INSTR(ZOKUSEI_CD, '" + sgyosyu_cd + "', 11) >= 11 ");
			ResultSet rset = db.executeQuery(stb.toString());    //抽出結果(ResultSet)

			stb.delete(0,stb.length());

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTagKeiryoHanku : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTagKeiryoHanku : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTagKeiryoHanku : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTagKeiryoHanku : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグ(生鮮用)を作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTagFre("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTagFre( String syubetu_no_cd, String code_cd, String param1 )
	{
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).length() <= 18){
					if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
						stb.append("<OPTION VALUE=\"");
						stb.append("");
						stb.append("\" SELECTED>");
						stb.append("　");
						stb.append("</OPTION>\n");
						flg = true;
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					} else if (cnt == 0) {
						stb.append("<OPTION VALUE=\"");
						stb.append("");
						stb.append("\">");
						stb.append("　");
						stb.append("</OPTION>\n");
						if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append("\" SELECTED>");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
							stb.append("</OPTION>\n");
							flg = true;
						} else {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append("\">");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
							stb.append("</OPTION>\n");
						}
					} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append("\" SELECTED>");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
							stb.append("</OPTION>\n");
							flg = true;
					} else {
							stb.append("<OPTION VALUE=\"");
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append("\">");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
							stb.append("</OPTION>\n");
					}
					cnt++;
				}
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTagFre : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTagFre : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTagFre : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTagFre : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag3("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag3( String syubetu_no_cd, String code_cd, String param1 )
    {
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					code_cd.equals(rset.getString("zokusei_cd").trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag3 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag3 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag3 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag3 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag4("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag4( String syubetu_no_cd, String code_cd, String param1 )
    {
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("zokusei_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag4 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag4 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag4 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag4 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag5("001","001" "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag5( String syubetu_no_cd, String where, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd_aft_like",where);
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					code_cd.equals(rset.getString("code_cd").trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag5 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag5 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag5 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag5 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。Valueに属性コードを設定（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag6("001", "001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag6( String syubetu_no_cd, String where, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd_aft_like",where);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next() && !where.equals("")) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));//BUGNO-006 2005.04.20 T.Makuta START
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag6 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag6 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag6 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag6 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag8("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param systemKb 		システム区分
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag8( String syubetu_no_cd, String systemKb, String code_cd, String param1 ) 
    {
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance();	//STCLIBのDBインスタンス格納用
		StringBuffer stb = new StringBuffer();						//生成したOPTIONタグの格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();	//mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);				//mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();	//mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd_aft_like",systemKb);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));	//抽出結果(ResultSet)
			int cnt = 0;            	//処理回数
			boolean flg = false;		//指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
					code_cd.equals(rset.getString("code_cd").trim().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}

						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));

						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag8 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag8 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag8 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag8 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag9("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param systemKb 		システム区分
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag9( String syubetu_no_cd, String systemKb, String code_cd, String param1 )
    {
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd_aft_like",systemKb);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag9 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag9 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag9 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag9 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * RNameCtfのコード+名称を作成します。<br>
	 * <br>
	 * mst000301_SelectBean.subCtfVal("0001", "0000000001") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfVal( String syubetu_no_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成した文字列の格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd",code_cd);
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			if( syubetu_no_cd != null && syubetu_no_cd.trim().length() > 0 
				&&	code_cd != null && code_cd.trim().length() > 0 ){
					ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)

					int cnt = 0;                 //処理回数
					boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

					if (rset.next()) {
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					}					
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfVal : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfVal : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * R_KEIRYOKI_THEMEのSELECTタグのOPTIONタグを作成します。（空白行を作成しない）<br>
	 * <br>
	 * mst000301_SelectBean.subThemeOptTag1("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数s_gyosyu_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param s_gyosyu_cd		小業種コード
	 * @param code_cd			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subThemeOptTag1(String s_gyosyu_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {

			String keiryoHankuCd = "";                       //計量販区コード
			String sGyosyuCd	= "";                        //小業種コード
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();  //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                  //mst000301_SelectBean用名称CTFのBeanHolder

			//小業種コードの取得
			Map sgyosyuM = new HashMap();   //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			sgyosyuM.put("syubetu_no_cd",mst000101_ConstDictionary.BUNRUI1);
			sgyosyuM.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			ResultSet sgyosyuRset = db.executeQuery(ctfm.getSelectSql(sgyosyuM));    //抽出結果(ResultSet)
			if(sgyosyuRset.next()) {
				sGyosyuCd=sgyosyuRset.getString("code_cd").trim();
			}


			//計量器テーママスタの取得
			mst000301_ThemeDM rthemeDM = new mst000301_ThemeDM();  //mst000301_SelectBean用計量器テーママスタのDMモジュール
			BeanHolder rthemebh = new BeanHolder(rthemeDM);          //mst000301_SelectBean用計量器テーママスタのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用計量器テーママスタのDMモジュールに対する抽出条件格納用
			if(s_gyosyu_cd==null || s_gyosyu_cd.equals("")){
				map.put("s_gyosyu_cd",sGyosyuCd);
			} else {
				map.put("s_gyosyu_cd",s_gyosyu_cd);
			}
			map.put("themecd","");
			ResultSet rset = db.executeQuery(rthemeDM.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (!flg && (cnt == 0 && (code_cd == null || code_cd.length() == 0)) || 
				code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subThemeOptTag1 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subThemeOptTag1 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subThemeOptTag1 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subThemeOptTag1 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * R_KEIRYOKI_THEMEのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subThemeOptTag2("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数s_gyosyu_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param s_gyosyu_cd		小業種コード
	 * @param code_cd			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subThemeOptTag2(String s_gyosyu_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {

			String keiryoHankuCd = "";                       //計量販区コード
			String sGyosyuCd	= "";                        //小業種コード
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();  //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                  //mst000301_SelectBean用名称CTFのBeanHolder
			//計量器テーママスタの取得
			mst000301_ThemeDM rthemeDM = new mst000301_ThemeDM();  //mst000301_SelectBean用計量器テーママスタのDMモジュール
			BeanHolder rthemebh = new BeanHolder(rthemeDM);          //mst000301_SelectBean用計量器テーママスタのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用計量器テーママスタのDMモジュールに対する抽出条件格納用
				map.put("s_gyosyu_cd",s_gyosyu_cd);
				map.put("themecd","");
			ResultSet rset = db.executeQuery(rthemeDM.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(rset.getString("theme_cd").trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(rset.getString("theme_na").trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(rset.getString("theme_cd").trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
				stcLog.getLog().fatal("mst000301_SelectBean subThemeOptTag2 : SQL例外が発生しました。" );
				stcLog.getLog().fatal("mst000301_SelectBean subThemeOptTag2 : " + sqle.toString());
		} catch(Exception e) {
				stcLog.getLog().error("mst000301_SelectBean subThemeOptTag2 : 例外が発生しました。" );
				stcLog.getLog().error("mst000301_SelectBean subThemeOptTag2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * R_KEIRYOKI_THEMEのコード+名称を作成します。<br>
	 * <br>
	 * mst000301_SelectBean.subThemeVal("0001", "0000000001") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数s_gyosyu_cdがnullの場合は、長さ0の文字列になります。<br>
	 * <br>
	 * @param s_gyosyu_cd		小業種コード
	 * @param code_cd			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subThemeVal(String s_gyosyu_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {

			String keiryoHankuCd = "";                       //計量販区コード
			String sGyosyuCd	= "";                        //小業種コード
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();  //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                  //mst000301_SelectBean用名称CTFのBeanHolder

			//小業種コードの取得
			Map sgyosyuM = new HashMap();   //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			sgyosyuM.put("syubetu_no_cd",mst000101_ConstDictionary.BUNRUI1);
			sgyosyuM.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			ResultSet sgyosyuRset = db.executeQuery(ctfm.getSelectSql(sgyosyuM));    //抽出結果(ResultSet)
			if(sgyosyuRset.next()) {
				sGyosyuCd=sgyosyuRset.getString("code_cd").trim();
			}


			//計量器テーママスタの取得
			mst000301_ThemeDM rthemeDM = new mst000301_ThemeDM();  //mst000301_SelectBean用計量器テーママスタのDMモジュール
			BeanHolder rthemebh = new BeanHolder(rthemeDM);          //mst000301_SelectBean用計量器テーママスタのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用計量器テーママスタのDMモジュールに対する抽出条件格納用
			if(s_gyosyu_cd==null || s_gyosyu_cd.equals("")){
				map.put("s_gyosyu_cd",sGyosyuCd);
				map.put("theme_cd", code_cd);
			} else {
				map.put("s_gyosyu_cd",s_gyosyu_cd);
				map.put("theme_cd", code_cd);
			}
			ResultSet rset = db.executeQuery(rthemeDM.getSelectSql(map));    //抽出結果(ResultSet)

			if(rset.next()){
				if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("theme_cd")).trim());
					stb.append(":");
				}
				stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("theme_na")).trim()));
			}
		  } catch(SQLException sqle) {
			  stcLog.getLog().fatal("mst000301_SelectBean subThemeVal : SQL例外が発生しました。" );
			  stcLog.getLog().fatal("mst000301_SelectBean subThemeVal : " + sqle.toString());
		  } catch(Exception e) {
			  stcLog.getLog().error("mst000301_SelectBean subThemeVal : 例外が発生しました。" );
			  stcLog.getLog().error("mst000301_SelectBean subThemeVal : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * R_TenkabutuのSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subTenkabutuOptTag2() -&gt; OPTIONタグ<br>
	 * <br>
	 * <br>
	 */
	public String subTenkabutuOptTag2()
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {

			mst840101_TenkabutuDM tenkabutuDM = new mst840101_TenkabutuDM();	//mst840101_TenkabutuBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(tenkabutuDM);                  	//mst840101_TenkabutuBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用計量器テーママスタのDMモジュールに対する抽出条件格納用

			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());

			ResultSet rset = db.executeQuery(tenkabutuDM.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 ) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("tenkabutu_na").trim())));
					stb.append("\">");
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("tenkabutu_na").trim())));
					stb.append("</OPTION>\n");
				} else {
					stb.append("<OPTION VALUE=\"");
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("tenkabutu_na").trim())));
					stb.append("\">");
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("tenkabutu_na").trim())));
					stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subTenkabutuOptTag2 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subTenkabutuOptTag2 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subTenkabutuOptTag2 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subTenkabutuOptTag2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

//	↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓　T.Ueda add Start 2005.05.02 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）(正式名称出力タイプ)<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag7("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfOptTag7( String syubetu_no_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag7 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag7 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag7 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag7 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのSELECTタグのOPTIONタグを作成します。(計量器用)（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCtfOptTag7("0001", "0000000001","1","1") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 * @param type 			計量器タイプ
	 */
	public String subCtfOptTag7( String syubetu_no_cd, String code_cd, String param1, String type )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("code_cd_aft_like",type);	//計量販区コードで判断
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag7 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag7 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag7 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag7 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのコード+名称を作成します。(正式名称出力)<br>
	 * <br>
	 * mst000301_SelectBean.subCtfVal2("0001", "0000000001") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subCtfVal2( String syubetu_no_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                       //生成した文字列の格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd",code_cd);
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			if( syubetu_no_cd != null && syubetu_no_cd.trim().length() > 0 
				&&	code_cd != null && code_cd.trim().length() > 0 ){
					ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)

					int cnt = 0;                 //処理回数
					boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

					if (rset.next()) {
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
					}					
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal2 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal2 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfVal2 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfVal2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * RNameCtfのコード+名称を作成します。(計量器用)<br>
	 * <br>
	 * mst000301_SelectBean.subCtfVal2("0001", "0000000001","1","1") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 * @param type 			計量器タイプ
	 */
	public String subCtfVal2( String syubetu_no_cd, String code_cd, String param1, String type )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd",type + code_cd);
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			if( syubetu_no_cd != null && syubetu_no_cd.trim().length() > 0 
				&&	code_cd != null && code_cd.trim().length() > 0 ){
					ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)

					int cnt = 0;                 //処理回数
					boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

					if (rset.next()) {
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1));
							stb.append(":");
						}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						
					}					
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal2 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfVal2 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfVal2 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfVal2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * 計量器コメント区分用のSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subCommentOptTag("0001", "0000000001","1","1") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、空白行がSELECTEDになります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 * @param type 			計量器タイプ
	 */
	public String subCommentOptTag( String syubetu_no_cd, String code_cd, String param1, String type )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("code_cd_aft_like",type);	//計量販区コードで判断
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			String Code = "";
			while (rset.next()) {
				
				Code = mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1);

				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(Code.substring(0,2));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(Code.substring(0,2));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(Code.substring(0,2))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(Code.substring(0,2));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(Code.substring(0,2));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(Code.substring(0,2));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(Code.substring(0,2));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
					}
				} else if (!flg && code_cd.equals(Code.substring(0,2))) {
						
						if (Code.substring(2).equals("0")) {
							stb.append("</OPTION>\n");							
						}
						stb.append("<OPTION VALUE=\"");
						stb.append(Code.substring(0,2));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(Code.substring(0,2));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						flg = true;
				} else {
						if (Code.substring(2).equals("0")) {
							stb.append("</OPTION>\n");							
							stb.append("<OPTION VALUE=\"");
							stb.append(Code.substring(0,2));
							stb.append("\">");
							if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
								stb.append(Code.substring(0,2));
								stb.append(":");
							}
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						} else {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));							
						}
				}
				cnt++;
			}
			
			if (cnt != 0) {
				stb.append("</OPTION>\n");							
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCommentOptTag : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCommentOptTag : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCommentOptTag : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCommentOptTag : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * 計量器コメント区分のコード+名称を作成します。<br>
	 * <br>
	 * mst000301_SelectBean.subCtfVal2("0001", "0000000001","1","1") -&gt; "abCDEfg"<br>
	 * <br>
	 * 引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。<br>
	 * <br>
	 * @param syubetu_no_cd 	種別NO
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 * @param type 			計量器タイプ
	 */
	public String subCommentVal( String syubetu_no_cd, String code_cd, String param1, String type )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("code_cd_aft_like",type + code_cd);
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			if( syubetu_no_cd != null && syubetu_no_cd.trim().length() > 0 
				&&	code_cd != null && code_cd.trim().length() > 0 ){
					ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)

					int cnt = 0;                 //処理回数
					boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)
					String Code = "";

					while (rset.next()) {
				
						Code = mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(1);

						if (Code.substring(2).equalsIgnoreCase("0") && param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)) {
								stb.append(Code.substring(0,2));
								stb.append(":");
								stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						} else {
							stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_na")).trim()));
						}
												
					}					
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCommentVal : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCommentVal : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCommentVal : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCommentVal : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}

	/**
	 * 小業種のSELECTタグのOPTIONタグを作成します。（空白行を作成する）<br>
	 * <br>
	 * mst000301_SelectBean.subSgyosyuOptTag("0001", "0000000001") -&gt; OPTIONタグ<br>
	 * <br>
	 * 引数s_gyosyu_cdがnullの場合は、長さ0の文字列になります。<br>
	 * 引数code_cdがnullの場合は、先頭行がSELECTEDになります。<br>
	 * <br>
	 * @param s_gyosyu_cd		小業種コード
	 * @param code_cd			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String subSgyosyuOptTag(String l_gyosyu_cd, String code_cd, String param1 )
	{
		StringBuffer stb = new StringBuffer();                           //生成したOPTIONタグの格納用
		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			
			//商品体系マスタより小業種を取得
			mst000701_MasterInfoDM mstDM = new mst000701_MasterInfoDM();    //各種テーブルの更新情報のDMモジュール
			String Column = "S_GYOSYU_CD";			//取得項目
			List whereListT = new ArrayList();		//商品体系マスタ抽出条件格納
			String strWhere = "";					//名称ＣＴＦ抽出条件格納

			//WHERE句作成
			whereListT.add("		D_GYOSYU_CD = '" + l_gyosyu_cd + "' ");
			whereListT.add("	AND	S_GYOSYU_CD <> '" + mst008001_KeiryokiDictionary.GROSSARY.getCode() + "' ");
			whereListT.add("	GROUP BY " + Column );
			ResultSet rset = db.executeQuery( mstDM.getSyohinTaikeiSelectSql(Column, whereListT) );    //抽出結果(ResultSet)
			while (rset.next()) {
				if ( strWhere.length() > 0) {
					strWhere = strWhere + ",";
				}
				strWhere = strWhere + rset.getString("CD_NAME").trim();
			}
			
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",mst000101_ConstDictionary.BUNRUI1 );
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_in", strWhere);
			rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim())) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim());
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}


		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag2 : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean subCtfOptTag2 : " + sqle.toString());
		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag2 : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean subCtfOptTag2 : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
	
	/**
	 * <p>RNameCtfのSELECTタグのOPTIONタグを作成します。（空白行を作成する）</p>
	 * <p>mst000301_SelectBean.subCtfOptTag2("0001", "0000000001") -&gt; OPTIONタグ</p>
	 * <p>引数syubetu_no_cdがnullの場合は、長さ0の文字列になります。</p>
	 * <p>引数code_cdがnullの場合は、空白行がSELECTEDになります。</p>
	 * <p>テーマ区分のリストボックス用</p>
	 * @param syubetu_no_cd 	種別NO
	 * @param syubetu_no_cd 	代表大業種
	 * @param code_cd 			コード
	 * @param param1 			0:コードなし 1:コードあり
	 */
	public String selThemekb( String syubetu_no_cd, String daigyosyu_cd, String code_cd, String param1 ) {

		StringBuffer stb = new StringBuffer();                       //生成したOPTIONタグの格納用

		mst000101_DbmsBean db = mst000101_DbmsBean.getInstance(); //STCLIBのDBインスタンス格納用
		try {
			//名称・ＣＴＦマスタの取得
			mst000301_NameCtfDM ctfm = new mst000301_NameCtfDM();      //mst000301_SelectBean用名称CTFのDMモジュール
			BeanHolder ctfh = new BeanHolder(ctfm);                      //mst000301_SelectBean用名称CTFのBeanHolder

			Map map = new HashMap();     //mst000301_SelectBean用名称CTFのDMモジュールに対する抽出条件格納用
			map.put("syubetu_no_cd",syubetu_no_cd);
			map.put("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
			map.put("code_cd_not_in",mst000101_ConstDictionary.NAMECTF_SYUBETU);
			map.put("code_cd_aft_like",daigyosyu_cd);
			ResultSet rset = db.executeQuery(ctfm.getSelectSql(map));    //抽出結果(ResultSet)
			int cnt = 0;                 //処理回数
			boolean flg = false;         //指定コード存在フラグ(true:指定コード処理終了 false:指定コード未処理)

			while (rset.next()) {
				if (cnt == 0 && (code_cd == null || code_cd.length() == 0)) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\" SELECTED>");
					stb.append("　");
					stb.append("</OPTION>\n");
					flg = true;
					stb.append("<OPTION VALUE=\"");
					stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
					stb.append("\">");
					if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
						stb.append(":");
					}
					stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
					stb.append("</OPTION>\n");
				} else if (cnt == 0) {
					stb.append("<OPTION VALUE=\"");
					stb.append("");
					stb.append("\">");
					stb.append("　");
					stb.append("</OPTION>\n");
					if (code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
					} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
					}
				} else if (!flg && code_cd.equals(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6))) {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
						stb.append("\" SELECTED>");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
						flg = true;
				} else {
						stb.append("<OPTION VALUE=\"");
						stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
						stb.append("\">");
						if(param1.equals(mst000101_ConstDictionary.FUNCTION_PARAM_1)){
							stb.append(mst000401_LogicBean.chkNullString(rset.getString("code_cd")).trim().substring(4,6));
							stb.append(":");
						}
						stb.append(HTMLStringUtil.convert(mst000401_LogicBean.chkNullString(rset.getString("kanji_rn")).trim()));
						stb.append("</OPTION>\n");
				}
				cnt++;
			}
		} catch(SQLException sqle) {
			stcLog.getLog().fatal("mst000301_SelectBean selThemekb : SQL例外が発生しました。" );
			stcLog.getLog().fatal("mst000301_SelectBean selThemekb : " + sqle.toString());

		} catch(Exception e) {
			stcLog.getLog().error("mst000301_SelectBean selThemekb : 例外が発生しました。" );
			stcLog.getLog().error("mst000301_SelectBean selThemekb : " + e.toString());
		} finally {
			if( null != db ) {
				db.close();
				db = null;
			}
		}
		return stb.toString();
	}
}
