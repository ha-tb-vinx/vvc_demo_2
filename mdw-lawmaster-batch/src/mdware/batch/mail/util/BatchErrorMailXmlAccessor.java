package mdware.batch.mail.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mdware.batch.mail.bean.MailBean;
import mdware.batch.mail.bean.ToAddressBean;
import mdware.common.util.InputProperties;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vincuram Japan corporation</p>
 * @author yunba(MJC)
 * @version 1.00 (2004/05/14) 初版作成
 */
public class BatchErrorMailXmlAccessor {
	
	private static final String PROPERTIES_FILE_NAME = "common.properties";		// バッチエラーメール設定が記述されているファイル名。
	private static final String PATH_FOR_WEB = "batchErrorMail.pathForWeb";		// common.properties内の取得項目名。（Web用）
	private static final String PATH_FOR_BATCH = "batchErrorMail.pathForBatch";	// common.properties内の取得項目名。（Batch用）
	
	private MailBean mailBean = null;
	private List addressList = new ArrayList();
	
	/**
	 * コンストラクタ。
	 * 
	 * @throws Exception
	 */
	public BatchErrorMailXmlAccessor() throws Exception {
		
	}
	
	/**
	 * errorMail.xmlのPATHを取得するメソッドです。
	 * 
	 * @return errorMail.xmlのPATH
	 */
	private String getFilePath() throws Exception {
		
		InputProperties properties = null;
		if( jp.co.vinculumjapan.stc.util.properties.StcLibProperty.propertiesDir != null ) {	// Tomcatで使用する場合。
			properties = new InputProperties(BatchErrorMailXmlAccessor.PROPERTIES_FILE_NAME , jp.co.vinculumjapan.stc.util.properties.StcLibProperty.propertiesDir);
			return properties.getStringProperties(BatchErrorMailXmlAccessor.PATH_FOR_WEB);
		} else {	// BatchLogで使用する場合。
			properties = new InputProperties(BatchErrorMailXmlAccessor.PROPERTIES_FILE_NAME , mdware.common.batch.util.properties.BatchProperty.propertiesDir);
			return properties.getStringProperties(BatchErrorMailXmlAccessor.PATH_FOR_BATCH);
		}
	}
	
	/**
	 * XMLを読み込み、サーバ情報のBeanと送信先情報のBeanを作成します。
	 * 
	 * @throws Exception
	 */
	public void xmlLoading() throws Exception {
		
		// XMLファイルのパスを取得。
		String filePath = this.getFilePath();
		
		// XMLからデータを読み込む処理を記述。
//		DOMParser parser = new DOMParser();
//		parser.setFeature("http://xml.org/sax/features/validation", true);
//		parser.parse(new InputSource(new FileInputStream("C:\\rbsitedate\\datas\\mail\\errorMail.xml")));
//		Document document = parser.getDocument();
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
		Element rootElement = document.getDocumentElement();
		
		// Mailタグの情報を取得。
		NodeList mailElementList = rootElement.getElementsByTagName("Mail");
		
		for( int i = 0; i < mailElementList.getLength(); i++ ) {
			
			Node mailElement = (Node)mailElementList.item(i);
			List mailTagList = this.getElementNode( mailElement.getChildNodes() );
			this.mailBean = new MailBean();
			
			for( int j = 0; j < mailTagList.size(); j++ ) {
				
				Node mailTag = (Node)mailTagList.get(j);
				
				if( mailTag.getNodeName().equals("CharSet") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setCharSet(this.getTextNode(mailTag));
				} else if( mailTag.getNodeName().equals("SmtpProp") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setSmtpProp(this.getTextNode(mailTag));
				} else if( mailTag.getNodeName().equals("SmtpServer") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setSmtpServer(this.getTextNode(mailTag));
				} else if( mailTag.getNodeName().equals("FromAddress") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setFromAddress(this.getTextNode(mailTag));
				} else if( mailTag.getNodeName().equals("FromName") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setFromName(this.getTextNode(mailTag));
				} else if( mailTag.getNodeName().equals("Template") ) {
					//System.out.println(this.getTextNode(mailTag));
					this.mailBean.setTemplate(this.getTextNode(mailTag));
				}
			}
		}
		
		// ToAddressタグの情報を取得。
		NodeList addressElementList = rootElement.getElementsByTagName("ToAddress");
		
		for( int i = 0; i < addressElementList.getLength(); i++ ) {
			
			Node addressElement = (Node)addressElementList.item(i);
			List addressTagList = this.getElementNode( addressElement.getChildNodes() );
			ToAddressBean addressBean = new ToAddressBean();
			
			for( int j = 0; j < addressTagList.size(); j++ ) {
				
				Node addressTag = (Node)addressTagList.get(j);
				if( addressTag.getNodeName().equals("RiyoUserId") ) {
					//System.out.println(this.getTextNode(addressTag));
					addressBean.setRiyoUserId(this.getTextNode(addressTag));
				} else if( addressTag.getNodeName().equals("Name") ) {
					//System.out.println(this.getTextNode(addressTag));
					addressBean.setName(this.getTextNode(addressTag));
				} else if( addressTag.getNodeName().equals("Address") ) {
					//System.out.println(this.getTextNode(addressTag));
					addressBean.setAddress(this.getTextNode(addressTag));
				} else if( addressTag.getNodeName().equals("Level") ) {
					//System.out.println(this.getTextNode(addressTag));
					addressBean.setLevel( Integer.parseInt( this.getTextNode(addressTag)) );
				}
			}
			this.addressList.add(addressBean);
		}
	}
	
