package mdware.common.batch.util.file.record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.csv.CSVTokenizer;
import mdware.common.util.StringUtility;
import mdware.common.util.properties.PropertyUtil;

/**
 * <p>タイトル: RecordHolder</p>
 * <p>説明: データファイルを読込みレコードを保持します。（ＣＳＶに対応するかどうか未定）</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.0 2014/06/23 Nghia-HT 海外LAWSON様UTF-8対応

 */
public class RecordHolder {
    private int errorRecordLength = 128; //指定されていないレコード区分が存在した場合に読み飛ばす文字バイト数
    private String filePath = null;
    private String fileKbn = null;
    private RecordKindProperties currentRecord = null;
    private List recordKindPropertiesList = null;
    private List recordList = null;
    private List noFoundRecordList = null;
    private String propertyDir = null;
    private Map recordItemMap = null;
    private List itemSeqNoList = null; //項目順番保持用リスト

    private String currentLineString = null;
    private int currentLineNo = 0;

    private RecordContents recordContents = null;

    private static final StcLog stcLog = StcLog.getInstance();

    /**
     * コンストラクタ
     * @param propertyDir プロパティファイル格納ディレクトリ
     * @param filePath 読込むファイルの絶対パス
     * @param fileKbn ファイル種類（プロパティファイルに指定されたもの）
     */
    public RecordHolder(String filePath, String fileKbn) {
    	//20061110 Shimatani mod Start
        //this.propertyDir = mdware.common.batch.util.properties.BatchProperty.propertiesDir;
    	this.propertyDir = PropertyUtil.getPropertiesDir();
    	//20061110 Shimatani mod End
        this.filePath = filePath;
        this.fileKbn = fileKbn;
        recordKindPropertiesList = new RecordKinds(fileKbn).getRecordKindPropertiesList();
        recordContents = new RecordContents(fileKbn);
    }

    /**
     * コンストラクタ
     * @param propertyDir プロパティファイル格納ディレクトリ
     * @param filePath 読込むファイルの絶対パス
     * @param fileKbn ファイル種類（プロパティファイルに指定されたもの）
     * @param errorRecordLength 指定されていないレコード区分が存在した場合に読み飛ばす文字バイト数　（行毎読みとばす場合は-1を指定して下さい。）
     */
    public RecordHolder(String filePath, String fileKbn, int errorRecordLength) {
        this(filePath, fileKbn);
        this.errorRecordLength = errorRecordLength;
    }

    /**
     * ファイルを読込み、内部にレコードデータとして保持します。
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ParseException
     */
    public void readLine(String lineString) throws Exception {
        recordList = new ArrayList();
        noFoundRecordList = new ArrayList();

        //一行ごとに読込んでレコードオブジェクトに変換する
        if (lineString.trim().length() != 0) {
            add(lineString);
        }
    }

