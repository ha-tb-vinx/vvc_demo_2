package mdware.common.batch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import jp.co.vinculumjapan.stc.util.csv.CSVTokenizer;
import mdware.batch.filedb.exception.FileDBConvertException;
import mdware.batch.retailif.RetailInterface;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.bean.SystemConfBean;
import mdware.common.dictionary.SyoriJoukyouSt;
import mdware.common.util.FunctionBeanHolder;
import mdware.common.util.StringUtility;
import mdware.common.util.SystemConfBeanHolder;

/**
 * <p>タイトル：集信処理抽象クラス</p>
 * <p>説明：集信処理のテンプレートクラスです</p>
 * <p>集信処理を実装するクラスはこのクラスを継承してください</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 李武勇
 * @version 1.00 2005/06/13 新規作成<BR>
 * @version 1.01 2005/07/08 李武勇	障害報告書No.763対応<BR>
 * @version 1.02 2005/07/12 李武勇	障害報告書No.770対応<BR>
 * @version 1.03 2005/07/13 李武勇	障害報告書No.774対応<BR>
 * 	１．AbstractFileReceiveの機能がＷＥＢから呼ばれるバッチファイルに合わない部分がありまして、作成することになりました。
 * 	２．DMファイルを使わないFileReceive処理の場合、継承します。
 * 	３．AbstractMasterLoadingの一部の機能を追加しました。(setShareDirCollectTx())
 */
public abstract class AbstractFileReceiveWithoutBean extends AbstractDistribute {

	//プロパティファイルの経路
	private static final String RECORD_CONTENTS_PROPETIES_FILE = "rbsite/record/recordContents.properties";

	//ラインのサイズ
	protected int LINE_LENGTH = 0;
	//ログを書く為にファイルから行ナンバーを取得。
	protected int cnt = 0;
	//エラー件数を取得する為の変数。
	protected int errCnt = 0;
	//入力エラーがあるか判断する為のフラグ。
	protected boolean hasErr = false;

	//2005/07/08 障害報告No.763 李武勇 Start
	//HasErrという変数を制御する変数
	protected boolean setFalseForHasErr = false;
	//2005/07/08 障害報告No.763 李武勇 End

	//エラーログ書き込み用文字列
	protected String LENGTH_ERR = "長さが不正なレコードです。";
	//Exception処理の為のtemp変数。
	protected String strForException = "";
	//インサートする明細データ。
	protected List bodyLine = null;
	//明細データを格納するリスト。file_head_nb
	protected List bodyBox = new ArrayList();
	//
	protected String shareDir = "";
	//
	protected SystemConfBeanHolder sysBh = null;
	//子クラスから呼んできたファイルのライン。
	protected String line = "";
	//
	protected String file_head_nb = "";
	//
	protected String haisinsaki_cd = "";

	//substringの処理が多すぎる場合、臨時的にその値を格納する為のtemp変数。
	private String value = "";
	//データが入っているファイルのPATH。
	private String filePath = "";
	//
	private boolean isBreak = false;
	//
	private boolean isKaigyoNasi = false;
	//インデックスマップ
	private Map indexMap = new HashMap();

	/**
	 * コンストラクタ<BR>
	 */
	protected AbstractFileReceiveWithoutBean() {
		super();
	}

	/**
	 * FilePathをセッティングする。
	 * @param str FilePath
	 */
	protected void setFilePath(String server_file_na) {
		this.filePath = server_file_na;
	}

	/**
	 * テーブルをトランケートします
	 * @param tableName トランケートするテーブル名
	 * @throws Exception
	 */
	protected String getFilePath() {
		if (this.filePath.trim().equals("")) {
			batchLog.getLog().error(getBatchID(), getBatchNa(), "メソッド【setFilePath】が実行されたか確認してください。");
		}
		return this.filePath;
	}

	/**
	 * テーブルをトランケートします
	 * @param tableName トランケートするテーブル名
	 * @throws Exception
	 */
	protected void setKaigyoNasi() {
		this.isKaigyoNasi = true;
	}

	/**
	 * テーブルをトランケートします
	 * @param tableName トランケートするテーブル名
	 * @throws Exception
	 */
	protected void trancateTable(String tableName) throws SQLException {
		String sql = "truncate table " + tableName;
		this.dataBase.executeUpdate(sql);
	}

