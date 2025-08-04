package mdware.batch.mail.control;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.batch.mail.bean.MailBean;

/**
 * <p>タイトル: バッチエラーメール送信オブジェクト</p>
 * <p>説明: バッチエラーメールを送信するオブジェクト。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba(MJC)
 * @version 1.00 (2004/05/14) 初版作成
 */
public class BatchErrorMailSender extends Thread {
	
	private final StcLog stcLog = StcLog.getInstance();
	
	private MailBean mailBean = null;	// 送信メールBean
	
	/**
	 * コンストラクタ。
	 * 
	 * @param mailDataList 送信するメールデータリスト
	 * @param smtpServer 送信時に使用するSMTPサーバ
	 * @param errorStr エラー内容
	 */
	public BatchErrorMailSender(MailBean mailBean) {
		this.mailBean = mailBean;
	}
	
	/* 
	 * コンストラクタで取得したメールのデータを使ってメールを送信する
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		try {
			stcLog.getLog().info("メール送信処理：" + "メール送信処理を開始します。");
			
			// SMTPサーバーのアドレスを指定
			Properties props = System.getProperties();
			props.put(this.mailBean.getSmtpProp(), this.mailBean.getSmtpServer());
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage mimeMessage = new MimeMessage(session);
			
			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress(this.mailBean.getFromAddress(), this.mailBean.getFromName(), this.mailBean.getCharSet()));
			
			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO, this.mailBean.getToAddress());
			
			// メールのタイトルを指定
			//mimeMessage.setSubject(this.mailBean.getSubject());
			mimeMessage.setSubject(MimeUtility.encodeText(this.mailBean.getSubject(), this.mailBean.getCharSet(), "B"));	// vodafoneがQエンコーディングに未対応のため。
			
			// メールの本文を設定
			mimeMessage.setText(this.mailBean.getTextForMail(), this.mailBean.getCharSet());
			
			// 送信日付を指定
			mimeMessage.setSentDate(new Date());
			
			// 送信
			Transport.send(mimeMessage);
			
			stcLog.getLog().info("メール送信処理：" + "メール送信完了しました。");
			stcLog.getLog().info("メール送信処理：" + "メール送信処理を終了します。");
			
		} catch(Exception e) {
			stcLog.getLog().fatal("メール送信処理：" + e.toString());
			stcLog.getLog().fatal("メール送信処理：" + "メール送信処理でエラーが発生しました。");
			stcLog.getLog().info("メール送信処理：" + "メール送信処理を終了します。");
		}
	}
}