package mdware.batch.mail.bean;

import org.apache.log4j.Level;

/**
 * <p>タイトル: 送信先アドレスBean</p>
 * <p>説明: 送信先アドレスの情報を保存するBean。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba(MJC)
 * @version 1.00 (2004/05/14) 初版作成
 */
public class ToAddressBean {
	
	private String riyoUserId = "";
	private String name = "";
	private String address = "";
	private int level = Integer.MAX_VALUE;
	
	/**
	 * 画面表示用のエラーレベル文字列を返します。
	 * 
	 * @return エラーレベル
	 */
	public String getLevelString() {
		
		if( this.level == Level.DEBUG_INT ) {
			return Level.DEBUG.toString() + "以上";
		} else if( this.level == Level.ERROR_INT ) {
			return Level.ERROR.toString() + "以上";
		} else if( this.level == Level.FATAL_INT ) {
			return Level.FATAL.toString() + "以上";
		} else if( this.level == Level.INFO_INT ) {
			return Level.INFO.toString() + "以上";
		} else if( this.level == Level.WARN_INT ) {
			return Level.WARN.toString() + "以上";
		} else {
			return "";
		}
		
	}
	
	/**
	 * 画面表示時にリストボックス中の"selected"を返します。
	 * 
	 * @param level
	 * @return
	 */
	public String getSelectedString(int level) {
		
		if( this.level == level ) {
			return " selected";
		} else {
			return "";
		}
	}
	
	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getRiyoUserId() {
		return riyoUserId;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param i
	 */
	public void setLevel(int i) {
		level = i;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setRiyoUserId(String string) {
		riyoUserId = string;
	}

}