	/**
	 * 集信ファイルのファイルパスを取得します
	 * @param dataKind データ種別コード
	 * @return 集信ファイルパス
	 */
	protected String getInputFilePath(String dataKind) {
		SystemConfBean bean = new SystemConfBeanHolder().getSysConfBean();

		String inputFileDir = bean.getShareDirCollectTx(); //集信用ディレクトリ
		String inputFileName = new FunctionBeanHolder().getIfFileHeadName(dataKind) + RetailInterface.SUFFIX_DATA;

		//機能ﾏｽﾀより取得
		String inputFilePath = inputFileDir + "/" + inputFileName;
		batchLog.getLog().info(this.getBatchID(), this.getBatchNa(), "集信ファイルパス：" + inputFilePath);

		if (!new File(inputFilePath).exists()) {
			return null;
		}

		return inputFilePath;
	}

	/**
	 * 文字列(YYYYMMDD)が日付として有効かチェックする。
	 * @param  cnt	ファイルでの行数
	 * @param  strDate  入力文字列 YYYYMMDD
	 * @param  line  ファイル行の内容
	 * @return true：有効 false：無効 
	 */
	protected boolean isDate(String[] target) {

		int intYear;
		int intMonth;
		int intDay;

		if ("".equals(value)) {
			return false;
		}

		// 文字列が日付のフォーマットか？
		if (!Pattern.compile("^[0-9]{4}[0-9]{2}[0-9]{2}$").matcher(value).find()) {
			this.writeErrorRecord(cnt, target[0] + "が数値ではありません。【" + value + "】", line);
			return false;
		}

		// 年(YYYY)を取得		                                  
		intYear = java.lang.Integer.parseInt(value.substring(0, 4));

		// 月(MM)を取得
		intMonth = java.lang.Integer.parseInt(value.substring(4, 6));

		// 日(DD)を取得
		intDay = java.lang.Integer.parseInt(value.substring(6, 8));

		Calendar cal = new GregorianCalendar();
		cal.setLenient(false);
		cal.set(intYear, intMonth - 1, intDay);

		try {
			java.util.Date ud = cal.getTime();
		} catch (IllegalArgumentException iae) {
			this.writeErrorRecord(cnt, target[0] + "が日付ではありません。【" + value + "】", line);
			return false;
		}
		return true;
	}

	/**
	 * 文字列(HHMI24SS)が時間として有効かチェックする。
	 * @param  cnt	ファイルでの行数
	 * @param  strTime  入力文字列 HHMI24SS
	 * @param  line  ファイル行の内容
	 * @return true：有効 false：無効 
	 */
	protected boolean isTime(String[] target) {

		int intHour;
		int intMinute;
		int intSecond;

		if ("".equals(value)) {
			// 空文字なので判定しない
			return true;
		}

		// 文字列が日付のフォーマットか？
		if (!Pattern.compile("^[0-9]{2}[0-9]{2}[0-9]{2}$").matcher(value).find()) {
			this.writeErrorRecord(cnt, target[0] + "が数値ではありません。【" + value + "】", line);
			return false;
		}

		// 年(YYYY)を取得
		intHour = java.lang.Integer.parseInt(value.substring(0, 2));

		// 月(MM)を取得
		intMinute = java.lang.Integer.parseInt(value.substring(2, 4));

		// 日(DD)を取得
		intSecond = java.lang.Integer.parseInt(value.substring(4, 6));

		if (intHour < 0 || intHour > 24 || intMinute < 0 || intMinute > 60 || intSecond < 0 || intSecond > 60) {
			this.writeErrorRecord(cnt, target[0] + "が正しい時間ではありません。【" + value + "】", line);
			return false;
		}
		return true;
	}

	/**
	 * 入力値が数値かチェックする。
	 * @param  cnt	ファイルでの行数
	 * @param  target	検査する項目
	 * @param  value  入力値
	 * @param  line  ファイル行の内容
	 * @return true：有効 false：無効 
	 */
	protected boolean isNumeric(String[] target) {

		if ("".equals(value)) {
			return false;
		}

		boolean result = false;

		if (("-".equals(value.substring(0, 1))) || ("+".equals(value.substring(0, 1)))) {
			result = Pattern.compile("^[0-9]++[\\.]?0*$").matcher(value.substring(1)).find();
		} else {
			result = Pattern.compile("^[0-9]++[\\.]?0*$").matcher(value).find();
		}

		if (!result) {
			this.writeErrorRecord(cnt, target[0] + "が数値でありません。【" + value + "】", line);
		}
		return result;
	}

