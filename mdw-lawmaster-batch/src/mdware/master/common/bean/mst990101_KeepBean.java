package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import mdware.master.common.command.mst580301_TenGroupNoEachUpdateCommand;
import mdware.portal.bean.MdwareUserSessionBean;

/**
 * <p>タイトル: メーカーコード照会画面データ保持用クラス</p>
 * <p>説明: メーカーコード照会画面の表示用データを保持するクラスです</p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author k_tanigawa
 * @version 1.0 (2006/10/15) 初版作成
 */
public class mst990101_KeepBean {

	/****************************************************
	 ****************************************************
	 ****************************************************
	                       定数宣言
	 ****************************************************
	 ****************************************************
	 ****************************************************/
	public static final String BUYER_MODE = "0";
	public static final String TORIHIKISAKI_MODE = "1";
	
	
	/****************************************************
	 ****************************************************
	 ****************************************************
	                       変数宣言
	 ****************************************************
	 ****************************************************
	 ****************************************************/
	private String bumonCd          = null;	//部門コード
	private String bumonNa          = null;	//部門名称
	private String makerCd          = null;	//メーカーコード
	private String makerNa          = null;	//メーカー名称
	private String siiresakiCd      = null;	//仕入先コード
	private String siiresakiNa      = null;	//仕入先名称
	private String groupNoCd        = null;	//店配列コード
	private String groupNoNa        = null;	//店配列名称

	private String displayMode      = BUYER_MODE;	//表示モード(バイヤユーザ：０　取引先ユーザ：１) デフォルトは、バイヤユーザ

	private String firstFocus       = null;		//フォーカス設定対象テキストボックス

	private String changePage       = null;		//ページ切り替え
	
	private List headerTenpoList         = null;	//検索結果ヘッダ部に出力する店舗名称格納用List
	private BeanHolder searchResultBh    = null;	//検索結果格納用BeanHolder
	
	private String errorMessage          = null;	//マスタ共通JSP側で使用しているため、仕方なく作成
	private String errorFlg              = null;	//マスタ共通JSP側で使用しているため、仕方なく作成
	private String bumonCdBackColor      = null;	//部門コード背景色
	private String makerCdBackColor      = null;	//メーカーコード背景色
	private String siiresakiCdBackColor  = null;	//仕入先コード背景色
	private String tenHairetuCdBackColor = null;	//店配列コード背景色

	private MdwareUserSessionBean userSessionBean = null;

	
	/****************************************************
	 ****************************************************
	 ****************************************************
	                    コンストラクタ
	 ****************************************************
	 ****************************************************
	 ****************************************************/
	public mst990101_KeepBean(){

	}
	
	
	/****************************************************
	 ****************************************************
	 ****************************************************
                   メソッド宣言(非共通系)
	 ****************************************************
	 ****************************************************
	 ****************************************************/

	/**
	 * 初期化処理
	 * NullPointerException発生防止のため、全メンバに対して何らかのインスタンスを設定する。
	 */
	public void init( MdwareUserSessionBean userSessionBean ){

		this.userSessionBean  = userSessionBean;

		//バイヤユーザか取引先ユーザかをユーザ種別区分にて判別( 1：お取引先　　２及びそれ以外はバイヤ  )
		if( userSessionBean.getUserSyubetuKb().toString().equals("1") ){
			this.displayMode  = TORIHIKISAKI_MODE;
			this.bumonCd      = "";
			this.bumonNa      = "";
			this.setSiiresakiCd(this.nullToBlank(this.userSessionBean.getSyozokuCd()));
			this.siiresakiNa  = this.nullToBlank(this.userSessionBean.getSyozokuNa());
		}else{
			this.displayMode  = BUYER_MODE;
			this.bumonCd      = this.nullToBlank(this.userSessionBean.getBumonCd());
			this.bumonNa      = this.nullToBlank(this.userSessionBean.getBumonNa());
			this.siiresakiCd  = "";
			this.siiresakiNa  = "";
		}
		this.makerCd          = "";
		this.makerNa          = "";
		this.groupNoCd        = mst580301_TenGroupNoEachUpdateCommand.ALLTENPOCD;	//初期表示時は、全店ＣＤを表示
		this.groupNoNa        = mst580301_TenGroupNoEachUpdateCommand.ALLTENPONA;	//初期表示時は、全店名称を表示
		this.changePage       = "";

		this.clearErrorInfo();
		
		this.clearSearchResult();
	}

	/**
	 * 画面エラー情報をクリア
	 */
	public void clearErrorInfo(){
		
		this.errorMessage          = "";
		this.errorFlg              = "";
		this.bumonCdBackColor      = "#FFFFFF";
		this.makerCdBackColor      = "#FFFFFF";
		this.siiresakiCdBackColor  = "#FFFFFF";
		this.tenHairetuCdBackColor = "#FFFFFF";
	}
	