	/**
	 * メールアドレスを新規登録するメソッド。
	 * 
	 * @param insertAddress
	 * @throws Exception
	 */
	public void insertAddress(ToAddressBean insertAddress) throws Exception {
		
		// XMLファイルのパスを取得。
		String filePath = this.getFilePath();
		
		// XMLからデータを読み込む処理を記述。
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
		Element rootElement = document.getDocumentElement();
		
		// 末尾に新規登録したメールアドレスを追加。
		rootElement.appendChild(document.createTextNode("\t"));
		rootElement.appendChild(this.beanToNode(document, insertAddress));
		rootElement.appendChild(document.createTextNode("\n"));
		
		this.xmlWriting(document, filePath);
	}
	
	/**
	 * メールアドレスを更新するメソッド。
	 * 
	 * @param updateAddress
	 * @param oldAddress
	 * @throws Exception
	 */
	public void updateAddress(ToAddressBean updateAddress, String oldAddress) throws Exception {
		
		// XMLファイルのパスを取得。
		String filePath = this.getFilePath();
		
		// XMLからデータを読み込む処理を記述。
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
		Element rootElement = document.getDocumentElement();
		
		NodeList childNodeList = rootElement.getElementsByTagName("ToAddress");
		
		for( int i = 0; i < childNodeList.getLength(); i++ ) {
			
			Element addressElement = (Element)childNodeList.item(i);
			NodeList addressNodeList = addressElement.getElementsByTagName("Address");
			
			// メールアドレスが同じものを更新する。
			if( addressNodeList.item(0) != null && this.getTextNode(addressNodeList.item(0)).equals(oldAddress) ) {
				rootElement.replaceChild(this.beanToNode(document, updateAddress), addressElement);
			}
		}
		
		this.xmlWriting(document, filePath);
	}
	
	/**
	 * メールアドレスを削除するメソッド。
	 * 
	 * @param deleteAddresses
	 * @throws Exception
	 */
	public void deleteAddresses(ToAddressBean[] deleteAddresses) throws Exception {
		
		// XMLファイルのパスを取得。
		String filePath = this.getFilePath();
		
		// XMLからデータを読み込む処理を記述。
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
		Element rootElement = document.getDocumentElement();
		
		NodeList childNodeList = rootElement.getElementsByTagName("ToAddress");
		
		for( int i = 0; i < deleteAddresses.length; i++ ) {
			
			for( int j = 0; j < childNodeList.getLength(); j++ ) {
				
				Element addressElement = (Element)childNodeList.item(j);
				NodeList addressNodeList = addressElement.getElementsByTagName("Address");
				
				if( addressNodeList.item(0) != null && this.getTextNode(addressNodeList.item(0)).equals(deleteAddresses[i].getAddress()) ) {
					
					// <ToAddress>タグの前の改行コードを削除。
					Node returnNode = addressElement.getPreviousSibling().getPreviousSibling();
					if( returnNode.getNodeType() == Node.TEXT_NODE ) {
						rootElement.removeChild(returnNode);
					}
					// <ToAddress>タグの前のタブを削除。
					Node tabNode = addressElement.getPreviousSibling();
					if( tabNode.getNodeType() == Node.TEXT_NODE ) {
						rootElement.removeChild(tabNode);
					}
					
					// <ToAddress>タグを削除。
					rootElement.removeChild((Node)addressElement);
					j--;	// Nodeを削除するとaddressNodeListも前づめされるのでjをデクリメント。
					
					//System.out.println(deleteAddresses[i] + "を削除しました。");
				}
			}
		}
		
		this.xmlWriting(document, filePath);
	}
	