	/**
	 * 入力値がパラメータと同じ値か判断する
	 * @param  target	String[]
	 * @return true：有効 false：無効 
	 */
	protected boolean equals(String[] target) {

		//入力値、パラメータが同じ値だったら、'true'をリターン。
		if (value.equals(target[1])) {
			return true;
		}

		//項目名がブランクではなかったら、ログを書く。
		if (!target[0].trim().equals("")) {
			this.writeErrorRecord(cnt, target[0] + "の値が不正です。【" + value + "】", line);
		}

		return false;
	}

	/**
	 * 入力値が比較される値と違ったら、'true'になる
	 * @param  target	検査する項目
	 * @return true：有効 false：無効 equalsIn
	 */
	protected boolean notEquals(String[] target) {

		if (value.equals(target[1])) {

			//項目名がブランクではなかったら、ログを書く。
			if (!target[0].trim().equals("")) {
				this.writeErrorRecord(cnt, target[0] + "の値が不正です。【" + value + "】", line);
			}

			return false;
		}
		return true;
	}

	/**
	 * 入力値が数値かチェックする。
	 * @param  target	検査する項目
	 * @return true：有効 false：無効 equalsIn
	 */
	protected boolean equalsIn(String[] target) {

		boolean result = false;

		for (int i = 1; i < target.length; i++) {
			if (value.equals(target[i])) {
				result = true;
			}
		}
		if (!result) {

			//項目名がブランクではなかったら、ログを書く。
			if (!target[0].trim().equals("")) {
				this.writeErrorRecord(cnt, target[0] + "の値が不正です。【" + value + "】", line);
			}

		}
		return result;
	}

	/**
	 * 現在、処理しているバリューをアップデートします。
	 * @param  target	{項目名, アップデートする(変換された)値}
	 * @return true：有効 false：エラー
	 */
	protected boolean updateValue(String modifiedValue) {

		//現在の値をアップデートする。
		try {
			this.value = modifiedValue;
		} catch (Exception e) {
			e.printStackTrace();
			batchLog.getLog().error(getBatchID(), getBatchNa(), "メソッドupdateValue(String[] target)で予期せぬエラーが発生しました。");
			return false;
		}
		return true;
	}

	/**
	 * 現在、処理しているバリューをリターンします。
	 * @return true：現在のバリュー
	 */
	protected String getItemValue() {
		//現在の値をリターンする。
		return this.value;
	}