	/**
	 * 画面名称情報(部門名称、仕入先名称…etc.)をクリア
	 */
	public void clearNameInfo(){
		this.bumonNa          = "";
		this.siiresakiNa      = "";
		this.makerNa          = "";
		this.groupNoNa     = "";
	}
	
	/**
	 * 保持している検索結果情報(ヘッダ店舗、検索結果…etc.)をクリア
	 */
	public void clearSearchResult(){
		this.searchResultBh  = null;
		this.headerTenpoList = null;
	}
	
	/**
	 * bumonCd を戻します。
	 * @return bumonCd
	 */
	public String getBumonCd() {
		
		if( this.bumonCd != null && this.bumonCd.length() == 4 ){
			this.bumonCd = this.bumonCd.substring(1);	//4桁なら3桁に
		}
		return this.nullToBlank(this.bumonCd);
	}

	/**
	 * bumonCd を設定
	 * @param bumonCd
	 */
	public void setBumonCd(String bumonCd) {
		this.bumonCd = bumonCd;
	}

	/**
	 * bumonNa を戻します。
	 * @return bumonNa
	 */
	public String getBumonNa() {
		return this.nullToBlank(bumonNa);
	}

	/**
	 * bumonNa を設定
	 * @param bumonNa
	 */
	public void setBumonNa(String bumonNa) {
		this.bumonNa = bumonNa;
	}

	/**
	 * bumonCdBackColor を戻します。
	 * @return bumonCdBackColor
	 */
	public String getBumonCdBackColor() {
		return bumonCdBackColor;
	}

	/**
	 * bumonCdBackColor を設定
	 * @param bumonCdBackColor
	 */
	public void setBumonCdBackColor(String bumonCdBackColor) {
		this.bumonCdBackColor = bumonCdBackColor;
	}

	/**
	 * makerCd を戻します。
	 * @return makerCd
	 */
	public String getMakerCd() {
		return this.nullToBlank(makerCd);
	}

	/**
	 * makerCd を設定
	 * @param makerCd
	 */
	public void setMakerCd(String makerCd) {
		this.makerCd = makerCd;
	}

	/**
	 * makerNa を戻します。
	 * @return makerNa
	 */
	public String getMakerNa() {
		return this.nullToBlank(makerNa);
	}

	/**
	 * makerNa を設定
	 * @param makerNa
	 */
	public void setMakerNa(String makerNa) {
		this.makerNa = makerNa;
	}

	/**
	 * makerCdBackColor を戻します。
	 * @return makerCdBackColor
	 */
	public String getMakerCdBackColor() {
		return makerCdBackColor;
	}

	/**
	 * makerCdBackColor を設定
	 * @param makerCdBackColor
	 */
	public void setMakerCdBackColor(String makerCdBackColor) {
		this.makerCdBackColor = makerCdBackColor;
	}

	/**
	 * siiresakiCd を戻します。
	 * @return siiresakiCd
	 */
	public String getSiiresakiCd() {
		return this.nullToBlank(siiresakiCd);
	}

	/**
	 * siiresakiCd を設定(7桁以上の値を受け取った場合は、仕入先コード=6桁、あふれた桁は無視とする(地区コードは不要であるため))
	 * @param siiresakiCd
	 */
	public void setSiiresakiCd(String siiresakiCd) {
		this.siiresakiCd = siiresakiCd;
		if( this.siiresakiCd != null && this.siiresakiCd.trim().length() > 0 ){
			this.siiresakiCd = this.siiresakiCd.trim();
			if( this.siiresakiCd.length() >= 6 ){
				this.siiresakiCd = this.siiresakiCd.substring(0, 6);
			}
		}
	}

	/**
	 * siiresakiNa を戻します。
	 * @return siiresakiNa
	 */
	public String getSiiresakiNa() {
		return this.nullToBlank(siiresakiNa);
	}

	/**
	 * siiresakiNa を設定
	 * @param siiresakiNa
	 */
	public void setSiiresakiNa(String siiresakiNa) {
		this.siiresakiNa = siiresakiNa;
	}

	/**
	 * siiresakiCdBackColor を戻します。
	 * @return siiresakiCdBackColor
	 */
	public String getSiiresakiCdBackColor() {
		return siiresakiCdBackColor;
	}

	/**
	 * siiresakiCdBackColor を設定
	 * @param siiresakiCdBackColor
	 */
	public void setSiiresakiCdBackColor(String siiresakiCdBackColor) {
		this.siiresakiCdBackColor = siiresakiCdBackColor;
	}
	
	/**
	 * tenHairetuCd を戻します。
	 * @return tenHairetuCd
	 */
	public String getGroupNoCd() {
		return this.nullToBlank(groupNoCd);
	}

	/**
	 * tenHairetuCd を設定
	 * @param tenHairetuCd
	 */
	public void setGroupNoCd(String tenHairetuCd) {
		this.groupNoCd = tenHairetuCd;
	}

	/**
	 * tenHairetuNa を戻します。
	 * @return tenHairetuNa
	 */
	public String getGroupNoNa() {
		return this.nullToBlank(groupNoNa);
	}