	/**
	 * NodeListから改行やタブ文字を抜いたElementのみのリストを返します。
	 * 
	 * @param nodeList
	 * @return
	 */
	private List getElementNode(NodeList nodeList) throws Exception {
		
		List returnList = new ArrayList();
		
		for( int i = 0; i < nodeList.getLength(); i++ ) {
			Node node = nodeList.item(i);
			
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				returnList.add(node);
			}
		}
		
		return returnList;
	}
	
	/**
	 * XMLファイルのタグの内容を取得
	 * 
	 * @param node
	 * @return
	 */	
	private String getTextNode(Node node) throws Exception {
		String value = "";
		if( node.getFirstChild() != null ) {
			value = node.getFirstChild().getNodeValue(); 
		}
		return value;
	}
	
	/**
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public Object[] getToAddressList(int level) throws Exception {
		
		List returnList = new ArrayList();
		
		for( int i = 0; i < this.addressList.size(); i++ ) {
			
			ToAddressBean addressBean = (ToAddressBean)this.addressList.get(i);
			// 送信するエラーレベルの応じてデータを保存。
			if( addressBean.getLevel() <= level ) {
				//System.out.println(" add " + level + " : "+ addressBean.getLevel());
				returnList.add(new InternetAddress(addressBean.getAddress()));
			}
		}
		
		return returnList.toArray(new Address[0]);
	}
	
	/**
	 * @param riyoUserId
	 * @return
	 * @throws Exception
	 */
	public List getToAddressList(String riyoUserId) throws Exception {
		
		List returnList = new ArrayList();
		
		for( int i = 0; i < this.addressList.size(); i++ ) {
			
			ToAddressBean addressBean = (ToAddressBean)this.addressList.get(i);
			// 引数の利用ユーザIDのものだけ取得。
			if( riyoUserId.equals( addressBean.getRiyoUserId() ) == true ) {
				//System.out.println(" add " + riyoUserId + " : "+ addressBean.getRiyoUserId() + " " + i);
				returnList.add(addressBean);
			}
		}
		
		return returnList;
	}
	
	/**
	 * ToAddressBeanをXML記述にインデントもあわせて変換するメソッド。
	 * 
	 * @param document
	 * @param bean
	 * @return
	 */
	private Node beanToNode(Document document, ToAddressBean bean) {
		
		Element element = document.createElement("ToAddress");
		
		element.appendChild(document.createTextNode("\n"));
		element.appendChild(document.createTextNode("\t"));
		element.appendChild(document.createTextNode("\t"));
		Element childElement = document.createElement("RiyoUserId");
		childElement.appendChild(document.createTextNode(bean.getRiyoUserId()));
		element.appendChild(childElement);
		
		element.appendChild(document.createTextNode("\n"));
		element.appendChild(document.createTextNode("\t"));
		element.appendChild(document.createTextNode("\t"));
		childElement = document.createElement("Name");
		childElement.appendChild(document.createTextNode(bean.getName()));
		element.appendChild(childElement);
		
		element.appendChild(document.createTextNode("\n"));
		element.appendChild(document.createTextNode("\t"));
		element.appendChild(document.createTextNode("\t"));
		childElement = document.createElement("Address");
		childElement.appendChild(document.createTextNode(bean.getAddress()));
		element.appendChild(childElement);
		
		element.appendChild(document.createTextNode("\n"));
		element.appendChild(document.createTextNode("\t"));
		element.appendChild(document.createTextNode("\t"));
		childElement = document.createElement("Level");
		childElement.appendChild(document.createTextNode(String.valueOf(bean.getLevel())));
		element.appendChild(childElement);
		
		element.appendChild(document.createTextNode("\n"));
		element.appendChild(document.createTextNode("\t"));
		
		return element;
	}
	
	/**
	 * XMLファイルを書き込むメソッド。
	 * 
	 * @param document
	 * @param filePath
	 * @throws Exception
	 */
	private void xmlWriting(Document document, String filePath) throws Exception {
		
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		
		DOMSource source = new DOMSource(document);
		File newXML = new File(filePath);
		FileOutputStream os = new FileOutputStream(newXML);
		StreamResult sr = new StreamResult(os);
		transformer.transform(source,sr);
	}
	
	/**
	 * @return
	 */
	public List getAddressList() {
		return addressList;
	}

	/**
	 * @return
	 */
	public MailBean getMailBean() {
		return mailBean;
	}
}
