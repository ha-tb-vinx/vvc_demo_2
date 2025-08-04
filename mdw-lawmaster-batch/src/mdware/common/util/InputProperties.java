package mdware.common.util;

import java.io.*;
import java.util.*;
import jp.co.vinculumjapan.stc.util.csv.*;
import jp.co.vinculumjapan.stc.log.StcLog;
/**
 *
 * <p>タイトル: InputProperties</p>
 * <p>説明: CSV形式(キー情報,データ情報)の形になっているプロパティファイルを読み込んでデータを取得。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author S.Shimatani
 * @version 1.0
 */
public class InputProperties {
	private final String DEFAULTROOT = "";
	private String root = "";
	private String fileName = "";
    private String partition = "";


	private boolean initilized = false;
	private Map map = null;
	private final StcLog stcLog = StcLog.getInstance();
    /**
     * コンストラクタ  ファイル名を渡します。
     * @param fileName
     */
    public InputProperties(String fileName) {
        this.fileName = fileName;
		this.initPropertiesData();
    }
    /**
     * コンストラクタ　ファイル名とルートを渡します。
     * @param fileName
     * @param root
     */
    public InputProperties(String fileName , String root) {
        this.root = root;
        if(!root.equalsIgnoreCase(""))
        {
            this.partition = "/";
        }
        this.fileName = fileName;
		this.initPropertiesData();
    }
    /**
     * ルートをセットMapにデータを取り込みなおします。。
     * @param root
     */
    public void setRoot(String root){
        this.root = root;
        if(!root.equalsIgnoreCase(""))
        {
            this.partition = "/";
        }
        this.clear();
        this.initPropertiesData();
    }
    /**
     * ファイル名をセットmapにデータを取り込みなおします。
     * @param fileName
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
        this.clear();
		this.initPropertiesData();
    }
    /**
     * ルートとファイル名をセットmapにデータを取り込みなおします。
     * @param root
     * @param fileName
     */
    public void setAttribute(String root , String fileName )
    {
        this.root = root;
        if(!root.equalsIgnoreCase(""))
        {
            this.partition = "/";
        }
        this.fileName = fileName;
        this.clear();
        this.initPropertiesData();
    }
    /**
	 * 与えられたキーがＭａｐに存在するかを返す。
	 * @param key String
	 * @return boolean
	 */
	public boolean constainsKey(String key) {
		return this.map.containsKey(key);
	}
	/**
	 * データをMapから全削除する。
	 */
	public void clear() {
        if(!initilized)
        {
            this.map.clear();
            this.initilized = false;
        }
	}
    /**
     * 与えられたキーでStringデータを取得
     * @param key　String
     * @return
     */
    public String getStringProperties(String key)
    {
        return (String)map.get(key);
    }

    /**
     *  プロパティファイルからデータの設定
     *  プロパティファイルのデータが(String1,String2)の形ならkey=String1、data＝String2。
     *  プロパティファイルのデータが（String1）の形ならkey＝1～順番の番号、data＝String1。
     */
    public void initPropertiesData()
    {
        if (this.initilized) {
			return;
		}
        int keyNo = 1;
        this.map = new HashMap();

		try {

			System.out.println(this.root + this.partition + this.fileName);
			FileReader inputFileReader = new FileReader(this.root + this.partition + this.fileName);

            BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

			String tempString;

			while ((tempString = inputBufferedReader.readLine()) != null) {
				// 空行なら読み飛ばす。
				if (tempString.trim().length() == 0)
					continue;

				stcLog.getSysLog().debug(tempString); // 読込行ログ出力

				// 最初の半角スペースを除き、最初の文字が'#'ならばコメントとみなし読み飛ばす。
				if (tempString.trim().indexOf("#") == 0)
					continue;

				CSVTokenizer data = new CSVTokenizer(tempString);

				/*if ( data.countTokens() < 2 ) {
					continue;
				}*/
                String lfCode;
				String key = data.nextToken().trim();
                if(data.hasMoreTokens())
                {
				    lfCode = data.nextToken().trim();
                }
                else
                {
                    lfCode = "";
                }
                //プロパティファイルの形が　String1 の場合　keyデータに1～の番号を入れる。
                if(lfCode.equalsIgnoreCase(""))
                {
                     lfCode = key;
                     key = String.valueOf(keyNo);
                     keyNo ++;
                }

				/*int readByte = 0;
				try {
					readByte = Integer.parseInt(data.nextToken().trim());
				} catch (Exception e) {
				}*/

				this.map.put(key, lfCode);
			}
			inputFileReader.close();
		} catch(Exception e) {
			stcLog.getSysLog().fatal("InputProperties#initPropertiesData:" + e.toString());
			return;
		}
        this.initilized = true;
    }

    /**
     *  InputPropertiesの中身をSystem.outで出力します。
     */
    public void systemOutValues()
    {
          Set key = this.map.keySet();
          Collection value = this.map.values();
          Iterator iteKey = key.iterator();
          Iterator iteValue = value.iterator();

          System.out.println("InputProperties");
          System.out.println("root=" + this.root);
          System.out.println("fileName=" + this.fileName);
          System.out.println("initilized=" + this.initilized);
          System.out.println("Map");
          while(iteKey.hasNext())
          {
            String stringKey = String.valueOf(iteKey.next());
            String stringValue = String.valueOf(iteValue.next());
            System.out.println(stringKey + " = " + stringValue);
          }
    }
}