	/**
	 * tenHairetuNa を設定
	 * @param tenHairetuNa
	 */
	public void setGroupNoNa(String tenHairetuNa) {
		this.groupNoNa = tenHairetuNa;
	}

	/**
	 * tenHairetuCdBackColor を戻します。
	 * @return tenHairetuCdBackColor
	 */
	public String getGroupNoCdBackColor() {
		return tenHairetuCdBackColor;
	}

	/**
	 * tenHairetuCdBackColor を設定
	 * @param tenHairetuCdBackColor
	 */
	public void setGroupNoCdBackColor(String tenHairetuCdBackColor) {
		this.tenHairetuCdBackColor = tenHairetuCdBackColor;
	}

	/**
	 * displayMode を戻します。
	 * @return displayMode
	 */
	public String getDisplayMode() {
		return this.nullToBlank(displayMode);
	}

	/**
	 * displayMode を設定
	 * @param displayMode
	 */
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
	
	/**
	 * firstFocus を戻します。
	 * @return firstFocus
	 */
	public String getFirstFocus() {
		return ( firstFocus == null || firstFocus.trim().length() <= 0 ) ? "bumon_cd" : firstFocus.trim();
	}

	/**
	 * firstFocus を設定
	 * @param firstFocus
	 */
	public void setFirstFocus(String firstFocus) {
		this.firstFocus = firstFocus;
	}

	/**
	 * changePage を戻します。
	 * @return changePage
	 */
	public String getChangePage() {
		return ( changePage == null ) ? "" : changePage.trim();
	}

	/**
	 * changePage を設定
	 * @param changePage
	 */
	public void setChangePage(String changePage) {
		this.changePage = changePage;
	}
	
	/**
	 * headerTenpoList を戻します。
	 * @return headerTenpoList
	 */
	public List getHeaderTenpoList() {
		return ( headerTenpoList == null ) ? new ArrayList() : headerTenpoList;
	}

	/**
	 * headerTenpoList を設定
	 * @param headerTenpoList
	 */
	public void setHeaderTenpoList(List headerTenpoList) {
		this.headerTenpoList = headerTenpoList;
	}
	
	/**
	 * searchResultList を戻します。
	 * @return searchResultList
	 */
	public BeanHolder getSearchResult() {
		return searchResultBh;
	}

	/**
	 * searchResultList を設定
	 * @param searchResultList
	 */
	public void setSearchResult(BeanHolder bh) {
		this.searchResultBh = bh;
	}

//	↓↓2007.01.21 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * headerHTML を戻します。
//	 * @return headerHTML
//	 */
//	public String getHeaderHTML() {
//
//		if( this.getHeaderTenpoList().isEmpty() ){
//			return "";
//		}
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("				<tr align=\"center\" width=\"1000\">");
//		sb.append("					<th nowrap rowspan=\"2\">ﾒｰｶｰ<BR>ｺｰﾄﾞ</th>");
//		sb.append("					<th nowrap rowspan=\"2\" width=\"200\">ﾒｰｶｰ名称</th>");
//		sb.append("					<th nowrap rowspan=\"2\">仕入先<BR>ｺｰﾄﾞ</th>");
//		sb.append("					<th nowrap rowspan=\"2\">地区</th>");
//		sb.append("					<th nowrap rowspan=\"2\" width=\"200\">仕入先名称</th>");
//		sb.append("					<th nowrap rowspan=\"2\">店舗数</th>");
//		sb.append("					<th colspan="+this.headerTenpoList.size()+" align=\"center\">店舗</th>");
//		sb.append("				</tr>");
//		sb.append("				<tr align=\"left\">");
//	for( Iterator ite = this.headerTenpoList.iterator(); ite.hasNext(); ){
//		mst990101_RTenGroupBean bean = (mst990101_RTenGroupBean)ite.next();
//		sb.append("					<th align=\"center\" style=\"writing-mode:tb-rl;Height:100;\">"+bean.getKanjiRn().trim()+"</th>");
//	}
//		sb.append("				</tr>");
//		
//		return sb.toString();
//	}
//	↑↑2007.01.21 H.Yamamoto カスタマイズ修正↑↑

	/****************************************************
	 ****************************************************
	 ****************************************************
                     メソッド宣言(共通系)
            共通JSP(mst000501_MessageInclude.jsp)で
                       使用するメソッド
	 ****************************************************
	 ****************************************************
	 ****************************************************/
	/**
	 * errorMessage を戻します。
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * errorMessage を設定
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * errorFlg を戻します。
	 * @return errorFlg
	 */
	public String getErrorFlg() {
		return errorFlg;
	}

	/**
	 * errorFlg を設定
	 * @param errorFlg
	 */
	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}

	
	/**
	 * 引数strがnull　　→""を返す
	 * 引数strがnull以外→str.trim()を返す
	 * @param str
	 */
	private String nullToBlank(String str){
		return ( (str == null) ? "" : str.trim() );
	}


}
	