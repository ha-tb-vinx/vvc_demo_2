package mdware.batch.mail.bean;

import java.util.List;

import javax.mail.Address;

/**
 * <p>タイトル: バッチエラーメールBean</p>
 * <p>説明: SMTPなどのバッチエラーメールの情報を保存するBean。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba(MJC)
 * @version 1.00 (2004/05/14) 初版作成
 */
public class MailBean {
	
	private String charSet = null;
	private String smtpProp = null;
	private String smtpServer = null;
	private String fromAddress = null;
	private String fromName = null;
	private Address[] toAddress = null;
	private String subject = null;
	private String template = null;
	private String text = null;
	
	/**
	 * メール本文を表示する際に、長文のSQLを省略して本文を返すメソッド。
	 * 
	 * @return
	 */
	public String getTextForMail() {
		
		if( this.text.indexOf("使用したＳＱＬは\n↓\n") >= 0 && this.text.indexOf("\n↑\nです。") >= 0 ) {
			StringBuffer buf = new StringBuffer();
			buf.append( this.text.substring(0, this.text.indexOf("使用したＳＱＬは\n↓\n") + 50) );
			buf.append("\n ----- 略 -----\n");
			buf.append( this.text.substring(this.text.indexOf("\n↑\nです。") - 30, this.text.length()) );
			return this.template.replaceAll("%text%", buf.toString());
		} else {
			return this.template.replaceAll("%text%", this.text);
		}
	}
	
	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append(" CharSet : " + this.charSet);
		buf.append("\n SMTP properties : " + this.smtpProp);
		buf.append("\n SMTP SERVER : " + this.smtpServer);
		buf.append("\n from Address : " + this.fromAddress);
		buf.append("\n from Name : " + this.fromName);
		buf.append("\n Subject : " + this.subject);
		if( toAddress != null ) {
			buf.append("\n ");
			for( int i = 0; i < toAddress.length; i++ ) {
				buf.append(toAddress[i] + ", ");
			}
		}
		buf.append("\n Text : " + this.text);
		
		return buf.toString();
	}
	
	/**
	 * @return
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * @return
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * @return
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * @return
	 */
	public String getSmtpProp() {
		return smtpProp;
	}

	/**
	 * @return
	 */
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return
	 */
	public Address[] getToAddress() {
		return toAddress;
	}

	/**
	 * @param string
	 */
	public void setCharSet(String string) {
		charSet = string;
	}

	/**
	 * @param string
	 */
	public void setFromAddress(String string) {
		fromAddress = string;
	}

	/**
	 * @param string
	 */
	public void setFromName(String string) {
		fromName = string;
	}

	/**
	 * @param string
	 */
	public void setSmtpProp(String string) {
		smtpProp = string;
	}

	/**
	 * @param string
	 */
	public void setSmtpServer(String string) {
		smtpServer = string;
	}

	/**
	 * @param string
	 */
	public void setSubject(String string) {
		subject = string;
	}

	/**
	 * @param string
	 */
	public void setTemplate(String string) {
		template = string;
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

	/**
	 * @param addresses
	 */
	public void setToAddress(Address[] addresses) {
		toAddress = addresses;
	}

}