	private List getRecordContentsPropertiesList(String recordKindParam) {

		/** recordContentsMap */
		Map recordContentsMap = null;
		List recordContentsPropertiesList = new ArrayList();

		//各要素を初期化する
		this.indexMap.clear(); // = new HashMap();
		int beginIndex = 0;
		int endIndex = 0;
		int totalValue = 0;

		String fileKbn = getFileKb();
		String fileName =
			mdware.common.batch.util.properties.BatchProperty.propertiesDir + "/" + RECORD_CONTENTS_PROPETIES_FILE;

		FileReader inputFileReader = null;
		try {
			inputFileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

		String tempString;
		int i = 0;

		try {
			while ((tempString = inputBufferedReader.readLine()) != null) {

				recordContentsMap = new HashMap();

				// 空行なら読み飛ばす。
				if (tempString.trim().length() == 0) {
					continue;
				}

				// 最初の半角スペースを除き、最初の文字が'#'ならばコメントとみなし読み飛ばす。
				if (tempString.trim().indexOf("#") == 0) {
					continue;
				}

				CSVTokenizer data = new CSVTokenizer(tempString);

				if (data.countTokens() < 13) {
					continue;
				}

				String fileKbn_w = data.nextToken().trim();
				String recordKind = data.nextToken().trim();

				if (!fileKbn_w.equals(fileKbn)) {
					continue;
				}

				if (null != recordKindParam) {
					//recordKindがヌルではなく、同じ値でもない場合、PATH。
					if (!recordKind.equals(recordKindParam)) {
						continue;
					}
				}

				String itemCode = data.nextToken().trim();
				String lenScaleStr = data.nextToken().trim();
				StringTokenizer st = new StringTokenizer(lenScaleStr, ".");
				int scale = 0;
				if (st.hasMoreTokens()) {
					scale = Integer.parseInt(st.nextToken());
				}
				String parameterDM = data.nextToken().trim();
				String type = data.nextToken().trim();
				String align = data.nextToken().trim();
				String preValue = data.nextToken().trim();
				String addList = data.nextToken().trim();
				String chkMethod = data.nextToken().trim();
				String param = data.nextToken().replaceAll("	", "");
				//String needSingleQuotes = data.nextToken().trim();
				String continueProcess = data.nextToken().trim();
				String dbUpdatable = data.nextToken().trim();

				recordContentsMap.put("fileKbn_w", fileKbn_w);
				recordContentsMap.put("recordKind", recordKind);
				recordContentsMap.put("itemCode", itemCode);
				recordContentsMap.put("lenScaleStr", lenScaleStr);
				recordContentsMap.put("parameterDM", parameterDM);
				recordContentsMap.put("type", type);
				recordContentsMap.put("align", align);
				recordContentsMap.put("preValue", preValue);
				recordContentsMap.put("addList", addList);
				recordContentsMap.put("chkMethod", chkMethod);
				recordContentsMap.put("param", param);
				//recordContentsMap.put("needSingleQuotes", needSingleQuotes);
				recordContentsMap.put("continueProcess", continueProcess);
				recordContentsMap.put("dbUpdatable", dbUpdatable);

				//substringの為にインデックスを溜めていく
				if (!lenScaleStr.trim().equals("0")) {
					beginIndex = totalValue;
					endIndex = totalValue + Integer.parseInt(lenScaleStr);
					totalValue = endIndex;
				}

				//indexMapにsubstringの為の値を格納する。
				List lst = new ArrayList();
				lst.add(0, recordKind);
				lst.add(1, new Integer(beginIndex));
				lst.add(2, new Integer(endIndex));
				indexMap.put(itemCode, lst);

				recordContentsPropertiesList.add(i, recordContentsMap);

				i++;
			}

			inputFileReader.close();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return recordContentsPropertiesList;
	}

	private String getItemValue(String key) {

		List lst = (List) indexMap.get(key);

		int bIdx = ((Integer) lst.get(1)).intValue();
		int eIdx = ((Integer) lst.get(2)).intValue();

		return line.substring(bIdx, eIdx);
	}

	protected List getResultList() throws Exception {
		return getResultList(null);
	}

	protected List getResultList(String recordKind) throws Exception {

		//リターンされる結果リスト
		List resultLst = new ArrayList();
		//BufferedReader
		BufferedReader in = null;

		//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 Start
		//FileReader
		FileReader fr = null;
		//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 End

		//recordContentsPropertiesListの取得
		List recordContentsPropertiesList = getRecordContentsPropertiesList(recordKind);

		//BufferedReaderクラスでFileReaderクラスのオブジェクトをラップ
		try {

			//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 Start
			//in = new BufferedReader(new FileReader(this.getFilePath()));
			fr = new FileReader(this.getFilePath());
			in = new BufferedReader(fr);
			//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 End

		} catch (FileNotFoundException fnfe) {
			batchLog.getLog().error(getBatchID(), getBatchNa(), "対象ファイルが存在しませんでした。");
			throw fnfe;
		}

		//(Map)recordContentsPropertiesList.get(i)を格納する為の臨時マップ
		Map map = null;
		//親クラスでメソッドを見つけた場合、子クラスはPASS。
		boolean passChildMethod = false;

		//Method配列(AbstractFileReceiveWithoutBean)
		Method[] superMethod = this.getClass().getSuperclass().getDeclaredMethods();
		//Method配列(子クラス)
		Method[] childMethod = this.getClass().getDeclaredMethods();

		//
		if (isKaigyoNasi) {

			int r = 0;
			int charCnt = 0;
			String ent = System.getProperty("line.separator");
			StringBuffer afterSubstring = new StringBuffer();

			while (true) {

				charCnt++;
				r = in.read();

				if ((int) r == -1)
					break;

				afterSubstring.append(String.valueOf((char) r));

				if ((charCnt % LINE_LENGTH) == 0) {
					afterSubstring.append(ent);
				}
			}
			in = new BufferedReader(new StringReader(afterSubstring.toString()));
		}

		//ファイルからテーブルへ、データを取り込む。
		while ((line = in.readLine()) != null) {

			//行ナンバーを取得する為にカウンターする。
			cnt++;

			//bodyLine(明細データ)を初期化する。
			bodyLine = new ArrayList();

			//1行の情報の処理
			for (int i = 0; i < recordContentsPropertiesList.size(); i++) {

				//2005/07/08 障害報告No.763 李武勇 Start
				setFalseForHasErr = false;
				//2005/07/08 障害報告No.763 李武勇 End

				//PropertiesListからMapをもらう。
				map = (Map) recordContentsPropertiesList.get(i);

				//DBにアップデートするんだったら、下の処理を行う。
				if (new Boolean(((String) map.get("dbUpdatable")).trim()).booleanValue()) {

					//ヴァリューを変数に格納する。
					value = getItemValue((String) map.get("itemCode"));

					//チェック：行の内容がLINE_LENGTHより小さい場合、ログ処理。
					if (line.length() != LINE_LENGTH) {
						// 行データがLINE_LENGTHbyteでなければエラーとする
						this.writeErrorRecord(cnt, LENGTH_ERR + ":【" + line.getBytes().length + "】バイト", line);
						hasErr = true;
						this.errCnt++;
						strForException = cnt + "行：行の長さが不正です。【" + line.getBytes().length + "】バイト";
						if ((map.get("continueProcess")).equals("continue")) {
							break;
						} else {
							isBreak = true;
							break;
						}
					}

					//チェックメソッドがあったら、行う。
					if (!(((String) map.get("chkMethod")).trim().equals(""))) {

						// 使うメソッドを持っているクラスを入れる配列
						Class[] typeArr = new Class[1];
						// 使うメソッドのパラメータを入れる配列
						Object[] paramArr = new Object[1];

						try {
							// クラスを配列に格納
							typeArr[0] = new String[0].getClass();

							// メソッド名とパラメータのタイプを格納
							Method method = null;
							//'true'だったら子クラスに該当するメソッドがあるかチェックしない。
							passChildMethod = false;

							for (int j = 0; j < superMethod.length; j++) {
								if (superMethod[j].getName().equals(map.get("chkMethod"))) {
									method =
										this.getClass().getSuperclass().getDeclaredMethod(
											(String) map.get("chkMethod"),
											typeArr);
									passChildMethod = true;
									break;
								}
							}
							if (!passChildMethod) {

								for (int j = 0; j < childMethod.length; j++) {
									if (childMethod[j].getName().equals(map.get("chkMethod"))) {
										method =
											this.getClass().getDeclaredMethod((String) map.get("chkMethod"), typeArr);
										break;
									}
								}
							}

							//メソッドに使われるパラメータを配列に入れる
							paramArr[0] = getStringForParam((String) map.get("param"));

							//メソッドを実行する
							if (!(((Boolean) method.invoke(this, paramArr)).booleanValue())) {

								//2005/07/08 障害報告No.763 李武勇 Start
								if (!setFalseForHasErr) {
									hasErr = true;
								}
								//2005/07/08 障害報告No.763 李武勇 End

								this.errCnt++;

								if ((map.get("continueProcess")).equals("continue")) {
									bodyLine.clear();
									break;
								} else if ((map.get("continueProcess")).equals("break")) {
									isBreak = true;

									strForException =
										cnt
											+ "行："
											+ getStringForLog((String) map.get("param"))
											+ "が不正です。【"
											+ value
											+ "】";

									break;
								}
							}
						} catch (SecurityException e1) {
							e1.printStackTrace();
						} catch (NoSuchMethodException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e2) {
							e2.printStackTrace();
						} catch (IllegalAccessException e2) {
							e2.printStackTrace();
						} catch (InvocationTargetException e2) {
							e2.printStackTrace();
						}
					}

					if (new Boolean(((String) map.get("addList")).trim()).booleanValue()) {
						if (((String) map.get("type")).trim().equalsIgnoreCase("X")) {
							bodyLine.add(StringUtility.elcloseBySingleQuotes(value));
						} else if (((String) map.get("type")).trim().equals("9")) {
							if (!value.trim().equals("")) {
								bodyLine.add(value);
							} else {
								bodyLine.add("NULL");
							}
						}
					}
				}
			}

			if (isBreak) {
				break;
			}

			//ラインのデータがあったら、bodyLineをresultLstにadd。
			if (bodyLine.size() != 0) {
				resultLst.add(bodyLine);
			}
		}

		//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 Start
		//in.close();
		//FileReaderをクローズする
		fr.close();
		//BufferedReaderをクローズする
		in.close();
		//2005/07/12 障害報告No.774	生成されたFileReaderオブジェクトがクローズされてないから、クローズする。 End

		return resultLst;
	}

	/**
	 *  Eアップロードファイルヘッダテーブルを更新します。
	 * @param db
	 * @param cnt
	 * @param line
	 * @param file_head_nb
	 * @return boolean
	 */
	protected boolean updateSyoriJyokyoFg(String file_head_nb) {

		//Eアップロードファイルヘッダの処理状況をエラーに更新
		try {
			dataBase.executeUpdate(getNohinMakeUpdateSql(file_head_nb));
		} catch (SQLException e) {
			dataBase.rollback();
			batchLog.getLog().error(getBatchID(), getBatchNa(), e.toString());
			e.printStackTrace();
			return false;
		}
		dataBase.commit();
		batchLog.getLog().error(getBatchID(), getBatchNa(), "ASNTYPE II  エラーにより、Eアップロードファイルヘッダテーブルへの更新が行われました。");

		return true;
	}

	/**
	 *  Eアップロードファイルヘッダテーブルを更新します。
	 * @param db
	 * @param cnt
	 * @param line
	 * @param file_head_nb
	 * @return boolean
	 */
	protected boolean changeHasErr(boolean isTrue) {

		//Eアップロードファイルヘッダの処理状況をエラーに更新
		try {
			dataBase.executeUpdate(getNohinMakeUpdateSql(file_head_nb));
		} catch (SQLException e) {
			dataBase.rollback();
			batchLog.getLog().error(getBatchID(), getBatchNa(), e.toString());
			e.printStackTrace();
			return false;
		}
		dataBase.commit();
		batchLog.getLog().error(getBatchID(), getBatchNa(), "ASNTYPE II  エラーにより、Eアップロードファイルヘッダテーブルへの更新が行われました。");

		return true;
	}

	/**
	 * Eアップロードファイルヘッダ情報の処理状況をエラーに更新するSQLを返します。
	 * @param file_head_nb
	 * @return SQL文
	 */
	private String getNohinMakeUpdateSql(String file_head_nb) {

		StringBuffer sb = new StringBuffer("");

		sb.append(" update e_upload_file_header ");
		sb.append("     set syori_jyokyo_fg = '" + SyoriJoukyouSt.ERROR.getCode() + "' ");
		sb.append(" where ");
		sb.append("     file_head_nb = '" + file_head_nb + "'");

		return sb.toString();
	}

	/**
	 * 全角文字を含めているストリングを切る為のメソッド
	 * @param str 元ストリング文字
	 * @param beginIdx
	 * @param endIdx
	 * @return String インデックスで切った後のストリング文字
	 */
	protected String cropByte(String str, int beginIdx, int endIdx) {

		if (str == null) {
			return "";
		}
		String tmp = str;
		int cursur = 0, beginSlen = 0, beginBlen = 0, endSlen = 0, endBlen = 0;
		char c0;
		char c1;

		if (tmp.getBytes().length > endIdx) {
			while (beginBlen != beginIdx) {
				c0 = tmp.charAt(cursur);
				beginBlen++;
				beginSlen++;
				if (c0 > 127) {
					beginIdx--;
				}
				cursur++;
			}

			cursur = 0;
			while (endBlen != endIdx) {
				c1 = tmp.charAt(cursur);
				endBlen++;
				endSlen++;
				if (c1 > 127) {
					endIdx--;
				}
				cursur++;
			}
			tmp = tmp.substring(beginSlen, endSlen);
		}
		return tmp;
	}

	/**
	 * ファイルの中にデータがあるかをチェックする。
	 * @param inputFilePath 集信ファイルパス
	 * @return 処理結果（成功:移動先のファイルパス　失敗:null）
	 */
	protected boolean hasDataInFile() throws IOException {

		//BufferedReader変数の初期化
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(this.getFilePath()));
			String tempString = "";
			while ((tempString = bufferedReader.readLine()) != null) {
				//データがあったら、処理を止める。
				if (tempString.trim().length() != 0) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			batchLog.getLog().error(getBatchID(), getBatchNa(), "対象ファイルが存在しませんでした。");
			e.printStackTrace();
			throw e;
		} catch (IOException e1) {
			batchLog.getLog().error(getBatchID(), getBatchNa(), e1.getMessage());
			e1.printStackTrace();
			throw e1;
		} finally {
			bufferedReader.close();
		}
		batchLog.getLog().info(getBatchID(), getBatchNa(), "ファイルにデータがありません。");

		return false;
	}

	/**
	 * propertiesファイルのパラメータ項目からログの為に項目名を取得する。
	 * @param str
	 * @return 項目名
	 */
	private String getStringForLog(String str) throws FileDBConvertException {

		String[] result = str.split("%", -1);

		if (result.length == 0) {
			batchLog.getLog().warn(getBatchID(), getBatchNa(), "getStringForLog(String str)のパラメータがありません。");
			throw new FileDBConvertException("getStringForLog(String str)のパラメータがありません。");
		}
		return result[0];
	}

	/**
	 * propertiesファイルのパラメータ項目からログの為にパラメータを取得する。
	 * @param str
	 * @return 項目名
	 */
	private String[] getStringForParam(String str) throws FileDBConvertException {

		String[] result = str.split("%", -1);

		if (result.length == 0) {
			batchLog.getLog().warn(getBatchID(), getBatchNa(), "getStringForParam(String str)のパラメータがありません。");
			throw new FileDBConvertException("getStringForParam(String str)のパラメータがありません。");
		}

		return result;
	}

	//2005/07/12 障害報告No.770	ASNTYPEⅡワークテーブルに項目追加。 Start
	/**
	 * ＤＢサーバーの日付を取得します。
	 * @return ret(YYYYMMDDhhmmss)
	 */
	protected String getSysDate() {

		String ret = "";

		try {
			ResultSet rs = dataBase.executeQuery("SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') SYSDT FROM DUAL");

			if (rs.next()) {
				ret = rs.getString("SYSDT");
			} else {
				batchLog.getLog().error(getBatchID(), getBatchNa(), "SYSDATEの取得に失敗しました。");
			}
		} catch (Exception e) {
			batchLog.getLog().error(getBatchID(), getBatchNa(), "SYSDATEの取得時、システムエラーが発生しました。" + e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * ASNTYPE2のシーケンシャルNo取得
	 * @param tblName 連番を取得するテーブル名(ASN系)
	 * @return int 連番
	 */
	/*
		protected int getSeqNo(String seqNbColName, String tblName, String sysdate) {
	
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer("");
			int seqNb = 0;
	
			sb.append("SELECT (NVL(MAX(" + seqNbColName + "), -1) + 1) SEQ_NB FROM " + tblName);
			sb.append("	" + "WHERE TORIKOMI_TS = '" + sysdate + "'");
			try {
				rs = dataBase.executeQuery(sb.toString());
	
				if (rs.next()) {
					seqNb = rs.getInt("SEQ_NB");
				} else {
					batchLog.getLog().error(getBatchID(), getBatchNa(), "連番の取得に失敗しました。");
				}
			} catch (SQLException e) {
				batchLog.getLog().error(getBatchID(), getBatchNa(), "連番の取得時、システムエラーが発生しました。" + e.getMessage());
				e.printStackTrace();
			}
			return seqNb;
		}
	*/
	//2005/07/12 障害報告No.770 ASNTYPEⅡワークテーブルに項目追加。End

	/**
	 * 親のshareDirにshare_dir_collect_txをセットする。
	 */
	protected void setShareDirCollectTx() {
		sysBh = new SystemConfBeanHolder();
		shareDir = sysBh.getSysConfBean().getShareDirCollectTx();
	}

	//2005/06/29 iwasaki add Start
	/**
	 * 集信ファイルをワークディレクトリに移動します。
	 * @param inputFilePath 集信ファイルパス
	 * @return 処理結果（成功:移動先のファイルパス　失敗:null）
	 */
	protected String moveTempDir(String inputFilePath) {
		SystemConfBean bean = new SystemConfBeanHolder().getSysConfBean();

		//移動先ファイルパス
		String moveFilePath =
			bean.getTempDirTx()
				+ "/"
				+ new FunctionBeanHolder().getIfFileHeadName(this.getDataKind())
				+ RetailInterface.SUFFIX_DATA;
		if (!DataExchanger.exchange(DataExchanger.NO_EXCHANGE_MOVE, inputFilePath, moveFilePath)) {
			batchLog.getLog().warn(this.getBatchID(), this.getBatchNa(), "\"" + inputFilePath + "\"の移動に失敗しました。");
			return null;
		}
		return moveFilePath;
	}

	//2005/06/29 iwasaki add End

}