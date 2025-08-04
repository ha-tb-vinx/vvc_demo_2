package mdware.batch.mail.control;

import java.util.Hashtable;

import javax.mail.Address;

import org.apache.log4j.Level;

import mdware.batch.mail.bean.MailBean;
import mdware.batch.mail.util.BatchErrorMailXmlAccessor;

/**
 * <p>タイトル: バッチエラーメール送信管理オブジェクト</p>
 * <p>説明: バッチでWarn以上のエラーログが発生した際に、メール送信の制御を行う。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba(MJC)
 * @version 1.00 (2004/05/14) 初版作成
 */
public class BatchMailController {
	
	private static final BatchMailController INSTANCE = new BatchMailController();
	
	private int lastCalledLevel = 0;					// 最後に呼び出されたLog4jのエラーレベル
	private Hashtable mailDataTable = new Hashtable();	// エラーレベルをキーに送信先メールのデータを保存
	private MailBean mailBean = null;					// エラーメールのプロパティ
	
	/**
	 * コンストラクタ。
	 * Singletonによりクラスのローディング時に一度だけ呼び出され、XMLで記述されたメールの送信データを取得します。
	 */
	private BatchMailController() {
		
		try {
			
			// XMLからデータを読み込む。
			BatchErrorMailXmlAccessor xmlAccesser = new BatchErrorMailXmlAccessor();
			xmlAccesser.xmlLoading();
			this.mailBean = xmlAccesser.getMailBean();
			this.mailDataTable.put(new Integer(Level.WARN_INT), xmlAccesser.getToAddressList(Level.WARN_INT));
			this.mailDataTable.put(new Integer(Level.ERROR_INT), xmlAccesser.getToAddressList(Level.ERROR_INT));
			this.mailDataTable.put(new Integer(Level.FATAL_INT), xmlAccesser.getToAddressList(Level.FATAL_INT));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * インスタンスを取得するGetterメソッド。
	 * 
	 * @return BatchMailContorollerオブジェクト
	 */
	public static BatchMailController getInstance() {
		return INSTANCE;
	}
	
	/**
	 * BatchLogでエラー発生時に呼び出されるメソッド。
	 * 一度送信したエラーレベルと以下のエラーの場合、メールを送信しません。
	 * 
	 * @param level エラーレベル
	 * @param obj エラー内容(String)
	 */
	public void sendBatchErrorMail(int level, Object obj) throws Exception {
		
		// すでにメール送信したエラーレベルと同等以下であればメールを送信しない。
		if( level <= this.lastCalledLevel ) {
			//System.out.println("すでに送信したエラーレベルなので送信処理を行いません。");
			return;
		} else {
			//System.out.println("メール送信");
			this.lastCalledLevel = level;
		}
		
		Address[] addresses = (Address[])mailDataTable.get(new Integer(level));
		if( addresses != null && addresses.length > 0 && this.mailBean != null && obj != null ) {
			
			mailBean.setToAddress(addresses);
			if( level == Level.WARN_INT ){
				mailBean.setSubject("RBSiteログ情報 エラーレベル : " + Level.WARN.toString());
			} else if( level == Level.ERROR_INT ){
				mailBean.setSubject("RBSiteログ情報 エラーレベル : " + Level.ERROR.toString());
			} else if( level == Level.FATAL_INT ){
				mailBean.setSubject("RBSiteログ情報 エラーレベル : " + Level.FATAL.toString());
			} else {
				mailBean.setSubject("RBSiteログ情報 エラーレベル不明");
			}
			mailBean.setText(obj.toString());
			
			BatchErrorMailSender mailSender = new BatchErrorMailSender(this.mailBean);
			mailSender.run();
		}
	}
}