    /**
     * ファイルを読込み、内部にレコードデータとして保持します。
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ParseException
     */
    public void readFile() throws IllegalArgumentException, IOException, ParseException {
        currentLineNo = 0;
        try {
            if (filePath == null || filePath.trim().length() == 0) {
                throw new IllegalArgumentException("指定されたファイルパスが不正です。");
            }

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            recordList = new ArrayList();

            String lineString;
            RecordProperties recordProperties = new Records().getRecordProperties(this.fileKbn);
            boolean isLineSepa = true;
            if (recordProperties != null) {
                String lf = recordProperties.getLfCode();
                if (lf.equals(RecordProperties.CODE_CR)
                    || lf.equals(RecordProperties.CODE_LF)
                    || lf.equals(RecordProperties.CODE_CRLF)) {
                    isLineSepa = true;
                } else {
                    isLineSepa = false;
                }
            }
            if (isLineSepa) {
                //一行ごとに読込んでレコードオブジェクトに変換する
                while ((lineString = bufferedReader.readLine()) != null) {
                    currentLineNo++;
                    this.currentLineString = lineString;
                    if (lineString.trim().length() != 0) {
                        add(lineString);
                    }
                }
            } else {
                if (recordProperties.getReadByte() <= 0) {
                    throw new Exception("設定ファイルが不正です。readByte = " + recordProperties.getReadByte());
                }
                int pos = 0;
                char[] buf = new char[recordProperties.getReadByte()];
                while (bufferedReader.read(buf, pos, recordProperties.getReadByte()) >= 0) {
                    currentLineNo++;
                    lineString = new String(buf);
                    this.currentLineString = lineString;
                    if (lineString.trim().length() != 0) {
                        add(lineString);
                    }
                    pos += recordProperties.getReadByte();
                }
            }
            fileReader.close();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (ParseException e) {
            throw new ParseException("ファイルフォーマットが不正です。", currentLineNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("ファイルフォーマットが不正です。", currentLineNo);
        }
    }

    /**
     *
     * @return
     */
    public int getCurrentLineNo() {
        return this.currentLineNo;
    }

    /**
     *
     * @return
     */
    public String getCurrentLineString() {
        return this.currentLineString;
    }

    /**
     * 内部レコード保持用ＭＡＰにRecordKindPropertiesオブジェクトを追加します。
     * @param lineString
     */
    private void add(String lineString) throws Exception {
        int curRecPointer = 0;
        try {
            while (true) {
                Iterator iteKind = recordKindPropertiesList.iterator();
                int recKindCount = recordKindPropertiesList.size(); //レコード種類の数
                boolean recFound = false;
                while (iteKind.hasNext()) {
                    RecordKindProperties recKind = (RecordKindProperties) iteKind.next();
                    String recordKbn = recKind.getRecordKbn();
                    if (recKindCount == 1 && recordKbn.equals("")) { //レコード種類が1つしかない場合、無条件に読込む
                        recordList.add(recKind.createCloneRecord(lineString));
                        break;
                    }
                    String recordMode = recKind.getRecordMode();
                    String recordString = null;
                    //レコード形式によって処理を分ける
                    if (recordMode.equalsIgnoreCase("fix")) { //固定長
                        if (recKind.getRecordLength() <= lineString.getBytes().length) {
                            //CSVを固定長として読込もうとするとエラーが起こるので抜ける
                            recordString = getItem(lineString, curRecPointer, recKind.getRecordLength());
                            //あるレコードは必ず一意となるレコード区分よりスタートするという前提
                            if (recordString.startsWith(recordKbn)) {
                                String recIte = recKind.getIteration();
                                if (!recIte.equals("0")) {
                                    recordList.add(recKind.createCloneRecord(recordString));
                                }
                                curRecPointer += recKind.getRecordLength();
                                if (curRecPointer >= lineString.getBytes().length) {
                                    return;
                                }
                                recFound = true;
                            }
                        }
                    } else if (recordMode.equalsIgnoreCase("csv")) { //可変長
                        recordString = lineString;
                        CSVTokenizer csvData = new CSVTokenizer(recordString);
                        //あるレコードは必ず一意となるレコード区分よりスタートするという前提
                        if (csvData.nextToken().trim().equals(recordKbn)) {
                            String recIte = recKind.getIteration();
                            if (!recIte.equals("0")) {
                                recordList.add(recKind.createCloneRecord(recordString));
                            }
                            //CSVモードのときは抜ける
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if (!recFound) { //該当するレコード区分がなかった場合
                    stcLog.getLog().debug("該当するレコード区分が存在しませんでした。読み飛ばします。");
                    if (this.errorRecordLength < 0) { //errorRecordLength < 0の場合は抜ける
                        return;
                    }
                    this.noFoundRecordList.add(getItem(lineString, curRecPointer, this.errorRecordLength));
                    curRecPointer += this.errorRecordLength;
                    if (curRecPointer > lineString.getBytes().length - 1) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            if (this.errorRecordLength < 0) { //errorRecordLength < 0の場合は抜ける
                return;
            }
            throw e;
        }
    }

    /**
     * レコード文字列を解析し、ＭＡＰオブジェクトに項目情報を保持します。
     * @param recordKindProperties レコード文字列が格納されたRecordKindPropertiesクラスオブジェクト
     * @throws ParseException
     */
    public void parseRecord(RecordKindProperties recordKindProperties) throws ParseException {
        if (recordItemMap != null) {
            recordItemMap.clear();
        }
        recordItemMap = new HashMap(); //レコード項目値格納用ＭＡＰ
        itemSeqNoList = new ArrayList(); //レコード項目名連番格納用リスト
        String recordString = recordKindProperties.getRecordString(); //レコード文字列

        currentRecord = recordKindProperties;

        if (recordString == null || recordString.trim().length() == 0) {
            throw new ParseException("解析するレコードがありません。", 0);
        }

        //*** レコード文字列から項目を取り出していく（プロパティファイルが正しいことが大前提！！！） ***//
        recordContents.setRecordKind(recordKindProperties);
        int curRecPointer = 0;
        try {
            CSVTokenizer csvData = new CSVTokenizer(recordString); //ＣＳＶ解析用
            for (int i = 0; i < recordContents.size(); i++) {
                RecordContentsProperties recContProp = recordContents.get(i);
                RecordContentsProperties newRecProp = recContProp.createClone();
                int itemLength = recContProp.getLength();
                //レコードの形式によって分ける
                String recordMode = recordKindProperties.getRecordMode();
                if (recordMode.equalsIgnoreCase("fix")) { //固定長
                    newRecProp.setItemValue(getItem(recordString, curRecPointer, itemLength));
                    curRecPointer += itemLength;
                } else if (recordMode.equalsIgnoreCase("csv")) { //可変長
                    newRecProp.setItemValue(csvData.nextToken().trim()); //スペース付加にしようと思ったが右詰か左詰か分からないのでやめる
                    curRecPointer++;
                } else {
                    throw new ParseException("解析するレコード形式が指定されていません。プロパティファイルの不備です。", 0);
                }
                recordItemMap.put(newRecProp.getItemCode(), newRecProp);
                itemSeqNoList.add(newRecProp.getItemCode()); //読込んだ順にＡＤＤ
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("解析するレコードが不正か、プロパティファイルの内容が不正です。", curRecPointer);
        }
    }

    /**
     * Beanオブジェクトを返します。必ずparseRecordメソッドを実行してから使用して下さい。
     * @param map
     * @return
     */
    public Object getBean() {
        Object bean = null;
        Class beanClass = null;
        try {
            beanClass = Class.forName(this.currentRecord.getUseBeanName());
            bean = beanClass.newInstance();
        } catch (Exception e) {
            return null;
        }

        Iterator ite = this.itemSeqNoList.iterator();
        for (int i = 0; i < itemSeqNoList.size(); i++) {
            //順にデータを取り出す
            RecordContentsProperties content = this.getRecordContentsProperties(i);

            String paramDM = content.getParameterDM();
            StringTokenizer st = new StringTokenizer(paramDM, "_");
            String methodName = "set";
            while (st.hasMoreTokens()) { //paramDMよりBeanのセッターメソッド名を取得する
                methodName += getHeadUpString(st.nextToken());
            }
            String value = content.getItemValue();
            //20031104 @UPDATE 符号数値対応 start
            if (content.getType().equals(content.TYPE_NUMBER) || content.getType().equals(content.TYPE_SIGN_NUMBER)) {
                //			if (content.getType().equals(content.TYPE_NUMBER)) {
                //20031104 @UPDATE 符号数値対応 end
                int scale = content.getScale();
                if (scale > 0) {
                    value = getDoubleString(value, scale);
                } else {
                    value = String.valueOf(getLong(value));
                }
            }
            try {
                Class stringClass = Class.forName("java.lang.String");
                java.lang.reflect.Method setMethod = beanClass.getMethod(methodName, new Class[] { stringClass });
                //setXXXX()の取得
                setMethod.invoke(bean, new Object[] { value }); //全てString型でセットする。
            } catch (Exception e) {}
        }

        //値をセットしたBeanを返す
        return bean;
    }

    /**
     * 引数がString型であるとし、その文字列を返します。
     * @param obj
     * @return
     */
    private String getString(Object obj) {
        if (obj == null) {
            return "";
        }
        return (String) obj;
    }

    /**
     * 引数がString型であるとし、その数値を返します。
     * @param obj
     * @return
     */
    private long getLong(Object obj) {
        if (obj == null) {
            return 0;
        }
        try {
            return Long.parseLong((String) obj);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 引数がString型であるとし、その実数値を返します。
     * @param obj
     * @return
     */
    private double getDouble(Object obj) {
        String str = getString(obj);
        if (str.trim().length() == 0) {
            return 0.0;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 引数がString型でかつ小数点なしの文字列であるとし、小数点を追加した実数値文字列を返します。
     * @param obj
     * @param scale 小数点以下桁数
     * @return
     */
    private String getDoubleString(Object obj, int scale) {
        String str = getString(obj).trim();
        if (str.length() <= scale) {
            return "";
        } else {
            return str.substring(0, str.length() - scale) + "." + str.substring(str.length() - scale);
        }
    }

    /**
     * Beanを使用してレコード情報を作成します。
     *  2003.01.08 @UPD recordKind.propertiesのBean名を使用してオブジェクトをインスタンス化するか、引数のBeanから直接インスタンス化
     *                  するかを選択できるように変更
     * @param recordKindProperties
     * @param bean
     * @param useProperty true:recordKind.propertiesのBean名を使用 false:引数のBeanから直接インスタンス化
     */
    public void parseBean(RecordKindProperties recordKindProperties, Object bean, boolean useProperty) {
        if (recordItemMap != null) {
            recordItemMap.clear();
        }
        recordItemMap = new HashMap(); //レコード項目値格納用ＭＡＰ
        itemSeqNoList = new ArrayList(); //レコード項目名連番格納用リスト

        currentRecord = recordKindProperties;

        recordContents.setRecordKind(recordKindProperties);
        for (int i = 0; i < recordContents.size(); i++) {
            RecordContentsProperties recContProp = recordContents.get(i);

            //ＤＢパラメータ（ＤＢカラム名）を使ってBeanよりデータを取得する
            String paramDM = recContProp.getParameterDM();
            StringTokenizer st = new StringTokenizer(paramDM, "_");
            String methodName = "get";
            while (st.hasMoreTokens()) { //paramDMよりBeanのゲッターメソッド名を取得する
                methodName += getHeadUpString(st.nextToken());
            }

            String value = recContProp.getItemValue();
            if (paramDM.trim().length() > 0) {
                try {
                    //2004.01.08 @UPD yamanaka usePropertyによって変化
                    //					Class beanClass = Class.forName(recordKindProperties.getUseBeanName());
                    Class beanClass = null;
                    if (useProperty) {
                        beanClass = Class.forName(recordKindProperties.getUseBeanName());
                    } else {
                        beanClass = bean.getClass();
                    }

                    //20030112 @UPD yamanaka
                    Object obj = beanClass.getMethod(methodName, null).invoke(bean, null); //getXXXX()の実行
                    if (obj != null) {
                        value = String.valueOf(obj);
                    }
                } catch (Exception e) {}
            }

            if (value != null) {} else {
                value = "";
            }
            //20030116 @ADD parseBeanの時点でレコード情報を全てセットしておく start
            if (recContProp.getType().equalsIgnoreCase(recContProp.TYPE_CHAR)) { //文字列なので空白付加
                value = StringUtility.adjustStringWithSpace(value, recContProp.getLength(), !recContProp.isLeft());
                //20031104 @UPDATE 符号数値対応 start
                //			} else { //数値の場合
            } else if (recContProp.getType().equalsIgnoreCase(recContProp.TYPE_NUMBER)) {
                //20031104 @UPDATE 符号数値対応 end
                int scale = recContProp.getScale();
                try {
                    StringTokenizer st2 = new StringTokenizer(value, ".");
                    String intPart =
                        StringUtility.adjustStringWithZero(st2.nextToken().trim(), recContProp.getLength() - scale);
                    if (scale > 0) {
                        if (st2.hasMoreTokens()) {
                            String scalePart = st2.nextToken().trim();
                            scalePart =
                                StringUtility.cutString(StringUtility.charFormat(scalePart, scale, "0", false), scale);
                            intPart += scalePart;
                        } else {
                            intPart += StringUtility.zeroFormat("", scale);
                        }
                    }
                    value = intPart;
                } catch (Exception e) {
                    value = StringUtility.adjustStringWithZero("", recContProp.getLength());
                }

                //20031104 @ADD 符号数値対応 start
            } else if (recContProp.getType().equalsIgnoreCase(recContProp.TYPE_SIGN_NUMBER)) {
                //符号数値の場合

                int scale = recContProp.getScale();
                try {
                    boolean minus = false;

                    //ﾏｲﾅｽ符号がついていたら...
                    if (value.startsWith("-")) {
                        minus = true;
                        if (!(recContProp.getLength() > 1)) {
                            throw new Exception(); //"-"を付ける為に桁数は2桁以上ないといけない
                        }
                        value = value.substring(1, value.length()); //符号を除く
                    }

                    StringTokenizer st2 = new StringTokenizer(value, ".");
                    String intPart =
                        StringUtility.adjustStringWithZero(st2.nextToken().trim(), recContProp.getLength() - 1 - scale);
                    if (scale > 0) {
                        if (st2.hasMoreTokens()) {
                            String scalePart = st2.nextToken().trim();
                            scalePart =
                                StringUtility.cutString(StringUtility.charFormat(scalePart, scale, "0", false), scale);
                            intPart += scalePart;
                        } else {
                            intPart += StringUtility.zeroFormat("", scale);
                        }
                    }

                    if (minus) {
                        value = "-" + intPart;
                    } else {
                        value = "0" + intPart;
                    }
                } catch (Exception e) {
                    value = StringUtility.adjustStringWithZero("", recContProp.getLength());
                }
                //20031104 @ADD 符号数値対応 end
                //20041224 @ADD ポスフール独自仕様+符号数値対応 start
            } else if (recContProp.getType().equalsIgnoreCase(recContProp.TYPE_PLUS_SIGN_NUMBER)) {
                //符号数値の場合

                int scale = recContProp.getScale();
                try {
                    boolean minus = false;

                    //ﾏｲﾅｽ符号がついていたら...
                    if (value.startsWith("-")) {
                        minus = true;
                        if (!(recContProp.getLength() > 1)) {
                            throw new Exception(); //"-"を付ける為に桁数は2桁以上ないといけない
                        }
                        value = value.substring(1, value.length()); //符号を除く
                    }

                    StringTokenizer st2 = new StringTokenizer(value, ".");
                    String intPart =
                        StringUtility.adjustStringWithZero(st2.nextToken().trim(), recContProp.getLength() - 1 - scale);
                    if (scale > 0) {
                        if (st2.hasMoreTokens()) {
                            String scalePart = st2.nextToken().trim();
                            scalePart =
                                StringUtility.cutString(StringUtility.charFormat(scalePart, scale, "0", false), scale);
                            intPart += scalePart;
                        } else {
                            intPart += StringUtility.zeroFormat("", scale);
                        }
                    }

                    if (minus) {
                        value = "-" + intPart;
                    } else {
                        value = "+" + intPart;
                    }
                } catch (Exception e) {

                    value = "+" + StringUtility.adjustStringWithZero("", recContProp.getLength() -1 );
                }
                //20041226 @ADD ポスフール独自仕様 符号数値対応 end

            }
            //20061109 Shimatani スペースカットするCAHR形を追加 add Start
            else if (recContProp.getType().equalsIgnoreCase(RecordContentsProperties.TYPE_SPC_CHAR)) { //文字列なので空白付加
            	if(value != null)
            	{
            		value = value.trim();
            	}
            }
            //20061109 Shimatani スペースカットするCAHR形を追加 add End

            recContProp.setItemValue(value);
            //20030116 @ADD parseBeanの時点でレコード情報を全てセットしておく end

            RecordContentsProperties newRecProp = recContProp.createClone();

            recordItemMap.put(newRecProp.getItemCode(), newRecProp);
            itemSeqNoList.add(newRecProp.getItemCode()); //読込んだ順にＡＤＤ

            //2004/04/26 add takata
            //ItemValueを初期化しておく
            recContProp.setItemValue((String) null);
        }
    }

    /**
     * Beanを使用してレコード情報を作成します。
     *  2003.01.08 @ADD recordKind.propertiesのBean名を使用してオブジェクトをインスタンス化します。従来の使い方に変更ありません。
     * @param recordKindProperties
     * @param bean
     */
    public void parseBean(RecordKindProperties recordKindProperties, Object bean) {
        parseBean(recordKindProperties, bean, true);
    }

    /**
     * 新規のレコード情報を作成します。ファイル解析を行わない場合に使用できます。
     * @param recordKindProperties
     */
    public void createEmptyRecord(RecordKindProperties recordKindProperties) {
        if (recordItemMap != null) {
            recordItemMap.clear();
        }
        recordItemMap = new HashMap(); //レコード項目値格納用ＭＡＰ
        itemSeqNoList = new ArrayList(); //レコード項目名連番格納用リスト

        currentRecord = recordKindProperties;

        recordContents.setRecordKind(recordKindProperties);
        Iterator ite = recordContents.getRecordContentsPropertiesList().iterator();
        while (ite.hasNext()) {
            RecordContentsProperties recContProp = (RecordContentsProperties) ite.next();
            //20030116 @ADD createEmptyRecordの時点でレコード情報を全てセットしておく start
            String value = recContProp.getItemValue();
            if (recContProp.getType().equalsIgnoreCase(recContProp.TYPE_CHAR)) { //文字列なので空白付加
                value = StringUtility.adjustStringWithSpace(value, recContProp.getLength(), !recContProp.isLeft());
            } else { //数値の場合
                int scale = recContProp.getScale();
                try {
                    StringTokenizer st2 = new StringTokenizer(value, ".");
                    String intPart =
                        StringUtility.adjustStringWithZero(st2.nextToken().trim(), recContProp.getLength() - scale);
                    if (scale > 0) {
                        if (st2.hasMoreTokens()) {
                            String scalePart = st2.nextToken().trim();
                            scalePart =
                                StringUtility.cutString(StringUtility.charFormat(scalePart, scale, "0", false), scale);
                            intPart += scalePart;
                        } else {
                            intPart += StringUtility.zeroFormat("", scale);
                        }
                    }
                    value = intPart;
                } catch (Exception e) {
                    value = StringUtility.adjustStringWithZero("", recContProp.getLength());
                }
            }

            recContProp.setItemValue(value);
            //20030116 @ADD parseBeanの時点でレコード情報を全てセットしておく end
            recordItemMap.put(recContProp.getItemCode(), recContProp);
            itemSeqNoList.add(recContProp.getItemCode());
        }
    }

    /**
     * 新規のレコード情報を作成します。ファイル解析を行わない場合に使用できます。
     * @param recordKind レコード区分文字列
     */
    public void createEmptyRecord(String recordKind) {
        createEmptyRecord(this.getRecordKindProperties(recordKind));
    }

    /**
     * 最初の1文字のみを大文字にした文字列を返します。
     * @param str
     * @return
     */
    private String getHeadUpString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * レコード文字列に含まれるデータ項目数を返します。
     * @return
     */
    public int getRecordItemCount() {
        if (recordItemMap == null) {
            return 0;
        };

        return recordItemMap.size();
    }

    /**
     * レコード文字列に含まれるデータ項目情報を返します。
     * @return データ項目情報が格納されたＭＡＰオブジェクト
     */
    public Map getRecordItemMap() {
        return recordItemMap;
    }

    /**
     * データ項目コードを返します。
     * @param seqNo recordContents.propertiesファイル内に記述された項目で指定レコード種別内の順番
     * @return
     */
    public String getItemCode(int seqNo) {
        if (recordItemMap == null) {
            return null;
        };

        if (seqNo < 0 || seqNo > getRecordItemCount() - 1) {
            return null;
        }

        return (String) itemSeqNoList.get(seqNo); //項目リストよりキーを連番で取り出す
    }

    /**
     * レコードデータ項目内容情報を返します。
     * @param itemCode データ項目コード（キー）
     * @return RecordContentsPropertiesオブジェクト
     */
    public RecordContentsProperties getRecordContentsProperties(String itemCode) {
        if (recordItemMap == null) {
            return null;
        };

        return (RecordContentsProperties) recordItemMap.get(itemCode);
    }

    /**
     * レコードデータ項目内容情報を返します。
     * @param seqNo recordContents.propertiesファイル内に記述された項目で指定レコード種別内の順番
     * @return RecordContentsPropertiesオブジェクト
     */
    public RecordContentsProperties getRecordContentsProperties(int seqNo) {
        String itemCode = getItemCode(seqNo); //項目リストよりキーを連番で取り出す

        if (itemCode == null) {
            return null;
        };

        return getRecordContentsProperties(itemCode);
    }

    /**
     * レコードデータ項目内容情報をリストにして返します。
     * @return RecordContentsPropertiesのリスト
     */
    public List getRecordContentsList() {
        List list = new ArrayList();
        for (int i = 0; i < this.itemSeqNoList.size(); i++) {
            list.add(getRecordContentsProperties(i));
        }
        return list;
    }

    /**
     * レコードデータ項目値を返します。
     * @param itemCode データ項目コード（キー）
     * @return
     */
    public String getItemValue(String itemCode) {
        RecordContentsProperties recContProp = getRecordContentsProperties(itemCode);
        if (recContProp == null) {
            return null;
        }
        return recContProp.getItemValue();
    }

    /**
     * レコードデータ項目値を返します。
     * @param seqNo recordContents.propertiesファイル内に記述された項目で指定レコード種別内の順番
     * @return
     */
    public String getItemValue(int seqNo) {
        RecordContentsProperties recContProp = getRecordContentsProperties(seqNo);
        if (recContProp == null) {
            return null;
        }
        return recContProp.getItemValue();
    }

    /**
     * レコード項目情報保持ＭＡＰにデータをセットします。
     * @param itemCode データ項目コード（キー）
     * @param itemValue セットする値文字列
     */
    public void setItemValue(String itemCode, String itemValue) {
        RecordContentsProperties recContProp = this.getRecordContentsProperties(itemCode);

        if (itemValue != null) {
            recContProp.setItemValue(itemValue);
        }

        //レコード項目用ＭＡＰオブジェクトにセット
        this.recordItemMap.put(itemCode, recContProp);
    }

    /**
     * レコード項目情報よりデータを1つに繋げたレコード文字列を返します。
     * @return
     */
    public String getRecordString() {
        StringBuffer recordString = new StringBuffer("");

        boolean isCsv = this.currentRecord.getRecordMode().equalsIgnoreCase("csv"); //csvかどうか
        boolean isTsv = this.currentRecord.getRecordMode().equalsIgnoreCase("tsv"); //csvかどうか

        //値をレコード文字列用に調整する。
        Iterator ite = this.itemSeqNoList.iterator();
        for (int i = 0; i < itemSeqNoList.size(); i++) {
            RecordContentsProperties recContProp = this.getRecordContentsProperties(i);
            String value = recContProp.getItemValue();
            if (i > 0) {
                if (isCsv) {
                    recordString.append(","); //区切文字はコンマ
                } else if (isTsv) {
                    recordString.append("\t"); //区切文字はタブ
                }
            }
            recordString.append(value);
        }
        return recordString.toString();
    }

    /**
     * 文字列から部分文字列をバイト指定で抜き取ります。エラーがあった場合はスペースが返ります。
     * @param record 指定文字列
     * @param start 抜き取り開始バイト数
     * @param length 抜き取る文字列のバイト数
     * @return
     */
    private static String getItem(String record, int start, int length) {
        byte[] recBytes = record.getBytes();
        byte[] itemBytes = new byte[length];
        for (int i = start; i < start + length; i++) {
            itemBytes[i - start] = recBytes[i];
        }
        try {
            String newStr = new String(itemBytes, "UTF-8");
            if (newStr.getBytes().length < length) {
                newStr = StringUtility.spaceFormat(newStr, length);
            }
            return newStr;
        } catch (Exception e) {
            return StringUtility.spaceFormat("", length);
        }
    }

    /**
     * 指定されていないレコード区分が存在した場合に読み飛ばす文字バイト数を指定します。デフォルト値は128です。
     * その行自体読み飛ばしたい場合は-1を指定して下さい。
     * @param errorRecordLength
     */
    public void setErrorRecordLength(int errorRecordLength) {
        this.errorRecordLength = errorRecordLength;
    }

    /**
     * レコード情報リストを返します。
     * @return RecordKindPropertiesクラスオブジェクトのリスト
     */
    public List getRecordList() {
        return recordList;
    }

    /**
     * 行読込みを行った際にレコード区分が存在しなかったものをリストで返します。
     * @return 読込めなかった行文字列のリスト
     */
    public List getNoFoundRecordList() {
        return noFoundRecordList;
    }

    /**
     * RecordKindPropertiesのリストを返します。
     * @return
     */
    public List getRecordKindPropertiesList() {
        return recordKindPropertiesList;
    }

    /**
     * RecordKindPropertiesオブジェクトを返します。
     * @param seqNo
     * @return RecordKindProperties
     */
    public RecordKindProperties getRecordKindProperties(int seqNo) {
        return (RecordKindProperties) recordKindPropertiesList.get(seqNo);
    }

    /**
     * RecordKindPropertiesオブジェクトを返します。
     * @param recordKind
     * @return RecordKindProperties
     */
    public RecordKindProperties getRecordKindProperties(String recordKind) {
        for (int i = 0; i < recordKindPropertiesList.size(); i++) {
            RecordKindProperties record = getRecordKindProperties(i);
            if (record.getRecordKind().equalsIgnoreCase(recordKind.trim())) {
                return record;
            }
        }
        return null;
    }

    /**
     * 直前で使用した（parseRecord,parseBean,createEmptyRecordの何れかで）RecordKindPropertiesクラスオブジェクトを返します。
     * @return RecordKindProperties
     */
    public RecordKindProperties getRecordKindProperties() {
        return this.currentRecord;
    }
}
