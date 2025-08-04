package mdware.portal.bean;

/**
 * <P>タイトル : MdwareUserSessionBean</P>
 * <P>説明 : セッションに登録してみんなで使う</P>
 * <P>著作権: Copyright (c) 2006</p>					
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author Nakazawa
 * @version 1.0 (2006.05.15) 初版作成 hyu
 * @version 1.1 (2006.06.27) 各部修正リリース Nakazawa
 * @see なし				
 */
public class MdwareUserSessionBean {
	
	//利用ユーザＩＤ
	private String userId = "";
	//利用ユーザ名称
	private String userNa  = "";
	//所属コード
	private String syozokuCd = "";
	//所属名称
	private String syozokuNa = "";
	//利用ユーザ種別
	private String userSyubetuKb  = "";
	//部門コード
	private String bumonCd = "";
	//部門名
	private String bumonNa = "";
	//店舗コード
	private String tenpoCd = "";
	//店舗名称
	private String tenpoNa = "";
	//有効開始日
	private String yukoKaisiDt = "";
	//有効終了日
	private String yukoSyuryoDt = "";
	//システム区分
	private String systemKb = "";
	//サイト区分
	private String siteKb = "";
	//セット区分
	private String setKb = "";

	/**
	 * @return
	 */
	public String getBumonCd() {
		return bumonCd;
	}

	/**
	 * @return
	 */
	public String getBumonNa() {
		return bumonNa;
	}

	/**
	 * @return
	 */
	public String getSyozokuCd() {
		return syozokuCd;
	}

	/**
	 * @return
	 */
	public String getSyozokuNa() {
		return syozokuNa;
	}

	/**
	 * @return
	 */
	public String getSystemKb() {
		return systemKb;
	}

	/**
	 * @return
	 */
	public String getTenpoCd() {
		return tenpoCd;
	}

	/**
	 * @return
	 */
	public String getTenpoNa() {
		return tenpoNa;
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return
	 */
	public String getUserNa() {
		return userNa;
	}

	/**
	 * @return
	 */
	public String getUserSyubetuKb() {
		return userSyubetuKb;
	}

	/**
	 * @return
	 */
	public String getYukoKaisiDt() {
		return yukoKaisiDt;
	}

	/**
	 * @return
	 */
	public String getYukoSyuryoDt() {
		return yukoSyuryoDt;
	}

	/**
	 * @param string
	 */
	public void setBumonCd(String string) {
		bumonCd = string;
	}

	/**
	 * @param string
	 */
	public void setBumonNa(String string) {
		bumonNa = string;
	}

	/**
	 * @param string
	 */
	public void setSyozokuCd(String string) {
		syozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setSyozokuNa(String string) {
		syozokuNa = string;
	}

	/**
	 * @param string
	 */
	public void setSystemKb(String string) {
		systemKb = string;
	}

	/**
	 * @param string
	 */
	public void setTenpoCd(String string) {
		tenpoCd = string;
	}

	/**
	 * @param string
	 */
	public void setTenpoNa(String string) {
		tenpoNa = string;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

	/**
	 * @param string
	 */
	public void setUserNa(String string) {
		userNa = string;
	}

	/**
	 * @param string
	 */
	public void setUserSyubetuKb(String string) {
		userSyubetuKb = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKaisiDt(String string) {
		yukoKaisiDt = string;
	}

	/**
	 * @param string
	 */
	public void setYukoSyuryoDt(String string) {
		yukoSyuryoDt = string;
	}

	/**
	 * @return
	 */
	public String getSiteKb() {
		return siteKb;
	}

	/**
	 * @param string
	 */
	public void setSiteKb(String string) {
		siteKb = string;
	}



	/**
	 * @return
	 */
	public String getSetKb() {
		return setKb;
	}

	/**
	 * @param string
	 */
	public void setSetKb(String string) {
		setKb = string;
	}

}
